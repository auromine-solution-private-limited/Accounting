// formsales.jsp Required Field validation

function notEmpty()
{
      var errormessage="";

	if (document.getElementById("date").value == "" )
	{ 
		errormessage += " Please Select Sales Date \n";
		
    }
if (document.getElementById("salesmanName").value == "" )
	{ 
		errormessage += " Please enter the Sales Man Name \n";		
    }
	
if (document.getElementById("customerName").value == "" )
	{ 
		errormessage += " Please enter the Customer Name \n";		
    }	
	
   if (errormessage !="")  
   {

      alert(errormessage);
       return false;
   }
 }
 
 //loginForm.jsp Required Field validation
 
 function logval()
 {
 var errormessage="";
 var element = document.getElementById("lblLV");
 if (document.getElementById("userName").value == "")
 { 
errormessage = " Please enter the username\n";
element.innerHTML = errormessage;
element.style.color="red";
}
 if (errormessage !="")  
 {
  //alert(errormessage);
 return false;
 }
 else
 {
 document.getElementById("lblLV").innerHTML = "";
 }
 if(document.getElementById("password").value == "") 
 {
 errormessage = " Please enter the password\n";
 element.innerHTML = errormessage;
 element.style.color="red";
 }
 if (errormessage !="")  
 {
  //alert(errormessage);
 return false;
 }
 else
 {
 document.getElementById("lblLV").innerHTML = "";
 }
}
 

 
// reports.jsp Required Field validation
function validate()
{
var errormessage="";
var element = document.getElementById("lblfrmdate");
if (document.getElementById("billno").value == "" ){ 
errormessage = " Please enter the Bill No \n";
		element.innerHTML = errormessage;
        element.style.color="red";
}
  
 
 if (errormessage !="")  {

 // alert(errormessage);
  return false;
  }
  else
  {
  document.getElementById("lblfrmdate").innerHTML = "";
  }
  }
function bulliosales()
{
var errormessage="";
var element = document.getElementById("bullion_error");
if (document.getElementById("bullionsalesid").value == "" ){ 
errormessage = " Please enter the Bill No \n";
		element.innerHTML = errormessage;
        element.style.color="red";
}
  
 
 if (errormessage !="")  {

 // alert(errormessage);
  return false;
  }
  else
  {
  document.getElementById("bullion_error").innerHTML = "";
  }
  }
  

function validate1()
{
 var errormessage="";
  var element = document.getElementById("lblPR");
  
  if(document.getElementById("prfrmdate").value == "") {
  errormessage = " Please Select From Date\n";
  element.innerHTML = errormessage;
  element.style.color="red";
  
  }
   if(document.getElementById("prtodate").value == "") {
  errormessage = " Please Select To Date\n";
  element.innerHTML = errormessage;
  element.style.color="red";
  
  }

 if (errormessage !="")  {
 // alert(errormessage);
  return false;
  }
    else
  {
  document.getElementById("lblPR").innerHTML = "";
  }
  }

function exchangePurchase()
{
 var errormessage="";
  var element = document.getElementById("exchangeVal");
  
  if(document.getElementById("exprfrmdate").value == "") {
  errormessage = " Please Select From Date\n";
  element.innerHTML = errormessage;
  element.style.color="red";
  
  }
   if(document.getElementById("exprtodate").value == "") {
  errormessage = " Please Select To Date\n";
  element.innerHTML = errormessage;
  element.style.color="red";
  
  }

 if (errormessage !="")  {
 // alert(errormessage);
  return false;
  }
    else
  {
  document.getElementById("exchangeVal").innerHTML = "";
  }
  }

  
function validate2()
{
 var errormessage="";
  var element = document.getElementById("lblSR");
  
  if(document.getElementById("srfrmdate").value == "") {
  errormessage = " Please Select From Date\n";
  element.innerHTML = errormessage;
  element.style.color="red";
  
  }
   if(document.getElementById("srtodate").value == "") {
  errormessage = " Please Select To Date\n";
  element.innerHTML = errormessage;
  element.style.color="red";
  
  }

 if (errormessage !="")  {
 // alert(errormessage);
  return false;
  }
      else
  {
  document.getElementById("lblSR").innerHTML = "";
  }
  }
  
