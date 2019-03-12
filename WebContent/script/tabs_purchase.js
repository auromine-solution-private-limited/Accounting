

$(document).ready(function(){
	$(".menu > li").click(function(e){
		switch(e.target.id){
			case "news":
				//change status & style menu
				$("#news").addClass("active");
				$("#tutorials").removeClass("active");
				$("#links").removeClass("active");
				$("#manzoor").removeClass("active");
				$("#tuts").removeClass("active");
				$("#line").removeClass("active");
				$("#line2").removeClass("active");
				//display selected division, hide others
				$("div.news").fadeIn();
				$("div.tutorials").css("display", "none");
				$("div.links").css("display", "none");
				$("div.manzoor").css("display", "none");
				$("div.tuts").css("display", "none");
				$("div.line").css("display", "none");
				$("div.line2").css("display", "none");
			break;
			case "tutorials":
				//change status & style menu
				$("#news").removeClass("active");
				$("#tutorials").addClass("active");
				$("#links").removeClass("active");
				$("#manzoor").removeClass("active");
				$("#tuts").removeClass("active");
				$("#line").removeClass("active");
				$("#line2").removeClass("active");
				//display selected division, hide others
				$("div.news").css("display", "none");
				$("div.tutorials").fadeIn();
				$("div.links").css("display", "none");
				$("div.manzoor").css("display", "none");
				$("div.tuts").css("display", "none");
				$("div.line").css("display", "none");
				$("div.line2").css("display", "none");
			break;
			case "links":
				//change status & style menu
				$("#news").removeClass("active");
				$("#tutorials").removeClass("active");
				$("#links").addClass("active");
				$("#manzoor").removeClass("active");
				$("#tuts").removeClass("active");
				$("#line").removeClass("active");
				$("#line2").removeClass("active");
				//display selected division, hide others
				$("div.news").css("display", "none");
				$("div.tutorials").css("display", "none");
				$("div.links").fadeIn();
				$("div.manzoor").css("display", "none");
				$("div.tuts").css("display", "none");
				$("div.line").css("display", "none");
				$("div.line2").css("display", "none");
			break;
			case "manzoor":
				//change status & style menu
				$("#news").removeClass("active");
				$("#tutorials").removeClass("active");
				$("#links").removeClass("active");
				$("#manzoor").addClass("active");
				$("#tuts").removeClass("active");
				$("#line").removeClass("active");
				$("#line2").removeClass("active");
				//display selected division, hide others
				$("div.news").css("display", "none");
				$("div.tutorials").css("display", "none");
				$("div.links").css("display", "none");
				$("div.manzoor").fadeIn();
				$("div.tuts").css("display", "none");
				$("div.line").css("display", "none");
				$("div.line2").css("display", "none");
			break;
			case "tuts":
				//change status & style menu
				$("#news").removeClass("active");
				$("#tutorials").removeClass("active");
				$("#links").removeClass("active");
				$("#manzoor").removeClass("active");
				$("#tuts").addClass("active");
				$("#line").removeClass("active");
				$("#line2").removeClass("active");
				//display selected division, hide others
				$("div.news").css("display", "none");
				$("div.tutorials").css("display", "none");
				$("div.links").css("display", "none");
				$("div.manzoor").css("display", "none");
				$("div.tuts").fadeIn();
				$("div.line").css("display", "none");
				$("div.line2").css("display", "none");
			break;
			case "line":
				//change status & style menu
				$("#news").removeClass("active");
				$("#tutorials").removeClass("active");
				$("#links").removeClass("active");
				$("#manzoor").removeClass("active");
				$("#tuts").removeClass("active");
				$("#line").addClass("active");
				$("#line2").removeClass("active");
				//display selected division, hide others
				$("div.news").css("display", "none");
				$("div.tutorials").css("display", "none");
				$("div.links").css("display", "none");
				$("div.manzoor").css("display", "none");
				$("div.tuts").css("display", "none");
				$("div.line").fadeIn();
				$("div.line2").css("display", "none");
			break;
			case "line2":
				//change status & style menu
				$("#news").removeClass("active");
				$("#tutorials").removeClass("active");
				$("#links").removeClass("active");
				$("#manzoor").removeClass("active");
				$("#tuts").removeClass("active");
				$("#line").removeClass("active");
				$("#line2").addClass("active");
				//display selected division, hide others
				$("div.news").css("display", "none");
				$("div.tutorials").css("display", "none");
				$("div.links").css("display", "none");
				$("div.manzoor").css("display", "none");
				$("div.tuts").css("display", "none");
				$("div.line").css("display", "none");
				$("div.line2").fadeIn();
			break;
		}
		
		//alert(e.target.id);
		return false;
	});
});