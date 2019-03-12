package com.jewellery.dao;

import java.util.List;

import com.jewellery.entity.ConfigSetting;

public interface ConfigDao {
	public void insertConfigSetting(ConfigSetting config);
	public List<ConfigSetting> listConfigSetting();

}
