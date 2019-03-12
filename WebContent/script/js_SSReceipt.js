$(document).ready(function(){
	
	var URIloc = $(location).attr('href');
	var uripath = URIloc.split('/')[4];
	var errorType = $("#errorName").val();
	
	if(uripath.indexOf("viewSavingReceipt.htm") >= 0 || uripath.indexOf("viewSSRCancel.htm") >= 0){
		$("#insert").hide();
		$("#update").show();
		if($("#ssrFormType").val() == "Saving Scheme Receipt") {
			if($("#lastNo").val() == "No"){
				$("#update").hide();
			}
		}
		SchemeAmtGms();
	}
	if(uripath.indexOf("formsavingreceipt.htm") >= 0 || uripath.indexOf("formSSRCancel.htm") >= 0){
		$("#insert").show();
		$("#update").hide();
	}
	if(errorType == "insertError") {
		$("#insert").show();
		$("#update").hide();
		SchemeAmtGms();
	}else if(errorType == "updateError") {
		$("#insert").hide();
		$("#update").show();
		SchemeAmtGms();
	}
	
	//Receipt Methods
//	$("#ssrCustomerName").change(ajax_SchemeName_Visibility);
//	$("#ssrCustomerName").keyup(ajax_SchemeName_Visibility);
	
//	$("#ssrSchemeName").change(ajax_GetCardList);
// 	$("#ssrSchemeName").keyup(ajax_GetCardList);
	
	
	$("#ssrCardNo").focusout(ajax_Retreive_Values);
	
	//Payment Methods
	$("#SSPCustomerName").change(ajax_SSP_SchemeName_Visibility);
	$("#SSPCustomerName").keyup(ajax_SSP_SchemeName_Visibility);
	
	$("#SSPSchemeName").change(ajax_SSP_GetCardList);
	$("#SSPSchemeName").keyup(ajax_SSP_GetCardList);
	
	$("#sspCardNo").change(ajax_SchemeCalc);
	$("#sspCardNo").keyup(ajax_SchemeCalc);
	
	
	/** For Invalid date Validations on Insert **/
	$("#insert").click(validatForm);
	
	/** For Invalid date Validations on Insert **/
	$("#update").click(validatForm);
	
	/** On Save Print Option **/
	if($("#ssrFormType").val() == "Saving Scheme Receipt")
	{
		var receiptNO= $("#printId").val();
		var printConfirm = $("#printInvoice").val();
	
		if(receiptNO == null || receiptNO == 'undefined' || receiptNO == ''){
			receiptNO = null;
		}
		if(printConfirm == null || printConfirm == 'undefined' || printConfirm == ''){
			printConfirm = null;
		}
		if(printConfirm=="true" && receiptNO != null){
			window.open('SSReceiptVoucher.htm?receiptNO='+receiptNO,'','');
			window.location.replace('formsavingreceipt.htm');
			initPrintPreview();
		}else if(printConfirm=="false" && receiptNO !=null){
			var successMsg = "Last Receipt : " + receiptNO + " Saved Successfully...";
			$(".smsg").html(successMsg);
			initPrintPreview();
		}
	}
});

function initPrintPreview(){
	$("#printId").val();
	$("#printInvoice").val();
}

 function validatForm(){
	 
	 $(".smsg").html("");
	
	$('input.numField').each(function(){
		var TempVal = $(this).val();
		if(TempVal.length === 0 || TempVal === '' || TempVal === null){
			$(this).val("0");			
		};
	});
	
	if($("#ssrFormType").val() == "Saving Scheme Receipt")
	{
		var cardNo = $("#ssrCardNo").val();
		var custName = $("#ssrCustomerName").val();
		var ssrSchemeName = $("#ssrSchemeName").val();
		var debitAccountName = $("#debitAccountName").val();
	
		if(cardNo == null || cardNo == 'undefined' || cardNo == ''){
			alert("card NO :"+cardNo);
			$("#form_error").slideDown("fast");
			$("#form_error span").text("* Card Number Required.");
			return false;
		}
		if(custName == null || custName == 'undefined' || custName == ''){
			$("#form_error").slideDown("fast");
			$("#form_error span").text("* Customer Name Required.");
			return false;
		}
		if(ssrSchemeName == null || ssrSchemeName == 'undefined' || ssrSchemeName == ''){
			$("#form_error").slideDown("fast");
			$("#form_error span").text("* Scheme Name Required.");
			return false;
		}
		if(debitAccountName == "0"){
			$("#form_error").slideDown("fast");
			$("#form_error span").text(" * Please Select Debit Account Name Required.");
			return false;
		}
	}
	
	var result = SSRcheckDate();
	if(result == false){
		alert("*Enter Valid Date");
		return false;
	}
	
	$("#SSPSchemeName").attr("disabled", false);
	$("#sspCardNo").attr("disabled", false);
	
	if($("#ssrFormType").val() == "Saving Scheme Receipt")
	{
		if ($(this).attr("id") == "insert") {
			var test = confirm("Storing Saving Scheme Receipt.. Click OK to view Print preview.");
			$("#printInvoice").val(test);
		}
	}
	return true;
}


