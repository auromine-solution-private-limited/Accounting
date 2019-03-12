package com.jewellery.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import com.jewellery.dao.RateMasterDao;
import com.jewellery.entity.RateMaster;
import com.jewellery.validator.RateMasterValidator;


@Controller
public class FormRateMasterController {
	
	@Autowired
	private RateMasterDao ratemasterDao;
	
	@Autowired
	private RateMasterValidator rateMasterValidator;
	private @Autowired HttpServletRequest request; 
		
	@InitBinder
	protected void initBinder(WebDataBinder binder)
	throws Exception{
	
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"), true);
		binder.registerCustomEditor(Date.class,null,dateEditor);	
	}
	
//listing Rate Mater
		@RequestMapping(value="/ratemaster")
		public String listRateMater(Map<String, Object> map){		
			List<RateMaster> rateMasterList = ratemasterDao.listRateMaster();
			((ModelMap) map).addAttribute("rateMasterList", rateMasterList);
			return "ratemaster";
		}	

//Map the data
		@RequestMapping(value="/formratemaster.htm", method=RequestMethod.GET)	
		public ModelAndView rateMasterList(@ModelAttribute("ratemaster")RateMaster rateMaster){
					
					Map<String, Object> model = new HashMap<String, Object>();					
					model.put("command", rateMaster);					
					return  new ModelAndView("formratemaster",model); 
		}
				
		
//To Add Or Save the record
		
			@RequestMapping(value="/formratemaster.htm", method = RequestMethod.POST, params="insert")
			public ModelAndView addData(@ModelAttribute("ratemaster") RateMaster ratemaster, BindingResult result,SessionStatus status) 
			{	
				HttpSession session = request.getSession(true);
				rateMasterValidator.validate(ratemaster, result);
											
				if(result.hasErrors()) 
				{
					ModelMap map = new ModelMap();
					map.put("command", ratemaster);						
					map.addAttribute("rateErrorType","insertError");
					return new ModelAndView("formratemaster",map);
				}
				
				 session.setAttribute("GoldRate", ratemaster.getGoldOrnaments());
				 session.setAttribute("SilverRate", ratemaster.getSilverOrnaments());
						
				ratemasterDao.insertRateMaster(ratemaster);													
				status.setComplete();
				return new ModelAndView("redirect:ratemaster.htm");
			}
			
//Redirect the form for update
			@RequestMapping(value = "/viewratemaster.htm", method = RequestMethod.GET)
			public ModelAndView showForm(@ModelAttribute("ratemaster") RateMaster rateMaster,@RequestParam(value="ratemasterId", required=false) Integer id, Model model) 
			{
				model.addAttribute("ratemaster", ratemasterDao.getRateMaster(id));
				return new ModelAndView ("formratemaster");
			}

			@RequestMapping(value = "/formratemaster.htm", method = RequestMethod.POST, params="update")
			public ModelAndView updateData(@ModelAttribute("ratemaster") RateMaster ratemaster, BindingResult result){
							
				rateMasterValidator.validate(ratemaster, result);
				HttpSession session = request.getSession(true);
									 		 
				if(result.hasErrors())
				{			
					ModelMap map = new ModelMap();			
					map.put("command", ratemaster);		
					map.addAttribute("rateErrorType","updateError");
					return new ModelAndView("formratemaster",map);				
				}
				
				
				session.setAttribute("GoldRate", ratemaster.getGoldOrnaments());
				session.setAttribute("SilverRate", ratemaster.getSilverOrnaments());
				ratemasterDao.updateRateMaster(ratemaster);
				return new ModelAndView("redirect:ratemaster.htm");			
			}	
			
			//canceling Order request mapping				
			@RequestMapping(value="/formratemaster.htm",method=RequestMethod.POST,params="cancel") // cancel button to redirect to customer list page
			public String Cancel()
			{
				return "redirect:ratemaster.htm";
			}

}

