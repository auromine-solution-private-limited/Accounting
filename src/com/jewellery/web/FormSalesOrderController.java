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
import org.springframework.ui.Model;
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


import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.JobOrderDao;
import com.jewellery.dao.JournalDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.dao.PurchaseDao;
import com.jewellery.dao.RateMasterDao;
import com.jewellery.dao.SalesOrderDao;
import com.jewellery.dao.UserloginDao;
import com.jewellery.entity.ItemMaster;
import com.jewellery.entity.Journal;
import com.jewellery.entity.Ledger;
import com.jewellery.entity.Purchase;
import com.jewellery.entity.SalesOrder;
import com.jewellery.validator.SalesOrderValidator;

import com.jewellery.util.JournalCode;

@Controller
public class FormSalesOrderController extends JournalCode{

	private SalesOrderDao salesorderDao;
	private LedgerDao ledgerDao;	
	private ItemMasterDao itemmasterDao;
	private RateMasterDao ratemasterDao;
	private UserloginDao userloginDao;
	private JournalDao journalDao;	
	private Journal jrnl;
	private JobOrderDao jobOrderDao;
	private PurchaseDao purchaseDao;//new added
		
	BigDecimal clBal;	
	String clType;		 	
	String closingType; 
	String closingTyp;	
	String partyName = null;
	BigDecimal closingAmount= new BigDecimal("0.0");
	String order_Date = null;	   	
	BigDecimal ZERO = new BigDecimal(0);
	BigDecimal CONVERT = new BigDecimal("-1");
	List<String> ledgerGroupCode;
	
	@Autowired
	public FormSalesOrderController(JobOrderDao jobOrderDao,SalesOrderDao salesorderDao, LedgerDao ledgerDao, ItemMasterDao itemmasterDao, RateMasterDao ratemasterDao, UserloginDao userloginDao, JournalDao journalDao, PurchaseDao purchaseDao){
				
		this.salesorderDao = salesorderDao;
		this.ledgerDao = ledgerDao;		
		this.itemmasterDao = itemmasterDao;
		this.ratemasterDao = ratemasterDao;
		this.userloginDao = userloginDao;
		this.journalDao = journalDao;
		this.jobOrderDao=jobOrderDao;
		this.purchaseDao = purchaseDao;		
	}
	
	@Autowired
	private SalesOrderValidator salesOrderValidator;

	@InitBinder
	protected void initBinder(WebDataBinder binder)
	throws Exception{
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}	
	
//listing sales order details
	
			@RequestMapping(value="/salesorder.htm",method = RequestMethod.GET)
			public  ModelAndView listSalesOrder(SalesOrder salesOrder){
				Map<String, Object> model = new HashMap<String, Object>();				
				model.put("salesOrderList", salesorderDao.listSalesOrder());
				return new ModelAndView("salesorder",model);
			}

			
//Redirect the form to Job Order
			@RequestMapping(value = "/salesorderjoborder.htm", method = RequestMethod.GET)
			public ModelAndView showOrderForm(@RequestParam(value="orderNo") Integer orderNo, Model model) 
			{
				model.addAttribute("jobOrderList", jobOrderDao.searchJobOrder(orderNo));				
				return new ModelAndView ("salesorderjoborder");
			}
			
			
//Redirect the form for update
			@RequestMapping(value = "/viewSalesOrder.htm", method = RequestMethod.GET)
			public ModelAndView showForm(@ModelAttribute("salesorder") SalesOrder salesorder,@RequestParam(value="salesOrderId", required=false) Integer salesOrderId,Model model) 
			{				
				model.addAttribute("suppliername",ledgerDao.listLedgerName());		    
				model.addAttribute("s_manname", userloginDao.userlist());				
				model.addAttribute("itemnamelist", itemmasterDao.listAllItems());	
			    model.addAttribute("gs_rate", ratemasterDao.searchRateMaster());
			    model.addAttribute("bank_name", ledgerDao.listPosBank());
				model.addAttribute("assets_name", ledgerDao.listCurrentAssets());
				model.addAttribute("cash_name", ledgerDao.listCashAccount());
			    model.addAttribute("salesorder", salesorderDao.getSalesOrder(salesOrderId));
				return new ModelAndView ("formsalesorder");
			}

//bind the names in salesorder form	
			@RequestMapping(value="/newSalesOrder.htm", method=RequestMethod.GET)	
			public ModelAndView salesOrderList(@ModelAttribute("salesorder")SalesOrder salesorder,@RequestParam(value="salesid",required = false)Integer salesid,@RequestParam(value="printInvoice",required = false)String printInvoice){
						
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("suppliername",ledgerDao.listLedgerName());		    
				model.put("s_manname", userloginDao.userlist());				
				model.put("itemnamelist", itemmasterDao.listAllItems());	
			    model.put("gs_rate", ratemasterDao.searchRateMaster());
			    model.put("bank_name", ledgerDao.listPosBank());
			    model.put("assets_name", ledgerDao.listCurrentAssets());
			    model.put("cash_name", ledgerDao.listCashAccount());
			    model.put("salesid", salesid);
			    model.put("printInvoice",printInvoice);
				model.put("command", salesorder);
				
				return  new ModelAndView("formsalesorder",model);
			}
			
//To Add Or Save the record
			
