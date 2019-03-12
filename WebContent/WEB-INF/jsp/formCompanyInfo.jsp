<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery-ui.min.js"></script>
<script type="text/javascript" src="script/NaNValidate.js"></script>
<body>	
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">COMPANY SETTINGS</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<form:form commandName="companyinfo" action="saveCompanyInfo.htm" method="post" class="formStyle">
	<form:errors path="*" cssStyle="color:red"/>
	<div id="pos"></div>
	<table id="formcompanyinfo" width="400px" cellpadding="0" border="0" cellspacing="0" align="center" >
			<tr class="form_cat_head"><td colspan=2><B> COMPANY INFORMATION FORM </B></td></tr>
			<tr>
				<td class="label"> Company Name * </td>
				<td width="380"><form:input path="companyName" id="company_name" class="nameCapitalize" onkeydown = "return (event.keyCode!=13);"/></td>
			</tr>
			<tr>
				<td class="label"> Company Address * </td>
				<td width="380"><form:textarea path="companyAddress" col="3" row="2" id="company_address" onkeydown = "return (event.keyCode!=13);"></form:textarea></td>
			</tr>			
			<tr>
				<td class="label"> City </td>
				<td width="380"><form:input path="city" id="city" onkeydown = "return (event.keyCode!=13);"/></td>
			</tr>
			<tr>
				<td class="label"> Country </td>
				<td width="380"><form:input path="country" id="country" onkeydown = "return (event.keyCode!=13);"/></td>
			</tr>
			<tr>
				<td  class="label"> PinCode </td>
				<td width="380"><form:input path="pincode" id="pincode" onkeypress="return ValidateNumberKeyPress(this, event);" onkeydown = "return (event.keyCode!=13);"/></td>
			</tr>
			<tr>
				<td class="label"> Phone </td>
				<td width="380"><form:input path="phone" id="phone" onkeypress="return ValidateNumberPhone(this, event);" onkeydown = "return (event.keyCode!=13);"/></td>
			</tr>
			<tr>
				<td class="label"> Fax </td>
				<td width="380"><form:input path="fax" id="fax" onkeypress="return ValidateNumberKeyPress(this, event);" onkeydown = "return (event.keyCode!=13);" /></td>
			</tr>
			<tr>
				<td class="label"> PAN Number </td>
				<td width="380"><form:input path="PanNumber" onkeydown = "return (event.keyCode!=13);"/></td>
			</tr>
			<tr>
				<td class="label"> TIN Number </td>
				<td width="380"><form:input path="TinNumber" onkeydown = "return (event.keyCode!=13);" /></td>
			</tr>
			<tr>
				<td class="label"> CST Number </td>
				<td width="380"><form:input path="CSTNumber" onkeydown = "return (event.keyCode!=13);" /></td>
			</tr>
			<tr>
				<td class="label"> Web site </td>
				<td width="380"><form:input path="website" onkeydown = "return (event.keyCode!=13);" /></td>
			</tr>
  				  			
  			<tr>
  				<td class="label"> Email-id </td>
  				<td width="380"><form:input path="C_email" id="C_email" onkeydown = "return (event.keyCode!=13);" /></td>
  				
  			</tr>
  			<tr>
  				<td class="label"> Reference </td>
  				<td width="380"><form:input path="Reference" onkeydown = "return (event.keyCode!=13);"  /></td>
  			</tr>
  			<tr>
  				<td class="label"> POS Barcode Print Format </td>
  				<td width="380">
  				<form:select path="posBarcodePrint" id="posBarcodePrint" onkeydown = "return (event.keyCode!=13);"  >
					<%-- <form:option value="Laser3x24" label="Laser Printer 3 x 24 "></form:option>
					<form:option value="Laser3x30" label="Laser Printer 3 x 30 "></form:option>
					<form:option value="Laser4x48" label="Laser Printer 4 x 48 "></form:option>
					<form:option value="Laser5x65" label="Laser Printer 5 x 65 "></form:option> --%>
					<form:option value="TSC" label="TSC Printer Format"></form:option>
					<form:option value="Citizen" label="Citizen Printer Format"></form:option>
					<form:option value="Zebra" label="Zebra Printer Format"></form:option>				
				</form:select></td>
  			</tr>
  			<tr>
  				<td class="label"> Ornament Barcode Print Format </td>
  				<td width="380">
  				<form:select path="ornBarcodePrint" id="ornBarcodePrint" onkeydown = "return (event.keyCode!=13);"  >
  					<form:option value="Argox" label="Argox Printer Format"></form:option>
  					<form:option value="TSC" label="Godex Printer Format"></form:option>
  					<form:option value="Citizen" label="Citizen Printer Format"></form:option>					
					<form:option value="Zebra" label="Zebra Printer Format"></form:option>				
				</form:select></td>
  			</tr>
  			<tr>
  				<td class="label"> POS Invoice Print Format </td>
  				<td width="380">
  				<form:select path="posInvoicePrint" id="posInvoicePrint" onkeydown = "return (event.keyCode!=13);"  >
					<form:option value="PDF" label="PDF Format"></form:option>
					<form:option value="Citizen3" label="Citizen 3 Inch Format"></form:option>
					<form:option value="dotMatrix4" label="Dot Matrix 4 Inch Format"></form:option>
					<form:option value="dotMatrix8" label="Dot Matrix 8 Inch Format"></form:option>
				</form:select></td>
  			</tr>
  			<tr>
  				<td class="label"> Sales Invoice Print Format on Save </td>
  				<td width="380">
  				<form:select path="ornInvoicePrint" id="ornInvoicePrint" onkeydown = "return (event.keyCode!=13);"  >
					<form:option value="PDF" label="PDF Format"></form:option>
					<form:option value="dotMatrix4" label="Dot Matrix 4 Inch Format"></form:option>					
					<form:option value="dotMatrix8" label="Dot Matrix 8 Inch Format"></form:option>
					
				</form:select></td>
  			</tr>
			<tr><td bgcolor="#33FFFF" colspan=2 align="center">
				<input type="submit" name="insert" value="Submit" id="insert" class="button_style"/></input>
				<input type="submit" name="update" value="Update" id="update" class="button_style"/></input>
				<input type="submit" name="cancel" value= "Back" id="cancel" class="button_style"></input></td>				
				</tr>
				<!-- form:input path="companyId" type="hidden" /-->
		</table>
		
		
		</form:form>
	
	</div>
	</div>
