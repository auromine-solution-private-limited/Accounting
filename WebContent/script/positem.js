//calulation for pcs and qty
$(document).ready(function(){
	 $('#qty').keyup(sum);
	 $('#ops_set').keyup(sum);
	 $('#costRt').keyup(salesRate);
	 $('#margin').keyup(salesRate);
	 $('#discountAmt').keyup(salesRate);//for auto zero
	 $('#vatAmt').keyup(salesRate);//for auto zero
	//for reverse calculation
	 $('#mrp').bind('input', function() {
		 $('#mrp').keyup(marginAmt);
	 });
	 
	 });
function sum()
{
	var tot=0;
	var set=$('#ops_set').val();
	var qty=$('#qty').val();
	if(!isNaN(set) && set.length!=0 &&!isNaN(qty) && qty.length!=0) 
	{
		tot=parseInt(set)*parseInt(qty);
	}
$('#totpieces').val(tot);
}
function salesRate()
{
	var salRateAmt=0.00;
	var amtftrDisnt=0.00;
	var mrpAmt=0.00;
	var costrate=$('#costRt').val();
	var margin=$('#margin').val();
	var discount=$('#discountAmt').val();
	var vat=$('#vatAmt').val();
	
	costrate =	 checkNull(costrate);
	margin =	 checkNull(margin);
	discount =	 checkNull(discount);
	vat =	 checkNull(vat);
	mrpAmt =	 checkNull(mrpAmt);
	
	if (margin == null || margin.length == 0 || margin == 'NaN' || margin == 'Infinity') {
		margin = 0.00;
	}
	
	 
	 
	//if(!isNaN(costrate) && costrate.length!=0 &&!isNaN(margin) && margin.length!=0 &&!isNaN(discount) && discount.length!=0 &&!isNaN(vat) && vat.length!=0) 
	//{
		salRateAmt=parseFloat(costrate)+(parseFloat(costrate)*parseFloat(margin)*0.01);	
		amtftrDisnt=parseFloat(salRateAmt)-(parseFloat(salRateAmt)*parseFloat(discount)*0.01);
		mrpAmt=parseFloat(amtftrDisnt)+(parseFloat(amtftrDisnt)*parseFloat(vat)*0.01);
	//}
	
	$('#salesRate').val(salRateAmt.toFixed(2));
	$('#mrp').val(mrpAmt.toFixed(0));
}
function marginAmt()
{
	var marginAmt=0.00;
	var t_amount=0.00;
	var salesRate=0.00;
	var mrpVal=$('#mrp').val();
	var costrate=$('#costRt').val();
	var reversVat=$('#vatAmt').val();
	var reverseDiscount=$('#discountAmt').val();
	if (mrpVal == null || mrpVal.length == 0 || mrpVal == 'NaN' || mrpVal == 'Infinity') {
		mrpVal = 0.00;
	}
	if (marginAmt == null || marginAmt.length == 0 || marginAmt == 'NaN' || marginAmt == 'Infinity') {
		marginAmt = 0.00;
	}
	if (t_amount == null || t_amount.length == 0 || t_amount == 'NaN' || t_amount == 'Infinity') {
		t_amount = 0.00;
	}
	if (salesRate == null || salesRate.length == 0 || salesRate == 'NaN' || salesRate == 'Infinity') {
		salesRate = 0.00;
	}
	if (costrate == null || costrate.length == 0 || costrate == 'NaN' || costrate == 'Infinity') {
		costrate = 0.00;
	}
	if (reversVat == null || reversVat.length == 0 || reversVat == 'NaN' || reversVat == 'Infinity') {
		reversVat = 0.00;
	}
	if (reverseDiscount == null || reverseDiscount.length == 0 || reverseDiscount == 'NaN' || reverseDiscount == 'Infinity') {
		reverseDiscount = 0.00;
	}
	
	//if(mrpVal!=0 && t_amount!=0)
	if(!isNaN(mrpVal)&& mrpVal.length!=0)
		{
		t_amount=(parseFloat(mrpVal)/(100+parseFloat(reversVat)))*100;
		salesRate=(parseFloat(t_amount)/(100-parseFloat(reverseDiscount)))*100;	
		if(costrate!=0){
		marginAmt=((parseFloat(salesRate)-parseFloat(costrate))/parseFloat(costrate))*100;
		}
		}
	if(marginAmt!=0)
	//	if(!isNaN(marginAmt)&& marginAmt.length!=0 )
			{
			$('#margin').val(marginAmt.toFixed(2));			
			}
	

$('#salesRate').val(salesRate.toFixed(2));	

}

