package com.jewellery.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.jewellery.entity.SalesOrder;

@Component("SalesOrderValidator")
public class SalesOrderValidator {

	public boolean supports(Class<?> clazz){
		return SalesOrder.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors){
		SalesOrder salesOrder=(SalesOrder) command;
		BigDecimal ZERO = new BigDecimal("0.00");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerName", "customerName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "orderDate", "orderDate.required");	
		
     if(salesOrder.getCashPaymentSO()!=null ){
			if(salesOrder.getCashAmountSO()== null || salesOrder.getCashAmountSO().compareTo(ZERO)==0){
				errors.rejectValue("salesorder","Cash.Amount.Required","Cash Amount Required");
			}
			if(salesOrder.getCashBankSO().length() == 0){
				errors.rejectValue("salesorder","cash.bank.required","Cash  Required");
			}
		}
     
     /** For Card Amount  & Card Bank Name**/
		if(salesOrder.getCardPaymentSO()!=null ){
			if(salesOrder.getCardAmountSO()== null || salesOrder.getCardAmountSO().compareTo(ZERO)==0){
				errors.rejectValue("salesorder","Card.Amount.Required","Card Amount Required");
			}
			if(salesOrder.getCardBankSO().length() == 0){
				errors.rejectValue("salesorder","card.bank.required","Card Bank Required");
			}
		}	
		/** For Cheque Amount  & Cheque Bank Name**/
		if(salesOrder.getChequePaymentSO()!=null ){
			if(salesOrder.getChequeAmountSO()== null || salesOrder.getChequeAmountSO().compareTo(ZERO)==0){
				errors.rejectValue("salesorder","Cheque.Amount.Required","Cheque Amount Required");
			}
			if(salesOrder.getChequeBankSO().length() == 0){
				errors.rejectValue("salesorder","Cheque.bank.required","Cheque Bank Required");
			}
		}	
		
		/** For Voucher Amount  & Voucher Bank Name**/
		if(salesOrder.getVoucherPaymentSO()!=null) {
			if(salesOrder.getVoucherAmountSO()== null || salesOrder.getVoucherAmountSO().compareTo(ZERO)==0){
				errors.rejectValue("voucherAmountSO","Voucher.Amount.Required","Voucher Amount Required");
			}
			if(salesOrder.getVoucherListSO().length() == 0){
				errors.rejectValue("voucherListSO","Voucher.list.required","Please Select Vouchers");
			}
		}

	}
	
	
}
