<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<body>	
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Add a Rate</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<form:form commandName="ratemaster" action="formratemaster.htm" method="post" class="formStyle">
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"/></a></div><br>
		<span class="error" id="pos"><form:errors path="*" id="errorDisplay"/></span>
	</div>
	<table id="formratemaster" cellpadding="0" border="0" cellspacing="0" align="center" >
			<tr class="form_cat_head"><td colspan=4><B>Rate Master Information</B></td></tr>
			<tr>
				<td class="label">Gold 22karat (R)*</td><td width="280"><form:input path="goldOrnaments" id="goldOrnaments" maxlength="15" onkeypress="return ValidateNumberKeyPress(this, event);"onkeyup="extractNumber(this,2,true);"/></td>
				<td class="label">Silver 1gm (R)*</td><td width="280"><form:input path="silverOrnaments" id="silverOrnaments" maxlength="15" onkeypress="return ValidateNumberKeyPress(this, event);"onkeyup="extractNumber(this,2,true);"/></td>
			</tr>
			<tr>
				<td class="label">Exchange Gold</td><td width="280"><form:input path="exchangeGold" id="exchangeGold" maxlength="15" onkeypress="return ValidateNumberKeyPress(this, event);"onkeyup="extractNumber(this,2,true);"/></td>
				<td class="label">Exchange Silver</td><td width="280"><form:input path="exchangeSilver" id="exchangeSilver" maxlength="15" onkeypress="return ValidateNumberKeyPress(this, event);"onkeyup="extractNumber(this,2,true);"/></td>
			</tr>
			<tr>
				<td class="label">Bullion Gold 1gm (W)</td><td width="280"><form:input path="goldBullion" id="goldBullion" maxlength="15" onkeypress="return ValidateNumberKeyPress(this, event);"onkeyup="extractNumber(this,2,true);"/></td>
				<td class="label">Bullion Silver 1gm (W)</td><td width="280"><form:input path="silverBullion" id="silverBullion" maxlength="15" onkeypress="return ValidateNumberKeyPress(this, event);"onkeyup="extractNumber(this,2,true);"/></td>
			</tr>
  			<tr>
  				<td class="label">LastUpdate Date</td><td width="280"><form:input path="lastUpdateDate" id="date" readonly="true"/></td>
  				<td class="label"></td><td></td>  				
  			<form:input type="hidden" path="ratemasterId"/>
  			<input type="hidden" id="errorName" value="${rateErrorType}" />	
  			
  			</tr>

			<tr><td bgcolor="#33FFFF" colspan=4 align="center">
			<input type="submit" name="insert" value="Insert" id="insert" class="button_style"/></input>
			<input type="submit" name="update" value="Update" id="update" class="button_style"/></input>
          	<input type="submit" name="cancel" value= "cancel" class="button_style"></input>
			</td></tr>
		</table>
		</form:form>
	
	</div>
	</div>
</div>

<script type="text/javascript">

var URIloc = $(location).attr('href');
var uripath = URIloc.split('/')[4];

var errorType = $("#errorName").val();

$(document).ready(function(){
	
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var hour   = myDate.getHours();
	var minute = myDate.getMinutes();
	var second = myDate.getSeconds();
	var ap = "AM";
	
	if (hour   > 11) { ap = "PM";             }
	if (hour   > 12) { hour = hour - 12;      } 
	if (hour   == 0) { hour = 12;             }
	if (hour   < 10) { hour   = "0" + hour;   }
	if (minute < 10) { minute = "0" + minute; }
	if (second < 10) { second = "0" + second; }
	var timeString = hour +':' +minute +':' +second +" " +ap;
	var prettyDate = myDate.getDate()+ '-' + month + '-' +myDate.getFullYear()+" "+timeString;
	$("#date").val(prettyDate);
	

	if(uripath.indexOf("viewratemaster.htm") >=0)
	{		
		$("#insert").hide();
		$("#update").show();		
	}
	
	if(uripath.indexOf("formratemaster.htm") >= 0)
	{
		$("#update").hide();
		$("#insert").show();
	}
	
	if(errorType == "insertError")
	{	
		$("#update").hide();
		$("#insert").show();			
	}
	else if(errorType == "updateError")
	{			
		$("#insert").hide();
		$("#update").show();		
	}
	
	$("#insert").click(initZero);
	$("#goldOrnaments").focus();

});

function initZero(){
	
	var ZERO = 0;
	
	if($("#goldOrnaments").val().length == 0){
		$("#goldOrnaments").val(ZERO.toFixed(2));
	}
	
	if($("#silverOrnaments").val().length == 0){
		$("#silverOrnaments").val(ZERO.toFixed(2));
	}
		
	if($("#exchangeGold").val().length == 0){
		$("#exchangeGold").val(ZERO.toFixed(2));
	}
	
	if($("#exchangeSilver").val().length == 0){
		$("#exchangeSilver").val(ZERO.toFixed(2));
	}
	
	if($("#goldBullion").val().length == 0){
		$("#goldBullion").val(ZERO.toFixed(2));
	}

	if($("#silverBullion").val().length == 0){
		$("#silverBullion").val(ZERO.toFixed(2));
	}	
}

</script>
</body>	
</html>