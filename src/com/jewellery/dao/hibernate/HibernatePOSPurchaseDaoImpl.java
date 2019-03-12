package com.jewellery.dao.hibernate;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.jewellery.dao.POSPuchaseDao;
import com.jewellery.entity.POSPurchase;
import com.jewellery.entity.POSPurchaseItem;


public class HibernatePOSPurchaseDaoImpl extends HibernateDaoSupport implements POSPuchaseDao{
	
	// ************** POS Purchase ************** /
	@Transactional
	public void insertPOSPurchase(POSPurchase posPurchase){		
		getHibernateTemplate().saveOrUpdate(posPurchase);
	}
	
	@Transactional
	public void updatePOSPurchase(POSPurchase posPurchase){		
		getHibernateTemplate().update(posPurchase);
	}
	
	@Transactional
	public void deletePOSPurchase(POSPurchase posPurchase){		
		getHibernateTemplate().delete(posPurchase);
	}
	
	@SuppressWarnings("unchecked") 
	@Transactional
	public List<POSPurchase> listPOSPurchase(){		
		return (List<POSPurchase>) getHibernateTemplate().find("from POSPurchase");
	}
	
	@Transactional
	public POSPurchase getPOSPurchase(Integer purchaseId){
		//System.out.println("POSPurchase: Executing list method :::::::::::::::::");
		return (POSPurchase) getHibernateTemplate().get(POSPurchase.class,purchaseId);
	}
	
	// ************** POS Purchase Item ************** /
		
	@Transactional
	public void insertPOSPurchaseItem(POSPurchaseItem posPurchaseItem){		
		getHibernateTemplate().save(posPurchaseItem);
	}
	@Transactional
	public void updatePOSPurchaseItem(POSPurchaseItem posPurchaseItem){		
		getHibernateTemplate().update(posPurchaseItem);
	}
	@Transactional
	public void deletePOSPurchaseItem(POSPurchaseItem posPurchaseItem){		
		getHibernateTemplate().delete(posPurchaseItem);
	}
			
	@SuppressWarnings("unchecked")
	@Transactional
	public List<POSPurchaseItem> getPOSPurchaseItem(Integer purchaseId){		
		return (List<POSPurchaseItem>) getHibernateTemplate().find("FROM POSPurchaseItem WHERE purchaseId = ?",purchaseId);
	}
		
	@SuppressWarnings("unchecked")
	public BigInteger getLatestPurchaseCode(){
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(transactionNo,'R',-1),UNSIGNED INTEGER)) FROM POSPurchaseItem WHERE transactionNo LIKE 'PR%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	
	// ********************* POP *************************/
	
		
}