/***** SSReceipt Get Scheme Names List ***********
function ajax_SchemeName_Visibility(){
		
	var ssrCustomerName = $("#ssrCustomerName").val();
	
	// For Disable
	if(ssrCustomerName == 0 || ssrCustomerName == "Select"){
		$("#ssrSchemeName").val('Select');
		$("#ssrSchemeName").attr("disabled", true);		
		$("#ssrCardNo").val('Select');
		$("#ssrCardNo").attr("disabled", true);	
	}else{
		$("#ssrSchemeName").attr("disabled", false);
		$("#ssrCardNo").attr("disabled", false);	
	}
	
	var ssrSchemeName = $("#ssrSchemeName").val();
	if(ssrSchemeName == 0 || ssrSchemeName == "Select"){
		$("#ssrCardNo").val('Select');
		$("#ssrCardNo").attr("disabled", true);	
	}else{
		$("#ssrCardNo").attr("disabled", false);
	}
	
	var entryId = $("#entryId").val();
	if(entryId.length == 0 || entryId == null){
		entryId = 0;
	}
		
	//Retrieve Scheme List
 	$.ajax({
		type:'GET',
		url:'getSchemeNameList.htm',
		data:{customerName:ssrCustomerName, entryId:entryId},
		success: function(datas){
			
			if(datas != ""){
				$("select[id$=ssrSchemeName] > option").remove();
				var arr=new Array();
				datas=datas.replace('[','');
				datas=datas.replace(']','');
				datas=datas.replace('[','');
				datas=datas.replace(']','');
				arr = datas.split(',');
				
				$.each(arr,function(index, item) {
			        
					var result = item.replace(/^\s+|\s+$/g,'').replace(/\s+/g,' ');
					if(item != ']' && item !=',' && item !=' ' && item !='' && item != '[' )
					{					
		              $("#ssrSchemeName").get(0).options[index] = new Option(result);	
					}	
				});
				$('select option:empty').remove();
			}else{
				$("select[id$=ssrSchemeName] > option").remove();
				$("#ssrSchemeName").options[0] = new Option("Select");				
			}			
		},
		error: function(xmlHttpRequest,textStatus,errorThrown){
			if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
				return;
		}
	});
 	$("#ssrCardNo").val('Select');
 	$("#ssrCardNo").attr("disabled", true);	
 	Reset_Calculation();
}***/

function ajax_Retreive_Values(){
	
	var ssrCardNo = $("#ssrCardNo").val();
	var receiptId = $("#entryId").val();
	$("#ssrCustomerName").val("");
	$("#ssrSchemeName").val("");
	
	//Retrieve Card Detail List
 	$.ajax({
		type:'GET',
		url:'getSSRCardList.htm',
		data:{ssrCardNo:ssrCardNo, receiptId:receiptId},
		success: function(data){
			if(data != ""){
				data=data.replace('[','');
				data=data.replace(']','');
				var arr=new Array();																
				arr = data.split(',');
				$.each(arr,function(index, detail) 
				{
					switch(index)
					{
						case 0:{
							$("#ssrCustomerName").val(detail);
							$("#headerCustomer").html(detail);
							break;
						} 
						case 1:{
							$("#ssrSchemeName").val(detail);
							break;
						}
						case 2:{
							detail = detail.replace(' ','');
							$("#schemeType").val(detail);
							break;
						}
					}
					ajax_SchemeCalc();
				});	
				SchemeAmtGms();
			}
		},
		error: function(xmlHttpRequest,textStatus,errorThrown){
			if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
				return;
		}
	});
	
}

