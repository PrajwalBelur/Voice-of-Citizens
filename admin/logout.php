<?php

session_start();

session_destroy();

$_SESSION['admin_rid'] = "";

header("location: index.php");
die();
