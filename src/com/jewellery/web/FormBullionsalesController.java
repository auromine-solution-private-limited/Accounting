package com.jewellery.web;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import com.jewellery.validator.BullionSalesValidator;
import com.jewellery.entity.CompanyInfo;
import com.jewellery.entity.JewelStock;
import com.jewellery.entity.Sales;
import com.jewellery.entity.Ledger;
import com.jewellery.dao.CompanyInfoDao;
import com.jewellery.dao.JewelStockDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.dao.SalesDao;
import com.jewellery.dao.UserloginDao;
import com.jewellery.entity.ItemMaster;
import com.jewellery.dao.ItemMasterDao;
import com.jewellery.entity.Journal;
import com.jewellery.dao.JournalDao;
import com.jewellery.util.JournalCode;;


@Controller
public class FormBullionsalesController extends JournalCode{
		
	private SalesDao salesDao;	
	private ItemMasterDao itemmasterDao;		
	private LedgerDao ledgerDao; 
	private UserloginDao userloginDao;	
	private JournalDao journalDao;
	public  Journal jrnl;	
	private JewelStockDao jewelStockDao;
	private JewelStock jewelStockObj;
	private CompanyInfoDao companyInfoDao;
	
	String partyName ;
 	String ledgername;
 	String closingType = "Debit";
	String closingTyp = "Credit"; 		 	
 	String clType;
 	
 	
 	BigDecimal clBal;
 	BigDecimal ZERO=new BigDecimal("0");
 	BigDecimal CONVERT=new BigDecimal("-1");
 	List<String> ledgerGroupCode;
 	
	@Autowired
	private BullionSalesValidator bullionSalesValidator;

