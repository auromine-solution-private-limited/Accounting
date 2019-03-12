package com.jewellery.dao;

import java.util.List;
import com.jewellery.entity.POSItem;

public interface POSItemDao {

	public List<POSItem> listPOSItem();	
	public POSItem getPOSItem(Integer id);
	public void insertPOSItem(POSItem posItem);
	public void updatePOSItem(POSItem posItem);
	public void deletePOSItem(POSItem posItem);
	public List<String> getInamesList(String cnameValue);
	public List<POSItem> getItemCategory(int itemId);
	public List<POSItem> searchPOSItemBasedCategory(String itemName,String catName); // for Search item Record based on Item Name
	public List<POSItem> searchPOSItem(String itemName);
	public List<String> listPOSItemName(); // for POS Purchase itemName List
}