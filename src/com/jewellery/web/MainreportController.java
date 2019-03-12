package com.jewellery.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.jewellery.dao.JournalDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.dao.POSCategoryDao;
import com.jewellery.dao.SavingSchemeDao;
import com.jewellery.entity.CardIssue;
import com.jewellery.entity.Journal;
import com.jewellery.dao.StartSchemeDao;

@Controller
public class MainreportController 
{
	@Autowired
	JournalDao journalDao;
	@Autowired
	private StartSchemeDao statartchemeDao;
	@Autowired
	private SavingSchemeDao savingschemeDao;	
	@Autowired
	private POSCategoryDao posCategoryDao;
	@Autowired
	LedgerDao ledgerDao;


	@RequestMapping(value ="/reports.htm",method = RequestMethod.GET)	
	public String get(@ModelAttribute("cardissue") CardIssue cardissue,Map<String, Object> map)
	{
		map.put("SchemeNameList", savingschemeDao.listSavingScheme());
		map.put("schemename", statartchemeDao.getSchmename());
		map.put("posCategoryNameList", posCategoryDao.getCategoryNameList());
		map.put("ledgerNameList", ledgerDao.getLedgerName());
		return "reports";
	}

	@RequestMapping("/binvoice.htm")
	public String getInvoice(@RequestParam("invoiceNo")String invoiceNo)
	{
		return ("invoice");
	}
	
	@RequestMapping("/bullioninc.htm")
	public String getBInvoice(@RequestParam("bullion_billno")String bullion_billno)
	{
		return ("bullioninvoice");
	}
	
	@RequestMapping("/purchasereg.htm")
	public String getPurchase(@RequestParam("prfrmdate")String prfrmdate,@RequestParam("prtodate")String prtodate)
	{
		return ("purchasereg");
	}
	
	@RequestMapping("/purchaseReturnReport.htm")
	public String getPurchasereturn(@RequestParam("prfrmdate1")String prfrmdate1,@RequestParam("prtodate1")String prtodate1)
	{
		return ("purchaseReturnReport");
	}
	
	@RequestMapping("/salesreg.htm")
	public String getSalesReg(@RequestParam("srfrmdate")String srfrmdate,@RequestParam("srtodate")String srtodate)
	{
		return ("salesreg");
	}

	@RequestMapping("/goldsalesreg.htm")
	public String getGoldSalesReg(@RequestParam("srfrmdate")String srfrmdate,@RequestParam("srtodate")String srtodate)
	{
		return ("Vattaxregister");
	}
	
	@RequestMapping("/silversalesreg.htm")
	public String getSilverSalesReg(@RequestParam("srfrmdate")String srfrmdate,@RequestParam("srtodate")String srtodate)
	{
		return ("Vattaxfree");
	}
		
	@RequestMapping("/salesReturnReport.htm")
	public String getSalesreturn(@RequestParam("srfrmdate1")String srfrmdate1,@RequestParam("srtodate1")String srtodate1,@RequestParam("bullion_Type")String bullionType)
	{
		return ("salesReturnReport");
	}
	
	@RequestMapping("/purchasebill.htm")
	public String getPurchasebill(@RequestParam("pbillNo")String pbillNo)
	{
		return ("purchasebill");
	}
	
	@RequestMapping("/oldgoldpurchase.htm")
	public String getOldgoldpurchase(@RequestParam("OldpbillNo")String OldpbillNo)
	{
		return ("oldgoldpurchase");
	}

	@RequestMapping("/stock.htm")
	public String getStock()
	{
		return ("stock");
	}	

	@RequestMapping("/jeweltypereport.htm")
	public String getCategory()
	{
		return ("jeweltypereport");
	}

	@RequestMapping("/categorylst.htm")
	public String getdetailedStock(@RequestParam("metalUsed")String metalUsed,@RequestParam("baseCategory")String baseCategory)
	{
		if(!baseCategory.equals("All"))
		{
			return ("categorylst");	
		}
		else
			return ("allCategory");
	}
	
