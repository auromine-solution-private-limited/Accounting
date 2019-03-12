package com.jewellery.dao.hibernate;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.entity.Purchase;
import com.jewellery.dao.PurchaseDao;

public class HibernatePurchaseDaoImpl extends HibernateDaoSupport implements PurchaseDao {

	public static final String LIST_ALL = "listAllPurchase";

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Purchase> listPurchase() {	
		return (List<Purchase>)getHibernateTemplate().findByNamedQuery(LIST_ALL);
	}
	
	@SuppressWarnings("unchecked")
	public BigInteger getgoldexchangeinvoiceNo() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(purchseInvoice,'E',-1),UNSIGNED INTEGER)) FROM purchase WHERE purchseInvoice LIKE 'GE%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public BigInteger getPurchaseInvoiceNo() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(purchseInvoice,'P',-1),UNSIGNED INTEGER)) FROM purchase WHERE purchseInvoice LIKE 'OP%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	@SuppressWarnings("unchecked")
	public BigInteger getsilverdexchangeinvoiceNo() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(purchseInvoice,'E',-1),UNSIGNED INTEGER)) FROM purchase WHERE purchseInvoice LIKE 'SE%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
		
	}
	public Purchase getPurchase(Integer id) {
		return (Purchase)getHibernateTemplate().get(Purchase.class,id);
	}

	@Transactional
	public void insertPurchase(Purchase purchase) {
		System.out.println("Purchase: Executing insert method ::::::::::::");
		getHibernateTemplate().saveOrUpdate(purchase);	
	}

	@Transactional
	public void updatePurchase(Purchase purchase) {
		System.out.println("Purchase: Executing update method ::::::::::::");
		getHibernateTemplate().update(purchase);
	}

	@Transactional 
	public void deletePurchase(Purchase purchase) {
		System.out.println("Purchase: Executing delete method ::::::::::::");
		getHibernateTemplate().delete(purchase);
	}
	
	@SuppressWarnings("unchecked")
	public List<Purchase> getPurchaseBillNo(String customer){		
		return (List<Purchase>)getHibernateTemplate().find("SELECT purchseInvoice FROM Purchase WHERE supplierName=? and paymentMode='Credit' and exchangePurchase='Yes' and purchaseType='Purchase' and (bullionType='Gold Exchange' or bullionType='Silver Exchange')  ",customer);
	}

	@SuppressWarnings("unchecked")
	public List<Purchase> getPurchaseAmount(String id){	
		return (List<Purchase>)getHibernateTemplate().find("from Purchase where purchseInvoice = ?", id);
	}
	@SuppressWarnings("unchecked")
	public List<Purchase> diplayPurchaseBillID(String oldCustName,String id){
	//	System.out.println("Dao Printing:::"+oldCustName+"\n"+id);
		Object[] param={oldCustName,id,oldCustName};
		return (List<Purchase>)getHibernateTemplate().find("SELECT purchseInvoice FROM Purchase WHERE (paymentMode='Credit' and exchangePurchase='Yes' and purchaseType='Purchase' and (bullionType='Gold Exchange' or bullionType='Silver Exchange') and supplierName=?) or (purchseInvoice=? and supplierName=?) ", param);
	}

	public void UpdateStatusToAccept(String id){
		String query=("update Purchase set exchangePurchase='Yes' where  purchseInvoice=?");
		getHibernateTemplate().bulkUpdate(query, id);
	}
}
