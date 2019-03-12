package com.jewellery.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.jewellery.dao.AccountsDao;
import com.jewellery.dao.JournalDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.entity.Accounts;
import com.jewellery.entity.Journal;
import com.jewellery.entity.Ledger;
import com.jewellery.util.JournalCode;
import com.jewellery.validator.LedgerValidator;

@Controller
public class FormSupplierController extends JournalCode {
	@Autowired
	private AccountsDao accountsDao;
	
	@Autowired
	private LedgerDao ledgerDao;
	
	@Autowired 
	private LedgerValidator ledgerValidator;
	
	@Autowired
	private JournalDao journalDao;
	
	Journal jrnl;		

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	

	// redirect to create new SUPPLIER jsp page.
	
	@RequestMapping(value = "/newSupplier", method = RequestMethod.GET)
	public ModelAndView newForm(Ledger ledger) 
	{
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("command", ledger);
		return new ModelAndView("formsupplier", model);
		// directed to blank formsupplier.jsp page.

	}
	
	// to view SUPPLIER details (formbackingObject).
	
		@RequestMapping(value = "/viewSupplier", method = RequestMethod.GET)
		public ModelAndView showForm(@ModelAttribute("ledger") Ledger ledger,@RequestParam(value = "ledgerId", required = false) Integer ledgerId,ModelMap model) {
			ledger = ledgerDao.getLedger(ledgerId);
			model.addAttribute("ledger", ledger);
			return new ModelAndView("formsupplier");
			
			// directed to formSupplier.jsp	page with details.
		}

		// for add new SUPPLIER detail.
		
		@RequestMapping(value = "/Supplier", method = RequestMethod.POST, params = "insert")
		public ModelAndView add(@ModelAttribute("ledger") Ledger ledg, BindingResult result,SessionStatus status) {
			ledgerValidator.validate(ledg, result);
			
			if (result.hasErrors()) {
				ModelMap map = new ModelMap();
				map.put("command", ledg);
				map.addAttribute("errorType","insertError");
				return new ModelAndView("formsupplier", map);
			}
			ledg.setClosingTotalBalance(ledg.getOpTotalBalance());
			ledg.setClosingTotalType(ledg.getOpTotalType());
			List<String> accountGroupCode=accountsDao.getAccountGroupCode("Sundry Creditors");
			String groupCode=accountGroupCode.get(0).toString();
			ledg.setAccountGroupCode(groupCode);
			Accounts acct = accountsDao.getAccounts(17);
			acct.getLedgers().add(ledg);
			accountsDao.insertAccounts(acct);
			status.setComplete();
			
			//Journal Entry For the Opening Balance
			
				jrnl = new Journal();
				List<String> ledgerCode=ledgerDao.getLedgerGroupCode(ledg.getAccountGroup());
				String journalCode=ledgerCode.get(0).toString();
				jrnl.setCreditCode(journalCode);
				jrnl.setDebitCode(journalCode);
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Opening Balance");
				jrnl.setTransactionId("L"+ledg.getLedgerId()); 
			    jrnl.setJournalDate(ledg.getLedgerDate());
			    if(ledg.getOpeningType().equals("Debit")){
					jrnl.setDebitAccountName(ledg.getLedgerName());
					jrnl.setCreditAccountName("Opening Balance");
			    }
			    else{
					jrnl.setDebitAccountName("Opening Balance");
					jrnl.setCreditAccountName(ledg.getLedgerName());			
				}
			    
				jrnl.setDebitAmount(ledg.getOpeningBalance());
				jrnl.setCreditAmount(ledg.getOpeningBalance()); 
				jrnl.setNarration("");							
				journalDao.insertJournal((Journal) jrnl);					
			
			return new ModelAndView("redirect:accountsledger2.htm?accountId=17");

		}
		
		// for update SUPPLIER detail.
		
