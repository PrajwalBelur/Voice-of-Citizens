$(document).ready(function () {

    $('#solvedBeforeDate').datepicker({dateFormat: 'dd/mm/yy', maxDate: 0});

    $('#solvedBeforeDate').keypress(function (evt) {
        evt.preventDefault();   // prevent manually entering dates
    });

    $('#complaintAction').change(function (evt) {

        var action = $('#complaintAction').val();

        if (action === "-1") {  // reject
            $('#rejectReasonDiv').removeClass('d-none');
        } else {
            $('#rejectReason').val(""); // clear existing reason
            $('#rejectReasonDiv').addClass('d-none');
        }

        if (action === "1") {   // accept
            $('#solveBeforeDateDiv').removeClass('d-none');
        } else {
            $('#solveBeforeDate').val("");
            $('#solveBeforeDateDiv').addClass('d-none');
        }

    });

    $('#isSolvedBeforeDateYes, #isSolvedBeforeDateNo').change(function (evt) {
        var solvedBeforeDate = $('input[name="isSolvedBeforeDate"]:checked').val();

        if (solvedBeforeDate === "1") {
            $('#solvedBeforeDateDiv').removeClass('d-none');
        } else {
            $('#solvedBeforeDateDiv').addClass('d-none');
        }

        $('#solvedBeforeDate').val("");
    });

    $('#btnSaveComplaintStatus').click(function (evt) {
        evt.preventDefault();
        saveComplaintStatus();
    });

    $('#btnMarksAsComplete').click(function (evt) {
        evt.preventDefault();
        markAsComplete();
    });

});

function saveComplaintStatus() {
    var complaintAction = $('#complaintAction').val();
    var rejectReason = $('#rejectReason').val();

    if (complaintAction === "-2") {
        alert("Please select an action");
        return false;
    }

    if (complaintAction === "-1" && rejectReason === "") {
        alert("Please enter reject reason");
        return false;
    }

    $.ajax({
        url: $('#processComplaintForm').attr('action'),
        type: $('#processComplaintForm').attr('method'),
        dataType: 'json',
        data: $('#processComplaintForm').serialize(),
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

function showMarkAsCompleteModal(complaintRid, panchayathId, complaintDate, scheduledDate) {
    if (complaintRid, panchayathId, complaintDate, scheduledDate) {
        $('#complaintRid').val(complaintRid);
        $('#panchayathId').val(panchayathId);
        $('#complaintDate').val(complaintDate);

        $('#markAsCompleteModal').modal('show');
    }
}

function markAsComplete() {

    var solvedBeforeDate = $('#solvedBeforeDate').val();
    var solvedBeforeDate = $('input[name="isSolvedBeforeDate"]:checked').val();

    if (solvedBeforeDate === "1" && solvedBeforeDate === "") {
        alert("Please enter solved before date");
        return false;
    }

    $.ajax({
        url: $('#formMarkAsComplete').attr('action'),
        type: $('#formMarkAsComplete').attr('method'),
        dataType: 'json',
        data: $('#formMarkAsComplete').serialize(),
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