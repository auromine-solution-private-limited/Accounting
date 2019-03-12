package com.jewellery.dao;

import java.util.List;

import com.jewellery.entity.POSStock;

public interface POSStockDao {
	
public void insertPOSStock(POSStock posStock);
public void updatePOSStock(POSStock posStock);
public void deletePOSStock(POSStock posStock);
public List<POSStock> getStockCode();
public void updatePOSStock(Integer qty, String transactionType, String barcodeId);
public List<POSStock> searchposTag(String icode);
public List<POSStock> POSTagByReference(Integer referenceId);
public void deleteBarcodeId(String barcodeId);
}
