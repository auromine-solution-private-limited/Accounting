package com.jewellery.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@Entity
@NamedQueries({ @NamedQuery(name = "listAllPurchase", query = "from Purchase"), 
@NamedQuery(name="goldExchange",query="from Purchase where purchseInvoice= (SELECT purchseInvoice from Purchase WHERE purchaseId = ( SELECT MAX(purchaseId) FROM Purchase where purchseInvoice LIKE 'GE%')))"),
@NamedQuery(name="silverExchange",query="from Purchase where purchseInvoice= (SELECT purchseInvoice from Purchase WHERE purchaseId = ( SELECT MAX(purchaseId) FROM Purchase where purchseInvoice LIKE 'SE%')))"),
@NamedQuery(name="purchaseInvoiceNo",query="from Purchase where purchseInvoice= (SELECT purchseInvoice from Purchase WHERE purchaseId = ( SELECT MAX(purchaseId) FROM Purchase where purchseInvoice LIKE 'OP%')))"),
})
@Table( name = "purchase",
uniqueConstraints= { @UniqueConstraint( columnNames = {"purchseInvoice"} ) } )
public class Purchase {
	
	private int purchasebillNO;
	private String purchseInvoice;
	private Date purchaseDate;	
	private String purchaseType;
	private String supplierName;
	private String bullionType;
	private String itemCode;
	private String paymentMode;
	private String itemName;
	private BigDecimal purchaseAmount = new BigDecimal("0.00");
	private String primaryStone;
	private String secondaryStone;
	private BigDecimal melting = new BigDecimal("0.00");
	private BigDecimal touch = new BigDecimal("0.00");
	private BigDecimal grossWeight = new BigDecimal("0.000");
	private BigDecimal netWeight = new BigDecimal("0.000");
	private BigDecimal grosslossafterTesting = new BigDecimal("0.000");
	private BigDecimal netlossafterTesting = new BigDecimal("0.000");
	private BigDecimal stoneAmount = new BigDecimal("0.00");
	private String exchangePurchase;
	private BigDecimal roundOff = new BigDecimal("0.00");
	private BigDecimal vatPercentage = new BigDecimal("0.00");
	private BigDecimal vatAmount = new BigDecimal("0.00");
	private BigDecimal cstPercentage = new BigDecimal("0.00");
	private BigDecimal cstAmount = new BigDecimal("0.00");
	private BigDecimal rate = new BigDecimal("0.00");
	private int numberOfPieces;
	private Integer purchaseId;
	private BigDecimal totalAmount = new BigDecimal("0.00");
	private BigDecimal stoneWeight = new BigDecimal("0.000");
	private String less;
	private BigDecimal lessValue = new BigDecimal("0.00");
	private BigDecimal lessValue2 = new BigDecimal("0.00");
	private String diamondStone;
	private BigDecimal diamondStoneWt = new BigDecimal("0.000");
	private BigDecimal stoneTotalAmount = new BigDecimal("0.00");
	private BigDecimal primaryStoneWt = new BigDecimal("0.000");
	private int primaryStonePcs;
	private BigDecimal primaryStoneRate = new BigDecimal("0.00");
	private BigDecimal secondaryStoneWt = new BigDecimal("0.000");
	private int secondaryStonePcs;
	private BigDecimal secondaryStoneRate = new BigDecimal("0.00");
	private BigDecimal mcAmt = new BigDecimal("0.00");
	private String currentTime;
	
	private String purDescription;
	
	public Purchase() {

	}

