package com.jewellery.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	@NamedQuery(name = "getStartSchemeByName", query = "from StartScheme where schemeStatus='Active' AND schemeName = ?"),	
	@NamedQuery(name = "SchemeByName", query = "from StartScheme where schemeStatus='Active'"),
	@NamedQuery(name="scheme_List",query="from StartScheme order by start_schemeId")
}) 

public class StartScheme implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer start_schemeId;	
	private String schemeName;	
	private String schemeType;	
	private BigDecimal schemeInAmount = new BigDecimal("0.00");;	
	private BigDecimal schemeInGrams = new BigDecimal("0.000");;
	private Date schemeStartDate;
	private Integer schemeDuration;
	private String schemeStatus;
	private SavingScheme savingscheme;
	private String description;
	
	private List<CardIssue> cardIssueList = new ArrayList<CardIssue>();

	public StartScheme() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue
	public Integer getStart_schemeId() {
		return start_schemeId;	
	}

	public void setStart_schemeId(Integer start_schemeId) {
		this.start_schemeId = start_schemeId;
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

	public BigDecimal getSchemeInAmount() {
		return schemeInAmount;
	}

	public void setSchemeInAmount(BigDecimal schemeInAmount) {
		this.schemeInAmount = schemeInAmount;
	}

	public BigDecimal getSchemeInGrams() {
		return schemeInGrams;
	}

	public void setSchemeInGrams(BigDecimal schemeInGrams) {
		this.schemeInGrams = schemeInGrams;
	}	
 
	public Integer getSchemeDuration() {
		return schemeDuration;
	}

	public void setSchemeDuration(Integer schemeDuration) {
		this.schemeDuration = schemeDuration;
	}

	public Date getSchemeStartDate() {
		return schemeStartDate;
	}

	public void setSchemeStartDate(Date schemeStartDate) {
		this.schemeStartDate = schemeStartDate;
	}

	public String getSchemeStatus() {
		return schemeStatus;
	}

	public void setSchemeStatus(String schemeStatus) {
		this.schemeStatus = schemeStatus;
	}	
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "startscheme")
	public List<CardIssue> getCardIssueList() { 
		return cardIssueList;
	}

	public void setCardIssueList(List<CardIssue> cardIssueList) {
		this.cardIssueList = cardIssueList;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="saving_schemeId",nullable=true)
	public SavingScheme getSavingscheme() {
		return savingscheme;
	}

	public void setSavingscheme(SavingScheme savingscheme) {
		this.savingscheme = savingscheme;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}