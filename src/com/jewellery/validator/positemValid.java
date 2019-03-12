package com.jewellery.validator;
import java.util.List;

import com.jewellery.entity.POSItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.jewellery.dao.PostagItemDao;
import com.jewellery.entity.PostagItem;
@Component("posItem")
public class positemValid {
	
	@Autowired
	private PostagItemDao postagDao;
	public boolean supports(Class<?> clazz){
		return POSItem.class.isAssignableFrom(clazz);
		
	}
	
	public void validate(Object command, Errors errors){
		POSItem positem=(POSItem)command;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName", "itemName.isVaid");	
		
	
			if(positem.getOpeningStockSet()!=0&&positem.getPiecesPerQty()==0)
			{
				errors.rejectValue("piecesPerQty","piecesPerQty.required");
			}	
		if(positem.getMRP()==null||positem.getMRP().signum()==0)
		{
			errors.rejectValue("MRP","MRP.required");
		}
		if(positem.getCostRate()==null||positem.getCostRate().signum()==0)
		{
			errors.rejectValue("costRate","costRate.required");
		}
		if(positem.getMargin()==null||positem.getMargin().signum()==0)
		{
			errors.rejectValue("margin", "margin.required");
		}
		if(positem.getStockDate()==null)
		{
			errors.rejectValue("stockDate","stockDate.isEmpty");
		}
		if(positem.getPrintName()=="")
		{
			errors.rejectValue("printName","printName.notEmpty");
		}
		if(positem.getMRP().signum()==-1)
		{
			errors.rejectValue("MRP", "POS.Item.MRP.required", "MRP should not be negative!");
		}
		
	}
	public void updateValidate(Object command, Errors errors)
	{
		POSItem positem=(POSItem)command;
		int itemid =positem.getItemId();
		Integer newQTYSET=positem.getOpeningStockSet();
		int soldQuantity = 0;
		String referenceId=new Integer(itemid).toString();
		if(itemid!=0)
		{
		List<PostagItem> listSOLDTagitem = postagDao.searchsoldStatus(referenceId);
		for(@SuppressWarnings("unused") PostagItem postag:listSOLDTagitem)	
		{
			soldQuantity++;
		}
			
		if(newQTYSET<soldQuantity)
		{
			errors.rejectValue("OpeningStockSet","OpeningStockSet.can'tmodify");
		}
		}	
	}
}
