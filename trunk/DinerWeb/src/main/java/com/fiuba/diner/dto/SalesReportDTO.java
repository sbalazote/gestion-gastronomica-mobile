package com.fiuba.diner.dto;

import java.io.Serializable;
import java.util.Date;

public class SalesReportDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date billingDate;
	private Double dayTotal;

	public Date getBillingDate() {
		return this.billingDate;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}

	public Double getDayTotal() {
		return this.dayTotal;
	}

	public void setDayTotal(Double dayTotal) {
		this.dayTotal = dayTotal;
	}

}
