package com.jewellery.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class RefineryIssue implements Serializable{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Integer refineryId;	
private String refineryIssueNo;
private String refineryIssueType="Refinery Issue";
private Date refineryDate;
private String refinerySupplierName;
private String ornamentsType;
private String itemCode;
private Integer purity=new Integer("0");
private Integer pieces=new Integer("0");
private BigDecimal grossWeight=new BigDecimal("0.000");
private BigDecimal netWeight=new BigDecimal("0.000");
private String narration;

@Id
@GeneratedValue
public Integer getRefineryId() {
	return refineryId;
}
public void setRefineryId(Integer refineryId) {
	this.refineryId = refineryId;
}
public String getRefineryIssueNo() {
	return refineryIssueNo;
}
public void setRefineryIssueNo(String refineryIssueNo) {
	this.refineryIssueNo = refineryIssueNo;
}
public Date getRefineryDate() {
	return refineryDate;
}
public void setRefineryDate(Date refineryDate) {
	this.refineryDate = refineryDate;
}
public String getRefinerySupplierName() {
	return refinerySupplierName;
}
public void setRefinerySupplierName(String refinerySupplierName) {
	this.refinerySupplierName = refinerySupplierName;
}
public String getOrnamentsType() {
	return ornamentsType;
}
public void setOrnamentsType(String ornamentsType) {
	this.ornamentsType = ornamentsType;
}
public Integer getPurity() {
	return purity;
}
public void setPurity(Integer purity) {
	this.purity = purity;
}
public Integer getPieces() {
	return pieces;
}
public void setPieces(Integer pieces) {
	this.pieces = pieces;
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
public String getNarration() {
	return narration;
}
public void setNarration(String narration) {
	this.narration = narration;
}
public static long getSerialversionuid() {
	return serialVersionUID;
}
public String getItemCode() {
	return itemCode;
}
public void setItemCode(String itemCode) {
	this.itemCode = itemCode;
}
public String getRefineryIssueType() {
	return refineryIssueType;
}
public void setRefineryIssueType(String refineryIssueType) {
	this.refineryIssueType = refineryIssueType;
}


}
