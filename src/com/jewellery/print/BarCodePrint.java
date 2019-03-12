package com.jewellery.print;

import javax.servlet.http.HttpSession;

import com.jewellery.entity.ItemMaster;
import com.jewellery.entity.PostagItem;

public interface BarCodePrint {
	
	// POS Bar code Print Interface Methods
	public void posPrintTSC(PostagItem positemtag, HttpSession session);
	public void posPrintZebra(PostagItem positemtag, HttpSession session);
	public void posPrintCitizen(PostagItem positemtag, HttpSession session);
	
	// Ornament Bar code Print Interface Methods
	public void ornamentPrintTSC(ItemMaster itemmaster, HttpSession session);
	public void ornamentPrintCitizen(ItemMaster itemmaster, HttpSession session);
	public void ornamentPrintZebra(ItemMaster itemmaster, HttpSession session);
	public void ornamentPrintArgox(ItemMaster itemmaster, HttpSession session);
	public void ornamentPrintArgoxSGH(ItemMaster itemmaster, HttpSession session);
}
