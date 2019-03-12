package com.jewellery.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.jewellery.dao.CardIssueDao;
import com.jewellery.dao.JournalDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.dao.RateMasterDao;
import com.jewellery.dao.SSReceiptDao;
import com.jewellery.dao.StartSchemeDao;
import com.jewellery.entity.CardIssue;
import com.jewellery.entity.Journal;
import com.jewellery.entity.Ledger;
import com.jewellery.entity.Saving_SchemeReceipt;
import com.jewellery.util.JournalCode;
import com.jewellery.validator.SSReceiptValidator;

@Controller
public class FormSSReceiptController extends JournalCode{
	
	@Autowired
	private SSReceiptDao ssReceiptDao;
	@Autowired
	private LedgerDao ledgerDao; 
	@Autowired
	private RateMasterDao rateMasterDao;
	@Autowired
	private CardIssueDao cardIssueDao;
	@Autowired
	private JournalDao journalDao;
	@Autowired
	SSReceiptValidator ssReceiptValidator;
	@Autowired
	StartSchemeDao startSchemeDao;
	
	BigDecimal ZERO = new BigDecimal("0.00");
	BigDecimal CONVERT = new BigDecimal("-1");
	String finalClType = "";
	List<String> ledgerGroupCode;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/** Form Page - Receipt **/
	@RequestMapping(value="/formsavingreceipt.htm",method=RequestMethod.GET)
	public ModelAndView newSavingReceipt(@ModelAttribute("ssReceipt") Saving_SchemeReceipt ssReceipt,@RequestParam(value="printInvoice",required=false) String printInvoice, @RequestParam(value="receiptNO",required=false) String receiptNO,ModelMap model){
		
		//Customer Names
		List<String> CustomerNameList = cardIssueDao.getCustomerActiveSet();
		HashSet<String> CustomerNameSet = new HashSet<String>(CustomerNameList);
		
		model.addAttribute("CustomerNameList", CustomerNameSet);
		model.addAttribute("DebitAccountList", ledgerDao.listJournalRecieptDebit());	
		model.addAttribute("printInvoice",printInvoice);
		model.addAttribute("receiptNO", receiptNO);
		model.put("ssReceipt", ssReceipt);
		return new ModelAndView("formsavingreceipt",model);
	}
	
	/** Form Page - Payment **/
	@RequestMapping(value="/formSSRCancel.htm",method=RequestMethod.GET)
	public ModelAndView newSavingPayment(@ModelAttribute("ssReceipt") Saving_SchemeReceipt ssReceipt,ModelMap model){	
		
		//Customer Names
		List<String> PaymentCustomerList = cardIssueDao.getCustomerActiveMatureSet();
		HashSet<String> PaymentCustomerSet = new HashSet<String>(PaymentCustomerList);
		
		model.addAttribute("CustomerNameList", PaymentCustomerSet) ;
		model.addAttribute("DebitAccountList", ledgerDao.listJournalRecieptDebit());		
		model.put("ssReceipt", ssReceipt);
		return new ModelAndView("formSSRCancel");
	}
	
	/** Listing Page - Receipt **/
	@RequestMapping(value="/savingReceipt.htm",method=RequestMethod.GET)
	public ModelAndView listSavingReceipt(@ModelAttribute("ssReceipt") Saving_SchemeReceipt ssReceipt, ModelMap model){
		model.addAttribute("savingReceiptList", ssReceiptDao.listSSReceipt());
		return new ModelAndView("savingReceipt",model);
	}
	
	/** Listing Page - Payment **/
	@RequestMapping(value="/ssrCancelList.htm",method=RequestMethod.GET)
	public ModelAndView listSavingPayment(ModelMap model){	
		model.addAttribute("cancelCardList", ssReceiptDao.listSSPayment()); //** Cancelled card List
		return new ModelAndView("ssrCancelList");
	}
	
	/** Form Cancel - List Page **/
	@RequestMapping(value="/formsavingreceipt.htm",method=RequestMethod.POST,params="cancel")
	public ModelAndView cancelSavingReceipt(@ModelAttribute("ssReceipt") Saving_SchemeReceipt ssReceipt,ModelMap model){
		
		if(ssReceipt.getFormType().equalsIgnoreCase("Saving Scheme Receipt")){
			model.addAttribute("savingReceiptList", ssReceiptDao.listSSReceipt());
			return new ModelAndView("savingReceipt",model);
			
		}else{ /************************ FormType = Saving Scheme Payment *************************/
			
			model.addAttribute("cancelCardList", ssReceiptDao.listSSPayment()); //** Cancelled card List
			return new ModelAndView("redirect:ssrCancelList.htm",model);
		}
		
	}		
	
