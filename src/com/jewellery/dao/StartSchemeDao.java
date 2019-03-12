package com.jewellery.dao;

import java.util.List;
import com.jewellery.entity.StartScheme;

public interface StartSchemeDao {
		public List<StartScheme> listStartScheme();
		public StartScheme getStartScheme(Integer start_schemeId);
		public List<StartScheme> searchStartScheme(String schemeName);
		public void insertStartScheme(StartScheme startscheme);
		public void updateStartScheme(StartScheme startscheme);
		public List<StartScheme> getSchmename();
		public String getstartedSchemeName(String schemeName);
		public List<StartScheme> searchStartSchemeSSRP(String schemeName);// For SSReceipt and SSPayment checking Scheme Duration
		public  String getStartDate(String schemeName);//add for cancel card report 
		public  String getstartDate(String schemeName);//add for cancel card report 
}
