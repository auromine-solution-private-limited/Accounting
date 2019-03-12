<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Jewel Mine</title>
<link rel="stylesheet" type="text/css" href="default.css" />
<script type="text/javascript" src="script/jsreport.js"></script>
<script type="text/javascript">
window.onload=function() {
setCursor(document.getElementById('userName'))
}
function setCursor(el) {
if(el.setSelectionRange) {
el.focus();
el.setSelectionRange();
}
else {
if(el.createTextRange) {
range=el.createTextRange();
range.collapse(true);
range.moveEnd('character');
range.moveStart('character');
range.select();
}
}
}
</script>
<script type="text/javascript">
	window.history.forward();
	function noBack(){ window.history.forward(); }
</script>
</head>

<body id="body" onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">

<div id="login">
    	<div id="log_bg">
            <div id="log">
            <div id="logo_1">
            </div>
            	<div id="logo_1">
    				<img src="images/logo.png" />	
            	</div>

<table width="270" border="0" cellspacing="0" cellpadding="0" align="center">
   <tr>
    					<td>&nbsp;<br /><br /></td>
				      <td>&nbsp;</td>
  					</tr>
                    
  					<tr>
    					<td>
    					<c:url var="saveUrl" value="authentic.htm"/>
<form:form modelAttribute="user" method="POST" action="${saveUrl}" onSubmit=" return logval();">
    					 
						<span class="label">Username  </span>
						
					<form:input type="text" id="userName" path="userName" style="margin-left:8px;"/>
						
   					<br />
					
				<span class="label">Password &nbsp;</span>
				
				<form:input type="password" id="password" path="password" style="margin-top:5px; margin-left:0px" />
			
      				<br/>
      				<label>
						<input type="image" name="login" src="images/button.png" id="button" value="LogIn" style="margin-top:15px; margin-left:135px"/>
                           
      				</label>	
					
					<label class="label" id="lblLV"> 
					<%
    if (request.getParameter("Logininfo") == null) {
} else {
out.println(request.getParameter("Logininfo"));
}
if (request.getParameter("login_invalid") == null) {
} else {
out.println(request.getParameter("login_invalid"));
}
if (request.getParameter("username_password") == null) {
} else {
out.println(request.getParameter("username_password"));
}
%>
					
					</label>
					
</form:form>   </td> 
			  
    						 <td>&nbsp;</td>
  					</tr>
			  </table>
            </div>
         </div>   
     </div>   
</body>
</html>

