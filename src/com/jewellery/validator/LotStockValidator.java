package com.jewellery.validator;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jewellery.dao.LotStockDao;
import com.jewellery.entity.LotStock;

@Component("LotStockValidator")
public class LotStockValidator implements Validator{
	
	@Autowired
	LotStockDao lotStockDao;
	
	private final BigDecimal CONVERT = new BigDecimal("-1"); 

	public boolean supports(Class<?> clazz){
		return LotStock.class.isAssignableFrom(clazz);
	}
	
	//Insert and Update Mode Common Validations
	public void validate(Object command, Errors errors){
		
		LotStock lotStock=(LotStock)command;
	
		//grossWeight
		if(lotStock.getGrossWeight() == null || lotStock.getGrossWeight().signum() <= 0){
			errors.rejectValue("grossWeight", "grossWeight.Required", " * Gross Weight cannot be ZERO.");
		}
		
		//netWeight
		if(lotStock.getNetWeight() == null || lotStock.getNetWeight().signum() <= 0){
			errors.rejectValue("netWeight", "netWeight.Required", " * Net Weight cannot be ZERO.");
		}
		
		//quantity
		if(lotStock.getQuantity() <= 0){
			errors.rejectValue("quantity", "quantity.Required", " * Quantity cannot be ZERO.");
		}
		
		// Date 
		if(lotStock.getLotDate() == null || lotStock.getLotDate().equals("")){
			errors.rejectValue("lotDate", "lotDate.Required", " * Date Required.");
		}
		
	}
	
	// Insert Mode Validation
	public void ValidateInsert(Object command, Errors errors){
		
		LotStock lotStock=(LotStock)command;
		
		// Item Name and totalGrossWeight && totalQty Negative Validation
		if(lotStock.getLotItemName() == null || lotStock.getLotItemName().equals("")){
			errors.rejectValue("lotItemName", "lotItemName.Required", " * Item Name field cannot be Empty.");
		}
		else if(lotStock.getLotItemName().equalsIgnoreCase("GoldLotStock")){
			
			List<Object[]> goldLotList = lotStockDao.sumofGoldLotStockGwt();
			BigDecimal totalGoldGrossWt = new BigDecimal("0.000");
			BigDecimal totalGoldQty = new BigDecimal("0.00");
			BigDecimal GoldQty = new BigDecimal(lotStock.getQuantity());
			
			for (Object goldVal[] : goldLotList) {
				totalGoldGrossWt = (BigDecimal) goldVal[0];
				totalGoldQty = (BigDecimal) goldVal[1];		
			 }
			
			
			// Negative Stock Validation for Return Lot Stock
			
				if(totalGoldGrossWt.compareTo(lotStock.getGrossWeight()) == -1){
					errors.rejectValue("grossWeight","grossWeight.Invalid","Grossweight Can't be greater than 'Total Gold Grossweight' !");
				}
				if(totalGoldQty.compareTo(GoldQty) == -1){
					errors.rejectValue("quantity","quantity.Invalid","Quantity Can't be greater than 'Total Gold Quantity' !");
				}
			
			
			
		//Validation For Silver Lot Stock	
		}else if(lotStock.getLotItemName().equalsIgnoreCase("SilverLotStock")){
			
			List<Object[]> silverLotList = lotStockDao.sumofSilverLotStockGwt();
			BigDecimal totalSilvetGrossWt = new BigDecimal("0.000");
			BigDecimal totalSilverQty = new BigDecimal("0.00");
			BigDecimal SilverQty = new BigDecimal(lotStock.getQuantity());
			
			 for (Object silverVal[] : silverLotList) {
					totalSilvetGrossWt = (BigDecimal) silverVal[0];
					totalSilverQty=(BigDecimal) silverVal[1];
			 }			 
	
			 if(totalSilvetGrossWt.compareTo(lotStock.getGrossWeight()) == -1){
				 errors.rejectValue("grossWeight","grossWeight.Invalid","Grossweight Can't be greater than 'Total Silver Grossweight' !");
			 }
			 if(totalSilverQty.compareTo(SilverQty) == -1){
				errors.rejectValue("quantity","quantity.Invalid","Quantity Can't be greater than 'Total Silver Quantity' !");
			 }
		}	
	}
	
