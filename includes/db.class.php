<?php

class DB {

    private $con = null;

    function __construct() {
        $this->con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
        if (mysqli_connect_errno()) {
            throw new Exception("Failed to connect to MySQL: " . mysqli_connect_error());
        }
    }

    function __destruct() {
        $this->close();
    }

    public function close() {
        if ($this->con != null) {
            mysqli_close($this->con);
            $this->con = null;
        }
    }

    public function executeSelect($query) {
        try {
            if (!($result = mysqli_query($this->con, $query))) {
                throw new Exception(mysqli_error($this->con));
            }

            $results_array = array();
            while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC)) {
                $results_array[] = $row;
            }
            mysqli_free_result($result);
            return $results_array;
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function executeInsert($query) {
        try {
            if (!mysqli_query($this->con, $query)) {
                throw new Exception(mysqli_error($this->con));
            }
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function executeUpdate($query) {
        $this->executeInsert($query);
        return $this->con->affected_rows;
    }

    public function executeInsertAndGetId($query) {
        try {
            if (!mysqli_query($this->con, $query)) {
                throw new Exception(mysqli_error($this->con));
            }
            return mysqli_insert_id($this->con);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

}
