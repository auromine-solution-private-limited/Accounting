 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Application Config</title>
</head>
<body>
<div id="container"><!-- Container Div Open -->
<!---------------------------------------->
	<div id="col-form" align="left"><!-- Form Div Open -->
		<div class="boxed"><!-- Boxed Div Open -->
			<div class="title">Application Config</div>	
			<div class="content"><!-- Content Div Open -->
				<%
			if(session.getAttribute("username")==null)
			{
			response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
			}
			%>
			<br />
			<form:form name="appConfig" method="POST" commandName="config" action="saveConfig.htm" class="formStyle">
				  <div id="form_error">
					<div><a class="close"><img src="images/closebox.png"></a></div><br>
					<span class="error"><form:errors path="*" id="errorDisplay"/></span>		
				</div> 
				
				<div class="summary">Application Config </div>
				<br />
				<table id="formLedger"  cellpadding="0" border="0" cellspacing="0" align="center">
					 <tr class="form_cat_head"><td colspan=4>Configuration Settings</td></tr>
					 <tr><td align="center">
						 <form:input path="configSetting.authorizer" hidden="true" />
						 <form:label path="listConfigDetail[0].ModuleName" value="Estimated Sales">Estimate Sales </form:label>
						 <form:radiobutton path="listConfigDetail[0].status" value="active" label="Active" />
						 <form:radiobutton path="listConfigDetail[0].status" value="inactive" label="In Active" />
						 <form:input path="listConfigDetail[0].ModuleCode" hidden="true" value="ES" />
						 </td>
					 </tr>
					 <tr><td align="center">
						 <form:input path="configSetting.authorizer" hidden="true" />
						 <form:label path="listConfigDetail[1].ModuleName" value="Estimate POS Sales">Estimate POS Sales </form:label>
						 <form:radiobutton path="listConfigDetail[1].status" value="active" label="Active" />
						 <form:radiobutton path="listConfigDetail[1].status" value="inactive" label="In Active" />
						 <form:input path="listConfigDetail[1].ModuleCode" hidden="true" value="EP" />
						 </td>
					 </tr>
					 
					 <tr><td align="center">
					 		<input type="submit" name="insert" value="Insert" id="insert" class="button_style"/>
							<input type="submit" name="cancel" value="Cancel" class="button_style" />
							<input type="hidden" id="mode" value="${mode}"> 
						 </td>
					</tr>
				</table>
			</form:form>
			</div> <!-- Content Div Close -->
		</div><!-- Boxed Div Close -->
	</div><!-- Form Div Close -->
</div><!-- Container Div Close -->
<script type="text/javascript">
$(document).ready(function(){
	if($("#mode").val()){
		$("#insert").hide();
	}
});
</script>
<script type="text/javascript" src="script/appConfig.js"></script>
</body>
</html>