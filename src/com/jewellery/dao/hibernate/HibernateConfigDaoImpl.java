package com.jewellery.dao.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.jewellery.dao.ConfigDao;
import com.jewellery.entity.ConfigSetting;

public class HibernateConfigDaoImpl extends HibernateDaoSupport implements ConfigDao {
	
	@Transactional
	public void insertConfigSetting(ConfigSetting configSetting) {
		getHibernateTemplate().saveOrUpdate(configSetting);
	}

	@SuppressWarnings("unchecked")
	public List<ConfigSetting> listConfigSetting() {
		return (List<ConfigSetting>) getHibernateTemplate().find("from ConfigSetting");
	}
}
