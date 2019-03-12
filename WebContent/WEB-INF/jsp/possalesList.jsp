<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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

<a href="formPOSsales.htm" class="create_or_list">New POS Sales</a> <!-- ************************ Call to URI  formcustomer.jsp page-->
<a href="possalesreturnList.htm" class="create_or_list">New POS Sales Return</a>  <!-- ************************ Call to URI  formcustomer.jsp page-->
<br>
<div class="summary">POS Sales List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
<thead class="row_head">
            <th>S.NO</th> 
            <th>Invoice Number</th> 
			<th>Sales Date</th>
			<th>Sales Type</th>
			<th>SalesMan Name</th>
			<th>Customer Name</th>
			<th>Tax Amount</th>
			<th>Grand Amount</th>
			<th>Action</th>					
	</thead>
	
	
			
	<c:forEach var="possales" items="${possalesList}">	
	<c:set var="form" value="${possales.salesType}" />
	<c:if test="${form eq 'POS Sales' || form eq 'POS Sales Return'}">
	<tr bgcolor="#FFFFFF">							  
		<td class="s_no"></td> 
		<td>${possales.billNo}</td>
		<td><fmt:formatDate  value="${possales.salesdate}" pattern="yyyy-MMM-dd"/></td>
		<td>${possales.salesType}</td>
		<td>${possales.salesmanName}</td>
		<td>${possales.customerName}</td>		
		<td>${possales.taxinAmount}</td>
		<td>${possales.netAmount}</td>		
		
	<!--  -->	
		<td><a href="viewPOSSales.htm?posSalesId=${possales.posSalesId}" class="edit">Edit</a></td>		
				   
	</tr>
	</c:if>
			</c:forEach>
		
		</table>		
	</div>
	</div>
	</div>
</body>
</html>