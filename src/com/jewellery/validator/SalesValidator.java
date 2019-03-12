package com.jewellery.validator;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jewellery.dao.CardIssueDao;
import com.jewellery.dao.ItemMasterDao;
import com.jewellery.entity.CardIssue;
import com.jewellery.entity.ItemMaster;
import com.jewellery.entity.Sales;


@Component("salesValidator")
public class SalesValidator implements Validator
{
	@Autowired
	private ItemMasterDao itemmasterDao;
	@Autowired
	private CardIssueDao cardIssueDao;
	
	private ItemMaster itemDetails;
	
	
	BigDecimal initZero3 = new BigDecimal("0.000"); 
	BigDecimal initZero2 = new BigDecimal("0.00"); 
	
	
	public boolean supports(Class<?> clazz){
		return Sales.class.isAssignableFrom(clazz);
	}
	
	public void validateInsert(Object command, Errors errors){
		
		Sales sales = (Sales) command;
		
		String iCode = sales.getItemCode();
		String iCode1 = sales.getItemCode1();
		String iCode2 = sales.getItemCode2();
		String iCode3 = sales.getItemCode3();
		String iCode4 = sales.getItemCode4();
		
		BigDecimal ZERO = new BigDecimal("0.00");
		BigDecimal MAXPAN = new BigDecimal("500000.00");
		Boolean sameType = false;
		Boolean sameVat = false;
		
		/** Checking for Same Metal Type for all Five rows **/
		//Row1
		if(sales.getBullionType().length() != 0 && sales.getBullionType1().length() != 0){
			if(!sales.getBullionType().equalsIgnoreCase(sales.getBullionType1())){
				sameType = true;
			}
			if(sales.getVtax().compareTo(sales.getVtax1()) != 0){
				sameVat = true;
			}
		}
		
		if(sales.getBullionType().length() != 0 && sales.getBullionType2().length() != 0){
			if(!sales.getBullionType().equalsIgnoreCase(sales.getBullionType2())){
				sameType = true;
			}
			if(sales.getVtax().compareTo(sales.getVtax2()) != 0){
				sameVat = true;
			}
		}
		if(sales.getBullionType().length() != 0 && sales.getBullionType3().length() != 0){
			if(!sales.getBullionType().equalsIgnoreCase(sales.getBullionType3())){
				sameType = true;
			}
			if(sales.getVtax().compareTo(sales.getVtax3()) != 0){
				sameVat = true;
			}
		}
		if(sales.getBullionType().length() != 0 && sales.getBullionType4().length() != 0){
			if(!sales.getBullionType().equalsIgnoreCase(sales.getBullionType4())){
				sameType = true;
			}
			if(sales.getVtax().compareTo(sales.getVtax4()) != 0){
				sameVat = true;
			}
		}
		//row 2
		if(sales.getBullionType1().length() != 0 && sales.getBullionType2().length() != 0){
			if(!sales.getBullionType1().equalsIgnoreCase(sales.getBullionType2())){
				sameType = true;
			}
			if(sales.getVtax1().compareTo(sales.getVtax2()) != 0){
				sameVat = true;
			}
		}
		if(sales.getBullionType1().length() != 0 && sales.getBullionType3().length() != 0){
			if(!sales.getBullionType1().equalsIgnoreCase(sales.getBullionType3())){
				sameType = true;
			}
			if(sales.getVtax1().compareTo(sales.getVtax3()) != 0){
				sameVat = true;
			}
		}
		if(sales.getBullionType1().length() != 0 && sales.getBullionType4().length() != 0){
			if(!sales.getBullionType1().equalsIgnoreCase(sales.getBullionType4())){
				sameType = true;
			}
			if(sales.getVtax1().compareTo(sales.getVtax4()) != 0){
				sameVat = true;
			}
		}
		//row 3
		if(sales.getBullionType2().length() != 0 && sales.getBullionType3().length() != 0){
			if(!sales.getBullionType2().equalsIgnoreCase(sales.getBullionType3())){
				sameType = true;
			}
			if(sales.getVtax2().compareTo(sales.getVtax3()) != 0){
				sameVat = true;
			}
		}
		if(sales.getBullionType2().length() != 0 && sales.getBullionType4().length() != 0){
			if(!sales.getBullionType2().equalsIgnoreCase(sales.getBullionType4())){
				sameType = true;
			}
			if(sales.getVtax2().compareTo(sales.getVtax4()) != 0){
				sameVat = true;
			}
		}
		//row 4
		if(sales.getBullionType3().length() != 0 && sales.getBullionType4().length() != 0){
			if(!sales.getBullionType3().equalsIgnoreCase(sales.getBullionType4())){
				sameType = true;
			}
			if(sales.getVtax3().compareTo(sales.getVtax4()) != 0){
				sameVat = true;
			}
		}
		
		if(sameType == true){
			errors.rejectValue("bullionType", "bullionType.not.same", "Please Enter Same Metal Type Ornaments !");
		}
		
		if(sameVat == true){
			// errors.rejectValue("vtax", "vtax.not.same", "Please Enter Same vat % Ornaments !");
		}
		
				
		/**** Date Field Validation ****/
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salesDate", "sales.date.required", "Date field can't be empty !");
		
										
		//11111111111111
		if (iCode.length()!=0) {
			if(iCode.equalsIgnoreCase("IT100011") || iCode.equalsIgnoreCase("IT100012")){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "grossWeight", "r1.grossweight.required","Gross weight required on Row1");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName", "r1.categoryName.required","Category name required on Row1");
			}
		}
		if (iCode1.length()!=0) {
			if(iCode1.equalsIgnoreCase("IT100011") || iCode1.equalsIgnoreCase("IT100012")){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "grossWeight1", "r2.grossweight.required","Gross weight required on Row2");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName1", "r2.categoryName.required","Category name required on Row2");
			}
		}
		if (iCode2.length()!=0) {
			if(iCode2.equalsIgnoreCase("IT100011") || iCode2.equalsIgnoreCase("IT100012")){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "grossWeight2", "r3.grossweight.required","Gross weight required on Row3");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName2", "r3.categoryName.required","Category name required on Row3");
			}
		}
		if (iCode3.length()!=0) {
			if(iCode3.equalsIgnoreCase("IT100011") || iCode3.equalsIgnoreCase("IT100012")){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "grossWeight3", "r4.grossweight.required","Gross weight required on Row4");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName3", "r4.categoryName.required","Category name required on Row4");
			}
		}
		if (iCode4.length()!=0) {
			if(iCode4.equalsIgnoreCase("IT100011") || iCode4.equalsIgnoreCase("IT100012")){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "grossWeight4", "r5.grossweight.required","Gross weight required on Row5");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName4", "r5.categoryName.required","Category name required on Row5");
			}
		}
		
		//2222222222222
		/** For first Row itemCode **/
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemCode", "message.itemCode.filled","ItemCode on first row Required ");
										

		//55555555555555555
		/** For walk-in Name and Address , !walk-in Credit sales **/
		if(sales.getCustomerName().equalsIgnoreCase("walk_in")){
						
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "walkIn_Name", "walkIn.Name.required","Walkin Name required for Walk-in Customer	");
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "walkIn_City", "walkIn.City.required","Walkin City required for Walk-in Customer	");
			
			if(sales.getBalToPay().compareTo(ZERO) > 0){
				errors.rejectValue("balToPay", "balToPay.required", "Credit Sales not allowed for Walk-in Customer !");
			}
		}
		
		//66666666666			
		if(sales.getAmountAfterLess().signum() <= 0 && sales.getAmountAfterLess1().signum() <= 0 && sales.getAmountAfterLess2().signum() <= 0 && sales.getAmountAfterLess3().signum() <= 0 && sales.getAmountAfterLess3().signum() <= 0)
		{
			if(sales.getAdjustmentAmount().signum()>0){
				errors.rejectValue("adjustmentAmount", "adjustmentAmount.required", "Invalid Bill !");
			}					
		}else if(sales.getBillAmount().signum()<=0 || sales.getBillAmount()==null){
			
			//3333333333333
			/** For Bill Amount && PAN Number**/
			errors.rejectValue("billAmount", "billAmount.required","BillAmount can't be Zero");
			
			
		}else if(sales.getBillAmount().compareTo(MAXPAN) > 0){					
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "panNumber", "panNumber.required","BillAmount greater than 5 Lakhs PAN Number Required");
		}  
		
		
		//4444444444
		/** For Cash Amount **/
		if(sales.getCashPayment() != null) {
			if(sales.getCashAmount() == null ||sales.getCashAmount().compareTo(ZERO)==0 ){
				errors.rejectValue("cashAmount","Cash.Amount.Required","Cash Amount Required");
			}
			
		}			
		/** For Card Amount  & Card Bank Name**/
		if(sales.getCardPayment()!=null ){
			
			if(sales.getCalCardAmount()== null || sales.getCalCardAmount().compareTo(ZERO)==0){
				errors.rejectValue("cardAmount","Card.Amount.Required","Card Amount Required");
			}else if(sales.getSalesType().equalsIgnoreCase("Estimate Sales")){
				errors.rejectValue("cardAmount","Estimate.Card.Amount","Card Payment restricted for Estimate Sales.");
			}
			
			if(sales.getCardBank().length() == 0){
				errors.rejectValue("cardBank","card.bank.required","Card Bank Required");
			}
		}			
		/** For Cheque Amount  & Cheque Bank Name**/
		if(sales.getChequePayment() !=null ) {
			
			if(sales.getChequeAmount() == null || sales.getChequeAmount().compareTo(ZERO)==0){
				errors.rejectValue("chequeAmount","Cheque.Amount.Required","Cheque Amount Required");
			}else if(sales.getSalesType().equalsIgnoreCase("Estimate Sales")){
				errors.rejectValue("chequeAmount","Estimate.Cheque.Amount","Cheque Payment restricted for Estimate Sales.");
			}
			
			if(sales.getChequeBank().length() == 0){
				errors.rejectValue("chequeBank","Cheque.bank.required","Cheque Bank Required");
			}
		}			
		/** For Voucher Amount  & Voucher Bank Name**/
		if(sales.getVoucherPayment()!=null) {
			
			if(sales.getVoucherAmount() == null || sales.getVoucherAmount().compareTo(ZERO)==0){
				errors.rejectValue("voucherAmount","Voucher.Amount.Required","Voucher Amount Required");
			}else if(sales.getSalesType().equalsIgnoreCase("Estimate Sales")){
				errors.rejectValue("voucherAmount","Estimate.Voucher.Amount","Voucher Payment restricted for Estimate Sales.");
			}
			
			if(sales.getVoucherList().length() == 0){
				errors.rejectValue("voucherList","Voucher.list.required","Please Select Vouchers");
			}
		}
		
		// Balance To Pay/*** this is commented for SGH Client as per His GIven Requirement 14-06-2013****/
		/*if(sales.getBalToPay().signum() < 0){
			errors.rejectValue("balToPay", "Baltopay.negative", "Balance to Pay can't be less than ZERO !");
		}*/
		
		// SS Card No Savings Amount
		if(sales.getSSCardNoVal().length() != 0){
			
			try{
				if(sales.getSSCardNo() != null && !sales.getSSCardNo().isEmpty()){
					String SelectedCardNo[] = sales.getSSCardNo().split(",");
					for(int i = 0 ; i<SelectedCardNo.length; i++){
						CardIssue CardIssueEntity = cardIssueDao.getCardIssueCardNO(SelectedCardNo[i]).get(0);
						if(CardIssueEntity.getClosingBalanceInGrams().signum() <= 0 && CardIssueEntity.getClosingBalanceInRs().signum() <= 0){
							errors.rejectValue("SSCardNoVal", "SSCardNoVal.negative", "Savings Card Amount for Card NO. '"+SelectedCardNo[i]+"' is ZERO !");
						}
					}
							
				}
			}catch(IndexOutOfBoundsException ie){
				System.out.println(" 'Sales Validator :: line 283' :: "+ie);
			}
			
			if(sales.getSSCardAmount() == null || sales.getSSCardAmount().signum() <= 0){
				errors.rejectValue("SSCardNoVal", "SSCardNoVal.negative", "Savings Scheme Amount can't be ZERO !");
			}else if(sales.getSalesType().equalsIgnoreCase("Estimate Sales")){
				errors.rejectValue("SSCardNoVal","Estimate.SSCardNoVal.Amount","Savings Scheme Payment restricted for Estimate Sales.");
			}
		}
		
		if((sales.getExchangeAmount() != null && sales.getExchangeAmount().signum() > 0 ) && sales.getSalesType().equalsIgnoreCase("Estimate Sales")){
			errors.rejectValue("exchangeAmount","Estimate.exchangeAmount.Amount","Exchange Purchase restricted for Estimate Sales.");
		}
	}
	
	//Row 2 Same Metal Type and Same Vat  
	public void validateR2(Object command, String R2bullionType, BigDecimal R2tax, Errors errors){
		Sales sales=(Sales)command;
		if(!sales.getBullionType().equalsIgnoreCase(R2bullionType)){
			errors.rejectValue("bullionType1", "bullionType.not.same", "Row 2 Please Enter Same Metal Type Ornaments !");
			sales = clearR2(sales);
		}
		if(sales.getVtax().compareTo(R2tax) != 0){
			//errors.rejectValue("vtax1", "vtax.not.same", "Row 2 Please Enter Same vat % Ornaments !");
		//	sales = clearR2(sales);
		}
	}
	
	//Row 3 Same Metal Type and Same Vat
	public void validateR3(Object command, String R3bullionType, BigDecimal R3tax, Errors errors){
		Sales sales=(Sales)command;
		if(!sales.getBullionType().equalsIgnoreCase(R3bullionType)){
			errors.rejectValue("bullionType2", "bullionType.not.same", "Row 3 Please Enter Same Metal Type Ornaments !");
			sales = clearR3(sales);
		}
		if(sales.getVtax().compareTo(R3tax) != 0){
			//errors.rejectValue("vtax2", "vtax.not.same", "Row 3 Please Enter Same vat % Ornaments !");
			//sales = clearR3(sales);
		}
	}
	
	//Row 4 Same Metal Type and Same Vat
	public void validateR4(Object command, String R4bullionType, BigDecimal R4tax, Errors errors){
		Sales sales=(Sales)command;
		if(!sales.getBullionType().equalsIgnoreCase(R4bullionType)){
			errors.rejectValue("bullionType3", "bullionType.not.same", "Row 4 Please Enter Same Metal Type Ornaments !");
			sales = clearR4(sales);
		}
		if(sales.getVtax().compareTo(R4tax) != 0){
			//errors.rejectValue("vtax3", "vtax.not.same", "Row 4 Please Enter Same vat % Ornaments !");
			//sales = clearR4(sales);
		}
	}
	
	//Row 5 Same Metal Type and Same Vat
	public void validateR5(Object command, String R5bullionType, BigDecimal R5tax, Errors errors){
		Sales sales=(Sales)command;
		if(!sales.getBullionType().equalsIgnoreCase(R5bullionType)){
			errors.rejectValue("bullionType4", "bullionType.not.same", "Row 5 Please Enter Same Metal Type Ornaments !");
			sales = clearR5(sales);
		}
		if(sales.getVtax().compareTo(R5tax) != 0){
			//errors.rejectValue("vtax4", "vtax.not.same", "Row 5 Please Enter Same vat % Ornaments !");
			//sales = clearR5(sales);
		}
		
	}
	
	public void validate(Object command, Errors errors){
		
			Sales sales=(Sales)command;
			
			String iCode = sales.getItemCode();
			String iCode1 = sales.getItemCode1();
			String iCode2 = sales.getItemCode2();
			String iCode3 = sales.getItemCode3();
			String iCode4 = sales.getItemCode4();
						
			List<ItemMaster> itemList = itemmasterDao.searchItemMaster(iCode);
			List<ItemMaster> itemList1 = itemmasterDao.searchItemMaster(iCode1);
			List<ItemMaster> itemList2 = itemmasterDao.searchItemMaster(iCode2);
			List<ItemMaster> itemList3 = itemmasterDao.searchItemMaster(iCode3);
			List<ItemMaster> itemList4 = itemmasterDao.searchItemMaster(iCode4);
			
			BigDecimal total_grossWt = new BigDecimal("0.00");
			BigDecimal total_grossWt1 = new BigDecimal("0.00");
			BigDecimal total_grossWt2 = new BigDecimal("0.00");
			BigDecimal total_grossWt3 = new BigDecimal("0.00");
			BigDecimal total_grossWt4 = new BigDecimal("0.00");
			int nop = 0,nop1 = 0,nop2 = 0,nop3 = 0,nop4 = 0;
			
			/** To Check Invalid itemCodes and stock validation */
			/** Row 1*/
			if (iCode.length()!=0) {
								
				if (itemList == null || itemList.isEmpty()) {
					errors.rejectValue("itemCode", "iCode.invalid","ItemCode on Row 1 is invalid");		
					sales = clearR1(sales); // Clearing Row
				}
				else {					
					for (int i = 0; i < itemList.size(); i++) {
						
						ItemMaster imast = (ItemMaster) itemList.get(i);
						if (imast instanceof ItemMaster) {
							itemDetails = (ItemMaster) imast;
							total_grossWt = itemDetails.getTotalGrossWeight();
							nop = itemDetails.getQty();

						}
					}
					if(iCode.compareToIgnoreCase("IT100011") == 0 || iCode.compareToIgnoreCase("IT100012") == 0 ){
						
						if(total_grossWt != null && total_grossWt.signum() == 0){
							errors.rejectValue("grossWeight", "grossweight.zero", "Stock Not Available for Row 1 ItemCode");
							sales = clearR1(sales);
						}else if(total_grossWt != null && total_grossWt.compareTo(sales.getGrossWeight()) == -1){
							errors.rejectValue("grossWeight", "loose.grossweight.zero", "In Row 1 Entered Gross Weight is greater than Available stock");
						}
					}
					else if(total_grossWt.signum() == 0 || nop == 0){
						errors.rejectValue("grossWeight", "grossweight.zero", "Stock Not Available for Row 1 ItemCode");
						sales = clearR1(sales);
					}

				}

			}
			/***
			 * New validation added for Category Loose Stock 1 row
			 * ****/
			if(sales.getMetalUsed().equalsIgnoreCase("Gold Loose Stock") || sales.getMetalUsed().equalsIgnoreCase("Silver Loose Stock"))
					{	
				if(total_grossWt != null && total_grossWt.signum() == 0){
					errors.rejectValue("grossWeight", "grossweight.zero", "Stock Not Available for Row 1 ItemCode");
					sales = clearR1(sales);
				}else if(total_grossWt != null && total_grossWt.compareTo(sales.getGrossWeight()) == -1){
					errors.rejectValue("grossWeight", "loose.grossweight.zero", "In Row 1 Entered Gross Weight is greater than Available stock");
				}
	                }
			
			/** Row 2*/
			if (iCode1.length()!=0) {
				
				if (itemList1 == null || itemList1.isEmpty()) {
					errors.rejectValue("itemCode1", "iCode1.invalid","ItemCode on Row 2 is invalid");	
					sales = clearR2(sales); // Clearing Row
				}
				else {					
					for (int i = 0; i < itemList1.size(); i++) {
						
						ItemMaster imast1 = (ItemMaster) itemList1.get(i);
						if (imast1 instanceof ItemMaster) {
							itemDetails = (ItemMaster) imast1;
							total_grossWt1 = itemDetails.getTotalGrossWeight();
							nop1 = itemDetails.getQty();
						}
					}
					if(iCode1.compareToIgnoreCase("IT100011") == 0 || iCode1.compareToIgnoreCase("IT100012") == 0 ){
						
						if(total_grossWt1 != null && total_grossWt1.signum() == 0){
							errors.rejectValue("grossWeight1", "grossweight1.zero", "Stock Not Available for Row 2 ItemCode");
							sales = clearR2(sales); // Clearing Row
						}
						else if(total_grossWt1 != null && total_grossWt1.compareTo(sales.getGrossWeight1()) == -1){
							errors.rejectValue("grossWeight1", "loose.grossweight1.zero", "In Row 2 Entered Gross Weight is greater than Available stock");
						}
						
					}
					else if(total_grossWt1 != null && total_grossWt1.signum() == 0 || nop1 == 0){
						errors.rejectValue("grossWeight1", "grossweight1.zero", "Stock Not Available for Row 2 ItemCode");
						sales = clearR2(sales); // Clearing Row
					}

				}

			}
			/***
			 * New validation added for Category Loose Stock 2 row
			 * ****/
			if(sales.getMetalUsed1().equalsIgnoreCase("Gold Loose Stock") || sales.getMetalUsed1().equalsIgnoreCase("Silver Loose Stock"))
			{
				if(total_grossWt1 != null && total_grossWt1.signum() == 0){
					errors.rejectValue("grossWeight1", "grossweight1.zero", "Stock Not Available for Row 2 ItemCode");
					sales = clearR2(sales); // Clearing Row
				}
				else if(total_grossWt1 != null && total_grossWt1.compareTo(sales.getGrossWeight1()) == -1){
					errors.rejectValue("grossWeight1", "loose.grossweight1.zero", "In Row 2 Entered Gross Weight is greater than Available stock");
				}
			}
			
			/** Row 3*/
			if (iCode2.length()!=0) {
				
				if (itemList2 == null || itemList2.isEmpty()) {
					errors.rejectValue("itemCode2", "iCode2.invalid","ItemCode on Row 3 is invalid");
					sales = clearR3(sales); // Clearing Row
				}
				else {					
					for (int i = 0; i < itemList2.size(); i++) {
						
						ItemMaster imast2 = (ItemMaster) itemList2.get(i);
						if (imast2 instanceof ItemMaster) {
							itemDetails = (ItemMaster) imast2;
							total_grossWt2 = itemDetails.getTotalGrossWeight();
							nop2 = itemDetails.getQty();
						}
					}
					if(iCode2.compareToIgnoreCase("IT100011") == 0 || iCode2.compareToIgnoreCase("IT100012") == 0 ){
						
						if(total_grossWt2 != null && total_grossWt2.signum() == 0){
							errors.rejectValue("grossWeight2", "grossweight2.zero", "Stock Not Available for Row 3 ItemCode");
							sales = clearR3(sales); // Clearing Row
						}
						else if(total_grossWt2 != null && total_grossWt2.compareTo(sales.getGrossWeight2()) == -1){
							errors.rejectValue("grossWeight2", "loose.grossweight2.zero", "In Row 3 Entered Gross Weight is greater than Available stock");							
						}
						
					}
					else if(total_grossWt2.signum() == 0 || nop2 == 0){
						errors.rejectValue("grossWeight2", "grossweight2.zero", "Stock Not Available for Row 3 ItemCode");
						sales = clearR3(sales); // Clearing Row
					}

				}

			}
			/***
			 * New validation added for Category Loose Stock 3 row
			 * ****/
			if(sales.getMetalUsed2().equalsIgnoreCase("Gold Loose Stock") || sales.getMetalUsed2().equalsIgnoreCase("Silver Loose Stock"))
			{
				if(total_grossWt2 != null && total_grossWt2.signum() == 0){
					errors.rejectValue("grossWeight2", "grossweight2.zero", "Stock Not Available for Row 3 ItemCode");
					sales = clearR3(sales); // Clearing Row
				}
				else if(total_grossWt2 != null && total_grossWt2.compareTo(sales.getGrossWeight2()) == -1){
					errors.rejectValue("grossWeight2", "loose.grossweight2.zero", "In Row 3 Entered Gross Weight is greater than Available stock");							
				}
			}
			/** Row 4*/
			if (iCode3.length()!=0) {
				
				
				if (itemList3 == null || itemList3.isEmpty()) {
					errors.rejectValue("itemCode3", "iCode3.invalid","ItemCode on Row 4 is invalid");
					sales = clearR4(sales); // Clearing Row
				}
				else {					
					for (int i = 0; i < itemList3.size(); i++) {
						
						ItemMaster imast3 = (ItemMaster) itemList3.get(i);
						if (imast3 instanceof ItemMaster) {
							itemDetails = (ItemMaster) imast3;
							total_grossWt3 = itemDetails.getTotalGrossWeight();
							nop3 = itemDetails.getQty();

						}
					}
					if(iCode3.compareToIgnoreCase("IT100011") == 0 || iCode3.compareToIgnoreCase("IT100012") == 0 ){
						
						if(total_grossWt3 != null && total_grossWt3.signum() == 0){
							errors.rejectValue("grossWeight3", "grossweight3.zero", "Stock Not Available for Row 4 ItemCode");
							sales = clearR4(sales); // Clearing Row
						}
						else if(total_grossWt3 != null && total_grossWt3.compareTo(sales.getGrossWeight3()) == -1){
							errors.rejectValue("grossWeight3", "loose.grossweight3.zero", "In Row 4 Entered Gross Weight is greater than Available stock");
							
						}
					}
					else if(total_grossWt3.signum() == 0 || nop3 == 0){
						errors.rejectValue("grossWeight3", "grossweight3.zero", "Stock Not Available for Row 4 ItemCode");
						sales = clearR4(sales); // Clearing Row
					}

				}

			}
			/***
			 * New validation added for Category Loose Stock 4 row
			 * ****/
			if(sales.getMetalUsed3().equalsIgnoreCase("Gold Loose Stock") || sales.getMetalUsed3().equalsIgnoreCase("Silver Loose Stock"))
			{
				if(total_grossWt3 != null && total_grossWt3.signum() == 0){
					errors.rejectValue("grossWeight3", "grossweight3.zero", "Stock Not Available for Row 4 ItemCode");
					sales = clearR4(sales); // Clearing Row
				}
				else if(total_grossWt3 != null && total_grossWt3.compareTo(sales.getGrossWeight3()) == -1){
					errors.rejectValue("grossWeight3", "loose.grossweight3.zero", "In Row 4 Entered Gross Weight is greater than Available stock");
					
				}
			}
			
			/** Row 5*/
			if (iCode4.length()!=0) {
				
				if (itemList4 == null || itemList4.isEmpty()) {
					errors.rejectValue("itemCode4", "iCode4.invalid","ItemCode on Row 5 is invalid");
					sales = clearR5(sales); // Clearing Row
				}
				else {					
					for (int i = 0; i < itemList4.size(); i++) {
						
						ItemMaster imast4 = (ItemMaster) itemList4.get(i);
						if (imast4 instanceof ItemMaster) {
							itemDetails = (ItemMaster) imast4;
							total_grossWt4 = itemDetails.getTotalGrossWeight();
							nop4 = itemDetails.getQty();

						}
					}
					if(iCode4.compareToIgnoreCase("IT100011") == 0 || iCode4.compareToIgnoreCase("IT100012") == 0 ){
						
						 if(total_grossWt4 != null && total_grossWt4.signum() == 0){
								errors.rejectValue("grossWeight4", "grossweight4.zero", "Stock Not Available for Row 5 ItemCode");
								sales = clearR5(sales); // Clearing Row
							}else if(total_grossWt4.compareTo(sales.getGrossWeight4()) == -1){
								errors.rejectValue("grossWeight4", "loose.grossweight4.zero", "In Row 5 Entered Gross Weight is greater than Available stock");
							}
					}
					else if(total_grossWt4 != null && total_grossWt4.signum() == 0 || nop4 == 0){
						errors.rejectValue("grossWeight4", "grossweight4.zero", "Stock Not Available for Row 5 ItemCode");
						sales = clearR5(sales); // Clearing Row						
					}

				}

			}
			/***
			 * New validation added for Category Loose Stock 4 row
			 * ****/
			if(sales.getMetalUsed4().equalsIgnoreCase("Gold Loose Stock") || sales.getMetalUsed4().equalsIgnoreCase("Silver Loose Stock"))
			{
				if(total_grossWt4 != null && total_grossWt4.signum() == 0){
					errors.rejectValue("grossWeight4", "grossweight4.zero", "Stock Not Available for Row 5 ItemCode");
					sales = clearR5(sales); // Clearing Row
				}else if(total_grossWt4.compareTo(sales.getGrossWeight4()) == -1){
					errors.rejectValue("grossWeight4", "loose.grossweight4.zero", "In Row 5 Entered Gross Weight is greater than Available stock");
				}
			}
			
			/** Same Item code validation only for ornaments */
			if (!(iCode.compareToIgnoreCase("IT100011") == 0	|| iCode.compareToIgnoreCase("IT100012") == 0)) {
			
			if (iCode != "" && iCode1 != "") {
				if (iCode.compareToIgnoreCase(iCode1) == 0) {
					errors.rejectValue("itemCode1","Sales.itemCode.Duplicate","Same ItemCode Cannot be Used");					
					}
				}
			}
			
			if(iCode != "" && iCode2 != ""){
				if(iCode.compareToIgnoreCase(iCode2) == 0){
					
					if(iCode.compareToIgnoreCase("IT100011") == 0 || iCode.compareToIgnoreCase("IT100012") == 0 ){
					}else{
					errors.rejectValue("itemCode1", "itemCode3.Duplicate","Same ItemCode Cannot be Used");
						
					}

				}
			}
					
		if(iCode != "" && iCode3 != ""){
			
			if(iCode.compareToIgnoreCase(iCode3)==0){
			
				if(iCode.compareToIgnoreCase("IT100011") == 0 || iCode.compareToIgnoreCase("IT100012") == 0 ){
				}else{
					errors.rejectValue("itemCode1", "Sales.itemCode.Duplicate","Same ItemCode Cannot be Used");
					
				}
				}
		}
					
			if(iCode != "" && iCode4 != ""){
			
				if(iCode.compareToIgnoreCase(iCode4)==0){
			
					if(iCode.compareToIgnoreCase("IT100011") == 0 || iCode.compareToIgnoreCase("IT100012") == 0 ){
						
					}else{
						errors.rejectValue("itemCode1", "Sales.itemCode.Duplicate","Same ItemCode Cannot be Used");
						

				}
			}
		}
			
			if(iCode1 != "" && iCode2 != ""){
			
				if(iCode1.compareToIgnoreCase(iCode2)==0){
			
					if(iCode1.compareToIgnoreCase("IT100011") == 0 || iCode1.compareToIgnoreCase("IT100012") == 0 ){
					}else{						
						errors.rejectValue("itemCode2", "Sales.itemCode.Duplicate","Same ItemCode Cannot be Used");
				}
			}
		}

		if(iCode1 != "" && iCode3 != ""){
				if(iCode1.compareToIgnoreCase(iCode3)==0){
					if(iCode1.compareToIgnoreCase("IT100011") == 0 || iCode1.compareToIgnoreCase("IT100012") == 0 ){
					}else{					
					errors.rejectValue("itemCode2", "Sales.itemCode.Duplicate","Same ItemCode Cannot be Used");
					
				}
			}
		}
			
		if(iCode1 != "" && iCode4 != ""){
				if(iCode1.compareToIgnoreCase(iCode4)==0){
					if(iCode1.compareToIgnoreCase("IT100011") == 0 || iCode1.compareToIgnoreCase("IT100012") == 0 ){
					}else{			
					errors.rejectValue("itemCode2", "Sales.itemCode.Duplicate","Same ItemCode Cannot be Used");
					

				}
			}
		}

			if(iCode2 != "" && iCode3 != ""){
				if(iCode2.compareToIgnoreCase(iCode3)==0){
					if(iCode2.compareToIgnoreCase("IT100011") == 0 || iCode2.compareToIgnoreCase("IT100012") == 0 ){
					}else{
						errors.rejectValue("itemCode3", "Sales.itemCode.Duplicate","Same ItemCode Cannot be Used");
						

				}
			}
		}
			
		if(iCode2 != "" && iCode4 != ""){
				if(iCode2.compareToIgnoreCase(iCode4)==0){
					if(iCode2.compareToIgnoreCase("IT100011") == 0 || iCode2.compareToIgnoreCase("IT100012") == 0 ){
					}else{
						errors.rejectValue("itemCode3", "Sales.itemCode.Duplicate","Same ItemCode Cannot be Used");
						
				}
			}
		}
			
		if(iCode3 != "" && iCode4 != ""){
			if(iCode3.compareToIgnoreCase(iCode4)==0){
				if(iCode3.compareToIgnoreCase("IT100011") == 0 || iCode3.compareToIgnoreCase("IT100012") == 0 ){
				}else{
					errors.rejectValue("itemCode3", "Sales.itemCode.Duplicate","Same ItemCode Cannot be Used");
					
				}
			}
		}
		
		//*********** Check for loss metal gross weight *****************/
		BigDecimal GoldtempGross = new BigDecimal("0.00");
		BigDecimal silvertempGross = new BigDecimal("0.00");
		  
		if(!iCode.isEmpty()){
			  if(iCode.compareToIgnoreCase("IT100011") == 0){
				  GoldtempGross = GoldtempGross.add(sales.getGrossWeight());
			  }else if(iCode.compareToIgnoreCase("IT100012") == 0){
				  silvertempGross = silvertempGross.add(sales.getGrossWeight());
			  }			  
		 }
		if(!iCode1.isEmpty()){
			  if(iCode1.compareToIgnoreCase("IT100011") == 0){
				  GoldtempGross = GoldtempGross.add(sales.getGrossWeight1());
			  }else if(iCode1.compareToIgnoreCase("IT100012") == 0){
				  silvertempGross = silvertempGross.add(sales.getGrossWeight1());
			  }			  
		 }
		if(!iCode2.isEmpty()){
			  if(iCode2.compareToIgnoreCase("IT100011") == 0){
				  GoldtempGross = GoldtempGross.add(sales.getGrossWeight2());
			  }else if(iCode2.compareToIgnoreCase("IT100012") == 0){
				  silvertempGross = silvertempGross.add(sales.getGrossWeight2());
			  }			  
		 }
		if(!iCode3.isEmpty()){
			  if(iCode3.compareToIgnoreCase("IT100011") == 0){
				  GoldtempGross = GoldtempGross.add(sales.getGrossWeight3());
			  }else if(iCode3.compareToIgnoreCase("IT100012") == 0){
				  silvertempGross = silvertempGross.add(sales.getGrossWeight3());
			  }			  
		 }
		if(!iCode4.isEmpty()){
			  if(iCode4.compareToIgnoreCase("IT100011") == 0){
				  GoldtempGross = GoldtempGross.add(sales.getGrossWeight4());
			  }else if(iCode4.compareToIgnoreCase("IT100012") == 0){
				  silvertempGross = silvertempGross.add(sales.getGrossWeight4());
			  }			  
		 }
		List<ItemMaster> GoldActualStock = itemmasterDao.searchItemMaster("IT100011");
		if(GoldActualStock.get(0).getTotalGrossWeight().compareTo(GoldtempGross) < 0){
			errors.rejectValue("grossWeight", "total.gold.weight", "* Entered Gold Loose Stock is less than Available Stock !");
		}
		List<ItemMaster> SilverActualStock = itemmasterDao.searchItemMaster("IT100012");
		if(SilverActualStock.get(0).getTotalGrossWeight().compareTo(silvertempGross) < 0){
			errors.rejectValue("grossWeight1", "total.Silver.weight", "* Entered Silver Loose Stock is less than Available Stock !");
		}
		//********** end *********//

	}	


	/* Validator method for Sales Update */
	public void validate(Object command1, Object command2, Errors errors){
		
		Sales salesOld = (Sales)command1;
		Sales salesNew = (Sales)command2;
		
		String iCode = salesNew.getItemCode();
		String iCode1 = salesNew.getItemCode1();
		String iCode2 = salesNew.getItemCode2();
		String iCode3 = salesNew.getItemCode3();
		String iCode4 = salesNew.getItemCode4();
					
		List<ItemMaster> itemList = itemmasterDao.searchItemMaster(iCode);
		List<ItemMaster> itemList1 = itemmasterDao.searchItemMaster(iCode1);
		List<ItemMaster> itemList2 = itemmasterDao.searchItemMaster(iCode2);
		List<ItemMaster> itemList3 = itemmasterDao.searchItemMaster(iCode3);
		List<ItemMaster> itemList4 = itemmasterDao.searchItemMaster(iCode4);
		
		BigDecimal total_grossWt = new BigDecimal("0.00");
		BigDecimal total_grossWt1 = new BigDecimal("0.00");
		BigDecimal total_grossWt2 = new BigDecimal("0.00");
		BigDecimal total_grossWt3 = new BigDecimal("0.00");
		BigDecimal total_grossWt4 = new BigDecimal("0.00");
		
		/* *********************************************** Walk-in to Regular *********************************/
		
		if(salesOld.getCustomerName().equals("walk_in") && !salesNew.getCustomerName().equals("walk_in")){			
			errors.rejectValue("customerName", "walk_in.Regular", "Invalid Transaction - Walk in Customer to Regular Customer Cannot Be Updated");					
		}
	
	
		if(!salesOld.getCustomerName().equals("walk_in") && salesNew.getCustomerName().equals("walk_in")){			
			errors.rejectValue("customerName", "Regular.walk_in", "Invalid Transaction - Regular Customer to Walkin Customer Cannot Be Updated");					
		}
	
		
		/** To Check Invalid itemCodes and stock validation */
		/** Row 1*/
		if (!iCode.isEmpty()) {
			if (itemList == null || itemList.isEmpty()) {
				errors.rejectValue("itemCode", "iCode.invalid","ItemCode on Row 1 is invalid");
				
			}
			else {	
				
				for (int i = 0; i < itemList.size(); i++) {
					
					ItemMaster imast = (ItemMaster) itemList.get(i);
					if (imast instanceof ItemMaster) {
						itemDetails = (ItemMaster) imast;
						total_grossWt = itemDetails.getTotalGrossWeight();
					}
				}
				
				if(iCode.compareToIgnoreCase("IT100011") == 0 || iCode.compareToIgnoreCase("IT100012") == 0 ){					
									
					BigDecimal diffgrwt1 = total_grossWt.add(salesOld.getGrossWeight());				
					BigDecimal finalgrwt1 = diffgrwt1.subtract(salesNew.getGrossWeight());
					
					if(finalgrwt1.signum() < 0){
						errors.rejectValue("grossWeight", "loose.grossweight.zero", "Loose Metal Stock Not Available for Row 1 ItemCode");						
					}					
					
				}			

			}

		}
		
		if(salesNew.getMetalUsed().equalsIgnoreCase("Gold Loose Stock") || salesNew.getMetalUsed().equalsIgnoreCase("Silver Loose Stock"))
		{
			BigDecimal oldgrwt = salesOld.getGrossWeight();
			BigDecimal newgrwt=salesNew.getGrossWeight();
			int oldqty = salesOld.getSoldQty();
			BigDecimal diffgrwt = newgrwt.subtract(oldgrwt);
			int diffQty = salesNew.getSoldQty() - oldqty;
			if(diffgrwt.compareTo(itemList.get(0).getGrossWeight()) == 1 ){
				errors.rejectValue("grossWeight", "catloose.grossweight.zero", "Closing Stock Is Not Sufficient To Update");
			}
			if(diffQty > itemList.get(0).getQty()){
				errors.rejectValue("soldQty", "catloose.grossweight.zero", "Closing Stock Is Not Sufficient To Update");
			}
		}
		
		/** Row 2*/
		if (!iCode1.isEmpty()) {
			if (itemList1 == null || itemList1.isEmpty()) {
				errors.rejectValue("itemCode1", "iCode1.invalid","ItemCode on Row 2 is invalid");
				
			}
			else {					
				for (int i = 0; i < itemList1.size(); i++) {
					
					ItemMaster imast1 = (ItemMaster) itemList1.get(i);
					if (imast1 instanceof ItemMaster) {
						itemDetails = (ItemMaster) imast1;
						total_grossWt1 = itemDetails.getTotalGrossWeight();
					}
				}
				if(iCode1.compareToIgnoreCase("IT100011") == 0 || iCode1.compareToIgnoreCase("IT100012") == 0 ){
					
					BigDecimal diffgrwt2 = total_grossWt1.add(salesOld.getGrossWeight1());
					BigDecimal finalgrwt2 = diffgrwt2.subtract(salesNew.getGrossWeight1());
					
					if(finalgrwt2.signum() < 0){
						errors.rejectValue("grossWeight1", "loose.grossweight1.zero", "Loose Metal Stock Not Available for Row 2 ItemCode");
						
					}						
				}

			}

		}
		
		if(salesNew.getMetalUsed1().equalsIgnoreCase("Gold Loose Stock") || salesNew.getMetalUsed1().equalsIgnoreCase("Silver Loose Stock"))
		{
			BigDecimal oldgrwt = salesOld.getGrossWeight1();
			BigDecimal newgrwt=salesNew.getGrossWeight1();
			int oldqty = salesOld.getSoldQty1();
			BigDecimal diffgrwt = newgrwt.subtract(oldgrwt);
			int diffQty = salesNew.getSoldQty1() - oldqty;
			if(diffgrwt.compareTo(itemList1.get(0).getGrossWeight()) == 1 ){
				errors.rejectValue("grossWeight1", "catloose.grossweight.zero", "Closing Stock Is Not Sufficient To Update");
			}
			if(diffQty > itemList1.get(0).getQty()){
				errors.rejectValue("soldQty1", "catloose.grossweight.zero", "Closing Stock Is Not Sufficient To Update");
			}
		}
		/** Row 3*/
		if (!iCode2.isEmpty()) {
			if (itemList2 == null || itemList2.isEmpty()) {
				errors.rejectValue("itemCode2", "iCode2.invalid","ItemCode on Row 3 is invalid");
				
			}
			else {					
				for (int i = 0; i < itemList2.size(); i++) {
					
					ItemMaster imast2 = (ItemMaster) itemList2.get(i);
					if (imast2 instanceof ItemMaster) {
						itemDetails = (ItemMaster) imast2;
						total_grossWt2 = itemDetails.getTotalGrossWeight();
					}
				}
				if(iCode2.compareToIgnoreCase("IT100011") == 0 || iCode2.compareToIgnoreCase("IT100012") == 0 ){
					
					BigDecimal diffgrwt3 = total_grossWt2.add(salesOld.getGrossWeight2());
					BigDecimal finalgrwt3 = diffgrwt3.subtract(salesNew.getGrossWeight2());
					
					if(finalgrwt3.signum() < 0){
						errors.rejectValue("grossWeight2", "loose.grossweight2.zero", "Loose Metal Stock Not Available for Row 3 ItemCode");
					}						
				}
			}

		}
		if(salesNew.getMetalUsed2().equalsIgnoreCase("Gold Loose Stock") || salesNew.getMetalUsed2().equalsIgnoreCase("Silver Loose Stock"))
		{
			BigDecimal oldgrwt = salesOld.getGrossWeight2();
			BigDecimal newgrwt=salesNew.getGrossWeight2();
			int oldqty = salesOld.getSoldQty2();
			BigDecimal diffgrwt = newgrwt.subtract(oldgrwt);
			int diffQty = salesNew.getSoldQty2() - oldqty;
			if(diffgrwt.compareTo(itemList2.get(0).getGrossWeight()) == 1 ){
				errors.rejectValue("grossWeight2", "catloose.grossweight.zero", "Closing Stock Is Not Sufficient To Update");
			}
			if(diffQty > itemList2.get(0).getQty()){
				errors.rejectValue("soldQty2", "catloose.grossweight.zero", "Closing Stock Is Not Sufficient To Update");
			}
		}
		
		/** Row 4*/
		if (!iCode3.isEmpty()) {
			if (itemList3 == null || itemList3.isEmpty()) {
				errors.rejectValue("itemCode3", "iCode3.invalid","ItemCode on Row 4 is invalid");
			
			}
			else {					
				for (int i = 0; i < itemList3.size(); i++) {
					
					ItemMaster imast3 = (ItemMaster) itemList3.get(i);
					if (imast3 instanceof ItemMaster) {
						itemDetails = (ItemMaster) imast3;
						total_grossWt3 = itemDetails.getTotalGrossWeight();
					}
				}
				if(iCode3.compareToIgnoreCase("IT100011") == 0 || iCode3.compareToIgnoreCase("IT100012") == 0 ){
					
					BigDecimal diffgrwt4 = total_grossWt3.add(salesOld.getGrossWeight3());
					BigDecimal finalgrwt4 = diffgrwt4.subtract(salesNew.getGrossWeight3());
					
					if(finalgrwt4.signum() < 0){
						errors.rejectValue("grossWeight3", "loose.grossweight3.zero", "Loose Metal Stock Not Available for Row 4 ItemCode");						
					}						
				}
			}

		}
		if(salesNew.getMetalUsed3().equalsIgnoreCase("Gold Loose Stock") || salesNew.getMetalUsed3().equalsIgnoreCase("Silver Loose Stock"))
		{
			BigDecimal oldgrwt = salesOld.getGrossWeight3();
			BigDecimal newgrwt=salesNew.getGrossWeight3();
			int oldqty = salesOld.getSoldQty3();
			BigDecimal diffgrwt = newgrwt.subtract(oldgrwt);
			int diffQty = salesNew.getSoldQty3() - oldqty;
			if(diffgrwt.compareTo(itemList3.get(0).getGrossWeight()) == 1 ){
				errors.rejectValue("grossWeight3", "catloose.grossweight.zero", "Closing Stock Is Not Sufficient To Update");
			}
			if(diffQty > itemList3.get(0).getQty()){
				errors.rejectValue("soldQty2", "catloose.grossweight.zero", "Closing Stock Is Not Sufficient To Update");
			}
		}
		/** Row 5*/
		if (!iCode4.isEmpty()) {
			if (itemList4 == null || itemList4.isEmpty()) {
				errors.rejectValue("itemCode4", "iCode4.invalid","ItemCode on Row 5 is invalid");
			}
			else {					
				for (int i = 0; i < itemList4.size(); i++) {
					
					ItemMaster imast4 = (ItemMaster) itemList4.get(i);
					if (imast4 instanceof ItemMaster) {
						itemDetails = (ItemMaster) imast4;
						total_grossWt4 = itemDetails.getTotalGrossWeight();
					}
				}
				if(iCode4.compareToIgnoreCase("IT100011") == 0 || iCode4.compareToIgnoreCase("IT100012") == 0 ){
					
					BigDecimal diffgrwt5 = total_grossWt4.add(salesOld.getGrossWeight4());
					BigDecimal finalgrwt5 = diffgrwt5.subtract(salesNew.getGrossWeight4());
					
					if(finalgrwt5.signum() < 0){
						errors.rejectValue("grossWeight4", "loose.grossweight4.zero", "Loose Metal Stock Not Available for Row 5 ItemCode");
					}						
				}
			}

		}
		if(salesNew.getMetalUsed4().equalsIgnoreCase("Gold Loose Stock") || salesNew.getMetalUsed4().equalsIgnoreCase("Silver Loose Stock"))
		{
			BigDecimal oldgrwt = salesOld.getGrossWeight4();
			BigDecimal newgrwt=salesNew.getGrossWeight4();
			int oldqty = salesOld.getSoldQty4();
			BigDecimal diffgrwt = newgrwt.subtract(oldgrwt);
			int diffQty = salesNew.getSoldQty4() - oldqty;
			if(diffgrwt.compareTo(itemList4.get(0).getGrossWeight()) == 1 ){
				errors.rejectValue("grossWeight4", "catloose.grossweight.zero", "Closing Stock Is Not Sufficient To Update");
			}
			if(diffQty > itemList4.get(0).getQty()){
				errors.rejectValue("soldQty4", "catloose.grossweight.zero", "Closing Stock Is Not Sufficient To Update");
			}
		}
				
	}
	/****** Row 1 Clear **********/
	public Sales clearR1(Sales sales){
		sales.setItemName("");
		sales.setBullionRate(initZero2);
		sales.setStone("");
		sales.setMcByGrams(initZero3);
		sales.setLessAmount(initZero2);
		sales.setCategoryName("");
		sales.setStoneCost(initZero2);
		sales.setWastageByGrams(initZero3);
		sales.setLessPercentage(initZero2);
		sales.setManufacturerSeal("");
		sales.setTotalPieces(0);
		sales.setBullionType("");
		sales.setValueAdditionCharges(initZero2);
		sales.setVtax(initZero2);
		sales.setNumberOfPieces(0);
		sales.setGrossWeight(initZero3);
		sales.setAmountAfterLess(initZero2);
		return sales;
	}/****** Row 2 Clear **********/
	public Sales clearR2(Sales sales){
		sales.setItemName1("");
		sales.setBullionRate1(initZero2);
		sales.setStone1("");
		sales.setMcByGrams1(initZero3);
		sales.setLessAmount1(initZero2);
		sales.setCategoryName1("");
		sales.setStoneCost1(initZero2);
		sales.setWastageByGrams1(initZero3);
		sales.setLessPercentage1(initZero2);
		sales.setManufacturerSeal1("");
		sales.setTotalPieces1(0);
		sales.setBullionType1("");
		sales.setValueAdditionCharges1(initZero2);
		sales.setVtax1(initZero2);
		sales.setNumberOfPieces1(0);
		sales.setGrossWeight1(initZero3);
		sales.setAmountAfterLess1(initZero2);
		return sales;		
	}/****** Row 3 Clear **********/
	public Sales clearR3(Sales sales){
		sales.setItemName2("");
		sales.setBullionRate2(initZero2);
		sales.setStone2("");
		sales.setMcByGrams2(initZero3);
		sales.setLessAmount2(initZero2);
		sales.setCategoryName2("");
		sales.setStoneCost2(initZero2);
		sales.setWastageByGrams2(initZero3);
		sales.setLessPercentage2(initZero2);
		sales.setManufacturerSeal2("");
		sales.setTotalPieces2(0);
		sales.setBullionType2("");
		sales.setValueAdditionCharges2(initZero2);
		sales.setVtax2(initZero2);
		sales.setNumberOfPieces2(0);
		sales.setGrossWeight2(initZero3);
		sales.setAmountAfterLess2(initZero2);
		return sales;		
	}/****** Row 4 Clear **********/
	public Sales clearR4(Sales sales){
		sales.setItemName3("");
		sales.setBullionRate3(initZero2);
		sales.setStone3("");
		sales.setMcByGrams3(initZero3);
		sales.setLessAmount3(initZero2);
		sales.setCategoryName3("");
		sales.setStoneCost3(initZero2);
		sales.setWastageByGrams3(initZero3);
		sales.setLessPercentage3(initZero2);
		sales.setManufacturerSeal3("");
		sales.setTotalPieces3(0);
		sales.setBullionType3("");
		sales.setValueAdditionCharges3(initZero2);
		sales.setVtax3(initZero2);
		sales.setNumberOfPieces3(0);
		sales.setGrossWeight3(initZero3);
		sales.setAmountAfterLess3(initZero2);
		return sales;		
	}/****** Row 5 Clear **********/
	public Sales clearR5(Sales sales){
		sales.setItemName4("");
		sales.setBullionRate4(initZero2);
		sales.setStone4("");
		sales.setMcByGrams4(initZero3);
		sales.setLessAmount4(initZero2);
		sales.setCategoryName4("");
		sales.setStoneCost4(initZero2);
		sales.setWastageByGrams4(initZero3);
		sales.setLessPercentage4(initZero2);
		sales.setManufacturerSeal4("");
		sales.setTotalPieces4(0);
		sales.setBullionType4("");
		sales.setValueAdditionCharges4(initZero2);
		sales.setVtax4(initZero2);
		sales.setNumberOfPieces4(0);
		sales.setGrossWeight4(initZero3);
		sales.setAmountAfterLess4(initZero2);
		return sales;		
	}
}