	//lot add stock
	@RequestMapping("/lotaddstockRep.htm")
	public String getlotaddstockrep(@RequestParam("lotfrmdate")String lotfrmdate,@RequestParam("lottodate")String lottodate,@RequestParam("lotItemName")String lotItemName)
	{
		return ("lotaddstockRep");
	}
	
	 //lot detials stock 
	@RequestMapping("/lotDetailedStock.htm")
	public String getLotItemdetailedStock(@RequestParam("metalUsed")String metalUsed,@RequestParam("baseCategory")String baseCategory)
	{
		return ("lotDetailedStock");
	}
	
	//lot detials stock with ornamental details sotck 13/12/12
	@RequestMapping("/lotWithOrnamentalDetails.htm")
	public String getLotWithOrnamentalStock(@RequestParam("metalUsed")String metalUsed,@RequestParam("baseCategory")String baseCategory)
	{
		return ("lotWithOrnamentalDetails");
	}
	
	
	
	@RequestMapping("/salesest.htm")
	public String getsalesEstimate(@RequestParam("seic")String seic)	
	{
		return ("salesest");
	}

	@RequestMapping("/CashReceipt.htm")
	public String getCashreceipt (@RequestParam("crfrmdate")String crfrmdate,@RequestParam("crtodate")String crtodate)
	{
		return ("CashReceipt");
	}

	@RequestMapping("/CashPayment.htm")
	public String getCashpayment(@RequestParam("cpfrmdate")String cpfrmdate,@RequestParam("cptodate")String cptodate)
	{
		return ("CashPayment");	
	}

	@RequestMapping("/customer_summary.htm")
	public String getCustomer()
	{
		return ("customer_summary");
	}

	@RequestMapping("/supplier_saummary.htm")
	public String getSupplier()
	{
		return ("supplier_saummary");
	}

	@RequestMapping("/jobOrderReport.htm")
	public String getJoborder(@RequestParam("jofrmdate")String jofrmdate,@RequestParam("jotodate")String jotodate)
	{
		return ("jobOrderReport");
	}

	@RequestMapping("/jobOrderIssueReport.htm")
	public String getJoborderissue(@RequestParam("jofrmdate")String jofrmdate,@RequestParam("jotodate")String jotodate)
	{
		return ("jobOrderIssueReport");
	}

	@RequestMapping("/jobOrderReceiptReport.htm")
	public String getJoborderreceipt(@RequestParam("jofrmdate")String jofrmdate,@RequestParam("jotodate")String jotodate)
	{
		return ("jobOrderReceiptReport");
	}

	@RequestMapping("/tagged_stock_cancel.htm")
	public String getCanceleditemtaglist()
	{
		return ("tagged_stock_cancel");
	}

	@RequestMapping("/salesOrderReport.htm")
	public String getSalesOrder(@RequestParam("sofrmdate")String sofrmdate,@RequestParam("sotodate")String sotodate)
	{
		return ("salesOrderReport");
	}

	@RequestMapping("/salesorder_slip.htm")
	public String getSalesorderclip(@RequestParam("salesid")String salesid)
	{
		return ("salesorder_slip");
	}

	@RequestMapping("/Dailystock.htm")
	public String getDaywiseDag(@RequestParam("frmDate")String frmDate,@RequestParam("toDate")String toDate)
	{
		return ("Daywisetag");
	}
	
	@RequestMapping("/DayWiseStock.htm")
	public String getDaywiseStock(@RequestParam("StockMetalType")String StockMetalType,@RequestParam("frmDate")String frmDate,@RequestParam("toDate")String toDate)
	{
		return ("DayWiseStockReport");
	}
	
	@RequestMapping("/Exchangepurchasereg.htm")
	public String getExchangepurchase(@RequestParam("exprfrmdate")String exprfrmdate,@RequestParam("exprtodate")String exprtodate,@RequestParam("ExchangeType")String ExchangeType)
	{
		return ("ExchangepurchaseReg");
	}
	
