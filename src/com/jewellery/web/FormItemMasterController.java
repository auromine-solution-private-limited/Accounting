package com.jewellery.web;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import org.springframework.web.servlet.view.RedirectView;

import com.jewellery.core.JewelStockCore;
import com.jewellery.dao.CategoryDao;
import com.jewellery.dao.CompanyInfoDao;
import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.JewelStockDao;
import com.jewellery.dao.JewelTypeDao;
import com.jewellery.dao.LotStockDao;
import com.jewellery.entity.Category;
import com.jewellery.entity.CompanyInfo;
import com.jewellery.entity.ItemMaster;
import com.jewellery.entity.JewelStock;
import com.jewellery.entity.LotStock;
import com.jewellery.print.BarCodePrint;
import com.jewellery.util.WeightScale;
import com.jewellery.validator.ItemMasterValidator;
import com.jewellery.validator.OpeningStockValidator;

@Controller
public class FormItemMasterController 
{
	String jewelName;
	String metalType;
	String subCategoryName;
	String metalUsed;	
	String categoryName;
	String stockType;
	
	BigDecimal totalGrossWeight;
	BigDecimal grossWeight;
	BigDecimal netWeight;
	BigDecimal oldWeight;
	BigDecimal clStockWeight;
	BigDecimal newGwt;
	
	int qty;
	int oldQty;
	int clQty;
	int tpcs;
	int oldTpcs;	
	int clTpcs;
	
	private final BigDecimal Negative=new BigDecimal("-1");
	
	@Autowired(required=false)
	private ItemMasterValidator itemMasterValidator;
	
	@Autowired(required=false)
	private OpeningStockValidator openingStockValidator;
	
	@Autowired(required=false)
	private CategoryDao categoryDao;

	@Autowired(required=false)
	private JewelTypeDao jeweltypeDao;
	
	@Autowired(required=false)
	private ItemMasterDao itemmasterDao;
	
	@Autowired(required=false)
	private LotStockDao lotStockDao;
	
	@Autowired(required=false)
	private BarCodePrint barCodePrint;
	
	@Autowired(required=false)
	private CompanyInfoDao companyInfoDao;
	 
	@Autowired(required=false)
	private JewelStockDao jewelStockDao;
		
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
	throws Exception{
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class, dateEditor);	
	}
	

	// Tag List Page
	@RequestMapping("/categoryitemmaster.htm")
	public String listmasterCategory(@RequestParam(value="categoryId", required=true)Integer categoryId, Model model) 
	{
		Category category=categoryDao.getCategory(categoryId);	
		List<ItemMaster> itemnames=itemmasterDao.listItemMaster();
		model.addAttribute("itemnames", itemnames);
		model.addAttribute("category", category);
		List<CompanyInfo> companyList = companyInfoDao.listCompanyInfo();
		if(companyList.isEmpty()){
			model.addAttribute("ornPrintFormat","Citizen");
		}else{
			model.addAttribute("ornPrintFormat",companyList.get(0).getOrnBarcodePrint());
		}
		return "categoryitemmaster";
	}
		
//Creating new item
		@RequestMapping(value="/formitemmaster.htm",method=RequestMethod.GET)	
		public String newForm(@RequestParam("categoryId")Integer categoryId, Model model)
		{
			ItemMaster itemmaster=new ItemMaster();
			List<Category> categoryName=categoryDao.categoryName(categoryId);
			model.addAttribute("categoryId", categoryId);
			model.addAttribute("itemmaster", itemmaster);
			model.addAttribute("itemCode", getNewItemCode()); 
			model.addAttribute("categoryName", categoryName);
			List<CompanyInfo> companyList = companyInfoDao.listCompanyInfo();
			if(companyList.isEmpty()){
				model.addAttribute("ornPrintFormat","Citizen");
			}else{
				model.addAttribute("ornPrintFormat",companyList.get(0).getOrnBarcodePrint());
			}
			return "formitemmaster";			
		}
		
//Update item request mapping
		@RequestMapping(value="/viewitemmaster.htm",method=RequestMethod.GET)
		public String newView(@RequestParam("Id")Integer itemId,@RequestParam("cId")Integer categoryId,Model model)
		{
			ItemMaster existItem=itemmasterDao.getItemMaster(itemId);
			model.addAttribute("itemModel",existItem);
			model.addAttribute("categoryId",categoryId);
			return "viewitemmaster";
		}
		
		//Update loose metal stock
		@RequestMapping(value="/losemetalUpdate.htm",method=RequestMethod.GET)
		public String loosmetalUpdate(@ModelAttribute("itemModel")ItemMaster itemmaster,@RequestParam("iId")Integer itemId,@RequestParam("cId")Integer categoryId,Model model)
		{
			ItemMaster existItem=itemmasterDao.getItemMaster(itemId);
			model.addAttribute("itemModel",existItem);
			model.addAttribute("categoryId",categoryId);
			return "loosemetalView"; 
		}
		
