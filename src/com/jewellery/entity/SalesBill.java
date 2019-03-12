package com.jewellery.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class SalesBill {

	private Integer salesId;
	private Date salesDate;
	private String salesType;
	private String customerName;
	private String Name;
	private String Address;
	private BigDecimal subTotal = new BigDecimal("0.00");
	private BigDecimal totaltax = new BigDecimal("0.00"); // Sum of Vat
	private BigDecimal netAmount = new BigDecimal("0.00");
	private BigDecimal tradeDiscount = new BigDecimal("0.00");
	private BigDecimal billAmount = new BigDecimal("0.00");
	private BigDecimal roundOff = new BigDecimal("0.00");
	private String paymentMode;
	private BigDecimal amountPaid = new BigDecimal("0.00");
	private BigDecimal adjustmentAmt = new BigDecimal("0.00");
	private String salesmanName;
	private Integer salesOrderNo;
	private BigDecimal balanceAmt = new BigDecimal("0.00"); // Balance to pay
	private String advanceAmt; // Advance amount
	private String greetings;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getSalesId() {
		return salesId;
	}

	public void setSalesId(Integer salesId) {
		this.salesId = salesId;
	}

	public Date getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

	public String getSalesType() {
		return salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getTotaltax() {
		return totaltax;
	}

	public void setTotaltax(BigDecimal totaltax) {
		this.totaltax = totaltax;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public BigDecimal getTradeDiscount() {
		return tradeDiscount;
	}

	public void setTradeDiscount(BigDecimal tradeDiscount) {
		this.tradeDiscount = tradeDiscount;
	}

	public BigDecimal getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}

	public BigDecimal getRoundOff() {
		return roundOff;
	}

	public void setRoundOff(BigDecimal roundOff) {
		this.roundOff = roundOff;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}

	public BigDecimal getAdjustmentAmt() {
		return adjustmentAmt;
	}

	public void setAdjustmentAmt(BigDecimal adjustmentAmt) {
		this.adjustmentAmt = adjustmentAmt;
	}

	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}

	public Integer getSalesOrderNo() {
		return salesOrderNo;
	}

	public void setSalesOrderNo(Integer salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}

	public BigDecimal getBalanceAmt() {
		return balanceAmt;
	}

	public void setBalanceAmt(BigDecimal balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	public String getAdvanceAmt() {
		return advanceAmt;
	}

	public void setAdvanceAmt(String advanceAmt) {
		this.advanceAmt = advanceAmt;
	}

	public String getGreetings() {
		return greetings;
	}

	public void setGreetings(String greetings) {
		this.greetings = greetings;
	}

}
