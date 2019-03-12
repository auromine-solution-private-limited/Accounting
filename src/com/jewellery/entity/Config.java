package com.jewellery.entity;

import java.util.List;

public class Config {
	
	public ConfigSetting configSetting;
	public List<ConfigDetailList> listConfigDetail;

	public ConfigSetting getConfigSetting() {
		return configSetting;
	}
	public void setConfigSetting(ConfigSetting configSetting) {
		this.configSetting = configSetting;
	}
	public List<ConfigDetailList> getListConfigDetail() {
		return listConfigDetail;
	}
	public void setListConfigDetail(List<ConfigDetailList> listConfigDetail) {
		this.listConfigDetail = listConfigDetail;
	}
	
}
