
 function ValidateNumberKeyPress(field, evt)
        {
            var charCode = (evt.which) ? evt.which : event.keyCode;
            var keychar = String.fromCharCode(charCode);

            if (charCode > 31 && (charCode < 48 || charCode > 57) && keychar != "." )
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
 
 function ValidateNumberPhone(field, evt)
 {
	 var charCode = (evt.which) ? evt.which : event.keyCode;
	 var keychar = String.fromCharCode(charCode);
	 
	 if (charCode > 31 && (charCode < 48 || charCode > 57) && keychar != "." && keychar!="+" )
	 {
		// alert("first if");
		 return false;
	 }
	 
	 if (keychar == "." && field.value.indexOf(".") != -1 && field.value != "0.0") 
	 {	//alert("2 if");
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
 
 
 
 


 function validateNegative() {
	  
	  alert(this.value+"  "+this.value.indexOf("."));
      
	  
	  	       
	
}
 document.ready(function(){
	 if (this.value == "." && this.value.indexOf(".") != -1 && this.value != "0.0") 
     {		
		  	alert("f");
     		return false;						   
     }
	 
 // Allow: backspace, delete, tab and escape
   if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 || event.keyCode == 109 ||event.keyCode == 189||event.keyCode == 190||event.keyCode == 110
       
        || // Allow: home, end, left, right .= 110 and 190 -=109 and 189
        (event.keyCode >= 35 && event.keyCode <= 39)) {
             // let it happen, don't do anything
             return true;
    }
    else {
        // Ensure that it is a number and stop the keypress
        if ((event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
            event.preventDefault();
            return false;
        }   
    }
 });
 


  
	   function ValidateNumberKeyUp(field)
        {
            if(document.selection.type == "Text")
            {
                return;
            }
        
             //save caret position
            var caretPos = getCaretPosition(field);            
            var fdlen = field.value.length;
       
            UnFormatNumber(field);
 
            var IsFound = /^-?\d+\.{0,1}\d*$/.test(field.value);
            if(!IsFound)
            {
                setSelectionRange(field, caretPos, caretPos);
                return false;             
            }
            
            field.value = FormatNumber(field.value);
            
            fdlen = field.value.length - fdlen;
            setSelectionRange(field, caretPos+fdlen, caretPos+fdlen);
        }

        function ValidateAndFormatNumber(NumberTextBox)
        {
            if(NumberTextBox.value == "") return;
            UnFormatNumber(NumberTextBox);
            var IsFound = /^-?\d+\.{0,1}\d*$/.test(NumberTextBox.value);
            if(!IsFound)
            {
                //alert("Not a number");
                NumberTextBox.focus();
                NumberTextBox.select();  
                return;             
            }            
            if(isNaN(parseFloat(NumberTextBox.value)))
            {
               // alert("Number exceeding float range");
                NumberTextBox.focus();
                NumberTextBox.select();               
            }
            NumberTextBox.value = FormatNumber(NumberTextBox.value);
        }
        
        function FormatNumber(fnum)
        {
            var orgfnum = fnum;
            var flagneg = false;
            
            if(fnum.charAt(0) == "-")
            {
                flagneg = true;
                fnum = fnum.substr(1, fnum.length-1);
            }
            
            psplit = fnum.split(".");

            var cnum = psplit[0],
	            parr = [],
	            j = cnum.length,
	            m = Math.floor(j / 20),
	            n = cnum.length % 20 || 20;

            // break the number into chunks of 3 digits; first chunk may be less than 3
            for (var i = 0; i < j; i += n) {
	            if (i != 0) {n = 20;}
	            parr[parr.length] = cnum.substr(i, n);
	            m -= 1;
            }

            // put chunks back together, separated by comma
            fnum = parr.join(",");

            // add the precision back in
            //if (psplit[1]) {fnum += "." + psplit[1];}
            if (orgfnum.indexOf(".") != -1)  
            {
                fnum += "." + psplit[1];
            }
            
            if(flagneg == true)
            {
                fnum = "-" + fnum;
            }
            
            return fnum;
        }
           
        function UnFormatNumber(obj)
        {
            if(obj.value == "") return;
            
            obj.value = obj.value.replace(/,/gi, "");
        }
        
        function getCaretPosition(objTextBox){

            var objTextBox = window.event.srcElement;

            var i = objTextBox.value.length;

            if (objTextBox.createTextRange){
                objCaret = document.selection.createRange().duplicate();
                while (objCaret.parentElement()==objTextBox &&
                  objCaret.move("character",1)==1) --i;
            }
            return i;
        }

        function setSelectionRange(input, selectionStart, selectionEnd) {
            if (input.setSelectionRange) {
                input.focus();
                input.setSelectionRange(selectionStart, selectionEnd);
            }
            else if (input.createTextRange) {
                var range = input.createTextRange();
                range.collapse(true);
                range.moveEnd('character', selectionEnd);
                range.moveStart('character', selectionStart);
                range.select();
            }
        }
function isNumberKey(evt) {

      var charCode = (evt.which) ? evt.which : event.keyCode;

       if ((charCode >= 48 && charCode <= 57) || (charCode == 8 ) ) {

         return true;

       }

       else {

            return false;

       }

     }

function extractNumber(obj, decimalPlaces, allowNegative)
{
	var temp = obj.value;
	
	// avoid changing things if already formatted correctly
	var reg0Str = '[0-9]*';
	if (decimalPlaces > 0) {
		reg0Str += '\\.?[0-9]{0,' + decimalPlaces + '}';
	} else if (decimalPlaces < 0) {
		reg0Str += '\\.?[0-9]*';
	}
	reg0Str = allowNegative ? '^-?' + reg0Str : '^' + reg0Str;
	reg0Str = reg0Str + '$';
	var reg0 = new RegExp(reg0Str);
	if (reg0.test(temp)) return true;

	// first replace all non numbers
	var reg1Str = '[^0-9' + (decimalPlaces != 0 ? '.' : '') + (allowNegative ? '-' : '') + ']';
	var reg1 = new RegExp(reg1Str, 'g');
	temp = temp.replace(reg1, '');

	if (allowNegative) {
		// replace extra negative
		var hasNegative = temp.length > 0 && temp.charAt(0) == '-';
		var reg2 = /-/g;
		temp = temp.replace(reg2, '');
		if (hasNegative) temp = '-' + temp;
	}
	
	if (decimalPlaces != 0) {
		var reg3 = /\./g;
		var reg3Array = reg3.exec(temp);
		if (reg3Array != null) {
			// keep only first occurrence of .
			//  and the number of places specified by decimalPlaces or the entire string if decimalPlaces < 0
			var reg3Right = temp.substring(reg3Array.index + reg3Array[0].length);
			reg3Right = reg3Right.replace(reg3, '');
			reg3Right = decimalPlaces > 0 ? reg3Right.substring(0, decimalPlaces) : reg3Right;
			temp = temp.substring(0,reg3Array.index) + '.' + reg3Right;
		}
	}
	
	obj.value = temp;
}


//Validate Number key Press
function ValidateIntegerNumberKeyPress(field, evt)
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