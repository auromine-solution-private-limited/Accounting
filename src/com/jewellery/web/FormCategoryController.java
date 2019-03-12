package com.jewellery.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.jewellery.entity.Category;
import com.jewellery.entity.ItemMaster;
import com.jewellery.dao.CategoryDao;
import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.JewelTypeDao;
import com.jewellery.dao.UtilMethodsDao;
import com.jewellery.validator.CategoryValidator;
@Controller
public class FormCategoryController {
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private JewelTypeDao jeweltypeDao;
	@Autowired	
	private ItemMasterDao itemmasterDao;
	@Autowired
	private CategoryValidator categoryValidator;
	
	@Autowired
	private UtilMethodsDao utilDao;

//List the master category page
	@RequestMapping("/category.htm")
	public String listmasterCategory(Map<String, Object> map) 
	{
	List<Category> category=categoryDao.defaultcategory();
	((ModelMap) map).addAttribute("categoryList", category);
	return "category"; 
	}
	//List the master subcategory page
	@RequestMapping(value="/category_sub.htm",method=RequestMethod.GET)
	public String listsubCategory(Map<String, Object> map) 
	{
	List<Category> category=categoryDao.listCategory();
	
	((ModelMap) map).addAttribute("categoryList", category);
	return "category_sub";
	}
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
	throws Exception{
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class,null, dateEditor);
	}
	//New subcategory creation form request mapping
	@RequestMapping(value="/formcategory",method=RequestMethod.GET)
	public String newForm(@RequestParam(value="bcat")String baseCategory,@ModelAttribute("category")Category category,Model model)
	{
		List<?> jewlType=jeweltypeDao.listgoldOrnaments();
		List<?> categoryList=categoryDao.listCategoryName();
		List<?>bCat=jeweltypeDao.searchJewel(baseCategory);
		model.addAttribute("bCat",bCat);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("JewelName",jewlType);
		return "formcategory";
	}
	//Getting Request Mapping for update existing category id
	@RequestMapping(value = "/viewcategory", method = RequestMethod.GET)
	public String getEdit(@RequestParam(value="categoryId") Integer categoryId,Model model) 
	{
		String found = "false";
		List<?> jewlType=jeweltypeDao.listgoldOrnaments();
		model.addAttribute("JewelName",jewlType);
		List<?> categoryList=categoryDao.listCategoryName();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("category", categoryDao.getCategory(categoryId));
		Object categoryName=categoryDao.getCategory(categoryId).getCategoryName();
		List<ItemMaster> itemCategoryName=itemmasterDao.getBaseCategoryName(categoryName.toString());
		if(!itemCategoryName.isEmpty())
		{
			found="true";
		}else {
			found = "false";
		}
		model.addAttribute("baseCategoryName",found);
		return "formcategory";
	}
	
	//List subCategory based On the BaseCategory
		@RequestMapping(value="/categoryList.htm",method=RequestMethod.GET)
		public String categoryList(@RequestParam(value="bcat")String baseCategory,Model model)
		{
		
			List<Category> categoryList=(List<Category>) categoryDao.searchCategory(baseCategory);
			model.addAttribute("categoryList", categoryList);
		return "categoryList";
		}
	
//Updating the existing category details
	@RequestMapping(value="/formcategory",method=RequestMethod.POST,params="update")
	public ModelAndView updateCategory(@ModelAttribute("category")Category category,BindingResult result,SessionStatus status,Model model)
	{
		List<?> jewlType=jeweltypeDao.listgoldOrnaments();
		model.addAttribute("JewelName",jewlType);
		List<?> categoryList=categoryDao.listCategoryName();
		model.addAttribute("categoryList", categoryList);
		Category CategoryOld = categoryDao.getCategory(category.getCategoryId());
		categoryValidator.validateUpdate(category,CategoryOld, result);//validation of the category entity fields
		if(result.hasErrors())
		{
			ModelMap map = new ModelMap();
			map.put("command",category);
			map.addAttribute("errorType","updateError");
			model.addAttribute("JewelName",jewlType);
			model.addAttribute("categoryList", categoryList);
			return new ModelAndView("formcategory",map);
		}
		String metalUsed=category.getMetalType();
		String subCategoryName=category.getCategoryName();
		String basecategory=category.getBaseCategory();
		BigDecimal CatZERO = new BigDecimal("0.00");
		
		BigDecimal vaPercentage=category.getVaPercentage();
		if(vaPercentage == null || vaPercentage.signum() == 0){
			category.setVaPercentage(CatZERO);
		}
		
		BigDecimal mc=category.getMcPerGram();
		if(mc == null || mc.signum() == 0){
			category.setMcPerGram(CatZERO);
		}
		BigDecimal mcrupees=category.getMcInRupees();
		if(mcrupees == null || mcrupees.signum() == 0){
			category.setMcInRupees(CatZERO);
		}
		
		BigDecimal Vat=category.getVat();
		if(Vat == null || Vat.signum() == 0){      
			category.setVat(CatZERO);
		}
		
		BigDecimal less=category.getLessPercentage();
		if(less == null || less.signum() == 0){
			category.setLessPercentage(CatZERO);
		}
		BigDecimal catHMC=category.getCategoryHMCharges();
		if(catHMC == null || catHMC.signum() == 0){
			category.setCategoryHMCharges(CatZERO);
		}
		categoryDao.updateCategory(category);
		itemmasterDao.updateVaPercentage(less, vaPercentage, mc,mcrupees, Vat, metalUsed, subCategoryName, catHMC);
		status.setComplete();
		return new ModelAndView(new RedirectView("categoryList.htm?bcat="+basecategory));
	}
//Adding new category request mapping
	@RequestMapping(value="/formcategory",method=RequestMethod.POST,params="insert")
	public ModelAndView addnewCategory(@RequestParam(value="bcat")String baseCategory,@ModelAttribute("category")Category category,BindingResult result,SessionStatus status,Model model)
	{
		List<?> jewlType=jeweltypeDao.listgoldOrnaments();
		model.addAttribute("JewelName",jewlType);
		List<?> categoryList=categoryDao.listCategoryName();
		model.addAttribute("categoryList", categoryList);
		categoryValidator.validate(category, result);//validation of the category entity fields
		if(result.hasErrors())
		{
			ModelMap map = new ModelMap();
			map.put("command",category);
			map.addAttribute("errorType","insertError");
			return new ModelAndView("formcategory",map);
		}
				
		category.setCategoryName(utilDao.capitalizeFirstLetter(category.getCategoryName()));		
		categoryDao.insertCategory(category);
		return new ModelAndView(new RedirectView("categoryList.htm?bcat="+baseCategory));
	}
	//canceling new subcategory creation request mapping				
	@RequestMapping(value="/formcategory",method=RequestMethod.POST,params="cancel") // cancel button to redirect to customer list page
	public ModelAndView cancelForm(@ModelAttribute("category")Category category)
	{
		String baseCategory=category.getBaseCategory();
		return new ModelAndView(new RedirectView("categoryList.htm?bcat="+baseCategory));
	}

}
