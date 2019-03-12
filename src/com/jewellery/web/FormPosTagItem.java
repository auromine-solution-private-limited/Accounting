package com.jewellery.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jewellery.dao.CompanyInfoDao;
import com.jewellery.dao.PostagItemDao;
import com.jewellery.entity.CompanyInfo;
import com.jewellery.entity.PostagItem;
import com.jewellery.print.BarCodePrint;
import com.jewellery.printservice.BarCodePrintService;

@Controller
public class FormPosTagItem{

	@Autowired
	public PostagItemDao postagItemDao;
	
	@Autowired(required=false)
	private CompanyInfoDao companyInfoDao;
			
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value="/postagitem.htm",method=RequestMethod.GET)	
	public String newtagForm(@ModelAttribute("postagitem")PostagItem postagitem,Model model)
	{
		List<PostagItem> positemlist=postagItemDao.getposItemList();
		model.addAttribute("positemlist",positemlist);
		List<CompanyInfo> companyList = companyInfoDao.listCompanyInfo();
		if(companyList.isEmpty()){
			model.addAttribute("posPrintFormat","Laser3x24");
		}else{
			model.addAttribute("posPrintFormat",companyList.get(0).getPosBarcodePrint());
		}
		//model.addAttribute("printed", postagItemDao.getposprintedTagedList());
		return "postagform";
	}
	
	//listing page for posPrintedList 30-1-13
	@RequestMapping(value="/POSPrintedList.htm",method=RequestMethod.GET)	
	public String PrintedListingForm(@ModelAttribute("postagitem")PostagItem postagitem,Model model)
	{
		
		List<PostagItem> posprinteditemlist=postagItemDao.getposPrintedItemList();
		model.addAttribute("posprinteditemlist",posprinteditemlist);
		List<CompanyInfo> companyList = companyInfoDao.listCompanyInfo();
		if(companyList.isEmpty()){
			model.addAttribute("posPrintFormat","Laser3x24");
		}else{
			model.addAttribute("posPrintFormat",companyList.get(0).getPosBarcodePrint());
		}
		//model.addAttribute("printed", postagItemDao.getposprintedTagedList());
		return "POSPrintedList";
	}
	
	//Print POS Bar codes in Multiple Printer Formats
	@RequestMapping(value="/posBarcode.htm",method=RequestMethod.GET)	
	public ModelAndView posBarcodePreview(HttpServletRequest req,HttpSession session,@RequestParam(value="itemArray",required=false)String itemArray,@RequestParam(value="printFormat",required=false)String printFormat) throws Exception
	{
		if(itemArray!=null){
				ArrayList<String> itemCodeList = new ArrayList<String>(Arrays.asList(itemArray.split(",")));
				for(String itemCode : itemCodeList) {
					 List<PostagItem> postagItemList = postagItemDao.getItemCode(itemCode);
					 if(!postagItemList.isEmpty()){
						 postagItemList.get(0).setStatus("printed");
				    	 postagItemDao.update(postagItemList.get(0));
				    	 BarCodePrint barCodePrint = new BarCodePrintService();
				    	 
				    	 if(printFormat.equalsIgnoreCase("TSC")){ // If TSC Printer Format
				    		 barCodePrint.posPrintTSC(postagItemList.get(0), session);
							 System.out.println("Printing in Tsc...");
				    	 }else if(printFormat.equalsIgnoreCase("Zebra")){ // If Zebra Printer Format
				    		 barCodePrint.posPrintZebra(postagItemList.get(0), session);
				    		 System.out.println("Printing in Zebra...");
				    	 }else if(printFormat.equalsIgnoreCase("Citizen")){ // If Citizen Printer Format
				    		 System.out.println("Printing in Citizen...");
				    		 barCodePrint.posPrintCitizen(postagItemList.get(0), session);
				    		
				    	 }
					 }
				}
			req.setAttribute("itemArray",itemArray);
			// Laser Printer Format
			if(printFormat.equalsIgnoreCase("Laser3x24") || printFormat.equalsIgnoreCase("Laser3x30") || printFormat.equalsIgnoreCase("Laser4x48") || printFormat.equalsIgnoreCase("Laser5x65")){
				return new ModelAndView("posBarcode");
			}
		}
		return new ModelAndView("redirect:postagitem.htm");
	}
		
	
	// ( Not In Use ) Print Barcode in Laser Printer through controller
	@RequestMapping(value="/posTagLaser.htm",method=RequestMethod.POST)
	public @ResponseBody synchronized String printBarcodeLaser(@RequestParam(value="itemArray",required=true)String itemArray,HttpServletRequest request,HttpServletResponse response)
	{	
		ArrayList<String> itemCodeList = new ArrayList<String>(Arrays.asList(itemArray.split(",")));
			
		for(String itemCode : itemCodeList) {
			 List<PostagItem> postagItemList = postagItemDao.getItemCode(itemCode);
			 if(!postagItemList.isEmpty()){
				 postagItemList.get(0).setStatus("printed");
		    	 postagItemDao.update(postagItemList.get(0));
			 }
		}
		return "redirect:posBarcode.htm?itemArray="+itemCodeList.toString();
		
		/*	StringBuilder sb = new StringBuilder();
			
			for(String iCode:itemCodeList){
				sb.append(iCode+",");
			}
				
			String filename =  "Barcode7.jasper";
			String reporttype =  "pdf";
			//String Paramtype =  "";
			Map  jasperParameter = new  HashMap();
			jasperParameter.put("itemcode",sb);
			    
			System.out.println("Connection  Established");
			
			*//*********************//*
			
			ServletContext context = request.getSession().getServletContext();
			String reportLocation = context.getRealPath("WEB-INF");
			FileInputStream  fis = new FileInputStream(reportLocation + "/reports/Barcode7.jasper");
			JRDataSource ds = (JRDataSource) myDao.getJdbcTemplate();
		       
			JasperPrint jasperPrint =  JasperFillManager.fillReport(fis, jasperParameter,ds);
				
			*//*********************//*
			
			System.out.println("Report Created... in  "+reporttype +" Format");
			OutputStream ouputStream =  response.getOutputStream();
			JRExporter exporter = null;
			
			if( "pdf".equalsIgnoreCase(reporttype)  )
			{
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition",  "inline; filename=\"Barcode.pdf\"");
				
				exporter = new  JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,  jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,  ouputStream);
			}else if(  "rtf".equalsIgnoreCase(reporttype)  )
			{
				response.setContentType("application/rtf");
				response.setHeader("Content-Disposition",  "inline; filename=\"Barcode.rtf\"");
				
				exporter = new  JRRtfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,  jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,  ouputStream);
			}else if(  "html".equalsIgnoreCase(reporttype)  )
			{
				response.setContentType("application/html");
				response.setHeader("Content-Disposition",  "inline; filename=\"Barcode.html\"");
				
				exporter = new  JRHtmlExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,  jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,  ouputStream);
				exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,"image?image=");
			}else if( "xls".equalsIgnoreCase(reporttype)  )
			{
				response.setContentType("application/xls");
				response.setHeader("Content-Disposition",  "inline; filename=\"Barcode.xls\"");
				
				exporter = new  JRXlsExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,  jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,  ouputStream);
			}else if(  "csv".equalsIgnoreCase(reporttype)  )
			{
				response.setContentType("application/csv");
				response.setHeader("Content-Disposition",  "inline; filename=\"Barcode.csv\"");
				
				exporter = new  JRCsvExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,  jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,  ouputStream);
			}
			
			try
			{
				exporter.exportReport();
			}catch  (JRException e)
			{
				throw new  ServletException("RJMS JRException :"+e);
			}finally
			{
				if (ouputStream !=  null)
				{
				try{
					ouputStream.close();
					ouputStream.flush();
					return;
				}catch (IOException  ex){
					System.out.println("RJMS JRException :"+ex);
				}
				}
			}*/
	}
	
	/**barcode printing option in Barcode Printer
	@RequestMapping(value="/postagbarcode.htm",method=RequestMethod.POST)
	public @ResponseBody synchronized void printBarcode(@RequestParam(value="posicode",required=true)String posicode) throws Exception
	{		
	       
	   //System.out.println(file.getAbsolutePath());
	    //  "//ams2/Printer2/"   
	   	     
	     int id=Integer.parseInt(posicode);
	     PostagItem posQuery=postagItemDao.getpostagId(id);
	     Writer output = null;
	     int no_print = 1;	     
	     
	   /***Constant Values"  This Segment of Code is Zebra Barcode Printer
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
	    
	    //System.out.println(file.getAbsolutePath());
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
	     output.write("B686,57,2,1B,1,3,23,B,\""+posQuery.getBarcodeId()+"\"");
	     output.write("\n");          
	     output.write("A503,54,2,3,1,1,N,\""+posQuery.getCompanyName()+"\"");
	     output.write("\n");
	     output.write("A505,28,2,3,1,1,N,\""+"MRP:"+posQuery.getMrpinRs()+"\"");
	     output.write("\n");
	     output.write("A505,78,2,3,1,1,N,\""+"Dis:"+posQuery.getDiscountPercentage()+"\"");
	     output.write("\n");
	  
	     if(posQuery.getPrintName().length()>10)
	     {
	     	output.write("A684,80,2,3,1,1,N,\""+posQuery.getPrintName().substring(0,10)+"\"");	
	     }
	     else
	     {
	     	output.write("A684,80,2,3,1,1,N,\""+posQuery.getPrintName()+"\"");
	     }
	     output.write("\n"); 
	     output.write(L13);
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
	          /* print the doc as specified 
	          pj.print(doc, aset);
	          fis.close();
	      }
	      catch (Exception ex){
	     	 ex.printStackTrace();
	      }	
	     
	      posQuery.setStatus("printed");
	      postagItemDao.update(posQuery);***/
	      
	        
