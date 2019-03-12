package com.jewellery.dao;

import java.math.BigDecimal;
import java.util.List;
import com.jewellery.entity.Accounts;

public interface AccountsDao{
	public List<Accounts> listAccounts();
	public Accounts getAccounts(Integer id);
	public Accounts getAccounts(String account);
	public List<Accounts> getAccountId();
	public void insertAccounts(Accounts account);
	public void updateAccounts(Accounts account);
	public void deleteAccounts(Accounts account);
	public void updateClosingBalance(BigDecimal closingBalance, String closingType, String accountGroup);
	public void updateCreditBalance(BigDecimal closingBalance, String closingType, String accountGroup);
	public void updateDebitBalance(BigDecimal closingBalance, String closingType, String accountGroup);
	public List<String> getAccountGroupCode(String accountGroupcode);
	public List<Accounts> getAccountId(String accountGroupName);
}