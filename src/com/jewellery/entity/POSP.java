package com.jewellery.entity;

import java.util.List;


public class POSP {
	
	public POSPurchase posp;
	public List<POSPurchaseItem> listpospurchase;
	
	
	public POSPurchase getPosp() {
		return posp;
	}
	public void setPosp(POSPurchase posp) {
		this.posp = posp;
	}
	
	public List<POSPurchaseItem> getListpospurchase() {
		return listpospurchase;
	}
	public void setListpospurchase(List<POSPurchaseItem> listpospurchase) {
		this.listpospurchase = listpospurchase;
	}
	
			
	
}
