package com.jewellery.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@NamedQueries({	@NamedQuery(name="cardList", query="from CardIssue order by cardId") })

@Table( name = "CardIssue",
uniqueConstraints= { @UniqueConstraint( columnNames = { "cardNo"} ) } )
public class CardIssue implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer cardId;
	private String cardNo;
	private String customerName; 
	private String schemeName;	
	private String schemeType;
	private Integer installment = 0;
	private BigDecimal schemeInAmount = new BigDecimal("0.00");;	
	private BigDecimal schemeInGrams = new BigDecimal("0.000");;
	private Date dateOfJoining;
	private BigDecimal openingBalanceInRs = new BigDecimal("0.00");
	private BigDecimal closingBalanceInRs = new BigDecimal("0.00");
	private BigDecimal openingBalanceInGrams = new BigDecimal("0.000");
	private BigDecimal closingBalanceInGrams = new BigDecimal("0.000");
	private String schemeDuration;
	private String status;
	private StartScheme startscheme;
	private String description;

	public CardIssue() {
		
	}
	
	@Id
	@GeneratedValue	
	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSchemeName() {
		return schemeName;
	}
 
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(String schemeType) {
		this.schemeType = schemeType;
	}

	public Integer getInstallment() {
		return installment;
	}

	public void setInstallment(Integer installment) {
		this.installment = installment;
	}
	@Column(precision = 19, scale = 2)
	public BigDecimal getSchemeInAmount() {
		return schemeInAmount;
	}
	
	public void setSchemeInAmount(BigDecimal schemeInAmount) {
		this.schemeInAmount = schemeInAmount;
	}
	@Column(precision = 19, scale = 3)
	public BigDecimal getSchemeInGrams() {
		return schemeInGrams;
	}

	public void setSchemeInGrams(BigDecimal schemeInGrams) {
		this.schemeInGrams = schemeInGrams;
	}	

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	@Column(precision = 19, scale = 2)
	public BigDecimal getOpeningBalanceInRs() {
		return openingBalanceInRs;
	}

	public void setOpeningBalanceInRs(BigDecimal openingBalanceInRs) {
		this.openingBalanceInRs = openingBalanceInRs;
	}
	@Column(precision = 19, scale = 2)
	public BigDecimal getClosingBalanceInRs() {
		return closingBalanceInRs;
	}

	public void setClosingBalanceInRs(BigDecimal closingBalanceInRs) {
		this.closingBalanceInRs = closingBalanceInRs;
	}
	@Column(precision = 19, scale = 3)
	public BigDecimal getOpeningBalanceInGrams() {
		return openingBalanceInGrams;
	}

	public void setOpeningBalanceInGrams(BigDecimal openingBalanceInGrams) {
		this.openingBalanceInGrams = openingBalanceInGrams;
	}
	@Column(precision = 19, scale = 3)
	public BigDecimal getClosingBalanceInGrams() {
		return closingBalanceInGrams;
	}

	public void setClosingBalanceInGrams(BigDecimal closingBalanceInGrams) {
		this.closingBalanceInGrams = closingBalanceInGrams;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSchemeDuration() {
		return schemeDuration; 
	}

	public void setSchemeDuration(String schemeDuration) {
		this.schemeDuration = schemeDuration;
	}	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="start_schemeId",nullable=false)
	public StartScheme getStartscheme() {
		return startscheme;
	}

	public void setStartscheme(StartScheme startscheme) {
		this.startscheme = startscheme;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}