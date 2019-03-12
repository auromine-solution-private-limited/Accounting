

package com.jewellery.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.jewellery.entity.ItemMaster;
import com.jewellery.core.JewelStockCore;
import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.JewelStockDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.dao.JobOrderDao;
import com.jewellery.entity.JobOrder;
import com.jewellery.validator.JobOrderValidator;

@Controller
public class FormJobOrderController {

	private JobOrderDao joborderDao;
	private LedgerDao ledgerDao; 
	private ItemMasterDao itemmasterDao;	
	private ItemMaster itemDetails = new ItemMaster();
	private JewelStockDao jewelStockDao;
	//private JewelStock jewelStockObj;
	
	String jobStatus;
	String itemcd;
	String jewelTransType;
	
	BigDecimal issuedGrossWeight;
	BigDecimal issuedNetWeight;
	BigDecimal grossWeight;
	BigDecimal netWt;
	BigDecimal totalGrossWt;
	BigDecimal jewelStockGrossWt;
	BigDecimal jewelStockNetWt;
	BigDecimal CONVERT=new BigDecimal("-1");
	
	int nop;
	int ppq, tp;
	int jewelStockQty;
	
	@Autowired
	private JobOrderValidator jobOrderValidator;
	
	@Autowired
	public FormJobOrderController(JobOrderDao joborderDao, LedgerDao ledgerDao, ItemMasterDao itemmasterDao, JewelStockDao jewelStockDao){		
		this.joborderDao = joborderDao;
		this.ledgerDao = ledgerDao; 
		this.itemmasterDao = itemmasterDao;			
		this.jewelStockDao = jewelStockDao;
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
	throws Exception{
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class,null, dateEditor);
	}		
	
	//To save the record
	@RequestMapping(value = "/formjoborder.htm", method = RequestMethod.POST,params="insert")
	public ModelAndView addOrder(@ModelAttribute("joborder") JobOrder joborder, BindingResult result,  ModelMap model, SessionStatus status) 
	{		
		jobOrderValidator.validate(joborder, result);
				
		itemcd = joborder.getFromItemCode();
		issuedGrossWeight = joborder.getIssuedGrossWeight();
		issuedNetWeight = joborder.getIssuedNetWeight();
		jobStatus = joborder.getStatus();
		
		List<?> ledgerlist = ledgerDao.listallSmithName();
		model.addAttribute("suppliername", ledgerlist);
		
		if(result.hasErrors())
		{
			ModelMap map = new ModelMap();
			map.addAttribute("errorType", "insertError");
			map.put("command", joborder);
			return new ModelAndView("formjoborder",map);
		}
		
		joborderDao.insertJobOrder(joborder);// Insert the record in Job order		
		status.setComplete();
		
		
		//Update stock if the Job order status is Issued  
		if(jobStatus.equals("Issued")){	          	
         			
      		List<ItemMaster> itemList = itemmasterDao.searchItemMaster(itemcd);
      		
  			for (int i = 0; i < itemList.size(); i++) {
  				ItemMaster imast = (ItemMaster)itemList.get(i);
  				if (imast instanceof ItemMaster) {
  					
	  				itemDetails = (ItemMaster) imast; 
					
	  				grossWeight = itemDetails.getGrossWeight();
	  				grossWeight = grossWeight.subtract(issuedGrossWeight);
	  				itemDetails.setGrossWeight(grossWeight);
					
	  				netWt = itemDetails.getNetWeight();    			
	  				netWt = netWt.subtract(issuedNetWeight);   				 
	  				itemDetails.setNetWeight(netWt);    
	
					totalGrossWt = itemDetails.getTotalGrossWeight();
	  				totalGrossWt = totalGrossWt.subtract(issuedGrossWeight);
	  				itemDetails.setTotalGrossWeight(totalGrossWt); 
	  				
	  				itemmasterDao.updateItemMaster(itemDetails);//update detail in item master 
  				}    				
  			}
  			
  			//Update Stock Entry In Jewel Stock
  			
  			JewelStockCore.JewelStock_unTaged_NegativeEntry_JobOrder(jewelStockDao, issuedGrossWeight, issuedNetWeight, issuedGrossWeight, jobStatus, joborder);  				 			
			
         }     
		
		if(joborder.getStatus().equalsIgnoreCase("Issued") ||  joborder.getStatus().equalsIgnoreCase("Received")){
			return new ModelAndView("redirect:jobOrderPreview.htm?jobOrderNO="+joborder.getJobOrderId()+"&joborderStatus="+joborder.getStatus(), model);
		}
		
			return new ModelAndView("redirect:joborder.htm");
	} 
	 
	
	@RequestMapping(value = "/jobOrderPreview.htm", method = RequestMethod.GET)
	public ModelAndView pdfVoucherPreview() {
		return new ModelAndView("jobOrderPreview");
	}
	
