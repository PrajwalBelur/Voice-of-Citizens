$(document).ready(function () {

    $('#btnPanchayathForgotPassword').click(function (evt) {
        evt.preventDefault();
        panchayathForgotPassword();
    });

});

function panchayathForgotPassword() {

    var emailId = $('#forgotPasswordEmail').val();

    if (emailId === "") {
        alert("Please enter email ID");
        return false;
    }

    $.ajax({
        url: $('#formForgotPassword').attr('action'),
        type: $('#formForgotPassword').attr('method'),
        dataType: 'json',
        data: $('#formForgotPassword').serialize(),
        success: function (data, textStatus, jqXHR) {
            console.log(data);

            if (data.success) {
                alert(data.mes);
                location.reload();
            } else {
                alert(data.mes);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}