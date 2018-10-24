<?php
include "connect.php";

if (isset($_GET['id'])) {
    $id = $_GET['id'];
    $result = -1;

    $result = deleteStaff($id);

    result($result);
}

mysqli_close($con);

function result($result) {
    $success="success";
    $message = "message";

    echo json_encode(array($success=>$result,$message=>"Success"));
}

function deleteStaff($id) {
    global $con;

    $query;
    $select_staff_by_bill_not_payment = "
        SELECT
            *
        FROM
            `bill`
        WHERE
            `bill`.`staff_id` = '$id' && `bill`.`status` = '0'
    ";

    $data1 = mysqli_query($con, $select_staff_by_bill_not_payment);
    foreach ($data1 as $row) {
        if (!is_null($row)) {
            return 0;
        }
    }

    $select_staff_by_bill_paymented = "SELECT * FROM `bill` WHERE `bill`.`staff_id` = '$id' && `bill`.`status` = '1'";

    $data2 = mysqli_query($con, $select_staff_by_bill_paymented);
    foreach ($data2 as $row) {
        if (!is_null($row)) {
            $update_staff = "UPDATE `staff` SET `status` = '-99' WHERE `id` = '$id'";
            return mysqli_query($con, $update_staff) ? 1 : -1;
        }
    }
    
    $delete_wkt = "DELETE FROM `working_time` WHERE `staff_id` = '$id'";
    $delete_staff = "DELETE FROM `staff` WHERE `id` = '$id'";

    return mysqli_query($con, $delete_wkt) ? (mysqli_query($con, $delete_staff) ? 2 : -1) : -1;
}

?>