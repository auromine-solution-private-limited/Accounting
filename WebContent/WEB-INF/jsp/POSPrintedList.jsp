<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script language="javascript"  type="text/javascript" src="script/jquery-latest.js"></script> 
<script type="text/javascript" src="script/jquery.bgiframe.min.js"></script>
<script type="text/javascript" src="script/jquery-ui.min.js"></script>
</head>
<script language="javascript" type="text/javascript" > 
//$(".printStatus").each(function(){
	//var printStatus = $(this).val();
	//if(printStatus == "Unprinted"){
	//	$(this).parents("tr").find("td:eq(7)").text("Untagged");
//	}
	//if(printStatus == "printed"){
	//	$(this).parents("tr").find("td:eq(7)").text("PrintTagged");
	//}
//});

</script>

<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">POS TAG LIST</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
<br>
<div class="summary"><br><input name="search" type="text" id="listsearch" style="float: left;"  /><input name="search" type="hidden" id="listsearch2" style="float: left;"  />
	<div class="printbarcode">
		<form:form method="get" target="new" action="posBarcode.htm" onsubmit="return sendBarcodes();">
			<textarea name="itemArray" id="txtitemCodes"></textarea>
			<input type="hidden" value="${posPrintFormat}" name="printFormat" />
			<input type="submit" value="Print Label" id="print" class="p_button create_or_list"/>
		</form:form>
	</div>
</div>
<div id="printedtab">
	<ul>
	<!--  	<li class="linkss" >< a  class="TabClass"  href="POSPrintedList.htm" id="Printed" title="PrintTagged">Printed</a></li>-->
		<li class="linkss" ><a class="TabClass activetabs" href="POSPrintedList.htm">Printed</a></li>
		<li class="linkss" ><a class="TabClass" href="postagitem.htm">Unprinted</a></li>
	<!--  	<li class="linkss"><a id="Untagged" title="Untagged">Unprinted</a></li>-->
	
	</ul>
	<span class="checkallclass">Check all<input type="checkbox" id="checkall"></span>
	<div id="Printed" class="catTabclass"  style="width:763px;">
			
<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
<thead class="row_head">
			<!--<th>TAG ID</th>-->
		<tr>
			<th>BARCODE ID</th>
			<th>ITEM NAME</th>
			<th>QTY/SET</th>
			<th>TOT PIECES</th>
			<th>DATE</th>
			<th>SALES RATE</th>
			<th>STATUS</th>	
			<th>ACTION<!-- <input type="checkbox" id="checkall"><br> --></th>
		</tr>
	</thead>
	<c:forEach var="posList" items="${posprinteditemlist}">
				<tr bgcolor="#FFFFFF">
				<!--<td>${posList.tagId}</td>-->
				<td>${posList.barcodeId}</td>
				<td>${posList.itemName}</td>	  
				<td>${posList.qtyset}</td>
				<td>${posList.totalpieces}</td>
				<td><fmt:formatDate  value="${posList.date}" pattern="yyyy-MMM-dd"/></td>
				<td>${posList.salesRate}</td>
				<td>${posList.status}</td>
				<td style="display:none;">${posList.status}</td>
				<td style="display:none;"><input type="text"  class="printStatus" value="${posList.status}" /></td>
				<td><input type="checkbox" name="paradigm" id="${posList.tagId}" title="${posList.barcodeId}" class="print" /></td>
				</tr>
		</c:forEach>
		</table>
	</div>
		</div>
	</div>
		</div>
		</div>		 
<script type="text/javascript" src="script/POSPrintedlist.js"></script>
</body>		

</html>