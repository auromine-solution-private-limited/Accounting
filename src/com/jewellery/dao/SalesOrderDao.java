package com.jewellery.dao;


import java.util.List;
import com.jewellery.entity.SalesOrder;

public interface SalesOrderDao{
	
	public List<SalesOrder> listSalesOrder();
	public List<SalesOrder> SalesOrderPending();
	public SalesOrder getSalesOrder(Integer id);
	public SalesOrder getSalesOrder(String customername);
	public void insertSalesOrder(SalesOrder salesorder);
	public void updateSalesOrder(SalesOrder salesorder);
	public void deleteSalesOrder(SalesOrder salesorder);
	public List<SalesOrder> searchSalesOrder(Integer orderNo);
	public List<SalesOrder> getSalesOrderId(String customerName);
	public List<SalesOrder> getCustomerId(String customeR);
	public List<SalesOrder> getOrderAdvance(Integer id);
	public void UpdateStatusID(Integer id);
	public void UpdateStatusToAccept(Integer id);
	public List<SalesOrder> ViewSalesOrderId(String customerName,Integer id);//add for bug
	public List<SalesOrder> getOrderFixAmt(Integer id);//add for ratefix amt to sales
}