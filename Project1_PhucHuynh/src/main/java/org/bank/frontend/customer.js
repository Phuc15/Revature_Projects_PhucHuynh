
let username = sessionStorage.getItem('username');
//display bank account
btn.addEventListener("click", () =>{
  let url = "http://localhost:8000/customer/" + username;
  //console.log(url);
  fetch(url)
      .then(res => res.json())
      .then(res1 => {
        if(res1 == "No account is found with this username"){
          document.getElementById("err").innerHTML = res1;
        }else{
          let data = "<table class='table table-bordered table-striped'><thead class='thead-dark'><tr><th>Bank Account</th><th>Amount</th></tr></thead>";
          res1.forEach(element => {
              data = data + "<tr><td>" + element.accountType + "</td>"
              data = data + "<td>" + element.balance + "</td></tr>"
          });
          data = data + "</table>"
          document.getElementById("banktable").innerHTML = data;
        }
        })
});

//Apply for the bank account

btn2.addEventListener("click", () => {
  let balance = document.getElementById("amount").value;
  let accountType = document.getElementById("banktype").value
  var number = /(?=.*?\d)^\$?(([1-9]\d{0,2}(,\d{3})*)|\d+)?(\.\d{1,2})?$/;
  if(balance == "" || !balance.match(number)){
    document.getElementById("err1").innerHTML = "<p1>invalid input, please re-enter<\p1>";
  }else{
   let url = "http://localhost:8000/customer/create-bankaccount"
   document.getElementById("err1").innerText =""; 
   fetch(url, {
     method: "POST",
     body: JSON.stringify({
       username: username,
       accountType: accountType,
       balance: balance
     }),
     headers: {
       "Content-type": "application/json; charset=UTF-8",
     },
   })
     .then((response) => response.json())
     .then(res => {
       if(res == "Your account is still pending to be approved. Thank you!" || res == "You already have this account" ){
      document.getElementById("err1").innerHTML = res;
       }else{
        document.getElementById("err1").innerHTML = "<p1>YAY! Congrats! You have sucessfully opened a <\p1>"+accountType + " <p1>with $<\p1>" +balance;
       }
     });
  }
})

//deposit
btn3.addEventListener("click", () => {
  let balance = document.getElementById("balance").value;
  let accountType = document.getElementById("banktype-d").value
  var number = /(?=.*?\d)^\$?(([1-9]\d{0,2}(,\d{3})*)|\d+)?(\.\d{1,2})?$/;
   console.log(balance);
  if(balance == "" || !balance.match(number)){
    document.getElementById("err3").innerHTML = "invalid input. Please re-enter";
  }else{
   let url = "http://localhost:8000/customer/bankaccount-deposit"
   document.getElementById("err3").innerHTML =""; 
   fetch(url, {
     method: "POST",
     body: JSON.stringify({
       username: username,
       accountType: accountType,
       balance: balance
     }),
     headers: {
       "Content-type": "application/json; charset=UTF-8",
     },
   })
     .then((response) => response.json())
     .then(res => {
       if(res == "successful"){
        document.getElementById("err3").innerHTML = "<p1>YAY! Congrats! You have sucessfully deposited <\p1>" + "<p1>$<\p1>" +balance +" <p1>into<\p1> " + accountType;
       }else{
        document.getElementById("err3").innerHTML = res;
       }
     });
  }
})


//Withdraw
btn4.addEventListener("click", () => {
  let balance = document.getElementById("balance-w").value;
  let accountType = document.getElementById("banktype-w").value
  var number = /(?=.*?\d)^\$?(([1-9]\d{0,2}(,\d{3})*)|\d+)?(\.\d{1,2})?$/;
  if(balance == "" || !balance.match(number)){
    document.getElementById("err4").innerHTML = "<p1>invalid input. Please re-enter<\p1>";
  }else{
   let url = "http://localhost:8000/customer/bankaccount-withdraw"
   document.getElementById("err4").innerHTML =""; 
   fetch(url, {
     method: "POST",
     body: JSON.stringify({
       username: username,
       accountType: accountType,
       balance: balance
     }),
     headers: {
       "Content-type": "application/json; charset=UTF-8",
     },
   })
     .then((response) => response.json())
     .then(res => {
       if(res == "successful"){
        document.getElementById("err4").innerHTML = "<p1>YAY! Congrats! You have sucessfully withdrew <\p1>" + "<p1>$<\p1>" +balance +" <p1>from<\p1> " + accountType;
       }else{
        document.getElementById("err4").innerHTML = res;
       }
     });
  }
})

