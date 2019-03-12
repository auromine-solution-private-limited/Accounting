$(document).ready(function(){
	
	$("#scs_startdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
	
	if($("#scs_startdate").val()=='')
	{
		$("#scs_startdate").val(prettyDate);
	}	
		
	var URIloc = $(location).attr('href');
	var uripath = URIloc.split('/')[4];
	var errorType = $("#errorName").val();
	
	if(uripath.indexOf("viewstartscheme.htm") >= 0 ){
		$("#insert").hide();
		if($('#IssuedName').val()!="empty")
		{
			$("#update").hide();	
		}
		else
		{
			$('#update').show();
		}	
	} 
	
	if(uripath.indexOf("addstartscheme.htm") >= 0){
		$("#insert").show();
		$("#update").hide();
	}
		
	if(errorType == "insertError") {
		$("#insert").show(); 
		$("#update").hide();
	}else if(errorType == "updateError") {
		$("#insert").hide();
		$("#update").show();
	}
	
	$("#SchemeName").change(ajax_SchemeDetail).triggerHandler('change');
	$("#SchemeDuration").attr("maxlength","8");
});


function ajax_SchemeDetail(){

	var schemeName =  $("#SchemeName").val();
	
	$.ajax({
		type:'GET',
		url:'SchemeDetail.htm',
		data:{schemeName:schemeName},
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
						$("#schemeType").val(item1);						
						break;
					} 
					case 1:{
						$("#SchemeInAmount").val(parseFloat(item1).toFixed(2));						
						break;
					}
					case 2:{
						$("#SchemeInGrams").val(parseFloat(item1).toFixed(3));
						break;
					}
				}
				SchemeAmtGms();
			});
		},error: function(xmlHttpRequest,textStatus,errorThrown){
			if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
				return;
		}		
	});
}
