package com.soms.payment.enums;

public enum PaymentMode {
    UPI,
    CARD,
    PAY_LATER,
    CASH,
    UNKNOWN;

    public static PaymentMode fromString(String mode) {
        try {
            return PaymentMode.valueOf(mode.toUpperCase().replace(" ", "_"));
        } catch (Exception e) {
            return UNKNOWN;
        }
    }
}
