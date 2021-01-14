package com.northwind.controllers;

import com.northwind.entities.Category;
import com.northwind.entities.Order;
import com.northwind.entities.Product;
import com.northwind.handlers.ReportRequest;
import com.northwind.services.CategoryService;
import com.northwind.services.OrderService;
import com.northwind.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Controller
public class ReportsController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @GetMapping("/dashboard/reports")
    public String getReports(Model model) {
        model.addAttribute("initReport", new ReportRequest());
        initGeneralReport(model);
        return "reports/menu";
    }

    /**
     * Finds the most popular product and order count
     */
    public void setTopProduct(Model model, boolean isMonthly, List<Order> orders) {
        Map<Integer, Integer> orderedProductsCount = new HashMap<>();
        orders.forEach(order ->
                order.getOrderDetails()
                        .forEach(orderDetail ->
                                orderedProductsCount.put(orderDetail.getProductID(),
                                        orderedProductsCount.getOrDefault(orderDetail.getProductID(), 0)
                                                + (int) orderDetail.getQuantity())
                        )
        );
        Product product;
        int productCount;
        if (orderedProductsCount.size() > 0) {
            product = productService.getProduct(orderedProductsCount
                    .entrySet()
                    .stream()
                    .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                    .get()
                    .getKey());
            productCount = orderedProductsCount.get(product.getId());
        } else {
            product = null;
            productCount = 0;
        }
        if (isMonthly) {
            model.addAttribute("mTopProduct", product);
            model.addAttribute("mTopProductCount", productCount);
        } else {
            model.addAttribute("topProduct", product);
            model.addAttribute("topProductCount", productCount);
        }
    }

    /**
     * Finds the most popular category and order count
     */
    public void setTopCategory(Model model, boolean isMonthly, List<Order> orders) {
        Map<Integer, Integer> orderedCategoriesCount = new HashMap<>();
        orders.forEach(order ->
                order.getOrderDetails()
                        .forEach(orderDetail -> {
                            int categoryId = productService.getProduct(orderDetail.productID).categoryId;
                            orderedCategoriesCount.put(categoryId,
                                    orderedCategoriesCount.getOrDefault(categoryId, 0)
                                            + (int) orderDetail.getQuantity());
                        })
        );
        Category category;
        int categoryCount;
        if (orderedCategoriesCount.size() > 0) {
            category = categoryService.getCategoryById(orderedCategoriesCount
                    .entrySet()
                    .stream()
                    .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                    .get()
                    .getKey());
            categoryCount = orderedCategoriesCount.get(category.getId());
        } else {
            category = null;
            categoryCount = 0;
        }
        if (isMonthly) {
            model.addAttribute("mTopCategory", category);
            model.addAttribute("mTopCategoryCount", categoryCount);
        } else {
            model.addAttribute("topCategory", category);
            model.addAttribute("topCategoryCount", categoryCount);
        }
    }

    /**
     * Counts total income and average order income
     */
    public void countTotalIncome(Model model, boolean isMonthly, List<Order> orders) {
        AtomicReference<Double> totalIncome = new AtomicReference<>(0.0);
        int ordersCount = orders.size();
        orders.forEach(order ->
                order.getOrderDetails()
                        .forEach(orderDetail -> {
                            totalIncome.updateAndGet(v -> (v + orderDetail.getQuantity() * orderDetail.getUnitPrice()));
                        })
        );
        double formattedTotalIncome = BigDecimal.valueOf(totalIncome.get())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        if (isMonthly) {
            model.addAttribute("mTotalIncome", formattedTotalIncome);
            model.addAttribute("mOrdersCount", ordersCount);
            if (ordersCount > 0) {
                model.addAttribute("mAverageIncome", formattedTotalIncome / ordersCount);
            } else {
                model.addAttribute("mAverageIncome", 0.0);
            }
        } else {
            model.addAttribute("totalIncome", formattedTotalIncome);
            model.addAttribute("ordersCount", ordersCount);
            if (ordersCount > 0) {
                model.addAttribute("averageIncome", formattedTotalIncome / ordersCount);
            } else {
                model.addAttribute("averageIncome", 0.0);
            }
        }
    }

    private void  initGeneralReport(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);

        setTopCategory(model, false, orders);
        setTopProduct(model, false, orders);
        countTotalIncome(model, false, orders);
    }

    @PostMapping("/dashboard/reports/monthlyReport")
    public String initMonthlyReport(Model model, @ModelAttribute ReportRequest reportRequest) {
        model.addAttribute("reportRequest", reportRequest);
        List<Order> allOrders = orderService.getAllOrders();
        List<Order> filteredOrders;
        YearMonth yearMonth = reportRequest.getYearMonth();
        filteredOrders = allOrders
                .stream()
                .filter(order -> isDateIncluded(order.orderDate, yearMonth))
                .collect(Collectors.toList());

        setTopCategory(model, true, filteredOrders);
        setTopProduct(model, true, filteredOrders);
        countTotalIncome(model, true, filteredOrders);
        model.addAttribute("initReport", new ReportRequest());
        initGeneralReport(model);
        return "reports/menu";
    }

    private boolean isDateIncluded(Date date, YearMonth yearMonth) {
        Date firstDay = Date.from(yearMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date lastDay = Date.from(yearMonth.atDay(
                yearMonth.atEndOfMonth()
                        .getDayOfMonth())
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        date = trimDate(date);

        return !date.before(firstDay) && !date.after(lastDay);
    }

    private Date trimDate(Date date) {
        Calendar result = Calendar.getInstance();
        result.setTime(date);
        result.set(Calendar.MINUTE, 0);
        result.set(Calendar.SECOND, 0);
        result.set(Calendar.HOUR_OF_DAY, 0);
        return result.getTime();
    }
}

