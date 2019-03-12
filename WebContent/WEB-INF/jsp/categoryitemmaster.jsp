<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<script language="javascript"  type="text/javascript" src="script/jquery-latest.js"></script> 
	<script type="text/javascript" src="script/jquery.bgiframe.min.js"></script>
	<script type="text/javascript" src="script/jquery-ui.min.js"></script>
</head>
<body>
	<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Item List</div>
	<div class="content">
	<%
	if(session.getAttribute("username")==null)
	{
		response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
	}
	%>
	<a href="formitemmaster.htm?categoryId=${category.categoryId}" class="create_or_list gLosestock" >New Item</a> <!-- ************************ Call to URI  formcustomer.jsp page-->
	<a style="float:right;" href="categoryList.htm?bcat=${category.baseCategory}" class="create_or_list">Return To Sub Category List</a>
	<br>
	<div class="summary"> < c:out value="${category.metalType}"  /> &rarr; < c:out value="${category.baseCategory}" /> &rarr; < c:out value="${category.categoryName}" /> <br><input name="search" type="text" id="listsearch" style="float: left;"  />
		<input type="hidden" value="${category.metalType}" id="metalType">
		<%-- <div class="printbarcode">
		<form:form method="get" target="new" action="ornBarcodePrint.htm" onsubmit="return sendOrnBarcodes();">
			
			<input type="submit" value="Print Label" id="print" class="p_button create_or_list"/>
		</form:form> --%>
	<div class="printbarcode">
		<textarea name="itemArray" id="txtitemCodes"></textarea>
		<input type="hidden" id="ornPrintFormat" value="${ornPrintFormat}" name="ornPrintFormat" />
		<input type="submit" value="Print" id="print" class="p_button create_or_list"/>
	</div>
	

	<span class="checkallclass">Check all<input type="checkbox" id="checkall"></span>
	</div>
	
	<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table categoryItemMaster">
	<thead class="row_head">
	<tr>
		<th>S.No</th>
		<th>Item Code</th>
		<th>Item Name</th>
		<th>Gross Wt</th>
		<th>V.A %</th>
		<th>M.C per gram</th>
		<th>M.C In Rupees</th>
		<th>No. Of Sets</th>
		<th>Item Seal</th>
		<th>Total Gwt</th>		
		<th>Action<!-- <input type="checkbox" id="checkall"><br> --></th>
	</tr>
					
	</thead>
	<c:forEach var="itemmaster" items="${category.itemnames}">
		<tr class="ItemMasterRow">
			<td class="s_no"></td>				  
			<td>${itemmaster.itemCode}</td>
			<td>${itemmaster.itemName}</td>
			<td>${itemmaster.grossWeight}</td>
			<td>${itemmaster.vaPercentage}</td>
			<td>${itemmaster.mcPerGram}</td>
			<td>${itemmaster.mcInRupee}</td>
			<td class="ItemMasterQty">${itemmaster.qty}</td>	
			<td>${itemmaster.itemseal}</td>
			<td>${itemmaster.totalGrossWeight}</td>
			<td><a href="viewitemmaster.htm?Id=${itemmaster.itemId}&cId=${category.categoryId}" class="edit">Edit</a><input type="checkbox" name="paradigm" id="${itemmaster.itemId}" title="${itemmaster.itemId}" class="print" /></td>	
		</tr>
	</c:forEach>
	</table>
	</div>
	</div>
	<script language="javascript" type="text/javascript" > 
	$(document).ready(function(){
		$("#checkall").click(function()				
		{
			var checked_status = this.checked;
			$(".print").each(function()
			{
				this.checked = checked_status;
			});
		});	
		
		$(".p_button").click(sendOrnBarcodes);
		
		$("#txtitemCodes").hide();
		tohide();
	});
function tohide(){
	var metaltype=$("#metalType").val();
	var Sno = $("td.s_no").text();
	if(metaltype=="Gold Loose Stock" ){
		$("#print").hide();
		$(".print").hide();
		$(".checkallclass").hide(); //
		if(Sno == "1"){
			$(".gLosestock").hide();
		}
		
	}
	if(metaltype=="Silver Loose Stock" ){
		$("#print").hide();
		$(".print").hide();
		$(".checkallclass").hide(); //
		if(Sno == "1"){
			$(".gLosestock").hide();
		}
		
	}
}
	/** For Printing Bar codes in Multiple Formats **/
	function sendOrnBarcodes(){
		
	   var checkBoxes = $('.print');
	   var itemArray = new Array();
	   $.each(checkBoxes, function() 
	   {
	    	if ($(this).attr('checked'))
	    	{
	    		itemArray.push($(this).attr("title"));
	    		$(this).attr('checked',false);
	    		$('#checkall').attr('checked',false);
	    	}
	   });
	   
	   if(itemArray==null || itemArray.length ==0){
		   alert("Please Check atleast one Tag");
		   return false;
	   }else{
		   $("#txtitemCodes").val("");
		   $("#txtitemCodes").val(itemArray);
		 var itemIds = itemArray.toString();
		 var ornPrintFormat = $("#ornPrintFormat").val();
		$.ajax({
			type : 'GET',
			url : 'ornBarcodePrint.htm',
			data : { itemArray : itemIds , ornPrintFormat : ornPrintFormat },
			success : function(sdata) {
      		}
       	});
	   }
	   return true;
	}	
	</script>	 
</body>		
</html>