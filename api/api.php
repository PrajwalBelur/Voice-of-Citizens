<?php

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

require_once './Exception.php';
require_once './PHPMailer.php';
require_once './SMTP.php';

require_once '../includes/config.php';
require_once './util.php';
require_once './Panchayath.php';

$panchayath = new Panchayath();

$response = array();

try {

    $cmd = '';

    if (isset($_GET['cmd'])) {
        $cmd = $_GET['cmd'];
    } else if (isset($_POST['cmd'])) {
        $cmd = $_POST['cmd'];
    } else {
        throw new Exception("Invalid HTTP request!");
    }

    if ('getRegPageDetails' == $cmd) {

        $info = array(
            'district' => $panchayath->getDistrictList(),
            'taluk' => $panchayath->getTalukList(0),
            'grama' => $panchayath->getGramaList(),
            'panchayath' => $panchayath->getPanchayathList(0)
        );

        $response['success'] = true;
        $response['info'] = $info;
    } else if ('getDistrictList' == $cmd) {

        $res = $panchayath->getDistrictList();

        if (count($res) > 0) {
            $response['success'] = true;
            $response['info'] = $res;
        } else {
            throw new Exception("Districts list is empty");
        }
    } else if ('getTalukList' == $cmd) {

        $districtId = $_GET['districtId'];

        $res = $panchayath->getTalukList($districtId);

        if (count($res) > 0) {
            $response['success'] = true;
            $response['info'] = $res;
        } else {
            throw new Exception("Taluk list is empty");
        }
    } else if ('getGramaList' == $cmd) {

        $talukId = $_GET['talukId'];

        $res = $panchayath->getPanchayathList($talukId);

        if (count($res) > 0) {
            $response['success'] = true;
            $response['info'] = $res;
        } else {
            throw new Exception("Panchayath list is empty");
        }
    } else if ('getComplaints' == $cmd) {
        $citizenId = $_GET['citizenId'];

        $res = $panchayath->getComplaintsList($citizenId);
        $response['success'] = true;
        $response['info'] = $res;
    } else if ('getPanchayathDetails' == $cmd) {
        $panchayathRid = $_GET['panchayathRid'];

        $res = $panchayath->getPanchayathDetails($panchayathRid);
        if (count($res) > 0) {
            $response['success'] = true;
            $response['info'] = $res[0];
        } else {
            throw new Exception("Panchayath list is empty");
        }
    } else if ('login' == $cmd) {
        $loginId = $_POST['loginId'];
        $password = $_POST['password'];

        $res = $panchayath->login($loginId, $password);

        if (count($res) > 0) {
            $response['success'] = true;
            $response['info'] = $res[0];
        } else {
            throw new Exception("Invalid login credentials");
        }
    } else if ('register' == $cmd) {
        $profilePhoto = uploadFile("profile");

        $grama = $_POST['grama'];
        $name = $_POST['name'];
        $password = $_POST['password'];
        $address = $_POST['address'];
        $mobileNo = $_POST['mobileNo'];
        $email = $_POST['email'];
        $gender = $_POST['gender'];

        $res = $panchayath->register($profilePhoto, $grama, $name, $password, $address, $mobileNo, $email, $gender);

        if ($res > 0) {
            $response['success'] = true;
            $response['info'] = "Successfully registered";
        } else {
            throw new Exception("Something went wrong while registering");
        }
    } else if ('update' == $cmd) {
        $citizenRid = $_POST['citizenRid'];
        $name = $_POST['name'];
        $password = $_POST['password'];
        $address = $_POST['address'];
        $mobileNo = $_POST['mobileNo'];
        $email = $_POST['email'];

        $res = $panchayath->update($citizenRid, $name, $password, $address, $mobileNo, $email);

        $result = $panchayath->getCitizenDetails($citizenRid);

        if (count($result) > 0) {
            $response['success'] = true;
            $response['info'] = $result[0];
        } else {
            throw new Exception("Invalid login credentials");
        }
    } else if ('saveComplaint' == $cmd) {
        $photos = uploadMultipleFiles("photo");
        $citizenId = $_POST['citizenId'];
        $panchayathId = $_POST['panchayathId'];
        $mobile = $_POST['mobile'];
        $compType = $_POST['compType'];
        $location = $_POST['location'];
        $landMark = $_POST['landMark'];
        $description = $_POST['description'];

        $res = $panchayath->saveComplaints($citizenId, $panchayathId, $mobile, $compType, $location, $landMark, $description, $photos);
        if ($res > 0) {
            $response['success'] = true;
            $response['info'] = "Successfully submitted";
        } else {
            throw new Exception("Something went wrong while submitting");
        }
    } else if ('deleteProfile' == $cmd) {
        $citizenRid = $_POST['citizenRid'];
        $res = $panchayath->deleteProfile($citizenRid);
        if ($res > 0) {
            $response['success'] = true;
            $response['info'] = "Successfully deleted";
        } else {
            throw new Exception("Something went wrong while deleting");
        }
    } else if ('confirm' == $cmd) {
        $complaintRid = $_POST['complaintRid'];

        $res = $panchayath->markAsComplete($complaintRid);
        if ($res > 0) {
            $response['success'] = true;
            $response['info'] = "Successfully updated";
        } else {
            throw new Exception("Something went wrong while updating");
        }
    } else if ('resetUserPassword' == $cmd) {
        $email = $_POST['email'];
        $res = $panchayath->forgotPassword($email);
        if (count($res) > 0) {

            $password = $res[0]['c_password'];

            $subject = "Password Reset";
            $mailBody = "Your password is $password";

            if (sendMail($email, $subject, $mailBody)) {
                $response['success'] = true;
                $response['info'] = "Mail sent...";
            } else {
                throw new Exception("Something went wrong while sending mail");
            }
        } else {
            throw new Exception("Invalid email ID");
        }
    } else if ("getCompletedImages" == $cmd) {
        $complaintRid = $_GET['complaintRid'];
        $res = $panchayath->getCompletedImages($complaintRid);

        $images = array();

        foreach ($res as $image) {
            $images[] = $image['ci_img_url'];
        }

        $response['success'] = true;
        $response['info'] = $images;
    }
} catch (\Exception $ex) {
    $response['success'] = false;
    $response['error'] = $ex->getMessage();
}

echo json_encode($response);
