package com.jewellery.dao.hibernate;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.SalesDao;
import com.jewellery.entity.Sales;


public class HibernateSalesDaoImpl extends HibernateDaoSupport implements SalesDao
{
	public static final String LIST_ALL = "listAllSales";
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Sales> listSales(){
		return (List<Sales>)getHibernateTemplate().findByNamedQuery(LIST_ALL);
	}
	
	@Transactional
	public Sales getSales(Integer id){
		return (Sales)getHibernateTemplate().get(Sales.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Sales> getSalesByIdList(Integer salesId){
		return (List<Sales>) getHibernateTemplate().find("FROM Sales WHERE salesId = ?",salesId);
	}
			
	
	@SuppressWarnings("unchecked")
	public BigInteger getLatestBillNo(){
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(billNo,'S',-1),UNSIGNED INTEGER)) from sales where billNo like 'OS%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public BigInteger getLastGoldTypeNo(){
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(salesTypeId,'S',-1),UNSIGNED INTEGER)) from sales where salesTypeId like 'GS%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public BigInteger getLastEstimSalesNo(){
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(salesTypeId,'S',-1),UNSIGNED INTEGER)) from sales where salesTypeId like 'ES%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public BigInteger getLastSilverTypeNo(){
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(salesTypeId,'S',-1),UNSIGNED INTEGER)) from sales where salesTypeId like 'SS%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
		
	@SuppressWarnings("unchecked")
	public BigInteger getLatestBullionNo()
	{
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(billNo,'S',-1),UNSIGNED INTEGER)) from sales where billNo like 'BS%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public BigInteger getLastBullionGoldTypeId() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(salesTypeId,'B',-1), UNSIGNED INTEGER)) from sales where salesTypeId like 'GB%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public BigInteger getLastBullionSilverTypeId() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(salesTypeId,'B',-1), UNSIGNED INTEGER)) from sales where salesTypeId like 'SB%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}	
	@Transactional
	public void insertSales(Sales sales) {
		getHibernateTemplate().saveOrUpdate(sales);	
	}
	/* Method for update sales */
	@Transactional
	public void updateSales(Sales sales){
		getHibernateTemplate().update(sales);		
	}
	
	public void deleteSales(Sales salesId){
		getHibernateTemplate().delete(salesId);
	}
	
    /************** For SR Id *************/
	@SuppressWarnings("unchecked")
	public List<Sales> getSalesReturnId(String customer){
		return (List<Sales>)getHibernateTemplate().find("SELECT salesTypeId FROM Sales WHERE customerName=? and paymentMode='Credit' and salesReturnStatus='Yes' and salestype='Sales Return'",customer);
	}
	// for SR ID amount 
	@SuppressWarnings("unchecked")
	public List<Sales> getSRAmount(String id){
		return (List<Sales>)getHibernateTemplate().find("from Sales where salesTypeId = ?", id);
	}
	// for Not to show SR id in View mode 
	public void UpdateSRID(String id){
		String query=("update Sales set salesReturnStatus='Sold' where salesTypeId=?");
		getHibernateTemplate().bulkUpdate(query, id);
	}
	// for SOlD to Yes change SR ID
	public void UpdateSRToYes(String id){
		String query=("update Sales set salesReturnStatus='Yes' where salesTypeId=?");
		getHibernateTemplate().bulkUpdate(query, id);
	}
	// for SR Id reterving in View Mode
	@SuppressWarnings("unchecked")
	public List<Sales> ViewSRId(String CustomerName,String id){
	//	System.out.println("Retrive SalesOrderID Executing:::::::::::::");
		Object[] param={CustomerName,id};
		String query=("select salesTypeId from Sales where ((customerName = ? and salesReturnStatus='Yes' ) or salesTypeId=?)");
		return (List<Sales>)getHibernateTemplate().find(query, param);
	}
	
}