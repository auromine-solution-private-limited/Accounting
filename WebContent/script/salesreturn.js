$('.itemcode_class').change(function() {
	var icode1=$(this).val();
	var subtot = $('#subtotal').val();
	
		if(icode1=='IT100001')
		{
		$(this).parents(".DeSelectClass").find("[id^=itemname]").val("Gold Ornament");
		$(this).parents(".DeSelectClass").find('[id^=catname]').val("Gold Ornament");
		$(this).parents(".DeSelectClass").find('[id^=metal]').val("Gold");
		}
	 
	if(icode1=='IT100002')
	{
	$(this).parents(".DeSelectClass").find('[id^=itemname]').val("Gold Bullion");
	$(this).parents(".DeSelectClass").find('[id^=catname]').val("Gold Bullion");
	$(this).parents(".DeSelectClass").find('[id^=metal]').val("Gold");
	}
	if(icode1=='IT100006')
	{
	$(this).parents(".DeSelectClass").find('[id^=itemname]').val('Silver Ornament');
	$(this).parents(".DeSelectClass").find('[id^=catname]').val('Silver Ornament');
	$(this).parents(".DeSelectClass").find('[id^=metal]').val('Silver');
	}
	if(icode1=='IT100007')
	{
	$(this).parents(".DeSelectClass").find('[id^=itemname]').val('Silver Bullion');
	$(this).parents(".DeSelectClass").find('[id^=catname]').val('Silver Bullion');
	$(this).parents(".DeSelectClass").find('[id^=metal]').val('Silver');
	}
	
	if(icode1=='')
	{
		$(this).parents(".DeSelectClass").find('.char_class').val('');
		$(this).parents(".DeSelectClass").find('.wt_class').val('0.000');		
		$(this).parents(".DeSelectClass").find('.pcs_class').val('0');
		$(this).parents(".DeSelectClass").find('.currency_class').val('0.00');
		sales_return();
		if($('#subtotal').val() == "0.00"){
			$('.tcurrency_class').val('0.00');
		}
	
		
	}
	return false;
});

