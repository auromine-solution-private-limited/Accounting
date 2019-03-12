package com.jewellery.web;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.jewellery.entity.Userlogin;
import com.jewellery.dao.UserloginDao;
import com.jewellery.validator.UserValidator;
@Controller
public class FormUserloginController{
	
	@Autowired
	private UserloginDao Userlogindao;
	@Autowired 
	private UserValidator userValidator;
	@Autowired
	private UserloginDao userLoginDao;
	

//Get The  list of existing user details request mapping	
		@RequestMapping("/alluser.htm")
		public ModelAndView listContacts(ModelMap model, HttpSession session) 
		{
			List<Userlogin> userlogin=Userlogindao.listUserlogin();
			model.addAttribute("userlogin", userlogin);
			
			if(session.getAttribute("username") != null) {
				List<Userlogin> userLoginList = userLoginDao.searchByFullName(session.getAttribute("username").toString());
				if(!userLoginList.isEmpty()){
					model.addAttribute("roll", userLoginList.get(0).getRollName());
				}
			}
			return new ModelAndView("userlogin");
		}

//receiving request mapping for updating existing users 		

		@RequestMapping(value = "/edituser.htm", method = RequestMethod.GET)
		public String getEdit(@RequestParam(value="id", required=true) Integer id,Model model) 
		{
			model.addAttribute("user", Userlogindao.getUserlogin(id));
			return "editpage";
		}
		
//New User creation form request mapping
		@RequestMapping(value="/addnewuser.htm",method=RequestMethod.GET)
		public String shouuserForm(@ModelAttribute("user")Userlogin user)
		{
			return "formuserlogin";
		}
		
//canceling new user creation request mapping				
		@RequestMapping(value="/addnewuser.htm",method=RequestMethod.POST,params="cancel") // cancel button to redirect to customer list page
		public String cancelForm(@ModelAttribute("user")Userlogin user)
		{
			return "redirect:alluser.htm";
		}
			
//To save the New User details request mapping
				@RequestMapping(value = "/addnewuser.htm", method = RequestMethod.POST,params="insert")
				public String addContact(@ModelAttribute("user")Userlogin user, BindingResult result,SessionStatus status) 
				{
					
					userValidator.validate(user, result);
					if(result.hasErrors())
					{
						ModelMap map = new ModelMap();
						map.put("command",user);
						return "formuserlogin";
					}
					
					Userlogindao.insertUserlogin(user);
					status.setComplete();
					return "redirect:alluser.htm";
				}
				
//Return to Updated list page request mapping
@RequestMapping(value = "/edituser.htm", method = RequestMethod.POST,params="edit")
	public String getEditedPage(@ModelAttribute("user")Userlogin user, BindingResult result)
	{	
		userValidator.validate(user, result);
		
		if(result.hasErrors())
		{			
			ModelMap map = new ModelMap();
			map.put("command",user);
			return "editpage";
		}
		
		Userlogindao.updateUserlogin(user);
		return "redirect:alluser.htm";
	}

//canceling new user creation request mapping				
		@RequestMapping(value="/edituser.htm",method=RequestMethod.POST,params="cancel") // cancel button to redirect to customer list page
		public String cancelUpdateForm(@ModelAttribute("user")Userlogin user)
		{
			return "redirect:alluser.htm";
		}

//Get The  list of existing user details request mapping	
		@RequestMapping("/addnewuser.htm")
		public String listLoginUsers(Map<String, Object> map) 
		{
		List<Userlogin> userlogin=Userlogindao.listUserlogin();
		((ModelMap) map).addAttribute("userlogin", userlogin);
		return "alluser";
		}
}

