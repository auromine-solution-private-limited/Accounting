package com.jewellery.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.JewelStockDao;
import com.jewellery.dao.JournalDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.dao.RefineryReceiptDao;
import com.jewellery.entity.ItemMaster;
import com.jewellery.entity.JewelStock;
import com.jewellery.entity.Journal;
import com.jewellery.entity.Ledger;
import com.jewellery.entity.RefineryReceipt;
import com.jewellery.util.JournalCode;
import com.jewellery.validator.RefineryReceiptValidator;

@Controller
public class FormRefineryReceiptController extends JournalCode{
	@Autowired
	private LedgerDao ledgerDao;
	@Autowired
	private RefineryReceiptDao refineryReceiptDao;
	@Autowired
	private RefineryReceiptValidator refineryRvalidator;
	@Autowired
	private ItemMasterDao itemmasterDao;
	@Autowired
	private JewelStockDao jstockDao;
	private ItemMaster itemDetails = new ItemMaster();
	String RRitemcd;
	BigDecimal RRGrossWeight;
	BigDecimal RRNetWeight;
	BigDecimal RRgrossWeight;
	BigDecimal RRnetWt;
	BigDecimal RRtotalGrossWt;
	Integer pieces;
	Integer RRpieces;
	int ppq, tp;	
	BigDecimal ZERO = new BigDecimal(0);
	BigDecimal CONVERT = new BigDecimal("-1");
	private Journal jrnl;
	@Autowired
	private JournalDao journalDao;
	List<String> ledgerGroupCode;
	
	//Date Binding 
		@InitBinder
		protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
		throws Exception{
			CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
			binder.registerCustomEditor(Date.class,null, dateEditor);
		}	
		
	// Form View of Refinery Receipt
			@RequestMapping(value="/FormRefineryReceipt", method=RequestMethod.GET)
			public String jobOrderList(@ModelAttribute("refineryReceipt") RefineryReceipt refRefinery, Model model){
				model.addAttribute("suppliername", ledgerDao.listallSmithName());		    
				return "FormRefineryReceipt";
			}
			
		//  listing For RefineryReceiptList page
			@RequestMapping(value = "/RefineryReceiptList", method = RequestMethod.GET)
			public ModelAndView showForm(@ModelAttribute("refineryReceipt") RefineryReceipt refRefinery) {
				ModelMap model = new ModelMap();
				model.put("refineryReceiptList", refineryReceiptDao.listRefineryReceipt());
				return new ModelAndView("RefineryReceiptList", model);// this will return all the
			}
			
			//FormRefineryReceip  Cancel 
			@RequestMapping(value="/FormRefineryReceipt.htm",method=RequestMethod.POST,params="cancel") // cancel button to redirect to customer list page
			public String cancelForm(@ModelAttribute("refineryReceipt") RefineryReceipt refRefinery)
			{
				return "redirect:RefineryReceiptList.htm";
			}
			
