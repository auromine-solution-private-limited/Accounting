$(document).ready(function() {
/*function call for Receipt mode Hide and show */
		$(".SalesOrderPaymentModetrhead input").click(SalesOrderReceiptMode);
		SalesOrderReceiptMode();
		
		//****  SalesOrder Receipt Mode Calculation Method*******///
		$("#cheque_Amount").keyup(receipt_ModeCalc).trigger('keyup');
		$("#card_Amount").keyup(receipt_ModeCalc).trigger('keyup');
		$("#voucher_Amount").keyup(receipt_ModeCalc).trigger('keyup');
		$("#cash_Amount").keyup(receipt_ModeCalc).trigger('keyup');
		
		//****** for uncheck calucaltion in receipt mode *****/
		$("#card").change(receipt_ModeCalc).trigger('change');
		$("#cheque").change(receipt_ModeCalc).trigger('change');
		$("#voucher").change(receipt_ModeCalc).trigger('change');
		$("#cash").change(receipt_ModeCalc).trigger('change');
		
		
		$("#insert").click(receipt_validate);
 		$("#update").click(receipt_validate);
 		
 		
 		/// calculation for Sales Order Itemcode first row 
 		$("#grossWeight").keyup(calculation_Itemcode1).trigger('keyup');
 		$("#wastage").keyup(calculation_Itemcode1).trigger('keyup');
 		$("#m_charge").keyup(calculation_Itemcode1).trigger('keyup');
 		$("#stoneweight").keyup(calculation_Itemcode1).trigger('keyup');
 		$("#noOfPcs").keyup(calculation_Itemcode1).trigger('keyup');
 		$("#stoneRate").keyup(calculation_Itemcode1).trigger('keyup');
 		
 		/// calculation for Sales Order Itemcode second row 
 		$("#grsWht2").keyup(calculation_Itemcode2).trigger('keyup');
 		$("#wastag2").keyup(calculation_Itemcode2).trigger('keyup');
 	 	$("#m_charg2").keyup(calculation_Itemcode2).trigger('keyup');
 	    $("#stweight2").keyup(calculation_Itemcode2).trigger('keyup');
 	 	$("#noPcs2").keyup(calculation_Itemcode2).trigger('keyup');
 	  	$("#stRate2").keyup(calculation_Itemcode2).trigger('keyup');
 	  	
 	  	/// calculation for Sales Order Itemcode third row 
 	  	$("#grsWht3").keyup(calculation_Itemcode3).trigger('keyup');
 	  	$("#wastag3").keyup(calculation_Itemcode3).trigger('keyup');
 	  	$("#m_charg3").keyup(calculation_Itemcode3).trigger('keyup');
 	  	$("#stweight3").keyup(calculation_Itemcode3).trigger('keyup');
 	  	$("#noPcs3").keyup(calculation_Itemcode3).trigger('keyup');
 	  	$("#stRate3").keyup(calculation_Itemcode3).trigger('keyup');
 	  	
 	  	/// calculation for Sales Order Itemcode four row 
 	  	$("#grsWht4").keyup(calculation_Itemcode4).trigger('keyup');
 	  	$("#wastag4").keyup(calculation_Itemcode4).trigger('keyup');
 	  	$("#m_charg4").keyup(calculation_Itemcode4).trigger('keyup');
 	  	$("#stweight4").keyup(calculation_Itemcode4).trigger('keyup');
 	  	$("#noPcs4").keyup(calculation_Itemcode4).trigger('keyup');
 	  	$("#stRate4").keyup(calculation_Itemcode4).trigger('keyup');
 	  	
 	  	/// calculation for Sales Order Itemcode fifth row 
 	  	$("#grsWht5").keyup(calculation_Itemcode5).trigger('keyup');
 	  	$("#wastag5").keyup(calculation_Itemcode5).trigger('keyup');
 	  	$("#m_charg5").keyup(calculation_Itemcode5).trigger('keyup');
 	  	$("#stweight5").keyup(calculation_Itemcode5).trigger('keyup');
 	  	$("#noPcs5").keyup(calculation_Itemcode5).trigger('keyup');
 	  	$("#stRate5").keyup(calculation_Itemcode5).trigger('keyup');
 	  	
 	  	/** On Save Print Option **/
 		var salesOrderNo= $("#printId").val();
 		var printConfirm = $("#printInvoice").val();
 	
 		if(salesOrderNo == null || salesOrderNo == 'undefined' || salesOrderNo == ''){
 			salesOrderNo = null;
 		}
 		if(printConfirm == null || printConfirm == 'undefined' || printConfirm == ''){
 			printConfirm = null;
 		}
 		
 		if(printConfirm=="true" && salesOrderNo != null){
 			window.open('salesorder_slip.htm?salesid='+salesOrderNo,'','');
 			window.location.replace('newSalesOrder.htm');
 			initPrintPreview();
 		}else if(printConfirm=="false" && salesOrderNo !=null){
 			var successMsg = "Last Sales Order No : " + salesOrderNo + " Saved Successfully...";
 			$(".smsg").html(successMsg);
 			initPrintPreview();
 		}
 	  	
});


