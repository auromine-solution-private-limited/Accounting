package com.jewellery.web;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.jewellery.dao.AppConfigDao;
import com.jewellery.dao.CompanyInfoDao;
import com.jewellery.dao.JournalDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.dao.POSCategoryDao;
import com.jewellery.dao.POSItemDao;
import com.jewellery.dao.POSSalesDao;
/*import com.jewellery.dao.POSStockDao;*/
import com.jewellery.dao.PostagItemDao;
import com.jewellery.dao.UserloginDao;

import com.jewellery.entity.CompanyInfo;
import com.jewellery.entity.ConfigDetailList;
import com.jewellery.entity.Journal;
import com.jewellery.entity.Ledger;
import com.jewellery.entity.POSSales;
import com.jewellery.entity.POSSalesItem;
import com.jewellery.entity.PSales;
import com.jewellery.entity.PostagItem;
import com.jewellery.print.InvoiceBillFormat;
import com.jewellery.printservice.InvoiceTextFormatPrint;
import com.jewellery.util.JournalCode;
import com.jewellery.validator.POSSalesValidator;


@Controller
public class FormPOSSalesController extends JournalCode {
	
	@Autowired
	private POSSalesDao posSalesDao;
	@Autowired
	private LedgerDao ledgerDao;
	@Autowired
	private PostagItemDao posTagItemDao;
	@Autowired POSItemDao positemDao;
	@Autowired
	private UserloginDao userloginDao;
	private Journal jrnl;
	@Autowired
	private JournalDao journalDao;
	@Autowired
	private POSSalesValidator posSalesValidator;	
	/*@Autowired
	private POSStockDao posstockDao;*/
	@Autowired
	AccountsDao accountsDao;
	@Autowired
	private POSCategoryDao posCategoryDao;
	@Autowired
	private AppConfigDao appConfigDao;
	@Autowired
	private CompanyInfoDao companyInfoDao;
	
	
	BigDecimal ZERO = new BigDecimal(0);
	BigDecimal CONVERT = new BigDecimal("-1");
	String tag_status;
	List<String> ledgerGroupCode;	
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
	throws Exception{
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class, dateEditor);	
	}
		
	
	//Ajax autoComplete code for POSCustomer - Customer Name.
		@RequestMapping(value="/customerName_Auto.htm",method = RequestMethod.GET)
		public @ResponseBody String customerName_Auto(@RequestParam(value="sNamePart",required=true)String sNamePart){
			
			List<String> snames = ledgerDao.getautoCustomerName(sNamePart);
			String customerNames = "";
			try{
				customerNames = snames.toString();
			} 
			catch(java.lang.IndexOutOfBoundsException e)
			{
				
			}
			
			return customerNames;
		}
		
		//ajax code for Itemcode
		@RequestMapping(value="/itemCodeDetail.htm",method = RequestMethod.POST)
		public @ResponseBody String itemCode_Auto(@RequestParam(value="iCodeValue",required=true)String iCodeValue){
			
			ArrayList<String> al = new ArrayList<String>();
			String SucessData = "";
			try{	
				
			List<PostagItem> snames = posTagItemDao.getItemCode(iCodeValue);
			if(snames.isEmpty() || snames.get(0).getQtyset()==0){
				SucessData="Invalid Itemcode";
				return SucessData;
			}
			
				al.add(0, snames.get(0).getCategoryName());			
				al.add(1,snames.get(0).getItemName());			
				al.add(2,snames.get(0).getQtyset().toString());			
				al.add(3,snames.get(0).getTotalpieces().toString());			
				al.add(4,snames.get(0).getSalesRate().toString());			
				al.add(5,snames.get(0).getDiscountPercentage().toString());	
				al.add(6,snames.get(0).getVatPercentage().toString());	
				SucessData = al.toString();	
				
				}
				catch(NullPointerException e){
					//System.out.println(e);
			}
				catch(IndexOutOfBoundsException ie){	
					//System.out.println(ie);
			}
			
			return SucessData;
		}
		
	@RequestMapping(value="/formPOSsales.htm",method=RequestMethod.GET)	
	public ModelAndView newForm(@ModelAttribute("POSsales")PSales possales,ModelMap model)
	{		
		ModelAndView mav=new ModelAndView();
		mav.addObject("POSsales",new PSales());
		model.addAttribute("bank_name", ledgerDao.listPosBank());
		model.addAttribute("assets_name", ledgerDao.listCurrentAssets());
		model.addAttribute("s_manname", userloginDao.userlist());
		model.addAttribute("customername", ledgerDao.listallCustomerName());
		return new ModelAndView("formPOSsales",model);			
	}	
	
	// for Estimate POS Sales List 3-1-13
		@RequestMapping(value="/estimatePOSSalesList.htm",method = RequestMethod.GET)
		public  ModelAndView listBullionSales(@ModelAttribute("POSsales")PSales possales, ModelMap model){
			List<POSSalesItem> posSalesItemList = posSalesDao.listSalesItem();
			model.addAttribute("possalesList", posSalesItemList);			
			return new ModelAndView("estimatePOSSalesList",model);
			
		}
		
		// for Estimate POS Sales List 3-1-13
		@RequestMapping(value = "/CheckEP.htm", method = RequestMethod.GET)
		public @ResponseBody String getCheckES() {
			//System.out.println("insdie controller ");
			List<ConfigDetailList> configDetail = appConfigDao.getModuleCode("EP");
			if(!configDetail.isEmpty()){
				if(configDetail.get(0).getStatus().equalsIgnoreCase("Active")){
					return("yes");
				}
			}
			return ("no");
			
		}
		
	@RequestMapping(value="/formPOSsales.htm",method=RequestMethod.POST,params="insert")
	public  ModelAndView insert(@ModelAttribute("POSsales")PSales possales,BindingResult result,SessionStatus status, HttpServletRequest request, HttpSession session, ModelMap model) throws Exception
	{		
		Integer qty = 0;
		tag_status = "Sold";		
		//Integer posSalesId = possales.getPossales().getPosSalesId();
		//POSSales PossalesOld = posSalesDao.getPOSSales(posSalesId);
		List<POSSalesItem> listpos=possales.getListpossalesItem();
		model.addAttribute("customername", ledgerDao.listallCustomerName());
		
		// $$$$$$$ POS Sales Entity Validation $$$$$$
		
		posSalesValidator.validate(possales.getPossales(), possales.getListpossalesItem(), result);
						
		if (result.hasErrors()) {
			possales.setPossales(possales.getPossales());
			possales.setListpossalesItem(possales.getListpossalesItem());	
			model.addAttribute("possalesList", listpos);
			model.addAttribute("errorName", "insertError");
			model.addAttribute("bank_name", ledgerDao.listPosBank());
			model.addAttribute("assets_name", ledgerDao.listCurrentAssets());
			model.addAttribute("s_manname", userloginDao.userlist());
			model.addAttribute("customername", ledgerDao.listallCustomerName());
			model.addAttribute(possales);
			return new ModelAndView("formPOSsales", model);
		}
		
		//setting item as sold 
		for(POSSalesItem newitem:listpos){
			newitem.setItemStatus("Sold");
			//newitem.setItemSalesType("POS Sales");
		}
		
		/**************delete dynamic rows*****************/
		Iterator<POSSalesItem> iter= listpos.iterator();
		while(iter.hasNext()) {	
			POSSalesItem popItem = iter.next();
			if(popItem.isPOSdeleted()==true ||(popItem.getPosItemCode()==null ) && (popItem.getCategoryName() == null || popItem.getCategoryName().length() == 0 ) && (popItem.getItemName() == null || popItem.getItemName().length() == 0) ){
				iter.remove();
				listpos.remove(popItem);
				possales.setListpossalesItem(listpos);
				}
		}
		
		possales.setListpossalesItem(listpos);	
		//add for invoice id for pos and EP 7-1-13
		if(possales.getPossales().getSalesType().equalsIgnoreCase("POS Sales")){
			possales.getPossales().setBillNo(getPOSINV(possales));
		}else if(possales.getPossales().getSalesType().equalsIgnoreCase("Estimate POS Sales")){
			possales.getPossales().setBillNo(getEP(possales));
		}
		
		posSalesDao.insertPOSSales(possales.getPossales());
		
		
		
		

//******************** Ledger Updation  ****************************/
				/* Regular or Walkin Cash */
		
		if(!possales.getPossales().getCustomerName().equals("Walk-in")){			
			
			List<Ledger> listpartyledger=ledgerDao.searchLedger(possales.getPossales().getCustomerName());
			String salesBlTypes = listpartyledger.get(0).getClosingTotalType();
			BigDecimal oldBalancePartycardParty=listpartyledger.get(0).getClosingTotalBalance();
			
			if(salesBlTypes.equals("Credit")){
				oldBalancePartycardParty = ZERO.subtract(oldBalancePartycardParty);
			}
			
			BigDecimal finalClosintPartyBal=oldBalancePartycardParty.add(possales.getPossales().getNetAmount());
			
			if(finalClosintPartyBal.signum() >= 0){
				ledgerDao.updateLedgerPartyBalance(finalClosintPartyBal, "Debit", possales.getPossales().getCustomerName());
			}
			else{
				finalClosintPartyBal = finalClosintPartyBal.multiply(CONVERT);
				ledgerDao.updateLedgerPartyBalance(finalClosintPartyBal, "Credit", possales.getPossales().getCustomerName());
			}
			
			  if(possales.getPossales().getSalesType().equalsIgnoreCase("POS Sales")){	
			/*********************************** Insert Operation - POS Sales Account Ledger : Regular - Cash & Credit Mode ***********/
			List<Ledger>  salesLedger = ledgerDao.searchLedger("POS Sales Account");
			BigDecimal OldsalesBl = salesLedger.get(0).getClosingTotalBalance();
			String salesBlType = salesLedger.get(0).getClosingTotalType();
			
			if(salesBlType.equals("Credit")){
				OldsalesBl = ZERO.subtract(OldsalesBl);
			}
			BigDecimal finalClSalesActBal=OldsalesBl.subtract(possales.getPossales().getNetAmount());
						
			if(finalClSalesActBal.signum() >= 0){
				ledgerDao.updateLedgerPartyBalance(finalClSalesActBal, "Debit", "POS Sales Account");
			}else{
				finalClSalesActBal = finalClSalesActBal.multiply(CONVERT);
				ledgerDao.updateLedgerPartyBalance(finalClSalesActBal, "Credit", "POS Sales Account");
			}	
			
			
				//****** Insert Operation - Auto Journal entry for POS Sales Regular Customer 
			
				jrnl = new Journal();
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("POS Sales Account");				
				jrnl.setJournalDate(possales.getPossales().getSalesdate());
				jrnl.setTransactionId("POS"+possales.getPossales().getPosSalesId());
 				
				
				// Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(possales.getPossales().getCustomerName());			
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
		        jrnl.setDebitAccountName(possales.getPossales().getCustomerName());
		        
		     // Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode("POS Sales Account");			
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());	
		        jrnl.setCreditAccountName("POS Sales Account");
		        
		        jrnl.setDebitAmount(possales.getPossales().getNetAmount());
		        jrnl.setCreditAmount(possales.getPossales().getNetAmount());
		        jrnl.setNarration("Bill No"+possales.getPossales().getBillNo());
			    journalDao.insertJournal(jrnl);
			  }
			  
			  ///********************* Ledger Updation for Receipt mode:Cash Account 
				   
			    if(possales.getPossales().getCash()!=null){
			    	if(possales.getPossales().getCash().equals("Cash") ){
			    		
	    	//******************* party Ledger Updation Ledger
					List<Ledger> ledgername=ledgerDao.searchLedger(possales.getPossales().getCustomerName());
					String typeSales=ledgername.get(0).getClosingTotalType();
					BigDecimal oldPartyCashBal=ledgername.get(0).getClosingTotalBalance();
					
					if(typeSales.equals("Credit")){
						oldPartyCashBal = ZERO.subtract(oldPartyCashBal);
					}
					
					BigDecimal finalClcashPartyBal=oldPartyCashBal.subtract(possales.getPossales().getCashAmount());
				if(finalClcashPartyBal.signum() >= 0){
						ledgerDao.updateLedgerPartyBalance(finalClcashPartyBal, "Debit", possales.getPossales().getCustomerName());
					}else{
						finalClcashPartyBal = finalClcashPartyBal.multiply(CONVERT);
						ledgerDao.updateLedgerPartyBalance(finalClcashPartyBal, "Credit", possales.getPossales().getCustomerName());
					}
				
				 if(possales.getPossales().getSalesType().equalsIgnoreCase("POS Sales")){
					///**************** Cash Account Updation Ledger
					List<Ledger> ledName=ledgerDao.searchLedger("Cash Account");
					String salTypes = ledName.get(0).getClosingTotalType();
					BigDecimal oldBalpartyCash=ledName.get(0).getClosingTotalBalance();
					
					if(salTypes.equals("Credit")){
						oldBalpartyCash = ZERO.subtract(oldBalpartyCash);
					}
					
					BigDecimal finalClCashActBal=oldBalpartyCash.add(possales.getPossales().getCashAmount());
					
					if(finalClCashActBal.signum() >= 0){
						ledgerDao.updateLedgerPartyBalance(finalClCashActBal, "Debit", "Cash Account");
					}else{
						finalClCashActBal = finalClCashActBal.multiply(CONVERT);
						ledgerDao.updateLedgerPartyBalance(finalClCashActBal, "Credit", "Cash Account");
					}
					
					///Journal Entry For Cash Account 
					jrnl = new Journal();
					jrnl.setJournalNO(getJournalNumber(jrnl));
					jrnl.setJournalType("POS Sales Cash Receipt");
					jrnl.setJournalDate(possales.getPossales().getSalesdate());
					jrnl.setTransactionId("POS"+possales.getPossales().getPosSalesId());
					
					// Set Account group code as Debit code
					ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");			
					jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
					jrnl.setDebitAccountName("Cash Account");
					
					//Set Account group code as Credit code
				    ledgerGroupCode = ledgerDao.getLedgerAccountCode(possales.getPossales().getCustomerName());			
				    jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					jrnl.setCreditAccountName(possales.getPossales().getCustomerName());
					
					jrnl.setDebitAmount(possales.getPossales().getCashAmount());
					jrnl.setCreditAmount(possales.getPossales().getCashAmount());
					jrnl.setNarration("Bill No"+possales.getPossales().getBillNo());
					journalDao.insertJournal(jrnl);
				 }//for Ep condtion end here
			        }//Cash condition ends here 
			    }//Not Equal to Condition ends here 
			    
			  //Condition for POS Sales First bill 4-1-13
				   if(possales.getPossales().getSalesType().equalsIgnoreCase("POS Sales")){
			  ///********************* Ledger Updation for Receipt mode:Cheque  
			     if(possales.getPossales().getCheque()!=null){
			    if(possales.getPossales().getCheque().equals("Cheque")){
			    	//******************* party Ledger Updation Ledger
			    	List<Ledger> ledgerNamepartyCheq=ledgerDao.searchLedger(possales.getPossales().getCustomerName());
					String salesTypes2 = ledgerNamepartyCheq.get(0).getClosingTotalType();
					BigDecimal oldBalcheqparty=ledgerNamepartyCheq.get(0).getClosingTotalBalance();
			    	
					if(salesTypes2.equals("Credit")){
						oldBalcheqparty = ZERO.subtract(oldBalcheqparty);
					}
					BigDecimal finalClcheqPartyBal=oldBalcheqparty.subtract(possales.getPossales().getChequeAmount());
					if(finalClcheqPartyBal.signum() >= 0){
						ledgerDao.updateLedgerPartyBalance(finalClcheqPartyBal, "Debit", possales.getPossales().getCustomerName());
					}else{
						finalClcheqPartyBal = finalClcheqPartyBal.multiply(CONVERT);
						ledgerDao.updateLedgerPartyBalance(finalClcheqPartyBal, "Credit", possales.getPossales().getCustomerName());
					}
					
					///**************** Cheque Account Updation Ledger
						List<Ledger> ledName=ledgerDao.searchLedger(possales.getPossales().getChequeBank());
						String salTypes2 = ledName.get(0).getClosingTotalType();
						BigDecimal oldBalCash=ledName.get(0).getClosingTotalBalance();
						
						if(salTypes2.equals("Credit")){
							oldBalCash = ZERO.subtract(oldBalCash);
						}
						
						BigDecimal finalClCarActBal=oldBalCash.add(possales.getPossales().getChequeAmount());
						
						if(finalClCarActBal.signum() >= 0){
							ledgerDao.updateLedgerPartyBalance(finalClCarActBal, "Debit", possales.getPossales().getChequeBank());
						}else{
							finalClCarActBal = finalClCarActBal.multiply(CONVERT);
							ledgerDao.updateLedgerPartyBalance(finalClCarActBal, "Credit", possales.getPossales().getChequeBank());
						}
					
					///****************** Journal Entry for Cheque Amount 
					jrnl=new Journal();
					jrnl.setJournalNO(getJournalNumber(jrnl));
			    	jrnl.setJournalType("POS Sales Cheque Receipt");
			    	jrnl.setJournalDate(possales.getPossales().getSalesdate());
			    	jrnl.setTransactionId("POS"+possales.getPossales().getPosSalesId());
			    	
			    	//Set Account group code as Debit code
			    	ledgerGroupCode = ledgerDao.getLedgerAccountCode(possales.getPossales().getChequeBank());			
			    	jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
			    	jrnl.setDebitAccountName(possales.getPossales().getChequeBank());
			    	
			    	//Set Account group code as Credit code
				    ledgerGroupCode = ledgerDao.getLedgerAccountCode(possales.getPossales().getCustomerName());			
				    jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
			    	jrnl.setCreditAccountName(possales.getPossales().getCustomerName());
			    	
			    	jrnl.setDebitAmount(possales.getPossales().getChequeAmount());
			    	jrnl.setCreditAmount(possales.getPossales().getChequeAmount());
			    	jrnl.setNarration("Bill No"+possales.getPossales().getBillNo());
			    	journalDao.insertJournal(jrnl);
			   
			    		}//checque conditon ends here
			    }//Not Equal to Condition
		
			    
			   /********************************** Card Amount Updation *********/
			     if(possales.getPossales().getCard()!=null){
			    	 if(possales.getPossales().getCard().equals("Card")){
			    		 
			    		//******************* party Ledger Updation 
					    	List<Ledger> ledgerNamepartCard=ledgerDao.searchLedger(possales.getPossales().getCustomerName());
							String salesTypes3 = ledgerNamepartCard.get(0).getClosingTotalType();
							BigDecimal oldBalcardparty=ledgerNamepartCard.get(0).getClosingTotalBalance();
					    	
							if(salesTypes3.equals("Credit")){
								oldBalcardparty = ZERO.subtract(oldBalcardparty);
							}
							BigDecimal finalClCarPartyBal = oldBalcardparty.subtract(possales.getPossales().getCardAmount());
							if(finalClCarPartyBal.signum() >= 0){
								ledgerDao.updateLedgerPartyBalance(finalClCarPartyBal, "Debit", possales.getPossales().getCustomerName());
							}else{
								finalClCarPartyBal = finalClCarPartyBal.multiply(CONVERT);
								ledgerDao.updateLedgerPartyBalance(finalClCarPartyBal, "Credit", possales.getPossales().getCustomerName());
							}
			    	 
			    	 //*************** Card Account Ledger update 
	                     String cardlist=possales.getPossales().getCardBank();		    
			    	 List<Ledger> ledName=ledgerDao.searchLedger(cardlist);
						String salTypes3 = ledName.get(0).getClosingTotalType();
						BigDecimal oldBalCash=ledName.get(0).getClosingTotalBalance();
						
						if(salTypes3.equals("Credit")){
							oldBalCash = ZERO.subtract(oldBalCash);
						}
						
						BigDecimal finalClCardActBal=oldBalCash.add(possales.getPossales().getCardAmount());
						
						if(finalClCardActBal.signum() >= 0){
							ledgerDao.updateLedgerPartyBalance(finalClCardActBal, "Debit", cardlist);
						}else{
							finalClCardActBal = finalClCardActBal.multiply(CONVERT);
							ledgerDao.updateLedgerPartyBalance(finalClCardActBal, "Credit", cardlist);
						}
			    
			     //********** new Journal Entry Card Amount 
			     jrnl=new Journal();
			     jrnl.setJournalNO(getJournalNumber(jrnl));
			     jrnl.setJournalType("POS Sales Card Receipt");
			     jrnl.setJournalDate(possales.getPossales().getSalesdate());
			     jrnl.setTransactionId("POS"+possales.getPossales().getPosSalesId());
			     
			     // Set Account group code as Debit code
			 	 ledgerGroupCode = ledgerDao.getLedgerAccountCode(cardlist);			
			 	 jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
			     jrnl.setDebitAccountName(cardlist);
			     
			     //Set Account group code as Credit code
			     ledgerGroupCode = ledgerDao.getLedgerAccountCode(possales.getPossales().getCustomerName());			
			     jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
			     jrnl.setCreditAccountName(possales.getPossales().getCustomerName());
			     
			     jrnl.setDebitAmount(possales.getPossales().getCardAmount());
			     jrnl.setCreditAmount(possales.getPossales().getCardAmount());
			     jrnl.setNarration("Bill No"+possales.getPossales().getBillNo());
			     journalDao.insertJournal(jrnl);
			     
			    	 		}//card conditon ends here
			     		}//card null condition check ends here
			    
			     /**************** Receipt Mode For::::: Voucher Amount ***********/
			     if(possales.getPossales().getGiftVoucher()!=null){
			if(possales.getPossales().getGiftVoucher().equalsIgnoreCase("Voucher")){
				
				//******************* party Ledger Updation 
		    	List<Ledger> ledgerNamepartyVouch=ledgerDao.searchLedger(possales.getPossales().getCustomerName());
				String salesTypes4 = ledgerNamepartyVouch.get(0).getClosingTotalType();
				BigDecimal oldBalVouchparty=ledgerNamepartyVouch.get(0).getClosingTotalBalance();
		    	
		    	
				if(salesTypes4.equals("Credit")){
					oldBalVouchparty = ZERO.subtract(oldBalVouchparty);
				}
				
				BigDecimal finalClVouhPartyBal=oldBalVouchparty.subtract(possales.getPossales().getVoucherAmount());
				if(finalClVouhPartyBal.signum() >= 0){
					ledgerDao.updateLedgerPartyBalance(finalClVouhPartyBal, "Debit", possales.getPossales().getCustomerName());
				}else{
					finalClVouhPartyBal = finalClVouhPartyBal.multiply(CONVERT);
					ledgerDao.updateLedgerPartyBalance(finalClVouhPartyBal, "Credit", possales.getPossales().getCustomerName());
				}
    	 
    	 
    	 //*************** Voucher Account Ledger update 
             String voucherlist=possales.getPossales().getVoucherList();		    
    	 List<Ledger> ledName=ledgerDao.searchLedger(voucherlist);
			String salTypes = ledName.get(0).getClosingTotalType();
			BigDecimal oldBalCash=ledName.get(0).getClosingTotalBalance();
			
			if(salTypes.equals("Credit")){
				oldBalCash = ZERO.subtract(oldBalCash);
			}
			
			BigDecimal finalClVoucherActBal=oldBalCash.add(possales.getPossales().getVoucherAmount());
			
			if(finalClVoucherActBal.signum() >= 0){
				ledgerDao.updateLedgerPartyBalance(finalClVoucherActBal, "Debit", voucherlist);
			}else{
				finalClVoucherActBal = finalClVoucherActBal.multiply(CONVERT);
				ledgerDao.updateLedgerPartyBalance(finalClVoucherActBal, "Credit", voucherlist);
			}
     
			     //********** new Journal Entry Voucher Amount 
			     jrnl=new Journal();
			     jrnl.setJournalNO(getJournalNumber(jrnl));
			     jrnl.setJournalType("POS Sales Voucher Receipt");
			     jrnl.setJournalDate(possales.getPossales().getSalesdate());
			     jrnl.setTransactionId("POS"+possales.getPossales().getPosSalesId());
			     
			     // Set Account group code as Debit code
			 	 ledgerGroupCode = ledgerDao.getLedgerAccountCode(voucherlist);			
			 	 jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
			     jrnl.setDebitAccountName(voucherlist);
			     
			     //Set Account group code as Credit code
			     ledgerGroupCode = ledgerDao.getLedgerAccountCode(possales.getPossales().getCustomerName());			
			     jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
			     jrnl.setCreditAccountName(possales.getPossales().getCustomerName());
			     
			     jrnl.setDebitAmount(possales.getPossales().getVoucherAmount());
			     jrnl.setCreditAmount(possales.getPossales().getVoucherAmount());
			     jrnl.setNarration("Bill No"+possales.getPossales().getBillNo());
			     journalDao.insertJournal(jrnl);     			   
			     
							}//voucher conditon ends here
			     		}//voucher null conditon check ends here
			    
			   	} //not walk in condition check ends here  
		}
		else{
			///************************** walk In Customer Update for Insert 
			                       /************************************* Recipt Mode :Cash ***************************/
			 //Condition for POS Sales First bill 4-1-13
			   if(possales.getPossales().getSalesType().equalsIgnoreCase("POS Sales")){
			if(possales.getPossales().getCash()!=null){
				if(possales.getPossales().getCash().equalsIgnoreCase("Cash")){
					/************ Sales Ledger Update ****************/
					List<Ledger>  salesLedger = ledgerDao.searchLedger("POS Sales Account");
					BigDecimal OldsalesBl = salesLedger.get(0).getClosingTotalBalance();
					String salesBlType = salesLedger.get(0).getClosingTotalType();
					
					if(salesBlType.equals("Credit")){
						OldsalesBl = ZERO.subtract(OldsalesBl);
					}
					BigDecimal finalClSalesActBal=OldsalesBl.subtract(possales.getPossales().getCashAmount());
								
					if(finalClSalesActBal.signum() >= 0){
						ledgerDao.updateLedgerPartyBalance(finalClSalesActBal, "Debit", "POS Sales Account");
					}else{
						finalClSalesActBal = finalClSalesActBal.multiply(CONVERT);
						ledgerDao.updateLedgerPartyBalance(finalClSalesActBal, "Credit", "POS Sales Account");
					}	
					
					                         /**************** Cash Book Update *******************/
					List<Ledger> ledName=ledgerDao.searchLedger("Cash Account");
					String salTypes = ledName.get(0).getClosingTotalType();
					BigDecimal oldBalpartyCash=ledName.get(0).getClosingTotalBalance();
					
					if(salTypes.equals("Credit")){
						oldBalpartyCash = ZERO.subtract(oldBalpartyCash);
					}
					
					BigDecimal finalClCashActBal=oldBalpartyCash.add(possales.getPossales().getCashAmount());
					
					if(finalClCashActBal.signum() >= 0){
						ledgerDao.updateLedgerPartyBalance(finalClCashActBal, "Debit", "Cash Account");
					}else{
						finalClCashActBal = finalClCashActBal.multiply(CONVERT);
						ledgerDao.updateLedgerPartyBalance(finalClCashActBal, "Credit", "Cash Account");
					}
					
					
					             /*********************** Journal Entry For Cash in Walk in Customer ************/
					jrnl = new Journal();
					jrnl.setJournalNO(getJournalNumber(jrnl));
					jrnl.setJournalType("POS Sales");
					jrnl.setJournalDate(possales.getPossales().getSalesdate());
					jrnl.setTransactionId("POS"+possales.getPossales().getPosSalesId());
					
					// Set Account group code as Debit code
					ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");			
					jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
					jrnl.setDebitAccountName("Cash Account");
					
					//Set Account group code as Credit code
				    ledgerGroupCode = ledgerDao.getLedgerAccountCode("POS Sales Account");			
				    jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					jrnl.setCreditAccountName("POS Sales Account");
					
					jrnl.setDebitAmount(possales.getPossales().getCashAmount());
					jrnl.setCreditAmount(possales.getPossales().getCashAmount());
					jrnl.setNarration("Bill No"+possales.getPossales().getBillNo());
					journalDao.insertJournal(jrnl);
					
						}/**** Walkin in cash mode conditon ends here ******/
					}//**** Not nulll condition ends here 
			
			/********************** Receipt Mode for Cheque Amount *************/
			
			if(possales.getPossales().getCheque()!=null){
				if(possales.getPossales().getCheque().equalsIgnoreCase("Cheque")){
					/************ Sales Ledger Update ****************/
					List<Ledger>  salesLedger = ledgerDao.searchLedger("POS Sales Account");
					BigDecimal OldsalesBl = salesLedger.get(0).getClosingTotalBalance();
					String salesBlType = salesLedger.get(0).getClosingTotalType();
					
					if(salesBlType.equals("Credit")){
						OldsalesBl = ZERO.subtract(OldsalesBl);
					}
					BigDecimal finalClSalesActBal=OldsalesBl.subtract(possales.getPossales().getChequeAmount());
								
					if(finalClSalesActBal.signum() >= 0){
						ledgerDao.updateLedgerPartyBalance(finalClSalesActBal, "Debit", "POS Sales Account");
					}else{
						finalClSalesActBal = finalClSalesActBal.multiply(CONVERT);
						ledgerDao.updateLedgerPartyBalance(finalClSalesActBal, "Credit", "POS Sales Account");
					}	
					
					/******************** Bank Account Update for Cheque receipt mode *********/
					

					
					List<Ledger> ledName=ledgerDao.searchLedger(possales.getPossales().getChequeBank());
					String salTypes2 = ledName.get(0).getClosingTotalType();
					BigDecimal oldBalCash=ledName.get(0).getClosingTotalBalance();
					
					if(salTypes2.equals("Credit")){
						oldBalCash = ZERO.subtract(oldBalCash);
					}
					
					BigDecimal finalClCarActBal=oldBalCash.add(possales.getPossales().getChequeAmount());
					
					if(finalClCarActBal.signum() >= 0){
						ledgerDao.updateLedgerPartyBalance(finalClCarActBal, "Debit", possales.getPossales().getChequeBank());
					}else{
						finalClCarActBal = finalClCarActBal.multiply(CONVERT);
						ledgerDao.updateLedgerPartyBalance(finalClCarActBal, "Credit", possales.getPossales().getChequeBank());
					}
					
						
					///****************** Journal Entry for Cheque Amount 
					jrnl=new Journal();
					jrnl.setJournalNO(getJournalNumber(jrnl));
			    	jrnl.setJournalType("POS Sales");
			    	jrnl.setJournalDate(possales.getPossales().getSalesdate());
			    	jrnl.setTransactionId("POS"+possales.getPossales().getPosSalesId());
			    	
			    	// Set Account group code as Debit code
			    	ledgerGroupCode = ledgerDao.getLedgerAccountCode(possales.getPossales().getChequeBank());			
			    	jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
			    	jrnl.setDebitAccountName(possales.getPossales().getChequeBank());
			    	
			    	//Set Account group code as Credit code
				    ledgerGroupCode = ledgerDao.getLedgerAccountCode("POS Sales Account");			
				    jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
			    	jrnl.setCreditAccountName("POS Sales Account");
			    	
			    	jrnl.setDebitAmount(possales.getPossales().getChequeAmount());
			    	jrnl.setCreditAmount(possales.getPossales().getChequeAmount());
			    	jrnl.setNarration("Bill No"+possales.getPossales().getBillNo());
			    	journalDao.insertJournal(jrnl);
				}
				
			}
				/*************************** Receipt Mode for Card **********************/
				if(possales.getPossales().getCard()!=null){
					if(possales.getPossales().getCard().equalsIgnoreCase("Card")){
				
				//************ Sales Ledger Update ****************/
				List<Ledger>  salesLedger = ledgerDao.searchLedger("POS Sales Account");
				BigDecimal OldsalesBl = salesLedger.get(0).getClosingTotalBalance();
				String salesBlType = salesLedger.get(0).getClosingTotalType();
				
				if(salesBlType.equals("Credit")){
					OldsalesBl = ZERO.subtract(OldsalesBl);
				}
				BigDecimal finalClSalesActBal=OldsalesBl.subtract(possales.getPossales().getCardAmount());
							
				if(finalClSalesActBal.signum() >= 0){
					ledgerDao.updateLedgerPartyBalance(finalClSalesActBal, "Debit", "POS Sales Account");
				}else{
					finalClSalesActBal = finalClSalesActBal.multiply(CONVERT);
					ledgerDao.updateLedgerPartyBalance(finalClSalesActBal, "Credit", "POS Sales Account");
				}	
				
				//*************** Card Account Ledger update 
                String cardlist=possales.getPossales().getCardBank();		    
	    	 List<Ledger> ledName=ledgerDao.searchLedger(cardlist);
				String salTypes3 = ledName.get(0).getClosingTotalType();
				BigDecimal oldBalCash=ledName.get(0).getClosingTotalBalance();
				
				if(salTypes3.equals("Credit")){
					oldBalCash = ZERO.subtract(oldBalCash);
				}
				
				BigDecimal finalClCardActBal=oldBalCash.add(possales.getPossales().getCardAmount());
				
				if(finalClCardActBal.signum() >= 0){
					ledgerDao.updateLedgerPartyBalance(finalClCardActBal, "Debit", cardlist);
				}else{
					finalClCardActBal = finalClCardActBal.multiply(CONVERT);
					ledgerDao.updateLedgerPartyBalance(finalClCardActBal, "Credit", cardlist);
				}
	    
	     
			     //********** new Journal Entry Card Amount 
			     jrnl=new Journal();
			     jrnl.setJournalNO(getJournalNumber(jrnl));
			     jrnl.setJournalType("POS Sales");
			     jrnl.setJournalDate(possales.getPossales().getSalesdate());
			     jrnl.setTransactionId("POS"+possales.getPossales().getPosSalesId());
			     
			     // Set Account group code as Debit code
			 	 ledgerGroupCode = ledgerDao.getLedgerAccountCode(cardlist);			
			 	 jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
			     jrnl.setDebitAccountName(cardlist);
			     
			     //Set Account group code as Credit code
			     ledgerGroupCode = ledgerDao.getLedgerAccountCode("POS Sales Account");			
			     jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
		         jrnl.setCreditAccountName("POS Sales Account");
			     
			     jrnl.setDebitAmount(possales.getPossales().getCardAmount());
			     jrnl.setCreditAmount(possales.getPossales().getCardAmount());
			     jrnl.setNarration("Bill No"+possales.getPossales().getBillNo());
			     journalDao.insertJournal(jrnl);
	     
					}/*** card conditon ends here for walk in ******/
				}//**** Card null codniton ends here
				
				
				/**************** Receipt Mode for Voucher **********************/
				
				if(possales.getPossales().getGiftVoucher()!=null){
					if(possales.getPossales().getGiftVoucher().equalsIgnoreCase("Voucher")){
						
						//************ Sales Ledger Update ****************/
						List<Ledger>  salesLedger = ledgerDao.searchLedger("POS Sales Account");
						BigDecimal OldsalesBl = salesLedger.get(0).getClosingTotalBalance();
						String salesBlType = salesLedger.get(0).getClosingTotalType();
						
						if(salesBlType.equals("Credit")){
							OldsalesBl = ZERO.subtract(OldsalesBl);
						}
						BigDecimal finalClSalesActBal=OldsalesBl.subtract(possales.getPossales().getVoucherAmount());
									
						if(finalClSalesActBal.signum() >= 0){
							ledgerDao.updateLedgerPartyBalance(finalClSalesActBal, "Debit", "POS Sales Account");
						}else{
							finalClSalesActBal = finalClSalesActBal.multiply(CONVERT);
							ledgerDao.updateLedgerPartyBalance(finalClSalesActBal, "Credit", "POS Sales Account");
						}	
						
						 //*************** Voucher Account Ledger update 
			             String voucherlist=possales.getPossales().getVoucherList();		    
			    	 List<Ledger> ledName=ledgerDao.searchLedger(voucherlist);
						String salTypes = ledName.get(0).getClosingTotalType();
						BigDecimal oldBalCash=ledName.get(0).getClosingTotalBalance();
						
						if(salTypes.equals("Credit")){
							oldBalCash = ZERO.subtract(oldBalCash);
						}
						
						BigDecimal finalClVoucherActBal=oldBalCash.add(possales.getPossales().getVoucherAmount());
						
						if(finalClVoucherActBal.signum() >= 0){
							ledgerDao.updateLedgerPartyBalance(finalClVoucherActBal, "Debit", voucherlist);
						}else{
							finalClVoucherActBal = finalClVoucherActBal.multiply(CONVERT);
							ledgerDao.updateLedgerPartyBalance(finalClVoucherActBal, "Credit", voucherlist);
						}
			     
			     //********** new Journal Entry Voucher Amount 
			     jrnl=new Journal();
			     jrnl.setJournalNO(getJournalNumber(jrnl));
			     jrnl.setJournalType("POS Sales");
			     jrnl.setJournalDate(possales.getPossales().getSalesdate());
			     jrnl.setTransactionId("POS"+possales.getPossales().getPosSalesId());
			     
			     // Set Account group code as Debit code
			 	 ledgerGroupCode = ledgerDao.getLedgerAccountCode(voucherlist);			
			 	 jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
			     jrnl.setDebitAccountName(voucherlist);
			     
			     //Set Account group code as Credit code
			     ledgerGroupCode = ledgerDao.getLedgerAccountCode("POS Sales Account");			
			     jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
			     jrnl.setCreditAccountName("POS Sales Account");
			     
			     jrnl.setDebitAmount(possales.getPossales().getVoucherAmount());
			     jrnl.setCreditAmount(possales.getPossales().getVoucherAmount());
			     jrnl.setNarration("Bill No"+possales.getPossales().getBillNo());
			     journalDao.insertJournal(jrnl);    
						
					}/*** voucher condition ends here for walk in *****/
				}//**** VOurcher null contion ends here
			
			}//first billing contions ends here
		}
	//	System.out.println("size : "+listpos.size());
		if(!listpos.isEmpty()){
				for (POSSalesItem objpos : listpos) {
				String barcodeID = objpos.getPosItemCode();				    
								
				if(!objpos.equals(null)){					
					objpos.setPossales(possales.getPossales());
					
				objpos.setItemSalesType(possales.getPossales().getSalesType());
					//	posstockDao.updatePOSStock(qty, tag_status, barcodeID);//Update stock
					posSalesDao.insertPOSSalesItem(objpos);//Insert values into pos sales item				
					posTagItemDao.updateSoldTagItem(qty, tag_status, barcodeID);//update Tag item		
					posCategoryDao.updateCategorySet(-1, objpos.getCategoryName());
				}
			}	
		}
			model.addAttribute("s_manname", userloginDao.userlist());
			model.addAttribute("bank_name", ledgerDao.listBank()); 
			model.addAttribute("assets_name", ledgerDao.listCurrentAssets());
			model.addAttribute("POSsales",new PSales());
			model.addAttribute("possales",possales);
			model.addAttribute("posSalesId",possales.getPossales().getPosSalesId());			
			model.addAttribute("invc", possales.getPossales().getBillNo());
			
			if(possales.getPossales().isPrintInvoice()) {	  
				InvoiceBillFormat posinoviceObj = new InvoiceTextFormatPrint();
				List<CompanyInfo> companyList = companyInfoDao.listCompanyInfo();
				if( ! companyList.isEmpty()){ // if Printer Format set in Company Info
					if(companyList.get(0).getPosInvoicePrint().equalsIgnoreCase("dotMatrix8")){
						posinoviceObj.printPOSInvoiceMatrixEightInch(possales,companyInfoDao, ledgerDao, request);
						return new ModelAndView("redirect:formPOSsales.htm",model);	
					}else if(companyList.get(0).getPosInvoicePrint().equalsIgnoreCase("dotMatrix4")){
						posinoviceObj.printPOSInvoiceMatrixFourInch(possales,companyInfoDao, ledgerDao, request);
						return new ModelAndView("redirect:formPOSsales.htm",model);	
						//return new ModelAndView("redirect:posInvoicePreview.htm",model);	
					}else if(companyList.get(0).getPosInvoicePrint().equalsIgnoreCase("Citizen3")){
						posinoviceObj.printPOSInvoiceCitizen(possales,companyInfoDao, ledgerDao, request);
						return new ModelAndView("redirect:formPOSsales.htm",model);	
					}
				}			
				status.setComplete();
				return new ModelAndView("redirect:invoiceSalesPreview.htm",model);
			}
			else {
				status.setComplete();
				return new ModelAndView("redirect:formPOSsales.htm",model);	
			}
	}
	
	@RequestMapping(value = "/invoiceSalesPreview.htm", method = RequestMethod.GET)
	public ModelAndView pdfInvoicePreview() {
		return new ModelAndView("POSsalePreview");
	}
	
	/** For View Mode POS Sales ***/
	@RequestMapping(value="/viewPOSSales.htm",method=RequestMethod.GET)
	public ModelAndView viewForm(@ModelAttribute("POSsales") PSales psales,@RequestParam(value="posSalesId",required=false)Integer posSalesId,ModelMap model){
			
		POSSales Possales = posSalesDao.getPOSSales(posSalesId);
		String sales_type = Possales.getSalesType();
		List<POSSalesItem> possalesitem = posSalesDao.getPOSSalesItem(posSalesId, sales_type);
	
		 	if (Possales != null && !"".equals(posSalesId)) {		 					
			psales.setPossales(Possales);
			psales.setListpossalesItem(possalesitem);
			List<POSSalesItem> pospJspSalesList = posSalesDao.getPOSSalesItem(posSalesId, sales_type);
			POSSalesItem pospItemSalesTemp = pospJspSalesList.get(pospJspSalesList.size() - 1);
			pospJspSalesList.remove(pospItemSalesTemp);
			
				if(Possales.getSalesType().equals("POS Sales") || Possales.getSalesType().equals("Estimate POS Sales")){
					model.addAttribute("salesItemsList", pospJspSalesList);				
					model.addAttribute(psales);					
					model.addAttribute("bank_name", ledgerDao.listPosBank());
					model.addAttribute("assets_name", ledgerDao.listCurrentAssets());
					model.addAttribute("s_manname", userloginDao.userlist());
					model.addAttribute("customername", ledgerDao.listallCustomerName());
					return new ModelAndView("formPOSsales",model);
				}
				else if (Possales.getSalesType().equals("POS Sales Return")) {
					model.addAttribute("salesItemsList", pospJspSalesList);	
					return new ModelAndView("formPOSsalesreturn",model);
				}
		 	}
		 	return new ModelAndView("formPOSsales",model);
		
	}
	 
	/**  For Update Mode Sales **/
	@RequestMapping(value="/formPOSsales.htm",method=RequestMethod.POST,params="update")
	public ModelAndView updateForm(@ModelAttribute("POSsales")PSales posSalesCmd,BindingResult result,ModelMap model){
		
		Integer posSalesId = posSalesCmd.getPossales().getPosSalesId();
		POSSales PossalesOld = posSalesDao.getPOSSales(posSalesId);
        List<POSSalesItem> listsalesOld = posSalesDao.getPOSSalesItem(posSalesId,posSalesCmd.getPossales().getSalesType());
		List<POSSalesItem> listpos = posSalesCmd.getListpossalesItem();
		//System.out.println(listpos.get(0).getItemSalesType());
		model.addAttribute("customername", ledgerDao.listallCustomerName());
		
		/**************delete dynamic rows*****************/
		Iterator<POSSalesItem> iter= listpos.iterator();
		while(iter.hasNext()) {	
			POSSalesItem popItem = iter.next();
			if( popItem.getPosItemCode()==null && (popItem.getCategoryName() == null || popItem.getCategoryName().length() == 0 ) && (popItem.getItemName() == null || popItem.getItemName().length() == 0) ){
				iter.remove();
				listpos.remove(popItem);
				posSalesCmd.setListpossalesItem(listpos);
			}
			
		}
		
		/*for deleteing row */
		for(POSSalesItem oldPosItem:listpos){
			
			if(oldPosItem.getQuantity()==0)
			{		
				String delededBarcodeId=oldPosItem.getPosItemCode();
				List<PostagItem> existingBarcode=posTagItemDao.getItemCode(delededBarcodeId);
			
				for(PostagItem tag:existingBarcode)
				{
			
					if(tag.getQtyset()==0){
						
						String deletedbarcodeId=oldPosItem.getPosItemCode();	 
						posTagItemDao.refilldeletedBarcode(deletedbarcodeId);			
						posCategoryDao.updateCategorySet(1, tag.getCategoryName());
					}
				}
			}
		}		
		
		posSalesValidator.validateUpdate(posSalesCmd.getPossales(), PossalesOld, result);
		posSalesValidator.validate(posSalesCmd.getPossales(), posSalesCmd.getListpossalesItem(), result);
		
           if (result.hasErrors()) {
        	  
   			posSalesCmd.setPossales(posSalesCmd.getPossales());
   			posSalesCmd.setListpossalesItem(listpos);
   			model.addAttribute("errorName", "updateError");
   			model.addAttribute("possalesList", posSalesCmd.getListpossalesItem());
   			model.addAttribute("bank_name", ledgerDao.listPosBank());
   			model.addAttribute("assets_name", ledgerDao.listCurrentAssets());
   			model.addAttribute("s_manname", userloginDao.userlist());
   			model.addAttribute("customername", ledgerDao.listallCustomerName());
   			model.addAttribute(posSalesCmd);   			
   			return new ModelAndView("formPOSsales",model);
   		}	
           
		for(POSSalesItem newposObj :listpos){
			
			if(newposObj.getSalesItemID()==null || newposObj.getSalesItemID().equals("")){
				
				newposObj.setPossales(posSalesCmd.getPossales());
				newposObj.setItemSalesType(listsalesOld.get(0).getItemSalesType());
			//	System.out.println("new add row"+listsalesOld.get(0).getItemSalesType());
				newposObj.setItemStatus("Sold");
				posSalesDao.insertPOSSalesItem(newposObj);
				
				if(newposObj.isPOSdeleted()!=true){
					posTagItemDao.updateSoldTagItem(0, "Sold", newposObj.getPosItemCode());//update Tag item
					posCategoryDao.updateCategorySet(-1, newposObj.getCategoryName());//Update Category Quantity				
				}
			}
			else{
				for(POSSalesItem oldPosObj:listsalesOld){				
					
					/* existing id update */
					if(newposObj.getSalesItemID().equals(oldPosObj.getSalesItemID())){
						newposObj.setPossales(posSalesCmd.getPossales());
						newposObj.setItemStatus(oldPosObj.getItemStatus());					
						posSalesDao.updatePOSSalesItem(newposObj);					
					}
				}
			}			
		}
			 POSSales possalesOld = posSalesDao.getPOSSales(posSalesId);
			 String finalClType ="";	
				
			//**************************************   Bill Amount : Sales Ledger Update : Same Customer & Different Customer *********************************************/
				if(possalesOld.getSalesType().equalsIgnoreCase("POS Sales")){
				if(possalesOld.getNetAmount().compareTo(posSalesCmd.getPossales().getNetAmount()) != 0) {
							
					List <Ledger> ledgerListSales = ledgerDao.searchLedger("POS Sales Account");
					BigDecimal oldClBalanceSales = ledgerListSales.get(0).getClosingTotalBalance();
					String clTypeSales = ledgerListSales.get(0).getClosingTotalType();
					
					if(clTypeSales.equalsIgnoreCase("Credit")){
						oldClBalanceSales = ZERO.subtract(oldClBalanceSales);						
					}
				
					BigDecimal dropClBalanceSales = oldClBalanceSales.add(possalesOld.getNetAmount());
					BigDecimal finalClBalanceSales  = dropClBalanceSales.subtract(posSalesCmd.getPossales().getNetAmount());				
				
					if (finalClBalanceSales.signum() == -1) { 
						finalClType = "Credit";
						finalClBalanceSales = finalClBalanceSales.multiply(CONVERT);
					} else {
						finalClType = "Debit";					
					}
					
					ledgerDao.updateLedgerPartyBalance(finalClBalanceSales, finalClType, "POS Sales Account");
				}	
				}

				///////////////////////////////  Bill Amount Party balance Update : if - Same Customer Names ///////////////////////////
				
					if(possalesOld.getCustomerName().equals(posSalesCmd.getPossales().getCustomerName())) 
					{
						if(!possalesOld.getCustomerName().equals("Walk-in")) 
						{
						
							//*********************************** Same Customer Journal Entry Update for Sales Bill Amount ***************************/
							if(possalesOld.getSalesType().equalsIgnoreCase("POS Sales")){
							List<Journal> JournalNewList = journalDao.getJournalUpdateSales("POS Sales Account", "POS"+posSalesCmd.getPossales().getPosSalesId());
							if(!JournalNewList.isEmpty()){
							//	System.out.println("the amount is " +posSalesCmd.getPossales().getNetAmount());
								JournalNewList.get(0).setDebitAmount(posSalesCmd.getPossales().getNetAmount());
								JournalNewList.get(0).setCreditAmount(posSalesCmd.getPossales().getNetAmount());
								JournalNewList.get(0).setJournalDate(posSalesCmd.getPossales().getSalesdate());
								JournalNewList.get(0).setDebitAccountName(posSalesCmd.getPossales().getCustomerName());
								journalDao.updateJournal(JournalNewList.get(0));
							}						
							
							}
							
							// ***************** if Regular Party ledger ************************//
							List <Ledger> ledgerListParty = ledgerDao.searchLedger(possalesOld.getCustomerName());
							String clTypeParty = ledgerListParty.get(0).getClosingTotalType();
							BigDecimal oldClBalanceParty = ledgerListParty.get(0).getClosingTotalBalance();
								
							if(clTypeParty.equalsIgnoreCase("Credit")){
								oldClBalanceParty = ZERO.subtract(oldClBalanceParty);						
							}
							
							BigDecimal dropClBalanceParty = oldClBalanceParty.subtract(possalesOld.getNetAmount());
							BigDecimal finalClBalanceParty  = dropClBalanceParty.add(posSalesCmd.getPossales().getNetAmount());
											
							if(finalClBalanceParty.signum() == -1){
								finalClType = "Credit";
								finalClBalanceParty = finalClBalanceParty.multiply(CONVERT);
							}else{
								finalClType = "Debit";						
							}
							
							ledgerDao.updateLedgerPartyBalance(finalClBalanceParty, finalClType, possalesOld.getCustomerName());
						}				
						
						
					}//********************* Party balance Update : if - different Customer Names **************************
					else if(!posSalesCmd.getPossales().getCustomerName().equals("Walk-in")) {	
						/** From Party 1 */
						List <Ledger> ledgerListParty1 = ledgerDao.searchLedger(possalesOld.getCustomerName());
						String clTypeParty1 = ledgerListParty1.get(0).getClosingTotalType();
						BigDecimal oldClBalanceParty1 = ledgerListParty1.get(0).getClosingTotalBalance();
						
						String finalClTypeParty1 = "";
						String finalClTypeParty2 = "";
						
						/** To Party 2 */
						List <Ledger> ledgerListParty2 = ledgerDao.searchLedger(posSalesCmd.getPossales().getCustomerName());
						String clTypeParty2 = ledgerListParty2.get(0).getClosingTotalType();
						BigDecimal oldClBalanceParty2 = ledgerListParty2.get(0).getClosingTotalBalance();
		
						if(clTypeParty1.equalsIgnoreCase("Credit")){
							oldClBalanceParty1 = ZERO.subtract(oldClBalanceParty1);						
						}
						if(clTypeParty2.equalsIgnoreCase("Credit")){
							oldClBalanceParty2 = ZERO.subtract(oldClBalanceParty2);						
						}
						
						BigDecimal finalClBalanceParty1 = oldClBalanceParty1.subtract(possalesOld.getNetAmount());
						BigDecimal finalClBalanceParty2 = oldClBalanceParty2.add(posSalesCmd.getPossales().getNetAmount());
										
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
						 
						ledgerDao.updateLedgerPartyBalance(finalClBalanceParty1, finalClTypeParty1, possalesOld.getCustomerName());
						ledgerDao.updateLedgerPartyBalance(finalClBalanceParty2, finalClTypeParty2, posSalesCmd.getPossales().getCustomerName());
						
						
						//*********************************** Different Customer Journal Entry Update for Sales Bill Amount ***************************/
						if(possalesOld.getSalesType().equalsIgnoreCase("POS Sales")){
						List<Journal> jrnlListSales = journalDao.getJournalUpdateSales("POS Sales Account","POS"+possalesOld.getPosSalesId());
						if(!jrnlListSales.isEmpty()){
							jrnl = new Journal();
							jrnl = jrnlListSales.get(0);
							jrnl.setJournalDate(posSalesCmd.getPossales().getSalesdate());
							jrnl.setDebitAccountName(posSalesCmd.getPossales().getCustomerName());
							jrnl.setDebitAmount(posSalesCmd.getPossales().getNetAmount());
							jrnl.setCreditAmount(posSalesCmd.getPossales().getNetAmount());
						//	System.out.println("update entry net amount :"+jrnl.getDebitAmount());
							journalDao.updateJournal(jrnl);
						}	
						}
					}
						
					////////////////////////////  Receipt Mode - Cash Ledgers Update /////////////////////////////////////////
					if(!possalesOld.getCustomerName().equals(posSalesCmd.getPossales().getCustomerName()) && !posSalesCmd.getPossales().getCustomerName().equals("Walk-in")) 
					{
							
						//********************* Cash Party balance Update : if - different Customer Names **************************
						/** From Party 1 */
						List <Ledger> ledgerListCashParty1 = ledgerDao.searchLedger(possalesOld.getCustomerName());
						String clTypeCashParty1 = ledgerListCashParty1.get(0).getClosingTotalType();
						BigDecimal oldClBalanceCashParty1 = ledgerListCashParty1.get(0).getClosingTotalBalance();
						
						String finalClTypeParty1 = "";
						String finalClTypeParty2 = "";
						
						/** To Party 2 */
						List <Ledger> ledgerListCashParty2 = ledgerDao.searchLedger(posSalesCmd.getPossales().getCustomerName());
						String clTypeCashParty2 = ledgerListCashParty2.get(0).getClosingTotalType();
						BigDecimal oldClBalanceCashParty2 = ledgerListCashParty2.get(0).getClosingTotalBalance();

						if(clTypeCashParty1.equalsIgnoreCase("Credit")){
							oldClBalanceCashParty1 = ZERO.subtract(oldClBalanceCashParty1);						
						}
						if(clTypeCashParty2.equalsIgnoreCase("Credit")){
							oldClBalanceCashParty2 = ZERO.subtract(oldClBalanceCashParty2);						
						}
						
						BigDecimal finalClBalanceCashParty1 = oldClBalanceCashParty1.add(possalesOld.getCashAmount());
						BigDecimal finalClBalanceCashParty2 = oldClBalanceCashParty2.subtract(posSalesCmd.getPossales().getCashAmount());
										
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
						
						ledgerDao.updateLedgerPartyBalance(finalClBalanceCashParty1, finalClTypeParty1, possalesOld.getCustomerName());
						ledgerDao.updateLedgerPartyBalance(finalClBalanceCashParty2, finalClTypeParty2, posSalesCmd.getPossales().getCustomerName());
					}
					else if(!posSalesCmd.getPossales().getCustomerName().equals("Walk-in")){
						//********************* Cash Party balance Update : if - Same Customer Names **************************
					
							List <Ledger> ledgerListCashParty = ledgerDao.searchLedger(possalesOld.getCustomerName());
							BigDecimal oldClBalanceCashParty = ledgerListCashParty.get(0).getClosingTotalBalance();
							String clTypeCashParty = ledgerListCashParty.get(0).getClosingTotalType();
							
							if(clTypeCashParty.equalsIgnoreCase("Credit")){
								oldClBalanceCashParty = ZERO.subtract(oldClBalanceCashParty);						
							}
							
							BigDecimal dropClBalanceCashParty = oldClBalanceCashParty.add(possalesOld.getCashAmount());
							BigDecimal finalClBalanceCashParty  = dropClBalanceCashParty.subtract(posSalesCmd.getPossales().getCashAmount());
												
							if(finalClBalanceCashParty.signum() == -1){
								finalClType = "Credit";
								finalClBalanceCashParty = finalClBalanceCashParty.multiply(CONVERT);
							}else{
									finalClType = "Debit";						
								}
							ledgerDao.updateLedgerPartyBalance(finalClBalanceCashParty, finalClType, possalesOld.getCustomerName());					
						
							/*Receipt Mode : Cash Checkbox*\	
						/* Cash2.Cash Ledger Update */	
					if(possalesOld.getSalesType().equalsIgnoreCase("POS Sales")){
						List <Ledger> ledgerListCashLedger = ledgerDao.searchLedger("Cash Account");
						BigDecimal oldClBalanceCashLedger = ledgerListCashLedger.get(0).getClosingTotalBalance();
						String clTypeCashLedger = ledgerListCashLedger.get(0).getClosingTotalType();
						
						if(clTypeCashLedger.equalsIgnoreCase("Credit")){
							oldClBalanceCashLedger = ZERO.subtract(oldClBalanceCashLedger);						
						}
						
						BigDecimal dropClBalanceCashLedger = oldClBalanceCashLedger.subtract(possalesOld.getCashAmount());
						BigDecimal finalClBalanceCashLedger = dropClBalanceCashLedger.add(posSalesCmd.getPossales().getCashAmount());
								
						if(finalClBalanceCashLedger.signum() == -1){
							finalClType = "Credit";
							finalClBalanceCashLedger = finalClBalanceCashLedger.multiply(CONVERT);
						}else{
								finalClType = "Debit";						
							}
						ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger, finalClType, "Cash Account");	
							
						/* Cash3.Journal Entry Update for Cash */
						List<Journal> jrnlPOSSalesC = journalDao.getJournalUpdateSales("POS Sales Cash Receipt","POS"+possalesOld.getPosSalesId());
						if(!jrnlPOSSalesC.isEmpty()){	
							 if(possalesOld.getCashAmount().signum() ==  1 && posSalesCmd.getPossales().getCashAmount().signum() == 0){
									journalDao.deleteJournal(jrnlPOSSalesC.get(0)); /*** Delete Entry ***/
								}
							 else if(possalesOld.getCashAmount().signum() == 1  && posSalesCmd.getPossales().getCashAmount().signum() == 1){
							jrnlPOSSalesC.get(0).setDebitAmount(posSalesCmd.getPossales().getCashAmount());
							jrnlPOSSalesC.get(0).setCreditAmount(posSalesCmd.getPossales().getCashAmount());
							jrnlPOSSalesC.get(0).setCreditAccountName(posSalesCmd.getPossales().getCustomerName());
							jrnlPOSSalesC.get(0).setJournalDate(posSalesCmd.getPossales().getSalesdate());
							journalDao.updateJournal(jrnlPOSSalesC.get(0));
							
								}
						}else if(jrnlPOSSalesC.isEmpty() && posSalesCmd.getPossales().getCashAmount().signum() == 1){
							///Journal Entry For Cash Account 
							jrnl = new Journal();
							jrnl.setJournalNO(getJournalNumber(jrnl));
							jrnl.setJournalType("POS Sales Cash Receipt"); 
							jrnl.setJournalDate(posSalesCmd.getPossales().getSalesdate());
							jrnl.setTransactionId("POS"+posSalesCmd.getPossales().getPosSalesId());
							
							// Set Account group code as Debit code
							ledgerGroupCode = ledgerDao.getLedgerAccountCode("Cash Account");			
							jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
							jrnl.setDebitAccountName("Cash Account");
							
							//Set Account group code as Credit code
						    ledgerGroupCode = ledgerDao.getLedgerAccountCode(posSalesCmd.getPossales().getCustomerName());			
						    jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
							jrnl.setCreditAccountName(posSalesCmd.getPossales().getCustomerName());
							
							jrnl.setDebitAmount(posSalesCmd.getPossales().getCashAmount());
							jrnl.setCreditAmount(posSalesCmd.getPossales().getCashAmount());
							jrnl.setNarration("Bill No"+posSalesCmd.getPossales().getBillNo());
							journalDao.insertJournal(jrnl);
							
						}
					}
					}
				////////////////////////////////// Receipt Mode - Cheque Ledgers & Journal Update //////////////////////////////////
						if(possalesOld.getSalesType().equalsIgnoreCase("POS Sales")){
				if(!possalesOld.getCustomerName().equals(posSalesCmd.getPossales().getCustomerName()) && !posSalesCmd.getPossales().getCustomerName().equals("Walk-in"))
				{
					//********************* Cheque Party balance Update : if - different Customer Names ************************//
					
					/** From Party 1 */
					List <Ledger> ledgerListChequeParty1 = ledgerDao.searchLedger(possalesOld.getCustomerName());
					String clTypeChequeParty1 = ledgerListChequeParty1.get(0).getClosingTotalType();
					BigDecimal oldClBalanceChequeParty1 = ledgerListChequeParty1.get(0).getClosingTotalBalance();
					String finalClTypeParty1 = "";
					String finalClTypeParty2 = "";
					
					/** To Party 2 */
					List <Ledger> ledgerListChequeParty2 = ledgerDao.searchLedger(posSalesCmd.getPossales().getCustomerName());
					String clTypeChequeParty2 = ledgerListChequeParty2.get(0).getClosingTotalType();
					BigDecimal oldClBalanceChequeParty2 = ledgerListChequeParty2.get(0).getClosingTotalBalance();

					if(clTypeChequeParty1.equalsIgnoreCase("Credit")){
						oldClBalanceChequeParty1 = ZERO.subtract(oldClBalanceChequeParty1);						
					}
					if(clTypeChequeParty2.equalsIgnoreCase("Credit")){
						oldClBalanceChequeParty2 = ZERO.subtract(oldClBalanceChequeParty2);						
					}
					
					BigDecimal finalClBalanceChequeParty1 = oldClBalanceChequeParty1.add(possalesOld.getChequeAmount());
					BigDecimal finalClBalanceChequeParty2 = oldClBalanceChequeParty2.subtract(posSalesCmd.getPossales().getChequeAmount());
									
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
					
					ledgerDao.updateLedgerPartyBalance(finalClBalanceChequeParty1, finalClTypeParty1, possalesOld.getCustomerName());
					ledgerDao.updateLedgerPartyBalance(finalClBalanceChequeParty2, finalClTypeParty2, posSalesCmd.getPossales().getCustomerName());
				}
						//}
				else if(!posSalesCmd.getPossales().getCustomerName().equals("Walk-in")){
					//********************* Cheque Party balance Update : if - Same Customer Names **************************
					//if(possalesOld.getSalesType().equalsIgnoreCase("POS Sales")){
						List <Ledger> ledgerListChequeParty = ledgerDao.searchLedger(possalesOld.getCustomerName());
						BigDecimal oldClBalanceChequeParty = ledgerListChequeParty.get(0).getClosingTotalBalance();
						String clTypeChequeParty = ledgerListChequeParty.get(0).getClosingTotalType();
						
						if(clTypeChequeParty.equalsIgnoreCase("Credit")){
							oldClBalanceChequeParty = ZERO.subtract(oldClBalanceChequeParty);						
						}
						
						BigDecimal dropClBalanceChequeParty = oldClBalanceChequeParty.add(possalesOld.getChequeAmount());
						BigDecimal finalClBalanceChequeParty  = dropClBalanceChequeParty.subtract(posSalesCmd.getPossales().getChequeAmount());
											
						if(finalClBalanceChequeParty.signum() == -1){
							finalClType = "Credit";
							finalClBalanceChequeParty = finalClBalanceChequeParty.multiply(CONVERT);
						}else{
								finalClType = "Debit";						
							}
						ledgerDao.updateLedgerPartyBalance(finalClBalanceChequeParty, finalClType, possalesOld.getCustomerName());					
					
				//////////////////////////////////// C2.Cheque Bank Ledger Update ////////////////////////////////////////////
				List <Ledger> ledgerListChequeLedger = ledgerDao.searchLedger(possalesOld.getChequeBank());
					
				if(!ledgerListChequeLedger.isEmpty()) 
				{ // if initially cheque selected
					
						BigDecimal oldClBalanceChequeLedger = ledgerListChequeLedger.get(0).getClosingTotalBalance();
						String clTypeChequeLedger = ledgerListChequeLedger.get(0).getClosingTotalType();
						
						if(clTypeChequeLedger.equalsIgnoreCase("Credit")){
							oldClBalanceChequeLedger = ZERO.subtract(oldClBalanceChequeLedger);						
						}
						
						if(possalesOld.getChequeBank().equals(posSalesCmd.getPossales().getChequeBank())){
						//********************************* If the Bank A/c is "Same".******************************************/
							BigDecimal dropClBalanceChequeLedger = oldClBalanceChequeLedger.subtract(possalesOld.getChequeAmount());
							BigDecimal finalClBalanceChequeLedger = dropClBalanceChequeLedger.add(posSalesCmd.getPossales().getChequeAmount());
							
							if(finalClBalanceChequeLedger.signum() == -1){
								finalClType = "Credit";
								finalClBalanceChequeLedger = finalClBalanceChequeLedger.multiply(CONVERT);
							}
							else{
								finalClType = "Debit";						
							}
							ledgerDao.updateLedgerPartyBalance(finalClBalanceChequeLedger, finalClType, possalesOld.getChequeBank());					
						}else {					
							//************************ If the Bank A/c Name "Changes".******************************************/		
							/* B1. For old Bank A/c  */// Subtract old amount value in old Ledger
							
							BigDecimal finalOldBankLedgerAmt = oldClBalanceChequeLedger.subtract(possalesOld.getChequeAmount());
							if(finalOldBankLedgerAmt.signum() == -1){
								finalClType = "Credit";
								finalOldBankLedgerAmt = finalOldBankLedgerAmt.multiply(CONVERT);
							}else{
								finalClType = "Debit";						
							}
							ledgerDao.updateLedgerPartyBalance(finalOldBankLedgerAmt, finalClType, possalesOld.getChequeBank());
							
							/* B2. For New Bank A/c */// Add old amount value in old Ledger
							
							List <Ledger> ledgerNewBankLedger = ledgerDao.searchLedger(posSalesCmd.getPossales().getChequeBank());
							BigDecimal NewBankLedgerAmt = ledgerNewBankLedger.get(0).getClosingTotalBalance();
							String clNewBankLedgerType = ledgerNewBankLedger.get(0).getClosingTotalType();
							
							if(clNewBankLedgerType.equalsIgnoreCase("Credit")){
								NewBankLedgerAmt = ZERO.subtract(NewBankLedgerAmt);						
							}
							
							BigDecimal finalNewBankLedgerAmt = NewBankLedgerAmt.add(posSalesCmd.getPossales().getChequeAmount());
									
							if(finalNewBankLedgerAmt.signum() == -1){
								finalClType = "Credit";
								finalNewBankLedgerAmt = finalNewBankLedgerAmt.multiply(CONVERT);
							}else{
								finalClType = "Debit";						
							}
								
							ledgerDao.updateLedgerPartyBalance(finalNewBankLedgerAmt, finalClType, posSalesCmd.getPossales().getChequeBank());
						}				
				}else if(possalesOld.getChequeAmount().signum() ==  0 && posSalesCmd.getPossales().getChequeAmount().signum() == 1 ){
						// on Update added Cheque option 
						List <Ledger> ledgerNewBankLedger = ledgerDao.searchLedger(posSalesCmd.getPossales().getChequeBank());
						BigDecimal NewBankLedgerAmt = ledgerNewBankLedger.get(0).getClosingTotalBalance();
						String clNewBankLedgerType = ledgerNewBankLedger.get(0).getClosingTotalType();
						
						if(clNewBankLedgerType.equalsIgnoreCase("Credit")){
							NewBankLedgerAmt = ZERO.subtract(NewBankLedgerAmt);						
						}
						
						BigDecimal finalNewBankLedgerAmt = NewBankLedgerAmt.add(posSalesCmd.getPossales().getChequeAmount());
								
							if(finalNewBankLedgerAmt.signum() == -1){
								finalClType = "Credit";
								finalNewBankLedgerAmt = finalNewBankLedgerAmt.multiply(CONVERT);
							}else{
								finalClType = "Debit";						
							}
							
						ledgerDao.updateLedgerPartyBalance(finalNewBankLedgerAmt, finalClType, posSalesCmd.getPossales().getChequeBank());	
					}
					///////////////////////////////////// Journal Entries ////////////////////////////////////////////
					/* C3.Journal Entry Update for Cheque */
				List<Journal> jrnlPOSSalesChq = journalDao.getJournalUpdateSales("POS Sales Cheque Receipt","POS"+possalesOld.getPosSalesId());
				if(!jrnlPOSSalesChq.isEmpty()){	
					
						if(possalesOld.getChequeAmount().signum() ==  1 && posSalesCmd.getPossales().getChequeAmount().signum() == 0  ){
							//System.out.println("Inside Detle journal:::::::::::::::::;");
							journalDao.deleteJournal(jrnlPOSSalesChq.get(0));/*** Delete Entry***/
						}
						else if(posSalesCmd.getPossales().getChequeAmount().signum() == 1  && possalesOld.getChequeAmount().signum() == 1){
						//	System.out.println("Inside Journal LIst Empty check:::::::::::::"+possalesOld.getChequeAmount()+"\n"+posSalesCmd.getPossales().getChequeAmount());
							jrnlPOSSalesChq.get(0).setDebitAmount(posSalesCmd.getPossales().getChequeAmount());
							jrnlPOSSalesChq.get(0).setCreditAmount(posSalesCmd.getPossales().getChequeAmount());
							jrnlPOSSalesChq.get(0).setJournalDate(posSalesCmd.getPossales().getSalesdate());
							jrnlPOSSalesChq.get(0).setCreditAccountName(posSalesCmd.getPossales().getCustomerName());
							jrnlPOSSalesChq.get(0).setDebitAccountName(posSalesCmd.getPossales().getChequeBank());
					journalDao.updateJournal(jrnlPOSSalesChq.get(0));
				   }
				}else if(jrnlPOSSalesChq.isEmpty() && posSalesCmd.getPossales().getChequeAmount().signum() == 1){
					///****************** Journal Entry for Cheque Amount 
					jrnl=new Journal();
					jrnl.setJournalNO(getJournalNumber(jrnl));
			    	jrnl.setJournalType("POS Sales Cheque Receipt");
			    	jrnl.setJournalDate(posSalesCmd.getPossales().getSalesdate());
			    	jrnl.setTransactionId("POS"+posSalesCmd.getPossales().getPosSalesId());
			    	
			    	// Set Account group code as Debit code
			    	ledgerGroupCode = ledgerDao.getLedgerAccountCode(posSalesCmd.getPossales().getChequeBank());			
			    	jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
			    	jrnl.setDebitAccountName(posSalesCmd.getPossales().getChequeBank());
			    	
			    	//Set Account group code as Credit code
				    ledgerGroupCode = ledgerDao.getLedgerAccountCode(posSalesCmd.getPossales().getCustomerName());			
				    jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
			    	jrnl.setCreditAccountName(posSalesCmd.getPossales().getCustomerName());
			    	
			    	jrnl.setDebitAmount(posSalesCmd.getPossales().getChequeAmount());
			    	jrnl.setCreditAmount(posSalesCmd.getPossales().getChequeAmount());
			    	jrnl.setNarration("Bill No"+posSalesCmd.getPossales().getBillNo());
			    	journalDao.insertJournal(jrnl); 
				}			
				}
				}
				//////////////////////////////////////  Receipt Mode - Card Ledgers & Journal Update ///////////////////////////////////
			
			
						if(possalesOld.getSalesType().equalsIgnoreCase("POS Sales")){		
				if(!possalesOld.getCustomerName().equals(posSalesCmd.getPossales().getCustomerName()) && !posSalesCmd.getPossales().getCustomerName().equals("Walk-in"))
				{
					
					//********************* Card Party balance Update : if - different Customer Names **************************
					
					/** From Party 1 */
					List <Ledger> ledgerListCardParty1 = ledgerDao.searchLedger(possalesOld.getCustomerName());
					String clTypeCardParty1 = ledgerListCardParty1.get(0).getClosingTotalType();
					BigDecimal oldClBalanceCardParty1 = ledgerListCardParty1.get(0).getClosingTotalBalance();
					String finalClTypeParty1 = "";
					String finalClTypeParty2 = "";
					
					/** To Party 2 */
					List <Ledger> ledgerListCardParty2 = ledgerDao.searchLedger(posSalesCmd.getPossales().getCustomerName());
					String clTypeCardParty2 = ledgerListCardParty2.get(0).getClosingTotalType();
					BigDecimal oldClBalanceCardParty2 = ledgerListCardParty2.get(0).getClosingTotalBalance();

					if(clTypeCardParty1.equalsIgnoreCase("Credit")){
						oldClBalanceCardParty1 = ZERO.subtract(oldClBalanceCardParty1);						
					}
					if(clTypeCardParty2.equalsIgnoreCase("Credit")){
						oldClBalanceCardParty2 = ZERO.subtract(oldClBalanceCardParty2);						
					}
					
					BigDecimal finalClBalanceCardParty1 = oldClBalanceCardParty1.add(possalesOld.getCardAmount());
					BigDecimal finalClBalanceCardParty2 = oldClBalanceCardParty2.subtract(posSalesCmd.getPossales().getCardAmount());
									
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
					
					ledgerDao.updateLedgerPartyBalance(finalClBalanceCardParty1, finalClTypeParty1, possalesOld.getCustomerName());
					ledgerDao.updateLedgerPartyBalance(finalClBalanceCardParty2, finalClTypeParty2, posSalesCmd.getPossales().getCustomerName());
				
				}else if(!posSalesCmd.getPossales().getCustomerName().equals("Walk-in")){
					//********************* Card Party balance Update : if - Same Customer Names **************************

						List <Ledger> ledgerListCardParty = ledgerDao.searchLedger(possalesOld.getCustomerName());
						BigDecimal oldClBalanceCardParty = ledgerListCardParty.get(0).getClosingTotalBalance();
						String clTypeCardParty = ledgerListCardParty.get(0).getClosingTotalType();
						
						if(clTypeCardParty.equalsIgnoreCase("Credit")){
							oldClBalanceCardParty = ZERO.subtract(oldClBalanceCardParty);						
						}
						
						BigDecimal dropClBalanceCardParty = oldClBalanceCardParty.add(possalesOld.getCardAmount());
						BigDecimal finalClBalanceCardParty  = dropClBalanceCardParty.subtract(posSalesCmd.getPossales().getCardAmount());
						
						
						if(finalClBalanceCardParty.signum() == -1){
							finalClType = "Credit";
							finalClBalanceCardParty = finalClBalanceCardParty.multiply(CONVERT);
						}else{
							finalClType = "Debit";						
						}
						ledgerDao.updateLedgerPartyBalance(finalClBalanceCardParty, finalClType, possalesOld.getCustomerName());
				
					//////////////////////////////* Card2.Card Bank Ledger Update *///////////////////////////////////////
					List <Ledger> ledgerListCardLedger = ledgerDao.searchLedger(possalesOld.getCardBank());
					
					if(!ledgerListCardLedger.isEmpty())
					{ //initially card selected
						BigDecimal oldClBalanceCardLedger = ledgerListCardLedger.get(0).getClosingTotalBalance();
						String clTypeCardLedger = ledgerListCardLedger.get(0).getClosingTotalType();
						
						if(clTypeCardLedger.equalsIgnoreCase("Credit")) {
							oldClBalanceCardLedger = ZERO.subtract(oldClBalanceCardLedger);
						}
						
						if(possalesOld.getCardBank().equals(posSalesCmd.getPossales().getCardBank())) 
						{//************************************** If the Bank A/c is Same. *******************************/
							BigDecimal dropClBalanceCardLedger = oldClBalanceCardLedger.subtract(possalesOld.getCardAmount());
							BigDecimal finalClBalanceCardLedger  = dropClBalanceCardLedger.add(posSalesCmd.getPossales().getCardAmount());
												
							if(finalClBalanceCardLedger.signum() == -1){
								finalClType = "Credit";
								finalClBalanceCardLedger = finalClBalanceCardLedger.multiply(CONVERT);
							}else{
								finalClType = "Debit";						
							}
							ledgerDao.updateLedgerPartyBalance(finalClBalanceCardLedger, finalClType, possalesOld.getCardBank());					
						
						}else{	//************************** B0. If the Bank A/c Name changes for Card .*********************************/
							/*B1. For old Bank A/c for Card   */ // Subtract old card amount in old ledger
													
							BigDecimal finalOldBankLedgerCardAmt = oldClBalanceCardLedger.subtract(possalesOld.getCardAmount());
							if(finalOldBankLedgerCardAmt.signum() == -1){
								finalClType = "Credit";
								finalOldBankLedgerCardAmt = finalOldBankLedgerCardAmt.multiply(CONVERT);
							}else{
								finalClType = "Debit";						
							}
							ledgerDao.updateLedgerPartyBalance(finalOldBankLedgerCardAmt, finalClType, possalesOld.getCardBank());
								
							/* B2. For New Bank A/c for Card */// Add new amount value in ledger
							List <Ledger> ledgerNewBankCardLedger = ledgerDao.searchLedger(posSalesCmd.getPossales().getCardBank());
							BigDecimal NewBankLedgerCardAmt = ledgerNewBankCardLedger.get(0).getClosingTotalBalance();
							String clNewBankLedgerCardType = ledgerNewBankCardLedger.get(0).getClosingTotalType();
							
							if(clNewBankLedgerCardType.equalsIgnoreCase("Credit")){
								NewBankLedgerCardAmt = ZERO.subtract(NewBankLedgerCardAmt);						
							}
							
							BigDecimal finalNewBankLedgerAmt = NewBankLedgerCardAmt.add(posSalesCmd.getPossales().getCardAmount());
									
							if(finalNewBankLedgerAmt.signum() == -1){
								finalClType = "Credit";
								finalNewBankLedgerAmt = finalNewBankLedgerAmt.multiply(CONVERT);
							}else{
								finalClType = "Debit";						
							}
							ledgerDao.updateLedgerPartyBalance(finalNewBankLedgerAmt, finalClType, posSalesCmd.getPossales().getCardBank());					
					}				

				}else if(possalesOld.getCardAmount().signum() == 0 && posSalesCmd.getPossales().getCardAmount().signum() == 1 ) {
					// On update added card option
					List <Ledger> ledgerNewBankCardLedger = ledgerDao.searchLedger(posSalesCmd.getPossales().getCardBank());
					BigDecimal NewBankLedgerCardAmt = ledgerNewBankCardLedger.get(0).getClosingTotalBalance();
					String clNewBankLedgerCardType = ledgerNewBankCardLedger.get(0).getClosingTotalType();
					
					if(clNewBankLedgerCardType.equalsIgnoreCase("Credit")){
						NewBankLedgerCardAmt = ZERO.subtract(NewBankLedgerCardAmt);						
					}
					
					BigDecimal finalNewBankLedgerAmt = NewBankLedgerCardAmt.add(posSalesCmd.getPossales().getCardAmount());
							
					if(finalNewBankLedgerAmt.signum() == -1){
						finalClType = "Credit";
						finalNewBankLedgerAmt = finalNewBankLedgerAmt.multiply(CONVERT);
					}else{
						finalClType = "Debit";						
					}
					ledgerDao.updateLedgerPartyBalance(finalNewBankLedgerAmt, finalClType, posSalesCmd.getPossales().getCardBank());	
				}
				/* Card3.Journal Entry Update for Card */
					List<Journal> jrnlPOSSalesCard = journalDao.getJournalUpdateSales("POS Sales Card Receipt","POS"+possalesOld.getPosSalesId());
					if(!jrnlPOSSalesCard.isEmpty()){
						if(possalesOld.getCardAmount().signum() == 1 && posSalesCmd.getPossales().getCardAmount().signum() == 0){
							journalDao.deleteJournal(jrnlPOSSalesCard.get(0)); // Delete Entry 
						}else if(posSalesCmd.getPossales().getCardAmount().signum() == 1 && possalesOld.getCardAmount().signum() == 1){
						jrnlPOSSalesCard.get(0).setDebitAmount(posSalesCmd.getPossales().getCardAmount());
						jrnlPOSSalesCard.get(0).setCreditAmount(posSalesCmd.getPossales().getCardAmount());
						jrnlPOSSalesCard.get(0).setJournalDate(posSalesCmd.getPossales().getSalesdate());
						jrnlPOSSalesCard.get(0).setCreditAccountName(posSalesCmd.getPossales().getCustomerName());
						jrnlPOSSalesCard.get(0).setDebitAccountName(posSalesCmd.getPossales().getCardBank());
						journalDao.updateJournal(jrnlPOSSalesCard.get(0));
					}
					}else if(jrnlPOSSalesCard.isEmpty() && posSalesCmd.getPossales().getCardAmount().signum() == 1){
						//System.out.println("Card New entry::;");
						//********** new Journal Entry Card Amount 
					     jrnl=new Journal();
					     jrnl.setJournalNO(getJournalNumber(jrnl));
					     jrnl.setJournalType("POS Sales Card Receipt");
					     jrnl.setJournalDate(posSalesCmd.getPossales().getSalesdate());
					     jrnl.setTransactionId("POS"+posSalesCmd.getPossales().getPosSalesId());
					     
					     // Set Account group code as Debit code
					 	 ledgerGroupCode = ledgerDao.getLedgerAccountCode(posSalesCmd.getPossales().getCardBank());			
					 	 jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
					     jrnl.setDebitAccountName(posSalesCmd.getPossales().getCardBank());
					     
					     //Set Account group code as Credit code
					     ledgerGroupCode = ledgerDao.getLedgerAccountCode(posSalesCmd.getPossales().getCustomerName());			
        				 jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					     jrnl.setCreditAccountName(posSalesCmd.getPossales().getCustomerName());
					     
					     jrnl.setDebitAmount(posSalesCmd.getPossales().getCardAmount());
					     jrnl.setCreditAmount(posSalesCmd.getPossales().getCardAmount());
					     jrnl.setNarration("Bill No"+posSalesCmd.getPossales().getBillNo());
					     journalDao.insertJournal(jrnl);
					}
						}
	}
			///////////////////////////////////// Receipt Mode - Voucher Ledgers & Journal Update ///////////////////////////////////
			
			/* Voucher1. party Balance Update */
						if(possalesOld.getSalesType().equalsIgnoreCase("POS Sales")){
			if(!possalesOld.getCustomerName().equals(posSalesCmd.getPossales().getCustomerName()) && !posSalesCmd.getPossales().getCustomerName().equals("Walk-in")){
				
				//********************* Voucher Party balance Update : if - different Customer Names ************************//
				
				/** From Party 1 */
				List <Ledger> ledgerListVoucherParty1 = ledgerDao.searchLedger(possalesOld.getCustomerName());
				String clTypeVoucherParty1 = ledgerListVoucherParty1.get(0).getClosingTotalType();
				BigDecimal oldClBalanceVoucherParty1 = ledgerListVoucherParty1.get(0).getClosingTotalBalance();
				String finalClTypeParty1 = "";
				String finalClTypeParty2 = "";
				
				/** To Party 2 */
				List <Ledger> ledgerListVoucherParty2 = ledgerDao.searchLedger(posSalesCmd.getPossales().getCustomerName());
				String clTypeVoucherParty2 = ledgerListVoucherParty2.get(0).getClosingTotalType();
				BigDecimal oldClBalanceVoucherParty2 = ledgerListVoucherParty2.get(0).getClosingTotalBalance();

				if(clTypeVoucherParty1.equalsIgnoreCase("Credit")){
					oldClBalanceVoucherParty1 = ZERO.subtract(oldClBalanceVoucherParty1);						
				}
				if(clTypeVoucherParty2.equalsIgnoreCase("Credit")){
					oldClBalanceVoucherParty2 = ZERO.subtract(oldClBalanceVoucherParty2);						
				}
				
				BigDecimal finalClBalanceVoucherdParty1 = oldClBalanceVoucherParty1.add(possalesOld.getVoucherAmount());
				BigDecimal finalClBalanceVoucherParty2 = oldClBalanceVoucherParty2.subtract(posSalesCmd.getPossales().getVoucherAmount());
								
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
				
				ledgerDao.updateLedgerPartyBalance(finalClBalanceVoucherdParty1, finalClTypeParty1, possalesOld.getCustomerName());
				ledgerDao.updateLedgerPartyBalance(finalClBalanceVoucherParty2, finalClTypeParty2, posSalesCmd.getPossales().getCustomerName());
			}else if(!posSalesCmd.getPossales().getCustomerName().equals("Walk-in")) {
				//********************* Voucher Party balance Update : if - Same Customer Names **************************//

				List <Ledger> ledgerListVoucherParty = ledgerDao.searchLedger(possalesOld.getCustomerName());
				BigDecimal oldClBalanceVoucherParty = ledgerListVoucherParty.get(0).getClosingTotalBalance();
				String clTypeVoucherParty = ledgerListVoucherParty.get(0).getClosingTotalType();
				
				if(clTypeVoucherParty.equalsIgnoreCase("Credit")) {
					oldClBalanceVoucherParty = ZERO.subtract(oldClBalanceVoucherParty);
				}
				
				BigDecimal dropClBalanceVoucherParty = oldClBalanceVoucherParty.add(possalesOld.getVoucherAmount());
				BigDecimal finalClBalanceVoucherParty  = dropClBalanceVoucherParty.subtract(posSalesCmd.getPossales().getVoucherAmount());
				
				if(finalClBalanceVoucherParty.signum() == -1){
					finalClType = "Credit";
					finalClBalanceVoucherParty = finalClBalanceVoucherParty.multiply(CONVERT);
				}
				else{
					finalClType = "Debit";						
				}
					
				ledgerDao.updateLedgerPartyBalance(finalClBalanceVoucherParty, finalClType, possalesOld.getCustomerName());					
			
			
			////////////////////////////////////////* Voucher2. Voucher List Ledger Update *//////////////////////////////////
			List <Ledger> ledgerListVoucherLedger = ledgerDao.searchLedger(possalesOld.getVoucherList());
			
			if(!ledgerListVoucherLedger.isEmpty())
			{ //Initially voucher selected
					BigDecimal oldClBalanceVoucherLedger = ledgerListVoucherLedger.get(0).getClosingTotalBalance();
					String clTypeVoucherLedger = ledgerListVoucherLedger.get(0).getClosingTotalType();
				
					if(clTypeVoucherLedger.equalsIgnoreCase("Credit")) {
						oldClBalanceVoucherLedger = ZERO.subtract(oldClBalanceVoucherLedger);
					}
					
					if(possalesOld.getVoucherList().equals(posSalesCmd.getPossales().getVoucherList()) )
					{	 //************************************ If the Bank Names are same	********************************//
						BigDecimal dropClBalanceVoucherLedger = oldClBalanceVoucherLedger.subtract(possalesOld.getVoucherAmount());
						BigDecimal finalClBalanceVoucherLedger  = dropClBalanceVoucherLedger.add(posSalesCmd.getPossales().getVoucherAmount());
						
						if(finalClBalanceVoucherLedger.signum() == -1){
							finalClType = "Credit";
							finalClBalanceVoucherLedger = finalClBalanceVoucherLedger.multiply(CONVERT);
						}else{
							finalClType = "Debit";						
						}
						ledgerDao.updateLedgerPartyBalance(finalClBalanceVoucherLedger, finalClType, possalesOld.getVoucherList());
					}else {	/* ************************************B0. If the Bank A/c Name changes for Voucher .***************/
						/* B1. For old Bank A/c for Voucher */ // Subtract Old Voucher Amount in ledger
													
						BigDecimal finalOldBankLedgerVoucherAmt = oldClBalanceVoucherLedger.subtract(possalesOld.getVoucherAmount());
						if(finalOldBankLedgerVoucherAmt.signum() == -1){
							finalClType = "Credit";
							finalOldBankLedgerVoucherAmt = finalOldBankLedgerVoucherAmt.multiply(CONVERT);
						}else{
							finalClType = "Debit";						
						}
						ledgerDao.updateLedgerPartyBalance(finalOldBankLedgerVoucherAmt, finalClType, possalesOld.getVoucherList());
							
						/* B2. For New Bank A/c for Voucher */ //Add new Voucher amount in ledger
						List <Ledger> ledgerNewBankVoucherLedger = ledgerDao.searchLedger(posSalesCmd.getPossales().getVoucherList());
						BigDecimal NewBankLedgerVoucherAmt = ledgerNewBankVoucherLedger.get(0).getClosingTotalBalance();
						String clNewBankLedgerVoucherType = ledgerNewBankVoucherLedger.get(0).getClosingTotalType();
						
						if(clNewBankLedgerVoucherType.equalsIgnoreCase("Credit")){
							NewBankLedgerVoucherAmt = ZERO.subtract(NewBankLedgerVoucherAmt);						
						}
						BigDecimal finalNewBankLedgerVoucherAmt = NewBankLedgerVoucherAmt.add(posSalesCmd.getPossales().getVoucherAmount());
								
						if(finalNewBankLedgerVoucherAmt.signum() == -1){
							finalClType = "Credit";
							finalNewBankLedgerVoucherAmt = finalNewBankLedgerVoucherAmt.multiply(CONVERT);
						}else{
							finalClType = "Debit";						
						}
						ledgerDao.updateLedgerPartyBalance(finalNewBankLedgerVoucherAmt, finalClType, posSalesCmd.getPossales().getVoucherList());
					}
				}else if(possalesOld.getVoucherAmount().signum() == 0 && posSalesCmd.getPossales().getVoucherAmount().signum() == 1 ){
					// On Update added Voucher Option
					List <Ledger> ledgerNewBankVoucherLedger = ledgerDao.searchLedger(posSalesCmd.getPossales().getVoucherList());
					BigDecimal NewBankLedgerVoucherAmt = ledgerNewBankVoucherLedger.get(0).getClosingTotalBalance();
					String clNewBankLedgerVoucherType = ledgerNewBankVoucherLedger.get(0).getClosingTotalType();
					
					if(clNewBankLedgerVoucherType.equalsIgnoreCase("Credit")){
						NewBankLedgerVoucherAmt = ZERO.subtract(NewBankLedgerVoucherAmt);						
					}
					BigDecimal finalNewBankLedgerVoucherAmt = NewBankLedgerVoucherAmt.add(posSalesCmd.getPossales().getVoucherAmount());
							
					if(finalNewBankLedgerVoucherAmt.signum() == -1){
						finalClType = "Credit";
						finalNewBankLedgerVoucherAmt = finalNewBankLedgerVoucherAmt.multiply(CONVERT);
					}else{
						finalClType = "Debit";						
					}
					ledgerDao.updateLedgerPartyBalance(finalNewBankLedgerVoucherAmt, finalClType, posSalesCmd.getPossales().getVoucherList());
				}
					
				
				/* C3.Journal Entry Update for Voucher */
			List<Journal> jrnlPOSSalesV = journalDao.getJournalUpdateSales("POS Sales Voucher Receipt","POS"+possalesOld.getPosSalesId());
			if(!jrnlPOSSalesV.isEmpty()){	
				if(possalesOld.getVoucherAmount().signum() == 1 && posSalesCmd.getPossales().getVoucherAmount().signum() == 0){
					journalDao.deleteJournal(jrnlPOSSalesV.get(0)); // Delete Entry
				}else if(posSalesCmd.getPossales().getVoucherAmount().signum() == 1){
				jrnlPOSSalesV.get(0).setDebitAmount(posSalesCmd.getPossales().getVoucherAmount());
				jrnlPOSSalesV.get(0).setCreditAmount(posSalesCmd.getPossales().getVoucherAmount());
				jrnlPOSSalesV.get(0).setJournalDate(posSalesCmd.getPossales().getSalesdate());
				jrnlPOSSalesV.get(0).setCreditAccountName(posSalesCmd.getPossales().getCustomerName());
				jrnlPOSSalesV.get(0).setDebitAccountName(posSalesCmd.getPossales().getVoucherList());
				journalDao.updateJournal(jrnlPOSSalesV.get(0));
				}
			}else if(jrnlPOSSalesV.isEmpty() && posSalesCmd.getPossales().getVoucherAmount().signum()==1){
				
				//********** new Journal Entry Voucher Amount 
			     jrnl=new Journal();
			     jrnl.setJournalNO(getJournalNumber(jrnl));
			     jrnl.setJournalType("POS Sales Voucher Receipt");
			     jrnl.setJournalDate(posSalesCmd.getPossales().getSalesdate());
			     jrnl.setTransactionId("POS"+posSalesCmd.getPossales().getPosSalesId());
			     
			     // Set Account group code as Debit code
			 	 ledgerGroupCode = ledgerDao.getLedgerAccountCode(posSalesCmd.getPossales().getVoucherList());			
			     jrnl.setDebitCode(ledgerGroupCode.get(0).toString());	
			     jrnl.setDebitAccountName(posSalesCmd.getPossales().getVoucherList());
			     
			     //Set Account group code as Credit code
				 ledgerGroupCode = ledgerDao.getLedgerAccountCode(posSalesCmd.getPossales().getCustomerName());			
				 jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
			     jrnl.setCreditAccountName(posSalesCmd.getPossales().getCustomerName());
			     
			     jrnl.setDebitAmount(posSalesCmd.getPossales().getVoucherAmount());
			     jrnl.setCreditAmount(posSalesCmd.getPossales().getVoucherAmount());
			     jrnl.setNarration("Bill No"+posSalesCmd.getPossales().getBillNo());
			     journalDao.insertJournal(jrnl);     
			}
						}
	}
		posSalesDao.updatePOSSales(posSalesCmd.getPossales());
		
		return new ModelAndView("redirect:possalesList.htm");
		
	}	
	//List of Possales
			@RequestMapping(value="/possalesList.htm",method=RequestMethod.GET)
			public String PosSalesList(ModelMap model)
			{
				List<POSSalesItem> posSalesItemList = posSalesDao.listSalesItem();
				model.addAttribute("possalesList", posSalesItemList);			
				return "possalesList";
			}
	
	//Canceling request mapping				
			@RequestMapping(value="/formPOSsales.htm",method=RequestMethod.POST, params="cancel") 
			public String cancelForm()
			{
				return "redirect:possalesList.htm";
			}
			
			
			/** Estimate Sales Cancel Invoice **/
			@RequestMapping(value="/formPOSsales", method = RequestMethod.POST, params = "cancelSales")
			public ModelAndView cancelSales(@ModelAttribute("POSsales")PSales possales, BindingResult result, ModelMap model, SessionStatus stauts)
			{
				//Data from Database
				POSSales CancelledSales = posSalesDao.getPOSSales(possales.getPossales().getPosSalesId());
				
				if(!CancelledSales.getCustomerName().equals("Walk-in")){			
					
					List<Ledger> listpartyledger=ledgerDao.searchLedger(CancelledSales.getCustomerName());
					String salesBlTypes = listpartyledger.get(0).getClosingTotalType();
					BigDecimal oldBalancePartycardParty=listpartyledger.get(0).getClosingTotalBalance();
					
					if(salesBlTypes.equals("Credit")){
						oldBalancePartycardParty = ZERO.subtract(oldBalancePartycardParty);
					}
					
					BigDecimal finalClosintPartyBal=oldBalancePartycardParty.subtract(CancelledSales.getNetAmount());
					
					if(finalClosintPartyBal.signum() >= 0){
						ledgerDao.updateLedgerPartyBalance(finalClosintPartyBal, "Debit", CancelledSales.getCustomerName());
					}
					else{
						finalClosintPartyBal = finalClosintPartyBal.multiply(CONVERT);
						ledgerDao.updateLedgerPartyBalance(finalClosintPartyBal, "Credit", CancelledSales.getCustomerName());
					}
					
					if(CancelledSales.getCash()!=null){
				    	if(CancelledSales.getCash().equals("Cash") ){
				    		
		    	//******************* party Ledger Updation Ledger
						List<Ledger> ledgername=ledgerDao.searchLedger(CancelledSales.getCustomerName());
						String typeSales=ledgername.get(0).getClosingTotalType();
						BigDecimal oldPartyCashBal=ledgername.get(0).getClosingTotalBalance();
						
						if(typeSales.equals("Credit")){
							oldPartyCashBal = ZERO.subtract(oldPartyCashBal);
						}
						
						BigDecimal finalClcashPartyBal=oldPartyCashBal.add(CancelledSales.getCashAmount());
					if(finalClcashPartyBal.signum() >= 0){
							ledgerDao.updateLedgerPartyBalance(finalClcashPartyBal, "Debit", CancelledSales.getCustomerName());
						}else{
							finalClcashPartyBal = finalClcashPartyBal.multiply(CONVERT);
							ledgerDao.updateLedgerPartyBalance(finalClcashPartyBal, "Credit", CancelledSales.getCustomerName());
						}
				    	}
				 }
				}
				
				 
				 List<POSSalesItem> listpos = possales.getListpossalesItem();
				 for(POSSalesItem oldPosItem:listpos){
					
							String deletedbarcodeId=oldPosItem.getPosItemCode();	 
							posTagItemDao.refillstockEP(deletedbarcodeId);			
						//	posCategoryDao.updateCategorySet(1, tag.getCategoryName());
						
				 }
	
				 try{
						CancelledSales.setBillposType("Cancelled");
						posSalesDao.updatePOSSales(CancelledSales);
					}catch(Exception e){
						System.out.println("EP EX");
					}
				 
				 
				return new ModelAndView("redirect:estimatePOSSalesList.htm",model);
			}				
}
