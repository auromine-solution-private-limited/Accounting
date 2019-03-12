package com.jewellery.web;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.jewellery.core.JewelStockCore;
import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.JewelStockDao;
import com.jewellery.dao.TransferDao;
import com.jewellery.entity.ItemMaster;
import com.jewellery.entity.JewelStock;
import com.jewellery.entity.Transfer;
import com.jewellery.validator.TagIssueValidator;
import com.jewellery.validator.TransferValidator;
import com.jewellery.dao.LotStockDao;

@Controller
public class FormTransferController {
	
	@Autowired
	private TransferDao transferDao;
	
	@Autowired
	private ItemMasterDao itemmasterDao;
	
	@Autowired 
	private TransferValidator transferValidator;
		
	@Autowired
	private TagIssueValidator tagIssueValidaor;
	@Autowired
	private LotStockDao lotstockDao;
	
	@Autowired
	JewelStockDao jewelStockDao;
	private final static BigDecimal Negative=new BigDecimal("-1");
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
	throws Exception{
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class,null, dateEditor);
	}
	
	@RequestMapping(value="/tagissue.htm",method=RequestMethod.GET)
	public ModelAndView forTagissue(@ModelAttribute("tagissue")Transfer treansfer)
	{
		return new ModelAndView("formtagissue");
	}
	
	//Transfer list url mapping
	@RequestMapping(value="/transfer",method=RequestMethod.GET)
	public ModelAndView listtransfer(@ModelAttribute("show")Transfer transfer){
		Map<String,Object> model=new HashMap<String,Object>();
		model.put("transferList", transferDao.listTransfer());
		return new ModelAndView("transfer",model);
	}
	
	//Tag issue url mapping
	@RequestMapping(value="/tagissuelist.htm",method=RequestMethod.GET)
	public ModelAndView listtagissue(){
		Map<String,Object> model=new HashMap<String,Object>();
		model.put("transferList", transferDao.listTransfer());
		return new ModelAndView("tagissue",model);
	}
	
	@RequestMapping(value="/formtransfer",method=RequestMethod.GET)
	public ModelAndView showForm(@ModelAttribute("Transfers")Transfer transfer){
		ModelMap model = new ModelMap();
		model.put("transferNO", getdisplaycode1());
		model.put("itemname",itemmasterDao.listAllitemCode());
		model.put("itemWeight",itemmasterDao.listItemWeight()); 
		model.put("goldWeight", itemmasterDao.listGoldWeight().get(0));
		model.put("silverWeight", itemmasterDao.listSilverWeight().get(0));
		
		return new ModelAndView("formtransfer",model);
	}

	//First search Method
	@RequestMapping(value="/formtransfer",method=RequestMethod.POST,params="f")
	public ModelAndView getForm(@ModelAttribute("Transfers") Transfer transfer,BindingResult result,Model model){
		transferValidator.validate(transfer, result);
		String item_code = transfer.getFromItemNo();
		
		if(result.hasErrors()){

			ModelMap map = new ModelMap();
            map.put("f",itemmasterDao.getItemsByCode(item_code));
			map.put("itemWeight",itemmasterDao.listItemWeight()); 
			map.put("goldWeight", itemmasterDao.listGoldWeight().get(0));
			map.put("silverWeight", itemmasterDao.listSilverWeight().get(0));

					return new ModelAndView("formtransfer",map);
		}
		ModelMap map = new ModelMap();
        map.put("f",itemmasterDao.getItemsByCode(item_code));
		map.put("itemWeight",itemmasterDao.listItemWeight()); 
		map.put("goldWeight", itemmasterDao.listGoldWeight().get(0));
		map.put("silverWeight", itemmasterDao.listSilverWeight().get(0));

		//System.out.println("printing item code" + model);
		return new ModelAndView("formtransfer",map);
	}
	
