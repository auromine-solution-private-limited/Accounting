package com.jewellery.dao;

import java.math.BigDecimal;
import java.util.List;
import com.jewellery.entity.RateMaster;

public interface RateMasterDao {
	public List<RateMaster> listRateMaster();
	public RateMaster getRateMaster(Integer ratemasterId);
	public List<RateMaster> searchRateMaster();
	public void insertRateMaster(RateMaster ratemaster);
	public void updateRateMaster(RateMaster ratemaster);
	public List<BigDecimal> getGoldOrnamentRate(); /** For Savings Scheme to Sales Retrieve Gold Rate **/
}
