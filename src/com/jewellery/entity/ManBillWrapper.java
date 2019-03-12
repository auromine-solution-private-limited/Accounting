package com.jewellery.entity;

import java.util.List;


public class ManBillWrapper {
	
	public ManualBilling manBill;
	public List<ManBillRowList> manBillRowList;
		
	public ManualBilling getManBill() {
		return manBill;
	}
	public void setManBill(ManualBilling manBill) {
		this.manBill = manBill;
	}
	public List<ManBillRowList> getManBillRowList() {
		return manBillRowList;
	}
	public void setManBillRowList(List<ManBillRowList> manBillRowList) {
		this.manBillRowList = manBillRowList;
	}
}