package com.jewellery.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class POSSales implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean printInvoice; // print confirmation dialog box value
	private Integer posSalesId;
	private String salesType;
	private Integer referencePosno;	
	private String customerName;
	private String walkinCustomer;
	private String walkinAddress;
	private Date salesdate;
	private String salesmanName;
	private Integer transactionNo;
	private BigDecimal taxinAmount = new BigDecimal("0.00");	
	private Set<POSSalesItem> saless = new HashSet<POSSalesItem>(0);
	private String receiptType;	
	private String cardDetails;
	private String card;
	private String cash;
	private String cheque;
    private String chequeDetails;
	private String giftVoucher;
	private String voucherDetails;
	private String returnreason;
	private String chequeBank;
	private String cardBank;
	private String voucherList;
	private BigDecimal netAmount = new BigDecimal("0.00");
	private BigDecimal subTotal = new BigDecimal("0.00");
	private BigDecimal roundOff= new BigDecimal("0.00");
	private BigDecimal totalAmount = new BigDecimal("0.00");
	private BigDecimal cashAmount= new BigDecimal("0.00");
    private BigDecimal cardAmount=new BigDecimal("0.00");
    private BigDecimal chequeAmount=new BigDecimal("0.00");
    private BigDecimal voucherAmount=new BigDecimal("0.00");
    private BigDecimal counterCash=new BigDecimal("0.00");
    private BigDecimal totalAmountReceived=new BigDecimal("0.00");
    private BigDecimal balanceToCustomer=new BigDecimal("0.00");
    private String billNo;
    private String billposType;
    
    public POSSales(){}
    
    @Id
    @GeneratedValue
    public Integer getPosSalesId() {
		return posSalesId;
	}

	public void setPosSalesId(Integer posSalesId) {
		this.posSalesId = posSalesId;
	}
	public String getSalesType() {
		return salesType;
	}
	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}
	
	public Integer getReferencePosno() {
		return referencePosno;
	}
	public void setReferencePosno(Integer referencePosno) {
		this.referencePosno = referencePosno;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getWalkinCustomer() {
		return walkinCustomer;
	}
	public void setWalkinCustomer(String walkinCustomer) {
		this.walkinCustomer = walkinCustomer;
	}
	public String getWalkinAddress() {
		return walkinAddress;
	}
	public void setWalkinAddress(String walkinAddress) {
		this.walkinAddress = walkinAddress;
	}
	
	public Integer getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(Integer transactionNo) {
		this.transactionNo = transactionNo;
	
	}
	
	public BigDecimal getTaxinAmount() {
		return taxinAmount;
	}
	public void setTaxinAmount(BigDecimal taxinAmount) {
		this.taxinAmount = taxinAmount;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "possales")
	public Set<POSSalesItem> getSaless() {
		return this.saless;
	}

	public void setSaless(Set<POSSalesItem> saless) {
		this.saless = saless;
	}
	
	
	public String getReceiptType() {
		return receiptType;
	}
	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}
	
    
	public String getCardDetails() {
		return cardDetails;
	}
	public void setCardDetails(String cardDetails) {
		this.cardDetails = cardDetails;
	}
	public String getCardBank() {
		return cardBank;
	}
	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}
	public String getCheque() {
		return cheque;
	}
	public void setCheque(String cheque) {
		this.cheque = cheque;
	}
	public String getChequeDetails() {
		return chequeDetails;
	}
	public void setChequeDetails(String chequeDetails) {
		this.chequeDetails = chequeDetails;
	}
	public String getGiftVoucher() {
		return giftVoucher;
	}
	public void setGiftVoucher(String giftVoucher) {
		this.giftVoucher = giftVoucher;
	}
	public String getVoucherDetails() {
		return voucherDetails;
	}
	public void setVoucherDetails(String voucherDetails) {
		this.voucherDetails = voucherDetails;
	}
	public String getReturnreason() {
		return returnreason;
	}
	public void setReturnreason(String returnreason) {
		this.returnreason = returnreason;
	}
	
	public BigDecimal getRoundOff() {
		return roundOff;
	}
	public void setRoundOff(BigDecimal roundOff) {
		this.roundOff = roundOff;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}
	public BigDecimal getCardAmount() {
		return cardAmount;
	}
	public void setCardAmount(BigDecimal cardAmount) {
		this.cardAmount = cardAmount;
	}
	public BigDecimal getChequeAmount() {
		return chequeAmount;
	}
	public void setChequeAmount(BigDecimal chequeAmount) {
		this.chequeAmount = chequeAmount;
	}
	public BigDecimal getVoucherAmount() {
		return voucherAmount;
	}
	public void setVoucherAmount(BigDecimal voucherAmount) {
		this.voucherAmount = voucherAmount;
	}
	public String getCash() {
		return cash;
	}
	public void setCash(String cash) {
		this.cash = cash;
	}
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	public BigDecimal getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}
    
	public Date getSalesdate() {
		return salesdate;
	}
	
	public void setSalesdate(Date salesdate) {
		this.salesdate = salesdate;
	}
	
	public String getSalesmanName() {
		return salesmanName;
	}
	
	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}

	public String getChequeBank() {
		return chequeBank;
	}

	public void setChequeBank(String chequeBank) {
		this.chequeBank = chequeBank;
	}

	

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getVoucherList() {
		return voucherList;
	}

	public void setVoucherList(String voucherList) {
		this.voucherList = voucherList;
	}

	public BigDecimal getCounterCash() {
		return counterCash;
	}

	public void setCounterCash(BigDecimal counterCash) {
		this.counterCash = counterCash;
	}

	public boolean isPrintInvoice() {
		return printInvoice;
	}

	public void setPrintInvoice(boolean printInvoice) {
		this.printInvoice = printInvoice;
	}

	public BigDecimal getTotalAmountReceived() {
		return totalAmountReceived;
	}

	public void setTotalAmountReceived(BigDecimal totalAmountReceived) {
		this.totalAmountReceived = totalAmountReceived;
	}

	public BigDecimal getBalanceToCustomer() {
		return balanceToCustomer;
	}

	public void setBalanceToCustomer(BigDecimal balanceToCustomer) {
		this.balanceToCustomer = balanceToCustomer;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBillposType() {
		return billposType;
	}

	public void setBillposType(String billposType) {
		this.billposType = billposType;
	}

	

	

	
}
