<?php

session_start();

if (!isset($_SESSION['admin_rid']) || $_SESSION['admin_rid'] == 0) {
    header("location: index.php");
    die();
}