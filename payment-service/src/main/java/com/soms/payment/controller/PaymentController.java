package com.soms.payment.controller;

import com.soms.payment.dto.PaymentRequest;
import com.soms.payment.dto.PaymentResponse;
import com.soms.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService svc;

    public PaymentController(PaymentService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest req) {
        return ResponseEntity.ok(svc.processPayment(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(svc.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        return ResponseEntity.ok(svc.getAll());
    }

    @GetMapping({"/byUser/{userId}"})
    public ResponseEntity<List<PaymentResponse>> getPaymentsByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(svc.getPaymentsByUserId(userId));
    }
}
