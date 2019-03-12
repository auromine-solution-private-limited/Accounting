package com.jewellery.dao.hibernate;
import java.util.Date;
import java.util.List;

import com.jewellery.dao.ProductInfoDao;
import com.jewellery.entity.ProductInfo;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

public class HibernateProductInfoDaoImpl extends HibernateDaoSupport implements ProductInfoDao{

	@Transactional
	public void storeProductInfo(ProductInfo productinfo) {
		getHibernateTemplate().save(productinfo);
		
	}
	
	@Transactional
	public void updateProductInfo(ProductInfo productinfo) {
		getHibernateTemplate().update(productinfo);
		
	}

	public void update(Integer numberofDays,Date lastUsedDate) {
		Object[] param = {numberofDays,lastUsedDate};
		String query="update ProductInfo p set p.numberofDays=?,p.lastUsedDate=? where p.productVersionId=1";
		getHibernateTemplate().bulkUpdate(query, param);
	}

	
	public List<ProductInfo> getProductinfo() {
		
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ProductInfo> getExistingList() {
		return (List<ProductInfo>)getHibernateTemplate().find("from ProductInfo where productVersionId=1");
	}

}