			@RequestMapping(value = "/formsalesorder.htm", method = RequestMethod.POST, params="insert")
			public ModelAndView addData(@ModelAttribute("salesorder") SalesOrder salesorder, BindingResult result, @RequestParam(value="printInvoice", required = false) String printInvoice, SessionStatus status, ModelMap model) 
			{					
				closingType = "Debit"; 
				closingTyp = "Credit";	
						
				
				salesOrderValidator.validate(salesorder, result);				    
				model.addAttribute("bank_name", ledgerDao.listPosBank());
				model.addAttribute("assets_name", ledgerDao.listCurrentAssets());
				model.addAttribute("cash_name", ledgerDao.listCashAccount());
				
				if(result.hasErrors()) 
				{
					ModelMap map = new ModelMap();
					map.put("command", salesorder);
					map.put("suppliername",ledgerDao.listLedgerName());		    
					map.put("s_manname", userloginDao.userlist());					
					map.put("itemnamelist", itemmasterDao.listAllItems());	
				    map.put("gs_rate", ratemasterDao.searchRateMaster());
				    map.put("bank_name", ledgerDao.listPosBank());
				    map.put("assets_name", ledgerDao.listCurrentAssets());
				    map.put("cash_name", ledgerDao.listCashAccount());
					map.addAttribute("orderErrorType","insertError");
					return new ModelAndView("formsalesorder",map);
				}
				
				salesorderDao.insertSalesOrder(salesorder);							
				
				partyName = salesorder.getCustomerName();
				closingAmount = salesorder.getAdvance();
				

				          ///********************* Ledger Updation for Receipt mode:Cash Account 
			    if(salesorder.getCashPaymentSO()!=null){
			    	if(salesorder.getCashPaymentSO().equals("Cash") ){
	                           	//******************* party Ledger Updation Ledger
					List<Ledger> ledgername=ledgerDao.searchLedger(salesorder.getCustomerName());
					String typeSales=ledgername.get(0).getClosingTotalType();
					BigDecimal oldPartyCashBal=ledgername.get(0).getClosingTotalBalance();
					
					if(typeSales.equals("Credit")){
						oldPartyCashBal = ZERO.subtract(oldPartyCashBal);
					}
					
					BigDecimal finalClcashPartyBal=oldPartyCashBal.subtract(salesorder.getCashAmountSO());
					
			    	if(finalClcashPartyBal.signum() >= 0){
						ledgerDao.updateLedgerPartyBalance(finalClcashPartyBal, "Debit", salesorder.getCustomerName());
					}else{
						finalClcashPartyBal = finalClcashPartyBal.multiply(CONVERT);
						ledgerDao.updateLedgerPartyBalance(finalClcashPartyBal, "Credit", salesorder.getCustomerName());
					}
					
				              	///**************** Cash Account Updation Ledger			    	
					List<Ledger> ledName=ledgerDao.searchLedger(salesorder.getCashBankSO());
					String salTypes = ledName.get(0).getClosingTotalType();
					BigDecimal oldBalpartyCash=ledName.get(0).getClosingTotalBalance();
					
					if(salTypes.equals("Credit")){
						oldBalpartyCash = ZERO.subtract(oldBalpartyCash);
					}
					
					BigDecimal finalClCashActBal=oldBalpartyCash.add(salesorder.getCashAmountSO());
					
					if(finalClCashActBal.signum() >= 0){
						ledgerDao.updateLedgerPartyBalance(finalClCashActBal, "Debit", salesorder.getCashBankSO());
					}else{
						finalClCashActBal = finalClCashActBal.multiply(CONVERT);
						ledgerDao.updateLedgerPartyBalance(finalClCashActBal, "Credit", salesorder.getCashBankSO());
					}
					
					///Journal Entry For Cash Account 
					jrnl = new Journal();
					jrnl.setJournalNO(getJournalNumber(jrnl));
					jrnl.setJournalType("Sales Order Cash Receipt");
					jrnl.setJournalDate(salesorder.getOrderDate());
					jrnl.setTransactionId("SO"+salesorder.getSalesOrderId());
					
					//Set Account group code as Debit code
					ledgerGroupCode = ledgerDao.getLedgerAccountCode(salesorder.getCashBankSO());
					jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
					jrnl.setDebitAccountName(salesorder.getCashBankSO());
					
					//Set Account group code as Credit code
					ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
					jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					jrnl.setCreditAccountName(salesorder.getCustomerName());
					
					jrnl.setDebitAmount(salesorder.getCashAmountSO());
					jrnl.setCreditAmount(salesorder.getCashAmountSO());
					jrnl.setNarration("Bill No "+salesorder.getSalesOrderId());	
					journalDao.insertJournal(jrnl);
			        }//Cash condition ends here 
			    }//Not Equal to Condition ends here 
				
			    			//********************* Ledger Updation for Receipt mode:Cheque  
				   
			    		if(salesorder.getChequePaymentSO()!=null){
			    			if(salesorder.getChequePaymentSO().equals("Cheque")){
			    				//******************* party Ledger Updation Ledger
			    	List<Ledger> ledgerNamepartyCheq=ledgerDao.searchLedger(salesorder.getCustomerName());
					String salesTypes2 = ledgerNamepartyCheq.get(0).getClosingTotalType();
					BigDecimal oldBalcheqparty=ledgerNamepartyCheq.get(0).getClosingTotalBalance();
			    	
					if(salesTypes2.equals("Credit")){
						oldBalcheqparty = ZERO.subtract(oldBalcheqparty);
					}
					
					BigDecimal finalClcheqPartyBal=oldBalcheqparty.subtract(salesorder.getChequeAmountSO());
					
					if(finalClcheqPartyBal.signum() >= 0){
						ledgerDao.updateLedgerPartyBalance(finalClcheqPartyBal, "Debit",salesorder.getCustomerName());
					}else{
						finalClcheqPartyBal = finalClcheqPartyBal.multiply(CONVERT);
						ledgerDao.updateLedgerPartyBalance(finalClcheqPartyBal, "Credit",salesorder.getCustomerName());
					}
					
					///**************** Cheque Account Updation Ledger
						List<Ledger> ledName=ledgerDao.searchLedger(salesorder.getChequeBankSO());
						String salTypes2 = ledName.get(0).getClosingTotalType();
						BigDecimal oldBalCash=ledName.get(0).getClosingTotalBalance();
						
						if(salTypes2.equals("Credit")){
							oldBalCash = ZERO.subtract(oldBalCash);
						}
						
						BigDecimal finalClCarActBal=oldBalCash.add(salesorder.getChequeAmountSO());
						
						if(finalClCarActBal.signum() >= 0){
							ledgerDao.updateLedgerPartyBalance(finalClCarActBal, "Debit", salesorder.getChequeBankSO());
						}else{
							finalClCarActBal = finalClCarActBal.multiply(CONVERT);
							ledgerDao.updateLedgerPartyBalance(finalClCarActBal, "Credit", salesorder.getChequeBankSO());
						}
					
									///****************** Journal Entry for Cheque Amount 
					jrnl=new Journal();
					jrnl.setJournalNO(getJournalNumber(jrnl));
			    	jrnl.setJournalType("Sales Order Cheque Receipt");
			    	jrnl.setJournalDate(salesorder.getOrderDate());
			    	jrnl.setTransactionId("SO"+salesorder.getSalesOrderId());
			    	
			    	//Set Account group code as Debit code
					ledgerGroupCode = ledgerDao.getLedgerAccountCode(salesorder.getChequeBankSO());
					jrnl.setDebitCode(ledgerGroupCode.get(0).toString());			
			    	jrnl.setDebitAccountName(salesorder.getChequeBankSO());
			    	
			    	//Set Account group code as Credit code
					ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
					jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
			    	jrnl.setCreditAccountName(salesorder.getCustomerName());
			    	
			    	jrnl.setDebitAmount(salesorder.getChequeAmountSO());
			    	jrnl.setCreditAmount(salesorder.getChequeAmountSO());
			    	jrnl.setNarration("Bill No "+salesorder.getSalesOrderId());
			    	journalDao.insertJournal(jrnl);
			    	}//checque conditon ends here
			    }//Not Equal to Condition
				
			    		
			    		 ///********************************** Card Amount Updation 
					     if(salesorder.getCardPaymentSO()!=null){
					    	 if(salesorder.getCardPaymentSO().equals("Card")){
					    		 
					    		 
					    		 					//******************* party Ledger Updation 
							    	List<Ledger> ledgerNamepartCard=ledgerDao.searchLedger(salesorder.getCustomerName());
									String salesTypes3 = ledgerNamepartCard.get(0).getClosingTotalType();
									BigDecimal oldBalcardparty=ledgerNamepartCard.get(0).getClosingTotalBalance();
							    	
									if(salesTypes3.equals("Credit")){
										oldBalcardparty = ZERO.subtract(oldBalcardparty);
									}
									BigDecimal finalClCarPartyBal = oldBalcardparty.subtract(salesorder.getCardAmountSO());
									if(finalClCarPartyBal.signum() >= 0){
										ledgerDao.updateLedgerPartyBalance(finalClCarPartyBal, "Debit", salesorder.getCustomerName());
									}else{
										finalClCarPartyBal = finalClCarPartyBal.multiply(CONVERT);
										ledgerDao.updateLedgerPartyBalance(finalClCarPartyBal, "Credit", salesorder.getCustomerName());
									}
					    	 
												//*************** Card Account Ledger update 
			                 String cardlist=salesorder.getCardBankSO();		    
					    	 List<Ledger> ledName=ledgerDao.searchLedger(cardlist);
								String salTypes3 = ledName.get(0).getClosingTotalType();
								BigDecimal oldBalCash=ledName.get(0).getClosingTotalBalance();
								
								if(salTypes3.equals("Credit")){
									oldBalCash = ZERO.subtract(oldBalCash);
								}
								
								BigDecimal finalClCardActBal=oldBalCash.add(salesorder.getCardAmountSO());
								
								if(finalClCardActBal.signum() >= 0){
									ledgerDao.updateLedgerPartyBalance(finalClCardActBal, "Debit", cardlist);
								}else{
									finalClCardActBal = finalClCardActBal.multiply(CONVERT);
									ledgerDao.updateLedgerPartyBalance(finalClCardActBal, "Credit", cardlist);
								}
					    
										//********** new Journal Entry Card Amount 
					     jrnl=new Journal();
					     jrnl.setJournalNO(getJournalNumber(jrnl));
					     jrnl.setJournalType("Sales Order Card Receipt");
					     jrnl.setJournalDate(salesorder.getOrderDate());
					     jrnl.setTransactionId("SO"+salesorder.getSalesOrderId());
					     
					     //Set Account group code as Debit code
						 ledgerGroupCode = ledgerDao.getLedgerAccountCode(cardlist);
						 jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
					     jrnl.setDebitAccountName(cardlist);
					     
					     //Set Account group code as Credit code
						 ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
						 jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					     jrnl.setCreditAccountName(salesorder.getCustomerName());
					     
					     jrnl.setDebitAmount(salesorder.getCardAmountSO());
					     jrnl.setCreditAmount(salesorder.getCardAmountSO());
					     jrnl.setNarration("Bill No "+salesorder.getSalesOrderId());
					     journalDao.insertJournal(jrnl);
					    	 }//card conditon ends here
				}//card null condition check ends here
			    		
					     
					   //**************** Receipt Mode For::::: Voucher Amount
					     if(salesorder.getVoucherPaymentSO()!=null){
					if(salesorder.getVoucherPaymentSO().equalsIgnoreCase("Voucher")){
						
						//******************* party Ledger Updation 
				    	List<Ledger> ledgerNamepartyVouch=ledgerDao.searchLedger(salesorder.getCustomerName());
						String salesTypes4 = ledgerNamepartyVouch.get(0).getClosingTotalType();
						BigDecimal oldBalVouchparty=ledgerNamepartyVouch.get(0).getClosingTotalBalance();
				    	
				    	
						if(salesTypes4.equals("Credit")){
							oldBalVouchparty = ZERO.subtract(oldBalVouchparty);
						}
						
						BigDecimal finalClVouhPartyBal=oldBalVouchparty.subtract(salesorder.getVoucherAmountSO());
					//	System.out.println("Voucher oldbalparty::::"+oldBalVouchparty+"final amt:::"+finalClVouhPartyBal);
						if(finalClVouhPartyBal.signum() >= 0){
							ledgerDao.updateLedgerPartyBalance(finalClVouhPartyBal, "Debit", salesorder.getCustomerName());
						}else{
							finalClVouhPartyBal = finalClVouhPartyBal.multiply(CONVERT);
							ledgerDao.updateLedgerPartyBalance(finalClVouhPartyBal, "Credit", salesorder.getCustomerName());
						}
		    	 
		    	 
		    	 //*************** Voucher Account Ledger update 
		         String voucherlist=salesorder.getVoucherListSO();		    
		    	 List<Ledger> ledName=ledgerDao.searchLedger(voucherlist);
					String salTypes = ledName.get(0).getClosingTotalType();
					BigDecimal oldBalCash=ledName.get(0).getClosingTotalBalance();
					
					if(salTypes.equals("Credit")){
						oldBalCash = ZERO.subtract(oldBalCash);
					}
					
					BigDecimal finalClVoucherActBal=oldBalCash.add(salesorder.getVoucherAmountSO());
					
					if(finalClVoucherActBal.signum() >= 0){
						ledgerDao.updateLedgerPartyBalance(finalClVoucherActBal, "Debit", voucherlist);
					}else{
						finalClVoucherActBal = finalClVoucherActBal.multiply(CONVERT);
						ledgerDao.updateLedgerPartyBalance(finalClVoucherActBal, "Credit", voucherlist);
					}
		     
		     //********** new Journal Entry Voucher Amount 
		     jrnl=new Journal();
		     jrnl.setJournalNO(getJournalNumber(jrnl));
		     jrnl.setJournalType("Sales Order Voucher Receipt");
		     jrnl.setJournalDate(salesorder.getOrderDate());
		     jrnl.setTransactionId("SO"+salesorder.getSalesOrderId());
		     
		     //Set Account group code as Debit code
		     ledgerGroupCode = ledgerDao.getLedgerAccountCode(voucherlist);
			 jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
		     jrnl.setDebitAccountName(voucherlist);
		      
		     //Set Account group code as Credit code
			 ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
			 jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
		     jrnl.setCreditAccountName(salesorder.getCustomerName());
		     
		     jrnl.setDebitAmount(salesorder.getVoucherAmountSO());
		     jrnl.setCreditAmount(salesorder.getVoucherAmountSO());
		     jrnl.setNarration("Bill No "+salesorder.getSalesOrderId());
		     journalDao.insertJournal(jrnl);     			   
					     
				}//voucher conditon ends here
					     }//voucher null conditon check ends here
				
				/*                  Auto Receipt For journal		(THIS IS COMMENTED FOR MST,SINCE WE IMPLEMETING MULTIPLY RECEIPT MODE )							
				if(salesorder.getAdvance().signum() != 0)
				{
					jrnl = new Journal();		           	
					jrnl.setJournalNO(getJournalNumber(jrnl));
					jrnl.setTransactionId("SO"+salesorder.getSalesOrderId());
					jrnl.setJournalType("SalesOrder Receipt");
				    jrnl.setJournalDate(salesorder.getOrderDate());
					jrnl.setDebitAccountName("Cash Account");
					jrnl.setCreditAccountName(partyName);
					jrnl.setDebitAmount(closingAmount);
					jrnl.setCreditAmount(closingAmount);
					jrnl.setNarration("Order Number " +salesorder.getSalesOrderId());				
					journalDao.insertJournal((Journal) jrnl);	
		           	
		            //update party balance
		                     	
					List<Ledger> customerList = ledgerDao.searchLedger(partyName);				
					clBal = customerList.get(0).getClosingTotalBalance();	
					clType = customerList.get(0).getClosingTotalType();		 			 				 
				 	
					if(clType.equals("Credit")){
				 		ledgerDao.updatePartyBalance(closingAmount, closingTyp, partyName);		 		
				 	}
				 	else if(clType.equals("Debit")){	
				 		if(clBal.compareTo(closingAmount) == 1){
				 			ledgerDao.updateCreditPartyBalance(closingAmount, closingType, partyName);
				 		}
				 		else{
				 			ledgerDao.updateCrPartyBalance(closingAmount, "Credit", partyName);
				 		}
				 	}	//update Ledger list End			
					
					//Update Cash Book
					String ledgername="Cash Account";
					List <Ledger> ledgerList = ledgerDao.searchLedger(ledgername);		
					clBal = ledgerList.get(0).getClosingTotalBalance();	
					clType = ledgerList.get(0).getClosingTotalType();					  				 
				 	 
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
				 	}// Update Cash book end
					
				 }*/
				model.addAttribute("printInvoice",printInvoice.trim());
				model.addAttribute("salesid", salesorder.getSalesOrderId());
				status.setComplete();
				return new ModelAndView("redirect:newSalesOrder.htm",model);
			}
			
