package com.rcode.productmicroservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rcode.productmicroservice.dto.ProductRequest;
import com.rcode.productmicroservice.dto.ProductResponse;
import com.rcode.productmicroservice.model.Product;
import com.rcode.productmicroservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
	
	private final ProductRepository productRepository;
	
	public void createProduct(ProductRequest productRequest) {
		Product product = Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.build();
		
		productRepository.save(product);
		log.info("Product {} is saved", product.getId());
	}

	public List<ProductResponse> getAllProducts() {
		List<Product> products = productRepository.findAll();
		return products.stream().map(product -> ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice()).build()).toList();
		
	}
}
