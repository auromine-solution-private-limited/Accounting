package com.jewellery.entity;
import com.jewellery.entity.PostagItem;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
@Entity
@NamedQueries({
	@NamedQuery(name = "getPOSItems", query = "from POSItem")	
})
//@Table(name="POSItem",catalog="jewellery")
public class POSItem {
	
	private Integer itemId;
	private String barcode;
	private String categoryName;
	private String itemName;
	private String companyName;	
	private String printName;
	private String stockType;
	private Date stockDate;	
	private BigDecimal vatPercentage = new BigDecimal("0.00");
	private BigDecimal discountPercentage = new BigDecimal("0.00");
	private int openingStockSet;
	private int piecesPerQty;
	private int totalPieces;
	private int closingStockSet;	
	private int clStockTotalPcs;	
	private BigDecimal costRate = new BigDecimal("0.00");	
	private BigDecimal margin = new BigDecimal("0.00");
	private BigDecimal salesRate = new BigDecimal("0.00");
	private BigDecimal MRP = new BigDecimal("0.00");
	private int minInSet;
	private Integer ROL ;
	private String isPrint;
	private String description;
	private String posPrintFormat;
	
	private Set<PostagItem> postag = new HashSet<PostagItem>(0);	
 
	public POSItem() {
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}	

	@Column(nullable = false, length = 25)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(nullable = false, length = 50)
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}	
	
	public int getPiecesPerQty() {
		return piecesPerQty;
	}

	public void setPiecesPerQty(int piecesPerQty) {
		this.piecesPerQty = piecesPerQty;
	}

	public int getTotalPieces() {
		return totalPieces;
	}

	public void setTotalPieces(int totalPieces) {
		this.totalPieces = totalPieces;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public Date getStockDate() {
		return stockDate;
	}

	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPrintName() {
		return printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}

	public BigDecimal getVatPercentage() {
		return vatPercentage;
	}

	public void setVatPercentage(BigDecimal vatPercentage) {
		this.vatPercentage = vatPercentage;
	}

	public BigDecimal getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(BigDecimal discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	
	
	public int getOpeningStockSet() {
		return openingStockSet;
	}
	public void setOpeningStockSet(int openingStockSet) {
		this.openingStockSet = openingStockSet;
	}
	public int getClosingStockSet() {
		return closingStockSet;
	}

	public void setClosingStockSet(int closingStockSet) {
		this.closingStockSet = closingStockSet;
	}

	public int getClStockTotalPcs() {
		return clStockTotalPcs;
	}

	public void setClStockTotalPcs(int clStockTotalPcs) {
		this.clStockTotalPcs = clStockTotalPcs;
	}

	public BigDecimal getCostRate() {
		return costRate;
	}

	public void setCostRate(BigDecimal costRate) {
		this.costRate = costRate;
	}

	public BigDecimal getMargin() {
		return margin;
	}

	public void setMargin(BigDecimal margin) {
		this.margin = margin;
	}

	public BigDecimal getSalesRate() {
		return salesRate;
	}

	public void setSalesRate(BigDecimal salesRate) {
		this.salesRate = salesRate;
	}

	public BigDecimal getMRP() {
		return MRP;
	}

	public void setMRP(BigDecimal mRP) {
		MRP = mRP;
	}

	public int getMinInSet() {
		return minInSet;
	}

	public void setMinInSet(int minInSet) {
		this.minInSet = minInSet;
	}

	public Integer getROL() {
		return ROL;
	}

	public void setROL(Integer rOL) { 
		ROL = rOL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the isPrint
	 */
	public String getIsPrint() {
		return isPrint;
	}
	/**
	 * @param isPrint the isPrint to set
	 */
	public void setIsPrint(String isPrint) {
		this.isPrint = isPrint;
	}
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "positem")
	public Set<PostagItem> getPostag() {
		return postag;
	}

	public void setPostag(Set<PostagItem> postag) {
		this.postag = postag;
	}
	public String getPosPrintFormat() {
		return posPrintFormat;
	}
	public void setPosPrintFormat(String posPrintFormat) {
		this.posPrintFormat = posPrintFormat;
	}
}