package com.jewellery.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;


@Entity
public class Payment {
	
	private Integer paymentId;
	private String paymentType;
	private Integer salesBillNo;
	private String cardNo;
	private String passesNo;
	private BigDecimal amount = new BigDecimal("0.00");
	
	//private List<SalesBill> listSalesBill = new ArrayList();
	
	public Payment(){
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getPaymentId() {
		return paymentId;
	}
	
	
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	public String getPaymentType() {
		return paymentType;
	}
	
	@Column(nullable = false)
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public Integer getSalesBillNo() {
		return salesBillNo;
	}
	
	@Column(nullable = false)
	public void setSalesBillNo(Integer salesBillNo) {
		this.salesBillNo = salesBillNo;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getPassesNo() {
		return passesNo;
	}
	public void setPassesNo(String passesNo) {
		this.passesNo = passesNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
		
	/*@ManyToMany
	@JoinTable (name = "salesBill_payment", 
		joinColumns = { @JoinColumn(name = "paymentId")},
		inverseJoinColumns={@JoinColumn(name="salesId")}   
	)
	
	public List<SalesBill> getSales(){
		return this.listSalesBill;
	}
	
	public void setSales(List<SalesBill> sales){
		 this.listSalesBill = sales;
	}*/
	
}
