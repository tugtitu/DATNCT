<?php
include "connect.php";

if (isset($_GET['food_name']) && 
    isset($_GET['promotions']) && 
    isset($_GET['price']) && 
    isset($_GET['kind_food']) && 
    isset($_GET['id']) && 
    isset($_GET['is'])) {

    $food_name = $_GET['food_name'];
    $promotions = $_GET['promotions'];
    $price = $_GET['price'];
    $kind_food = $_GET['kind_food'];
    $is = $_GET['is'];
    $id = $_GET['id'];
    $result = -1;

    switch ($is) {
        case '0':
            $result = insertFood($food_name, $promotions, $price, $kind_food);
            break;
        
        case '1':
            $result = updateFood($id, $food_name, $promotions, $price, $kind_food);
            break;
            
        case '2':
            $result = deleteFood($id);
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

function insertFood($food_name, $promotions, $price, $kind_food) {
    global $con;
    $data = "
        INSERT INTO `food`(
            `id`,
            `food_name`,
            `kind_food`,
            `quantity`,
            `price`,
            `promotions`)
        VALUES(
            NULL,
            '$food_name',
            '$kind_food',
            NULL,
            '$price',
            '$promotions')
    ";

    return mysqli_query($con, $data) ? 3 : -1;
}

function updateFood($id, $food_name, $promotions, $price, $kind_food) {
    global $con;

    $select_detail_by_bill_not_payment = "
        SELECT
            bd.*
        FROM
            `bill_details` AS bd
        LEFT JOIN `bill` ON bd.`bill_id` = `bill`.id
        WHERE
            bd.`food_id` = '$id' && `bill`.`status` = 0
    ";

    $data = mysqli_query($con, $select_detail_by_bill_not_payment);
    foreach ($data as $row) {
        if (!is_null($row)) {
            return 0;
        }
    }

    $data = "
        UPDATE
            `food`
        SET
            `food_name` = '$food_name',
            `kind_food` = '$kind_food',
            `quantity` = NULL,
            `price` = '$price',
            `promotions` = '$promotions'
        WHERE
            `id` = '$id'
    ";
    return mysqli_query($con, $data) ? 1 : -1;
}

function deleteFood($id) {
    global $con;

    $query;
    $select_detail_by_bill_not_payment = "
        SELECT
            bd.*
        FROM
            `bill_details` AS bd
        LEFT JOIN `bill` ON bd.`bill_id` = `bill`.id
        WHERE
            bd.`food_id` = '$id' && `bill`.`status` = '0'
    ";

    $data1 = mysqli_query($con, $select_detail_by_bill_not_payment);
    foreach ($data1 as $row) {
        if (!is_null($row)) {
            return 0;
        }
    }

    $select_all_detail = "
        SELECT
            bd.*
        FROM
            `bill_details` AS bd
        LEFT JOIN `bill` ON bd.`bill_id` = `bill`.id
        WHERE
            bd.`food_id` = '$id' && `bill`.`status` = '1'
    ";

    $data2 = mysqli_query($con, $select_all_detail);
    foreach ($data2 as $row) {
        if (!is_null($row)) {
            $query = "UPDATE `food` SET `status` = '-99' WHERE `id` = '$id'";
            return mysqli_query($con, $query) ? 1 : -1;
        }
    }

    $query = "DELETE FROM `food` WHERE `id` = '$id'";
    return mysqli_query($con, $query) ? 2 : -1;
}

?>