function validate3()
{
 var errormessage="";
  var element = document.getElementById("lblPB");
  
  if(document.getElementById("pbfrmdate").value == "") {
  errormessage = " Please Select Purchase Date\n";
  element.innerHTML = errormessage;
  element.style.color="red";
  
  }
   if(document.getElementById("pbillNo").value == "") {
  errormessage = " Please Enter Bill No\n";
  element.innerHTML = errormessage;
  element.style.color="red";
  
  }
  
     if(document.getElementById("supplierName").value == "") {
  errormessage = " Please Enter Supplier Name\n";
  element.innerHTML = errormessage;
  element.style.color="red";
  
  }

 if (errormessage !="")  {
 // alert(errormessage);
  return false;
  }
      else
  {
  document.getElementById("lblPB").innerHTML = "";
  }
  }
  
function validate4()
{
 var errormessage="";
   var element = document.getElementById("lblSE");
  if(document.getElementById("seic").value == "") {
  errormessage = " Please Enter Item Code \n";
  element.innerHTML = errormessage;
  element.style.color="red";
  
  }
  
 if (errormessage !="")  {
 // alert(errormessage);
  return false;
  }
      else
  {
  document.getElementById("lblSE").innerHTML = "";
  }
  }
  
   function validate5()
{
 var errormessage="";
   var element = document.getElementById("lblSE1");
  if(document.getElementById("seics").value == "") {
  errormessage = " Please Enter Item Code \n";
  element.innerHTML = errormessage;
  element.style.color="red";
  
  }
  
 if (errormessage !="")  {
 // alert(errormessage);
  return false;
  }
      else
  {
  document.getElementById("lblSE1").innerHTML = "";
  }
  }

  function validate6()
{
 var errormessage="";
  var element = document.getElementById("lblcr");
  
  if(document.getElementById("crfrmdate").value == "") {
  errormessage = " Please Select From Date\n";
  element.innerHTML = errormessage;
  element.style.color="red";
  
  }
   if(document.getElementById("crtodate").value == "") {
  errormessage = " Please Select To Date\n";
  element.innerHTML = errormessage;
  element.style.color="red";
  
  }

 if (errormessage !="")  {
 // alert(errormessage);
  return false;
  }
      else
  {
  document.getElementById("lblcr").innerHTML = "";
  }
  }
  
  
  function validate7()
{
 var errormessage="";
  var element = document.getElementById("lblpr");
  
  if(document.getElementById("cpfrmdate").value == "") {
  errormessage = " Please Select From Date\n";
  element.innerHTML = errormessage;
  element.style.color="red";
  
  }
   if(document.getElementById("cptodate").value == "") {
  errormessage = " Please Select To Date\n";
  element.innerHTML = errormessage;
  element.style.color="red";
  
  }

 if (errormessage !="")  {
 // alert(errormessage);
  return false;
  }
      else
  {
  document.getElementById("lblpr").innerHTML = "";
  }
  }
  
  //Purchase Return
  function validate8()
{
 var errormessage="";
  var element = document.getElementById("lblPR1");
  
  if(document.getElementById("prfrmdate1").value == "") {
  errormessage = " Please Select From Date\n";
  element.innerHTML = errormessage;
  element.style.color="red";
  
  }
   if(document.getElementById("prtodate1").value == "") {
  errormessage = " Please Select To Date\n";
  element.innerHTML = errormessage;
  element.style.color="red";
  
  }

 if (errormessage !="")  {
 // alert(errormessage);
  return false;
  }
    else
  {
  document.getElementById("lblPR1").innerHTML = "";
  }
  }
  
   //SALES RETURN
  function validate_salesreturn()
  {
   var errormessage="";
    var element = document.getElementById("lblSR1");
    
    if(document.getElementById("srfrmdate1").value == "") {
    errormessage = " Please Select From Date\n";
    element.innerHTML = errormessage;
    element.style.color="red";
    
    }
     if(document.getElementById("srtodate1").value == "") {
    errormessage = " Please Select To Date\n";
    element.innerHTML = errormessage;
    element.style.color="red";
    
    }

   if (errormessage !="")  {
   // alert(errormessage);
    return false;
    }
        else
    {
    document.getElementById("lblSR1").innerHTML = "";
    }
    }
  //Bullion SALES Register
  function validate_bullionRegister()
  {
	  var errormessage="";
	  var element = document.getElementById("bullErrorMsg");
	  
	  if(document.getElementById("bulsalesfrmdate").value == "") {
		  errormessage = " Please Select From Date\n";
		  element.innerHTML = errormessage;
		  element.style.color="red";
		  
	  }
	  if(document.getElementById("bulsalestomdate").value == "") {
		  errormessage = " Please Select To Date\n";
		  element.innerHTML = errormessage;
		  element.style.color="red";
		  
	  }
	  
	  if (errormessage !="")  {		
		  return false;
	  }
	  else
	  {
		  document.getElementById("bullErrorMsg").innerHTML = "";
	  }
  }
  
  //Group SALES Register
  function validate_GroupSalesRegister()
  {
	  var errormessage="";
	  var element = document.getElementById("bullErrorMsg");
	  
	  if(document.getElementById("groupsalesfrmdate").value == "") {
		  errormessage = " Please Select From Date\n";
		  element.innerHTML = errormessage;
		  element.style.color="red";
		  
	  }
	  if(document.getElementById("groupsalestomdate").value == "") {
		  errormessage = " Please Select To Date\n";
		  element.innerHTML = errormessage;
		  element.style.color="red";
		  
	  }
	  
	  if (errormessage !="")  {		
		  return false;
	  }
	  else
	  {
		  document.getElementById("bullErrorMsg").innerHTML = "";
	  }
  }
  
  //JOB ORDER SCRIPT
  function validate_joORDER()
  {
   var errormessage="";
    var element = document.getElementById("lblJO");
    
    if(document.getElementById("jofrmdate").value == "") {
    errormessage = " Please Select From Date\n";
    element.innerHTML = errormessage;
    element.style.color="red";
    
    }
     if(document.getElementById("jotodate").value == "") {
    errormessage = " Please Select To Date\n";
    element.innerHTML = errormessage;
    element.style.color="red";
    
    }

   if (errormessage !="")  {
   // alert(errormessage);
    return false;
    }
        else
    {
    document.getElementById("lblJO").innerHTML = "";
    }
    }
  
