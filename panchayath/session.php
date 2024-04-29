<?php

session_start();

if (!isset($_SESSION['panchayath_rid']) || $_SESSION['panchayath_rid'] == 0) {
    header("location: ../index.php");
    die();
}