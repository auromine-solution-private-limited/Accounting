$(document).ready(function(){
	
	$("#POSPdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});	
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
	
	$("#itemCategoryName").keyup(POPItemCategoryAuto).trigger('keyup');
	
	var URIloc = $(location).attr('href');
	var uripath = URIloc.split('/')[4];
	var errorType = $("#errorType").val();
	
	if(uripath.indexOf("viewPOSPurchase.htm") >= 0 ){
		$("#insert").hide();
		$("#update").show();
	}
	
	if(uripath.indexOf("formPOSpurchase.htm") >= 0){
		$("#insert").show();
		$("#update").hide();
		$("#POSPdate").val(prettyDate);
	}
	
	if(uripath.indexOf("formPOSPurchaseReturn.htm") >= 0){
		$("#insert").show();
		$("#update").hide();
		$("#POSPdate").val(prettyDate);
	}
	
	if(errorType == "insertError") {
		$("#insert").show();
		$("#update").hide();
		$("#POSPdate").val(prettyDate);
	}else if(errorType == "updateError") {
		$("#insert").hide();
		$("#update").show();
	}
	
	$("#insert").click(Validate); 
	$("#update").click(Validate); 
	$("#saveCategory").click(submitCategory);	
	$("#saveSupplier").click(submitSupplier);
	$("#saveItem").click(submitItem);
	$("#catPopup").click(clearCatPopup);	
	$("#suppPopup").click(clearSuppPopup);
	$("#itemPopup").click(clearItemPopup);
	
	$("#newItem").click(function(){
		$("#itemCategoryName").focusout(getDiscVat);	
		$("#itemCategoryName").keyup(POPItemCategoryAuto).trigger('keyup');
	});
		
	/** For making readonly of SOLD items */
	$(".itemStatus").each(function(){
		var rowNum1 = $(this).attr('title');
		var itemStatus = $("#itemStatus"+rowNum1).val();		
		if(itemStatus == "SOLD" || itemStatus == "Purchase Return"){
			$("#rowDelButton"+rowNum1).hide();
		}else{
			$("#rowDelButton"+rowNum1).show();
		}
	});

	/** For Invalid date Validations on Insert **/
	$("#insert").bind('click',function(){
		var result = POPcheckDate();
		if(result == false){
			alert("*Enter Valid Date");
			return false;
		}		
	});
	
	/** For Invalid date Validations on Insert **/
	$("#update").bind('click',function(){
		var result = POPcheckDate();
		if(result == false){
			alert("*Enter Valid Date");
			return false;
		}	
		$(".rowStatus").each(function(){
			var rowClass = $(this).val();
			var rowNum = $(this).attr('title');
			if(rowClass == "Persisted"){			
				$("#categoryName"+rowNum).attr("disabled",false);
				$("#itemName"+rowNum).attr("disabled",false);			
			}
		});
	});
	
	/** Moved From jquery-all-manuscript */ 
	POSPurchasedel();
	POPPurchaseaddrow();
	$("[id^=qtySet]").keyup(POPPurchaseAmtCalc);
	$("[id^=costRate]").keyup(POPPurchaseAmtCalc);
	$("[id^=marginP]").keyup(POPPurchaseAmtCalc);
	$("[id^=salesRate]").keyup(POPPurchaseSalesRCalc);
	$("[id^=taxP]").keyup(POP_Calc_Subfunctions).trigger('keyup');
	$("[id^=discountPer]").keyup(POP_Calc_Subfunctions).trigger('keyup');
	$("[id^=purchaseAmt]").keyup(POP_Calc_Subfunctions);
	$("#fixedPer0").keyup(fixDiscP);
	
	/** On Load hide Blank Rows in Form**/	
	$(".isDeleted").each(function(){
		var delClass = $(this).val();
		if(delClass == "true"){			
			$(this).parents(".staticRowP").hide();
		}
	});
	
	/** For Making category Name, Item Name readonly on Update Mode */
	$(".rowStatus").each(function(){
		var rowClass = $(this).val();
		var rowNum = $(this).attr('title');
		if(rowClass == "Persisted"){			
			$("#categoryName"+rowNum).attr("disabled",true);
			$("#itemName"+rowNum).attr("disabled",true);			
		}
	});
	
	/** Binding data to itemName **/
	POPItemAuto();
	
	/** For Setting Default Select if category value is 'Select' **/
	if($("#categoryName0").val() == ""){
		$("#itemName0").get(0).options[0] = new Option("Select");	
	}
	
	/*** new script added to allow only _ and - for customer name field on 25-2-13 after bug is posted ****/
	$('#supplierName').keyup(function() {
		 $(this).val($(this).val().replace(/[^A-Za-z0-9\s_-]/g,''));
	});
	
});

/** For Item Name POP UP Window Category name focus out get disc% & vat % **/
function getDiscVat(){
	
	var catNameVal = $("#itemCategoryName").val();
	if(catNameVal == null || catNameVal.length == 0){
		catNameVal = "";
	}
	$.ajax({						
			type:'GET',
			url:'getDiscVat.htm',
			data: { catNameVal : catNameVal },
			success:function(data) {
				
				data=data.replace('[','');
				data=data.replace(']','');
				var arr=new Array();																
				arr = data.split(',');
				
				$.each(arr,function(index, item1) 
				{
					switch(index)
					{
						case 0: {		
							if(item1 == null || item1.length == 0){
								$("#itemDiscP").val("0.00");
								$("#itemVatP").val("0.00");
							}else{
								$("#itemDiscP").val(item1);
							}						
							break;
						}
						case 1: {
							$("#itemVatP").val(item1);							
							break;
						}		
					}
				});
			}
	});
}

/** Form Validation **/
function Validate(){
	
	checkIsEmpty(); // empty field auto zero before validation
	
	var POPinvoiceNO = $("#invoiceNo0").val(); 
	var POPsupplierName = $("#supplierName0").val();
	var POPpdate = $("#POSPdate").val();
	var POPgrandTotal = $("#grandAmt0").val();
	
	if(POPinvoiceNO == null  || POPinvoiceNO.length == 0){
		alert("Purchase Invoice NO Can't be Empty");
		return false;
	}	
	if(POPpdate == null  || POPpdate.length == 0){
		alert("Date field Can't be Empty");
		return false;
	}
	if(POPsupplierName == null  || POPsupplierName.length == 0){
		alert("Please Select Supplier Name");
		return false;
	}
	if(POPgrandTotal <= 0){
		alert("Grand Total Amount Can't be Zero !");
		return false;
	}
}

// clear Category pop up window 
function clearCatPopup(){
	 $("#categoryName").val("");
	 $("#DiscountPercentage").val("0.00");
	 $("#vatPercentage").val("0.00");
	 $("#description").val("");
	 $("#POSPCatErrorMsg").html("");
}

//*********** Category Creation POP Window ****************************//
function submitCategory() {
		
	var catName = $("#categoryName").val();
	var discper = $("#DiscountPercentage").val();
	var vatper = $("#vatPercentage").val();
	var desc = $("#description").val();
	
	if(discper == null || discper.length == 0){
		discper = 0.00;		
	}
	if(vatper == null || vatper.length == 0){
		vatper = "";
	}
	if(desc == null || desc.length == 0){
		desc = 0.00;
	}	
	
	if(catName == null || catName.length == 0){
		catName = "";
		$("#POSPCatErrorMsg").html('<span class="error"> * Catogory Name cannot empty </span>');
		return false;
	}else 
	{
		// Catogory existing check server side validation through ajax
		$.ajax({
			type:'GET',
			url:'POSPCatValidate.htm',
			data : {catName : catName},
			success : function(data) {
				if(data == "true"){
					$("#POSPCatErrorMsg").html('<span class="error"> * Catogory Name already exist.</span>');
					return false;
				}else{
					// Save POS Category pop up
					$.ajax({
						type:'POST',
						url:'POScategoryPOPUP.htm',
						data : {catName : catName, discper : discper, vatper : vatper, desc: desc},
						success : function(data){
							$('input.categorytext').val('');
							  $('#mask, .window').hide();
							  $("[id^=categoryName]").append("<option value='"+catName+"'>"+catName+"</option>");
							  $("#tempCategoryName").append("<option value='"+catName+"'>"+catName+"</option>");
							  alert("'"+catName+"' "+"Added To Category");
						},
						error : function(xmlHttpRequest, textStatus, errorThrown){
								if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
									return;
						},	
						
					});// Save POS Category pop up ends here
				}			  				
			},
			error : function(xmlHttpRequest, textStatus, errorThrown){
					if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
						return;
			},	
			
		});	
	}
}

//clear Supplier pop up window
function clearSuppPopup(){
	$("#supplierName").val("");
	$("#openingBal").val("0.00");
	$("#Address1").val("");
	$("#phoneNumber").val("");
	$("#tinNumber").val(""); 
	$("#POSPSuppErrorMsg").html("");
	
}

//*********** Supplier Creation POP Window ****************************//
function submitSupplier() {
	
	var suppName = $("#supplierName").val();
	var openingBal = $("#openingBal").val();
	var openingType = $("#openingType").val();
	var address = $("#Address1").val();
	var phoneNumber = $("#phoneNumber").val();
	var tinNumber = $("#tinNumber").val();
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var suppCreatedDate = myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
	
	if(suppName == null || suppName.length == 0){
		suppName = "";
		$("#POSPSuppErrorMsg").html('<span class="error"> * Supplier Name cannot be Empty.</span>');
		return false;
	}else if(suppName != null){
		// SupplierName existing check server side validation through ajax
		$.ajax({
			type:'GET',
			url:'POSPSuppValidate.htm',
			data : {suppName : suppName},
			success : function(data) {
				if(data == "true"){
					$("#POSPSuppErrorMsg").html('<span class="error"> * Supplier Name already Exist.</span>');
					return false;
				}else {
					// Save POS Supplier Pop up			
					$.ajax({
						type:'POST',
						url:'POSsupplierPOPUP.htm',
						data : { suppCreatedDate : suppCreatedDate, suppName : suppName, openingBal : openingBal, openingType : openingType, address : address,phoneNumber : phoneNumber,tinNumber : tinNumber, City : ""},
						success : function(sdata) {
							  $('#mask, .window').hide();
							  $("#supplierName0").append("<option value='"+suppName+"'>"+suppName+"</option>");
							  $("#supplierName0").val(suppName);
							  alert("'"+suppName+"' "+"Supplier Detail Added");
						},
						error : function(xmlHttpRequest, textStatus, errorThrown) {			
								if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
								return;
						},	
						
					});	// Save POS Supplier Pop up	ends here
				}			  				
			},
			error : function(xmlHttpRequest, textStatus, errorThrown){
					if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
						return;
			},	
		});
	}
	
	if(openingBal == null || openingBal.length == 0){
		openingBal = 0.00;
	}
	
	if(address == null || address.length == 0){
		address = "";
	}
}

//clear Supplier pop up window
function clearItemPopup(){
	$("#popIName").val("");
	$("#itemCategoryName").val("");
	$("#itemDiscP").val("0.00");
	$("#itemVatP").val("0.00");
	$("#itemCompanyName").val("");	
	$("#POSPItemErrorMsg").html("");
	$("#itemCategoryName").keyup(POPItemCategoryAuto).trigger('keyup');
}

//*********** Item Creation Pop Up Window - Category Name Auto Complete ****************************//
function POPItemCategoryAuto(){
	
	var POPItemcnameVal = $("#itemCategoryName").val();	
	
	$.ajax({			
		type:'GET',
		url:'categoryName_Auto.htm',
		data:{cnamePart:POPItemcnameVal},
		success:function(Cipopdata){
			
			var Cipoparr=new Array();
			Cipopdata = Cipopdata.replace('[','');
			Cipopdata = Cipopdata.replace(']','');
			var CipopfinalData="";
			
			Cipoparr = Cipopdata.split(',');	
			$.each(Cipoparr,function(Cipopindex, Cipopitem) {
		      					        
				var Cipopresult = Cipopitem.replace(/^\s+|\s+$/g,'').replace(/\s+/g,' ');
				if(Cipopitem != ']' && Cipopitem !=',' && Cipopitem !=' ' && Cipopitem !='' && Cipopitem != '[' )
				{
					CipopfinalData = CipopfinalData.concat(Cipopresult);
					CipopfinalData = CipopfinalData.concat(":");
				}				
			});
			CipopfinalData = CipopfinalData.concat(":");				
			var CipopiVal = CipopfinalData.split(":");
			$("#itemCategoryName").autocomplete('');
			$("#itemCategoryName").autocomplete(CipopiVal);			
			},
			error:function(xmlHttpRequest, textStatus, errorThrown){
			if(xmlHttpRequest.readyState=0 || xmlHttpRequest.status == 0)
				return;
		},
		
	});	
}

//*********** Item Creation POP Window ****************************//
function submitItem() {
	var popIName = $("#popIName").val();	
	var itemCategoryName = $("#itemCategoryName").val();
	//var catName =  $("#itemCategoryName").val();
	var itemDiscP = $("#itemDiscP").val();
	var itemVatP =$("#itemVatP").val();
	var itemCompanyName = $("#itemCompanyName").val();
	
	if(itemDiscP == null || itemDiscP.length == 0){
		itemDiscP = 0.00;
	}
	
	if(itemVatP == null || itemVatP.length == 0){
		itemVatP = 0.00;
	}
	
	if(itemCompanyName == null || itemCompanyName.length == 0){
		itemCompanyName = "";
	}
	
	if(popIName == null || popIName.length == 0){
		popIName = "";
		$("#POSPItemErrorMsg").html('<span class="error"> * Item Name cannot be Empty.</span>');
		return false;
	}
	
	if(itemCategoryName == null || itemCategoryName.length == 0){
		itemCategoryName = "";
		//catName = "";
		$("#POSPItemErrorMsg").html('<span class="error"> * Category Name cannot be Empty.</span>');
		return false;
	}	
	
	if(popIName != null){
		//  Item Name existing check server side validation through ajax
		$.ajax({
			type:'GET',
			url:'POSPItemNameValidate.htm',
			data : {popIName : popIName},
			success : function(idata) {
				if(idata == "true"){
					$("#POSPItemErrorMsg").html('<span class="error"> * Item Name already Exist.</span>');					
					return false;
				}else if(itemCategoryName != null){
					//  category existing check server side validation through ajax
					$.ajax({
						type:'GET',
						url:'POSPCatValidate.htm',
						data : { catName : itemCategoryName},
						success : function(cdata) {
							if(cdata == "false") {
								$("#POSPItemErrorMsg").html('<span class="error"> * Invalid Category Name.</span>');
								return false;
							}else {
								// Save POS Item Creation Pop up
								$.ajax({
									type:'GET',
									url:'POSItemPOPUP.htm',
									data : { popIName : popIName, itemCategoryName : itemCategoryName, itemDiscP : itemDiscP, itemVatP : itemVatP, itemCompanyName : itemCompanyName},
									success : function(sidata) {
										  $('#mask, .window').hide();
										  $(".cname").each(function(){
												var catName = $(this).val();
												var rNum = $(this).attr('title');
												if(catName == itemCategoryName){
													$("#itemName"+rNum).append("<option value='"+popIName+"'>"+popIName+"</option>");
												}
										  });
										  alert("'"+popIName+"' "+"Item Name Created");
										  POPItemAuto();
									},
									error : function(xmlHttpRequest, textStatus, errorThrown) {			
											if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
												return;
									},	
									
								});	// Save POS Item Creation Pop up ends here
							}							
						},
						error : function(xmlHttpRequest, textStatus, errorThrown) {
								if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
									return;
						},	
					}); 
				} // Category check ends here 				
			},
			error : function(xmlHttpRequest, textStatus, errorThrown){
					if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
						return;
			},	
			
		});
	}
}

function checkIsEmpty(){
	$('input.cal_dep').each(function(){
		var TempVal = $(this).val();
		if(TempVal.length === 0 || TempVal === '' || TempVal === null){
			$(this).val("0");			
		}
	});
}

function POPcheckDate(){
	var currVal = $("#POSPdate").val();
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

/************************       POS Purchase Add / Delete row           *******************************/
function POPPurchaseaddrow(){
	
	var counter = 1;
	
		$("#addItemP").click(function(){
			
			var URIloc = $(location).attr('href');
			var uripath = URIloc.split('/')[4];
			
			if(uripath.indexOf("viewPOSPurchase.htm") >= 0){
				counter = $("[id^=purchaseAmt]").size();
			}else{
				counter = $("[id^=purchaseAmt]").size();				
			}
			
			var rowId = '<td>'+counter+'</td>';
			var delbutton = '<td><input type="button" name="delete" id="rowDelButton" class="delP" value="" style="width:20px;"  title="'+counter+'"/></td>';
			var categoryName = '<td><select name="listpospurchase['+counter+'].categoryName" title="'+counter+'" id="categoryName'+ counter +'" class="cname category iname" value="" onkeydown="return (event.keyCode!=13);"><option value="" label="Select">Select</option></select>';
			var itemName = '<td><select name="listpospurchase['+counter+'].itemName"  title="'+counter+'" id="itemName'+counter+'" class="category iname itemname" value="" onkeydown="return (event.keyCode!=13);" ><option value="" label="Select">Select</option></select>';
			var qty = '<td><input name="listpospurchase['+counter+'].qtySet" type="text" title="'+counter+'" id="qtySet' + counter + '" class="iname QtyPcs cal_dep qty_pcs_tax" value="0" onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,0,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>';
			var piecesPerSet = '<td><input name="listpospurchase['+counter+'].piecesPerSet" type="text" title="'+counter+'" id="piecesPerSet' + counter + '" class="iname piecesPerSet cal_dep qty_pcs_tax" value="0" onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,0,true);"  onkeypress="return ValidateNumberKeyPress(this, event);" /></td>';
			var costRate = '<td><input name="listpospurchase['+counter+'].costRate" type="text" title="'+counter+'" id="costRate' + counter + '" class="iname posrate cal_dep" value="0.00" onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" / ></td>';
			var margin = '<td><input name="listpospurchase['+counter+'].marginPercentage" type="text" title="'+counter+'" id="marginP' + counter + '" class="iname QtyPcs marginP cal_dep" value="0.00" onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"  / ></td>';			
			var salesRate = '<td><input name="listpospurchase['+counter+'].salesRate" type="text" title="'+counter+'" id="salesRate' + counter + '" class="iname cal_dep posrate" value="0.00"  onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"  / ></td>';
			var discVal = '<td><input name="listpospurchase['+counter+'].discPer" type="text" title="'+counter+'" id="discountPer' + counter + '" class="discPer cal_dep qty_pcs_tax" value="0.00" onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"  / ></td>';
			var tax = '<td><input name="listpospurchase['+counter+'].taxPercantage" type="text" title="'+counter+'" id="taxP' + counter + '" class="iname cal_dep qty_pcs_tax" value="0.00" onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"  / ></td>';
			var amt = '<td><input name="listpospurchase['+counter+'].purchaseAmt" type="text" readonly="readonly" title="'+counter+'" id="purchaseAmt'+ counter +'" class="iname posrate cal_dep " value="0.00" onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"  / ></td>';
			var deleted = '<td style="display:none"><input name="listpospurchase['+counter+'].deleted" id="deleted'+counter+'" title="'+counter+'" class="isDeleted" value="false" /></td>';
			var rows = '<tr class="staticRowP">' + rowId + delbutton + categoryName + itemName + qty + piecesPerSet + costRate + margin + salesRate + discVal + tax  +amt +'<input type="hidden" name="listpospurchase['+counter+'].purchaeItemID" />'+deleted+'</tr>';
			$("tr.staticRowP:last").after(rows);
			$('#tempCategoryName option').clone().appendTo("#categoryName"+counter);
			$("input[name=delete]:last").focus();
			counter++;
			POSPurchasedel();	
			$("[id^=qtySet]").keyup(POPPurchaseAmtCalc);
			$("[id^=costRate]").keyup(POPPurchaseAmtCalc);
			$("[id^=marginP]").keyup(POPPurchaseAmtCalc);
			$("[id^=salesRate]").keyup(POPPurchaseSalesRCalc);
			$("[id^=purchaseAmt]").keyup(POP_Calc_Subfunctions).trigger('keyup');
			$("[id^=taxP]").keyup(POP_Calc_Subfunctions).trigger('keyup');				
			$("[id^=discountPer]").keyup(POP_Calc_Subfunctions).trigger('keyup');
			$("#fixedPer0").keyup(fixDiscP).trigger('keyup');
			POPItemAuto();
		});	
}

/** POS Purchase del click */
function POSPurchasedel() {
	
	$(".delP").click(function(){
			
			var thisRow = $(".staticRowP").length;
			if(thisRow == 1){
				
			}
			else if(thisRow > 1) {	
				$(this).parents(".staticRowP").find("[id^=deleted]").val("true");
				$(this).parents(".staticRowP").find("[id^=purchaseAmt]").val("0.00");
				//$(this).parents(".staticRowP").find("[id^=categoryName]").val("");
				//$(this).parents(".staticRowP").find("[id^=itemName]").val("");
				$(this).parents(".staticRowP").find("[id^=qtySet]").val("0");
				$(this).parents(".staticRowP").find("[id^=piecesPerSet]").val("0");
				$(this).parents(".staticRowP").find("[id^=costRate]").val("0.00");
				$(this).parents(".staticRowP").find("[id^=marginP]").val("0.00");
				$(this).parents(".staticRowP").find("[id^=salesRate]").val("0.00");
				$(this).parents(".staticRowP").find("[id^=discountPer]").val("0.00");
				$(this).parents(".staticRowP").find("[id^=taxP]").val("0.00");
				$(this).parents(".staticRowP").hide();
				
				POP_Calc_Subfunctions();
			}	
			//fixDiscP();
			/*$(this).parents(".staticRowP").find("[id^=deleted]").val("true");
			var delClass = $("#deleted").val();
			if(delClass == "true"){			
				$(this).parents(".staticRowP").hide();
			}*/
			
		});
	/** For hiding deleted rows on Update mode */
	
		
		
	
	}

/**  POS Purchase Fixed discount % **/
function fixDiscP(){
	var fixedDiscount = $("#fixedPer0").val();
	if(fixedDiscount > 0){		
		$("[id^=discountPer]").val(fixedDiscount);				
		$("[id^=discountPer]").attr("readonly","readonly");
		POP_Calc_Subfunctions();
	}else if(fixedDiscount == 0.00){
		//$("[id^=discountPer]").val("0.000");
		$("[id^=discountPer]").removeAttr("readonly","readonly");				
	}
}

/******** POS Purchase Item Name Auto Complete ************/
function POPItemAuto(){ 
	
	$("[id^=itemName]").focusin(function(){	
	var Icount = $(this).attr('title');
	var IcompleteItemId = "#itemName"+Icount;
	var IcnameVal = $("#categoryName"+Icount).val();	
	$(IcompleteItemId).empty(); //Clear before binding Values.
	
	$.ajax({						
			type:'GET',
			url:'itemName_Detail.htm',
			data: { cnameValue : IcnameVal },
			success:function(Idata){
				if(Idata != ""){
					var Iarr=new Array();
					Idata=Idata.replace('[','');
					Idata=Idata.replace(']','');
					Idata=Idata.replace('[','');
					Idata=Idata.replace(']','');
					Iarr = Idata.split(',');
					$.each(Iarr,function(Iindex, Iitem) {
				      					        
						var Iresult = Iitem.replace(/^\s+|\s+$/g,'').replace(/\s+/g,' ');
						
						if(Iitem != ']' && Iitem !=',' && Iitem !=' ' && Iitem !='' && Iitem != '[' )
						{
							$(IcompleteItemId).get(0).options[Iindex] = new Option(Iresult);	
						}	
						
						$("select[id$="+IcompleteItemId+"] > option:empty").remove();
					});
				}else{
					$(IcompleteItemId).options[0] = new Option("Select");	
				}
			},
			error:function(xmlHttpRequest, textStatus, errorThrown){
				if(xmlHttpRequest.readyState=0 || xmlHttpRequest.status == 0)
					return;
			},
		});
	
		/** For Setting Default Select if category value is 'Select' **/
		if(IcnameVal == ""){
			$(IcompleteItemId).get(0).options[0] = new Option("Select");	
		}
	});
	
	$("[id^=itemName]").select(function(){
		
		
	});
}

//******* POS Purchase Sub Total Field Calculation ******* //
function POP_Calc_Subfunctions(){
	
	var siz = $("[id^=purchaseAmt]").size();
	var sum = 0;
	var taxAmt = 0;
	var POPdiscAmt = 0;
	
	for(var i=0;i<siz;i++) {
		
		var purAmt = $("#purchaseAmt"+i).val();
		var taxP = $("#taxP"+i).val();
		var discPer = $("#discountPer"+i).val();
			
		if(purAmt.length == 0 || purAmt == '' || purAmt == null || purAmt == 'NaN'){
			purAmt = 0;
		}
		
		if(taxP.length == 0 || taxP == '' || taxP == null || taxP == 'NaN'){
			taxP = 0;
		}
		
		if(discPer.length == 0 || discPer == '' || discPer == null || discPer == 'NaN'){
			discPer = 0;
		}
		
		/******* Sub Total Field Calculation *********/
			
		sum = parseFloat(sum) + parseFloat(purAmt);
		
		/******* Total tax Field Calculation *********/
					
		var t1 = parseFloat(taxP) / 100;
		var t2 = parseFloat(purAmt) * parseFloat(t1);
		taxAmt = parseFloat(taxAmt) + parseFloat(t2);
		
		/******* Total Discount Field Calculation *****/
		var cal1 = parseFloat(discPer) / 100;
		var cal2 = parseFloat(purAmt) * parseFloat(cal1);
		POPdiscAmt = parseFloat(POPdiscAmt) + cal2;	
	}
	
	$("#subTotal0").val(sum.toFixed(2));		
	$("#totalTax0").val(taxAmt.toFixed(2));	
	$("#totalDiscount0").val(POPdiscAmt.toFixed(2));
	
	/********* Grand Total Value Field Calculation  *****/
	
	var totalval = ( parseFloat(sum) + parseFloat(taxAmt) ) - parseFloat(POPdiscAmt);	
	$("#totalValue0").val(totalval.toFixed(2));
	
	var totalvalue = $("#totalValue0").val();
	var beforeRound = totalvalue;
	var afterRound = parseFloat(beforeRound).toFixed(0);
	var roundedVal = parseFloat(afterRound) - parseFloat(beforeRound);
	
	$("#roundOff0").val(parseFloat(roundedVal).toFixed(2));
	$("#grandAmt0").val(parseFloat(afterRound).toFixed(2));	
} 

//******  POS Purchase Row Amount field  calculation ***** //
function POPPurchaseAmtCalc() {
	
	var rowNum  = $(this).attr('title');
	
	var costRate = $("#costRate"+rowNum).val();
	if(costRate.length == 0 || costRate == '' || costRate == null){
		costRate = 0;
	}
	
	var qty = $("#qtySet"+rowNum).val();
	if(qty.length == 0 || qty == '' || qty == null){
		qty = 0;
	}
	
	var purhcaseamt = parseFloat(costRate) * parseFloat(qty);
	$("#purchaseAmt"+rowNum).val(parseFloat(purhcaseamt).toFixed(2));	
	
	var marginp = $("#marginP"+rowNum).val();
	if(marginp.length == 0 || marginp == '' || marginp == null){
		marginp = 0;
	}
	
	var margin = marginp / 100;
	var salesRate =  ( parseFloat(costRate) * margin ) + parseFloat(costRate);
	
	$("#salesRate"+rowNum).val(salesRate.toFixed(2));
	
	POP_Calc_Subfunctions(); // subFunctions
}


//******  POS Purchase Margin Percentage calculation ***** //
function POPPurchaseSalesRCalc() {
	
	var rowNum  = $(this).attr('title');
	
	var costRate = $("#costRate"+rowNum).val();
	if(costRate.length == 0 || costRate == '' || costRate == null){
		costRate = 0;
	}
	
	var salesRate = $("#salesRate"+rowNum).val();
	if(salesRate.length == 0 || salesRate == '' || salesRate == null){
		salesRate = 0;
	}
		
	var tempmargin1 = (parseFloat(salesRate) - parseFloat(costRate) ) ;
	var marginP = "0.00";
	if(tempmargin1 != 0){
		var margin = tempmargin1 / parseFloat(costRate);
		marginP = parseFloat(margin) * 100;
	}
	$("#marginP"+rowNum).val(parseFloat(marginP).toFixed(2));
	
}