//JOB ORDER ISSUE SCRIPT
  function validate_joIssue()
  {
   var errormessage="";
    var element = document.getElementById("lblJOI");
    
    if(document.getElementById("joifrmdate").value == "") {
    errormessage = " Please Select From Date\n";
    element.innerHTML = errormessage;
    element.style.color="red";
    
    }
     if(document.getElementById("joitodate").value == "") {
    errormessage = " Please Select To Date\n";
    element.innerHTML = errormessage;
    element.style.color="red";
    
    }

   if (errormessage !="")  {
   // alert(errormessage);
    return false;
    }
        else
    {
    document.getElementById("lblJOI").innerHTML = "";
    }
    }
  
//JOB ORDER RECEIPT SCRIPT
  function validate_joReceipt()
  {
   var errormessage="";
    var element = document.getElementById("lblJOR");
    
    if(document.getElementById("jorfrmdate").value == "") {
    errormessage = " Please Select From Date\n";
    element.innerHTML = errormessage;
    element.style.color="red";
    
    }
     if(document.getElementById("jortodate").value == "") {
    errormessage = " Please Select To Date\n";
    element.innerHTML = errormessage;
    element.style.color="red";
    
    }

   if (errormessage !="")  {
   // alert(errormessage);
    return false;
    }
        else
    {
    document.getElementById("lblJOR").innerHTML = "";
    }
    }  
