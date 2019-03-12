package com.jewellery.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jewellery.dao.AppConfigDao;
import com.jewellery.dao.ConfigDao;
import com.jewellery.dao.UserloginDao;
import com.jewellery.entity.Config;
import com.jewellery.entity.ConfigDetailList;
import com.jewellery.entity.ConfigSetting;
import com.jewellery.entity.Userlogin;



@Controller
public class FormConfigController {
	
	@Autowired
	private ConfigDao configDao;
	@Autowired
	private AppConfigDao appConfigDao;
	@Autowired
	private UserloginDao userLoginDao;
	
	/** For Initial Config Form load */
	@RequestMapping(value = "/Config.htm", method = RequestMethod.GET)
	public ModelAndView showform(@ModelAttribute("config") Config config, HttpSession session, ModelMap model)  {
		
		if (configDao.listConfigSetting().isEmpty()) {
			return new ModelAndView("formConfig"); 		//** if entity is empty it allows to open form and can be saved *//*
		}else if(session.getAttribute("username") != null){
			List<Userlogin> userLoginList = userLoginDao.searchByFullName(session.getAttribute("username").toString());
			if(!userLoginList.isEmpty()){
				if(userLoginList.get(0).getRollName().equalsIgnoreCase("Admin")){
					config.setConfigSetting(appConfigDao.getConfigSetting(1));
					config.setListConfigDetail(appConfigDao.getConfigDetailList(1));
					model.addAttribute("mode", "ViewMode");
					model.addAttribute("config", config);
					return new ModelAndView("formConfig",model);
				}
			}
			return new ModelAndView("redirect:viewConfig.htm");
		}
		return new ModelAndView("redirect:Warning.htm");
	}
	
	@RequestMapping(value = "/Warning.htm", method = RequestMethod.GET)
	public ModelAndView warning()  {
		 return new ModelAndView("Warning");
	}

	@RequestMapping(value="/saveConfig.htm", method = RequestMethod.POST, params = "insert")
	public ModelAndView saveForm(@ModelAttribute("config") Config config,HttpSession session,ModelMap model) 
	{
		if (configDao.listConfigSetting().isEmpty()) {
			ConfigSetting configSetting = config.getConfigSetting();
			configSetting.setAuthorizer(session.getAttribute("username").toString());
			appConfigDao.insertConfigSetting(configSetting);
			List<ConfigDetailList> configDetail = config.getListConfigDetail();
			
			for(ConfigDetailList configDetailObj: configDetail)
			{
				configDetailObj.setConfigSetting(configSetting);
				appConfigDao.insertConfigDetailList(configDetailObj);	
			}
		}
		return new ModelAndView("redirect:homepage.htm");  /** if entity is not empty it opens the saved info */
	}
	
	/** view mode */
	@RequestMapping(value="/viewConfig.htm",method=RequestMethod.GET)
	public ModelAndView viewForm(@ModelAttribute("config") Config config, HttpSession session, ModelMap model){
		
		List<Userlogin> userLoginList = userLoginDao.searchUserName(session.getAttribute("username").toString());
		
		if(!userLoginList.isEmpty()){
			if(userLoginList.get(0).getRollName().equalsIgnoreCase("Admin")){
				config.setConfigSetting(appConfigDao.getConfigSetting(1));
				config.setListConfigDetail(appConfigDao.getConfigDetailList(1));
				model.addAttribute("config", config);
				return new ModelAndView("formConfig",model);
			}
		}
		return new ModelAndView("redirect:Warning.htm");
	}
	
	/** for cancel button*/
	@RequestMapping(value="/saveConfig.htm", method = RequestMethod.POST, params = "cancel")
	public ModelAndView cancelForm(@ModelAttribute("config") Config config){
		return new ModelAndView("redirect:homepage.htm");
	}
	
	
}