			//to Update the record
			
			@RequestMapping(value = "/formsalesorder.htm", method = RequestMethod.POST, params="update")
			public ModelAndView updateData(@ModelAttribute("salesorder") SalesOrder salesorder, BindingResult result, Model model)
			{						
				closingType = "Debit"; 
				closingTyp = "Credit";	
				
				salesOrderValidator.validate(salesorder, result);
				
				model.addAttribute("suppliername",ledgerDao.listLedgerName());		    
				model.addAttribute("s_manname", userloginDao.userlist());				
				model.addAttribute("itemnamelist", itemmasterDao.listAllItems());	
			    model.addAttribute("gs_rate", ratemasterDao.searchRateMaster());
			    model.addAttribute("bank_name", ledgerDao.listPosBank());
				model.addAttribute("assets_name", ledgerDao.listCurrentAssets());
				model.addAttribute("cash_name", ledgerDao.listCashAccount());
				
				if(result.hasErrors())
				{			
					ModelMap map = new ModelMap();			
					map.put("command", salesorder);
					map.put("suppliername",ledgerDao.listLedgerName());		    
					map.put("s_manname", userloginDao.userlist());						
					map.put("itemnamelist", itemmasterDao.listAllItems());	
				    map.put("gs_rate", ratemasterDao.searchRateMaster());
				    map.put("bank_name", ledgerDao.listPosBank());
				    map.put("assets_name", ledgerDao.listCurrentAssets());
				    map.put("cash_name", ledgerDao.listCashAccount());
					map.addAttribute("orderErrorType","updateError");
					return new ModelAndView("formsalesorder",map);				
				}
			
				partyName = salesorder.getCustomerName(); 
				closingAmount = salesorder.getAdvance();				
				Integer order_Id = salesorder.getSalesOrderId();
				SalesOrder oldOrderObj = salesorderDao.getSalesOrder(order_Id);
				
////////////////////////////Receipt Mode - Cash Ledgers Update /////////////////////////////////////////
		if(!oldOrderObj.getCustomerName().equals(partyName)) 
		{
				//********************* Cash Party balance Update : if - different Customer Names **************************
			/** From Party 1 */
			List <Ledger> ledgerListCashParty1 = ledgerDao.searchLedger(oldOrderObj.getCustomerName());
			String clTypeCashParty1 = ledgerListCashParty1.get(0).getClosingTotalType();
			BigDecimal oldClBalanceCashParty1 = ledgerListCashParty1.get(0).getClosingTotalBalance();
			
			String finalClTypeParty1 = "";
			String finalClTypeParty2 = "";
			
			/** To Party 2 */
			List <Ledger> ledgerListCashParty2 = ledgerDao.searchLedger(partyName);
			String clTypeCashParty2 = ledgerListCashParty2.get(0).getClosingTotalType();
			BigDecimal oldClBalanceCashParty2 = ledgerListCashParty2.get(0).getClosingTotalBalance();

			if(clTypeCashParty1.equalsIgnoreCase("Credit")){
				oldClBalanceCashParty1 = ZERO.subtract(oldClBalanceCashParty1);						
			}
			if(clTypeCashParty2.equalsIgnoreCase("Credit")){
				oldClBalanceCashParty2 = ZERO.subtract(oldClBalanceCashParty2);						
			}
			
			BigDecimal finalClBalanceCashParty1 = oldClBalanceCashParty1.add(oldOrderObj.getCashAmountSO());
			BigDecimal finalClBalanceCashParty2 = oldClBalanceCashParty2.subtract(salesorder.getCashAmountSO());
							
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
			
			ledgerDao.updateLedgerPartyBalance(finalClBalanceCashParty1, finalClTypeParty1, oldOrderObj.getCustomerName());
			ledgerDao.updateLedgerPartyBalance(finalClBalanceCashParty2, finalClTypeParty2, salesorder.getCustomerName());
		}
		else if(!oldOrderObj.getCustomerName().equals("Walk-in")){
			//********************* Cash Party balance Update : if - Same Customer Names **************************
		
				List <Ledger> ledgerListCashParty = ledgerDao.searchLedger(oldOrderObj.getCustomerName());
				BigDecimal oldClBalanceCashParty = ledgerListCashParty.get(0).getClosingTotalBalance();
				String clTypeCashParty = ledgerListCashParty.get(0).getClosingTotalType();
				String	finalClCashType="";
				if(clTypeCashParty.equalsIgnoreCase("Credit")){
					oldClBalanceCashParty = ZERO.subtract(oldClBalanceCashParty);						
				}
				
				BigDecimal dropClBalanceCashParty = oldClBalanceCashParty.add(oldOrderObj.getCashAmountSO());
				BigDecimal finalClBalanceCashParty  = dropClBalanceCashParty.subtract(salesorder.getCashAmountSO());
									
				if(finalClBalanceCashParty.signum() == -1){
					finalClCashType = "Credit";
					finalClBalanceCashParty = finalClBalanceCashParty.multiply(CONVERT);
				}else{
					finalClCashType = "Debit";						
					}
				ledgerDao.updateLedgerPartyBalance(finalClBalanceCashParty, finalClCashType, oldOrderObj.getCustomerName());					
			}
				////////////////////////////////////C2.Cash Bank Ledger Update ////////////////////////////////////////////
				List <Ledger> ledgerListCashLedger = ledgerDao.searchLedger(oldOrderObj.getCashBankSO());
				
				if(!ledgerListCashLedger.isEmpty()) 
				{ // if initially cash selected
				
				BigDecimal oldClBalanceCashLedger = ledgerListCashLedger.get(0).getClosingTotalBalance();
				String clTypeCashLedger = ledgerListCashLedger.get(0).getClosingTotalType();
				String finalClType="";
				if(clTypeCashLedger.equalsIgnoreCase("Credit")){
					oldClBalanceCashLedger = ZERO.subtract(oldClBalanceCashLedger);						
				}
				
				if(oldOrderObj.getCashBankSO().equals(salesorder.getCashBankSO())){
				//********************************* If the Bank A/c is "Same".******************************************/
				BigDecimal dropClBalanceCashLedger = oldClBalanceCashLedger.subtract(oldOrderObj.getCashAmountSO());
				BigDecimal finalClBalanceCashLedger = dropClBalanceCashLedger.add(salesorder.getCashAmountSO());
				
				if(finalClBalanceCashLedger.signum() == -1){
				finalClType = "Credit";
				finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
				}
				else{
				finalClType = "Debit";						
				}
				ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, finalClType, oldOrderObj.getCashBankSO());					
				}else {					
				//************************ If the Bank A/c Name "Changes".******************************************/		
				/* B1. For old Bank A/c  */// Subtract old amount value in old Ledger
				
				BigDecimal finalOldBankLedgerAmt = oldClBalanceCashLedger.subtract(oldOrderObj.getCashAmountSO());
				if(finalOldBankLedgerAmt.signum() == -1){
				finalClType = "Credit";
				finalOldBankLedgerAmt = finalOldBankLedgerAmt.multiply(CONVERT);
				}else{
				finalClType = "Debit";						
				}
				ledgerDao.updateLedgerPartyBalance(finalOldBankLedgerAmt, finalClType, oldOrderObj.getCashBankSO());
				
				/* B2. For New Bank A/c */// Add old amount value in old Ledger
				
				List <Ledger> ledgerNewBankLedger = ledgerDao.searchLedger(salesorder.getCashBankSO());
				BigDecimal NewBankLedgerAmt = ledgerNewBankLedger.get(0).getClosingTotalBalance();
				String clNewBankLedgerType = ledgerNewBankLedger.get(0).getClosingTotalType();
				
				if(clNewBankLedgerType.equalsIgnoreCase("Credit")){
				NewBankLedgerAmt = ZERO.subtract(NewBankLedgerAmt);						
				}
				
				BigDecimal finalNewBankLedgerAmt = NewBankLedgerAmt.add(salesorder.getCashAmountSO());
				
				if(finalNewBankLedgerAmt.signum() == -1){
				finalClType = "Credit";
				finalNewBankLedgerAmt = finalNewBankLedgerAmt.multiply(CONVERT);
				}else{
				finalClType = "Debit";						
				}
				
				ledgerDao.updateLedgerPartyBalance(finalNewBankLedgerAmt, finalClType, salesorder.getCashBankSO());
				}				
				}else if(oldOrderObj.getCashAmountSO().signum() ==  0 && salesorder.getCashAmountSO().signum() == 1 ){
				// on Update added Cheque option 
				List <Ledger> ledgerNewBankLedger = ledgerDao.searchLedger(salesorder.getCashBankSO());
				BigDecimal NewBankLedgerAmt = ledgerNewBankLedger.get(0).getClosingTotalBalance();
				String clNewBankLedgerType = ledgerNewBankLedger.get(0).getClosingTotalType();
				String finalClType="";
				if(clNewBankLedgerType.equalsIgnoreCase("Credit")){
				NewBankLedgerAmt = ZERO.subtract(NewBankLedgerAmt);						
				}
				
				BigDecimal finalNewBankLedgerAmt = NewBankLedgerAmt.add(salesorder.getCashAmountSO());
				
				if(finalNewBankLedgerAmt.signum() == -1){
				finalClType = "Credit";
				finalNewBankLedgerAmt = finalNewBankLedgerAmt.multiply(CONVERT);
				}else{
				finalClType = "Debit";						
				}
				
				ledgerDao.updateLedgerPartyBalance(finalNewBankLedgerAmt, finalClType, salesorder.getCashBankSO());	
				}
				
