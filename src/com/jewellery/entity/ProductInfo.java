package com.jewellery.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity 
public class ProductInfo {
	
	private Integer productVersionId;
	private String productName;
	private String segmentTypeS;
	private String productversion;
	private String versionType;
	private Integer numberofDays=15;
	private Date installationDate;
	private Date lastUsedDate;
	private String private1;
	private String private2;
	private String plk;
	
	/**
	 * @return the productVersionId
	 */
	@Id
	@GeneratedValue
	public Integer getProductVersionId() {
		return productVersionId;
	}
	/**
	 * @param productVersionId the productVersionId to set
	 */
	public void setProductVersionId(Integer productVersionId) {
		this.productVersionId = productVersionId;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the segmentTypeS
	 */
	public String getSegmentTypeS() {
		return segmentTypeS;
	}
	/**
	 * @param segmentTypeS the segmentTypeS to set
	 */
	public void setSegmentTypeS(String segmentTypeS) {
		this.segmentTypeS = segmentTypeS;
	}
	/**
	 * @return the productversion
	 */
	public String getProductversion() {
		return productversion;
	}
	/**
	 * @param productversion the productversion to set
	 */
	public void setProductversion(String productversion) {
		this.productversion = productversion;
	}
	/**
	 * @return the versionType
	 */
	public String getVersionType() {
		return versionType;
	}
	/**
	 * @param versionType the versionType to set
	 */
	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}
	/**
	 * @return the numberofDays
	 */
	public Integer getNumberofDays() {
		return numberofDays;
	}
	/**
	 * @param numberofDays the numberofDays to set
	 */
	public void setNumberofDays(Integer numberofDays) {
		this.numberofDays = numberofDays;
	}
	/**
	 * @return the installationDate
	 */
	public Date getInstallationDate() {
		return installationDate;
	}
	/**
	 * @param installationDate the installationDate to set
	 */
	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}
	/**
	 * @return the sysDate
	 */
	/**
	 * @return the lastUsedDate
	 */
	public Date getLastUsedDate() {
		return lastUsedDate;
	}
	/**
	 * @param lastUsedDate the lastUsedDate to set
	 */
	public void setLastUsedDate(Date lastUsedDate) {
		this.lastUsedDate = lastUsedDate;
	}
	
	@Column(columnDefinition="BLOB", nullable = true, length=50)
	public String getPrivate1() {
		return private1;
	}
	public void setPrivate1(String private1) {
		this.private1 = private1;
	}
	@Column(columnDefinition="BLOB", nullable = true, length=50)
	public String getPrivate2() {
		return private2;
	}
	public void setPrivate2(String private2) {
		this.private2 = private2;
	}
	@Column(columnDefinition="BLOB", nullable = true, length=50)
	public String getPlk() {
		return plk;
	}
	public void setPlk(String plk) {
		this.plk = plk;
	}
}
