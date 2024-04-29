<?php

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

require_once './api/Exception.php';
require_once './api/PHPMailer.php';
require_once './api/SMTP.php';

require_once './api/util.php';

$db = new DB();

$selectComplaints = "SELECT pan_email, COUNT(*) AS comp_count FROM complaints"
        . " JOIN panchayath ON (comp_panchayath = comp_panchayath)"
        . " WHERE comp_status = 0 AND DATEDIFF(NOW(), DATE(comp_date_time)) > 3"
        . " GROUP BY comp_panchayath, pan_email";

$complaintsCount = $db->executeSelect($selectComplaints);

$subject = 'Pending complaints alert';

foreach ($complaintsCount as $comp) {
    $email = $comp['pan_email'];
    $compCount = $comp['comp_count'];

    $mailBody = "You have " . $compCount . " complaint(s) pending to take action.";

    if (sendMail($email, $subject, $mailBody)) {
        $response['success'] = true;
        $response['info'] = "Mail sent...";
    } else {
        throw new Exception("Something went wrong while sending mail");
    }
}
