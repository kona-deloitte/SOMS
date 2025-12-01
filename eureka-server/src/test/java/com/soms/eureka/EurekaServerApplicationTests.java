package com.soms.eureka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EurekaServerApplicationTests {

    @Value("${server.port}")
    private int serverPort;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${eureka.client.register-with-eureka}")
    private boolean shouldRegister;

    @Value("${eureka.client.fetch-registry}")
    private boolean shouldFetchRegistry;

    @Value("${eureka.server.enable-self-preservation}")
    private boolean selfPreservation;

    @Test
    void contextLoads() {
        // Just ensures Spring context starts successfully
    }
    void validateEurekaProperties() {

        // Confirm server port is the correct Eureka/UI port
        assertThat(serverPort).isEqualTo(8761);

        // Confirm service name
        assertThat(appName).isEqualTo("eureka-server");

        // Eureka server should NOT register itself
        assertThat(shouldRegister).isFalse();

        // Eureka server should NOT fetch registry from others
        assertThat(shouldFetchRegistry).isFalse();

        // Self-preservation disabled (useful in dev)
        assertThat(selfPreservation).isFalse();
    }
}
