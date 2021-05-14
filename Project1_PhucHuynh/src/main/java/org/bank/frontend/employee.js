let employeeUsername = sessionStorage.getItem("employee-username");
//Register for new
//let emplyeeUsername = 'phuch';
console.log(employeeUsername);
btn10.addEventListener("click", () => {
  let username = document.getElementById("username").value;
  let password = document.getElementById("password").value;
  let employeename = document.getElementById("employee-name").value;
  if (username == "" || password == "" || employeename == "") {
    document.getElementById("err10").innerHTML =
      "Invalid input or missing values";
  } else {
    document.getElementById("err10").innerHTML = "";
    let url = "http://localhost:8000/employee";
    fetch(url, {
      method: "POST",
      body: JSON.stringify({
        employeeUsername: username,
        employeePassword: password,
        employeeName: employeename,
      }),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
    })
      .then((response) => response.json())
      .then((res) => {
        if (res == "Congrats! You have successfully registered YAY!!!!") {
          document.getElementById("err10").innerHTML = res;
        } else {
          document.getElementById("err10").innerHTML = "";
          document.getElementById("err10").innerHTML = res;
        }
      });
  }
});

//Display banking info
btn3.addEventListener("click", () => {
  let id = document.getElementById("id-c").value;
  var num = /^[0-9]+$/;
  if (id == "" || id.length == 0 || !id.match(num)) {
    document.getElementById("err3").innerHTML =
      "Invalid input. Please re-enter";
    setTimeout(function () {
      document.getElementById("err3").innerHTML = "";
    }, 3000);
  } else {
    let url = "http://localhost:8000/employee/customer/" + id;
    console.log(url);
    fetch(url)
      .then((res) => res.json())
      .then((res1) => {
        if (res1 == "No bank account with customer id") {
          document.getElementById("err3").innerHTML =
            "No bank account is found";
          setTimeout(function () {
            document.getElementById("err3").innerHTML = "";
          }, 3000);
          document.getElementById("banktable").innerHTML = "";
        } else {
          let data =
            "<table class='table table-bordered table-striped'><thead class='thead-dark'><tr><th>Bank Account</th><th>Amount</th></tr></thead>";
          res1.forEach((element) => {
            data = data + "<tr><td>" + element.accountType + "</td>";
            data = data + "<td>" + element.balance + "</td></tr>";
          });
          data = data + "</table>";
          document.getElementById("banktable").innerHTML = data;
        }
      });
  }
});

//display previous transaction by customer id

btn4.addEventListener("click", () => {
  let id = document.getElementById("customerid-p").value;
  var num = /^[0-9]+$/;
  if (id == "" || id.length == 0 || !id.match(num)) {
    document.getElementById("err4").innerHTML =
      "Invalid input. Please re-enter";
    setTimeout(function () {
      document.getElementById("err4").innerHTML = "";
    }, 3000);
  } else {
    let url = "http://localhost:8000/employee/bankaccount/transaction/" + id;
    console.log(url);
    fetch(url)
      .then((res) => res.json())
      .then((res1) => {
        if (res1 == "No account found  found for this customer id") {
          document.getElementById("err4").innerHTML = "No transactions found";
          setTimeout(function () {
            document.getElementById("err4").innerHTML = "";
          }, 3000);
          document.getElementById("banktable-t").innerHTML = "";
        } else {
          let data =
            "<table class='table table-bordered table-striped'><thead class='thead-dark'><tr><th>Account ID</th><th>Previous Transaction Amount</th><th>Date</th></tr></thead>";
          res1.forEach((element) => {
            data = data + "<tr><td>" + element.accountId + "</td>";
            data = data + "<td>" + element.previousTransaction + "</td>";
            data = data + "<td>" + element.date + "</td></tr>";
          });
          data = data + "</table>";
          document.getElementById("banktable-t").innerHTML = data;
        }
      });
  }
});

