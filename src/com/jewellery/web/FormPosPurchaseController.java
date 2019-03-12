package com.jewellery.web;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.jewellery.dao.AccountsDao;
import com.jewellery.dao.JournalDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.dao.POSCategoryDao;
import com.jewellery.dao.POSItemDao;
import com.jewellery.dao.POSPuchaseDao;
import com.jewellery.dao.PostagItemDao;
import com.jewellery.entity.Accounts;
import com.jewellery.entity.Journal;
import com.jewellery.entity.Ledger;
import com.jewellery.entity.POSCategory;
import com.jewellery.entity.POSItem;
import com.jewellery.entity.POSP;
import com.jewellery.entity.POSPurchase;
import com.jewellery.entity.POSPurchaseItem;
import com.jewellery.entity.PostagItem;
import com.jewellery.util.JournalCode;
import com.jewellery.validator.POSPurchaseValidator;

@Controller
public class FormPosPurchaseController extends JournalCode{

	@Autowired
	POSPuchaseDao posPurchaseDao;

	@Autowired
	POSCategoryDao poscategorydao;

	@Autowired
	POSItemDao positemdao;

	@Autowired
	LedgerDao ledgerDao;

	@Autowired
	PostagItemDao postagDao;

	@Autowired
	JournalDao journalDao;

	@Autowired
	private POSCategoryDao poscategoryDao;

	/*@Autowired
	POSStockDao posStockDao;*/
	
	@Autowired
	AccountsDao accountsDao;
	
	@Autowired
	private POSPurchaseValidator POSPurValidator;

	private Journal jrnl;

	JournalCode jobj = new JournalCode();
	BigDecimal ZERO = new BigDecimal(0);
	BigDecimal CONVERT = new BigDecimal("-1");
	
	List<String> ledgerGroupCode;

	// POSPurchase purhcaseObj = new POSPurchase();

