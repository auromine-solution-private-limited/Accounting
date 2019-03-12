package com.jewellery.print;

import javax.servlet.http.HttpServletRequest;

import com.jewellery.dao.CompanyInfoDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.entity.PSales;
import com.jewellery.entity.Purchase;
import com.jewellery.entity.Sales;

public interface InvoiceBillFormat {
	
	// Print Service Method
	public void getPrinterService(String billno, String fileName);
	
	//Ornament Invoice Formats 
	public  void printinvoice(Sales sales, CompanyInfoDao companyinfo, LedgerDao ledgerDao, HttpServletRequest request);
	public void printFourInchInvoice(Sales sales, CompanyInfoDao companyinfo,LedgerDao ledgerDao, HttpServletRequest request);
	public void printFourInchOldPurchase(Purchase purchase, CompanyInfoDao companyinfo,LedgerDao ledgerDao, HttpServletRequest request);

	//POS Invoice Formats
	public void printPOSInvoiceMatrixEightInch(PSales posSales, CompanyInfoDao companyinfo,LedgerDao ledgerDao, HttpServletRequest request);
	public void printPOSInvoiceMatrixFourInch(PSales posSales, CompanyInfoDao companyinfo,LedgerDao ledgerDao, HttpServletRequest request);
	public void printPOSInvoiceCitizen(PSales posSales, CompanyInfoDao companyinfo,LedgerDao ledgerDao, HttpServletRequest request);
}
