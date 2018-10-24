<?php
	include "connect.php";
    if (isset($_GET['id'])){
        $id_area = $_GET['id'];

        $data = getAllTableByArea($id_area);

        if($data === FALSE) { 
            die();
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
                    "",
                    $row['area_name']));
            }

            if(!empty($jsonTable)){
                echo json_encode(array("json_table"=>$jsonTable,$success=>true,$message=>"Success"));
            }else {
                echo json_encode(array("json_table"=>null,$success=>false, $message=>"Error"));
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

    function getAllTableByArea($idArea){
        global $con;
        $query = "SELECT DISTINCT
            t.id,
            t.table_name,
            t.area_id,
            t.time_booking,
            t.customer_phone,
            t.status,
            a.area AS 'area_name'
        FROM 
            `table_f` as t 
        LEFT JOIN 
            `area` as a
        ON 
            t.area_id = a.id 
        WHERE t.area_id = '$idArea' AND t.status = 0";

        return mysqli_query($con, $query);
    }
?>