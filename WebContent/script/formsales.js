/** code for Date Picker */
$(document).ready(
		function() {

			$("#date").datepicker({
				showOn : 'both',
				buttonImageOnly : true,
				buttonImage : 'images/icon_cal.png',
				dateFormat : 'd/mm/yy'
			});

			var myDate = new Date();
			var month = myDate.getMonth() + 1;
			var prettyDate = myDate.getDate() + '/' + month + '/'
					+ myDate.getFullYear();
			if (document.getElementById('date').value == '') {
				document.getElementById('date').value = prettyDate;
				return true;
			} else {
				return false;
			}
			var admin = session.getParameter("username");
			$("#username").val(admin);
		});

/** to load hidden field values at document load */
$(document).ready(setRoundOff);
$(document).ready(customerName_change);
$(document).ready(row1);
$(document).ready(row2);
$(document).ready(row3);
$(document).ready(row4);
$(document).ready(row5);
$(document).ready(check_hide);

$(document).keydown(function (e){
	
	if($("#insert").is(":visible")){
		if(e.which==81 && e.ctrlKey){
			$.ajax({
				type : 'GET',
				url  : 'CheckES.htm',
				success : function(data) {
					if(data=="yes"){
						var salesType = $("#salesType").val();
						if(salesType == "Sales"){
					   	 $("#salesType").val("Estimate Sales");
					   	 $("#s").hide();
					   	 $("#s1").hide();
					   	 $("#s2").hide();
					   	 $("#s3").hide();
					   	 $("#s4").hide();
					   }else{
					   	$("#salesType").val("Sales");
					   	$("#s").show();
					  	 	$("#s1").show();
					  	 	$("#s2").show();
					  	 	$("#s3").show();
					  	 	$("#s4").show();
					   }
					}
				},
				error : function(xmlHttpRequest, textStatus, errorThrown) {
					if (xmlHttpRequest.readyState = 0 || xmlHttpRequest.status == 0)
						return;
				},
			});
		}
	}
    return true;//Pass the event on
});

function EstimateMode(){
	var salesType = $("#salesType").val();
	if((salesType == "Sales" && $("#update").is(":visible")) || salesType == "Estimate Sales"){
	   	 $("#s").hide();
	   	 $("#s1").hide();
	   	 $("#s2").hide();
	   	 $("#s3").hide();
	   	 $("#s4").hide();
	   }else if($("#billType").val()!="Cancelled" && $("#insert").is(":visible")){
	   	  	$("#s").show();
	  	 	$("#s1").show();
	  	 	$("#s2").show();
	  	 	$("#s3").show();
	  	 	$("#s4").show();
	   }
}

/** For show Roundoff in view Mode * */
function setRoundOff() {
	var tempRound = $("#roundOff").val();
	$("#rondOffReference").val(tempRound);
}

/** main Code for Entire Script */
$(document).ready(function() {
		
	if($("#salesType").val() == ""){
		$("#salesType").val("Sales");
	}
	
	/** function for purchase bill number enable disable */
	bilno();

	
	/** Roundoff field negative validation * */
	$("#roundOff").numeric();

	/**
	 * For Print Confirm Box and cash cheque voucher amount field zero
	 * validation
	 */
	$("#insert").click(initZero);
	$("#insert").click(report_confirm);
	$("#update").click(initZero);
	$("#update").click(report_confirm);

	/** For Customer Name Drop down Box */
	$("#custName").change(customerName_change);
	$("#custName").change(CustomerName_Dynamic);

	/** For Retrieving itemDetails on Search button click */
	$("#s").click(initZero);
	$("#s").click(checkrow1);
	$("#s").click(calc_billamount);
	$("#s1").click(initZero);
	$("#s1").click(checkrow2);
	$("#s2").click(initZero);
	$("#s2").click(checkrow3);
	$("#s3").click(initZero);
	$("#s3").click(checkrow4);
	$("#s4").click(initZero);
	$("#s4").click(checkrow5);

	/** for Row1 Fields Dynamic calculation */
	$("#stn").keyup(row1_change);
	$("#mcgrams").keyup(row1_change);
	$("#valueAdditionCharges").keyup(row1_change);
	$("#brate").keyup(row1_change);
	$("#grwt").keyup(row1_change);
	$("#lessAmt").keyup(calc_discount1);
	$("#vat").keyup(row1_change);
	$("#amountAfterLess").keyup(row1_change);

	/** for Row2 Fields Dynamic calculation */
	$("#stn1").keyup(row2_change);
	$("#mcgrams1").keyup(row2_change);
	$("#valueAdditionCharges1").keyup(row2_change);
	$("#brate1").keyup(row2_change);
	$("#grwt1").keyup(row2_change);
	$("#lessAmt1").keyup(calc_discount2);
	$("#vat1").keyup(row2_change);
	$("#amountAfterLess1").keyup(row2_change);

	/** for Row3 Fields Dynamic calculation */
	$("#stn2").keyup(row3_change);
	$("#mcgrams2").keyup(row3_change);
	$("#valueAdditionCharges2").keyup(row3_change);
	$("#brate2").keyup(row3_change);
	$("#grwt2").keyup(row3_change);
	$("#lessAmt2").keyup(calc_discount3);
	$("#vat2").keyup(row3_change);
	$("#amountAfterLess2").keyup(row3_change);

	/** for Row4 Fields Dynamic calculation */
	$("#stn3").keyup(row4_change);
	$("#mcgrams3").keyup(row4_change);
	$("#valueAdditionCharges3").keyup(row4_change);
	$("#brate3").keyup(row4_change);
	$("#grwt3").keyup(row4_change);
	$("#lessAmt3").keyup(calc_discount4);
	$("#vat3").keyup(row4_change);
	$("#amountAfterLess3").keyup(row4_change);

	/** for Row5 Fields Dynamic calculation */
	$("#stn4").keyup(row5_change);
	$("#mcgrams4").keyup(row5_change);
	$("#valueAdditionCharges4").keyup(row5_change);
	$("#brate4").keyup(row5_change);
	$("#grwt4").keyup(row5_change);
	$("#lessAmt4").keyup(calc_discount5);
	$("#vat4").keyup(row5_change);
	$("#amountAfterLess4").keyup(row5_change);

	/** For roundOff calculation */
	$("#roundOff").keyup(calc_roundOffAmt);

	var URIloc = $(location).attr('href');
	var uripath = URIloc.split('/')[4];

	if (uripath.indexOf("viewSales.htm") >= 0) {
		update_Mode();
	} else if ($("#errorName").val() == "updateError") {
		update_Mode();
	} else {
		$("#insert").show();
		$("#update").hide();
		$("#s").show();
		$("#s1").show();
		$("#s2").show();
		$("#s3").show();
		$("#s4").show();
		$("#icode").removeAttr("readonly", "readonly");
		$("#icode1").removeAttr("readonly", "readonly");
		$("#icode2").removeAttr("readonly", "readonly");
		$("#icode3").removeAttr("readonly", "readonly");
		$("#icode4").removeAttr("readonly", "readonly");
		$("#cash").attr("checked",true);
		$(".PaymentModeHead .table tr.cash").show();
	}
	
	$("#cancelSales").hide();
	if($("#salesType").val() == "Estimate Sales" && $("#update").is(":visible")){
		$("#cancelSales").show();
	}

	$("#adjustmentAmt").keyup(calcAdjustment);
	$("#com_percentage").keyup(calc_commision);//added for narayana new reqrmnt 25/10/12
  	$("#com_Amount").keyup(calc_commision_amount);//added for narayana new reqrmnt 30/10/12
  	$("#calCard_Amount").keyup(calc_commision);//added for narayana new reqrmnt 30/10/12

  	/** checkbox validation */
	$("#cheque_Amount").keyup(payment_Mode);
	$("#cash_Amount").keyup(payment_Mode);
	$("#card_Amount").keyup(payment_Mode);
	$("#voucher_Amount").keyup(payment_Mode);
	$("#exchange_Amount").keyup(payment_Mode);
	$("#order_Amount").keyup(payment_Mode);

	$("#cash").click(payment_Mode);
	$("#cheque").click(payment_Mode);
	$("#card").click(payment_Mode);
	$("#voucher").click(payment_Mode);

	$("#billNo").change(doAjax);
	$("#salesOrderIds").change(ajaxSalesAdvance);
	$("#salesReturnId").change(getSRamount);//add for SR amt on 14-02-13

	ajaxSalesAdvance(); //added for ratefix on 25-1-13
	
	if ($("#adjustmentAmt").val() != 0) {
		calcAdjustment();
	}

	var no = null;
	no = $.getUrlVar('invc');

	if (no != null) {
		var successMsg = "Last Invoice No : " + no + " Saved Successfully...";
		$(".smsg").html(successMsg);
	}
	checkgrosswt();

	// Savings Scheme
	var checkedCardNos = new Array();
	checkedCardNos = $("#cardnoVal").val().split(",");
	$.each(checkedCardNos, function(index, item) {
		$("#SSCardNo option").filter(function() {
			return this.text == item;
		}).attr('selected', true);
	});

	$("#SSCardNo").change(function() {
		if ($("#billamount").val() == 0) {
			alert("! Bill Amount can't be Zero");
			$("#SSCardNo option").attr('selected', false);
			return false;
		} else {
			ajaxGetCardsAmount();
		}
	});
	$("#jewelDiscount").keyup(function() {
		if ($("#billamount").val() == 0) {
			alert("Bill Amount can't be Zero !");
			$("#jewelDiscount").val("0.00");
			return false;
		} else {
			balance_to_pay();
		}
	});
	EstimateMode();
	
	
	/*** new script added to allow only _ and - for customer name field on 25-2-13 after bug is posted ****/
	$('#popcustomerName').keyup(function() {
		 $(this).val($(this).val().replace(/[^A-Za-z0-9\s_-]/g,''));
	});
	
	$("#billDiscAmt").keyup(calc_billamount);
});

/**
 * For getting the url value to display a message if the record saved
 * successfully *
 */
$.extend({
	getUrlVars : function() {
		var vars = [], hash;
		var hashes = window.location.href.slice(
				window.location.href.indexOf('?') + 1).split('&');
		for ( var i = 0; i < hashes.length; i++) {
			hash = hashes[i].split('=');
			vars.push(hash[0]);
			vars[hash[0]] = hash[1];
		}
		return vars;
	},
	getUrlVar : function(name) {
		return $.getUrlVars()[name];
	}
});

/** ajax Function For Getting Card Number Details * */
function ajaxGetCardsAmount() {

	var str = "";
	$("#SSCardNo option:selected").each(function() {
		$("#cardnoVal").val("");
		str += $(this).text() + ",";
	});
	$("#cardnoVal").val(str);
	var checkedCardNos = new Array();
	checkedCardNos = $("#cardnoVal").val();

	$.ajax({
		type : 'GET',
		url : 'SSCardAmtGrms.htm',
		data : {
			SelectedValues : checkedCardNos.toString()
		},
		success : function(data) {
			var arr = new Array();
			data = data.replace('[', '');
			data = data.replace(']', '');
			arr = data.split(',');

			$.each(arr, function(index, item) {
				switch (index) {
				case 0: {
					$("#SSCardGrams").val(parseFloat(item).toFixed(3));
					break;
				}
				case 1: {
					$("#SSCardAmount").val(parseFloat(item).toFixed(2));
					break;
				}
				}
			});
			balance_to_pay();
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			if (xmlHttpRequest.readyState = 0 || xmlHttpRequest.status == 0)
				return;
		},
	});

}

/** * For Retrieving Card NO List Based on Customer Name * */
function ajaxSSCardNoRetrieve() {

	var customerName = $("#custName").val();
	if (customerName != "walk_in") {
		$.ajax({
			type : 'GET',
			url : 'SSCardNO.htm',
			data : {
				customerName : customerName
			},
			success : function(data) {
				$("select[id$=SSCardNo] > option").remove();
				var arr = new Array();
				data = data.replace('[', '');
				data = data.replace(']', '');
				data = data.replace('[', '');
				data = data.replace(']', '');
				arr = data.split(',');
				$.each(arr,	function(index, item) {
				var result = item.replace(/^\s+|\s+$/g, '').replace(/\s+/g, ' ');
				if (item != ']' && item != ',' && item != ' ' && item != ''	&& item != '[') {
					$("#SSCardNo").get(0).options[index] = new Option(result);
				}
			});
			$('select option:empty').remove();
			ajaxGetCardsAmount();
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			if (xmlHttpRequest.readyState = 0 || xmlHttpRequest.status == 0)
				return;
		},
	});
	}else{
		$("#cardnoVal").val("");
		$("#SSCardNo").val("");
		$('#SSCardNo option').remove();
		$("#SSCardAmount").val("0.00");
		$("#SSCardGrams").val("0.000");
	}
}

/** For Roundoff Manual Calculation **/
function resetRoundOff() {

	var subAmount = $("#subtotal").val();
	subAmount = Init_Value(subAmount);
	
	var taxAmount = $("#tax").val();
	taxAmount = Init_Value(taxAmount);

	var tempRound = $("#rondOffReference").val();
	$("#roundOff").val(tempRound);

	var Amount = parseFloat(subAmount) + parseFloat(taxAmount);
	var finalAmt = parseFloat(Amount) + parseFloat(tempRound);
	$("#billamount").val(parseFloat(finalAmt).toFixed(2));
	balance_to_pay();
}

function initZero() {
	$('input.numField').each(function() {
		var TempVal = $(this).val();
		if (TempVal.length === 0 || TempVal === '' || TempVal === null) {
			$(this).val("0");
		}
	});
}

function update_Mode() {
	$("#insert").hide();
	if($("#billType").val()=="Cancelled"){
		$(".smsg").html("Cancelled Bill");
		$("#cancelSales").hide();
		$("#update").hide();
	}else{
		$(".smsg").html("");
		$("#update").show();
	}
	$("#s").hide();
	$("#s1").hide();
	$("#s2").hide();
	$("#s3").hide();
	$("#s4").hide();
	$("#icode").attr("readonly", "readonly");
	$("#icode1").attr("readonly", "readonly");
	$("#icode2").attr("readonly", "readonly");
	$("#icode3").attr("readonly", "readonly");
	$("#icode4").attr("readonly", "readonly");
	resetRoundOff();
}

function checkrow1() {
	row1();
}
function checkrow2() {
	if ($("#btype").val() == null || $("#btype").val() == 'undefined'
			|| $("#btype").val() == '') {
		alert("Please Enter First Row Details");
		$("#icode1").val('');
		$("#icode").focus();
		return false;
	}
	row2();
	return true;
}

function checkrow3() {
	if ($("#btype").val() == null || $("#btype").val() == 'undefined'
			|| $("#btype").val() == '') {
		alert("Please Enter First Row Details");
		$("#icode1").val('');
		$("#icode").focus();
		return false;
	}
	if ($("#btype1").val() == null || $("#btype1").val() == 'undefined'
			|| $("#btype1").val() == '') {
		alert("Please Enter Second Row Details");
		$("#icode1").focus();
		return false;
	}

	row3();
	return true;

}

function checkrow4() {
	if ($("#btype").val() == null || $("#btype").val() == 'undefined'
			|| $("#btype").val() == '') {
		alert("Please Enter First Row Details");
		$("#icode1").val('');
		$("#icode").focus();
		return false;
	}
	if ($("#btype1").val() == null || $("#btype1").val() == 'undefined'
			|| $("#btype1").val() == '') {
		alert("Please Enter Second Row Details");
		$("#icode1").focus();
		return false;
	}
	if ($("#btype2").val() == null || $("#btype2").val() == 'undefined'
			|| $("#btype2").val() == '') {
		alert("Please Enter Third Row Details");
		$("#icode2").focus();
		return false;
	}
	row4();
	return true;
}

function checkrow5() {
	if ($("#btype").val() == null || $("#btype").val() == 'undefined'
			|| $("#btype").val() == '') {
		alert("Please Enter First Row Details");
		$("#icode1").val('');
		$("#icode").focus();
		return false;
	}
	if ($("#btype1").val() == null || $("#btype1").val() == 'undefined'
			|| $("#btype1").val() == '') {
		alert("Please Enter Second Row Details");
		$("#icode1").focus();
		return false;
	}
	if ($("#btype2").val() == null || $("#btype2").val() == 'undefined'
			|| $("#btype2").val() == '') {
		alert("Please Enter Third Row Details");
		$("#icode2").focus();
		return false;
	}
	if ($("#btype3").val() == null || $("#btype3").val() == 'undefined'
			|| $("#btype3").val() == '') {
		alert("Please Enter Fourth Row Details");
		$("#icode3").focus();
		return false;
	}
	row5();
	return true;
}

