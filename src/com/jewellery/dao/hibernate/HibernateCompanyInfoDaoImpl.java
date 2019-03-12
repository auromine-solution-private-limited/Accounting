package com.jewellery.dao.hibernate;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.CompanyInfoDao;
import com.jewellery.entity.CompanyInfo;


public class HibernateCompanyInfoDaoImpl extends HibernateDaoSupport implements CompanyInfoDao {
	
	public static final String LIST_ALL_INFO = "listAllInfo";
	
	@Transactional
	public void insertCompanyInfo(CompanyInfo cinfo){
		System.out.println("CompanyInfo: Executing insert method ::::::::::::");
		getHibernateTemplate().save(cinfo);
	}
	
	@Transactional
	public void updateCompanyInfo(CompanyInfo cinfo){
		System.out.println("CompanyInfo: Executing update method ::::::::::::");
		getHibernateTemplate().update(cinfo);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<CompanyInfo> listCompanyInfo(){
		return (List<CompanyInfo>)getHibernateTemplate().findByNamedQuery(LIST_ALL_INFO);
	}
}
