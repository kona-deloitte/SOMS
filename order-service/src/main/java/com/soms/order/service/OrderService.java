package com.soms.order.service;

import com.soms.order.dto.*;
import com.soms.order.exception.OrderNotFoundException;
import com.soms.order.feign.PaymentClient;
import com.soms.order.feign.ProductClient;
import com.soms.order.model.Order;
import com.soms.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repo;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;

    public OrderService(OrderRepository repo, ProductClient productClient, PaymentClient paymentClient) {
        this.repo = repo;
        this.productClient = productClient;
        this.paymentClient = paymentClient;
    }

    @Transactional
    public OrderResponse placeOrder(OrderRequest req) {
        ProductResponse product = productClient.getProductById(req.productId);
        if (product == null) throw new OrderNotFoundException("Product not found!");

        double total = product.price * req.quantity;

        Order order = new Order();
        order.setProductId(product.id);
        order.setQuantity(req.quantity);
        order.setTotalAmount(total);

        repo.save(order);

        PaymentRequest payment = new PaymentRequest();
        payment.orderId = order.getId();
        payment.amount = total;
        payment.paymentMode = req.paymentMode;

        paymentClient.processPayment(payment);

        order.setPaymentStatus("SUCCESS");
        repo.save(order);

        return mapToResponse(order);
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

    private OrderResponse mapToResponse(Order o) {
        OrderResponse res = new OrderResponse();
        res.id = o.getId();
        res.productId = o.getProductId();
        res.quantity = o.getQuantity();
        res.totalAmount = o.getTotalAmount();
        res.paymentStatus = o.getPaymentStatus();
        res.createdAt = o.getCreatedAt();
        return res;
    }
}