			/** For View RefineryReceipt   Form ***/
			@RequestMapping(value="/viewRefineryReceipt.htm",method=RequestMethod.GET)
			public ModelAndView viewForm(@ModelAttribute("refineryReceipt")RefineryReceipt refRefinery,@RequestParam(value="rrId",required=false)Integer rrId,ModelMap model){
				model.addAttribute("refineryReceipt", refineryReceiptDao.getRefineryReceipt(rrId));
				model.addAttribute("suppliername", ledgerDao.listallSmithName());	
				return new ModelAndView("FormRefineryReceipt", model);
			}
			
			
			//To save the record
			@RequestMapping(value = "/FormRefineryReceipt.htm", method = RequestMethod.POST,params="insert")
			public ModelAndView addOrder(@ModelAttribute("refineryReceipt") RefineryReceipt refRefinery, BindingResult result,SessionStatus status, Model model) 
			{
				refineryRvalidator.validate(refRefinery, result);
				RRitemcd = refRefinery.getRritemcode();
				RRGrossWeight = refRefinery.getRrGrossWeight();
				RRNetWeight = refRefinery.getRrNetWeight();
				RRpieces=refRefinery.getRrpieces();
				
				if(result.hasErrors())
				{
					ModelMap map = new ModelMap();
					map.addAttribute("errorName", "insertError");
					model.addAttribute("suppliername", ledgerDao.listallSmithName());	
					map.put("command", refRefinery);
					return new ModelAndView("FormRefineryReceipt",map);
				}
				refRefinery.setRefineryReceiptNo(generateRefineryReceiptNO(refRefinery));
				refineryReceiptDao.insertRefineryReceipt(refRefinery);
				
				//Update stock For Refinery Receipt
				 List<ItemMaster> itemList = itemmasterDao.searchItemMaster(RRitemcd);
		  			for (int i = 0; i < itemList.size(); i++) {
		  				ItemMaster imast = (ItemMaster)itemList.get(i);
		  				if (imast instanceof ItemMaster) {
		  					
			  				itemDetails = (ItemMaster) imast; 
							
			  				RRgrossWeight = itemDetails.getGrossWeight();
			  				RRgrossWeight = RRgrossWeight.add(RRGrossWeight);
			  				itemDetails.setGrossWeight(RRgrossWeight);
			  				
			  				RRnetWt = itemDetails.getNetWeight();    			
			  				RRnetWt = RRnetWt.add(RRNetWeight);   				
			  				itemDetails.setNetWeight(RRnetWt);   
			  				
							RRtotalGrossWt = itemDetails.getTotalGrossWeight();
			  				RRtotalGrossWt = RRtotalGrossWt.add(RRgrossWeight);
			  				itemDetails.setTotalGrossWeight(RRtotalGrossWt);
			  				
			  				pieces=itemDetails.getQty();
			  				pieces=pieces+RRpieces;
			  				itemDetails.setQty(pieces);
			  				
			  				itemmasterDao.updateItemMaster(itemDetails);//update detail in item master 
		  				}    				
		  			}
		  		//Update Stock Entry In Jewel Stock
		  			JewelStock_RefineryReceipt_PositiveEntry(jstockDao, refRefinery.getRrGrossWeight(), refRefinery.getRrGrossWeight(), refRefinery.getRrGrossWeight(),refRefinery.getRrpieces(), "", refRefinery);  				 			
		  			/****
		  			 * Journal Entry & Ledger updation for Refinery Labour Charge If record saved with labour charge
		  			 * then this entry will be inserted in journal 
		  			 * */
		  			if(refRefinery.getLabourCharge().signum()==1){
		  				
		  			//******************* party Ledger Updation Ledger
						List<Ledger> ledgername=ledgerDao.searchLedger(refRefinery.getRrName());
						String typeSales=ledgername.get(0).getClosingTotalType();
						BigDecimal oldPartyCashBal=ledgername.get(0).getClosingTotalBalance();
						
						if(typeSales.equals("Credit")){
							oldPartyCashBal = ZERO.subtract(oldPartyCashBal);
						}
						
						BigDecimal finalClcashPartyBal=oldPartyCashBal.subtract(refRefinery.getLabourCharge());
					if(finalClcashPartyBal.signum() >= 0){
							ledgerDao.updateLedgerPartyBalance(finalClcashPartyBal, "Debit", refRefinery.getRrName());
						}else{
							finalClcashPartyBal = finalClcashPartyBal.multiply(CONVERT);
							ledgerDao.updateLedgerPartyBalance(finalClcashPartyBal, "Credit", refRefinery.getRrName());
						}
					
					//Refinery Labour charge ledger updation 
					List<Ledger>  salesLedger = ledgerDao.searchLedger("Refinery Labour Charge");
					BigDecimal OldsalesBl = salesLedger.get(0).getClosingTotalBalance();
					String salesBlType = salesLedger.get(0).getClosingTotalType();
					if(salesBlType.equals("Credit")){
						OldsalesBl = ZERO.subtract(OldsalesBl);
					}
					BigDecimal finalClSalesActBal=OldsalesBl.add(refRefinery.getLabourCharge());
								
					if(finalClSalesActBal.signum() >= 0){
						ledgerDao.updateLedgerPartyBalance(finalClSalesActBal, "Debit", "Refinery Labour Charge");
					}else{
						finalClSalesActBal = finalClSalesActBal.multiply(CONVERT);
						ledgerDao.updateLedgerPartyBalance(finalClSalesActBal, "Credit", "Refinery Labour Charge");
					}	
					
					//Journal Entry for Refinery Labour Charge 
					jrnl = new Journal();
					jrnl.setJournalNO(getJournalNumber(jrnl));
					jrnl.setJournalType("Journal");				
					jrnl.setJournalDate(refRefinery.getRrDate());
					jrnl.setTransactionId("RR"+refRefinery.getRrId());
	 				
					// Set Account group code as Debit code
					ledgerGroupCode = ledgerDao.getLedgerAccountCode("Refinery Labour Charge");			
					jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
			        jrnl.setDebitAccountName("Refinery Labour Charge");
			        
			     // Set Account group code as Credit code
					ledgerGroupCode = ledgerDao.getLedgerAccountCode(refRefinery.getRrName());			
					jrnl.setCreditCode(ledgerGroupCode.get(0).toString());	
			        jrnl.setCreditAccountName(refRefinery.getRrName());
			        
			        jrnl.setDebitAmount(refRefinery.getLabourCharge());
			        jrnl.setCreditAmount(refRefinery.getLabourCharge());
				    journalDao.insertJournal(jrnl);
		  			}
		  			
		  			
				return new ModelAndView("redirect:RefineryReceiptList.htm");
			}
			