/** Calculation for Adjustment amount field */
function calcAdjustment() {

	var ZERO = 0;
	var vatR1 = 0, vatR2 = 0, vatR3 = 0, vatR4 = 0, vatR5 = 0;
	var b1, b2, b3, b4, b5;

	/**
	 * The adjustment amount calculation should only work if all rows/items vat %
	 * are common/same.
	 */

	if ($("#btype").val().length != 0) {
		var temp1 = $("#vat").val();
		var t1 = parseFloat(temp1);
		vatR1 = t1.toFixed(2);
		b1 = true;
	} else {
		b1 = false;
	}

	if ($("#btype1").val().length != 0) {
		var temp2 = $("#vat1").val();
		var t2 = parseFloat(temp2);
		vatR2 = t2.toFixed(2);
		b2 = true;
	} else {
		b2 = false;
	}

	if ($("#btype2").val().length != 0) {
		var temp3 = $("#vat2").val();
		var t3 = parseFloat(temp3);
		vatR3 = t3.toFixed(2);
		b3 = true;
	} else {
		b3 = false;
	}

	if ($("#btype3").val().length != 0) {
		var temp4 = $("#vat3").val();
		var t4 = parseFloat(temp4);
		vatR4 = t4.toFixed(2);
		b4 = true;
	} else {
		b4 = false;
	}

	if ($("#btype4").val().length != 0) {
		var temp5 = $("#vat4").val();
		var t5 = parseFloat(temp5);
		vatR5 = t5.toFixed(2);
		b5 = true;
	} else {
		b5 = false;
	}

	if (b1 && b2) {
		if (vatR1 != vatR2) {
			alert("Vat % not Same adjustment amount Cannot be Edited.");
			$("#adjustmentAmt").val(ZERO.toFixed(2));
			return false;
		}
	}

	if (b1 && b3) {
		if (vatR1 != vatR3) {
			alert("Vat % not Same adjustment amount Cannot be Edited.");
			$("#adjustmentAmt").val(ZERO.toFixed(2));
			return false;
		}
	}

	if (b1 && b4) {
		if (vatR1 != vatR4) {
			alert("Vat % not Same adjustment amount Cannot be Edited.");
			$("#adjustmentAmt").val(ZERO.toFixed(2));
			return false;
		}
	}

	if (b1 && b5) {
		if (vatR1 != vatR2) {
			alert("Vat % not Same adjustment amount Cannot be Edited.");
			$("#adjustmentAmt").val(ZERO.toFixed(2));
			return false;
		}
	}

	if (b2 && b3) {
		if (vatR2 != vatR3) {
			alert("Vat % not Same adjustment amount Cannot be Edited.");
			$("#adjustmentAmt").val(ZERO.toFixed(2));
			return false;
		}

	}
	if (b2 && b4) {
		if (vatR2 != vatR4) {
			alert("Vat % not Same adjustment amount Cannot be Edited.");
			$("#adjustmentAmt").val(ZERO.toFixed(2));
			return false;
		}

	}
	if (b2 && b5) {
		if (vatR2 != vatR5) {
			alert("Vat % not Same adjustment amount Cannot be Edited.");
			$("#adjustmentAmt").val(ZERO.toFixed(2));
			return false;
		}

	}
	if (b3 && b4) {
		if (vatR3 != vatR4) {
			alert("Vat % not Same adjustment amount Cannot be Edited.");
			$("#adjustmentAmt").val(ZERO.toFixed(2));
			return false;
		}
	}
	if (b3 && b5) {
		if (vatR3 != vatR5) {
			alert("Vat % not Same adjustment amount Cannot be Edited.");
			$("#adjustmentAmt").val(ZERO.toFixed(2));
			return false;
		}
	}
	if (b4 && b5) {
		if (vatR4 != vatR5) {
			alert("Vat % not Same adjustment amount Cannot be Edited.");
			$("#adjustmentAmt").val(ZERO.toFixed(2));
			return false;
		}
	}

	var adj = $("#adjustmentAmt").val();
	adj = Init_Value(adj);

	var adjAmtvalue = parseFloat(adj);
	$("#billamount").val(adjAmtvalue.toFixed(2));

	var bill = $("#billamount").val();
	bill = Init_Value(bill);

	var vatP = $("#vat").val();
	vatP = Init_Value(vatP);

	var temp1 = 0.01 * parseFloat(vatP);
	var temp2 = 1 + parseFloat(temp1);
	var subAmt = parseFloat(bill) / parseFloat(temp2);
	var vatAmt = parseFloat(subAmt) * 0.01 * parseFloat(vatP);
	// var billAmt = parseFloat(subAmt) + parseFloat(vatAmt);
	$("#subtotal").val(subAmt.toFixed(2));
	$("#tax").val(vatAmt.toFixed(2));
	var Amount = parseFloat(subAmt) + parseFloat(vatAmt);
	var beforeRound = Amount.toFixed(2);
	var afterRound = Amount.toFixed(0);
	var roundedValue = afterRound - beforeRound;
	$("#roundOff").val(roundedValue.toFixed(2));
	$("#billamount").val(afterRound);
	// Balance Amount should be re-calculated.
	balance_to_pay();

	/**
	 * If adjustment amount is zero the bill amount will be calculated as
	 * Regular
	 */
	if ($("#adjustmentAmt").val() == 0) {
		subfunctions();
	}
}

/** Confirm box for Print Preview */
/** Cash Cheque Voucher Card validation */
function report_confirm() {

	var cashamt = $("#cash_Amount").val();
	var chqamt = $("#cheque_Amount").val();
	var cardamt = $("#calCard_Amount").val();
	var voucherd = $("#voucher_Amount").val();
	var val = $("#billamount").val();

	if ($("#icode").val() == "IT100011" || $("#icode").val() == "it100011"
			|| $("#icode").val() == "IT100012"
			|| $("#icode").val() == "IT100012") {
		if ($("#grwt").val() == null || $("#grwt").val() == ''
				|| $("#grwt").val() == 0) {
			alert("Gross Weight Required for Loose Metals in Row 1");
			return false;
		}
		if ($("#cname").val() == null || $("#cname").val() == '') {
			alert("Category Name Required for Loose Metals in Row 1");
			return false;
		}
	}
	/***
	 * This valdation added for Category Loose Stock in sales .
	 * Mandatory Fields Qty and GrossWeight for Row 1
	 * *****/
    if($("#metalUsed").val()=="Gold Loose Stock" || $("#metalUsed").val()=="Silver Loose Stock"){
    	if ($("#grwt").val() == null || $("#grwt").val() == ''
			|| $("#grwt").val() == 0) {
		alert("Gross Weight Required for Category Loose Stock in Row 1");
		return false;
	}
    	if ($("#soldQty").val() == null || $("#soldQty").val() == ''
			|| $("#soldQty").val() == 0) {
		alert("Gross Weight Required for Category Loose Qty in Row 1");
		return false;
	}
    	
    }
	if ($("#icode1").val() == "IT100011" || $("#icode1").val() == "it100011"
			|| $("#icode1").val() == "IT100012"
			|| $("#icode1").val() == "IT100012") {
		if ($("#grwt1").val() == null || $("#grwt1").val() == ''
				|| $("#grwt1").val() == 0) {
			alert("Gross Weight Required for Loose Metals in Row 2");
			return false;
		}
		if ($("#cname1").val() == null || $("#cname1").val() == '') {
			alert("Category Name Required for Loose Metals in Row 2");
			return false;
		}
	}
	/***
	 * This valdation added for Category Loose Stock in sales .
	 * Mandatory Fields Qty and GrossWeight for Row 2
	 * *****/
    if($("#metalUsed1").val()=="Gold Loose Stock" || $("#metalUsed1").val()=="Silver Loose Stock"){
    	if ($("#grwt1").val() == null || $("#grwt1").val() == ''
			|| $("#grwt1").val() == 0) {
		alert("Gross Weight Required for Category Loose Stock in Row 2");
		return false;
	}
    	if ($("#soldQty1").val() == null || $("#soldQty1").val() == ''
			|| $("#soldQty1").val() == 0) {
		alert("Gross Weight Required for Category Loose Qty in Row 2");
		return false;
	}
    	
    }
	if ($("#icode2").val() == "IT100011" || $("#icode2").val() == "it100011"
			|| $("#icode2").val() == "IT100012"
			|| $("#icode2").val() == "IT100012") {
		if ($("#grwt2").val() == null || $("#grwt2").val() == ''
				|| $("#grwt2").val() == 0) {
			alert("Gross Weight Required for Loose Metals in Row 3");
			return false;
		}
		if ($("#cname2").val() == null || $("#cname2").val() == '') {
			alert("Category Name Required for Loose Metals in Row 3");
			return false;
		}
	}

	/***
	 * This valdation added for Category Loose Stock in sales .
	 * Mandatory Fields Qty and GrossWeight for Row 3
	 * *****/
    if($("#metalUsed2").val()=="Gold Loose Stock" || $("#metalUsed2").val()=="Silver Loose Stock"){
    	if ($("#grwt2").val() == null || $("#grwt2").val() == ''
			|| $("#grwt2").val() == 0) {
		alert("Gross Weight Required for Category Loose Stock in Row 3");
		return false;
	}
    	if ($("#soldQty2").val() == null || $("#soldQty2").val() == ''
			|| $("#soldQty2").val() == 0) {
		alert("Gross Weight Required for Category Loose Qty in Row 2");
		return false;
	}
    }	
	if ($("#icode3").val() == "IT100011" || $("#icode3").val() == "it100011"
			|| $("#icode3").val() == "IT100012"
			|| $("#icode3").val() == "IT100012") {
		if ($("#grwt3").val() == null || $("#grwt3").val() == ''
				|| $("#grwt3").val() == 0) {
			alert("Gross Weight Required for Loose Metals in Row 4");
			return false;
		}
		if ($("#cname3").val() == null || $("#cname3").val() == '') {
			alert("Category Name Required for Loose Metals in Row 4");
			return false;
		}
	}
	/***
	 * This valdation added for Category Loose Stock in sales .
	 * Mandatory Fields Qty and GrossWeight for Row 4
	 * *****/
    if($("#metalUsed3").val()=="Gold Loose Stock" || $("#metalUsed3").val()=="Silver Loose Stock"){
    	if ($("#grwt3").val() == null || $("#grwt3").val() == ''
			|| $("#grwt3").val() == 0) {
		alert("Gross Weight Required for Category Loose Stock in Row 4");
		return false;
	}
    	if ($("#soldQty3").val() == null || $("#soldQty3").val() == ''
			|| $("#soldQty3").val() == 0) {
		alert("Gross Weight Required for Category Loose Qty in Row 4");
		return false;
	}
    }	
	if ($("#icode4").val() == "IT100011" || $("#icode4").val() == "it100011"
			|| $("#icode4").val() == "IT100012"
			|| $("#icode4").val() == "IT100012") {
		if ($("#grwt4").val() == null || $("#grwt4").val() == ''
				|| $("#grwt3").val() == 0) {
			alert("Gross Weight Required for Loose Metals in Row 5");
			return false;
		}
		if ($("#cname4").val() == null || $("#cname4").val() == '') {
			alert("Category Name Required for Loose Metals in Row 5");
			return false;
		}
	}
	/***
	 * This valdation added for Category Loose Stock in sales .
	 * Mandatory Fields Qty and GrossWeight for Row 4
	 * *****/
    if($("#metalUsed4").val()=="Gold Loose Stock" || $("#metalUsed4").val()=="Silver Loose Stock"){
    	if ($("#grwt4").val() == null || $("#grwt4").val() == ''
			|| $("#grwt4").val() == 0) {
		alert("Gross Weight Required for Category Loose Stock in Row 5");
		return false;
	}
    	if ($("#soldQty4").val() == null || $("#soldQty4").val() == ''
			|| $("#soldQty4").val() == 0) {
		alert("Gross Weight Required for Category Loose Qty in Row 5");
		return false;
	}
    }	
    
	if ($("#icode").val() == '' || $("#icode").val() == null) {
		alert("ItemCode on Row 1 cannot be Empty !");
		return false;
	}

	if (val == 0) {
		alert("Bill Amount cannot be Zero");
		return false;
	}

	if ($("#cash").is(":checked")) {
		if (cashamt == 0) {
			alert("Cash Amount Cannot Be Zero");
			return false;
		}
	}

	if ($("#cheque").is(":checked")) {
		if (chqamt == 0) {
			alert("Cheque Amount Cannot Be Zero");
			return false;
		}
		if ($("#cheque_Bank").val() == 0) {
			alert("Please Select Cheque Bank Name. ");
			return false;
		}
	}

	if ($("#card").is(":checked")) {
		if (cardamt == 0) {
			alert("Card Amount Cannot Be Zero");
			return false;
		}
		if ($("#card_Bank").val() == 0) {
			alert(" Please Select Card Bank Name. ");
			return false;
		}
	}
	if ($("#voucher").is(":checked")) {
		if (voucherd == 0) {
			alert("Voucher Amount Cannot Be Zero");
			return false;
		}
		if ($("#vouchers").val() == 0) {
			alert("Please Select Voucher Name.");
			return false;
		}
	}

	if ($("#billamount").val() != 0) {

		if ($("#fpan_Number").val() == ''
				&& parseFloat($("#billamount").val()) >= 500000) {
			alert(" Pan Number Required, Bill Amount Is Equal To Five Lakhs Or Above ! ");
			return false;
		}
		if ($("#custName").val() == "walk_in") {

			if ($("#w_Name").val() == '' || $("#w_City").val() == '') {
				//alert(" Name and City values are required for Walk-in Customer ! ");
				//return false;
			}
			if ($("#balAmt").val() != 0) {
				alert(" Credit Sales not allowed for Walk-in Customer !");
				return false;
			}
		}
	}
	// For Adjustment Field Validation
	if ($("#adjustmentAmt").val() > 0) {
		if ($("#amountAfterLess").val() <= 0
				&& $("#amountAfterLess1").val() <= 0
				&& $("#amountAfterLess2").val() <= 0
				&& $("#amountAfterLess3").val() <= 0
				&& $("#amountAfterLess4").val()) {
			alert("Invalid Bill ! Check Amount");
			return false;
		}
	}

	// For Second Row for bug number 1983
	if ($("#icode2").val().length != 0) {
		if ($("#icode1").val() == "") {
			alert("Item Code Reqired For 2nd Row");
			return false;
		}
	}

	// For third Row for bug number 1984
	if ($("#icode3").val().length != 0) {
		if ($("#icode2").val() == "") {
			alert("Item Code Reqired For 3nd Row");
			return false;
		}
	}

	// For fourth Row for bug number 1985
	if ($("#icode4").val().length != 0) {
		if ($("#icode3").val() == "") {
			alert("Item Code Reqired For 4nd Row");
			return false;
		}
	}

	// For Fith Row for bug number 1986
	if ($("#icode4").val().length == 0 && $("#btype4").val().length != 0) {
		alert("Item Code Required For 5th Row");
		return false;
	}

	// for bug no 1983 date 16-8-12
	if ($("#icode1").val().length == 0) {
		if ($("#btype1").val().length != 0) {
			alert("Item code on 2nd row can not be empty while their field values are exist!");
			return false;
		}
	}

	// for bug no 1984 16-8-12
	if ($("#icode2").val().length == 0) {
		if ($("#btype2").val().length != 0) {
			alert("Item code on 3rd row can not be empty while their field values are exist!");
			return false;
		}
	}

	// for bug no 1985 16-8-12
	if ($("#icode3").val().length == 0) {
		if ($("#btype3").val().length != 0) {
			alert("Item code on 4th row can not be empty while their field values are exist!");
			return false;
		}
	}

	/** For Checking Irrelevant Item Details On Insert Mode * */
	var URIlocs = $(location).attr('href');
	var uripaths = URIlocs.split('/')[4];
	
	if (uripaths.indexOf("newSales.htm") >= 0 || uripaths.indexOf("salesItem.htm") >= 0 || $("#errorName").val() == "insertError") {
		// R1 Irrelevant data
		if ($("#icode").val().length != 0 && $("#btype").val().length != 0) {
			if ($("#icode").val().toUpperCase() != $("#R1icode").val()
					.toUpperCase()) {
				$("#form_error").slideDown("fast");
				$("#form_error span").text(" * Irrelevant item Details on Row 1");
				return false;
			}
		}

		// R2 Irrelevant data
		if ($("#icode1").val().length != 0 && $("#btype1").val().length != 0) {
			if ($("#icode1").val().toUpperCase() != $("#R2icode").val()
					.toUpperCase()) {
				$("#form_error").slideDown("fast");
				$("#form_error span").text(" * Irrelevant item Details on Row 2");
				return false;
			}
		}

		// R3 Irrelevant data
		if ($("#icode2").val().length != 0 && $("#btype2").val().length != 0) {
			if ($("#icode2").val().toUpperCase() != $("#R3icode").val()
					.toUpperCase()) {
				$("#form_error").slideDown("fast");
				$("#form_error span").text(" * Irrelevant item Details on Row 3");
				return false;
			}
		}

		// R4 Irrelevant data
		if ($("#icode3").val().length != 0 && $("#btype3").val().length != 0) {
			if ($("#icode3").val().toUpperCase() != $("#R4icode").val()
					.toUpperCase()) {
				$("#form_error").slideDown("fast");
				$("#form_error span").text(" * Irrelevant item Details on Row 4");
				return false;
			}
		}

		// R5 Irrelevant data
		if ($("#icode4").val().length != 0 && $("#btype4").val().length != 0) {
			if ($("#icode4").val().toUpperCase() != $("#R5icode").val()
					.toUpperCase()) {
				$("#form_error").slideDown("fast");
				$("#form_error span").text(" * Irrelevant item Details on Row 5");
				return false;
			}
		}
	}
	
	/** Row Amount Zero Validation */
	if ($("#icode").val().length != 0 && $("#btype").val().length != 0 && $("#amountAfterLess").val()== 0) {
		$("#form_error").slideDown("fast");
		$("#form_error span").text(" ! Amount value can't be Zero on Row 1");
		return false;
	}
	if ($("#icode1").val().length != 0 && $("#btype1").val().length != 0 && $("#amountAfterLess1").val()== 0) {
		$("#form_error").slideDown("fast");
		$("#form_error span").text(" ! Amount value can't be Zero on Row 2");
		return false;
	}
	if ($("#icode2").val().length != 0 && $("#btype2").val().length != 0 && $("#amountAfterLess2").val()== 0) {
		$("#form_error").slideDown("fast");
		$("#form_error span").text(" ! Amount value can't be Zero on Row 3");
		return false;
	}
	if ($("#icode3").val().length != 0 && $("#btype3").val().length != 0 && $("#amountAfterLess3").val()== 0) {
		$("#form_error").slideDown("fast");
		$("#form_error span").text(" ! Amount value can't be Zero on Row 4");
		return false;
	}
	if ($("#icode4").val().length != 0 && $("#btype4").val().length != 0 && $("#amountAfterLess4").val()== 0) {
		$("#form_error").slideDown("fast");
		$("#form_error span").text(" ! Amount value can't be Zero on Row 5");
		return false;
	}
	
	if($("#salesType").val() == "Estimate Sales"){
		$("#formType").val("Estimate Sales");
	}else{
		$("#formType").val("Sales");
	}
	
	/*if ($(this).attr("id") == "insert") {
		var test = confirm(" Saving Invoice...  Click OK to view Print preview.");
		$("#printInvoice").val(test);
	}*/
	
	return true;
}

