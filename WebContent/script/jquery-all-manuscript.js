/********  All list odd and even styles  ********/
$(document).ready(function(){
	//accountsQuikLinks();
	listSearchinput();
	 CatListZero();
	 lotStockLink();
	//$(".stripeMe tr").mouseover(function(){$(this).addClass("over");}).mouseout(function(){$(this).removeClass("over");});
	   $(".list_table tr:even, .list_table2 tr:even").addClass("row_2");
	   $(".list_table tr:odd, .list_table2 tr:odd").addClass("row_1");
	   $("#insert").val("Save");
	  
	   
	   $("#customerName01").change(bullionisales_cus).trigger('change');
	   $("#orderNo").attr("maxlength","6");//salesOrderId Mximum number validation
	   homepagePendings();
	   sales_form_rowhide();
	   popup_window();
	   categorylistarr();
	   error_msg();
	   mainmenu();
	   cmpanyInformation();
	  
	  $("#form1").submit(function(){
		 
		  //$(".loading").show();
	        
		  popup_window_POSCAT();
		 
		  
	  });
	   /******* Saving Scheme  *******/
	   $(".SchemeGms").hide();
	   $("#SchemeName").change(SchemeAmtGms).trigger("change");
	   $("#SchemeTypeSav").change(SchemeAmtGms).trigger("change");
	   
	// radio_ReceiptShow();
	   $(".radio_ReceiptShow").hide();
		$('#journalType').change(radio_ReceiptShow).trigger('change');		
		$("input").focus(ReadonlyFocus);		
		$('#supplierName0').focusout(PosSupplier).trigger('focusout');
				

	//	jeweltypeTabs();//jeweltype tabs	
		MaualBillingTabs();
		
		
		$('.noEnterSubmit').keypress(function(e){			
			if ( e.which == 13 ) e.preventDefault();
		});

		//******/
		salesReturnJs();
		
		PurchaseSelect();
		category_link();
	
		
		//var value = $('.exchangepending span, #HeaderExchange .exchangepending span').text();
		$("#homepage .exchangepending span").html(function(i, oldVal) {
		    var value = oldVal.split("/");
		    return "<span class='euros'>" + value[0] +"/"+ 
		        "<a class='spandd'>" + value[1].slice(0, -1) + "m</a>" + "</span>";
		});
		$(".HeaderExchange .exchange_pending span").html(function(i, oldVal) {
		    var value = oldVal.split("/");
		    return "<span class='euros'>" + value[0] +"/"+ 
		        "<a class='spandd'>" + value[1].slice(0, -1) + "m</a>" + "</span>";
		});
		
		var URIloc_ledg = $(location).attr('href');
		var uripath_ledg = URIloc_ledg.split('/')[4];
		if(uripath_ledg.indexOf("accountsledger.htm") >= 0) {						
			$(".list_table .row_head th:nth-child(2)").text("Ledger Id");		
		}
		if(uripath_ledg.indexOf("accountsledger1.htm") >= 0) {						
			$(".list_table .row_head th:nth-child(2)").text("Customer Id");		
		}
		if(uripath_ledg.indexOf("accountsledger2.htm") >= 0) {						
			$(".list_table .row_head th:nth-child(2)").text("Supplier Id");	
			
		}
});

/********************            menu            **************************/
function mainmenu() {
	
    var menu = $("#menu ul li.li, #menu ul li.last");
	$(menu).click(mouseovershow);
	$(menu).click(mouseover_add);
	//$('#menu ul li.a_li ul.submenu li.list a.a_first, a.a, a.a_last').mouseout(mouseoverhide);
	$(menu).hover(mouseoverhide);
	$(menu,"a.a_menu").mouseover(mouseover_add);
	$(menu,"a.a_menu").mouseout(mouseover_rem);

}
function mouseovershow() {
   //show its submenu
   $('ul.submenu', this).slideDown(200);
}
function mouseoverhide() {
   //show its submenu
   $('ul.submenu').slideUp(500);
}
function mouseover_add() {
	//$(this).children('a_li_hover').stop(true, true).fadeIn('1000');
	$("a.a_menu", this).addClass('a_li_hover').fadeIn(600);

	//$("a.a_menu", this).fadeIn(100).addClass('a_li_hover');
	
}
function mouseover_rem() {
	$("a.a_menu", this).removeClass('a_li_hover');
}
function menuclick() {
	//$(this).children('a_li_hover').stop(true, true).fadeIn('1000');
	$("a.a_menu", this).addClass('a_li_hover');

	//$("a.a_menu", this).fadeIn(100).addClass('a_li_hover');
	
}
/*********************************************************************/
/********************            purchase form            *************************/
function PurchaseSelect() {
	$(".form_disable").hide();
	$('#bullion1').change(changebullion1).trigger('change');
}