	/** POS Sales Register */
	@RequestMapping("/possalesregister.htm")
	public String getPOSSalesReg(@RequestParam("posfrmdate")String posfrmdate,@RequestParam("postodate")String postodate)
	{
		return ("possalesregister");
	}
	
	/** POS Sales Return Register */
	@RequestMapping("/possalesreturnregister.htm")
	public String getPOSSalesReturnReg(@RequestParam("posfrmdate")String posfrmdate,@RequestParam("postodate")String postodate)
	{
		return ("possalesreturnregister");
	}
		
	/** POS Sales Invoice */
	@RequestMapping("/possalesinvoice.htm")
	public String getPosInvoice(@RequestParam("posSalesId")String posSalesId)
	{
		return ("possalesinvoice");
	}
	 
	/** POS Purchase Register */
	@RequestMapping("/pospurchaseregister.htm")
	public String getPOSPurchaseReg(@RequestParam("posfrmdate")String posfrmdate,@RequestParam("postodate")String postodate)
	{
		return ("pospurchaseregister");
	}
	
	/** POS Purchase Return Register */
	@RequestMapping("/pospurchasereturnregister.htm")
	public String getPOSPurchaseReturnReg(@RequestParam("posfrmdate")String posfrmdate,@RequestParam("postodate")String postodate)
	{
		return ("pospurchasereturnregister");
	}
	
	/** For Receipt Voucher */
	@RequestMapping("/voucher.htm")
	public String getReceiptVoucher(@RequestParam("voucherId") String voucherId){
		return ("receiptVoucher");
	}
	
	/** For Receipt Voucher Number validation **/
	@RequestMapping(value="RVReportValidate.htm", method= RequestMethod.GET)
	public @ResponseBody String checkRVNumber(@RequestParam("voucherNo") String VoucherNo) {
		String found = "false";
		try{
			List<Journal> jrnl = journalDao.getJournalAmt(VoucherNo);
			if(!jrnl.isEmpty()){
				if(jrnl.get(0).getJournalType().equalsIgnoreCase("Receipt"))
					found = "true";
				else
					found = "false";
			}else {
				found = "false";
			}
		}catch(Exception e){ }
		return found;		
	}
	
	/** For Payment Voucher */
	@RequestMapping("/paymentVoucher.htm")
	public String getPaymentVoucher(@RequestParam("voucherId") String voucherId){
		return ("paymentVoucher");
	}
	
	/** For Payment Voucher Number validation **/
	@RequestMapping(value="PVReportValidate.htm", method= RequestMethod.GET)
	public @ResponseBody String checkPVNumber(@RequestParam("voucherNo") String VoucherNo) {
		String found = "false";
		try{
			List<Journal> jrnl = journalDao.getJournalAmt(VoucherNo);
			if(!jrnl.isEmpty()){
				if(jrnl.get(0).getJournalType().equalsIgnoreCase("Payment"))
					found = "true";
				else
					found = "false";
			}else {
				found = "false";
			}
		}catch(Exception e){ }
		return found;		
	}
	
	/************Job order issue voucher****************/
	@RequestMapping("/jobOrderIssueVoucher.htm")
	public String joborderissueVoucher(@RequestParam("issueId") String issueId){
		return ("joborderIssueVoucher");
	} 
	
	/************Job order receipt voucher****************/
	@RequestMapping("/jobOrderReceiptVoucher.htm")
	public String joborderreceiptVoucher(@RequestParam("jobordervoucherId") String jobordervoucherId){
		return ("joborderReceiptVoucher");
	}
	
	/************Old Jewel from customer****************/
	@RequestMapping("/oldjewelfromcustomer.htm")
	public String oldJewelFromCustomer(@RequestParam("jewelrepaiId") String jewelrepaiId){
		return ("oldjewelfromcustomer");
	}
	
	/************Old Jewel To Smith****************/
	@RequestMapping("/oldjeweltosmith.htm")
	public String oldJewelToSmith(@RequestParam("jobordervoucherId") String jobordervoucherId){
		return ("oldjeweltosmith");
	}
	
