package com.jewellery.dao.hibernate;

import java.math.BigInteger;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.JewelFixDao;
import com.jewellery.entity.JewelFix;

public class HibernateJewelFixDaoImpl extends HibernateDaoSupport implements JewelFixDao
{
	public static final String GET_BY_NAME = "getJewelFixByName";
	public static final String LIST_BY_JEWELFIX= "listJewelFix";	

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<JewelFix> listJewelFix(){	
		return (List<JewelFix>)getHibernateTemplate().findByNamedQuery(LIST_BY_JEWELFIX);
	}

	@Transactional
	public JewelFix getJewelFix(Integer id){

		if(getHibernateTemplate()!=null){
			return (JewelFix)getHibernateTemplate().get(JewelFix.class, id);
		}else{
		System.out.println("getHibernateTemplate() returns null value");
		return (JewelFix)getHibernateTemplate().get(JewelFix.class, id);
		}
	}	
	
	@SuppressWarnings("unchecked")
	public BigInteger getOrderCode(){
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(orderNO,'R',-1),UNSIGNED INTEGER)) FROM JewelFix");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	
	@Transactional(readOnly=true)
	public JewelFix getJewelFix(String customerName){		
		Session session = getSession();
		try{
			Query query = session.getNamedQuery(GET_BY_NAME);
			query.setParameter("Customer Name", customerName);
		
			return (JewelFix)query.uniqueResult();
		}finally{
			releaseSession(session);
		}		
	} 
	
	@Transactional
	public void insertJewelFix(JewelFix jewelfix){
		System.out.println("JewelFix: Executing insert method ::::::::::::");
		getHibernateTemplate().saveOrUpdate(jewelfix);		
	}

	@Transactional
	public void updateJewelFix(JewelFix jewelfix) {
		System.out.println("JewelFix: Executing update method ::::::::::::");
		getHibernateTemplate().update(jewelfix);
    }

	@Transactional
    public void deleteJewelFix(JewelFix jewelfix) {
		System.out.println("JewelFix: Executing delete method ::::::::::::");
      getHibernateTemplate().delete(jewelfix);
    }			
}