function changebullion1() {
    var values = $('#bullion1').val();
    if(values == "Gold Ornaments"){
         $(".mytext").hide();
		$(".mytext1").show();
		$(".mytext2").show();
		$(".purTosal1").hide();
		$(".purTosal").hide();
		$(".buttons_col").show();
		$(".always_show").show();
		$(".purMc").show();
		 
    }
    else if(values == "Silver Ornaments"){
         $(".mytext").hide();
		$(".mytext1").show();
		$(".mytext2").show();
		$(".purTosal1").hide();
		$(".purTosal").hide();
		$(".buttons_col").show();
		$(".always_show").show();
		$(".purMc").show();
    }
    else if(values == "Gold Bullion"){
          $(".mytext1").hide();
		  $(".mytext").hide();
		  $(".mytext2").show();
		  $(".purTosal1").hide();
		  $(".purTosal").show();
		  $(".buttons_col").show();
		  $(".always_show").show();
		  $(".purMc").hide();
    }
    else if(values == "Silver Bullion"){
          $(".mytext1").hide();
		  $(".mytext").hide();
		  $(".mytext2").show();
		  $(".purTosal1").hide();
		  $(".purTosal").show();
		  $(".buttons_col").show();
		  $(".always_show").show();
		  $(".purMc").hide();
    }
	else if(values == "Gold Exchange" || values == 'Old Gold Pure Bullion' || values == 'Old Gold Coin'){
          $(".mytext1").hide();
          $(".mytext2").hide();
		  $(".mytext").show();
		  $(".purTosal1").show();
		  $(".purTosal").hide();
		  $(".buttons_col").show();
		  $(".always_show").show();
		  $(".purMc").hide();
    }
    else if(values == "Silver Exchange" || values == 'Old Silver Pure Bullion' || values == 'Old Silver Coin'){
          $(".mytext1").hide();
          $(".mytext2").hide();
		  $(".mytext").show();
		  $(".purTosal1").show();
		  $(".purTosal").hide();
		  $(".buttons_col").show();
		  $(".always_show").show();
		  $(".purMc").hide();
    }
       
    return false;
}

/**loose metal stock creation anchor tag enable**/

