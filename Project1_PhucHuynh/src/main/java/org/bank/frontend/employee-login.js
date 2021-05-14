btn10.addEventListener("click", () => {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value; 
    if (
        username == "" ||
        username.length == 0 ||
        password == "" ||
        password.length == ""
      ) {
        document.getElementById("err").innerText =
          "Username and password can not be empty";
      } else {
        let url = "http://localhost:8000/employee/" +username+"/"+password;
        fetch(url)
        .then(res => res.json())
        .then(res1 => {
           if(res1 ==true ){
            sessionStorage.setItem("employee-username", username);
            location.replace("http://127.0.0.1:5500/employee.html");
           }else{
            document.getElementById("err").innerText = res1;
           }
            });

      }

});