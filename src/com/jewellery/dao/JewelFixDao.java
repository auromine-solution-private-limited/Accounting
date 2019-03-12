package com.jewellery.dao;

import java.math.BigInteger;
import java.util.List;

import com.jewellery.entity.JewelFix;

public interface JewelFixDao {
	public List<JewelFix> listJewelFix();	
	public JewelFix getJewelFix(Integer id);
	public JewelFix getJewelFix(String customerName);	
	public BigInteger getOrderCode();
	public void insertJewelFix(JewelFix jewelfix);
	public void updateJewelFix(JewelFix jewelfix);
	public void deleteJewelFix(JewelFix jewelfix);	
}
