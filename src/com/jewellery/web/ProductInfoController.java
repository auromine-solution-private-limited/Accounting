package com.jewellery.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jewellery.dao.CompanyInfoDao;
import com.jewellery.dao.GenPrivateKeysDao;
import com.jewellery.dao.ProductInfoDao;
import com.jewellery.entity.CompanyInfo;
import com.jewellery.entity.ProductInfo;
import com.jewellery.validator.ProductInfoValidator;

@Controller
public class ProductInfoController {
	
	@Autowired
	private ProductInfoDao productInfoDao;
	
	@Autowired
	private ProductInfoValidator productInfoValidator;
	
	@Autowired
	private GenPrivateKeysDao genPrivateKeysDao;
	
	@Autowired
	private CompanyInfoDao cInfodao;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception{
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Calendar.class, null, dateEditor);
	}
	
	@RequestMapping(value="/productinfo.htm",method=RequestMethod.GET)
	public ModelAndView newForm(@ModelAttribute("productinfo")ProductInfo productinfo, ModelMap model)
	{
		List<ProductInfo> productInfoList = productInfoDao.getExistingList();
		if(productInfoList.isEmpty()){
		Resource resource = new ClassPathResource("/labels.properties");
		try{
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			model.addAttribute("productName", props.getProperty("Product.Name"));
			model.addAttribute("productversion", props.getProperty("Product.Version"));
		}catch (IOException e) {
			System.out.println("PROP");
			e.printStackTrace();
		}
		model.addAttribute("CurrentDate", new SimpleDateFormat("dd/mm/yyyy").format(new Date()));
		model.addAttribute("private1", genPrivateKeysDao.getSNAMS("C"));
		model.put("loadingMode","insertMode");
		productinfo.setVersionType("Trial Version");
		model.addAttribute("productinfo",productinfo);
		return new ModelAndView("formproductinfo",model);
		}else{
			return new ModelAndView("redirect:productRegister.htm");
		}
	}
	
	@RequestMapping(value="/productInstall.htm",method=RequestMethod.POST, params = "generatekeys")
	public ModelAndView generateProductInfo(@RequestParam(value="registerCompany",required=true)String registerCompany,@ModelAttribute("productinfo")ProductInfo productinfo,BindingResult result,ModelMap model)
	{
		List<ProductInfo> productInfoList = productInfoDao.getExistingList();
		if(productInfoList.isEmpty()){
			productInfoValidator.validateRegisterCompany(result);
			if(registerCompany.length()==0){
				result.addError(new FieldError("version", "segmentTypeS", " * Registering Company Name can't be Empty."));
			}
			model.addAttribute("registerCompany", registerCompany);
			if(result.hasErrors()){
				model.put("loadingMode","generateError");
				model.put("productinfo",productinfo);
				return new ModelAndView("formproductinfo", model);
			}
			model.put("loadingMode","generateSuccess");
 			model.addAttribute("private2", genPrivateKeysDao.AMSCF(registerCompany));
 			return new ModelAndView("formproductinfo", model);
		}else{
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	@RequestMapping(value="/productInstall.htm",method=RequestMethod.POST, params="Register")
	public ModelAndView storeProductInfos(@ModelAttribute("productinfo")ProductInfo productinfo,BindingResult result,@RequestParam(value="registerCompany",required=false)String registerCompany,ModelMap model)
	{
		List<ProductInfo> productInfoList = productInfoDao.getExistingList();
		if(productInfoList.isEmpty()){
			productInfoValidator.validate(productinfo, registerCompany, result);
			if(registerCompany.length()==0){
				result.addError(new FieldError("version", "segmentTypeS", " * Registering Company Name can't be Empty."));
				model.put("loadingMode","generateError");
			}else{
				model.put("loadingMode","generateSuccess");
			}
			model.addAttribute("registerCompany", registerCompany);
			if(result.hasErrors()){
				model.put("productinfo",productinfo);
				return new ModelAndView("formproductinfo", model);
			}
			CompanyInfo cinfo = new CompanyInfo();
 			cinfo.setCompanyName(registerCompany);
 			cInfodao.insertCompanyInfo(cinfo);
			productInfoDao.storeProductInfo(productinfo);
			return new ModelAndView("redirect:login.htm");
		}else{
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	@RequestMapping(value="/productRegister.htm",method=RequestMethod.GET)
	public ModelAndView saveForm(@ModelAttribute("productinfo")ProductInfo productinfo, ModelMap model)
	{
		List<ProductInfo> productInfoList = productInfoDao.getExistingList();
		if(productInfoList.isEmpty()){
			return new ModelAndView("redirect:productinfo.htm");
		}else{
			productinfo = productInfoList.get(0);
			model.addAttribute("CurrentDate", new SimpleDateFormat("dd/mm/yyyy").format(new Date()));
			productinfo.setVersionType("Full Version");
			productinfo.setPlk("");
			model.addAttribute("registerCompany", cInfodao.listCompanyInfo().get(0).getCompanyName());
			model.addAttribute("productinfo",productinfo);
			return new ModelAndView("ProductRegister",model);
		}
	}
	
	@RequestMapping(value="/productRegister.htm",method=RequestMethod.POST, params = "insert")
	public ModelAndView updateProductRegister(@RequestParam(value="registerCompany",required=true)String registerCompany,@ModelAttribute("productinfo")ProductInfo productinfo,BindingResult result,ModelMap model)
	{
		List<ProductInfo> productInfoList = productInfoDao.getExistingList();
		if(productInfoList.isEmpty()){
			return new ModelAndView("redirect:productinfo.htm");
		}else if (productInfoList.get(0).getVersionType().equalsIgnoreCase("Full Version")){
			return new ModelAndView("redirect:login.htm");
		}else{
			productInfoValidator.validate(productinfo, registerCompany, result);
			if(registerCompany.length()==0){
				result.addError(new FieldError("version", "segmentTypeS", " * Registering Company Name can't be Empty."));
			}
			model.addAttribute("registerCompany", registerCompany);
			if(result.hasErrors()){
				model.put("productinfo",productinfo);
				return new ModelAndView("ProductRegister", model);
			}
			productInfoDao.updateProductInfo(productinfo);
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	@RequestMapping(value="/productRegistraionFailed.htm",method=RequestMethod.GET)
	public ModelAndView registrationFailed(@ModelAttribute("productinfo")ProductInfo productinfo, ModelMap model)
	{
		return new ModelAndView("productRegFailed");
	}
}
