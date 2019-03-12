package com.jewellery.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import com.jewellery.dao.JournalDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.entity.Journal;
import com.jewellery.entity.Ledger;
import com.jewellery.validator.FormJournalValidator;
import com.jewellery.util.JournalCode;

@Controller
public class FormJournalController extends JournalCode {

	@Autowired(required = false)
	private JournalDao journalDao;
	@Autowired(required = false)
	private LedgerDao ledgerDao;
	@Autowired(required = false)
	private FormJournalValidator formjournalValidator;

	List<String> ledgerGroupCode;
	BigDecimal clBal;
	String clType;

	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		CustomDateEditor dateEditor = new CustomDateEditor(
				new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class, null, dateEditor);
	}

	// this is for mapping journal.jsp and listing all the journal
	@RequestMapping(value = "/journal", method = RequestMethod.GET)
	public ModelAndView showForm(@ModelAttribute("show") Journal journal) {
		ModelMap model = new ModelMap();
		model.put("journalList", journalDao.listJournal());
		return new ModelAndView("journal", model);// this will return all the
													// list of journal
	}

	// this is mapping is for formjournal
	@RequestMapping(method = RequestMethod.GET, value = "/formjournal")
	public ModelAndView showFormjournal(
			@ModelAttribute("Journal") Journal journal) {
		ModelMap model = new ModelMap();
		// model.put("journalNO", getJournalNumber(journal));
		model.put("RecieptDebit", ledgerDao.listJournalRecieptDebit());
		model.put("RecieptCredit", ledgerDao.listJournalRecieptCredit());
		model.put("Journaltype", ledgerDao.listjournal());
		// model.put("bankaccount",ledgerDao.listBankAccount());
		// model.put("cashaccount",ledgerDao.listCashAccount());
		// model.put("customername",ledgerDao.listallCustomerName());
		// model.put("suppliername",ledgerDao.listSupplierName());

		return new ModelAndView("formjournal", model);
	}

	// cancel button to redirect to Journal list page

	// Canceling request mapping
	@RequestMapping(value = "/formjournal.htm", method = RequestMethod.POST, params = "cancel")
	public String cancelForm(@ModelAttribute("Journal") Journal journal) {
		return "redirect:journal.htm";
	}

	// this is for inserting the New journal
	@RequestMapping(value = "/formjournal", method = RequestMethod.POST, params = "insert")
	public ModelAndView insertForm(@ModelAttribute("Journal") Journal journal, 
			BindingResult result, ModelMap model,SessionStatus status) {

		formjournalValidator.validate(journal, result);

		String debitAccount = journal.getDebitAccountName();
		String creditAccount = journal.getCreditAccountName();
		String journalType = journal.getJournalType();
		BigDecimal closingAmount = journal.getDebitAmount();
		String closingType = "Debit";
		String closingTyp = "Credit";
		BigDecimal clBal = new BigDecimal("0.0");
		String clType = null;

		if (result.hasErrors()) {
			ModelMap map = new ModelMap();
			map.put("RecieptDebit", ledgerDao.listJournalRecieptDebit());
			map.put("RecieptCredit", ledgerDao.listJournalRecieptCredit());
			map.put("Journaltype", ledgerDao.listjournal());
			map.addAttribute("errorType", "insertError");
			map.put("command", journal);
			return new ModelAndView("formjournal", map);
		}

		updateDebitCreditCode(journal);//Set Account Group code
		journal.setCreditCode(ledgerGroupCode.get(0).toString());
		journal.setJournalNO(getJournalNumber(journal));
		journalDao.insertJournal((Journal) journal);
		status.setComplete();

		/*** update ledger accounts ******/

		if (journalType.equals("Receipt")) {

			List<Ledger> customerList = ledgerDao.searchLedger(creditAccount);
			clBal = customerList.get(0).getClosingTotalBalance();
			clType = customerList.get(0).getClosingTotalType();

			if (clType.equals("Debit")) {
				if (clBal.compareTo(closingAmount) == 1) {
					ledgerDao.updateCreditPartyBalance(closingAmount,
							closingType, creditAccount);
				} else {
					ledgerDao.updateCrPartyBalance(closingAmount, closingTyp,
							creditAccount);
				}
			} else if (clType.equals("Credit")) {
				ledgerDao.updatePartyBalance(closingAmount, closingTyp,
						creditAccount);
			}

			// update Bank or cash balance

			List<Ledger> paymentList = ledgerDao.searchLedger(debitAccount);
			clBal = paymentList.get(0).getClosingTotalBalance();
			clType = paymentList.get(0).getClosingTotalType();

			if (clType.equals("Debit")) {
				ledgerDao.updatePartyBalance(closingAmount, closingType,
						debitAccount);
			} else if (clType.equals("Credit")) {
				if (clBal.compareTo(closingAmount) == 1) {
					ledgerDao.updateCreditPartyBalance(closingAmount,
							closingTyp, debitAccount);
				} else {
					ledgerDao.updateCrPartyBalance(closingAmount, closingType,
							debitAccount);
				}
			}
		}

		if (journalType.equals("Payment")) {

			// update Party Balance
			List<Ledger> customerList = ledgerDao.searchLedger(debitAccount);
			clBal = customerList.get(0).getClosingTotalBalance();
			clType = customerList.get(0).getClosingTotalType();

			if (clType.equals("Credit")) {
				if (clBal.compareTo(closingAmount) == 1) {
					ledgerDao.updateCreditPartyBalance(closingAmount,
							closingTyp, debitAccount);
				} else {
					ledgerDao.updateCrPartyBalance(closingAmount, closingType,
							debitAccount);
				}
			} else if (clType.equals("Debit")) {
				ledgerDao.updatePartyBalance(closingAmount, closingType,
						debitAccount);
			}

			// update Bank or cash balance

			List<Ledger> paymentList = ledgerDao.searchLedger(creditAccount);
			clBal = paymentList.get(0).getClosingTotalBalance();
			clType = paymentList.get(0).getClosingTotalType();

			if (clType.equals("Credit")) {
				ledgerDao.updatePartyBalance(closingAmount, closingTyp,
						creditAccount);
			} else if (clType.equals("Debit")) {
				if (clBal.compareTo(closingAmount) == 1) {
					ledgerDao.updateCreditPartyBalance(closingAmount,
							closingType, creditAccount);
				} else {
					ledgerDao.updateCrPartyBalance(closingAmount, closingTyp,
							creditAccount);
				}
			}
		}

		// Ledger Updation when type is Journal
		if (journalType.equals("Journal")) {

			// update Sales Discount/Purchase Discount

			List<Ledger> customerList = ledgerDao.searchLedger(debitAccount);
			clBal = customerList.get(0).getClosingTotalBalance();
			clType = customerList.get(0).getClosingTotalType();

			if (clType.equals("Credit")) {
				if (clBal.compareTo(closingAmount) == 1) {
					ledgerDao.updateCreditPartyBalance(closingAmount,
							closingTyp, debitAccount);
				} else {
					ledgerDao.updateCrPartyBalance(closingAmount, closingType,
							debitAccount);
				}
			} else if (clType.equals("Debit")) {
				ledgerDao.updatePartyBalance(closingAmount, closingType,
						debitAccount);
			}

			// update Party Balance

			List<Ledger> paymentList = ledgerDao.searchLedger(creditAccount);
			clBal = paymentList.get(0).getClosingTotalBalance();
			clType = paymentList.get(0).getClosingTotalType();

			if (clType.equals("Credit")) {
				ledgerDao.updatePartyBalance(closingAmount, closingTyp,
						creditAccount);
			} else if (clType.equals("Debit")) {
				if (clBal.compareTo(closingAmount) == 1) {
					ledgerDao.updateCreditPartyBalance(closingAmount,
							closingType, creditAccount);
				} else {
					ledgerDao.updateCrPartyBalance(closingAmount, closingTyp,
							creditAccount);
				}
			}
		}

		// Update Ledger if Journal Type is Contra
		if (journalType.equals("Contra")) {

			// update Bank or cash balance For Debit Account

			List<Ledger> contraList = ledgerDao.searchLedger(debitAccount);
			clBal = contraList.get(0).getClosingTotalBalance();
			clType = contraList.get(0).getClosingTotalType();

			if (clType.equals("Debit")) {
				ledgerDao.updatePartyBalance(closingAmount, closingType,
						debitAccount);
			} else if (clType.equals("Credit")) {
				if (clBal.compareTo(closingAmount) == 1) {
					ledgerDao.updateCreditPartyBalance(closingAmount,
							closingTyp, debitAccount);
				} else {
					ledgerDao.updateCrPartyBalance(closingAmount, closingType,
							debitAccount);
				}
			}

			// update Bank or cash balance For Credit Account

			List<Ledger> paymentList = ledgerDao.searchLedger(creditAccount);
			clBal = paymentList.get(0).getClosingTotalBalance();
			clType = paymentList.get(0).getClosingTotalType();

			if (clType.equals("Credit")) {
				ledgerDao.updatePartyBalance(closingAmount, closingTyp,
						creditAccount);
			} else if (clType.equals("Debit")) {
				if (clBal.compareTo(closingAmount) == 1) {
					ledgerDao.updateCreditPartyBalance(closingAmount,
							closingType, creditAccount);
				} else {
					ledgerDao.updateCrPartyBalance(closingAmount, closingTyp,
							creditAccount);
				}
			}
		}

		if(journal.getJournalType().equalsIgnoreCase("Receipt") || journal.getJournalType().equalsIgnoreCase("Payment")){
			return new ModelAndView("redirect:journalPreview.htm?journalNO="+journal.getJournalNO()+"&journalType="+journal.getJournalType(), model);
		}
		
		return new ModelAndView("redirect:journal.htm");
	}
	
	@RequestMapping(value = "/journalPreview.htm", method = RequestMethod.GET)
	public ModelAndView pdfInvoicePreview() {
		return new ModelAndView("journalPreview");
	}

	// redirect the form for update
	@RequestMapping(value = "/viewformjournal.htm", method = RequestMethod.GET)
	public String getUpdate(
			@RequestParam(value = "journalId", required = true) Integer journalId,
			@ModelAttribute("Journal") Journal journal, Model model) {
		model.addAttribute("Journal", journalDao.getJournal(journalId));
		model.addAttribute("RecieptDebit", ledgerDao.listJournalRecieptDebit());
		model.addAttribute("RecieptCredit",
				ledgerDao.listJournalRecieptCredit());
		model.addAttribute("Journaltype", ledgerDao.listjournal());
		return "formjournal";// this will return to the formjournal
	}
	
	//Update Journal Debit or Credit Ccode
	private void updateDebitCreditCode(Journal journal){
		// Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(journal
						.getDebitAccountName());
				journal.setDebitCode(ledgerGroupCode.get(0).toString());
				
		// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(journal
						.getCreditAccountName());
				journal.setCreditCode(ledgerGroupCode.get(0).toString());			
	}	

	// this method is for updating the form Journal
	@RequestMapping(value = "/formjournal", method = RequestMethod.POST, params = "update")
	public ModelAndView updateForm(@ModelAttribute("Journal") Journal journal,
			BindingResult result, Integer journalId) {

		// Journal id=journalDao.getJournal(journalId);

		formjournalValidator.validate(journal, result);

		Integer journalid = journal.getJournalId();
		Journal journalObj = journalDao.getJournal(journalid);
		BigDecimal convert = new BigDecimal("-1");
		BigDecimal zero = new BigDecimal("0");
		String finalcltype;

		if (result.hasErrors()) {
			ModelMap map = new ModelMap();
			map.addAttribute("RecieptDebit",
					ledgerDao.listJournalRecieptDebit());
			map.addAttribute("RecieptCredit",
					ledgerDao.listJournalRecieptCredit());
			map.addAttribute("Journaltype", ledgerDao.listjournal());
			map.addAttribute("errorType", "updateError");
			map.put("command", journal);
			return new ModelAndView("formjournal", map);
		}
		/*************** UPDATE MODE FOR SAME RECEIPT TYPE *************************/
		if (journalObj.getJournalType().equalsIgnoreCase(
				journal.getJournalType())) {

			if (journal.getJournalType().equals("Receipt")) {
				/**** (For changing the Receipt amount) deptor account ****/
				if (journalObj.getDebitAmount().compareTo(
						journal.getDebitAmount()) != 0) {
					if (journalObj.getDebitAccountName().equalsIgnoreCase(
							journal.getDebitAccountName())) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journal.getDebitAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal
								.subtract(journalObj.getDebitAmount());
						BigDecimal finalClBalanceParty = dropClBalanceParty
								.add(journal.getDebitAmount());

						if (finalClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							finalClBalanceParty = finalClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,
								finalcltype, journal.getDebitAccountName());
					}

				}
				/******* (For changing the Receipt amount) creditor account *****/
				if (journalObj.getCreditAmount().compareTo(
						journal.getCreditAmount()) != 0) {
					if (journalObj.getCreditAccountName().equalsIgnoreCase(
							journal.getCreditAccountName())) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journal.getCreditAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal.add(journalObj
								.getCreditAmount());
						BigDecimal finalClBalanceParty = dropClBalanceParty
								.subtract(journal.getCreditAmount());

						if (finalClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							finalClBalanceParty = finalClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,
								finalcltype, journal.getCreditAccountName());
					}
				}
				/******* UPDATE MODE FOR RECEIPT (For changing the ledger name): *******/
				if (!journalObj.getDebitAccountName().equalsIgnoreCase(
						journal.getDebitAccountName())) {
					if (journalObj.getDebitAmount().compareTo(
							journal.getDebitAmount()) == 0) {
						/********** Formula for Old Debit Account ledger: **********/
						List<Ledger> customerList = ledgerDao
								.searchLedger(journalObj.getDebitAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}
						BigDecimal dropClBalanceParty = clBal
								.subtract(journalObj.getDebitAmount());

						if (dropClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							dropClBalanceParty = dropClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
								finalcltype, journalObj.getDebitAccountName());
					}

					// Set Account group code as Debit code
					updateDebitCreditCode(journal);
				}

				/********** Formula for Old credit Account ledger: **********/
				if (!journalObj.getCreditAccountName().equalsIgnoreCase(
						journal.getCreditAccountName())) {
					if (journalObj.getCreditAmount().compareTo(
							journal.getCreditAmount()) == 0) {

						List<Ledger> customerList = ledgerDao
								.searchLedger(journalObj.getCreditAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal.add(journalObj
								.getCreditAmount());

						if (dropClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							dropClBalanceParty = dropClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
								finalcltype, journalObj.getCreditAccountName());
					}

					// Set Account group code as Credit code
					updateDebitCreditCode(journal);
				}

				/******* Formula for New Debit Account Ledger: *******/
				if (!journalObj.getDebitAccountName().equalsIgnoreCase(
						journal.getDebitAccountName())) {
					if (journalObj.getDebitAmount().compareTo(
							journal.getDebitAmount()) == 0) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journal.getDebitAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();
						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}
						BigDecimal dropClBalanceParty = clBal.add(journal
								.getDebitAmount());

						if (dropClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							dropClBalanceParty = dropClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
								finalcltype, journal.getDebitAccountName());

					}
					
					// Set Account group code as Debit code
					updateDebitCreditCode(journal);

				}
				/******* Formula for New Credit Account Ledger: *****/
				if (!journalObj.getCreditAccountName().equalsIgnoreCase(
						journal.getCreditAccountName())) {

					if (journalObj.getCreditAmount().compareTo(
							journal.getCreditAmount()) == 0) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journal.getCreditAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal.subtract(journal
								.getCreditAmount());

						if (dropClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							dropClBalanceParty = dropClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
								finalcltype, journal.getCreditAccountName());
					}
					
					// Set Account group code as Debit code
					updateDebitCreditCode(journal);

				}
				/**********
				 * UPDATE MODE FOR RECEIPT (For changing the ledger name and
				 * Receipt amount):
				 ************/
				if (!journalObj.getDebitAccountName().equalsIgnoreCase(
						journal.getDebitAccountName())
						&& journalObj.getDebitAmount().compareTo(
								journal.getDebitAmount()) != 0) {
					/******* Formula for Old Debit Account ledger: *******/
					List<Ledger> customerList = ledgerDao
							.searchLedger(journalObj.getDebitAccountName());
					clBal = customerList.get(0).getClosingTotalBalance();
					clType = customerList.get(0).getClosingTotalType();

					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}
					BigDecimal dropClBalanceParty = clBal.subtract(journalObj
							.getDebitAmount());
					if (dropClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						dropClBalanceParty = dropClBalanceParty
								.multiply(convert);
					} else {
						finalcltype = "Debit";
					}
					ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
							finalcltype, journalObj.getDebitAccountName());
					
					// Set Account group code
					updateDebitCreditCode(journal);

				}
				/****** Formula for Old credit Account ledger: **********/
				if (!journalObj.getCreditAccountName().equalsIgnoreCase(
						journal.getCreditAccountName())
						&& journalObj.getDebitAmount().compareTo(
								journal.getDebitAmount()) != 0) {

					List<Ledger> customerList = ledgerDao
							.searchLedger(journalObj.getCreditAccountName());
					clBal = customerList.get(0).getClosingTotalBalance();
					clType = customerList.get(0).getClosingTotalType();

					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}

					BigDecimal dropClBalanceParty = clBal.add(journalObj
							.getCreditAmount());

					if (dropClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						dropClBalanceParty = dropClBalanceParty
								.multiply(convert);
					} else {
						finalcltype = "Debit";
					}
					ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
							finalcltype, journalObj.getCreditAccountName());

					// Set Account group code
					updateDebitCreditCode(journal);

				}
				/********* Formula for New Debit Account ledger: ********/
				if (!journalObj.getDebitAccountName().equalsIgnoreCase(
						journal.getDebitAccountName())
						&& journalObj.getDebitAmount().compareTo(
								journal.getDebitAmount()) != 0) {

					List<Ledger> customerList = ledgerDao.searchLedger(journal
							.getDebitAccountName());
					clBal = customerList.get(0).getClosingTotalBalance();
					clType = customerList.get(0).getClosingTotalType();

					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}

					BigDecimal dropClBalanceParty = clBal.add(journal
							.getDebitAmount());

					if (dropClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						dropClBalanceParty = dropClBalanceParty
								.multiply(convert);
					} else {
						finalcltype = "Debit";
					}
					ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
							finalcltype, journal.getDebitAccountName());
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}
				/********* Formula for New Credit Account ledger: ********/
				if (!journalObj.getCreditAccountName().equalsIgnoreCase(
						journal.getCreditAccountName())
						&& journalObj.getDebitAmount().compareTo(
								journal.getDebitAmount()) != 0) {

					List<Ledger> customerList = ledgerDao.searchLedger(journal
							.getCreditAccountName());
					clBal = customerList.get(0).getClosingTotalBalance();
					clType = customerList.get(0).getClosingTotalType();

					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}

					BigDecimal dropClBalanceParty = clBal.subtract(journal
							.getCreditAmount());

					if (dropClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						dropClBalanceParty = dropClBalanceParty
								.multiply(convert);
					} else {
						finalcltype = "Debit";
					}
					ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
							finalcltype, journal.getCreditAccountName());
				}
				
				// Set Account group code
				updateDebitCreditCode(journal);

			}

			/******** UPDATE MODE FOR PAYMENT (For changing the Payment amount): ********/
			if (journal.getJournalType().equals("Payment")) {
				/***** Formula for Debit Account ledger: ***********/
				if (journalObj.getDebitAmount().compareTo(
						journal.getDebitAmount()) != 0) {
					if (journalObj.getDebitAccountName().equalsIgnoreCase(
							journal.getDebitAccountName())) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journalObj.getDebitAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal
								.subtract(journalObj.getDebitAmount());
						BigDecimal finalClBalanceParty = dropClBalanceParty
								.add(journal.getDebitAmount());

						if (finalClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							finalClBalanceParty = finalClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,
								finalcltype, journalObj.getDebitAccountName());
					}

				}

				/*** Formula for Credit Account Ledger: ****/
				if (journalObj.getCreditAmount().compareTo(
						journal.getCreditAmount()) != 0) {
					if (journalObj.getCreditAccountName().equalsIgnoreCase(
							journal.getCreditAccountName())) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journalObj.getCreditAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal.add(journalObj
								.getCreditAmount());
						BigDecimal finalClBalanceParty = dropClBalanceParty
								.subtract(journal.getCreditAmount());

						if (finalClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							finalClBalanceParty = finalClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,
								finalcltype, journalObj.getCreditAccountName());
					}
				}

				/****** UPDATE MODE FOR PAYMENT (For changing the ledger name): ******/
				if (!journalObj.getDebitAccountName().equalsIgnoreCase(
						journal.getDebitAccountName())) {
					if (journalObj.getDebitAmount().compareTo(
							journal.getDebitAmount()) == 0) {
						/********* Formula for Old Debit Account ledger: *********/
						List<Ledger> customerList = ledgerDao
								.searchLedger(journalObj.getDebitAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal
								.subtract(journalObj.getDebitAmount());

						if (dropClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							dropClBalanceParty = dropClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
								finalcltype, journalObj.getDebitAccountName());
					}
					
					// Set Account group code
					updateDebitCreditCode(journal);

				}
				/****** Formula for Old Credit Account ledger: *****/
				if (!journalObj.getCreditAccountName().equalsIgnoreCase(
						journal.getCreditAccountName())) {
					if (journalObj.getCreditAmount().compareTo(
							journal.getCreditAmount()) == 0) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journalObj.getCreditAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal.add(journalObj
								.getCreditAmount());

						if (dropClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							dropClBalanceParty = dropClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
								finalcltype, journalObj.getCreditAccountName());
					}
					
					// Set Account group code
					updateDebitCreditCode(journal);

				}
				/******* Formula for New Debit Account ledger: *******/
				if (!journalObj.getDebitAccountName().equalsIgnoreCase(
						journal.getDebitAccountName())) {
					if (journalObj.getDebitAmount().compareTo(
							journal.getDebitAmount()) == 0) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journal.getDebitAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal.add(journal
								.getDebitAmount());

						if (dropClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							dropClBalanceParty = dropClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
								finalcltype, journal.getDebitAccountName());
					}
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}
				/****** Formula for New credit Account ledger: *****/
				if (!journalObj.getCreditAccountName().equalsIgnoreCase(
						journal.getCreditAccountName())) {
					if (journalObj.getCreditAmount().compareTo(
							journal.getCreditAmount()) == 0) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journal.getCreditAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal.subtract(journal
								.getCreditAmount());

						if (dropClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							dropClBalanceParty = dropClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
								finalcltype, journal.getCreditAccountName());
					}
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}
				/*****
				 * UPDATE MODE FOR PAYMENT (For changing the ledger name and
				 * Payment amount):
				 ******/
				if (!journalObj.getDebitAccountName().equalsIgnoreCase(
						journal.getDebitAccountName())
						&& journalObj.getDebitAmount().compareTo(
								journal.getDebitAmount()) != 0) {
					/******* Formula for Old Debit Account ledger: ******/
					List<Ledger> customerList = ledgerDao
							.searchLedger(journalObj.getDebitAccountName());
					clBal = customerList.get(0).getClosingTotalBalance();
					clType = customerList.get(0).getClosingTotalType();

					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}

					BigDecimal dropClBalanceParty = clBal.subtract(journalObj
							.getDebitAmount());

					if (dropClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						dropClBalanceParty = dropClBalanceParty
								.multiply(convert);
					} else {
						finalcltype = "Debit";
					}

					ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
							finalcltype, journalObj.getDebitAccountName());
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}
				if (!journalObj.getCreditAccountName().equalsIgnoreCase(
						journal.getCreditAccountName())
						&& journalObj.getDebitAmount().compareTo(
								journal.getDebitAmount()) != 0) {
					/***** Formula for Old Credit Account ledger: *****/
					List<Ledger> customerList = ledgerDao
							.searchLedger(journalObj.getCreditAccountName());
					clBal = customerList.get(0).getClosingTotalBalance();
					clType = customerList.get(0).getClosingTotalType();

					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}

					BigDecimal dropClBalanceParty = clBal.add(journalObj
							.getCreditAmount());

					if (dropClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						dropClBalanceParty = dropClBalanceParty
								.multiply(convert);
					} else {
						finalcltype = "Debit";
					}

					ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
							finalcltype, journalObj.getCreditAccountName());
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}
				/********* Formula for New Debit Account ledger: **********/
				if (!journalObj.getDebitAccountName().equalsIgnoreCase(
						journal.getDebitAccountName())
						&& journalObj.getDebitAmount().compareTo(
								journal.getDebitAmount()) != 0) {

					List<Ledger> customerList = ledgerDao.searchLedger(journal
							.getDebitAccountName());
					clBal = customerList.get(0).getClosingTotalBalance();
					clType = customerList.get(0).getClosingTotalType();

					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}

					BigDecimal dropClBalanceParty = clBal.add(journal
							.getDebitAmount());

					if (dropClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						dropClBalanceParty = dropClBalanceParty
								.multiply(convert);
					} else {
						finalcltype = "Debit";
					}

					ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
							finalcltype, journal.getDebitAccountName());
					
					// Set Account group code
					updateDebitCreditCode(journal);

				}
				/****** Formula for New credit Account ledger: *******/
				if (!journalObj.getCreditAccountName().equalsIgnoreCase(
						journal.getCreditAccountName())
						&& journalObj.getDebitAmount().compareTo(
								journal.getDebitAmount()) != 0) {

					List<Ledger> customerList = ledgerDao.searchLedger(journal
							.getCreditAccountName());
					clBal = customerList.get(0).getClosingTotalBalance();
					clType = customerList.get(0).getClosingTotalType();

					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}

					BigDecimal dropClBalanceParty = clBal.subtract(journal
							.getCreditAmount());

					if (dropClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						dropClBalanceParty = dropClBalanceParty
								.multiply(convert);
					} else {
						finalcltype = "Debit";
					}

					ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
							finalcltype, journal.getCreditAccountName());
				}
				
				// Set Account group code
				updateDebitCreditCode(journal);

			}

			/******** UPDATE MODE FOR JOURNAL (For changing the Journal amount): ********/
			if (journal.getJournalType().equals("Journal")) {
				/****** Formula for Debit Account ledger: *****/
				if (journalObj.getDebitAmount().compareTo(
						journal.getDebitAmount()) != 0) {
					if (journalObj.getDebitAccountName().equalsIgnoreCase(
							journal.getDebitAccountName())) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journalObj.getDebitAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal
								.subtract(journalObj.getDebitAmount());
						BigDecimal finalClBalanceParty = dropClBalanceParty
								.add(journal.getDebitAmount());

						if (finalClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							finalClBalanceParty = finalClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,
								finalcltype, journalObj.getDebitAccountName());
					}
				}
				/******* Formula for Credit Account Ledger: *****/
				if (journalObj.getCreditAmount().compareTo(
						journal.getCreditAmount()) != 0) {
					if (journalObj.getCreditAccountName().equalsIgnoreCase(
							journal.getCreditAccountName())) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journalObj.getCreditAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal.add(journalObj
								.getCreditAmount());
						BigDecimal finalClBalanceParty = dropClBalanceParty
								.subtract(journal.getCreditAmount());

						if (finalClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							finalClBalanceParty = finalClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,
								finalcltype, journalObj.getCreditAccountName());
					}
				}
				/******* UPDATE MODE FOR JOURNAL (For changing the ledger name): *****/
				if (!journalObj.getDebitAccountName().equalsIgnoreCase(
						journal.getDebitAccountName())) {
					if (journalObj.getDebitAmount().compareTo(
							journal.getDebitAmount()) == 0) {
						/******** Formula for Old Debit Account ledger: ********/
						List<Ledger> customerList = ledgerDao
								.searchLedger(journalObj.getDebitAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal
								.subtract(journalObj.getDebitAmount());

						if (dropClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							dropClBalanceParty = dropClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
								finalcltype, journalObj.getDebitAccountName());
					}
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}
				/****** Formula for Old Credit Account ledger: *******/
				if (!journalObj.getCreditAccountName().equalsIgnoreCase(
						journal.getCreditAccountName())) {
					if (journalObj.getCreditAmount().compareTo(
							journal.getCreditAmount()) == 0) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journalObj.getCreditAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal.add(journalObj
								.getCreditAmount());

						if (dropClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							dropClBalanceParty = dropClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
								finalcltype, journalObj.getCreditAccountName());
					}
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}
				/******* Formula for New Debit Account ledger: ***************/
				if (!journalObj.getDebitAccountName().equalsIgnoreCase(
						journal.getDebitAccountName())) {
					if (journalObj.getDebitAmount().compareTo(
							journal.getDebitAmount()) == 0) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journal.getDebitAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal.add(journal
								.getDebitAmount());

						if (dropClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							dropClBalanceParty = dropClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
								finalcltype, journal.getDebitAccountName());
					}
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}
				/************* Formula for New credit Account ledger: ***************/
				if (!journalObj.getCreditAccountName().equalsIgnoreCase(
						journal.getCreditAccountName())) {
					if (journalObj.getCreditAmount().compareTo(
							journal.getCreditAmount()) == 0) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journal.getCreditAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal.subtract(journal
								.getCreditAmount());

						if (dropClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							dropClBalanceParty = dropClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
								finalcltype, journal.getCreditAccountName());
					}
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}
				/********
				 * UPDATE MODE FOR JOURNAL (For changing the ledger name and
				 * Journal amount):
				 ************/
				if (!journalObj.getDebitAccountName().equalsIgnoreCase(
						journal.getDebitAccountName())
						&& journalObj.getDebitAmount().compareTo(
								journal.getDebitAmount()) != 0) {
					/******** Formula for Old Debit Account ledger: *********/
					List<Ledger> customerList = ledgerDao
							.searchLedger(journalObj.getDebitAccountName());
					clBal = customerList.get(0).getClosingTotalBalance();
					clType = customerList.get(0).getClosingTotalType();

					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}

					BigDecimal dropClBalanceParty = clBal.subtract(journalObj
							.getDebitAmount());

					if (dropClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						dropClBalanceParty = dropClBalanceParty
								.multiply(convert);
					} else {
						finalcltype = "Debit";
					}

					ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
							finalcltype, journalObj.getDebitAccountName());
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}
				/************ Formula for Old Credit Account ledger: ***********/
				if (!journalObj.getCreditAccountName().equalsIgnoreCase(
						journal.getCreditAccountName())
						&& journalObj.getCreditAmount().compareTo(
								journal.getCreditAmount()) != 0) { 

					List<Ledger> customerList = ledgerDao
							.searchLedger(journalObj.getCreditAccountName());
					clBal = customerList.get(0).getClosingTotalBalance();
					clType = customerList.get(0).getClosingTotalType();

					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}

					BigDecimal dropClBalanceParty = clBal.add(journalObj
							.getCreditAmount());

					if (dropClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						dropClBalanceParty = dropClBalanceParty
								.multiply(convert);
					} else {
						finalcltype = "Debit";
					}

					ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
							finalcltype, journalObj.getCreditAccountName());
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}
				/********* Formula for New Debit Account ledger: ************/
				if (!journalObj.getDebitAccountName().equalsIgnoreCase(
						journal.getDebitAccountName())
						&& journalObj.getDebitAmount().compareTo(
								journal.getDebitAmount()) != 0) {

					List<Ledger> customerList = ledgerDao.searchLedger(journal
							.getDebitAccountName());
					clBal = customerList.get(0).getClosingTotalBalance();
					clType = customerList.get(0).getClosingTotalType();

					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}

					BigDecimal dropClBalanceParty = clBal.add(journal
							.getDebitAmount());

					if (dropClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						dropClBalanceParty = dropClBalanceParty
								.multiply(convert);
					} else {
						finalcltype = "Debit";
					}

					ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
							finalcltype, journal.getDebitAccountName());
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}
				/*********** Formula for New credit Account ledger: ***************/
				if (!journalObj.getCreditAccountName().equalsIgnoreCase(
						journal.getCreditAccountName())
						&& journalObj.getCreditAmount().compareTo(
								journal.getCreditAmount()) != 0) {
					List<Ledger> customerList = ledgerDao.searchLedger(journal
							.getCreditAccountName());
					clBal = customerList.get(0).getClosingTotalBalance();
					clType = customerList.get(0).getClosingTotalType();

					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}

					BigDecimal dropClBalanceParty = clBal.subtract(journal
							.getCreditAmount());

					if (dropClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						dropClBalanceParty = dropClBalanceParty
								.multiply(convert);
					} else {
						finalcltype = "Debit";
					}

					ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
							finalcltype, journal.getCreditAccountName());
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}				
			}

			/******** UPDATE MODE FOR CONTRA (For changing the Contra amount): ***********/
			if (journal.getJournalType().equals("Contra")) {
				/******* Formula for Debit Account ledger: ***********/
				if (journalObj.getDebitAmount().compareTo(
						journal.getDebitAmount()) != 0) {
					if (journalObj.getDebitAccountName().equalsIgnoreCase(
							journal.getDebitAccountName())) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journalObj.getDebitAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal
								.subtract(journalObj.getDebitAmount());
						BigDecimal finalClBalanceParty = dropClBalanceParty
								.add(journal.getDebitAmount());

						if (finalClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							finalClBalanceParty = finalClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,
								finalcltype, journalObj.getDebitAccountName());
					}
				}
				/******* Formula for Credit Account Ledger: ******/
				if (journalObj.getCreditAmount().compareTo(
						journal.getCreditAmount()) != 0) {
					if (journalObj.getCreditAccountName().equalsIgnoreCase(
							journal.getCreditAccountName())) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journalObj.getCreditAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal.add(journalObj
								.getCreditAmount());
						BigDecimal finalClBalanceParty = dropClBalanceParty
								.subtract(journal.getCreditAmount());

						if (finalClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							finalClBalanceParty = finalClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,
								finalcltype, journalObj.getCreditAccountName());
					}
				}
				/**************** UPDATE MODE FOR CONTRA (For changing the ledger name): ****************/
				if (!journalObj.getDebitAccountName().equalsIgnoreCase(
						journal.getDebitAccountName())) {
					if (journalObj.getDebitAmount().compareTo(
							journal.getDebitAmount()) == 0) {
						/********** Formula for Old Debit Account ledger: ************/
						List<Ledger> customerList = ledgerDao
								.searchLedger(journalObj.getDebitAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal
								.subtract(journalObj.getDebitAmount());

						if (dropClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							dropClBalanceParty = dropClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
								finalcltype, journalObj.getDebitAccountName());
					}
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}
				/******************* Formula for Old Credit Account ledger: ******************/
				if (!journalObj.getCreditAccountName().equalsIgnoreCase(
						journal.getCreditAccountName())) {
					if (journalObj.getCreditAmount().compareTo(
							journal.getCreditAmount()) == 0) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journalObj.getCreditAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal.add(journalObj
								.getCreditAmount());

						if (dropClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							dropClBalanceParty = dropClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";

						}
						ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
								finalcltype, journalObj.getCreditAccountName());
					}
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}

				/*********************** Formula for New Debit Account ledger: *********************/
				if (!journalObj.getDebitAccountName().equalsIgnoreCase(
						journal.getDebitAccountName())) {
					if (journalObj.getDebitAmount().compareTo(
							journal.getDebitAmount()) == 0) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journal.getDebitAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal.add(journal
								.getDebitAmount());

						if (dropClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							dropClBalanceParty = dropClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
								finalcltype, journal.getDebitAccountName());
					}
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}
				/****************** Formula for New credit Account ledger: ********************/
				if (!journalObj.getCreditAccountName().equalsIgnoreCase(
						journal.getCreditAccountName())) {
					if (journalObj.getCreditAmount().compareTo(
							journal.getCreditAmount()) == 0) {
						List<Ledger> customerList = ledgerDao
								.searchLedger(journal.getCreditAccountName());
						clBal = customerList.get(0).getClosingTotalBalance();
						clType = customerList.get(0).getClosingTotalType();

						if (clType.equalsIgnoreCase("Credit")) {
							clBal = zero.subtract(clBal);
						}

						BigDecimal dropClBalanceParty = clBal.subtract(journal
								.getCreditAmount());

						if (dropClBalanceParty.signum() == -1) {
							finalcltype = "Credit";
							dropClBalanceParty = dropClBalanceParty
									.multiply(convert);
						} else {
							finalcltype = "Debit";
						}

						ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
								finalcltype, journal.getCreditAccountName());
					}
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}
				/*******
				 * UPDATE MODE FOR CONTRA (For changing the ledger name and
				 * Contra amount):
				 *********/
				if (!journalObj.getDebitAccountName().equalsIgnoreCase(
						journal.getDebitAccountName())
						&& journalObj.getDebitAmount().compareTo(
								journal.getDebitAmount()) != 0) {
					/*********** Formula for old Debit Account ledger: *********/
					List<Ledger> customerList = ledgerDao
							.searchLedger(journalObj.getDebitAccountName());
					clBal = customerList.get(0).getClosingTotalBalance();
					clType = customerList.get(0).getClosingTotalType();

					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}

					BigDecimal dropClBalanceParty = clBal.subtract(journalObj
							.getDebitAmount());

					if (dropClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						dropClBalanceParty = dropClBalanceParty
								.multiply(convert);
					} else {
						finalcltype = "Debit";
					}

					ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
							finalcltype, journalObj.getDebitAccountName());
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}

				/********** Formula for old credit Account ledger: *********/
				if (!journalObj.getCreditAccountName().equalsIgnoreCase(
						journal.getCreditAccountName())
						&& journalObj.getCreditAmount().compareTo(
								journal.getCreditAmount()) != 0) {
					List<Ledger> customerList = ledgerDao
							.searchLedger(journalObj.getCreditAccountName());
					clBal = customerList.get(0).getClosingTotalBalance();
					clType = customerList.get(0).getClosingTotalType();

					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}

					BigDecimal dropClBalanceParty = clBal.add(journalObj
							.getCreditAmount());

					if (dropClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						dropClBalanceParty = dropClBalanceParty
								.multiply(convert);
					} else {
						finalcltype = "Debit";
					}

					ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
							finalcltype, journalObj.getCreditAccountName());
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}
				/******** Formula for New Debit Account Ledger: ********/
				if (!journalObj.getDebitAccountName().equalsIgnoreCase(
						journal.getDebitAccountName())
						&& journalObj.getDebitAmount().compareTo(
								journal.getDebitAmount()) != 0) {
					List<Ledger> customerList = ledgerDao.searchLedger(journal
							.getDebitAccountName());
					clBal = customerList.get(0).getClosingTotalBalance();
					clType = customerList.get(0).getClosingTotalType();

					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}

					BigDecimal dropClBalanceParty = clBal.add(journal
							.getDebitAmount());

					if (dropClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						dropClBalanceParty = dropClBalanceParty
								.multiply(convert);
					} else {
						finalcltype = "Debit";
					}

					ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
							finalcltype, journal.getDebitAccountName());
					
					// Set Account group code
					updateDebitCreditCode(journal);

				}
				/************ Formula for New Credit Account Ledger: ************/
				if (!journalObj.getCreditAccountName().equalsIgnoreCase(
						journal.getCreditAccountName())
						&& journalObj.getCreditAmount().compareTo(
								journal.getCreditAmount()) != 0) {

					List<Ledger> customerList = ledgerDao.searchLedger(journal
							.getCreditAccountName());
					clBal = customerList.get(0).getClosingTotalBalance();
					clType = customerList.get(0).getClosingTotalType();

					if (clType.equalsIgnoreCase("Credit")) {
						clBal = zero.subtract(clBal);
					}

					BigDecimal dropClBalanceParty = clBal.subtract(journal
							.getCreditAmount());

					if (dropClBalanceParty.signum() == -1) {
						finalcltype = "Credit";
						dropClBalanceParty = dropClBalanceParty
								.multiply(convert);
					} else {
						finalcltype = "Debit";
					}

					ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
							finalcltype, journal.getCreditAccountName());
					
					// Set Account group code
					updateDebitCreditCode(journal);
				}
			}

		}
		/*************** For changing the journal type while update mode. ***************/
		if (!journalObj.getJournalType().equalsIgnoreCase(
				journal.getJournalType())) {
			// Formula for Old Debit and Credit Account ledger:
			if (!journalObj.getDebitAccountName().equalsIgnoreCase(
					journal.getDebitAccountName())) {
				List<Ledger> customerList = ledgerDao.searchLedger(journalObj
						.getDebitAccountName());
				clBal = customerList.get(0).getClosingTotalBalance();
				clType = customerList.get(0).getClosingTotalType();

				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}

				BigDecimal dropClBalanceParty = clBal.subtract(journalObj
						.getDebitAmount());

				if (dropClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					dropClBalanceParty = dropClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";
				}

				ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
						finalcltype, journalObj.getDebitAccountName());
				
				// Set Account group code
				updateDebitCreditCode(journal);
			}

			if (!journalObj.getCreditAccountName().equalsIgnoreCase(
					journal.getCreditAccountName())) {
				List<Ledger> customerList = ledgerDao.searchLedger(journalObj
						.getCreditAccountName());
				clBal = customerList.get(0).getClosingTotalBalance();
				clType = customerList.get(0).getClosingTotalType();

				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}

				BigDecimal dropClBalanceParty = clBal.add(journalObj
						.getCreditAmount());

				if (dropClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					dropClBalanceParty = dropClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";
				}

				ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
						finalcltype, journalObj.getCreditAccountName());
				
				// Set Account group code
				updateDebitCreditCode(journal);
			}

			// Formula for New Debit and credit Account ledger:
			if (!journalObj.getDebitAccountName().equalsIgnoreCase(
					journal.getDebitAccountName())) {
				List<Ledger> customerList = ledgerDao.searchLedger(journal
						.getDebitAccountName());
				clBal = customerList.get(0).getClosingTotalBalance();
				clType = customerList.get(0).getClosingTotalType();

				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}

				BigDecimal dropClBalanceParty = clBal.add(journal
						.getDebitAmount());

				if (dropClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					dropClBalanceParty = dropClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";
				}

				ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
						finalcltype, journal.getDebitAccountName());
				
				// Set Account group code
				updateDebitCreditCode(journal);
			}
			/************ Formula for New Debit and credit Account ledger: ***********/
			if (!journalObj.getCreditAccountName().equalsIgnoreCase(
					journal.getCreditAccountName())) {
				List<Ledger> customerList = ledgerDao.searchLedger(journal
						.getCreditAccountName());
				clBal = customerList.get(0).getClosingTotalBalance();
				clType = customerList.get(0).getClosingTotalType();

				if (clType.equalsIgnoreCase("Credit")) {
					clBal = zero.subtract(clBal);
				}

				BigDecimal dropClBalanceParty = clBal.subtract(journal
						.getCreditAmount());

				if (dropClBalanceParty.signum() == -1) {
					finalcltype = "Credit";
					dropClBalanceParty = dropClBalanceParty.multiply(convert);
				} else {
					finalcltype = "Debit";
				}

				ledgerDao.updateLedgerPartyBalance(dropClBalanceParty,
						finalcltype, journal.getCreditAccountName());
				
				// Set Account group code
				updateDebitCreditCode(journal);
			}

			/** For Journal_NO series change if JournalType Changes **/
			journal.setJournalNO(getJournalNumber(journal));
		}

		/** For Journal_NO series change if receiptType added **/
		if (journalObj.getReceiptType() == null
				&& journal.getReceiptType() != null) {
			journal.setJournalNO(getJournalNumber(journal));
		}

		journalDao.updateJournal(journal);
		return new ModelAndView("redirect:journal.htm");
	}

}