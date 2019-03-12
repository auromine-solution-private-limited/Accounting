package com.jewellery.dao.hibernate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.ItemMasterDao;
import com.jewellery.entity.ItemMaster;

public class HibernateItemMasterDaoImpl extends HibernateDaoSupport implements ItemMasterDao
{
	public static final String LIST_ALL_ITEMS = "listAllItems";
	public static final String SEARCH_ITEM = "getItems";
	public static final String LIST_ITEM="listItems";
	public static final String LIST_ITEM_STOCK="Itemstocklist";
	public static List <ItemMaster> imast;

	public static final String GET_ITEM_BY_CODE = "getItemsByCode";
	public static final String GET_ITEMCODE="listItemCode";
	public static final String LIST_ITEM_WEIGHT = "itemWeightList";
	
	// Auto Complete ItemCode List for Sales Form
	@SuppressWarnings("unchecked")
	@Transactional
	public List<String> getAutoItemCode(String itemCodePart){
		Object[] obj = {itemCodePart+"%"};
		return (List<String>)getHibernateTemplate().find("SELECT itemCode FROM ItemMaster WHERE itemCode like ?",obj);
	}
	
	// Auto Complete ItemNames List for itemNames Search
	@SuppressWarnings("unchecked")
	@Transactional
	public List<String> getAutoItemName(String itemNamePart){
		Object[] obj = {itemNamePart+"%"};
		return (List<String>)getHibernateTemplate().find("SELECT DISTINCT itemName FROM ItemMaster WHERE itemName like ?",obj);		
	}
	
	@Transactional
	public void updateVaPercentage(BigDecimal lessPercent, BigDecimal vaPercentage, BigDecimal mc,BigDecimal mcrupees, BigDecimal vat, String metalGroup, String subCategoryName, BigDecimal categoryHMCharges){
		 Object[] param = {lessPercent, vaPercentage, mc,mcrupees, vat, categoryHMCharges, metalGroup,subCategoryName};
		 String query = "update ItemMaster i set i.lessPercentage = ?, i.vaPercentage = ?, i.mcPerGram = ?,i.mcInRupee=?, i.tax = ?,i.itemHMCharges = ?  where (i.metalUsed = ? and i.subCategoryName = ?) ";	 
		 getHibernateTemplate().bulkUpdate(query, param);	
	}
	
	@Transactional
	public void updateuntagstock(BigDecimal totalGrossWeight,BigDecimal grossWeight,BigDecimal netWeight, int qty)
	{
		Object[] param={totalGrossWeight,grossWeight,netWeight,qty};
		String query="update ItemMaster i set totalGrossWeight=totalGrossWeight-?,grossWeight=grossWeight-?,netWeight=netWeight-?,qty=qty-? where itemCode='IT100001' and metalUsed='Gold Ornaments'";
		 getHibernateTemplate().bulkUpdate(query, param);
	}
	
	@Transactional
	public void updateuntagstocksilver(BigDecimal totalGrossWeight,BigDecimal grossWeight,BigDecimal netWeight, int qty)
	{
		Object[] param={totalGrossWeight,grossWeight,netWeight,qty};
		String query="update ItemMaster i set totalGrossWeight=totalGrossWeight-?,grossWeight=grossWeight-?,netWeight=netWeight-?,qty=qty-? where itemCode='IT100006' and metalUsed='Silver Ornaments'";
	    getHibernateTemplate().bulkUpdate(query, param);
	}	

