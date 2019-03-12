package com.jewellery.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jewellery.dao.GenPrivateKeysDao;
import com.jewellery.entity.ProductInfo;

@Component
public class ProductInfoValidator implements Validator{
	
	@Autowired
	GenPrivateKeysDao genPrivateKeysDao;

	public void validateRegisterCompany(Errors errors){
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productName", "productName.required", "Product Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productversion", "productversion.required", "Product Version Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "installationDate", "installationDate.required", "Installation Date Required");
	}

	public void validate(Object command, String rname, Errors errors) {
		
		ProductInfo productInfo = (ProductInfo) command;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productName", "productName.required", "Product Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productversion", "productversion.required", "Product Version Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "installationDate", "installationDate.required", "Installation Date Required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "private1", "private1.required", "Private key 1 Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "private2", "private2.required", "Private Key 2 Required");
		
		if(productInfo.getVersionType().equalsIgnoreCase("Full Version")){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "plk", "plk.required", "Product Liscence Key Required");
		}else{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "plk", "plk.required", "Product Trial Liscence Key Required");
		}
		
		if(productInfo.getPlk().length() != 0){
			if(!genPrivateKeysDao.getMainVal(productInfo.getVersionType(), rname).equals(productInfo.getPlk().toString())){
				errors.rejectValue("plk", "plk.invalid", "Warning Invalid Liscense Key, Invalid Attempts will Self Crash your Application Setup !" );
			}
		}
	}


	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
