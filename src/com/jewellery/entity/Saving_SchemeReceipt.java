package com.jewellery.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@NamedQueries({	@NamedQuery(name="schemereceiptList", query="from Saving_SchemeReceipt order by cardId") })

@Table( name="Saving_SchemeReceipt", uniqueConstraints = { @UniqueConstraint ( columnNames = {"receiptNO" } ) } )

public class Saving_SchemeReceipt implements Serializable {

	private static final long serialVersionUID = 1L;	
	private Integer receiptId;
	private String cardNo;
	private String customerName;
	private String schemeName;	
	private String schemeType;	
	private BigDecimal schemeInAmount = new BigDecimal("0.00");;	
	private BigDecimal schemeInGrams = new BigDecimal("0.000");;
	private Date receiptDate;
	private BigDecimal receiptAmount = new BigDecimal("0.00");	
	private String debitAccount;	
	private String narration;
	private String receiptNO;
	private String creditAccount;
	private BigDecimal paymentAmount = new BigDecimal("0.00");
	private BigDecimal paymentClosingGrams = new BigDecimal("0.000"); 
	private String formType;
	private Integer receiptInstallmentNO;

	public Saving_SchemeReceipt() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue	
	public Integer getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Integer receiptId) {
		this.receiptId = receiptId;
	}
		
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(String schemeType) {
		this.schemeType = schemeType;
	}

	public BigDecimal getSchemeInAmount() {
		return schemeInAmount;
	}

	public void setSchemeInAmount(BigDecimal schemeInAmount) {
		this.schemeInAmount = schemeInAmount;
	}

	public BigDecimal getSchemeInGrams() {
		return schemeInGrams;
	}

	public void setSchemeInGrams(BigDecimal schemeInGrams) {
		this.schemeInGrams = schemeInGrams;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public BigDecimal getReceiptAmount() {
		return receiptAmount;
	}

	public void setReceiptAmount(BigDecimal receiptAmount) {
		this.receiptAmount = receiptAmount;
	}

	public String getDebitAccount() {
		return debitAccount;
	}

	public void setDebitAccount(String debitAccount) {
		this.debitAccount = debitAccount;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getReceiptNO() {
		return receiptNO;
	}

	public void setReceiptNO(String receiptNO) {
		this.receiptNO = receiptNO;
	}

	public String getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(String creditAccount) {
		this.creditAccount = creditAccount;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public Integer getReceiptInstallmentNO() {
		return receiptInstallmentNO;
	}

	public void setReceiptInstallmentNO(Integer receiptInstallmentNO) {
		this.receiptInstallmentNO = receiptInstallmentNO;
	}

	public BigDecimal getPaymentClosingGrams() {
		return paymentClosingGrams;
	}

	public void setPaymentClosingGrams(BigDecimal paymentClosingGrams) {
		this.paymentClosingGrams = paymentClosingGrams;
	}	
	
	
}