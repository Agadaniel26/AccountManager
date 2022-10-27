package com.account.manager.accountmanager.util;

import com.account.manager.accountmanager.dto.Account;
import com.account.manager.accountmanager.dto.TransactionLog;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Tochukwu Agada.
 */
public class WebUtils {


	public static String convertAccountsToJson(List<Account> accounts) {

		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for (Account account : accounts) {
			JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
					.add("accountName", account.getAccountName())
					.add("phone", account.getPhone())
					.add("accountNumber", account.getAccountNumber())
					.add("balance", account.getBalance());
			getObjectArray(arrayBuilder, objectBuilder);
		}

		return cleanUpJson(arrayBuilder);
	}

	public static String convertTransactionsToJson(String accountNumber) {

		Stream<TransactionLog> transactionLogStream = FinanceUtil.transactionLogs.stream();

		if (accountNumber != null) {
			if (accountNumber.length() > 8) {
				transactionLogStream = transactionLogStream.filter(e -> e.getAccountNumber().equalsIgnoreCase(accountNumber));
			}
		}
		//the design provides that there is no need for this sorting, but you stated that you want it, so Ok.
		transactionLogStream = transactionLogStream.sorted(Comparator.comparingLong(TransactionLog::getCreatedAt).reversed());

		List<TransactionLog> selectedTransactionLogs = transactionLogStream.collect(Collectors.toList());

		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for (TransactionLog transactionLog : selectedTransactionLogs) {
			JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
					.add("accountNumber", transactionLog.getAccountNumber())
					.add("amount", transactionLog.getAmount())
					.add("balance", transactionLog.getBalance())
					.add("transactionType", transactionLog.getTransactionType().toString());
			getObjectArray(arrayBuilder, objectBuilder);
		}

		return cleanUpJson(arrayBuilder);
	}

	private static String cleanUpJson(JsonArrayBuilder arrayBuilder) {
		String jsonString = arrayBuilder.build().toString();
		jsonString = jsonString.replace("[\"", "[");
		jsonString = jsonString.replace("\"]", "]");
		jsonString = jsonString.replace("{\\", "{");
		jsonString = jsonString.replace("}\"", "}");
		jsonString = jsonString.replace("\"{", "{");
		jsonString = jsonString.replace("\\", "");
		return jsonString;
	}

	public static String convertTransactionLogToJson(TransactionLog transactionLog) {

		JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
				.add("accountNumber", transactionLog.getAccountNumber())
				.add("amount", transactionLog.getAmount())
				.add("balance", transactionLog.getBalance())
				.add("transactionType", transactionLog.getTransactionType().toString());
		return objectBuilder.build().toString();
	}

	private static void getObjectArray(JsonArrayBuilder arrayBuilder, JsonObjectBuilder objectBuilder) {
		JsonObject jsonObject = objectBuilder.build();
		String jsonString;
		try (Writer writer = new StringWriter()) {
			Json.createWriter(writer).writeObject(jsonObject);
			jsonString = writer.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		arrayBuilder.add(jsonString);
	}
}
