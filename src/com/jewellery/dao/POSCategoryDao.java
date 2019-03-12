package com.jewellery.dao;

import java.util.List;
import com.jewellery.entity.POSCategory;

public interface POSCategoryDao{
	
	public List<POSCategory> listCategoryName();	
	public POSCategory getCategory(Integer id);
	public POSCategory getCategory(String categoryName);
	public List<POSCategory> listCategoryName(Integer id);
	public List<POSCategory> searchExistingCategory(String categoryName);
	public void updateCategorySet(int quantity, String categoryName);	
	public void insertCategory(POSCategory category);
	public void updateCategory(POSCategory category);
	public void deleteCategory(POSCategory category);
	public List<String> getCatagoryNames(String cNamePart);
	public List<POSCategory> getCategoryNameList();
	
}