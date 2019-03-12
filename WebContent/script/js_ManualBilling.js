
$(document).ready(function(){
	ManualBullingedel();
	ManualBillingaddrow();
	grossToNetweight();
	
	$("#insert").bind('click',function(){
		return formValidate();	
	});
	$("#update").bind('click',function(){
		return formValidate();	
	});
	$('[id^=mBillamount]').keyup(MB_Subtotal_Calc);
	$("#subTotal").keyup(totalAmt_Calc);
	$("#vatAmount").keyup(totalAmt_Calc);
	$("#cashAmount").keyup(totalAmtReceived_Calc);
	$("#cardAmount").keyup(totalAmtReceived_Calc);
	$("#chequeAmount").keyup(totalAmtReceived_Calc);
	$("#voucherAmount").keyup(totalAmtReceived_Calc);
});

//** On Load hide Blank Rows in Form**/	
$(".isDeleted").each(function(){
	var delClass = $(this).val();
	if(delClass == "true"){			
		$(this).parents(".staticRowMB").hide();
	}
});

//**** on Key Up copy Gross Weight value to Net Weight *******/
function grossToNetweight(){
	$('[id^=grossWeight]').keyup(function() {
		var rowId = $(this).attr('title');
		var grossVal = $(this).val();
		$("#netWeight"+rowId).val(parseFloat(grossVal).toFixed(3));
	});
}

//************  for Add Dynamic Row **********/
function ManualBillingaddrow(){
	
	var counter = 1;
	
		$("#addItemMb").click(function(){
			
			var URIloc = $(location).attr('href');
			var uripath = URIloc.split('/')[4];
			
			if(uripath.indexOf("viewManualSales.htm") >= 0){
				counter = $("[id^=description]").size();
			}else{
				counter = $("[id^=description]").size();				
			}
			var rowId = '<td>'+counter+'</td>';
			var Mbdelbutton = '<td><input type="button" name="delete" class="delMB noEnterSubmit" title="'+counter+'" style="width:20px;"/></td>';
			var MbDescription = '<td><input type="text" name="manBillRowList['+counter+'].description" id="description'+counter+'" class="MbDescription noEnterSubmit" title="'+counter+'" /></td>';
			var Mbqty = '<td><input type="text" name="manBillRowList['+counter+'].qty" id="qty'+counter+'" value="0" class="MbQtyWght noEnterSubmit cal_dep"  onkeyup="extractNumber(this,0,true);" title="'+counter+'" /></td>';
			var MbGross_weight = '<td><input type="text" name="manBillRowList['+counter+'].grossWeight" id="grossWeight'+counter+'" value="0.00" class="MbQtyWght noEnterSubmit cal_dep"  onkeyup="extractNumber(this,3,true);" title="'+counter+'" /></td>';
			var MbNet_weight = '<td><input type="text" name="manBillRowList['+counter+'].netWeight" id="netWeight'+counter+'" class="MbQtyWght noEnterSubmit cal_dep" value="0.00" readonly="readonly"  onkeyup="extractNumber(this,3,true);" title="'+counter+'" /></td>';
			var MbAmt = '<td><input type="text" name="manBillRowList['+counter+'].amount" id="mBillamount'+counter+'"value="0.00"  class="MbAmt noEnterSubmit cal_dep"  onkeyup="extractNumber(this,2,true);" title="'+counter+'" /></td>';
			var displayNone1 = '<td style="display:none"><input name="manBillRowList['+counter+'].deleted" id="deleted'+counter+'" value="false" class="isDeleted noEnterSubmit" title="'+counter+'" />';
			//var displayNone2 = '<input type="hidden" name="manBillRowList['+counter+'].manBillRowListId"/></td>';
			var rows = '<tr class="staticRowMB">' + rowId + Mbdelbutton + MbDescription + Mbqty + MbGross_weight + MbNet_weight+ MbAmt + displayNone1 +'</tr>';
			$("tr.staticRowMB:last").after(rows);
			$("input.MbDescription:last").focus();
			counter++;
			ManualBullingedel();
			totalAmt_Calc();
			totalAmtReceived_Calc();
			grossToNetweight();
			$('[id^=mBillamount]').keyup(MB_Subtotal_Calc);
			$("input").focus(ReadonlyFocus);
			
		});	
}

//** for Row Delete button *****/
function ManualBullingedel() {
	
	$(".delMB").click(function() {
			
			var thisRow = $(".staticRowMB").length;
			if(thisRow > 1) {
				$(this).parents(".staticRowMB").find("[id^=deleted]").val("true");
				$(this).parents(".staticRowMB").find("[id^=mBillamount]").val("0.00");
				MB_Subtotal_Calc();
				$(this).parents(".staticRowMB").hide();
			}	
		});
}

//** Sub Total Calculation. ( Sum of all Amount Fields ) */
function MB_Subtotal_Calc(){
	
	var siz = $("[id^=mBillamount]").size();
	var subTotalAmt = 0;
	for(var i=0;i<siz;i++) {
		var amount = $("#mBillamount"+i).val();
		if(amount == null || amount.length == 0 || amount == '.' || amount == '-' || amount == 'NaN' || amount == 'undefined'){
			amount = 0;
		}
		subTotalAmt = parseFloat(subTotalAmt) + parseFloat(amount);
	}
	$("#subTotal").val(subTotalAmt.toFixed(2));
	totalAmt_Calc();
}

