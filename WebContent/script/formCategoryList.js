var uripath=window.location.pathname;
	var update_record=$("#jewelTypeId");
	var errorType = $("#errorName").val();
	
	$(document).ready(function(){
		/*$('a[href="#category"]').click(function(e) {
			if(e.ctrlKey) {}
		    if(e.altKey){ window.location.href = "formledger.htm";}
		});*/
		$(".list_table").advancedtable({searchField: "#listsearch", loadElement: "#loader", searchCaseSensitive: false, ascImage: "images/up.png", descImage: "images/down.png"});
		$("#search").show();
		$('.noEnterSubmit').keypress(function(e){
		if ( e.which == 13 ) e.preventDefault();
		});
		
		if(uripath == "/rjms/formjeweltype.htm")
		{
			$("#update").attr("disabled","disabled");
			$("#insert").removeAttr("disabled");
		}
		
		if(errorType == "insertError")
		{	
			$("#update").attr("disabled","disabled");
			$("#insert").removeAttr("disabled");			
		}	
		
		$("#popupCategoryForm").click(initForm);
		$("#insert").click(submitCategory);
		
		var ssssd = $("#CatMetalTyp").val();
		if(ssssd == "Silver"){
			$('.catTabclass').hide();
			$("#goldsiltab ul li a").css('background','#fff');
			$('#goldsiltab ul li a[href="#silvert"]').css('background','#eee');
			$("#silvert").show();
		}
	});
	
	function initForm(){
		$('#metal_type').change(function() {
			var values = $('#metal_type').val();
		    if (values == 'Gold Ornaments') {
		        $('#base_metal').val('Gold');
		        $(".Popup_heading").text("New Gold Category Form");
		    }
			if (values == 'Silver Ornaments') {
		        $('#base_metal').val('Silver');
		        $(".Popup_heading").text("New Silver Category Form");
		    }
	    });
		
		$("#jewel_Name").val("");
		$("#metal_type").val(0);
		$("#base_metal").val("");
		$("#description").val("");
		$("#CatErrorMsg").html("");
		//var data = "Thali Necklace Earrings Nosepin Pendant Cufflinks Mangalsutra Broach/pin LadiesRing Drops Bangel Braslet Kada Kapu Mogappu Pendant ChainS Watch BlackBeads Jumki BabyEaring BabyBangel Babytops GentsBracelet GentsRing Ladiesbracelet BabyBracelet Coins PendantSet Neclaceset Chain Chain Bali ChainNecklaceSet Haar Villakku KunJUmChimiz Tahttu KamachiVilakku SandanaKinnam Thanda Schain Pista Khusboo Fancy Allindia Broddpayal MathuraPayal CastingMetti StoneMetti Keychain StKeyChain HipChain HpHipChain ".split(" ");
		//$("#jewel_Name").autocomplete(data);
		$("#jewel_Name").focus();
	}
	
	
	//*********** Category Creation POP Window *****************//
	function submitCategory() {
		var jewel_Name = $("#jewel_Name").val();
		var metal_type = $("#metal_type").val();
		var base_metal = $("#base_metal").val();
		var description = $("#description").val();
		
		if(base_metal == null || base_metal.length == 0 || base_metal == "undefined"){
			base_metal = "";		
		}
		if(description == null || description.length == 0 || description == "undefined"){
			description = "";		
		}
		
		if(jewel_Name == null || jewel_Name.length == 0 || jewel_Name == "undefined"){
			jewel_Name = "";
			$("#CatErrorMsg").html('<span class="error"> * Base Category Name cannot empty </span>');
			return false;
		}
		if(metal_type == null || metal_type.length == 0 || metal_type == "undefined" || metal_type ==  0){
			$("#CatErrorMsg").html('<span class="error"> * Please Select Metal Type </span>');
			return false;
		}else 
		{
			// Catogory existing check server side validation through ajax
			$.ajax({
				type:'GET',
				url:'CatValidate.htm',
				data : {jewel_Name : jewel_Name},
				success : function(data) {
					if(data == "true"){
						$("#CatErrorMsg").html('<span class="error"> * Base Catogory Name already exist.</span>');
						return false;
					}else{
						// Save POS Category pop up
						$.ajax({
							type:'POST',
							url:'submitJewelType.htm',
							data : {jewel_Name : jewel_Name, metal_type : metal_type, base_metal : base_metal, description: description},
							success : function(data){
								$('input.categorytext').val('');
								alert("'"+jewel_Name+"' "+"Added To Base Category");
								$("#CatMetalTyp").val(base_metal);
								$('#mask, .window').hide();

								location.reload();
								
							},
							error : function(xmlHttpRequest, textStatus, errorThrown){
									if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
										return;
							},	
							
						});// SaveCategory pop up ends here
					}			  				
				},
				error : function(xmlHttpRequest, textStatus, errorThrown){
						if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
							return;
				},	
				
			});	
		}
	}