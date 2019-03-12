package com.jewellery.dao;

import java.math.BigInteger;
import java.util.List;

import com.jewellery.entity.POSSales;
import com.jewellery.entity.POSSalesItem;
import com.jewellery.entity.PSales;


public interface POSSalesDao {
	public PSales getPSales(Integer id);
	public POSSalesItem getSalesItem(Integer id);
	public void insertPOSSales(POSSales sales);
	public void insertPOSSalesItem(POSSalesItem objpos);
	public void updatePOSSales(POSSales sales);
	public void deletePOSSales(POSSales sales);
	public List<POSSalesItem> listSalesItem();	
public List<POSSalesItem> listPOSItems();
public List<POSSalesItem> getPOSSalesItem(Integer salesId, String saletype);
	public List<POSSales> getPOSsales(Integer salesId);
	public void updatePOSSalesItem(POSSalesItem sales);
	public void deletePOSSalesItem(POSSalesItem salesId);
	public POSSales getPOSSales(Integer salesId); // To retrieve POSSales based on salesId
	public  List<POSSalesItem> getPOSSalesSearchItem(String salesId);//for search in possales return
	public List<POSSalesItem> getPOSSoldItem(String itemcode);
	public BigInteger getLastEstimPOSSalesNo();// added for Estimate Sales POS 4-1-13
	public BigInteger getLastPOSSalesNo();// added for Estimate Sales POS 7-1-13
	public BigInteger getLastPORInvNo();// added for POS Sales Return 10-1-13
	public List<POSSales> getPOSInov(String salesId);
}
