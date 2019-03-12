package com.jewellery.dao;

import java.math.BigInteger;
import java.util.List;

import com.jewellery.entity.RefineryReceipt;

public interface RefineryReceiptDao {
	public void insertRefineryReceipt(RefineryReceipt refinery);
	public void updateRefineryReceipt(RefineryReceipt refinery);
	public RefineryReceipt getRefineryReceipt(Integer refineryId);
	public List<RefineryReceipt> listRefineryReceipt();
	public BigInteger getRReceiptNo();
	public List<RefineryReceipt> getrefineryReceiptId(Integer rrId);
}
