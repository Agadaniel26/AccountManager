package com.account.manager.accountmanager.servlets;

import com.account.manager.accountmanager.dto.Account;
import com.account.manager.accountmanager.util.FinanceUtil;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.account.manager.accountmanager.util.WebUtils.convertAccountsToJson;

@WebServlet(name = "AccountServlet", value = "/create-accounts")
public class AccountServlet extends HttpServlet {

	public void init() {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();


		String name = request.getParameter("name");
		String phone = request.getParameter("phone");

		if (name == null || name.length() < 1) {
			String errorResponse = Json.createObjectBuilder()
					.add("message", "Invalid Account name").build().toString();
			response.setStatus(500);
			out.print(errorResponse);
			out.flush();
			return;
		}

		if (phone == null || phone.length() < 1) {
			String errorRespone = Json.createObjectBuilder()
					.add("message", "Invalid phone number").build().toString();
			response.setStatus(500);
			out.print(errorRespone);
			out.flush();
			return;
		}

		FinanceUtil.accounts = generateAccounts(name, phone);

		FinanceUtil.accounts.forEach(System.out::println);
		FinanceUtil.transactionLogs = new ArrayList<>();

		String accountsJson = convertAccountsToJson(FinanceUtil.accounts);


		out.print(accountsJson);
		out.flush();
	}

	public void destroy() {
	}

	private List<Account> generateAccounts(String name, String phone) {
		List<Account> accounts = new ArrayList<>();
		Random random = new Random();
		for (int j = 0; j < 10; j++) {
			String accountNumber = random.ints(10, 0, 9)
					.boxed()
					.map(e -> "" + e)
					.collect(Collectors.joining());
			Account account = new Account(name, phone, BigDecimal.ZERO);
			account.setAccountNumber(accountNumber);
			accounts.add(account);
		}

		return accounts;
	}
}