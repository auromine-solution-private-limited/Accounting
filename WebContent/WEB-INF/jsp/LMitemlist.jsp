<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="default.css" type="text/css"></link>
<link rel="stylesheet" href="general.css" type="text/css" media="screen" />
<base href="${pageContext.request.contextPath}">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Jewel Mine</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
</head>

<body>
<div id="container">
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
<div id="header_container">
<div id="top_nav"> 
<ul>
<li><% out.print("Welcome :"+session.getAttribute("username")); %></li>
<li><a href="logout.htm">Logout</a></li>
<li><a href="userlogin.htm">Settings</a></li>
</ul>
</div>
<div id="logo">
	<h1><a href="#"><img src="images/logo.png"></a></h1>

</div>

<div id="menu">
	<ul>
		<li><a href="formledger.htm" title="">Accounts</a></li>
		<li><a href="accountsledger1.htm?accountId=18" title="">Customer</a></li>
		<li><a href="accountsledger2.htm?accountId=17" title="">Supplier</a></li>
		<li><a href="category.htm" title="">Items</a></li>
		<li><a href="salesorder.htm" title="">Orders</a></li>
		<li><a href="purchase.htm" title="">Purchase</a></li>
		<li><a href="sales.htm" title="">Sales</a></li>
		<li><a href="journal.htm" title="">Journal</a></li>
		<li><a href="reports.jsp" title="">Reports</a></li>
	</ul>
</div><!-- menu -->
</div><!-- header-container -->
<div id="content_container">

<div id="col-one">

	<div class="boxed" align="left">
			<div class="title">Account Search</div>
			<div class="content">
			<form id="form4" method="get" onsubmit="return validate_account();" action="searchledger.htm">
					<fieldset>
					<legend>Search</legend>
					Account Name
					<input id="ledgerName" type="text" name="ledgerName" value="" class="textfield" />
					<input id="inputsubmit2" type="submit" name="search" value="Search" class="button" />
					<label id="lblledgerName"></label>					
					</fieldset>					
			</form>				
			</div>
	</div>
	
	<div class="boxed" align="left">
			<div class="title">Master Details</div>
			<div class="content">
				<form id="form3" method="get" action="#">
					<fieldset>					
					<tr>
					<td><p class="tiny"><a href = "wastage.htm" ><input id="wsubmit" type="submit" name="wsubmit" value="Wastage Master" class="button1" /></a></p></td>
					</tr>
					<tr>
					<td><p class="tiny"><a href = "ratemaster.htm" ><input id="rsubmit" type="submit" name="rsubmit" value="Rate Master" class="button1" /></a></p></td>
					</tr>					
					</fieldset>
				</form>
			</div>
	</div>	
	
	
	<div class="boxed" align="left">
			<div class="title">Sales Order Search</div>
			<div class="content">
				<form id="form2" method="get" action="searchorder.htm" onsubmit="return validate_order();">
				<fieldset>				
				Order No
				<input id="orderNo" type="text" name="orderNo" value="" class="textfield" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" />
				<input id="inputsubmit2" type="submit" name="search" value="Search" class="button" />							
				<label id="lblorderNo"> </label>
				</fieldset>					
				</form>	
			</div>
	</div>
	
	
	<div class="boxed" align="left">
		<div class="title">Job Order Search</div>
		<div class="content">
		<form id="form2" method="get" action="searchsmith.htm" >
				<fieldset>
				<legend>Search</legend>
				Job Order No
				<input id="orderNo" type="text" name="orderNo" value="0" class="textfield" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" />										
				Smith Name
				<input id="smithName" type="text" name="smithName" value="" class="textfield"  />
				<input id="inputsubmit2" type="submit" name="search" value="Search" class="button" />							
				<label id="lblorderNo"> </label>				
				</fieldset>
			</form>
		</div>
	</div>
	
	<div class="boxed" align="left">
		<div class="title">Item Search</div>
		<div class="content">
		<form id="form7" method="get" action="searchitem.htm" onsubmit="return validate_item();">
		<fieldset>
		<legend>Search</legend>
		Item Name
		<input id="itemName" type="text" name="itemName" value="" class="textfield" />
		Item Weight
		<input id="netWeight" type="text" name="netWeight" value="0.0" class="textfield" />
		<input id="inputsubmit2" type="submit" name="search" value="Search" class="button" />
		<label id="lblitemName"> </label>		
		</fieldset>
		</form>
		</div>
	</div>


</div><!-- col-one -->

<div id="col-form">
	<div class="boxed">
	<div class="title" align="left">Low Metal Balance</div>
	<div class="content" style="height:518px; width:748px; overflow-x:auto ; overflow-y: auto; padding-bottom:10px;">
	<!----------------------------------------------------->
    <table border="1" width=100%>			
	<tr> 
			<td bgcolor="#BBDDFF" colspan="11" align="center"><b>Item List</b></td>
		</tr>
	<tr>
			<td bgcolor="#BBDDFF" >Item Code</td>
			<td bgcolor="#BBDDFF" >Item Name</td>
			<td bgcolor="#BBDDFF" >Category Name</td>
			<td bgcolor="#BBDDFF" >Sub Category Name</td>
			<td bgcolor="#BBDDFF" >Bullion Type</td>
			<td bgcolor="#BBDDFF" >Gross Weight</td>
			<td bgcolor="#BBDDFF" >Wastage By %</td>
			<td bgcolor="#BBDDFF" >Making charges %</td>
			<td bgcolor="#BBDDFF" >Number Of Pieces</td>	
	</tr>	
				
	<c:forEach var="stocklist" items="${stocklist}">
	<tr bgcolor="#FFFFFF">
				 <td>${stocklist.itemCode}</td>
				   <td>${stocklist.itemName}</td>	
				   <td>${stocklist.categoryName}</td>
				   <td>${stocklist.subCategoryName}</td>
				   <td>${stocklist.bullionType}</td>
				   <td>${stocklist.grossWeight}</td>
				   <td>${stocklist.wastageByPercentage}</td>
				   <td>${stocklist.mcByPercentage}</td>
					<td>${stocklist.numberOfPieces}</td>	
		
	</tr>
	</c:forEach>
	
	</table>
	
	</div> <!-- content -->
	</div>  <!-- boxed -->
</div> <!-- col-form -->

</div> <!-- content_container -->

<div id="footer">
	<p id="legal">Copyright &copy; 2010 Auromine Solutions P. Ltd. All Rights Reserved.</p>
	<p id="links"><a href="#">Privacy Policy</a> | <a href="#">Terms of Use</a> | Powered by <a href="http://www.auromine.com/">Auromine Solutions</a>.</p>
</div>
</div> <!-- container -->
</body>		

</html>