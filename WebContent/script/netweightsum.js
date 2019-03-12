$(document).ready(function(){
	
	$("#lessGrams").change(enableLessField);
	$("#lessPer").change(enableLessField);
	$("#lessValue").keyup(calcLess);
	$("#wt").keyup(calcLess);
	$("#t2").keyup(calcLess);
	$("#dWt").keyup(calcLess);
	$("#round_Off").keyup(calcLess);
		
	if($("#lessGrams").is(':checked') == true || $("#lessPer").is(':checked') == true){
		$("#lessValue").removeAttr('readonly','readonly');
	}else{
		$("#lessValue").attr('readonly','readonly');
	}
	if($("#update").is(':visible') == true){
		calcLess();
	}
});

function calcLess(){
	
	var netWt = $("#f").val();
	if(netWt.length == 0 || netWt == 'NaN' || netWt == null){
		netWt = 0.00;			
	}
	var lessValue = $("#lessValue").val();
	if(lessValue.length == 0 || lessValue == 'NaN' || lessValue == null){
		lessValue = 0.00;			
	}
	var grossWt = document.getElementById('wt').value;
	if(grossWt.length == 0){
		grossWt = 0.000;
	}
	var d_StoneWt = document.getElementById('dWt').value;
	if(d_StoneWt.length == 0){
		d_StoneWt = 0.000;
	}
	var stoneWeight = document.getElementById('t2').value;
	if(stoneWeight.length == 0){
		stoneWeight = 0.00;
	}
	netWt =  (parseFloat(grossWt) -  parseFloat(stoneWeight)) - parseFloat(d_StoneWt);
	if(netWt <= 0){
		if($(this).attr('id') != "wt" && $("#lessValue").val() != 0 && $("#wt").val() != 0){
			$("#lessGrams").removeAttr('checked', 'checked' );
			$("#lessPer").removeAttr('checked', 'checked' );
			$("#lessValue2").val("0.00");
			$("#lessValue").val("0.00");
			$("#f").val( $("#wt").val());
			sum();
			alert("Warning : Net Weight can't be ZERO or Negative");
			return false;
		}
	}else {
		if($("#lessGrams").is(':checked') == true ){
			$("#lessConvert").html("Converted Less % : ");
			$("#lessValue").keyup(extractNumber(this,3,true));
			if(netWt > 0){
				var tempLessPer1 = parseFloat(lessValue) / parseFloat(netWt);
				var tempLessPer2 = parseFloat(tempLessPer1) * 100;
				 
				 $("#lessValue2").val(parseFloat(tempLessPer2).toFixed(2));	
			}else{
				alert("Net Weight can't be Negative !");
				$("#lessConvert").html("");
				return false;
			}
		}
		if($("#lessPer").is(':checked') == true ){
			$("#lessConvert").html("Converted Less Grams : ");
			$("#lessValue").keyup(extractNumber(this,2,true));
			var templessPer = parseFloat(netWt) * parseFloat(lessValue) * 0.01;
			$("#lessValue2").val(parseFloat(templessPer).toFixed(3));	
		}	
	}
	sum();
}

function enableLessField(){
	var netWt = $("#f").val();
	if(netWt.length == 0 || netWt == 'NaN' || netWt == null){
		netWt = 0.00;			
	}
	
	if(netWt <= 0){
		$("#lessGrams").removeAttr('checked', 'checked' );
		$("#lessPer").removeAttr('checked', 'checked' );
		$("#lessValue2").val("0.00");
		$("#lessValue").val("0.00");
		$("#f").val( $("#wt").val());
		sum();
		alert("Warning : Net Weight can't be ZERO or Negative !");
		return false;
	}else {
		if($("#lessGrams").is(':checked') == true || $("#lessPer").is(':checked') == true){
			$("#lessValue").removeAttr('readonly','readonly');
		}else{
			$("#lessValue").attr('readonly','readonly');
		}
	}
	calcLess();
}