//For Tag issue item code searching
	@RequestMapping(value="/formtagissue.htm" ,method=RequestMethod.POST,params="issue")
	public ModelAndView formTagissue(@ModelAttribute("tagissue")Transfer transfer, BindingResult result, Model model)
	{
		tagIssueValidaor.validate(transfer, result);
		
		String tag=transfer.getTagissue();
		
		List<ItemMaster> tagList = itemmasterDao.getItemsByCode(tag);
		
		if(tagList == null || tagList.isEmpty()){
			model.addAttribute("errorTag","Please Enter the Valid Tag No");	
		}		
		
		if(result.hasFieldErrors()){
			ModelMap map=new ModelMap();			
			return new ModelAndView("formtagissue",map);
		}
		
		model.addAttribute("tagissuecode", itemmasterDao.getItemsByCode(tag));
		return new ModelAndView("formtagissue");
	}
	
	//second search button method 
		@RequestMapping(value="/formtransfer",method=RequestMethod.POST,params="f1")
		public ModelAndView secondButton(@ModelAttribute("Transfers") Transfer transfer,BindingResult result,Model model){
			
			transferValidator.validate2(transfer, result);
			
			String item_code = transfer.getToItemNo();
		
			if(result.hasErrors()){

				ModelMap map = new ModelMap();
				map.put("f1",itemmasterDao.getItemsByCode(item_code));
				map.put("itemWeight",itemmasterDao.listItemWeight()); 
				map.put("goldWeight", itemmasterDao.listGoldWeight().get(0));
				map.put("silverWeight", itemmasterDao.listSilverWeight().get(0));
				
				return new ModelAndView("formtransfer",map);
		}	
			ModelMap map = new ModelMap();
			map.put("f1",itemmasterDao.getItemsByCode(item_code));
			map.put("itemWeight",itemmasterDao.listItemWeight()); 
			map.put("goldWeight", itemmasterDao.listGoldWeight().get(0));
			map.put("silverWeight", itemmasterDao.listSilverWeight().get(0));
		
			return new ModelAndView("formtransfer",map);
		}

//this is a method for getting journal Code
	public String getdisplaycode1(){
		
		String displayCode = null;
		BigInteger found = transferDao.getTransferCode();
		String transferCodeNo = "TR100000";
		String transferNoList = "TR"+found;
		
		if(found != null){
			 transferCodeNo = transferNoList; 	
		}

	    String splitNo = transferCodeNo.substring(2);
	    int num = Integer.parseInt(splitNo);   
	    num++;        
	    String number = Integer.toString(num);        
	    displayCode = transferCodeNo.substring(0, 2) + number;
		return displayCode;			
	}	

//Canceling request mapping				
	@RequestMapping(value="/formtransfer.htm",method=RequestMethod.POST, params="cancel") 
	public String cancelForm(@ModelAttribute("Transfers")Transfer transfer)
	{		
		return "redirect:transfer.htm";
	}
	//Canceling request mapping	for tag issue			
		@RequestMapping(value="/formtagissue.htm",method=RequestMethod.POST, params="cancel") 
		public String canceltagForm(@ModelAttribute("tagissue")Transfer transfer)
		{		
			return "redirect:tagissuelist.htm";
		}
