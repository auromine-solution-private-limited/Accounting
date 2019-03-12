package com.jewellery.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jewellery.dao.LedgerDao;
import com.jewellery.entity.Ledger;

@Component("FormLedgerValidator")
public class FormLedgerValidator implements Validator{
	
	@Autowired	
	private LedgerDao ledgerDao;
	
	public boolean supports(Class<?> clazz) {
		return Ledger.class.isAssignableFrom(clazz);
	}	
	
	public void validate(Object command, Errors errors)
	{
		//code for validating bankaccount fields
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ledgerName","ledgerName.required","Ledger Name Required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountGroup","accountGroup.required","Group Type Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "closingTotalBalance","closingTotalBalance.required","Closing Type Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "openingType","openingType.required","Opening Type Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "openingBalance", "openingbalance.required", "Opening Balance Balance Can't Be empty");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "opTotalBalance", "openingbalance.required", "Opening Balance Balance Can't Be empty");
		
		//code for checking duplicate ledgerName
		Ledger leg = (Ledger) command;
		Integer id = 0;
		String ledgerName = leg.getLedgerName();
		id = leg.getLedgerId();

		List<Ledger> ledList = ledgerDao.searchLedger(ledgerName);
			if (id == null)			
			{				
				if (!ledList.isEmpty())
				{
					errors.rejectValue("ledgerName", "ledgerName.Duplicate","ledgerName already exists");
				}	
		
			}	
	}
	
}
