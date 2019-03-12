package com.jewellery.validator;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.RefineryDao;
import com.jewellery.entity.ItemMaster;
import com.jewellery.entity.RefineryIssue;

@Component("RefineryValidator")
public class RefineryValidator implements Validator{
	@Autowired	
	private ItemMasterDao itemmasterDao;
	@Autowired
	private RefineryDao refineryDao;
	private ItemMaster itemDetails = new ItemMaster();//Instance of itemmaster class
	
	public boolean supports(Class<?> clazz){
		return RefineryIssue.class.isAssignableFrom(clazz);		
	}	
	
	public void validate(Object command, Errors errors){
			RefineryIssue obj=(RefineryIssue)command;
		 	BigDecimal ZERO = new BigDecimal("0.000");
		 	BigDecimal	total_grossWt = new BigDecimal("0.000");
		 	Integer pieces=0;
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "refineryDate", "RefineryDate.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "refinerySupplierName", "refinerySupplierName.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ornamentsType", "ornamentsType.required");
				
			if(obj.getPieces()== null || obj.getPieces()==0){
			 errors.rejectValue("pieces", "pieces.required");	
		 }
				if(obj.getGrossWeight()==null || obj.getGrossWeight().compareTo(ZERO)==0){
			 errors.rejectValue("grossWeight", "grossWeight.required");	
		 }
				if(obj.getNetWeight()==null || obj.getNetWeight().compareTo(ZERO)==0){
			 errors.rejectValue("netWeight", "netWeight.required");	
		 }
				
				String item_code = obj.getItemCode();
				List<ItemMaster> itemList = itemmasterDao.searchItemMaster(item_code);
				for (int i = 0; i < itemList.size(); i++) {
					
					ItemMaster imast = (ItemMaster)itemList.get(i);
					
					if (imast instanceof ItemMaster) {					
						itemDetails = (ItemMaster) imast;				
						total_grossWt = itemDetails.getGrossWeight();	
					 pieces=itemDetails.getQty();
					}
				}
				if(obj.getGrossWeight().compareTo(total_grossWt) == 1 || obj.getPieces() > pieces){
					
					 errors.rejectValue("pieces", "pieces.requ", "Closing Stock is not sufficient to save");
				}
	}
	public void validateUpdate(Object command, Errors errors){
		RefineryIssue obj=(RefineryIssue)command;
		 List<RefineryIssue> oldOrderObj = refineryDao.getrefineryId(obj.getRefineryId());
	 	BigDecimal ZERO = new BigDecimal("0.000");
	 	BigDecimal	total_grossWt = new BigDecimal("0.000");
	 	Integer pieces=0;
	 	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "refineryDate", "RefineryDate.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "refinerySupplierName", "refinerySupplierName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ornamentsType", "ornamentsType.required");
			
		if(obj.getPieces()== null || obj.getPieces()==0){
		 errors.rejectValue("pieces", "pieces.required");	
	 }
			if(obj.getGrossWeight()==null || obj.getGrossWeight().compareTo(ZERO)==0){
		 errors.rejectValue("grossWeight", "grossWeight.required");	
	 }
			if(obj.getNetWeight()==null || obj.getNetWeight().compareTo(ZERO)==0){
		 errors.rejectValue("netWeight", "netWeight.required");	
	 }
		String item_code = obj.getItemCode();
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
		BigDecimal oldgrswt=oldOrderObj.get(0).getGrossWeight();
    	BigDecimal newgrswt=obj.getGrossWeight();
    	BigDecimal diffGrswt=newgrswt.subtract(oldgrswt);
    	
    	//pieces difference calculation 
    	Integer oldpieces=oldOrderObj.get(0).getPieces();
    	Integer newppieces=obj.getPieces();
    	Integer finalpieces=newppieces-oldpieces;
    	if(diffGrswt.compareTo(total_grossWt) == 1 || finalpieces > pieces){
			 errors.rejectValue("pieces", "pieces.requ", "Closing Stock is not sufficient to save");
		}
	}
	
}
