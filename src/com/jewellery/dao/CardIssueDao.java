package com.jewellery.dao;

import java.math.BigInteger;
import java.util.List;

import com.jewellery.entity.CardIssue;

public interface CardIssueDao {
	
	public void insertCardIssue(CardIssue cardIssue);
	public void updateCardIssue(CardIssue cardIssue);
	public void deleteCardIssue(CardIssue cardIssue);
	public CardIssue getissuedCardById(Integer id);
	public List<CardIssue> cardissueList();
	public String getIssuedSchemeName(String schemeName);
	//public String getjoiningDate(String schemeName);
	public BigInteger amountinGrams();
	public BigInteger amountinCash();
	public List<CardIssue> getSSCCustomerSchemeList(String customerName); // For SSPayment SchemeList
	public List<CardIssue> getSSRCustomerSchemeList(String customerName); // For SSReceipt SchemeList
	public List<String> SSRSchemeSet(String customerName); // For SSReceipt View Mode "Scheme list"  
	public List<String> SSCSchemeSet(String customerName); // For SSPayment View Mode "Scheme list" 
	public List<CardIssue> SSRgetCardDetails(String customerName,String SchemeName); // For SSReceipt CardIssueDao ajax call
	public List<CardIssue> SSCgetCardDetails(String customerName,String SchemeName); // For SSPayment CardIssueDao ajax call
	public List<String> SSRCardNoSet(String customerName,String SchemeName); // For SSReceipt View Mode "card list" 
	public List<String> SSCCardNoSet(String customerName,String SchemeName); // For SSPayment View Mode "card list"
	public List<CardIssue> getSSRPLedgerCardIssueList(String customerName, String cardNO); // For SSReceipt and SSPayment Ledger Updation  
	public String getSchemeType(String SchemeName); //to get Scheme Type based on scheme Name
	public List<CardIssue> getCardNoDetails(String cardNo); //get Card No list based on cardNO details
	public List<CardIssue> getCardIssueList(String customerName, String cardNO); //based on customerName and cardNo details
	public List<String> getCustomerActiveSet(); // To retrieve active Customer list for SSR And SSP
	public List<String> getCustomerActiveMatureSet() ; // To retrieve active and Matured Customer list for SSR And SSP
	public List<CardIssue> getCardIssueCardNO(String cardNO);  // For SSReceipt to retrieve cardIssue based on Card NO
	public List<String> SSSalesCardNoList(String customerName); // For Sales to Retrieve Card NO List Based on Customer Name
	public List<CardIssue> getCardDetailList(String cardNo); // For SSReceipt cardDetail based on Card No
}