	/*
	 * @Autowired private FormPOSPuchaseValidator POSPuchasevalidatior;
	 */
	@InitBinder
	protected void initBinder(HttpServletRequest request,ServletRequestDataBinder binder) throws Exception {
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class, null, dateEditor);
	}

	/*** To show Purchase Form ***/
	@RequestMapping(value = "/formPOSpurchase.htm", method = RequestMethod.GET)
	public ModelAndView getform(@ModelAttribute("pospCommand") POSP posp,
			ModelMap model) {
		model.addAttribute("SupplierNameList", ledgerDao.listSupplierNameOnly());
		model.addAttribute("categoryList", poscategoryDao.getCategoryNameList());
		model.put("pospCommand", new POSP());
		return new ModelAndView("formPOSPurchase");
	}

	/*** POS Purchase Listing ***/
	@RequestMapping(value = "/POSPurchase.htm", method = RequestMethod.GET)
	public ModelAndView showList(ModelMap model) {
		List<POSPurchase> pospurchaseList = posPurchaseDao.listPOSPurchase();
		model.addAttribute("pospurchaseList", pospurchaseList);
		return new ModelAndView("POSPurchase", model);
	}

	/** For Cancel **/
	@RequestMapping(value = "formPOSpurchase.htm", method = RequestMethod.POST, params = "cancel")
	public String cancelForm() {
		return "redirect:POSPurchase.htm";
	}

	/** For Insert **/
	@RequestMapping(value = "/formPOSpurchase.htm", method = RequestMethod.POST, params = "insert")
	public ModelAndView saveForm(@ModelAttribute("pospCommand") POSP posp,BindingResult result, ModelMap model, SessionStatus status) {

		// $$$$$$$ POS Purchase Entity Validation $$$$$$ //
		POSPurValidator.validateInsert(posp.getPosp(), result);
		POSPurValidator.validate(posp.getPosp(),posp.getListpospurchase(), result);
		
		List<POSPurchaseItem> listpos = posp.getListpospurchase();
		
		/** For Deleting Blank row **/
		Iterator<POSPurchaseItem> iter= listpos.iterator();	
		while(iter.hasNext()) {	
			POSPurchaseItem popItem = iter.next();
			if ((popItem.getCategoryName() == null || popItem.getCategoryName().length() == 0) && (popItem.getItemName() == null || popItem.getItemName().length() == 0)) {
				iter.remove();
				listpos.remove(popItem);
				posp.setListpospurchase(listpos);
				
			}
		}

		model.addAttribute("SupplierNameList", ledgerDao.listSupplierNameOnly());
		model.addAttribute("categoryList", poscategoryDao.getCategoryNameList());
		if (result.hasErrors()) {
			posp.setPosp(posp.getPosp());
			posp.setListpospurchase(posp.getListpospurchase());
			model.addAttribute("popPurchaseList", listpos);
			model.addAttribute("errorName", "insertError");
			List<String> posItemList = positemdao.listPOSItemName();
			ArrayList<String> itemList = new ArrayList<String>();
			int i = 1;
			if (!posItemList.isEmpty()) {
				itemList.add(0, "Select");
				for (String s : posItemList) {
					itemList.add(i, s);
					i++;
				}
			}
			model.addAttribute("itemNameList", itemList);
			model.addAttribute(posp);
			return new ModelAndView("formPOSPurchase", model);
		}
		/** For Storing Row status **/
		for(POSPurchaseItem rPOPItem : posp.getListpospurchase()){
			rPOPItem.setRowStatus("Persisted");
		}
		
		posPurchaseDao.insertPOSPurchase(posp.getPosp());

		// $$$$$$$ Ledger Updation $$$$$$//
		
		
		/*************** Insert Operation - Purchase Account Ledger : Regular - Cash & Credit Mode ***********/

		List<Ledger> purchaseLedger = ledgerDao.searchLedger("POS Purchase Account");
		BigDecimal OldpurchaseBl = purchaseLedger.get(0).getClosingTotalBalance();
		String purchaseBlType = purchaseLedger.get(0).getClosingTotalType();

		if (purchaseBlType.equals("Debit")) {
			BigDecimal RtotalBalanceDB = OldpurchaseBl.add(posp.getPosp().getGrandAmount());
			purchaseLedger.get(0).setClosingTotalBalance(RtotalBalanceDB);
			ledgerDao.updateLedgerPartyBalance(RtotalBalanceDB, "Debit","POS Purchase Account");

		} else if (purchaseBlType.equals("Credit")) {
			BigDecimal oldpurchaseBl = ZERO.subtract(OldpurchaseBl);
			BigDecimal RtotalBalanceCR = oldpurchaseBl.add(posp.getPosp().getGrandAmount());
			if (RtotalBalanceCR.signum() >= 0) {
				ledgerDao.updateLedgerPartyBalance(RtotalBalanceCR, "Debit","POS Purchase Account");
			} else {
				RtotalBalanceCR = RtotalBalanceCR.multiply(CONVERT);
				ledgerDao.updateLedgerPartyBalance(RtotalBalanceCR, "Credit","POS Purchase Account");
			}
		}

		/* only Regular - Cash or Credit */
		if (!posp.getPosp().getSupplierName().equals("Walk-in")) {

			if (posp.getPosp().getPaymentType().equals("Credit")) {

				/***** Insert Operation - Party Supplier Ledger : Regular - Credit Mode ******/
				List<Ledger> SupplierLedger = ledgerDao.searchLedger(posp.getPosp().getSupplierName());
				BigDecimal OldSupplierBl = SupplierLedger.get(0).getClosingTotalBalance();
				String SupplierBlType = SupplierLedger.get(0).getClosingTotalType();

				if (SupplierBlType.equals("Debit")) {

					BigDecimal RtotalBalanceDB = OldSupplierBl.subtract(posp.getPosp().getGrandAmount());

					if (RtotalBalanceDB.signum() >= 0) {
						ledgerDao.updateLedgerPartyBalance(RtotalBalanceDB,"Debit", posp.getPosp().getSupplierName());
					} else {
						RtotalBalanceDB = RtotalBalanceDB.multiply(CONVERT);
						ledgerDao.updateLedgerPartyBalance(RtotalBalanceDB,"Credit", posp.getPosp().getSupplierName());
					}

				} else if (SupplierBlType.equals("Credit")) {
					BigDecimal RtotalBalanceCR = OldSupplierBl.add(posp.getPosp().getGrandAmount());
					ledgerDao.updateLedgerPartyBalance(RtotalBalanceCR,"Credit", posp.getPosp().getSupplierName());
				}
			}

			// ***** Insert Operation - Auto Journal entry for : Regular - Cash
			// Mode or Credit Mode
			jrnl = new Journal();
			jrnl.setJournalNO(getJournalNumber(jrnl));
			jrnl.setJournalType("POS Purchase");
			jrnl.setJournalDate(posp.getPosp().getPdate());
			
			// Set Account group code as Debit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode("POS Purchase Account");			
			jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
			jrnl.setDebitAccountName("POS Purchase Account");
			
			// Set Account group code as Credit code
			ledgerGroupCode = ledgerDao.getLedgerAccountCode(posp.getPosp().getSupplierName());
			jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
			jrnl.setCreditAccountName(posp.getPosp().getSupplierName());
			
			jrnl.setTransactionId("POP" + posp.getPosp().getPurchaseId());
			jrnl.setDebitAmount(posp.getPosp().getGrandAmount());
			jrnl.setCreditAmount(posp.getPosp().getGrandAmount());
			jrnl.setNarration("Bill No " + posp.getPosp().getPurchaseId());
			journalDao.insertJournal((Journal) jrnl);
		}
		/* Regular or Walkin Cash */
		if (posp.getPosp().getPaymentType().equals("Cash")) {

			/*********************************** Insert Operation - Cash Account Ledger : Regular - Cash Mode **************/

			List<Ledger> CashLedger = ledgerDao.searchLedger("Cash Account");
			BigDecimal OldCashBl = CashLedger.get(0).getClosingTotalBalance();
			String CashBlType = CashLedger.get(0).getClosingTotalType();

			if (CashBlType.equals("Debit")) {

				BigDecimal RtotalBalanceDB = OldCashBl.subtract(posp.getPosp().getGrandAmount());

				if (RtotalBalanceDB.signum() >= 0) {
					ledgerDao.updateLedgerPartyBalance(RtotalBalanceDB,"Debit", "Cash Account");
				} else {
					RtotalBalanceDB = RtotalBalanceDB.multiply(CONVERT);
					ledgerDao.updateLedgerPartyBalance(RtotalBalanceDB,"Credit", "Cash Account");
				}

			} else if (CashBlType.equals("Credit")) {

				BigDecimal RtotalBalanceCR = OldCashBl.add(posp.getPosp().getGrandAmount());
				ledgerDao.updateLedgerPartyBalance(RtotalBalanceCR, "Credit","Cash Account");

			}

			if (posp.getPosp().getSupplierName().equals("Walk-in")) {

				// ****** Insert Operation - Auto Journal entry for POS Purchase
				// Walk-in : Cash Mode
				jrnl = new Journal();
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("POS Purchase");
				jrnl.setJournalDate(posp.getPosp().getPdate());
				
				// Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("POS Purchase Account");			
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
				jrnl.setDebitAccountName("POS Purchase Account");
				
				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName("Cash Account");
				
				jrnl.setTransactionId("POP" + posp.getPosp().getPurchaseId());
				jrnl.setDebitAmount(posp.getPosp().getGrandAmount());
				jrnl.setCreditAmount(posp.getPosp().getGrandAmount());
				jrnl.setNarration("Bill No "+ posp.getPosp().getPurchaseId());
				journalDao.insertJournal((Journal) jrnl);

			} else {

				// ***** Insert Operation - Auto Journal entry for Payment :
				// Regular - Cash Mode
				jrnl = new Journal();
				jrnl.setJournalNO(getJournalNumber(jrnl));				
				jrnl.setJournalType("POS Purchase Payment");
				jrnl.setJournalDate(posp.getPosp().getPdate());
				
				// Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(posp.getPosp().getSupplierName());			
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
				jrnl.setDebitAccountName(posp.getPosp().getSupplierName());
				
				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName("Cash Account");
				
				jrnl.setTransactionId("POP" + posp.getPosp().getPurchaseId());
				jrnl.setDebitAmount(posp.getPosp().getGrandAmount());
				jrnl.setCreditAmount(posp.getPosp().getGrandAmount());
				jrnl.setNarration("Bill No " + posp.getPosp().getPurchaseId());
				journalDao.insertJournal((Journal) jrnl);
			}

		}

		// $$$$$ Stock And Tag Updation $$$$$$$$$//

		PostagItem postag = new PostagItem();
	/*	POSStock stockObj = new POSStock();*/

		if (!listpos.isEmpty()) {

			for (POSPurchaseItem objpos : listpos) {

				if (!objpos.equals(null)) {

					objpos.setPospurchase(posp.getPosp());
					String purchaseCode = getNewPurchaseCode();
					objpos.setTransactionNo(purchaseCode);

					posPurchaseDao.insertPOSPurchaseItem(objpos);

					Integer quantity = 1;
					Integer tpcs = quantity * objpos.getPiecesPerSet();
					Integer qty = objpos.getQtySet();

					for (int i = 0; i < qty; i++) {

						String item_Code = getNewItemCode();
						postag.setBarcodeId(item_Code);
						postag.setPrintName(objpos.getItemName());
						postag.setCategoryName(objpos.getCategoryName());
						postag.setPps(objpos.getPiecesPerSet());
						POSItem posItem = positemdao
								.searchPOSItemBasedCategory(
										objpos.getItemName(),
										objpos.getCategoryName()).get(0);
						postag.setCompanyName(posItem.getCompanyName());
						postag.setItemName(objpos.getItemName());
						postag.setDate(posp.getPosp().getPdate());
						postag.setTotalpieces(tpcs);
						postag.setDiscountPercentage(posItem
								.getDiscountPercentage());
						postag.setVatPercentage(posItem.getVatPercentage());
						postag.setSalesRate(objpos.getSalesRate());
						postag.setCostRate(objpos.getCostRate());
						postag.setMrpinRs(objpos.getSalesRate());
						postag.setQtyset(quantity);
						postag.setMargin(objpos.getMarginPercentage());
						postag.setTransactionId(purchaseCode);
						postag.setPOSReferenceID(objpos.getPurchaseItemID().toString());
						postag.setTransactionType("POS Purchase");
						postag.setStatus("Unprinted");

						postagDao.save(postag);

					/*	stockObj.setBarcodeId(item_Code);
						stockObj.setItemID(objpos.getPurchaseItemID());
						stockObj.setCategoryName(objpos.getCategoryName());
						stockObj.setItemName(objpos.getItemName());
						stockObj.setQtySet(quantity);
						stockObj.setPps(objpos.getPiecesPerSet());
						stockObj.setTotalPieces(tpcs);
						stockObj.setStockDate(posp.getPosp().getPdate());
						stockObj.setSalesRate(objpos.getSalesRate());
						stockObj.setCostRate(objpos.getCostRate());
						stockObj.setMarginP(objpos.getMarginPercentage());
						stockObj.setMrp(objpos.getSalesRate());
						stockObj.setTransactionId(purchaseCode);
						stockObj.setTransactionType("POS Purchase");
						posStockDao.insertPOSStock(stockObj);	*/				
					}
					
					//Update POS category closing Stock
					poscategoryDao.updateCategorySet(objpos.getQtySet(), objpos.getCategoryName());
				}
			}
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("pospCommand", new POSP());
		return new ModelAndView("redirect:POSPurchase.htm");
	}

	/** Barcode Id generation **/
	private String getNewItemCode() {

		BigInteger found = postagDao.getposItemCode();
		String itemCodeNo = "PS1000";
		String itemcodelist = "PS"+found;
		
		if (found != null) {
			itemCodeNo = itemcodelist;
		}

		String splitNo = itemCodeNo.substring(2);
		int num = Integer.parseInt(splitNo);
		num++;
		String number = Integer.toString(num);
		String displayCode = itemCodeNo.substring(0, 2) + number;
		return displayCode;
	}

	/** Purcahse Transaction code generation **/
	private String getNewPurchaseCode() {

		BigInteger found = posPurchaseDao.getLatestPurchaseCode();
		String itemCodeNo = "PR1000";
		String purchasecodelist = "PR"+found;
		
		if (found != null) {
			itemCodeNo = purchasecodelist;
		}

		String splitNo = itemCodeNo.substring(2);
		int num = Integer.parseInt(splitNo);
		num++;
		String number = Integer.toString(num);
		String displayCode = itemCodeNo.substring(0, 2) + number;
		return displayCode;
	}

	/** For View Mode POS Purchase ***/
	@RequestMapping(value = "/viewPOSPurchase.htm", method = RequestMethod.GET)
	public ModelAndView viewForm(@ModelAttribute("pospCommand") POSP posp,@RequestParam(value = "purchaseId", required = false) Integer purchaseId,	ModelMap model) {
		
		// To disable rows already SolD based on tag status
		boolean SOLD = false;
		boolean RETURN = false;
		List<POSPurchaseItem> pospurItem = posPurchaseDao.getPOSPurchaseItem(purchaseId);
		for(POSPurchaseItem popListITem : pospurItem){
			try {
					List<PostagItem> popItemTagList = postagDao.POSTagByReference(popListITem.getPurchaseItemID().toString());
					SOLD = false; RETURN = false;
					for(PostagItem poptag: popItemTagList)
					{
						if(poptag.getStatus().equalsIgnoreCase("SOLD")){
							SOLD = true;
						}
						if(poptag.getStatus().equalsIgnoreCase("Purchase Return")){
							RETURN = true;
						}
					}
					
					if(SOLD == true){
						popListITem.setItemStatus("SOLD");
					}else if(RETURN == true){
						popListITem.setItemStatus("Purchase Return");
					}else{
						popListITem.setItemStatus("Purchased");
					}
					
					posPurchaseDao.updatePOSPurchaseItem(popListITem);
				}catch(Exception e){ }
			}
		POSPurchase Pospurchase = posPurchaseDao.getPOSPurchase(purchaseId);
		List<POSPurchaseItem> pospurchaseItem = posPurchaseDao.getPOSPurchaseItem(purchaseId);

		if (Pospurchase != null && !purchaseId.equals("")) {
			
			posp.setPosp(Pospurchase);
			posp.setListpospurchase(pospurchaseItem);
			List<POSPurchaseItem> pospJspList = posPurchaseDao.getPOSPurchaseItem(purchaseId);
			POSPurchaseItem pospItemTemp = pospJspList.get(pospJspList.size() - 1); // to remove static row from dynamic row
			pospJspList.remove(pospItemTemp);
						
			if (Pospurchase.getPurchaseType().equals("POS Purchase")) {
				model.addAttribute("purchaseItemsList", pospJspList);
				model.addAttribute("SupplierNameList", ledgerDao.listSupplierNameOnly());
				model.addAttribute("categoryList", poscategoryDao.getCategoryNameList());
				List<String> posItemList = positemdao.listPOSItemName();
				ArrayList<String> itemList = new ArrayList<String>();
				int i = 1;
				if (!posItemList.isEmpty()) {
					itemList.add(0, "Select");
					for (String s : posItemList) {
						itemList.add(i, s);
						i++;
					}
				}
				model.addAttribute("itemNameList", itemList);
				model.addAttribute(posp);
				return new ModelAndView("formPOSPurchase", model);
			} else if (Pospurchase.getPurchaseType().equals("POS Purchase Return")) {
				List<?> ledgerlist = ledgerDao.listallSmithName();    
				model.addAttribute("suppliername", ledgerlist);
				model.addAttribute("purchaseItemsList", pospJspList);
				return new ModelAndView("formPOSPurchaseReturn", model);
			}
		}
		return new ModelAndView("redirect:POSPurchase.htm");
	}


	/** For Update Mode **/
	@RequestMapping(value = "/formPOSpurchase.htm", method = RequestMethod.POST, params = "update")
	public ModelAndView updateForm(@ModelAttribute("pospCommand") POSP posp,BindingResult result, ModelMap model, SessionStatus stauts) 
	{

		Integer purchaseId = posp.getPosp().getPurchaseId();

		List<POSPurchaseItem> listposOld = posPurchaseDao.getPOSPurchaseItem(purchaseId);
		List<POSPurchaseItem> listposNew = posp.getListpospurchase();
		
		POSPurchase posPurchaseOld = posPurchaseDao.getPOSPurchase(purchaseId);
		POSPurchase posPurchaseNew = posp.getPosp();
			
		POSPurValidator.validateUpdate(posPurchaseOld, posPurchaseNew, listposOld, listposNew, result);
		POSPurValidator.validate(posp.getPosp(), posp.getListpospurchase(), result);
		
		/** Deleting Blank row */ 	
		Iterator<POSPurchaseItem> iter= listposNew.iterator();	
		while(iter.hasNext()) {	
			POSPurchaseItem popItem = iter.next();
			if(popItem.getRowStatus().equalsIgnoreCase("") && (popItem.getCategoryName() == null || popItem.getCategoryName().length() == 0 ) && (popItem.getItemName() == null || popItem.getItemName().length() == 0) ){
				iter.remove();
				listposNew.remove(popItem);
				posp.setListpospurchase(listposNew);				
			}
		}
		model.addAttribute("SupplierNameList", ledgerDao.listSupplierNameOnly());
		model.addAttribute("categoryList", poscategoryDao.getCategoryNameList());

		if (result.hasErrors()) {
			posp.setPosp(posPurchaseNew);
			posp.setListpospurchase(listposNew);
			List<POSPurchaseItem> listpos = posp.getListpospurchase();
			model.addAttribute("errorName", "updateError");
			model.addAttribute("popPurchaseList", listpos);
			List<String> posItemList = positemdao.listPOSItemName();
			ArrayList<String> itemList = new ArrayList<String>();
			int i = 1;
			if (!posItemList.isEmpty()) {
				itemList.add(0, "Select");
				for (String s : posItemList) {
					itemList.add(i, s);
					i++;
				}
			}
			model.addAttribute("itemNameList", itemList);
			model.addAttribute(posp);
			return new ModelAndView("formPOSPurchase", model);
		}	
		
		// $$$$$$$$$$$$$$ STOCK UPDATION $$$$$$$$$$$$$$$$$$$$$$$$$$$//

		PostagItem postag = new PostagItem();
		/*POSStock stockObj = new POSStock();*/

		for (POSPurchaseItem newposobj : listposNew) {
			
				//** For new Row added while Update **/
			if (newposobj.getPurchaseItemID() == null || newposobj.getPurchaseItemID().equals("")) {
				
				/** For Storing Row status **/
				for(POSPurchaseItem rPOPItem : posp.getListpospurchase()){
					rPOPItem.setRowStatus("Persisted");
				}
				newposobj.setPospurchase(posp.getPosp());
				posPurchaseDao.insertPOSPurchaseItem(newposobj);
				
				/************* POSPurchase Entity Update ************/
				String purchaseCode = getNewPurchaseCode();
				newposobj.setTransactionNo(purchaseCode);

				Integer quantity = 1;
				//Integer tpcs = quantity * newposobj.getPiecesPerSet();
				Integer qty = newposobj.getQtySet();

				for (int i = 0; i < qty; i++) {
					
					/******************* POSTagItem Entity Update *****/
					
					String item_Code = getNewItemCode();
					postag.setBarcodeId(item_Code);
					postag.setPrintName(newposobj.getItemName());
					postag.setCategoryName(newposobj.getCategoryName());
					postag.setPps(newposobj.getPiecesPerSet());
					postag.setTotalpieces(newposobj.getPiecesPerSet());
					postag.setItemName(newposobj.getItemName());
					POSItem posItem = positemdao.searchPOSItemBasedCategory(
							newposobj.getItemName(),
							newposobj.getCategoryName()).get(0);
					postag.setCompanyName(posItem.getCompanyName());
					postag.setDate(posp.getPosp().getPdate());
					postag.setDiscountPercentage(posItem
							.getDiscountPercentage());
					postag.setVatPercentage(posItem.getVatPercentage());
					postag.setSalesRate(newposobj.getSalesRate());
					postag.setCostRate(newposobj.getCostRate());
					postag.setMrpinRs(newposobj.getSalesRate());
					postag.setQtyset(quantity);
					postag.setMargin(newposobj.getMarginPercentage());
					postag.setTransactionId(purchaseCode);
					postag.setPOSReferenceID(newposobj.getPurchaseItemID().toString());
					postag.setTransactionType("POS Purchase");
					postag.setStatus("Unprinted");
					postagDao.save(postag);
					
					/************** POSStock Entity Update *********/		
					
					/*stockObj.setBarcodeId(item_Code);
					stockObj.setItemID(newposobj.getPurchaseItemID());
					stockObj.setCategoryName(newposobj.getCategoryName());
					stockObj.setItemName(newposobj.getItemName());
					stockObj.setQtySet(quantity);
					stockObj.setPps(newposobj.getPiecesPerSet());
					stockObj.setTotalPieces(tpcs);
					stockObj.setStockDate(posp.getPosp().getPdate());
					stockObj.setSalesRate(newposobj.getSalesRate());
					stockObj.setCostRate(newposobj.getCostRate());
					stockObj.setMarginP(newposobj.getMarginPercentage());
					stockObj.setMrp(newposobj.getSalesRate());
					stockObj.setTransactionId(purchaseCode);
					stockObj.setTransactionType("POS Purchase");
					posStockDao.insertPOSStock(stockObj); */ 
				}
				
					poscategorydao.updateCategorySet(newposobj.getQtySet(), newposobj.getCategoryName());

			} else {
				for (POSPurchaseItem oldposobj : listposOld) {
					/**          For Existing row Update              **/
					if (newposobj.getPurchaseItemID().equals(oldposobj.getPurchaseItemID())) {

						newposobj.setPospurchase(posp.getPosp());
					//	List<POSStock> listPOSStockitem = posStockDao.POSTagByReference(oldposobj.getPurchaseItemID());
						List<PostagItem> listPOSTagitem = postagDao
								.POSTagByReference(oldposobj
										.getPurchaseItemID().toString());

						int diffqty = newposobj.getQtySet()
								- oldposobj.getQtySet();
						// int stackqty = diffqty;
						Integer quantity = 1;
						Integer tpcs = quantity * newposobj.getPiecesPerSet();						
							if (diffqty > 0) {  /** Tag Code - Increased Qty (Set) on Update **/
								
								poscategorydao.updateCategorySet(diffqty, oldposobj.getCategoryName());//Update Category Set
								
								while (diffqty > 0) {
	
									String item_Code = getNewItemCode();
									postag.setBarcodeId(item_Code);
									postag.setPrintName(newposobj.getItemName());
									postag.setCategoryName(newposobj.getCategoryName());
									postag.setItemName(newposobj.getItemName());
									POSItem posItem = positemdao.searchPOSItemBasedCategory(newposobj.getItemName(),newposobj.getCategoryName()).get(0);
									postag.setCompanyName(posItem.getCompanyName());
									postag.setDate(posp.getPosp().getPdate());
									postag.setTotalpieces(tpcs);
									postag.setDiscountPercentage(posItem
											.getDiscountPercentage());
									postag.setVatPercentage(posItem
											.getVatPercentage());
									postag.setSalesRate(newposobj.getSalesRate());
									postag.setCostRate(newposobj.getCostRate());
									postag.setMrpinRs(newposobj.getSalesRate());
									postag.setQtyset(quantity);
									postag.setMargin(newposobj
											.getMarginPercentage());
									postag.setTransactionId(getNewPurchaseCode());
									postag.setPOSReferenceID(newposobj
											.getPurchaseItemID().toString());
									postag.setTransactionType("POS Purchase");
									postag.setStatus("Unprinted");
									postagDao.save(postag);
									diffqty--;
							}

						} else if (diffqty < 0) {
							/** Tag Code - Decreased Qty (Set) on Update **/
							poscategorydao.updateCategorySet(diffqty, oldposobj.getCategoryName());// Update Category Set								
							for (PostagItem postagitem : listPOSTagitem) {

								if (diffqty < 0 && postagitem.getQtyset() != 0) {
									postagitem.setQtyset(0);
									postagitem.setDeleted("YES");
									postagDao.update(postagitem);
									diffqty++;
								}
							}
						} else if (diffqty == 0) {
							/** Tag Code - General Update **/
							for (PostagItem postagitems : listPOSTagitem) {
								if (!postagitems.getStatus().equalsIgnoreCase("SOLD") && !postagitems.getStatus().equalsIgnoreCase("Purchase Return") && !postagitems.deleted.equalsIgnoreCase("YES")) { // other than SOLD barcodes
									postagitems.setTotalpieces(tpcs);
									postagitems.setSalesRate(newposobj
											.getSalesRate());
									postagitems.setCostRate(newposobj
											.getCostRate());
									postagitems.setMrpinRs(newposobj
											.getSalesRate());
									postagitems.setMargin(newposobj
											.getMarginPercentage());
									postagDao.update(postagitems);
								}
							}

						}
							/*if (stackqty > 0) {  *//** Stock Code - Increased Qty (Set) on Update **//*
								
							
							while (stackqty > 0) {
		
									stockObj.setBarcodeId(getNewItemCode());
									stockObj.setItemID(newposobj.getPurchaseItemID());
									stockObj.setCategoryName(newposobj.getCategoryName());
									stockObj.setItemName(newposobj.getItemName());
									stockObj.setQtySet(quantity);
									stockObj.setPps(newposobj.getPiecesPerSet());
									stockObj.setTotalPieces(tpcs);
									stockObj.setStockDate(posp.getPosp().getPdate());
									stockObj.setSalesRate(newposobj.getSalesRate());
									stockObj.setCostRate(newposobj.getCostRate());
									stockObj.setMarginP(newposobj.getMarginPercentage());
									stockObj.setMrp(newposobj.getSalesRate());
									stockObj.setTransactionId(getNewPurchaseCode());
									stockObj.setTransactionType("POS Purchase");
									posStockDao.insertPOSStock(stockObj);
									stackqty--;

								}
						


						} else if(stackqty < 0){ *//** Stock Code - Decreased Qty (Set) on Update **//*
							
								for (POSStock POSStockitem : listPOSStockitem) {
									
									if (stackqty < 0 && POSStockitem.getQtySet() != 0) {	
										POSStockitem.setQtySet(0);
										POSStockitem.setDeleted("YES");
										posStockDao.updatePOSStock(POSStockitem);
										stackqty++;
								}
							}
						}else if(stackqty == 0){ *//** Stock Code - General Update **//*
							for (POSStock POSStockitems : listPOSStockitem) {
									if(!POSStockitems.getTransactionType().equalsIgnoreCase("SOLD") && POSStockitems.getDeleted().equalsIgnoreCase("YES")){								
										POSStockitems.setPps(newposobj.getPiecesPerSet());
										POSStockitems.setTotalPieces(tpcs);
										POSStockitems.setSalesRate(newposobj.getSalesRate());
										POSStockitems.setCostRate(newposobj.getCostRate());
										POSStockitems.setMarginP(newposobj.getMarginPercentage());
										POSStockitems.setMrp(newposobj.getSalesRate());
										posStockDao.updatePOSStock(POSStockitems);
									}
							}
						}*/
							/** Updating POS Purchase Item Entity ***/
						posPurchaseDao.updatePOSPurchaseItem(newposobj);						
					}
				}
			}
		}

		BigDecimal OldGrandAmt = posPurchaseOld.getGrandAmount();
		BigDecimal NewGrandAmt = posp.getPosp().getGrandAmount();
		String OldSupplierName = posPurchaseOld.getSupplierName();
		String NewSupplierName = posp.getPosp().getSupplierName();

		// ########################### Ledger Updation Same Supplier #################################*/
		/**
		 * POS Purchase Ledger Updation - Same Supplier - Cash Mode & Credit
		 * ***************** Closing balance = Current Purchase ledger - (Old
		 * Grand Amount) + (New Grand Amount)
		 */

		/*
		 * $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Same Supplier POS PURCHASE
		 * LEDGER for CASH OR CREDIT $$$$$$$$
		 */

		List<Ledger> OldpurchaseLedger = ledgerDao.searchLedger("POS Purchase Account");
		BigDecimal OldpurchaseBl = OldpurchaseLedger.get(0)
				.getClosingTotalBalance();

		if (OldpurchaseLedger.get(0).getClosingTotalType().equals("Credit")) {
			OldpurchaseBl = ZERO.subtract(OldpurchaseBl);
		}
		BigDecimal tempPurchaseTotal = OldpurchaseBl.subtract(OldGrandAmt);
		BigDecimal finalPurchaseTotal = tempPurchaseTotal.add(NewGrandAmt);

		if (finalPurchaseTotal.signum() >= 0) {
			ledgerDao.updateLedgerPartyBalance(finalPurchaseTotal,"Debit","POS Purchase Account");
		} else {
			finalPurchaseTotal = finalPurchaseTotal.multiply(CONVERT);
			ledgerDao.updateLedgerPartyBalance(finalPurchaseTotal,"Credit","POS Purchase Account");
		}

		if (OldSupplierName.equals(NewSupplierName)) {

			/**$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Same Supplier CASH to CASH $$$$$$$$$$$$$$$$$$$$$$$$$$**/

			if (posp.getPosp().getPaymentType().equals("Cash") && posPurchaseOld.getPaymentType().equals("Cash")) {

				/**
				 * Cash Updation - Same Supplier - Cash Mode
				 * **************************** Closing balance = Current Cash
				 * ledger + (Old Grand Amount) - (New Grand Amount)
				 */

				List<Ledger> OldCashLedger = ledgerDao.searchLedger("Cash Account");
				BigDecimal OldCashBl = OldCashLedger.get(0)
						.getClosingTotalBalance();

				if (OldCashLedger.get(0).getClosingTotalType().equals("Credit")) {
					OldCashBl = ZERO.subtract(OldCashBl);
				}

				BigDecimal tempCashTotal = OldCashBl.add(OldGrandAmt);
				BigDecimal finalCashTotal = tempCashTotal.subtract(NewGrandAmt);

				if (finalCashTotal.signum() >= 0) {
					ledgerDao.updateLedgerPartyBalance(finalCashTotal, "Debit","Cash Account");
				} else {
					finalCashTotal = finalCashTotal.multiply(CONVERT);
					ledgerDao.updateLedgerPartyBalance(finalCashTotal,"Credit", "Cash Account");
				}

				/*** 888888888888888888888 Journal Updation - Same Supplier Cash Mode 88888888888888888888 */

				List<Journal> jrnlPOSPurchase = journalDao.getJournalUpdate("POS Purchase", "POP" + posPurchaseOld.getPurchaseId());
				if (!jrnlPOSPurchase.isEmpty()) {
					jrnlPOSPurchase.get(0).setDebitAmount(NewGrandAmt);
					jrnlPOSPurchase.get(0).setCreditAmount(NewGrandAmt);
					jrnlPOSPurchase.get(0).setJournalDate(posp.getPosp().getPdate());
					journalDao.updateJournal(jrnlPOSPurchase.get(0));

				}

				List<Journal> jrnlPOSPayment = journalDao.getJournalUpdate("POS Purchase Payment", "POP" + posPurchaseOld.getPurchaseId());
				if (!jrnlPOSPayment.isEmpty()) {
					jrnlPOSPayment.get(0).setDebitAmount(NewGrandAmt);
					jrnlPOSPayment.get(0).setCreditAmount(NewGrandAmt);
					jrnlPOSPayment.get(0).setJournalDate(posp.getPosp().getPdate());
					journalDao.updateJournal(jrnlPOSPayment.get(0));

				}

			} /** $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Same Supplier CREDIT to CREDIT $$$$$$$$$$$$$$$$$$$$$$$$$$ */

			else if (posp.getPosp().getPaymentType().equals("Credit") && posPurchaseOld.getPaymentType().equals("Credit")) 
			{

				/*** Party Ledger Updation - Same Supplier - Credit Mode 
				 * Closing balance = Current party balance + (old Grand amount) - (new Grand amount) ****/

				List<Ledger> OldSupplierLedger = ledgerDao.searchLedger(posp.getPosp().getSupplierName());
				BigDecimal OldSupplierBal = OldSupplierLedger.get(0).getClosingTotalBalance();

				if (OldSupplierLedger.get(0).getClosingTotalType().equals("Credit")) {
					OldSupplierBal = ZERO.subtract(OldSupplierBal);
				}

				BigDecimal tempSupplierTotal = OldSupplierBal.add(OldGrandAmt);
				BigDecimal finalSupplierTotal = tempSupplierTotal.subtract(NewGrandAmt);

				if (finalSupplierTotal.signum() >= 0) {
					ledgerDao.updateLedgerPartyBalance(finalSupplierTotal,
							"Debit", posp.getPosp().getSupplierName());
				} else {
					finalSupplierTotal = finalSupplierTotal.multiply(CONVERT);
					ledgerDao.updateLedgerPartyBalance(finalSupplierTotal,"Credit", posp.getPosp().getSupplierName());
				}

				/*** 8888888888888888888888888888888 Journal Updation - Same Supplier Credit Mode 8888888888888888888888
				 */
				List<Journal> jrnlPOSPurchase = journalDao.getJournalUpdate("POS Purchase", "POP" + posPurchaseOld.getPurchaseId());
				if (!jrnlPOSPurchase.isEmpty()) {
					jrnlPOSPurchase.get(0).setDebitAmount(NewGrandAmt);
					jrnlPOSPurchase.get(0).setCreditAmount(NewGrandAmt);
					jrnlPOSPurchase.get(0).setJournalDate(posp.getPosp().getPdate());
					journalDao.updateJournal(jrnlPOSPurchase.get(0));

				}
			} /** $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Same Supplier CASH to CREDIT $$$$$$$$$$$$$$$$$$$$$$$$$$ */
			else if (posPurchaseOld.getPaymentType().equals("Cash")
					&& posp.getPosp().getPaymentType().equals("Credit")) {

				/**
				 * 8888888888888 Party Balance Updation : Closing balance=
				 * Current balance - New Grand amount 88888
				 **/

				List<Ledger> NewSupplierLedgerCSCR = ledgerDao
						.searchLedger(posp.getPosp().getSupplierName());
				BigDecimal NewSupplierBalCSCR = NewSupplierLedgerCSCR.get(0)
						.getClosingTotalBalance();

				if (NewSupplierLedgerCSCR.get(0).getClosingTotalType()
						.equals("Credit")) {
					NewSupplierBalCSCR = ZERO.subtract(NewSupplierBalCSCR);
				}

				BigDecimal finalNewSupplierBalCSCR = NewSupplierBalCSCR
						.subtract(NewGrandAmt);

				if (finalNewSupplierBalCSCR.signum() >= 0) {
					ledgerDao.updateLedgerPartyBalance(finalNewSupplierBalCSCR,
							"Debit", posp.getPosp().getSupplierName());
				} else {
					finalNewSupplierBalCSCR = finalNewSupplierBalCSCR
							.multiply(CONVERT);
					ledgerDao.updateLedgerPartyBalance(finalNewSupplierBalCSCR,
							"Credit", posp.getPosp().getSupplierName());
				}

				/*** 888888888888888 Cash Ledger Updation : Closing Balance = Current Balance + Old Grand Amount. 8888888888 **/

				List<Ledger> OldCashLedgerCSCR = ledgerDao
						.searchLedger("Cash Account");
				BigDecimal OldCashBlCSCR = OldCashLedgerCSCR.get(0)
						.getClosingTotalBalance();

				if (OldCashLedgerCSCR.get(0).getClosingTotalType()
						.equals("Credit")) {
					OldCashBlCSCR = ZERO.subtract(OldCashBlCSCR);
				}

				BigDecimal finalCashTotalCSCR = OldCashBlCSCR.add(OldGrandAmt);

				if (finalCashTotalCSCR.signum() >= 0) {
					ledgerDao.updateLedgerPartyBalance(finalCashTotalCSCR,
							"Debit", "Cash Account");
				} else {
					finalCashTotalCSCR = finalCashTotalCSCR.multiply(CONVERT);
					ledgerDao.updateLedgerPartyBalance(finalCashTotalCSCR,
							"Credit", "Cash Account");
				}

				/*** 8888888888888 Journal Updation : Same Supplier Cash to Credit POS Purchase 888888888888888888888 **/

				List<Journal> jrnlListPOSPurchaseCash = journalDao.getJournalUpdate("POS Purchase", "POP"+ posPurchaseOld.getPurchaseId());
				if (!jrnlListPOSPurchaseCash.isEmpty()) {
					jrnlListPOSPurchaseCash.get(0).setDebitAmount(NewGrandAmt);
					jrnlListPOSPurchaseCash.get(0).setCreditAmount(NewGrandAmt);
					jrnlListPOSPurchaseCash.get(0).setJournalDate(posp.getPosp().getPdate());
					journalDao.updateJournal(jrnlListPOSPurchaseCash.get(0));
				}

				/*** 88888888888888888888888888 Second Journal (Payment) Should be deleted 888888888888888888888888888 */
				List<Journal> jrnlListPOSPurchasePayment = journalDao.getJournalUpdate("POS Purchase Payment","POP" + posPurchaseOld.getPurchaseId());
				if (!jrnlListPOSPurchasePayment.isEmpty()) {
					journalDao.deleteJournal(jrnlListPOSPurchasePayment.get(0));
				}

			} /** $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Same Supplier CREDIT to CASH $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
			else if (posPurchaseOld.getPaymentType().equals("Credit") && posp.getPosp().getPaymentType().equals("Cash")) {

				/*** 8888888888888 Party Balance : Closing balance= Current balance + Old Grand amount 8888888888888888888 **/

				List<Ledger> OldSupplierLedgerCRCS = ledgerDao.searchLedger(posPurchaseOld.getSupplierName());
				BigDecimal OldSupplierBalCRCS = OldSupplierLedgerCRCS.get(0).getClosingTotalBalance();

				if (OldSupplierLedgerCRCS.get(0).getClosingTotalType().equals("Credit")) {
					OldSupplierBalCRCS = ZERO.subtract(OldSupplierBalCRCS);
				}

				BigDecimal OldSupplierTotalBalCRCS = OldSupplierBalCRCS.add(OldGrandAmt);

				if (OldSupplierTotalBalCRCS.signum() >= 0) {
					ledgerDao.updateLedgerPartyBalance(OldSupplierTotalBalCRCS,"Debit", posPurchaseOld.getSupplierName());
				} else {
					OldSupplierTotalBalCRCS = OldSupplierTotalBalCRCS.multiply(CONVERT);
					ledgerDao.updateLedgerPartyBalance(OldSupplierTotalBalCRCS,"Credit", posPurchaseOld.getSupplierName());
				}

				/*** 888888888888888888888 Cash Ledger Updation : Closing Balance = Current Balance - New Grand Amount.. 8888888 **/

				List<Ledger> OldCashLedgerCRCS = ledgerDao.searchLedger("Cash Account");
				BigDecimal OldCashBlCRCS = OldCashLedgerCRCS.get(0).getClosingTotalBalance();

				if (OldCashLedgerCRCS.get(0).getClosingTotalType().equals("Credit")) {
					OldCashBlCRCS = ZERO.subtract(OldCashBlCRCS);
				}

				BigDecimal finalCashTotalCRCS = OldCashBlCRCS.subtract(NewGrandAmt);

				if (finalCashTotalCRCS.signum() >= 0) {
					ledgerDao.updateLedgerPartyBalance(finalCashTotalCRCS,"Debit", "Cash Account");
				} else {
					finalCashTotalCRCS = finalCashTotalCRCS.multiply(CONVERT);
					ledgerDao.updateLedgerPartyBalance(finalCashTotalCRCS,"Credit", "Cash Account");
				}

				/*** 88888888888 Journal Updation : Same Supplier Credit to Cash POS Purchase 888888888888888888888 **/

				List<Journal> jrnlListPOSPurchaseCRCS = journalDao.getJournalUpdate("POS Purchase", "POP"+ posPurchaseOld.getPurchaseId());
				if (!jrnlListPOSPurchaseCRCS.isEmpty()) {
					jrnlListPOSPurchaseCRCS.get(0).setDebitAmount(NewGrandAmt);
					jrnlListPOSPurchaseCRCS.get(0).setCreditAmount(NewGrandAmt);
					jrnlListPOSPurchaseCRCS.get(0).setJournalDate(posp.getPosp().getPdate());
					journalDao.updateJournal(jrnlListPOSPurchaseCRCS.get(0));
				}

				/*** 8888888888 Insert Operation - Auto Journal entry for Payment : Regular - Cash Mode 888888888888888 */
				jrnl = new Journal();
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("POS Purchase Payment");
				jrnl.setJournalDate(posp.getPosp().getPdate());
				
				// Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(posp.getPosp().getSupplierName());			
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
				jrnl.setDebitAccountName(posp.getPosp().getSupplierName()); 								
				
				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName("Cash Account");
				
				jrnl.setTransactionId("POP" + posp.getPosp().getPurchaseId());
				jrnl.setDebitAmount(posp.getPosp().getGrandAmount());
				jrnl.setCreditAmount(posp.getPosp().getGrandAmount());
				jrnl.setNarration("Bill No " + posp.getPosp().getPurchaseId());
				journalDao.insertJournal((Journal) jrnl);
			}
		}



		// $$$$$$$$$$$$$$$$$$$$$$$$$ Different Supplier $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$/

		if (!OldSupplierName.equals(NewSupplierName)) {
			
			/** Party balance Update :::::::::: Old Supplier: Closing balance=Current party balance+ (old purchase amount) 
			 * New Supplier: Closing balance = Current party balance - (new purchase amount) */

			//**$$$$$$$$$$$$$$$$$$$$$ Different Supplier - Credit to Credit $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */

			if (posPurchaseOld.getPaymentType().equals("Credit") && posp.getPosp().getPaymentType().equals("Credit")) {
				
				List<Ledger> OldSupplierLedger = ledgerDao.searchLedger(posPurchaseOld.getSupplierName());
				BigDecimal OldSupplierBal = OldSupplierLedger.get(0).getClosingTotalBalance();
				
				/*** 8888888888888888888888888888888 Old Party Supplier 88888888888888888	 */

				if (OldSupplierLedger.get(0).getClosingTotalType().equals("Credit")) {
					OldSupplierBal = ZERO.subtract(OldSupplierBal);
				}
				BigDecimal OldSupplierTotalBal = OldSupplierBal.add(OldGrandAmt);

				if (OldSupplierTotalBal.signum() >= 0) {
					ledgerDao.updateLedgerPartyBalance(OldSupplierTotalBal,"Debit", posPurchaseOld.getSupplierName());
				} else {
					OldSupplierTotalBal = OldSupplierTotalBal.multiply(CONVERT);
					ledgerDao.updateLedgerPartyBalance(OldSupplierTotalBal,"Credit", posPurchaseOld.getSupplierName());
				}

				/*** 88888888888888888888888888 New Party Supplier 88888888888888888 */
				
				List<Ledger> NewSupplierLedger = ledgerDao.searchLedger(posp.getPosp().getSupplierName());
				BigDecimal NewSupplierBal = NewSupplierLedger.get(0).getClosingTotalBalance();

				if (NewSupplierLedger.get(0).getClosingTotalType().equals("Credit")) {
					NewSupplierBal = ZERO.subtract(NewSupplierBal);
				}
				BigDecimal NewSupplierTotalBal = NewSupplierBal.subtract(NewGrandAmt);
				if (NewSupplierTotalBal.signum() >= 0) {
					ledgerDao.updateLedgerPartyBalance(NewSupplierTotalBal,"Debit", posp.getPosp().getSupplierName());
				} else {
					NewSupplierTotalBal = NewSupplierTotalBal.multiply(CONVERT);
					ledgerDao.updateLedgerPartyBalance(NewSupplierTotalBal,"Credit", posp.getPosp().getSupplierName());
				}
				
				
				/*** 88888888888888888888888888 Journal Different Supplier Credit to Credit Mode 8888888888888*/
				List<Journal> jrnlPOSPurchase = journalDao.getJournalUpdate("POS Purchase", "POP" + posPurchaseOld.getPurchaseId());
				if (!jrnlPOSPurchase.isEmpty()) {
					jrnlPOSPurchase.get(0).setDebitAmount(NewGrandAmt);
					jrnlPOSPurchase.get(0).setCreditAmount(NewGrandAmt);
					jrnlPOSPurchase.get(0).setCreditAccountName(NewSupplierName);
					jrnlPOSPurchase.get(0).setJournalDate(posp.getPosp().getPdate());
					journalDao.updateJournal(jrnlPOSPurchase.get(0));
				}

			}

			// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Different Supplier - Cash to Cash $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$/

			else if (posPurchaseOld.getPaymentType().equals("Cash")	&& posp.getPosp().getPaymentType().equals("Cash")) {
				
				/** 88888888888888888888888888 CASH Ledger Update payment Type - Cash to Cash 8888888888888888888888 */

				/*** Closing balance = Current Cash ledger + (Old Grand Amount) - (New Grand Amount)**/

				List<Ledger> OldCashLedgerCS = ledgerDao.searchLedger("Cash Account");
				BigDecimal OldCashBlCS = OldCashLedgerCS.get(0).getClosingTotalBalance();

				if (OldCashLedgerCS.get(0).getClosingTotalType().equals("Credit")) {
					OldCashBlCS = ZERO.subtract(OldCashBlCS);
				}

				BigDecimal tempCashTotalCS = OldCashBlCS.add(OldGrandAmt);
				BigDecimal finalCashTotalCS = tempCashTotalCS.subtract(NewGrandAmt);

				if (finalCashTotalCS.signum() >= 0) {
					ledgerDao.updateLedgerPartyBalance(finalCashTotalCS,"Debit", "Cash Account");
				} else {
					finalCashTotalCS = finalCashTotalCS.multiply(CONVERT);
					ledgerDao.updateLedgerPartyBalance(finalCashTotalCS,"Credit", "Cash Account");
				}

				/*** 888888888888888888888888888 Journal Updation - Different Supplier Cash to Cash Mode 8888888888888*/

				List<Journal> jrnlPOSPurchaseCS = journalDao.getJournalUpdate("POS Purchase", "POP" + posPurchaseOld.getPurchaseId());
				if (!jrnlPOSPurchaseCS.isEmpty()) {
					jrnlPOSPurchaseCS.get(0).setDebitAmount(NewGrandAmt);
					jrnlPOSPurchaseCS.get(0).setCreditAmount(NewGrandAmt);
					jrnlPOSPurchaseCS.get(0).setCreditAccountName(NewSupplierName);
					jrnlPOSPurchaseCS.get(0).setJournalDate(posp.getPosp().getPdate());
					journalDao.updateJournal(jrnlPOSPurchaseCS.get(0));
				}

				List<Journal> jrnlPOSPaymentCS = journalDao.getJournalUpdate("POS Purchase Payment", "POP" + posPurchaseOld.getPurchaseId());
				if (!jrnlPOSPaymentCS.isEmpty()) {
					jrnlPOSPaymentCS.get(0).setDebitAmount(NewGrandAmt);
					jrnlPOSPaymentCS.get(0).setCreditAmount(NewGrandAmt);
					jrnlPOSPaymentCS.get(0).setDebitAccountName(NewSupplierName);
					jrnlPOSPaymentCS.get(0).setJournalDate(posp.getPosp().getPdate());
					journalDao.updateJournal(jrnlPOSPaymentCS.get(0));
				}
			}// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Different Supplier - CASH to CREDIT $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$/
			else if (posPurchaseOld.getPaymentType().equals("Cash") && posp.getPosp().getPaymentType().equals("Credit")) {

				/** 88888888888888888 Party Balance : New Supplier:Closing balance= Current balance - New Grand amount 88888888888 **/

				List<Ledger> NewSupplierLedgerCSCR = ledgerDao.searchLedger(posp.getPosp().getSupplierName());
				BigDecimal NewSupplierBalCSCR = NewSupplierLedgerCSCR.get(0).getClosingTotalBalance();

				if (NewSupplierLedgerCSCR.get(0).getClosingTotalType().equals("Credit")) {
					NewSupplierBalCSCR = ZERO.subtract(NewSupplierBalCSCR);
				}

				BigDecimal NewSupplierTotalBalCSCR = NewSupplierBalCSCR.subtract(NewGrandAmt);
				
				if (NewSupplierTotalBalCSCR.signum() >= 0) {
					ledgerDao.updateLedgerPartyBalance(NewSupplierTotalBalCSCR,	"Debit", posp.getPosp().getSupplierName());
				} else {
					NewSupplierTotalBalCSCR = NewSupplierTotalBalCSCR.multiply(CONVERT);
					ledgerDao.updateLedgerPartyBalance(NewSupplierTotalBalCSCR,"Credit", posp.getPosp().getSupplierName());
				}

				/**
				 * 88888888888888888 CASH Updation : Closing Balance = Current Balance + Old Grand Amount 88888888888
				 **/

				List<Ledger> NewCashLedgerCSCR = ledgerDao.searchLedger("Cash Account");
				BigDecimal NewCashBalCSCR = NewCashLedgerCSCR.get(0).getClosingTotalBalance();

				if (NewCashLedgerCSCR.get(0).getClosingTotalType().equals("Credit")) {
					NewCashBalCSCR = ZERO.subtract(NewCashBalCSCR);
				}

				BigDecimal NewCashTotalBalCSCR = NewCashBalCSCR.add(OldGrandAmt);

				if (NewCashTotalBalCSCR.signum() >= 0) {
					ledgerDao.updateLedgerPartyBalance(NewCashTotalBalCSCR,"Debit", "Cash Account");
				} else {
					NewCashTotalBalCSCR = NewCashTotalBalCSCR.multiply(CONVERT);
					ledgerDao.updateLedgerPartyBalance(NewCashTotalBalCSCR,"Credit", "Cash Account");
				}

				/**
				 * 8888888888888888 Journal Update : POS Purchase 8888888888888888888888888888888888888888888
				 */

				List<Journal> jrnlPOSPurchaseCSCR = journalDao
						.getJournalUpdate("POS Purchase", "POP" + posPurchaseOld.getPurchaseId());
				if (!jrnlPOSPurchaseCSCR.isEmpty()) {
					jrnlPOSPurchaseCSCR.get(0).setDebitAmount(NewGrandAmt);
					jrnlPOSPurchaseCSCR.get(0).setCreditAmount(NewGrandAmt);
					jrnlPOSPurchaseCSCR.get(0).setCreditAccountName(NewSupplierName);
					jrnlPOSPurchaseCSCR.get(0).setJournalDate(posp.getPosp().getPdate());
					journalDao.updateJournal(jrnlPOSPurchaseCSCR.get(0));
				}

				/**
				 * 888888888888888888 Second Journal (Payment) Should be deleted 888888888888888888888888888
				 */
				List<Journal> jrnlListPOSPurchasePaymentCSCR = journalDao.getJournalUpdate("POS Purchase Payment","POP" + posPurchaseOld.getPurchaseId());
				if (!jrnlListPOSPurchasePaymentCSCR.isEmpty()) {
					journalDao.deleteJournal(jrnlListPOSPurchasePaymentCSCR.get(0));
				}

			}// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Different Supplier - CREDIT to CASH $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$/
			else if (posPurchaseOld.getPaymentType().equals("Credit") && posp.getPosp().getPaymentType().equals("Cash")) {
				/**
				 * 88888888888888888 Party Balance : Old Supplier : Closing balance= Current balance + Old Grand amount 888888
				 **/

				List<Ledger> OldSupplierLedgerCRCS = ledgerDao.searchLedger(posPurchaseOld.getSupplierName());
				BigDecimal OldSupplierBalCRCS = OldSupplierLedgerCRCS.get(0).getClosingTotalBalance();

				if (OldSupplierLedgerCRCS.get(0).getClosingTotalType().equals("Credit")) {
					OldSupplierBalCRCS = ZERO.subtract(OldSupplierBalCRCS);
				}

				BigDecimal OldSupplierTotalBalCRCS = OldSupplierBalCRCS.add(OldGrandAmt);

				if (OldSupplierTotalBalCRCS.signum() >= 0) {
					ledgerDao.updateLedgerPartyBalance(OldSupplierTotalBalCRCS,"Debit", posPurchaseOld.getSupplierName());
				} else {
					OldSupplierTotalBalCRCS = OldSupplierTotalBalCRCS.multiply(CONVERT);
					ledgerDao.updateLedgerPartyBalance(OldSupplierTotalBalCRCS,"Credit", posPurchaseOld.getSupplierName());
				}

				/**
				 * 88888888888888888 CASH Ledger Update : Current Balance - New Grand Amount. 88888888888
				 **/

				List<Ledger> NewCashLedgerCRCS = ledgerDao.searchLedger("Cash Account");
				BigDecimal NewCashBalCRCS = NewCashLedgerCRCS.get(0).getClosingTotalBalance();

				if (NewCashLedgerCRCS.get(0).getClosingTotalType().equals("Credit")) {
					NewCashBalCRCS = ZERO.subtract(NewCashBalCRCS);
				}

				BigDecimal NewCashTotalBalCRCS = NewCashBalCRCS.subtract(NewGrandAmt);

				if (NewCashTotalBalCRCS.signum() >= 0) {
					ledgerDao.updateLedgerPartyBalance(NewCashTotalBalCRCS,"Debit", "Cash Account");
				} else {
					NewCashTotalBalCRCS = NewCashTotalBalCRCS.multiply(CONVERT);
					ledgerDao.updateLedgerPartyBalance(NewCashTotalBalCRCS,"Credit", "Cash Account");
				}

				/**
				 * 88888888888888888 Journal Update : POS Purchase 8888888888888888888888888888888888888888888888
				 **/

				List<Journal> jrnlListPOSPurchaseCRCS = journalDao.getJournalUpdate("POS Purchase", "POP"+ posPurchaseOld.getPurchaseId());
				if (!jrnlListPOSPurchaseCRCS.isEmpty()) {
					jrnlListPOSPurchaseCRCS.get(0).setDebitAmount(NewGrandAmt);
					jrnlListPOSPurchaseCRCS.get(0).setCreditAmount(NewGrandAmt);
					jrnlListPOSPurchaseCRCS.get(0).setCreditAccountName(NewSupplierName);
					jrnlListPOSPurchaseCRCS.get(0).setJournalDate(posp.getPosp().getPdate());
					journalDao.updateJournal(jrnlListPOSPurchaseCRCS.get(0));
				}

				/**
				 * 88888888888888888 Second Journal entry for Payment 8888888888888888888888888888888888888888888
				 */
				jrnl = new Journal();
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("POS Purchase Payment");
				jrnl.setJournalDate(posp.getPosp().getPdate());
				
				//Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(NewSupplierName);			
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());				
				jrnl.setDebitAccountName(NewSupplierName);				
				
				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName("Cash Account");
				
				jrnl.setTransactionId("POP" + posp.getPosp().getPurchaseId());
				jrnl.setDebitAmount(posp.getPosp().getGrandAmount());
				jrnl.setCreditAmount(posp.getPosp().getGrandAmount());
				jrnl.setNarration("Bill No " + posp.getPosp().getPurchaseId());
				journalDao.insertJournal((Journal) jrnl);

			}

		}// end of else if for different supplier

		posPurchaseDao.updatePOSPurchase(posp.getPosp());
		return new ModelAndView("redirect:POSPurchase.htm");
	}

	/** For Item Name Ajax **/
	@RequestMapping(value = "/itemName_Detail.htm", method = RequestMethod.GET)
	public @ResponseBody
	String itemNamesList(
			@RequestParam(value = "cnameValue", required = true) String cnameValue) {
		String ID = "";
		try {
			List<String> inames = positemdao.getInamesList(cnameValue);
			if (!inames.isEmpty()) {
				ArrayList<String> itemNamesList = new ArrayList<String>();
				itemNamesList.add(0, "Select");
				itemNamesList.add(1, inames.toString());
				ID = itemNamesList.toString();
			}
		} catch (Exception e) {
		}
		return ID;
	}

	/** For CategoryName Auto complete **/
	@RequestMapping(value = "/categoryName_Auto.htm", method = RequestMethod.GET)
	public @ResponseBody String cNameAuto(@RequestParam(value = "cnamePart", required = true) String cnamePart) {
		
		List<String> cnames = poscategorydao.getCatagoryNames(cnamePart);
		String categoryNames = "";
		
		try {
			categoryNames = cnames.toString();
		} catch (java.lang.IndexOutOfBoundsException e) {
		}
		return categoryNames;
	}
	
	/** For Supplier Name Pop Up validation **/
	@RequestMapping(value="POSPSuppValidate.htm", method= RequestMethod.GET)
	public @ResponseBody String checkSupplier(@RequestParam("suppName") String suppName) 
	{		
		String found = "false";
		try{
			List<Ledger> suppNameList = ledgerDao.listCustomerPan(suppName);
			if(!suppNameList.isEmpty()){
				found = "true";
			}else {
				found = "false";
			}
		}catch(Exception e){ }
		return found;		
	}
	
	/** For category Name Pop Up validation **/
	@RequestMapping(value="POSPCatValidate.htm", method= RequestMethod.GET)
	public @ResponseBody String checkCategory(@RequestParam("catName") String catName) {
		String found = "false";
		try{
			List<POSCategory> poscatNameList = poscategorydao.searchExistingCategory(catName);
			if(!poscatNameList.isEmpty()){
				found = "true";
			}else {
				found = "false";
			}
		}catch(Exception e){ }
		return found;		
	}
	
	/** For Item Name Pop Up validation **/
	@RequestMapping(value="POSPItemNameValidate.htm", method= RequestMethod.GET)
	public @ResponseBody String checkItem(@RequestParam("popIName") String itemName) {
		String found = "false";
		try{
			if(itemName == null || itemName.length() == 0 ){
				found = "false";
			}else {			
				List<POSItem> positemNameList = positemdao.searchPOSItem(itemName);
				if(!positemNameList.isEmpty()){
					found = "true";
				}else {
					found = "false";
				}
			}
		}catch(Exception e){ }
		return found;		
	}
		
	/** For Submit new Item request mapping POPUP window **/
	@RequestMapping(value = "/POSItemPOPUP.htm", method = RequestMethod.GET)
	public @ResponseBody void newItem(@RequestParam("popIName") String popIName,@RequestParam("itemCategoryName") String itemCategoryName,@RequestParam("itemDiscP") String itemDiscP,@RequestParam("itemVatP") String itemVatP,@RequestParam("itemCompanyName") String itemCompanyName) 
	{
		if(popIName.length() == 0){
			popIName = "";
		}
		if(itemCategoryName.length() == 0){
			itemCategoryName = "";
		}
		if(itemDiscP.length() == 0){
			itemDiscP = "0.00";
		}
		if(itemVatP.length() == 0){
			itemVatP = "0.00";
		}
		if(itemCompanyName.length() == 0){
			itemCompanyName = "";
		}
		
		try 
		{				
			POSItem positem = new POSItem();
			List<POSCategory> posCategoryList = poscategoryDao.searchExistingCategory(itemCategoryName);			
			positem.setItemName(popIName);
			positem.setCategoryName(itemCategoryName);
			positem.setDiscountPercentage(new BigDecimal(itemDiscP.trim()));
			BigDecimal vatP = new BigDecimal(itemVatP.trim());
			positem.setVatPercentage(vatP);		
			positem.setCompanyName(itemCompanyName);
			
			if(!posCategoryList.isEmpty()){
				POSCategory poscategory = posCategoryList.get(0);
				poscategory.getPosItems().add(positem);
				poscategoryDao.insertCategory(poscategory);	
			}
			
		} catch (Exception e) {
		}		
	}
	
	/** For Submit new category request mapping POPUP window **/
	@RequestMapping(value = "/POScategoryPOPUP.htm", method = RequestMethod.POST)
	public @ResponseBody void newCategory(@RequestParam("catName") String catName,@RequestParam("discper") String discper,@RequestParam("vatper") String vatper,@RequestParam("desc") String desc) 
	{	
		if(catName.length()==0){
			catName = "";
		}
		if(discper.length()==0){
			discper = "0.00";
		}
		if(vatper.length() == 0){
			vatper = "0.00";
		}
		if(desc.length() == 0){
			desc = "";
		}
		
		try 
		{	
			POSCategory poscat = new POSCategory();
			poscat.setCategoryName(catName);
			poscat.setDiscountPercentage(new BigDecimal(discper.trim()));
			poscat.setVatPercentage(new BigDecimal(vatper.trim()));
			poscat.setDescription(desc);
			poscat.setTotalQuantity(0);
			poscategoryDao.insertCategory(poscat);
			
		} catch (ArrayIndexOutOfBoundsException e) {
		}catch(NumberFormatException ne){
		}		
	}
	
	/** For Item POP up Disc % and Vat % Retrievals **/
	@RequestMapping(value = "/getDiscVat.htm", method = RequestMethod.GET)
	public @ResponseBody String getDiscVat(@RequestParam("catNameVal") String catNameVal) 
	{	
		String result= "";
		try {
			List<POSCategory> popCategoryList = poscategorydao.searchExistingCategory(catNameVal);
			if(!popCategoryList.isEmpty()){
				List<String> finalList = new ArrayList<String>();
				finalList.add(popCategoryList.get(0).getDiscountPercentage().toString());
				finalList.add(popCategoryList.get(0).getVatPercentage().toString());
				result = finalList.toString();
			}			
		} catch (Exception e) {
		}
		return result;
	}	
	
	/** For Customer Name Pop Up validation **/
	@RequestMapping(value="POSCusValidate.htm", method= RequestMethod.GET)
	public @ResponseBody String checkCustomer(@RequestParam("custName") String custName) 
	{		//System.out.println("inside popup validation::::::");
		String found = "false";
		try{
			List<Ledger> custNameList = ledgerDao.listCustomerPan(custName);
		
			if(!custNameList.isEmpty()){
				found = "true";
			}else {
				found = "false";
			}
		}catch(Exception e){ }
		return found;		
	}
	
	// Adding new Customer request mapping POPUP window
	@RequestMapping(value = "/POScustomerPOPUP.htm", method = RequestMethod.POST)
	public @ResponseBody
	synchronized void newCustomer(
			@RequestParam("cusCreatedDate") String cusCreatedDate,
			@RequestParam("custName") String custName,
			@RequestParam("openingBal") String openingBal,
			@RequestParam("address") String address,
			@RequestParam("openingType") String openingType) {
		if (custName.length() == 0) {
			custName = "";
		}
		if (openingBal.length() == 0) {
			openingBal = "0.00";
		}
		if (openingType.length() == 0) {
			openingType = "";
		}
		if (address.length() == 0) {
			address = "";
		}

		Ledger ledger = new Ledger();
		try {

			ledger.setLedgerName(custName);
			Date currentDate = new SimpleDateFormat("dd/MM/yyyy").parse(cusCreatedDate);
			ledger.setLedgerDate(currentDate);
			ledger.setOpeningBalance(new BigDecimal(openingBal.trim()));
			ledger.setOpTotalBalance(new BigDecimal(openingBal.trim()));
			ledger.setClosingTotalBalance(new BigDecimal(openingBal.trim()));
			ledger.setOpeningType(openingType);
			ledger.setClosingTotalType(openingType);
			ledger.setOpTotalType(openingType);
			ledger.setAddress1(address);
			ledger.setAccountGroup("Sundry Debtors");
			ledger.setAccountGroupCode("A118");
			ledger.setCast("Unknown");
			ledger.setTypeOfSource("Friend");

			Accounts acct = accountsDao.getAccounts(18);
			acct.getLedgers().add(ledger);
			accountsDao.insertAccounts(acct);

			// Journal Entry For the Opening Balance

			jrnl = new Journal();
			List<String> ledgerCode = ledgerDao.getLedgerGroupCode(ledger.getAccountGroup());
			String journalCode = ledgerCode.get(0).toString();
			jrnl.setCreditCode(journalCode);
			jrnl.setDebitCode(journalCode);
			jrnl.setJournalNO(getJournalNumber(jrnl));
			jrnl.setJournalType("Opening Balance");
			jrnl.setTransactionId("L" + ledger.getLedgerId());
			jrnl.setJournalDate(ledger.getLedgerDate());

			if (openingType.equals("Debit")) {
				jrnl.setDebitAccountName(custName);
				jrnl.setCreditAccountName("Opening Balance");
			} else {
				jrnl.setDebitAccountName("Opening Balance");
				jrnl.setCreditAccountName(custName);
			}

			jrnl.setDebitAmount(new BigDecimal(openingBal.trim()));
			jrnl.setCreditAmount(new BigDecimal(openingBal.trim()));
			jrnl.setNarration("");
			journalDao.insertJournal((Journal) jrnl);
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {

		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	/** For Submit new Supplier request mapping POPUP window **/
	@RequestMapping(value = "/POSsupplierPOPUP.htm", method = RequestMethod.POST)
	public @ResponseBody
	void newSupplier(
			@RequestParam("suppCreatedDate") String createdDate,
			@RequestParam("suppName") String suppName,
			@RequestParam("openingBal") String openingBal,
			@RequestParam("address") String address,
			@RequestParam("openingType") String openingType,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("tinNumber") String tinNumber) {

		if (suppName.length() == 0) {
			suppName = "";
		}
		if (openingBal.length() == 0) {
			openingBal = "0.00";
		}
		if (openingType.length() == 0) {
			openingType = "";
		}
		if (address.length() == 0) {
			address = "";
		}
		if (phoneNumber.length() == 0) {
			phoneNumber = "";
		}
		if (tinNumber.length() == 0) {
			tinNumber = "";
		}

		Ledger ledger = new Ledger();
		try {
			// First Name Capitalization
			ledger.setLedgerName(suppName);
			Date currentDate = new SimpleDateFormat("dd/MM/yyyy").parse(createdDate);
			ledger.setLedgerDate(currentDate);
			ledger.setOpeningBalance(new BigDecimal(openingBal.trim()));
			ledger.setOpTotalBalance(new BigDecimal(openingBal.trim()));
			ledger.setClosingTotalBalance(new BigDecimal(openingBal.trim()));
			ledger.setOpeningType(openingType);
			ledger.setClosingTotalType(openingType);
			ledger.setOpTotalType(openingType);
			ledger.setAddress1(address);
			ledger.setPrimaryPhone(phoneNumber);
			ledger.setAlternatePhone(phoneNumber);
			ledger.setPanNumber(tinNumber);
			ledger.setAccountGroup("Sundry Creditors");
			ledger.setCast("Unknown");
			ledger.setTypeOfSource("Friend");
			ledger.setAccountGroupCode("A117");
			Accounts acct = accountsDao.getAccounts(17);
			acct.getLedgers().add(ledger);
			accountsDao.insertAccounts(acct);
			
			//Journal Entry For the Opening Balance
			
			jrnl = new Journal();
			List<String> ledgerCode = ledgerDao.getLedgerGroupCode(ledger.getAccountGroup());
			String journalCode = ledgerCode.get(0).toString();
			jrnl.setCreditCode(journalCode);
			jrnl.setDebitCode(journalCode);
			jrnl.setJournalNO(getJournalNumber(jrnl));
			jrnl.setJournalType("Opening Balance");
			jrnl.setTransactionId("L"+ledger.getLedgerId()); 
		    jrnl.setJournalDate(ledger.getLedgerDate());
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
			
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {

		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/*@RequestMapping(value = "/formPOScategoryPOPUP.htm", method = RequestMethod.POST)
	public @ResponseBody
	void addnewCategory(@ModelAttribute("posCategory") POSCategory poscategory,
			BindingResult result, SessionStatus status) {
		poscategoryValidator.validate(poscategory, result);
		if (result.hasErrors()) {
			ModelMap map = new ModelMap();
			map.put("command", poscategory);
			map.put("categoryList", poscategoryDao.listCategoryName());
			// map.addAttribute("errorType","insertError");
		}
		poscategoryDao.insertCategory(poscategory);
	}*/

}
