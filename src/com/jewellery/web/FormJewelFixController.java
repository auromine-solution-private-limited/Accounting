package com.jewellery.web;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import com.jewellery.entity.JewelFix;
import com.jewellery.entity.Journal;
import com.jewellery.entity.Ledger;
import com.jewellery.dao.AccountsDao;
import com.jewellery.dao.JewelFixDao;
import com.jewellery.dao.JournalDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.validator.JewelFixValidator;
import com.jewellery.util.JournalCode;

@Controller
public class FormJewelFixController extends JournalCode {
	
	private JewelFixDao jewelfixDao;
	private LedgerDao ledgerDao;
	private JournalDao journalDao;
	private Journal jrnl;	
	List<String> ledgerGroupCode;
	
	@Autowired
	private JewelFixValidator jewelFixValidator;
	@Autowired
	AccountsDao accountsDao;
	@Autowired
	public FormJewelFixController(JewelFixDao jewelfixDao, LedgerDao ledgerDao, JournalDao journalDao){
		
		this.jewelfixDao = jewelfixDao;
		this.ledgerDao = ledgerDao; 
		this.journalDao = journalDao;	
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
	throws Exception{
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class,null, dateEditor);
	}	
		
//	To Add/Save the record
	
		@RequestMapping(value = "/formjewelfix.htm", method = RequestMethod.POST,params="insert")
		public ModelAndView addData(@ModelAttribute("jewelfix") JewelFix jewelfix, BindingResult result,SessionStatus status, ModelMap model) 
		{
			jewelfix.setOrderNO(getOrderCode());
			jewelFixValidator.validate(jewelfix, result);
					
			model.addAttribute("suppliername", ledgerDao.listallSmithName());
			model.addAttribute("customername", ledgerDao.listallCustomerName());
			
			if(result.hasErrors())
			{
				ModelMap map = new ModelMap();
				map.put("command", jewelfix);
				map.addAttribute("errorType","insertError");
				return new ModelAndView("formjewelfix",map);
			}
			
			jewelfixDao.insertJewelFix(jewelfix);					
			status.setComplete();
			
			
			
			if(jewelfix.getStatus().equalsIgnoreCase("Received") ||  jewelfix.getStatus().equalsIgnoreCase("Delivered")){
				return new ModelAndView("redirect: jewelRepairPreview.htm?jewelrepaiId="+jewelfix.getRepairId()+"&orderStatus="+jewelfix.getStatus(), model);
			}
			
			return new ModelAndView("redirect:jewelfix.htm");
		}

		
		@RequestMapping(value = "/ jewelRepairPreview.htm", method = RequestMethod.GET)
		public ModelAndView pdfVoucherPreview() {
			return new ModelAndView(" jewelRepairPreview");
		}
		
		//to Update the record
		
