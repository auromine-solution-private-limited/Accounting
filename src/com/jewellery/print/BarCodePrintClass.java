package com.jewellery.print;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Writer;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.servlet.http.HttpSession;

import com.jewellery.entity.PostagItem;

public class BarCodePrintClass {
	
	// Method For TSC 244 Bar_code Printer
	public void printTSC(PostagItem positemtag, HttpSession session) throws Exception
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
	         FileInputStream fis = new FileInputStream(currentUser+"_TSCBar302.prn");
	         Doc doc = new SimpleDoc(fis, flavor, null);
	         // print the doc as specified 
	         pj.print(doc, aset);
	         fis.close();
	     }
	     catch (Exception ex){
	    	 ex.printStackTrace();
	     }  
	}
	
	
	// Method For Bar_code Printer
	public void printZebra(PostagItem positemtag, HttpSession session)throws Exception
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
	    //int printnbr = 0;
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
	}
	
	public void PrintCitizen(PostagItem positemtag, HttpSession session) throws Exception
	{
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
        
        String currentUser = "";
  		if(session != null){
  			currentUser = session.getAttribute("username").toString();
  		}			
  	    File file = new File(currentUser+"_CitizenBar302.prn");
        
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
	         FileInputStream fis = new FileInputStream(currentUser+"_CitizenBar302.prn");
	         Doc doc = new SimpleDoc(fis, flavor, null);
	         // print the doc as specified 
	         pj.print(doc, aset);
	         fis.close();
	     }
	     catch (Exception ex){
	    	 ex.printStackTrace();
	     }	
	}

}
