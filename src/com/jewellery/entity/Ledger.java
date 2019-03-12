package com.jewellery.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.validator.NotNull;


@Entity
@NamedQueries( { @NamedQuery(name = "listAllLedger", query = "from Ledger"),
	 @NamedQuery(name = "listLedgerName", query = "from Ledger where accountGroup='Sundry Debtors' or accountGroup='Sundry Creditors' order by ledgerName"),
	 @NamedQuery(name = "listCustomerNameOnly", query = "from Ledger where accountGroup='Sundry Debtors' order by ledgerName"),
	 @NamedQuery(name = "jtypeRecieptDebitside", query = "from Ledger where accountGroup='Cash Account' or accountGroup='Bank Account' or accountGroup='Bank Loan' or accountGroup='Bank OCC Account' order by ledgerName"),
	 @NamedQuery(name = "jtypeRecieptCreditside", query = "from Ledger where accountGroup!='Profit & Loss Account' and accountGroup!='Stock Account' order by ledgerName"),
	 @NamedQuery(name = "journal", query = "from Ledger where accountGroup!='Stock Account' order by ledgerName"),
	 @NamedQuery(name = "listSupplierNameOnly", query = "from Ledger where accountGroup='Sundry Creditors' order by ledgerName"),
	 @NamedQuery(name ="listCurrentAssets",query="from Ledger where accountGroup='Current Assets' order  by ledgerName"),
	 @NamedQuery(name = "listAllsmithname", query = "from Ledger where accountGroup='Sundry Creditors' order by ledgerId desc"),	 
	 @NamedQuery(name ="listCustomerName",query="from Ledger where accountGroup='Sundry Debtors' order by ledgerId desc"),
	 @NamedQuery(name = "listBank", query = "from Ledger where accountGroup='Bank Account' order by ledgerName"),
	 @NamedQuery(name = "listCashAccount", query = "from Ledger where accountGroup='Cash Account' order by ledgerName"),//add for salesorder 31/10/12
	 @NamedQuery(name = "listPOSBank", query = "from Ledger where accountGroup='Bank Account' or accountGroup='Bank Loan'or accountGroup='Bank OCC Account'   order by ledgerName"),
	 @NamedQuery(name = "getCustomerType", query = "from Ledger where ledgerName = ? ")
})

public class Ledger {

	private Integer ledgerId;
	private String ledgerName;
	private String accountGroup;
	private Date ledgerDate;
	private BigDecimal openingBalance = new BigDecimal("0.00");
	private String openingType;
	private BigDecimal opGoldBalance = new BigDecimal("0.00");
	private String opGoldType;
	private BigDecimal opSilverBalance = new BigDecimal("0.00");
	private String opSilverType;
	private BigDecimal opTotalBalance = new BigDecimal("0.00");;
	private BigDecimal closingTotalBalance = new BigDecimal("0.00");
	private String opTotalType;
	private String closingTotalType;
	private BigDecimal clGoldBalance = new BigDecimal("0.00");
	private String clGoldType;
	private BigDecimal clSilverBalance = new BigDecimal("0.00");
	private String clSilverType;
	private String companyName;
	private String cast;
	private String cstNumber;
	private String serviceTaxNumber;
	private String panNumber;
	private String vatNumber;
	private String primaryPhone;
	private String alternatePhone;
	private String email;
	private String website;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String pincode;
	private String source;
	private String typeOfSource;
	private String description;
	private String accountGroupCode;
	private List<Accounts> accounts = new ArrayList<Accounts>();

	public Ledger() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getLedgerId() {
		return ledgerId;
	}

	public void setLedgerId(Integer ledgerId) {
		this.ledgerId = ledgerId;
	}

