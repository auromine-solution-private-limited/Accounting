$(document).ready(function(){		
		$("#voucherSlip").click(receipt_check_id);
		$("#paymentVoucherSlip").click(payment_check_id);
	});

/** For Gold Sales Register Validation **/	
function gold_sales_Registers(){	
	
  	// From Date
	if($("#goldsrfrmdate").val() == "") {
		$("#lblGSR").html('<span class="error"> * Please Select From Date.</span>');
		return false;
	}else if(checkDates($("#goldsrfrmdate").val())){
		$("#lblGSR").html('<span class="error"> * In-Valid From Date.</span>');
		return false;			
	}
	
	//To date
	if($("#goldsrtodate").val() == "") {
		$("#lblGSR").html('<span class="error"> * Please Select To Date.</span>');
		return false;
	}else if(checkDates($("#goldsrtodate").val())){
		$("#lblGSR").html('<span class="error"> * In-Valid To Date.</span>');
		return false;			
	}		
		
	$("#lblGSR").html("");
}

/** For Silver Sales Register Validation **/	
function silver_sales_Registers(){
  	
	//From Date
	if($("#silversrfrmdate").val() == "") {
		$("#lblSSR").html('<span class="error"> * Please Select From Date.</span>');
		return false;
	}else if(checkDates($("#silversrfrmdate").val())){
		$("#lblSSR").html('<span class="error"> * In-Valid From Date.</span>');
		return false;
	}
	
	//To Date
	if($("#silversrtodate").val() == "") {
		$("#lblSSR").html('<span class="error"> * Please Select To Date.</span>');
		return false;
	}else if(checkDates($("#silversrtodate").val())){
		$("#lblSSR").html('<span class="error"> * In-Valid From Date.</span>');
		return false;
	}
	
	$("#lblSSR").html("");
}


	function receipt_check_id(){
			var voucherNo = $("#receiptno").val();
			if(voucherNo == null || voucherNo==""){
				$("#lblrv").html("Please Enter Voucher Id.");
				return false;
			}else {
				$.ajax({
					type:'GET',
					url:'RVReportValidate.htm',
					data : {voucherNo : voucherNo},
					success : function(data) {
						if(data == "false"){
							$("#lblrv").html('<span class="error"> * Invalid Receipt Voucher Number.</span>');
							return false;
						}else{
							return true;
						}
						},
						error : function(xmlHttpRequest, textStatus, errorThrown){
							if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
								return;
					},	
					
				});	
			}
			$("#lblrv").html("");
	}
	
	function payment_check_id(){
		var voucherNo = $("#paymentno").val();
		if(voucherNo == null || voucherNo==""){
			$("#lblpv").html("Please Enter Voucher Id.");
			return false;
		}if(voucherNo != "") {
			$.ajax({
				type:'GET',
				url:'PVReportValidate.htm',
				data : {voucherNo : voucherNo},
				success : function(data) {
					if(data == "false"){
						$("#lblpv").html('<span class="error"> * Invalid Payment Voucher Number.</span>');
						return false;
					}
					},
					error : function(xmlHttpRequest, textStatus, errorThrown){
						if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
							return;
				},	
				
			});	
		}		
		$("#lblpv").html("");
	}
	
	function checkDates(fieldVal){
		var currVal = fieldVal;
		var regexDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
		var dtArray = currVal.match(regexDatePattern);
		if(dtArray == null){
			return true;
		}
		var dtDay = dtArray[1];
		var dtMonth = dtArray[3];
		var dtYear = dtArray[5];
		if(dtMonth < 1 || dtMonth > 12){
			return true;
		}else if(dtDay < 1 || dtDay > 31){
			return true;
		}else if((dtMonth == 4 || dtMonth ==6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31){
			return true;
		}else if(dtMonth == 2){
			var isLeap = ( dtYear %4 ==0 && (dtYear % 100 != 0 || dtYear % 400 == 0) );
			if(dtDay > 29 || (dtDay == 29 && !isLeap)){
				return true;
			}
		}
		return false;
	}