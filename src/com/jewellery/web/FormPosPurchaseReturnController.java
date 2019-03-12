package com.jewellery.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.jewellery.dao.JournalDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.dao.POSCategoryDao;
import com.jewellery.dao.POSPuchaseDao;
import com.jewellery.dao.POSStockDao;
import com.jewellery.entity.Journal;
import com.jewellery.entity.Ledger;
import com.jewellery.entity.POSCategory;
import com.jewellery.entity.POSP;
import com.jewellery.entity.POSPurchase;
import com.jewellery.entity.POSPurchaseItem;
/*import com.jewellery.entity.POSStock;*/
import com.jewellery.entity.PostagItem;
import com.jewellery.util.JournalCode;
import com.jewellery.validator.POSPurchasereturn;

import com.jewellery.dao.PostagItemDao;

@Controller
public class FormPosPurchaseReturnController extends JournalCode {

	@Autowired(required = false)
	POSPuchaseDao posPurchaseDao;
	@Autowired(required = false)
	POSStockDao posStockDao;
	@Autowired(required = false)
	PostagItemDao postagItemDao;
	@Autowired(required = false)
	LedgerDao ledgerDao;
	@Autowired(required = false)
	JournalDao journalDao;
	@Autowired
	POSCategoryDao poscategoryDao;
	@Autowired(required = false)
	 private POSPurchasereturn POSPurValidator; 
	/** Pospurchase return form**/
	BigDecimal clBal;
	BigDecimal closingAmount;
	BigDecimal totalamt;
	BigDecimal partyBal_EVAL = new BigDecimal("0.00");
	/** partyBal_EVAL existing party val **/
	BigDecimal partyBal_updatedVal = new BigDecimal("0.00");
	/** partyBal_updatedVal party val **/
	// BigDecimal ZERO=new BigDecimal("0");
	// BigDecimal CONVERT=new BigDecimal("-1");
	String partyName;
	/** party balance updations while doing pos purchase return **/
	String ledgername;
	/** pos purchase return leadger updation **/
	String clType;
	/** pos purchase leader closing balance type **/
	String closingType = "Debit";
	String closingTyp = "Credit";
	String ptype;
	String payment_mode;
	String finalcltype;
	
	BigDecimal convert = new BigDecimal("-1");
	BigDecimal zero = new BigDecimal("0");
	
	private Journal jrnl;
	