//posting updated loose metal details to Database server
		@RequestMapping(value="/losemetalUpdate.htm",method=RequestMethod.POST,params="update")	
		public String loosemetalUpdate(@ModelAttribute("itemModel")ItemMaster itemmaster,BindingResult result,SessionStatus status)
		{			
			metalType = itemmaster.getCategoryName(); 
			
			openingStockValidator.validateOpeningStock(itemmaster, result);
						
			if(result.hasErrors())
			{
				ModelMap map = new ModelMap();
				map.put("command",itemmaster);
				return "loosemetalView";
			}
			
			totalGrossWeight = itemmaster.getOp_GrossWeight();
			qty = itemmaster.getOp_Quantity();				
			
			// To get the value of the item based before updating value
			ItemMaster oldObj = itemmasterDao.getItemMaster(itemmaster.getItemId());
			oldWeight = oldObj.getOp_GrossWeight();
			oldQty = oldObj.getOp_Quantity(); 
			
			clStockWeight = oldObj.getGrossWeight();
			clQty = oldObj.getQty();
			
			if(oldWeight.signum() == 0){	// First Time Entry Insert Mode	
				
				itemmaster.setGrossWeight(totalGrossWeight);
				itemmaster.setNetWeight(totalGrossWeight); 
				itemmaster.setTotalGrossWeight(totalGrossWeight);
				itemmaster.setQty(itemmaster.getOp_Quantity());
				JewelStockCore.JewelStock_Tagged_PositiveEntry(jewelStockDao,itemmaster, "OpeningStock");
			}
			else{ // Second Time Entry Update Mode				 
				
				totalGrossWeight = totalGrossWeight.subtract(oldWeight);												
				clStockWeight = clStockWeight.add(totalGrossWeight); 					
				
				qty = qty - oldQty;						
				clQty = clQty + qty;
										
				itemmaster.setGrossWeight(clStockWeight);
				itemmaster.setNetWeight(clStockWeight);
				itemmaster.setTotalGrossWeight(clStockWeight);
				itemmaster.setQty(clQty);	 
				
				List<JewelStock> jewelStockDBList = jewelStockDao.searchByTransNO_TransType(itemmaster.getItemId().toString(),"ItemMaster");
				JewelStock jewelStockDB = jewelStockDBList.get(0);
				
				jewelStockDB.setStock_OPGrossWeight(itemmaster.getOp_GrossWeight());
				jewelStockDB.setStock_OPNetWeight(itemmaster.getOp_NetWeight());
				jewelStockDB.setStock_OPTotalGrossWeight(itemmaster.getOp_TotalGrossWeight());
				jewelStockDB.setStock_OPQty(itemmaster.getOp_Quantity());
				jewelStockDB.setStock_OPTotalPieces(itemmaster.getTotalPieces());
				jewelStockDB.setStock_CLGrossWeight(itemmaster.getGrossWeight());
				jewelStockDB.setStock_CLNetWeight(itemmaster.getNetWeight());
				jewelStockDB.setStock_CLTotalGrossWeight(itemmaster.getTotalGrossWeight());
				jewelStockDB.setStock_CLQty(itemmaster.getQty());
				jewelStockDB.setStock_CLTotalPieces(itemmaster.getTotalPieces());
				jewelStockDB.setStock_TransDate(itemmaster.getStockDate());
				jewelStockDB.setStock_VAPercent(itemmaster.getVaPercentage());
				jewelStockDB.setStock_MCPerGram(itemmaster.getMcPerGram());
				jewelStockDB.setStock_LessPercent(itemmaster.getLessPercentage());
				jewelStockDB.setStock_Tax(itemmaster.getTax());
				
				jewelStockDao.updateJewelStock(jewelStockDB);
			}		

			categoryDao.updateCategoryWeight(totalGrossWeight, metalType);//Update total gross weight in category list
			itemmasterDao.updateItemMaster((ItemMaster)itemmaster); //Update Item master record
			status.setComplete();
			return"redirect:category.htm";
		}
		
