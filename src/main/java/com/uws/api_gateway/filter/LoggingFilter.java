package com.uws.api_gateway.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter  implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime=System.currentTimeMillis();
        String path=exchange.getRequest().getPath().value();
        String method=exchange.getRequest().getMethod().name();
        String correlationId=exchange.getRequest().getHeaders().getFirst("X-Correlation-Id");

        log.info("Incoming request: {} {} [correlationId: {}]", method, path, correlationId);

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            long duration = System.currentTimeMillis() - startTime;
            int statusCode = exchange.getResponse().getStatusCode() != null
                    ? exchange.getResponse().getStatusCode().value()
                    : 0;

            log.info("Outgoing response: {} {} - Status: {} - Duration: {}ms [correlationId: {}]",
                    method, path, statusCode, duration, correlationId);
        }));
    }
}
