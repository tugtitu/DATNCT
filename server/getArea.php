<?php
include "connect.php";
class DisplayJsonArea{
    function getJsonArea(){
        global $con;
        $jsonArea = array();
        $success="success";
        $message = "message";

        try{
            $sqlQuery = "SELECT * FROM area WHERE status = '0'";

            $result = mysqli_query($con, $sqlQuery);

            foreach($result as $row){
                array_push($jsonArea,
                    array('idArea'=>$row['id'], 
                        'nameArea'=>$row['area']));
            }
        }catch (PDOException $e){
            echo "Error while displaying json : " . $e->getMessage();
        }

        if(!empty($jsonArea)){
            echo json_encode(array("json_area"=>$jsonArea,$success=>true,$message=>"Success"));
        }else{
            echo json_encode(array("json_area"=>null,$success=>false, $message=>"Error!"));
        }
    }
}

$json = new DisplayJsonArea();
$json->getJsonArea();