/** function for adjustmentAmt & roundoff enable disable */
function bilno() {
	var firstrow = $("#btype").val();
	if (firstrow == null || $("#btype").val().length == 0 || firstrow == '') {
		$("#adjustmentAmt").attr('disabled', true);
		$("#roundOff").attr('disabled', true);
	} else {
		$("#adjustmentAmt").attr('disabled', false);
		$("#roundOff").attr('disabled', false);
	}
}

/** ItemCode Binding Row1 */
function row1() {
	
	var hiname = $("#hiname").val();
	var hcname = $("#hcname").val();
	var hgrwt = $("#hgrwt").val();
	var hgrate = $("#hgrate").val();
	var hsrate = $("#hsrate").val();
	var hstnCost = $("#hstnCost").val();
	var hvaPercent = $("#hvaPercent").val();
	var hmcAmt = $("#hmcAmt").val(); // gram field for ornaments
	var hmcRupee = $("#hmcRupee").val(); // Rupees field for ornaments 
	var hVat = $("#hVat").val();
	var hlessP = $("#hlessP").val();
	var hstnDesc = $("#hstnDesc").val();
	var icode = $("#icode").val();
	var hbtype = $("#hbtype").val();
	var wastgms, rate;
	var amtT1, amtT2, amt;
	var discAmt = 0.00, discP;
	var hman = $("#hman").val();
	var totpieces = $("#totpieces").val();
	var hitemHMCharges = $("#hitemHMCharges").val();
	/**
	 * New Code added as per SGH CHanges on 27-06-2013
	 * ***/
	var hqty = $("#hqty").val();
	var hmetalUsed = $("#hmetalUsed").val();

	if (icode != "") {

		$("#R1icode").val($("#icode").val());

		if (hbtype == "Gold" && hgrate != null) {
			rate = parseFloat(hgrate);
		} else if (hbtype == "Silver" && hsrate != null) {
			rate = parseFloat(hsrate);
		}

		if (hman != null) {
			$("#script_hman").val(hman);
		}
		if (totpieces != null) {
			$("#script_totpieces").val(totpieces);
		}
		if (hmetalUsed != null) {
			$("#metalUsed").val(hmetalUsed);
		}

		if (hiname != null) {
			$("#iname").val(hiname);
		}

		if (hbtype != null) {
			$("#btype").val(hbtype);
		}

		if (hvaPercent != null) {
			$("#valueAdditionCharges").val(hvaPercent);
		}

		if (hVat != null) {
			$("#vat").val(hVat);
		}

		if (hlessP != null) {
			$("#lessPercent").val(hlessP);
		}

		if (hstnDesc != null) {
			$("#stnDesc").val(hstnDesc);
		}

		if (rate != null) {
			$("#brate").val(rate);
		}

		if (hstnCost != null) {
			$("#stn").val(hstnCost);
		}
		
		if (hitemHMCharges != null) {
			$("#salesHMCharges").val(hitemHMCharges);
		}else{
			hitemHMCharges = "0";
		}

		if ($("#stn").val().length == 0) {
			$("#stn").val(0);
			hstnCost = 0;
		}

		if (hcname != null) {
			if (icode == "IT100011" || icode == "it100011"
					|| icode == "it100012" || icode == "IT100012") {
				$("#cname").val('');
			} else {
				$("#cname").val(hcname);
			}
		}

		if (hgrwt != null) {
			if (icode == "IT100011" || icode == "it100011"
					|| icode == "it100012" || icode == "IT100012") {
				$("#grwt").val(0);
			} else {
				$("#grwt").val(hgrwt);
			}
		}
		
		hgrwt = $("#grwt").val();

		if (hgrwt != null) {
			var va = $("#valueAdditionCharges").val();
			va = Init_Value(va);
			
			var vatemp = parseFloat(va) / 100;
			wastgms = parseFloat(hgrwt) * (vatemp);
		}

		if ($("#wastbygms").val() != null && $("#wastbygms").val() != 'NaN') {
			$("#wastbygms").val(wastgms.toFixed(3));
		}

		if (hgrwt != null && rate != null) {
			amtT1 = parseFloat(hgrwt) * parseFloat(rate);
		}

		if (wastgms != null && rate != null && hgrwt != null) {
			amtT2 = parseFloat(wastgms) * parseFloat(rate);
		}
	//new code added here for mc rupes and gram 11/12	
if(hmcAmt!= 0.00){

		/** if loose metals direct value to mc else calculated value to mc */
		if (icode == "IT100011" || icode == "it100011" || icode == "it100012"
				|| icode == "IT100012") {
			if (hmcAmt != null && hmcAmt != '') {
				$("#mcgrams").val(hmcAmt);
			}
		} else {
			if ((hmcAmt != null && hmcAmt != '') && hgrwt != null) {
				var mcGval = parseFloat(hmcAmt) * parseFloat(hgrwt);
				$("#mcgrams").val(mcGval.toFixed(2));
			}
		}
}else {
	
	hmcRupee = Init_Value(hmcRupee);
	
	$("#mcgrams").val(hmcRupee);
}
//ends here

/**
 * New Code added as per SGH CHanges on 27-06-2013
 * ***/
if (hgrwt != null) {
	if (hmetalUsed == "Gold Loose Stock" || hmetalUsed == "Silver Loose Stock") {
		$("#soldQty").val(0);
		$("#grwt").val(0);
		$("#soldQty").removeAttr("readonly");
	}else if ($("#metalUsed").val() == "Gold Ornaments" || $("#metalUsed").val() == "Silver Ornaments") {
		if(hqty!=null){
			$("#soldQty").val(hqty);
		}
	}
}
		var mc = $("#mcgrams").val();

		if (amtT1 != null && amtT2 != null && hstnCost != null && mc != null) {

			amt = parseFloat(amtT1) + parseFloat(amtT2) + parseFloat(hstnCost) + parseFloat(mc) + parseFloat(hitemHMCharges);

			/** TRADE DISCOUNT */
			discP = $("#lessPercent").val();
			if (discP != '' && discP != null) {
				discAmt = parseFloat(discP) * 0.01 * parseFloat(hgrwt) * parseFloat(rate);
			}
			$("#lessAmt").val(discAmt.toFixed(2));
			amt = parseFloat(amt) - parseFloat(discAmt) ;
		}
		if (amt != null && hgrwt != null) {
			$("#amountAfterLess").val(amt.toFixed(2));
			$("#tempRowAmt").val(amt.toFixed(2));
		}
	} else {
		$("#mcgrams").val(0);
		$("#wastbygms").val(0);
		$("#iname").val("");
		$("#cname").val("");
		$("#btype").val("");
		$("#grwt").val(0);
		$("#brate").val(0);
		$("#stn").val(0);
		$("#valueAdditionCharges").val(0);
		$("#vat").val(0);
		$("#lessPercent").val(0);
		$("#stnDesc").val("");
		$("#amountAfterLess").val(0);
		$("#subtotal").val(0);
		$("#lessAmt").val(0);
	}
	if (icode == "IT100011" || icode == "it100011" || icode == "it100012"
			|| icode == "IT100012") {
		$("#iname").removeAttr("readonly");
		$("#cname").removeAttr("readonly");
		$("#grwt").removeAttr("readonly");
		$("#vat").removeAttr("readonly");
		row1_change(); // to reset amount column
	} else {
		$("#iname").attr("readonly", "readonly");
		$("#cname").attr("readonly", "readonly");
		$("#grwt").attr("readonly", "readonly");
		$("#vat").attr("readonly", "readonly");
	}
	if ($("#metalUsed").val() == "Gold Loose Stock" || $("#metalUsed").val() == "Silver Loose Stock") {
		$("#grwt").removeAttr("readonly");
		$("#soldQty").removeAttr("readonly");
	}
	checkform1();
	subfunctions();
}

/** Dynamic calculation Row 1 */
function row1_change() {

	var amt = 0.0;
	var amtVal1 = 0.0;
	var amtVal2 = 0.0;
	var vatemp = 0.0;
	var wastgms = 0.0;

	var rate = $("#brate").val();
	rate = Init_Value(rate);
	
	var va = $("#valueAdditionCharges").val();
	va = Init_Value(va);
	
	var mcgrams = $("#mcgrams").val();
	mcgrams = Init_Value(mcgrams);
	
	var grwt = $("#grwt").val();
	grwt = Init_Value(grwt);
	
	var stncost = $("#stn").val();
	stncost = Init_Value(stncost);
	
	var lessAmt = $("#lessAmt").val();
	lessAmt = Init_Value(lessAmt);
	
	var salesHMCharges = $("#salesHMCharges").val();
	salesHMCharges = Init_Value(salesHMCharges);
	
	if (rate.length == 0 || wastgms.length == 0 || mcgrams.length == 0) {
	} else if (!isNaN(rate) || !isNaN(wastgms) || !isNaN(mcgrams)) {

		vatemp = parseFloat(va) / 100;
		wastgms = parseFloat(grwt) * (vatemp);
		amtVal1 = parseFloat(grwt) * parseFloat(rate);
		amtVal2 = parseFloat(wastgms) * parseFloat(rate);
		amt = amtVal1 + amtVal2 + parseFloat(stncost) + parseFloat(mcgrams) + parseFloat(salesHMCharges);
	}
	
	if(this.id == "amountAfterLess")
	{
		var amountAfterLess = $("#amountAfterLess").val();
		amountAfterLess = Init_Value(amountAfterLess);
		
		var oldDiscPercent = $("#hlessP").val();
		oldDiscPercent = Init_Value(oldDiscPercent);
		
		var oldDiscAmt = 0;
		if (oldDiscPercent != '' && oldDiscPercent != null) {
			oldDiscAmt = parseFloat(oldDiscPercent) * 0.01 * parseFloat(grwt) * parseFloat(rate);
		}
		
		var tempRowAmt = parseFloat(amt) - parseFloat(oldDiscAmt);
		tempRowAmt = Init_Value(tempRowAmt);
		
		var newAmt = parseFloat(tempRowAmt) - parseFloat(amountAfterLess);
		var newDiscAmt = parseFloat(newAmt) + parseFloat(oldDiscAmt);
					
		if(newDiscAmt <= 0){
			$("#amountAfterLess").val(parseFloat(tempRowAmt).toFixed(2));
			$("#lessAmt").val(parseFloat(oldDiscAmt).toFixed(2));
			$("#lessPercent").val(oldDiscPercent);
		}else{
			$("#lessAmt").val(parseFloat(newDiscAmt).toFixed(2));
		}
		subfunctions();
   }else
   {
		/** Discount Amount Field calculation */
		var discP = $("#lessPercent").val();
		discP = Init_Value(discP);
			
		/** For Disc Percent dynamic Calc**/
		var DiscPer = "0.00";
		
		if(grwt != 0 && rate !=0){
			DiscPer = parseFloat(lessAmt) / ( parseFloat(grwt) * 0.01 * parseFloat(rate) );
		}
		DiscPer = Init_Value(DiscPer);
		
		$("#lessPercent").val(parseFloat(DiscPer).toFixed(2));
		
		if (amt != null) {
			
			$("#wastbygms").val(wastgms.toFixed(3));
			
			if ($("#grwt").val() == 0 || $("#brate").val() == 0) {
				$("#amountAfterLess").val(0);
			}else
			{
				if (amt > lessAmt) {
					amt = amt - lessAmt;
				}else{
					//$("#lessAmt").val(0);
				}
				$("#amountAfterLess").val(amt.toFixed(2));
			}
	
			/** calling Sub calculation functions */
			subfunctions();
		} else {
			$("#amountAfterLess").val(0);
		}
   }

}

/** ItemCode Binding Row 2 */
function row2() {
	
	var hiname1 = $("#hiname1").val();
	var hcname1 = $("#hcname1").val();
	var hbtype1 = $("#hbtype1").val();
	var hgrwt1 = $("#hgrwt1").val();
	var hgrate1 = $("#hgrate").val();
	var hsrate1 = $("#hsrate").val();
	var hstnCost1 = $("#hstnCost1").val();
	var hvaPercent1 = $("#hvaPercent1").val();
	var hmcAmt1 = $("#hmcAmt1").val();
	var hmcRupee1 = $("#hmcRupee1").val(); // Rupees field for ornaments 
	var hVat1 = $("#hVat1").val();
	var hlessP1 = $("#hlessP1").val();
	var icode1 = $("#icode1").val();
	var hstnDesc1 = $("#hstnDesc1").val();
	var wastgms1, mc1, rate1;
	var amtT1, amtT2, amt1;
	var mc1Gval, discAmt1;
	var hman1 = $("#hman1").val();
	var totpieces1 = $("#totpieces1").val();
	var hitemHMCharges1 = $("#hitemHMCharges1").val();
	/**
	 * New Code added as per SGH CHanges on 27-06-2013
	 * ***/
	var hqty1 = $("#hqty1").val();
	var hmetalUsed1 = $("#hmetalUsed1").val();
	
	if (icode1 != "") {

		$("#R2icode").val($("#icode1").val());

		if (hbtype1 == "Gold" && hgrate1 != null) {
			rate1 = parseFloat(hgrate1);
		} else if (hbtype1 == "Silver" && hsrate1 != null) {
			rate1 = parseFloat(hsrate1);
		}

		if (hman1 != null) {
			$("#script_hman1").val(hman1);
		}

		if (totpieces1 != null) {
			$("#script_totpieces1").val(totpieces1);
		}

		if (hiname1 != null) {
			$("#iname1").val(hiname1);
		}

		if (hbtype1 != null) {
			$("#btype1").val(hbtype1);
		}

		if (rate1 != null) {
			$("#brate1").val(rate1);
		}
		if (hmetalUsed1 != null) {
			$("#metalUsed1").val(hmetalUsed1);
		}
		if (hstnCost1 != null) {
			$("#stn1").val(hstnCost1);
		}

		if (hitemHMCharges1 != null) {
			$("#salesHMCharges1").val(hitemHMCharges1);
		}else{
			hitemHMCharges1 = "0";
		}

		if ($("#stn1").val().length == 0) {
			$("#stn1").val(0);
			hstnCost1 = 0;
		}

		if (hvaPercent1 != null) {
			$("#valueAdditionCharges1").val(hvaPercent1);
		}

		if (hVat1 != null) {
			$("#vat1").val(hVat1);
		}

		if (hlessP1 != null) {
			$("#lessPercent1").val(hlessP1);
		}

		if (hstnDesc1 != null) {
			$("#stnDesc1").val(hstnDesc1);
		}

		if (hcname1 != null) {
			if (icode1 == "IT100011" || icode1 == "it100011"
					|| icode1 == "it100012" || icode1 == "IT100012") {
				$("#cname1").val('');
			} else {
				$("#cname1").val(hcname1);
			}
		}

		if (hgrwt1 != null) {
			if (icode1 == "IT100011" || icode1 == "it100011"
					|| icode1 == "it100012" || icode1 == "IT100012") {
				$("#grwt1").val(0);
			} else {
				$("#grwt1").val(hgrwt1);
			}
		}
		
		hgrwt1 = $("#grwt1").val();

		if (hgrwt1 != null) {
			var va1 = $("#valueAdditionCharges1").val();
			var vatemp1 = parseFloat(va1) / 100;
			wastgms1 = parseFloat(hgrwt1) * (vatemp1);
		}

		if ($("#wastbygms1").val() != null && $("#wastbygms1").val() != 'NaN') {
			$("#wastbygms1").val(wastgms1.toFixed(3));
		}

		if (hgrwt1 != null && rate1 != null) {
			amtT1 = parseFloat(hgrwt1) * parseFloat(rate1);

		}
		if (wastgms1 != null && rate1 != null) {
			amtT2 = parseFloat(wastgms1) * parseFloat(rate1);
		}

		//new code added here for mc rupes and gram 11/12/12	
		if(hmcAmt1!= 0.00){
		/** if loose metals direct value to mc else calculated value to mc */
		if (icode1 == "IT100011" || icode1 == "it100011"
				|| icode1 == "it100012" || icode1 == "IT100012") {
			if (hmcAmt1 != null && hmcAmt1 != '') {
				$("#mcgrams1").val(hmcAmt1);
			}
		} else {
			if ((hmcAmt1 != null && hmcAmt1 != '') && hgrwt1 != null) {
				mc1Gval = parseFloat(hmcAmt1) * parseFloat(hgrwt1);
				$("#mcgrams1").val(mc1Gval.toFixed(2));
			}
		}
		}
		else{
			hmcRupee1 = Init_Value(hmcRupee1);
			
			$("#mcgrams1").val(hmcRupee1);
		}
		//ends here
		/**
		 * New Code added as per SGH CHanges on 27-06-2013
		 * ***/
		if (hgrwt1 != null) {
			if (hmetalUsed1 == "Gold Loose Stock" || hmetalUsed1 == "Silver Loose Stock") {
				$("#soldQty1").val(0);
				$("#grwt1").val(0);
				$("#soldQty1").removeAttr("readonly");
			} else if($("#metalUsed1").val() == "Gold Ornaments" || $("#metalUsed1").val() == "Silver Ornaments") {
				if(hqty1!=null){
					$("#soldQty1").val(hqty1);
				}
			}
		}
		mc1 = $("#mcgrams1").val();

		if (amtT1 != null && amtT2 != null && hstnCost1 != null && mc1 != null) {

			amt1 = parseFloat(amtT1) + parseFloat(amtT2) + parseFloat(hstnCost1) + parseFloat(mc1) + parseFloat(hitemHMCharges1);

			/** Trade discount */
			var discP1 = $("#lessPercent1").val();
			if (discP1 != '' && discP1 != null) {
				discAmt1 = parseFloat(discP1) * 0.01 * parseFloat(hgrwt1) * parseFloat(rate1);
			} else {
				discAmt1 = 0.00;
			}
			$("#lessAmt1").val(discAmt1.toFixed(2));
			amt1 = parseFloat(amt1) - parseFloat(discAmt1);
		}
		if (amt1 != null && hgrwt1 != null) {
			$("#amountAfterLess1").val(amt1.toFixed(2));
			$("#tempRowAmt1").val(amt1.toFixed(2));
		}
	} else {
		$("#mcgrams1").val(0);
		$("#wastbygms1").val(0);
		$("#iname1").val("");
		$("#cname1").val("");
		$("#btype1").val("");
		$("#grwt1").val(0);
		$("#brate1").val(0);
		$("#stn1").val(0);
		$("#valueAdditionCharges1").val(0);
		$("#vat1").val(0);
		$("#lessPercent1").val(0);
		$("#stnDesc1").val("");
		$("#amountAfterLess1").val(0);
		$("#subtotal1").val(0);
		$("#lessAmt1").val(0);
	}
	if (icode1 == "IT100011" || icode1 == "it100011" || icode1 == "it100012"
			|| icode1 == "IT100012") {
		$("#iname1").removeAttr("readonly");
		$("#cname1").removeAttr("readonly");
		$("#grwt1").removeAttr("readonly");
		$("#vat1").removeAttr("readonly");
		row2_change(); // to reset amount column
	} else {
		$("#iname1").attr("readonly", "readonly");
		$("#cname1").attr("readonly", "readonly");
		$("#grwt1").attr("readonly", "readonly");
		$("#vat1").attr("readonly", "readonly");
	}
	if ($("#metalUsed1").val() == "Gold Loose Stock" || $("#metalUsed1").val() == "Silver Loose Stock") {
		$("#grwt1").removeAttr("readonly");
		$("#soldQty1").removeAttr("readonly");
	}
	/** calling Sub calculation functions */
	checkform2();
	subfunctions();
}

