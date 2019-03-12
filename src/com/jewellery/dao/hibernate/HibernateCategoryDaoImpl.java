package com.jewellery.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.CategoryDao;
import com.jewellery.entity.Category;


public class HibernateCategoryDaoImpl extends HibernateDaoSupport implements CategoryDao
{
	public static final String GET_BY_CAT_NAME = "getCategoryByName";
	public static final String LIST_BY_CAT_ID = "listCategoryById";
	public static final String SEARCH_CATEGORY = "getCategories";
	public static final String LIST_CAT_NAME = "listCategoryName";
	public static final String DEFULTCAT_LIST="defaultcategorylist";

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Category>defaultcategory()
	{
		return (List<Category>)getHibernateTemplate().findByNamedQuery(DEFULTCAT_LIST);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Category> listCategory(){
			return (List<Category>)getHibernateTemplate().findByNamedQuery(LIST_BY_CAT_ID);
	}

	
	@Transactional
	public Category getCategory(Integer id){	
//		System.out.println("Method getCategory(int)::::::::::::::::::::::::::::::::::::::::::::::::");
		if(getHibernateTemplate()!=null){
			return (Category)getHibernateTemplate().get(Category.class, id);
		}else{
	//	System.out.println("getHibernateTemplate() returns null value");
		return (Category)getHibernateTemplate().get(Category.class, id);
		}		
	}
	
	@Transactional
	public void updateCategoryWeight(BigDecimal totalGrossWeight, String metalType){
		 Object[] param = {totalGrossWeight,metalType};
		 String query = "update Category c set c.totalGrossWeight = c.totalGrossWeight +? where c.metalType = ? and c.categoryType = 'Master' ";
		 getHibernateTemplate().bulkUpdate(query, param);		 				 
	}
	
	@Transactional
	public void updateSubCategory(BigDecimal totalGrossWeight, String metalType,String categoryName,String subCategoryName){
		 Object[] param = {totalGrossWeight,metalType,categoryName,subCategoryName};
		 String query = "update Category c set c.totalGrossWeight = c.totalGrossWeight + ? where c.metalType = ? and c.baseCategory = ? and c.categoryName = ? and c.categoryType = 'Subcategory' ";
		 getHibernateTemplate().bulkUpdate(query, param);		 				 
	}
	
	@Transactional(readOnly=true)
	public Category getCategory(String categoryName){
	//	System.out.println("Method getCategory(String):::::::::::::::::::::::::::::::::::::::::::::::");	
		Session session = getSession();
		try{
			Query query = session.getNamedQuery(GET_BY_CAT_NAME);
			query.setParameter("categoryName", categoryName);
			return (Category)query.uniqueResult();
		}finally{
			releaseSession(session);
		}		
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Category> searchCategory(final String catName){
		//System.out.println("Search Category Method ...............");
		List<Category> baseCategory=getHibernateTemplate().find("from Category  where baseCategory=?",catName);
		if(baseCategory!=null)
		{
			return baseCategory;
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Category> searchExistingCategory(final String subcategoryName){
		//System.out.println("Search Category Method ...............");
		List<Category> baseCategory=getHibernateTemplate().find("from Category  where categoryName=?",subcategoryName);
		if(baseCategory!=null)
		{
			return baseCategory;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Category> categoryName(Integer id) {
		
	return (List<Category>) getHibernateTemplate().find("from Category  where categoryId = ?",id);
	}
	
	@Transactional()
	public List<Category> listCategoryName() 
	{
	@SuppressWarnings("unchecked")
	List<Category> find = (List<Category>) getHibernateTemplate().findByNamedQuery(LIST_CAT_NAME);
	return find;
	}
	
	@Transactional
	public void insertCategory(Category category){
		getHibernateTemplate().saveOrUpdate(category);		
	}

	@Transactional
	public void updateCategory(Category category){
		
		Session session = getSession();
		Category existingcategory=(Category)session.get(Category.class, category.getCategoryId());
		existingcategory.setLessPercentage(category.getLessPercentage());
		existingcategory.setMcPerGram(category.getMcPerGram());
		existingcategory.setVaPercentage(category.getVaPercentage());
		existingcategory.setVat(category.getVat());
		existingcategory.setStartDate(category.getStartDate());
		existingcategory.setEndDate(category.getEndDate());
		existingcategory.setMcTypes(category.getMcTypes());
		existingcategory.setMcInRupees(category.getMcInRupees());
		existingcategory.setRolquantity(category.getRolquantity());
		existingcategory.setCategoryHMCharges(category.getCategoryHMCharges());
		existingcategory.setScheme(category.getScheme());
		existingcategory.setDescription(category.getDescription());
	
		List<String> exCatName=categoryNameList(category.getCategoryName());
		if(exCatName.isEmpty())
		{
		existingcategory.setCategoryName(category.getCategoryName());	
		}
		session.update(existingcategory);
	}
	
	@Transactional
    public void deleteCategory(Category category) {
      getHibernateTemplate().delete(category);
    }
	@Transactional
	public void updateBaseCategoryName(String newCatValue, String oldCatValue) {
		 Object[] param = {newCatValue,oldCatValue};
		 String query = "UPDATE Category c set c.baseCategory = ? WHERE c.baseCategory = ?";
		 getHibernateTemplate().bulkUpdate(query, param);	
		
	}

	@SuppressWarnings("unchecked")
	public List<String> categoryNameList(String category) {
		Object[] obj = {category};
		return (List<String>)getHibernateTemplate().find("SELECT categoryName FROM Category WHERE categoryName= ?",obj);
	}			
}