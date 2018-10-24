<?php
include "connect.php";

if (isset($_GET['name']) && 
    isset($_GET['idArea']) && 
    isset($_GET['id']) && 
    isset($_GET['is'])) {

    $table_name = $_GET['name'];
    $idArea = $_GET['idArea'];
    $is = $_GET['is'];
    $id = $_GET['id'];
    $result = -1;

    switch ($is) {
        case '0':
            $result = insertTable($table_name, $idArea);
            break;
        
        case '1':
            $result = updateTable($id, $table_name, $idArea);
            break;
            
        case '2':
            $result = deleteTable($id);
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

function insertTable($table_name, $idArea) {
    global $con;
    $data = "INSERT INTO `table_f`(`id`, `table_name`, `area_id`, `status`) 
    VALUES (null, '$table_name', '$idArea', 0)";

    return mysqli_query($con, $data) ? 3 : -1;
}

function updateTable($id, $table_name, $idArea) {
    global $con;

    $data = "
        UPDATE
            `table_f`
        SET
            `table_name` = '$table_name',
            `area_id` = '$idArea'
        WHERE
            `id` = '$id'
    ";
    return mysqli_query($con, $data) ? 1 : -1;
}

function deleteTable($id) {
    global $con;

    $query;
    $select_table_by_bill_not_payment = "
        SELECT
            *
        FROM
            `bill`
        WHERE
            `bill`.`table_id` = '$id' && `bill`.`status` = '0'
    ";
    $data1 = mysqli_query($con, $select_table_by_bill_not_payment);
    foreach ($data1 as $row) {
        if (!is_null($row)) {
            return 0;
        }
    }

    $select_table_by_bill_paymented = "
        SELECT
            *
        FROM
            `bill`
        WHERE
            `bill`.`table_id` = '$id' && `bill`.`status` = '1'
    ";

    $data2 = mysqli_query($con, $select_table_by_bill_paymented);
    foreach ($data2 as $row) {
        if (!is_null($row)) {
            $query = "UPDATE `table_f` SET `status` = '-99' WHERE `id` = '$id'";
            return mysqli_query($con, $query) ? 1 : -1;
        }
    }

    $query = "DELETE FROM `table_f` WHERE `id` = '$id'";
    return mysqli_query($con, $query) ? 2 : -1; 
}

?>