/** Dynamic calculation Row 2 */
function row2_change() {

	var amt1 = 0.0;
	var amtVal1 = 0.0;
	var amtVal2 = 0.0;
	var vatemp1 = 0.0;
	var wastgms1 = 0.0;

	var rate1 = $("#brate1").val();
	rate1 = Init_Value(rate1);
	
	var va1 = $("#valueAdditionCharges1").val();
	va1 = Init_Value(va1);
	
	var mcgrams1 = $("#mcgrams1").val();
	mcgrams1 = Init_Value(mcgrams1);
	
	var grwt1 = $("#grwt1").val();
	grwt1 = Init_Value(grwt1);
	
	var stncost1 = $("#stn1").val();
	stncost1 = Init_Value(stncost1);
	
	var lessAmt1 = $("#lessAmt1").val();
	lessAmt1 = Init_Value(lessAmt1);
	
	var salesHMCharges1 = $("#salesHMCharges1").val();
	salesHMCharges1 = Init_Value(salesHMCharges1);
	

	if (rate1.length == 0 || wastgms1.length == 0 || mcgrams1.length == 0) {
	} else if (!isNaN(rate1) || !isNaN(wastgms1) || !isNaN(mcgrams1)) {

		vatemp1 = parseFloat(va1) / 100;
		wastgms1 = parseFloat(grwt1) * (vatemp1);
		amtVal1 = parseFloat(grwt1) * parseFloat(rate1);
		amtVal2 = parseFloat(wastgms1) * parseFloat(rate1);
		amt1 = amtVal1 + amtVal2 + parseFloat(stncost1) + parseFloat(mcgrams1) + parseFloat(salesHMCharges1);
	}
	
	if(this.id == "amountAfterLess1")
	{
		var amountAfterLess1 = $("#amountAfterLess1").val();
		amountAfterLess1 = Init_Value(amountAfterLess1);
		
		var oldDiscPercent1 = $("#hlessP1").val();
		oldDiscPercent1 = Init_Value(oldDiscPercent1);
		
		var oldDiscAmt1 = 0;
		if (oldDiscPercent1 != '' && oldDiscPercent1 != null) {
			oldDiscAmt1 = parseFloat(oldDiscPercent1) * 0.01 * parseFloat(grwt1) * parseFloat(rate1);
		}
		
		var tempRowAmt1 = parseFloat(amt1) - parseFloat(oldDiscAmt1);
		tempRowAmt1 = Init_Value(tempRowAmt1);
		
		var newAmt1 = parseFloat(tempRowAmt1) - parseFloat(amountAfterLess1);
		var newDiscAmt1 = parseFloat(newAmt1) + parseFloat(oldDiscAmt1);
			
					
		if(newDiscAmt1 <= 0){
			$("#amountAfterLess1").val(parseFloat(tempRowAmt1).toFixed(2));
			$("#lessAmt1").val(parseFloat(oldDiscAmt1).toFixed(2));
			$("#lessPercent1").val(oldDiscPercent1);
		}else{
			$("#lessAmt1").val(parseFloat(newDiscAmt1).toFixed(2));
		}
		subfunctions();
   }else
   {

		/** Discount Amount Field calculation */
		var discP1 = $("#lessPercent1").val();
		discP1 = Init_Value(discP1);
		
		/**	var discAmt1 = parseFloat(discP1) * 0.01 * parseFloat(grwt1)
				* parseFloat(rate1);
		$("#lessAmt1").val(discAmt1.toFixed(2));
		lessAmt1 = discAmt1;*/
		
		/** For Disc Percent dynamic Calc**/
		var DiscPer1 = "0.00";
		
		if(grwt1 != 0 && rate1 !=0){
			DiscPer1 = parseFloat(lessAmt1) / ( parseFloat(grwt1) * 0.01 * parseFloat(rate1) );
		}
		DiscPer1 = Init_Value(DiscPer1);
		
		$("#lessPercent1").val(parseFloat(DiscPer1).toFixed(2));
	
		if (amt1 != null) {
	
			$("#wastbygms1").val(wastgms1.toFixed(3));
			if ($("#grwt1").val() == 0 || $("#brate1").val() == 0) {
				$("#amountAfterLess1").val(0);
			} else {
	
				if (amt1 > lessAmt1) {
					amt1 = amt1 - lessAmt1;
				} else {
					//$("#lessAmt1").val(0);
				}
				$("#amountAfterLess1").val(amt1.toFixed(2));
			}
			subfunctions();
	
		} else {
			$("#amountAfterLess1").val(0);
		}
		subfunctions();
   }
}

/** ItemCode Binding Row 3 */
function row3() {

	var hiname2 = $("#hiname2").val();
	var hcname2 = $("#hcname2").val();
	var hbtype2 = $("#hbtype2").val();
	var hgrwt2 = $("#hgrwt2").val();
	var hgrate2 = $("#hgrate").val();
	var hsrate2 = $("#hsrate").val();
	var hstnCost2 = $("#hstnCost2").val();
	var hvaPercent2 = $("#hvaPercent2").val();
	var hmcAmt2 = $("#hmcAmt2").val();
	var hmcRupee2 = $("#hmcRupee2").val(); // Rupees field for ornaments 
	var hVat2 = $("#hVat2").val();
	var hlessP2 = $("#hlessP2").val();
	var icode2 = $("#icode2").val();
	var hstnDesc2 = $("#hstnDesc2").val();
	var wastgms2, mc2, rate2;
	var amtT1, amtT2, amt2;
	var mc2Gval, discAmt2 = 0.00;
	var hman2 = $("#hman2").val();
	var totpieces2 = $("#totpieces2").val();
	var hitemHMCharges2 = $("#hitemHMCharges2").val();
	/**
	 * New Code added as per SGH CHanges on 27-06-2013
	 * ***/
	var hqty2 = $("#hqty2").val();
	var hmetalUsed2 = $("#hmetalUsed2").val();

	if (icode2 != "") {

		$("#R3icode").val($("#icode2").val());

		if (hbtype2 == "Gold" && hgrate2 != null) {
			rate2 = parseFloat(hgrate2);
		} else if (hbtype2 == "Silver" && hsrate2 != null) {
			rate2 = parseFloat(hsrate2);
		}

		if (hman2 != null) {
			$("#script_hman2").val(hman2);
		}
		if (totpieces2 != null) {
			$("#script_totpieces2").val(totpieces2);
		}

		if (hiname2 != null) {
			$("#iname2").val(hiname2);
		}

		if (hbtype2 != null) {
			$("#btype2").val(hbtype2);
		}

		if (rate2 != null) {
			$("#brate2").val(rate2);
		}
		if (hmetalUsed2 != null) {
			$("#metalUsed2").val(hmetalUsed2);
		}

		if (hstnCost2 != null) {
			$("#stn2").val(hstnCost2);
		}
		
		if (hitemHMCharges2 != null) {
			$("#salesHMCharges2").val(hitemHMCharges2);
		}else{
			hitemHMCharges2 = "0";
		}

		if ($("#stn2").val().length == 0) {
			$("#stn2").val(0);
			hstnCost2 = 0;
		}

		if (hvaPercent2 != null) {
			$("#valueAdditionCharges2").val(hvaPercent2);
		}

		if (hVat2 != null) {
			$("#vat2").val(hVat2);
		}

		if (hlessP2 != null) {
			$("#lessPercent2").val(hlessP2);
		}

		if (hstnDesc2 != null) {
			$("#stnDesc2").val(hstnDesc2);
		}

		if (hcname2 != null) {
			if (icode2 == "IT100011" || icode2 == "it100011"
					|| icode2 == "it100012" || icode2 == "IT100012") {
				$("#cname2").val('');
			} else {
				$("#cname2").val(hcname2);
			}
		}

		if (hgrwt2 != null) {
			if (icode2 == "IT100011" || icode2 == "it100011"
					|| icode2 == "it100012" || icode2 == "IT100012") {
				$("#grwt2").val(0);
			} else {
				$("#grwt2").val(hgrwt2);
			}
		}

		hgrwt2 = $("#grwt2").val();

		if (hgrwt2 != null) {
			var va2 = $("#valueAdditionCharges2").val();
			var vatemp2 = parseFloat(va2) / 100;
			wastgms2 = parseFloat(hgrwt2) * (vatemp2);
		}

		if ($("#wastbygms2").val() != null && $("#wastbygms2").val() != 'NaN') {
			$("#wastbygms2").val(wastgms2.toFixed(3));
		}

		if (hgrwt2 != null && rate2 != null) {
			amtT1 = parseFloat(hgrwt2) * parseFloat(rate2);
		}

		if (wastgms2 != null && rate2 != null) {
			amtT2 = parseFloat(wastgms2) * parseFloat(rate2);
		}
		//new code added here for mc rupes and gram 11/12/12
		if(hmcAmt2!=0.00){
		/** if loose metals direct value to mc else calculated value to mc */
		if (icode2 == "IT100011" || icode2 == "it100011"
				|| icode2 == "it100012" || icode2 == "IT100012") {
			if (hmcAmt2 != null && hmcAmt2 != '') {
				$("#mcgrams2").val(hmcAmt2);
			}
		} else if (hmcAmt2 != null && hmcAmt2 != '' && hgrwt2 != null) {
			mc2Gval = parseFloat(hmcAmt2) * parseFloat(hgrwt2);
			$("#mcgrams2").val(mc2Gval.toFixed(2));
		}
		}
		else{
			hmcRupee2 = Init_Value(hmcRupee2);
			$("#mcgrams2").val(hmcRupee2);
		}
		//ends here
		/**
		 * New Code added as per SGH CHanges on 27-06-2013
		 * ***/
		if (hgrwt2 != null) {
			if (hmetalUsed2 == "Gold Loose Stock" || hmetalUsed2 == "Silver Loose Stock") {
				$("#soldQty2").val(0);
				$("#grwt2").val(0);
				$("#soldQty2").removeAttr("readonly");
			} else {
				if(hqty2!=null){
					$("#soldQty2").val(hqty2);
				}
			}
		}
		
		mc2 = $("#mcgrams2").val();

		if (amtT1 != null && amtT2 != null && hstnCost2 != null && mc2 != null) {
			amt2 = parseFloat(amtT1) + parseFloat(amtT2) + parseFloat(hstnCost2) + parseFloat(mc2) + parseFloat(hitemHMCharges2);
			/** Trade discount */
			var discP2 = $("#lessPercent2").val();
			if (discP2 != null && discP2 != '') {
				discAmt2 = parseFloat(discP2) * 0.01 * parseFloat(hgrwt2) * parseFloat(rate2);
			}
			$("#lessAmt2").val(discAmt2.toFixed(2));
			amt2 = parseFloat(amt2) - parseFloat(discAmt2);
		}

		if (amt2 != null && hgrwt2 != null) {
			$("#amountAfterLess2").val(amt2.toFixed(2));
			$("#tempRowAmt2").val(amt2.toFixed(2));
		}
	} else {
		$("#mcgrams2").val(0);
		$("#wastbygms2").val(0);
		$("#iname2").val("");
		$("#cname2").val("");
		$("#btype2").val("");
		$("#grwt2").val(0);
		$("#brate2").val(0);
		$("#stn2").val(0);
		$("#valueAdditionCharges2").val(0);
		$("#vat2").val(0);
		$("#lessPercent2").val(0);
		$("#stnDesc2").val("");
		$("#amountAfterLess2").val(0);
		$("#subtotal2").val(0);
		$("#lessAmt2").val(0);
	}

	if (icode2 == "IT100011" || icode2 == "it100011" || icode2 == "it100012"
			|| icode2 == "IT100012") {
		$("#iname2").removeAttr("readonly");
		$("#cname2").removeAttr("readonly");
		$("#grwt2").removeAttr("readonly");
		$("#vat2").removeAttr("readonly");
		row3_change(); // to reset amount column
	} else {
		$("#iname2").attr("readonly", "readonly");
		$("#cname2").attr("readonly", "readonly");
		$("#grwt2").attr("readonly", "readonly");
		$("#vat2").attr("readonly", "readonly");
	}
	if ($("#metalUsed2").val() == "Gold Loose Stock" || $("#metalUsed2").val() == "Silver Loose Stock") {
		$("#grwt2").removeAttr("readonly");
		$("#soldQty2").removeAttr("readonly");
	}
	/** calling Sub calculation functions */
	checkform3();
	subfunctions();
}

/** Dynamic calculation Row 3 */
function row3_change() {

	var amt2 = 0.0;
	var amtVal1 = 0.0;
	var amtVal2 = 0.0;
	var vatemp2 = 0.0;
	var wastgms2 = 0.0;

	var rate2 = $("#brate2").val();
	rate2 = Init_Value(rate2);
	
	var va2 = $("#valueAdditionCharges2").val();
	va2 = Init_Value(va2);
	
	var mcgrams2 = $("#mcgrams2").val();
	mcgrams2 = Init_Value(mcgrams2);
	
	var grwt2 = $("#grwt2").val();
	grwt2 = Init_Value(grwt2);
	
	var stncost2 = $("#stn2").val();
	stncost2 = Init_Value(stncost2);
	
	var lessAmt2 = $("#lessAmt2").val();
	lessAmt2 = Init_Value(lessAmt2);
	
	var salesHMCharges2 = $("#salesHMCharges2").val();
	salesHMCharges2 = Init_Value(salesHMCharges2);
	
	if (rate2.length == 0 || wastgms2.length == 0 || mcgrams2.length == 0) {
	} else if (!isNaN(rate2) || !isNaN(wastgms2) || !isNaN(mcgrams2)) {

		vatemp2 = parseFloat(va2) / 100;
		wastgms2 = parseFloat(grwt2) * (vatemp2);
		amtVal1 = parseFloat(grwt2) * parseFloat(rate2);
		amtVal2 = parseFloat(wastgms2) * parseFloat(rate2);
		amt2 = amtVal1 + amtVal2 + parseFloat(stncost2) + parseFloat(mcgrams2) + parseFloat(salesHMCharges2);

	}
	
	if(this.id == "amountAfterLess2")
	{
		var amountAfterLess2 = $("#amountAfterLess2").val();
		amountAfterLess2 = Init_Value(amountAfterLess2);
		
		var oldDiscPercent2 = $("#hlessP2").val();
		oldDiscPercent2 = Init_Value(oldDiscPercent2);
		
		var oldDiscAmt2 = 0;
		if (oldDiscPercent2 != '' && oldDiscPercent2 != null) {
			oldDiscAmt2 = parseFloat(oldDiscPercent2) * 0.01 * parseFloat(grwt2) * parseFloat(rate2);
		}
		
		var tempRowAmt2 =  parseFloat(amt2) - parseFloat(oldDiscAmt2);
		tempRowAmt2 = Init_Value(tempRowAmt2);
		
		var newAmt2 = parseFloat(tempRowAmt2) - parseFloat(amountAfterLess2);
		var newDiscAmt2 = parseFloat(newAmt2) + parseFloat(oldDiscAmt2);
					
		if(newDiscAmt2 <= 0){
			$("#amountAfterLess2").val(parseFloat(tempRowAmt2).toFixed(2));
			$("#lessAmt2").val(parseFloat(oldDiscAmt2).toFixed(2));
			$("#lessPercent2").val(oldDiscPercent2);
		}else{
			$("#lessAmt2").val(parseFloat(newDiscAmt2).toFixed(2));
		}
		subfunctions();
   }else
	{
		/** Discount Amount Field calculation */
		var discP2 = $("#lessPercent2").val();
		discP2 = Init_Value(discP2);
		
		
		/**var discAmt2 = parseFloat(discP2) * 0.01 * parseFloat(grwt2)
				* parseFloat(rate2);
		$("#lessAmt2").val(discAmt2.toFixed(2));
		lessAmt2 = discAmt2; */
		
		/** For Disc Percent dynamic Calc**/
		var DiscPer2 = "0.00";
		
		if(grwt2 != 0 && rate2 !=0){
			DiscPer2 = parseFloat(lessAmt2) / ( parseFloat(grwt2) * 0.01 * parseFloat(rate2) );
		}
		DiscPer2 = Init_Value(DiscPer2);
		
		$("#lessPercent2").val(parseFloat(DiscPer2).toFixed(2));
	
		if (amt2 != null) {
			$("#wastbygms2").val(wastgms2.toFixed(3));
	
			if ($("#grwt2").val() == 0 || $("#brate2").val() == 0) {
				$("#amountAfterLess2").val(0);
			} else {
				/** To avoid negative values */
				if (amt2 > lessAmt2) {
					amt2 = amt2 - lessAmt2;
				} else {
					//$("#lessAmt2").val(0);
				}
				$("#amountAfterLess2").val(amt2.toFixed(2));
			}
			subfunctions();
	
		} else {
			$("#amountAfterLess2").val(0);
		}
		subfunctions();
	 }
}

