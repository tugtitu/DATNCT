<?php

include "connect.php";

if (isset($_GET['id1']) && isset($_GET['id2'])) {
    $table1 = $_GET['id1'];
    $table2 = $_GET['id2'];

    $idFirstBill = -1; // idOrder của bàn đầu tiên
    $idSeconrdBill = -1; // idOrder của bàn thứ 2
    $isFirstStatus = -1; // status của bàn đầu tiên
    $isSecondStatus = -1; // status của bàn thứ 2

    $data = mysqli_query($con, "SELECT id FROM bill WHERE table_id = '$table2' AND status = 0");
    while ($row = mysqli_fetch_assoc($data)){
       $idSeconrdBill = $row['id'];
    }
    $data = mysqli_query($con, "SELECT id FROM bill WHERE table_id = '$table1' AND status = 0");
    while ($row = mysqli_fetch_assoc($data)){
       $idFirstBill = $row['id'];
    }

    if ($idFirstBill != -1 || $idSeconrdBill != -1) {
        $data = mysqli_query($con, "SELECT status FROM table_f WHERE id = '$table1'");
        while ($row = mysqli_fetch_assoc($data)){
           $isFirstStatus = $row['status'];
        }
        
        $data = mysqli_query($con, "SELECT status FROM table_f WHERE id = '$table2'");
        while ($row = mysqli_fetch_assoc($data)){
           $isSecondStatus = $row['status'];
        }
    }

    $query1 = "UPDATE bill SET table_id = '$table2' WHERE id = '$idFirstBill' AND status = 0";
    $query2 = "UPDATE bill SET table_id = '$table1' WHERE id = '$idSeconrdBill' AND status = 0";
    
    $success ="success";
    $message = "message";

    if (mysqli_query($con, $query1) && mysqli_query($con, $query2)) {
        echo json_encode(array($success=>true,$message=>"Success"));
    }else {
        echo json_encode(array($success=>false,$message=>"Error!"));
    }
    

    if ($isFirstStatus != -1 && $isSecondStatus != -1) {
        mysqli_query($con, "UPDATE table_f SET status = '$isFirstStatus' WHERE id = '$table2'");
        mysqli_query($con, "UPDATE table_f SET status = '$isSecondStatus' WHERE id = '$table1'");
    }
}

mysqli_close($con);

?>