 package com.jewellery.web;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map; 

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.jewellery.dao.CardIssueDao;
import com.jewellery.dao.SavingSchemeDao;
import com.jewellery.dao.StartSchemeDao;
import com.jewellery.entity.SavingScheme;
import com.jewellery.entity.StartScheme;
import com.jewellery.validator.StartSchemeValidator;


@Controller
public class FormStartSchemeController {
	
	@Autowired
	private StartSchemeDao startschemeDao;
	
	@Autowired
	private SavingSchemeDao savingschemeDao;	
	
	@Autowired
	private CardIssueDao cardIssueDao;
	
	@Autowired
	private StartSchemeValidator startschemeValidator;
		
	@InitBinder
	protected void initBinder(HttpServletRequest request,ServletRequestDataBinder binder) throws Exception {
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class, null, dateEditor);
	}
	
	@RequestMapping(value = "/addstartscheme.htm")
	public String showStartScheme(@ModelAttribute("start_scheme")StartScheme startscheme, ModelMap model){
			
		model.addAttribute("schemename", savingschemeDao.listSavingScheme());
		return "formstartscheme";
	}	 
	 
	//To save the new scheme details request mapping
	@RequestMapping(value = "/formstartscheme.htm", method = RequestMethod.POST,params="insert")
	public ModelAndView addScheme(@ModelAttribute("start_scheme")StartScheme startscheme, BindingResult result, SessionStatus status) 
	{
		startschemeValidator.validate(startscheme, result);
		
		if(result.hasErrors()) 
		{
			ModelMap map = new ModelMap();	
			map.put("schemename", savingschemeDao.listSavingScheme());
			map.put("errorType","insertError");
			map.put("command", startscheme);
			return new ModelAndView("formstartscheme",map);
		}		
		
		List<SavingScheme> getschemeId = savingschemeDao.searchSavingScheme(startscheme.getSchemeName());
		startscheme.setSavingscheme(getschemeId.get(0));
		startschemeDao.insertStartScheme(startscheme);
		status.setComplete();
		return new ModelAndView("redirect:startscheme.htm");		
	}	
	 
	//listing Start Scheme 
	@RequestMapping(value="/startscheme")
	public String listStartScheme(Map<String, Object> map){
		((ModelMap) map).addAttribute("startSchemeList", startschemeDao.listStartScheme());
		return "startscheme";
	}
	
	//receiving request mapping for updating existing Record
	@RequestMapping(value = "/viewstartscheme.htm")
	public ModelAndView goToEdit(@RequestParam(value="start_schemeId", required=true) Integer start_schemeId, @ModelAttribute("start_scheme") StartScheme startscheme ,ModelMap model) 
	{		
		StartScheme getschemeName = startschemeDao.getStartScheme(start_schemeId);		
		String schemeName = getschemeName.getSchemeName();
		
		model.addAttribute("issuedSchemeName", cardIssueDao.getIssuedSchemeName(schemeName));
		model.addAttribute("schemename", savingschemeDao.listSavingScheme());
		model.addAttribute("start_scheme", startschemeDao.getStartScheme(start_schemeId));		
		return new ModelAndView("formstartscheme", model);
	}
	
	//Return to Updated list page request mapping
	@RequestMapping(value = "/formstartscheme.htm", method = RequestMethod.POST,params="update")
	public ModelAndView updateScheme(@ModelAttribute("start_scheme")StartScheme startscheme, BindingResult result, SessionStatus status)
	{		
		startschemeValidator.validate(startscheme, result);
		
		if(result.hasErrors()) 
		{
			ModelMap map = new ModelMap();
			map.put("schemename", savingschemeDao.listSavingScheme());
			map.put("errorType","updateError");
			map.put("command", startscheme);
			return new ModelAndView("formstartscheme",map);
		}		
		
		List<SavingScheme> getschemeId = savingschemeDao.searchSavingScheme(startscheme.getSchemeName());
		startscheme.setSavingscheme(getschemeId.get(0));
		startschemeDao.updateStartScheme(startscheme);
		status.setComplete(); 
		return new ModelAndView("redirect:startscheme.htm");
	}
	 
	
	//Return to Updated list page request mapping
	@RequestMapping(value = "/formclosesavingscheme.htm", method = RequestMethod.POST,params="update")
	public ModelAndView updateCloseScheme(@ModelAttribute("start_scheme")StartScheme startscheme, SessionStatus status)
	{		
		startschemeDao.updateStartScheme(startscheme);
		status.setComplete(); 
		return new ModelAndView("redirect:startscheme.htm");
	}
	
	/** For Cancel **/
	@RequestMapping(value = "/formstartscheme.htm", params = "cancel")
	public String cancelForm() {
		return "redirect:startscheme.htm";
	}	
	
	/** For Cancel **/
	@RequestMapping(value = "/formclosesavingscheme.htm", params = "cancel")
	public String cancelCloseForm() {
		return "redirect:startscheme.htm";
	}	
	
	@RequestMapping(value="/SchemeDetail.htm",method=RequestMethod.GET)
	public @ResponseBody String getAjaxSchemeDetail(@RequestParam(value="schemeName",required=true)String schemeName){
	
		String result = "";
		try{
			List<SavingScheme> schemeDetailsList =  savingschemeDao.searchSavingScheme(schemeName);
			
			if(!schemeDetailsList.isEmpty()){
				ArrayList<String> cardNoList = new ArrayList<String>();
				
				cardNoList.add(0,schemeDetailsList.get(0).getSchemeType().trim());
				cardNoList.add(1, schemeDetailsList.get(0).getSchemeInAmount().toString());
				cardNoList.add(2, schemeDetailsList.get(0).getSchemeInGrams().toString());
				
				result = cardNoList.toString();				
			}
		}
		catch(Exception e){		
		}		
		return result;		
	}
	
	
	//receiving request mapping for Closing the Scheme
		@RequestMapping(value = "/closestartscheme.htm")
		public ModelAndView goToCloseScheme(@RequestParam(value="start_schemeId", required=true) Integer start_schemeId, @ModelAttribute("start_scheme") StartScheme startscheme ,ModelMap model) 
		{	
			model.addAttribute("schemename", savingschemeDao.listSavingScheme());
			model.addAttribute("start_scheme", startschemeDao.getStartScheme(start_schemeId));		
			return new ModelAndView("formclosesavingscheme", model);
		}

		/*** For Retrieving Card NO List Based on Customer Name 
	 * @throws ParseException **/
	@RequestMapping(value="/Getcardissuedate.htm",method=RequestMethod.GET)
	public @ResponseBody String getCardNoList(@RequestParam(value="schemeName",required=false) String schemeName) throws ParseException{
		
		
			String joiningdate = startschemeDao.getStartDate(schemeName);
			
			
		return joiningdate.toString();
	}
	
	
}
