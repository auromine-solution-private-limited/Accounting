package com.jewellery.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@javax.persistence.NamedQueries ({ @NamedQuery(name = "listAllSales", query = "from Sales") })

@Table( name = "sales",
uniqueConstraints = { @UniqueConstraint( columnNames = { "salesTypeId"} ) } )
public class Sales {

	private boolean printInvoice; // print confirmation dialog box value
	private Integer salesId;
	private String billNo;
	private Date salesDate;
	private String salesType;
	private String cashPayment;
	private String cardPayment;
	private String chequePayment;
	private String voucherPayment;
	private String billType;
	private String customerName;
	private String salesmanName;
	private String walkIn_Name;
	private String walkIn_City;
	private String panNumber;
	private String salesTypeId;
	
	private String itemCode;
	private String itemName;
	private String categoryName;
	private String bullionType;
	private String karatInfo;
	private int numberOfPieces;
	private BigDecimal grossWeight = new BigDecimal("0.000");
	private BigDecimal netWeight = new BigDecimal("0.000");
	private BigDecimal bullionRate = new BigDecimal("0.00");
	private BigDecimal wastageByGrams = new BigDecimal("0.000");
	private BigDecimal wastageByAmount = new BigDecimal("0.000");
	private BigDecimal mcByGrams = new BigDecimal("0.000");
	private BigDecimal mcByAmount = new BigDecimal("0.00");
	private BigDecimal valueAdditionCharges = new BigDecimal("0.00");
	private String stone;
	private BigDecimal stoneRatePerCaret = new BigDecimal("0.00");
	private BigDecimal stoneCost = new BigDecimal("0.00");
	private BigDecimal amount = new BigDecimal("0.00");
	private BigDecimal less = new BigDecimal("0.00");
	private BigDecimal amountAfterLess = new BigDecimal("0.00");
	private BigDecimal lessPercentage = new BigDecimal("0.00");
	private BigDecimal lessAmount = new BigDecimal("0.00");
	private BigDecimal vtax = new BigDecimal("0.00");
	private String manufacturerSeal;
	private int totalPieces;
	private int soldQty;
	private BigDecimal salesHMCharges = new BigDecimal("0.00");
	private String remarks;
	private String metalUsed;

	private String itemCode1;
	private String itemName1;
	private String categoryName1;
	private String bullionType1;
	private String karatInfo1;
	private int numberOfPieces1;
	private BigDecimal grossWeight1 = new BigDecimal("0.000");
	private BigDecimal netWeight1 = new BigDecimal("0.000");
	private BigDecimal bullionRate1 = new BigDecimal("0.00");
	private BigDecimal wastageByGrams1 = new BigDecimal("0.000");
	private BigDecimal wastageByAmount1 = new BigDecimal("0.00");
	private BigDecimal mcByGrams1 = new BigDecimal("0.000");
	private BigDecimal mcByAmount1 = new BigDecimal("0.00");
	private BigDecimal valueAdditionCharges1 = new BigDecimal("0.00");

