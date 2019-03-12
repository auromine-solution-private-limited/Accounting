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
import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.JournalDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.dao.PostagItemDao;
import com.jewellery.dao.RateMasterDao;
import com.jewellery.dao.SalesOrderDao;
import com.jewellery.entity.Accounts;
import com.jewellery.entity.Journal;
import com.jewellery.entity.Ledger;
import com.jewellery.util.JournalCode;
import com.jewellery.validator.FormLedgerValidator;

//This is the Controller for FormLedger
@Controller

public class FormLedgerController extends JournalCode{

	private AccountsDao accountsDao;
	private LedgerDao ledgerDao;
	private RateMasterDao ratemasterDao;
	private SalesOrderDao salesorderDao;
	private ItemMasterDao itemmasterDao;
	private JournalDao  journalDao;
	Journal jrnl;
	@Autowired
	public PostagItemDao postagItemDao;
	@Autowired
	private FormLedgerValidator validator;

	@Autowired
	public FormLedgerController(AccountsDao accountsDao, LedgerDao ledgerDao, RateMasterDao rateMasterDao, SalesOrderDao salesOrderDao, ItemMasterDao itemMasterDao, JournalDao  journalDao) {
		this.accountsDao = accountsDao;
		this.ledgerDao = ledgerDao;
		this.ratemasterDao = rateMasterDao;
		this.salesorderDao = salesOrderDao;
		this.itemmasterDao = itemMasterDao;
		this.journalDao = journalDao;
	}	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
		
