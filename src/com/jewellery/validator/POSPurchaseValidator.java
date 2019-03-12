package com.jewellery.validator;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.jewellery.dao.LedgerDao;
import com.jewellery.dao.POSCategoryDao;
import com.jewellery.dao.POSItemDao;
import com.jewellery.dao.PostagItemDao;
import com.jewellery.entity.POSCategory;
import com.jewellery.entity.POSItem;
import com.jewellery.entity.POSPurchase;
import com.jewellery.entity.POSPurchaseItem;
import com.jewellery.entity.PostagItem;

@Component("POSPurchaseValidator")
public class POSPurchaseValidator {
	
	@Autowired
	LedgerDao ledgerDao;
	 
	@Autowired
	POSCategoryDao poscategorydao;

	@Autowired
	POSItemDao positemdao;
	
	@Autowired
	private PostagItemDao postagDao;
	
	
	/** Validate method for POSPurchase Update */
	@SuppressWarnings("unused")
	public void validateUpdate(Object command1, Object command2,List<POSPurchaseItem> posOldItemsList, List<POSPurchaseItem> posNewItemsList, Errors errors){
		
		POSPurchase posOld = (POSPurchase)command1;
		POSPurchase posNew = (POSPurchase)command2;
		
		if(posOld.getSupplierName().isEmpty() || posOld.getSupplierName().length() == 0){
			errors.rejectValue("posp", "POP.supplierName.empty", "Please Select Supplier Name");
		}else if(posOld.getSupplierName().equals("Walk-in") && !posNew.getSupplierName().equals("Walk-in")){
			errors.rejectValue("posp", "POP.supplierName.Walkin.regular", "Can't Update from Walk-in to Regular Supplier Name");
		}else if(!posOld.getSupplierName().equals("Walk-in") && posNew.getSupplierName().equals("Walk-in")){
			errors.rejectValue("posp", "POP.supplierName.regular.Walkin", "Can't Update from Regular to Walk-in Supplier Name");			
		}

		/** For Updated Quantity Can't less than Sold Quantity **/
		int rowNum = 0;
		for (POSPurchaseItem newposobj : posNewItemsList) {
			try{
					if (newposobj.getPurchaseItemID() != null || !newposobj.getPurchaseItemID().equals("")) {		
			
						for (POSPurchaseItem popOld : posOldItemsList) {
					
						if (newposobj.getPurchaseItemID().equals(popOld.getPurchaseItemID())) {
	
							int formQty = newposobj.getQtySet();
							int soldQuantity = 0;
							List<PostagItem> listSOLDTagitem = postagDao.getSoldTagByReference(popOld.getPurchaseItemID().toString());
							for(PostagItem tagItem : listSOLDTagitem){
								soldQuantity++;
							}
							if(formQty < soldQuantity){
								errors.rejectValue("posp", "POP.QTY.DECREASE", " * Items are Already Sold or Purchase Return so you can't Minimize the Quantity on Row Id : "+rowNum);
							}
						}
					}
				}
			}catch(NullPointerException ne){
				
			}
				rowNum++;
		}
						
	}// end of validateUpdate
	
	public void validateInsert(Object command,Errors errors){
				
	}// end of validateInsert
	
