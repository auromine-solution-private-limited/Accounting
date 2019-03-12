package com.jewellery.dao.hibernate;

import java.util.List;


import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.jewellery.dao.JewelStockDao;
import com.jewellery.entity.JewelStock;


@SuppressWarnings("unchecked")
public class HibernateJewelStockDaoImpl extends HibernateDaoSupport implements JewelStockDao {

	public HibernateJewelStockDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	public void saveJewelStock(JewelStock jewelStock) {
		getHibernateTemplate().save(jewelStock);

	}

	public void updateJewelStock(JewelStock jewelStock) {
		getHibernateTemplate().update(jewelStock);
	}

	public void deleteJewelStock(JewelStock jewelStock) {
		getHibernateTemplate().delete(jewelStock);

	}

	public List<JewelStock> listAllJewelStock() {
		return (List<JewelStock>) getHibernateTemplate().find("FROM JewelStock");
	}
	
	public List<JewelStock> searchJewelStock(String jewelStockId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// For Opening Stock Untagged
	public List<JewelStock> searchByTransNO_TransType(String TransNO, String TransType){
		Object[] searchByItemIdParam = {TransNO,TransType};
		String searchByItemIdQuery = "FROM JewelStock WHERE stock_TransNO = ? AND stock_TransType = ?"; 
		return (List<JewelStock>) getHibernateTemplate().find(searchByItemIdQuery,searchByItemIdParam);
	}
	
	// For Opening Stock Untagged and Tagged
	public List<JewelStock> searchByItemCode_TransType(String ItemCode, String TransType){
		Object[] searchByItemCodeParam = {ItemCode, TransType};
		String searchByItemCodeQuery = "FROM JewelStock WHERE stock_ItemCode = ? AND stock_TransType = ?";
		return (List<JewelStock>) getHibernateTemplate().find(searchByItemCodeQuery,searchByItemCodeParam);
	}
	

}
