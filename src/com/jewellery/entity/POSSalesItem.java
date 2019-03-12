package com.jewellery.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
//@NamedQueries({ @NamedQuery(name = "listAllPOSSalesItem", query = " from POSSalesItem  ") })
public class POSSalesItem implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer salesItemID;
	private String itemSalesType;
    private String posItemCode;
	private String categoryName;
	private String itemName;
	private Integer quantity;
	private Integer totalPieces;
	private BigDecimal salesRate= new BigDecimal("0.00");
	private BigDecimal discountAmount = new BigDecimal("0.00");
	private BigDecimal salesTax = new BigDecimal("0.00");
	private BigDecimal salesAmount = new BigDecimal("0.00");
    private POSSales possales;
    public boolean POSdeleted = false;
    private String itemStatus = "";
	
	//private Integer posSalesId;
    public POSSalesItem(){}
 
    @Id
    @GeneratedValue
    
    public Integer getSalesItemID() {
		return salesItemID;
	}
	public void setSalesItemID(Integer salesItemID) {
		this.salesItemID = salesItemID;
	}
    
	public String getItemSalesType() {
		return itemSalesType;
	}

	public void setItemSalesType(String itemSalesType) {
		this.itemSalesType = itemSalesType;
	}

	public String getPosItemCode() {
		return posItemCode;
	}
	public void setPosItemCode(String posItemCode) {
		this.posItemCode = posItemCode;
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
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getTotalPieces() {
		return totalPieces;
	}
	public void setTotalPieces(Integer totalPieces) {
		this.totalPieces = totalPieces;
	}
	
	public BigDecimal getSalesRate() {
		return salesRate;
	}
	public void setSalesRate(BigDecimal salesRate) {
		this.salesRate = salesRate;
	}
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}
	public BigDecimal getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(BigDecimal salesAmount) {
		this.salesAmount = salesAmount;
	}
	
	public BigDecimal getSalesTax() {
		return salesTax;
	}
	public void setSalesTax(BigDecimal salesTax) {
		this.salesTax = salesTax;
	}
	@Fetch(FetchMode.SELECT)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "posSalesId", referencedColumnName="posSalesId",nullable = false)
	public POSSales getPossales() {
		return possales;
	}
	
	public void setPossales(POSSales possales) {
		this.possales = possales;
	}

	public boolean isPOSdeleted() {
		return POSdeleted;
	}

	public void setPOSdeleted(boolean pOSdeleted) {
		POSdeleted = pOSdeleted;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	
	
	
	
}
