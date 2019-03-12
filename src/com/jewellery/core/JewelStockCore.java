package com.jewellery.core;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.jewellery.dao.JewelStockDao;
import com.jewellery.entity.ItemMaster;
import com.jewellery.entity.JewelStock;
import com.jewellery.entity.JobOrder;
import com.jewellery.entity.LotStock;
import com.jewellery.entity.Sales;
import com.jewellery.entity.Transfer;

public class JewelStockCore {
	
	private final static BigDecimal Negative = new BigDecimal("-1");
	
	/** Lot Stock : Tagged Negative Entry - Insert **/
	public static void JewelStock_LotStock_NegativeEntry(JewelStockDao jewelStockDao, LotStock lotstock, ItemMaster itemmaster)
	{
		JewelStock jewelStock = new JewelStock();
		
		jewelStock.setStock_TransType("ItemMaster");
		jewelStock.setStock_StockType(itemmaster.getStockType());
		jewelStock.setStock_TransNO(itemmaster.getItemId().toString()); // Item Id
		
		jewelStock.setStock_TransDate(itemmaster.getStockDate());
		jewelStock.setStock_ItemCode(itemmaster.getItemCode());
		jewelStock.setStock_CategoryName(itemmaster.getCategoryName());
		jewelStock.setStock_SubCategoryName(itemmaster.getSubCategoryName());
		jewelStock.setStock_ItemName(itemmaster.getItemName());
		jewelStock.setStock_MetalUsed(itemmaster.getMetalUsed());
		jewelStock.setStock_MetalType(itemmaster.getMetalType());
		jewelStock.setStock_OPQty(itemmaster.getOp_Quantity());
		jewelStock.setStock_CLQty(itemmaster.getQty());
		jewelStock.setStock_StonePieces(itemmaster.getStonePieces());
		jewelStock.setStock_OPTotalPieces(itemmaster.getOp_TotalPieces());
		jewelStock.setStock_CLTotalPieces(itemmaster.getTotalPieces());
		jewelStock.setStock_CLPieces(itemmaster.getPiecesPerQty());
		jewelStock.setStock_OPGrossWeight(itemmaster.getOp_GrossWeight());
		jewelStock.setStock_OPNetWeight(itemmaster.getOp_NetWeight());
		jewelStock.setStock_OPTotalGrossWeight(itemmaster.getOp_TotalGrossWeight());
		jewelStock.setStock_CLGrossWeight(itemmaster.getGrossWeight());
		jewelStock.setStock_CLNetWeight(itemmaster.getNetWeight());
		jewelStock.setStock_CLTotalGrossWeight(itemmaster.getTotalGrossWeight());
		jewelStock.setStock_StoneName(itemmaster.getStone());
		jewelStock.setStock_StoneRatePerCarat(itemmaster.getStoneRatePerCaret());
		jewelStock.setStock_StoneCost(itemmaster.getStoneCost());
		jewelStock.setStock_StonePieces(itemmaster.getStonePieces());
		jewelStock.setStock_BullionRate(itemmaster.getBullionRate());
		jewelStock.setStock_VAPercent(itemmaster.getVaPercentage());
		jewelStock.setStock_LessPercent(itemmaster.getLessPercentage());
		jewelStock.setStock_MCInRupee(itemmaster.getMcInRupee());
		jewelStock.setStock_MCPerGram(itemmaster.getMcPerGram());
		jewelStock.setStock_MCAmount(itemmaster.getMcAmount());
		jewelStock.setStock_ItemHMCharges(itemmaster.getItemHMCharges());
		jewelStock.setStock_ManuSeal(itemmaster.getManufacturerSeal());
		jewelStock.setStock_ItemSeal(itemmaster.getItemseal());
		jewelStockDao.saveJewelStock(jewelStock);
	}
	
	/** Lot Stock :  Opening Stock & Return Stock - Insert **/
	public static void JewelStock_LotStock_Insert(JewelStockDao jewelStockDao, LotStock lotStock)
	{
		JewelStock jewelStock = new JewelStock();
		
		jewelStock.setStock_TransType("LotStock");
		jewelStock.setStock_StockType(lotStock.getLotType());
		jewelStock.setStock_TransNO(lotStock.getLotId().toString()); // Lot Stock Id
		jewelStock.setStock_TransDate(lotStock.getLotDate());
		jewelStock.setStock_ItemCode(lotStock.getLotCode());
		jewelStock.setStock_CategoryName("LotStock");
		jewelStock.setStock_ItemName(lotStock.getLotItemName());
		jewelStock.setStock_MetalUsed(lotStock.getLotItemName());
		
		if(lotStock.getLotItemName().equalsIgnoreCase("GoldLotStock")){
			jewelStock.setStock_MetalType("Gold");
		}else if(lotStock.getLotItemName().equalsIgnoreCase("SilverLotStock")){
			jewelStock.setStock_MetalType("Silver");
		}
		
		jewelStock.setStock_CLQty(lotStock.getQuantity());
		jewelStock.setStock_CLGrossWeight(lotStock.getGrossWeight());
		jewelStock.setStock_CLNetWeight(lotStock.getNetWeight());
		jewelStock.setStock_CLTotalGrossWeight(lotStock.getGrossWeight());
											
		jewelStockDao.saveJewelStock(jewelStock);
	}
	