function category_link(){
	
	$("#category_link tr:nth-child(n) td:nth-child(2)").addClass("categoryName");
	//.html("<a class='add_new'>Add Stock</a>")
	var CatRow2 = $("#category_link tr:nth-child(2) td:nth-child(5)").html();
	var  CatRow11= $("#category_link tr:nth-child(11) td:nth-child(5)").html();
	var  CatRow12= $("#category_link tr:nth-child(12) td:nth-child(5)").html();
	var  CatRow7= $("#category_link tr:nth-child(7) td:nth-child(5)").html();
	var  CatRow5= $("#category_link tr:nth-child(5) td:nth-child(5)").html();
	var  CatRow10= $("#category_link tr:nth-child(10) td:nth-child(5)").html();
	
	   var  row11= $("#category_link tr:nth-child(11) td:nth-child(3)");
	   var  row12= $("#category_link tr:nth-child(12) td:nth-child(3)");
	   var  row2= $("#category_link tr:nth-child(2) td:nth-child(3)");
	   var  row7= $("#category_link tr:nth-child(7) td:nth-child(3)");
	   var  row5= $("#category_link tr:nth-child(5) td:nth-child(3)");
	   var  row10= $("#category_link tr:nth-child(10) td:nth-child(3)");
	   
	// Gold Bullion
	if(CatRow2 == 0.000){ 
		$(row2).html("<a class='add_new'>Add Stock</a>");
	}
	else{
		$(row2).html("<a class='edit'>Update Stock</a>");
	}
	
	// Gold Bullion
	if(CatRow11 == 0.000){ 
		$(row11).html("<a class='add_new'>Add Stock</a>");
	}
	else{
		$(row11).html("<a class='edit'>Update Stock</a>");
	}
	// Gold Bullion
	if(CatRow12 == 0.000){ 
		$(row12).html("<a class='add_new'>Add Stock</a>");
	}
	else{
		$(row12).html("<a class='edit'>Update Stock</a>");
	}
	// Gold Bullion
	if(CatRow7 == 0.000){ 
		$(row7).html("<a class='add_new'>Add Stock</a>");
	}
	else{
		$(row7).html("<a class='edit'>Update Stock</a>");
	}
	// Gold Bullion
	if(CatRow5 == 0.000){ 
		$(row5).html("<a class='add_new'>Add Stock</a>");
	}
	else{
		$(row5).html("<a class='edit'>Update Stock</a>");
	}
	// Gold Bullion
	if(CatRow10 == 0.000){ 
		$(row10).html("<a class='add_new'>Add Stock</a>");
	}
	else{
		$(row10).html("<a class='edit'>Update Stock</a>");
	}
	   
	   
	   $(row12).click(function(){
		   window.location.href = 'losemetalUpdate.htm?iId=12&cId=12';
		    return false;		  
	   });
	   
	   $(row11).click(function(){
		   window.location.href = 'losemetalUpdate.htm?iId=11&cId=11';
		    return false;
		  
	   });
	   $(row2).click(function(){ //gold bullion
		   window.location.href = 'bullionexchangestock.htm?iId=2&cId=2';
		    return false;		  
	   });
	   
	   $(row7).click(function(){ //silver bullion
		   window.location.href = 'bullionexchangestock.htm?iId=7&cId=7';
		    return false;
		  
	   });
	   $(row5).click(function(){ //gold exchange
		   window.location.href = 'bullionexchangestock.htm?iId=5&cId=5';
		    return false;		  
	   });
	   
	   $(row10).click(function(){ //silver exchange
		   window.location.href = 'bullionexchangestock.htm?iId=10&cId=10';
		    return false;
		  
	   });
	   
	   $(row11).css("cursor", "pointer");
	   $(row12).css("cursor", "pointer");
	   $(row2).css("cursor", "pointer");
	   $(row7).css("cursor", "pointer");
	   $(row5).css("cursor", "pointer");
	   $(row10).css("cursor", "pointer");
	   //
	  $("#table_rowRemove tr:nth-child(17) td:nth-child(2) a").removeAttr('href').addClass("a_linkRemove");
	  $("#table_rowRemove tr:nth-child(18) td:nth-child(2) a").removeAttr('href').addClass("a_linkRemove");
	  var categoryNamesitems = $("#categoryNames").val();
	/*  var categorygrossWeight = $("#grossWeight").val();
	  if(categorygrossWeight == 0.00){
		  $("#update").val("Save");
	  }
	  else{
		  $("#update").val("Update");
	  }*/
	  
	  if(categoryNamesitems == "Gold Bullion" || categoryNamesitems == "Silver Bullion")
		{
		  $(".GS_bullion").show();
			$(".GS_exchange").hide();
			
		}
	  if(categoryNamesitems == "Gold Exchange" || categoryNamesitems == "Silver Exchange")
		{		  
			$(".GS_exchange").show();
			$(".GS_bullion").hide();
		}	  
}

