<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="default.css" type="text/css"></link>
<link rel="stylesheet" href="ui.all.css" type="text/css"/>
<link rel="stylesheet" href="general.css" type="text/css" media="screen" />
<base href="${pageContext.request.contextPath}/">
<meta http-equiv="container_tab-Type" container_tab_tab="text/html; charset=UTF-8" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Jewel Mine</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<script type="text/javascript" src="script/NaNValidate.js"></script>
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery-ui.min.js"></script>
<script type="text/javascript" src="script/search_form.js"></script>
		
</head>
<body>
<div id="container">
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
<div id="col-one" align="left">
	<div class="boxed">
			<div class="title">Report List</div>
			<div class="content" style="height:571px;">
			<div id="container_tab">
		<ul id="treeview" class="treeview">
				
						<li rel=".news" class="active link list1">Invoice</li>
						<li class="list"><a><span class="expand" id="close"></span>Sales</a>
							<ul class="ul">
								<li rel=".links" class="link">Sales Register </li>
								<li rel=".Goldlinks" class="link">VatTax Sales Register</li>
								<li rel=".Silverlinks" class="link">VatTax Free Sales Register</li>
								<li rel=".line2" class="link">Sales Estimate</li> 
								<li rel=".sal_ret" class="link">Sales Return Register</li>
								<li rel=".sal_bullion" class="link">Bullion Register </li>
								<li rel=".sal_groupSales" class="link">Group Sales Register </li>
							</ul>
						</li>
						<li class="list"><a><span class="expand" id="close"></span>Purchase</a>
							<ul class="ul">
								<li rel=".tutorials" class="link">Purchase Register</li>
								<li rel =".exchangepurchase" class="link">Exchange Purchase Register</li>
								<li rel=".manzoor" class="link">Purchase Bill</li>
								<li rel=".pur_ret" class="link">Purchase Return</li>
								<li rel=".oldPurchaseCatWiseReport" class="link">Old Purchase Category Report</li>
								
							</ul>
						</li>
						<li class="list"><a><span class="expand" id="close"></span>Order</a>
							<ul class="ul">
								<li rel=".Jo_Ord" class="link">Job Order</li>
								<li rel=".Jo_Issue" class="link">Job Order Issue</li>
		  						<li rel=".Jo_Receipt" class="link">Job Order Receipt</li>
		  						<li rel=".Jo_Issue_voucher" class="link">Job Order Issue Voucher</li>
		  						<li rel=".Jo_Receipt_voucher" class="link">Job Order Receipt Voucher</li>
								<li rel=".S_Order" class="link">Sales Order</li>
								
							</ul>
						</li>
						<!--jewel repair new reports-->
						<li class="list"><a><span class="expand" id="close"></span>Jewel Repair</a>
							<ul class="ul">
								<li rel=".old_Jewel_from_customer" class="link">Old Jewel From Customer</li>
								<li rel=".old_Jewel_to_smith" class="link">Old Jewel To Smith</li>
		  						<li rel=".new_jewel_from_smith" class="link">New Jewel From Smith</li>
		  						<li rel=".new_jewel_to_customer" class="link">New Jewel To Customer</li>
		  						</ul>
						</li>
						<li class="list"><a><span class="expand" id="close"></span>Stock</a>
							<ul class="ul">
								<li rel=".tuts" class="link">Stock</li>
								<li rel=".jewel" class="link">Opening Stock</li>
								<li rel=".line" class="link">Detailed Stock</li>
								<li rel=".canceled_tag" class="link">Cancelled Items</li>
							</ul>
						</li>
						<li class="list"><a><span class="expand" id="close"></span>Cash</a>
							<ul class="ul">
								<li rel=".cr" class="link">Cash Receipt</li>  
								<li rel=".cp" class="link">Cash Payment</li>
								<li rel=".rv" class="link">Receipt Voucher</li>
								<li rel=".pv" class="link">Payment Voucher</li>
							</ul>
						</li>
						<li class="list"><a><span class="expand" id="close"></span>Point of Sales</a>
							<ul class="ul">
							<li rel=".pos_invoice" class="link">POS Sales Invoice</li>
								<li rel=".pos_s" class="link">Point Of Sales Register</li>  
								<li rel=".pos_sr" class="link">Point Of Sales Return Register</li>
								<li rel=".pos_pReg" class="link">Point Of Purchase Register</li>
								<li rel=".pos_pRReg" class="link">Point Of Purchase Return Register</li>  
								<li rel=".pos_stock" class="link">POS Stock Report</li>  
							</ul>
						</li>
						<li class="list"><a><span class="expand" id="close"></span>Savings Scheme</a>
							<ul class="ul">
								<li rel=".ss_receiptVoucher" class="link"> Receipt Voucher</li>
								<li rel=".ss_cardIssueDetails" class="link">Scheme wise Card Details</li>
								<li rel=".ss_savingreceiptregister" class="link">Receipt Register</li>
								<li rel=".ss_cancelcard" class="link"> Cancelled Card </li>
									<li rel=".SS_PaymentDue" class="link"> Payment Dues From Customer</li>
							</ul>
						</li>
						<li class="list"><a><span class="expand" id="close"></span>Accounting Reports</a>
							<ul class="ul">
								<li rel=".ac_CashBook" class="link"> Cash Book</li>
								<li rel=".ac_DayBook" class="link"> Day Book</li>
								<li rel=".LedgerReport" class="link"> Ledger Report</li>
								<li rel=".TrialBalanceReport" class="link"> Trial Balance</li>
								<li rel=".BalanceSheetReport" class="link"> Balance Sheet</li>
								<li rel=".IncomeAndExpense" class="link">Income And Expense</li> 
								
							</ul>
						</li>
					<li class="list lotDetailedStock"><a><span class="expand" id="close"></span>lot Detailed Stock</a>
							<ul class="ul">
								<li rel=".lotdetialedstock" class="link"> Lot Detailed Stock</li>
								<li rel=".addlotstocks" class="link"> Add Stock</li>
								<li rel=".returnlotstocks" class="link">Return Stock</li>
								<li rel=".lotWithOrnamental" class="link">lotStockWithOrnamental</li>
							</ul>
						</li>
					<li class="list lotDetailedStock"><a><span class="expand" id="close"></span>Sales</a>
							<ul class="ul">
								<li rel=".secondSales" class="link"> Sales Register Report </li>
								<li rel=".secondPOSSales" class="link"> POS Sales Register Report</li>
							</ul>
						</li>
						<li rel=".customer" class="link list1" >Party Summary</li>
					</ul>		 
        
		</div>
			</div>
	</div>

	
