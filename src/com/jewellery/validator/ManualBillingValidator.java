package com.jewellery.validator;

//import java.util.List;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.jewellery.entity.ManBillRowList;
import com.jewellery.entity.ManualBilling;

@Component("ManualBillingValidator")
public class ManualBillingValidator {

	@SuppressWarnings("unchecked")
	public void validate(Object command1, Object command2, Errors errors) {
		
		ManualBilling manualBilling = (ManualBilling) command1;
		List<ManBillRowList> manBillRowList = (List<ManBillRowList>) command2;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "manBill.mBDate", "MB.Date.required", "Date Field Cannot be Empty !"); 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "manBill.mBillNo", "MB.mBillNo.required", "Bill No Field Cannot be Empty !"); 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "manBill.boardRate", "MB.boardRate.required", "Board Rate Field Cannot be Empty !"); 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "manBill.customerName", "MB.customerName.required", "Customer Name Field Cannot be Empty !");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "manBill.totalAmount", "MB.totalAmount.required", "Total Amount Field Cannot be Empty !");
		
		if(manualBilling.getBoardRate().signum() <= 0){
			errors.rejectValue("manBill.boardRate", "MB.boardRate.Zero", "Board Rate Field Cannot be ZERO or Negative !");
		}
		if(manualBilling.getTotalAmount().signum() <= 0){
			errors.rejectValue("manBill.totalAmount", "MB.totalAmount.Zero", "Total Amount Field Cannot be ZERO or Negative !");
		}
		
		if((manBillRowList.get(0).getDescription() == null || manBillRowList.get(0).getDescription().length() == 0 ) && (manBillRowList.get(0).getQty() == 0) && (manBillRowList.get(0).getGrossWeight() == null || manBillRowList.get(0).getGrossWeight().signum() == 0 ) && (manBillRowList.get(0).getAmount() == null || manBillRowList.get(0).getAmount().signum() == 0 )){
			errors.rejectValue("manBill","MB.Single.Row.Mandatory", "Atleast one Item detail is Required !");
		}
		
	}

}
