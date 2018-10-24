<?php
include "connect.php";

if (isset($_GET['idTable'])) {
	$id = $_GET['idTable'];
	$status = $_GET['status'];
	$time = $_GET['time'];
	$success = "success";
    $message = "message";
	$query;

	switch ($status == 2) {
		case '0':
			$query = "UPDATE table_f SET status = '$status', time_booking = null, customer_phone = null WHERE id = '$id'";
			break;

		case '2':
			$phone = $_GET['phone'];
			$query = "UPDATE table_f SET status = '$status', time_booking = '$time', customer_phone = '$phone' WHERE id = '$id'";
			break;
		
		default:
			$query = "UPDATE table_f SET status = '$status' WHERE id = '$id'";
			break;
	}

	if ($status == 2) {
	 	
	}else {
		
	}

	
	if (mysqli_query($con, $query)) {
        echo json_encode(array($success=>true, $message=>"Success"));
	}else {
		echo json_encode(array($success=>false, $message=>"Error"));
	}
}

mysqli_close($con);

?>