	/** View Mode **/
	@RequestMapping(value="/viewSavingReceipt.htm",method=RequestMethod.GET)
	public ModelAndView viewForm(@ModelAttribute("ssReceipt") Saving_SchemeReceipt ssReceipt, @RequestParam(value = "receiptId", required = false) Integer receiptId,ModelMap model)
	{
		
		ssReceipt = ssReceiptDao.getSSReceipt(receiptId).get(0);
				
		//CustomerName List binding
		List<String> ReceiptCustomerList = cardIssueDao.getCustomerActiveSet();
		HashSet<String> ReceiptCustomerSet = new HashSet<String>(ReceiptCustomerList); 
		List<String> PaymentCustomerList = cardIssueDao.getCustomerActiveMatureSet();
		HashSet<String> PaymentCustomerSet = new HashSet<String>(PaymentCustomerList); 
		
		//SchemeName List binding
		List<String> ReceiptSchemeList = cardIssueDao.SSRSchemeSet(ssReceipt.getCustomerName());
		HashSet<String> ReceiptSchemeSet = new HashSet<String>(ReceiptSchemeList); 
		List<String> PaymentSchemeList = cardIssueDao.SSCSchemeSet(ssReceipt.getCustomerName());
		HashSet<String> PaymentSchemeSet = new HashSet<String>(PaymentSchemeList);
		
		//CardNo List binding
		List<String> ReceiptcardNoList = cardIssueDao.SSRCardNoSet(ssReceipt.getCustomerName(), ssReceipt.getSchemeName());
		HashSet<String> ReceiptcardNoSet = new HashSet<String>(ReceiptcardNoList);
		List<String> PaymentcardNoList = cardIssueDao.SSCCardNoSet(ssReceipt.getCustomerName(), ssReceipt.getSchemeName());
		HashSet<String> PaymentcardNoSet = new HashSet<String>(PaymentcardNoList);
		
		//Current Record Binding
		CardIssue cardIssueRecord = cardIssueDao.getCardIssueCardNO(ssReceipt.getCardNo()).get(0);
		if(!cardIssueRecord.getStatus().equalsIgnoreCase("Active")){
			ReceiptCustomerSet.add(cardIssueRecord.getCustomerName().trim());
			ReceiptSchemeSet.add(cardIssueRecord.getSchemeName().trim());
			ReceiptcardNoSet.add(cardIssueRecord.getCardNo().trim());
		}
		if(!cardIssueRecord.getStatus().equalsIgnoreCase("Active") && !cardIssueRecord.getStatus().equalsIgnoreCase("Matured")){
			PaymentCustomerSet.add(cardIssueRecord.getCustomerName().trim());
			PaymentSchemeSet.add(cardIssueRecord.getSchemeName().trim());
			PaymentcardNoSet.add(cardIssueRecord.getCardNo().trim());
		}
		model.addAttribute("DebitAccountList", ledgerDao.listJournalRecieptDebit());
		model.addAttribute("currSchemeName", ssReceipt.getSchemeName());
		if(ssReceipt.getFormType().equalsIgnoreCase("Saving Scheme Receipt")){
			model.addAttribute("CustomerNameList", ReceiptCustomerSet);
			model.addAttribute("SchemeNameList", ReceiptSchemeSet);
			model.addAttribute("CardNumbersList", ReceiptcardNoSet);
			String lastReceiptNumber = ssReceiptDao.getSSRLastCardNo(ssReceipt.getCardNo()).get(0);
			if (ssReceipt.getReceiptNO().equalsIgnoreCase(lastReceiptNumber)) {
				model.addAttribute("lastReceiptNumber", "Yes");
			} else {
				model.addAttribute("lastReceiptNumber", "No");
			}
			model.put("ssReceipt", ssReceipt);
			return new ModelAndView("formsavingreceipt",model);
		}else{
			/************* FormType = Saving Scheme Payment *******/
			model.addAttribute("CustomerNameList", PaymentCustomerSet);
			model.addAttribute("SchemeNameList", PaymentSchemeSet);
			model.addAttribute("CardNumbersList", PaymentcardNoSet);
			model.put("ssReceipt", ssReceipt);
			return new ModelAndView("formSSRCancel",model);
		}
	}
	
