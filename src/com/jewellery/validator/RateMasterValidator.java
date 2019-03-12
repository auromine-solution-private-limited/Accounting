package com.jewellery.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.jewellery.entity.RateMaster;

@Component
public class RateMasterValidator {
	public boolean supports(Class<?> clazz){
		return RateMaster.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors){
		
		RateMaster rate=(RateMaster)command;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastUpdateDate", "lastUpdateDate.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "exchangeGold", "exchangeGold.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "exchangeSilver", "exchangeSilver.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "goldBullion", "goldBullion.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "silverBullion", "silverBullion.empty");
		
		BigDecimal goldOrRate = rate.getGoldOrnaments();
			
		if(goldOrRate == null || goldOrRate.signum() == 0 )
		{  
			errors.rejectValue("goldOrnaments", "goldOrnaments.required");			
		}
		if(rate.getSilverOrnaments().signum() == 0)
		{
			errors.rejectValue("silverOrnaments", "silverOrnaments.required");
		}			
	}	
}
