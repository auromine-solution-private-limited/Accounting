 package com.jewellery.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.POSCategoryDao;
import com.jewellery.entity.POSCategory;


public class HibernatePOSCategoryDaoImpl extends HibernateDaoSupport implements POSCategoryDao
{
	public static final String GET_CATEGORYlIST = "getCategoryList";
	public static final String GET_CATEGORY_BYNAME = "getPOSCategoryByName";
		
	@Transactional
	public POSCategory getCategory(Integer id){	

		if(getHibernateTemplate()!=null){
			return (POSCategory)getHibernateTemplate().get(POSCategory.class, id);
		}else{
			return (POSCategory)getHibernateTemplate().get(POSCategory.class, id);
		}		
	}
	
	@Transactional(readOnly=true)
	public POSCategory getCategory(String categoryName){
		Session session = getSession();
		try{
			Query query = session.getNamedQuery(GET_CATEGORY_BYNAME);
			query.setParameter("categoryName", categoryName);
			return (POSCategory)query.uniqueResult();
		}finally{
			releaseSession(session);
		}		
	}
	
		
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<POSCategory> listCategoryName(Integer id) {		
		return (List<POSCategory>) getHibernateTemplate().find("from POSCategory  where categoryId = ?",id);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<POSCategory> searchExistingCategory(String catogoryName) {		
		return (List<POSCategory>) getHibernateTemplate().find("from POSCategory  where categoryName = ?", catogoryName);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<String> getCatagoryNames(String cNamePart){
		Object[] obj = {cNamePart+"%"};
		return (List<String>) getHibernateTemplate().find("SELECT DISTINCT categoryName from POSCategory where categoryName LIKE ?",obj);
	}
		
	
	@Transactional
	public void updateCategorySet(int quantity, String categoryName){
		 Object[] param = {quantity,categoryName};
		 String query = "update POSCategory SET totalQuantity = totalQuantity + ? where categoryName = ?";
		 getHibernateTemplate().bulkUpdate(query, param);		 				 
	}
		
	@Transactional()
	@SuppressWarnings("unchecked")
	public List<POSCategory> listCategoryName() 
	{	
		List<POSCategory> find = (List<POSCategory>) getHibernateTemplate().findByNamedQuery(GET_CATEGORYlIST);
		return find;
	}
	
	@Transactional
	public void insertCategory(POSCategory posCategory){ 
		getHibernateTemplate().saveOrUpdate(posCategory);		
	}

	@Transactional
	public void updateCategory(POSCategory posCategory){
		
		Session session = getSession();
		POSCategory existingcategory=(POSCategory)session.get(POSCategory.class, posCategory.getCategoryId());
		session.update(existingcategory);
		}
	
	@Transactional
    public void deleteCategory(POSCategory posCategory) {
      getHibernateTemplate().delete(posCategory);
    }	
	
	/** For POS Purchase CategoryName drop down **/
	@SuppressWarnings("unchecked")
	public List<POSCategory> getCategoryNameList() {		
		return (List<POSCategory>)getHibernateTemplate().find("SELECT DISTINCT categoryName FROM POSCategory");
	}
}