$.extend({
	  getUrlVars: function(){
	    var vars = [], hash;
	    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	    for(var i = 0; i < hashes.length; i++)
	    {
	      hash = hashes[i].split('=');
	      vars.push(hash[0]);
	      vars[hash[0]] = hash[1];
	    }
	    return vars;
	  },
	  getUrlVar: function(name){
	    return $.getUrlVars()[name];
	  }
});

function initPrintPreview(){
	$("#printId").val();
	$("#printInvoice").val();
}

/*    POS Sales ReceiptMode */
function SalesOrderReceiptMode(){
		
		
	if($("#cash").is(":checked")){
		$(".SalesOrderPaymentMode_cash").show();
	}else{
		$(".SalesOrderPaymentMode_cash").hide();
		$("#cash_Amount").val(0);
	}
	
	if($("#cheque").is(":checked")){
		$(".SalesOrderPaymentMode_cheque").show();
	}
	else{
		$(".SalesOrderPaymentMode_cheque").hide();
		$("#cheque_Amount").val(0);
	}
	if($("#card").is(":checked")){
		$(".SalesOrderPaymentMode_card").show();
	}
	else{
		$(".SalesOrderPaymentMode_card").hide();
		$("#card_Amount").val(0);
	}
	if($("#voucher").is(":checked")){
		$(".SalesOrderPaymentMode_voucher").show();
		
	}
	else{
		$(".SalesOrderPaymentMode_voucher").hide();
		$("#voucher_Amount").val(0);
	} 
		
}

/* Receipt Mode Calculation */
function receipt_ModeCalc(){
	
	var cashamt=0;
 	var chequeAmt = 0;
 	var cardAmt = 0;
 	var voucherAmt = 0;
 	
 	chequeAmt=$("#cheque_Amount").val();
 	cashamt=$("#cash_Amount").val();
 	
 	if(chequeAmt.length === 0 || chequeAmt === '' || chequeAmt === null){
 		chequeAmt="0.00";			
 	}
 		cardAmt=$("#card_Amount").val() ;
 		if(cardAmt.length === 0 || cardAmt === '' || cardAmt === null){
 			cardAmt="0.00";			
 		}
 			voucherAmt=$("#voucher_Amount").val();
 			
 			if(voucherAmt.length === 0 || voucherAmt === '' || voucherAmt === null){
 				voucherAmt="0.00";			
 			}
 			
 			
 			
 			if(cashamt.length === 0 || cashamt === '' || cashamt === null){
 				cashamt="0.00";			
 			}
 			
 			
 			var totRecivAmt=parseFloat(chequeAmt)+parseFloat(cardAmt)+parseFloat(voucherAmt)+parseFloat(cashamt);
 			$("#advance_paid").val(totRecivAmt.toFixed(2));
 			
 		//add new on 17-1-12 for ratefix amount calculation 
 			var popamt=$("#exchangeAmount").val();
 			var totadvanceamt=$("#advance_paid").val();
 			if (popamt == null || popamt.length == 0 || popamt == 'NaN') {
 				popamt = 0.00;
 			}
 			if (totadvanceamt == null || totadvanceamt.length == 0 || totadvanceamt == 'NaN') {
 				totadvanceamt = 0.00;
 			}
 			var temp=parseFloat(totadvanceamt) + parseFloat(popamt);
 			$("#rateFixAmount").val(parseFloat(temp).toFixed(2));
 			ratefixgrmsum();
}

