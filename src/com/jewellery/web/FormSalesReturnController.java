package com.jewellery.web;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.servlet.ModelAndView;

import com.jewellery.core.JewelStockCore;
import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.JewelStockDao;
import com.jewellery.dao.JournalDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.dao.SalesReturnDao;
import com.jewellery.dao.UserloginDao;
import com.jewellery.entity.ItemMaster;
import com.jewellery.entity.Journal;
import com.jewellery.entity.Ledger;
import com.jewellery.entity.Sales;
import com.jewellery.util.JournalCode;
import com.jewellery.validator.SalesReturnValidator;

@Controller
public class FormSalesReturnController extends JournalCode {
		
	@Autowired
	private SalesReturnDao salesreturnDao;
	@Autowired
	private LedgerDao ledgerDao;
	@Autowired	
	private ItemMasterDao itemmasterDao;
	@Autowired
	private UserloginDao userloginDao;
	@Autowired
	private JournalDao journalDao;
	@Autowired
	private JewelStockDao jewelStockDao;
	
	private Journal jrnl;
	
	BigDecimal ZERO = new BigDecimal("0");
	BigDecimal CONVERT = new BigDecimal("-1");
	String finalClType ="";	
	String ledgerName;
	BigDecimal clBal;
	String clType;
	ItemMaster itemDetails;
	List<String> ledgerGroupCode;
	
	String itemcd = new String();		
	String itemcd1 = new String();
	String itemcd2 = new String();
	String itemcd3 = new String();
	String itemcd4 = new String();
	
	BigDecimal grosswt = new BigDecimal("0.0");
	BigDecimal netwt = new BigDecimal("0.0");
	BigDecimal itemgrosswt = new BigDecimal("0.0");
	BigDecimal itemnetwt = new BigDecimal("0.0");
	BigDecimal totgross = new BigDecimal("0.0");
	
	int nos = 0;
	int ppq, tp;
	int noqty = 0;
	
	@Autowired
	private SalesReturnValidator salesReturnValidator;	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		SimpleDateFormat dateFormat  = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true));		
	}
	
	@RequestMapping(value="/viewSalesReturn", method=RequestMethod.GET)
	public ModelAndView showForm(Sales sales,ModelMap model,@RequestParam(value="salesId",required=false)Integer salesId){
		sales = salesreturnDao.getSales(salesId);
		model.addAttribute("sales",sales); 
		return new ModelAndView("formsalesreturn",model);
	}
	
	@RequestMapping(value="/newSalesReturn",method=RequestMethod.GET)
	public ModelAndView newForm(Sales sales, ModelMap model){
		model.addAttribute("s_manname", userloginDao.userlist());
		model.addAttribute("name", ledgerDao.listLedgerName());	
		model.addAttribute("sales",sales);
		return new ModelAndView("formsalesreturn",model);
	}

	@RequestMapping(value="/SalesReturn",method=RequestMethod.POST,params="cancel")
	public ModelAndView cancelForm() {		
		return new ModelAndView("redirect:sales.htm");
	}
	