	@Id
	@GeneratedValue
	public Integer getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Integer purchaseId) {
		this.purchaseId = purchaseId;
	}

	public String getItemName() {
		return itemName;   
	}

	@Column(nullable = false, length = 50)
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public String getPurchseInvoice() {
		return purchseInvoice;
	}
	
	public void setPurchseInvoice(String purchseInvoice) {
		this.purchseInvoice = purchseInvoice;
	}

	@Column(nullable = false, length = 50)
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
	
	@NumberFormat(style = Style.CURRENCY)
	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public int getPurchasebillNO() {
		return purchasebillNO;
	}

	@Column(nullable = false, length = 50)
	public void setPurchasebillNO(int purchasebillNO) {
		this.purchasebillNO = purchasebillNO;
	}

	public String getBullionType() {
		return bullionType;
	}

	@Column(nullable = false, length = 50)
	public void setBullionType(String bullionType) {
		this.bullionType = bullionType;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPrimaryStone() {
		return primaryStone;
	}

	@Column(nullable = false, length = 50)
	public void setPrimaryStone(String primaryStone) {
		this.primaryStone = primaryStone;
	}

	public String getSecondaryStone() {
		return secondaryStone;
	}

	@Column(nullable = false, length = 50)
	public void setSecondaryStone(String secondaryStone) {
		this.secondaryStone = secondaryStone;
	}
	

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public int getNumberOfPieces() {
		return numberOfPieces;
	}

	public void setNumberOfPieces(int numberOfPieces) {
		this.numberOfPieces = numberOfPieces;
	}

	public String getDiamondStone() {
		return diamondStone;
	}

	public void setDiamondStone(String diamondStone) {
		this.diamondStone = diamondStone;
	}

	public BigDecimal getMelting() {
		return melting;
	}

	public void setMelting(BigDecimal melting) {
		this.melting = melting;
	}

	public BigDecimal getTouch() {
		return touch;
	}

	public void setTouch(BigDecimal touch) {
		this.touch = touch;
	}
	
	
	@Column(precision = 19, scale = 3,nullable=false)
	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getGrosslossafterTesting() {
		return grosslossafterTesting;
	}

	public void setGrosslossafterTesting(BigDecimal grosslossafterTesting) {
		this.grosslossafterTesting = grosslossafterTesting;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getNetlossafterTesting() {
		return netlossafterTesting;
	}

	public void setNetlossafterTesting(BigDecimal netlossafterTesting) {
		this.netlossafterTesting = netlossafterTesting;
	}
	
	@NumberFormat(style = Style.CURRENCY)
	public BigDecimal getStoneAmount() {
		return stoneAmount;
	}

	public void setStoneAmount(BigDecimal stoneAmount) {
		this.stoneAmount = stoneAmount;
	}

	public BigDecimal getRoundOff() {
		return roundOff;
	}

	public void setRoundOff(BigDecimal roundOff) {
		this.roundOff = roundOff;
	}

	public BigDecimal getVatPercentage() {
		return vatPercentage;
	}

	public void setVatPercentage(BigDecimal vatPercentage) {
		this.vatPercentage = vatPercentage;
	}
	
	@NumberFormat(style = Style.CURRENCY)
	public BigDecimal getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

	public BigDecimal getCstPercentage() {
		return cstPercentage;
	}

	public void setCstPercentage(BigDecimal cstPercentage) {
		this.cstPercentage = cstPercentage;
	}
	
	@NumberFormat(style = Style.CURRENCY)
	public BigDecimal getCstAmount() {
		return cstAmount;
	}

	public void setCstAmount(BigDecimal cstAmount) {
		this.cstAmount = cstAmount;
	}
	
	@NumberFormat(style = Style.CURRENCY)
	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	@NumberFormat(style = Style.CURRENCY)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getStoneWeight() {
		return stoneWeight;
	}

	public void setStoneWeight(BigDecimal stoneWeight) {
		this.stoneWeight = stoneWeight;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getDiamondStoneWt() {
		return diamondStoneWt;
	}

	public void setDiamondStoneWt(BigDecimal diamondStoneWt) {
		this.diamondStoneWt = diamondStoneWt;
	}
	
	@NumberFormat(style = Style.CURRENCY)
	public BigDecimal getStoneTotalAmount() {
		return stoneTotalAmount;
	}

	public void setStoneTotalAmount(BigDecimal stoneTotalAmount) {
		this.stoneTotalAmount = stoneTotalAmount;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getPrimaryStoneWt() {
		return primaryStoneWt;
	}

	public void setPrimaryStoneWt(BigDecimal primaryStoneWt) {
		this.primaryStoneWt = primaryStoneWt;
	}

	public int getPrimaryStonePcs() {
		return primaryStonePcs;
	}

	public void setPrimaryStonePcs(int primaryStonePcs) {
		this.primaryStonePcs = primaryStonePcs;
	}
	
	@NumberFormat(style = Style.CURRENCY)
	public BigDecimal getPrimaryStoneRate() {
		return primaryStoneRate;
	}

	public void setPrimaryStoneRate(BigDecimal primaryStoneRate) {
		this.primaryStoneRate = primaryStoneRate;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getSecondaryStoneWt() {
		return secondaryStoneWt;
	}

	public void setSecondaryStoneWt(BigDecimal secondaryStoneWt) {
		this.secondaryStoneWt = secondaryStoneWt;
	}

	public int getSecondaryStonePcs() {
		return secondaryStonePcs; 
	}

	public void setSecondaryStonePcs(int secondaryStonePcs) {
		this.secondaryStonePcs = secondaryStonePcs;
	}
	
	@NumberFormat(style = Style.CURRENCY)
	public BigDecimal getSecondaryStoneRate() {
		return secondaryStoneRate;
	}

	public String getExchangePurchase() {
		return exchangePurchase;
	}

	public void setExchangePurchase(String exchangePurchase) {
		this.exchangePurchase = exchangePurchase;
	}

	public void setSecondaryStoneRate(BigDecimal secondaryStoneRate) {
		this.secondaryStoneRate = secondaryStoneRate;
	}

	public BigDecimal getMcAmt() {
		return mcAmt;
	}

	public void setMcAmt(BigDecimal mcAmt) {
		this.mcAmt = mcAmt;
	}

	public String getPurDescription() {
		return purDescription;
	}

	public void setPurDescription(String purDescription) {
		this.purDescription = purDescription;
	}
	
	public String getLess() {
		return less;
	}

	public void setLess(String less) {
		this.less = less;
	}

	public BigDecimal getLessValue() {
		return lessValue;
	}

	public void setLessValue(BigDecimal lessValue) {
		this.lessValue = lessValue;
	}

	public BigDecimal getLessValue2() {
		return lessValue2;
	}

	public void setLessValue2(BigDecimal lessValue2) {
		this.lessValue2 = lessValue2;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	
}
