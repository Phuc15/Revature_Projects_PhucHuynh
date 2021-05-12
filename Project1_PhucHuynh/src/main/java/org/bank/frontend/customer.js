
btn.addEventListener("click", () => {
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
        let url = "http://localhost:8000/customer/" +username+"/"+password;
        fetch(url)
        .then(res => res.json())
        .then(res1 => {
           if(res1 ==true ){
            CustomerFunctions(username);
            location.replace("http://127.0.0.1:5500/customer.html");
           }else{
            document.getElementById("err").innerText = res1;
           }
            });

      }

});

function CustomerFunctions(username){
  console.log(username);

}
