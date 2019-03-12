package com.jewellery.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jewellery.dao.CompanyInfoDao;
import com.jewellery.entity.CompanyInfo;

@Controller
public class FormCompanyInfoController {
	
	@Autowired
	private CompanyInfoDao companyInfoDao;
	
	/** For initial form load */
	@RequestMapping(value = "/CompanyDetail.htm", method = RequestMethod.GET)
	public ModelAndView showform(@ModelAttribute("companyinfo") CompanyInfo cinfo,ModelMap model) {
		
		if (companyInfoDao.listCompanyInfo().isEmpty()) {
			return new ModelAndView("formCompanyInfo"); 		/** if entity is empty it allows to open form and can be saved */
		} else {
			return new ModelAndView("redirect:viewCompanyInfo.htm", model); /** if entity is not empty it allows to open form and can be saved */ 
		}
	}	
	
	/** for insert button*/
	@RequestMapping(value="/saveCompanyInfo.htm",method=RequestMethod.POST,params="insert")
	public ModelAndView saveForm(@ModelAttribute("companyinfo") CompanyInfo cinfo,ModelMap model){
		
		if (companyInfoDao.listCompanyInfo().isEmpty()) {
			companyInfoDao.insertCompanyInfo(cinfo);
			return new ModelAndView("redirect:alluser.htm");  /** if entity is not empty it opens the saved info */
		}
		return new ModelAndView("redirect:alluser.htm",model);
	}
	
	/** for update button*/
	@RequestMapping(value="/saveCompanyInfo.htm",method=RequestMethod.POST,params="update")
	public ModelAndView updateForm(@ModelAttribute("companyinfo") CompanyInfo cinfo,ModelMap model)
	{
		companyInfoDao.updateCompanyInfo(cinfo);
		return new ModelAndView("redirect:alluser.htm",model);  /** if entity is not empty it opens the saved info */
	}
	
	/** view mode*/
	@RequestMapping(value="/viewCompanyInfo.htm",method=RequestMethod.GET)
	public ModelAndView viewForm(@ModelAttribute("companyinfo") CompanyInfo cinfo,ModelMap model){
		model.addAttribute("companyinfo", companyInfoDao.listCompanyInfo().get(0));
		return new ModelAndView("formCompanyInfo",model);
	}
	/** for cancel button*/
	@RequestMapping(value="/saveCompanyInfo.htm",method=RequestMethod.POST,params="cancel")
	public ModelAndView cancelForm(@ModelAttribute("companyinfo") CompanyInfo cinfo){
		return new ModelAndView("redirect:alluser.htm");
	}

}
