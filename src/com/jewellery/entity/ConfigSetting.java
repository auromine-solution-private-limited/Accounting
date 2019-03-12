package com.jewellery.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ConfigSetting implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer configId;
	private String authorizer;
	private Set<ConfigDetailList> configMapping = new HashSet<ConfigDetailList>(0);
		
	@Id
	@GeneratedValue
	public Integer getConfigId() {
		return configId;
	}
	public void setConfigId(Integer configId) {
		this.configId = configId;
	}
	
	public String getAuthorizer() {
		return authorizer;
	}
	public void setAuthorizer(String authorizer) {
		this.authorizer = authorizer;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "configSetting")
	public Set<ConfigDetailList> getConfigMapping() {
		return configMapping;
	}
	public void setConfigMapping(Set<ConfigDetailList> configMapping) {
		this.configMapping = configMapping;
	}
	
}
