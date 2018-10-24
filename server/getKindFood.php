<?php
include "connect.php";
class DisplayJsonKindFood{
    function getAllJsonKindFood(){
        global $con;

        $jsonKindFood = array();
        $status="status";
        $message = "message";

        try{
            $sqlQuery = "SELECT * FROM `kind_food` WHERE `status` = 0";

            $result = mysqli_query($con, $sqlQuery);

            foreach($result as $row){
                array_push($jsonKindFood,
                    array('idFood'=>$row['id'], 
                        'nameFoodKind'=>$row['kind_food_name']));
            }
        }catch (PDOException $e){
            echo "Error while displaying json : " . $e->getMessage();
        }

        if(!empty($jsonKindFood)){
            echo json_encode(array("kind_food"=>$jsonKindFood,$status=>1,$message=>"Success"));
        }else{
            echo json_encode(
                array("kind_food"=>null,$status=>0, $message=>"Failed while displaying data kind food"));
        }
    }
}

$json = new DisplayJsonKindFood();
$json->getAllJsonKindFood();