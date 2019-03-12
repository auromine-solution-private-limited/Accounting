package com.jewellery.web;


import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.jewellery.dao.AccountsDao;
import com.jewellery.dao.CardIssueDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.dao.SSReceiptDao;
import com.jewellery.dao.SavingSchemeDao;
import com.jewellery.dao.StartSchemeDao;

import com.jewellery.entity.CardIssue;

import com.jewellery.entity.SavingScheme;
import com.jewellery.entity.StartScheme;
import com.jewellery.validator.Cardissuevalidator;

@Controller
public class FormCardIssueController {
	@Autowired
	private SavingSchemeDao savingschemeDao;
	@Autowired
	private LedgerDao ledgerDao;
	@Autowired
	private CardIssueDao cardissueDao;
	@Autowired
	private StartSchemeDao statartchemeDao;
	@Autowired
	private Cardissuevalidator cardissuevalidator;
	@Autowired SSReceiptDao ssreceiptDao;
	@Autowired
	AccountsDao accountsDao;
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		CustomDateEditor dateEditor = new CustomDateEditor(
				new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class, null, dateEditor);
	}

	// *********************New card issue form*************//
	@RequestMapping(value = "/formcardissue.htm", method = RequestMethod.GET)
	public String listcardissue(
			@ModelAttribute("cardissue") CardIssue cardissue,
			Map<String, Object> map) {
		map.put("customername", ledgerDao.listCustomerName());
		map.put("schemename", statartchemeDao.getSchmename());
		return "formcardissue";
	}

	// ****************saving the new card issue****************//
	@RequestMapping(value = "/formcardissue.htm", method = RequestMethod.POST, params = "insert")
	public ModelAndView saveCardissue(
			@ModelAttribute("cardissue") CardIssue cardissue,
			BindingResult result, SessionStatus status, Model model) {
		model.addAttribute("customername", ledgerDao.listCustomerName());
		model.addAttribute("schemename", statartchemeDao.getSchmename());

		cardissuevalidator.validate(cardissue, result);

		if (result.hasErrors()) {
			ModelMap map = new ModelMap();
			map.put("command", cardissue);
			map.addAttribute("errorType", "insertError");
			return new ModelAndView( "formcardissue",map);
		}
		String cardissue_No = getCardIssuetNumber(cardissue);
		String scheme_name=cardissue.getSchemeName();
		List<StartScheme> schme=statartchemeDao.searchStartScheme(scheme_name);
		cardissue.setStartscheme(schme.get(0));
		cardissue.setCardNo(cardissue_No);
		cardissue.setStatus("Active");
		cardissueDao.insertCardIssue(cardissue);
		return new ModelAndView(new RedirectView("cardissueList.htm"));
	}

	// ****************saving the new card issue****************//
	@RequestMapping(value = "/formcardissue.htm", method = RequestMethod.POST, params = "update")
	public ModelAndView updateCardissue(@ModelAttribute("cardissue") CardIssue cardissue, BindingResult result, SessionStatus statusModel, Model model) {
		model.addAttribute("customername", ledgerDao.listCustomerName());
		model.addAttribute("schemename", statartchemeDao.getSchmename());

		cardissuevalidator.validate(cardissue, result);

		if (result.hasErrors()) {
			ModelMap map = new ModelMap();
			map.put("command", cardissue);
			map.addAttribute("errorType", "updateError");
			return new ModelAndView("formcardissue",map);
		}
		CardIssue OldCardIssue = cardissueDao.getissuedCardById(cardissue.getCardId());
		String scheme_name=cardissue.getSchemeName();
		List<StartScheme> schme=statartchemeDao.searchStartScheme(scheme_name);
		cardissue.setStartscheme(schme.get(0));
		
		/** Card No changes if Scheme Name Changes in Update Mode **/
		if(!OldCardIssue.getSchemeType().equalsIgnoreCase(cardissue.getSchemeType())){
			cardissue.setCardNo(getCardIssuetNumber(cardissue));
		}
		
		cardissueDao.updateCardIssue(cardissue);
		return  new ModelAndView(new RedirectView("cardissueList.htm"));
	}

	// ***********CardIssueList*************//
	@RequestMapping(value = "/cardissueList.htm", method = RequestMethod.GET)
	public String listStartScheme(Map<String, Object> map) {
		((ModelMap) map).addAttribute("cardissueList",
				cardissueDao.cardissueList());
		return "cardissueList";
	}

	// ************** For Cancel *****************//
	@RequestMapping(value = "/formcardissue.htm", params = "cancel")
	public String cancelForm() {
		return "redirect:cardissueList.htm";
	}

	// ************** Card issue view mode*****************//
	@RequestMapping(value = "/editcardissue.htm", method = RequestMethod.GET)
	public String goToEdit(@RequestParam(value = "cardId", required = true) Integer cardId,@ModelAttribute("cardissue") CardIssue cardissue, ModelMap model) {
		CardIssue card=cardissueDao.getissuedCardById(cardId);
		String cardNo=card.getCardNo();
		String receipt_payment_cardNo=ssreceiptDao.getcardNo(cardNo);
		model.addAttribute("payment_receipt_details",receipt_payment_cardNo);
		model.addAttribute("cardissue", cardissueDao.getissuedCardById(cardId));
		model.put("customername", ledgerDao.listCustomerName());
		model.put("schemename", statartchemeDao.getSchmename());

		return "formcardissue";
	}

	// *****************Ajax script for Getting Scheme details from start scheme
	// table*****************//
	@RequestMapping(value = "/SchemeDetailCardIssue.htm", method = RequestMethod.GET)
	public @ResponseBody
	String getAjaxSchemeDetail(
			@RequestParam(value = "schemeName", required = true) String schemeName) {

		String result = "";
		try {
			List<SavingScheme> schemeDetailsList = savingschemeDao
					.searchSavingScheme(schemeName);
			if (!schemeDetailsList.isEmpty()) {
				ArrayList<String> cardNoList = new ArrayList<String>();

				cardNoList.add(0, schemeDetailsList.get(0).getSchemeType());
				cardNoList.add(1, schemeDetailsList.get(0).getSchemeInAmount()
						.toString());
				cardNoList.add(2, schemeDetailsList.get(0).getSchemeInGrams()
						.toString());
				
				result = cardNoList.toString();
			}
		} catch (Exception e) {
		}
		return result;
	}

	public String getCardIssuetNumber(CardIssue ssReceipt) {

		String finalCode = null;
		String cardissueNoList = "";
		String cardissueCodeNo = null;
		BigInteger found = null; 

		if (ssReceipt.getSchemeType().equalsIgnoreCase("Amount")) {
			found = cardissueDao.amountinCash();
			cardissueNoList = "AC"+found;
			cardissueCodeNo = "AC0";
		} else {
			found = cardissueDao.amountinGrams();
			cardissueNoList = "AG"+found;
			cardissueCodeNo = "AG0";
		}

		if (found != null) {
			cardissueCodeNo = cardissueNoList;
		}

		String splitNo = cardissueCodeNo.substring(2);
		int num = Integer.parseInt(splitNo);
		num++;
		String number = Integer.toString(num);
		finalCode = cardissueCodeNo.substring(0, 2) + number;
		return finalCode;
	}

	// Ajax  code for start date in cancel card report.
			@RequestMapping(value = "/getstartdate.htm", method = RequestMethod.GET)
			public @ResponseBody String getstartDate(@RequestParam(value="schemeName",required=false) String schemeName) throws ParseException{
				
			String  joiningdate = statartchemeDao.getstartDate(schemeName);
			System.out.println(joiningdate);
				return joiningdate.toString();
			}
}