</div>	
<div id="col-form">
	<div class="boxed">
	<div class="title"  style="text-align:left;">Reports</div>
	<div class="content" > 		
        <span class="clear"></span>
       
		<div class="content_tab news">
			<h1>Invoice</h1>
			<div class="tab_form"><span>Invoice</span>
			<form:form id="form6" method="post" action="binvoice.htm"  target="new" onsubmit=" return validate();">
	<table border="" width=400 >	
		<tr bgcolor="#FFFFFF">
				
		        <tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Bill No</td> <td><input id="billno" type="text" name="invoiceNo"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td> <td><input type="submit" value="Sales Invoice" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lblfrmdate"> </label> </td> </tr>
					
		</tr>	
	</table>	
	</form:form>
	</div>
		</div>
		<div class="content_tab news">
			<h1>Bullion Invoice</h1>
			<div class="tab_form"><span>Bullion Invoice</span>
			<form:form id="form6" method="post" action="bullioninc.htm" target="new" onsubmit="return bulliosales();">
	<table border="" width=400 >	
		<tr bgcolor="#FFFFFF">
		<tr bgcolor="#FFFFFF"><td bgcolor="#BBDDFF" >Bill No</td> <td><input id="bullionsalesid" type="text" name="bullion_billno"  class="textfield" /></td></tr>
		<tr bgcolor="#FFFFFF"><td bgcolor="#BBDDFF" >View</td> <td><input type="submit" value="Bullion Invoice" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
		<tr bgcolor="#FFFFFF"><td bgcolor="#BBDDFF" > Message </td>  <td> <label id="bullion_error"> </label> </td> </tr>
					
		</tr>	
	</table>	
	</form:form>
	</div>
		</div>
		<div class="content_tab tutorials">
			<h1>Purchase Register</h1>
			<div class="tab_form"><span>Purchase Register</span>
			<form:form id="form7" method="post" action="purchasereg.htm" target="new" onsubmit=" return validate1();">
			<table border="1" width=400 >	
			<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="prfrmdate" type="text" name="prfrmdate"  class="textfield" /> </td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="prtodate" type="text" name="prtodate" value="" class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td> <td> <input type="submit" value="Purchase" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lblPR"> </label> </td> </tr>
					
			</tr>	
			</table>	
			</form:form>
		</div>
		</div>
		<div class="content_tab exchangepurchase">
			<h1>Exchange Purchase Register</h1>
			<div class="tab_form"><span>Exchange Purchase Register</span>
			<form:form id="form30" method="post" action="Exchangepurchasereg.htm" target="new" onsubmit=" return exchangePurchase();">
			<table border="1" width=400 >	
			<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="exprfrmdate" type="text" name="exprfrmdate"  class="textfield" /> </td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="exprtodate" type="text" name="exprtodate" value="" class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >BullionType</td> 
		<td>
		<select id="ExchangeType" name="ExchangeType" class="textfield">
		<option value="Gold Exchange">Gold Exchange</option>
		<option value="Silver Exchange">Silver Exchange</option>
		</select>
		</td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td> <td> <input type="submit" value="ExchangePurchase" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="exchangeVal"> </label> </td> </tr>
					
			</tr>	
			</table>	
			</form:form>
		</div>
		</div>
			<div class="content_tab oldPurchaseCatWiseReport">
			<h1>Old Purchase Category Wise  Register Report</h1>
			<div class="tab_form"><span>Old Purchase Category Wise  Register Report</span>
			<form:form id="formSecondSales" method="post" action="oldPurchaseCatWiseReport.htm" target="new" onsubmit="return validate_ESalesRegister();" >
			<table border="1" width=400 >	
			<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="ESRfrmdate" type="text" name="exprfrmdate"  class="textfield" /> </td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="ESRtodate" type="text" name="exprtodate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td> <td> <input type="submit" value="Generate Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="ESRErrorMsg"> </label> </td> </tr>
			</table>	
			</form:form>
		</div>
		</div>
	<!-- Add lot Stock report  -->
		<div class="content_tab addlotstocks">
			<h1>Add Stock Report</h1>
			<div class="tab_form"><span>Add Stock Report</span>
			<form:form id="form30" method="post" action="lotaddstockRep.htm" target="new" onsubmit="return validate_lotaddstock();" >
			<table border="1" width=400 >	
			<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="lotstockfrmdate" type="text" name="lotfrmdate"  class="textfield" /> </td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="lotstocktodate" type="text" name="lottodate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >MetalType</td> 
		<td>
		<select id="lotItemName" name="lotItemName" class="textfield">
		<option value="GoldLotStock">GoldLotStock</option>
		<option value="SilverLotStock">SilverLotStock</option>
		</select>
		</td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td> <td> <input type="submit" value="A  ddStock" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lotaddErrorMsg"> </label> </td> </tr>
			</table>	
			</form:form>
		</div>
		</div>
		<!-- Second Sales  -->
		<div class="content_tab secondSales">
			<h1>Sales Register Report</h1>
			<div class="tab_form"><span>Sales Register</span>
			<form:form id="formSecondSales" method="post" action="ESalesRegister.htm" target="new" onsubmit="return validate_ESalesRegister();" >
			<table border="1" width=400 >	
			<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="ESRfrmdate" type="text" name="ESRfrmdate"  class="textfield" /> </td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="ESRtodate" type="text" name="ESRtodate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td> <td> <input type="submit" value="Generate Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="ESRErrorMsg"> </label> </td> </tr>
			</table>	
			</form:form>
		</div>
		</div>
		<!-- Second POS Sales  -->
		<div class="content_tab secondPOSSales">
			<h1>POS Sales Register Report</h1>
			<div class="tab_form"><span>POS Sales Register</span>
			<form:form id="formSecondSales" method="post" action="EPOSSalesRegister.htm" target="new" onsubmit="return validate_EPSalesRegister();" >
			<table border="1" width=400 >	
			<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="EPSRfrmdate" type="text" name="EPSRfrmdate"  class="textfield" /> </td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="EPSRtodate" type="text" name="EPSRtodate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td> <td> <input type="submit" value="Generate Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="EPSRErrorMsg"> </label> </td> </tr>
			</table>	
			</form:form>
		</div>
		</div>
	<!-- Return lot Stock report  -->
		<div class="content_tab returnlotstocks">
			<h1>Lot Return Report</h1>
			<div class="tab_form"><span>Lot Return Report</span>
			<form:form id="form30" method="post" action="lotreturnstockRep.htm" onsubmit="return validate_ReturnLotStock();" target="new">
			<table border="1" width=400 >	
			<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="stockFromDate" type="text" name="stockFromDate"  class="textfield" /> </td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="stockToDate" type="text" name="stockToDate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >MetalType</td> 
		<td>
		<select id="returnlotItemName" name="lotItemName" class="textfield">
		<option value="GoldLotStock">GoldLotStock</option>
		<option value="SilverLotStock">SilverLotStock</option>
		</select>
		</td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td> <td> <input type="submit" value="ShowReport" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lblErrorMsg"> </label> </td> </tr>
			</table>	
			</form:form>
		</div>
		</div>
		<div class="content_tab pur_ret">
			<h1>Purchase Return</h1>
			<div class="tab_form"><span>Purchase Return</span>
					
			<form:form id="form8" method="post" action="purchaseReturnReport.htm" target="new" onsubmit=" return validate8();">
			<table border="1" width=400 >		
			<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="prfrmdate1" type="text" name="prfrmdate1"  class="textfield" /> </td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="prtodate1" type="text" name="prtodate1"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td> <td> <input type="submit" value="PurchaseReturn" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message </td>  <td> <label id="lblPR1"> </label> </td> </tr>					
			</tr>	
			</table>	
			</form:form>	
			</div>
		</div>
		
	<!-- Lot item stock details  -->
		
	<div class="content_tab lotdetialedstock">
		<h1>Lot Item Detailed Stock</h1>
		<div class="tab_form"><span>Lot Item Detailed Stock</span>
		<form:form id="form14" method="post" action="lotDetailedStock.htm" target="new"  >
		<table border="1" width=400 >	
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >MetalUsed</td> 
				<td>
				<select name="metalUsed" id="lotMetalUsedID" class="textfield">
					<option value="0">---select---</option>
					<option value="Gold">Gold</option>
					<option value="Silver">Silver</option>
				</select>
				</td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Category Name</td> 
				<td>
				<select name="baseCategory" id="lotBaseCategory" class="textfield">
					<option value="select">---select---</option>
					<option value=""></option>
				</select>
				</td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" id="lotDetailedStockID" value="Detailed Stock" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="metalUsed1"> </label> </td> </tr>
				</tr>
			</tr>			
		</table>	
		</form:form>
		</div>
	</div>	
	
		<!-- Lot item stock details with ornamental deatil stock 13/12/12-->
	<div class="content_tab lotWithOrnamental">
		<h1>Lot Item Stock With Ornamental</h1>
		<div class="tab_form"><span>Lot Item Stock With Ornamental</span>
			<form:form id="form14" method="post" action="lotWithOrnamentalDetails.htm" target="new"  >
			<table border="1" width=400 >	
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >MetalUsed</td> 
			<td>
			<select id="metalUsedOrn" name="metalUsed" class="textfield">
				<option value="0">---select---</option>
				<option value="Gold">Gold</option>
				<option value="Silver">Silver</option>
			</select>
			</td></tr>
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Category Name</td> 
			<td>
			<select name="baseCategory" id="lotBaseCategoryOrn" class="textfield">
				<option value="select">---select---</option>
				<option value=""></option>
			</select>
			</td></tr>
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" id="lotDetailStockOrn" value="Detailed Stock" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
			<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="metalUsed1"> </label> </td> </tr>
			</table>	
		</form:form>
		</div>
	</div>
		
