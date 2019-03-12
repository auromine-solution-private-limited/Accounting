package com.jewellery.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jewellery.dao.LedgerDao;
import com.jewellery.entity.Ledger;

@Component("LedgerValidator")
public class LedgerValidator implements Validator
{
	@Autowired
	private LedgerDao ledgerDao;
	
	public boolean supports(Class<?> clazz){
		return Ledger.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors){
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ledgerName", "ledgerName.required");	
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ledgerDate", "ledgerDate.required");
		
		Ledger l=(Ledger)command;
		Integer id  = 0;
		String ledgerName = l.getLedgerName();
		
		id = l.getLedgerId(); // code starts here to check duplicate for ledger/customer name
		
		List<Ledger> ledList = ledgerDao.searchLedger(ledgerName);	
					
		if(id == null)
		{						
			if(!ledList.isEmpty()){
				errors.rejectValue("ledgerName", "ledgerName.Duplicate");
			}
		}			
		
		/*if(l.getPrimaryPhone().length()<6 || l.getPrimaryPhone().length() > 10) // code for telephone number check
		{
			errors.rejectValue("primaryPhone", "primaryPhone.required");
		}	*/	
	}
}	