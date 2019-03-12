package com.jewellery.dao;
import java.math.BigInteger;
import java.util.List;
import com.jewellery.entity.Purchase;

public interface PurchaseDao {
	public List<Purchase> listPurchase();
	public Purchase getPurchase(Integer id);
	public List<Purchase> getPurchaseBillNo(String customer);
	public List<Purchase> getPurchaseAmount(String id);
	public void insertPurchase(Purchase purchase);
	public void updatePurchase(Purchase purchase);
	public void deletePurchase(Purchase purchase);
	public List<Purchase> diplayPurchaseBillID(String oldCustName,String id);//for bug 
	public BigInteger getgoldexchangeinvoiceNo();
	public BigInteger getsilverdexchangeinvoiceNo();
	public BigInteger getPurchaseInvoiceNo(); // for common invoice NO
	public void UpdateStatusToAccept(String id);
}
