package com.jewellery.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class SalesItems {

	private Integer itemId;
	private String itemCode;
	private String itemName;
	private String categoryName;
	private String bullionType;
	private Integer noOfPieces;
	private BigDecimal grossWeight = new BigDecimal("0.000");
	private BigDecimal rate = new BigDecimal("0.00"); // Ornament or Bullion Rate
	private String stoneDesc;
	private BigDecimal stoneCost = new BigDecimal("0.00");
	private BigDecimal vaCharge = new BigDecimal("0.00");
	private BigDecimal wastageGms = new BigDecimal("0.000");
	private BigDecimal mcRupees = new BigDecimal("0.00");
	private BigDecimal vat = new BigDecimal("0.00");
	private BigDecimal amt = new BigDecimal("0.00");
	private String salesType;
	private String manufacturerSeal;
	private String subCategory;
	private BigDecimal less = new BigDecimal("0.00");

	private List<SalesBill> SalesBill = new ArrayList<SalesBill>();

	public SalesItems() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBullionType() {
		return bullionType;
	}

	public void setBullionType(String bullionType) {
		this.bullionType = bullionType;
	}

	public Integer getNoOfPieces() {
		return noOfPieces;
	}

	public void setNoOfPieces(Integer noOfPieces) {
		this.noOfPieces = noOfPieces;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getStoneDesc() {
		return stoneDesc;
	}

	public void setStoneDesc(String stoneDesc) {
		this.stoneDesc = stoneDesc;
	}

	public BigDecimal getStoneCost() {
		return stoneCost;
	}

	public void setStoneCost(BigDecimal stoneCost) {
		this.stoneCost = stoneCost;
	}

	public BigDecimal getVaCharge() {
		return vaCharge;
	}

	public void setVaCharge(BigDecimal vaCharge) {
		this.vaCharge = vaCharge;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getWastageGms() {
		return wastageGms;
	}

	public void setWastageGms(BigDecimal wastageGms) {
		this.wastageGms = wastageGms;
	}

	public BigDecimal getMcRupees() {
		return mcRupees;
	}

	public void setMcRupees(BigDecimal mcRupees) {
		this.mcRupees = mcRupees;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public String getSalesType() {
		return salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	public String getManufacturerSeal() {
		return manufacturerSeal;
	}

	public void setManufacturerSeal(String manufacturerSeal) {
		this.manufacturerSeal = manufacturerSeal;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public BigDecimal getLess() {
		return less;
	}

	public void setLess(BigDecimal less) {
		this.less = less;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "salesBill_salesItems", joinColumns = { @JoinColumn(name = "itemId") }, inverseJoinColumns = { @JoinColumn(name = "salesId") })
	public List<SalesBill> getSalesBill() {
		return SalesBill;
	}

	public void setSalesBill(List<SalesBill> salesBill) {
		SalesBill = salesBill;
	}

}
