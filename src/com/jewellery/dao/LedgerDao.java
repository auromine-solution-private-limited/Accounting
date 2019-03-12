package com.jewellery.dao;

import java.math.BigDecimal;
import java.util.List;

import com.jewellery.entity.Ledger;

public interface LedgerDao {
	
	public List<Ledger> listLedger();
	public List<Ledger> listPartyLedger();
	public List<Ledger> listLedgerName();
	public List<Ledger> listCustomerName(); // For Listing Only Customer Names
	public List<Ledger> listallSmithName();
	public List<Ledger> listallCustomerName();
	public List<Ledger> listSupplierNameOnly();
	public List<Ledger> listJournalRecieptDebit();
	public List<Ledger> listJournalRecieptCredit();
	public List<Ledger> listjournal();
	public List<Ledger> listBank();
	public List<Ledger> listPosBank();
	public List<Ledger> listCashAccount();//added for salesorder receipt mode:cash
	public List<Ledger> listCurrentAssets();
	public Ledger getLedger(Integer id);
	public void updateLedger(Ledger ledgerId);
	public void deleteLedger(Ledger ledger);
	public void insertLedger(Ledger ledger);
	public List<Ledger> searchLedger(String ledger);
	public void updatePartyBalance(BigDecimal closingAmount, String closingType, String partyName);
	public void updateCreditPartyBalance(BigDecimal closingAmount, String closingType, String partyName);
	public void updateCrPartyBalance(BigDecimal closingAmount, String closingType, String partyName);	
	public void updateLedgerPartyBalance(BigDecimal closingAmount, String closingType, String partyName);
	public List<Ledger> searchLedgerAccount(String ledger);//changed	
	public List<Ledger> listCustomerPan(String cname); // to check whether sales to Customer or supplier
	public List<String> getAutoLedgerNames(String lNamePart);// for Autocomplete
	public List<String> getautoCustomerName(String sNamePart);
	public List<String> getAutoSupplierNames(String sNamePart); // for POS purchase Autocomplete SupplierNames
	public List<String> getLedgerName();//List ledgername
	public List<String> getSupplierValidation(String suppName); // for POS Purchase supplier Name Validation
	public List<Ledger> listCustomerPOPUP(String cname);//to check whether customer
	public List<String> getLedgerGroupCode(String ledgerGroupcode);
	public List<String> getLedgerAccountCode(String ledgerName); // For getting ledger group code
}