<div class="content_tab links">
<h1>Sales Register</h1>
	<div class="tab_form"><span>Sales Register</span>
	<form:form id="form9" method="post" action="salesreg.htm" target="new" onsubmit=" return validate2();">
	<table border="1" width=400 >	
	<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="srfrmdate" type="text" name="srfrmdate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="srtodate" type="text" name="srtodate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Sales" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lblSR"> </label> </td> </tr>
					
		</tr>	
	</table>	
	</form:form>
	</div>
</div>

<div class="content_tab Goldlinks">
<h1>VatTax Sales Register</h1>
	<div class="tab_form"><span>VatTax Sales Register</span>
	<form:form id="form9a" method="post" action="goldsalesreg.htm" target="new" onsubmit=" return gold_sales_Registers();">
	<table border="1" width=400 >	
	<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="goldsrfrmdate" type="text" name="srfrmdate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="goldsrtodate" type="text" name="srtodate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Gold Sales" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lblGSR"> </label> </td> </tr>
		</tr>	
	</table>	
	</form:form>
	</div>
</div>
<div class="content_tab Silverlinks">
<h1>VatTax Free Sales Register</h1>
	<div class="tab_form"><span>VatTax Free Sales Register</span>
	<form:form id="form9b" method="post" action="silversalesreg.htm" target="new" onsubmit=" return silver_sales_Registers();">
	<table border="1" width=400 >	
	<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="silversrfrmdate" type="text" name="srfrmdate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="silversrtodate" type="text" name="srtodate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Silver Sales" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lblSSR"> </label> </td> </tr>
		</tr>	
	</table>	
	</form:form>
	</div>
</div>
<div class="content_tab sal_ret">
		<h1>Sales Return Register</h1>
		<div class="tab_form"><span>Sales Return</span>
			<form:form id="form10" method="post" action="salesReturnReport.htm" target="new" onsubmit="return validate_salesreturn()">
			<table border="1" width=400 >			
			<tr bgcolor="#FFFFFF">				
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="srfrmdate1" type="text" name="srfrmdate1"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="srtodate1" type="text" name="srtodate1"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >BullionType</td> 	
				<td>
				<select id="bullion_Type" name="bullion_Type" class="textfield">
				<option value="Gold">Gold</option>
				<option value="Silver">Silver</option>
				</select>
				</td>		
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Sales Return" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message </td>  <td> <label id="lblSR1"> </label> </td> </tr>					
			</tr>	
			</table>	
			</form:form>
		</div>
</div>

<div class="content_tab sal_bullion">
		<h1> Bullion Sales Register</h1>
		<div class="tab_form"><span>Bullion Sales Register</span>
			<form:form id="form10" method="post" action="bullionSalesReg.htm" target="new" onsubmit="return validate_bullionRegister()" >
			<table border="1" width=400 >			
			<tr bgcolor="#FFFFFF">				
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="bulsalesfrmdate" type="text" name="exprfrmdate"  class="textfield" /> </td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="bulsalestomdate" type="text" name="exprtodate" value="" class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >BullionType</td> 	
				<td>
		<select id="ExchangeType" name="ExchangeType" class="textfield">
		<option value="Gold">Gold</option>
		<option value="Silver">Silver</option>
		</select>
		</td>		
		<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Bullion Register" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
		<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message </td>  <td> <label id="bullErrorMsg"> </label> </td> </tr>		
			</table>	
			</form:form>
		</div>
</div>
<div class="content_tab sal_groupSales">
		<h1> Group Sales Register</h1>
		<div class="tab_form"><span>Group Sales Register</span>
			<form:form id="form10" method="post" action="groupSalesReg.htm" target="new" onsubmit="return validate_GroupSalesRegister()" >
			<table border="1" width=400 >			
			<tr bgcolor="#FFFFFF">				
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id=groupsalesfrmdate type="text" name="frmDate"  class="textfield" /> </td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="groupsalestomdate" type="text" name="toDate" value="" class="textfield" /></td></tr>
		<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Group Sales Register" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
		<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message </td>  <td> <label id="bullErrorMsg"> </label> </td> </tr>		
			</table>	
			</form:form>
		</div>
</div>

