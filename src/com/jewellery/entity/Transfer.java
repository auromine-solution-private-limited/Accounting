package com.jewellery.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({	@NamedQuery(name = "listAllTransfer", query = "from Transfer") })
		 

public class Transfer {
	private Integer transferId;
	private String transferNO;
	private String transferType;
	private Date transferDate;
	private String fromItemNo;
	private String toItemNo;
	private String fromItemName;
	private String toItemName;
	private String fromBullion;
	private String toBullion;
	private BigDecimal fromGrossWeight = new BigDecimal("0.000");
	private BigDecimal toGrossWeight = new BigDecimal("0.000");
	private BigDecimal fromTotalGrossWeight = new BigDecimal("0.000");;// newly added field
	private BigDecimal toTotalGrossWeight = new BigDecimal("0.000");;
	private BigDecimal fromNetWeight = new BigDecimal("0.000");;
	private BigDecimal toNetWeight = new BigDecimal("0.000");
	private int fromQty;
	private int toQty;
	private int fromPieces;
	private int toPieces;
	private String tagissue;
	private String category;
	private String subcategory;
	private String description;
	private String itemName;
	private String metaltype;
	private BigDecimal itemweght = new BigDecimal("0.000");
	private int itemqtset;

	public Transfer() {

	}

	@Id
	@GeneratedValue
	public Integer getTransferId() {
		return transferId;
	}

	public void setJournalId(Integer transferId) {
		this.transferId = transferId;
	}

	public String getTransferNO() {
		return transferNO;
	}

	public void setTransferNO(String transferNO) {
		this.transferNO = transferNO;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getFromGrossWeight() {
		return fromGrossWeight;
	}

	public void setFromGrossWeight(BigDecimal fromGrossWeight) {
		this.fromGrossWeight = fromGrossWeight;
	}

	public String getFromItemName() {
		return fromItemName;
	}

	public void setFromItemName(String fromItemName) {
		this.fromItemName = fromItemName;
	}

	public String getFromItemNo() {
		return fromItemNo;
	}

	public void setFromItemNo(String fromItemNo) {
		this.fromItemNo = fromItemNo;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getFromNetWeight() {
		return fromNetWeight;
	}

	public void setFromNetWeight(BigDecimal fromNetWeight) {
		this.fromNetWeight = fromNetWeight;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getToGrossWeight() {
		return toGrossWeight;
	}

	public void setToGrossWeight(BigDecimal toGrossWeight) {
		this.toGrossWeight = toGrossWeight;
	}

	public String getToItemName() {
		return toItemName;
	}

	public void setToItemName(String toItemName) {
		this.toItemName = toItemName;
	}

	public String getToItemNo() {
		return toItemNo;
	}

	public void setToItemNo(String toItemNo) {
		this.toItemNo = toItemNo;
	}

	public String getFromBullion() {
		return fromBullion;
	}

	public void setFromBullion(String fromBullion) {
		this.fromBullion = fromBullion;
	}

	public String getToBullion() {
		return toBullion;
	}

	public void setToBullion(String toBullion) {
		this.toBullion = toBullion;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getToNetWeight() {
		return toNetWeight;
	}

	public void setToNetWeight(BigDecimal toNetWeight) {
		this.toNetWeight = toNetWeight;
	}

	public int getFromQty() {
		return fromQty;
	}

	public void setFromQty(int fromQty) {
		this.fromQty = fromQty;
	}

	public int getToQty() {
		return toQty;
	}

	public void setToQty(int toQty) {
		this.toQty = toQty;
	}

	public int getFromPieces() {
		return fromPieces;
	}

	public void setFromPieces(int fromPieces) {
		this.fromPieces = fromPieces;
	}

	public int getToPieces() {
		return toPieces;
	}

	public void setToPieces(int toPieces) {
		this.toPieces = toPieces;
	}

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public void setTransferId(Integer transferId) {
		this.transferId = transferId;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getFromTotalGrossWeight() {
		return fromTotalGrossWeight;
	}

	public void setFromTotalGrossWeight(BigDecimal fromTotalGrossWeight) {
		this.fromTotalGrossWeight = fromTotalGrossWeight;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getToTotalGrossWeight() {
		return toTotalGrossWeight;
	}

	public void setToTotalGrossWeight(BigDecimal toTotalGrossWeight) {
		this.toTotalGrossWeight = toTotalGrossWeight;
	}

	public String getTagissue() {
		return tagissue;
	}

	public void setTagissue(String tagissue) {
		this.tagissue = tagissue;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getMetaltype() {
		return metaltype;
	}

	public void setMetaltype(String metaltype) {
		this.metaltype = metaltype;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getItemweght() {
		return itemweght;
	}

	public void setItemweght(BigDecimal itemweght) {
		this.itemweght = itemweght;
	}

	public int getItemqtset() {
		return itemqtset;
	}

	public void setItemqtset(int itemqtset) {
		this.itemqtset = itemqtset;
	}

}
