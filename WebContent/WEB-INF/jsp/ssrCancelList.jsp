<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html >

<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Saving Scheme Payment List</div>
<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
<a href="formSSRCancel.htm" class="create_or_list">New Saving Scheme Payment</a>
<br />
<div class="summary">Saving Scheme Cancel/Payment Listing <br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>

<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
		<thead class="row_head">
		<tr>
			<th>S.No</th>
			<th>Payment NO.</th>
			<th>Receipt Date</th>
			<th>Customer Name</th>
			<th>Scheme Name</th>
			<th>Card Number</th>
			<th>Payment Amount</th>
			<th>Action</th>
		</tr>				
	</thead>
				
	<c:forEach var="SPayment" items="${cancelCardList}">
	<tr bgcolor="#FFFFFF">
		<td class="s_no"></td>	
		<td>${SPayment.receiptNO}</td>		
		<td><fmt:formatDate  value="${SPayment.receiptDate}" pattern="yyyy-MMM-dd"/></td>
		<td>${SPayment.customerName}</td>
		<td>${SPayment.schemeName}</td>
		<td>${SPayment.cardNo}</td>
		<td>${SPayment.paymentAmount}</td>
		<td><a href="viewSavingReceipt.htm?receiptId=${SPayment.receiptId}" class="edit">Edit</a></td>				
	</tr>
	</c:forEach>
	
	</table>

</div>
	</div>
	</div>
</body>
</html>