</div>
<script language="javascript" type="text/javascript" > 

	$(document).ready(function() {	
		
		var URIloc = $(location).attr('href');
		var uripath = URIloc.split('/')[4];
		
		if(uripath.indexOf("viewCompanyInfo.htm") >= 0){
			$('#insert').hide();	
			$('#update').show();	
			$("#company_name").attr('readonly',true);
		}else if(uripath.indexOf("CompanyDetail.htm") >= 0){
			$('#insert').show();
			$('#update').hide();
			$("#company_name").attr('readonly',false);
		}
		    $('#insert').click(validate_email);		
	});
	
	function validate_email() {  

        $(".error").hide();
        var hasError = false;
        var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
        var emailaddressVal = $("#C_email").val();
        
        /*if(emailaddressVal == '') {
            $("#C_email").after('<span class="error">Enter email address.</span>');
            hasError = true;
        }else*/
        if($("#company_name").val()==''){
        	$("#company_name").after('<span class="error"> * Enter Company Name.');
        	hasError=true;        	
        }
        if($("#company_address").val()==''){
        	$("#company_address").after('<span class="error"> * Enter Company Address.');
        	hasError = true;
        }
        if (emailaddressVal != '') {
        	if(!emailReg.test(emailaddressVal)) {
            $("#pos").html('<span class="error"> Invalid email address.</span>');
            hasError = true;
        }
        	}

        if(hasError == true) { return false; }
    }
	</script>
</body>	
</html>