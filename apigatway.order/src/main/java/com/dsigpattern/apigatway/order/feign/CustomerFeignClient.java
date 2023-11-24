package com.dsigpattern.apigatway.order.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dsigpattern.apigatway.order.entity.Customer;


@FeignClient(name = "customer-service") 
public interface CustomerFeignClient {

    @PostMapping("/customers")
    Customer createCustomer(@RequestBody Customer customer);

    @GetMapping("/customers")
    List<Customer> getAllCustomers();

    @GetMapping("/customers/{id}")
    Customer getCustomerById(@PathVariable Long id);

    @PutMapping("/customers/{id}")
    Customer updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer);

    @DeleteMapping("/customers/{id}")
    void deleteCustomer(@PathVariable Long id);
}