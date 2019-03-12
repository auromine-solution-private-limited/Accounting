$(document).ready(
		function() {
			//Customer POP fucntion calls 
	 	  	$("#saveCustomer").click(submitCustomer);
	 		$("#custSalesPopup").click(clearCustPopup);
			});
		

//Customer Pop function for sales adde on 4-12-12 
//*********** Customer Creation POP Window ****************************//
function submitCustomer() {
	
	var custName = $("#popcustomerName").val();
	//alert("hi:"+custName);
	var openingBal = $("#openingBal").val();
	var openingType = $("#openingType").val();
	var address = $("#Address1").val();
	
	if(custName == "" || custName.length == 0 && custName=="Walk-in"){
		//alert("lkkkk");
		custName = "";
		$("#POSCustErrorMsg").html('<span class="error"> * Customer Name cannot be Empty.</span>');
		return false;
	}else if(custName != null ){
		// Cusotmer existing check server side validation through ajax
		$.ajax({
			type:'GET',
			url:'SSCIPOPCusValidate.htm',
			data : {custName : custName},
			success : function(data) {
				if(data == "true"){
					$("#POSCustErrorMsg").html('<span class="error"> * Customer Name already Exist.</span>');
					return false;
				}else {
					// Save POS Customer Pop up
					
					$.ajax({
						type:'POST',
						url:'SSCIPOPcustomerPOPUP.htm',
						data : { custName : custName, openingBal : openingBal, openingType : openingType, address : address},
						success : function(data) {
							
							  $('#mask, .window').hide();
							  $("#poscustomer").append("<option value='"+custName+"'>"+custName+"</option>");
							  alert("Customer Detail Added");			  
							//  $("#poscustomer").focus(autoCustomerName);	 	       
						},
						error : function(xmlHttpRequest, textStatus, errorThrown) {			
								if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
									return;
						},	
						
					});	// Save POS Customer Pop up	ends here
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

//clear Customer pop up window
function clearCustPopup(){

	$("#popcustomerName").val("");
	$("#openingBal").val("0.00");
	$("#Address1").val("");
	$("#POSCustErrorMsg").html("");
}