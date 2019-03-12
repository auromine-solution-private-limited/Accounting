
function validate_account()
{
 var errormessage="";
 var element = document.getElementById("lblledgerName");
  
  if (document.getElementById("ledgerName").value == "" ){ 
		errormessage = "Please Enter the Id or account name \n";
		element.innerHTML  = errormessage;
        element.style.color="red";
}
  
 if (errormessage !="")  {

	 return false;
  }
  else
  {
	document.getElementById("lblledgerName").innerHTML = "";	
  }
  }


function validate_item()
{
 var errormessage="";
 var element = document.getElementById("lblitemName");
  
  if (document.getElementById("itemName").value == "" ){ 
		errormessage = "Please Enter the item name \n";
		element.innerHTML  = errormessage;
        element.style.color="red";
}
  
 if (errormessage !="")  {

	 return false;
  }
  else
  {
	document.getElementById("lblitemName").innerHTML = "";	
  }
  }

function validate_order(evt)
{
 var errormessage="";
 var element = document.getElementById("lblorderNo");
   if (document.getElementById("orderNo").value == "" ){ 
		errormessage = "Please Enter the order No\n";
		element.innerHTML  = errormessage;
        element.style.color="red";
}
  
 if (errormessage !="")  {

	 return false;
  }
  else
  {
	document.getElementById("lblorderNo").innerHTML = "";	
  }
}



function ValidateOrderNumberKeyPress(field, evt)
{
    var charCode = (evt.which) ? evt.which : event.keyCode;
    var keychar = String.fromCharCode(charCode);

    if (charCode > 31 && (charCode < 48 || charCode > 57 ) || charCode == 190 )
    {
        return false;
    }

    if (keychar == "." && field.value.indexOf(".") != -1 && field.value != "0.0") 
    {
    	return false;						   
    }
        
    if(keychar == "-")
    {
        if (field.value.indexOf("-") != -1 /* || field.value[0] == "-" */) 
        {
            return false;
        }
        else
        {
            //save caret position
            var caretPos = getCaretPosition(field);
            if(caretPos != 0)
            {
                return false;
            }
        }
    }

    return true;
}

function validate_smith()
{
 var errormessage="";
 var element = document.getElementById("lblsmithName");
  
  if (document.getElementById("smithName").value == "" ){ 
		errormessage = "Please Enter the Smith Name \n";
		element.innerHTML  = errormessage;
        element.style.color="red";
}
  
 if (errormessage !="")  {

	 return false;
  }
  else
  {
	document.getElementById("lblsmithName").innerHTML = "";	
  }
  }


// Validation For Sales Estimate search
function validate_SalesEstimate()
{
 var errormessage="";
 var element = document.getElementById("lbltagName");
  
  if (document.getElementById("seic").value == "" ){ 
		errormessage = "Please Enter the Tag Code \n";
		element.innerHTML  = errormessage;
        element.style.color="red";
}
  
 if (errormessage !="")  {

	 return false;
  }
  else
  {
	document.getElementById("lbltagName").innerHTML = "";	
  }
}
