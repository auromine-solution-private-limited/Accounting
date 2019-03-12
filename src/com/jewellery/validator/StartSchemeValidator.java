package com.jewellery.validator;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.jewellery.dao.StartSchemeDao;
import com.jewellery.entity.StartScheme;

@Component("StartSchemeValidatior")
public class StartSchemeValidator{
	
		
	@Autowired
	private StartSchemeDao startschemeDao;
		
	public boolean supports(Class<?> clazz){
		return StartScheme.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors){
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "schemeName", "scheme_Name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "schemeDuration", "schemeDuration.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "schemeStartDate", "schemeStartDate.required");				
		
		Integer id  = 0;
		StartScheme startschemeObj = (StartScheme)command;
		String schemeName =startschemeObj.getSchemeName();
		id = startschemeObj.getStart_schemeId();
	
		List<StartScheme> schemeList = startschemeDao.searchStartScheme(schemeName);
		
		if(!schemeList.isEmpty() && id == null){
			errors.rejectValue("schemeName", "schemeName.Exist");
		}		
		
		if(id != null)
		{		
			StartScheme oldSchemeObj = startschemeDao.getStartScheme(id);
			String scheme_name = oldSchemeObj.getSchemeName();
			
			if(!scheme_name.equals(schemeName) && !schemeList.isEmpty()){				
				errors.rejectValue("schemeName", "schemeName.Exist");						
			}
		}		
						
			
	}	
}