/**************   sales Popupwindow     ***************/
function popup_window() { 
	 
    //select all the a tag with name equal to modal
    $('a[name=modal]').click(function(e) {
        //Cancel the link behavior
        e.preventDefault();
        //Get the A tag
        var id = $(this).attr('href');
        
        var winH = $(window).height();
        var winW = $(window).width();
               
        //Set the popup window to center
        $(id).css('top',  winH/2-$(id).height()/2);
        $(id).css('left', winW/2-$(id).width()/2);
     
        //transition effect
        $(id).slideDown(200);
        //Get the screen height and width
        var maskHeight = $(document).height();
        var maskWidth = $(document).width();
     
        //Set height and width to mask to fill up the whole screen
        $('#mask').css({'width':maskWidth,'height':maskHeight});
         
        //transition effect    
        $('#mask').fadeIn(); 
        $('#mask input[type=text]').val('');
        //Get the window height and width
       
     
    });
  /**   $("#insert").click(function(){
    	 var error_val = $("#errorDisplay").val();
    	 if(error_val != null){
    		 $('#mask, .window').show();
    	 }
    	 else{
    		 $('#mask, .window').hide();
    	 }
     });*/
    //if close button is clicked
    $('.window .close, .window input[value=Cancel] ').click(function (e) {
        //Cancel the link behavior
        e.preventDefault();
        $('#mask, .window').hide();
    });    
     
    /*/if mask is clicked
    $('#mask').click(function () {
        $(this).hide();
        $('.window').hide();
    }); */       
     
}
function popup_window_POSCAT() { 
	
		var id = $("#insert").attr("title");
        var winH = $(window).height();
        var winW = $(window).width();
        $(id).css('top',  winH/2-$(id).height()/2);
        $(id).css('left', winW/2-$(id).width()/2);
        $(id).slideDown(200);
       // var percentage = Math.floor(100 * parseInt(data.bytes_uploaded) / parseInt(data.bytes_total));
       // $("#spaceused4").progressBar(percentage, { showText: true} );
          
        var maskHeight = $(document).height();
        var maskWidth = $(document).width();
        $('#mask').css({'width':maskWidth,'height':maskHeight});
        $('#mask').fadeIn(); 
   	 e.preventDefault();
   
}

/*******   sales row hide/show   *********/
function sales_form_rowhide() { 
	

	$(".row_hide").hide();
	$("#row1").show();
	$("#row2").show();
	
	/*$(".del").click(function() {
		$(this).parents(".row_hide").hide();
	});*/
	
};

/*******   form error display   *********/

function error_msg(){
	$("#form_error").hide();
	var error_val = $("#errorDisplay").val();
	//var error_val2 = $("#pos").val();
	//var error_val3 = $("#pos_cus").val();
	//var error_val4 = $("#errorDisplay").val();
	//var error_val4 = $("#pos, #pos2, #pos3").val();
	if(error_val != null){
		$("#form_error").slideDown("fast");
	}
	$("#form_error div a.close").click(function(){
		$("#form_error").slideUp("fast");
	});
}

/*******   Form Customer/Supplier company information hide   *********/

function cmpanyInformation(){
	$(".cmpanyRow").hide();
	/*$(".cmpanyHide span").click(function(){
		$(this).removeClass("formexpand");
		$(this).addClass("formcollapse");
		$(".cmpanyRow").show();
	});*/
	$('#cmpanyHide td span').toggle(cmpanyRowShow, cmpanyRowHide);
	
	function cmpanyRowShow(){
		$(this).removeClass("formexpand");
		$(this).addClass("formcollapse");
		$(".cmpanyRow").show();
	}
	function cmpanyRowHide(){
		
		$(this).removeClass("formcollapse");
		$(this).addClass("formexpand");
		$(".cmpanyRow").hide();
	}
}

/*******   radio_ReceiptShow  *********/

function radio_ReceiptShow(){
	$(".radio_ReceiptShow").hide();
	var jtype= $("#journalType").val();
	
	if(jtype=="Receipt"){
		$(".radio_ReceiptShow").show();
	}
	else{
		$(".radio_ReceiptShow").hide();
	}
}


/*********         *********/

