<?php
require_once './session.php';
require_once '../includes/config.php';
require_once '../includes/db.class.php';

$db = new DB();

$selectDistrict = "SELECT * FROM districts"
        . " ORDER BY d_name";

$districtList = $db->executeSelect($selectDistrict);

$selectPanchayaths = "SELECT * FROM panchayath"
        . " JOIN grama ON(pan_grama_rid = g_rid)"
        . " WHERE pan_status = 1"
        . " ORDER BY g_name";

$panchayathList = $db->executeSelect($selectPanchayaths);
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
                            <h3>Panchayaths</h3>
                            <hr>
                            <div class="row p-3">
                                <button class="btn btn-primary btn-sm"
                                        data-target="#addPanchayath" data-toggle="modal">
                                    Add New Panchayath
                                </button>
                            </div>
                            <table class="table table-bordered table-hover table-sm">
                                <tr>
                                    <th>#</th>
                                    <th>Code</th>
                                    <th>Grama Name</th>
                                    <th>Email</th>
                                    <th>Action</th>
                                </tr>
                                <?php
                                $i = 0;
                                foreach ($panchayathList as $row) {
                                    $panchayathRid = $row['pan_rid'];
                                    ?>
                                    <tr>
                                        <td><?php echo ++$i; ?></td>
                                        <td><?php echo $row['pan_code']; ?></td>
                                        <td><?php echo $row['g_name']; ?></td>
                                        <td><?php echo $row['pan_email']; ?></td>
                                        <td>
                                            <a href="#" onclick="editPanchayath(<?php echo $panchayathRid; ?>)">Edit</a>
                                            /
                                            <a href="#" onclick="deletePunchayath(<?php echo $panchayathRid; ?>)">Delete</a>
                                        </td>
                                    </tr>
                                <?php } ?>
                                <tr>
                                    <?php if ($i == 0) { ?>
                                        <td colspan="100%" class="alert alert-danger text-center">
                                            No record
                                        </td>
                                    <?php } ?>
                                </tr>
                            </table>
                        </div>
                    </div>
                </main>
            </div>
        </div>

        <div class="modal fade" id="addPanchayath" tabindex="-1" role="dialog"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Add Panchayath</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="formAddPanchayath" action="../actions/panchayath_action.php" method="post">

                            <input type="hidden" name="command" value="addPanchayath"/>
                            <input type="hidden" name="panchayathId" id="panchayathId" value="0"/>

                            <div class="form-group">
                                <label>District</label>
                                <select class="form-control" id="district" name="district">
                                    <option value="-1">Choose..</option>
                                    <?php foreach ($districtList as $row) { ?>
                                        <option value="<?php echo $row['d_rid']; ?>"><?php echo $row['d_name']; ?></option>
                                    <?php } ?>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Taluk</label>
                                <select class="form-control" id="taluk" name="taluk">
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Grama Name</label>
                                <select class="form-control" id="gramName" name="gramName">
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Panchayath Code</label>
                                <input type="text" class="form-control" id="panchayathCode" name="panchayathCode"
                                       autocomplete="off" readonly/>
                            </div>
                            <div class="form-group">
                                <label>President Name</label>
                                <input type="text" class="form-control" id="panchayathPresident" name="panchayathPresident"
                                       autocomplete="off"/>
                            </div>
                            <div class="form-group">
                                <label>President Mobile Number</label>
                                <input type="text" class="form-control" id="panchayathMobile" name="panchayathMobile"
                                       autocomplete="off" maxlength="10"/>
                            </div>
                            <div class="form-group">
                                <label>Panchayath Phone Number</label>
                                <input type="text" class="form-control" id="panchayathPhone" name="panchayathPhone"
                                       autocomplete="off" maxlength="6"/>
                            </div>
                            <div class="form-group">
                                <label>Panchayath Email ID</label>
                                <input class="form-control" type="email" id="panchayathEmail" name="panchayathEmail"
                                       autocomplete="off"/>
                            </div>
                            <div class="form-group">
                                <label>Panchayath Password</label>
                                <input class="form-control" type="password" id="panchayathPassword" name="panchayathPassword"
                                       autocomplete="off"/>
                            </div>
                            <div class="form-group">
                                <label>Description</label>
                                <textarea class="form-control" id="panchayathDesc" name="panchayathDesc"
                                          autofocus="off"></textarea>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" id="btnAddPanchayath">Save</button>
                    </div>
                </div>
            </div>
        </div>

        <?php require_once '../common/footer.php'; ?>
        <script src="../assets/js/admin/panchayath.js"></script>
    </body>
</html>
