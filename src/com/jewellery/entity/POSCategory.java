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
@NamedQuery(name = "getCategoryList", query = "from POSCategory order by categoryId"),
@NamedQuery(name = "getPOSCategoryByName", query = "from POSCategory where categoryName = :categoryName")
})

public class POSCategory implements Serializable {

	private static final long serialVersionUID = 1L;	
	
	private Integer categoryId;
	private String categoryName;
	private Date POS_Date;
	private int totalQuantity; 
	private BigDecimal DiscountPercentage = new BigDecimal("0.00");
	private BigDecimal vatPercentage = new BigDecimal("0.00");	
	private String description;
	

	private List<POSItem> posItems = new ArrayList<POSItem>();


	public POSCategory() {
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
	
	public Date getPOS_Date() {
		return POS_Date;
	}

	public void setPOS_Date(Date pOS_Date) {
		POS_Date = pOS_Date;
	}
	
	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public BigDecimal getDiscountPercentage() {
		return DiscountPercentage;
	}

	public void setDiscountPercentage(BigDecimal discountPercentage) {
		DiscountPercentage = discountPercentage;
	}

	public BigDecimal getVatPercentage() {
		return vatPercentage;
	}

	public void setVatPercentage(BigDecimal vatPercentage) {
		this.vatPercentage = vatPercentage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "poscategory_positem", joinColumns = { @JoinColumn(name = "categoryId") }, inverseJoinColumns = { @JoinColumn(name = "itemId") })
	public List<POSItem> getPosItems() {
		return posItems;
	}

	public void setPosItems(List<POSItem> posItems) {
		this.posItems = posItems;
	}
	
}