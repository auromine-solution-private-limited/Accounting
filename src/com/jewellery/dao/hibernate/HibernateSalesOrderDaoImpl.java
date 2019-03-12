package com.jewellery.dao.hibernate;


import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.SalesOrderDao;
import com.jewellery.entity.SalesOrder;

public class HibernateSalesOrderDaoImpl extends HibernateDaoSupport implements SalesOrderDao
{
	public static final String GET_BY_SO_CNAME = "getSalesOrderByCName";
	public static final String LIST_BY_SO= "listSalesOrder";
	public static final String LIST_PENDING_SO="PendingSalesOrder";

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<SalesOrder> listSalesOrder(){
	//	System.out.println("Executing SalesOrder Query:::::::::::::::::::::::::::::::::::::::::::::::");
		return (List<SalesOrder>)getHibernateTemplate().findByNamedQuery(LIST_BY_SO);
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<SalesOrder> SalesOrderPending(){
	//	System.out.println("Executing Pending Sales Order Query::::::::::::::::::::::::::::::::::::::::");
		return (List<SalesOrder>)getHibernateTemplate().findByNamedQuery(LIST_PENDING_SO);
				
	}

	@Transactional
	public SalesOrder getSalesOrder(Integer id){	
	//	System.out.println("Method getSalesOrder by Id::::::::::::::::::::::::::::::::::::::::::::::::::");

		if(getHibernateTemplate()!=null){
			return (SalesOrder)getHibernateTemplate().get(SalesOrder.class, id);
		}else{
	//	System.out.println("getHibernateTemplate() returns null value");
		return (SalesOrder)getHibernateTemplate().get(SalesOrder.class, id);
		}
	}	

	@Transactional(readOnly=true)
	public SalesOrder getSalesOrder(String customername){
	//	System.out.println("Method getSalesOrder by customer name::::::::::::::::::::::::::::::::::::::");
		Session session = getSession();
		try{
			Query query = session.getNamedQuery(GET_BY_SO_CNAME);
			query.setParameter("customerName", customername);
			//System.out.println(query.list());
			return (SalesOrder)query.uniqueResult();
		}finally{
			releaseSession(session);
		}		
	}


	@SuppressWarnings("unchecked")
		@Transactional(readOnly=true)
		public List<SalesOrder> searchSalesOrder(Integer orderNo){	
		
		//	System.out.println("Executing Search Order Query::::::::::::::::::::::::::::::::::::::::");		
			List<SalesOrder> order = getHibernateTemplate().find("from SalesOrder s where s.salesOrderId=?", orderNo);	
			return order;					
		}
	
	@Transactional
	public void insertSalesOrder(SalesOrder salesorder){
	
		getHibernateTemplate().saveOrUpdate(salesorder);		
	}

	@Transactional
	public void updateSalesOrder(SalesOrder salesorder) {
	
		getHibernateTemplate().update(salesorder);
    }

	@Transactional
    public void deleteSalesOrder(SalesOrder salesorder) {
	
      getHibernateTemplate().delete(salesorder);
    }		
	
	@SuppressWarnings("unchecked")
	public List<SalesOrder> getSalesOrderId(String CustomerName){
		
		String query=("select salesOrderId from SalesOrder where (customerName = ? and orderStatus ='Accepted' and advance != 0)");
		return(List<SalesOrder>)getHibernateTemplate().find(query,CustomerName);
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<SalesOrder> getCustomerId(String customeR){	
		//System.out.println(customeR);
		return (List<SalesOrder>)getHibernateTemplate().find("from SalesOrder where customerName = ?",customeR);
	
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<SalesOrder> getOrderAdvance(Integer id){	
		//System.out.println(id);
		return (List<SalesOrder>)getHibernateTemplate().find("from SalesOrder where salesOrderId = ?",id);
	
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<SalesOrder> getOrderFixAmt(Integer id){	
		//System.out.println(id);
		return (List<SalesOrder>)getHibernateTemplate().find(" from SalesOrder where salesOrderId = ? and rateFix='Fix' ",id);
		
	}
	
	public void UpdateStatusID(Integer id){
		String query=("update SalesOrder set orderStatus='Sold' where salesOrderId=?");
		getHibernateTemplate().bulkUpdate(query, id);
	}
	
	public void UpdateStatusToAccept(Integer id){
		String query=("update SalesOrder set orderStatus='Accepted' where salesOrderId=?");
		getHibernateTemplate().bulkUpdate(query, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<SalesOrder> ViewSalesOrderId(String CustomerName,Integer id){
		System.out.println("Retrive SalesOrderID Executing:::::::::::::");
		Object[] param={CustomerName,id};
		String query=("select salesOrderId from SalesOrder where ((customerName = ? and orderStatus ='Accepted' and advance != 0) or salesOrderId=?)");
		return (List<SalesOrder>)getHibernateTemplate().find(query, param);
	}
	
}