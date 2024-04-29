<?php

require_once '../includes/db.class.php';

class Panchayath {

    public function __construct() {
        $this->db = new DB();
    }

    public function __destruct() {
        $this->db = null;
    }

    public function login($loginId, $password) {
        try {
            $select = "SELECT * FROM citizen"
                    . " LEFT JOIN panchayath ON(c_grama = pan_rid)"
                    . " LEFT JOIN grama ON(pan_grama_rid = g_rid)"
                    . " WHERE (c_email = '$loginId' OR c_contact = '$loginId')"
                    . " AND c_password = '$password'"
                    . " AND c_is_active = 1"
                    . " LIMIT 1";

            return $this->db->executeSelect($select);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function panchayathLogin($loginId, $password) {
        try {
            $select = "SELECT * FROM panchayath"
                    . " LEFT JOIN grama ON(pan_grama_rid = g_rid)"
                    . " WHERE pan_code = '$loginId'"
                    . " AND pan_password = '$password'"
                    . " AND pan_status = 1"
                    . " LIMIT 1";

            return $this->db->executeSelect($select);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function getDistrictList() {
        try {
            $select = "SELECT * FROM districts"
                    . " ORDER BY d_name ASC";

            return $this->db->executeSelect($select);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function getGramaList() {
        try {
            $select = "SELECT * FROM grama"
                    . " ORDER BY g_name ASC";

            return $this->db->executeSelect($select);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function getTalukList($districtId = 0) {
        try {
            $select = "SELECT * FROM taluk";

            if ($districtId > 0) {
                $select .= " WHERE t_district_id = $districtId";
            }

            $select .= " ORDER BY t_name ASC";

            return $this->db->executeSelect($select);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function getPanchayathList($talukId = 0) {
        try {
            $select = "SELECT * FROM panchayath"
                    . " JOIN grama ON(pan_grama_rid = g_rid)";

            if ($talukId > 0) {
                $select .= " WHERE g_taluk_id = $talukId";
            }

            $select .= " ORDER BY g_name ASC";

            return $this->db->executeSelect($select);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function register($profilePhoto, $grama, $name, $password, $address, $mobileNo, $email, $gender) {
        try {

            if ($this->isDuplicateContact($this->db, $mobileNo)) {
                throw new Exception("Duplicate contact number!");
            }

            if (!empty($email) && $this->isDuplicateEmail($this->db, $email)) {
                throw new Exception("Duplicate email ID");
            }

            $email = empty($email) ? "NULL" : "'$email'";

            $insert = "INSERT INTO citizen(c_name, c_contact, c_email, "
                    . "c_password, c_gender, c_address, c_grama, c_profile)"
                    . " VALUES('$name', '$mobileNo', $email, '$password', "
                    . "'$gender', '$address', '$grama', '$profilePhoto')";

            return $this->db->executeInsertAndGetId($insert);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function isDuplicateContact(DB $db, $mobileNo) {
        $select = "SELECT 1 FROM citizen"
                . " WHERE c_contact = '$mobileNo'";

        $res = $db->executeSelect($select);
        return count($res) > 0;
    }

    public function isDuplicateEmail(DB $db, $email) {
        $select = "SELECT 1 FROM citizen"
                . " WHERE c_email = '$email'";

        $res = $db->executeSelect($select);
        return count($res) > 0;
    }

    public function saveComplaints($citizenId, $panchayathId, $mobile, $compType, $location, $landMark, $description, $photos) {
        try {
            $insert = "INSERT INTO complaints(comp_from, comp_panchayath, comp_contact, comp_type, comp_land_mark,"
                    . " comp_description, comp_loc, comp_date_time)"
                    . " VALUES('$citizenId', '$panchayathId', '$mobile', '$compType', '$landMark', '$description', "
                    . "'$location', NOW())";

            $compRid = $this->db->executeInsertAndGetId($insert);

            return 1;
//            return $this->saveComplaintImages($this->db, $compRid, $photos);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function getComplaintsList($citizenId) {
        try {
            $select = "SELECT *, DATE_FORMAT(comp_date_time, '%d %M %Y %H:%i %p') AS comp_date_time,"
                    . " DATE_FORMAT(comp_solved_date, '%d %M %Y') AS comp_solved_date"
                    . " FROM complaints"
                    . " WHERE comp_from	= $citizenId"
                    . " ORDER BY comp_rid DESC";
            return $this->db->executeSelect($select);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function getComplaintsListForPanchayath($panchayathRid) {
        try {
            $select = "SELECT *, DATE_FORMAT(comp_date_time, '%d %M %Y %H:%i %p') AS comp_date_time,"
                    . " DATE_FORMAT(comp_solved_date, '%d %M %Y') AS comp_solved_date"
                    . " FROM complaints"
                    . " LEFT JOIN citizen ON (comp_from = c_rid)"
                    . " WHERE comp_panchayath = $panchayathRid"
                    . " ORDER BY comp_rid DESC";
            return $this->db->executeSelect($select);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function getPanchayathDetails($panchayathRid) {
        try {
            $select = "SELECT * FROM panchayath"
                    . " JOIN grama ON(pan_grama_rid = g_rid)"
                    . " WHERE pan_rid	= $panchayathRid";
            return $this->db->executeSelect($select);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function deleteProfile($citizenRid) {
        try {
            $update = "UPDATE citizen SET c_is_active = 0 WHERE c_rid = $citizenRid";
            return $this->db->executeUpdate($update);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function forgotPassword($email) {
        try {
            $select = "SELECT * FROM citizen"
                    . " WHERE c_email = '$email'";
            return $this->db->executeSelect($select);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function forgotPanPassword($email) {
        try {
            $select = "SELECT * FROM panchayath"
                    . " WHERE pan_email = '$email'";
            return $this->db->executeSelect($select);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function update($citizenRid, $name, $password, $address, $mobileNo, $email) {
        try {
            $update = "UPDATE citizen SET"
                    . " c_name = '$name',"
                    . " c_contact = '$mobileNo',"
                    . " c_email = '$email',"
                    . " c_password = '$password',"
                    . " c_address = '$address'"
                    . " WHERE c_rid = '$citizenRid'";
            return $this->db->executeUpdate($update);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function getCitizenDetails($citizenRid) {
        try {
            $select = "SELECT * FROM citizen"
                    . " JOIN panchayath ON(c_grama = pan_rid)"
                    . " JOIN grama ON(pan_grama_rid = g_rid)"
                    . " WHERE c_rid = '$citizenRid' AND c_is_active = 1"
                    . " LIMIT 1";

            return $this->db->executeSelect($select);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function markAsComplete($complaintRid) {
        try {
            $update = "UPDATE complaints SET comp_is_confirmed_by_citizen = 1"
            . " WHERE comp_rid = $complaintRid";

            return $this->db->executeUpdate($update);
        } catch (Exception $ex) {
            throw $ex;
        }   
    }

    public function confirm($complaintRid, $images) {
        try {
            $update = "UPDATE complaints SET comp_status = 2, comp_solved_date = NOW()"
                    . " WHERE comp_rid = $complaintRid";

            $a = $this->db->executeUpdate($update);

            $sql = "INSERT INTO completed_images(ci_comp_rid, ci_img_url) VALUES";

            for ($i = 0; $i < count($images); $i++) {

                if ($i > 0) {
                    $sql .= ", ";
                }

                $sql .= "('$complaintRid', '$images[$i]')";
            }

            return $this->db->executeInsertAndGetId($sql);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function saveComplaintImages(DB $db, $compRid, $photos) {

        try {
            $sql = "INSERT INTO complaint_images(ci_comp_rid, ci_img_url)"
                    . " VALUES";

            for ($i = 0; $i < sizeof($photos); $i++) {

                if ($i > 0) {
                    $sql .= ", ";
                }

                $sql .= "('$compRid', '$photos[$i]')";
            }

            return $db->executeInsertAndGetId($sql);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

    public function getCompletedImages($complaintRid) {
        try {
            $sql = "SELECT ci_img_url FROM completed_images"
                    . " WHERE ci_comp_rid = $complaintRid";

            return $this->db->executeSelect($sql);
        } catch (Exception $ex) {
            throw $ex;
        }
    }

}
