$(document).ready(function(){
	
$("#date").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
		
		
	$("[id^=pritcode]").css("text-transform","uppercase");
	POSPurchaseReturnaddrow();
	POSPurchasReturnedel();	
	pospurchaseReturn();
	$("[id^=costRate]").keyup(POSPitemRetrieve).trigger('keyup');
	$("[id^=taxP]").keyup(POSPTotalTaxCalc).trigger('keyup');
	$('#insert').click(returnValidate);
	$('#update').click(returnValidate);
	
	var URIloc = $(location).attr('href');
	var uripath = URIloc.split('/')[4];
	var errorType = $("#errorType").val();
	if(uripath.indexOf("viewPOSPurchase.htm") >= 0 ){
		$("#insert").hide();
		$("#update").show();
		
	}
	if(uripath.indexOf("formPOSPurchaseReturn.htm") >= 0){
		$("#insert").show();
		$("#update").hide();
		$("#date").val(prettyDate);
	}
	if(errorType == "insertError") {
		$("#insert").show();
		$("#update").hide();
		$("#date").val(prettyDate);
	} if(errorType == "updateError") {
		$("#insert").hide();
		$("#update").show();
		
	}
	$(".rowStatus").each(function(){
		var rowClass = $(this).val();
		var rowNum = $(this).attr('title');
		if(rowClass == "Persisted"){			
			$("#pritcode"+rowNum).attr("readonly","readonly");
			}
	});
	/** On Load hide Blank Rows in Form**/	
	$(".isDeleted").each(function(){
		var delClass = $(this).val();
		if(delClass == "true"){			
			$(this).parents(".staticRowPR").hide();
		}
	});
});
/**************Pos Purchase Return Validation*************/
function returnValidate()
{
	

	var POPRpdate = $("#date").val();
	 var POPRsupplierName = $("#supplierName0").val();
	 var POPRgrandTotal = $("#grandAmt0").val();
	 if(POPRpdate == null  || POPRpdate.length == 0){
			alert("Date field Can't be Empty");
			return false;
		}
		if(POPRsupplierName == null  || POPRsupplierName.length == 0){
			alert("Supplier Name Can't be Empty");
			return false;
		}
		if(POPRgrandTotal <= 0){
			alert("Grand Total Amount Can't be Zero !");
			return false;
		}
	

	}
