package com.jewellery.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
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

import com.jewellery.dao.ManualBillingDao;
import com.jewellery.entity.ManBillRowList;
import com.jewellery.entity.ManBillWrapper;
import com.jewellery.entity.ManualBilling;
import com.jewellery.util.JournalCode;
import com.jewellery.validator.ManualBillingValidator;

@Controller
public class FormManualBillingController extends JournalCode{
	
	@Autowired
	private ManualBillingDao manualBillingDao;
	@Autowired
	private ManualBillingValidator manualBilingValidator;	
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,ServletRequestDataBinder binder) throws Exception {
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
		binder.registerCustomEditor(Date.class, null, dateEditor);
	}
	
	/** Show Manual Billing Form **/
	@RequestMapping(value="/newManualSales.htm",method=RequestMethod.GET)	
	public ModelAndView newMBForm(@ModelAttribute("ManBillWrapper")ManBillWrapper manBillWrapper,ModelMap model)
	{		
		return new ModelAndView("FormManualBilling",model);			
	}	
	/** Show Manual Billing Form **/
	@RequestMapping(value="/newManualSalesVF.htm",method=RequestMethod.GET)	
	public ModelAndView newMBVFForm(@ModelAttribute("ManBillWrapper")ManBillWrapper manBillWrapper,ModelMap model)
	{		
		return new ModelAndView("FormManualBilling",model);			
	}	
	
	/** For View Manual Billing Form ***/
	@RequestMapping(value="/viewManualSales.htm",method=RequestMethod.GET)
	public ModelAndView viewMBForm(@ModelAttribute("ManBillWrapper") ManBillWrapper manBillWrapper,@RequestParam(value="mBillId",required=false)Integer mBillId,ModelMap model){
		
		ManualBilling manualBilling = manualBillingDao.getManualBilling(mBillId);
		List<ManBillRowList> manBillRowList = manualBillingDao.listManualBilling(mBillId);
		
		
		manBillWrapper.setManBill(manualBilling);
		manBillWrapper.setManBillRowList(manBillRowList);
		
		List<ManBillRowList> manJspRowList = manualBillingDao.listManualBilling(mBillId);		
		ManBillRowList manRowListTemp = manJspRowList.get(manJspRowList.size() - 1); // to remove static row from dynamic row
		manJspRowList.remove(manRowListTemp);
		
		model.addAttribute("ManBillRowList", manJspRowList);
		model.addAttribute(manBillWrapper);
		return new ModelAndView("FormManualBilling", model);
	}
	
	/** For Insert Manual Billing Form ***/
	@RequestMapping(value = "/formManualSales.htm", method = RequestMethod.POST, params = "insert")
	public ModelAndView saveMBForm(@ModelAttribute("ManBillWrapper") ManBillWrapper manBillWrapper,BindingResult result, ModelMap model, SessionStatus status) 
	{
		manualBilingValidator.validate(manBillWrapper.getManBill(), manBillWrapper.getManBillRowList(), result);
		
		List<ManBillRowList> listMBillNew = manBillWrapper.getManBillRowList();
		
		/** Deleting Blank row */ 	
		Iterator<ManBillRowList> iter = listMBillNew.iterator();	
		while(iter.hasNext()) {	
			ManBillRowList MBRow = iter.next();
			if((MBRow.getDescription() == null || MBRow.getDescription().length() == 0 ) && (MBRow.getQty() == 0) && (MBRow.getGrossWeight() == null || MBRow.getGrossWeight().signum() == 0 ) && (MBRow.getAmount() == null || MBRow.getAmount().signum() == 0 )){
				iter.remove();
				listMBillNew.remove(MBRow);
				manBillWrapper.setManBillRowList(listMBillNew);
			}
		}
		
		if(result.hasErrors()){
			model.addAttribute("errorType", "insertError");
			model.addAttribute("ManRowList", manBillWrapper.getManBillRowList());
			return new ModelAndView("FormManualBilling", model);
		}
		
		String mBillFormType = manBillWrapper.getManBill().getmBillFormType();
		manBillWrapper.getManBill().setmBillAutoNO(generateManualBillingNO(mBillFormType));
		manualBillingDao.insertManualBilling(manBillWrapper.getManBill());
		List<ManBillRowList> listMBill = manBillWrapper.getManBillRowList();
		
		for(ManBillRowList objMBill: listMBill) {
			objMBill.setManualBilling(manBillWrapper.getManBill());
			manualBillingDao.insertManBillRowList(objMBill);
		}
		return new ModelAndView("redirect:manualSalesList.htm");
	}
	
	/** For Update Manual Billing Form ***/
	@RequestMapping(value = "/formManualSales.htm", method = RequestMethod.POST, params = "update")
	public ModelAndView updateMBForm(@ModelAttribute("ManBillWrapper") ManBillWrapper manBillWrapper,BindingResult result, ModelMap model, SessionStatus status) 
	{
		manualBilingValidator.validate(manBillWrapper.getManBill(), manBillWrapper.getManBillRowList(), result);
		
		if(result.hasErrors()){
			model.addAttribute("errorType", "updateError");
			model.addAttribute("ManRowList", manBillWrapper.getManBillRowList());
			return new ModelAndView("FormManualBilling", model);
		}
		
		List<ManBillRowList> listMBillNew = manBillWrapper.getManBillRowList();
		List<ManBillRowList> listMBillOld = manualBillingDao.listManualBilling(manBillWrapper.getManBill().getmBillId());
				
		/** Deleting Blank row */ 	
		Iterator<ManBillRowList> iter = listMBillNew.iterator();	
		while(iter.hasNext()) {	
			ManBillRowList MBRow = iter.next();
			if((MBRow.getDescription() == null || MBRow.getDescription().length() == 0 ) && (MBRow.getQty() == 0) && (MBRow.getGrossWeight() == null || MBRow.getGrossWeight().signum() == 0 ) && (MBRow.getAmount() == null || MBRow.getAmount().signum() == 0 )){
				iter.remove();
				listMBillNew.remove(MBRow);
				manBillWrapper.setManBillRowList(listMBillNew);
			}
		}
		
		for(ManBillRowList newMBRowobj: listMBillNew) {
			//** For new Row added while Update **/
			if (newMBRowobj.getManBillRowListId() == null || newMBRowobj.getManBillRowListId().equals("")) {
				newMBRowobj.setManualBilling(manBillWrapper.getManBill());
				manualBillingDao.insertManBillRowList(newMBRowobj);
			}
			//**  For Existing row Update  **/
			else {
				for (ManBillRowList oldMBRowobj : listMBillOld) {
					if (newMBRowobj.getManBillRowListId().equals(oldMBRowobj.getManBillRowListId())) {
						newMBRowobj.setManualBilling(manBillWrapper.getManBill());
						manualBillingDao.updateManBillRowList(newMBRowobj);
					}
				}
			}
		}
		/** For Seperate Id generation based on Vat amount //
			String mBillFormType = manBillWrapper.getManBill().getmBillFormType();
			manBillWrapper.getManBill().setmBillAutoNO(generateManualBillingNO(mBillFormType));**/
	
		manualBillingDao.updateManualBilling(manBillWrapper.getManBill());
		return new ModelAndView("redirect:manualSalesList.htm");
	}
	
	/** For List Manual Billing Form ***/
	@RequestMapping(value = "/manualSalesList.htm", method = RequestMethod.GET)
	public ModelAndView showList(ModelMap model) {
		List<ManualBilling> manualVatSalesList = manualBillingDao.listVatManualBilling();
		List<ManualBilling> manualVatFreeSalesList = manualBillingDao.listVatFreeManualBilling();
		model.addAttribute("manualVatSalesList", manualVatSalesList);
		model.addAttribute("manualVatFreeSalesList", manualVatFreeSalesList);
		return new ModelAndView("ManualBillingList", model);
	}

	/** For Cancel **/
	@RequestMapping(value = "formManualSales.htm", method = RequestMethod.POST, params = "cancel")
	public String cancelForm() {
		return "redirect:manualSalesList.htm";
	}

}
