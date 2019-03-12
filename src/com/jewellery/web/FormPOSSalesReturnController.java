package com.jewellery.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
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
import com.jewellery.dao.JournalDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.dao.POSCategoryDao;
import com.jewellery.dao.POSSalesDao;
import com.jewellery.dao.PostagItemDao;
import com.jewellery.entity.Journal;
import com.jewellery.entity.Ledger;
import com.jewellery.entity.POSSales;
import com.jewellery.entity.POSSalesItem;
import com.jewellery.entity.PSales;
import com.jewellery.util.JournalCode;
import com.jewellery.validator.POSSalesReturnValidator;

@Controller
public class FormPOSSalesReturnController extends JournalCode{
	
	@Autowired
	private POSSalesDao posSalesDao;
	
	@Autowired
	private LedgerDao ledgerDao;
	
	@Autowired
	private PostagItemDao postagitemDao;
	
	@Autowired
	private POSCategoryDao poscategoryDao; 
		
	@Autowired
	private JournalDao journalDao;
	
	@Autowired
	POSSalesReturnValidator possalesreturnValidator;
	 
	Journal jrnl;
	
	/* Temporary Static Variables for Calculation */
	BigDecimal ZERO = new BigDecimal("0");
	BigDecimal CONVERT = new BigDecimal("-1");
	String finalClType ="";	
	String ledgerName;
	
