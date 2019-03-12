package com.jewellery.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.jewellery.dao.CompanyInfoDao;
import com.jewellery.dao.POSCategoryDao;
import com.jewellery.dao.POSItemDao;
import com.jewellery.dao.PostagItemDao;
import com.jewellery.entity.CompanyInfo;
import com.jewellery.entity.POSCategory;
import com.jewellery.entity.POSItem;
import com.jewellery.entity.POSStock;
import com.jewellery.entity.PostagItem;
import com.jewellery.print.BarCodePrint;
import com.jewellery.validator.positemValid;

@Controller
public class FormPOSItemController
{
	@Autowired(required=false)	
	private POSItemDao positemDao;
	
	@Autowired(required=false)
	private POSCategoryDao poscategoryDao;
	
	@Autowired(required=false)
	private PostagItemDao postagDao;
	
	@Autowired(required=false)
	private CompanyInfoDao companyInfoDao;
	
	/*@Autowired(required=false)
	private POSStockDao posStockDao;*/
	
	@Autowired(required=false)
	private positemValid positemValidate;

	@Autowired(required=false)
	private BarCodePrint barCodePrint;

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
	throws Exception{
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class, dateEditor);	
	}
	
	private String getNewItemCode(){
		
		BigInteger found = postagDao.getposItemCode();
		String itemCodeNo = "PS1000";	 
		String itemcodelist = "PS"+found;
		  
		if(found !=null){
			 itemCodeNo = itemcodelist;
		}
	
        String splitNo = itemCodeNo.substring(2);
        int num = Integer.parseInt(splitNo);	      
        num++;        
        String number = Integer.toString(num);        
        String displayCode = itemCodeNo.substring(0, 2) + number;
        return displayCode;        	
	}
	
	/*
	private String getNewStockCode(){
		
		List <POSStock> stockcodelist = posStockDao.getStockCode();			
		String itemCodeNo = "ST1000";	   
		if(!stockcodelist.isEmpty()){
			 itemCodeNo = stockcodelist.get(0).getTransactionId();
		}
	
        String splitNo = itemCodeNo.substring(2);
        int num = Integer.parseInt(splitNo);	      
        num++;        
        String number = Integer.toString(num);        
        String displayCode = itemCodeNo.substring(0, 2) + number;
        return displayCode;        	
	}*/
	
	//List the  Category Itemmaster page
	@RequestMapping("/positemList.htm")
	public String positemList(@RequestParam(value="Id", required=true)Integer Id,ModelMap model)
	{
		POSCategory poscategory=poscategoryDao.getCategory(Id);
		List<POSItem> posItems=positemDao.listPOSItem();
		model.addAttribute("poscategory", poscategory);
		model.addAttribute("posItems", posItems); 
		return "poscategoryItemlist";
	}
		
	//Creating new item
	@RequestMapping(value="/formPOSitems.htm", method=RequestMethod.GET)	
	public String newForm(@RequestParam(value="Id",required=true)Integer Id, Model model)
	{	
		POSItem POSitems=new POSItem();		
		List<POSCategory> poscategory=poscategoryDao.listCategoryName(Id);
		model.addAttribute("Id",Id);
		model.addAttribute("poscategory",poscategory);
		model.addAttribute("POSitems",POSitems);
		List<CompanyInfo> companyList = companyInfoDao.listCompanyInfo();
		if(companyList.isEmpty()){
			model.addAttribute("posPrintFormat","Laser3x24");
		}else{
			model.addAttribute("posPrintFormat",companyList.get(0).getPosBarcodePrint());
		}
		return "formPOSitems";			
	}
	
	/********cancel button redirect to list********/
	@RequestMapping(value="formPOSitems.htm",method=RequestMethod.POST,params="cancel")
	public String getposList(@RequestParam(value="Id", required=true)Integer Id,@ModelAttribute("POSitems")POSItem positem)
	{
		return "redirect:positemList.htm?Id="+Id;
	}
	
