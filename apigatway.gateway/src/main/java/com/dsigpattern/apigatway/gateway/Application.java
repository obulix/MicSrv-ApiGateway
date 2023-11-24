package com.dsigpattern.apigatway.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
/*
import com.dsigpattern.apigatway.gateway.filters.ErrorFilter;
import com.dsigpattern.apigatway.gateway.filters.PostFilter;
import com.dsigpattern.apigatway.gateway.filters.PreFilter;
import com.dsigpattern.apigatway.gateway.filters.RouteFilter;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
*/
//@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	/*  @Bean
	  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
	  return builder.routes()
	    .route("customer-service", r -> r.path("/customers/**")
	      .filters(f -> f.stripPrefix(1))
	      .uri("lb://customers"))
	    .build();
	  }
*/
	
/*	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}
	@Bean
	public PostFilter postFilter() {
		return new PostFilter();
	}
	@Bean
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}
	@Bean
	public RouteFilter routeFilter() {
		return new RouteFilter();
	}*/
}
