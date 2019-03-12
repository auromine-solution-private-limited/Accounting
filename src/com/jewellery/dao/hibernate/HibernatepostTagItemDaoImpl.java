package com.jewellery.dao.hibernate;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.PostagItemDao;
import com.jewellery.entity.PostagItem;

public class HibernatepostTagItemDaoImpl extends HibernateDaoSupport implements PostagItemDao{

	public static final String GET_POS_ITEM_LIST = "getposTagedList";
	public static final String GET_POS_ITEM_TAG_PRINTED_LIST = "getposTagedPrintedList";
	public static final String GET_POS_ITEM_PRINTED_LIST="getposprintedTagedList";
	
	public void save(PostagItem postagitem) {
		getHibernateTemplate().save(postagitem);	
		getHibernateTemplate().flush();
		getHibernateTemplate().clear();
	}

	public void update(PostagItem postagitem) {		
		getHibernateTemplate().update(postagitem);
	}

	public void delete(PostagItem postagItem) {				
		
	}
	
	@SuppressWarnings("unchecked")
	public BigInteger getposItemCode() {
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(barcodeId,'S',-1),UNSIGNED INTEGER)) from PostagItem");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PostagItem> getposItemList() {
		return(List<PostagItem>)getHibernateTemplate().findByNamedQuery(GET_POS_ITEM_LIST);
	}
	//Printed list for tagged item add on 30-1-13
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PostagItem> getposPrintedItemList() {
		return(List<PostagItem>)getHibernateTemplate().findByNamedQuery(GET_POS_ITEM_TAG_PRINTED_LIST);
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PostagItem> getposprintedTagedList() {
		
		return(List<PostagItem>)getHibernateTemplate().findByNamedQuery(GET_POS_ITEM_PRINTED_LIST);
	}
	public PostagItem getpostagId(Integer postagid) {	
		return (PostagItem) getHibernateTemplate().get(PostagItem.class, postagid);
	}

	public void updateSoldTagItem(Integer qty, String status, String barcodeID){
		Object[] param = {qty, status, barcodeID};
		String query = "update PostagItem set qtyset = ?, status = ? where barcodeId=?";
		getHibernateTemplate().bulkUpdate(query, param);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<PostagItem> POSTagByReference(String referenceId){
		Object[] obj = {referenceId};
		return(List<PostagItem>) getHibernateTemplate().find("from PostagItem where POSReferenceID = ?",obj);
	}
	
	@SuppressWarnings("unchecked")
	public List<PostagItem> getSoldTagByReference(String referenceId){
		return(List<PostagItem>) getHibernateTemplate().find("FROM PostagItem WHERE (status = 'SOLD' OR status = 'Purchase Return') AND transactionType = 'POS Purchase' AND POSReferenceID = ?",referenceId);
	}
	
@SuppressWarnings("unchecked")
public List<PostagItem> getItemCode(String itemcode){
		return(List<PostagItem>)getHibernateTemplate().find("from PostagItem where barcodeId=?",itemcode);
	}


@SuppressWarnings({ "unchecked" })
@Transactional(readOnly=false)
public List<PostagItem> getBarcodeID(String POSReferenceID,int limit) {
	 final Session session = getSession();
	 Criteria criteria = session.createCriteria(PostagItem.class);
	 criteria.add(Expression.eq("POSReferenceID",POSReferenceID));
	 criteria.add(Expression.eq("transactionType","Opening Stock"));
	 criteria.add(Restrictions.not(Restrictions.eq("status","Sold")));
	 criteria.add(Restrictions.not(Restrictions.eq("deleted","YES")));
	 criteria.add(Restrictions.not(Restrictions.eq("status", "Purchase Return")));
	 criteria.setFirstResult(0);
	 criteria.setMaxResults(limit);
	return criteria.list();
}

public void deleteBarcodeId(String barcodeId) {
	
	Object[] param = {barcodeId};
	String query = "update PostagItem set deleted = 'YES',qtyset=0 where barcodeId=?";
	getHibernateTemplate().bulkUpdate(query, param);	
}
public void refilldeletedBarcode(String barcodeId) {
	Object[] param = {barcodeId};
	String query = "update PostagItem set qtyset=1, status='Unprinted' where deleted!='YES' and barcodeId=?";
	getHibernateTemplate().bulkUpdate(query, param);
}
@SuppressWarnings("unchecked")
public List<PostagItem> searchsoldStatus(String POSReferenceID) {
	return(List<PostagItem>) getHibernateTemplate().find("FROM PostagItem WHERE (status = 'Sold' OR status = 'Purchase Return') AND transactionType = 'Opening Stock' AND POSReferenceID = ?",POSReferenceID);
}


@SuppressWarnings("unchecked")
public List<PostagItem> searchupdateBarcodeLabel(String POSReferenceID) {
	 final Session session = getSession();
	 Criteria criteria=session.createCriteria(PostagItem.class);
	 criteria.add(Expression.eq("POSReferenceID",POSReferenceID));
	 criteria.add(Restrictions.eq("transactionType","Opening Stock"));
	  return criteria.list();
}

public void updateBarcodelabel(String printName,String companyName,BigDecimal mrpinRs, BigDecimal salesRate,BigDecimal margin,BigDecimal costRate,BigDecimal discountPercentage,BigDecimal vatPercentage,Date date,String POSReferenceID) {
	Object[] param = {printName,companyName, mrpinRs,salesRate,margin,costRate,discountPercentage,vatPercentage,date,POSReferenceID};
	String query = "update PostagItem set printName = ?, companyName = ?,mrpinRs=?,salesRate=?,margin=?,costRate=?,discountPercentage=?,vatPercentage=?,date=? where transactionType='Opening Stock' and POSReferenceID=?";
	getHibernateTemplate().bulkUpdate(query, param);
	
}
public void refillstockEP(String barcodeId) {
	Object[] param = {barcodeId};
	String query = "update PostagItem set qtyset=1, status='Unprinted' where status='Sold' and barcodeId=?";
	getHibernateTemplate().bulkUpdate(query, param);
}

//Add on 21-02-13 for POS low item stock re-order level
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public List<PostagItem> POSlowItemStockList(){
			Session session = getSession();
				Query query=session.createSQLQuery("SELECT i.categoryName,i.companyName,i.itemName,p.ROL,sum(i.qtyset) as sumqty from POSItem p INNER JOIN PostagItem i on p.itemId=i.itemId GROUP BY p.itemId having (sumqty < p.ROL)" );
				query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				List list=query.list();
				Iterator iterat=list.iterator();
				while(iterat.hasNext()){
					Map map=(Map) iterat.next();
					System.out.println("pos  catogery::::"+map.get(list));
				}
			return query.list();
	}
}
