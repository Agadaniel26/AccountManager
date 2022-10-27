function createAccounts() {

	// document.getElementById(formId).preventDe
	const xhttp = new XMLHttpRequest();
	const accountName = document.getElementById("accountName").value;
	const phone = document.getElementById("phone").value;
	xhttp.open("GET", "/accountmanager/create-accounts?name=" + accountName + "&phone=" + phone);
	xhttp.send();

	xhttp.onreadystatechange = function() {
		if (this.readyState === 4) {
			if (this.status === 200) {
				console.log("responseText", this.responseText);
				const accounts = JSON.parse(this.responseText);
				// Insert new rows to display accounts created.

				const table = document.getElementById("generated_accounts");
				accounts.forEach((account) => {
					// We already have a table header, so we insert after it.
					const row = table.insertRow(-1);
					const accountNameCell = row.insertCell(0);
					const accountNumberCell = row.insertCell(1);
					const balanceCell = row.insertCell(2);

					// Account details.
					accountNameCell.innerHTML = account.accountName;
					accountNumberCell.innerHTML = account.accountNumber;
					balanceCell.innerHTML = account.balance.toFixed(2);
				});
			}else{
				const message = JSON.parse(this.responseText).message;
				alert("Accounts creation: " + message);
			}
		}
	}
}

function depositMoney() {
	const xhttp = new XMLHttpRequest();
	const accountNumber = document.getElementById("creditAccount").value;
	const amount = document.getElementById("amount").value;
	xhttp.open("GET", "/accountmanager/deposit?accountnumber=" + accountNumber + "&amount=" + amount);
	xhttp.send();

	xhttp.onreadystatechange = function() {
		if (this.readyState === 4) {
			if (this.status === 200) {
				console.log("responseText", this.responseText);
				populateTransaction(accountNumber);
				alert("Credit successful, log updated");
			}else {
				const message = JSON.parse(this.responseText).message;
				alert("Credit failure, " + message);
			}
		}
	}
}

function withdrawMoney() {
	const xhttp = new XMLHttpRequest();
	const debitAccount = document.getElementById("debitAccount").value;
	const debitAmount = document.getElementById("debitAmount").value;
	xhttp.open("GET", "/accountmanager/withdraw?accountnumber=" + debitAccount + "&amount=" + debitAmount);
	xhttp.send();

	xhttp.onreadystatechange = function() {
		if (this.readyState === 4) {
			if (this.status === 200) {
				console.log("responseText", this.responseText);
				populateTransaction(debitAccount);
				alert("Debit successful, log updated");
			}
			else {
				const message = JSON.parse(this.responseText).message;
				alert("Debit failure, " + message);
			}
		}
	}
}

function getTransactionLogs(){
	const transactionAccount = document.getElementById("transactionAccount").value;
	populateTransaction(transactionAccount);
}

function populateTransaction(accountNumber){
	const xhttp = new XMLHttpRequest();
	xhttp.open("GET", "/accountmanager/transactions?accountnumber=" + accountNumber);
	xhttp.send();

	xhttp.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			console.log("responseText", this.responseText);
			const transactions = JSON.parse(this.responseText);
			// Insert new rows to display accounts created.

			const table = document.getElementById("transactionLogs");

			const rows = table.getElementsByTagName("tr");
			while (rows.length > 1) {
				rows[1].parentNode.removeChild(rows[1]);
			}

			transactions.forEach((transaction) => {
				// We already have a table header, so we insert after it.
				const row = table.insertRow(-1);
				const accountNumberCell = row.insertCell(0);
				const tranAmountCell = row.insertCell(1);
				const balanceCell = row.insertCell(2);
				const tranTypeCell = row.insertCell(3);

				// Account details.
				accountNumberCell.innerHTML = transaction.accountNumber;
				tranAmountCell.innerHTML = transaction.amount;
				balanceCell.innerHTML = transaction.balance.toFixed(2);
				tranTypeCell.innerHTML = transaction.transactionType;
			});

		}
	}
}