package com.jewellery.web;

import java.math.BigDecimal;
import java.text.DateFormat;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import com.jewellery.dao.LedgerDao;
import com.jewellery.entity.ItemMaster;
import com.jewellery.dao.CompanyInfoDao;
import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.JewelStockDao;
import com.jewellery.dao.PurchaseDao;
import com.jewellery.validator.PurchaseValidator;
import com.jewellery.entity.CompanyInfo;
import com.jewellery.entity.JewelStock;
import com.jewellery.entity.Purchase;
import com.jewellery.entity.Journal;
import com.jewellery.dao.JournalDao;
import com.jewellery.entity.Ledger;
import com.jewellery.print.InvoiceBillFormat;
import com.jewellery.printservice.InvoiceTextFormatPrint;
import com.jewellery.util.JournalCode;


@Controller
public class FormPurchaseController extends JournalCode
{	
	private PurchaseDao purchaseDao;
	private LedgerDao ledgerDao;	
	private ItemMasterDao itemmasterDao;
	private JournalDao journalDao;
	private Journal jrnl;
	private CompanyInfoDao companyInfoDao;
	private JewelStockDao jewelStockDao;
	private JewelStock jewelStockObj;
	
	BigDecimal clBal;
	BigDecimal itemgrosswt;
	BigDecimal itemnetwt;
	BigDecimal closingAmount;
	BigDecimal grosswt;
	BigDecimal netwt;
	BigDecimal totalamt;	
	BigDecimal jewelStockGrossWt;
	BigDecimal jewelStockNetWt;
		
	
	Integer jewelStockQty;
	Integer noqty;
	Integer ppq;	
	Integer nos;
	
	String partyName;
	String ledgername;
	String clType;		 	
	String closingType;
	String closingTyp;	
	String ptype;
	String itemcd;
	String jewelTransType;
	
	BigDecimal ZERO=new BigDecimal("0");
	BigDecimal CONVERT=new BigDecimal("-1");
	
	List<String> ledgerGroupCode;
	ItemMaster itemDetails = new ItemMaster();	
	
	@Autowired
	private PurchaseValidator purchaseValidator; 
	
	@Autowired
	public FormPurchaseController(PurchaseDao purchaseDao, LedgerDao ledgerDao, ItemMasterDao itemmasterDao, JournalDao journalDao, CompanyInfoDao companyInfoDao, JewelStockDao jewelStockDao)
	{
		this.purchaseDao = purchaseDao;
		this.ledgerDao = ledgerDao;		
		this.itemmasterDao = itemmasterDao;
		this.journalDao = journalDao;
		this.companyInfoDao = companyInfoDao;
		this.jewelStockDao = jewelStockDao;
	}
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
	throws Exception{
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class,null, dateEditor);
	}
	
	
//listing Purchase Detail		
	@RequestMapping(value="/purchase")
	public String listPurchase(Map<String, Object> map){	
		
		List<Purchase> purchaseList = purchaseDao.listPurchase();
		((ModelMap) map).addAttribute("purchaseList", purchaseList);
		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
		  //get current date time with Calendar()
		   Calendar cal = Calendar.getInstance();
		   dateFormat.format(cal.getTime());
		return "purchase";
	}	
	
//Bind the names in Purchase form
	@RequestMapping(value="/formpurchase.htm", method=RequestMethod.GET)
	public ModelAndView purchaseList(@ModelAttribute("purchase") Purchase purchase){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("suppliername",ledgerDao.listLedgerName());	
		model.put("command", purchase);
		return new ModelAndView("formpurchase",model);
	}	
	
