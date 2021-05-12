btn1.addEventListener("click",() =>{

    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    let customerName = document.getElementById("customerName").value;
    let contact = document.getElementById("contact").value; 
    let status = "Pending";
    var phoneno = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
    if(contact.match(phoneno) || username == "" || password =="" || contact ==""){
        document.getElementById("err").innerText =
        "Invalid input or missing values";
    }else{
    
    }
    });