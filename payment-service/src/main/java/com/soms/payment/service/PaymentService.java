package com.soms.payment.service;

import com.soms.payment.dto.*;
import com.soms.payment.exception.PaymentNotFoundException;
import com.soms.payment.feign.UserClient;
import com.soms.payment.model.Payment;
import com.soms.payment.repository.PaymentRepository;
import com.soms.payment.enums.PaymentMode;
import com.soms.payment.enums.PaymentStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository repo;
    private final UserClient userClient;

    public PaymentService(PaymentRepository repo, UserClient userClient) {
        this.repo = repo;
        this.userClient = userClient;
    }

    @Transactional
    public PaymentResponse processPayment(PaymentRequest req) {

        PaymentMode mode = PaymentMode.fromString(req.paymentMode);
        PaymentStatus status;

        switch (mode) {
            case UPI, CARD, PAY_LATER -> status = PaymentStatus.SUCCESS;
            case CASH -> status = PaymentStatus.PENDING;
            default -> status = PaymentStatus.FAILURE;
        }

        Payment payment = new Payment();
        payment.setOrderId(req.orderId);
        payment.setUserId(req.userId);
        payment.setPaymentMode(mode.name());
        payment.setAmount(req.amount);
        payment.setStatus(status.name());

        repo.save(payment);
        return mapToResponse(payment);
    }

    public PaymentResponse getById(Long id) {
        Payment p = repo.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + id));

        UserResponse user = userClient.getUserById(p.getUserId());

        PaymentResponse res = mapToResponse(p);
        res.userName = user != null ? user.name : "Unknown User";
        return res;
    }

    public List<PaymentResponse> getAll() {
        return repo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PaymentResponse> getPaymentsByUserId(Long userId){
        return repo.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private PaymentResponse mapToResponse(Payment p) {
        PaymentResponse res = new PaymentResponse();
        res.id = p.getId();
        res.orderId = p.getOrderId();
        res.userId = p.getUserId();
        res.paymentMode = p.getPaymentMode();
        res.amount = p.getAmount();
        res.status = p.getStatus();
        res.createdAt = p.getCreatedAt();
        return res;
    }



}
