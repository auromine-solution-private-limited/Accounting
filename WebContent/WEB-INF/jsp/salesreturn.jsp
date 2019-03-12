<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="com.jewellery.entity.ItemMaster"%>
<%@ page language="java" import="com.jewellery.dao.ItemMasterDao"%>
<%@ page language="java" import="com.jewellery.dao.hibernate.HibernateItemMasterDaoImpl"%>

<%@ page language="java" import="java.util.List"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="default.css" type="text/css"></link>
<link rel="stylesheet" href="ui.all.css" type="text/css"/>
<base href="${pageContext.request.contextPath}/">
<!--link rel="stylesheet" href="<c:url value = "css/default.css" />" type="text/css"></link--> 

<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Jewel Mine</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery-ui.min.js"></script>
<script type="text/javascript" src="script/NaNValidate.js"></script>
<script>
$(document).ready(function(){
$("#date").datepicker({ showOn: 'button', buttonImageOnly: true, buttonImage: 'images/icon_cal.png' });
var myDate = new Date();
var month = myDate.getMonth() + 1;
var prettyDate = month + '/' + myDate.getDate() + '/' + myDate.getFullYear();
$("#date").val(prettyDate);
});
</script>
<script language="javascript"> 
function notEmpty(elem, helperMsg){
	if(elem.value.length == 0){
		alert(helperMsg);
		elem.focus();
		return false;
	}
	return true;
}
		
function init()
{
<%
			String sbutton="";
			String itemcd ="";
			String itemNm ="";
			String catNm ="";
			String btype ="";
			String kinfo ="";
			String pcs = "1";
			String grosswt = "0";
			String netwt = "0"; 
			String brate = "0";
			String wastegms = "0";
			String mcgms = "0";
			String stone = "";
			String stcost = "0";
			String va = "0";
			String amtless = "0";
			String catless = "0";
			String slorderid ="0";
			String custName = "";
			String slDate = "";
			String salesMan = "";
			
			String itemcd1 ="";
			String itemNm1 ="";
			String catNm1 ="";
			String btype1 ="";
			String kinfo1 ="";
			String pcs1 = "1";
			String grosswt1 = "0";
			String netwt1 = "0"; 
			String brate1 = "0";
			String wastegms1 = "0";
			String mcgms1 = "0";
			String stone1 = "";
			String stcost1 = "0";
			String va1 = "0";
			String amtless1 = "0";
			String catless1 = "0";
			String slorderid1 ="0";
			String custName1 = "";
			String slDate1 = "";
			String salesMan1 = "";
			
			String itemcd2 ="";
			String itemNm2 ="";
			String catNm2 ="";
			String btype2 ="";
			String kinfo2 ="";
			String pcs2 = "1";
			String grosswt2 = "0";
			String netwt2 = "0"; 
			String brate2 = "0";
			String wastegms2 = "0";
			String mcgms2 = "0";
			String stone2 = "";
			String stcost2 = "0";
			String va2 = "0";
			String amtless2 = "0";
			String catless2 = "0";
			String slorderid2 ="0";
			String custName2 = "";
			String slDate2 = "";
			String salesMan2 = "";
			
			String itemcd3 ="";
			String itemNm3 ="";
			String catNm3 ="";
			String btype3 ="";
			String kinfo3 ="";
			String pcs3 = "1";
			String grosswt3 = "0";
			String netwt3 = "0"; 
			String brate3 = "0";
			String wastegms3 = "0";
			String mcgms3 = "0";
			String stone3 = "";
			String stcost3 = "0";
			String va3 = "0";
			String amtless3 = "0";
			String catless3 = "0";
			String slorderid3 ="0";
			String custName3 = "";
			String slDate3 = "";
			String salesMan3 = "";
			
			String itemcd4 ="";
			String itemNm4 ="";
			String catNm4 ="";
			String btype4 ="";
			String kinfo4 ="";
			String pcs4 = "1";
			String grosswt4 = "0";
			String netwt4 = "0"; 
			String brate4 = "0";
			String wastegms4 = "0";
			String mcgms4 = "0";
			String stone4 = "";
			String stcost4 = "0";
			String va4 = "0";
			String amtless4 = "0";
			String catless4 = "0";
			String slorderid4 ="0";
			String custName4 = "";
			String slDate4 = "";
			String salesMan4 = "";
		
			
			
			float temp=0;
			float brt =2030;
			float totAmount =0 ;
			float totLess = 0;
			float netAmount = 0;
			float vat = 0;
			float cst = 0;
			float billAmount =0;
			float advance =0;
			float balanceAmount =0;
	        Double t=0.00;
	        float less=0.00f;
	        float less1=0.00f;
	        float less2=0.00f;
	        float less3=0.00f;
	        float less4=0.00f;	
	        float tot=0;
	
%>
}

