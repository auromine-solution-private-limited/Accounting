<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<!---------------------------------------->	
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Refinery Receipt List</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<a href="formjoborder.htm"  class="create_or_list">Job Order Issue</a>
	<a href="salesorder.htm"  class="create_or_list">Sales Order List</a>
	<a href="FormRefineryReceipt.htm"  class="create_or_list">Refinery Receipt</a>
	<br>
	<div class="summary">Refinery Receipt List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
	<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	
			
	<thead class="row_head">
	<tr>
		<th>S.No</th>	
		<th>Issue No</th>	
		<th>Refinery Name</th>	
		<th>Refinery Date</th>
		<th>Ornaments Type</th>
		<th>Pieces</th>
		<th>Gross Weight</th>		
		<th>Net Weight</th>
		<th>Action</th>
		</tr>
	</thead>	
				
	<c:forEach var="Refineryrefinery" items="${refineryReceiptList}">
	<tr bgcolor="#FFFFFF">
		<td class="s_no"></td> 
		<td>${Refineryrefinery.refineryReceiptNo}</td>
		<td>${Refineryrefinery.rrName}</td>
		<td><fmt:formatDate  value="${Refineryrefinery.rrDate}" pattern="yyyy-MMM-dd"/></td>
		<td>${Refineryrefinery.rrOrnamentsType}</td>
		<td>${Refineryrefinery.rrpieces}</td>		
		<td>${Refineryrefinery.rrGrossWeight}</td>
		<td>${Refineryrefinery.rrNetWeight}</td>
		<td><a href="viewRefineryReceipt.htm?rrId=${Refineryrefinery.rrId}" class="edit">Edit</a></td>
		
	</tr>
	</c:forEach>	
	
	</table>	
	</div>
	</div>
</div>

</html>