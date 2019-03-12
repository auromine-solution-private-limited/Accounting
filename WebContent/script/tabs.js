/***************************/
//@Author: Adrian "yEnS" Mato Gondelle & Ivan Guardado Castro
//@website: www.yensdesign.com
//@email: yensamg@gmail.com
//@license: Feel free to use it, but keep this credits please!					
/***************************/

$(document).ready(function(){
	$(".menu_acc > li").click(function(e){
		switch(e.target.id){
			case "Bank":
				//change status & style menu
				$("#Bank").addClass("active");
				$("#Bank_Loan").removeClass("active");
				$("#Bank_OCC").removeClass("active");
				$("#Capital").removeClass("active");
				$("#Cash").removeClass("active");
				$("#Current_Assets").removeClass("active");
				$("#Current_Lia").removeClass("active");
				$("#Direct_Exp").removeClass("active");
				$("#Direct_Income").removeClass("active");
				$("#Fixed_Assets").removeClass("active");
				$("#Indirect_Exp").removeClass("active");
				$("#Indirect_Income").removeClass("active");
				$("#Profit_Loss").removeClass("active");
				$("#Purchase").removeClass("active");
				$("#Sales").removeClass("active");
				$("#Stock").removeClass("active");
				//display selected division, hide others
				$("div.Bank").fadeIn();
				$("div.Bank_Loan").css("display", "none");
				$("div.Bank_OCC").css("display", "none");
				$("div.Capital").css("display", "none");
				$("div.Cash").css("display", "none");
				$("div.Current_Assets").css("display", "none");
				$("div.Current_Lia").css("display", "none");
				$("div.Direct_Exp").css("display", "none");
				$("div.Direct_Income").css("display", "none");
				$("div.Fixed_Assets").css("display", "none");
				$("div.Indirect_Exp").css("display", "none");
				$("div.Indirect_Income").css("display", "none");
				$("div.Profit_Loss").css("display", "none");
				$("div.Purchase").css("display", "none");
				$("div.Sales").css("display", "none");
				$("div.Stock").css("display", "none");
			break;
			case "Bank_Loan":
				//change status & style menu
				$("#Bank").removeClass("active");
				$("#Bank_Loan").addClass("active");
				$("#Bank_OCC").removeClass("active");
				$("#Capital").removeClass("active");
				$("#Cash").removeClass("active");
				$("#Current_Assets").removeClass("active");
				$("#Current_Lia").removeClass("active");
				$("#Direct_Exp").removeClass("active");
				$("#Direct_Income").removeClass("active");
				$("#Fixed_Assets").removeClass("active");
				$("#Indirect_Exp").removeClass("active");
				$("#Indirect_Income").removeClass("active");
				$("#Profit_Loss").removeClass("active");
				$("#Purchase").removeClass("active");
				$("#Sales").removeClass("active");
				$("#Stock").removeClass("active");
				//display selected division, hide others
				$("div.Bank").css("display", "none");
				$("div.Bank_Loan").fadeIn();
				$("div.Bank_OCC").css("display", "none");
				$("div.Capital").css("display", "none");
				$("div.Cash").css("display", "none");
				$("div.Current_Assets").css("display", "none");
				$("div.Current_Lia").css("display", "none");
				$("div.Direct_Exp").css("display", "none");
				$("div.Direct_Income").css("display", "none");
				$("div.Fixed_Assets").css("display", "none");
				$("div.Indirect_Exp").css("display", "none");
				$("div.Indirect_Income").css("display", "none");
				$("div.Profit_Loss").css("display", "none");
				$("div.Purchase").css("display", "none");
				$("div.Sales").css("display", "none");
				$("div.Stock").css("display", "none");
			break;
			case "Bank_OCC":
				//change status & style menu
				$("#Bank").removeClass("active");
				$("#Bank_Loan").removeClass("active");
				$("#Bank_OCC").addClass("active");
				$("#Capital").removeClass("active");
				$("#Cash").removeClass("active");
				$("#Current_Assets").removeClass("active");
				$("#Current_Lia").removeClass("active");
				$("#Direct_Exp").removeClass("active");
				$("#Direct_Income").removeClass("active");
				$("#Fixed_Assets").removeClass("active");
				$("#Indirect_Exp").removeClass("active");
				$("#Indirect_Income").removeClass("active");
				$("#Profit_Loss").removeClass("active");
				$("#Purchase").removeClass("active");
				$("#Sales").removeClass("active");
				$("#Stock").removeClass("active");
				//display selected division, hide others
				$("div.Bank").css("display", "none");
				$("div.Bank_Loan").css("display", "none");
				$("div.Bank_OCC").fadeIn();
				$("div.Capital").css("display", "none");
				$("div.Cash").css("display", "none");
				$("div.Current_Assets").css("display", "none");
				$("div.Current_Lia").css("display", "none");
				$("div.Direct_Exp").css("display", "none");
				$("div.Direct_Income").css("display", "none");
				$("div.Fixed_Assets").css("display", "none");
				$("div.Indirect_Exp").css("display", "none");
				$("div.Indirect_Income").css("display", "none");
				$("div.Profit_Loss").css("display", "none");
				$("div.Purchase").css("display", "none");
				$("div.Sales").css("display", "none");
				$("div.Stock").css("display", "none");
			break;
			case "Capital":
				//change status & style menu
				$("#Bank").removeClass("active");
				$("#Bank_Loan").removeClass("active");
				$("#Bank_OCC").removeClass("active");
				$("#Capital").addClass("active");
				$("#Cash").removeClass("active");
				$("#Current_Assets").removeClass("active");
				$("#Current_Lia").removeClass("active");
				$("#Direct_Exp").removeClass("active");
				$("#Direct_Income").removeClass("active");
				$("#Fixed_Assets").removeClass("active");
				$("#Indirect_Exp").removeClass("active");
				$("#Indirect_Income").removeClass("active");
				$("#Profit_Loss").removeClass("active");
				$("#Purchase").removeClass("active");
				$("#Sales").removeClass("active");
				$("#Stock").removeClass("active");
				//display selected division, hide others
				$("div.Bank").css("display", "none");
				$("div.Bank_Loan").css("display", "none");
				$("div.Bank_OCC").css("display", "none");
				$("div.Capital").fadeIn();
				$("div.Cash").css("display", "none");
				$("div.Current_Assets").css("display", "none");
				$("div.Current_Lia").css("display", "none");
				$("div.Direct_Exp").css("display", "none");
				$("div.Direct_Income").css("display", "none");
				$("div.Fixed_Assets").css("display", "none");
				$("div.Indirect_Exp").css("display", "none");
				$("div.Indirect_Income").css("display", "none");
				$("div.Profit_Loss").css("display", "none");
				$("div.Purchase").css("display", "none");
				$("div.Sales").css("display", "none");
				$("div.Stock").css("display", "none");
			break;
			case "Cash":
				//change status & style menu
				$("#Bank").removeClass("active");
				$("#Bank_Loan").removeClass("active");
				$("#Bank_OCC").removeClass("active");
				$("#Capital").removeClass("active");
				$("#Cash").addClass("active");
				$("#Current_Assets").removeClass("active");
				$("#Current_Lia").removeClass("active");
				$("#Direct_Exp").removeClass("active");
				$("#Direct_Income").removeClass("active");
				$("#Fixed_Assets").removeClass("active");
				$("#Indirect_Exp").removeClass("active");
				$("#Indirect_Income").removeClass("active");
				$("#Profit_Loss").removeClass("active");
				$("#Purchase").removeClass("active");
				$("#Sales").removeClass("active");
				$("#Stock").removeClass("active");
				//display selected division, hide others
				$("div.Bank").css("display", "none");;
				$("div.Bank_Loan").css("display", "none");
				$("div.Bank_OCC").css("display", "none");
				$("div.Capital").css("display", "none");
				$("div.Cash").fadeIn();
				$("div.Current_Assets").css("display", "none");
				$("div.Current_Lia").css("display", "none");
				$("div.Direct_Exp").css("display", "none");
				$("div.Direct_Income").css("display", "none");
				$("div.Fixed_Assets").css("display", "none");
				$("div.Indirect_Exp").css("display", "none");
				$("div.Indirect_Income").css("display", "none");
				$("div.Profit_Loss").css("display", "none");
				$("div.Purchase").css("display", "none");
				$("div.Sales").css("display", "none");
				$("div.Stock").css("display", "none");
			break;
			case "Current_Assets":
				//change status & style menu
				$("#Bank").removeClass("active");
				$("#Bank_Loan").removeClass("active");
				$("#Bank_OCC").removeClass("active");
				$("#Capital").removeClass("active");
				$("#Cash").removeClass("active");
				$("#Current_Assets").addClass("active");
				$("#Current_Lia").removeClass("active");
				$("#Direct_Exp").removeClass("active");
				$("#Direct_Income").removeClass("active");
				$("#Fixed_Assets").removeClass("active");
				$("#Indirect_Exp").removeClass("active");
				$("#Indirect_Income").removeClass("active");
				$("#Profit_Loss").removeClass("active");
				$("#Purchase").removeClass("active");
				$("#Sales").removeClass("active");
				$("#Stock").removeClass("active");
				//display selected division, hide others
				$("div.Bank").css("display", "none");
				$("div.Bank_Loan").css("display", "none");
				$("div.Bank_OCC").css("display", "none");
				$("div.Capital").css("display", "none");
				$("div.Cash").css("display", "none");
				$("div.Current_Assets").fadeIn();
				$("div.Current_Lia").css("display", "none");
				$("div.Direct_Exp").css("display", "none");
				$("div.Direct_Income").css("display", "none");
				$("div.Fixed_Assets").css("display", "none");
				$("div.Indirect_Exp").css("display", "none");
				$("div.Indirect_Income").css("display", "none");
				$("div.Profit_Loss").css("display", "none");
				$("div.Purchase").css("display", "none");
				$("div.Sales").css("display", "none");
				$("div.Stock").css("display", "none");
			break;
			case "Current_Lia":
				//change status & style menu
				$("#Bank").removeClass("active");
				$("#Bank_Loan").removeClass("active");
				$("#Bank_OCC").removeClass("active");
				$("#Capital").removeClass("active");
				$("#Cash").removeClass("active");
				$("#Current_Assets").removeClass("active");
				$("#Current_Lia").addClass("active");
				$("#Direct_Exp").removeClass("active");
				$("#Direct_Income").removeClass("active");
				$("#Fixed_Assets").removeClass("active");
				$("#Indirect_Exp").removeClass("active");
				$("#Indirect_Income").removeClass("active");
				$("#Profit_Loss").removeClass("active");
				$("#Purchase").removeClass("active");
				$("#Sales").removeClass("active");
				$("#Stock").removeClass("active");
				//display selected division, hide others
				$("div.Bank").css("display", "none");
				$("div.Bank_Loan").css("display", "none");
				$("div.Bank_OCC").css("display", "none");
				$("div.Capital").css("display", "none");
				$("div.Cash").css("display", "none");
				$("div.Current_Assets").css("display", "none");
				$("div.Current_Lia").fadeIn();
				$("div.Direct_Exp").css("display", "none");
				$("div.Direct_Income").css("display", "none");
				$("div.Fixed_Assets").css("display", "none");
				$("div.Indirect_Exp").css("display", "none");
				$("div.Indirect_Income").css("display", "none");
				$("div.Profit_Loss").css("display", "none");
				$("div.Purchase").css("display", "none");
				$("div.Sales").css("display", "none");
				$("div.Stock").css("display", "none");
			break;
			case "Direct_Exp":
				//change status & style menu
				$("#Bank").removeClass("active");
				$("#Bank_Loan").removeClass("active");
				$("#Bank_OCC").removeClass("active");
				$("#Capital").removeClass("active");
				$("#Cash").removeClass("active");
				$("#Current_Assets").removeClass("active");
				$("#Current_Lia").removeClass("active");
				$("#Direct_Exp").addClass("active");
				$("#Direct_Income").removeClass("active");
				$("#Fixed_Assets").removeClass("active");
				$("#Indirect_Exp").removeClass("active");
				$("#Indirect_Income").removeClass("active");
				$("#Profit_Loss").removeClass("active");
				$("#Purchase").removeClass("active");
				$("#Sales").removeClass("active");
				$("#Stock").removeClass("active");
				//display selected division, hide others
				$("div.Bank").css("display", "none");
				$("div.Bank_Loan").css("display", "none");
				$("div.Bank_OCC").css("display", "none");
				$("div.Capital").css("display", "none");
				$("div.Cash").css("display", "none");
				$("div.Current_Assets").css("display", "none");
				$("div.Current_Lia").css("display", "none");
				$("div.Direct_Exp").fadeIn();
				$("div.Direct_Income").css("display", "none");
				$("div.Fixed_Assets").css("display", "none");
				$("div.Indirect_Exp").css("display", "none");
				$("div.Indirect_Income").css("display", "none");
				$("div.Profit_Loss").css("display", "none");
				$("div.Purchase").css("display", "none");
				$("div.Sales").css("display", "none");
				$("div.Stock").css("display", "none");
			break;
			case "Direct_Income":
				//change status & style menu
				$("#Bank").removeClass("active");
				$("#Bank_Loan").removeClass("active");
				$("#Bank_OCC").removeClass("active");
				$("#Capital").removeClass("active");
				$("#Cash").removeClass("active");
				$("#Current_Assets").removeClass("active");
				$("#Current_Lia").removeClass("active");
				$("#Direct_Exp").removeClass("active");
				$("#Direct_Income").addClass("active");;
				$("#Fixed_Assets").removeClass("active");
				$("#Indirect_Exp").removeClass("active");
				$("#Indirect_Income").removeClass("active");
				$("#Profit_Loss").removeClass("active");
				$("#Purchase").removeClass("active");
				$("#Sales").removeClass("active");
				$("#Stock").removeClass("active");
				//display selected division, hide others
				$("div.Bank").css("display", "none");
				$("div.Bank_Loan").css("display", "none");
				$("div.Bank_OCC").css("display", "none");
				$("div.Capital").css("display", "none");
				$("div.Cash").css("display", "none");
				$("div.Current_Assets").css("display", "none");
				$("div.Current_Lia").css("display", "none");
				$("div.Direct_Exp").css("display", "none");
				$("div.Direct_Income").fadeIn();
				$("div.Fixed_Assets").css("display", "none");
				$("div.Indirect_Exp").css("display", "none");
				$("div.Indirect_Income").css("display", "none");
				$("div.Profit_Loss").css("display", "none");
				$("div.Purchase").css("display", "none");
				$("div.Sales").css("display", "none");
				$("div.Stock").css("display", "none");
			break;
			case "Fixed_Assets":
				//change status & style menu
				$("#Bank").removeClass("active");
				$("#Bank_Loan").removeClass("active");
				$("#Bank_OCC").removeClass("active");
				$("#Capital").removeClass("active");
				$("#Cash").removeClass("active");
				$("#Current_Assets").removeClass("active");
				$("#Current_Lia").removeClass("active");
				$("#Direct_Exp").removeClass("active");
				$("#Direct_Income").removeClass("active");
				$("#Fixed_Assets").addClass("active");
				$("#Indirect_Exp").removeClass("active");
				$("#Indirect_Income").removeClass("active");
				$("#Profit_Loss").removeClass("active");
				$("#Purchase").removeClass("active");
				$("#Sales").removeClass("active");
				$("#Stock").removeClass("active");
				//display selected division, hide others
				$("div.Bank").css("display", "none");
				$("div.Bank_Loan").css("display", "none");
				$("div.Bank_OCC").css("display", "none");
				$("div.Capital").css("display", "none");
				$("div.Cash").css("display", "none");
				$("div.Current_Assets").css("display", "none");
				$("div.Current_Lia").css("display", "none");
				$("div.Direct_Exp").css("display", "none");
				$("div.Direct_Income").css("display", "none");
				$("div.Fixed_Assets").fadeIn();
				$("div.Indirect_Exp").css("display", "none");
				$("div.Indirect_Income").css("display", "none");
				$("div.Profit_Loss").css("display", "none");
				$("div.Purchase").css("display", "none");
				$("div.Sales").css("display", "none");
				$("div.Stock").css("display", "none");
			break;
			case "Indirect_Exp":
				//change status & style menu
				$("#Bank").removeClass("active");
				$("#Bank_Loan").removeClass("active");
				$("#Bank_OCC").removeClass("active");
				$("#Capital").removeClass("active");
				$("#Cash").removeClass("active");
				$("#Current_Assets").removeClass("active");
				$("#Current_Lia").removeClass("active");
				$("#Direct_Exp").removeClass("active");
				$("#Direct_Income").removeClass("active");
				$("#Fixed_Assets").removeClass("active");
				$("#Indirect_Exp").addClass("active");
				$("#Indirect_Income").removeClass("active");
				$("#Profit_Loss").removeClass("active");
				$("#Purchase").removeClass("active");
				$("#Sales").removeClass("active");
				$("#Stock").removeClass("active");
				//display selected division, hide others
				$("div.Bank").css("display", "none");
				$("div.Bank_Loan").css("display", "none");
				$("div.Bank_OCC").css("display", "none");
				$("div.Capital").css("display", "none");
				$("div.Cash").css("display", "none");
				$("div.Current_Assets").css("display", "none");
				$("div.Current_Lia").css("display", "none");
				$("div.Direct_Exp").css("display", "none");
				$("div.Direct_Income").css("display", "none");
				$("div.Fixed_Assets").css("display", "none");
				$("div.Indirect_Exp").fadeIn();
				$("div.Indirect_Income").css("display", "none");
				$("div.Profit_Loss").css("display", "none");
				$("div.Purchase").css("display", "none");
				$("div.Sales").css("display", "none");
				$("div.Stock").css("display", "none");
			break;
			case "Indirect_Income":
				//change status & style menu
				$("#Bank").removeClass("active");
				$("#Bank_Loan").removeClass("active");
				$("#Bank_OCC").removeClass("active");
				$("#Capital").removeClass("active");
				$("#Cash").removeClass("active");
				$("#Current_Assets").removeClass("active");
				$("#Current_Lia").removeClass("active");
				$("#Direct_Exp").removeClass("active");
				$("#Direct_Income").removeClass("active");
				$("#Fixed_Assets").removeClass("active");
				$("#Indirect_Exp").removeClass("active");
				$("#Indirect_Income").addClass("active");
				$("#Profit_Loss").removeClass("active");
				$("#Purchase").removeClass("active");
				$("#Sales").removeClass("active");
				$("#Stock").removeClass("active");
				//display selected division, hide others
				$("div.Bank").css("display", "none");
				$("div.Bank_Loan").css("display", "none");
				$("div.Bank_OCC").css("display", "none");
				$("div.Capital").css("display", "none");
				$("div.Cash").css("display", "none");
				$("div.Current_Assets").css("display", "none");
				$("div.Current_Lia").css("display", "none");
				$("div.Direct_Exp").css("display", "none");
				$("div.Direct_Income").css("display", "none");
				$("div.Fixed_Assets").css("display", "none");
				$("div.Indirect_Exp").css("display", "none");
				$("div.Indirect_Income").fadeIn();
				$("div.Profit_Loss").css("display", "none");
				$("div.Purchase").css("display", "none");
				$("div.Sales").css("display", "none");
				$("div.Stock").css("display", "none");
			break;
			case "Profit_Loss":
				//change status & style menu
				$("#Bank").removeClass("active");
				$("#Bank_Loan").removeClass("active");
				$("#Bank_OCC").removeClass("active");
				$("#Capital").removeClass("active");
				$("#Cash").removeClass("active");
				$("#Current_Assets").removeClass("active");
				$("#Current_Lia").removeClass("active");
				$("#Direct_Exp").removeClass("active");
				$("#Direct_Income").removeClass("active");
				$("#Fixed_Assets").removeClass("active");
				$("#Indirect_Exp").removeClass("active");
				$("#Indirect_Income").removeClass("active");
				$("#Profit_Loss").addClass("active");
				$("#Purchase").removeClass("active");
				$("#Sales").removeClass("active");
				$("#Stock").removeClass("active");
				//display selected division, hide others
				$("div.Bank").css("display", "none");
				$("div.Bank_Loan").css("display", "none");
				$("div.Bank_OCC").css("display", "none");
				$("div.Capital").css("display", "none");
				$("div.Cash").css("display", "none");
				$("div.Current_Assets").css("display", "none");
				$("div.Current_Lia").css("display", "none");
				$("div.Direct_Exp").css("display", "none");
				$("div.Direct_Income").css("display", "none");
				$("div.Fixed_Assets").css("display", "none");
				$("div.Indirect_Exp").css("display", "none");
				$("div.Indirect_Income").css("display", "none");
				$("div.Profit_Loss").fadeIn();
				$("div.Purchase").css("display", "none");
				$("div.Sales").css("display", "none");
				$("div.Stock").css("display", "none");
			break;
			case "Purchase":
				//change status & style menu
				$("#Bank").removeClass("active");
				$("#Bank_Loan").removeClass("active");
				$("#Bank_OCC").removeClass("active");
				$("#Capital").removeClass("active");
				$("#Cash").removeClass("active");
				$("#Current_Assets").removeClass("active");
				$("#Current_Lia").removeClass("active");
				$("#Direct_Exp").removeClass("active");
				$("#Direct_Income").removeClass("active");
				$("#Fixed_Assets").removeClass("active");
				$("#Indirect_Exp").removeClass("active");
				$("#Indirect_Income").removeClass("active");
				$("#Profit_Loss").removeClass("active");
				$("#Purchase").addClass("active");
				$("#Sales").removeClass("active");
				$("#Stock").removeClass("active");
				//display selected division, hide others
				$("div.Bank").css("display", "none");
				$("div.Bank_Loan").css("display", "none");
				$("div.Bank_OCC").css("display", "none");
				$("div.Capital").css("display", "none");
				$("div.Cash").css("display", "none");
				$("div.Current_Assets").css("display", "none");
				$("div.Current_Lia").css("display", "none");
				$("div.Direct_Exp").css("display", "none");
				$("div.Direct_Income").css("display", "none");
				$("div.Fixed_Assets").css("display", "none");
				$("div.Indirect_Exp").css("display", "none");
				$("div.Indirect_Income").css("display", "none");
				$("div.Profit_Loss").css("display", "none");
				$("div.Purchase").fadeIn();
				$("div.Sales").css("display", "none");
				$("div.Stock").css("display", "none");
			break;
			case "Sales":
				//change status & style menu
				$("#Bank").removeClass("active");
				$("#Bank_Loan").removeClass("active");
				$("#Bank_OCC").removeClass("active");
				$("#Capital").removeClass("active");
				$("#Cash").removeClass("active");
				$("#Current_Assets").removeClass("active");
				$("#Current_Lia").removeClass("active");
				$("#Direct_Exp").removeClass("active");
				$("#Direct_Income").removeClass("active");
				$("#Fixed_Assets").removeClass("active");
				$("#Indirect_Exp").removeClass("active");
				$("#Indirect_Income").removeClass("active");
				$("#Profit_Loss").removeClass("active");
				$("#Purchase").removeClass("active");
				$("#Sales").addClass("active");
				$("#Stock").removeClass("active");
				//display selected division, hide others
				$("div.Bank").css("display", "none");
				$("div.Bank_Loan").css("display", "none");
				$("div.Bank_OCC").css("display", "none");
				$("div.Capital").css("display", "none");
				$("div.Cash").css("display", "none");
				$("div.Current_Assets").css("display", "none");
				$("div.Current_Lia").css("display", "none");
				$("div.Direct_Exp").css("display", "none");
				$("div.Direct_Income").css("display", "none");
				$("div.Fixed_Assets").css("display", "none");
				$("div.Indirect_Exp").css("display", "none");
				$("div.Indirect_Income").css("display", "none");
				$("div.Profit_Loss").css("display", "none");
				$("div.Purchase").css("display", "none");
				$("div.Sales").fadeIn();
				$("div.Stock").css("display", "none");
			break;
				case "Stock":
				//change status & style menu
				$("#Bank").removeClass("active");
				$("#Bank_Loan").removeClass("active");
				$("#Bank_OCC").removeClass("active");
				$("#Capital").removeClass("active");
				$("#Cash").removeClass("active");
				$("#Current_Assets").removeClass("active");
				$("#Current_Lia").removeClass("active");
				$("#Direct_Exp").removeClass("active");
				$("#Direct_Income").removeClass("active");
				$("#Fixed_Assets").removeClass("active");
				$("#Indirect_Exp").removeClass("active");
				$("#Indirect_Income").removeClass("active");
				$("#Profit_Loss").removeClass("active");
				$("#Purchase").removeClass("active");
				$("#Sales").removeClass("active");
				$("#Stock").addClass("active");
				//display selected division, hide others
				$("div.Bank").css("display", "none");
				$("div.Bank_Loan").css("display", "none");
				$("div.Bank_OCC").css("display", "none");
				$("div.Capital").css("display", "none");
				$("div.Cash").css("display", "none");
				$("div.Current_Assets").css("display", "none");
				$("div.Current_Lia").css("display", "none");
				$("div.Direct_Exp").css("display", "none");
				$("div.Direct_Income").css("display", "none");
				$("div.Fixed_Assets").css("display", "none");
				$("div.Indirect_Exp").css("display", "none");
				$("div.Indirect_Income").css("display", "none");
				$("div.Profit_Loss").css("display", "none");
				$("div.Purchase").css("display", "none");
				$("div.Sales").css("display", "none");
				$("div.Stock").fadeIn();
			break;
			
		}
		
		//alert(e.target.id);
		return false;
	});
});