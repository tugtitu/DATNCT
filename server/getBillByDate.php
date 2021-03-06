<?php

include "connect.php";

if (isset($_GET['from_date']) && isset($_GET['to_date'])){
    $from_date = $_GET['from_date'];
    $to_date = $_GET['to_date'];

    $jsonBill = array();
    $success="success";
    $message = "message";

    try{
        $billData = getBillByDate($from_date, $to_date);

        foreach ($billData as $row_bill) {
            if (!is_null($row_bill)) {
                $infoData = getListDetailsByBill($row_bill['id']);
                $jsonDetails = array();

                foreach ($infoData as $row_details){
                   array_push($jsonDetails, 
                        new bill_details(
                            $row_details['id'],
                            $row_details['bill_id'],
                            $row_details['food_id'],
                            $row_details['quantity'],
                            is_null($row_details['note']) ? "" : $row_details['note'],
                            $row_details['food_name'],
                            $row_details['price'],
                            $row_details['promotions'],
                            $row_details['status']));
                }

                array_push($jsonBill, 
                    array('idBill'=>$row_bill['id'], 
                        'idTable'=>$row_bill['table_id'], 
                        'idStaff'=>$row_bill['staff_id'],
                        'total_price'=>$row_bill['total_price'],
                        'time_order'=>is_null($row_bill['time_order']) ? "" : $row_bill['time_order'],
                        'customer'=>is_null($row_bill['customer']) ? "" : $row_bill['customer'],
                        'guest_money'=>$row_bill['guest_money'],
                        'money_back'=>$row_bill['money_back'],
                        'discount'=>$row_bill['discount'],
                        'surcharge'=>$row_bill['surcharge'],
                        'ship'=>$row_bill['ship'],
                        'staff_name'=>$row_bill['staff_name'],
                        'bill_note'=>is_null($row_bill['bill_note']) ? "" : $row_bill['bill_note'],
                        'status'=>$row_bill['status'],
                        'bill_date'=>is_null($row_bill['bill_date']) ? "" : $row_bill['bill_date'],
                        'bill_details'=>$jsonDetails));
            }
        }
    }catch (PDOException $e){
        echo "Error while displaying json : " . $e->getMessage();
    }

    if(!empty($jsonBill)) {
        echo json_encode(array("json_bill"=>$jsonBill,$success=>true,$message=>"Success"));
    }else {
        echo json_encode(array("json_bill"=>null,$success=>false, $message=>"Failed while displaying data bill"));
    }
}

mysqli_close($con);

function getBillByDate($from_date, $to_date) {
    global $con;
    $query = "SELECT b.*, s.staff_name
        FROM
            `bill` AS b
        LEFT JOIN staff AS s
        ON
            b.`staff_id` = s.id 
        WHERE b.`status` = '1' AND `bill_date` BETWEEN '$from_date' AND '$to_date'
     ";

    return mysqli_query($con, $query);
}

function getListDetailsByBill($idBill) {
    global $con;
    $query = "
        SELECT bd.id, bd.bill_id, bd.food_id, bd.quantity, bd.note, bd.status, f.food_name, f.price, f.promotions 
        FROM bill_details as bd LEFT JOIN food as f ON bd.food_id = f.id 
        WHERE bd.bill_id = '$idBill'
    ";
    return mysqli_query($con, $query);
}


class bill_details{
    function bill_details($id, $bill_id, $food_id, $quantity, $note, $food_name, $price, $promotions, $status){
        $this->id = $id;
        $this->bill_id = $bill_id;
        $this->food_id = $food_id;
        $this->quantity = $quantity;
        $this->note = $note;
        $this->food_name = $food_name;
        $this->price = $price;
        $this->promotions = $promotions;
        $this->status = $status;
    }
}

?>