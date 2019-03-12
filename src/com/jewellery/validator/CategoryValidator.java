package com.jewellery.validator;

import java.util.List;

import com.jewellery.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.jewellery.dao.CategoryDao;
@Component("Subcategory")
public class CategoryValidator {

	@Autowired
	private CategoryDao categoryDao;
	public boolean supports(Class<?> clazz){
		return Category.class.isAssignableFrom(clazz);
		
	}
	public void validate(Object command, Errors errors){
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName", "categoryName.required1");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scheme", "scheme.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "metalType", "metalType.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "metalUsed", "metalUsed.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "baseCategory", "baseCategory.required");
		Category existingCategory=(Category)command;
		
		List<Category> ListCategory=categoryDao.searchExistingCategory(existingCategory.getCategoryName());
			if(!ListCategory.isEmpty()){
				errors.rejectValue("categoryName", "categoryName.duplicate", "CategoryName Already Exists");
		}
	}
	
	public void validateUpdate(Object command,Object command2, Errors errors){
		Category newCategory=(Category)command;
		Category oldCategory=(Category)command2;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName", "categoryName.required1");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scheme", "scheme.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "metalType", "metalType.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "metalUsed", "metalUsed.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "baseCategory", "baseCategory.required");
		if(!newCategory.getCategoryName().equalsIgnoreCase(oldCategory.getCategoryName())){
		List<Category> ListCategory=categoryDao.searchExistingCategory(newCategory.getCategoryName());
			if(!ListCategory.isEmpty()){
				errors.rejectValue("categoryName", "categoryName.duplicate", "CategoryName Already Exists");
		}
		}
	}
}
