$(document).ready(function() {
	//Customer POP fucntion calls
	$("#saveCustomer").click(submitCustomer);
	$("#custSalesPopup").click(clearCustPopup);
});

//Customer Pop function for sales adde on 4-12-12 
//*********** Customer Creation POP Window ****************************//
function submitCustomer() {
	
	var custName = $("#popcustomerName").val();
	var openingBal = $("#openingBal").val();
	var openingType = $("#openingType").val();
	var address = $("#Address1").val();
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var cusCreatedDate = myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
	
	if(custName == "" || custName.length == 0 && custName=="Walk-in"){
		custName = "";
		$("#POSCustErrorMsg").html('<span class="error"> * Customer Name cannot be Empty.</span>');
		return false;
	}else if(custName != null ){
		// Customer existing check server side validation through ajax
		$.ajax({
			type:'GET',
			url:'POSCusValidate.htm',
			data : {custName : custName},
			success : function(data) {
				if(data == "true"){
					$("#POSCustErrorMsg").html('<span class="error"> * Customer Name already Exist.</span>');
					return false;
				}else {
					// Save POS Customer Pop up
					
					$.ajax({
						type:'POST',
						url:'POScustomerPOPUP.htm',
						data : { cusCreatedDate : cusCreatedDate,custName : custName, openingBal : openingBal, openingType : openingType, address : address, City : ""},
						success : function(data) {
					                		
							  $('#mask, .window').hide();
							  $(".custName").append("<option value='"+custName+"'>"+custName+"</option>");
							  $(".custName").val(custName); 
							  $(".row_NameCity").hide();
							  $(".possupplier1").show();
							  $(".w_Name").val('');
							  $(".w_City").val('');
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