<?php

session_start();

session_destroy();

$_SESSION['panchayath_rid'] = "";

header("location: ../index.php");
die();
