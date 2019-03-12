package com.jewellery.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.jewellery.entity.JewelFix;

@Component("JewelFixValidator")
public class JewelFixValidator{
	
	public boolean supports(Class<?> clazz){
		return JewelFix.class.isAssignableFrom(clazz);			
	}

	 public void validate(Object command, Errors errors)
	 {				 
		JewelFix jf =(JewelFix)command;			 
		 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerName", "customerName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "smithName", "smith_Name.required");		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "metalType", "metalType.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numberOfPieces","numberOfPieces.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "smithCost","smithCost.required","smith cost can't be empty");
		
		if(jf.getNoOfstone() == null || jf.getNoOfstone().equals("")){
			errors.rejectValue("noOfstone", "noOfstone.required");
		}
											
		if(jf.getIssuedGrossWeight().signum() == 0) {
			errors.rejectValue("issuedGrossWeight", "issuedGrossWeight.required");
		}
		
		if(jf.getIssueDate() == null || jf.getIssueDate().equals("")){
			errors.rejectValue("issueDate", "issueDate.required", "*Received from Customer Date Required.");
		}
			
		if(jf.getStatus().equals("Delivered")) {				
				
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "receivedDate", "receivedDate.required");
				
			if( jf.getWastage().signum()!=0 || jf.getGrossWtAdded().signum()!=0 ) {
				if(jf.getRate().signum() == 0)
				{
					errors.rejectValue("rate", "rate.required");
				}
			}
			
			if(jf.getTotalCost().signum() <= 0){
				errors.rejectValue("totalCost","totalCost.required","*Total Cost Can't be ZERO");
			}
			
			/*if(jf.getSmithCost().signum() == 0)
			{
				errors.rejectValue("rate", "smith_Cost.required");
			}*/
		}			
	}	
}
