package com.jewellery.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity
@NamedQueries({
	@NamedQuery(name = "getAccountsByName", query = "from Accounts where accountName = :accountName"),	
	@NamedQuery(name = "listAccountsByName", query = "from Accounts order by accountName"),
	@NamedQuery(name = "getAccountId", query = "from Accounts order by accountId")
})
public class Accounts implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer accountId;
	private String accountName;
	private String accountGroup;
	private BigDecimal closingBalance = new BigDecimal("0.00");
	private String closingType;
	private String accountGroupCode;
	private String remarks;

	private List<Ledger> ledgers = new ArrayList<Ledger>();

	public Accounts() {
	}

	@Id
	@GeneratedValue
	public Integer getaccountId() {
		return accountId;
	}

	public void setaccountId(Integer accountId) {
		this.accountId = accountId;
	}

	@Column(nullable = false, length = 50)
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(nullable = false, length = 50)
	public String getAccountGroup() {
		return accountGroup;
	}

	public void setAccountGroup(String accountGroup) {
		this.accountGroup = accountGroup;
	}

	public String getClosingType() {
		return closingType;
	}

	public void setClosingType(String closingType) {
		this.closingType = closingType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "accounts_ledgers", joinColumns = { @JoinColumn(name = "accountId") }, inverseJoinColumns = { @JoinColumn(name = "ledgerId") })
	public List<Ledger> getLedgers() {
		return ledgers;
	}

	public void setLedgers(List<Ledger> ledgers) {
		this.ledgers = ledgers;
	}

	public BigDecimal getClosingBalance() {
		return closingBalance;
	}

	// @Column(nullable = false, length = 15)
	public void setClosingBalance(BigDecimal closingBalance) {
		this.closingBalance = closingBalance;
	}

	public String getAccountGroupCode() {
		return accountGroupCode;
	}

	public void setAccountGroupCode(String accountGroupCode) {
		this.accountGroupCode = accountGroupCode;
	}

}