/**			//This Segment of Code is For TSC 244 Barcode Printer
	     	//Constant Values"		         
	         String L1 = "<xpml><page quantity='0' pitch='10.0 mm'></xpml>SIZE 73.7 mm, 10 mm"; 
	         String L2 = "DIRECTION 0,0";
	         String L3 = "REFERENCE 0,0";
	         String L4 = "OFFSET 0 mm";
	         String L5 = "SET PEEL OFF";
	         String L6 = "SET CUTTER OFF";
	         String L7 = "<xpml></page></xpml><xpml><page quantity='1' pitch='10.0 mm'></xpml>SET TEAR ON";
	         String L8 = "CLS";	         
	         String L9 = "BARCODE 542,71,\"128M\",44,0,180,1,2,\""+posQuery.barcodeId +"\"";
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
	            output.write("TEXT 571,22,\"1\",180,2,1,\""+posQuery.getBarcodeId()+"\"");		           
	            output.write("\n");
	            
	            if(posQuery.getPrintName().length()>13)
	            {
	            	output.write("TEXT 374,76,\"6\",180,1,1,\""+posQuery.getPrintName().substring(0,13)+"\"");	
	            }
	            else
	            {
	            	output.write("TEXT 374,76,\"6\",180,1,1,\""+posQuery.getPrintName()+"\"");
	            }		            
	            output.write("\n");
	            output.write("TEXT 374,52,\"6\",180,1,1,\""+"MRP "+posQuery.getMrpinRs()+"\"");		            
	            output.write("\n");     
		        
		        output.write("TEXT 374,28,\"6\",180,1,1,\""+posQuery.getCompanyName()+"\"");		        
	            
	            output.write("\n");     
	            output.write(L11);		            
	            output.write("\n");	            
	            output.write(L12);		 
	            output.write("\n");
	            output.close();		
	             
	            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
	            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
	            PrintService pservices = PrintServiceLookup.lookupDefaultPrintService();
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
	             
	             posQuery.setStatus("printed");
	    	     postagItemDao.update(posQuery);    
	
	}*/
	@RequestMapping(value="/POSRolList.htm",method=RequestMethod.GET)	
	public ModelAndView POSLowStockList(@ModelAttribute PostagItem postagitem,Model model)
	{		
		model.addAttribute("posStocklist",postagItemDao.POSlowItemStockList());
		model.addAttribute("SLCount", postagItemDao.POSlowItemStockList().size());		
		return  new ModelAndView("POSRolList");
	}	
}