			/* Cash3.Journal Entry Update for Cash */
				List<Journal> jrnlPOSSalesCash = journalDao.getJournalUpdateSales("Sales Order Cash Receipt","SO"+oldOrderObj.getSalesOrderId());
				if(!jrnlPOSSalesCash.isEmpty()){
					//System.out.println("inside not empty of cash journal::::");
				if(oldOrderObj.getCashAmountSO().signum() == 1 && salesorder.getCashAmountSO().signum() == 0){
				//	System.out.println("inside journal cash delete::::");
				journalDao.deleteJournal(jrnlPOSSalesCash.get(0)); // Delete Entry 
				}else if(salesorder.getCashAmountSO().signum() == 1 && oldOrderObj.getCashAmountSO().signum() == 1){//Amount Update
					jrnlPOSSalesCash.get(0).setDebitAmount(salesorder.getCashAmountSO());
					jrnlPOSSalesCash.get(0).setCreditAmount(salesorder.getCashAmountSO());
					jrnlPOSSalesCash.get(0).setJournalDate(salesorder.getOrderDate());
					jrnlPOSSalesCash.get(0).setCreditAccountName(salesorder.getCustomerName());
					jrnlPOSSalesCash.get(0).setDebitAccountName(salesorder.getCashBankSO());
				journalDao.updateJournal(jrnlPOSSalesCash.get(0));
				}
				}else if(jrnlPOSSalesCash.isEmpty() && salesorder.getCashAmountSO().signum() == 1){
				System.out.println("Cash New entry::;");
				//********** new Journal Entry Cash Amount 
				jrnl=new Journal();
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Sales Order Cash Receipt");
				jrnl.setJournalDate(salesorder.getOrderDate());
				jrnl.setTransactionId("SO"+salesorder.getSalesOrderId());
				
				//Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(salesorder.getCashBankSO());
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
				jrnl.setDebitAccountName(salesorder.getCashBankSO());
				
				//Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName(salesorder.getCustomerName());
				
				jrnl.setDebitAmount(salesorder.getCashAmountSO());
				jrnl.setCreditAmount(salesorder.getCashAmountSO());
				jrnl.setNarration("Bill No"+salesorder.getSalesOrderId());
				journalDao.insertJournal(jrnl);
				}
				
//////////////////////////////////Receipt Mode - Cheque Ledgers & Journal Update //////////////////////////////////
			
				if(!oldOrderObj.getCustomerName().equals(salesorder.getCustomerName()))
				{
					//********************* Cheque Party balance Update : if - different Customer Names ************************//

					/** From Party 1 */
					List <Ledger> ledgerListChequeParty1 = ledgerDao.searchLedger(oldOrderObj.getCustomerName());
					String clTypeChequeParty1 = ledgerListChequeParty1.get(0).getClosingTotalType();
					BigDecimal oldClBalanceChequeParty1 = ledgerListChequeParty1.get(0).getClosingTotalBalance();
					String finalClTypeParty1 = "";
					String finalClTypeParty2 = "";

					/** To Party 2 */
					List <Ledger> ledgerListChequeParty2 = ledgerDao.searchLedger(salesorder.getCustomerName());
					String clTypeChequeParty2 = ledgerListChequeParty2.get(0).getClosingTotalType();
					BigDecimal oldClBalanceChequeParty2 = ledgerListChequeParty2.get(0).getClosingTotalBalance();
					
					if(clTypeChequeParty1.equalsIgnoreCase("Credit")){
						oldClBalanceChequeParty1 = ZERO.subtract(oldClBalanceChequeParty1);						
					}
					if(clTypeChequeParty2.equalsIgnoreCase("Credit")){
						oldClBalanceChequeParty2 = ZERO.subtract(oldClBalanceChequeParty2);						
					}

					BigDecimal finalClBalanceChequeParty1 = oldClBalanceChequeParty1.add(oldOrderObj.getChequeAmountSO());
					BigDecimal finalClBalanceChequeParty2 = oldClBalanceChequeParty2.subtract(salesorder.getChequeAmountSO());
									
					if(finalClBalanceChequeParty1.signum() == -1){
						finalClTypeParty1 = "Credit";
						finalClBalanceChequeParty1 = finalClBalanceChequeParty1.multiply(CONVERT);
					}else{
						finalClTypeParty1 = "Debit";						
					}

					if(finalClBalanceChequeParty2.signum() == -1){
						finalClTypeParty2 = "Credit";
						finalClBalanceChequeParty2 = finalClBalanceChequeParty2.multiply(CONVERT);
					}else{
						finalClTypeParty2 = "Debit";						
					}

					ledgerDao.updateLedgerPartyBalance(finalClBalanceChequeParty1, finalClTypeParty1, oldOrderObj.getCustomerName());
					ledgerDao.updateLedgerPartyBalance(finalClBalanceChequeParty2, finalClTypeParty2, salesorder.getCustomerName());
					}
				else if(!salesorder.getCustomerName().equals("Walk-in")){
				//********************* Cheque Party balance Update : if - Same Customer Names **************************
				
					List <Ledger> ledgerListChequeParty = ledgerDao.searchLedger(oldOrderObj.getCustomerName());
					BigDecimal oldClBalanceChequeParty = ledgerListChequeParty.get(0).getClosingTotalBalance();
					String clTypeChequeParty = ledgerListChequeParty.get(0).getClosingTotalType();
					String finalClType="";
					if(clTypeChequeParty.equalsIgnoreCase("Credit")){
						oldClBalanceChequeParty = ZERO.subtract(oldClBalanceChequeParty);						
					}
	
					BigDecimal dropClBalanceChequeParty = oldClBalanceChequeParty.add(oldOrderObj.getChequeAmountSO());
					BigDecimal finalClBalanceChequeParty  = dropClBalanceChequeParty.subtract(salesorder.getChequeAmountSO());
										
					if(finalClBalanceChequeParty.signum() == -1){
						finalClType = "Credit";
						finalClBalanceChequeParty = finalClBalanceChequeParty.multiply(CONVERT);
					}else{
							finalClType = "Debit";						
						}
					ledgerDao.updateLedgerPartyBalance(finalClBalanceChequeParty, finalClType, oldOrderObj.getCustomerName());					
				}	
				//////////////////////////////////// C2.Cheque Bank Ledger Update ////////////////////////////////////////////
				List <Ledger> ledgerListChequeLedger = ledgerDao.searchLedger(oldOrderObj.getChequeBankSO());
				
