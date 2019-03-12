 package com.jewellery.printservice;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterResolution;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jewellery.dao.CompanyInfoDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.entity.CompanyInfo;
import com.jewellery.entity.Ledger;
import com.jewellery.entity.POSSalesItem;
import com.jewellery.entity.PSales;
import com.jewellery.entity.Purchase;
import com.jewellery.entity.Sales;
import com.jewellery.print.InvoiceBillFormat;
import com.jewellery.util.NumToWords;

public class InvoiceTextFormatPrint implements InvoiceBillFormat {
	
	NumToWords number=new NumToWords();
	DateFormat dateFormat = new SimpleDateFormat("HH:mm");
	Calendar cal = Calendar.getInstance();
	String font_bold_on="E";
	String font_bold_off="F";

	public void getPrinterService(String billno, String fileName)
	{
		try
		{ 
		 FileInputStream fis = new FileInputStream(fileName);
		 DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		 Doc doc = new SimpleDoc(fis, flavor, null);
		 PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		 aset.add(MediaSizeName.ISO_A4);
		 aset.add(OrientationRequested.PORTRAIT);
		 PrinterResolution pr = new PrinterResolution(300, 300, PrinterResolution.DPI);
		 aset.add(pr);
		 //PrintService[] services = PrintServiceLookup.lookupPrintServices(flavor, aset);
	/*	 PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
	
	     for (PrintService printer : printServices)
	         System.out.println("Printer: " + printer.getName()); */
	 
		 PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
		 if(defaultService==null)
		 {
			 System.out.println("This system doesn't found any default printer.Please install a printer.");	
		 }
		 else{
	
	    //print using default
	    DocPrintJob job = defaultService.createPrintJob();
	    
	    System.out.println("Printed Invoice NO :"+billno);
	    System.out.println("The Printer Name is:"+job.getPrintService());
	    job.print(doc, aset);
		}	    
		}catch(FileNotFoundException FNE){
			System.out.println(FNE.toString());
		}catch(Exception E){
			System.out.println(E.toString());
		}
	}
	
