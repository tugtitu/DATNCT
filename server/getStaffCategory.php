<?php
include "connect.php";
class DisplayJsonKindStaff{
    function getAllJsonKindStaff(){
        global $con;
        $jsonKindStaff = array();
        $success="success";
        $message = "message";

        try{
            $sqlQuery = "SELECT * FROM kind_of_staff";

            $result = mysqli_query($con, $sqlQuery);

            foreach($result as $row){
                array_push($jsonKindStaff,
                    array('id'=>$row['id'], 
                        'kind_name'=>$row['kind_name']));
            }
        }catch (PDOException $e){
            echo "Error while displaying json : " . $e->getMessage();
        }

        if(!empty($jsonKindStaff)){
            echo json_encode(
                array("staff_category"=>$jsonKindStaff,$success=>true,$message=>"Success"));
        }else{
            echo json_encode(
                array("staff_category"=>null, $success=>false, 
                    $message=>"Failed while displaying data kind staff"));
        }
    }
}

$json = new DisplayJsonKindStaff();
$json->getAllJsonKindStaff();