	/** Lot Stock  :  Opening Stock & Return Stock - Update **/
	public static void JewelStock_LotStock_Update(JewelStockDao jewelStockDao, LotStock lotStock)
	{
		JewelStock jewelStock = jewelStockDao.searchByTransNO_TransType(lotStock.getLotId().toString(), "LotStock").get(0);
		
		jewelStock.setStock_TransType("LotStock");
		jewelStock.setStock_StockType(lotStock.getLotType());
		jewelStock.setStock_TransNO(lotStock.getLotId().toString());
		jewelStock.setStock_TransDate(lotStock.getLotDate());
		jewelStock.setStock_ItemCode(lotStock.getLotCode());
		jewelStock.setStock_CategoryName("LotStock");
		jewelStock.setStock_ItemName(lotStock.getLotItemName());
		jewelStock.setStock_MetalUsed(lotStock.getLotItemName());
		
		if(lotStock.getLotItemName().equalsIgnoreCase("GoldLotStock")){
			jewelStock.setStock_MetalType("Gold");
		}else if(lotStock.getLotItemName().equalsIgnoreCase("SilverLotStock")){
			jewelStock.setStock_MetalType("Silver");
		}
		
		jewelStock.setStock_CLQty(lotStock.getQuantity());
		jewelStock.setStock_CLGrossWeight(lotStock.getGrossWeight());
		jewelStock.setStock_CLNetWeight(lotStock.getNetWeight());
		jewelStock.setStock_CLTotalGrossWeight(lotStock.getGrossWeight());
		
		jewelStockDao.updateJewelStock(jewelStock);
	}
	
	/** ItemMaster : Tagged Positive Entry - Insert **/
	public static void JewelStock_Tagged_PositiveEntry(JewelStockDao jewelStockDao,ItemMaster itemmaster, String stockType)
	{
		
		JewelStock jewelStock = new JewelStock();
		
		jewelStock.setStock_TransType("ItemMaster");
		jewelStock.setStock_StockType(stockType);
		jewelStock.setStock_TransNO(itemmaster.getItemId().toString()); // Item Id
		
		jewelStock.setStock_TransDate(itemmaster.getStockDate());
		jewelStock.setStock_ItemCode(itemmaster.getItemCode());
		jewelStock.setStock_CategoryName(itemmaster.getCategoryName());
		jewelStock.setStock_SubCategoryName(itemmaster.getSubCategoryName());
		jewelStock.setStock_ItemName(itemmaster.getItemName());
		jewelStock.setStock_MetalUsed(itemmaster.getMetalUsed());
		jewelStock.setStock_MetalType(itemmaster.getMetalType());
		jewelStock.setStock_OPQty(itemmaster.getOp_Quantity());
		jewelStock.setStock_CLQty(itemmaster.getQty());
		jewelStock.setStock_StonePieces(itemmaster.getStonePieces());
		jewelStock.setStock_OPTotalPieces(itemmaster.getOp_TotalPieces());
		jewelStock.setStock_CLTotalPieces(itemmaster.getTotalPieces());
		jewelStock.setStock_OPGrossWeight(itemmaster.getOp_GrossWeight());
		jewelStock.setStock_OPNetWeight(itemmaster.getOp_NetWeight());
		jewelStock.setStock_OPTotalGrossWeight(itemmaster.getOp_TotalGrossWeight());
		jewelStock.setStock_CLGrossWeight(itemmaster.getGrossWeight());
		jewelStock.setStock_CLNetWeight(itemmaster.getNetWeight());
		jewelStock.setStock_CLTotalGrossWeight(itemmaster.getTotalGrossWeight());
		jewelStock.setStock_CLPieces(itemmaster.getPiecesPerQty());
		jewelStock.setStock_StoneName(itemmaster.getStone());
		jewelStock.setStock_StoneRatePerCarat(itemmaster.getStoneRatePerCaret());
		jewelStock.setStock_StoneCost(itemmaster.getStoneCost());
		jewelStock.setStock_StonePieces(itemmaster.getStonePieces());
		jewelStock.setStock_BullionRate(itemmaster.getBullionRate());
		jewelStock.setStock_VAPercent(itemmaster.getVaPercentage());
		jewelStock.setStock_LessPercent(itemmaster.getLessPercentage());
		jewelStock.setStock_MCInRupee(itemmaster.getMcInRupee());
		jewelStock.setStock_MCPerGram(itemmaster.getMcPerGram());
		jewelStock.setStock_MCAmount(itemmaster.getMcAmount());
		jewelStock.setStock_Tax(itemmaster.getTax());
		jewelStock.setStock_ItemHMCharges(itemmaster.getItemHMCharges());
		jewelStock.setStock_ManuSeal(itemmaster.getManufacturerSeal());
		jewelStock.setStock_ItemSeal(itemmaster.getItemseal());
									
		jewelStockDao.saveJewelStock(jewelStock);
	}
	
