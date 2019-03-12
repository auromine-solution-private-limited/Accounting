package com.jewellery.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jewellery.dao.ItemMasterDao;
import com.jewellery.entity.ItemMaster;
import com.jewellery.entity.Transfer;


	@Component("TagIssueValidator")
	public class TagIssueValidator  implements Validator{
		
		public boolean supports(Class<?> clazz){
			return Transfer.class.isAssignableFrom(clazz);
		}
		
		@Autowired
		private ItemMasterDao itemmasterDao;
		private ItemMaster itemDetails;
		
		public void validate(Object command, Errors errors){
			
			Transfer tagObj =  (Transfer)command;
			int qty =0;
			
			if( tagObj.getTagissue() == "" ){ 
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tagissue", "message.tagissue.filled");
			}
			
	/*					
		if(tagObj.getTagissue().compareToIgnoreCase("IT100001") == 0 || tagObj.getTagissue().compareToIgnoreCase("IT100002") == 0 || tagObj.getTagissue().compareToIgnoreCase("IT100003") == 0 || tagObj.getTagissue().compareToIgnoreCase("IT100004") == 0 || tagObj.getTagissue().compareToIgnoreCase("IT100005") == 0 || tagObj.getTagissue().compareToIgnoreCase("IT100006") == 0 || 
						 tagObj.getTagissue().compareToIgnoreCase("IT100007") == 0 || tagObj.getTagissue().compareToIgnoreCase("IT100008") == 0 || tagObj.getTagissue().compareToIgnoreCase("IT100009") == 0 || tagObj.getTagissue().compareToIgnoreCase("IT100010") == 0 || tagObj.getTagissue().compareToIgnoreCase("IT100011") == 0 || tagObj.getTagissue().compareToIgnoreCase("IT100012") == 0){
				errors.rejectValue("tagissue","tagissue.invalidCode");
			}
			*/
			
			String item_code = tagObj.getTagissue();
			List<ItemMaster> itemList = itemmasterDao.searchItemMaster(item_code);
			
			for (int i = 0; i < itemList.size(); i++) {
				
				ItemMaster imast = (ItemMaster)itemList.get(i);
				
				if (imast instanceof ItemMaster) {
				itemDetails = (ItemMaster) imast;					
				qty = itemDetails.getQty();
				}
				if(qty == 0){
					errors.rejectValue("itemqtset","itemqtset.zeroqty");
				}	
			}
			
			
			
		}
	}


