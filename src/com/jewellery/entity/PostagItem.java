package com.jewellery.entity;

import com.jewellery.entity.POSItem;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
@Entity
@NamedQueries({
	@NamedQuery (name = "getposTagedList", query = "from PostagItem where qtyset!=0 and status='Unprinted'" ),
	@NamedQuery (name = "getposTagedPrintedList", query = "from PostagItem where qtyset!=0 and status='printed'")

})
public class PostagItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public Integer tagId;
	public String transactionId;
	public String barcodeId;
	public String transactionType;
	private String categoryName;
	private String itemName;
	public Date date;
	public BigDecimal costRate=new BigDecimal("0.00");
	public BigDecimal margin=new BigDecimal("0.00");
	public BigDecimal salesRate=new BigDecimal("0.00");
	public BigDecimal mrpinRs=new BigDecimal("0.00");
	private BigDecimal discountPercentage = new BigDecimal("0.00");
	private BigDecimal vatPercentage = new BigDecimal("0.00");
	public Integer qtyset;
	public Integer pps;
	public Integer totalpieces;
	public String printName;
	public String status;
	public String deleted="";
	public String companyName;
	private POSItem positem;
	private String POSReferenceID;
	
	public PostagItem(){}
	
		/**
	 * @return the tagId
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getTagId() {
		return tagId;
	}
	/**
	 * @param tagId the tagId to set
	 */
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}
	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}
	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	/**
	 * @return the barcodeId
	 */
	public synchronized String getBarcodeId() {
		return barcodeId;
	}
	/**
	 * @param barcodeId the barcodeId to set
	 */
	public synchronized void setBarcodeId(String barcodeId) {
		this.barcodeId = barcodeId;
	}
	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}
	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	/** 
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the costRate
	 */
	@Column(precision = 19, scale = 2)
	public BigDecimal getCostRate() {
		return costRate;
	}
	/**
	 * @param costRate the costRate to set
	 */
	public void setCostRate(BigDecimal costRate) {
		this.costRate = costRate;
	}
	/**
	 * @return the margin
	 */
	@Column(precision = 19, scale = 2)
	public BigDecimal getMargin() {
		return margin;
	}
	/**
	 * @param margin the margin to set
	 */
	public void setMargin(BigDecimal margin) {
		this.margin = margin;
	}
	/**
	 * @return the salesRate
	 */
	@Column(precision = 19, scale = 2)
	public BigDecimal getSalesRate() {
		return salesRate;
	}
	/**
	 * @param salesRate the salesRate to set
	 */
	public void setSalesRate(BigDecimal salesRate) {
		this.salesRate = salesRate;
	}
	/**
	 * @return the mrpinRs
	 */
	@Column(precision = 19, scale = 2)
	public BigDecimal getMrpinRs() {
		return mrpinRs;
	}
	/**
	 * @param mrpinRs the mrpinRs to set
	 */
	public void setMrpinRs(BigDecimal mrpinRs) {
		this.mrpinRs = mrpinRs;
	}
	/**
	 * @return the qtyset
	 */
	public Integer getQtyset() {
		return qtyset;
	}
	/**
	 * @param qtyset the qtyset to set
	 */
	public void setQtyset(Integer qtyset) {
		this.qtyset = qtyset;
	}
	
	public Integer getPps() {
		return pps;
	}

	public void setPps(Integer pps) {
		this.pps = pps;
	}

	/**
	 * @return the totalpieces
	 */
	public Integer getTotalpieces() {
		return totalpieces;
	}
	/**
	 * @param totalpieces the totalpieces to set
	 */
	public void setTotalpieces(Integer totalpieces) {
		this.totalpieces = totalpieces;
	}
	/**
	 * @return the printName
	 */
	public String getPrintName() {
		return printName;
	}
	/**
	 * @param printName the printName to set
	 */
	public void setPrintName(String printName) {
		this.printName = printName;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the positem
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="itemId",nullable=true)
	public POSItem getPositem() {
		return positem;
	}

	/**
	 * @param positem the positem to set
	 */
	public void setPositem(POSItem positem) {
		this.positem = positem;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	


	public String getPOSReferenceID() {
		return POSReferenceID;
	}

	public void setPOSReferenceID(String pOSReferenceID) {
		POSReferenceID = pOSReferenceID;
	}
	
	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public BigDecimal getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(BigDecimal discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public BigDecimal getVatPercentage() {
		return vatPercentage;
	}

	public void setVatPercentage(BigDecimal vatPercentage) {
		this.vatPercentage = vatPercentage;
	}
	
}