	/**  ItemMaster : Tagged Positive Entry - Update **/
	public static void JewelStock_Taged_UpdateEntry(JewelStockDao jewelStockDao,ItemMaster itemmaster)
	{
		JewelStock jewelStockTagDB = jewelStockDao.searchByItemCode_TransType(itemmaster.getItemCode(),"ItemMaster").get(0);
		
		jewelStockTagDB.setStock_ItemName(itemmaster.getItemName());
		jewelStockTagDB.setStock_MetalUsed(itemmaster.getMetalUsed());
		jewelStockTagDB.setStock_OPGrossWeight(itemmaster.getOp_GrossWeight());
		jewelStockTagDB.setStock_OPNetWeight(itemmaster.getOp_NetWeight());
		jewelStockTagDB.setStock_OPTotalGrossWeight(itemmaster.getOp_TotalGrossWeight());
		jewelStockTagDB.setStock_OPQty(itemmaster.getOp_Quantity());
		jewelStockTagDB.setStock_OPTotalPieces(itemmaster.getOp_TotalPieces());
		jewelStockTagDB.setStock_CLGrossWeight(itemmaster.getGrossWeight());
		jewelStockTagDB.setStock_CLNetWeight(itemmaster.getNetWeight());
		jewelStockTagDB.setStock_CLTotalGrossWeight(itemmaster.getTotalGrossWeight());
		jewelStockTagDB.setStock_CLQty(itemmaster.getQty());
		jewelStockTagDB.setStock_CLPieces(itemmaster.getPiecesPerQty());
		jewelStockTagDB.setStock_CLTotalPieces(itemmaster.getTotalPieces());
		jewelStockTagDB.setStock_StoneName(itemmaster.getStone());
		jewelStockTagDB.setStock_StonePieces(itemmaster.getStonePieces());
		jewelStockTagDB.setStock_StoneWeight(itemmaster.getStoneWeight());
		jewelStockTagDB.setStock_StoneRatePerCarat(itemmaster.getStoneRatePerCaret());
		jewelStockTagDB.setStock_ManuSeal(itemmaster.getManufacturerSeal());
		jewelStockTagDB.setStock_ItemSeal(itemmaster.getItemseal());
		jewelStockTagDB.setStock_TransDate(itemmaster.getStockDate());
		
		jewelStockDao.updateJewelStock(jewelStockTagDB);
	}
	
	/**  ItemMaster : Un tagged Stock  - Negative Insert **/
	public static void JewelStock_unTaged_NegativeEntry(JewelStockDao jewelStockDao,BigDecimal grossWeight, BigDecimal netWeight, BigDecimal totalGrossWeight,Integer qty, String StockType, ItemMaster itemmaster, String metalUsed)
	{
		
		JewelStock jewelStock = new JewelStock();
		
		if(metalUsed.equals("Gold Ornaments")){
			jewelStock.setStock_ItemCode("IT100001");
		}else if(metalUsed.equals("Silver Ornaments")){
			jewelStock.setStock_ItemCode("IT100006");
		}
				
		jewelStock.setStock_TransType("ItemMaster");
		jewelStock.setStock_StockType("PurchasedStock");
		jewelStock.setStock_TransNO(itemmaster.getItemId().toString()); // Item Id
		jewelStock.setStock_TransDate(itemmaster.getStockDate());
		jewelStock.setStock_CategoryName(itemmaster.getCategoryName());
		jewelStock.setStock_SubCategoryName(itemmaster.getSubCategoryName());
		jewelStock.setStock_ItemName(itemmaster.getItemName());
		jewelStock.setStock_MetalUsed(itemmaster.getMetalUsed());
		jewelStock.setStock_MetalType(itemmaster.getMetalType());
		jewelStock.setStock_CLQty(qty * -1);
		jewelStock.setStock_CLGrossWeight(grossWeight.multiply(Negative));
		jewelStock.setStock_CLNetWeight(grossWeight.multiply(Negative));
		jewelStock.setStock_CLTotalGrossWeight(totalGrossWeight.multiply(Negative));
		jewelStock.setStock_CLTotalPieces(itemmaster.getTotalPieces() *  -1);
		jewelStock.setStock_CLPieces(itemmaster.getPiecesPerQty() *  -1);

		jewelStockDao.saveJewelStock(jewelStock);
		
	}
	
