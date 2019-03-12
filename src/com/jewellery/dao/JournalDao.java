package com.jewellery.dao;

import java.math.BigInteger;
import java.util.List;
import com.jewellery.entity.Journal;

	public interface JournalDao {
	public BigInteger getAdvanceCode();
	public BigInteger getJewelRepairCode();
	public BigInteger getBillCode();
	public BigInteger getReceiptCode();
	public BigInteger getPaymentCode();
	public BigInteger getJournalCode();
	public BigInteger getContraCode();
	public BigInteger getSavingCode();
	public BigInteger getAutoJournalCode(); //for automatic journal entries
	public List<Journal> listJournal();
	public Journal getJournal(Integer id);
	public void insertJournal(Journal journal);
	public void updateJournal(Journal journal);
	public void deleteJournal(Journal journal);
	public List<Journal> getCustomername();
	public List<Journal> getReceiptTypeName(String customername);
	public List<Journal> getJournalAmt(String id);
	public List<Journal> getJournalUpdateSales(String journalType,String transId);
	public List<Journal> getJournalUpdate(String journalType,String transId);
	public void UpdateStatusID(String id);//add for bug
	public List<String> ViewJournalId(String creditName,String id);//add for view mode retrive
	public void UpdateStatusToAccept(String id);
	public Object getDebitAmount(String fromDate);//Sum of Opening balance for all cash account group as debt
	public Object getCreditAmount(String fromDate);//Sum of Opening balance for all cash account group as credit
	public Object getLedgerDebitAmount(String fromDate, String ledgername); //Get the sum of opening balance for debit account
	public Object getLedgerCreditAmount(String fromDate, String ledgername);//Get the sum of opening balance fro credit account
}