/** ItemCode Binding Row 4 */
function row4() {

	var hiname3 = $("#hiname3").val();
	var hcname3 = $("#hcname3").val();
	var hbtype3 = $("#hbtype3").val();
	var hgrwt3 = $("#hgrwt3").val();
	var hgrate3 = $("#hgrate").val();
	var hsrate3 = $("#hsrate").val();
	var hstnCost3 = $("#hstnCost3").val();
	var hvaPercent3 = $("#hvaPercent3").val();
	var hmcAmt3 = $("#hmcAmt3").val();
	var hmcRupee3 = $("#hmcRupee3").val(); // Rupees field for ornaments
	var hVat3 = $("#hVat3").val();
	var hlessP3 = $("#hlessP3").val();
	var icode3 = $("#icode3").val();
	var hstnDesc3 = $("#hstnDesc3").val();
	var wastgms3, mc3, rate3;
	var amtT1, amtT2, amt3;
	var mc3Gval, discAmt3 = 0.00;
	var hman3 = $("#hman3").val();
	var totpieces3 = $("#totpieces3").val();
	var hitemHMCharges3 = $("#hitemHMCharges3").val();
	/**
	 * New Code added as per SGH CHanges on 27-06-2013
	 * ***/
	var hqty3 = $("#hqty3").val();
	var hmetalUsed3 = $("#hmetalUsed3").val();

	if (icode3 != "") {

		$("#R4icode").val($("#icode3").val());

		if (hbtype3 == "Gold" && hgrate3 != null) {
			rate3 = parseFloat(hgrate3);
		} else if (hbtype3 == "Silver" && hsrate3 != null) {
			rate3 = parseFloat(hsrate3);
		}

		if (hman3 != null) {
			$("#script_hman3").val(hman3);
		}

		if (totpieces3 != null) {
			$("#script_totpieces3").val(totpieces3);
		}

		if (hbtype3 != null) {
			$("#btype3").val(hbtype3);
		}

		if (hiname3 != null) {
			$("#iname3").val(hiname3);
		}

		if (hstnCost3 != null) {
			$("#stn3").val(hstnCost3);
		}
		
		if (hitemHMCharges3 != null) {
			$("#salesHMCharges3").val(hitemHMCharges3);
		}else{
			hitemHMCharges3 = "0";
		}

		if ($("#stn3").val().length == 0) {
			$("#stn3").val(0);
			hstnCost3 = 0;
		}

		if (rate3 != null) {
			$("#brate3").val(rate3);
		}
		if (hmetalUsed3 != null) {
			$("#metalUsed3").val(hmetalUsed3);
		}
		if (hvaPercent3 != null) {
			$("#valueAdditionCharges3").val(hvaPercent3);
		}

		if (hVat3 != null) {
			$("#vat3").val(hVat3);
		}

		if (hlessP3 != null) {
			$("#lessPercent3").val(hlessP3);
		}

		if (hstnDesc3 != null) {
			$("#stnDesc3").val(hstnDesc3);
		}

		if (hcname3 != null) {
			if (icode3 == "IT100011" || icode3 == "it100011"
					|| icode3 == "it100012" || icode3 == "IT100012") {
				$("#cname3").val('');
			} else {
				$("#cname3").val(hcname3);
			}
		}

		if (hgrwt3 != null) {
			if (icode3 == "IT100011" || icode3 == "it100011"
					|| icode3 == "it100012" || icode3 == "IT100012") {
				$("#grwt3").val(0);
			} else {
				$("#grwt3").val(hgrwt3);
			}
		}

		hgrwt3 = $("#grwt3").val();

		if (hgrwt3 != null) {
			var va3 = $("#valueAdditionCharges3").val();
			var vatemp3 = parseFloat(va3) / 100;
			wastgms3 = parseFloat(hgrwt3) * (vatemp3);
		}

		if ($("#wastbygms3").val() != null && $("#wastbygms3").val() != 'NaN') {
			$("#wastbygms3").val(wastgms3.toFixed(3));
		}

		if (hgrwt3 != null && rate3 != null) {
			amtT1 = parseFloat(hgrwt3) * parseFloat(rate3);
		}

		if (wastgms3 != null && rate3 != null) {
			amtT2 = parseFloat(wastgms3) * parseFloat(rate3);
		}
		
if(hmcAmt3!=0.00){
		/** if loose metals direct value to mc else calculated value to mc */
		if (icode3 == "IT100011" || icode3 == "it100011"
				|| icode3 == "it100012" || icode3 == "IT100012") {
			if (hmcAmt3 != null && hmcAmt3 != '') {
				$("#mcgrams3").val(hmcAmt3);
			}
		} else {
			if (hmcAmt3 != null && hmcAmt3 != '' && hgrwt3 != null) {
				mc3Gval = parseFloat(hmcAmt3) * parseFloat(hgrwt3);
				$("#mcgrams3").val(mc3Gval.toFixed(2));
			}
		}
}else{
	hmcRupee3 = Init_Value(hmcRupee3);
	
	$("#mcgrams3").val(hmcRupee3);
}
/**
 * New Code added as per SGH CHanges on 27-06-2013
 * ***/
if (hgrwt3 != null) {
	if (hmetalUsed3 == "Gold Loose Stock" || hmetalUsed3 == "Silver Loose Stock") {
		$("#soldQty3").val(0);
		$("#grwt3").val(0);
		$("#soldQty3").removeAttr("readonly");
	} else {
		if(hqty3!=null){
			$("#soldQty3").val(hqty3);
		}
	}
}
		mc3 = $("#mcgrams3").val();

		if (amtT1 != null && amtT2 != null && hstnCost3 != null && mc3 != null) {
			amt3 = parseFloat(amtT1) + parseFloat(amtT2) + parseFloat(hstnCost3) + parseFloat(mc3) + parseFloat(hitemHMCharges3);

			/** Trade discount */
			var discP3 = $("#lessPercent3").val();
			if (discP3 != null && discP3 != '') {
				discAmt3 = parseFloat(discP3) * 0.01 * parseFloat(hgrwt3) * parseFloat(rate3);
			}
			$("#lessAmt3").val(discAmt3.toFixed(2));
			amt3 = parseFloat(amt3) - parseFloat(discAmt3);
		}

		if (amt3 != null && hgrwt3 != null) {
			$("#amountAfterLess3").val(amt3.toFixed(2));
			$("#tempRowAmt3").val(amt3.toFixed(2));
		}

	} else {
		$("#mcgrams3").val(0);
		$("#wastbygms3").val(0);
		$("#iname3").val("");
		$("#cname3").val("");
		$("#btype3").val("");
		$("#grwt3").val(0);
		$("#brate3").val(0);
		$("#stn3").val(0);
		$("#valueAdditionCharges3").val(0);
		$("#vat3").val(0);
		$("#lessPercent3").val(0);
		$("#stnDesc3").val("");
		$("#amountAfterLess3").val(0);
		$("#subtotal3").val(0);
		$("#lessAmt3").val(0);
	}

	if (icode3 == "IT100011" || icode3 == "it100011" || icode3 == "it100012"
			|| icode3 == "IT100012") {
		$("#iname3").removeAttr("readonly");
		$("#cname3").removeAttr("readonly");
		$("#grwt3").removeAttr("readonly");
		$("#vat3").removeAttr("readonly");
		row4_change(); // to reset amount column
	} else {
		$("#iname3").attr("readonly", "readonly");
		$("#cname3").attr("readonly", "readonly");
		$("#grwt3").attr("readonly", "readonly");
		$("#vat3").attr("readonly", "readonly");
	}
	
	if ($("#metalUsed3").val() == "Gold Loose Stock" || $("#metalUsed3").val() == "Silver Loose Stock") {
		$("#grwt3").removeAttr("readonly");
		$("#soldQty3").removeAttr("readonly");
	}
	/** calling Sub calculation functions */
	checkform4();
	subfunctions();
}

/** Dynamic calculation Row 4 */
function row4_change() {

	var amt3 = 0.0;
	var amtVal1 = 0.0;
	var amtVal2 = 0.0;
	var vatemp3 = 0.0;
	var wastgms3 = 0.0;

	var rate3 = $("#brate3").val();
	rate3 = Init_Value(rate3);
	
	var va3 = $("#valueAdditionCharges3").val();
	va3 = Init_Value(va3);
	
	var mcgrams3 = $("#mcgrams3").val();
	mcgrams3 = Init_Value(mcgrams3);
	
	var grwt3 = $("#grwt3").val();
	grwt3 = Init_Value(grwt3);
	
	var stncost3 = $("#stn3").val();
	stncost3 = Init_Value(stncost3);
	
	var lessAmt3 = $("#lessAmt3").val();
	lessAmt3 = Init_Value(lessAmt3);
	
	var salesHMCharges3 = $("#salesHMCharges3").val();
	salesHMCharges3 = Init_Value(salesHMCharges3);
	
	if (rate3.length == 0 || wastgms3.length == 0 || mcgrams3.length == 0) {
	} else if (!isNaN(rate3) || !isNaN(wastgms3) || !isNaN(mcgrams3)) {

		vatemp3 = parseFloat(va3) / 100;
		wastgms3 = parseFloat(grwt3) * (vatemp3);
		amtVal1 = parseFloat(grwt3) * parseFloat(rate3);
		amtVal2 = parseFloat(wastgms3) * parseFloat(rate3);
		amt3 = amtVal1 + amtVal2 + parseFloat(stncost3) + parseFloat(mcgrams3)+ parseFloat(salesHMCharges3);
	}
	
	if(this.id == "amountAfterLess3")
	{
		var amountAfterLess3 = $("#amountAfterLess3").val();
		amountAfterLess3 = Init_Value(amountAfterLess3);
		
		var oldDiscPercent3 = $("#hlessP3").val();
		oldDiscPercent3 = Init_Value(oldDiscPercent3);
		
		var oldDiscAmt3 = 0;
		if (oldDiscPercent3 != '' && oldDiscPercent3 != null) {
			oldDiscAmt3 = parseFloat(oldDiscPercent3) * 0.01 * parseFloat(grwt3) * parseFloat(rate3);
		}
		
		var tempRowAmt3 =  parseFloat(amt3) - parseFloat(oldDiscAmt3);
		tempRowAmt3 = Init_Value(tempRowAmt3);
		
		var newAmt3 = parseFloat(tempRowAmt3) - parseFloat(amountAfterLess3);
		var newDiscAmt3 = parseFloat(newAmt3) + parseFloat(oldDiscAmt3);
					
		if(newDiscAmt3 <= 0){
			$("#amountAfterLess3").val(parseFloat(tempRowAmt3).toFixed(2));
			$("#lessAmt3").val(parseFloat(oldDiscAmt3).toFixed(2));
			$("#lessPercent3").val(oldDiscPercent3);
		}else{
			$("#lessAmt3").val(parseFloat(newDiscAmt3).toFixed(2));
		}
		subfunctions();
   }else
   {
		/** Discount Amount Field calculation */
		var discP3 = $("#lessPercent3").val();
		discP3 = Init_Value(discP3);
		
		
		/** var discAmt3 = parseFloat(discP3) * 0.01 * parseFloat(grwt3)
				* parseFloat(rate3);
		$("#lessAmt3").val(discAmt3.toFixed(2));
		lessAmt3 = discAmt3; */
		
		/** For Disc Percent dynamic Calc**/
		var DiscPer3 = "0.00";
		
		if(grwt3 != 0 && rate3 !=0){
			DiscPer3 = parseFloat(lessAmt3) / ( parseFloat(grwt3) * 0.01 * parseFloat(rate3) );
		}
		DiscPer3 = Init_Value(DiscPer3);
		
		$("#lessPercent3").val(parseFloat(DiscPer3).toFixed(2));
	
		if (amt3 != null && !isNaN(amt3)) {
	
			$("#wastbygms3").val(wastgms3.toFixed(3));
	
			if ($("#grwt3").val() == 0 || $("#brate3").val() == 0) {
				$("#amountAfterLess3").val(0);
			} else {
				/** To avoid negative values */
				if (amt3 > lessAmt3) {
					amt3 = amt3 - lessAmt3;
				} else {
					//$("#lessAmt3").val(0);
				}
				$("#amountAfterLess3").val(amt3.toFixed(2));
			}
	
			/** calling Sub calculation functions */
			subfunctions();
		} else {
			$("#amountAfterLess3").val(0);
		}
   }
}

/** ItemCode Binding Row 5 */
function row5() {

	var hiname4 = $("#hiname4").val();
	var hcname4 = $("#hcname4").val();
	var hbtype4 = $("#hbtype4").val();
	var hgrwt4 = $("#hgrwt4").val();
	var hgrate4 = $("#hgrate").val();
	var hsrate4 = $("#hsrate").val();
	var hstnCost4 = $("#hstnCost4").val();
	var hvaPercent4 = $("#hvaPercent4").val();
	var hmcAmt4 = $("#hmcAmt4").val();
	var hmcRupee4 = $("#hmcRupee4").val(); // Rupees field for ornaments
	var hVat4 = $("#hVat4").val();
	var hlessP4 = $("#hlessP4").val();
	var icode4 = $("#icode4").val();
	var hstnDesc4 = $("#hstnDesc4").val();
	var wastgms4, mc4, rate4;
	var amtT1, amtT2, amt4;
	var mc4Gval, discAmt4 = 0.00;
	var hman4 = $("#hman4").val();
	var totpieces4 = $("#totpieces4").val();
	var hitemHMCharges4 = $("#hitemHMCharges4").val();
	/**
	 * New Code added as per SGH CHanges on 27-06-2013
	 * ***/
	var hqty4 = $("#hqty4").val();
	var hmetalUsed4 = $("#hmetalUsed4").val();

	if (icode4 != "") {

		$("#R5icode").val($("#icode4").val());

		if (hbtype4 == "Gold" && hgrate4 != null) {
			rate4 = parseFloat(hgrate4);
		} else if (hbtype4 == "Silver" && hsrate4 != null) {
			rate4 = parseFloat(hsrate4);
		}

		if (hman4 != null) {
			$("#script_hman4").val(hman4);
		}
		if (totpieces4 != null) {
			$("#script_totpieces4").val(totpieces4);
		}

		if (hiname4 != null) {
			$("#iname4").val(hiname4);
		}

		if (hbtype4 != null) {
			$("#btype4").val(hbtype4);
		}

		if (rate4 != null) {
			$("#brate4").val(rate4);
		}
		if (hmetalUsed4 != null) {
			$("#metalUsed4").val(hmetalUsed4);
		}
		if (hstnCost4 != null) {
			$("#stn4").val(hstnCost4);
		}
		
		if (hitemHMCharges4 != null) {
			$("#salesHMCharges4").val(hitemHMCharges4);
		}else{
			hitemHMCharges4 = "0";
		}

		if ($("#stn4").val().length == 0) {
			$("#stn4").val(0);
			hstnCost4 = 0;
		}

		if (hvaPercent4 != null) {
			$("#valueAdditionCharges4").val(hvaPercent4);
		}

		if (hVat4 != null) {
			$("#vat4").val(hVat4);
		}

		if (hlessP4 != null) {
			$("#lessPercent4").val(hlessP4);
		}

		if (hstnDesc4 != null) {
			$("#stnDesc4").val(hstnDesc4);
		}

		if (hcname4 != null) {
			if (icode4 == "IT100011" || icode4 == "it100011"
					|| icode4 == "it100012" || icode4 == "IT100012") {
				$("#cname4").val('');
			} else {
				$("#cname4").val(hcname4);
			}
		}

		if (hgrwt4 != null) {
			if (icode4 == "IT100011" || icode4 == "it100011"
					|| icode4 == "it100012" || icode4 == "IT100012") {
				$("#grwt4").val(0);
			} else {
				$("#grwt4").val(hgrwt4);
			}
		}

		hgrwt4 = $("#grwt4").val();

		if (hgrwt4 != null) {
			var va4 = $("#valueAdditionCharges4").val();
			var vatemp4 = parseFloat(va4) / 100;
			wastgms4 = parseFloat(hgrwt4) * (vatemp4);
		}

		if ($("#wastbygms4").val() != null && $("#wastbygms4").val() != 'NaN') {
			$("#wastbygms4").val(wastgms4.toFixed(3));
		}

		if (hgrwt4 != null && rate4 != null) {
			amtT1 = parseFloat(hgrwt4) * parseFloat(rate4);
		}

		if (wastgms4 != null && rate4 != null) {
			amtT2 = parseFloat(wastgms4) * parseFloat(rate4);
		}

		if(hmcAmt4!=0.00){
		/** if loose metals direct value to mc else calculated value to mc */
		if (icode4 == "IT100011" || icode4 == "it100011"
				|| icode4 == "it100012" || icode4 == "IT100012") {
			if (hmcAmt4 != null && hmcAmt4 != '') {
				$("#mcgrams4").val(hmcAmt4);
			}
		} else if (hmcAmt4 != null && hmcAmt4 != '' && hgrwt4 != null) {
			mc4Gval = parseFloat(hmcAmt4) * parseFloat(hgrwt4);
			$("#mcgrams4").val(mc4Gval.toFixed(2));
		}
		}
		else{
			hmcRupee4 = Init_Value(hmcRupee4);
			$("#mcgrams4").val(hmcRupee4);
		}
		/**
		 * New Code added as per SGH CHanges on 27-06-2013
		 * ***/
		if (hgrwt4 != null) {
			if (hmetalUsed4 == "Gold Loose Stock" || hmetalUsed4 == "Silver Loose Stock") {
				$("#soldQty4").val(0);
				$("#grwt4").val(0);
				$("#soldQty4").removeAttr("readonly");
			} else {
				if(hqty4!=null){
					$("#soldQty4").val(hqty4);
				}
			}
		}
		
		mc4 = $("#mcgrams4").val();

		if (amtT1 != null && amtT2 != null && hstnCost4 != null && mc4 != null) {

			amt4 = parseFloat(amtT1) + parseFloat(amtT2) + parseFloat(hstnCost4) + parseFloat(mc4) + parseFloat(hitemHMCharges4);

			/** Trade discount */
			var discP4 = $("#lessPercent4").val();
			if (discP4 != '' && discP4 != null) {
				discAmt4 = parseFloat(discP4) * 0.01 * parseFloat(hgrwt4) * parseFloat(rate4);
			}
			$("#lessAmt4").val(discAmt4.toFixed(2));
			amt4 = parseFloat(amt4) - parseFloat(discAmt4);
		}

		if (amt4 != null && hgrwt4 != null) {
			$("#amountAfterLess4").val(amt4.toFixed(2));
			$("#tempRowAmt4").val(amt4.toFixed(2));
		}
	} else {
		$("#mcgrams4").val(0);
		$("#wastbygms4").val(0);
		$("#iname4").val("");
		$("#cname4").val("");
		$("#btype4").val("");
		$("#grwt4").val(0);
		$("#brate4").val(0);
		$("#stn4").val(0);
		$("#valueAdditionCharges4").val(0);
		$("#vat4").val(0);
		$("#lessPercent4").val(0);
		$("#stnDesc4").val("");
		$("#amountAfterLess4").val(0);
		$("#subtotal4").val(0);
		$("#lessAmt4").val(0);
	}

	if (icode4 == "IT100011" || icode4 == "it100011" || icode4 == "it100012"
			|| icode4 == "IT100012") {
		$("#iname4").removeAttr("readonly");
		$("#cname4").removeAttr("readonly");
		$("#grwt4").removeAttr("readonly");
		$("#vat4").removeAttr("readonly");
		row5_change(); // to reset amount column
	} else {
		$("#iname4").attr("readonly", "readonly");
		$("#cname4").attr("readonly", "readonly");
		$("#grwt4").attr("readonly", "readonly");
		$("#vat4").attr("readonly", "readonly");
	}
	/** calling Sub calculation functions */
	checkform5();
	subfunctions();
	if ($("#metalUsed4").val() == "Gold Loose Stock" || $("#metalUsed4").val() == "Silver Loose Stock") {
		$("#grwt4").removeAttr("readonly");
		$("#soldQty4").removeAttr("readonly");
	}
}

