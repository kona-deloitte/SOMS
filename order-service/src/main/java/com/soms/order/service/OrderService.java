package com.soms.order.service;

import com.soms.order.dto.*;
import com.soms.order.exception.OrderNotFoundException;
import com.soms.order.feign.PaymentClient;
import com.soms.order.feign.ProductClient;
import com.soms.order.feign.UserClient;
import com.soms.order.model.Order;
import com.soms.order.repository.OrderRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class OrderService {

    private final OrderRepository repo;
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final UserClient userClient;

    public OrderService(OrderRepository repo, ProductClient productClient, PaymentClient paymentClient, UserClient userClient) {
        this.repo = repo;
        this.productClient = productClient;
        this.paymentClient = paymentClient;
        this.userClient = userClient;
    }

    @Transactional
    @CircuitBreaker(name = "orderServiceCB", fallbackMethod = "orderFallback")
    @Retry(name = "orderServiceRetry")
    public OrderResponse placeOrder(OrderRequest req) {
        log.info("Placing new order for user ID: {}", req.userId);

        ProductResponse product = productClient.getProductById(req.productId);
        if (product == null) throw new OrderNotFoundException("Product not found!");

        UserResponse user = userClient.getUserById(req.userId);

        double baseTotal = product.price * req.quantity;

        int discountPercent = new Random().nextInt(10) + 1;
        double discountAmount = (baseTotal * discountPercent) / 100;
        double finalTotal = baseTotal - discountAmount;

        log.info("Discount of {}% applied for user '{}'. Final amount: {}",
                discountPercent, user != null ? user.name : "Unknown", finalTotal);

        Order order = new Order();
        order.setUserId(req.userId);
        order.setProductId(product.id);
        order.setQuantity(req.quantity);
        order.setTotalAmount(finalTotal);
        repo.save(order);

        PaymentRequest payment = new PaymentRequest();
        payment.orderId = order.getId();
        payment.userId = req.userId;
        payment.amount = finalTotal;
        payment.paymentMode = req.paymentMode;

        paymentClient.processPayment(payment);

        productClient.reduceProductQuantity(product.id, req.quantity);

        order.setPaymentStatus("SUCCESS");
        repo.save(order);

        OrderResponse res = mapToResponse(order);
        res.discountApplied = discountPercent + "%";
        res.userName = user != null ? user.name : "Guest";
        res.email = user != null ? user.email : "N/A";
        res.phone = user != null ? user.phone : "N/A";

        return res;
    }

    public OrderResponse orderFallback(OrderRequest req, Throwable ex) {
        log.error("Circuit Breaker Fallback triggered for user {}: {}",
                req != null ? req.userId : "unknown", ex.getMessage());

        OrderResponse fallback = new OrderResponse();
        fallback.paymentStatus = "FAILED";
        fallback.totalAmount = 0.0;
        fallback.message = "Order Service temporarily unavailable. Please try again later.";

        return fallback;
    }


    public List<OrderResponse> getAll() {
        return repo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public OrderResponse getOrderById(Long id) {
        Order o = repo.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
        return mapToResponse(o);
    }

    public List<OrderResponse> getOrdersByUser(Long userId) {
        var user = userClient.getUserById(userId);

        return repo.findByUserId(userId)
                .stream()
                .map(order -> {
                    OrderResponse res = mapToResponse(order);
                    if (user != null) {
                        res.userName = user.name;
                        res.email = user.email;
                        res.phone = user.phone;
                    }
                    return res;
                })
                .toList();
    }

    private OrderResponse mapToResponse(Order o) {
        OrderResponse res = new OrderResponse();
        res.id = o.getId();
        res.userId = o.getUserId();
        res.productId = o.getProductId();
        res.quantity = o.getQuantity();
        res.totalAmount = o.getTotalAmount();
        res.paymentStatus = o.getPaymentStatus();
        res.createdAt = o.getCreatedAt();
        return res;
    }
}
