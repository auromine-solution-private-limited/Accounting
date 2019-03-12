package com.jewellery.validator;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;


import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.LotStockDao;

import com.jewellery.entity.ItemMaster;

@Component("ItemMaster")
public class ItemMasterValidator {
	
	@Autowired 
	private ItemMasterDao itemDao;
	@Autowired	
	private LotStockDao lotstockDao; 
	@Autowired(required=false)
	private ItemMasterDao itemmasterDao;
	BigDecimal lotgwt= null;
	BigDecimal lotQty= null;
	public boolean supports(Class<?> clazz){
		return ItemMaster.class.isAssignableFrom(clazz);		 
	}
	
	public void validate(Object command, Errors errors){
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName", "itemName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "qty", "qty.required");
		ItemMaster item=(ItemMaster)command;
		Integer itemId = 0;
		itemId = item.getItemId();
		BigDecimal gWt = new BigDecimal("0.000");
		int quantity  = 0;
			
		if(item.getGrossWeight() == null ||item.getGrossWeight().signum()==0)
		{
			errors.rejectValue("grossWeight","grossWeight.required");
		}
		
		if(item.getStockType() == ""){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stockType", "stockType.required");
		}
		
		if(item.getPiecesPerQty()==0)
		{ 
			errors.rejectValue("piecesPerQty","piecesPerQty.required");
		}
		
		String metalType=item.getMetalUsed();
		gWt=item.getGrossWeight();		
		quantity = item.getQty();
		
		if(itemId != null){
			ItemMaster oldItemObj = itemDao.getItemMaster(item.getItemId());
			BigDecimal oldWt = oldItemObj.getGrossWeight();
			BigDecimal newWt = item.getGrossWeight();
			gWt = newWt.subtract(oldWt);			
		}
		if(item.getStockType().equals("Gold Ornaments") || item.getStockType().equals("Silver Ornaments") ){
		
		BigDecimal purchaseGwt=itemDao.purchasedStock(metalType);
		Integer qty=itemDao.purchasedStockuntag(metalType);
		
		if(item.getStockType().equals("PurchasedStock"))
		{			
			if(purchaseGwt.compareTo(gWt) == -1)
			{				
				errors.rejectValue("grossWeight","grossWeight.Insufficientgwt");
			}
			
			if(itemId == null){
				if(qty < quantity)
				{
					errors.rejectValue("qty","Qty.Insufficientgwt");
				}
			}
		}
		} 
		
		
		/*************Gold lot tagging validation for gwt and qty***************/
		if(item.getStockType().equals("GoldLotStock"))
		{
			List<Object[]> goldlotlist = null;
			goldlotlist = lotstockDao.sumofGoldLotStockGwt();
			
				for (Object o[] : goldlotlist) {
					 lotgwt = (BigDecimal) o[0];
					 lotQty=(BigDecimal) o[1];					
				}
				
				 
				if(lotgwt == null || lotQty == null){
					errors.rejectValue("grossWeight","grossWeight.LotStockStockRequired","Please Create Gold Lot Stock detail first");
				}else if(lotgwt.compareTo(gWt) == -1 || lotQty.intValueExact()<item.getQty())
					{
						errors.rejectValue("grossWeight","grossWeight.InsufficientgwtgoldLot");
					}
				
			}	
		
		
		/*************Silver lot tagging validation for gwt and qty***************/
		if(item.getStockType().equals("SilverLotStock"))
		{
			List<Object[]> silverlotlist=lotstockDao.sumofSilverLotStockGwt();
						
			 for (Object o[] : silverlotlist) {
				 lotgwt = (BigDecimal) o[0];
				 lotQty=(BigDecimal) o[1];					
			}
			 
			 if(lotgwt == null || lotQty == null){
					errors.rejectValue("grossWeight","grossWeight.LotStockStockRequired","Please Create Lot Silver Stock detail first");
				}
			 	else if(lotgwt.compareTo(gWt)==-1 || lotQty.intValueExact()<item.getQty())
				{
					errors.rejectValue("grossWeight","grossWeight.InsufficientgwtgoldLot");
				}
			 }			
	}
	
	public void updatevalidate(Object command2, Errors errors){
		ItemMaster item=(ItemMaster)command2;
		/***** Validation for Category Loose Stock ****/
		if( item.getStockType().equalsIgnoreCase("OpeningStock") && item.getMetalUsed().equals("Gold Loose Stock") )
		{
			System.out.println("inside Validatior of Category loose stock");
			ItemMaster itemObj = itemmasterDao.getItemMaster(item.getItemId());

			BigDecimal oldOpGrosweight=itemObj.getOp_GrossWeight();
			BigDecimal newOpGrossweight=item.getOp_GrossWeight();
			BigDecimal diffGrossweight=newOpGrossweight.subtract(oldOpGrosweight);
			
			int oldOp_Quantity=itemObj.getOp_Quantity();
			int newOp_Quantity=item.getOp_Quantity();
			int diffQty=newOp_Quantity-oldOp_Quantity;
			
			if (diffGrossweight.compareTo(itemObj.getGrossWeight()) == 1  || diffQty > itemObj.getQty()) 
			{
				System.out.println("inside Greater than condition");
				errors.rejectValue("op_GrossWeight","op_GrossWeightlooseCat.required","Closing Stock Is Not Sufficient To Update!");
			}
		}
	
	
	
	
}
}
