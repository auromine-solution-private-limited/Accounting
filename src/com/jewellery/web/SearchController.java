package com.jewellery.web;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jewellery.dao.AccountsDao;
import com.jewellery.dao.CategoryDao;
import com.jewellery.dao.ItemMasterDao;
import com.jewellery.dao.JobOrderDao;
import com.jewellery.dao.LedgerDao;
import com.jewellery.dao.SalesOrderDao;
import com.jewellery.entity.Accounts;
import com.jewellery.entity.Category;
import com.jewellery.entity.ItemMaster;
import com.jewellery.entity.JobOrder;
import com.jewellery.entity.Ledger;
import com.jewellery.entity.SalesOrder;

@Controller
public class SearchController{

@Autowired
private CategoryDao categoryDao;
@Autowired
private LedgerDao ledgerDao;
@Autowired
private SalesOrderDao salesorderDao;
@Autowired
private JobOrderDao joborderDao;
@Autowired
private ItemMasterDao itemmasterDao;
@Autowired
private AccountsDao accountsDao;

	public SearchController(){	
		//System.out.println("Search Accounts Controller::::::::::::::::::::::::::::");
	}		
	
	
	@RequestMapping(value="/searchledger.htm",method=RequestMethod.GET,params="search")
	public ModelAndView secondButton(@ModelAttribute("Ledger")Ledger ledger,Model model){

		String ledgerName = ledger.getLedgerName();		
		List<Ledger> accounts = ledgerDao.searchLedgerAccount(ledgerName);
		if(!accounts.isEmpty()){
		String accountGroup = accounts.get(0).getAccountGroup();
		List<Accounts> accountId = accountsDao.getAccountId(accountGroup);
		model.addAttribute("accId", accountId.get(0).getaccountId());		
			model.addAttribute("ledgerList", accounts);
		}
			return new ModelAndView("searchledger");
	}
	
	//Ajax autoComplete code for ledgerName Search.
	@RequestMapping(value="/ledgerName_Auto.htm",method = RequestMethod.GET)
	public @ResponseBody String getLedgerNames(@RequestParam(value="lNamePart",required=true)String lNamePart){
		
		List<String> lNames = ledgerDao.getAutoLedgerNames(lNamePart);
		String Names = "";
		try{
			Names = lNames.toString();
		}catch(java.lang.IndexOutOfBoundsException e)
		{
			
		}
		return Names;
	}
	
	//Ajax autoComplete code for itemName Search.
	@RequestMapping(value="/itemName_Auto.htm",method = RequestMethod.GET)
	public @ResponseBody String getItemNames(@RequestParam(value="iNamePart",required=true)String iNamePart){
		
		List<String> iNames = itemmasterDao.getAutoItemName(iNamePart);
		String itemNames = "";
		try{
			itemNames = iNames.toString();
		}catch(java.lang.IndexOutOfBoundsException e)
		{
			
		}
		return itemNames;
	}

	//Ajax autoComplete code for jobOrderNo - smithName Search.
		@RequestMapping(value="/smithName_Auto.htm",method = RequestMethod.GET)
		public @ResponseBody String getsmithNames(@RequestParam(value="sNamePart",required=true)String sNamePart){
			
			List<String> sNames = joborderDao.getAutoSmithName(sNamePart);
			
			String itemNames = "";
			try{
				itemNames = sNames.toString();
			}catch(java.lang.IndexOutOfBoundsException e)
			{
				
			}
			return itemNames;
		}
	
	//Redirect the form to salesOrder
	@RequestMapping(value ="/searchorder.htm", method = RequestMethod.GET, params="search2")
	public ModelAndView showOrderForm(@ModelAttribute("Orderno") SalesOrder salesorder, Model model) 
	{		
		Integer orderNo = salesorder.getSalesOrderId();		
		model.addAttribute("salesOrderList", salesorderDao.searchSalesOrder(orderNo));				
		return new ModelAndView ("searchorder");
	}
	
	//salesorderjob search method
	@RequestMapping(value ="/searchsmith.htm", method = RequestMethod.GET, params="search3")
	public ModelAndView jobOrderForm(@ModelAttribute("jos")JobOrder joborder, Model model) 
	{	
		String smithName=joborder.getSmithName();
		List<JobOrder> joborders = joborderDao.searchSmith(smithName);	
		model.addAttribute("jobOrderList",joborders );
		return new ModelAndView ("searchsmith");
	}
	
	//search Item method
	@RequestMapping(value ="/searchitem.htm", method = RequestMethod.GET, params="search4")
	public ModelAndView itemForm(@ModelAttribute("ItSearch")ItemMaster Itemmaster, Model model) 
	{		
		String itemName=Itemmaster.getItemName();
		//String itemCode=Itemmaster.getItemCode();
		BigDecimal netWeight=Itemmaster.getNetWeight();
		
		List<ItemMaster> items = itemmasterDao.searchItemName(itemName, netWeight);
		
		if(!items.isEmpty())
		{
			String categoryName = items.get(0).getSubCategoryName();
			List<Category> categoryId=categoryDao.searchExistingCategory(categoryName);		
			model.addAttribute("category", categoryId);		
			model.addAttribute("itemMasterList", items);
		}
	
		return new ModelAndView ("searchitem");
	}	
	
}