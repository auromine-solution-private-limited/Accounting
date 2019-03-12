package com.jewellery.dao.hibernate;


import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.JobOrderDao;
import com.jewellery.entity.JobOrder;

public class HibernateJobOrderDaoImpl extends HibernateDaoSupport implements JobOrderDao
{
	public static final String GET_BY_JO_NAME = "getJobOrderByName";
	public static final String LIST_BY_JOBORDER= "listJobOrder";
	public static final String SEARCH_JOB_ORDER = "getJobOrders";

	// Auto Complete JobOrder - Smith Name Search Box
	@SuppressWarnings("unchecked")
	@Transactional
	public List<String> getAutoSmithName(String smithNamePart){
		Object[] obj = {smithNamePart+"%"};
		return (List<String>)getHibernateTemplate().find("SELECT DISTINCT smithName FROM JobOrder WHERE smithName like ?",obj);		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<JobOrder> listJobOrder(){
	//	System.out.println("Executing JobOrder Query:::::::::::::::::::::::::::::::::::::::::::::::");
		return (List<JobOrder>)getHibernateTemplate().findByNamedQuery(LIST_BY_JOBORDER);
	}

	@Transactional
	public JobOrder getJobOrder(Integer id){	
		return (JobOrder)getHibernateTemplate().get(JobOrder.class, id);
	}	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<JobOrder> searchJobOrder(final Integer order){
		return (List<JobOrder>) getHibernateTemplate().findByNamedQuery(SEARCH_JOB_ORDER, order);
	}
	
	
	@Transactional(readOnly=true)
	public JobOrder getJobOrder(String smithName){
		System.out.println("Method getJobOrder by smith name::::::::::::::::::::::::::::::::::::::");
		Session session = getSession();
		try{
			Query query = session.getNamedQuery(GET_BY_JO_NAME);
			query.setParameter("Smith Name", smithName);
		
			return (JobOrder)query.uniqueResult();
		}finally{
			releaseSession(session);
		}		
	} 
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<JobOrder> searchSmith(String smithName)
	{
		Integer id;
		try{
			id=Integer.parseInt(smithName);
		}
		catch(NumberFormatException e)
		{
			id=null;
		}
		
		List<JobOrder> sname = null;
			
		if(smithName != null)
		{
			Object[] obj = {id,smithName};
			sname = getHibernateTemplate().find("from JobOrder J where J.orderNo = ? or J.smithName = ?", obj);				
		}
		
		if (sname != null)
		{
			return sname;
		}
		return null;							
	}
	
	@Transactional
	public void insertJobOrder(JobOrder joborder){		
		getHibernateTemplate().saveOrUpdate(joborder);		
	}

	@Transactional
	public void updateJobOrder(JobOrder joborder) {		
		getHibernateTemplate().update(joborder);
    }

	@Transactional
    public void deleteJobOrder(JobOrder joborder) {		
      getHibernateTemplate().delete(joborder);
    }			
}