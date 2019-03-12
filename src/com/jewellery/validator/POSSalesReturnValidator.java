package com.jewellery.validator;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import com.jewellery.dao.PostagItemDao;
import com.jewellery.entity.POSSales;
import com.jewellery.entity.POSSalesItem;


@Component("POSSalesReturnValidator")
public class POSSalesReturnValidator {  
	@Autowired
	PostagItemDao postagitemdao;
		
	@SuppressWarnings("unchecked")
	public void validateSalesReturn(Object command,Object command2,Errors errors){
		POSSales pos = (POSSales)command;
		List<POSSalesItem> popSalesItemList = (List<POSSalesItem>) command2;
		
		//BigDecimal ZERO = new BigDecimal("0.00");
		
		
		if(pos.getSalesdate() == null){
			errors.rejectValue("possales", "SalesDate.required", "Date is Required!!!");
		}
	
		if(!popSalesItemList.isEmpty()){
			//int RowCount = 0; 
			for(POSSalesItem pospSalesItem : popSalesItemList) {
				
				if(pospSalesItem.getSalesRate() == null || pospSalesItem.getSalesRate().signum() == 0 ){
					errors.rejectValue("possales", "SalesRate.required", "Rate is Required!!!");
				}
				
				if(pospSalesItem.getSalesAmount() == null || pospSalesItem.getSalesAmount().signum() == 0 ){
					errors.rejectValue("possales", "SalesAmount.required", "Amount Cannot be Zero!!!");
				}
				
				//RowCount++;
			}
		}
		
	 
		}
		
	}
	