/* Receipt Mode Validation */
function receipt_validate(){
	
	$(".smsg").html("");
	 
	var cashamt = $("#cash_Amount").val();
	var chqamt = $("#cheque_Amount").val();
	var cardamt = $("#card_Amount").val();
	var voucherd = $("#voucher_Amount").val();
	var customerName = $("#poscustomer").val();
	
	if(customerName == ''){
		$("#form_error").slideDown("fast");
		$("#form_error span").text(" ! Please Select Customer Name.");
		return false;
	}
	
	
	if(cashamt.length === 0 || cashamt === '' || cashamt === null){
		cashamt="0.00";			
	}

	if(chqamt.length === 0 || chqamt === '' || chqamt === null){
		chqamt="0.00";			
	}
	
	if(cardamt.length === 0 || cardamt === '' || cardamt === null){
		cardamt="0.00";			
	}

	if(voucherd.length === 0 || voucherd === '' || voucherd === null){
		voucherd="0.00";			
	}
	
	
	if ($("#cash").is(":checked")) {
		if (cashamt == 0) {
			$("#form_error").slideDown("fast");
			$("#form_error span").text("*Cash Amount Cannot Be Zero");
			return false;
		}
		if ($("#cash_Bank").val() == 0) {
			$("#form_error").slideDown("fast");
			$("#form_error span").text("*Please Select Cash  Name");
			return false;
		}
	}
	
	if ($("#cheque").is(":checked")) {
		if (chqamt == 0) {
			$("#form_error").slideDown("fast");
			$("#form_error span").text("*Cheque Amount Cannot Be Zero");
			return false;
		}
		if ($("#cheque_Bank").val() == 0) {
			$("#form_error").slideDown("fast");
			$("#form_error span").text("*Please Select Cheque Bank Name");
			return false;
		}
	}

	if ($("#card").is(":checked")) {
		if (cardamt == 0) {
			$("#form_error").slideDown("fast");
			$("#form_error span").text("*Card Amount Cannot Be Zero");
			return false;
		}
		if ($("#card_Bank").val() == 0) {
			$("#form_error").slideDown("fast");
			$("#form_error span").text("*Please Select Card Bank Name");
			return false;
		}
	}
	var sType  = $("#status").val();
	if(sType == "Sold" || sType == "Cancelled"){
		 alert('Select the status as Accepted');
		 return false;
	 }
	if ($(this).attr("id") == "insert") {
		var test = confirm(" Saving Sales Order...  Click OK to view Print preview.");
		$("#printInvoice").val(test);
	}
	
	return true;
	
	
}

