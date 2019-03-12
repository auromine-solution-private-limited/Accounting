package com.jewellery.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.StartSchemeDao;
import com.jewellery.entity.StartScheme;

public class HibernateStartSchemeDaoImpl extends HibernateDaoSupport implements StartSchemeDao
{	
	public static final String LIST_START_SCHEME = "scheme_List";
	public static final String SEARCH_SCHEME = "getStartSchemeByName";
	public static final String SCHEMENAME="SchemeByName";
	 
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<StartScheme> listStartScheme(){
		return (List<StartScheme>)getHibernateTemplate().findByNamedQuery(LIST_START_SCHEME);		
	}		

	@Transactional
	public StartScheme getStartScheme(Integer StartSchemeId){
		
		if(getHibernateTemplate()!=null){
			return (StartScheme)getHibernateTemplate().get(StartScheme.class, StartSchemeId);
		}
		else{			
			return (StartScheme)getHibernateTemplate().get(StartScheme.class, StartSchemeId);
		}	
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<StartScheme> searchStartScheme(String schemeName){		
		return (List<StartScheme>) getHibernateTemplate().findByNamedQuery(SEARCH_SCHEME, schemeName);
	}
	
	// For SSReceipt and SSPayment checking Scheme Duration
	@SuppressWarnings("unchecked")
	public List<StartScheme> searchStartSchemeSSRP(String schemeName){		
		return (List<StartScheme>) getHibernateTemplate().find("FROM StartScheme WHERE schemeName = ?", schemeName);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<StartScheme> getSchmename() {
		
		return (List<StartScheme>) getHibernateTemplate().findByNamedQuery(SCHEMENAME);
	}
	@Transactional
	public void insertStartScheme(StartScheme startscheme){		
		getHibernateTemplate().saveOrUpdate(startscheme);		
	}

	@Transactional
	public void updateStartScheme(StartScheme startscheme) {
		getHibernateTemplate().update(startscheme);
    }

	public String getstartedSchemeName(String schemeName) {		
		Session session = getSession();
		try{
		Query query=session.createQuery("SELECT schemeName FROM StartScheme WHERE schemeName = :schemeName");
		query.setParameter("schemeName", schemeName);
		String list=(String)query.list().get(0);
			return(list);
			}
		catch(Exception e){}
		finally{
			releaseSession(session);
		}
		return "empty";	 
	}
	public String getStartDate(String schemeName) {
		Session session = getSession();
		//return getHibernateTemplate().find("SELECT  dateOfJoining FROM CardIssue WHERE schemeName = ? ",schemeName).toString();
		String sql = "SELECT DISTINCT(DATE_FORMAT(schemeStartDate, '%d/%m/%Y'))  FROM StartScheme WHERE schemeName = :schemeName";
				Query query = session.createQuery(sql);
				query.setParameter("schemeName", schemeName);
			
				@SuppressWarnings("unchecked")
				List<String> result = query.list();
				
		return result.toString();	
	}
	
	@SuppressWarnings("unchecked")
	public String getstartDate(String schemeName){
		Session session = getSession();
	//	return getHibernateTemplate().find("SELECT  dateOfJoining FROM CardIssue WHERE schemeName = ? ",schemeName).toString();
		String sql = "SELECT DATE_FORMAT(schemestartdate, '%d/%m/%Y')  FROM StartScheme WHERE schemeName = :schemeName";
				Query query = session.createQuery(sql);
				query.setParameter("schemeName", schemeName);
				List<String> result = query.list();
				
		return result.toString();
	
	}
}