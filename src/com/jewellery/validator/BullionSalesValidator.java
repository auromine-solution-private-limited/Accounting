package com.jewellery.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;


import com.jewellery.entity.Sales;

@Component("BullionSalesValidator")
public class BullionSalesValidator {
	
		
	public boolean supports(Class<?> clazz){		
		return Sales.class.isAssignableFrom(clazz);				
	}

	public void validate(Object command, Errors errors) {
		System.out.println("at update:::");
		Sales salesObj = (Sales) command;	
		BigDecimal bullionGross = salesObj.getGrossWeight(); 
		BigDecimal bullionAmt = salesObj.getBillAmount();
		BigDecimal balAmount = salesObj.getBalToPay();
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemCode", "message.itemCode.bullionfilled");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerName", "CustomerName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salesmanName", "salesman_Name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "grossWeight", "bullionWeight.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bullionRate", "bullionRate.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "billAmount", "billAmount.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roundOff", "roundOff.required");
			
		if(salesObj.getSalesDate()==null || salesObj.getSalesDate().toString().isEmpty()){
					errors.rejectValue("salesDate", "bullionSalesDate.required");
				}
				
		if(bullionGross == null || bullionGross.signum() == 0){
			errors.rejectValue("grossWeight", "bullionWeight.required");
		}
		
		if(bullionAmt == null || bullionAmt.signum() == 0){
			errors.rejectValue("billAmount", "billAmount.required");
		}

		if (salesObj.getCustomerName().equals("walk_in")){
			
			if(balAmount.signum() != 0) {
				errors.rejectValue("billType", "billType.Walkin");
			}
				
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,"walkIn_Name", "walkIn_Name.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,"walkIn_City", "walkIn_City.required");
		}		
	}
	
	public void Updatevalidate(Object command1,Object command2, Errors errors) {
		System.out.println("at update Mode:::::::");
		Sales salesOld = (Sales) command1;
		Sales salesNew = (Sales) command2;
		
		if(!salesOld.getCustomerName().equals("walk_in") && salesNew.getCustomerName().equals("walk_in")){
			errors.rejectValue("customerName", "walkin.notAllowed","*Can't be Update Customer Name from Regular Customer to Walkin");
		}else if(salesOld.getCustomerName().equals("walk_in") && !salesNew.getCustomerName().equals("walk_in")){
			errors.rejectValue("customerName", "walkin.notAllowed", "*Can't be Update from Walk-in to Regular Customer ");
		}
	}
	
}


