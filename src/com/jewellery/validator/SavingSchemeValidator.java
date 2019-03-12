package com.jewellery.validator;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.jewellery.dao.SavingSchemeDao;
import com.jewellery.entity.SavingScheme;

@Component("SavingSchemeValidatior")
public class SavingSchemeValidator{
	
	@Autowired
	private SavingSchemeDao savingschemeDao;
		
	public boolean supports(Class<?> clazz){
		return SavingScheme.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors){
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "schemeName", "schemeName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "schemeType", "schemeType.required");	
		
	
		SavingScheme savingObj = (SavingScheme)command;		
		Integer id  = 0;
		String schemeName = savingObj.getSchemeName();
		
		if(savingObj.getSchemeType().equals("Amount") && savingObj.getSchemeInAmount().signum() == 0){
			errors.rejectValue("schemeInAmount", "schemeAmount.required");
		}
		
		
		if(savingObj.getSchemeType().equals("Gold") && savingObj.getSchemeInGrams().signum() == 0){
			errors.rejectValue("schemeInGrams", "schemeGrams.required");
		}
				
		id = savingObj.getSaving_schemeId(); // code starts here to check duplicate for scheme name
		
		List<SavingScheme> schemeList = savingschemeDao.searchSavingScheme(schemeName);
					
		if(id == null)
		{						
			if(!schemeList.isEmpty()){
				errors.rejectValue("schemeName", "schemeName.Duplicate");
			}
		}		
	}	
}
