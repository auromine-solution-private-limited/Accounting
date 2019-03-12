package com.jewellery.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity
//@Table(name="POSStock", catalog="jewellery")

@NamedQueries({
	@NamedQuery (name = "getPOSStockCode", query = "from POSStock WHERE transactionId = (SELECT MAX(transactionId) FROM POSStock WHERE transactionType='Opening Stock')")	
})
public class POSStock implements Serializable{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private Integer stockID;
	private Integer itemID;
	private String barcodeId;
	private String transactionId;
	private String transactionType;
	private String categoryName;
	private String itemName;
	private Integer qtySet;
	private Integer pps;
	private Integer totalPieces;
	public String deleted="";
	private BigDecimal costRate = new BigDecimal("0.00");
	private BigDecimal marginP = new BigDecimal("0.00");
	private BigDecimal salesRate= new BigDecimal("0.00");
	private BigDecimal mrp= new BigDecimal("0.00");
	private Date stockDate;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getStockID() {
		return stockID;
	}
	public void setStockID(Integer stockID) {
		this.stockID = stockID;
	}
	public Integer getItemID() {
		return itemID;
	}
	public void setItemID(Integer itemID) {
		this.itemID = itemID;
	}
	public String getBarcodeId() {
		return barcodeId;
	}
	public void setBarcodeId(String barcodeId) {
		this.barcodeId = barcodeId;
	}	
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Integer getQtySet() {
		return qtySet;
	}
	public void setQtySet(Integer qtySet) {
		this.qtySet = qtySet;
	}
	public Integer getPps() {
		return pps;
	}
	public void setPps(Integer pps) {
		this.pps = pps;
	}
	public Integer getTotalPieces() {
		return totalPieces;
	}
	public void setTotalPieces(Integer totalPieces) {
		this.totalPieces = totalPieces;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getCostRate() {
		return costRate;
	}
	public void setCostRate(BigDecimal costRate) {
		this.costRate = costRate;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getMarginP() {
		return marginP;
	}
	public void setMarginP(BigDecimal marginP) {
		this.marginP = marginP;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getSalesRate() {
		return salesRate;
	}
	public void setSalesRate(BigDecimal salesRate) {
		this.salesRate = salesRate;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getMrp() {
		return mrp;
	}
	public void setMrp(BigDecimal mrp) {
		this.mrp = mrp;
	}
	
	
	public Date getStockDate() {
		return stockDate;
	}
	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
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
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
		
	
}
