package com.jewellery.dao;

import java.math.BigInteger;
import java.util.List;
import com.jewellery.entity.POSPurchase;
import com.jewellery.entity.POSPurchaseItem;

public interface POSPuchaseDao {
	
	public void insertPOSPurchase(POSPurchase posPurchase);
	public void updatePOSPurchase(POSPurchase posPurchase);
	public void deletePOSPurchase(POSPurchase posPurchase);
	public List<POSPurchase> listPOSPurchase(); // To retrieve all the POSPurchase List
	
	public POSPurchase getPOSPurchase(Integer purchaseId); // To retrieve POSPurchase based on purchaseId
	
	public void insertPOSPurchaseItem(POSPurchaseItem posPurchaseItem);
	public void updatePOSPurchaseItem(POSPurchaseItem posPurchaseItem);
	public void deletePOSPurchaseItem(POSPurchaseItem posPurchaseItem);
	
	public List<POSPurchaseItem> getPOSPurchaseItem(Integer purchaseId); // To retrieve POSPurchase based on purchaseId
	public BigInteger getLatestPurchaseCode();

}
