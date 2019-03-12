package com.jewellery.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import com.jewellery.entity.LotStock;

public interface LotStockDao {
	public List<LotStock> listLotStock();
	public List<LotStock> searchLotStock(String itemname);
	public LotStock getLotStock(Integer id);
	public LotStock getLotStock(String itemName);	
	public void insertLotStock(LotStock lotstock);
	public void updateLotStock(LotStock lotstock);
	public void deleteLotStock(LotStock lotstock);
	public List<LotStock> listGoldLotStock();
	public List<LotStock> listSilverLotStock();
	public void updateexitingTaggedlotStock(BigDecimal gwt,BigDecimal nwt,String itemcode);
	public List<Object[]> sumofGoldLotStockGwt();
	public List<Object[]> sumofSilverLotStockGwt();
	public BigInteger getLotCode();
	public void tagissueUpdate(String tagNo);
	public List<LotStock> listReturnGoldLotStock(); //For Return Lot Stock List
	public List<LotStock> listReturnSilverLotStock(); //For Return Lot Stock List
	public List<LotStock> searchLotStockByLotCode(String LotCode);
	
}
