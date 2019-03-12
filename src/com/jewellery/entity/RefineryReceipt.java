package com.jewellery.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RefineryReceipt implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Integer rrId;
private Date rrDate;
private String refineryReceiptNo;
private String rrName;
private String rrOrnamentsType;
private String rritemcode;
private Integer rrpurity;
private Integer rrpieces;
private BigDecimal rrGrossWeight=new BigDecimal("0.000");
private BigDecimal rrNetWeight=new BigDecimal("0.000");
private BigDecimal labourCharge=new BigDecimal("0.00");
private String rrNarration;

@Id
@GeneratedValue
public Integer getRrId() {
	return rrId;
}
public void setRrId(Integer rrId) {
	this.rrId = rrId;
}
public Date getRrDate() {
	return rrDate;
}
public void setRrDate(Date rrDate) {
	this.rrDate = rrDate;
}
public String getRrName() {
	return rrName;
}
public void setRrName(String rrName) {
	this.rrName = rrName;
}
public String getRrOrnamentsType() {
	return rrOrnamentsType;
}
public void setRrOrnamentsType(String rrOrnamentsType) {
	this.rrOrnamentsType = rrOrnamentsType;
}
public Integer getRrpurity() {
	return rrpurity;
}
public void setRrpurity(Integer rrpurity) {
	this.rrpurity = rrpurity;
}
public Integer getRrpieces() {
	return rrpieces;
}
public void setRrpieces(Integer rrpieces) {
	this.rrpieces = rrpieces;
}
public BigDecimal getRrGrossWeight() {
	return rrGrossWeight;
}
public void setRrGrossWeight(BigDecimal rrGrossWeight) {
	this.rrGrossWeight = rrGrossWeight;
}
public BigDecimal getRrNetWeight() {
	return rrNetWeight;
}
public void setRrNetWeight(BigDecimal rrNetWeight) {
	this.rrNetWeight = rrNetWeight;
}
public BigDecimal getLabourCharge() {
	return labourCharge;
}
public void setLabourCharge(BigDecimal labourCharge) {
	this.labourCharge = labourCharge;
}
public String getRrNarration() {
	return rrNarration;
}
public void setRrNarration(String rrNarration) {
	this.rrNarration = rrNarration;
}
public String getRritemcode() {
	return rritemcode;
}
public void setRritemcode(String rritemcode) {
	this.rritemcode = rritemcode;
}
public String getRefineryReceiptNo() {
	return refineryReceiptNo;
}
public void setRefineryReceiptNo(String refineryReceiptNo) {
	this.refineryReceiptNo = refineryReceiptNo;
}


}
