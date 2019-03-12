<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<body>

<!---------------------------------------->
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title" >Create Refinery</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<c:url var="saveUrl" value="formRefinery.htm" />
	<form:form modelAttribute="refinery" action="${saveUrl}" method="post" class="formStyle">
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<table id="formRefinery" cellpadding="0" border="0" cellspacing="0" align="center">
	<tr class="form_cat_head"><td colspan="4">Refinery Information</td></tr>	
	
	<tr>
		<form:input path="refineryIssueNo" Type="hidden" id="refineryIssueNo" onkeypress="return ValidateNumberKeyPress(this, event);" />
		<td class="label">Refinery Name</td>
			<td>
				<form:select path="refinerySupplierName">
				<form:option value="" label="Select"></form:option>
				<c:forEach var="ledger" items="${suppliername}">
				<form:option  value="${ledger.ledgerName}">
				</form:option>
				</c:forEach>
				</form:select>
			</td>
		<td class="label">Refinery Date</td><td><form:input path="refineryDate" id="date"/></td>
	</tr>
	
		<tr>		
				<td class="label">Ornaments Type</td><td>
		
			<form:select path="ornamentsType" id="ornamentsType">	
			<form:option value="Select" label = "Select"></form:option>
			<form:option value="Old Silver Ornaments" label="Old Silver Ornaments"/>		
			<form:option value="Old Gold Ornaments" label="Old Gold Ornaments"/>							
			</form:select>
		</td>	
		<td class="label">Purity</td>	    
	    <td><form:input path="purity" id="purity" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit cal_dep"/></td>
		</tr>	
		
		<tr>		
	    <td class="label">Pieces</td>	
	    <td><form:input path="pieces" id="pieces" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit cal_dep"/></td>	
	    <td class="label">Gross Weight</td>
		<td><form:input path="grossWeight" id="grossWeight" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit cal_dep"/></td>	
  		</tr>	
  							
		<tr>		
		<td class="label">Net Weight</td>
		<td><form:input path="netWeight" id="RInetWeight" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit cal_dep"/></td> 	
		<td class="label">Narration</td><td><form:input path="narration"/></td>	
		<form:input type="hidden" path="refineryId"  />
		<form:input type="hidden" path="itemCode" id="riCode" />
		</tr>	
		<tr>
		    <td bgcolor="#33FFFF" colspan="4" align="center">
		    <input type="submit" name="insert" value="Insert" id="RefineryInsert" class="button_style"/>            
		    <input type="submit" name="update" value="Update" id="RefineryUpdate" class="button_style"/>            
          	<input type="submit" name="cancel" value= "Cancel" class="button_style"/></td>
        </tr>    
		
	</table>
	<input type="hidden" id="errorTypeRi" value="${errorName}">
	</form:form>
		</div>
	</div>
</div>
<script type="text/javascript" src="script/RefineryIssue.js"></script>
</body>		

</html>