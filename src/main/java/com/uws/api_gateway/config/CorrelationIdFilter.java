package com.uws.api_gateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class CorrelationIdFilter  implements GatewayFilter {

    private static final String CORRELATION_ID_HEADER= "X-Correlation-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request=exchange.getRequest();
        String correlationId = request.getHeaders().getFirst(CORRELATION_ID_HEADER);
        if(correlationId==null || correlationId.isEmpty()){
            correlationId= UUID.randomUUID().toString();
            log.debug("Generated new correlation ID: {}", correlationId);
        }
        ServerHttpRequest newRequest= request.mutate()
                .header(CORRELATION_ID_HEADER,correlationId)
                .build();
        exchange.getResponse().getHeaders().add(CORRELATION_ID_HEADER,correlationId);
        log.debug("Processing request with correlation ID: {}, path: {}",
                correlationId, request.getPath());
        return chain.filter(exchange.mutate().request(newRequest).build());
    }
}
