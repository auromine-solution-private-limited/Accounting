<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script language="javascript"  type="text/javascript" src="script/jquery-latest.js"></script> 
<script type="text/javascript" src="script/jquery.bgiframe.min.js"></script>
<script type="text/javascript" src="script/jquery-ui.min.js"></script>

</head>
<body>
<div id="col-form" align="left">
<div class="boxed" >
<div class="title">Debtors Account</div>
<div class="content">
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>


<br>
<div class="summary">POS Sales Return List</div>

<div id="salesreturn_div">
<form:form modelAttribute="posSalesReturn" action="possalesreturnList.htm"> 
	<span>Invoice No :</span><input name="billNo"  id="invoiceNo"  class="salesreturn_invoiceinput"/>
	<input name="search" type="submit" id="POSSRsearch" value="" class="salesreturn_searchBttn"/>	
</form:form>

<form:form commandName="POSsales" action="formPOSsalesreturn.htm" method="POST" >
	<input type="hidden" name="ids" id="tempids" />
	<input type="hidden" name="billNo" value="${possalesId}"/>
	<!--  <input type="submit" name="SRLISTcheckbox" id="salesreturn_checkbox" class="salesreturn_return" value="Return Selected Items" />-->	
	<input type="submit" name="SRLISTsubmit" id="salesreturnSubmit" value="Return Selected Item" class="salesreturn_return"/>
</form:form>

</div>
<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">

<thead class="row_head">
	<tr>
	      
			<th>ID</th>
			<th>Item Code</th>
			<th>Category Name</th>
			<th>Item Name</th>			
			<th>Quantity</th>
			<th>Rate</th>
			<th>Discount</th>
			<th>Sales Amount</th>			
			<th>Return All<input type="checkbox" id="POSSRcheckall"/></th>
		</tr>	 
</thead>			
<tbody>
	<c:forEach var="posSalesRet" items="${posreturnList}">	
	<tr class="salesreturn_Rowlist" >		
	   <td>${posSalesRet.salesItemID}</td>
	   <td>${posSalesRet.posItemCode}</td>		
	   <td>${posSalesRet.categoryName}</td>
	   <td>${posSalesRet.itemName}</td>	
	   <td>${posSalesRet.quantity}</td>
	   <td>${posSalesRet.salesRate}</td>
	   <td>${posSalesRet.discountAmount}</td>
	   <td>${posSalesRet.salesAmount}</td>   
	   <td><input type="checkbox" id="${posSalesRet.salesItemID}" class="salesreturn_checkbox"/></td>	
	</tr>
	</c:forEach>	
	</tbody>		
</table>
	</div>
	</div>
	</div>
	
	
<script language="javascript" type="text/javascript" > 
$(document).ready(function(){
	
	$(".salesreturn_return").click(function(){
			
			var checkBoxes = $("input.salesreturn_checkbox:checkbox:checked").length;
			
	        if (checkBoxes == 0){
	        	
	        	alert('Please Check the item you want to return');
	        return false;
	        }
	        
		});
	
	$(".salesreturn_return").click(function(){
		
		var checkBoxes = $('.salesreturn_checkbox');
		 
        $.each(checkBoxes, function() {
            if ($(this).attr('checked')){
	            var itemcode=$(this).attr("id");
	           // alert(itemcode);
	        	$.ajax({
	        		type:'POST',
	        		url:'possalesreturnList.htm',
	        		data:{posSalesId:itemcode},
	        		success:function(data){	        			
	        		}
        		});
        	}
              
        });
	});
	
$("#salesreturnSubmit").click(function(){
		
		var checkBoxes = $('.salesreturn_checkbox');
		 var temparr = new Array();
        $.each(checkBoxes, function() {
            if ($(this).attr('checked')){
	            var itemcode=$(this).attr("id");
	          temparr.push(itemcode);
	          }            
        });          
        $("#tempids").val(temparr);
	});
	
	
	$("#POSSRcheckall").click(function()				
	{
		//alert("hello");
		var checked_status = this.checked;
		$(".salesreturn_checkbox").each(function()
		{
			this.checked = checked_status;
		});
	});
	
	$("#POSSRsearch").click(searchalert); 
	
});

function searchalert(){ 
	var invoiceNo = $('#invoiceNo').val();
	if(invoiceNo == '' || invoiceNo == null){
		alert("Please Enter the Inovice No");
	}
}	

</script>
	
</body>
</html>