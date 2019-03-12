package com.jewellery.validator;
import java.util.List;
import org.springframework.validation.Errors;
import com.jewellery.entity.POSPurchase;
import com.jewellery.entity.POSPurchaseItem;
import org.springframework.stereotype.Component;
@Component("POSPurchasereturn")
public class POSPurchasereturn {
	
	/************ Validate method for POSPurchase return Update ************/
	public void validateUpdate(Object command1, Object command2,List<POSPurchaseItem> posOldItemsList, List<POSPurchaseItem> posNewItemsList, Errors errors){
		POSPurchase posOld = (POSPurchase)command1;
		if(posOld.getSupplierName().isEmpty() || posOld.getSupplierName().length() == 0){
			errors.rejectValue("posp", "POP.supplierName.empty", "Supplier Name Can't be Empty");
		}
		if(!posNewItemsList.isEmpty() && !posOldItemsList.isEmpty()){
			for (int i = 0; i < posOldItemsList.size(); i++) {
				  for (int j = i+1; j < posNewItemsList.size(); j++) {
					  // compare list.get(i) and list.get(j)
					  if(posOldItemsList.get(i).isDeleted == false && posNewItemsList.get(j).isDeleted == true){
						  
					  }
				  }
			 }
		}
		
	}/*******End update validation*************/
	public void validateInsert(Object command,Errors errors){
		
	}// end of validateInsert
	@SuppressWarnings("unchecked")
	public void validate(Object command3, Object command4, Errors errors) {
		
		POSPurchase purchaseNew2 = (POSPurchase) command3;
		List<POSPurchaseItem> popPurchItemList = (List<POSPurchaseItem>) command4;
		
			
		/** POP empty Supplier Name */
		if(purchaseNew2.getSupplierName().isEmpty() || purchaseNew2.getSupplierName().length() == 0){
			errors.rejectValue("posp.supplierName", "POP.supplierName.empty", "Supplier Name Can't be Empty");
		}/** Invalid Supplier Name */
		
		/** POP Purchase Date */
		if(purchaseNew2.getPdate() == null){
			errors.rejectValue("posp.pdate", "POP.date.required", "Purchase Date field can't be Empty");
		}
		
				
		/** Dynamic Row Validations */
		if(!popPurchItemList.isEmpty()){
			int RowCount = 0;
			for(POSPurchaseItem pospItem : popPurchItemList) {	
				
				 if( pospItem.isDeleted == false && (pospItem.getPurchaseAmt().signum() < 1) ){
					 
					errors.rejectValue("posp", "POP.amt.zero", "Amount can't be Zero on Row id "+RowCount+".");
				}else if(RowCount == 1){
					
				}
				
			RowCount++; // Counter for Dynamic Row
			}
		}
		
		/** Grand Total Can't be Zero */
		if(purchaseNew2.getGrandAmount().signum() < 1){
			errors.rejectValue("posp.grandAmount", "POP.grandAmount.required", "Grand Total Amount Can't be Zero !");
		}
			
	}
}// end of validate
	