		@RequestMapping(value = "/formjewelfix.htm", method = RequestMethod.POST, params="update")
		public ModelAndView updateData(@ModelAttribute("jewelfix") JewelFix jewelfix, BindingResult result, ModelMap model){
			
			BigDecimal ZERO = new BigDecimal("0.00");
			BigDecimal CONVERT = new BigDecimal("-1");
			String finalClType = "";
			
			model.addAttribute("suppliername", ledgerDao.listallSmithName());
			model.addAttribute("customername", ledgerDao.listallCustomerName());
			jewelFixValidator.validate(jewelfix, result);
	 		 
			if(result.hasErrors())
			{			
				ModelMap map = new ModelMap();			
				map.put("command", jewelfix);		
				map.addAttribute("errorType","updateError");
				return new ModelAndView("formjewelfix",map);				
			}
			JewelFix jewelOld = jewelfixDao.getJewelFix(jewelfix.getRepairId()); // To get old jewelRepair Object.
				//if(jewelfix.getStatus().equals("Delivered") && jewelOld.getStatus().equals("Received")) {
							
								
				//***************************************  For Same Customer Name Update   ****************************/						
					if(jewelfix.getCustomerName().equals(jewelOld.getCustomerName())) {
						
						/********************** 1.Party Balance Update : Total Cost - Same Customer *********************/
						
						List<Ledger> JewelpartyLedger = ledgerDao.searchLedger(jewelfix.getCustomerName());
						String clTypeParty = JewelpartyLedger.get(0).getClosingTotalType();
						BigDecimal oldClBalanceParty = JewelpartyLedger.get(0).getClosingTotalBalance();
				
						if(clTypeParty.equalsIgnoreCase("Credit")){
							oldClBalanceParty = ZERO.subtract(oldClBalanceParty);						
						}
												
						BigDecimal dropClBalanceParty = oldClBalanceParty.subtract(jewelOld.getTotalCost());
						BigDecimal finalClBalanceParty = dropClBalanceParty.add(jewelfix.getTotalCost());
							
						if(finalClBalanceParty.signum() == -1){
							finalClType = "Credit";
							finalClBalanceParty = finalClBalanceParty.multiply(CONVERT);
						}else{
							finalClType = "Debit";						
						}

						ledgerDao.updateLedgerPartyBalance(finalClBalanceParty, finalClType, jewelOld.getCustomerName());
						
						/*********************  2.Jewel Repair Income : Total Cost - Same Customer **********************/
						
						List <Ledger> JewelRepairIncome = ledgerDao.searchLedger("Job Repair Income");
						BigDecimal oldClBalanceJIncome = JewelRepairIncome.get(0).getClosingTotalBalance();
						String clTypeJIncome = JewelRepairIncome.get(0).getClosingTotalType();
					
						if(clTypeJIncome.equalsIgnoreCase("Credit")){
							oldClBalanceJIncome = ZERO.subtract(oldClBalanceJIncome);						
						}
						
						// Total Cost 	
						BigDecimal dropClBalanceJIncome = oldClBalanceJIncome.add(jewelOld.getTotalCost());
						BigDecimal finalClBalanceJIncome  = dropClBalanceJIncome.subtract(jewelfix.getTotalCost());
						
						if (finalClBalanceJIncome.signum() == -1) {
							finalClType = "Credit";
							finalClBalanceJIncome = finalClBalanceJIncome.multiply(CONVERT);
						} else {
							finalClType = "Debit";					
						}
						
						ledgerDao.updateLedgerPartyBalance(finalClBalanceJIncome, finalClType, "Job Repair Income");
						
						/******************** 3. Journal Entry Update : Total Cost - Same Customer ***********************/
						
						List<Journal> jrnlListjewelTcost = journalDao.getJournalUpdateSales("Revenue","JR"+jewelfix.getRepairId().toString());
						if(!jrnlListjewelTcost.isEmpty()){	
							jrnlListjewelTcost.get(0).setJournalDate(jewelfix.getIssueDate());
							jrnlListjewelTcost.get(0).setDebitAmount(jewelfix.getTotalCost());
							jrnlListjewelTcost.get(0).setCreditAmount(jewelfix.getTotalCost());
							journalDao.updateJournal(jrnlListjewelTcost.get(0));
						}else{
							//Journal Entry For Income
							jrnl = new Journal();			
							jrnl.setJournalNO(getJournalNumber(jrnl));
							jrnl.setJournalType("Revenue");
						    jrnl.setJournalDate(jewelfix.getIssueDate());
						    
						 // Set Account group code as Debit code
						    ledgerGroupCode = ledgerDao.getLedgerAccountCode(jewelfix.getCustomerName());	
						    jrnl.setDebitCode(ledgerGroupCode.get(0).toString());						    
							jrnl.setDebitAccountName(jewelfix.getCustomerName());
							
							// Set Account group code as Credit code
						    ledgerGroupCode = ledgerDao.getLedgerAccountCode("Job Repair Income");
						    jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
							jrnl.setCreditAccountName("Job Repair Income");
							
							jrnl.setDebitAmount(jewelfix.getTotalCost());
							jrnl.setCreditAmount(jewelfix.getTotalCost());
							jrnl.setTransactionId("JR"+jewelfix.getRepairId().toString());
							jrnl.setNarration("Bill No "+jewelfix.getRepairId());	
							journalDao.insertJournal((Journal) jrnl);	
						}
						
					}
					
				//***************************************  For Same Smith Name Update   ****************************/
					
					if(jewelfix.getSmithName().equals(jewelOld.getSmithName())) {
						
						/********************** 1.Party Balance Update : Smith Cost - Same Customer *********************/
						
						List<Ledger> JewelpartyLedgerS = ledgerDao.searchLedger(jewelfix.getSmithName());
						String clTypePartyS = JewelpartyLedgerS.get(0).getClosingTotalType();
						BigDecimal oldClBalancePartyS = JewelpartyLedgerS.get(0).getClosingTotalBalance();
				
						if(clTypePartyS.equalsIgnoreCase("Credit")){
							oldClBalancePartyS = ZERO.subtract(oldClBalancePartyS);						
						}
						
						BigDecimal finalClBalPartyS = oldClBalancePartyS.add(jewelOld.getSmithCost());
						finalClBalPartyS = finalClBalPartyS.subtract(jewelfix.getSmithCost());
							
						if(finalClBalPartyS.signum() == -1){
							finalClType = "Credit";
							finalClBalPartyS = finalClBalPartyS.multiply(CONVERT);
						}else{
							finalClType = "Debit";						
						}
						
						ledgerDao.updateLedgerPartyBalance(finalClBalPartyS, finalClType, jewelOld.getSmithName());
						
						/*********************  2.Jewel Repair Expense : Smith Cost - Same Customer **********************/
						
						List <Ledger> JewelRepairExpense = ledgerDao.searchLedger("Job Repair Expense");
						BigDecimal oldClBalanceJExpense = JewelRepairExpense.get(0).getClosingTotalBalance();
						String clTypeJExpense = JewelRepairExpense.get(0).getClosingTotalType();
						
						if(clTypeJExpense.equalsIgnoreCase("Credit")){
							oldClBalanceJExpense = ZERO.subtract(oldClBalanceJExpense);						
						}
						
						// Smith Cost 
						BigDecimal finalClBalJExpense = oldClBalanceJExpense.subtract(jewelOld.getSmithCost());
						finalClBalJExpense = finalClBalJExpense.add(jewelfix.getSmithCost());
						
						if (finalClBalJExpense.signum() == -1) {
							finalClType = "Credit";
							finalClBalJExpense = finalClBalJExpense.multiply(CONVERT);
						} else {
							finalClType = "Debit";					
						}
						
						ledgerDao.updateLedgerPartyBalance(finalClBalJExpense, finalClType, "Job Repair Expense");
						
						/******************** 3. Journal Entry Update : Smith Cost - Same Customer ***********************/
						
						List<Journal> jrnlListjewelScost = journalDao.getJournalUpdateSales("Expense","JR"+jewelfix.getRepairId().toString());
						if(!jrnlListjewelScost.isEmpty()){	
							jrnlListjewelScost.get(0).setJournalDate(jewelfix.getIssueDate());
							jrnlListjewelScost.get(0).setDebitAmount(jewelfix.getSmithCost());
							jrnlListjewelScost.get(0).setCreditAmount(jewelfix.getSmithCost());
							journalDao.updateJournal(jrnlListjewelScost.get(0));
						}else{
							//Journal Entry for Expense
							jrnl = new Journal();			
							jrnl.setJournalNO(getJournalNumber(jrnl));
							jrnl.setJournalType("Expense");
						    jrnl.setJournalDate(jewelfix.getIssueDate());
						    
						    // Set Account group code as Debit code
						    ledgerGroupCode = ledgerDao.getLedgerAccountCode("Job Repair Expense");	
						    jrnl.setDebitCode(ledgerGroupCode.get(0).toString());			
							jrnl.setDebitAccountName("Job Repair Expense");
							
							// Set Account group code as Credit code
						    ledgerGroupCode = ledgerDao.getLedgerAccountCode(jewelfix.getSmithName());
						    jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
							jrnl.setCreditAccountName(jewelfix.getSmithName());
							
							jrnl.setDebitAmount(jewelfix.getSmithCost());
							jrnl.setCreditAmount(jewelfix.getSmithCost());
							jrnl.setTransactionId("JR"+jewelfix.getRepairId().toString());
							jrnl.setNarration("Bill No "+jewelfix.getRepairId());							
							journalDao.insertJournal((Journal) jrnl);
						}
					}												
					
					
				//***************************************  For Different Customer Name Update   ****************************/
					
				if(!jewelfix.getCustomerName().equals(jewelOld.getCustomerName())) {
					
					/***************************** 1.Party Balance Update : Total Cost - Different Customer **************************/

					/** From Total Cost Party 1 */
					List <Ledger> fromTcostParty1 = ledgerDao.searchLedger(jewelOld.getCustomerName());
					String clTypeParty1 = fromTcostParty1.get(0).getClosingTotalType();
					BigDecimal fromPartyClBal1 = fromTcostParty1.get(0).getClosingTotalBalance();
					String finalClTypeParty1 = "";
					String finalClTypeParty2 = "";
					
					/** To Total Cost 2 Party */
					List <Ledger> toTcostParty2 = ledgerDao.searchLedger(jewelfix.getCustomerName());
					String clTypeParty2 = toTcostParty2.get(0).getClosingTotalType();
					BigDecimal toPartyClBal2 = toTcostParty2.get(0).getClosingTotalBalance();

					if(clTypeParty1.equalsIgnoreCase("Credit")){
						fromPartyClBal1 = ZERO.subtract(fromPartyClBal1);						
					}
					if(clTypeParty2.equalsIgnoreCase("Credit")){
						toPartyClBal2 = ZERO.subtract(toPartyClBal2);						
					}
					
					BigDecimal finalClBaltCostParty1 = fromPartyClBal1.subtract(jewelOld.getTotalCost());
					BigDecimal finalClBaltCostParty2 = toPartyClBal2.add(jewelfix.getTotalCost());
					
													
					if(finalClBaltCostParty1.signum() == -1){
						finalClTypeParty1 = "Credit";
						finalClBaltCostParty1 = finalClBaltCostParty1.multiply(CONVERT);
					}else{
						finalClTypeParty1 = "Debit";						
					}
					
					if(finalClBaltCostParty2.signum() == -1){
						finalClTypeParty2 = "Credit";
						finalClBaltCostParty2 = finalClBaltCostParty2.multiply(CONVERT);
					}else{
						finalClTypeParty2 = "Debit";						
					}
					
					ledgerDao.updateLedgerPartyBalance(finalClBaltCostParty1, finalClTypeParty1, jewelOld.getCustomerName());
					ledgerDao.updateLedgerPartyBalance(finalClBaltCostParty2, finalClTypeParty2, jewelfix.getCustomerName());
					
					/***************************** 2. Jewel Repair Income Ledger Update : Total Cost - Different Customer **************************/
					
					List <Ledger> JewelRepairIncomeDC = ledgerDao.searchLedger("Job Repair Income");
					BigDecimal oldClBalanceJIncomeDC = JewelRepairIncomeDC.get(0).getClosingTotalBalance();
					String clTypeJIncomeDC = JewelRepairIncomeDC.get(0).getClosingTotalType();
				
					if(clTypeJIncomeDC.equalsIgnoreCase("Credit")){
						oldClBalanceJIncomeDC = ZERO.subtract(oldClBalanceJIncomeDC);						
					}
					
					// Total Cost 	
					BigDecimal dropClBalanceJIncomeDC = oldClBalanceJIncomeDC.subtract(jewelOld.getTotalCost());
					BigDecimal finalClBalanceJIncomeDC = dropClBalanceJIncomeDC.add(jewelfix.getTotalCost());
					
					if (finalClBalanceJIncomeDC.signum() == -1) {
						finalClType = "Credit";
						finalClBalanceJIncomeDC = finalClBalanceJIncomeDC.multiply(CONVERT);
					} else {
						finalClType = "Debit";					
					}
					
					ledgerDao.updateLedgerPartyBalance(finalClBalanceJIncomeDC, finalClType, "Job Repair Income");
					
					/***************************** 3. Journal Update : Total Cost - Different Customer **************************/
					
					List<Journal> jrnlListjewelTcostDC = journalDao.getJournalUpdateSales("Revenue","JR"+jewelfix.getRepairId().toString());
					if(!jrnlListjewelTcostDC.isEmpty()){	
						jrnlListjewelTcostDC.get(0).setJournalDate(jewelfix.getIssueDate());
						jrnlListjewelTcostDC.get(0).setDebitAmount(jewelfix.getTotalCost());
						jrnlListjewelTcostDC.get(0).setCreditAmount(jewelfix.getTotalCost());
						jrnlListjewelTcostDC.get(0).setDebitAccountName(jewelfix.getCustomerName());
						journalDao.updateJournal(jrnlListjewelTcostDC.get(0));
					}else{
						//Journal Entry For Income
						jrnl = new Journal();			
						jrnl.setJournalNO(getJournalNumber(jrnl));
						jrnl.setJournalType("Revenue");
					    jrnl.setJournalDate(jewelfix.getIssueDate());
					    
					    // Set Account group code as Debit code
					    ledgerGroupCode = ledgerDao.getLedgerAccountCode(jewelfix.getCustomerName());	
					    jrnl.setDebitCode(ledgerGroupCode.get(0).toString());		
						jrnl.setDebitAccountName(jewelfix.getCustomerName());
						
						// Set Account group code as Credit code
					    ledgerGroupCode = ledgerDao.getLedgerAccountCode("Job Repair Income");
					    jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
						jrnl.setCreditAccountName("Job Repair Income");
						
						jrnl.setDebitAmount(jewelfix.getTotalCost());
						jrnl.setCreditAmount(jewelfix.getTotalCost());
						jrnl.setTransactionId("JR"+jewelfix.getRepairId().toString());
						jrnl.setNarration("Bill No "+jewelfix.getRepairId());	
						journalDao.insertJournal((Journal) jrnl);	
					}
					
				}
				
			//***************************************  For Different Smith Name Update   ****************************/
							
			if(!jewelfix.getSmithName().equals(jewelOld.getSmithName())) {
				
					/***************************** 1.Party Balance Update : Smith Cost - Different Customer ***********/
									
					/** From Smith Cost Party 1 */
					List <Ledger> ledgerListPartySmith1 = ledgerDao.searchLedger(jewelOld.getSmithName());
					String clTypePartySmith1 = ledgerListPartySmith1.get(0).getClosingTotalType();
					BigDecimal oldClBalancePartySmith1 = ledgerListPartySmith1.get(0).getClosingTotalBalance();
					String finalClTypePartySmith1 = "";
					String finalClTypePartySmith2 = "";
					
					/** To Smith Cost Party 2 */
					List <Ledger> ledgerListPartySmith2 = ledgerDao.searchLedger(jewelfix.getSmithName());
					String clTypePartySmith2 = ledgerListPartySmith2.get(0).getClosingTotalType();
					BigDecimal oldClBalancePartySmith2 = ledgerListPartySmith2.get(0).getClosingTotalBalance();

					if(clTypePartySmith1.equalsIgnoreCase("Credit")){
						oldClBalancePartySmith1 = ZERO.subtract(oldClBalancePartySmith1);						
					}
					if(clTypePartySmith2.equalsIgnoreCase("Credit")){
						oldClBalancePartySmith2 = ZERO.subtract(oldClBalancePartySmith2);						
					}
					
					BigDecimal finalClBalancePartySmith1 = oldClBalancePartySmith1.add(jewelOld.getSmithCost());
					BigDecimal finalClBalancePartySmith2 = oldClBalancePartySmith2.subtract(jewelfix.getSmithCost());
																		
					if(finalClBalancePartySmith1.signum() == -1){
						finalClTypePartySmith1 = "Credit";
						finalClBalancePartySmith1 = finalClBalancePartySmith1.multiply(CONVERT);
					}else{
						finalClTypePartySmith1 = "Debit";						
					}
					
					if(finalClBalancePartySmith2.signum() == -1){
						finalClTypePartySmith2 = "Credit";
						finalClBalancePartySmith2 = finalClBalancePartySmith2.multiply(CONVERT);
					}else{
						finalClTypePartySmith2 = "Debit";						
					}
					
					ledgerDao.updateLedgerPartyBalance(finalClBalancePartySmith1, finalClTypePartySmith1, jewelOld.getSmithName());
					ledgerDao.updateLedgerPartyBalance(finalClBalancePartySmith2, finalClTypePartySmith2, jewelfix.getSmithName());
					
					/***************************** 2. Jewel Repair Expense Ledger Update : Smith Cost - Different Customer **************************/
					
					List <Ledger> JewelRepairExpenseDC = ledgerDao.searchLedger("Job Repair Expense");
					BigDecimal oldClBalanceJExpenseDC = JewelRepairExpenseDC.get(0).getClosingTotalBalance();
					String clTypeJExpenseDC = JewelRepairExpenseDC.get(0).getClosingTotalType();
					
					if(clTypeJExpenseDC.equalsIgnoreCase("Credit")){
						oldClBalanceJExpenseDC = ZERO.subtract(oldClBalanceJExpenseDC);						
					}
					
					// Smith Cost 
					BigDecimal finalClBalJExpenseDC = oldClBalanceJExpenseDC.subtract(jewelOld.getSmithCost());
					finalClBalJExpenseDC = finalClBalJExpenseDC.add(jewelfix.getSmithCost());
					
					if (finalClBalJExpenseDC.signum() == -1) {
						finalClType = "Credit";
						finalClBalJExpenseDC = finalClBalJExpenseDC.multiply(CONVERT);
					} else {
						finalClType = "Debit";					
					}
					
					ledgerDao.updateLedgerPartyBalance(finalClBalJExpenseDC, finalClType, "Job Repair Expense");
					
					/***************************** 3. Journal Update : Smith Cost - Different Customer **************************/
					
					List<Journal> jrnlListjewelScostDC = journalDao.getJournalUpdateSales("Expense","JR"+jewelfix.getRepairId().toString());
					if(!jrnlListjewelScostDC.isEmpty()){	
						jrnlListjewelScostDC.get(0).setJournalDate(jewelfix.getIssueDate());
						jrnlListjewelScostDC.get(0).setDebitAmount(jewelfix.getSmithCost());
						jrnlListjewelScostDC.get(0).setCreditAmount(jewelfix.getSmithCost());
						jrnlListjewelScostDC.get(0).setCreditAccountName(jewelfix.getSmithName());
						journalDao.updateJournal(jrnlListjewelScostDC.get(0));
					}else{
						//Journal Entry for Expense
						jrnl = new Journal();			
						jrnl.setJournalNO(getJournalNumber(jrnl));
						jrnl.setJournalType("Expense");
					    jrnl.setJournalDate(jewelfix.getIssueDate());
					    
					 // Set Account group code as Debit code
					    ledgerGroupCode = ledgerDao.getLedgerAccountCode("Job Repair Expense");	
					    jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
						jrnl.setDebitAccountName("Job Repair Expense");
						
						// Set Account group code as Credit code
					    ledgerGroupCode = ledgerDao.getLedgerAccountCode(jewelfix.getSmithName());
					    jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
						jrnl.setCreditAccountName(jewelfix.getSmithName());
						
						jrnl.setDebitAmount(jewelfix.getSmithCost());
						jrnl.setCreditAmount(jewelfix.getSmithCost());
						jrnl.setTransactionId("JR"+jewelfix.getRepairId().toString());
						jrnl.setNarration("Bill No "+jewelfix.getRepairId());							
						journalDao.insertJournal((Journal) jrnl);
					}
					
				}
			jewelfixDao.updateJewelFix(jewelfix); // Jewel Repair Update
		//}			
			
			
		/**if(jewelfix.getStatus().equals("Delivered") && jewelOld.getStatus().equals("Received"))
		{
			model.addAttribute("suppliername", ledgerDao.listallSmithName());
			model.addAttribute("customername", ledgerDao.listallCustomerName());
			jewelFixValidator.validate(jewelfix, result);
	 		 
			if(result.hasErrors())
			{			
				ModelMap map = new ModelMap();			
				map.put("command", jewelfix);		
				map.addAttribute("errorType","updateError");
				return new ModelAndView("formjewelfix",map);				
			}
			
			jewelfixDao.updateJewelFix(jewelfix);
			
			//Update record for journal, stock and cash and party balance
			
			BigDecimal clBal = new BigDecimal("0.0");
			String clType = null;
			String partyName = jewelfix.getCustomerName();
		 	String smithName = jewelfix.getSmithName();
			BigDecimal closingAmount = jewelfix.getTotalCost();		 	
		 	BigDecimal smithAmount = jewelfix.getSmithCost();
		 	String closingType = "Debit";	 
			String closingTyp = "Credit";
			
			
			//Journal Entry for Customer and Supplier
			 
				// Update Customer balances
				
				List<Ledger> customerList = ledgerDao.searchLedger(partyName);			
					clBal = customerList.get(0).getClosingTotalBalance();	
					clType = customerList.get(0).getClosingTotalType();				
			 	
				if(clType.equals("Debit")){
			 		ledgerDao.updatePartyBalance(closingAmount, closingType, partyName);		 		
			 	}
			 	else if(clType.equals("Credit")){	
			 		if(clBal.compareTo(closingAmount) == 1){
			 			ledgerDao.updateCreditPartyBalance(closingAmount, closingTyp, partyName);
			 		}
			 		else{
			 			ledgerDao.updateCrPartyBalance(closingAmount, closingType, partyName);
			 		}
			 	}	
				
				//Update Supplier Balance
				
				List <Ledger> supplierList = ledgerDao.searchLedger(smithName);		
				clBal = supplierList.get(0).getClosingTotalBalance();	
				clType = supplierList.get(0).getClosingTotalType();					 				 
			 	
			 	if(clType.equals("Credit")){			 		
			 		ledgerDao.updatePartyBalance(smithAmount, closingTyp, smithName);			 		
			 	}
			 	else{
			 		if(clBal.compareTo(closingAmount) == 1){			 			
			 			ledgerDao.updateCreditPartyBalance(smithAmount, closingType, smithName);
			 		}
			 		else{
			 			ledgerDao.updateCrPartyBalance(smithAmount, closingTyp, smithName);
			 		}	
			 	}
				
				//Journal Entry For Income
				jrnl = new Journal();			
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Revenue");
			    jrnl.setJournalDate(jewelfix.getIssueDate());
				jrnl.setDebitAccountName(partyName);
				jrnl.setCreditAccountName("Job Repair Income");
				jrnl.setDebitAmount(closingAmount);
				jrnl.setCreditAmount(closingAmount);
				jrnl.setTransactionId("JR"+jewelfix.getRepairId().toString());
				jrnl.setNarration("Bill No " + jewelfix.getOrderNO());	
				journalDao.insertJournal((Journal) jrnl);				
         
				//Journal Entry for Expense
				jrnl = new Journal();			
				jrnl.setJournalNO(getJournalCode(jrnl));
				jrnl.setJournalType("Expense");
			    jrnl.setJournalDate(jewelfix.getIssueDate());
				jrnl.setDebitAccountName("Job Repair Expense");
				jrnl.setCreditAccountName(smithName);
				jrnl.setDebitAmount(smithAmount);
				jrnl.setCreditAmount(smithAmount);
				jrnl.setTransactionId("JR"+jewelfix.getRepairId().toString());
				jrnl.setNarration("Bill No " + jewelfix.getOrderNO());	
				journalDao.insertJournal((Journal) jrnl);
			}	**/		
			
			
			if(jewelfix.getStatus().equalsIgnoreCase("Delivered")){
				return new ModelAndView("redirect: jewelRepairPreview.htm?jewelrepaiId="+jewelfix.getRepairId()+"&orderStatus="+jewelfix.getStatus(), model);
			}
			
			return new ModelAndView("redirect:jewelfix.htm");	
			
		}		
		
