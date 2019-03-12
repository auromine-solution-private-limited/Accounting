package com.jewellery.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.LedgerDao;
import com.jewellery.entity.Ledger;


public class HibernateLedgerDaoImpl extends HibernateDaoSupport implements LedgerDao
{
	public static final String LIST_PARTY_LEDGERS = "listAllLedger";
	public static final String LIST_ALL_LEDGERS = "listAllLedger";
	public static final String LIST_LEDGER_NAME = "listLedgerName";
	public static final String LIST_CUSTOMER_NAME_ONLY = "listCustomerNameOnly";
	public static final String LIST_SUPPLIER_NAME_ONLY = "listSupplierNameOnly";
	public static final String LIST_ALL_SMITH="listAllsmithname";
	public static final String RECIEPTDEBIT_LIST_ALL_JOURNAL="jtypeRecieptDebitside";
	public static final String RECIEPTCREDIT_LIST_ALL_JOURNAL="jtypeRecieptCreditside";
	public static final String LIST_JOURNAL_ACCOUNT="journal";
	public static final String LIST_ALL_CUSTOMER="listCustomerName";
	public static final String LIST_BANK="listBank";
	public static final String LIST_POS_BANK="listPOSBank";
	public static final String LIST_Cash_Account="listCashAccount";//added for salesorder receipt mode cash 
	public static final String LIST_CURRENT_ASSETS="listCurrentAssets";
	public static final String GET_CUSTOMER_NAMES="getCustomerType";
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Ledger> listLedger(){	
		return (List<Ledger>)getHibernateTemplate().findByNamedQuery(LIST_ALL_LEDGERS);
	} 
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<String> getAutoLedgerNames(String lNamePart){
		Object[] obj = {lNamePart+"%"};
		return (List<String>)getHibernateTemplate().find("SELECT ledgerName FROM Ledger WHERE ledgerName like ?",obj);		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<String> getAutoSupplierNames(String sNamePart){
		Object[] obj = {sNamePart+"%"};
		return (List<String>)getHibernateTemplate().find("SELECT ledgerName FROM Ledger WHERE accountGroup='Sundry Creditors' AND ledgerName like ?",obj);		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<String> getSupplierValidation(String suppName){
		return (List<String>)getHibernateTemplate().find("SELECT ledgerName FROM Ledger WHERE accountGroup='Sundry Creditors' AND ledgerName = ?",suppName);		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<String> getautoCustomerName(String sNamePart){
		Object[] obj = {sNamePart+"%"};
		return (List<String>)getHibernateTemplate().find("SELECT ledgerName FROM Ledger WHERE accountGroup='Sundry Debtors' AND ledgerName like ?",obj);		
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<String> getLedgerName() {			
		return (List<String>)getHibernateTemplate().find("SELECT ledgerName from Ledger");
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Ledger> listPartyLedger(){	
		return (List<Ledger>)getHibernateTemplate().findByNamedQuery(LIST_PARTY_LEDGERS);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Ledger> listBank(){	
		return (List<Ledger>)getHibernateTemplate().findByNamedQuery(LIST_BANK);
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Ledger> listPosBank(){	
		return (List<Ledger>)getHibernateTemplate().findByNamedQuery(LIST_POS_BANK);
	}
	//added for salesorder receipt mode cash 
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Ledger> listCashAccount(){	
		return (List<Ledger>)getHibernateTemplate().findByNamedQuery(LIST_Cash_Account);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Ledger> listCurrentAssets(){	
		return (List<Ledger>)getHibernateTemplate().findByNamedQuery(LIST_CURRENT_ASSETS);
	}
	
	@Transactional
	public void updatePartyBalance(BigDecimal closingAmount, String closingType, String partyName){		
			Object[] param = {closingAmount,closingType ,partyName};		
			String query = "update Ledger l set l.closingTotalBalance = l.closingTotalBalance +  ?, l.closingTotalType = ? where l.ledgerName = ?";
			getHibernateTemplate().bulkUpdate(query, param);
	}	
	
	@Transactional
	public void updateCreditPartyBalance(BigDecimal closingAmount, String closingType, String partyName){		
		Object[] param = {closingAmount,closingType ,partyName};
		String query = "update Ledger l set l.closingTotalBalance = l.closingTotalBalance -  ?, l.closingTotalType = ? where l.ledgerName = ?";
		getHibernateTemplate().bulkUpdate(query, param);
	}
	
	@Transactional
	public void updateCrPartyBalance(BigDecimal closingAmount, String closingType, String partyName){	
		Object[] param = {closingAmount,closingType ,partyName};
		String query = "update Ledger l set l.closingTotalBalance = ? - l.closingTotalBalance , l.closingTotalType = ? where l.ledgerName = ?";
		getHibernateTemplate().bulkUpdate(query, param);
	}
	
	@Transactional
	public void updateLedgerPartyBalance(BigDecimal closingAmount, String closingType, String partyName){		
			Object[] param = {closingAmount,closingType ,partyName};		
			String query = "update Ledger l set l.closingTotalBalance = ?, l.closingTotalType = ? where l.ledgerName = ?";
			getHibernateTemplate().bulkUpdate(query, param);
	}	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Ledger> listLedgerName() {
		return (List<Ledger>)getHibernateTemplate().findByNamedQuery(LIST_LEDGER_NAME);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Ledger> listCustomerName() {
		return (List<Ledger>)getHibernateTemplate().findByNamedQuery(LIST_CUSTOMER_NAME_ONLY);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Ledger> listSupplierNameOnly() {
		return (List<Ledger>)getHibernateTemplate().findByNamedQuery(LIST_SUPPLIER_NAME_ONLY);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Ledger> listallSmithName() {
		return (List<Ledger>)getHibernateTemplate().findByNamedQuery(LIST_ALL_SMITH);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Ledger> listCustomerPan(String cname){
		//System.out.println(cname);
		return (List<Ledger>)getHibernateTemplate().findByNamedQuery(GET_CUSTOMER_NAMES,cname);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Ledger> listJournalRecieptDebit() 
	{
		return (List<Ledger>)getHibernateTemplate().findByNamedQuery(RECIEPTDEBIT_LIST_ALL_JOURNAL);
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Ledger> listJournalRecieptCredit() 
	{
		return (List<Ledger>)getHibernateTemplate().findByNamedQuery(RECIEPTCREDIT_LIST_ALL_JOURNAL);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
		public List<Ledger> searchLedgerAccount(String ledger){//changed 
			Integer id;
		try{
			id=Integer.parseInt(ledger);
			}
		catch(NumberFormatException e)
			{
				id=null;				
			}								
				List<Ledger> accounts = null;
				if(ledger != null)				
				{
					Object[] obj = {id, ledger};	
					accounts = getHibernateTemplate().find("from Ledger l where l.ledgerId = ? or l.ledgerName = ?", obj);
				}
					
			if (accounts != null)
			{
				return accounts;
			}
			return null;		
	}
	//list of customers
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Ledger> listallCustomerName()
	{
		return (List<Ledger>)getHibernateTemplate().findByNamedQuery(LIST_ALL_CUSTOMER);
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Ledger> searchLedger(String ledger){
			List<Ledger> accounts = getHibernateTemplate().find("from Ledger l where l.ledgerName = ?", ledger);	
		
			if (accounts != null)
			{
				return accounts;
			}
			return null;		
	}

	@Transactional
	public Ledger getLedger(Integer csAccountId){	
	//	System.out.println("Method getLedger:::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		return (Ledger)getHibernateTemplate().get(Ledger.class, csAccountId);
	}	
		
	@Transactional
	public void updateLedger(Ledger ledgerId){
		System.out.println("Mehtod update ledger excuting::::::::::");
		getHibernateTemplate().update(ledgerId);
	}
	
	public void deleteLedger(Ledger csAccountId){
		getHibernateTemplate().delete(csAccountId);
	}	
	public void insertLedger(Ledger csAccountId){
		getHibernateTemplate().saveOrUpdate(csAccountId);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Ledger> listjournal() {
		
		return (List<Ledger>)getHibernateTemplate().findByNamedQuery(LIST_JOURNAL_ACCOUNT);
	}

	@SuppressWarnings("unchecked")
	public List<Ledger> listCustomerPOPUP(String cname){
		
		return (List<Ledger>)getHibernateTemplate().find("SELECT ledgerName FROM Ledger WHERE accountGroup='Sundry Debtors'  AND ledgerName = ?",cname);	
		
	}

	@SuppressWarnings("unchecked")
	public List<String> getLedgerGroupCode(String ledgerGroupcode) {		
		String query ="SELECT accountGroupCode FROM Ledger WHERE accountGroup = ? ";
		return (List<String>)getHibernateTemplate().find(query,ledgerGroupcode);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getLedgerAccountCode(String ledgerName) {		
		String query ="SELECT accountGroupCode FROM Ledger WHERE ledgerName = ?";
		return (List<String>)getHibernateTemplate().find(query, ledgerName);
	}
	
}