				if(!ledgerListChequeLedger.isEmpty()) 
				{ // if initially cheque selected
				
					BigDecimal oldClBalanceChequeLedger = ledgerListChequeLedger.get(0).getClosingTotalBalance();
					String clTypeChequeLedger = ledgerListChequeLedger.get(0).getClosingTotalType();
					
					if(clTypeChequeLedger.equalsIgnoreCase("Credit")){
						oldClBalanceChequeLedger = ZERO.subtract(oldClBalanceChequeLedger);						
					}
	
				if(oldOrderObj.getChequeBankSO().equals(salesorder.getChequeBankSO())){
				//********************************* If the Bank A/c is "Same".******************************************/
					BigDecimal dropClBalanceChequeLedger = oldClBalanceChequeLedger.subtract(oldOrderObj.getChequeAmountSO());
					BigDecimal finalClBalanceChequeLedger = dropClBalanceChequeLedger.add(salesorder.getChequeAmountSO());
					String finalClType="";
					if(finalClBalanceChequeLedger.signum() == -1){
						finalClType = "Credit";
						finalClBalanceChequeLedger = finalClBalanceChequeLedger.multiply(CONVERT);
					}
					else{
						finalClType = "Debit";						
					}
					ledgerDao.updateLedgerPartyBalance(finalClBalanceChequeLedger, finalClType, oldOrderObj.getChequeBankSO());					
				}else {					
					//************************ If the Bank A/c Name "Changes".******************************************/		
					/* B1. For old Bank A/c  */// Subtract old amount value in old Ledger
					String finalClType="";
					BigDecimal finalOldBankLedgerAmt = oldClBalanceChequeLedger.subtract(oldOrderObj.getChequeAmountSO());
					if(finalOldBankLedgerAmt.signum() == -1){
						finalClType = "Credit";
						finalOldBankLedgerAmt = finalOldBankLedgerAmt.multiply(CONVERT);
					}else{
						finalClType = "Debit";						
					}
					ledgerDao.updateLedgerPartyBalance(finalOldBankLedgerAmt, finalClType, oldOrderObj.getChequeBankSO());
					
					/* B2. For New Bank A/c */// Add old amount value in old Ledger
					
					List <Ledger> ledgerNewBankLedger = ledgerDao.searchLedger(salesorder.getChequeBankSO());
					BigDecimal NewBankLedgerAmt = ledgerNewBankLedger.get(0).getClosingTotalBalance();
					String clNewBankLedgerType = ledgerNewBankLedger.get(0).getClosingTotalType();
					
					if(clNewBankLedgerType.equalsIgnoreCase("Credit")){
						NewBankLedgerAmt = ZERO.subtract(NewBankLedgerAmt);						
					}
		
					BigDecimal finalNewBankLedgerAmt = NewBankLedgerAmt.add(salesorder.getChequeAmountSO());
							
					if(finalNewBankLedgerAmt.signum() == -1){
						finalClType = "Credit";
						finalNewBankLedgerAmt = finalNewBankLedgerAmt.multiply(CONVERT);
					}else{
						finalClType = "Debit";						
					}
			
					ledgerDao.updateLedgerPartyBalance(finalNewBankLedgerAmt, finalClType, salesorder.getChequeBankSO());
				}				
			}else if(oldOrderObj.getChequeAmountSO().signum() ==  0 && salesorder.getChequeAmountSO().signum() == 1 ){
				// on Update added Cheque option 
				List <Ledger> ledgerNewBankLedger = ledgerDao.searchLedger(salesorder.getChequeBankSO());
				BigDecimal NewBankLedgerAmt = ledgerNewBankLedger.get(0).getClosingTotalBalance();
				String clNewBankLedgerType = ledgerNewBankLedger.get(0).getClosingTotalType();
				String finalClType="";
				if(clNewBankLedgerType.equalsIgnoreCase("Credit")){
					NewBankLedgerAmt = ZERO.subtract(NewBankLedgerAmt);						
				}
				
				BigDecimal finalNewBankLedgerAmt = NewBankLedgerAmt.add(salesorder.getChequeAmountSO());
						
					if(finalNewBankLedgerAmt.signum() == -1){
						finalClType = "Credit";
						finalNewBankLedgerAmt = finalNewBankLedgerAmt.multiply(CONVERT);
					}else{
						finalClType = "Debit";						
					}
					
				ledgerDao.updateLedgerPartyBalance(finalNewBankLedgerAmt, finalClType, salesorder.getChequeBankSO());	
			}
			///////////////////////////////////// Journal Entries ////////////////////////////////////////////
			/* C3.Journal Entry Update for Cheque */
				List<Journal> jrnlPOSSalesCheque = journalDao.getJournalUpdateSales("Sales Order Cheque Receipt","SO"+oldOrderObj.getSalesOrderId());
				if(!jrnlPOSSalesCheque.isEmpty()){
					System.out.println("inside not empty of cheque journal::::");
				if(oldOrderObj.getChequeAmountSO().signum() == 1 && salesorder.getChequeAmountSO().signum() == 0){
					System.out.println("inside journal cheque delete::::");
				journalDao.deleteJournal(jrnlPOSSalesCheque.get(0)); // Delete Entry 
				}else if(salesorder.getChequeAmountSO().signum() == 1 && oldOrderObj.getChequeAmountSO().signum() == 1){
					jrnlPOSSalesCheque.get(0).setDebitAmount(salesorder.getChequeAmountSO());
					jrnlPOSSalesCheque.get(0).setCreditAmount(salesorder.getChequeAmountSO());
					jrnlPOSSalesCheque.get(0).setJournalDate(salesorder.getOrderDate());
					jrnlPOSSalesCheque.get(0).setCreditAccountName(salesorder.getCustomerName());
					jrnlPOSSalesCheque.get(0).setDebitAccountName(salesorder.getChequeBankSO());
				journalDao.updateJournal(jrnlPOSSalesCheque.get(0));
				}
				}else if(jrnlPOSSalesCheque.isEmpty() && salesorder.getChequeAmountSO().signum() == 1){
				//System.out.println("Card New entry::;");
				//********** new Journal Entry Card Amount 
				jrnl=new Journal();
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Sales Order Cheque Receipt");
				jrnl.setJournalDate(salesorder.getOrderDate());
				jrnl.setTransactionId("SO"+salesorder.getSalesOrderId());
				
				//Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(salesorder.getChequeBankSO());
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
				jrnl.setDebitAccountName(salesorder.getChequeBankSO());
				
				//Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName(salesorder.getCustomerName());
				
				jrnl.setDebitAmount(salesorder.getChequeAmountSO());
				jrnl.setCreditAmount(salesorder.getChequeAmountSO());
				jrnl.setNarration("Bill No"+salesorder.getSalesOrderId());
				journalDao.insertJournal(jrnl);
				}
							
				
//////////////////////////////////////Receipt Mode - Card Ledgers & Journal Update ///////////////////////////////////
						
					if(!oldOrderObj.getCustomerName().equals(salesorder.getCustomerName()) )
					{
					
					//********************* Card Party balance Update : if - different Customer Names **************************
					
					/** From Party 1 */
					List <Ledger> ledgerListCardParty1 = ledgerDao.searchLedger(oldOrderObj.getCustomerName());
					String clTypeCardParty1 = ledgerListCardParty1.get(0).getClosingTotalType();
					BigDecimal oldClBalanceCardParty1 = ledgerListCardParty1.get(0).getClosingTotalBalance();
					String finalClTypeParty1 = "";
					String finalClTypeParty2 = "";
					
					/** To Party 2 */
					List <Ledger> ledgerListCardParty2 = ledgerDao.searchLedger(salesorder.getCustomerName());
					String clTypeCardParty2 = ledgerListCardParty2.get(0).getClosingTotalType();
					BigDecimal oldClBalanceCardParty2 = ledgerListCardParty2.get(0).getClosingTotalBalance();
					
					if(clTypeCardParty1.equalsIgnoreCase("Credit")){
					oldClBalanceCardParty1 = ZERO.subtract(oldClBalanceCardParty1);						
					}
					if(clTypeCardParty2.equalsIgnoreCase("Credit")){
					oldClBalanceCardParty2 = ZERO.subtract(oldClBalanceCardParty2);						
					}
					
					BigDecimal finalClBalanceCardParty1 = oldClBalanceCardParty1.add(oldOrderObj.getCardAmountSO());
					BigDecimal finalClBalanceCardParty2 = oldClBalanceCardParty2.subtract(salesorder.getCardAmountSO());
							
					if(finalClBalanceCardParty1.signum() == -1){
					finalClTypeParty1 = "Credit";
					finalClBalanceCardParty1 = finalClBalanceCardParty1.multiply(CONVERT);
					}else{
					finalClTypeParty1 = "Debit";						
					}
					
					if(finalClBalanceCardParty2.signum() == -1){
					finalClTypeParty2 = "Credit";
					finalClBalanceCardParty2 = finalClBalanceCardParty2.multiply(CONVERT);
					}else{
					finalClTypeParty2 = "Debit";						
					}
					
					ledgerDao.updateLedgerPartyBalance(finalClBalanceCardParty1, finalClTypeParty1, oldOrderObj.getCustomerName());
					ledgerDao.updateLedgerPartyBalance(finalClBalanceCardParty2, finalClTypeParty2, salesorder.getCustomerName());
					}else if(!salesorder.getCustomerName().equals("Walk-in")){
					//********************* Card Party balance Update : if - Same Customer Names **************************
					
					List <Ledger> ledgerListCardParty = ledgerDao.searchLedger(oldOrderObj.getCustomerName());
					BigDecimal oldClBalanceCardParty = ledgerListCardParty.get(0).getClosingTotalBalance();
					String clTypeCardParty = ledgerListCardParty.get(0).getClosingTotalType();
					
					if(clTypeCardParty.equalsIgnoreCase("Credit")){
					oldClBalanceCardParty = ZERO.subtract(oldClBalanceCardParty);						
					}
					
					BigDecimal dropClBalanceCardParty = oldClBalanceCardParty.add(oldOrderObj.getCardAmountSO());
					BigDecimal finalClBalanceCardParty  = dropClBalanceCardParty.subtract(salesorder.getCardAmountSO());
					String finalClType="";
					
					if(finalClBalanceCardParty.signum() == -1){
					finalClType = "Credit";
					finalClBalanceCardParty = finalClBalanceCardParty.multiply(CONVERT);
					}else{
					finalClType = "Debit";						
					}
					ledgerDao.updateLedgerPartyBalance(finalClBalanceCardParty, finalClType, oldOrderObj.getCustomerName());
					}
					//////////////////////////////* Card2.Card Bank Ledger Update *///////////////////////////////////////
					List <Ledger> ledgerListCardLedger = ledgerDao.searchLedger(oldOrderObj.getCardBankSO());
					
