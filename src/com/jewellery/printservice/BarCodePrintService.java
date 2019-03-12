package com.jewellery.printservice;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.jewellery.entity.ItemMaster;
import com.jewellery.entity.PostagItem;
import com.jewellery.print.BarCodePrint;

@Component
/** Contains Bar Code methods for Ornament and POS Formats **/
public class BarCodePrintService implements BarCodePrint {

	// POS TSC 244 Bar code Print Format
	public void posPrintTSC(PostagItem positemtag, HttpSession session)
	{
		String barcodeId=positemtag.getBarcodeId();
		Writer output = null;
	    int no_print = 1;		    
	
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
	    
	    String currentUser = "";
		if(session != null){
			currentUser = session.getAttribute("username").toString();
		}			
	    File file = new File(currentUser+"_TSCBar302.prn");
	    
	    //  "//ams2/Printer2/"
	    try{
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
	    DocPrintJob pj = pservices.createPrintJob();
	     try {
	         FileInputStream fis = new FileInputStream(currentUser+"_TSCBar302.prn");
	         Doc doc = new SimpleDoc(fis, flavor, null);
	         // print the doc as specified 
	         pj.print(doc, aset);
	         fis.close();
	     }
	     catch (Exception ex){
	    	 ex.printStackTrace();
	     }
	    }catch(IOException ie){
	    	ie.printStackTrace();
	    }
	}
	
	// POS ZEBRA Bar code Print Format
	public void posPrintZebra(PostagItem positemtag, HttpSession session)
	{
		String barcodeId=positemtag.getBarcodeId();
		Writer output = null;
	    int no_print = 1;		    
	    
	    //Constant Values"     
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
	    
	    String currentUser = "";
		if(session != null){
			currentUser = session.getAttribute("username").toString();
		}			
	    File file = new File(currentUser+"_ZBar302.prn");
	    //  "//ams2/Printer2/"
	    try{
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
	    output.close();		
	     
	    DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
	    PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
	    PrintService pservices = PrintServiceLookup.lookupDefaultPrintService();
	    DocPrintJob pj = pservices.createPrintJob();

	     try {
	         FileInputStream fis = new FileInputStream(currentUser+"_ZBar302.prn");
	         Doc doc = new SimpleDoc(fis, flavor, null);
	         // print the doc as specified 
	         pj.print(doc, aset);
	         fis.close();
	     }
	     catch (Exception ex){
	    	 ex.printStackTrace();
	     }	
	    }catch(IOException ie){
	    	ie.printStackTrace();
	    }
	}
	
	// POS CITIZEN Bar code Print Format 
	public void posPrintCitizen(PostagItem positemtag, HttpSession session)
	{
	  try{
		Writer output = null;
	    int no_print = 1;		    
		
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
         String L13 = "^FT571,40";
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
       	            
        if(positemtag.getPrintName().length() > 12)
        {
        	output.write("^ADN,18,10^FD"+positemtag.getPrintName().substring(0,12)+"^FS\"");	
        }
        else
        {
        	output.write("^ADN,18,10^FD"+positemtag.getPrintName()+"^FS\"");
        }		            
        output.write("\n");
        output.write("^FT571,88");            
        output.write("\n");
        if(positemtag.getDiscountPercentage().signum() != 0){
	        output.write("^ADN,18,10^FD"+"Disc:"+positemtag.getDiscountPercentage()+"%"+"^FS\"");	
	        output.write("\n");	         
        }
        output.write("^FT388,32");       
        output.write("\n");   
        if(positemtag.getCompanyName().length() > 13){
        	output.write("^ADN,18,10^FD"+positemtag.getCompanyName().substring(0, 13)+"^FS\"");
        }
        else{
        	output.write("^ADN,18,10^FD"+positemtag.getCompanyName()+"^FS\"");
        }
        output.write("\n");	            
        output.write("^FT571,64");
        output.write("\n");
        output.write("^ADN,18,10^FD"+"Rs."+positemtag.getMrpinRs()+"^FS\"");	
        output.write("\n");				            
        output.write("^FO388,40");
        output.write("\n");
        output.write("^BY1^BCN,38,N,N^FD>:"+positemtag.getBarcodeId()+"^FS\"");		           
        output.write("\n");	            
        output.write("^FT398,96");
        output.write("\n");
		output.write("^A0N,17,23^FD"+positemtag.getBarcodeId()+"^FS\"");
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
  	    }catch(IOException ie){
  	    	ie.printStackTrace();
  	    }
	}
	
