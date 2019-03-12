package com.jewellery.web;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.jewellery.core.JewelStockCore;
import com.jewellery.dao.AccountsDao;
import com.jewellery.dao.AppConfigDao;
import com.jewellery.dao.CardIssueDao;
import com.jewellery.dao.CategoryDao;
import com.jewellery.dao.CompanyInfoDao;
import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.JewelStockDao;
import com.jewellery.dao.JournalDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.dao.PurchaseDao;
import com.jewellery.dao.RateMasterDao;
import com.jewellery.dao.SalesDao;
import com.jewellery.dao.SalesOrderDao;
import com.jewellery.dao.StartSchemeDao;
import com.jewellery.dao.UserloginDao;
import com.jewellery.entity.Accounts;
import com.jewellery.entity.CardIssue;
import com.jewellery.entity.CompanyInfo;
import com.jewellery.entity.ConfigDetailList;
import com.jewellery.entity.ItemMaster;
import com.jewellery.entity.JewelStock;
import com.jewellery.entity.Journal;
import com.jewellery.entity.Ledger;
import com.jewellery.entity.Purchase;
import com.jewellery.entity.RateMaster;
import com.jewellery.entity.Sales;
import com.jewellery.entity.SalesOrder;
import com.jewellery.print.InvoiceBillFormat;
import com.jewellery.printservice.InvoiceTextFormatPrint;
import com.jewellery.util.JournalCode;
import com.jewellery.validator.SalesValidator;

@Controller
public class FormSalesController extends JournalCode {

	@Autowired
	private SalesDao salesDao;
	@Autowired
	private ItemMasterDao itemmasterDao;
	@Autowired
	private RateMasterDao ratemasterDao;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private LedgerDao ledgerDao;
	@Autowired
	private UserloginDao userloginDao;
	@Autowired
	private JournalDao journalDao;
	@Autowired
	private PurchaseDao purchaseDao;
	@Autowired
	private SalesOrderDao salesOrderDao;
	@Autowired
	private SalesValidator salesValidator;
	@Autowired
	private CardIssueDao cardIssueDao;
	@Autowired
	private AppConfigDao appConfigDao;
	@Autowired
	private CompanyInfoDao companyInfoDao;
	@Autowired
	StartSchemeDao startSchemeDao;
	@Autowired
	AccountsDao accountsDao;
	@Autowired
	JewelStockDao jewelStockDao;
	private Journal jrnl;
	BigDecimal clBal = new BigDecimal("0.0");
	String clType = null;
	BigDecimal ZERO = new BigDecimal(0);
	BigDecimal CONVERT = new BigDecimal("-1");
	List<String> ledgerGroupCode;
	private ItemMaster itemDetails;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	// Sales uri forwarded to formsales.jsp
	@RequestMapping(value = "/newSales.htm", method = RequestMethod.GET)
	public ModelAndView salesList(@ModelAttribute("sales") Sales sales,
			ModelMap model) {

		String id = sales.getExchangeBillNo();
		String customerName = sales.getCustomerName();
		model.addAttribute("name", ledgerDao.listLedgerName());
		model.addAttribute("s_manname", userloginDao.userlist());
		model.addAttribute("category", categoryDao.listCategoryName());
		model.addAttribute("itemnamelist", itemmasterDao.listAllItems());
		model.put("gs_rate", ratemasterDao.searchRateMaster());
		// model.addAttribute("billNo",
		// purchaseDao.getPurchaseBillNo(customerName));//getting Purchasebilno
		// of current date
		model.addAttribute("purchaseAmount", purchaseDao.getPurchaseAmount(id));
		model.addAttribute("bank_name", ledgerDao.listBank());
		model.addAttribute("salesorder_id",
				salesOrderDao.getSalesOrderId(customerName));
		model.addAttribute("salesReturn_id",
				salesDao.getSalesReturnId(customerName));// add new for SR Id on
															// 14-02-13
		// model.addAttribute("salesorder_amount",
		// salesOrderDao.getOrderAdvance(id));
		model.addAttribute("assets_name", ledgerDao.listCurrentAssets());
		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
		// get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		dateFormat.format(cal.getTime());
		model.put("sales", sales);
		return new ModelAndView("formsales", model);
	}

	@RequestMapping(value = "/CheckES.htm", method = RequestMethod.GET)
	public @ResponseBody
	String getCheckES() {
		List<ConfigDetailList> configDetail = appConfigDao.getModuleCode("ES");
		if (!configDetail.isEmpty()) {
			if (configDetail.get(0).getStatus().equalsIgnoreCase("Active")) {
				return ("yes");
			}
		}
		return ("no");
	}

	// ajax code
	@RequestMapping(value = "/purchase_Amt.htm", method = RequestMethod.GET)
	public @ResponseBody
	String getTime(@RequestParam(value = "name", required = true) String id,
			Sales saleobj) {

		String i = id;
		String amt = "0";
		if (i != "") {
			List<Purchase> result = purchaseDao.getPurchaseAmount(i);
			if (!result.isEmpty()) {
				amt = result.get(0).getTotalAmount().toString();
			}
		}

		return amt;
	}

	// Ajax autoComplete code for itemCodes.
	@RequestMapping(value = "/itemCode_Auto.htm", method = RequestMethod.GET)
	public @ResponseBody
	String getItemCodes(
			@RequestParam(value = "iCodePart", required = true) String icodePart) {

		List<String> icodes = itemmasterDao.getAutoItemCode(icodePart);
		String ID = "";
		try {
			ID = icodes.toString();
		} catch (java.lang.IndexOutOfBoundsException e) {

		}
		return ID;

	}

	// Ajax code for Purchase BillNO customer name add for bug no 1982 on
	// 16-8-12
	@RequestMapping(value = "/PurchaeBill_Customername.htm", method = RequestMethod.GET)
	public @ResponseBody
	String GetPurchaseBillName(
			@RequestParam(value = "customerPurchaeBillno", required = false) String customerName,
			@RequestParam(value = "SalesId") Integer salesid, Sales sales) {

		String ID = "";
		if (salesid != null) {
			try {
				Sales salesoldId = salesDao.getSales(salesid);
				String oldexchangeid = salesoldId.getExchangeBillNo();// for
																		// retrving
																		// the
																		// purchaseExchange
																		// Id
				List<Purchase> purchasebill = purchaseDao.diplayPurchaseBillID(
						customerName, oldexchangeid);
				ArrayList<String> purchaseBillNo = new ArrayList<String>();
				purchaseBillNo.add(0, "Select");
				purchaseBillNo.add(1, purchasebill.toString());
				ID = purchaseBillNo.toString();
			} catch (Exception w) {
				System.out.println(w);
			}
		} else {
			try {
				String oldexchangeid = null;// for retrving the purchaseExchange
											// Id
				List<Purchase> purchasebill = purchaseDao.diplayPurchaseBillID(
						customerName, oldexchangeid);
				ArrayList<String> purchaseBillNo = new ArrayList<String>();
				purchaseBillNo.add(0, "Select");
				purchaseBillNo.add(1, purchasebill.toString());
				ID = purchaseBillNo.toString();
			} catch (Exception w) {
				System.out.println(w);
			}
		}
		// System.out.println("Exchange purchaseID in ajax call:::"+ID);
		return ID;
	}

	/*
	 * ajax code for on change saleorder Id to get Advabce amount
	 * 
	 * @RequestMapping(value="/order_advance.htm",method=RequestMethod.GET)
	 * public @ResponseBody String
	 * GetadvanceAmount(@RequestParam(value="Amt",required=true)String
	 * id,SalesOrder salesOrder){ int i=0; try{ i=Integer.parseInt(id); }
	 * catch(java.lang.IndexOutOfBoundsException e) { id=null; }
	 * 
	 * String amt="0"; if(i!=0){
	 * List<SalesOrder>salesOrders=salesOrderDao.getOrderAdvance(i);
	 * amt=salesOrders.get(0).getAdvance().toString();
	 * 
	 * } return amt;
	 * 
	 * Commented for new chagne additonal changes ,since not in use . }
	 */

	/*******
	 * ajax code for on change saleorder Id to get Advabce amount with rate fix
	 * requirement
	 ******************/

	@RequestMapping(value = "/order_advance.htm", method = RequestMethod.GET)
	public @ResponseBody
	String GetadvanceAmount(
			@RequestParam(value = "SelectedValues", required = true) String id,
			String SelectedVal) {
		int i = 0;
		try {
			i = Integer.parseInt(id);
		} catch (java.lang.IndexOutOfBoundsException e) {
			id = null;
		}
		List<String> salesOrderlist = new ArrayList<String>();

		String salesOrderamtList = "0";
		if (i != 0) {
			try {
				List<SalesOrder> salesOrders = salesOrderDao.getOrderAdvance(i);
				List<SalesOrder> soRatefix = salesOrderDao.getOrderFixAmt(i);
				List<BigDecimal> rateMasterList = ratemasterDao
						.getGoldOrnamentRate();
				if (!salesOrders.isEmpty()) {
					salesOrderlist.add(0, salesOrders.get(0).getAdvance()
							.toString());
				}
				if (!soRatefix.isEmpty()) {
					salesOrderlist.add(1, soRatefix.get(0).getRateFixAmount()
							.toString());
					salesOrderlist.add(2, soRatefix.get(0).getRateFixGrams()
							.toString());
					salesOrderlist.add(3, soRatefix.get(0).getBullionRate1()
							.toString());
				} else {
					salesOrderlist.add(1, "0.00");
					salesOrderlist.add(2, "0.00");
					salesOrderlist.add(3, "0.00");
				}
				if (!rateMasterList.isEmpty()) {
					salesOrderlist.add(4, rateMasterList.get(0).toString());
				}
				salesOrderamtList = salesOrderlist.toString();
			} catch (IndexOutOfBoundsException ie) {
				ie.printStackTrace();
			}
		}
		return salesOrderamtList;
	}

	// ajax code for on change customer saleorder ID
	@RequestMapping(value = "/customer_name.htm", method = RequestMethod.GET)
	public @ResponseBody
	String GetName(
			@RequestParam(value = "customer", required = true) String customerName,
			Sales sales) {
		// List<SalesOrder> sOrders=salesOrderDao.getSalesOrderId(customerName);
		String ID = "";
		// int id;
		try {

			List<SalesOrder> listID = salesOrderDao
					.getSalesOrderId(customerName);
			if (!listID.isEmpty()) {
				ArrayList<String> salesorderNo = new ArrayList<String>();
				salesorderNo.add(0, "0");
				salesorderNo.add(1, listID.toString());
				ID = salesorderNo.toString();
			}
		} catch (Exception e) {

		}
		// System.out.println("sales order id in insert mode::::");
		return ID;
	}

	@RequestMapping(value = "/pan_Number.htm", method = RequestMethod.GET)
	public @ResponseBody
	String showPan(
			@RequestParam(value = "num", required = false) String customerName,
			Sales sales) {

		String PAN = "";
		List<Ledger> itemMaster = ledgerDao.listCustomerPan(customerName);
		try {
			if (itemMaster.get(0).getPanNumber() != null
					|| !itemMaster.get(0).getPanNumber().isEmpty()) {
				PAN = itemMaster.get(0).getPanNumber();
			}
		} catch (NullPointerException ne) {
		}

		return PAN;
	}

	/*** For Retrieving Card NO List Based on Customer Name **/
	@RequestMapping(value = "/SSCardNO.htm", method = RequestMethod.GET)
	public @ResponseBody
	String getCardNoList(
			@RequestParam(value = "customerName", required = false) String CustomerName) {

		ArrayList<String> cardList = new ArrayList<String>();
		String CardNOList = "";
		try {
			List<String> cardDetailsList = cardIssueDao
					.SSSalesCardNoList(CustomerName);

			if (cardDetailsList != null) {
				// cardList.add("Select");
				for (String s : cardDetailsList) {
					cardList.add(s);
				}
				CardNOList = cardList.toString();
			}
		} catch (Exception e) {
		}
		return CardNOList;
	}

	/*** For Retrieving Card Amount **/
	@RequestMapping(value = "/SSCardAmtGrms.htm", method = RequestMethod.GET)
	public @ResponseBody
	String getSSCardAmtGrms(
			@RequestParam(value = "SelectedValues", required = false) String SelectedVal) {
		String CardAmtList = "";
		BigDecimal TotalSumAmt = new BigDecimal("0.00");
		BigDecimal eachCardClAmt = new BigDecimal("0.00");
		BigDecimal eachCardClGram = new BigDecimal("0.00");
		List<CardIssue> cardDetailsList = null;
		List<String> cardList = new ArrayList<String>();
		String[] SelectedValues = SelectedVal.split(",");
		try {
			for (int i = 0; i < SelectedValues.length; i++) {
				cardDetailsList = cardIssueDao
						.getCardIssueCardNO(SelectedValues[i].trim());
				if (!cardDetailsList.isEmpty()) {
					if (cardDetailsList.get(0).getSchemeType()
							.equalsIgnoreCase("Amount")) {
						eachCardClAmt = eachCardClAmt.add(cardDetailsList
								.get(0).getClosingBalanceInRs());
					} else {
						eachCardClGram = eachCardClGram.add(cardDetailsList
								.get(0).getClosingBalanceInGrams());
					}
				}
			}
		} catch (Exception e) {
		}
		List<BigDecimal> rmaster = ratemasterDao.getGoldOrnamentRate();
		BigDecimal goldRate = new BigDecimal("0");
		if (!rmaster.isEmpty()) {
			goldRate = rmaster.get(0);
		}
		BigDecimal GramsAmt = eachCardClGram.multiply(goldRate);
		TotalSumAmt = eachCardClAmt.add(GramsAmt);
		cardList.add(0, eachCardClGram.toString());
		cardList.add(1, TotalSumAmt.toString());
		CardAmtList = cardList.toString();
		return CardAmtList;
	}

	/*
	 * @RequestMapping(value = "/itemCode_Auto.htm", method = RequestMethod.GET)
	 * public @ResponseBody String
	 * getItemCodes(@RequestParam(value="iCodePart",required=true)String
	 * icodePart){
	 * 
	 * List<String> icodes = itemmasterDao.getAutoItemCode(icodePart); String
	 * ID=""; try{ ID=icodes.toString(); }
	 * catch(java.lang.IndexOutOfBoundsException e) {
	 * 
	 * } return ID;
	 * 
	 * }
	 */

	@RequestMapping(value = "/itemCode_Detail.htm", method = RequestMethod.POST)
	public @ResponseBody
	String itemcode1_Detail(
			@RequestParam(value = "iCodeValue", required = true) String iCodeValue) {
		ArrayList<String> l1 = new ArrayList<String>();

		List<RateMaster> gs_rate = ratemasterDao.searchRateMaster();
		List<ItemMaster> itemList1 = itemmasterDao.searchItemMaster(iCodeValue);
		try {
			l1.add(0, gs_rate.get(0).getGoldOrnaments().toString());
			l1.add(1, gs_rate.get(0).getSilverOrnaments().toString());
			l1.add(2, itemList1.get(0).getItemName().toString());
			l1.add(3, itemList1.get(0).getCategoryName().toString());
			l1.add(4, itemList1.get(0).getMetalType().toString());
			l1.add(5, itemList1.get(0).getMetalUsed().toString());
			l1.add(6, itemList1.get(0).getGrossWeight().toString());
			l1.add(7, itemList1.get(0).getStoneCost().toString());
			l1.add(8, itemList1.get(0).getVaPercentage().toString());
			l1.add(9, itemList1.get(0).getMcPerGram().toString());
			l1.add(10, itemList1.get(0).getTax().toString());
			l1.add(11, itemList1.get(0).getLessPercentage().toString());
			l1.add(12, itemList1.get(0).getStone().toString());
			l1.add(13, itemList1.get(0).getItemseal().toString());
			l1.add(14,
					new Integer(itemList1.get(0).getTotalPieces()).toString());
			l1.add(15, new Integer(itemList1.get(0).getQty()).toString());
		} catch (NullPointerException e) {

		} catch (IndexOutOfBoundsException ie) {

		}
		String icodeDetail1 = "";
		try {
			icodeDetail1 = l1.toString();
		} catch (java.lang.IndexOutOfBoundsException e) {

		}
		return icodeDetail1;

	}

	// sales Row1 itemcode details
	@RequestMapping(value = "/salesItem", method = RequestMethod.POST, params = "s")
	public ModelAndView itemcode1(@ModelAttribute("sales") Sales sales,
			BindingResult result, SessionStatus status) {
		String salesId = sales.getItemCode();
		String id = sales.getExchangeBillNo();
		Integer orderId = sales.getSalesOrderID();
		List<ItemMaster> itemList1 = null;
		salesValidator.validate(sales, result);

		if (result.hasErrors()) {
			ModelMap model1 = new ModelMap();
			model1.addAttribute("name", ledgerDao.listLedgerName());
			model1.addAttribute("s_manname", userloginDao.userlist());
			model1.addAttribute("category", categoryDao.listCategoryName());
			model1.addAttribute("billNo",
					purchaseDao.getPurchaseBillNo(sales.getCustomerName()));// getting
																			// Purchasebilno
																			// on
																			// change
																			// customer
			model1.addAttribute("purchaseAmount",
					purchaseDao.getPurchaseAmount(id));
			model1.addAttribute("salesorder_id",
					salesOrderDao.getSalesOrderId(sales.getCustomerName()));
			model1.addAttribute("salesReturn_id",
					salesDao.getSalesReturnId(sales.getCustomerName()));// add
																		// new
																		// for
																		// SR Id
																		// on
																		// 14-02-13
			model1.addAttribute("bank_name", ledgerDao.listBank());
			model1.addAttribute("assets_name", ledgerDao.listCurrentAssets());
			model1.addAttribute("SSCardNoList",
					cardIssueDao.SSSalesCardNoList(sales.getCustomerName()));
			model1.put("command", sales);
			status.setComplete();
			return new ModelAndView("formsales", model1);
		}
		ModelMap model = new ModelMap();
		model.addAttribute("salesorder_amount",
				salesOrderDao.getOrderAdvance(orderId));
		model.addAttribute("salesorder_id",
				salesOrderDao.getSalesOrderId(sales.getCustomerName()));
		model.addAttribute("salesReturn_id",
				salesDao.getSalesReturnId(sales.getCustomerName()));// add new
																	// for SR Id
																	// on
																	// 14-02-13
		model.addAttribute("name", ledgerDao.listLedgerName());
		model.addAttribute("s_manname", userloginDao.userlist());
		model.addAttribute("category", categoryDao.listCategoryName());
		model.addAttribute("itemnamelist", itemmasterDao.listAllItems());
		itemList1 = itemmasterDao.searchItemMaster(salesId);
		model.put("gs_rate", ratemasterDao.searchRateMaster());
		model.addAttribute("itemList1", itemList1);
		model.addAttribute("billNo",
				purchaseDao.getPurchaseBillNo(sales.getCustomerName()));// getting
																		// Purchasebilno
																		// on
																		// Change
																		// customer
		model.addAttribute("purchaseAmount", purchaseDao.getPurchaseAmount(id));
		model.addAttribute("bank_name", ledgerDao.listBank());
		model.addAttribute("assets_name", ledgerDao.listCurrentAssets());
		model.addAttribute("SSCardNoList",
				cardIssueDao.SSSalesCardNoList(sales.getCustomerName()));
		model.put("command", sales);

		status.setComplete();
		return new ModelAndView("formsales", model);
	}

	// sales Row2 itemcode details
	@RequestMapping(value = "/salesItem", method = RequestMethod.POST, params = "s1")
	public ModelAndView itemcode2(@ModelAttribute("sales") Sales sales,
			BindingResult result, ModelMap model, SessionStatus status, HttpServletRequest request) {
		
		String id = sales.getExchangeBillNo();
		Integer orderId = sales.getSalesOrderID();
		model.addAttribute("salesorder_amount",
				salesOrderDao.getOrderAdvance(orderId));
		String salesId = sales.getItemCode1();

		salesValidator.validate(sales, result);

		model.addAttribute("name", ledgerDao.listLedgerName());
		model.addAttribute("s_manname", userloginDao.userlist());
		model.addAttribute("category", categoryDao.listCategoryName());
		model.addAttribute("billNo",
				purchaseDao.getPurchaseBillNo(sales.getCustomerName()));// getting
																		// PurchaseBill
																		// On
																		// Change
																		// Customer
		model.addAttribute("purchaseAmount", purchaseDao.getPurchaseAmount(id));
		model.addAttribute("salesorder_id",
				salesOrderDao.getSalesOrderId(sales.getCustomerName()));
		model.addAttribute("salesReturn_id",
				salesDao.getSalesReturnId(sales.getCustomerName()));// add new
																	// for SR Id
																	// on
																	// 14-02-13
		model.addAttribute("bank_name", ledgerDao.listBank());
		model.addAttribute("assets_name", ledgerDao.listCurrentAssets());
		model.addAttribute("SSCardNoList",
				cardIssueDao.SSSalesCardNoList(sales.getCustomerName()));

		if (result.hasErrors()) {
			model.put("command", sales);
			status.setComplete();
			return new ModelAndView("formsales", model);
		}

		List<ItemMaster> itemList2 = itemmasterDao.searchItemMaster(salesId);
		if (!itemList2.isEmpty()) {

			salesValidator.validateR2(sales, itemList2.get(0).getMetalType(),
					itemList2.get(0).getTax(), result);
			if (result.hasErrors()) {
				model.put("command", sales);
				status.setComplete();
				return new ModelAndView("formsales", model);
			}
		}
		model.addAttribute("itemnamelist", itemmasterDao.listAllItems());
		model.put("gs_rate", ratemasterDao.searchRateMaster());
		model.addAttribute("itemList2", itemList2);
		model.put("command", sales);

		status.setComplete();
		return new ModelAndView("formsales", model);
	}

	// sales Row3 itemcode details
	@RequestMapping(value = "/salesItem", method = RequestMethod.POST, params = "s2")
	public ModelAndView itemcode3(@ModelAttribute("sales") Sales sales,
			BindingResult result, ModelMap model, SessionStatus status) {
		String id = sales.getExchangeBillNo();
		String salesId = sales.getItemCode2();
		Integer orderId = sales.getSalesOrderID();

		salesValidator.validate(sales, result);

		model.addAttribute("name", ledgerDao.listLedgerName());
		model.addAttribute("s_manname", userloginDao.userlist());
		model.addAttribute("category", categoryDao.listCategoryName());
		model.addAttribute("billNo",
				purchaseDao.getPurchaseBillNo(sales.getCustomerName()));// getting
																		// Purchasebillno
																		// on
																		// change
																		// customer
		model.addAttribute("purchaseAmount", purchaseDao.getPurchaseAmount(id));
		model.addAttribute("salesorder_id",
				salesOrderDao.getSalesOrderId(sales.getCustomerName()));
		model.addAttribute("salesReturn_id",
				salesDao.getSalesReturnId(sales.getCustomerName()));// add new
																	// for SR Id
																	// on
																	// 14-02-13
		model.addAttribute("bank_name", ledgerDao.listBank());
		model.addAttribute("assets_name", ledgerDao.listCurrentAssets());
		model.addAttribute("salesorder_amount",
				salesOrderDao.getOrderAdvance(orderId));
		model.addAttribute("SSCardNoList",
				cardIssueDao.SSSalesCardNoList(sales.getCustomerName()));

		if (result.hasErrors()) {
			model.put("command", sales);
			status.setComplete();
			return new ModelAndView("formsales", model);
		}

		List<ItemMaster> itemList3 = itemmasterDao.searchItemMaster(salesId);
		if (!itemList3.isEmpty()) {
			salesValidator.validateR3(sales, itemList3.get(0).getMetalType(),
					itemList3.get(0).getTax(), result);

			if (result.hasErrors()) {
				model.put("command", sales);
				status.setComplete();
				return new ModelAndView("formsales", model);
			}
		}

		model.addAttribute("itemnamelist", itemmasterDao.listAllItems());
		model.put("gs_rate", ratemasterDao.searchRateMaster());
		model.addAttribute("itemList3", itemList3);
		model.put("command", sales);
		status.setComplete();
		return new ModelAndView("formsales", model);
	}