/** ***************** POSPurchase return add rows ****************************** */
function POSPurchaseReturnaddrow(){
	
	var counter = 1;
	
	
		$("#addItemPR").click(function(){
			var URIloc = $(location).attr('href');
			var uripath = URIloc.split('/')[4];
			
			if(uripath.indexOf("viewPOSPurchase.htm") >= 0){
				counter = $("[id^=purchaseAmt]").size();
			}
			else{
				counter = $("[id^=purchaseAmt]").size();				
			}
			var delbutton = '<td><input type="button" name="delete" class="delP" value="" style="width:20px;"/></td>';
			var Icode = '<td><input name="listpospurchase['+counter+'].barcode" type="text" title="'+counter+'" class="icode" id="pritcode'+ counter +'" value="" onkeydown="return (event.keyCode!=13);" /></td>';
			var categoryName = '<td><input name="listpospurchase['+counter+'].categoryName" type="text" title="'+counter+'" class="cname" id="categoryName'+ counter +'"  value="" onkeydown="return (event.keyCode!=13);" readOnly = readOnly/></td>';
			var itemName = '<td><input name="listpospurchase['+counter+'].itemName"  type="text" title="'+counter+'" id="itemName'+counter+'" value="" onkeydown="return (event.keyCode!=13);" readOnly = readOnly/></td>';
			var qty = '<td><input name="listpospurchase['+counter+'].qtySet" type="text" title="'+counter+'" id="qtySet' + counter + '" class="iname QtyPcs cal_dep qty_pcs_tax" value="0" onkeydown="return (event.keyCode!=13);" onkeypress="return ValidateNumberKeyPress(this, event);" readOnly = readOnly/></td>';
			var piecesPerSet = '<td><input name="listpospurchase['+counter+'].piecesPerSet" type="text" title="'+counter+'" id="piecesPerSet' + counter + '" class="iname piecesPerSet cal_dep qty_pcs_tax" value="0" onkeydown="return (event.keyCode!=13);" onkeypress="return ValidateNumberKeyPress(this, event);" readOnly = readOnly/></td>';
			var costRate = '<td><input name="listpospurchase['+counter+'].costRate" type="text" title="'+counter+'" id="costRate' + counter + '" class="iname posrate cal_dep" value="0.00" onkeydown="return (event.keyCode!=13);" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);"/ ></td>';
			var tax = '<td><input name="listpospurchase['+counter+'].taxPercantage" type="text" title="'+counter+'" id="taxP' + counter + '" class="iname cal_dep qty_pcs_tax" value="0.00" onkeydown="return (event.keyCode!=13);" onkeypress="return ValidateNumberKeyPress(this, event);"  onkeyup="extractNumber(this,2,true);"/ ></td>';
			var amt = '<td><input name="listpospurchase['+counter+'].purchaseAmt" type="text" title="'+counter+'" id="purchaseAmt'+ counter +'" class="iname posrate cal_dep " value="0.00" onkeydown="return (event.keyCode!=13);" onkeypress="return ValidateNumberKeyPress(this, event);"  readOnly = readOnly/ ></td>';			
			var deleted = '<td style="display:none"><input name="listpospurchase['+counter+'].deleted" id="deleted'+counter+'" title="'+counter+'" class="isDeleted" value="false" /></td>';
			var rows = '<tr class="staticRowPR">'+ delbutton + Icode + categoryName + itemName + qty + piecesPerSet + costRate + tax  +amt + deleted +'<input type="hidden" name="listpospurchase['+counter+'].purchaeItemID" />'+'</tr>';
			
			$("tr.staticRowPR:last").after(rows);
			$("[id^=pritcode]").css("text-transform","uppercase");
			
			POSPurchasReturnedel();
			$("input").focus(ReadonlyFocus);
			pospurchaseReturn();
			$("[id^=costRate]").keyup(POSPitemRetrieve);
			$("[id^=taxP]").keyup(POSPTotalTaxCalc);
			counter++;
		});	
		
}
/*******pos purchase return searching stock details********/
function pospurchaseReturn(){
	
	$("[id^=pritcode]").focusout(function(){
	
	var vlue=$(this).val();
	var name = $(this).attr("title");
	$("[id^=pritcode]").each(function(){
			var dIcodeattr =  $(this).attr("title");
			var dIcodeVal =  $("#pritcode"+dIcodeattr).val();
			var isDeleted = $("#deleted"+dIcodeattr).val();
			
			if(dIcodeattr != name){
				if(isDeleted != "true"){
					if(vlue.toLowerCase() == dIcodeVal.toLowerCase()){
						
						$("#form_error").slideDown("fast");
						$("#form_error span").text("*Item code is already Scanned.");
						$("#pritcode"+dIcodeattr).selected();
						return false;
					}
				}
			}
     });
	$.ajax({						
			type:'POST',
			url:'posbarcodesearch.htm',
			data: { posIcode : vlue },
			
			success:function(data) {
				if(data=='Invalid code')
					{
					$("#form_error").slideDown("fast");
					$("#form_error span").text("Invalid code");
					}
				else{
				data=data.replace('[','');
				data=data.replace(']','');
				var arr=new Array();																
				arr = data.split(',');
			
				$.each(arr,function(index, item1) 
				{
					
					switch(index)
					{
					case 1: {
						
						$("#categoryName"+name).val(item1);
						break;
						}
					case 2: {
						
						$("#itemName"+name).val(item1);
						
						break;
					}
					case 3: {
						
						$("#qtySet"+name).val(item1);
						break;
					}
					case 4: {
						$("#piecesPerSet"+name).val(item1);
						break;
					}
					case 5: {
                             
						$("#costRate"+name).val(item1);
						
						
						}
					}
					
				});
				
				POSPitemRetrieve();
				
			   }
			}		
			});
	
		$(this).css("text-transform","uppercase");
	});
	
	
}
/*************Delete dyamanic rows***************/
function POSPurchasReturnedel(){
	
	$(".delP").click(function(){
		
		var thisRow = $(".staticRowPR").length;
		if(thisRow == 1){
		
		}
		else if(thisRow > 1){
			$(this).parents(".staticRowPR").find("[id^=deleted]").val("true");
			//$(this).parents(".staticRowPR").find("[id^=pritcode]").val("");
			//$(this).parents(".staticRowPR").find("[id^=categoryName]").val("");
			//$(this).parents(".staticRowPR").find("[id^=itemName]").val("");
			$(this).parents(".staticRowPR").find("[id^=qtySet]").val("0");
			$(this).parents(".staticRowPR").find("[id^=piecesPerSet]").val("0");
			$(this).parents(".staticRowPR").find("[id^=costRate]").val("0.00");
			$(this).parents(".staticRowPR").find("[id^=taxP]").val("0.00");
			$(this).parents(".staticRowPR").find("[id^=purchaseAmt]").val('0.00');
			$(this).parents(".staticRowPR").hide();
			POSPcalc();
		}
		
	});
}
/******  Row Amount field  calculation *****/ 
function POSPitemRetrieve() {
	
	var rowNumbr  = $(this).attr('title');
	var costRate = $("#costRate"+rowNumbr).val();
	var qty=$("#qtySet"+rowNumbr).val();
	
	if(costRate.length == 0 || costRate == '' || costRate == null){
		costRate = 0;
	}
	if(qty.length == 0 || qty == '' || qty == null){
		qty = 0;
	}
	var amount;
	amount=costRate*qty;
	$("#purchaseAmt"+rowNumbr).val(parseFloat(amount).toFixed(2));	
	
	POSPcalc(); // Sub Total field
	
}
function POSPTotalValueCalc(){
	
	var subtotal = $("#subTotal0").val();
	if(subtotal == '' || subtotal == null){
		subtotal = 0;
	}	
	var totaltaxVal = $("#totalTax0").val();
	if(totaltaxVal == '' || totaltaxVal == null){
		totaltaxVal = 0;
	}
	var totalval = parseFloat(subtotal) + parseFloat(totaltaxVal);	
	$("#totalValue0").val(totalval.toFixed(2));
	
	var totalvalue = $("#totalValue0").val();
	var beforeRound = totalvalue;
	var afterRound = parseFloat(beforeRound).toFixed(0);
	var roundedVal = parseFloat(afterRound) - parseFloat(beforeRound);
	
	$("#roundOff0").val(parseFloat(roundedVal).toFixed(2));
	$("#grandAmt0").val(parseFloat(afterRound).toFixed(2));	
}
//******* Total tax Field Calculation ******* //
function POSPTotalTaxCalc(){
	
	var taxNum = $("[id^=taxP]").size();
	
		var taxAmt = 0;
		for(var i=0;i<taxNum;i++){
			
			var taxP = $("#taxP"+i).val();
			if(taxP.length == 0 || taxP == '' || taxP == null){
				taxP = 0;
			}
			
			var purAmt = $("#purchaseAmt"+i).val();
			if(purAmt.length == 0 || purAmt == '' || purAmt == null){
				purAmt = 0;
			}
			
			var t1 = parseFloat(taxP) / 100;
			var t2 = parseFloat(purAmt) * parseFloat(t1);
			taxAmt = parseFloat(taxAmt) + parseFloat(t2);
			
		}
		$("#totalTax0").val(taxAmt.toFixed(2));	
		POSPTotalValueCalc();	
}

//******* Sub Total Field Calculation ******* //
function POSPcalc(){
	
	
	
	var siz = $("[id^=purchaseAmt]").size();
		
		var sum = 0;
		for(var i=0;i<siz;i++){
			var purAmt = $("#purchaseAmt"+i).val();
			
			if(purAmt.length == 0 || purAmt == '' || purAmt == null){
				purAmt = 0;
			}
			sum = parseFloat(sum) + parseFloat(purAmt);
		}
		$("#subTotal0").val(sum.toFixed(2));	
		
		POSPTotalTaxCalc();	
	
} 
