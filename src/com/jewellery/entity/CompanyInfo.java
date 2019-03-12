package com.jewellery.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
	@NamedQuery(name="listAllInfo",query = "from CompanyInfo")
				
})

public class CompanyInfo {
	
	private String companyName;
	private String companyAddress;
	private String city;
	private String country;
	private String pincode;
	private String phone;
	private String fax;
	private String PanNumber;
	private String TinNumber;
	private String CSTNumber;
	private String website;
	private String Reference;
	private String C_email;
	private String posBarcodePrint;
	private String ornBarcodePrint;
	private String posInvoicePrint;
	private String ornInvoicePrint;
	
	@Id
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPanNumber() {
		return PanNumber;
	}

	public void setPanNumber(String panNumber) {
		PanNumber = panNumber;
	}

	public String getTinNumber() {
		return TinNumber;
	}

	public void setTinNumber(String tinNumber) {
		TinNumber = tinNumber;
	}

	public String getCSTNumber() {
		return CSTNumber;
	}

	public void setCSTNumber(String cSTNumber) {
		CSTNumber = cSTNumber;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getReference() {
		return Reference;
	}

	public void setReference(String reference) {
		Reference = reference;
	}

	public String getC_email() {
		return C_email;
	}

	public void setC_email(String c_email) {
		C_email = c_email;
	}

	public String getPosBarcodePrint() {
		return posBarcodePrint;
	}

	public void setPosBarcodePrint(String posBarcodePrint) {
		this.posBarcodePrint = posBarcodePrint;
	}

	public String getPosInvoicePrint() {
		return posInvoicePrint;
	}

	public void setPosInvoicePrint(String posInvoicePrint) {
		this.posInvoicePrint = posInvoicePrint;
	}

	public String getOrnBarcodePrint() {
		return ornBarcodePrint;
	}

	public void setOrnBarcodePrint(String ornBarcodePrint) {
		this.ornBarcodePrint = ornBarcodePrint;
	}

	public String getOrnInvoicePrint() {
		return ornInvoicePrint;
	}

	public void setOrnInvoicePrint(String ornInvoicePrint) {
		this.ornInvoicePrint = ornInvoicePrint;
	}
}
