<?php

include "connect.php";
if (isset($_GET['id'])){
    $id_area = $_GET['id'];

    $data;

    switch ($id_area) {
        case '-2': {
            $data = getAllTableByAreaNew();
            break;
        }

        case '-1': {
            $data = getAllTable();
            break;
        }

        case '0': {
            $data = getAllTableEmpty();
            break;
        }
        
        default: {
            $data = getAllTableByArea($id_area);
            break;
        }
    }
    if($data === FALSE) { 
        die(mysqli_error($mysqli));
    }else {
        $jsonTable = array();
        $success="success";
        $message = "message";
        foreach ($data as $row) {
           array_push($jsonTable, new table(
                $row['id'],
                $row['table_name'],
                $row['area_id'],
                is_null($row['time_booking']) ? "" : $row['time_booking'],
                is_null($row['customer_phone']) ? "" : $row['customer_phone'],
                $row['status'],
                is_null($row['time_order']) ? "" : (($row['status'] == 1 || $row['status'] == 3) ? $row['time_order'] : ""),
                $row['area_name']));
        }

        if(!empty($jsonTable)){
            echo json_encode(array("json_table"=>$jsonTable,$success=>true,$message=>"Success"));
        }else {
            echo json_encode(array("json_table"=>null,$success=>false, $message=>"Success"));
        }
    }
}

mysqli_close($con);

class table{
    function table($id, $name, $area_id, $time_booking, $customer_phone, $status, $time_order, $area_name){
        $this->id = $id;
        $this->table_name = $name;
        $this->area_id = $area_id;
        $this->time_booking = $time_booking;
        $this->customer_phone = $customer_phone;
        $this->status = $status;
        $this->time_order = $time_order;
        $this->area_name = $area_name;
    }
}

function getAllTable(){
    global $con;
    $query = " 
        SELECT DISTINCT
            t.id,
            t.table_name,
            t.area_id,
            t.time_booking,
            t.customer_phone,
            t.status,
            b.time_order,
            AREA.area AS 'area_name'
        FROM
            table_f AS t
        LEFT JOIN bill AS b
        ON
            t.id = b.table_id
        LEFT JOIN AREA ON t.area_id = AREA.id
        WHERE
            (t.status = 0 || t.status = 2) && b.time_order IS NULL || 
            (t.status = 1 || t.status = 3) && b.time_order IS NOT NULL
        ORDER BY
            t.id ASC
    ";
    return mysqli_query($con, $query);
}

function getAllTableEmpty(){
    global $con;
    $query = "
        SELECT DISTINCT
            t.id,
            t.table_name,
            t.area_id,
            t.time_booking,
            t.customer_phone,
            t.status,
            b.time_order,
            AREA.area AS 'area_name'
        FROM
            table_f AS t
        LEFT JOIN bill AS b
        ON
            t.id = b.table_id
        LEFT JOIN AREA ON t.area_id = AREA.id
        WHERE
            t.status = 0
        ORDER BY
            t.id ASC
    ";
    return mysqli_query($con, $query);
}

function getAllTableByArea($idArea){
    global $con;
    $query = " 
        SELECT DISTINCT
            t.id,
            t.table_name,
            t.area_id,
            t.time_booking,
            t.customer_phone,
            t.status,
            b.time_order,
            AREA.area AS 'area_name'
        FROM
            table_f AS t
        LEFT JOIN bill AS b
        ON
            t.id = b.table_id
        LEFT JOIN AREA ON t.area_id = AREA.id
        WHERE
            t.area_id = '$idArea' && (
                (t.status = 0 || t.status = 2) && b.time_order IS NULL || 
                (t.status = 1 || t.status = 3) && b.time_order IS NOT NULL
            )
        ORDER BY
            t.id ASC
    ";
    return mysqli_query($con, $query);
}

function getAllTableByAreaNew(){
    global $con;
    $query = " SELECT * FROM `table_f` WHERE `area_id` = (SELECT MAX(id) FROM `area`)";

    return mysqli_query($con, $query);
}

?>