//posting updated item details to Database server
		@RequestMapping(value="/viewitemmaster.htm",method=RequestMethod.POST,params="update")
		public ModelAndView addUpdateItem(@RequestParam(value="cId")Integer categoryId,@ModelAttribute("itemModel")ItemMaster itemmaster,BindingResult result,SessionStatus status,Model model)			
		{			
			itemMasterValidator.updatevalidate(itemmaster,result);
			if(result.hasErrors())
			{				
				model.addAttribute("command2", itemmaster);
				model.addAttribute("categoryId", categoryId);
				return new ModelAndView("viewitemmaster");
			}
			
			ItemMaster itemObj = itemmasterDao.getItemMaster(itemmaster.getItemId());			
			BigDecimal totGrossWtDB = itemObj.getTotalGrossWeight();
			
			stockType=itemmaster.getStockType();
			metalUsed=itemmaster.getMetalUsed();
			jewelName = itemmaster.getCategoryName();
			metalType = itemmaster.getMetalUsed();
			subCategoryName = itemmaster.getSubCategoryName();
			totalGrossWeight = itemmaster.getTotalGrossWeight();
			//newGwt=itemmaster.getTotalGrossWeight();
			qty = itemmaster.getQty();
			int oldQty = itemObj.getQty();			
			qty = qty - oldQty;	
			totalGrossWeight = totalGrossWeight.subtract(totGrossWtDB);
			
					// update to Base Metal Type
			
					if(!(stockType.equals("GoldLotStock") || stockType.equals("SilverLotStock")))
					{
						categoryDao.updateCategoryWeight(totalGrossWeight, metalType);
						// update jewel type totalGrossWeight
						jeweltypeDao.updateTotalGrossWt(totalGrossWeight, metalType, jewelName);
						//update Sub Category totalGrossWeight
						categoryDao.updateSubCategory(totalGrossWeight, metalType,jewelName,subCategoryName);
					}
					
					if(stockType.equals("PurchasedStock") && metalUsed.equals("Gold Ornaments"))
					{					
						itemmasterDao.updateLooseStock(totalGrossWeight, totalGrossWeight, totalGrossWeight, qty, "IT100001");
					}
					
					if(stockType.equals("PurchasedStock") && metalUsed.equals("Silver Ornaments"))
					{
						itemmasterDao.updateLooseStock(totalGrossWeight, totalGrossWeight, totalGrossWeight, qty, "IT100006");	
					}
					
					/** Jewel Stock Update */
					if(stockType.equals("PurchasedStock") && (metalUsed.equalsIgnoreCase("Silver Ornaments") || metalUsed.equalsIgnoreCase("Gold Ornaments"))){
						JewelStockCore.JewelStock_unTaged_UpdateEntry(jewelStockDao, itemmaster);
						JewelStockCore.JewelStock_Taged_UpdateEntry(jewelStockDao,itemmaster);
					}else if(stockType.equals("OpeningStock") && (metalUsed.equalsIgnoreCase("Silver Ornaments") || metalUsed.equalsIgnoreCase("Gold Ornaments"))){
						JewelStockCore.JewelStock_Taged_UpdateEntry(jewelStockDao,itemmaster);
					}
		            else if(stockType.equals("OpeningStock") && (metalUsed.equalsIgnoreCase("Gold Loose Stock") || metalUsed.equalsIgnoreCase("Silver Loose Stock"))){
		            	totalGrossWeight=totalGrossWeight.multiply(Negative);
						String itemCode=itemmaster.getItemCode();
						lotStockDao.updateexitingTaggedlotStock(totalGrossWeight, totalGrossWeight, itemCode);
		            	JewelStockCore.JewelStock_Taged_UpdateEntry(jewelStockDao,itemmaster);
		             }
					
					if(stockType.equals("OpeningStock") && (metalUsed.equalsIgnoreCase("Gold Loose Stock") || metalUsed.equalsIgnoreCase("Silver Loose Stock")))
					{
						/***
						 * if the updated value is greater then the inserted value(gross Weight) .
						 ***/
						if (itemmaster.getOp_GrossWeight().compareTo(itemObj.getOp_GrossWeight()) == 1 ) {
							BigDecimal oldOpGrosweight=itemObj.getOp_GrossWeight();
							BigDecimal newOpGrossweight=itemmaster.getOp_GrossWeight();
							BigDecimal diffGrossweight=newOpGrossweight.subtract(oldOpGrosweight);
						    BigDecimal finalGrossweight=itemObj.getGrossWeight().add(diffGrossweight);
						    itemmaster.setGrossWeight(finalGrossweight);
						    itemmaster.setNetWeight(finalGrossweight);
						    itemmaster.setTotalGrossWeight(finalGrossweight);
						    itemmasterDao.updateItemMaster(itemmaster);
						}else if(itemmaster.getOp_GrossWeight().compareTo(itemObj.getOp_GrossWeight()) == -1 )
						{
							/***
							 * if the updated value is samller then the inserted value(gross Weight) .
							 ***/
							BigDecimal oldOpGrosweight=itemObj.getOp_GrossWeight();
							BigDecimal newOpGrossweight=itemmaster.getOp_GrossWeight();
							BigDecimal diffGrossweight=newOpGrossweight.subtract(oldOpGrosweight);
						    BigDecimal finalGrossweight=itemObj.getGrossWeight().subtract(diffGrossweight);
						    itemmaster.setGrossWeight(finalGrossweight);
						    itemmaster.setNetWeight(finalGrossweight);
						    itemmaster.setTotalGrossWeight(finalGrossweight);
						    itemmasterDao.updateItemMaster(itemmaster);
						}
						/***
						 * if the updated value is Greater then the inserted value(Qty) .
						 ***/
						if (itemmaster.getOp_Quantity() > itemObj.getOp_Quantity())
						{
							int oldOp_Quantity=itemObj.getOp_Quantity();
							int newOp_Quantity=itemmaster.getOp_Quantity();
							int diffQty=newOp_Quantity-oldOp_Quantity;
							int finalQty=itemObj.getQty()+diffQty;
						    itemmaster.setQty(finalQty);
						    itemmasterDao.updateItemMaster(itemmaster);
						}else if(itemmaster.getOp_Quantity() < itemObj.getOp_Quantity()){
							/***
							 * if the updated value is samller then the inserted value(Qty) .
							 ***/
							int oldOp_Quantity=itemObj.getOp_Quantity();
							int newOp_Quantity=itemmaster.getOp_Quantity();
							int diffQty=newOp_Quantity-oldOp_Quantity;
							int finalQty=itemObj.getQty()-diffQty;
						    itemmaster.setQty(finalQty);
						    itemmasterDao.updateItemMaster(itemmaster);
						}
						
					}
					
					/****************** Lot Stock Update *************/
					if(stockType.equals("GoldLotStock") || stockType.equals("SilverLotStock"))
					{
						totalGrossWeight=totalGrossWeight.multiply(Negative);
						String itemCode=itemmaster.getItemCode();
						lotStockDao.updateexitingTaggedlotStock(totalGrossWeight, totalGrossWeight, itemCode);
																		
						//Jewel Stock Update					
						LotStock lotStock = lotStockDao.searchLotStockByLotCode(itemCode).get(0);
						lotStock.setGrossWeight(itemmaster.getGrossWeight().multiply(Negative));
						lotStock.setNetWeight(itemmaster.getGrossWeight().multiply(Negative));
						lotStock.setQuantity(itemmaster.getQty() * -1);
						JewelStockCore.JewelStock_LotStock_Update(jewelStockDao, lotStock); // Negative Entry : Lot Opening Stock - Update
						JewelStockCore.JewelStock_Taged_UpdateEntry(jewelStockDao, itemmaster); // Positive entry : Tagged Stock
					}
					itemmasterDao.updateItemMaster((ItemMaster)itemmaster);
					
					
					status.setComplete();
					
					return new ModelAndView ("redirect:categoryitemmaster.htm?categoryId="+categoryId);
		}
		
		//posting updated item details to Database server
		@RequestMapping(value="/viewitemmaster.htm",method=RequestMethod.POST,params="print")
		public ModelAndView updateexistinTag(@RequestParam(value="cId")Integer categoryId,@ModelAttribute("itemModel")ItemMaster itemmaster,BindingResult result,HttpSession session, SessionStatus status,Model model) throws Exception			
		{			
			itemMasterValidator.validate(itemmaster,result);
			if(result.hasErrors())
			{				
				model.addAttribute("command", itemmaster);
				model.addAttribute("categoryId", categoryId);
				return new ModelAndView("viewitemmaster");
			}
			
			ItemMaster itemObj = itemmasterDao.getItemMaster(itemmaster.getItemId());			
			BigDecimal totGrossWt = itemObj.getTotalGrossWeight();
			
			stockType=itemmaster.getStockType();
			metalUsed=itemmaster.getMetalUsed();
			jewelName = itemmaster.getCategoryName();
			metalType = itemmaster.getMetalUsed();
			subCategoryName = itemmaster.getSubCategoryName();
			totalGrossWeight = itemmaster.getTotalGrossWeight();
			newGwt=itemmaster.getTotalGrossWeight();
			qty = itemmaster.getQty();
			int oldQty = itemObj.getQty();			
			qty = qty - oldQty;	
			totalGrossWeight = totalGrossWeight.subtract(totGrossWt);
					
					// update to Base Metal Type
			
					if(!(stockType.equals("GoldLotStock") || stockType.equals("SilverLotStock")))
					{
						categoryDao.updateCategoryWeight(totalGrossWeight, metalType);
						// update jewel type totalGrossWeight
						jeweltypeDao.updateTotalGrossWt(totalGrossWeight, metalType, jewelName);
						//update Sub Category totalGrossWeight
						categoryDao.updateSubCategory(totalGrossWeight, metalType,jewelName,subCategoryName);
					}
					
					if(stockType.equals("PurchasedStock") && metalUsed.equals("Gold Ornaments"))
					{					
						itemmasterDao.updateLooseStock(totalGrossWeight, totalGrossWeight, totalGrossWeight, qty, "IT100001");						
					}
					
					if(stockType.equals("PurchasedStock") && metalUsed.equals("Silver Ornaments"))
					{
						itemmasterDao.updateLooseStock(totalGrossWeight, totalGrossWeight, totalGrossWeight, qty, "IT100006");	
					}
					
					/** Jewel Stock Update */
					if(stockType.equals("PurchasedStock") && (metalUsed.equalsIgnoreCase("Silver Ornaments") || metalUsed.equalsIgnoreCase("Gold Ornaments"))){
						JewelStockCore.JewelStock_unTaged_UpdateEntry(jewelStockDao, itemmaster);
						JewelStockCore.JewelStock_Taged_UpdateEntry(jewelStockDao, itemmaster);
					}else if(stockType.equals("OpeningStock") && (metalUsed.equalsIgnoreCase("Silver Ornaments") || metalUsed.equalsIgnoreCase("Gold Ornaments"))){
						JewelStockCore.JewelStock_Taged_UpdateEntry(jewelStockDao,itemmaster);
					}
							
					/******************Lot stock updation*************/
					if(stockType.equals("GoldLotStock") || stockType.equals("SilverLotStock"))
					{
						BigDecimal totGwt=new BigDecimal("-1");
						totalGrossWeight=totalGrossWeight.multiply(totGwt);
						String itemCode=itemmaster.getItemCode();
						lotStockDao.updateexitingTaggedlotStock(totalGrossWeight, totalGrossWeight, itemCode);
						
						//Jewel Stock Update					
						LotStock lotStock = lotStockDao.searchLotStockByLotCode(itemCode).get(0);
						lotStock.setGrossWeight(itemmaster.getGrossWeight().multiply(Negative));
						lotStock.setNetWeight(itemmaster.getGrossWeight().multiply(Negative));
						lotStock.setQuantity(itemmaster.getQty() * -1);
						JewelStockCore.JewelStock_LotStock_Update(jewelStockDao, lotStock); // Negative Entry : Lot Opening Stock - Update
						JewelStockCore.JewelStock_Taged_UpdateEntry(jewelStockDao, itemmaster); // Positive entry : Tagged Stock
								
					}
					itemmasterDao.updateItemMaster((ItemMaster)itemmaster);
					List<CompanyInfo> companyList = companyInfoDao.listCompanyInfo();
					if( ! companyList.isEmpty()){ // if Ornament Bar Code Print Format set in Company Info
						if(companyList.get(0).getOrnBarcodePrint().equalsIgnoreCase("TSC")){
							barCodePrint.ornamentPrintTSC(itemmaster, session);
							System.out.println("Godex");
							status.setComplete();
						}else if(companyList.get(0).getOrnBarcodePrint().equalsIgnoreCase("Citizen")){
							barCodePrint.ornamentPrintCitizen(itemmaster, session);
							System.out.println("Citizen");
							status.setComplete();
						}else if(companyList.get(0).getOrnBarcodePrint().equalsIgnoreCase("Zebra")){
							System.out.println("Zebra");
							barCodePrint.ornamentPrintZebra(itemmaster, session);
							status.setComplete();
						}
						else if(companyList.get(0).getOrnBarcodePrint().equalsIgnoreCase("Argox")){
							System.out.println("Argox");
							barCodePrint.ornamentPrintArgoxSGH(itemmaster, session);
							status.setComplete();
						}
					}else{ // if Ornament Bar Code Print format NOT SET in Company Info set : Default Format
						barCodePrint.ornamentPrintCitizen(itemmaster, session);
						System.out.println("Citizen");
						status.setComplete();
					}
					return new ModelAndView ("redirect:categoryitemmaster.htm?categoryId="+categoryId);
				}

