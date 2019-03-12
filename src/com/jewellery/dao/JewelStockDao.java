package com.jewellery.dao;

import java.util.List;

import com.jewellery.entity.JewelStock;

public interface JewelStockDao {
	
	public void saveJewelStock(JewelStock jewelStock);
	public void updateJewelStock(JewelStock jewelStock);
	public void deleteJewelStock(JewelStock jewelStock);
	public List<JewelStock> listAllJewelStock();
	public List<JewelStock> searchJewelStock(String jewelStockId);
	public List<JewelStock> searchByTransNO_TransType(String TransNO, String TransType);
	//public List<JewelStock> searchByItemId(String stockTranstype, String ItemId); // For Opening Stock Update Mode
	public List<JewelStock> searchByItemCode_TransType(String ItemCode, String TransType);
}
