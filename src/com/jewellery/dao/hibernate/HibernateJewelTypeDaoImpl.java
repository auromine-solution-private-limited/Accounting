package com.jewellery.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.JewelTypeDao;
import com.jewellery.entity.JewelType;


public class HibernateJewelTypeDaoImpl extends HibernateDaoSupport implements JewelTypeDao
{

	public static final String GET_BY_JEWEL_NAME = "getJewelByName";
	public static final String LIST_BY_JEWEL_TYPE_ID = "listJewelTypeId";
	public static final String GOLD_LIST = "goldornamentlist";
	public static final String SILVER_LIST = "silverornamentlist";
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)

	public List<JewelType> listJewelType(){
	//	System.out.println("Executing JewelType Query:::::::::::::::::::::::::::::::::::::::::::::::");
		return (List<JewelType>)getHibernateTemplate().findByNamedQuery(LIST_BY_JEWEL_TYPE_ID);
	}

	
	@Transactional
	public JewelType getJewelType(Integer id){	
	//	System.out.println("Method getJewelType(int)::::::::::::::::::::::::::::::::::::::::::::::::");
		if(getHibernateTemplate()!=null){
			return (JewelType)getHibernateTemplate().get(JewelType.class, id);
		}else{
		System.out.println("getHibernateTemplate() returns null value");
		return (JewelType)getHibernateTemplate().get(JewelType.class, id);
		}		
	}	
//Checking for existing jeweltype while creating new category	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<JewelType> searchJewel(String jewelName) 
	{
		 List<JewelType> jeweltype=getHibernateTemplate().find("from JewelType j where j.jewelName = ?",jewelName);
		 if (jeweltype != null)
			{
				return jeweltype;
			}
	return null;
	}

	@Transactional(readOnly=true)
	public JewelType getJewelType(String jewelName){
	//	System.out.println("Method getJewelType(String):::::::::::::::::::::::::::::::::::::::::::::::");	
		Session session = getSession();
		try{
			Query query = session.getNamedQuery(GET_BY_JEWEL_NAME);
			query.setParameter("jewelName", jewelName);
			return (JewelType)query.uniqueResult();
		}finally{
			releaseSession(session);
		}		
	}
	
	@Transactional
	public void updateTotalGrossWt(BigDecimal grossWeight, String metalType, String categoryName){
		 Object[] param = {grossWeight,metalType, categoryName};
		 String query = "update JewelType j set j.totalGrossWeight = j.totalGrossWeight + ? where j.metalType = ? and j.jewelName = ?";
		 getHibernateTemplate().bulkUpdate(query, param);		 				 
	}
	@Transactional
	public void insertJewelType(JewelType jewel){
		
		getHibernateTemplate().saveOrUpdate(jewel);		
	}

	@Transactional
	public void updateJewelType(JewelType jewel){
		
      getHibernateTemplate().update(jewel);
    }

	@Transactional
    public void deleteJewelType(JewelType jewel) {
	
      getHibernateTemplate().delete(jewel);
    }

	@Transactional()
	@SuppressWarnings("unchecked")
	public List<JewelType> listgoldOrnaments() {
		List<JewelType> findgoldlist = (List<JewelType>) getHibernateTemplate().findByNamedQuery(GOLD_LIST);
		return findgoldlist;
	}

	@Transactional()
	@SuppressWarnings("unchecked")
	public List<JewelType> listsilverOrnaments() {
		List<JewelType> findsilverlist = (List<JewelType>) getHibernateTemplate().findByNamedQuery(SILVER_LIST);
		return findsilverlist;
	}

	@SuppressWarnings("unchecked")
	public List<JewelType> getcategoryName(String metalUsed){		
		List<JewelType> categoryList = (List<JewelType>) getHibernateTemplate().find("SELECT jewelName FROM JewelType WHERE jewelTypeId >10 AND metalUsed = ? ",metalUsed);
		return categoryList;
	}

	@Transactional
	public void updateCategoryName(String metal_type, String base_metal, String description, String newCategoryName, String oldcategoryName) {
		 Object[] param = {metal_type, base_metal, description, newCategoryName, oldcategoryName};
		 String query = "UPDATE JewelType j set j.metalType = ?, j.metalUsed = ?, j.description = ?, j.jewelName = ? WHERE j.jewelName = ?";
		 getHibernateTemplate().bulkUpdate(query, param);		
	}

}