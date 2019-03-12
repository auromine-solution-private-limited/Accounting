package com.jewellery.dao;

import java.math.BigInteger;
import java.util.List;
import com.jewellery.entity.Sales;

public interface SalesDao {
	
	public List<Sales> listSales();
	public Sales getSales(Integer id);
	public void insertSales(Sales sales);
	public void updateSales(Sales sales);
	public void deleteSales(Sales sales);
	public BigInteger getLatestBillNo();
	public BigInteger getLatestBullionNo();
	public BigInteger getLastGoldTypeNo();
	public BigInteger getLastSilverTypeNo();
	public BigInteger getLastBullionGoldTypeId();
	public BigInteger getLastBullionSilverTypeId();
	public BigInteger getLastEstimSalesNo();
	public List<Sales> getSalesByIdList(Integer salesId);
	public List<Sales> getSalesReturnId(String customer);//added new for sales return Getting Id to sales 
	public List<Sales> getSRAmount(String id);//added new for sales return Getting amt to sales
	public void UpdateSRID(String id);//added for update SR status as SOld
	public void UpdateSRToYes(String id);//added for update SR status as Yes
	public List<Sales> ViewSRId(String CustomerName,String id);//added for Reterving SR Id in view mode
}