$('#journalType').change(function() {
	$('.DebitTypepayment, .CreditTypepayment, .DebitTypereceipt, .CreditTypereceipt, .CreditTypejournal, .DebitTypejournal').hide();
	var journalType=$(this).val();
	
	if(journalType=='Receipt')
		{
		$('.DebitTypereceipt, .CreditTypereceipt').show();
		}
	else if(journalType=='Payment')
		{
		$('.DebitTypepayment, .CreditTypepayment').show();
		}
	else if(journalType=='Journal')
		{
		$('.DebitTypejournal, .CreditTypejournal').show();
		}
	else if(journalType=='Contra')
		{
		$('.DebitTypereceipt, .CreditTypepayment').show();
		}
	else{
		$('.DebitTypepayment, .CreditTypepayment, .DebitTypereceipt, .CreditTypereceipt, .CreditTypejournal, .DebitTypejournal').hide();
	}
});