	// sales Row4 itemcode details
	@RequestMapping(value = "/salesItem", method = RequestMethod.POST, params = "s3")
	public ModelAndView itemcode4(@ModelAttribute("sales") Sales sales,
			BindingResult result, ModelMap model, SessionStatus status) {
		String id = sales.getExchangeBillNo();
		String salesId = sales.getItemCode3();
		Integer orderId = sales.getSalesOrderID();

		salesValidator.validate(sales, result);

		model.addAttribute("name", ledgerDao.listLedgerName());
		model.addAttribute("s_manname", userloginDao.userlist());
		model.addAttribute("category", categoryDao.listCategoryName());
		model.addAttribute("billNo",
				purchaseDao.getPurchaseBillNo(sales.getCustomerName()));// getting
																		// PurchasbillNo
																		// on
																		// change
																		// customer
		model.addAttribute("purchaseAmount", purchaseDao.getPurchaseAmount(id));
		model.addAttribute("salesorder_id",
				salesOrderDao.getSalesOrderId(sales.getCustomerName()));
		model.addAttribute("salesReturn_id",
				salesDao.getSalesReturnId(sales.getCustomerName()));// add new
																	// for SR Id
																	// on
																	// 14-02-13
		model.addAttribute("bank_name", ledgerDao.listBank());
		model.addAttribute("assets_name", ledgerDao.listCurrentAssets());
		model.addAttribute("salesorder_amount",
				salesOrderDao.getOrderAdvance(orderId));
		model.addAttribute("SSCardNoList",
				cardIssueDao.SSSalesCardNoList(sales.getCustomerName()));

		if (result.hasErrors()) {
			model.put("command", sales);
			status.setComplete();
			return new ModelAndView("formsales", model);
		}

		List<ItemMaster> itemList4 = itemmasterDao.searchItemMaster(salesId);
		if (!itemList4.isEmpty()) {
			salesValidator.validateR4(sales, itemList4.get(0).getMetalType(),
					itemList4.get(0).getTax(), result);

			if (result.hasErrors()) {
				model.put("command", sales);
				status.setComplete();
				return new ModelAndView("formsales", model);
			}
		}

		model.addAttribute("itemnamelist", itemmasterDao.listAllItems());
		model.put("gs_rate", ratemasterDao.searchRateMaster());
		model.addAttribute("itemList4", itemList4);
		model.put("command", sales);
		status.setComplete();
		return new ModelAndView("formsales", model);
	}

	// sales Row5 itemcode details

	@RequestMapping(value = "/salesItem", method = RequestMethod.POST, params = "s4")
	public ModelAndView itemcode5(@ModelAttribute("sales") Sales sales,
			BindingResult result, ModelMap model, SessionStatus status) {
		String id = sales.getExchangeBillNo();
		String salesId = sales.getItemCode4();
		Integer orderId = sales.getSalesOrderID();

		salesValidator.validate(sales, result);

		model.addAttribute("name", ledgerDao.listLedgerName());
		model.addAttribute("s_manname", userloginDao.userlist());
		model.addAttribute("billNo",
				purchaseDao.getPurchaseBillNo(sales.getCustomerName()));// getting
																		// Purchasebilno
																		// on
																		// change
																		// customer
		model.addAttribute("purchaseAmount", purchaseDao.getPurchaseAmount(id));
		model.addAttribute("salesorder_id",
				salesOrderDao.getSalesOrderId(sales.getCustomerName()));
		model.addAttribute("salesReturn_id",
				salesDao.getSalesReturnId(sales.getCustomerName()));// add new
																	// for SR Id
																	// on
																	// 14-02-13
		model.addAttribute("bank_name", ledgerDao.listBank());
		model.addAttribute("salesorder_amount",
				salesOrderDao.getOrderAdvance(orderId));
		model.addAttribute("assets_name", ledgerDao.listCurrentAssets());
		model.addAttribute("category", categoryDao.listCategoryName());
		model.addAttribute("SSCardNoList",
				cardIssueDao.SSSalesCardNoList(sales.getCustomerName()));

		if (result.hasErrors()) {
			model.put("command", sales);
			status.setComplete();
			return new ModelAndView("formsales", model);
		}

		List<ItemMaster> itemList5 = itemmasterDao.searchItemMaster(salesId);
		if (!itemList5.isEmpty()) {
			salesValidator.validateR5(sales, itemList5.get(0).getMetalType(),
					itemList5.get(0).getTax(), result);

			if (result.hasErrors()) {
				model.put("command", sales);
				status.setComplete();
				return new ModelAndView("formsales", model);
			}
		}

		model.addAttribute("itemnamelist", itemmasterDao.listAllItems());
		model.put("gs_rate", ratemasterDao.searchRateMaster());
		model.addAttribute("itemList5", itemList5);
		model.put("command", sales);
		status.setComplete();
		return new ModelAndView("formsales", model);
	}

	// for CANCEL button code
	@RequestMapping(value = "/salesItem.htm", method = RequestMethod.POST, params = "cancel")
	public ModelAndView cancelForm() {
		return new ModelAndView("redirect:sales.htm");
	}

	// for Estimate Sales List
	@RequestMapping(value = "/estimateSalesList.htm", method = RequestMethod.GET)
	public ModelAndView estimateList(@ModelAttribute("sales") Sales sales,
			ModelMap model) {
		model.put("salesList", salesDao.listSales());
		return new ModelAndView("estimateSalesList", model);
	}

	// Sales listing
	@RequestMapping(value = "/viewSales.htm", method = RequestMethod.GET)
	public ModelAndView showForm(@ModelAttribute("sales") Sales sales,
			@RequestParam(value = "salesId", required = false) Integer salesId,
			Integer purchaseId, ModelMap model) {
		List<Sales> salesList = salesDao.listSales();
		Sales salesoldId = salesDao.getSales(salesId);

		Integer orderId = salesoldId.getSalesOrderID();// added for retriving
														// the salesorderid in
														// viewmode.
		String oldName = salesoldId.getCustomerName();// added for retriving the
														// salesorderid in
														// viewmode.

		String orderid = salesoldId.getReceiptID();// added for retrving the
													// journal id in bullion
													// sales.
		String oldname = salesoldId.getCustomerName();// added for retrving the
														// journal id in bullion
														// sales.

		// System.out.println("cusotmer:::"+oldName+"id::::"+orderid);

		String oldsuppliername = salesoldId.getCustomerName();// for retrving
																// the
																// purchaseExchange
																// customername
		String oldexchangeid = salesoldId.getExchangeBillNo();// for retrving
																// the
																// purchaseExchange
																// Id

		String OldSRId = salesoldId.getSalesReturnId();
		List<Purchase> purchasebill = purchaseDao.diplayPurchaseBillID(
				oldsuppliername, oldexchangeid);// for retrving the
												// purchaseExchange BillNO

		List<SalesOrder> retriveId = salesOrderDao.ViewSalesOrderId(oldName,
				orderId);// added for retriving the salesorderid in viewmode.

		List<String> journalRetriveId = journalDao.ViewJournalId(oldname,
				orderid);// add for journal Id

		List<Sales> SRIDRetrive = salesDao.ViewSRId(oldsuppliername, OldSRId);// add
																				// for
																				// Retriving
																				// SR
																				// ID

		ArrayList<String> journalId = new ArrayList<String>();
		int ID = 1;
		if (!journalRetriveId.isEmpty()) {
			journalId.add(0, "Select");
			for (String s : journalRetriveId) {
				journalId.add(ID, s);
				ID++;
			}

		}
		// System.out.println("ReceiptID IN VIEW MODE:::::::"+journalId);

		if (salesList == null || salesList.isEmpty()) {
			salesId = null;
		}

		if (salesId != null && !"".equals(salesId)) {
			sales = salesDao.getSales(salesId);

			if (sales.getFormType().equals("Sales")
					|| sales.getFormType().equals("Estimate Sales")) {
				String id = sales.getExchangeBillNo();
				model.addAttribute("name", ledgerDao.listLedgerName());
				model.put("gs_rate", ratemasterDao.searchRateMaster());
				model.addAttribute("s_manname", userloginDao.userlist());
				// model.addAttribute("billNo",
				// purchaseDao.getPurchaseBillNo());//getting Purchasebilno of
				// current date
				model.addAttribute("purchaseAmount",
						purchaseDao.getPurchaseAmount(id));
				model.addAttribute("bank_name", ledgerDao.listBank());
				model.addAttribute("assets_name", ledgerDao.listCurrentAssets());
				// model.addAttribute("salesorder_id",
				// salesOrderDao.getSalesOrderId(sales.getCustomerName()));
				model.addAttribute("salesorder_id", retriveId);// for retrving
																// the
																// salesORderId
				model.addAttribute("billNo", purchasebill);// for retrving the
															// purchaseExchange
															// BillNO
				model.addAttribute("salesReturn_id", SRIDRetrive);// for
																	// retrving
																	// the Sales
																	// Return ID
				model.addAttribute("sales", sales);
				// Saving Scheme Selected Card No Binding even if card is NOT
				// "Active" and "Matured"
				List<String> SSCardNoList = cardIssueDao
						.SSSalesCardNoList(sales.getCustomerName());
				try {
					if (sales.getSSCardNo() != null
							&& !sales.getSSCardNo().isEmpty()
							&& !sales.getSSCardNo().equalsIgnoreCase("")) {
						String SelectedCarNo[] = sales.getSSCardNo().split(",");
						for (int i = 0; i < SelectedCarNo.length; i++) {
							CardIssue cardIssueRecord = cardIssueDao
									.getCardIssueCardNO(SelectedCarNo[i])
									.get(0);
							if (!cardIssueRecord.getStatus().equalsIgnoreCase(
									"Active")
									&& !cardIssueRecord.getStatus()
											.equalsIgnoreCase("Matured")) {
								SSCardNoList.add(cardIssueRecord.getCardNo());
							}
						}
					}
				} catch (IndexOutOfBoundsException e) {
				}

				model.addAttribute("SSCardNoList", SSCardNoList);
				return new ModelAndView("formsales", model);

			} else if (sales.getFormType().equals("Bullion Sales")) {
				// System.out.println("in bullion sales view::::");
				model.addAttribute("journalListReceiptID", journalId);// add for
																		// jorunal
																		// retreving
																		// id
				model.addAttribute("name", ledgerDao.listLedgerName());
				model.addAttribute("s_manname", userloginDao.userlist());
				model.put("gs_rate", ratemasterDao.searchRateMaster());
				model.addAttribute("sales", sales);

				return new ModelAndView("formbullionsales", model);

			} else if (sales.getFormType().equals("Sales Return")) {
				model.addAttribute("name", ledgerDao.listLedgerName());
				model.addAttribute("s_manname", userloginDao.userlist());
				model.addAttribute("sales", sales);
				model.put("gs_rate", ratemasterDao.searchRateMaster());
				return new ModelAndView("formsalesreturn", model);
			}
		}
		return new ModelAndView("redirect:sales.htm", model);
		// directed to formSales.jsp page with details.
	}

	/** Estimate Sales Cancel Invoice **/
	@RequestMapping(value = "/salesItem", method = RequestMethod.POST, params = "cancelSales")
	public ModelAndView cancelSales(@ModelAttribute("sales") Sales salesobj,
			BindingResult result, ModelMap model, SessionStatus stauts) {
		// Data from Database
		Sales CancelledSales = salesDao.getSalesByIdList(salesobj.getSalesId())
				.get(0);

		// SalesOrder
		if (CancelledSales.getSalesOrderID() != null
				|| CancelledSales.getSalesOrderID() != 0) {
			salesOrderDao
					.UpdateStatusToAccept(CancelledSales.getSalesOrderID());
		}

		try {
			CancelledSales.setBillType("Cancelled");
			CancelledSales.setSalesOrderID(null);
			CancelledSales.setSalesOrderAmount(ZERO);
			salesDao.updateSales(CancelledSales);
		} catch (Exception e) {
			System.out.println("FSC CS EX");
		}

		/** Ledger Updation */
		// **************************** Party Ledger for GrandAmount
		if (!CancelledSales.getCustomerName().equals("walk_in")) {
			List<Ledger> customerNameList = ledgerDao
					.searchLedger(CancelledSales.getCustomerName());
			BigDecimal partyClosingBal = customerNameList.get(0)
					.getClosingTotalBalance();
			String clTypeParty = customerNameList.get(0).getClosingTotalType();
			String finalClType = "";

			if (clTypeParty.equalsIgnoreCase("Credit")) {
				partyClosingBal = ZERO.subtract(partyClosingBal);
			}

			BigDecimal finalPartyClosingBal = partyClosingBal
					.subtract(CancelledSales.getBillAmount());

			if (finalPartyClosingBal.signum() == -1) {
				finalClType = "Credit";
				finalPartyClosingBal = finalPartyClosingBal.multiply(CONVERT);
			} else {
				finalClType = "Debit";
			}

			ledgerDao.updateLedgerPartyBalance(finalPartyClosingBal,
					finalClType, CancelledSales.getCustomerName());

			// ************************** Party Ledger for Cash Amount ( Payment
			// Mode : Cash )

			List<Ledger> customerName = ledgerDao.searchLedger(CancelledSales
					.getCustomerName());
			BigDecimal ClBalParty = customerName.get(0)
					.getClosingTotalBalance();
			String typePartyBal = customerName.get(0).getClosingTotalType();
			finalClType = "";

			if (typePartyBal.equalsIgnoreCase("Credit")) {
				ClBalParty = ZERO.subtract(ClBalParty);
			}

			BigDecimal finalClBalanceParty = ClBalParty.add(CancelledSales
					.getCashAmount());

			if (finalClBalanceParty.signum() == -1) {
				finalClType = "Credit";
				finalClBalanceParty = finalClBalanceParty.multiply(CONVERT);
			} else {
				finalClType = "Debit";
			}

			ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,
					finalClType, CancelledSales.getCustomerName());
		}

		// ****************************** Stock Update Reversal

		BigDecimal Weight = new BigDecimal("0.0");

		String itemCode = CancelledSales.getItemCode();
		Weight = CONVERT.multiply(CancelledSales.getGrossWeight());
		int qty = 1;
		int totalPcs = qty;

		if (itemCode != null && CancelledSales.getGrossWeight().signum() == 1) {
			if (itemCode.compareToIgnoreCase("IT100011") == 0
					|| itemCode.compareToIgnoreCase("IT100012") == 0) {
				itemmasterDao.updateLooseStock(Weight, Weight, Weight, -1,
						itemCode);
			} else {
				itemmasterDao.updateSalesStock(CancelledSales.getGrossWeight(),
						qty, totalPcs, itemCode);
			}
		} else {
			CancelledSales.setItemCode("");
		}

		String itemcode1 = CancelledSales.getItemCode1();
		Weight = CONVERT.multiply(CancelledSales.getGrossWeight1());
		qty = 1;
		totalPcs = qty;

		if (itemcode1 != null && CancelledSales.getGrossWeight1().signum() == 1) {
			if (itemcode1.compareToIgnoreCase("IT100011") == 0
					|| itemcode1.compareToIgnoreCase("IT100012") == 0) {
				itemmasterDao.updateLooseStock(Weight, Weight, Weight, -1,
						itemcode1);
			} else {
				itemmasterDao.updateSalesStock(
						CancelledSales.getGrossWeight1(), qty, totalPcs,
						itemcode1);
			}
		} else {
			CancelledSales.setItemCode1("");
		}

		String itemcode2 = CancelledSales.getItemCode2();
		Weight = CONVERT.multiply(CancelledSales.getGrossWeight2());
		qty = 1;
		totalPcs = qty;

		if (itemcode2 != null && CancelledSales.getGrossWeight2().signum() == 1) {
			if (itemcode2.compareToIgnoreCase("IT100011") == 0
					|| itemcode2.compareToIgnoreCase("IT100012") == 0) {
				itemmasterDao.updateLooseStock(Weight, Weight, Weight, -1,
						itemcode2);
			} else {
				itemmasterDao.updateSalesStock(
						CancelledSales.getGrossWeight2(), qty, totalPcs,
						itemcode2);
			}
		} else {
			CancelledSales.setItemCode2("");
		}

		String itemcode3 = CancelledSales.getItemCode3();
		Weight = CONVERT.multiply(CancelledSales.getGrossWeight3());
		qty = 1;
		totalPcs = qty;

		if (itemcode3 != null && CancelledSales.getGrossWeight3().signum() == 1) {
			if (itemcode3.compareToIgnoreCase("IT100011") == 0
					|| itemcode3.compareToIgnoreCase("IT100012") == 0) {
				itemmasterDao.updateLooseStock(Weight, Weight, Weight, -1,
						itemcode3);
			} else {
				itemmasterDao.updateSalesStock(
						CancelledSales.getGrossWeight3(), qty, totalPcs,
						itemcode3);
			}
		} else {
			CancelledSales.setItemCode3("");
		}

		String itemcode4 = CancelledSales.getItemCode4();
		Weight = CONVERT.multiply(CancelledSales.getGrossWeight4());
		qty = 1;
		totalPcs = qty;

		if (itemcode4 != null && CancelledSales.getGrossWeight4().signum() == 1) {
			if (itemcode4.compareToIgnoreCase("IT100011") == 0
					|| itemcode4.compareToIgnoreCase("IT100012") == 0) {
				itemmasterDao.updateLooseStock(Weight, Weight, Weight, -1,
						itemcode4);
			} else {
				itemmasterDao.updateSalesStock(
						CancelledSales.getGrossWeight4(), qty, totalPcs,
						itemcode4);
			}
		} else {
			CancelledSales.setItemCode4("");
		}