//SALES ORDER SCRIPT
  function validate_soORDER()
  {
   var errormessage="";
    var element = document.getElementById("lblSO");
    
    if(document.getElementById("sofrmdate").value == "") {
    errormessage = " Please Select From Date\n";
    element.innerHTML = errormessage;
    element.style.color="red";
    
    }
     if(document.getElementById("sotodate").value == "") {
    errormessage = " Please Select To Date\n";
    element.innerHTML = errormessage;
    element.style.color="red";
    
    }

   if (errormessage !="")  {
   // alert(errormessage);
    return false;
    }
        else
    {
    document.getElementById("lblSO").innerHTML = "";
    }
    }    
//SALES ORDER SLIP SCRIPT
  function salesorderid()
  {
   var errormessage="";
    var element = document.getElementById("isValid");
    
    if(document.getElementById("salesid").value == "") {
    errormessage = " Please Enter the sales order number\n";
    element.innerHTML = errormessage;
    element.style.color="red";
    
    }
    if (errormessage !="")  {
   // alert(errormessage);
    return false;
    }
        else
    {
    document.getElementById("isValid").innerHTML = "";
    }
    }    
//OLD GOLD PURCHASE BILL FORMAT
  function oldpurchase()
  {
   var errormessage="";
    var element = document.getElementById("isValidbill_No");
    
    if(document.getElementById("OldpbillNo").value == "") {
    errormessage = " Please Enter the bill number\n";
    element.innerHTML = errormessage;
    element.style.color="red";
    
    }
    if (errormessage !="")  {
   // alert(errormessage);
    return false;
    }
        else
    {
    document.getElementById("isValidbill_No").innerHTML = "";
    }
    }    

    
    
// CATEGORY LIST
/* function validate_category()
  {
   var errormessage="";
     var element = document.getElementById("metalUsed1");
    if(document.getElementById("metalUsedID").value == "0") {
    errormessage = " Please Select The MetalType \n";
    element.innerHTML = errormessage;
    element.style.color="red";
    
    }
    
   if (errormessage !="")  {
   // alert(errormessage);
    return false;
    }
        else
    {
    document.getElementById("metalUsed1").innerHTML = "";
    }
    }
 */
 //daywise tag list 
 function DaywiseTag()
 {
  var errormessage="";
   var element = document.getElementById("Dlblcr");
   
   if(document.getElementById("Dfrmdate").value == "") {
   errormessage = " Please Select From Date\n";
   element.innerHTML = errormessage;
   element.style.color="red";
   
   }
    if(document.getElementById("Dtodate").value == "") {
   errormessage = " Please Select To Date\n";
   element.innerHTML = errormessage;
   element.style.color="red";
   
   }

  if (errormessage !="")  {
  // alert(errormessage);
   return false;
   }
       else
   {
   document.getElementById("Dlblcr").innerHTML = "";
   }
 }
 
 //day wise Stock Report 
 function DaywiseStockReport()
 {
	 var errormessage="";
	 var element = document.getElementById("DayWiselbl");
	 element.innerHTML = errormessage;
	 element.style.color="red";
	 
	 if(document.getElementById("DayWisefrmdate").value == "") {
		 errormessage = " Please Select From Date\n";
		 
	 }else if(checkDates(document.getElementById("DayWisefrmdate").value)){
		 errormessage = " ! Enter Valid From Date. \n";
	 }
	 if(document.getElementById("DayWisetodate").value == "") {
		 errormessage = " Please Select To Date\n";
	 }else if(checkDates(document.getElementById("DayWisetodate").value)){
		 errormessage = " ! Enter Valid To Date. \n";
	 }
	 
	/* if(document.getElementById("DayWisetodate").value < document.getElementById("DayWisefrmdate").value ){
		 errormessage = " ! To Date Can't Less than From Date. \n";
	 }*/
	 
	 if (errormessage !="")  {
		 element.innerHTML = errormessage;
		 element.style.color="red";
		 return false;
	 }
	 else
	 {
		 document.getElementById("DayWiselbl").innerHTML = "";
	 }
 }
 
 //POS Sales Register Validation
 function validate_POSSalesRegister()
 {
  var errormessage="";
   var element = document.getElementById("lblPOS_Register");
   
   if(document.getElementById("posfrmdate").value == "") {
   errormessage = " Please Select From Date\n";
   element.innerHTML = errormessage;
   element.style.color="red";
   
   }
    if(document.getElementById("postodate").value == "") {
   errormessage = " Please Select To Date\n";
   element.innerHTML = errormessage;
   element.style.color="red";
   
   }

  if (errormessage !="")  { 
   return false;
  }
  else
  {
    document.getElementById("lblPOS_Register").innerHTML = "";
  }
}
 