function sum() {
	
		var grossWt = document.getElementById('wt').value;
		if(grossWt.length == 0){
			grossWt = 0.000;
		}
		var touch = document.getElementById('t').value;
		if(touch.length == 0){
			touch = 0.00;
		}
		var melting = document.getElementById('m').value;
		if(melting.length == 0){
			melting = 0.00;
		}
		var stoneWeight = document.getElementById('t2').value;
		if(stoneWeight.length == 0){
			stoneWeight = 0.00;
		}
		var rt = document.getElementById('r').value;
		if(rt.length == 0){
			rt = 0.00;
		}
		var st = document.getElementById('s').value;
		if(st.length == 0){
			st = 0.00;
		}
		var vat = document.getElementById('vt').value;
		if(vat.length == 0){
			vat = 0.00;
		}
		var d_StoneWt = document.getElementById('dWt').value;
		if(d_StoneWt.length == 0){
			d_StoneWt = 0.000;
		}
		var pst_wt = document.getElementById('p_swt').value;
		if(pst_wt.length == 0){
			pst_wt = 0.000;
		}
		var pst_pcs = document.getElementById('p_spcs').value;
		if(pst_pcs.length == 0){
			pst_pcs = 0;
		}
		var pst_rate = document.getElementById('p_srate').value;
		if(pst_rate.length == 0){
			pst_rate = 0.00;
		}
		var sst_wt = document.getElementById('s_swt').value;
		if(sst_wt.length == 0){
			sst_wt = 0.000;
		}
		var sst_pcs = document.getElementById('s_spcs').value;
		if(sst_pcs.length == 0){
			sst_pcs = 0.000;
		}
		var sst_rate = document.getElementById('s_srate').value;
		if(sst_rate.length == 0){
			sst_rate = 0.00;
		}
		var round_Off = document.getElementById('round_Off').value;
		if(round_Off.length == 0 || round_Off == 'NaN' || round_Off == null){
			round_Off = 0.00;			
		}
		var pMcAmt = document.getElementById('pMcAmt').value;
		if(pMcAmt.length == 0 || pMcAmt == 'NaN' || pMcAmt == null){
			pMcAmt = 0.00;			
		}
		var lessValue = $("#lessValue").val();
		if(lessValue.length == 0 || lessValue == 'NaN' || lessValue == null){
			lessValue = 0.00;			
		}
		var lessValue2 = $("#lessValue2").val();
		if(lessValue2.length == 0 || lessValue2 == 'NaN' || lessValue2 == null){
			lessValue2 = 0.00;			
		}
		var netWt = $("#f").val();
		if(netWt.length == 0 || netWt == 'NaN' || netWt == null){
			netWt = 0.00;			
		}
		var d_stoneTotamt = 0;
		var st_totamt = 0;
		var FNTsum=0;		
		var amount=0;
		var vatam=0;
		var totalamount=0;		
		
		var open=document.getElementById("bullion1").value;	
		if(open=='Gold Exchange' || open=='Silver Exchange' || open=='Old Gold Coin' || open=='Old Silver Coin' || open=='Old Gold Pure Bullion' || open=='Old Silver Pure Bullion')
		{	
						
			var lessGrams = 0.000;
			if($("#lessGrams").is(':checked') == true ){
				lessGrams = lessValue;
			}
			
			if($("#lessPer").is(':checked') == true ){
				lessGrams = lessValue2;
			}	
			/** FNTsum= (grossWt-(grossWt * parseFloat(stoneWeight*0.01))-(grossWt* parseFloat(lessGrams*0.01))- d_StoneWt); */
			FNTsum = (parseFloat(grossWt) -  parseFloat(stoneWeight)) -  parseFloat(lessGrams) - parseFloat(d_StoneWt);
			
			document.getElementById('f').value = FNTsum.toFixed(3);
			d_stoneTotamt = parseFloat(st * 5) * d_StoneWt;
			document.getElementById('stTotAmt').value = d_stoneTotamt.toFixed(2);
			amount = FNTsum * parseFloat(rt) + parseFloat(d_stoneTotamt);
			
			var tempAmount = amount.toFixed(0);
			var finalAmount = parseFloat(tempAmount);
			document.getElementById('amount').value = finalAmount.toFixed(2);
			
			vatam = parseFloat(finalAmount) * parseFloat(vat/100);
			var tempVatAmount = vatam.toFixed(0);
			var finalVatAmount = parseFloat(tempVatAmount);			
			document.getElementById('vatamount').value = finalVatAmount.toFixed(2);
			
			totalamount = parseFloat(finalAmount) + parseFloat(finalVatAmount);
		}		
		if(open=='Gold Ornaments' || open=='Silver Ornaments')
		{
			if(pst_wt > 0)
			{
				document.getElementById('p_swt').value = pst_wt;
				document.getElementById('p_spcs').value = 0;	
			}			
			if(pst_pcs > 0)
			{
				document.getElementById('p_spcs').value = pst_pcs;
				document.getElementById('p_swt').value = 0;
			}
			if(pst_wt > 0)
			{		
				document.getElementById('p_spcs').value = 0;
				d_stoneTotamt = parseFloat(pst_wt) * 5 * parseFloat(pst_rate);				
			}
			if(pst_pcs > 0)
			{					
				document.getElementById('p_swt').value = 0;
				d_stoneTotamt = parseFloat(pst_pcs)* parseFloat(pst_rate);				
			}
			document.getElementById('stTotAmt').value = d_stoneTotamt;
			if(sst_wt > 0)
			{
				document.getElementById('s_swt').value = sst_wt;
				document.getElementById('s_spcs').value = 0;
			}
			if(sst_pcs > 0)
			{				
				document.getElementById('s_spcs').value = sst_pcs;
				document.getElementById('s_swt').value = 0;
			}
			
			if(sst_wt > 0)
			{		
				document.getElementById('s_spcs').value = 0;
				st_totamt =  parseFloat(sst_wt) * 5 * parseFloat(sst_rate);			
				document.getElementById('stTotAmt').value = d_stoneTotamt;					
			}
			
			if(sst_pcs > 0)
			{
				document.getElementById('s_swt').disabled = 0;
				st_totamt = parseFloat(sst_pcs)* parseFloat(sst_rate);			
				document.getElementById('stTotAmt').value = d_stoneTotamt;			
			}
			
			d_stoneTotamt = d_stoneTotamt + st_totamt;
			
			FNTsum=grossWt*((touch/100)+(melting/100));		
			document.getElementById('f').value =FNTsum.toFixed(3);
			document.getElementById('stTotAmt').value = d_stoneTotamt.toFixed(2);
			amount = FNTsum * parseFloat(rt) + parseFloat(d_stoneTotamt) + parseFloat(pMcAmt);
			
			var tempAmount = amount.toFixed(0);
			var finalAmount = parseFloat(tempAmount);
			document.getElementById('amount').value = finalAmount.toFixed(2);
											
			vatam=amount*parseFloat(vat/100);
			var tempVatAmount = vatam.toFixed(0);
			var finalVatAmount = parseFloat(tempVatAmount);			
			document.getElementById('vatamount').value = finalVatAmount.toFixed(2);
			totalamount=parseFloat(finalAmount)+parseFloat(finalVatAmount);
		}
		
		if(open=='Gold Bullion' || open=='Silver Bullion' )
		{
			FNTsum=grossWt*((touch/100)+(melting/100));		
			document.getElementById('f').value =FNTsum.toFixed(3);			
			amount=FNTsum*parseFloat(rt);
			
			var tempAmount = amount.toFixed(0);
			var finalAmount = parseFloat(tempAmount);
			document.getElementById('amount').value = finalAmount.toFixed(2);
			
			vatam=finalAmount*parseFloat(vat/100);
			var tempVatAmount = vatam.toFixed(0);
			var finalVatAmount = parseFloat(tempVatAmount);
			document.getElementById('vatamount').value = finalVatAmount.toFixed(2);
			
			totalamount=parseFloat(finalAmount)+parseFloat(finalVatAmount);
		}	
		/**** After all calculations setting totalAmount and Roundoff values***/
		//For Total Amount - Round Off
		var temp = parseFloat(totalamount) + parseFloat(round_Off);
		if(temp < 0){
			$("#total").val(parseFloat(totalamount).toFixed(2));
		}else {
			$("#total").val(parseFloat(temp).toFixed(2));
		}
}