	/************New Jewel From Smith****************/
	@RequestMapping("/newjewelfromsmith.htm")
	public String newJewelFromSmith(@RequestParam("smithId") String smithId){
		return ("newjewelfromsmith");
	}
	
	/************New Jewel To Customer****************/
	@RequestMapping("/newjeweltocustomer.htm")
	public String newJewelToCustomer(@RequestParam("customerId") String customerId){
		return ("newjeweltocustomer");
	}
	
	/** For Saving Scheme Receipt Voucher Report */ 
	@RequestMapping("/SSReceiptVoucher.htm")
	public String getSSReceiptVoucher(@RequestParam("receiptNO") String receiptNO){
		return ("SSReceiptVoucher");
	}
	/** For Saving Scheme PAYMENT DUES FROM CUSTOMER Report */ 
	@RequestMapping("/SSPaymentDue.htm")
	public String getpaymentDue(@RequestParam("schemeName") String schemeName,@RequestParam("tillDate") String tillDate,@RequestParam("reportType") String reportType){
		if(reportType.equalsIgnoreCase("single")){
			return ("SSPaymentDueReport");
		}else{
			return ("SSPaymentDueAllReport");
		}
	}
	//add new on 06-11-12(bullion sales register report)
		@RequestMapping("/bullionSalesReg.htm")
		public String getBullionSalesReg(@RequestParam("exprfrmdate")String exprfrmdate,@RequestParam("exprtodate")String exprtodate,@RequestParam("ExchangeType")String ExchangeType)
		{
			return ("bullionSalesReg");
		}
/** For Saving Scheme Card Wise Register Report */ 
	@RequestMapping("/SSCardissuedetails.htm")
	public String getCardissue(@RequestParam("schemeName") String schemeName){
		return ("Savingcardregister");
	}
	/** Saving Receipt Register */
	@RequestMapping("/savingreceiptregister.htm")
	public String getReceiptRegister(@RequestParam("recfrmdate")String recfrmdate,@RequestParam("rectodate")String rectodate)
	{
	  	return ("savingreceiptregister");
	}
	//Saving Scheme CancelCard Register
		@RequestMapping("/cancelCardRegister.htm")
		public String getSScancelcard(@RequestParam("schemeName") String schemeName){
			return ("cancelCardRegister");
		}
		
		//LOT Return  Register
		@RequestMapping("/lotreturnstockRep.htm")
		public String getlotReturnRep(@RequestParam("stockToDate") String stockToDate,@RequestParam("stockFromDate") String stockFromDate,@RequestParam("lotItemName") String lotItemName){
			return ("lotreturnstockRep");
		}
		
		
		/*** POS Stock Reports ***/
		@RequestMapping("/POSStock.htm")
		public String getPOSStock(@RequestParam("Stock_Type")String Stock_Type,@RequestParam("Category_Name")String Category_Name)
		{
			return ("PosStockReport");
		}
	/*****Cash Book  Register*******/
		@RequestMapping("/CashBookRegister.htm")
		public ModelAndView getCashBookRegister(@RequestParam("FromDateJournalDate")String FromDateJournalDate,@RequestParam("EndJournalDate")String EndJournalDate,HttpServletRequest request)
		{
		
		BigDecimal convert = new BigDecimal("-1");
		Object debitSum = journalDao.getDebitAmount(FromDateJournalDate);
		Object creditSum = journalDao.getCreditAmount(FromDateJournalDate);
		if(debitSum==null){
			debitSum=0.00;
		}
		if(creditSum==null){
			creditSum=0.00;
		}
		BigDecimal op_debitsum = new BigDecimal(debitSum.toString());
		BigDecimal op_creditsum = new BigDecimal(creditSum.toString());
		BigDecimal openingBal = op_debitsum.subtract(op_creditsum);
		
		
			if(op_debitsum.compareTo(op_creditsum)==1)
			{
				request.setAttribute("debitSum", openingBal);
				request.setAttribute("creditSum", 0.00);
			}
			else
			{
				openingBal = openingBal.multiply(convert);
				request.setAttribute("debitSum", 0.00);
				request.setAttribute("creditSum", openingBal);
			}
		
		return new ModelAndView("CashBookRegister");
	}
		