	@RequestMapping(method = RequestMethod.GET, value="/formledger.htm")
	public ModelAndView showForm(Ledger ledger) {
		ModelMap model = new ModelMap();
		model.put("boardrate", ratemasterDao.searchRateMaster());
		model.put("Pendingorder", salesorderDao.SalesOrderPending());
		model.put("message", salesorderDao.SalesOrderPending().size());
		model.put("SLCount", itemmasterDao.lowMetalStockList().size());
		model.put("stocklist", itemmasterDao.lowMetalStockList());
		model.addAttribute("posStocklist",postagItemDao.POSlowItemStockList());
		model.addAttribute("SLCount",postagItemDao.POSlowItemStockList().size());
		model.put("command", ledger);
		ModelAndView mv = new ModelAndView("formledger", model);	
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/formledger.htm",params="insert")
	public ModelAndView insert(Integer accountId, @ModelAttribute("ledgerId") Ledger ledger, BindingResult result,SessionStatus status)
				throws Exception {
		
	//BigDecimal closingBalance = ledger.getClosingTotalBalance();
	//String closingType = ledger.getClosingTotalType();
	//String accountGroup = ledger.getAccountGroup();
			
	
		validator.validate(ledger, result);
		ModelMap map = new ModelMap();
		if (result.hasErrors()) {
		
			map.put("boardrate", ratemasterDao.searchRateMaster());
			map.put("Pendingorder", salesorderDao.SalesOrderPending());
			map.put("message", salesorderDao.SalesOrderPending().size());
			map.put("SLCount", itemmasterDao.Itemstocklist().size());
			map.put("stocklist", itemmasterDao.Itemstocklist());
			map.put("accountGroup", ledger.getAccountGroup());
			map.put("errorType", "insertError");			
			map.put("command", ledger);
			return new ModelAndView("formledger",map);				
		}
		
		ledger.setClosingTotalBalance(ledger.getOpeningBalance());
		ledger.setClosingTotalType(ledger.getOpeningType());
		String groupCode;
		if(ledger.getAccountGroup().equals("Profit And Loss Account"))
		{
			List<String> accountGroupCode=accountsDao.getAccountGroupCode("Profit & Loss Account");
			groupCode=accountGroupCode.get(0).toString();
			ledger.setAccountGroupCode(groupCode);
		}
		else
		{
			List<String> accountGroupCode=accountsDao.getAccountGroupCode(ledger.getAccountGroup());
			groupCode=accountGroupCode.get(0).toString();
			ledger.setAccountGroupCode(groupCode);
		}
		
		//Accounts acct = accountsDao.getAccounts(accountId);
		//acct.getLedgers().add(ledger);
		//accountsDao.insertAccounts(acct);
		//status.setComplete();
		
		/* update to Accounts Ledger Closing balance

					BigDecimal clBal = acct.getClosingBalance();
					String clType = acct.getClosingType();

					if (clType.equals("Credit")) 
					{
						if (closingType.equals("Debit") && closingBalance > clBal) 
						{
							accountsDao.updateClosingBalance(closingBalance,
									closingType, accountGroup);
						}
						else if (closingType.equals("Debit")) {
							accountsDao.updateCreditBalance(closingBalance, clType,
									accountGroup);
						}
						else {
							accountsDao.updateDebitBalance(closingBalance, closingType,
									accountGroup);
						  }
					}
					else 
					{
						if (closingType.equals("Credit") && closingBalance > clBal) {
							accountsDao.updateClosingBalance(closingBalance,
									closingType, accountGroup);
						} 
						else if (closingType.equals("Credit")) {
							accountsDao.updateCreditBalance(closingBalance, clType,
									accountGroup);
						}
						else {
							accountsDao.updateDebitBalance(closingBalance, closingType,
									accountGroup);
						}
					}*/ 
		
			
			
		
			if(ledger.getAccountGroup().equals("Bank Account")){
				Accounts acct1 = accountsDao.getAccounts(1);
				acct1.getLedgers().add(ledger);
				accountsDao.insertAccounts(acct1);
				status.setComplete();
				addJournalEntry(ledger);
				return new ModelAndView("redirect:accountsledger.htm?accountId=1");													
			}else if(ledger.getAccountGroup().equals("Bank Loan")){
				Accounts acct2 = accountsDao.getAccounts(2);
				acct2.getLedgers().add(ledger);
				accountsDao.insertAccounts(acct2);
				status.setComplete();
				addJournalEntry(ledger);
				return new ModelAndView("redirect:accountsledger.htm?accountId=2");
			}else if(ledger.getAccountGroup().equals("Bank OCC Account")){
				Accounts acct3 = accountsDao.getAccounts(3);
				acct3.getLedgers().add(ledger);
				accountsDao.insertAccounts(acct3);
				status.setComplete();
				addJournalEntry(ledger);
				return new ModelAndView("redirect:accountsledger.htm?accountId=3");
			}else if(ledger.getAccountGroup().equals("Capital Account")){
				Accounts acct4 = accountsDao.getAccounts(4);
				acct4.getLedgers().add(ledger);
				accountsDao.insertAccounts(acct4);
				status.setComplete();
				addJournalEntry(ledger);
				return new ModelAndView("redirect:accountsledger.htm?accountId=4");
			}else if(ledger.getAccountGroup().equals("Cash Account")){
				Accounts acct5 = accountsDao.getAccounts(5);
				acct5.getLedgers().add(ledger);   
				accountsDao.insertAccounts(acct5);
				status.setComplete();
				addJournalEntry(ledger);
				return new ModelAndView("redirect:accountsledger.htm?accountId=5");
			}else if(ledger.getAccountGroup().equals("Current Assets")){
				Accounts acct6 = accountsDao.getAccounts(6);
				acct6.getLedgers().add(ledger);
				accountsDao.insertAccounts(acct6);
				status.setComplete();
				addJournalEntry(ledger);
				return new ModelAndView("redirect:accountsledger.htm?accountId=6");
			}else if(ledger.getAccountGroup().equals("Current Liabilities")){
				Accounts acct7 = accountsDao.getAccounts(7);
				acct7.getLedgers().add(ledger);
				accountsDao.insertAccounts(acct7);
				status.setComplete();
				addJournalEntry(ledger);
				return new ModelAndView("redirect:accountsledger.htm?accountId=7");
			}else if(ledger.getAccountGroup().equals("Direct Expenditure")){
				Accounts acct8 = accountsDao.getAccounts(8);
				acct8.getLedgers().add(ledger);
				accountsDao.insertAccounts(acct8);
				status.setComplete();
				addJournalEntry(ledger);
				return new ModelAndView("redirect:accountsledger.htm?accountId=8");
			}else if(ledger.getAccountGroup().equals("Direct Income")){
				Accounts acct9 = accountsDao.getAccounts(9);
				acct9.getLedgers().add(ledger);
				accountsDao.insertAccounts(acct9);
				status.setComplete();
				addJournalEntry(ledger);
				return new ModelAndView("redirect:accountsledger.htm?accountId=9");
			}else if(ledger.getAccountGroup().equals("Fixed Assets")){
				Accounts acct10 = accountsDao.getAccounts(10);
				acct10.getLedgers().add(ledger);
				accountsDao.insertAccounts(acct10);
				status.setComplete();
				addJournalEntry(ledger);
				return new ModelAndView("redirect:accountsledger.htm?accountId=10");
			}else if(ledger.getAccountGroup().equals("Indirect Expenditure")){
				Accounts acct11 = accountsDao.getAccounts(11);
				acct11.getLedgers().add(ledger);
				accountsDao.insertAccounts(acct11);
				status.setComplete();
				addJournalEntry(ledger);
				return new ModelAndView("redirect:accountsledger.htm?accountId=11");
			}else if(ledger.getAccountGroup().equals("Indirect Income")){
				Accounts acct12 = accountsDao.getAccounts(12);
				acct12.getLedgers().add(ledger);
				accountsDao.insertAccounts(acct12);
				status.setComplete();
				addJournalEntry(ledger);
				return new ModelAndView("redirect:accountsledger.htm?accountId=12");
			}else if(ledger.getAccountGroup().equals("Profit And Loss Account")){
				Accounts acct13 = accountsDao.getAccounts(13);
				acct13.getLedgers().add(ledger);
				accountsDao.insertAccounts(acct13);
				status.setComplete();
				addJournalEntry(ledger);
				return new ModelAndView("redirect:accountsledger.htm?accountId=13");
			}else if(ledger.getAccountGroup().equals("Purchase")){
				Accounts acct14 = accountsDao.getAccounts(14);
				acct14.getLedgers().add(ledger);
				accountsDao.insertAccounts(acct14);
				status.setComplete();
				addJournalEntry(ledger);
				return new ModelAndView("redirect:accountsledger.htm?accountId=14");
			}else if(ledger.getAccountGroup().equals("Sales")){
				Accounts acct15 = accountsDao.getAccounts(15);
				acct15.getLedgers().add(ledger);
				accountsDao.insertAccounts(acct15);
				status.setComplete();
				addJournalEntry(ledger);
				return new ModelAndView("redirect:accountsledger.htm?accountId=15");
			}else if(ledger.getAccountGroup().equals("Stock Account")){
				Accounts acct16 = accountsDao.getAccounts(16);
				acct16.getLedgers().add(ledger);
				accountsDao.insertAccounts(acct16);
				status.setComplete();
				addJournalEntry(ledger);
				return new ModelAndView("redirect:accountsledger.htm?accountId=16");
		}									
			
		return new ModelAndView("redirect:accountsledger.htm?accountId=" + accountId);		
	}

	private void addJournalEntry(Ledger ledger){
		
		jrnl = new Journal();
		List<String> ledgerCode=ledgerDao.getLedgerGroupCode(ledger.getAccountGroup());
		String journalCode=ledgerCode.get(0).toString();
		jrnl.setCreditCode(journalCode);
		jrnl.setDebitCode(journalCode);

		jrnl.setJournalNO(getJournalNumber(jrnl));
		jrnl.setJournalType("Opening Balance");
		jrnl.setTransactionId("L"+ledger.getLedgerId()); 
		jrnl.setJournalDate(ledger.getLedgerDate());
		if (ledger.getAccountGroup().equals("Cash Account")) {
			jrnl.setJournalType("Cash Opening Balance");
			}
		else
		{
			jrnl.setJournalType("Opening Balance");
		}
		if(ledger.getOpeningType().equals("Debit")){
			jrnl.setDebitAccountName(ledger.getLedgerName());
			jrnl.setCreditAccountName("Opening Balance");
		}
		else{
			jrnl.setDebitAccountName("Opening Balance");
			jrnl.setCreditAccountName(ledger.getLedgerName());		
		}
			    
		jrnl.setDebitAmount(ledger.getOpeningBalance());
		jrnl.setCreditAmount(ledger.getOpeningBalance());
		jrnl.setNarration("");							
		journalDao.insertJournal((Journal) jrnl);			
	}
	@RequestMapping(method=RequestMethod.POST,value="/formledger.htm",params="update")
	public ModelAndView updateBank(@ModelAttribute("ledgerId")Ledger ledgerId,BindingResult result,Integer accountId)throws Exception{
		
		validator.validate(ledgerId, result);
		if (result.hasErrors()) {
			ModelMap map = new ModelMap();
			map.put("command", ledgerId);
			map.put("errorType", "updateError");
			return new ModelAndView("formledger",map);		
		}
		
		Integer ledger2 = ledgerId.getLedgerId();
		ledgerId.setLedgerId(ledger2);
		String finalClType; 
		BigDecimal finalClBalanceParty;
		
		/** Update  Ledger Opening and Closing Balance */
		BigDecimal ZERO = new BigDecimal("0");
		BigDecimal CONVERT = new BigDecimal("-1");
		
		Ledger ledgerObj = ledgerDao.getLedger(ledger2);		
		BigDecimal old_OpBal = ledgerObj.getOpeningBalance();		
		String old_OpType = ledgerObj.getOpeningType();		
		
		String new_OpType = ledgerId.getOpeningType();		
		BigDecimal new_OpBal = ledgerId.getOpeningBalance();
		String ledger_name = ledgerId.getLedgerName();
				 
		/** Party Balance Update */
			
			List <Ledger> ledgerListParty = ledgerDao.searchLedger(ledgerId.getLedgerName());
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
			
			ledgerDao.updateLedger((Ledger) ledgerId);
			ledgerDao.updateLedgerPartyBalance(finalClBalanceParty, finalClType, ledger_name);
			
			/* Journal Update */
			List<Journal> jrnlLedgerList = journalDao.getJournalUpdateSales("Opening Balance","L"+ledgerId.getLedgerId().toString());						
			
			if(!jrnlLedgerList.isEmpty()){	
				jrnlLedgerList.get(0).setJournalDate(ledgerId.getLedgerDate());
				jrnlLedgerList.get(0).setDebitAmount(ledgerId.getOpeningBalance());
				jrnlLedgerList.get(0).setCreditAmount(ledgerId.getOpeningBalance());	
				
				if(ledgerId.getOpeningType().equals("Debit")){
					jrnlLedgerList.get(0).setDebitAccountName(ledgerId.getLedgerName());
					jrnlLedgerList.get(0).setCreditAccountName("Opening Balance");
				}
				else{
					jrnlLedgerList.get(0).setDebitAccountName("Opening Balance");
					jrnlLedgerList.get(0).setCreditAccountName(ledgerId.getLedgerName());
				}
				
				journalDao.updateJournal(jrnlLedgerList.get(0));
			}
			/* Journal Update Cash Account Group*/
			List<Journal> jrnlLedgerListCash = journalDao.getJournalUpdateSales("Cash Opening Balance","L"+ledgerId.getLedgerId().toString());						
			
			if(!jrnlLedgerListCash.isEmpty()){	
				jrnlLedgerListCash.get(0).setJournalDate(ledgerId.getLedgerDate());
				jrnlLedgerListCash.get(0).setDebitAmount(ledgerId.getOpeningBalance());
				jrnlLedgerListCash.get(0).setCreditAmount(ledgerId.getOpeningBalance());	
				
				if(ledgerId.getOpeningType().equals("Debit")){
					jrnlLedgerListCash.get(0).setDebitAccountName(ledgerId.getLedgerName());
					jrnlLedgerListCash.get(0).setCreditAccountName("Opening Balance");
				}
				else{
					jrnlLedgerListCash.get(0).setDebitAccountName("Opening Balance");
					jrnlLedgerListCash.get(0).setCreditAccountName(ledgerId.getLedgerName());
				}
				
				journalDao.updateJournal(jrnlLedgerListCash.get(0));
			}
			
			return new ModelAndView("redirect:accountsledger.htm?accountId="+accountId);
	}	
	
	
		//this is to map the formledgerlist 
		
		@RequestMapping(value="/formledgerlist",method=RequestMethod.GET)
		public ModelAndView ledgerlist(@ModelAttribute Ledger ledger){
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("SLCount", itemmasterDao.lowMetalStockList().size());
			model.put("stocklist", itemmasterDao.lowMetalStockList());
			model.put("command", ledger);			
			return  new ModelAndView("formledgerlist",model);
		}
	
		@ModelAttribute("ledgerId")
		public Ledger getLedger(Integer ledgerId) throws Exception {
			if (ledgerId != null && ledgerId != 0) {
				return ledgerDao.getLedger(ledgerId);
			}
			else {
				return new Ledger();
			}
		}
		
		//this is to map the formledgerpo and list the pending orders	
		@RequestMapping(value = "/formledgerpo", method = RequestMethod.GET)
		public ModelAndView  listformledgerpo(@ModelAttribute("show") Ledger ledger) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("Pendingorder", salesorderDao.SalesOrderPending());
			model.put("command", ledger);
			ModelAndView mv=new ModelAndView("formledgerpo",model);			
			return mv;			
		}			 
				