			/** For Update Refinery Receipt  Form ***/
			@RequestMapping(value = "/FormRefineryReceipt.htm", method = RequestMethod.POST,params="update")
			public ModelAndView update(@ModelAttribute("refineryReceipt") RefineryReceipt refRefinery, BindingResult result, Model model) 
			{
				
				refineryRvalidator.validateUpdate(refRefinery, result);
				if(result.hasErrors())
				{
					ModelMap map = new ModelMap();
					map.addAttribute("errorName", "updateError");
					model.addAttribute("suppliername", ledgerDao.listallSmithName());	
					map.put("command", refRefinery);
					return new ModelAndView("FormRefineryReceipt",map);
				}
				List<RefineryReceipt> oldOrderObj = refineryReceiptDao.getrefineryReceiptId(refRefinery.getRrId());
				refineryReceiptDao.updateRefineryReceipt(refRefinery);
				//Update stock For Refinery Issue  
				  RRitemcd = refRefinery.getRritemcode();
				List<ItemMaster> itemList = itemmasterDao.searchItemMaster(RRitemcd);
	            /***
	             * if the updated value is greater then the inserted value then add difference value to c.s
	             * ****/
	            if(refRefinery.getRrGrossWeight().compareTo(oldOrderObj.get(0).getRrGrossWeight())==1 || refRefinery.getRrpieces().compareTo(oldOrderObj.get(0).getRrpieces())==1){
	            	for (int i = 0; i < itemList.size(); i++) {
	      				ItemMaster imast = (ItemMaster)itemList.get(i);
	      				if (imast instanceof ItemMaster) {
	    	  				itemDetails = (ItemMaster) imast; 
	    	  				
	            	BigDecimal oldgrswt=oldOrderObj.get(0).getRrGrossWeight();
	            	BigDecimal newgrswt=refRefinery.getRrGrossWeight();
	            	BigDecimal diffGrswt=oldgrswt.subtract(newgrswt);
	            	BigDecimal	RIgrossWeight = itemDetails.getGrossWeight();
	            	RIgrossWeight = RIgrossWeight.add(diffGrswt);
	            	itemDetails.setGrossWeight(RIgrossWeight);
	            	itemDetails.setNetWeight(RIgrossWeight); 
	            	itemDetails.setTotalGrossWeight(RIgrossWeight);
	            	
	            	Integer oldpieces=oldOrderObj.get(0).getRrpieces();
	            	Integer newppieces=refRefinery.getRrpieces();
	            	Integer finalpieces=oldpieces-newppieces;
	            	Integer pieces=itemDetails.getQty();
	            	pieces=pieces+finalpieces;
	            	System.out.println(pieces);
	            	itemDetails.setQty(pieces);
	            	
	            	itemmasterDao.updateItemMaster(itemDetails);//update detail in item master 
	            	//Update Stock Entry In Jewel Stock
	            	JewelStock_RefineryReceipt_PositiveEntryUpdate(jstockDao, refRefinery.getRrGrossWeight(), refRefinery.getRrGrossWeight(), refRefinery.getRrGrossWeight(),refRefinery.getRrpieces(), "", refRefinery); 
	      				}
	            	}
	            	  /***
	                 * if the updated value is smaller then the inserted value then add difference value to c.s
	                 * ****/
	            }else if(refRefinery.getRrGrossWeight().compareTo(oldOrderObj.get(0).getRrGrossWeight())==-1 || refRefinery.getRrpieces().compareTo(oldOrderObj.get(0).getRrpieces())==-1){
	            	for (int i = 0; i < itemList.size(); i++) {
	      				ItemMaster imast = (ItemMaster)itemList.get(i);
	      				if (imast instanceof ItemMaster) {
	    	  				itemDetails = (ItemMaster) imast; 
	    	  				
	            	BigDecimal oldgrswt=oldOrderObj.get(0).getRrGrossWeight();
	            	BigDecimal newgrswt=refRefinery.getRrGrossWeight();
	            	BigDecimal diffGrswt=oldgrswt.subtract(newgrswt);
	            	BigDecimal	RIgrossWeight = itemDetails.getGrossWeight();
	            	RIgrossWeight = RIgrossWeight.subtract(diffGrswt);
	            	itemDetails.setGrossWeight(RIgrossWeight);
	            	itemDetails.setNetWeight(RIgrossWeight); 
	            	itemDetails.setTotalGrossWeight(RIgrossWeight);
	            	
	            	Integer oldpieces=oldOrderObj.get(0).getRrpieces();
	            	Integer newppieces=refRefinery.getRrpieces();
	            	Integer finalpieces=oldpieces-newppieces;
	            	Integer pieces=itemDetails.getQty();
	            	pieces=pieces-finalpieces;
	            	itemDetails.setQty(pieces); 
	            	
	            	itemmasterDao.updateItemMaster(itemDetails);//update detail in item master 
	            	//Update Stock Entry In Jewel Stock
	            	JewelStock_RefineryReceipt_PositiveEntryUpdate(jstockDao, refRefinery.getRrGrossWeight(), refRefinery.getRrGrossWeight(), refRefinery.getRrGrossWeight(),refRefinery.getRrpieces(), "", refRefinery); 
	      				}
	            	}
	            }
	          /*  *//***
	             * if the updated value is greater then the inserted value then add difference value to c.s
	             * ****//*
	            if(refRefinery.getRrpieces().compareTo(oldOrderObj.get(0).getRrpieces())==1){
	            	Integer oldpieces=oldOrderObj.get(0).getRrpieces();
	            	Integer newppieces=refRefinery.getRrpieces();
	            	Integer finalpieces=oldpieces-newppieces;
	            	Integer pieces=itemDetails.getQty();
	            	pieces=pieces+finalpieces;
	            	System.out.println(pieces);
	            	itemDetails.setQty(pieces);
	            	itemmasterDao.updateItemMaster(itemDetails);//update detail in item master 
	            }
	            *//***
	             * if the updated value is smaller then the inserted value then add difference value to c.s
	             * ****//*
	            else if(refRefinery.getRrpieces().compareTo(oldOrderObj.get(0).getRrpieces())==-1){
	            	Integer oldpieces=oldOrderObj.get(0).getRrpieces();
	            	Integer newppieces=refRefinery.getRrpieces();
	            	Integer finalpieces=oldpieces-newppieces;
	            	Integer pieces=itemDetails.getQty();
	            	pieces=pieces-finalpieces;
	            	itemDetails.setQty(pieces); 
	            	itemmasterDao.updateItemMaster(itemDetails);//update detail in item master
	            }*/
	            
	            /****
	  			 * Journal Entry & Ledger updation for Refinery Labour Charge If record saved with labour charge
	  			 * then this entry will be inserted in journal 
	  			 * */
	  			if(oldOrderObj.get(0).getLabourCharge().signum()==0){
	  			
	  				//if Labour charge changed in update for Supplier Name
	  				List<Ledger> ledgername=ledgerDao.searchLedger(oldOrderObj.get(0).getRrName());
					String typeSales=ledgername.get(0).getClosingTotalType();
					BigDecimal oldPartyLabourBal=ledgername.get(0).getClosingTotalBalance();
					
					if(typeSales.equals("Credit")){
						oldPartyLabourBal = ZERO.subtract(oldPartyLabourBal);
					}
					BigDecimal tempLabCalc=oldPartyLabourBal.add(oldOrderObj.get(0).getLabourCharge());
					BigDecimal finalPartyLabourBal=tempLabCalc.subtract(refRefinery.getLabourCharge());
				if(finalPartyLabourBal.signum() >= 0){
						ledgerDao.updateLedgerPartyBalance(finalPartyLabourBal, "Debit", refRefinery.getRrName());
					}else{
						finalPartyLabourBal = finalPartyLabourBal.multiply(CONVERT);
						ledgerDao.updateLedgerPartyBalance(finalPartyLabourBal, "Credit", refRefinery.getRrName());
					}
				
	  				//if Labour charge changed in update for Refinery Labour Charge Ledger
	  				List<Ledger>  salesLedger = ledgerDao.searchLedger("Refinery Labour Charge");
					BigDecimal OldLabourBl = salesLedger.get(0).getClosingTotalBalance();
					String salesBlType = salesLedger.get(0).getClosingTotalType();
					
					if(salesBlType.equals("Credit")){
						OldLabourBl = ZERO.subtract(OldLabourBl);
					}
					BigDecimal tempLabCal=OldLabourBl.subtract(oldOrderObj.get(0).getLabourCharge());
					BigDecimal finalClLabourBal=tempLabCal.add(refRefinery.getLabourCharge());
								
					if(finalClLabourBal.signum() >= 0){
						ledgerDao.updateLedgerPartyBalance(finalClLabourBal, "Debit", "Refinery Labour Charge");
					}else{
						finalClLabourBal = finalClLabourBal.multiply(CONVERT);
						ledgerDao.updateLedgerPartyBalance(finalClLabourBal, "Credit", "Refinery Labour Charge");
					}
					//if Labour charge changed in update then update for journal and if already exist delete journal
					List<Journal> jrnlRefineryLabour = journalDao.getJournalUpdateSales("Journal","RR"+oldOrderObj.get(0).getRrId());
					if(!jrnlRefineryLabour.isEmpty()){	
						 if(refRefinery.getLabourCharge().signum() == 0){
								journalDao.deleteJournal(jrnlRefineryLabour.get(0)); /*** Delete Entry ***/
							}
						 else if(oldOrderObj.get(0).getLabourCharge().signum() == 1  && refRefinery.getLabourCharge().signum() == 1){
							 jrnlRefineryLabour.get(0).setDebitAmount(refRefinery.getLabourCharge());
							 jrnlRefineryLabour.get(0).setCreditAmount(refRefinery.getLabourCharge());
							 jrnlRefineryLabour.get(0).setJournalDate(refRefinery.getRrDate());
							 jrnlRefineryLabour.get(0).setCreditAccountName(refRefinery.getRrName());
						journalDao.updateJournal(jrnlRefineryLabour.get(0));
							}
					}
					//if Refinery Name Change 
					if(!oldOrderObj.get(0).getRrName().equals(refRefinery.getRrName()))
					{
						/** From Party 1 */
						List <Ledger> ledgerListCashParty1 = ledgerDao.searchLedger(oldOrderObj.get(0).getRrName());
						String clTypeCashParty1 = ledgerListCashParty1.get(0).getClosingTotalType();
						BigDecimal oldClBalanceCashParty1 = ledgerListCashParty1.get(0).getClosingTotalBalance();
						
						String finalClTypeParty1 = "";
						String finalClTypeParty2 = "";
						
						/** To Party 2 */
						List <Ledger> ledgerListCashParty2 = ledgerDao.searchLedger(refRefinery.getRrName());
						String clTypeCashParty2 = ledgerListCashParty2.get(0).getClosingTotalType();
						BigDecimal oldClBalanceCashParty2 = ledgerListCashParty2.get(0).getClosingTotalBalance();

						if(clTypeCashParty1.equalsIgnoreCase("Credit")){
							oldClBalanceCashParty1 = ZERO.subtract(oldClBalanceCashParty1);						
						}
						if(clTypeCashParty2.equalsIgnoreCase("Credit")){
							oldClBalanceCashParty2 = ZERO.subtract(oldClBalanceCashParty2);						
						}
						
						BigDecimal finalClBalanceCashParty1 = oldClBalanceCashParty1.add(oldOrderObj.get(0).getLabourCharge());
						BigDecimal finalClBalanceCashParty2 = oldClBalanceCashParty2.subtract(refRefinery.getLabourCharge());
										
						if(finalClBalanceCashParty1.signum() == -1){
							finalClTypeParty1 = "Credit";
							finalClBalanceCashParty1 = finalClBalanceCashParty1.multiply(CONVERT);
						}else{
							finalClTypeParty1 = "Debit";						
						}
						
						if(finalClBalanceCashParty2.signum() == -1){
							finalClTypeParty2 = "Credit";
							finalClBalanceCashParty2 = finalClBalanceCashParty2.multiply(CONVERT);
						}else{
							finalClTypeParty2 = "Debit";						
						}
						
						ledgerDao.updateLedgerPartyBalance(finalClBalanceCashParty1, finalClTypeParty1, oldOrderObj.get(0).getRrName());
						ledgerDao.updateLedgerPartyBalance(finalClBalanceCashParty2, finalClTypeParty2, refRefinery.getRrName());
					}
					
	  			}else if(refRefinery.getLabourCharge().signum()==0){
	  			//******************* party Ledger Updation Ledger
					List<Ledger> ledgername=ledgerDao.searchLedger(refRefinery.getRrName());
					String typeSales=ledgername.get(0).getClosingTotalType();
					BigDecimal oldPartyCashBal=ledgername.get(0).getClosingTotalBalance();
					
					if(typeSales.equals("Credit")){
						oldPartyCashBal = ZERO.subtract(oldPartyCashBal);
					}
					
					BigDecimal finalClcashPartyBal=oldPartyCashBal.subtract(refRefinery.getLabourCharge());
				if(finalClcashPartyBal.signum() >= 0){
						ledgerDao.updateLedgerPartyBalance(finalClcashPartyBal, "Debit", refRefinery.getRrName());
					}else{
						finalClcashPartyBal = finalClcashPartyBal.multiply(CONVERT);
						ledgerDao.updateLedgerPartyBalance(finalClcashPartyBal, "Credit", refRefinery.getRrName());
					}
				
				//Refinery Labour charge ledger updation 
				List<Ledger>  salesLedger = ledgerDao.searchLedger("Refinery Labour Charge");
				BigDecimal OldsalesBl = salesLedger.get(0).getClosingTotalBalance();
				String salesBlType = salesLedger.get(0).getClosingTotalType();
				if(salesBlType.equals("Credit")){
					OldsalesBl = ZERO.subtract(OldsalesBl);
				}
				BigDecimal finalClSalesActBal=OldsalesBl.add(refRefinery.getLabourCharge());
							
				if(finalClSalesActBal.signum() >= 0){
					ledgerDao.updateLedgerPartyBalance(finalClSalesActBal, "Debit", "Refinery Labour Charge");
				}else{
					finalClSalesActBal = finalClSalesActBal.multiply(CONVERT);
					ledgerDao.updateLedgerPartyBalance(finalClSalesActBal, "Credit", "Refinery Labour Charge");
				}	
				
				//Journal Entry for Refinery Labour Charge 
				jrnl = new Journal();
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Journal");				
				jrnl.setJournalDate(refRefinery.getRrDate());
				jrnl.setTransactionId("RR"+refRefinery.getRrId());
 				
				// Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("Refinery Labour Charge");			
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
		        jrnl.setDebitAccountName("Refinery Labour Charge");
		        
		     // Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(refRefinery.getRrName());			
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());	
		        jrnl.setCreditAccountName(refRefinery.getRrName());
		        
		        jrnl.setDebitAmount(refRefinery.getLabourCharge());
		        jrnl.setCreditAmount(refRefinery.getLabourCharge());
			    journalDao.insertJournal(jrnl);
			    
	  			}
	  			