/** Dynamic calculation Row 5 */
function row5_change() {

	var amt4 = 0.0;
	var amtVal1 = 0.0;
	var amtVal2 = 0.0;
	var vatemp4 = 0.0;
	var wastgms4 = 0.0;

	var rate4 = $("#brate4").val();
	rate4 = Init_Value(rate4);
	
	var va4 = $("#valueAdditionCharges4").val();
	va4 = Init_Value(va4);
	
	var mcgrams4 = $("#mcgrams4").val();
	mcgrams4 = Init_Value(mcgrams4);
	
	var grwt4 = $("#grwt4").val();
	grwt4 = Init_Value(grwt4);
	
	var stncost4 = $("#stn4").val();
	stncost4 = Init_Value(stncost4);
	
	var lessAmt4 = $("#lessAmt4").val();
	lessAmt4 = Init_Value(lessAmt4);
	
	var salesHMCharges4 = $("#salesHMCharges4").val();
	salesHMCharges4 = Init_Value(salesHMCharges4);
	
	if (rate4.length == 0 || wastgms4.length == 0 || mcgrams4.length == 0) {
	} else if (!isNaN(rate4) || !isNaN(wastgms4) || !isNaN(mcgrams4)) {

		vatemp4 = parseFloat(va4) / 100;
		wastgms4 = parseFloat(grwt4) * (vatemp4);
		amtVal1 = parseFloat(grwt4) * parseFloat(rate4);
		amtVal2 = parseFloat(wastgms4) * parseFloat(rate4);
		amt4 = amtVal1 + amtVal2 + parseFloat(stncost4) + parseFloat(mcgrams4) + parseFloat(salesHMCharges4);
	}
	
	if(this.id == "amountAfterLess4")
	{
		var amountAfterLess4 = $("#amountAfterLess4").val();
		amountAfterLess4 = Init_Value(amountAfterLess4);
		
		var oldDiscPercent4 = $("#hlessP4").val();
		oldDiscPercent4 = Init_Value(oldDiscPercent4);
		
		var oldDiscAmt4 = 0;
		if (oldDiscPercent4 != '' && oldDiscPercent4 != null) {
			oldDiscAmt4 = parseFloat(oldDiscPercent4) * 0.01 * parseFloat(grwt4) * parseFloat(rate4);
		}
		
		var tempRowAmt4 =  parseFloat(amt4) - parseFloat(oldDiscAmt4);
		tempRowAmt4 = Init_Value(tempRowAmt4);
		
		var newAmt4 = parseFloat(tempRowAmt4) - parseFloat(amountAfterLess4);
		var newDiscAmt4 = parseFloat(newAmt4) + parseFloat(oldDiscAmt4);
					
		if(newDiscAmt4 <= 0){
			$("#amountAfterLess4").val(parseFloat(tempRowAmt4).toFixed(2));
			$("#lessAmt4").val(parseFloat(oldDiscAmt4).toFixed(2));
			$("#lessPercent4").val(oldDiscPercent4);
		}else{
			$("#lessAmt4").val(parseFloat(newDiscAmt4).toFixed(2));
		}
		subfunctions();
   }else
   {
	
		/** Discount Amount Field calculation */
		var discP4 = $("#lessPercent4").val();
		discP4 = Init_Value(discP4);
		
		/** var discAmt4 = parseFloat(discP4) * 0.01 * parseFloat(grwt4)
				* parseFloat(rate4);
		$("#lessAmt4").val(discAmt4.toFixed(2));
		lessAmt4 = discAmt4; */
		
		/** For Disc Percent dynamic Calc**/
		var DiscPer4 = "0.00";
		
		if(grwt4 != 0 && rate4 !=0){
			DiscPer4 = parseFloat(lessAmt4) / ( parseFloat(grwt4) * 0.01 * parseFloat(rate4) );
		}
		DiscPer4 = Init_Value(DiscPer4);
		
		$("#lessPercent4").val(parseFloat(DiscPer4).toFixed(2));
	
		if (amt4 != null && !isNaN(amt4)) {
			$("#wastbygms4").val(wastgms4.toFixed(3));
			if ($("#grwt4").val() == 0 || $("#brate4").val() == 0) {
				$("#amountAfterLess4").val(0);
			} else {
	
				/** To avoid negative values */
				if (amt4 > lessAmt4) {
					amt4 = amt4 - lessAmt4;
				} else {
					//$("#lessAmt4").val(0);
				}
	
				$("#amountAfterLess4").val(amt4.toFixed(2));
			}
			subfunctions();
		} else {
			$("#amountAfterLess4").val(0);
		}
   }
}

/** To check for payment modes checkboxes values and calculations */
function payment_Mode() {

	var amt = 0;
	
	var cashAmt = $("#cash_Amount").val();
	cashAmt = Init_Value(cashAmt);
	
	var chequeAmt = $("#cheque_Amount").val();
	chequeAmt = Init_Value(chequeAmt);
		
	var cardAmt = $("#calCard_Amount").val();
	cardAmt = Init_Value(cardAmt);
	
	var voucherAmt = $("#voucher_Amount").val();
	voucherAmt = Init_Value(voucherAmt);
	
	if ($("#billamount").val() == 0 || $("#billamount").val().length == 0) {
		alert(" Bill Amount Can't Be Zero. ");
		//$("#cash").removeAttr("checked");
		$("#cheque").removeAttr("checked");
		$("#card").removeAttr("checked");
		$("#voucher").removeAttr("checked");
		$("#amtReceived").val(0);
		$("#exchange_Amount").val(0);
		$("#billNo").reset();
		balance_to_pay();
		
	} else {

		if ($("#cash").is(":checked")) {
			$("#lcash_Amount").show();
			$("#cash_Amount").show();
		} else {
			$("#lcash_Amount").hide();
			$("#cash_Amount").hide();
			$("#cash_Amount").val(0);
			cashAmt = 0;
		}
		if ($("#cheque").is(":checked")) {
			showCheque();
		} else {
			chequeAmt = 0;
			$("#cheque_Amount").val(0);
			hideCheque();
		}
		if ($("#card").is(":checked")) {
			showCard();
		} else {
			cardAmt = 0;
			$("#card_Amount").val(0);
			$("#calCard_Amount").val(0);
			$("#com_percentage").val(0);
			$("#com_Amount").val(0);
			hideCard();
		}
		if ($("#voucher").is(":checked")) {
			showVoucher();
		} else {
			voucherAmt = 0;
			$("#voucher_Amount").val(0);
			hideVoucher();
		}

		amt = parseFloat(cashAmt) + parseFloat(chequeAmt) + parseFloat(cardAmt)
					+ parseFloat(voucherAmt) ;//+ parseFloat(exchangeAmt);
		$("#amtReceived").val(amt.toFixed(2));
		balance_to_pay();
		
	}
}

function check_hide() {

	if ($("#cash").is(":checked")) {
		$("#lcash_Amount").show();
		$("#cash_Amount").show();
	} else {
		$("#lcash_Amount").hide();
		$("#cash_Amount").hide();
	}

	if ($("#cheque").is(":checked")) {
		showCheque();
	} else {
		hideCheque();
	}

	if ($("#card").is(":checked")) {
		showCard();
	} else {
		hideCard();
	}

	if ($("#voucher").is(":checked")) {
		showVoucher();
	} else {
		hideVoucher();
	}
}

function showCard() {
	$("#lcard_Details").show();
	$("#lcard_Bank").show();
	$("#lcard_Amount").show();
	$("#card_Bank").show();
	$("#card_Details").show();
	$("#card_Amount").show();
	$("#calCard_Amount").show();//added
	$("#com_percentage").show();//added
	$("#com_Amount").show();//added
}

function hideCard() {
	$("#lcard_Details").hide();
	$("#lcard_Bank").hide();
	$("#lcard_Amount").hide();
	$("#card_Bank").hide();
	$("#card_Details").hide();
	$("#card_Amount").hide();
	$("#calCard_Amount").hide();//added
	$("#com_percentage").hide();//added
	$("#com_Amount").hide();//added
}

function showVoucher() {
	$("#lvoucher_Amount").show();
	$("#lvouchers").show();
	$("#lvoucher_Details").show();
	$("#voucher_Amount").show();
	$("#vouchers").show();
	$("#voucher_Details").show();
}

function hideVoucher() {
	$("#lvoucher_Amount").hide();
	$("#lvouchers").hide();
	$("#lvoucher_Details").hide();
	$("#voucher_Amount").hide();
	$("#vouchers").hide();
	$("#voucher_Details").hide();
}

function showCheque() {
	$("#lcheque_Amount").show();
	$("#lcheque_Bank").show();
	$("#lcheque_Details").show();
	$("#cheque_Amount").show();
	$("#cheque_Bank").show();
	$("#cheque_Details").show();
}

function hideCheque() {
	$("#lcheque_Amount").hide();
	$("#lcheque_Bank").hide();
	$("#lcheque_Details").hide();
	$("#cheque_Amount").hide();
	$("#cheque_Bank").hide();
	$("#cheque_Details").hide();
	$("#cheque_Amount").val(0);
}

/** Calculation for Commsion Card Service New Requrment for Narayana** */
function calc_commision(){
	var comPerct=$("#com_percentage").val();
	comPerct = Init_Value(comPerct);
	
	var cardAmount=$("#calCard_Amount").val();
	cardAmount = Init_Value(cardAmount);
	
	var temP2amt=parseFloat(cardAmount)*parseFloat(comPerct);
	var actualAmt=temP2amt/100;
	
	$("#com_Amount").val(actualAmt.toFixed(2));
	var commAmount=$("#com_Amount").val();
	
	var addAmt=parseFloat(cardAmount)+parseFloat(commAmount);
	$("#card_Amount").val(addAmt.toFixed(2));
	payment_Mode();
}

/** Calculation for Commsion Card amount (reverse) New Requrment for Narayana** */
function calc_commision_amount(){
	
	var comcardAmount=$("#com_Amount").val();
	comcardAmount = Init_Value(comcardAmount);
	
	var cardAmount=$("#calCard_Amount").val();
	cardAmount = Init_Value(cardAmount);
	
	if($("#card_Amount").val() <=0 ){
	}
	
	if(cardAmount <=0 ){
		alert("Card Amount Cannot be Zero");
		return false;
	}else{
		
		var temP2amt=parseFloat(comcardAmount)*100;
		var actualAmt=temP2amt/parseFloat(cardAmount);
		
		$("#com_percentage").val(actualAmt.toFixed(2));
		
		var commAmount=$("#com_Amount").val();
		commAmount = Init_Value(commAmount);
		
		var addAmt=parseFloat(cardAmount)+parseFloat(commAmount);
		$("#card_Amount").val(addAmt.toFixed(2));
		payment_Mode();
	}
	
	
}

/** Bill amount field calculation */
function calc_billamount() {

	var subAmount = $("#subtotal").val();
	subAmount = Init_Value(subAmount);
	
	var taxAmount = $("#tax").val();
	taxAmount = Init_Value(taxAmount);
	
	var billDiscAmt = $("#billDiscAmt").val();
	billDiscAmt = Init_Value(billDiscAmt);
	
	if(subAmount >0){
	
		var Amount = parseFloat(subAmount) + parseFloat(taxAmount) - parseFloat(billDiscAmt);
		var beforeRound = parseFloat(Amount).toFixed(2);
	
		var afterRound = parseFloat(Amount).toFixed(0);
		var roundedValue = afterRound - beforeRound;
		
		$("#roundOff").val(parseFloat(roundedValue).toFixed(2));
		$("#billamount").val(parseFloat(afterRound).toFixed(2));
		//ajaxSalesAdvance();
		if($("#insert").is(":visible")){
			$("#cash_Amount").val(parseFloat(afterRound).toFixed(2));
			payment_Mode();
		}
		balance_to_pay();
	
	}else{
		$("#billDiscAmt").val("0");
		$("#roundOff").val("0.00");
		$("#billamount").val("0.00");
		$("#cash_Amount").val("0");
		$("#balAmt").val("0.00");
	}
}

function calc_roundOffAmt() {
	
	var subAmount = $("#subtotal").val();
	subAmount = Init_Value(subAmount);
	
	var taxAmount = $("#tax").val();
	taxAmount = Init_Value(taxAmount);
	
	var billDiscAmt = $("#billDiscAmt").val();
	billDiscAmt = Init_Value(billDiscAmt);
	
	var roundAmt = $("#roundOff").val();
	roundAmt = Init_Value(roundAmt);
	
	var Amount = parseFloat(subAmount) + parseFloat(taxAmount) - parseFloat(billDiscAmt) + parseFloat(roundAmt);
	$("#billamount").val(Amount.toFixed(2));
	balance_to_pay();
}

/** Calculation for Balance to pay */
function balance_to_pay() {

	var billAmt = $("#billamount").val();
	billAmt = Init_Value(billAmt);
	
	var amtReceived = $("#amtReceived").val();
	amtReceived = Init_Value(amtReceived);
	
	var amtOrder = $("#order_Amount").val();
	amtOrder = Init_Value(amtOrder);
	
	var SSCardAmount = $("#SSCardAmount").val();
	SSCardAmount = Init_Value(SSCardAmount);
	
	var jewelDiscount = $("#jewelDiscount").val();
	jewelDiscount = Init_Value(jewelDiscount);
	
	var exchangeAmt=$("#exchange_Amount").val();
	var SalesReturnAmt=$("#salesReturnAmount").val();
	var bal = parseFloat(billAmt) - parseFloat(amtReceived)	- parseFloat(amtOrder) - parseFloat(SSCardAmount)-parseFloat(exchangeAmt) - parseFloat(jewelDiscount)-parseFloat(SalesReturnAmt);
	$("#balAmt").val(bal.toFixed(2));
}

/** For getting Customer names List in CutomerName Drop down box */
function customerName_change() {

	var fieldval = $("#custName").val();

	if (fieldval != "walk_in") {
		$("#row_NameCity").hide();
		$("#w_Name").val("");
		$("#w_City").val("");
	} else {
		$("#row_NameCity").show();
	}
}

function CustomerName_Dynamic() {

	ajaxPanNumber();
	ajaxSalesOrderID();
	ajaxPurchaseBillno();// add on 16-8-12 for bug no 1982

	// *** For Retrieving Card NO List Based on Customer Name **/
	ajaxSSCardNoRetrieve();
}

