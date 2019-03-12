package com.jewellery.dao;

import java.math.BigInteger;
import java.util.List;

import com.jewellery.entity.Saving_SchemeReceipt;

public interface SSReceiptDao {
	
	public void insertSSReceipt(Saving_SchemeReceipt ssReceipt);
	public void updateSSReceipt(Saving_SchemeReceipt ssReceipt);
	public void deleteSSReceipt(Saving_SchemeReceipt ssReceipt);
	public List<Saving_SchemeReceipt> listSSReceipt(); // To retrieve all the Saving_SchemeReceipt List
	public List<Saving_SchemeReceipt> listSSPayment(); // To retrieve all the Saving_SchemePayment List
	public List<Saving_SchemeReceipt> getSSReceipt(Integer receiptId); // To retrieve Saving_SchemeReceipt based on receiptId
	public BigInteger getAutoSSRCode(); // Auto Code for Amount ReceiptNO
	public String getcardNo(String cardNo);
	public BigInteger getAutoSSPCode(); // Auto Code for Grams ReceiptNO
	public List<String> getSSRLastCardNo (String currentCardNO); // To get last Receipt entry
}
