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

//select from class name and class name is case sensitive
@Entity
@NamedQueries({
		@NamedQuery(name = "getSalesOrderByCName", query = "from SalesOrder where customerName = :customerName"),
		@NamedQuery(name = "listSalesOrder", query = "from SalesOrder order by salesOrderId"),
		@NamedQuery(name = "PendingSalesOrder", query = "from SalesOrder where salesDate < CURRENT_DATE and orderStatus ='Accepted'")

})
public class SalesOrder implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer salesOrderId;
	private Date orderDate;
	private Date salesDate;
	private String orderStatus;
	private String customerName;
	private String salesmanName;
	private String rateFix;

	private String itemName1;
	private String bullionType1;
	private BigDecimal grossWeight1 = new BigDecimal("0.000");
	private BigDecimal netWeight1 = new BigDecimal("0.000");
	private BigDecimal bullionRate1 = new BigDecimal("0.00");
	private BigDecimal wastage1 = new BigDecimal("0.000");
	private BigDecimal mcPerGram1 = new BigDecimal("0.00");
	private int noOfPcs1;
	private String stoneDetails1;
	private String description;
	private BigDecimal stoneWeight1 = new BigDecimal("0.000");
	private BigDecimal stoneRate1 = new BigDecimal("0.00");
	private BigDecimal stoneCost1 = new BigDecimal("0.00");
	private BigDecimal itemCost1 = new BigDecimal("0.00");

	private String itemName2;
	private String bullionType2;
	private BigDecimal grossWeight2 = new BigDecimal("0.000");
	private BigDecimal netWeight2 = new BigDecimal("0.00");
	private BigDecimal bullionRate2 = new BigDecimal("0.00");
	private BigDecimal wastage2 = new BigDecimal("0.000");
	private BigDecimal mcPerGram2 = new BigDecimal("0.00");
	private int noOfPcs2;
	private String stoneDetails2;
	private String description2;
	private BigDecimal stoneWeight2 = new BigDecimal("0.000");
	private BigDecimal stoneRate2 = new BigDecimal("0.00");
	private BigDecimal stoneCost2 = new BigDecimal("0.00");
	private BigDecimal itemCost2 = new BigDecimal("0.00");

	private String itemName3;
	private String bullionType3;
	private BigDecimal grossWeight3 = new BigDecimal("0.000");
	private BigDecimal netWeight3 = new BigDecimal("0.000");
	private BigDecimal bullionRate3 = new BigDecimal("0.00");
	private BigDecimal wastage3 = new BigDecimal("0.00");
	private BigDecimal mcPerGram3 = new BigDecimal("0.00");
	private int noOfPcs3;
	private String stoneDetails3;
	private String description3;
	private BigDecimal stoneWeight3 = new BigDecimal("0.000");
	private BigDecimal stoneRate3 = new BigDecimal("0.00");
	private BigDecimal stoneCost3 = new BigDecimal("0.00");
	private BigDecimal itemCost3 = new BigDecimal("0.00");

	private String itemName4;
	private String bullionType4;
	private BigDecimal grossWeight4 = new BigDecimal("0.000");
	private BigDecimal netWeight4 = new BigDecimal("0.000");
	private BigDecimal bullionRate4 = new BigDecimal("0.00");
	private BigDecimal wastage4 = new BigDecimal("0.000");
	private BigDecimal mcPerGram4 = new BigDecimal("0.00");
	private int noOfPcs4;
	private String stoneDetails4;
	private String description4;
	private BigDecimal stoneWeight4 = new BigDecimal("0.000");
	private BigDecimal stoneRate4 = new BigDecimal("0.00");
	private BigDecimal stoneCost4 = new BigDecimal("0.00");
	private BigDecimal itemCost4 = new BigDecimal("0.00");

	private String itemName5;
	private String bullionType5;
	private BigDecimal grossWeight5 = new BigDecimal("0.000");
	private BigDecimal netWeight5 = new BigDecimal("0.000");
	private BigDecimal bullionRate5 = new BigDecimal("0.00");
	private BigDecimal wastage5 = new BigDecimal("0.000");
	private BigDecimal mcPerGram5 = new BigDecimal("0.00");
	private int noOfPcs5;
	private String stoneDetails5;
	private BigDecimal stoneWeight5 = new BigDecimal("0.000");
	private BigDecimal stoneRate5 = new BigDecimal("0.00");
	private BigDecimal stoneCost5 = new BigDecimal("0.00");
	private BigDecimal itemCost5 = new BigDecimal("0.00");
	private BigDecimal advance = new BigDecimal("0.00");
	private String description5;
	private BigDecimal totalAmount = new BigDecimal("0.00");
	private BigDecimal netAmount = new BigDecimal("0.00");

	
	private String cashPaymentSO;
	private String cardPaymentSO;
	private String chequePaymentSO;
	private String voucherPaymentSO;
	
	private BigDecimal cashAmountSO = new BigDecimal("0.00");
	private String cashBankSO;
	private String chequeBankSO;
	private String chequeDetailsSO;
	private BigDecimal chequeAmountSO = new BigDecimal("0.00");
	private String cardBankSO;
	private String cardDetailsSO;
	private BigDecimal cardAmountSO = new BigDecimal("0.00");
	private String voucherListSO;
	private String voucherDetailsSO;
	private BigDecimal voucherAmountSO = new BigDecimal("0.00");
	private BigDecimal exchangeGrams = new BigDecimal("0.000");//add new on 21/12/12
	private BigDecimal exchangeAmount = new BigDecimal("0.00");//add new on 21/12/12
	private BigDecimal rateFixAmount = new BigDecimal("0.00");//add new on 16/1/13
	private BigDecimal rateFixGrams = new BigDecimal("0.00");//add new on 16/1/13
	
	
	
	public SalesOrder() {
	}

	@Id
	@GeneratedValue
	public Integer getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(Integer salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(Date salesDate) {  
		this.salesDate = salesDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}

	public String getRateFix() {
		return rateFix;
	}

	public void setRateFix(String rateFix) {
		this.rateFix = rateFix;
	}

	public String getItemName1() {
		return itemName1;
	}

	public void setItemName1(String itemName1) {
		this.itemName1 = itemName1;
	}

	public String getBullionType1() {
		return bullionType1;
	}

	public void setBullionType1(String bullionType1) {
		this.bullionType1 = bullionType1;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getGrossWeight1() {
		return grossWeight1;
	}

	public void setGrossWeight1(BigDecimal grossWeight1) {
		this.grossWeight1 = grossWeight1;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getNetWeight1() {
		return netWeight1;
	}

	public void setNetWeight1(BigDecimal netWeight1) {
		this.netWeight1 = netWeight1;
	}

	public BigDecimal getBullionRate1() {
		return bullionRate1;
	}

	public void setBullionRate1(BigDecimal bullionRate1) {
		this.bullionRate1 = bullionRate1;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getWastage1() {
		return wastage1;
	}

	public void setWastage1(BigDecimal wastage1) {
		this.wastage1 = wastage1;
	}

	public BigDecimal getMcPerGram1() {
		return mcPerGram1;
	}

	public void setMcPerGram1(BigDecimal mcPerGram1) {
		this.mcPerGram1 = mcPerGram1;
	}

	public int getNoOfPcs1() {
		return noOfPcs1;
	}

	public void setNoOfPcs1(int noOfPcs1) {
		this.noOfPcs1 = noOfPcs1;
	}

	public String getStoneDetails1() {
		return stoneDetails1;
	}

	public void setStoneDetails1(String stoneDetails1) {
		this.stoneDetails1 = stoneDetails1;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getStoneWeight1() {
		return stoneWeight1;
	}

	public void setStoneWeight1(BigDecimal stoneWeight1) {
		this.stoneWeight1 = stoneWeight1;
	}

	public BigDecimal getStoneRate1() {
		return stoneRate1;
	}

	public void setStoneRate1(BigDecimal stoneRate1) {
		this.stoneRate1 = stoneRate1;
	}

	public BigDecimal getStoneCost1() {
		return stoneCost1;
	}

	public void setStoneCost1(BigDecimal stoneCost1) {
		this.stoneCost1 = stoneCost1;
	}

	public BigDecimal getItemCost1() {
		return itemCost1;
	}

	public void setItemCost1(BigDecimal itemCost1) {
		this.itemCost1 = itemCost1;
	}

	public String getItemName2() {
		return itemName2;
	}

	public void setItemName2(String itemName2) {
		this.itemName2 = itemName2;
	}

	public String getBullionType2() {
		return bullionType2;
	}

	public void setBullionType2(String bullionType2) {
		this.bullionType2 = bullionType2;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getGrossWeight2() {
		return grossWeight2;
	}

	public void setGrossWeight2(BigDecimal grossWeight2) {
		this.grossWeight2 = grossWeight2;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getNetWeight2() {
		return netWeight2;
	}

	public void setNetWeight2(BigDecimal netWeight2) {
		this.netWeight2 = netWeight2;
	}

	public BigDecimal getBullionRate2() {
		return bullionRate2;
	}

	public void setBullionRate2(BigDecimal bullionRate2) {
		this.bullionRate2 = bullionRate2;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getWastage2() {
		return wastage2;
	}

	public void setWastage2(BigDecimal wastage2) {
		this.wastage2 = wastage2;
	}

	public BigDecimal getMcPerGram2() {
		return mcPerGram2;
	}

	public void setMcPerGram2(BigDecimal mcPerGram2) {
		this.mcPerGram2 = mcPerGram2;
	}

	public int getNoOfPcs2() {
		return noOfPcs2;
	}

	public void setNoOfPcs2(int noOfPcs2) {
		this.noOfPcs2 = noOfPcs2;
	}

	public String getStoneDetails2() {
		return stoneDetails2;
	}

	public void setStoneDetails2(String stoneDetails2) {
		this.stoneDetails2 = stoneDetails2;
	}

	public BigDecimal getStoneWeight2() {
		return stoneWeight2;
	}

	public void setStoneWeight2(BigDecimal stoneWeight2) {
		this.stoneWeight2 = stoneWeight2;
	}

	public BigDecimal getStoneRate2() {
		return stoneRate2;
	}

	public void setStoneRate2(BigDecimal stoneRate2) {
		this.stoneRate2 = stoneRate2;
	}

	public BigDecimal getStoneCost2() {
		return stoneCost2;
	}

	public void setStoneCost2(BigDecimal stoneCost2) {
		this.stoneCost2 = stoneCost2;
	}

	public BigDecimal getItemCost2() {
		return itemCost2;
	}

	public void setItemCost2(BigDecimal itemCost2) {
		this.itemCost2 = itemCost2;
	}

	public String getItemName3() {
		return itemName3;
	}

	public void setItemName3(String itemName3) {
		this.itemName3 = itemName3;
	}

	public String getBullionType3() {
		return bullionType3;
	}

	public void setBullionType3(String bullionType3) {
		this.bullionType3 = bullionType3;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getGrossWeight3() {
		return grossWeight3;
	}

	public void setGrossWeight3(BigDecimal grossWeight3) {
		this.grossWeight3 = grossWeight3;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getNetWeight3() {
		return netWeight3;
	}

	public void setNetWeight3(BigDecimal netWeight3) {
		this.netWeight3 = netWeight3;
	}

	public BigDecimal getBullionRate3() {
		return bullionRate3;
	}

	public void setBullionRate3(BigDecimal bullionRate3) {
		this.bullionRate3 = bullionRate3;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getWastage3() {
		return wastage3;
	}

	public void setWastage3(BigDecimal wastage3) {
		this.wastage3 = wastage3;
	}

	public BigDecimal getMcPerGram3() {
		return mcPerGram3;
	}

	public void setMcPerGram3(BigDecimal mcPerGram3) {
		this.mcPerGram3 = mcPerGram3;
	}

	public int getNoOfPcs3() {
		return noOfPcs3;
	}

	public void setNoOfPcs3(int noOfPcs3) {
		this.noOfPcs3 = noOfPcs3;
	}

	public String getStoneDetails3() {
		return stoneDetails3;
	}

	public void setStoneDetails3(String stoneDetails3) {
		this.stoneDetails3 = stoneDetails3;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getStoneWeight3() {
		return stoneWeight3;
	}

	public void setStoneWeight3(BigDecimal stoneWeight3) {
		this.stoneWeight3 = stoneWeight3;
	}

	public BigDecimal getStoneRate3() {
		return stoneRate3;
	}

	public void setStoneRate3(BigDecimal stoneRate3) {
		this.stoneRate3 = stoneRate3;
	}

	public BigDecimal getStoneCost3() {
		return stoneCost3;
	}

	public void setStoneCost3(BigDecimal stoneCost3) {
		this.stoneCost3 = stoneCost3;
	}

	public BigDecimal getItemCost3() {
		return itemCost3;
	}

	public void setItemCost3(BigDecimal itemCost3) {
		this.itemCost3 = itemCost3;
	}

	public String getItemName4() {
		return itemName4;
	}

	public void setItemName4(String itemName4) {
		this.itemName4 = itemName4;
	}

	public String getBullionType4() {
		return bullionType4;
	}

	public void setBullionType4(String bullionType4) {
		this.bullionType4 = bullionType4;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getGrossWeight4() {
		return grossWeight4;
	}

	public void setGrossWeight4(BigDecimal grossWeight4) {
		this.grossWeight4 = grossWeight4;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getNetWeight4() {
		return netWeight4;
	}

	public void setNetWeight4(BigDecimal netWeight4) {
		this.netWeight4 = netWeight4;
	}

	public BigDecimal getBullionRate4() {
		return bullionRate4;
	}

	public void setBullionRate4(BigDecimal bullionRate4) {
		this.bullionRate4 = bullionRate4;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getWastage4() {
		return wastage4;
	}

	public void setWastage4(BigDecimal wastage4) {
		this.wastage4 = wastage4;
	}

	public BigDecimal getMcPerGram4() {
		return mcPerGram4;
	}

	public void setMcPerGram4(BigDecimal mcPerGram4) {
		this.mcPerGram4 = mcPerGram4;
	}

	public int getNoOfPcs4() {
		return noOfPcs4;
	}

	public void setNoOfPcs4(int noOfPcs4) {
		this.noOfPcs4 = noOfPcs4;
	}

	public String getStoneDetails4() {
		return stoneDetails4;
	}

	public void setStoneDetails4(String stoneDetails4) {
		this.stoneDetails4 = stoneDetails4;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getStoneWeight4() {
		return stoneWeight4;
	}

	public void setStoneWeight4(BigDecimal stoneWeight4) {
		this.stoneWeight4 = stoneWeight4;
	}

	public BigDecimal getStoneRate4() {
		return stoneRate4;
	}

	public void setStoneRate4(BigDecimal stoneRate4) {
		this.stoneRate4 = stoneRate4;
	}

	public BigDecimal getStoneCost4() {
		return stoneCost4;
	}

	public void setStoneCost4(BigDecimal stoneCost4) {
		this.stoneCost4 = stoneCost4;
	}

	public BigDecimal getItemCost4() {
		return itemCost4;
	}

	public void setItemCost4(BigDecimal itemCost4) {
		this.itemCost4 = itemCost4;
	}

	public String getItemName5() {
		return itemName5;
	}

	public void setItemName5(String itemName5) {
		this.itemName5 = itemName5;
	}

	public String getBullionType5() {
		return bullionType5;
	}

	public void setBullionType5(String bullionType5) {
		this.bullionType5 = bullionType5;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getGrossWeight5() {
		return grossWeight5;
	}

	public void setGrossWeight5(BigDecimal grossWeight5) {
		this.grossWeight5 = grossWeight5;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getNetWeight5() {
		return netWeight5;
	}

	public void setNetWeight5(BigDecimal netWeight5) {
		this.netWeight5 = netWeight5;
	}

	public BigDecimal getBullionRate5() {
		return bullionRate5;
	}

	public void setBullionRate5(BigDecimal bullionRate5) {
		this.bullionRate5 = bullionRate5;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getWastage5() {
		return wastage5;
	}

	public void setWastage5(BigDecimal wastage5) {
		this.wastage5 = wastage5;
	}

	public BigDecimal getMcPerGram5() {
		return mcPerGram5;
	}

	public void setMcPerGram5(BigDecimal mcPerGram5) {
		this.mcPerGram5 = mcPerGram5;
	}

	public int getNoOfPcs5() {
		return noOfPcs5;
	}

	public void setNoOfPcs5(int noOfPcs5) {
		this.noOfPcs5 = noOfPcs5;
	}

	public String getStoneDetails5() {
		return stoneDetails5;
	}

	public void setStoneDetails5(String stoneDetails5) {
		this.stoneDetails5 = stoneDetails5;
	}

	@Column(precision = 19, scale = 3)
	public BigDecimal getStoneWeight5() {
		return stoneWeight5;
	}

	public void setStoneWeight5(BigDecimal stoneWeight5) {
		this.stoneWeight5 = stoneWeight5;
	}

	public BigDecimal getStoneRate5() {
		return stoneRate5;
	}

	public void setStoneRate5(BigDecimal stoneRate5) {
		this.stoneRate5 = stoneRate5;
	}

	public BigDecimal getStoneCost5() {
		return stoneCost5;
	}

	public void setStoneCost5(BigDecimal stoneCost5) {
		this.stoneCost5 = stoneCost5;
	}

	public BigDecimal getItemCost5() {
		return itemCost5;
	}

	public void setItemCost5(BigDecimal itemCost5) {
		this.itemCost5 = itemCost5;
	}

	@Column(nullable = false)
	public BigDecimal getAdvance() {
		return advance;
	}

	public void setAdvance(BigDecimal advance) {
		this.advance = advance;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(nullable = false)
	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	@Column(nullable = false)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCashPaymentSO() {
		return cashPaymentSO;
	}

	public void setCashPaymentSO(String cashPaymentSO) {
		this.cashPaymentSO = cashPaymentSO;
	}

	public String getCardPaymentSO() {
		return cardPaymentSO;
	}

	public void setCardPaymentSO(String cardPaymentSO) {
		this.cardPaymentSO = cardPaymentSO;
	}

	public String getChequePaymentSO() {
		return chequePaymentSO;
	}

	public void setChequePaymentSO(String chequePaymentSO) {
		this.chequePaymentSO = chequePaymentSO;
	}

	public String getVoucherPaymentSO() {
		return voucherPaymentSO;
	}

	public void setVoucherPaymentSO(String voucherPaymentSO) {
		this.voucherPaymentSO = voucherPaymentSO;
	}

	public BigDecimal getCashAmountSO() {
		return cashAmountSO;
	}

	public void setCashAmountSO(BigDecimal cashAmountSO) {
		this.cashAmountSO = cashAmountSO;
	}

	public String getChequeBankSO() {
		return chequeBankSO;
	}

	public void setChequeBankSO(String chequeBankSO) {
		this.chequeBankSO = chequeBankSO;
	}

	public String getChequeDetailsSO() {
		return chequeDetailsSO;
	}

	public void setChequeDetailsSO(String chequeDetailsSO) {
		this.chequeDetailsSO = chequeDetailsSO;
	}

	public BigDecimal getChequeAmountSO() {
		return chequeAmountSO;
	}

	public void setChequeAmountSO(BigDecimal chequeAmountSO) {
		this.chequeAmountSO = chequeAmountSO;
	}

	public String getCardBankSO() {
		return cardBankSO;
	}

	public void setCardBankSO(String cardBankSO) {
		this.cardBankSO = cardBankSO;
	}

	public String getCardDetailsSO() {
		return cardDetailsSO;
	}

	public void setCardDetailsSO(String cardDetailsSO) {
		this.cardDetailsSO = cardDetailsSO;
	}

	public BigDecimal getCardAmountSO() {
		return cardAmountSO;
	}

	public void setCardAmountSO(BigDecimal cardAmountSO) {
		this.cardAmountSO = cardAmountSO;
	}

	public String getVoucherListSO() {
		return voucherListSO;
	}

	public void setVoucherListSO(String voucherListSO) {
		this.voucherListSO = voucherListSO;
	}

	public String getVoucherDetailsSO() {
		return voucherDetailsSO;
	}

	public void setVoucherDetailsSO(String voucherDetailsSO) {
		this.voucherDetailsSO = voucherDetailsSO;
	}

	public BigDecimal getVoucherAmountSO() {
		return voucherAmountSO;
	}

	public void setVoucherAmountSO(BigDecimal voucherAmountSO) {
		this.voucherAmountSO = voucherAmountSO;
	}

	public String getCashBankSO() {
		return cashBankSO;
	}

	public void setCashBankSO(String cashBankSO) {
		this.cashBankSO = cashBankSO;
	}

	public String getDescription2() {
		return description2;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	public String getDescription3() {
		return description3;
	}

	public void setDescription3(String description3) {
		this.description3 = description3;
	}

	public String getDescription4() {
		return description4;
	}

	public void setDescription4(String description4) {
		this.description4 = description4;
	}

	public String getDescription5() {
		return description5;
	}

	public void setDescription5(String description5) {
		this.description5 = description5;
	}

	public BigDecimal getExchangeGrams() {
		return exchangeGrams;
	}

	public void setExchangeGrams(BigDecimal exchangeGrams) {
		this.exchangeGrams = exchangeGrams;
	}

	public BigDecimal getExchangeAmount() {
		return exchangeAmount;
	}

	public void setExchangeAmount(BigDecimal exchangeAmount) {
		this.exchangeAmount = exchangeAmount;
	}

	public BigDecimal getRateFixAmount() {
		return rateFixAmount;
	}

	public void setRateFixAmount(BigDecimal rateFixAmount) {
		this.rateFixAmount = rateFixAmount;
	}

	public BigDecimal getRateFixGrams() {
		return rateFixGrams;
	}

	public void setRateFixGrams(BigDecimal rateFixGrams) {
		this.rateFixGrams = rateFixGrams;
	}

	
	

}