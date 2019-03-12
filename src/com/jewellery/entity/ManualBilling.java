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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table( name="ManualBilling",
uniqueConstraints = { @UniqueConstraint ( columnNames = {"mBillAutoNO" } ) } )
public class ManualBilling implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer mBillId;
	private String mBillNo;
	private String mBillAutoNO;
	private String customerName;
	private String customerNameTinNo;
	private String address;
	private String salesOrderNo;
	private String savingsSchemeNO;
	private String Notes;
	private BigDecimal boardRate = new BigDecimal("0.00");
	private BigDecimal subTotal = new BigDecimal("0.00");
	private BigDecimal vatAmount = new BigDecimal("0.00");
	private BigDecimal totalAmount = new BigDecimal("0.00");
	private BigDecimal advanceAmount = new BigDecimal("0.00");
	private BigDecimal savingAmount = new BigDecimal("0.00");
	private BigDecimal bonus = new BigDecimal("0.00");
	private BigDecimal totalAmtReceived = new BigDecimal("0.00");
	private BigDecimal cashAmount = new BigDecimal("0.00");
	private BigDecimal cardAmount = new BigDecimal("0.00");
	private BigDecimal chequeAmount = new BigDecimal("0.00");
	private BigDecimal voucherAmount = new BigDecimal("0.00");
	private Date mBDate;
	private String mBillFormType;
	
	private Set<ManBillRowList> manBill = new HashSet<ManBillRowList>(0);
	
	@Id
	@GeneratedValue
	public Integer getmBillId() {
		return mBillId;
	}
	public void setmBillId(Integer mBillId) {
		this.mBillId = mBillId;
	}
	public String getmBillAutoNO() {
		return mBillAutoNO;
	}
	public void setmBillAutoNO(String mBillAutoNO) {
		this.mBillAutoNO = mBillAutoNO;
	}
	public String getmBillNo() {
		return mBillNo;
	}
	public void setmBillNo(String mBillNo) {
		this.mBillNo = mBillNo;
	}
	public Date getmBDate() {
		return mBDate;
	}
	public void setmBDate(Date mBDate) {
		this.mBDate = mBDate;
	}
	public BigDecimal getBoardRate() {
		return boardRate;
	}
	public void setBoardRate(BigDecimal boardRate) {
		this.boardRate = boardRate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerNameTinNo() {
		return customerNameTinNo;
	}
	public void setCustomerNameTinNo(String customerNameTinNo) {
		this.customerNameTinNo = customerNameTinNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public BigDecimal getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}
	public BigDecimal getVatAmount() {
		return vatAmount;
	}
	public void setVatAmount(BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getSalesOrderNo() {
		return salesOrderNo;
	}
	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}
	public BigDecimal getAdvanceAmount() {
		return advanceAmount;
	}
	public void setAdvanceAmount(BigDecimal advanceAmount) {
		this.advanceAmount = advanceAmount;
	}
	public String getSavingsSchemeNO() {
		return savingsSchemeNO;
	}
	public void setSavingsSchemeNO(String savingsSchemeNO) {
		this.savingsSchemeNO = savingsSchemeNO;
	}
	public BigDecimal getSavingAmount() {
		return savingAmount;
	}
	public void setSavingAmount(BigDecimal savingAmount) {
		this.savingAmount = savingAmount;
	}
	public BigDecimal getBonus() {
		return bonus;
	}
	public void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
	}
	public BigDecimal getTotalAmtReceived() {
		return totalAmtReceived;
	}
	public void setTotalAmtReceived(BigDecimal totalAmtReceived) {
		this.totalAmtReceived = totalAmtReceived;
	}
	public BigDecimal getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}
	public BigDecimal getCardAmount() {
		return cardAmount;
	}
	public void setCardAmount(BigDecimal cardAmount) {
		this.cardAmount = cardAmount;
	}
	public BigDecimal getChequeAmount() {
		return chequeAmount;
	}
	public void setChequeAmount(BigDecimal chequeAmount) {
		this.chequeAmount = chequeAmount;
	}
	public BigDecimal getVoucherAmount() {
		return voucherAmount;
	}
	public void setVoucherAmount(BigDecimal voucherAmount) {
		this.voucherAmount = voucherAmount;
	}
	public String getNotes() {
		return Notes;
	}
	public void setNotes(String notes) {
		Notes = notes;
	}
	public String getmBillFormType() {
		return mBillFormType;
	}
	public void setmBillFormType(String mBillFormType) {
		this.mBillFormType = mBillFormType;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "manualBilling")
	public Set<ManBillRowList> getManBill() {
		return manBill;
	}
	public void setManBill(Set<ManBillRowList> manBill) {
		this.manBill = manBill;
	}
}