		/*** Accounting Reports - DAY BOOK ***/
		@RequestMapping("/AccDayBook.htm")
		public String getAccDayBook(@RequestParam("DBReportType")String DBReportType,@RequestParam("DBfrmdate")String DBfrmdate,@RequestParam("DBtodate")String DBtodate)
		{
			if(DBReportType.equalsIgnoreCase("Day Book")){
			return ("AccDayBookReport");
			}else {
				return ("AccDetailedDBReport");
			}
		}
		
		
		/*************************************Ledger Report *******************************/
		@RequestMapping("/allLedgerReport.htm")
		public ModelAndView getLedgerReport(@RequestParam("LedgerName")String LedgerName  ,@RequestParam("FromDateJournalDate")String FromDateJournalDate,@RequestParam("ToJournalDate")String ToJournalDate,HttpServletRequest request)
		{
			
			BigDecimal convert = new BigDecimal("-1");
			Object debitSum = journalDao.getLedgerDebitAmount(FromDateJournalDate, LedgerName);
			Object creditSum = journalDao.getLedgerCreditAmount(FromDateJournalDate, LedgerName);
			if(debitSum==null){
				debitSum=0.00;
			}
			if(creditSum==null){
				creditSum=0.00;
			}
			BigDecimal op_debitsum = new BigDecimal(debitSum.toString());
			BigDecimal op_creditsum = new BigDecimal(creditSum.toString());
			BigDecimal openingBal = op_debitsum.subtract(op_creditsum);
						
				if(op_debitsum.compareTo(op_creditsum)==1)
				{
					request.setAttribute("debitSum", openingBal);
					request.setAttribute("creditSum", 0.00);
				}
				else
				{
					openingBal = openingBal.multiply(convert);
					request.setAttribute("creditSum", openingBal);
					request.setAttribute("debitSum", 0.00);
				}	
			return new ModelAndView("allLedgerReport");
		}
		
		/// Trial Balance Report	
		@RequestMapping("/trialBalance.htm")
		public String showTrialBalanceReport()
		{
			return ("trialBalanceReport");
		}
		
		
		///  Balance Sheet Report	
			@RequestMapping("/balancesheet.htm")
			public String showBalanceSheetReport(HttpServletRequest request)
			{
				/*Object openingstockamount = postagItemDao.sumOpeningStock();
			    BigDecimal stockAmount = new BigDecimal(openingstockamount.toString());
			    System.out.println(stockAmount);
				request.setAttribute("stockAmount",  stockAmount);*/
				return ("balanceSheetReport");
			}
			
			/*** IncomeAndExpense Reports ***/
			@RequestMapping("/IncomeAndExpense.htm")
			public String getIncomeAndExpense()
			{				
				return ("IncomeAndExpense");
			}
	
	// Estimate Sales Register
	@RequestMapping("/ESalesRegister.htm")
	public String getESalesReg(@RequestParam("ESRfrmdate")String ESRfrmdate,@RequestParam("ESRtodate")String ESRtodate)
	{
		return ("ESalesRegister");
	}
	
	// Estimate Sales Register
	@RequestMapping("/EPOSSalesRegister.htm")
	public String getEPSalesReg(@RequestParam("EPSRfrmdate")String EPSRfrmdate,@RequestParam("EPSRtodate")String EPSRtodate)
	{
		return ("EPOSSalesRegister");
	}
	// Group Sales Register
	@RequestMapping("/groupSalesReg.htm")
	public String getGroupSalesReg(@RequestParam("frmDate")String EPSRfrmdate,@RequestParam("toDate")String EPSRtodate)
	{
		return ("groupSalesReg");
	}
	// Group Sales Register
	@RequestMapping("/oldPurchaseCatWiseReport.htm")
	public String getoldPurchaseCatWiseReport(@RequestParam("exprfrmdate")String EPSRfrmdate,@RequestParam("exprtodate")String EPSRtodate)
	{
		return ("oldPurchaseCatWiseReport");
	}

}