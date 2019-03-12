$(document).ready(function(){
	
	$("#exDate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	$("#exBullionType").change(displayItemCode);
	$("#exBullionType").change(Calculate);
	$("#exGrossWeight").keyup(Calculate);
	$("#exRound_Off").keyup(Calculate);
	$("#exVat").keyup(Calculate);
	$("#exRate").keyup(Calculate);
	$("#exDiamondAmt").keyup(Calculate);
	$("#exdiamondWt").keyup(Calculate);
	$("#exStoneWeight").keyup(Calculate);
	$("#exRoundOff").keyup(Calculate);
	$("#exchangePopup").click(Clear_ExchangePurchase);
	addToSalesCheckBox();
	$("#exchangeinsert").click(SubmitExchangePurchase2); // Pop up in Sales Order
	$("#exchangeinsert2").click(SubmitExchangePurchase);// Pop up in Sales 
	Calculate();
	checkValues();
	$("#brate1").keyup(ratefixgrmsum);
	$("#cash_Amount").keyup(ratefixgrmsum);
	$("#cheque_Amount").keyup(ratefixgrmsum);
	$("#card_Amount").keyup(ratefixgrmsum);
	$("#exchangeGrams").keyup(ratefixgrmsum);
	$("#voucher_Amount").keyup(ratefixgrmsum);
	$("#exchangeAmount").keyup(ratefixamtsum);
	$("#bullionType1").change(ratefixgrmsum);
	
	$("#exLessGrams").change(enableLessField);
	$("#exLessPer").change(enableLessField);
	$("#exLessValue").keyup(exCalcLess);
	$("#exGrossWeight").keyup(exCalcLess);
	$("#exStoneWeight").keyup(exCalcLess);
	$("#exdiamondWt").keyup(exCalcLess);
	if($("#exLessGrams").is(':checked') == true || $("#exLessPer").is(':checked') == true){
		$("#exLessValue").removeAttr('readonly','readonly');
	}else{
		$("#exLessValue").attr('readonly','readonly');
	}
});


function exCalcLess(){
	
	var netWt = $("#exNetWeight").val();
	if(netWt.length == 0 || netWt == 'NaN' || netWt == null){
		netWt = 0.00;			
	}
	var lessValue = $("#exLessValue").val();
	if(lessValue.length == 0 || lessValue == 'NaN' || lessValue == null){
		lessValue = 0.00;			
	}
	var grossWt = document.getElementById('exGrossWeight').value;
	if(grossWt.length == 0){
		grossWt = 0.000;
	}
	var d_StoneWt = document.getElementById('exdiamondWt').value;
	if(d_StoneWt.length == 0){
		d_StoneWt = 0.000;
	}
	var stoneWeight = document.getElementById('exStoneWeight').value;
	if(stoneWeight.length == 0){
		stoneWeight = 0.00;
	}
	netWt =  (parseFloat(grossWt) -  parseFloat(stoneWeight)) - parseFloat(d_StoneWt);
	if(netWt <= 0){
		if($(this).attr('id') != "exGrossWeight" && $("#exLessValue").val() != 0 && $("#exGrossWeight").val() != 0){
			alert("Warning : Net Weight can't be ZERO or Negative !");
			$("#exLessGrams").removeAttr('checked', 'checked' );
			$("#exLessPer").removeAttr('checked', 'checked' );
			//$("#exLessValue2").val("0.00");
			//$("#exLessValue").val("0.00");
			$("#exNetWeight").val( $("#exGrossWeight").val());
			Calculate();
			return false;
		}
	}else {
		if($("#exLessGrams").is(':checked') == true ){
			if(netWt > 0){
				var tempLessPer1 = parseFloat(lessValue) / parseFloat(netWt);
				var tempLessPer2 = parseFloat(tempLessPer1) * 100;
				 $("#exLessConvert").html("Converted Less % : ");
				 $("#exLessValue2").val(parseFloat(tempLessPer2).toFixed(2));	
			}else{
				alert("Net Weight can't be Negative !");
				$("#exLessConvert").html("");
				return false;
			}
		}
		if($("#exLessPer").is(':checked') == true ){
			var templessPer = parseFloat(netWt) * parseFloat(lessValue) * 0.01;
			$("#exLessConvert").html("Converted Less Grams : ");
			$("#exLessValue2").val(parseFloat(templessPer).toFixed(3));	
		}	
	}
	Calculate();
}

function enableLessField(){
	var netWt = $("#exNetWeight").val();
	if(netWt.length == 0 || netWt == 'NaN' || netWt == null){
		netWt = 0.00;			
	}
	if(netWt <= 0){
		alert("Warning : Net Weight can't be ZERO or Negative !");
		$("#exLessGrams").removeAttr('checked', 'checked' );
		$("#exLessPer").removeAttr('checked', 'checked' );
		//$("#exLessValue2").val("0.00");
		//$("#exLessValue").val("0.00");
		$("#exNetWeight").val( $("#exGrossWeight").val());
		Calculate();
		return false;
	}else {
		if($("#exLessGrams").is(':checked') == true || $("#exLessPer").is(':checked') == true){
			$("#exLessValue").removeAttr('readonly','readonly');
		}else{
			$("#exLessValue").attr('readonly','readonly');
		}
	}
	exCalcLess();
}


function checkPaymentReadonly(){
	
	if ($("#addToSales").is(":checked")) {
		$('#payment_mode').val('Credit');
		$("#exchange_purchase").val('Yes');
		$("#payment_mode").attr("disabled",true);
	} 
	if (!$("#addToSales").is(":checked")) {
		$('#payment_mode').val('Cash');
		$("#exchange_purchase").val('No');
		$("#payment_mode").attr("disabled",false);
	}
}

// Pop up in Sales
function SubmitExchangePurchase(){
	
	checkValues();
	ratefixamtsum();//add on 16-1-13
	ratefixgrmsum();//add on 16-1-13
	
	var ErrorMsg = "";
	//Purchase Date
	var date = $("#exDate").val();
	if(date == null || date == ""){
		ErrorMsg = '<span class="error"> * Purchase Date cannot be Empty. </span> <br />';
	}
	
	//Purchase Bill No
	var exPurchaseBillNo = $("#exPurchaseBillNo").val();
	if(exPurchaseBillNo == null || exPurchaseBillNo == ""){
		exPurchaseBillNo = 0;
	}
	
	//Bullion Type
	var exBullionType = $("#exBullionType").val();
	if(exBullionType == null || exBullionType == ""){
		ErrorMsg = ErrorMsg + '<span class="error"> * Please Select Bullion Type. </span> <br />';
	}
	
	//Item Qty / Set
	var qtyset = $("#qtyset").val();
	if(qtyset == null || qtyset == "" || qtyset == 0){
		ErrorMsg = ErrorMsg + '<span class="error"> * Item Quantity(Set) should be atleast 1. </span> <br />';
	}
	
	//Purchase Amount 
	var exPurchaseAmt = $("#exPurchaseAmt").val();
	if(exPurchaseAmt == null || exPurchaseAmt == "" || exPurchaseAmt == 0){
		ErrorMsg = ErrorMsg + '<span class="error"> * Purchase Amount cannot be ZERO. </span> <br />';
	}
	
	//Rate
	var exRate = $("#exRate").val();
	if(exRate == null || exRate == "" || exRate == 0){
		ErrorMsg = ErrorMsg + '<span class="error"> * Rate cannot be ZERO. </span> <br />';
	}
	
	//Gross Weight
	var exGrossWeight = $("#exGrossWeight").val();
	if(exGrossWeight == null || exGrossWeight == "" || exGrossWeight == 0){
		ErrorMsg = ErrorMsg + '<span class="error"> * Gross Weight cannot be ZERO. </span> <br />';
	}
	
	//NetWeight
	var exNetWeight = $("#exNetWeight").val();
	if(exNetWeight == null || exNetWeight == "" || exNetWeight <= 0){
		ErrorMsg = ErrorMsg + '<span class="error"> * Net Weight cannot ZERO or Negative. </span> <br />';
	}
	
	//Walk-in, Credit
	var exSupplierName = $("#exSupplierName").val();
	var payment_mode = $("#payment_mode").val();
	if(exSupplierName == "Walk-in" && payment_mode == "Credit"){
		ErrorMsg = ErrorMsg + '<span class="error"> ! No credit transaction for the Walk in Customer. </span> <br />';
	}
		
	//Purchase Amount NEGATIVE
	if(exPurchaseAmt < 0){
		ErrorMsg = ErrorMsg + '<span class="error"> * Purchase Amount cannot be NEGATIVE. </span> <br />';
	}
	
	//Roundoff
	var exRoundOff = $("#exRoundOff").val();
	if(exRoundOff == '-'){
		ErrorMsg = ErrorMsg + '<span class="error"> * Invalid RoundOff Value. </span> <br />';
	}
	
	//TotalAmt
	var exTotalAmt = $("#exTotalAmt").val();
	if(exTotalAmt <= 0){
		ErrorMsg = ErrorMsg + '<span class="error"> * Total Amount Value Cannot be ZERO or Negative. </span> <br />';
	}
	
	//Less Radio
	var exLess = "";
	if($("#exLessGrams").is(':checked') == true ){
		exLess = $("#exLessGrams").val();
	}
	
	if($("#exLessPer").is(':checked') == true ){
		exLess = $("#exLessPer").val();
	}
	
	if ($("#addToSales").is(":checked")) {
		$('#payment_mode').val('Credit');
		$("#exchange_purchase").val('Yes');
	}
	if(ErrorMsg != ""){
		$("#ExPurchaseErrorMsg").html(ErrorMsg);
		return false;
	}else{
		$.ajax(
		{
			type:'POST',
			url:'exformpurchase.htm',
			data:{purchaseType: $("#exPurchaseType").val(), purchaseDate:date , purchasebillNO:exPurchaseBillNo, supplierName:exSupplierName,
				  bullionType:exBullionType, itemCode : $("#exItemCode").val(), itemName:$("#exItemName").val(), numberOfPieces:$("#qtyset").val(),
				  grossWeight:exGrossWeight, netWeight:exNetWeight, stoneWeight:$("#exStoneWeight").val(), less:exLess,
				  lessValue:$("#exLessValue").val(), lessValue2:$("#exLessValue2").val(),
				  diamondStone:$("#exdiamondName").val(), diamondStoneWt:$("#exdiamondWt").val(), stoneAmount:$("#exDiamondAmt").val(),
				  stoneTotalAmount:$("#exTotalStoneAmt").val(), paymentMode:payment_mode, rate:exRate, purchaseAmount:exPurchaseAmt, 
				  vatPercentage:$("#exVat").val(), vatAmount:$("#exVatAmt").val(), roundOff:$("#exRoundOff").val(), totalAmount:$("#exTotalAmt").val(),
				  addToSales:$("#addToSales").val(), exchangePurchase:$("#exchange_purchase").val(), purDescription:$("#purDescription").val()
				  },
			success : function(data) 
			{
				addPEGramsAmt();
				$(".smsg").html("Exchange Purchase '"+data+"' Saved Successfully...");
				if($("#custName").val() == exSupplierName){
					if ($("#exchange_purchase").val()=='Yes') {
					$("#billNo").append("<option value='"+data+"'>"+data+"</option>");
					return true;
					}
				}
				 $('#mask, .window').hide();
			},
			error : function(xmlHttpRequest, textStatus, errorThrown) {			
				if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
				return;
			},	
		});
	}
	$('#mask, .window').hide();
}

// Pop up in Sales Order
function SubmitExchangePurchase2(){
	
	checkValues();
	
	var ErrorMsg = "";
	//Purchase Date
	var date = $("#exDate").val();
	if(date == null || date == ""){
		ErrorMsg = '<span class="error"> * Purchase Date cannot be Empty. </span> <br />';
	}
	
	//Purchase Bill No
	var exPurchaseBillNo = $("#exPurchaseBillNo").val();
	if(exPurchaseBillNo == null || exPurchaseBillNo == ""){
		exPurchaseBillNo = 0;
	}
	
	//Bullion Type
	var exBullionType = $("#exBullionType").val();
	if(exBullionType == null || exBullionType == ""){
		ErrorMsg = ErrorMsg + '<span class="error"> * Please Select Bullion Type. </span> <br />';
	}
	
	//Item Qty / Set
	var qtyset = $("#qtyset").val();
	if(qtyset == null || qtyset == "" || qtyset == 0){
		ErrorMsg = ErrorMsg + '<span class="error"> * Item Quantity(Set) should be atleast 1. </span> <br />';
	}
	
	//Purchase Amount 
	var exPurchaseAmt = $("#exPurchaseAmt").val();
	if(exPurchaseAmt == null || exPurchaseAmt == "" || exPurchaseAmt == 0){
		ErrorMsg = ErrorMsg + '<span class="error"> * Purchase Amount cannot be ZERO. </span> <br />';
	}
	
	//Rate
	var exRate = $("#exRate").val();
	if(exRate == null || exRate == "" || exRate == 0){
		ErrorMsg = ErrorMsg + '<span class="error"> * Rate cannot be ZERO. </span> <br />';
	}
	
	//Gross Weight
	var exGrossWeight = $("#exGrossWeight").val();
	if(exGrossWeight == null || exGrossWeight == "" || exGrossWeight == 0){
		ErrorMsg = ErrorMsg + '<span class="error"> * Gross Weight cannot be ZERO. </span> <br />';
	}
	
	//NetWeight
	var exNetWeight = $("#exNetWeight").val();
	if(exNetWeight == null || exNetWeight == "" || exNetWeight <= 0){
		ErrorMsg = ErrorMsg + '<span class="error"> * Net Weight cannot ZERO or Negative. </span> <br />';
	}
	
	//Walk-in, Credit
	var exSupplierName = $("#exSupplierName").val();
	var payment_mode = $("#payment_mode").val();
	if(exSupplierName == "Walk-in" && payment_mode == "Credit"){
		ErrorMsg = ErrorMsg + '<span class="error"> ! No credit transaction for the Walk in Customer. </span> <br />';
	}
	
	//Purchase Amount NEGATIVE
	if(exPurchaseAmt < 0){
		ErrorMsg = ErrorMsg + '<span class="error"> * Purchase Amount cannot be NEGATIVE. </span> <br />';
	}
	
	//Roundoff
	var exRoundOff = $("#exRoundOff").val();
	if(exRoundOff == '-'){
		ErrorMsg = ErrorMsg + '<span class="error"> * Invalid RoundOff Value. </span> <br />';
	}
	
	//TotalAmt
	var exTotalAmt = $("#exTotalAmt").val();
	if(exTotalAmt <= 0){
		ErrorMsg = ErrorMsg + '<span class="error"> * Total Amount Value Cannot be ZERO or Negative. </span> <br />';
	}
	
	//Less Radio
	var exLess = "";
	if($("#exLessGrams").is(':checked') == true ){
		exLess = $("#exLessGrams").val();
	}
	
	if($("#exLessPer").is(':checked') == true ){
		exLess = $("#exLessPer").val();
	}
	
	if ($("#addToSales").is(":checked")) {
		$('#payment_mode').val('Credit');
		$("#exchange_purchase").val('Yes');
	}
	if(ErrorMsg != ""){
		$("#ExPurchaseErrorMsg").html(ErrorMsg);
		return false;
	}else{
		$.ajax(
				{
					type:'POST',
					url:'exformpurchase.htm',
					data:{purchaseType: $("#exPurchaseType").val(), purchaseDate:date , purchasebillNO:exPurchaseBillNo, supplierName:exSupplierName,
						bullionType:exBullionType, itemCode : $("#exItemCode").val(), itemName:$("#exItemName").val(), numberOfPieces:$("#qtyset").val(),
						grossWeight:exGrossWeight, netWeight:exNetWeight, stoneWeight:$("#exStoneWeight").val(), less:exLess,
						lessValue:$("#exLessValue").val(), lessValue2:$("#exLessValue2").val(),
						diamondStone:$("#exdiamondName").val(), diamondStoneWt:$("#exdiamondWt").val(), stoneAmount:$("#exDiamondAmt").val(),
						stoneTotalAmount:$("#exTotalStoneAmt").val(), paymentMode:payment_mode, rate:exRate, purchaseAmount:exPurchaseAmt, 
						vatPercentage:$("#exVat").val(), vatAmount:$("#exVatAmt").val(), roundOff:$("#exRoundOff").val(), totalAmount:$("#exTotalAmt").val(),
						addToSales:$("#addToSales").val(), exchangePurchase:$("#exchange_purchase").val(), purDescription:$("#purDescription").val()
					},
					success : function(data) 
					{
						addPEGramsAmt();
						$(".smsg").html("Exchange Purchase '"+data+"' Saved Successfully...");
						if($("#custName").val() == exSupplierName){
							if ($("#exchange_purchase").val()=='Yes') {
								$("#billNo").append("<option value='"+data+"'>"+data+"</option>");
								return true;
							}
						}
						$('#mask, .window').hide();
					},
					error : function(xmlHttpRequest, textStatus, errorThrown) {			
						if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
							return;
					},	
				});
	}
	$('#mask, .window').hide();
}

// Setting Default Values On Load //
function Clear_ExchangePurchase(){
	$("#exTotalAmt").val("");	
	$("#exRound_Off").val("");	
	$("#exVatAmt").val("");	
	$("#exVat").val("");	
	$("#exPurchaseAmt").val("");	
	$("#exRate").val("");	
	$("#exTotalStoneAmt").val("");	
	$("#exDiamondAmt").val("");	
	$("#exdiamondWt").val("");	
	$("#exdiamondName").val("");	
	$("#exLessValue").val("");	
	$("#exLessValue2").val("");	
	$("#exStoneWeight").val("");	
	$("#exNetWeight").val("");	
	$("#exGrossWeight").val("");	
	$("#qtyset").val("");	
	$("#exItemName").val("");	
	$("#exItemCode").val("");	
	$("#exPurchaseBillNo").val("");
	$("#exSupplierName").val("Walk-in");
	$("#exBullionType").val("");
	$("#purDescription").val("");
	///$("#addToSales").removeAttr('checked');
	$("#addToSales").attr('checked', 'checked');
	$("#exchange_purchase").val('Yes');
	checkPaymentReadonly();
	$("#ExPurchaseErrorMsg").html('');
	$("#exLessValue").removeAttr('readonly','readonly');
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var prettyDate = myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
	$("#exDate").val(prettyDate);
	$("#exTotalAmt").val("0.00");
	$("#exRoundOff").val("0.00");
	 $("#exLessConvert").html("");
	 $("#exLessGrams").removeAttr('checked');
	 $("#exLessPer").removeAttr('checked');
}

function checkValues(){
	$(".numField").each(function(){
		var currVal = $(this).val();
		if(currVal.length == 0 || currVal == 'NaN' || currVal == null){
			$(this).val(0);			
		};
	});	
}

function displayItemCode(){
	var bullionType = $("#exBullionType").val();
	if(bullionType == "Gold Exchange"){
		$("#exItemCode").val("IT100005");
		$("#exItemName").val("Gold Exchange");
	}else if(bullionType == "Silver Exchange"){
		$("#exItemCode").val("IT100010");
		$("#exItemName").val("Silver Exchange");
	}
	else if(bullionType == "Old Gold Coin"){
		$("#exItemCode").val("IT100001");
		$("#exItemName").val("Gold Ornaments");
	}
	else if(bullionType == "Old Silver Coin"){
		$("#exItemCode").val("IT100006");
		$("#exItemName").val("Silver Ornaments");
	}
	else if(bullionType == "Old Gold Pure Bullion"){
		$("#exItemCode").val("IT100002");
		$("#exItemName").val("Gold Bullion");
	}
	else if(bullionType == "Old Silver Pure Bullion"){
		$("#exItemCode").val("IT100007");
		$("#exItemName").val("Silver Bullion");
	}
	else {
		$("#exItemCode").val("");
		$("#exItemName").val("");
	}
}

function addToSalesCheckBox(){
	
	// Manually Check the Checkbox				
	if($("#exchange_purchase").val() == 'Yes' || $("#exchange_purchase").val() == 'Sold'){
		$("#addToSales").attr("checked",true);			
	}
	
	// On click of Checkbox
	$("#addToSales").click(function(){
		/** if Status sold making checkbox disabled **/
		if($("#exchange_purchase").val() == 'Sold'){
			$("#addToSales").attr("disabled",true);
			return false;
		}
		if ($("#addToSales").is(":checked")) {
			$('#payment_mode').val('Credit');
			$("#exchange_purchase").val('Yes');
			$('#payment_mode').attr("disabled", true);
			alert("'Added to Sales' and Payment Mode set to 'Credit'");			
		}else{
			$('#payment_mode').val('Cash');			
			$("#exchange_purchase").val('No');
			$('#payment_mode').attr("disabled", false);
			alert("'Not Added to Sales' and Payment Mode set to 'Cash'");
		}
	});
}

function Calculate() {
	
	var txt1 = $("#exGrossWeight").val();
	if(txt1.length == 0 || txt1 == 'NaN' || txt1 == null){
		txt1 = 0.00;			
	}
	var txt8 = $("#exStoneWeight").val();
	if(txt8.length == 0 || txt8 == 'NaN' || txt8 == null){
		txt8 = 0.00;			
	}
	var rt = $("#exRate").val();
	if(rt.length == 0 || rt == 'NaN' || rt == null){
		rt = 0.00;			
	}
	var st = $("#exDiamondAmt").val();
	if(st.length == 0 || st == 'NaN' || st == null){
		st = 0.00;			
	}
	var vat = $("#exVat").val();
	if(vat.length == 0 || vat == 'NaN' || vat == null){
		vat = 0.00;			
	}
	var d_StoneWt = $("#exdiamondWt").val();
	if(d_StoneWt.length == 0 || d_StoneWt == 'NaN' || d_StoneWt == null){
		d_StoneWt = 0.00;			
	}
	var roundOf = $("#exRoundOff").val();
	if(roundOf.length == 0 || roundOf == 'NaN' || roundOf == null){
		roundOf = 0.00;			
	}
	
	var lessValue = $("#exLessValue").val();
	if(lessValue.length == 0 || lessValue == 'NaN' || lessValue == null){
		lessValue = 0.00;			
	}
	var lessValue2 = $("#exLessValue2").val();
	if(lessValue2.length == 0 || lessValue2 == 'NaN' || lessValue2 == null){
		lessValue2 = 0.00;			
	}
	
	var d_stoneTotamt = 0;
	var FNTsum=0;		
	var amount=0;
	var vatam=0;
	var totalamount=0;	
	
	var lessGrams = 0.000;
	if($("#exLessGrams").is(':checked') == true ){
		lessGrams = lessValue;
	}
	
	if($("#exLessPer").is(':checked') == true ){
		lessGrams = lessValue2;
	}
		
	FNTsum = (parseFloat(txt1) - parseFloat (txt8)) - parseFloat(lessGrams) - parseFloat(d_StoneWt);
	$("#exNetWeight").val(FNTsum.toFixed(3));
	d_stoneTotamt = parseFloat(st * 5) * d_StoneWt;
	$("#exTotalStoneAmt").val(d_stoneTotamt.toFixed(2));
	amount = FNTsum * parseFloat(rt) + parseFloat(d_stoneTotamt);
	var tempAmount = amount.toFixed(0);
	var finalAmount = parseFloat(tempAmount);
	$("#exPurchaseAmt").val(finalAmount.toFixed(2));
	vatam = parseFloat(finalAmount) * parseFloat(vat/100);
	var tempVatAmount = vatam.toFixed(0);
	var finalVatAmount = parseFloat(tempVatAmount);			
	$("#exVatAmt").val(finalVatAmount.toFixed(2));
	totalamount=parseFloat(finalAmount) + parseFloat(finalVatAmount);
	
	//For Total Amount - Round Off
	var temp = parseFloat(totalamount) + parseFloat(roundOf);
	if(temp < 0){
		$("#exTotalAmt").val(parseFloat(totalamount).toFixed(2));
	}else {
		$("#exTotalAmt").val(parseFloat(temp).toFixed(2));
	}
}

function addPEGramsAmt(){
	var popgram=$("#exGrossWeight").val();
	var popamt=$("#exTotalAmt").val();
	$("#exchangeGrams").val(parseFloat(popgram).toFixed(3));
	$("#exchangeAmount").val(popamt);
	ratefixamtsum();
}

//add on 16-1-13
function ratefixamtsum(){
	
	//var popamt = $("#exTotalAmt").val();
	var totadvanceamt=$("#advance_paid").val();
	var ratefixamt=$("#exchangeAmount").val();
	
	if(ratefixamt.length == 0 || ratefixamt == 'NaN' || ratefixamt == null){
		ratefixamt = 0.00;			
	}
	if(totadvanceamt.length == 0 || totadvanceamt == 'NaN' || totadvanceamt == null){
		totadvanceamt = 0.00;			
	}
	var temp=parseFloat(totadvanceamt) + parseFloat(ratefixamt);
	$("#rateFixAmount").val(parseFloat(temp).toFixed(2));
	ratefixgrmsum();
}

//add on 16-1-13
function ratefixgrmsum(){

	var ratefixamt=$("#rateFixAmount").val();
	var frstboardRate=$("#brate1").val();
	var bullionType1=$("#bullionType1").val();
	if(ratefixamt.length == 0 || ratefixamt == 'NaN' || ratefixamt == null){
		ratefixamt = 0.00;			
	}
	if(frstboardRate.length == 0 || frstboardRate == 'NaN' || frstboardRate == null){
		frstboardRate = 0.00;			
	}
	if(frstboardRate!=0 && bullionType1=="Gold"){
	var temp=parseFloat(ratefixamt)/parseFloat(frstboardRate);
	$("#rateFixGrams").val(parseFloat(temp).toFixed(2));
	}else{
		$("#rateFixGrams").val("0.00");
	}
}
