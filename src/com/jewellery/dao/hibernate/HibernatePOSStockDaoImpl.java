package com.jewellery.dao.hibernate;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.POSStockDao;
import com.jewellery.entity.POSStock;


public class HibernatePOSStockDaoImpl extends HibernateDaoSupport implements POSStockDao
{
	public static final String GET_ITEM_CODE = "getPOSStockCode";
			
	@Transactional
	public void updatePOSStock(POSStock posStock){
		getHibernateTemplate().update(posStock);		
	}
	
	public void deletePOSStock(POSStock posStock){
		getHibernateTemplate().delete(posStock);
	}

	@SuppressWarnings("unchecked")
	public List<POSStock> getStockCode() {		
		return (List<POSStock>)getHibernateTemplate().findByNamedQuery(GET_ITEM_CODE);
	}

	public void insertPOSStock(POSStock posStock) {
		getHibernateTemplate().save(posStock);
		//getHibernateTemplate().flush();
		//getHibernateTemplate().clear();
	}	
	 
	public void updatePOSStock(Integer qty, String transaction, String barcodeId){
		Object[] param = {qty, transaction, barcodeId};
		String query = "update POSStock set qtySet = ?, transactionType = ? where barcodeId = ?";
		getHibernateTemplate().bulkUpdate(query, param);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<POSStock> searchposTag(String icode) {
		Object[] obj ={icode};
		return (List<POSStock>) getHibernateTemplate().find("from POSStock where barcodeId = ?",obj);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<POSStock> POSTagByReference(Integer referenceId){
		Object[] obj = {referenceId};
		return(List<POSStock>) getHibernateTemplate().find("from POSStock where itemID = ?",obj);
	}
	public void deleteBarcodeId(String barcodeId) {
		
		Object[] param = {barcodeId};
		String query = "update POSStock set deleted = 'YES',qtyset=0 where barcodeId=?";
		getHibernateTemplate().bulkUpdate(query, param);	
	}
}