<div class="content_tab manzoor">
			<h1>Purchase Bill</h1>
			<div class="tab_form"><span>Purchase Bill</span>
			<form:form id="form11" method="post" action="purchasebill.htm" target="new" onsubmit=" return validate3();">
			<table border="1" width=400 >	
			<tr bgcolor="#FFFFFF">
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Bill No</td> <td><input id="pbillNo" type="text" name="pbillNo"  class="textfield" /></td></tr>
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="PurchaseBill" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
			<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lblPB"> </label> </td> </tr>
			</tr>	
	</table>	
	</form:form>
	</div>
		</div>
	<div class="content_tab manzoor">
			<h1>Old Jewel Purchase Bill</h1>
			<div class="tab_form"><span>Old Jewel Purchase Bill</span>
			<form:form id="form11" method="post" action="oldgoldpurchase.htm" target="new" onsubmit=" return oldpurchase();">
			<table border="1" width=400 >	
			<tr bgcolor="#FFFFFF">
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Bill No</td> <td><input id="OldpbillNo" type="text" name="OldpbillNo"  class="textfield" /></td></tr>
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="OldJewelPurchase" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
			<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="isValidbill_No"> </label> </td> </tr>
			</tr>	
	</table>	
	</form:form>
	</div>
		</div>
		
        <div class="content_tab tuts">
			<h1>Stock</h1>
			<div class="tab_form"><span>Stock</span>
	<form:form id="form12" method="post" action="stock.htm" target="new" >
	<table border="1" width=400 >	
			
	<tr bgcolor="#FFFFFF">
	<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Stock Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')" /></td>
	</tr>
	</tr>		
					
			
	</table>	
	</form:form>
	</div>
		</div>
		<div class="content_tab jewel">
		<h1>Opening Stock</h1>
		<div class="tab_form"><span>Opening Stock</span>
		<form:form id="form13" method="post" action="jeweltypereport.htm" target="new">
		<table border="1" width="400">
		<tr bgcolor="#FFFFFF">
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Opening Stock" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
		</tr>			
		
		</table>
		</form:form>
		</div>
		</div>
		
  <div class="content_tab line">
	<h1>Detailed Stock</h1>
	<div class="tab_form"><span>Detailed Stock</span>
		<form:form id="form14" method="post" action="categorylst.htm" target="new">
		<table border="1" width=400 >	
		<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >MetalUsed</td> 
			<td>
			<select name="metalUsed" id="metalUsedID" class="textfield">
			<option value="0">---select---</option>
			<option value="Gold">Gold</option>
			<option value="Silver">Silver</option>
			</select>
			</td>
		</tr>
		<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Category Name</td> 
			<td>
			<select name="baseCategory" id="baseCategory" class="textfield">
			<option value="select">---select---</option>
			<option value=""></option>
			</select>
			</td>
		</tr>
		<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" id="detailedStockID" value="Detailed Stock" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
		<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="metalUsed1"> </label> </td> </tr>
		</table>	
		</form:form>
	</div>
 </div>

		<!--Daywise TagList Report -->
		<div class="content_tab line">
			<h1>DayWise TagList</h1>
			<div class="tab_form"><span>DayWise TagList</span>
			<form:form id="form16" method="post" action="Dailystock.htm" target="new" onsubmit=" return DaywiseTag();">
			<table border="1" width=400 >	
			<tr bgcolor="#FFFFFF">
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="Dfrmdate" type="text" name="frmDate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="Dtodate" type="text" name="toDate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Tag List" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="Dlblcr"> </label> </td> </tr>
			</tr>	
	</table>	
	</form:form>
	</div>
		</div>
		
			<!--Day wise stock Report-->
		<div class="content_tab line">
			<h1>Day Wise Stock Report</h1>
			<div class="tab_form"><span>Day Wise Stock Report</span>
			<form:form id="form16" method="post" action="DayWiseStock.htm" target="new" onsubmit=" return DaywiseStockReport();">
			<table border="1" width=400 >	
			<tr bgcolor="#FFFFFF">
				<td bgcolor="#BBDDFF" >Report Type</td>
				<td >	
					<select name="StockMetalType" id="StockMetalType" class="textfield">
						<option value="ALl">All Stock</option>
						<option value="Ornament">Ornament Stock</option>
						<option value="Bullion">Bullion Stock</option>
						<option value="OldMetal">Old Metal Stock</option>
						<option value="LooseMetal">Loose Stock</option>
						<option value="Untagged">Untagged Stock</option>
						<option value="LotStock">Lot Stock</option>
					</select> 
				</td>
			 </tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="DayWisefrmdate" type="text" name="frmDate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="DayWisetodate" type="text" name="toDate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Generate" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="DayWiselbl"> </label> </td> </tr>
			</table>	
			</form:form>
			</div>
		</div>
		
		
        <div class="content_tab line2">
		<h1>Sales Estimate</h1>
			<div class="tab_form"><span></span>
	        <form:form id="form15" method="post" action="salesest.htm" target="new" onsubmit=" return validate4();">
			<table border="1" width=400 >	
			<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Tag /Item Code</td> <td><input id="seic" type="text" name="seic" class="textfield" onkeydown = "return (event.keyCode!=13);"/></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Print</td><td><input type="submit" value="Sales Bill" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message</td>  <td> <label id="lblSE"> </label> </td> </tr>
			</tr>
					
			
	</table>	
	</form:form>
	</div>
		</div>
		 <div class="content_tab cr">
			<h1>Cash Receipt </h1>
			<div class="tab_form"><span>Cash Receipt</span>
			<form:form id="form16" method="post" action="CashReceipt.htm" target="new" onsubmit=" return validate6();">
			<table border="1" width=400 >	
			<tr bgcolor="#FFFFFF">
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="crfrmdate" type="text" name="crfrmdate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="crtodate" type="text" name="crtodate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Cash Receipt" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lblcr"> </label> </td> </tr>
			</tr>	
	</table>	
	</form:form>
	</div>
		</div>
		
		 <div class="content_tab cp">
			<h1>Cash Payment </h1>
			<div class="tab_form"><span>Cash Payment</span>
			<form:form id="form17" method="post" action="CashPayment.htm" target="new" onsubmit=" return validate7();">
			<table border="1" width=400 >	
			<tr bgcolor="#FFFFFF">
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="cpfrmdate" type="text" name="cpfrmdate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="cptodate" type="text" name="cptodate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Cash Payment" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lblpr"> </label> </td> </tr>
			</tr>
	</table>	
	</form:form>
	</div>
		</div>
		
		<div class="content_tab rv">
			<h1>Receipt Voucher</h1>
			<div class="tab_form">  <span>Receipt Voucher</span>
			<form:form  method="post" action="voucher.htm" target="new">
			<table border="1" width=400 >	
			<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Receipt No</td> <td><input id="receiptno" type="text" name="voucherId" id="voucherId" class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="VoucherSlip" id="voucherSlip" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <div id="lblrv" style="color:red;"> </div> </td> </tr>
			</tr>
	</table>	
	</form:form>
	</div>
		</div>
		<div class="content_tab pv">
			<h1>Payment Voucher</h1>
			<div class="tab_form">  <span>Payment Voucher</span>
			<form:form  method="post" onsubmit="return payment_check_id();" action="paymentVoucher.htm" target="new" id="pvForm">
			<table border="1" width=400 >	
			<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Payment No</td> <td><input id="paymentno" type="text" name="voucherId" class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="VoucherSlip" id="paymentVoucherSlip" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <div id="lblpv" style="color:red;"> </div> </td> </tr>
	</table>	
	</form:form>
	</div>
		</div>
	<div class="content_tab customer">
		<h1>Customer Summary</h1>
		<div class="tab_form"><span>Customer Summary</span>
		<form:form id="form18" method="post" action="customer_summary.htm" target="new">
		<table border="1" width="400">
		<tr bgcolor="#FFFFFF">
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="CustomerSummary" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
		</tr>			
		
		</table>
		</form:form>
		</div>
		</div>
		<div class="content_tab customer">
		<h1>Supplier Summary</h1>
		<div class="tab_form"><span>Supplier Summary</span>
		<form:form id="form19" method="post" action="supplier_saummary.htm" target="new">
		<table border="1" width="400">
		<tr bgcolor="#FFFFFF">
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="SupplierSummary" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
		</tr>			
		</table>
		</form:form>
		</div>
		</div>
		<div class="content_tab Jo_Ord">
			<h1>Job Order</h1>
			<div class="tab_form"><span>Job Order</span>
				<form:form id="form19" method="post" action="jobOrderReport.htm" target="new" onsubmit="return validate_joORDER();">
				<table border="1" width=400 >	
				<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="jofrmdate" type="text" name="jofrmdate" value="" class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="jotodate" type="text" name="jotodate" value="" class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Job Order" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message </td>  <td> <label id="lblJO"> </label> </td> </tr>
				</tr>	
				</table>	
				</form:form>
			</div>
		</div>
		
		<div class="content_tab Jo_Issue">
			<h1>Job Order Issue</h1>
			<div class="tab_form"><span>Job Order Issue</span>
				<form:form id="form20" method="post" action="jobOrderIssueReport.htm" target="new" onsubmit="return validate_joIssue();">
				<table border="1" width=400 >	
				<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="joifrmdate" type="text" name="jofrmdate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="joitodate" type="text" name="jotodate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Job Order Issue" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message </td>  <td> <label id="lblJOI"> </label> </td> </tr>
				</tr>	
				</table>	
				</form:form>
			</div>
		</div>
		<div class="content_tab Jo_Receipt">
			<h1>Job Order Receipt</h1>
			<div class="tab_form"><span>Job Order Receipt</span>
				<form:form id="form21" method="post" action="jobOrderReceiptReport.htm" target="new" onsubmit="return validate_joReceipt();">
				<table border="1" width=400 >	
				<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="jorfrmdate" type="text" name="jofrmdate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="jortodate" type="text" name="jotodate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Job Order Receipt" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message </td>  <td> <label id="lblJOR"> </label> </td> </tr>
				</tr>	
				</table>	
				</form:form>
			</div>
		</div>
		<!-- Job order issue and receipt voucher div -->
		<div class="content_tab Jo_Issue_voucher">
			<h1>Job Order Issue Voucher</h1>
			<div class="tab_form"><span>Job Order Issue Voucher</span>
				<form:form id="form20" method="post" action="jobOrderIssueVoucher.htm" target="new" onsubmit="return joborderIssueVoucher();">
				<table border="1" width=400 >	
				<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Issue Voucher No:</td> <td><input id="issueId" type="text" name="issueId"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Job Order Issue Voucher" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message </td>  <td> <label id="lblJOI_issue"> </label> </td> </tr>
				</tr>	
				</table>	
				</form:form>
			</div>
		</div>
		<div class="content_tab Jo_Receipt_voucher">
			<h1>Job Order Receipt Voucher</h1>
			<div class="tab_form"><span>Job Order Receipt Voucher</span>
				<form:form id="form21" method="post" action="jobOrderReceiptVoucher.htm" target="new" onsubmit="return joborderReceiptVoucher();">
				<table border="1" width=400 >	
				<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Receipt Voucher No:</td> <td><input id="jobordervoucherId" type="text" name="jobordervoucherId"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Job Order Receipt Voucher" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message </td>  <td> <label id="lblJOR_receipt"> </label> </td> </tr>
				</tr>	
				</table>	
				</form:form>
			</div>
		</div>
		<div class="content_tab S_Order">
			<h1>Sales Order</h1>
			<div class="tab_form"><span>Sales Order</span>
				<form:form id="form22" method="post" action="salesOrderReport.htm" target="new" onsubmit="return validate_soORDER();">
				<table border="1" width=400 >	
				<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="sofrmdate" type="text" name="sofrmdate" class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="sotodate" type="text" name="sotodate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Sales Order" onclick=window.open"('salesOrderReport.jsp','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message </td>  <td> <label id="lblSO"> </label> </td> </tr>
				</tr>	
				</table>	
				</form:form>
			</div>
		</div>
		<div class="content_tab S_Order">
			<h1>Sales Order Slip</h1>
			<div class="tab_form"><span>Sales Order Slip</span>
				<form:form id="form26" method="post" action="salesorder_slip.htm" target="new" onsubmit="return salesorderid();">
				<table border="1" width=400 >	
				<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Sales Order Number</td><td><input id="salesid" type="text" name="salesid"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Sales Order Slip" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message </td>  <td> <label id="isValid"> </label> </td> </tr>
				</tr>	
				</table>	
				</form:form>
			</div>
		</div>