				return new ModelAndView("redirect:RefineryReceiptList.htm"); 
			}
			
			/** RefineryReceipt :  Negative Entry - Insert **/
			public static void JewelStock_RefineryReceipt_PositiveEntry(JewelStockDao jstockDao,BigDecimal grossWeight, BigDecimal netWeight, BigDecimal totalGrossWeight,Integer qty, String StockType, RefineryReceipt refRefinery)
			{		
				JewelStock jewelStock = new JewelStock();		
							
				jewelStock.setStock_TransType("Refinery Receipt");
				jewelStock.setStock_StockType(StockType);
				jewelStock.setStock_TransNO(refRefinery.getRefineryReceiptNo());
				jewelStock.setStock_ItemCode(refRefinery.getRritemcode());
				jewelStock.setStock_TransDate(refRefinery.getRrDate());
				jewelStock.setStock_CategoryName(refRefinery.getRrOrnamentsType());		
				jewelStock.setStock_MetalUsed(refRefinery.getRrOrnamentsType());
				jewelStock.setStock_MetalType(refRefinery.getRrOrnamentsType());		
				jewelStock.setStock_CLGrossWeight(grossWeight);
				jewelStock.setStock_CLNetWeight(grossWeight);
				jewelStock.setStock_CLTotalGrossWeight(totalGrossWeight);
				jewelStock.setStock_CLTotalPieces(qty);
				jstockDao.saveJewelStock(jewelStock); 
				
			}
			
			/** RefineryReceipt :  Positive Entry - Update **/
			public static void JewelStock_RefineryReceipt_PositiveEntryUpdate(JewelStockDao jstockDao,BigDecimal grossWeight, BigDecimal netWeight, BigDecimal totalGrossWeight,Integer qty, String StockType, RefineryReceipt refineryIssue)
			{		
				// Stock Update ( UnTag )
				String transType="Refinery Receipt";
				List<JewelStock> jewelStockDBlist = jstockDao.searchByTransNO_TransType(refineryIssue.getRefineryReceiptNo(),transType);
				JewelStock jewelStockDB = jewelStockDBlist.get(0);
				jewelStockDB.setStock_TransNO(refineryIssue.getRefineryReceiptNo());
				jewelStockDB.setStock_ItemCode(refineryIssue.getRritemcode());
				jewelStockDB.setStock_TransDate(refineryIssue.getRrDate());
				jewelStockDB.setStock_CategoryName(refineryIssue.getRrOrnamentsType());		
				jewelStockDB.setStock_MetalUsed(refineryIssue.getRrOrnamentsType());
				jewelStockDB.setStock_MetalType(refineryIssue.getRrOrnamentsType());		
				jewelStockDB.setStock_CLGrossWeight(grossWeight);
				jewelStockDB.setStock_CLNetWeight(grossWeight);
				jewelStockDB.setStock_CLTotalGrossWeight(totalGrossWeight);
				jewelStockDB.setStock_CLTotalPieces(qty);
				jstockDao.updateJewelStock(jewelStockDB); 
				
			}

}
