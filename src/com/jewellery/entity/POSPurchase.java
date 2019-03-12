package com.jewellery.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class POSPurchase implements Serializable{
	
/****/
private static final long serialVersionUID = 1L;
private Integer purchaseId;
private String purchaseType;
private String invoiceNO;
private String supplierName;
private String walkinName;
private String walkinAddress;
private Date pdate;
private BigDecimal subTotal = new BigDecimal("0.00");
private BigDecimal fixedPer = new BigDecimal("0.00");
private BigDecimal totalDiscount = new BigDecimal("0.00");
private BigDecimal totalTax = new BigDecimal("0.00");
private BigDecimal totalValue = new BigDecimal("0.00");
private BigDecimal roundOff = new BigDecimal("0.00");
private BigDecimal grandAmount = new BigDecimal("0.00");
private String paymentType;
private String reason;
private Set<POSPurchaseItem> orderses = new HashSet<POSPurchaseItem>(0);

@Id
@GeneratedValue
public Integer getPurchaseId() {
	return purchaseId;
}

public void setPurchaseId(Integer purchaseId) {
	this.purchaseId = purchaseId;
}
public String getPurchaseType() {
	return purchaseType;
}
public void setPurchaseType(String purchaseType) {
	this.purchaseType = purchaseType;
}
public String getInvoiceNO() {
	return invoiceNO;
}
public void setInvoiceNO(String invoiceNO) {
	this.invoiceNO = invoiceNO;
}
public String getSupplierName() {
	return supplierName;
}
public void setSupplierName(String supplierName) {
	this.supplierName = supplierName;
}
public String getWalkinName() {
	return walkinName;
}
public void setWalkinName(String walkinName) {
	this.walkinName = walkinName;
}
public String getWalkinAddress() {
	return walkinAddress;
}
public void setWalkinAddress(String walkinAddress) {
	this.walkinAddress = walkinAddress;
}
public Date getPdate() {
	return pdate;
}
public void setPdate(Date pdate) {
	this.pdate = pdate;
}
public BigDecimal getSubTotal() {
	return subTotal;
}
public void setSubTotal(BigDecimal subTotal) {
	this.subTotal = subTotal;
}
public BigDecimal getTotalTax() {
	return totalTax;
}
public void setTotalTax(BigDecimal totalTax) {
	this.totalTax = totalTax;
}
public BigDecimal getTotalValue() {
	return totalValue;
}

public void setTotalValue(BigDecimal totalValue) {
	this.totalValue = totalValue;
}

public BigDecimal getRoundOff() {
	return roundOff;
}
public void setRoundOff(BigDecimal roundOff) {
	this.roundOff = roundOff;
}
public BigDecimal getGrandAmount() {
	return grandAmount;
}
public void setGrandAmount(BigDecimal grandAmount) {
	this.grandAmount = grandAmount;
}

public String getPaymentType() {
	return paymentType;
}

public void setPaymentType(String paymentType) {
	this.paymentType = paymentType;
}

public String getReason() {
	return reason;
}
public void setReason(String reason) {
	this.reason = reason;
}

public BigDecimal getTotalDiscount() {
	return totalDiscount;
}

public void setTotalDiscount(BigDecimal totalDiscount) {
	this.totalDiscount = totalDiscount;
}

public BigDecimal getFixedPer() {
	return fixedPer;
}

public void setFixedPer(BigDecimal fixedPer) {
	this.fixedPer = fixedPer;
}

@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pospurchase")
public Set<POSPurchaseItem> getOrderses() {
	return this.orderses;
}

public void setOrderses(Set<POSPurchaseItem> orderses) {
	this.orderses = orderses;
}

}
