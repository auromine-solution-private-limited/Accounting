<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Auromine Solutions</title>
	<link rel="stylesheet" href="default.css" type="text/css"></link>
	<!--  <link rel="stylesheet" href="general.css" type="text/css" media="screen" />-->
	<link rel="stylesheet" href="jquery.autocomplete.css" type="text/css" />
	<link rel="stylesheet" href="ui.all.css" type="text/css"/>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<base href="${pageContext.request.contextPath}/"/>
	<script language="javascript"  type="text/javascript" type="text/javascript" src="script/xfade2.js"></script>
	<script language="javascript"  type="text/javascript"  type="text/javascript" src="script/search_form.js"></script>
	<script language="javascript"  type="text/javascript" src="script/NaNValidate.js"></script>
	<script language="javascript"  type="text/javascript" src="script/jquery-latest.js"></script> 
	<script type="text/javascript" src="script/jquery.bgiframe.min.js"></script>
	<script type="text/javascript" src="script/jquery.dimensions.js"></script>
	<script type="text/javascript" src="script/jquery.autocomplete.js"></script>	
	<script type="text/javascript" src="script/jquery-ui.min.js"></script>
	<script type="text/javascript" src="script/jquery-all-manuscript.js"></script>	
 	<script type="text/javascript" src="script/jquery-formstyle.js"></script>
 	<script type="text/javascript" src="script/j-listing.js"></script>
 	<script type="text/javascript" src="script/jquery.progressbar.min.js"></script>
 	
<script type="text/javascript" src="script/tabs_report.js"></script>
 	
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
<!--<div class="HeaderExchange" id="HeaderExchange">
	<div class="exchange_pending">
			<span class="gold"><% out.print("Gold : Rs.&nbsp;"+session.getAttribute("GoldRate"));%>/Gm</span>
			<span class="silver"><% out.print("Silver : Rs.&nbsp;"+session.getAttribute("SilverRate"));%>/Gm</span>			
	</div>
</div>-->
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
		  <li class="li">
			<a title="" class="a_menu">Items</a>
			<ul class="submenu">
				<li class="arrow_submenu"></li>
				<li class="list"><a class="a_first" href="category.htm" title="">Metal Summary List</a></li>
				<li class="list"><a class="a_last" href="formjeweltype.htm" title="">Category List</a></li>
				<!-- <li class="list"><a class="a" href="lotstock.htm" title="">Lot Stock</a></li>
				<li class="list"><a class="a_last" href="manualSalesList.htm" title="">Manual Billing</a></li>  --> 
			 </ul>
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
		
		<li class="li"><a class="a_menu" href="sales.htm" title="">Sales</a></li>-->
		<li class="li"><a class="a_menu" title="">Point Of Sales</a>
			<ul class="submenu">
				<li class="arrow_submenu"></li>
				<li class="list"><a class="a_first" href="formPOScategory.htm" title="">Category</a></li>
				<li class="list"><a class="a" href="postagitem.htm" title="">Tagged</a></li>
				<li class="list"><a class="a" href="POSPurchase.htm" title="">Purchase</a></li>
				<li class="list"><a class="a_last" href="possalesList.htm" title="">Sales</a></li>
			</ul>
		</li>
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


