package com.dsigpattern.apigatway.order.service;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsigpattern.apigatway.order.entity.Customer;
import com.dsigpattern.apigatway.order.entity.Order;
import com.dsigpattern.apigatway.order.exception.NotFoundException;
import com.dsigpattern.apigatway.order.feign.CustomerFeignClient;
import com.dsigpattern.apigatway.order.repository.OrderRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerFeignClient customerFeignClient;

    public Order createOrder(Order order) {
        try {
            // Validate customer existence before creating the order
            validateCustomerExistence(order.getCustomerId());

            Order createdOrder = orderRepository.save(order);
            logger.info("Created order with id: {}", createdOrder.getId());
            return createdOrder;
        } catch (NotFoundException e) {
            logger.warn("Customer not found. Could not create order.", e);
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while creating order", e);
            throw new ServiceException("Could not create order", e);
        }
    }

    public List<Order> getAllOrders() {
        try {
            List<Order> orders = orderRepository.findAll();
            logger.info("Retrieved all orders. Count: {}", orders.size());
            return orders;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving all orders", e);
            throw new ServiceException("Could not retrieve orders", e);
        }
    }

    public Order getOrderById(Long id) {
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Order not found with id: " + id));
            logger.info("Retrieved order by id: {}", id);
            return order;
        } catch (NotFoundException e) {
            logger.warn("Order not found with id: {}", id);
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving order by id: {}", id, e);
            throw new ServiceException("Could not retrieve order", e);
        }
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        try {
            // Validate customer existence before updating the order
            validateCustomerExistence(updatedOrder.getCustomerId());

            if (!orderRepository.existsById(id)) {
                logger.warn("Order not found with id: {}", id);
                throw new NotFoundException("Order not found with id: " + id);
            }

            updatedOrder.setId(id);
            Order savedOrder = orderRepository.save(updatedOrder);
            logger.info("Updated order with id: {}", id);
            return savedOrder;
        } catch (NotFoundException e) {
            logger.warn("Customer not found. Could not update order.", e);
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while updating order with id: {}", id, e);
            throw new ServiceException("Could not update order", e);
        }
    }

    public void deleteOrder(Long id) {
        try {
            if (!orderRepository.existsById(id)) {
                logger.warn("Order not found with id: {}", id);
                throw new NotFoundException("Order not found with id: " + id);
            }

            orderRepository.deleteById(id);
            logger.info("Deleted order with id: {}", id);
        } catch (NotFoundException e) {
            logger.warn("Order not found with id: {}", id);
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while deleting order with id: {}", id, e);
            throw new ServiceException("Could not delete order", e);
        }
    }

    private void validateCustomerExistence(Long customerId) {
        try {
            // Use the Feign client to check if the customer exists
            Customer customer = customerFeignClient.getCustomerById(customerId);
            if (customer == null) {
                throw new NotFoundException("Customer not found with id: " + customerId);
            }
        } catch (Exception e) {
            logger.error("Error occurred while validating customer existence for customer id: {}", customerId, e);
            throw new ServiceException("Error validating customer existence", e);
        }
    }
}
