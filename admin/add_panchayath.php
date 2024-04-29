<?php
require_once './session.php';

require_once '../includes/config.php';
require_once '../includes/db.class.php';

$db = new DB();

$selectGrama = "SELECT * FROM grama ORDER BY g_name ASC";

$gramaList = $db->executeSelect($selectGrama);
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
                        <div class="col-md-3"></div>
                        <div class="col-md-6">
                            <form id="formAddPanchayath" action="../actions/panchayath.php" method="post">

                                <input type="hidden" name="command" value="addPanchayath"/>

                                <div class="card">
                                    <div class="card-body">
                                        <h3 class="text-center">Add Panchayath</h3>
                                        <br>
                                        <div class="form-group">
                                            <label>Grama Name</label>
                                            <select class="form-control" id="gramName" name="gramName">
                                                <option value="-1">Choose..</option>
                                                <?php foreach ($gramaList as $row) { ?>
                                                    <option value="<?php echo $row['g_rid'] ?>"><?php echo $row['g_name']; ?></option>
                                                <?php } ?>
                                            </select>
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
                                        <div class="form-group text-center">
                                            <button type="submit" id="btnAddPanchayath" class="btn btn-primary">
                                                Submit
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="col-md-3"></div>
                    </div>
                </main>
            </div>
        </div>

        <?php require_once '../common/footer.php'; ?>
        <script src="../assets/js/admin/panchayath.js"></script>
    </body>
</html>