//save the new posItem
@RequestMapping(value="/formPOSitems.htm",method=RequestMethod.POST,params="insert")		
public ModelAndView posItemSave(@RequestParam(value="Id", required=true)Integer Id,@ModelAttribute("POSitems")POSItem positem,BindingResult result,HttpServletRequest req,SessionStatus status,Model model) throws Exception
{
	Integer totpieces = 0;
	
	positemValidate.validate(positem, result);	
	
	if(result.hasErrors())
	{
		ModelMap map = new ModelMap();
		map.put("command", positem);
		map.addAttribute("errorType","insertError");
		model.addAttribute("Id",Id);
		return new ModelAndView("formPOSitems", map);
	}
		PostagItem postag = new PostagItem();
		/*POSStock stockObj = new POSStock();*/
		
		POSCategory poscategory=poscategoryDao.getCategory(Id);	
		poscategory.getPosItems().add(positem);
		
		poscategoryDao.insertCategory(poscategory);
						
		status.setComplete();
		totpieces = positem.getOpeningStockSet();
		String categoryName = positem.getCategoryName();
		/*String stockCode = getNewStockCode();*/
		Integer qtySet = 1;
		Integer totPcs = qtySet * positem.getPiecesPerQty();
		
		StringBuilder itemArray = new StringBuilder(); // For Laser Printer
		List<PostagItem> posTagList = new ArrayList<PostagItem>(); // For other than Laser printers
		
		for(int i=0; i<totpieces; i++)
		{
			String item_Code=getNewItemCode();			
			
			/*stockObj.setBarcodeId(item_Code);  
			stockObj.setItemID(positem.getItemId());
			stockObj.setQtySet(qtySet);
			stockObj.setPps(positem.getPiecesPerQty());
			stockObj.setTotalPieces(totPcs);
			stockObj.setStockDate(positem.getStockDate());
			stockObj.setSalesRate(positem.getSalesRate());
			stockObj.setCostRate(positem.getCostRate());
			stockObj.setMarginP(positem.getMargin());
			stockObj.setMrp(positem.getMRP());	
			stockObj.setCategoryName(positem.getCategoryName());
			stockObj.setItemName(positem.getItemName());
			stockObj.setTransactionId(stockCode);
			stockObj.setTransactionType("Opening Stock");*/
			
			postag.setBarcodeId(item_Code);
			postag.setCategoryName(positem.getCategoryName());
			postag.setPps(positem.getPiecesPerQty());
			postag.setItemName(positem.getItemName());
			postag.setPrintName(positem.getPrintName());
			postag.setCompanyName(positem.getCompanyName());
			postag.setPOSReferenceID(positem.getItemId().toString());
			postag.setTotalpieces(totPcs);
			postag.setPositem(positem);
			postag.setDiscountPercentage(positem.getDiscountPercentage());
			postag.setDate(positem.getStockDate());
			postag.setSalesRate(positem.getSalesRate()); 
			postag.setVatPercentage(positem.getVatPercentage());
			postag.setCostRate(positem.getCostRate());
			postag.setMrpinRs(positem.getMRP());
			postag.setQtyset(qtySet);			
			postag.setMargin(positem.getMargin());
			/*postag.setTransactionId(stockCode);*/
			postag.setTransactionType("Opening Stock");
						
			/*posStockDao.insertPOSStock(stockObj);*/
			poscategoryDao.updateCategorySet(qtySet, categoryName);
			
			String printbarcode=positem.getIsPrint();
			if(printbarcode!=null)
			{
				postag.setStatus("printed");
				itemArray.append(postag.getBarcodeId()+",");
				posTagList.add(postag);
			} 
			else if(printbarcode==null)
			{
				postag.setStatus("Unprinted"); 
			}
			postagDao.save(postag);			 
		}		
		HttpSession session = req.getSession();
		if( ! posTagList.isEmpty()){ // if Print Option Checked 
			List<CompanyInfo> companyList = companyInfoDao.listCompanyInfo();
			if( ! companyList.isEmpty()){ // if Printer Format set in Company Info
				if(companyList.get(0).getPosBarcodePrint().equalsIgnoreCase("TSC")){
					for(PostagItem printPostag: posTagList){
						barCodePrint.posPrintTSC(printPostag, session);
					}
				}else if(companyList.get(0).getPosBarcodePrint().equalsIgnoreCase("Citizen")){
					for(PostagItem printPostag: posTagList){
						barCodePrint.posPrintCitizen(printPostag, session);
					}
				}else if(companyList.get(0).getPosBarcodePrint().equalsIgnoreCase("Zebra")){
					for(PostagItem printPostag: posTagList){
						barCodePrint.posPrintZebra(printPostag, session);
					}
				}else{
					req.setAttribute("itemArray", itemArray);
					req.setAttribute("catId", Id);
					req.setAttribute("printFormat", companyList.get(0).getPosBarcodePrint());
					return new ModelAndView("posBarcodePreview");
				}
			}else{ // if Printer format NOT SET in Company Info set : Default Format
				req.setAttribute("itemArray", itemArray);
				req.setAttribute("catId", Id);
				req.setAttribute("printFormat", "Laser3x24");
				return new ModelAndView("posBarcodePreview");
			}
		}
		return new ModelAndView("redirect:positemList.htm?Id="+Id);
	}

