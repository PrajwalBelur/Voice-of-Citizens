<?php
require_once './session.php';
require_once '../includes/config.php';
require_once '../includes/db.class.php';

$db = new DB();

$selectCitizens = "SELECT * FROM citizen"
        . " JOIN panchayath ON(c_grama = pan_rid)"
        . " JOIN grama ON(pan_grama_rid = g_rid)"
        . " WHERE c_is_active = '1'"
        . " ORDER BY c_name";

$citizensList = $db->executeSelect($selectCitizens);
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
                            <h3>Citizens</h3>
                            <hr>
                            <table class="table table-bordered table-hover table-sm">
                                <tr>
                                    <th>#</th>
                                    <th>Name</th>
                                    <th>Contact</th>
                                    <th>Email</th>
                                    <th>Village</th>
                                    <th>Action</th>
                                </tr>
                                <?php
                                $i = 0;
                                foreach ($citizensList as $row) {
                                    $citizenRid = $row['c_rid'];
                                    ?>
                                    <tr>
                                        <td><?php echo ++$i; ?></td>
                                        <td><?php echo $row['c_name']; ?></td>
                                        <td><?php echo $row['c_contact']; ?></td>
                                        <td><?php echo $row['c_email']; ?></td>
                                        <td><?php echo $row['g_name']; ?></td>
                                        <td>
                                            <a href="#" onclick="deleteUser(<?php echo $citizenRid; ?>)">Delete</a>
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

        <?php require_once '../common/footer.php'; ?>
        <script src="../assets/js/admin/citizen.js"></script>
    </body>
</html>