	private String stone1;
	private BigDecimal stoneRatePerCaret1 = new BigDecimal("0.00");
	private BigDecimal stoneCost1 = new BigDecimal("0.00");
	private BigDecimal amount1 = new BigDecimal("0.00");
	private BigDecimal less1 = new BigDecimal("0.00");
	private BigDecimal vtax1 = new BigDecimal("0.00");
	private BigDecimal amountAfterLess1 = new BigDecimal("0.00");
	private BigDecimal lessPercentage1 = new BigDecimal("0.00");
	private BigDecimal lessAmount1 = new BigDecimal("0.00");
	private String manufacturerSeal1;
	private int totalPieces1;
	private int soldQty1;
	private String metalUsed1;
	private BigDecimal salesHMCharges1 = new BigDecimal("0.00");
	private String itemCode2;
	private String itemName2;
	private String categoryName2;
	private String bullionType2;
	private String karatInfo2;
	private int numberOfPieces2;
	private BigDecimal grossWeight2 = new BigDecimal("0.000");
	private BigDecimal netWeight2 = new BigDecimal("0.000");
	private BigDecimal bullionRate2 = new BigDecimal("0.00");
	private BigDecimal wastageByGrams2 = new BigDecimal("0.000");
	private BigDecimal wastageByAmount2 = new BigDecimal("0.00");
	private BigDecimal mcByGrams2 = new BigDecimal("0.000");
	private BigDecimal mcByAmount2 = new BigDecimal("0.00");
	private BigDecimal valueAdditionCharges2 = new BigDecimal("0.00");
	private String stone2;
	private BigDecimal stoneRatePerCaret2 = new BigDecimal("0.00");
	private BigDecimal stoneCost2 = new BigDecimal("0.00");
	private BigDecimal amount2 = new BigDecimal("0.00");
	private BigDecimal less2 = new BigDecimal("0.00");
	private BigDecimal amountAfterLess2 = new BigDecimal("0.00");
	private BigDecimal vtax2 = new BigDecimal("0.00");
	private BigDecimal lessPercentage2 = new BigDecimal("0.00");
	private BigDecimal lessAmount2 = new BigDecimal("0.00");
	private String manufacturerSeal2;
	private int totalPieces2;
	private int soldQty2;
	private String metalUsed2;
	private BigDecimal salesHMCharges2 = new BigDecimal("0.00");
	private String itemCode3;
	private String itemName3;
	private String categoryName3;
	private String bullionType3;
	private String karatInfo3;
	private int numberOfPieces3;
	private BigDecimal grossWeight3 = new BigDecimal("0.000");
	private BigDecimal netWeight3 = new BigDecimal("0.000");
	private BigDecimal bullionRate3=new BigDecimal("0.00");
	private BigDecimal wastageByGrams3 = new BigDecimal("0.000");
	private BigDecimal wastageByAmount3 = new BigDecimal("0.000");
	private BigDecimal mcByGrams3 = new BigDecimal("0.000");
	private BigDecimal mcByAmount3 = new BigDecimal("0.00");
	private BigDecimal valueAdditionCharges3 = new BigDecimal("0.00");
	private String stone3;
	private BigDecimal stoneRatePerCaret3 = new BigDecimal("0.00");
	private BigDecimal stoneCost3 = new BigDecimal("0.00");
	private BigDecimal amount3 = new BigDecimal("0.00");
	private BigDecimal less3 = new BigDecimal("0.00");
	private BigDecimal amountAfterLess3 = new BigDecimal("0.00");
	private BigDecimal vtax3 = new BigDecimal("0.00");
	private BigDecimal lessPercentage3 = new BigDecimal("0.00");
	private BigDecimal lessAmount3 = new BigDecimal("0.00");
	private String manufacturerSeal3;
	private int totalPieces3;
	private int soldQty3;
	private BigDecimal salesHMCharges3 = new BigDecimal("0.00");
	private String metalUsed3;
	private String itemCode4;
	private String itemName4;
	private String categoryName4;
	private String bullionType4;
	private String karatInfo4;
	private int numberOfPieces4;
	private BigDecimal grossWeight4 = new BigDecimal("0.000");
	private BigDecimal netWeight4 = new BigDecimal("0.000");
	private BigDecimal bullionRate4 = new BigDecimal("0.00");
	private BigDecimal wastageByGrams4 = new BigDecimal("0.000");
	private BigDecimal wastageByAmount4 = new BigDecimal("0.00");
	private BigDecimal mcByGrams4 = new BigDecimal("0.000");
	private BigDecimal mcByAmount4 = new BigDecimal("0.00");
	private BigDecimal valueAdditionCharges4 = new BigDecimal("0.00");
	private String stone4;
	private BigDecimal stoneRatePerCaret4 = new BigDecimal("0.00");
	private BigDecimal stoneCost4 = new BigDecimal("0.00");
	private BigDecimal amount4 = new BigDecimal("0.00");
	private BigDecimal less4 = new BigDecimal("0.00");
	private BigDecimal amountAfterLess4 = new BigDecimal("0.00");
	private BigDecimal vtax4 = new BigDecimal("0.00");
	private BigDecimal lessPercentage4 = new BigDecimal("0.00");
	private BigDecimal lessAmount4 = new BigDecimal("0.00");
	private String manufacturerSeal4;
	private int totalPieces4;
	private int soldQty4;
	private BigDecimal salesHMCharges4 = new BigDecimal("0.00");
	private String metalUsed4;
	private BigDecimal totalAmount = new BigDecimal("0.00");
	private BigDecimal totalLess = new BigDecimal("0.00");
	private BigDecimal lessPerGram = new BigDecimal("0.000");
	private BigDecimal lessOnWeight = new BigDecimal("0.000");
	private BigDecimal netAmount = new BigDecimal("0.00");
	private BigDecimal tax = new BigDecimal("0.00");
	private BigDecimal billAmount = new BigDecimal("0.00");
	private BigDecimal advance = new BigDecimal("0.00");
	private BigDecimal balanceAmount = new BigDecimal("0.00");
	private BigDecimal adjustmentAmount = new BigDecimal("0.00");
	private BigDecimal roundOff = new BigDecimal("0.00");
	private BigDecimal balToPay = new BigDecimal("0.00");
	private BigDecimal amtRecd = new BigDecimal("0.00");
	private String exchangeBillNo;
	private BigDecimal exchangeAmount = new BigDecimal("0.00");
	private BigDecimal cashAmount = new BigDecimal("0.00");
	private String chequeBank;
	private String chequeDetails;
	private BigDecimal chequeAmount = new BigDecimal("0.00");
	private String cardBank;
	private String cardDetails;
	private BigDecimal cardAmount = new BigDecimal("0.00");
	private String voucherList;
	private String voucherDetails;
	private BigDecimal voucherAmount = new BigDecimal("0.00");
	private String formType;
	private String greetings;
	
	// Savings Scheme 
	private String SSCardNo;
	private BigDecimal SSCardAmount = new BigDecimal("0.00");
	private BigDecimal SSCardGrams = new BigDecimal("0.000");
	private String SSCardNoVal;
	
	//New Field added..
	private Integer salesOrderID;
	private BigDecimal salesOrderAmount = new BigDecimal("0.00");
	private String receiptID;
	private BigDecimal receiptAmount=new BigDecimal("0.00");
	private BigDecimal jewelDiscount = new BigDecimal("0.00");		
	private BigDecimal commissionPercent = new BigDecimal("0.00");
	private BigDecimal commissionAmount = new BigDecimal("0.00");
	private BigDecimal calCardAmount=new BigDecimal("0.00");
	private BigDecimal rateFixDifferenceAmount =new BigDecimal("0.00");//add new on 16/1/13
	private BigDecimal rateFixAmount = new BigDecimal("0.00");//add new on 16/1/13
	private BigDecimal rateFixGrams = new BigDecimal("0.00");//add new on 16/1/13
	private BigDecimal boardRateSO  = new BigDecimal("0.00");//add new on 16/1/13
	
	private String paymentMode;//added on 11-02-13 for salesReturn
	private String salesReturnStatus;//added on 11-02-13 for salesReturn
	private String salesReturnId;//added on 11-02-13 for salesReturn
	private BigDecimal salesReturnAmount = new BigDecimal("0.00");//added on 11-02-13 for salesReturn
	/***
	 * New Fields Added For SGH Client Change Request ,inorder
	 * to get the board rate of gold,silever etc in Sales Invoice.14-06-13
	 * *******/
	private BigDecimal goldOrnBoardRate  = new BigDecimal("0.00");
	private BigDecimal goldBullBoardRate  = new BigDecimal("0.00");
	private BigDecimal oldGoldOrnBoardRate  = new BigDecimal("0.00");
	private BigDecimal silverOrnBoardRate  = new BigDecimal("0.00");
	private BigDecimal oldSilverOrnBoardRate  = new BigDecimal("0.00");
	private BigDecimal silverBullBoardRate  = new BigDecimal("0.00");
	private String rateMasterDate;
   private String currentTime;
	