		@RequestMapping(value = "/Supplier", method = RequestMethod.POST, params = "update")
		public ModelAndView update(@ModelAttribute("ledger") Ledger ledg, BindingResult result,SessionStatus status) {

			ledgerValidator.validate(ledg, result);
			
			if (result.hasErrors()) {
				ModelMap map = new ModelMap();
				map.put("command", ledg);
				map.addAttribute("errorType","updateError");
				return new ModelAndView("formsupplier", map);
			}
			
			/**To Update Supplier Opening and Closing Balance*/			

			Integer supplierId = ledg.getLedgerId(); 
			String finalClType; 
			BigDecimal finalClBalanceParty;
			
			/** Getting old party balance*/
			BigDecimal ZERO = new BigDecimal("0");
			BigDecimal CONVERT = new BigDecimal("-1");
			
			Ledger ledgerObj = ledgerDao.getLedger(supplierId);
			BigDecimal old_OpBal = ledgerObj.getOpeningBalance();
			
			String old_OpType = ledgerObj.getOpeningType();			
			String new_OpType = ledg.getOpeningType();					
			BigDecimal new_OpBal = ledg.getOpeningBalance();
			
			String ledger_name = ledg.getLedgerName();			
						
			/** Party Balance Update */
				
				List <Ledger> ledgerListParty = ledgerDao.searchLedger(ledg.getLedgerName());
				String clTypeParty = ledgerListParty.get(0).getClosingTotalType();
				
				BigDecimal clBalanceParty = ledgerListParty.get(0).getClosingTotalBalance();				
			
				if(clTypeParty.equalsIgnoreCase("Credit")){
					clBalanceParty = ZERO.subtract(clBalanceParty);				
				}
				
				if(old_OpType.equalsIgnoreCase("Credit")){
					old_OpBal = ZERO.subtract(old_OpBal);
				}
				
				if(new_OpType.equals("Credit")){
					new_OpBal = ZERO.subtract(new_OpBal);
				}
				
				BigDecimal dropClBalanceParty = clBalanceParty.subtract(old_OpBal);				
				finalClBalanceParty  = dropClBalanceParty.add(new_OpBal);
													
				if(finalClBalanceParty.signum() == -1){
					finalClType = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(CONVERT);				
				}
				else{
					finalClType = "Debit";						
				}					
										
			ledgerDao.updateLedger((Ledger) ledg);
			Accounts acct = accountsDao.getAccounts(17); 
			// to update in	 accounts_ledgerstable
			acct.getLedgers().add(ledg);
			accountsDao.insertAccounts(acct);
			
			ledgerDao.updateLedgerPartyBalance(finalClBalanceParty, finalClType, ledger_name);
			
			/* Journal Update */
			List<Journal> jrnlLedgerList = journalDao.getJournalUpdateSales("Opening Balance","L"+ledg.getLedgerId().toString());						
			
			if(!jrnlLedgerList.isEmpty()){	
				jrnlLedgerList.get(0).setJournalDate(ledg.getLedgerDate());
		  		jrnlLedgerList.get(0).setDebitAmount(ledg.getOpeningBalance());
				jrnlLedgerList.get(0).setCreditAmount(ledg.getOpeningBalance());	
				
				if(ledg.getOpeningType().equals("Debit")){
					jrnlLedgerList.get(0).setDebitAccountName(ledg.getLedgerName());
					jrnlLedgerList.get(0).setCreditAccountName("Opening Balance");
				}
				else{
					jrnlLedgerList.get(0).setDebitAccountName("Opening Balance");
					jrnlLedgerList.get(0).setCreditAccountName(ledg.getLedgerName());
				}
				
				journalDao.updateJournal(jrnlLedgerList.get(0));
			}
			
			
			status.setComplete();
			
			return new ModelAndView("redirect:accountsledger2.htm?accountId=17");
		}
		
		// cancel button to redirect to SUPPLIER list page
		
		@RequestMapping(value = "/Supplier", method = RequestMethod.POST, params = "cancel")
		public ModelAndView cancelForm(Ledger ledger) {
			return new ModelAndView("redirect:accountsledger2.htm?accountId=17");

		}
		
	//this is to map the accountsledger2 and list them
			@RequestMapping(value="/accountsledger2",method=RequestMethod.GET)
			public  ModelAndView views(@ModelAttribute("show") Ledger ledger,Integer accountId){
				Map<String,Object> model=new HashMap<String,Object>();
				 model.put("accounts", accountsDao.getAccounts(17));
					
				ModelAndView mv=new ModelAndView("accountsledger2",model);			
				return mv;
				// this will return the list of  suplier ledger in each account type.
			}

		
		
		//delete button to delete SUPPLIER record 
	/*	
		 @SuppressWarnings("rawtypes")
		 @RequestMapping(value="/Supplier",method=RequestMethod.POST,params="delete")
		 public ModelAndView deletesupplier(@RequestParam(value="ledgerId",required=false) Integer ledgerId,@ModelAttribute("ledger") Ledger ledg, BindingResult result,SessionStatus status)
		 {
		 Accounts acct = accountsDao.getAccounts(17);
		 for(Iterator itr = acct.getLedgers().iterator();itr.hasNext();)
		 {
			 ledg = (Ledger) itr.next();
			 if(ledg.getLedgerId().intValue() == ledgerId.intValue() )
			 {
				 acct.getLedgers().remove(ledg); 
				 accountsDao.updateAccounts(acct);
				 ledgerDao.deleteLedger(ledg);
				 break;
			}
		}
		 status.setComplete(); 
		 return new ModelAndView("redirect:accountsledger2.htm?accountId=17");
	  }
	  */
		
}

	
	
	