					if(!ledgerListCardLedger.isEmpty())
					{ //initially card selected
					BigDecimal oldClBalanceCardLedger = ledgerListCardLedger.get(0).getClosingTotalBalance();
					String clTypeCardLedger = ledgerListCardLedger.get(0).getClosingTotalType();
					
					if(clTypeCardLedger.equalsIgnoreCase("Credit")) {
					oldClBalanceCardLedger = ZERO.subtract(oldClBalanceCardLedger);
					}
					
					if(oldOrderObj.getCardBankSO().equals(salesorder.getCardBankSO())) 
					{//************************************** If the Bank A/c is Same. *******************************/
					BigDecimal dropClBalanceCardLedger = oldClBalanceCardLedger.subtract(oldOrderObj.getCardAmountSO());
					BigDecimal finalClBalanceCardLedger  = dropClBalanceCardLedger.add(salesorder.getCardAmountSO());
					String finalClType="";			
					if(finalClBalanceCardLedger.signum() == -1){
						finalClType = "Credit";
						finalClBalanceCardLedger = finalClBalanceCardLedger.multiply(CONVERT);
					}else{
						finalClType = "Debit";						
					}
					ledgerDao.updateLedgerPartyBalance(finalClBalanceCardLedger, finalClType, oldOrderObj.getCardBankSO());					
					
					}else{	//************************** B0. If the Bank A/c Name changes for Card .*********************************/
					/*B1. For old Bank A/c for Card   */ // Subtract old card amount in old ledger
						String finalClType="";			
					BigDecimal finalOldBankLedgerCardAmt = oldClBalanceCardLedger.subtract(oldOrderObj.getCardAmountSO());
					if(finalOldBankLedgerCardAmt.signum() == -1){
						finalClType = "Credit";
						finalOldBankLedgerCardAmt = finalOldBankLedgerCardAmt.multiply(CONVERT);
					}else{
						finalClType = "Debit";						
					}
					ledgerDao.updateLedgerPartyBalance(finalOldBankLedgerCardAmt, finalClType, oldOrderObj.getCardBankSO());
						
					/* B2. For New Bank A/c for Card */// Add new amount value in ledger
					List <Ledger> ledgerNewBankCardLedger = ledgerDao.searchLedger(salesorder.getCardBankSO());
					BigDecimal NewBankLedgerCardAmt = ledgerNewBankCardLedger.get(0).getClosingTotalBalance();
					String clNewBankLedgerCardType = ledgerNewBankCardLedger.get(0).getClosingTotalType();
					
					if(clNewBankLedgerCardType.equalsIgnoreCase("Credit")){
						NewBankLedgerCardAmt = ZERO.subtract(NewBankLedgerCardAmt);						
					}
					
					BigDecimal finalNewBankLedgerAmt = NewBankLedgerCardAmt.add(salesorder.getCardAmountSO());
							
					if(finalNewBankLedgerAmt.signum() == -1){
						finalClType = "Credit";
						finalNewBankLedgerAmt = finalNewBankLedgerAmt.multiply(CONVERT);
					}else{
						finalClType = "Debit";						
					}
					ledgerDao.updateLedgerPartyBalance(finalNewBankLedgerAmt, finalClType, salesorder.getCardBankSO());					
					}				
					
					}else if(oldOrderObj.getCardAmountSO().signum() == 0 && salesorder.getCardAmountSO().signum() == 1 ) {
					// On update added card option
					List <Ledger> ledgerNewBankCardLedger = ledgerDao.searchLedger(salesorder.getCardBankSO());
					BigDecimal NewBankLedgerCardAmt = ledgerNewBankCardLedger.get(0).getClosingTotalBalance();
					String clNewBankLedgerCardType = ledgerNewBankCardLedger.get(0).getClosingTotalType();
					String finalClType="";
					if(clNewBankLedgerCardType.equalsIgnoreCase("Credit")){
					NewBankLedgerCardAmt = ZERO.subtract(NewBankLedgerCardAmt);						
					}
					
					BigDecimal finalNewBankLedgerAmt = NewBankLedgerCardAmt.add(salesorder.getCardAmountSO());
					
					if(finalNewBankLedgerAmt.signum() == -1){
					finalClType = "Credit";
					finalNewBankLedgerAmt = finalNewBankLedgerAmt.multiply(CONVERT);
					}else{
					finalClType = "Debit";						
					}
					ledgerDao.updateLedgerPartyBalance(finalNewBankLedgerAmt, finalClType, salesorder.getCardBankSO());	
					}
					/* Card3.Journal Entry Update for Card */
					List<Journal> jrnlPOSSalesCard = journalDao.getJournalUpdateSales("Sales Order Card Receipt","SO"+oldOrderObj.getSalesOrderId());
					if(!jrnlPOSSalesCard.isEmpty()){
					if(oldOrderObj.getCardAmountSO().signum() == 1 && salesorder.getCardAmountSO().signum() == 0){
					journalDao.deleteJournal(jrnlPOSSalesCard.get(0)); // Delete Entry 
					}else if(salesorder.getCardAmountSO().signum() == 1 && oldOrderObj.getCardAmountSO().signum() == 1){
					jrnlPOSSalesCard.get(0).setDebitAmount(salesorder.getCardAmountSO());
					jrnlPOSSalesCard.get(0).setCreditAmount(salesorder.getCardAmountSO());
					jrnlPOSSalesCard.get(0).setJournalDate(salesorder.getOrderDate());
					jrnlPOSSalesCard.get(0).setCreditAccountName(salesorder.getCustomerName());
					jrnlPOSSalesCard.get(0).setDebitAccountName(salesorder.getCardBankSO());
					journalDao.updateJournal(jrnlPOSSalesCard.get(0));
					}
					}else if(jrnlPOSSalesCard.isEmpty() && salesorder.getCardAmountSO().signum() == 1){
					//System.out.println("Card New entry::;");
					//********** new Journal Entry Card Amount 
					jrnl=new Journal();
					jrnl.setJournalNO(getJournalNumber(jrnl));
					jrnl.setJournalType("Sales Order Card Receipt");
					jrnl.setJournalDate(salesorder.getOrderDate());
					jrnl.setTransactionId("SO"+salesorder.getSalesOrderId());
					
					
					//Set Account group code as Debit code
					ledgerGroupCode = ledgerDao.getLedgerAccountCode(salesorder.getCardBankSO());
					jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
					jrnl.setDebitAccountName(salesorder.getCardBankSO());
					
					//Set Account group code as Credit code
					ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
					jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					jrnl.setCreditAccountName(salesorder.getCustomerName());
					
					jrnl.setDebitAmount(salesorder.getCardAmountSO());
					jrnl.setCreditAmount(salesorder.getCardAmountSO());
					jrnl.setNarration("Bill No"+salesorder.getSalesOrderId());
					journalDao.insertJournal(jrnl);
					}
					///////////////////////////////////// Receipt Mode - Voucher Ledgers & Journal Update ///////////////////////////////////
										
					/* Voucher1. party Balance Update */
					if(!oldOrderObj.getCustomerName().equals(salesorder.getCustomerName()) ){
					
					//********************* Voucher Party balance Update : if - different Customer Names ************************//
					
					/** From Party 1 */
					List <Ledger> ledgerListVoucherParty1 = ledgerDao.searchLedger(oldOrderObj.getCustomerName());
					String clTypeVoucherParty1 = ledgerListVoucherParty1.get(0).getClosingTotalType();
					BigDecimal oldClBalanceVoucherParty1 = ledgerListVoucherParty1.get(0).getClosingTotalBalance();
					String finalClTypeParty1 = "";
					String finalClTypeParty2 = "";
					
					/** To Party 2 */
					List <Ledger> ledgerListVoucherParty2 = ledgerDao.searchLedger(salesorder.getCustomerName());
					String clTypeVoucherParty2 = ledgerListVoucherParty2.get(0).getClosingTotalType();
					BigDecimal oldClBalanceVoucherParty2 = ledgerListVoucherParty2.get(0).getClosingTotalBalance();
					
					if(clTypeVoucherParty1.equalsIgnoreCase("Credit")){
						oldClBalanceVoucherParty1 = ZERO.subtract(oldClBalanceVoucherParty1);						
					}
					if(clTypeVoucherParty2.equalsIgnoreCase("Credit")){
						oldClBalanceVoucherParty2 = ZERO.subtract(oldClBalanceVoucherParty2);						
					}
					
					BigDecimal finalClBalanceVoucherdParty1 = oldClBalanceVoucherParty1.add(oldOrderObj.getVoucherAmountSO());
					BigDecimal finalClBalanceVoucherParty2 = oldClBalanceVoucherParty2.subtract(salesorder.getVoucherAmountSO());
									
					if(finalClBalanceVoucherdParty1.signum() == -1){
						finalClTypeParty1 = "Credit";
						finalClBalanceVoucherdParty1 = finalClBalanceVoucherdParty1.multiply(CONVERT);
					}else{
						finalClTypeParty1 = "Debit";						
					}
					
					if(finalClBalanceVoucherParty2.signum() == -1){
						finalClTypeParty2 = "Credit";
						finalClBalanceVoucherParty2 = finalClBalanceVoucherParty2.multiply(CONVERT);
					}else{
						finalClTypeParty2 = "Debit";						
					}
					
					ledgerDao.updateLedgerPartyBalance(finalClBalanceVoucherdParty1, finalClTypeParty1, oldOrderObj.getCustomerName());
					ledgerDao.updateLedgerPartyBalance(finalClBalanceVoucherParty2, finalClTypeParty2,salesorder.getCustomerName());
					}else if(!salesorder.getCustomerName().equals("Walk-in")) {
					//********************* Voucher Party balance Update : if - Same Customer Names **************************//
					
					List <Ledger> ledgerListVoucherParty = ledgerDao.searchLedger(oldOrderObj.getCustomerName());
					BigDecimal oldClBalanceVoucherParty = ledgerListVoucherParty.get(0).getClosingTotalBalance();
					String clTypeVoucherParty = ledgerListVoucherParty.get(0).getClosingTotalType();
					String finalClType="";
					if(clTypeVoucherParty.equalsIgnoreCase("Credit")) {
						oldClBalanceVoucherParty = ZERO.subtract(oldClBalanceVoucherParty);
					}
					
					BigDecimal dropClBalanceVoucherParty = oldClBalanceVoucherParty.add(oldOrderObj.getVoucherAmountSO());
					BigDecimal finalClBalanceVoucherParty  = dropClBalanceVoucherParty.subtract(salesorder.getVoucherAmountSO());
					
					if(finalClBalanceVoucherParty.signum() == -1){
						finalClType = "Credit";
						finalClBalanceVoucherParty = finalClBalanceVoucherParty.multiply(CONVERT);
					}
					else{
						finalClType = "Debit";						
					}
						
					ledgerDao.updateLedgerPartyBalance(finalClBalanceVoucherParty, finalClType, oldOrderObj.getCustomerName());					
					}
					
					////////////////////////////////////////* Voucher2. Voucher List Ledger Update *//////////////////////////////////
					List <Ledger> ledgerListVoucherLedger = ledgerDao.searchLedger(oldOrderObj.getVoucherListSO());
					
