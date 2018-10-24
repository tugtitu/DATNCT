<?php
include "connect.php";

if (isset($_GET['idD']) && isset($_GET['idB']) &&isset($_GET['status'])) {
	$idD = $_GET['idD'];
	$idB = $_GET['idB'];
	$status = $_GET['status'];

	$success = "success";
    $message = "message";
	$update_detail = "UPDATE `bill_details` SET `status` = '$status' WHERE `id` = '$idD'";

	if (mysqli_query($con, $update_detail)) {
        $select_table_id = "SELECT `table_id` FROM `bill` WHERE `id` = '$idB' && `status` = '0'";
        $data_table = mysqli_query($con, $select_table_id);
        $idT = -1;
        foreach ($data_table as $row) {
        	if (!is_null($row)) {
        		$idT = $row['table_id'];
        	}
        }

        if ($idT != -1) {
        	$sum_row = 0;
        	$update_table;
        	$total_number_of_lines_is_one = 0;
        	$select_detail_by_bill_id = "
        		SELECT
				    SUM(`status` = 1) AS 'total_number_of_lines_is_one',
				    SUM(`status` = 0) + SUM(`status` = 1) AS 'sum_row'
				FROM
				    `bill_details`
				WHERE `bill_id` = '$idB'
			";

        	$data_detail = mysqli_query($con, $select_detail_by_bill_id);
        	foreach ($data_detail as $row) {
	        	if (!is_null($row)) {
	        		$sum_row = $row['sum_row'];
	        		$total_number_of_lines_is_one = $row['total_number_of_lines_is_one'];
	        	}
	        }

	        if ($sum_row != 0) {
	        	if ($total_number_of_lines_is_one == $sum_row) {
	        		$update_table = "UPDATE `table_f` SET `status`= '3' WHERE `id` = '$idT'";
	        	}else {
	        		$update_table = "UPDATE `table_f` SET `status`= '1' WHERE `id` = '$idT'";
	        	}

	        	if (mysqli_query($con, $update_table)) {
			        echo json_encode(array($success=>true, $message=>"Success"));
				}else {
					echo json_encode(array($success=>false, $message=>"Error"));
				}
	        }
        }
	}
}

mysqli_close($con);

?>