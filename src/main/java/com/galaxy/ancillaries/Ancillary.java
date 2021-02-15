package com.galaxy.ancillaries;

import java.math.BigDecimal;
import java.util.Objects;

public class Ancillary {
    private String issuedDate;
    private BigDecimal bid;
    private String locale;
    private String contractEntity;
    private String collectingEntity;
    private String collectingCurrency;
    private BigDecimal transactionFeeCollectingCurrency;
    private BigDecimal transactionFeeContractingCurrency;
    private BigDecimal premiumDiscountCollectingCurrency;
    private BigDecimal premiumDiscountContractingCurrency;
    private BigDecimal couponCollectingCurrency;
    private BigDecimal couponContractingCurrency;
    private BigDecimal pointsCollectingCurrency;
    private BigDecimal pointsContractingCurrency;
    private BigDecimal uniqueCodeCollectingCurrency;
    private BigDecimal uniqueCodeContractingCurrency;
    private BigDecimal invoiceAmountCollectingCurrency;
    private BigDecimal invoiceAmountContractingCurrency;
    private BigDecimal rebookCostCollectingCurrency;
    private BigDecimal rebookCostContractingCurrency;
    private String contractCurrency;
    private BigDecimal commissionContractingCurrency;
    private BigDecimal totalFareNTAContractingCurrency;
    private BigDecimal commissionRevenue;
    private BigDecimal netMargin;

