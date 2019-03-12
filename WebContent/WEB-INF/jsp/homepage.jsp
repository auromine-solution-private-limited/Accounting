<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"	prefix="decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Auromine Solutions</title>
	<link rel="stylesheet" href="default.css" type="text/css"></link>
	<link rel="stylesheet" href="general.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="jquery.autocomplete.css" type="text/css" />
	<link rel="stylesheet" href="ui.all.css" type="text/css"/>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<base href="${pageContext.request.contextPath}/"/>
	<script language="javascript"  type="text/javascript" src="script/jquery-latest.js"></script> 
	<script type="text/javascript" src="script/jquery.bgiframe.min.js"></script>
	<script type="text/javascript" src="script/jquery.dimensions.js"></script>
	<script type="text/javascript" src="script/jquery.autocomplete.js"></script>	
	<script type="text/javascript" src="script/jquery-ui.min.js"></script>
	<script type="text/javascript" src="script/jquery-all-manuscript.js"></script>
	<script type="text/javascript" src="script/jquery-formstyle.js"></script>	

<script type="text/javascript">


</script>
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
<ul style="float: left;">
<li class="companyName"><% out.print(session.getAttribute("companyname")); %></li>
</ul>
<ul>
<li class="usernameLabel"><% out.print("Hi &nbsp;"+session.getAttribute("username")); %></li>
<li><a href="homepage.htm"  class="home"  title="Home"></a></li>
<li><a href="alluser.htm" class="settings" title="Settings"></a></li>
<li><a href="logout.htm"  class="logout"  title="Logout"></a></li>
</ul>
<ul class="Version">
<li class="product"><% out.print(session.getAttribute("pdroductName")); %></li>
<li class="ver"><% out.print("V"+session.getAttribute("pdroductVersion")); %></li>
	<c:set var="productType" value="${sessionScope.pdroductType}" /> 
	<c:if test="${productType eq 'Trial Version'}">
		<li class="trial"><% out.print("&nbsp;"+session.getAttribute("dayLeft")+"-DAYS:"+session.getAttribute("pdroductType")); %></li>
	</c:if>
</ul>

</div>
<div id="logo">
	<h1><a href="homepage.htm"><img src="images/logo.png"/></a></h1>
</div>

<div id="menu" style="margin: 40px 2px 0 0">
	<ul>
		<li class="active li">
			<a  title="" class="a_menu">Accounts</a>
			<ul class="submenu">
				<li class="arrow_submenu"></li>
				<li class="list"><a class="a_first" href="formledger.htm" title="">Create Ledger</a></li>
				<li class="list"><a class="a_last" href="accounts.htm" title="">List Of Accounts</a></li>
			</ul>
		</li>
		<li class="li">
			<a title="" class="a_menu">Party Ledger</a>
			<ul class="submenu">
				<li class="arrow_submenu"></li>
				<li class="list"><a class="a_first" href="accountsledger1.htm?accountId=18" title="">Customer</a></li>
				<li class="list"><a class="a_last" href="accountsledger2.htm?accountId=17" title="">Supplier</a></li>
				<!-- <li class="list"><a class="a_last" href="#" title="">List Of Accounts</a></li> -->
			</ul>
		</li>
		 <!-- <li class="li">
			<a title="" class="a_menu">Items</a>
			<ul class="submenu">
				<li class="arrow_submenu"></li>
				<li class="list"><a class="a_first" href="category.htm" title="">Metal Summary List</a></li>
				<li class="list"><a class="a_last" href="formjeweltype.htm" title="">Category List</a></li>
				 <li class="list"><a class="a" href="lotstock.htm" title="">Lot Stock</a></li>
				<li class="list"><a class="a_last" href="manualSalesList.htm" title="">Manual Billing</a></li>  --> 
			<!-- </ul>
		</li>
		<!--<li class="li">
			<a class="a_menu" title="">Orders</a> 
			<ul class="submenu">
				<li class="arrow_submenu"></li>
				<li class="list"><a class="a_first" href="joborder.htm" title="">Job Order List</a></li>
				<li class="list"><a class="a" href="newSalesOrder.htm" title="">New Sales Order</a></li>
				<li class="list"><a class="a_last" href="jewelfix.htm" title="">Jewel Repair List</a></li>
				<li class="list"><a class="a_last" href="RefineryList.htm" title="">Refinery Issue</a></li>
				<li class="list"><a class="a_last" href="RefineryReceiptList.htm" title="">Refinery Receipt</a></li>
			</ul>
		</li>
		<li class="li"><a class="a_menu" href="purchase.htm" title="">Purchase</a></li>
		<li class="li"><a class="a_menu" title="">Point Of Sales</a>
			<ul class="submenu">
				<li class="arrow_submenu"></li>
				<li class="list"><a class="a_first" href="formPOScategory.htm" title="">Category</a></li>
				<li class="list"><a class="a" href="postagitem.htm" title="">Tagged</a></li>
				<li class="list"><a class="a" href="POSPurchase.htm" title="">Purchase</a></li>
				<li class="list"><a class="a_last" href="possalesList.htm" title="">Sales</a></li>
			</ul>
		</li>-->
		<!-- <li class="li"><a class="a_menu" href="sales.htm" title="">Sales</a></li>-->
		<li class="li">
			<a class="a_menu" title="">Saving Scheme</a>
			<ul class="submenu">
				<li class="arrow_submenu"></li>
				<li class="list"><a class="a_first" href="savingscheme.htm" title="">Scheme Creation</a></li>
				<li class="list"><a class="a" href="startscheme.htm" title="">Start Scheme</a></li>
				<li class="list"><a class="a" href="cardissueList.htm" title="">Issue Card</a></li>
				<li class="list"><a class="a" href="savingReceipt.htm" title="">Scheme Receipt</a></li>
				<li class="list"><a class="a_last" href="ssrCancelList.htm" title="">Scheme Payment</a></li>
			</ul>
		</li>
		<li class="li">
			<a class="a_menu" title="">Journal</a>
			<ul class="submenu">
				<li class="arrow_submenu"></li>
				<li class="list"><a class="a_first" href="journal.htm" title="">Journal List</a></li>
				<li class="list"><a class="a" href="formjournal.htm" title="">New Journal</a></li>
				<li class="list"><a class="a" href="tagissuelist.htm" title="">Tag Issue List</a></li>
				<li class="list"><a class="a_last" href="transfer.htm" title="">Transfer List</a></li>
			</ul>
		</li>
		<li class="last"><a class="a_menu" href="reports.htm" title="">Reports</a></li>
	</ul>
