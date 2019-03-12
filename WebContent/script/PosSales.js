

 $(document).ready(function(){

	 $('#poscustomer').keypress(autoCustomerName).trigger("keypress");
});


//*********** Supplier Name Auto Complete ****************************//
function autoCustomerName(){	

	
 $.ajax({	
	
		type:'GET',
		url:'customerName_Auto.htm',
		data:{sNamePart:$("#poscustomer").val()},
		success:function(data){
			//alert(data);
			var arr=new Array();
			data=data.replace('[','');
			data=data.replace(']','');
			
			var finalData="";
			
			arr = data.split(',');	
			$.each(arr,function(index, item) {
		      					        
				var result = item.replace(/^\s+|\s+$/g,'').replace(/\s+/g,' ');
				if(item != ']' && item !=',' && item !=' ' && item !='' && item != '[' )
				{
					finalData = finalData.concat(result);
					finalData = finalData.concat(":");
				}				
			});
			finalData = finalData.concat("Walk-in");
			finalData = finalData.concat(":");
			var iVal = finalData.split(":");
			$("#poscustomer").autocomplete(iVal);
		},
		error:function(xmlHttpRequest, textStatus, errorThrown){
			if(xmlHttpRequest.readyState=0 || xmlHttpRequest.status == 0)
				return;
		},
	});
}
///////////////////////////////////////////////