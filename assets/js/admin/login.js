function validateLogin() {
    var email = $("#email").val();
    var password = $("#password").val();

    if (email === "") {
        alert("Please enter Email ID");
        return false;
    }

    if (password === "") {
        alert("Please enter Password");
        return false;
    }

    return true;
}