function bullionisales_cus(){
	var val_type = $("#customerName01").val();
		if(val_type != "walk_in"){
			$("#NameCitybullion1").hide();
		}
		else{
			$("#NameCitybullion1").show();
		}
}
/*********    possupplier     *********/

function PosSupplier(){
	var supplier_type = $("#supplierName0").val();
		if(supplier_type != "Walk-in"){
			$(".possupplier").hide();
			$(".possupplier1").show();
		}
		else{
			$(".possupplier1").hide();
			$(".possupplier").show();
			$("#walkinName0").focus();
		}
}

/********  Readonly Focus ******************/

function ReadonlyFocus(e) {
	 var readonly = $(this).is("[readonly]");
	 	if(readonly){
			$(this).focusNextInputField().after().is(function(){if(readonly){$(this).focusNextInputField();}});
	 	}
	 	
}

$.fn.focusNextInputField = function() {
	 return this.each(function() {
        var fields = $(this).parents('form:eq(0),body').find('button,input,textarea,select');
        var index = fields.index( this );
        if ( index > -1 && ( index + 1 ) < fields.length) {
        	fields.eq( index + 1 ).focus().select();
        }
        return false;
    });
};

function ReadonlyFocusKey(e){
	var readonly = $(this).is("[readonly]");
	var keyCode = e.keyCode || e.which;
	if(keyCode == 9) {
	    if(e.shiftKey) {
	    	$(this).prev().is(function(){if(readonly){alert("kjhkjhkjhkjhkj");}});
		}//$(this).focusNextInputFieldKey();
	}
}
$.fn.focusNextInputFieldKey = function() {
	 return this.each(function() {
       var fields = $(this).parents('form:eq(0),body').find('button,input,textarea,select');
       var index = fields.index( this );
       if ( index > -1 && ( index - 1 ) < fields.length) {
          	fields.eq( index - 1 ).focus().select();
     }
       return false;
   });
};


function POSAutoCategory(){
	$(".cname").focusout(function(e) { 
		var count = $(this).attr('title');
		  $.ajax({						
				type:'POST',
				url:'itemNameList.htm',
				data: { cnameValue : $(this).val() },
				success:function(dataI1) {
					$("#pinames"+count).val(dataI1);
				},
				error:function(xmlHttpRequest, textStatus, errorThrown){
					if(xmlHttpRequest.readyState=0 || xmlHttpRequest.status == 0)
					return;
				},
		});
	});
}

/*function jeweltypeTabs(){
	 	$('.catTabclass').hide();
		$('.catTabclass:first').show();
		$('#goldsiltab ul li a:first').css('background','#eee');
		 
		$('#goldsiltab ul li a').click(function(){
			$('#goldsiltab ul li a').not($(this)).css('background','#fff');
			$(this).css('background','#eee');
			var currentTab = $(this).attr('href');
			$('#goldsiltab div').hide();
			$(currentTab).show();
			return false;
});
}*/

function MaualBillingTabs(){
 	$('.ManulTabclass').hide();
	$('.ManulTabclass:first').show();
	$('#Manualtab ul li a:first').css('background','#eee');
	$('#Manualtab ul li a').click(function(){
		$('#Manualtab ul li a').not($(this)).css('background','#fff');
		$(this).css('background','#eee');
		var currentTab = $(this).attr('href');
		$('#Manualtab div').hide();
		$(currentTab).show();
		
		return false;
});
}



/*******************       sales return js *********************/

