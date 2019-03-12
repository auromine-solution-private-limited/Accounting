package com.jewellery.entity;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@NamedQueries({
	@NamedQuery(name = "getItemsByCode", query = "from ItemMaster where itemCode = ?"),
	@NamedQuery(name = "getItems", query = "from ItemMaster where itemCode = ?"),
	@NamedQuery (name = "listAllItems", query = "from ItemMaster order by itemCode desc"),
	@NamedQuery (name = "listItems", query = "from ItemMaster as itemName where itemId >'10' order by itemName"),
	@NamedQuery (name = "listItemCode", query = "from ItemMaster order by itemCode"),
	@NamedQuery (name = "Itemstocklist", query = "from ItemMaster where (qty != 0) and (itemCode != 'IT100003' and itemCode != 'IT100004' and itemCode != 'IT100005' and itemCode != 'IT100008' and itemCode != 'IT100009' and itemCode != 'IT100010') "),
	@NamedQuery (name = "getItemCode", query = "from ItemMaster where itemCode = (SELECT MAX(itemCode) FROM ItemMaster)"),
	@NamedQuery (name = "itemWeightList", query ="from ItemMaster where(itemCode between 'IT100001' and 'IT100012') and (categoryName != 'Gold Bullion' and categoryName != 'Silver Bullion')"),
    @NamedQuery(name = "subcatlowstocklist", query = "SELECT sum(qty) from ItemMaster where subCategoryName = :subCategoryName and (qty!=0)")
})
@Table( name = "itemMaster",
uniqueConstraints= { @UniqueConstraint( columnNames = { "itemCode"} ) } )
public class ItemMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Integer itemId;
	private String itemCode;
	private String itemName;
	private String categoryName;
	private String subCategoryName;
	private String metalUsed;
	private String metalType;
	private int qty = 1;
	private int piecesPerQty;
	private int totalPieces;
	private BigDecimal grossWeight = new BigDecimal("0.000");
	private BigDecimal netWeight = new BigDecimal("0.000");
	private BigDecimal bullionRate = new BigDecimal("0.00");
	private BigDecimal vaPercentage = new BigDecimal("0.00");
	private BigDecimal lessPercentage = new BigDecimal("0.00");	
	private BigDecimal mcPerGram = new BigDecimal("0.00");
	private BigDecimal mcAmount = new BigDecimal("0.00");
	private String Stone;
	private BigDecimal stoneWeight = new BigDecimal("0.000");
	private BigDecimal stoneRatePerCaret = new BigDecimal("0.00");
	private int stonePieces;
	private BigDecimal stoneCost = new BigDecimal("0.00");
	private BigDecimal totalGrossWeight = new BigDecimal("0.000");
	private BigDecimal op_GrossWeight = new BigDecimal("0.000");
	private BigDecimal op_NetWeight = new BigDecimal("0.000");
	private BigDecimal op_TotalGrossWeight = new BigDecimal("0.000");
	private int op_Quantity;
	private int op_TotalPieces;
	private String manufacturerSeal;
	private BigDecimal tax= new BigDecimal("0.00"); 
	private String stockType;
	private String itemseal;
	private Date stockDate;
	private BigDecimal mcInRupee=new BigDecimal("0.00");
	private String ornPrintFormat;
	private BigDecimal itemHMCharges = new BigDecimal("0.00");
	
	
	public ItemMaster() {
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public String getMetalUsed() {
		return metalUsed;
	}

	public void setMetalUsed(String metalUsed) {
		this.metalUsed = metalUsed;
	}

	public String getMetalType() {
		return metalType;
	}

	public void setMetalType(String metalType) {
		this.metalType = metalType;
	}

	public String getManufacturerSeal() {
		return manufacturerSeal;
	}

	public void setManufacturerSeal(String manufacturerSeal) {
		this.manufacturerSeal = manufacturerSeal;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	// Stone Details
	public String getStone() {
		return Stone;
	}

	public void setStone(String stone) {
		Stone = stone;
	}

	public int getStonePieces() {
		return stonePieces;
	}

	public void setStonePieces(int stonePieces) {
		this.stonePieces = stonePieces;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
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

	public String getItemseal() {
		return itemseal;
	}

	public void setItemseal(String itemseal) {
		this.itemseal = itemseal;
	}

	public Date getStockDate() {
		return stockDate;
	}

	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}
	
	@Column(precision = 19, scale = 3)
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

	public BigDecimal getBullionRate() {
		return bullionRate;
	}

	public void setBullionRate(BigDecimal bullionRate) {
		this.bullionRate = bullionRate;
	}

	public BigDecimal getVaPercentage() {
		return vaPercentage;
	}

	public void setVaPercentage(BigDecimal vaPercentage) {
		this.vaPercentage = vaPercentage;
	}

	public BigDecimal getLessPercentage() {
		return lessPercentage;
	}

	public void setLessPercentage(BigDecimal lessPercentage) {
		this.lessPercentage = lessPercentage;
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
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getStoneWeight() {
		return stoneWeight;
	}

	public void setStoneWeight(BigDecimal stoneWeight) {
		this.stoneWeight = stoneWeight;
	}

	public BigDecimal getStoneRatePerCaret() {
		return stoneRatePerCaret;
	}

	public void setStoneRatePerCaret(BigDecimal stoneRatePerCaret) {
		this.stoneRatePerCaret = stoneRatePerCaret;
	}

	public BigDecimal getStoneCost() {
		return stoneCost;
	}

	public void setStoneCost(BigDecimal stoneCost) {
		this.stoneCost = stoneCost;
	}
	
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getOp_GrossWeight() {
		return op_GrossWeight;
	}

	public void setOp_GrossWeight(BigDecimal op_GrossWeight) {
		this.op_GrossWeight = op_GrossWeight;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getOp_NetWeight() {
		return op_NetWeight;
	}

	@Column(precision = 19, scale = 3)
	public void setOp_NetWeight(BigDecimal op_NetWeight) {
		this.op_NetWeight = op_NetWeight;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getOp_TotalGrossWeight() {
		return op_TotalGrossWeight;
	}

	public void setOp_TotalGrossWeight(BigDecimal op_TotalGrossWeight) {
		this.op_TotalGrossWeight = op_TotalGrossWeight;
	}

	public int getOp_Quantity() {
		return op_Quantity;
	}

	public void setOp_Quantity(int op_Quantity) {
		this.op_Quantity = op_Quantity;
	}

	public int getOp_TotalPieces() {
		return op_TotalPieces;
	}

	public void setOp_TotalPieces(int op_TotalPieces) {
		this.op_TotalPieces = op_TotalPieces;
	}	

	
	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	@Column(precision = 19, scale = 3,nullable=false)
	public BigDecimal getTotalGrossWeight() {
		return totalGrossWeight;
	}

	public void setTotalGrossWeight(BigDecimal totalGrossWeight) {
		this.totalGrossWeight = totalGrossWeight;
	}

	public BigDecimal getMcInRupee() {
		return mcInRupee;
	}

	public void setMcInRupee(BigDecimal mcInRupee) {
		this.mcInRupee = mcInRupee;
	}

	public String getOrnPrintFormat() {
		return ornPrintFormat;
	}

	public void setOrnPrintFormat(String ornPrintFormat) {
		this.ornPrintFormat = ornPrintFormat;
	}

	public BigDecimal getItemHMCharges() {
		return itemHMCharges;
	}

	public void setItemHMCharges(BigDecimal itemHMCharges) {
		this.itemHMCharges = itemHMCharges;
	}
	
}