	@SuppressWarnings("unchecked")
	public List<ItemMaster> listItemWeight(){		
		return (List<ItemMaster>)getHibernateTemplate().findByNamedQuery(LIST_ITEM_WEIGHT);
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemMaster> listGoldWeight(){
		return (List<ItemMaster>)getHibernateTemplate().find("SELECT SUM(totalGrossWeight) FROM ItemMaster WHERE(itemCode > 'IT100010' AND metalUsed = 'Gold Ornaments')");
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemMaster> listSilverWeight(){
		return (List<ItemMaster>)getHibernateTemplate().find("SELECT SUM(totalGrossWeight) FROM ItemMaster WHERE(itemCode > 'IT100010' AND metalUsed = 'Silver Ornaments')");
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<ItemMaster> listItemMaster(){		
		return (List<ItemMaster>)getHibernateTemplate().findByNamedQuery(LIST_ALL_ITEMS);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<ItemMaster> listAllItems() {
		
		return (List<ItemMaster>)getHibernateTemplate().findByNamedQuery(LIST_ITEM);
	}	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<ItemMaster> Itemstocklist(){
		return (List<ItemMaster>)getHibernateTemplate().findByNamedQuery(LIST_ITEM_STOCK);	
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<ItemMaster> getItemsByCode(String item_code){
		
		return (List<ItemMaster>)getHibernateTemplate().findByNamedQuery(GET_ITEM_BY_CODE, item_code);	
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<ItemMaster> searchItemMaster(final String itemCode){		
		return (List<ItemMaster>) getHibernateTemplate().findByNamedQuery(SEARCH_ITEM, itemCode);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<ItemMaster> searchItemName(String item, BigDecimal weight){
		List<ItemMaster> items = null;		

		if( weight.signum() == 1)
		{
			Object[] obj = {item, weight};
			items = getHibernateTemplate().find("from ItemMaster i where (i.itemName = ? and i.netWeight <= ? and i.qty != 0)", obj);
		}
		else
		{			
			items = getHibernateTemplate().find("from ItemMaster i where (i.itemName = ? and i.qty != 0)", item);
		}	
		
		return items;			
	}	
	
	@SuppressWarnings("unchecked")
	public BigInteger getItemCode(){
		Session session = getSession();
		Query query = session.createSQLQuery("Select MAX(CONVERT(SUBSTRING_INDEX(itemCode,'T',-1),UNSIGNED INTEGER)) FROM ItemMaster");
		List<BigInteger> resultBig = query.list();
		session.close();		
		return resultBig.get(0);
	}
	
	@Transactional
	public  ItemMaster getItemMaster(Integer id){	
		return (ItemMaster) getHibernateTemplate().get(ItemMaster.class, id);
	}	
		
	@Transactional
	public void updateItemMaster(ItemMaster itemMaster){
		getHibernateTemplate().update(itemMaster);		
	}
	
	public void deleteItemMaster(ItemMaster itemMaster){
		getHibernateTemplate().delete(itemMaster);
	}

	@SuppressWarnings("unchecked")
	public List<ItemMaster> listAllitemCode() {
		
		return (List<ItemMaster>)getHibernateTemplate().findByNamedQuery(GET_ITEMCODE);
	}	
	
	@Transactional
	public void updateSalesStock(BigDecimal totalGrossWeight,int qty, int totalpcs, String itemCode)
	{
		Object[] salesParam = {totalGrossWeight, qty, totalpcs, itemCode};
		String query="update ItemMaster set totalGrossWeight = ?, totalPieces = ?, qty= ? where itemCode = ?";
	    getHibernateTemplate().bulkUpdate(query, salesParam);
	}
	@Transactional
	public void updateLooseCategorySalesStock(BigDecimal totalGrossWeight,BigDecimal grossWeight,BigDecimal netWeight,int qty, String itemCode)
	{
		Object[] salesParam = {totalGrossWeight,grossWeight,netWeight, qty, itemCode};
		String query="update ItemMaster set totalGrossWeight = ?,grossWeight=?,netWeight=?, qty= ? where itemCode = ? ";
		getHibernateTemplate().bulkUpdate(query, salesParam);
	}
	
	@Transactional
	public void updateLooseStock(BigDecimal totalGrossWeight,BigDecimal grossWeight,BigDecimal netWeight, int qty, String itemCode)
	{
		Object[] param={totalGrossWeight,grossWeight,netWeight,qty, itemCode};
		String query="update ItemMaster i set totalGrossWeight=totalGrossWeight-?,grossWeight=grossWeight-?,netWeight=netWeight-?,qty=qty-? where itemCode= ?";
	    getHibernateTemplate().bulkUpdate(query, param);
	}
	/* Method for Sales Updates*/
	@Transactional
	public void updateSalesLooseStock(BigDecimal totalGrossWeight,BigDecimal grossWeight,BigDecimal netWeight, String itemCode)
	{
		Object[] param={totalGrossWeight,grossWeight,netWeight, itemCode};
		String query="update ItemMaster i set totalGrossWeight=?, grossWeight=?, netWeight=? where itemCode= ?";
	    getHibernateTemplate().bulkUpdate(query, param);
	}
	@Transactional
	public void updateSalesCategoryLooseStock(BigDecimal totalGrossWeight,BigDecimal grossWeight,BigDecimal netWeight,int qty, String itemCode)
	{
		Object[] param={totalGrossWeight,grossWeight,netWeight,qty, itemCode};
		String query="update ItemMaster i set totalGrossWeight=?, grossWeight=?, netWeight=?,qty=? where itemCode= ?";
		getHibernateTemplate().bulkUpdate(query, param);
	}

	public BigDecimal purchasedStock(String metalType) {
		Session session = getSession();
		try{
		Query query=session.createQuery("SELECT grossWeight FROM ItemMaster WHERE itemName =:itemName");
		query.setParameter("itemName", metalType);
		return(BigDecimal)query.list().get(0);
		}finally{
			releaseSession(session);
		}	 
	}
	public Integer purchasedStockuntag(String metalType) {
		Session session = getSession();
			
		try{
			Query query=session.createQuery("SELECT qty FROM ItemMaster WHERE itemName =:itemName");
			query.setParameter("itemName", metalType);
			return(Integer)query.list().get(0);
			}finally{
				releaseSession(session);
			}	
	}
	@Transactional
	public void updateOldStock(BigDecimal totalGrossWeight,BigDecimal grossWeight,BigDecimal netWeight, int qty, String itemCode)
	{
		Object[] param={totalGrossWeight,grossWeight,netWeight, qty, itemCode};
		String query="update ItemMaster set totalGrossWeight = totalGrossWeight + ?,grossWeight = grossWeight + ?, netWeight = netWeight + ?, qty = qty + ? WHERE itemCode = ?";
		getHibernateTemplate().bulkUpdate(query, param);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ItemMaster> getBullionVat(String itemcode){
		return (List<ItemMaster>)getHibernateTemplate().find("FROM ItemMaster WHERE itemCode = ?", itemcode);		
	}

	
//Add on 29-11-12 for low metal stock
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public List<ItemMaster> lowMetalStockList(){
		Session session = getSession();
			Query query=session.createSQLQuery("SELECT i.subcategoryname,i.categoryname,c.rolquantity,SUM(i.qty) as sumqty FROM   Category c INNER JOIN Category_ItemMaster ci ON c.categoryId = ci.categoryId INNER JOIN ItemMaster i ON ci.itemId = i.itemId GROUP BY c.categoryId having (sumqty < c.rolquantity)" );
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List list=query.list();
			Iterator iterat=list.iterator();
			while(iterat.hasNext()){
				Map map=(Map) iterat.next();
				//System.out.println("SUb catogery::::"+map.get(list));
			}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<ItemMaster> getBaseCategoryName(String categoryName) {
		return (List<ItemMaster>)getHibernateTemplate().find("FROM ItemMaster WHERE subcategoryName = ?", categoryName);
	}

	@SuppressWarnings("unchecked")
	public List<ItemMaster> searchItemListByBaseCategoryName(String baseCategoryName) {
		return (List<ItemMaster>)getHibernateTemplate().find("FROM ItemMaster WHERE categoryName = ?", baseCategoryName);
	}
}