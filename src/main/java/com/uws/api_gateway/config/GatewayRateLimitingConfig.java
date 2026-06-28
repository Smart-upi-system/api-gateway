package com.uws.api_gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

//rate-limit.default.capacity=100
//rate-limit.default.refill-tokens=10
//rate-limit.default.refill-duration=1s
//rate-limit.transaction.capacity=10
@Configuration
public class GatewayRateLimitingConfig {



    @Value("${rate-limit.default.capacity:100}")
    private int defaultCapacity;

    @Value("${rate-limit.default.refill-tokens:100}")
    private int defaultRefillTokens;

    @Value("${rate-limit.transaction.refill-tokens:1}")
    private int transactionRefillTokens;

    @Value("${rate-limit.transaction.capacity:10}")
    private int transactionCapacity;

    @Bean
    public KeyResolver userKeyResolver(@Value("${rate-limiting.user-key-header:X-User-Id}") String userKeyHeader) {
        return exchange -> {
            String userId = exchange.getRequest().getHeaders().getFirst(userKeyHeader);

            if (userId == null || userId.trim().isEmpty()) {
                // FAIL FAST: Reject right at the gateway before touching any microservice or Redis
                return Mono.error(new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Missing or invalid identification header"
                ));
            }

            // Safe to limit based on the strict, verified User identity
            return Mono.just(userId);
        };
    }

    /**
     * Default Rate Limiter Bean using your properties
     */
    @Primary
    @Bean
    public RedisRateLimiter defaultRateLimiter() {
        // RedisRateLimiter arguments: (replenishRate, burstCapacity)
        return new RedisRateLimiter(defaultRefillTokens, defaultCapacity);
    }

    /**
     * Specialized Rate Limiter Bean for Transactions
     */
    @Bean
    public RedisRateLimiter transactionRateLimiter() {
        return new RedisRateLimiter(transactionRefillTokens, transactionCapacity);
    }
}