	/**  ItemMaster : Un tagged Stock - Update **/
	public static void JewelStock_unTaged_UpdateEntry(JewelStockDao jewelStockDao,ItemMaster itemmaster)
	{
		String JewelItemCode ="";
		if( itemmaster.getMetalUsed().equalsIgnoreCase("Gold Ornaments")){
			JewelItemCode = "IT100001";
		}else if(itemmaster.getMetalUsed().equalsIgnoreCase("Silver Ornaments")){
			JewelItemCode = "IT100006";
		}
		
		JewelStock jewelStockDB = jewelStockDao.searchByItemCode_TransType(JewelItemCode,"ItemMaster").get(0);
		
		jewelStockDB.setStock_CLGrossWeight(itemmaster.getGrossWeight().multiply(Negative));
		jewelStockDB.setStock_CLNetWeight(itemmaster.getNetWeight().multiply(Negative));
		jewelStockDB.setStock_CLTotalGrossWeight(itemmaster.getTotalGrossWeight().multiply(Negative));
		jewelStockDB.setStock_CLQty(itemmaster.getQty() * -1);
		jewelStockDB.setStock_CLTotalPieces(itemmaster.getTotalPieces() * -1);
		jewelStockDB.setStock_CLPieces(itemmaster.getPiecesPerQty() * -1);
		jewelStockDB.setStock_TransDate(itemmaster.getStockDate());
		
		jewelStockDao.updateJewelStock(jewelStockDB);
	}
		
	/** Job Order : Un tagged Stock  - Negative Insert **/
	public static void JewelStock_unTaged_NegativeEntry_JobOrder(JewelStockDao jewelStockDao,BigDecimal grossWeight, BigDecimal netWeight, BigDecimal totalGrossWeight, String StockType, JobOrder joborder)
	{		
		JewelStock jewelStock = new JewelStock();			
					
		// Stock Update ( UnTag )
		jewelStock.setStock_TransType("Joborder Issue");
		jewelStock.setStock_StockType(StockType);
		jewelStock.setStock_TransNO(joborder.getJobOrderId().toString());
		jewelStock.setStock_ItemCode(joborder.getFromItemCode());
		jewelStock.setStock_TransDate(joborder.getOrderDate());
		jewelStock.setStock_CategoryName(joborder.getBullionType());		
		jewelStock.setStock_MetalUsed(joborder.getBullionType());
		jewelStock.setStock_MetalType(joborder.getBullionType());		
		jewelStock.setStock_CLGrossWeight(grossWeight.multiply(Negative));
		jewelStock.setStock_CLNetWeight(grossWeight.multiply(Negative));
		jewelStock.setStock_CLTotalGrossWeight(totalGrossWeight.multiply(Negative));
			
		jewelStockDao.saveJewelStock(jewelStock); 
		
	}
	
	/** Job Order : Un tagged Stock  - Positive Insert **/
	public static void JewelStock_unTaged_PositiveEntry_JobOrder(JewelStockDao jewelStockDao,BigDecimal grossWeight, BigDecimal netWeight, BigDecimal totalGrossWeight, Integer qty, String StockType, JobOrder joborder)
	{		
		JewelStock jewelStock = new JewelStock();			
					
		// Stock Update ( UnTag )
		jewelStock.setStock_TransType("Joborder Received");
		jewelStock.setStock_StockType(StockType);
		jewelStock.setStock_TransNO(joborder.getJobOrderId().toString());
		jewelStock.setStock_ItemCode(joborder.getToItemCode());
		jewelStock.setStock_TransDate(joborder.getOrderDate());
		jewelStock.setStock_CategoryName(joborder.getCategoryName());		
		jewelStock.setStock_MetalUsed(joborder.getCategoryName());
		jewelStock.setStock_MetalType(joborder.getCategoryName());		
		jewelStock.setStock_CLGrossWeight(grossWeight);
		jewelStock.setStock_CLNetWeight(netWeight); 
		jewelStock.setStock_CLTotalGrossWeight(totalGrossWeight);		
		jewelStock.setStock_CLQty(qty);
		jewelStock.setStock_CLTotalPieces(qty);
		jewelStockDao.saveJewelStock(jewelStock);
	}
	
	/** Job Order : Un tagged Stock  - Negative Update **/
	public static void JewelStock_unTaged_UpdateEntry_JobOrder(JewelStockDao jewelStockDao,BigDecimal grossWeight, BigDecimal netWeight, BigDecimal totalGrossWeight, int qty, String categoryname, String itemCode, String StockType, JobOrder joborder)
	{						
		String transType;
		if(StockType.equalsIgnoreCase("Issued")){
			transType = "Joborder Issue";
		}else{
			transType = "Joborder Received";
		}
		
		List<JewelStock> jewelStockDBlist = jewelStockDao.searchByTransNO_TransType(joborder.getJobOrderId().toString(),transType);
		JewelStock jewelStockDB = jewelStockDBlist.get(0);
		
		jewelStockDB.setStock_StockType(StockType);
		jewelStockDB.setStock_ItemCode(itemCode);		
		jewelStockDB.setStock_TransDate(joborder.getOrderDate());
		jewelStockDB.setStock_CategoryName(categoryname);		
		jewelStockDB.setStock_MetalUsed(joborder.getBullionType());
		jewelStockDB.setStock_MetalType(joborder.getBullionType());		
		jewelStockDB.setStock_CLGrossWeight(grossWeight);
		jewelStockDB.setStock_CLNetWeight(netWeight);		
		jewelStockDB.setStock_CLTotalGrossWeight(totalGrossWeight);	
		jewelStockDB.setStock_CLQty(qty);
		jewelStockDB.setStock_CLTotalPieces(qty);
		
		jewelStockDao.updateJewelStock(jewelStockDB);
		
	}
	