	//to Update the record
	@RequestMapping(value = "/formjoborderrcpt.htm", method = RequestMethod.POST,params="update")
	public ModelAndView updateOrder(@ModelAttribute("joborder") JobOrder joborder, BindingResult result, ModelMap model) {
		
		jobOrderValidator.validate(joborder, result);
		
		 jobStatus = joborder.getStatus();
		 itemcd = joborder.getToItemCode();		  
 		 BigDecimal finisheditemGrossWt = joborder.getFinisheditemGrossWt();
 		 Integer numberOfPieces = joborder.getNumberOfPieces(); 
 		 
 		Integer orderId=joborder.getJobOrderId();
		JobOrder jobOrderobj=joborderDao.getJobOrder(orderId);		 	
		 		
		model.addAttribute("suppliername", ledgerDao.listallSmithName());
 		 
		if(result.hasErrors())
		{			 
			ModelMap map = new ModelMap();			
			model.addAttribute("errorType","updateError");
			map.put("command", joborder);
			return new ModelAndView("formjoborderrcpt",map);
		}	
		
		joborderDao.updateJobOrder(joborder);	

		String frmitemcode=joborder.getFromItemCode();
		 
		 /************************ Stock Updation for Issue ***********************/
		
		 if(jobStatus.equals("Issued") && !jobOrderobj.getStatus().equals("Received")){	 		  
			 
			 List<ItemMaster> itemList = itemmasterDao.searchItemMaster(frmitemcode);
	    		
			 for (int i = 0; i < itemList.size(); i++) {
 				ItemMaster imast = (ItemMaster)itemList.get(i);
 				if (imast instanceof ItemMaster) {
 					
		 			itemDetails = (ItemMaster) imast;   
		 			
			 			if(jobOrderobj.getBullionType().equals(joborder.getBullionType())){
			 			
				 			BigDecimal newissuedgrossWeight = joborder.getIssuedGrossWeight();			 					 				
				 					 			
					 		grossWeight = itemDetails.getGrossWeight();		 			
						 	BigDecimal diff = jobOrderobj.getIssuedGrossWeight().subtract(newissuedgrossWeight);
						 	grossWeight=grossWeight.add(diff); 
						 						 					
						 	netWt = itemDetails.getNetWeight();
						 	BigDecimal diffnet = jobOrderobj.getIssuedGrossWeight().subtract(newissuedgrossWeight);
						 	netWt=netWt.add(diffnet);		 					 
						 				
						 	totalGrossWt = itemDetails.getTotalGrossWeight();
						 	BigDecimal totGrossWt = jobOrderobj.getIssuedGrossWeight().subtract(newissuedgrossWeight);
						 	totalGrossWt=	totalGrossWt.add(totGrossWt);
						 	
						 	itemDetails.setNetWeight(netWt); 
				 			itemDetails.setGrossWeight(grossWeight);  
				 			itemDetails.setTotalGrossWeight(totalGrossWt);
				 			itemmasterDao.updateItemMaster(itemDetails);	 			
				 		
			 			} 
			 			else{
			 				frmitemcode = jobOrderobj.getFromItemCode();
			 				issuedGrossWeight = jobOrderobj.getIssuedGrossWeight();	
			 				totalGrossWt = joborder.getIssuedGrossWeight();
			 				itemmasterDao.updateOldStock(issuedGrossWeight, issuedGrossWeight, issuedGrossWeight,0, frmitemcode);
			 				itemmasterDao.updateLooseStock(totalGrossWt, totalGrossWt, totalGrossWt,0, joborder.getFromItemCode());	 				
						 
			 			}		 			
 					}	 			 
	 			}
			 
			 //jewel Stock Updation on Joborder Issue Updation
			 JewelStockCore.JewelStock_unTaged_UpdateEntry_JobOrder(jewelStockDao, CONVERT.multiply(joborder.getIssuedGrossWeight()), CONVERT.multiply(joborder.getIssuedGrossWeight()), CONVERT.multiply(joborder.getIssuedGrossWeight()), 0, joborder.getBullionType(), joborder.getFromItemCode(), jobStatus, joborder);			
		}	
		 /*****************Stock update on order receive********************/
		 else if(jobStatus.equals("Received")){			 
			    		    	
	 		 List<ItemMaster> itemList1 = itemmasterDao.searchItemMaster(itemcd);
    		
    			for (int i = 0; i < itemList1.size(); i++) {
    				ItemMaster imast = (ItemMaster)itemList1.get(i);
    				if (imast instanceof ItemMaster) {
    					 
    				itemDetails = (ItemMaster) imast;     				
    				
    				grossWeight = itemDetails.getGrossWeight(); 
    				netWt = itemDetails.getNetWeight();  
    				totalGrossWt = itemDetails.getTotalGrossWeight();
    				nop = itemDetails.getQty();
    				System.out.println("The finished item gross weight is" +finisheditemGrossWt);
    				 
    				// Check the current and old category names are equal
    				if(!joborder.getCategoryName().equals(jobOrderobj.getCategoryName())){
    					    					 
    					frmitemcode = jobOrderobj.getFromItemCode();
		 				issuedGrossWeight = jobOrderobj.getIssuedGrossWeight();	 
		 				totalGrossWt = joborder.getIssuedGrossWeight();			 				
		 				
		 				itemmasterDao.updateOldStock(issuedGrossWeight, issuedGrossWeight, issuedGrossWeight,0, frmitemcode);// add old Issued Weight to the stock  
		 				itemmasterDao.updateLooseStock(totalGrossWt, totalGrossWt, totalGrossWt,0, joborder.getFromItemCode());//Subtract the Current Issued Weight from the stock		 				
		 				
		 				itemmasterDao.updateLooseStock(jobOrderobj.getFinisheditemGrossWt(), jobOrderobj.getFinisheditemGrossWt(), jobOrderobj.getFinisheditemGrossWt(), jobOrderobj.getNumberOfPieces(), jobOrderobj.getToItemCode());// Subtract the Old finsished weight to the stock
		 				itemmasterDao.updateOldStock(joborder.getFinisheditemGrossWt(), joborder.getFinisheditemGrossWt(),joborder.getFinisheditemGrossWt(), joborder.getNumberOfPieces(), joborder.getToItemCode());//Add the current finished weight to the stock
		 				
		 				//Jewel Stock Updation on Jorder received item updation
        				//jewelCore.JewelStock_unTaged_UpdateEntry_JobOrder(jewelStockDao, finisheditemGrossWt, finisheditemGrossWt, finisheditemGrossWt, numberOfPieces, joborder.getCategoryName(), joborder.getToItemCode(), jobStatus, joborder);
		 				
		 				//Jewel Stock Insertion on Receiving the job order
		 				
		 				if(jobOrderobj.getFinisheditemGrossWt().signum() == 0)
		 				{
		 					JewelStockCore.JewelStock_unTaged_PositiveEntry_JobOrder(jewelStockDao, finisheditemGrossWt, finisheditemGrossWt, finisheditemGrossWt, numberOfPieces, jobStatus, joborder);
		 				}     			  			
		       		}
    				else  
    					{
    						BigDecimal diff_wt = jobOrderobj.getFinisheditemGrossWt().subtract(finisheditemGrossWt);   
    						int diff_pcs = jobOrderobj.getNumberOfPieces() - numberOfPieces;
        				    				
		        			if(jobOrderobj.getFinisheditemGrossWt().signum() == 0.0){
		        				
		        				grossWeight = grossWeight.add(finisheditemGrossWt);
		        				netWt = netWt.add(finisheditemGrossWt);
		        				totalGrossWt = totalGrossWt.add(finisheditemGrossWt); 
		        				nop = nop + numberOfPieces;
		        				
		        				//Jewel Stock Insertion on Receiving the job order
		        				if(jobOrderobj.getFinisheditemGrossWt().signum() == 0)
				 				{
		        					JewelStockCore.JewelStock_unTaged_PositiveEntry_JobOrder(jewelStockDao, finisheditemGrossWt, finisheditemGrossWt, finisheditemGrossWt, numberOfPieces, jobStatus, joborder);
				 				}
		        			}
		        			else{
		        				grossWeight = grossWeight.subtract(diff_wt);
		        				netWt = netWt.subtract(diff_wt); 
		        				totalGrossWt = totalGrossWt.subtract(diff_wt);
		        				nop = nop - diff_pcs;		        				
		        				itemmasterDao.updateOldStock(joborder.getFinisheditemGrossWt(), joborder.getFinisheditemGrossWt(),joborder.getFinisheditemGrossWt(), joborder.getNumberOfPieces(), joborder.getToItemCode());//Add the current finished weight to the stock
		        				
		        				
		        				//Jewel Stock Updation on Jorder received item updation
		        				//jewelCore.JewelStock_unTaged_UpdateEntry_JobOrder(jewelStockDao, finisheditemGrossWt, finisheditemGrossWt, finisheditemGrossWt, numberOfPieces, joborder.getCategoryName(), joborder.getToItemCode(), jobStatus, joborder);
		        			}  			
		        			
	        			 
	        				ppq = itemDetails.getPiecesPerQty();
	        				tp = nop *ppq;
	        				itemDetails.setGrossWeight(grossWeight);
	        				itemDetails.setNetWeight(netWt);
	        				itemDetails.setTotalGrossWeight(totalGrossWt);
	        				itemDetails.setTotalPieces(tp);
	        				itemDetails.setQty(nop);    			
	        				
	        				itemmasterDao.updateItemMaster(itemDetails);   	
    		 			}
        			}    					
    			}
    			
    			if(jobOrderobj.getFinisheditemGrossWt().signum() != 0 || jobOrderobj.getNumberOfPieces() != 0  || !jobOrderobj.getFinisheditemGrossWt().equals(finisheditemGrossWt) || jobOrderobj.getNumberOfPieces() != numberOfPieces)
    			{
    				//Jewel Stock Updation on Jorder received item updation
    				JewelStockCore.JewelStock_unTaged_UpdateEntry_JobOrder(jewelStockDao, finisheditemGrossWt, finisheditemGrossWt, finisheditemGrossWt, numberOfPieces, joborder.getCategoryName(), joborder.getToItemCode(), jobStatus, joborder);
    			}

    			if(!jobOrderobj.getIssuedGrossWeight().equals(joborder.getIssuedGrossWeight()))
    			{
	    			 //jewel Stock Updation on Joborder Issue Updation
    				JewelStockCore.JewelStock_unTaged_UpdateEntry_JobOrder(jewelStockDao, CONVERT.multiply(joborder.getIssuedGrossWeight()), CONVERT.multiply(joborder.getIssuedGrossWeight()), CONVERT.multiply(joborder.getIssuedGrossWeight()), 0, joborder.getBullionType(), joborder.getFromItemCode(), jobStatus, joborder);
    			}
    			
			
    			
		 	}
		 	/****************** Stock Update when  the job order status changed from receive to Issue ******************/
		 	else if(jobOrderobj.getStatus().equals("Received") && joborder.getStatus().equals("Issued")){
				totalGrossWt = jobOrderobj.getFinisheditemGrossWt();
				issuedGrossWeight = jobOrderobj.getIssuedGrossWeight();
				BigDecimal newissuedgrossWeight = joborder.getIssuedGrossWeight();			 		
				nop = jobOrderobj.getNumberOfPieces();				
				ppq = itemDetails.getPiecesPerQty();
				tp = nop *ppq;				 
				
				if(!issuedGrossWeight.equals(newissuedgrossWeight)){
					BigDecimal diffWt = jobOrderobj.getIssuedGrossWeight().subtract(newissuedgrossWeight);	
					itemmasterDao.updateOldStock(diffWt, diffWt, diffWt, 0, joborder.getFromItemCode());
					
					 //jewel Stock Updation on Joborder Issue Updation
					JewelStockCore.JewelStock_unTaged_UpdateEntry_JobOrder(jewelStockDao, CONVERT.multiply(joborder.getIssuedGrossWeight()), CONVERT.multiply(joborder.getIssuedGrossWeight()), CONVERT.multiply(joborder.getIssuedGrossWeight()), 0, joborder.getBullionType(), joborder.getFromItemCode(), jobStatus, joborder);			
				}
				
				itemmasterDao.updateLooseStock(totalGrossWt, totalGrossWt, totalGrossWt, nop, jobOrderobj.getToItemCode());				
				
				//Jewel Stock Updation on Jorder received item updation
				JewelStockCore.JewelStock_unTaged_UpdateEntry_JobOrder(jewelStockDao, finisheditemGrossWt, finisheditemGrossWt, finisheditemGrossWt, numberOfPieces, joborder.getCategoryName(), joborder.getToItemCode(), jobOrderobj.getStatus(), joborder);
				
				
		 	}
		 	else if(jobOrderobj.getStatus().equals("Issued") && joborder.getStatus().equals("Received")){
		 		totalGrossWt = joborder.getFinisheditemGrossWt();
				nop = joborder.getNumberOfPieces();				 
				ppq = itemDetails.getPiecesPerQty();
				tp = nop *ppq;				 
				
				itemmasterDao.updateOldStock(totalGrossWt, totalGrossWt, totalGrossWt, nop, joborder.getToItemCode());	
				
				if(jobOrderobj.getFinisheditemGrossWt().signum() == 0)
				{
				//Jewel Stock Insertion on Receiving the job order
					JewelStockCore.JewelStock_unTaged_PositiveEntry_JobOrder(jewelStockDao, finisheditemGrossWt, finisheditemGrossWt, finisheditemGrossWt, numberOfPieces, jobStatus, joborder);
				}
		 	}
		 
		 if(joborder.getStatus().equalsIgnoreCase("Issued") ||  joborder.getStatus().equalsIgnoreCase("Received")){
				return new ModelAndView("redirect:jobOrderPreview.htm?jobOrderNO="+joborder.getJobOrderId()+"&joborderStatus="+joborder.getStatus(), model);
			}
		
		return new ModelAndView("redirect:joborder.htm"); 
	}
	
