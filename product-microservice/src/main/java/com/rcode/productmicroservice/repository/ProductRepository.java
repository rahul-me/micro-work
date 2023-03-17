package com.rcode.productmicroservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rcode.productmicroservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