//To Add Or Save the record	
	@RequestMapping(value = "/formpurchase.htm", method = RequestMethod.POST, params="insert")
	public ModelAndView addData(@ModelAttribute("purchase") Purchase purchase, BindingResult result,SessionStatus status,HttpServletRequest request ,Model model) 
	{
		purchaseValidator.validate(purchase, result);
		
		model.addAttribute("suppliername", ledgerDao.listLedgerName());
		
		closingType = "Debit";
		closingTyp = "Credit";	
		itemcd = purchase.getItemCode();
		partyName = purchase.getSupplierName();
	    closingAmount = purchase.getTotalAmount();	
	    ptype = purchase.getPurchaseType();
	    grosswt = purchase.getGrossWeight();
	    netwt = purchase.getNetWeight();
	    nos = purchase.getNumberOfPieces();	    
	    String payment_mode = purchase.getPaymentMode();	    
		
		if(result.hasErrors()) 
		{
			ModelMap map = new ModelMap();
			map.addAttribute("errorType","insertError");
			map.put("command", purchase);
			return new ModelAndView("formpurchase", map);
		}
		
		// Insert Record in Purchase
		String INVOICE_NO = generatePurchaseInvoiceNO(purchase);
		purchase.setPurchseInvoice(INVOICE_NO);
		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
		  //get current date time with Calendar()
		   Calendar cal = Calendar.getInstance();
		   dateFormat.format(cal.getTime());
		   purchase.setCurrentTime(dateFormat.format(cal.getTime()));
		purchaseDao.insertPurchase(purchase);
				
		
		status.setComplete();			
		
		//To update Stock 
		
		
		
		List<ItemMaster> itemList = itemmasterDao.searchItemMaster(itemcd);
		
		for (int i = 0; i < itemList.size(); i++) {
			ItemMaster imast = (ItemMaster)itemList.get(i);
			if (imast instanceof ItemMaster) { 
				
				itemDetails = (ItemMaster) imast;
				itemgrosswt = itemDetails.getGrossWeight();
				itemnetwt = itemDetails.getNetWeight();
				noqty = itemDetails.getQty();
			    ppq = itemDetails.getPiecesPerQty();
		   
			    if (ptype.compareTo("Purchase") == 0)
			    {
			        noqty = noqty + nos;			   
					itemgrosswt = itemgrosswt.add(grosswt);
					itemnetwt = itemnetwt.add(grosswt);
					
					
					//Update Jewel Stock
					
					jewelTransType = "Purchase";
					jewelStockQty = nos;
					jewelStockGrossWt = grosswt;
					jewelStockNetWt = netwt;
					
					
			    }
			    else
			    {
			    	noqty = noqty - nos;			   
					itemgrosswt = itemgrosswt.subtract(grosswt);
					itemnetwt = itemnetwt.subtract(grosswt);
					
					
					//Update Jewel Stock
					jewelTransType = "Purchase Return";
					jewelStockQty = -nos;
					jewelStockGrossWt =  CONVERT.multiply(grosswt);
					jewelStockNetWt = CONVERT.multiply(netwt);
			    }		    
				
				//tp = ppq * noqty;
				itemDetails.setGrossWeight(itemgrosswt);
				itemDetails.setNetWeight(itemnetwt);
				itemDetails.setQty(noqty);
				itemDetails.setPiecesPerQty(ppq);
				itemDetails.setTotalGrossWeight(itemgrosswt);			
				itemmasterDao.updateItemMaster(itemDetails);//Update Item Master
				
				//Update Stock Entry In Jewel Stock
				
				jewelStockObj = new JewelStock();
				
				jewelStockObj.setStock_TransType(jewelTransType );
				jewelStockObj.setStock_StockType("Purchase Stock");
				jewelStockObj.setStock_TransNO(purchase.getPurchseInvoice());
				jewelStockObj.setStock_TransDate(purchase.getPurchaseDate());
				jewelStockObj.setStock_ItemCode(purchase.getItemCode());
				jewelStockObj.setStock_CategoryName(purchase.getItemName());
				jewelStockObj.setStock_SubCategoryName(purchase.getItemName());
				jewelStockObj.setStock_ItemName(purchase.getItemName());
				jewelStockObj.setStock_MetalType(purchase.getBullionType());
				jewelStockObj.setStock_MetalUsed(purchase.getBullionType());
				jewelStockObj.setStock_CLQty(jewelStockQty);
				jewelStockObj.setStock_CLTotalPieces(jewelStockQty);
				jewelStockObj.setStock_CLGrossWeight(jewelStockGrossWt);
				jewelStockObj.setStock_CLNetWeight(jewelStockNetWt);
				jewelStockObj.setStock_CLTotalGrossWeight(jewelStockGrossWt);
				jewelStockObj.setStock_BullionRate(purchase.getRate());
				jewelStockDao.saveJewelStock(jewelStockObj);
			}				
		}	
		
		if(ptype.equals("Purchase"))
		{			
			if (partyName.equals("Walk-in"))
			{
				/*************************Auto Journal entry for purchase*************************************/
				jrnl = new Journal();			
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Cash Purchase");
			    jrnl.setJournalDate(purchase.getPurchaseDate());
			    
			 // Set Account group code as Debit code
			    ledgerGroupCode = ledgerDao.getLedgerAccountCode("Purchase Account");				
			    jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
				jrnl.setDebitAccountName("Purchase Account");
								
				jrnl.setTransactionId("P"+purchase.getPurchaseId().toString());
				
				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName("Cash Account");
				
				jrnl.setDebitAmount(closingAmount);
				jrnl.setCreditAmount(closingAmount);
				jrnl.setNarration("Bill No " + purchase.getPurchseInvoice());				
				journalDao.insertJournal((Journal) jrnl);				
				
				/************************* Update Purchase Account *************************************/
				ledgername = "Purchase Account";
				List <Ledger> purchaseList = ledgerDao.searchLedger(ledgername);
				
				clBal = purchaseList.get(0).getClosingTotalBalance();	
				clType = purchaseList.get(0).getClosingTotalType();					 				 
			 	
			 	if(clType.equals("Debit")){	
			 		ledgerDao.updatePartyBalance(closingAmount, closingType, ledgername);			 		
			 	}
			 	else{
			 		if(clBal.compareTo(closingAmount) == 1){			 			
			 			ledgerDao.updateCreditPartyBalance(closingAmount, closingTyp, ledgername);
			 		}
			 		else{			 			
			 			ledgerDao.updateCrPartyBalance(closingAmount, closingType, ledgername);
			 		}	
			 	}			
				
				/************************* update cash balance *************************************/
				ledgername="Cash Account";
				List <Ledger> ledgerList = ledgerDao.searchLedger(ledgername);
				
				clBal = ledgerList.get(0).getClosingTotalBalance();	
				clType = ledgerList.get(0).getClosingTotalType();					 				 
			 	
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
			}
			else{//For Regular Customer
				
					if(payment_mode.equals("Cash"))
					{						
						/************************* Auto Journal Entry for Regular customer *************************************/	
						jrnl = new Journal();			
						jrnl.setJournalNO(getJournalNumber(jrnl));
						jrnl.setJournalType("Purchase");
					    jrnl.setJournalDate(purchase.getPurchaseDate());
					    
					 // Set Account group code as Debit code
					    ledgerGroupCode = ledgerDao.getLedgerAccountCode("Purchase Account");				
					    jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
						jrnl.setDebitAccountName("Purchase Account");
						
						
						jrnl.setTransactionId("P"+purchase.getPurchaseId().toString());
						
						// Set Account group code as Credit code
						ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
						jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
						jrnl.setCreditAccountName(partyName);
						
						jrnl.setDebitAmount(closingAmount);
						jrnl.setCreditAmount(closingAmount);
						jrnl.setNarration("Bill No " + purchase.getPurchseInvoice());				
						journalDao.insertJournal((Journal) jrnl);	
			
						/************************ Auto Journal Entry for Payment *************************/			
						jrnl = new Journal();			
						jrnl.setJournalNO(getJournalNumber(jrnl));
						jrnl.setJournalType("Purchase Payment");
					    jrnl.setJournalDate(purchase.getPurchaseDate());
						jrnl.setTransactionId("P"+purchase.getPurchaseId().toString());
						
						// Set Account group code as Debit code
						ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);				
						jrnl.setDebitCode(ledgerGroupCode.get(0).toString());						
						jrnl.setDebitAccountName(partyName);
						
						// Set Account group code as Credit code
						ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");
						jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
						jrnl.setCreditAccountName("Cash Account");
						
						jrnl.setDebitAmount(closingAmount);
						jrnl.setCreditAmount(closingAmount);
						jrnl.setNarration("Bill No " + purchase.getPurchseInvoice());				
						journalDao.insertJournal((Journal) jrnl);							
						
						//update cash balance
						ledgername="Cash Account";
						List <Ledger> ledgerList = ledgerDao.searchLedger(ledgername);
						
						clBal = ledgerList.get(0).getClosingTotalBalance();	
						clType = ledgerList.get(0).getClosingTotalType();
																 	
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
					 	
					 	//Update Purchase Account
						ledgername = "Purchase Account";
						List <Ledger> purchaseList = ledgerDao.searchLedger(ledgername);
						
						clBal = purchaseList.get(0).getClosingTotalBalance();	
						clType = purchaseList.get(0).getClosingTotalType();					 				 
					 	
					 	if(clType.equals("Debit")){						 		
					 		ledgerDao.updatePartyBalance(closingAmount, closingType, ledgername);			 		
					 	}
					 	else{
					 		if(clBal.compareTo(closingAmount) == 1){					 		
					 			ledgerDao.updateCreditPartyBalance(closingAmount, closingTyp, ledgername);
					 		}
					 		else{					 		
					 			ledgerDao.updateCrPartyBalance(closingAmount, closingType, ledgername);
					 		}	
					 	}												
					}
					else{ //For Payment mode is Credit
						
						/*************************** Auto Journal Entry for Regular customer **************************/				
						jrnl = new Journal();			
						jrnl.setJournalNO(getJournalNumber(jrnl));
						jrnl.setJournalType("Purchase");
					    jrnl.setJournalDate(purchase.getPurchaseDate());
					    
					 // Set Account group code as Debit code
						ledgerGroupCode = ledgerDao.getLedgerAccountCode("Purchase Account");				
						jrnl.setDebitCode(ledgerGroupCode.get(0).toString());		
						jrnl.setDebitAccountName("Purchase Account");
						
						jrnl.setTransactionId("P"+purchase.getPurchaseId().toString());
						
						// Set Account group code as Credit code
						ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
						jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
						jrnl.setCreditAccountName(partyName);
						
						jrnl.setDebitAmount(closingAmount);
						jrnl.setCreditAmount(closingAmount);
						jrnl.setNarration("Bill No " + purchase.getPurchseInvoice());				
						journalDao.insertJournal((Journal) jrnl);
						
						//Update Purchase Account
						
						ledgername = "Purchase Account";
						List <Ledger> purchaseList = ledgerDao.searchLedger(ledgername);
						
						clBal = purchaseList.get(0).getClosingTotalBalance();	
						clType = purchaseList.get(0).getClosingTotalType();					 				 
					 	
					 	if(clType.equals("Debit")){						 		
					 		ledgerDao.updatePartyBalance(closingAmount, closingType, ledgername);			 		
					 	}
					 	else{
					 		if(clBal.compareTo(closingAmount) == 1){					 				
					 			ledgerDao.updateCreditPartyBalance(closingAmount, closingTyp, ledgername);
					 		}
					 		else{					 			
					 			ledgerDao.updateCrPartyBalance(closingAmount, closingType, ledgername);
					 		}	
					 	}
					 	
					 	//update party balance
						List <Ledger> customer_List = ledgerDao.searchLedger(partyName);		
						clBal = customer_List.get(0).getClosingTotalBalance();	
						clType = customer_List.get(0).getClosingTotalType();					 				 
					 	
					 	if(clType.equals("Credit")){					 		
					 		ledgerDao.updatePartyBalance(closingAmount, closingTyp, partyName);			 		
					 	}
					 	else{
					 		if(clBal.compareTo(closingAmount) == 1){					 				
					 			ledgerDao.updateCreditPartyBalance(closingAmount, closingType, partyName);
					 		}
					 		else{					 			
					 			ledgerDao.updateCrPartyBalance(closingAmount, closingTyp, partyName);
					 		}	
					 	}				 	
					}//End of if for payment mode					
				}// End of if condition for party name			
			} 
	
	// if condition for type equal to purchase 
	if(ptype.equals("Purchase Return")){
			
		if(payment_mode.equals("Cash"))
		{
			/*****************************  Auto Journal entry for purchase return *****************************/ 
			jrnl = new Journal();			
			jrnl.setJournalNO(getJournalNumber(jrnl));
			jrnl.setJournalType("Purchase Return");
		    jrnl.setJournalDate(purchase.getPurchaseDate());
		    
		 // Set Account group code as Debit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);				
			jrnl.setDebitCode(ledgerGroupCode.get(0).toString());		
			jrnl.setDebitAccountName(partyName);
			
			// Set Account group code as Credit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode("Purchase Account");
			jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
			jrnl.setCreditAccountName("Purchase Account");
			
			jrnl.setTransactionId("P"+purchase.getPurchaseId().toString());
			jrnl.setDebitAmount(closingAmount);			
			jrnl.setCreditAmount(closingAmount);
			jrnl.setNarration("Bill No " + purchase.getPurchseInvoice());				
			journalDao.insertJournal((Journal) jrnl);	
		
			/************************* Auto Journal entry for Cash Amount ****************************************/ 
			jrnl = new Journal();			
			jrnl.setJournalNO(getJournalNumber(jrnl));
			jrnl.setJournalType("Purchase Return Receipt");
		    jrnl.setJournalDate(purchase.getPurchaseDate());
		    
		 // Set Account group code as Debit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");				
			jrnl.setDebitCode(ledgerGroupCode.get(0).toString());		
			jrnl.setDebitAccountName("Cash Account");
			  
			jrnl.setTransactionId("P"+purchase.getPurchaseId().toString());			
			
			// Set Account group code as Credit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
			jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
			jrnl.setCreditAccountName(partyName);
			
			jrnl.setDebitAmount(closingAmount);
			jrnl.setCreditAmount(closingAmount);
			jrnl.setNarration("Bill No " + purchase.getPurchseInvoice());				
			journalDao.insertJournal((Journal) jrnl);	
			
			//update Cash Account
			
			ledgername="Cash Account";
			List<Ledger> customerList = ledgerDao.searchLedger("Cash Account");				
			
			clBal = customerList.get(0).getClosingTotalBalance();	
			clType = customerList.get(0).getClosingTotalType();		 			 				 
		 	
			if(clType.equals("Debit")){			 		
		 		ledgerDao.updatePartyBalance(closingAmount, closingType, ledgername);			 		
		 	}
		 	else{
		 		if(clBal.compareTo(closingAmount) == 1){		 				
		 			ledgerDao.updateCreditPartyBalance(closingAmount, closingTyp, ledgername);
		 		}
		 		else{		 			
		 			ledgerDao.updateCrPartyBalance(closingAmount, closingType, ledgername);
		 		}	
		 	}
			
			//Update Purchase Account
			
			ledgername = "Purchase Account";
			List <Ledger> purchaseList = ledgerDao.searchLedger(ledgername);
			
			clBal = purchaseList.get(0).getClosingTotalBalance();	
			clType = purchaseList.get(0).getClosingTotalType();					 				 
		 	
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
		}
		else
		{//If payment mode is Credit
			
			/****************************** Auto Journal Entry for Regular customer for credit mode ************************/				
			jrnl = new Journal();			
			jrnl.setJournalNO(getJournalNumber(jrnl));
			jrnl.setJournalType("Purchase Return");
		    jrnl.setJournalDate(purchase.getPurchaseDate());
		    
		    // Set Account group code as Debit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);				
			jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
			jrnl.setDebitAccountName(partyName);
			
			// Set Account group code as Credit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode("Purchase Account");
			jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
			jrnl.setCreditAccountName("Purchase Account");
			
			jrnl.setTransactionId("P"+purchase.getPurchaseId().toString());
			jrnl.setDebitAmount(closingAmount);
			jrnl.setCreditAmount(closingAmount);
			jrnl.setNarration("Bill No " + purchase.getPurchseInvoice());				
			journalDao.insertJournal((Journal) jrnl);
			
			//update Party Balance	
					
			List <Ledger> customerList = ledgerDao.searchLedger(partyName);
			
			clBal = customerList.get(0).getClosingTotalBalance();	
			clType = customerList.get(0).getClosingTotalType();		 			 				 
		 	
			if(clType.equals("Debit")){		 		
		 		ledgerDao.updatePartyBalance(closingAmount, closingType, partyName);			 		
		 	}
		 	else{
		 		if(clBal.compareTo(closingAmount) == 1){		 				
		 			ledgerDao.updateCreditPartyBalance(closingAmount, closingTyp, partyName);
		 		}
		 		else{		 			
		 			ledgerDao.updateCrPartyBalance(closingAmount, closingType, partyName);
		 		}	
		 	}
			
			//Update Purchase Account	
			
			ledgername = "Purchase Account";
			
			List<Ledger> purchaseList = ledgerDao.searchLedger("Purchase Account");
			
			clBal = purchaseList.get(0).getClosingTotalBalance();	
			clType = purchaseList.get(0).getClosingTotalType();					 				 
		 	
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
		}
		
		}//End of If for purchase return
		
		//model.addAttribute("invc", purchase.getPurchseInvoice());
		List<CompanyInfo> companyList = companyInfoDao.listCompanyInfo();
		InvoiceBillFormat orninoviceObj = new InvoiceTextFormatPrint();
		
		// if Printer Format set in Company Info
		if(!companyList.isEmpty() && (purchase.getItemCode().equalsIgnoreCase("IT100005") || purchase.getItemCode().equalsIgnoreCase("IT100010") || purchase.getBullionType().equalsIgnoreCase("Old Gold Coin") || purchase.getBullionType().equalsIgnoreCase("Old Silver Coin") || purchase.getBullionType().equalsIgnoreCase("Old Gold Pure Bullion") || purchase.getBullionType().equalsIgnoreCase("Old Silver Pure Bullion")))
		{ 
			if(companyList.get(0).getOrnInvoicePrint().equalsIgnoreCase("dotMatrix4")){
				orninoviceObj.printFourInchOldPurchase(purchase, companyInfoDao, ledgerDao, request);
				status.setComplete();
				return new ModelAndView("redirect:purchase.htm");
			}						
		}
	
		return new ModelAndView("redirect:purchase.htm");
	}
	
