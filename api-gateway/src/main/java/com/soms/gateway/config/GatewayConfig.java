package com.soms.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class GatewayConfig {

    // ROUTES
    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-service", r -> r.path("/products/**").uri("lb://PRODUCT-SERVICE"))
                .route("order-service", r -> r.path("/orders/**").uri("lb://ORDER-SERVICE"))
                .route("payment-service", r -> r.path("/payments/**").uri("lb://PAYMENT-SERVICE"))
                .route("user-service", r -> r.path("/users/**").uri("lb://USER-SERVICE"))
                .build();
    }

    // CORS for WebFlux Gateway
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowCredentials(true);
        cors.setAllowedOriginPatterns(Arrays.asList("*")); // allow any frontend
        cors.setAllowedHeaders(Arrays.asList("*"));
        cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);

        return new CorsWebFilter(source);
    }
}