//POS Sales Return Register Validation
 function validate_POSSalesReturnRegister()
 {
  var errormessage="";
   var element = document.getElementById("lblPOSReturnRegister");
   
   if(document.getElementById("pos_frmdate").value == "") {
   errormessage = " Please Select From Date\n";
   element.innerHTML = errormessage;
   element.style.color="red";
   
   }
    if(document.getElementById("pos_todate").value == "") {
   errormessage = " Please Select To Date\n";
   element.innerHTML = errormessage;
   element.style.color="red";
   
   }

  if (errormessage !="")  {
   return false;
  }
  else
  {
    document.getElementById("lblPOSReturnRegister").innerHTML = "";
  }
} 
 function possalesInvoice()
 {
	 var errormessage=""; 
	 var element = document.getElementById("lblPOSSalesId");
	 if (document.getElementById("posSalesId").value == "" ){ 
	 errormessage = " Invoice number is required. \n";
	 		element.innerHTML = errormessage;
	         element.style.color="red";
	 }
	   
	  
	  if (errormessage !="")  {

	  // alert(errormessage);
	   return false;
	   }
	   else
	   {
	   document.getElementById("lblPOSSalesId").innerHTML = "";
	   } 
 }
 
//POS Purchase Register Validation
 function validate_POSPurchaseRegister()
 {
  var errormessage="";
   var element = document.getElementById("lblPOS_PurRegister");
   
   if(document.getElementById("pos_purfrmdate").value == "") {
   errormessage = " Please Select From Date\n";
   element.innerHTML = errormessage;
   element.style.color="red";
   
   }
    if(document.getElementById("pos_purtodate").value == "") {
   errormessage = " Please Select To Date\n";
   element.innerHTML = errormessage;
   element.style.color="red";
   
   }

  if (errormessage !="")  {  
   return false;
  }
  else
  {
    document.getElementById("lblPOS_PurRegister").innerHTML = "";
  }
}
 
//POS Purchase Return Register Validation
 function validate_POSPurchaseReturnRegister()
 {
  var errormessage="";
   var element = document.getElementById("lblPOS_PurRetRegister");
   
   if(document.getElementById("pos_purretfrmdate").value == "") {
   errormessage = " Please Select From Date\n";
   element.innerHTML = errormessage;
   element.style.color="red";
   
   }
    if(document.getElementById("pos_purrettodate").value == "") {
   errormessage = " Please Select To Date\n";
   element.innerHTML = errormessage;
   element.style.color="red";
   
   }

  if (errormessage !="")  {  
   return false;
  }
  else
  {
    document.getElementById("lblPOS_PurREtRegister").innerHTML = "";
  }
}
 
 
//Saving Scheme Receipt Register Validation
 function validate_SavingReceiptRegister()
 {
  var errormessage="";
   var element = document.getElementById("lblREC_Register");
   
   if(document.getElementById("recfrmdate").value == "") {
   errormessage = " Please Select From Date\n";
   element.innerHTML = errormessage;
   element.style.color="red";
   
   }
    if(document.getElementById("rectodate").value == "") {
   errormessage = " Please Select To Date\n";
   element.innerHTML = errormessage;
   element.style.color="red";
   
   }

  if (errormessage !="")  { 
   return false;
  }
  else
  {
    document.getElementById("lblREC_Register").innerHTML = "";
  }
}
 
 //Return Lot Stock Validation
 function validate_ReturnLotStock()
 {
	  var errormessage="";
	  var element = document.getElementById("lblErrorMsg");
	  
	  if(document.getElementById("stockFromDate").value == "") {
		  errormessage = " Please Select From Date\n";
		  element.innerHTML = errormessage;
		  element.style.color="red";
		  
	  }
	  if(document.getElementById("stockToDate").value == "") {
		  errormessage = " Please Select To Date\n";
		  element.innerHTML = errormessage;
		  element.style.color="red";
		  
	  }
	  
	  if (errormessage !="")  {		
		  return false;
	  }
	  else
	  {
		  document.getElementById("lblErrorMsg").innerHTML = "";
	  }
 } 