	@Column(nullable = false, length = 50)
	public String getLedgerName() {
		return ledgerName;
	}

	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}

	@Column(nullable = false, length = 50)
	public String getAccountGroup() {
		return accountGroup;
	}

	public void setAccountGroup(String accountGroup) {
		this.accountGroup = accountGroup;
	}

	public Date getLedgerDate() {
		return ledgerDate;
	}

	public void setLedgerDate(Date ledgerDate) {
		this.ledgerDate = ledgerDate;
	}
	
	public String getOpeningType() {
		return openingType;
	}

	public void setOpeningType(String openingType) {
		this.openingType = openingType;
	}

	public String getOpGoldType() {
		return opGoldType;
	}

	public void setOpGoldType(String opGoldType) {
		this.opGoldType = opGoldType;
	}

	public String getOpSilverType() {
		return opSilverType;
	}

	public void setOpSilverType(String opSilverType) {
		this.opSilverType = opSilverType;
	}

	public String getOpTotalType() {
		return opTotalType;
	}

	public void setOpTotalType(String opTotalType) {
		this.opTotalType = opTotalType;
	}

	public String getClosingTotalType() {
		return closingTotalType;
	}

	public void setClosingTotalType(String closingTotalType) {
		this.closingTotalType = closingTotalType;
	}

	public String getClGoldType() {
		return clGoldType;
	}

	public void setClGoldType(String clGoldType) {
		this.clGoldType = clGoldType;
	}

	public String getClSilverType() {
		return clSilverType;
	}

	public void setClSilverType(String clSilverType) {
		this.clSilverType = clSilverType;
	}

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public String getAlternatePhone() {
		return alternatePhone;
	}

	public void setAlternatePhone(String alternatePhone) {
		this.alternatePhone = alternatePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCast() {
		return cast;
	}

	public void setCast(String cast) {
		this.cast = cast;
	}

	public String getCstNumber() {
		return cstNumber;
	}

	public void setCstNumber(String cstNumber) {
		this.cstNumber = cstNumber;
	}

	public String getServiceTaxNumber() {
		return serviceTaxNumber;
	}

	public void setServiceTaxNumber(String serviceTaxNumber) {
		this.serviceTaxNumber = serviceTaxNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	@ManyToMany
	@JoinTable(name = "accounts_ledgers", joinColumns = { @JoinColumn(name = "ledgerId") }, inverseJoinColumns = { @JoinColumn(name = "accountId") })
	public List<Accounts> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Accounts> accounts) {
		this.accounts = accounts;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTypeOfSource() {
		return typeOfSource;
	}

	public void setTypeOfSource(String typeOfSource) {
		this.typeOfSource = typeOfSource;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}

	public void setOpGoldBalance(BigDecimal opGoldBalance) {
		this.opGoldBalance = opGoldBalance;
	}

	public void setOpSilverBalance(BigDecimal opSilverBalance) {
		this.opSilverBalance = opSilverBalance;
	}

	public void setOpTotalBalance(BigDecimal opTotalBalance) {
		this.opTotalBalance = opTotalBalance;
	}

	public void setClosingTotalBalance(BigDecimal closingTotalBalance) {
		this.closingTotalBalance = closingTotalBalance;
	}

	public void setClGoldBalance(BigDecimal clGoldBalance) {
		this.clGoldBalance = clGoldBalance;
	}

	public void setClSilverBalance(BigDecimal clSilverBalance) {
		this.clSilverBalance = clSilverBalance;
	}
@NotNull
	//@Column(nullable = false)
	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}

	public BigDecimal getOpGoldBalance() {
		return opGoldBalance;
	}

	public BigDecimal getOpSilverBalance() {
		return opSilverBalance;
	}

	public BigDecimal getOpTotalBalance() {
		return opTotalBalance;
	}

	@Column(nullable = false)
	public BigDecimal getClosingTotalBalance() {
		return closingTotalBalance;
	}

	public BigDecimal getClGoldBalance() {
		return clGoldBalance;
	}

	public BigDecimal getClSilverBalance() {
		return clSilverBalance;
	}

	public String getAccountGroupCode() {
		return accountGroupCode;
	}

	public void setAccountGroupCode(String accountGroupCode) {
		this.accountGroupCode = accountGroupCode;
	}

}