	/** Insert Mode **/
	@RequestMapping(value="/formsavingreceipt.htm",method=RequestMethod.POST,params="insert")
	public ModelAndView saveForm(@ModelAttribute("ssReceipt") Saving_SchemeReceipt ssReceipt, BindingResult result,@RequestParam(value="printInvoice",required=false) String printInvoice, SessionStatus status, ModelMap model)
	{
		if(ssReceipt.getFormType().equalsIgnoreCase("Saving Scheme Receipt")){
			
			ssReceiptValidator.validate(ssReceipt, result);
			if (result.hasErrors()) {
				
				//Customer Names
				List<String> CustomerNameList = cardIssueDao.getCustomerActiveSet();
				HashSet<String> CustomerNameSet = new HashSet<String>(CustomerNameList);
				
				//Scheme Names
				List<String> ReceiptSchemeList = cardIssueDao.SSRSchemeSet(ssReceipt.getCustomerName());
				HashSet<String> ReceiptSchemeSet = new HashSet<String>(ReceiptSchemeList); 
				
				//CardNo List binding
				List<String> ReceiptcardNoList = cardIssueDao.SSRCardNoSet(ssReceipt.getCustomerName(), ssReceipt.getSchemeName());
				HashSet<String> ReceiptcardNoSet = new HashSet<String>(ReceiptcardNoList);
				 
				model.addAttribute("CustomerNameList", CustomerNameSet);
				model.addAttribute("DebitAccountList", ledgerDao.listJournalRecieptDebit());
				model.addAttribute("CardNumbersList", ReceiptcardNoSet);
				model.addAttribute("SchemeNameList", ReceiptSchemeSet);
				model.addAttribute("errorType", "insertError");
				model.put("ssReceipt", ssReceipt);				
				return new ModelAndView("formsavingreceipt", model);
			}
			
			/************************ LEDGER , JOURNAL UPDATE ****************************/
			//System.out.println("customer Name :"+ssReceipt.getCustomerName()+"Card No:"+ssReceipt.getCardNo());
			//*** PARTY BALANCE ***//
			List<CardIssue> CardObjList = cardIssueDao.getSSRPLedgerCardIssueList(ssReceipt.getCustomerName(),ssReceipt.getCardNo());
			
			if(!CardObjList.isEmpty()){
				
				CardIssue cardObj  = CardObjList.get(0);
				if(ssReceipt.getSchemeType().equalsIgnoreCase("Amount")){ //IN AMOUNT
					BigDecimal receiptAmt = cardObj.getClosingBalanceInRs().add(ssReceipt.getReceiptAmount());
					cardObj.setClosingBalanceInRs(receiptAmt);
				}else{ //IN GRAMS
					BigDecimal receiptGrams = cardObj.getClosingBalanceInGrams().add(ssReceipt.getSchemeInGrams());
					cardObj.setClosingBalanceInGrams(receiptGrams);
				}
				
				// Card Status Update based on Scheme Duration //
				Integer cardInstallMent = cardObj.getInstallment();
				if(cardInstallMent == null){
					cardInstallMent = 0;
				}
				int InstallmentNo = cardInstallMent+1;
				cardObj.setInstallment(InstallmentNo);
				ssReceipt.setReceiptInstallmentNO(InstallmentNo);
				
				if(startSchemeDao.searchStartSchemeSSRP(cardObj.getSchemeName()).get(0).getSchemeDuration() == InstallmentNo && cardObj.getStatus().equalsIgnoreCase("Active")){
					cardObj.setStatus("Matured");
				}
				cardIssueDao.updateCardIssue(cardObj);
			}
					
			//*** CASH/BANK ACCOUNT LEDGER UPDATE ***//
			
			List <Ledger> cashLedgerList = ledgerDao.searchLedger(ssReceipt.getDebitAccount());
			BigDecimal oldClBalanceCash = cashLedgerList.get(0).getClosingTotalBalance();
			String clTypeCash = cashLedgerList.get(0).getClosingTotalType();
			String finalClType = "";
			
			if(clTypeCash.equalsIgnoreCase("Credit")){
				oldClBalanceCash = ZERO.subtract(oldClBalanceCash);						
			}
			
			BigDecimal finalClBalanceCash = oldClBalanceCash.add(ssReceipt.getReceiptAmount());
					
			if(finalClBalanceCash.signum() == -1){
				finalClType = "Credit";
				finalClBalanceCash = finalClBalanceCash.multiply(CONVERT);
			}else{
					finalClType = "Debit";						
				}
			ledgerDao.updateLedgerPartyBalance(finalClBalanceCash, finalClType, ssReceipt.getDebitAccount());
			
			//** INSERT SAVINGS SCHEME RECEIPT ***//
			
			String receiptNO = getSSReceiptNumber(ssReceipt);
			ssReceipt.setReceiptNO(receiptNO);
			ssReceiptDao.insertSSReceipt(ssReceipt);
			
			//*** JOURNAL ENTRY ***//
			
			Journal jrnl = new Journal();	 
			jrnl.setJournalNO(getJournalNumber(jrnl));
			jrnl.setTransactionId(ssReceipt.getReceiptId().toString());
			jrnl.setJournalType("SAVINGS RECEIPT");
		    jrnl.setJournalDate(ssReceipt.getReceiptDate());
		    
		 // Set Account group code as Debit codex
		 	ledgerGroupCode = ledgerDao.getLedgerAccountCode(ssReceipt.getDebitAccount());			
		 	jrnl.setDebitCode(ledgerGroupCode.get(0).toString());		 			
			jrnl.setDebitAccountName(ssReceipt.getDebitAccount());			

			// Set Account group code as Credit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode(ssReceipt.getCustomerName());
			jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
			jrnl.setCreditAccountName(ssReceipt.getCustomerName());
			
			jrnl.setDebitAmount(ssReceipt.getReceiptAmount());
			jrnl.setCreditAmount(ssReceipt.getReceiptAmount());
			jrnl.setNarration(receiptNO);							
			journalDao.insertJournal((Journal) jrnl);	
		
			model.addAttribute("savingReceiptList", ssReceiptDao.listSSReceipt());
			model.addAttribute("printInvoice",printInvoice.trim());
			model.addAttribute("receiptNO", ssReceipt.getReceiptNO());
			status.setComplete();
			return new ModelAndView("redirect:formsavingreceipt.htm",model);
			
		}else{ /********************* FormType = Saving Scheme Payment *********************/
			
			ssReceiptValidator.validate(ssReceipt, result);
			if (result.hasErrors()) {
				
				//Customer Names
				List<String> PaymentCustomerList = cardIssueDao.getCustomerActiveMatureSet();
				HashSet<String> PaymentCustomerSet = new HashSet<String>(PaymentCustomerList);
				
				//Scheme Names
				List<String> PaymentSchemeList = cardIssueDao.SSCSchemeSet(ssReceipt.getCustomerName());
				HashSet<String> PaymentSchemeSet = new HashSet<String>(PaymentSchemeList);
				
				//CardNo List
				List<String> PaymentcardNoList = cardIssueDao.SSCCardNoSet(ssReceipt.getCustomerName(), ssReceipt.getSchemeName());
				HashSet<String> PaymentcardNoSet = new HashSet<String>(PaymentcardNoList);
				
				model.addAttribute("CustomerNameList", PaymentCustomerSet);
				model.addAttribute("DebitAccountList", ledgerDao.listJournalRecieptDebit());
				model.addAttribute("CardNumbersList", PaymentcardNoSet);
				model.addAttribute("SchemeNameList", PaymentSchemeSet);
				model.put("command", ssReceipt);
				model.put("errorType", "insertError");
				status.setComplete();
				return new ModelAndView("formSSRCancel", model);
			}
			
			/************************ LEDGER , JOURNAL UPDATE ****************************/
			//*** PARTY BALANCE ***//
			List<CardIssue> CardObjListPayment = cardIssueDao.getSSRPLedgerCardIssueList(ssReceipt.getCustomerName(),ssReceipt.getCardNo());
			
			if(!CardObjListPayment.isEmpty()){
				
				CardIssue cardObjPayment  = CardObjListPayment.get(0);
				
				if(ssReceipt.getSchemeType().equalsIgnoreCase("Amount")){ //IN AMOUNT
					BigDecimal paymentAmt = cardObjPayment.getClosingBalanceInRs().subtract(ssReceipt.getPaymentAmount());
					cardObjPayment.setClosingBalanceInRs(paymentAmt);
				}else{ //IN GRAMS
					BigDecimal paymentGrams =  cardObjPayment.getClosingBalanceInGrams().subtract(ssReceipt.getPaymentClosingGrams());
					cardObjPayment.setClosingBalanceInGrams(paymentGrams);
				}

				// Card Status Update based on Scheme Duration //
				cardObjPayment.setStatus("Cancelled");				
				cardIssueDao.updateCardIssue(cardObjPayment);
			}
					
			//*** CASH/BANK ACCOUNT LEDGER UPDATE ***//
			
			List <Ledger> cashLedgerListPayment = ledgerDao.searchLedger(ssReceipt.getCreditAccount());
			BigDecimal oldClBalanceCash = cashLedgerListPayment.get(0).getClosingTotalBalance();
			String clTypeCash = cashLedgerListPayment.get(0).getClosingTotalType();
					
			if(clTypeCash.equalsIgnoreCase("Credit")){
				oldClBalanceCash = ZERO.subtract(oldClBalanceCash);						
			}
			
			BigDecimal finalClBalanceCash = oldClBalanceCash.subtract(ssReceipt.getPaymentAmount());
					
			if(finalClBalanceCash.signum() == -1){
				finalClType = "Credit";
				finalClBalanceCash = finalClBalanceCash.multiply(CONVERT);
			}else{
					finalClType = "Debit";						
				}
			ledgerDao.updateLedgerPartyBalance(finalClBalanceCash, finalClType, ssReceipt.getCreditAccount());
			
			//** INSERT SAVINGS SCHEME RECEIPT ***//
			
			String receiptNO = getSSReceiptNumber(ssReceipt);
			ssReceipt.setReceiptNO(receiptNO);
			ssReceiptDao.insertSSReceipt(ssReceipt);
			
			//*** JOURNAL ENTRY ***//
			
			Journal jrnl = new Journal();	
			jrnl.setJournalNO(getJournalNumber(jrnl));
			jrnl.setTransactionId(ssReceipt.getReceiptId().toString());
			jrnl.setJournalType("SAVINGS PAYMENT");
		    jrnl.setJournalDate(ssReceipt.getReceiptDate());
		    
		    // Set Account group code as Debit code
		 	ledgerGroupCode = ledgerDao.getLedgerAccountCode(ssReceipt.getCustomerName());			
		 	jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
			jrnl.setDebitAccountName(ssReceipt.getCustomerName());			

			// Set Account group code as Credit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode(ssReceipt.getCreditAccount());
			jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
			jrnl.setCreditAccountName(ssReceipt.getCreditAccount());
			
			jrnl.setDebitAmount(ssReceipt.getPaymentAmount());
			jrnl.setCreditAmount(ssReceipt.getPaymentAmount());
			jrnl.setNarration(receiptNO);							
			journalDao.insertJournal((Journal) jrnl);	
		
			model.addAttribute("cancelCardList", ssReceiptDao.listSSPayment()); //** Cancelled card List
			status.setComplete();
			return new ModelAndView("redirect:ssrCancelList.htm");
		}
		
	}
	
