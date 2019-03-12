package com.jewellery.dao.hibernate;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.POSItemDao;
import com.jewellery.entity.POSItem;

public class HibernatePOSItemDaoImpl extends HibernateDaoSupport implements POSItemDao
{
	public static final String LIST_ALL_ITEMS = "getPOSItems";
		
	@Transactional
	public POSItem getPOSItem(Integer id){	
		return (POSItem) getHibernateTemplate().get(POSItem.class, id);
	}	
		
	@Transactional
	public void updatePOSItem(POSItem posItem){
		getHibernateTemplate().update(posItem);		
	}
	
	public void deletePOSItem(POSItem posItem){
		getHibernateTemplate().delete(posItem);
	}

	@SuppressWarnings("unchecked")
	public List<POSItem> listPOSItem() {		
		return (List<POSItem>)getHibernateTemplate().findByNamedQuery(LIST_ALL_ITEMS);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> listPOSItemName() {		
		return (List<String>)getHibernateTemplate().find("SELECT itemName FROM POSItem");
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<String> getInamesList(String cnameValue){
		Object[] obj = {cnameValue};
		return(List<String>) getHibernateTemplate().find("SELECT itemName FROM POSItem Where categoryName = ?",obj);
	}
	
	/** For Search item Record based on Category Name */
	@SuppressWarnings("unchecked")
	public List<POSItem> searchPOSItemBasedCategory(String itemName,String catName){
		Object[] iobj = {catName,itemName};
		String query = "FROM POSItem WHERE categoryName = ? AND itemName = ?";
		return(List<POSItem>) getHibernateTemplate().find(query, iobj);
	}
	/** For Search item Record */
	@SuppressWarnings("unchecked")
	public List<POSItem> searchPOSItem(String itemName){
		Object[] iobj = {itemName};
		return(List<POSItem>) getHibernateTemplate().find("FROM POSItem WHERE itemName = ?" ,iobj);
	}	

	public void insertPOSItem(POSItem posItem) {
		
	}	
	
	@SuppressWarnings("unchecked")
	public List<POSItem> getItemCategory(int itemId){
		Object[] obj = {itemId};
		System.out.println("Executing ITEMDAO :::::");
		return(List<POSItem>)getHibernateTemplate().find("from POSItem where itemId=?",obj);
	}
	
}