	// Ornament TSC Bar code print Format 
	/*public synchronized void ornamentPrintTSC(ItemMaster itemmaster, HttpSession session)	
	{					
		Writer output = null;
        int no_print = 1;
        String itemCode=itemmaster.getItemCode();
        String itemName=itemmaster.getItemName();
        BigDecimal grossWeight=itemmaster.getGrossWeight();				            
        String itemseal=itemmaster.getItemseal();
        String SupplierName = itemmaster.getManufacturerSeal();         
        
        This segment of code is for the TSC 244 Barcode printer*  
         // Constant Values" 
         String L1 = "SIZE 73.7 mm, 10 mm"; 
         String L2 = "GAP 3 mm, 0 mm";
         String L3 = "SPEED 2";
         String L4 = "DENSITY 14";
         String L5 = "SET RIBBON ON";
         String L6 = "DIRECTION 0,0";				         
         String L7 = "REFERENCE 0,0";
         String L8 = "OFFSET 0 mm";
         String L9 = "SET PEEL OFF";
         String L10 = "SET CUTTER OFF";
         String L11 = "SET TEAR ON";
         String L12 = "CLS";
         String L13 = "BARCODE 558,79,\"128M\",44,0,180,1,2,\""+itemCode+"\"";
         String L14 = "CODEPAGE 850";		         
         //String L15 = "REVERSE 283,33,84,21";
         String L16 ="PRINT 1,"+ String.valueOf(no_print);
         
         
        @SuppressWarnings("unused")
		String ClientName = "Jewel Mine";
        String currentUser = "";
  		if(session != null){
  			currentUser = session.getAttribute("username").toString();
  		}			
  	    File file = new File(currentUser+"_ornTSCBar302.prn");
        
        //  "//ams2/Printer2/"
        try{
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
        output.write("TEXT 546,32,\"0\",180,7,7,\""+itemCode+"\"");		           
        output.write("\n");
        
        if(itemmaster.getItemName().length() > 25)
        {
        	output.write("TEXT 366,77,\"0\",180,6,6,\""+itemName.substring(0,25)+"\"");	
        }
        else
        {
        	output.write("TEXT 366,77,\"0\",180,6,6,\""+itemName+"\"");
        }		            
        output.write("\n");				            
        output.write("TEXT 366,53,\"0\",180,7,7,\""+"GW: "+grossWeight+"\"");	
        output.write("\n");				            
        if(!itemmaster.getItemseal().equalsIgnoreCase("NON KDM"))
        {
        	output.write("TEXT 278,29,\"0\",180,6,6,\""+itemseal+"\"");					              
        }
        output.write("\n");
       
        if(itemmaster.getManufacturerSeal().length() > 8){
        	output.write("TEXT 254,53,\"0\",180,6,6,\""+SupplierName.substring(0,8)+"\"");			            	
		}
		else
		{
			output.write("TEXT 254,53,\"0\",180,6,6,\""+itemmaster.getManufacturerSeal()+"\"");
		}

        output.write("\n");
        output.write("TEXT 366,29,\"0\",180,6,6,\""+"VA "+itemmaster.getVaPercentage()+"%"+"\"");		
        output.write("\n");     
        output.write(L16);		            
        output.write("\n");			            
        output.close();		
         
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        PrintService pservices = PrintServiceLookup.lookupDefaultPrintService();
        //int printnbr = 0;
        DocPrintJob pj = pservices.createPrintJob();

         try {
             FileInputStream fis = new FileInputStream(currentUser+"_ornTSCBar302.prn");
             Doc doc = new SimpleDoc(fis, flavor, null);
             // print the doc as specified 
             pj.print(doc, aset);
             fis.close();
         }
         catch (Exception ex){
        	 ex.printStackTrace();
         }   
        }catch(IOException ie){
        	ie.printStackTrace();
        }
	}		*/
	
