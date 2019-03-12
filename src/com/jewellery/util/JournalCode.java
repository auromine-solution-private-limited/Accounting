package com.jewellery.util;


import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;

import com.jewellery.dao.JournalDao;
import com.jewellery.dao.ManualBillingDao;
import com.jewellery.dao.POSSalesDao;
import com.jewellery.dao.PurchaseDao;
import com.jewellery.dao.RefineryDao;
import com.jewellery.dao.RefineryReceiptDao;
import com.jewellery.dao.SSReceiptDao;
import com.jewellery.entity.Journal;
import com.jewellery.entity.PSales;
import com.jewellery.entity.Purchase;
import com.jewellery.entity.RefineryIssue;
import com.jewellery.entity.RefineryReceipt;
import com.jewellery.entity.Saving_SchemeReceipt;

public class JournalCode {

	@Autowired
	JournalDao journalDao;
	@Autowired
	PurchaseDao purchaseDao;
	@Autowired
	ManualBillingDao manualBillingDao;
	@Autowired
	private SSReceiptDao ssrDao;
	@Autowired
	private POSSalesDao posSalesDao;
	@Autowired
	private RefineryDao refineryDao;
	@Autowired
	private RefineryReceiptDao refineryReceiptDao;

	public String getJournalNumber(Journal journal) {

		String displayCode = null;
		String journalNoList = null;
		String journalCodeNo = null;
		BigInteger found = null;

		if (journal.getJournalType() == null) {
			found = journalDao.getAutoJournalCode();
			journalNoList = "JE"+found;
			journalCodeNo = "JE0";
			
		} else if (journal.getJournalType().equalsIgnoreCase("Receipt")) {

			String receiptType = journal.getReceiptType();
			
			if(journal.getReceiptType() == null){
				found = journalDao.getReceiptCode();
				journalNoList = "RC"+found;
				journalCodeNo = "RC0";
				
			}else if (receiptType.equalsIgnoreCase("Advance Receipt")) {
				found = journalDao.getAdvanceCode();
				journalNoList = "AR"+found;
				journalCodeNo = "AR0";
				

			} else if (receiptType.equalsIgnoreCase("Jewel Repair")) {
				found = journalDao.getJewelRepairCode();
				journalNoList = "JR"+found;
				journalCodeNo = "JR0";
				

			} else if (receiptType.equalsIgnoreCase("Bill Receipt")) {
				found = journalDao.getBillCode();
				journalNoList = "BR"+found;
				journalCodeNo = "BR0";
				

			} else if (receiptType.equalsIgnoreCase("Saving Receipt")) {
				found = journalDao.getSavingCode();
				journalNoList = "SR"+found;
				journalCodeNo = "SR0";
				
			} 

		}
		else if(journal.getJournalType().equalsIgnoreCase("POS Purchase Return Receipt"))
		{
			found = journalDao.getReceiptCode();
			journalNoList = "RC"+found;
			journalCodeNo = "RC0";
		}
		else if (journal.getJournalType().equalsIgnoreCase("Payment")) {
			found = journalDao.getPaymentCode();
			journalNoList = "PT"+found;
			journalCodeNo = "PT0";
		
		} else if (journal.getJournalType().equalsIgnoreCase("Journal")) {
			found = journalDao.getJournalCode();
			journalNoList = "JL"+found;
			journalCodeNo = "JL0";
			
		} else if (journal.getJournalType().equalsIgnoreCase("Contra")) {
			found = journalDao.getContraCode();
			journalNoList = "CT"+found;
			journalCodeNo = "CT0";
			
		}
		
		if (found != null) {
			journalCodeNo = journalNoList;
		}

		String splitNo = journalCodeNo.substring(2);
		int num = Integer.parseInt(splitNo);
		num++;
		String number = Integer.toString(num);
		displayCode = journalCodeNo.substring(0, 2) + number;
		return displayCode;	
	}
	