	//redirect the form for update
	@RequestMapping(value = "/formjoborderrcpt.htm", method = RequestMethod.GET)
	public ModelAndView getUpdate(@RequestParam(value="jobOrderId", required=true) Integer jobOrderId,  @ModelAttribute("joborder") JobOrder jobOrder, ModelMap model) 
	{
		model.addAttribute("joborder", joborderDao.getJobOrder(jobOrderId));
		List<?> ledgerlist = ledgerDao.listallSmithName();    
		model.addAttribute("suppliername", ledgerlist);	
		return new ModelAndView("formjoborderrcpt",model);		
	}
	
	//bind the sithname for joborder
	@RequestMapping(value="/formjoborder", method=RequestMethod.GET)
	public String jobOrderList(@ModelAttribute("joborder") JobOrder jobOrder, Model model){
		List<?> ledgerlist = ledgerDao.listallSmithName();    
		model.addAttribute("suppliername", ledgerlist);		    
		return "formjoborder";
	}
		
	//canceling Order Issue request mapping				
	@RequestMapping(value="/formjoborder.htm",method=RequestMethod.POST,params="cancel") // cancel button to redirect to customer list page
	public String cancelForm(@ModelAttribute("joborder") JobOrder joborder)
	{
		return "redirect:joborder.htm";
	}
	
	//canceling Order request mapping				
	@RequestMapping(value="/formjoborderrcpt.htm",method=RequestMethod.POST,params="cancel") // cancel button to redirect to customer list page
	public String updateCancel(@ModelAttribute("joborder") JobOrder joborder)
	{
		return "redirect:joborder.htm";
	}
	
	//listing job order
	@RequestMapping(value="/joborder")
	public String listJobOrder(Map<String, Object> map){		
		List<JobOrder> jobOrderList = joborderDao.listJobOrder();
		((ModelMap) map).addAttribute("jobOrderList", jobOrderList);
		return "joborder";
	}	
}

