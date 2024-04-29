<?php
require_once './session.php';
require_once '../includes/config.php';
require_once '../includes/db.class.php';

$db = new DB();

$complaintRid = -1;

$res = array();
$images = array();

if (isset($_GET['complaintRid'])) {

    $complaintRid = $_GET['complaintRid'];

    $selectComplaints = "SELECT *,"
            . " DATE_FORMAT(comp_date_time, '%d %M %Y %h:%i %p') AS comp_date_time"
            . " FROM complaints"
            . " JOIN citizen ON(comp_from = c_rid)"
            . " WHERE comp_rid = $complaintRid";

    $res = $db->executeSelect($selectComplaints);

    if (count($res) <= 0) {
        header("location: complaints.php");
        die();
    } else {
        $res = $res[0];

        $selectImages = "SELECT * FROM complaint_images"
                . " WHERE ci_comp_rid = " . $res['comp_rid'];

        $images = $db->executeSelect($selectImages);
    }
} else {
    header("location: complaints.php");
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
                                    <h3>Details</h3>
                                    <hr>
                                    <form id="processComplaintForm"
                                          action="../actions/panchayath_action.php"
                                          method="post">

                                        <input type="hidden" name="command" value="saveComplaintStatus"/>
                                        <input type="hidden" name="complaintId" value="<?php echo $complaintRid; ?>"/>

                                        <div class="form-group">
                                            <h6>From</h6>
                                            <p><?php echo $res['c_name'] ?></p>
                                        </div>
                                        <div class="form-group">
                                            <h6>Contact</h6>
                                            <p><?php echo $res['comp_contact'] ?></p>
                                        </div>
                                        <div class="form-group">
                                            <h6>Description</h6>
                                            <p><?php echo $res['comp_description'] ?></p>
                                        </div>
                                        <div class="form-group">
                                            <h6>Location</h6>
                                            <p><?php echo $res['comp_loc'] ?></p>
                                        </div>
                                        <div class="form-group">
                                            <h6>Land Mark</h6>
                                            <p><?php echo $res['comp_land_mark'] ?></p>
                                        </div>
                                        <div class="form-group">
                                            <h6>Type</h6>
                                            <p><?php echo $res['comp_type'] ?></p>
                                        </div>
                                        <div class="form-group">
                                            <h6>Submitted Date & Time</h6>
                                            <p><?php echo $res['comp_date_time'] ?></p>
                                        </div>
                                        <div class="form-group">
                                            <h6>Photos</h6>
                                            <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                                                <ol class="carousel-indicators">
                                                    <?php for ($i = 0; $i < sizeof($images); $i++) { ?>
                                                        <li data-target="#carouselExampleIndicators" data-slide-to="<?php echo $i; ?>"
                                                            <?php echo $i == 0 ? ' class="active"' : ''; ?>></li>
                                                        <?php } ?>
                                                </ol>
                                                <div class="carousel-inner">
                                                    <?php for ($i = 0; $i < sizeof($images); $i++) { ?>
                                                        <div class="carousel-item <?php echo $i == 0 ? 'active' : ''; ?>">
                                                            <img src="../uploads/<?php echo $images[$i]['ci_img_url']; ?>" class="d-block w-100" alt="...">
                                                        </div>
                                                    <?php } ?>
                                                </div>
                                                <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                                    <span class="sr-only">Previous</span>
                                                </a>
                                                <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                                    <span class="sr-only">Next</span>
                                                </a>
                                            </div>

                                        </div>
                                        <div class="form-group">
                                            <h6>Status</h6>
                                            <p>
                                                <?php
                                                $status = $res['comp_status'];

                                                if ($status == 0) {
                                                    echo 'PENDING';
                                                } else if ($status == 1) {
                                                    echo 'ACCEPTED';
                                                } else if ($status == -1) {
                                                    echo 'REJECTED';
                                                } else if ($status == 2) {
                                                    echo 'COMPLETED';
                                                }
                                                ?>
                                            </p>
                                        </div>
                                        <?php if ($status == 0) {   // PENDING complaints   ?>
                                            <div class="form-group">
                                                <h6>Action</h6>
                                                <p>
                                                    <select class="form-control" name="complaintAction" id="complaintAction">
                                                        <option value="-2">--Select Action--</option>
                                                        <option value="1">Accept</option>
                                                        <option value="-1">Reject</option>
                                                    </select>
                                                </p>
                                            </div>
                                            <div class="form-group d-none" id="rejectReasonDiv">
                                                <h6>Reject Reason</h6>
                                                <p>
                                                    <textarea class="form-control" name="rejectReason" id="rejectReason"></textarea>
                                                </p>
                                            </div>
                                            <div class="form-group text-center">
                                                <button class="btn btn-primary" id="btnSaveComplaintStatus">
                                                    Save
                                                </button>
                                            </div>
                                        <?php } ?>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2"></div>
                    </div>
                </main>
            </div>
        </div>

        <?php require_once '../common/footer.php'; ?>
        <script src="../assets/js/panchayath/complaints.js"></script>
    </body>
</html>