<!--jewel repair new reports div-->
<div class="content_tab old_Jewel_from_customer">
			<h1>Old Jewel From Customer</h1>
			<div class="tab_form"><span>Old Jewel From Customer</span>
				<form:form id="form26" method="post" action="oldjewelfromcustomer.htm" target="new" onsubmit="return oldjewelfrom_customer();">
				<table border="1" width=400 >	
				<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Jewel Repair ID</td><td><input id="jewelfromcustomer" type="text" name="jewelrepaiId"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Old Jewel Bill" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message </td>  <td> <label id="repaird_1"> </label> </td> </tr>
				</tr>	
				</table>	
				</form:form>
			</div>
		</div>
<div class="content_tab old_Jewel_to_smith">
			<h1>Old Jewel To Smith</h1>
			<div class="tab_form"><span>Old Jewel To Smith</span>
				<form:form id="form26" method="post" action="oldjeweltosmith.htm" target="new" onsubmit="return oldjewelto_smith();">
				<table border="1" width=400 >	
				<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Jewel Repair ID</td><td><input id="oldjeweltosmith" type="text" name="jobordervoucherId"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Old Jewel Bill" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message </td>  <td> <label id="repaird_2"> </label> </td> </tr>
				</tr>	
				</table>	
				</form:form>
			</div>
		</div>
<div class="content_tab new_jewel_from_smith">
			<h1>New Jewel From Smith</h1>
			<div class="tab_form"><span>New Jewel From Smith</span>
				<form:form id="form26" method="post" action="newjewelfromsmith.htm" target="new" onsubmit="return newjewelfrom_smith();">
				<table border="1" width=400 >	
				<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Jewel Repair ID</td><td><input id="newjewelfromsmith" type="text" name="smithId"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="New Jewel Bill" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message </td>  <td> <label id="repaird_3"> </label> </td> </tr>
				</tr>	
				</table>	
				</form:form>
			</div>
		</div>
