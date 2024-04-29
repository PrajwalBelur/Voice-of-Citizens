<?php
session_start();

require_once './includes/config.php';
require_once './includes/db.class.php';

require_once './actions/mailer_cron.php';

$db = new DB();

$mes = "";

if (isset($_POST['panchayathCode']) && isset($_POST['password'])) {

    $panchayathCode = $_POST['panchayathCode'];
    $password = $_POST['password'];

    $selectPanchayath = "SELECT * FROM panchayath"
            . " WHERE pan_code = '$panchayathCode' AND pan_password = '$password'"
            . " LIMIT 1";

    $res = $db->executeSelect($selectPanchayath);

    if (count($res) > 0) {
        $_SESSION['panchayath_rid'] = $res[0]['pan_rid'];
        header("location: panchayath/profile.php");
    } else {
        $mes = "Invalid Login Credentials";
    }
}
?>

<html>
<head>
  <meta charset="utf-8">
  <title>Panchayath Login</title>
  <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="assets/css/dashboard.css"/>
  <link rel="stylesheet" href="assets/css/custom.css"/>
</head>

<body>
  <div class="login-root">
    <div class="box-root flex-flex flex-direction--column" style="min-height: 100vh;flex-grow: 1;">
      <div class="loginbackground box-background--white padding-top--64">
        <div class="loginbackground-gridContainer">
          <div class="box-root flex-flex" style="grid-area: top / start / 8 / end;">
            <div class="box-root" style="background-image: linear-gradient(white 0%, rgb(247, 250, 252) 33%); flex-grow: 1;">
            </div>
          </div>
          <div class="box-root flex-flex" style="grid-area: 4 / 2 / auto / 5;">
            <div class="box-root box-divider--light-all-2 animationLeftRight tans3s" style="flex-grow: 1;"></div>
          </div>
          <div class="box-root flex-flex" style="grid-area: 6 / start / auto / 2;">
            <div class="box-root box-background--blue800" style="flex-grow: 1;"></div>
          </div>
          <div class="box-root flex-flex" style="grid-area: 7 / start / auto / 4;">
            <div class="box-root box-background--blue animationLeftRight" style="flex-grow: 1;"></div>
          </div>
          <div class="box-root flex-flex" style="grid-area: 8 / 4 / auto / 6;">
            <div class="box-root box-background--gray100 animationLeftRight tans3s" style="flex-grow: 1;"></div>
          </div>
          <div class="box-root flex-flex" style="grid-area: 2 / 15 / auto / end;">
            <div class="box-root box-background--cyan200 animationRightLeft tans4s" style="flex-grow: 1;"></div>
          </div>
          <div class="box-root flex-flex" style="grid-area: 3 / 14 / auto / end;">
            <div class="box-root box-background--blue animationRightLeft" style="flex-grow: 1;"></div>
          </div>
          <div class="box-root flex-flex" style="grid-area: 4 / 17 / auto / 20;">
            <div class="box-root box-background--gray100 animationRightLeft tans4s" style="flex-grow: 1;"></div>
          </div>
          <div class="box-root flex-flex" style="grid-area: 5 / 14 / auto / 17;">
            <div class="box-root box-divider--light-all-2 animationRightLeft tans3s" style="flex-grow: 1;"></div>
          </div>
        </div>
      </div>
      <div class="box-root padding-top--24 flex-flex flex-direction--column" style="flex-grow: 1; z-index: 9;">
        <div class="box-root padding-top--48 padding-bottom--24 flex-flex flex-justifyContent--center">
          <h1><a href="assets/css/inexx.html" rel="dofollow">Voice of Citizen</a></h1>
        </div>
        <div class="formbg-outer">
          <div class="formbg">
            <div class="formbg-inner padding-horizontal--48">
              <span class="padding-bottom--15">Panchayat Login</span>
             <form action="#" method="post">
              <form id="stripe-login">
                <div class="field padding-bottom--24">
                  <label for="email">Panchayath ID</label>
                   <input type="text" class="form-control" id="panchayathCode" name="panchayathCode"
                                           placeholder="Panchayth Code" autocomplete="off"/>
                </div>
                <div class="field padding-bottom--24">
                  <div class="grid--50-50">
                    <label for="password">Password</label>
                    <div class="reset-pass">
                      
                      <a href="#"></a>
                    </div>
                  </div>




                














                  <input type="password" class="form-control" id="password" name="password"
                                           placeholder="Password" autocomplete="off"/>
                </div>
                
                <div class="field padding-bottom--24">
                  

                  <button type="submit" class="btn btn-primary" id="btnLogin">
                                        Login
                                    </button>
                </div>
                
              </form>
            </div>
          </div>
          <div class="footer-link padding-top--24">
            <span>Forgot Password? <a href="#" data-target="#forgotPasswordModal" data-toggle="modal">click here</a></span>

            <div class="listing padding-top--24 padding-bottom--24 flex-flex center-center">
              <div class="container text-center">
        <p class="text-muted mb-0 py-2">© 2021 VoiceOfCitizen All rights reserved.</p>
      </div>
              
              
            

            </div>
             <?php if (!empty($mes)) { ?>
                                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                        <?php echo $mes; ?>
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                <?php } ?>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--forgot password modal-->
        <div class="modal fade" id="forgotPasswordModal" tabindex="-1"
             role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Forgot Password</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="formForgotPassword"
                              action="actions/panchayath_action.php" method="post">

                            <input type="hidden" name="command" value="panchayathForgotPassword"/>

                            <div class="form-group">
                                <label>Email ID</label>
                                <input type="email" class="form-control"
                                       id="forgotPasswordEmail" name="forgotPasswordEmail" autocomplete="off"/>
                            </div>
                            <div class="form-group text-right">
                                <button type="submit" id="btnPanchayathForgotPassword" class="btn btn-primary">
                                    Send Password
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

            
          </div>
        </div>
      </div>
    </div>
  </div>
   <script src="assets/js/jquery.min.js"></script>
        <script src="assets/js/bootstrap.bundle.min.js"></script>
   <script src="assets/js/panchayath/forgot_password.js"></script>
</body>
</html>