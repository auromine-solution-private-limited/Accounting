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
	@NamedQuery(name = "getJewelFixByName", query = "from JewelFix where customerName = :customerName"),	
    @NamedQuery(name = "listJewelFix", query = "from JewelFix order by repairId")
})                                                                                                           
public class JewelFix implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orderNO;
	private String customerName;
	private String smithName;
	private String metalType;
	private String jewelName;
	private Date issueDate;
	private Date receivedDate;
	private String status;
	private String newitemName;
	private BigDecimal issuedGrossWeight = new BigDecimal("0.000");
	private int numberOfPieces;
	private BigDecimal noOfstone = new BigDecimal("0.00");
	private BigDecimal newStoneApplied = new BigDecimal("0.00");
	private BigDecimal grossWtAfterFixing = new BigDecimal("0.000");
	private BigDecimal grossWtAdded = new BigDecimal("0.00");
	private BigDecimal balanceWt = new BigDecimal("0.000");
	private BigDecimal stoneCost = new BigDecimal("0.00");
	private BigDecimal rate = new BigDecimal("0.00"); 
	private BigDecimal wastage = new BigDecimal("0.000");
	private BigDecimal metalCost = new BigDecimal("0.00");
	private BigDecimal serviceCharge = new BigDecimal("0.00");
	private BigDecimal polishCharge = new BigDecimal("0.00");
	private BigDecimal totalCost = new BigDecimal("0.00");
	private BigDecimal smithCost = new BigDecimal("0.00");
	private String description;
	private String mode;

	@Id
	@GeneratedValue
	private Integer repairId;

	public Integer getRepairId() {
		return repairId;
	}

	public void setRepairId(Integer repairId) {
		this.repairId = repairId;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSmithName() {
		return smithName;
	}

	public void setSmithName(String smithName) {
		this.smithName = smithName;
	}

	public String getMetalType() {
		return metalType;
	}

	public void setMetalType(String metalType) {
		this.metalType = metalType;
	}

	public String getJewelName() {
		return jewelName;
	}

	public void setJewelName(String jewelName) {
		this.jewelName = jewelName;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getNewitemName() {
		return newitemName;
	}

	public void setNewitemName(String newitemName) {
		this.newitemName = newitemName;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getIssuedGrossWeight() {
		return issuedGrossWeight;
	}

	public void setIssuedGrossWeight(BigDecimal issuedGrossWeight) {
		this.issuedGrossWeight = issuedGrossWeight;
	}

	public int getNumberOfPieces() {
		return numberOfPieces;
	}

	public void setNumberOfPieces(int numberOfPieces) {
		this.numberOfPieces = numberOfPieces;
	}

	public BigDecimal getNoOfstone() {
		return noOfstone;
	}

	public void setNoOfstone(BigDecimal noOfstone) {
		this.noOfstone = noOfstone;
	}

	public BigDecimal getNewStoneApplied() {
		return newStoneApplied;
	}

	public void setNewStoneApplied(BigDecimal newStoneApplied) {
		this.newStoneApplied = newStoneApplied;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getGrossWtAfterFixing() {
		return grossWtAfterFixing;
	}

	public void setGrossWtAfterFixing(BigDecimal grossWtAfterFixing) {
		this.grossWtAfterFixing = grossWtAfterFixing;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getGrossWtAdded() {
		return grossWtAdded;
	}

	public void setGrossWtAdded(BigDecimal grossWtAdded) {
		this.grossWtAdded = grossWtAdded;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getBalanceWt() {
		return balanceWt;
	}

	public void setBalanceWt(BigDecimal balanceWt) {
		this.balanceWt = balanceWt;
	}

	public BigDecimal getStoneCost() {
		return stoneCost;
	}

	public void setStoneCost(BigDecimal stoneCost) {
		this.stoneCost = stoneCost;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getWastage() {
		return wastage;
	}

	public void setWastage(BigDecimal wastage) {
		this.wastage = wastage;
	}

	public BigDecimal getMetalCost() {
		return metalCost;
	}

	public void setMetalCost(BigDecimal metalCost) {
		this.metalCost = metalCost;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public BigDecimal getPolishCharge() {
		return polishCharge;
	}

	public void setPolishCharge(BigDecimal polishCharge) {
		this.polishCharge = polishCharge;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public BigDecimal getSmithCost() {
		return smithCost;
	}

	public void setSmithCost(BigDecimal smithCost) {
		this.smithCost = smithCost;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	

}