/***** SSReceipt Get Card Numbers List ***********
function ajax_GetCardList(){
	
	var ssrCustomerName =  $("#ssrCustomerName").val();
	var ssrSchemeName = $("#ssrSchemeName").val();
	if(ssrCustomerName == 0 || ssrCustomerName == "Select" || ssrSchemeName == 0 || ssrSchemeName == "Select"){
		$("#ssrCardNo").val('Select');
		$("#ssrCardNo").attr("disabled", true);	
	}else{
		$("#ssrCardNo").attr("disabled", false);	
	}
	var entryId = $("#entryId").val();
	if(entryId == null){
		entryId = 0;
	}
	
	//Retrieve Card Number List
	$.ajax({
		type:'GET',
		url:'getCardDetail.htm',
		data:{customerName:ssrCustomerName, schemeName:ssrSchemeName, entryId:entryId},
		success: function(data)
		{	
			$("#ssrCardNo option").remove();
			var arr=new Array();
			data=data.replace('[','');
			data=data.replace(']','');
			data=data.replace('[','');
			data=data.replace(']','');
			arr = data.split(',');
			$.each(arr,function(index, item) {
				var result = item.replace(/^\s+|\s+$/g,'').replace(/\s+/g,' ');
				if(item != ']' && item !=',' && item !=' ' && item !='' && item != '[' )
				{					
	              $("#ssrCardNo").get(0).options[index] = new Option(result);	 
				}	
			});
			$('select option:empty').remove();
			
			
			//** TO get Scheme Type ***
			$.ajax({
				type:'GET',
				url:'getSchemeType.htm',
				data:{schemeName:$("#ssrSchemeName").val()},
				success: function(data2){
					data2=data2.replace('[','');
					data2=data2.replace(']','');
					$("#schemeType").val(data2);
					SchemeAmtGms();
				},
				error: function(xmlHttpRequest,textStatus,errorThrown){
					if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
						return;
				}
			});	//** end of TO get Scheme Type ***
		},
		error: function(xmlHttpRequest,textStatus,errorThrown){
			if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
				return;
		}
	});
	$("#ssrCardNo").val('Select');
	Reset_Calculation();
}***/

//on Card No Call : Scheme Amount Calculation
function ajax_SchemeCalc(){
	
	var formType = $("#ssrFormType").val();
	var cardNo = "";
	if(formType == "Saving Scheme Receipt"){
		cardNo = $("#ssrCardNo").val();
	}else{
		cardNo = $("#sspCardNo").val();
	}
	if(cardNo == 0 || cardNo == 'Select'){
		Reset_Calculation();
	}
	else{
		$.ajax({
			type:'GET',
			url:'SchemeCalc.htm',
			data:{cardNo:cardNo,formType:formType},
			success: function(data)
			{
				data=data.replace('[','');
				data=data.replace(']','');
				var arr=new Array();																
				arr = data.split(',');
				$.each(arr,function(index, item1) 
				{
					switch(index)
					{
						case 0:{
							$("#SchemeInAmount").val(item1);
							break;
						} 
						case 1:{
							$("#SchemeInGrams").val(parseFloat(item1).toFixed(3));
							break;
						}
						case 2:{
							$("#ssrReceiptAmount").val(parseFloat(item1).toFixed(2));
							break;
						}
						case 3:{
							if(formType == "Saving Scheme Payment"){
								$("#sspClosingGrams").val(parseFloat(item1).toFixed(3));
								break;
							}
						}
					}
				});
			},error: function(xmlHttpRequest,textStatus,errorThrown){
				if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
					return;
			}
		});
	}
}

function Reset_Calculation(){
	$("#SchemeInAmount").val("0.00");
	$("#SchemeInGrams").val("0.000");
	$("#ssrReceiptAmount").val("0.00");
	$("#sspClosingGrams").val("0.000");
}

/******************* Payment Methods ******************/

