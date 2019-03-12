package com.jewellery.dao;

import java.math.BigInteger;
import java.util.List;

import com.jewellery.entity.ManualBilling;
import com.jewellery.entity.ManBillRowList;

public interface ManualBillingDao {
	
	/*** Manual Billing Methods **/ 
	public void insertManualBilling(ManualBilling manualBilling);
	public void updateManualBilling(ManualBilling manualBilling);
	public void deleteManualBilling(ManualBilling manualBilling);
	public List<ManualBilling> getManualBillingList(Integer mBillId);
	public List<ManualBilling> listManualBilling();	
	public List<ManualBilling> listVatManualBilling();
	public List<ManualBilling> listVatFreeManualBilling();
	public ManualBilling getManualBilling(Integer mBillId); // To retrieve ManualBilling based on manualBillingId
	
	/*** Manual Billing Row List Methods **/
	public void insertManBillRowList(ManBillRowList manBillRowList);
	public void updateManBillRowList(ManBillRowList manBillRowList);
	public void deleteManBillRowList(ManBillRowList manBillRowList);
	public ManBillRowList getManBillRowList(Integer manBillRowListId);
	public List<ManBillRowList> listAllManBillRowList() ;	
	public List<ManBillRowList> listManualBilling(Integer mBillId); //for search in ManualBilling return
	
	public BigInteger getLatestMBillCode();
	public BigInteger getLatestMBillVatFreeCode();
}