//Posting the tag issue item details to DB
	@RequestMapping(value="/formtagissue.htm",method=RequestMethod.POST,params="save")
	public ModelAndView saveTagissue(@ModelAttribute("tagissue")Transfer transfer,BindingResult result,SessionStatus status, Model model)
	{
		tagIssueValidaor.validate(transfer, result);
	
		String tag=transfer.getTagissue();
		
		List<ItemMaster> tagList = itemmasterDao.getItemsByCode(tag);
		
		if(tagList == null || tagList.isEmpty()){
			model.addAttribute("errorTag","Please Enter the Valid Tag No");	
		}		
		
		if(result.hasErrors()){
			ModelMap map=new ModelMap();
			map.put("command", transfer);
			return new ModelAndView("formtagissue",map);
		}
		
		String transferNo=getdisplaycode1();
		transfer.setTransferNO(transferNo);
		
		transferDao.insertTransfer((Transfer)transfer);
		
		
		// Stock Updation After Issue
		List<ItemMaster> itemList=itemmasterDao.searchItemMaster(tag);
		ItemMaster itemDetails;
		BigDecimal grosswt;
		BigDecimal totgwt;
		int nopcs;
		int nop;
		int totpcs;
		
		for (int i = 0; i < itemList.size(); i++) {
			ItemMaster imast = (ItemMaster)itemList.get(i);
			
			if (imast instanceof ItemMaster) {	
				itemDetails = (ItemMaster) imast;
				
				nopcs=itemDetails.getQty();
				nop=itemDetails.getPiecesPerQty();
				totgwt=itemDetails.getTotalGrossWeight();
				grosswt = itemDetails.getGrossWeight();
				totpcs=itemDetails.getTotalPieces();
			
				if(nopcs <= 0)
				{
					nopcs=nopcs-0;
					nop = nop - 0;
					totpcs=totpcs * nopcs;
					BigDecimal npcs = new BigDecimal(nopcs);
					totgwt = grosswt.multiply(npcs);
					//totpcs=totpcs * nopcs;
				
				}
				else{
					nopcs=nopcs-1;
					nop = nop - 1;
					BigDecimal npcs = new BigDecimal(nopcs);
					totgwt = grosswt.multiply(npcs);
					totpcs=totpcs * nopcs;
				}		
			
				itemDetails.setTotalPieces(totpcs);
				itemDetails.setQty(nopcs);
				itemDetails.setTotalGrossWeight(totgwt);
				itemmasterDao.updateItemMaster(itemDetails);
				
				/** Jewel Stock Updation **/
				JewelStockCore.jewelSTagIssueUpdation(jewelStockDao,"Tag Issue",transfer.getTransferNO(),transfer.getTransferDate()
						,transfer.getTagissue(),transfer.getCategory(),transfer.getSubcategory(),
						transfer.getItemName(),itemDetails.getMetalUsed(),transfer.getMetaltype(),transfer.getTransferType(),transfer.getItemweght(),transfer.getItemweght(),transfer.getItemweght(),transfer.getItemqtset(),transfer.getItemqtset());
			}
			if(transfer.getTransferType().equals("GoldLotStock") || transfer.getTransferType().equals("SilverLotStock"))
			{
			String tagNo=transfer.getTagissue();
			lotstockDao.tagissueUpdate(tagNo);
			}
		}
		return new ModelAndView("redirect:tagissuelist.htm");
	}
		
	//transfer insert method
	@SuppressWarnings("unused")
	@RequestMapping(value="/formtransfer",method=RequestMethod.POST,params="insert")
	public ModelAndView insertForm(@ModelAttribute("Transfers")Transfer transfer,BindingResult result,SessionStatus status){
		
		BigDecimal FromGrossWt = transfer.getFromGrossWeight();
		transfer.setFromNetWeight(FromGrossWt);
		transfer.setFromTotalGrossWeight(FromGrossWt);
		
		BigDecimal ToGrossWeight = transfer.getToGrossWeight();
		transfer.setToNetWeight(ToGrossWeight);
		transfer.setToTotalGrossWeight(ToGrossWeight);
		
		transferValidator.validate(transfer, result);
		transferValidator.validate2(transfer, result);//add for bug number 2057
		if (result.hasErrors()) {
			ModelMap map = new ModelMap();
			map.put("command", map);
			map.put("itemWeight",itemmasterDao.listItemWeight()); 
			map.put("goldWeight", itemmasterDao.listGoldWeight().get(0));
			map.put("silverWeight", itemmasterDao.listSilverWeight().get(0));
			String item_code2 = transfer.getToItemNo();
			String item_code1 = transfer.getToItemNo(); 
		
			if(item_code2!="" && item_code1!=""){
				if(item_code1==item_code2){
					map.addAttribute("errorTypes","Same ItemCode Cannot Be Used");	
				}
			}
			return new ModelAndView("formtransfer",map);
		}
		//add for bug number 2052
		String transferNo=getdisplaycode1();
		transfer.setTransferNO(transferNo);
		
			transferDao.insertTransfer((Transfer)transfer);

			//String trans_type = transfer.getTransferType();
		   String frmitemcd = transfer.getFromItemNo();
		   String toitemcd = transfer.getToItemNo();
	 	  
		   BigDecimal gross = transfer.getFromGrossWeight(); 
	 	   BigDecimal gross1 = transfer.getToGrossWeight();
	 	   BigDecimal frm_transferWt = transfer.getFromTotalGrossWeight();
	 	   BigDecimal to_transferWt = transfer.getToTotalGrossWeight();
		  
	 	   int frmpcs = transfer.getFromQty();
		   int topcs = transfer.getToQty();
		   int pcs = transfer.getFromPieces();
		   int to_pcs = transfer.getToPieces();
			//	 BigDecimal f1 = BigDecimal.parseBigDecimal(str);
		    BigDecimal grosswt = gross;
		    List<ItemMaster> itemList = itemmasterDao.searchItemMaster(frmitemcd);
			
			Object transferWeight = grosswt;
		
				
			ItemMaster itemDetails;
			BigDecimal frmnetwt;
			int nopcs;
			int ppq;
			int nop;
			int totpcs;
			for (int i = 0; i < itemList.size(); i++) {
				ItemMaster imast = (ItemMaster)itemList.get(i);
				if (imast instanceof ItemMaster) {
				itemDetails = (ItemMaster) imast;
				BigDecimal frmgrosswt = itemDetails.getGrossWeight();
				frmnetwt = itemDetails.getNetWeight();
				BigDecimal frmtotgrosswt = itemDetails.getTotalGrossWeight();
				nopcs = itemDetails.getQty();
				ppq = itemDetails.getPiecesPerQty();
				nop = itemDetails.getTotalPieces();
				frmitemcd = frmitemcd.toUpperCase();
				 
				if ( (frmitemcd.compareTo("IT100001")==0) | (frmitemcd.compareTo("IT100003" )==0) | (frmitemcd.compareTo("IT100004")==0) | (frmitemcd.compareTo("IT100005")==0) | (frmitemcd.compareTo("IT100006")==0) | (frmitemcd.compareTo("IT100008")==0) | (frmitemcd.compareTo("IT100009")==0) | (frmitemcd.compareTo("IT100010")==0)|(frmitemcd.compareTo("IT100011")==0|(frmitemcd.compareTo("IT100012")==0)) )
				 { // Reduce stock from untagged items
					frmgrosswt = frmgrosswt.subtract(frm_transferWt);
					frmnetwt = frmnetwt.subtract(frm_transferWt);
					frmtotgrosswt = frmtotgrosswt.subtract(frm_transferWt);
					nopcs = nopcs - frmpcs;	
					nop = nop - pcs; 
					//System.out.println("Untagged : Deducting gross from " + frmitemcd + " " + frmgrosswt + " " + frmtotgrosswt + " " + nop);
					itemDetails.setTotalPieces(nop);
				}else if(itemDetails.getMetalUsed().equalsIgnoreCase("Gold Loose Stock") || itemDetails.getMetalUsed().equalsIgnoreCase("Silver Loose Stock"))
				{
					frmgrosswt = frmgrosswt.subtract(frm_transferWt);
					frmnetwt = frmnetwt.subtract(frm_transferWt);
					frmtotgrosswt = frmtotgrosswt.subtract(frm_transferWt);
					nopcs = nopcs - frmpcs;	
					nop = nop - pcs;  /** FOR SGH client */
				}
				else
				{ //Reduce stock from  tagged items
					/*frmtotgrosswt = frmtotgrosswt.subtract(frm_transferWt);/** FOR SGH client */
					nopcs = nopcs - frmpcs;		
					totpcs= nopcs * ppq;
					//System.out.println("Tagged : Deducting gross from " + frmitemcd + " "  + frmtotgrosswt);
					itemDetails.setTotalPieces(totpcs);
				}			
				
			    itemDetails.setGrossWeight(frmgrosswt);
			    itemDetails.setNetWeight(frmnetwt);
				itemDetails.setTotalGrossWeight(frmtotgrosswt);
				itemDetails.setQty(nopcs);			
				itemmasterDao.updateItemMaster(itemDetails);
				
				/** Jewel Stock Untag / tagged Postive From Entry **/
				JewelStockCore.JewelStock_Negative_Insert_FromTransfer(jewelStockDao, transfer, itemDetails);
				}
			}
			
			//To Item
			grosswt = gross1;
			List<ItemMaster> itemList1 = itemmasterDao.searchItemMaster(toitemcd);
			transferWeight = grosswt;
			
			for (int i = 0; i < itemList1.size(); i++) {
				ItemMaster imast = (ItemMaster)itemList1.get(i);
				if (imast instanceof ItemMaster) {
				
				itemDetails = (ItemMaster) imast;
				
				BigDecimal togrosswt = itemDetails.getGrossWeight();
				BigDecimal tototgrosswt = itemDetails.getTotalGrossWeight();
				frmnetwt = itemDetails.getNetWeight();
				BigDecimal tonetwt = itemDetails.getNetWeight();
				nopcs = itemDetails.getQty();
				ppq = itemDetails.getPiecesPerQty();
				nop = itemDetails.getTotalPieces();
				toitemcd = toitemcd.toUpperCase();	
				
				if ( (toitemcd.compareTo("IT100001")==0) | (toitemcd.compareTo("IT100003" )==0) | (toitemcd.compareTo("IT100004")==0) | (toitemcd.compareTo("IT100005")==0) | (toitemcd.compareTo("IT100006")==0) | (toitemcd.compareTo("IT100008")==0) | (toitemcd.compareTo("IT100009")==0) | (toitemcd.compareTo("IT100010")==0)| (toitemcd.compareTo("IT100011")==0)| (toitemcd.compareTo("IT100012")==0))

				{ //add to untagged items
				   	togrosswt = togrosswt.add(to_transferWt);
			    	tonetwt = tonetwt.add(to_transferWt);
			    	tototgrosswt = tototgrosswt.add(to_transferWt); 
			    	nopcs = nopcs + topcs;
			    	nop = nop + to_pcs; 
			    	itemDetails.setTotalPieces(nop);
			    	//System.out.println("Untagged : Adding gross to " + toitemcd + " " + togrosswt + " " + tototgrosswt + " " +transferWeight+ " " + nop);
			    	
				}else if(itemDetails.getMetalUsed().equalsIgnoreCase("Gold Loose Stock") || itemDetails.getMetalUsed().equalsIgnoreCase("Silver Loose Stock"))
				{
					togrosswt = togrosswt.add(to_transferWt);
			    	tonetwt = tonetwt.add(to_transferWt);
			    	tototgrosswt = tototgrosswt.add(to_transferWt); 
			    	nopcs = nopcs + topcs;
			    	nop = nop + to_pcs; 	 	/** FOR SGH client */
				}				
			    else
			    { // add to tagged items
			    	/*tototgrosswt = tototgrosswt.add(to_transferWt);
			      	nopcs = nopcs + topcs;
			      	totpcs = nopcs * ppq;
			      	itemDetails.setTotalPieces(totpcs);*/ /** FOR SGH client */
			    	//System.out.println("Tagged : Adding gross to " + toitemcd + " " + togrosswt + " " + tototgrosswt+ " " + transferWeight);
			    }
			
				itemDetails.setGrossWeight(togrosswt);
				itemDetails.setNetWeight(tonetwt);
				itemDetails.setTotalGrossWeight(tototgrosswt);
				itemDetails.setQty(nopcs);				
				itemmasterDao.updateItemMaster(itemDetails);
				
				/** Jewel Stock Untag / tagged Postive TO Entry **/
				JewelStockCore.JewelStock_Positive_Insert_ToTransfer(jewelStockDao, transfer, itemDetails);
				}
			}
		
			return new ModelAndView("redirect:transfer.htm");
		}
	
	@SuppressWarnings("unused")
	private void jewelSTagIssueUpdation(String tagissueType,String transferNo,Date tagissueDate
			,String itemcode,String categoryName,String subcategory,String itemName,String metaltype,String transferType,BigDecimal itemweght,BigDecimal totgwt, BigDecimal totgwt2, int itemqtset,int totalpieces){
		JewelStock jewelStock = new JewelStock();
		jewelStock.setStock_TransType(tagissueType);
		jewelStock.setStock_TransNO(transferNo);
		jewelStock.setStock_TransDate(tagissueDate);
		jewelStock.setStock_ItemCode(itemcode);
		jewelStock.setStock_CategoryName(categoryName);
		jewelStock.setStock_SubCategoryName(subcategory);
		jewelStock.setStock_ItemName(itemName);
		jewelStock.setStock_MetalType(metaltype);
		jewelStock.setStock_StockType(transferType);
		jewelStock.setStock_CLGrossWeight(itemweght.multiply(Negative));
		jewelStock.setStock_CLNetWeight(totgwt.multiply(Negative));
		jewelStock.setStock_CLTotalGrossWeight(totgwt2.multiply(Negative));
		jewelStock.setStock_CLQty(itemqtset*-1);
		jewelStock.setStock_CLTotalPieces(totalpieces*-1);
		jewelStockDao.saveJewelStock(jewelStock);
		
	}
	}