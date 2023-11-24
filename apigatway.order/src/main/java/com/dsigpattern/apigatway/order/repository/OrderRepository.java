package com.dsigpattern.apigatway.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsigpattern.apigatway.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}