	/** For Purchase Auto Alpha Numeric Id Generation **/
	public String generatePurchaseInvoiceNO(Purchase purchase)
	{
		String Invoiceno=null;
		String getPurInvoiceNo;
		String displayCode=null;
		BigInteger found = null;
		
		if(purchase.getPurchaseType().equalsIgnoreCase("Purchase") )
		{
			if(purchase.getBullionType().equalsIgnoreCase("Gold Exchange") || purchase.getBullionType().equalsIgnoreCase("Old Gold Coin") || purchase.getBullionType().equalsIgnoreCase("Old Gold Pure Bullion"))
			{
				found = purchaseDao.getgoldexchangeinvoiceNo();
				getPurInvoiceNo= "GE"+found;
				Invoiceno="GE0";	
			}
			else if(purchase.getBullionType().equalsIgnoreCase("Silver Exchange") || purchase.getBullionType().equalsIgnoreCase("Old Silver Coin") || purchase.getBullionType().equalsIgnoreCase("Old Silver Pure Bullion"))
			{
				found = purchaseDao.getsilverdexchangeinvoiceNo();
				getPurInvoiceNo = "SE"+found;
				Invoiceno="SE0";	
			}
			else {
				found = purchaseDao.getPurchaseInvoiceNo();
				getPurInvoiceNo = "OP"+found;
				Invoiceno="OP0";
			}
		} else {
			found = purchaseDao.getPurchaseInvoiceNo();
			getPurInvoiceNo = "OP"+found;
			Invoiceno="OP0";
		}
		
		if(found != null)
		{
			 Invoiceno = getPurInvoiceNo;
		}
		String splitNo=Invoiceno.substring(2);
		int num = Integer.parseInt(splitNo);
		num++;
		String number = Integer.toString(num);
		displayCode = Invoiceno.substring(0, 2) + number;
		return displayCode;
	}
	
	/** For ManualBilling Alpha Numeric Id Generation **/
	public String generateManualBillingNO(String mBillFormType)
	{
		String Invoiceno=null;
		String getMBillNo;
		String displayCode=null;
		BigInteger found = null;
		
		if(mBillFormType.equalsIgnoreCase("vatFreeForm")){
			found = manualBillingDao.getLatestMBillVatFreeCode();
			getMBillNo = "MF"+found;
			Invoiceno="MF0";
		}else{
			found = manualBillingDao.getLatestMBillCode();
			getMBillNo = "MV"+found;
			Invoiceno="MV0";
		}
		
		if(found != null)
		{
			 Invoiceno = getMBillNo;
		}
		String splitNo=Invoiceno.substring(2);
		int num = Integer.parseInt(splitNo);
		num++;
		String number = Integer.toString(num);
		displayCode = Invoiceno.substring(0, 2) + number;
		return displayCode;
	}
	
	/** For Savings Scheme Receipt ***/
	public String getSSReceiptNumber(Saving_SchemeReceipt ssReceipt) {
		
		String finalCode = null;
		String receiptNoList = null;
		String receiptCodeNo = null;
		BigInteger found = null;

		if(ssReceipt.getFormType().equalsIgnoreCase("Saving Scheme Receipt")){
			found = ssrDao.getAutoSSRCode();
			receiptNoList = "CR"+found;
			receiptCodeNo = "CR0";
		}else{
			found = ssrDao.getAutoSSPCode();
			receiptNoList = "CP"+found;
			receiptCodeNo = "CP0";
		}
				
		if (found != null) {
			receiptCodeNo = receiptNoList;
		}

		String splitNo = receiptCodeNo.substring(2);
		int num = Integer.parseInt(splitNo);
		num++;
		String number = Integer.toString(num);
		finalCode = receiptCodeNo.substring(0, 2) + number;
		return finalCode;			
	}
	
