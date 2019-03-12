package com.jewellery.dao.hibernate;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.jewellery.dao.RefineryReceiptDao;

import com.jewellery.entity.RefineryReceipt;

public class HibernateRefineryReceiptDaoDaoImpl extends HibernateDaoSupport implements RefineryReceiptDao {
	@Transactional	
	public void insertRefineryReceipt(RefineryReceipt refinery){
		getHibernateTemplate().saveOrUpdate(refinery);
	}
	@Transactional
	public void updateRefineryReceipt(RefineryReceipt refinery){
		getHibernateTemplate().update(refinery);
	}

	public RefineryReceipt getRefineryReceipt(Integer refineryId){
		return (RefineryReceipt)getHibernateTemplate().get(RefineryReceipt.class, refineryId);
	}
	@SuppressWarnings("unchecked")
	public List<RefineryReceipt> listRefineryReceipt(){
		return (List<RefineryReceipt>) getHibernateTemplate().find("FROM RefineryReceipt");
	}
	
	public BigInteger getRReceiptNo(){
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(refineryReceiptNo,'R',-1),UNSIGNED INTEGER)) FROM RefineryReceipt WHERE refineryReceiptNo LIKE 'RR%'");
		@SuppressWarnings("unchecked")
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	@SuppressWarnings("unchecked")
	public List<RefineryReceipt> getrefineryReceiptId(Integer rrId){
		return (List<RefineryReceipt>) getHibernateTemplate().find("FROM RefineryReceipt WHERE rrId = ?",rrId);
	}
}
