package com.northwind.repositories;

import com.northwind.entities.Product;
import com.northwind.handlers.ProductCategoryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface ProductRepository extends MongoRepository<Product, Integer> {
    Product findFirstById(int productId);
    @Query("{$expr: {$gt: ['$unitsInStock', '$unitsOnOrder']} }")
    List<Product> findAllByNameContains(String name, Sort sort);
    List<Product> findAllByUnitPriceBetweenOrderByUnitPrice(double priceFrom, double priceTo, Sort sort);
    @Query("{ 'unitPrice' : { $gte: ?0, $lte: ?1 } }")
    List<Product> findAllProductByPriceBetween (double priceFrom, double priceTo, Sort sort);
    @Query("{$expr: {$gt: ['$unitsInStock', '$unitsOnOrder']} }")
    List<Product> findAllOffer(Sort sort);
    @Query("{ 'unitsInStock' : { $lte: ?0} }")
    List<Product> findAllProductsWithLowStock(int stockNumber, Sort sort);


    @Aggregation(pipeline = {"{$lookup:{from: 'order',let: { pid: '$_id'},	" +
            "pipeline:[{$match: {$expr: {$in: [ '$$pid', '$orderDetails.productID' ]  } } }],as: 'result'}}" ,
            "{$match:{ 'result': []}}",
             "{$lookup:{from: 'category', localField:'categoryId',foreignField: '_id', as: 'category'}}"}
    )
    AggregationResults<ProductCategoryHandler> getAllUnBoughtProductWithCategory();
}