//to Update the record	
		@RequestMapping(value = "/formpurchase.htm", method = RequestMethod.POST, params="update")
		public ModelAndView updateData(@ModelAttribute("purchase") Purchase purchaseNew, BindingResult result, Model model,HttpServletRequest request)
		{
			BigDecimal ZERO = new BigDecimal("0");
			BigDecimal CONVERT = new BigDecimal("-1");
			String finalClType = "";
			closingType = "Debit";
			closingTyp = "Credit";	
			
			model.addAttribute("suppliername", ledgerDao.listLedgerName());
			purchaseValidator.validate(purchaseNew, result);
			
			Integer purchaseId = purchaseNew.getPurchaseId(); 
			itemcd = purchaseNew.getItemCode();
			partyName = purchaseNew.getSupplierName();
			totalamt = purchaseNew.getTotalAmount();	
		    ptype = purchaseNew.getPurchaseType();
		    grosswt = purchaseNew.getGrossWeight();
		    netwt = purchaseNew.getNetWeight();
		    nos = purchaseNew.getNumberOfPieces();	
		    
		    //Retrieve data based on purchase Id For update
		    
		    Purchase purchaseOld = purchaseDao.getPurchase(purchaseId);
		 	String oldItemcd = purchaseOld.getItemCode();
			int nop = purchaseOld.getNumberOfPieces();
		    BigDecimal GW = purchaseOld.getGrossWeight();
		    BigDecimal NW = purchaseOld.getNetWeight();
			Integer tot_pcs;
			
			List<ItemMaster> itemList = itemmasterDao.searchItemMaster(itemcd);
			
	 		purchaseValidator.validateUpdate(purchaseOld, purchaseNew, result);
	 		
			if(result.hasErrors())
			{			
				ModelMap map = new ModelMap();			
				map.put("command", purchaseNew);	
				map.addAttribute("errorType","updateError");
				return new ModelAndView("formpurchase",map);			
			}		
			
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			if(ptype.equals("Purchase"))
			{	
					//**************************************   Purchase Ledger Update : Total Amount  *********************************************/		
					List <Ledger> ledgerListSales = ledgerDao.searchLedger("Purchase Account");
					BigDecimal oldClBalanceSales = ledgerListSales.get(0).getClosingTotalBalance();
					String clTypeSales = ledgerListSales.get(0).getClosingTotalType();
					
					if(clTypeSales.equalsIgnoreCase("Credit")){
						oldClBalanceSales = ZERO.subtract(oldClBalanceSales);						
					}
				
					BigDecimal dropClBalanceSales = oldClBalanceSales.subtract(purchaseOld.getTotalAmount());
					BigDecimal finalClBalanceSales  = dropClBalanceSales.add(purchaseNew.getTotalAmount());
									
					if (finalClBalanceSales.signum() == -1) {
						finalClType = "Credit";
						finalClBalanceSales = finalClBalanceSales.multiply(CONVERT);
					} else {
						finalClType = "Debit";					
					}
					
					ledgerDao.updateLedgerPartyBalance(finalClBalanceSales, finalClType, "Purchase Account");
			
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
									
					//************************************ Same Supplier : Cash Ledger Update : Total Amount : Payment Mode - Cash *********************************************/
 					if(purchaseOld.getSupplierName().equals(purchaseNew.getSupplierName())) {						
					
					if(purchaseOld.getPaymentMode().equals("Cash") && purchaseNew.getPaymentMode().equals("Cash")) {
						/* Ledger Update */
						
						ledgername = "Cash Account";
						List <Ledger> ledgerListCashLedger = ledgerDao.searchLedger(ledgername);
						
						BigDecimal oldClBalanceCashLedger = ledgerListCashLedger.get(0).getClosingTotalBalance();
						String clTypeCashLedger = ledgerListCashLedger.get(0).getClosingTotalType();
				
						if(clTypeCashLedger.equalsIgnoreCase("Credit")){
							oldClBalanceCashLedger = ZERO.subtract(oldClBalanceCashLedger);							
						}	
				
						BigDecimal dropClBalanceCashLedger = oldClBalanceCashLedger.add(purchaseOld.getTotalAmount());
						BigDecimal finalClBalanceCashLedger = dropClBalanceCashLedger.subtract(purchaseNew.getTotalAmount());
						
						if(finalClBalanceCashLedger.signum() == -1){
							finalClType = "Credit";
							finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
						}else{
							finalClType = "Debit";						
						}
						
						ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, finalClType, ledgername);
						
						/* Journal Update */
						List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("Purchase","P"+purchaseOld.getPurchaseId().toString());						
						
						if(!jrnlListPurchaseCash.isEmpty()){	
							jrnlListPurchaseCash.get(0).setJournalDate(purchaseNew.getPurchaseDate());
							jrnlListPurchaseCash.get(0).setDebitAmount(purchaseNew.getTotalAmount());
							jrnlListPurchaseCash.get(0).setCreditAmount(purchaseNew.getTotalAmount());							
							journalDao.updateJournal(jrnlListPurchaseCash.get(0));
						}
						//walk-in journal entry updation
						List<Journal> jrnlListPurchaseCashWalkin = journalDao.getJournalUpdateSales("Cash Purchase","P"+purchaseOld.getPurchaseId().toString());						
						
						if(!jrnlListPurchaseCashWalkin.isEmpty()){	
							jrnlListPurchaseCashWalkin.get(0).setJournalDate(purchaseNew.getPurchaseDate());
							jrnlListPurchaseCashWalkin.get(0).setDebitAmount(purchaseNew.getTotalAmount());
							jrnlListPurchaseCashWalkin.get(0).setCreditAmount(purchaseNew.getTotalAmount());							
							journalDao.updateJournal(jrnlListPurchaseCashWalkin.get(0));
						}
						List<Journal> jrnlListPurchaseCredit2 = journalDao.getJournalUpdateSales("Purchase Payment", "P"+purchaseOld.getPurchaseId().toString());
						
						if(!jrnlListPurchaseCredit2.isEmpty()){	
							jrnlListPurchaseCredit2.get(0).setJournalDate(purchaseNew.getPurchaseDate());
							jrnlListPurchaseCredit2.get(0).setDebitAmount(purchaseNew.getTotalAmount());
							jrnlListPurchaseCredit2.get(0).setCreditAmount(purchaseNew.getTotalAmount());
							journalDao.updateJournal(jrnlListPurchaseCredit2.get(0));
						} 
			
					} else if(purchaseOld.getPaymentMode().equals("Credit") && purchaseNew.getPaymentMode().equals("Credit")) {
					
						//**************************************** Same Supplier : Update Party Balance : Payment Mode-Credit ***************************************/
						
						/** Party Balance Update  */
						List<Ledger> partyLedger = ledgerDao.searchLedger(purchaseOld.getSupplierName());
						
						String clTypeParty = partyLedger.get(0).getClosingTotalType();
						BigDecimal oldClBalanceParty = partyLedger.get(0).getClosingTotalBalance();
					
						if(clTypeParty.equalsIgnoreCase("Credit")){
							oldClBalanceParty = ZERO.subtract(oldClBalanceParty);	
						}
																	
						BigDecimal dropClBalanceParty = oldClBalanceParty.add(purchaseOld.getTotalAmount());
						BigDecimal finalClBalanceParty  = dropClBalanceParty.subtract(purchaseNew.getTotalAmount());
									
						if(finalClBalanceParty.signum() == -1){
							finalClType = "Credit";
							finalClBalanceParty = finalClBalanceParty.multiply(CONVERT);
						}else{
							finalClType = "Debit";						
						}
					
						ledgerDao.updateLedgerPartyBalance(finalClBalanceParty, finalClType, purchaseNew.getSupplierName());
						
						//*********************************** (Same Supplier) Journal : Total Amount : Payment Mode - Cash ***************************/
					
						List<Journal> jrnlListPurchaseCredit1 = journalDao.getJournalUpdateSales("Purchase", "P"+purchaseOld.getPurchaseId().toString());
				 		
						if(!jrnlListPurchaseCredit1.isEmpty()){		
				 			jrnlListPurchaseCredit1.get(0).setJournalDate(purchaseNew.getPurchaseDate());
							jrnlListPurchaseCredit1.get(0).setDebitAmount(purchaseNew.getTotalAmount());
							jrnlListPurchaseCredit1.get(0).setCreditAmount(purchaseNew.getTotalAmount());
							journalDao.updateJournal(jrnlListPurchaseCredit1.get(0));
						}			    	
					}
					else if(purchaseOld.getPaymentMode().equals("Cash") && purchaseNew.getPaymentMode().equals("Credit"))
					{
					//*********************************** (Same Supplier) Payment Mode : from Cash to Credit ***********************************/
					
						/* Party Balance Update */
						List<Ledger> partyLedger = ledgerDao.searchLedger(purchaseOld.getSupplierName());
						String clTypeParty = partyLedger.get(0).getClosingTotalType();
						BigDecimal oldClBalanceParty = partyLedger.get(0).getClosingTotalBalance();
					
						if(clTypeParty.equalsIgnoreCase("Credit")){
							oldClBalanceParty = ZERO.subtract(oldClBalanceParty);						
						}
						
						BigDecimal ClBalanceParty = oldClBalanceParty.subtract(purchaseNew.getTotalAmount());
															
						if(ClBalanceParty.signum() == -1){
							finalClType = "Credit";
							ClBalanceParty = ClBalanceParty.multiply(CONVERT); 
						}else{
							finalClType = "Debit";						
						}
						
						ledgerDao.updateLedgerPartyBalance(ClBalanceParty, finalClType, purchaseNew.getSupplierName());
						
						/* Cash Ledger Update */
						ledgername = "Cash Account";
						List<Ledger> CashLedger = ledgerDao.searchLedger(ledgername);
						String clTypeCash = CashLedger.get(0).getClosingTotalType();
						BigDecimal oldClBalanceCash = CashLedger.get(0).getClosingTotalBalance();
					
						if(clTypeCash.equalsIgnoreCase("Credit")){
							oldClBalanceCash = ZERO.subtract(oldClBalanceCash);						
						}	
						
						BigDecimal ClBalanceCash = oldClBalanceCash.add(purchaseOld.getTotalAmount());
															
						if(ClBalanceCash.signum() == -1){
							finalClType = "Credit";
							ClBalanceCash = ClBalanceCash.multiply(CONVERT);
						}else{
							finalClType = "Debit";						
						}	
						
						ledgerDao.updateLedgerPartyBalance(ClBalanceCash, finalClType, ledgername);
						
						/* Journal Entry Update */ 
						List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("Purchase", "P"+purchaseOld.getPurchaseId().toString());
						if(!jrnlListPurchaseCash.isEmpty()) {
							jrnlListPurchaseCash.get(0).setJournalDate(purchaseNew.getPurchaseDate());
							jrnlListPurchaseCash.get(0).setDebitAmount(purchaseNew.getTotalAmount());							
							jrnlListPurchaseCash.get(0).setCreditAmount(purchaseNew.getTotalAmount());							
							journalDao.updateJournal(jrnlListPurchaseCash.get(0));
						}
						
						/* Second Journal Should be deleted  */
						List<Journal> jrnlListPurchasePayment = journalDao.getJournalUpdateSales("Purchase Payment", "P"+purchaseOld.getPurchaseId().toString());
						if(!jrnlListPurchasePayment.isEmpty()) {						
							journalDao.deleteJournal(jrnlListPurchasePayment.get(0));
						}
					}
					else if(purchaseOld.getPaymentMode().equals("Credit") && purchaseNew.getPaymentMode().equals("Cash")) 
					{
						/** Party Balance Update */
						List<Ledger> partyLedger = ledgerDao.searchLedger(purchaseOld.getSupplierName());
						
						String clTypeParty = partyLedger.get(0).getClosingTotalType();
						BigDecimal oldClBalanceParty = partyLedger.get(0).getClosingTotalBalance();
					
						if(clTypeParty.equalsIgnoreCase("Credit")){
							oldClBalanceParty = ZERO.subtract(oldClBalanceParty);						
						}	
						
						BigDecimal ClBalanceParty = oldClBalanceParty.add(purchaseOld.getTotalAmount());
															
						if(ClBalanceParty.signum() == -1){
							finalClType = "Credit";
							ClBalanceParty = ClBalanceParty.multiply(CONVERT);
						}else{
							finalClType = "Debit";						
						}
						
						ledgerDao.updateLedgerPartyBalance(ClBalanceParty, finalClType, purchaseNew.getSupplierName());
						
						/** Cash Ledger Update */
						ledgername = "Cash Account";
						List<Ledger> CashLedger = ledgerDao.searchLedger(ledgername);
						String clTypeCash = CashLedger.get(0).getClosingTotalType();
						BigDecimal oldClBalanceCash = CashLedger.get(0).getClosingTotalBalance();
					
						if(clTypeCash.equalsIgnoreCase("Credit")){
							oldClBalanceCash = ZERO.subtract(oldClBalanceCash);						
						}	
						
						BigDecimal ClBalanceCash = oldClBalanceCash.subtract(purchaseNew.getPurchaseAmount());
															
						if(ClBalanceCash.signum() == -1){
							finalClType = "Credit";
							ClBalanceCash = ClBalanceCash.multiply(CONVERT);
						}else{
							finalClType = "Debit";						
						}
						
						ledgerDao.updateLedgerPartyBalance(ClBalanceCash, finalClType, ledgername);
						
						/** Journal Update */
						List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("Purchase", "P" + purchaseOld.getPurchaseId().toString());
						if(!jrnlListPurchaseCash.isEmpty()) {	
							jrnlListPurchaseCash.get(0).setJournalDate(purchaseNew.getPurchaseDate());
							jrnlListPurchaseCash.get(0).setDebitAmount(purchaseNew.getTotalAmount());
							jrnlListPurchaseCash.get(0).setCreditAmount(purchaseNew.getTotalAmount());
							journalDao.updateJournal(jrnlListPurchaseCash.get(0));
						}
						
						jrnl = new Journal();			
						jrnl.setJournalNO(getJournalNumber(jrnl));
						jrnl.setJournalType("Purchase Payment");
					    jrnl.setJournalDate(purchaseNew.getPurchaseDate());
					    
					 // Set Account group code as Debit code
						ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);			
						jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
						jrnl.setDebitAccountName(partyName);						
						
						// Set Account group code as Credit code
						ledgerGroupCode = ledgerDao.getLedgerAccountCode(ledgername);
						jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
						jrnl.setCreditAccountName(ledgername);						
						
						jrnl.setTransactionId("P"+purchaseNew.getPurchaseId().toString());
						jrnl.setDebitAmount(purchaseNew.getTotalAmount());
						jrnl.setCreditAmount(purchaseNew.getTotalAmount());
						jrnl.setNarration("Bill No" + purchaseNew.getPurchseInvoice());				
						journalDao.insertJournal((Journal) jrnl);
					}								
				}
 				
 				/* Different Supplier Names */
				else if(!purchaseOld.getSupplierName().equals(purchaseNew.getSupplierName())) {	
			    	
					if(purchaseOld.getPaymentMode().equals("Credit") && purchaseNew.getPaymentMode().equals("Credit")) {
					/* Party Ledger Update */
					
			    	/** From Party 1 */
					List <Ledger> ledgerListParty1 = ledgerDao.searchLedger(purchaseOld.getSupplierName());
					String clTypeParty1 = ledgerListParty1.get(0).getClosingTotalType();
					BigDecimal oldClBalanceParty1 = ledgerListParty1.get(0).getClosingTotalBalance();
					String finalClTypeParty1 = "";
					String finalClTypeParty2 = "";
					
					/** To Party 2 */
					List <Ledger> ledgerListParty2 = ledgerDao.searchLedger(purchaseNew.getSupplierName());
					String clTypeParty2 = ledgerListParty2.get(0).getClosingTotalType();
					BigDecimal oldClBalanceParty2 = ledgerListParty2.get(0).getClosingTotalBalance();

					if(clTypeParty1.equalsIgnoreCase("Credit")) {
						oldClBalanceParty1 = ZERO.subtract(oldClBalanceParty1);						
					}					
					if(clTypeParty2.equalsIgnoreCase("Credit")) {
						oldClBalanceParty2 = ZERO.subtract(oldClBalanceParty2);						
					}
					
					BigDecimal finalClBalanceParty1 = oldClBalanceParty1.add(purchaseOld.getTotalAmount());
					BigDecimal finalClBalanceParty2  = oldClBalanceParty2.subtract(purchaseNew.getTotalAmount());
									
					if(finalClBalanceParty1.signum() == -1){
						finalClTypeParty1 = "Credit";
						finalClBalanceParty1 = finalClBalanceParty1.multiply(CONVERT);
					}else{
						finalClTypeParty1 = "Debit";						
					}
					
					if(finalClBalanceParty2.signum() == -1){
						finalClTypeParty2 = "Credit";
						finalClBalanceParty2 = finalClBalanceParty2.multiply(CONVERT);
					}else{
						finalClTypeParty2 = "Debit";						
					}
					
					ledgerDao.updateLedgerPartyBalance(finalClBalanceParty1, finalClTypeParty1, purchaseOld.getSupplierName());
					ledgerDao.updateLedgerPartyBalance(finalClBalanceParty2, finalClTypeParty2, purchaseNew.getSupplierName());
										
					/* Journal Entry Update */	
					List<Journal> jrnlListPurchaseCredit = journalDao.getJournalUpdateSales("Purchase", "P"+purchaseOld.getPurchaseId().toString());
					if(!jrnlListPurchaseCredit.isEmpty()) {	
						jrnlListPurchaseCredit.get(0).setJournalDate(purchaseNew.getPurchaseDate());
						jrnlListPurchaseCredit.get(0).setDebitAmount(purchaseNew.getTotalAmount());
						jrnlListPurchaseCredit.get(0).setCreditAmount(purchaseNew.getTotalAmount());
						jrnlListPurchaseCredit.get(0).setCreditAccountName(purchaseNew.getSupplierName());
						journalDao.updateJournal(jrnlListPurchaseCredit.get(0));
					}					

					}else if(purchaseOld.getPaymentMode().equals("Cash") && purchaseNew.getPaymentMode().equals("Cash")){
			    	
					/** party Ledger Update */	
					List <Ledger> ledgerListCashLedger = ledgerDao.searchLedger("Cash Account");
					BigDecimal oldClBalanceCashLedger = ledgerListCashLedger.get(0).getClosingTotalBalance();
					String clTypeCashLedger = ledgerListCashLedger.get(0).getClosingTotalType();
			
					if(clTypeCashLedger.equalsIgnoreCase("Credit")){
						oldClBalanceCashLedger = ZERO.subtract(oldClBalanceCashLedger);						
					}	
			
					BigDecimal dropClBalanceCashLedger = oldClBalanceCashLedger.add(purchaseOld.getTotalAmount());
					BigDecimal finalClBalanceCashLedger = dropClBalanceCashLedger.subtract(purchaseNew.getTotalAmount());
					
					if(finalClBalanceCashLedger.signum() == -1){
						finalClType = "Credit";
						finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
					}else{
						finalClType = "Debit";						
					}

					ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, finalClType, "Cash Account");
					
					/** Journal Update */
					List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("Purchase", "P"+purchaseOld.getPurchaseId().toString());
					if(!jrnlListPurchaseCash.isEmpty()){	
						jrnlListPurchaseCash.get(0).setJournalDate(purchaseNew.getPurchaseDate());
						jrnlListPurchaseCash.get(0).setDebitAmount(purchaseNew.getTotalAmount());
						jrnlListPurchaseCash.get(0).setCreditAmount(purchaseNew.getTotalAmount());
						jrnlListPurchaseCash.get(0).setCreditAccountName(purchaseNew.getSupplierName());
						journalDao.updateJournal(jrnlListPurchaseCash.get(0));
					}
					List<Journal> jrnlListPurPayment = journalDao.getJournalUpdateSales("Purchase Payment", "P"+purchaseOld.getPurchaseId().toString());
					
					if(!jrnlListPurPayment.isEmpty()){	
						jrnlListPurPayment.get(0).setJournalDate(purchaseNew.getPurchaseDate());
						jrnlListPurPayment.get(0).setDebitAmount(purchaseNew.getTotalAmount());
						jrnlListPurPayment.get(0).setCreditAmount(purchaseNew.getTotalAmount());
						jrnlListPurPayment.get(0).setDebitAccountName(purchaseNew.getSupplierName());
						journalDao.updateJournal(jrnlListPurPayment.get(0));
					} 
					}else if(purchaseOld.getPaymentMode().equals("Cash") && purchaseNew.getPaymentMode().equals("Credit")){		
					/** Party Balance Update */
					List<Ledger> partyLedger = ledgerDao.searchLedger(purchaseNew.getSupplierName());
					String clTypeParty = partyLedger.get(0).getClosingTotalType();
					BigDecimal oldClBalanceParty = partyLedger.get(0).getClosingTotalBalance();
				
					if(clTypeParty.equalsIgnoreCase("Credit")){
						oldClBalanceParty = ZERO.subtract(oldClBalanceParty);						
					}
					
					BigDecimal ClBalanceParty = oldClBalanceParty.subtract(purchaseNew.getTotalAmount());
														
					if(ClBalanceParty.signum() == -1){
						finalClType = "Credit";
						ClBalanceParty = ClBalanceParty.multiply(CONVERT);
					}else{
						finalClType = "Debit";						
					}
					
					ledgerDao.updateLedgerPartyBalance(ClBalanceParty, finalClType, purchaseNew.getSupplierName());
								
					/** Cash Ledger Update */
					ledgername = "Cash Account";
					List<Ledger> CashLedger = ledgerDao.searchLedger(ledgername);
					String clTypeCash = CashLedger.get(0).getClosingTotalType();
					BigDecimal oldClBalanceCash = CashLedger.get(0).getClosingTotalBalance();
				
					if(clTypeCash.equalsIgnoreCase("Credit")){
						oldClBalanceCash = ZERO.subtract(oldClBalanceCash);						
					}						
					BigDecimal ClBalanceCash = oldClBalanceCash.add(purchaseOld.getTotalAmount());
														
					if(ClBalanceCash.signum() == -1){
						finalClType = "Credit";
						ClBalanceCash = ClBalanceCash.multiply(CONVERT);
					}else{
						finalClType = "Debit";						
					}
					
					ledgerDao.updateLedgerPartyBalance(ClBalanceCash, finalClType, ledgername);
					
					/** Journal Update */
					List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("Purchase","P"+purchaseOld.getPurchaseId().toString());
					if(!jrnlListPurchaseCash.isEmpty()) {	
						jrnlListPurchaseCash.get(0).setJournalDate(purchaseNew.getPurchaseDate());
						jrnlListPurchaseCash.get(0).setDebitAmount(purchaseNew.getTotalAmount());
						jrnlListPurchaseCash.get(0).setCreditAmount(purchaseNew.getTotalAmount());
						jrnlListPurchaseCash.get(0).setCreditAccountName(purchaseNew.getSupplierName());
						journalDao.updateJournal(jrnlListPurchaseCash.get(0));
					}
				
					/** Second Journal Should be deleted  */
					List<Journal> jrnlListPurchasePayment = journalDao.getJournalUpdateSales("Purchase Payment", "P"+purchaseOld.getPurchaseId().toString());
					if(!jrnlListPurchasePayment.isEmpty()) {						
						journalDao.deleteJournal(jrnlListPurchasePayment.get(0));
					}					
					
					} else if (purchaseOld.getPaymentMode().equals("Credit") && purchaseNew.getPaymentMode().equals("Cash")) {
					
					/** Party Balance Update */
					List<Ledger> partyLedger = ledgerDao.searchLedger(purchaseNew.getSupplierName());
					String clTypeParty = partyLedger.get(0).getClosingTotalType();
					BigDecimal oldClBalanceParty = partyLedger.get(0).getClosingTotalBalance();
				
					if(clTypeParty.equalsIgnoreCase("Credit")){
						oldClBalanceParty = ZERO.subtract(oldClBalanceParty);						
					}						
					BigDecimal ClBalanceParty = oldClBalanceParty.add(purchaseOld.getTotalAmount());
																		
					if(ClBalanceParty.signum() == -1){
						finalClType = "Credit"; 
						ClBalanceParty = ClBalanceParty.multiply(CONVERT);
					}else{
						finalClType = "Debit";						
					}
					ledgerDao.updateLedgerPartyBalance(ClBalanceParty, finalClType, purchaseOld.getSupplierName());
					
					/** Cash Ledger Update*/
					ledgername = "Cash Account";
					List<Ledger> CashLedger = ledgerDao.searchLedger(ledgername);
					String clTypeCash = CashLedger.get(0).getClosingTotalType();
					BigDecimal oldClBalanceCash = CashLedger.get(0).getClosingTotalBalance();
				
					if(clTypeCash.equalsIgnoreCase("Credit")){
						oldClBalanceCash = ZERO.subtract(oldClBalanceCash);						
					}						
					BigDecimal ClBalanceCash = oldClBalanceCash.subtract(purchaseNew.getTotalAmount());
														
					if(ClBalanceCash.signum() == -1){
						finalClType = "Credit";
						ClBalanceCash = ClBalanceCash.multiply(CONVERT);
					}else{
						finalClType = "Debit";						
					}	
					
					ledgerDao.updateLedgerPartyBalance(ClBalanceCash, finalClType, ledgername);
					
					/** Journal Update*/
					List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("Purchase", "P"+purchaseOld.getPurchaseId().toString());
					if(!jrnlListPurchaseCash.isEmpty()) {			
						jrnlListPurchaseCash.get(0).setJournalDate(purchaseNew.getPurchaseDate());
						jrnlListPurchaseCash.get(0).setDebitAmount(purchaseNew.getTotalAmount());
						jrnlListPurchaseCash.get(0).setCreditAmount(purchaseNew.getTotalAmount());
						jrnlListPurchaseCash.get(0).setCreditAccountName(purchaseNew.getSupplierName());
						journalDao.updateJournal(jrnlListPurchaseCash.get(0));
					}
				
					jrnl = new Journal();			
					jrnl.setJournalNO(getJournalNumber(jrnl));
					jrnl.setJournalType("Purchase Payment");
				    jrnl.setJournalDate(purchaseNew.getPurchaseDate());
				    
				 // Set Account group code as Debit code
					ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);			
					jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
					jrnl.setDebitAccountName(purchaseNew.getSupplierName());
					
					// Set Account group code as Credit code
					ledgerGroupCode = ledgerDao.getLedgerAccountCode(ledgername);
					jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					jrnl.setCreditAccountName(ledgername);
					
					jrnl.setTransactionId("P"+purchaseNew.getPurchaseId().toString());
					jrnl.setDebitAmount(purchaseNew.getTotalAmount());
					jrnl.setCreditAmount(purchaseNew.getTotalAmount());
					jrnl.setNarration("Bill No " + purchaseNew.getPurchseInvoice());				
					journalDao.insertJournal((Journal) jrnl);
				
			      }
			   }
			}		
	  //*************************************************** Update Stock **************************************/			    
		
 					
 					//************************** Purchase Return updation starts here ************************************/
	//******* Purchase Return Same Supplier : Cash Ledger Update : Total Amount : Payment Mode - Cash *********************************************/
	else if(ptype.equals("Purchase Return"))
			{
				if(purchaseOld.getSupplierName().equals(purchaseNew.getSupplierName()))
				{					
					if(purchaseOld.getPaymentMode().equals("Cash") && purchaseNew.getPaymentMode().equals("Cash"))
					{
								
						ledgername = "Purchase Account";
						List <Ledger> purchaseList1 = ledgerDao.searchLedger(ledgername);
						clBal = purchaseList1.get(0).getClosingTotalBalance();	
						clType = purchaseList1.get(0).getClosingTotalType();
						BigDecimal oldtotamt=purchaseOld.getTotalAmount();
						BigDecimal newtotamt=purchaseNew.getTotalAmount();
						BigDecimal addtotamt=clBal.add(oldtotamt);
						
						BigDecimal ftotamt=addtotamt.subtract(newtotamt);
						if(ftotamt.signum()==-1){
							closingTyp = "Credit";
							ftotamt=ftotamt.multiply(CONVERT);
						}else{
							closingTyp="Debit";
						}
			
						ledgerDao.updateLedgerPartyBalance(ftotamt, closingTyp, ledgername);
								
						//************ cash ledger update for supplier::paymode::Cash ************/
						List <Ledger> ledgerListCash = ledgerDao.searchLedger("Cash Account");
						BigDecimal oldClBalanceCashLedger = ledgerListCash.get(0).getClosingTotalBalance();
						String clTypeCashLedger = ledgerListCash.get(0).getClosingTotalType();
						
						if(clTypeCashLedger.equalsIgnoreCase("Credit")){
							oldClBalanceCashLedger = ZERO.subtract(oldClBalanceCashLedger);						
						}
						
						BigDecimal dropClBalanceCashLedger = oldClBalanceCashLedger.subtract(purchaseOld.getTotalAmount());
						BigDecimal finalClBalanceCashLedger = dropClBalanceCashLedger.add(purchaseNew.getTotalAmount());
								
						if(finalClBalanceCashLedger.signum() == -1){
							closingType = "Credit";
							finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
						}else{
								closingType = "Debit";						
							}
						ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, closingType, "Cash Account");
					
						
						//************ journal entry purchase return for same customer **********************/
							List<Journal> jrnlListSales=journalDao.getJournalUpdateSales("Purchase Return", "P"+purchaseOld.getPurchaseId().toString());
				          
							if(!jrnlListSales.isEmpty()){
				        	   
				        	   jrnlListSales.get(0).setDebitAmount(purchaseNew.getTotalAmount());
				        	   jrnlListSales.get(0).setCreditAmount(purchaseNew.getTotalAmount());
				        	   jrnlListSales.get(0).setDebitAccountName(purchaseNew.getSupplierName());
				        	   jrnlListSales.get(0).setJournalDate(purchaseNew.getPurchaseDate());
				        	   journalDao.updateJournal(jrnlListSales.get(0));
				        	   
				           }
				           
				           List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("Purchase Return Receipt","P"+purchaseOld.getPurchaseId().toString());
				           
				           if(!jrnlListPurchaseCash.isEmpty()){
				    
				        	   jrnlListPurchaseCash.get(0).setDebitAmount(purchaseNew.getTotalAmount());
				        	   jrnlListPurchaseCash.get(0).setCreditAmount(purchaseNew.getTotalAmount());
				        	   jrnlListPurchaseCash.get(0).setCreditAccountName(purchaseNew.getSupplierName());
				        	   jrnlListPurchaseCash.get(0).setJournalDate(purchaseNew.getPurchaseDate());
				        	   journalDao.updateJournal(jrnlListPurchaseCash.get(0));
		        	   
				           }
					}	//************************** same customer for Credit ***************************/
					else if(purchaseOld.getPaymentMode().equals("Credit") && purchaseNew.getPaymentMode().equals("Credit"))
					{
							partyName = purchaseNew.getSupplierName();
							List<Ledger> customerlist = ledgerDao.searchLedger(partyName);
							clType = customerlist.get(0).getClosingTotalType();	
							clBal = customerlist.get(0).getClosingTotalBalance();	
							BigDecimal newclBal=purchaseNew.getTotalAmount();
							BigDecimal oldclBal=purchaseOld.getTotalAmount();
							
							BigDecimal difBal=clBal.subtract(oldclBal);
						    BigDecimal finalBal=difBal.add(newclBal);
							
						    if(finalBal.signum()==-1){
								closingTyp = "Credit";
								finalBal=finalBal.multiply(CONVERT);
							}else{
								closingTyp="Debit";
							}
								
						    ledgerDao.updateLedgerPartyBalance(finalBal, closingTyp, partyName);
						     //  System.out.println("partybalance:"+clBal+"\n"+"oldtoatamt:"+oldclBal+"\n"+"newtotamt:"+newclBal+"final:"+finalBal);
						   
						    //****************** journal entry for credit ***********************/
							
							List<Journal> jrnlListPurchaseCredit1 = journalDao.getJournalUpdateSales("Purchase Return", "P"+purchaseOld.getPurchaseId().toString());
							if(!jrnlListPurchaseCredit1.isEmpty()){						
								jrnlListPurchaseCredit1.get(0).setDebitAmount(purchaseNew.getTotalAmount());
								jrnlListPurchaseCredit1.get(0).setCreditAmount(purchaseNew.getTotalAmount());
								jrnlListPurchaseCredit1.get(0).setDebitAccountName(purchaseNew.getSupplierName());
								jrnlListPurchaseCredit1.get(0).setJournalDate(purchaseNew.getPurchaseDate());
								journalDao.updateJournal(jrnlListPurchaseCredit1.get(0));
							}
							
							List<Journal> jrnlListPurchaseCredit2 = journalDao.getJournalUpdateSales("Purchase Return Receipt", "P"+purchaseOld.getPurchaseId().toString());
							if(!jrnlListPurchaseCredit2.isEmpty()){						
								jrnlListPurchaseCredit2.get(0).setDebitAmount(purchaseNew.getTotalAmount());
								jrnlListPurchaseCredit2.get(0).setCreditAmount(purchaseNew.getTotalAmount());
								jrnlListPurchaseCredit2.get(0).setCreditAccountName(purchaseNew.getSupplierName());
								jrnlListPurchaseCredit2.get(0).setJournalDate(purchaseNew.getPurchaseDate());
								journalDao.updateJournal(jrnlListPurchaseCredit2.get(0));
							}
							
							//************************ Same supplier Cash To Credit ***********************/
					 	}
						else if(purchaseOld.getPaymentMode().equals("Cash") && purchaseNew.getPaymentMode().equals("Credit"))
						{
					 		/* Party Balance Update */
							List<Ledger> partyLedger = ledgerDao.searchLedger(purchaseOld.getSupplierName());
							String clTypeParty = partyLedger.get(0).getClosingTotalType();
							BigDecimal oldClBalanceParty = partyLedger.get(0).getClosingTotalBalance();
							
							if(clTypeParty.equalsIgnoreCase("Credit")){
								oldClBalanceParty = ZERO.subtract(oldClBalanceParty);						
							}						
							BigDecimal ClBalanceParty = oldClBalanceParty.add(purchaseNew.getTotalAmount());
																
							if(ClBalanceParty.signum() == -1){
								closingTyp = "Credit";
								ClBalanceParty = ClBalanceParty.multiply(CONVERT);
							}else{
								closingTyp = "Debit";						
							}
							
							ledgerDao.updateLedgerPartyBalance(ClBalanceParty, closingTyp, purchaseNew.getSupplierName());
							
							/* Purchase Ledger */							
							List <Ledger> ledgerListCash = ledgerDao.searchLedger("Purchase Account");
							BigDecimal oldClBalanceCashLedger = ledgerListCash.get(0).getClosingTotalBalance();
							String clTypeCashLedger = ledgerListCash.get(0).getClosingTotalType();
							
							if(clTypeCashLedger.equalsIgnoreCase("Credit")){
								oldClBalanceCashLedger = ZERO.subtract(oldClBalanceCashLedger);						
							}
							
							BigDecimal dropClBalanceCashLedger = oldClBalanceCashLedger.add(purchaseOld.getTotalAmount());
							BigDecimal finalClBalanceCashLedger = dropClBalanceCashLedger.subtract(purchaseNew.getTotalAmount());
									
							if(finalClBalanceCashLedger.signum() == -1){
								closingType = "Credit";
								finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
							}else{
									closingType = "Debit";						
								}
							ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, closingType, "Purchase Account");
							
							
							/* Cash Ledger Update */
							List<Ledger> CashLedger = ledgerDao.searchLedger("Cash Account");
							String clTypeCash = CashLedger.get(0).getClosingTotalType();
							BigDecimal oldClBalanceCash = CashLedger.get(0).getClosingTotalBalance();
									
							if(clTypeCash.equalsIgnoreCase("Credit")){
								oldClBalanceCash = ZERO.subtract(oldClBalanceCash);						
							}						
							BigDecimal ClBalanceCash = oldClBalanceCash.subtract(purchaseOld.getTotalAmount());
												
														
							if(ClBalanceCash.signum() == -1){
								closingTyp = "Credit";
								ClBalanceCash = ClBalanceCash.multiply(CONVERT);
							}else{
								closingTyp = "Debit";						
							}	
							
							ledgerDao.updateLedgerPartyBalance(ClBalanceCash, closingTyp, "Cash Account");
								
							/* Journal Entry Update */ 
							List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("Purchase Return", "P"+purchaseOld.getPurchaseId().toString());
							if(!jrnlListPurchaseCash.isEmpty()) {						
								jrnlListPurchaseCash.get(0).setDebitAmount(purchaseNew.getTotalAmount());
								jrnlListPurchaseCash.get(0).setCreditAmount(purchaseNew.getTotalAmount());
								jrnlListPurchaseCash.get(0).setDebitAccountName(purchaseNew.getSupplierName());
								jrnlListPurchaseCash.get(0).setJournalDate(purchaseNew.getPurchaseDate());
								journalDao.updateJournal(jrnlListPurchaseCash.get(0));
							}
							
							/* Second Journal Should be deleted  */
							List<Journal> jrnlListPurchasePayment = journalDao.getJournalUpdateSales("Purchase Return Receipt", "P"+purchaseOld.getPurchaseId().toString());
							if(!jrnlListPurchasePayment.isEmpty()) {						
								journalDao.deleteJournal(jrnlListPurchasePayment.get(0));
							}
							
					 		//************* same supplier name for credit to cash mode *********************/
					 	} 
						else if(purchaseOld.getPaymentMode().equals("Credit") && purchaseNew.getPaymentMode().equals("Cash")) {
						     
							/** Party Balance Update */
							List<Ledger> partyLedger = ledgerDao.searchLedger(purchaseOld.getSupplierName());
							String clTypeParty = partyLedger.get(0).getClosingTotalType();
							BigDecimal oldClBalanceParty = partyLedger.get(0).getClosingTotalBalance();
							
							if(clTypeParty.equalsIgnoreCase("Credit")){
								oldClBalanceParty = ZERO.subtract(oldClBalanceParty);						
							}						
							BigDecimal ClBalanceParty = oldClBalanceParty.subtract(purchaseOld.getTotalAmount());
																
							if(ClBalanceParty.signum() == -1){
								closingTyp = "Credit";
								ClBalanceParty = ClBalanceParty.multiply(CONVERT);
							}else{
								closingTyp = "Debit";						
							}
							
							ledgerDao.updateLedgerPartyBalance(ClBalanceParty, closingTyp, purchaseNew.getSupplierName());
							
							/** Purchase Ledger Update */
							List <Ledger> ledgerListCash = ledgerDao.searchLedger("Purchase Account");
							BigDecimal oldClBalanceCashLedger = ledgerListCash.get(0).getClosingTotalBalance();
							String clTypeCashLedger = ledgerListCash.get(0).getClosingTotalType();
							
							if(clTypeCashLedger.equalsIgnoreCase("Credit")){
								oldClBalanceCashLedger = ZERO.subtract(oldClBalanceCashLedger);						
							}
							
							BigDecimal dropClBalanceCashLedger = oldClBalanceCashLedger.add(purchaseOld.getTotalAmount());
							BigDecimal finalClBalanceCashLedger = dropClBalanceCashLedger.subtract(purchaseNew.getTotalAmount());
									
							if(finalClBalanceCashLedger.signum() == -1){
								closingType = "Credit";
								finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
							}else{
									closingType = "Debit";						
								}
							
							ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, closingType, "Purchase Account");
							
							/** Cash Ledger Update */
							List<Ledger> CashLedger = ledgerDao.searchLedger("Cash Account");
							String clTypeCash = CashLedger.get(0).getClosingTotalType();
							BigDecimal oldClBalanceCash = CashLedger.get(0).getClosingTotalBalance();
						
							if(clTypeCash.equalsIgnoreCase("Credit")){
								oldClBalanceCash = ZERO.subtract(oldClBalanceCash);						
							}						
							BigDecimal ClBalanceCash = oldClBalanceCash.add(purchaseNew.getTotalAmount());
																
							if(ClBalanceCash.signum() == -1){
								closingTyp = "Credit";
								ClBalanceCash = ClBalanceCash.multiply(CONVERT);
							}else{
								closingTyp = "Debit";						
							}											
							ledgerDao.updateLedgerPartyBalance(ClBalanceCash, closingTyp, "Cash Account");
							
							/** Journal Update */
							List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("Purchase Return", "P"+purchaseOld.getPurchaseId().toString());
							if(!jrnlListPurchaseCash.isEmpty()) {						
								jrnlListPurchaseCash.get(0).setDebitAmount(purchaseNew.getTotalAmount());
								jrnlListPurchaseCash.get(0).setCreditAmount(purchaseNew.getTotalAmount());
								jrnlListPurchaseCash.get(0).setDebitAccountName(purchaseNew.getSupplierName());
								jrnlListPurchaseCash.get(0).setJournalDate(purchaseNew.getPurchaseDate());
								journalDao.updateJournal(jrnlListPurchaseCash.get(0));
							}						
							
							jrnl = new Journal();			
							jrnl.setJournalNO(getJournalNumber(jrnl));
							jrnl.setJournalType("Purchase Return Receipt");
						    jrnl.setJournalDate(purchaseNew.getPurchaseDate());
						    
						 // Set Account group code as Debit code
							ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);			
							jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
							jrnl.setDebitAccountName(purchaseNew.getSupplierName());
							
							// Set Account group code as Credit code
							ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");
							jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
							jrnl.setCreditAccountName("Cash Account");
							
							jrnl.setTransactionId("P"+purchaseNew.getPurchaseId().toString());
							jrnl.setDebitAmount(purchaseNew.getTotalAmount());
							jrnl.setCreditAmount(purchaseNew.getTotalAmount());
							jrnl.setNarration("Bill No " + purchaseNew.getPurchseInvoice());				
							journalDao.insertJournal((Journal) jrnl);
						}
					
					//************************************ different supplier name *****************************/
					}
					//same customer condtion ends here
					else //if(!purchaseOld.getSupplierName().equals(purchaseNew.getSupplierName())){
							
							if(purchaseOld.getPaymentMode().equals("Credit") && purchaseNew.getPaymentMode().equals("Credit")){
								
								//***** old party supplier*********/
								
								List<Ledger> customerlist = ledgerDao.searchLedger(purchaseOld.getSupplierName());
								clType = customerlist.get(0).getClosingTotalType();	
								BigDecimal	clBal1 = customerlist.get(0).getClosingTotalBalance();	
								
								BigDecimal finaltoBal=clBal1.subtract(purchaseOld.getTotalAmount());

								  if(finaltoBal.signum()==-1){
										closingTyp = "Credit";
										finaltoBal=finaltoBal.multiply(CONVERT);
									}else{
										closingTyp="Debit";
									}
								
								  ledgerDao.updateLedgerPartyBalance(finaltoBal, closingTyp, purchaseOld.getSupplierName());
								 
								//**************** new party supplier ****************/
							
							List<Ledger> customerlists = ledgerDao.searchLedger(purchaseNew.getSupplierName());
							clType = customerlists.get(0).getClosingTotalType();	
							BigDecimal	clBal2 = customerlists.get(0).getClosingTotalBalance();
								
							BigDecimal finalTobal=clBal2.add(purchaseNew.getTotalAmount());
								  
							 if(finalTobal.signum()==-1){
								 	closingTyp = "Credit";
									finalTobal=finalTobal.multiply(CONVERT);
							}else{
									closingTyp="Debit";
								}
								
							 ledgerDao.updateLedgerPartyBalance(finalTobal, closingTyp, partyName);
							 				
							//*********** Purchase Ledger update for Credit mode and DIfferent supplier ******************/
							List <Ledger> ledgerListCash = ledgerDao.searchLedger("Purchase Account");
							BigDecimal oldClBalanceCashLedger = ledgerListCash.get(0).getClosingTotalBalance();
							String clTypeCashLedger = ledgerListCash.get(0).getClosingTotalType();
							
							if(clTypeCashLedger.equalsIgnoreCase("Credit")){
								oldClBalanceCashLedger = ZERO.subtract(oldClBalanceCashLedger);						
							}
							
							BigDecimal dropClBalanceCashLedger = oldClBalanceCashLedger.add(purchaseOld.getTotalAmount());
							BigDecimal finalClBalanceCashLedger = dropClBalanceCashLedger.add(purchaseNew.getTotalAmount());
									
							if(finalClBalanceCashLedger.signum() == -1){
								closingType = "Credit";
								finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
							}else{
									closingType = "Debit";						
								}
							
							ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, closingType, "Purchase Account");
						
							//************** journal entry for differnt supplier credit mode *******************************/
													
							List<Journal> jrnlListPurchaseCredit1 = journalDao.getJournalUpdateSales("Purchase Return", "P"+purchaseOld.getPurchaseId().toString());
							if(!jrnlListPurchaseCredit1.isEmpty()){						
								jrnlListPurchaseCredit1.get(0).setDebitAmount(purchaseNew.getTotalAmount());
								jrnlListPurchaseCredit1.get(0).setCreditAmount(purchaseNew.getTotalAmount());
								jrnlListPurchaseCredit1.get(0).setDebitAccountName(purchaseNew.getSupplierName());
								jrnlListPurchaseCredit1.get(0).setJournalDate(purchaseNew.getPurchaseDate());
								journalDao.updateJournal(jrnlListPurchaseCredit1.get(0));
							}
							
							List<Journal> jrnlListPurchaseCredit2 = journalDao.getJournalUpdateSales("Purchase Return Receipt ", "P"+purchaseOld.getPurchaseId().toString());
							if(!jrnlListPurchaseCredit2.isEmpty()){						
								jrnlListPurchaseCredit2.get(0).setDebitAmount(purchaseNew.getTotalAmount());
								jrnlListPurchaseCredit2.get(0).setCreditAmount(purchaseNew.getTotalAmount());
								jrnlListPurchaseCredit2.get(0).setCreditAccountName(purchaseNew.getSupplierName());
								jrnlListPurchaseCredit2.get(0).setJournalDate(purchaseNew.getPurchaseDate());
								journalDao.updateJournal(jrnlListPurchaseCredit2.get(0));
							}
							
						//****************** differrent supplier for cash to credit mode **************************/
						}else if(purchaseOld.getPaymentMode().equals("Cash") && purchaseNew.getPaymentMode().equals("Credit")){
					
						/** Party Balance Update */
						List<Ledger> partyLedger = ledgerDao.searchLedger(purchaseNew.getSupplierName());
						String clTypeParty = partyLedger.get(0).getClosingTotalType();
						BigDecimal oldClBalanceParty = partyLedger.get(0).getClosingTotalBalance();
					
						if(clTypeParty.equalsIgnoreCase("Credit")){
							oldClBalanceParty = ZERO.subtract(oldClBalanceParty);						
						}						
						BigDecimal ClBalanceParty = oldClBalanceParty.add(purchaseNew.getTotalAmount());
															
						if(ClBalanceParty.signum() == -1){
							closingTyp = "Credit";
							ClBalanceParty = ClBalanceParty.multiply(CONVERT);
						}else{
							closingTyp = "Debit";						
						}
						
						ledgerDao.updateLedgerPartyBalance(ClBalanceParty, closingTyp, purchaseNew.getSupplierName());
						
						/** Purchase ledger update */
						
						List <Ledger> ledgerListCash = ledgerDao.searchLedger("Purchase Account");
						BigDecimal oldClBalanceCashLedger = ledgerListCash.get(0).getClosingTotalBalance();
						String clTypeCashLedger = ledgerListCash.get(0).getClosingTotalType();
						
						if(clTypeCashLedger.equalsIgnoreCase("Credit")){
							oldClBalanceCashLedger = ZERO.subtract(oldClBalanceCashLedger);						
						}
						
						BigDecimal dropClBalanceCashLedger = oldClBalanceCashLedger.add(purchaseOld.getTotalAmount());
						BigDecimal finalClBalanceCashLedger = dropClBalanceCashLedger.subtract(purchaseNew.getTotalAmount());
								
						if(finalClBalanceCashLedger.signum() == -1){
							closingType = "Credit";
							finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
						}else{
								closingType = "Debit";						
							}
						
						ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, closingType, "Purchase Account");
						
						/** Cash Ledger Update */
						List<Ledger> CashLedger = ledgerDao.searchLedger("Cash Account");
						String clTypeCash = CashLedger.get(0).getClosingTotalType();
						BigDecimal oldClBalanceCash = CashLedger.get(0).getClosingTotalBalance();
					
						if(clTypeCash.equalsIgnoreCase("Credit")){
							oldClBalanceCash = ZERO.subtract(oldClBalanceCash);						
						}						
						BigDecimal ClBalanceCash = oldClBalanceCash.subtract(purchaseOld.getTotalAmount());
															
						if(ClBalanceCash.signum() == -1){
							closingTyp = "Credit";
							ClBalanceCash = ClBalanceCash.multiply(CONVERT);
						}else{
							closingTyp = "Debit";						
						}	
						
						ledgerDao.updateLedgerPartyBalance(ClBalanceCash, closingTyp, "Cash Account");
						
						/** Journal Update */
						List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("Purchase Return","P"+purchaseOld.getPurchaseId().toString());
						if(!jrnlListPurchaseCash.isEmpty()) {						
							jrnlListPurchaseCash.get(0).setDebitAmount(purchaseOld.getTotalAmount());
							jrnlListPurchaseCash.get(0).setCreditAmount(purchaseOld.getTotalAmount());
							jrnlListPurchaseCash.get(0).setCreditAccountName(purchaseOld.getSupplierName());
							jrnlListPurchaseCash.get(0).setJournalDate(purchaseNew.getPurchaseDate());
							journalDao.updateJournal(jrnlListPurchaseCash.get(0));
						}
						
						/** Second Journal Should be deleted  */
						List<Journal> jrnlListPurchasePayment = journalDao.getJournalUpdateSales("Purchase Return Receipt", "P"+purchaseOld.getPurchaseId().toString());
						if(!jrnlListPurchasePayment.isEmpty()) {						
							journalDao.deleteJournal(jrnlListPurchasePayment.get(0));
						}
				
						}else if (purchaseOld.getPaymentMode().equals("Credit") && purchaseNew.getPaymentMode().equals("Cash")) {
							
							/** Party Balance Update */
							List<Ledger> partyLedger = ledgerDao.searchLedger(purchaseNew.getSupplierName());
							String clTypeParty = partyLedger.get(0).getClosingTotalType();
							BigDecimal oldClBalanceParty = partyLedger.get(0).getClosingTotalBalance();
						
							if(clTypeParty.equalsIgnoreCase("Credit")){
								oldClBalanceParty = ZERO.subtract(oldClBalanceParty);						
							}		
							
							BigDecimal ClBalanceParty = oldClBalanceParty.subtract(purchaseOld.getTotalAmount());
																
							if(ClBalanceParty.signum() == -1){
								closingTyp = "Credit";
								ClBalanceParty = ClBalanceParty.multiply(CONVERT);
							}else{
								closingTyp = "Debit";						
							}
							
							ledgerDao.updateLedgerPartyBalance(ClBalanceParty, closingTyp, purchaseNew.getSupplierName());
							
							/** Purchase Ledger Update */							
							List <Ledger> ledgerListCash = ledgerDao.searchLedger("Purchase Account");
							BigDecimal oldClBalanceCashLedger = ledgerListCash.get(0).getClosingTotalBalance();
							String clTypeCashLedger = ledgerListCash.get(0).getClosingTotalType();
							
							if(clTypeCashLedger.equalsIgnoreCase("Credit")){
								oldClBalanceCashLedger = ZERO.subtract(oldClBalanceCashLedger);						
							}
							
							BigDecimal dropClBalanceCashLedger = oldClBalanceCashLedger.add(purchaseOld.getTotalAmount());
							BigDecimal finalClBalanceCashLedger = dropClBalanceCashLedger.subtract(purchaseNew.getTotalAmount());
									
							if(finalClBalanceCashLedger.signum() == -1){
								closingType = "Credit";
								finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
							}
							else{
									closingType = "Debit";						
							}
							
							ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, closingType, "Purchase Account");
														
							/** Cash Ledger Update*/
							List<Ledger> CashLedger = ledgerDao.searchLedger("Cash Account");
							String clTypeCash = CashLedger.get(0).getClosingTotalType();
							BigDecimal oldClBalanceCash = CashLedger.get(0).getClosingTotalBalance();
						
							if(clTypeCash.equalsIgnoreCase("Credit")){
								oldClBalanceCash = ZERO.subtract(oldClBalanceCash);						
							}						
							BigDecimal ClBalanceCash = oldClBalanceCash.subtract(purchaseNew.getTotalAmount());
																
							if(ClBalanceCash.signum() == -1){
								closingTyp = "Credit";
								ClBalanceCash = ClBalanceCash.multiply(CONVERT);
							}else{
								closingTyp = "Debit";						
							}	
							
							ledgerDao.updateLedgerPartyBalance(ClBalanceCash, closingTyp, "Cash Account");
							
							/** Journal Update*/
							List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("Purchase Return", "P"+purchaseOld.getPurchaseId().toString());
							if(!jrnlListPurchaseCash.isEmpty()) {						
								jrnlListPurchaseCash.get(0).setDebitAmount(purchaseOld.getTotalAmount());
								jrnlListPurchaseCash.get(0).setCreditAmount(purchaseOld.getTotalAmount());
								jrnlListPurchaseCash.get(0).setCreditAccountName(purchaseOld.getSupplierName());
								jrnlListPurchaseCash.get(0).setJournalDate(purchaseNew.getPurchaseDate());
								journalDao.updateJournal(jrnlListPurchaseCash.get(0));
							}
							
							jrnl = new Journal();			
							jrnl.setJournalNO(getJournalNumber(jrnl));
							jrnl.setJournalType("Purchase Return Receipt");
						    jrnl.setJournalDate(purchaseNew.getPurchaseDate());
						    
						 // Set Account group code as Debit code
							ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);			
							jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
							jrnl.setDebitAccountName(partyName);
							
							// Set Account group code as Credit code
							ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account" );
							jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
							jrnl.setCreditAccountName("Cash Account");
							
							jrnl.setTransactionId("P"+purchaseNew.getPurchaseId().toString());
							jrnl.setDebitAmount(purchaseNew.getTotalAmount());
							jrnl.setCreditAmount(purchaseNew.getTotalAmount());
							jrnl.setNarration("Bill No " + purchaseNew.getPurchseInvoice());				
							journalDao.insertJournal((Journal) jrnl);
							
						   }if(purchaseOld.getPaymentMode().equals("Cash") && purchaseNew.getPaymentMode().equals("Cash"))
							{
								
								ledgername = "Purchase Account";
								List <Ledger> purchaseList1 = ledgerDao.searchLedger(ledgername);
								clBal = purchaseList1.get(0).getClosingTotalBalance();	
								clType = purchaseList1.get(0).getClosingTotalType();
								BigDecimal oldtotamt=purchaseOld.getTotalAmount();
								BigDecimal newtotamt=purchaseNew.getTotalAmount();
								BigDecimal addtotamt=clBal.add(oldtotamt);
								
								BigDecimal ftotamt=addtotamt.subtract(newtotamt);
								if(ftotamt.signum()==-1){
									closingTyp = "Credit";
									ftotamt=ftotamt.multiply(CONVERT);
								}else{
									closingTyp="Debit";
								}
					
								ledgerDao.updateLedgerPartyBalance(ftotamt, closingTyp, ledgername);
										
								//************ cash ledger update for supplier::paymode::Cash ************/
								List <Ledger> ledgerListCash = ledgerDao.searchLedger("Cash Account");
								BigDecimal oldClBalanceCashLedger = ledgerListCash.get(0).getClosingTotalBalance();
								String clTypeCashLedger = ledgerListCash.get(0).getClosingTotalType();
								
								if(clTypeCashLedger.equalsIgnoreCase("Credit")){
									oldClBalanceCashLedger = ZERO.subtract(oldClBalanceCashLedger);						
								}
								
								BigDecimal dropClBalanceCashLedger = oldClBalanceCashLedger.subtract(purchaseOld.getTotalAmount());
								BigDecimal finalClBalanceCashLedger = dropClBalanceCashLedger.add(purchaseNew.getTotalAmount());
										
								if(finalClBalanceCashLedger.signum() == -1){
									closingType = "Credit";
									finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
								}else{
										closingType = "Debit";						
									}
								ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, closingType, "Cash Account");
							
								
								//************ journal entry purchase return for same customer **********************/
									List<Journal> jrnlListSales=journalDao.getJournalUpdateSales("Purchase Return", "P"+purchaseOld.getPurchaseId().toString());
						          
									if(!jrnlListSales.isEmpty()){
									   jrnlListSales.get(0).setJournalDate(purchaseNew.getPurchaseDate());
						        	   jrnlListSales.get(0).setDebitAmount(purchaseNew.getTotalAmount());
						        	   jrnlListSales.get(0).setCreditAmount(purchaseNew.getTotalAmount());
						        	   jrnlListSales.get(0).setDebitAccountName(purchaseNew.getSupplierName());
						        	   
						        	   journalDao.updateJournal(jrnlListSales.get(0));
						           }
						           
						           List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("Purchase Return Receipt","P"+purchaseOld.getPurchaseId().toString());
						           
						           if(!jrnlListPurchaseCash.isEmpty()){
						        	   jrnlListPurchaseCash.get(0).setJournalDate(purchaseNew.getPurchaseDate());
						        	   jrnlListPurchaseCash.get(0).setDebitAmount(purchaseNew.getTotalAmount());
						        	   jrnlListPurchaseCash.get(0).setCreditAmount(purchaseNew.getTotalAmount());
						        	   jrnlListPurchaseCash.get(0).setCreditAccountName(purchaseNew.getSupplierName());
						        	   journalDao.updateJournal(jrnlListPurchaseCash.get(0));
				        	   
						           }
							}					 
					//}
				
				}//******************************* PUrchase Return Updation ends here **************************/
			
			
				if(ptype.equals("Purchase")) //If purchase return
				{			    
				    for (int i = 0; i < itemList.size(); i++)  
					{
						ItemMaster imast = (ItemMaster)itemList.get(i);
						if (imast instanceof ItemMaster) 
						{
							itemDetails = (ItemMaster) imast;
							
					 		itemgrosswt = itemDetails.getGrossWeight();
							itemnetwt = itemDetails.getNetWeight();				
						    noqty = itemDetails.getQty();
						    
						    if(purchaseNew.getBullionType().equals(purchaseOld.getBullionType())){
						    	nos = nos - nop;								
								grosswt = grosswt.subtract(GW);
								netwt = netwt.subtract(NW);
								
								noqty = noqty + nos;
								itemgrosswt = itemgrosswt.add(grosswt);
								itemnetwt = itemnetwt.add(netwt);	
								
								
								ppq = itemDetails.getPiecesPerQty();
								tot_pcs = ppq * noqty;
								
								itemDetails.setGrossWeight(itemgrosswt);
								itemDetails.setNetWeight(itemgrosswt);
								itemDetails.setQty(noqty);
								itemDetails.setTotalPieces(tot_pcs);
								itemDetails.setTotalGrossWeight(itemgrosswt);
								
								itemmasterDao.updateItemMaster(itemDetails);//Update ItemMaster
						    }
						    else 
						    {
						    	itemmasterDao.updateOldStock(grosswt, grosswt, grosswt, nos, purchaseNew.getItemCode());
						    	itemmasterDao.updateLooseStock(GW, GW, GW, nop, oldItemcd);				 				
						    }													
						}
					}
				    
				    
				    //Update the Jewel Stock Data
				    jewelTransType = "Purchase";
				    jewelStockQty = purchaseNew.getNumberOfPieces();
				    jewelStockGrossWt = purchaseNew.getGrossWeight();
				    jewelStockNetWt = purchaseNew.getNetWeight();    
				    
				}	
				else if(ptype.equals("Purchase Return")) 
				{					
					for (int i = 0; i < itemList.size(); i++) 
					{
						ItemMaster imast = (ItemMaster)itemList.get(i); 
						if (imast instanceof ItemMaster) 
						{
							itemDetails = (ItemMaster) imast;
							
							itemgrosswt = itemDetails.getGrossWeight();
							itemnetwt = itemDetails.getNetWeight();				
						    noqty = itemDetails.getQty();
						    
						    if(purchaseNew.getBullionType().equals(purchaseOld.getBullionType()))
						    {					    					   
								nos = nos - nop;							
								grosswt = grosswt.subtract(GW);
								netwt = netwt.subtract(NW);
								
								noqty = noqty - nos;
								itemgrosswt = itemgrosswt.subtract(grosswt);
								itemnetwt = itemnetwt.subtract(netwt);								

								ppq = itemDetails.getPiecesPerQty();
								tot_pcs = ppq * noqty;
								
								itemDetails.setGrossWeight(itemgrosswt);
								itemDetails.setNetWeight(itemgrosswt);
								itemDetails.setQty(noqty);
								itemDetails.setTotalPieces(tot_pcs);
								itemDetails.setTotalGrossWeight(itemgrosswt);
								
								itemmasterDao.updateItemMaster(itemDetails);//Update ItemMaster								
						    }
						    else 
						    {
						    	itemmasterDao.updateOldStock(GW, GW, GW, nop, oldItemcd);
						    	itemmasterDao.updateLooseStock(grosswt, grosswt, grosswt, nos, itemcd);				 				
						    }						    
						}					
					}
					
					 //Update the Jewel Stock Data
				    jewelTransType = "Purchase Return";
				    jewelStockQty = -purchaseNew.getNumberOfPieces();
				    jewelStockGrossWt = CONVERT.multiply(purchaseNew.getGrossWeight());
				    jewelStockNetWt = CONVERT.multiply(purchaseNew.getNetWeight());    
				    
				}
				
				String exGoldBullionType[] = { "Gold Exchange","Old Gold Coin","Old Gold Pure Bullion"};
				String exSilverBullionType[] = { "Silver Exchange","Old Silver Coin","Old Silver Pure Bullion"};
				
				/** For Gold Exchanging Purchase Type **/
				if(!Arrays.asList(exGoldBullionType).contains(purchaseOld.getBullionType()) && Arrays.asList(exGoldBullionType).contains(purchaseNew.getBullionType())){
					purchaseNew.setPurchseInvoice(generatePurchaseInvoiceNO(purchaseNew));
				}
 				if(Arrays.asList(exGoldBullionType).contains(purchaseOld.getBullionType()) && !Arrays.asList(exGoldBullionType).contains(purchaseNew.getBullionType())){
					purchaseNew.setPurchseInvoice(generatePurchaseInvoiceNO(purchaseNew));
				}
								
				/** For Silver Exchanging Purchase Type **/
				if(!Arrays.asList(exSilverBullionType).contains(purchaseOld.getBullionType()) && Arrays.asList(exSilverBullionType).contains(purchaseNew.getBullionType())){
					purchaseNew.setPurchseInvoice(generatePurchaseInvoiceNO(purchaseNew));
				}
				if(Arrays.asList(exSilverBullionType).contains(purchaseOld.getBullionType()) && !Arrays.asList(exSilverBullionType).contains(purchaseNew.getBullionType())){
					purchaseNew.setPurchseInvoice(generatePurchaseInvoiceNO(purchaseNew));
				}
				
				//Update Jewel Stock Table
				jewelStockObj = new JewelStock();
				
				List<JewelStock> jewelStockList = jewelStockDao.searchByTransNO_TransType(purchaseOld.getPurchseInvoice(),jewelTransType);
				
				jewelStockList.get(0).setStock_MetalType(purchaseNew.getBullionType());
				jewelStockList.get(0).setStock_CategoryName(purchaseNew.getItemName());
				jewelStockList.get(0).setStock_SubCategoryName(purchaseNew.getItemName());
				jewelStockList.get(0).setStock_ItemName(purchaseNew.getItemName());
				jewelStockList.get(0).setStock_ItemCode(purchaseNew.getItemCode());
				jewelStockList.get(0).setStock_CLGrossWeight(jewelStockGrossWt);
				jewelStockList.get(0).setStock_CLNetWeight(jewelStockNetWt);
				jewelStockList.get(0).setStock_CLTotalGrossWeight(jewelStockGrossWt);
				jewelStockList.get(0).setStock_CLQty(jewelStockQty);
				jewelStockList.get(0).setStock_CLTotalPieces(jewelStockQty);
				jewelStockList.get(0).setStock_TransDate(purchaseNew.getPurchaseDate());
				jewelStockList.get(0).setStock_TransNO(purchaseNew.getPurchseInvoice());
								
				jewelStockDao.updateJewelStock(jewelStockList.get(0));
				
			
				
					DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
				  //get current date time with Calendar()
				   Calendar cal = Calendar.getInstance();
				   dateFormat.format(cal.getTime());
				   purchaseNew.setCurrentTime(dateFormat.format(cal.getTime()));				             
				purchaseDao.updatePurchase(purchaseNew);//Update Purchase	
				if(ptype.equals("Purchase"))
				{
					// Print Option
					List<CompanyInfo> companyList = companyInfoDao.listCompanyInfo();
					InvoiceBillFormat orninoviceObj = new InvoiceTextFormatPrint();
					
					if(!companyList.isEmpty() && (purchaseNew.getItemCode().equalsIgnoreCase("IT100005") || purchaseNew.getItemCode().equalsIgnoreCase("IT100010"))){ // if Printer Format set in Company Info
						if(companyList.get(0).getOrnInvoicePrint().equalsIgnoreCase("dotMatrix4")){
							orninoviceObj.printFourInchOldPurchase(purchaseNew, companyInfoDao, ledgerDao, request);
							return new ModelAndView("redirect:purchase.htm");
						}						
					}
				}
				return new ModelAndView("redirect:purchase.htm");		
		}		
	
//Redirect the form for update
			@RequestMapping(value = "/viewpurchase.htm", method = RequestMethod.GET)
			public ModelAndView getUpdate(@RequestParam(value="purchaseId", required=true) Integer purchaseId, @ModelAttribute("purchase") Purchase purchase, Model model) 
			{
				model.addAttribute("purchase", purchaseDao.getPurchase(purchaseId));							  
				model.addAttribute("suppliername", ledgerDao.listLedgerName());		 		 
				return new ModelAndView("formpurchase");
			}	
	
//Canceling request mapping				
			@RequestMapping(value="/formpurchase.htm", params="cancel") 
			public ModelAndView cancelForm()
			{
				return new ModelAndView("redirect:purchase.htm");
			}	
}