		model.put("salesList", salesDao.listSales());
		return new ModelAndView("redirect:estimateSalesList.htm", model);
	}

	@RequestMapping(value = "/salesItem", method = RequestMethod.POST, params = "update")
	public ModelAndView updateSales(@ModelAttribute("sales") Sales salesNew, BindingResult result, ModelMap model, SessionStatus stauts,HttpServletRequest request) 
	{
		Integer salesId = salesNew.getSalesId();
		String id = salesNew.getExchangeBillNo();
		Integer sales_orderId = salesNew.getSalesOrderID();
		Sales salesOld = salesDao.getSales(salesId);
		String salesReturnId = salesNew.getSalesReturnId();// for SR id update
		String OldSReturnId = salesOld.getSalesReturnId();// for SR id update
		/* add for bug number 0062 */
		Integer Old_salesOrderId = salesOld.getSalesOrderID();
		try {
			if (!Old_salesOrderId.equals(sales_orderId)) {
				salesOrderDao.UpdateStatusToAccept(Old_salesOrderId);
			}
		} catch (java.lang.NullPointerException e) {

		}

		String Old_purchaseExhchangeId = salesOld.getExchangeBillNo();
		if (!Old_purchaseExhchangeId.equals(id)) {
			purchaseDao.UpdateStatusToAccept(Old_purchaseExhchangeId);
		}

		/* add for SR ID update From SOld to yes */
		try {
			if (!OldSReturnId.equals(salesReturnId)) {
				salesDao.UpdateSRToYes(OldSReturnId);
			}
		} catch (java.lang.NullPointerException e) {

		}
		// ************************************************* Validating Sales
		// Update Form ************************************* /
		salesValidator.validate(salesOld, salesNew, result);
		salesValidator.validateInsert(salesNew, result);
		if (result.hasErrors()) {

			model.addAttribute("name", ledgerDao.listLedgerName());
			model.addAttribute("s_manname", userloginDao.userlist());
			// model.addAttribute("billNo",
			// purchaseDao.getPurchaseBillNo(sales)); //getting Purchase bill no
			// of current date
			model.addAttribute("purchaseAmount",purchaseDao.getPurchaseAmount(id));
			model.addAttribute("bank_name", ledgerDao.listBank());
			model.addAttribute("assets_name", ledgerDao.listCurrentAssets());

			// Saving Scheme Selected Card No Binding
			List<String> SSCardNoList = cardIssueDao.SSSalesCardNoList(salesNew.getCustomerName());
			try {
				if (salesNew.getSSCardNo() != null
						&& !salesNew.getSSCardNo().isEmpty()) {
					String SelectedCarNo[] = salesNew.getSSCardNo().split(",");
					for (int i = 0; i < SelectedCarNo.length; i++) {
						CardIssue cardIssueRecord = cardIssueDao.getCardIssueCardNO(SelectedCarNo[i]).get(0);
						if (!cardIssueRecord.getStatus().equalsIgnoreCase("Active") && !cardIssueRecord.getStatus().equalsIgnoreCase("Matured")) {
							SSCardNoList.add(cardIssueRecord.getCardNo());
						}
					}
				}
			} catch (IndexOutOfBoundsException e) {
			}
			model.addAttribute("SSCardNoList", SSCardNoList);
			model.addAttribute("command", salesNew);
			model.addAttribute("errorType", "updateError");
			return new ModelAndView("formsales", model);
		}

		// *********************************************** Stock Update for five
		// Rows **********************************************/

		/* Stock Update for Row 1 */

		String icodeR1 = null;
		icodeR1 = salesNew.getItemCode();

		if (icodeR1 != null && icodeR1 != "") {
			if (!salesNew.getMetalUsed().equalsIgnoreCase("Gold Loose Stock") && !salesNew.getMetalUsed().equalsIgnoreCase("Silver Loose Stock")) 
			{
				List<ItemMaster> itemObj1 = itemmasterDao.getItemsByCode(icodeR1);
				BigDecimal T1grwt = itemObj1.get(0).getGrossWeight();

				BigDecimal diffgrwt1 = T1grwt.add(salesOld.getGrossWeight());
				BigDecimal finalgrwt1 = diffgrwt1.subtract(salesNew.getGrossWeight());

				if (finalgrwt1.signum() != -1) {
					itemmasterDao.updateSalesLooseStock(finalgrwt1, finalgrwt1,	finalgrwt1, icodeR1);
					/****
					 * Stockupdation in JewelStock table for update Mode
					 * **/
					/*int UpdatedQty = 0;
					if (salesNew.getMetalUsed().equalsIgnoreCase("Gold Loose Stock") || salesNew.getMetalUsed().equalsIgnoreCase("Silver Loose Stock")){
						UpdatedQty = salesNew.getSoldQty();
					}else{
						UpdatedQty = salesNew.getNumberOfPieces();
					}*/
					
					JewelStockCore.jewelSUpdationForUpdate(jewelStockDao,
							"Sales", salesOld.getSalesTypeId(),
							salesNew.getSalesDate(), salesNew.getItemName(),
							icodeR1, salesNew.getCategoryName(),
							salesNew.getBullionType(), salesNew.getMetalUsed(),
							/*UpdatedQty,*/
							salesNew.getNumberOfPieces(),
							salesNew.getTotalPieces(), finalgrwt1, finalgrwt1,
							finalgrwt1, salesNew.getStone(),
							salesNew.getStoneRatePerCaret(),
							salesNew.getStoneCost(), salesNew.getBullionRate(),
							salesNew.getValueAdditionCharges(),
							salesNew.getLessPercentage(),
							salesNew.getMcByAmount(), salesNew.getMcByGrams(),
							salesNew.getVtax(), salesNew.getSalesHMCharges());
				}
			} else {
				List<ItemMaster> itemObj1 = itemmasterDao.getItemsByCode(icodeR1);
				BigDecimal T1grwt = itemObj1.get(0).getGrossWeight();
				int T1qty = itemObj1.get(0).getQty();
				if (salesNew.getGrossWeight().compareTo(salesOld.getGrossWeight()) == 1) {
					/***
					 * if the updated value is greater then the inserted value .
					 ***/
					BigDecimal temp = salesOld.getGrossWeight().subtract(salesNew.getGrossWeight());
					BigDecimal finalgrwt = T1grwt.subtract(temp);
					int diffQty = salesOld.getSoldQty() - salesNew.getSoldQty();
					int finalQty = T1qty - diffQty;
					itemmasterDao.updateSalesCategoryLooseStock(finalgrwt, finalgrwt, finalgrwt, finalQty, icodeR1);
					/****
					 * Stockupdation in JewelStock table for update Mode
					 * **/
					JewelStockCore.jewelStockCatLooseStockUpdate(jewelStockDao,
							"Sales", salesOld.getSalesTypeId(),
							salesNew.getSalesDate(), salesNew.getItemName(),
							icodeR1, salesNew.getCategoryName(),
							salesNew.getBullionType(), salesNew.getMetalUsed(),
							salesNew.getSoldQty(),
							salesNew.getSoldQty(), salesNew.getGrossWeight(), salesNew.getGrossWeight(),
							salesNew.getGrossWeight(), salesNew.getStone(),
							salesNew.getStoneRatePerCaret(),
							salesNew.getStoneCost(), salesNew.getBullionRate(),
							salesNew.getValueAdditionCharges(),
							salesNew.getLessPercentage(),
							salesNew.getMcByAmount(), salesNew.getMcByGrams(),
							salesNew.getVtax(), salesNew.getSalesHMCharges());
				} else {
					/***
					 * if the updated value is smaller then the inserted value .
					 ***/
					BigDecimal temp = salesOld.getGrossWeight().subtract(salesNew.getGrossWeight());
					BigDecimal finalgrwt = T1grwt.add(temp);
					int diffQty = salesOld.getSoldQty() - salesNew.getSoldQty();
					int finalQty = T1qty + diffQty;
					itemmasterDao.updateSalesCategoryLooseStock(finalgrwt, finalgrwt, finalgrwt, finalQty, icodeR1);
					/****
					 * Stockupdation in JewelStock table for update Mode
					 * **/
					JewelStockCore.jewelStockCatLooseStockUpdate(jewelStockDao,
							"Sales", salesOld.getSalesTypeId(),
							salesNew.getSalesDate(), salesNew.getItemName(),
							icodeR1, salesNew.getCategoryName(),
							salesNew.getBullionType(), salesNew.getMetalUsed(),
							salesNew.getSoldQty(),
							salesNew.getSoldQty(), salesNew.getGrossWeight(), salesNew.getGrossWeight(),
							salesNew.getGrossWeight(), salesNew.getStone(),
							salesNew.getStoneRatePerCaret(),
							salesNew.getStoneCost(), salesNew.getBullionRate(),
							salesNew.getValueAdditionCharges(),
							salesNew.getLessPercentage(),
							salesNew.getMcByAmount(), salesNew.getMcByGrams(),
							salesNew.getVtax(), salesNew.getSalesHMCharges());
				}
			}
		}

		/* Stock Update for Row 2 */
		String icodeR2 = null;
		icodeR2 = salesNew.getItemCode1();

		if (icodeR2 != null && icodeR2 != "") {
			if (!salesNew.getMetalUsed1().equalsIgnoreCase("Gold Loose Stock") && !salesNew.getMetalUsed1().equalsIgnoreCase("Silver Loose Stock")) 
			{
				List<ItemMaster> itemObj2 = itemmasterDao.getItemsByCode(icodeR2);
				BigDecimal T2grwt = itemObj2.get(0).getGrossWeight();

				BigDecimal diffgrwt2 = T2grwt.add(salesOld.getGrossWeight1());
				BigDecimal finalgrwt2 = diffgrwt2.subtract(salesNew.getGrossWeight1());

				if (finalgrwt2.signum() != -1) {
					itemmasterDao.updateSalesLooseStock(finalgrwt2, finalgrwt2,	finalgrwt2, icodeR2);
				/*	
					int UpdatedQty1 = 0;
					if (salesNew.getMetalUsed1().equalsIgnoreCase("Gold Loose Stock") || salesNew.getMetalUsed1().equalsIgnoreCase("Silver Loose Stock")){
						UpdatedQty1 = salesNew.getSoldQty1();
					}else{
						UpdatedQty1 = salesNew.getNumberOfPieces1();
					}*/
					/****
					 * Stockupdation in JewelStock table for update Mode
					 * **/
					JewelStockCore.jewelSUpdationForUpdate(jewelStockDao,
							"Sales", salesOld.getSalesTypeId(),
							salesNew.getSalesDate(), salesNew.getItemName1(),
							icodeR2, salesNew.getCategoryName1(),
							salesNew.getBullionType1(),
							salesNew.getMetalUsed1(),
							/*UpdatedQty1,*/
							salesNew.getNumberOfPieces1(),
							salesNew.getTotalPieces1(), finalgrwt2, finalgrwt2,
							finalgrwt2, salesNew.getStone1(),
							salesNew.getStoneRatePerCaret1(),
							salesNew.getStoneCost1(), salesNew.getBullionRate1(),
							salesNew.getValueAdditionCharges1(),
							salesNew.getLessPercentage1(),
							salesNew.getMcByAmount1(), salesNew.getMcByGrams1(),
							salesNew.getVtax1(), salesNew.getSalesHMCharges1());
				}
			} else {
				List<ItemMaster> itemObj2 = itemmasterDao.getItemsByCode(icodeR2);
				BigDecimal T2grwt = itemObj2.get(0).getGrossWeight();
				int T2qty = itemObj2.get(0).getQty();
				if (salesNew.getGrossWeight1().compareTo(salesOld.getGrossWeight1()) == 1) {
					/***
					 * if the updated value is greater then the inserted value .
					 ***/
					BigDecimal temp = salesOld.getGrossWeight1().subtract(salesNew.getGrossWeight1());
					BigDecimal finalgrwt = T2grwt.subtract(temp);
					int diffQty = salesOld.getSoldQty1() - salesNew.getSoldQty1();
					int finalQty = T2qty - diffQty;
					itemmasterDao.updateSalesCategoryLooseStock(finalgrwt,	finalgrwt, finalgrwt, finalQty, icodeR2);
					/****
					 * Stockupdation in JewelStock table for update Mode
					 * **/
					JewelStockCore.jewelStockCatLooseStockUpdate(jewelStockDao,
							"Sales", salesOld.getSalesTypeId(),
							salesNew.getSalesDate(), salesNew.getItemName1(),
							icodeR2, salesNew.getCategoryName1(),
							salesNew.getBullionType1(), salesNew.getMetalUsed1(),
							salesNew.getSoldQty1(),
							salesNew.getSoldQty1(), salesNew.getGrossWeight1(), salesNew.getGrossWeight1(),
							salesNew.getGrossWeight1(), salesNew.getStone1(),
							salesNew.getStoneRatePerCaret1(),
							salesNew.getStoneCost1(), salesNew.getBullionRate1(),
							salesNew.getValueAdditionCharges1(),
							salesNew.getLessPercentage1(),
							salesNew.getMcByAmount1(), salesNew.getMcByGrams1(),
							salesNew.getVtax1(), salesNew.getSalesHMCharges1());
				} else {
					/***
					 * if the updated value is smaller then the inserted value .
					 ***/
					BigDecimal temp = salesOld.getGrossWeight1().subtract(salesNew.getGrossWeight1());
					BigDecimal finalgrwt = T2grwt.add(temp);
					int diffQty = salesOld.getSoldQty1() - salesNew.getSoldQty1();
					int finalQty = T2qty + diffQty;
					itemmasterDao.updateSalesCategoryLooseStock(finalgrwt,	finalgrwt, finalgrwt, finalQty, icodeR2);
					/****
					 * Stockupdation in JewelStock table for update Mode
					 * **/
					JewelStockCore.jewelStockCatLooseStockUpdate(jewelStockDao,
							"Sales", salesOld.getSalesTypeId(),
							salesNew.getSalesDate(), salesNew.getItemName1(),
							icodeR2, salesNew.getCategoryName1(),
							salesNew.getBullionType1(), salesNew.getMetalUsed1(),
							salesNew.getSoldQty1(),
							salesNew.getSoldQty1(), salesNew.getGrossWeight1(), salesNew.getGrossWeight1(),
							salesNew.getGrossWeight1(), salesNew.getStone1(),
							salesNew.getStoneRatePerCaret1(),
							salesNew.getStoneCost1(), salesNew.getBullionRate1(),
							salesNew.getValueAdditionCharges1(),
							salesNew.getLessPercentage1(),
							salesNew.getMcByAmount1(), salesNew.getMcByGrams1(),
							salesNew.getVtax1(), salesNew.getSalesHMCharges1());
				} 
			}
		}

		/* Stock Update for Row 3 */
		String icodeR3 = null;
		icodeR3 = salesNew.getItemCode2();

		if (icodeR3 != null && icodeR3 != "") {
			if (!salesNew.getMetalUsed2().equalsIgnoreCase("Gold Loose Stock")	&& !salesNew.getMetalUsed2().equalsIgnoreCase("Silver Loose Stock")) 
			{
				List<ItemMaster> itemObj3 = itemmasterDao.getItemsByCode(icodeR3);
				BigDecimal T3grwt = itemObj3.get(0).getGrossWeight();
				BigDecimal diffgrwt3 = T3grwt.add(salesOld.getGrossWeight2());
				BigDecimal finalgrwt3 = diffgrwt3.subtract(salesNew.getGrossWeight2());

				if (finalgrwt3.signum() != -1) {
					itemmasterDao.updateSalesLooseStock(finalgrwt3, finalgrwt3,	finalgrwt3, icodeR3);
					/****
					 * Stockupdation in JewelStock table for update Mode
					 * **/
					JewelStockCore.jewelSUpdationForUpdate(jewelStockDao,
							"Sales", salesOld.getSalesTypeId(),
							salesNew.getSalesDate(), salesNew.getItemName(),
							icodeR3, salesNew.getCategoryName(),
							salesNew.getBullionType(),
							salesNew.getMetalUsed2(),
							salesNew.getNumberOfPieces(),
							salesNew.getTotalPieces(), finalgrwt3, finalgrwt3,
							finalgrwt3, salesNew.getStone(),
							salesNew.getStoneRatePerCaret(),
							salesNew.getStoneCost(), salesNew.getBullionRate(),
							salesNew.getValueAdditionCharges(),
							salesNew.getLessPercentage(),
							salesNew.getMcByAmount(), salesNew.getMcByGrams(),
							salesNew.getVtax(), salesNew.getSalesHMCharges());
				}
			} else {
				List<ItemMaster> itemObj3 = itemmasterDao.getItemsByCode(icodeR3);
				BigDecimal T3grwt = itemObj3.get(0).getGrossWeight();
				int T3qty = itemObj3.get(0).getQty();
				if (salesNew.getGrossWeight2().compareTo(salesOld.getGrossWeight2()) == 1) {
					/***
					 * if the updated value is greater then the inserted value .
					 ***/
					BigDecimal temp = salesOld.getGrossWeight2().subtract(salesNew.getGrossWeight2());
					BigDecimal finalgrwt = T3grwt.subtract(temp);
					int diffQty = salesOld.getSoldQty2() - salesNew.getSoldQty2();
					int finalQty = T3qty - diffQty;
					itemmasterDao.updateSalesCategoryLooseStock(finalgrwt, finalgrwt, finalgrwt, finalQty, icodeR3);
					/****
					 * Stockupdation in JewelStock table for update Mode
					 * **/
					JewelStockCore.jewelStockCatLooseStockUpdate(jewelStockDao,
							"Sales", salesOld.getSalesTypeId(),
							salesNew.getSalesDate(), salesNew.getItemName2(),
							icodeR3, salesNew.getCategoryName2(),
							salesNew.getBullionType2(), salesNew.getMetalUsed2(),
							salesNew.getSoldQty2(),
							salesNew.getSoldQty2(), salesNew.getGrossWeight2(), salesNew.getGrossWeight2(),
							salesNew.getGrossWeight2(), salesNew.getStone2(),
							salesNew.getStoneRatePerCaret2(),
							salesNew.getStoneCost2(), salesNew.getBullionRate2(),
							salesNew.getValueAdditionCharges2(),
							salesNew.getLessPercentage2(),
							salesNew.getMcByAmount2(), salesNew.getMcByGrams2(),
							salesNew.getVtax2(), salesNew.getSalesHMCharges2());
				} else {
					/***
					 * if the updated value is smaller then the inserted value .
					 ***/
					BigDecimal temp = salesOld.getGrossWeight2().subtract( salesNew.getGrossWeight2());
					BigDecimal finalgrwt = T3grwt.add(temp);
					int diffQty = salesOld.getSoldQty2() - salesNew.getSoldQty2();
					int finalQty = T3qty + diffQty;
					itemmasterDao.updateSalesCategoryLooseStock(finalgrwt, finalgrwt, finalgrwt, finalQty, icodeR3);
					/****
					 * Stockupdation in JewelStock table for update Mode
					 * **/
					JewelStockCore.jewelStockCatLooseStockUpdate(jewelStockDao,
							"Sales", salesOld.getSalesTypeId(),
							salesNew.getSalesDate(), salesNew.getItemName2(),
							icodeR3, salesNew.getCategoryName2(),
							salesNew.getBullionType2(), salesNew.getMetalUsed2(),
							salesNew.getSoldQty2(),
							salesNew.getSoldQty2(), salesNew.getGrossWeight2(), salesNew.getGrossWeight2(),
							salesNew.getGrossWeight2(), salesNew.getStone2(),
							salesNew.getStoneRatePerCaret2(),
							salesNew.getStoneCost2(), salesNew.getBullionRate2(),
							salesNew.getValueAdditionCharges2(),
							salesNew.getLessPercentage2(),
							salesNew.getMcByAmount2(), salesNew.getMcByGrams2(),
							salesNew.getVtax2(), salesNew.getSalesHMCharges2());
				}
			}
		}

		/* Stock Update for Row 4 */
		String icodeR4 = null;
		icodeR4 = salesNew.getItemCode3();

		if (icodeR4 != null && icodeR4 != "") {
			if (!salesNew.getMetalUsed3().equalsIgnoreCase("Gold Loose Stock") && !salesNew.getMetalUsed3().equalsIgnoreCase("Silver Loose Stock")) {
				List<ItemMaster> itemObj4 = itemmasterDao.getItemsByCode(icodeR4);
				BigDecimal T4grwt = itemObj4.get(0).getGrossWeight();

				BigDecimal diffgrwt4 = T4grwt.add(salesOld.getGrossWeight3());
				BigDecimal finalgrwt4 = diffgrwt4.subtract(salesNew.getGrossWeight3());

				if (finalgrwt4.signum() != -1) {
					itemmasterDao.updateSalesLooseStock(finalgrwt4, finalgrwt4,	finalgrwt4, icodeR4);
					/****
					 * Stockupdation in JewelStock table for update Mode
					 * **/
					JewelStockCore.jewelSUpdationForUpdate(jewelStockDao,
							"Sales", salesOld.getSalesTypeId(),
							salesNew.getSalesDate(), salesNew.getItemName(),
							icodeR4, salesNew.getCategoryName(),
							salesNew.getBullionType(),
							salesNew.getMetalUsed3(),
							salesNew.getNumberOfPieces(),
							salesNew.getTotalPieces(), finalgrwt4, finalgrwt4,
							finalgrwt4, salesNew.getStone(),
							salesNew.getStoneRatePerCaret(),
							salesNew.getStoneCost(), salesNew.getBullionRate(),
							salesNew.getValueAdditionCharges(),
							salesNew.getLessPercentage(),
							salesNew.getMcByAmount(), salesNew.getMcByGrams(),
							salesNew.getVtax(), salesNew.getSalesHMCharges());
				}
			} else {
				List<ItemMaster> itemObj4 = itemmasterDao.getItemsByCode(icodeR4);
				BigDecimal T4grwt = itemObj4.get(0).getGrossWeight();
				int T4qty = itemObj4.get(0).getQty();
				if (salesNew.getGrossWeight3().compareTo(salesOld.getGrossWeight3()) == 1) {
					/***
					 * if the updated value is greater then the inserted value .
					 ***/
					BigDecimal temp = salesOld.getGrossWeight3().subtract(salesNew.getGrossWeight3());
					BigDecimal finalgrwt = T4grwt.subtract(temp);
					int diffQty = salesOld.getSoldQty3() - salesNew.getSoldQty3();
					int finalQty = T4qty - diffQty;
					itemmasterDao.updateSalesCategoryLooseStock(finalgrwt, finalgrwt, finalgrwt, finalQty, icodeR4);
					/****
					 * Stockupdation in JewelStock table for update Mode
					 * **/
					JewelStockCore.jewelStockCatLooseStockUpdate(jewelStockDao,
							"Sales", salesOld.getSalesTypeId(),
							salesNew.getSalesDate(), salesNew.getItemName3(),
							icodeR4, salesNew.getCategoryName3(),
							salesNew.getBullionType3(), salesNew.getMetalUsed3(),
							salesNew.getSoldQty3(),
							salesNew.getSoldQty3(), salesNew.getGrossWeight3(), salesNew.getGrossWeight3(),
							salesNew.getGrossWeight3(), salesNew.getStone3(),
							salesNew.getStoneRatePerCaret3(),
							salesNew.getStoneCost3(), salesNew.getBullionRate3(),
							salesNew.getValueAdditionCharges3(),
							salesNew.getLessPercentage3(),
							salesNew.getMcByAmount3(), salesNew.getMcByGrams3(),
							salesNew.getVtax3(), salesNew.getSalesHMCharges3());
				} else {
					/***
					 * if the updated value is smaller then the inserted value .
					 ***/
					BigDecimal temp = salesOld.getGrossWeight3().subtract(salesNew.getGrossWeight3());
					BigDecimal finalgrwt = T4grwt.add(temp);
					int diffQty = salesOld.getSoldQty3() - salesNew.getSoldQty3();
					int finalQty = T4qty + diffQty;
					itemmasterDao.updateSalesCategoryLooseStock(finalgrwt, finalgrwt, finalgrwt, finalQty, icodeR4);
					/****
					 * Stockupdation in JewelStock table for update Mode
					 * **/
					JewelStockCore.jewelStockCatLooseStockUpdate(jewelStockDao,
							"Sales", salesOld.getSalesTypeId(),
							salesNew.getSalesDate(), salesNew.getItemName3(),
							icodeR4, salesNew.getCategoryName3(),
							salesNew.getBullionType3(), salesNew.getMetalUsed3(),
							salesNew.getSoldQty3(),
							salesNew.getSoldQty3(), salesNew.getGrossWeight3(), salesNew.getGrossWeight3(),
							salesNew.getGrossWeight3(), salesNew.getStone3(),
							salesNew.getStoneRatePerCaret3(),
							salesNew.getStoneCost3(), salesNew.getBullionRate3(),
							salesNew.getValueAdditionCharges3(),
							salesNew.getLessPercentage3(),
							salesNew.getMcByAmount3(), salesNew.getMcByGrams3(),
							salesNew.getVtax3(), salesNew.getSalesHMCharges3());
				}
			}
		}

		/* Stock Update for Row 5 */
		String icodeR5 = null;
		icodeR5 = salesNew.getItemCode4();

		if (icodeR5 != null && icodeR5 != "") {
			if (!salesNew.getMetalUsed4().equalsIgnoreCase("Gold Loose Stock")	&& !salesNew.getMetalUsed4().equalsIgnoreCase("Silver Loose Stock")) 
			{
				List<ItemMaster> itemObj5 = itemmasterDao.getItemsByCode(icodeR5);
				BigDecimal T5grwt = itemObj5.get(0).getGrossWeight();
				BigDecimal diffgrwt5 = T5grwt.add(salesOld.getGrossWeight4());
				BigDecimal finalgrwt5 = diffgrwt5.subtract(salesNew.getGrossWeight4());

				if (finalgrwt5.signum() != -1) {
					itemmasterDao.updateSalesLooseStock(finalgrwt5, finalgrwt5,	finalgrwt5, icodeR5);
					/****
					 * Stockupdation in JewelStock table for update Mode
					 * **/
					JewelStockCore.jewelSUpdationForUpdate(jewelStockDao,
							"Sales", salesOld.getSalesTypeId(),
							salesNew.getSalesDate(), salesNew.getItemName(),
							icodeR5, salesNew.getCategoryName(),
							salesNew.getBullionType(),
							salesNew.getMetalUsed4(),
							salesNew.getNumberOfPieces(),
							salesNew.getTotalPieces(), finalgrwt5, finalgrwt5,
							finalgrwt5, salesNew.getStone(),
							salesNew.getStoneRatePerCaret(),
							salesNew.getStoneCost(), salesNew.getBullionRate(),
							salesNew.getValueAdditionCharges(),
							salesNew.getLessPercentage(),
							salesNew.getMcByAmount(), salesNew.getMcByGrams(),
							salesNew.getVtax(), salesNew.getSalesHMCharges());
				}
			} else {
				List<ItemMaster> itemObj5 = itemmasterDao.getItemsByCode(icodeR5);
				BigDecimal T5grwt = itemObj5.get(0).getGrossWeight();
				int T5qty = itemObj5.get(0).getQty();
				if (salesNew.getGrossWeight4().compareTo(
						salesOld.getGrossWeight4()) == 1) {
					/***
					 * if the updated value is greater then the inserted value .
					 ***/
					BigDecimal temp = salesOld.getGrossWeight4().subtract(salesNew.getGrossWeight4());
					BigDecimal finalgrwt = T5grwt.subtract(temp);
					int diffQty = salesOld.getSoldQty4() - salesNew.getSoldQty4();
					int finalQty = T5qty - diffQty;
					itemmasterDao.updateSalesCategoryLooseStock(finalgrwt, finalgrwt, finalgrwt, finalQty, icodeR5);
					/****
					 * Stockupdation in JewelStock table for update Mode
					 * **/
					JewelStockCore.jewelStockCatLooseStockUpdate(jewelStockDao,
							"Sales", salesOld.getSalesTypeId(),
							salesNew.getSalesDate(), salesNew.getItemName4(),
							icodeR5, salesNew.getCategoryName4(),
							salesNew.getBullionType4(), salesNew.getMetalUsed4(),
							salesNew.getSoldQty4(),
							salesNew.getSoldQty4(), salesNew.getGrossWeight4(), salesNew.getGrossWeight4(),
							salesNew.getGrossWeight4(), salesNew.getStone4(),
							salesNew.getStoneRatePerCaret4(),
							salesNew.getStoneCost4(), salesNew.getBullionRate4(),
							salesNew.getValueAdditionCharges4(),
							salesNew.getLessPercentage4(),
							salesNew.getMcByAmount4(), salesNew.getMcByGrams4(),
							salesNew.getVtax4(), salesNew.getSalesHMCharges4());
				} else {
					/***
					 * if the updated value is smaller then the inserted value .
					 ***/
					BigDecimal temp = salesOld.getGrossWeight4().subtract(salesNew.getGrossWeight4());
					BigDecimal finalgrwt = T5grwt.add(temp);
					int diffQty = salesOld.getSoldQty4() - salesNew.getSoldQty4();
					int finalQty = T5qty + diffQty;
					itemmasterDao.updateSalesCategoryLooseStock(finalgrwt, finalgrwt, finalgrwt, finalQty, icodeR5);
					/****
					 * Stockupdation in JewelStock table for update Mode
					 * **/
					JewelStockCore.jewelStockCatLooseStockUpdate(jewelStockDao,
							"Sales", salesOld.getSalesTypeId(),
							salesNew.getSalesDate(), salesNew.getItemName4(),
							icodeR5, salesNew.getCategoryName4(),
							salesNew.getBullionType4(), salesNew.getMetalUsed4(),
							salesNew.getSoldQty4(),
							salesNew.getSoldQty4(), salesNew.getGrossWeight4(), salesNew.getGrossWeight4(),
							salesNew.getGrossWeight4(), salesNew.getStone4(),
							salesNew.getStoneRatePerCaret4(),
							salesNew.getStoneCost4(), salesNew.getBullionRate4(),
							salesNew.getValueAdditionCharges4(),
							salesNew.getLessPercentage4(),
							salesNew.getMcByAmount4(), salesNew.getMcByGrams4(),
							salesNew.getVtax4(), salesNew.getSalesHMCharges4());
				}
			}
		}

		// ** Saving Scheme Updating Used Card No Status **/
		try {

			ArrayList<String> oldCardNoList = new ArrayList<String>();
			ArrayList<String> newCardNoList = new ArrayList<String>();

			if (salesOld.getSSCardNo() != null	&& !salesOld.getSSCardNo().isEmpty()) {
				String SelectedCardNo1[] = salesOld.getSSCardNo().split(",");
				for (int i = 0; i < SelectedCardNo1.length; i++) {
					oldCardNoList.add(SelectedCardNo1[i].trim());
				}
			}
			if (salesNew.getSSCardNo() != null	&& !salesNew.getSSCardNo().isEmpty()) {
				String SelectedCardNo2[] = salesNew.getSSCardNo().split(",");
				for (int i = 0; i < SelectedCardNo2.length; i++) {
					newCardNoList.add(SelectedCardNo2[i].trim());
				}
			}

			for (String newCard : newCardNoList) {
				if (oldCardNoList.contains(newCard)) {
					/** existing Update ***/
				} else {
					/*** added at update mode **/
					CardIssue CardIssueEntity = cardIssueDao.getCardIssueCardNO(newCard).get(0);
					CardIssueEntity.setStatus("Finished");
					cardIssueDao.updateCardIssue(CardIssueEntity);
				}
			}
			for (String oldCard : oldCardNoList) {
				if (!newCardNoList.contains(oldCard)) {
					/*** Removed at updated Mode **/
					// Old Card Status Update based on Scheme Duration //
					CardIssue OldcardObj = cardIssueDao.getCardIssueCardNO(oldCard).get(0);
					Integer OldCardInstallment = OldcardObj.getInstallment();
					if (OldCardInstallment == null) {
						OldCardInstallment = 0;
					}
					int oldInstallmentNo = OldCardInstallment;
					if (startSchemeDao.searchStartSchemeSSRP(OldcardObj.getSchemeName()).get(0).getSchemeDuration() == oldInstallmentNo) {
						OldcardObj.setStatus("Matured");
					} else {
						OldcardObj.setStatus("Active");
					}
					cardIssueDao.updateCardIssue(OldcardObj);
				}
			}

		} catch (IndexOutOfBoundsException ie) {
		}

		/* Temporary Variables for Calculation */
		BigDecimal ZERO = new BigDecimal("0");
		BigDecimal CONVERT = new BigDecimal("-1");
		String finalClType = "";

		// ************************************** Bill Amount : Sales Ledger
		// Update : Same Customer & Different Customer
		// *********************************************/
		if (salesOld.getSalesType().equalsIgnoreCase("Sales")) {
			if (salesOld.getBillAmount().compareTo(salesNew.getBillAmount()) != 0) {

				List<Ledger> ledgerListSales = ledgerDao.searchLedger("Sales Account");
				BigDecimal oldClBalanceSales = ledgerListSales.get(0).getClosingTotalBalance();
				String clTypeSales = ledgerListSales.get(0).getClosingTotalType();

				if (clTypeSales.equalsIgnoreCase("Credit")) {
					oldClBalanceSales = ZERO.subtract(oldClBalanceSales);
				}

				BigDecimal dropClBalanceSales = oldClBalanceSales.add(salesOld.getBillAmount());
				BigDecimal finalClBalanceSales = dropClBalanceSales.subtract(salesNew.getBillAmount());

				if (finalClBalanceSales.signum() == -1) {
					finalClType = "Credit";
					finalClBalanceSales = finalClBalanceSales.multiply(CONVERT);
				} else {
					finalClType = "Debit";
				}

				ledgerDao.updateLedgerPartyBalance(finalClBalanceSales, finalClType, "Sales Account");
			}
		}

		// ///////////////////////////// Bill Amount Party balance Update : if -
		// Same Customer Names ///////////////////////////
		if (salesOld.getCustomerName().equals(salesNew.getCustomerName())) {
			if (!salesOld.getCustomerName().equals("walk_in")) {

				// ***************** if Regular Party ledger
				// ************************//
				List<Ledger> ledgerListParty = ledgerDao.searchLedger(salesOld.getCustomerName());
				String clTypeParty = ledgerListParty.get(0).getClosingTotalType();
				BigDecimal oldClBalanceParty = ledgerListParty.get(0).getClosingTotalBalance();

				if (clTypeParty.equalsIgnoreCase("Credit")) {
					oldClBalanceParty = ZERO.subtract(oldClBalanceParty);
				}

				BigDecimal dropClBalanceParty = oldClBalanceParty.subtract(salesOld.getBillAmount());
				BigDecimal finalClBalanceParty = dropClBalanceParty.add(salesNew.getBillAmount());

				if (finalClBalanceParty.signum() == -1) {
					finalClType = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(CONVERT);
				} else {
					finalClType = "Debit";
				}

				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty, finalClType, salesOld.getCustomerName());

				if (salesOld.getSalesType().equalsIgnoreCase("Sales")) {

					// *** Same Customer - SAVING SCHEME Amount Party Balance
					// Update ***/
					// ** Closing balance = Current party balance + (Old savings
					// scheme Amount) - (New savings scheme Amount) ***/

					List<Ledger> SSledgerListParty = ledgerDao.searchLedger(salesOld.getCustomerName());
					String SSclTypeParty = SSledgerListParty.get(0).getClosingTotalType();
					BigDecimal SSClBalanceParty = SSledgerListParty.get(0).getClosingTotalBalance();

					String SSfinalClType = "";

					if (SSclTypeParty.equalsIgnoreCase("Credit")) {
						SSClBalanceParty = ZERO.subtract(SSClBalanceParty);
					}

					BigDecimal SSdropClBalanceParty = SSClBalanceParty.add(salesOld.getSSCardAmount());
					BigDecimal SSfinalClBalanceParty = SSdropClBalanceParty.subtract(salesNew.getSSCardAmount());

					if (SSfinalClBalanceParty.signum() == -1) {
						SSfinalClType = "Credit";
						SSfinalClBalanceParty = SSfinalClBalanceParty
								.multiply(CONVERT);
					} else {
						SSfinalClType = "Debit";
					}

					ledgerDao.updateLedgerPartyBalance(SSfinalClBalanceParty,
							SSfinalClType, salesOld.getCustomerName());

					// *********************************** Same Customer Journal
					// Entry Update for Sales Bill Amount
					// ***************************/
					List<Journal> jrnlListSales = journalDao
							.getJournalUpdateSales("Sales",
									"S" + salesOld.getSalesId());
					if (!jrnlListSales.isEmpty()) {
						jrnlListSales.get(0).setJournalDate(
								salesNew.getSalesDate());
						jrnlListSales.get(0).setDebitAmount(
								salesNew.getBillAmount());
						jrnlListSales.get(0).setCreditAmount(
								salesNew.getBillAmount());
						journalDao.updateJournal(jrnlListSales.get(0));
					}
				}
			}

		}// ********************* Party balance Update : if - different Customer
			// Names **************************
		else if (!salesNew.getCustomerName().equals("walk_in")) {

			/** From Party 1 */
			List<Ledger> ledgerListParty1 = ledgerDao.searchLedger(salesOld
					.getCustomerName());
			String clTypeParty1 = ledgerListParty1.get(0).getClosingTotalType();
			BigDecimal oldClBalanceParty1 = ledgerListParty1.get(0)
					.getClosingTotalBalance();

			String finalClTypeParty1 = "";
			String finalClTypeParty2 = "";

			/** To Party 2 */
			List<Ledger> ledgerListParty2 = ledgerDao.searchLedger(salesNew
					.getCustomerName());
			String clTypeParty2 = ledgerListParty2.get(0).getClosingTotalType();
			BigDecimal oldClBalanceParty2 = ledgerListParty2.get(0)
					.getClosingTotalBalance();

			if (clTypeParty1.equalsIgnoreCase("Credit")) {
				oldClBalanceParty1 = ZERO.subtract(oldClBalanceParty1);
			}
			if (clTypeParty2.equalsIgnoreCase("Credit")) {
				oldClBalanceParty2 = ZERO.subtract(oldClBalanceParty2);
			}

			BigDecimal finalClBalanceParty1 = oldClBalanceParty1
					.subtract(salesOld.getBillAmount());
			BigDecimal finalClBalanceParty2 = oldClBalanceParty2.add(salesNew
					.getBillAmount());

			if (finalClBalanceParty1.signum() == -1) {
				finalClTypeParty1 = "Credit";
				finalClBalanceParty1 = finalClBalanceParty1.multiply(CONVERT);
			} else {
				finalClTypeParty1 = "Debit";
			}

			if (finalClBalanceParty2.signum() == -1) {
				finalClTypeParty2 = "Credit";
				finalClBalanceParty2 = finalClBalanceParty2.multiply(CONVERT);
			} else {
				finalClTypeParty2 = "Debit";
			}

			ledgerDao.updateLedgerPartyBalance(finalClBalanceParty1,
					finalClTypeParty1, salesOld.getCustomerName());
			ledgerDao.updateLedgerPartyBalance(finalClBalanceParty2,
					finalClTypeParty2, salesNew.getCustomerName());

			if (salesOld.getSalesType().equalsIgnoreCase("Sales")) {

				// *** Different Customer - SAVING SCHEME Amount Party Balance
				// Update ***/
				// ** Old = Closing balance = Current party balance + (Old
				// savings scheme Amount)
				// ** New = Closing balance = Current party balance - (New
				// savings scheme Amount) ***/

				/** From Party 1 */
				List<Ledger> SSledgerListParty1 = ledgerDao
						.searchLedger(salesOld.getCustomerName());
				String SSclTypeParty1 = SSledgerListParty1.get(0)
						.getClosingTotalType();
				BigDecimal SSClBalanceParty1 = SSledgerListParty1.get(0)
						.getClosingTotalBalance();

				String SSfinalClType1 = "";
				String SSfinalClType2 = "";

				/** To Party 2 */
				List<Ledger> SSledgerListParty2 = ledgerDao
						.searchLedger(salesNew.getCustomerName());
				String SSclTypeParty2 = SSledgerListParty2.get(0)
						.getClosingTotalType();
				BigDecimal SSClBalanceParty2 = SSledgerListParty2.get(0)
						.getClosingTotalBalance();

				if (SSclTypeParty1.equalsIgnoreCase("Credit")) {
					SSClBalanceParty1 = ZERO.subtract(SSClBalanceParty1);
				}
				if (SSclTypeParty2.equalsIgnoreCase("Credit")) {
					SSClBalanceParty2 = ZERO.subtract(SSClBalanceParty2);
				}

				BigDecimal SSfinalClBalanceParty1 = SSClBalanceParty1
						.add(salesOld.getSSCardAmount());
				BigDecimal SSfinalClBalanceParty2 = SSClBalanceParty2
						.subtract(salesNew.getSSCardAmount());

				if (SSfinalClBalanceParty1.signum() == -1) {
					SSfinalClType1 = "Credit";
					SSfinalClBalanceParty1 = SSfinalClBalanceParty1
							.multiply(CONVERT);
				} else {
					SSfinalClType1 = "Debit";
				}
				if (SSfinalClBalanceParty2.signum() == -1) {
					SSfinalClType2 = "Credit";
					SSfinalClBalanceParty2 = SSfinalClBalanceParty2
							.multiply(CONVERT);
				} else {
					SSfinalClType2 = "Debit";
				}

				ledgerDao.updateLedgerPartyBalance(SSfinalClBalanceParty1,
						SSfinalClType1, salesOld.getCustomerName());
				ledgerDao.updateLedgerPartyBalance(SSfinalClBalanceParty2,
						SSfinalClType2, salesNew.getCustomerName());

				// *********************************** Different Customer
				// Journal Entry Update for Sales Bill Amount
				// ***************************/
				List<Journal> jrnlListSales = journalDao.getJournalUpdateSales(
						"Sales", "S" + salesOld.getSalesId());
				if (!jrnlListSales.isEmpty()) {
					jrnlListSales.get(0)
							.setJournalDate(salesNew.getSalesDate());
					jrnlListSales.get(0).setDebitAccountName(
							salesNew.getCustomerName());
					jrnlListSales.get(0).setDebitAmount(
							salesNew.getBillAmount());
					jrnlListSales.get(0).setCreditAmount(
							salesNew.getBillAmount());
					journalDao.updateJournal(jrnlListSales.get(0));
				}
			}
		}

		// ////////////////////////// Receipt Mode - Cash Ledgers Update
		// /////////////////////////////////////////
		if (!salesOld.getCustomerName().equals(salesNew.getCustomerName())
				&& !salesNew.getCustomerName().equals("walk_in")) {

			// ********************* Cash Party balance Update : if - different
			// Customer Names **************************
			/** From Party 1 */
			List<Ledger> ledgerListCashParty1 = ledgerDao.searchLedger(salesOld
					.getCustomerName());
			String clTypeCashParty1 = ledgerListCashParty1.get(0)
					.getClosingTotalType();
			BigDecimal oldClBalanceCashParty1 = ledgerListCashParty1.get(0)
					.getClosingTotalBalance();

			String finalClTypeParty1 = "";
			String finalClTypeParty2 = "";

			/** To Party 2 */
			List<Ledger> ledgerListCashParty2 = ledgerDao.searchLedger(salesNew
					.getCustomerName());
			String clTypeCashParty2 = ledgerListCashParty2.get(0)
					.getClosingTotalType();
			BigDecimal oldClBalanceCashParty2 = ledgerListCashParty2.get(0)
					.getClosingTotalBalance();

			if (clTypeCashParty1.equalsIgnoreCase("Credit")) {
				oldClBalanceCashParty1 = ZERO.subtract(oldClBalanceCashParty1);
			}
			if (clTypeCashParty2.equalsIgnoreCase("Credit")) {
				oldClBalanceCashParty2 = ZERO.subtract(oldClBalanceCashParty2);
			}

			BigDecimal finalClBalanceCashParty1 = oldClBalanceCashParty1
					.add(salesOld.getCashAmount());
			BigDecimal finalClBalanceCashParty2 = oldClBalanceCashParty2
					.subtract(salesNew.getCashAmount());

			if (finalClBalanceCashParty1.signum() == -1) {
				finalClTypeParty1 = "Credit";
				finalClBalanceCashParty1 = finalClBalanceCashParty1
						.multiply(CONVERT);
			} else {
				finalClTypeParty1 = "Debit";
			}

			if (finalClBalanceCashParty2.signum() == -1) {
				finalClTypeParty2 = "Credit";
				finalClBalanceCashParty2 = finalClBalanceCashParty2
						.multiply(CONVERT);
			} else {
				finalClTypeParty2 = "Debit";
			}

			ledgerDao.updateLedgerPartyBalance(finalClBalanceCashParty1,
					finalClTypeParty1, salesOld.getCustomerName());
			ledgerDao.updateLedgerPartyBalance(finalClBalanceCashParty2,
					finalClTypeParty2, salesNew.getCustomerName());
		} else if (!salesNew.getCustomerName().equals("walk_in")) {
			// ********************* Cash Party balance Update : if - Same
			// Customer Names **************************

			List<Ledger> ledgerListCashParty = ledgerDao.searchLedger(salesOld
					.getCustomerName());
			BigDecimal oldClBalanceCashParty = ledgerListCashParty.get(0)
					.getClosingTotalBalance();
			String clTypeCashParty = ledgerListCashParty.get(0)
					.getClosingTotalType();

			if (clTypeCashParty.equalsIgnoreCase("Credit")) {
				oldClBalanceCashParty = ZERO.subtract(oldClBalanceCashParty);
			}

			BigDecimal dropClBalanceCashParty = oldClBalanceCashParty
					.add(salesOld.getCashAmount());
			BigDecimal finalClBalanceCashParty = dropClBalanceCashParty
					.subtract(salesNew.getCashAmount());

			if (finalClBalanceCashParty.signum() == -1) {
				finalClType = "Credit";
				finalClBalanceCashParty = finalClBalanceCashParty
						.multiply(CONVERT);
			} else {
				finalClType = "Debit";
			}
			ledgerDao.updateLedgerPartyBalance(finalClBalanceCashParty,
					finalClType, salesOld.getCustomerName());
		}
		if (salesOld.getSalesType().equalsIgnoreCase("Sales")) {

			/* Cash2.Cash Ledger Update */
			List<Ledger> ledgerListCashLedger = ledgerDao
					.searchLedger("Cash Account");
			BigDecimal oldClBalanceCashLedger = ledgerListCashLedger.get(0)
					.getClosingTotalBalance();
			String clTypeCashLedger = ledgerListCashLedger.get(0)
					.getClosingTotalType();

			if (clTypeCashLedger.equalsIgnoreCase("Credit")) {
				oldClBalanceCashLedger = ZERO.subtract(oldClBalanceCashLedger);
			}

			BigDecimal dropClBalanceCashLedger = oldClBalanceCashLedger
					.subtract(salesOld.getCashAmount());
			BigDecimal finalClBalanceCashLedger = dropClBalanceCashLedger
					.add(salesNew.getCashAmount());

			if (finalClBalanceCashLedger.signum() == -1) {
				finalClType = "Credit";
				finalClBalanceCashLedger = finalClBalanceCashLedger
						.multiply(CONVERT);
			} else {
				finalClType = "Debit";
			}
			ledgerDao.updateLedgerPartyBalance(finalClBalanceCashLedger,
					finalClType, "Cash Account");

			/* Cash3.Journal Entry Update for Cash */
			List<Journal> OldjrnlListCash = journalDao.getJournalUpdateSales(
					"Sales Cash Receipt", "S" + salesOld.getSalesId());

			if (!OldjrnlListCash.isEmpty()) {
				if (salesOld.getCashAmount().signum() == 1
						&& salesNew.getCashAmount().signum() == 0) {
					journalDao.deleteJournal(OldjrnlListCash.get(0));
					/*** Delete Entry ***/
				} else if (salesOld.getCashAmount().signum() == 1
						&& salesNew.getCashAmount().signum() == 1) {
					OldjrnlListCash.get(0).setJournalDate(
							salesNew.getSalesDate());
					OldjrnlListCash.get(0).setCreditAccountName(
							salesNew.getCustomerName());
					OldjrnlListCash.get(0).setDebitAmount(
							salesNew.getCashAmount());
					OldjrnlListCash.get(0).setCreditAmount(
							salesNew.getCashAmount());
					journalDao.updateJournal(OldjrnlListCash.get(0));
				}
			} else if (OldjrnlListCash.isEmpty()
					&& salesNew.getCashAmount().signum() == 1) {
				jrnl = new Journal();
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Sales Cash Receipt");
				jrnl.setTransactionId("S" + salesNew.getSalesId()); // New
																	// Column
																	// added for
																	// tracking
																	// journal
																	// entries
				jrnl.setJournalDate(salesNew.getSalesDate());

				// Set Account group code as Debit code
				ledgerGroupCode = ledgerDao
						.getLedgerAccountCode("Cash Account");
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
				jrnl.setDebitAccountName("Cash Account");

				// Set Account group code as Credit code
				if (salesNew.getCustomerName().equalsIgnoreCase("walk_in")) {
					ledgerGroupCode = ledgerDao
							.getLedgerAccountCode("Sales Account");
					jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					jrnl.setCreditAccountName("Sales Account");
				} else {
					ledgerGroupCode = ledgerDao.getLedgerAccountCode(salesNew
							.getCustomerName());
					jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					jrnl.setCreditAccountName(salesNew.getCustomerName());
				}

				jrnl.setDebitAmount(salesNew.getCashAmount());
				jrnl.setCreditAmount(salesNew.getCashAmount());
				jrnl.setNarration("Bill No " + salesOld.getSalesTypeId());
				journalDao.insertJournal((Journal) jrnl);
			}
		}

		if (salesOld.getSalesType().equalsIgnoreCase("Sales")) {
			// //////////////////////////////// Receipt Mode - Cheque Ledgers &
			// Journal Update //////////////////////////////////

			if (!salesOld.getCustomerName().equals(salesNew.getCustomerName())
					&& !salesNew.getCustomerName().equals("walk_in")) {
				// ********************* Cheque Party balance Update : if -
				// different Customer Names ************************//

				/** From Party 1 */
				List<Ledger> ledgerListChequeParty1 = ledgerDao
						.searchLedger(salesOld.getCustomerName());
				String clTypeChequeParty1 = ledgerListChequeParty1.get(0)
						.getClosingTotalType();
				BigDecimal oldClBalanceChequeParty1 = ledgerListChequeParty1
						.get(0).getClosingTotalBalance();
				String finalClTypeParty1 = "";
				String finalClTypeParty2 = "";

				/** To Party 2 */
				List<Ledger> ledgerListChequeParty2 = ledgerDao
						.searchLedger(salesNew.getCustomerName());
				String clTypeChequeParty2 = ledgerListChequeParty2.get(0)
						.getClosingTotalType();
				BigDecimal oldClBalanceChequeParty2 = ledgerListChequeParty2
						.get(0).getClosingTotalBalance();

				if (clTypeChequeParty1.equalsIgnoreCase("Credit")) {
					oldClBalanceChequeParty1 = ZERO
							.subtract(oldClBalanceChequeParty1);
				}
				if (clTypeChequeParty2.equalsIgnoreCase("Credit")) {
					oldClBalanceChequeParty2 = ZERO
							.subtract(oldClBalanceChequeParty2);
				}

				BigDecimal finalClBalanceChequeParty1 = oldClBalanceChequeParty1
						.add(salesOld.getChequeAmount());
				BigDecimal finalClBalanceChequeParty2 = oldClBalanceChequeParty2
						.subtract(salesNew.getChequeAmount());

				if (finalClBalanceChequeParty1.signum() == -1) {
					finalClTypeParty1 = "Credit";
					finalClBalanceChequeParty1 = finalClBalanceChequeParty1
							.multiply(CONVERT);
				} else {
					finalClTypeParty1 = "Debit";
				}

				if (finalClBalanceChequeParty2.signum() == -1) {
					finalClTypeParty2 = "Credit";
					finalClBalanceChequeParty2 = finalClBalanceChequeParty2
							.multiply(CONVERT);
				} else {
					finalClTypeParty2 = "Debit";
				}

				ledgerDao.updateLedgerPartyBalance(finalClBalanceChequeParty1,
						finalClTypeParty1, salesOld.getCustomerName());
				ledgerDao.updateLedgerPartyBalance(finalClBalanceChequeParty2,
						finalClTypeParty2, salesNew.getCustomerName());
			} else if (!salesNew.getCustomerName().equals("walk_in")) {
				// ********************* Cheque Party balance Update : if - Same
				// Customer Names **************************

				List<Ledger> ledgerListChequeParty = ledgerDao
						.searchLedger(salesOld.getCustomerName());
				BigDecimal oldClBalanceChequeParty = ledgerListChequeParty.get(
						0).getClosingTotalBalance();
				String clTypeChequeParty = ledgerListChequeParty.get(0)
						.getClosingTotalType();

				if (clTypeChequeParty.equalsIgnoreCase("Credit")) {
					oldClBalanceChequeParty = ZERO
							.subtract(oldClBalanceChequeParty);
				}

				BigDecimal dropClBalanceChequeParty = oldClBalanceChequeParty
						.add(salesOld.getChequeAmount());
				BigDecimal finalClBalanceChequeParty = dropClBalanceChequeParty
						.subtract(salesNew.getChequeAmount());

				if (finalClBalanceChequeParty.signum() == -1) {
					finalClType = "Credit";
					finalClBalanceChequeParty = finalClBalanceChequeParty
							.multiply(CONVERT);
				} else {
					finalClType = "Debit";
				}
				ledgerDao.updateLedgerPartyBalance(finalClBalanceChequeParty,
						finalClType, salesOld.getCustomerName());
			}
			// ////////////////////////////////// C2.Cheque Bank Ledger Update
			// ////////////////////////////////////////////
			List<Ledger> ledgerListChequeLedger = ledgerDao
					.searchLedger(salesOld.getChequeBank());

			if (!ledgerListChequeLedger.isEmpty()) { // if initially cheque
														// selected

				BigDecimal oldClBalanceChequeLedger = ledgerListChequeLedger
						.get(0).getClosingTotalBalance();
				String clTypeChequeLedger = ledgerListChequeLedger.get(0)
						.getClosingTotalType();

				if (clTypeChequeLedger.equalsIgnoreCase("Credit")) {
					oldClBalanceChequeLedger = ZERO
							.subtract(oldClBalanceChequeLedger);
				}

				if (salesOld.getChequeBank().equals(salesNew.getChequeBank())) {
					// ********************************* If the Bank A/c is
					// "Same".******************************************/
					BigDecimal dropClBalanceChequeLedger = oldClBalanceChequeLedger
							.subtract(salesOld.getChequeAmount());
					BigDecimal finalClBalanceChequeLedger = dropClBalanceChequeLedger
							.add(salesNew.getChequeAmount());

					if (finalClBalanceChequeLedger.signum() == -1) {
						finalClType = "Credit";
						finalClBalanceChequeLedger = finalClBalanceChequeLedger
								.multiply(CONVERT);
					} else {
						finalClType = "Debit";
					}
					ledgerDao.updateLedgerPartyBalance(
							finalClBalanceChequeLedger, finalClType,
							salesOld.getChequeBank());
				} else {
					// ************************ If the Bank A/c Name
					// "Changes".******************************************/
					/* B1. For old Bank A/c */// Subtract old amount value in
												// old Ledger

					BigDecimal finalOldBankLedgerAmt = oldClBalanceChequeLedger
							.subtract(salesOld.getChequeAmount());
					if (finalOldBankLedgerAmt.signum() == -1) {
						finalClType = "Credit";
						finalOldBankLedgerAmt = finalOldBankLedgerAmt
								.multiply(CONVERT);
					} else {
						finalClType = "Debit";
					}
					ledgerDao.updateLedgerPartyBalance(finalOldBankLedgerAmt,
							finalClType, salesOld.getChequeBank());

					/* B2. For New Bank A/c */// Add old amount value in old
												// Ledger

					List<Ledger> ledgerNewBankLedger = ledgerDao
							.searchLedger(salesNew.getChequeBank());
					BigDecimal NewBankLedgerAmt = ledgerNewBankLedger.get(0)
							.getClosingTotalBalance();
					String clNewBankLedgerType = ledgerNewBankLedger.get(0)
							.getClosingTotalType();

					if (clNewBankLedgerType.equalsIgnoreCase("Credit")) {
						NewBankLedgerAmt = ZERO.subtract(NewBankLedgerAmt);
					}

					BigDecimal finalNewBankLedgerAmt = NewBankLedgerAmt
							.add(salesNew.getChequeAmount());

					if (finalNewBankLedgerAmt.signum() == -1) {
						finalClType = "Credit";
						finalNewBankLedgerAmt = finalNewBankLedgerAmt
								.multiply(CONVERT);
					} else {
						finalClType = "Debit";
					}

					ledgerDao.updateLedgerPartyBalance(finalNewBankLedgerAmt,
							finalClType, salesNew.getChequeBank());
				}
			} else if (salesOld.getChequeAmount().signum() == 0
					&& salesNew.getChequeAmount().signum() == 1) {
				// on Update added Cheque option
				List<Ledger> ledgerNewBankLedger = ledgerDao
						.searchLedger(salesNew.getChequeBank());
				BigDecimal NewBankLedgerAmt = ledgerNewBankLedger.get(0)
						.getClosingTotalBalance();
				String clNewBankLedgerType = ledgerNewBankLedger.get(0)
						.getClosingTotalType();

				if (clNewBankLedgerType.equalsIgnoreCase("Credit")) {
					NewBankLedgerAmt = ZERO.subtract(NewBankLedgerAmt);
				}

				BigDecimal finalNewBankLedgerAmt = NewBankLedgerAmt
						.add(salesNew.getChequeAmount());

				if (finalNewBankLedgerAmt.signum() == -1) {
					finalClType = "Credit";
					finalNewBankLedgerAmt = finalNewBankLedgerAmt
							.multiply(CONVERT);
				} else {
					finalClType = "Debit";
				}

				ledgerDao.updateLedgerPartyBalance(finalNewBankLedgerAmt,
						finalClType, salesNew.getChequeBank());
			}
			// /////////////////////////////////// Journal Entries
			// ////////////////////////////////////////////
			/* C3.Journal Entry Update for Cheque */
			List<Journal> OldjrnlListCheque = journalDao.getJournalUpdateSales(
					"Sales Cheque Receipt", "S" + salesOld.getSalesId());

			if (!OldjrnlListCheque.isEmpty()) {
				if (salesOld.getChequeAmount().signum() == 1
						&& salesNew.getChequeAmount().signum() == 0) {
					journalDao.deleteJournal(OldjrnlListCheque.get(0));
					/*** Delete Entry ***/
				} else if (salesNew.getChequeAmount().signum() == 1
						&& salesOld.getChequeAmount().signum() == 1) {
					OldjrnlListCheque.get(0).setJournalDate(
							salesNew.getSalesDate());
					OldjrnlListCheque.get(0).setCreditAccountName(
							salesNew.getCustomerName());
					OldjrnlListCheque.get(0).setDebitAccountName(
							salesNew.getChequeBank());
					OldjrnlListCheque.get(0).setDebitAmount(
							salesNew.getChequeAmount());
					OldjrnlListCheque.get(0).setCreditAmount(
							salesNew.getChequeAmount());
					journalDao.updateJournal(OldjrnlListCheque.get(0));
				}
			} else if (OldjrnlListCheque.isEmpty()
					&& salesNew.getChequeAmount().signum() == 1) { // on Update
																	// checked
																	// cheque
																	// option
				jrnl = new Journal();
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Sales Cheque Receipt");
				jrnl.setTransactionId("S" + salesNew.getSalesId()); // New
																	// Column
																	// added for
																	// tracking
																	// journal
																	// entries
				jrnl.setJournalDate(salesNew.getSalesDate());

				// Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(salesNew
						.getChequeBank());
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
				jrnl.setDebitAccountName(salesNew.getChequeBank());

				// Set Account group code as Credit code
				if (salesNew.getCustomerName().equalsIgnoreCase("walk_in")) {
					ledgerGroupCode = ledgerDao
							.getLedgerAccountCode("Sales Account");
					jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					jrnl.setCreditAccountName("Sales Account");
				} else {
					ledgerGroupCode = ledgerDao.getLedgerAccountCode(salesNew
							.getCustomerName());
					jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					jrnl.setCreditAccountName(salesNew.getCustomerName());
				}

				jrnl.setDebitAmount(salesNew.getChequeAmount());
				jrnl.setCreditAmount(salesNew.getChequeAmount());
				jrnl.setNarration("Bill No " + salesOld.getSalesTypeId());
				journalDao.insertJournal((Journal) jrnl);
			}

			// //////////////////////////////////// Receipt Mode - Card Ledgers
			// & Journal Update ///////////////////////////////////

			if (!salesOld.getCustomerName().equals(salesNew.getCustomerName())
					&& !salesNew.getCustomerName().equals("walk_in")) {

				// ********************* Card Party balance Update : if -
				// different Customer Names **************************

				/** From Party 1 */
				List<Ledger> ledgerListCardParty1 = ledgerDao
						.searchLedger(salesOld.getCustomerName());
				String clTypeCardParty1 = ledgerListCardParty1.get(0)
						.getClosingTotalType();
				BigDecimal oldClBalanceCardParty1 = ledgerListCardParty1.get(0)
						.getClosingTotalBalance();
				String finalClTypeParty1 = "";
				String finalClTypeParty2 = "";

				/** To Party 2 */
				List<Ledger> ledgerListCardParty2 = ledgerDao
						.searchLedger(salesNew.getCustomerName());
				String clTypeCardParty2 = ledgerListCardParty2.get(0)
						.getClosingTotalType();
				BigDecimal oldClBalanceCardParty2 = ledgerListCardParty2.get(0)
						.getClosingTotalBalance();

				if (clTypeCardParty1.equalsIgnoreCase("Credit")) {
					oldClBalanceCardParty1 = ZERO
							.subtract(oldClBalanceCardParty1);
				}
				if (clTypeCardParty2.equalsIgnoreCase("Credit")) {
					oldClBalanceCardParty2 = ZERO
							.subtract(oldClBalanceCardParty2);
				}

				BigDecimal finalClBalanceCardParty1 = oldClBalanceCardParty1
						.add(salesOld.getCardAmount());
				BigDecimal finalClBalanceCardParty2 = oldClBalanceCardParty2
						.subtract(salesNew.getCardAmount());

				if (finalClBalanceCardParty1.signum() == -1) {
					finalClTypeParty1 = "Credit";
					finalClBalanceCardParty1 = finalClBalanceCardParty1
							.multiply(CONVERT);
				} else {
					finalClTypeParty1 = "Debit";
				}

				if (finalClBalanceCardParty2.signum() == -1) {
					finalClTypeParty2 = "Credit";
					finalClBalanceCardParty2 = finalClBalanceCardParty2
							.multiply(CONVERT);
				} else {
					finalClTypeParty2 = "Debit";
				}

				ledgerDao.updateLedgerPartyBalance(finalClBalanceCardParty1,
						finalClTypeParty1, salesOld.getCustomerName());
				ledgerDao.updateLedgerPartyBalance(finalClBalanceCardParty2,
						finalClTypeParty2, salesNew.getCustomerName());

				// Commission Party Ledger Updation for different customer

				// if(salesOld.getCommissionAmount()!=null &&
				// salesOld.getCommissionAmount().signum()>0){

				/** From Party 1 */
				List<Ledger> ledgerCardCommissionParty1 = ledgerDao
						.searchLedger(salesOld.getCustomerName());
				String clTypeCardcommParty1 = ledgerCardCommissionParty1.get(0)
						.getClosingTotalType();
				BigDecimal oldClBalanceCardcommParty1 = ledgerCardCommissionParty1
						.get(0).getClosingTotalBalance();
				String finalClTypeCommParty1 = "";
				String finalClTypecommParty2 = "";

				/** To Party 2 */
				List<Ledger> ledgerCardCommissionParty2 = ledgerDao
						.searchLedger(salesNew.getCustomerName());
				String clTypeCardcommParty2 = ledgerCardCommissionParty2.get(0)
						.getClosingTotalType();
				BigDecimal oldClBalanceCardcommParty2 = ledgerCardCommissionParty2
						.get(0).getClosingTotalBalance();

				if (clTypeCardcommParty1.equalsIgnoreCase("Credit")) {
					oldClBalanceCardcommParty1 = ZERO
							.subtract(oldClBalanceCardcommParty1);
				}
				if (clTypeCardcommParty2.equalsIgnoreCase("Credit")) {
					oldClBalanceCardcommParty2 = ZERO
							.subtract(oldClBalanceCardcommParty2);
				}

				BigDecimal finalClBalanceCardcommParty1 = oldClBalanceCardcommParty1
						.subtract(salesOld.getCommissionAmount());
				BigDecimal finalClBalanceCardcommParty2 = oldClBalanceCardcommParty2
						.add(salesNew.getCommissionAmount());

				if (finalClBalanceCardcommParty1.signum() == -1) {
					finalClTypeCommParty1 = "Credit";
					finalClBalanceCardcommParty1 = finalClBalanceCardcommParty1
							.multiply(CONVERT);
				} else {
					finalClTypeCommParty1 = "Debit";
				}

				if (finalClBalanceCardcommParty2.signum() == -1) {
					finalClTypecommParty2 = "Credit";
					finalClBalanceCardcommParty2 = finalClBalanceCardcommParty2
							.multiply(CONVERT);
				} else {
					finalClTypecommParty2 = "Debit";
				}

				ledgerDao.updateLedgerPartyBalance(
						finalClBalanceCardcommParty1, finalClTypeCommParty1,
						salesOld.getCustomerName());
				ledgerDao.updateLedgerPartyBalance(
						finalClBalanceCardcommParty2, finalClTypecommParty2,
						salesNew.getCustomerName());

				// }

			} else if (!salesNew.getCustomerName().equals("walk_in")) {

				// ********************* Card Party balance Update : if - Same
				// Customer Names **************************
				// if(salesOld.getCalCardAmount().compareTo(salesNew.getCalCardAmount())==-1){
				List<Ledger> ledgerListCardParty = ledgerDao
						.searchLedger(salesOld.getCustomerName());
				BigDecimal oldClBalanceCardParty = ledgerListCardParty.get(0)
						.getClosingTotalBalance();
				String clTypeCardParty = ledgerListCardParty.get(0)
						.getClosingTotalType();

				if (clTypeCardParty.equalsIgnoreCase("Credit")) {
					oldClBalanceCardParty = ZERO
							.subtract(oldClBalanceCardParty);
				}

				BigDecimal dropClBalanceCardParty = oldClBalanceCardParty
						.add(salesOld.getCardAmount());
				BigDecimal finalClBalanceCardParty = dropClBalanceCardParty
						.subtract(salesNew.getCardAmount());

				if (finalClBalanceCardParty.signum() == -1) {
					finalClType = "Credit";
					finalClBalanceCardParty = finalClBalanceCardParty
							.multiply(CONVERT);
				} else {
					finalClType = "Debit";
				}
				ledgerDao.updateLedgerPartyBalance(finalClBalanceCardParty,
						finalClType, salesOld.getCustomerName());

				// }
				// if(salesOld.getCommissionAmount().signum()!=0 &&
				// salesNew.getCommissionAmount().signum()==1)
				// {

				// update party balance for commission amount
				List<Ledger> ledger = ledgerDao.searchLedger(salesOld
						.getCustomerName());
				String typeSales = ledger.get(0).getClosingTotalType();
				BigDecimal oldPartyBal = ledger.get(0).getClosingTotalBalance();

				if (typeSales.equals("Credit")) {
					oldPartyBal = ZERO.subtract(oldPartyBal);
				}

				BigDecimal tempcardPartyBal = oldPartyBal.subtract(salesOld
						.getCommissionAmount());
				BigDecimal finalClcardPartyBal = tempcardPartyBal.add(salesNew
						.getCommissionAmount());

				if (finalClcardPartyBal.signum() == -1) {
					finalClType = "Credit";
					finalClcardPartyBal = finalClcardPartyBal.multiply(CONVERT);
				} else {
					finalClType = "Debit";
				}
				ledgerDao.updateLedgerPartyBalance(finalClcardPartyBal,
						finalClType, salesOld.getCustomerName());

				// }

			}

			// Card Commission Bank Ledger Update same customer
			// /////////////////////////////
			List<Ledger> ledgerListCardComLedger = ledgerDao
					.searchLedger("Card Commission from customer");
			BigDecimal oldClBalanceCardComLedger = ledgerListCardComLedger.get(
					0).getClosingTotalBalance();
			String clTypeCardComLedger = ledgerListCardComLedger.get(0)
					.getClosingTotalType();

			if (clTypeCardComLedger.equalsIgnoreCase("Credit")) {
				oldClBalanceCardComLedger = ZERO
						.subtract(oldClBalanceCardComLedger);
			}

			BigDecimal tempcommledgbal = oldClBalanceCardComLedger.add(salesOld
					.getCommissionAmount());
			BigDecimal finalClBalanceCardComLedger = tempcommledgbal
					.subtract(salesNew.getCommissionAmount());
			// System.out.println("old bank commission amount::"+oldClBalanceCardComLedger);
			// System.out.println("new  commission amount::"+salesNew.getCommissionAmount());
			// System.out.println("final bank commission amount::"+finalClBalanceCardComLedger);
			if (finalClBalanceCardComLedger.signum() == -1) {
				finalClType = "Credit";
				finalClBalanceCardComLedger = finalClBalanceCardComLedger
						.multiply(CONVERT);
			} else {
				finalClType = "Debit";
			}
			ledgerDao.updateLedgerPartyBalance(finalClBalanceCardComLedger,
					finalClType, "Card Commission from customer");

			List<Journal> OldjrnlListCard = journalDao.getJournalUpdateSales(
					"S.Journal", "S" + salesOld.getSalesId());

			if (!OldjrnlListCard.isEmpty()) {
				if (salesOld.getCommissionAmount().signum() == 1
						&& salesNew.getCommissionAmount().signum() == 0) {
					journalDao.deleteJournal(OldjrnlListCard.get(0));
					/*** Delete Entry ***/
				} else if (salesOld.getCommissionAmount().signum() == 1
						&& salesNew.getCommissionAmount().signum() == 1) {
					OldjrnlListCard.get(0).setJournalDate(
							salesNew.getSalesDate());
					OldjrnlListCard.get(0).setCreditAccountName(
							"Card Commission from customer");
					OldjrnlListCard.get(0).setDebitAmount(
							salesNew.getCommissionAmount());
					OldjrnlListCard.get(0).setCreditAmount(
							salesNew.getCommissionAmount());
					journalDao.updateJournal(OldjrnlListCard.get(0));
				}
			} else if (OldjrnlListCard.isEmpty()
					&& salesNew.getCommissionAmount().signum() == 1) {
				jrnl = new Journal();
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("S.Journal");
				jrnl.setTransactionId("S" + salesNew.getSalesId()); // New
																	// Column
																	// added for
																	// tracking
																	// journal
																	// entries
				jrnl.setJournalDate(salesNew.getSalesDate());

				// Set Account group code as Debit code
				if (salesNew.getCustomerName().equalsIgnoreCase("walk_in")) {
					ledgerGroupCode = ledgerDao
							.getLedgerAccountCode("Sales Account");
					jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					jrnl.setCreditAccountName("Sales Account");
				} else {
					ledgerGroupCode = ledgerDao.getLedgerAccountCode(salesNew
							.getCustomerName());
					jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
					jrnl.setDebitAccountName(salesNew.getCustomerName());
				}
				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao
						.getLedgerAccountCode("Card Commission from customer");
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName("Card Commission from customer");

				jrnl.setDebitAmount(salesNew.getCommissionAmount());
				jrnl.setCreditAmount(salesNew.getCommissionAmount());
				jrnl.setNarration("Bill No " + salesOld.getSalesTypeId());
				journalDao.insertJournal((Journal) jrnl);
			}

			// ////////////////////////////* Card2.Card Bank Ledger Update
			// *///////////////////////////////////////
			List<Ledger> ledgerListCardLedger = ledgerDao.searchLedger(salesOld
					.getCardBank());

			if (!ledgerListCardLedger.isEmpty()) { // initially card selected
				BigDecimal oldClBalanceCardLedger = ledgerListCardLedger.get(0)
						.getClosingTotalBalance();
				String clTypeCardLedger = ledgerListCardLedger.get(0)
						.getClosingTotalType();

				if (clTypeCardLedger.equalsIgnoreCase("Credit")) {
					oldClBalanceCardLedger = ZERO
							.subtract(oldClBalanceCardLedger);
				}

				if (salesOld.getCardBank().equals(salesNew.getCardBank())) {// **************************************
																			// If
																			// the
																			// Bank
																			// A/c
																			// is
																			// Same.
																			// *******************************/
					BigDecimal dropClBalanceCardLedger = oldClBalanceCardLedger
							.subtract(salesOld.getCardAmount());
					BigDecimal finalClBalanceCardLedger = dropClBalanceCardLedger
							.add(salesNew.getCardAmount());

					if (finalClBalanceCardLedger.signum() == -1) {
						finalClType = "Credit";
						finalClBalanceCardLedger = finalClBalanceCardLedger
								.multiply(CONVERT);
					} else {
						finalClType = "Debit";
					}
					ledgerDao.updateLedgerPartyBalance(
							finalClBalanceCardLedger, finalClType,
							salesOld.getCardBank());

				} else { // ************************** B0. If the Bank A/c Name
							// changes for Card
							// .*********************************/
					/* B1. For old Bank A/c for Card */// Subtract old card
														// amount in old ledger

					BigDecimal finalOldBankLedgerCardAmt = oldClBalanceCardLedger
							.subtract(salesOld.getCardAmount());
					if (finalOldBankLedgerCardAmt.signum() == -1) {
						finalClType = "Credit";
						finalOldBankLedgerCardAmt = finalOldBankLedgerCardAmt
								.multiply(CONVERT);
					} else {
						finalClType = "Debit";
					}
					ledgerDao.updateLedgerPartyBalance(
							finalOldBankLedgerCardAmt, finalClType,
							salesOld.getCardBank());

					/* B2. For New Bank A/c for Card */// Add new amount value
														// in ledger
					List<Ledger> ledgerNewBankCardLedger = ledgerDao
							.searchLedger(salesNew.getCardBank());
					BigDecimal NewBankLedgerCardAmt = ledgerNewBankCardLedger
							.get(0).getClosingTotalBalance();
					String clNewBankLedgerCardType = ledgerNewBankCardLedger
							.get(0).getClosingTotalType();

					if (clNewBankLedgerCardType.equalsIgnoreCase("Credit")) {
						NewBankLedgerCardAmt = ZERO
								.subtract(NewBankLedgerCardAmt);
					}

					BigDecimal finalNewBankLedgerAmt = NewBankLedgerCardAmt
							.add(salesNew.getCardAmount());

					if (finalNewBankLedgerAmt.signum() == -1) {
						finalClType = "Credit";
						finalNewBankLedgerAmt = finalNewBankLedgerAmt
								.multiply(CONVERT);
					} else {
						finalClType = "Debit";
					}
					ledgerDao.updateLedgerPartyBalance(finalNewBankLedgerAmt,
							finalClType, salesNew.getCardBank());
				}

			} else if (salesOld.getCardAmount().signum() == 0
					&& salesNew.getCardAmount().signum() == 1) {
				// On update added card option
				List<Ledger> ledgerNewBankCardLedger = ledgerDao
						.searchLedger(salesNew.getCardBank());
				BigDecimal NewBankLedgerCardAmt = ledgerNewBankCardLedger
						.get(0).getClosingTotalBalance();
				String clNewBankLedgerCardType = ledgerNewBankCardLedger.get(0)
						.getClosingTotalType();

				if (clNewBankLedgerCardType.equalsIgnoreCase("Credit")) {
					NewBankLedgerCardAmt = ZERO.subtract(NewBankLedgerCardAmt);
				}

				BigDecimal finalNewBankLedgerAmt = NewBankLedgerCardAmt
						.add(salesNew.getCardAmount());

				if (finalNewBankLedgerAmt.signum() == -1) {
					finalClType = "Credit";
					finalNewBankLedgerAmt = finalNewBankLedgerAmt
							.multiply(CONVERT);
				} else {
					finalClType = "Debit";
				}
				ledgerDao.updateLedgerPartyBalance(finalNewBankLedgerAmt,
						finalClType, salesNew.getCardBank());

			}
			/* Card3.Journal Entry Update for Card */
			List<Journal> OldjrnlListCD = journalDao.getJournalUpdateSales(
					"Sales Card Receipt", "S" + salesOld.getSalesId());

			if (!OldjrnlListCD.isEmpty()) {
				if (salesOld.getCardAmount().signum() == 1
						&& salesNew.getCardAmount().signum() == 0) {
					journalDao.deleteJournal(OldjrnlListCD.get(0)); // Delete
																	// Entry
				} else if (salesNew.getCardAmount().signum() == 1
						&& salesOld.getCardAmount().signum() == 1) {
					OldjrnlListCD.get(0)
							.setJournalDate(salesNew.getSalesDate());
					OldjrnlListCD.get(0).setCreditAccountName(
							salesNew.getCustomerName());
					OldjrnlListCD.get(0).setDebitAccountName(
							salesNew.getCardBank());
					OldjrnlListCD.get(0).setDebitAmount(
							salesNew.getCardAmount());
					OldjrnlListCD.get(0).setCreditAmount(
							salesNew.getCardAmount());
					journalDao.updateJournal(OldjrnlListCD.get(0));
				}
			} else if (salesNew.getCardAmount().signum() == 1) {
				jrnl = new Journal();
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Sales Card Receipt");
				jrnl.setTransactionId("S" + salesNew.getSalesId()); // New
																	// Column
																	// added for
																	// tracking
																	// journal
																	// entries
				jrnl.setJournalDate(salesNew.getSalesDate());

				// Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(salesNew
						.getCardBank());
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
				jrnl.setDebitAccountName(salesNew.getCardBank());

				// Set Account group code as Credit code
				if (salesNew.getCustomerName().equalsIgnoreCase("walk_in")) {
					ledgerGroupCode = ledgerDao
							.getLedgerAccountCode("Sales Account");
					jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					jrnl.setCreditAccountName("Sales Account");
				} else {
					ledgerGroupCode = ledgerDao.getLedgerAccountCode(salesNew
							.getCustomerName());
					jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					jrnl.setCreditAccountName(salesNew.getCustomerName());
				}

				jrnl.setDebitAmount(salesNew.getCardAmount());
				jrnl.setCreditAmount(salesNew.getCardAmount());
				jrnl.setNarration("Bill No " + salesOld.getSalesTypeId());
				journalDao.insertJournal((Journal) jrnl);
			}

			// /////////////////////////////////// Receipt Mode - Voucher
			// Ledgers & Journal Update ///////////////////////////////////

			/* Voucher1. party Balance Update */
			if (!salesOld.getCustomerName().equals(salesNew.getCustomerName())
					&& !salesNew.getCustomerName().equals("walk_in")) {

				// ********************* Voucher Party balance Update : if -
				// different Customer Names ************************//

				/** From Party 1 */
				List<Ledger> ledgerListVoucherParty1 = ledgerDao
						.searchLedger(salesOld.getCustomerName());
				String clTypeVoucherParty1 = ledgerListVoucherParty1.get(0)
						.getClosingTotalType();
				BigDecimal oldClBalanceVoucherParty1 = ledgerListVoucherParty1
						.get(0).getClosingTotalBalance();
				String finalClTypeParty1 = "";
				String finalClTypeParty2 = "";

				/** To Party 2 */
				List<Ledger> ledgerListVoucherParty2 = ledgerDao
						.searchLedger(salesNew.getCustomerName());
				String clTypeVoucherParty2 = ledgerListVoucherParty2.get(0)
						.getClosingTotalType();
				BigDecimal oldClBalanceVoucherParty2 = ledgerListVoucherParty2
						.get(0).getClosingTotalBalance();

				if (clTypeVoucherParty1.equalsIgnoreCase("Credit")) {
					oldClBalanceVoucherParty1 = ZERO
							.subtract(oldClBalanceVoucherParty1);
				}
				if (clTypeVoucherParty2.equalsIgnoreCase("Credit")) {
					oldClBalanceVoucherParty2 = ZERO
							.subtract(oldClBalanceVoucherParty2);
				}

				BigDecimal finalClBalanceVoucherdParty1 = oldClBalanceVoucherParty1
						.add(salesOld.getVoucherAmount());
				BigDecimal finalClBalanceVoucherParty2 = oldClBalanceVoucherParty2
						.subtract(salesNew.getVoucherAmount());

				if (finalClBalanceVoucherdParty1.signum() == -1) {
					finalClTypeParty1 = "Credit";
					finalClBalanceVoucherdParty1 = finalClBalanceVoucherdParty1
							.multiply(CONVERT);
				} else {
					finalClTypeParty1 = "Debit";
				}

				if (finalClBalanceVoucherParty2.signum() == -1) {
					finalClTypeParty2 = "Credit";
					finalClBalanceVoucherParty2 = finalClBalanceVoucherParty2
							.multiply(CONVERT);
				} else {
					finalClTypeParty2 = "Debit";
				}

				ledgerDao.updateLedgerPartyBalance(
						finalClBalanceVoucherdParty1, finalClTypeParty1,
						salesOld.getCustomerName());
				ledgerDao.updateLedgerPartyBalance(finalClBalanceVoucherParty2,
						finalClTypeParty2, salesNew.getCustomerName());
			} else if (!salesNew.getCustomerName().equals("walk_in")) {
				// ********************* Voucher Party balance Update : if -
				// Same Customer Names **************************//

				List<Ledger> ledgerListVoucherParty = ledgerDao
						.searchLedger(salesOld.getCustomerName());
				BigDecimal oldClBalanceVoucherParty = ledgerListVoucherParty
						.get(0).getClosingTotalBalance();
				String clTypeVoucherParty = ledgerListVoucherParty.get(0)
						.getClosingTotalType();

				if (clTypeVoucherParty.equalsIgnoreCase("Credit")) {
					oldClBalanceVoucherParty = ZERO
							.subtract(oldClBalanceVoucherParty);
				}

				BigDecimal dropClBalanceVoucherParty = oldClBalanceVoucherParty
						.add(salesOld.getVoucherAmount());
				BigDecimal finalClBalanceVoucherParty = dropClBalanceVoucherParty
						.subtract(salesNew.getVoucherAmount());

				if (finalClBalanceVoucherParty.signum() == -1) {
					finalClType = "Credit";
					finalClBalanceVoucherParty = finalClBalanceVoucherParty
							.multiply(CONVERT);
				} else {
					finalClType = "Debit";
				}

				ledgerDao.updateLedgerPartyBalance(finalClBalanceVoucherParty,
						finalClType, salesOld.getCustomerName());
			}

			// //////////////////////////////////////* Voucher2. Voucher List
			// Ledger Update *//////////////////////////////////
			List<Ledger> ledgerListVoucherLedger = ledgerDao
					.searchLedger(salesOld.getVoucherList());

			if (!ledgerListVoucherLedger.isEmpty()) { // Initially voucher
														// selected
				BigDecimal oldClBalanceVoucherLedger = ledgerListVoucherLedger
						.get(0).getClosingTotalBalance();
				String clTypeVoucherLedger = ledgerListVoucherLedger.get(0)
						.getClosingTotalType();

				if (clTypeVoucherLedger.equalsIgnoreCase("Credit")) {
					oldClBalanceVoucherLedger = ZERO
							.subtract(oldClBalanceVoucherLedger);
				}

				if (salesOld.getVoucherList().equals(salesNew.getVoucherList())) { // ************************************
																					// If
																					// the
																					// Bank
																					// Names
																					// are
																					// same
																					// ********************************//
					BigDecimal dropClBalanceVoucherLedger = oldClBalanceVoucherLedger
							.subtract(salesOld.getVoucherAmount());
					BigDecimal finalClBalanceVoucherLedger = dropClBalanceVoucherLedger
							.add(salesNew.getVoucherAmount());

					if (finalClBalanceVoucherLedger.signum() == -1) {
						finalClType = "Credit";
						finalClBalanceVoucherLedger = finalClBalanceVoucherLedger
								.multiply(CONVERT);
					} else {
						finalClType = "Debit";
					}
					ledgerDao.updateLedgerPartyBalance(
							finalClBalanceVoucherLedger, finalClType,
							salesOld.getVoucherList());
				} else { /*
						 *  ************************************B0. If the Bank
						 * A/c Name changes for Voucher .**************
						 */
					/* B1. For old Bank A/c for Voucher */// Subtract Old
															// Voucher Amount in
															// ledger

					BigDecimal finalOldBankLedgerVoucherAmt = oldClBalanceVoucherLedger
							.subtract(salesOld.getVoucherAmount());
					if (finalOldBankLedgerVoucherAmt.signum() == -1) {
						finalClType = "Credit";
						finalOldBankLedgerVoucherAmt = finalOldBankLedgerVoucherAmt
								.multiply(CONVERT);
					} else {
						finalClType = "Debit";
					}
					ledgerDao.updateLedgerPartyBalance(
							finalOldBankLedgerVoucherAmt, finalClType,
							salesOld.getVoucherList());

					/* B2. For New Bank A/c for Voucher */// Add new Voucher
															// amount in ledger
					List<Ledger> ledgerNewBankVoucherLedger = ledgerDao
							.searchLedger(salesNew.getVoucherList());
					BigDecimal NewBankLedgerVoucherAmt = ledgerNewBankVoucherLedger
							.get(0).getClosingTotalBalance();
					String clNewBankLedgerVoucherType = ledgerNewBankVoucherLedger
							.get(0).getClosingTotalType();

					if (clNewBankLedgerVoucherType.equalsIgnoreCase("Credit")) {
						NewBankLedgerVoucherAmt = ZERO
								.subtract(NewBankLedgerVoucherAmt);
					}
					BigDecimal finalNewBankLedgerVoucherAmt = NewBankLedgerVoucherAmt
							.add(salesNew.getVoucherAmount());

					if (finalNewBankLedgerVoucherAmt.signum() == -1) {
						finalClType = "Credit";
						finalNewBankLedgerVoucherAmt = finalNewBankLedgerVoucherAmt
								.multiply(CONVERT);
					} else {
						finalClType = "Debit";
					}
					ledgerDao.updateLedgerPartyBalance(
							finalNewBankLedgerVoucherAmt, finalClType,
							salesNew.getVoucherList());
				}
			} else if (salesOld.getVoucherAmount().signum() == 0
					&& salesNew.getVoucherAmount().signum() == 1) {
				// On Update added Voucher Option
				List<Ledger> ledgerNewBankVoucherLedger = ledgerDao
						.searchLedger(salesNew.getVoucherList());
				BigDecimal NewBankLedgerVoucherAmt = ledgerNewBankVoucherLedger
						.get(0).getClosingTotalBalance();
				String clNewBankLedgerVoucherType = ledgerNewBankVoucherLedger
						.get(0).getClosingTotalType();

				if (clNewBankLedgerVoucherType.equalsIgnoreCase("Credit")) {
					NewBankLedgerVoucherAmt = ZERO
							.subtract(NewBankLedgerVoucherAmt);
				}
				BigDecimal finalNewBankLedgerVoucherAmt = NewBankLedgerVoucherAmt
						.add(salesNew.getVoucherAmount());

				if (finalNewBankLedgerVoucherAmt.signum() == -1) {
					finalClType = "Credit";
					finalNewBankLedgerVoucherAmt = finalNewBankLedgerVoucherAmt
							.multiply(CONVERT);
				} else {
					finalClType = "Debit";
				}
				ledgerDao.updateLedgerPartyBalance(
						finalNewBankLedgerVoucherAmt, finalClType,
						salesNew.getVoucherList());
			}

			/* C3.Journal Entry Update for Voucher */
			List<Journal> OldjrnlListV = journalDao.getJournalUpdateSales(
					"Sales Voucher Receipt", "S" + salesOld.getSalesId());

			if (!OldjrnlListV.isEmpty()) {
				if (salesOld.getVoucherAmount().signum() == 1
						&& salesNew.getVoucherAmount().signum() == 0) {
					journalDao.deleteJournal(OldjrnlListV.get(0)); // Delete
																	// Entry
				} else if (salesNew.getVoucherAmount().signum() == 1) {
					Journal jrnlOld = OldjrnlListV.get(0);
					jrnlOld.setJournalDate(salesNew.getSalesDate());
					jrnlOld.setCreditAccountName(salesNew.getCustomerName());
					jrnlOld.setDebitAccountName(salesNew.getVoucherList());
					jrnlOld.setDebitAmount(salesNew.getVoucherAmount());
					jrnlOld.setCreditAmount(salesNew.getVoucherAmount());
					journalDao.updateJournal(jrnlOld);
				}
			} else if (salesNew.getVoucherAmount().signum() == 1) {
				jrnl = new Journal();
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Sales Voucher Receipt");
				jrnl.setTransactionId("S" + salesNew.getSalesId()); // New
																	// Column
																	// added for
																	// tracking
																	// journal
																	// entries
				jrnl.setJournalDate(salesNew.getSalesDate());

				// Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(salesNew
						.getVoucherList());
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
				jrnl.setDebitAccountName(salesNew.getVoucherList());

				// Set Account group code as Credit code
				if (salesNew.getCustomerName().equalsIgnoreCase("walk_in")) {
					ledgerGroupCode = ledgerDao
							.getLedgerAccountCode("Sales Account");
					jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					jrnl.setCreditAccountName("Sales Account");
				} else {
					ledgerGroupCode = ledgerDao.getLedgerAccountCode(salesNew
							.getCustomerName());
					jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					jrnl.setCreditAccountName(salesNew.getCustomerName());
				}

				jrnl.setDebitAmount(salesNew.getVoucherAmount());
				jrnl.setCreditAmount(salesNew.getVoucherAmount());
				jrnl.setNarration("Bill No " + salesOld.getSalesTypeId());
				journalDao.insertJournal((Journal) jrnl);
			}
		}

		/**
		 * generate Automatic Id if Tax Type Changes on Update
		 * if(salesOld.getTax().compareTo(salesNew.getTax()) != 0){
		 * salesNew.setSalesTypeId(getSalesTypeId(salesNew)); }
		 **/
		// SR Id will be updated as SOld
		salesDao.UpdateSRID(salesReturnId);
		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
		// get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		dateFormat.format(cal.getTime());
		salesNew.setCurrentTime(dateFormat.format(cal.getTime()));
		salesDao.updateSales(salesNew);
		salesOrderDao.UpdateStatusID(sales_orderId);
		String pBillStaus = "Sold";
		List<Purchase> purlist = purchaseDao.getPurchaseAmount(salesNew
				.getExchangeBillNo());

		if (!purlist.isEmpty()) {
			purlist.get(0).setExchangePurchase(pBillStaus);
			purchaseDao.updatePurchase(purlist.get(0));
		}

		model.addAttribute("invc", salesNew.getSalesTypeId());
		List<CompanyInfo> companyList = companyInfoDao.listCompanyInfo();
		InvoiceBillFormat orninoviceObj = new InvoiceTextFormatPrint();
		try{
			if (!companyList.isEmpty()) { // if Printer Format set in Company Info
				if (companyList.get(0).getOrnInvoicePrint()
						.equalsIgnoreCase("dotMatrix8")) {
					orninoviceObj.printinvoice(salesNew, companyInfoDao, ledgerDao,
							request);
					return new ModelAndView("redirect:sales.htm");
				} else if (companyList.get(0).getOrnInvoicePrint()
						.equalsIgnoreCase("dotMatrix4")) {
					orninoviceObj.printFourInchInvoice(salesNew, companyInfoDao,
							ledgerDao, request);
					return new ModelAndView("redirect:sales.htm");
				}
			}
		}catch(NullPointerException npe){
			System.out.println(" Please Update CompanyInfo Settings ");
			
		}
		return new ModelAndView("redirect:invoicePreview.htm", model);
	}

	// //////////////////////////////// Update Code Ends here
	// /////////////////////////////////////////////////

	// Sales SAVE code
	@RequestMapping(value = "/salesItem", method = RequestMethod.POST, params = "insert")
	public ModelAndView saveSales(@ModelAttribute("sales") Sales sales,
			BindingResult result, ModelMap model, HttpServletRequest request,
			SessionStatus status) {

		String id = sales.getExchangeBillNo();
		Integer sales_orderId = sales.getSalesOrderID();
		salesValidator.validate(sales, result);
		salesValidator.validateInsert(sales, result);

		if (result.hasErrors()) {
			model.addAttribute("name", ledgerDao.listLedgerName());
			model.addAttribute("s_manname", userloginDao.userlist());
			model.addAttribute("billNo",
					purchaseDao.getPurchaseBillNo(sales.getCustomerName()));// getting
																			// Purchasebilno
																			// on
																			// customer
																			// change
			model.addAttribute("purchaseAmount",
					purchaseDao.getPurchaseAmount(id));
			model.addAttribute("bank_name", ledgerDao.listBank());
			model.addAttribute("assets_name", ledgerDao.listCurrentAssets());
			model.addAttribute("salesorder_id",
					salesOrderDao.getSalesOrderId(sales.getCustomerName()));
			model.addAttribute("SSCardNoList",
					cardIssueDao.SSSalesCardNoList(sales.getCustomerName()));
			model.addAttribute("salesReturn_id",
					salesDao.getSalesReturnId(sales.getCustomerName()));// add
																		// new
																		// for
																		// SR Id
																		// on
																		// 14-02-13
			model.addAttribute("command", sales);
			return new ModelAndView("formsales", model);
		}

		/********* added on 4-7-12 for purchaseBill ***************/
		String pBillStaus = "Sold";
		List<Purchase> purlist = purchaseDao.getPurchaseAmount(sales
				.getExchangeBillNo());
		if (!purlist.isEmpty()) {
			purlist.get(0).setExchangePurchase(pBillStaus);
			purchaseDao.updatePurchase(purlist.get(0));
		}
		/********* ends *************************/
		String billNumber = getLastBillNo();
		sales.setBillNo(billNumber);

		String salesTypeId = getSalesTypeId(sales);
		sales.setSalesTypeId(salesTypeId);
		// *************** For Stock Update
		// ****************************************

		BigDecimal Weight = new BigDecimal("0.0");
		int quantity;
		String itemCode = sales.getItemCode();
		Weight = sales.getGrossWeight();
		quantity = sales.getNumberOfPieces();
		int qty = quantity - 1;
		int totalPcs = qty;

		String iCode2 = sales.getItemCode2();
		String iCode3 = sales.getItemCode3();
		String iCode4 = sales.getItemCode4();

		List<ItemMaster> itemList2 = itemmasterDao.searchItemMaster(iCode2);
		List<ItemMaster> itemList3 = itemmasterDao.searchItemMaster(iCode3);
		List<ItemMaster> itemList4 = itemmasterDao.searchItemMaster(iCode4);
		if (itemCode != null && sales.getGrossWeight().signum() == 1) {
			if (itemCode.compareToIgnoreCase("IT100011") == 0 || itemCode.compareToIgnoreCase("IT100012") == 0) {
				itemmasterDao.updateLooseStock(Weight, Weight, Weight,quantity, itemCode);
				/****
				 * Stockupdation in JewelStock table
				 * **/
				JewelStockCore.jewelStockInsert(jewelStockDao, "Sales",
						sales.getSalesTypeId(), sales.getSalesDate(),
						sales.getItemName(), sales.getItemCode(),
						sales.getCategoryName(), sales.getBullionType(),
						sales.getMetalUsed(), sales.getNumberOfPieces(),
						sales.getTotalPieces(), sales.getGrossWeight(),
						sales.getNetWeight(), sales.getGrossWeight(),
						sales.getStone(), sales.getStoneRatePerCaret(),
						sales.getStoneCost(), sales.getBullionRate(),
						sales.getValueAdditionCharges(),
						sales.getLessPercentage(), sales.getMcByAmount(),
						sales.getMcByGrams(), sales.getVtax(),
						sales.getSalesHMCharges());
				
			} else if (!sales.getMetalUsed().equalsIgnoreCase("Gold Loose Stock")&& !sales.getMetalUsed().equalsIgnoreCase("Silver Loose Stock")) 
			{
				itemmasterDao.updateSalesStock(ZERO, qty, totalPcs, itemCode);
				/****
				 * Stockupdation in JewelStock table
				 * **/
				JewelStockCore.jewelStockInsert(jewelStockDao, "Sales",
						sales.getSalesTypeId(), sales.getSalesDate(),
						sales.getItemName(), sales.getItemCode(),
						sales.getCategoryName(), sales.getBullionType(),
						sales.getMetalUsed(), sales.getNumberOfPieces(),
						sales.getTotalPieces(), sales.getGrossWeight(),
						sales.getNetWeight(), sales.getGrossWeight(),
						sales.getStone(), sales.getStoneRatePerCaret(),
						sales.getStoneCost(), sales.getBullionRate(),
						sales.getValueAdditionCharges(),
						sales.getLessPercentage(), sales.getMcByAmount(),
						sales.getMcByGrams(), sales.getVtax(),
						sales.getSalesHMCharges());
			} else {

				String iCode = sales.getItemCode();
				List<ItemMaster> itemList = itemmasterDao.searchItemMaster(iCode);
				
				/*System.out.println(itemList.get(0).getItemCode());*/
				for (int i = 0; i < itemList.size(); i++) {
					ItemMaster imast = (ItemMaster) itemList.get(i);
					if (imast instanceof ItemMaster) {
						itemDetails = (ItemMaster) imast;
						BigDecimal itemtotal_grossWt = itemDetails.getTotalGrossWeight();
						BigDecimal salesTotalGroswt = sales.getGrossWeight();
						BigDecimal finalTotalGrossWt = itemtotal_grossWt.subtract(salesTotalGroswt);
						int itemMasterQty = itemDetails.getQty();
						int salesQty = sales.getSoldQty();
						int finalQty = itemMasterQty - salesQty;
						itemmasterDao.updateLooseCategorySalesStock(finalTotalGrossWt, finalTotalGrossWt,finalTotalGrossWt, finalQty, itemCode);
					}
				}
				
				/****
				 * Stockupdation in JewelStock table
				 * **/
				JewelStockCore.jewelStockCatLooseStockInsert(jewelStockDao,
						"Sales", sales.getSalesTypeId(), sales.getSalesDate(),
						sales.getItemName(), sales.getItemCode(),
						sales.getCategoryName(), sales.getBullionType(),
						sales.getMetalUsed(), sales.getSoldQty(),
						sales.getTotalPieces(), sales.getGrossWeight(),
						sales.getNetWeight(), sales.getGrossWeight(),
						sales.getStone(), sales.getStoneRatePerCaret(),
						sales.getStoneCost(), sales.getBullionRate(),
						sales.getValueAdditionCharges(),
						sales.getLessPercentage(), sales.getMcByAmount(),
						sales.getMcByGrams(), sales.getVtax(),
						sales.getSalesHMCharges());
			}
		} else {
			sales.setItemCode("");
		}

		String itemcode1 = sales.getItemCode1();
		Weight = sales.getGrossWeight1();
		quantity = sales.getNumberOfPieces1();
		qty = quantity - 1;
		totalPcs = qty;

		if (itemcode1 != null && sales.getGrossWeight1().signum() == 1) {
			if (itemcode1.compareToIgnoreCase("IT100011") == 0	|| itemcode1.compareToIgnoreCase("IT100012") == 0) {
				itemmasterDao.updateLooseStock(Weight, Weight, Weight,quantity, itemcode1);
				/****
				 * Stockupdation in JewelStock table
				 * **/
				JewelStockCore.jewelStockInsert(jewelStockDao, "Sales",
						sales.getSalesTypeId(), sales.getSalesDate(),
						sales.getItemName1(), sales.getItemCode1(),
						sales.getCategoryName1(), sales.getBullionType1(),
						sales.getMetalUsed1(), sales.getNumberOfPieces1(),
						sales.getTotalPieces1(), sales.getGrossWeight1(),
						sales.getNetWeight1(), sales.getGrossWeight1(),
						sales.getStone1(), sales.getStoneRatePerCaret1(),
						sales.getStoneCost1(), sales.getBullionRate1(),
						sales.getValueAdditionCharges1(),
						sales.getLessPercentage1(), sales.getMcByAmount1(),
						sales.getMcByGrams1(), sales.getVtax1(),
						sales.getSalesHMCharges1());
				
			} else if (!sales.getMetalUsed().equalsIgnoreCase("Gold Loose Stock") && !sales.getMetalUsed().equalsIgnoreCase("Silver Loose Stock")) 
			{
				itemmasterDao.updateSalesStock(ZERO, qty, totalPcs, itemcode1);
				/****
				 * Stockupdation in JewelStock table
				 * **/
				JewelStockCore.jewelStockInsert(jewelStockDao, "Sales",
						sales.getSalesTypeId(), sales.getSalesDate(),
						sales.getItemName1(), sales.getItemCode1(),
						sales.getCategoryName1(), sales.getBullionType1(),
						sales.getMetalUsed1(), sales.getNumberOfPieces1(),
						sales.getTotalPieces1(), sales.getGrossWeight1(),
						sales.getNetWeight1(), sales.getGrossWeight1(),
						sales.getStone1(), sales.getStoneRatePerCaret1(),
						sales.getStoneCost1(), sales.getBullionRate1(),
						sales.getValueAdditionCharges1(),
						sales.getLessPercentage1(), sales.getMcByAmount1(),
						sales.getMcByGrams1(), sales.getVtax1(),
						sales.getSalesHMCharges1());
			} else {
				
				String iCode1 = sales.getItemCode1();
				List<ItemMaster> itemList1 = itemmasterDao
						.searchItemMaster(iCode1);
				for (int i = 0; i < itemList1.size(); i++) {
					ItemMaster imast = (ItemMaster) itemList1.get(i);
					if (imast instanceof ItemMaster) {
						itemDetails = (ItemMaster) imast;
						BigDecimal itemtotal_grossWt = itemDetails.getTotalGrossWeight();
						BigDecimal salesTotalGroswt = sales.getGrossWeight1();
						BigDecimal finalTotalGrossWt = itemtotal_grossWt.subtract(salesTotalGroswt);
						int itemMasterQty = itemDetails.getQty();
						int salesQty = sales.getSoldQty1();
						int finalQty = itemMasterQty - salesQty;
						itemmasterDao.updateLooseCategorySalesStock(
								finalTotalGrossWt, finalTotalGrossWt,
								finalTotalGrossWt, finalQty, itemcode1);
					}
				}
				/****
				 * Stockupdation in JewelStock table
				 * **/
				JewelStockCore.jewelStockCatLooseStockInsert(jewelStockDao,
						"Sales", sales.getSalesTypeId(), sales.getSalesDate(),
						sales.getItemName1(), sales.getItemCode1(),
						sales.getCategoryName1(), sales.getBullionType1(),
						sales.getMetalUsed1(), sales.getSoldQty1(),
						sales.getTotalPieces1(), sales.getGrossWeight1(),
						sales.getNetWeight1(), sales.getGrossWeight1(),
						sales.getStone1(), sales.getStoneRatePerCaret1(),
						sales.getStoneCost1(), sales.getBullionRate1(),
						sales.getValueAdditionCharges1(),
						sales.getLessPercentage1(), sales.getMcByAmount1(),
						sales.getMcByGrams1(), sales.getVtax1(),
						sales.getSalesHMCharges1());
			}
		} else {
			sales.setItemCode1("");
		}

		String itemcode2 = sales.getItemCode2();
		Weight = sales.getGrossWeight2();
		quantity = sales.getNumberOfPieces2();
		qty = quantity - 1;
		totalPcs = qty;

		if (itemcode2 != null && sales.getGrossWeight2().signum() == 1) {
			if (itemcode2.compareToIgnoreCase("IT100011") == 0	|| itemcode2.compareToIgnoreCase("IT100012") == 0) {
				itemmasterDao.updateLooseStock(Weight, Weight, Weight,quantity, itemcode2);
				/****
				 * Stockupdation in JewelStock table
				 * **/
				JewelStockCore.jewelStockInsert(jewelStockDao, "Sales",
						sales.getSalesTypeId(), sales.getSalesDate(),
						sales.getItemName2(), sales.getItemCode2(),
						sales.getCategoryName2(), sales.getBullionType2(),
						sales.getMetalUsed2(), sales.getNumberOfPieces2(),
						sales.getTotalPieces2(), sales.getGrossWeight2(),
						sales.getNetWeight2(), sales.getGrossWeight2(),
						sales.getStone2(), sales.getStoneRatePerCaret2(),
						sales.getStoneCost2(), sales.getBullionRate2(),
						sales.getValueAdditionCharges2(),
						sales.getLessPercentage2(), sales.getMcByAmount2(),
						sales.getMcByGrams2(), sales.getVtax2(),
						sales.getSalesHMCharges2());
			} else if (!sales.getMetalUsed().equalsIgnoreCase("Gold Loose Stock") && !sales.getMetalUsed().equalsIgnoreCase("Silver Loose Stock")) {
				itemmasterDao.updateSalesStock(ZERO, qty, totalPcs, itemcode2);
				/****
				 * Stockupdation in JewelStock table
				 * **/
				JewelStockCore.jewelStockInsert(jewelStockDao, "Sales",
						sales.getSalesTypeId(), sales.getSalesDate(),
						sales.getItemName2(), sales.getItemCode2(),
						sales.getCategoryName2(), sales.getBullionType2(),
						sales.getMetalUsed2(), sales.getNumberOfPieces2(),
						sales.getTotalPieces2(), sales.getGrossWeight2(),
						sales.getNetWeight2(), sales.getGrossWeight2(),
						sales.getStone2(), sales.getStoneRatePerCaret2(),
						sales.getStoneCost2(), sales.getBullionRate2(),
						sales.getValueAdditionCharges2(),
						sales.getLessPercentage2(), sales.getMcByAmount2(),
						sales.getMcByGrams2(), sales.getVtax2(),
						sales.getSalesHMCharges2());
			} else {

				for (int i = 0; i < itemList2.size(); i++) {
					ItemMaster imast = (ItemMaster) itemList2.get(i);
					if (imast instanceof ItemMaster) {
						itemDetails = (ItemMaster) imast;
						BigDecimal itemtotal_grossWt = itemDetails.getTotalGrossWeight();
						BigDecimal salesTotalGroswt = sales.getGrossWeight2();
						BigDecimal finalTotalGrossWt = itemtotal_grossWt.subtract(salesTotalGroswt);
						int itemMasterQty = itemDetails.getQty();
						int salesQty = sales.getSoldQty2();
						int finalQty = itemMasterQty - salesQty;
						itemmasterDao.updateLooseCategorySalesStock(
								finalTotalGrossWt, finalTotalGrossWt,
								finalTotalGrossWt, finalQty, itemcode2);
					}
				}
				/****
				 * Stockupdation in JewelStock table
				 * **/
				JewelStockCore.jewelStockCatLooseStockInsert(jewelStockDao,
						"Sales", sales.getSalesTypeId(), sales.getSalesDate(),
						sales.getItemName2(), sales.getItemCode2(),
						sales.getCategoryName2(), sales.getBullionType2(),
						sales.getMetalUsed2(), sales.getSoldQty2(),
						sales.getTotalPieces2(), sales.getGrossWeight2(),
						sales.getNetWeight2(), sales.getGrossWeight2(),
						sales.getStone2(), sales.getStoneRatePerCaret2(),
						sales.getStoneCost2(), sales.getBullionRate2(),
						sales.getValueAdditionCharges2(),
						sales.getLessPercentage2(), sales.getMcByAmount2(),
						sales.getMcByGrams2(), sales.getVtax2(),
						sales.getSalesHMCharges2());
			}
		} else {
			sales.setItemCode2("");
		}

		String itemcode3 = sales.getItemCode3();
		Weight = sales.getGrossWeight3();
		quantity = sales.getNumberOfPieces3();
		qty = quantity - 1;
		totalPcs = qty;

		if (itemcode3 != null && sales.getGrossWeight3().signum() == 1) {
			if (itemcode3.compareToIgnoreCase("IT100011") == 0 || itemcode3.compareToIgnoreCase("IT100012") == 0) {
				itemmasterDao.updateLooseStock(Weight, Weight, Weight,quantity, itemcode3);
				/****
				 * Stockupdation in JewelStock table
				 * **/
				JewelStockCore.jewelStockInsert(jewelStockDao, "Sales",
						sales.getSalesTypeId(), sales.getSalesDate(),
						sales.getItemName3(), sales.getItemCode3(),
						sales.getCategoryName3(), sales.getBullionType3(),
						sales.getMetalUsed3(), sales.getNumberOfPieces3(),
						sales.getTotalPieces3(), sales.getGrossWeight3(),
						sales.getNetWeight3(), sales.getGrossWeight3(),
						sales.getStone3(), sales.getStoneRatePerCaret3(),
						sales.getStoneCost3(), sales.getBullionRate3(),
						sales.getValueAdditionCharges3(),
						sales.getLessPercentage3(), sales.getMcByAmount3(),
						sales.getMcByGrams3(), sales.getVtax3(),
						sales.getSalesHMCharges3());
			} else if (!sales.getMetalUsed().equalsIgnoreCase("Gold Loose Stock")	&& !sales.getMetalUsed().equalsIgnoreCase("Silver Loose Stock")) 
			{
				itemmasterDao.updateSalesStock(ZERO, qty, totalPcs, itemcode3);
				/****
				 * Stockupdation in JewelStock table
				 * **/
				JewelStockCore.jewelStockInsert(jewelStockDao, "Sales",
						sales.getSalesTypeId(), sales.getSalesDate(),
						sales.getItemName3(), sales.getItemCode3(),
						sales.getCategoryName3(), sales.getBullionType3(),
						sales.getMetalUsed3(), sales.getNumberOfPieces3(),
						sales.getTotalPieces3(), sales.getGrossWeight3(),
						sales.getNetWeight3(), sales.getGrossWeight3(),
						sales.getStone3(), sales.getStoneRatePerCaret3(),
						sales.getStoneCost3(), sales.getBullionRate3(),
						sales.getValueAdditionCharges3(),
						sales.getLessPercentage3(), sales.getMcByAmount3(),
						sales.getMcByGrams3(), sales.getVtax3(),
						sales.getSalesHMCharges3());
			} else {
				for (int i = 0; i < itemList3.size(); i++) {
					ItemMaster imast = (ItemMaster) itemList3.get(i);
					if (imast instanceof ItemMaster) {
						itemDetails = (ItemMaster) imast;
						BigDecimal itemtotal_grossWt = itemDetails.getTotalGrossWeight();
						BigDecimal salesTotalGroswt = sales.getGrossWeight3();
						BigDecimal finalTotalGrossWt = itemtotal_grossWt.subtract(salesTotalGroswt);
						int itemMasterQty = itemDetails.getQty();
						int salesQty = sales.getSoldQty3();
						int finalQty = itemMasterQty - salesQty;
						itemmasterDao.updateLooseCategorySalesStock(
								finalTotalGrossWt, finalTotalGrossWt,
								finalTotalGrossWt, finalQty, itemcode3);
					}
				}
				/****
				 * Stockupdation in JewelStock table
				 * **/
				JewelStockCore.jewelStockCatLooseStockInsert(jewelStockDao,
						"Sales", sales.getSalesTypeId(), sales.getSalesDate(),
						sales.getItemName3(), sales.getItemCode3(),
						sales.getCategoryName3(), sales.getBullionType3(),
						sales.getMetalUsed3(), sales.getSoldQty3(),
						sales.getTotalPieces3(), sales.getGrossWeight3(),
						sales.getNetWeight3(), sales.getGrossWeight3(),
						sales.getStone3(), sales.getStoneRatePerCaret3(),
						sales.getStoneCost3(), sales.getBullionRate3(),
						sales.getValueAdditionCharges3(),
						sales.getLessPercentage3(), sales.getMcByAmount3(),
						sales.getMcByGrams3(), sales.getVtax3(),
						sales.getSalesHMCharges3());
			}
		} else {
			sales.setItemCode3("");
		}

		String itemcode4 = sales.getItemCode4();
		Weight = sales.getGrossWeight4();
		quantity = sales.getNumberOfPieces4();
		qty = quantity - 1;
		totalPcs = qty;

		if (itemcode4 != null && sales.getGrossWeight4().signum() == 1) {
			if (itemcode4.compareToIgnoreCase("IT100011") == 0	|| itemcode4.compareToIgnoreCase("IT100012") == 0) {
				
				itemmasterDao.updateLooseStock(Weight, Weight, Weight,quantity, itemcode4);
				/****
				 * Stockupdation in JewelStock table
				 * **/
				JewelStockCore.jewelStockInsert(jewelStockDao, "Sales",
						sales.getSalesTypeId(), sales.getSalesDate(),
						sales.getItemName4(), sales.getItemCode4(),
						sales.getCategoryName4(), sales.getBullionType4(),
						sales.getMetalUsed4(), sales.getNumberOfPieces4(),
						sales.getTotalPieces4(), sales.getGrossWeight4(),
						sales.getNetWeight4(), sales.getGrossWeight4(),
						sales.getStone4(), sales.getStoneRatePerCaret4(),
						sales.getStoneCost4(), sales.getBullionRate4(),
						sales.getValueAdditionCharges4(),
						sales.getLessPercentage4(), sales.getMcByAmount4(),
						sales.getMcByGrams4(), sales.getVtax4(),
						sales.getSalesHMCharges4());
				
			} else if (!sales.getMetalUsed().equalsIgnoreCase("Gold Loose Stock") && !sales.getMetalUsed().equalsIgnoreCase("Silver Loose Stock")) 
			{
				itemmasterDao.updateSalesStock(ZERO, qty, totalPcs, itemcode4);
				JewelStockCore.jewelStockInsert(jewelStockDao, "Sales",
						sales.getSalesTypeId(), sales.getSalesDate(),
						sales.getItemName4(), sales.getItemCode4(),
						sales.getCategoryName4(), sales.getBullionType4(),
						sales.getMetalUsed4(), sales.getNumberOfPieces4(),
						sales.getTotalPieces4(), sales.getGrossWeight4(),
						sales.getNetWeight4(), sales.getGrossWeight4(),
						sales.getStone4(), sales.getStoneRatePerCaret4(),
						sales.getStoneCost4(), sales.getBullionRate4(),
						sales.getValueAdditionCharges4(),
						sales.getLessPercentage4(), sales.getMcByAmount4(),
						sales.getMcByGrams4(), sales.getVtax4(),
						sales.getSalesHMCharges4());
			} else {
				for (int i = 0; i < itemList4.size(); i++) {
					ItemMaster imast = (ItemMaster) itemList4.get(i);
					if (imast instanceof ItemMaster) {
						itemDetails = (ItemMaster) imast;
						BigDecimal itemtotal_grossWt = itemDetails.getTotalGrossWeight();
						BigDecimal salesTotalGroswt = sales.getGrossWeight4();
						BigDecimal finalTotalGrossWt = itemtotal_grossWt.subtract(salesTotalGroswt);
						int itemMasterQty = itemDetails.getQty();
						int salesQty = sales.getSoldQty4();
						int finalQty = itemMasterQty - salesQty;
						itemmasterDao.updateLooseCategorySalesStock(
								finalTotalGrossWt, finalTotalGrossWt,
								finalTotalGrossWt, finalQty, itemcode4);
					}
				}
				/****
				 * Stockupdation in JewelStock table
				 * **/
				JewelStockCore.jewelStockCatLooseStockInsert(jewelStockDao,
						"Sales", sales.getSalesTypeId(), sales.getSalesDate(),
						sales.getItemName4(), sales.getItemCode4(),
						sales.getCategoryName4(), sales.getBullionType4(),
						sales.getMetalUsed4(), sales.getSoldQty4(),
						sales.getTotalPieces4(), sales.getGrossWeight4(),
						sales.getNetWeight4(), sales.getGrossWeight4(),
						sales.getStone4(), sales.getStoneRatePerCaret4(),
						sales.getStoneCost4(), sales.getBullionRate4(),
						sales.getValueAdditionCharges4(),
						sales.getLessPercentage4(), sales.getMcByAmount4(),
						sales.getMcByGrams4(), sales.getVtax4(),
						sales.getSalesHMCharges4());
			}
		} else {
			sales.setItemCode4("");
		}

		// ** Saving Scheme Updating Used Card No Status to Finished **/
		try {
			if (sales.getSSCardNo() != null && !sales.getSSCardNo().isEmpty()) {
				String SelectedCardNo[] = sales.getSSCardNo().split(",");
				for (int i = 0; i < SelectedCardNo.length; i++) {
					CardIssue CardIssueEntity = cardIssueDao
							.getCardIssueCardNO(SelectedCardNo[i]).get(0);
					CardIssueEntity.setStatus("Finished");
					cardIssueDao.updateCardIssue(CardIssueEntity);
				}

			}
		} catch (IndexOutOfBoundsException ie) {
		}
		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
		// get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		dateFormat.format(cal.getTime());
		sales.setCurrentTime(dateFormat.format(cal.getTime()));
		salesDao.insertSales(sales);
		salesOrderDao.UpdateStatusID(sales_orderId);

		String salesReturnId = sales.getSalesReturnId();
		salesDao.UpdateSRID(salesReturnId);

		// Updating party balances on Credit, Card and Cheque sales

		String ledgername = null;
		String partyName = sales.getCustomerName();
		String closingType = "Debit";
		String closingTyp = "Credit";
		String cashPayment = sales.getCashPayment();
		String cardPayment = sales.getCardPayment();
		String chequePayment = sales.getChequePayment();
		String voucherPayment = sales.getVoucherPayment();
		String chequeList = sales.getChequeBank();
		String cardList = sales.getCardBank();
		String voucherList = sales.getVoucherList();

		BigDecimal cashAmt = sales.getCashAmount();
		BigDecimal chequeAmt = sales.getChequeAmount();
		BigDecimal cardAmt = sales.getCardAmount();
		BigDecimal voucherAmt = sales.getVoucherAmount();
		BigDecimal closingAmount = sales.getBillAmount();
		BigDecimal clBal = new BigDecimal("0.0");

		// ************************JOURNAL ENTRY AND LEDGER UPDATION FOR
		// SALES********************************

		if (partyName.equals("walk_in")) // for walk-in customer
		{
			if (sales.getSalesType().equalsIgnoreCase("Sales")) {
				// Journal Entry and Ledger update for sales
				jrnl = new Journal();
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Cash Sales");
				jrnl.setTransactionId("S" + sales.getSalesId()); // New Column
																	// added for
																	// tracking
																	// journal
																	// entries
				jrnl.setJournalDate(sales.getSalesDate());

				// Set Account group code as Debit code
				ledgerGroupCode = ledgerDao
						.getLedgerAccountCode("Cash Account");
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
				jrnl.setDebitAccountName("Cash Account");

				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao
						.getLedgerAccountCode("Sales Account");
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName("Sales Account");

				jrnl.setDebitAmount(closingAmount);
				jrnl.setCreditAmount(closingAmount);
				jrnl.setNarration("Bill No " + sales.getSalesTypeId());
				journalDao.insertJournal((Journal) jrnl);

				// Update cash balance
				ledgername = "Cash Account";

				List<Ledger> ledgerList = ledgerDao.searchLedger(ledgername);

				clBal = ledgerList.get(0).getClosingTotalBalance();
				clType = ledgerList.get(0).getClosingTotalType();

				if (clType.equals("Debit")) {
					ledgerDao.updatePartyBalance(closingAmount, closingType,
							ledgername);
				} else if (clType.equals("Credit")) {
					if (clBal.compareTo(closingAmount) == 1) {
						ledgerDao.updateCreditPartyBalance(closingAmount,
								closingTyp, ledgername);
					} else {
						ledgerDao.updateCrPartyBalance(closingAmount,
								closingType, ledgername);
					}
				}

				// Update Sales Account
				ledgername = "Sales Account";
				List<Ledger> salesList = ledgerDao.searchLedger(ledgername);

				clBal = salesList.get(0).getClosingTotalBalance();
				clType = salesList.get(0).getClosingTotalType();

				if (clType.equals("Credit")) {
					ledgerDao.updatePartyBalance(closingAmount, closingTyp,
							ledgername);
				} else {
					if (clBal.compareTo(closingAmount) == 1) {
						ledgerDao.updateCreditPartyBalance(closingAmount,
								closingType, ledgername);
					} else {
						ledgerDao.updateCrPartyBalance(closingAmount,
								closingTyp, ledgername);
					}
				}
			}
		} else { // for regular customer

			// Update Party Balance
			List<Ledger> customerList = ledgerDao.searchLedger(partyName);
			clBal = customerList.get(0).getClosingTotalBalance();
			clType = customerList.get(0).getClosingTotalType();

			if (clType.equals("Debit")) {
				ledgerDao.updatePartyBalance(closingAmount, closingType,
						partyName);
			} else if (clType.equals("Credit")) {
				if (clBal.compareTo(closingAmount) == 1) {
					ledgerDao.updateCreditPartyBalance(closingAmount,
							closingTyp, partyName);
				} else {
					ledgerDao.updateCrPartyBalance(closingAmount, closingType,
							partyName);
				}
			}

			if (sales.getSalesType().equalsIgnoreCase("Sales")) {

				// Auto journal entry for sales
				jrnl = new Journal();
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Sales");
				jrnl.setTransactionId("S" + sales.getSalesId()); // New Column
																	// added for
																	// tracking
																	// journal
																	// entries
				jrnl.setJournalDate(sales.getSalesDate());

				// Set Account group code as Debit code
				ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
				jrnl.setDebitAccountName(partyName);

				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao
						.getLedgerAccountCode("Sales Account");
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName("Sales Account");

				jrnl.setDebitAmount(closingAmount);
				jrnl.setCreditAmount(closingAmount);
				jrnl.setNarration("Bill No " + sales.getSalesTypeId());
				journalDao.insertJournal((Journal) jrnl);

				// *** SAVING SCHEME Amount Party Balance Update Closing balance
				// = Current ledger balance - (savings scheme Amount) ***/

				List<Ledger> ledgerListParty = ledgerDao.searchLedger(sales
						.getCustomerName());
				String clTypeParty = ledgerListParty.get(0)
						.getClosingTotalType();
				BigDecimal ClBalanceParty = ledgerListParty.get(0)
						.getClosingTotalBalance();

				String finalClType = "";

				if (clTypeParty.equalsIgnoreCase("Credit")) {
					ClBalanceParty = ZERO.subtract(ClBalanceParty);
				}

				BigDecimal finalClBalanceParty = ClBalanceParty.subtract(sales
						.getSSCardAmount());

				if (finalClBalanceParty.signum() == -1) {
					finalClType = "Credit";
					finalClBalanceParty = finalClBalanceParty.multiply(CONVERT);
				} else {
					finalClType = "Debit";
				}

				ledgerDao.updateLedgerPartyBalance(finalClBalanceParty,
						finalClType, sales.getCustomerName());

				// Update Sales Account
				ledgername = "Sales Account";
				List<Ledger> salesList = ledgerDao.searchLedger(ledgername);

				clBal = salesList.get(0).getClosingTotalBalance();
				clType = salesList.get(0).getClosingTotalType();

				if (clType.equals("Credit")) {
					ledgerDao.updatePartyBalance(closingAmount, closingTyp,
							ledgername);
				} else {
					if (clBal.compareTo(closingAmount) == 1) {
						ledgerDao.updateCreditPartyBalance(closingAmount,
								closingType, ledgername);
					} else {
						ledgerDao.updateCrPartyBalance(closingAmount,
								closingTyp, ledgername);
					}
				}
			}
			// ************************ JOURNAL AND LEDGER UPDATION FOR CASH
			// *************************************

			if (cashPayment != null) {

				if (cashPayment.equals("Cash"))// If the payment mode is cash
				{

					// update party balance
					List<Ledger> custList = ledgerDao.searchLedger(partyName);

					clBal = custList.get(0).getClosingTotalBalance();
					clType = custList.get(0).getClosingTotalType();

					if (clType.equals("Debit")) {
						if (clBal.compareTo(cashAmt) == 1) {
							ledgerDao.updateCreditPartyBalance(cashAmt,
									closingType, partyName);
						} else {
							ledgerDao.updateCrPartyBalance(cashAmt, closingTyp,
									partyName);
						}
					} else if (clType.equals("Credit")) {
						ledgerDao.updatePartyBalance(cashAmt, closingTyp,
								partyName);
					}

					if (sales.getSalesType().equalsIgnoreCase("Sales")) {
						// BigDecimal totalCash = cashAmt.add(exchangeAmount); -
						// Ayaz

						jrnl = new Journal();
						jrnl.setJournalNO(getJournalNumber(jrnl));
						jrnl.setJournalType("Sales Cash Receipt");
						jrnl.setTransactionId("S" + sales.getSalesId()); // New
																			// Column
																			// added
																			// for
																			// tracking
																			// journal
																			// entries
						jrnl.setJournalDate(sales.getSalesDate());

						// Set Account group code as Debit code
						ledgerGroupCode = ledgerDao
								.getLedgerAccountCode("Cash Account");
						jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
						jrnl.setDebitAccountName("Cash Account");

						// Set Account group code as Credit code
						ledgerGroupCode = ledgerDao
								.getLedgerAccountCode(partyName);
						jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
						jrnl.setCreditAccountName(partyName);

						jrnl.setDebitAmount(cashAmt);
						jrnl.setCreditAmount(cashAmt);
						jrnl.setNarration("Bill No " + sales.getSalesTypeId());
						journalDao.insertJournal((Journal) jrnl);

						// update cash balance
						ledgername = "Cash Account";

						List<Ledger> ledgerList = ledgerDao
								.searchLedger(ledgername);

						clBal = ledgerList.get(0).getClosingTotalBalance();
						clType = ledgerList.get(0).getClosingTotalType();

						if (clType.equals("Debit")) {
							ledgerDao.updatePartyBalance(cashAmt, closingType,
									ledgername);
						} else if (clType.equals("Credit")) {
							if (clBal.compareTo(closingAmount) == 1) {
								ledgerDao.updateCreditPartyBalance(cashAmt,
										closingTyp, ledgername);
							} else {
								ledgerDao.updateCrPartyBalance(cashAmt,
										closingType, ledgername);
							}
						}

					}
				}
			}
			if (sales.getSalesType().equalsIgnoreCase("Sales")) {

				// ************************ JOURNAL AND LEDGER UPDATION FOR
				// CHEQUE *************************************
				if (chequePayment != null) {
					if (chequePayment.equals("Cheque"))// If the payment mode is
														// Cheque
					{
						// Auto Receipt For Cheque Amount
						jrnl = new Journal();
						jrnl.setJournalNO(getJournalNumber(jrnl));
						jrnl.setJournalType("Sales Cheque Receipt");
						jrnl.setTransactionId("S" + sales.getSalesId()); // New
																			// Column
																			// added
																			// for
																			// tracking
																			// journal
																			// entries
						jrnl.setJournalDate(sales.getSalesDate());

						// Set Account group code as Debit code
						ledgerGroupCode = ledgerDao
								.getLedgerAccountCode(chequeList);
						jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
						jrnl.setDebitAccountName(chequeList);

						// Set Account group code as Credit code
						ledgerGroupCode = ledgerDao
								.getLedgerAccountCode(partyName);
						jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
						jrnl.setCreditAccountName(partyName);

						jrnl.setDebitAmount(chequeAmt);
						jrnl.setCreditAmount(chequeAmt);
						jrnl.setNarration("Bill No " + sales.getSalesTypeId());
						journalDao.insertJournal((Journal) jrnl);

						// update Bank Account

						List<Ledger> ledgerList = ledgerDao
								.searchLedger(chequeList);

						clBal = ledgerList.get(0).getClosingTotalBalance();
						clType = ledgerList.get(0).getClosingTotalType();

						if (clType.equals("Debit")) {
							ledgerDao.updatePartyBalance(chequeAmt,
									closingType, chequeList);
						} else if (clType.equals("Credit")) {
							if (clBal.compareTo(chequeAmt) == 1) {
								ledgerDao.updateCreditPartyBalance(chequeAmt,
										closingTyp, chequeList);
							} else {
								ledgerDao.updateCrPartyBalance(chequeAmt,
										closingType, chequeList);
							}
						}

						// update party balance
						List<Ledger> custList = ledgerDao
								.searchLedger(partyName);

						clBal = custList.get(0).getClosingTotalBalance();
						clType = custList.get(0).getClosingTotalType();

						if (clType.equals("Debit")) {
							if (clBal.compareTo(chequeAmt) == 1) {
								ledgerDao.updateCreditPartyBalance(chequeAmt,
										closingType, partyName);
							} else {
								ledgerDao.updateCrPartyBalance(chequeAmt,
										closingTyp, partyName);
							}
						} else if (clType.equals("Credit")) {
							ledgerDao.updatePartyBalance(chequeAmt, closingTyp,
									partyName);
						}
					}
				}

				// ************************ JOURNAL AND LEDGER UPDATION FOR CARD
				// *************************************
				if (cardPayment != null) {
					if (cardPayment.equals("Card"))// If the payment mode is
													// Card
					{
						// Auto Receipt For Card Amount
						jrnl = new Journal();
						jrnl.setJournalNO(getJournalNumber(jrnl));
						jrnl.setJournalType("Sales Card Receipt");
						jrnl.setTransactionId("S" + sales.getSalesId()); // New
																			// Column
																			// added
																			// for
																			// tracking
																			// journal
																			// entries
						jrnl.setJournalDate(sales.getSalesDate());

						// Set Account group code as Debit code
						ledgerGroupCode = ledgerDao.getLedgerAccountCode(sales
								.getCardBank());
						jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
						jrnl.setDebitAccountName(sales.getCardBank());

						// Set Account group code as Credit code
						ledgerGroupCode = ledgerDao
								.getLedgerAccountCode(partyName);
						jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
						jrnl.setCreditAccountName(partyName);

						jrnl.setDebitAmount(cardAmt);
						jrnl.setCreditAmount(cardAmt);
						jrnl.setNarration("Bill No " + sales.getSalesTypeId());
						journalDao.insertJournal((Journal) jrnl);

						// update Bank Account

						List<Ledger> ledgerList = ledgerDao
								.searchLedger(cardList);

						clBal = ledgerList.get(0).getClosingTotalBalance();
						clType = ledgerList.get(0).getClosingTotalType();

						if (clType.equals("Debit")) {
							ledgerDao.updatePartyBalance(cardAmt, closingType,
									cardList);
						} else if (clType.equals("Credit")) {
							if (clBal.compareTo(cardAmt) == 1) {
								ledgerDao.updateCreditPartyBalance(cardAmt,
										closingTyp, cardList);
							} else {
								ledgerDao.updateCrPartyBalance(cardAmt,
										closingType, cardList);
							}
						}

						// update party balance
						List<Ledger> custList = ledgerDao
								.searchLedger(partyName);

						clBal = custList.get(0).getClosingTotalBalance();
						clType = custList.get(0).getClosingTotalType();

						if (clType.equals("Debit")) {
							if (clBal.compareTo(cardAmt) == 1) {
								ledgerDao.updateCreditPartyBalance(cardAmt,
										closingType, partyName);
							} else {
								ledgerDao.updateCrPartyBalance(cardAmt,
										closingTyp, partyName);
							}
						} else if (clType.equals("Credit")) {
							ledgerDao.updatePartyBalance(cardAmt, closingTyp,
									partyName);
						}

						if (sales.getCommissionAmount() != null
								&& sales.getCommissionAmount().signum() > 0) {
							// Auto Receipt For Comission Amount
							jrnl = new Journal();
							jrnl.setJournalNO(getJournalNumber(jrnl));
							jrnl.setJournalType("S.Journal");
							jrnl.setTransactionId("S" + sales.getSalesId()); // New
																				// Column
																				// added
																				// for
																				// tracking
																				// journal
																				// entries
							jrnl.setJournalDate(sales.getSalesDate());

							// Set Account group code as Debit code
							ledgerGroupCode = ledgerDao
									.getLedgerAccountCode(partyName);
							jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
							jrnl.setDebitAccountName(sales.getCustomerName());

							// Set Account group code as Credit code
							ledgerGroupCode = ledgerDao
									.getLedgerAccountCode("Card Commission from customer");
							jrnl.setCreditCode(ledgerGroupCode.get(0)
									.toString());
							jrnl.setCreditAccountName("Card Commission from customer");

							jrnl.setDebitAmount(sales.getCommissionAmount());
							jrnl.setCreditAmount(sales.getCommissionAmount());
							jrnl.setNarration("Bill No "
									+ sales.getSalesTypeId());
							journalDao.insertJournal((Journal) jrnl);

							// update party balance
							List<Ledger> ledger = ledgerDao
									.searchLedger(partyName);
							String typeSales = ledger.get(0)
									.getClosingTotalType();
							BigDecimal oldPartyBal = ledger.get(0)
									.getClosingTotalBalance();

							if (typeSales.equals("Credit")) {
								oldPartyBal = ZERO.subtract(oldPartyBal);
							}

							BigDecimal finalClcashPartyBal = oldPartyBal
									.add(sales.getCommissionAmount());

							if (finalClcashPartyBal.signum() >= 0) {
								ledgerDao.updateLedgerPartyBalance(
										finalClcashPartyBal, "Debit",
										sales.getCustomerName());
							} else {
								finalClcashPartyBal = finalClcashPartyBal
										.multiply(CONVERT);
								ledgerDao.updateLedgerPartyBalance(
										finalClcashPartyBal, "Credit",
										sales.getCustomerName());
							}

							// *************** Card Commission Account Ledger
							// update

							List<Ledger> ledName = ledgerDao
									.searchLedger("Card Commission from customer");
							String salTypes3 = ledName.get(0)
									.getClosingTotalType();
							BigDecimal oldBalCash = ledName.get(0)
									.getClosingTotalBalance();

							if (salTypes3.equals("Credit")) {
								oldBalCash = ZERO.subtract(oldBalCash);
							}

							BigDecimal finalClCardActBal = oldBalCash
									.subtract(sales.getCommissionAmount());

							if (finalClCardActBal.signum() >= 0) {
								ledgerDao.updateLedgerPartyBalance(
										finalClCardActBal, "Debit",
										"Card Commission from customer");
							} else {
								finalClCardActBal = finalClCardActBal
										.multiply(CONVERT);
								ledgerDao.updateLedgerPartyBalance(
										finalClCardActBal, "Credit",
										"Card Commission from customer");
							}

						}// end condition of commission amount !=null

					}

				}

				// ************************ JOURNAL AND LEDGER UPDATION FOR
				// VOUCHER *************************************
				if (voucherPayment != null) {
					if (voucherPayment.equals("Voucher"))// If the payment mode
															// is Voucher
					{
						// Auto Receipt For Voucher Amount
						jrnl = new Journal();
						jrnl.setJournalNO(getJournalNumber(jrnl));
						jrnl.setJournalType("Sales Voucher Receipt");
						jrnl.setTransactionId("S" + sales.getSalesId()); // New
																			// Column
																			// added
																			// for
																			// tracking
																			// journal
																			// entries
						jrnl.setJournalDate(sales.getSalesDate());

						// Set Account group code as Debit code
						ledgerGroupCode = ledgerDao
								.getLedgerAccountCode(voucherList);
						jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
						jrnl.setDebitAccountName(voucherList);

						// Set Account group code as Credit code
						ledgerGroupCode = ledgerDao
								.getLedgerAccountCode(partyName);
						jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
						jrnl.setCreditAccountName(partyName);

						jrnl.setDebitAmount(voucherAmt);
						jrnl.setCreditAmount(voucherAmt);
						jrnl.setNarration("Bill No " + sales.getSalesTypeId());
						journalDao.insertJournal((Journal) jrnl);

						// update Bank Account

						List<Ledger> ledgerList = ledgerDao
								.searchLedger(voucherList);

						clBal = ledgerList.get(0).getClosingTotalBalance();
						clType = ledgerList.get(0).getClosingTotalType();

						if (clType.equals("Debit")) {
							ledgerDao.updatePartyBalance(voucherAmt,
									closingType, voucherList);
						} else if (clType.equals("Credit")) {
							if (clBal.compareTo(voucherAmt) == 1) {
								ledgerDao.updateCreditPartyBalance(voucherAmt,
										closingTyp, voucherList);
							} else {
								ledgerDao.updateCrPartyBalance(voucherAmt,
										closingType, voucherList);
							}
						}

						// update party balance
						List<Ledger> custList = ledgerDao
								.searchLedger(partyName);

						clBal = custList.get(0).getClosingTotalBalance();
						clType = custList.get(0).getClosingTotalType();

						if (clType.equals("Debit")) {
							if (clBal.compareTo(voucherAmt) == 1) {
								ledgerDao.updateCreditPartyBalance(voucherAmt,
										closingType, partyName);
							} else {
								ledgerDao.updateCrPartyBalance(voucherAmt,
										closingTyp, partyName);
							}
						} else if (clType.equals("Credit")) {
							ledgerDao.updatePartyBalance(voucherAmt,
									closingTyp, partyName);
						}
					}
				}
			}// for non-walkin customer
		}
		model.addAttribute("invc", sales.getSalesTypeId());
		List<CompanyInfo> companyList = companyInfoDao.listCompanyInfo();
		InvoiceBillFormat orninoviceObj = new InvoiceTextFormatPrint();
		try{
			if (!companyList.isEmpty()) { // if Printer Format set in Company Info
				if (companyList.get(0).getOrnInvoicePrint()
						.equalsIgnoreCase("dotMatrix8")) {
					orninoviceObj.printinvoice(sales, companyInfoDao, ledgerDao,
							request);
					status.setComplete();
					return new ModelAndView("redirect:newSales.htm", model);
				} else if (companyList.get(0).getOrnInvoicePrint()
						.equalsIgnoreCase("dotMatrix4")) {
					orninoviceObj.printFourInchInvoice(sales, companyInfoDao,
							ledgerDao, request);
					status.setComplete();
					return new ModelAndView("redirect:newSales.htm", model);
				}
			}
		}catch(NullPointerException npe){
			System.out.println("Please Update Company info Printer Settings");
		}
		
		status.setComplete();
		return new ModelAndView("redirect:invoicePreview.htm", model);
	}

	@RequestMapping(value = "/invoicePreview.htm", method = RequestMethod.GET)
	public ModelAndView showinvoice() {
		return new ModelAndView("salePreview");
	}

	/* for AJAX ItemCode details */
	@RequestMapping(value = "/Sales", method = RequestMethod.POST)
	public @ResponseBody
	String getRequest(@RequestParam(value = "name", required = false) String id) {

		// model.addAttribute("itemDetail",
		itemmasterDao.searchItemMaster(id);
		return "gg";
	}

	@RequestMapping(value = "/exformpurchase.htm", method = RequestMethod.POST)
	public @ResponseBody
	String exchangePurchase(@ModelAttribute("purchase") Purchase purchase,
			BindingResult result, SessionStatus status, Model model,
			HttpServletRequest request) {

		model.addAttribute("suppliername", ledgerDao.listLedgerName());

		BigDecimal clBal;
		BigDecimal itemgrosswt;
		BigDecimal itemnetwt;
		Integer noqty;
		Integer ppq;
		String ledgername;
		String clType;

		ItemMaster itemDetails = new ItemMaster();

		String closingType = "Debit";
		String closingTyp = "Credit";
		String itemcd = purchase.getItemCode();
		String partyName = purchase.getSupplierName();
		BigDecimal closingAmount = purchase.getTotalAmount();
		String ptype = purchase.getPurchaseType();
		BigDecimal grosswt = purchase.getGrossWeight();
		Integer nos = purchase.getNumberOfPieces();
		String payment_mode = purchase.getPaymentMode();

		// Insert Record in Purchase
		String INVOICE_NO = generatePurchaseInvoiceNO(purchase);
		purchase.setPurchseInvoice(INVOICE_NO);
		purchaseDao.insertPurchase(purchase);
		status.setComplete();

		// To update Stock
		List<ItemMaster> itemList = itemmasterDao.searchItemMaster(itemcd);

		for (int i = 0; i < itemList.size(); i++) {
			ItemMaster imast = (ItemMaster) itemList.get(i);
			if (imast instanceof ItemMaster) {

				itemDetails = (ItemMaster) imast;
				itemgrosswt = itemDetails.getGrossWeight();
				itemnetwt = itemDetails.getNetWeight();
				noqty = itemDetails.getQty();
				ppq = itemDetails.getPiecesPerQty();

				if (ptype.compareTo("Purchase") == 0) {
					noqty = noqty + nos;
					itemgrosswt = itemgrosswt.add(grosswt);
					itemnetwt = itemnetwt.add(grosswt);
				} else {
					noqty = noqty - nos;
					itemgrosswt = itemgrosswt.subtract(grosswt);
					itemnetwt = itemnetwt.subtract(grosswt);
				}

				// tp = ppq * noqty;
				itemDetails.setGrossWeight(itemgrosswt);
				itemDetails.setNetWeight(itemnetwt);
				itemDetails.setQty(noqty);
				itemDetails.setPiecesPerQty(ppq);
				itemDetails.setTotalGrossWeight(itemgrosswt);
				itemmasterDao.updateItemMaster(itemDetails);// Update Item
															// Master

				// Update Stock Entry In Jewel Stock

				JewelStock jewelStockObj = new JewelStock();

				jewelStockObj.setStock_TransType("Purchase");
				jewelStockObj.setStock_StockType("Purchase Stock");
				jewelStockObj.setStock_TransNO(purchase.getPurchseInvoice());
				jewelStockObj.setStock_TransDate(purchase.getPurchaseDate());
				jewelStockObj.setStock_ItemCode(purchase.getItemCode());
				jewelStockObj.setStock_CategoryName(purchase.getItemName());
				jewelStockObj.setStock_SubCategoryName(purchase.getItemName());
				jewelStockObj.setStock_ItemName(purchase.getItemName());
				jewelStockObj.setStock_MetalType(purchase.getBullionType());
				jewelStockObj.setStock_CLQty(purchase.getNumberOfPieces());
				jewelStockObj.setStock_CLTotalPieces(purchase
						.getNumberOfPieces());
				jewelStockObj.setStock_CLGrossWeight(grosswt);
				jewelStockObj.setStock_CLNetWeight(purchase.getNetWeight());
				jewelStockObj.setStock_CLTotalGrossWeight(purchase
						.getGrossWeight());
				jewelStockObj.setStock_BullionRate(purchase.getRate());
				jewelStockDao.saveJewelStock(jewelStockObj);
			}
		}

		if (ptype.equals("Purchase")) {
			if (partyName.equals("Walk-in")) {
				// Auto Journal entry for purchase
				jrnl = new Journal();
				jrnl.setJournalNO(getJournalNumber(jrnl));
				jrnl.setJournalType("Purchase");
				jrnl.setJournalDate(purchase.getPurchaseDate());

				// Set Account group code as Debit code
				ledgerGroupCode = ledgerDao
						.getLedgerAccountCode("Purchase Account");
				jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
				jrnl.setDebitAccountName("Purchase Account");

				jrnl.setTransactionId("P" + purchase.getPurchaseId().toString());

				// Set Account group code as Credit code
				ledgerGroupCode = ledgerDao
						.getLedgerAccountCode("Cash Account");
				jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
				jrnl.setCreditAccountName("Cash Account");

				jrnl.setDebitAmount(closingAmount);
				jrnl.setCreditAmount(closingAmount);
				jrnl.setNarration("Bill No " + purchase.getPurchaseId());
				journalDao.insertJournal((Journal) jrnl);

				// Update Purchase Account
				ledgername = "Purchase Account";
				List<Ledger> purchaseList = ledgerDao.searchLedger(ledgername);

				clBal = purchaseList.get(0).getClosingTotalBalance();
				clType = purchaseList.get(0).getClosingTotalType();

				if (clType.equals("Debit")) {
					ledgerDao.updatePartyBalance(closingAmount, closingType,
							ledgername);
				} else {
					if (clBal.compareTo(closingAmount) == 1) {
						ledgerDao.updateCreditPartyBalance(closingAmount,
								closingTyp, ledgername);
					} else {
						ledgerDao.updateCrPartyBalance(closingAmount,
								closingType, ledgername);
					}
				}

				// update cash balance
				ledgername = "Cash Account";
				List<Ledger> ledgerList = ledgerDao.searchLedger(ledgername);

				clBal = ledgerList.get(0).getClosingTotalBalance();
				clType = ledgerList.get(0).getClosingTotalType();

				if (clType.equals("Credit")) {
					ledgerDao.updatePartyBalance(closingAmount, closingTyp,
							ledgername);
				} else {
					if (clBal.compareTo(closingAmount) == 1) {
						ledgerDao.updateCreditPartyBalance(closingAmount,
								closingType, ledgername);
					} else {
						ledgerDao.updateCrPartyBalance(closingAmount,
								closingTyp, ledgername);
					}
				}
			} else {// For Regular Customer

				if (payment_mode.equals("Cash")) {
					// Auto Journal Entry for Regular customer
					jrnl = new Journal();
					jrnl.setJournalNO(getJournalNumber(jrnl));
					jrnl.setJournalType("Purchase");
					jrnl.setJournalDate(purchase.getPurchaseDate());

					// Set Account group code as Debit code
					ledgerGroupCode = ledgerDao
							.getLedgerAccountCode("Purchase Account");
					jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
					jrnl.setDebitAccountName("Purchase Account");

					jrnl.setTransactionId("P"
							+ purchase.getPurchaseId().toString());

					// Set Account group code as Credit code
					ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
					jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					jrnl.setCreditAccountName(partyName);

					jrnl.setDebitAmount(closingAmount);
					jrnl.setCreditAmount(closingAmount);
					jrnl.setNarration("Bill No " + purchase.getPurchaseId());
					journalDao.insertJournal((Journal) jrnl);

					// Auto Journal Entry for Payment
					jrnl = new Journal();
					jrnl.setJournalNO(getJournalNumber(jrnl));
					jrnl.setJournalType("Payment");
					jrnl.setJournalDate(purchase.getPurchaseDate());
					jrnl.setTransactionId("P"
							+ purchase.getPurchaseId().toString());

					// Set Account group code as Debit code
					ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
					jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
					jrnl.setDebitAccountName(partyName);

					// Set Account group code as Credit code
					ledgerGroupCode = ledgerDao
							.getLedgerAccountCode("Cash Account");
					jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					jrnl.setCreditAccountName("Cash Account");

					jrnl.setDebitAmount(closingAmount);
					jrnl.setCreditAmount(closingAmount);
					jrnl.setNarration("Bill No " + purchase.getPurchaseId());
					journalDao.insertJournal((Journal) jrnl);

					// update cash balance
					ledgername = "Cash Account";
					List<Ledger> ledgerList = ledgerDao
							.searchLedger(ledgername);

					clBal = ledgerList.get(0).getClosingTotalBalance();
					clType = ledgerList.get(0).getClosingTotalType();

					if (clType.equals("Credit")) {
						ledgerDao.updatePartyBalance(closingAmount, closingTyp,
								ledgername);
					} else {
						if (clBal.compareTo(closingAmount) == 1) {
							ledgerDao.updateCreditPartyBalance(closingAmount,
									closingType, ledgername);
						} else {
							ledgerDao.updateCrPartyBalance(closingAmount,
									closingTyp, ledgername);
						}
					}

					// Update Purchase Account
					ledgername = "Purchase Account";
					List<Ledger> purchaseList = ledgerDao
							.searchLedger(ledgername);

					clBal = purchaseList.get(0).getClosingTotalBalance();
					clType = purchaseList.get(0).getClosingTotalType();

					if (clType.equals("Debit")) {
						ledgerDao.updatePartyBalance(closingAmount,
								closingType, ledgername);
					} else {
						if (clBal.compareTo(closingAmount) == 1) {
							ledgerDao.updateCreditPartyBalance(closingAmount,
									closingTyp, ledgername);
						} else {
							ledgerDao.updateCrPartyBalance(closingAmount,
									closingType, ledgername);
						}
					}
				} else { // For Payment mode is Credit

					// Auto Journal Entry for Regular customer
					jrnl = new Journal();
					jrnl.setJournalNO(getJournalNumber(jrnl));
					jrnl.setJournalType("Purchase");
					jrnl.setJournalDate(purchase.getPurchaseDate());

					// Set Account group code as Debit code
					ledgerGroupCode = ledgerDao
							.getLedgerAccountCode("Purchase Account");
					jrnl.setDebitCode(ledgerGroupCode.get(0).toString());
					jrnl.setDebitAccountName("Purchase Account");

					jrnl.setTransactionId("P"
							+ purchase.getPurchaseId().toString());

					// Set Account group code as Credit code
					ledgerGroupCode = ledgerDao.getLedgerAccountCode(partyName);
					jrnl.setCreditCode(ledgerGroupCode.get(0).toString());
					jrnl.setCreditAccountName(partyName);

					jrnl.setDebitAmount(closingAmount);
					jrnl.setCreditAmount(closingAmount);
					jrnl.setNarration("Bill No " + purchase.getPurchaseId());
					journalDao.insertJournal((Journal) jrnl);

					// Update Purchase Account

					ledgername = "Purchase Account";
					List<Ledger> purchaseList = ledgerDao
							.searchLedger(ledgername);

					clBal = purchaseList.get(0).getClosingTotalBalance();
					clType = purchaseList.get(0).getClosingTotalType();

					if (clType.equals("Debit")) {
						ledgerDao.updatePartyBalance(closingAmount,
								closingType, ledgername);
					} else {
						if (clBal.compareTo(closingAmount) == 1) {
							ledgerDao.updateCreditPartyBalance(closingAmount,
									closingTyp, ledgername);
						} else {
							ledgerDao.updateCrPartyBalance(closingAmount,
									closingType, ledgername);
						}
					}

					// update party balance
					List<Ledger> customer_List = ledgerDao
							.searchLedger(partyName);
					clBal = customer_List.get(0).getClosingTotalBalance();
					clType = customer_List.get(0).getClosingTotalType();

					if (clType.equals("Credit")) {
						ledgerDao.updatePartyBalance(closingAmount, closingTyp,
								partyName);
					} else {
						if (clBal.compareTo(closingAmount) == 1) {
							ledgerDao.updateCreditPartyBalance(closingAmount,
									closingType, partyName);
						} else {
							ledgerDao.updateCrPartyBalance(closingAmount,
									closingTyp, partyName);
						}
					}
				}// End of if for payment mode
			}// End of if condition for party name
		}

		// Print Option
		List<CompanyInfo> companyList = companyInfoDao.listCompanyInfo();
		InvoiceBillFormat orninoviceObj = new InvoiceTextFormatPrint();

		if (!companyList.isEmpty() && (purchase.getItemCode().equalsIgnoreCase("IT100005") || purchase.getItemCode().equalsIgnoreCase("IT100010")|| purchase.getBullionType().equalsIgnoreCase("Old Gold Coin") || purchase.getBullionType().equalsIgnoreCase("Old Silver Coin") || purchase.getBullionType().equalsIgnoreCase("Old Gold Pure Bullion") || purchase.getBullionType().equalsIgnoreCase("Old Silver Pure Bullion"))) 
		{ 
			// if Printer Format set in Company Info
			if (companyList.get(0).getOrnInvoicePrint().equalsIgnoreCase("dotMatrix4")) {
				if (request != null) {
					orninoviceObj.printFourInchOldPurchase(purchase, companyInfoDao, ledgerDao, request);
				}
			}
		}
		
		return INVOICE_NO;
	}

	private String getLastBillNo() {

		BigInteger found = salesDao.getLatestBillNo();
		String bill_NO = "OS000";
		String listBillNo = "OS" + found;
		String displayCode;

		if (found != null) {
			bill_NO = listBillNo;
		}

		String splitNo = bill_NO.substring(2);
		int num = Integer.parseInt(splitNo);
		num++;
		String number = Integer.toString(num);
		displayCode = bill_NO.substring(0, 2) + number;
		return displayCode;
	}

	public String getSalesTypeId(Sales sales) {
		String displayCode;
		String listBillNo;
		String bill_NO;
		BigInteger found = null;

		if (sales.getSalesType().equalsIgnoreCase("Estimate Sales")) {
			found = salesDao.getLastEstimSalesNo();
			listBillNo = "ES" + found;
			bill_NO = "ES0";
		} else if (sales.getBullionType().equalsIgnoreCase("Gold")) {
			found = salesDao.getLastGoldTypeNo();
			listBillNo = "GS" + found;
			bill_NO = "GS0";
		} else {
			found = salesDao.getLastSilverTypeNo();
			listBillNo = "SS" + found;
			bill_NO = "SS0";
		}

		if (found != null) {
			bill_NO = listBillNo;
		}
		String splitNo = bill_NO.substring(2);
		int num = Integer.parseInt(splitNo);
		num++;
		String number = Integer.toString(num);
		displayCode = bill_NO.substring(0, 2) + number;
		return displayCode;

	}

	/** For Customer Name Pop Up validation on 4-12-12 **/
	@RequestMapping(value = "CusValidate.htm", method = RequestMethod.GET)
	public @ResponseBody
	String checkCustomer(@RequestParam("custName") String custName) { // System.out.println("inside popup validation::::::");
		String found = "false";
		try {
			List<Ledger> custNameList = ledgerDao.listCustomerPan(custName);

			if (!custNameList.isEmpty()) {
				found = "true";
			} else {
				found = "false";
			}
		} catch (Exception e) {
		}
		return found;
	}

	// Adding new Supplier request mapping POPUP window 4-12-12
	@RequestMapping(value = "/customerPOPUP.htm", method = RequestMethod.POST)
	public @ResponseBody
	void newCustomer(@RequestParam("custName") String custName,
			@RequestParam("openingBal") String openingBal,
			@RequestParam("address") String address,
			@RequestParam("openingType") String openingType) {
		// System.out.println("Inside popup save:::::");
		if (custName.length() == 0) {
			custName = "";
		}
		if (openingBal.length() == 0) {
			openingBal = "0.00";
		}
		if (openingType.length() == 0) {
			openingType = "";
		}
		if (address.length() == 0) {
			address = "";
		}

		Ledger ledger = new Ledger();
		try {
			ledger.setLedgerName(custName);
			ledger.setOpeningBalance(new BigDecimal(openingBal.trim()));
			ledger.setOpTotalBalance(new BigDecimal(openingBal.trim()));
			ledger.setClosingTotalBalance(new BigDecimal(openingBal.trim()));
			ledger.setOpeningType(openingType);
			ledger.setLedgerDate(ledger.getLedgerDate());
			ledger.setClosingTotalType(openingType);
			ledger.setOpTotalType(openingType);
			ledger.setAddress1(address);
			ledger.setAccountGroup("Sundry Debtors");
			ledger.setAccountGroupCode("A118");
			ledger.setCast("Unknown");
			ledger.setTypeOfSource("Friend");
			Accounts acct = accountsDao.getAccounts(18);
			acct.getLedgers().add(ledger);
			accountsDao.insertAccounts(acct);
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {

		}

	}

	// Ajax code for Sales Return ID customer name add on 14-02-13
	@RequestMapping(value = "/SalesReturnID_Customername.htm", method = RequestMethod.GET)
	public @ResponseBody
	String GetSalesReturnID(
			@RequestParam(value = "custSRId", required = false) String customerName) {

		String ID = "";

		try {

			List<Sales> listID = salesDao.getSalesReturnId(customerName);
			if (!listID.isEmpty()) {
				ArrayList<String> SRId = new ArrayList<String>();
				SRId.add(0, "0");
				SRId.add(1, listID.toString());
				ID = SRId.toString();
			}
		} catch (Exception e) {

		}

		// System.out.println("Sales Return ID in ajax call:::"+ID);
		return ID;
	}

	// ajax code for Sales Return Amount added on 14-02-13
	@RequestMapping(value = "/SalesReturn_Amt.htm", method = RequestMethod.GET)
	public @ResponseBody
	String getSRamt(@RequestParam(value = "name", required = true) String id,
			Sales saleobj) {

		String i = id;
		String amt = "0";
		if (i != "") {
			List<Sales> result = salesDao.getSRAmount(i);
			if (!result.isEmpty()) {
				amt = result.get(0).getBillAmount().toString();
			}
		}

		return amt;
	}

}
