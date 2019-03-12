package com.jewellery.dao;

import java.math.BigDecimal;
import java.util.List;
import com.jewellery.entity.Category;


public interface CategoryDao{
	public List<Category> listCategory();
	public List<Category>defaultcategory();
	public List<Category> searchCategory(String catName);
	public List<Category> searchExistingCategory(String subcategoryName);
	public List<Category> listCategoryName();
	public Category getCategory(Integer id);
	public List<Category> categoryName(Integer id);
	public Category getCategory(String categoryName);
	public void insertCategory(Category category);
	public void updateCategory(Category category);
	public void deleteCategory(Category category);
	public void updateCategoryWeight(BigDecimal totalGrossWeight, String metalType);
	public void updateSubCategory(BigDecimal totalGrossWeight, String metalType, String categoryName, String subCategoryName);
	public void updateBaseCategoryName(String newCatValue,String oldCatValue);
	public List<String> categoryNameList(String category);
}