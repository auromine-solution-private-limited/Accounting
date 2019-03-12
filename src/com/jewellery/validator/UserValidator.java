package com.jewellery.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.jewellery.dao.UserloginDao;
import com.jewellery.entity.Userlogin;

@SuppressWarnings("unused")
@Component("UserValidator")
public class UserValidator 
{
	@Autowired
	private UserloginDao userloginDao; 
	public boolean supports(Class<?> clazz){ 
		return Userlogin.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors){
		Userlogin userLogin=(Userlogin)command;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "userName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "confirmPassword.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "fullName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rollName", "rollName.required");
		String userName=userLogin.getUserName();
		
		Userlogin user=(Userlogin)command;
		Integer id  = 0;
		String userName1 = user.getUserName();
		
		if(!user.getPassword().equals(user.getConfirmPassword())){
			errors.rejectValue("confirmPassword", "notmatch.password");			
		}
		
		id = user.getId(); // code to check duplicate for ledger/customer name
		
		List<Userlogin> userlist = userloginDao.searchUserName(userName1);	
					
		if(id == null)
		{						
			if(!userlist.isEmpty()){
				errors.rejectValue("userName", "userName.duplicate");
			}
		}			
		
			
	}
}	
