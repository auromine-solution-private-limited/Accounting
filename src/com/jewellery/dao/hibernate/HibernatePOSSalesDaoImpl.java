package com.jewellery.dao.hibernate;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.POSSalesDao;
import com.jewellery.entity.POSSales;
import com.jewellery.entity.POSSalesItem;
import com.jewellery.entity.PSales;

public class HibernatePOSSalesDaoImpl extends HibernateDaoSupport implements POSSalesDao{
	
	public static final String LIST_ALL = "listAllPOSSalesItem";
	
	@Transactional
	public PSales getPSales(Integer id){
		return (PSales)getHibernateTemplate().get(PSales.class, id);
				 
	}	
	@Transactional
	public void insertPOSSales(POSSales sales) {
		getHibernateTemplate().save(sales);	
		
	}
	
	/* Method for update sales */
	@Transactional
	public void updatePOSSales(POSSales posSales){
		getHibernateTemplate().update(posSales);		
	}
	
	public void deletePOSSales(POSSales salesId){
		getHibernateTemplate().delete(salesId);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<POSSales> getPOSsales(Integer salesId){
		System.out.println("POSSales Executing List ::::::::::::::::");
		return (List<POSSales>) getHibernateTemplate().find("FROM POSSales WHERE posSalesId = ?",salesId);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<POSSales> getPOSInov(String salesId){
		System.out.println("POSSales Executing List ::::::::::::::::");
		return (List<POSSales>) getHibernateTemplate().find("FROM POSSales WHERE billNo = ?",salesId);
	}
	
	@Transactional
	public POSSales getPOSSales(Integer salesId){
		//System.out.println("POSSales: Executing list method :::::::::::::::::");
		return (POSSales) getHibernateTemplate().get(POSSales.class,salesId);
	}
	
	/**POSSaleItem insert,update,delete methods */
	@Transactional
	public void insertPOSSalesItem(POSSalesItem sales) {
		
		getHibernateTemplate().save(sales);	
	}
	public void updatePOSSalesItem(POSSalesItem sales){
		getHibernateTemplate().update(sales);		
	}
	
	public void deletePOSSalesItem(POSSalesItem salesId){
		getHibernateTemplate().delete(salesId);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true) 
	public List<POSSalesItem> listSalesItem() {	
		//return (List<POSSalesItem>)getHibernateTemplate().find("from POSSales s inner join s.POSSalesItem on s.posSalesId=POSSalesItem.posSalesId");
		return (List<POSSalesItem>)getHibernateTemplate().find("from POSSales ");
	}
		
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<POSSalesItem> listPOSItems() {	
		
		return (List<POSSalesItem>)getHibernateTemplate().find("from POSSalesItem ");
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<POSSalesItem> getPOSSalesItem(Integer salesId, String saletype){ 
		
		Object[] param = {salesId, saletype};
		return (List<POSSalesItem>) getHibernateTemplate().find("FROM POSSalesItem WHERE (posSalesId = ? AND itemSalesType= ? )", param);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public  List<POSSalesItem> getPOSSalesSearchItem(String salesId){
		System.out.println("Inside the dao of sales return search box :::::");
		Session session = getSession();
		Query query = session.createSQLQuery("SELECT POSSalesItem.salesItemID,POSSalesItem.positemcode,POSSalesItem.categoryName,POSSalesItem.itemName,POSSalesItem.quantity,POSSalesItem.salesRate,POSSalesItem.discountAmount,POSSalesItem.salesAmount,POSSales.billno FROM POSSalesItem INNER JOIN POSSales  on (POSSalesItem.posSalesId = POSSales.posSalesId) WHERE (POSSales.billNo =:billNo AND POSSalesItem.itemSalesType='POS Sales' AND POSSalesItem.itemStatus!='Return' And POSSalesItem.POSdeleted='true')");
		query.setParameter("billNo", salesId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return    query.list();
	}
	
public POSSalesItem getSalesItem(Integer id) {
		
		return(POSSalesItem)getHibernateTemplate().get(POSSalesItem.class, id);
	}



@SuppressWarnings("unchecked")
	@Transactional
	public List<POSSalesItem> getPOSSoldItem(String itemcode){		
			return (List<POSSalesItem> ) getHibernateTemplate().find("From POSSalesItem where itemSalesType='POS Sales' and itemStatus='Sold' and posItemCode=?",itemcode);
	}
//added for Estimate pos sales on 4-1-13
@SuppressWarnings("unchecked")
public BigInteger getLastEstimPOSSalesNo(){
	Session session = getSession();
	Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(billNo,'P',-1),UNSIGNED INTEGER)) from POSSales where salesType='Estimate POS Sales' and billNo like 'EP%'");
	List<BigInteger> resultBig = query.list();
	session.close();		
	return resultBig.get(0);
}
//added for  pos sales on 7-1-13
@SuppressWarnings("unchecked")
public BigInteger getLastPOSSalesNo(){
	Session session = getSession();
	Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(billNo,'S',-1),UNSIGNED INTEGER)) from POSSales where salesType='POS Sales' and billNo like 'POS%'");
	List<BigInteger> resultBig = query.list();
	session.close();		
	return resultBig.get(0);
}
//added for  pos sales Return on 10-1-13
@SuppressWarnings("unchecked")
public BigInteger getLastPORInvNo(){
	Session session = getSession();
	Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(billNo,'R',-1),UNSIGNED INTEGER)) from POSSales where salesType='POS Sales Return' and billNo like 'POR%'");
	List<BigInteger> resultBig = query.list();
	session.close();		
	return resultBig.get(0);
}

}
