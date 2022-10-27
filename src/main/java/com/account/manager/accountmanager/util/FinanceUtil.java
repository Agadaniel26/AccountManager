package com.account.manager.accountmanager.util;

import com.account.manager.accountmanager.dto.Account;
import com.account.manager.accountmanager.dto.TransactionLog;
import com.account.manager.accountmanager.enums.TransactionTypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class FinanceUtil {

	public static List<Account> accounts;

	public static List<TransactionLog> transactionLogs = new ArrayList<>();
	public static TransactionLog addTransactionLog(String accountNumber, BigDecimal amount, BigDecimal balance, TransactionTypes transactionType){

		TransactionLog transactionLog = new TransactionLog(accountNumber, amount, balance, transactionType);

		transactionLogs.add(transactionLog);

		return transactionLog;
	}
}
