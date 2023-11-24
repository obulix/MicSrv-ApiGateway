package com.dsigpattern.apigatway.customer.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsigpattern.apigatway.customer.entity.Customer;
import com.dsigpattern.apigatway.customer.repository.CustomerRepository;
import com.dsigpattern.apigatway.customer.util.exceptions.NotFoundException;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        try {
            Customer savedCustomer = customerRepository.save(customer);
            logger.info("Created a new customer with id: {}", savedCustomer.getId());
            return savedCustomer;
        } catch (Exception e) {
            logger.error("Error occurred while creating a customer", e);
            throw new ServiceException("Could not create customer", e);
        }
    }

    public List<Customer> getAllCustomers() {
        try {
            List<Customer> customers = customerRepository.findAll();
            logger.info("Retrieved all customers. Count: {}", customers.size());
            return customers;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving all customers", e);
            throw new ServiceException("Could not retrieve customers", e);
        }
    }

    public Customer getCustomerById(Long id) {
        try {
            Optional<Customer> optionalCustomer = customerRepository.findById(id);
            if (optionalCustomer.isPresent()) {
                logger.info("Retrieved customer by id: {}", id);
                return optionalCustomer.get();
            } else {
                logger.warn("Customer not found with id: {}", id);
                throw new NotFoundException("Customer not found with id: " + id);
            }
        } catch (Exception e) {
            logger.error("Error occurred while retrieving customer by id: {}", id, e);
            throw new ServiceException("Could not retrieve customer", e);
        }
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        try {
            if (!customerRepository.existsById(id)) {
                logger.warn("Customer not found with id: {}", id);
                throw new NotFoundException("Customer not found with id: " + id);
            }

            updatedCustomer.setId(id);
            Customer savedCustomer = customerRepository.save(updatedCustomer);
            logger.info("Updated customer with id: {}", id);
            return savedCustomer;
        } catch (Exception e) {
            logger.error("Error occurred while updating customer with id: {}", id, e);
            throw new ServiceException("Could not update customer", e);
        }
    }

    public void deleteCustomer(Long id) {
        try {
            if (!customerRepository.existsById(id)) {
                logger.warn("Customer not found with id: {}", id);
                throw new NotFoundException("Customer not found with id: " + id);
            }

            customerRepository.deleteById(id);
            logger.info("Deleted customer with id: {}", id);
        } catch (Exception e) {
            logger.error("Error occurred while deleting customer with id: {}", id, e);
            throw new ServiceException("Could not delete customer", e);
        }
    }
}