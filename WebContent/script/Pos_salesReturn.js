  $(document).ready(function(){
	  
	  POSSalesReturnDesign();
	  POSSalesCalcs();//POS Sales Sub Total Field Calculation
	  
	  	$("[id^=salesrates]").keyup(POSSalesRateCalcs);
		$("[id^=discounts]").keyup(discountRateCalc);
		$('#poscustomer').focusout(PosCustomerName).trigger('focusout');
		
		$("#update").click(function(){
			if($("#date").val()==""){
				$("#form_error").slideDown("fast");
				$("#form_error span").text(" * Date Cannot be Emtpy.");
				return false;
			}
		});
	});
  
  /***  PosSalesReturn List design *****/
  function POSSalesReturnDesign(){
  	
  	//$(".salesreturn_return").hide();
  	//var check = $(".salesreturn_checkbox").is(":checked");
  	
  	if($(".salesreturn_checkbox").is("checked", "checked")){
  		$(".salesreturn_return").hide();
  	}	
  	else{
  		$(".salesreturn_return").show();
  	}
  }
  
  /******** POS Sales Return rate keyup Calulation in Rate and which affects  AMount Field******************/
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
  
  /***** Discount caluciton for pos Sales return 25-7-12*****/
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
  		//$("#amt"+name).val("0.00");
  		
  	}else if(tempcal > 0){	
  			$("#amt"+name).val(tempcal.toFixed(2));
  			}
  		
  	POSSalesCalcs();
  	
  	
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
  	
  }
  
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
  			$("#walkinName0").focus();
  		}
  }