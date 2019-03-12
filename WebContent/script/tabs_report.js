/************************/
$(document).ready(function(){
	
	$('.treeview .list ul').hide();
	var treeList = $('.treeview .list a');
	$(treeList).click(firstClick);
	
	$('.treeview .list1').click(function(){
		$('.treeview .list .ul').slideUp('fast');
		$('.treeview .list a span').not($(this)).addClass("expand");
	});
			/**$('.treeview .list ul').hide();
			$('.treeview .list1').click(function(){
				$('.treeview .list .ul').slideUp('fast');
				$('.treeview .list a span').not($(this)).addClass("expand");
			});
		//	$('.treeview .link').click(LinkClick);
			$('.treeview .list').toggle(firstClick, secondClick);
			$('.treeview .list a, .treeview .list1').click(function(){
				$('.treeview .list a span').not($(this)).addClass("expand");
				//$('span', this).addClass("collapse");
			});
			*/
			$(".content_tab").hide();
			$(".news").show();
			$('.treeview .link').click(tabs_report);
			
			
			/*****    Lot Stock  *******/
			
			$(".lotDetailedStock").hide();
			var ParamIdVal = window.location.search;
			if(ParamIdVal == "?lotDetailedStock"){
				$(".content_tab").hide();
				$(".treeview li.list, .treeview li.list1").hide();
				$(".lotDetailedStock").show();
				$(".lotdetialedstock").show();
			}
			else{
				$(".lotDetailedStock").hide();
			}
			
});

function firstClick(){
	//$(".ssssd").append("<b>click One</b><br>");
	
	if($(this).closest(".list").children(".ul").is(':visible')){
		$(this).parents(".list").children(".ul").slideUp('fast');
		$('span', this).removeClass("collapse");
		$('span', this).addClass("expand");
		//$(".ssssd").append("<b>click One-------SlideDown</b><br>");
		return false;
	}
	if ($(this).closest(".list").children(".ul").not(':visible')){
		$('.ul').not($(this)).slideUp('fast');
		$('span').not($(this)).removeClass("collapse").addClass("expand");
		
		$(this).parents(".list").children(".ul").slideDown('fast');
		$('span', this).removeClass("expand");
		$('span', this).addClass("collapse");
		//$(".ssssd").append("<b>click One------SlideUp</b><br>");
		return false;
	} 
	
}
function secondClick(){
	
	//$(".ssssd").append("click Two<br>");
	
	var List_child_ul = $(this).parents(".list").children(".ul"); 
	if (List_child_ul.slideDown){
		$(this).parents(".list").children(".ul").slideUp('fast');
		$('span', this).removeClass("collapse");
		$('span', this).addClass("expand");
		$(".ssssd").append("click Two----<br>");
	}
	else if (List_child_ul.slideUp){
		$('.ul').not($(this)).slideUp('fast');
		$('span').not($(this)).removeClass("collapse").addClass("expand");
		
		$(this).parents(".list").children(".ul").slideDown('fast');
		$('span', this).removeClass("expand");
		$('span', this).addClass("collapse");
		$(".ssssd").append("click Two--====--<br>");
	}
}
function tabs_report(){
	var rel = $(this).attr("rel");
	$(".content_tab").hide();
	$(rel).show();
}
