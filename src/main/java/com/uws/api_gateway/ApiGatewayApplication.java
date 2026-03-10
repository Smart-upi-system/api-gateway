package com.uws.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                // Auth Service Routes (No JWT required)
//                .route("auth-service", r -> r
//                        .path("/api/auth/**")
//                        .filters(f -> f
//                                .stripPrefix(1)
//                                .circuitBreaker(c -> c.setName("authCircuitBreaker"))
//                        )
//                        .uri("http://localhost:4001"))
//
//                // User Service Routes
//                .route("user-service", r -> r
//                        .path("/api/users/**")
//                        .filters(f -> f
//                                .stripPrefix(1)
//                                .circuitBreaker(c -> c.setName("userCircuitBreaker"))
//                        )
//                        .uri("http://localhost:4002"))
//
//                // Transaction Service Routes
//                .route("transaction-service", r -> r
//                        .path("/api/transactions/**")
//                        .filters(f -> f
//                                .stripPrefix(1)
//                                .circuitBreaker(c -> c.setName("transactionCircuitBreaker"))
//                        )
//                        .uri("http://localhost:4003"))
//
//                // Ledger Service Routes (Read-only)
//                .route("ledger-service", r -> r
//                        .path("/api/ledger/**")
//                        .filters(f -> f
//                                .stripPrefix(1)
//                                .circuitBreaker(c -> c.setName("ledgerCircuitBreaker"))
//                        )
//                        .uri("http://localhost:4004"))
//
//                .build();
//    }


}
