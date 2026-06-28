//package com.uws.api_gateway.config;
//
//import com.uws.api_gateway.filter.LoggingFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
//import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
//import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import reactor.core.publisher.Mono;
//
//@Configuration
//@RequiredArgsConstructor
//public class GatewayConfig {
//
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final CorrelationIdFilter correlationFilter;
//    private final LoggingFilter loggingFilter;
//
//
//    @Bean
//    public KeyResolver keyResolver(){
//        return exchange -> {
//            String userId=exchange.getRequest().getHeaders().getFirst("X-User-Id");
//            return Mono.just(userId!=null ? userId: "anonymous");
//        };
//    }
//
//    @Bean
//    public RedisRateLimiter defaultRateLimiter(){
//        return new RedisRateLimiter(100,200,1);
//    }
//
//    @Bean
//    @Primary
//    public RedisRateLimiter transactionRateLimiter() {
//        return new RedisRateLimiter(10, 10, 1);
//    }
//
//
//}