	@Autowired
	public FormBullionsalesController(SalesDao salesDao, ItemMasterDao itemmasterDao, LedgerDao ledgerDao, UserloginDao userloginDao, JournalDao journalDao, JewelStockDao jewelStockDao, CompanyInfoDao companyInfoDao){
				
	 	this.salesDao = salesDao;		
		this.itemmasterDao = itemmasterDao;
		this.ledgerDao = ledgerDao;
		this.userloginDao = userloginDao;
		this.journalDao = journalDao;	
		this.jewelStockDao = jewelStockDao;
		this.companyInfoDao = companyInfoDao;
	}
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
	throws Exception{
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class, dateEditor);		
	}
	
	//listing Bullion sales details	
	@RequestMapping(value="/sales.htm",method = RequestMethod.GET)
	public  ModelAndView listBullionSales(@ModelAttribute("sales")Sales sales,String customername,String itemcode, ModelMap model){
	
		model.addAttribute("journalListReceiptID", journalDao.getReceiptTypeName(customername).toString());		
		model.put("salesList", salesDao.listSales());
		return new ModelAndView("sales",model);
	}
	
	//ajax code for on change customer journal Receipt ID
			@RequestMapping(value="/creditAccount_name.htm",method=RequestMethod.GET)
			public @ResponseBody String GetJournalID(@RequestParam(value="credit",required=false)String customername, ModelMap model,Sales sales){		
			
				String ID="";
				try
				{
				String journalList = journalDao.getReceiptTypeName(customername).toString();
					 if(!journalList.isEmpty()){
						 ArrayList<String> journalId=new ArrayList<String>();
						 journalId.add(0, "Select");
						 journalId.add(1, journalList.toString());
						 ID=journalId.toString();
					 }
					}
				catch(NumberFormatException e){
					
				}					
				
				return ID;
			}
			//ajax code for on change JouranlId to get Amount
			@RequestMapping(value="/JournalId_amt.htm",method=RequestMethod.GET)
			public @ResponseBody String GetAmount(@RequestParam(value="creditAmt",required=false)String id, ModelMap model){

				String amt="0";
				try
				{
					List<Journal> jourl=journalDao.getJournalAmt(id);
					amt=jourl.get(0).getDebitAmount().toString();
				}
				catch(NumberFormatException e){
					amt ="0"; 
				}catch(IndexOutOfBoundsException e){
					amt ="0";
				}
				
				return amt;
			}
			
			
			//ajax panNumber code 
			@RequestMapping(value = "/tin_Number.htm",method = RequestMethod.GET)
			public @ResponseBody String showPan(@RequestParam(value="num",required=false)String customerName, Sales sales){
				String PAN = "";
				List<Ledger> itemMaster = ledgerDao.listCustomerPan(customerName);
				try{
					if(itemMaster.get(0).getPanNumber() != null || !itemMaster.get(0).getPanNumber().isEmpty()){
						PAN=itemMaster.get(0).getPanNumber();
					}	
				}catch (NullPointerException ne) {			
				}			
				return PAN;
			}
			
			
			//ajax vat amount code
			@RequestMapping(value = "/vat_amount.htm",method = RequestMethod.GET)
			public @ResponseBody String showbullionvat(@RequestParam(value="ItemCode",required=false)String ItemCode){
				String VAT="0";
				
				List<ItemMaster> ajaxitemObj = itemmasterDao.getBullionVat(ItemCode);
				BigDecimal vv = ajaxitemObj.get(0).getTax();				
				VAT = vv.toString();
				return VAT;
				 
			}
			
			
			private String getLastBullionNo(){
				
				BigInteger found = salesDao.getLatestBullionNo();
				String listBillNo = "BS"+found;
				String bill_NO = "BS000";
				String displayCode;
							
				if(found != null)
				{
					bill_NO = listBillNo;				
				}
				
				String splitNo = bill_NO.substring(2);
			    int num = Integer.parseInt(splitNo);   
			    num++;        
			    String number = Integer.toString(num);        
			    displayCode = bill_NO.substring(0, 2) + number;
				return displayCode;		
			}
			
		//To Add Or Save the record	
		@RequestMapping(value = "/formbullionsales.htm", method = RequestMethod.POST, params="insert")
		public ModelAndView addData(@ModelAttribute("sales") Sales sales, BindingResult result,Integer salesId, SessionStatus status,ModelMap model) 
		{	
			bullionSalesValidator.validate(sales, result);
			ItemMaster itemDetails = new ItemMaster();			
			
			if(result.hasErrors()) 
			{
				ModelMap map = new ModelMap();			
				map.put("name", ledgerDao.listLedgerName());		    
				map.put("s_manname", userloginDao.userlist());
				model.addAttribute("journalListReceiptID", journalDao.getReceiptTypeName(sales.getCustomerName()));				
				map.put("command", sales);
				return new ModelAndView("formbullionsales",map);
			}
					
				String billNumber = getLastBullionNo();
				sales.setBillNo(billNumber);
				String salesTypeId = getBullionSalesTypeId(sales);
				sales.setSalesTypeId(salesTypeId);
				
				
				salesDao.insertSales(sales);//Insert into sales														
				status.setComplete();	
					
				/*** Receipt Id update *****/
				String journal_NO = sales.getReceiptID();
			    journalDao.UpdateStatusID(journal_NO);
			    //Update Stock for the first Item Code 
			    
				String itemcd = sales.getItemCode();  
				BigDecimal grosswt =  sales.getGrossWeight();						

				List<ItemMaster> itemList = itemmasterDao.searchItemMaster(itemcd);	
				
				BigDecimal totgross= new BigDecimal("0.0");
				BigDecimal gross_Wt = new BigDecimal("0.0");
				BigDecimal net_Wt = new BigDecimal("0.0");
				
				for (int i = 0; i < itemList.size(); i++) {
					ItemMaster imast = (ItemMaster)itemList.get(i);
					if (imast instanceof ItemMaster) {
						
						itemDetails = (ItemMaster) imast;							
						
						gross_Wt = itemDetails.getGrossWeight();
						gross_Wt = gross_Wt.subtract(grosswt);
						itemDetails.setGrossWeight(gross_Wt);
						
						net_Wt = itemDetails.getNetWeight();
						net_Wt = net_Wt.subtract(grosswt);
						itemDetails.setNetWeight(net_Wt);
											
						totgross = itemDetails.getTotalGrossWeight();
						totgross = totgross.subtract(grosswt);
						itemDetails.setTotalGrossWeight(totgross);						
						
						itemmasterDao.updateItemMaster(itemDetails);
						// Update bullion Weight in the item master for item code
						
						}
					}//for loop		
				
				
//Update Stock Entry In Jewel Stock
				
				jewelStockObj = new JewelStock();
				
				jewelStockObj.setStock_TransType(sales.getFormType());
				jewelStockObj.setStock_StockType("Bullion");
				jewelStockObj.setStock_TransNO(sales.getSalesTypeId());
				jewelStockObj.setStock_TransDate(sales.getSalesDate());
				jewelStockObj.setStock_ItemCode(sales.getItemCode());
				jewelStockObj.setStock_CategoryName(sales.getItemName());
				jewelStockObj.setStock_SubCategoryName(sales.getItemName());
				jewelStockObj.setStock_ItemName(sales.getItemName());
				jewelStockObj.setStock_MetalType(sales.getBullionType());	
				jewelStockObj.setStock_MetalUsed(sales.getBullionType());
				jewelStockObj.setStock_CLGrossWeight(CONVERT.multiply(sales.getGrossWeight()));
				jewelStockObj.setStock_CLNetWeight(CONVERT.multiply(sales.getGrossWeight()));
				jewelStockObj.setStock_CLTotalGrossWeight(CONVERT.multiply(sales.getGrossWeight()));
				jewelStockObj.setStock_BullionRate(sales.getBullionRate());
				jewelStockDao.saveJewelStock(jewelStockObj);
			    
				//Update Stock for the second item code
				
				String itemcd1 = sales.getItemCode1();			
				BigDecimal grosswt1 = (BigDecimal)sales.getGrossWeight1();
			
				List<ItemMaster> itemList1 = itemmasterDao.searchItemMaster(itemcd1);
						
				for (int i = 0; i < itemList1.size(); i++) {
					ItemMaster imast = (ItemMaster)itemList1.get(i);
					if (imast instanceof ItemMaster) {
						
						itemDetails = (ItemMaster) imast;					
						
						gross_Wt = itemDetails.getGrossWeight();
						gross_Wt = gross_Wt.subtract(grosswt1);
						itemDetails.setGrossWeight(gross_Wt);
						
						net_Wt = itemDetails.getNetWeight();
						net_Wt = net_Wt.subtract(grosswt1);
						itemDetails.setNetWeight(net_Wt);					
						
						totgross = itemDetails.getTotalGrossWeight();
						totgross = totgross.subtract(grosswt1);
						itemDetails.setTotalGrossWeight(totgross);
						
						itemmasterDao.updateItemMaster(itemDetails);
						//Update Bullion Weight in the item master for the item code 1 
						}
					}// for loop		

							
				//updating party balances on Credit, Card and Cheque sales
				BigDecimal closingAmount = sales.getBillAmount();
				BigDecimal receivedAmount = sales.getAmtRecd();
			 	String partyName = sales.getCustomerName();
			 	Date sales_date = sales.getSalesDate();
			 	String ledgername;
			 	String closingType = "Debit";
				String closingTyp = "Credit"; 			 
			 	
			 	String clType;
			 	BigDecimal clBal;
			 	
			 		 	
			 	//Update //Update Sales Account					
				ledgername = "Sales Account";
				List <Ledger> salesList = ledgerDao.searchLedger(ledgername);
				
				clBal = salesList.get(0).getClosingTotalBalance();	
				clType = salesList.get(0).getClosingTotalType();					 				 
			 	
			 	if(clType.equals("Credit")){			 		
			 		ledgerDao.updatePartyBalance(closingAmount, closingTyp, ledgername);			 		
			 	}
			 	else{
			 		if(clBal.compareTo(closingAmount) == 1){			 			
			 			ledgerDao.updateCreditPartyBalance(closingAmount, closingType, ledgername);
			 		}
			 		else{
			 			ledgerDao.updateCrPartyBalance(closingAmount, closingTyp, ledgername);
			 		}	
			 	}					
				
			 	if (partyName.equals("walk_in"))  // for walk-in customer
			 	{			
			 			//Auto Receipt entry for journal
						jrnl = new Journal();	
						jrnl.setJournalNO(getJournalNumber(jrnl));
						jrnl.setTransactionId("S"+sales.getSalesId());
						jrnl.setJournalType("Cash Bullion Sales");
					    jrnl.setJournalDate(sales_date);
					    
					 // Set Account group code as Debit code
						ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");			
						jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
						jrnl.setDebitAccountName("Cash Account");
						
						// Set Account group code as Credit code
						ledgerGroupCode = ledgerDao.getLedgerAccountCode("Sales Account");
						jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
						jrnl.setCreditAccountName("Sales Account");
						
						jrnl.setDebitAmount(closingAmount);
						jrnl.setCreditAmount(closingAmount);
						jrnl.setNarration("Bill No "+sales.getSalesTypeId());							
						journalDao.insertJournal((Journal) jrnl);			 
									
						//update cash balance in cash account 
						ledgername="Cash Account";
						List <Ledger> ledgerList = ledgerDao.searchLedger(ledgername);		
						clBal = ledgerList.get(0).getClosingTotalBalance();	
						clType = ledgerList.get(0).getClosingTotalType();			 	

						if(clType.equals("Debit")){
					 		ledgerDao.updatePartyBalance(closingAmount, closingType, ledgername);		 		
					 	}
					 	else if(clType.equals("Credit")){	
					 		if(clBal.compareTo(closingAmount)==1){
					 			ledgerDao.updateCreditPartyBalance(closingAmount, "Credit", ledgername);
					 		}
					 		else{
					 			ledgerDao.updateCrPartyBalance(closingAmount, closingType, ledgername);
					 		}
					 	}			 		
			 	}
			else{  
					// for regular customer(party Balance)
				
					List<Ledger> customerList = ledgerDao.searchLedger(partyName);			
					clBal = customerList.get(0).getClosingTotalBalance();	
					clType = customerList.get(0).getClosingTotalType();
									
					if(clType.equals("Debit")){
				 		ledgerDao.updatePartyBalance(closingAmount, closingType, partyName);		 		
				 	}
				 	else if(clType.equals("Credit")){	
				 		if(clBal.compareTo(closingAmount) == 1){
				 			ledgerDao.updateCreditPartyBalance(closingAmount, "Credit", partyName);
				 		}
				 		else{
				 			ledgerDao.updateCrPartyBalance(closingAmount, closingType, partyName);
				 		}
				 	}
				
						//if the type is credit
						jrnl = new Journal(); 	
						jrnl.setJournalNO(getJournalNumber(jrnl));
						jrnl.setJournalType("Bullion Sales");
						jrnl.setTransactionId("S"+sales.getSalesId());
					    jrnl.setJournalDate(sales_date);
					    
					 // Set Account group code as Debit code
						ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);			
						jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
						jrnl.setDebitAccountName(partyName);
						
						// Set Account group code as Credit code
						ledgerGroupCode = ledgerDao.getLedgerAccountCode("Sales Account");
						jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
						jrnl.setCreditAccountName("Sales Account");
						
						jrnl.setDebitAmount(closingAmount);
						jrnl.setCreditAmount(closingAmount);
						jrnl.setNarration("Bill No " + sales.getSalesTypeId());							
						journalDao.insertJournal((Journal) jrnl);					 
			 
			 	if(sales.getAmtRecd().signum() != 0)
				 {		 
			 			//Auto Cash receipt for journal
						jrnl = new Journal();	
						jrnl.setJournalNO(getJournalNumber(jrnl));
						jrnl.setTransactionId("S"+sales.getSalesId());
						jrnl.setJournalType("Bullion Sales Receipt");
					    jrnl.setJournalDate(sales_date);
					    
					    
					    // Set Account group code as Debit code
						ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");			
						jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
						jrnl.setDebitAccountName("Cash Account");
						
						// Set Account group code as Credit code
						ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
						jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
						jrnl.setCreditAccountName(partyName);
						
						jrnl.setDebitAmount(receivedAmount);
						jrnl.setCreditAmount(receivedAmount);
						jrnl.setNarration("Bill No "+sales.getSalesTypeId());							
						journalDao.insertJournal((Journal) jrnl);		

						//update cash balance
						ledgername="Cash Account";
						List <Ledger> ledgerList = ledgerDao.searchLedger(ledgername);		
						clBal = ledgerList.get(0).getClosingTotalBalance();	
						clType = ledgerList.get(0).getClosingTotalType();					 				 
					 	
						if(clType.equals("Debit")){
					 		ledgerDao.updatePartyBalance(receivedAmount, closingType, ledgername);		 		
					 	}
					 	else if(clType.equals("Credit")){	
					 		if(clBal.compareTo(receivedAmount) == 1){
					 			ledgerDao.updateCreditPartyBalance(receivedAmount, "Credit", ledgername);
					 		}
					 		else{
					 			ledgerDao.updateCrPartyBalance(receivedAmount, closingType, ledgername);
					 		}
					 	}
						
						
						//update party balance
						List<Ledger> custList = ledgerDao.searchLedger(partyName);			
						clBal = custList.get(0).getClosingTotalBalance();	
						clType = custList.get(0).getClosingTotalType();
					 			
						
					  if(clType.equals("Debit")){	
					 		if(clBal.compareTo(receivedAmount) == 1){
					 			ledgerDao.updateCreditPartyBalance(receivedAmount,closingType, partyName);
					 		}
					 		else{
					 			ledgerDao.updateCrPartyBalance(receivedAmount, closingTyp, partyName);
					 		}
					 }
					  else if(clType.equals("Credit")){
					 		ledgerDao.updatePartyBalance(receivedAmount, closingTyp, partyName);		 		
					  }

				 }//for receipt Amount
					 
				} //for non-walkin customer
			 	
			 	
			 	model.addAttribute("invc", sales.getSalesTypeId());
				List<CompanyInfo> companyList = companyInfoDao.listCompanyInfo();
				//InvoiceBillFormat orninoviceObj = new InvoiceTextFormatPrint();

				if (!companyList.isEmpty()) { // if 
					return new ModelAndView("redirect:bullioninvoicePreview.htm", model);
					
					
				}
			 					
				return new ModelAndView("redirect:sales.htm");
		}
	
		@RequestMapping(value = "/bullioninvoicePreview.htm", method = RequestMethod.GET)
		public ModelAndView showinvoice() {
			return new ModelAndView("bullionSalePreview");
		}
		
		@RequestMapping(value = "/formbullionsales", method = RequestMethod.POST, params = "update")
		public ModelAndView updateBullionSales(@ModelAttribute("sales") Sales sales,BindingResult result,ModelMap model) {

			//BigDecimal closingAmount = sales.getBillAmount();
			//BigDecimal receivedAmount = sales.getAmtRecd();
		 	String partyName = sales.getCustomerName();
		 	//Date sales_date = sales.getSalesDate();
	/* DB  bullionsales value       **/
			Integer id=sales.getSalesId();			
			Sales bullionObj = salesDao.getSales(id);
			BigDecimal grossWt = bullionObj.getGrossWeight();
			String oldCustomer=bullionObj.getCustomerName();
			String newCustomer=sales.getCustomerName();
			
			String orderid = bullionObj.getReceiptID();//added for retrving the journal id in bullion sales.
			String oldname = bullionObj.getCustomerName();//added for retrving the journal id in bullion sales.
			  bullionSalesValidator.validate(sales, result);
			  bullionSalesValidator.Updatevalidate(bullionObj,sales ,result);
			  
			  if(result.hasErrors()) 
				{							
				   model.addAttribute("name", ledgerDao.listLedgerName());	
				   model.addAttribute("journalListReceiptID", journalDao.ViewJournalId(oldname, orderid));
				   model.addAttribute("s_manname", userloginDao.userlist());
				   model.addAttribute("command", sales);
				   model.addAttribute("errorType","updateError");
				   return new ModelAndView("formbullionsales",model);
				}
		
		
		   /* form bullionsales value **/
		BigDecimal form_grosswt=sales.getGrossWeight();
			
		    /*** Receipt Id update*****/
		       String journal_NO = sales.getReceiptID();
	           journalDao.UpdateStatusID(journal_NO); 
		
	           /* Recipt Id Retrving from sold to stock */
	           String newReceiptId=sales.getReceiptID();
	           String Old_JournalReceiptId=bullionObj.getReceiptID();
	           
	           if(Old_JournalReceiptId != null){
				if(!Old_JournalReceiptId.equals(newReceiptId)){
					journalDao.UpdateStatusToAccept(Old_JournalReceiptId);
				}
	           }
	           
		    /**Differnece of stock*/
	       
			BigDecimal comp_grosswt=grossWt.subtract(form_grosswt);
			
			/**    Difference code end   */
			
			
		String itemcd = sales.getItemCode();
		List<ItemMaster> itemList = itemmasterDao.searchItemMaster(itemcd);
		itemList.get(0).getGrossWeight();
		BigDecimal final_grosswt=itemList.get(0).getGrossWeight().add(comp_grosswt);
		itemList.get(0).setGrossWeight(final_grosswt);
		itemList.get(0).setNetWeight(final_grosswt);
		itemList.get(0).setTotalGrossWeight(final_grosswt);
		itemmasterDao.updateItemMaster(itemList.get(0)); 
		
		
		//Update Jewel Stock Table on updating the bullion sales
		jewelStockObj = new JewelStock();
		
		List<JewelStock> jewelStockList = jewelStockDao.searchByTransNO_TransType(sales.getSalesTypeId(), sales.getFormType());	
		jewelStockList.get(0).setStock_CategoryName(sales.getItemName());
		jewelStockList.get(0).setStock_SubCategoryName(sales.getItemName());
		jewelStockList.get(0).setStock_ItemName(sales.getItemName());		
		jewelStockList.get(0).setStock_ItemCode(sales.getItemCode());
		jewelStockList.get(0).setStock_MetalType(sales.getBullionType());
		jewelStockList.get(0).setStock_MetalUsed(sales.getBullionType());
		jewelStockList.get(0).setStock_CLGrossWeight(CONVERT.multiply(sales.getGrossWeight()));
		jewelStockList.get(0).setStock_CLNetWeight(CONVERT.multiply(sales.getGrossWeight()));
		jewelStockList.get(0).setStock_CLTotalGrossWeight(CONVERT.multiply(sales.getGrossWeight()));		
		jewelStockList.get(0).setStock_BullionRate(sales.getBullionRate());
		jewelStockList.get(0).setStock_TransDate(sales.getSalesDate());
		
		jewelStockDao.updateJewelStock(jewelStockList.get(0));
		
			/* ledger updation for same customer */
		
		if(newCustomer.equals(oldCustomer)){
			
			/* ledger updation for walkin customer*/
			if(sales.getCustomerName().equals("walk_in")){
				
				//****************** if walkin customer ********************/
				/*sales ledger update for walk in customer */
				
				String salesaccount="Sales Account";
				List<Ledger> salesacuntlist = ledgerDao.searchLedger(salesaccount);
				BigDecimal currntSalLedg=salesacuntlist.get(0).getClosingTotalBalance();
				BigDecimal oldSalBil=bullionObj.getBillAmount();
				BigDecimal newSalBil=sales.getBillAmount();
				
				BigDecimal diffSalBil=currntSalLedg.add(oldSalBil);
				BigDecimal finalBil=diffSalBil.subtract(newSalBil);
			
				if(finalBil.signum()==-1){
					closingTyp="Credit";
					finalBil=finalBil.multiply(CONVERT);
				}else{
					closingTyp="Debit";
				}
				
				ledgerDao.updateLedgerPartyBalance(finalBil, closingTyp, salesaccount);				
				
				/* cash book update for walk in customer */
				List <Ledger> ledgerListCashWalkin = ledgerDao.searchLedger("Cash Account");
				BigDecimal oldClBalanceCashLedger = ledgerListCashWalkin.get(0).getClosingTotalBalance();
				String clTypeCashLedger = ledgerListCashWalkin.get(0).getClosingTotalType();
				
				if(clTypeCashLedger.equalsIgnoreCase("Credit")){
					oldClBalanceCashLedger = ZERO.subtract(oldClBalanceCashLedger);						
				}
				BigDecimal oldRecvAmt=bullionObj.getAmtRecd();
				BigDecimal NewreceAmount = sales.getAmtRecd();
				BigDecimal dropClBalanceCashLedger = oldClBalanceCashLedger.subtract(oldRecvAmt);
				BigDecimal finalClBalanceCashLedger = dropClBalanceCashLedger.add(NewreceAmount);
						
				if(finalClBalanceCashLedger.signum() == -1){
					closingType = "Credit";
					finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
				}else{
						closingType = "Debit";						
					}
				ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, closingType, "Cash Account");
			
				//Integer salesId=sales.getSalesId();
				//Sales salesNew=salesDao.getSales(salesId);
				
				//************ Journ Entry Update for Bullion Sales Bill AMount  *********/
				List<Journal> jrnlListSales=journalDao.getJournalUpdateSales("Cash Bullion Sales", "S"+sales.getSalesId().toString());
				
				
	           if(!jrnlListSales.isEmpty()){
	        	  
	        	   jrnlListSales.get(0).setDebitAmount(sales.getBillAmount());
	        	   jrnlListSales.get(0).setCreditAmount(sales.getBillAmount());
	        	   jrnlListSales.get(0).setJournalDate(sales.getSalesDate());
	        	   journalDao.updateJournal(jrnlListSales.get(0));	        	   
	           }
	           
			}//walk in customer condition ends here 
			
			else {// regular customer 
			
			    /****** party ledger update  for same customer *****************************/
			partyName = sales.getCustomerName();
			List<Ledger> customerList = ledgerDao.searchLedger(partyName);
			clType = customerList.get(0).getClosingTotalType();	
			clBal = customerList.get(0).getClosingTotalBalance();
			
			if(clType.equalsIgnoreCase("Credit")){			
				clBal=ZERO.subtract(clBal);			
			}			
				
			BigDecimal newclBal=sales.getBillAmount();
			BigDecimal oldclBal=bullionObj.getBillAmount();
			
			BigDecimal difBal=clBal.subtract(oldclBal);
		    BigDecimal finalBal=difBal.add(newclBal);
			
		    if(finalBal.signum()==-1){
				closingTyp = "Credit";
				finalBal=finalBal.multiply(CONVERT);
			}else{
				closingTyp="Debit";
			}
				
		    ledgerDao.updateLedgerPartyBalance(finalBal, closingTyp, partyName);
		    
			 /* Sales Ledger update for same customer */
			 
		    String salesaccount="Sales Account";
			List<Ledger> salesacuntlist = ledgerDao.searchLedger(salesaccount);
			String ClTYPE = salesacuntlist.get(0).getClosingTotalType();	
			BigDecimal currntSalLedg=salesacuntlist.get(0).getClosingTotalBalance();
			
            if(ClTYPE.equalsIgnoreCase("Credit")){				
            	currntSalLedg=ZERO.subtract(currntSalLedg);			
			}
            
			BigDecimal oldSalBil=bullionObj.getBillAmount();
			BigDecimal newSalBil=sales.getBillAmount();
			
			BigDecimal diffSalBil=currntSalLedg.add(oldSalBil);
			BigDecimal finalBil=diffSalBil.subtract(newSalBil);
		
			if(finalBil.signum()==-1){
				closingTyp="Credit";
				finalBil=finalBil.multiply(CONVERT);
			}else{
				closingTyp="Debit";
			}
			
			ledgerDao.updateLedgerPartyBalance(finalBil, closingTyp, salesaccount);
			
			Integer salesId=sales.getSalesId();
			Sales salesNew=salesDao.getSales(salesId);
			
			//************ Journ Entry Update for Bullion Sales Bill AMount  *********/
			List<Journal> jrnlListSales=journalDao.getJournalUpdateSales("Bullion Sales", "S"+sales.getSalesId().toString());
           if(!jrnlListSales.isEmpty()){
        	   
        	   jrnlListSales.get(0).setDebitAmount(salesNew.getBillAmount());
        	   jrnlListSales.get(0).setCreditAmount(salesNew.getBillAmount());
        	   jrnlListSales.get(0).setJournalDate(sales.getSalesDate());
        	   journalDao.updateJournal(jrnlListSales.get(0));        	   
           }
			
                  /* for receipt amount update */
				 /** Party ledger update for same customer in recevied amount*/
				
				partyName=sales.getCustomerName();
				List<Ledger> partyupdte = ledgerDao.searchLedger(partyName);
				String	clREvdType = customerList.get(0).getClosingTotalType();	
				BigDecimal curntpartybal=partyupdte.get(0).getClosingTotalBalance();
				if(clREvdType.equalsIgnoreCase("Credit")){
					
					curntpartybal=ZERO.subtract(curntpartybal);
					
				}
				BigDecimal oldCashbal=bullionObj.getAmtRecd();
				BigDecimal addCunt=curntpartybal.add(oldCashbal);
			
				BigDecimal Newcashamt = sales.getAmtRecd();
				BigDecimal finalPartybal=addCunt.subtract(Newcashamt);
			
				if(finalPartybal.signum()==-1){
					closingTyp="Credit";
					finalPartybal=finalPartybal.multiply(CONVERT);
				}else{
					closingTyp="Debit";
				}
				
				ledgerDao.updateLedgerPartyBalance(finalPartybal, closingTyp, partyName);
			 
			/**  cash book ledger   */
				
				BigDecimal NewreceivedAmount = sales.getAmtRecd();	
				if(NewreceivedAmount.signum() != 0)
				 {
					String cashbalance="Cash Account";
					List<Ledger> updte = ledgerDao.searchLedger(cashbalance);
					BigDecimal curntParycahbal=updte.get(0).getClosingTotalBalance();
					BigDecimal oldcashbal=bullionObj.getAmtRecd();
				
					BigDecimal addcurntbal=curntParycahbal.subtract(oldcashbal);
					BigDecimal finalCashbal=addcurntbal.add(NewreceivedAmount);
					
					if(finalCashbal.signum()==-1){
						closingTyp="Credit";
						finalCashbal=finalCashbal.multiply(CONVERT);
					}else{
						closingTyp="Debit";
					}
					
	              ledgerDao.updateLedgerPartyBalance(finalCashbal, closingTyp, cashbalance);
	              
				 }
				
				//Integer salesIds=sales.getSalesId();
				//Sales salesNews = salesDao.getSales(salesIds);
			
	           List<Journal> jrnlListSalesRec=journalDao.getJournalUpdateSales("Bullion Sales Receipt", "S"+sales.getSalesId().toString());
	           if(!jrnlListSalesRec.isEmpty()){
	    
	        	   jrnlListSalesRec.get(0).setDebitAmount(sales.getAmtRecd());
	        	   jrnlListSalesRec.get(0).setCreditAmount(sales.getAmtRecd());
	        	   jrnlListSalesRec.get(0).setJournalDate(sales.getSalesDate());
	        	   journalDao.updateJournal(jrnlListSalesRec.get(0));
	        	   
	           }/*else{
	        	   
	        	   jrnl = new Journal();	
					jrnl.setJournalNO(getJournalNumber(jrnl));
					jrnl.setTransactionId("S"+sales.getSalesId());
					jrnl.setJournalType("Receipt");
				    jrnl.setJournalDate(sales_date);
					jrnl.setDebitAccountName("Cash Account");
					jrnl.setCreditAccountName(partyName);
					jrnl.setDebitAmount(receivedAmount);
					jrnl.setCreditAmount(receivedAmount);
					jrnl.setNarration("Bill No "+sales.getSalesTypeId());							
					journalDao.insertJournal((Journal) jrnl);	
	        	   
	           }*/
	           
			}//regular customer else conditon ends here
	 	
		}//if condtion of regular customer ends here 
		
		
		//if condition of different customer starts here 
		else if(!oldCustomer.equals(newCustomer)){

			 /*** Party Balnace if different customer **/
			
			BigDecimal oldSalBil=bullionObj.getBillAmount();			
			BigDecimal newSalBil=sales.getBillAmount();
			/** From Party 1 */
			
			List <Ledger> ledgerListParty1 = ledgerDao.searchLedger(oldCustomer);
			String clTypeParty1 = ledgerListParty1.get(0).getClosingTotalType();
			BigDecimal oldClBalanceParty1 = ledgerListParty1.get(0).getClosingTotalBalance();
			
			String finalClTypeParty1 = "";
			if(clTypeParty1.equalsIgnoreCase("Credit")){
				oldClBalanceParty1 = ZERO.subtract(oldClBalanceParty1);						
			}
			
			BigDecimal finalClBalanceParty1 = oldClBalanceParty1.subtract(oldSalBil);
			
			if(finalClBalanceParty1.signum() == -1){
				finalClTypeParty1 = "Credit";
				finalClBalanceParty1 = finalClBalanceParty1.multiply(CONVERT);
			}else{
				finalClTypeParty1 = "Debit";						
			}
			
			ledgerDao.updateLedgerPartyBalance(finalClBalanceParty1, finalClTypeParty1, oldCustomer);
			
			/** To Party 2 */
			List <Ledger> ledgerListParty2 = ledgerDao.searchLedger(newCustomer);
			String clTypeParty2 = ledgerListParty2.get(0).getClosingTotalType();
			BigDecimal oldClBalanceParty2 = ledgerListParty2.get(0).getClosingTotalBalance();

			String finalClTypeParty2 = "";
			
			if(clTypeParty2.equalsIgnoreCase("Credit")){
				oldClBalanceParty2 = ZERO.subtract(oldClBalanceParty2);						
			}
			BigDecimal finalClBalanceParty2 = oldClBalanceParty2.add(newSalBil);
							
			
			
			if(finalClBalanceParty2.signum() == -1){
				finalClTypeParty2 = "Credit";
				finalClBalanceParty2 = finalClBalanceParty2.multiply(CONVERT);
			}else{
				finalClTypeParty2 = "Debit";						
			}
			
			ledgerDao.updateLedgerPartyBalance(finalClBalanceParty2, finalClTypeParty2, newCustomer);
				
				
				/* Sales Ledger update for different customer */
				 
			    String salesaccount="Sales Account";
				List<Ledger> salesacuntlist = ledgerDao.searchLedger(salesaccount);
				BigDecimal currntSalLedg=salesacuntlist.get(0).getClosingTotalBalance();
				
				
				BigDecimal diffSalBil=currntSalLedg.add(oldSalBil);
				BigDecimal finalBil=diffSalBil.subtract(newSalBil);
			
				if(finalBil.signum()==-1){
					closingTyp="Credit";
					finalBil=finalBil.multiply(CONVERT);
				}else{
					closingTyp="Debit";
				}
				
				ledgerDao.updateLedgerPartyBalance(finalBil, closingTyp, salesaccount);
				
				
			//	Integer salesId=sales.getSalesId();
				//Sales salesNew=salesDao.getSales(salesId);
				//************ Journ Entry Update for Bullion Sales Bill AMount in different customer   *********/
				List<Journal> jrnlListSales=journalDao.getJournalUpdateSales("Bullion Sales", "S"+sales.getSalesId().toString());
	           if(!jrnlListSales.isEmpty()){
	        	  
	        	   jrnlListSales.get(0).setDebitAmount(sales.getBillAmount());
	        	   jrnlListSales.get(0).setCreditAmount(sales.getBillAmount());
	        	   jrnlListSales.get(0).setDebitAccountName(sales.getCustomerName());
	        	   jrnlListSales.get(0).setJournalDate(sales.getSalesDate());
	        	   journalDao.updateJournal(jrnlListSales.get(0));
	        	   
	           }
	           
	           /*Recepit mode amount updation for different customer */
	           
				
	           /*** Party Balnace if different customer **/
	           /** From Party 1 */
	           BigDecimal NewreceivedAmount = sales.getAmtRecd();	
				BigDecimal oldcashbal=bullionObj.getAmtRecd();
				
				List <Ledger> ledgerListrecParty1 = ledgerDao.searchLedger(oldCustomer);
				String clTyperecParty1 = ledgerListrecParty1.get(0).getClosingTotalType();
				BigDecimal oldClBalancerecParty1 = ledgerListrecParty1.get(0).getClosingTotalBalance();
				
				String finalClTyperecParty1 = "";
				
				if(clTyperecParty1.equalsIgnoreCase("Credit")){
					oldClBalancerecParty1 = ZERO.subtract(oldClBalancerecParty1);						
				}
				
				BigDecimal finalClBalancerecParty1 = oldClBalancerecParty1.add(oldcashbal);
				
				if(finalClBalancerecParty1.signum() == -1){
					finalClTyperecParty1 = "Credit";
					finalClBalancerecParty1 = finalClBalancerecParty1.multiply(CONVERT);
				}else{
					finalClTyperecParty1 = "Debit";						
				}
				
				ledgerDao.updateLedgerPartyBalance(finalClBalancerecParty1, finalClTyperecParty1, oldCustomer);
				String finalclTyperecParty2 = "";
				
				/** To Party 2 */
				List <Ledger> ledgerListrecParty2 = ledgerDao.searchLedger(newCustomer);
				String clTyperecParty2 = ledgerListrecParty2.get(0).getClosingTotalType();
				BigDecimal oldClBalancerecParty2 = ledgerListrecParty2.get(0).getClosingTotalBalance();

				
				if(clTyperecParty2.equalsIgnoreCase("Credit")){
					oldClBalancerecParty2 = ZERO.subtract(oldClBalancerecParty2);						
				}
				
				BigDecimal finalClBalancerecParty2 = oldClBalancerecParty2.subtract(NewreceivedAmount);
				
				if(finalClBalancerecParty2.signum() == -1){
					finalclTyperecParty2 = "Credit";
					finalClBalancerecParty2 = finalClBalancerecParty2.multiply(CONVERT);
				}else{
					finalclTyperecParty2 = "Debit";						
				}
				
				ledgerDao.updateLedgerPartyBalance(finalClBalancerecParty2, finalclTyperecParty2, newCustomer);
					
					
					/* cash book ledger update for receipt mode in different customer */
					
					if(NewreceivedAmount.signum() != 0)
					 {
						String cashbalance="Cash Account";
						List<Ledger> updte = ledgerDao.searchLedger(cashbalance);
						BigDecimal curntParycahbal=updte.get(0).getClosingTotalBalance();
						
					
						BigDecimal addcurntbal=curntParycahbal.subtract(oldcashbal);
						BigDecimal finalCashbal=addcurntbal.add(NewreceivedAmount);
						
						if(finalCashbal.signum()==-1){
							closingTyp="Credit";
							finalCashbal=finalCashbal.multiply(CONVERT);
						}else{
							closingTyp="Debit";
						}
						
		              ledgerDao.updateLedgerPartyBalance(finalCashbal, closingTyp, cashbalance);
					 }
					
					//Integer salesIds=sales.getSalesId();
					//Sales salesnew=salesDao.getSales(salesIds);
					
		           List<Journal> jrnlListSalesRec=journalDao.getJournalUpdateSales("Bullion Sales Receipt", "S"+sales.getSalesId().toString());
		           if(!jrnlListSalesRec.isEmpty()){
		    
		        	   jrnlListSalesRec.get(0).setDebitAmount(sales.getAmtRecd());
		        	   jrnlListSalesRec.get(0).setCreditAmount(sales.getAmtRecd());
		        	   jrnlListSalesRec.get(0).setCreditAccountName(sales.getCustomerName());
		        	   jrnlListSalesRec.get(0).setJournalDate(sales.getSalesDate());
		        	   journalDao.updateJournal(jrnlListSalesRec.get(0));
		        	   
		           }		           
			
		}//differend customer if conditon ends here	
		
		/** For Auto generate Id if BullionType Changes **/
		if(!bullionObj.getItemCode().equalsIgnoreCase(sales.getItemCode())){
			sales.setSalesTypeId(getBullionSalesTypeId(sales));
		}
         
		salesDao.updateSales(sales);
		return new ModelAndView("redirect:sales.htm");
		}
		  
		 
		//bind the names in bullionsales form	
		@RequestMapping(value="/formbullionsales", method=RequestMethod.GET)	
		public ModelAndView bullionSalesList(@ModelAttribute("sales") Sales sales,ModelMap model){
				//	Integer i=jrnl.getJournalId();
								
					String customername=sales.getCustomerName();
					model.put("name",ledgerDao.listLedgerName());		     
					model.put("s_manname", userloginDao.userlist());					
					model.addAttribute("journalListReceiptID", journalDao.ViewJournalId(customername, sales.getReceiptID()));								
					model.put("command", sales);					
					return  new ModelAndView("formbullionsales",model);
		} 
		
		//Redirect the form for update
		@RequestMapping(value = "/viewbullionsales.htm", method = RequestMethod.GET)
		public ModelAndView showForm(@ModelAttribute("sales") Sales sales, @RequestParam(value="salesId", required=false) Integer salesId,Model model) 
		{
			/** add for retreving id in view mode 
			Sales salesoldId = salesDao.getSales(salesId);
			System.out.println("ID:::::"+salesoldId);
			Integer orderId = salesoldId.getReceiptID();
			String oldName = salesoldId.getCustomerName();
			List<Journal> journalRetriveId=journalDao.ViewJournalId(oldName, orderId);
			model.addAttribute("journalListReceiptID", journalRetriveId);//add for retreving id
			System.out.println("oldName"+oldName);
			
			/*** ends here ****/
			model.addAttribute("sales", salesDao.getSales(salesId));					
			model.addAttribute("name",ledgerDao.listLedgerName());		    
			model.addAttribute("s_manname", userloginDao.userlist());	
			return new ModelAndView ("formbullionsales");
		}
		
		
		//Canceling request mapping				
		@RequestMapping(value="/formbullionsales.htm",method=RequestMethod.POST, params="cancel") 
		public String cancelForm()
		{
			return "redirect:sales.htm";
		}
		private String getBullionSalesTypeId(Sales sales){
			
			String bill_NO = ""; 
			String displayCode;
			String listBillNo;
			BigInteger found = null;
			
			if(sales.getItemCode().equalsIgnoreCase("IT100002")){
				found = salesDao.getLastBullionGoldTypeId();
				listBillNo = "GB"+found;
				bill_NO = "GB0";
			}else{
				found = salesDao.getLastBullionSilverTypeId();
				listBillNo = "SB"+found;
				bill_NO = "SB0";
			}
			
			if(found != null)
			{
				bill_NO = listBillNo;
			}
			
			String splitNo = bill_NO.substring(2);
		    int num = Integer.parseInt(splitNo);   
		    num++;        
		    String number = Integer.toString(num);        
		    displayCode = bill_NO.substring(0, 2) + number;
			return displayCode;		
		}
}

