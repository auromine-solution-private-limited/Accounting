 $(document).ready(function(){
	 //date picker 
	 $("#date").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});	
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
		
		 var URIloc = $(location).attr('href');
			var uripath = URIloc.split('/')[4];
			var errorTypeRi = $("#errorTypeRi").val();

			if(uripath.indexOf("formRefinery.htm") >= 0){
				$("#RefineryInsert").show();
				$("#RefineryUpdate").hide();
				$("#date").val(prettyDate);
			}
			if(uripath.indexOf("viewRefinery.htm") >= 0){
				$("#RefineryInsert").hide();
				$("#RefineryUpdate").show();
				$("#date").val(prettyDate);
				$('#ornamentsType').attr("disabled", true);
			}

			if(errorTypeRi == "insertError") {
				$("#RefineryInsert").show();
				$("#RefineryUpdate").hide();
				$("#date").val(prettyDate);
			}else if(errorTypeRi == "updateError") {
				$("#RefineryInsert").hide();
				$("#RefineryUpdate").show();
			}
			
			$('#ornamentsType').change(function() {
			    var values = $('#ornamentsType').val();
			    if (values == 'Old Gold Ornaments') {
			        $('#riCode').val('IT100005');
			    }
				if (values == 'Old Silver Ornaments') {
			        $('#riCode').val('IT100010');
			    }
			    return false;
			});
			
			
			$("#grossWeight").keyup(autoNetweight);
			$("#RefineryInsert").click(validation);
			$("#RefineryUpdate").click(validation);
 });
 function autoNetweight(){
	 var grosswgt=$("#grossWeight").val();
	 if (grosswgt === null  || grosswgt === '' || grosswgt.length === 0 ){
		 grosswgt="0.00";
		}
	 $("#RInetWeight").val(grosswgt);
 }
 
 function validation(){
	 checkIsEmpty();
	 $('#ornamentsType').attr("disabled",false);
 }
 function checkIsEmpty(){
		
		$('input.cal_dep').each(function(){
			var TempVal = $(this).val();
			if(TempVal.length === 0 || TempVal === '' || TempVal === null){
				$(this).val("0");			
			}
		});
		
	}