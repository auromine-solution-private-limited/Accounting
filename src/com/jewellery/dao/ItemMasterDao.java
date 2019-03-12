package com.jewellery.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import com.jewellery.entity.ItemMaster;


public interface ItemMasterDao {

	public List<ItemMaster> listItemMaster();
	public List<ItemMaster> listAllItems();
	public List<ItemMaster>listAllitemCode();
	public List<ItemMaster> Itemstocklist();
	public List<ItemMaster> searchItemMaster(String itemcd);
	public ItemMaster getItemMaster(Integer id);
	public void updateItemMaster(ItemMaster itemmaster);
	public void deleteItemMaster(ItemMaster itemmaster);
	public void updateVaPercentage(BigDecimal lessPercent, BigDecimal vaPercentage, BigDecimal mc,BigDecimal mcrupees, BigDecimal vat, String metalGroup, String subCategoryName, BigDecimal categoryHMCharges);
	public void updateuntagstock(BigDecimal totalGrossWeight,BigDecimal grossWeight,BigDecimal netWeight, int qty);
	public void updateuntagstocksilver(BigDecimal totalGrossWeight,BigDecimal grossWeight,BigDecimal netWeight, int qty);
	public void updateSalesStock(BigDecimal totalGrossWeight,int qty, int totalPcs, String itemCode);
	public void updateLooseStock(BigDecimal totalGrossWeight,BigDecimal grossWeight,BigDecimal netWeight, int qty, String itemcode);
	public void updateLooseCategorySalesStock(BigDecimal totalGrossWeight,BigDecimal GrossWeight,BigDecimal netWeight,int qty, String itemCode);
	/* Method for Sales Update */
	public void updateSalesLooseStock(BigDecimal totalGrossWeight,BigDecimal grossWeight,BigDecimal netWeight, String itemcode);
	public void updateSalesCategoryLooseStock(BigDecimal totalGrossWeight,BigDecimal grossWeight,BigDecimal netWeight,int qty, String itemcode);
	public List<ItemMaster> searchItemName(String item, BigDecimal weight);
	public BigInteger getItemCode();
	public List<ItemMaster> getItemsByCode(String item_code);//get item by code
	public List<ItemMaster> listItemWeight();
	public List<ItemMaster> listGoldWeight();
	public List<ItemMaster> listSilverWeight();
	public BigDecimal purchasedStock(String metalType);
	public Integer purchasedStockuntag(String metalType);
	public List<String> getAutoItemCode(String itemCodePart); //Auto complete for ItemCode Search - Sales
	public List<String> getAutoItemName(String itemNamePart); //Auto Complete for ItemName Search - basic theme
	public void updateOldStock(BigDecimal totalGrossWeight,BigDecimal grossWeight,BigDecimal netWeight, int qty,String itemCode);
	public List<ItemMaster> getBullionVat(String itemcode);
	public List<ItemMaster> lowMetalStockList(); // add on 29-11-12 for low metal stock list
	public List<ItemMaster> getBaseCategoryName(String categoryName);//extisnting base category name validation
	public List<ItemMaster> searchItemListByBaseCategoryName(String baseCategoryName);
	}