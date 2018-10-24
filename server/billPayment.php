<?php
include "connect.php";

class BillPayment{
    function paymentAndGetJsonBill(){
        global $con;
        $bill_id = $_GET['idBill'];
        $staff_id = $_GET['idStaff'];
        $table_id = $_GET['idTable'];
        $cus_name = $_GET['customer'];
        $current_total = $_GET['current_total'];
        $guest_money = $_GET['guest_money'];
        $money_back = $_GET['money_back'];
        $discount = $_GET['discount'];
        $surcharge = $_GET['surcharge'];
        $ship = $_GET['ship'];
        $note = $_GET['note'];
        $isSuccess = updateBill($bill_id, $staff_id, $table_id, $cus_name, $current_total, 
            $guest_money, $money_back, $discount, $surcharge, $ship, $note);

        if ($isSuccess) {
            $jsonBill = array();
            $success="success";
            $message = "message";

            try{
                $query = "SELECT
                    b.id,
                    b.table_id,
                    b.staff_id,
                    b.customer,
                    b.time_order,
                    b.bill_date,
                    b.total_price,
                    b.guest_money,
                    b.money_back,
                    b.discount,
                    b.surcharge,
                    b.ship,
                    b.bill_note,
                    b.status,
                    s.staff_name
                FROM
                    bill AS b
                LEFT JOIN staff AS s
                ON
                    b.staff_id = s.id
                WHERE
                    b.id = '$bill_id'";

                $billData = mysqli_query($con, $query);

                foreach ($billData as $row_bill) {
                    if (!is_null($row_bill)) {
                        $detailsData = getListDetailsByBill($row_bill['id']);
                        $jsonDetails = array();

                        foreach ($detailsData as $row_details){
                           array_push($jsonDetails, 
                                new bill_details(
                                    $row_details['id'],
                                    $row_details['bill_id'],
                                    $row_details['food_id'],
                                    $row_details['quantity'],
                                    is_null($row_details['note']) ? "" : $row_details['note'],
                                    $row_details['food_name'],
                                    $row_details['price'],
                                    $row_details['promotions']));
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
    }
}

$json = new BillPayment();
$json->paymentAndGetJsonBill();

mysqli_close($con);

function updateBill($bill_id, $staff_id, $table_id, $cus_name, $current_total, 
            $guest_money, $money_back, $discount, $surcharge, $ship, $note) {
    global $con;

    $insert = "
        UPDATE
            `bill`
        SET
            `staff_id` = '$staff_id', 
            `customer` = '$cus_name', 
            `bill_date` = CURRENT_TIMESTAMP(),
            `total_price` = '$current_total',
            `guest_money` = '$guest_money',
            `money_back` = '$money_back',
            `discount` = '$discount',
            `surcharge` = '$surcharge',
            `ship` = '$ship',
            `bill_note` = '$note',
            `status` = 1
        WHERE
            `id` = '$bill_id'
    ";

    if (mysqli_query($con, $insert)) {
        $update_bill = "UPDATE `bill` SET `time_order` = null WHERE `id` = '$bill_id'";
        $update_table = "UPDATE `table_f` SET `status` = 0 WHERE `id` = '$table_id'";
        return mysqli_query($con, $update_bill) ? mysqli_query($con, $update_table) : false;
    }

    return false;
}

function getListDetailsByBill($bill_id) {
    global $con;
    $query = "
        SELECT
            bd.`id`,
            bd.`bill_id`,
            bd.`food_id`,
            bd.`quantity`,
            bd.`note`,
            f.food_name,
            f.price,
            f.promotions
        FROM
            bill_details AS bd
        LEFT JOIN food AS f
        ON
            bd.food_id = f.id 
        WHERE bd.bill_id = '$bill_id'
    ";
    return mysqli_query($con, $query);
}

class bill_details{
    function bill_details($id, $bill_id, $food_id, $quantity, $note, $food_name, $price, $promotions){
        $this->id = $id;
        $this->bill_id = $bill_id;
        $this->food_id = $food_id;
        $this->quantity = $quantity;
        $this->note = $note;
        $this->food_name = $food_name;
        $this->price = $price;
        $this->promotions = $promotions;
    }
}

?>