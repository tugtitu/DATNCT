<?php
include "connect.php";

if (isset($_GET['is']) && 
    isset($_GET['id']) && 
    isset($_GET['idStaff']) && 
    isset($_GET['name']) && 
    isset($_GET['weekdays']) && 
    isset($_GET['from_hour']) && 
    isset($_GET['come_hour'])) {

    $is = $_GET['is'];
    $id = $_GET['id'];
    $idStaff = $_GET['idStaff'];
    $name = $_GET['name'];
    $weekdays = $_GET['weekdays'];
    $from_hour = $_GET['from_hour'];
    $come_hour = $_GET['come_hour'];
    $isSuccess;

    switch ($is) {
        case '1':
            $isSuccess = insertWorkingTime($name, $weekdays, $from_hour, $come_hour, $idStaff);
            break;

        case '2':
            $isSuccess = updateWorkingTime($id, $name, $weekdays, $from_hour, $come_hour);
            break;
    }

    result($isSuccess);
}

mysqli_close($con);

function result($isSuccess) {
    $success="success";
    $message = "message";

    if ($isSuccess) {
        echo json_encode(
            array($success=>true,$message=>"Success"));
    }else {
        echo json_encode(
            array($success=>false, $message=>"Error!"));
    }
}

function insertWorkingTime($name, $weekdays, $from_hour, $come_hour, $idStaff) {
    global $con;
    $data = "
        INSERT INTO `working_time`(
            `id`,
            `working_time_name`,
            `weekdays`,
            `from_hour`,
            `come_hour`,
            `staff_id`)
        VALUES(
            NULL,
            '$name',
            '$weekdays',
            '$from_hour',
            '$come_hour',
            null)
    ";

    return mysqli_query($con, $data);
}

function updateWorkingTime($id, $name, $weekdays, $from_hour, $come_hour) {
    global $con;
    $data = "
        UPDATE
            `working_time`
        SET
            `working_time_name` = '$name',
            `weekdays` = '$weekdays',
            `from_hour` = '$from_hour',
            `come_hour` = '$come_hour'
        WHERE
            `id` = '$id'
    ";

    return mysqli_query($con, $data);
}

?>