	/** For Savings Scheme Receipt ***/
	
	
	
	
	/** POS Estimate Sales 2-1-12 ***/
public String getEP(PSales possales) {
		
		String finalCode = null;
		String receiptNoList = null;
		String receiptCodeNo = null;
		BigInteger found = null;

		if(possales.getPossales().getSalesType().equalsIgnoreCase("Estimate POS Sales")){
			found = posSalesDao.getLastEstimPOSSalesNo();
			receiptNoList = "EP"+found;
			receiptCodeNo = "EP0";
			System.out.println(found);
		}
				
		if (found != null) {
			receiptCodeNo = receiptNoList;
		}

		String splitNo = receiptCodeNo.substring(2);
		int num = Integer.parseInt(splitNo);
		num++;
		String number = Integer.toString(num);
		finalCode = receiptCodeNo.substring(0, 2) + number;
		return finalCode;			
	}
/** POS Sales 7-1-12 ***/
public String getPOSINV(PSales possales) {
	
	String finalCode = null;
	String receiptNoList = null;
	String receiptCodeNo = null;
	BigInteger found = null;
	
	if(possales.getPossales().getSalesType().equalsIgnoreCase("POS Sales")){
		found = posSalesDao.getLastPOSSalesNo();
		receiptNoList = "POS"+found;
		receiptCodeNo = "POS0";
		System.out.println(found);
	}
	
	if (found != null) {
		receiptCodeNo = receiptNoList;
	}
	
	String splitNo = receiptCodeNo.substring(3);
	int num = Integer.parseInt(splitNo);
	num++;
	String number = Integer.toString(num);
	finalCode = receiptCodeNo.substring(0, 3) + number;
	return finalCode;			
}

/** POS Sales Return 9-1-12 ***/
public String getPOSReturnInv(PSales possales) {
		
		String finalCode = null;
		String receiptNoList = null;
		String receiptCodeNo = null;
		BigInteger found = null;

		if(possales.getPossales().getSalesType().equalsIgnoreCase("POS Sales Return")){
			found = posSalesDao.getLastPORInvNo();
			receiptNoList = "POR"+found;
			receiptCodeNo = "POR0";
			System.out.println(found);
		}
				
		if (found != null) {
			receiptCodeNo = receiptNoList;
		}

		String splitNo = receiptCodeNo.substring(3);
		int num = Integer.parseInt(splitNo);
		num++;
		String number = Integer.toString(num);
		finalCode = receiptCodeNo.substring(0, 3) + number;
		return finalCode;			
	}

/** For Refinery Issue No Alpha Numeric Id Generation **/
public String generateRefineryIssueNO(RefineryIssue refinery)
{
	String Invoiceno=null;
	String getrefineryIssueNo;
	String displayCode=null;
	BigInteger found = null;
	
		found = refineryDao.getRefineryIssueNo();
		getrefineryIssueNo = "RI"+found;
		Invoiceno="RI0";
	
	if(found != null)
	{
		 Invoiceno = getrefineryIssueNo;
	}
	String splitNo=Invoiceno.substring(2);
	int num = Integer.parseInt(splitNo);
	num++;
	String number = Integer.toString(num);
	displayCode = Invoiceno.substring(0, 2) + number;
	return displayCode;
}
/** For Refinery Receipt No Alpha Numeric Id Generation **/
public String generateRefineryReceiptNO(RefineryReceipt refineryReceipt)
{
	String Invoiceno=null;
	String getrefineryReceiptNo;
	String displayCode=null;
	BigInteger found = null;
	
	found = refineryReceiptDao.getRReceiptNo();
	getrefineryReceiptNo = "RR"+found;
	Invoiceno="RR0";
	
	if(found != null)
	{
		Invoiceno = getrefineryReceiptNo;
	}
	String splitNo=Invoiceno.substring(2);
	int num = Integer.parseInt(splitNo);
	num++;
	String number = Integer.toString(num);
	displayCode = Invoiceno.substring(0, 2) + number;
	return displayCode;
}
}