//Display all of previous transactions by account id
btn5.addEventListener("click", () => {
  let id = document.getElementById("accountid-p").value;
  var num = /^[0-9]+$/;
  if (id == "" || id.length == 0 || !id.match(num)) {
    document.getElementById("err5").innerHTML =
      "Invalid input. Please re-enter";
    setTimeout(function () {
      document.getElementById("err5").innerHTML = "";
    }, 3000);
  } else {
    let url =
      "http://localhost:8000/employee/bankaccount/transaction-by-accountid/" +
      id;
    console.log(url);
    fetch(url)
      .then((res) => res.json())
      .then((res1) => {
        if (res1 == "Transactions not found with this account id") {
          document.getElementById("err5").innerHTML = "No transactions found";
          setTimeout(function () {
            document.getElementById("err5").innerHTML = "";
          }, 3000);
          document.getElementById("banktable-t-accountid").innerHTML = "";
        } else {
          let data =
            "<table class='table table-bordered table-striped'><thead class='thead-dark'><tr><th>Account ID</th><th>Previous Transaction Amount</th><th>Date</th></tr></thead>";
          res1.forEach((element) => {
            data = data + "<tr><td>" + element.accountId + "</td>";
            data = data + "<td>" + element.previousTransaction + "</td>";
            data = data + "<td>" + element.date + "</td></tr>";
          });
          data = data + "</table>";
          document.getElementById("banktable-t-accountid").innerHTML = data;
        }
      });
  }
});

//Display previous transactions by transaction id
btn6.addEventListener("click", () => {
  let id = document.getElementById("transactionid-p").value;
  var num = /^[0-9]+$/;
  if (id == "" || id.length == 0 || !id.match(num)) {
    document.getElementById("err6").innerHTML =
      "Invalid input. Please re-enter";
    setTimeout(function () {
      document.getElementById("err6").innerHTML = "";
    }, 3000);
  } else {
    let url =
      "http://localhost:8000/employee/bankaccount/transaction-by-transactionid/" +
      id;
    console.log(url);
    fetch(url)
      .then((res) => res.json())
      .then((res1) => {
        if (res1 == "Transactions not found with this transaction id") {
          document.getElementById("err6").innerHTML = "No transactions found";
          setTimeout(function () {
            document.getElementById("err6").innerHTML = "";
          }, 3000);
          document.getElementById("banktable-t-transactionid").innerHTML = "";
        } else {
          let data =
            "<table class='table table-bordered table-striped'><thead class='thead-dark'><tr><th>Account ID</th><th>Previous Transaction Amount</th><th>Date</th></tr></thead>";
          res1.forEach((element) => {
            data = data + "<tr><td>" + element.accountId + "</td>";
            data = data + "<td>" + element.previousTransaction + "</td>";
            data = data + "<td>" + element.date + "</td></tr>";
          });
          data = data + "</table>";
          document.getElementById("banktable-t-transactionid").innerHTML = data;
        }
      });
  }
});

// Display previous transactions by date
btn7.addEventListener("click", () => {
  let date = document.getElementById("date-p").value;
  var dateValidation = /^(\d{4})(\/|-)(\d{1,2})(\/|-)(\d{1,2})$/;
  if (date == "" || date.length == 0 || !date.match(dateValidation)) {
    document.getElementById("err7").innerHTML =
      "Invalid input. Please re-enter";
    setTimeout(function () {
      document.getElementById("err7").innerHTML = "";
    }, 3000);
  } else {
    let url =
      "http://localhost:8000/employee/bankaccount/transaction-by-date/" + date;
    console.log(url);
    fetch(url)
      .then((res) => res.json())
      .then((res1) => {
        if (res1 == "Transactions not found with this date") {
          document.getElementById("err7").innerHTML = "No transactions found";
          setTimeout(function () {
            document.getElementById("err7").innerHTML = "";
          }, 3000);
          document.getElementById("banktable-t-date").innerHTML = "";
        } else {
          let data =
            "<table class='table table-bordered table-striped'><thead class='thead-dark'><tr><th>Account ID</th><th>Previous Transaction Amount</th><th>Date</th></tr></thead>";
          res1.forEach((element) => {
            data = data + "<tr><td>" + element.accountId + "</td>";
            data = data + "<td>" + element.previousTransaction + "</td>";
            data = data + "<td>" + element.date + "</td></tr>";
          });
          data = data + "</table>";
          document.getElementById("banktable-t-date").innerHTML = data;
        }
      });
  }
});

