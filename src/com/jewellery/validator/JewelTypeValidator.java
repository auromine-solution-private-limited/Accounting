package com.jewellery.validator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.jewellery.entity.JewelType;
import com.jewellery.dao.JewelTypeDao;
@Component("JewelValidator")
public class JewelTypeValidator {
	
	@Autowired
	private JewelTypeDao jewelTypeDao;
	public boolean supports(Class<?> clazz){
		return JewelType.class.isAssignableFrom(clazz);
	}
	public void validate(Object command, Errors errors){
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jewelName", "jewelName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "metalUsed", "metalUsed.choose");
		JewelType jeweltype=(JewelType)command;
		Integer id  = 0;
		String jewelName=jeweltype.getJewelName();
		id=jeweltype.getJewelTypeId();
		List<JewelType> jewel_List=jewelTypeDao.searchJewel(jewelName);
		if(id == null)
		{
			if(!jewel_List.isEmpty()){
				errors.rejectValue("jewelName", "jewelName.duplicate");
			}
		}
	}
}
