function sum_cost()
{
	var txt1 = document.getElementById('stone_cost').value;
	if(txt1.length == 0){
		txt1 = 0;
	}
	var txt2 = document.getElementById('ser_cost').value;
	if(txt2.length == 0 || txt2 == ''){
		txt2 = 0;
	}
	var txt3 = document.getElementById('pol_cost').value;
	if(txt3.length == 0 || txt3 == ''){
		txt3 = 0;
	}
	var gs_wt = document.getElementById('gwt').value;
	if(gs_wt.length == 0 || gs_wt == ''){
		gs_wt = 0;
	}
	var gwt_F = document.getElementById('wt').value;
	if(gwt_F.length == 0 || gwt_F == ''){
		gwt_F = 0;
	}
	var wst = document.getElementById('wst').value;
	if(wst.length == 0 || wst == ''){
		wst = 0;
	}
	var amount = document.getElementById('r').value;
	if(amount.length == 0 || amount == ''){
		amount = 0;
	}

	var bal_wt = 0;
	var met_cost = 0;
	var total_cost=0;
		
	bal_wt = parseFloat(gwt_F) - parseFloat(gs_wt); 

	if(bal_wt > 0){
		
		document.getElementById('f_bwt').value = 0;
		document.getElementById('f_gwt').value = bal_wt.toFixed(3);		
		met_cost = (parseFloat(bal_wt) + parseFloat(wst)) * amount;
		document.getElementById('metal_cost').value = met_cost.toFixed(2);		
	}
	else{
		document.getElementById('f_gwt').value = 0;
		bal_wt = parseFloat(gs_wt) - parseFloat(gwt_F);
		document.getElementById('f_bwt').value = bal_wt.toFixed(3);
		met_cost = parseFloat(amount * wst);
		document.getElementById('metal_cost').value = met_cost.toFixed(2);	
	}
	var txt4 = document.getElementById('metal_cost').value;
	if(txt4.length == 0 || txt4 == ''){
		txt4 = 0;
	}
	total_cost = parseFloat(txt1) + parseFloat(txt2) + parseFloat(txt3) + parseFloat(txt4);
	document.getElementById('tot_cost').value = total_cost.toFixed(2);
}

function updateStatus(){
	var statustype = document.getElementById("statusType").value;
	if (statustype == 'Received'){		
	alert('Please Change the status to Delivered');
	return false;
	}
}