@RequestMapping(value="/SalesReturn.htm",method=RequestMethod.POST,params="insert")
public ModelAndView onSubmit(@ModelAttribute("sales") Sales sales, BindingResult result, ModelMap model)
{		
		salesReturnValidator.validate(sales, result);
		ItemMaster itemDetails = new ItemMaster();
		String itemcd = new String();		
		
	/*	BigDecimal grosswt = new BigDecimal("0.0");
		BigDecimal netwt = new BigDecimal("0.0");
		BigDecimal itemgrosswt = new BigDecimal("0.0");
		BigDecimal itemnetwt = new BigDecimal("0.0");
		BigDecimal totgross = new BigDecimal("0.0");
		int nos = 0;*/
		// row 2
		String itemcd1 = new String();		
		BigDecimal grosswt1 = new BigDecimal("0.0");
		BigDecimal netwt1 = new BigDecimal("0.0");
		int nos1 = 0;
		// row 3
		String itemcd2 = new String();		
		BigDecimal grosswt2 = new BigDecimal("0.0");
		BigDecimal netwt2 = new BigDecimal("0.0");
		int nos2 = 0;
		// row 4
		String itemcd3 = new String();		
		
		BigDecimal grosswt3 = new BigDecimal("0.0");
		BigDecimal netwt3 = new BigDecimal("0.0");
		
		int nos3 = 0;
		
		
		if(result.hasErrors()) 
		{
			ModelMap map = new ModelMap();			
			map.put("name", ledgerDao.listLedgerName());		    
			map.put("s_manname", userloginDao.userlist());
			map.put("errorType","insertError");
			map.put("command", sales);
			return new ModelAndView("formsalesreturn",map);
		}
			String salesTypeId = getSalesReturnTypeId(sales);
			sales.setSalesTypeId(salesTypeId);		
			salesreturnDao.insertSales((Sales) sales);
			
			itemcd =  sales.getItemCode();		
			grosswt = sales.getGrossWeight();
			netwt = sales.getNetWeight();
			nos = sales.getNumberOfPieces();
			// BigDecimal f1 = BigDecimal.parseBigDecimal(str);

			itemcd1 = sales.getItemCode1();		
			grosswt1 = sales.getGrossWeight1();
			netwt1 = sales.getNetWeight1();
			nos1 = sales.getNumberOfPieces1();
			
			itemcd2 = sales.getItemCode2();			
			grosswt2 = sales.getGrossWeight2();
			netwt2 = sales.getNetWeight2();
			nos2 = sales.getNumberOfPieces2();
			
			itemcd3 = sales.getItemCode3();			
			grosswt3 = sales.getGrossWeight3();
			netwt3 = sales.getNetWeight3();
			nos3 = sales.getNumberOfPieces3();

			// row 1			
			List<ItemMaster> itemList = itemmasterDao.searchItemMaster(itemcd);

			for (int i = 0; i < itemList.size(); i++) {
				ItemMaster imast = (ItemMaster) itemList.get(i);
				if (imast instanceof ItemMaster) {
					itemDetails = (ItemMaster) imast;
					itemgrosswt = itemDetails.getGrossWeight();
					itemnetwt = itemDetails.getNetWeight();
					totgross = itemDetails.getTotalGrossWeight();
					noqty = itemDetails.getQty();
					noqty = noqty + nos;
					itemgrosswt = itemgrosswt.add(grosswt);
					itemnetwt = itemnetwt.add(netwt);
					totgross = totgross.add(grosswt);
					itemDetails.setGrossWeight((BigDecimal)itemgrosswt);
					itemDetails.setNetWeight((BigDecimal)itemgrosswt);
					itemDetails.setQty(noqty);
					ppq = itemDetails.getPiecesPerQty();
					tp = ppq * noqty;
					itemDetails.setTotalPieces(tp);
					itemDetails.setTotalGrossWeight((BigDecimal)itemgrosswt);
					itemmasterDao.updateItemMaster(itemDetails);
					/***
					 * JewelStock Table Insert Updation for row 1
					 * *****/
					JewelStockCore.jewelStockSRInsertUpdation(jewelStockDao, "Sales Return","Bullion",sales.getSalesTypeId(),sales.getSalesDate(),
							sales.getItemName(),sales.getItemCode(),sales.getBullionType(), sales.getNumberOfPieces(),sales.getGrossWeight(),sales.getNetWeight(),sales.getGrossWeight(),
							sales.getStone(),sales.getStoneCost(),sales.getBullionRate(), sales);
					
				}

			}
		
			// row 2			
			itemList = itemmasterDao.searchItemMaster(itemcd1);

			for (int i = 0; i < itemList.size(); i++) {
				ItemMaster imast = (ItemMaster) itemList.get(i);
				if (imast instanceof ItemMaster) {
					itemDetails = (ItemMaster) imast;
					itemgrosswt = itemDetails.getGrossWeight();
					itemnetwt = itemDetails.getNetWeight();
					totgross = itemDetails.getTotalGrossWeight();
					noqty = itemDetails.getQty();
					noqty = noqty + nos1;

					itemgrosswt = itemgrosswt.add(grosswt1);
					itemnetwt = itemnetwt.add(netwt1);
					totgross = totgross.add(grosswt1);
					itemDetails.setGrossWeight((BigDecimal)itemgrosswt);
					itemDetails.setNetWeight((BigDecimal)itemgrosswt);
					itemDetails.setQty(noqty);
					ppq = itemDetails.getPiecesPerQty();
					tp = ppq * noqty;
					itemDetails.setTotalPieces(tp);
					itemDetails.setTotalGrossWeight((BigDecimal)itemgrosswt);
					itemmasterDao.updateItemMaster(itemDetails);
					/***
					 * JewelStock Table Insert Updation for row2
					 * *****/
				
					JewelStockCore.jewelStockSRInsertUpdation(jewelStockDao,"Sales Return","Bullion",sales.getSalesTypeId(),sales.getSalesDate(),
							sales.getItemName1(),sales.getItemCode1(),sales.getBullionType1(), sales.getNumberOfPieces1(),
							sales.getGrossWeight1(),sales.getNetWeight1(),sales.getGrossWeight1(),
							sales.getStone1(),sales.getStoneCost1(),sales.getBullionRate1(), sales);
				}

			}

			//row 3			
			itemList = itemmasterDao.searchItemMaster(itemcd2);

			for (int i = 0; i < itemList.size(); i++) {
				ItemMaster imast = (ItemMaster) itemList.get(i);
				if (imast instanceof ItemMaster) {
					itemDetails = (ItemMaster) imast;
					itemgrosswt = itemDetails.getGrossWeight();
					itemnetwt = itemDetails.getNetWeight();
					totgross = itemDetails.getTotalGrossWeight();
					noqty = itemDetails.getQty();
					noqty = noqty + nos2;
					itemgrosswt = itemgrosswt.add(grosswt2);
					itemnetwt = itemnetwt.add(netwt2);
					totgross = totgross.add(grosswt2);
					itemDetails.setGrossWeight((BigDecimal)itemgrosswt);
					itemDetails.setNetWeight((BigDecimal)itemgrosswt);
					itemDetails.setQty(noqty);
					ppq = itemDetails.getPiecesPerQty();
					tp = ppq * noqty;
					itemDetails.setTotalPieces(tp);
					itemDetails.setTotalGrossWeight((BigDecimal)itemgrosswt);
					itemmasterDao.updateItemMaster(itemDetails);
					/***
					 * JewelStock Table Insert Updation for row3
					 * *****/
					JewelStockCore.jewelStockSRInsertUpdation(jewelStockDao,"Sales Return","Bullion",sales.getSalesTypeId(),sales.getSalesDate(),
							sales.getItemName2(),sales.getItemCode2(),sales.getBullionType2(), sales.getNumberOfPieces2(),
							sales.getGrossWeight2(),sales.getNetWeight2(),sales.getGrossWeight2(),
							sales.getStone2(),sales.getStoneCost2(),sales.getBullionRate2(),sales);
				}

			}

			// row 4
			
			itemList = itemmasterDao.searchItemMaster(itemcd3);

			for (int i = 0; i < itemList.size(); i++) {
				ItemMaster imast = (ItemMaster) itemList.get(i);
				if (imast instanceof ItemMaster) {
				
					itemDetails = (ItemMaster) imast;
					
					itemgrosswt = itemDetails.getGrossWeight();
					itemnetwt = itemDetails.getNetWeight();
					totgross = itemDetails.getTotalGrossWeight();
					noqty = itemDetails.getQty();
					noqty = noqty + nos3;

					itemgrosswt = itemgrosswt.add(grosswt3);
					itemnetwt = itemnetwt.add(netwt3);
					totgross = totgross.add(grosswt3);
					
					itemDetails.setGrossWeight((BigDecimal)itemgrosswt);
					itemDetails.setNetWeight((BigDecimal)itemgrosswt);
					itemDetails.setQty(noqty);
					ppq = itemDetails.getPiecesPerQty();
					tp = ppq * noqty;
					itemDetails.setTotalPieces(tp);
					itemDetails.setTotalGrossWeight((BigDecimal)itemgrosswt);
					itemmasterDao.updateItemMaster(itemDetails);
					/***
					 * JewelStock Table Insert Updation for row2
					 * *****/
					JewelStockCore.jewelStockSRInsertUpdation(jewelStockDao,"Sales Return","Bullion",sales.getSalesTypeId(),sales.getSalesDate(),
							sales.getItemName3(),sales.getItemCode3(),sales.getBullionType3(), sales.getNumberOfPieces3(),
							sales.getGrossWeight3(),sales.getNetWeight3(),sales.getGrossWeight3(),
							sales.getStone3(),sales.getStoneCost3(),sales.getBullionRate3(),sales);
				}// end of if statement

			}// end of for loop
			
			//Auto Journal Entry and Ledger updation			
			String partyName = sales.getCustomerName();
			BigDecimal closingAmount = (BigDecimal) sales.getBillAmount();
			
			

			/* Update party Balance			
			
			String closingType = "Debit";
			String closingTyp = "Credit";

			List<Ledger> customerList = ledgerDao.searchLedger(partyName);
			clBal = customerList.get(0).getClosingTotalBalance();
			clType = customerList.get(0).getClosingTotalType();

			if (clType.equals("Debit")) {
				
				if (clBal.compareTo(closingAmount) == 1) {
					ledgerDao.updateCreditPartyBalance(closingAmount,closingType, partyName);
				} else {
					ledgerDao.updateCrPartyBalance(closingAmount, closingTyp,partyName);
				}
			}
			else {
				ledgerDao.updatePartyBalance(closingAmount, closingTyp,partyName);
			}
		
			/* Update Sales Account					
			String ledgername = "Sales Account";
			List <Ledger> salesList = ledgerDao.searchLedger(ledgername);
			
			clBal = salesList.get(0).getClosingTotalBalance();	
			clType = salesList.get(0).getClosingTotalType();					 				 
		 	
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
		 	}	*/	
		 	
		 	if(sales.getPaymentMode().equals("Cash")){
		 		
		 		/********************** Sales Ledger Updation ************************/
				 ledgerName = "Sales Account";
				List <Ledger> ledgerListSales = ledgerDao.searchLedger(ledgerName);
				BigDecimal clBalanceSales = ledgerListSales.get(0).getClosingTotalBalance();
				String clTypeSales = ledgerListSales.get(0).getClosingTotalType();
				
				if(clTypeSales.equalsIgnoreCase("Credit")){
					clBalanceSales = ZERO.subtract(clBalanceSales);						
				}
						
				BigDecimal finalClBalanceSales  = clBalanceSales.add(sales.getBillAmount());				
			
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
						
				BigDecimal finalClBalanceCashLedger = clBalanceCashLedger.subtract(sales.getBillAmount());
						
				if(finalClBalanceCashLedger.signum() == -1){
					finalClType = "Credit";
					finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
				}else{
						finalClType = "Debit";						
					}
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, finalClType, ledgerName);
				
		 		
		 	// Journal Entry for Sales  ledger
		 		jrnl = new Journal();
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Sales Return"); 
				jrnl.setJournalDate(sales.getSalesDate());
				jrnl.setTransactionId("SR"+sales.getSalesId());
				
				// Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("Sales Account");
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
				jrnl.setDebitAccountName("Sales Account");
				
				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName(partyName);
				
				jrnl.setDebitAmount(closingAmount);
				jrnl.setCreditAmount(closingAmount);
				jrnl.setNarration("Bill No "+sales.getSalesTypeId());
				journalDao.insertJournal((Journal) jrnl);
				
				// Journal Entry for Cash ledger
				
				jrnl = new Journal();	
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Sales Return Payment");
				jrnl.setTransactionId("SR"+sales.getSalesId()); // New Column added for tracking journal entries
			    jrnl.setJournalDate(sales.getSalesDate());
			    
			    // Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(sales.getCustomerName());			
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());				
				jrnl.setDebitAccountName(sales.getCustomerName());
								
				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName("Cash Account");
				
				jrnl.setDebitAmount(sales.getBillAmount());
				jrnl.setCreditAmount(sales.getBillAmount());
				jrnl.setNarration("Bill No "+ sales.getSalesTypeId());							
				journalDao.insertJournal((Journal) jrnl);
				
		 	}
		 	else if(sales.getPaymentMode().equals("Credit")){
		 		/***************************************** Party Ledger Udaption *******************************************/
				ledgerName = sales.getCustomerName();
				
				List <Ledger> ledgerListPartyLedger = ledgerDao.searchLedger(ledgerName);
				BigDecimal clBalanceCashLedger = ledgerListPartyLedger.get(0).getClosingTotalBalance();
				String clTypeCashLedger = ledgerListPartyLedger.get(0).getClosingTotalType();
				
				if(clTypeCashLedger.equalsIgnoreCase("Credit")){
					clBalanceCashLedger = ZERO.subtract(clBalanceCashLedger);						
				}
						
				BigDecimal finalClBalanceCashLedger = clBalanceCashLedger.subtract(sales.getBillAmount());
						
				if(finalClBalanceCashLedger.signum() == -1){
					finalClType = "Credit";
					finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
				}else{
						finalClType = "Debit";						
					}
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, finalClType, ledgerName);	
		 		
				/****************************************** Sales Ledger Updation ************************************************/
				ledgerName = "Sales Account";
				List <Ledger> ledgerListSales = ledgerDao.searchLedger(ledgerName);
				BigDecimal clBalanceSales = ledgerListSales.get(0).getClosingTotalBalance();
				String clTypeSales = ledgerListSales.get(0).getClosingTotalType();
				
				if(clTypeSales.equalsIgnoreCase("Credit")){
					clBalanceSales = ZERO.subtract(clBalanceSales);						
				}
						
				BigDecimal finalClBalanceSales  = clBalanceSales.add(sales.getBillAmount());				
			
				if (finalClBalanceSales.signum() == -1) {
					finalClType = "Credit";
					finalClBalanceSales = finalClBalanceSales.multiply(CONVERT);
				}
				else {
					finalClType = "Debit";					
				}
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceSales, finalClType, ledgerName);
				
				/*********** Journal Entry for Sales ledger **********/
				
				jrnl = new Journal();	
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Sales Return");
				jrnl.setTransactionId("SR"+sales.getSalesId()); // New Column added for tracking journal entries
			    jrnl.setJournalDate(sales.getSalesDate());
			    
			    // Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("Sales Account");			
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
				jrnl.setDebitAccountName("Sales Account");
				
				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(sales.getCustomerName());
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName(sales.getCustomerName());
				
				jrnl.setDebitAmount(sales.getBillAmount()); 
				jrnl.setCreditAmount(sales.getBillAmount());   
				jrnl.setNarration("Bill No " + sales.getSalesTypeId());							
				journalDao.insertJournal((Journal) jrnl);
			
		 	}
		return new ModelAndView("redirect:sales.htm",model);
	}


