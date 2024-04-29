<?php
require_once './session.php';
require_once '../includes/config.php';
require_once '../includes/db.class.php';

$db = new DB();

$yearsArray = array();
$complaintsCountArray = array();
$solvedComplaintsCountArray = array();

$sql = "SELECT COUNT(*) AS no_of_complaints, SUM(comp_is_confirmed_by_citizen) AS solved_complaints,"
        . " YEAR(comp_date_time) AS comp_date FROM complaints"
        . " GROUP BY comp_date";

$complaintsArray = $db->executeSelect($sql);

foreach ($complaintsArray as $comp) {

    $yearsArray[] = $comp['comp_date'];
    $complaintsCountArray[] = $comp['no_of_complaints'];
    $solvedComplaintsCountArray[] = $comp['solved_complaints'];
}

$years = implode(", ", $yearsArray);
$complaintsCount = implode(", ", $complaintsCountArray);
$solvedComplaintsCount = implode(", ", $solvedComplaintsCountArray);
?>

<!DOCTYPE html>
<html>
    <?php require_once '../common/header.php'; ?>
    <script src="../assets/js/Chart.bundle.min.js"></script>
    <body>
        <?php require_once './nav.php'; ?>

        <div class="container-fluid">
            <div class="row">
                <?php require_once './left_nav.php'; ?>
                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
                    <br>
                    <div class="card">
                        <div class="card-body">
                            <canvas id="myChart" width="300" height="150"></canvas>
                        </div>
                    </div>
                </main>
            </div>
        </div>
        <script>
            var ctx = document.getElementById('myChart').getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: [<?php echo $years; ?>],
                    datasets: [{
                            label: 'Number of Complaints Raised',
                            data: [<?php echo $complaintsCount; ?>],
                            backgroundColor: "rgba(1, 13, 3, 0.23)"
                        },
                        {
                            label: 'Number of Complaints Solved',
                            data: [<?php echo $solvedComplaintsCount; ?>],
                            backgroundColor: "rgba(155, 70, 18, 0.23)"
                        }]
                },
                options: {
                    scales: {
                        yAxes: [{
                                ticks: {
                                    beginAtZero: true
                                }
                            }]
                    }
                }
            });
        </script>
        <?php require_once '../common/footer.php'; ?>
    </body>
</html>
