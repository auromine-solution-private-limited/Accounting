<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>

<div id="col-form">
	<div class="boxed">
	<div class="title">Sales Order Pending</div>
	<div class="content" style="height:550px; width:748px; overflow-x:auto ; overflow-y: auto; padding-bottom:10px;">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<table border="1" width=100%>			
	<tr>	
	<td bgcolor="#BBDDFF" colspan="13" align="center"><b>Pending Sales Order List</b></td></tr>	
	<tr>
		<td bgcolor="#BBDDFF" >S.O. Id</td>		
		<td bgcolor="#BBDDFF" >Order.No</td>
		<td bgcolor="#BBDDFF" >S.O. Date</td>		
		<td bgcolor="#BBDDFF" >Customer Name</td>
		<td bgcolor="#BBDDFF" >Bullion Type</td>
		<td bgcolor="#BBDDFF" >Finished ItemName</td>
		<td bgcolor="#BBDDFF" >Finished Item Gross Weight</td>
		<td bgcolor="#BBDDFF" >Finished Item Net Weight</td>
		<td bgcolor="#BBDDFF" >Rate</td>
		<td bgcolor="#BBDDFF" >Advance</td>
		<td bgcolor="#BBDDFF" >Delivery Date</td>
	</tr>	
				
	<c:forEach var="Pendingorder" items="${Pendingorder}">
	<tr bgcolor="#FFFFFF">
		<td>${Pendingorder.salesOrderId}</td>   
		<td>${Pendingorder.orderNo}</td>
		<td>${Pendingorder.orderDate}</td>
		<td>${Pendingorder.customerName}</td>
		<td>${Pendingorder.bullionType}</td>
		<td>${Pendingorder.itemName}</td>
		<td>${Pendingorder.finisheditemGrossWt}</td>
		<td>${Pendingorder.finisheditemNetWt}</td>
		<td>${Pendingorder.bullionRate}</td>
		<td>${Pendingorder.advance}</td>
		<td>${Pendingorder.salesDate}</td>
	</tr>
	</c:forEach>	
	</table>
	<a href="formledger.htm" ><b><u>Back</u></b></a>	
	</div> <!-- content -->
	</div>  <!-- boxed -->
</div> <!-- col-form -->

</html>