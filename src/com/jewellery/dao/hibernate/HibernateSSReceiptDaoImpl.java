package com.jewellery.dao.hibernate;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.jewellery.dao.SSReceiptDao;
import com.jewellery.entity.Saving_SchemeReceipt;

public class HibernateSSReceiptDaoImpl extends HibernateDaoSupport implements SSReceiptDao
{	
	@Transactional
	public void insertSSReceipt(Saving_SchemeReceipt ssReceipt){		
		getHibernateTemplate().saveOrUpdate(ssReceipt);
	}
	
	@Transactional
	public void updateSSReceipt(Saving_SchemeReceipt ssReceipt){		
		getHibernateTemplate().update(ssReceipt);
	}
	
	@Transactional
	public void deleteSSReceipt(Saving_SchemeReceipt ssReceipt){		
		getHibernateTemplate().delete(ssReceipt);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Saving_SchemeReceipt> listSSReceipt(){		
		return (List<Saving_SchemeReceipt>) getHibernateTemplate().find("from Saving_SchemeReceipt where formType='Saving Scheme Receipt'");
	}
	
	@SuppressWarnings("unchecked")
	public List<Saving_SchemeReceipt> listSSPayment(){
		return (List<Saving_SchemeReceipt>) getHibernateTemplate().find("from Saving_SchemeReceipt where formType='Saving Scheme Payment'");
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Saving_SchemeReceipt> getSSReceipt(Integer receiptId){
		return (List<Saving_SchemeReceipt>) getHibernateTemplate().find("FROM Saving_SchemeReceipt where receiptId=?",receiptId);
	}
	
	@SuppressWarnings("unchecked")
	public BigInteger getAutoSSRCode(){
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(receiptNO,'R',-1),UNSIGNED INTEGER)) FROM Saving_SchemeReceipt WHERE receiptNO LIKE 'CR%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public BigInteger getAutoSSPCode(){
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(receiptNO,'P',-1),UNSIGNED INTEGER)) FROM Saving_SchemeReceipt WHERE receiptNO LIKE 'CP%'");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}

	public String getcardNo(String cardNo) {
		
		Session session = getSession();
		try{
		Query query=session.createQuery("SELECT cardNo FROM Saving_SchemeReceipt WHERE cardNo =:cardNo");
		query.setParameter("cardNo", cardNo);
		String list=(String)query.list().get(0);
			return(list);
			}
		catch(Exception e){}
		finally{
			releaseSession(session);
		}
		return "empty";	 
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getSSRLastCardNo (String currentCardNO){
		return (List<String>) getHibernateTemplate().find("SELECT receiptNO FROM Saving_SchemeReceipt WHERE formType='Saving Scheme Receipt' AND cardNo = ? ORDER BY receiptId DESC LIMIT 1", currentCardNO);
	}
		
}