function calculation_Itemcode1(){
	
	// calucaltion for itemcode1 and total amount
	var Rate=$("#brate1").val();
	var Gr_wt=$("#grossWeight").val(); 
	var V_a = $("#wastage").val();
 	var Mc =$("#m_charge").val() ;
 	var StoneWt = $("#stoneweight").val();
 	var Pcs = $("#noOfPcs").val();
  	var StoneRate =$("#stoneRate").val() ;
  	var StoneCost =$("#stoneCost").val() ;
  	
  	if(Pcs>0){
		$("#noOfPcs").val(Pcs);
		$("#stoneweight").val(0.00);
		Pcs=	checkNull(Pcs);
		StoneRate=	checkNull(StoneRate);
		StoneCost=	checkNull(StoneCost);
		var temp=parseFloat(Pcs)*parseFloat(StoneRate);
		$("#stoneCost").val(temp);
	}else if(StoneWt>0){
		$("#stoneweight").val(StoneWt);
		$("#noOfPcs").val(0);
		StoneWt =	checkNull(StoneWt);
		StoneRate=	checkNull(StoneRate);
		var temp=parseFloat(StoneWt*5) * parseFloat(StoneRate);
		$("#stoneCost").val(temp.toFixed(2));
	}
  	
  	Rate =	 checkNull(Rate);
	Gr_wt = checkNull(Gr_wt);
	V_a =	checkNull(V_a);
	Mc =	checkNull(Mc);
	StoneWt =	checkNull(StoneWt);
	Pcs=	checkNull(Pcs);
	StoneRate=	checkNull(StoneRate);
	StoneCost=	checkNull(StoneCost);
	
	var temp=parseFloat(Rate)*parseFloat(Gr_wt) + (parseFloat(V_a) * parseFloat(Gr_wt) * 0.01 * parseFloat(Rate)) + (parseFloat(Mc)*parseFloat(Gr_wt)) + parseFloat(StoneCost);
	
	$("#itemCost1").val(parseFloat(temp.toFixed(2)));
	
	Calculation_totalAmount();
}
function calculation_Itemcode2(){
	
	// calucaltion for itemcode2 
	var Rate2=$("#brate2").val();
	var Gr_wt2=$("#grsWht2").val();
	var V_a2 = $("#wastag2").val();
 	var Mc2 =$("#m_charg2").val() ;
 	var StoneWt2 = $("#stweight2").val();
 	var Pcs2 = $("#noPcs2").val();
  	var StoneRate2 =$("#stRate2").val() ;
  	var StoneCost2 =$("#stoneCost2").val() ;
  	if(Pcs2>0){
		$("#noPcs2").val(Pcs2);
		$("#stweight2").val(0.00);
		Pcs2=	checkNull(Pcs2);
		StoneRate2=	checkNull(StoneRate2);
		StoneCost2=	checkNull(StoneCost2);
		var temp2=parseFloat(Pcs2)*parseFloat(StoneRate2);
		$("#stoneCost2").val(temp2);
	}else if(StoneWt2>0){
		$("#stweight2").val(StoneWt2);
		$("#noPcs2").val(0);
		StoneWt2 =	checkNull(StoneWt2);
		StoneRate2=	checkNull(StoneRate2);
		var temp2=parseFloat(StoneWt2*5) * parseFloat(StoneRate2);
		$("#stoneCost2").val(temp2.toFixed(2));
	}
	
  	Rate2 =	 checkNull(Rate2);
	Gr_wt2 = checkNull(Gr_wt2);
	V_a2 =	checkNull(V_a2);
	Mc2 =	checkNull(Mc2);
	StoneWt2 =	checkNull(StoneWt2);
	Pcs2=	checkNull(Pcs2);
	StoneRate2=	checkNull(StoneRate2);
	StoneCost2=	checkNull(StoneCost2);
	
	var temp=parseFloat(Rate2)*parseFloat(Gr_wt2) + (parseFloat(V_a2)*parseFloat(Gr_wt2)*0.01*parseFloat(Rate2)) + (parseFloat(Mc2)*parseFloat(Gr_wt2)) + parseFloat(StoneCost2);
	
	$("#itemCost2").val(parseFloat(temp.toFixed(2)));
	Calculation_totalAmount();
}
function calculation_Itemcode3(){
	
	// calucaltion for itemcode2 and total amount
	var Rate3=$("#brate3").val();
	var Gr_wt3=$("#grsWht3").val();
	var V_a3 = $("#wastag3").val();
 	var Mc3 =$("#m_charg3").val() ;
 	var StoneWt3 = $("#stweight3").val();
 	var Pcs3 = $("#noPcs3").val();
  	var StoneRate3 =$("#stRate3").val() ;
  	var StoneCost3 =$("#stoneCost3").val() ;
  	
  	if(Pcs3>0){
		$("#noPcs3").val(Pcs3);
		$("#stweight3").val(0.00);
		Pcs3=	checkNull(Pcs3);
		StoneRate3=	checkNull(StoneRate3);
		StoneCost3=	checkNull(StoneCost3);
		var temp3=parseFloat(Pcs3)*parseFloat(StoneRate3);
		$("#stoneCost3").val(temp3);
	}else if(StoneWt3>0){
		$("#stweight3").val(StoneWt3);
		$("#noPcs3").val(0);
		StoneWt3 =	checkNull(StoneWt3);
		StoneRate3=	checkNull(StoneRate3);
		var temp3=parseFloat(StoneWt3*5) * parseFloat(StoneRate3);
		$("#stoneCost3").val(temp3.toFixed(2));
	}
  	
  	Rate3 =	 checkNull(Rate3);
	Gr_wt3 = checkNull(Gr_wt3);
	V_a3 =	checkNull(V_a3);
	Mc3 =	checkNull(Mc3);
	StoneWt3 =	checkNull(StoneWt3);
	Pcs3=	checkNull(Pcs3);
	StoneRate3=	checkNull(StoneRate3);
	StoneCost3=	checkNull(StoneCost3);
	var temp=parseFloat(Rate3)*parseFloat(Gr_wt3) + (parseFloat(V_a3)*parseFloat(Gr_wt3)*0.01*parseFloat(Rate3)) + (parseFloat(Mc3)*parseFloat(Gr_wt3)) + parseFloat(StoneCost3);
	$("#itemCost3").val(parseFloat(temp.toFixed(2)));
	
	Calculation_totalAmount();
}
function calculation_Itemcode4(){
	
	// calucaltion for itemcode4 
	var Rate4=$("#brate4").val();
	var Gr_wt4=$("#grsWht4").val();
	var V_a4 = $("#wastag4").val();
	var Mc4 =$("#m_charg4").val() ;
	var StoneWt4 = $("#stweight4").val();
	var Pcs4 = $("#noPcs4").val();
	var StoneRate4 =$("#stRate4").val() ;
	var StoneCost4 =$("#stoneCost4").val() ;
	if(Pcs4>0){
		$("#noPcs4").val(Pcs4);
		$("#stweight4").val(0.00);
		Pcs4=	checkNull(Pcs3);
		StoneRate4=	checkNull(StoneRate4);
		StoneCost4=	checkNull(StoneCost4);
		var temp4=parseFloat(Pcs4)*parseFloat(StoneRate4);
		$("#stoneCost4").val(temp4);
	}else if(StoneWt4>0){
		$("#stweight4").val(StoneWt4);
		$("#noPcs4").val(0);
		StoneWt4 =	checkNull(StoneWt4);
		StoneRate4=	checkNull(StoneRate4);
		var temp4=parseFloat(StoneWt4*5) * parseFloat(StoneRate4);
		$("#stoneCost4").val(temp4.toFixed(2));
	}
	
	Rate4 =	 checkNull(Rate4);
	Gr_wt4 = checkNull(Gr_wt4);
	V_a4 =	checkNull(V_a4);
	Mc4 =	checkNull(Mc4);
	StoneWt4 =	checkNull(StoneWt4);
	Pcs4=	checkNull(Pcs4);
	StoneRate4=	checkNull(StoneRate4);
	StoneCost4=	checkNull(StoneCost4);
	
	var temp=parseFloat(Rate4)*parseFloat(Gr_wt4) + (parseFloat(V_a4)*parseFloat(Gr_wt4)*0.01*parseFloat(Rate4)) + (parseFloat(Mc4)*parseFloat(Gr_wt4)) + parseFloat(StoneCost4);
	$("#itemCost4").val(parseFloat(temp.toFixed(2)));
	
	Calculation_totalAmount();
	
}
function calculation_Itemcode5(){
	
	// calucaltion for itemcode5 
	var Rate5=$("#brate5").val();
	var Gr_wt5=$("#grsWht5").val();
	var V_a5 = $("#wastag5").val();
	var Mc5 =$("#m_charg5").val() ;
	var StoneWt5 = $("#stweight5").val();
	var Pcs5 = $("#noPcs5").val();
	var StoneRate5 =$("#stRate5").val() ;
	var StoneCost5 =$("#stoneCost5").val() ;
	if(Pcs5>0){
		$("#noPcs5").val(Pcs5);
		$("#stweight5").val(0.00);
		Pcs5=	checkNull(Pcs5);
		StoneRate5=	checkNull(StoneRate5);
		StoneCost5=	checkNull(StoneCost5);
		var temp5=parseFloat(Pcs5)*parseFloat(StoneRate5);
		$("#stoneCost5").val(temp5);
	}else if(StoneWt5>0){
		$("#stweight5").val(StoneWt5);
		$("#noPcs5").val(0);
		StoneWt5 =	checkNull(StoneWt5);
		StoneRate5=	checkNull(StoneRate5);
		var temp5=parseFloat(StoneWt5*5) * parseFloat(StoneRate5);
		$("#stoneCost5").val(temp5.toFixed(2));
	}
	
	Rate5 =	 checkNull(Rate5);
	Gr_wt5 = checkNull(Gr_wt5);
	V_a5 =	checkNull(V_a5);
	Mc5 =	checkNull(Mc5);
	StoneWt5 =	checkNull(StoneWt5);
	Pcs5=	checkNull(Pcs5);
	StoneRate5=	checkNull(StoneRate5);
	StoneCost5=	checkNull(StoneCost5);
	
	
	var temp=parseFloat(Rate5)*parseFloat(Gr_wt5) + (parseFloat(V_a5)*parseFloat(Gr_wt5)*0.01*parseFloat(Rate5)) + (parseFloat(Mc5)*parseFloat(Gr_wt5)) + parseFloat(StoneCost5);
	$("#itemCost5").val(parseFloat(temp.toFixed(2)));
	
	Calculation_totalAmount();
}

