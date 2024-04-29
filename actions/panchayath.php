<?php

require_once '../includes/config.php';
require_once '../includes/db.class.php';

$db = new DB();

if (isset($_POST['command'])) {

    $message = array();

    try {

        $command = $_POST['command'];

        if ('addPanchayath' == $command) {
            $gramName = $_POST['gramName'];
            $panchayathEmail = $_POST['panchayathEmail'];
            $panchayathPassword = $_POST['panchayathPassword'];

            if (isDuplicateEmailForPanchayath($db, $panchayathEmail)) {
                throw new Exception("Duplicate Email ID");
            }

            $insertPanchayath = "INSERT INTO panchayath(pan_grama_rid, pan_email, pan_password)"
                    . " VALUES('$gramName', '$panchayathEmail', '$panchayathPassword')";

            $i = $db->executeInsertAndGetId($insertPanchayath);

            $message['success'] = true;
            $message['mes'] = "Panchayath added successfully...";
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