/******for second rows******
$('#icode2').change(function() {
	//dropdown2
	var icode2=$('#icode2').val();
	if(icode2=='IT100001')
		{
		$('#itemname2').val('Gold Ornament');
		$('#catname2').val('Gold Ornament');
		$('#metal2').val('Gold');
		}
	if(icode2=='IT100002')
	{
	$('#itemname2').val('Gold Bullion');
	$('#catname2').val('Gold Bullion');
	$('#metal2').val('Gold');
	}
	if(icode2=='IT100006')
	{
	$('#itemname2').val('Silver Ornament');
	$('#catname2').val('Silver Ornament');
	$('#metal2').val('Silver');
	}
	if(icode2=='IT100007')
	{
	$('#itemname2').val('Silver Bullion');
	$('#catname2').val('Silver Bullion');
	$('#metal2').val('Silver');
	}	
	if(icode2=='')
	{
		$('.char_class').val('');
		$('.wt_class').val('0.000');
		$('.currency_class').val('0.00');
		$('.pcs_class').val('0');
	}
	return false;
});

/******for second third rows******
$('#icode3').change(function() {
	var icode3=$('#icode3').val();
	if(icode3=='IT100001')
		{
		$('#itemname3').val('Gold Ornament');
		$('#catname3').val('Gold Ornament');
		$('#metal3').val('Gold');
		}
	if(icode3=='IT100002')
	{
	$('#itemname3').val('Gold Bullion');
	$('#catname3').val('Gold Bullion');
	$('#metal3').val('Gold');
	}
	if(icode3=='IT100006')
	{
	$('#itemname3').val('Silver Ornament');
	$('#catname3').val('Silver Ornament');
	$('#metal3').val('Silver');
	}
	if(icode3=='IT100007')
	{
	$('#itemname3').val('Silver Bullion');
	$('#catname3').val('Silver Bullion');
	$('#metal3').val('Silver');
	}
	if(icode3=='')
	{
	$('#itemname3').val('');
	$('#catname3').val('');
	$('#metal3').val('');
	}
	return false;
});


$('#icode4').change(function() {
	var icode4=$('#icode4').val();
	if(icode4=='IT100001')
	{
		$('#itemname4').val('Gold Ornament');
		$('#catname4').val('Gold Ornament');
		$('#metal4').val('Gold');
	}
	if(icode4=='IT100002')
	{
	$('#itemname4').val('Gold Bullion');
	$('#catname4').val('Gold Bullion');
	$('#metal4').val('Gold');
	}
	if(icode4=='IT100006')
	{
	$('#itemname4').val('Silver Ornament');
	$('#catname4').val('Silver Ornament');
	$('#metal4').val('Silver');
	}
	if(icode4=='IT100007')
	{
	$('#itemname4').val('Silver Bullion');
	$('#catname4').val('Silver Bullion');
	$('#metal4').val('Silver');
	}	
	if(icode4=='')
	{
	$('#itemname4').val('');
	$('#catname4').val('');
	$('#metal4').val('');
	}
	return false;
});*/
function salesReturnFIrstRow1(){
	var nwt1 =$("#nwt1").val();
	var rate1 =$("#rate1").val();
	var stcost1 =$("#stcost1").val();
	var VA1 =$("#salesReturnVA1").val();
	var hc1 =$("#salesReturnHC1").val();
	var mc1 =$("#salesReturnMC1").val();
	var tax =$("#tax").val();
	
	/**
	 * NAN and null Validation 
	 * ****/
	nwt1 =	 checkNull(nwt1);
	rate1 =	 checkNull(rate1);
	stcost1 =	 checkNull(stcost1);
	VA1 =	 checkNull(VA1);
	hc1 =	 checkNull(hc1);
	mc1 =	 checkNull(mc1);
	tax =	 checkNull(tax);
	
	/**
	 * formula for calucalting VA % into Grams
	 * **/
	VA1=parseFloat(nwt1)*parseFloat(VA1)/100;
	nwt1=parseFloat(nwt1)+parseFloat(VA1);
	
	/**
	 * Formula for calculating netwt * rate 
	 * ****/
	nwt1=parseFloat(nwt1)*parseFloat(rate1);
	/**
	 * Formula for calculationg amt,subtoal ,netamtount,billamt;
	 * ***/
	var finalValue=parseFloat(nwt1)+parseFloat(mc1)+parseFloat(hc1)+parseFloat(stcost1);
	
	$("#amount1").val(finalValue.toFixed(2));
	subtotalCalc();
}
function salesReturnFIrstRow2(){
	var nwt2 =$("#nwt2").val();
	var rate2 =$("#rate2").val();
	var stcost2 =$("#stcost2").val();
	var VA2 =$("#salesReturnVA2").val();
	var hc2 =$("#salesReturnHC2").val();
	var mc2 =$("#salesReturnMC2").val();

	/**
	 * NAN and null Validation 
	 * ****/
	nwt2 =	 checkNull(nwt2);
	rate2 =	 checkNull(rate2);
	stcost2 =	 checkNull(stcost2);
	VA2 =	 checkNull(VA2);
	hc2 =	 checkNull(hc2);
	mc2 =	 checkNull(mc2);
	tax =	 checkNull(tax);

	/**
	 * formula for calucalting VA % into Grams
	 * **/
	VA2=parseFloat(nwt2)*parseFloat(VA2)/100;
	nwt2=parseFloat(nwt2)+parseFloat(VA2);
	
	/**
	 * Formula for calculating netwt * rate 
	 * ****/
	nwt2=parseFloat(nwt2)*parseFloat(rate2);
	/**
	 * Formula for calculationg amt,subtoal ,netamtount,billamt;
	 * ***/
	var finalValue=parseFloat(nwt2)+parseFloat(mc2)+parseFloat(hc2)+parseFloat(stcost2);
	
	
	$("#amount2").val(finalValue.toFixed(2));
	subtotalCalc();
}
function salesReturnFIrstRow3(){
	var nwt3 =$("#nwt3").val();
	var rate3 =$("#rate3").val();
	var stcost3 =$("#stcost3").val();
	var VA3 =$("#salesReturnVA3").val();
	var hc3 =$("#salesReturnHC3").val();
	var mc3 =$("#salesReturnMC3").val();
	var tax =$("#tax").val();
	
	/**
	 * NAN and null Validation 
	 * ****/
	nwt3 =	 checkNull(nwt3);
	rate3 =	 checkNull(rate3);
	stcost3 =	 checkNull(stcost3);
	VA3 =	 checkNull(VA3);
	hc3 =	 checkNull(hc3);
	mc3 =	 checkNull(mc3);
	tax =	 checkNull(tax);
	
	/**
	 * formula for calucalting VA % into Grams
	 * **/
	VA3=parseFloat(nwt3)*parseFloat(VA3)/100;
	nwt3=parseFloat(nwt3)+parseFloat(VA3);
	
	/**
	 * Formula for calculating netwt * rate 
	 * ****/
	nwt3=parseFloat(nwt3)*parseFloat(rate3);
	/**
	 * Formula for calculationg amt,subtoal ,netamtount,billamt;
	 * ***/
	var finalValue=parseFloat(nwt3)+parseFloat(mc3)+parseFloat(hc3)+parseFloat(stcost3);
	
	$("#amount3").val(finalValue.toFixed(2));
	subtotalCalc();
}
function salesReturnFIrstRow4(){
	var nwt4 =$("#nwt4").val();
	var rate4 =$("#rate4").val();
	var stcost4 =$("#stcost4").val();
	var VA4 =$("#salesReturnVA4").val();
	var hc4 =$("#salesReturnHC4").val();
	var mc4 =$("#salesReturnMC4").val();
	var tax =$("#tax").val();
	
	/**
	 * NAN and null Validation 
	 * ****/
	nwt4 =	 checkNull(nwt4);
	rate4 =	 checkNull(rate4);
	stcost4 =	 checkNull(stcost4);
	VA4 =	 checkNull(VA4);
	hc4 =	 checkNull(hc4);
	mc4 =	 checkNull(mc4);
	tax =	 checkNull(tax);
	
	/**
	 * formula for calucalting VA % into Grams
	 * **/
	VA4=parseFloat(nwt4)*parseFloat(VA4)/100;
	nwt4=parseFloat(nwt4)+parseFloat(VA4);
	
	/**
	 * Formula for calculating netwt * rate 
	 * ****/
	nwt4=parseFloat(nwt4)*parseFloat(rate4);
	/**
	 * Formula for calculationg amt,subtoal ,netamtount,billamt;
	 * ***/
	var finalValue=parseFloat(nwt4)+parseFloat(mc4)+parseFloat(hc4)+parseFloat(stcost4);
	
	$("#amount4").val(finalValue.toFixed(2));
	subtotalCalc();
}
function subtotalCalc(){
	var amt1=$("#amount1").val();
	var amt2=$("#amount2").val();
	var amt3=$("#amount3").val();
	var amt4=$("#amount4").val();
	
	amt1 =	 checkNull(amt1);
	amt2 =	 checkNull(amt2);
	amt3 =	 checkNull(amt3);
	amt4 =	 checkNull(amt4);
	
	var finalvalue=parseFloat(amt1)+parseFloat(amt2)+parseFloat(amt3)+parseFloat(amt4);
	$("#subtotal").val(finalvalue.toFixed(2));
	netAmtCalc();
}
function netAmtCalc(){
	var subtotal=$("#subtotal").val();
	var tax=$("#tax").val();
	
	subtotal =	 checkNull(subtotal);
	tax =	 checkNull(tax);
	/***
	 * net amount calculation formula 
	 * ***/
	var finalvalue=parseFloat(subtotal)+parseFloat(tax);
	$("#netamount").val(finalvalue.toFixed(2));
	billAmtCalc();
}

