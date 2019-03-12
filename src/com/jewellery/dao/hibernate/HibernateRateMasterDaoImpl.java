package com.jewellery.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.RateMasterDao;
import com.jewellery.entity.RateMaster;

public class HibernateRateMasterDaoImpl extends HibernateDaoSupport implements RateMasterDao
{	
	public static final String LIST_BY_RATE = "listRateMaster";
	public static final String SEARCH_RATE = "getRates";
	public static final String BOARD_RATE = "boardRates"; 
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<RateMaster> listRateMaster(){
		return (List<RateMaster>)getHibernateTemplate().findByNamedQuery(LIST_BY_RATE);		
	}		

	@Transactional
	public RateMaster getRateMaster(Integer ratemasterId){
		
		if(getHibernateTemplate()!=null){
			return (RateMaster)getHibernateTemplate().get(RateMaster.class, ratemasterId);
		}
		else{
			System.out.println("getHibernateTemplate() returns null value");
			return (RateMaster)getHibernateTemplate().get(RateMaster.class, ratemasterId);
		}	
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<RateMaster> searchRateMaster(){		
		return (List<RateMaster>) getHibernateTemplate().findByNamedQuery(BOARD_RATE);
	}
	
	@Transactional
	public void insertRateMaster(RateMaster ratemaster){
		System.out.println("RateMasters: Executing insert method ::::::::::::");
		getHibernateTemplate().saveOrUpdate(ratemaster);		
	}

	@Transactional
	public void updateRateMaster(RateMaster ratemaster) {
		System.out.println("RateMasters: Executing update method ::::::::::::");
		getHibernateTemplate().update(ratemaster);
    }	
	
	/** For Savings Scheme to Sales Retrieve Gold Rate **/
	@SuppressWarnings("unchecked")
	public List<BigDecimal> getGoldOrnamentRate(){
		return (List<BigDecimal>) getHibernateTemplate().find("SELECT goldOrnaments FROM RateMaster where ratemasterId = (SELECT MAX(ratemasterId) FROM RateMaster)");		
	}	
}