 $(document).ready(function(){
	 //date picker 
	 $("#rrdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});	
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
		
		 var URIloc = $(location).attr('href');
			var uripath = URIloc.split('/')[4];
			var errorType = $("#errorType").val();

			if(uripath.indexOf("FormRefineryReceipt.htm") >= 0){
				$("#RRInsert").show();
				$("#RRUpdate").hide();
				$("#rrdate").val(prettyDate);
			}
			if(uripath.indexOf("viewRefineryReceipt.htm") >= 0){
				$("#RRInsert").hide();
				$("#RRUpdate").show();
				$("#rrdate").val(prettyDate);
				$('#rrornamentsType').attr("disabled", true);
			}

			if(errorType == "insertError") {
				$("#RRInsert").show();
				$("#RRUpdate").hide();
				$("#rrdate").val(prettyDate);
			}else if(errorType == "updateError") {
				$("#RRInsert").hide();
				$("#RRUpdate").show();
			}
			
			$('#rrornamentsType').change(function() {
			    var values = $('#rrornamentsType').val();
			    if (values == 'Gold Bullion') {
			        $('#rritemcode').val('IT100002');
			    }
				if (values == 'Silver Bullion') {
			        $('#rritemcode').val('IT100007');
			    }
			    return false;
			});
			
			
			$("#RRGrossWeight").keyup(autoNetweight);
			$("#RRInsert").click(validation);
			$("#RRUpdate").click(validation);
 });
 function autoNetweight(){
	 var grosswgt=$("#RRGrossWeight").val();
	 if (grosswgt === null  || grosswgt === '' || grosswgt.length === 0 ){
		 grosswgt="0.00";
		}
	 $("#RRNetWeight").val(grosswgt);
 }
 
 function validation(){
	 checkIsEmpty();
	 $('#rrornamentsType').attr("disabled",false);
 }
 function checkIsEmpty(){
		
		$('input.cal_dep').each(function(){
			var TempVal = $(this).val();
			if(TempVal.length === 0 || TempVal === '' || TempVal === null){
				$(this).val("0");			
			}
		});
		
	}