	/** Transfer : FROM Untagged / Tagged Stock - Negative Insert **/
	public static void JewelStock_Negative_Insert_FromTransfer(JewelStockDao jewelStockDao, Transfer transfer, ItemMaster itemmaster)
	{
		
		JewelStock jewelStock = new JewelStock();
		
		jewelStock.setStock_TransType("Transfer From");
		jewelStock.setStock_StockType(transfer.getTransferType());
		jewelStock.setStock_TransNO(transfer.getTransferNO()); 
		
		jewelStock.setStock_TransDate(transfer.getTransferDate());
		jewelStock.setStock_ItemCode(transfer.getFromItemNo());
		jewelStock.setStock_CategoryName(itemmaster.getCategoryName());
		jewelStock.setStock_SubCategoryName(itemmaster.getSubCategoryName());
		jewelStock.setStock_ItemName(transfer.getFromItemName());
		jewelStock.setStock_MetalUsed(itemmaster.getMetalUsed()); 
		jewelStock.setStock_MetalType(transfer.getFromBullion());
		jewelStock.setStock_CLQty(transfer.getFromQty() * -1);
		jewelStock.setStock_CLPieces(transfer.getFromPieces() * -1);
		jewelStock.setStock_CLGrossWeight(transfer.getFromGrossWeight().multiply(Negative));
		jewelStock.setStock_CLNetWeight(transfer.getFromGrossWeight().multiply(Negative));
		jewelStock.setStock_CLTotalGrossWeight(transfer.getFromGrossWeight().multiply(Negative));
		//jewelStock.setStock_CLTotalPieces(itemmaster.getTotalPieces() * -1);
		jewelStockDao.saveJewelStock(jewelStock);

	}
	
	
	/** Transfer : TO Untagged / Tagged Stock - Positive Insert **/
	public static void JewelStock_Positive_Insert_ToTransfer(JewelStockDao jewelStockDao, Transfer transfer, ItemMaster itemmaster)
	{
		
		JewelStock jewelStock = new JewelStock();
		
		jewelStock.setStock_TransType("Transfer To");
		jewelStock.setStock_StockType(transfer.getTransferType());
		jewelStock.setStock_TransNO(transfer.getTransferNO()); // Item Id
		
		jewelStock.setStock_TransDate(transfer.getTransferDate());
		jewelStock.setStock_ItemCode(transfer.getToItemNo());
		jewelStock.setStock_CategoryName(itemmaster.getCategoryName());
		jewelStock.setStock_SubCategoryName(itemmaster.getSubCategoryName()); 
		jewelStock.setStock_ItemName(transfer.getToItemName());
		jewelStock.setStock_MetalUsed(itemmaster.getMetalUsed()); 
		jewelStock.setStock_MetalType(transfer.getToBullion());
		jewelStock.setStock_CLQty(transfer.getToQty());
		jewelStock.setStock_CLPieces(transfer.getToPieces());
		jewelStock.setStock_CLGrossWeight(transfer.getToGrossWeight());
		jewelStock.setStock_CLNetWeight(transfer.getToGrossWeight());
		jewelStock.setStock_CLTotalGrossWeight(transfer.getToGrossWeight());
		//jewelStock.setStock_CLTotalPieces(itemmaster.getTotalPieces() * -1);
		
		jewelStockDao.saveJewelStock(jewelStock);
	}
	
	/** Tag Issue : Untagged / Tagged Stock - Negative Insert **/
	public static void jewelSTagIssueUpdation(JewelStockDao jewelStockDao, String tagissueType,String transferNo,Date tagissueDate
			,String itemcode,String categoryName,String subcategory,String itemName,String MetalUsed,String metaltype,String transferType,BigDecimal itemweght,BigDecimal totgwt, BigDecimal totgwt2, int itemqtset, int totalpieces){
		
		JewelStock jewelStock = new JewelStock();
		jewelStock.setStock_TransType(tagissueType);
		jewelStock.setStock_TransNO(transferNo);
		jewelStock.setStock_TransDate(tagissueDate);
		jewelStock.setStock_ItemCode(itemcode.toUpperCase());
		jewelStock.setStock_CategoryName(categoryName);
		jewelStock.setStock_SubCategoryName(subcategory);
		jewelStock.setStock_ItemName(itemName);
		jewelStock.setStock_MetalUsed(MetalUsed);
		jewelStock.setStock_MetalType(metaltype);
		jewelStock.setStock_StockType(transferType);
		jewelStock.setStock_CLGrossWeight(itemweght.multiply(Negative));
		jewelStock.setStock_CLNetWeight(totgwt.multiply(Negative));
		jewelStock.setStock_CLTotalGrossWeight(totgwt2.multiply(Negative));
		jewelStock.setStock_CLQty(itemqtset * -1);
		jewelStock.setStock_CLTotalPieces(totalpieces * -1);
		
		jewelStockDao.saveJewelStock(jewelStock);
	}
	