/************************************* Updating the sales return *******************************************/

@RequestMapping(value="/SalesReturn.htm",method=RequestMethod.POST,params="update")
public ModelAndView updateSalesreturn(@ModelAttribute("sales") Sales sales,BindingResult result, ModelMap model)
{
	
	salesReturnValidator.validate(sales, result);
	Integer id = sales.getSalesId();
	Sales salesObj = salesreturnDao.getSales(id);
	BigDecimal convert=new BigDecimal("-1");
	BigDecimal zero=new BigDecimal("0");
	BigDecimal clBal;
	//BigDecimal dropClBalanceParty;
	//BigDecimal finalClBalanceParty;
	String finalcltype;		
	String clType;
	if(result.hasErrors()) 
	{
		ModelMap map = new ModelMap();			
		map.put("name",ledgerDao.listallCustomerName());		    
		map.put("s_manname", userloginDao.userlist());
		map.put("errorType","updateError");
		map.put("command", sales);
		return new ModelAndView("formsalesreturn",map);
	}
	
	itemcd = sales.getItemCode();
	itemcd1 = sales.getItemCode1();
	itemcd2 = sales.getItemCode2();
	itemcd3 = sales.getItemCode3();
	String oldItemCode;
	
	//row1
		if (itemcd != null) {
			oldItemCode = salesObj.getItemCode();
			BigDecimal gross_Wt = salesObj.getGrossWeight();
			BigDecimal grosswt_form = sales.getGrossWeight();
			BigDecimal gwt;
			
			int pcs = salesObj.getNumberOfPieces();
			int pcs_set = sales.getNumberOfPieces();
			int pcs_edit;
			
			List<ItemMaster> itemList = itemmasterDao.searchItemMaster(itemcd);
			
			for (int i = 0; i < itemList.size(); i++) {
				ItemMaster imast = (ItemMaster) itemList.get(i);
				if (imast instanceof ItemMaster) {
					
					itemDetails = (ItemMaster) imast;
					itemgrosswt = itemDetails.getGrossWeight();
					noqty = itemDetails.getQty();
					
					if(sales.getItemCode().equals(salesObj.getItemCode()))
					{					
						gwt = grosswt_form.subtract(gross_Wt);
						pcs_edit = pcs_set - pcs;
						
						itemgrosswt = itemgrosswt.add(gwt);
						noqty = noqty + pcs_edit;					
						
						itemDetails.setGrossWeight(itemgrosswt);
						itemDetails.setNetWeight(itemgrosswt);
						itemDetails.setQty(noqty);
						itemDetails.setTotalGrossWeight(itemgrosswt);
						itemmasterDao.updateItemMaster(itemDetails);
						
						JewelStockCore.jewelStockSRUpdation(jewelStockDao,"Sales Return",salesObj.getSalesTypeId(),sales.getSalesDate(),
								sales.getItemName(),sales.getItemCode(),sales.getBullionType(), sales.getNumberOfPieces(),
								sales.getGrossWeight(),sales.getNetWeight(),sales.getGrossWeight(),
								sales.getStone(),sales.getStoneCost(),sales.getBullionRate(),sales);
					}	
					
					else
					{
						JewelStockCore.jewelStockSRUpdation(jewelStockDao,"Sales Return",salesObj.getSalesTypeId(),sales.getSalesDate(),
								sales.getItemName(),sales.getItemCode(),sales.getBullionType(), sales.getNumberOfPieces(),
								sales.getGrossWeight(),sales.getNetWeight(),sales.getGrossWeight(),
								sales.getStone(),sales.getStoneCost(),sales.getBullionRate(),sales);
						
						itemmasterDao.updateLooseStock( gross_Wt, gross_Wt, gross_Wt, pcs, oldItemCode);
						itemmasterDao.updateOldStock(grosswt_form, grosswt_form, grosswt_form, pcs_set, itemcd);						
					}
				}
			}
		}
	
	
		// row2
		if (itemcd1 != null) {
			
			oldItemCode = salesObj.getItemCode1();
			BigDecimal gross_Wt1 = salesObj.getGrossWeight1();
			BigDecimal grosswt_form1 = sales.getGrossWeight1();
			BigDecimal gwt1;
			
			int pcs_set1 = sales.getNumberOfPieces1();
			int pcs1 = salesObj.getNumberOfPieces1();
			int pcs_edit1;
			
			List<ItemMaster> itemList1 = itemmasterDao.searchItemMaster(itemcd1);
			
			for (int i = 0; i < itemList1.size(); i++) {
				ItemMaster imast = (ItemMaster) itemList1.get(i);
				if (imast instanceof ItemMaster) {
					itemDetails = (ItemMaster) imast;
					itemgrosswt = itemDetails.getGrossWeight();
					noqty = itemDetails.getQty();
					
					if(sales.getItemCode1().equals(salesObj.getItemCode1()))
					{
						gwt1 = grosswt_form1.subtract(gross_Wt1);
						pcs_edit1 = pcs_set1 - pcs1;
	
						itemgrosswt = itemgrosswt.add(gwt1);
						noqty = noqty + pcs_edit1;
						
						itemDetails.setGrossWeight(itemgrosswt);
						itemDetails.setNetWeight(itemgrosswt);
						itemDetails.setQty(noqty);
						itemDetails.setTotalGrossWeight(itemgrosswt);
						itemmasterDao.updateItemMaster(itemDetails);
						
						JewelStockCore.jewelStockSRUpdation( jewelStockDao,"Sales Return",salesObj.getSalesTypeId(),sales.getSalesDate(),
								sales.getItemName1(),sales.getItemCode1(),sales.getBullionType1(), sales.getNumberOfPieces1(),
								sales.getGrossWeight1(),sales.getNetWeight1(),sales.getGrossWeight1(),
								sales.getStone1(),sales.getStoneCost1(),sales.getBullionRate1(),sales);
					}
					else
					{
						JewelStockCore.jewelStockSRUpdation(jewelStockDao,"Sales Return",salesObj.getSalesTypeId(),sales.getSalesDate(),
								sales.getItemName1(),sales.getItemCode1(),sales.getBullionType1(), sales.getNumberOfPieces1(),
								sales.getGrossWeight1(),sales.getNetWeight1(),sales.getGrossWeight1(),
								sales.getStone1(),sales.getStoneCost1(),sales.getBullionRate1(),sales);
						itemmasterDao.updateLooseStock( gross_Wt1, gross_Wt1, gross_Wt1, pcs1, oldItemCode);
						itemmasterDao.updateOldStock(grosswt_form1, grosswt_form1, grosswt_form1, pcs_set1, itemcd1);
					}
				}
			}
		}
	
	//row3
	if(itemcd2!=null)
	{
		oldItemCode = salesObj.getItemCode2();
		BigDecimal gross_Wt2 = salesObj.getGrossWeight2();
		BigDecimal grosswt_form2=sales.getGrossWeight2();
		BigDecimal gwt2;
		
		int pcs2=salesObj.getNumberOfPieces2();
		int pcs_set2=sales.getNumberOfPieces2();
		int pcs_edit2;
		
		List<ItemMaster> itemList2 = itemmasterDao.searchItemMaster(itemcd2);
		
		for (int i = 0; i < itemList2.size(); i++) {
			ItemMaster imast = (ItemMaster) itemList2.get(i);
			if (imast instanceof ItemMaster) {
				
				itemDetails = (ItemMaster) imast;
				itemgrosswt = itemDetails.getGrossWeight();
				noqty = itemDetails.getQty();
				
				if(sales.getItemCode2().equals(salesObj.getItemCode2()))
				{					
					gwt2 =	grosswt_form2.subtract(gross_Wt2);
					pcs_edit2=pcs_set2-pcs2;
			
					itemgrosswt = itemgrosswt.add(gwt2);
					noqty=noqty+pcs_edit2;
					
					itemDetails.setGrossWeight(itemgrosswt);
					itemDetails.setNetWeight(itemgrosswt);
					itemDetails.setQty(noqty);
					itemDetails.setTotalGrossWeight(itemgrosswt);
					itemmasterDao.updateItemMaster(itemDetails);	
					
					JewelStockCore.jewelStockSRUpdation(jewelStockDao,"Sales Return",salesObj.getSalesTypeId(),sales.getSalesDate(),
							sales.getItemName2(),sales.getItemCode2(),sales.getBullionType2(), sales.getNumberOfPieces2(),
							sales.getGrossWeight2(),sales.getNetWeight2(),sales.getGrossWeight2(),
							sales.getStone2(),sales.getStoneCost2(),sales.getBullionRate2(),sales);
				}
				else
				{
					JewelStockCore.jewelStockSRUpdation(jewelStockDao,"Sales Return",salesObj.getSalesTypeId(),sales.getSalesDate(),
							sales.getItemName2(),sales.getItemCode2(),sales.getBullionType2(), sales.getNumberOfPieces2(),
							sales.getGrossWeight2(),sales.getNetWeight2(),sales.getGrossWeight2(),
							sales.getStone2(),sales.getStoneCost2(),sales.getBullionRate2(),sales);
					itemmasterDao.updateLooseStock( gross_Wt2, gross_Wt2, gross_Wt2, pcs2, oldItemCode);
					itemmasterDao.updateOldStock(grosswt_form2, grosswt_form2, grosswt_form2, pcs_set2, itemcd2);
				}
		}
	}
}
		
		//row4
	if(itemcd3!=null)
	{
		oldItemCode = salesObj.getItemCode3();
		BigDecimal gross_Wt3 = salesObj.getGrossWeight3();
		BigDecimal grosswt_form3=sales.getGrossWeight3();
		BigDecimal gwt3;
		int pcs3=salesObj.getNumberOfPieces3();
		int pcs_set3=sales.getNumberOfPieces3();
		int pcs_edit3;
		
		List<ItemMaster> itemList3 = itemmasterDao.searchItemMaster(itemcd3);
		
		for (int i = 0; i < itemList3.size(); i++) {
			ItemMaster imast = (ItemMaster) itemList3.get(i);
			if (imast instanceof ItemMaster) {
				itemDetails = (ItemMaster) imast;
				itemgrosswt = itemDetails.getGrossWeight();
				noqty = itemDetails.getQty();
				
				if(sales.getItemCode3().equals(salesObj.getItemCode3()))
				{					
					gwt3 =	grosswt_form3.subtract(gross_Wt3);
					pcs_edit3=pcs_set3-pcs3;
			
					itemgrosswt = itemgrosswt.add(gwt3);
					noqty=noqty+pcs_edit3;
					
					itemDetails.setGrossWeight(itemgrosswt);
					itemDetails.setNetWeight(itemgrosswt);
					itemDetails.setQty(noqty);
					itemDetails.setTotalGrossWeight(itemgrosswt);
					itemmasterDao.updateItemMaster(itemDetails);	
					
					JewelStockCore.jewelStockSRUpdation(jewelStockDao,"Sales Return",salesObj.getSalesTypeId(),sales.getSalesDate(),
							sales.getItemName3(),sales.getItemCode3(),sales.getBullionType3(), sales.getNumberOfPieces3(),
							sales.getGrossWeight3(),sales.getNetWeight3(),sales.getGrossWeight3(),
							sales.getStone3(),sales.getStoneCost3(),sales.getBullionRate3(),sales);
				}
				else
				{
					JewelStockCore.jewelStockSRUpdation(jewelStockDao,"Sales Return",salesObj.getSalesTypeId(),sales.getSalesDate(),
							sales.getItemName3(),sales.getItemCode3(),sales.getBullionType3(), sales.getNumberOfPieces3(),
							sales.getGrossWeight3(),sales.getNetWeight3(),sales.getGrossWeight3(),
							sales.getStone3(),sales.getStoneCost3(),sales.getBullionRate3(),sales);
					itemmasterDao.updateLooseStock( gross_Wt3, gross_Wt3, gross_Wt3, pcs3, oldItemCode);
					itemmasterDao.updateOldStock(grosswt_form3, grosswt_form3, grosswt_form3, pcs_set3, itemcd3);
				}
		}
	}
}
				
				//Auto Journal Entry and Ledger updation			
				//String partyName = sales.getCustomerName();
				//BigDecimal convert=new BigDecimal("-1");
				//BigDecimal zero=new BigDecimal("0");
				//String finalcltype;
/************************ Update party Balance	for same customer************************	
				if(salesObj.getCustomerName().equalsIgnoreCase(sales.getCustomerName()))
				{
					if(salesObj.getBillAmount().compareTo(sales.getBillAmount())!=0)
					{
						List<Ledger> customerList = ledgerDao.searchLedger(salesObj.getCustomerName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();
						if(clType.equalsIgnoreCase("Credit"))
						{
							clBal=zero.subtract(clBal);	
						}
						BigDecimal dropClBalanceParty =clBal.add(salesObj.getBillAmount());
						BigDecimal finalClBalanceParty  = dropClBalanceParty.subtract(sales.getBillAmount());
						if(finalClBalanceParty.signum() == -1){
							finalcltype = "Credit";
							finalClBalanceParty = finalClBalanceParty.multiply(convert);
						}
						else{
							finalcltype = "Debit";						
							
						}
						ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype,salesObj.getCustomerName());	
					}
					
				}*/
/**************************updating sales ledger***************************************
				if(salesObj.getCustomerName().equalsIgnoreCase(sales.getCustomerName()))
				{
					if(salesObj.getBillAmount().compareTo(sales.getBillAmount())!=0)
					{
						List<Ledger> customerList = ledgerDao.searchLedger("Sales Account");
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();
						if(clType.equalsIgnoreCase("Credit"))
						{
							clBal=zero.subtract(clBal);	
						}
						BigDecimal dropClBalanceParty =clBal.subtract(salesObj.getBillAmount());
						BigDecimal finalClBalanceParty  = dropClBalanceParty.add(sales.getBillAmount());
						if(finalClBalanceParty.signum() == -1){
							finalcltype = "Credit";
							finalClBalanceParty = finalClBalanceParty.multiply(convert);
						}
						else{
							finalcltype = "Debit";						
							
						}
						ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype,"Sales Account");
					}	
				}*/
				
/****************************journal entry updations******************************************
				if(salesObj.getCustomerName().equalsIgnoreCase(sales.getCustomerName()))
				{
					List<Journal> jrn=journalDao.getJournalUpdateSales("Sales Return", "SR"+sales.getSalesId());
					if(!jrn.isEmpty())
					{
						Journal list=jrn.get(0);
						list.setCreditAmount(sales.getBillAmount());
						list.setDebitAmount(sales.getBillAmount());
						list.setJournalDate(sales.getSalesDate());
						journalDao.updateJournal(list);							
					}
					
				}
/**************************update partybalance if changing customer name***********************
				if(!salesObj.getCustomerName().equalsIgnoreCase(sales.getCustomerName()))
				{
					
					if(salesObj.getCustomerName()!=null)
					{
						List<Ledger> customerList = ledgerDao.searchLedger(salesObj.getCustomerName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();
						if(clType.equalsIgnoreCase("Credit"))
						{
							clBal=zero.subtract(clBal);	
						}
						BigDecimal dropClBalanceParty =clBal.add(salesObj.getBillAmount());
						
						if(dropClBalanceParty.signum() == -1){
							finalcltype = "Credit";
							dropClBalanceParty = dropClBalanceParty.multiply(convert);
						}
						else{
							finalcltype = "Debit";						
						}
						
						ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,finalcltype,salesObj.getCustomerName());
					}
					if(sales.getCustomerName()!=null)
					{
						List<Ledger> customerList = ledgerDao.searchLedger(sales.getCustomerName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();
						if(clType.equalsIgnoreCase("Credit"))
						{
							clBal=zero.subtract(clBal);	
						}
						BigDecimal dropClBalanceParty =clBal.subtract(salesObj.getBillAmount());
						
						if(dropClBalanceParty.signum() == -1){
							finalcltype = "Credit";
							dropClBalanceParty = dropClBalanceParty.multiply(convert);
						}
						else{
							finalcltype = "Debit";						
							
						}
						ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,finalcltype,sales.getCustomerName());
					}
				}*/
/**********************updating journal entry when changing the customer names
				if(!salesObj.getCustomerName().equalsIgnoreCase(sales.getCustomerName()))
				{
					List<Journal> jrn=journalDao.getJournalUpdateSales("Sales Return", "SR"+sales.getSalesId());
					if(!jrn.isEmpty())
					{
						Journal list=jrn.get(0);
						list.setCreditAmount(sales.getBillAmount());
						list.setDebitAmount(sales.getBillAmount());
						list.setCreditAccountName(sales.getCustomerName());
						list.setJournalDate(sales.getSalesDate());
						journalDao.updateJournal(list);	
						
					}	
				}*/
			/*************** Ledger and Journal entry Updation **************/
	
			if(salesObj.getBillAmount().compareTo(sales.getBillAmount()) != 0) {
				
				List <Ledger> ledgerListSales = ledgerDao.searchLedger("Sales Account");
				BigDecimal oldClBalanceSales = ledgerListSales.get(0).getClosingTotalBalance();
				String clTypeSales = ledgerListSales.get(0).getClosingTotalType();
				
				if(clTypeSales.equalsIgnoreCase("Credit")){
					oldClBalanceSales = ZERO.subtract(oldClBalanceSales);						
				}
			
				BigDecimal dropClBalanceSales = oldClBalanceSales.subtract(salesObj.getBillAmount());
				BigDecimal finalClBalanceSales  = dropClBalanceSales.add(sales.getBillAmount());	
				
				if (finalClBalanceSales.signum() == -1) {
					finalClType = "Credit";
					finalClBalanceSales = finalClBalanceSales.multiply(CONVERT);
				} else {
					finalClType = "Debit";					
				}
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceSales, finalClType, "Sales Account");
			}	
	
	/***************** Credit mode for same customer **************/
	if(salesObj.getCustomerName().equalsIgnoreCase(sales.getCustomerName()))
	{ 
		if(salesObj.getPaymentMode().equalsIgnoreCase("Credit") && sales.getPaymentMode().equalsIgnoreCase("Credit")){
			
			List<Ledger> customerList = ledgerDao.searchLedger(salesObj.getCustomerName());
			clBal = customerList.get(0).getClosingTotalBalance();
			clType = customerList.get(0).getClosingTotalType();
			if(clType.equalsIgnoreCase("Credit"))
			{
				clBal=zero.subtract(clBal);	
			}
			BigDecimal dropClBalanceParty =clBal.add(salesObj.getBillAmount());
			BigDecimal finalClBalanceParty  = dropClBalanceParty.subtract(sales.getBillAmount());
			
			if(finalClBalanceParty.signum() == -1){
				finalcltype = "Credit";
				finalClBalanceParty = finalClBalanceParty.multiply(convert);
			}
			else{
				finalcltype = "Debit";						
				
			}
			ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype,salesObj.getCustomerName());	
			
			
			
			List<Journal> jrn=journalDao.getJournalUpdateSales("Sales Return", "SR"+sales.getSalesId());
			if(!jrn.isEmpty())
			{
				Journal list=jrn.get(0);
				list.setCreditAmount(sales.getBillAmount());
				list.setDebitAmount(sales.getBillAmount());
				list.setJournalDate(sales.getSalesDate());
				journalDao.updateJournal(list);							
			}
			
		/********************* same customer Cash mode updation *****************/
		}else if(salesObj.getPaymentMode().equalsIgnoreCase("Cash") && sales.getPaymentMode().equalsIgnoreCase("Cash")){	
			
			/* Cash Ledger Update */					
			List <Ledger> ledgerListCashLedger = ledgerDao.searchLedger("Cash Account");
			BigDecimal oldClBalanceCashLedger = ledgerListCashLedger.get(0).getClosingTotalBalance();
			String clTypeCashLedger = ledgerListCashLedger.get(0).getClosingTotalType();
			
			if(clTypeCashLedger.equalsIgnoreCase("Credit")){
				oldClBalanceCashLedger = ZERO.subtract(oldClBalanceCashLedger);						
			}
			
			BigDecimal dropClBalanceCashLedger = oldClBalanceCashLedger.add(salesObj.getBillAmount());
			BigDecimal finalClBalanceCashLedger = dropClBalanceCashLedger.subtract(sales.getBillAmount());
			if(finalClBalanceCashLedger.signum() == -1){
				finalClType = "Credit";
				finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
			}else{
					finalClType = "Debit";						
				}
			ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, finalClType, "Cash Account");	
			
			List<Journal> jrn=journalDao.getJournalUpdateSales("Sales Return", "SR"+sales.getSalesId());
			if(!jrn.isEmpty())
			{
				Journal list=jrn.get(0);
				list.setCreditAmount(sales.getBillAmount());
				list.setDebitAmount(sales.getBillAmount());
				list.setCreditAccountName(sales.getCustomerName());
				list.setJournalDate(sales.getSalesDate());
				journalDao.updateJournal(list);							
			}
			
			List<Journal> jrn2=journalDao.getJournalUpdateSales("Sales Return Payment", "SR"+sales.getSalesId());
			if(!jrn2.isEmpty())
			{
				Journal list=jrn2.get(0);
				list.setCreditAmount(sales.getBillAmount());
				list.setDebitAmount(sales.getBillAmount());
				list.setCreditAccountName(sales.getCustomerName());
				list.setJournalDate(sales.getSalesDate());
				journalDao.updateJournal(list);							
			}
			/***** Sames cutomer Cash to Credit updation ***********************/
		}else if(salesObj.getPaymentMode().equalsIgnoreCase("Cash") && sales.getPaymentMode().equalsIgnoreCase("Credit")){
			System.out.println("Same customer Cash to credit conditon::::::::::");
			
			/******************************** Update Party Balance ********************************/
			List<Ledger> customerList = ledgerDao.searchLedger(sales.getCustomerName());
			clBal = customerList.get(0).getClosingTotalBalance();
			clType = customerList.get(0).getClosingTotalType();
		//	System.out.println("current party balacne of cash to credit::::::"+clBal);
			if(clType.equalsIgnoreCase("Credit"))
			{
				clBal=zero.subtract(clBal);	
			}
			
		BigDecimal	finalClBalanceParty =clBal.subtract(sales.getBillAmount());
		//System.out.println("new bill amount of cash to credit::::::"+sales.getBillAmount());
		//System.out.println("FInal partybalance of cash to credit::::::"+sales.getBillAmount());
			if(finalClBalanceParty.signum() == -1){
				finalcltype = "Credit";
				finalClBalanceParty = finalClBalanceParty.multiply(convert);
			}
			else{
				finalcltype = "Debit";						
				
			}
			
			ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype,sales.getCustomerName());
			
			/******************** Update Cash Book Ledger ***************************/
			List<Ledger> cashList = ledgerDao.searchLedger("Cash Account");
			clBal = cashList.get(0).getClosingTotalBalance();
			clType = cashList.get(0).getClosingTotalType();
		//	System.out.println("current  Cash account balance of cash to credit::::::"+sales.getBillAmount());
			if(clType.equalsIgnoreCase("Credit"))
			{
				clBal=zero.subtract(clBal);	
			}
			
		BigDecimal	finalClCashAcct = clBal.add(salesObj.getBillAmount());		
	//	System.out.println("old bill amount of cash to credit::::::"+salesObj.getBillAmount());
			//System.out.println("final  Cash account balance of cash to credit::::::"+finalClCashAcct);
			if(finalClCashAcct.signum() == -1){
				finalcltype = "Credit";
				finalClCashAcct = finalClCashAcct.multiply(convert);
			}
			else{
				finalcltype = "Debit";						
				
			}
			
			ledgerDao.updateLedgerPartyBalance(finalClCashAcct, finalcltype, "Cash Account");	
			
			/* Second Journal Should be deleted  */
			List<Journal> jrnlListPayment = journalDao.getJournalUpdateSales("Sales Return Payment", "SR"+sales.getSalesId());
			if(!jrnlListPayment.isEmpty()) {						
				journalDao.deleteJournal(jrnlListPayment.get(0));
			}
			
			List<Journal> jrn=journalDao.getJournalUpdateSales("Sales Return", "SR"+sales.getSalesId());
			if(!jrn.isEmpty())
			{
				Journal list=jrn.get(0);
				list.setCreditAmount(salesObj.getBillAmount());
				list.setDebitAmount(salesObj.getBillAmount());
				list.setJournalDate(salesObj.getSalesDate());
				journalDao.updateJournal(list);							
			}
			/*********** Same CUstomer for Credit to cash ****************/
		}else if(salesObj.getPaymentMode().equals("Credit") && sales.getPaymentMode().equals("Cash")){
			
			//System.out.println("Same CUstomer for Credit to cash conditiont::::::::::::::::::::::::::::::::::::");
			/******************************** Update Party Balance ********************************/
			List<Ledger> customerList = ledgerDao.searchLedger(salesObj.getCustomerName());
			clBal = customerList.get(0).getClosingTotalBalance();
			clType = customerList.get(0).getClosingTotalType();
			
			if(clType.equalsIgnoreCase("Credit"))
			{
				clBal=zero.subtract(clBal);	
			}
			
			BigDecimal finalClBalanceParty =clBal.add(salesObj.getBillAmount());
		  		//System.out.println("old party amt:::::"+clBal);		
		  		//System.out.println("old bill amt:::::"+salesObj.getBillAmount());		
		  		//System.out.println("final party amt:::::"+finalClBalanceParty);		
			if(finalClBalanceParty.signum() == -1){
				finalcltype = "Credit";
				finalClBalanceParty = finalClBalanceParty.multiply(convert);
			}
			else{
				finalcltype = "Debit";						
				
			}
			
			ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype,sales.getCustomerName());
			
			
			/******************** Update Cash Book Ledger ***************************/
			List<Ledger> cashList = ledgerDao.searchLedger("Cash Account");
			clBal = cashList.get(0).getClosingTotalBalance();
			clType = cashList.get(0).getClosingTotalType();
			
			if(clType.equalsIgnoreCase("Credit"))
			{
				clBal=zero.subtract(clBal);	
			}
			
		BigDecimal	finalClcashAct = clBal.subtract(sales.getBillAmount());
			
	//	System.out.println("old cash amt:::::"+clBal);		
  	//	System.out.println("new bill amt:::::"+sales.getBillAmount());		
  	//	System.out.println("final cash amt:::::"+finalClcashAct);	
			
			if(finalClcashAct.signum() == -1){
				finalcltype = "Credit";
				finalClcashAct = finalClcashAct.multiply(convert);
			}
			else{
				finalcltype = "Debit";						
				
			}
			
			ledgerDao.updateLedgerPartyBalance(finalClcashAct, finalcltype, "Cash Account");
			
			List<Journal> jrn=journalDao.getJournalUpdateSales("Sales Return", "SR"+sales.getSalesId());
			if(!jrn.isEmpty())
			{
				Journal list=jrn.get(0);
				list.setCreditAmount(salesObj.getBillAmount());
				list.setDebitAmount(salesObj.getBillAmount());
			//	list.setCreditAccountName(salesObj.getCustomerName());
				list.setJournalDate(salesObj.getSalesDate());
				journalDao.updateJournal(list);		
			}
               // New Journal Entry for Cash ledger
				
				jrnl = new Journal();	
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Sales Return Payment");
				jrnl.setTransactionId("SR"+sales.getSalesId()); // New Column added for tracking journal entries
			    jrnl.setJournalDate(sales.getSalesDate());
			    
			    // Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(sales.getCustomerName());			
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());				
				jrnl.setDebitAccountName(sales.getCustomerName());
								
				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName("Cash Account");
				
				jrnl.setDebitAmount(sales.getBillAmount());
				jrnl.setCreditAmount(sales.getBillAmount());
				jrnl.setNarration("Bill No "+sales.getSalesTypeId());							
				journalDao.insertJournal((Journal) jrnl);
			
			
		}
	}else if(!salesObj.getCustomerName().equalsIgnoreCase(sales.getCustomerName())){
		if(salesObj.getPaymentMode().equalsIgnoreCase("Credit") && sales.getPaymentMode().equalsIgnoreCase("Credit")){
		/** From Party 1 */
		List <Ledger> ledgerListParty1 = ledgerDao.searchLedger(salesObj.getCustomerName());
		String clTypeCreditParty1 = ledgerListParty1.get(0).getClosingTotalType();
		BigDecimal oldClBalanceCreditParty1 = ledgerListParty1.get(0).getClosingTotalBalance();
		String finalClTypeParty1 = "";
		String finalClTypeParty2 = "";
		/** To Party 2 */
		List <Ledger> ledgerListCreditParty2 = ledgerDao.searchLedger(sales.getCustomerName());
		String clTypeCreditParty2 = ledgerListCreditParty2.get(0).getClosingTotalType();
		BigDecimal oldClBalanceCreditParty2 = ledgerListCreditParty2.get(0).getClosingTotalBalance();
		
		
		if(clTypeCreditParty1.equalsIgnoreCase("Credit")){
			oldClBalanceCreditParty1 = ZERO.subtract(oldClBalanceCreditParty1);						
		}
		if(clTypeCreditParty2.equalsIgnoreCase("Credit")){
			oldClBalanceCreditParty2 = ZERO.subtract(oldClBalanceCreditParty2);						
		}
		
		BigDecimal finalClBalanceCreditParty1 = oldClBalanceCreditParty1.add(salesObj.getBillAmount());
		BigDecimal finalClBalanceCreditParty2 = oldClBalanceCreditParty2.subtract(sales.getBillAmount());
		
		if(finalClBalanceCreditParty1.signum() == -1){
			finalClTypeParty1 = "Credit";
			finalClBalanceCreditParty1 = finalClBalanceCreditParty1.multiply(CONVERT);
		}else{
			finalClTypeParty1 = "Debit";						
		}
		
		if(finalClBalanceCreditParty2.signum() == -1){
			finalClTypeParty2 = "Credit";
			finalClBalanceCreditParty2 = finalClBalanceCreditParty2.multiply(CONVERT);
		}else{
			finalClTypeParty2 = "Debit";						
		}
		
		ledgerDao.updateLedgerPartyBalance(finalClBalanceCreditParty1, finalClTypeParty1, salesObj.getCustomerName());
		ledgerDao.updateLedgerPartyBalance(finalClBalanceCreditParty2, finalClTypeParty2, sales.getCustomerName());
		
		
		List<Journal> jrn=journalDao.getJournalUpdateSales("Sales Return", "SR"+sales.getSalesId());
		if(!jrn.isEmpty())
		{
			Journal list=jrn.get(0);
			list.setCreditAmount(sales.getBillAmount());
			list.setDebitAmount(sales.getBillAmount());
			list.setCreditAccountName(sales.getCustomerName());
			list.setJournalDate(sales.getSalesDate());
			journalDao.updateJournal(list);		
			
		}
		/******************* Cash to Credit for Different Customer ****************/
		}else if(salesObj.getPaymentMode().equals("Cash") && sales.getPaymentMode().equals("Credit")){
			//System.out.println("Different customer Cash to credit conditon::::::::::");
			
			/******************************** Update Party Balance ********************************/
			List<Ledger> customerList = ledgerDao.searchLedger(sales.getCustomerName());
			clBal = customerList.get(0).getClosingTotalBalance();
			clType = customerList.get(0).getClosingTotalType();
			
			if(clType.equalsIgnoreCase("Credit"))
			{
				clBal=zero.subtract(clBal);	
			}
			
			BigDecimal finalClBalanceParty =clBal.subtract(sales.getBillAmount());
		  				
			if(finalClBalanceParty.signum() == -1){
				finalcltype = "Credit";
				finalClBalanceParty = finalClBalanceParty.multiply(convert);
			}
			else{
				finalcltype = "Debit";						
				
			}
			
			ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype,sales.getCustomerName());
			
			
			/******************** Update Cash Book Ledger ***************************/
			List<Ledger> cashList = ledgerDao.searchLedger("Cash Account");
			clBal = cashList.get(0).getClosingTotalBalance();
			clType = cashList.get(0).getClosingTotalType();
			
			if(clType.equalsIgnoreCase("Credit"))
			{
				clBal=zero.subtract(clBal);	
			}
			
		BigDecimal	finalClCashAct = clBal.add(salesObj.getBillAmount());				
			
			if(finalClCashAct.signum() == -1){
				finalcltype = "Credit";
				finalClCashAct = finalClCashAct.multiply(convert);
			}
			else{
				finalcltype = "Debit";	
			}
			
			ledgerDao.updateLedgerPartyBalance(finalClCashAct, finalcltype, "Cash Account");	
			
			List<Journal> jrn=journalDao.getJournalUpdateSales("Sales Return", "SR"+sales.getSalesId());
			if(!jrn.isEmpty())
			{
				Journal list=jrn.get(0);
				list.setCreditAmount(salesObj.getBillAmount());
				list.setDebitAmount(salesObj.getBillAmount());
				list.setCreditAccountName(salesObj.getCustomerName());
				list.setJournalDate(salesObj.getSalesDate());
				journalDao.updateJournal(list);		
				
			}
			
			/* Second Journal Should be deleted  */
			List<Journal> jrnlListPayment = journalDao.getJournalUpdateSales("Sales Return Payment", "SR"+sales.getSalesId());
			if(!jrnlListPayment.isEmpty()) {						
				journalDao.deleteJournal(jrnlListPayment.get(0));
			}
			/************** different customer Credit to cash ************/
		}else if(salesObj.getPaymentMode().equals("Credit") && sales.getPaymentMode().equals("Cash")){
			//System.out.println(("different customer credit to cash conditon;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;"));
			/******************************** Update Party Balance ********************************/
			List<Ledger> customerList = ledgerDao.searchLedger(salesObj.getCustomerName());
			clBal = customerList.get(0).getClosingTotalBalance();
			clType = customerList.get(0).getClosingTotalType();
			
			if(clType.equalsIgnoreCase("Credit"))
			{
				clBal=zero.subtract(clBal);	
			}
			
			BigDecimal finalClBalanceParty =clBal.add(salesObj.getBillAmount());
		  		//System.out.println("old party amt:::::"+clBal);		
		  	//	System.out.println("old bill amt:::::"+salesObj.getBillAmount());		
		  		//System.out.println("final party amt:::::"+finalClBalanceParty);		
			if(finalClBalanceParty.signum() == -1){
				finalcltype = "Credit";
				finalClBalanceParty = finalClBalanceParty.multiply(convert);
			}
			else{
				finalcltype = "Debit";						
				
			}
			
			ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,finalcltype,salesObj.getCustomerName());
			
			
			/******************** Update Cash Book Ledger ***************************/
			List<Ledger> cashList = ledgerDao.searchLedger("Cash Account");
			clBal = cashList.get(0).getClosingTotalBalance();
			clType = cashList.get(0).getClosingTotalType();
			
			if(clType.equalsIgnoreCase("Credit"))
			{
				clBal=zero.subtract(clBal);	
			}
			
		BigDecimal	finalClcashAct = clBal.subtract(sales.getBillAmount());
			
	//	System.out.println("old cash amt:::::"+clBal);		
  	///	System.out.println("new bill amt:::::"+sales.getBillAmount());		
  	//	System.out.println("final cash amt:::::"+finalClcashAct);	
			
			if(finalClcashAct.signum() == -1){
				finalcltype = "Credit";
				finalClcashAct = finalClcashAct.multiply(convert);
			}
			else{
				finalcltype = "Debit";						
				
			}
			
			ledgerDao.updateLedgerPartyBalance(finalClcashAct, finalcltype, "Cash Account");
			
			List<Journal> jrn=journalDao.getJournalUpdateSales("Sales Return", "SR"+sales.getSalesId());
			if(!jrn.isEmpty())
			{
				Journal list=jrn.get(0);
				list.setCreditAmount(salesObj.getBillAmount());
				list.setDebitAmount(salesObj.getBillAmount());
				list.setCreditAccountName(salesObj.getCustomerName());
				list.setJournalDate(salesObj.getSalesDate());
				journalDao.updateJournal(list);		
			}
               // New Journal Entry for Cash ledger
				
				jrnl = new Journal();	
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Sales Return Payment");
				jrnl.setTransactionId("SR"+sales.getSalesId()); // New Column added for tracking journal entries
			    jrnl.setJournalDate(sales.getSalesDate());
			    
			    // Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(sales.getCustomerName());			
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());				
				jrnl.setDebitAccountName(sales.getCustomerName());
								
				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName("Cash Account");
				
				jrnl.setDebitAmount(sales.getBillAmount());
				jrnl.setCreditAmount(sales.getBillAmount());
				jrnl.setNarration("Bill No "+ sales.getSalesTypeId());							
				journalDao.insertJournal((Journal) jrnl);
		}else if(salesObj.getPaymentMode().equals("Cash") && sales.getPaymentMode().equals("Cash")){
			
			/******************** Update Cash Book Ledger ***************************/
			List<Ledger> cashList = ledgerDao.searchLedger("Cash Account");
			clBal = cashList.get(0).getClosingTotalBalance();
			clType = cashList.get(0).getClosingTotalType();
			
			if(clType.equalsIgnoreCase("Credit"))
			{
				clBal=zero.subtract(clBal);	
			}
			
		BigDecimal	ClCashAct = clBal.add(salesObj.getBillAmount());				
		BigDecimal	finalClCashAct = ClCashAct.subtract(sales.getBillAmount());		
			
			if(finalClCashAct.signum() == -1){
				finalcltype = "Credit";
				finalClCashAct = finalClCashAct.multiply(convert);
			}
			else{
				finalcltype = "Debit";	
			}
			
			ledgerDao.updateLedgerPartyBalance(finalClCashAct, finalcltype, "Cash Account");	
			
			List<Journal> jrn=journalDao.getJournalUpdateSales("Sales Return", "SR"+sales.getSalesId());
			if(!jrn.isEmpty())
			{
				Journal list=jrn.get(0);
				list.setCreditAmount(sales.getBillAmount());
				list.setDebitAmount(sales.getBillAmount());
				list.setCreditAccountName(sales.getCustomerName());
				list.setJournalDate(sales.getSalesDate());
				journalDao.updateJournal(list);							
			}
			
			List<Journal> jrn2=journalDao.getJournalUpdateSales("Sales Return Payment", "SR"+sales.getSalesId());
			if(!jrn2.isEmpty())
			{
				Journal list=jrn2.get(0);
				list.setCreditAmount(sales.getBillAmount());
				list.setDebitAmount(sales.getBillAmount());
				list.setCreditAccountName(sales.getCustomerName());
				list.setJournalDate(sales.getSalesDate());
				journalDao.updateJournal(list);							
			}
		}
	}
				salesreturnDao.updateSales((Sales) sales);	
				return new ModelAndView("redirect:sales.htm",model);
}
				public String getSalesReturnTypeId(Sales sales){
					
					BigInteger found = salesreturnDao.getLastSalesReturnNo();
					String billnoList = "SR"+found;
					String billNo = "SR0";	
					
					if(found != null){
						billNo = billnoList;
					}
				
				    String splitNo = billNo.substring(2);
				    int num = Integer.parseInt(splitNo);	      
				    num++;        
				    String number = Integer.toString(num);        
				    String displayCode = billNo.substring(0, 2) + number;
				    return displayCode;	
				}
} 