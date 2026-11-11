package com.uws.api_gateway.config;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.cors.reactive.CorsWebFilter;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class CorsConfigurationTests {
    private final CorsWebFilter filter = new CorsWebFilter(new SecurityConfig().corsConfigurationSource());

    @Test
    void permitsProfilePreflightFromBothFrontendPorts() {
        for (String origin : new String[]{"http://localhost:3000", "http://localhost:5173"}) {
            MockServerWebExchange exchange = preflight(origin);
            filter.filter(exchange, ignored -> Mono.error(new AssertionError("Preflight must end before authentication"))).block();
            assertEquals(origin, exchange.getResponse().getHeaders().getAccessControlAllowOrigin());
            assertTrue(exchange.getResponse().getHeaders().getAccessControlAllowCredentials());
            assertTrue(exchange.getResponse().getHeaders().getAccessControlAllowHeaders().contains("authorization"));
            assertTrue(exchange.getResponse().getHeaders().getAccessControlAllowHeaders().contains("x-user-id"));
        }
    }

    @Test
    void rejectsUnlistedOrigin() {
        MockServerWebExchange exchange = preflight("https://untrusted.example");
        filter.filter(exchange, ignored -> Mono.empty()).block();
        assertEquals(HttpStatus.FORBIDDEN, exchange.getResponse().getStatusCode());
        assertNull(exchange.getResponse().getHeaders().getAccessControlAllowOrigin());
    }

    private MockServerWebExchange preflight(String origin) {
        return MockServerWebExchange.from(MockServerHttpRequest.options("http://localhost:8080/users/profile")
                .header(HttpHeaders.ORIGIN, origin)
                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "GET")
                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, "authorization,x-user-id"));
    }
}
