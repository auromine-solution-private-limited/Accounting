package com.jewellery.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ConfigDetailList implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*
	 * 
	 */
	private Integer ModuleId;
	private String ModuleCode;
	private String ModuleName;
	private String status;
	private Integer configReferenceId;
	private String configReference;
	
	private ConfigSetting configSetting;
		
	@Id
	@GeneratedValue
	public Integer getModuleId() {
		return ModuleId;
	}
	public void setModuleId(Integer moduleId) {
		ModuleId = moduleId;
	}
	public String getModuleCode() {
		return ModuleCode;
	}
	public void setModuleCode(String moduleCode) {
		ModuleCode = moduleCode;
	}
	public String getModuleName() {
		return ModuleName;
	}
	public void setModuleName(String moduleName) {
		ModuleName = moduleName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getConfigReferenceId() {
		return configReferenceId;
	}
	public void setConfigReferenceId(Integer configReferenceId) {
		this.configReferenceId = configReferenceId;
	}
	public String getConfigReference() {
		return configReference;
	}
	public void setConfigReference(String configReference) {
		this.configReference = configReference;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "configId", nullable = false)
	public ConfigSetting getConfigSetting() {
		return configSetting;
	}
	public void setConfigSetting(ConfigSetting configSetting) {
		this.configSetting = configSetting;
	}
}
