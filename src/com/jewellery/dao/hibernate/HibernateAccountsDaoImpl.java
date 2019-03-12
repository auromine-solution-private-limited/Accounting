package com.jewellery.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.AccountsDao;
import com.jewellery.entity.Accounts;


public class HibernateAccountsDaoImpl extends HibernateDaoSupport implements AccountsDao
{
	public static final String GET_BY_ACCT_NAME = "getAccountsByName";
	public static final String LIST_BY_ACCT_NAME = "listAccountsByName";
	public static final String LIST_BY_ACCT_Id = "getAccountId";

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Accounts> listAccounts(){
	//	System.out.println("Executing Accounts Query:::::::::::::::::::::::::::::::::::::::::::::::");
		return (List<Accounts>)getHibernateTemplate().findByNamedQuery(LIST_BY_ACCT_NAME);
	}

	@Transactional
	public Accounts getAccounts(Integer id){	
		//System.out.println("Method getAccounts by Id:::::::::::::::::::::::::::::::::::::::::::::::::::::");

		if(getHibernateTemplate()!=null){
			return (Accounts)getHibernateTemplate().get(Accounts.class, id);
		}else{
		System.out.println("getHibernateTemplate() returns null value");
		return (Accounts)getHibernateTemplate().get(Accounts.class, id);
		}
	
	}	

	@Transactional(readOnly=true)
	public Accounts getAccounts(String account){
	//	System.out.println("Method getAccounts by String::::::::::::::::::::::::::::::::::::::::::::::::");
		Session session = getSession();
		try{
			Query query = session.getNamedQuery(LIST_BY_ACCT_NAME);
			query.setParameter("account", account);
			return (Accounts)query.uniqueResult();
		}finally{
			releaseSession(session);
		}		
	}

	@Transactional
	public void updateDebitBalance(BigDecimal closingBalance, String closingType, String accountGroup){
		Object[] param = {closingBalance,closingType ,accountGroup};
		String query = "update Accounts a set a.closingBalance = a.closingBalance +  ?, a.closingType = ? where a.accountGroup = ?";
		getHibernateTemplate().bulkUpdate(query, param);
	}
	
	@Transactional
	public void updateCreditBalance(BigDecimal closingBalance, String closingType, String accountGroup){
		Object[] param = {closingBalance,closingType ,accountGroup};
		String query = "update Accounts a set a.closingBalance = a.closingBalance -  ?, a.closingType = ? where a.accountGroup = ?";
		getHibernateTemplate().bulkUpdate(query, param);
	}
	
	@Transactional
	public void updateClosingBalance(BigDecimal closingBalance, String closingType, String accountGroup){
		Object[] param = {closingBalance,closingType ,accountGroup};
		String query = "update Accounts a set a.closingBalance = ? - a.closingBalance , a.closingType = ? where a.accountGroup = ?";
		getHibernateTemplate().bulkUpdate(query, param);
	}

	//account id getting for checking max limit
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Accounts> getAccountId(){
		return (List<Accounts>) getHibernateTemplate().findByNamedQuery(LIST_BY_ACCT_Id);	
	}
	
	
	@Transactional
	public void insertAccounts(Accounts account){
	System.out.println("Accounts: Executing insert method ::::::::::::");
		getHibernateTemplate().saveOrUpdate(account);		
	}

	@Transactional
	public void updateAccounts(Accounts account) {
	System.out.println("Accounts: Executing update method ::::::::::::");
		getHibernateTemplate().update(account);
    }

	@Transactional
    public void deleteAccounts(Accounts account) {
		System.out.println("Accounts: Executing delete method ::::::::::::");
      getHibernateTemplate().delete(account);
    }

	@SuppressWarnings("unchecked")
	public List<String> getAccountGroupCode(String accountGroupcode) {
		Object[] param={accountGroupcode};
		String query ="SELECT accountGroupCode FROM Accounts WHERE accountGroup=?";
		return (List<String>)getHibernateTemplate().find(query,param);
	}	
	
	@SuppressWarnings("unchecked")
	public List<Accounts> getAccountId(String accountGroupName) {
		
		List<Accounts> accountId=getHibernateTemplate().find("FROM Accounts WHERE accountGroup = ?",accountGroupName);
		return accountId;
	}	
}