 package com.jewellery.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( 
		name="JewelStock", 
		schema="jewellery"		
	  )
public class JewelStock implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer jewel_StockId;
	
	private String stock_TransType;
	private String stock_StockType;
	private String stock_TransNO;
	private Date stock_TransDate;
	private Date stock_ItemStockDate;
	private String stock_ItemCode;
	private String stock_CategoryName;
	private String stock_SubCategoryName;
	private String stock_ItemName;
	private String stock_MetalUsed;
	private String stock_MetalType;
			
	private Integer stock_OPQty;
	private Integer stock_CLQty;
	private Integer stock_CLPieces;
	private Integer stock_OPTotalPieces;
	private Integer stock_CLTotalPieces;
	
	private BigDecimal stock_OPGrossWeight = new BigDecimal("0.000");
	private BigDecimal stock_OPNetWeight = new BigDecimal("0.000");
	private BigDecimal stock_OPTotalGrossWeight = new BigDecimal("0.000");
	private BigDecimal stock_CLGrossWeight = new BigDecimal("0.000");
	private BigDecimal stock_CLNetWeight = new BigDecimal("0.000");
	private BigDecimal stock_CLTotalGrossWeight = new BigDecimal("0.000");
	
	private String stock_StoneName;
	
	private BigDecimal stock_StoneWeight = new BigDecimal("0.000");
	private BigDecimal stock_StoneRatePerCarat = new BigDecimal("0.00");
	private BigDecimal stock_StoneCost = new BigDecimal("0.00");
	
	private Integer stock_StonePieces;
	
	private BigDecimal stock_BullionRate = new BigDecimal("0.00");
	private BigDecimal stock_VAPercent = new BigDecimal("0.00");
	private BigDecimal stock_LessPercent = new BigDecimal("0.00");
	private BigDecimal stock_MCInRupee = new BigDecimal("0.00");
	private BigDecimal stock_MCPerGram = new BigDecimal("0.00");
	private BigDecimal stock_MCAmount = new BigDecimal("0.00");
	private BigDecimal stock_Tax = new BigDecimal("0.00");
	private BigDecimal stock_ItemHMCharges = new BigDecimal("0.00");
	
	private String stock_ManuSeal;
	private String stock_ItemSeal;
	
	
	public JewelStock() {
		
	}

	@Column( name="jewel_StockId", nullable=false, unique=true)
	public Integer getJewel_StockId() {
		return jewel_StockId;
	}


	public void setJewel_StockId(Integer jewel_StockId) {
		this.jewel_StockId = jewel_StockId;
	}

	@Column( name="stock_TransType", nullable=true, unique=false)
	public String getStock_TransType() {
		return stock_TransType;
	}

	public void setStock_TransType(String stock_TransType) {
		this.stock_TransType = stock_TransType;
	}

	@Column( name="stock_StockType", nullable=true, unique=false)
	public String getStock_StockType() {
		return stock_StockType;
	}
	
	public void setStock_StockType(String stock_StockType) {
		this.stock_StockType = stock_StockType;
	}

	@Column( name="stock_transNO", nullable=true, unique=false)
	public String getStock_TransNO() {
		return stock_TransNO;
	}

	public void setStock_TransNO(String stock_TransNO) {
		this.stock_TransNO = stock_TransNO;
	}

	
	@Column( name="stock_transDate", nullable=true, unique=false)
	public Date getStock_TransDate() {
		return stock_TransDate;
	}

	public void setStock_TransDate(Date stock_TransDate) {
		this.stock_TransDate = stock_TransDate;
	}

	@Column( name="stock_ItemStockDate", nullable=true, unique=false)
	public Date getStock_ItemStockDate() {
		return stock_ItemStockDate;
	}


	public void setStock_ItemStockDate(Date stock_ItemStockDate) {
		this.stock_ItemStockDate = stock_ItemStockDate;
	}

	@Column( name="stock_ItemCode", nullable=true, unique=false)
	public String getStock_ItemCode() {
		return stock_ItemCode;
	}


	public void setStock_ItemCode(String stock_ItemCode) {
		this.stock_ItemCode = stock_ItemCode;
	}

	@Column( name="stock_CategoryName", nullable=true, unique=false)
	public String getStock_CategoryName() {
		return stock_CategoryName;
	}


	public void setStock_CategoryName(String stock_CategoryName) {
		this.stock_CategoryName = stock_CategoryName;
	}

	@Column( name="stock_SubCategoryName", nullable=true, unique=false)
	public String getStock_SubCategoryName() {
		return stock_SubCategoryName;
	}


	public void setStock_SubCategoryName(String stock_SubCategoryName) {
		this.stock_SubCategoryName = stock_SubCategoryName;
	}

	@Column( name="stock_ItemName", nullable=true, unique=false)
	public String getStock_ItemName() {
		return stock_ItemName;
	}


	public void setStock_ItemName(String stock_ItemName) {
		this.stock_ItemName = stock_ItemName;
	}

	@Column( name="stock_MetalUsed", nullable=true, unique=false)
	public String getStock_MetalUsed() {
		return stock_MetalUsed;
	}


	public void setStock_MetalUsed(String stock_MetalUsed) {
		this.stock_MetalUsed = stock_MetalUsed;
	}

	@Column( name="stock_MetalType", nullable=true, unique=false)
	public String getStock_MetalType() {
		return stock_MetalType;
	}


	public void setStock_MetalType(String stock_MetalType) {
		this.stock_MetalType = stock_MetalType;
	}

	@Column( name="stock_OPQty", nullable=true, unique=false)
	public Integer getStock_OPQty() {
		return stock_OPQty;
	}

	
	public void setStock_OPQty(Integer stock_OPQty) {
		this.stock_OPQty = stock_OPQty;
	}

	@Column( name="stock_CLQty", nullable=true, unique=false)
	public Integer getStock_CLQty() {
		return stock_CLQty;
	}


	public void setStock_CLQty(Integer stock_CLQty) {
		this.stock_CLQty = stock_CLQty;
	}

	@Column( name="stock_CLPieces", nullable=true, unique=false)
	public Integer getStock_CLPieces() {
		return stock_CLPieces;
	}


	public void setStock_CLPieces(Integer stock_CLPieces) {
		this.stock_CLPieces = stock_CLPieces;
	}

	@Column( name="stock_OPTotalPieces", nullable=true, unique=false)
	public Integer getStock_OPTotalPieces() {
		return stock_OPTotalPieces;
	}


	public void setStock_OPTotalPieces(Integer stock_OPTotalPieces) {
		this.stock_OPTotalPieces = stock_OPTotalPieces;
	}

	@Column( name="stock_CLTotalPieces", nullable=true, unique=false)
	public Integer getStock_CLTotalPieces() {
		return stock_CLTotalPieces;
	}

	
	public void setStock_CLTotalPieces(Integer stock_CLTotalPieces) {
		this.stock_CLTotalPieces = stock_CLTotalPieces;
	}

	@Column( name="stock_OPGrossWeight", precision=19, scale=3, nullable=true, unique=false)
	public BigDecimal getStock_OPGrossWeight() {
		return stock_OPGrossWeight;
	}


	public void setStock_OPGrossWeight(BigDecimal stock_OPGrossWeight) {
		this.stock_OPGrossWeight = stock_OPGrossWeight;
	}

	@Column( name="stock_OPNetWeight", precision=19, scale=3, nullable=true, unique=false)
	public BigDecimal getStock_OPNetWeight() {
		return stock_OPNetWeight;
	}


	public void setStock_OPNetWeight(BigDecimal stock_OPNetWeight) {
		this.stock_OPNetWeight = stock_OPNetWeight;
	}

	@Column( name="stock_OPTotalGrossWeight", precision=19, scale=3, nullable=true, unique=false)
	public BigDecimal getStock_OPTotalGrossWeight() {
		return stock_OPTotalGrossWeight;
	}


	public void setStock_OPTotalGrossWeight(BigDecimal stock_OPTotalGrossWeight) {
		this.stock_OPTotalGrossWeight = stock_OPTotalGrossWeight;
	}

	@Column( name="stock_CLGrossWeight", precision=19, scale=3, nullable=true, unique=false)
	public BigDecimal getStock_CLGrossWeight() {
		return stock_CLGrossWeight;
	}


	public void setStock_CLGrossWeight(BigDecimal stock_CLGrossWeight) {
		this.stock_CLGrossWeight = stock_CLGrossWeight;
	}

	@Column( name="stock_CLNetWeight", precision=19, scale=3, nullable=true, unique=false)
	public BigDecimal getStock_CLNetWeight() {
		return stock_CLNetWeight;
	}


	public void setStock_CLNetWeight(BigDecimal stock_CLNetWeight) {
		this.stock_CLNetWeight = stock_CLNetWeight;
	}

	@Column( name="stock_CLTotalGrossWeight", precision=19, scale=3, nullable=true, unique=false)
	public BigDecimal getStock_CLTotalGrossWeight() {
		return stock_CLTotalGrossWeight;
	}


	public void setStock_CLTotalGrossWeight(BigDecimal stock_CLTotalGrossWeight) {
		this.stock_CLTotalGrossWeight = stock_CLTotalGrossWeight;
	}

	@Column( name="stock_StoneName", nullable=true, unique=false)
	public String getStock_StoneName() {
		return stock_StoneName;
	}


	public void setStock_StoneName(String stock_StoneName) {
		this.stock_StoneName = stock_StoneName;
	}

	@Column( name="stock_StoneWeight", precision=19, scale=3, nullable=true, unique=false)
	public BigDecimal getStock_StoneWeight() {
		return stock_StoneWeight;
	}


	public void setStock_StoneWeight(BigDecimal stock_StoneWeight) {
		this.stock_StoneWeight = stock_StoneWeight;
	}

	@Column( name="stock_StoneRatePerCarat", precision=19, scale=2, nullable=true, unique=false)
	public BigDecimal getStock_StoneRatePerCarat() {
		return stock_StoneRatePerCarat;
	}


	public void setStock_StoneRatePerCarat(BigDecimal stock_StoneRatePerCarat) {
		this.stock_StoneRatePerCarat = stock_StoneRatePerCarat;
	}

	@Column( name="stock_StoneCost", precision=19, scale=2, nullable=true, unique=false)
	public BigDecimal getStock_StoneCost() {
		return stock_StoneCost;
	}


	public void setStock_StoneCost(BigDecimal stock_StoneCost) {
		this.stock_StoneCost = stock_StoneCost;
	}

	@Column( name="stock_StonePieces", precision=19, scale=2, nullable=true, unique=false)
	public Integer getStock_StonePieces() {
		return stock_StonePieces;
	}


	public void setStock_StonePieces(Integer stock_StonePieces) {
		this.stock_StonePieces = stock_StonePieces;
	}

	@Column( name="stock_BullionRate", precision=19, scale=2, nullable=true, unique=false)
	public BigDecimal getStock_BullionRate() {
		return stock_BullionRate;
	}


	public void setStock_BullionRate(BigDecimal stock_BullionRate) {
		this.stock_BullionRate = stock_BullionRate;
	}

	@Column( name="stock_VAPercent", precision=19, scale=2, nullable=true, unique=false)
	public BigDecimal getStock_VAPercent() {
		return stock_VAPercent;
	}


	public void setStock_VAPercent(BigDecimal stock_VAPercent) {
		this.stock_VAPercent = stock_VAPercent;
	}

	@Column( name="stock_LessPercent", precision=19, scale=2, nullable=true, unique=false)
	public BigDecimal getStock_LessPercent() {
		return stock_LessPercent;
	}


	public void setStock_LessPercent(BigDecimal stock_LessPercent) {
		this.stock_LessPercent = stock_LessPercent;
	}

	@Column( name="stock_MCInRupee", precision=19, scale=2, nullable=true, unique=false)
	public BigDecimal getStock_MCInRupee() {
		return stock_MCInRupee;
	}


	public void setStock_MCInRupee(BigDecimal stock_MCInRupee) {
		this.stock_MCInRupee = stock_MCInRupee;
	}

	@Column( name="stock_MCPerGram", precision=19, scale=3, nullable=true, unique=false)
	public BigDecimal getStock_MCPerGram() {
		return stock_MCPerGram;
	}


	public void setStock_MCPerGram(BigDecimal stock_MCPerGram) {
		this.stock_MCPerGram = stock_MCPerGram;
	}

	@Column( name="stock_MCAmount", precision=19, scale=2, nullable=true, unique=false)
	public BigDecimal getStock_MCAmount() {
		return stock_MCAmount;
	}


	public void setStock_MCAmount(BigDecimal stock_MCAmount) {
		this.stock_MCAmount = stock_MCAmount;
	}

	@Column( name="stock_Tax", precision=19, scale=2, nullable=true, unique=false)
	public BigDecimal getStock_Tax() {
		return stock_Tax;
	}


	public void setStock_Tax(BigDecimal stock_Tax) {
		this.stock_Tax = stock_Tax;
	}

	@Column( name="stock_ItemHMCharges", precision=19, scale=2, nullable=true, unique=false)
	public BigDecimal getStock_ItemHMCharges() {
		return stock_ItemHMCharges;
	}


	public void setStock_ItemHMCharges(BigDecimal stock_ItemHMCharges) {
		this.stock_ItemHMCharges = stock_ItemHMCharges;
	}

	@Column( name="stock_ManuSeal", nullable=true, unique=false)
	public String getStock_ManuSeal() {
		return stock_ManuSeal;
	}


	public void setStock_ManuSeal(String stock_ManuSeal) {
		this.stock_ManuSeal = stock_ManuSeal;
	}

	@Column( name="stock_ItemSeal", nullable=true, unique=false)
	public String getStock_ItemSeal() {
		return stock_ItemSeal;
	}


	public void setStock_ItemSeal(String stock_ItemSeal) {
		this.stock_ItemSeal = stock_ItemSeal;
	}
}
