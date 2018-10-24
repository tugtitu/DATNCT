<?php

include "connect.php";

if (isset($_GET['id1']) && isset($_GET['id2'])) {
    $table1 = $_GET['id1'];
    $table2 = $_GET['id2'];

    $idFirstBill = -1; // idOrder của bàn đầu tiên
    $idSeconrdBill = -1; // idOrder của bàn thứ 2
    
    $idSeconrdBill = selectIdBillByTable($table2);
    $idFirstBill = selectIdBillByTable($table1);

    if ($idFirstBill != -1 && $idSeconrdBill != -1) {
        $data1 = mysqli_query($con, "SELECT * FROM `bill_details` WHERE `bill_id` = '$idFirstBill'");
        $data2 = mysqli_query($con, "SELECT * FROM `bill_details` WHERE `bill_id` = '$idSeconrdBill'");

        foreach ($data1 as $row1){
            $isExisting = 0;
            foreach ($data2 as $row2){
                if ($row1['food_id'] == $row2['food_id'] && $row1['note'] == $row2['note']) {
                    //update
                    $quantity = $row1['quantity'] + $row2['quantity'];
                    $details_id1 = $row1['id'];
                    $details_id2 = $row2['id'];
                    $query = "
                        UPDATE bill_details 
                        SET quantity = '$quantity' 
                        WHERE id = '$details_id2'
                    ";
                    mysqli_query($con, $query);
                    mysqli_query($con, "DELETE FROM `bill_details` WHERE id = '$details_id1'");
                    $isExisting = 1;
                    break;
                }
            }

            if ($isExisting == 0) {
                $food_id = $row1['food_id'];
                $quantity = $row1['quantity'];
                $note = null;
                if (!is_null($row1['note'])) {
                    $note = $row1['note'];
                }
                
                updateBillDetails($idSeconrdBill, $row1['id']);
            }
        }

        $success="success";
        $message = "message";

        if (deleteBillById($idFirstBill, $table1)) {
            echo json_encode(array($success=>true,$message=>"Success"));
        }else {
            echo json_encode(array($success=>false,$message=>"Error"));
        }
    }
}

function deleteBillById($idBill, $idTable){
    global $con;

    $delete_bill = "DELETE FROM `bill` WHERE `id` = '$idBill' AND `status` = 0";
    $update_table = "UPDATE `table_f` SET `status` = 0 WHERE `id` = '$idTable'";

    return mysqli_query($con, $delete_bill) ? mysqli_query($con, $update_table) : false;
}

function selectIdBillByTable($idTable){
    global $con;
    $data = mysqli_query($con, "SELECT `id` FROM `bill` WHERE `table_id` = '$idTable' AND `status` = 0");
    if ($row = mysqli_fetch_assoc($data)){
        return $row['id'];
    }
    return -1;
}

function updateBillDetails($bill_id, $bd_id){
    global $con;
    $query = "UPDATE bill_details SET bill_id = '$bill_id' WHERE id = '$bd_id'";

    return mysqli_query($con, $query) ? updateBill($bill_id) : false;
}

function updateBill($bill_id){
    global $con;
    $query = "
        UPDATE bill 
        SET total_price = (
            SELECT SUM(bd.quantity * f.price) 
            FROM bill_details AS bd, food AS f 
            WHERE bd.bill_id = '$bill_id' 
            AND bd.food_id = f.id
        ) 
        WHERE id = '$bill_id'
        AND status = 0
    ";
    return mysqli_query($con, $query);
}

mysqli_close($con);

?>