	Integer qty = 1;
	String tag_status = "Purchase";
	List<String> ledgerGroupCode;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
	throws Exception{
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class, dateEditor);	
	}	
			
	@RequestMapping(value="/formPOSsalesreturn.htm",method=RequestMethod.GET)	
	public ModelAndView newForm(@ModelAttribute("POSsales")PSales possales)
	{		
		ModelAndView mav=new ModelAndView();
		mav.addObject("POSsales",new PSales());
		return new ModelAndView("formPOSsalesreturn");			
	}	
	
	@RequestMapping(value="/formPOSsalesreturn.htm",method=RequestMethod.POST,params="insert")
	public ModelAndView insert(@ModelAttribute("POSsales")PSales possales,BindingResult result,SessionStatus status,Integer salesid,ModelMap model){
		
		possalesreturnValidator.validateSalesReturn(possales.getPossales(), possales.getListpossalesItem(), result);
				
		if (result.hasErrors()) {
			possales.setPossales(possales.getPossales());
			model.addAttribute("possalesList",possales.getListpossalesItem());
			possales.setListpossalesItem(possales.getListpossalesItem());		
			model.addAttribute(possales);
			model.addAttribute("errorName", "insertError");
			return new ModelAndView("formPOSsalesreturn", model);
		}
		
		//add for invoice id for pos sales Return 10-1-13
				if(possales.getPossales().getSalesType().equalsIgnoreCase("POS Sales Return")){
					possales.getPossales().setBillNo(getPOSReturnInv(possales));		
				}
		posSalesDao.insertPOSSales(possales.getPossales());	
		
		List<POSSalesItem> listpos=possales.getListpossalesItem();
		//sale_Id= listpos.get(0).getPossales().getPosSalesId();
		
		if(!listpos.isEmpty()){
			
			for (POSSalesItem objpos : listpos) {				 				    
			
				String barcodeID = objpos.getPosItemCode();	
				
				if(!objpos.equals(null)){					
					objpos.setPossales(possales.getPossales());
					objpos.setItemSalesType("POS Sales Return");
					objpos.setItemStatus("Return");
					posSalesDao.insertPOSSalesItem(objpos);//Insert values into pos sales item
					
					List<POSSalesItem> possalesOld = posSalesDao.getPOSSoldItem(objpos.getPosItemCode());
					
					
					//for change the itemstatus in possalesitem of sales.
					if(possalesOld.get(0).getItemStatus().equalsIgnoreCase("Sold") && possalesOld.get(0).getItemSalesType().equalsIgnoreCase("POS Sales")){
						System.out.println("insdie itemstatus condition");
						possalesOld.get(0).setItemSalesType("POS Sales");
						possalesOld.get(0).setItemStatus("Return");
						posSalesDao.updatePOSSalesItem(possalesOld.get(0));
					}
					
					postagitemDao.updateSoldTagItem(qty, "Unprinted", barcodeID);//update Tag item
				//	posstockDao.updatePOSStock(qty, tag_status, barcodeID);//Update stock	
					poscategoryDao.updateCategorySet(qty, objpos.getCategoryName());
				}
			}	
		}
		
		//**************************************  Sales Ledger Update and Journal Entry  *********************************************/
		
		
		if(possales.getPossales().getReceiptType().equals("Cash")){
			
			if(possales.getPossales().getCustomerName().equals("Walk-in")){
				jrnl = new Journal();	
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("POS Sales Return");
				jrnl.setTransactionId("POS"+ possales.getPossales().getPosSalesId()); // New Column added for tracking journal entries
			    jrnl.setJournalDate(possales.getPossales().getSalesdate());
			    
			 // Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("POS Sales Account");			
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
				jrnl.setDebitAccountName("POS Sales Account");
				
				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName("Cash Account");
				
				jrnl.setDebitAmount(possales.getPossales().getNetAmount());
				jrnl.setCreditAmount(possales.getPossales().getNetAmount());
				jrnl.setNarration("Bill No "+ possales.getPossales().getPosSalesId());							
				journalDao.insertJournal((Journal) jrnl);
			}
			else
			{		
				
				// Journal Entry for Sales ledger
				
				jrnl = new Journal();	
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("POS Sales Return");
				jrnl.setTransactionId("POS"+ possales.getPossales().getPosSalesId()); // New Column added for tracking journal entries
			    jrnl.setJournalDate(possales.getPossales().getSalesdate());
			    
			    // Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("POS Sales Account");			
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
				jrnl.setDebitAccountName("POS Sales Account");
				
				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(possales.getPossales().getCustomerName());
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName(possales.getPossales().getCustomerName());
				
				jrnl.setDebitAmount(possales.getPossales().getNetAmount());
				jrnl.setCreditAmount(possales.getPossales().getNetAmount());
				jrnl.setNarration("Bill No "+ possales.getPossales().getBillNo());							
				journalDao.insertJournal((Journal) jrnl);
				
				
				// Journal Entry for Cash ledger
				
				jrnl = new Journal();	
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("POS Sales Return Payment");
				jrnl.setTransactionId("POS"+ possales.getPossales().getPosSalesId()); // New Column added for tracking journal entries
			    jrnl.setJournalDate(possales.getPossales().getSalesdate());
			    
			 // Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(possales.getPossales().getCustomerName());			
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());				
				jrnl.setDebitAccountName(possales.getPossales().getCustomerName());
								
				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName("Cash Account");
				
				jrnl.setDebitAmount(possales.getPossales().getNetAmount());
				jrnl.setCreditAmount(possales.getPossales().getNetAmount());
				jrnl.setNarration("Bill No "+ possales.getPossales().getBillNo());							
				journalDao.insertJournal((Journal) jrnl);
							
				
				/********************** POS Sales Ledger Updation ************************/
				ledgerName = "POS Sales Account";
				List <Ledger> ledgerListSales = ledgerDao.searchLedger(ledgerName);
				BigDecimal clBalanceSales = ledgerListSales.get(0).getClosingTotalBalance();
				String clTypeSales = ledgerListSales.get(0).getClosingTotalType();
				
				if(clTypeSales.equalsIgnoreCase("Credit")){
					clBalanceSales = ZERO.subtract(clBalanceSales);						
				}
						
				BigDecimal finalClBalanceSales  = clBalanceSales.add(possales.getPossales().getNetAmount());				
			
				if (finalClBalanceSales.signum() == -1) {
					finalClType = "Credit";
					finalClBalanceSales = finalClBalanceSales.multiply(CONVERT);
				} else {
					finalClType = "Debit";					
				}
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceSales, finalClType, ledgerName);
						
			
				/************************************ Cash Ledger Update ************************************/
			
				ledgerName = "Cash Account";
				List <Ledger> ledgerListCashLedger = ledgerDao.searchLedger(ledgerName);
				BigDecimal clBalanceCashLedger = ledgerListCashLedger.get(0).getClosingTotalBalance();
				String clTypeCashLedger = ledgerListCashLedger.get(0).getClosingTotalType();
				
				if(clTypeCashLedger.equalsIgnoreCase("Credit")){
					clBalanceCashLedger = ZERO.subtract(clBalanceCashLedger);						
				}
						
				BigDecimal finalClBalanceCashLedger = clBalanceCashLedger.subtract(possales.getPossales().getNetAmount());
						
				if(finalClBalanceCashLedger.signum() == -1){
					finalClType = "Credit";
					finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
				}else{
						finalClType = "Debit";						
					}
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, finalClType, ledgerName);	
			}
		}
		else{
			/**************************************** Journal Entry for Sales ledger*******************************************/
			
			jrnl = new Journal();	
			jrnl.setJournalNO(getJournalNumber(jrnl));
			jrnl.setJournalType("POS Sales Return");
			jrnl.setTransactionId("POS"+ possales.getPossales().getPosSalesId()); // New Column added for tracking journal entries
		    jrnl.setJournalDate(possales.getPossales().getSalesdate());
		    
		    // Set Account group code as Debit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode("POS Sales Account");			
			jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
			jrnl.setDebitAccountName("POS Sales Account");
			
			// Set Account group code as Credit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode(possales.getPossales().getCustomerName());
			jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
			jrnl.setCreditAccountName(possales.getPossales().getCustomerName());
			
			jrnl.setDebitAmount(possales.getPossales().getNetAmount()); 
			jrnl.setCreditAmount(possales.getPossales().getNetAmount());   
			jrnl.setNarration("Bill No " + possales.getPossales().getBillNo());							
			journalDao.insertJournal((Journal) jrnl);
			
			/****************************************** Sales Legger Updation ************************************************/
			ledgerName = "POS Sales Account";
			List <Ledger> ledgerListSales = ledgerDao.searchLedger(ledgerName);
			BigDecimal clBalanceSales = ledgerListSales.get(0).getClosingTotalBalance();
			String clTypeSales = ledgerListSales.get(0).getClosingTotalType();
			
			if(clTypeSales.equalsIgnoreCase("Credit")){
				clBalanceSales = ZERO.subtract(clBalanceSales);						
			}
					
			BigDecimal finalClBalanceSales  = clBalanceSales.add(possales.getPossales().getNetAmount());				
		
			if (finalClBalanceSales.signum() == -1) {
				finalClType = "Credit";
				finalClBalanceSales = finalClBalanceSales.multiply(CONVERT);
			}
			else {
				finalClType = "Debit";					
			}
			
			ledgerDao.updateLedgerPartyBalance(finalClBalanceSales, finalClType, ledgerName);
			

			/***************************************** Party Ledger Udaption *******************************************/
			ledgerName = possales.getPossales().getCustomerName();
			
			List <Ledger> ledgerListPartyLedger = ledgerDao.searchLedger(ledgerName);
			BigDecimal clBalanceCashLedger = ledgerListPartyLedger.get(0).getClosingTotalBalance();
			String clTypeCashLedger = ledgerListPartyLedger.get(0).getClosingTotalType();
			
			if(clTypeCashLedger.equalsIgnoreCase("Credit")){
				clBalanceCashLedger = ZERO.subtract(clBalanceCashLedger);						
			}
					
			BigDecimal finalClBalanceCashLedger = clBalanceCashLedger.subtract(possales.getPossales().getNetAmount());
					
			if(finalClBalanceCashLedger.signum() == -1){
				finalClType = "Credit";
				finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
			}else{
					finalClType = "Debit";						
				}
			
			ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, finalClType, ledgerName);	
		}		
		
		ModelAndView mav=new ModelAndView();
		mav.addObject("POSsales",new PSales());
		return new ModelAndView("redirect:possalesList.htm");		
	}
	
	/**  For Update Mode **/
	@RequestMapping(value="/formPOSsalesreturn.htm",method=RequestMethod.POST,params="update")
	public ModelAndView updateForm(@ModelAttribute("POSsales")PSales possales, BindingResult result, ModelMap model){
		
		BigDecimal convert=new BigDecimal("-1");
		BigDecimal zero=new BigDecimal("0");
		BigDecimal clBal;
		BigDecimal dropClBalanceParty;
		BigDecimal finalClBalanceParty;
		String finalcltype;		
		String clType;
		Integer salesid = possales.getPossales().getPosSalesId();

		POSSales possalesOld = posSalesDao.getPOSSales(salesid);
		BigDecimal oldGrandAmt=possalesOld.getNetAmount();
		BigDecimal newGrandAmt=possales.getPossales().getNetAmount();		
		
		List<POSSalesItem> PosSalesOld = posSalesDao.getPOSSalesItem(salesid, possales.getPossales().getSalesType());
		List<POSSalesItem> posSalesItemnew = possales.getListpossalesItem();	
	
		possalesreturnValidator.validateSalesReturn(possales.getPossales(), possales.getListpossalesItem(), result);
		
		if (result.hasErrors()) {
			possales.setPossales(possales.getPossales());
			possales.setListpossalesItem(possales.getListpossalesItem());				
			model.addAttribute(possales);
			model.addAttribute("possalesList",posSalesItemnew);
			model.addAttribute("errorName", "updateError");
			return new ModelAndView("formPOSsalesreturn", model);
		}
				  
		for (POSSalesItem newposobj : posSalesItemnew) {
			
			if (newposobj.getSalesItemID() == null || newposobj.getSalesItemID().equals("")) {
				newposobj.setPossales(possales.getPossales());
				newposobj.setItemSalesType("POS Sales Return");
				newposobj.setItemStatus("Return");
				posSalesDao.insertPOSSalesItem(newposobj);				
			}
			else {				
				for (POSSalesItem oldposobj : PosSalesOld) {	
					
					if (newposobj.getSalesItemID().equals(oldposobj.getSalesItemID())) {
						newposobj.setPossales(possales.getPossales());	
						newposobj.setItemSalesType("POS Sales Return");
						//newposobj.setItemStatus("Return");
						posSalesDao.updatePOSSales(possales.getPossales());					
						posSalesDao.updatePOSSalesItem(newposobj);						
					}					
				}
			}		
		}
							
//************************ Update party Balance	for Customer************************	
			if(possales.getPossales().getReceiptType().equals("Credit") && possalesOld.getReceiptType().equals("Credit"))
			{
				List<Ledger> customerList = ledgerDao.searchLedger(possales.getPossales().getCustomerName());
				clBal = customerList.get(0).getClosingTotalBalance();
				clType = customerList.get(0).getClosingTotalType();
				
				if(clType.equalsIgnoreCase("Credit"))
				{
					clBal=zero.subtract(clBal);	
				}
				
				dropClBalanceParty =clBal.add(oldGrandAmt);
				finalClBalanceParty  = dropClBalanceParty.subtract(newGrandAmt);
				
				if(finalClBalanceParty.signum() == -1){
					finalcltype = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(convert);
				}
				else{
					finalcltype = "Debit";						
					
				}
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype,possales.getPossales().getCustomerName());	
				
			}
			
				/* *********************** Cash Ledger Updation *****************************************/
			if(possales.getPossales().getReceiptType().equals("Cash") && possalesOld.getReceiptType().equals("Cash") )
			{			
				List<Ledger> cashList = ledgerDao.searchLedger("Cash Account");
				clBal = cashList.get(0).getClosingTotalBalance();
				clType = cashList.get(0).getClosingTotalType();
				
				if(clType.equalsIgnoreCase("Credit"))
				{
					clBal=zero.subtract(clBal);	
				}
				
				dropClBalanceParty = clBal.add(oldGrandAmt);
				finalClBalanceParty  = dropClBalanceParty.subtract(newGrandAmt);
				
				if(finalClBalanceParty.signum() == -1){
					finalcltype = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(convert);
				}
				else{
					finalcltype = "Debit";						
					  
				}
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty, finalcltype, "Cash Account");	
				
				
				//***********************************  Journal Entry Update for Sales Return Bill Amount ***************************/			
				List<Journal> JournalNewList = journalDao.getJournalUpdateSales("POS Sales Return Payment", "POS"+possales.getPossales().getPosSalesId());
				if(!JournalNewList.isEmpty()){				
					JournalNewList.get(0).setDebitAmount(newGrandAmt);
					JournalNewList.get(0).setCreditAmount(newGrandAmt);
					JournalNewList.get(0).setJournalDate(possales.getPossales().getSalesdate());					
					journalDao.updateJournal(JournalNewList.get(0));
				}		
			}			
			
			
			//****************************** IF receipt type change from Cash to Credit
			if(possalesOld.getReceiptType().equals("Cash") && possales.getPossales().getReceiptType().equals("Credit")){
				
				/******************************** Update Party Balance ********************************/
				List<Ledger> customerList = ledgerDao.searchLedger(possales.getPossales().getCustomerName());
				clBal = customerList.get(0).getClosingTotalBalance();
				clType = customerList.get(0).getClosingTotalType();
				
				if(clType.equalsIgnoreCase("Credit"))
				{
					clBal=zero.subtract(clBal);	
				}
				
				finalClBalanceParty =clBal.subtract(newGrandAmt);
			  				
				if(finalClBalanceParty.signum() == -1){
					finalcltype = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(convert);
				}
				else{
					finalcltype = "Debit";						
					
				}
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype,possales.getPossales().getCustomerName());
				
				
				/******************** Update Cash Book Ledger ***************************/
				List<Ledger> cashList = ledgerDao.searchLedger("Cash Account");
				clBal = cashList.get(0).getClosingTotalBalance();
				clType = cashList.get(0).getClosingTotalType();
				
				if(clType.equalsIgnoreCase("Credit"))
				{
					clBal=zero.subtract(clBal);	
				}
				
				finalClBalanceParty = clBal.add(oldGrandAmt);				
				
				if(finalClBalanceParty.signum() == -1){
					finalcltype = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(convert);
				}
				else{
					finalcltype = "Debit";						
					
				}
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty, finalcltype, "Cash Account");	
				
				
				/* Second Journal Should be deleted  */
				List<Journal> jrnlListPayment = journalDao.getJournalUpdateSales("POS Sales Return Payment", "POS"+salesid);
				if(!jrnlListPayment.isEmpty()) {						
					journalDao.deleteJournal(jrnlListPayment.get(0));
				}
			}
			
			
			
			//****************************** IF receipt type change from Credit To Cash
			if(possalesOld.getReceiptType().equals("Credit") && possales.getPossales().getReceiptType().equals("Cash")){
				
				/******************************** Update Party Balance ********************************/
				List<Ledger> customerList = ledgerDao.searchLedger(possales.getPossales().getCustomerName());
				clBal = customerList.get(0).getClosingTotalBalance();
				clType = customerList.get(0).getClosingTotalType();
				
				if(clType.equalsIgnoreCase("Credit"))
				{
					clBal=zero.subtract(clBal);	
				}
				
				finalClBalanceParty =clBal.add(oldGrandAmt);
			  				
				if(finalClBalanceParty.signum() == -1){
					finalcltype = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(convert);
				}
				else{
					finalcltype = "Debit";						
					
				}
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype,possales.getPossales().getCustomerName());
				
				
				/******************** Update Cash Book Ledger ***************************/
				List<Ledger> cashList = ledgerDao.searchLedger("Cash Account");
				clBal = cashList.get(0).getClosingTotalBalance();
				clType = cashList.get(0).getClosingTotalType();
				
				if(clType.equalsIgnoreCase("Credit"))
				{
					clBal=zero.subtract(clBal);	
				}
				
				finalClBalanceParty = clBal.subtract(newGrandAmt);
				
				
				if(finalClBalanceParty.signum() == -1){
					finalcltype = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(convert);
				}
				else{
					finalcltype = "Debit";						
					
				}
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty, finalcltype, "Cash Account");	
				
				//Journal Entry for Cash ledger
				
				jrnl = new Journal();	
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("POS Sales Return Payment");
				jrnl.setTransactionId("POS"+ possales.getPossales().getPosSalesId()); // New Column added for tracking journal entries
			    jrnl.setJournalDate(possales.getPossales().getSalesdate());
			    
			    //Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(possales.getPossales().getCustomerName());			
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());		
				jrnl.setDebitAccountName(possales.getPossales().getCustomerName());
				
				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName("Cash Account");
				
				jrnl.setDebitAmount(possales.getPossales().getNetAmount());
				jrnl.setCreditAmount(possales.getPossales().getNetAmount());
				jrnl.setNarration("Bill No "+ possales.getPossales().getBillNo());							
				journalDao.insertJournal((Journal) jrnl);	
				
			}
				
			//**************************updating sales ledger***************************************
				List<Ledger> ledgerList = ledgerDao.searchLedger("POS Sales Account");
				clBal = ledgerList.get(0).getClosingTotalBalance();
				clType = ledgerList.get(0).getClosingTotalType();
				
				if(clType.equalsIgnoreCase("Credit"))
				{
					clBal=zero.subtract(clBal);	
				}
				
				dropClBalanceParty =clBal.subtract(oldGrandAmt);
				finalClBalanceParty  = dropClBalanceParty.add(newGrandAmt);
				
				if(finalClBalanceParty.signum() == -1){
					finalcltype = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(convert);
				}
				else{
					finalcltype = "Debit";						
					
				}
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype,"POS Sales Account");
				
		
		