	// Ornament Citizen Bar code print Format
	public synchronized void ornamentPrintCitizen(ItemMaster itemmaster, HttpSession session)	
	{					
			
		Writer output = null;
        int no_print = 1;
        String itemCode=itemmaster.getItemCode();
        String itemName=itemmaster.getItemName();
        BigDecimal grossWeight=itemmaster.getGrossWeight();				            
        String itemseal=itemmaster.getItemseal();
        //String SupplierName = itemmaster.getManufacturerSeal();         
        
        // Constant Values"		         
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
         String L13 = "^FT380,29";
         String L14 = "^CI0";		           
         //String L15 = "REVERSE 283,33,84,21";
         String L15 ="PQ1,0,1,Y"+ String.valueOf(no_print);
         String L16 = "^XZ";		       
         
        @SuppressWarnings("unused")
		String ClientName = "Jewel Mine";
        String currentUser = "";
  		if(session != null){
  			currentUser = session.getAttribute("username").toString();
  		}			
  	    File file = new File(currentUser+"_ornCitizenBar301.prn");
        
        //  "//ams2/Printer2/"
        try{
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
       	            
        if(itemName.length() > 16)
        {
        	output.write("^A0N,17,23^FD"+itemName.substring(0,16)+"^FS\"");	
        }
        else
        {
        	output.write("^A0N,17,23^FD"+itemName+"^FS\"");
        }		            
        output.write("\n");
        output.write("^FT563,45");            
        output.write("\n");
        output.write("^A0N,17,23^FD"+"VA:"+itemmaster.getVaPercentage()+"%"+"^FS\"");	
        output.write("\n");     
        output.write("^FT563,61");
        output.write("\n");  
        if(!itemseal.equalsIgnoreCase("NON KDM"))
        {
           output.write("^A0N,17,23^FD"+itemseal+"^FS\"");   
        }
        output.write("\n");	            
        output.write("^FT563,29");
        output.write("\n");
        output.write("^A0N,17,23^FD"+"GW: "+grossWeight+"^FS\"");	
        output.write("\n");				            
        output.write("^FO396,40");
        output.write("\n");
        output.write("^BY1^BCN,38,N,N^FD>:"+itemCode+"^FS\"");		           
        output.write("\n");	            
        output.write("^FT397,98");
        output.write("\n");
		output.write("^A0N,20,27^FD"+itemCode+"^FS\"");
		output.write("\n");  
		output.write("^FT563,77");
        output.write("\n");
        if(itemmaster.getStoneWeight().signum() != 0){
        	output.write("^A0N,17,23^FD"+"Diamond:"+itemmaster.getStoneWeight()+"^FS\"");
        }else{
        	output.write("^A0N,17,23^FD"+itemmaster.getManufacturerSeal()+"^FS\"");
        }
        output.write("\n"); 
        output.write("^FT563,93");
        output.write("\n");
        if(itemmaster.getStoneCost().signum() != 0){
        	output.write("^A0N,17,23^FD"+"D Rate:"+itemmaster.getStoneCost()+"^FS\"");
        }
        output.write("\n");       
        output.write(L15);		            
        output.write("\n");	
        output.write(L16);		            
        output.write("\n");	
        output.close();		
             
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        PrintService pservices = PrintServiceLookup.lookupDefaultPrintService();
        DocPrintJob pj = pservices.createPrintJob();

        try {		
	         FileInputStream fis = new FileInputStream(currentUser+"_ornCitizenBar301.prn");
	         Doc doc = new SimpleDoc(fis, flavor, null);
	         // print the doc as specified 
	         pj.print(doc, aset);
	         fis.close();
            } 
        catch (Exception ex){
           	 ex.printStackTrace();
        }	
        }catch(IOException ie){
        	ie.printStackTrace();
        }
	}	
	