<div class="content_tab new_jewel_to_customer">
			<h1>New Jewel To Customer</h1>
			<div class="tab_form"><span>New Jewel To Customer</span>
				<form:form id="form26" method="post" action="newjeweltocustomer.htm" target="new" onsubmit="return newjewelto_customer();">
				<table border="1" width=400 >	
				<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Jewel Repair ID</td><td><input id="newjeweltocustomer" type="text" name="customerId"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="New Jewel Bill" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message </td>  <td> <label id="repaird_4"> </label> </td> </tr>
				</tr>	
				</table>	
				</form:form>
			</div>
		</div>
		
	<div class="content_tab canceled_tag">
		<h1>Canceled Item Tag List</h1>
		<div class="tab_form"><span>Canceled Item Tag List</span>
		<form:form id="form23" method="post" action="tagged_stock_cancel.htm" target="new">
		<table border="1" width="400">
		<tr bgcolor="#FFFFFF">
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="CanceledTag" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
		</tr>			
		
		</table>
		</form:form>
		</div>
	</div>
<div class="content_tab pos_invoice">
<h1>POS Sales Invoice</h1>
	<div class="tab_form"><span>POS Sales Invoice</span>
	<form:form id="form35" method="post" action="possalesinvoice.htm" target="new" onsubmit=" return possalesInvoice();">
	<table border="1" width=400 >	
	<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Invoice No:</td>  <td><input id="posSalesId" type="text" name="posSalesId"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Show Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lblPOSSalesId"> </label> </td> </tr>
					
		</tr>	
	</table>	
	</form:form>
	</div>
</div>

<div class="content_tab pos_pReg">
<h1>Point Of Purchase Register</h1>
	<div class="tab_form"><span>Point Of Purchase Register</span>
	<form:form id="form9" method="post" action="pospurchaseregister.htm" target="new" onsubmit=" return validate_POSPurchaseRegister();">
	<table border="1" width=400 >	
	<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="pos_purfrmdate" type="text" name="posfrmdate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="pos_purtodate" type="text" name="postodate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Show Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lblPOS_PurRegister"> </label> </td> </tr>
					
		</tr>	
	</table>	
	</form:form>
	</div>
</div>
<div class="content_tab pos_pRReg">
<h1>Point Of Purchase Return Register</h1>
	<div class="tab_form"><span>Point Of Purchase Return Register</span>
	<form:form id="form9" method="post" action="pospurchasereturnregister.htm" target="new" onsubmit=" return validate_POSPurchaseReturnRegister();">
	<table border="1" width=400 >	
	<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="pos_purretfrmdate" type="text" name="posfrmdate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="pos_purrettodate" type="text" name="postodate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Show Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lblPOS_PurRetRegister"> </label> </td> </tr>
					
		</tr>	
	</table>	
	</form:form>
	</div>
</div>


<div class="content_tab pos_s">
<h1>Point Of Sales Register</h1>
	<div class="tab_form"><span>Point Of Sales Register</span>
	<form:form id="form9" method="post" action="possalesregister.htm" target="new" onsubmit=" return validate_POSSalesRegister();">
	<table border="1" width=400 >	
	<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="posfrmdate" type="text" name="posfrmdate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="postodate" type="text" name="postodate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Show Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lblPOS_Register"> </label> </td> </tr>
					
		</tr>	
	</table>	
	</form:form>
	</div>
</div>

<div class="content_tab pos_sr">
<h1>Point Of Sales Return Register</h1>
	<div class="tab_form"><span>Point Of Sales Return Register</span>
	<form:form id="form9" method="post" action="possalesreturnregister.htm" target="new" onsubmit=" return validate_POSSalesReturnRegister();">
	<table border="1" width=400 >	
	<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="pos_frmdate" type="text" name="posfrmdate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="pos_todate" type="text" name="postodate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Show Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lblPOSReturnRegister"> </label> </td> </tr>
					
		</tr>	
	</table>	
	</form:form>
	</div>
</div>	
	
<div class="content_tab pos_stock">
<h1>POS Stock Report</h1>
	<div class="tab_form"><span>POS Stock Report</span>
	<form:form id="pos_stock_form24" method="post" action="POSStock.htm" target="new" >
	<table border="1" width=400 >	
		<tr bgcolor="#FFFFFF">	
			<td bgcolor="#BBDDFF" >Stock Type </td>  
			<td><select name="Stock_Type" id="pos_stock_type" class="textfield">
					<option value="all">All</option>
					<option value="Opening Stock">Opening Stock</option>
					<option value="POS Purchase">Purchase Stock</option>
				</select>
			</td>
		</tr>
		<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Category </td>
			 <td><select name="Category_Name" id="pos_stock_catName" class="textfield">
					<option value="all">All</option>
					<c:forEach var="posCategoryName" items="${posCategoryNameList}">
						<option value="${posCategoryName}">${posCategoryName}</option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Generate Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
		<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message</td>  <td> <label id="lblPOSStock"> </label> </td> </tr>
	</table>	
	</form:form>
	</div>
</div>		

<div class="content_tab ss_receiptVoucher">
<h1>Savings Scheme Receipt Voucher</h1>
	<div class="tab_form">
	<form:form id="form10" method="post" action="SSReceiptVoucher.htm" target="new" onsubmit="return validate_SSReceiptVoucher();">
	<table border="1" width=400 >	
	<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Receipt No</td> <td><input id="ss_ReceiptNo" type="text" name="receiptNO" class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td> <td><input type="submit" id="SSReceiptVC" value="Show Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message</td> <td><label id="lblSSReceiptVoucher"> </label> </td> </tr>
		</tr>	
	</table>	
	</form:form>
	</div>
</div>
<div class="content_tab ac_CashBook">
<h1>Cash Book</h1>
<div class="tab_form"><span>Cash Book Register</span>
<form:form id="form19" method="post" action="CashBookRegister.htm" target="new">
		<table border="1" width="400">
		<tr bgcolor="#FFFFFF">
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="fromjournaldate" type="text" name="FromDateJournalDate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="tojournaldate" type="text" name="EndJournalDate"  class="textfield" /></td></tr>
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Cash Book" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
		</tr>			
		</table>
		</form:form>
</div>
</div>
<div class="content_tab ac_DayBook">
<h1>Day Book</h1>
	<div class="tab_form">
	<form:form id="AccDayBook" method="post" action="AccDayBook.htm" target="new" onsubmit="return validate_AccDayBook();">
	<table border="1" width=400 >	
	<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	
					<td bgcolor="#BBDDFF" >Report Type</td> 
					<td><select name="DBReportType" id="DBReportType" class="textfield">
							<option value="Day Book">Day Book</option>
							<option value="Detailed Day Book">Detailed Day Book</option>
						</select>
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="accDB_frmdate" type="text" name="DBfrmdate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="accDB_todate" type="text" name="DBtodate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Show Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lblAccDayBook"> </label> </td> </tr>
		</tr>	
	</table>	
	</form:form>
	</div>
