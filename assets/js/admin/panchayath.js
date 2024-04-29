var gramaListMap = new Map();

$(document).ready(function () {

    $('#btnAddPanchayath').click(function (evt) {
        evt.preventDefault();
        savePanchayath();
    });

    $('#district').change(function (evt) {
        evt.preventDefault();

        loadTalukList();
    });

    $('#taluk').change(function (evt) {
        evt.preventDefault();

        loadGramaList();
    });

    $('#gramName').change(function () {
        loadPanchayathCode();
    });

});

function loadPanchayathCode() {
    var gramaRid = $('#gramName').val();
    $('#panchayathCode').val(gramaListMap.get(gramaRid));
}

function loadTalukList() {

    var district = $('#district').val();

    if (district === "-1") {
        var choose = '<option value="-1">Choose District...</option>';
        $('#taluk, #gramName').html('');
        $('#taluk, #gramName').html(choose);
        return false;
    }

    $.ajax({
        url: "../actions/panchayath_action.php",
        type: 'GET',
        dataType: 'json',
        data: {
            command: 'getTalukList',
            district: district
        },
        success: function (data, textStatus, jqXHR) {
            console.log(data);

            if (data.success) {

                var talukList = data.mes;

                $('#taluk').html('');

                $.each(talukList, function (index, taluk) {
                    var t = '<option value="' + taluk.t_rid + '">' + taluk.t_name + '</option>';
                    $('#taluk').append(t);
                });

                loadGramaList();

            } else {
                alert(data.mes);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });

}

function loadGramaList() {

    var taluk = $('#taluk').val();

    if (taluk === "-1") {
        var choose = '<option value="-1">Choose Taluk...</option>';
        $('#gramName').html('');
        $('#gramName').html(choose);
        return false;
    }

    $.ajax({
        url: "../actions/panchayath_action.php",
        type: 'GET',
        dataType: 'json',
        data: {
            command: 'getGramaList',
            taluk: taluk
        },
        success: function (data, textStatus, jqXHR) {
            console.log(data);

            if (data.success) {

                var gramaList = data.mes;

                $('#gramName').html('');

                $.each(gramaList, function (index, grama) {
                    gramaListMap.set(grama.g_rid, grama.g_code);
                    var t = '<option value="' + grama.g_rid + '">' + grama.g_name + '</option>';
                    $('#gramName').append(t);
                });

                loadPanchayathCode();

            } else {
                alert(data.mes);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}

function savePanchayath() {

    var district = $('#district').val();
    var gramName = $('#gramName').val();
    var panchayathPresident = $('#panchayathPresident').val();
    var panchayathMobile = $('#panchayathMobile').val();
    var panchayathPhone = $('#panchayathPhone').val();
    var panchayathEmail = $('#panchayathEmail').val();
    var panchayathPassword = $('#panchayathPassword').val();
    var panchayathDesc = $('#panchayathDesc').val();

    if (district === "-1") {
        alert("Please select district");
        return false;
    }

    if (gramName === "" || gramName === null) {
        alert("Please select grama name");
        return false;
    }

    if (panchayathPresident === "") {
        alert("Please enter president name");
        return false;
    }

    if (panchayathMobile === "" || panchayathMobile.length !== 10 || isNaN(panchayathMobile)) {
        alert("Please enter president mobile number");
        return false;
    }

    if (panchayathPhone === "" || panchayathPhone.length !== 6 || isNaN(panchayathPhone)) {
        alert("Please enter panchayath phone number");
        return false;
    }

    if (panchayathEmail === "") {
        alert("Please enter email");
        return false;
    }

    if (panchayathPassword === "") {
        alert("Please enter password");
        return false;
    }

    if (panchayathDesc === "") {
        alert("Please enter description");
        return false;
    }

    if (!confirm("Are you sure?")) {
        return false;
    }

    $.ajax({
        url: $('#formAddPanchayath').attr('action'),
        type: $('#formAddPanchayath').attr('method'),
        dataType: 'json',
        data: $('#formAddPanchayath').serialize(),
        success: function (data, textStatus, jqXHR) {
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

function deletePunchayath(panchayathRid) {
    if (panchayathRid) {

        if (!confirm("Are you sure?")) {
            return false;
        }

        $.ajax({
            url: "../actions/panchayath_action.php",
            type: "POST",
            dataType: 'json',
            data: {
                command: 'deletePanchayath',
                panchayathRid: panchayathRid
            },
            success: function (data, textStatus, jqXHR) {
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

    } else {
        alert("Something went wrong. Please refresh the page");
    }
}

function editPanchayath(panchayathRid) {
    if (panchayathRid) {

        $.ajax({
            url: "../actions/panchayath_action.php",
            type: 'GET',
            dataType: 'json',
            data: {
                command: 'getPanchayathDetail',
                panchayathRid: panchayathRid
            },
            success: function (data, textStatus, jqXHR) {

                console.log(data);

                if (data.success) {

                    var panchayath = data.mes;

                    $('#panchayathId').val(panchayath.pan_rid);
                    $('#district').val(panchayath.d_rid);
                    $('#district').trigger('change', loadTalukList());
                    $('#taluk').val(panchayath.t_rid);
                    $('#taluk').trigger('change', loadGramaList());
                    $('#gramName').val(panchayath.pan_grama_rid);
                    $('#panchayathPresident').val(panchayath.pan_president);
                    $('#panchayathMobile').val(panchayath.pan_conatct);
                    $('#panchayathPhone').val(panchayath.pan_tel_no);
                    $('#panchayathEmail').val(panchayath.pan_email);
                    $('#panchayathPassword').val(panchayath.pan_password);
                    $('#panchayathDesc').val(panchayath.pan_description);

                    $('#addPanchayath').modal('show');

                } else {
                    alert(data.mes);
                }

            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(errorThrown);
            }
        });

    } else {
        alert("Somethig went wrong. Please refresh the page");
    }
}
