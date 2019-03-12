package com.jewellery.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.jewellery.dao.StartSchemeDao;
import com.jewellery.entity.CardIssue;
import com.jewellery.entity.StartScheme;

@Component("Cardissuevalidator")
public class Cardissuevalidator {
	
	@Autowired
	private StartSchemeDao statartchemeDao;
	
	public boolean supports(Class<?> clazz){
		return CardIssue.class.isAssignableFrom(clazz);
	}
	public void validate(Object command, Errors errors){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerName", "customerName.required_issuecard");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "schemeName", "schemeName.required_issuecard");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfJoining", "dateOfJoining.required_issuecard");
			
		CardIssue cardissue=(CardIssue)command;
		if(cardissue.getSchemeType().equals("Amount") && cardissue.getSchemeInAmount().signum() == 0){
			errors.rejectValue("schemeInAmount", "schemeInAmount.required_issuecard");
		}
		if(cardissue.getSchemeType().equals("Gold") && cardissue.getSchemeInGrams().signum() == 0){
			errors.rejectValue("schemeInGrams", "schemeInGrams.required_issuecard");
		}
		if(cardissue.getInstallment() == null ){
			errors.rejectValue("installment", "installment.required", "No. Installment Should be atleast Zero.");
		}
		
		List<StartScheme> startSchemeList = statartchemeDao.searchStartScheme(cardissue.getSchemeName());
		if(!startSchemeList.isEmpty()){
			Integer schemeDuration = startSchemeList.get(0).getSchemeDuration(); 
			if(cardissue.getInstallment() != null && cardissue.getInstallment() > schemeDuration ){
				errors.rejectValue("installment", "installment.cant.exceed","No of installment should not greater than Scheme Duration");
			}
		}
		
	}
	
	
}
