<?php

require_once '../api/Exception.php';
require_once '../api/PHPMailer.php';
require_once '../api/SMTP.php';

require_once '../includes/config.php';
require_once '../api/util.php';
require_once '../includes/db.class.php';

$db = new DB();

if (isset($_POST['command'])) {

    $message = array();

    try {

        $command = $_POST['command'];

        if ('addPanchayath' == $command) {
            $panchayathId = $_POST['panchayathId'];
            $gramName = $_POST['gramName'];
            $panchayathPresident = $_POST['panchayathPresident'];
            $panchayathMobile = $_POST['panchayathMobile'];
            $panchayathPhone = $_POST['panchayathPhone'];
            $panchayathDesc = $_POST['panchayathDesc'];
            $panchayathEmail = $_POST['panchayathEmail'];
            $panchayathPassword = $_POST['panchayathPassword'];
            $panchayathCode = $_POST['panchayathCode'];

            if ($panchayathId > 0) {

                $updatePanchayath = "UPDATE panchayath SET"
                        . " pan_grama_rid = '$gramName',"
                        . " pan_email = '$panchayathEmail',"
                        . " pan_password = '$panchayathPassword',"
                        . " pan_president = '$panchayathPresident',"
                        . " pan_conatct = '$panchayathMobile',"
                        . " pan_tel_no = '$panchayathPhone',"
                        . " pan_description = '$panchayathDesc'"
                        . " WHERE pan_rid = '$panchayathId'";

                $i = $db->executeUpdate($updatePanchayath);

                $message['mes'] = "Panchayath updated successfully...";
            } else {

                if (isDuplicateEmailForPanchayath($db, $panchayathEmail)) {
                    throw new Exception("Duplicate Email ID");
                }

                if (!isValidPanchayathCode($db, $panchayathCode)) {
                    throw new Exception("Invalid panchayath code");
                }

                if (isDuplicatePanchayath($db, $panchayathCode)) {
                    throw new Exception("Duplicate panchayath");
                }

                $insertPanchayath = "INSERT INTO panchayath(pan_grama_rid, pan_code, pan_email, pan_password,"
                        . " pan_president, pan_conatct, pan_tel_no, pan_description)"
                        . " VALUES('$gramName', '$panchayathCode', '$panchayathEmail', '$panchayathPassword', '$panchayathPresident', '$panchayathMobile', '$panchayathPhone', '$panchayathDesc')";

                $i = $db->executeInsertAndGetId($insertPanchayath);

                $message['mes'] = "Panchayath added successfully...";
            }

            $message['success'] = true;
        } else if ('deletePanchayath' == $command) {
            $panchayathRid = $_POST['panchayathRid'];

            $deletePanchayath = "UPDATE panchayath SET pan_status = 0"
                    . " WHERE pan_rid = '$panchayathRid'";

            $res = $db->executeUpdate($deletePanchayath);

            $message['success'] = true;
            $message['mes'] = "Panchayath deleted successfully...";
        } else if ('saveComplaintStatus' == $command) {

            $complaintId = $_POST['complaintId'];
            $complaintAction = $_POST['complaintAction'];
            $rejectReason = isset($_POST['rejectReason']) ? $_POST['rejectReason'] : "";

            $rejectReason = str_replace("'", "\'", $rejectReason);

            $updateStatus = "UPDATE complaints SET"
                    . " comp_status = '$complaintAction',"
                    . " comp_reject_reason = '$rejectReason'"
                    . " WHERE comp_rid = '$complaintId'";

            $res = $db->executeUpdate($updateStatus);

            $message['success'] = true;
            $message['mes'] = "Successfully updated...";
        } else if ('markAsComplete' == $command) {
            $panchayathId = $_POST['panchayathId'];
            $complaintRid = $_POST['complaintRid'];
            $isSolvedBeforeDate = $_POST['isSolvedBeforeDate'];
            $solvedBeforeDate = $_POST['solvedBeforeDate'];

            $solvedDate = "NOW()";

            if (!empty($solveBeforeDate) && $isSolvedBeforeDate == 1) {
                $solveBeforeDate = displayToDb($solveBeforeDate);
                $solvedDate = "'$solveBeforeDate'";
            }

            $updateComplaints = "UPDATE complaints SET"
                    . " comp_status = 2, "  // COMPLETE
                    . " comp_solved_date = $solvedDate"
                    . " WHERE comp_rid = $complaintRid";

            $res = $db->executeUpdate($updateComplaints);

            $message['success'] = true;
            $message['mes'] = "Successfully updated...";
        } else if ('panchayathForgotPassword' == $command) {
            $emailId = $_POST['forgotPasswordEmail'];

            $selectPassword = "SELECT * FROM panchayath"
                    . " WHERE pan_email = '$emailId'";

            $res = $db->executeSelect($selectPassword);

            if (count($res) > 0) {
                $password = $res[0]['pan_password'];

                $subject = "Password Reset";
                $mailBody = "Your password is $password";

                if (sendMail($emailId, $subject, $mailBody)) {
                    $message['success'] = true;
                    $message['mes'] = "Mail sent...";
                } else {
                    throw new Exception("Something went wrong while sending mail");
                }
            } else {
                $message['success'] = false;
                $message['mes'] = "Invalid email ID";
            }
        } else if ('adminForgotPassword' == $command) {
            $emailId = $_POST['forgotPasswordEmail'];

            $selectPassword = "SELECT * FROM admin"
                    . " WHERE a_email = '$emailId'";

            $res = $db->executeSelect($selectPassword);

            if (count($res) > 0) {
                $password = $res[0]['a_password'];

                $subject = "Password Reset";
                $mailBody = "Your password is $password";

                if (sendMail($emailId, $subject, $mailBody)) {
                    $message['success'] = true;
                    $message['mes'] = "Mail sent...";
                } else {
                    throw new Exception("Something went wrong while sending mail");
                }
            } else {
                $message['success'] = false;
                $message['mes'] = "Invalid email ID";
            }
        } else if ('deleteCitizen' == $command) {

            $citizenRid = $_POST['citizenRid'];

            $deleteCitizen = "UPDATE `citizen` SET c_is_active = '0' WHERE c_rid = '$citizenRid'";


            $db->executeUpdate($deleteCitizen);

            echo 'Successfully deleted...';
        }
    } catch (Exception $ex) {
        $message['success'] = false;
        $message['mes'] = $ex->getMessage();
    }

    echo json_encode($message);
} else if (isset($_GET['command'])) {

    $message = array();

    try {

        $command = $_GET['command'];

        if ('getPanchayathDetail' == $command) {

            $panchayathRid = $_GET['panchayathRid'];

            $selectPanchayath = "SELECT * FROM panchayath"
                    . " JOIN grama ON(pan_grama_rid = g_rid)"
                    . " JOIN taluk ON(g_taluk_id = t_rid)"
                    . " JOIN districts ON(t_district_id = d_rid)"
                    . " WHERE pan_rid = $panchayathRid";

            $res = $db->executeSelect($selectPanchayath);

            if (count($res) > 0) {
                $message['success'] = true;
                $message['mes'] = $res[0];
            } else {
                throw new Exception("Invalid panchayth ID");
            }
        } else if ('getTalukList' == $command) {
            $district = $_GET['district'];

            $selectTaluks = "SELECT * FROM taluk"
                    . " WHERE t_district_id = $district"
                    . " ORDER BY t_name ASC";

            $res = $db->executeSelect($selectTaluks);

            $message['success'] = true;
            $message['mes'] = $res;
        } else if ('getGramaList' == $command) {
            $taluk = $_GET['taluk'];

            $selectGrama = "SELECT * FROM grama"
                    . " WHERE g_taluk_id = $taluk"
                    . " ORDER BY g_name ASC";

            $res = $db->executeSelect($selectGrama);

            $message['success'] = true;
            $message['mes'] = $res;
        }
    } catch (Exception $ex) {
        $message['success'] = false;
        $message['mes'] = $ex->getMessage();
    }

    echo json_encode($message);
}

function isDuplicateEmailForPanchayath(DB $db, $email) {
    $selectPanchayath = "SELECT 1 FROM panchayath"
            . " WHERE pan_email = '$email'";

    $res = $db->executeSelect($selectPanchayath);

    return count($res) > 0;
}

function isDuplicatePanchayath(DB $db, $panchayathCode) {
    $selectPanchayath = "SELECT 1 FROM panchayath"
            . " WHERE pan_code = '$panchayathCode'";

    $res = $db->executeSelect($selectPanchayath);

    return count($res) > 0;
}

function isValidPanchayathCode(DB $db, $panchayathCode) {
    $selectCode = "SELECT 1 FROM grama"
            . " WHERE g_code = '$panchayathCode'";

    $res = $db->executeSelect($selectCode);

    return count($res) > 0;
}
