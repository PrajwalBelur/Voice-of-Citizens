<?php
require_once './session.php';
require_once '../includes/config.php';
require_once '../includes/db.class.php';

$db = new DB();

$panchayathId = $_SESSION['panchayath_rid'];

$selectProfile = "SELECT * FROM panchayath"
        . " JOIN grama ON(pan_grama_rid = g_rid)"
        . " JOIN taluk ON(g_taluk_id = t_rid)"
        . " JOIN districts ON(t_district_id = d_rid)"
        . " WHERE pan_rid = $panchayathId";

$res = $db->executeSelect($selectProfile);

if (count($res) > 0) {
    $res = $res[0];
} else {
    header("location: ../index.php");
    die();
}
?>

<!DOCTYPE html>
<html>
    <?php require_once '../common/header.php'; ?>
    <body>

        <?php require_once './nav.php'; ?>

        <div class="container-fluid">
            <div class="row">
                <?php require_once './left_nav.php'; ?>

                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
                    <br>
                    <div class="row">
                        <div class="col-md-2"></div>
                        <div class="col-md-8">
                            <div class="card">
                                <div class="card-body">
                                    <h3>Profile</h3>
                                    <hr>
                                    <div class="form-group">
                                        <h6>District</h6>
                                        <p><?php echo $res['d_name'] ?></p>
                                    </div>
                                    <div class="form-group">
                                        <h6>Taluk</h6>
                                        <p><?php echo $res['t_name'] ?></p>
                                    </div>
                                    <div class="form-group">
                                        <h6>Grama Name</h6>
                                        <p><?php echo $res['g_name'] ?></p>
                                    </div>
                                    <div class="form-group">
                                        <h6>Panchayath Code</h6>
                                        <p><?php echo $res['pan_code'] ?></p>
                                    </div>
                                    <div class="form-group">
                                        <h6>President Name</h6>
                                        <p><?php echo $res['pan_president'] ?></p>
                                    </div>
                                    <div class="form-group">
                                        <h6>President's Contact Number</h6>
                                        <p><?php echo $res['pan_conatct'] ?></p>
                                    </div>
                                    <div class="form-group">
                                        <h6>Panchayath Telephone Number</h6>
                                        <p><?php echo $res['pan_tel_no'] ?></p>
                                    </div>
                                    <div class="form-group">
                                        <h6>Email ID</h6>
                                        <p><?php echo $res['pan_email'] ?></p>
                                    </div>
                                    <div class="form-group">
                                        <h6>Description</h6>
                                        <p><?php echo $res['pan_description'] ?></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2"></div>
                    </div>
                </main>
            </div>
        </div>

        <?php require_once '../common/footer.php'; ?>
    </body>
</html>