//positem view
		@RequestMapping(value="/viewPOSitems.htm",method=RequestMethod.GET)
		public String viewposForm(@RequestParam(value="posId")Integer posId,@RequestParam(value="Id",required=true)Integer Id,Model model)
		{
			POSItem POSitems=positemDao.getPOSItem(posId);
			model.addAttribute("POSitems",POSitems);
			model.addAttribute("Id", Id);
			return "formPOSitems";
		}
		
		//save the updated posItem form fields
		@SuppressWarnings("unused")
		@RequestMapping(value="/formPOSitems.htm",method=RequestMethod.POST,params="update")
		public ModelAndView updatePos(@RequestParam(value="Id")Integer Id,@ModelAttribute("POSitems")POSItem positem,BindingResult result,SessionStatus status,Model model)throws Exception
		{
			PostagItem postag = new PostagItem();
			POSStock stockObj = new POSStock();
			positemValidate.validate(positem, result);
			positemValidate.updateValidate(positem, result);
			if(result.hasErrors())
			{
				ModelMap map = new ModelMap();
				map.put("command", positem);
				map.addAttribute("errorType","updateError");
				map.put("Id", Id);
				return new ModelAndView("formPOSitems", map);
			}
			
		Integer posId=positem.getItemId();
		POSItem Obj=positemDao.getPOSItem(posId);
		Integer oldQTYSET=Obj.getOpeningStockSet();
		Integer newQTYSET=positem.getOpeningStockSet();
		Integer qtyset_difference;
		
		/******************** Update Category Quantity ********************/
		int cat_qty = newQTYSET - oldQTYSET;
		poscategoryDao.updateCategorySet(cat_qty, positem.getCategoryName());
		
		String categoryName = positem.getCategoryName();
		Integer qtySet = 1;
		Integer totPcs = qtySet * positem.getPiecesPerQty();
		
		/*String stockCode = getNewStockCode();*/
		/*********decreasing the generated barcode if qty/set is modified***********/
		if(oldQTYSET.compareTo(newQTYSET)>0)
		{
			String refernceid=new Integer(posId).toString();
			qtyset_difference=oldQTYSET-newQTYSET;
					
			List<PostagItem> tag=postagDao.getBarcodeID(refernceid,qtyset_difference);
			
			 for (PostagItem tags : tag) {
				String barcodeId=tags.getBarcodeId();
				 postagDao.deleteBarcodeId(barcodeId);//tag table updation
				/* posStockDao.deleteBarcodeId(barcodeId);//stock table updation
*/				
			 }
			}
		/**********increasing extra barcode label if the qty/set is modified********/
		if(newQTYSET.compareTo(oldQTYSET)>0)
		{
			qtyset_difference=newQTYSET-oldQTYSET;
						
			for(int i=0;i<qtyset_difference;i++)
			{
				String item_Code=getNewItemCode();			
				
				/*stockObj.setBarcodeId(item_Code);  
				stockObj.setItemID(positem.getItemId());
				stockObj.setQtySet(qtySet);
				stockObj.setPps(positem.getPiecesPerQty());
				stockObj.setTotalPieces(totPcs);
				stockObj.setStockDate(positem.getStockDate());
				stockObj.setSalesRate(positem.getSalesRate());
				stockObj.setCostRate(positem.getCostRate());
				stockObj.setMarginP(positem.getMargin());
				stockObj.setMrp(positem.getMRP());	
				stockObj.setCategoryName(positem.getCategoryName());
				stockObj.setItemName(positem.getItemName());
				stockObj.setTransactionId(stockCode);
				stockObj.setTransactionType("Opening Stock");
				*/
				
				
				postag.setBarcodeId(item_Code);
				postag.setCategoryName(positem.getCategoryName());
				postag.setItemName(positem.getItemName());
				postag.setPps(positem.getPiecesPerQty());
				postag.setPrintName(positem.getPrintName());
				postag.setCompanyName(positem.getCompanyName());
				postag.setPOSReferenceID(positem.getItemId().toString());
				postag.setTotalpieces(totPcs);
				postag.setPositem(positem);
				postag.setDiscountPercentage(positem.getDiscountPercentage());
                postag.setVatPercentage(positem.getVatPercentage());
				postag.setDate(positem.getStockDate());
				postag.setSalesRate(positem.getSalesRate()); 
				postag.setCostRate(positem.getCostRate());
				postag.setMrpinRs(positem.getMRP());
				postag.setQtyset(qtySet);			
				postag.setMargin(positem.getMargin());
			/*	postag.setTransactionId(stockCode);*/
				postag.setTransactionType("Opening Stock");
							
				/*posStockDao.insertPOSStock(stockObj);*/
				postag.setStatus("Unprinted");				 
				postagDao.save(postag);	
			}
		}
		Integer positemId=positem.getItemId();
		String POSReferenceID=new Integer(positemId).toString();
		BigDecimal mrp=positem.getMRP();
		String printName=positem.getPrintName();
		String companyNmae=positem.getCompanyName();
		BigDecimal salesrate=positem.getSalesRate();
		BigDecimal margin=positem.getMargin();
		BigDecimal costRate=positem.getCostRate();
		BigDecimal discountPercentage=positem.getDiscountPercentage();
		BigDecimal vatPercentage=positem.getVatPercentage();
		Date date=positem.getStockDate();
		List<PostagItem> taglist=postagDao.searchupdateBarcodeLabel(POSReferenceID);
		for(PostagItem postagObj:taglist)
		{
			postagDao.updateBarcodelabel(printName,companyNmae,mrp,salesrate,margin,costRate,discountPercentage,vatPercentage,date,POSReferenceID);
		}
		
			positemDao.updatePOSItem(positem);
			status.setComplete();
			return new ModelAndView("redirect:positemList.htm?Id="+Id);
		}
		
		public  void print(PostagItem positemtag)throws Exception
		{
			
			//String barcodeId=positemtag.getBarcodeId();
			Writer output = null;
		    int no_print = 1;		    
		    
		  /***Constant Values"     This Segment of Code is for Zebra Barcode printer
		    String L1 = "I8,A,001";
		    String L2 = "Q92,024";
		    String L3 = "q831";
		    String L4 = "rN";
		    String L5 = "S1";
		    String L6 = "D15";
		    String L7 = "ZT";
		    String L8 = "JF";
		    String L9 = "OD";
		    String L10 = "R59,0";
		    String L11 = "f100";
		    String L12 = "N";
		    String L13 ="P" + String.valueOf(no_print);
		    @SuppressWarnings("unused")
			String ClientName = "Jewel Mine";
		    File file = new File("ZBar302.prn");
		//  "//ams2/Printer2/"
		    output = new BufferedWriter(new FileWriter(file));
		    output.write(L1);
		    output.write("\n");
		    output.write("\n");
		    output.write("\n");
		    output.write(L2);
		    output.write("\n");
		    output.write(L3);
		    output.write("\n");
		    output.write(L4);
		    output.write("\n");
		    output.write(L5);
		    output.write("\n");
		    output.write(L6);
		    output.write("\n");
		    output.write(L7);
		    output.write("\n");
		    output.write(L8);
		    output.write("\n");
		    output.write(L9);
		    output.write("\n");
		    output.write(L10);
		    output.write("\n");
		    output.write(L11);
		    output.write("\n");
		    output.write(L12);
		    output.write("\n");
		    output.write("B686,57,2,1B,1,3,23,B,\""+barcodeId+"\"");
		    output.write("\n");          
		    output.write("A503,54,2,3,1,1,N,\""+positemtag.getCompanyName()+"\"");
		    output.write("\n");
		    output.write("A505,28,2,3,1,1,N,\""+"MRP:"+positemtag.getMrpinRs()+".00"+"\"");
		    output.write("\n");
		    output.write("A505,78,2,3,1,1,N,\""+"Dis:"+positemtag.getDiscountPercentage()+"\"");
		    output.write("\n");
		 
		    if(positemtag.getPrintName().length()>10)
		    {
		    	output.write("A684,80,2,3,1,1,N,\""+positemtag.getPrintName().substring(0,10)+"\"");	
		    }
		    else
		    {
		    	output.write("A684,80,2,3,1,1,N,\""+positemtag.getPrintName()+"\"");
		    }
		    output.write("\n"); 
		    output.write(L13);
		    output.write("\n");
		    output.close();********/
		    
		    
		     
			  // Constant Values"		This Segment of code is for Citizen barcode printer         
		         String L1 = "^XA"; 
		         String L2 = "^SZ2^JMA"; 
		         String L3 = "^MCY^PMN";
		         String L4 = "^PW758";
		         String L5 = "~JSN";
		         String L6 = "~SD15^MD0";
		         String L7 = "~!G0";				         
		         String L8 = "^PR3,3,3";
		         String L9 = "^JZY";
		         String L10 = "^LH0,0^LRN";
		         String L11 = "^XZ";
		         String L12 = "^XA";
		         String L13 = "^FT571,37";
		         String L14 = "^CI0";		           
		         //String L15 = "REVERSE 283,33,84,21";
		         String L15 ="PQ1,0,1,Y"+ String.valueOf(no_print);
		         String L16 = "^XZ";		       
		         
		         
		        @SuppressWarnings("unused")
				String ClientName = "Jewel Mine";
		        File file = new File("ZBar302.prn");
		        
		        //  "//ams2/Printer2/"
		        output = new BufferedWriter(new FileWriter(file));
		        output.write(L1);	
		        output.write("\n");
		        output.write(L2);
		        output.write("\n");
		        output.write(L3);
		        output.write("\n");
		        output.write(L4);
		        output.write("\n");
		        output.write(L5);
		        output.write("\n");
		        output.write(L6);
		        output.write("\n");
		        output.write(L7);
		        output.write("\n");
		        output.write(L8);
		        output.write("\n");
		        output.write(L9);
		        output.write("\n");
		        output.write(L10);
		        output.write("\n");
		        output.write(L11);
		        output.write("\n");
		        output.write(L12);
		        output.write("\n");
		        output.write(L13);
		        output.write("\n");
		        output.write(L14);				            				          
		        output.write("\n");
		       	            
		        if(positemtag.getPrintName().length() > 15)
		        {
		        	output.write("^A0N,17,23^FD"+positemtag.getPrintName().substring(0,15)+"^FS\"");	
		        }
		        else
		        {
		        	output.write("^A0N,17,23^FD"+positemtag.getPrintName()+"^FS\"");
		        }		            
		        output.write("\n");
		        output.write("^FT571,85");            
		        output.write("\n");
		        output.write("^A0N,17,23^FD"+positemtag.getCompanyName()+"^FS\"");	
		        output.write("\n");     
		        output.write("^FT571,69");
		        output.write("\n");        
		        output.write("^A0N,17,23^FD"+positemtag.getDiscountPercentage()+"%"+"^FS\"");       
		        output.write("\n");	            
		        output.write("^FT571,53");
		        output.write("\n");
		        output.write("^A0N,17,23^FD"+"MRP: "+positemtag.getMrpinRs()+"^FS\"");	
		        output.write("\n");				            
		        output.write("^FO396,24");
		        output.write("\n");
		        output.write("^BY1^BCN,46,N,N^FD>:"+positemtag.getBarcodeId()+"^FS\"");		           
		        output.write("\n");	            
		        output.write("^FT396,91");
		        output.write("\n");
				output.write("^A0N,20,27^FD"+positemtag.getBarcodeId()+"^FS\"");
				output.write("\n");           
		        output.write(L15);		            
		        output.write("\n");	
		        output.write(L16);		             
		        output.write("\n");	
		        output.close();		//Citizen PRN code Ends here
		     
		    DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		    PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		    PrintService pservices = PrintServiceLookup.lookupDefaultPrintService();
		    //int printnbr = 0;
		    DocPrintJob pj = pservices.createPrintJob();

		     try {
		         FileInputStream fis = new FileInputStream("ZBar302.prn");
		         Doc doc = new SimpleDoc(fis, flavor, null);
		         // print the doc as specified 
		         pj.print(doc, aset);
		         fis.close();
		     }
		     catch (Exception ex){
		    	 ex.printStackTrace();
		     }
		     
		  /****** This Segment of Code is For TSC 244 Barcode Printer
		     //Constant Values"	                     	         
	         String L1 = "<xpml><page quantity='0' pitch='10.0 mm'></xpml>SIZE 73.7 mm, 10 mm"; 
	         String L2 = "DIRECTION 0,0";
	         String L3 = "REFERENCE 0,0"; 
	         String L4 = "OFFSET 0 mm";
	         String L5 = "SET PEEL OFF";
	         String L6 = "SET CUTTER OFF";
	         String L7 = "<xpml></page></xpml><xpml><page quantity='1' pitch='10.0 mm'></xpml>SET TEAR ON";
	         String L8 = "CLS";	         
	         String L9 = "BARCODE 542,71,\"128M\",44,0,180,1,2,\""+barcodeId+"\"";
	         String L10 = "CODEPAGE 850";		         		        
	         String L11 ="PRINT 1,"+ String.valueOf(no_print);
	         String L12 = "<xpml></page></xpml><xpml><end/>";
		    
		    
		    @SuppressWarnings("unused")  
			String ClientName = "Jewel Mine";
		    File file = new File("ZBar302.prn");
		    
		//  "//ams2/Printer2/"
            output = new BufferedWriter(new FileWriter(file));	            	            
            output.write(L1);	
            output.write("\n");
            output.write(L2);
            output.write("\n");
            output.write(L3);
            output.write("\n");
            output.write(L4);
            output.write("\n");
            output.write(L5);
            output.write("\n");
            output.write(L6);
            output.write("\n");
            output.write(L7);
            output.write("\n");
            output.write(L8);
            output.write("\n");
            output.write(L9);
            output.write("\n");
            output.write(L10);		           
            output.write("\n");
            output.write("TEXT 571,22,\"1\",180,2,1,\""+positemtag.getBarcodeId()+"\"");		           
            output.write("\n");		    
            if(positemtag.getPrintName().length()>13)
            {
            	output.write("TEXT 374,76,\"6\",180,1,1,\""+positemtag.getPrintName().substring(0,13)+"\"");	
            }
            else
            {
            	output.write("TEXT 374,76,\"6\",180,1,1,\""+positemtag.getPrintName()+"\"");
            }		            
            output.write("\n");
            output.write("TEXT 374,52,\"6\",180,1,1,\""+"MRP "+positemtag.getMrpinRs()+"\"");		            
            output.write("\n");     
	        
	        output.write("TEXT 374,28,\"6\",180,1,1,\""+positemtag.getCompanyName()+"\"");		        
            
            output.write("\n");     
            output.write(L11);		            
            output.write("\n");	            
            output.write(L12);		  
            output.write("\n"); 
		    output.close();		
		     
		    DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		    PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		    PrintService pservices = PrintServiceLookup.lookupDefaultPrintService();
		    //int printnbr = 0;
		    DocPrintJob pj = pservices.createPrintJob();

		     try {
		         FileInputStream fis = new FileInputStream("ZBar302.prn");
		         Doc doc = new SimpleDoc(fis, flavor, null);
		         // print the doc as specified 
		         pj.print(doc, aset);
		         fis.close();
		     }
		     catch (Exception ex){
		    	 ex.printStackTrace();
		     }*********/  
		}
}