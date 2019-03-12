package com.jewellery.validator;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.JobOrderDao;
import com.jewellery.entity.ItemMaster;
import com.jewellery.entity.JobOrder;


@Component("JobOrderValidator")
public class JobOrderValidator{
	
	@Autowired	
	private ItemMasterDao itemmasterDao;
	
	@Autowired
	private JobOrderDao joborderDao;
	
	private ItemMaster itemDetails = new ItemMaster();//Instance of itemmaster class
	
	public boolean supports(Class<?> clazz){
		return JobOrder.class.isAssignableFrom(clazz);		
	}	
	
	public void validate(Object command, Errors errors){
		  
		 JobOrder jo = (JobOrder)command;  
		 String orderStatus = jo.getStatus(); 
		 String oldStatus ="";
		 String item_code = jo.getFromItemCode();
		 String oldfrm_itemcode = "";
		 String to_itemCode ="";
		 Integer id = 0;
		 BigDecimal oldIssuedWt = new BigDecimal("0.0");
		 BigDecimal newIssuedWt = new BigDecimal("0.0");
		 BigDecimal oldReceivedWt = new BigDecimal("0.0");
		 BigDecimal updatedIssueWt = new BigDecimal("0.0");
		 int oldPcs = 0;
		 
		 BigDecimal total_grossWt = new BigDecimal("0.0");
		 int pcs = 0;
		 
		 List<ItemMaster> itemList = itemmasterDao.searchItemMaster(item_code);
			
			for (int i = 0; i < itemList.size(); i++) {
				
				ItemMaster imast = (ItemMaster)itemList.get(i);
				
				if (imast instanceof ItemMaster) {					
					itemDetails = (ItemMaster) imast;				
					total_grossWt = itemDetails.getTotalGrossWeight();							
				}
			}
			
			id = jo.getJobOrderId();
			
			if(id != null){
				
				JobOrder oldOrderObj = joborderDao.getJobOrder(id);				
				oldIssuedWt = oldOrderObj.getIssuedGrossWeight();
				newIssuedWt = jo.getIssuedGrossWeight();		
				oldReceivedWt = oldOrderObj.getFinisheditemGrossWt();
				oldfrm_itemcode = oldOrderObj.getFromItemCode();
				to_itemCode = oldOrderObj.getToItemCode();
				updatedIssueWt = total_grossWt.subtract(newIssuedWt.subtract(oldIssuedWt));	
				oldStatus = oldOrderObj.getStatus();
				oldPcs = oldOrderObj.getNumberOfPieces();
			}
			 
			
			if(orderStatus.equals("Issued"))
			{
				if(id == null || id == 0){
					if(jo.getIssuedGrossWeight().compareTo(total_grossWt) == 1){
						errors.rejectValue("issuedGrossWeight", "ItemIssueWeight.required");
					}
				}
				else {
					if(!item_code.equals(oldfrm_itemcode) && jo.getIssuedGrossWeight().compareTo(total_grossWt) == 1 || updatedIssueWt.signum() == -1){											
						errors.rejectValue("issuedGrossWeight", "ItemIssueWeight.required");						
					}
				}
				
				if(oldReceivedWt.signum() == 0 && jo.getFinisheditemGrossWt().signum() != 0 || oldPcs == 0 && jo.getNumberOfPieces() != 0){
					errors.rejectValue("status", "status.change");
				}
			}
			
			if(orderStatus.equals("Issued") && oldStatus.equals("Received") || orderStatus.equals("Received")) {				
				 List<ItemMaster> recList = itemmasterDao.searchItemMaster(to_itemCode);
					
					for (int i = 0; i < recList.size(); i++) {						
						ItemMaster imast = (ItemMaster)recList.get(i);						
						if (imast instanceof ItemMaster) {					
							itemDetails = (ItemMaster) imast;				
							total_grossWt = itemDetails.getTotalGrossWeight();		
							pcs = itemDetails.getQty();
						}
					}
			
					if(oldReceivedWt.compareTo(total_grossWt) == 1 || oldPcs > pcs ){
						errors.rejectValue("finisheditemGrossWt", "ItemIssueWeight.required");	
					}			
			}			
		 
			if(jo.getIssuedGrossWeight().signum() == 0){
				errors.rejectValue("issuedGrossWeight", "OrderGrossWeight.required");
			}			
			
			if(jo.getOrderNo() == null){
				//errors.rejectValue("orderNo", "orderNo.required");
			}
			
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "orderNo", "orderNo.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "smithName", "smithName.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "orderDate", "jobOrderDate.required");		
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bullionType", "OrderType.required");					
			
			if(orderStatus.equals("Received"))
			{ 
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName", "ItemCategory.required");
				
				if(jo.getFinisheditemGrossWt().signum() == 0){
					errors.rejectValue("finisheditemGrossWt", "finishedGrossWeight.required");
				}
				
				if(jo.getNumberOfPieces() == 0){
					errors.rejectValue("numberOfPieces", "NumberOfPieces.required");
				}	
				
				if(oldIssuedWt.compareTo(newIssuedWt) != 0 && oldStatus.equals("Received")){
					errors.rejectValue("issuedGrossWeight", "issuewt.Notchange");
				}
			}			
	}	
}
