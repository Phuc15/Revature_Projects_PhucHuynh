btn1.addEventListener("click", () => {
  let username = document.getElementById("username").value;
  let password = document.getElementById("password").value;
  let customername = document.getElementById("customername").value;
  let contact = document.getElementById("contact").value;
  let status = "Pending";
  var phoneno = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
  if (
    !contact.match(phoneno) ||
    username == "" ||
    password == "" ||
    contact == ""
  ) {
    document.getElementById("err").innerText =
      "Invalid input or missing values";
  } else {
    document.getElementById("err").innerText = "";
    let url = "http://localhost:8000/customer";
    fetch(url, {
      method: "POST",
      body: JSON.stringify({
        username: username,
        password: password,
        customerName: customername,
        contact: contact,
        status: status,
      }),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
    })
      .then((response) => response.json())
      .then((res) => {
        let data = JSON.stringify(res.data);
        console.log(data);
        if (
          res ==
          "Thank you for choosing Billy Banking. Your account is now pending to be approved"
        ) {
          location.replace("http://127.0.0.1:5500/customerlogin.html");
        }
        document.getElementById("err").innerText = res;
      });
  }
});
