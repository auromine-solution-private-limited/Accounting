package com.jewellery.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

//select from class name and class name is case sensitive
@Entity
@NamedQueries({
	@NamedQuery(name = "listJewelTypeId", query = "from JewelType order by jewelTypeId"),
    @NamedQuery(name = "goldornamentlist", query="from JewelType as jewelTypeId where jewelTypeId>'10' and (metaltype='Gold Ornaments' or metaltype='Gold Loose Stock')order by jewelTypeId DESC"),
     @NamedQuery(name = "silverornamentlist", query="from JewelType as jewelTypeId where jewelTypeId>'10' and (metaltype='Silver Ornaments' or metaltype='Silver Loose Stock') order by jewelTypeId DESC")
})
public class JewelType implements Serializable {

	/**
	 **/
	private static final long serialVersionUID = 1L;

	private Integer jewelTypeId;
	private String jewelName;
	private String metalType;
	private String metalUsed;
	private String description;
	private BigDecimal totalGrossWeight =new BigDecimal("0.000");
	private int totalPieces;

	// private List<Category> categorynames = new ArrayList<Category>();

	public JewelType() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue
	public Integer getJewelTypeId() {
		return jewelTypeId;
	}

	public void setJewelTypeId(Integer jewelTypeId) {
		this.jewelTypeId = jewelTypeId;
	}

	@Column(nullable = false, unique = true, length = 50)
	public String getJewelName() {
		return jewelName;
	}

	public void setJewelName(String jewelName) {
		this.jewelName = jewelName;
	}

	public String getMetalType() {
		return metalType;
	}

	public void setMetalType(String metalType) {
		this.metalType = metalType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMetalUsed() {
		return metalUsed;
	}

	public void setMetalUsed(String metalUsed) {
		this.metalUsed = metalUsed;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getTotalGrossWeight() {
		return totalGrossWeight;
	}

	public void setTotalGrossWeight(BigDecimal totalGrossWeight) {
		this.totalGrossWeight = totalGrossWeight;
	}

	public int getTotalPieces() {  
		return totalPieces;
	}

	public void setTotalPieces(int totalPieces) {
		this.totalPieces = totalPieces;
	}
	 
	

	/*
	 * @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	 * 
	 * @JoinTable(name = "jeweltype_category", joinColumns = { @JoinColumn(name
	 * = "jewelTypeId")}, inverseJoinColumns={@JoinColumn(name="categoryId")} )
	 */

	/*
	 * public List<Category> getCategorynames() { return categorynames; }
	 * 
	 * public void setCategorynames(List<Category> categorynames) {
	 * this.categorynames = categorynames; }
	 */

}