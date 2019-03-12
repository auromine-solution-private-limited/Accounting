package com.jewellery.web;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.jewellery.dao.CategoryDao;
import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.JewelTypeDao;
import com.jewellery.dao.UtilMethodsDao;
import com.jewellery.entity.Category;
import com.jewellery.entity.ItemMaster;
import com.jewellery.entity.JewelType;


@Controller
public class FormJeweltypeController{

	@Autowired
	private JewelTypeDao jeweltypeDao;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private ItemMasterDao itemMasterDao;
	@Autowired(required=false)
	private UtilMethodsDao utilDao;
	
	@RequestMapping("/jeweltype.htm")
	
	public String listAllJewel(Map<String, Object> map) 
	{
		List<JewelType> jewelTypeListgold=jeweltypeDao.listgoldOrnaments();
		List<JewelType> jewelTypeListsilver=jeweltypeDao.listsilverOrnaments();
		((ModelMap) map).addAttribute("jewelTypeListgold", jewelTypeListgold);
		((ModelMap) map).addAttribute("jewelTypeListsilver", jewelTypeListsilver);
		return "jeweltype";
		
	}
	
	//New jewellery creation form request mapping
	@RequestMapping(value="/formjeweltype",method=RequestMethod.GET)
	public String getjewelForm(Model model)
	{
		List<JewelType> jewelTypeListgold=jeweltypeDao.listgoldOrnaments();
		//List<JewelType> jewelTypeListsilver=jeweltypeDao.listsilverOrnaments();
		model.addAttribute("jewelTypeListgold", jewelTypeListgold);
		//model.addAttribute("jewelTypeListsilver", jewelTypeListsilver);
		return "formjeweltype";
	}
	//New jewellery creation form request mapping silver list 
	@RequestMapping(value="/listCategorySilver",method=RequestMethod.GET)
	public String getjewelSilverListForm(Model model)
	{
	//	List<JewelType> jewelTypeListgold=jeweltypeDao.listgoldOrnaments();
		List<JewelType> jewelTypeListsilver=jeweltypeDao.listsilverOrnaments();
		//model.addAttribute("jewelTypeListgold", jewelTypeListgold);
		model.addAttribute("jewelTypeListsilver", jewelTypeListsilver);
		return "listCategorySilver";
	}

	//Getting Request Mapping from update existing jewel id
	@RequestMapping(value = "/viewjeweltype", method = RequestMethod.GET)
	public String getEdit(@RequestParam(value="jewelTypeId", required=true) Integer jewelTypeId,Model model) 
	{
		model.addAttribute("newjewel", jeweltypeDao.getJewelType(jewelTypeId));
		return "formjeweltype";
	}
	
	//Update the data back to server
	@RequestMapping(value="/formjeweltype",method=RequestMethod.POST,params="update")
	public ModelAndView updateJewel(@ModelAttribute("newjewel")JewelType newjewel,BindingResult result,SessionStatus status)
	{
		
		//jeweltypeValidator.validate(newjewel, result);
		if(result.hasErrors())
		{
			ModelMap map = new ModelMap();
			map.put("command",newjewel);
			return new ModelAndView("formjeweltype");
		}
		jeweltypeDao.updateJewelType(newjewel);
		return new ModelAndView(new RedirectView("jeweltype.htm"));
	}

	//creating new Jewellery request mapping
	@RequestMapping(value="/submitJewelType.htm", method = RequestMethod.POST)
	public @ResponseBody void addnewJewel(@RequestParam("jewel_Name") String jewel_Name,@RequestParam("metal_type") String metal_type,@RequestParam("base_metal") String base_metal,@RequestParam("description") String description)
	{
		if(jewel_Name.length()==0){
			jewel_Name = "";
		}
		if(metal_type.length()==0){
			metal_type = "";
		}
		if(base_metal.length() == 0){
			base_metal = "";
		}
		if(description.length() == 0){
			description = "";
		}
		try 
		{	
			JewelType jewelType = new JewelType();
			jewelType.setJewelName(utilDao.capitalizeFirstLetter(jewel_Name));
			jewelType.setMetalType(metal_type);
			jewelType.setMetalUsed(base_metal);
			jewelType.setDescription(description);
			jeweltypeDao.insertJewelType(jewelType);
		} catch (ArrayIndexOutOfBoundsException e) {
		}catch(NumberFormatException ne){
		}	
	}
	
	/** For category Name Pop Up validation **/
	@RequestMapping(value="CatValidate.htm", method= RequestMethod.GET)
	public @ResponseBody String checkCategory(@RequestParam("jewel_Name") String jewel_Name) {
		String found = "false";
		try{
			List<JewelType> catNameList = jeweltypeDao.searchJewel(jewel_Name);
			if(!catNameList.isEmpty()){
				found = "true";
			}else {
				found = "false";
			}
		}catch(Exception e){ }
		return found;		
	}
	
	//canceling new user creation request mapping				
	@RequestMapping(value="/formjeweltype",method=RequestMethod.POST,params="cancel") // cancel button to redirect to customer list page
	public ModelAndView cancelForm(@ModelAttribute("newjewel")JewelType newjewel)
	{
		return new ModelAndView(new RedirectView("formjeweltype.htm"));
	}
	
	// Ajax  code for start date in cancel card report.
	@RequestMapping(value = "/getbaseCategoryName.htm", method = RequestMethod.GET)
	public @ResponseBody String getCategoryName(@RequestParam(value="categoryName",required=false) String categoryName)
	{
		List<JewelType>  catName = jeweltypeDao.getcategoryName(categoryName);
		ArrayList<String> categoryNameList=new ArrayList<String>();
		categoryNameList.add(0, "All");
		categoryNameList.add(1, catName.toString());
		return categoryNameList.toString();
	}
	
	// Ajax code for validating basecatogry in itememater
	@RequestMapping(value = "/ValidateCategoryName.htm", method = RequestMethod.GET)
	public @ResponseBody String getItemCategoryName(@RequestParam("newCategoryName") String newCategoryName,@RequestParam("oldCategoryName") String oldCategoryName)
	{
		String errorMsg = "";
		try {
			 List<ItemMaster> itemNameList =  itemMasterDao.searchItemListByBaseCategoryName(oldCategoryName);
			 List<JewelType> baseCategoryNameList = jeweltypeDao.searchJewel(newCategoryName);
			if(!itemNameList.isEmpty()) {
				errorMsg = "* Item tags already Created Under Base Category : '"+oldCategoryName+"' can't Update";
			}else if(!oldCategoryName.equalsIgnoreCase(newCategoryName) && !baseCategoryNameList.isEmpty()){
				errorMsg = "* Duplicate Base Category Name can't Update";
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return errorMsg;
	}

	@RequestMapping(value="/updateExistingCategory.htm", method = RequestMethod.POST)
	public @ResponseBody void editCategoryName(@RequestParam("metal_type") String metal_type,@RequestParam("base_metal") String base_metal,@RequestParam("description") String description,@RequestParam("newCategoryName") String newCategoryName,@RequestParam("oldCategoryName") String oldCategoryName)
	{
		if (newCategoryName.length() == 0) {
			newCategoryName = "";
		}
		if (oldCategoryName.length() == 0) {
			oldCategoryName = "";
		}
		List<Category> subCategoryNameList = categoryDao.searchCategory(oldCategoryName);

		try {
			if (!subCategoryNameList.isEmpty()) {
				categoryDao.updateBaseCategoryName(newCategoryName,oldCategoryName);
			}
			jeweltypeDao.updateCategoryName(metal_type,base_metal,description,newCategoryName,oldCategoryName);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
