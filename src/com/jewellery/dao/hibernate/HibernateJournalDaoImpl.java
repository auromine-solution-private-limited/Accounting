package com.jewellery.dao.hibernate;

import java.math.BigInteger;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.JournalDao;
import com.jewellery.entity.Journal;

public class HibernateJournalDaoImpl extends HibernateDaoSupport implements	JournalDao {

	public static final String LIST_ALL = "listAllJournal";

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Journal> listJournal() {
		return (List<Journal>) getHibernateTemplate().findByNamedQuery(LIST_ALL);
	}

	@SuppressWarnings("unchecked")
	public BigInteger getReceiptCode() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(journalNO,'C',-1),UNSIGNED INTEGER)) from Journal where journalNO like 'RC%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}

	@SuppressWarnings("unchecked")
	public BigInteger getPaymentCode() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(journalNO,'T',-1),UNSIGNED INTEGER)) from Journal where journalNO like 'PT%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}

	@SuppressWarnings("unchecked")
	public BigInteger getJournalCode() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(journalNO,'L',-1),UNSIGNED INTEGER)) from Journal where journalNO like 'JL%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}

	@SuppressWarnings("unchecked")
	public BigInteger getContraCode() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(journalNO,'T',-1),UNSIGNED INTEGER)) from Journal where journalNO like 'CT%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}

	@SuppressWarnings("unchecked")
	public BigInteger getAdvanceCode() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(journalNO,'R',-1),UNSIGNED INTEGER)) from Journal where journalNO like 'AR%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}

	@SuppressWarnings("unchecked")
	public BigInteger getJewelRepairCode() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(journalNO,'R',-1),UNSIGNED INTEGER)) from Journal where journalNO like 'JR%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}

	@SuppressWarnings("unchecked")
	public BigInteger getBillCode() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(journalNO,'R',-1),UNSIGNED INTEGER)) from Journal where journalNO like 'BR%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}

	@SuppressWarnings("unchecked")
	public BigInteger getSavingCode() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(journalNO,'R',-1),UNSIGNED INTEGER)) from Journal where journalNO like 'SR%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	
	/** for automatic journal entries **/
	@SuppressWarnings("unchecked")
	public BigInteger getAutoJournalCode() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(journalNO,'E',-1),UNSIGNED INTEGER)) from Journal where journalNO like 'JE%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}

	public Journal getJournal(Integer id) {
		return (Journal) getHibernateTemplate().get(Journal.class, id);
	}

	@Transactional
	public void insertJournal(Journal purchase) {
		System.out.println("Journal: Executing insert method ::::::::::::");
		getHibernateTemplate().saveOrUpdate(purchase);
	}

	@Transactional
	public void updateJournal(Journal purchase) {
		System.out.println("Journal: Executing insert method ::::::::::::");
		getHibernateTemplate().update(purchase);
	}

	@Transactional
	public void deleteJournal(Journal purchase) {
		System.out.println("Journal: Executing insert method ::::::::::::");
		getHibernateTemplate().delete(purchase);
	}

	@SuppressWarnings("unchecked")
	public List<Journal> getCustomername() {

		return (List<Journal>) getHibernateTemplate().find(
				"select journalId from Journal ");
	}

	@SuppressWarnings("unchecked")
	public List<Journal> getReceiptTypeName(String customername) {
		// System.out.println(name);
		return (List<Journal>) getHibernateTemplate()
				.find("select journalNO from Journal where (narration!='Advance Receive'  and receiptType='Advance Receipt' and creditAccountName=?) ",
						customername);
	}

	/* Method for sales Update */
	@SuppressWarnings("unchecked")
	public List<Journal> getJournalUpdateSales(String journalType,
			String transId) {
		return (List<Journal>) getHibernateTemplate().find(
				"from Journal where journalType = ? and transactionId = ?",
				journalType, transId);
	}

	/* Method for Journal Update based on journal Type and Transaction ID */
	@SuppressWarnings("unchecked")
	public List<Journal> getJournalUpdate(String journalType, String transId) {
		return (List<Journal>) getHibernateTemplate().find(
				"from Journal where journalType = ? and transactionId = ?",
				journalType, transId);
	}

	@SuppressWarnings("unchecked")
	public List<Journal> getJournalAmt(String id) {
		// System.out.println(id);
		return (List<Journal>) getHibernateTemplate().find(
				"from Journal where journalNO=?", id);
	}

	// add for bug (TO remove the receipt id)
	public void UpdateStatusID(String id) {
		String query = ("update Journal set narration='Advance Receive' where journalNO=?");
		getHibernateTemplate().bulkUpdate(query, id);
	}

	// add for retreving sold id to sale 12-9-12
	public void UpdateStatusToAccept(String id) {
		String query = ("update Journal set narration='Accepted' where journalNO=?");
		getHibernateTemplate().bulkUpdate(query, id);
	}

	// add for retreving recept id in view mode
	@SuppressWarnings("unchecked")
	public List<String> ViewJournalId(String creditName, String id) {
		System.out.println("JournalView Id executing :::::::::::");
		Object[] param = { creditName, id };
		String query = ("select journalNO from Journal where ((narration!='Advance Receive' and receiptType='Advance Receipt' and creditAccountName=?) or journalNO=?)");
		System.out.println("in view mode receipt id::::" + id);
		return (List<String>) getHibernateTemplate().find(query, param);
	}

	
	
	public Object getDebitAmount(String fromDate) {
		
		Session session = getSession();
		Query query=session.createSQLQuery("SELECT SUM(Journal.debitAmount) AS debitSum FROM Journal INNER JOIN Ledger ON Journal.debitAccountName = Ledger.ledgerName WHERE Journal.journalDate < str_to_date(:journalDate,'%d/%m/%Y') AND Ledger.accountGroup='Cash Account'");
		query.setParameter("journalDate", fromDate);
	
		@SuppressWarnings("rawtypes")
		List list=query.list();
		if(list.get(0)!=null)
		{
			return list.get(0);
		}
		else
			return 0.00;
	
	}
	public Object getCreditAmount(String fromDate) {
		
		Session session = getSession();
		Query query=session.createSQLQuery("SELECT SUM(Journal.creditAmount) AS CreditSum FROM Journal INNER JOIN Ledger ON Journal.creditAccountName = Ledger.ledgerName WHERE Journal.journalDate < str_to_date(:journalDate,'%d/%m/%Y') AND Ledger.accountGroup='Cash Account'");
		query.setParameter("journalDate", fromDate);
		@SuppressWarnings("rawtypes")
		List list=query.list();
		if(list.get(0)!=null)
		{
			return list.get(0);
		}
		else
			return 0.00;
	}
	
	
	public Object getLedgerDebitAmount(String fromDate, String ledgername) {
		
		Session session = getSession();
		Query query=session.createSQLQuery("SELECT SUM(Journal.debitAmount) AS debitSum FROM Journal INNER JOIN Ledger ON Journal.debitAccountName = Ledger.ledgerName WHERE Journal.journalDate < str_to_date(:journalDate,'%d/%m/%Y') AND Ledger.ledgerName=:LedgerName");
		query.setParameter("journalDate", fromDate);
		query.setParameter("LedgerName", ledgername);
		@SuppressWarnings("rawtypes")
		List list=query.list();
		if(list.get(0)!=null)
		{
			return list.get(0);
		}
		else
			return 0.00;
	
	}
	public Object getLedgerCreditAmount(String fromDate, String ledgername) {
		
		Session session = getSession();
		Query query=session.createSQLQuery("SELECT SUM(Journal.creditAmount) AS CreditSum FROM Journal INNER JOIN Ledger ON Journal.creditAccountName = Ledger.ledgerName WHERE Journal.journalDate < str_to_date(:journalDate,'%d/%m/%Y') AND Ledger.ledgerName=:LedgerName");
		query.setParameter("journalDate", fromDate);
		query.setParameter("LedgerName", ledgername);
		@SuppressWarnings("rawtypes")
		List list=query.list();
		if(list.get(0)!=null)
		{
			return list.get(0);
		}
		else
			return 0.00;
	}

}