function access(){ 
	<%	

	sbutton= request.getParameter("s");

		if (request.getParameter("itemCode") != null )
		{			
			slorderid = request.getParameter("salesOrderId");
			custName = request.getParameter("customerName");
			slDate = request.getParameter("salesDate");
			salesMan = request.getParameter("salesmanName");
			
			itemcd = request.getParameter("itemCode");
			itemNm = request.getParameter("itemName");
			catNm  = request.getParameter("categoryName");
			btype = request.getParameter("bulliontype");
			kinfo = request.getParameter("karatinfo");
			//pcs = request.getParameter("pcs");
			grosswt = request.getParameter("grosswt");
			netwt = request.getParameter("netwt");
			brate = request.getParameter("brate");
			stone = request.getParameter("stone");
			stcost = request.getParameter("stcost");
			wastegms = request.getParameter("wastegms");
			mcgms = request.getParameter("mcgms");			
			va = request.getParameter("va");
		
	
			
			t = new Double(grosswt);
			temp =(float) t.floatValue();
			t = new Double(brate);
			brt = (float) t.floatValue();     tot = brt * temp;     //rate * grosswt
			t = new Double(catless);
			less = (float) t.floatValue(); less = (less * tot) /100;
			tot = tot - less;
			amtless = Float.toString(tot);
			catless = Float.toString(less);		
			
			totAmount = totAmount + tot; 
	        totLess =0.00f;		
		    vat = 0.0f;
		  
			cst= 0.00f;
			billAmount = totAmount + vat + cst;
			advance = 0.00f;
			}			
%>			
}

function access1(){ 
	<%	

	sbutton= request.getParameter("s1");
	if (request.getParameter("itemCode1") != null )
		{
			itemcd1 = request.getParameter("itemCode1");
			itemNm1 = request.getParameter("itemName1");
			catNm1  = request.getParameter("categoryName1");
			btype1 = request.getParameter("bulliontype1");
			kinfo1 = request.getParameter("karatinfo1");
			//pcs1 = request.getParameter("pcs1");
			grosswt1 = request.getParameter("grosswt1");
			netwt1 = request.getParameter("netwt1");
			brate1 = request.getParameter("brate1");
			stone1 = request.getParameter("stone1");
			stcost1 = request.getParameter("stcost1");
			wastegms1 = request.getParameter("wastegms1");
			mcgms1 = request.getParameter("mcgms1");
			va1 = request.getParameter("va1");
			catless1 = request.getParameter("less1");
			amtless1 = request.getParameter("va1"); 
		
			
			
			t = new Double(grosswt1);
			temp =(float) t.floatValue();
			t = new Double(brate1);
			brt = (float) t.floatValue();     tot = brt * temp;     //rate * grosswt
				
			t = new Double(catless1);
			less1 = (float) t.floatValue();   less1 = (less1 * tot) /100;
			tot = tot - less1; 
			amtless1 = Float.toString(tot);	
			catless1 = Float.toString(less1);				
			
			totAmount = totAmount + tot;
	        totLess =0.00f;		
		    vat = 0.00f;
	
			cst= 0.00f;
			billAmount = totAmount + vat + cst;
			advance = 0.00f;
		}

 			
%>			
}

function access2(){ 
	<%	

	sbutton= request.getParameter("s2");
if (request.getParameter("itemCode2") != null )
		{
			itemcd2 = request.getParameter("itemCode2");
			itemNm2 = request.getParameter("itemName2");
			catNm2  = request.getParameter("categoryName2");
			btype2 = request.getParameter("bulliontype2");
			kinfo2 = request.getParameter("karatinfo2");
			//pcs2 = request.getParameter("pcs2");
			grosswt2 = request.getParameter("grosswt2");
			netwt2 = request.getParameter("netwt2");
			brate2 = request.getParameter("brate2");
			stone2 = request.getParameter("stone2");
			stcost2 = request.getParameter("stcost2");
			wastegms2 = request.getParameter("wastegms2");
			mcgms2 = request.getParameter("mcgms2");
			va2 = request.getParameter("va2");
		
				
				
			t = new Double(grosswt2);
			temp =(float) t.floatValue();
			t = new Double(brate2);
			brt = (float) t.floatValue();     
			tot = brt * temp;     //rate * grosswt
					
			t = new Double(catless2);
			less2 = (float) t.floatValue(); less2 = (less2 * tot) /100;
			tot = tot - less2;     
			amtless2 = Float.toString(tot);	
			catless2 = Float.toString(less2);
			
			totAmount = totAmount + tot;
	        totLess =0.00f;		
		     vat = 0.00f;
		
			cst= 0.00f;
			billAmount = totAmount + vat + cst;
			advance = 0.00f; 
				
		}		
%>			
}