// Make a transfer

btn5.addEventListener("click", () => {
  let balance = document.getElementById("balance-t").value;
  let accountType = document.getElementById("banktype-t").value
  let toUser = document.getElementById("to-account-t").value
  var number = /(?=.*?\d)^\$?(([1-9]\d{0,2}(,\d{3})*)|\d+)?(\.\d{1,2})?$/;
  if(balance == "" || !balance.match(number)){
    document.getElementById("err5").innerHTML = "<p1>invalid input. Please re-enter<\p1>";
  }else{
   let url = "http://localhost:8000/customer/bankaccount-transfer"
   document.getElementById("err5").innerText =""; 
   fetch(url, {
     method: "POST",
     body: JSON.stringify({
       username: username,
       accountType: accountType,
       balance: balance,
       toUser: toUser
     }),
     headers: {
       "Content-type": "application/json; charset=UTF-8",
     },
   })
     .then((response) => response.json())
     .then(res => {
       if(res == "successful"){
        document.getElementById("err5").innerHTML = "<p1>YAY! Congrats! You have sucessfully tranfered <\p1>" + "<p1>$<\p1>" +balance +" <p1>to<\p1> " + toUser;
       }else{
        document.getElementById("err5").innerHTML = res;
       }
     });
  }
})

//Display the pending customers
btn6.addEventListener("click", () =>{
  let url = "http://localhost:8000/customer/bankaccount/get-pending-transaction/" + username;
  //console.log(url);
  fetch(url)
      .then(res => res.json())
      .then(res1 => {
        if(res1 == "There is no pending transaction" || res1 == "No transaction found with the account id"){
          document.getElementById("err6").innerHTML = res1;
        }else{
          let data = "<table class='table table-bordered table-striped'><thead class='thead-dark'><tr><th>Transaction Id</th><th>Pending Amount</th><th>Date</th></tr></thead>";
          res1.forEach(element => {
              data = data + "<tr><td>" + element.transactionId + "</td>"
              data = data + "<td>" + element.pendingTransaction + "</td>"
              data = data + "<td>" + element.date + "</td></tr>"
          });
          data = data + "</table>"
          document.getElementById("pending-transaction").innerHTML = data;
        }
        })
});

//Accept transfer from other account using transaction id
btn7.addEventListener("click", () => {
  let id = document.getElementById("customer-id").value;
  var num = /^[0-9]+$/;
  if (
      id == "" ||
      id.length == 0||
      !id.match(num)
    ) {
      document.getElementById("err7").innerHTML = "<P1>ID can not be empty and only contains number [0-9]. Please re-enter again<\p1>";
    } else {
      let url = "http://localhost:8000/customer/bankaccount/accept-pending-transaction/"+id;
      fetch(url)
      .then(res => res.json())
      .then(res1 => {
        if(res1 ==true){
          document.getElementById("err7").innerHTML = "<p1>Pending transaction has been added to your account.<\p1>";
        }else{
         document.getElementById("err7").innerHTML = "<p1>Transaction ID not found. Please try again!<\p1>" ;
        }
          });
    }  

});

//Display all previous transaction
btn8.addEventListener("click", () =>{
  let url = "http://localhost:8000/customer/bankaccount/display/previous-transaction/" + username;
  //console.log(url);
  fetch(url)
      .then(res => res.json())
      .then(res1 => {
        if(res1 == "There is no pending transaction" || res1 == "No transaction found with the account id"){
          document.getElementById("err8").innerHTML = res1;
        }else{
          let data = "<table class='table table-bordered table-striped'><thead class='thead-dark'><tr><th>Account ID</th><th>Previous Transaction Amount</th><th>Date</th></tr></thead>";
          res1.forEach(element => {
              data = data + "<tr><td>" + element.accountId + "</td>"
              data = data + "<td>" + element.previousTransaction + "</td>"
              data = data + "<td>" + element.date + "</td></tr>"
          });
          data = data + "</table>"
          document.getElementById("transaction-p").innerHTML = data;
        }
        })
});

btn9.addEventListener("click", () => {
  location.replace("http://127.0.0.1:5500/customerlogin.html");
});
