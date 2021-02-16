package com.galaxy.flight;

public enum RevenueComponent {
    TRANSACTION_FEE("Transaction Fee"),
    PREMIUM("Premium"),
    DISCOUNT("Discount"),
    COUPON("Coupon"),
    REDEEMED_POINTS("Redeemed Points"),
    UNIQUE_CODE("Unique Code"),
    REBOOK_COST("Rebook Cost"),
    COMMISSION_REVENUE("Commission Revenue - Contract Currency");

    RevenueComponent(String name) {
        this.name = name;
    }

    private final String name;

    public String getName() {
        return name;
    }
}