function salesReturnJs(){
	
	$("#date").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var prettyDate = myDate.getDate()+ '/'+month + '/' + myDate.getFullYear();
	
	
	var URIloc = $(location).attr('href');
	var uripath = URIloc.split('/')[4];
	var errorType=$("#errorName").val();
	 if(errorType=="insertError"){
		$("#update").hide();
		$("#insert").show();
	}
	 else if(errorType=="updateError"){
			$("#update").show();
			$("#insert").hide();
		}
	 if(uripath.indexOf("viewSales.htm") >=0 ){
			$("#insert").hide();	
			$("#update").show();
		}
			
		
	if(uripath.indexOf("newSalesReturn.htm") >=0 )
		{
		$("#update").hide();
		$("#insert").show();
		$("#date").val(prettyDate);
		}
	


	$('#gwt1').keyup(function() {
		   $('#nwt1').val(this.value);
	});
	
	$('#gwt2').keyup(function() {
		   $('#nwt2').val(this.value);
	});		
	$('#gwt3').keyup(function() {
		   $('#nwt3').val(this.value);
	});		
	$('#gwt4').keyup(function() {
		   $('#nwt4').val(this.value);
	});		
	
		
		$("#inputsubmit3").click(Account_validation);
		$("#insert").click(checkIsEmpty);


	function Account_validation(){
		var text1=$("#netWeight").val();
		var text2=$("#itemName").val();
		
		if(text1=="" || text2==""){
			$("#netWeight").val(0);		
		}		
	}
	
	
	function checkIsEmpty(){
		$('input.cal_dep').each(function(){
			var TempVal = $(this).val();
			if(TempVal.length === 0 || TempVal === '' || TempVal === null ||TempVal=='NaN'){
				$(this).val("0");			
			}
		});
	}	
} 

/********************************************/
function listSearchinput(){
	$(".summary #listsearch").val("Enter Text to Search from the list").addClass("listsearchinput");
	$(".summary #listsearch").focusin(function(){
		$(this).val("").removeClass("listsearchinput");
	});
	$(".summary #listsearch").focusout(function(){
		$(this).val("Enter Text to Search from the list").addClass("listsearchinput");
	});
}

/****************     Category List arrangements    **********************/
function categorylistarr(){
	
	$("tr:eq(2)").next("tr td:eq(6)");
}


function CatListZero(){	
	var metalType=$("#metalType").val();
	if(metalType=="Gold Ornaments" || metalType=="Silver Ornaments"){
	$(".categoryItemMaster tr").each(function(){
		var categoryItemMaster = $("td.ItemMasterQty",this).text();
		if(categoryItemMaster == '0'){
			$(this).remove();
			
		}
	});
	}
	var numbr = 1;
	
	$("td.s_no").each(function(){
		$(this).text(numbr);
		numbr++;
	});
	var numbr_sil = 1;
	$(".s_no_sil").each(function(){
		$(this).text(numbr_sil);
		numbr_sil++;
	});
}

/*******************************     Saving  Scheme                 ***********************************/

function SchemeAmtGms(){
	var schem_type =  $("#schemeType, #SchemeTypeSav").val();
	if(schem_type == "Amount"){
		$("#SchemeGrams").val(0.000);
		$('#op_bal_amt_grms').val(0.000);
		$('#cls_bal_amt_grms').val(0.000);
		$(".SchemeGms").hide();
		$(".SchemeAmt").show();
	}
	if(schem_type == "Gold"){
		$("#SchemeAmount").val(0.00);
		$('#op_bal_amt_rs').val(0.00);
		$('#cls_bal_amt_rs').val(0.00);
		$(".SchemeAmt").hide();
		$(".SchemeGms").show();
	}
}
/******************************************************/
function homepagePendings(){
	$(".salesOrderPending").click(function(){
		 window.location.href = 'formledgerpo.htm';
	});
	$(".lowMetal").click(function(){
		 window.location.href = 'formledgerlist.htm';
		 //window.location.href = 'POSRolList.htm';
	});
}

/*******************************    Lot Stock Link          ***********************************/
function lotStockLink(){
	$(document).keydown(function(e){
		var keycde = e.keyCode || e.which;
		if(e.ctrlKey == true && e.shiftKey == true && keycde == "76"){
			window.location.href = 'lotstock.htm';
		}
	});
}

/**** This function is added for checking universal null and NaN condition in every calculation 5-2-13 **/
function checkNull(tempVar){
	if(tempVar == null || tempVar.length == 0 || tempVar == '' || tempVar == 'NaN'){
			return 0;
		}else{
			return tempVar;
		}
}


/*********************************/
function accountsQuikLinks(){
	var rel= $(".quicklinks ul li").attr("rel");
	$(".quicklinks ul li a span").text(rel);
}