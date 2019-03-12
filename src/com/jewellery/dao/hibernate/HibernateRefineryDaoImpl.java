package com.jewellery.dao.hibernate;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.jewellery.dao.RefineryDao;

import com.jewellery.entity.RefineryIssue;


public class HibernateRefineryDaoImpl extends HibernateDaoSupport implements RefineryDao{
@Transactional	
public void insertRefinery(RefineryIssue refinery){
	getHibernateTemplate().saveOrUpdate(refinery);
}
@Transactional
public void updateRefinery(RefineryIssue refinery){
	getHibernateTemplate().update(refinery);
}

public RefineryIssue getRefinery(Integer refineryId){
	return (RefineryIssue)getHibernateTemplate().get(RefineryIssue.class, refineryId);
}
public BigInteger getRefineryIssueNo(){
	Session session = getSession();
	Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(refineryIssueNo,'I',-1),UNSIGNED INTEGER)) FROM RefineryIssue WHERE refineryIssueNo LIKE 'RI%'");
	@SuppressWarnings("unchecked")
	List<BigInteger> resultBig = query.list();
	session.close();		
	return resultBig.get(0);
}
@SuppressWarnings("unchecked")
public List<RefineryIssue> listRefinery(){
	return (List<RefineryIssue>) getHibernateTemplate().find("FROM RefineryIssue");
}
@SuppressWarnings("unchecked")
public List<RefineryIssue> getrefineryId(Integer refineryId){
	return (List<RefineryIssue>) getHibernateTemplate().find("FROM RefineryIssue WHERE refineryId = ?",refineryId);
}
}
