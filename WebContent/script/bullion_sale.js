////////////////////////////////////////Bullion sales client side auto calculations//////////////////////////////////
/**Ajax call function dnt change this  */
 $(document).ready(function(){
		
	 $("#brate").keyup(sum);
	 $("#roundOffValue").keyup(sum);
	 $("#grwt").keyup(sum);
	 $("#billamount").keyup(Revers_Cal);	 
	 $("#customerName01").change(ajaxPanNumbers); 
	 $('#sid').change(getJournalDebitAmt);
	 $('#sid').click(getJournalDebitAmt);
	 $("#amtReceived").keyup(balance_to_advanceOrder);	
	 $("#sid").click(balance_to_advanceOrder);	
	 $("#insert").click(checkIsEmpty);
	$("#update").click(checkIsEmpty); 
	$("#item_code").change(ajaxbullionvat);
});
 
 function checkIsEmpty(){
		
		$('input.cal_dep').each(function(){
			var TempVal = $(this).val();
			if(TempVal.length === 0 || TempVal === '' || TempVal === null){
				$(this).val("0");			
			}
		});
	}
function sum()
{
	var grosswt = $("#grwt").val(); //Getting gross weight field value
 	var bullion_rate = $("#brate").val();//Getting rate field value
 	var tax_val = $("#vat").val();
 	
 	if( grosswt === 'NaN' || grosswt === null|| grosswt === '' || grosswt.length === 0 ){
 		grosswt="0";			
 	}
 	if( bullion_rate === 'NaN' || bullion_rate === null|| bullion_rate === '' || bullion_rate.length === 0 ){
 		bullion_rate="0";			
 	}
 	var totalBillAmt=grosswt*bullion_rate;
 	var tax=totalBillAmt *(tax_val/100); 
 	var billamount= parseFloat(totalBillAmt.toFixed(2))+ parseFloat(tax.toFixed(2));
 	var beforeRound = billamount.toFixed(2);
	var afterRound = billamount.toFixed(0);
	if( afterRound === 'NaN' || afterRound === null|| afterRound === '' || afterRound.length === 0 ){
		afterRound="0";			
	}
	$("#balAmt").val(afterRound);
 	
 	
 	
	var	roundedValue = afterRound - beforeRound;
	$("#amountAfterLess").val(totalBillAmt.toFixed(2));
 	$("#netamount").val(totalBillAmt.toFixed(2));
 	$("#tax").val(tax.toFixed(2));
 	$("#roundOffValue").val(roundedValue.toFixed(2));
 	
 	$("#billamount").val(afterRound);
 	
 }
/*
function calc_roundOffAmt() {
	var zer = 0;
	var subAmount = $("#subtotal").val();
	if(subAmount == null || subAmount.length == 0 || subAmount == 'NaN'){
		subAmount = 0.00;
	}
	var taxAmount = $("#tax").val();
	if(taxAmount == null || taxAmount.length == 0 || taxAmount == 'NaN'){
		taxAmount = 0.00;
	}
	var roundAmt = $("#roundOff").val();
	if(roundAmt == null || roundAmt.length == 0 || roundAmt == 'NaN' || roundAmt == '-'){
		roundAmt = 0.00;
	}
	var Amount = parseFloat(subAmount) + parseFloat(taxAmount) + parseFloat(roundAmt);
	$("#billamount").val(Amount.toFixed(2));
	balance_to_pay();
}*/
function balance_to_advanceOrder(){
	//alert(":::::::");
	var amtRecpt=$("#amtReceived").val();
    var balAmt = $("#billamount").val();
	var recept =   $("#receipt_Amount").val();
	
	if( balAmt === 'NaN' || balAmt === null|| balAmt === '' || balAmt.length === 0 ){
		balAmt=0;			
	}
	if( amtRecpt === 'NaN' || amtRecpt === null|| amtRecpt === '' || amtRecpt.length === 0 ){
		amtRecpt=0;			
	} 
	
	if(recept === 'NaN' || recept === null|| recept === '' || recept.length === 0 ){
		recept=0;			
	}
	
	var bal=parseFloat(balAmt)-parseFloat(amtRecpt)-parseFloat(recept);
	$("#balAmt").val(bal.toFixed(2));  
}