//lot add stock  Register
 function validate_lotaddstock()
 {
  var errormessage="";
   var element = document.getElementById("lotaddErrorMsg");
   
   if(document.getElementById("lotstockfrmdate").value == "") {
   errormessage = " Please Select From Date\n";
   element.innerHTML = errormessage;
   element.style.color="red";
   
   }
    if(document.getElementById("lotstocktodate").value == "") {
   errormessage = " Please Select To Date\n";
   element.innerHTML = errormessage;
   element.style.color="red";
   
   }

  if (errormessage !="")  {
 //  alert(errormessage);
   return false;
   }
       else
   {
   document.getElementById("lotaddErrorMsg").innerHTML = "";
   }
   }
 
//Estimate Sales Register Report
 function validate_ESalesRegister()
 {
	 var errormessage="";
	 var element = document.getElementById("ESRErrorMsg");
	 
	 if(document.getElementById("ESRfrmdate").value == "") {
		 errormessage = " Please Select From Date\n";
		 
	 }else if(checkDates(document.getElementById("ESRfrmdate").value)){
		 errormessage = " ! Enter Valid From Date. \n";
	 }
	 
	 if(document.getElementById("ESRtodate").value == "") {
		 errormessage = " Please Select To Date\n";
	 }else if(checkDates(document.getElementById("ESRtodate").value)){
		 errormessage = " ! Enter Valid To Date. \n";
	 }
	 
	 if (errormessage !="")
	 {
	  element.innerHTML = errormessage;
	  element.style.color="red";
	  return false;
	 }else {
	   document.getElementById("ESRErrorMsg").innerHTML = "";
	   return true;
	 } 
 }
 
//Estimate POS Sales Register Report
 function validate_EPSalesRegister()
 {
	 var errormessage="";
	 var element = document.getElementById("EPSRErrorMsg");
	 
	 if(document.getElementById("EPSRfrmdate").value == "") {
		 errormessage = " Please Select From Date\n";
		 
	 }else if(checkDates(document.getElementById("EPSRfrmdate").value)){
		 errormessage = " ! Enter Valid From Date. \n";
	 }
	 
	 if(document.getElementById("EPSRtodate").value == "") {
		 errormessage = " Please Select To Date\n";
	 }else if(checkDates(document.getElementById("EPSRtodate").value)){
		 errormessage = " ! Enter Valid To Date. \n";
	 }
	 
	 if (errormessage !="")
	 {
		 element.innerHTML = errormessage;
		 element.style.color="red";
		 return false;
	 }else {
		 document.getElementById("EPSRErrorMsg").innerHTML = "";
		 return true;
	 } 
 }

//SS Payment Dues From Customer Report 
function validate_SSPaymentDue(){
	 
	 var errormessage="";
	 var element = document.getElementById("lblSSPaymentDue");
	 
	 if (document.getElementById("ss_PaymentDue_tillDate").value == "" ){ 
		 errormessage = " * Till Date required. \n";
	 }else if(checkDates(document.getElementById("ss_PaymentDue_tillDate").value)){
		 errormessage = " ! Enter Valid Till Date. \n";
	 }
	 
		
	 if (document.getElementById('SSPaymentDueSingle').checked && document.getElementById("PaymentDue_schemeName").value =="0"){
		 errormessage = " * Scheme Name Required. \n";
	 }
	 	
	  if (errormessage !="")
	  {
		  element.innerHTML = errormessage;
		  element.style.color="red";
		  return false;
	   }else {
		   document.getElementById("lblSSPaymentDue").innerHTML = "";
		   return true;
	   } 

}

