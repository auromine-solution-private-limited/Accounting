package com.jewellery.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jewellery.entity.Accounts;

@Component("FormAccountsValidator")
public class FormAccountsValidator implements Validator {
	
	public boolean supports(Class<?> clazz) {
		return Accounts.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors)
	{
		
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountName", "accountName.required");
	
	}
		
}
