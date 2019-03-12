<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Start Scheme Form</title>
</head>
<body>

<div id="container"><!-- Container Div Open -->
<!---------------------------------------->
<div id="col-form" align="left"><!-- Form Div Open -->
	<div class="boxed"><!-- Boxed Div Open -->
	<div class="title">Close Scheme Form</div>	
	<div class="content"><!-- Content Div Open -->
	<%
if(session.getAttribute("username")==null)
{
	response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
		<c:url var="saveUrl" value="formclosesavingscheme.htm"/>
		<form:form modelAttribute="start_scheme" action="${saveUrl}" method="post" class="formStyle">
	
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>		
	</div> 
	
	<table id="formLedger"  cellpadding="0" border="0" cellspacing="0" align="center">
		 <tr class="form_cat_head"><td colspan=4>Close Scheme</td></tr>
	
		<tr>		
		<td class="label">Scheme Name</td>
		<td><form:input path="schemeName" id="SchemeName" readonly="true"/>
		<td class="label"></td>
		<td></td>
		</tr>
		<tr style="display:none;">	
		<td class="label"> Scheme Type</td>
		<td><form:input path="schemeType" id="schemeType" readonly="true"/></td>
		<td class="label SchemeAmt">Scheme Amount</td>
		<td class="SchemeAmt"><form:input path="schemeInAmount" id="SchemeInAmount" readonly="true"/></td>
		<td class="label SchemeGms"> Scheme Grams</td>
		<td class="SchemeGms"><form:input path="schemeInGrams" id="SchemeInGrams" readonly="true"/></td>
		</tr>
		<tr style="display:none;">	
		<td class="label">Scheme Duration</td>
		<td><form:input path="schemeDuration" id="SchemeDuration" style="width:50px;" readonly="true"/> in months</td>
		<td class="label"> Scheme Start Date</td>
		<td><form:input path="schemeStartDate" id="scs_startdate" readonly="true"/></td>
		</tr>		
		<tr class="status">
		<td class="label">Scheme Status</td>
		<td>
		<form:select path="schemeStatus" id="schemeStatus">
		<form:option value="Close">Close</form:option>
		</form:select></td>
		<td class="label"></td> 
		<td></td>
		</tr>
		<form:input type="hidden" path="start_schemeId"/>
		<input type="hidden" id="errorName" value="${errorType}"/>		
		<tr><td colspan="4" align="center">         
       	<input type="submit" name="update" value="update" id="update" onclick="return schemedatevalidation();" class="button_style" /></input>	
       	<input type="submit" name="cancel" value= "Cancel" class="button_style"></input>
       	</td>
        </tr> 
	</table> 
	
	</form:form>	
	
	</div><!-- Content Div Close --> 
	</div><!-- Boxed Div Close -->
	</div><!-- Form Div Close -->
</div><!-- Container Div Close -->


<script type="text/javascript">

function changebuttonname(){
	var URIloc = $(location).attr('href');
	   var uripath = URIloc.split('/')[4];

	   if(uripath.indexOf("closestartscheme.htm") >= 0 ){
	   	$('#update').val("Close Scheme");
	   }
}


function schemedatevalidation() {
	
	$("#scs_startdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	var myDate = new Date();
	var scheme_duration = $("#SchemeDuration").val();
	
	var start_date = new Date();
	start_date = $("#scs_startdate" ).datepicker({dateFormat:"yy/mm/dd"}).datepicker("getDate",new Date());		
		
	var diff = new Date(myDate-start_date);
	
	var monthDiff = diff/2592000000;
	var modDiff = parseInt(monthDiff);
	
    //alert("modDiffs :"+modDiff);
    
	if(modDiff < scheme_duration)	
	{		  	
	  	$("#form_error").slideDown("fast");
	  	$("#form_error span").text("The Scheme Cannot be Closed Now");
	  	return false;		
	}
	else{
		return true;
	}
	
}
</script>
</body>

</html>