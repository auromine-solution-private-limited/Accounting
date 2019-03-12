package com.jewellery.dao.hibernate;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.jewellery.dao.CardIssueDao;
import com.jewellery.entity.CardIssue;
public class HibernateCardIssueDaoImpl extends HibernateDaoSupport implements CardIssueDao{
	
	public static final String LIST_BY_CARDISSUE_ID = "cardList";
		
	@Transactional
	public void insertCardIssue(CardIssue cardIssue){		
		getHibernateTemplate().save(cardIssue);
	}
	
	@Transactional
	public void updateCardIssue(CardIssue cardIssue){		
		getHibernateTemplate().update(cardIssue);
	}
	
	@Transactional
	public void deleteCardIssue(CardIssue cardIssue){		
		getHibernateTemplate().delete(cardIssue);
	}
	// For SSReceipt customerName List
	@SuppressWarnings("unchecked")
	public List<String> getCustomerActiveSet() {
		return (List<String>)getHibernateTemplate().find("SELECT DISTINCT customerName FROM CardIssue WHERE status='Active'");
	}
	
	// For SSReceipt cardDetail based on Card No
	@SuppressWarnings("unchecked")
	public List<CardIssue> getCardDetailList(String cardNo) {
		return (List<CardIssue>)getHibernateTemplate().find("FROM CardIssue WHERE status='Active' and cardNo = ?",cardNo);
	}
	
	// For SSPayment customerName List
	@SuppressWarnings("unchecked")
	public List<String> getCustomerActiveMatureSet() {
		return (List<String>)getHibernateTemplate().find("SELECT DISTINCT customerName FROM CardIssue WHERE status='Active' or status='Matured'");
	}

	// For SSReceipt SchemeList
	@SuppressWarnings("unchecked")
	public List<CardIssue> getSSRCustomerSchemeList(String customerName){
		return (List<CardIssue>) getHibernateTemplate().find("SELECT DISTINCT schemeName FROM CardIssue WHERE customerName = ? and status='Active'",customerName);
	}
		
	// For SSPayment SchemeList
	@SuppressWarnings("unchecked")
	public List<CardIssue> getSSCCustomerSchemeList(String customerName){
		return (List<CardIssue>) getHibernateTemplate().find("SELECT DISTINCT schemeName FROM CardIssue WHERE customerName = ? and (status='Active' or status='Matured') ",customerName);
	}
	
	// For SSReceipt View Mode "Scheme list" 
	@SuppressWarnings("unchecked")
	public List<String> SSRSchemeSet(String customerName){
		return (List<String>) getHibernateTemplate().find("SELECT DISTINCT schemeName FROM CardIssue WHERE customerName = ? and status='Active' ",customerName);
	}
	
	// For SSPayment View Mode "Scheme list" 
	@SuppressWarnings("unchecked")
	public List<String> SSCSchemeSet(String customerName){
		return (List<String>) getHibernateTemplate().find("SELECT DISTINCT schemeName FROM CardIssue WHERE customerName = ? and (status='Active' or status='Matured')",customerName);
	}
	
	// For SSReceipt View Mode "card list" 
	@SuppressWarnings("unchecked")
	public List<String> SSRCardNoSet(String customerName,String SchemeName){
		Object[] param = {customerName,SchemeName};
		return (List<String>) getHibernateTemplate().find("SELECT DISTINCT cardNo FROM CardIssue where customerName = ? and schemeName = ? and (status='Active')",param);
	}
	
	// For SSPayment View Mode "card list" 
	@SuppressWarnings("unchecked")
	public List<String> SSCCardNoSet(String customerName,String SchemeName){
		Object[] param = {customerName,SchemeName};
		return (List<String>) getHibernateTemplate().find("SELECT DISTINCT cardNo FROM CardIssue where customerName = ? and schemeName = ? and (status='Active' or status='Matured')",param);
	}
	
		
	// For SSReceipt CardIssueDao ajax call
	@SuppressWarnings("unchecked")
	public List<CardIssue> SSRgetCardDetails(String customerName,String SchemeName){
		Object[] param = {customerName,SchemeName};
		return (List<CardIssue>) getHibernateTemplate().find("SELECT cardNo from CardIssue where customerName = ? and schemeName = ? and status='Active')",param);
	}
	
