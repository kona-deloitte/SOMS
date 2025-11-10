package com.soms.payment.service;

import com.soms.payment.dto.PaymentRequest;
import com.soms.payment.dto.PaymentResponse;
import com.soms.payment.exception.PaymentNotFoundException;
import com.soms.payment.model.Payment;
import com.soms.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository repo;

    public PaymentService(PaymentRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public PaymentResponse processPayment(PaymentRequest req) {
        Payment payment = new Payment();
        payment.setOrderId(req.orderId);
        payment.setPaymentMode(req.paymentMode);
        payment.setAmount(req.amount);

        // Simple mock payment validation logic
        if (req.amount <= 0) {
            payment.setStatus("FAILED");
        } else {
            payment.setStatus("SUCCESS");
        }

        repo.save(payment);
        return mapToResponse(payment);
    }

    public PaymentResponse getById(Long id) {
        Payment p = repo.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + id));
        return mapToResponse(p);
    }

    public List<PaymentResponse> getAll() {
        return repo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private PaymentResponse mapToResponse(Payment p) {
        PaymentResponse res = new PaymentResponse();
        res.id = p.getId();
        res.orderId = p.getOrderId();
        res.paymentMode = p.getPaymentMode();
        res.amount = p.getAmount();
        res.status = p.getStatus();
        res.createdAt = p.getCreatedAt();
        return res;
    }
}