//Display all of pending customers
btn8.addEventListener("click", () => {
  let url = "http://localhost:8000/employee/customer-pending";
  console.log(url);
  fetch(url)
    .then((res) => res.json())
    .then((res1) => {
      if (res1 == "There is no pending customer.") {
        document.getElementById("err8").innerHTML =
          "No pending customers found";
        setTimeout(function () {
          document.getElementById("err8").innerHTML = "";
        }, 3000);
        document.getElementById("banktable-pending-customers").innerHTML = "";
      } else {
        let data =
          "<table class='table table-bordered table-striped'><thead class='thead-dark'><tr><th>Customer ID</th><th>Name</th><th>Username</th></tr></thead>";
        res1.forEach((element) => {
          data = data + "<tr><td>" + element.customerId + "</td>";
          data = data + "<td>" + element.customerName + "</td>";
          data = data + "<td>" + element.username + "</td></tr>";
        });
        data = data + "</table>";
        document.getElementById("banktable-pending-customers").innerHTML = data;
      }
    });
});

//Appprove the Customer Accounts by Customer ID
btn11.addEventListener("click", () => {
  let id = document.getElementById("id-ap").value;
  var num = /^[0-9]+$/;
  if (id == "" || id.length == 0 || !id.match(num)) {
    document.getElementById("err11").innerHTML =
      "Invalid input. Please re-enter";
    setTimeout(function () {
      document.getElementById("err11").innerHTML = "";
    }, 3000);
  } else {
    let url =
      "http://localhost:8000/employee/approve-customer-pending/" +
      id +
      "/" +
      employeeUsername;
    console.log(url);
    fetch(url)
      .then((res) => res.json())
      .then((res1) => {
        if (res1 == true) {
          document.getElementById("err11").innerHTML =
            "You sucessfully approved this account with id " + id;
        } else {
          document.getElementById("err11").innerHTML = "Account is not found";
        }
      });
  }
});

//Reject the Customer Accounts by Customer ID

btn12.addEventListener("click", () => {
  let id = document.getElementById("id-re").value;
  var num = /^[0-9]+$/;
  if (id == "" || id.length == 0 || !id.match(num)) {
    document.getElementById("err12").innerHTML =
      "Invalid input. Please re-enter";
    setTimeout(function () {
      document.getElementById("err12").innerHTML = "";
    }, 3000);
  } else {
    let url = "http://localhost:8000/employee/reject-customer-pending/" + id;
    console.log(url);
    fetch(url)
      .then((res) => res.json())
      .then((res1) => {
        if (res1 == true) {
          document.getElementById("err12").innerHTML =
            "You sucessfully reject this account with id " + id;
        } else {
          document.getElementById("err12").innerHTML = "Account is not found";
        }
      });
  }
});

//Display approver
btn13.addEventListener("click", () => {
  let id = document.getElementById("id-apr").value;
  var num = /^[0-9]+$/;
  if (id == "" || id.length == 0 || !id.match(num)) {
    document.getElementById("err13").innerHTML =
      "Invalid input. Please re-enter";
    setTimeout(function () {
      document.getElementById("err13").innerHTML = "";
    }, 3000);
  } else {
    let url = "http://localhost:8000/employee/display/approver/" + id;
    console.log(url);
    fetch(url)
      .then((res) => res.json())
      .then((res1) => {
        console.log(res1);
        if (
          res1.message === "Id is not found" ||
          res1.message === "Account is still pending to be approved"
        ) {
          document.getElementById("err13").innerHTML =
            "Account is pending or not found. Please try again";
          setTimeout(function () {
            document.getElementById("err13").innerHTML = "";
          }, 3000);
          document.getElementById("banktable-approver").innerHTML = "";
        } else {
          let data =
            "<table class='table table-bordered table-striped'><thead class='thead-dark'><tr><th>Employee Username</th><th>Picture</th></tr></thead>";
          data = data + "<tr><td>" + res1.status + "</td>";
          data =
            data +
            "<td><img class='rounded' height='200px' width='200px' src='" +
            "https://yt3.ggpht.com/a/AATXAJz-iBcY8fpw9HE3ngNwiWATqoCSOcvq_6NGTx_U-w=s900-c-k-c0xffffffff-no-rj-mo" +
            "'/></td></tr>";
          data = data + "</table>";
          document.getElementById("banktable-approver").innerHTML = data;
        }
      });
  }
});

btn9.addEventListener("click", () => {
  location.replace("http://127.0.0.1:5500/employeelogin.html");
});
