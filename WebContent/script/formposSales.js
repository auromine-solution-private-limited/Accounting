
     $(document).ready(function(){
    	 
    	 //date picker 
    	 $("#date").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});	
    		var myDate = new Date();
    		var month = myDate.getMonth() + 1;
    		var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
    	
    	 
    	 /**** POS Sales Calculationa ,dynamic row add fnction calls ***/
    	 
    	 $(".ReceiptMode .table .head input").click(POSSalesReceiptMode); //POSSales
 		POSSalesReceiptMode();//POSSales
 		POSSalesaddrow();
 		POSSalesdel();	
 	//	POSSalesReturnDesign();
 		
 		//****** autocomplete for customer name in Pos Sales*****/
   	 $(".possupplier1").hide();
		$('#poscustomer').change(PosCustomerName).trigger('change');
		//** POSSales Method Calls ****//
		POSSalesCalc();
		
		$("[id^=salesrates]").keyup(POSSalesRateCalcs);
		
		//*** POS Sales Discount Rate Caluction 25-7-12 ****/
		$("[id^=discounts]").keyup(discountRateCalc);
		
		//**** POS Sales Receipt Mode Calculation Method*******///
		$("#cheque_Amount").keyup(receiptModeCalc).trigger('keyup');
		$("#card_Amount").keyup(receiptModeCalc).trigger('keyup');
		$("#voucher_Amount").keyup(receiptModeCalc).trigger('keyup');
		$("#counter_cash").keyup(receiptModeCalc).trigger('keyup');
		
		$("#cheque_Amount").keyup(AmountModeReceivedCalc).trigger('keyup');
		$("#card_Amount").keyup(AmountModeReceivedCalc).trigger('keyup');
		$("#voucher_Amount").keyup(AmountModeReceivedCalc).trigger('keyup');
		
		
		//****** for uncheck calucaltion in receipt mode *****/
		$("#card").change(receiptModeCalc).trigger('change');
		$("#cheque").change(receiptModeCalc).trigger('change');
		$("#voucher").change(receiptModeCalc).trigger('change');
		$("#cash").change(receiptModeCalc).trigger('change');
		
		$("#card").change(AmountModeReceivedCalc).trigger('change');
		$("#cheque").change(AmountModeReceivedCalc).trigger('change');
		$("#voucher").change(AmountModeReceivedCalc).trigger('change');
		//$("#cash").change(AmountModeReceivedCalc).trigger('change');
		
		
		$("#card").change(cashcaluclation).trigger('change');
		$("#cheque").change(cashcaluclation).trigger('change');
		$("#voucher").change(cashcaluclation).trigger('change');
		
		//****** for Balance to Customer Calc in receipt Mode *****/
		$("#counter_cash").keyup(CounterCashCalc).trigger('keyup');
		$("#counter_cash").keyup(cashcaluclation).trigger('keyup');
		$("#counter_cash").change(CounterCashCalc).trigger('change');
		
		//add for bug number 2388
		$("#card_Amount").keyup(cashcaluclation).trigger('keyup');
		$("#cheque_Amount").keyup(cashcaluclation).trigger('keyup');
	   $("#voucher_Amount").keyup(cashcaluclation).trigger('keyup');
		
		/* possales dicode uppercase transform ****/
		$("[id^=dIcode]").css("text-transform","uppercase");
    	 
    	 /****** ends here */
    	 
	// $('#poscustomer').keypress(autoCustomerName).trigger("keypress");
	 var URIloc = $(location).attr('href');
		var uripath = URIloc.split('/')[4];
		var errorType = $("#errorType").val();
		
		if(uripath.indexOf("viewPOSSales.htm") >= 0){
			$("#insert").hide();
			$("#update").show();
			$(".delP").hide();
			error_msg();
			if($("#billposType").val()=="Cancelled"){
				$(".smsg").html("Cancelled Bill");
				$("#cancelSales").hide();
				$("#update").hide();
			}else{
				$(".smsg").html("");
				$("#update").show();
			}
		}

		if(uripath.indexOf("formPOSsales.htm") >= 0){
			$("#insert").show();
			$("#update").hide();
			$(".delP").show();
			$("#date").val(prettyDate);
			error_msg();
		}
		
		if(errorType == "insertError") {
			$("#insert").show();
			$("#update").hide();
			$("#date").val(prettyDate);
		}else if(errorType == "updateError") {
			$("#insert").hide();
			$("#update").show();
		}
		
		/***** Cancel fucntion for EP 4-1-13******/
		$("#cancelSales").hide();
		if($("#salesType").val() == "Estimate POS Sales" && $("#update").is(":visible")){
			$("#cancelSales").show();
		}
	
		
		/** On Load hide Blank Rows in Form**/	
		$(".isDeleted").each(function(){
			var delClass = $(this).val();
			if(delClass == "true"){			
				$(this).parents(".staticRow").hide();
			}
		});
		
		$("#insert").click(reportConfirmValidation);
		$("#update").click(reportConfirmValidation);
		
		
		/** For making readonly of Return items */
		$(".itemStatus").each(function(){
			var rowNum1 = $(this).attr('title');
			var itemStatus = $("#positemStatus"+rowNum1).val();		
			if(itemStatus == "Return"){
				$("#PosrowDelButton"+rowNum1).hide();
				$("#poscustomer").attr("disabled", true);  
				$("#dIcode"+rowNum1).attr("readonly", true);
			}else{
				$("#PosrowDelButton"+rowNum1).show();
				$("#dIcode"+rowNum1).attr("disabled",false);
			}
		});
		
		/** For Enable  customer on update **/
		$("#update").bind('click',function(){
			$(".itemStatus").each(function(){
				var rowClass = $(this).val();
				if(rowClass == "Return"){			
					$("#poscustomer").attr("disabled",false);
							
				}
			});
		});
		
		/** For making readonly of Sold items */
		$(".itemStatus").each(function(){
			var rowNum1 = $(this).attr('title');
			var itemStatus = $("#positemStatus"+rowNum1).val();		
			if(itemStatus == "Sold"){
				 
				$("#dIcode"+rowNum1).attr("readonly", true); 
			}else{
				$("#dIcode"+rowNum1).show();
			}
		});
		
		
		var no = null;
		//no =  $.getUrlVar('posSalesId');
		no =  $.getUrlVar('invc');
		if(no != null) {
			var successMsg = "Last Invoice No : "+no+" Saved Successfully...";
			//alert(successMsg);
			$(".smsg").html(successMsg);
		}	
		
		/*** new script added to allow only _ and - for customer name field on 25-2-13 after bug is posted ****/
		$('#popcustomerName').keyup(function() {
			 $(this).val($(this).val().replace(/[^A-Za-z0-9\s_-]/g,''));
		});
		
		
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
     
     /** main Code for Entire Script */
     $(document).ready(function() {
     		
     	if($("#salesType").val() == ""){
     		$("#salesType").val("POS Sales");
     	}
     });
     
  /*** Hot Key Function call with Ajax bind for Estimate POS Sales ****/
     $(document).keydown(function (e){
    		
    		if($("#insert").is(":visible")){
    			if(e.which==81 && e.ctrlKey){
    				$.ajax({
    					type : 'GET',
    					url  : 'CheckEP.htm',
    					success : function(data) {
    						if(data=="yes"){
    							var salesType = $("#salesType").val(); 
    							if(salesType == "POS Sales"){
    					        	 $("#salesType").val("Estimate POS Sales");
    					        }else{
    					        	$("#salesType").val("POS Sales");
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
     
     
     
     /*    POS Sales ReceiptMode */
     function POSSalesReceiptMode(){
    		
    		
 		if($("#cash").is(":checked")){
 			$(".ReceiptMode .table tr.cash").show();
 		}
 		else{
 			$(".ReceiptMode .table tr.cash").hide();
 			$("#counter_cash").val(0);
 			$("#cash_Amount").val(0);
 			
 		}
 		if($("#cheque").is(":checked")){
 			$(".ReceiptMode .table tr.cheque").show();
 		}
 		else{
 			$(".ReceiptMode .table tr.cheque").hide();
 			$("#cheque_Amount").val(0);
 		}
 		if($("#card").is(":checked")){
 			$(".ReceiptMode .table tr.card").show();
 		}
 		else{
 			$(".ReceiptMode .table tr.card").hide();
 			$("#card_Amount").val(0);
 		}
 		if($("#voucher").is(":checked")){
 			$(".ReceiptMode .table tr.voucher").show();
 			
 		}
 		else{
 			$(".ReceiptMode .table tr.voucher").hide();
 			$("#voucher_Amount").val(0);
 		} 
 		if($("#credit").is(":checked")){
 			$(".ReceiptMode .table tr.credit").show();
 		}
 		else{
 			$(".ReceiptMode .table tr.credit").hide();
 		}
 	
 		
 }
     
     /************************       POS Sales Add / Delete row           *******************************/

     function POSSalesaddrow(){
     	
     	var counter = 1;
     	
     	
     		$("#addItem").click(function(){
     			
     			var URIloc = $(location).attr('href');
    			var uripath = URIloc.split('/')[4];
    			
    			if(uripath.indexOf("viewPOSSales.htm") >= 0){
    				counter = $("[id^=amt]").size();
    			} 
    			else{
    				counter = $("[id^=amt]").size();				
    			}
     			
     			var delbutton = '<td><input type="button" name="delete" class="del" value="" style="width:20px;"/></td>';
     			var itemCode = '<td><input type="text" name="listpossalesItem['+counter+'].posItemCode" id="dIcode' + counter + '"  class="icode" title="'+counter+'"></td>';
     			var itemName = '<td><input type="text" name="listpossalesItem['+counter+'].itemName" readonly="true" id="dIname' + counter + '" class="category iname" title="'+counter+'"  ></td>';
     			var categoryname = '<td><input type="text" name="listpossalesItem['+counter+'].categoryName" readonly="true" id="categoryname' + counter + '" title="'+counter+'" class="category iname" ></td>';
     			var qty = '<td><input type="text" name="listpossalesItem['+counter+'].quantity" readonly="true" id="qty' + counter + '" class="iname QtyPcs"  title="'+counter+'" ></td>';
     			var pcs = '<td><input type="text" name="listpossalesItem['+counter+'].totalPieces" readonly="true" id="pcs' + counter + '" class="iname QtyPcs" title="'+counter+'"  ></td>';
     			var rate = '<td><input type="text" name="listpossalesItem['+counter+'].salesRate"  id="salesrates'+ counter +'" class="iname posrate"  title="'+counter+'"value="0.00"  onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" ></td>';
     			var discount = '<td><input type="text" name="listpossalesItem['+counter+'].discountAmount" id="discounts' + counter + '" class="iname" title="'+counter+'"value="0.00"  onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" ></td>';
     			var tax = '<td><input type="text" name="listpossalesItem['+counter+'].salesTax" readonly="true" id="tax' + counter + '" class="iname" title="'+counter+'" value="0.00"></td>';
     			var amt = '<td><input type="text" name="listpossalesItem['+counter+'].salesAmount" readonly="true"id="amt' + counter + '" class="iname posrate" title="'+counter+'" ></td>';
     			var deleted = '<td style="display:none"><input name="listpossalesItem['+counter+'].POSdeleted" id="posdeleted'+counter+'" title="'+counter+'" class="isDeleted" value="false" /></td>';
     			var itemstatus = '<td style="display:none"><input name="listpossalesItem['+counter+'].itemStatus" id="positemStatus'+counter+'" title="'+counter+'"   /></td>';
     			var rows = '<tr class="staticRow">'+delbutton+ itemCode + categoryname + itemName + qty + pcs + rate + discount + tax  +amt +'<input type="hidden" name="listpossalesItem['+counter+'].salesItemID" />'+deleted+itemstatus+'</tr>';
     			$("tr.staticRow:last").after(rows);
     			POSSalesdel();	
     			// possales dicode uppercase transform 
     			$("[id^=dIcode]").css("text-transform","uppercase");
     			
     		//	$("#insert").click(reportConfirmValidation);
     		//	$("#update").click(reportConfirmValidation);

     			$("input").focus(ReadonlyFocus);
     			POSSalesCalc();
     			$("[id^=salesrates]").keyup(POSSalesRateCalcs);
     			$("[id^=discounts]").keyup(discountRateCalc);
     			
     			
     			counter++;			
     		});		
     }
     
     
     
     /***  PosSalesReturn List design 
     function POSSalesReturnDesign(){
     	
     	//$(".salesreturn_return").hide();
     	//var check = $(".salesreturn_checkbox").is(":checked");
     	
     	if($(".salesreturn_checkbox").is("checked", "checked")){
     		$(".salesreturn_return").hide();
     	}	
     	else{
     		$(".salesreturn_return").show();
     	}
     }*****/
     
     /*********    poscustomerName    *********/
     function PosCustomerName(){
    	
     	var customer_type = $("#poscustomer").val();
     		if(customer_type != "Walk-in"){
     			$(".possupplier").hide();
     			$(".possupplier1").show();
     		}
     		else{
     			$(".possupplier1").hide();
     			$(".possupplier").show();
     		}
     }
     
     /* PoS sales on search data fetch and calcution */
     function POSSalesCalc(){	
    	 
     
     	$("[id^=dIcode]").focusout(function(){
     	var vlue=$(this).val();
     	var name = $(this).attr("title");
     	var error = false;
     	$("[id^=dIcode]").each(function(){
 			var dIcodeattr =  $(this).attr("title");
 			var dIcodeID =  $(this).attr("id");
 			var dIcodeVal =  $("#"+dIcodeID).val();
 			var POSDltFlds =  $("#posdeleted"+dIcodeattr).val();
 			var POSitemStatus = $("#positemStatus"+dIcodeattr).val();
 			if(dIcodeattr != name){
 				if(POSDltFlds == "false"){
 					if(vlue.toLowerCase() == dIcodeVal.toLowerCase()){
	 					if(POSitemStatus == "Sold" || POSitemStatus == "" || POSitemStatus==null){
	 						$("#form_error").slideDown("fast");
		 					$("#form_error span").text("*Item code is already Scanned........");
		 					//$("#dIcode"+name).selected();
		 					
		 					$("#categoryname"+name).val("");	$("#dIname"+name).val("");
		 					$("#qty"+name).val("");	$("#pcs"+name).val("");
		 					$("#salesrates"+name).val("");	$("#discounts"+name).val("");
		 					error = true;
		 					return false;
	 					} 				
 					}	
	 			} 				
 			}
	     });
     	
     	if(error == false){
     	$.ajax({						
     			type:'POST',
     			url:'itemCodeDetail.htm',
     			data: { iCodeValue : vlue },
     			
     			success:function(data) {
     				if(data=="Invalid Itemcode"){
     					$("#form_error").slideDown("fast");
     					$("#form_error span").text("*Invalid Item Code");
     				}else{
     				data=data.replace('[','');
     				data=data.replace(']','');
     				
     				var arr=new Array();																
     				arr = data.split(',');
     				
     				$.each(arr,function(index, item1) 
     				{
     					switch(index)
     					{
     					case 0: {
     						if(item1==null || item1==0 || item1==""){
     							$("#categoryname"+name).val("");
     						}else{
     							
     							$("#categoryname"+name).val(item1);
     							
     						}
     						break;
     						}
     					case 1: {
     						
     						if(item1==null || item1==0 || item1==""){
     							$("#dIname"+name).val("");
     						}else{
     							
     							$("#dIname"+name).val(item1);
     							
     						}
     						
     						break;
     					}
     					case 2: {
     						$("#qty"+name).val(item1);
     						break;
     					}
     					case 3: {
     						$("#pcs"+name).val(item1);
     						break;
     					}
     					case 4: {
                                  
     						$("#salesrates"+name).val(item1);
     						
     						break;
     					}
     					case 5: {
     						var rateS=$("#salesrates"+name).val();
     						var disct=$("#discounts"+name).val(item1);
     						if(disct.length === 0 || disct === '' || disct === null){
     							disct = "0.00";			
     						}
     						if(rateS.length === 0 || rateS === '' || rateS === null){
     							rateS = "0.00";			
     						}
     						 
     						 disct= $("#discounts"+name).val();
     						
     						var dis=parseFloat(rateS) * parseFloat(disct)/100;
     						$("#discounts"+name).val(parseFloat(dis.toFixed(2)));
     						break;
     					}
     					case 6: {
     						
     						$("#tax"+name).val(item1);
     						break;
     					}
     																	
     					}
     					var Qty=$("#qty"+name).val();
     					if(Qty.length === 0 || Qty === '' || Qty === null){
     						Qty = "0";			
     					}
     					var Pcs=$("#pcs"+name).val();
     					if(Pcs.length === 0 || Pcs === '' || Pcs === null){
     						Pcs = "0";			
     					}
     					var rate=$("#salesrates"+name).val();
     					if(rate.length === 0 || rate === '' || rate === null){
     						rate = "0.00";			
     					}
     					var Discunt=$("#discounts"+name).val();		
     					if(Discunt.length === 0 || Discunt === '' || Discunt === null){
     						Discunt = "0.00";			
     					}
     					
     					var calc =  parseFloat(rate) - parseFloat(Discunt) ;
     					
     					if(calc==0 || calc==null || calc==""){
     						$("#amt"+name).val("0.00");
     					}else{
     						if(calc <0){							
     							$("#amt"+name).val("0.00");
     							}else{
     								$("#amt"+name).val(calc.toFixed(2));
     							}
     					}
     					
     					POSSalesCalcs();
     				});
     			   }
     			}	
     			});	
     	}
     	$(this).css("text-transform","uppercase");
     	});
     }
     
     /******** POS Sales rate keyup Calulation in Rate and which affects  AMount Field******************/
     function POSSalesRateCalcs(){
     	var name = $(this).attr("title");	
     	var Sales_rate=$("#salesrates"+name).val();
     	
     	if(Sales_rate.length === 0 || Sales_rate === '' || Sales_rate === null){
     		Sales_rate = "0.00";			
     	}
     	
     	var discnts=$("#discounts"+name).val();

     	if(discnts.length === 0 || discnts === '' || discnts === null){
     		discnts = "0.00";			
     	}
         
     	var finalAmt=parseFloat(Sales_rate)-parseFloat(discnts);
     	//if(finalAmt<=discnts){
     		//alert("Discount Cannot Be Greater Than Equal To SalesRate!!!");
     		//return false;
     	
     	//}
     	if(Sales_rate ==0){
     		$("#amt"+name).val("0.00");
     	}else
     		{
     		if(finalAmt < 0){	
     			
     			$("#amt"+name).val("0.00");
     			}else{
     				$("#amt"+name).val(finalAmt.toFixed(2));
     			}
     		}
     	
     	POSSalesCalcs();
     	
     }
     
     /***** Discount caluciton for pos Sales 25-7-12*****/
     function discountRateCalc(){
     	
     	var name = $(this).attr("title");
     	var discntRate=$("#discounts"+name).val();
     	
     	if(discntRate.length === 0 || discntRate === '' || discntRate === null){
     		discntRate="0.00";			
     	}
     	var Sales_rate=$("#salesrates"+name).val();
     	if(Sales_rate.length === 0 || Sales_rate === '' || Sales_rate === null){
     		Sales_rate = "0.00";			
     	}
     	
     	var tempcal=parseFloat(Sales_rate)-parseFloat(discntRate);
     	if(tempcal <= 0 ){
     		alert("Discount Cannot Be Greater Than Equal To SalesRate!!!");
     		$("#discounts"+name).val("0.00");
     	//	$("#amt"+name).val("0.00");
     		POSSalesRateCalcs();
     	}else if(tempcal > 0){	
     			$("#amt"+name).val(tempcal.toFixed(2));
     			}
     		
     	POSSalesCalcs();
     	
     	
     }
     
     /***** total Amount Received calculation ******/
     function receiptModeCalc(){
     	
     	
     	var cashamt=0;
     	var chequeAmt = 0;
     	var cardAmt = 0;
     	var voucherAmt = 0;
     	var receidamt=$("#receivedAmts").val();
     	chequeAmt=$("#cheque_Amount").val();
     	cashamt=$("#counter_cash").val();
     	
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
     			
     			if(receidamt.length === 0 || receidamt === '' || receidamt === null){
     				receidamt="0.00";			
     			}
     			
     			if(cashamt.length === 0 || cashamt === '' || cashamt === null){
     				cashamt="0.00";			
     			}
     			
     			
     			var totRecivAmt=parseFloat(chequeAmt)+parseFloat(cardAmt)+parseFloat(voucherAmt)+parseFloat(cashamt);
     			$("#receivedAmts").val(totRecivAmt.toFixed(2));
     			
     			balanceToReceive();
     			CounterCashCalc();
     }

     /**** amount receive calculation *****/
     function AmountModeReceivedCalc(){
     	var cheque_Amt = 0;
     	var card_Amt = 0;
     	var voucher_Amt = 0;
     	var receid_amt=$("#amountreceiv").val();
     	cheque_Amt=$("#cheque_Amount").val();
     	
     	if(cheque_Amt.length === 0 || cheque_Amt === '' || cheque_Amt === null){
     		cheque_Amt="0.00";			
     	}
     		card_Amt=$("#card_Amount").val() ;
     		if(card_Amt.length === 0 || card_Amt === '' || card_Amt === null){
     			card_Amt="0.00";			
     		}
     		voucher_Amt=$("#voucher_Amount").val();
     			
     			if(voucher_Amt.length === 0 || voucher_Amt === '' || voucher_Amt === null){
     				voucher_Amt="0.00";			
     			}
     			
     			if(receid_amt.length === 0 || receid_amt === '' || receid_amt === null){
     				receid_amt="0.00";			
     			}
     			
     			
     			var totReciv_Amt=parseFloat(cheque_Amt)+parseFloat(card_Amt)+parseFloat(voucher_Amt);
     			$("#amountreceiv").val(totReciv_Amt.toFixed(2));
     			balanceToReceive();
     			CounterCashCalc();
     }

   //******Balance to customer  calculation for Receipt mode *******//

     function CounterCashCalc(){
        
     	var countCash=$("#counter_cash").val();
     	var balToReceiv=$("#balReceiv").val();
     	
     	if (countCash === null  || countCash === '' || countCash.length === 0){
     		countCash="0.00";
     	}
     			
     	if (balToReceiv === null  || balToReceiv === '' || balToReceiv.length === 0 ){
     		balToReceiv="0.00";
     	}
     	
     	

     	var finalCashAmount=parseFloat(countCash)-parseFloat(balToReceiv);
     	
     	$("#balToCust").val(finalCashAmount.toFixed(2));
     	
     	
     }
     
     /**** cash calculation *****/
     function cashcaluclation(){
     	
     	 var baltocust=$("#balToCust").val();
     	
     		var countercash=$("#counter_cash").val();

     		if (countercash === null  || countercash === '' || countercash.length === 0 ){
     			countercash="0.00";
     		}
     				
     		if (baltocust === null  || baltocust === '' || baltocust.length === 0 ){
     			baltocust="0.00";
     		}
     		
     		
     		
     		if(baltocust < 0){
     		
     			$("#cash_Amount").val(countercash);
     		
     		}else{
     			
     			var tempcashamount=parseFloat(countercash)-parseFloat(baltocust);
     			
     			
     			$("#cash_Amount").val(tempcashamount.toFixed(2));
     			
     		}
     			
     	
     }
     
     
   //*******POS Sales Sub Total Field Calculation ******* //
     function POSSalesCalcs(){	
     	var sizs = $("[id^=amt]").size();
     		var sum = 0;
     		for(var j=0;j<sizs;j++){
     			
     			var subAmt =  $("#amt"+j).val();
     			
     			if(subAmt.length == 0 || subAmt == '' || subAmt == null){
     				subAmt = 0.00;
     			}
     			sum = parseFloat(sum) + parseFloat(subAmt);
     			
     		}
     			
     		$("#subTotal").val(sum.toFixed(2));
     		POSSTotalTaxCalcs();
     } 	

     //*******POS Sales Total tax Field Calculation ******* //
     function POSSTotalTaxCalcs(){
    	// alert("in return");
     	var taxNum = $("[id^=tax]").size();
     		var taxAmt = 0;
     		for(var i=0;i<taxNum;i++){
     			
     			var taxP = $("#tax"+i).val();
     			if(taxP.length == 0 || taxP == '' || taxP == null){
     				taxP = 0.00;
     			}
     			
     			 var subAmt = $("#amt"+i).val(); 
     			if(subAmt.length == 0 || subAmt == '' || subAmt == null){
     				subAmt = 0.00;
     			}						
     			var t1 = parseFloat(taxP) / 100;
     			var t2 = parseFloat(subAmt) * parseFloat(t1);
     			taxAmt = parseFloat(taxAmt) + parseFloat(t2);		
     		//	alert(taxAmt);
     			$("#totalTax").val(taxAmt.toFixed(2));
     		}
     		
     		POSSalesTotalValueCalc();
     		
     }



     //*******POS Sales Total Value Field Calculation ******* //
     function POSSalesTotalValueCalc(){

     	var subtotal = $("#subTotal").val();
     	if(subtotal == '' || subtotal == null){
     		subtotal = 0.00;
     	}	
     	var totaltaxVal = $("#totalTax").val();
     	if(totaltaxVal == '' || totaltaxVal == null){
     		totaltaxVal = 0.00;
     	}
     	var totalval = parseFloat(subtotal) + parseFloat(totaltaxVal);	
     	$("#totalVal").val(totalval.toFixed(2));
     	
     	var totalvalue = $("#totalVal").val();
     	var beforeRound = totalvalue;
     	var afterRound = parseFloat(beforeRound).toFixed(0.00);
     	var roundedVal = parseFloat(afterRound) - parseFloat(beforeRound);
     	
     	$("#roundOff").val(parseFloat(roundedVal).toFixed(2));
     	$("#grandAmt").val(parseFloat(afterRound).toFixed(2));	
     	receiptModeCalc();
     	cashcaluclation();
     	CounterCashCalc();
     }
     
   //**** Balance to Recive calcualtin for receipt mode *****/
     function balanceToReceive(){
     	var toTamount=$("#grandAmt").val();
     	if(toTamount.length === 0 || toTamount === '' || toTamount === null){
     		toTamount="0.00";			
     	}
     	var receivdAmount=$("#amountreceiv").val();
     	if(receivdAmount.length === 0 || receivdAmount === '' || receivdAmount === null){
     		receivdAmount="0.00";			
     	}
     	var finalReceivdAmount=parseFloat(toTamount)-parseFloat(receivdAmount);
     	$("#balReceiv").val(finalReceivdAmount.toFixed(2));
     	
     }
     /* Dynamic row delete fucntion */
     function POSSalesdel(){
     	
     	$(".del").click(function(){
     		
     		var thisRow = $(".staticRow").length;
     		if(thisRow <= 1){
     			
     		
     		}
     		else if(thisRow > 1){
     			$(this).parents(".staticRow").find("[id^=posdeleted]").val('true');
				$(this).parents(".staticRow").find("[id^=amt]").val("0.00");
				$(this).parents(".staticRow").find("[id^=categoryname]").val("");
				$(this).parents(".staticRow").find("[id^=dIname]").val("");
				$(this).parents(".staticRow").find("[id^=qty]").val("0");
				$(this).parents(".staticRow").find("[id^=pcs]").val("0");
				$(this).parents(".staticRow").find("[id^=salesrates]").val("0.00");
				$(this).parents(".staticRow").find("[id^=discounts]").val("0.00");
				$(this).parents(".staticRow").find("[id^=tax]").val("0.00");
				$(this).parents(".staticRow").find("[id^=positemStatus]").val("");
				$(this).parents(".staticRow").hide();
     			POSSalesCalcs();
     		}
     		
     	});
     }
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
   /*  
function autoCustomerName(){ 
	
	$.ajax({	
		
		type:'GET',
		url:'customerName_Auto.htm',
		data:{sNamePart:$("#poscustomer").val()},
		success:function(data){
			//alert(data);
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
			finalData = finalData.concat("Walk-in");
			finalData = finalData.concat(":");
			var iVal = finalData.split(":");
			$("#poscustomer").autocomplete(iVal);
		},
		error:function(xmlHttpRequest, textStatus, errorThrown){
			if(xmlHttpRequest.readyState=0 || xmlHttpRequest.status == 0)
				return;
		},
	});
	
}*/

function checkIsEmpty(){
	
	$('input.cal_dep').each(function(){
		var TempVal = $(this).val();
		if(TempVal.length === 0 || TempVal === '' || TempVal === null){
			$(this).val("0");			
		}
	});
	
}


function reportConfirmValidation(){
	
	checkIsEmpty();
	//*****  check row empty   *****/
	var dIcodsize = $("[id^=dIcode]").size();
	for(var j=0;j<dIcodsize;j++){
		
		var dIcodVal =  $("#dIcode"+j).val();
		
		if(dIcodVal == '' || dIcodVal == null){
			var naaam = $("#dIcode"+j).attr("title");
			var posDeletd=$("#posdeleted"+naaam).val();
			if(posDeletd=="false"){
				$("#form_error").slideDown("fast");
				$("#form_error span").text("A Row is empty");
				return false;
			}
		}
	}
	//salesRateValidate();
	//*****  check salesrates empty   *****/
	var salesRatesize = $("[id^=salesrates]").size();
	//alert(salesRatesize);
	for(var k=0;k<=salesRatesize;k++){
		
		var $salesRate =  $("#salesrates"+k).val();
		
		if($salesRate == '' || $salesRate == null || $salesRate.length == 0){
			var numbe = $("#salesrates"+k).attr("title");
			var posDeletdFlds = $('#posdeleted'+numbe).val(); 
			
			if(posDeletdFlds=="false"){
				$("#form_error").slideDown("fast");
				$("#form_error span").text("*Rate is Required!");
				return false;
			}
		}
		
	}
	
	
	
	//**************************************************/
	var result = POScheckDate();
		if(result == false){
		$("#form_error").slideDown("fast");
		$("#form_error span").text("*Enter Valid Date");
			return false;
		}
	var cashamt = $("#cash_Amount").val();
	var Posdate=$("#date").val();
	var chqamt = $("#cheque_Amount").val();
	var cardamt = $("#card_Amount").val();
	var voucherd = $("#voucher_Amount").val();
	var bilamt_val=$("#grandAmt").val();
	var custname=$("#poscustomer").val();
	var walkincust=$("#walkincust").val();
	
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
	
	if(bilamt_val.length === 0 || bilamt_val === '' || bilamt_val === null){
		bilamt_val="0.00";			
	}
	
	if (bilamt_val == 0) {
		$("#form_error").slideDown("fast");
		$("#form_error span").text("*Grand Amount cannot be Zero");
		return false;
	}
	
	
	if ($("#cash").is(":checked")) {
		if (cashamt == 0) {
			$("#form_error").slideDown("fast");
			$("#form_error span").text("*Cash Amount Cannot Be Zero");
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
	
	
	//customer name validatoin in update mode
	if(custname == "Select"  || custname.length == 0 || custname == null){
		
		$("#form_error").slideDown("fast");
		$("#form_error span").text("*Customer Name Required");
		return false;
	}
	else{
		if(custname=="Walk-in" ){
			if(walkincust==null || walkincust.length == 0){
		
			$("#form_error").slideDown("fast");
			$("#form_error span").text("*Walk-in Customer Name is Required");
			return false;
		}
			if( $("#grandAmt").val()  < $("#receivedAmts").val() || $("#receivedAmts").val() < $("#grandAmt").val() ){
				$("#form_error").slideDown("fast");
				$("#form_error span").text("*Credit Sales Not Allowed For Walk-in Customer");
				return false;
			}
		}
	}
	
	
	
	
	// date validation in update mode
	if(Posdate == null  || Posdate.length == 0){
		$("#form_error").slideDown("fast");
		$("#form_error span").text("*Date field Can't be Empt");
		return false;
	}
	
	var cashAmount=$("#cash_Amount").val();
	//cash negative validation
	if(cashAmount < 0 ){
			$("#form_error").slideDown("fast");
		$("#form_error span").text("*Incorrect Received amount ");
			return false;
		}
	
	
	if ($("#voucher").is(":checked")) {
		if (voucherd == 0) {
			$("#form_error").slideDown("fast");
			$("#form_error span").text("*Voucher Amount Cannot Be Zero");
			return false;
		}
		
		if ($("#vouchers").val() == 0) {
			$("#form_error").slideDown("fast");
			$("#form_error span").text("*Please Select Voucher Name.");
			return false;
		}
		
	}
	
	
	
		
if($(this).attr("id") == "insert"){
	var test = confirm(" Saving Invoice...  Click OK to view Print preview.");
	$("#printInvoice").val(test);
	
}
	return true;
}


function POScheckDate(){
	var currVal = $("#date").val();
	var regexDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
	var dtArray = currVal.match(regexDatePattern);
	if(dtArray == null){
		return false;
	}
	var dtDay = dtArray[1];
	var dtMonth = dtArray[3];
	var dtYear = dtArray[5];
	if(dtMonth < 1 || dtMonth > 12){
		return false;
	}else if(dtDay < 1 || dtDay > 31){
		return false;
	}else if((dtMonth == 4 || dtMonth ==6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31){
		return false;
	}else if(dtMonth == 2){
		var isLeap = ( dtYear %4 ==0 && (dtYear % 100 != 0 || dtYear % 400 == 0) );
		if(dtDay > 29 || (dtDay == 29 && !isLeap)){
			return false;
		}
	}
	return true;
}

