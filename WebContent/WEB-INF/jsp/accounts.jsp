<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<body>
<head>
<script language="javascript"  type="text/javascript" type="text/javascript" src="script/xfade2.js"></script>
	<script language="javascript"  type="text/javascript"  type="text/javascript" src="script/search_form.js"></script>
	<script language="javascript"  type="text/javascript" src="script/NaNValidate.js"></script>
	<script language="javascript"  type="text/javascript" src="script/jquery-latest.js"></script>
</head>
<div id="col-one">
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>

<!-- ><div class="boxed">
			<div class="title">Board Rate</div>
			<div class="content">
			<c:forEach var="ratemaster" items="${boardrate}">
			<div class="boardrate" cellpadding="0" cellspacing="2">
			<tr>
				<td> <img src="images/gold.png" align="middle">&nbsp;</td><td><b>Gold:</b> Rs.${ratemaster.goldOrnaments}/gm</td>
			</tr>
			<br><br>
			<tr>	
				<td> <img src="images/silver.png" align="middle">&nbsp;</td><td><b>Silver:</b> Rs.${ratemaster.silverOrnaments}/gm</td>
           
			</tr>
        	</div>
			</c:forEach>
			</div>
</div>	
<div class="boxed">
			<div class="title">Dash Board</div>
			<div class="content">
			<form id="form2" method="get" action="#">
			<fieldset>	
			<legend>Search</legend>
					<b>Sales Order Pending<br><br>
					Pending SO:</b>${message}<br>					
					
					<p class="tiny"><a href="formledgerpo.htm">More Details...</a></p>
					<br>
					
					<b>Low Metal Balance<br><br>
					Low Metal:</b>${SLCount}<br>
					<p class="tiny"><a href="formledgerlist.htm">More Details...</a></p>
					<br>
			</fieldset>	
			</form>
			</div>		
	</div>
	
		<div class="boxed" align="left">
			<div class="title"align="left">Account Search</div>
			<div class="content">
				<form:form id="form4" method="get" commandName="Ledger" onsubmit="return validate_account();" action="searchledger.htm">
					<fieldset>
					<legend>Search</legend>
				 	Account Name (or) Id
					<input id="ledgerName"  name="ledgerName" value="" class="textfield" />
					<input type="submit" id="search" name="search" value="Search" class="button" />
					<label id="lblledgerName"></label>					
					</fieldset>					
			</form:form>			
			</div> 
	</div>	

	<div class="boxed" align="left">
			<div class="title">Admin</div>
			<div class="content">
				<form id="form3" method="get" action="#">
					<fieldset>					
				
					<tr>
					<td><p class="tiny"><a href = "ratemaster.htm" ><b>Rate Master</b></a></p></td>
					</tr><br>
					<tr>
					<td><p class="tiny"><a href = "alluser.htm" ><b>List Of User</b></a></p></td>
					</tr>					
					</fieldset>
				</form>
			</div>
	</div>
	
	
</div>-->

	
	
<div id="col-form">
	<div class="boxed">
		<div class="title">Group Account</div>
		<div class="content">
		<!-- <h3 align="right"><a href="formledger.htm">Ledgers</a></h3> -->
		<div class="summary">Account Group List</div>
		<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table" id="table_rowRemove">
		<thead class="row_head">		
		<tr>
			<th>Account Id</th>
			<th>Account Group</th>	
			<th>Closing Balance</th>
			<th>Closing Type</th>
			<th>Remarks</th>
		</tr>
	</thead>	
		<!--  <tr>
		<td bgcolor="#BBDDFF" >Account Id</td>
		<td bgcolor="#BBDDFF" >Account Group</td>
		<td bgcolor="#BBDDFF" >Closing Balance</td>
		<td bgcolor="#BBDDFF" >Account Type</td>
		<td bgcolor="#BBDDFF" >Remarks</td>
		</tr>	-->				
		<c:forEach var="accounts" items="${accountsList}">
		<tr bgcolor="#FFFFFF">
		<!-- td><a href="formaccounts.htm?accountId=${accounts.accountId}">${accounts.accountId}</a></td -->	
		<td>${accounts.accountId}</td>				   
		<td><a href="accountsledger.htm?accountId=${accounts.accountId}" class="a_link" title='Click to View "${accounts.accountGroup}" List'>${accounts.accountGroup}</a></td>
		<td align="right">${accounts.closingBalance}</td>
		<td>${accounts.closingType}</td>
		<td>${accounts.remarks}</td>
		</tr>
		</c:forEach>
		<!-- tr bgcolor="#FFFFFF">
			<td colspan="8"><a href="formaccounts.htm">New Group Account</a></td>
			</tr --->
		</table>	
		</div> <!-- content -->
	</div>  <!-- boxed -->
</div> <!-- col-form -->

<script type="text/javascript" src="script/jquery.autocomplete.js"></script>	
<script language="javascript"  type="text/javascript" src="script/ledger_Name_Auto.js"></script>
</body>		
