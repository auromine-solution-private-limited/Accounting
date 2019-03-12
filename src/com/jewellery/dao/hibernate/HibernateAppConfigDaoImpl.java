package com.jewellery.dao.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.jewellery.dao.AppConfigDao;
import com.jewellery.entity.ConfigDetailList;
import com.jewellery.entity.ConfigSetting;

public class HibernateAppConfigDaoImpl extends HibernateDaoSupport implements AppConfigDao{
	
	/************************ Single : ConfigSetting bean Methods *******************************/
	
	public void insertConfigSetting(ConfigSetting configSetting) {
		getHibernateTemplate().save(configSetting);
	}

	public void updateConfigSetting(ConfigSetting configSetting) {
		getHibernateTemplate().update(configSetting);
	}

	public void deleteConfigSetting(ConfigSetting configSetting) {
		getHibernateTemplate().delete(configSetting);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigSetting> listConfigSetting() {
		return(List<ConfigSetting>) getHibernateTemplate().find("From ConfigSetting");
	}

	public ConfigSetting getConfigSetting(Integer configId) {
		return (ConfigSetting) getHibernateTemplate().find("FROM ConfigSetting WHERE configId = ?",configId).get(0);
	}
	
	/*********************** Multiple : ConfigDetailList bean Methods ******************************/
	
	public void insertConfigDetailList(ConfigDetailList configDetailList) {
		getHibernateTemplate().save(configDetailList);
	}

	public void updateConfigDetailList(ConfigDetailList configDetailList) {
		getHibernateTemplate().update(configDetailList);
	}

	public void deleteConfigDetailList(ConfigDetailList configDetailList) {
		getHibernateTemplate().delete(configDetailList);
	}

	@SuppressWarnings("unchecked")
	public List<ConfigDetailList> getConfigDetailList(Integer configId) {
		return (List<ConfigDetailList>) getHibernateTemplate().find("FROM ConfigDetailList WHERE configId = ?",configId);
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigDetailList> getModuleCode(String moduleCode) {
		return (List<ConfigDetailList>) getHibernateTemplate().find("FROM ConfigDetailList WHERE ModuleCode = ?", moduleCode);
	}
}