	private BigDecimal salesReturnMC1=new BigDecimal("0.00");
	private BigDecimal salesReturnVA1=new BigDecimal("0.00");
	private BigDecimal salesReturnHC1=new BigDecimal("0.00");
	private BigDecimal salesReturnMC2=new BigDecimal("0.00");
	private BigDecimal salesReturnVA2=new BigDecimal("0.00");
	private BigDecimal salesReturnHC2=new BigDecimal("0.00");
	private BigDecimal salesReturnMC3=new BigDecimal("0.00");
	private BigDecimal salesReturnVA3=new BigDecimal("0.00");
	private BigDecimal salesReturnHC3=new BigDecimal("0.00");
	private BigDecimal salesReturnMC4=new BigDecimal("0.00");
	private BigDecimal salesReturnVA4=new BigDecimal("0.00");
	private BigDecimal salesReturnHC4=new BigDecimal("0.00");
	
	private BigDecimal billDiscAmt=new BigDecimal("0.00");
	
	//ends 14-06-13
	
	public Sales(){
		
	}

	@Id
	@GeneratedValue
	public Integer getSalesId() {
		return salesId;
	}

	public void setSalesId(Integer salesId) {
		this.salesId = salesId;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public boolean isPrintInvoice() {
		return printInvoice;
	}

	public void setPrintInvoice(boolean printInvoice) {
		this.printInvoice = printInvoice;
	}

	

	public Date getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

	public String getSalesType() {
		return salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	public String getCashPayment() {
		return cashPayment;
	}

	public void setCashPayment(String cashPayment) {
		this.cashPayment = cashPayment;
	}

	public String getCardPayment() {
		return cardPayment;
	}

	public void setCardPayment(String cardPayment) {
		this.cardPayment = cardPayment;
	}

	public String getChequePayment() {
		return chequePayment;
	}

	public void setChequePayment(String chequePayment) {
		this.chequePayment = chequePayment;
	}

	public String getVoucherPayment() {
		return voucherPayment;
	}

	public void setVoucherPayment(String voucherPayment) {
		this.voucherPayment = voucherPayment;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
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

	public String getWalkIn_Name() {
		return walkIn_Name;
	}

	public void setWalkIn_Name(String walkIn_Name) {
		this.walkIn_Name = walkIn_Name;
	}

	public String getWalkIn_City() {
		return walkIn_City;
	}

	public void setWalkIn_City(String walkIn_City) {
		this.walkIn_City = walkIn_City;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBullionType() {
		return bullionType;
	}

	public void setBullionType(String bullionType) {
		this.bullionType = bullionType;
	}

	public String getKaratInfo() {
		return karatInfo;
	}

	public void setKaratInfo(String karatInfo) {
		this.karatInfo = karatInfo;
	}

	public int getNumberOfPieces() {
		return numberOfPieces;
	}

	public void setNumberOfPieces(int numberOfPieces) {
		this.numberOfPieces = numberOfPieces;
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
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getWastageByGrams() {
		return wastageByGrams;
	}

	public void setWastageByGrams(BigDecimal wastageByGrams) {
		this.wastageByGrams = wastageByGrams;
	}

	public BigDecimal getWastageByAmount() {
		return wastageByAmount;
	}

	public void setWastageByAmount(BigDecimal wastageByAmount) {
		this.wastageByAmount = wastageByAmount;
	}

	public BigDecimal getMcByGrams() {
		return mcByGrams;
	}

	public void setMcByGrams(BigDecimal mcByGrams) {
		this.mcByGrams = mcByGrams;
	}

	public BigDecimal getMcByAmount() {
		return mcByAmount;
	}

	public void setMcByAmount(BigDecimal mcByAmount) {
		this.mcByAmount = mcByAmount;
	}

	public BigDecimal getValueAdditionCharges() {
		return valueAdditionCharges;
	}

	public void setValueAdditionCharges(BigDecimal valueAdditionCharges) {
		this.valueAdditionCharges = valueAdditionCharges;
	}

	public String getStone() {
		return stone;
	}

	public void setStone(String stone) {
		this.stone = stone;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getLess() {
		return less;
	}

	public void setLess(BigDecimal less) {
		this.less = less;
	}

	public BigDecimal getAmountAfterLess() {
		return amountAfterLess;
	}

	public void setAmountAfterLess(BigDecimal amountAfterLess) {
		this.amountAfterLess = amountAfterLess;
	}

	public BigDecimal getLessPercentage() {
		return lessPercentage;
	}

	public void setLessPercentage(BigDecimal lessPercentage) {
		this.lessPercentage = lessPercentage;
	}

	public BigDecimal getLessAmount() {
		return lessAmount;
	}

	public void setLessAmount(BigDecimal lessAmount) {
		this.lessAmount = lessAmount;
	}

	public BigDecimal getVtax() {
		return vtax;
	}

	public void setVtax(BigDecimal vtax) {
		this.vtax = vtax;
	}

	public String getManufacturerSeal() {
		return manufacturerSeal;
	}

	public void setManufacturerSeal(String manufacturerSeal) {
		this.manufacturerSeal = manufacturerSeal;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getItemCode1() {
		return itemCode1;
	}

	public void setItemCode1(String itemCode1) {
		this.itemCode1 = itemCode1;
	}

	public String getItemName1() {
		return itemName1;
	}

	public void setItemName1(String itemName1) {
		this.itemName1 = itemName1;
	}

	public String getCategoryName1() {
		return categoryName1;
	}

	public void setCategoryName1(String categoryName1) {
		this.categoryName1 = categoryName1;
	}

	public String getBullionType1() {
		return bullionType1;
	}

	public void setBullionType1(String bullionType1) {
		this.bullionType1 = bullionType1;
	}

	public String getKaratInfo1() {
		return karatInfo1;
	}

	public void setKaratInfo1(String karatInfo1) {
		this.karatInfo1 = karatInfo1;
	}

	public int getNumberOfPieces1() {
		return numberOfPieces1;
	}

	public void setNumberOfPieces1(int numberOfPieces1) {
		this.numberOfPieces1 = numberOfPieces1;
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
	public BigDecimal getWastageByGrams1() {
		return wastageByGrams1;
	}

	public void setWastageByGrams1(BigDecimal wastageByGrams1) {
		this.wastageByGrams1 = wastageByGrams1;
	}

	public BigDecimal getWastageByAmount1() {
		return wastageByAmount1;
	}

	public void setWastageByAmount1(BigDecimal wastageByAmount1) {
		this.wastageByAmount1 = wastageByAmount1;
	}

	public BigDecimal getMcByGrams1() {
		return mcByGrams1;
	}

	public void setMcByGrams1(BigDecimal mcByGrams1) {
		this.mcByGrams1 = mcByGrams1;
	}

	public BigDecimal getMcByAmount1() {
		return mcByAmount1;
	}

	public void setMcByAmount1(BigDecimal mcByAmount1) {
		this.mcByAmount1 = mcByAmount1;
	}

	public BigDecimal getValueAdditionCharges1() {
		return valueAdditionCharges1;
	}

	public void setValueAdditionCharges1(BigDecimal valueAdditionCharges1) {
		this.valueAdditionCharges1 = valueAdditionCharges1;
	}

	public String getStone1() {
		return stone1;
	}

	public void setStone1(String stone1) {
		this.stone1 = stone1;
	}

	public BigDecimal getStoneRatePerCaret1() {
		return stoneRatePerCaret1;
	}

	public void setStoneRatePerCaret1(BigDecimal stoneRatePerCaret1) {
		this.stoneRatePerCaret1 = stoneRatePerCaret1;
	}

	public BigDecimal getStoneCost1() {
		return stoneCost1;
	}

	public void setStoneCost1(BigDecimal stoneCost1) {
		this.stoneCost1 = stoneCost1;
	}

	public BigDecimal getAmount1() {
		return amount1;
	}

	public void setAmount1(BigDecimal amount1) {
		this.amount1 = amount1;
	}

	public BigDecimal getLess1() {
		return less1;
	}

	public void setLess1(BigDecimal less1) {
		this.less1 = less1;
	}

	public BigDecimal getVtax1() {
		return vtax1;
	}

	public void setVtax1(BigDecimal vtax1) {
		this.vtax1 = vtax1;
	}

	public BigDecimal getAmountAfterLess1() {
		return amountAfterLess1;
	}

	public void setAmountAfterLess1(BigDecimal amountAfterLess1) {
		this.amountAfterLess1 = amountAfterLess1;
	}

	public BigDecimal getLessPercentage1() {
		return lessPercentage1;
	}

	public void setLessPercentage1(BigDecimal lessPercentage1) {
		this.lessPercentage1 = lessPercentage1;
	}

	public BigDecimal getLessAmount1() {
		return lessAmount1;
	}

	public void setLessAmount1(BigDecimal lessAmount1) {
		this.lessAmount1 = lessAmount1;
	}

	public String getManufacturerSeal1() {
		return manufacturerSeal1;
	}

	public void setManufacturerSeal1(String manufacturerSeal1) {
		this.manufacturerSeal1 = manufacturerSeal1;
	}

	public String getItemCode2() {
		return itemCode2;
	}

	public void setItemCode2(String itemCode2) {
		this.itemCode2 = itemCode2;
	}

	public String getItemName2() {
		return itemName2;
	}

	public void setItemName2(String itemName2) {
		this.itemName2 = itemName2;
	}

	public String getCategoryName2() {
		return categoryName2;
	}

	public void setCategoryName2(String categoryName2) {
		this.categoryName2 = categoryName2;
	}

	public String getBullionType2() {
		return bullionType2;
	}

	public void setBullionType2(String bullionType2) {
		this.bullionType2 = bullionType2;
	}

	public String getKaratInfo2() {
		return karatInfo2;
	}

	public void setKaratInfo2(String karatInfo2) {
		this.karatInfo2 = karatInfo2;
	}

	public int getNumberOfPieces2() {
		return numberOfPieces2;
	}

	public void setNumberOfPieces2(int numberOfPieces2) {
		this.numberOfPieces2 = numberOfPieces2;
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
	public BigDecimal getWastageByGrams2() {
		return wastageByGrams2;
	}

	public void setWastageByGrams2(BigDecimal wastageByGrams2) {
		this.wastageByGrams2 = wastageByGrams2;
	}

	public BigDecimal getWastageByAmount2() {
		return wastageByAmount2;
	}

	public void setWastageByAmount2(BigDecimal wastageByAmount2) {
		this.wastageByAmount2 = wastageByAmount2;
	}

	public BigDecimal getMcByGrams2() {
		return mcByGrams2;
	}

	public void setMcByGrams2(BigDecimal mcByGrams2) {
		this.mcByGrams2 = mcByGrams2;
	}

	public BigDecimal getMcByAmount2() {
		return mcByAmount2;
	}

	public void setMcByAmount2(BigDecimal mcByAmount2) {
		this.mcByAmount2 = mcByAmount2;
	}

	public BigDecimal getValueAdditionCharges2() {
		return valueAdditionCharges2;
	}

	public void setValueAdditionCharges2(BigDecimal valueAdditionCharges2) {
		this.valueAdditionCharges2 = valueAdditionCharges2;
	}

	public String getStone2() {
		return stone2;
	}

	public void setStone2(String stone2) {
		this.stone2 = stone2;
	}

	public BigDecimal getStoneRatePerCaret2() {
		return stoneRatePerCaret2;
	}

	public void setStoneRatePerCaret2(BigDecimal stoneRatePerCaret2) {
		this.stoneRatePerCaret2 = stoneRatePerCaret2;
	}

	public BigDecimal getStoneCost2() {
		return stoneCost2;
	}

	public void setStoneCost2(BigDecimal stoneCost2) {
		this.stoneCost2 = stoneCost2;
	}

	public BigDecimal getAmount2() {
		return amount2;
	}

	public void setAmount2(BigDecimal amount2) {
		this.amount2 = amount2;
	}

	public BigDecimal getLess2() {
		return less2;
	}

	public void setLess2(BigDecimal less2) {
		this.less2 = less2;
	}

	public BigDecimal getAmountAfterLess2() {
		return amountAfterLess2;
	}

	public void setAmountAfterLess2(BigDecimal amountAfterLess2) {
		this.amountAfterLess2 = amountAfterLess2;
	}

	public BigDecimal getVtax2() {
		return vtax2;
	}

	public void setVtax2(BigDecimal vtax2) {
		this.vtax2 = vtax2;
	}

	public BigDecimal getLessPercentage2() {
		return lessPercentage2;
	}

	public void setLessPercentage2(BigDecimal lessPercentage2) {
		this.lessPercentage2 = lessPercentage2;
	}

	public BigDecimal getLessAmount2() {
		return lessAmount2;
	}

	public void setLessAmount2(BigDecimal lessAmount2) {
		this.lessAmount2 = lessAmount2;
	}

	public String getManufacturerSeal2() {
		return manufacturerSeal2;
	}

	public void setManufacturerSeal2(String manufacturerSeal2) {
		this.manufacturerSeal2 = manufacturerSeal2;
	}

	public String getItemCode3() {
		return itemCode3;
	}

	public void setItemCode3(String itemCode3) {
		this.itemCode3 = itemCode3;
	}

	public String getItemName3() {
		return itemName3;
	}

	public void setItemName3(String itemName3) {
		this.itemName3 = itemName3;
	}

	public String getCategoryName3() {
		return categoryName3;
	}

	public void setCategoryName3(String categoryName3) {
		this.categoryName3 = categoryName3;
	}

	public String getBullionType3() {
		return bullionType3;
	}

	public void setBullionType3(String bullionType3) {
		this.bullionType3 = bullionType3;
	}

	public String getKaratInfo3() {
		return karatInfo3;
	}

	public void setKaratInfo3(String karatInfo3) {
		this.karatInfo3 = karatInfo3;
	}

	public int getNumberOfPieces3() {
		return numberOfPieces3;
	}

	public void setNumberOfPieces3(int numberOfPieces3) {
		this.numberOfPieces3 = numberOfPieces3;
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
	public BigDecimal getWastageByGrams3() {
		return wastageByGrams3;
	}

	public void setWastageByGrams3(BigDecimal wastageByGrams3) {
		this.wastageByGrams3 = wastageByGrams3;
	}

	public BigDecimal getWastageByAmount3() {
		return wastageByAmount3;
	}

	public void setWastageByAmount3(BigDecimal wastageByAmount3) {
		this.wastageByAmount3 = wastageByAmount3;
	}

	public BigDecimal getMcByGrams3() {
		return mcByGrams3;
	}

	public void setMcByGrams3(BigDecimal mcByGrams3) {
		this.mcByGrams3 = mcByGrams3;
	}

	public BigDecimal getMcByAmount3() {
		return mcByAmount3;
	}

	public void setMcByAmount3(BigDecimal mcByAmount3) {
		this.mcByAmount3 = mcByAmount3;
	}

	public BigDecimal getValueAdditionCharges3() {
		return valueAdditionCharges3;
	}

	public void setValueAdditionCharges3(BigDecimal valueAdditionCharges3) {
		this.valueAdditionCharges3 = valueAdditionCharges3;
	}

	public String getStone3() {
		return stone3;
	}

	public void setStone3(String stone3) {
		this.stone3 = stone3;
	}

	public BigDecimal getStoneRatePerCaret3() {
		return stoneRatePerCaret3;
	}

	public void setStoneRatePerCaret3(BigDecimal stoneRatePerCaret3) {
		this.stoneRatePerCaret3 = stoneRatePerCaret3;
	}

	public BigDecimal getStoneCost3() {
		return stoneCost3;
	}

	public void setStoneCost3(BigDecimal stoneCost3) {
		this.stoneCost3 = stoneCost3;
	}

	public BigDecimal getAmount3() {
		return amount3;
	}

	public void setAmount3(BigDecimal amount3) {
		this.amount3 = amount3;
	}

	public BigDecimal getLess3() {
		return less3;
	}

	public void setLess3(BigDecimal less3) {
		this.less3 = less3;
	}

	public BigDecimal getAmountAfterLess3() {
		return amountAfterLess3;
	}

	public void setAmountAfterLess3(BigDecimal amountAfterLess3) {
		this.amountAfterLess3 = amountAfterLess3;
	}

	public BigDecimal getVtax3() {
		return vtax3;
	}

	public void setVtax3(BigDecimal vtax3) {
		this.vtax3 = vtax3;
	}

	public BigDecimal getLessPercentage3() {
		return lessPercentage3;
	}

	public void setLessPercentage3(BigDecimal lessPercentage3) {
		this.lessPercentage3 = lessPercentage3;
	}

	public BigDecimal getLessAmount3() {
		return lessAmount3;
	}

	public void setLessAmount3(BigDecimal lessAmount3) {
		this.lessAmount3 = lessAmount3;
	}

	public String getManufacturerSeal3() {
		return manufacturerSeal3;
	}

	public void setManufacturerSeal3(String manufacturerSeal3) {
		this.manufacturerSeal3 = manufacturerSeal3;
	}

	public String getItemCode4() {
		return itemCode4;
	}

	public void setItemCode4(String itemCode4) {
		this.itemCode4 = itemCode4;
	}

	public String getItemName4() {
		return itemName4;
	}

	public void setItemName4(String itemName4) {
		this.itemName4 = itemName4;
	}

	public String getCategoryName4() {
		return categoryName4;
	}

	public void setCategoryName4(String categoryName4) {
		this.categoryName4 = categoryName4;
	}

	public String getBullionType4() {
		return bullionType4;
	}

	public void setBullionType4(String bullionType4) {
		this.bullionType4 = bullionType4;
	}

	public String getKaratInfo4() {
		return karatInfo4;
	}

	public void setKaratInfo4(String karatInfo4) {
		this.karatInfo4 = karatInfo4;
	}

	public int getNumberOfPieces4() {
		return numberOfPieces4;
	}

	public void setNumberOfPieces4(int numberOfPieces4) {
		this.numberOfPieces4 = numberOfPieces4;
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
	public BigDecimal getWastageByGrams4() {
		return wastageByGrams4;
	}

	public void setWastageByGrams4(BigDecimal wastageByGrams4) {
		this.wastageByGrams4 = wastageByGrams4;
	}

	public BigDecimal getWastageByAmount4() {
		return wastageByAmount4;
	}

	public void setWastageByAmount4(BigDecimal wastageByAmount4) {
		this.wastageByAmount4 = wastageByAmount4;
	}

	public BigDecimal getMcByGrams4() {
		return mcByGrams4;
	}

	public void setMcByGrams4(BigDecimal mcByGrams4) {
		this.mcByGrams4 = mcByGrams4;
	}

	public BigDecimal getMcByAmount4() {
		return mcByAmount4;
	}

	public void setMcByAmount4(BigDecimal mcByAmount4) {
		this.mcByAmount4 = mcByAmount4;
	}

	public BigDecimal getValueAdditionCharges4() {
		return valueAdditionCharges4;
	}

	public void setValueAdditionCharges4(BigDecimal valueAdditionCharges4) {
		this.valueAdditionCharges4 = valueAdditionCharges4;
	}

	public String getStone4() {
		return stone4;
	}

	public void setStone4(String stone4) {
		this.stone4 = stone4;
	}

	public BigDecimal getStoneRatePerCaret4() {
		return stoneRatePerCaret4;
	}

	public void setStoneRatePerCaret4(BigDecimal stoneRatePerCaret4) {
		this.stoneRatePerCaret4 = stoneRatePerCaret4;
	}

	public BigDecimal getStoneCost4() {
		return stoneCost4;
	}

	public void setStoneCost4(BigDecimal stoneCost4) {
		this.stoneCost4 = stoneCost4;
	}

	public BigDecimal getAmount4() {
		return amount4;
	}

	public void setAmount4(BigDecimal amount4) {
		this.amount4 = amount4;
	}

	public BigDecimal getLess4() {
		return less4;
	}

	public void setLess4(BigDecimal less4) {
		this.less4 = less4;
	}

	public BigDecimal getAmountAfterLess4() {
		return amountAfterLess4;
	}

	public void setAmountAfterLess4(BigDecimal amountAfterLess4) {
		this.amountAfterLess4 = amountAfterLess4;
	}

	public BigDecimal getVtax4() {
		return vtax4;
	}

	public void setVtax4(BigDecimal vtax4) {
		this.vtax4 = vtax4;
	}

	public BigDecimal getLessPercentage4() {
		return lessPercentage4;
	}

	public void setLessPercentage4(BigDecimal lessPercentage4) {
		this.lessPercentage4 = lessPercentage4;
	}

	public BigDecimal getLessAmount4() {
		return lessAmount4;
	}

	public void setLessAmount4(BigDecimal lessAmount4) {
		this.lessAmount4 = lessAmount4;
	}

	public String getManufacturerSeal4() {
		return manufacturerSeal4;
	}

	public void setManufacturerSeal4(String manufacturerSeal4) {
		this.manufacturerSeal4 = manufacturerSeal4;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalLess() {
		return totalLess;
	}

	public void setTotalLess(BigDecimal totalLess) {
		this.totalLess = totalLess;
	}

	public BigDecimal getLessPerGram() {
		return lessPerGram;
	}

	public void setLessPerGram(BigDecimal lessPerGram) {
		this.lessPerGram = lessPerGram;
	}
	
	@Column(precision = 19, scale = 3)
	public BigDecimal getLessOnWeight() {
		return lessOnWeight;
	}

	public void setLessOnWeight(BigDecimal lessOnWeight) {
		this.lessOnWeight = lessOnWeight;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public BigDecimal getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}

	public BigDecimal getAdvance() {
		return advance;
	}

	public void setAdvance(BigDecimal advance) {
		this.advance = advance;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public BigDecimal getAdjustmentAmount() {
		return adjustmentAmount;
	}

	public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}

	public BigDecimal getRoundOff() {
		return roundOff;
	}

	public void setRoundOff(BigDecimal roundOff) {
		this.roundOff = roundOff;
	}

	public BigDecimal getBalToPay() {
		return balToPay;
	}

	public void setBalToPay(BigDecimal balToPay) {
		this.balToPay = balToPay;
	}

	public BigDecimal getAmtRecd() {
		return amtRecd;
	}

	public void setAmtRecd(BigDecimal amtRecd) {
		this.amtRecd = amtRecd;
	}

	

	public String getExchangeBillNo() {
		return exchangeBillNo;
	}

	public void setExchangeBillNo(String exchangeBillNo) {
		this.exchangeBillNo = exchangeBillNo;
	}

	public BigDecimal getExchangeAmount() {
		return exchangeAmount;
	}

	public void setExchangeAmount(BigDecimal exchangeAmount) {
		this.exchangeAmount = exchangeAmount;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public String getChequeBank() {
		return chequeBank;
	}

	public void setChequeBank(String chequeBank) {
		this.chequeBank = chequeBank;
	}

	public String getChequeDetails() {
		return chequeDetails;
	}

	public void setChequeDetails(String chequeDetails) {
		this.chequeDetails = chequeDetails;
	}

	public BigDecimal getChequeAmount() {
		return chequeAmount;
	}

	public void setChequeAmount(BigDecimal chequeAmount) {
		this.chequeAmount = chequeAmount;
	}

	public String getCardBank() {
		return cardBank;
	}

	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}

	public String getCardDetails() {
		return cardDetails;
	}

	public void setCardDetails(String cardDetails) {
		this.cardDetails = cardDetails;
	}

	public BigDecimal getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(BigDecimal cardAmount) {
		this.cardAmount = cardAmount;
	}

	public String getVoucherList() {
		return voucherList;
	}

	public void setVoucherList(String voucherList) {
		this.voucherList = voucherList;
	}

	public String getVoucherDetails() {
		return voucherDetails;
	}

	public void setVoucherDetails(String voucherDetails) {
		this.voucherDetails = voucherDetails;
	}

	public BigDecimal getVoucherAmount() {
		return voucherAmount;
	}

	public void setVoucherAmount(BigDecimal voucherAmount) {
		this.voucherAmount = voucherAmount;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getGreetings() {
		return greetings;
	}

	public void setGreetings(String greetings) {
		this.greetings = greetings;
	}

	//new feild added on 15/3/12

	public Integer getSalesOrderID() {
		return salesOrderID;
	}

	public void setSalesOrderID(Integer salesOrderID) {
		this.salesOrderID = salesOrderID;
	}


	public String getReceiptID() {
		return receiptID;
	}

	public void setReceiptID(String receiptID) {
		this.receiptID = receiptID;
	}

	public BigDecimal getReceiptAmount() {
		return receiptAmount;
	}

	public void setReceiptAmount(BigDecimal receiptAmount) {
		this.receiptAmount = receiptAmount;
	}

	/**
	 * @return the totalPieces
	 */
	public int getTotalPieces() {
		return totalPieces;
	}

	/**
	 * @param totalPieces the totalPieces to set
	 */
	public void setTotalPieces(int totalPieces) {
		this.totalPieces = totalPieces;
	}

	/**
	 * @return the totalPieces1
	 */
	public int getTotalPieces1() {
		return totalPieces1;
	}

	/**
	 * @param totalPieces1 the totalPieces1 to set
	 */
	public void setTotalPieces1(int totalPieces1) {
		this.totalPieces1 = totalPieces1;
	}

	/**
	 * @return the totalPieces2
	 */
	public int getTotalPieces2() {
		return totalPieces2;
	}

	/**
	 * @param totalPieces2 the totalPieces2 to set
	 */
	public void setTotalPieces2(int totalPieces2) {
		this.totalPieces2 = totalPieces2;
	}

	/**
	 * @return the totalPieces3
	 */
	public int getTotalPieces3() {
		return totalPieces3;
	}

	/**
	 * @param totalPieces3 the totalPieces3 to set
	 */
	public void setTotalPieces3(int totalPieces3) {
		this.totalPieces3 = totalPieces3;
	}

	/**
	 * @return the totalPieces4
	 */
	public int getTotalPieces4() {
		return totalPieces4;
	}

	/**
	 * @param totalPieces4 the totalPieces4 to set
	 */
	public void setTotalPieces4(int totalPieces4) {
		this.totalPieces4 = totalPieces4;
	}
	
	
	public String getSalesTypeId() {
		return salesTypeId;
	}

	public void setSalesTypeId(String salesTypeId) {
		this.salesTypeId = salesTypeId;
	}

	public String getSSCardNo() {
		return SSCardNo;
	}

	public void setSSCardNo(String sSCardNo) {
		SSCardNo = sSCardNo;
	}

	public BigDecimal getSSCardAmount() {
		return SSCardAmount;
	}

	public void setSSCardAmount(BigDecimal sSCardAmount) {
		SSCardAmount = sSCardAmount;
	}

	public BigDecimal getSSCardGrams() {
		return SSCardGrams;
	}

	public void setSSCardGrams(BigDecimal sSCardGrams) {
		SSCardGrams = sSCardGrams;
	}

	public String getSSCardNoVal() {
		return SSCardNoVal;
	}

	public void setSSCardNoVal(String sSCardNoVal) {
		SSCardNoVal = sSCardNoVal;
	}

public BigDecimal getCommissionPercent() {
		return commissionPercent;
	}

	public void setCommissionPercent(BigDecimal commissionPercent) {
		this.commissionPercent = commissionPercent;
	}

	public BigDecimal getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(BigDecimal commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public BigDecimal getCalCardAmount() {
		return calCardAmount;
	}

	public void setCalCardAmount(BigDecimal calCardAmount) {
		this.calCardAmount = calCardAmount;
	}

	public BigDecimal getJewelDiscount() {
		return jewelDiscount;
	}

	public void setJewelDiscount(BigDecimal jewelDiscount) {
		this.jewelDiscount = jewelDiscount;
	}

	public BigDecimal getRateFixDifferenceAmount() {
		return rateFixDifferenceAmount;
	}

	public void setRateFixDifferenceAmount(BigDecimal rateFixDifferenceAmount) {
		this.rateFixDifferenceAmount = rateFixDifferenceAmount;
	}

	

	public BigDecimal getSalesOrderAmount() {
		return salesOrderAmount;
	}

	public void setSalesOrderAmount(BigDecimal salesOrderAmount) {
		this.salesOrderAmount = salesOrderAmount;
	}

	public BigDecimal getRateFixGrams() {
		return rateFixGrams;
	}

	public void setRateFixGrams(BigDecimal rateFixGrams) {
		this.rateFixGrams = rateFixGrams;
	}

	public BigDecimal getBoardRateSO() {
		return boardRateSO;
	}

	public void setBoardRateSO(BigDecimal boardRateSO) {
		this.boardRateSO = boardRateSO;
	}

	public BigDecimal getRateFixAmount() {
		return rateFixAmount;
	}

	public void setRateFixAmount(BigDecimal rateFixAmount) {
		this.rateFixAmount = rateFixAmount;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getSalesReturnStatus() {
		return salesReturnStatus;
	}

	public void setSalesReturnStatus(String salesReturnStatus) {
		this.salesReturnStatus = salesReturnStatus;
	}

	public String getSalesReturnId() {
		return salesReturnId;
	}

	public void setSalesReturnId(String salesReturnId) {
		this.salesReturnId = salesReturnId;
	}

	public BigDecimal getSalesReturnAmount() {
		return salesReturnAmount;
	}

	public void setSalesReturnAmount(BigDecimal salesReturnAmount) {
		this.salesReturnAmount = salesReturnAmount;
	}

	public BigDecimal getSalesHMCharges() {
		return salesHMCharges;
	}

	public void setSalesHMCharges(BigDecimal salesHMCharges) {
		this.salesHMCharges = salesHMCharges;
	}

	public BigDecimal getSalesHMCharges1() {
		return salesHMCharges1;
	}

	public void setSalesHMCharges1(BigDecimal salesHMCharges1) {
		this.salesHMCharges1 = salesHMCharges1;
	}

	public BigDecimal getSalesHMCharges2() {
		return salesHMCharges2;
	}

	public void setSalesHMCharges2(BigDecimal salesHMCharges2) {
		this.salesHMCharges2 = salesHMCharges2;
	}

	public BigDecimal getSalesHMCharges3() {
		return salesHMCharges3;
	}

	public void setSalesHMCharges3(BigDecimal salesHMCharges3) {
		this.salesHMCharges3 = salesHMCharges3;
	}

	public BigDecimal getSalesHMCharges4() {
		return salesHMCharges4;
	}

	public void setSalesHMCharges4(BigDecimal salesHMCharges4) {
		this.salesHMCharges4 = salesHMCharges4;
	}

	public BigDecimal getGoldOrnBoardRate() {
		return goldOrnBoardRate;
	}

	public void setGoldOrnBoardRate(BigDecimal goldOrnBoardRate) {
		this.goldOrnBoardRate = goldOrnBoardRate;
	}

	public BigDecimal getGoldBullBoardRate() {
		return goldBullBoardRate;
	}

	public void setGoldBullBoardRate(BigDecimal goldBullBoardRate) {
		this.goldBullBoardRate = goldBullBoardRate;
	}

	public BigDecimal getOldGoldOrnBoardRate() {
		return oldGoldOrnBoardRate;
	}

	public void setOldGoldOrnBoardRate(BigDecimal oldGoldOrnBoardRate) {
		this.oldGoldOrnBoardRate = oldGoldOrnBoardRate;
	}

	public BigDecimal getSilverOrnBoardRate() {
		return silverOrnBoardRate;
	}

	public void setSilverOrnBoardRate(BigDecimal silverOrnBoardRate) {
		this.silverOrnBoardRate = silverOrnBoardRate;
	}

	public BigDecimal getOldSilverOrnBoardRate() {
		return oldSilverOrnBoardRate;
	}

	public void setOldSilverOrnBoardRate(BigDecimal oldSilverOrnBoardRate) {
		this.oldSilverOrnBoardRate = oldSilverOrnBoardRate;
	}

	public BigDecimal getSilverBullBoardRate() {
		return silverBullBoardRate;
	}

	public void setSilverBullBoardRate(BigDecimal silverBullBoardRate) {
		this.silverBullBoardRate = silverBullBoardRate;
	}

	public String getRateMasterDate() {
		return rateMasterDate;
	}

	public void setRateMasterDate(String rateMasterDate) {
		this.rateMasterDate = rateMasterDate;
	}


	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public BigDecimal getSalesReturnMC1() {
		return salesReturnMC1;
	}

	public void setSalesReturnMC1(BigDecimal salesReturnMC1) {
		this.salesReturnMC1 = salesReturnMC1;
	}

	public BigDecimal getSalesReturnVA1() {
		return salesReturnVA1;
	}

	public void setSalesReturnVA1(BigDecimal salesReturnVA1) {
		this.salesReturnVA1 = salesReturnVA1;
	}

	public BigDecimal getSalesReturnHC1() {
		return salesReturnHC1;
	}

	public void setSalesReturnHC1(BigDecimal salesReturnHC1) {
		this.salesReturnHC1 = salesReturnHC1;
	}

	public BigDecimal getSalesReturnMC2() {
		return salesReturnMC2;
	}

	public void setSalesReturnMC2(BigDecimal salesReturnMC2) {
		this.salesReturnMC2 = salesReturnMC2;
	}

	public BigDecimal getSalesReturnVA2() {
		return salesReturnVA2;
	}

	public void setSalesReturnVA2(BigDecimal salesReturnVA2) {
		this.salesReturnVA2 = salesReturnVA2;
	}

	public BigDecimal getSalesReturnHC2() {
		return salesReturnHC2;
	}

	public void setSalesReturnHC2(BigDecimal salesReturnHC2) {
		this.salesReturnHC2 = salesReturnHC2;
	}

	public BigDecimal getSalesReturnMC3() {
		return salesReturnMC3;
	}

	public void setSalesReturnMC3(BigDecimal salesReturnMC3) {
		this.salesReturnMC3 = salesReturnMC3;
	}

	public BigDecimal getSalesReturnVA3() {
		return salesReturnVA3;
	}

	public void setSalesReturnVA3(BigDecimal salesReturnVA3) {
		this.salesReturnVA3 = salesReturnVA3;
	}

	public BigDecimal getSalesReturnHC3() {
		return salesReturnHC3;
	}

	public void setSalesReturnHC3(BigDecimal salesReturnHC3) {
		this.salesReturnHC3 = salesReturnHC3;
	}

	public BigDecimal getSalesReturnMC4() {
		return salesReturnMC4;
	}

	public void setSalesReturnMC4(BigDecimal salesReturnMC4) {
		this.salesReturnMC4 = salesReturnMC4;
	}

	public BigDecimal getSalesReturnVA4() {
		return salesReturnVA4;
	}

	public void setSalesReturnVA4(BigDecimal salesReturnVA4) {
		this.salesReturnVA4 = salesReturnVA4;
	}

	public BigDecimal getSalesReturnHC4() {
		return salesReturnHC4;
	}

	public void setSalesReturnHC4(BigDecimal salesReturnHC4) {
		this.salesReturnHC4 = salesReturnHC4;
	}

	public BigDecimal getBillDiscAmt() {
		return billDiscAmt;
	}

	public void setBillDiscAmt(BigDecimal billDiscAmt) {
		this.billDiscAmt = billDiscAmt;
	}

	public int getSoldQty() {
		return soldQty;
	}

	public void setSoldQty(int soldQty) {
		this.soldQty = soldQty;
	}

	public int getSoldQty1() {
		return soldQty1;
	}

	public void setSoldQty1(int soldQty1) {
		this.soldQty1 = soldQty1;
	}

	public int getSoldQty2() {
		return soldQty2;
	}

	public void setSoldQty2(int soldQty2) {
		this.soldQty2 = soldQty2;
	}

	public int getSoldQty3() {
		return soldQty3;
	}

	public void setSoldQty3(int soldQty3) {
		this.soldQty3 = soldQty3;
	}

	public int getSoldQty4() {
		return soldQty4;
	}

	public void setSoldQty4(int soldQty4) {
		this.soldQty4 = soldQty4;
	}

	public String getMetalUsed() {
		return metalUsed;
	}

	public void setMetalUsed(String metalUsed) {
		this.metalUsed = metalUsed;
	}

	public String getMetalUsed1() {
		return metalUsed1;
	}

	public void setMetalUsed1(String metalUsed1) {
		this.metalUsed1 = metalUsed1;
	}

	public String getMetalUsed2() {
		return metalUsed2;
	}

	public void setMetalUsed2(String metalUsed2) {
		this.metalUsed2 = metalUsed2;
	}

	public String getMetalUsed3() {
		return metalUsed3;
	}

	public void setMetalUsed3(String metalUsed3) {
		this.metalUsed3 = metalUsed3;
	}

	public String getMetalUsed4() {
		return metalUsed4;
	}

	public void setMetalUsed4(String metalUsed4) {
		this.metalUsed4 = metalUsed4;
	}
}