/** Total Amount Calculation. (sum of Sub-total and Vat Amount)  */
function totalAmt_Calc(){
	
	var SubTotAmt = $("#subTotal").val();
	var vatAmt =  $("#vatAmount").val();
	var totalAmt = 0;
		
	if(SubTotAmt == null || SubTotAmt.length == 0 || SubTotAmt == '.' || SubTotAmt == '-' ||  SubTotAmt == 'NaN' || SubTotAmt == 'undefined'){
		SubTotAmt = 0;
	}
	if( vatAmt == null || vatAmt.length == 0 || vatAmt == '.' || vatAmt == '-' || vatAmt == 'NaN' || vatAmt == 'undefined'){
		vatAmt = 0;
	}
	totalAmt = parseFloat(SubTotAmt) + parseFloat(vatAmt);
	$("#totalAmount").val(totalAmt.toFixed(2));
}

/** Total Amount Received Calculation. (Card Amount + Cheque Amount + Gift Voucher Amount + Cash)  */
function totalAmtReceived_Calc(){
	
	var cashAmount = $("#cashAmount").val();
	var cardAmount = $("#cardAmount").val();
	var chequeAmount = $("#chequeAmount").val();
	var voucherAmount =  $("#voucherAmount").val();
	var totalAmtReceived = 0;
		
	if( cashAmount == null || cashAmount.length == 0 || cashAmount == '.' || cashAmount == '-' ||cashAmount == 'NaN' || cashAmount == 'undefined'){
		cashAmount = 0;
	}
	if(cardAmount == null || cardAmount.length == 0 ||  cardAmount == '.' || cardAmount == '-' ||  cardAmount == 'NaN' || cardAmount == 'undefined'){
		cardAmount = 0;
	}
	if(chequeAmount == null || chequeAmount.length == 0 ||  chequeAmount == '.' || chequeAmount == '-' || chequeAmount == 'NaN' || chequeAmount == 'undefined'){
		chequeAmount = 0;
	}
	if(voucherAmount == null || voucherAmount.length == 0 ||  voucherAmount == '.' || voucherAmount == '-' || voucherAmount == 'NaN' || voucherAmount == 'undefined'){
		voucherAmount = 0;
	}
	
	totalAmtReceived = parseFloat(cashAmount)+parseFloat(cardAmount)+parseFloat(chequeAmount)+parseFloat(voucherAmount);
	$("#totalAmtReceived").val(totalAmtReceived.toFixed(2));
}

// Form Validations
function formValidate(){
	
	var mbBillNo = $("#mBillNo").val();
	var mbCustomerName = $("#customerName").val();
	var mbBoardRate = $("#boardRate").val();
	var mbTotalAmount = $("#totalAmount").val();
	var mbDate = $("#mnulBllng").val();
	
	if(mbBillNo == null || mbBillNo.length == 0 || mbBillNo == 'NaN' || mbBillNo == 'undefined'){
		alert("Bill No Can't be Empty !");
		return false;
	}
	
	if(mbDate == null || mbDate.length == 0 || mbDate == 'NaN' || mbDate == 'undefined'){
		alert("Date Can't be Empty !");
		return false;
	}
	
	if(mbCustomerName == null || mbCustomerName.length == 0 || mbCustomerName == 'NaN' || mbCustomerName == 'undefined'){
		alert("Customer Name Can't be Empty !");
		return false;
	}
	if(mbBoardRate == null || mbBoardRate.length == 0 || mbBoardRate == 'NaN' || mbBoardRate == 'undefined'){
		alert("Board Rate Can't be Empty !");
		return false;
	}
	
	if(mbBoardRate <= 0){
		alert("Board Rate Field Cannot be ZERO or Negative !");
		return false;
	}
	
	if(mbTotalAmount == null || mbTotalAmount.length == 0 || mbTotalAmount == 'NaN' || mbTotalAmount == 'undefined'){
		alert("Total Amount Can't be Empty");
		return false;
	}
	
	if(mbTotalAmount <= 0){
		alert("Total Amount Field Cannot be ZERO or Negative !");
		return false;
	}
		
	var result = MBcheckDate();
	if(result == false){
		alert(" * Enter Valid Date !");
		return false;
	}
	
	if(($("#qty0").length == 0 || $("#qty0").val() <= 0) && ($("#grossWeight0").length == 0 || $("#grossWeight0").val() <= 0) && ($("#mBillamount0").length == 0 || $("#mBillamount0").val() <= 0)){
		alert("Atleast one Item detail is Required..!");
		$("#form_error").slideDown("fast");
		$("#form_error span").text(" Atleast one Item detail is Required !");
		return false;
	}
	return true;
}
// Date Validations
function MBcheckDate(){
	var currVal = $("#mnulBllng").val();
	var regexDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
	var dtArray = currVal.match(regexDatePattern);
	if(dtArray == null){
		return false;
	}
	var dtDay = dtArray[1];
	var dtMonth = dtArray[3];
	var dtYear = dtArray[5];
	if(dtMonth < 1 || dtMonth > 12){
		return false;
	}else if(dtDay < 1 || dtDay > 31){
		return false;
	}else if((dtMonth == 4 || dtMonth ==6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31){
		return false;
	}else if(dtMonth == 2){
		var isLeap = ( dtYear %4 ==0 && (dtYear % 100 != 0 || dtYear % 400 == 0) );
		if(dtDay > 29 || (dtDay == 29 && !isLeap)){
			return false;
		}
	}
	return true;
}

