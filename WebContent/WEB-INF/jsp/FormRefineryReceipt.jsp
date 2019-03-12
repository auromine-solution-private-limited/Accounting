<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<body>

<!---------------------------------------->
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title" >Create Refinery Receipt</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<c:url var="saveUrl" value="FormRefineryReceipt.htm" />
	<form:form modelAttribute="refineryReceipt" action="${saveUrl}" method="post" class="formStyle">
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<table id="formRefineryReceipt" cellpadding="0" border="0" cellspacing="0" align="center">
	<tr class="form_cat_head"><td colspan="4">Refinery Receipt Information</td></tr>	
	
	<tr>
		<form:input path="refineryReceiptNo" Type="hidden" id="refineryReceiptNo" onkeypress="return ValidateNumberKeyPress(this, event);" />
		<td class="label">Refinery Name</td>
			<td>
				<form:select path="rrName">
				<form:option value="Select" label="Select"></form:option>
				<c:forEach var="ledger" items="${suppliername}">
				<form:option  value="${ledger.ledgerName}">
				</form:option>
				</c:forEach>
				</form:select>
			</td>
		<td class="label">Refinery Date</td><td><form:input path="rrDate" id="rrdate"/></td>
	</tr>
	
		<tr>		
				<td class="label">Ornaments Type</td><td>
		
			<form:select path="rrOrnamentsType" id="rrornamentsType">	
			<form:option value="Select" label = "Select"></form:option>
			<form:option value="Gold Bullion" label="Gold Bullion"/>	
			<form:option value="Silver Bullion" label="Silver Bullion"/>							
			</form:select>
		</td>	
		<td class="label">Purity</td>	    
	    <td><form:input path="rrpurity" id="rrpurity" class="noEnterSubmit cal_dep" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		</tr>	
		
		<tr>		
	    <td class="label">Pieces</td>	
	    <td><form:input path="rrpieces" id="rrpieces" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit cal_dep"/></td>	
	    <td class="label">Gross Weight</td>
		<td><form:input path="rrGrossWeight" id="RRGrossWeight" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit cal_dep"/></td>	
  		</tr>	
  							
		<tr>		
		<td class="label">Net Weight</td>
		<td><form:input path="rrNetWeight" id="RRNetWeight"class="noEnterSubmit cal_dep"/></td> 	
		<form:input type="hidden" path="rrId"  />
		<form:input type="hidden" path="rritemcode" id="rritemcode" />
		<td class="label">Labour Charge</td>
		<td><form:input path="labourCharge" id="labourCharge" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit cal_dep"/></td>
		</tr>	
		<tr>
		<td class="label">Narration</td><td><form:input path="rrNarration"/></td>	
		<td></td> 
		<td></td> 
		</tr>
		<tr>
		    <td bgcolor="#33FFFF" colspan="4" align="center">
		    <input type="submit" name="insert" value="Insert" id="RRInsert" class="button_style"/>            
		    <input type="submit" name="update" value="Update" id="RRUpdate" class="button_style"/>            
          	<input type="submit" name="cancel" value= "Cancel" class="button_style"/></td>
        </tr>    
		
	</table>
	<input type="hidden" id="errorType" value="${errorName}">
	</form:form>
		</div>
	</div>
</div>
<script type="text/javascript" src="script/RefineryReceipt.js"></script>
</body>		

</html>