package com.jewellery.validator;

import java.util.List;
import com.jewellery.entity.POSCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.jewellery.dao.POSCategoryDao;

@Component
public class POSCategoryValidator {
 
	@Autowired
	private POSCategoryDao poscategoryDao;
	
	public boolean supports(Class<?> clazz){
		return POSCategory.class.isAssignableFrom(clazz);		
	}
	
	public void validate(Object command, Errors errors){
	
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName", "poscategoryName.required");		
		
		POSCategory existingCategory=(POSCategory)command;
		String categoryName=existingCategory.getCategoryName();
		Integer id  = 0;
		id=existingCategory.getCategoryId();
		
		List<POSCategory> ListCategory = poscategoryDao.searchExistingCategory(categoryName);
		
		if(id == null)
		{
			if(!ListCategory.isEmpty()){
				errors.rejectValue("categoryName", "poscategoryName.duplicate");
			}
		}
	}
}