function access3(){ 
	<%	

	sbutton= request.getParameter("s3");
if (request.getParameter("itemCode3") != null )
		{
			
			itemcd3 = request.getParameter("itemCode3");
			itemNm3 = request.getParameter("itemName3");
			catNm3  = request.getParameter("categoryName3");
			btype3 = request.getParameter("bulliontype3");
			kinfo3 = request.getParameter("karatinfo3");
			//pcs3 = request.getParameter("pcs3");
			grosswt3 = request.getParameter("grosswt3");
			netwt3 = request.getParameter("netwt3");
			brate3 = request.getParameter("brate3");
			stone3 = request.getParameter("stone3");
			stcost3 = request.getParameter("stcost3");
			wastegms3 = request.getParameter("wastegms3");
			mcgms3 = request.getParameter("mcgms3");
			va3 = request.getParameter("va3");
			catless3 = request.getParameter("less3");
			amtless3 = request.getParameter("va3");

	 
		    t = new Double(grosswt3);
			temp =(float) t.floatValue();
			t = new Double(brate3);
			brt = (float) t.floatValue();     
			tot = brt * temp;     //rate * grosswt
			
			t = new Double(catless3);
			less3 = (float) t.floatValue();   less3 = (less3 * tot) /100;
			tot = tot - less3;
			amtless3 = Float.toString(tot);	
			catless3 = Float.toString(less3);
			
			totAmount = totAmount + tot;
	        totLess =0.00f;		
		    vat = 0.00f;
	
			cst= 0.00f;
			billAmount = totAmount + vat + cst;
			advance = 0.00f;
	}
 			
%>			
}

function access4(){ 
	<%	

	sbutton= request.getParameter("s4");
if (request.getParameter("itemCode4") != null )
		{
			
			itemcd4 = request.getParameter("itemCode4");
			itemNm4 = request.getParameter("itemName4");
			catNm4  = request.getParameter("categoryName4");
			btype4 = request.getParameter("bulliontype4");
			kinfo4 = request.getParameter("karatinfo4");
			//pcs4 = request.getParameter("pcs4");
			grosswt4 = request.getParameter("grosswt4");
			netwt4 = request.getParameter("netwt4");
			brate4 = request.getParameter("brate4");
			stone4 = request.getParameter("stone4");
			stcost4 = request.getParameter("stcost4");
			wastegms4 = request.getParameter("wastegms4");
			mcgms4 = request.getParameter("mcgms4");
			va4 = request.getParameter("va4");
			catless4 = request.getParameter("less4");
			amtless4 = request.getParameter("va4"); 


			t = new Double(grosswt4);
			temp =(float) t.floatValue();
			t = new Double(brate4);
			brt = (float) t.floatValue();     
			tot = brt * temp;     //rate * grosswt
			
			t = new Double(catless4);
			less4 = (float) t.floatValue();
			tot = tot - less4;         less4 = (less4 * tot) /100;
			amtless4 = Float.toString(tot);	
			catless4 = Float.toString(less4);
						
			totAmount = totAmount + tot;
	        totLess =0.00f;		

		    vat = 0.00f;
		
			cst= 0.00f;
			billAmount = totAmount + vat + cst;
			advance = 0.00f;
			
		}

 			
%>			
}

function sum() {
var txt1 = document.getElementById('subtotal').value;
var txt2 = document.getElementById('vatpercentage').value;
var txt3 = document.getElementById('tradediscount').value;
var FNTsum=0;
var FNTsum1=0;
var FNTsum2=0;
FNTsum=txt1*(txt2/100);
document.getElementById('vat').value =FNTsum;
FNTsum1=parseFloat(txt1)+FNTsum;
document.getElementById('netamount').value =FNTsum1;
FNTsum2=FNTsum1-parseFloat(txt3);
document.getElementById('billamount').value =FNTsum2;
}
</script> 

</head>
<body onLoad="onload();">

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
		<li class="active"><a href="sales.htm" title="">Sales</a></li>
		<li><a href="journal.htm" title="">Journal</a></li>
		<li><a href="reports.jsp" title="">Reports</a></li>
	</ul>
