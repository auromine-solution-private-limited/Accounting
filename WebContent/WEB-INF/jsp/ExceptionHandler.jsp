<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page isErrorPage="true" import="java.io.*" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="default.css" type="text/css"></link>
<base href="${pageContext.request.contextPath}">
<script type="text/javascript" src="script/xfade2.js"></script>

<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Jewel Mine</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
</head>
<body>
<div id="container">

<div id="header_container">
<div id="top_nav"> 
<ul>
<li>Username</li>
<li><a href="#">Logout</a></li>
<li><a href="#">Settings</a></li>
</ul>
</div>
<div id="logo">
	<h1><a href="#"><img src="images/logo.png"></a></h1>
</div>

<div id="menu">
	<ul>
		<li class="active"><a href="accounts.htm" title="">Accounts</a></li>
		<li><a href="accountsledger1.htm?accountId=18" title="">Customer</a></li>
		<li><a href="accountsledger2.htm?accountId=17" title="">Supplier</a></li>
		<li><a href="category.htm" title="">Items</a></li>
		<li><a href="salesorder.htm" title="">Orders</a></li>
		<li><a href="purchase.htm" title="">Purchase</a></li>
		<li><a href="salesorder.htm" title="">Sales</a></li>
		<li><a href="#" title="">Journal</a></li>
		<li><a href="reports.jsp" title="">Reports</a></li>
	</ul>
</div> <!-- menu -->
</div> <!-- header-container -->

<div id="content_container">
<div id="col-one">

<div class="boxed">
			<div class="title">Board Rate</div>
			<div class="content">
					<tr>
					<td><p class="tiny">Gold : Rs.2013/gm</p></td>
					</tr><br>
					<tr>
					<td><p class="tiny">Silver: 120.00/gm</p></td>
					</tr>
					
			</div>
	</div>	
<div class="boxed">
		<div id="rotator">
			<img src="catalog/slider-img6.jpg">
			<img src="catalog/slider-img1.jpg">
			<img src="catalog/slider-img2.jpg">
			<img src="catalog/slider-img3.jpg">
			<img src="catalog/slider-img4.jpg">
        	<img src="catalog/slider-img5.jpg">
      	</div>
	</div>
	
	<div class="boxed">
			<div class="title">Account Search</div>
			<div class="content">
				<form id="form4" method="get" action="#">
					<fieldset>
					<legend>Search</legend>
					<input id="inputtext3" type="text" name="inputtext3" value="" class="textfield" />
					<input id="inputsubmit2" type="submit" name="inputsubmit2" value="Search" class="button" />
					<p class="tiny"><a href="#">Advanced Search</a></p>
					</fieldset>
				</form>
			</div>
	</div>
	

	<div class="boxed">
			<div class="title">Master Details</div>
			<div class="content">
				<form id="form3" method="get" action="#">
					<fieldset>					
					<tr>
					<td><p class="tiny"><a href = "wastage.htm" ><input id="wsubmit" type="submit" name="wsubmit" value="Wastage Master" class="button1" /></a></p></td>
					</tr><br>
					<tr>
					<td><p class="tiny"><a href = "ratemaster.htm" ><input id="rsubmit" type="submit" name="rsubmit" value="Rate Master" class="button1" /></a></p></td>
					</tr>
					</fieldset>
				</form>
			</div>
	</div>
		
</div>

<div id="col-form">
	<div class="boxed">
		<div class="title">Exception Handler</div>
		<div class="content">
			<%-- Exception Handler --%>
		<font color="red">
			<%= exception.toString() %><br>
		</font>

		<%
			out.println("<!--");
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			//exception.printStackTrace(pw);
			pw.write(exception.getMessage());
			out.print(sw);
			sw.close();
			pw.close();
			out.println("-->");
		%>	
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