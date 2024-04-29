<?php
require_once './session.php';
require_once '../includes/config.php';
require_once '../includes/db.class.php';

$db = new DB();

$panchayathId = $_SESSION['panchayath_rid'];

$selectComplaints = "SELECT *, DATE_FORMAT(comp_date_time, '%d %M %Y %h:%i %p') AS comp_date_time_formatted FROM complaints"
        . " JOIN citizen ON(comp_from = c_rid)"
        . " WHERE comp_panchayath = $panchayathId"
        . " ORDER BY comp_rid DESC";

$res = $db->executeSelect($selectComplaints);
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
                    <div class="card">
                        <div class="card-body">
                            <h3>Complaints List</h3>
                            <hr>
                            <table class="table table-bordered table-hover">
                                <tr>
                                    <th>Sl No</th>
                                    <th>Name</th>
                                    <th>Location</th>
                                    <th>Date Time</th>
                                    <th>Action</th>
                                </tr>
                                <?php
                                $i = 0;
                                if (count($res) > 0) {
                                    foreach ($res as $row) {
                                        $complaintRid = $row['comp_rid'];
                                        $compStatus = $row['comp_status'];
                                        $isSolved = $row['comp_is_solved'];
                                        ?>
                                        <tr>
                                            <td><?php echo ++$i; ?></td>
                                            <td><?php echo $row['c_name']; ?></td>
                                            <td><?php echo $row['comp_loc']; ?></td>
                                            <td><?php echo $row['comp_date_time_formatted']; ?></td>
                                            <td>
                                                <a href="complaint_details.php?complaintRid=<?php echo $complaintRid ?>">
                                                    View Details
                                                </a>
                                                <?php
                                                // only accepted and unsolved complaints can be marked as complete
                                                if ($compStatus == 1 && $isSolved == 0) {
                                                    ?>
                                                    /
                                                    <a href="#" onclick="showMarkAsCompleteModal(
                                                                                '<?php echo $row['comp_rid']; ?>',
                                                                                '<?php echo $panchayathId; ?>',
                                                                                '<?php echo $row['comp_date_time']; ?>',
                                                                                '<?php echo $row['comp_scheduled_date']; ?>')">
                                                        Mark as Completed
                                                    </a>
                                                <?php } ?>
                                            </td>
                                        </tr>
                                        <?php
                                    }
                                } else {
                                    ?>
                                    <tr>
                                        <td colspan="5" class="alert alert-warning text-center">
                                            No complaints
                                        </td>
                                    </tr>
                                <?php } ?>
                            </table>
                        </div>
                    </div>
                </main>
            </div>
        </div>


        <!-- Modal -->
        <div class="modal fade" id="markAsCompleteModal" tabindex="-1"
             role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Mark as Complete</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="formMarkAsComplete"
                              action="../actions/panchayath_action.php" method="post">

                            <input type="hidden" name="command" value="markAsComplete"/>
                            <input type="hidden" id="complaintRid" name="complaintRid"/>
                            <input type="hidden" id="panchayathId" name="panchayathId"/>

                            <div class="form-group">
                                <label>Complaint Date / Time</label>
                                <input type="text" class="form-control" id="complaintDate" name="complaintDate"
                                       disabled/>
                            </div>
                            <div class="form-group">
                                <label>Scheduled Date</label>
                                <input type="text" class="form-control" id="scheduledDate" name="scheduledDate"
                                       disabled/>
                            </div>
                            <div class="form-group">
                                <label>Solved before scheduled date?</label>
                                <br>
                                <input type="radio" id="isSolvedBeforeDateYes" name="isSolvedBeforeDate" value="1"/>
                                <label for="isSolvedBeforeDateYes">Yes</label>
                                <input type="radio" id="isSolvedBeforeDateNo" name="isSolvedBeforeDate" value="0" checked/>
                                <label for="isSolvedBeforeDateNo">No</label>
                            </div>
                            <div class="form-group d-none" id="solvedBeforeDateDiv">
                                <label>Solved on Date</label>
                                <input type="text" class="form-control" id="solvedBeforeDate" name="solvedBeforeDate"
                                       autocomplete="off"/>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" id="btnMarksAsComplete">Save</button>
                    </div>
                </div>
            </div>
        </div>

        <?php require_once '../common/footer.php'; ?>
        <script src="../assets/js/panchayath/complaints.js"></script>
    </body>
</html>
