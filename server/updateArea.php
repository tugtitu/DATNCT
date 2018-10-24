<?php
include "connect.php";

if (isset($_GET['name']) && 
    isset($_GET['id']) && 
    isset($_GET['is'])) {

    $area_name = $_GET['name'];
    $is = $_GET['is'];
    $id = $_GET['id'];
    $result = -1;;

    switch ($is) {
        case '0':
            $result = insertArea($area_name);
            break;
        
        case '1':
            $result = updateArea($id, $area_name);
            break;
            
        case '2':
            $result = deleteArea($id);
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

function insertArea($area_name) {
    global $con;
    $data = "INSERT INTO `area`(`id`, `area`) 
    VALUES (null, '$area_name')";

    return mysqli_query($con, $data) ? 3 : -1;
}

function updateArea($id, $area_name) {
    global $con;
    $data = "UPDATE `area` 
    SET `area` = '$area_name' WHERE `id`='$id'";

    return mysqli_query($con, $data) ? 1 : -1;
}

function deleteArea($id) {
    global $con;

    $select_table = "SELECT * FROM `table_f` WHERE `area_id` = '$id'";
    $result = mysqli_query($con, $select_table);

    $count_food = -1;
    foreach ($result as $row) {
        $table_id = $row['id'];
        $select_bill_by_status_0 = "
            SELECT
                *
            FROM
                `bill`
            WHERE
                `bill`.`table_id` = '$table_id' && `bill`.`status` = '0'
        ";

        $data1 = mysqli_query($con, $select_bill_by_status_0);
        foreach ($data1 as $row) {
            if (!is_null($row)) {
                return 0;
            }
        }

        $select_bill_by_status_1 = "
            SELECT
                *
            FROM
                `bill`
            WHERE
                `bill`.`table_id` = '$table_id' && `bill`.`status` = '1'
        ";

        $query;
        $data2 = mysqli_query($con, $select_bill_by_status_1);
        foreach ($data2 as $row) {
            if (!is_null($row)) {
                $query = "UPDATE `table_f` SET `status` = '-99' WHERE `id` = '$table_id'";
                $count_food = 1;
                break;
            }
        }

        if ($count_food != 1) {
            $query = "DELETE FROM `table_f` WHERE `id` = '$table_id'";
        }

        mysqli_query($con, $query);
    }

    if ($count_food == 1) {
        $query = "UPDATE `area` SET `status` = '-99' WHERE `id` = '$id'";
        return mysqli_query($con, $query) ? 1 : -1;
    }else {
        $query = "DELETE FROM `area` WHERE `id` = '$id'";
        return mysqli_query($con, $query) ? 2 : -1;
    }

}

?>