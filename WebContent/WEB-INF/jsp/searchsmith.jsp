<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Search Job Order</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<div class="summary">Job Order Search List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
	<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	<thead class="row_head">	
	
		<th bgcolor="#BBDDFF" >Job orderNo</th>		
		<th bgcolor="#BBDDFF" >Job order Date</th>
		<th bgcolor="#BBDDFF" >SmithName</th>
		<th bgcolor="#BBDDFF" >Metal Issue Type</th>
		<th bgcolor="#BBDDFF" >Issued Gross Weight</th>
		<th bgcolor="#BBDDFF" >Issued Net Weight</th>
		<th bgcolor="#BBDDFF" >Status</th>
		<th bgcolor="#BBDDFF" >Received Item</th>
		<th bgcolor="#BBDDFF" >Finished Item Gross Weight</th>
		<th bgcolor="#BBDDFF" >Finished Item Net Weight</th>
		<th bgcolor="#BBDDFF" >No. Of Pcs</th>
		<th bgcolor="#BBDDFF" >Delivery Date</th>
		<th bgcolor="#BBDDFF" >Action</th>
		
	</thead>
		<c:if test="${empty jobOrderList }">
		<b><font color="red">***No such ID and User Name Exits</font></b>
		</c:if>		
	<c:forEach var="joborder" items="${jobOrderList}">
	<tr bgcolor="#FFFFFF">
		<td>${joborder.orderNo}</td>
		<td>${joborder.orderDate}</td>
		<td>${joborder.smithName}</td>
		<td>${joborder.bullionType}</td>
		<td>${joborder.issuedGrossWeight}</td>
		<td>${joborder.issuedNetWeight}</td>
		<td>${joborder.status}</td>
		<td>${joborder.categoryName}</td>
		<td>${joborder.finisheditemGrossWt}</td>
		<td>${joborder.finisheditemNetWt}</td>
		<td>${joborder.numberOfPieces}</td>
		<td>${joborder.deliveryDate}</td>
		<td><a href="formjoborderrcpt.htm?jobOrderId=${joborder.jobOrderId}" class="edit">Edit</a></td>		
		
	</tr>
	</c:forEach>
	</table>	
	</div>
	</div>
</div>
</html>