function billAmtCalc(){
	var netamt=$("#netamount").val();
	var discount=$("#tradediscount").val();
	
	netamt =	 checkNull(netamt);
	discount =	 checkNull(discount);
	
	/**
	 * Billamt formula 
	 * ****/
	var finalvalue=parseFloat(netamt)-parseFloat(discount);
	$("#billamount").val(finalvalue.toFixed(2));
}

/*function sales_return()
{   this code is comment after sgh changes and as well as product .new functions have implemented 
     18-06-13
		var nwt1 = document.getElementById('nwt1').value;
		var rate1 = document.getElementById('rate1').value;
		var stcost1 = document.getElementById('stcost1').value;
		var amount1=0;
		var subtot=0;
		var netamt=0;
		var billamt=0;
		
		amount1=nwt1*rate1;
		amount1=amount1+parseFloat(stcost1);
		subtot=subtot+amount1;
		netamt=netamt+amount1;
		billamt=billamt+amount1;
		
		document.getElementById('amount1').value=amount1.toFixed(2);
		document.getElementById('subtotal').value=subtot.toFixed(2);
		document.getElementById('netamount').value=netamt.toFixed(2);
		document.getElementById('billamount').value=billamt.toFixed(2);
		
		var nwt2 = document.getElementById('nwt2').value;
		var rate2 = document.getElementById('rate2').value;
		var stcost2 = document.getElementById('stcost2').value;
		var amount2=0;
		
		amount2=nwt2*rate2;
		amount2=amount2+parseFloat(stcost2);
		subtot=subtot+amount2;
		netamt=netamt+amount2;
		billamt=billamt+amount2;
		document.getElementById('amount2').value=amount2.toFixed(2);
		document.getElementById('subtotal').value=subtot.toFixed(2);
		document.getElementById('netamount').value=netamt.toFixed(2);
		document.getElementById('billamount').value=billamt.toFixed(2);
		
		
		var nwt3 = document.getElementById('nwt3').value;
		var rate3 = document.getElementById('rate3').value;
		var stcost3 = document.getElementById('stcost3').value;
		var amount3=0;
		
		amount3=nwt3*rate3;
		amount3=amount3+parseFloat(stcost3);
		subtot=subtot+amount3;
		netamt=netamt+amount3;
		billamt=billamt+amount3;
		document.getElementById('amount3').value=amount3.toFixed(2);
		document.getElementById('subtotal').value=subtot.toFixed(2);
		document.getElementById('netamount').value=netamt.toFixed(2);
		document.getElementById('billamount').value=billamt.toFixed(2);
		
		var nwt4 = document.getElementById('nwt4').value;
		var rate4 = document.getElementById('rate4').value;
		var stcost4 = document.getElementById('stcost4').value;
		var amount4=0;
		
		amount4=nwt4*rate4;
		amount4=amount4+parseFloat(stcost4);
		subtot=subtot+amount4;
		netamt=netamt+amount4;
		billamt=billamt+amount4;
		document.getElementById('amount4').value=amount4.toFixed(2);
		document.getElementById('subtotal').value=subtot.toFixed(2);
		document.getElementById('netamount').value=netamt.toFixed(2);
		document.getElementById('billamount').value=billamt.toFixed(2);
		
		var vat = document.getElementById('tax').value;                           
		var tradediscount= document.getElementById('tradediscount').value;		
		var subtotal=amount1+amount2+amount3+amount4;
		
		document.getElementById('subtotal').value=subtotal.toFixed(2);
		if(vat.length==0)
			{}
		else if(!isNaN(vat))
			{
			var netamount = amount1+amount2+amount3+amount4+parseFloat(vat);
			document.getElementById('netamount').value=netamount.toFixed(2);
			}
		if(vat.length==0 ||tradediscount.length==0)
		{}
		else if(!isNaN(tradediscount) ||!isNaN(vat))
			{
			var billamount = netamount;
			var fixed=billamount-parseFloat(tradediscount);
			document.getElementById('billamount').value=fixed.toFixed(2);	
			}
		
}*/


function calculateLess(){ 
    var totBillAmount = document.getElementById("totBillAmount").value;
    var tradeLess = document.getElementById("tradeDiscount").value;
    var netAmount = document.getElementById("netAmount");
    netAmount.value = totBillAmount - tradeLess;
    
}

function notEmpty(elem, helperMsg){
	if(elem.value.length == 0){
		alert(helperMsg);
		elem.focus();
		return false;
	}
	return true;
}
$(document).ready(function(){
	
	$("#insert").click(disCount);
	$("#gwt1").keyup(salesReturnFIrstRow1);
	$("#gwt2").keyup(salesReturnFIrstRow2);
	$("#gwt3").keyup(salesReturnFIrstRow3);
	$("#gwt4").keyup(salesReturnFIrstRow4);
	
});
function disCount()
{
	
	var billAmt=$('#billamount').val();
	if($('#salesmanName').val()!='' && $('#customerName').val()!='' && $('#icode1').val()!='')
		{
		if(billAmt<=0)
		{
		alert("Bill Amount can't be zero!");
		return false;
		}
		}
	
}