	@SuppressWarnings("unchecked")
	public void validate(Object command3, Object command4, Errors errors) {
		
		POSPurchase purchaseNew2 = (POSPurchase) command3;
		List<POSPurchaseItem> popPurchItemList = (List<POSPurchaseItem>) command4;
			
		/** POP empty Supplier Name */
		if(purchaseNew2.getSupplierName().isEmpty() || purchaseNew2.getSupplierName().length() == 0){
			errors.rejectValue("posp.supplierName", "POP.supplierName.empty", "Please Select Supplier Name");
		}/** Invalid Supplier Name */
			else {
				List<String> suppNameFound = ledgerDao.getSupplierValidation(purchaseNew2.getSupplierName());
				if(suppNameFound.size() == 0){
					errors.rejectValue("posp.supplierName", "POP.supplierName.invalid", " * Invalid Supplier Name - '"+purchaseNew2.getSupplierName()+"' ");
				}			
		}
		
		/** POP Purchase Invoice No */
		if(purchaseNew2.getInvoiceNO() == null || purchaseNew2.getInvoiceNO().length() == 0 ){
			errors.rejectValue("posp.invoiceNO", "POP.invoiceNO.required", "Purchase Invoice NO field can't be empty");
		}
		
		/** POP Purchase Date */
		if(purchaseNew2.getPdate() == null){
			errors.rejectValue("posp.pdate", "POP.date.required", "Purchase Date field can't be Empty");
		}
				
		/** Dynamic Row Validations */
		if(!popPurchItemList.isEmpty()){
			int RowCount = 0;
			for(POSPurchaseItem pospItem : popPurchItemList) {	
				
				if( (pospItem.getCategoryName() == null || pospItem.getCategoryName().length() == 0 ) && (pospItem.getItemName() == null || pospItem.getItemName().length() == 0) && (pospItem.getPurchaseAmt().signum() == 1)){
					errors.rejectValue("posp", "POP.cate.item.required", "'Category Name' and 'Item Name' Required on Row id "+RowCount+".");
				}else if( (pospItem.getCategoryName() == null || pospItem.getCategoryName().length() == 0 ) && (pospItem.getItemName() != null || pospItem.getItemName().length() != 0) &&  (pospItem.getPurchaseAmt().signum() == 1)){
					errors.rejectValue("posp", "POP.cate.required", "'Category Name' required on Row id "+RowCount+".");
				}else if( (pospItem.getCategoryName() != null || pospItem.getCategoryName().length() != 0 ) && (pospItem.getItemName() == null || pospItem.getItemName().length() == 0) &&  (pospItem.getPurchaseAmt().signum() == 1)){
					errors.rejectValue("posp", "POP.item.required", "'Item Name' required on Row id "+RowCount+".");
				}else if( pospItem.isDeleted == false && (pospItem.getPiecesPerSet() == 0) && ( (pospItem.getCategoryName().length() != 0 ) || (pospItem.getItemName().length() != 0) ) ){
					errors.rejectValue("posp", "POP.Pieces.required", "'Pieces Per Set' Required on Row id "+RowCount+".");
				}else if( pospItem.isDeleted == false && (pospItem.getSalesRate().signum() < 1) && ( (pospItem.getCategoryName().length() != 0 ) || (pospItem.getItemName().length() != 0) ) ){
					errors.rejectValue("posp", "POP.SalesRate.required", "'Sales Rate' Required on Row id "+RowCount+".");
				}else if( pospItem.isDeleted == false && (pospItem.getPurchaseAmt().signum() < 1) && ( (pospItem.getCategoryName().length() != 0 ) || (pospItem.getItemName().length() != 0) ) ){
					errors.rejectValue("posp", "POP.amt.zero", "'Amount' can't be Zero on Row id "+RowCount+".");
				}else if(RowCount == 1){
					
					/** Static/First Row Validation 
					
					POSPurchaseItem firstPopItem = popPurchItemList.get(0);
					if((firstPopItem.getCategoryName() == null || firstPopItem.getCategoryName().length() == 0) &&  (firstPopItem.getItemName() == null || firstPopItem.getItemName().length() == 0)  && (firstPopItem.getPurchaseAmt().signum() == 0) ){
						errors.rejectValue("posp", "POP.first.cate.item.required", "Category Name and ItemName Required for First Row.");
					}else if((firstPopItem.getCategoryName() == null || firstPopItem.getCategoryName().length() == 0 ) && (firstPopItem.getItemName() != null || firstPopItem.getItemName().length() != 0) && (firstPopItem.getPurchaseAmt().signum() == 0) ){
						errors.rejectValue("posp", "POP.first.cate.required", "Category Name Required for First Row.");
					}else if((firstPopItem.getCategoryName() != null || firstPopItem.getCategoryName().length() != 0 ) && (firstPopItem.getItemName() == null || firstPopItem.getItemName().length() == 0) && (firstPopItem.getPurchaseAmt().signum() == 0) ) {
						errors.rejectValue("posp", "POP.first.item.required", "Item Name Required for First Row.");
					}else if(firstPopItem.getPurchaseAmt().signum() < 1){
						errors.rejectValue("posp", "POP.first.amt.zero", "Amount can't be Zero for First Row.");
					}	*/
				}
				
				/** Invalid Category Name in  */
				if(pospItem.getCategoryName() != null && pospItem.getCategoryName().length() != 0) {
					List<POSCategory> poscatNameFound = poscategorydao.searchExistingCategory(pospItem.getCategoryName());
					if(poscatNameFound.size() == 0){
						errors.rejectValue("posp", "POP.cate.invalid", "Invalid Category Name on Row id "+RowCount+"");
					}					
				}
				/** Invalid Item Name */
				if(pospItem.getItemName() !=null && pospItem.getItemName().length() != 0) {
					List<POSItem> positemNameFound = positemdao.searchPOSItemBasedCategory(pospItem.getItemName(),pospItem.getCategoryName());
					if(positemNameFound.size() == 0) {
						errors.rejectValue("posp", "POP.item.invalid", "Invalid Item Name on Row id "+RowCount+"");
					}
				}			
				
				RowCount++; // Counter for Dynamic Row
			}
		}else {
			errors.rejectValue("posp", "POP.min.row", "* Invalid Purchase Bill !");
		}
		
		/** Grand Total Can't be Zero */
		if(purchaseNew2.getGrandAmount().signum() < 1){
			errors.rejectValue("posp.grandAmount", "POP.grandAmount.required", "Grand Total Amount Can't be Zero !");
		}
			
		
	}// end of validate
	
	/*public void validateTagStatus(PostagItem poptagitem, int rowCount, Errors errors){
		if(poptagitem.getStatus().equalsIgnoreCase("SOLD")){ 
			errors.rejectValue("posp", "POP.qty.sold", "Generated Barcode for Row id "+rowCount+" item is already sold.");
		}
	}	*/
	
} // end of class
