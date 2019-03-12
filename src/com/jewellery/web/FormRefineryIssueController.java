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

import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.JewelStockDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.dao.RefineryDao;
import com.jewellery.entity.ItemMaster;
import com.jewellery.entity.JewelStock;
import com.jewellery.entity.RefineryIssue;
import com.jewellery.util.JournalCode;
import com.jewellery.validator.RefineryValidator;

@Controller
public class FormRefineryIssueController extends JournalCode {
	
	@Autowired
	private LedgerDao ledgerDao;
	@Autowired
	private RefineryDao refineryDao;
	@Autowired
	private RefineryValidator refineryValidator;
	@Autowired
	private ItemMasterDao itemmasterDao;
	@Autowired
	private JewelStockDao jstockDao;
	private ItemMaster itemDetails = new ItemMaster();
	String RIitemcd;
	BigDecimal RIGrossWeight;
	BigDecimal RINetWeight;
	BigDecimal RIgrossWeight;
	BigDecimal RInetWt;
	BigDecimal RItotalGrossWt;
	Integer pieces;
	Integer RIpieces;
	int ppq, tp;	
	private final static BigDecimal Negative = new BigDecimal("-1");
	//Date Binding 
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
	throws Exception{
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class,null, dateEditor);
	}	
	// Form View of Refinery
		@RequestMapping(value="/formRefinery", method=RequestMethod.GET)
		public String jobOrderList(@ModelAttribute("refinery") RefineryIssue refinery, Model model){
			model.addAttribute("suppliername", ledgerDao.listallSmithName());		    
			return "formRefinery";
		}
		
		//  listing For RefineryList page
		@RequestMapping(value = "/RefineryList", method = RequestMethod.GET)
		public ModelAndView showForm(@ModelAttribute("refinery") RefineryIssue refinery) {
			ModelMap model = new ModelMap();
			model.put("refineryList", refineryDao.listRefinery());
			return new ModelAndView("RefineryList", model);// this will return all the
		}
		
		//FormRefinery  Cancel 
				@RequestMapping(value="/formRefinery.htm",method=RequestMethod.POST,params="cancel") // cancel button to redirect to customer list page
				public String cancelForm(@ModelAttribute("refinery") RefineryIssue refinery)
				{
					return "redirect:RefineryList.htm";
				}
		//To save the record
		@RequestMapping(value = "/formRefinery.htm", method = RequestMethod.POST,params="insert")
		public ModelAndView addOrder(@ModelAttribute("refinery") RefineryIssue refinery, BindingResult result,SessionStatus status, Model model) 
		{
			refineryValidator.validate(refinery, result);
			
			RIitemcd = refinery.getItemCode();
			RIGrossWeight = refinery.getGrossWeight();
			RINetWeight = refinery.getNetWeight();
			RIpieces=refinery.getPieces();
			
			if(result.hasErrors())
			{
				ModelMap map = new ModelMap();
				map.addAttribute("errorName", "insertError");
				model.addAttribute("suppliername", ledgerDao.listallSmithName());	
				map.put("command", refinery);
				return new ModelAndView("formRefinery",map);
			}
			refinery.setRefineryIssueNo(generateRefineryIssueNO(refinery));
			
			
			//Update stock For Refinery Issue  
			
              List<ItemMaster> itemList = itemmasterDao.searchItemMaster(RIitemcd);
  			for (int i = 0; i < itemList.size(); i++) {
  				ItemMaster imast = (ItemMaster)itemList.get(i);
  				if (imast instanceof ItemMaster) {
  					
	  				itemDetails = (ItemMaster) imast; 
					
	  				RIgrossWeight = itemDetails.getGrossWeight();
	  				RIgrossWeight = RIgrossWeight.subtract(RIGrossWeight);
	  				itemDetails.setGrossWeight(RIgrossWeight);
	  				
	  				RInetWt = itemDetails.getNetWeight();    			
	  				RInetWt = RInetWt.subtract(RINetWeight);   				
	  				itemDetails.setNetWeight(RInetWt);   
	  				
					RItotalGrossWt = itemDetails.getTotalGrossWeight();
	  				RItotalGrossWt = RItotalGrossWt.subtract(RIgrossWeight);
	  				itemDetails.setTotalGrossWeight(RItotalGrossWt);
	  				
	  				pieces=itemDetails.getQty();
	  				pieces=pieces-RIpieces;
	  				itemDetails.setQty(pieces);
	  				
	  				itemmasterDao.updateItemMaster(itemDetails);//update detail in item master 
  				}    				
  			}
	  		//Update Stock Entry In Jewel Stock
	  			JewelStock_RefineryIssue_NegativeEntry(jstockDao, refinery.getGrossWeight(), refinery.getGrossWeight(), refinery.getGrossWeight(),refinery.getPieces(), "", refinery);  				 			
  			refineryDao.insertRefinery(refinery);
			return new ModelAndView("redirect:RefineryList.htm");
		}
		
		/** For View Refinery Issue  Form ***/
		@RequestMapping(value="/viewRefinery.htm",method=RequestMethod.GET)
		public ModelAndView viewForm(@ModelAttribute("refinery")RefineryIssue refinery,@RequestParam(value="refineryId",required=false)Integer refineryId,ModelMap model){
			model.addAttribute("refinery", refineryDao.getRefinery(refineryId));
			model.addAttribute("suppliername", ledgerDao.listallSmithName());	
			return new ModelAndView("formRefinery", model);
		}
		
		/** For Update Refinery Issue  Form ***/
		@RequestMapping(value = "/formRefinery.htm", method = RequestMethod.POST,params="update")
		public ModelAndView update(@ModelAttribute("refinery") RefineryIssue refinery, BindingResult result, Model model) 
		{
			
			refineryValidator.validateUpdate(refinery, result);
			if(result.hasErrors())
			{
				ModelMap map = new ModelMap();
				map.addAttribute("errorName", "updateError");
				model.addAttribute("suppliername", ledgerDao.listallSmithName());	
				map.put("command", refinery);
				return new ModelAndView("formRefinery",map);
			}
			
			 List<RefineryIssue> oldOrderObj = refineryDao.getrefineryId(refinery.getRefineryId());
			  refineryDao.updateRefinery(refinery);
			  
			//Update stock For Refinery Issue  
			  RIitemcd = refinery.getItemCode();
			List<ItemMaster> itemList = itemmasterDao.searchItemMaster(RIitemcd);
            /***
             * if the updated value is greater then the inserted value then add difference value to c.s
             * ****/
            if(refinery.getGrossWeight().compareTo(oldOrderObj.get(0).getGrossWeight())==1 || refinery.getPieces().compareTo(oldOrderObj.get(0).getPieces())==1){
            	for (int i = 0; i < itemList.size(); i++) {
      				ItemMaster imast = (ItemMaster)itemList.get(i);
      				if (imast instanceof ItemMaster) {
    	  				itemDetails = (ItemMaster) imast; 
    	  				
            	BigDecimal oldgrswt=oldOrderObj.get(0).getGrossWeight();
            	BigDecimal newgrswt=refinery.getGrossWeight();
            	BigDecimal diffGrswt=oldgrswt.subtract(newgrswt);
            	BigDecimal	RIgrossWeight = itemDetails.getGrossWeight();
            	RIgrossWeight = RIgrossWeight.subtract(diffGrswt);
            	System.out.println(RIgrossWeight);
            	itemDetails.setGrossWeight(RIgrossWeight);
            	itemDetails.setNetWeight(RIgrossWeight); 
            	itemDetails.setTotalGrossWeight(RIgrossWeight);
            	
            	Integer oldpieces=oldOrderObj.get(0).getPieces();
            	Integer newppieces=refinery.getPieces();
            	Integer finalpieces=oldpieces-newppieces;
            	Integer pieces=itemDetails.getQty();
            	pieces=pieces-finalpieces;
            	System.out.println(pieces);
            	itemDetails.setQty(pieces);
            	
            	itemmasterDao.updateItemMaster(itemDetails);//update detail in item master 
            	JewelStock_RefineryIssue_NegativeEntryUpdate(jstockDao, refinery.getGrossWeight(), refinery.getGrossWeight(), refinery.getGrossWeight(),refinery.getPieces(), "", refinery);
      				}
            	}
            	  /***
                 * if the updated value is smaller then the inserted value then add difference value to c.s
                 * ****/
            }else if(refinery.getGrossWeight().compareTo(oldOrderObj.get(0).getGrossWeight())==-1 || refinery.getPieces().compareTo(oldOrderObj.get(0).getPieces())==-1){
            	for (int i = 0; i < itemList.size(); i++) {
      				ItemMaster imast = (ItemMaster)itemList.get(i);
      				if (imast instanceof ItemMaster) {
    	  				itemDetails = (ItemMaster) imast; 
            	BigDecimal oldgrswt=oldOrderObj.get(0).getGrossWeight();
            	BigDecimal newgrswt=refinery.getGrossWeight();
            	BigDecimal diffGrswt=oldgrswt.subtract(newgrswt);
            	BigDecimal	RIgrossWeight = itemDetails.getGrossWeight();
            	RIgrossWeight = RIgrossWeight.add(diffGrswt);
            	itemDetails.setGrossWeight(RIgrossWeight);
            	itemDetails.setNetWeight(RIgrossWeight); 
            	itemDetails.setTotalGrossWeight(RIgrossWeight);
            	
            	Integer oldpieces=oldOrderObj.get(0).getPieces();
            	Integer newppieces=refinery.getPieces();
            	Integer finalpieces=oldpieces-newppieces;
            	Integer pieces=itemDetails.getQty();
            	pieces=pieces+finalpieces;
            	itemDetails.setQty(pieces);
            	
            	itemmasterDao.updateItemMaster(itemDetails);//update detail in item master 
            	JewelStock_RefineryIssue_NegativeEntryUpdate(jstockDao, refinery.getGrossWeight(), refinery.getGrossWeight(), refinery.getGrossWeight(),refinery.getPieces(), "", refinery);
      				}
            	}
            }
           /* *//***
             * if the updated value is greater then the inserted value then add difference value to c.s
             * ****//*
            if(refinery.getPieces().compareTo(oldOrderObj.get(0).getPieces())==1){
            	Integer oldpieces=oldOrderObj.get(0).getPieces();
            	Integer newppieces=refinery.getPieces();
            	Integer finalpieces=oldpieces-newppieces;
            	Integer pieces=itemDetails.getQty();
            	pieces=pieces-finalpieces;
            	System.out.println(pieces);
            	itemDetails.setQty(pieces);
            	itemmasterDao.updateItemMaster(itemDetails);//update detail in item master 
            }
            *//***
             * if the updated value is smaller then the inserted value then add difference value to c.s
             * ****//*
            else if(refinery.getPieces().compareTo(oldOrderObj.get(0).getPieces())==-1){
            	Integer oldpieces=oldOrderObj.get(0).getPieces();
            	Integer newppieces=refinery.getPieces();
            	Integer finalpieces=oldpieces-newppieces;
            	Integer pieces=itemDetails.getQty();
            	pieces=pieces+finalpieces;
            	itemDetails.setQty(pieces);
            	itemmasterDao.updateItemMaster(itemDetails);//update detail in item master
            }*/
            
          
			return new ModelAndView("redirect:RefineryList.htm"); 
		}
		
		/** RefineryIssue :  Negative Entry - Insert **/
		public static void JewelStock_RefineryIssue_NegativeEntry(JewelStockDao jstockDao,BigDecimal grossWeight, BigDecimal netWeight, BigDecimal totalGrossWeight,Integer qty, String StockType, RefineryIssue refineryIssue)
		{		
			JewelStock jewelStock = new JewelStock();		
						
			jewelStock.setStock_TransType("Refinery Issue");
			jewelStock.setStock_StockType(StockType);
			jewelStock.setStock_TransNO(refineryIssue.getRefineryIssueNo());
			jewelStock.setStock_ItemCode(refineryIssue.getItemCode());
			jewelStock.setStock_TransDate(refineryIssue.getRefineryDate());
			if(refineryIssue.getOrnamentsType().equalsIgnoreCase("Old Gold Ornaments")){
				jewelStock.setStock_CategoryName("Gold Exchange");	
			}else{
				jewelStock.setStock_CategoryName("Silver Exchange");
			}
			jewelStock.setStock_MetalUsed(refineryIssue.getOrnamentsType());
			jewelStock.setStock_MetalType(refineryIssue.getOrnamentsType());		
			jewelStock.setStock_CLGrossWeight(grossWeight.multiply(Negative));
			jewelStock.setStock_CLNetWeight(grossWeight.multiply(Negative));
			jewelStock.setStock_CLTotalGrossWeight(totalGrossWeight.multiply(Negative));
			//jewelStock.setStock_CLTotalPieces(qty*-1);
			jewelStock.setStock_CLQty(qty * -1);
			jstockDao.saveJewelStock(jewelStock); 
			
		}
		/** RefineryIssue :  Negative Entry - Update **/
		public static void JewelStock_RefineryIssue_NegativeEntryUpdate(JewelStockDao jstockDao,BigDecimal grossWeight, BigDecimal netWeight, BigDecimal totalGrossWeight,Integer qty, String StockType, RefineryIssue refineryIssue)
		{		
			// Stock Update ( UnTag )
			String transType="Refinery Issue";
			List<JewelStock> jewelStockDBlist = jstockDao.searchByTransNO_TransType(refineryIssue.getRefineryIssueNo(),transType);
			JewelStock jewelStockDB = jewelStockDBlist.get(0);
			jewelStockDB.setStock_TransNO(refineryIssue.getRefineryIssueNo());
			jewelStockDB.setStock_ItemCode(refineryIssue.getItemCode());
			jewelStockDB.setStock_TransDate(refineryIssue.getRefineryDate());
			if(refineryIssue.getOrnamentsType().equalsIgnoreCase("Old Gold Ornaments")){
				jewelStockDB.setStock_CategoryName("Gold Exchange");	
			}else{
				jewelStockDB.setStock_CategoryName("Silver Exchange");
			}
			jewelStockDB.setStock_MetalUsed(refineryIssue.getOrnamentsType());
			jewelStockDB.setStock_MetalType(refineryIssue.getOrnamentsType());		
			jewelStockDB.setStock_CLGrossWeight(grossWeight.multiply(Negative));
			jewelStockDB.setStock_CLNetWeight(grossWeight.multiply(Negative));
			jewelStockDB.setStock_CLTotalGrossWeight(totalGrossWeight.multiply(Negative));
			//jewelStockDB.setStock_CLTotalPieces(qty*-1);
			jewelStockDB.setStock_CLQty(qty * -1);
			jstockDao.updateJewelStock(jewelStockDB); 
			
		}
}
