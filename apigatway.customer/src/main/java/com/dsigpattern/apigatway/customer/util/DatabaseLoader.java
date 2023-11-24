package com.dsigpattern.apigatway.customer.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dsigpattern.apigatway.customer.entity.Customer;
import com.dsigpattern.apigatway.customer.repository.CustomerRepository;


@Configuration
public class DatabaseLoader {

	@Bean
	CommandLineRunner init(CustomerRepository repository) {
		return args -> {
			repository.save(new Customer("Kumar"));
			repository.save(new Customer("Nirmal"));
			repository.save(new Customer("Sampath"));
			repository.save(new Customer("Manohar"));
			repository.save(new Customer("Siva"));
		};
	}
}