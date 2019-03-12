$(document).ready(function(){
		
	/*$(".printStatus").each(function(){
		var printStatus = $(this).val();
		if(printStatus == "Unprinted"){
			$(this).parents("tr").find("td:eq(7)").text("Untagged");
		}
		if(printStatus == "printed"){
			$(this).parents("tr").find("td:eq(7)").text("PrintTagged");
		}
	});*/
	
	$(".list_table").advancedtable({searchField: "#listsearch, #listsearch2", loadElement: "#loader", searchCaseSensitive: false, ascImage: "images/up.png", descImage: "images/down.png"});
	//$("#listsearch").val("PrintTagged").triggerHandler("keyup");
	
	$('#printedtab ul li.linkss a').click(function(){
		
		var PrintedBttn = $(this).attr("title");
		
		$("#listsearch2").val(PrintedBttn).triggerHandler("keyup");
		
		/*if(PrintedBttn == "Printed"){
			//alert(printStatus);
			//$(this).addClass("Print");
			//alert($(this).attr("class"));
			
			$(".list_table2 tr .printStatus").each(function(){
				var printStatus = $(this).val();
				if(printStatus == "Unprinted" || printStatus == "Sold"){
					$(this).parents("tr").remove();
				}
			});
			
		}
		else if(PrintedBttn == "Unprinted"){
		//	alert("Unprinted");
		}*/
	});
	
	//Existing to print barcode in Barcode Printer
	/** $(".p_button").click(function(){
		
		var checkBoxes = $('.print');
		
        $.each(checkBoxes, function() {
            if ($(this).attr('checked')){
            var itemcode=$(this).attr("id");
           $.ajax({
        	type:'POST',
        	url:'postagbarcode.htm',
        	data:{posicode:itemcode},
        	success:function(data){
        		}
        		});
        	$(this).attr('checked',false);
        	}
            
            });
        window.location.reload();
       
		});**/
	
	$("#checkall").click(function()			
	{
		var checked_status = this.checked;
		$(".print").each(function()
		{
			this.checked = checked_status;
		});
	});	
	 	/*$('.catTabclass').hide();
		$('.catTabclass:first').show();
		$('#Printedtab ul li.linkss a:first').addClass('activetabs');
		 
		$('#printedtab ul li.linkss a').click(function(){
			$('#printedtab ul li.linkss a').removeClass('activetabs');
			$(this).addClass('activetabs');
			var currentTab = $(this).attr('href');
			$('#printedtab div').hide();
			$(currentTab).show();
			return false;
		});*/
		
	 $("#txtitemCodes").hide();
});


/** For Printing Bar codes in Multiple Formats **/
function sendBarcodes(){
	$("#txtitemCodes").val("");
	
	var checkBoxes = $('.print');
	var itemArray = new Array();
    $.each(checkBoxes, function() 
    {
    	if ($(this).attr('checked'))
    	{
    		itemArray.push($(this).attr("title"));
    		$(this).attr('checked',false);
    	}
   });

   $("#txtitemCodes").val(itemArray);
   if(itemArray==null || itemArray.length ==0){
	   alert("Please Check atleast one Barcode");
	   return false;
   }
   return true;
   
 /*  $.ajax({
	   type:'POST',
	   url:'posTagLaser.htm',
	   data:{itemArray:itemArray.toString()},
	   success:function(data)
	   {
		   if(data != null){
			   window.open('posBarcode.htm?itemArray='+data,'','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no');
		   }
	   }
   });*/
}	
