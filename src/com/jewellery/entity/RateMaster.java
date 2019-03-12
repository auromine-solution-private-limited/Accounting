package com.jewellery.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
@NamedQuery(name="listRateMaster", query="from com.jewellery.entity.RateMaster"),
@NamedQuery(name = "getRates", query = "from RateMaster"),
@NamedQuery(name = "boardRates", query = "from RateMaster where ratemasterId=(SELECT MAX(ratemasterId) FROM RateMaster)")
})
public class RateMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	private int ratemasterId;
	private BigDecimal goldBullion = new BigDecimal("0.00");
	private BigDecimal goldOrnaments = new BigDecimal("0.00");
	private BigDecimal silverBullion = new BigDecimal("0.00");
	private BigDecimal silverOrnaments = new BigDecimal("0.00");
	private BigDecimal exchangeGold = new BigDecimal("0.00");
	private BigDecimal exchangeSilver = new BigDecimal("0.00");
	private Date lastUpdateDate;

	public RateMaster() {
	}

	@Id
	@GeneratedValue
	public int getRatemasterId() {
		return ratemasterId;
	}

	public void setRatemasterId(int ratemasterId) {
		this.ratemasterId = ratemasterId;
	}

	public BigDecimal getGoldBullion() {
		return goldBullion;
	}

	public void setGoldBullion(BigDecimal goldBullion) {
		this.goldBullion = goldBullion;
	}

	public BigDecimal getGoldOrnaments() {
		return goldOrnaments;
	}

	public void setGoldOrnaments(BigDecimal goldOrnaments) {
		this.goldOrnaments = goldOrnaments;
	}

	public BigDecimal getSilverBullion() {
		return silverBullion;
	}

	public void setSilverBullion(BigDecimal silverBullion) {
		this.silverBullion = silverBullion;
	}

	public BigDecimal getSilverOrnaments() {
		return silverOrnaments;
	}

	public void setSilverOrnaments(BigDecimal silverOrnaments) {
		this.silverOrnaments = silverOrnaments;
	}

	public BigDecimal getExchangeGold() {
		return exchangeGold;
	}

	public void setExchangeGold(BigDecimal exchangeGold) {
		this.exchangeGold = exchangeGold;
	}

	public BigDecimal getExchangeSilver() {
		return exchangeSilver;
	}

	public void setExchangeSilver(BigDecimal exchangeSilver) {
		this.exchangeSilver = exchangeSilver;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

}