function checkform1() {
	var itemCode = $("#icode").val();
	if (itemCode == 'it100001' || itemCode == 'IT100001'
			|| itemCode == 'it100006' || itemCode == 'IT100006'
			|| itemCode == 'it100002' || itemCode == 'IT100002'
			|| itemCode == 'it100003' || itemCode == 'IT100003'
			|| itemCode == 'it100004' || itemCode == 'IT100004'
			|| itemCode == 'it100005' || itemCode == 'IT100005'
			|| itemCode == 'it100007' || itemCode == 'IT100007'
			|| itemCode == 'it100008' || itemCode == 'IT100008'
			|| itemCode == 'it100009' || itemCode == 'IT100009'
			|| itemCode == 'it100010' || itemCode == 'IT100010') {
		clear_R1();
		subfunctions();
		alert(" * ItemCode on Row 1 is not for Sale ! ");
		$("#icode").val('');
		$("#icode").focus();
		return false;
	} else {
		return true;
	}
}
/** Clearing 5 Rows */
function clear_R1() {
	$("#mcgrams").val(0);
	$("#wastbygms").val(0);
	$("#iname").val("");
	$("#cname").val("");
	$("#btype").val("");
	$("#grwt").val(0);
	$("#brate").val(0);
	$("#stn").val(0);
	$("#valueAdditionCharges").val(0);
	$("#vat").val(0);
	$("#lessPercent").val(0);
	$("#stnDesc").val("");
	$("#amountAfterLess").val(0);
	$("#lessAmt").val(0);
}

function clear_R2() {
	$("#mcgrams1").val(0);
	$("#wastbygms1").val(0);
	$("#iname1").val("");
	$("#cname1").val("");
	$("#btype1").val("");
	$("#grwt1").val(0);
	$("#brate1").val(0);
	$("#stn1").val(0);
	$("#valueAdditionCharges1").val(0);
	$("#vat1").val(0);
	$("#lessPercent1").val(0);
	$("#stnDesc1").val("");
	$("#amountAfterLess1").val(0);
	$("#lessAmt1").val(0);
}

function clear_R3() {
	$("#mcgrams2").val(0);
	$("#wastbygms2").val(0);
	$("#iname2").val("");
	$("#cname2").val("");
	$("#btype2").val("");
	$("#grwt2").val(0);
	$("#brate2").val(0);
	$("#stn2").val(0);
	$("#valueAdditionCharges2").val(0);
	$("#vat2").val(0);
	$("#lessPercent2").val(0);
	$("#stnDesc2").val("");
	$("#amountAfterLess2").val(0);
	$("#lessAmt2").val(0);
}

function clear_R4() {
	$("#mcgrams3").val(0);
	$("#wastbygms3").val(0);
	$("#iname3").val("");
	$("#cname3").val("");
	$("#btype3").val("");
	$("#grwt3").val(0);
	$("#brate3").val(0);
	$("#stn3").val(0);
	$("#valueAdditionCharges3").val(0);
	$("#vat3").val(0);
	$("#lessPercent3").val(0);
	$("#stnDesc3").val("");
	$("#amountAfterLess3").val(0);
	$("#lessAmt3").val(0);
}

function clear_R5() {
	$("#mcgrams4").val(0);
	$("#wastbygms4").val(0);
	$("#iname4").val("");
	$("#cname4").val("");
	$("#btype4").val("");
	$("#grwt4").val(0);
	$("#brate4").val(0);
	$("#stn4").val(0);
	$("#valueAdditionCharges4").val(0);
	$("#vat4").val(0);
	$("#lessPercent4").val(0);
	$("#stnDesc4").val("");
	$("#amountAfterLess4").val(0);
	$("#lessAmt4").val(0);
}

function checkform2() {
	var itemCode1 = $("#icode1").val();

	if (itemCode1 == 'it100001' || itemCode1 == 'IT100001'
			|| itemCode1 == 'it100006' || itemCode1 == 'IT100006'
			|| itemCode1 == 'it100002' || itemCode1 == 'IT100002'
			|| itemCode1 == 'it100003' || itemCode1 == 'IT100003'
			|| itemCode1 == 'it100004' || itemCode1 == 'IT100004'
			|| itemCode1 == 'it100005' || itemCode1 == 'IT100005'
			|| itemCode1 == 'it100007' || itemCode1 == 'IT100007'
			|| itemCode1 == 'it100008' || itemCode1 == 'IT100008'
			|| itemCode1 == 'it100009' || itemCode1 == 'IT100009'
			|| itemCode1 == 'it100010' || itemCode1 == 'IT100010') {
		clear_R2();
		subfunctions();
		alert(" * ItemCode on Row 2 is not for Sale ! ");
		$("#icode1").val('');
		return false;
	} else {
		return true;
	}
}

function checkform3() {
	var itemCode2 = $("#icode2").val();
	if (itemCode2 == 'it100001' || itemCode2 == 'IT100001'
			|| itemCode2 == 'it100006' || itemCode2 == 'IT100006'
			|| itemCode2 == 'it100002' || itemCode2 == 'IT100002'
			|| itemCode2 == 'it100003' || itemCode2 == 'IT100003'
			|| itemCode2 == 'it100004' || itemCode2 == 'IT100004'
			|| itemCode2 == 'it100005' || itemCode2 == 'IT100005'
			|| itemCode2 == 'it100007' || itemCode2 == 'IT100007'
			|| itemCode2 == 'it100008' || itemCode2 == 'IT100008'
			|| itemCode2 == 'it100009' || itemCode2 == 'IT100009'
			|| itemCode2 == 'it100010' || itemCode2 == 'IT100010') {
		clear_R3();
		subfunctions();
		alert(" * ItemCode on Row 3 is not for Sale ! ");
		$("#icode2").val('');
		return false;
	} else {
		return true;
	}
}

function checkform4() {
	var itemCode3 = $("#icode3").val();
	if (itemCode3 == 'it100001' || itemCode3 == 'IT100001'
			|| itemCode3 == 'it100006' || itemCode3 == 'IT100006'
			|| itemCode3 == 'it100002' || itemCode3 == 'IT100002'
			|| itemCode3 == 'it100003' || itemCode3 == 'IT100003'
			|| itemCode3 == 'it100004' || itemCode3 == 'IT100004'
			|| itemCode3 == 'it100005' || itemCode3 == 'IT100005'
			|| itemCode3 == 'it100007' || itemCode3 == 'IT100007'
			|| itemCode3 == 'it100008' || itemCode3 == 'IT100008'
			|| itemCode3 == 'it100009' || itemCode3 == 'IT100009'
			|| itemCode3 == 'it100010' || itemCode3 == 'IT100010') {
		clear_R4();
		subfunctions();
		alert(" * ItemCode on Row 4 is not for Sale ! ");
		$("#icode3").val('');
		return false;
	} else {
		return true;
	}
}

function checkform5() {

	var itemCode4 = $("#icode4").val();
	if (itemCode4 == 'it100001' || itemCode4 == 'IT100001'
			|| itemCode4 == 'it100006' || itemCode4 == 'IT100006'
			|| itemCode4 == 'it100002' || itemCode4 == 'IT100002'
			|| itemCode4 == 'it100003' || itemCode4 == 'IT100003'
			|| itemCode4 == 'it100004' || itemCode4 == 'IT100004'
			|| itemCode4 == 'it100005' || itemCode4 == 'IT100005'
			|| itemCode4 == 'it100007' || itemCode4 == 'IT100007'
			|| itemCode4 == 'it100008' || itemCode4 == 'IT100008'
			|| itemCode4 == 'it100009' || itemCode4 == 'IT100009'
			|| itemCode4 == 'it100010' || itemCode4 == 'IT100010') {
		clear_R5();
		subfunctions();
		alert(" * ItemCode on Row 5 is not for Sale ! ");
		$("#icode4").val('');
		return false;
	} else {
		return true;
	}
}

function calc_AmtAfterLess(){
	
	var amountAfterLess = $("#amountAfterLess").val();
	amountAfterLess = Init_Value(amountAfterLess);
	
	var lessAmt = $("#lessAmt").val();
	lessAmt = Init_Value(lessAmt);
	
	var tempRowAmt = $("#tempRowAmt").val();
	tempRowAmt = Init_Value(tempRowAmt);
	
	var newAmt = parseFloat(tempRowAmt) - parseFloat(amountAfterLess);
	var newDiscAmt = parseFloat(newAmt) + parseFloat(lessAmt);
	
	alert("tempRowAmt : "+tempRowAmt);
	alert("lessAmt : "+lessAmt);
	alert("newDisc Amt :"+newDiscAmt);
	
	if(newDiscAmt <= 0){
		$("#amountAfterLess").val(parseFloat(tempRowAmt).toFixed(2));
		$("#lessAmt").val(parseFloat(lessAmt).toFixed(2));
	}else{
		$("#lessAmt").val(parseFloat(newDiscAmt).toFixed(2));
	}
	
	
	
	subfunctions();
}

function subfunctions() {
	
	/**************** calling SUBTOTAL calculate function *********************/
	
	var R1amt = $("#amountAfterLess").val();
	R1amt = Init_Value(R1amt);
	
	var R2amt = $("#amountAfterLess1").val();
	R2amt = Init_Value(R2amt);
	
	var R3amt = $("#amountAfterLess2").val();
	R3amt = Init_Value(R3amt);
	
	var R4amt = $("#amountAfterLess3").val();
	R4amt = Init_Value(R4amt);
	
	var R5amt = $("#amountAfterLess4").val();
	R5amt = Init_Value(R5amt);
	
	var subtotal = parseFloat(R1amt) + parseFloat(R2amt) + parseFloat(R3amt) + parseFloat(R4amt) + parseFloat(R5amt);
	$("#subtotal").val(subtotal.toFixed(2));
	
	/************************ calling Vat calculate function *******************/
	var r1Vat = $("#vat").val();
	r1Vat = Init_Value(r1Vat);
	
	var r2Vat = $("#vat1").val();
	r2Vat = Init_Value(r2Vat);
	
	var r3Vat = $("#vat2").val();
	r3Vat = Init_Value(r3Vat);
	
	var r4Vat = $("#vat3").val();
	r4Vat = Init_Value(r4Vat);
	
	var r5Vat = $("#vat4").val();
	r5Vat = Init_Value(r5Vat);
	
	var r1Vatp = parseFloat(r1Vat) / 100; // vatamt1 for vat1 percentage
	var r1Vatamt = parseFloat(r1Vatp) * parseFloat(R1amt); // vatamt1 * amt1
	
	var r2Vatp = parseFloat(r2Vat) / 100;
	var r2Vatamt = parseFloat(r2Vatp) * parseFloat(R2amt);

	var r3Vatp = parseFloat(r3Vat) / 100;
	var r3Vatamt = parseFloat(r3Vatp) * parseFloat(R3amt);

	var r4Vatp = parseFloat(r4Vat) / 100;
	var r4Vatamt = parseFloat(r4Vatp) * parseFloat(R4amt);

	var r5Vatp = parseFloat(r5Vat) / 100;
	var r5Vatamt = parseFloat(r5Vatp) * parseFloat(R5amt);
	
	var taxAmt = parseFloat(r1Vatamt) + parseFloat(r2Vatamt) + parseFloat(r3Vatamt) + parseFloat(r4Vatamt) + parseFloat(r5Vatamt);
	
	$("#tax").val(taxAmt.toFixed(2));
	
	/**************************** calling Trade Discount calculate function *********************/
	var R1disc = $("#lessAmt").val();
	R1disc = Init_Value(R1disc);
	
	var R2disc = $("#lessAmt1").val();
	R2disc = Init_Value(R2disc);
	
	var R3disc = $("#lessAmt2").val();
	R3disc = Init_Value(R3disc);
	
	var R4disc = $("#lessAmt3").val();
	R4disc = Init_Value(R4disc);
	
	var R5disc = $("#lessAmt4").val();
	R5disc = Init_Value(R5disc);
	
	var tradedisc = parseFloat(R1disc) + parseFloat(R2disc) + parseFloat(R3disc) + parseFloat(R4disc) + parseFloat(R5disc);
	$("#tradediscount").val(tradedisc.toFixed(2));
	
	/** check payment mode check boxes are hidden */
	check_hide();
	/** calling Bill Amount and Balanace To Pay calculations function */
	calc_billamount();
	
}
/** Ajax get SalesOrder Id code to Server formsalescontroller*/
function ajaxSalesOrderID(){

	$("#salesOrderIds").val("");
	$("#salesOrderIds").empty();
	$("#order_Amount").val("0.00");
	$("#rateFixAmount").val("0.00");
	$("#rateFixGrams").val("0.00");
	$("#boardRateSO").val("0.00");
	$("#rateFixDifferenceAmount").val("0.00");
	$('<option value="0">Select</option>').appendTo("#salesOrderIds");
	var customerName=$("#custName").val();
	if(customerName!="walk_in"){
		$.ajax({
			type:'GET',
			url:'customer_name.htm',
			data:{customer:customerName},
			success:function(data){					
				if(data!=""){								
					$("select[id$=salesOrderIds] > option").remove();
					var arr=new Array();
					data=data.replace('[','');
					data=data.replace(']','');
					data=data.replace('[','');
					data=data.replace(']','');
					arr = data.split(',');
										
					$.each(arr,function(index, item) {
			        
						var result = item.replace(/^\s+|\s+$/g,'').replace(/\s+/g,' ');
						if(item != ']' && item !=',' && item !=' ' && item !='' && item != '[' )
						{					
							if(index == 0){
								$("#salesOrderIds").get(0).options[index] = new Option('Select',result);
							}else{
								$("#salesOrderIds").get(0).options[index] = new Option(result);
							}
						}				
					});
						$('select option:empty').remove();
				}else{
					$("#salesOrderIds").options[0].options[index] = new Option(0);
				}
						// $('<option value="0">Select</option>').appendTo("#billNo");
						},
						
				error:function(xmlHttpRequest, textStatus, errorThrown){
					if(xmlHttpRequest.readyState=0 || xmlHttpRequest.status == 0)
						return;
					},
			});
		
	}
	
}
/**
 * Ajax get Purchase BIll NO code for Server FormSalesController add on 16-8-12
 * for bug no 1982
 */

function ajaxPurchaseBillno(){
	
	$("#billNo").val("");
	$("#billNo").empty();
	$('<option value="0">Select</option>').appendTo("#billNo");
	var custName=$("#custName").val();
	var salesid=$("#salesID").val();
	if(custName!="walk_in"){
		
		$.ajax({
			type:'GET',
			url:'PurchaeBill_Customername.htm',
			data:{customerPurchaeBillno:custName,SalesId:salesid},
			success:function(data){
				if(data!=""){
					
				
				$("select[id$=billNo] > option").remove();
				var arr=new Array();
				data=data.replace('[','');
				data=data.replace(']','');
				data=data.replace('[','');
				data=data.replace(']','');
				arr = data.split(',');
				
				
				
				$.each(arr,function(index, item) {
		        
					var result = item.replace(/^\s+|\s+$/g,'').replace(/\s+/g,' ');
					if(item != ']' && item !=',' && item !=' ' && item !='' && item != '[' )
					{					
		              $("#billNo").get(0).options[index] = new Option(result);		               
					}				
				});
					$('select option:empty').remove();
			}else{
				$("#billNo").options[0] = new Option("Select");
			}
					// $('<option value="0">Select</option>').appendTo("#billNo");
					},
					
			error:function(xmlHttpRequest, textStatus, errorThrown){
				if(xmlHttpRequest.readyState=0 || xmlHttpRequest.status == 0)
					return;
				},
		});
		
	}
	ajaxSalesOrderID();
	doAjax();
	ajaxSalesReturnID();//add for SR Id call 14-02-13
}

/** Ajax get advance code to server formsalescontroller 
function ajaxSalesAdvance() {
	
	$("#order_Amount").val("0.00");
	var id = $("#salesOrderIds").val();
	$.ajax({
		type : 'GET',
		url : 'order_advance.htm',
		data : {
			Amt : id
		},
		success : function(data) {
			$("#order_Amount").val(data);
			//payment_Mode();
			balance_to_pay();
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			if (xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
				return; // it's not really an error
		},
	});

}*/
/** Ajax get advance code to server formsalescontroller udated with ratefix 25-1-13 *****/
function ajaxSalesAdvance() {
	
	$("#order_Amount").val("0.00");
	$("#rateFixAmount").val("0.00");
	$("#rateFixGrams").val("0.00");
	$("#boardRateSO").val("0.00");
	$("#rateFixDifferenceAmount").val("0.00");
	var id = $("#salesOrderIds").val();
	
	$.ajax({
		type : 'GET',
		url : 'order_advance.htm',
		data : {
			SelectedValues : id.toString()
		},
		success : function(data) {
			
			if(data==0){
				subfunctions();
			}
			
			var arr = new Array();
			data = data.replace('[', '');
			data = data.replace(']', '');
			arr = data.split(',');
			//$("#order_Amount").val(data);
			$.each(arr, function(index, item) {
				switch (index) {
				case 0: {
					
					if(item==null || item==0 || item==""){
							$("#order_Amount").val("0.00");
						}else{
							$("#order_Amount").val(parseFloat(item).toFixed(2));
						}
						break;
						}
				
				case 1: {
					if(item==null || item==0 || item==""){
						$("#rateFixAmount").val("0.00");
					}else{
						
						$("#rateFixAmount").val(parseFloat(item).toFixed(2));
						
					}
					break;
					}
					
				case 2: {
					if(item==null || item==0 || item==""){
						$("#rateFixGrams").val("0.00");
					}else{
						$("#rateFixGrams").val(parseFloat(item).toFixed(2));
						
					}
					break;
					}
					
				case 3: {
					if(item==null || item==0 || item==""){
						$("#boardRateSO").val("0.00");
					}else{
						$("#boardRateSO").val(parseFloat(item).toFixed(2));
						
						
					}
					break;
					}
				case 4: {
					if(item==null || item==0 || item==""){
						$("#ornamentRate").val("0.00");
					}else{
						$("#ornamentRate").val(parseFloat(item).toFixed(2));
					}
					ratefixCalculation();
					break;
				}
				}
			});
			balance_to_pay();
			
					},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			if (xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
				return; // it's not really an error
		},
	});

	
}

