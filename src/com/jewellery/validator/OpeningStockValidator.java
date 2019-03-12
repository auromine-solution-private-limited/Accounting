package com.jewellery.validator;

import java.math.BigDecimal;
import com.jewellery.dao.ItemMasterDao;
import com.jewellery.entity.ItemMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("OpeningStockValidator")
public class OpeningStockValidator {
	 
	@Autowired 
	private ItemMasterDao itemDao;
	
	public boolean supports(Class<?> clazz){
		return ItemMaster.class.isAssignableFrom(clazz);		
	}
	
	public void validateOpeningStock(Object command, Errors errors){
				
		ItemMaster item=(ItemMaster)command;
		Integer id = 0; 
		
		
		if(item.getOp_GrossWeight() == null ||item.getOp_GrossWeight().signum() == 0)
		{
			errors.rejectValue("op_GrossWeight","op_GrossWeight.required");
		}		
		
		id = item.getItemId();
		if(id != null){
			ItemMaster openingStockObj = itemDao.getItemMaster(id);
			 
			BigDecimal cl_gwt = openingStockObj.getGrossWeight();
			int clqty = openingStockObj.getQty();
			
				
			if(item.getOp_GrossWeight().compareTo(openingStockObj.getOp_GrossWeight()) == -1 || item.getOp_Quantity() < openingStockObj.getOp_Quantity())
			{
				BigDecimal diffWt = openingStockObj.getOp_GrossWeight().subtract(item.getOp_GrossWeight());
				int diffPcs = openingStockObj.getOp_Quantity() - item.getOp_Quantity();
							
				if(diffWt.compareTo(cl_gwt) == 1 || diffPcs > clqty){
					errors.rejectValue("op_GrossWeight","grossWeight.negativestockWeight");
				}
			}
		}
	}
}
