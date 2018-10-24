<?php
	include "connect.php";
    $isSuccess;
    if (isset($_GET['idBill']) && isset($_GET['idTable']) && isset($_GET['is'])) {
        $bill_id = $_GET['idBill'];
        $table_id = $_GET['idTable'];
        $is = $_GET['is'];
        $bill_details_id = -1;
        $query = "";

        $query = "SELECT * FROM bill_details WHERE bill_id = '$bill_id'"; 

        $data = mysqli_query($con, $query);

        if ($data === FALSE) {
            yourErrorHandler(mysqli_error($mysqli));
        }else {
            foreach ($data as $row) {
                $bill_details_id = $row['id'];
                $isSuccess = deleteDetail($bill_details_id);
            }

            if ($is == 2) { $isSuccess = (deleteBill($bill_id) ? (updateTable($table_id) ? true : false) : false); }
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

    function updateTable($idTable) {
        global $con;
        return mysqli_query($con, "UPDATE table_f SET status = 0 WHERE id = '$idTable'") ? true : false;
    }

    function deleteBill($idBill) {
        global $con;
        return mysqli_query($con, "DELETE FROM `bill` WHERE id = '$idBill'") ? true : false;
    }

    function deleteDetail($details_id) {
        global $con;
        return mysqli_query($con, "DELETE FROM `bill_details` WHERE id = '$details_id'") ? true : false;
    }
    
    function updateStatusTable($table_id, $status) {
    	global $con;

	    $query = "UPDATE table_f SET status = 1 WHERE id = '$table_id'";

		return mysqli_query($con, $query) ? true : false;
    }
?>