	/** Sales : TO Untagged / Tagged Stock - Negative Insert **/
	public static void jewelStockInsert(JewelStockDao jewelStockDao,String salesType,String salesTypeId,Date salesDate,String itemName,String itemcode,
			String categoryname,String bullionType,String metalUsed,int nop,int totalPieces,BigDecimal grswt,
			BigDecimal netwght,BigDecimal totalGrswght,String stoneDesc,BigDecimal stoneRatePerCaret,
			BigDecimal stoneCost,BigDecimal bullionRate,BigDecimal valueAdditionCharges,BigDecimal lessPercentage,
			BigDecimal mcByAmount,BigDecimal mcByGrams,BigDecimal vtax,BigDecimal salesHMCharges){
		
		JewelStock jewelStock = new JewelStock();
		
		jewelStock.setStock_TransType(salesType);
		jewelStock.setStock_TransNO(salesTypeId);
		jewelStock.setStock_TransDate(salesDate);
		jewelStock.setStock_ItemName(itemName);
		jewelStock.setStock_ItemCode(itemcode);
		if(itemcode.equalsIgnoreCase("IT100011")){
			jewelStock.setStock_CategoryName("Gold Loose Stock");
		}else if(itemcode.equalsIgnoreCase("IT100012")){
			jewelStock.setStock_CategoryName("Silver Loose Stock");
		}else{
			jewelStock.setStock_CategoryName(categoryname);
		}
		jewelStock.setStock_MetalType(bullionType);
		jewelStock.setStock_MetalUsed(metalUsed);
		jewelStock.setStock_CLQty(nop*-1);
		jewelStock.setStock_CLTotalPieces(totalPieces*-1);
		jewelStock.setStock_CLGrossWeight(grswt.multiply(Negative));
		jewelStock.setStock_CLNetWeight(netwght.multiply(Negative));
		jewelStock.setStock_CLTotalGrossWeight(totalGrswght.multiply(Negative));
		jewelStock.setStock_StoneName(stoneDesc);
		jewelStock.setStock_StoneRatePerCarat(stoneRatePerCaret);
		jewelStock.setStock_StoneCost(stoneCost);
		jewelStock.setStock_BullionRate(bullionRate);
		jewelStock.setStock_VAPercent(valueAdditionCharges);
		jewelStock.setStock_LessPercent(lessPercentage);
		jewelStock.setStock_MCInRupee(mcByAmount);
		jewelStock.setStock_MCPerGram(mcByGrams);
		jewelStock.setStock_Tax(vtax);
		jewelStock.setStock_ItemHMCharges(salesHMCharges);
		
		jewelStockDao.saveJewelStock(jewelStock);
	}
	
	/** Sales : category wise loose sotck Stock - Negative Insert **/
	public static void jewelStockCatLooseStockInsert(JewelStockDao jewelStockDao,String salesType,String salesTypeId,Date salesDate,String itemName,String itemcode,
			String categoryname,String bullionType,String metalUsed,int nop,int totalPieces,BigDecimal grswt,
			BigDecimal netwght,BigDecimal totalGrswght,String stoneDesc,BigDecimal stoneRatePerCaret,
			BigDecimal stoneCost,BigDecimal bullionRate,BigDecimal valueAdditionCharges,BigDecimal lessPercentage,
			BigDecimal mcByAmount,BigDecimal mcByGrams,BigDecimal vtax,BigDecimal salesHMCharges){
		
		JewelStock jewelStock = new JewelStock();
		
		jewelStock.setStock_TransType(salesType);
		jewelStock.setStock_TransNO(salesTypeId);
		jewelStock.setStock_TransDate(salesDate);
		jewelStock.setStock_ItemName(itemName);
		jewelStock.setStock_ItemCode(itemcode);
		jewelStock.setStock_CategoryName(categoryname);
		jewelStock.setStock_MetalType(bullionType);
		jewelStock.setStock_MetalUsed(metalUsed);
		jewelStock.setStock_CLQty(nop*-1);
		jewelStock.setStock_CLTotalPieces(totalPieces*-1);
		jewelStock.setStock_CLGrossWeight(grswt.multiply(Negative));
		jewelStock.setStock_CLNetWeight(netwght.multiply(Negative));
		jewelStock.setStock_CLTotalGrossWeight(totalGrswght.multiply(Negative));
		jewelStock.setStock_StoneName(stoneDesc);
		jewelStock.setStock_StoneRatePerCarat(stoneRatePerCaret);
		jewelStock.setStock_StoneCost(stoneCost);
		jewelStock.setStock_BullionRate(bullionRate);
		jewelStock.setStock_VAPercent(valueAdditionCharges);
		jewelStock.setStock_LessPercent(lessPercentage);
		jewelStock.setStock_MCInRupee(mcByAmount);
		jewelStock.setStock_MCPerGram(mcByGrams);
		jewelStock.setStock_Tax(vtax);
		jewelStock.setStock_ItemHMCharges(salesHMCharges);
		
		jewelStockDao.saveJewelStock(jewelStock);
	}
	
