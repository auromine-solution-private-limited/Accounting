<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<body>	
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">userlogin </div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<c:url var="saveUrl" value="/edituser.htm?id=${user.id}" />
	<form:form modelAttribute="user" method="POST" action="${saveUrl}" class="formStyle">
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<table id="formuserlogin" width="50%" cellpadding="0" border="0" cellspacing="0" align="center" >
			<tr class="form_cat_head"><td colspan=4>User Group Information</td></tr>	
	
			<tr>
			<td class="label">User Name</td>
			<td width="280"><form:input path="userName" id="username"/></td>
			<td class="label">Password</td>
			<td width="280"><form:password path="password" showPassword="true"/></td>
			</tr>
			<tr>
			<td class="label">Confirm Password</td>
			<td width="280"><form:password path="confirmPassword" showPassword="true"/></td>
			<td class="label">Full Name</td>
			<td width="280"><form:input path="fullName" /></td>
			</tr>
			<tr>
			<td class="label">Email-Id</td>
			<td width="280"><form:input path="emailId" id="emailid" onblur="emailValidator(document.getElementById('emailId'), 'Enter Valid Email')"/></td>
			<td class="label"> User Roll</td>	
            <td width="280">
	        <form:select path="rollName" >
			<form:option value="" label="Select"/>
			<form:option value="Admin" label="Admin"/>
			<form:option value="User" label="User"/>
			<form:option value="Manager" label="Manager"/>
			<form:option value="SalesMan" label="SalesMan"/>
			</form:select>
			</td>
            </tr>
            
			<tr> 
			<td class="label"> Active</td>	
            <td width="280">
	        <form:select path="active" id="userActive">			
			<form:option value="Enable" label="Enable"/>
			<form:option value="Disable" label="Disable"/>
			</form:select>
			</td>
			<td class="label"></td>
			<td></td>
			</tr>
           
			<tr><td bgcolor="#33FFFF" colspan=4 align="center">
			<input type="submit" id="update" name="edit" value="Update"  class="button_style"/></input>
			<input type="submit" name="cancel" value= "Cancel" class="button_style"></input>
			</td></tr>
		</table><!-- content -->
		<h3><b><u><a href="alluser.htm">Back to User List</a></u></b></h3>
		</form:form>	
	</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('#username').focus();
	$('#update').click(validateEmail);
	
	var no = null;
	no =  $.getUrlVar('id');

	if(no != null) {
		if(no == 1){
				// var userActiveVal = $("#userActive").val();
				$("#userActive option[value=Disable]").remove();
			}
		
	}
});

$.extend({
	  getUrlVars: function(){
	    var vars = [], hash;
	    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	    for(var i = 0; i < hashes.length; i++)
	    {
	      hash = hashes[i].split('=');
	      vars.push(hash[0]);
	      vars[hash[0]] = hash[1];
	    }
	    return vars;
	  },
	  getUrlVar: function(name){
	    return $.getUrlVars()[name];
	  }
});
function validateEmail(){
	var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	var emailaddressVal = $("#emailid").val();
		$("#form_error").find('#ss').remove();
	if(!emailReg.test(emailaddressVal)) {
      	$("#form_error").slideDown("fast").append('<span class="error" id="ss">Please Enter valid Email id</span>');
       return false;
    }
	return true;
}

</script>
</body>		
</html>