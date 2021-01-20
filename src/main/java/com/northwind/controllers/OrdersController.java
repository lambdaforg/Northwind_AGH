package com.northwind.controllers;

import com.northwind.entities.Order;
import com.northwind.entities.OrderDetail;
import com.northwind.entities.Product;
import com.northwind.handlers.BasketProduct;
import com.northwind.handlers.EditOrderRequest;
import com.northwind.handlers.OrderRequest;
import com.northwind.repositories.OrderRepository;
import com.northwind.repositories.ProductRepository;
import com.northwind.services.OrderService;
import com.northwind.services.ProductService;
import com.northwind.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes({"basketProductList", "order"})
public class OrdersController {
    @ModelAttribute("basketProductList")
    public HashMap<Integer, BasketProduct> basketProducts() {
        return new HashMap<>();
    }

    @ModelAttribute("order")
    public OrderRequest order() {
        return new OrderRequest();
    }

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @GetMapping("/addProductToBasket/{id}")
    public ModelAndView addProductToBasket(@ModelAttribute("basketProductList") HashMap<Integer, BasketProduct> basketProductList, @PathVariable int id, Model model, RedirectAttributes attributes) {
        var tempProd = productService.getProduct(id);
        if (basketProductList.containsKey(id)) {
            basketProductList.get(id).setCount(basketProductList.get(id).getCount() + 1);
        } else {
            var prod = new BasketProduct();
            prod.setCount(1);
            prod.setProductId(id);
            prod.setPricePerUnit(tempProd.getUnitPrice());
            prod.setProductName(tempProd.getName());
            basketProductList.put(id, prod);
        }
        attributes.addFlashAttribute("basketProductList", basketProductList);
        tempProd.setUnitsOnOrder(tempProd.getUnitsOnOrder() + 1);
        productService.saveProduct(tempProd);
        return new ModelAndView("redirect:" + "/");
    }

    @GetMapping("/getBasketProducts")
    public String getBasketProducts(@ModelAttribute("basketProductList") HashMap<Integer, BasketProduct> basketProductList, @ModelAttribute("order") OrderRequest order, Model model, RedirectAttributes attributes) {
        List<BasketProduct> products = new ArrayList<>();
        for (Map.Entry<Integer, BasketProduct> entry : basketProductList.entrySet()) {
            products.add(entry.getValue());
        }
        model.addAttribute("basketProducts", products);
        model.addAttribute("totalPrice", products.stream().map(BasketProduct::getTotalPrice).reduce(0.0, (a, b) -> a + b));
        model.addAttribute("totalQuantity", products.stream().map(BasketProduct::getCount).reduce(0, (a, b) -> a + b));
        return "/fragments/basket";
    }

    @GetMapping("/addUnit/{id}")
    public ModelAndView addPiece(@ModelAttribute("basketProductList") HashMap<Integer, BasketProduct> basketProductList, @PathVariable int id, Model model, RedirectAttributes attributes) {
        var prod = productService.getProduct(id);
        if (prod.getUnitsInStock() != prod.getUnitsOnOrder()) {
            basketProductList.get(id).setCount(basketProductList.get(id).getCount() + 1);
            prod.setUnitsOnOrder(prod.getUnitsOnOrder() + 1);
            productService.saveProduct(prod);
        }
        attributes.addFlashAttribute("basketProductList", basketProductList);
        return new ModelAndView("redirect:" + "/getBasketProducts");
    }

    @GetMapping("/removeUnit/{id}")
    public ModelAndView removePiece(@ModelAttribute("basketProductList") HashMap<Integer, BasketProduct> basketProductList, @PathVariable int id, Model model, RedirectAttributes attributes) {
        if (basketProductList.containsKey(id)) {
            if (basketProductList.get(id).getCount() == 1)
                basketProductList.remove(id);
            else
                basketProductList.get(id).setCount(basketProductList.get(id).getCount() - 1);
        }
        var prod = productService.getProduct(id);
        prod.setUnitsOnOrder(prod.getUnitsOnOrder() - 1);
        productService.saveProduct(prod);
        attributes.addFlashAttribute("basketProductList", basketProductList);
        return new ModelAndView("redirect:" + "/getBasketProducts");
    }

    @PostMapping("/makeOrder")
    public ModelAndView makeOrder(@ModelAttribute("basketProductList") HashMap<Integer, BasketProduct> products, @ModelAttribute OrderRequest orderReq, Model model) {
        Order order = new Order();
        order.setId(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME));
        var orderDate = new Date();
        orderDate.setHours(orderDate.getHours() + 1);
        order.setOrderDate(new Date());
        order.setShipAddress(orderReq.shipAddress);
        order.setShipCity(orderReq.shipCity);
        order.setShipPostalCode(orderReq.shipPostalCode);
        order.setShipRegion(orderReq.shipRegion);
        order.setShipCountry(orderReq.shipCountry);
        order.setShipName(orderReq.shipName);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(orderReq.requiredDate);
            date.setHours(date.getHours() + 1);
            order.setRequireDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (Map.Entry<Integer, BasketProduct> entry : products.entrySet()) {
            OrderDetail detail = new OrderDetail();
            var value = entry.getValue();
            detail.setProductID(entry.getKey());
            detail.setQuantity(value.getCount());
            detail.setUnitPrice(value.getPricePerUnit());
            order.addOrderDetail(detail);
            var prod = productService.getProduct(value.getProductId());
            prod.setUnitsInStock(prod.getUnitsInStock() - value.getCount());
            prod.setUnitsOnOrder(prod.getUnitsOnOrder() - value.getCount());
            productService.saveProduct(prod);
        }
        orderService.saveOrder(order);
        products.clear();
        return new ModelAndView("redirect:" + "/");
    }

    @GetMapping("/dashboard/editOrder/{id}")
    public String editProduct(@PathVariable int id, Model model) {
        Order order = orderService.getOrder(id);
        EditOrderRequest req = new EditOrderRequest();
        req.id = order.id;
        req.shipName = order.shipName;
        req.orderDate = order.getFormattedOrderDate();
        req.shippedDate = order.getFormattedShippedDate();
        req.requireDate = order.getFormattedRequireDate();
        req.shipAddress = order.shipAddress;
        req.shipCity = order.shipCity;
        req.shipCountry = order.shipCountry;
        req.shipPostalCode = order.shipPostalCode;
        req.shipRegion = order.shipRegion;
        model.addAttribute("orderReq", req);
        return "/admin/editOrder";
    }

    @PostMapping("/dashboard/deleteOrder/{id}")
    public ModelAndView deleteProduct(@PathVariable int id, Model model) {
        orderService.removeOrder(id);
        return new ModelAndView("redirect:" + "/dashboard/managementAll");
    }

    @PostMapping("/dashboard/saveOrder")
    public ModelAndView saveProduct(@ModelAttribute EditOrderRequest orderReq, Model model) {
        Order order = orderService.getOrder(orderReq.id);
        if (orderReq.shippedDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = formatter.parse(orderReq.shippedDate);
                date.setHours(date.getHours() + 1);
                order.setShippedDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        order.shipName = orderReq.shipName;
        order.shipAddress = orderReq.shipAddress;
        order.shipCity = orderReq.shipCity;
        order.shipCountry = orderReq.shipCountry;
        order.shipPostalCode = orderReq.shipPostalCode;
        order.shipRegion = orderReq.shipRegion;
        orderService.saveOrder(order);
        return new ModelAndView("redirect:" + "/dashboard/managementAll");
    }
}
