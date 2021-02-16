package com.galaxy.flight;

import java.math.BigDecimal;
import java.util.Objects;

public class FlightGroup {
    private String period;
    private String product;
    private RevenueComponent revenueComponent;
    private String contractEntity;
    private String trxCurrency;
    private String marginTag; //POSITIVE / NEGATIVE
    private String status; // ISSUED
    private BigDecimal amount;

    public String hash(){
        return new StringBuffer(period).append("-")
                .append(product).append("-")
                .append(revenueComponent.getName()).append("-")
                .append(contractEntity).append("-")
                .append(trxCurrency).append("-")
                .append(marginTag).append("-")
                .append(status).toString();
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public RevenueComponent getRevenueComponent() {
        return revenueComponent;
    }

    public void setRevenueComponent(RevenueComponent revenueComponent) {
        this.revenueComponent = revenueComponent;
    }

    public String getContractEntity() {
        return contractEntity;
    }

    public void setContractEntity(String contractEntity) {
        this.contractEntity = contractEntity;
    }

    public String getTrxCurrency() {
        return trxCurrency;
    }

    public void setTrxCurrency(String trxCurrency) {
        this.trxCurrency = trxCurrency;
    }

    public String getMarginTag() {
        return marginTag;
    }

    public void setMarginTag(String marginTag) {
        this.marginTag = marginTag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightGroup that = (FlightGroup) o;
        return Objects.equals(period, that.period) &&
                Objects.equals(product, that.product) &&
                revenueComponent == that.revenueComponent &&
                Objects.equals(contractEntity, that.contractEntity) &&
                Objects.equals(trxCurrency, that.trxCurrency) &&
                Objects.equals(marginTag, that.marginTag) &&
                Objects.equals(status, that.status) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(period, product, revenueComponent, contractEntity, trxCurrency, marginTag, status, amount);
    }
}
