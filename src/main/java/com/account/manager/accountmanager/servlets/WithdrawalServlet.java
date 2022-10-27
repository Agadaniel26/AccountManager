package com.account.manager.accountmanager.servlets;

import com.account.manager.accountmanager.dto.TransactionLog;
import com.account.manager.accountmanager.enums.TransactionTypes;
import com.account.manager.accountmanager.util.FinanceUtil;

import javax.json.Json;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.account.manager.accountmanager.util.WebUtils.convertTransactionLogToJson;

@WebServlet(name = "WithdrawalServlet", value = "/withdraw")
public class WithdrawalServlet extends HttpServlet {

	public void init() {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();


		String accountNumber = request.getParameter("accountnumber");
		String amount = request.getParameter("amount");

		if (accountNumber == null || accountNumber.length() < 1) {
			String errorResponse = Json.createObjectBuilder()
					.add("message", "Invalid Account number").build().toString();
			response.setStatus(500);
			out.print(errorResponse);
			out.flush();
			return;
		}

		if (amount == null || amount.length() < 1) {
			String errorResponse = Json.createObjectBuilder()
					.add("message", "Invalid Amount").build().toString();
			response.setStatus(500);
			out.print(errorResponse);
			out.flush();
			return;
		}

		AtomicReference<TransactionLog> transactionLog = new AtomicReference<>();
		String[] errorResponse = new String[1];
		FinanceUtil.accounts = FinanceUtil.accounts.stream()
				.peek(account -> {
					if (account.getAccountNumber().equalsIgnoreCase(accountNumber)) {
						if (account.getBalance().compareTo(new BigDecimal(amount)) > -1) {
							BigDecimal currentBalance = account.getBalance().subtract(new BigDecimal(amount));
							account.setBalance(currentBalance);
							transactionLog.set(FinanceUtil.addTransactionLog(accountNumber, new BigDecimal(amount).setScale(2, RoundingMode.HALF_EVEN), account.getBalance(), TransactionTypes.WITHDRAWAL));
						} else  {
							System.out.println("insufficient funds");
							errorResponse[0] = Json.createObjectBuilder()
									.add("message", "Insufficient funds").build().toString();
						}
					}
				})
				.collect(Collectors.toList());
		if (errorResponse[0] != null) {
			response.setStatus(500);
			out.print(errorResponse[0]);
			out.flush();
			return;
		}

		FinanceUtil.accounts.forEach(System.out::println);

		String accountsJson = convertTransactionLogToJson(transactionLog.get());

		out.print(accountsJson);
		out.flush();
	}

	public void destroy() {
	}
}