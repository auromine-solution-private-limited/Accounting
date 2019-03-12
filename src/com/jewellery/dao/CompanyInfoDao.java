package com.jewellery.dao;

import java.util.List;
import com.jewellery.entity.CompanyInfo;


public interface CompanyInfoDao {	
	public void insertCompanyInfo(CompanyInfo cinfo);
	public void updateCompanyInfo(CompanyInfo cinfo);
	public List<CompanyInfo> listCompanyInfo();
}