//**************************** Journal Entry Updation ******************************************
			List<Journal> jrnl = journalDao.getJournalUpdateSales("POS Sales Return", "POS"+salesid);
			
			if(!jrnl.isEmpty())
			{
				Journal list=jrnl.get(0); 
				list.setCreditAmount(newGrandAmt);
				list.setDebitAmount(newGrandAmt);
				list.setJournalDate(possales.getPossales().getSalesdate());
				journalDao.updateJournal(list);							
			}			
			
		return new ModelAndView("redirect:possalesList.htm");		
	}
	
		
	@RequestMapping(value = "/possalesreturnList.htm",method=RequestMethod.GET)
	public ModelAndView salesreturnlist(@ModelAttribute("posSalesReturn") POSSalesItem possalesItem)
	{			
		ModelMap model=new ModelMap();
		model.addAttribute("posreturnList");		
		return new ModelAndView("possalesreturnList", model);		
	}
	
	@RequestMapping(value="/possalesreturnList.htm", method=RequestMethod.POST, params="search")
	public ModelAndView getReturnList(@ModelAttribute("posSalesReturn") POSSalesItem posSalesReturn,@RequestParam(value="billNo")String billNo,ModelMap model){
		System.out.println("inside the controller mapping::::");
	//	String data=posSalesDao.getPOSSalesSearchItem(billNo);
		
		model.addAttribute("posreturnList",posSalesDao.getPOSSalesSearchItem(billNo));
		model.addAttribute("possalesId",billNo);		
		return new ModelAndView("possalesreturnList",model);	
	}
	
	//Canceling request mapping				
	@RequestMapping(value="/formPOSsalesreturn.htm",method=RequestMethod.POST, params="cancel") 
	public String cancelForm()
	{
		return "redirect:possalesList.htm";
	}
	
	@RequestMapping(value="/formPOSsalesreturn.htm",method=RequestMethod.POST, params="SRLISTsubmit")
	public ModelAndView transferForm(@RequestParam(value="ids",required=true)String checkedIds,@RequestParam(value="billNo")String POSsalesId,PSales possales,ModelMap model) {
		
		List<POSSales> posSalesList=posSalesDao.getPOSInov(POSsalesId);
		List<POSSalesItem> posSalesItem = new ArrayList<POSSalesItem>();
		String a[] = checkedIds.split(",");
		
		for(String b: a){
			POSSalesItem positemsObj = posSalesDao.getSalesItem(new Integer(b));
			posSalesItem.add(positemsObj);			
		}		
		
		List<POSSalesItem> POSSRDynamicList = new ArrayList<POSSalesItem>();
		POSSRDynamicList.addAll(posSalesItem);
		
		if(POSSRDynamicList.size()!=0){
			POSSalesItem posSRTemp = POSSRDynamicList.get(POSSRDynamicList.size()-1);
			POSSRDynamicList.remove(posSRTemp);
		}
		 
		possales.setListpossalesItem(posSalesItem);	
		possales.setPossales(posSalesList.get(0));
		model.addAttribute("POSsales", possales);
		model.addAttribute("salesItemsList", POSSRDynamicList);
		return new ModelAndView("formPOSsalesreturn",model);		
	}
	
	
	/**@RequestMapping(value="/possalesreturnList.htm",method=RequestMethod.POST, params="SRLISTcheckbox")
	public synchronized String printBarcode(@RequestParam(value="posSalesId",required=true)String posSalesId, PSales possales) 
	{	
		int id=Integer.parseInt(posSalesId);
					
		POSSalesItem positemsObj = posSalesDao.getSalesItem(id);
		String barcodeID = positemsObj.getPosItemCode();
		Integer salesId = positemsObj.getPossales().getPosSalesId();		
		List<POSSales> posQuery = posSalesDao.getPOSsales(salesId);

		posQuery.get(0).setSalesType("POS Sales Return");
		
		posSalesDao.insertPOSSales(posQuery.get(0));

		positemsObj.setPossales(posQuery.get(0));
		//positemsObj.setItemSalesType("POS Sales Return");
		posSalesDao.insertPOSSalesItem(positemsObj);	
		
		postagitemDao.updateSoldTagItem(qty, "Unprinted", barcodeID);//update Tag item
		posstockDao.updatePOSStock(qty, tag_status, barcodeID);//Update stock
	
		************************************  Sales Ledger Update and Journal Entry  *********************************************
		
		System.out.println("the type is" +posQuery.get(0).getReceiptType());
		
		
		BigDecimal billAmount = posQuery.get(0).getNetAmount();
		
		if(posQuery.get(0).getReceiptType().equals("Cash")){
					
			if(posQuery.get(0).getCustomerName().equals("Walk-in")){
				jrnl = new Journal();	
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("POS Sales Return");
				jrnl.setTransactionId("SR"+ posQuery.get(0).getPosSalesId()); // New Column added for tracking journal entries
			    jrnl.setJournalDate(posQuery.get(0).getSalesdate());
				jrnl.setDebitAccountName("Sales Account");
				jrnl.setCreditAccountName("Cash Account");
				jrnl.setDebitAmount(billAmount);
				jrnl.setCreditAmount(billAmount);
				jrnl.setNarration("Cash Received from Walk-in customer.");							
				journalDao.insertJournal((Journal) jrnl);
			}
			else
			{			
				// Journal Entry for Cash ledger
				
				jrnl = new Journal();	
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Payment");
				jrnl.setTransactionId("SR"+ posQuery.get(0).getPosSalesId()); // New Column added for tracking journal entries
			    jrnl.setJournalDate(posQuery.get(0).getSalesdate());
				jrnl.setDebitAccountName(posQuery.get(0).getCustomerName());
				jrnl.setCreditAccountName("Cash Account");
				jrnl.setDebitAmount(billAmount);
				jrnl.setCreditAmount(billAmount);
				jrnl.setNarration("Cash Received");							
				journalDao.insertJournal((Journal) jrnl);
				
				
				// Journal Entry for Sales ledger
				
				jrnl = new Journal();	
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("POS Sales Return");
				jrnl.setTransactionId("SR"+ posQuery.get(0).getPosSalesId()); // New Column added for tracking journal entries
			    jrnl.setJournalDate(posQuery.get(0).getSalesdate());
				jrnl.setDebitAccountName("POS Account");
				jrnl.setCreditAccountName(posQuery.get(0).getCustomerName());
				jrnl.setDebitAmount(billAmount);
				jrnl.setCreditAmount(billAmount);
				jrnl.setNarration("Cash Received");							
				journalDao.insertJournal((Journal) jrnl);

				ledgerName = "POS Account";
				List <Ledger> ledgerListSales = ledgerDao.searchLedger(ledgerName);
				BigDecimal clBalanceSales = ledgerListSales.get(0).getClosingTotalBalance();
				String clTypeSales = ledgerListSales.get(0).getClosingTotalType();
				
				if(clTypeSales.equalsIgnoreCase("Credit")){
					clBalanceSales = ZERO.subtract(clBalanceSales);						
				}
						
				BigDecimal finalClBalanceSales  = clBalanceSales.add(billAmount);				
			
				if (finalClBalanceSales.signum() == -1) {
					finalClType = "Credit";
					finalClBalanceSales = finalClBalanceSales.multiply(CONVERT);
				} else {
					finalClType = "Debit";					
				}
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceSales, finalClType, ledgerName);
						
			
				/************************************ Cash Ledger Update ************************************
			
				ledgerName = "Cash Account";
				List <Ledger> ledgerListCashLedger = ledgerDao.searchLedger(ledgerName);
				BigDecimal clBalanceCashLedger = ledgerListCashLedger.get(0).getClosingTotalBalance();
				String clTypeCashLedger = ledgerListCashLedger.get(0).getClosingTotalType();
				
				if(clTypeCashLedger.equalsIgnoreCase("Credit")){
					clBalanceCashLedger = ZERO.subtract(clBalanceCashLedger);						
				}
						
				BigDecimal finalClBalanceCashLedger = clBalanceCashLedger.subtract(billAmount);
						
				if(finalClBalanceCashLedger.signum() == -1){
					finalClType = "Credit";
					finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
				}else{
						finalClType = "Debit";						
					}
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, finalClType, ledgerName);	
			}
		}
		else{
			/**************************************** Journal Entry for Sales ledger*******************************************
			
			jrnl = new Journal();	
			jrnl.setJournalNO(getJournalNumber(jrnl));
			jrnl.setJournalType("POS Sales Return");
			jrnl.setTransactionId("SR"+ posQuery.get(0).getPosSalesId()); // New Column added for tracking journal entries
		    jrnl.setJournalDate(posQuery.get(0).getSalesdate());
			jrnl.setDebitAccountName("POS Account");
			jrnl.setCreditAccountName(possales.getPossales().getCustomerName());
			jrnl.setDebitAmount(billAmount); 
			jrnl.setCreditAmount(billAmount);   
			jrnl.setNarration("Cash Received");							
			journalDao.insertJournal((Journal) jrnl);
			
			/****************************************** Sales Legger Updation ************************************************
			ledgerName = "POS Account";
			List <Ledger> ledgerListSales = ledgerDao.searchLedger(ledgerName);
			BigDecimal clBalanceSales = ledgerListSales.get(0).getClosingTotalBalance();
			String clTypeSales = ledgerListSales.get(0).getClosingTotalType();
			
			if(clTypeSales.equalsIgnoreCase("Credit")){
				clBalanceSales = ZERO.subtract(clBalanceSales);						
			}
					
			BigDecimal finalClBalanceSales  = clBalanceSales.add(billAmount);				
		
			if (finalClBalanceSales.signum() == -1) {
				finalClType = "Credit";
				finalClBalanceSales = finalClBalanceSales.multiply(CONVERT);
			}
			else {
				finalClType = "Debit";					
			}
			
			ledgerDao.updateLedgerPartyBalance(finalClBalanceSales, finalClType, ledgerName);
			

			/***************************************** Party Ledger Udaption *******************************************
			ledgerName = posQuery.get(0).getCustomerName();
			
			List <Ledger> ledgerListPartyLedger = ledgerDao.searchLedger(ledgerName);
			BigDecimal clBalanceCashLedger = ledgerListPartyLedger.get(0).getClosingTotalBalance();
			String clTypeCashLedger = ledgerListPartyLedger.get(0).getClosingTotalType();
			
			if(clTypeCashLedger.equalsIgnoreCase("Credit")){
				clBalanceCashLedger = ZERO.subtract(clBalanceCashLedger);						
			}
					
			BigDecimal finalClBalanceCashLedger = clBalanceCashLedger.subtract(billAmount);
					
			if(finalClBalanceCashLedger.signum() == -1){
				finalClType = "Credit";
				finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
			}else{
					finalClType = "Debit";						
				}
			
			ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, finalClType, ledgerName);	
		}	
		
		return  "redirect:possalesList.htm";
		
	}	****/	
}