function Revers_Cal()
{
	var billamount_r=$("#billamount").val();
	
	if( billamount_r === 'NaN' || billamount_r === null|| billamount_r === '' || billamount_r.length === 0 ){
		billamount_r = 0;			
	}
	var grosswt_r = $("#grwt").val();
	if( grosswt_r === 'NaN' || grosswt_r === null|| grosswt_r === '' || grosswt_r.length === 0 ){
		grosswt_r=0;			
	}
	var netAmount = $("#netamount").val();
	if( netAmount === 'NaN' || netAmount === null|| netAmount === '' || netAmount.length === 0 ){
		netAmount=0;			
	}
	
	var tax_r = $("#vat").val();
	var net_Amount = parseFloat(billamount_r) / (1 + (tax_r * 0.01));
	
	
	var	tax_rate=parseFloat(net_Amount) * tax_r * 0.01;
	var bullion_rate=$("#brate").val();
	var bull_rate=parseFloat(net_Amount)/parseFloat(grosswt_r);
	var Amountafterless=$("#amountAfterLess").val();
	
	
	if( tax_rate === 'NaN' || tax_rate === null|| tax_rate === '' || tax_rate.length === 0 ){
		tax_rate=0;			
	}
	 		
	if( bullion_rate === 'NaN' || bullion_rate === null|| bullion_rate === '' || bullion_rate.length === 0 ){
	 		bullion_rate=0;			
	 }
		
	if( Amountafterless=== 'NaN' || Amountafterless === null|| Amountafterless === '' || Amountafterless.length === 0 ){
		Amountafterless=0;			
	}
	
	
 		$("#amountAfterLess").val(net_Amount.toFixed(2));
 	 	$("#netamount").val(net_Amount.toFixed(2));
 	 	
 	 	$("#tax").val(tax_rate.toFixed(2));
 	 	
 	 	$("#brate").val(bull_rate.toFixed(2));
 	 	
 	 	if(bull_rate == 'NaN'|| bull_rate == 'Infinity' ){
 	 		
 	 		var ZERO=0;
 	 		$("#brate").val(ZERO.toFixed(2));
 	 	}
 	 	//RoundOff
 	 	var billamount= parseFloat(net_Amount)+ parseFloat(tax_rate);
 	 	var beforeRound = billamount.toFixed(2);
 		var afterRound = billamount.toFixed(0);
 		var	roundedValue = afterRound - beforeRound;
 		$("#roundOffValue").val(roundedValue.toFixed(2));
 		//balance to pay
 		if( afterRound === 'NaN' || afterRound === null|| afterRound === '' || afterRound.length === 0 ){
 			afterRound="0";			
 		}
 		$("#balAmt").val(afterRound);
}


function billno(){
	var billamt = $("#billamount").val();
	var customerName01 = $("#customerName01").val();
	if(billamt == 0 || customerName01 =='walk_in'){	
		$("#sid").attr('disabled', true);		
	}
	else{
		$("#sid").removeAttr('disabled');		
	} 
}

/** Ajax code for receipt */
function getAjaxReceipt(){
	
	var custname=$("#customerName01").val();
	if(custname!="walk_in"){
		$.ajax({
			type:'GET',
			url:'creditAccount_name.htm',
			data:{credit:custname},
			success:function(data){	
				
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
		              $("#sid").get(0).options[index] = new Option(result);		               
					}				
				});
					$('select option:empty').remove();		
					
					},
					
			error:function(xmlHttpRequest, textStatus, errorThrown){
				if(xmlHttpRequest.readyState=0 || xmlHttpRequest.status == 0)
					return;
				},
		});
	}
}
	/** Ajax get DebitAMount to server formBullionSalesCOntroller*/
	function getJournalDebitAmt(){
	
		var id=$("#sid").val();
		   
	    $.ajax({
	    	  type:'GET',
	        url: 'JournalId_amt.htm',
	        data: {creditAmt : id},
	        success: function(data) {
	      $("#receipt_Amount").val(data);
	  
		    
	      
	   //  alert(data);
	        
	          balance_to_advanceOrder();          
	        },
	        error:function (xmlHttpRequest, textStatus, errorThrown) {
	            if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0) 
	                return;  // it's not really an error
	           },
	     });
	    if(id=="Select"){
	    	  $("#receipt_Amount").val(0);
	    } 
	   }
/** Ajax get Pan Number code to server FormBullionSalesController */
	function ajaxPanNumbers(){
	
		var custName = $("#customerName01").val();	
		
		if(custName != "walk_in")
		{
			$.ajax({
				type:'GET',
				url:'tin_Number.htm',
				data:{num:custName},
				success:function(data){					
					$("#fpan_Number").val(data);
					
				},
				error:function(xmlHttpRequest, textStatus, errorThrown){
					if(xmlHttpRequest.readyState=0 || xmlHttpRequest.status == 0)
						return;
				},
			});
		}else
			{
			$("#fpan_Number").val('');
			}
	}
	
	
	/** Ajax get Vat Tax to server FormBullionSalesController */
	function ajaxbullionvat(){

		var itemcode = $("#item_code").val();	
			
			$.ajax({
				type:'GET',
				url:'vat_amount.htm',
				data:{ItemCode:itemcode},
				success:function(data){				
				$("#vat").val(data);	
				sum();
				},
				error:function(xmlHttpRequest, textStatus, errorThrown){
					if(xmlHttpRequest.readyState=0 || xmlHttpRequest.status == 0)
						return;
				},
			});		
	}