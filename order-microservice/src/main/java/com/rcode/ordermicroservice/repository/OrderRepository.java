package com.rcode.ordermicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rcode.ordermicroservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
