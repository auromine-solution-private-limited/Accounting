package com.jewellery.dao.hibernate;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.jewellery.dao.UtilMethodsDao;

public class HibernateUtilMethodsDaoImpl extends HibernateDaoSupport implements UtilMethodsDao {
	
	public String capitalizeFirstLetter(String original){
	    if(original.length() <= 1){
	    	return original;
	    }else{
	    	return original.substring(0, 1).toUpperCase() + original.substring(1);	
	    }
	}
}
