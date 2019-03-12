package com.jewellery.dao;
import java.util.Date;
import java.util.List;

import com.jewellery.entity.ProductInfo;
/**
 * @author admin 
 *
 */
public interface ProductInfoDao {
	public void storeProductInfo(ProductInfo productinfo);
	public void updateProductInfo(ProductInfo productinfo);
	public void update(Integer numberofDays,Date lastUsedDate);
	public List<ProductInfo> getExistingList();
	public List<ProductInfo> getProductinfo();
}
