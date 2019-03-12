package com.jewellery.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	@NamedQuery(name = "getSchemeByName", query = "from SavingScheme where schemeName = :schemeName"),
	@NamedQuery(name = "getSchemes", query = "from SavingScheme where schemeName = ?"),
	@NamedQuery(name="scheme_list",query="from SavingScheme order by saving_schemeId")    
})

public class SavingScheme implements Serializable {
  
	private static final long serialVersionUID = 1L;
	
	private Integer saving_schemeId;
	private String schemeName;	
	private String schemeType;	 
	private BigDecimal schemeInAmount = new BigDecimal("0.00");;	
	private BigDecimal schemeInGrams = new BigDecimal("0.000");;	
	private String description;
	
	private List<StartScheme> schemeList = new ArrayList<StartScheme>();

	
	public SavingScheme() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue
	public Integer getSaving_schemeId() {
		return saving_schemeId;
	}

	public void setSaving_schemeId(Integer saving_schemeId) {
		this.saving_schemeId = saving_schemeId;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	
	public String getSchemeName() {
		return schemeName;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

	/*@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "Start_Saving", joinColumns = { @JoinColumn(name = "saving_schemeId") }, inverseJoinColumns = { @JoinColumn(name = "start_schemeId") })*/	
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "savingscheme")
	public List<StartScheme> getSchemeList() {
		return schemeList;
	}

	public void setSchemeList(List<StartScheme> schemeList) {
		this.schemeList = schemeList;
	}
}