	// Ornament Zebra Bar code print Format 
	public synchronized void ornamentPrintZebra(ItemMaster itemmaster, HttpSession session)		
	{					
		Writer output = null;
        int no_print = 1;
        String itemCode=itemmaster.getItemCode();
        String itemName=itemmaster.getItemName();
        BigDecimal grossWeight=itemmaster.getGrossWeight();				            
        String itemseal=itemmaster.getItemseal();
        String SupplierName = itemmaster.getManufacturerSeal();
 
        //Constant Values"  This Segment of code is for Zebra Barcode printer
        String L1 = "I8,A,001";
        String L2 = "Q92,024";
        String L3 = "q831";
        String L4 = "rN";
        String L5 = "S2";
        String L6 = "D10";
        String L7 = "ZT";
        String L8 = "JF";
        String L9 = "OD";
        String L10 = "R59,0";
        String L11 = "f100";
        String L12 = "N";
        String L13 ="P" + String.valueOf(no_print);
        @SuppressWarnings("unused")
		String ClientName = "Jewel Mine";
        String currentUser = "";
  		if(session != null){
  			currentUser = session.getAttribute("username").toString();
  		}			
  	    File file = new File(currentUser+"_ornZebraBar301.prn");
  	    
        //  "//ams2/Printer2/"
        try{
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
        output.write("B681,56,2,1B,1,3,24,B,\""+itemCode+"\"");
        output.write("\n");          
        output.write("A503,54,2,3,1,1,N,\""+SupplierName+"\"");
        output.write("\n");
        
       if(!itemseal.equals("NON Kdm"))
       {
    	   output.write("A505,28,2,3,1,1,N,\""+itemseal+"\"");   
       }
        
        output.write("\n");
        output.write("A505,78,2,3,1,1,N,\""+"GW "+grossWeight+"\"");
        output.write("\n");
     
        if(itemName.length()>10)
        {
        	output.write("A678,77,2,3,1,1,N,\""+itemName.substring(0,10)+"\"");	
        }
        else
        {
        	output.write("A678,77,2,3,1,1,N,\""+itemName+"\"");
        }
        output.write("\n"); 
        output.write(L13);
        output.write("\n");
        output.close();		
         
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        PrintService pservices = PrintServiceLookup.lookupDefaultPrintService();
        DocPrintJob pj = pservices.createPrintJob();

         try {
             FileInputStream fis = new FileInputStream(currentUser+"_ornZebraBar301.prn");
             Doc doc = new SimpleDoc(fis, flavor, null);
             // print the doc as specified 
             pj.print(doc, aset);
             fis.close();
         }
         catch (Exception ex){
        	 ex.printStackTrace();
         }	     
        }catch(IOException ie){
        	ie.printStackTrace();
        }
	}

