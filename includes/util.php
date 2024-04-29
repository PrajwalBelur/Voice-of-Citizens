<?php

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

function getGender($type) {
    switch ($type) {
        case 1: return 'Male';
        case 2: return 'Female';
        default: return 'Unknown';
    }
}

function uploadFile($fieldName, $folder) {

    $fielUploadPath = '../uploads/' . $folder . "/";
    $uploadedFileType = strtolower(pathinfo($_FILES[$fieldName]["name"], PATHINFO_EXTENSION));
    $fullUploadPath = $fielUploadPath . uniqid(rand()) . "." . $uploadedFileType;
    $acceptedFileTypes = array('jpg', 'jpeg', 'png');

    if (in_array($uploadedFileType, $acceptedFileTypes)) {
        if (is_uploaded_file($_FILES[$fieldName]['tmp_name']) &&
                move_uploaded_file($_FILES[$fieldName]['tmp_name'], $fullUploadPath)) {
            return $fullUploadPath;
        } else {
            throw new Exception("An error occurred while uploading file. Please try again");
        }
    } else {
        throw new Exception("Please upload valid JPG or PNG image");
    }
}