</div>

<div id="wrapper">

<!-- div id="col-one">
<div class="boxed">
<h2 class="title">Account Search</h2>
<div class="content">
<form id="form4" method="get" action="#">
<fieldset><legend>Search</legend> <input id="inputtext3"
	type="text" name="inputtext3" value="" class="textfield" /> <input
	id="inputsubmit2" type="submit" name="inputsubmit2" value="Search"
	class="button" />
<p class="tiny"><a href="#">Advanced Search</a></p>
</fieldset>
</form>
</div>
</div>

<div class="boxed">
<h2 class="title">Customer Search</h2>
<div class="content">
<form id="form3" method="get" action="#">
<fieldset><legend>Search</legend> <input id="inputtext3"
	type="text" name="inputtext3" value="" class="textfield" /> <input
	id="inputsubmit2" type="submit" name="inputsubmit2" value="Search"
	class="button" />
<p class="tiny"><a href="#">Advanced Search</a></p>
</fieldset>
</form>
</div>
</div>

<div class="boxed">
<h2 class="title">Supplier Search</h2>
<div class="content">
<form id="form4" method="get" action="#">
<fieldset><legend>Search</legend> <input id="inputtext3"
	type="text" name="inputtext3" value="" class="textfield" /> <input
	id="inputsubmit2" type="submit" name="inputsubmit2" value="Search"
	class="button" />
<p class="tiny"><a href="#">Advanced Search</a></p>
</fieldset>
</form>
</div>
</div>

<div class="boxed">
<h2 class="title">Category Search</h2>
<div class="content">
<form id="form2" method="get" action="#">
<fieldset><legend>Search</legend> <input id="inputtext3"
	type="text" name="inputtext3" value="" class="textfield" /> <input
	id="inputsubmit2" type="submit" name="inputsubmit2" value="Search"
	class="button" />
<p class="tiny"><a href="#">Advanced Search</a></p>
</fieldset>
</form>
</div>
</div>

<div class="boxed">
<h2 class="title">Item Search</h2>
<div class="content">
<form id="form2" method="get" action="#">
<fieldset><legend>Search</legend> <input id="inputtext3"
	type="text" name="inputtext3" value="" class="textfield" /> <input
	id="inputsubmit2" type="submit" name="inputsubmit2" value="Search"
	class="button" />
<p class="tiny"><a href="#">Advanced Search</a></p>
</fieldset>
</form>
</div>
</div>

</div --> <!-- Col-one --->