	/** Sales : category wise loose sotck Stock - Negative Update **/
	public static void jewelStockCatLooseStockUpdate(JewelStockDao jewelStockDao,String salesType,String salesTypeId,Date salesDate,String itemName,String itemcode,
			String categoryname,String bullionType,String metalUsed,int nop,int totalPieces,BigDecimal grswt,
			BigDecimal netwght,BigDecimal totalGrswght,String stoneDesc,BigDecimal stoneRatePerCaret,
			BigDecimal stoneCost,BigDecimal bullionRate,BigDecimal valueAdditionCharges,BigDecimal lessPercentage,
			BigDecimal mcByAmount,BigDecimal mcByGrams,BigDecimal vtax,BigDecimal salesHMCharges){
		
		JewelStock jewelStockDB = jewelStockDao.searchByTransNO_TransType(salesTypeId, "Sales").get(0);
		
		jewelStockDB.setStock_TransType(salesType);
		jewelStockDB.setStock_TransNO(salesTypeId);
		jewelStockDB.setStock_TransDate(salesDate);
		jewelStockDB.setStock_ItemName(itemName);
		jewelStockDB.setStock_ItemCode(itemcode);
		jewelStockDB.setStock_CategoryName(categoryname);
		jewelStockDB.setStock_MetalType(bullionType);
		jewelStockDB.setStock_MetalUsed(metalUsed);
		jewelStockDB.setStock_CLQty(nop*-1);
		jewelStockDB.setStock_CLTotalPieces(totalPieces*-1);
		jewelStockDB.setStock_CLGrossWeight(grswt.multiply(Negative));
		jewelStockDB.setStock_CLNetWeight(netwght.multiply(Negative));
		jewelStockDB.setStock_CLTotalGrossWeight(totalGrswght.multiply(Negative));
		jewelStockDB.setStock_StoneName(stoneDesc);
		jewelStockDB.setStock_StoneRatePerCarat(stoneRatePerCaret);
		jewelStockDB.setStock_StoneCost(stoneCost);
		jewelStockDB.setStock_BullionRate(bullionRate);
		jewelStockDB.setStock_VAPercent(valueAdditionCharges);
		jewelStockDB.setStock_LessPercent(lessPercentage);
		jewelStockDB.setStock_MCInRupee(mcByAmount);
		jewelStockDB.setStock_MCPerGram(mcByGrams);
		jewelStockDB.setStock_Tax(vtax);
		jewelStockDB.setStock_ItemHMCharges(salesHMCharges);
		
		jewelStockDao.updateJewelStock(jewelStockDB);
		System.out.println("successfully updated in Table::::");
	}
	
	/** Sales : TO Tagged Stock - Negative Update **/
	public static void jewelSUpdationForUpdate(JewelStockDao jewelStockDao,String salesType,String salesTypeId,Date salesDate,String itemName,String itemcode,
			String categoryname,String bullionType,String metalUsed,int nop,int totalPieces,BigDecimal grswt,
			BigDecimal netwght,BigDecimal totalGrswght,String stoneDesc,BigDecimal stoneRatePerCaret,
			BigDecimal stoneCost,BigDecimal bullionRate,BigDecimal valueAdditionCharges,BigDecimal lessPercentage,
			BigDecimal mcByAmount,BigDecimal mcByGrams,BigDecimal vtax,BigDecimal salesHMCharges){
		
		JewelStock jewelStockDB = jewelStockDao.searchByTransNO_TransType(salesTypeId, "Sales").get(0);
		jewelStockDB.setStock_TransType(salesType);
		jewelStockDB.setStock_TransNO(salesTypeId);
		jewelStockDB.setStock_TransDate(salesDate);
		jewelStockDB.setStock_ItemName(itemName);
		jewelStockDB.setStock_ItemCode(itemcode);
		if(itemcode.equalsIgnoreCase("IT100011")){
			jewelStockDB.setStock_CategoryName("Gold Loose Stock");
		}else if(itemcode.equalsIgnoreCase("IT100012")){
			jewelStockDB.setStock_CategoryName("Silver Loose Stock");
		}else{
			jewelStockDB.setStock_CategoryName(categoryname);
		}
		jewelStockDB.setStock_MetalType(bullionType);
		jewelStockDB.setStock_MetalUsed(metalUsed);
		jewelStockDB.setStock_CLQty(nop*-1);
		jewelStockDB.setStock_CLTotalPieces(totalPieces*-1);
		jewelStockDB.setStock_CLGrossWeight(grswt.multiply(Negative));
		jewelStockDB.setStock_CLNetWeight(netwght.multiply(Negative));
		jewelStockDB.setStock_CLTotalGrossWeight(totalGrswght.multiply(Negative));
		jewelStockDB.setStock_StoneName(stoneDesc);
		jewelStockDB.setStock_StoneRatePerCarat(stoneRatePerCaret);
		jewelStockDB.setStock_StoneCost(stoneCost);
		jewelStockDB.setStock_BullionRate(bullionRate);
		jewelStockDB.setStock_VAPercent(valueAdditionCharges);
		jewelStockDB.setStock_LessPercent(lessPercentage);
		jewelStockDB.setStock_MCInRupee(mcByAmount);
		jewelStockDB.setStock_MCPerGram(mcByGrams);
		jewelStockDB.setStock_Tax(vtax);
		jewelStockDB.setStock_ItemHMCharges(salesHMCharges);
		
		jewelStockDao.updateJewelStock(jewelStockDB);
	}
	
