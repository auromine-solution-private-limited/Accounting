package com.jewellery.dao.hibernate;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.SalesReturnDao;
import com.jewellery.entity.Sales;


public class HibernateSalesReturnDaoImpl extends HibernateDaoSupport implements SalesReturnDao
{
	public static final String LIST_ALL = "listAllSales";
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Sales> listSales(){	
	//	System.out.println("Executing Sales Query:::::::::::::::::::::::::::::::::::::::::::::::::::");
		return (List<Sales>)getHibernateTemplate().findByNamedQuery(LIST_ALL);
	}
	
	@Transactional
	public Sales getSales(Integer id){	
	//	System.out.println("Method getSales:::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		return (Sales)getHibernateTemplate().get(Sales.class, id);
				
	}	
	@Transactional
	public void insertSales(Sales sales) {
		System.out.println("Sales: Executing insert method ::::::::::::");
		getHibernateTemplate().saveOrUpdate(sales);	
	}
		
	@Transactional
	public void updateSales(Sales salesId){
		getHibernateTemplate().update(salesId);		
	}
	
	public void deleteSales(Sales salesId){
		getHibernateTemplate().delete(salesId);
	}	
	@SuppressWarnings("unchecked")
	public BigInteger getLastSalesReturnNo() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(salesTypeId,'R',-1),UNSIGNED INTEGER)) from sales where salesTypeId like 'SR%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}

}