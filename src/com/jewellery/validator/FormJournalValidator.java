package com.jewellery.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.jewellery.entity.Journal;
@Component("FormJournalValidator")
public class FormJournalValidator {
	public boolean supports(Class<?> clazz) {
		return Journal.class.isAssignableFrom(clazz);
	}
	public void validate(Object command, Errors errors)
	{ 
		Journal jObj = (Journal)command;
		/*if(jObj.getJournalType().equals("Receipt"))
		{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "receiptType", "receiptType.required");	
		}*/
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "journalDate", "journalDate.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "debitAccountName", "debitAccountName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditAccountName", "creditAccountName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "journalType", "journalType.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "debitAmount", "debitAmount.required");
		if(jObj.getDebitAmount().signum() == 0){
			errors.rejectValue( "debitAmount", "debitAmount.required");				
		}
		
		if(jObj.getCreditAmount().signum() == 0){
			errors.rejectValue("creditAmount", "creditAmount.required");				
		}
		if(jObj.getDebitAccountName().equalsIgnoreCase(jObj.getCreditAccountName())){
			errors.rejectValue("debitAccountName", "debitAccountName.creditAccountName","Debit Account and Credit Account Cannot be Same Ledger Name !");
		}
	}
}
