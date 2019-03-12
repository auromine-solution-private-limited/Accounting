$(document).ready(function(){
	$("#insert").click(checkIsEmpty);
	$("#update").click(checkIsEmpty);
});


function checkIsEmpty(){
	$('input.cal_dep').each(function(){
		var TempVal = $(this).val();
		if(TempVal == null || TempVal.length == 0 || TempVal == '.' || TempVal == '-' ||  TempVal == 'NaN' || TempVal == 'undefined' || TempVal == 'Infinity'){
			$(this).val("0");			
		}
	});
}

function Init_Value(temp){
	var ZERO = "0";
	if(temp == null || temp.length == 0 || temp == '.' || temp == '-' ||  temp == 'NaN' || temp == 'undefined' || temp == 'Infinity'){
		return ZERO;		
	}else{
		return temp;	
	}
}