</div><!-- menu -->
</div><!-- header-container -->
<div id="homepage">
	<div class="leftpane">
		<!-- <div class="exchangepending">
			<c:forEach var="ratemaster" items="${boardrate}">
			<span class="gold"> Gold : Rs.${ratemaster.goldOrnaments}/Gm</span>
			<span class="silver"> Silver : Rs.${ratemaster.silverOrnaments}/Gm</span>
			</c:forEach>
		</div>
		<div class="exchangepending">
			<span class="pendings salesOrderPending"> Sales Order Pending : ${message}</span>
			<span class="pendings lowMetal"> Low Metal : ${SLCount}</span>
		</div> -->
		<div class="quicklinks">
			<ul>
				<li class="accounts"><a href="formledger.htm"><span>Create Ledger</span></a></li>
				<li class="accounts1"><a href="accounts.htm"><span>Accounts List</span></a></li>
				<li class="journal"><a href="journal.htm"><span>Journal</span></a></li>
				<li class="savings"><a href="savingscheme.htm"><span>Saving Scheme</span></a></li>
				<li class="cus"><a href="newCustomer.htm"><span>Add Cutomer</span></a></li>
				<li class="supp"><a href="newSupplier.htm?accountId=17"><span>Add Supplier</span></a></li>
				<li class="reports"><a href="reports.htm"><span>Reports</span></a></li>
				<li class="settings"><a href="alluser.htm"><span>Settings</span></a></li>
			</ul>
		</div>
	</div>
	<div class="rightpane">
		<!-- <div class="home_links">
			<ul>
				<li><a href="ratemaster.htm">Rate Master</a></li>
				<li><a href="homepage.htm">VAT Master</a></li>
				<li><a href="alluser.htm">List of Users</a></li>
			</ul>
		</div>
	</div> -->
	<div class="rightpane">
		<div class="home_date">
			<div class="head">Date</div>
			<div id="date"></div>
		</div>
	</div>
</div>



<div id="footer">
	<p id="legal">Copyright &copy; 2013 Auromine Solutions P. Ltd. All Rights Reserved.</p>
	<p id="links"><a href="#">Privacy Policy</a> | <a href="#">Terms of Use</a> | Powered by <a href="http://www.auromine.com/">Auromine Solutions</a>.</p>
</div>
</div>
</body>		


</html>