	//** Ornament Sales Invoice For 8 Inch Dot Matrix Printer **/
	public  void printinvoice(Sales sales, CompanyInfoDao companyinfo,LedgerDao ledgerDao, HttpServletRequest request)
	{
		try
		{
			HttpSession session = request.getSession();	
			String currentUser = "";
			if(session != null){
				currentUser = session.getAttribute("username").toString();
			}	
					
			Writer wri = new FileWriter(currentUser+"_Invoice.txt");
			wri = new BufferedWriter(wri);
			PrintWriter out = new PrintWriter(wri);
			CompanyInfo Compnayadd = companyinfo.listCompanyInfo().get(0);
			out.write(font_bold_on);
			out.write("\n");
			out.write(String.format("%35s", Compnayadd.getCompanyName())+"\n");
			out.write(font_bold_off);
			out.write(String.format("%46s", Compnayadd.getCompanyAddress())+"\n");
			out.write(String.format("%30s", Compnayadd.getCity()));
			out.write("\n");
			out.write(String.format("%30s","Ph:"+Compnayadd.getPhone()));
			out.write("\n");
			out.write(String.format("%59s","-----------------------------------------------------------"));
			out.write("\n");
			String customername = sales.getCustomerName();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			 String salesDate = formatter.format(sales.getSalesDate());
			if(!sales.getCustomerName().equals("walk_in"))
			{
				 List<Ledger> customerAdd = ledgerDao.searchLedger(customername);
				 out.write(font_bold_on);
				 if(sales.getFormType().equalsIgnoreCase("Sales")){
					 //out.write(String.format("%30s","To:"+customerAdd.get(0).getLedgerName()));
					 out.write(String.format("%30s","Invoice"));
				 }else{
					 out.write(String.format("%30s","Estimate"));
				 }
				 out.write(font_bold_off);
				 out.write("\n");
				if(customerAdd.get(0).getAddress1()!=null)
				{
					out.write(String.format("%33s", customerAdd.get(0).getAddress1()));
					out.write("\n");
				}
				if(customerAdd.get(0).getCity()!=null)
				{
				out.write(String.format("%33s",customerAdd.get(0).getCity()));	
				out.write("\n");
				}
					
				}
			else
			{
				 if(sales.getFormType().equalsIgnoreCase("Sales")){
					/* out.write(String.format("%35s","TO:"+sales.getWalkIn_Name()));
					 out.write("\n");
					 out.write(String.format("%35s", sales.getWalkIn_City()));
					 out.write("\n"); */
					 out.write(font_bold_on);
					 out.write(String.format("%30s","Invoice")+"\n");
					 out.write(font_bold_off);
				 }else{
					 out.write(String.format("%30s","Estimate"));
					 out.write("\n");
				 }
					
			}
			out.write(String.format("%18s %35s","Rate : "+sales.getBullionRate(),"Date : "+salesDate)+"\n");
			out.write(String.format("%60s","-----------------------------------------------------------\n"));
			out.write(String.format("%4s %4s %15s %4s %5s %5s %5s %8s","","Item","Pcs","Wt.","V.A","M.C","Stn/Rt","Amount"));
			out.write("\n");
			out.write(String.format("%60s","-----------------------------------------------------------\n"));
			
				
			out.write(String.format("%4s %-15s %2s %7.3f %3.0f %5.0f %5.0f %10.2f","",sales.getCategoryName(),sales.getTotalPieces(),sales.getGrossWeight(),sales.getValueAdditionCharges().setScale(0),sales.getMcByGrams(),sales.getStoneCost(),sales.getAmountAfterLess())+"\n");
				
				if(sales.getGrossWeight1().signum()!=0.000)
				{
					
					out.write(String.format("%4s %-15s %2s %7.3f %3.0f %5.0f %5.0f %10.2f","",sales.getCategoryName1(),sales.getTotalPieces1(),sales.getGrossWeight1(),sales.getValueAdditionCharges1().setScale(0),sales.getMcByGrams1(),sales.getStoneCost1(),sales.getAmountAfterLess1())+"\n");
						
				}
				
				if(sales.getGrossWeight2().signum()!=0.000)
				{
					
					out.write(String.format("%4s %-15s %2s %7.3f %3.0f %5.0f %5.0f %10.2f","",sales.getCategoryName2(),sales.getTotalPieces2(),sales.getGrossWeight2(),sales.getValueAdditionCharges2().setScale(0),sales.getMcByGrams2(),sales.getStoneCost2(),sales.getAmountAfterLess2())+"\n");
						
				}
				
				if(sales.getGrossWeight3().signum()!=0.000)
				{
					
					out.write(String.format("%4s %-15s %2s %7.3f %3.0f %5.0f %5.0f %10.2f","",sales.getCategoryName3(),sales.getTotalPieces3(),sales.getGrossWeight3(),sales.getValueAdditionCharges3().setScale(0),sales.getMcByGrams3(),sales.getStoneCost3(),sales.getAmountAfterLess3())+"\n");
						
				}
				
				if(sales.getGrossWeight4().signum()!=0.000)
				{
					out.write(String.format("%4s %-15s %2s %7.3f %3.0f %5.0f %5.0f %10.2f","",sales.getCategoryName4(),sales.getTotalPieces4(),sales.getGrossWeight4(),sales.getValueAdditionCharges4().setScale(0),sales.getMcByGrams4(),sales.getStoneCost4(),sales.getAmountAfterLess4())+"\n");
						
				}
				out.write(String.format("%60s","-----------------------------------------------------------\n"));
			
			BigDecimal cashamount=new BigDecimal("0.00");
			BigDecimal voucheramount=new BigDecimal("0.00");
			BigDecimal balanceAmount=new BigDecimal("0.00");
			BigDecimal checkAmount=new BigDecimal("0.00");
			BigDecimal cardAmount=new BigDecimal("0.00");
			
			cashamount=sales.getCashAmount();
			voucheramount=sales.getVoucherAmount();
			balanceAmount=sales.getBalToPay();
			checkAmount=sales.getChequeAmount();
			cardAmount=sales.getCalCardAmount();
			out.write(String.format("%18s %25s","TOTAL",sales.getBillAmount())+"\n");
			if(sales.getTax().signum()!=0.00)
			{
				out.write(String.format("%18s %25s","VAT TAX 1(+)%",sales.getTax())+"\n");	
			}
			out.write(String.format("%18s %25s","TOTAL AMOUNT",sales.getBillAmount())+"\n");
			if(sales.getExchangeAmount().signum()!=0.00)
			{
				out.write(String.format("%18s %25s","PURCHASE No:"+sales.getExchangeBillNo(),sales.getExchangeAmount())+"\n");	
			}
			if(sales.getSalesOrderAmount().signum()!=0.00)
			{
				out.write(String.format("%18s %25s","SAL.ORDER ADVANCE NO:"+sales.getSalesOrderID(),sales.getSalesOrderAmount())+"\n");	
			}
			if(sales.getSSCardAmount().signum()!=0.00)
			{
				out.write(String.format("%18s %25s","SAVING SCHEME CARD NO:"+sales.getSSCardNo(),sales.getSSCardAmount())+"\n");	
			}
			if(cashamount.signum()>0)
			{
				out.write(String.format("%18s %25s","RECEIVED CASH:",cashamount.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			if(voucheramount.signum()>0)
			{
				out.write(String.format("%18s %25s","AMOUNT RECEIVED VOUCHER:",voucheramount.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			if(checkAmount.signum()>0)
			{
				out.write(String.format("%18s %25s","BANK AMOUNT:",checkAmount.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			if(cardAmount.signum()>0)
			{
				out.write(String.format("%18s %25s","CARD AMOUNT:",cardAmount.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			if(balanceAmount.signum()>0)
			{
				out.write(String.format("%18s %25s","BALANCE AMOUNT:",balanceAmount.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			out.write("\n");
			out.write(font_bold_on);
			out.write(String.format("%4s %35s","","Rs."+sales.getBillAmount())+"\n");
			out.write(String.format("%4s %35s","",number.convert(sales.getBillAmount())+"\n"));
			out.write(font_bold_off);
			out.write(String.format("%9s","-----------------------------------------------------------\n"));
			out.write(String.format("%4s %25s","","*"+dateFormat.format(cal.getTime())+"/"+sales.getSalesTypeId()+"\n"));
			out.write(String.format("%4s %35s","","SUBJECT TO SALES ON APPROVAL")+"\n");
			out.write(String.format("%4s %35s","","THANK U VISIT AGAIN & AGAIN!!!\n"));
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.flush();
			out.close();
			getPrinterService(sales.getSalesTypeId(),currentUser+"_Invoice.txt");
		}
		catch(Exception e)
		{
			System.out.println("InvoicePrint:"+e.getMessage());
		}
	}
	
	/****** Ornament Sales Invoice For 4 Inch Dot Matrix Printer
		public void printFourInchInvoice(Sales sales, CompanyInfoDao companyinfo,LedgerDao ledgerDao, HttpServletRequest request)
		{
			try
			{
				HttpSession session = request.getSession();	
				String currentUser = "";
				if(session != null){
					currentUser = session.getAttribute("username").toString();
				}	
						
				Writer wri = new FileWriter(currentUser+"_Invoice.txt");
				wri = new BufferedWriter(wri);
				PrintWriter out = new PrintWriter(wri);
				CompanyInfo Compnayadd = companyinfo.listCompanyInfo().get(0);
				out.write(font_bold_on);
				out.write("\n");
				out.write(String.format("%30s", Compnayadd.getCompanyName())+"\n");
				out.write(font_bold_off);
				/*	String CompanyAddress1 = Compnayadd.getCompanyAddress();
				String CompanyAddress2 = Compnayadd.getCompanyAddress();
				
				if(CompanyAddress1.length() > 35){
					CompanyAddress1 = CompanyAddress1.substring(0,35);
					out.write(String.format("%35s", Compnayadd.getCompanyAddress().substring(0,CompanyAddress1.lastIndexOf(" "))+"\n"));
					
					while(CompanyAddress2.length() < 70){
						CompanyAddress2 = CompanyAddress2 + " "; 
					}
					CompanyAddress2 = CompanyAddress2.substring(CompanyAddress1.lastIndexOf(" "),70);
					out.write(String.format("%35s", CompanyAddress2)+"\n");
				}else{
					out.write(String.format("%36s", Compnayadd.getCompanyAddress())+"\n");	
				}
				//out.write(String.format("%46s", Compnayadd.getCompanyAddress())+"\n");
				out.write(String.format("%20s", Compnayadd.getCity()));
				out.write("\n");
				if(Compnayadd.getPhone()!="")
				{
					out.write(String.format("%25s","Ph:"+Compnayadd.getPhone()));
				}
				out.write("\n");
				out.write(String.format("%59s","-----------------------------------------------------------"));
				out.write("\n");
				String customername = sales.getCustomerName();
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				 String salesDate = formatter.format(sales.getSalesDate());
				if(!sales.getCustomerName().equals("walk_in"))
				{
					 List<Ledger> customerAdd = ledgerDao.searchLedger(customername);
					 out.write(font_bold_on);
					 if(sales.getFormType().equalsIgnoreCase("Sales")){
						 out.write(String.format("%25s","Estimate Slip")+"\n");
						 out.write(String.format("%2s","To:"+customerAdd.get(0).getLedgerName()));
						 
					 }else{
						 out.write(String.format("%25s","Estimate Slip"));
					 }
					 out.write(font_bold_off);
					 out.write("\n");
					if(customerAdd.get(0).getAddress1()!=null)
					{
						out.write(String.format("%5s", customerAdd.get(0).getAddress1()));
						out.write("\n"); 
					}
					if(customerAdd.get(0).getCity()!=null)
					{
					out.write(String.format("%4s",customerAdd.get(0).getCity()));	
					out.write("\n");
					}
						
					}
				else
				{
					 if(sales.getFormType().equalsIgnoreCase("Sales")){
						/* out.write(String.format("%35s","TO:"+sales.getWalkIn_Name()));
						 out.write("\n");
						 out.write(String.format("%35s", sales.getWalkIn_City()));
						 out.write("\n"); 
						 out.write(font_bold_on);
						 out.write(String.format("%25s","Estimate Slip")+"\n");
						 out.write(font_bold_off);
					 }else{ 
						 out.write(String.format("%25s","Estimate Slip"));
						 out.write("\n");
					 }
						
				}
				out.write(String.format("%15s","GOLD RATE : "+sales.getGoldOrnBoardRate())+"\n");
				out.write(String.format("%15s","SILVER RATE : "+sales.getSilverOrnBoardRate())+"\n");
				out.write(String.format("%10s %15s","DATE : "+salesDate,"TIME :"+sales.getCurrentTime())+"\n");
				out.write(String.format("%60s","-----------------------------------------------------------\n"));
				out.write(String.format("%1s %3s %4s %5s %3s %4s %4s","","JEWElNAME","Wt","V.A%","M.C","H.M","Amt")); 
				out.write("\n");
				out.write(String.format("%60s","-----------------------------------------------------------\n"));
				
				String jewelName = sales.getCategoryName(); 
				
				if(jewelName.length() > 9){
					jewelName = sales.getCategoryName().substring(0, 9);				 
				}
					
				out.write(String.format("%1s %-9s %3.3f %1.2f %3.0f %2.0f %5.0f","",jewelName,sales.getGrossWeight(),sales.getValueAdditionCharges(),sales.getMcByGrams(),sales.getSalesHMCharges(),sales.getAmountAfterLess())+"\n");
							
					if(sales.getGrossWeight1().signum()!=0.000) 
					{
						
						String jewelName1 = sales.getCategoryName1();
						
						if(jewelName1.length() > 9){
							jewelName1 = sales.getCategoryName1().substring(0, 9);				
						}
							
						out.write(String.format("%1s %-9s %3.3f %1.2f %3.0f %2.0f %5.0f","",jewelName1,sales.getGrossWeight1(),sales.getValueAdditionCharges1(),sales.getMcByGrams1(),sales.getSalesHMCharges1(),sales.getAmountAfterLess1())+"\n");

							
					}
					
					if(sales.getGrossWeight2().signum()!=0.000)
					{
											
						String jewelName2 = sales.getCategoryName2();
						
						if(jewelName2.length() > 9){
							jewelName2 = sales.getCategoryName2().substring(0, 9);				
						}
							
						out.write(String.format("%1s %-9s %3.3f %1.2f %3.0f %2.0f %5.0f","",jewelName2,sales.getGrossWeight2(),sales.getValueAdditionCharges2(),sales.getMcByGrams2(),sales.getSalesHMCharges2(),sales.getAmountAfterLess2())+"\n");

							
					}
					
					if(sales.getGrossWeight3().signum()!=0.000)
					{
						
						String jewelName3 = sales.getCategoryName3();
						
						if(jewelName3.length() > 9){
							jewelName3 = sales.getCategoryName3().substring(0, 9);				
						}
							
						out.write(String.format("%1s %-9s %3.3f %1.2f %3.0f %2.0f %5.0f","",jewelName3,sales.getGrossWeight3(),sales.getValueAdditionCharges3(),sales.getMcByGrams3(),sales.getSalesHMCharges3(),sales.getAmountAfterLess3())+"\n");

							
					}
					
					if(sales.getGrossWeight4().signum()!=0.000)
					{
						
						String jewelName4 = sales.getCategoryName4();
						
						if(jewelName4.length() > 9){
							jewelName4 = sales.getCategoryName4().substring(0, 9);				
						}
						out.write(String.format("%1s %-9s %3.3f %1.2f %3.0f %2.0f %5.0f","",jewelName4,sales.getGrossWeight4(),sales.getValueAdditionCharges4(),sales.getMcByGrams4(),sales.getSalesHMCharges4(),sales.getAmountAfterLess4())+"\n");
							
					}
					out.write(String.format("%60s","-----------------------------------------------------------\n"));
				
				BigDecimal cashamount=new BigDecimal("0.00");
				BigDecimal voucheramount=new BigDecimal("0.00");
				BigDecimal balanceAmount=new BigDecimal("0.00");
				BigDecimal checkAmount=new BigDecimal("0.00");
				BigDecimal cardAmount=new BigDecimal("0.00");
				
				cashamount=sales.getCashAmount();
				voucheramount=sales.getVoucherAmount();
				balanceAmount=sales.getBalToPay();
				checkAmount=sales.getChequeAmount();
				cardAmount=sales.getCalCardAmount();
				
				if(sales.getAmountAfterLess1().signum()>0){
				out.write(String.format("%26s %10s","SUB TOTAL",sales.getTotalAmount())+"\n");
				}
				if(sales.getTax().signum()!=0.00)
				{
					out.write(String.format("%26s %10s","VAT 1%",sales.getTax())+"\n");	
				}	
				if(sales.getBillDiscAmt().signum()>0){
					
					out.write(String.format("%26s %10s","DISC AMOUNT",sales.getBillDiscAmt())+"\n");	
				}
				out.write(String.format("%26s %10s","GRAND TOTAL",sales.getBillAmount())+"\n");
				if(sales.getSalesReturnAmount().signum()!=0.00)
				{
					out.write(String.format("%26s %10s","SALES RETURN:"+sales.getSalesReturnId(),sales.getSalesReturnAmount())+"\n");	
				}
				
				if(sales.getExchangeAmount().signum()!=0.00)
				{
					out.write(String.format("%26s %10s","Old PURCHASE NO:"+sales.getExchangeBillNo(),sales.getExchangeAmount())+"\n");	
				}
				if(sales.getSalesOrderAmount().signum()!=0.00)
				{
					out.write(String.format("%26s %10s","SAL.ORDER ADVANCE NO:"+sales.getSalesOrderID(),sales.getSalesOrderAmount())+"\n");	
				}
				if(sales.getSSCardAmount().signum()!=0.00)
				{
					out.write(String.format("%26s %10s","SAVING SCHEME CARD NO:"+sales.getSSCardNo(),sales.getSSCardAmount())+"\n");	
				}
				if(cashamount.signum()>0)
				{
					out.write(String.format("%26s %10s","RECEIVED CASH:",cashamount.setScale(2, RoundingMode.HALF_UP))+"\n");	
				}
				if(voucheramount.signum()>0)
				{
					out.write(String.format("%26s %10s","VOUCHER AMOUNT:",voucheramount.setScale(2, RoundingMode.HALF_UP))+"\n");	
				}
				if(checkAmount.signum()>0)
				{
					out.write(String.format("%26s %10s","BANK AMOUNT:",checkAmount.setScale(2, RoundingMode.HALF_UP))+"\n");	
				}
				if(cardAmount.signum()>0)
				{
					out.write(String.format("%26s %10s","CARD AMOUNT:",cardAmount.setScale(2, RoundingMode.HALF_UP))+"\n");	
				}
				if(balanceAmount.signum()>0)
				{
					out.write(String.format("%26s %10s","BALANCE AMOUNT:",balanceAmount.setScale(2, RoundingMode.HALF_UP))+"\n");	
				}
				out.write("\n");
				out.write(font_bold_on);
				/*out.write(String.format("%4s %28s","","Rs."+sales.getBillAmount())+"\n");
				//out.write(String.format("%4s %35s","",number.convert(sales.getBillAmount())+"\n"));
				String ConvertedValue = number.convert(sales.getBillAmount())+" ONLY.";
				String ConvertedLine1 = ConvertedValue;
				String ConvertedLine2 = ConvertedValue; 
				
				if(ConvertedLine1.length() > 35){ 
					ConvertedLine1 = ConvertedLine1.substring(0,35);
					out.write(String.format("%2s", ConvertedValue.substring(0,ConvertedLine1.lastIndexOf(" "))+"\n"));
					
					while(ConvertedLine2.length() < 70){
						ConvertedLine2 = ConvertedLine2 + " ";
					}
					ConvertedLine2 = ConvertedLine2.substring(ConvertedLine1.lastIndexOf(" "),70);
					out.write(String.format("%2s", ConvertedLine2)+"\n");
				}else{
					out.write(String.format("%2s", ConvertedValue)+"\n");	
				}
				out.write(font_bold_off);
				out.write(String.format("%9s","-----------------------------------------------------------\n"));
				//out.write(String.format("%4s %25s","","*"+dateFormat.format(cal.getTime())+"/"+sales.getSalesTypeId()+"\n"));			
				out.write(String.format("%4s %35s","","THANK U VISIT AGAIN & AGAIN!!!\n"));
				out.write("\n");
				out.write("\n");
				out.write("\n");
				out.write("\n");
				out.write("\n");
				out.write("\n");
				out.write("\n");
				out.write("\n");
				out.write("\n");
				out.write("\n");
				out.write("\n");
				out.flush();
				out.close();
				getPrinterService(sales.getSalesTypeId(),currentUser+"_Invoice.txt");
			}
			catch(Exception e)
			{
				System.out.println("Invoice Print E Message :"+e.getMessage());
				e.printStackTrace();
			}
		}****************/
	
	//** Ornament Sales Invoice For SGH 4 Inch Dot Matrix Printer **/
	public void printFourInchInvoice(Sales sales, CompanyInfoDao companyinfo,LedgerDao ledgerDao, HttpServletRequest request)
	{
		try
		{
			HttpSession session = request.getSession();	
			String currentUser = "";
			if(session != null){
				currentUser = session.getAttribute("username").toString();
			}	
					
			Writer wri = new FileWriter(currentUser+"_Invoice.txt");
			wri = new BufferedWriter(wri);
			PrintWriter out = new PrintWriter(wri);
			CompanyInfo Compnayadd = companyinfo.listCompanyInfo().get(0);
			out.write(font_bold_on);
			out.write("\n");
			out.write(String.format("%30s", Compnayadd.getCompanyName())+"\n");
			out.write(font_bold_off);
			/*	String CompanyAddress1 = Compnayadd.getCompanyAddress();
			String CompanyAddress2 = Compnayadd.getCompanyAddress();
			
			if(CompanyAddress1.length() > 35){
				CompanyAddress1 = CompanyAddress1.substring(0,35);
				out.write(String.format("%35s", Compnayadd.getCompanyAddress().substring(0,CompanyAddress1.lastIndexOf(" "))+"\n"));
				
				while(CompanyAddress2.length() < 70){
					CompanyAddress2 = CompanyAddress2 + " "; 
				}
				CompanyAddress2 = CompanyAddress2.substring(CompanyAddress1.lastIndexOf(" "),70);
				out.write(String.format("%35s", CompanyAddress2)+"\n");
			}else{
				out.write(String.format("%36s", Compnayadd.getCompanyAddress())+"\n");	
			}
			//out.write(String.format("%46s", Compnayadd.getCompanyAddress())+"\n");
			out.write(String.format("%20s", Compnayadd.getCity()));
			out.write("\n");*/
			if(Compnayadd.getPhone() != "" || Compnayadd.getPhone() != null)
			{
				out.write(String.format("%25s","Ph:"+Compnayadd.getPhone()));
			}
			out.write("\n");
			out.write(String.format("%59s","-----------------------------------------------------------"));
			out.write("\n");
			//String customername = sales.getCustomerName();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			 String salesDate = formatter.format(sales.getSalesDate());
			 
			/*if(!sales.getCustomerName().equals("walk_in"))
			{
				 List<Ledger> customerAdd = ledgerDao.searchLedger(customername);
				 out.write(font_bold_on);
				 if(sales.getFormType().equalsIgnoreCase("Sales")){
					 out.write(String.format("%25s","Estimate Slip")+"\n");
					 out.write(String.format("%2s","To:"+customerAdd.get(0).getLedgerName()));
					 
				 }else{
					 out.write(String.format("%25s","Estimate Slip"));
				 }
				 out.write(font_bold_off);
				 out.write("\n");
				if(customerAdd.get(0).getAddress1()!=null)
				{
					out.write(String.format("%5s", customerAdd.get(0).getAddress1()));
					out.write("\n"); 
				}
				if(customerAdd.get(0).getCity()!=null)
				{
				out.write(String.format("%4s",customerAdd.get(0).getCity()));	
				out.write("\n");
				}
					
			}
			else 
			{
				 if(sales.getFormType().equalsIgnoreCase("Sales")){
					 out.write(String.format("%35s","TO:"+sales.getWalkIn_Name()));
					 out.write("\n");
					 out.write(String.format("%35s", sales.getWalkIn_City()));
					 out.write("\n"); 
					 out.write(font_bold_on);
					 out.write(String.format("%25s","Estimate Slip")+"\n");
					 out.write(font_bold_off);
				 }else{ 
					 out.write(String.format("%25s","Estimate Slip"));
					 out.write("\n");
				 }
					
			}*/
			 
			 out.write(font_bold_on);
			 out.write(String.format("%25s","Estimate Slip")+"\n");
			 out.write(font_bold_off);
			
			out.write(String.format("%15s","GOLD RATE : "+sales.getGoldOrnBoardRate())+"\n");
			out.write(String.format("%15s","SILVER RATE : "+sales.getSilverOrnBoardRate())+"\n");
			out.write(String.format("%10s %15s","DATE : "+salesDate,"TIME :"+sales.getCurrentTime())+"\n");
			out.write(String.format("%60s","-----------------------------------------------------------\n"));
			out.write(String.format("%1s %14s %14s","","S.No JEWElNAME","PARTICULARS")); 
			out.write("\n");
			out.write(String.format("%60s","-----------------------------------------------------------\n"));
			
			
			//Adding Data for Row 1
			
			
			String jewelName = sales.getItemName(); 
			
			if(jewelName.length() > 15){
				jewelName = sales.getItemName().substring(0, 15);				 
			}
				
			out.write(String.format("%1s %4s %-15s %3.3f ","","1",jewelName,sales.getGrossWeight())+"\n");
			
			if(sales.getValueAdditionCharges().signum() != 0)			
			{
				out.write(String.format("%20s %8s","WASTAGE "+sales.getValueAdditionCharges()+"%",sales.getWastageByGrams())+"\n");	
			}				
			
			out.write(String.format("%35s","------------\n"));
			
			BigDecimal sumWtVa = sales.getGrossWeight().add(sales.getWastageByGrams());
			
			if(sumWtVa.signum() != 0.0){
				out.write(String.format("%29s", sumWtVa)+"\n");	 
			}
			
			out.write(String.format("%35s","------------\n"));
			
			if(sales.getBullionRate().signum() != 0)
			{
				out.write(String.format("%20s %10s","RATE", sales.getBullionRate())+"\n");
			}
			
			out.write(String.format("%35s","------------\n"));
			BigDecimal VawAmt = new BigDecimal(0.00);
			VawAmt = sumWtVa.multiply(sales.getBullionRate());
			
			if(VawAmt.signum() > 0){
				out.write(String.format("%20s %10s","AMOUNT", VawAmt.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			
			if(sales.getMcByGrams().signum() != 0){
				out.write(String.format("%20s %10.2f","MC", sales.getMcByGrams().setScale(2, RoundingMode.UP))+"\n");
			}
			
			if(sales.getSalesHMCharges().signum() != 0){
				out.write(String.format("%20s %10.2f","HM", sales.getSalesHMCharges().setScale(2, RoundingMode.UP))+"\n");
			}
			 
			out.write(String.format("%35s","------------\n"));
			
			if(sales.getAmountAfterLess().signum() != 0)
			{
				out.write(String.format("%20s %10.2f","AMOUNT", sales.getAmountAfterLess().setScale(2, RoundingMode.UP))+"\n");
			}
			
			
			out.write(String.format("%36s","------------\n\n"));
			
			
			// Adding Data for Row 2
			
			
			if(sales.getGrossWeight1().signum()!=0.000) 
			{
				
				String jewelName1 = sales.getItemName1();
				
				if(jewelName1.length() > 15){
					jewelName1 = sales.getItemName1().substring(0, 15);				
				}			
			
				
				out.write(String.format("%1s %4s %-15s %3.3f ","","2",jewelName1,sales.getGrossWeight1())+"\n");
				
				if(sales.getValueAdditionCharges1().signum() != 0)			
				{
					out.write(String.format("%20s %8s","WASTAGE "+sales.getValueAdditionCharges1()+"%",sales.getWastageByGrams1())+"\n");	
				}				
				
				out.write(String.format("%35s","------------\n"));
				
				BigDecimal sumWtVa1 = sales.getGrossWeight1().add(sales.getWastageByGrams1());
				
				if(sumWtVa1.signum() != 0.0){
					out.write(String.format("%29s", sumWtVa1)+"\n");	 
				}
				
				out.write(String.format("%35s","------------\n"));
				
				if(sales.getBullionRate1().signum() != 0)
				{
					out.write(String.format("%20s %10s","RATE", sales.getBullionRate1())+"\n");
				}
				
				out.write(String.format("%35s","------------\n"));
				
				BigDecimal VawAmt1 = new BigDecimal(0.00);
				VawAmt1 = sumWtVa1.multiply(sales.getBullionRate1());
				
				if(VawAmt1.signum() > 0){
					out.write(String.format("%20s %10s","AMOUNT", VawAmt1.setScale(2, RoundingMode.HALF_UP))+"\n");	
				}
				
				if(sales.getMcByGrams1().signum() != 0){
					out.write(String.format("%20s %10s","MC", sales.getMcByGrams1())+"\n");
				}
				
				if(sales.getSalesHMCharges1().signum() != 0){
					out.write(String.format("%20s %10s","HM", sales.getSalesHMCharges1())+"\n");
				}
				
				out.write(String.format("%35s","------------\n"));
				
				if(sales.getAmountAfterLess1().signum() != 0)
				{
					out.write(String.format("%20s %10s","AMOUNT", sales.getAmountAfterLess1())+"\n");
				}
				
				
				out.write(String.format("%36s","------------\n\n"));
			
			}		
			
			
			
			
			
			//Adding Data for the Third Row
			
			if(sales.getGrossWeight2().signum()!=0.000) 
			{
				
				String jewelName2 = sales.getItemName2();
				
				if(jewelName2.length() > 15){
					jewelName2 = sales.getItemName2().substring(0, 15);				
				}			
			
				
				out.write(String.format("%1s %4s %-15s %3.3f ","","3",jewelName2,sales.getGrossWeight2())+"\n");
				
				if(sales.getValueAdditionCharges2().signum() != 0)			
				{
					out.write(String.format("%20s %8s","WASTAGE "+sales.getValueAdditionCharges2()+"%",sales.getWastageByGrams2())+"\n");	
				}				
				
				out.write(String.format("%35s","------------\n"));
				
				BigDecimal sumWtVa2 = sales.getGrossWeight2().add(sales.getWastageByGrams2());
				
				if(sumWtVa2.signum() != 0.0){
					out.write(String.format("%29s", sumWtVa2)+"\n");	 
				}
				
				out.write(String.format("%35s","------------\n"));
				
				if(sales.getBullionRate2().signum() != 0)
				{
					out.write(String.format("%20s %10s","RATE", sales.getBullionRate2())+"\n");
				}
				
				out.write(String.format("%35s","------------\n"));
				
				BigDecimal VawAmt2 = new BigDecimal(0.00);
				VawAmt2 = sumWtVa2.multiply(sales.getBullionRate2());
				
				if(VawAmt2.signum() > 0){
					out.write(String.format("%20s %10s","AMOUNT", VawAmt2.setScale(2, RoundingMode.HALF_UP))+"\n");	
				}
				
				if(sales.getMcByGrams2().signum() != 0){
					out.write(String.format("%20s %10s","MC", sales.getMcByGrams2())+"\n");
				}
				
				if(sales.getSalesHMCharges2().signum() != 0){
					out.write(String.format("%20s %10s","HM", sales.getSalesHMCharges2())+"\n");
				}
				
				out.write(String.format("%35s","------------\n"));
				
				if(sales.getAmountAfterLess2().signum() != 0)
				{
					out.write(String.format("%20s %10s","AMOUNT", sales.getAmountAfterLess2())+"\n");
				}
				
				out.write(String.format("%36s"," ------------\n\n"));
			
			}		
			
			
			
					
			
			//Adding Data for the Fourth Row
			

			if(sales.getGrossWeight3().signum()!=0.000) 
			{
				
				String jewelName3 = sales.getCategoryName3();
				
				if(jewelName3.length() > 15){
					jewelName3 = sales.getItemName3().substring(0, 15);				
				}			
			
				
				out.write(String.format("%1s %4s %-15s %3.3f ","","4",jewelName3,sales.getGrossWeight3())+"\n");
				
				if(sales.getValueAdditionCharges3().signum() != 0)			
				{
					out.write(String.format("%20s %8s","WASTAGE "+sales.getValueAdditionCharges3()+"%",sales.getWastageByGrams3())+"\n");	
				}				
				
				out.write(String.format("%35s","------------\n"));
				
				BigDecimal sumWtVa3 = sales.getGrossWeight3().add(sales.getWastageByGrams3());
				
				if(sumWtVa3.signum() != 0.0){
					out.write(String.format("%29s", sumWtVa3)+"\n");	 
				}
				
				out.write(String.format("%35s","------------\n"));
				
				if(sales.getBullionRate3().signum() != 0)
				{
					out.write(String.format("%20s %10s","RATE", sales.getBullionRate3())+"\n");
				}
				
				out.write(String.format("%35s","------------\n"));
				
				BigDecimal VawAmt3 = new BigDecimal(0.00);
				VawAmt3 = sumWtVa3.multiply(sales.getBullionRate3());
				
				if(VawAmt3.signum() > 0){
					out.write(String.format("%20s %10s","AMOUNT", VawAmt3.setScale(2, RoundingMode.HALF_UP))+"\n");	
				}
				
				if(sales.getMcByGrams3().signum() != 0){
					out.write(String.format("%20s %10s","MC", sales.getMcByGrams3())+"\n");
				}
				
				if(sales.getSalesHMCharges3().signum() != 0){
					out.write(String.format("%20s %10s","HM", sales.getSalesHMCharges3())+"\n");
				}
				
				out.write(String.format("%35s","------------\n"));
				
				if(sales.getAmountAfterLess3().signum() != 0)
				{
					out.write(String.format("%20s %10s","AMOUNT", sales.getAmountAfterLess3())+"\n");
				}
				
				out.write(String.format("%36s"," ------------\n\n"));
			
			}		
			
								
			
			//Adding Data for the Fifth Row
			

			if(sales.getGrossWeight4().signum()!=0.000) 
			{
				
				String jewelName4 = sales.getCategoryName4();
				
				if(jewelName4.length() > 15){
					jewelName4 = sales.getItemName4().substring(0, 15);				
				}			
			
				
			out.write(String.format("%1s %4s %-15s %3.3f ","","5",jewelName4,sales.getGrossWeight4())+"\n");
			
			if(sales.getValueAdditionCharges4().signum() != 0)			
			{
				out.write(String.format("%20s %8s","WASTAGE "+sales.getValueAdditionCharges4()+"%",sales.getWastageByGrams4())+"\n");	
			}				
			
			out.write(String.format("%35s","------------\n"));
			
			BigDecimal sumWtVa4 = sales.getGrossWeight4().add(sales.getWastageByGrams4());
			
			if(sumWtVa4.signum() != 0.0){
				out.write(String.format("%29s", sumWtVa4)+"\n");	 
			}
			
			out.write(String.format("%35s","------------\n"));
			
			if(sales.getBullionRate4().signum() != 0)
			{
				out.write(String.format("%20s %10s","RATE", sales.getBullionRate4())+"\n");
			}
			
			out.write(String.format("%35s","------------\n"));
			
			BigDecimal VawAmt4 = new BigDecimal(0.00);
			VawAmt4 = sumWtVa4.multiply(sales.getBullionRate4());
			
			if(VawAmt4.signum() > 0){
				out.write(String.format("%20s %10s","AMOUNT", VawAmt4.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			
			if(sales.getMcByGrams4().signum() != 0){
				out.write(String.format("%20s %10s","MC", sales.getMcByGrams4())+"\n");
			}
			
			if(sales.getSalesHMCharges4().signum() != 0){
				out.write(String.format("%20s %10s","HM", sales.getSalesHMCharges4())+"\n");
			}
			
			out.write(String.format("%35s","------------\n"));
			
			if(sales.getAmountAfterLess4().signum() != 0)
			{
				out.write(String.format("%20s %10s","AMOUNT", sales.getAmountAfterLess4())+"\n");
			}
			
			
			}		
			
			//out.write(String.format("%35s","------------\n"));
				
			
			
				out.write(String.format("%60s","-----------------------------------------------------------\n"));
			
			/*BigDecimal cashamount=new BigDecimal("0.00");
			BigDecimal voucheramount=new BigDecimal("0.00");			
			BigDecimal checkAmount=new BigDecimal("0.00");
			BigDecimal cardAmount=new BigDecimal("0.00");
			
			cashamount=sales.getCashAmount();
			voucheramount=sales.getVoucherAmount();			
			checkAmount=sales.getChequeAmount();
			cardAmount=sales.getCalCardAmount();*/
				
			BigDecimal balanceAmount=new BigDecimal("0.00");
			balanceAmount=sales.getBalToPay();
			
			if(sales.getAmountAfterLess1().signum() > 0){
			out.write(String.format("%20s %10s","GROSS TOTAL",sales.getTotalAmount().setScale(2, RoundingMode.HALF_UP))+"\n");
			}
			if(sales.getTax().signum()!=0.00)
			{
				out.write(String.format("%20s %10s","VAT 1%",sales.getTax().setScale(2, RoundingMode.HALF_UP))+"\n");	
			}	
			if(sales.getBillDiscAmt().signum()>0){
				
				out.write(String.format("%20s %10s","DISC AMOUNT",sales.getBillDiscAmt())+"\n");	
			}
			out.write(String.format("%20s %10s","SUB TOTAL",sales.getBillAmount())+"\n");
			if(sales.getSalesReturnAmount().signum()!=0.00)
			{
				out.write(String.format("%20s %10s","RETURN:"+sales.getSalesReturnId(),sales.getSalesReturnAmount())+"\n");	
			}
			
			if(sales.getExchangeAmount().signum()!=0.00)
			{
				out.write(String.format("%20s %10s","Old ITEM:"+sales.getExchangeBillNo(),sales.getExchangeAmount())+"\n");	
			}
			if(sales.getSalesOrderAmount().signum()!=0.00)
			{
				out.write(String.format("%20s %10s","ORDER:"+sales.getSalesOrderID(),sales.getSalesOrderAmount())+"\n");	
			}
			if(sales.getSSCardAmount().signum()!=0.00)
			{
				out.write(String.format("%20s %10s","CHIT:"+sales.getSSCardNo(),sales.getSSCardAmount())+"\n");	
			}
			if(sales.getJewelDiscount().signum()>0)
			{
				out.write(String.format("%20s %10s","DISCOUNT:", sales.getJewelDiscount().setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			/*if(voucheramount.signum()>0)
			{
				out.write(String.format("%20s %10s","VOUCHER AMOUNT:",voucheramount.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			if(checkAmount.signum()>0)
			{
				out.write(String.format("%20s %10s","BANK AMOUNT:",checkAmount.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			if(cardAmount.signum()>0)
			{
				out.write(String.format("%20s %10s","CARD AMOUNT:",cardAmount.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}*/
			if(balanceAmount.signum() < 0)
			{
				out.write(String.format("%20s %10s","NET TOTAL:",balanceAmount.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			out.write("\n");
			out.write(font_bold_on);
			/*out.write(String.format("%4s %28s","","Rs."+sales.getBillAmount())+"\n");*/
			//out.write(String.format("%4s %35s","",number.convert(sales.getBillAmount())+"\n"));
			String ConvertedValue = number.convert(sales.getBillAmount())+" ONLY.";
			String ConvertedLine1 = ConvertedValue;
			String ConvertedLine2 = ConvertedValue; 
			
			if(ConvertedLine1.length() > 35){ 
				ConvertedLine1 = ConvertedLine1.substring(0,35);
				out.write(String.format("%2s", ConvertedValue.substring(0,ConvertedLine1.lastIndexOf(" "))+"\n"));
				
				while(ConvertedLine2.length() < 70){
					ConvertedLine2 = ConvertedLine2 + " ";
				}
				ConvertedLine2 = ConvertedLine2.substring(ConvertedLine1.lastIndexOf(" "),70);
				out.write(String.format("%2s", ConvertedLine2)+"\n");
			}else{
				out.write(String.format("%2s", ConvertedValue)+"\n");	
			}
			out.write(font_bold_off);
			out.write(String.format("%9s","-----------------------------------------------------------\n"));
			//out.write(String.format("%4s %25s","","*"+dateFormat.format(cal.getTime())+"/"+sales.getSalesTypeId()+"\n"));			
			out.write(String.format("%4s %35s","","THANK U VISIT AGAIN & AGAIN!!!\n"));
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.flush();
			out.close();
			getPrinterService(sales.getSalesTypeId(),currentUser+"_Invoice.txt");
		}
		catch(Exception e)
		{
			System.out.println("Invoice Print E Message :"+e.getMessage());
			e.printStackTrace();
		}
	}
 
	
	//**Old Purchase Invoice For 4 Inch Dot Matrix Printer **/
	public void printFourInchOldPurchase(Purchase purchase, CompanyInfoDao companyinfo,LedgerDao ledgerDao, HttpServletRequest request)
	{
		try
		{
			HttpSession session = request.getSession();	
			String currentUser = "";
			if(session != null){
				currentUser = session.getAttribute("username").toString();
			}	
					
			Writer wri = new FileWriter(currentUser+"_OldPurchaseInvoice.txt");
			wri = new BufferedWriter(wri);
			PrintWriter out = new PrintWriter(wri);
			CompanyInfo Compnayadd = companyinfo.listCompanyInfo().get(0);
			out.write(font_bold_on);
			out.write("\n");
			out.write(String.format("%28s", Compnayadd.getCompanyName())+"\n");
			out.write(font_bold_off);
			/*String CompanyAddress1 = Compnayadd.getCompanyAddress();
			String CompanyAddress2 = Compnayadd.getCompanyAddress();
			
			if(CompanyAddress1.length() > 35){
				CompanyAddress1 = CompanyAddress1.substring(0,35);
				out.write(String.format("%38s", Compnayadd.getCompanyAddress().substring(0,CompanyAddress1.lastIndexOf(" "))+"\n"));
				
				while(CompanyAddress2.length() < 70){
					CompanyAddress2 = CompanyAddress2 + " "; 
				}
				CompanyAddress2 = CompanyAddress2.substring(CompanyAddress1.lastIndexOf(" "),70);
				out.write(String.format("%35s", CompanyAddress2)+"\n");
			}else{
				out.write(String.format("%36s", Compnayadd.getCompanyAddress())+"\n");	
			}
			//out.write(String.format("%46s", Compnayadd.getCompanyAddress())+"\n");
			out.write(String.format("%20s", Compnayadd.getCity()));
			out.write("\n");
			out.write(String.format("%24s","Ph:"+Compnayadd.getPhone()));
			out.write("\n");*/
			out.write(String.format("%59s","-----------------------------------------------------------"));
			out.write("\n");
			String customername =purchase.getSupplierName();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			 String purchaseDate = formatter.format(purchase.getPurchaseDate());
			if(!customername.equals("Walk-in"))
			{
				 List<Ledger> customerAdd = ledgerDao.searchLedger(customername);
				 out.write(font_bold_on);
				 if(purchase.getPurchaseType().equalsIgnoreCase("Purchase") && (purchase.getItemCode().equalsIgnoreCase("IT100005") || purchase.getItemCode().equalsIgnoreCase("IT100010") || purchase.getBullionType().equalsIgnoreCase("Old Gold Coin") || purchase.getBullionType().equalsIgnoreCase("Old Silver Coin") || purchase.getBullionType().equalsIgnoreCase("Old Gold Pure Bullion") || purchase.getBullionType().equalsIgnoreCase("Old Silver Pure Bullion")))
				 {
					 out.write(String.format("%30s","Estimate Old Purchase")+"\n");
					 out.write(String.format("%2s","To:"+customerAdd.get(0).getLedgerName()));
					 
				 }else{
					 out.write(String.format("%30s","Estimate Old Purchase"));
				 }
				 out.write(font_bold_off);
				 out.write("\n");
				if(customerAdd.get(0).getAddress1()!=null)
				{
					out.write(String.format("%5s", customerAdd.get(0).getAddress1()));
					out.write("\n"); 
				}
				if(customerAdd.get(0).getCity()!=null)
				{
				out.write(String.format("%4s",customerAdd.get(0).getCity()));	
				out.write("\n");
				}
					
				}
			else
			{
				 
				if(purchase.getPurchaseType().equalsIgnoreCase("Purchase") && (purchase.getItemCode().equalsIgnoreCase("IT100005") || purchase.getItemCode().equalsIgnoreCase("IT100010") || purchase.getBullionType().equalsIgnoreCase("Old Gold Coin") || purchase.getBullionType().equalsIgnoreCase("Old Silver Coin") || purchase.getBullionType().equalsIgnoreCase("Old Gold Pure Bullion") || purchase.getBullionType().equalsIgnoreCase("Old Silver Pure Bullion")))
					/* out.write(String.format("%35s","TO:"+sales.getWalkIn_Name()));
					 out.write("\n");
					 out.write(String.format("%35s", sales.getWalkIn_City()));
					 out.write("\n"); */
					 out.write(font_bold_on);
					 out.write(String.format("%30s","Estimate Old Purchase")+"\n");
					 out.write(font_bold_off);		 
					
			}
			
			BigDecimal lessPercent;
			if(purchase.getLess().equalsIgnoreCase("per")){
				lessPercent = purchase.getLessValue();
			}else{
				lessPercent = purchase.getLessValue2();
			}
			
			
			BigDecimal lessGrams;
			if(purchase.getLess().equalsIgnoreCase("grams")){
				lessGrams = purchase.getLessValue();
			}else{
				lessGrams = purchase.getLessValue2();
			}
						
			
			out.write(String.format("%15s","RATE : "+purchase.getRate())+"\n");
			out.write(String.format("%10s %15s","DATE : "+purchaseDate,"TIME :"+purchase.getCurrentTime())+"\n");
			out.write(String.format("%60s","-----------------------------------------------------------\n"));
			out.write(String.format("%1s %10s %4s %7s %6s","","JewelName","Wt","Dust","Less "+lessPercent+"%")); 
			out.write("\n");
			out.write(String.format("%60s","-----------------------------------------------------------\n"));
			
			String jewelName = purchase.getBullionType();
									
			if(jewelName.equalsIgnoreCase("Gold Exchange")){
				jewelName = "Old Gold";
			}
			
			if(jewelName.equalsIgnoreCase("Silver Exchange")){
				jewelName = "Old Silver";
			}
			
			
			if(jewelName.length() > 12){
				jewelName = jewelName.substring(0, 12);				
			}
			
			
				
			out.write(String.format("%1s %-10s %3.3f %3.3f %2.3f","",jewelName ,purchase.getGrossWeight(),purchase.getStoneWeight(), lessGrams)+"\n");
							
			out.write(String.format("%60s","-----------------------------------------------------------\n"));
						
			out.write(String.format("%22s %10s","PURCHASE AMOUNT",purchase.getPurchaseAmount())+"\n");  
			if(purchase.getVatAmount().signum()!=0.00)
			{
				out.write(String.format("%22s %10s","VAT Amount",purchase.getVatAmount())+"\n");	 
			}
			out.write(String.format("%22s %10s","NET AMOUNT", purchase.getTotalAmount())+"\n");
			
			out.write("\n");
			out.write(font_bold_on);		
			String ConvertedValue = number.convert(purchase.getTotalAmount())+" ONLY.";
			String ConvertedLine1 = ConvertedValue;
			String ConvertedLine2 = ConvertedValue; 
			
			if(ConvertedLine1.length() > 35){ 
				ConvertedLine1 = ConvertedLine1.substring(0,35);
				out.write(String.format("%2s", ConvertedValue.substring(0,ConvertedLine1.lastIndexOf(" "))+"\n"));
				
				while(ConvertedLine2.length() < 70){
					ConvertedLine2 = ConvertedLine2 + " ";
				}
				ConvertedLine2 = ConvertedLine2.substring(ConvertedLine1.lastIndexOf(" "),70);
				out.write(String.format("%2s", ConvertedLine2)+"\n");
			}else{
				out.write(String.format("%2s", ConvertedValue)+"\n");	
			}
			out.write(font_bold_off);
			out.write(String.format("%9s","---------------------------------------------------------\n"));
			//out.write(String.format("%4s %25s","","*"+dateFormat.format(cal.getTime())+"/"+sales.getSalesTypeId()+"\n"));			
			out.write(String.format("%4s %30s","","THANK U VISIT AGAIN & AGAIN!!!\n"));
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.flush();
			out.close();
			getPrinterService(purchase.getPurchseInvoice(), currentUser+"_OldPurchaseInvoice.txt");
		}
		catch(Exception e)
		{
			System.out.println("InvoicePrint:"+e.getMessage());
		}
	}
	
	
	/** POS Sales Invoice For 8 Inch Dot Matrix Printer **/
	public void printPOSInvoiceMatrixEightInch(PSales posSales, CompanyInfoDao companyinfo,LedgerDao ledgerDao, HttpServletRequest request) 
	{
	try{
		HttpSession session = request.getSession();	
		String currentUser = "";
		if(session != null){
			currentUser = session.getAttribute("username").toString();
		}
		File file = new File(request.getSession().getServletContext().getRealPath("/")+"/"+"/WEB-INF/reports/"+currentUser+"_POSInvoice8.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		
		System.out.println("The file path is "+request.getSession().getServletContext().getRealPath("/"));
		Writer wri = new FileWriter(file.getAbsoluteFile());
		wri = new BufferedWriter(wri); 
		PrintWriter out = new PrintWriter(wri); 
		CompanyInfo Compnayadd = companyinfo.listCompanyInfo().get(0);
		out.write("\n");
		out.write(font_bold_on);
		out.write(String.format("%38s", Compnayadd.getCompanyName()));
		out.write(font_bold_off);
		out.write("\n");
		out.write(String.format("%56s", Compnayadd.getCompanyAddress()));
		out.write("\n");
		out.write(String.format("%27s %12s",Compnayadd.getCity()+",","PH:"+Compnayadd.getPhone())+"\n");
		out.write(String.format("%31s","TIN:"+Compnayadd.getTinNumber()));
		out.write("\n");
		out.write("----------------------------------------------------------------");
		out.write("\n");
		String customername = posSales.getPossales().getCustomerName(); 
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String salesDate = formatter.format(posSales.getPossales().getSalesdate());
		
		if(!customername.equalsIgnoreCase("Walk-in")) 
		{
			List<Ledger> customerAdd = ledgerDao.searchLedger(customername); 
			//out.write(String.format("%21s","To:"+customerAdd.get(0).getLedgerName()));
			out.write(String.format("%1s","To:"+customerAdd.get(0).getLedgerName()));
			out.write("\n");
			if(customerAdd.get(0).getAddress1()!=null)
			{
				//out.write(String.format("%46s", customerAdd.get(0).getAddress1()));
				out.write(String.format("%1s", customerAdd.get(0).getAddress1()));
				out.write("\n");
			}
			if(customerAdd.get(0).getCity()!=null) 
			{         
				//out.write(String.format("%11s",customerAdd.get(0).getCity()));	
				out.write(String.format("%1s",customerAdd.get(0).getCity()));	
				out.write("\n");
			}
									
		}
		/*else
		{  
			out.write(String.format("%27s","TO:"+posSales.getPossales().getWalkinCustomer()));
			out.write("\n");
			if(posSales.getPossales().getWalkinAddress() != null)
			{
				out.write(String.format("%38s", posSales.getPossales().getWalkinAddress()));
				out.write("\n");
			}
		}*/
		
		out.write(font_bold_on);
		out.write(String.format("%35s","CASH BILL"));
		out.write(font_bold_off);
		out.write("\n");
		out.write(String.format("%18s %40s","Bill No:"+posSales.getPossales().getBillNo(),"Date:"+salesDate)+"\n");
		out.write("----------------------------------------------------------------\n");
		out.write(String.format("%15s %7s %5s %9s %7s %10s","ITEM NAME","QTY","RATE","DISC","TAX","AMOUNT"));
		out.write("\n");
		out.write("-----------------------------------------------------------------\n");
		List<POSSalesItem> list= posSales.getListpossalesItem();
		for(POSSalesItem s : list)
		{	
			StringBuilder ItemName = new StringBuilder(s.getItemName()); 
			while(ItemName.length() < 15){ 
				ItemName.append(" ");
			}
			out.write(String.format("%3s %-15s %2s %8s %7s %7s %10s","", ItemName.substring(0, 15),s.getQuantity(),s.getSalesRate(),s.getDiscountAmount(),s.getSalesTax(),s.getSalesAmount()));
			out.write("\n");				
			out.write("--------------------------------------------------------------- \n");
		}     
		 
		BigDecimal cashamount=new BigDecimal("0.00");
		BigDecimal voucheramount=new BigDecimal("0.00");
		BigDecimal balanceAmount=new BigDecimal("0.00");
		BigDecimal checkAmount=new BigDecimal("0.00"); 
		BigDecimal cardAmount=new BigDecimal("0.00");
		NumToWords number=new NumToWords(); 
		cashamount=posSales.getPossales().getCashAmount();						
		voucheramount=posSales.getPossales().getVoucherAmount();
		balanceAmount=posSales.getPossales().getBalanceToCustomer();
		checkAmount=posSales.getPossales().getChequeAmount(); 
		cardAmount=posSales.getPossales().getCardAmount();   
		
		out.write(String.format("%47s %10s","TOTAL",posSales.getPossales().getSubTotal())+"\n");
		if(posSales.getPossales().getTaxinAmount().signum()!=0.00)
		{
			out.write(String.format("%47s %10s","VAT TAX 1(+)%",posSales.getPossales().getTaxinAmount())+"\n");	
		}
			
			out.write(String.format("%47s %10s","TOTAL AMOUNT",posSales.getPossales().getNetAmount())+"\n");
		
		if(cashamount.signum()>0)
		{ 
			out.write(String.format("%47s %10s","CASH :",cashamount.setScale(2, RoundingMode.HALF_UP))+"\n");	
		}
		if(voucheramount.signum()>0)
		{
			out.write(String.format("%47s %10s","VOUCHER :",voucheramount.setScale(2, RoundingMode.HALF_UP))+"\n");	
		}
		if(checkAmount.signum()>0)
		{
			out.write(String.format("%47s %10s","BANK :",checkAmount.setScale(2, RoundingMode.HALF_UP))+"\n");	
		}
		if(cardAmount.signum()>0)
		{
			out.write(String.format("%47s %10s","CARD :",cardAmount.setScale(2, RoundingMode.HALF_UP))+"\n");	
		}
		if(balanceAmount.signum()>0)
		{
			out.write(String.format("%47s %10s","BALANCE AMOUNT:",balanceAmount.setScale(2, RoundingMode.HALF_UP))+"\n");	
		}
			out.write(font_bold_on);
			out.write(String.format("%47s %10s","NET AMOUNT :",posSales.getPossales().getNetAmount())+"\n");
			out.write("\n");
			out.write(number.convert(posSales.getPossales().getNetAmount())+" ONLY."+"\n");
			out.write(font_bold_off);		
			out.write("----------------------------------------------------------------\n");		
			out.write(String.format("%50s","THANK YOU VISIT AGAIN & AGAIN!!!\n"));	
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n"); 
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n"); 
			out.write("\n");
			out.flush(); 
			out.close();
			getPrinterService(posSales.getPossales().getBillNo(),file.getAbsolutePath());
		}catch(Exception e)
		{
			System.out.println("InvoicePrint:"+e.getMessage());
		}
	}

	
	/** POS Sales Invoice For Four Inch Dot Matrix printer **/
	public void printPOSInvoiceMatrixFourInch(PSales posSales, CompanyInfoDao companyinfo,LedgerDao ledgerDao, HttpServletRequest request  ) 
	{
		String Printer_Init = "@";		
		String clear = "";
		
		try
		{
			HttpSession session = request.getSession();	
			String currentUser = "";
			if(session != null){
				currentUser = session.getAttribute("username").toString();
			}
			File file = new File(request.getSession().getServletContext().getRealPath("/")+"/"+"/WEB-INF/reports/"+currentUser+"_POSInvoice4.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			Writer wri = new FileWriter(file.getAbsoluteFile());
			wri = new BufferedWriter(wri); 
			PrintWriter out = new PrintWriter(wri); 
			CompanyInfo Compnayadd = companyinfo.listCompanyInfo().get(0);
			out.write(Printer_Init); 
			out.write(font_bold_on);
			out.write("\n");
			out.write(String.format("%31s", Compnayadd.getCompanyName()));
			out.write("\n");
			out.write(font_bold_off);
			String CompanyAddress1 = Compnayadd.getCompanyAddress();
			String CompanyAddress2 = Compnayadd.getCompanyAddress();
			
			if(CompanyAddress1.length() > 35){
				CompanyAddress1 = CompanyAddress1.substring(0,35);
				out.write(String.format("%35s", Compnayadd.getCompanyAddress().substring(0,CompanyAddress1.lastIndexOf(" "))+"\n"));
				
				while(CompanyAddress2.length() < 70){
					CompanyAddress2 = CompanyAddress2 + " "; 
				}
				CompanyAddress2 = CompanyAddress2.substring(CompanyAddress1.lastIndexOf(" "),70);
				out.write(String.format("%35s", CompanyAddress2)+"\n");
			}else{
				out.write(String.format("%36s", Compnayadd.getCompanyAddress())+"\n");	
			}
			out.write(String.format("%20s %13s", Compnayadd.getCity()+",","PH:"+Compnayadd.getPhone())+"\n");
			out.write(String.format("%27s","TIN:"+Compnayadd.getTinNumber())+"\n");
			out.write("------------------------------------------------"+"\n");
			String customername = posSales.getPossales().getCustomerName(); 
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String salesDate = formatter.format(posSales.getPossales().getSalesdate());
			
			if(!customername.equalsIgnoreCase("Walk-in")) 
			{
				List<Ledger> customerAdd = ledgerDao.searchLedger(customername); 
				out.write(String.format("%11s","To:"+customerAdd.get(0).getLedgerName())+"\n");
				if(customerAdd.get(0).getAddress1()!=null)
				{
					out.write(String.format("%11s", customerAdd.get(0).getAddress1())+"\n");
				}
				if(customerAdd.get(0).getCity()!=null)
				{
					out.write(String.format("%7s",customerAdd.get(0).getCity())+"\n");	
				}
										
			}
			/*else
			{  
				out.write(String.format("%11s","TO:"+posSales.getPossales().getWalkinCustomer())+"\n");
				if(posSales.getPossales().getWalkinAddress() != null)
				{
					out.write(String.format("%7s", posSales.getPossales().getWalkinAddress())+"\n");
				}
			}*/
			out.write(font_bold_on);
			out.write("\n");
			out.write(String.format("%22s","CASH BILL"));
			out.write("\n");
			out.write(font_bold_off); 
			out.write(String.format("%11s %24s","Bill No:"+posSales.getPossales().getBillNo(),"Date:"+salesDate)+"\n");			
			out.write("------------------------------------------------"+"\n");
			out.write(String.format("%2s %5s %7s %11s","ITEM NAME","QTY","RATE","AMOUNT"));
			out.write("\n");
			out.write("------------------------------------------------"+"\n");
			List<POSSalesItem> list= posSales.getListpossalesItem();
			for(POSSalesItem s : list)
			{
				StringBuilder ItemName = new StringBuilder(s.getItemName()); 
				while(ItemName.length() < 12){
					ItemName.append(" ");
				}
				out.write(String.format("%1s %2s %10s %9s",ItemName.substring(0, 12),s.getQuantity(),s.getSalesRate(),s.getSalesAmount())+"\n");
				out.write("------------------------------------------------"+"\n");
			}
			
			BigDecimal cashamount=new BigDecimal("0.00");
			BigDecimal voucheramount=new BigDecimal("0.00");
			BigDecimal balanceAmount=new BigDecimal("0.00");
			BigDecimal checkAmount=new BigDecimal("0.00"); 
			BigDecimal cardAmount=new BigDecimal("0.00");
			NumToWords number=new NumToWords(); 
			cashamount=posSales.getPossales().getCashAmount();						
			voucheramount=posSales.getPossales().getVoucherAmount();
			balanceAmount=posSales.getPossales().getBalanceToCustomer();
			checkAmount=posSales.getPossales().getChequeAmount(); 
			cardAmount=posSales.getPossales().getCardAmount();   
			
			out.write(String.format("%26s %9s","TOTAL",posSales.getPossales().getSubTotal())+"\n");
			if(posSales.getPossales().getTaxinAmount().signum()!=0.00)
			{
				out.write(String.format("%26s %9s","VAT TAX 1(+)%",posSales.getPossales().getTaxinAmount())+"\n");	
			}
			out.write(String.format("%26s %9s","TOTAL AMOUNT",posSales.getPossales().getNetAmount())+"\n");
			if(cashamount.signum()>0)
			{ 
				out.write(String.format("%26s %9s","CASH :",cashamount.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			if(voucheramount.signum()>0)
			{
				out.write(String.format("%26s %9s","VOUCHER :",voucheramount.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			if(checkAmount.signum()>0)
			{
				out.write(String.format("%26s %9s","BANK :",checkAmount.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			if(cardAmount.signum()>0) 
			{
				out.write(String.format("%26s %9s","CARD :",cardAmount.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			if(balanceAmount.signum()>0)
			{
				out.write(String.format("%26s %9s","BALANCE AMOUNT :",balanceAmount.setScale(2, RoundingMode.HALF_UP)));	
			}
			out.write("\n");
			out.write(font_bold_on);			
			out.write(String.format("%26s %9s","NET AMOUNT ",posSales.getPossales().getNetAmount().setScale(2, RoundingMode.HALF_UP))+"\n");
			String ConvertedValue = number.convert(posSales.getPossales().getNetAmount())+" ONLY.";
			String ConvertedLine1 = ConvertedValue;
			String ConvertedLine2 = ConvertedValue; 
			
			if(ConvertedLine1.length() > 35){ 
				ConvertedLine1 = ConvertedLine1.substring(0,35);
				out.write(String.format("%2s", ConvertedValue.substring(0,ConvertedLine1.lastIndexOf(" "))+"\n"));
				
				while(ConvertedLine2.length() < 70){
					ConvertedLine2 = ConvertedLine2 + " ";
				}
				ConvertedLine2 = ConvertedLine2.substring(ConvertedLine1.lastIndexOf(" "),70);
				out.write(String.format("%2s", ConvertedLine2)+"\n");
			}else{
				out.write(String.format("%2s", ConvertedValue)+"\n");	
			}
			//out.write("\n");
			out.write(font_bold_off); 
			out.write("----------------------------------------------"+"\n");						
			out.write(String.format("%30s","THANK YOU VISIT AGAIN !!!"));
			out.write("\n");
			out.write(clear);
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n"); 
			out.write("\n");
			out.write("\n");
			out.write("\n"); 
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");		
			out.flush(); 
			out.close();
			getPrinterService(posSales.getPossales().getBillNo(),file.getAbsolutePath());			
			
		}catch(Exception e){
				System.out.println("InvoicePrint:"+e.getMessage());
		}		
	}

	/** POS Sales Invoice For Citizen 3 inch Printer **/
	public void printPOSInvoiceCitizen(PSales posSales, CompanyInfoDao companyinfo,LedgerDao ledgerDao, HttpServletRequest request) 
	{
		String Printer_Init = "@";
		String Partial_Cut = "VB ";
		String clear = "";
		
		try
		{
			HttpSession session = request.getSession();	
			String currentUser = "";
			if(session != null){
				currentUser = session.getAttribute("username").toString();
			}
			File file = new File(request.getSession().getServletContext().getRealPath("/")+"/"+"/WEB-INF/reports/"+currentUser+"_POSInvoice3.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			Writer wri = new FileWriter(file.getAbsoluteFile());
			wri = new BufferedWriter(wri); 
			PrintWriter out = new PrintWriter(wri); 
			CompanyInfo Compnayadd = companyinfo.listCompanyInfo().get(0);
			out.write(Printer_Init);
			out.write(font_bold_on);
			out.write("\n");
			out.write(String.format("%31s", Compnayadd.getCompanyName()));
			out.write("\n");
			out.write(font_bold_off);
			out.write(String.format("%36s", Compnayadd.getCompanyAddress())+"\n");
			out.write(String.format("%20s %13s", Compnayadd.getCity()+",","Ph:"+Compnayadd.getPhone())+"\n");
			out.write(String.format("%27s","TIN:"+Compnayadd.getTinNumber())+"\n");
			out.write("------------------------------------------------"+"\n");
			String customername = posSales.getPossales().getCustomerName(); 
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String salesDate = formatter.format(posSales.getPossales().getSalesdate());
			
			if(!customername.equalsIgnoreCase("Walk-in")) 
			{
				List<Ledger> customerAdd = ledgerDao.searchLedger(customername); 
				out.write(String.format("%11s","To:"+customerAdd.get(0).getLedgerName())+"\n");
				if(customerAdd.get(0).getAddress1()!=null)
				{
					out.write(String.format("%11s", customerAdd.get(0).getAddress1())+"\n");
				}
				if(customerAdd.get(0).getCity()!=null)
				{
					out.write(String.format("%1s",customerAdd.get(0).getCity())+"\n");	
				}
										
			}
			/*else
			{  
				out.write(String.format("%11s","TO:"+posSales.getPossales().getWalkinCustomer())+"\n");
				if(posSales.getPossales().getWalkinAddress() != null)
				{
					out.write(String.format("%11s", posSales.getPossales().getWalkinAddress())+"\n");
				}
			}*/
			out.write(font_bold_on);
			out.write("\n");
			out.write(String.format("%26s","CASH BILL"));
			out.write("\n");
			out.write(font_bold_off);
			out.write(String.format("%11s %30s","Bill No:"+posSales.getPossales().getBillNo(),"Date:"+salesDate)+"\n");
			out.write("------------------------------------------------"+"\n");
			out.write(String.format("%1s %7s %8s %7s %10s","ITEM NAME","QTY","RATE","TAX","AMOUNT"));
			out.write("\n");
			out.write("------------------------------------------------"+"\n");
			List<POSSalesItem> list= posSales.getListpossalesItem();
			for(POSSalesItem s : list)
			{
				StringBuilder ItemName = new StringBuilder(s.getItemName()); 
				while(ItemName.length() < 12){
					ItemName.append(" ");
				}
				out.write(String.format("%1s %2s %10s %7s %10s",ItemName.substring(0, 12),s.getQuantity(),s.getSalesRate(),s.getDiscountAmount(),s.getSalesAmount())+"\n");
				out.write("------------------------------------------------"+"\n");
			}
			
			BigDecimal cashamount=new BigDecimal("0.00");
			BigDecimal voucheramount=new BigDecimal("0.00");
			BigDecimal balanceAmount=new BigDecimal("0.00");
			BigDecimal checkAmount=new BigDecimal("0.00"); 
			BigDecimal cardAmount=new BigDecimal("0.00");
			NumToWords number=new NumToWords(); 
			cashamount=posSales.getPossales().getCashAmount();						
			voucheramount=posSales.getPossales().getVoucherAmount();
			balanceAmount=posSales.getPossales().getBalanceToCustomer();
			checkAmount=posSales.getPossales().getChequeAmount(); 
			cardAmount=posSales.getPossales().getCardAmount();   
			
			out.write(String.format("%35s %9s","TOTAL",posSales.getPossales().getSubTotal())+"\n");
			if(posSales.getPossales().getTaxinAmount().signum()!=0.00)
			{
				out.write(String.format("%35s %9s","VAT TAX 1(+)%",posSales.getPossales().getTaxinAmount())+"\n");	
			}
			out.write(String.format("%35s %9s","TOTAL AMOUNT",posSales.getPossales().getNetAmount())+"\n");
			if(cashamount.signum()>0)
			{ 
				out.write(String.format("%35s %9s","CASH :",cashamount.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			if(voucheramount.signum()>0)
			{
				out.write(String.format("%35s %9s","VOUCHER :",voucheramount.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			if(checkAmount.signum()>0)
			{
				out.write(String.format("%35s %9s","BANK :",checkAmount.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			if(cardAmount.signum()>0)
			{
				out.write(String.format("%35s %9s","CARD :",cardAmount.setScale(2, RoundingMode.HALF_UP))+"\n");	
			}
			if(balanceAmount.signum()>0)
			{
				out.write(String.format("%35s %9s","BALANCE AMOUNT:",balanceAmount.setScale(2, RoundingMode.HALF_UP)));	
			}
			out.write(font_bold_on);
			out.write("\n");
			out.write(String.format("%35s %9s","NET AMOUNT:", posSales.getPossales().getNetAmount().setScale(2, RoundingMode.HALF_UP))+"\n");
			out.write("\n");
			String ConvertedValue = number.convert(posSales.getPossales().getNetAmount())+" ONLY.";
			String ConvertedLine1 = ConvertedValue;
			String ConvertedLine2 = ConvertedValue; 
			
			if(ConvertedLine1.length() > 40){ 
				ConvertedLine1 = ConvertedLine1.substring(0,40);
				out.write(String.format("%2s", ConvertedValue.substring(0,ConvertedLine1.lastIndexOf(" "))+"\n"));
				
				while(ConvertedLine2.length() < 70){
					ConvertedLine2 = ConvertedLine2 + " ";
				}
				ConvertedLine2 = ConvertedLine2.substring(ConvertedLine1.lastIndexOf(" "),70);
				out.write(String.format("%2s", ConvertedLine2)+"\n");
			}else{
				out.write(String.format("%2s", ConvertedValue)+"\n");	
			}
			//out.write("\n");
			out.write(font_bold_off);
			out.write("------------------------------------------------"+"\n");						
			out.write(String.format("%35s","THANK YOU VISIT AGAIN!!!"));
			out.write("\n");
			out.write(clear);
			out.write("\n");
			out.write(Partial_Cut);
						
			out.flush(); 
			out.close();
			getPrinterService(posSales.getPossales().getBillNo(),file.getAbsolutePath());
			
		}catch(Exception e){
				System.out.println("InvoicePrint:"+e.getMessage());
		}		
	}
}
