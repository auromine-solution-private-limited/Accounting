package com.jewellery.dao;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.jewellery.entity.PostagItem;
	
	public interface PostagItemDao {
	public void save(PostagItem postagitem);
	public void update(PostagItem postagitem);
	public void delete(PostagItem postagItem);
	public BigInteger getposItemCode();
	public List<PostagItem> getposItemList();
	public List<PostagItem> getposPrintedItemList();//added new for printed tag list 30-1-13
	//public List<PostagItem> getposprintedTagedList();
	public PostagItem getpostagId(Integer postagid);
	public void updateSoldTagItem(Integer qty, String status, String barcodeID);
	public List<PostagItem> POSTagByReference(String referenceId);
	public List<PostagItem> getSoldTagByReference(String referenceId);
	public List<PostagItem> getItemCode(String itemcode);
	//public List<PostagItem> searchExistingItemCode(String itemNameCode);
	public  List<PostagItem>  getBarcodeID(String POSReferenceID,int limit);
	public void deleteBarcodeId(String barcodeId);
	public void refilldeletedBarcode(String barcodeId);
	public List<PostagItem> searchsoldStatus(String POSReferenceID);
	public List<PostagItem> searchupdateBarcodeLabel(String POSReferenceID);
	public void updateBarcodelabel(String printName,String companyName,BigDecimal mrpinRs,BigDecimal salesRate,BigDecimal margin,BigDecimal costRate,BigDecimal discountPercentage,BigDecimal vatPercentage, Date date,String POSReferenceID);
	public void refillstockEP(String barcodeId);
	public List<PostagItem> POSlowItemStockList();
}