<div id="col-form1" >
	<div class="boxed">
	<div class="title_1">Sales Details</div>
	<div class="content">
	<form:form>
	<form:errors path="*" />
	<table id="formSales" align="left">
			<tr><td bgcolor="#33FFFF" colspan="10"><B>Sales Information</B></td></tr>
			<tr><td colspan="8">&nbsp;</td></tr>
			<tr><td  bgcolor="#BBDDFF">Sales Order Id</td><td><input name="slSalesOrderId" onclick="init()" value="<%=slorderid%>" /></td>
		<td bgcolor="#BBDDFF" >Customer Name</td>
			<td><select name="customerName">
			<option value="0">----select----</option>
			<c:forEach var="ledger" items="${name}">
			<option value="<c:out value="${ledger.ledgerName}" />">
			<c:out value="${ledger.ledgerName}" />
			</option>
			</c:forEach>
			</select>
			</td></tr>
			<tr><td  bgcolor="#BBDDFF" >Sales Date</td>
			<td><input name="salesDate"  value="<%=slDate%>" id="date"/></td>
			<td  bgcolor="#BBDDFF" >Salesman Name</td>
			<td>
			<select name="salesmanName">
			<option value="0">----select----</option>
			<c:forEach var="salesnamname" items="${s_manname}">
			<option value="<c:out value="${salesnamname.fullName}" />">
			<c:out value="${salesnamname.fullName}" />
			</option>
			</c:forEach>
			</select>
			</td>
			</tr>
	</table>
	
	<table align="left" width=100%>
			<tr><td colspan=8>&nbsp;</td></tr>
	   		<tr><td  bgcolor="#BBDDFF" width="7">Item Code</td><td bgcolor="#BBDDFF" width="2">Se</td><td  bgcolor="#BBDDFF" width="7">Item Name</td><td  bgcolor="#BBDDFF" width="7">Category</td><td  bgcolor="#BBDDFF" width="5">Metal</td>
	   		<!-- td  bgcolor="#BBDDFF" width="5">Karat</td -->
	   		<td  bgcolor="#BBDDFF" width="5">Pcs/Set</td><td  bgcolor="#BBDDFF" width="5">Gross Wt.</td><td  bgcolor="#BBDDFF" width="4">Net Wt.</td><td  bgcolor="#BBDDFF" width="4">Rate</td><td  bgcolor="#BBDDFF" width="5" >Stone Desc.</td><td  bgcolor="#BBDDFF" width="5">Stone Cost.</td>
			<td  bgcolor="#BBDDFF" width="5">Waste gms.</td><td  bgcolor="#BBDDFF" width="5">MC gms.</td><td  bgcolor="#BBDDFF" width="5">VA </td><td  bgcolor="#BBDDFF" width="7">Amount</td>
			</tr>
			<tr>
			<td><input name="itemCode" type="text" size="9" id="icode" onkeydown = "return (event.keyCode!=13);" value="<%=itemcd%>" style="background-color:#ffffff;" onkeypress="return notEmpty(document.getElementById('date'), 'Please Select Date')"/></td>
			<td><input name="s"  id="s" value="S"  style="height: 20px; width: 25px" size="3" type="submit"  onkeyup="access();" /></td>
			<td><input name="itemName" type="text" size="5" id="iname" value="<%=itemNm%>"readonly="readonly" style="background-color:#ffffff;"/></td>
			<td><input name="categoryName" type="text" size="7" id="cname" value="<%=catNm%>" readonly="readonly" style="background-color:#ffffff;"/></td>
			<td><input name="bullionType" type="text" size="5" id="btype" value="<%=btype%>" readonly="readonly" style="background-color:#ffffff;"/></td>
			<!-- td><input name="karatInfo" type="text" id="kinfo" size="5" value="<%=kinfo%>" readonly="readonly" style="background-color:#ffffff;"/></td-->
			<td><input name="numberOfPieces" type="text" size="5" value="<%=pcs%>" readonly="readonly" style="background-color:#ffffff;"/></td><td><input name="grossWeight" type="text" size="5" id="grwt" value="<%=grosswt%>" readonly="readonly" style="background-color:#ffffff;"/></td>
			<td><input name="netWeight" type="text" size="8" value="<%=netwt%>"readonly="readonly" style="background-color:#ffffff;" /></td><td><input name="bullionRate" type="text" size="8" id="brate" value="<%=brate%>" style="background-color:#ffffff;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"
                onblur="ValidateAndFormatNumber(this)"/></td>
			<td><input name="stone" type="text" size="5" value="<%=stone%>" readonly="readonly" style="background-color:#ffffff;"/></td><td><input name="stoneCost" type="text" size="5" id="stoneCost" value="<%=stcost%>" readonly="readonly" style="background-color:#ffffff;"/></td>
			<td><input name="wastageByGrams" type="text" size="5" value="<%=wastegms%>" style="background-color:#ffffff;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"/></td><td><input name="mcByGrams" type="text" size="5" id="mcgrams" value="<%=mcgms%>" style="background-color:#ffffff;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" /></td>
			<td><input name="valueAdditionCharges" id="valueAdditionCharges" type="text" size="5" value="<%=va%>" readonly="readonly" style="background-color:#ffffff;"/></td><td><input name="amountAfterLess" type="text" size="7" value="<%=amtless%>" readonly="readonly" style="background-color:#ffffff; text-align:right"/></td>
			</tr>
			
			<tr>
			<td><input name="itemCode1" type="text" size="9" id="icode1" onkeydown = "return (event.keyCode!=13);" value="<%=itemcd1%>" style="background-color:#ffffff;" onkeypress="return notEmpty(document.getElementById('date'), 'Please Select Date')"/></td>
			<td><input name="s1"  id="s1" value="S"  style="height: 20px; width: 25px" size="3" type="submit" onkeyup="access1();" /></td>
			<td><input name="itemName1" type="text" size="5" id="iname1" value="<%=itemNm1%>" readonly="readonly" style="background-color:#ffffff;"/></td>
			<td><input name="categoryName1" type="text" id="cname1" size="7" value="<%=catNm1%>" readonly="readonly" style="background-color:#ffffff;"/></td>
			<td><input name="bullionType1" type="text" size="5" id="btype1" value="<%=btype1%>" readonly="readonly" style="background-color:#ffffff;"/></td>
			<!-- td><input name="karatInfo1" type="text" id="kinfo1" size="5" value="<%=kinfo1%>" readonly="readonly" style="background-color:#ffffff;"/></td -->
			<td><input name="numberOfPieces1" type="text" size="5" value="<%=pcs1%>" readonly="readonly" style="background-color:#ffffff;"/></td><td><input name="grossWeight1" type="text" size="5" id="grwt1" value="<%=grosswt1%>" readonly="readonly" style="background-color:#ffffff;"/></td>
			<td><input name="netWeight1" type="text" size="8" value="<%=netwt1%>" readonly="readonly" style="background-color:#ffffff;"/></td><td><input name="bullionRate1" type="text" size="8" id="brate1" value="<%=brate%>" style="background-color:#ffffff;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"/></td>
			<td><input name="stone1" type="text" size="5" value="<%=stone1%>" readonly="readonly" style="background-color:#ffffff;"/></td><td><input name="stoneCost1" type="text" size="5" id="stoneCost1" value="<%=stcost1%>" readonly="readonly" style="background-color:#ffffff;"/></td>
			<td><input name="wastageByGrams1" type="text" size="5" value="<%=wastegms1%>" style="background-color:#ffffff;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"/></td><td><input name="mcByGrams1" type="text" size="5" id="mcgrams1" value="<%=mcgms1%>" style="background-color:#ffffff;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"/></td>
			<td><input name="valueAdditionCharges1" type="text" size="5" value="<%=va1%>" readonly="readonly" style="background-color:#ffffff;"/></td><td><input name="amountAfterLess1" type="text" size="7" value="<%=amtless1%>" readonly="readonly" style="background-color:#ffffff; text-align:right"/></td>
			</tr>
			
			
			<tr>
			<td><input name="itemCode2" type="text" size="9" id="icode2" onkeydown = "return (event.keyCode!=13);" value="<%=itemcd2%>" style="background-color:#ffffff;" onkeypress="return notEmpty(document.getElementById('date'), 'Please Select Date')"/></td>
			<td><input name="s2"  id="s2" value="S"  style="height: 20px; width: 25px" size="3" type="submit" onkeyup="access2();" /></td>
			<td><input name="itemName2" type="text" size="5" id="iname2" value="<%=itemNm2%>" readonly="readonly" style="background-color:#ffffff;"/></td><td><input name="categoryName2" type="text" id="cname2" size="7" value="<%=catNm2%>" readonly="readonly" style="background-color:#ffffff;" /></td>
			<td><input name="bullionType2" type="text" size="5" id="btype2" value="<%=btype2%>" readonly="readonly" style="background-color:#ffffff;"/></td>
			<!-- td><input name="karatInfo2" type="text" id="kinfo2" size="5" value="<%=kinfo2%>" readonly="readonly"style="background-color:#ffffff;"/></td -->
			<td><input name="numberOfPieces2" type="text" size="5" value="<%=pcs2%>" readonly="readonly" style="background-color:#ffffff;"/></td><td><input name="grossWeight2" type="text" size="5" id="grwt2" value="<%=grosswt2%>" readonly="readonly" style="background-color:#ffffff;"/></td>
			<td><input name="netWeight2" type="text" size="8" value="<%=netwt2%>" readonly="readonly" style="background-color:#ffffff;"/></td><td><input name="bullionRate2" type="text" size="8" id="brate2" value="<%=brate%>" style="background-color:#ffffff;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"/></td>
			<td><input name="stone2" type="text" size="5" value="<%=stone2%>" readonly="readonly" style="background-color:#ffffff;"/></td><td><input name="stoneCost2" type="text" size="5" id="stoneCost2" value="<%=stcost2%>" readonly="readonly"style="background-color:#ffffff;"/></td>
			<td><input name="wastageByGrams2" type="text" size="5" value="<%=wastegms2%>" style="background-color:#ffffff;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"/></td><td><input name="mcByGrams2" type="text" size="5" id="mcgrams2" value="<%=mcgms2%>" style="background-color:#ffffff;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"/></td>
			<td><input name="valueAdditionCharges2" type="text" size="5" value="<%=va2%>" readonly="readonly" style="background-color:#ffffff;"/></td><td><input name="amountAfterLess2" type="text" size="7" value="<%=amtless2%>" readonly="readonly"style="background-color:#ffffff; text-align:right"/></td>
			</tr>
			
			<tr>
			<td><input name="itemCode3" type="text" size="9" id="icode3" onkeydown = "return (event.keyCode!=13);" value="<%=itemcd3%>" style="background-color:#ffffff;" onkeypress="return notEmpty(document.getElementById('date'), 'Please Select Date')"/></td>
			<td><input name="s3"  id="s3" value="S"  style="height: 20px; width: 25px" size="3" type="submit" onkeyup="access3();" /></td>
			<td><input name="itemName3" type="text" size="5" id="iname3" value="<%=itemNm3%>" readonly="readonly"style="background-color:#ffffff;"/></td>
			<td><input name="categoryName3" type="text" id="cname3" size="7" value="<%=catNm3%>" readonly="readonly" style="background-color:#ffffff;"/></td>
			<td><input name="bullionType3" type="text" size="5" id="btype3" value="<%=btype3%>" readonly="readonly"style="background-color:#ffffff;"/></td>
			<!-- td><input name="karatInfo3" type="text" id="kinfo3" size="5" value="<%=kinfo3%>" readonly="readonly" style="background-color:#ffffff;"/></td  --> 
			<td><input name="numberOfPieces3" type="text" size="5" value="<%=pcs3%>" readonly="readonly"style="background-color:#ffffff;"/></td><td><input name="grossWeight3" type="text" size="5" id="grwt3" value="<%=grosswt3%>" readonly="readonly" style="background-color:#ffffff;"/></td>
			<td><input name="netWeight3" type="text" size="8" value="<%=netwt3%>" readonly="readonly"style="background-color:#ffffff;"/></td><td><input name="bullionRate3" type="text" size="8" id="brate3" value="<%=brate%>" style="background-color:#ffffff;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"/></td>
			<td><input name="stone3" type="text" size="5" value="<%=stone3%>" readonly="readonly"style="background-color:#ffffff;"/></td><td><input name="stoneCost3" type="text" size="5" id="stoneCost3" value="<%=stcost3%>" readonly="readonly" style="background-color:#ffffff;"/></td>
			<td><input name="wastageByGrams3" type="text" size="5" value="<%=wastegms3%>" style="background-color:#ffffff;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"/></td><td><input name="mcByGrams3" type="text" size="5" id="mcgrams3" value="<%=mcgms3%>" style="background-color:#ffffff;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"/></td>
			<td><input name="valueAdditionCharges3" type="text" size="5" value="<%=va3%>" readonly="readonly" style="background-color:#ffffff;"/></td><td><input name="amountAfterLess3" type="text" size="7" value="<%=amtless3%>" readonly="readonly" style="background-color:#ffffff; text-align:right"/></td>
			</tr>
			
			<tr>
			<td><input name="itemCode4" type="text" size="9" id="icode4" onkeydown = "return (event.keyCode!=13);" value="<%=itemcd4%>" style="background-color:#ffffff;" onkeypress="return notEmpty(document.getElementById('date'), 'Please Select Date')"/></td>
			<td><input name="s4"  id="s4" value="S"  style="height: 20px; width: 25px" size="3" type="submit" onkeyup="access4();" /></td>
			<td><input name="itemName4" type="text" size="5" id="iname4" value="<%=itemNm4%>" readonly="readonly" style="background-color:#ffffff;"/></td><td><input name="categoryName4" type="text" id="cname4" size="7" value="<%=catNm4%>" readonly="readonly" style="background-color:#ffffff;"/></td>
			<td><input name="bullionType4" type="text" size="5" id="btype4" value="<%=btype4%>" readonly="readonly"style="background-color:#ffffff;"/></td>
			<!-- td><input name="karatInfo4" type="text" id="kinfo4" size="5" value="<%=kinfo4%>" readonly="readonly" style="background-color:#ffffff;"/></td -->
			<td><input name="numberOfPieces4" type="text" size="5" value="<%=pcs4%>" readonly="readonly"style="background-color:#ffffff;"/></td><td><input name="grossWeight4" type="text" size="5" id="grwt4" value="<%=grosswt4%>" readonly="readonly" style="background-color:#ffffff;"/></td>
			<td><input name="netWeight4" type="text" size="8" value="<%=netwt4%>" readonly="readonly"style="background-color:#ffffff;"/></td><td><input name="bullionRate4" type="text" size="8" id="brate4" value="<%=brate%>" style="background-color:#ffffff;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"/></td>
			<td><input name="stone4" type="text" size="5" value="<%=stone4%>" readonly="readonly"style="background-color:#ffffff;"/></td><td><input name="stoneCost4" type="text" size="5" id="stoneCost4" value="<%=stcost4%>" readonly="readonly" style="background-color:#ffffff;"/></td>
			<td><input name="wastageByGrams4" type="text" size="5" value="<%=wastegms4%>" style="background-color:#ffffff;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"/></td><td><input name="mcByGrams4" type="text" size="5" id="mcgrams4" value="<%=mcgms4%>" style="background-color:#ffffff;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"/></td>
			<td><input name="valueAdditionCharges4" type="text" size="5" value="<%=va4%>" readonly="readonly" style="background-color:#ffffff;"/></td><td><input name="amountAfterLess4" type="text" size="7" value="<%=amtless4%>" readonly="readonly"style="background-color:#ffffff; text-align:right"/></td>
			</tr>
			