	// this is mapping for accounts for listing all accounts in accounts.jsp
		@RequestMapping(value="/accounts",method=RequestMethod.GET)
		public  ModelAndView view(@ModelAttribute("show") Ledger ledger){
			Map<String,Object> model=new HashMap<String,Object>();
			model.put("boardrate", ratemasterDao.searchRateMaster());
			model.put("Pendingorder", salesorderDao.SalesOrderPending());
			model.put("message", salesorderDao.SalesOrderPending().size());
			model.put("SLCount", itemmasterDao.lowMetalStockList().size());
			model.put("stocklist", itemmasterDao.lowMetalStockList());
			model.put("accountsList",accountsDao.listAccounts());			
			ModelAndView mv=new ModelAndView("accounts",model);			
			return mv;			
		}
				
		//this is to map the accountsledger and list them
		@RequestMapping(value="/accountsledger",method=RequestMethod.GET)
		public  ModelAndView views(@ModelAttribute("show") Ledger ledger,Integer accountId){
			Map<String,Object> model=new HashMap<String,Object>();
			model.put("accounts", accountsDao.getAccounts(accountId));
			model.put("ledger",ledgerDao.listLedger()); 
			ModelAndView mv=new ModelAndView("accountsledger",model);			
			return mv;
			// this will return the list of ledger in each account type.
		}
		
		@RequestMapping(value = "/formledger", params = "cancel")
		public ModelAndView cancelForm() {
			return new ModelAndView("redirect:accounts.htm");
		}
		
		@RequestMapping(value="/viewformledger",method=RequestMethod.GET)
		public ModelAndView bindUpdate(@RequestParam("accountId")Integer accountId,@ModelAttribute("ledgerId")Ledger ledgerId)throws Exception{
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("boardrate", ratemasterDao.searchRateMaster());
			map.put("Pendingorder", salesorderDao.SalesOrderPending());
			map.put("message", salesorderDao.SalesOrderPending().size());
			map.put("SLCount", itemmasterDao.Itemstocklist().size());
			map.put("stocklist", itemmasterDao.Itemstocklist());
			map.put("accountId",accountId);
			return new ModelAndView("formledger", map);
		}
}



