package com.jewellery.dao.hibernate;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.jewellery.dao.TransferDao;
import com.jewellery.entity.Transfer;
public class HibernateTransferDaoImpl extends HibernateDaoSupport implements TransferDao {

	public static final String LIST_ALL_TRANS = "listAllTransfer";

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Transfer> listTransfer() {
	//	System.out.println("Executing Journal Query::::::::::::::::::::");
		return (List<Transfer>)getHibernateTemplate().findByNamedQuery(LIST_ALL_TRANS);
	}
	
	@SuppressWarnings("unchecked")
	public BigInteger getTransferCode(){
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(transferNO,'R',-1),UNSIGNED INTEGER)) FROM transfer");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}

	public Transfer getTransfer(Integer id) {
	// System.out.println("Method getTransfer::::::::::::::::::::::::::");
	return (Transfer)getHibernateTemplate().get(Transfer.class,id);
	}

	@Transactional
	public void insertTransfer(Transfer transfer) {
		System.out.println("Transfer: Executing insert method ::::::::::::");
		getHibernateTemplate().saveOrUpdate(transfer);	
	}

	@Transactional
	public void updateTransfer(Transfer transfer) {
		System.out.println("Transfer: Executing update method ::::::::::::");
		getHibernateTemplate().update(transfer);
	}

	
	public void deleteTransfer(Transfer transfer) {
		System.out.println("Transfer: Executing delete method ::::::::::::");
		getHibernateTemplate().delete(transfer);	
	}

}
