<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<!-- col-one -->	
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Account Groups</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<form:form method="post" action="formaccounts.htm" commandName="accountid">
	<form:errors path="*" cssStyle="color:red"/>
	<table id="formAccountGroups">
			<tr><td bgcolor="#33FFFF" width="180" colspan=2><B>Account Group Information</B></td></tr>
			<tr><td colspan=2>&nbsp;</td></tr>
			
			<tr><td  bgcolor="#BBDDFF" width="180">Account Name</td><td width="280"><form:input path="accountName" title="accountName"/></td></tr>
			<tr><td  bgcolor="#BBDDFF" width="180">Group Under</td>
				<td>
			<form:select path="accountGroup" title="accountGroup">
			<form:option value="0">-----select-----</form:option>
			<form:option value="Bank Account"></form:option>
			<form:option value="Bank Loan"></form:option>
			<form:option value="Bank OCC Account"></form:option>
			<form:option value="Capital Account"></form:option>
			<form:option value="Cash Account"></form:option>
			<form:option value="Current Assets"></form:option>
			<form:option value="Current Liabilities"></form:option>
			<form:option value="Direct Expenditure"> </form:option>
			<form:option value="Direct Income"></form:option>
			<form:option value="Fixed Assets"></form:option>
			<form:option value="Indirect Expenditure"></form:option>
			<form:option value="Indirect Income"></form:option>
			<form:option value="Profit & Loss Account"></form:option>	
			<form:option value="Purchase"> </form:option>
			<form:option value="Sales"></form:option>
			<form:option value="Stock Account"></form:option>
			<form:option value="Sundry Creditors"></form:option>
			<form:option value="Sundry Debtors"></form:option>
			</form:select>
			</td></tr>
			<tr><td  bgcolor="#BBDDFF" width="180">Closing Balance</td><td width="280"><form:input path="closingBalance" title="closingBalance"/></td></tr>
			<tr><td bgcolor="#BBDDFF" width="180">Type</td>
			<td width="280">
			<form:radiobutton path="closingType" value="credit"  />Credit &nbsp;&nbsp;&nbsp;
            <form:radiobutton path="closingType" value="debit"  />Debit &nbsp;&nbsp;&nbsp;
			</td></tr>
			<tr><td  bgcolor="#BBDDFF" width="180">Description</td><td width="280"><form:input path="remarks" /></td></tr>
			<tr><td colspan=2>GroupCode

			<form:input path="accountGroupCode"/>
			
</td></tr>
			
			<tr><td bgcolor="#33FFFF" colspan=2 align="center">
			<input type="submit" name="insert" value="Insert" /></input>
		<!--<input type="submit" name="update" value="Update"/></input>
          	<input type="submit" name="delete" value="Delete"/></input>-->
			<input type="submit" name="cancel" value= "cancel"></input>
			</td></tr>
		</table><!-- content -->
		
		</form:form>
	
	</div><!-- content -->
	</div><!-- boxed -->
</div><!-- col-form -->
</html>