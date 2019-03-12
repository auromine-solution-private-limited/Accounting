$(document).ready(function(){
	$("#schemeNames").change(ajaxSSCardCancelDate);
	
	$('#schemeName').change(function() {
		ajaxSSCardDate();
	});
	
	$('#metalUsedID').change(function(){
		getCategoryName();
	});
	
	$('#lotMetalUsedID').change(function(){
		getLotCategoryName();
	});
	$('#metalUsedOrn').change(function(){
		getLotCategoryNameOrn();
	});

	$('#detailedStockID').click(function()
	{
		if($('#metalUsedID').val()=='0')
		{
			alert("Select Metal Used !");
			return false;
		}
	});
	$('#lotDetailedStockID').click(function()
	{
		if($('#lotMetalUsedID').val()=='0')
		{
			alert("Select Metal Used !");
			return false;
		}
	});
	$('#lotDetailStockOrn').click(function()
			{
		if($('#metalUsedOrn').val()=='0')
		{
			alert("Select Metal Used !");
			return false;
		}
	});
});

function ajaxSSCardDate() {

	var schemeName = $("#schemeName").val();
			$.ajax({
			type : 'GET',
			url : 'Getcardissuedate.htm',
			data : {
				schemeName : schemeName
			},
			success : function(data) {
				
				$("select[id$=joinDate] > option").remove();
				var arr = new Array();
				data = data.replace('[', '');
				data = data.replace(']', '');
				data = data.replace('[', '');
				data = data.replace(']', '');
				arr = data.split(',');
				$.each(arr,	function(index, item) {
				var result = item.replace(/^\s+|\s+$/g, '').replace(/\s+/g, ' ');
				if (item != ']' && item != ',' && item != ' ' && item != ''	&& item != '[') {
					$("#joinDate").get(0).options[index] = new Option(result);
				}
			});
			$('select option:empty').remove();
			},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			if (xmlHttpRequest.readyState = 0 || xmlHttpRequest.status == 0)
				return;
		},
	});
	
}

function ajaxSSCardCancelDate() {

	var schemeName = $("#schemeNames").val();
	
	$.ajax({
		type : 'GET',
		url : 'getstartdate.htm',
		data : {
			schemeName : schemeName
		},
		success : function(data) {
		
			$("select[id$=startDate] > option").remove();
			var arr = new Array();
			data = data.replace('[', '');
			data = data.replace(']', '');
			data = data.replace('[', '');
			data = data.replace(']', '');
			arr = data.split(',');
			$.each(arr,	function(index, item) {
				var result = item.replace(/^\s+|\s+$/g, '').replace(/\s+/g, ' ');
				if (item != ']' && item != ',' && item != ' ' && item != ''	&& item != '[') {
					$("#startDate").get(0).options[index] = new Option(result);
				}
			});
			$('select option:empty').remove();
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			if (xmlHttpRequest.readyState = 0 || xmlHttpRequest.status == 0)
				return;
		},
	});
	
}
function getCategoryName()
{

	var categoryName = $("#metalUsedID").val();

	$.ajax({
		type : 'GET',
		url : 'getbaseCategoryName.htm',
		data : {
			categoryName : categoryName
		},
		success : function(data) {
		
			$("select[id$=baseCategory] > option").remove();
			var arr = new Array();
			data = data.replace('[', '');
			data = data.replace(']', '');
			data = data.replace('[', '');
			data = data.replace(']', '');
			arr = data.split(',');
			$.each(arr,	function(index, item) {
				var result = item.replace(/^\s+|\s+$/g, '').replace(/\s+/g, ' ');
				if (item != ']' && item != ',' && item != ' ' && item != ''	&& item != '[') {
					$("#baseCategory").get(0).options[index] = new Option(result);
				}
			});
			$('select option:empty').remove();
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			if (xmlHttpRequest.readyState = 0 || xmlHttpRequest.status == 0)
				return;
		},
	});
}
function getLotCategoryName()
{
	
	var categoryName = $("#lotMetalUsedID").val();
	
	$.ajax({
		type : 'GET',
		url : 'getbaseCategoryName.htm',
		data : {
			categoryName : categoryName
		},
		success : function(data) {
			
			$("select[id$=lotBaseCategory] > option").remove();
			var arr = new Array();
			data = data.replace('[', '');
			data = data.replace(']', '');
			data = data.replace('[', '');
			data = data.replace(']', '');
			arr = data.split(',');
			$.each(arr,	function(index, item) {
				var result = item.replace(/^\s+|\s+$/g, '').replace(/\s+/g, ' ');
				if (item != ']' && item != ',' && item != ' ' && item != ''	&& item != '[') {
					$("#lotBaseCategory").get(0).options[index] = new Option(result);
				}
			});
			$('select option:empty').remove();
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			if (xmlHttpRequest.readyState = 0 || xmlHttpRequest.status == 0)
				return;
		},
	});
}
function getLotCategoryNameOrn()
{
	var categoryName = $("#metalUsedOrn").val();
	$.ajax({
		type : 'GET',
		url : 'getbaseCategoryName.htm',
		data : {
			categoryName : categoryName
		},
		success : function(data) {
			
			$("select[id$=lotBaseCategoryOrn] > option").remove();
			var arr = new Array();
			data = data.replace('[', '');
			data = data.replace(']', '');
			data = data.replace('[', '');
			data = data.replace(']', '');
			arr = data.split(',');
			$.each(arr,	function(index, item) {
				var result = item.replace(/^\s+|\s+$/g, '').replace(/\s+/g, ' ');
				if (item != ']' && item != ',' && item != ' ' && item != ''	&& item != '[') {
					$("#lotBaseCategoryOrn").get(0).options[index] = new Option(result);
				}
			});
			$('select option:empty').remove();
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			if (xmlHttpRequest.readyState = 0 || xmlHttpRequest.status == 0)
				return;
		},
	});
}
