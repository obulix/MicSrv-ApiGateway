package com.dsigpattern.apigatway.customer.controller;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsigpattern.apigatway.customer.entity.Customer;
import com.dsigpattern.apigatway.customer.service.CustomerService;
import com.dsigpattern.apigatway.customer.util.exceptions.NotFoundException;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            Customer createdCustomer = customerService.createCustomer(customer);
            logger.info("Created customer with id: {}", createdCustomer.getId());
            return ResponseEntity.ok(createdCustomer);
        } catch (ServiceException e) {
            logger.error("Error creating customer", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            logger.info("Retrieved all customers. Count: {}", customers.size());
            return ResponseEntity.ok(customers);
        } catch (ServiceException e) {
            logger.error("Error retrieving all customers", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        try {
            Customer customer = customerService.getCustomerById(id);
            logger.info("Retrieved customer by id: {}", id);
            return ResponseEntity.ok(customer);
        } catch (NotFoundException e) {
            logger.warn("Customer not found with id: {}", id);
            return ResponseEntity.notFound().build();
        } catch (ServiceException e) {
            logger.error("Error retrieving customer by id: {}", id, e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        try {
            Customer customer = customerService.updateCustomer(id, updatedCustomer);
            logger.info("Updated customer with id: {}", id);
            return ResponseEntity.ok(customer);
        } catch (NotFoundException e) {
            logger.warn("Customer not found with id: {}", id);
            return ResponseEntity.notFound().build();
        } catch (ServiceException e) {
            logger.error("Error updating customer with id: {}", id, e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            logger.info("Deleted customer with id: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            logger.warn("Customer not found with id: {}", id);
            return ResponseEntity.notFound().build();
        } catch (ServiceException e) {
            logger.error("Error deleting customer with id: {}", id, e);
            return ResponseEntity.status(500).build();
        }
    }
}