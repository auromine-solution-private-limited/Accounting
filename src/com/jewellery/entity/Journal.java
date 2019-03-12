package com.jewellery.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@NamedQueries({ @NamedQuery(name = "listAllJournal", query = "from Journal"), 
	@NamedQuery (name = "getBillCode", query = "FROM Journal WHERE journalNO = (SELECT journalNO FROM Journal WHERE journalId = (SELECT MAX(journalId) FROM Journal WHERE journalNO LIKE 'BR%'))")
})

@Table( name="journal",
uniqueConstraints = { @UniqueConstraint ( columnNames = {"journalNO" } ) } )

public class Journal {
private Integer journalId;
private String transactionId;
private String journalNO;
private String journalType;
private  Date journalDate;
private String debitCode;
private String creditCode;
private String debitAccountName;
private String creditAccountName;
private BigDecimal debitAmount = new BigDecimal("0.00");
private BigDecimal creditAmount = new BigDecimal("0.00");
private String narration;
private String receiptType;

public Journal()
{
	
}
@Id
@GeneratedValue
public Integer getJournalId() {
	return journalId;
}
public void setJournalId(Integer journalId) {
	this.journalId = journalId;
}

public String getTransactionId() {
	return transactionId;
}
public void setTransactionId(String transactionId) {
	this.transactionId = transactionId;
}

public String getJournalNO() {
	return journalNO;
}
public void setJournalNO(String journalNO) {
	this.journalNO = journalNO;
}
public String getDebitAccountName() {
	return debitAccountName;
}
@Column(nullable = false, length = 50)
public void setDebitAccountName(String debitAccountName) {
	this.debitAccountName = debitAccountName;
}

public String getCreditAccountName() {
	return creditAccountName;
}
@Column(nullable = false, length = 50)
public void setCreditAccountName(String creditAccountName) {
	this.creditAccountName = creditAccountName;
}

public Date getJournalDate() {
	return journalDate;
}
@Column(nullable = false, length = 50)
public void setJournalDate(Date journalDate) {
	this.journalDate = journalDate;
}

public String getJournalType() {
	return journalType;
}
public void setJournalType(String journalType) {
	this.journalType = journalType;
}
public String getNarration() {
	return narration;
}
public void setNarration(String narration) {
	this.narration = narration;
}
public BigDecimal getDebitAmount() {
	return debitAmount;
}
public void setDebitAmount(BigDecimal debitAmount) {
	this.debitAmount = debitAmount;
}
public BigDecimal getCreditAmount() {
	return creditAmount;
}
public void setCreditAmount(BigDecimal creditAmount) {
	this.creditAmount = creditAmount;
}

/**
 * @return the receiptType
 */
public String getReceiptType() {
	return receiptType;
}
/**
 * @param receiptType the receiptType to set
 */
public void setReceiptType(String receiptType) {
	this.receiptType = receiptType;
}
public String getDebitCode() {
	return debitCode;
}
public void setDebitCode(String debitCode) {
	this.debitCode = debitCode;
}
public String getCreditCode() {
	return creditCode;
}
public void setCreditCode(String creditCode) {
	this.creditCode = creditCode;
}

}
