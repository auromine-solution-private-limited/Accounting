package com.jewellery.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity                                                                                                      
@NamedQueries({                                                                                              
	@NamedQuery(name = "getLotStocks", query = "from LotStock where lotItemName = :lotItemName"),
	@NamedQuery(name = "getGoldLotStock", query = "from LotStock where lotItemName = 'GoldLotStock' AND lotType='Add Lot Stock'"),	
	@NamedQuery(name = "getSilverLotStock", query = "from LotStock where lotItemName = 'SilverLotStock' AND lotType='Add Lot Stock'"),
    @NamedQuery(name = "listLotStock", query = "from LotStock order by lotId")                                          
})     


public class LotStock implements Serializable {
	
	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer lotId; 
	private Date lotDate;
	private String lotItemName; 
	private String lotType;
	private String lotCode;
	private BigDecimal grossWeight =new BigDecimal("0.000");
	private BigDecimal netWeight =new BigDecimal("0.000");
	private int quantity;
	private BigDecimal totalGrossWeight =new BigDecimal("0.000");
	private int totalQuantity;
	private String description;
		
	
	public Integer getLotId() {
		return lotId;
	}
	public void setLotId(Integer lotId) {
		this.lotId = lotId;
	}
	
	public Date getLotDate() {
		return lotDate;
	}
	public void setLotDate(Date lotDate) {
		this.lotDate = lotDate;
	}
	public String getLotItemName() {
		return lotItemName;
	}
	public void setLotItemName(String lotItemName) {
		this.lotItemName = lotItemName;
	}
	public String getLotType() {
		return lotType;
	}
	public void setLotType(String lotType) {
		this.lotType = lotType;
	}
	public String getLotCode() {
		return lotCode;
	}
	public void setLotCode(String lotCode) {
		this.lotCode = lotCode;
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Column(precision = 19, scale = 3)
	public BigDecimal getTotalGrossWeight() {
		return totalGrossWeight;
	}
	public void setTotalGrossWeight(BigDecimal totalGrossWeight) {
		this.totalGrossWeight = totalGrossWeight;
	}
	public int getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
}