	// For SSPayment CardIssueDao ajax call
	@SuppressWarnings("unchecked")
	public List<CardIssue> SSCgetCardDetails(String customerName,String SchemeName){
		Object[] param = {customerName,SchemeName};
		return (List<CardIssue>) getHibernateTemplate().find("SELECT cardNo from CardIssue where customerName = ? and schemeName = ? and (status='Active' or status='Matured')",param);
	}
	
	// For Sales to Retrieve Card NO List Based on Customer Name
	@SuppressWarnings("unchecked")
	public List<String> SSSalesCardNoList(String customerName){
		return (List<String>) getHibernateTemplate().find("SELECT cardNo FROM CardIssue where customerName = ? and (status='Active' or status='Matured')",customerName);
	}
		
	// For CardIssueDao and Saving Scheme Calculation
	@SuppressWarnings("unchecked")
	public List<CardIssue> getCardNoDetails(String cardNo){
		return (List<CardIssue>) getHibernateTemplate().find("from CardIssue where cardNo= ? and (status='Active' or status='Matured')",cardNo);
	}
	
	// For CardIssueDao
	@SuppressWarnings("unchecked")
	public List<CardIssue> getCardIssueList(String customerName, String cardNO){
		Object[] param = {customerName,cardNO};
		return (List<CardIssue>) getHibernateTemplate().find("from CardIssue where customerName= ? and cardNo = ? and (status='Active' or status='Matured')",param);
	}
	
	// For CardIssueDao
	@SuppressWarnings("unchecked")
	public List<CardIssue> getSSRPLedgerCardIssueList(String customerName, String cardNO){
		Object[] param = {customerName,cardNO};
		return (List<CardIssue>) getHibernateTemplate().find("from CardIssue where customerName= ? and cardNo = ?",param);
	}
	
	// For CardIssueDao ajax call
	public String getSchemeType(String SchemeName){
		return getHibernateTemplate().find("select DISTINCT schemeType from CardIssue where schemeName = ? and (status='Active' or status='Matured')",SchemeName).toString();
	}
	
	// For SSReceipt and SSPayment to retrieve cardIssue based on CardNO
	@SuppressWarnings("unchecked")
	public List<CardIssue> getCardIssueCardNO(String cardNO){
		return (List<CardIssue>) getHibernateTemplate().find("FROM CardIssue WHERE cardNo = ?", cardNO);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<CardIssue> cardissueList() {
		return (List<CardIssue>)getHibernateTemplate().findByNamedQuery(LIST_BY_CARDISSUE_ID);
	}
	
	public CardIssue getissuedCardById(Integer id) {
		if(getHibernateTemplate()!=null){
			return (CardIssue)getHibernateTemplate().get(CardIssue.class, id);
		}
		else{
			return (CardIssue)getHibernateTemplate().get(CardIssue.class, id);
		}
	} 
	@SuppressWarnings("unchecked")
	public BigInteger amountinGrams() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(cardNo,'G',-1),UNSIGNED INTEGER)) FROM CardIssue where cardNo LIKE 'AG%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public BigInteger amountinCash() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(cardNo,'C',-1),UNSIGNED INTEGER)) FROM CardIssue where cardNo LIKE 'AC%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	
	public String getIssuedSchemeName(String schemeName) {		
		Session session = getSession();
		try{
		Query query=session.createQuery("SELECT schemeName FROM CardIssue WHERE schemeName = :schemeName");
		query.setParameter("schemeName", schemeName);
		String list=(String)query.list().get(0);
			return(list);
			}
		catch(Exception e){}
		finally{
			releaseSession(session);
		}
		return "empty";	 
	}

	/*public String getjoiningDate(String schemeName) {
		Session session = getSession();
		//return getHibernateTemplate().find("SELECT  dateOfJoining FROM CardIssue WHERE schemeName = ? ",schemeName).toString();
		String sql = "SELECT DISTINCT(DATE_FORMAT(dateOfJoining, '%d/%m/%Y'))  FROM CardIssue WHERE schemeName = :schemeName";
				Query query = session.createQuery(sql);
				query.setParameter("schemeName", schemeName);
				@SuppressWarnings("unchecked")
				List<String> result = query.list();
				
		return result.toString();
	}
	*/

	
}
