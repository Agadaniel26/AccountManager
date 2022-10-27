package com.account.manager.accountmanager.dto;

import com.account.manager.accountmanager.enums.TransactionTypes;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Created by Chukwudere Adindu.
 */
public class TransactionLog {

	private String accountNumber;

	private BigDecimal amount;

	private BigDecimal balance;

	private TransactionTypes transactionType;

	private Long createdAt;

	public TransactionLog(String accountNumber, BigDecimal amount,  BigDecimal balance, TransactionTypes transactionType) {
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.balance = balance;
		this.transactionType = transactionType;
		this.createdAt = Instant.now().getEpochSecond();
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public TransactionTypes getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionTypes transactionType) {
		this.transactionType = transactionType;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "TransactionLog{" +
				"accountNumber='" + accountNumber + '\'' +
				", amount=" + amount +
				", balance=" + balance +
				", transactionType=" + transactionType +
				", createdAt=" + createdAt +
				'}';
	}
}
