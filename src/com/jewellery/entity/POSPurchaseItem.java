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
public class POSPurchaseItem implements Serializable{

	private static final long serialVersionUID = 1L;
		
	private Integer purchaseItemID;
	private String transactionNo;
	private String categoryName;
	private String itemName;
	private String barcode;
	private int qtySet;
	private int piecesPerSet;
	public boolean isDeleted = false;	
	private String itemStatus = "Purchased";
	private BigDecimal costRate=new BigDecimal("0.00");
	private BigDecimal marginPercentage=new BigDecimal("0.00");
	private BigDecimal salesRate=new BigDecimal("0.00");
	private BigDecimal discPer = new BigDecimal("0.00");
	private BigDecimal purchaseAmt=new BigDecimal("0.00");
	private BigDecimal taxPercantage=new BigDecimal("0.00");
	private String rowStatus = ""; // added to make readonly of categoryname and item name
	
	private POSPurchase pospurchase;
	
	@Id
	@GeneratedValue
	public Integer getPurchaseItemID() {
		return purchaseItemID;
	}
	public void setPurchaseItemID(Integer purchaseItemID) {
		this.purchaseItemID = purchaseItemID;
	}
	
	public String getTransactionNo() {
		return transactionNo;
	}
	
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchaseId", nullable = false)
	public POSPurchase getPospurchase() {
		return pospurchase;
	}
	public void setPospurchase(POSPurchase pospurchase) {
		this.pospurchase = pospurchase;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getBarcode() {
		return barcode;
	}
	
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	public int getQtySet() {
		return qtySet;
	}
	public void setQtySet(int qtySet) {
		this.qtySet = qtySet;
	}
	public BigDecimal getCostRate() {
		return costRate;
	}
	public void setCostRate(BigDecimal costRate) {
		this.costRate = costRate;
	}
	public BigDecimal getMarginPercentage() {
		return marginPercentage;
	}
	public void setMarginPercentage(BigDecimal marginPercentage) {
		this.marginPercentage = marginPercentage;
	}
	public BigDecimal getSalesRate() {
		return salesRate;
	}
	public void setSalesRate(BigDecimal salesRate) {
		this.salesRate = salesRate;
	}
	public BigDecimal getPurchaseAmt() {
		return purchaseAmt;
	}
	public void setPurchaseAmt(BigDecimal purchaseAmt) {
		this.purchaseAmt = purchaseAmt;
	}
	public BigDecimal getTaxPercantage() {
		return taxPercantage;
	}
	public void setTaxPercantage(BigDecimal taxPercantage) {
		this.taxPercantage = taxPercantage;
	}
	public int getPiecesPerSet() {
		return piecesPerSet;
	}
	public void setPiecesPerSet(int piecesPerSet) {
		this.piecesPerSet = piecesPerSet;
	}
	public BigDecimal getDiscPer() {
		return discPer;
	}
	public void setDiscPer(BigDecimal discPer) {
		this.discPer = discPer;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}
	public String getRowStatus() {
		return rowStatus;
	}
	public void setRowStatus(String rowStatus) {
		this.rowStatus = rowStatus;
	}
	
}
