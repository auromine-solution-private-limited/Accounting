package com.jewellery.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


//select from class name and class name is case sensitive
@Entity
@NamedQueries({
	@NamedQuery(name = "getCategoryByName", query = "from Category where categoryName = :categoryName"),
	@NamedQuery(name = "getCategories", query = "from Category where categoryName = ?"),
	@NamedQuery(name="defaultcategorylist",query="from Category order by categoryId"),
	@NamedQuery(name = "listCategoryById", query = "from Category order by categoryId"),
    @NamedQuery(name = "listCategoryName", query="from Category where categoryType='Subcategory' order by categoryId DESC")
})
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_CATEGORY_TYPE = "Subcategory";
	private Integer categoryId;
	private String categoryName;
	private String baseCategory;
	private String categoryType = DEFAULT_CATEGORY_TYPE;
	private String metalType;
	private String metalUsed;
	private String scheme;
	private Date startDate;
	private Date endDate;
	private BigDecimal lessPercentage = new BigDecimal("0.00");;
	private BigDecimal vaPercentage = new BigDecimal("0.00");;
	private BigDecimal mcPerGram = new BigDecimal("0.00");;
	private BigDecimal mcAmount = new BigDecimal("0.00");;
	private BigDecimal vat = new BigDecimal("0.00");;
	private String description;
	private BigDecimal totalGrossWeight = new BigDecimal("0.000");;
	private BigDecimal rolWeight= new BigDecimal("0.00"); //add new on 28-11-12
	private int rolquantity;
	private BigDecimal mcInRupees=new BigDecimal("0.00");
	private String mcTypes;
	private BigDecimal categoryHMCharges = new BigDecimal("0.00");
	
	private List<ItemMaster> itemnames = new ArrayList<ItemMaster>();

	// private List<JewelType> jeweltype=new ArrayList<JewelType>();
	public Category() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue
	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Column(nullable = false, unique = true, length = 50)
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getBaseCategory() {
		return baseCategory;
	}

	public void setBaseCategory(String baseCategory) {
		this.baseCategory = baseCategory;
	}

	public String getMetalType() {
		return metalType;
	}

	public void setMetalType(String metalType) {
		this.metalType = metalType;
	}

	public String getMetalUsed() {
		return metalUsed;
	}

	public void setMetalUsed(String metalUsed) {
		this.metalUsed = metalUsed;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "category_itemmaster", joinColumns = { @JoinColumn(name = "categoryId") }, inverseJoinColumns = { @JoinColumn(name = "itemId") })
	public List<ItemMaster> getItemnames() {
		return itemnames;
	}

	public void setItemnames(List<ItemMaster> itemnames) {
		this.itemnames = itemnames;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getLessPercentage() {
		return lessPercentage;
	}

	public void setLessPercentage(BigDecimal lessPercentage) {
		this.lessPercentage = lessPercentage;
	}

	public BigDecimal getVaPercentage() {
		return vaPercentage;
	}

	public void setVaPercentage(BigDecimal vaPercentage) {
		this.vaPercentage = vaPercentage;
	}

	public BigDecimal getMcPerGram() {
		return mcPerGram;
	}

	public void setMcPerGram(BigDecimal mcPerGram) {
		this.mcPerGram = mcPerGram;
	}

	public BigDecimal getMcAmount() {
		return mcAmount;
	}

	public void setMcAmount(BigDecimal mcAmount) {
		this.mcAmount = mcAmount;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getTotalGrossWeight() {
		return totalGrossWeight;
	}

	public void setTotalGrossWeight(BigDecimal totalGrossWeight) {
		this.totalGrossWeight = totalGrossWeight;
	}
	public BigDecimal getRolWeight() {
		return rolWeight;
	}

	public void setRolWeight(BigDecimal rolWeight) {
		this.rolWeight = rolWeight;
	}

	public int getRolquantity() {
		return rolquantity;
	}

	public void setRolquantity(int rolquantity) {
		this.rolquantity = rolquantity;
	}

	public BigDecimal getMcInRupees() {
		return mcInRupees;
	}

	public void setMcInRupees(BigDecimal mcInRupees) {
		this.mcInRupees = mcInRupees;
	}

	public String getMcTypes() {
		return mcTypes;
	}

	public void setMcTypes(String mcTypes) {
		this.mcTypes = mcTypes;
	}

	public BigDecimal getCategoryHMCharges() {
		return categoryHMCharges;
	}

	public void setCategoryHMCharges(BigDecimal categoryHMCharges) {
		this.categoryHMCharges = categoryHMCharges;
	}
	
}