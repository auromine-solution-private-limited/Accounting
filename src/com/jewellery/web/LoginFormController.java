package com.jewellery.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.jewellery.dao.CompanyInfoDao;
import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.PostagItemDao;
import com.jewellery.dao.ProductInfoDao;
import com.jewellery.dao.RateMasterDao;
import com.jewellery.dao.SalesOrderDao;
import com.jewellery.dao.UserloginDao;
import com.jewellery.entity.CompanyInfo;
import com.jewellery.entity.ProductInfo;
import com.jewellery.entity.RateMaster;
import com.jewellery.entity.Userlogin;
import com.jewellery.dao.GenPrivateKeysDao;



@Controller
public class LoginFormController {
	
	private String pattern = "dd/MM/yyyy";
	private String patternDate="dd/MM/yyyy";
	String dateInString =new SimpleDateFormat(patternDate).format(new Date());	
	int counts=0;
	
	@Autowired
	private UserloginDao userloginDao;	
	@Autowired
	private RateMasterDao ratemasterDao;
	@Autowired
	private SalesOrderDao salesorderDao;
	@Autowired
	private ItemMasterDao itemmasterDao;
	@Autowired
	private CompanyInfoDao companyDao;
	@Autowired
	public PostagItemDao postagItemDao;
	@Autowired(required=false)
	private ProductInfoDao productInfoDao; 

	@Autowired(required=false)
	GenPrivateKeysDao genPrivateKeysDao;

	/*@Autowired
	private UserValidator userValidator;*/
	private @Autowired HttpServletRequest request;
	
	@RequestMapping(value="/logout.htm",method=RequestMethod.GET)
	public String logoutform()
	{
		HttpSession session=request.getSession();
		session.invalidate();
		return "redirect:login.htm";
	}
	
	@RequestMapping(value="/login.htm",method=RequestMethod.GET)
	public ModelAndView shouuserForm(@ModelAttribute("user")Userlogin user, ModelMap model)
	{	
		HttpSession session=request.getSession();
		session.invalidate();
		List<ProductInfo> info=productInfoDao.getExistingList();
		if(info.isEmpty()){
			return new ModelAndView("redirect:productinfo.htm");		
		}
		model.addAttribute("productVersion", info.get(0).getProductversion());
		model.addAttribute("pdroductType", info.get(0).getVersionType());
		model.addAttribute("daysLeft", info.get(0).getNumberofDays());
		return new ModelAndView("loginForm",model);
	}
	
	@RequestMapping(value="/homepage.htm",method=RequestMethod.GET)
	public ModelAndView showhomepage()
	{	
		ModelMap model = new ModelMap();
		model.put("boardrate", ratemasterDao.searchRateMaster());
		model.put("Pendingorder", salesorderDao.SalesOrderPending());
		model.put("message", salesorderDao.SalesOrderPending().size());
		model.put("SLCount", itemmasterDao.lowMetalStockList().size());
		model.put("stocklist", itemmasterDao.lowMetalStockList());	
		//model.addAttribute("posStocklist",postagItemDao.POSlowItemStockList());
		//model.addAttribute("SLCount",postagItemDao.POSlowItemStockList().size());
		return new ModelAndView("homepage",model);
	}
	
	@RequestMapping(value="/authentic.htm",method=RequestMethod.POST)
	public ModelAndView processForm(@ModelAttribute("user")Userlogin userlogin)
	{
	try
		{
		String companyName="";
		String goldRate="";
		String silverRate="";		
		HttpSession session = request.getSession(true);		

		/** Demo Section starts here */
			//Gettting product details from DB	
			List<ProductInfo> info=productInfoDao.getExistingList();
			if(info.isEmpty()){
				return new ModelAndView("productinfo.htm");		
			}else if(info.get(0).getVersionType().equalsIgnoreCase("Trial Version")){

		Integer daysLeft=info.get(0).getNumberofDays();
		Date lastUsedDate=info.get(0).getLastUsedDate();
		String last =new SimpleDateFormat(pattern).format(lastUsedDate);
		
		if(daysLeft <= 0){
			return new ModelAndView("productExpire");
		}else if(! last.equals(dateInString))
		{
			daysLeft=daysLeft-1;
			productInfoDao.update(daysLeft, new Date());
		}
		
		try{
			String ProductKey = info.get(0).getPlk().toString();		
		
			if(!ProductKey.equals(genPrivateKeysDao.getMainVal(info.get(0).getVersionType(),companyDao.listCompanyInfo().get(0).getCompanyName()))){
				return new ModelAndView("redirect:productRegistraionFailed.htm");	
			}
		
		}
		catch (NullPointerException e) {
			e.printStackTrace();
			return new ModelAndView("redirect:productRegistraionFailed.htm");	
		}
		
	}else if(info.get(0).getVersionType().equalsIgnoreCase("Full Version")){
		String ProductKey = info.get(0).getPlk().toString();
		if(!ProductKey.equals(genPrivateKeysDao.getMainVal(info.get(0).getVersionType(),companyDao.listCompanyInfo().get(0).getCompanyName()))){
			return new ModelAndView("redirect:productRegistraionFailed.htm");	
		}
	}
	/** Demo Section ends here */
		
			// Setting company name in session object
			List<CompanyInfo> companyList=companyDao.listCompanyInfo();
			if(!companyList.isEmpty())
			{
				 companyName=companyList.get(0).getCompanyName();	
			}
			session.setAttribute("companyname", companyName);
			
			// For Already Login check 
			if(session.getAttribute("username") != null){		
				String Logininfo = "User Already Login";
				return new ModelAndView(new RedirectView("login.htm"),"Logininfo",Logininfo);	
			}	
			
			// For Invalid UserName and Password  			
			List<Userlogin> logins = userloginDao.searchlogin(userlogin.getUserName(), userlogin.getPassword());
			if(!logins.get(0).getPassword().equals(userlogin.getPassword())){
				String username_password="Enter the Valid username and password";
				return new ModelAndView(new RedirectView("login.htm"),"username_password",username_password);	
			}
			
			// For Valid Login
			if (! logins.isEmpty()){
				counts++;
				session.setAttribute("username",logins.get(0).getUserName());
				session.setAttribute("fullname",logins.get(0).getFullName());
				session.setAttribute("userRoll",logins.get(0).getRollName()); 
			   	session.setAttribute("counts",new Integer(counts));
			   	
			   	/** Demo Section starts here */
			   	//adding product details in session object
			   	session.setAttribute("pdroductName", info.get(0).getProductName());
			   	session.setAttribute("pdroductVersion", info.get(0).getProductversion());
				session.setAttribute("pdroductType", info.get(0).getVersionType());
				session.setAttribute("dayLeft", info.get(0).getNumberofDays());
				/** Demo Section ends here */
			}
			
			List<RateMaster> ratemasterList = ratemasterDao.searchRateMaster();
			
			if(!ratemasterList.isEmpty()){
				goldRate = ratemasterList.get(0).getGoldOrnaments().toString();
				silverRate = ratemasterList.get(0).getSilverOrnaments().toString();
			}
			
			 session.setAttribute("GoldRate", goldRate);
			 session.setAttribute("SilverRate", silverRate);
			    		
			
			return new ModelAndView(new RedirectView("homepage.htm"));		
		}catch(IndexOutOfBoundsException e) 
		{
			System.out.println("authentic.htm : "); e.printStackTrace();
		}
	
		String login_invalid="Invalid Username and Password";
		return new ModelAndView(new RedirectView("login.htm"),"login_invalid",login_invalid);
			
			

	}
}