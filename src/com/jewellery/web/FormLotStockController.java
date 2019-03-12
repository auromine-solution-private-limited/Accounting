 package com.jewellery.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.jewellery.dao.JewelStockDao;
import com.jewellery.dao.LotStockDao;
import com.jewellery.entity.LotStock;
import com.jewellery.validator.LotStockValidator;

import com.jewellery.core.JewelStockCore;

@Controller
public class FormLotStockController {
	
	@Autowired
	private LotStockDao lotstockDao; 
	
	@Autowired
	private JewelStockDao jewelStockDao; 
	
	@Autowired
	private LotStockValidator lotStockValidator;
	
	private final BigDecimal CONVERT = new BigDecimal("-1");
		
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
	throws Exception{
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class,null, dateEditor);
	}	
	
	
		@RequestMapping(value="/lotstock.htm")		
		public ModelAndView listLotItem(ModelMap map){
			
			List<Object[]> goldlotlist=lotstockDao.sumofGoldLotStockGwt();
			List<Object[]> silverlotlist=lotstockDao.sumofSilverLotStockGwt();
			
			 for (Object o[] : goldlotlist) {
					BigDecimal lotgwt = (BigDecimal) o[0];
					BigDecimal lotQty=(BigDecimal) o[1];
					map.addAttribute("lotgoltgwt", lotgwt);
					map.addAttribute("lotgoltqty", lotQty);
			 }
			 for (Object o[] : silverlotlist) {
					BigDecimal lotgwt = (BigDecimal) o[0];
					BigDecimal lotQty=(BigDecimal) o[1];
					map.addAttribute("lotsilvergwt", lotgwt);
					map.addAttribute("lotsilverqty", lotQty);
			 }
			 
			 return new ModelAndView( "lotStockList",map);
		}
		
		 /************Mapping to gold lotstock list*************/
		@RequestMapping(value="/lotaddstock.htm")
		public ModelAndView showaddstocklist(ModelMap map){
			map.addAttribute("goldStockList", lotstockDao.listGoldLotStock());
			map.addAttribute("silverStockList", lotstockDao.listSilverLotStock());
			return new ModelAndView ("lotaddstock",map);
		}
		
		@RequestMapping(value="/formaddlotstock.htm", method=RequestMethod.GET)
		public ModelAndView showformaddstock(@ModelAttribute("lotAddStock") LotStock lotStock){
			return new ModelAndView ("formaddlotstock");
		}	
		 
		//To save the record 
		@RequestMapping(value = "/formaddlotstock.htm", method = RequestMethod.POST, params="insert")
		public ModelAndView addLotStock(@ModelAttribute("lotAddStock") LotStock lotstock, BindingResult result,SessionStatus status, Model model) 
		{
			
			lotStockValidator.validate(lotstock, result);			
			
			if(result.hasErrors()){
				model.addAttribute("errorType", "insertError");
				return new ModelAndView("formaddlotstock");  
			}
			
			lotstockDao.insertLotStock(lotstock);		
			JewelStockCore.JewelStock_LotStock_Insert(jewelStockDao,lotstock);
			status.setComplete();	
			
			if(lotstock.getLotItemName().equalsIgnoreCase("GoldLotStock")){
				return new ModelAndView("redirect:lotaddstock.htm?lt_type=gold");
			}
			else{
				return new ModelAndView("redirect:lotaddstock.htm?lt_type=silver");
			}
		}	
		
		
		//redirect the form for update
		@RequestMapping(value = "/viewlotstockadd.htm", method = RequestMethod.GET)
		public ModelAndView updateAddStock(@RequestParam(value="lotId", required=true) Integer lotId,  @ModelAttribute("lotAddStock") LotStock lotStock, ModelMap model) 
		{
			model.addAttribute("lotAddStock", lotstockDao.getLotStock(lotId));			
			return new ModelAndView("formaddlotstock",model);		
		}
		
		//to Update the record
		@RequestMapping(value = "/formaddlotstock.htm", method = RequestMethod.POST, params="update")
		public ModelAndView updateOrder(@ModelAttribute("lotAddStock") LotStock lotstock, BindingResult result, Model model) {
			
			
			lotStockValidator.validate(lotstock, result);
			
			if(!result.hasErrors()){			
				lotStockValidator.ValidateUpdate(lotstock, result);
			}
			
			if(result.hasErrors()){
				model.addAttribute("errorType", "updateError");
				return new ModelAndView("formaddlotstock");
			}
			JewelStockCore.JewelStock_LotStock_Update(jewelStockDao, lotstock);
			lotstockDao.updateLotStock(lotstock);
			
			if(lotstock.getLotItemName().equalsIgnoreCase("GoldLotStock")){
				return new ModelAndView("redirect:lotaddstock.htm?lt_type=gold");
			}
			else{
				return new ModelAndView("redirect:lotaddstock.htm?lt_type=silver");
			}
		}
		
		
		//URL redirection for cancelling				
		@RequestMapping(value="/formaddlotstock.htm", params="cancel") // cancel button to redirect to customer list page
		public ModelAndView addLotStock(LotStock lotstock)
		{
			if(lotstock.getLotItemName().equalsIgnoreCase("GoldLotStock")){
				return new ModelAndView("redirect:lotaddstock.htm?lt_type=gold");
			}
			else{
				return new ModelAndView("redirect:lotaddstock.htm?lt_type=silver");
			}
		}
		
	/**********************  Lot Stock Return Mappings **************************/
		
	//Return Page Listing 
	@RequestMapping(value="/lotreturnstock.htm") 
	public ModelAndView showreturnstocklist(ModelMap map){ 
		map.addAttribute("goldReturnStockList", lotstockDao.listReturnGoldLotStock());
		map.addAttribute("silverReturnStockList", lotstockDao.listReturnSilverLotStock());
		return new ModelAndView ("lotreturnstock");
	}
	
	//Show Return Form Page
	@RequestMapping(value="/formreturnlotstock.htm")
	public ModelAndView showformreturnstock(@ModelAttribute("lotStockReturn") LotStock lotstock){
		return new ModelAndView ("formreturnlotstock");
	}
	
	//Form View Mode
	@RequestMapping(value = "/viewlotstockreturn.htm", method = RequestMethod.GET)
	public ModelAndView updateLotStockReturn(@ModelAttribute("lotStockReturn") LotStock lotStock,@RequestParam(value="lotId", required=true) Integer lotId, ModelMap model) 
	{
		//***  Setting Negative Values***//
		LotStock lotStockNew = lotstockDao.getLotStock(lotId);
		
		BigDecimal returnGrossWt = CONVERT.multiply(lotStockNew.getGrossWeight());
		BigDecimal returnNetWt = CONVERT.multiply(lotStockNew.getNetWeight());
		int returnQty = lotStockNew.getQuantity() * -1;
		
		lotStockNew.setGrossWeight(returnGrossWt);
		lotStockNew.setNetWeight(returnNetWt);
		lotStockNew.setQuantity(returnQty);	
	
		model.addAttribute("lotStockReturn", lotStockNew);
		return new ModelAndView("formreturnlotstock",model);		
	}
		
	//Return Page Cancel - redirect to Return List Page				
	@RequestMapping(value="/formreturnlotstock.htm", params="cancel")
	public ModelAndView returnLotStockCancel(LotStock lotstock)
	{
		if(lotstock.getLotItemName().equalsIgnoreCase("GoldLotStock")){
			return new ModelAndView("redirect:lotreturnstock.htm?lt_type=gold");
		}else{
			return new ModelAndView("redirect:lotreturnstock.htm?lt_type=silver");
		}
	}
	
	//To save the record 
	@RequestMapping(value = "/formreturnlotstock.htm", method = RequestMethod.POST, params="insert")
	public ModelAndView SaveLotStockReturn(@ModelAttribute("lotStockReturn") LotStock lotstock, BindingResult result,SessionStatus status, Model model) 
	{	
		lotStockValidator.validate(lotstock, result);
		lotStockValidator.ValidateInsert(lotstock, result);
		
		if(result.hasErrors()){
			model.addAttribute("errorType", "insertError");
			return new ModelAndView("formreturnlotstock");
		}
		
		//***  Setting Negative Values***//
		BigDecimal returnGrossWt = CONVERT.multiply(lotstock.getGrossWeight());
		BigDecimal returnNetWt = CONVERT.multiply(lotstock.getNetWeight());
		int returnQty = lotstock.getQuantity() * -1;

		lotstock.setGrossWeight(returnGrossWt);
		lotstock.setNetWeight(returnNetWt);
		lotstock.setQuantity(returnQty);
		
		lotstockDao.insertLotStock(lotstock);		
		JewelStockCore.JewelStock_LotStock_Insert(jewelStockDao, lotstock);
		
		status.setComplete();	
		
		if(lotstock.getLotItemName().equalsIgnoreCase("GoldLotStock")){
			return new ModelAndView("redirect:lotreturnstock.htm?lt_type=gold");
		}
		else{
			return new ModelAndView("redirect:lotreturnstock.htm?lt_type=silver");
		}
	}	
	
	//to Update the record
	@RequestMapping(value = "/formreturnlotstock.htm", method = RequestMethod.POST, params="update")
	public ModelAndView updateLotStockReturn(@ModelAttribute("lotStockReturn") LotStock lotstock, BindingResult result, Model model) {
		
		lotStockValidator.validate(lotstock, result);
		
		if(!result.hasErrors()){	
			lotStockValidator.ValidateUpdate(lotstock, result);
		}
		
		if(result.hasErrors()){
			model.addAttribute("errorType", "updateError");
			return new ModelAndView("formreturnlotstock");
		}
		
		//***  Setting Negative Values ***//
		BigDecimal returnGrossWt = CONVERT.multiply(lotstock.getGrossWeight());
		BigDecimal returnNetWt = CONVERT.multiply(lotstock.getNetWeight());
		int returnQty = lotstock.getQuantity() * -1;

		lotstock.setGrossWeight(returnGrossWt);
		lotstock.setNetWeight(returnNetWt);
		lotstock.setQuantity(returnQty);
		
		lotstockDao.updateLotStock(lotstock);
		
		JewelStockCore.JewelStock_LotStock_Update(jewelStockDao, lotstock);
		
		if(lotstock.getLotItemName().equalsIgnoreCase("GoldLotStock")){
			return new ModelAndView("redirect:lotreturnstock.htm?lt_type=gold");
		}
		else{
			return new ModelAndView("redirect:lotreturnstock.htm?lt_type=silver");
		}
	}
	
}
