package com.account.manager.accountmanager.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Chukwudere Adindu.
 */

public class Account {

	private String accountName;
	private String phone;
	private String accountNumber;

	private BigDecimal balance;

	public Account() {
	}

	public Account(String accountName, String phone, BigDecimal balance) {
		this.accountName = accountName;
		this.phone = phone;
		this.balance = balance.setScale(2, RoundingMode.HALF_UP);
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance.setScale(2, RoundingMode.HALF_EVEN);
	}

	@Override
	public String toString() {
		return "Account{" +
				"accountName='" + accountName + '\'' +
				", phone='" + phone + '\'' +
				", accountNumber='" + accountNumber + '\'' +
				", balance='" + balance + '\'' +
				'}';
	}
}
