package com.account.manager.accountmanager.servlets;

import com.account.manager.accountmanager.util.FinanceUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.account.manager.accountmanager.util.WebUtils.convertTransactionsToJson;

@WebServlet(name = "TransactionServlet", value = "/transactions")
public class TransactionServlet extends HttpServlet {

	public void init() {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String accountNumber = request.getParameter("accountnumber");
		System.out.println(accountNumber);

		FinanceUtil.transactionLogs.forEach(System.out::println);

		String transactionToJson = convertTransactionsToJson(accountNumber);

		PrintWriter out = response.getWriter();

		out.print(transactionToJson);
		out.flush();
	}

	public void destroy() {
	}
}