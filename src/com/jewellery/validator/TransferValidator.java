package com.jewellery.validator;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.jewellery.dao.ItemMasterDao;
import com.jewellery.entity.ItemMaster;
import com.jewellery.entity.Transfer;

@Component("transferValidator")
public class TransferValidator  implements Validator{

	@Autowired
	private ItemMasterDao itemmasterDao;
	private ItemMaster itemDetails;
	
	
	public boolean supports(Class<?> clazz){
		return Transfer.class.isAssignableFrom(clazz);
	}
	// add for bug number 2057
	public void validate2(Object command,Errors errors){
		Transfer transferObj =  (Transfer)command;
		String Toitemcode=transferObj.getToItemNo();
		if(transferObj.getToItemNo()==""){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "toItemNo", "message.ToitemCode.filled");
			List<ItemMaster> itemcode2=itemmasterDao.getItemsByCode(Toitemcode);
			if(itemcode2==null || itemcode2.isEmpty()){
				errors.rejectValue("toItemNo", "Invalid.check");
			}
		}
	}
	public void validate(Object command, Errors errors){ 
		
		Transfer transferObj =  (Transfer)command;

		if(transferObj.getFromItemNo() =="" ){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fromItemNo", "message.FromitemCode.filled");
		}
		
		String Fromitemcode=transferObj.getFromItemNo();
		
		
	
		
		// Invalid Itemcode validation for tranfer
				List<ItemMaster> itemcode1=itemmasterDao.getItemsByCode(Fromitemcode);
				
				
				if(itemcode1==null || itemcode1.isEmpty() ){
					errors.rejectValue("fromItemNo", "Invalid.check");
				}

		BigDecimal from_grosswt = new BigDecimal("0.0");
		//int nop;
		String item_code = transferObj.getFromItemNo();
		//System.out.println(item_code);
		List<ItemMaster> itemList =itemmasterDao.getItemsByCode(item_code);
		for (int i = 0; i < itemList.size(); i++) {
			
			ItemMaster imast = (ItemMaster)itemList.get(i);
			
			if (imast instanceof ItemMaster) {
			itemDetails = (ItemMaster) imast;		
			from_grosswt = itemDetails.getTotalGrossWeight();			
			//nop = itemDetails.getQty();
			
			}
		}
System.out.println(from_grosswt);
System.out.println(transferObj.getFromTotalGrossWeight());
		if(transferObj.getFromTotalGrossWeight().compareTo(from_grosswt) == 1){
			errors.rejectValue("fromTotalGrossWeight", "fromTotalGrossWeight.check","FromGrossweight is Greater than Actual value");
		}
		

	}
}
