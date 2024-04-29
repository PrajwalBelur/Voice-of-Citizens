<?php

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

function displayToDb($displayDate) {
    $date = str_replace('/', '-', $displayDate);
    $dob = date('Y-m-d', strtotime($date));
    return $dob;
}

function dbToDisplay($dbDate) {
    $date = str_replace('-', '/', $dbDate);
    $dob = date('d/m/Y', strtotime($date));
    return $dob;
}

function uploadFile($fieldName) {

    $uploadedFileType = strtolower(pathinfo($_FILES[$fieldName]["name"], PATHINFO_EXTENSION));
    $generatedFileName = uniqid(rand()) . "." . $uploadedFileType;
    $fullUploadPath = IMG_PATH . $generatedFileName;
    $acceptedFileTypes = array('jpg', 'jpeg', 'png');

    if (in_array($uploadedFileType, $acceptedFileTypes)) {
        if (is_uploaded_file($_FILES[$fieldName]['tmp_name']) &&
                move_uploaded_file($_FILES[$fieldName]['tmp_name'], $fullUploadPath)) {
            return $generatedFileName;
        } else {
            throw new Exception("An error occurred while uploading file. Please try again");
        }
    } else {
        throw new Exception("Please upload valid JPG or PNG image");
    }
}

function uploadMultipleFiles($fieldName) {

    $fielUploadPath = '../uploads/';
    $fileNamesArray = array();

    foreach ($_FILES[$fieldName]['name'] as $key => $name) {

        $uploadedFileType = strtolower(pathinfo($_FILES[$fieldName]['name'][$key], PATHINFO_EXTENSION));  // .txt

        $fileName = uniqid(rand()) . "." . $uploadedFileType;

        $fullUploadPath = $fielUploadPath . $fileName;    // uploads/hghghg3434gfgfd6757.txt

        if (move_uploaded_file($_FILES[$fieldName]['tmp_name'][$key], $fullUploadPath)) {
            $fileNamesArray[] = $fileName;
        } else {
            throw new Exception("An error occurred while uploading file. Please try again");
        }
    }

    return $fileNamesArray;
}

function sendMail($toAddress, $subject, $mailBody) {

    $mail = new PHPMailer;

    $mail->SMTPDebug = 0;
    $mail->isSMTP();                                      // Set mailer to use SMTP
    $mail->Host = 'smtp.gmail.com';                         // Specify main and backup SMTP servers
    $mail->SMTPAuth = true;                               // Enable SMTP authentication
    $mail->Username = 'projectdemoemail@gmail.com';                 // SMTP username
    $mail->Password = 'agiletec@demo';                           // SMTP password
    $mail->SMTPSecure = 'tls';                            // Enable encryption, 'ssl' also accepted

    $mail->Port = 587;
    $mail->SMTPOptions = array(
        'ssl' => array(
            'verify_peer' => false,
            'verify_peer_name' => false,
            'allow_self_signed' => true
        )
    );

    $mail->From = 'projectdemoemail@gmail.com';
    $mail->FromName = 'Voice of Citizen';
    $mail->addAddress($toAddress, '');

    $mail->WordWrap = 50;                                 // Set word wrap to 50 characters
    $mail->isHTML(true);                                  // Set email format to HTML

    $mail->Subject = $subject;
    $mail->Body = $mailBody;
    $mail->AltBody = $mailBody;

    if (!$mail->send()) {
        throw new Exception('Message could not be sent.: ' . $mail->ErrorInfo);
    } else {
        return true;
    }
}