<!--Subtotal-->			
<tr><td colspan="5">&nbsp;</td><td colspan="2">&nbsp;</td><td colspan="2">&nbsp;</td><td colspan="3">Sub Total </td><td colspan="3">
<input name="totalAmount" id="subtotal" onkeyup="sum();" value="<%=totAmount%>" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" 
STYLE="text-align:right" onblur="ValidateAndFormatNumber(this)"/></td></tr>
			
<!--vatpercentage-->			
<tr><td colspan="5">&nbsp;</td><td colspan="2">&nbsp;</td><td colspan="2">&nbsp;</td><td colspan="3">VAT Percentage </td><td colspan="3">
<input name="cst" id="vatpercentage" onkeyup="sum();" value="<%=cst%>" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"
STYLE="text-align:right" onblur="ValidateAndFormatNumber(this)"/></td></tr>

<!--Vat-->        
<tr><td colspan="5">&nbsp;</td><td colspan="2">&nbsp;</td><td colspan="2">&nbsp;</td><td colspan="3">VAT </td><td colspan="3">
<input name="vat" id="vat" onkeyup="sum();" value="<%=vat%>" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"
STYLE="text-align:right" onblur="ValidateAndFormatNumber(this)"/></td></tr>
		
<!--NetAmount-->		
<tr><td colspan="5">&nbsp;</td><td colspan="2">&nbsp;</td><td colspan="2">&nbsp;</td><td colspan="3">Net Amount </td><td colspan="3">
<input name="netAmount" id="netamount" value="<%=netAmount%>" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"
STYLE="text-align:right" onblur="ValidateAndFormatNumber(this)"/></td></tr>
		
