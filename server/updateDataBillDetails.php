<?php
include "connect.php";

$isSuccess;
if (isset($_GET['idBill']) && 
    isset($_GET['idTable']) && 
    isset($_GET['idStaff']) && 
    isset($_GET['idFood']) && 
    isset($_GET['idInfo']) && 
    isset($_GET['isPlus']) && 
    isset($_GET['note'])) {

    $bill_id = $_GET['idBill'];
    $table_id = $_GET['idTable'];
    $staff_id = $_GET['idStaff'];
    $food_id = $_GET['idFood'];
    $note = $_GET['note'];
    $isPlus = $_GET['isPlus'];
    $idBillDetails = $_GET['idInfo'];
    $quantity = 0;

    if ($idBillDetails != -1) {
        $query = "SELECT * FROM `bill_details` AS bd WHERE bd.id = '$idBillDetails'";
        $data = mysqli_query($con, $query);
        if($data === FALSE) { 
            yourErrorHandler(mysqli_error($mysqli));

        }else { foreach ($data as $row){ if(!is_null($row)){ $quantity = $row['quantity']; } } }

        if ($isPlus == -1 && $quantity == 1) { $isPlus = 0; }
        $isSuccess = updateBillDetails($note, $quantity, $bill_id, $idBillDetails, $isPlus);

    }else {
        if ($bill_id != -1) {
            $query = "SELECT * FROM `bill_details` AS bd WHERE bd.bill_id = '$bill_id' AND bd.food_id = '$food_id'";
            $data = mysqli_query($con, $query);
            if($data === FALSE) { 
                yourErrorHandler(mysqli_error($mysqli));
            }else {
                foreach ($data as $row){
                   if(!is_null($row)){
                        $quantity = $row['quantity'];
                        $idBillDetails = $row['id'];
                    }
                }
            }
            if ($idBillDetails != -1) {
                if ($isPlus == -1 && $quantity == 1 && $isPlus != 0) { $isPlus = 0; }
                $isSuccess = updateBillDetails($note, $quantity, $bill_id, $idBillDetails, $isPlus);

            } else { $isSuccess = insertBillDetails($bill_id, $food_id); }

        }else {
            $query = "
                INSERT INTO `bill`(`id`, `table_id`, `staff_id`, `customer`, `time_order`, `bill_date`, `total_price`, `guest_money`, `money_back`, `discount`, `surcharge`, `ship`, `bill_note`, `status`) 
                VALUES(null, '$table_id', '$staff_id', null, CURRENT_TIMESTAMP(), null, 0, 0, 0, 0, 0, 0, null, 0)
            ";

            if (mysqli_query($con, $query)) {
                $data = mysqli_query($con, "SELECT MAX(id) AS id FROM `bill`");
                if($data === FALSE) { 
                    die(mysqli_error($mysqli));
                } else { 
                    foreach ($data as $row){
                       if(!is_null($row)){
                            $bill_id = $row['id'];
                            mysqli_query($con, "UPDATE table_f SET status = 1 WHERE id = '$table_id'");

                            $isSuccess = insertBillDetails($bill_id, $food_id); 
                        }
                    }
                }
            }
        }
    }

}else { $isSuccess = false; }

result($isSuccess);

mysqli_close($con);

function result($isSuccess) {
    $success="success";
    $message = "message";

    if ($isSuccess) {
        echo json_encode(array($success=>true,$message=>"Success"));
    }else {
        echo json_encode(array($success=>false,$message=>"Data processing failed"));
    }
}

function updateBill($bill_id) {
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
    return mysqli_query($con, $query) ? true : false;
}

function updateBillDetails($note, $quantity, $bill_id, $idBillDetails, $isPlus){
    global $con;
    switch ($isPlus) {
        case '-9999': {
            if ($note != '' && strlen($note) > 0) {
                $query = "
                    UPDATE `bill_details` 
                    SET note = '$note' 
                    WHERE id = '$idBillDetails'
                ";
                return mysqli_query($con, $query) ? true : false;
            }
            break;
        }
        
        case '0': {
            $query = "DELETE FROM `bill_details` WHERE id = '$idBillDetails'";
            return mysqli_query($con, $query) ? (updateBill($bill_id) ? true : false) : false;
            break;
        }

        default: {
            $query = "
                UPDATE `bill_details` 
                SET quantity = '$quantity' + '$isPlus' 
                WHERE id = '$idBillDetails'
            ";
            return mysqli_query($con, $query) ? (updateBill($bill_id) ? true : false) : false;
            break;
        }
    }
}

function insertBillDetails($bill_id, $food_id){
    global $con;
    $query = "
        INSERT INTO bill_details(
            id, 
            bill_id, 
            food_id, 
            quantity, 
            note
        ) 
        VALUES(
            null , 
            '$bill_id', 
            '$food_id', 
            '1', 
            null
        )
    ";
    if (mysqli_query($con, $query)) {
        return updateBill($bill_id) ? true : false;
    } else {
        return false;
    }
}
?>