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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.jewellery.entity.POSCategory;
import com.jewellery.validator.POSCategoryValidator;
import com.jewellery.dao.POSCategoryDao;


@Controller
public class FormPOSCategoryController {
	
	@Autowired
	private POSCategoryDao poscategoryDao; 
	
	@Autowired
	private POSCategoryValidator poscategoryValidator;

	//List the master pos category page
	@RequestMapping(value="/formPOScategory.htm", method=RequestMethod.GET)
	public String listCategory(@ModelAttribute("posCategory") POSCategory poscategory, Model map) 
	{
		List<POSCategory> poscategoryList = poscategoryDao.listCategoryName();	
		map.addAttribute("categoryList", poscategoryList);
		return "formPOScategory";
	}
	
	
	//Adding new category request mapping
		@RequestMapping(value="/formPOScategory",method=RequestMethod.POST,params="insert")
		public ModelAndView addnewCategory(@ModelAttribute("posCategory") POSCategory poscategory,BindingResult result,SessionStatus status)
		{			
			poscategoryValidator.validate(poscategory, result);
			
			if(result.hasErrors())
			{
				ModelMap map = new ModelMap();
				map.put("command",poscategory);
				map.put("categoryList", poscategoryDao.listCategoryName());
				//map.addAttribute("errorType","insertError");
				return new ModelAndView("formPOScategory", map);
			}
			
			poscategoryDao.insertCategory(poscategory);			
			return new ModelAndView(new RedirectView("formPOScategory.htm"));
		}
}