function bulliontype1()
{
	var open=document.getElementById("bullion1").value;	
	if(open=='Gold Ornaments' || open=='Old Gold Coin')
	{
	document.getElementById("code1").value ='IT100001';	
	document.getElementById("itemname1").value ='Gold Ornaments';
	
	document.getElementById('t').disabled = false;
	document.getElementById('m').disabled = false;
	document.getElementById('t2').disabled = true;
	document.getElementById('m2').disabled = true;
	document.getElementById('dSt').disabled = true;
	document.getElementById('dWt').disabled = true;
	document.getElementById('s').disabled = true;
	document.getElementById('pst').disabled = false;
	document.getElementById('p_swt').disabled = false;
	document.getElementById('p_spcs').disabled = false;
	document.getElementById('p_srate').disabled = false;
	document.getElementById('sst').disabled = false;
	document.getElementById('s_swt').disabled = false;
	document.getElementById('s_spcs').disabled = false;
	document.getElementById('s_srate').disabled = false;
	}
	
	if(open=='Silver Ornaments' || open=='Old Silver Coin')
	{
	document.getElementById("code1").value ='IT100006';	
	document.getElementById("itemname1").value ='Silver Ornaments';
	
	document.getElementById('t').disabled = false;
	document.getElementById('m').disabled = false;
	document.getElementById('t2').disabled = true;
	document.getElementById('m2').disabled = true;
	document.getElementById('dSt').disabled = true;
	document.getElementById('dWt').disabled = true;
	document.getElementById('s').disabled = true;
	document.getElementById('pst').disabled = false;
	document.getElementById('p_swt').disabled = false;
	document.getElementById('p_spcs').disabled = false;
	document.getElementById('p_srate').disabled = false;
	document.getElementById('sst').disabled = false;
	document.getElementById('s_swt').disabled = false;
	document.getElementById('s_spcs').disabled = false;
	document.getElementById('s_srate').disabled = false;
	}
	if(open=='Gold Bullion' || open == 'Old Gold Pure Bullion' )
	{
	document.getElementById("code1").value ='IT100002';	
	document.getElementById("itemname1").value ='Gold Bullion';
	
	document.getElementById('t').disabled = false;
	document.getElementById('m').disabled = false;
	document.getElementById('t2').disabled = true;
	document.getElementById('m2').disabled = true;
	document.getElementById('dSt').disabled = true;
	document.getElementById('dWt').disabled = true;
	document.getElementById('s').disabled = true;
	document.getElementById('pst').disabled = true;
	document.getElementById('p_swt').disabled = true;
	document.getElementById('p_spcs').disabled = true;
	document.getElementById('p_srate').disabled = true;
	document.getElementById('sst').disabled = true;
	document.getElementById('s_swt').disabled = true;
	document.getElementById('s_spcs').disabled = true;
	document.getElementById('s_srate').disabled = true;
	}
	if(open=='Silver Bullion' || open == 'Old Silver Pure Bullion')
 	{
	 document.getElementById("code1").value ='IT100007';	 
	 document.getElementById("itemname1").value ='Silver Bullion';
	 
	 	document.getElementById('t').disabled = false;
		document.getElementById('m').disabled = false;
		document.getElementById('t2').disabled = true;
		document.getElementById('m2').disabled = true;
		document.getElementById('dSt').disabled = true;
		document.getElementById('dWt').disabled = true;
		document.getElementById('s').disabled = true;
		document.getElementById('pst').disabled = true;
		document.getElementById('p_swt').disabled = true;
		document.getElementById('p_spcs').disabled = true;
		document.getElementById('p_srate').disabled = true;
		document.getElementById('sst').disabled = true;
		document.getElementById('s_swt').disabled = true;
		document.getElementById('s_spcs').disabled = true;
		document.getElementById('s_srate').disabled = true;
 	}
	if(open=='Gold Exchange')
	{
	document.getElementById("code1").value ='IT100005';	
	document.getElementById("itemname1").value ='Gold Exchange';
	
	document.getElementById('t').disabled = true;
	document.getElementById('m').disabled = true;
	document.getElementById('t2').disabled = false;
	document.getElementById('m2').disabled = false;
	document.getElementById('dSt').disabled = false;
	document.getElementById('dWt').disabled = false;
	document.getElementById('s').disabled = false;
	document.getElementById('pst').disabled = true;
	document.getElementById('p_swt').disabled = true;
	document.getElementById('p_spcs').disabled = true;
	document.getElementById('p_srate').disabled = true;
	document.getElementById('sst').disabled = true;
	document.getElementById('s_swt').disabled = true;
	document.getElementById('s_spcs').disabled = true;
	document.getElementById('s_srate').disabled = true;
	}
	if(open=='Silver Exchange')
	{
	document.getElementById("code1").value ='IT100010';	
	document.getElementById("itemname1").value ='Silver Exchange';
	
	document.getElementById('t').disabled = true;
	document.getElementById('m').disabled = true;
	document.getElementById('t2').disabled = false;
	document.getElementById('m2').disabled = false;
	document.getElementById('dSt').disabled = false;
	document.getElementById('dWt').disabled = false;
	document.getElementById('s').disabled = false;
	document.getElementById('pst').disabled = true;
	document.getElementById('p_swt').disabled = true;
	document.getElementById('p_spcs').disabled = true;
	document.getElementById('p_srate').disabled = true;
	document.getElementById('sst').disabled = true;
	document.getElementById('s_swt').disabled = true;
	document.getElementById('s_spcs').disabled = true;
	document.getElementById('s_srate').disabled = true;
	}

}

function bulliontype(){
	var open=document.getElementById("bullion").value;	
	document.getElementById("item").value =open;
	if(open=='Gold Retail')
	{
		document.getElementById("code").value ='IT100001';
	}
	 if(open=='Gold Bullion' || open =='Old Gold Pure Bullion')
	{
		document.getElementById("code").value ='IT100002';
	}
	 if(open=='Silver Retail')
	{
		document.getElementById("code").value ='IT100006';
	}
	if(open=='Silver Bullion' || open =='Old Silver Pure Bullion')
	{
		document.getElementById("code").value ='IT100007';
	}
}

function category_item()
{	
	var open=document.getElementById("cat1").value;
	document.getElementById("item1").value =open;
	var open=document.getElementById("cat2").value;
	document.getElementById("item2").value =open;
	var open=document.getElementById("cat3").value;
	document.getElementById("item3").value =open;
}
					