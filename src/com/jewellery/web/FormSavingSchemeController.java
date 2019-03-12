 package com.jewellery.web;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import com.jewellery.dao.SavingSchemeDao;
import com.jewellery.dao.StartSchemeDao;
import com.jewellery.entity.SavingScheme;
import com.jewellery.validator.SavingSchemeValidator;

@Controller
public class FormSavingSchemeController {
	
	@Autowired
	private SavingSchemeDao savingschemeDao;
		
	@Autowired
	private SavingSchemeValidator savingschemeValidator;
	
	@Autowired
	private StartSchemeDao startschemeDao;
	
	@RequestMapping(value = "/addnewscheme.htm")
	public String showSavingScheme(@ModelAttribute("saving_scheme")SavingScheme savingscheme){
		return "formsavingscheme";
	}
	
	
	/** For Cancel **/
	@RequestMapping(value = "/formsavingscheme.htm", params = "cancel")
	public String cancelForm() {
		return "redirect:savingscheme.htm";
	}
	
	//To save the new scheme details request mapping
	@RequestMapping(value = "/formsavingscheme.htm", method = RequestMethod.POST,params="insert")
	public ModelAndView addScheme(@ModelAttribute("saving_scheme")SavingScheme savingscheme, BindingResult result,SessionStatus status) 
	{	
		savingschemeValidator.validate(savingscheme, result);
		
		if(result.hasErrors()) 
		{
			ModelMap map = new ModelMap();			
			map.put("errorType","insertError");
			map.put("command", savingscheme);
			return new ModelAndView("formsavingscheme",map);
		}		
		
		savingschemeDao.insertSavingScheme(savingscheme);
		status.setComplete();
		return new ModelAndView("redirect:savingscheme.htm");	
		
	}	
	 
	//listing Saving Scheme 
	@RequestMapping(value="/savingscheme")
	public String listSavingScheme(Map<String, Object> map){
		((ModelMap) map).addAttribute("savingSchemeList", savingschemeDao.listSavingScheme());
		return "savingscheme";
	}
	
	//receiving request mapping for updating existing users 		

	@RequestMapping(value = "/viewsavingscheme.htm")
	public ModelAndView goToEdit(@RequestParam(value="saving_schemeId", required=true) Integer saving_schemeId, @ModelAttribute("saving_scheme") SavingScheme savingscheme ,ModelMap model) 
	{
		SavingScheme getsavingschemename = savingschemeDao.getSavingScheme(saving_schemeId);
		String schemeName = getsavingschemename.getSchemeName();
		
		model.addAttribute("existingSchemeName", startschemeDao.getstartedSchemeName(schemeName));
		model.addAttribute("saving_scheme", savingschemeDao.getSavingScheme(saving_schemeId));		
		return new ModelAndView("formsavingscheme", model);
	} 
	
	//Return to Updated list page request mapping
	@RequestMapping(value = "/formsavingscheme.htm", method = RequestMethod.POST,params="update")
		public ModelAndView updateScheme(@ModelAttribute("saving_scheme")SavingScheme savingscheme, BindingResult result, SessionStatus status)
		{	
			savingschemeValidator.validate(savingscheme, result);
			
			if(result.hasErrors()) 
			{
				ModelMap map = new ModelMap();			
				map.put("errorType","updateError"); 
				map.put("command", savingscheme);
				return new ModelAndView("formsavingscheme",map);
			}		
			
				savingschemeDao.updateSavingScheme(savingscheme);
				status.setComplete(); 
				return new ModelAndView("redirect:savingscheme.htm");			
		}
}