					if(!ledgerListVoucherLedger.isEmpty())
					{ //Initially voucher selected
						BigDecimal oldClBalanceVoucherLedger = ledgerListVoucherLedger.get(0).getClosingTotalBalance();
						String clTypeVoucherLedger = ledgerListVoucherLedger.get(0).getClosingTotalType();
					
						if(clTypeVoucherLedger.equalsIgnoreCase("Credit")) {
							oldClBalanceVoucherLedger = ZERO.subtract(oldClBalanceVoucherLedger);
						}
						
						if(oldOrderObj.getVoucherListSO().equals(salesorder.getVoucherListSO()) )
						{	 //************************************ If the Bank Names are same	********************************//
							BigDecimal dropClBalanceVoucherLedger = oldClBalanceVoucherLedger.subtract(oldOrderObj.getVoucherAmountSO());
							BigDecimal finalClBalanceVoucherLedger  = dropClBalanceVoucherLedger.add(salesorder.getVoucherAmountSO());
							String finalClType="";
							if(finalClBalanceVoucherLedger.signum() == -1){
								finalClType = "Credit";
								finalClBalanceVoucherLedger = finalClBalanceVoucherLedger.multiply(CONVERT);
							}else{
								finalClType = "Debit";						
							}
							ledgerDao.updateLedgerPartyBalance(finalClBalanceVoucherLedger, finalClType, oldOrderObj.getVoucherListSO());
						}else {	/* ************************************B0. If the Bank A/c Name changes for Voucher .***************/
							/* B1. For old Bank A/c for Voucher */ // Subtract Old Voucher Amount in ledger
							String finalClType="";					
							BigDecimal finalOldBankLedgerVoucherAmt = oldClBalanceVoucherLedger.subtract(oldOrderObj.getVoucherAmountSO());
							if(finalOldBankLedgerVoucherAmt.signum() == -1){
								finalClType = "Credit";
								finalOldBankLedgerVoucherAmt = finalOldBankLedgerVoucherAmt.multiply(CONVERT);
							}else{
								finalClType = "Debit";						
							}
							ledgerDao.updateLedgerPartyBalance(finalOldBankLedgerVoucherAmt, finalClType, oldOrderObj.getVoucherListSO());
								
							/* B2. For New Bank A/c for Voucher */ //Add new Voucher amount in ledger
							List <Ledger> ledgerNewBankVoucherLedger = ledgerDao.searchLedger(salesorder.getVoucherListSO());
							BigDecimal NewBankLedgerVoucherAmt = ledgerNewBankVoucherLedger.get(0).getClosingTotalBalance();
							String clNewBankLedgerVoucherType = ledgerNewBankVoucherLedger.get(0).getClosingTotalType();
							
							if(clNewBankLedgerVoucherType.equalsIgnoreCase("Credit")){
								NewBankLedgerVoucherAmt = ZERO.subtract(NewBankLedgerVoucherAmt);						
							}
							BigDecimal finalNewBankLedgerVoucherAmt = NewBankLedgerVoucherAmt.add(salesorder.getVoucherAmountSO());
									
							if(finalNewBankLedgerVoucherAmt.signum() == -1){
								finalClType = "Credit";
								finalNewBankLedgerVoucherAmt = finalNewBankLedgerVoucherAmt.multiply(CONVERT);
							}else{
								finalClType = "Debit";						
							}
							ledgerDao.updateLedgerPartyBalance(finalNewBankLedgerVoucherAmt, finalClType, salesorder.getVoucherListSO());
						}
					}else if(oldOrderObj.getVoucherAmountSO().signum() == 0 && salesorder.getVoucherAmountSO().signum() == 1 ){
						// On Update added Voucher Option
						List <Ledger> ledgerNewBankVoucherLedger = ledgerDao.searchLedger(salesorder.getVoucherListSO());
						BigDecimal NewBankLedgerVoucherAmt = ledgerNewBankVoucherLedger.get(0).getClosingTotalBalance();
						String clNewBankLedgerVoucherType = ledgerNewBankVoucherLedger.get(0).getClosingTotalType();
						String finalClType="";
						if(clNewBankLedgerVoucherType.equalsIgnoreCase("Credit")){
							NewBankLedgerVoucherAmt = ZERO.subtract(NewBankLedgerVoucherAmt);						
						}
						BigDecimal finalNewBankLedgerVoucherAmt = NewBankLedgerVoucherAmt.add(salesorder.getVoucherAmountSO());
								
						if(finalNewBankLedgerVoucherAmt.signum() == -1){
							finalClType = "Credit";
							finalNewBankLedgerVoucherAmt = finalNewBankLedgerVoucherAmt.multiply(CONVERT);
						}else{
							finalClType = "Debit";						
						}
						ledgerDao.updateLedgerPartyBalance(finalNewBankLedgerVoucherAmt, finalClType, salesorder.getVoucherListSO());
					}
						
					
					/* C3.Journal Entry Update for Voucher */
					List<Journal> jrnlPOSSalesV = journalDao.getJournalUpdateSales("Sales Order Voucher Receipt","SO"+oldOrderObj.getSalesOrderId());
					if(!jrnlPOSSalesV.isEmpty()){	
					if(oldOrderObj.getVoucherAmountSO().signum() == 1 && salesorder.getVoucherAmountSO().signum() == 0){
						journalDao.deleteJournal(jrnlPOSSalesV.get(0)); // Delete Entry
					}else if(salesorder.getVoucherAmountSO().signum() == 1){
					jrnlPOSSalesV.get(0).setDebitAmount(salesorder.getVoucherAmountSO());
					jrnlPOSSalesV.get(0).setCreditAmount(salesorder.getVoucherAmountSO());
					jrnlPOSSalesV.get(0).setJournalDate(salesorder.getOrderDate());
					jrnlPOSSalesV.get(0).setCreditAccountName(salesorder.getCustomerName());
					jrnlPOSSalesV.get(0).setDebitAccountName(salesorder.getVoucherListSO());
					journalDao.updateJournal(jrnlPOSSalesV.get(0));
					}
					}else if(jrnlPOSSalesV.isEmpty() && salesorder.getVoucherAmountSO().signum()==1){
					
					//********** new Journal Entry Voucher Amount 
					 jrnl=new Journal();
					 jrnl.setJournalNO(getJournalNumber(jrnl));
					 jrnl.setJournalType("Sales Order Voucher Receipt");
					 jrnl.setJournalDate(salesorder.getOrderDate());
					 jrnl.setTransactionId("SO"+salesorder.getSalesOrderId());
					 
					//Set Account group code as Debit code
					 ledgerGroupCode = ledgerDao.getLedgerAccountCode(salesorder.getVoucherListSO());
					 jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
					 jrnl.setDebitAccountName(salesorder.getVoucherListSO());
					 
					 
					 //Set Account group code as Credit code
					 ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
					 jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					 jrnl.setCreditAccountName(salesorder.getCustomerName());
					   
					 jrnl.setDebitAmount(salesorder.getVoucherAmountSO());
					 jrnl.setCreditAmount(salesorder.getVoucherAmountSO());
					 jrnl.setNarration("Bill No"+salesorder.getSalesOrderId());
					 journalDao.insertJournal(jrnl);     
					}		
				
				salesorderDao.updateSalesOrder(salesorder);	//update Sales order	
				
				/*  (THIS CODE IS COMMENTED BEACUSE OF NEW FUNCTIONALITY HAS BEEN ADDED THAT IS MULITPLE RECEIPT MODE )32/10/12
				if(old_partyName.equals(partyName) && old_advanceAmount.signum() != 0.0)
				{
					/** Party Balance Update 
					List<Ledger> partyLedger = ledgerDao.searchLedger(partyName);
					String clTypeParty = partyLedger.get(0).getClosingTotalType();
					BigDecimal oldClBalanceParty = partyLedger.get(0).getClosingTotalBalance();
				
					if(clTypeParty.equalsIgnoreCase("Credit")){
						oldClBalanceParty = ZERO.subtract(oldClBalanceParty);						
					}		
					
					BigDecimal ClBalanceParty = oldClBalanceParty.add(old_advanceAmount); 
					finalClBalance = ClBalanceParty.subtract(closingAmount);
														
					if(finalClBalance.signum() == -1){
						closingTyp = "Credit";
						finalClBalance = finalClBalance.multiply(CONVERT);
					}else{
						closingTyp = "Debit";						
					}
					
					ledgerDao.updateLedgerPartyBalance(finalClBalance, closingTyp, partyName);	
					
					
					//Update Journal Entry
					List<Journal> jrnlList = journalDao.getJournalUpdateSales("SalesOrder Receipt","SO"+salesorder.getSalesOrderId());
					
					if(!jrnlList.isEmpty() ){
						
						jrnlList.get(0).setDebitAmount(closingAmount);
						jrnlList.get(0).setCreditAmount(closingAmount);
						jrnlList.get(0).setJournalDate(salesorder.getOrderDate());
						journalDao.updateJournal(jrnlList.get(0));						
					}
				}
				
				if(!old_partyName.equals(partyName) && old_advanceAmount.signum() != 0.0){
					/** From Party 1
					List <Ledger> ledgerListParty1 = ledgerDao.searchLedger(old_partyName);
					String clTypeParty1 = ledgerListParty1.get(0).getClosingTotalType();
					BigDecimal oldClBalanceParty1 = ledgerListParty1.get(0).getClosingTotalBalance();
					String finalClTypeParty1 = "";
					String finalClTypeParty2 = "";
					
					/** To Party 2 
					List <Ledger> ledgerListParty2 = ledgerDao.searchLedger(partyName);
					String clTypeParty2 = ledgerListParty2.get(0).getClosingTotalType();
					BigDecimal oldClBalanceParty2 = ledgerListParty2.get(0).getClosingTotalBalance();

					if(clTypeParty1.equalsIgnoreCase("Credit")) {
						oldClBalanceParty1 = ZERO.subtract(oldClBalanceParty1);						
					}					
					if(clTypeParty2.equalsIgnoreCase("Credit")) {
						oldClBalanceParty2 = ZERO.subtract(oldClBalanceParty2);						
					}
										
					
					BigDecimal finalClBalanceParty1 = oldClBalanceParty1.add(old_advanceAmount);
					BigDecimal finalClBalanceParty2  = oldClBalanceParty2.subtract(closingAmount);
									
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
					
					ledgerDao.updateLedgerPartyBalance(finalClBalanceParty1, finalClTypeParty1, old_partyName);
					ledgerDao.updateLedgerPartyBalance(finalClBalanceParty2, finalClTypeParty2, partyName);
					
					//Update Journal Entry
					List<Journal> jrnlList = journalDao.getJournalUpdateSales("SalesOrder Receipt","SO"+salesorder.getSalesOrderId());
					
					if(!jrnlList.isEmpty() ){
						
						jrnlList.get(0).setDebitAmount(closingAmount);
						jrnlList.get(0).setCreditAmount(closingAmount);
						jrnlList.get(0).setCreditAccountName(salesorder.getCustomerName());
						jrnlList.get(0).setJournalDate(salesorder.getOrderDate());
						journalDao.updateJournal(jrnlList.get(0));						
					}	
				}
				
				/** Cash Ledger Update 
				List<Ledger> CashLedger = ledgerDao.searchLedger("Cash Account");
				String clTypeCash = CashLedger.get(0).getClosingTotalType();
				BigDecimal oldClBalanceCash = CashLedger.get(0).getClosingTotalBalance();
			
				if(clTypeCash.equalsIgnoreCase("Credit")){
					oldClBalanceCash = ZERO.subtract(oldClBalanceCash);						
				}	 
				
				finalClBalance = oldClBalanceCash.subtract(old_advanceAmount);
				BigDecimal ClBalanceCash = finalClBalance.add(closingAmount);
													
				if(ClBalanceCash.signum() == -1){
					closingTyp = "Credit";
					ClBalanceCash = ClBalanceCash.multiply(CONVERT);
				}else{
					closingTyp = "Debit";						
				}	
				
				if(oldOrderObj.getAdvance().signum() == 0 && closingAmount.signum() != 0)
				{
					
					List<Journal> jrnlList = journalDao.getJournalUpdateSales("SalesOrder Receipt","SO"+salesorder.getSalesOrderId());
					
					if(!jrnlList.isEmpty() ){
						
						jrnlList.get(0).setDebitAmount(closingAmount);
						jrnlList.get(0).setCreditAmount(closingAmount);
						jrnlList.get(0).setJournalDate(salesorder.getOrderDate());
						jrnlList.get(0).setCreditAccountName(salesorder.getCustomerName());
						journalDao.updateJournal(jrnlList.get(0));						
					}
					else{
			           	jrnl = new Journal();		           	
						jrnl.setJournalNO(getJournalNumber(jrnl));  
						jrnl.setTransactionId("SO"+salesorder.getSalesOrderId());
						jrnl.setJournalType("SalesOrder Receipt");
					    jrnl.setJournalDate(salesorder.getOrderDate());
						jrnl.setDebitAccountName("Cash Account");
						jrnl.setCreditAccountName(partyName);
						jrnl.setDebitAmount(closingAmount);
						jrnl.setCreditAmount(closingAmount);
						jrnl.setNarration("Order Number " +salesorder.getSalesOrderId());				
						journalDao.insertJournal((Journal) jrnl);	
					}
		           	
		            //update party balance
		                     	
					List<Ledger> customerList = ledgerDao.searchLedger(partyName);				
					clBal = customerList.get(0).getClosingTotalBalance();	
					clType = customerList.get(0).getClosingTotalType();		 			 				 
				 	
					if(clType.equals("Credit")){
				 		ledgerDao.updatePartyBalance(closingAmount, closingTyp, partyName);		 		
				 	}
				 	else if(clType.equals("Debit")){	
				 		if(clBal.compareTo(closingAmount) == 1){
				 			ledgerDao.updateCreditPartyBalance(closingAmount, closingType, partyName);
				 		}
				 		else{
				 			ledgerDao.updateCrPartyBalance(closingAmount, closingTyp, partyName);
				 		}
				 	}	//update Ledger list End			
					
					//Update Cash Book
					String ledgername="Cash Account";
					List <Ledger> ledgerList = ledgerDao.searchLedger(ledgername);		
					clBal = ledgerList.get(0).getClosingTotalBalance();	
					clType = ledgerList.get(0).getClosingTotalType();					 				 
				 	
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
				 	}// Update Cash book end
					
				 }
				else{
					//Update Journal Entry
					List<Journal> jrnlList = journalDao.getJournalUpdateSales("SalesOrder Receipt","SO"+salesorder.getSalesOrderId());
					
					if(!jrnlList.isEmpty() ){
						
						jrnlList.get(0).setDebitAmount(closingAmount);
						jrnlList.get(0).setCreditAmount(closingAmount);
						jrnlList.get(0).setJournalDate(salesorder.getOrderDate());
						jrnlList.get(0).setCreditAccountName(salesorder.getCustomerName());
						journalDao.updateJournal(jrnlList.get(0));						
					}					
				} 
				
				ledgerDao.updateLedgerPartyBalance(ClBalanceCash, closingTyp, "Cash Account");			
				
				*/
				
