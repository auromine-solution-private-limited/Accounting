package com.jewellery.dao;

import java.math.BigDecimal;
import java.util.List;
import com.jewellery.entity.JewelType;

public interface JewelTypeDao{

	public List<JewelType> listgoldOrnaments();
	public List<JewelType> listsilverOrnaments();
	public JewelType getJewelType(Integer id);
	public JewelType getJewelType(String jewelName);
	public List<JewelType> searchJewel(String jewelName);
	public void insertJewelType(JewelType category);
	public void updateJewelType(JewelType category);
	public void deleteJewelType(JewelType category);
	public void updateTotalGrossWt(BigDecimal grossWeight,String metalType, String jewelName);
	public List<JewelType> getcategoryName(String metalUsed);
	public void updateCategoryName(String metal_type, String base_metal, String description, String newCategoryName, String oldcategoryName); //Update existing categoryName
}