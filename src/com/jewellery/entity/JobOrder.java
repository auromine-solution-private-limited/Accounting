package com.jewellery.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity                                                                                                      
@NamedQueries({                                                                                              
	@NamedQuery(name = "getJobOrderByName", query = "from JobOrder where smithName = :smithName"),
	@NamedQuery(name = "getJobOrders", query = "from JobOrder where orderNo = ?"),
    @NamedQuery(name = "listJobOrder", query = "from JobOrder order by jobOrderId")                                          
})                                                                                                           
public class JobOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer jobOrderId;
	private Integer orderNo;
	private String fromItemCode;
	private String toItemCode;
	private Date orderDate;
	private Date deliveryDate;
	private String smithName;
	private String bullionType;
	private String categoryName;
	private String finishedItem;
	private String status;
	private BigDecimal size = new BigDecimal("0.00");
	private String stoneDetails;
	private BigDecimal stoneWeight = new BigDecimal("0.000");
	private BigDecimal stoneCost = new BigDecimal("0.00");
	private BigDecimal finisheditemGrossWt = new BigDecimal("0.000");
	private BigDecimal touch = new BigDecimal("0.00");
	private BigDecimal melting = new BigDecimal("0.00");
	private BigDecimal finisheditemNetWt = new BigDecimal("0.000");
	private BigDecimal wastage = new BigDecimal("0.000");
	private BigDecimal labourCharge = new BigDecimal("0.00");
	private BigDecimal totalExpense = new BigDecimal("0.00");
	private BigDecimal issuedGrossWeight = new BigDecimal("0.000");
	private BigDecimal issuedNetWeight = new BigDecimal("0.000");
	private int numberOfPieces;
	private String Description;

	@Id
	@GeneratedValue
	public Integer getJobOrderId() {
		return jobOrderId;
	}

	public void setJobOrderId(Integer jobOrderId) {
		this.jobOrderId = jobOrderId;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getFromItemCode() {
		return fromItemCode;
	}

	public void setFromItemCode(String fromItemCode) {
		this.fromItemCode = fromItemCode;
	}

	public String getToItemCode() {
		return toItemCode;
	}

	public void setToItemCode(String toItemCode) {
		this.toItemCode = toItemCode;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getSmithName() {
		return smithName;
	}

	public void setSmithName(String smithName) {
		this.smithName = smithName;
	}

	public String getBullionType() {
		return bullionType;
	}

	public void setBullionType(String bullionType) {
		this.bullionType = bullionType;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getFinishedItem() {
		return finishedItem;
	}

	public void setFinishedItem(String finishedItem) {
		this.finishedItem = finishedItem;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getSize() {
		return size;
	}

	public void setSize(BigDecimal size) {
		this.size = size;
	}

	public String getStoneDetails() {
		return stoneDetails;
	}

	public void setStoneDetails(String stoneDetails) {
		this.stoneDetails = stoneDetails;
	}

	public BigDecimal getStoneWeight() {
		return stoneWeight;
	}

	public void setStoneWeight(BigDecimal stoneWeight) {
		this.stoneWeight = stoneWeight;
	}

	public BigDecimal getStoneCost() {
		return stoneCost;
	}

	public void setStoneCost(BigDecimal stoneCost) {
		this.stoneCost = stoneCost;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getFinisheditemGrossWt() {
		return finisheditemGrossWt;
	}

	public void setFinisheditemGrossWt(BigDecimal finisheditemGrossWt) {
		this.finisheditemGrossWt = finisheditemGrossWt;
	}

	public BigDecimal getTouch() {
		return touch;
	}

	public void setTouch(BigDecimal touch) {
		this.touch = touch;
	}

	public BigDecimal getMelting() {
		return melting;
	}

	public void setMelting(BigDecimal melting) {
		this.melting = melting;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getFinisheditemNetWt() {
		return finisheditemNetWt;
	}

	public void setFinisheditemNetWt(BigDecimal finisheditemNetWt) {
		this.finisheditemNetWt = finisheditemNetWt;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getWastage() {
		return wastage;
	}

	public void setWastage(BigDecimal wastage) {
		this.wastage = wastage;
	}

	public BigDecimal getLabourCharge() {
		return labourCharge;
	}

	public void setLabourCharge(BigDecimal labourCharge) {
		this.labourCharge = labourCharge;
	}

	public BigDecimal getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(BigDecimal totalExpense) {
		this.totalExpense = totalExpense;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getIssuedGrossWeight() {
		return issuedGrossWeight;
	}

	public void setIssuedGrossWeight(BigDecimal issuedGrossWeight) {
		this.issuedGrossWeight = issuedGrossWeight;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getIssuedNetWeight() {
		return issuedNetWeight;
	}

	public void setIssuedNetWeight(BigDecimal issuedNetWeight) {
		this.issuedNetWeight = issuedNetWeight;
	}

	public int getNumberOfPieces() {
		return numberOfPieces;
	}

	public void setNumberOfPieces(int numberOfPieces) {
		this.numberOfPieces = numberOfPieces;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

}
