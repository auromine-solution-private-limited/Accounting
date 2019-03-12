package com.jewellery.validator;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.jewellery.dao.PostagItemDao;
import com.jewellery.entity.POSSales;
import com.jewellery.entity.POSSalesItem; 
import com.jewellery.entity.PostagItem;

@Component("POSSalesValidator")
public class POSSalesValidator implements Validator{
	@Autowired
	PostagItemDao postagitemdao;
		 
	BigDecimal ZERO = new BigDecimal("0.00");
	public void validateUpdate(Object command ,Object command3,Errors errors){
		POSSales oldpos=(POSSales) command3;
		POSSales pos = (POSSales)command;

		//regular to walk in walkin validation
		if(!oldpos.getCustomerName().equals("Walk-in") && pos.getCustomerName().equals("Walk-in")){
			errors.rejectValue("possales", "POP.CustomerName.regular.Walkin", "Can't Update from Regular to Walk-in Customer Name");			
		}
		
		//walkin to regular validation
		if(oldpos.getCustomerName().equals("Walk-in") && !pos.getCustomerName().equals("Walk-in")){
			errors.rejectValue("possales", "POP.CustomerName.regular.Walkin", "Can't Update from Walk-in to Regular Customer Name");			
		}

	}
	
	@SuppressWarnings("unchecked")
	public void validate(Object command,Object command2,Errors errors){
		
		POSSales pos = (POSSales)command;
		List<POSSalesItem> popSalesItemList = (List<POSSalesItem>) command2;
		
		BigDecimal ZERO = new BigDecimal("0.00");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "possales.customerName", "possales.customerName.empty"," Customer	");
		if(pos.getCustomerName().isEmpty() || pos.getCustomerName()==null){
			errors.rejectValue("possales", "possales.customerName.empty", "Customer Name Can't be Empty");
		}
		
		
		//credit sales validtion 
				if(pos.getCustomerName().equalsIgnoreCase("Walk-in") && pos.getWalkinCustomer()!=null){
					if(pos.getNetAmount().signum()  < pos.getTotalAmountReceived().signum() || pos.getTotalAmountReceived().signum() < pos.getNetAmount().signum()){
						errors.rejectValue("possales", "CreditSales.not", "**Credit Sales Not Allowed For Walk-in Customer !");
					}
				}
		// walk in customer validation 
		if(pos.getCustomerName()=="Walk-in") {
			if(pos.getWalkinCustomer().isEmpty() || pos.getWalkinCustomer()==null){
				errors.rejectValue("possales", "possales.WalkcustomerName.empty", "Walk IN Customer Name Can't be Empty");
			}
		}
		
		// for date validation 
		if(pos.getSalesdate()==null  ){
			errors.rejectValue("possales.salesdate", "possales.Date.empty", "Sales Date Field can't be Empty !!!");
		}
		
		/** Dynamic Row Validations */
		if(!popSalesItemList.isEmpty()){
			//System.out.println("Inside Dynamic row validation::::::::::");
			int RowCount = 0;
			
			for(POSSalesItem pospSalesItem : popSalesItemList) {
			//	System.out.println("inside ");
				if(pospSalesItem.POSdeleted == false){
				if(pospSalesItem.getPosItemCode()==null || pospSalesItem.getPosItemCode().length()==0 ){
					errors.rejectValue("possales", "ItemCode.empty", "ItemCode Is Empty !!!");
				}
				
				/** Invalid itemCode Name in  */
				if(pospSalesItem.getPosItemCode().length() != 0) {
					List<PostagItem> positemCodeFound = postagitemdao.getItemCode(pospSalesItem.getPosItemCode());
					if(positemCodeFound.size() == 0){
						errors.rejectValue("possales", "POP.Itemcode.invalid", "Invalid ItemCode  on Row id "+RowCount+"");
					}					
				}
				
				if(pospSalesItem.getQuantity()==null ||  pospSalesItem.getTotalPieces()==null ||pospSalesItem.getQuantity()==0 ||pospSalesItem.getTotalPieces()==0){
					errors.rejectValue("possales", "Stock.empty", "Stock Not Available !!!");
				}
				
				// amount validaton 
				if(pospSalesItem.getSalesAmount()==null ||  pospSalesItem.getSalesAmount().signum()==0){
					errors.rejectValue("possales", "amount.empty", "Amount cannot be empty  !!!");
				}
				
				//rate validation 
		
				if(pospSalesItem.getSalesRate()==null || pospSalesItem.getSalesRate().signum()==0){
					errors.rejectValue("possales", "rate.empty", "Rate is Required!!");
				}
				
				RowCount++;
			}
			}
		}
	
		/**** validation for payment mode ****/
		/** For Cash Amount **/
		if(pos.getCash() != null) {
			if(pos.getCashAmount() == null ||pos.getCashAmount().compareTo(ZERO)==0 ){
				errors.rejectValue("possales","Cash.Amount.Required","Cash Amount Required");
			}
			
		}	
		
		/** For Card Amount  & Card Bank Name**/
		if(pos.getCard()!=null ){
			
			if(pos.getCardAmount()== null || pos.getCardAmount().compareTo(ZERO)==0){
				errors.rejectValue("possales","Card.Amount.Required","Card Amount Required");
			}else if(pos.getSalesType().equalsIgnoreCase("Estimate POS Sales")){
				errors.rejectValue("possales","Estimate.Card.Amount","Card Payment restricted for Estimate POS Sales.");
			}
			
			if(pos.getCardBank().length() == 0){
				errors.rejectValue("possales","card.bank.required","Card Bank Required");
			}
		}	
		
		/** For Cheque Amount  & Cheque Bank Name**/
		if(pos.getCheque() !=null ) {
			
			if(pos.getChequeAmount() == null || pos.getChequeAmount().compareTo(ZERO)==0){
				errors.rejectValue("possales","Cheque.Amount.Required","Cheque Amount Required");
			}else if(pos.getSalesType().equalsIgnoreCase("Estimate POS Sales")){
				errors.rejectValue("possales","Estimate.Cheque.Amount","Cheque Payment restricted for Estimate POS Sales.");
			}
			
			
			if(pos.getChequeBank().length() == 0){
				errors.rejectValue("possales","Cheque.bank.required","Cheque Bank Required");
			}
		}	
		
		//cash negative validation 
		if(pos.getCashAmount().signum() < 0){
			errors.rejectValue("possales", "cash.negtive", "Incorrect Received amount");
		}
		
		/** For Voucher Amount  & Voucher Bank Name**/
		if(pos.getGiftVoucher()!=null) {
			
			if(pos.getVoucherAmount()== null || pos.getVoucherAmount().compareTo(ZERO)==0){
				errors.rejectValue("possales","Voucher.Amount.Required","Voucher Amount Required");
			}else if(pos.getSalesType().equalsIgnoreCase("Estimate POS Sales")){
				errors.rejectValue("possales","Estimate.Voucher.Amount","Voucher Payment restricted for Estimate POS Sales.");
			}
			
			if(pos.getVoucherList().length() == 0){
				errors.rejectValue("possales","Voucher.list.required","Please Select Vouchers");
			}
		}
		
		if(pos.getNetAmount().signum() < 0){
			errors.rejectValue("possales", "Baltopay.negative", "Balance to Pay can't be less than ZERO !");
		}
		
			//System.out.println(":::::::::Server side Validations Completed successfully");
	
		}


	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		
	}
	
		
	}
	
