 $(document).ready(function(){
	 $("#wt").keyup(sum);
	 $("#opGrossweight").keyup(Op_sum);
	 $("#ppq").keyup(sum);
	 $("#spcs").keyup(sum1);
	 $("#swt").keyup(sum1);
	 $("#srpc").keyup(sum1);
	 $("#grossWeight").attr("maxlength","10");
	 $("#vat").attr("maxlength","6");
	 $("#vaPercentage").attr("maxlength","6");
	 $("#qty").attr("maxlength","6");
	 $("#mcAmount").attr("maxlength","10");
	 $("#discount").attr("maxlength","6");
	 $('#qty').keyup(nondecimalvalidation);
	 $('#tot_qty').keyup(nondecimalvalidation); 
	 lotStock();
	 hidingBarcodeInLooseStock();
	 $("#ItemInsert").click(goldloosestockValidation);
	 $("#Itemupdate").click(goldloosestockValidation);
	 $("#cancel").click(setZero);
	 $("#UpdateWithBarcode").click(setZero);
	 $("#Itemupdate").click(setZero);
	 $("#ItemInsert").click(setZero);
	 $("#saveBarcode").click(setZero);
	

	 var WsData ='';
	 $("#wt").focusin(function(){
		 WsData = setInterval(WeightScaleData, 2000);
	 });
	
	  $("#wt").focusout(function(){
		  clearInterval(WsData);
	  });
	 
	 //$("#wt").focus(WeightScaleData);
 });
 
 function WeightScaleData()
 {
	 resWSData = 0;
	 $.ajax({
		type:'POST',
		url:'WeightScale.htm',
		success : function(data) {
					
			var resWSData = parseFloat(data);
			
			if(resWSData.toString()!= 'NaN'){
				$("#wt").val(resWSData);
				$("#f1").val(resWSData);
				$("#netweight").val(resWSData);
			}
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {			
				if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
					return;
		},	
	});
 }
 
 function grwtValidate(){
	 if($("wt").val()=='NaN'){
	 		alert('Incorrect GrossWt Value');
	 		return false;
	 	}
 }
 
 
 function sum()
 {
	 var wt=$("#wt").val();
	 var grossWt = $("#wt").val();
	 var ppq = $("#ppq").val();
	 var piecesPerQty =  $("#ppq").val();
	 var qty = $("#quantity").val();
	 wt =	 checkNull(wt);
	 grossWt =	 checkNull(grossWt);
	 ppq =	 checkNull(ppq);
	 piecesPerQty =	 checkNull(piecesPerQty);
	 qty =	 checkNull(qty);
	 
	 if(wt==null || wt.length==0){ 
			$("#wt").val(0);
	 }
	 if(ppq==null || ppq.length==0){ 
		 $("#ppq").val(0);
	 }
	
	 if(qty==null || qty.length==0){ 
		 $("#quantity").val(0);
	 }
	
	
	 var totgwt = parseFloat(grossWt);
	 $("#f1").val(totgwt.toFixed(3));
	 $("#netweight").val(grossWt);
	 var totpieces = parseFloat(ppq);
	 $("#tp").val(totpieces);
 }
 /***
  * This function is for Opening Gross Weigh calculation and amt .
  * This function will only work in viewItemmaster.jsp page 
  * ***/
 function Op_sum()
 {
	 var wt=$("#opGrossweight").val();
	 var grossWt = $("#opGrossweight").val();
	 var ppq = $("#ppq").val();
	 var piecesPerQty =  $("#ppq").val();
	 var qty = $("#op_Quantity").val();
	 wt =	 checkNull(wt);
	 grossWt =	 checkNull(grossWt);
	 ppq =	 checkNull(ppq);
	 piecesPerQty =	 checkNull(piecesPerQty);
	 qty =	 checkNull(qty);
	 
	 if(wt==null || wt.length==0){ 
		 $("#opGrossweight").val(0);
	 }
	 if(ppq==null || ppq.length==0){ 
		 $("#ppq").val(0);
	 }
	 
	 if(qty==null || qty.length==0){ 
		 $("#op_Quantity").val(0);
	 }
	 
	 
	 var totgwt = parseFloat(grossWt);
	 $("#f1").val(totgwt.toFixed(3));
	 $("#op_NetWeight").val(grossWt);
	 var totpieces = parseFloat(qty) * parseFloat(ppq);
	 $("#tp").val(parseFloat(totpieces));
 }
 
 
 
 $('#qty').keyup(nondecimalvalidation);
 $('#tot_qty').keyup(nondecimalvalidation); 
	
function nondecimalvalidation() {
		$(this).val(function(index, oldVal) {
			return oldVal.replace(/[^\d-]/g, '');
		});
	}
 
 
 function sum1()
 {
	 if($("#spcs").val()==null || $("#spcs").val().length==0){ 
		 $("#spcs").val(0);
	 }
	 var StoneinPcs = $("#spcs").val();
	 if( $("#swt").val()==null ||  $("#swt").val().length==0){ 
		 $("#swt").val(0);
	 }
	 var StoneWeightgms = $("#swt").val();
	 if( $("#srpc").val()==null ||  $("#srpc").val().length==0){ 
		 $("#srpc").val(0);
	 }
	 var stoneRate = $("#srpc").val();
	 
	 var StoneCost=0.00;
	 
	 // Stone Weight Grams
	 if (StoneWeightgms<=0) 
	 {
		 $("#swt").val(0);
		 StoneCost=StoneinPcs*stoneRate;
 	 }else if (StoneWeightgms > 0)
	  {
		$("#spcs").val(0) ;
		StoneCost=StoneWeightgms*5*stoneRate;
	  }
	 
	 // Stone Weight Pieces
	 if (StoneinPcs<=0)
	 {	
	 	$("#spcs").val(0);
	 	StoneCost=StoneWeightgms*5*stoneRate;
	 }else if (StoneinPcs > 0)
	  {
		$("#swt").val(0);
		StoneCost=StoneinPcs*stoneRate;  
	  }
	 $("#f2").val(StoneCost.toFixed(2));
 }
 /**************lot item creation************/
 $("#lotitem").click(function() {
	 
	  $('.lotitemstock option').each(function() {
		    if ( $(this).val()) {
		        $(this).remove();
		    }
		   
		});
	  var metalType=$('#bullionTypes').val();
	 if(metalType=='Gold')
		 {
		 $("#stock_type").append('<option value=GoldLotStock>GoldLotStock</option>'); 
		 }
	 else
		 {
		 $("#stock_type").append('<option value=SilverLotStock>SilverLotStock</option>');
		 }
	});
 
 /******************lot stock creation dynamic validation value reset********************/
 function lotStock()
 {
	 var errorType=$('#errorType').val();
	 var stockType=$('#hiddenstockType').val();
	 if(errorType=='insertError' && stockType=='GoldLotStock' || stockType=='SilverLotStock')
	 	{
		 $('.lotitemstock option').each(function() {
			    if ( $(this).val()) {
			        $(this).remove();
			    }
			   
			});
		  var metalType=$('#bullionTypes').val();
			 if(metalType=='Gold')
				 {
				 $("#stock_type").append('<option value=GoldLotStock>GoldLotStock</option>'); 
				 }
			 else
				 {
				 $("#stock_type").append('<option value=SilverLotStock>SilverLotStock</option>');
				 }
	 	}

 }
 function hidingBarcodeInLooseStock(){
	 //$(".itemquantity").hide();
	 var bulliontp=$("#metalGroups").val();
		if(bulliontp=="Gold Loose Stock" || bulliontp=="Silver Loose Stock"){
			//$("#saveBarcode").hide();
			//$("#UpdateWithBarcode").hide();
			$(".itemQty").show();
			$(".opGrossWeight").show();
			$(".opQty").show();
			$(".clGrossWeight").hide();
			$(".clQty").hide();
			$(".ppq").hide();
			$("#ppq").val(1);
 }else{
	 $(".itemQty").hide();
	 $(".opGrossWeight").hide();
		$(".opQty").hide();
		$(".ppq").show();
 }
 }
 function goldloosestockValidation(){
	 	if($("wt").val()=='NaN'){
	 		alert('Incorrect GrossWt Value');
	 		return false;
	 	}
		var bullion=$("#metalGroups").val();
		var stockType=$("#stock_type").val();
		
		if(stockType=="PurchasedStock" && bullion=="Gold Loose Stock" ){
			alert("Purchase Stocked Is not Allowed For Loose Stock !");
			return false;
		}
		if(stockType=="PurchasedStock" && bullion=="Silver Loose Stock" ){
			alert("Purchase Stocked Is not Allowed For Loose Stock !");
			return false;
		}
		if(stockType=="GoldLotStock" && bullion=="Gold Loose Stock" ){
			alert("Loose stock cannot create from Lot Items !");
			return false;
		}
		if(stockType=="SilverLotStock" && bullion=="Silver Loose Stock" ){
			alert("Loose stock cannot create from Lot Items!");
			return false;
		}
		var itemquantity=$("#itemquantity").val();
		if(itemquantity==null || itemquantity==0){
			alert("Qty Cannot be Empty In ItemForm!");
			return false;
		}
 }
 function setZero(){
	 
	 	if($("wt").val()=='NaN'){
	 		alert('Incorrect GrossWt Value');
	 		return false;
	 	}
	 
			var itemquantity=$("#itemquantity").val();
		if(itemquantity==null ||qty==""){
			itemquantity=$("#itemquantity").val(0);
		}
		var wt=$("#wt").val();
		if(wt==null ||wt==""){
			wt=$("#wt").val(0);
		}
		var ppq=$("#ppq").val();
		if(ppq==null ||ppq==""){
			ppq=$("#ppq").val(0);
		}
 }