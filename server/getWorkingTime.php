<?php
include "connect.php";
class DisplayJsonWorkingTime{
    function getAllJsonWorkingTime(){
        global $con;
        $jsonWorkingTime = array();
        $success="success";
        $message = "message";

        if (isset($_GET['id'])){
            $id = $_GET['id'];

            $sqlQuery;
            try{
                if ($id != -1) {
                    $sqlQuery = "SELECT * FROM working_time WHERE staff_id = '$id'";
                }else {
                    $sqlQuery = "SELECT * FROM working_time WHERE staff_id IS null";
                }

                $result = mysqli_query($con, $sqlQuery);

                foreach($result as $row){
                    array_push($jsonWorkingTime,
                        array('id'=>$row['id'], 
                            'name'=>$row['working_time_name'],
                            'weekdays'=>is_null($row['weekdays']) ? "" : $row['weekdays'],
                            'from_hour'=>$row['from_hour'],
                            'come_hour'=>$row['come_hour'],
                            'staff_id'=>$row['staff_id']));
                }
            }catch (PDOException $e){
                echo "Error while displaying json : " . $e->getMessage();
            }
        }

        if(!empty($jsonWorkingTime)){
            echo json_encode(
                array("working_time"=>$jsonWorkingTime,$success=>true,$message=>"Success"));
        }else{
            echo json_encode(
                array("working_time"=>null, $success=>false, 
                    $message=>"Failed while displaying data kind working_time"));
        }
    }
}

$json = new DisplayJsonWorkingTime();
$json->getAllJsonWorkingTime();