				/**
				if(salesorder.getOrderStatus().equals("Cancelled") && closingAmount.signum() != 0){
    	           	
					jrnl = new Journal();			
					jrnl.setJournalNO(getJournalNumber(jrnl)); 
					jrnl.setJournalType("Payment");
					jrnl.setJournalDate(salesorder.getOrderDate()); 
					jrnl.setDebitAccountName(salesorder.getCustomerName());
					jrnl.setCreditAccountName("Cash Account");
					jrnl.setDebitAmount(closingAmount);
					jrnl.setCreditAmount(closingAmount);
					jrnl.setNarration("Order Number " +salesorder.getSalesOrderId());				
					journalDao.insertJournal((Journal) jrnl);	
					
					 //update party balance				
	             	
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
					
					//Update Cash Book
					String ledgername="Cash Account";
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
				}**/
					
				return new ModelAndView("redirect:salesorder.htm");			
			}				
			
	
			//Canceling request mapping				
			@RequestMapping(value="/formsalesorder.htm",method=RequestMethod.POST, params="cancel") 
			public String cancelForm()
			{
				return "redirect:salesorder.htm";
			}
			
						//adding purchae exchange for sale order 20-12-12
						
						@RequestMapping(value = "/PESalesOrderPOPUP.htm", method = RequestMethod.POST)
						public @ResponseBody String exchangePurchase(@ModelAttribute("purchase") Purchase purchase, BindingResult result,SessionStatus status, Model model) {
							
							model.addAttribute("suppliername", ledgerDao.listLedgerName());
							
							BigDecimal clBal;
							BigDecimal itemgrosswt;
							BigDecimal itemnetwt;
							Integer noqty;
							Integer ppq;	
							String ledgername;
							String clType;		 	
							
							ItemMaster itemDetails = new ItemMaster();	
							
							String closingType = "Debit";
							String closingTyp = "Credit";	
							String itemcd = purchase.getItemCode();
							String partyName = purchase.getSupplierName();
						    BigDecimal closingAmount = purchase.getTotalAmount();	
						    String ptype = purchase.getPurchaseType();
						    BigDecimal grosswt = purchase.getGrossWeight();
						    Integer nos = purchase.getNumberOfPieces();	    
						    String payment_mode = purchase.getPaymentMode();	    
							
							
							// Insert Record in Purchase
						    String INVOICE_NO = generatePurchaseInvoiceNO(purchase);
							purchase.setPurchseInvoice(INVOICE_NO);
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
								    }
								    else
								    {
								    	noqty = noqty - nos;			   
										itemgrosswt = itemgrosswt.subtract(grosswt);
										itemnetwt = itemnetwt.subtract(grosswt);
								    }		    
									
									//tp = ppq * noqty;
									itemDetails.setGrossWeight(itemgrosswt);
									itemDetails.setNetWeight(itemnetwt);
									itemDetails.setQty(noqty);
									itemDetails.setPiecesPerQty(ppq);
									itemDetails.setTotalGrossWeight(itemgrosswt);			
									itemmasterDao.updateItemMaster(itemDetails);//Update Item Master
								}				
							}	
							
							if(ptype.equals("Purchase"))
							{			
								if (partyName.equals("Walk-in"))
								{
									//Auto Journal entry for purchase
									jrnl = new Journal();			
									jrnl.setJournalNO(getJournalNumber(jrnl));
									jrnl.setJournalType("Purchase");
								    jrnl.setJournalDate(purchase.getPurchaseDate());
								    
								  //Set Account group code as Credit code
									ledgerGroupCode = ledgerDao.getLedgerAccountCode("Purchase Account");
									jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
									jrnl.setDebitAccountName("Purchase Account");
									
									jrnl.setTransactionId("P"+purchase.getPurchaseId().toString());
									
									//Set Account group code as Credit code
									ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");
									jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
									jrnl.setCreditAccountName("Cash Account");
									
									jrnl.setDebitAmount(closingAmount);
									jrnl.setCreditAmount(closingAmount);
									jrnl.setNarration("Bill No " + purchase.getPurchaseId());				
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
								}
								else{//For Regular Customer
									
										if(payment_mode.equals("Cash"))
										{						
											//Auto Journal Entry for Regular customer				
											jrnl = new Journal();			
											jrnl.setJournalNO(getJournalNumber(jrnl));
											jrnl.setJournalType("Purchase");
										    jrnl.setJournalDate(purchase.getPurchaseDate());
										    
										  //Set Account group code as Debit code
											ledgerGroupCode = ledgerDao.getLedgerAccountCode("Purchase Account");
											jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
											jrnl.setDebitAccountName("Purchase Account");
											
											jrnl.setTransactionId("P"+purchase.getPurchaseId().toString());
											
											//Set Account group code as Credit code
											ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
											jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
											jrnl.setCreditAccountName(partyName);
											
											jrnl.setDebitAmount(closingAmount);
											jrnl.setCreditAmount(closingAmount);
											jrnl.setNarration("Bill No " + purchase.getPurchaseId());				
											journalDao.insertJournal((Journal) jrnl);	
								
											//Auto Journal Entry for Payment			
											jrnl = new Journal();			
											jrnl.setJournalNO(getJournalNumber(jrnl));
											jrnl.setJournalType("Payment");
										    jrnl.setJournalDate(purchase.getPurchaseDate());
											jrnl.setTransactionId("P"+purchase.getPurchaseId().toString());
											
											//Set Account group code as Debit code
											ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
											jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
											jrnl.setDebitAccountName(partyName);
											
											//Set Account group code as Credit code
											ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");
											jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
											jrnl.setCreditAccountName("Cash Account");
											
											jrnl.setDebitAmount(closingAmount);
											jrnl.setCreditAmount(closingAmount);
											jrnl.setNarration("Bill No " + purchase.getPurchaseId());				
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
											
											//Auto Journal Entry for Regular customer				
											jrnl = new Journal();			
											jrnl.setJournalNO(getJournalNumber(jrnl));
											jrnl.setJournalType("Purchase");
										    jrnl.setJournalDate(purchase.getPurchaseDate());
										    
										    //Set Account group code as Debit code
											ledgerGroupCode = ledgerDao.getLedgerAccountCode("Purchase Account");
											jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
											jrnl.setDebitAccountName("Purchase Account");
											
											jrnl.setTransactionId("P"+purchase.getPurchaseId().toString());
											
											//Set Account group code as Credit code
											ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
											jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
											jrnl.setCreditAccountName(partyName);
											
											jrnl.setDebitAmount(closingAmount);
											jrnl.setCreditAmount(closingAmount);
											jrnl.setNarration("Bill No " + purchase.getPurchaseId());				
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
						
							return INVOICE_NO;
						}

}

