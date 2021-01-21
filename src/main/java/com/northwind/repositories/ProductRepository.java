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

import java.util.Date;
import java.util.List;


public interface ProductRepository extends MongoRepository<Product, Integer> {
    Product findFirstById(int productId);
    @Query("{$expr: {$gt: ['$unitsInStock', '$unitsOnOrder']}}")
    List<Product> findAllByNameContains(String name, Sort sort);
    List<Product> findAllByUnitPriceBetweenOrderByUnitPrice(double priceFrom, double priceTo, Sort sort);
    @Query("{ 'orderDate' : { $gte: ?0, $lte: ?1 } }")
    List<Product> findAllProductByPriceBetween (double priceFrom, double priceTo, Sort sort);
    @Query("{$expr: {$gt: ['$unitsInStock', '$unitsOnOrder']} }")
    List<Product> findAllOffer(Sort sort);
    @Query("{ 'unitsInStock' : { $lte: ?0} }")
    List<Product> findAllProductsWithLowStock(int stockNumber, Sort sort);


    /*
     *Zapytanie poniżej szuka wszystkich produktów którę w danym okresie czasu, się nie sprzedały,
     * przeszukuje wiec order i zlicza wystąpienia danego id produktu w orderDetails, i dodajego go do wyniku jako result
     * nastepnie jest match ktory filtruje tylko result z pusta tabele (oznacza to wtedy ze nigdy nie wystapiło kupno produktu)
     * nastepnie pobieranie sa dodatkowe dane jak kategoria do wyniku
     */


    @Aggregation(pipeline = {"{$lookup:{from: 'order',let: { pid: '$_id'},	" +
            "pipeline:[{$match:{ " +
            "orderDate: {'$gte': ?0, '$lte': ?1},$expr:{$in: [ '$$pid', '$orderDetails.productID' ] } }}],as: 'result'}}",
            "{$match:{ 'result': []}}",
             "{$lookup:{from: 'category', localField:'categoryId',foreignField: '_id', as: 'category'}}"}
    )
    AggregationResults<ProductCategoryHandler> getAllUnBoughtProductWithCategory(Date dateFrom, Date dateTo);
    /*
  Zapytanie poniżej szuka wszystkich produktów którę w danym okresie czasu, się nie sprzedały,
     * przeszukuje wiec order i zlicza wystąpienia danego id produktu w orderDetails, i dodajego go do wyniku jako result
        * grupuje po categoryId, dodaje do tablicy item: produkty, i sumuje rozmiar tablicy result
        * jesli arrSum jest równy 0 znaczy to tyle że każdy produkt miał wynik z result równym 0 (pusta tablica) czyli nie był sprzedany w tamtym okresie czasu
        * nastepnie pobierane sa dane z kategorii ( zeby dostać nazwe kategorii) - lookup
        * ostatecznie wybierane sa tylko tablice item i categoryname ( czyli nazwa kategorii) dzieki uzyciu project do wyniku
      */
    @Aggregation(pipeline = {"{$lookup:{from: 'order',let: { pid: '$_id'},	" +
            "pipeline:[{$match:{ " +
            "orderDate: {'$gte': ?0, '$lte': ?1},$expr:{$in: [ '$$pid', '$orderDetails.productID' ] } }}],as: 'result'}}",
            "{$group:{" +
            "'_id': '$categoryId'," +
            "'item': { $push: '$name' }," +
            "'arrSum': {$sum:  {$size: '$result'}}" +
            "}},",
            "{$match: {'arrSum': {$eq: 0}}}" ,
            "{$lookup:{from: 'category', localField: '_id', foreignField:'_id', as: 'category'}}" ,
            "{$project:{item: 1, categoryName: '$category.name'}}"}
    )
    AggregationResults<ProductCategoryHandler> getAllCategoryWithUnBoughtProducts(Date dateFrom, Date dateTo);

}
