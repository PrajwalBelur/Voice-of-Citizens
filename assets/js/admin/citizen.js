function deleteUser(citizenRid) {
    if (citizenRid) {

        if (!confirm("Are you sure want to delete?")) {
            return;
        }

        $.ajax({
            url: "../actions/panchayath_action.php",
            type: 'POST',
            data: {
                command: 'deleteCitizen',
                citizenRid: citizenRid
            },
            success: function (data, textStatus, jqXHR) {
                console.log(data);
                alert(data);
                location.reload();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(errorThrown);
            }
        });

    }

}