</div>
<div class="content_tab LedgerReport">
<h1>Ledger Report</h1>
	<div class="tab_form">
	<form:form id="LedgerBook" method="post" action="allLedgerReport.htm" target="new" onsubmit="return validate_LedgerBook();">
	<table border="1" width=400 >	
	<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	
					<td bgcolor="#BBDDFF" >Ledger Type</td> 
					<td><select name="LedgerType" id="Ledger_Type" class="textfield">
						<option value="All">All A/c</option>
						<option value="Individual A/c">Individual A/c</option>						
						</select>
					</td>
				</tr>
						<tr bgcolor="#FFFFFF">	
			<td bgcolor="#BBDDFF" >LedgerName </td>  
			<td><select name="LedgerName" id="Ledger_Name" class="textfield">
					<option value="All">All</option>
					<c:forEach var="ledgerNameList" items="${ledgerNameList}">
						<option value="${ledgerNameList}">${ledgerNameList}</option>
				</c:forEach>
				</select>
			</td>
		</tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="from_journaldate" type="text" name="FromDateJournalDate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="to_journaldate" type="text" name="ToJournalDate"  class="textfield" /></td></tr>
						
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" id="submitled" value="Show Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lblLedgerBook"> </label> </td> </tr>
		</tr>	
	</table>	
	</form:form>
	</div>
</div>

<div class="content_tab TrialBalanceReport">
		<h1>Trial Balance</h1>
		<div class="tab_form">
		<form:form id="form19" method="post" action="trialBalance.htm" target="new">
		<table border="1" width="400">
		<tr bgcolor="#FFFFFF">
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Show Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
		</tr>			
		</table>
		</form:form>
		</div>
</div>



<div class="content_tab BalanceSheetReport">
		<h1>Balance Sheet</h1>
		<div class="tab_form">
		<form:form id="form19" method="post" action="balancesheet.htm" target="new">
		<table border="1" width="400">
		<tr bgcolor="#FFFFFF">
			<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Show Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
		</tr>			
		</table>
		</form:form>
		</div>
</div>

<div class="content_tab IncomeAndExpense">
<h1>Income And Expense</h1>
	<div class="tab_form">
	<form:form id="IncomeAndExpense" method="post" action="IncomeAndExpense.htm" target="new" >
	<table border="1" width=400 >	
	<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" id="IncomeAndExpense" value="Show Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lblLedgerBook"> </label> </td> </tr>
	</table>	
	</form:form>
	</div>		  
</div>

<!--saving scheme cardissue-->
<div class="content_tab ss_cardIssueDetails">
<h1>Scheme Wise Card Details Register</h1>
	<div class="tab_form">
	<form:form modelAttribute="cardissue" method="post" action="SSCardissuedetails.htm" target="new" onsubmit="return validate_SSReceiptVoucher();">
	<table border="1" width=400 >	
	<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Scheme Name</td> 
				<td><form:select path="schemeName" id="schemeName" class="textfield">
				<form:option value="">Select</form:option>
				<c:forEach var="scheme" items="${schemename}">
				<form:option value="${scheme.schemeName}">
				</form:option>
				</c:forEach>
				</form:select>
				</td></tr>
				<%-- <tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Start Date</td> 
				<td><form:select path="dateOfJoining" id="joinDate"class="textfield">
				<form:option value="">Select</form:option>
				<form:option value=""></form:option>
				</form:select>
				</td></tr> --%><!-- This code is commented because of change of request  -->
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td> <td><input type="submit"  value="Generate" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message</td> <td><label id="lblSSReceiptVoucher"> </label> </td> </tr>
		</tr>		
			
	</table>	
	</form:form>
	</div>
</div>	
<div class="content_tab ss_cancelcard">
<h1>Cancelled Card Details Register</h1>
	<div class="tab_form">
	<form:form modelAttribute="cardissue" method="post" action="cancelCardRegister.htm" target="new">
	<table border="1" width=400 >	
	<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Scheme Name</td> 
				<td><form:select path="schemeName" id="schemeNames" class="textfield">
				<form:option value="">Select</form:option>
				<c:forEach var="scheme" items="${schemename}">
				<form:option value="${scheme.schemeName}">
				</form:option>
				</c:forEach>
				</form:select>
				</td></tr>
				<%-- <tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Start Date</td> 
				<td><form:select path="dateOfJoining" id="startDate"class="textfield">
				<form:option value="">Select</form:option>
				<form:option value=""></form:option>
				</form:select>
				</td></tr> --%>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td> <td><input type="submit"  value="Show Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message</td> <td><label id="cancelcard"> </label> </td> </tr>
		
			
	</table>	
	</form:form>
	</div>
</div>	

<div class="content_tab SS_PaymentDue">
<h1>Savings Scheme Payment Dues From Customer</h1>
	<div class="tab_form">
	<form:form id="form11" method="post" action="SSPaymentDue.htm" target="new" onsubmit="return validate_SSPaymentDue();">
	<table border="1" width=400 >	
	<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF" >	<td bgcolor="#BBDDFF" ><input type="radio" id="SSPaymentDueSingle" name="reportType" class="reportType" value="single" checked="checked">Scheme Name <input type="radio" id="SSPaymentDueMultiple" name="reportType" class="reportType" value="all">All Schemes</td></tr>
				<tr bgcolor="#FFFFFF" id="ss_PaymentDue_schemeName" > <td bgcolor="#BBDDFF" >Scheme Name * </td> <td>
					<select id="PaymentDue_schemeName" name="schemeName" class="textfield">
						<option value="0">Select</option>
						<c:forEach var="ssrscheme" items="${SchemeNameList}">
								<option value="${ssrscheme.schemeName}">${ssrscheme.schemeName}</option>
						</c:forEach>
					</select></td>
				</tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >Till Date * </td> <td><input id="ss_PaymentDue_tillDate" type="text" name="tillDate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td> <td><input type="submit" id="SSPaymentD" value="Show Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" >Message</td> <td><label id="lblSSPaymentDue"> </label> </td> </tr>
		</tr>	
	</table>	
	</form:form>
	</div>
</div>

<div class="content_tab ss_savingreceiptregister">
<h1>Saving Receipt Register</h1>
	<div class="tab_form"><span></span>
	<form:form id="form9" method="post" action="savingreceiptregister.htm" target="new" onsubmit=" return validate_SavingReceiptRegister();">
	<table border="1" width=400 >	
	<tr bgcolor="#FFFFFF">
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >From Date</td>  <td><input id="recfrmdate" type="text" name="recfrmdate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >To Date</td> <td><input id="rectodate" type="text" name="rectodate"  class="textfield" /></td></tr>
				<tr bgcolor="#FFFFFF">	<td bgcolor="#BBDDFF" >View</td><td><input type="submit" value="Show Report" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')"/></td></tr>
				<tr bgcolor="#FFFFFF">  <td bgcolor="#BBDDFF" > Message </td>  <td> <label id="lblREC_Register"> </label> </td> </tr>
		</tr>	
	</table>	
	</form:form>
	</div>
