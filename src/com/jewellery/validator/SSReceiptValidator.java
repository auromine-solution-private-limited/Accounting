package com.jewellery.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.jewellery.dao.CardIssueDao;
import com.jewellery.entity.Saving_SchemeReceipt;

@Component("SSReceiptValidator")
public class SSReceiptValidator {
	
	@Autowired
	CardIssueDao cardIssueDao;
	
	public void validate(Object command,Errors errors) {
		
		Saving_SchemeReceipt ssReceipt = (Saving_SchemeReceipt) command;
		
		//receiptDate
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "receiptDate", "SSR.date.required", "Date field can't be empty !");
		
		//customerName
		if(ssReceipt.getCustomerName().equalsIgnoreCase("Select") || ssReceipt.getCustomerName().equalsIgnoreCase("0") || ssReceipt.getCustomerName().equalsIgnoreCase("") ){
			errors.rejectValue("customerName", "customerName.required", "Please Select Customer Name !");
		}
		
		//SchemeName
		if(ssReceipt.getSchemeName().equalsIgnoreCase("Select") || ssReceipt.getSchemeName().equalsIgnoreCase("0") || ssReceipt.getSchemeName().equalsIgnoreCase("") ){
			errors.rejectValue("schemeName", "schemeName.required", "Scheme Name Required !");
		}
		
		//Card No
		if(ssReceipt.getCardNo().equalsIgnoreCase("Select") || ssReceipt.getCardNo().equalsIgnoreCase("0") || ssReceipt.getSchemeName().equalsIgnoreCase("") ){
			errors.rejectValue("cardNo", "cardNo.required", "Card Number Required !");
		}
		
		//Scheme Grams and Amount
		if(ssReceipt.getSchemeType().equalsIgnoreCase("Gold")){
			if(ssReceipt.getSchemeInGrams() == null || ssReceipt.getSchemeInGrams().signum() == 0 ){
				errors.rejectValue("schemeInGrams", "schemeInGrams.required", "Scheme Grams Required !");
			}
		}else{
			if(ssReceipt.getSchemeInAmount() == null || ssReceipt.getSchemeInAmount().signum() == 0 ){
				errors.rejectValue("schemeInAmount", "schemeInAmount.required", "Scheme Amount Required !");
			} 
		}
		
		//Receipt Amount and Payment Amount
		if(ssReceipt.getFormType().equalsIgnoreCase("Saving Scheme Receipt")){
			if(ssReceipt.getReceiptAmount() == null || ssReceipt.getReceiptAmount().signum() <= 0 ){
				errors.rejectValue("receiptAmount", "receiptAmount.required", "Receipt Amount Required !");
			}
		}else{
			if(ssReceipt.getPaymentAmount() == null || ssReceipt.getPaymentAmount().signum() <= 0 ){
				errors.rejectValue("paymentAmount", "paymentAmount.required", "Payment Amount Required !");
			}
		}
		
		//Debit and Credit Account
		if(ssReceipt.getFormType().equalsIgnoreCase("Saving Scheme Receipt")){
			if(ssReceipt.getDebitAccount().equalsIgnoreCase("Select") || ssReceipt.getDebitAccount().equalsIgnoreCase("0")){
				errors.rejectValue("debitAccount", "debitAccount.required", "Please Select Debit Account Name !");
			}
		}else{
			if(ssReceipt.getCreditAccount().equalsIgnoreCase("Select") ||ssReceipt.getCreditAccount().equalsIgnoreCase("0") ){
				errors.rejectValue("creditAccount", "creditAccount.required", "Please Select Credit Account Name !");
			}
		}
	}
	
	public void validateUpdate(Object command2, Errors errors){
		
		Saving_SchemeReceipt ssReceiptOld = (Saving_SchemeReceipt) command2;
		String OldCardStatus = cardIssueDao.getCardIssueCardNO(ssReceiptOld.getCardNo()).get(0).getStatus();
		
		if(ssReceiptOld.getFormType().equalsIgnoreCase("Saving Scheme Payment")){
			if(!OldCardStatus.equalsIgnoreCase("Cancelled")){
				errors.rejectValue("cardNo","cardNo.Status.false","! Card Status is not 'Cancelled' can't Update.");
			}	
		}else if(ssReceiptOld.getFormType().equalsIgnoreCase("Saving Scheme Receipt")){
			if(OldCardStatus.equalsIgnoreCase("Cancelled")){
				errors.rejectValue("cardNo","cardNo.Status.false","! Card Status is 'Cancelled' can't Update.");
			}
			if(OldCardStatus.equalsIgnoreCase("Finished")){
				errors.rejectValue("cardNo","cardNo.Status.false","! Card Status is 'Finished' can't Update.");
			}
		} 
	}
		

}
