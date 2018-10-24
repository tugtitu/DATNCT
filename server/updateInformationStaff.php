<?php
include "connect.php";

if (isset($_GET['is']) && 
    isset($_GET['id']) && 
    isset($_GET['staff_name']) && 
    isset($_GET['email']) && 
    isset($_GET['pass']) && 
    isset($_GET['phone']) &&
    isset($_GET['address']) &&
    isset($_GET['kind_of_staff']) &&
    isset($_GET['staff_root'])) {

    $is = $_GET['is'];
    $id = $_GET['id'];
    $name = $_GET['staff_name'];
    $email = $_GET['email'];
    $pass = $_GET['pass'];
    $phone = $_GET['phone'];
    $address = $_GET['address'];
    $kind_of_staff = $_GET['kind_of_staff'];
    $staff_root = $_GET['staff_root'];
    $isSuccess;

    switch ($is) {
        case '0':
            $isSuccess = changePassword($id, $pass);
            break;

        case '1':
            $isSuccess = 
                insertInfomationStaff($name, $email, $pass, 
                    $phone, $address, $kind_of_staff, $staff_root);
            break;

        case '2':
            $isSuccess = 
                updateInfomationStaff($id, $name, $email, 
                    $phone, $address, $kind_of_staff, $staff_root);
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

function changePassword($id, $pass) {
    global $con;
    $data = "UPDATE `staff` SET `password` = '$pass' WHERE `id` = '$id'";

    return mysqli_query($con, $data);
}

function insertInfomationStaff($name, $email, $pass, 
                    $phone, $address, $kind_of_staff, $staff_root) {
    global $con;
    $data = "INSERT INTO `staff`(
                `id`, `staff_name`, `email`, `password`, `kind_of_staff`, 
                `phone_number`, `address`, `res_id`, `staff_root`
            ) VALUES (
                null, '$name', '$email', '$pass', '$kind_of_staff',
                 '$phone', '$address', 1, '$staff_root'
            )";

    if (mysqli_query($con, $data)) {
        $idStaff = -1;
        $result = mysqli_query($con, "SELECT MAX(id) as 'id' FROM staff");
        foreach($result as $row) {
            $idStaff = $row['id'];
        }
        if ($idStaff != -1) {
            return updateWorkingTime($idStaff);
        }
    }

    return false;
}

function updateWorkingTime($idStaff) {
    global $con;
    $data = "UPDATE `working_time` 
    SET `staff_id` = '$idStaff'
    WHERE `staff_id` IS null";

    return mysqli_query($con, $data);
}

function updateInfomationStaff($id, $name, $email, 
    $phone, $address, $kind_of_staff, $staff_root) {
    global $con;
    $data = "UPDATE `staff` 
    SET `staff_name` = '$name', `email` = '$email', 
    `kind_of_staff` = '$kind_of_staff', 
    `phone_number` = '$phone', `address` = '$address', `staff_root` = '$staff_root'
    WHERE `id` = '$id'";

    return mysqli_query($con, $data);
}

?>