	//Update Mode Validation
	public void ValidateUpdate(Object command, Errors errors){
		
		LotStock lotStock=(LotStock)command;
		
		LotStock oldLotStock = lotStockDao.getLotStock(lotStock.getLotId());
			
		// Item Name and totalGrossWeight && totalQty Negative Validation
		if(lotStock.getLotItemName() == null || lotStock.getLotItemName().equals("")){
			errors.rejectValue("lotItemName", "lotItemName.Required", " * Item Name field cannot be Empty.");
		}
		else if(lotStock.getLotItemName().equalsIgnoreCase("GoldLotStock")){
						
			//Old Gold Values
			BigDecimal oldGoldGrossWt = CONVERT.multiply(oldLotStock.getGrossWeight());
			int oldGoldQty = oldLotStock.getQuantity() * -1;
						
			// Total Gross weight Values
			BigDecimal totalGoldGrossWt = new BigDecimal("0.000");
			BigDecimal totalGoldQty = new BigDecimal("0.00");
			List<Object[]> goldLotList = lotStockDao.sumofGoldLotStockGwt();
			for (Object goldVal[] : goldLotList) {
				totalGoldGrossWt = (BigDecimal) goldVal[0];
				totalGoldQty = (BigDecimal) goldVal[1];		
			}
			
			
			if(lotStock.getLotType().equalsIgnoreCase("Return Lot Stock")){
				//Difference Values
				BigDecimal diffGoldGross = lotStock.getGrossWeight().subtract(oldGoldGrossWt);
				int diffGoldQty	= lotStock.getQuantity() - oldGoldQty; 
						
				if(diffGoldGross.signum() != -1){
					if(totalGoldGrossWt.compareTo(diffGoldGross) == -1){
						errors.rejectValue("grossWeight","grossWeight.Invalid","Grossweight Can't be greater than 'Total Gold Grossweight' !");
					}			
				}
				if(diffGoldQty > 0 ){
					if(totalGoldQty.compareTo(new BigDecimal(diffGoldQty)) == -1){
						errors.rejectValue("quantity","quantity.Invalid","Quantity Can't be greater than 'Total Gold Quantity' !");
					}
				}
			}
			
			// Negative Stock Validation for Add Lot Stock
			if(lotStock.getLotType().equalsIgnoreCase("Add Lot Stock")){
							
				//Difference Values
				BigDecimal difGoldGross = oldLotStock.getGrossWeight().subtract(lotStock.getGrossWeight());
				int difGoldQty	= oldLotStock.getQuantity() - lotStock.getQuantity(); 
											
				if(difGoldGross.signum() != -1){
					if(totalGoldGrossWt.compareTo(difGoldGross) == -1){
						errors.rejectValue("grossWeight","grossWeight.Invalid","Grossweight Can't be greater than 'Total Gold Grossweight' !");
					}			
				}
				if(difGoldQty > 0 ){
					if(totalGoldQty.compareTo(new BigDecimal(difGoldQty)) == -1){
						errors.rejectValue("quantity","quantity.Invalid","Quantity Can't be greater than 'Total Gold Quantity' !");
					}
				}
			}		
					
		}else if(lotStock.getLotItemName().equalsIgnoreCase("SilverLotStock")){
			
			//Old Silver Values
			BigDecimal oldSilverGrossWt = CONVERT.multiply(oldLotStock.getGrossWeight());
			int oldSilverQty = oldLotStock.getQuantity() * -1;
			
			// Total Gross weight Values 
			BigDecimal totalSilvetGrossWt = new BigDecimal("0.000");
			BigDecimal totalSilverQty = new BigDecimal("0.00");
			List<Object[]> silverLotList = lotStockDao.sumofSilverLotStockGwt();
			 for (Object silverVal[] : silverLotList) {
					totalSilvetGrossWt = (BigDecimal) silverVal[0];
					totalSilverQty=(BigDecimal) silverVal[1];
			 }
			 
			 
			// Negative Stock Validation for Return Lot Stock
			if(lotStock.getLotType().equalsIgnoreCase("Return Lot Stock")){ 
			 
				BigDecimal diffSilverGross = lotStock.getGrossWeight().subtract(oldSilverGrossWt);
				int diffSilverQty	= lotStock.getQuantity() - oldSilverQty; 
				
				if(diffSilverGross.signum() != -1){
					if(totalSilvetGrossWt.compareTo(diffSilverGross) == -1 ){
						 errors.rejectValue("grossWeight","grossWeight.Invalid","Grossweight Can't be greater than 'Total Silver Grossweight' !");
					}
				}
				if(diffSilverQty > 0 ){
					if(totalSilverQty.compareTo(new BigDecimal(diffSilverQty)) == -1 ){
						errors.rejectValue("quantity","quantity.Invalid","Quantity Can't be greater than 'Total Silver Quantity' !");
					}
				}
			}
			
			// Negative Stock Validation for Add Lot Stock
			if(lotStock.getLotType().equalsIgnoreCase("Add Lot Stock")){
										
				//Difference Values
				BigDecimal diffGrossWt = oldLotStock.getGrossWeight().subtract(lotStock.getGrossWeight());
				int diffQty	= oldLotStock.getQuantity() - lotStock.getQuantity(); 
											
				if(diffGrossWt.signum() != -1){
					if(totalSilvetGrossWt.compareTo(diffGrossWt) == -1){
						errors.rejectValue("grossWeight","grossWeight.Invalid","Grossweight Can't be greater than 'Total Gold Grossweight' !");
					}			
				}
				if(diffQty > 0 ){
					if(totalSilverQty.compareTo(new BigDecimal(diffQty)) == -1){
						errors.rejectValue("quantity","quantity.Invalid","Quantity Can't be greater than 'Total Gold Quantity' !");
					}
				}
			}			
		}
	}


}// end of class
