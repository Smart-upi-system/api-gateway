package com.uws.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

//This is the main class for the API Gateway service. It is responsible for starting the Spring Boot application and enabling service discovery. The @SpringBootApplication annotation indicates that this is a Spring Boot application, and the @EnableDiscoveryClient annotation enables service discovery for this application.
// The main method runs the application using SpringApplication.run().
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}
