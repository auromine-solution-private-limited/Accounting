package com.jewellery.validator;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.jewellery.entity.ItemMaster; 
import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.PurchaseDao;
import com.jewellery.entity.Purchase;

@Component("PurchaseValidator")
public class PurchaseValidator {
	
	@Autowired
	private ItemMasterDao itemmasterDao;
	
	@Autowired
	private PurchaseDao purchaseDao;
	
	private ItemMaster itemDetails;
	
	BigDecimal total_grossWt = new BigDecimal("0.0");
	int nop = 0;
	
	public boolean supports(Class<?> clazz){
		return Purchase.class.isAssignableFrom(clazz);			
	}
	 // For Update Method
	public void validateUpdate(Object command1, Object command2, Errors errors){
		
		Purchase purchaseOld = (Purchase)command1;
		Purchase purchaseNew = (Purchase)command2;
		
		Integer id = 0;		
		BigDecimal oldWt = new BigDecimal("0.0");
			
		if(purchaseOld.getSupplierName().equals("Walk-in") && !purchaseNew.getSupplierName().equals("Walk-in")){
			errors.rejectValue("supplierName", "walkin.to.regular", "*Can't be Update Supplier Name from Walk-in to Regular Supplier");
		}else if(!purchaseOld.getSupplierName().equals("Walk-in") && purchaseNew.getSupplierName().equals("Walk-in")){
			errors.rejectValue("supplierName", "regular.to.walkin", "*Can't be Update Supplier Name from Regular Supplier to Walkin");
		}
		//*** for bug 1972
		if(!purchaseOld.getPurchaseType().equals(purchaseNew.getPurchaseType())) {
			errors.rejectValue("purchaseType","purchaseType.required");
		}					
		
		//for bug BUG0067
		if(purchaseOld.getExchangePurchase().equalsIgnoreCase("Sold")){
			errors.rejectValue("exchangePurchase", "exchangePurchase.cannot", "Exchange Already Sold, Cannot Update");
		}
		
		id = purchaseNew.getPurchaseId();
		if(id != null){		
					
			Purchase oldObj = purchaseDao.getPurchase(id);
			oldWt = oldObj.getGrossWeight();	
			int oldQty = oldObj.getNumberOfPieces();
			
			List<ItemMaster> itemList = itemmasterDao.searchItemMaster(oldObj.getItemCode());
			
			for (int i = 0; i < itemList.size(); i++) {
				
				ItemMaster imast = (ItemMaster)itemList.get(i);
				
				if (imast instanceof ItemMaster) {
					itemDetails = (ItemMaster) imast;		
					total_grossWt = itemDetails.getTotalGrossWeight();			
					nop = itemDetails.getQty();
				}
			}
			
			if(purchaseNew.getPurchaseType().equals("Purchase")){
				
				if(!purchaseNew.getBullionType().equals(oldObj.getBullionType()))
				{
					if(total_grossWt.compareTo(oldWt) == -1 || nop < oldQty){
						errors.rejectValue("grossWeight", "GrossWeight.negative");
					}
				}				
			}			
		}
	}
	
	public void validate(Object command, Errors errors){
		
		Purchase ps = (Purchase)command;				
		String item_code = ps.getItemCode();		
					
		List<ItemMaster> itemList = itemmasterDao.searchItemMaster(item_code);
		
		for (int i = 0; i < itemList.size(); i++) {
			
			ItemMaster imast = (ItemMaster)itemList.get(i);
			
			if (imast instanceof ItemMaster) {
				itemDetails = (ItemMaster) imast;		
				total_grossWt = itemDetails.getTotalGrossWeight();			
				nop = itemDetails.getQty();
			}
		}		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "purchasebillNO", "purchasebillNO.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bullionType", "bullionType.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "purchaseDate", "purchaseDate.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "supplierName", "supplierName.required");		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "purchaseAmount", "purchaseAmount.required");
		
		if( (ps.getBullionType().equalsIgnoreCase("Gold Ornaments") || ps.getBullionType().equalsIgnoreCase("Silver Ornaments") || ps.getBullionType().equalsIgnoreCase("Old Gold Coin") || ps.getBullionType().equalsIgnoreCase("Old Silver Coin") ) && ps.getSupplierName().equalsIgnoreCase("Walk-in") ){
			errors.rejectValue("supplierName","supplierName.not.Valid","This transaction not allowed for Walk-In Customer");
		}
		
		//Validation For Rate
		if(ps.getRate().signum() == 0)
		{
			errors.rejectValue("rate","rate.required","Rate is Required");
		}
			
		if(ps.getGrossWeight().signum() == 0){
			errors.rejectValue("grossWeight","grossWeight.required");
		}		
		
		/*if(ps.getNetWeight().signum() == 0){
			errors.rejectValue("netWeight","netWeight.required");
		}*/
		
		if(!(item_code.equals("IT100002") || item_code.equals("IT100007")))
		{
			if(ps.getNumberOfPieces() == 0 ){
				errors.rejectValue("numberOfPieces","purchase.NOP");
			}
		}
		
		if(ps.getNetWeight().signum() <= 0){
			errors.rejectValue("netWeight", "netWeight.required", "NetWeight Value must be greater than Zero");
		}
				
		//Validation For GrossWeight if return weight is greater than the stock weight
		if(ps.getPurchaseType().equals("Purchase Return")){
			if(ps.getGrossWeight().compareTo(total_grossWt) == 1){
				errors.rejectValue("grossWeight","itemWt.purchaseWt");
			}
		}		
		
		if(ps.getPurchaseType().equals("Purchase")){
			if(ps.getSupplierName().equalsIgnoreCase("Walk-in") && ps.getPaymentMode().equalsIgnoreCase("Credit")){
				errors.rejectValue("supplierName","supplierName.Walkin");
			}
		}
		
		//Validation For Number  of Pieces if the number of pieces in stock is lesser than the return one 
		if(ps.getPurchaseType().equals("Purchase Return")){
			if(ps.getNumberOfPieces() > nop){
				errors.rejectValue("numberOfPieces","itemPcs.purchasePcs");
			}
			
			if(ps.getSupplierName().equals("Walk-in")){
				errors.rejectValue("supplierName","supplierName.purchaseReturn");
			}			
		}
		
		if(ps.getNetWeight().signum() == -1){
			errors.rejectValue("netWeight","netWeight.negative");
		}
	}
}