/*** Calculation for RateFix Difference *****/
function ratefixCalculation(){
	
	var SOBoardRate=$("#boardRateSO").val();
	SOBoardRate = Init_Value(SOBoardRate);
	
	var ORBoardRate=$("#ornamentRate").val();
	ORBoardRate = Init_Value(ORBoardRate);
	
	var SOFixGrams=$("#rateFixGrams").val();
	SOFixGrams = Init_Value(SOFixGrams);
	
	var tempBrate=parseFloat(SOBoardRate) - parseFloat(ORBoardRate);
	var actualFixdif=parseFloat(tempBrate)*parseFloat(SOFixGrams);
	
	$("#rateFixDifferenceAmount").val(actualFixdif.toFixed(2));
	
	if(ORBoardRate >= SOBoardRate){
	//	If Ornamental sales board rate is greater than sales order board rate then 
		
		var subtAmt=$("#subtotal").val();
		subtAmt = Init_Value(subtAmt);
		
		var vatP=$("#tax").val();
		vatP = Init_Value(vatP);
		
		var ratediff=$("#rateFixDifferenceAmount").val();
		ratediff = Init_Value(ratediff);
		
		var subPlusVat=parseFloat(subtAmt)+parseFloat(vatP);
		subPlusVat = Init_Value(subPlusVat);
		
		if(ratediff<0){
			var tempConrt=parseFloat(ratediff)*-1;
			ratediff=tempConrt;
			//alert(tempConrt);
				}
		var roundOffam = $("#roundOff").val();
		roundOffam = Init_Value(roundOffam);
		
		var tempamt=parseFloat(subPlusVat)-parseFloat(ratediff) + parseFloat(roundOffam);
		
		$("#billamount").val(tempamt.toFixed(2));
	
	}else{// If Ornamental sales is smaller than sales order board rate then
		var subtotAmt=$("#subtotal").val();
		subtotAmt = Init_Value(subtotAmt);
		
		var vatAmt=$("#tax").val();
		vatAmt = Init_Value(vatAmt);
		
		var rdiff=$("#rateFixDifferenceAmount").val();
		rdiff = Init_Value(rdiff);
		
        var subplusVat=parseFloat(subtotAmt)+parseFloat(vatAmt);
        subplusVat = Init_Value(subplusVat);
		
		if(rdiff<0){
			var tempConverrt=parseFloat(rdiff)*-1;
			rdiff=tempConverrt;
				}
		var roundOffam = $("#roundOff").val();
		roundOffam = Init_Value(roundOffam);
		
		var tempamt=parseFloat(subplusVat)+parseFloat(rdiff) + parseFloat(roundOffam);
		$("#billamount").val(tempamt.toFixed(2));
		
	}
	
}
/** Ajax get Pan Number code to server formsalescontroller */
function ajaxPanNumber() {
	var customerName = $("#custName").val();
	if (customerName != "walk_in") {
		$
				.ajax({
					type : 'GET',
					url : 'pan_Number.htm',
					data : {
						num : customerName
					},
					success : function(data) {
						$("#fpan_Number").val(data);
					},
					error : function(xmlHttpRequest, textStatus, errorThrown) {
						if (xmlHttpRequest.readyState = 0 || xmlHttpRequest.status == 0)
							return;
					},
				});
	} else {
		$("#fpan_Number").val('');
	}
}

/** Ajax get purchase amount code to server formsalescontroller */
function doAjax() {
	$("#exchange_Amount").val("0.00");
	var id = $("#billNo").val();
	$.ajax({
		type : 'GET',
		url : 'purchase_Amt.htm',
		data : {
			name : id
		},
		success : function(data) {
			$("#exchange_Amount").val(data);
			//payment_Mode();
			balance_to_pay();
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			if (xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
				return; // it's not really an error
		},
	});
	
	
}

/** Ajax code ends here */

/**
 * This additional code same as row_change is to calculate on change disc Amount
 * field
 */
function calc_discount1() {

	var amt = 0.0;
	var amtVal1 = 0.0;
	var amtVal2 = 0.0;
	var vatemp = 0.0;
	var wastgms = 0.0;
	var rate = $("#brate").val();
	rate = Init_Value(rate);
	
	var va = $("#valueAdditionCharges").val();
	va = Init_Value(va);
	
	var mcgrams = $("#mcgrams").val();
	mcgrams = Init_Value(mcgrams);
	
	var grwt = $("#grwt").val();
	grwt = Init_Value(grwt);
	
	var stncost = $("#stn").val();
	stncost = Init_Value(stncost);
	
	var lessAmt = $("#lessAmt").val();
	lessAmt = Init_Value(lessAmt);
	
	var salesHMCharges = $("#salesHMCharges").val();
	salesHMCharges = Init_Value(salesHMCharges);

	vatemp = parseFloat(va) / 100;
	wastgms = parseFloat(grwt) * (vatemp);
	amtVal1 = parseFloat(grwt) * parseFloat(rate);
	amtVal2 = parseFloat(wastgms) * parseFloat(rate);
	amt = amtVal1 + amtVal2 + parseFloat(stncost) + parseFloat(mcgrams) + + parseFloat(salesHMCharges);
	
	var DiscPer = "0.00";
	
	if(grwt != 0 && rate !=0){
		DiscPer = parseFloat(lessAmt) / ( parseFloat(grwt) * 0.01 * parseFloat(rate) );
	}
	DiscPer = Init_Value(DiscPer);

	$("#lessPercent").val(parseFloat(DiscPer).toFixed(2));

	/** To avoid negative values */
	if (amt > lessAmt) {
		amt = amt - lessAmt;
	} else {
		$("#lessAmt").val(0);
		$("#lessPercent").val(0);
	}

	if (amt != null && !isNaN(amt)) {

		$("#wastbygms").val(wastgms.toFixed(3));

		if ($("#grwt").val() == 0 || $("#brate").val() == 0) {
			$("#amountAfterLess").val(0);
		} else {
			$("#amountAfterLess").val(amt.toFixed(2));
		}
		/** calling Sub calculation functions */
		subfunctions();

	} else {
		$("#amountAfterLess").val(0);
	}
}

function calc_discount2() {

	var amt = 0.0;
	var amtVal1 = 0.0;
	var amtVal2 = 0.0;
	var vatemp = 0.0;
	var wastgms = 0.0;
	var rate = $("#brate1").val();
	rate = Init_Value(rate);

	var va = $("#valueAdditionCharges1").val();
	va = Init_Value(va);

	var mcgrams = $("#mcgrams1").val();
	mcgrams = Init_Value(mcgrams);

	var grwt = $("#grwt1").val();
	grwt = Init_Value(grwt);

	var stncost = $("#stn1").val();
	stncost = Init_Value(stncost);

	var lessAmt1 = $("#lessAmt1").val();
	lessAmt1 = Init_Value(lessAmt1);
	
	var salesHMCharges1 = $("#salesHMCharges1").val();
	salesHMCharges1 = Init_Value(salesHMCharges1);

	vatemp = parseFloat(va) / 100;
	wastgms = parseFloat(grwt) * (vatemp);
	amtVal1 = parseFloat(grwt) * parseFloat(rate);
	amtVal2 = parseFloat(wastgms) * parseFloat(rate);
	amt = amtVal1 + amtVal2 + parseFloat(stncost) + parseFloat(mcgrams) + parseFloat(salesHMCharges1);
	
	/** For Disc Percent dynamic Calc**/
	var DiscPer1 = "0.00";
	
	if(grwt != 0 && rate !=0){
		DiscPer1 = parseFloat(lessAmt1) / ( parseFloat(grwt) * 0.01 * parseFloat(rate) );
	}
	DiscPer1 = Init_Value(DiscPer1);

	$("#lessPercent1").val(parseFloat(DiscPer1).toFixed(2));

	/** To avoid negative values */
	if (amt > lessAmt1) {
		amt = amt - lessAmt1;
	} else {
		$("#lessAmt1").val(0);
		$("#lessPercent1").val(0);
	}

	if (amt != null && !isNaN(amt)) {

		$("#wastbygms1").val(wastgms.toFixed(3));

		if ($("#grwt1").val() == 0 || $("#brate1").val() == 0) {
			$("#amountAfterLess1").val(0);
		} else {
			$("#amountAfterLess1").val(amt.toFixed(2));
		}

		subfunctions();

	} else {
		$("#amountAfterLess1").val(0);
	}
	subfunctions();
}

function calc_discount3() {

	var amt = 0.0;
	var amtVal1 = 0.0;
	var amtVal2 = 0.0;
	var vatemp = 0.0;
	var wastgms = 0.0;
	var rate = $("#brate2").val();
	rate = Init_Value(rate);

	var va = $("#valueAdditionCharges2").val();
	va = Init_Value(va);

	var mcgrams = $("#mcgrams2").val();
	mcgrams = Init_Value(mcgrams);

	var grwt = $("#grwt2").val();
	grwt = Init_Value(grwt);

	var stncost = $("#stn2").val();
	stncost = Init_Value(stncost);

	var lessAmt2 = $("#lessAmt2").val();
	lessAmt2 = Init_Value(lessAmt2);
	
	var salesHMCharges2 = $("#salesHMCharges2").val();
	salesHMCharges2 = Init_Value(salesHMCharges2);

	vatemp = parseFloat(va) / 100;
	wastgms = parseFloat(grwt) * (vatemp);
	amtVal1 = parseFloat(grwt) * parseFloat(rate);
	amtVal2 = parseFloat(wastgms) * parseFloat(rate);
	amt = amtVal1 + amtVal2 + parseFloat(stncost) + parseFloat(mcgrams) + parseFloat(salesHMCharges2);
	
	/** For Disc Percent dynamic Calc**/
	var DiscPer2 = "0.00";
	
	if(grwt != 0 && rate !=0){
		DiscPer2 = parseFloat(lessAmt2) / ( parseFloat(grwt) * 0.01 * parseFloat(rate) );
	}
	DiscPer2 = Init_Value(DiscPer2);

	$("#lessPercent2").val(parseFloat(DiscPer2).toFixed(2));
	
	/** To avoid negative values */
	if (amt > lessAmt2) {
		amt = amt - lessAmt2;
	} else {
		$("#lessAmt2").val(0);
		$("#lessPercent2").val(0);
	}

	if (amt != null && !isNaN(amt)) {
		$("#wastbygms2").val(wastgms.toFixed(3));

		if ($("#grwt2").val() == 0 || $("#brate2").val() == 0) {
			$("#amountAfterLess2").val(0);
		} else {
			$("#amountAfterLess2").val(amt.toFixed(2));
		}
		subfunctions();

	} else {
		$("#amountAfterLess2").val(0);
	}
}

function calc_discount4() {

	var amt = 0.0;
	var amtVal1 = 0.0;
	var amtVal2 = 0.0;
	var vatemp = 0.0;
	var wastgms = 0.0;
	var rate = $("#brate3").val();
	rate = Init_Value(rate);

	var va = $("#valueAdditionCharges3").val();
	va = Init_Value(va);

	var mcgrams = $("#mcgrams3").val();
	mcgrams = Init_Value(mcgrams);

	var grwt = $("#grwt3").val();
	grwt = Init_Value(grwt);

	var stncost = $("#stn3").val();
	stncost = Init_Value(stncost);

	var lessAmt3 = $("#lessAmt3").val();
	lessAmt3 = Init_Value(lessAmt3);
	
	var salesHMCharges3 = $("#salesHMCharges3").val();
	salesHMCharges3 = Init_Value(salesHMCharges3);

	vatemp = parseFloat(va) / 100;
	wastgms = parseFloat(grwt) * (vatemp);
	amtVal1 = parseFloat(grwt) * parseFloat(rate);
	amtVal2 = parseFloat(wastgms) * parseFloat(rate);
	amt = amtVal1 + amtVal2 + parseFloat(stncost) + parseFloat(mcgrams) + parseFloat(salesHMCharges3);
	
	/** For Disc Percent dynamic Calc**/
	var DiscPer3 = "0.00";
	
	if(grwt != 0 && rate !=0){
		DiscPer3 = parseFloat(lessAmt3) / ( parseFloat(grwt) * 0.01 * parseFloat(rate) );
	}
	DiscPer3 = Init_Value(DiscPer3);

	$("#lessPercent3").val(parseFloat(DiscPer3).toFixed(2));
	
	/** To avoid negative values */
	if (amt > lessAmt3) {
		amt = amt - lessAmt3;
	} else {
		$("#lessAmt3").val(0);
		$("#lessPercent3").val(0);
	}

	if (amt != null && !isNaN(amt)) {
		$("#wastbygms3").val(wastgms.toFixed(3));
		if ($("#grwt3").val() == 0 || $("#brate3").val() == 0) {
			$("#amountAfterLess3").val(0);
		} else {
			$("#amountAfterLess3").val(amt.toFixed(2));
		}
		subfunctions();
	} else {
		$("#amountAfterLess3").val(0);
	}
}

function calc_discount5() {

	var amt = 0.0;
	var amtVal1 = 0.0;
	var amtVal2 = 0.0;
	var vatemp = 0.0;
	var wastgms = 0.0;
	var rate = $("#brate4").val();
	rate = Init_Value(rate);

	var va = $("#valueAdditionCharges4").val();
	va = Init_Value(va);

	var mcgrams = $("#mcgrams4").val();
	mcgrams = Init_Value(mcgrams);

	var grwt = $("#grwt4").val();
	grwt = Init_Value(grwt);

	var stncost = $("#stn4").val();
	stncost = Init_Value(stncost);

	var lessAmt4 = $("#lessAmt4").val();
	lessAmt4 = Init_Value(lessAmt4);
	
	var salesHMCharges4 = $("#salesHMCharges4").val();
	salesHMCharges4 = Init_Value(salesHMCharges4);


	vatemp = parseFloat(va) / 100;
	wastgms = parseFloat(grwt) * (vatemp);
	amtVal1 = parseFloat(grwt) * parseFloat(rate);
	amtVal2 = parseFloat(wastgms) * parseFloat(rate);
	amt = amtVal1 + amtVal2 + parseFloat(stncost) + parseFloat(mcgrams) + parseFloat(salesHMCharges4);
	
	/** For Disc Percent dynamic Calc**/
	var DiscPer4 = "0.00";
	
	if(grwt != 0 && rate !=0){
		DiscPer4 = parseFloat(lessAmt4) / ( parseFloat(grwt) * 0.01 * parseFloat(rate) );
	}
	DiscPer4 = Init_Value(DiscPer4);

	$("#lessPercent4").val(parseFloat(DiscPer4).toFixed(2));

	/** To avoid negative values */
	if (amt > lessAmt4) {
		amt = amt - lessAmt4;
	} else {
		$("#lessAmt4").val(0);
		$("#lessPercent4").val(0);
	}

	if (amt != null && !isNaN(amt)) {
		$("#wastbygms4").val(wastgms.toFixed(3));
		if ($("#grwt4").val() == 0 || $("#brate4").val() == 0) {
			$("#amountAfterLess4").val(0);
		} else {
			$("#amountAfterLess4").val(amt.toFixed(2));
		}

		subfunctions();

	} else {
		$("#amountAfterLess4").val(0);
	}
}

function checkgrosswt() {
	// ROW1
	var metaltype = $("#btype").val();
	if (metaltype.length == 0) {
		$("#grwt").val(0);
		hgrwt = 0;
	}
	// ROW2
	var metaltype1 = $("#btype1").val();
	if (metaltype1.length == 0) {
		$("#grwt1").val(0);
		hgrwt1 = 0;
	}
	// ROW3
	var metaltype2 = $("#btype2").val();
	if (metaltype2.length == 0) {
		$("#grwt2").val(0);
		hgrwt2 = 0;
	}
	// ROW4
	var metaltype3 = $("#btype3").val();
	if (metaltype3.length == 0) {
		$("#grwt3").val(0);
		hgrwt3 = 0;
	}
	// ROW5
	var metaltype4 = $("#btype4").val();
	if (metaltype4.length == 0) {
		$("#grwt4").val(0);
		hgrwt4 = 0;
	}
}

/** Ajax get Sales Return Id code to Server formsalescontroller added on 14-02-13*/
function ajaxSalesReturnID(){

	$("#salesReturnId").val("");
	$("#salesReturnId").empty();
	$('<option value="0">Select</option>').appendTo("#salesReturnId");
	var customerName=$("#custName").val();
	if(customerName!="walk_in"){
		$.ajax({
			type:'GET',
			url:'SalesReturnID_Customername.htm',
			data:{custSRId:customerName},
			success:function(data){					
				if(data!=""){								
					$("select[id$=salesReturnId] > option").remove();
					var arr=new Array();
					data=data.replace('[','');
					data=data.replace(']','');
					data=data.replace('[','');
					data=data.replace(']','');
					arr = data.split(',');
										
					$.each(arr,function(index, item) {
			        
						var result = item.replace(/^\s+|\s+$/g,'').replace(/\s+/g,' ');
						if(item != ']' && item !=',' && item !=' ' && item !='' && item != '[' )
						{					
							if(index == 0){
								$("#salesReturnId").get(0).options[index] = new Option('Select',result);
							}else{
								$("#salesReturnId").get(0).options[index] = new Option(result);
							}
						}				
					});
						$('select option:empty').remove();
				}else{
					$("#salesReturnId").options[0].options[index] = new Option(0);
				}
						// $('<option value="0">Select</option>').appendTo("#billNo");
						},
						
				error:function(xmlHttpRequest, textStatus, errorThrown){
					if(xmlHttpRequest.readyState=0 || xmlHttpRequest.status == 0)
						return;
					},
			});
		
	}
	
}

/** Ajax get Sales Return amount code to server formsalescontroller  14-02-13*/
function getSRamount() {
	$("#salesReturnAmount").val("0.00");
	var id = $("#salesReturnId").val();
	$.ajax({
		type : 'GET',
		url : 'SalesReturn_Amt.htm',
		data : {
			name : id
		},
		success : function(data) {
			$("#salesReturnAmount").val(data);
			//payment_Mode();
			balance_to_pay();
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			if (xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
				return; // it's not really an error
		},
	});
}