		//Redirect the form for update
		@RequestMapping(value = "/formjewelfixing.htm", method = RequestMethod.GET)
		public String getUpdate(@RequestParam(value="repairId", required=true) Integer repairId, @ModelAttribute("jewelfix") JewelFix jewelfix, Model model) 
		{
			model.addAttribute("jewelfix", jewelfixDao.getJewelFix(repairId));			  
			model.addAttribute("suppliername", ledgerDao.listallSmithName());
			model.addAttribute("customername", ledgerDao.listallCustomerName());	 		 
			return "formjewelfix";
		}
		
		//listing jewel repair details
		@RequestMapping(value="/jewelfix")
		public String listJewelFix(Map<String, Object> map){		
			List<JewelFix> jewelFixList = jewelfixDao.listJewelFix();			
			((ModelMap) map).addAttribute("jewelFixList", jewelFixList);
			return "jewelfix";
		}	
		
				//bind the smithname and customer name
				@RequestMapping(value="/formjewelfix", method=RequestMethod.GET)
				public String bindNames(@ModelAttribute("jewelfix") JewelFix jewelfix, Model model){
					
					List<?> ledgerlist, customerlist;
					
					ledgerlist = ledgerDao.listallSmithName(); 
					customerlist = ledgerDao.listallCustomerName();			
									    
				    model.addAttribute("suppliername", ledgerlist);
					model.addAttribute("customername", customerlist);	
					model.addAttribute("orderNo", getOrderCode());			
				    
					return "formjewelfix";
				}
				
				public String getOrderCode(){
					
					String orderCodeNo = "OR10000";	   
					BigInteger found = jewelfixDao.getOrderCode();
					String orderNOList = "OR"+found; 
					
					if(found != null){
						orderCodeNo = orderNOList;
					}
					
				    String splitNo = orderCodeNo.substring(2);
				    int num = Integer.parseInt(splitNo);   
				    num++;        
				    String number = Integer.toString(num);        
				    String lastCode = orderCodeNo.substring(0, 2) + number;
				    
				    return lastCode;
				}
		
				//Canceling request mapping				
				@RequestMapping(value="/formjewelfix.htm",method=RequestMethod.POST, params="cancel") 
				public String cancelForm()
				{
					return "redirect:jewelfix.htm";
				}
}

