$(document).ready(function(){
	
	//***********************************  Auto Complete for Account Name Search ***********************/  
	$.ajax({			
		type:'GET',
		url:'ledgerName_Auto.htm',
		data:{lNamePart:$("#ledgerName").val()},
		success:function(data){
			
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
			var iVal = finalData.split(":");
			$("#ledgerName").autocomplete(iVal);
		},
		error:function(xmlHttpRequest, textStatus, errorThrown){
			if(xmlHttpRequest.readyState=0 || xmlHttpRequest.status == 0)
				return;
		},
	});
	
	//***********************************  Auto Complete for item Name Search ***********************/
	$.ajax({			
		type:'GET',
		url:'itemName_Auto.htm',
		data:{iNamePart:$("#itemName").val()},
		success:function(idata){
			
			var iarr=new Array();
			idata=idata.replace('[','');
			idata=idata.replace(']','');
			
			var ifinalData="";
			
			iarr = idata.split(',');	
			$.each(iarr,function(index, item) {
		      					        
				var result = item.replace(/^\s+|\s+$/g,'').replace(/\s+/g,' ');
				if(item != ']' && item !=',' && item !=' ' && item !='' && item != '[' )
				{
					ifinalData = ifinalData.concat(result);
					ifinalData = ifinalData.concat(":");
				}				
			});
			var iName = ifinalData.split(":");
			$("#itemName").autocomplete(iName);
		},
		error:function(xmlHttpRequest, textStatus, errorThrown){
			if(xmlHttpRequest.readyState=0 || xmlHttpRequest.status == 0)
				return;
		},
	});
	//***********************************  Auto Complete for JobOrderNo - Smith Name Search ***********************/
	$.ajax({			
		type:'GET',
		url:'smithName_Auto.htm',
		data:{sNamePart:$("#smithName").val()},
		success:function(sdata){
			
			var sarr=new Array();
			sdata=sdata.replace('[','');
			sdata=sdata.replace(']','');
			
			var sfinalData="";
			
			sarr = sdata.split(',');	
			$.each(sarr,function(index, item) {
		      					        
				var result = item.replace(/^\s+|\s+$/g,'').replace(/\s+/g,' ');
				if(item != ']' && item !=',' && item !=' ' && item !='' && item != '[' )
				{
					sfinalData = sfinalData.concat(result);
					sfinalData = sfinalData.concat(":");
				}				
			});
			var sName = sfinalData.split(":");
			$("#smithName").autocomplete(sName);
		},
		error:function(xmlHttpRequest, textStatus, errorThrown){
			if(xmlHttpRequest.readyState=0 || xmlHttpRequest.status == 0)
				return;
		},
	});

});