/***** SSPayment Get Scheme Names List **************/
function ajax_SSP_SchemeName_Visibility(){
	
	var sspCustomerName = $("#SSPCustomerName").val();
	if(sspCustomerName!="Select"){
		$("#headerCustomerSSP").html(sspCustomerName);
	}
	//For Disable
	if(sspCustomerName == 0 || sspCustomerName == "Select"){
		$("#SSPSchemeName").val('Select');
		$("#SSPSchemeName").attr("disabled", true);		
		$("#sspCardNo").val('Select');
		$("#sspCardNo").attr("disabled", true);	
	}else{
		$("#SSPSchemeName").attr("disabled", false);
		$("#sspCardNo").attr("disabled", false);	
	}
	
	var SSPSchemeName = $("#SSPSchemeName").val();
	if(SSPSchemeName == 0 || SSPSchemeName == "Select"){
		$("#sspCardNo").val('Select');
		$("#sspCardNo").attr("disabled", true);	
	}else{
		$("#sspCardNo").attr("disabled", false);
	}
	
	var entryId = $("#entryId").val();
	if(entryId == null){
		entryId = 0;
	}
	
	//Retrieve Scheme List
	$.ajax({
		type:'GET',
		url:'getSSPSchemeNameList.htm',
		data:{customerName:sspCustomerName, entryId:entryId},
		success: function(datas){
			
			if(datas != ""){
				$("select[id$=SSPSchemeName] > option").remove();
				var arr=new Array();
				datas=datas.replace('[','');
				datas=datas.replace(']','');
				datas=datas.replace('[','');
				datas=datas.replace(']','');
				arr = datas.split(',');
				
				$.each(arr,function(index, item) {
			        
					var result = item.replace(/^\s+|\s+$/g,'').replace(/\s+/g,' ');
					if(item != ']' && item !=',' && item !=' ' && item !='' && item != '[' )
					{					
		              $("#SSPSchemeName").get(0).options[index] = new Option(result);	
					}	
				});
				$('select option:empty').remove();
			}else{
				$("select[id$=SSPSchemeName] > option").remove();
				$("#SSPSchemeName").options[0] = new Option("Select");
			}			
		},
		error: function(xmlHttpRequest,textStatus,errorThrown){
			if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
				return;
		}
	});
	
	$("#sspCardNo").val('Select');
	$("#sspCardNo").attr("disabled", true);	
	Reset_Calculation();
}

/***** SSPayment Get Card Numbers List **************/
function ajax_SSP_GetCardList(){
	
	var sspCustomerName = $("#SSPCustomerName").val();
	var sspSchemeName = $("#SSPSchemeName").val();
	
	if(sspCustomerName == 0 || sspCustomerName == "Select" || sspSchemeName == 0 || sspSchemeName == "Select"){
		$("#sspCardNo").val('Select');
		$("#sspCardNo").attr("disabled", true);	
	}else{
		$("#sspCardNo").attr("disabled", false);	
	}
	var entryId = $("#entryId").val();
	if(entryId == null){
		entryId = 0;
	}
	
	$.ajax({
		type:'GET',
		url:'getSSPCardDetail.htm',
		data:{customerName:sspCustomerName, schemeName:sspSchemeName, entryId:entryId},
		success: function(data)
		{			
			$("select[id$=sspCardNo] > option").remove();
			var arr=new Array();
			data=data.replace('[','');
			data=data.replace(']','');
			data=data.replace('[','');
			data=data.replace(']','');
			arr = data.split(',');
			
			$.each(arr,function(index, item) {
		        
				var result = item.replace(/^\s+|\s+$/g,'').replace(/\s+/g,' ');
				if(item != ']' && item !=',' && item !=' ' && item !='' && item != '[' )
				{					
	              $("#sspCardNo").get(0).options[index] = new Option(result);	          
				}	
			});
			$('select option:empty').remove();
			
			// TO get Scheme Type 
			$.ajax({
				type:'GET',
				url:'getSchemeType.htm',
				data:{schemeName:$("#SSPSchemeName").val()},
				success: function(data2){
					data2=data2.replace('[','');
					data2=data2.replace(']','');
					$("#schemeType").val(data2);
					SchemeAmtGms();
				},
				error: function(xmlHttpRequest,textStatus,errorThrown){
					if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
						return;
				}
			});	//** end of TO get Scheme Type
		},
		error: function(xmlHttpRequest,textStatus,errorThrown){
			if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
				return;
		}
	});
	$("#sspCardNo").val('Select');
	Reset_Calculation();
}

function SSRcheckDate(){
	
	var currVal = $("#ssrReceiptDate").val();
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
