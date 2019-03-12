<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<script language="javascript"  type="text/javascript" src="script/jquery-latest.js"></script> 
	<script type="text/javascript" src="script/jquery.bgiframe.min.js"></script>
	<script type="text/javascript" src="script/jquery.dimensions.js"></script>
	<script type="text/javascript" src="script/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="ui.all.css" type="text/css"/>
	<link rel="stylesheet" href="default.css" type="text/css"></link>
</head>
<body id="PagePrdInfo">
<form:form method="POST" action="productInstall.htm" commandName="productinfo">
<div id="ContainerPrdInfo">
	<div id="PageHeader">
	<img src="images/logo.png" alt="JewelMine" align="middle"/> <br/>
	Product Installation Information</div>
	
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span> 
	</div>
	<table cellpadding="0" border="0" cellspacing="0" align="center" class="productinfoTable">
		<tr>
			<td class="label">Product Name *</td>
			<td><form:input path="productName" readonly="true" value="${productName}"/></td>
		</tr>
		<tr>
			<td>Product Version *</td>
			<td><form:input path="productversion" readonly="true" value="${productversion}"/></td>
		</tr>
		<tr>
		<td>Segment</td>
			<td>
			<form:select path="segmentTypeS">		
				<form:option value="Lower Segment" label="Lower Segment"/>
				<form:option value="Middle Segmment" label="Middle Segmment"/>
				<form:option value="Higher Segmment" label="Higher Segmment"/>
			</form:select>
			</td>
		</tr>
		<tr class="TVersionUpdate">
			<td>Version Type</td>
			<td>
				<form:radiobutton path="versionType" class="versionType" id="TVersion" value="Trial Version" label="Trial Version" />
				<form:radiobutton path="versionType" class="versionType" id="FVersion" value="Full Version" label="Full Version" />
			</td>
		<tr>
			<td>Installation Date*</td>
			<td><form:input path="installationDate" id="sysdate" value="${CurrentDate}"/></td>
			<form:hidden path="lastUsedDate" id="lastDate"/>
		</tr>
		<tr class="fullVersion">
			<td>Registering Company Name*</td>
			<td><input type="text"  maxlength="60" id="registerCompany" name="registerCompany" value="${registerCompany}">
				<input type="submit" id="generatePrivateKeys" name="generatekeys" value="GenerateKeys"></td>
		</tr>
		
		<tr class="fullVersion keyFields">
			<td>Private Key 1 *</td>
			<td><form:input path="private1" id="private1" value="${private1}"/></td>
		</tr>
		<tr class="fullVersion keyFields">
			<td>Private Key 2 *</td>
			<td><form:input path="private2" id="private2" value="${private2}"/></td>
		</tr>
		<tr class="fullVersion keyFields">
			<td>Product License Key *</td>
			<td><form:input path="plk" id="finalkey"/></td>
		</tr>
		<tr class="keyFields" >
			<td colspan="2">
			<input type="submit" name="Register" id="Register" value="Register" />
			<input type="hidden" name="loadingMode" id="loadingMode" value="${loadingMode}" />
			<form:input type="hidden" path="productVersionId" />
			</td>
		</tr>
		<tr>
		<td><a href="productinfo.htm" >Reset</a></td>
		</tr>
	</table>
	</div>
	</form:form>
	<script type="text/javascript" src="script/jquery-all-manuscript.js"></script>
	<script>
	$(document).ready(function(){
			$("#sysdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
			var myDate = new Date();
			var month = myDate.getMonth() + 1;
			var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
			$("#sysdate").val(prettyDate);
			$("#lastDate").val(prettyDate);
	});
	</script>
	<script type="text/javascript" src="script/productInfo.js"></script>
</body>
</html>