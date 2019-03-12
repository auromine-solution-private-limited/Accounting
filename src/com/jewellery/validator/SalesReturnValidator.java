package com.jewellery.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.jewellery.entity.Sales;

@Component("SalesReturnValidator")
public class SalesReturnValidator {
	
	public boolean supports(Class<?> clazz) {
		return Sales.class.isAssignableFrom(clazz);  
	}

	public void validate(Object command, Errors errors) {
		
		Sales salesObj = (Sales) command;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerName", "CustomerName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salesmanName", "salesman_Name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemCode", "message.itemCode.bullionfilled");
		
		if(salesObj.getItemCode() != ""){
			if(salesObj.getGrossWeight().signum() == 0){
				errors.rejectValue("grossWeight", "Weight.required");
			}
			
			if(salesObj.getBullionRate().signum() == 0){
				errors.rejectValue("bullionRate", "Rate.required");
			}
		}
		
		if(salesObj.getItemCode1() != ""){
			if(salesObj.getGrossWeight1().signum() == 0){
				errors.rejectValue("grossWeight1", "returnWeight.required");
			}
			
			if(salesObj.getBullionRate1().signum() == 0){
				errors.rejectValue("bullionRate1", "returnRate.required");
			}
		}
		
		if(salesObj.getItemCode2() != ""){
			if(salesObj.getGrossWeight2().signum() == 0){
				errors.rejectValue("grossWeight2", "returnWeight1.required");
			}
			
			if(salesObj.getBullionRate2().signum() == 0){
				errors.rejectValue("bullionRate2", "returnRate1.required");
			}
		}
		
		if(salesObj.getItemCode3() != ""){
			if(salesObj.getGrossWeight3().signum() == 0){
				errors.rejectValue("grossWeight3", "returnWeight2.required");
			}
			
			if(salesObj.getBullionRate3().signum() == 0){
				errors.rejectValue("bullionRate3", "returnRate2.required");
			}
		
		}
		if(salesObj.getBillAmount().signum() == 0){
			errors.rejectValue("billAmount", "billAmount.required");
		}
	}
}