</div>	
		  
        </div><!-- content -->
		</div><!--boxed-->
		</div><!--col-form-->
		</div><!--content_container-->
		<script type="text/javascript" src="script/js_Report_Validation.js"></script>
		<script type="text/javascript" src="script/jsreport.js"></script>
		<script>
		
		
	$(document).ready(function(){
		$("#prfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#prfrmdate").val(prettyDate);
	});

	$(document).ready(function(){
		$("#prtodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	
	
	$(document).ready(function(){
		$("#frmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#frmdate").val(prettyDate);
	});


	$(document).ready(function(){
		$("#prfrmdate1").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#prfrmdate1").val(prettyDate);
	});

	$(document).ready(function(){
		$("#prtodate1").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});

	//Daywise tag
	$(document).ready(function(){
		$("#Dfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#Dfrmdate").val(prettyDate);
	});

	$(document).ready(function(){
		$("#Dtodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	
	//Day wise Stock tag
	$(document).ready(function(){
		$("#DayWisefrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#DayWisefrmdate").val(prettyDate);
	});

	$(document).ready(function(){
		$("#DayWisetodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});

	$(document).ready(function(){
		$("#srfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#srfrmdate").val(prettyDate);
	});

	$(document).ready(function(){
		$("#exprfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#exprfrmdate").val(prettyDate);
	});
	
	$(document).ready(function(){
		$("#exprtodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	
	// Gold Sales register datePiker
	$(document).ready(function(){
		$("#goldsrfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#goldsrfrmdate").val(prettyDate);
	});
	
	$(document).ready(function(){
		$("#goldsrtodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	
	// Silver Sales register datePiker
	$(document).ready(function(){
		$("#silversrfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#silversrfrmdate").val(prettyDate);
	});

	$(document).ready(function(){
		$("#srtodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});

	$(document).ready(function(){
		$("#silversrtodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});

	$(document).ready(function(){
		$("#srfrmdate1").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#srfrmdate1").val(prettyDate);
	});
	$(document).ready(function(){
		$("#srtodate1").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});

	$(document).ready(function(){
		$("#jofrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#jofrmdate").val(prettyDate);
	});
	$(document).ready(function(){
		$("#jotodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});	

	$(document).ready(function(){
		$("#joifrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#joifrmdate").val(prettyDate);
	});
	$(document).ready(function(){
		$("#joitodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	

	$(document).ready(function(){
		$("#jorfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#jorfrmdate").val(prettyDate);
	});
	$(document).ready(function(){
		$("#jortodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});

	
	$(document).ready(function(){
		$("#sofrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#sofrmdate").val(prettyDate);
	});
	$(document).ready(function(){
		$("#sotodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	
	$(document).ready(function(){
		$("#pbfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#pbfrmdate").val(prettyDate);
	});
	$(document).ready(function(){
		$("#crfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	
	$(document).ready(function(){
		$("#crtodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	
	$(document).ready(function(){
		$("#cpfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	
	$(document).ready(function(){
		$("#cptodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	
	//POS Sales Register Calendar Date
	$(document).ready(function(){
		$("#posfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#posfrmdate").val(prettyDate);
	});

	$(document).ready(function(){
		$("#postodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	

	//SS Payment Due
	$(document).ready(function(){
		
		check_SSPaymentDue_Visibility();
		$(".reportType").click(check_SSPaymentDue_Visibility);
		$("#ss_PaymentDue_tillDate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#ss_PaymentDue_tillDate").val(prettyDate);
	});
	
	function check_SSPaymentDue_Visibility(){
		var selectedValue = $(".reportType:checked").val();
	    if(selectedValue == "single"){
	    	 $("#PaymentDue_schemeName").val("Select");
	    	 $("#ss_PaymentDue_schemeName").show();
	    }else{
	    	$("#ss_PaymentDue_schemeName").hide();
	    }
	}


	//POS Sales Return Register Calendar Date
	$(document).ready(function(){
		$("#pos_frmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#pos_frmdate").val(prettyDate);
	});

	$(document).ready(function(){
		$("#pos_todate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	
	// Accounting - Day Book and Detailed Day Book
	$(document).ready(function(){
		$("#accDB_frmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#accDB_frmdate").val(prettyDate);
	});

	$(document).ready(function(){
		$("#accDB_todate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	
	
	//POS Purchase Register Calendar Date
	$(document).ready(function(){
		$("#pos_purfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#pos_purfrmdate").val(prettyDate);
	});

	$(document).ready(function(){
		$("#pos_purtodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	
	//POS Purchase Retrun Register Calendar Date
	$(document).ready(function(){
		$("#pos_purretfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#pos_purretfrmdate").val(prettyDate);
	});

	$(document).ready(function(){
		$("#pos_purrettodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	
	
	//Saving Scheme Receipt Register Calendar Date
	$(document).ready(function(){
		$("#recfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#recfrmdate").val(prettyDate);
	});

	$(document).ready(function(){
		$("#rectodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});

	//lot add stock 
	$(document).ready(function(){
		$("#lotstockfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#lotstockfrmdate").val(prettyDate);
	});
	$(document).ready(function(){
		$("#lotstocktodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	
	//Estimate Sales Register Report
	$(document).ready(function(){
		$("#ESRtodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#ESRtodate").val(prettyDate);
	});
	$(document).ready(function(){
		$("#ESRfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	
	//Estimate POS Sales Register Report
	$(document).ready(function(){
		$("#EPSRtodate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#EPSRtodate").val(prettyDate);
	});
	$(document).ready(function(){
		$("#EPSRfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	
	//lot Remove  stock 
	$(document).ready(function(){
		$("#stockFromDate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#stockFromDate").val(prettyDate);
	});
	$(document).ready(function(){
		$("#stockToDate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});

	// bullion  Sales register datePiker
	$(document).ready(function(){
		$("#bulsalesfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#bulsalesfrmdate").val(prettyDate);
	});
	//bullion sales register 
	$(document).ready(function(){
		$("#bulsalestomdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	// Group  Sales register datePiker
	$(document).ready(function(){
		$("#groupsalesfrmdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#groupsalesfrmdate").val(prettyDate);
	});
	//group sales register 
	$(document).ready(function(){
		$("#groupsalestomdate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	});
	//cash book register
	$(document).ready(function(){
			$("#fromjournaldate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
			var myDate = new Date();
			var month = myDate.getMonth() + 1;
			var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
			$("#fromjournaldate").val(prettyDate);
			$("#tojournaldate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		});
	
	//Ledger Book
	$(document).ready(function(){
		$("#from_journaldate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate = myDate.getDate()+'/'+month +'/'+ myDate.getFullYear();
		$("#from_journaldate").val(prettyDate);
		$("#to_journaldate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});	
		
		makeLedgerReadOnly();
		$("#Ledger_Type").change(makeLedgerReadOnly).trigger('change');		
	});
	
	function makeLedgerReadOnly(){
		var LedgerType = $("#Ledger_Type").val();
		if(LedgerType=="All"){
				$("#Ledger_Name").parents("tr").hide();
				$("#from_journaldate").parents("tr").hide();
				$("#to_journaldate").parents("tr").hide();
				$("#Ledger_Name").val("All");
			}else{
				$("#Ledger_Name").parents("tr").show(); 	
				$("#from_journaldate").parents("tr").show();
				$("#to_journaldate").parents("tr").show();
				$("#Ledger_Name option[value=All]").hide();
				$("#Ledger_Name :not(option[value=All])").attr("selected",true);
			}		
	}

</script>		
<script type="text/javascript" src="script/cardissue.js"></script>	
</body>
</html>