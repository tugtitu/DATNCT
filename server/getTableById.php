<?php

include "connect.php";
if (isset($_GET['idT'])){
    $idTable = $_GET['idT'];

    $data = getTableById($idTable);
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

function getTableById($idTable){
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
            t.id = '$idTable' && (t.status = 1) && b.time_order IS NOT NULL)
    ";
    return mysqli_query($con, $query);
}

?>