	/** Update Mode **/
	@RequestMapping(value="/formsavingreceipt.htm",method=RequestMethod.POST,params="update")
	public ModelAndView updateForm(@ModelAttribute("ssReceipt") Saving_SchemeReceipt ssReceipt, BindingResult result, SessionStatus status, ModelMap model){
		
		Saving_SchemeReceipt ssReceiptOld = ssReceiptDao.getSSReceipt(ssReceipt.getReceiptId()).get(0);
	
		if(ssReceipt.getFormType().equalsIgnoreCase("Saving Scheme Receipt")){
			
			//Customer Names
			List<String> CustomerNameList = cardIssueDao.getCustomerActiveSet();
			HashSet<String> ReceiptCustomerSet = new HashSet<String>(CustomerNameList);
			
			//Scheme Names
			List<String> ReceiptSchemeList = cardIssueDao.SSRSchemeSet(ssReceiptOld.getCustomerName());
			HashSet<String> ReceiptSchemeSet = new HashSet<String>(ReceiptSchemeList); 
			
			//CardNo List binding
			List<String> ReceiptcardNoList = cardIssueDao.SSRCardNoSet(ssReceiptOld.getCustomerName(), ssReceiptOld.getSchemeName());
			HashSet<String> ReceiptcardNoSet = new HashSet<String>(ReceiptcardNoList);
			
			//Current Record Binding
			CardIssue cardIssueRecord = cardIssueDao.getCardIssueCardNO(ssReceiptOld.getCardNo()).get(0);
			if(!cardIssueRecord.getStatus().equalsIgnoreCase("Active")){
				ReceiptCustomerSet.add(cardIssueRecord.getCustomerName());
				ReceiptSchemeSet.add(cardIssueRecord.getSchemeName());
				ReceiptcardNoSet.add(cardIssueRecord.getCardNo());
			}	
			ssReceiptValidator.validate(ssReceipt, result);
			ssReceiptValidator.validateUpdate(ssReceiptOld, result);
			if (result.hasErrors()) {
				model.addAttribute("CustomerNameList", ReceiptCustomerSet);
				model.addAttribute("DebitAccountList", ledgerDao.listJournalRecieptDebit());
				model.addAttribute("CardNumbersList", ReceiptcardNoSet);
				model.addAttribute("SchemeNameList", ReceiptSchemeSet);
				model.put("command", ssReceipt);
				model.put("errorType", "updateError");
				status.setComplete();
				return new ModelAndView("formsavingreceipt", model);
			}
			
			/************************ LEDGER , JOURNAL UPDATE ****************************/
			//*** PARTY BALANCE ***//
			List<CardIssue> CardObjList1 = cardIssueDao.getSSRPLedgerCardIssueList(ssReceipt.getCustomerName(),ssReceipt.getCardNo());
			List<CardIssue> OldCardObjList1 = cardIssueDao.getSSRPLedgerCardIssueList(ssReceiptOld.getCustomerName(),ssReceiptOld.getCardNo());
			CardIssue OldcardObj1  = OldCardObjList1.get(0);
			CardIssue cardObj1  = CardObjList1.get(0);
			
			//*** OLD PARTY BALANCE **//
			if(!OldCardObjList1.isEmpty()){
				
				if(ssReceiptOld.getSchemeType().equalsIgnoreCase("Amount")){ //IN AMOUNT
					BigDecimal oldReceiptAmt = OldcardObj1.getClosingBalanceInRs().subtract(ssReceiptOld.getReceiptAmount());
					OldcardObj1.setClosingBalanceInRs(oldReceiptAmt);
				}else{ //IN GRAMS
					BigDecimal oldReceiptGrams = OldcardObj1.getClosingBalanceInGrams().subtract(ssReceiptOld.getSchemeInGrams());
					OldcardObj1.setClosingBalanceInGrams(oldReceiptGrams);
				}
				
				// OLD Card Status Update based on Scheme Duration //
				if(!OldcardObj1.getCardNo().equalsIgnoreCase(cardObj1.getCardNo())){
					Integer oldCardInstallment =  OldcardObj1.getInstallment();
					if(oldCardInstallment == null){
						oldCardInstallment = 0;
					}
					int oldInstallmentNo = oldCardInstallment-1;
					OldcardObj1.setInstallment(oldInstallmentNo);
					
					if(startSchemeDao.searchStartSchemeSSRP(OldcardObj1.getSchemeName()).get(0).getSchemeDuration() == oldInstallmentNo){
						OldcardObj1.setStatus("Matured");
					}else{
						OldcardObj1.setStatus("Active");
					}
				}
				
				cardIssueDao.updateCardIssue(OldcardObj1);
			}
			
			List<CardIssue> CardObjList2 = cardIssueDao.getSSRPLedgerCardIssueList(ssReceipt.getCustomerName(),ssReceipt.getCardNo());
			List<CardIssue> OldCardObjList2 = cardIssueDao.getSSRPLedgerCardIssueList(ssReceiptOld.getCustomerName(),ssReceiptOld.getCardNo());
			CardIssue OldcardObj2  = OldCardObjList2.get(0);
			CardIssue cardObj2  = CardObjList2.get(0);
				
			//*** NEW PARTY BALANCE **//
			if(!CardObjList2.isEmpty()){
				
				if(ssReceipt.getSchemeType().equalsIgnoreCase("Amount")){ //IN AMOUNT
					BigDecimal newReceiptAmt = cardObj2.getClosingBalanceInRs().add(ssReceipt.getReceiptAmount());
					cardObj2.setClosingBalanceInRs(newReceiptAmt);
				}else{ //IN GRAMS
					BigDecimal newReceiptGrams = cardObj2.getClosingBalanceInGrams().add(ssReceipt.getSchemeInGrams());
					cardObj2.setClosingBalanceInGrams(newReceiptGrams);
				}
				
				// NEW Card Status Update based on Scheme Duration //
				if(!OldcardObj2.getCardNo().equalsIgnoreCase(cardObj2.getCardNo())){
					Integer cardInstallment =  cardObj2.getInstallment();
					if(cardInstallment == null){
						cardInstallment = 0;
					}
					int InstallmentNo = cardInstallment + 1;
					cardObj2.setInstallment(InstallmentNo);
					ssReceipt.setReceiptInstallmentNO(InstallmentNo);
					if(startSchemeDao.searchStartSchemeSSRP(cardObj2.getSchemeName()).get(0).getSchemeDuration() == InstallmentNo){
						cardObj2.setStatus("Matured");
					}
				}
				
				cardIssueDao.updateCardIssue(cardObj2);
			}
			
			//** DEDIT LEDGER BALANCE UPDATE  ( Same Debit Account )***//
			if(ssReceiptOld.getDebitAccount().equalsIgnoreCase(ssReceipt.getDebitAccount())){
				
				List <Ledger> ledgerListSSDebit = ledgerDao.searchLedger(ssReceipt.getDebitAccount());
				BigDecimal oldClBalanceSSDebit = ledgerListSSDebit.get(0).getClosingTotalBalance();
				String clTypeSSDebit= ledgerListSSDebit.get(0).getClosingTotalType();
				String finalClTypeSSDebit = "";
				
				if(clTypeSSDebit.equalsIgnoreCase("Credit")){
					oldClBalanceSSDebit = ZERO.subtract(oldClBalanceSSDebit);						
				}
			
				BigDecimal dropClBalanceSSDebit = oldClBalanceSSDebit.subtract(ssReceiptOld.getReceiptAmount());
				BigDecimal finalClBalanceSSDebit  = dropClBalanceSSDebit.add(ssReceipt.getReceiptAmount());				
			
				if (finalClBalanceSSDebit.signum() == -1) {
					finalClTypeSSDebit = "Credit";
					finalClBalanceSSDebit = finalClBalanceSSDebit.multiply(CONVERT);
				} else {
					finalClTypeSSDebit = "Debit";					
				}
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceSSDebit, finalClTypeSSDebit, ssReceipt.getDebitAccount());
				
			}else{
				//** DEDIT LEDGER BALANCE UPDATE  ( Different Debit Account )***//
				/** From Old Party 1 */
				List <Ledger> ledgerListReceipt1 = ledgerDao.searchLedger(ssReceiptOld.getDebitAccount());
				String clTypeParty1 = ledgerListReceipt1.get(0).getClosingTotalType();
				BigDecimal oldClBalanceParty1 = ledgerListReceipt1.get(0).getClosingTotalBalance();
				String finalClTypeParty1 = "";
				String finalClTypeParty2 = "";
				
				/** To New Party 2 */
				List <Ledger> ledgerListReceipt2 = ledgerDao.searchLedger(ssReceipt.getDebitAccount());
				String clTypeParty2 = ledgerListReceipt2.get(0).getClosingTotalType();
				BigDecimal oldClBalanceParty2 = ledgerListReceipt2.get(0).getClosingTotalBalance();
			
	
				if(clTypeParty1.equalsIgnoreCase("Credit")){
					oldClBalanceParty1 = ZERO.subtract(oldClBalanceParty1);						
				}
				if(clTypeParty2.equalsIgnoreCase("Credit")){
					oldClBalanceParty2 = ZERO.subtract(oldClBalanceParty2);						
				}
				
				BigDecimal finalClBalanceParty1 = oldClBalanceParty1.subtract(ssReceiptOld.getReceiptAmount());
				BigDecimal finalClBalanceParty2 = oldClBalanceParty2.add(ssReceipt.getReceiptAmount());
								
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
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty1, finalClTypeParty1, ssReceiptOld.getDebitAccount());
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty2, finalClTypeParty2, ssReceipt.getDebitAccount());
			}
			
			//*** JOURNAL ENTRY ***//
			List<Journal> OldjrnlListReceipt = journalDao.getJournalUpdate("SAVINGS RECEIPT", ssReceiptOld.getReceiptId().toString());
			OldjrnlListReceipt.get(0).setJournalDate(ssReceipt.getReceiptDate());
			
			// Set Account group code as Debit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode(ssReceipt.getDebitAccount());			
			OldjrnlListReceipt.get(0).setDebitCode(ledgerGroupCode.get(0).toString());	
			OldjrnlListReceipt.get(0).setDebitAccountName(ssReceipt.getDebitAccount());
			
			// Set Account group code as Debit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode(ssReceipt.getCustomerName());			
			OldjrnlListReceipt.get(0).setCreditCode(ledgerGroupCode.get(0).toString());	
			OldjrnlListReceipt.get(0).setCreditAccountName(ssReceipt.getCustomerName());
			
			OldjrnlListReceipt.get(0).setDebitAmount(ssReceipt.getReceiptAmount());
			OldjrnlListReceipt.get(0).setCreditAmount(ssReceipt.getReceiptAmount());
			journalDao.updateJournal(OldjrnlListReceipt.get(0));
								
			ssReceiptDao.updateSSReceipt(ssReceipt);
			model.addAttribute("savingReceiptList", ssReceiptDao.listSSReceipt());
			status.setComplete();
			return new ModelAndView("savingReceipt",model);
			
		}else{ /********************* FormType = Saving Scheme Payment ******************/
			
			//Customer Names
			List<String> PaymentCustomerList = cardIssueDao.getCustomerActiveMatureSet();
			HashSet<String> PaymentCustomerSet = new HashSet<String>(PaymentCustomerList);
			
			//Scheme Names
			List<String> PaymentSchemeList = cardIssueDao.SSCSchemeSet(ssReceiptOld.getCustomerName());
			HashSet<String> PaymentSchemeSet = new HashSet<String>(PaymentSchemeList);
			
			//CardNo List
			List<String> PaymentcardNoList = cardIssueDao.SSCCardNoSet(ssReceiptOld.getCustomerName(), ssReceiptOld.getSchemeName());
			HashSet<String> PaymentcardNoSet = new HashSet<String>(PaymentcardNoList);
			
			//Current Record Binding
			CardIssue cardIssueRecord = cardIssueDao.getCardIssueCardNO(ssReceiptOld.getCardNo()).get(0);
			if(!cardIssueRecord.getStatus().equalsIgnoreCase("Active") && !cardIssueRecord.getStatus().equalsIgnoreCase("Matured")){
				PaymentCustomerSet.add(cardIssueRecord.getCustomerName());
				PaymentSchemeSet.add(cardIssueRecord.getSchemeName());
				PaymentcardNoSet.add(cardIssueRecord.getCardNo());
			}
			
			ssReceiptValidator.validate(ssReceipt, result);
			if (result.hasErrors()) {
				model.addAttribute("CustomerNameList", PaymentCustomerSet);
				model.addAttribute("DebitAccountList", ledgerDao.listJournalRecieptDebit());
				model.addAttribute("CardNumbersList", PaymentcardNoSet);
				model.addAttribute("SchemeNameList", PaymentSchemeSet);
				model.put("command", ssReceipt);
				model.put("errorType", "insertError");
				status.setComplete();
				return new ModelAndView("formSSRCancel", model);
			}
			
			/************************ LEDGER , JOURNAL UPDATE ****************************/
			
			//*** PARTY BALANCE ***//
			List<CardIssue> CardObjList3 = cardIssueDao.getSSRPLedgerCardIssueList(ssReceipt.getCustomerName(),ssReceipt.getCardNo());
			List<CardIssue> OldCardObjList3 = cardIssueDao.getSSRPLedgerCardIssueList(ssReceiptOld.getCustomerName(),ssReceiptOld.getCardNo());
			CardIssue OldcardObj3  = OldCardObjList3.get(0);
			CardIssue cardObj3  = CardObjList3.get(0);
			
			//*** OLD PARTY BALANCE **//
			if(!OldCardObjList3.isEmpty()){
				
				if(ssReceiptOld.getSchemeType().equalsIgnoreCase("Amount")){ //IN AMOUNT
					BigDecimal oldPaymentAmt = OldcardObj3.getClosingBalanceInRs().add(ssReceiptOld.getPaymentAmount());
					OldcardObj3.setClosingBalanceInRs(oldPaymentAmt);
				}else{ //IN GRAMS
					BigDecimal oldPaymentGrams = OldcardObj3.getClosingBalanceInGrams().add(ssReceiptOld.getPaymentClosingGrams());
					OldcardObj3.setClosingBalanceInGrams(oldPaymentGrams);
				}
				
				// Old Card Status Update based on Scheme Duration //
				if(!OldcardObj3.getCardNo().equalsIgnoreCase(cardObj3.getCardNo())){
					Integer OldCardInstallment = OldcardObj3.getInstallment();
					if(OldCardInstallment == null){
						OldCardInstallment = 0;
					}
					int oldInstallmentNo = OldCardInstallment;
					if(startSchemeDao.searchStartSchemeSSRP(OldcardObj3.getSchemeName()).get(0).getSchemeDuration() == oldInstallmentNo){
						OldcardObj3.setStatus("Matured");
					}else{
						OldcardObj3.setStatus("Active");
					}
				}				
				cardIssueDao.updateCardIssue(OldcardObj3);
			}
			
			List<CardIssue> CardObjList4 = cardIssueDao.getSSRPLedgerCardIssueList(ssReceipt.getCustomerName(),ssReceipt.getCardNo());
			List<CardIssue> OldCardObjList4 = cardIssueDao.getSSRPLedgerCardIssueList(ssReceiptOld.getCustomerName(),ssReceiptOld.getCardNo());
			CardIssue OldcardObj4  = OldCardObjList4.get(0);
			CardIssue cardObj4  = CardObjList4.get(0);
				
			//*** NEW PARTY BALANCE **//
			if(!CardObjList4.isEmpty()){
				
				if(ssReceipt.getSchemeType().equalsIgnoreCase("Amount")){ //IN AMOUNT
					BigDecimal newPaymentAmt = cardObj4.getClosingBalanceInRs().subtract(ssReceipt.getPaymentAmount());
					cardObj4.setClosingBalanceInRs(newPaymentAmt);
				}else{ //IN GRAMS
					BigDecimal newPaymentGrams = cardObj4.getClosingBalanceInGrams().subtract(ssReceipt.getPaymentClosingGrams());
					cardObj4.setClosingBalanceInGrams(newPaymentGrams);
				}
				
				// New Card Status Update based on Scheme Duration //
				if(!OldcardObj4.getCardNo().equalsIgnoreCase(cardObj4.getCardNo())){
					cardObj4.setStatus("Cancelled");
				}
				cardIssueDao.updateCardIssue(cardObj4);
			}
			
			//** CREDIT LEDGER BALANCE UPDATE  ( Same Credit Account )***//
			if(ssReceiptOld.getCreditAccount().equalsIgnoreCase(ssReceipt.getCreditAccount())){
				
				List <Ledger> ledgerListSSCredit = ledgerDao.searchLedger(ssReceipt.getCreditAccount());
				BigDecimal oldClBalanceSSCredit = ledgerListSSCredit.get(0).getClosingTotalBalance();
				String clTypeSSCredit= ledgerListSSCredit.get(0).getClosingTotalType();
				String finalClTypeCredit = "";
				
				if(clTypeSSCredit.equalsIgnoreCase("Credit")){
					oldClBalanceSSCredit = ZERO.subtract(oldClBalanceSSCredit);						
				}
			
				BigDecimal dropClBalanceSSCredit = oldClBalanceSSCredit.add(ssReceiptOld.getPaymentAmount());
				BigDecimal finalClBalanceSSCredit = dropClBalanceSSCredit.subtract(ssReceipt.getPaymentAmount());				
			
				if (finalClBalanceSSCredit.signum() == -1) {
					finalClTypeCredit = "Credit";
					finalClBalanceSSCredit = finalClBalanceSSCredit.multiply(CONVERT);
				} else {
					finalClTypeCredit = "Debit";					
				}
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceSSCredit, finalClTypeCredit, ssReceipt.getCreditAccount());
				
			}else{
				//** DEDIT LEDGER BALANCE UPDATE  ( Different Credit Account )***//
				
				/** From Old Party 1 */
				List <Ledger> ledgerListPayment1 = ledgerDao.searchLedger(ssReceiptOld.getCreditAccount());
				String clTypeParty1 = ledgerListPayment1.get(0).getClosingTotalType();
				BigDecimal oldClBalanceParty1 = ledgerListPayment1.get(0).getClosingTotalBalance();
				String finalClTypeParty1 = "";
				String finalClTypeParty2 = "";
				
				/** To New Party 2 */
				List <Ledger> ledgerListPayment2 = ledgerDao.searchLedger(ssReceipt.getCreditAccount());
				String clTypeParty2 = ledgerListPayment2.get(0).getClosingTotalType();
				BigDecimal oldClBalanceParty2 = ledgerListPayment2.get(0).getClosingTotalBalance();
	
				if(clTypeParty1.equalsIgnoreCase("Credit")){
					oldClBalanceParty1 = ZERO.subtract(oldClBalanceParty1);						
				}
				if(clTypeParty2.equalsIgnoreCase("Credit")){
					oldClBalanceParty2 = ZERO.subtract(oldClBalanceParty2);						
				}
				
				BigDecimal finalClBalanceParty1 = oldClBalanceParty1.add(ssReceiptOld.getPaymentAmount());
				BigDecimal finalClBalanceParty2 = oldClBalanceParty2.subtract(ssReceipt.getPaymentAmount());
								
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
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty1, finalClTypeParty1, ssReceiptOld.getCreditAccount());
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty2, finalClTypeParty2, ssReceipt.getCreditAccount());	
			}
			
			//*** JOURNAL ENTRY ***//
			List<Journal> OldjrnlListReceipt = journalDao.getJournalUpdate("SAVINGS PAYMENT", ssReceiptOld.getReceiptId().toString());
			OldjrnlListReceipt.get(0).setJournalDate(ssReceipt.getReceiptDate());
			
			
			// Set Account group code as Debit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode(ssReceipt.getCustomerName());			
			OldjrnlListReceipt.get(0).setDebitCode(ledgerGroupCode.get(0).toString());						
			OldjrnlListReceipt.get(0).setDebitAccountName(ssReceipt.getCustomerName());
			
			
			// Set Account group code as Debit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode(ssReceipt.getCreditAccount());			
			OldjrnlListReceipt.get(0).setCreditCode(ledgerGroupCode.get(0).toString());		
			OldjrnlListReceipt.get(0).setCreditAccountName(ssReceipt.getCreditAccount());
			
			OldjrnlListReceipt.get(0).setDebitAmount(ssReceipt.getPaymentAmount());
			OldjrnlListReceipt.get(0).setCreditAmount(ssReceipt.getPaymentAmount());
			journalDao.updateJournal(OldjrnlListReceipt.get(0));
			
		/*	String receiptNO = getSSReceiptNumber(ssReceipt);
			ssReceipt.setReceiptNO(receiptNO);*/
			ssReceiptDao.updateSSReceipt(ssReceipt);
			model.addAttribute("cancelCardList", ssReceiptDao.listSSPayment()); //** Cancelled card List
			status.setComplete();
			return new ModelAndView("redirect:ssrCancelList.htm",model);
		}
	}
	
	/** Ajax get Scheme Type values Based on Scheme Name **/
	@RequestMapping(value="/getSchemeType.htm",method=RequestMethod.GET)
	public @ResponseBody String getAjaxSchemeType(@RequestParam(value="schemeName",required=true)String schemeName){
		String result = "";
		try{
			result = cardIssueDao.getSchemeType(schemeName);			
		}catch(Exception e){	
			System.out.println("FormSSReceiptController :: Ajax get Scheme Type values Based on Scheme Name ::");
			e.printStackTrace();
		}			
		return result;		
	}
	
	/** SSReceipt Ajax get Scheme List values Based on Customer **/
	@RequestMapping(value="/getSchemeNameList.htm",method=RequestMethod.GET)
	public @ResponseBody String getSchemeNameList(@RequestParam(value="customerName",required=true)String CustomerName,@RequestParam(value="entryId",required=true)Integer entryId){
		String finalSchemeList = "";
		try{
			List<CardIssue> cardSchemeList = cardIssueDao.getSSRCustomerSchemeList(CustomerName);
			ArrayList<String> schemeList = new ArrayList<String>();
			schemeList.add(0, "Select");
			if(!cardSchemeList.isEmpty()){
				schemeList.add(1, cardSchemeList.toString());
			}
			if(entryId !=null && entryId != 0){
				Saving_SchemeReceipt currentSSR = ssReceiptDao.getSSReceipt(entryId).get(0);
				CardIssue currentCardNo = cardIssueDao.getCardIssueCardNO(currentSSR.getCardNo()).get(0);
				if(!currentCardNo.getStatus().equalsIgnoreCase("Active")){
					schemeList.add(2, currentCardNo.getSchemeName().trim());
				}
			}
			finalSchemeList = schemeList.toString();				
		}catch(Exception e){	
			System.out.println("FormSSReceiptController Ajax get Scheme List ::");
			e.printStackTrace();
		}		
		return finalSchemeList;		
	}
	
	/** SSPayment Ajax get Scheme List values Based on Customer **/
	@RequestMapping(value="/getSSPSchemeNameList.htm",method=RequestMethod.GET)
	public @ResponseBody String getSSPSchemeNameList(@RequestParam(value="customerName",required=true)String CustomerName,@RequestParam(value="entryId",required=true)Integer entryId){
		String finalSchemeList = "";
		try{
			List<CardIssue> cardSchemeList = cardIssueDao.getSSCCustomerSchemeList(CustomerName);
			ArrayList<String> schemeList = new ArrayList<String>();
			schemeList.add(0, "Select");
			if(!cardSchemeList.isEmpty()){
				schemeList.add(1, cardSchemeList.toString());			
			}
			if(entryId !=null && entryId != 0){
				Saving_SchemeReceipt currentSSR = ssReceiptDao.getSSReceipt(entryId).get(0);
				CardIssue currentCardNo = cardIssueDao.getCardIssueCardNO(currentSSR.getCardNo()).get(0);
				if(!currentCardNo.getStatus().equalsIgnoreCase("Active") && !currentCardNo.getStatus().equalsIgnoreCase("Matured")){
					schemeList.add(2, currentCardNo.getSchemeName().trim());
				}
			}
			finalSchemeList = schemeList.toString();
		}catch(Exception e){	
			System.out.println("FormSSReceiptController Ajax get Scheme List values Based on Customer ::");
			e.printStackTrace();
		}		
		return finalSchemeList;		
	}
	
	
	/** Ajax SSReceipt get Card Number List Based on Customer and Scheme Name **/
	@RequestMapping(value="/getCardDetail.htm",method=RequestMethod.GET)
	public @ResponseBody String getAjaxCardDetails(@RequestParam(value="customerName",required=true)String CustomerName,@RequestParam(value="schemeName",required=true)String SchemeName,@RequestParam(value="entryId",required=true)Integer entryId){
		String result = "";
		try{
			List<CardIssue> cardDetailsList = cardIssueDao.SSRgetCardDetails(CustomerName, SchemeName);
			ArrayList<String> cardList = new ArrayList<String>();
			cardList.add(0, "Select");
			if(!cardDetailsList.isEmpty()){
				cardList.add(1, cardDetailsList.toString());				
			}
			//Current Card No Binding for View Mode
			if(entryId!=null && entryId != 0){
				Saving_SchemeReceipt currentSSR = ssReceiptDao.getSSReceipt(entryId).get(0);
				CardIssue currentCardNo = cardIssueDao.getCardIssueCardNO(currentSSR.getCardNo()).get(0);
				if(SchemeName.equalsIgnoreCase(currentCardNo.getSchemeName()) && !currentCardNo.getStatus().equalsIgnoreCase("Active")){
					cardList.add(2, currentCardNo.getCardNo().trim());
				}
			}
			result = cardList.toString();		
		}catch(Exception e){	
			e.printStackTrace();
			System.out.println("SSReceiptController :: Ajax SSReceipt get Card Number List Based on Customer and Scheme Name::");
		}		
		return result;		
	}
	
	/** Ajax SSPayment get Card Number List Based on Customer and Scheme Name **/
	@RequestMapping(value="/getSSPCardDetail.htm",method=RequestMethod.GET)
	public @ResponseBody String getSSPCardDetail(@RequestParam(value="customerName",required=true)String CustomerName,@RequestParam(value="schemeName",required=true)String SchemeName,@RequestParam(value="entryId",required=true)Integer entryId){
		String result = "";
		try{
			List<CardIssue> cardDetailsList = cardIssueDao.SSCgetCardDetails(CustomerName, SchemeName);
			ArrayList<String> cardList = new ArrayList<String>();
			cardList.add(0, "Select");
			if(!cardDetailsList.isEmpty()){
				cardList.add(1, cardDetailsList.toString());				
			}
			//Current Card No Binding for View Mode
			if(entryId!=null && entryId != 0){
				Saving_SchemeReceipt currentSSR = ssReceiptDao.getSSReceipt(entryId).get(0);
				CardIssue currentCardNo = cardIssueDao.getCardIssueCardNO(currentSSR.getCardNo()).get(0);
				if(SchemeName.equalsIgnoreCase(currentCardNo.getSchemeName()) && !currentCardNo.getStatus().equalsIgnoreCase("Active") && !currentCardNo.getStatus().equalsIgnoreCase("Matured")){
					cardList.add(2, currentCardNo.getCardNo().trim());
				}
			}
			result = cardList.toString();	
		}catch(Exception e){
			System.out.println("SSReceiptController::Ajax SSPayment get Card Number List Based on Customer and Scheme Name ::");
			e.printStackTrace();
		}		
		return result;		
	}
	
	/** Ajax Scheme Calculation Based on Card Number **/
	@RequestMapping(value="/SchemeCalc.htm",method=RequestMethod.GET)
	public @ResponseBody String getAjaxSchemeCalc(@RequestParam(value="cardNo",required=true)String cardNo,@RequestParam(value="formType",required=true)String formType){
		String result = "";
		
		try{
			List<CardIssue> cardNoDetailsList = cardIssueDao.getCardIssueCardNO(cardNo);
			if(!cardNoDetailsList.isEmpty()){
				
				ArrayList<String> cardNoList = new ArrayList<String>();
				cardNoList.add(0, cardNoDetailsList.get(0).getSchemeInAmount().toString());
				cardNoList.add(1, cardNoDetailsList.get(0).getSchemeInGrams().toString());
				BigDecimal boardRate;
				if(!rateMasterDao.listRateMaster().isEmpty()){
					boardRate = rateMasterDao.listRateMaster().get(0).getGoldOrnaments();
				}else{
					boardRate = new BigDecimal("0.00");
				}
				
				// FOR RECEIPT FORM CALCUALTION
				if(formType.equalsIgnoreCase("Saving Scheme Receipt")){
					if(cardNoDetailsList.get(0).getSchemeType().equalsIgnoreCase("Amount")){
						cardNoList.add(2, cardNoDetailsList.get(0).getSchemeInAmount().toString());
					}else{
						BigDecimal grams = new BigDecimal(0.00);
						grams = cardNoDetailsList.get(0).getSchemeInGrams();
						cardNoList.add(2, boardRate.multiply(grams).toString());
					}
				}else{ // FOR PAYMENT FORM CALCULATION
					if(cardNoDetailsList.get(0).getSchemeType().equalsIgnoreCase("Amount")){
						cardNoList.add(2,cardNoDetailsList.get(0).getClosingBalanceInRs().toString());
					}else{
						BigDecimal grams = new BigDecimal(0.00);
						grams = cardNoDetailsList.get(0).getClosingBalanceInGrams();
						cardNoList.add(2, boardRate.multiply(grams).toString());
						cardNoList.add(3, grams.toString());
					}
				}
				result = cardNoList.toString();				
			}
		}catch(Exception e){	
			System.out.println("SSReceiptController :: Ajax Scheme Calculation Based on Card Number ::");
			e.printStackTrace();
		}		
		return result;		
	}
	
	/** Ajax Retrieve Card Details Based on Card Number **/
	@RequestMapping(value="/getSSRCardList.htm",method=RequestMethod.GET)
	public @ResponseBody String getSSRCardList(@RequestParam(value="ssrCardNo",required=true)String ssrCardNo,@RequestParam(value="receiptId",required=true)String receiptId){
		String result = "";
		List<CardIssue> cardNoDetailsList = null;
		try{
			if(receiptId.isEmpty() || receiptId.equalsIgnoreCase("undefined")){
				cardNoDetailsList = cardIssueDao.getCardDetailList(ssrCardNo);// only Active card detail
			}else{
				Saving_SchemeReceipt ssReceipt = ssReceiptDao.getSSReceipt(Integer.parseInt(receiptId)).get(0);
				if(ssReceipt.getCardNo().equalsIgnoreCase(ssrCardNo)){
					cardNoDetailsList = cardIssueDao.getCardIssueCardNO(ssrCardNo);
				}else{
					cardNoDetailsList = cardIssueDao.getCardDetailList(ssrCardNo);
				}
			}
			
			if(cardNoDetailsList != null && !cardNoDetailsList.isEmpty()){
				ArrayList<String> cardNoList = new ArrayList<String>();
				cardNoList.add(0, cardNoDetailsList.get(0).getCustomerName());
				cardNoList.add(1, cardNoDetailsList.get(0).getSchemeName());
				cardNoList.add(2, cardNoDetailsList.get(0).getSchemeType().trim());
				result = cardNoList.toString();				
			}
		}catch(Exception e){	
			System.out.println("SSReceiptController :: Ajax Retrieve Card Details Based on Card Number ::");
			e.printStackTrace();
		}		
		return result;		
	}
}
