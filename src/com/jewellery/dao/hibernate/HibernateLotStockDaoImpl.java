package com.jewellery.dao.hibernate;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.LotStockDao;
import com.jewellery.entity.LotStock;

public class HibernateLotStockDaoImpl extends HibernateDaoSupport implements LotStockDao
{	
	public static final String LIST_LOT_STOCK= "listLotStock";
	public static final String SEARCH_LOT_ITEM = "getLotStocks";
	public static final String GET_GOLD_LOTITEM = "getGoldLotStock";
	public static final String GET_SILVER_LOTITEM = "getSilverLotStock";
	
		
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<LotStock> listLotStock(){	
		return (List<LotStock>)getHibernateTemplate().findByNamedQuery(LIST_LOT_STOCK);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<LotStock> listGoldLotStock(){	
		return (List<LotStock>)getHibernateTemplate().findByNamedQuery(GET_GOLD_LOTITEM);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<LotStock> listSilverLotStock(){	
		return (List<LotStock>)getHibernateTemplate().findByNamedQuery(GET_SILVER_LOTITEM);
	}
	
	@Transactional
	public LotStock getLotStock(Integer id){	
		return (LotStock)getHibernateTemplate().get(LotStock.class, id);
	}	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<LotStock> searchLotStock(String itemName){
		return (List<LotStock>) getHibernateTemplate().findByNamedQuery(SEARCH_LOT_ITEM, itemName);
	}	
	
	@SuppressWarnings("unchecked")
	public BigInteger getLotCode() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(lotCode,'S',-1),UNSIGNED INTEGER)) from LotStock where lotCode like 'LS%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	
	@Transactional(readOnly=true)
	public LotStock getLotStock(String itemName){
		Session session = getSession();
		try{
			Query query = session.getNamedQuery(SEARCH_LOT_ITEM);
			query.setParameter("itemName", itemName);
		
			return (LotStock)query.uniqueResult();
		}finally{
			releaseSession(session);
		}		
	}	
	
	@Transactional
	public void insertLotStock(LotStock lotStock){		
		getHibernateTemplate().saveOrUpdate(lotStock);		
	}

	@Transactional
	public void updateLotStock(LotStock lotStock) {		
		getHibernateTemplate().update(lotStock);
    }

	@Transactional
    public void deleteLotStock(LotStock lotStock) {		
      getHibernateTemplate().delete(lotStock);
    }

	
	@SuppressWarnings("unchecked")
	public List<Object[]> sumofGoldLotStockGwt() {
		Session session = getSession();
		List<Object[]> list = session.createSQLQuery("SELECT SUM(grossWeight),SUM(quantity) FROM LotStock WHERE lotItemName = 'GoldLotStock'").list();		
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> sumofSilverLotStockGwt() {
		Session session = getSession();
		List<Object[]> list = session.createSQLQuery("SELECT SUM(grossWeight),SUM(quantity) FROM LotStock WHERE lotItemName = 'SilverLotStock'").list();
		return list;
	}

	public void updateexitingTaggedlotStock(BigDecimal gwt,BigDecimal nwt, String itemcode) {
		 Object[] param ={gwt,nwt,itemcode};
		 String query = "update LotStock l set l.grossWeight = l.grossWeight + ?,netWeight = l.netWeight + ? where l.lotCode = ?";
		 getHibernateTemplate().bulkUpdate(query, param);
	}

	public void tagissueUpdate(String tagNo) {
		Object[] param ={tagNo};
		 String query = "update LotStock l set l.grossWeight = 0.000,l.netWeight = 0.000,l.quantity=0,l.lotType='Tagissued' where l.lotCode = ?";
		 getHibernateTemplate().bulkUpdate(query, param);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<LotStock> searchLotStockByLotCode(String LotCode){	
		return (List<LotStock>)getHibernateTemplate().find("FROM LotStock WHERE lotCode = ?",LotCode);
	}
	

	//********************** Return Lot Stock Methods *********************************//
	
	@SuppressWarnings("unchecked")
	public List<LotStock> listReturnGoldLotStock(){	
		return (List<LotStock>)getHibernateTemplate().find("FROM LotStock WHERE lotItemName = 'GoldLotStock' AND lotType='Return Lot Stock'");
	}
	
	@SuppressWarnings("unchecked")
	public List<LotStock> listReturnSilverLotStock(){	
		return (List<LotStock>)getHibernateTemplate().find("FROM LotStock WHERE lotItemName = 'SilverLotStock' AND lotType='Return Lot Stock'");
	}
}