/*** Total Amount calucation for salesOrder of all Itemcodes 5-2-13***/
function Calculation_totalAmount(){
	var itemcode1=$("#itemCost1").val();
	var itemcode2=$("#itemCost2").val();
	var itemcode3=$("#itemCost3").val();
	var itemcode4=$("#itemCost4").val();
	var itemcode5=$("#itemCost5").val();
	
	itemcode1=checkNull(itemcode1);
	itemcode2=checkNull(itemcode2);
	itemcode3=checkNull(itemcode3);
	itemcode4=checkNull(itemcode4);
	itemcode5=checkNull(itemcode5);
	
	var totTemp=parseFloat(itemcode1)+parseFloat(itemcode2)+parseFloat(itemcode3)+parseFloat(itemcode4)+parseFloat(itemcode5);
	
	$("#totalamount").val(totTemp.toFixed(2));
}

/** sales order form Total Amount calculation 
function total_Amount()
//item cost1  
{
	  	 		var Gr_wt = document.getElementById('grossWeight1').value;
	  	 		var Rate = document.getElementById('rate1').value;
   	  		 	var V_a = document.getElementById('wastage1').value;
   	  	 		var Mc = document.getElementById('m_charge1').value;
   	   			var StoneWt = document.getElementById('stoneweight1').value;
   	   			var Pcs = document.getElementById('noOfPcs1').value;
   	   			var StoneRate = document.getElementById('stoneRate1').value;
   	   			var itemcost1=0;
   	   			var stonecost=0;
   	   			if(StoneWt>0)
				{
   	   			document.getElementById('stoneweight1').value=StoneWt;
   	   			document.getElementById('noOfPcs1').value=0;
   	   			}
   	   			if(Pcs>0)
	   			{
   	   			document.getElementById('noOfPcs1').value=Pcs;
   	   			document.getElementById('stoneweight1').value=0.0;
	   			}
   	   			if(StoneWt>0)
   	   			{
   	   			document.getElementById('noOfPcs1').value=0;
	   			stonecost=(StoneWt*5)*StoneRate;
	   			document.getElementById('stoneCost1').value=stonecost.toFixed(2);
	   			 }
	   			if(Pcs>0)
	   			{
	   			document.getElementById('stoneweight1').value=0.0;
	   			stonecost=Pcs*StoneRate;
	   			document.getElementById('stoneCost1').value=stonecost.toFixed(2);
	   			}
	   			itemcost1=(Gr_wt*Rate)+(Gr_wt*(V_a*0.01)*Rate)+(Gr_wt*Mc);
   				document.getElementById('itemCost1').value=(itemcost1+stonecost).toFixed(2);
   				document.getElementById('totalamount').value=(itemcost1+stonecost).toFixed(2);
   		      //item cost2
   				var Gr_wt1 = document.getElementById('grossWeight2').value;
	  	 		var Rate1 = document.getElementById('rate2').value;
   	  		 	var V_a1 = document.getElementById('wastage2').value;
   	  	 		var Mc1 = document.getElementById('m_charge2').value;
   	   			var StoneWt1 = document.getElementById('stoneweight2').value;
   	   			var Pcs1 = document.getElementById('noOfPcs2').value;
   	   			var StoneRate1 = document.getElementById('stoneRate2').value;
   	   			var itemcost2=0;
   	   			var stonecost1=0;
   	   			if(StoneWt1>0)
				{
   	   			document.getElementById('stoneweight2').value=StoneWt1;
   	   			document.getElementById('noOfPcs2').value=0;
   	   			    	   			 }
   	   			if(Pcs1>0)
	   			{
   	   			document.getElementById('noOfPcs2').value=Pcs1;
   	   			document.getElementById('stoneweight2').value=0.0;
   	   			}
   	   			if(StoneWt1>0)
   	   			{
   	   			document.getElementById('noOfPcs2').value=0;
	   			stonecost1=(StoneWt1*5)*StoneRate1;
	   			document.getElementById('stoneCost2').value=stonecost1.toFixed(2);
	   			 }
	   			if(Pcs1>0)
	   			{
	   			document.getElementById('stoneweight2').value=0.0;
	   			stonecost1=Pcs1*StoneRate1;
	   			document.getElementById('stoneCost2').value=stonecost1.toFixed(2);
	   			}
   	   	
   				itemcost2=(Gr_wt1*Rate1)+(Gr_wt1*(V_a1*0.01)*Rate1)+(Gr_wt1*Mc1);
   				
   				document.getElementById('itemCost2').value=(itemcost2+stonecost1).toFixed(2);
   				document.getElementById('totalamount').value=(itemcost1+stonecost+itemcost2+stonecost1).toFixed(2);
   			 //item cost3
   				var Gr_wt2 = document.getElementById('grossWeight3').value;
	  	 		var Rate2= document.getElementById('rate3').value;
   	  		 	var V_a2 = document.getElementById('wastage3').value;
   	  	 		var Mc2 = document.getElementById('m_charge3').value;
   	   			var StoneWt2 = document.getElementById('stoneweight3').value;
   	   			var Pcs2 = document.getElementById('noOfPcs3').value;
   	   			var StoneRate2 = document.getElementById('stoneRate3').value;
   	   			var itemcost3=0;
   	   			var stonecost2=0;
   	   			if(StoneWt2>0)
				{
   	   			document.getElementById('stoneweight3').value=StoneWt2;
   	   			document.getElementById('noOfPcs3').value=0;
   	   			}
   	   			if(Pcs2>0)
	   			{
   	   			document.getElementById('noOfPcs3').value=Pcs2;
   	   			document.getElementById('stoneweight3').value=0;
   	   			}
   	   			if(StoneWt2>0)
   	   			{
	   			document.getElementById('noOfPcs3').value=0;
	   			stonecost2=(StoneWt2*5)*StoneRate2;
	   			document.getElementById('stoneCost3').value=stonecost2.toFixed(2);
	   			}
	   			if(Pcs2>0)
	   			{
	   			document.getElementById('stoneweight3').value=0;
	   			stonecost2=Pcs2*StoneRate2;
	   			document.getElementById('stoneCost3').value=stonecost2.toFixed(2);
	   			}
	   			itemcost3=(Gr_wt2*Rate2)+(Gr_wt2*(V_a2*0.01)*Rate2)+(Gr_wt2*Mc2);
   				document.getElementById('itemCost3').value=(itemcost3+stonecost2).toFixed(2);
   				document.getElementById('totalamount').value=(itemcost1+stonecost+itemcost2+stonecost1+itemcost3+stonecost2).toFixed(2);
   			//item cost4
   				var Gr_wt3 = document.getElementById('grossWeight4').value;
	  	 		var Rate3= document.getElementById('rate4').value;
   	  		 	var V_a3 = document.getElementById('wastage4').value;
   	  	 		var Mc3 = document.getElementById('m_charge4').value;
   	   			var StoneWt3 = document.getElementById('stoneweight4').value;
   	   			var Pcs3 = document.getElementById('noOfPcs4').value;
   	   			var StoneRate3 = document.getElementById('stoneRate4').value;
   	   			var itemcost4=0;
   	   			var stonecost3=0;
   	   			if(StoneWt3>0)
				{
   	   			document.getElementById('stoneweight4').value=StoneWt3;
   	   			document.getElementById('noOfPcs4').value=0;
   	   				}
   	   			if(Pcs3>0)
	   			{
   	   			document.getElementById('noOfPcs4').value=Pcs3;
   	   			document.getElementById('stoneweight4').value=0;
   	   			}
   	   			if(StoneWt3>0)
   	   			{
	   			
	   			document.getElementById('noOfPcs4').value=0;
	   			stonecost3=(StoneWt3*5)*StoneRate3;
	   			document.getElementById('stoneCost4').value=stonecost3.toFixed(2);
	   			}
	   			if(Pcs3>0)
	   			{
	   			document.getElementById('stoneweight4').value=0;
	   			stonecost3=Pcs3*StoneRate3;
	   			document.getElementById('stoneCost4').value=stonecost3.toFixed(2);
	   			}
   	   	 
   				itemcost4=(Gr_wt3*Rate3)+(Gr_wt3*(V_a3*0.01)*Rate3)+(Gr_wt3*Mc3);
   				
   				document.getElementById('itemCost4').value=(itemcost4+stonecost3).toFixed(2);
   				document.getElementById('totalamount').value=(itemcost1+stonecost+itemcost2+stonecost1+itemcost3+stonecost2+itemcost4+stonecost3).toFixed(2);
   			//item cost5
   				var Gr_wt4 = document.getElementById('grossWeight5').value;
	  	 		var Rate4= document.getElementById('rate5').value;
   	  		 	var V_a4 = document.getElementById('wastage5').value;
   	  	 		var Mc4 = document.getElementById('m_charge5').value;
   	   			var StoneWt4 = document.getElementById('stoneweight5').value;
   	   			var Pcs4 = document.getElementById('noOfPcs5').value;
   	   			var StoneRate4 = document.getElementById('stoneRate5').value;
   	   			var itemcost5=0;
   	   			var stonecost4=0;
   	   			if(StoneWt4>0)
				{
   	   			document.getElementById('stoneweight5').value=StoneWt4;
   	   			document.getElementById('noOfPcs5').value=0;
   	   			}
   	   			if(Pcs4>0)
	   			{
   	   			document.getElementById('noOfPcs5').value=Pcs4;
   	   			document.getElementById('stoneweight5').value=0;
   	   			}
   	   			if(StoneWt4>0)
   	   			{
	   			document.getElementById('noOfPcs5').value=0;
	   			stonecost4=(StoneWt4*5)*StoneRate4;
	   			document.getElementById('stoneCost5').value=stonecost4.toFixed(2);
	   			}
	   			if(Pcs4>0)
	   			{
	   			document.getElementById('stoneweight5').value=0;
	   			stonecost4=Pcs4*StoneRate4;
	   			document.getElementById('stoneCost5').value=stonecost4.toFixed(2);
	   			}
   	   			itemcost5=(Gr_wt4*Rate4)+(Gr_wt4*(V_a4*0.01)*Rate4)+(Gr_wt4*Mc4);
   				
   				document.getElementById('itemCost5').value=(itemcost5+stonecost4).toFixed(2);
   				document.getElementById('totalamount').value=(itemcost1+stonecost+itemcost2+stonecost1+itemcost3+stonecost2+itemcost4+stonecost3+itemcost5+stonecost4).toFixed(2);

}**/
