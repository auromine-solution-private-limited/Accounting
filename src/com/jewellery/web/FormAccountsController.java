package com.jewellery.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import com.jewellery.dao.AccountsDao;
import com.jewellery.dao.RateMasterDao;
import com.jewellery.entity.Accounts;
import com.jewellery.validator.FormAccountsValidator;


@Controller
public class FormAccountsController{
@Autowired
private AccountsDao accountsDao;
@Autowired
private RateMasterDao ratemasterDao;
@Autowired
private FormAccountsValidator accountsvalidatior;

	//mapping for formaccounts
@RequestMapping(value="/formaccounts",method=RequestMethod.GET)
public String getform(@ModelAttribute("accountid")Accounts accounts){
	ModelMap model = new ModelMap();
	model.put("boardrate", ratemasterDao.searchRateMaster());	
	return "formaccounts";
}

//Insert method 
@RequestMapping(value="/formaccounts.htm",method=RequestMethod.POST,params="insert")
public  String insert(@ModelAttribute("accountid")Accounts accounts,BindingResult result,SessionStatus status,Model model){
	
	accountsvalidatior.validate(accounts, result);
	if (result.hasErrors()) {
		ModelMap map = new ModelMap();
		map.put("command", accounts);
	return "formaccounts";
			
	}
	
	Integer accountId= 0;
	List<Accounts> AccountNoList = accountsDao.getAccountId();
	if(!AccountNoList.isEmpty()){
		 accountId = AccountNoList.get(0).getaccountId();	 	
	}
	if(accountId>=18){
		System.out.println("Maxim limit");
		model.addAttribute("MaximLimit","Cannot Create more than 18" );
		
	}else
	{
	accountsDao.insertAccounts(accounts);
	status.setComplete();
	}
	
	return "redirect:accounts.htm";			

}
//Canceling request mapping				
	@RequestMapping(value="/formaccounts.htm",method=RequestMethod.POST, params="cancel") 
	public String cancelForm(@ModelAttribute("AccountId")Accounts accounts)
	{
		
		return "redirect:accounts.htm";
	}
}

