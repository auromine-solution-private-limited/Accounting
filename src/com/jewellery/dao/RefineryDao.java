package com.jewellery.dao;

import java.math.BigInteger;
import java.util.List;

import com.jewellery.entity.RefineryIssue;

public interface RefineryDao {
public void insertRefinery(RefineryIssue refinery);
public void updateRefinery(RefineryIssue refinery);
public RefineryIssue getRefinery(Integer refineryId);
public BigInteger getRefineryIssueNo();
public List<RefineryIssue> listRefinery();
public List<RefineryIssue> getrefineryId(Integer refineryId);
}
