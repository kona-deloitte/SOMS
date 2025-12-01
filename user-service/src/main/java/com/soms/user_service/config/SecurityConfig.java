//package com.soms.user_service.config; // change package per service
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableMethodSecurity(prePostEnabled = true)
//public class SecurityConfig {
//
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
//        var admin = User.builder()
//                .username("admin")
//                .password(encoder.encode("adminpass"))
//                .roles("ADMIN","USER")
//                .build();
//
//        var user = User.builder()
//                .username("user")
//                .password(encoder.encode("userpass"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        // allow actuator, swagger and health to be reachable (adjust paths as needed)
//                        .requestMatchers("/actuator/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
//                        // Public reads for products
//                        .requestMatchers("/products/**").permitAll()
//                        // Admin only endpoints (create/update/delete/addStock)
//                        .requestMatchers("/products/**/addStock","/products","/products/*").hasRole("ADMIN")
//                        // Order/payment endpoints require authenticated user
//                        .requestMatchers("/orders/**", "/payments/**").authenticated()
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }
//}
