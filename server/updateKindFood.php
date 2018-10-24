<?php
include "connect.php";

if (isset($_GET['name']) && 
    isset($_GET['id']) && 
    isset($_GET['is'])) {

    $name = $_GET['name'];
    $is = $_GET['is'];
    $id = $_GET['id'];
    $result = -1;

    switch ($is) {
        case '0':
            $result = insertKindFood($name);
            break;
        
        case '1':
            $result = updateKindFood($id, $name);
            break;

        case '2':
            $result = deleteKindFood($id);
            break;
    }

    result($result);
}

mysqli_close($con);

function result($result) {
    $success="success";
    $message = "message";

    echo json_encode(array($success=>$result,$message=>"Success"));
}

function insertKindFood($name) {
    global $con;
    $data = "INSERT INTO `kind_food`(`id`, `kind_food_name`) 
    VALUES (null, '$name')";

    return mysqli_query($con, $data) ? 3 : -1;
}

function updateKindFood($id, $name) {
    global $con;
    $data = "UPDATE `kind_food` 
    SET `kind_food_name` = '$name' 
    WHERE `id` = '$id'";

    return mysqli_query($con, $data) ? 1 : -1;
}

function deleteKindFood($id) {
    global $con;

    $select_food = "SELECT * FROM `food` WHERE `kind_food` = '$id'";
    $result = mysqli_query($con, $select_food);

    $count_food = -1;
    foreach ($result as $row) {
        $food_id = $row['id'];
        $select_detail_by_bill_not_payment = "
            SELECT
                bd.*
            FROM
                `bill_details` AS bd
            LEFT JOIN `bill` ON bd.`bill_id` = `bill`.id
            WHERE
                bd.`food_id` = '$food_id' && `bill`.`status` = '0'
        ";

        $data1 = mysqli_query($con, $select_detail_by_bill_not_payment);
        foreach ($data1 as $row) {
            if (!is_null($row)) {
                return 0;
            }
        } 

        $select_detail_by_bill_paymented = "
            SELECT
                bd.*
            FROM
                `bill_details` AS bd
            LEFT JOIN `bill` ON bd.`bill_id` = `bill`.id
            WHERE
                bd.`food_id` = '$food_id' && `bill`.`status` = '1'
        ";

        $query;
        $data2 = mysqli_query($con, $select_detail_by_bill_paymented);
        foreach ($data2 as $row) {
            if (!is_null($row)) {
                $query = "UPDATE `food` SET `status` = '-99' WHERE `id` = '$food_id'";
                $count_food = 1;
                break;
            }
        }

        if ($count_food != 1) {
            $query = "DELETE FROM `food` WHERE `id` = '$food_id'";
        }

        mysqli_query($con, $query);
    }

    if ($count_food == 1) {
        $query = "UPDATE `kind_food` SET `status` = '-99' WHERE `id` = '$id'";
        return mysqli_query($con, $query) ? 1 : -1;
    }else {
        $query = "DELETE FROM `kind_food` WHERE `id` = '$id'";
        return mysqli_query($con, $query) ? 2 : -1;
    }
}

?>