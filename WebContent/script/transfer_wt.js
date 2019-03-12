$(document).ready(function() {
	$("#insert").click(totalgrossweightCheck);
	$("#insert").click(bullionTypeCheck);
	$("#sb1").click(duplicateItemcode);
	$("#sb2").click(duplicateItemcode);

});

function bullionTypeCheck(){
	var frmbulType=$("#frombullion").val();
	var tobulType=$("#tobullion").val();
	if(frmbulType != tobulType){
		alert("Bullion Type Should Be Same!!");
		return false;
	}
	 
}
function duplicateItemcode(){
	//alert(":::::duplicate");
	var frmitemcode=$("#textbox1").val();
	var toitemcode=$("#textbox2").val();
	if(frmitemcode !="" && toitemcode !="")
	{
		if(frmitemcode==toitemcode){
			alert("Same ItemCode Cannot be Used!!!");
			return false;
		}
	}
}


function totalgrossweightCheck()
{
	var fromgrosswt=$("#frm_Gross").val();
	var togrosswt=$("#To_Gross").val();
	if(fromgrosswt == 0 || togrosswt == 0){
		alert("FromGrossWeight OR ToGrossWeight Cannot Be Zero !!!");
		return false;
	}
	if(fromgrosswt!=togrosswt)
	{
		alert('FromGrossWeight & ToGrossWeight Must Be Same !!!');
		return false;
	}

}