    public Ancillary() {
        this.setTransactionFeeCollectingCurrency(BigDecimal.ZERO);
        this.setPremiumDiscountCollectingCurrency(BigDecimal.ZERO);
        this.setCouponCollectingCurrency(BigDecimal.ZERO);
        this.setPointsCollectingCurrency(BigDecimal.ZERO);
        this.setUniqueCodeCollectingCurrency(BigDecimal.ZERO);
        this.setInvoiceAmountCollectingCurrency(BigDecimal.ZERO);
        this.setRebookCostCollectingCurrency(BigDecimal.ZERO);
        this.setTransactionFeeContractingCurrency(BigDecimal.ZERO);
        this.setPremiumDiscountContractingCurrency(BigDecimal.ZERO);
        this.setCouponContractingCurrency(BigDecimal.ZERO);
        this.setPointsContractingCurrency(BigDecimal.ZERO);
        this.setUniqueCodeContractingCurrency(BigDecimal.ZERO);
        this.setInvoiceAmountContractingCurrency(BigDecimal.ZERO);
        this.setRebookCostContractingCurrency(BigDecimal.ZERO);
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getContractEntity() {
        return contractEntity;
    }

    public void setContractEntity(String contractEntity) {
        this.contractEntity = contractEntity;
    }

    public String getCollectingEntity() {
        return collectingEntity;
    }

    public void setCollectingEntity(String collectingEntity) {
        this.collectingEntity = collectingEntity;
    }

    public String getCollectingCurrency() {
        return collectingCurrency;
    }

    public void setCollectingCurrency(String collectingCurrency) {
        this.collectingCurrency = collectingCurrency;
    }

    public BigDecimal getTransactionFeeCollectingCurrency() {
        return transactionFeeCollectingCurrency;
    }

    public void setTransactionFeeCollectingCurrency(BigDecimal transactionFeeCollectingCurrency) {
        this.transactionFeeCollectingCurrency = transactionFeeCollectingCurrency;
    }

    public BigDecimal getTransactionFeeContractingCurrency() {
        return transactionFeeContractingCurrency;
    }

    public void setTransactionFeeContractingCurrency(BigDecimal transactionFeeContractingCurrency) {
        this.transactionFeeContractingCurrency = transactionFeeContractingCurrency;
    }

    public BigDecimal getPremiumDiscountCollectingCurrency() {
        return premiumDiscountCollectingCurrency;
    }

    public void setPremiumDiscountCollectingCurrency(BigDecimal premiumDiscountCollectingCurrency) {
        this.premiumDiscountCollectingCurrency = premiumDiscountCollectingCurrency;
    }

    public BigDecimal getPremiumDiscountContractingCurrency() {
        return premiumDiscountContractingCurrency;
    }

    public void setPremiumDiscountContractingCurrency(BigDecimal premiumDiscountContractingCurrency) {
        this.premiumDiscountContractingCurrency = premiumDiscountContractingCurrency;
    }

    public BigDecimal getCouponCollectingCurrency() {
        return couponCollectingCurrency;
    }

    public void setCouponCollectingCurrency(BigDecimal couponCollectingCurrency) {
        this.couponCollectingCurrency = couponCollectingCurrency;
    }

    public BigDecimal getCouponContractingCurrency() {
        return couponContractingCurrency;
    }

    public void setCouponContractingCurrency(BigDecimal couponContractingCurrency) {
        this.couponContractingCurrency = couponContractingCurrency;
    }

    public BigDecimal getPointsCollectingCurrency() {
        return pointsCollectingCurrency;
    }

    public void setPointsCollectingCurrency(BigDecimal pointsCollectingCurrency) {
        this.pointsCollectingCurrency = pointsCollectingCurrency;
    }

    public BigDecimal getPointsContractingCurrency() {
        return pointsContractingCurrency;
    }

    public void setPointsContractingCurrency(BigDecimal pointsContractingCurrency) {
        this.pointsContractingCurrency = pointsContractingCurrency;
    }

    public BigDecimal getUniqueCodeCollectingCurrency() {
        return uniqueCodeCollectingCurrency;
    }

    public void setUniqueCodeCollectingCurrency(BigDecimal uniqueCodeCollectingCurrency) {
        this.uniqueCodeCollectingCurrency = uniqueCodeCollectingCurrency;
    }

    public BigDecimal getUniqueCodeContractingCurrency() {
        return uniqueCodeContractingCurrency;
    }

    public void setUniqueCodeContractingCurrency(BigDecimal uniqueCodeContractingCurrency) {
        this.uniqueCodeContractingCurrency = uniqueCodeContractingCurrency;
    }

    public BigDecimal getInvoiceAmountCollectingCurrency() {
        return invoiceAmountCollectingCurrency;
    }

    public void setInvoiceAmountCollectingCurrency(BigDecimal invoiceAmountCollectingCurrency) {
        this.invoiceAmountCollectingCurrency = invoiceAmountCollectingCurrency;
    }

    public BigDecimal getInvoiceAmountContractingCurrency() {
        return invoiceAmountContractingCurrency;
    }

    public void setInvoiceAmountContractingCurrency(BigDecimal invoiceAmountContractingCurrency) {
        this.invoiceAmountContractingCurrency = invoiceAmountContractingCurrency;
    }

    public BigDecimal getRebookCostCollectingCurrency() {
        return rebookCostCollectingCurrency;
    }

    public void setRebookCostCollectingCurrency(BigDecimal rebookCostCollectingCurrency) {
        this.rebookCostCollectingCurrency = rebookCostCollectingCurrency;
    }

    public BigDecimal getRebookCostContractingCurrency() {
        return rebookCostContractingCurrency;
    }

    public void setRebookCostContractingCurrency(BigDecimal rebookCostContractingCurrency) {
        this.rebookCostContractingCurrency = rebookCostContractingCurrency;
    }

    public String getContractCurrency() {
        return contractCurrency;
    }

    public void setContractCurrency(String contractCurrency) {
        this.contractCurrency = contractCurrency;
    }

    public BigDecimal getCommissionContractingCurrency() {
        return commissionContractingCurrency;
    }

    public void setCommissionContractingCurrency(BigDecimal commissionContractingCurrency) {
        this.commissionContractingCurrency = commissionContractingCurrency;
    }

    public BigDecimal getTotalFareNTAContractingCurrency() {
        return totalFareNTAContractingCurrency;
    }

    public void setTotalFareNTAContractingCurrency(BigDecimal totalFareNTAContractingCurrency) {
        this.totalFareNTAContractingCurrency = totalFareNTAContractingCurrency;
    }

    public BigDecimal getCommissionRevenue() {
        return commissionRevenue;
    }

    public void setCommissionRevenue(BigDecimal commissionRevenue) {
        this.commissionRevenue = commissionRevenue;
    }

    public BigDecimal getNetMargin() {
        return netMargin;
    }

    public void setNetMargin(BigDecimal netMargin) {
        this.netMargin = netMargin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ancillary ancillary = (Ancillary) o;
        return Objects.equals(issuedDate, ancillary.issuedDate) &&
                Objects.equals(bid, ancillary.bid) &&
                Objects.equals(locale, ancillary.locale) &&
                Objects.equals(contractEntity, ancillary.contractEntity) &&
                Objects.equals(collectingEntity, ancillary.collectingEntity) &&
                Objects.equals(collectingCurrency, ancillary.collectingCurrency) &&
                Objects.equals(transactionFeeCollectingCurrency, ancillary.transactionFeeCollectingCurrency) &&
                Objects.equals(transactionFeeContractingCurrency, ancillary.transactionFeeContractingCurrency) &&
                Objects.equals(premiumDiscountCollectingCurrency, ancillary.premiumDiscountCollectingCurrency) &&
                Objects.equals(premiumDiscountContractingCurrency, ancillary.premiumDiscountContractingCurrency) &&
                Objects.equals(couponCollectingCurrency, ancillary.couponCollectingCurrency) &&
                Objects.equals(couponContractingCurrency, ancillary.couponContractingCurrency) &&
                Objects.equals(pointsCollectingCurrency, ancillary.pointsCollectingCurrency) &&
                Objects.equals(pointsContractingCurrency, ancillary.pointsContractingCurrency) &&
                Objects.equals(uniqueCodeCollectingCurrency, ancillary.uniqueCodeCollectingCurrency) &&
                Objects.equals(uniqueCodeContractingCurrency, ancillary.uniqueCodeContractingCurrency) &&
                Objects.equals(invoiceAmountCollectingCurrency, ancillary.invoiceAmountCollectingCurrency) &&
                Objects.equals(invoiceAmountContractingCurrency, ancillary.invoiceAmountContractingCurrency) &&
                Objects.equals(rebookCostCollectingCurrency, ancillary.rebookCostCollectingCurrency) &&
                Objects.equals(rebookCostContractingCurrency, ancillary.rebookCostContractingCurrency) &&
                Objects.equals(contractCurrency, ancillary.contractCurrency) &&
                Objects.equals(commissionContractingCurrency, ancillary.commissionContractingCurrency) &&
                Objects.equals(totalFareNTAContractingCurrency, ancillary.totalFareNTAContractingCurrency) &&
                Objects.equals(commissionRevenue, ancillary.commissionRevenue) &&
                Objects.equals(netMargin, ancillary.netMargin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issuedDate, bid, locale, contractEntity, collectingEntity, collectingCurrency, transactionFeeCollectingCurrency, transactionFeeContractingCurrency, premiumDiscountCollectingCurrency, premiumDiscountContractingCurrency, couponCollectingCurrency, couponContractingCurrency, pointsCollectingCurrency, pointsContractingCurrency, uniqueCodeCollectingCurrency, uniqueCodeContractingCurrency, invoiceAmountCollectingCurrency, invoiceAmountContractingCurrency, rebookCostCollectingCurrency, rebookCostContractingCurrency, contractCurrency, commissionContractingCurrency, totalFareNTAContractingCurrency, commissionRevenue, netMargin);
    }
}
