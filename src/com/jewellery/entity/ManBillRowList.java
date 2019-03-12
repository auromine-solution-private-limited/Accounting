package com.jewellery.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ManBillRowList implements Serializable{
	

	private Integer manBillRowListId;
	private int qty;
	private String description;
	private BigDecimal grossWeight = new BigDecimal("0.00");
	private BigDecimal netWeight = new BigDecimal("0.00");
	private BigDecimal amount = new BigDecimal("0.00");
	private ManualBilling manualBilling;
	public boolean isDeleted = false;	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	public Integer getManBillRowListId() {
		return manBillRowListId;
	}
	public void setManBillRowListId(Integer manBillRowListId) {
		this.manBillRowListId = manBillRowListId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public BigDecimal getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}
	public BigDecimal getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mBillId", nullable = false)
	public ManualBilling getManualBilling() {
		return manualBilling;
	}
	public void setManualBilling(ManualBilling manualBilling) {
		this.manualBilling = manualBilling;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
