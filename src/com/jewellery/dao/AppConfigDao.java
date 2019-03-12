package com.jewellery.dao;

import java.util.List;

import com.jewellery.entity.ConfigDetailList;
import com.jewellery.entity.ConfigSetting;

public interface AppConfigDao {
	
	public void insertConfigSetting(ConfigSetting configSetting);
	public void updateConfigSetting(ConfigSetting configSetting);
	public void deleteConfigSetting(ConfigSetting configSetting);
	public List<ConfigSetting> listConfigSetting(); // To retrieve all the ConfigSetting List
	
	public ConfigSetting getConfigSetting(Integer configId); // To retrieve ConfigSetting based on configId
	
	public void insertConfigDetailList(ConfigDetailList posPurchaseItem);
	public void updateConfigDetailList(ConfigDetailList posPurchaseItem);
	public void deleteConfigDetailList(ConfigDetailList posPurchaseItem);
	
	public List<ConfigDetailList> getConfigDetailList(Integer configId); // To retrieve POSPurchase based on purchaseId
	public List<ConfigDetailList> getModuleCode(String moduleCode);
}
