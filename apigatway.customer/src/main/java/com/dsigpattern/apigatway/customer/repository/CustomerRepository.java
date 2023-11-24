package com.dsigpattern.apigatway.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsigpattern.apigatway.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByName(String name);

}