//Creating new item details to Database server
		@RequestMapping(value="/formitemmaster.htm",method=RequestMethod.POST,params="insert")
		public ModelAndView addnewItem(@RequestParam(value="categoryId", required=true)Integer categoryId,
				@ModelAttribute("itemmaster")ItemMaster itemmaster,BindingResult result,SessionStatus status,Model model)
		{
		
			jewelName = itemmaster.getCategoryName();
			metalType = itemmaster.getMetalUsed();
			subCategoryName = itemmaster.getSubCategoryName();	
			totalGrossWeight = itemmaster.getTotalGrossWeight();
			stockType=itemmaster.getStockType();
			grossWeight=itemmaster.getGrossWeight();
			netWeight=itemmaster.getNetWeight();
			metalUsed=itemmaster.getMetalUsed();
			qty=itemmaster.getQty();			
		
			itemMasterValidator.validate(itemmaster,result);
			if(result.hasErrors())
			{
				ModelMap map = new ModelMap();
				map.put("command", itemmaster);
				map.addAttribute("categoryId", categoryId);
				map.addAttribute("errorType","insertError");
				map.addAttribute("stockType",stockType);
				return new ModelAndView("formitemmaster",map);
			}
			
			String item_Code = getNewItemCode();
			itemmaster.setItemCode(item_Code);
			Category category=categoryDao.getCategory(categoryId);
			category.getItemnames().add(itemmaster);
			/***
			 * This code is added for Category Loose Item Insertion for Opening Stock fields of
			 * OP_grosweight,Op_netweight,_Op_TotalGrt,Opt_Qty.
			 * *****/
			if(stockType.equals("OpeningStock") && (metalUsed.equalsIgnoreCase("Gold Loose Stock") || metalUsed.equalsIgnoreCase("Silver Loose Stock")) ){
				itemmaster.setOp_GrossWeight(itemmaster.getGrossWeight());
				itemmaster.setOp_NetWeight(itemmaster.getGrossWeight());
				itemmaster.setOp_TotalGrossWeight(itemmaster.getGrossWeight());
				itemmaster.setOp_Quantity(itemmaster.getQty());
			}
			
			//creating new item under the categoryId
			categoryDao.insertCategory(category);
			if(stockType.equals("PurchasedStock") && metalUsed.equals("Gold Ornaments"))
			{
				itemmasterDao.updateuntagstock(totalGrossWeight,grossWeight,netWeight, qty);	
			}
			
			if(stockType.equals("PurchasedStock") && metalUsed.equals("Silver Ornaments"))
			{
				itemmasterDao.updateuntagstocksilver(totalGrossWeight, grossWeight, netWeight, qty);
			}
			
			/** Un tagged Jewel Stock Insert */
			if(stockType.equalsIgnoreCase("PurchasedStock") && (metalUsed.equals("Gold Ornaments") || metalUsed.equals("Silver Ornaments")) )
			{
				JewelStockCore.JewelStock_unTaged_NegativeEntry(jewelStockDao,grossWeight, netWeight, totalGrossWeight, qty, stockType, itemmaster, metalUsed );
				JewelStockCore.JewelStock_Tagged_PositiveEntry(jewelStockDao,itemmaster, "PurchasedStock");
			}
			
			if(stockType.equals("OpeningStock"))
			{
				//updating the totalGrossWeight of default sub category
				categoryDao.updateCategoryWeight(totalGrossWeight, metalType);
				
				//updating the totalGrossWeight of default base category
				jeweltypeDao.updateTotalGrossWt(totalGrossWeight, metalType, jewelName);
				//updating the totalGrossWeight of after  sub category
				categoryDao.updateSubCategory(totalGrossWeight, metalType,jewelName,subCategoryName);
				JewelStockCore.JewelStock_Tagged_PositiveEntry(jewelStockDao,itemmaster, "OpeningStock");
			}
			
			if(stockType.equals("GoldLotStock") || stockType.equals("SilverLotStock"))
			{
				LotStock lotstock=new LotStock();
				lotstock.setLotCode(itemmaster.getItemCode());
				lotstock.setLotDate(itemmaster.getStockDate());
				Integer totset=new Integer("-1");
				grossWeight=grossWeight.multiply(Negative);
				netWeight=netWeight.multiply(Negative);
				qty = qty * totset;
				lotstock.setGrossWeight(grossWeight);
				lotstock.setNetWeight(netWeight);
				lotstock.setQuantity(qty);
				
				if(itemmaster.getMetalUsed().equals("Gold Ornaments"))
				{
					lotstock.setLotItemName("GoldLotStock");		
				}
				else if(itemmaster.getMetalUsed().equals("Silver Ornaments"))
				{
					lotstock.setLotItemName("SilverLotStock");		
				}
				lotstock.setLotType("LotStock");
				lotStockDao.insertLotStock(lotstock);
				
				/**  Lot Items -  Jewel Stock Update */
				JewelStockCore.JewelStock_LotStock_Insert(jewelStockDao, lotstock); // Negative Entry Lot Opening Stock
				JewelStockCore.JewelStock_Tagged_PositiveEntry(jewelStockDao, itemmaster, lotstock.getLotItemName()); // Positive entry : Tagged Stock
			}
			
			status.setComplete();
			return new ModelAndView(new RedirectView("categoryitemmaster.htm?categoryId="+categoryId));

		}
		//Create new item tag and printing option
		@RequestMapping(value="/formitemmaster.htm",method=RequestMethod.POST,params="print")
		public ModelAndView addnewItemWithBarcode(@RequestParam(value="categoryId", required=true)Integer categoryId,
				@ModelAttribute("itemmaster")ItemMaster itemmaster,BindingResult result,HttpSession session,SessionStatus status,Model model) throws Exception
		{
		
			jewelName = itemmaster.getCategoryName();
			metalType = itemmaster.getMetalUsed();
			subCategoryName = itemmaster.getSubCategoryName();	
			totalGrossWeight = itemmaster.getTotalGrossWeight();
			stockType=itemmaster.getStockType();
			grossWeight=itemmaster.getGrossWeight();
			netWeight=itemmaster.getNetWeight();
			metalUsed=itemmaster.getMetalUsed();
			qty=itemmaster.getQty();			
		
			itemMasterValidator.validate(itemmaster,result);
			if(result.hasErrors())
			{
				ModelMap map = new ModelMap();
				map.put("command", itemmaster);
				map.addAttribute("categoryId", categoryId);
				map.addAttribute("errorType","insertError");
				map.addAttribute("stockType",stockType);
				return new ModelAndView("formitemmaster",map);
			}
			
			String item_Code = getNewItemCode();
			itemmaster.setItemCode(item_Code);
			Category category=categoryDao.getCategory(categoryId);
			category.getItemnames().add(itemmaster);
		
			//creating new item under the categoryId
			categoryDao.insertCategory(category);
			if(stockType.equals("PurchasedStock") && metalUsed.equals("Gold Ornaments"))
			{
				itemmasterDao.updateuntagstock(totalGrossWeight,grossWeight,netWeight, qty);	
			}
			
			if(stockType.equals("PurchasedStock") && metalUsed.equals("Silver Ornaments"))
			{
				itemmasterDao.updateuntagstocksilver(totalGrossWeight, grossWeight, netWeight, qty);	
			}
			
			/** Un tagged Jewel Stock Insert Print */
			if(stockType.equalsIgnoreCase("PurchasedStock") && (metalUsed.equals("Gold Ornaments") || metalUsed.equals("Silver Ornaments")) )
			{
				JewelStockCore.JewelStock_unTaged_NegativeEntry(jewelStockDao,grossWeight, netWeight, totalGrossWeight, qty, stockType, itemmaster, metalUsed );
				JewelStockCore.JewelStock_Tagged_PositiveEntry(jewelStockDao,itemmaster, "PurchasedStock");
			}
			
			if(stockType.equals("OpeningStock"))
			{
				//updating the totalGrossWeight of default sub category
				categoryDao.updateCategoryWeight(totalGrossWeight, metalType);
				
				//updating the totalGrossWeight of default base category
				jeweltypeDao.updateTotalGrossWt(totalGrossWeight, metalType, jewelName);
				//updating the totalGrossWeight of after  sub category
				categoryDao.updateSubCategory(totalGrossWeight, metalType,jewelName,subCategoryName);
				JewelStockCore.JewelStock_Tagged_PositiveEntry(jewelStockDao,itemmaster, "OpeningStock");
			}
			if(stockType.equals("GoldLotStock") || stockType.equals("SilverLotStock"))
			{
				LotStock lotstock=new LotStock();
				lotstock.setLotCode(itemmaster.getItemCode());
				lotstock.setLotDate(itemmaster.getStockDate());
				BigDecimal multipy=new BigDecimal("-1");
				Integer totset=new Integer("-1");
				grossWeight=grossWeight.multiply(multipy);
				netWeight=netWeight.multiply(multipy);
				qty=qty*totset;
				lotstock.setGrossWeight(grossWeight);
				lotstock.setNetWeight(netWeight);
				lotstock.setQuantity(qty);
				
				if(itemmaster.getMetalUsed().equals("Gold Ornaments"))
				{
					lotstock.setLotItemName("GoldLotStock");		
				}
				else if(itemmaster.getMetalUsed().equals("Silver Ornaments"))
				{
					lotstock.setLotItemName("SilverLotStock");		
				}
				lotstock.setLotType("LotStock");
				lotStockDao.insertLotStock(lotstock);
				
				/**  Lot Items : Jewel Stock - Update */
				JewelStockCore.JewelStock_LotStock_Insert(jewelStockDao, lotstock); // Negative Entry
				JewelStockCore.JewelStock_Tagged_PositiveEntry(jewelStockDao, itemmaster, lotstock.getLotItemName());
			}
			List<CompanyInfo> companyList = companyInfoDao.listCompanyInfo();
			if( ! companyList.isEmpty()){ // if Ornament Bar Code Print Format set in Company Info
				if(companyList.get(0).getOrnBarcodePrint().equalsIgnoreCase("TSC")){
					barCodePrint.ornamentPrintTSC(itemmaster, session);
					System.out.println("Godex");
					status.setComplete();
				}else if(companyList.get(0).getOrnBarcodePrint().equalsIgnoreCase("Citizen")){
					barCodePrint.ornamentPrintCitizen(itemmaster, session);
					System.out.println("Citizen");
					status.setComplete();
				}else if(companyList.get(0).getOrnBarcodePrint().equalsIgnoreCase("Zebra")){
					System.out.println("Zebra");
					barCodePrint.ornamentPrintZebra(itemmaster, session);
					status.setComplete();
				}
				else if(companyList.get(0).getOrnBarcodePrint().equalsIgnoreCase("Argox")){
					System.out.println("Argox");
					barCodePrint.ornamentPrintArgoxSGH(itemmaster, session);
					status.setComplete();
				}
			}else{ // if Ornament Bar Code Print format NOT SET in Company Info set : Default Format
				barCodePrint.ornamentPrintCitizen(itemmaster, session);
				System.out.println("Citizen");
				status.setComplete();
			}					
			return new ModelAndView(new RedirectView("categoryitemmaster.htm?categoryId="+categoryId));
		}
		/** Auto Generating Item Code**/
		private synchronized String getNewItemCode(){
			BigInteger found = itemmasterDao.getItemCode();		
			String itemCodeNo = "IT100000";
			String itemcodelist = "IT"+found;
			
			if(found != null){
				 itemCodeNo = itemcodelist;
			}
		
	        String splitNo = itemCodeNo.substring(2);
	        int num = Integer.parseInt(splitNo);	      
	        num++;        
	        String number = Integer.toString(num);        
	        String displayCode = itemCodeNo.substring(0, 2) + number;
	        return displayCode;
		}
		
		@RequestMapping(value="/formitemmaster.htm",method=RequestMethod.POST,params="cancel") // cancel button to redirect to sub category list page
		public ModelAndView cancelForm(@RequestParam(value="categoryId", required=true)Integer categoryId,@ModelAttribute("itemmaster")ItemMaster itemmaster)
		{
			return new ModelAndView(new RedirectView("categoryitemmaster.htm?categoryId="+categoryId));
		}
		
		@RequestMapping(value="/viewitemmaster.htm",method=RequestMethod.POST,params="cancel") // cancel button to redirect to sub category list page
		public ModelAndView cancelViewForm(@RequestParam("cId")Integer categoryId,@ModelAttribute("itemModel")ItemMaster itemmaster)
		{
			return new ModelAndView ("redirect:categoryitemmaster.htm?categoryId="+categoryId);
		}
		
		//loose metal update cancel redirect
		@RequestMapping(value="/losemetalUpdate.htm",method=RequestMethod.POST,params="cancel") // cancel button to redirect to sub category list page
		public ModelAndView cancelLossMetalForm(@ModelAttribute("itemModel")ItemMaster itemmaster)
		{
			return new ModelAndView(new RedirectView("category.htm"));
		}
		
		//Print Ornament Bar codes in Multiple Printer Formats
		@RequestMapping(value="/ornBarcodePrint.htm",method=RequestMethod.GET)	
		public @ResponseBody String ornBarcodePreview(HttpServletRequest req,HttpSession session,@RequestParam(value="itemArray",required=false)String itemArray,@RequestParam(value="ornPrintFormat",required=false)String ornPrintFormat) throws Exception
		{
			if(itemArray != null){
					ArrayList<String> itemCodeList = new ArrayList<String>(Arrays.asList(itemArray.split(",")));
					for(String itemCode : itemCodeList) {
						int id = Integer.parseInt(itemCode);
						ItemMaster itemObj = itemmasterDao.getItemMaster(id);
						 if(itemObj != null){
					    	 if(ornPrintFormat.equalsIgnoreCase("TSC")){ // If TSC Printer Format
					    		 barCodePrint.ornamentPrintTSC(itemObj, session);
								 System.out.println("Printing in Godex...");
					    	 }else if(ornPrintFormat.equalsIgnoreCase("Zebra")){ // If Zebra Printer Format
					    		 barCodePrint.ornamentPrintZebra(itemObj, session);
					    		 System.out.println("Printing in Zebra...");
					    	 }else if(ornPrintFormat.equalsIgnoreCase("Citizen")){ // If Citizen Printer Format
					    		 barCodePrint.ornamentPrintCitizen(itemObj, session);
					    		 System.out.println("Printing in Citizen...");
					    	 }else if(ornPrintFormat.equalsIgnoreCase("Argox")){
					    		 barCodePrint.ornamentPrintArgoxSGH(itemObj, session);
					    		 System.out.println("Printing in Argox...");
					    	 }else{
					    		 barCodePrint.ornamentPrintCitizen(itemObj, session);
					    		 System.out.println("Printing in Citizen...");
					    	 }
					    	 
						 }
					}
			}
			//return new ModelAndView("redirect:categoryitemmaster.htm?categoryId="+categoryId);
			return "Success";
		}
		
		//Opening stock Creation For Gold and silver Bullion and Exchange
		@RequestMapping(value="/bullionexchangestock.htm",method=RequestMethod.GET)
		public String bullionexchangeopeningstock(@ModelAttribute("openingStockModel")ItemMaster itemmaster,@RequestParam("iId")Integer itemId,@RequestParam("cId")Integer categoryId,Model model)
		{
			ItemMaster existItem=itemmasterDao.getItemMaster(itemId);
			model.addAttribute("openingStockModel",existItem);
			return "bullionexchangestock";
		}
		
		@RequestMapping(value="/bullionexchangestock.htm",method=RequestMethod.POST,params="update")	
		public String bullionexchangeStockUpdate(@ModelAttribute("openingStockModel")ItemMaster itemmaster,BindingResult result,SessionStatus status)
		{	
			openingStockValidator.validateOpeningStock(itemmaster, result);
			
			if(result.hasErrors())
			{
				ModelMap map = new ModelMap();
				map.put("command",itemmaster);
				return "bullionexchangestock";
			}					
			
			totalGrossWeight = itemmaster.getOp_GrossWeight(); //form opening Gross weight
			qty = itemmaster.getOp_Quantity(); //form opening Qty
			tpcs = itemmaster.getOp_TotalPieces(); //form opening TotPieces Only for Exchange
						
			metalType = itemmaster.getCategoryName();
			
			ItemMaster oldObj = itemmasterDao.getItemMaster(itemmaster.getItemId());
			oldWeight = oldObj.getOp_GrossWeight();
			oldQty = oldObj.getOp_Quantity();
			oldTpcs = oldObj.getOp_TotalPieces();
			
			clStockWeight = oldObj.getGrossWeight();
			clQty = oldObj.getQty();
			clTpcs = oldObj.getTotalPieces();
			
			if(oldWeight.signum() == 0){	//First Time Entry on Insert Mode
				
				itemmaster.setGrossWeight(totalGrossWeight);
				itemmaster.setNetWeight(totalGrossWeight); 
				itemmaster.setTotalGrossWeight(totalGrossWeight);
				itemmaster.setQty(itemmaster.getOp_Quantity());
				itemmaster.setTotalPieces(itemmaster.getOp_TotalPieces());
				
				JewelStockCore.JewelStock_Tagged_PositiveEntry(jewelStockDao,itemmaster,"OpeningStock");
			}
			else{	//Second Time Entry on Update Mode		
								
				totalGrossWeight = totalGrossWeight.subtract(oldWeight);												
				clStockWeight = clStockWeight.add(totalGrossWeight); 					
				
				qty = qty - oldQty;						
				clQty = clQty + qty;					 
										
				tpcs = tpcs - oldTpcs;						
				clTpcs = clTpcs + tpcs;
				
				itemmaster.setGrossWeight(clStockWeight);
				itemmaster.setNetWeight(clStockWeight);
				itemmaster.setTotalGrossWeight(clStockWeight);
				itemmaster.setQty(clQty);
				itemmaster.setTotalPieces(clTpcs);
				
				JewelStock jewelStockDB = jewelStockDao.searchByTransNO_TransType(itemmaster.getItemId().toString(),"ItemMaster").get(0);
				jewelStockDB.setStock_OPGrossWeight(itemmaster.getOp_GrossWeight());
				jewelStockDB.setStock_OPNetWeight(itemmaster.getOp_NetWeight());
				jewelStockDB.setStock_OPTotalGrossWeight(itemmaster.getOp_TotalGrossWeight());
				jewelStockDB.setStock_OPQty(itemmaster.getOp_Quantity());
				jewelStockDB.setStock_CLGrossWeight(itemmaster.getGrossWeight());
				jewelStockDB.setStock_CLNetWeight(itemmaster.getNetWeight());
				jewelStockDB.setStock_CLTotalGrossWeight(itemmaster.getTotalGrossWeight());
				jewelStockDB.setStock_CLQty(itemmaster.getQty());
				jewelStockDB.setStock_CLTotalPieces(itemmaster.getTotalPieces());
				jewelStockDB.setStock_TransDate(itemmaster.getStockDate());
				jewelStockDB.setStock_Tax(itemmaster.getTax());
				
				jewelStockDao.updateJewelStock(jewelStockDB);				
				
				/*Date currentDate = new SimpleDateFormat("dd/MM/yyyy").parse();
				itemmaster.setStockDate();*/
			}
			
			categoryDao.updateCategoryWeight(totalGrossWeight, metalType);
			itemmasterDao.updateItemMaster((ItemMaster)itemmaster);					
			status.setComplete();
			return"redirect:category.htm";
		}
		
		@RequestMapping(value="/bullionexchangestock.htm",method=RequestMethod.POST,params="cancel") // cancel button to redirect to sub category list page
		public ModelAndView cancelForm()
		{
			return new ModelAndView(new RedirectView("category.htm"));
		}
		
		@SuppressWarnings("static-access")
		@RequestMapping(value="/WeightScale.htm",method=RequestMethod.POST)	
		public @ResponseBody String WeightScaleData()
		{
			String wData = "";
			//try{
			WeightScale wscale = new WeightScale();
			/*	return wscale.getWeightScaleData(); */
				//wscale.close();
			  try {
				  	wscale.initialize();
					Thread.sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 wData = wscale.WeightData;
				 if(wData!=null){
					 return wData;
				 }else{
					 return "";
				 }
				 /*System.out.println(" In getWeightScaleData method Weight Data :"+WeightScale.WeightData+":");
					WeightScale wscale = new WeightScale();
					return wscale.getWeightScaleData();*/
		/*	}catch(Exception e){
				System.out.println("Exception in Weight Scale Retrieval...."+e);
			}*/
		
		}
}