package com.jewellery.dao;

import java.math.BigInteger;
import java.util.List;
import com.jewellery.entity.Sales;

public interface SalesReturnDao {
	
	public List<Sales> listSales();
	public Sales getSales(Integer id);
	public void insertSales(Sales sales);
	public void updateSales(Sales sales);
	public void deleteSales(Sales sales);
	public BigInteger getLastSalesReturnNo();
}