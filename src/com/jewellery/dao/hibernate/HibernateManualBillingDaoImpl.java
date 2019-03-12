package com.jewellery.dao.hibernate;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.jewellery.dao.ManualBillingDao;
import com.jewellery.entity.ManualBilling;
import com.jewellery.entity.ManBillRowList;

@SuppressWarnings("unchecked")
public class HibernateManualBillingDaoImpl extends HibernateDaoSupport implements ManualBillingDao{

	/******************* Manual Billing  ************/
	@Transactional
	public void insertManualBilling(ManualBilling manualBilling) {
		getHibernateTemplate().save(manualBilling);	
	}

	@Transactional
	public void updateManualBilling(ManualBilling manualBilling) {
		getHibernateTemplate().update(manualBilling);	
	}

	@Transactional
	public void deleteManualBilling(ManualBilling manualBilling) {
		getHibernateTemplate().delete(manualBilling);
	}
	
	public List<ManualBilling> getManualBillingList(Integer mBillId) {
		return (List<ManualBilling>) getHibernateTemplate().find("FROM ManualBilling WHERE mBillId = ?", mBillId);
	}

	public ManualBilling getManualBilling(Integer mBillId) {
		return (ManualBilling)getHibernateTemplate().get(ManualBilling.class, mBillId);
	}
	
	public List<ManualBilling> listManualBilling(){
		return (List<ManualBilling>)getHibernateTemplate().find("FROM ManualBilling");
	}
	
	public List<ManualBilling> listVatManualBilling(){
		return (List<ManualBilling>)getHibernateTemplate().find("FROM ManualBilling where mBillFormType='vatForm'");
	}
	
	public List<ManualBilling> listVatFreeManualBilling(){
		return (List<ManualBilling>)getHibernateTemplate().find("FROM ManualBilling where mBillFormType='vatFreeForm'");
	}
	
	public BigInteger getLatestMBillVatFreeCode(){
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(mBillAutoNO,'F',-1),UNSIGNED INTEGER)) FROM ManualBilling WHERE mBillAutoNO LIKE 'MF%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	
	public BigInteger getLatestMBillCode(){
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(mBillAutoNO,'V',-1),UNSIGNED INTEGER)) FROM ManualBilling WHERE mBillAutoNO LIKE 'MV%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	
	/**************** Manual Billing Row List ****************/
	
	@Transactional
	public void insertManBillRowList(ManBillRowList manBillRowList) {
		getHibernateTemplate().save(manBillRowList);	
	}
	
	@Transactional
	public void updateManBillRowList(ManBillRowList manBillRowList) {
		getHibernateTemplate().update(manBillRowList);	
	}

	@Transactional
	public void deleteManBillRowList(ManBillRowList manBillRowList) {
		getHibernateTemplate().delete(manBillRowList);
	}

	public List<ManBillRowList> listAllManBillRowList() {
		return (List<ManBillRowList>)getHibernateTemplate().find("FROM ManBillRowList");
	}

	public ManBillRowList getManBillRowList(Integer manBillRowListId) {
		return (ManBillRowList)getHibernateTemplate().get(ManBillRowList.class, manBillRowListId);
	}

	public List<ManBillRowList> listManualBilling(Integer mBillId) {
		return (List<ManBillRowList>) getHibernateTemplate().find("FROM ManBillRowList WHERE mBillId = ?",mBillId);
	}
}