<!-- <div id="col-one" align="left">
	<div class="boxed">
			<div class="title">Account Search</div>
			<div class="content">
			<form:form id="form4" method="get" commandName="Ledger" onsubmit="return validate_account();" action="searchledger.htm">
					<fieldset>
					<legend>Search</legend>
				 	Account Name (or) Id
					<input id="ledgerName"  name="ledgerName" value="" class="textfield" />
					<input id="search" type="submit" name="search" value="Search" class="button" />
					<label id="lblledgerName"></label>					
					</fieldset>					
			</form:form>				
			</div>
	</div>
	
	<div class="boxed">
			<div class="title">Sales Estimate</div>
			<div class="content">
			<form:form id="form15" method="post" action="salesest.htm" target="new" onsubmit=" return  validate_SalesEstimate();">
			<fieldset>
			Sales Estimate		
			<input id="seic" type="text" name="seic" class="textfield" onkeydown = "return (event.keyCode!=13);"/>
			<input type="submit" class="button" value="Sales Bill" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')" />
			<label id="lbltagName"></label>
			</fieldset>
			</form:form>				
			</div>
	</div>	
	
	
	<div class="boxed">
			<div class="title">Sales Order Search</div>
			<div class="content">
				<form:form id="form2" method="get" action="searchorder.htm" modelAttribute="Orderno" onsubmit="return validate_order();">
				<fieldset>				
				Order No
				<input id="orderNo" type="text" name="salesOrderId" value="" class="textfield" onkeypress="return ValidateOrderNumberKeyPress(this, event);"/>
				<input id="inputsubmit2" type="submit" name="search2" value="Search" class="button" />							
				<label id="lblorderNo"></label>
				</fieldset>					
				</form:form>	
			</div>
	</div>
		
	<div class="boxed">
		<div class="title">Job Order Search</div>
		<div class="content">
			<form:form id="form5" method="get" action="searchsmith.htm" modelAttribute="jos" onsubmit="return validate_smith();">
				<fieldset>
				<legend>Search</legend>				
				Smith Name (or) JobOrder No
				<input id="smithName" type="text" name="smithName" value="" class="textfield" />
				<input id="inputsubmit2" type="submit" name="search3" value="Search" class="button" />							
				<label id="lblsmithName"> </label>				
				</fieldset>
				</form:form>
		</div>
	</div>
	
	<div class="boxed">
		<div class="title">Item Search</div>
		<div class="content">
		<form:form id="form7" method="get" action="searchitem.htm" modelAttribute="ItSearch" onsubmit="return validate_item();">
		<fieldset>
		<legend>Search</legend>
		Item Name
		<input id="itemName" type="text" name="itemName" value="" class="textfield" />
		Item Weight
		<input id="netWeight" type="text" name="netWeight"  class="textfield" onkeypress="return ValidateNumberKeyPress(this, event);"/>
		<input id="inputsubmit3" type="submit" name="search4" value="Search" class="button" />
		<label id="lblitemName"> </label>		
		</fieldset>
		</form:form>
		</div>
	</div> 
</div>-->
	
<decorator:body />
<script language="javascript"  type="text/javascript"  type="text/javascript" src="script/ledger_Name_Auto.js"></script>
<script language="javascript" type="text/javascript">
 
$(document).ready(function(){	
	$("#inputsubmit3").click(Account_validation);
	$("#inputsubmit2").click(Decimal_Validation); // Order no Integer field decimal Validation.
	$(".list_table").advancedtable({searchField: "#listsearch", loadElement: "#loader", searchCaseSensitive: false, ascImage: "images/up.png", descImage: "images/down.png"});
	$("#search").show();	
});

function Account_validation(){
	
	var text1=$("#netWeight").val();
	var text2=$("#itemName").val();
	
	if(text1=="" || text2==""){
		$("#netWeight").val(0);	
	}		
}

function Decimal_Validation(){
	var orderNo = $("#orderNo").val();
	
	if(!orderNo.match(/[0-9]+/)){
		$("#lblorderNo").html("<span class='error'>Please Enter Only Numbers</span>");
		$("#orderNo").val("");
		return false;
	}
	
	if(orderNo == null || orderNo.length=="0" || orderNo == '' || orderNo ==  'NaN'){		
	}else{
		var temp = parseFloat(orderNo);
		$("#orderNo").val(temp.toFixed(0));
	}
}
</script>
<div id="footer">
	<p id="legal">Copyright &copy; 2013 Auromine Solutions P. Ltd. All Rights Reserved.</p>
	<p id="links"><a href="#">Privacy Policy</a> | <a href="#">Terms of Use</a> | Powered by <a href="http://www.auromine.com/">Auromine Solutions</a>.</p>
</div>
</div>
</body>		


</html>