<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Saving Scheme Form</title>
</head>
<body>

<div id="container"><!-- Container Div Open -->
<!---------------------------------------->
<div id="col-form" align="left"><!-- Form Div Open -->
	<div class="boxed"><!-- Boxed Div Open -->
	<div class="title">Create Saving Scheme Form</div>	
	<div class="content"><!-- Content Div Open -->
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	<c:url var="saveUrl" value="formsavingscheme.htm"/>
	<form:form modelAttribute="saving_scheme" action="${saveUrl}" method="post" class="formStyle">
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>		
	</div> 
	
	<table id="formsavingscheme"  cellpadding="0" border="0" cellspacing="0" align="center">
		 <tr class="form_cat_head"><td colspan=4>Saving Scheme Creation</td></tr>
		<tr>
		<td class="label">Scheme Name</td>
		<td><form:input path="schemeName" id="SchemeName" class="nameCapitalize"/></td>
		<td class="label"> Scheme Type</td>		
		<td><form:select path="schemeType" id="SchemeTypeSav" >
			<form:option value="" label="Select"></form:option>
			<form:option value="Amount">Amount</form:option>
			<form:option value="Gold">Gold</form:option>
		</form:select></td>
		</tr>
		<tr>
		<td class="label SchemeAmt">Scheme Amount</td>
		<td class="SchemeAmt"><form:input path="schemeInAmount" id="SchemeAmount" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td class="label SchemeGms"> Scheme Grams</td>
		<td class="SchemeGms"><form:input path="schemeInGrams" id="SchemeGrams" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td class="label"></td>
		<td></td>
		</tr>
		
		<form:input type="hidden" path="saving_schemeId"/>
		<input type="hidden" id="errorName" value="${errorType}"/>
		<input type="hidden" id="exist_scheme" value="${existingSchemeName}"/>
		
		<tr><td colspan="4" align="center">
          	<input type="submit" name="insert" value="Insert" id="insert" class="button_style"/></input>
          	<input type="submit" name="update" value="Update" id="update" class="button_style"/></input>
			<!-- <input type="submit" name="delete" value="Delete" id="delete"/></input> -->
        	<input type="submit" name="cancel" value="Cancel" class="button_style"></input>
         	</td>
        </tr>
	</table>	
	</form:form>
	
			</div> <!-- Content Div Close -->
		</div><!-- Boxed Div Close -->
	</div><!-- Form Div Close -->
</div><!-- Container Div Close -->
<script language="javascript"  type="text/javascript">

	$(document).ready(function() {
		
		var URIloc = $(location).attr('href');
		var uripath = URIloc.split('/')[4];			
		var errorType=$("#errorName").val();
			
		if(errorType=="insertError"){		
			$('#insert').show();
			$('#update').hide();
		}
		
		if(errorType=="updateError"){	
			$('#insert').hide;
			$('#update').show();
		}		
		 
		if(uripath.indexOf("addnewscheme.htm") >= 0) {						
			$("#insert").show();
			$('#update').hide();		
		}		
		
		if(uripath.indexOf("viewsavingscheme.htm") >= 0){	
			
			$("#insert").hide();			
			if($('#exist_scheme').val()!="empty")
			{
				$("#update").hide();	
			}
			else
				$('#update').show();
			
		}
		
		 $("#SchemeName").focus();
		 $("#SchemeName").attr("maxlength","30");
		 $("#SchemeAmount").attr("maxlength","10");
		 $("#SchemeGrams").attr("maxlength","10");
		 $('#insert').click(init);
		 $('#update').click(init);
});		
	
	function init(){
		
		var ZERO = 0;
		if($("#SchemeAmount").val().length == 0){
			$("#SchemeAmount").val(ZERO.toFixed(2));			
		}
		if($("#SchemeGrams").val().length == 0){
			$("#SchemeGrams").val(ZERO.toFixed(3));			
		}
	}
</script>		
</body>
</html>