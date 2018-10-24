<?php
include "connect.php";
$query;

if ($_GET['id']) {
    $id = $_GET['id'];

    if ($id == '-1') {
        $query = "SELECT s.*, k.kind_name
            FROM staff as s LEFT JOIN kind_of_staff as k 
            ON s.kind_of_staff = k.id 
            WHERE s.status = '0'
            ORDER BY `s`.`id` ASC";
    }else {
        $query = "SELECT s.*, k.kind_name
            FROM staff as s LEFT JOIN kind_of_staff as k 
            ON s.kind_of_staff = k.id 
            WHERE s.staff_root = '$id' && s.status = '0'
            ORDER BY `s`.`id` ASC";
    }

    $data = mysqli_query($con, $query);
    $jsonStaff = array();

    while ($row = mysqli_fetch_assoc($data)){
       array_push($jsonStaff, new staff(
            $row['id'],
            $row['staff_name'],
            $row['email'],
            $row['password'],
            $row['address'],
            $row['phone_number'],
            $row['kind_of_staff'],
            $row['res_id'], 
            $row['staff_root'],
            $row['kind_name']));
    }

    result($jsonStaff);
}

mysqli_close($con);

function result($jsonStaff) {
    $success="success";
    $message = "message";

    if(!empty($jsonStaff)){
        echo json_encode(
            array("listStaff"=>$jsonStaff,$success=>true,$message=>"Success"));
    }else{
        echo json_encode(
            array("listStaff"=>null,$success=>false, $message=>"Failed while displaying data staff"));
    }
}

class staff{
    function staff($id, $name, $email, $pass, $address, 
        $phone, $kind_of_staff, $res_id, $staff_root, $kind_name){
        $this->id = $id;
        $this->staff_name = $name;
        $this->email = $email;
        $this->password = $pass;
        $this->address = $address;
        $this->phone_number = $phone;
        $this->kind_of_staff = $kind_of_staff;
        $this->res_id = $res_id;
        $this->staff_root = $staff_root;
        $this->kind_name = $kind_name;
    }
}
?>