<!--TradeDiscount-->			
<tr><td colspan="5">&nbsp;</td><td colspan="2">&nbsp;</td><td colspan="2">&nbsp;</td><td colspan="3">Trade Discount</td><td colspan="3">
<input name="totalLess"  id="tradediscount" onkeyup="sum();" value="<%=totLess%>" onkeypress="return ValidateNumberKeyPress(this, event);"  onkeyup="calculateLess();"
STYLE="text-align:right" onblur="ValidateAndFormatNumber(this)"/></td></tr>

<!--BillAmount  -->
<tr><td colspan="9">&nbsp;</td><td colspan="3">Bill Amount </td><td colspan="3">
<input name="billAmount" id="billamount" onkeyup="sum();" value="<%=billAmount%>" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"
STYLE="text-align:right" onblur="ValidateAndFormatNumber(this)"/></td></tr>
            
			<!-- tr><td colspan="9">&nbsp;</td><td colspan="3">Advance Paid</td><td colspan="3">
				<input name="advance"  value="<%=advance%>" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"
                STYLE="text-align:right" onblur="ValidateAndFormatNumber(this)"/></td></tr>
			<tr><td colspan="9">&nbsp;</td><td colspan="3">Balance Amount</td><td colspan="3">
				<input name="balanceAmount" value="<%=balanceAmount%>" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"
                STYLE="text-align:right" onblur="ValidateAndFormatNumber(this)"/></td></tr -->			
			</tr>
			
			<tr><td colspan="9">&nbsp;</td></tr>
			<tr><td colspan="9" bgcolor="#33FFFF" align="center">
			<input type="submit" name="insert" value="Insert" onclick="return notEmpty(document.getElementById('salesmanName'), 'Please Select Salesman Name');"/>
			<input type="submit" name="update" value="Update"/>
       		<input type="submit" name="cancel" value="cancel"></input>
			</td>
			</tr>			
	</table>

	</form:form>	
	
	</div>
	</div>
</div>
	
</div>

<div id="footer">
	<p id="legal">Copyright &copy; 2010 Auromine Solutions P. Ltd. All Rights Reserved.</p>
	<p id="links"><a href="#">Privacy Policy</a> | <a href="#">Terms of Use</a> | Powered by <a href="http://www.auromine.com/">Auromine Solutions</a>.</p>
</div>
</div>
</div>
</body>		

</html>