	List<String> ledgerGroupCode;

	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		CustomDateEditor dateEditor = new CustomDateEditor(
				new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class, null, dateEditor);
	}

	@RequestMapping(value = "/formPOSPurchaseReturn.htm", method = RequestMethod.GET)
	public ModelAndView showForm(@ModelAttribute("pospCommand") POSP posp,
			@ModelAttribute("posCategory") POSCategory posCategory,ModelMap model) {
		ModelAndView mav = new ModelAndView();
		List<?> ledgerlist = ledgerDao.listallSmithName();    
		model.addAttribute("suppliername", ledgerlist);		
		mav.addObject("pospCommand", new POSP());
		return new ModelAndView("formPOSPurchaseReturn");
	}

	/** For Insert **/
	@RequestMapping(value = "/formPOSPurchaseReturn.htm", method = RequestMethod.POST, params = "insert")
	public ModelAndView saveForm(@ModelAttribute("pospCommand") POSP posp,
			BindingResult result, ModelMap model) {

		
		// $$$$$$$ POS Purchase Entity Validation $$$$$$ //
				POSPurValidator.validateInsert(posp.getPosp(), result);
				POSPurValidator.validate(posp.getPosp(),posp.getListpospurchase(), result);
				List<POSPurchaseItem> listpos = posp.getListpospurchase();
				List<?> ledgerlist = ledgerDao.listallSmithName();    
				model.addAttribute("suppliername", ledgerlist);	
				/**************delete dynamic rows*****************/
				Iterator<POSPurchaseItem> iter= listpos.iterator();
				while(iter.hasNext()) {	
					POSPurchaseItem popItem = iter.next();
					if(popItem.isDeleted()==true||((popItem.getCategoryName() == null || popItem.getCategoryName().length() == 0 ) && (popItem.getItemName() == null || popItem.getItemName().length() == 0))){
						iter.remove();
						listpos.remove(popItem);
						posp.setListpospurchase(listpos);						
					}
				}

		 if (result.hasErrors()) 
		 {
			 posp.setPosp(posp.getPosp());
			 posp.setListpospurchase(posp.getListpospurchase());
			 model.addAttribute("popPurchaseList", listpos);
			 model.addAttribute("errorName", "insertError");
			 model.addAttribute(posp);
			 return new ModelAndView("formPOSPurchaseReturn",model);
		 
		 }
		
		 /** For Storing Row status **/
			for(POSPurchaseItem rPOPItem : posp.getListpospurchase()){
				rPOPItem.setRowStatus("Persisted");
				rPOPItem.setItemStatus("Purchase Return");
			}
		
		posPurchaseDao.insertPOSPurchase(posp.getPosp());
		
		int qtyset = 0;

		ptype = posp.getPosp().getPurchaseType();
		/** Getting pos purchase type **/ 
		payment_mode = posp.getPosp().getPaymentType();
		closingAmount = posp.getPosp().getGrandAmount();
		
		/** Getting Grant total **/ 
		partyName = posp.getPosp().getSupplierName();
		ledgername = "POS Purchase Account";

		if (!listpos.isEmpty()) {
			for (POSPurchaseItem objpos : listpos) {
				
				String barcode = objpos.getBarcode();
							
				if (!objpos.equals(null)) {
					objpos.setPospurchase(posp.getPosp());					
					posPurchaseDao.insertPOSPurchaseItem(objpos);
					poscategoryDao.updateCategorySet(-1, objpos.getCategoryName().trim());						
					postagItemDao.updateSoldTagItem(qtyset, "Purchase Return", barcode);									
				}				
			}
		}
		/********* POS Purchase Account ledger updation if type is cash ********/
		if (payment_mode.equalsIgnoreCase("Cash")) {
			/******* pos Purchase ledger updation *******/
			List<Ledger> getposreturnLedger = ledgerDao.searchLedger("POS Purchase Account");
			clBal = getposreturnLedger.get(0).getClosingTotalBalance();
			clType = getposreturnLedger.get(0).getClosingTotalType();
			if (clType.equalsIgnoreCase("Credit")) {
				clBal = zero.subtract(clBal);
			}
			BigDecimal dropClBalanceParty = clBal.subtract(posp.getPosp().getGrandAmount());

			if (dropClBalanceParty.signum() == -1) {
				finalcltype = "Credit";
				dropClBalanceParty = dropClBalanceParty.multiply(convert);
			} else {
				finalcltype = "Debit";

			}
			ledgerDao.updateLedgerPartyBalance(dropClBalanceParty, finalcltype,"POS Purchase Account");
			/************ Cash ledger updation ************/
			
			List<Ledger> cashLedger = ledgerDao.searchLedger("Cash Account");
			clBal = cashLedger.get(0).getClosingTotalBalance();
			clType = cashLedger.get(0).getClosingTotalType();
			if (clType.equalsIgnoreCase("Credit")) {
				clBal = zero.subtract(clBal);
			}
			BigDecimal dropClBalancePar = clBal.add(posp.getPosp().getGrandAmount());

			if (dropClBalancePar.signum() == -1) {
				finalcltype = "Credit";
				dropClBalancePar = dropClBalancePar.multiply(convert);
			} else {
				finalcltype = "Debit";

			}
			ledgerDao.updateLedgerPartyBalance(dropClBalancePar, finalcltype,"Cash Account");
			/***************** pos purchase return new journal entry if type is Credit ***************/
			jrnl = new Journal();
			jrnl.setJournalNO(getJournalNumber(jrnl));
			jrnl.setJournalType("POS Purchase Return");
			jrnl.setJournalDate(posp.getPosp().getPdate());
			
			// Set Account group code as Debit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode(posp.getPosp().getSupplierName());			
			jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
			jrnl.setDebitAccountName(posp.getPosp().getSupplierName());
			
			jrnl.setTransactionId("POP" + posp.getPosp().getPurchaseId());
			
			// Set Account group code as Credit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode("POS Purchase Account");
			jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
			jrnl.setCreditAccountName("POS Purchase Account");
			
			jrnl.setDebitAmount(closingAmount);
			jrnl.setCreditAmount(closingAmount);
			jrnl.setNarration("Bill No " + posp.getPosp().getPurchaseId());
			journalDao.insertJournal((Journal) jrnl);
			
			/** Receipt journal entry **/
			jrnl = new Journal();
			jrnl.setJournalType("POS Purchase Return Receipt");
			jrnl.setJournalNO(getJournalNumber(jrnl));
			jrnl.setJournalDate(posp.getPosp().getPdate());
			
			// Set Account group code as Debit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");			
			jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
			jrnl.setDebitAccountName("Cash Account");			
			
			// Set Account group code as Credit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode(posp.getPosp().getSupplierName());
			jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
			jrnl.setCreditAccountName(posp.getPosp().getSupplierName());			
			
			jrnl.setTransactionId("POP" + posp.getPosp().getPurchaseId());
			jrnl.setDebitAmount(closingAmount);
			jrnl.setCreditAmount(closingAmount);
			jrnl.setNarration("Bill No " + posp.getPosp().getPurchaseId());
			journalDao.insertJournal((Journal) jrnl);

		}

		/************ POS Purchase Account ledger updation if type is Credit ****************/

		if (payment_mode.equalsIgnoreCase("Credit")) {
			List<Ledger> getposreturnLedger = ledgerDao.searchLedger("POS Purchase Account");
			clBal = getposreturnLedger.get(0).getClosingTotalBalance();
			clType = getposreturnLedger.get(0).getClosingTotalType();
			if (clType.equalsIgnoreCase("Credit")) {
				clBal = zero.subtract(clBal);
			}
			BigDecimal dropClBalanceParty = clBal.subtract(posp.getPosp().getGrandAmount());

			if (dropClBalanceParty.signum() == -1) {
				finalcltype = "Credit";
				dropClBalanceParty = dropClBalanceParty.multiply(convert);
			} else {
				finalcltype = "Debit";

			}
			ledgerDao.updateLedgerPartyBalance(dropClBalanceParty, finalcltype,"POS Purchase Account");
			/******** pos purchase return party balance updation if type is Credit *******/
			
			List<Ledger> partyBal = ledgerDao.searchLedger(partyName);
			clBal = partyBal.get(0).getClosingTotalBalance();
			clType = partyBal.get(0).getClosingTotalType();
			
			if (clType.equals("Debit")) {
				ledgerDao.updatePartyBalance(closingAmount, closingType,partyName);
			} else {
				if (clBal.compareTo(closingAmount) == 1) {

					ledgerDao.updateCreditPartyBalance(closingAmount,closingTyp, partyName);
				} else {

					ledgerDao.updateCrPartyBalance(closingAmount, closingType,partyName);
				}
			}
			/********* pos purchase return new journal entry if type is Credit ********/
			jrnl = new Journal();
			jrnl.setJournalNO(getJournalNumber(jrnl));
			jrnl.setJournalType("POS Purchase Return");
			jrnl.setJournalDate(posp.getPosp().getPdate());
			
			// Set Account group code as Debit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode(posp.getPosp().getSupplierName());			
			jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
			jrnl.setDebitAccountName(posp.getPosp().getSupplierName());
			
			jrnl.setTransactionId("POP" + posp.getPosp().getPurchaseId());
			
			// Set Account group code as Credit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode("POS Purchase Account");
			jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
			jrnl.setCreditAccountName("POS Purchase Account");
			
			jrnl.setDebitAmount(closingAmount);
			jrnl.setCreditAmount(closingAmount);
			jrnl.setNarration("Bill No " + posp.getPosp().getPurchaseId());
			journalDao.insertJournal((Journal) jrnl);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("pospCommand", new POSP());
		return new ModelAndView("redirect:POSPurchase.htm");
	}

	/** Updating the purchase Return field value for the same customer name **/
	/** For Cancel **/
	@RequestMapping(value = "/formPOSPurchaseReturn.htm", method = RequestMethod.POST, params = "cancel")
	public String cancelForm() {
		return "redirect:POSPurchase.htm";
	}

	/** purchase return update **/
	@RequestMapping(value = "/formPOSPurchaseReturn.htm", method = RequestMethod.POST, params = "update")
	public ModelAndView posreturnUpdate(@ModelAttribute("pospCommand") POSP posp,BindingResult result, ModelMap model) {
		
		Integer purchaseId = posp.getPosp().getPurchaseId();
		POSPurchase posobj = posPurchaseDao.getPOSPurchase(purchaseId);
		List<POSPurchaseItem> listposOld = posPurchaseDao.getPOSPurchaseItem(purchaseId);
		List<POSPurchaseItem> listpos = posp.getListpospurchase();
		POSPurchase posPurchaseOld = posPurchaseDao.getPOSPurchase(purchaseId);
		POSPurchase posPurchaseNew = posp.getPosp();
		
		// $$$$$$$ POS Purchase Entity Validation $$$$$$ //
		POSPurValidator.validate(posp.getPosp(), posp.getListpospurchase(), result);
		POSPurValidator.validateUpdate(posPurchaseOld, posPurchaseNew, listposOld, listpos, result);
		
		/**************delete dynamic rows*****************/
		Iterator<POSPurchaseItem> iter= listpos.iterator();
		while(iter.hasNext()) {	
			POSPurchaseItem popItem = iter.next();
			if(popItem.getBarcode()==null && (popItem.getCategoryName() == null || popItem.getCategoryName().length() == 0 ) && (popItem.getItemName() == null || popItem.getItemName().length() == 0) ){
				iter.remove();
				listpos.remove(popItem);
				posp.setListpospurchase(listpos);				
			}			
		}
		
		List<?> ledgerlist = ledgerDao.listallSmithName();    
		model.addAttribute("suppliername", ledgerlist);	
		if (result.hasErrors()) {
			posp.setPosp(posPurchaseNew);
			posp.setListpospurchase(listpos);
			listpos = posp.getListpospurchase();
			model.addAttribute("popPurchaseList", listpos);
			model.addAttribute("errorName", "updateError");
			model.addAttribute(posp);
			return new ModelAndView("formPOSPurchaseReturn", model);
		}
			for(POSPurchaseItem item:listpos)
			{			 
				 
				if(item.getPiecesPerSet()==0)
				{		
					String delededBarcodeId=item.getBarcode();
					List<PostagItem> existingBarcode=postagItemDao.getItemCode(delededBarcodeId);
					for(PostagItem tag:existingBarcode)
					{
						if(tag.getQtyset()==0)
						{
							String categoryName=tag.getCategoryName();						
							postagItemDao.refilldeletedBarcode(delededBarcodeId);
							poscategoryDao.updateCategorySet(1, categoryName.trim());
						}
					}					
				}				
			}
			List<POSPurchaseItem> listposNew = posp.getListpospurchase();
		
		for (POSPurchaseItem newposobj : listposNew) {	
					
			//** For new Row added while Update **/
			if (newposobj.getPurchaseItemID() == null || newposobj.getPurchaseItemID().equals("")) {
				 /** For Storing Row status **/
				for(POSPurchaseItem rPOPItem : listposOld){
					rPOPItem.setRowStatus("Persisted");
					rPOPItem.setItemStatus("Purchase Return");
				}
		
				newposobj.setPospurchase(posp.getPosp());
				int qtyset = 0;
				String barcode = newposobj.getBarcode(); 
				newposobj.setRowStatus("Persisted");
				newposobj.setItemStatus("Purchase Return"); 
				posPurchaseDao.insertPOSPurchaseItem(newposobj);			
				if(newposobj.isDeleted != true){
					postagItemDao.updateSoldTagItem(qtyset, "Purchase Return ", barcode);
					poscategoryDao.updateCategorySet(-1, newposobj.getCategoryName().trim());//Update Category Quantity
				}
			
			} else { 
								
				for (POSPurchaseItem oldposobj : listposOld) {  
									
					if (newposobj.getPurchaseItemID().equals(oldposobj.getPurchaseItemID())) {						
						newposobj.setPospurchase(posp.getPosp());
						newposobj.setItemStatus("Purchase Return");
						posPurchaseDao.updatePOSPurchaseItem(newposobj);			
					}				
				}
			}
	}
		String oldSupplier = posobj.getSupplierName();
		String newSupplier = posp.getPosp().getSupplierName();
		String paymentType = posp.getPosp().getPaymentType();
		String oldPaymenttype = posobj.getPaymentType();
		BigDecimal oldGrantot = posobj.getGrandAmount();
		BigDecimal newGrantot = posp.getPosp().getGrandAmount();
		
		
		/**************Update same supplier name and payement mode is cash**********/
			if (paymentType.equals("Cash")&&oldPaymenttype.equals("Cash")) {
				if (oldSupplier.equalsIgnoreCase(newSupplier)) {
				/***********update for POS Purchase Accoun if grant amount changedt*********/
				List<Ledger> getposreturnLedger = ledgerDao.searchLedger("POS Purchase Account");
				clBal = getposreturnLedger.get(0).getClosingTotalBalance();
				clType = getposreturnLedger.get(0).getClosingTotalType();
				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}
				BigDecimal dropClBalanceParty = clBal.add(oldGrantot);
				BigDecimal finalClBalanceParty = dropClBalanceParty.subtract(newGrantot);
				if (finalClBalanceParty.signum() == -1) { 
					finalcltype = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";

				}
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype, "POS Purchase Account");
				
				/***********update for Cash Account if grant amount changed*********/
				List<Ledger> cashLedger = ledgerDao.searchLedger("Cash Account");
				clBal = cashLedger.get(0).getClosingTotalBalance();
				clType = cashLedger.get(0).getClosingTotalType();
				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}
				dropClBalanceParty = clBal.subtract(oldGrantot);
				finalClBalanceParty = dropClBalanceParty.add(newGrantot);
				if (finalClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";
				}
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype, "Cash Account");
				
				/********POS Purchase Return Journal entry update if grant amount change *********/
				List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("POS Purchase Return", "POP"+ purchaseId);
				if (!jrnlListPurchaseCash.isEmpty()) {
					jrnlListPurchaseCash.get(0).setDebitAmount(newGrantot);
					jrnlListPurchaseCash.get(0).setCreditAmount(newGrantot);
					jrnlListPurchaseCash.get(0).setJournalDate(posp.getPosp().getPdate());
					journalDao.updateJournal(jrnlListPurchaseCash.get(0));
				}
				/******Receipt Journal entry update if grant amount change********/
				List<Journal> jrnlListReceipt = journalDao.getJournalUpdateSales("POS Purchase Return Receipt", "POP"+ purchaseId);
				if (!jrnlListReceipt.isEmpty()) {
					jrnlListReceipt.get(0).setDebitAmount(newGrantot);
					jrnlListReceipt.get(0).setCreditAmount(newGrantot);
					jrnlListReceipt.get(0).setJournalDate(posp.getPosp().getPdate());
					journalDao.updateJournal(jrnlListReceipt.get(0));
				}
			}
			/**********If supplier name changed while update mode paymenttype is cash************/
				 if (!oldSupplier.equalsIgnoreCase(newSupplier)) {
				/************** updating POS Purchase Account ******************/
				List<Ledger> getposreturnLedger = ledgerDao.searchLedger("POS Purchase Account");
				clBal = getposreturnLedger.get(0).getClosingTotalBalance();
				clType = getposreturnLedger.get(0).getClosingTotalType();
				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}
				BigDecimal dropClBalanceParty = clBal.add(oldGrantot);
				BigDecimal finalClBalanceParty = dropClBalanceParty
						.subtract(newGrantot);
				if (finalClBalanceParty.signum() == -1) { 
					finalcltype = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";

				}
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype, "POS Purchase Account");
				
				/************** updating Cash Account ******************/
				List<Ledger> cashLedger = ledgerDao.searchLedger("Cash Account");
				clBal = cashLedger.get(0).getClosingTotalBalance();
				clType = cashLedger.get(0).getClosingTotalType();
				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}
				dropClBalanceParty = clBal.subtract(oldGrantot);
				finalClBalanceParty = dropClBalanceParty.add(newGrantot);
				if (finalClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";
				}
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype, "Cash Account");
				/********POS Purchase Return Journal entry update if supplier name is changed *********/
				List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("POS Purchase Return", "POP"+ purchaseId);
				if (!jrnlListPurchaseCash.isEmpty()) {
					jrnlListPurchaseCash.get(0).setDebitAmount(newGrantot);
					jrnlListPurchaseCash.get(0).setCreditAmount(newGrantot);
					jrnlListPurchaseCash.get(0).setJournalDate(posp.getPosp().getPdate());
					jrnlListPurchaseCash.get(0).setDebitAccountName(newSupplier);
					journalDao.updateJournal(jrnlListPurchaseCash.get(0));
				}
				/******Receipt Journal entry update if supplier  name is changed********/
				List<Journal> jrnlListReceipt = journalDao.getJournalUpdateSales("POS Purchase Return Receipt", "POP"+ purchaseId);
				if (!jrnlListReceipt.isEmpty()) {
					jrnlListReceipt.get(0).setDebitAmount(newGrantot);
					jrnlListReceipt.get(0).setCreditAmount(newGrantot);
					jrnlListReceipt.get(0).setCreditAccountName(newSupplier);
					jrnlListReceipt.get(0).setJournalDate(posp.getPosp().getPdate());
					journalDao.updateJournal(jrnlListReceipt.get(0));
				}
			}

		}
		/********** payment mode is credit update party balance and ledger ***********/
		if (paymentType.equals("Credit")&&oldPaymenttype.equals("Credit")) {
			if (oldSupplier.equalsIgnoreCase(newSupplier)) {
				/******* update party balance of the pos purchase return supplier *******/
				List<Ledger> partyBal = ledgerDao.searchLedger(oldSupplier);
				clBal = partyBal.get(0).getClosingTotalBalance();
				clType = partyBal.get(0).getClosingTotalType();
				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}
				BigDecimal dropClBalanceParty = clBal.subtract(oldGrantot);
				BigDecimal finalClBalanceParty = dropClBalanceParty.add(newGrantot);
				if (finalClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";

				}
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype, oldSupplier);
				/** update pos purchase ledger updation **/
				List<Ledger> partyledger = ledgerDao.searchLedger("POS Purchase Account");
				clBal = partyledger.get(0).getClosingTotalBalance();
				clType = partyledger.get(0).getClosingTotalType();
				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}
				dropClBalanceParty = clBal.add(oldGrantot);
				finalClBalanceParty = dropClBalanceParty.subtract(newGrantot);
				if (finalClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";

				}
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype, "POS Purchase Account");

				/** updating corresponding journal entry **/
				List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("POS Purchase Return", "POP"+ purchaseId);
				if (!jrnlListPurchaseCash.isEmpty()) {
					jrnlListPurchaseCash.get(0).setDebitAmount(newGrantot);
					jrnlListPurchaseCash.get(0).setCreditAmount(newGrantot);
					jrnlListPurchaseCash.get(0).setJournalDate(posp.getPosp().getPdate());
					journalDao.updateJournal(jrnlListPurchaseCash.get(0));
				}
			}
			/******* if supplier name changing and bill amount change *******/
			 if (!oldSupplier.equalsIgnoreCase(newSupplier)) {
				if (oldGrantot.compareTo(newGrantot) != 0) {
					/******* UPDATE OLD SUPPLIER PARTY BALANCE ********/
					List<Ledger> oldsuplier = ledgerDao.searchLedger(oldSupplier);
					clBal = oldsuplier.get(0).getClosingTotalBalance();
					clType = oldsuplier.get(0).getClosingTotalType();
					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}
					BigDecimal dropClBalanceParty = clBal.subtract(oldGrantot);

					if (dropClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						dropClBalanceParty = dropClBalanceParty.multiply(convert);
					} else {
						finalcltype = "Debit";

					}
					ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,finalcltype, oldSupplier);
					/******* UPDATE NEW SUPPLIER PARTY BALANCE ********/
					List<Ledger> newsuplier = ledgerDao.searchLedger(newSupplier);
					clBal = newsuplier.get(0).getClosingTotalBalance();
					clType = newsuplier.get(0).getClosingTotalType();
					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}
					dropClBalanceParty = clBal.add(newGrantot);

					if (dropClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						dropClBalanceParty = dropClBalanceParty
								.multiply(convert);  
					} else {
						finalcltype = "Debit";

					}
					ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,finalcltype, newSupplier);
					/*********** UPDATE NEW PURCHASE LEDGER BALANCE **********/
					List<Ledger> creidpartyLedger = ledgerDao.searchLedger("POS Purchase Account");
					clBal = creidpartyLedger.get(0).getClosingTotalBalance();
					
					clType = creidpartyLedger.get(0).getClosingTotalType();
					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}
					dropClBalanceParty = clBal.add(oldGrantot);
					BigDecimal finalClBalanceParty = dropClBalanceParty.subtract(newGrantot);
					if (finalClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						finalClBalanceParty = finalClBalanceParty.multiply(convert);
					} else {
						finalcltype = "Debit";

					}
					ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype, "POS Purchase Account");
					/************* updating corresponding journal entry *************/
					List<Journal> jrnlListPurchaseCash = journalDao
							.getJournalUpdateSales("POS Purchase Return", "POP"
									+ purchaseId);
					if (!jrnlListPurchaseCash.isEmpty()) {
						jrnlListPurchaseCash.get(0).setDebitAmount(newGrantot);
						jrnlListPurchaseCash.get(0).setCreditAmount(newGrantot);
						jrnlListPurchaseCash.get(0).setDebitAccountName(newSupplier);
						jrnlListPurchaseCash.get(0).setJournalDate(posp.getPosp().getPdate());
						journalDao.updateJournal(jrnlListPurchaseCash.get(0));
					}
				}
			}
		}
		/************ CASH TO CREDIT ******************/
				if (oldSupplier.equalsIgnoreCase(newSupplier)) {
				if (oldPaymenttype.equalsIgnoreCase("Cash") && paymentType.equalsIgnoreCase("Credit")) {
				
				/*********** party balance updation *************/
				List<Ledger> partycashtocredit = ledgerDao.searchLedger(oldSupplier);
				clBal = partycashtocredit.get(0).getClosingTotalBalance();
				clType = partycashtocredit.get(0).getClosingTotalType();
				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}
				BigDecimal dropClBalanceParty = clBal.add(newGrantot);

				if (dropClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					dropClBalanceParty = dropClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";

				}
				ledgerDao.updateLedgerPartyBalance(dropClBalanceParty, finalcltype, oldSupplier);
				/************ updating pos purchase ledger amount *************/
				List<Ledger> searchLedger = ledgerDao.searchLedger("POS Purchase Account");
				clBal = searchLedger.get(0).getClosingTotalBalance();
				clType = searchLedger.get(0).getClosingTotalType();
				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}
				dropClBalanceParty = clBal.add(oldGrantot);
				BigDecimal finalClBalanceParty = dropClBalanceParty.subtract(newGrantot);
				if (finalClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";
 
				}
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty, finalcltype, "POS Purchase Account");
				/************ cash ledger updation ************/
				List<Ledger> cashLedger = ledgerDao.searchLedger("Cash Account");
				clBal = cashLedger.get(0).getClosingTotalBalance();
				clType = cashLedger.get(0).getClosingTotalType();
				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}
				dropClBalanceParty = clBal.subtract(oldGrantot);

				if (dropClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					dropClBalanceParty = dropClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";

				}
				ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,finalcltype, "Cash Account");
				/************* updating corresponding journal entry ************/
				List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("POS Purchase Return", "POP"+ purchaseId);
				if (!jrnlListPurchaseCash.isEmpty()) {
					jrnlListPurchaseCash.get(0).setDebitAmount(newGrantot);
					jrnlListPurchaseCash.get(0).setCreditAmount(newGrantot);
					journalDao.updateJournal(jrnlListPurchaseCash.get(0));
					List<Journal> jrnlListRecipt = journalDao.getJournalUpdateSales("POS Purchase Return Receipt", "POP"+ purchaseId);
					if(!jrnlListRecipt.isEmpty())
					{
					journalDao.deleteJournal(jrnlListRecipt.get(0));	
					}
					
				}
			}
		}
		/*********** for changin supplier name and cash to credit ************/
					if (!oldSupplier.equalsIgnoreCase(newSupplier)) {
			if (oldPaymenttype.equalsIgnoreCase("Cash") && paymentType.equalsIgnoreCase("Credit")) {
				/********* party balance updation ********/
				List<Ledger> partycashtocredit = ledgerDao.searchLedger(newSupplier);
				clBal = partycashtocredit.get(0).getClosingTotalBalance();
				clType = partycashtocredit.get(0).getClosingTotalType();
				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}
				BigDecimal dropClBalanceParty = clBal.add(newGrantot);

				if (dropClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					dropClBalanceParty = dropClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";

				}
				ledgerDao.updateLedgerPartyBalance(dropClBalanceParty, finalcltype, newSupplier);
				/********* pos purchase ledger updation *************/
				List<Ledger> searchLedger = ledgerDao.searchLedger("POS Purchase Account");
				clBal = searchLedger.get(0).getClosingTotalBalance();
				clType = searchLedger.get(0).getClosingTotalType();
				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}
				dropClBalanceParty = clBal.add(oldGrantot);
				BigDecimal finalClBalanceParty = dropClBalanceParty.subtract(newGrantot);
				if (finalClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";

				}

				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty, finalcltype, "POS Purchase Account");
				/*********** cash ledger updation *************/
				List<Ledger> cashLedger = ledgerDao.searchLedger("Cash Account");
				clBal = cashLedger.get(0).getClosingTotalBalance();
				clType = cashLedger.get(0).getClosingTotalType();
				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}
				dropClBalanceParty = clBal.subtract(oldGrantot);

				if (dropClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					dropClBalanceParty = dropClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";

				}
				ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,finalcltype, "Cash Account");
				/************ updating corresponding journal entry ************/
				List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("POS Purchase Return", "POP"+ purchaseId);
				if (!jrnlListPurchaseCash.isEmpty()) {
					jrnlListPurchaseCash.get(0).setDebitAmount(newGrantot);
					jrnlListPurchaseCash.get(0).setCreditAmount(newGrantot);
					jrnlListPurchaseCash.get(0).setJournalDate(posp.getPosp().getPdate());
					
					journalDao.updateJournal(jrnlListPurchaseCash.get(0));
					List<Journal> jrnlListRecipt = journalDao.getJournalUpdateSales("POS Purchase Return Receipt", "POP"+ purchaseId);
					if(!jrnlListRecipt.isEmpty())
					{
					journalDao.deleteJournal(jrnlListRecipt.get(0));	
					}
					
				}
			}
		}
		/*************** CREDIT TO CASH FOR SAME SUPPLIER*******************/
				 if (oldSupplier.equalsIgnoreCase(newSupplier)) {
			if (oldPaymenttype.equals("Credit") && paymentType.equals("Cash")) {
				/************ party balance updation **********/
				List<Ledger> partycashtocredit = ledgerDao.searchLedger(oldSupplier);
				clBal = partycashtocredit.get(0).getClosingTotalBalance();
				clType = partycashtocredit.get(0).getClosingTotalType();
				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}
				BigDecimal dropClBalanceParty = clBal.subtract(oldGrantot);

				if (dropClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					dropClBalanceParty = dropClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";
				}
				ledgerDao.updateLedgerPartyBalance(dropClBalanceParty ,finalcltype, oldSupplier);
				/********** updating pos purchase ledger amount ********/
				List<Ledger> searchLedger = ledgerDao.searchLedger("POS Purchase Account");
				clBal = searchLedger.get(0).getClosingTotalBalance();
				clType = searchLedger.get(0).getClosingTotalType();
				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}

				dropClBalanceParty = clBal.add(oldGrantot);
				BigDecimal finalClBalanceParty = dropClBalanceParty.subtract(newGrantot);

				if (finalClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";

				}

				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty, finalcltype, "POS Purchase Account");
				/*********************** cash ledger updation *******************/
				List<Ledger> cashLedger = ledgerDao.searchLedger("Cash Account");
				clBal = cashLedger.get(0).getClosingTotalBalance();
				clType = cashLedger.get(0).getClosingTotalType();
				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}
				dropClBalanceParty = clBal.add(newGrantot);

				if (dropClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					dropClBalanceParty = dropClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";
				}

				ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,finalcltype, "Cash Account");

				/******************************** updating corresponding journal entry ****************************/
				List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("POS Purchase", "POP"+ purchaseId);
				if (!jrnlListPurchaseCash.isEmpty()) {
					jrnlListPurchaseCash.get(0).setDebitAmount(newGrantot);
					jrnlListPurchaseCash.get(0).setCreditAmount(newGrantot);
					journalDao.updateJournal(jrnlListPurchaseCash.get(0));
					/************************* Second Entry of Receipt ************************/
					}
				/**************new Receipt entry if payement type has changed from credit to cash******************/
				jrnl = new Journal();
				jrnl.setJournalType("POS Purchase Return Receipt");
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalDate(posp.getPosp().getPdate());
				
				// Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");			
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
				jrnl.setDebitAccountName("Cash Account");
				
				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(posp.getPosp().getSupplierName());
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName(posp.getPosp().getSupplierName());
				
				jrnl.setTransactionId("POP" + posp.getPosp().getPurchaseId());
				jrnl.setDebitAmount(newGrantot);
				jrnl.setCreditAmount(newGrantot);
				jrnl.setNarration("POS Purchase Return Bill No " + posp.getPosp().getPurchaseId());
				journalDao.insertJournal((Journal) jrnl);
			}
		}
		/********************** for changin supplier name and credit to cash******************************/
			if (!oldSupplier.equalsIgnoreCase(newSupplier)) {
			if (oldPaymenttype.equals("Credit") && paymentType.equals("Cash")) {
				/********************* party balance updation **************/
				List<Ledger> partycashtocredit = ledgerDao.searchLedger(oldSupplier);
				clBal = partycashtocredit.get(0).getClosingTotalBalance();
				clType = partycashtocredit.get(0).getClosingTotalType();
				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}
				BigDecimal dropClBalanceParty = clBal.subtract(oldGrantot);

				if (dropClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					dropClBalanceParty = dropClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";

				}

				ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,finalcltype, oldSupplier);
				/*************** pos purchase ledger updation *******************/
				List<Ledger> searchLedger = ledgerDao.searchLedger("POS Purchase Account");
				clBal = searchLedger.get(0).getClosingTotalBalance();
				clType = searchLedger.get(0).getClosingTotalType();
				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}
				dropClBalanceParty = clBal.add(oldGrantot);
				BigDecimal finalClBalanceParty = dropClBalanceParty.subtract(newGrantot);
				if (finalClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";

				}
				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype, "POS Purchase Account");
				/****************** cash ledger updation **********************/
				List<Ledger> cashLedger = ledgerDao.searchLedger("Cash Account");
				clBal = cashLedger.get(0).getClosingTotalBalance();
				clType = cashLedger.get(0).getClosingTotalType();
				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal); 
				}
				dropClBalanceParty = clBal.add(newGrantot);
				if (dropClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					dropClBalanceParty = dropClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";

				}
				ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,finalcltype, "Cash Account");
			/***************** updating corresponding journal entry *********************/
				List<Journal> jrnlListPurchaseCash = journalDao.getJournalUpdateSales("POS Purchase", "POP"+ purchaseId);
				if (!jrnlListPurchaseCash.isEmpty()) {
					jrnlListPurchaseCash.get(0).setDebitAmount(newGrantot);
					jrnlListPurchaseCash.get(0).setCreditAmount(newGrantot);
					journalDao.updateJournal(jrnlListPurchaseCash.get(0));
				}
				/**************new Receipt entry if payment type has changed from credit to cash*****************/
				jrnl = new Journal();
				jrnl.setJournalType("POS Purchase Return Receipt");
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalDate(posp.getPosp().getPdate());
				
				// Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");			
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
				jrnl.setDebitAccountName("Cash Account");
				
				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(posp.getPosp().getSupplierName());
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName(posp.getPosp().getSupplierName());
				
				jrnl.setTransactionId("POP" + posp.getPosp().getPurchaseId());
				jrnl.setDebitAmount(newGrantot);
				jrnl.setCreditAmount(newGrantot);
				jrnl.setNarration("POS Purchase Return Bill No " + posp.getPosp().getPurchaseId());
				journalDao.insertJournal((Journal) jrnl);
			}
		}
		posPurchaseDao.updatePOSPurchase(posp.getPosp());
		return  new ModelAndView("redirect:POSPurchase.htm");
	}

	/** purchase return barcode search from stock **/
	@RequestMapping(value = "/posbarcodesearch.htm", method = RequestMethod.POST)
	public @ResponseBody
			String posIcodesearch(
			@RequestParam(value = "posIcode", required = true) String posIcode) {
		ArrayList<String> l1 = new ArrayList<String>();
		String icodeDetail1 = "Invalid code";
		try {
			
			List<PostagItem> getIcodevalues = postagItemDao.getItemCode(posIcode);
			if(getIcodevalues.get(0).getQtyset()!=0)				
			l1.add(0, getIcodevalues.get(0).getBarcodeId().toString());
			l1.add(1, getIcodevalues.get(0).getCategoryName().toString());
			l1.add(2, getIcodevalues.get(0).getItemName().toString());
			l1.add(3, getIcodevalues.get(0).getQtyset().toString());
			l1.add(4, getIcodevalues.get(0).getPps().toString());
			l1.add(5, getIcodevalues.get(0).getCostRate().toString());
			icodeDetail1 = l1.toString();
		} 
		  catch (NullPointerException e) {

		} 
		
		catch (IndexOutOfBoundsException e) {
		}
			return icodeDetail1;
		}	
		
}