//Accounting Reports - Day Book 
function validate_AccDayBook(){
	
	var errormessage="";
	var element = document.getElementById("lblAccDayBook");
	var DBfromDate = document.getElementById("accDB_frmdate").value;
	var DBtoDate = document.getElementById("accDB_todate").value;
	
	 if(DBfromDate == "" || checkDates(DBfromDate)) {
		   errormessage = " Please Select Valid 'From Date'\n";
		   element.innerHTML = errormessage;
		   element.style.color="red";
	 }
	 if(DBtoDate == "" || checkDates(DBtoDate)) {
	   errormessage = " Please Select Valid 'To Date'\n";
	   element.innerHTML = errormessage;
	   element.style.color="red";
	 }

	  if(errormessage !="")  {
		  return false;
	   }else  {
	   document.getElementById("lblAccDayBook").innerHTML = "";
	   }
}


//Accounting Reports -Ledger Report Validation
function validate_LedgerBook(){
	
	var errormessage="";
	var element = document.getElementById("lblLedgerBook");	
	var DBfromDate = document.getElementById("from_journaldate").value;
	var DBtoDate = document.getElementById("to_journaldate").value;
	var LedgerType = document.getElementById("Ledger_Type").value;
		
	if(LedgerType != "All"){
		 if(DBfromDate == "" || checkDates(DBfromDate)) {
			   errormessage = " Please Select Valid 'From Date'\n";
			   element.innerHTML = errormessage;
			   element.style.color="red";
		 }
		 if(DBtoDate == "" || checkDates(DBtoDate)) {
		   errormessage = " Please Select Valid 'To Date'\n";
		   element.innerHTML = errormessage;
		   element.style.color="red";
		 }
	
		  if(errormessage !="")  {
			  return false;
		   }else  {
			   document.getElementById("lblLedgerBook").innerHTML = "";
		   }
	}
}

//Accounting Reports - Day Book 
function validate_AccDayBook(){
	
	var errormessage="";
	var element = document.getElementById("lblAccDayBook");
	var DBfromDate = document.getElementById("accDB_frmdate").value;
	var DBtoDate = document.getElementById("accDB_todate").value;
	
	 if(DBfromDate == "" || checkDates(DBfromDate)) {
		   errormessage = " Please Select Valid 'From Date'\n";
		   element.innerHTML = errormessage;
		   element.style.color="red";
	 }
	 if(DBtoDate == "" || checkDates(DBtoDate)) {
	   errormessage = " Please Select Valid 'To Date'\n";
	   element.innerHTML = errormessage;
	   element.style.color="red";
	 }

	  if(errormessage !="")  {
		  return false;
	   }else  {
	   document.getElementById("lblAccDayBook").innerHTML = "";
	   }
}

function checkDates(fieldVal){
	
	var currVal = fieldVal;
	var regexDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
	var dtArray = currVal.match(regexDatePattern);
	if(dtArray == null){
		return true;
	}
	var dtDay = dtArray[1];
	var dtMonth = dtArray[3];
	var dtYear = dtArray[5];
	if(dtMonth < 1 || dtMonth > 12){
		return true;
	}else if(dtDay < 1 || dtDay > 31){
		return true;
	}else if((dtMonth == 4 || dtMonth ==6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31){
		return true;
	}else if(dtMonth == 2){
		var isLeap = ( dtYear %4 ==0 && (dtYear % 100 != 0 || dtYear % 400 == 0) );
		if(dtDay > 29 || (dtDay == 29 && !isLeap)){
			return true;
		}
	}
	return false;
}
  