	public synchronized void ornamentPrintTSC(ItemMaster itemmaster, HttpSession session)	
	{					
			
	Writer output = null;
        //int no_print = 1;
        String itemCode=itemmaster.getItemCode();
        String itemName=itemmaster.getItemName();
        BigDecimal grossWeight=itemmaster.getGrossWeight();				            
        String itemseal=itemmaster.getItemseal();
        String ManufactureSeal = itemmaster.getManufacturerSeal();         
        
        // Constant Values"		         
         String L1 = "^Q13,6"; 
         String L2 = "^W91"; 
         String L3 = "^H19";
         String L4 = "^P1";
         String L5 = "^S2";
         String L6 = "^AT";
         String L7 = "^C1";				         
         String L8 = "^R3";
         String L9 = "~Q+4";
         String L10 = "^O0";
         String L11 = "^D0";
         String L12 = "^E0";
         String L13 = "~R200";
         String L14 = "^L";
         String L15 ="Dy2-me-dd";
         String L16 = "Th:m:s";		       
         String L17 = "E";
         
        @SuppressWarnings("unused")
		String ClientName = "Jewel Mine";
        String currentUser = "";
  		if(session != null){
  			currentUser = session.getAttribute("username").toString();
  		}			
  	    File file = new File(currentUser+"_ornGodexBar301.prn");
        
        //  "//ams2/Printer2/"
        try{
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
        output.write(L15);		            
        output.write("\n");	
        output.write(L16);		            
        output.write("\n");	
        
        output.write("BQ2,373,25,1,3,32,0,0,"+"A"+itemCode);		           
        output.write("\n");	     
       	            
        if(itemName.length() > 13)
        {
        	output.write("AB,367,0,1,1,0,0,"+itemName.substring(0,13));	
        }
        else
        {
        	output.write("AB,367,0,1,1,0,0,"+itemName);
        }		            
        output.write("\n");        
        output.write("AB2,543,0,1,1,0,0,"+"GW: "+grossWeight);	
        output.write("\n");	
        if(itemmaster.getStoneWeight().signum() == 0){
        	output.write("AB,546,49,1,1,0,0,"+ManufactureSeal);
        	output.write("\n");                  
        }else{
        	output.write("AB,546,49,1,1,0,0,"+"Diamond:"+itemmaster.getStoneWeight());
        	output.write("\n");     
        }
           
        if(itemmaster.getStoneCost().signum() != 0){
        	output.write("AB,546,66,1,1,0,0,"+"D Rate:"+itemmaster.getStoneCost());
        }
        output.write("\n");
        output.write("AB,545,16,1,1,0,0,"+"VA:"+itemmaster.getVaPercentage()+"%");	
        output.write("\n");       
        if(!itemseal.equalsIgnoreCase("NON KDM"))
        {
           output.write("AB3,546,32,1,1,0,0,"+itemseal);   
        }
        output.write("\n");	
        output.write("AB,374,54,1,1,0,0,"+itemCode);		           
        output.write("\n");	   
        output.write(L17);	
		output.write("\n");        
        output.close();		
             
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        PrintService pservices = PrintServiceLookup.lookupDefaultPrintService();
        DocPrintJob pj = pservices.createPrintJob();

        try {		
	         FileInputStream fis = new FileInputStream(currentUser+"_ornGodexBar301.prn");
	         Doc doc = new SimpleDoc(fis, flavor, null);
	         // print the doc as specified 
	         pj.print(doc, aset);
	         fis.close();
            } 
        catch (Exception ex){
           	 ex.printStackTrace();
        }	
        }catch(IOException ie){
        	ie.printStackTrace();
        }
		
		
		
	}
	
	
	public synchronized void ornamentPrintArgox(ItemMaster itemmaster, HttpSession session)	
	{	
		Writer output = null;        
        String itemCode=itemmaster.getItemCode();
        String itemName=itemmaster.getItemName();
        BigDecimal grossWeight=itemmaster.getGrossWeight();				            
        String itemseal=itemmaster.getItemseal();
        String ManufactureSeal = itemmaster.getManufacturerSeal();         
        
        // Constant Values"		         
         String L1 = "e"; 
         String L2 = "O0210"; 
         String L3 = "f290";
         String L4 = "L";
         String L5 = "C0005";
         String L6 = "D11";
         String L7 = "PD";				         
         String L8 = "H13";       
         String L9 = "E";
         
        @SuppressWarnings("unused")
		String ClientName = "Jewel Mine";
        String currentUser = "";
  		if(session != null){
  			currentUser = session.getAttribute("username").toString();
  		}			
  	    File file = new File(currentUser+"_ornArgoxBar301.prn");
        
        //  "//ams2/Printer2/"
        try{
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
       
        if(itemName.length() > 16)
        {
        	output.write("191100100430005"+itemName.substring(0,16));	
        }
        else
        {
        	output.write("191100100430005"+itemName);
        }		            
        output.write("\n");           
        
        output.write("1e2101500250005"+itemCode);		           
        output.write("\n");	     
       	            
        output.write("191100200100005"+itemCode);		           
        output.write("\n");	    
        
        output.write("191100100440095"+"GW: "+grossWeight);	
        output.write("\n");	
        
        output.write("191100100360095"+"VA:"+itemmaster.getVaPercentage()+"%");	
        output.write("\n");  
                
        if(itemmaster.getStoneWeight().signum() == 0)
        {
           output.write("191100100280095"+itemseal);   
        }
        output.write("\n");	
        
        if(itemmaster.getStoneWeight().signum() == 0){
        	output.write("191100100180095"+ManufactureSeal);
        	output.write("\n");                  
        }else{
        	output.write("191100100180095"+"Diamond:"+itemmaster.getStoneWeight());
        	output.write("\n");     
        }
           
        if(itemmaster.getStoneCost().signum() != 0){
        	output.write("191100100080095"+"D Rate:"+itemmaster.getStoneCost());
        }
        output.write("\n");       
        output.write(L9);	
		output.write("\n");        
        output.close();		
             
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        PrintService pservices = PrintServiceLookup.lookupDefaultPrintService();
        DocPrintJob pj = pservices.createPrintJob();

        try {		
	         FileInputStream fis = new FileInputStream(currentUser+"_ornArgoxBar301.prn");
	         Doc doc = new SimpleDoc(fis, flavor, null);
	         // print the doc as specified 
	         pj.print(doc, aset);
	         fis.close();
            } 
        catch (Exception ex){
           	 ex.printStackTrace();
        }	
        }catch(IOException ie){
        	ie.printStackTrace();
        }
	}
	public synchronized void ornamentPrintArgoxSGH(ItemMaster itemmaster, HttpSession session)	
	{	
		Writer output = null;        
		String itemCode=itemmaster.getItemCode();
		String itemName=itemmaster.getItemName();
		BigDecimal grossWeight=itemmaster.getGrossWeight();				            
		String itemseal=itemmaster.getItemseal();
		BigDecimal hallMarkCharges = itemmaster.getItemHMCharges();         
		
		// Constant Values"		         
		String L1 = "e"; 
		String L2 = "O0210"; 
		String L3 = "f290";
		String L4 = "L";
		String L5 = "C0005";
		String L6 = "D11";
		String L7 = "PD";				         
		String L8 = "H16";       
		String L9 = "E";
		
		@SuppressWarnings("unused")
		String ClientName = "Jewel Mine";
		String currentUser = "";
		if(session != null){
			currentUser = session.getAttribute("username").toString();
		}			
		File file = new File(currentUser+"_ornArgoxBar301.prn");
		
		//  "//ams2/Printer2/"
		try{
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
			
			if(itemName.length() > 16)
			{
				output.write("191100100350005"+itemName.substring(0,16));	
			}
			else
			{
				output.write("191100100350005"+itemName);
			}		            
			output.write("\n");           
			
			output.write("1e2101500170005"+itemCode);		           
			output.write("\n");	     
			
			output.write("191100200010005"+itemCode);		           
			output.write("\n");	    
			
			output.write("191100200800005"+"GW: "+grossWeight);	
			output.write("\n");	
			
			//output.write("191100100700005"+"VA:"+itemmaster.getVaPercentage()+"%"+"MC:"+itemmaster.getMcInRupee());
			if(itemmaster.getVaPercentage().signum()>0 && itemmaster.getMcInRupee().signum()>0){
				output.write("191100100700005"+"VA:"+itemmaster.getVaPercentage()+"%"+"MC:"+itemmaster.getMcInRupee());	
			}
			if(itemmaster.getVaPercentage().signum()>0 && itemmaster.getMcPerGram().signum()>0){
				BigDecimal finalMC= itemmaster.getGrossWeight().multiply(itemmaster.getMcPerGram());
				DecimalFormat df=new DecimalFormat("###.##");
				output.write("191100100700005"+"VA:"+itemmaster.getVaPercentage()+"%"+"MC:"+df.format(finalMC));
			}
				
			output.write("\n");  
			
			if(!itemmaster.getItemseal().equalsIgnoreCase("NON KDM"))
			{
				output.write("291100100400081"+itemseal);   
			}
			output.write("\n");	
			
			if(itemmaster.getItemHMCharges().signum()> 0){
				output.write("191100100500005"+"HM:"+hallMarkCharges);
				output.write("\n");                  
			}
			//else{
				/*output.write("191100100180095"+"Diamond:"+itemmaster.getStoneWeight());*/
			if(itemmaster.getStoneWeight().signum()>0){
				output.write("191100100600005"+"St Wt:"+itemmaster.getStoneWeight()+"X"+itemmaster.getStoneRatePerCaret());
			}
				output.write("\n");     
			//}
			
			/*if(itemmaster.getStoneCost().signum() != 0){
				output.write("191100100080095"+"St Rate:"+itemmaster.getStoneCost());
			}*/
			output.write("\n");       
			output.write(L9);	
			output.write("\n");        
			output.close();		
			
			DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
			PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
			PrintService pservices = PrintServiceLookup.lookupDefaultPrintService();
			DocPrintJob pj = pservices.createPrintJob();
			
			try {		
				FileInputStream fis = new FileInputStream(currentUser+"_ornArgoxBar301.prn");
				Doc doc = new SimpleDoc(fis, flavor, null);
				// print the doc as specified 
				pj.print(doc, aset);
				fis.close();
			} 
			catch (Exception ex){
				ex.printStackTrace();
			}	
		}catch(IOException ie){
			ie.printStackTrace();
		}
	}
}
