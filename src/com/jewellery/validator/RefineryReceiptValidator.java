package com.jewellery.validator;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.RefineryReceiptDao;
import com.jewellery.entity.ItemMaster;
import com.jewellery.entity.RefineryReceipt;

@Component("RefineryReceiptValidator")
public class RefineryReceiptValidator implements Validator{
	@Autowired	
	private ItemMasterDao itemmasterDao;
	@Autowired
	private RefineryReceiptDao refineryReceiptDao;
	private ItemMaster itemDetails = new ItemMaster();//Instance of itemmaster class
	
	public boolean supports(Class<?> clazz){
		return RefineryReceipt.class.isAssignableFrom(clazz);		
	}	
	public void validate(Object command, Errors errors){
		 BigDecimal ZERO = new BigDecimal("0.000");
		 	//BigDecimal	total_grossWt = new BigDecimal("0.000");
		 	//Integer pieces=0;
		RefineryReceipt obj=(RefineryReceipt)command;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rrDate", "rrDate.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rrName", "rrName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rrOrnamentsType", "rrOrnamentsType.required");
		
	 if(obj.getRrpieces()== null || obj.getRrpieces()==0){
		 errors.rejectValue("rrpieces", "rrpieces.required");	
	 }
	 
	 if(obj.getRrGrossWeight()==null || obj.getRrGrossWeight().compareTo(ZERO)==0){
		 errors.rejectValue("rrGrossWeight", "rrGrossWeight.required");	
	 }
	 
	 if(obj.getRrNetWeight()==null || obj.getRrNetWeight().compareTo(ZERO)==0){
		 errors.rejectValue("rrNetWeight", "rrNetWeight.required");	
	 }
	 
	/* String item_code = obj.getRritemcode();
		List<ItemMaster> itemList = itemmasterDao.searchItemMaster(item_code);
		for (int i = 0; i < itemList.size(); i++) {
			
			ItemMaster imast = (ItemMaster)itemList.get(i);
			
			if (imast instanceof ItemMaster) {					
				itemDetails = (ItemMaster) imast;				
				total_grossWt = itemDetails.getGrossWeight();	
			 pieces=itemDetails.getQty();
			}
		}
		
		
		
	if(obj.getRrGrossWeight().compareTo(total_grossWt) == 1 || obj.getRrpieces() > pieces){
			 errors.rejectValue("rrpieces", "pieces.requ", "Closing Stock is not sufficient to save");
		}*/
	 
}
	public void validateUpdate(Object command, Errors errors){
		RefineryReceipt obj=(RefineryReceipt)command;
		List<RefineryReceipt> oldOrderObj = refineryReceiptDao.getrefineryReceiptId(obj.getRrId());		
	 	BigDecimal ZERO = new BigDecimal("0.000");
	 	BigDecimal	total_grossWt = new BigDecimal("0.000");
	 	Integer pieces=0;
	 	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rrDate", "rrDate.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rrName", "rrName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rrOrnamentsType", "rrOrnamentsType.required");
		
		 if(obj.getRrpieces()== null || obj.getRrpieces()==0){
			 errors.rejectValue("rrpieces", "rrpieces.required");	
		 }
		 
		 if(obj.getRrGrossWeight()==null || obj.getRrGrossWeight().compareTo(ZERO)==0){
			 errors.rejectValue("rrGrossWeight", "rrGrossWeight.required");	
		 }
		 
		 if(obj.getRrNetWeight()==null || obj.getRrNetWeight().compareTo(ZERO)==0){
			 errors.rejectValue("rrNetWeight", "rrNetWeight.required");	
		 }
		String item_code = obj.getRritemcode();
		List<ItemMaster> itemList = itemmasterDao.searchItemMaster(item_code);
		for (int i = 0; i < itemList.size(); i++) {
			
			ItemMaster imast = (ItemMaster)itemList.get(i);
			
			if (imast instanceof ItemMaster) {					
				itemDetails = (ItemMaster) imast;				
				total_grossWt = itemDetails.getGrossWeight();	
			 pieces=itemDetails.getQty();
			}
		}
		
		//finding the difference grwt and pieces for update validaiton of closing stock
		//grosswgt difference calculation
		BigDecimal oldgrswt=oldOrderObj.get(0).getRrGrossWeight();
    	BigDecimal newgrswt=obj.getRrGrossWeight();
    	BigDecimal diffGrswt=newgrswt.subtract(oldgrswt);
    	
    	//pieces difference calculation 
    	Integer oldpieces=oldOrderObj.get(0).getRrpieces();
    	Integer newppieces=obj.getRrpieces();
    	Integer finalpieces=newppieces-oldpieces;
    	if(diffGrswt.compareTo(total_grossWt) == 1 || finalpieces > pieces){
			 errors.rejectValue("rrpieces", "pieces.requ", "Closing Stock is not sufficient to save");
		}
	}
}