	/** Sales Return : Untagged / Tagged Stock - Negative Insert **/
	public static void jewelStockSRInsertUpdation(JewelStockDao jewelStockDao,String salesType,String stockType,String salesTypeId,Date salesDate,String itemName,String itemcode,String bullionType,
			int nop,BigDecimal grswt, BigDecimal netwght,BigDecimal totalGrswght,String stoneDesc,BigDecimal stoneCost,BigDecimal bullionRate,Sales sales){
		
		JewelStock jewelStock = new JewelStock();
		
		jewelStock.setStock_TransType(salesType);
		jewelStock.setStock_StockType(stockType);
		jewelStock.setStock_TransNO(salesTypeId);
		jewelStock.setStock_TransDate(salesDate);
		
		jewelStock.setStock_ItemName(itemName);
		jewelStock.setStock_ItemCode(itemcode);
		jewelStock.setStock_MetalType(bullionType);
		jewelStock.setStock_MetalUsed(bullionType);
		jewelStock.setStock_CLQty(nop);
		jewelStock.setStock_CLGrossWeight(grswt);
		jewelStock.setStock_CLNetWeight(netwght);
		jewelStock.setStock_CLTotalGrossWeight(totalGrswght);
		jewelStock.setStock_StoneName(stoneDesc);
		jewelStock.setStock_StoneCost(stoneCost);
		jewelStock.setStock_BullionRate(bullionRate);
		if(itemcode.equalsIgnoreCase("IT100011")){
			jewelStock.setStock_CategoryName("Gold Loose Stock");
		}else if(itemcode.equalsIgnoreCase("IT100012")){
			jewelStock.setStock_CategoryName("Silver Loose Stock");
		}else{
			jewelStock.setStock_CategoryName(itemName);
			jewelStock.setStock_SubCategoryName(itemName);
		}
		
		jewelStockDao.saveJewelStock(jewelStock);
	}
	
	/** Sales Return : Untagged / Tagged Stock - Negative Update **/
	public static void jewelStockSRUpdation(JewelStockDao jewelStockDao,String salesType,String salesTypeId,Date salesDate,String itemName,String itemcode,String bullionType,
			int nop,BigDecimal grswt,BigDecimal netwght,BigDecimal totalGrswght,String stoneDesc,BigDecimal stoneCost,BigDecimal bullionRate,Sales sales){
		
		JewelStock jewelStockDB = jewelStockDao.searchByTransNO_TransType(salesTypeId, "Sales Return").get(0);
		jewelStockDB.setStock_TransType(salesType);
		jewelStockDB.setStock_TransNO(salesTypeId);
		jewelStockDB.setStock_TransDate(salesDate);
		jewelStockDB.setStock_ItemName(itemName);
		jewelStockDB.setStock_ItemCode(itemcode);
		jewelStockDB.setStock_MetalType(bullionType);
		jewelStockDB.setStock_MetalUsed(bullionType);
		jewelStockDB.setStock_CLQty(nop);
		jewelStockDB.setStock_CLGrossWeight(grswt);
		jewelStockDB.setStock_CLNetWeight(netwght);
		jewelStockDB.setStock_CLTotalGrossWeight(totalGrswght);
		jewelStockDB.setStock_StoneName(stoneDesc);
		jewelStockDB.setStock_StoneCost(stoneCost);
		jewelStockDB.setStock_BullionRate(bullionRate);
		if(itemcode.equalsIgnoreCase("IT100011")){
			jewelStockDB.setStock_CategoryName("Gold Loose Stock");
		}else if(itemcode.equalsIgnoreCase("IT100012")){
			jewelStockDB.setStock_CategoryName("Silver Loose Stock");
		}else{
			jewelStockDB.setStock_CategoryName(itemName);
		}
		
		jewelStockDao.updateJewelStock(jewelStockDB);
	}
	
}
