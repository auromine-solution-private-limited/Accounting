package com.jewellery.dao.hibernate;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.SavingSchemeDao;
import com.jewellery.entity.SavingScheme;

public class HibernateSavingSchemeDaoImpl extends HibernateDaoSupport implements SavingSchemeDao
{	
	public static final String LIST_SCHEME_NAME = "scheme_list";
	public static final String SEARCH_SCHEME = "getSchemes";
	 
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<SavingScheme> listSavingScheme(){
		return (List<SavingScheme>)getHibernateTemplate().findByNamedQuery(LIST_SCHEME_NAME);		
	}		

	@Transactional
	public SavingScheme getSavingScheme(Integer savingSchemeId){
		
		if(getHibernateTemplate()!=null){
			return (SavingScheme)getHibernateTemplate().get(SavingScheme.class, savingSchemeId);
		}
		else{			
			return (SavingScheme)getHibernateTemplate().get(SavingScheme.class, savingSchemeId);
		}	
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<SavingScheme> searchSavingScheme(String schemename){		
		return (List<SavingScheme>) getHibernateTemplate().findByNamedQuery(SEARCH_SCHEME, schemename);
	}
	
	@Transactional
	public void insertSavingScheme(SavingScheme savingscheme){		
		getHibernateTemplate().saveOrUpdate(savingscheme);		
	}

	@Transactional
	public void updateSavingScheme(SavingScheme savingscheme) {
		getHibernateTemplate().update(savingscheme);
    }			
}