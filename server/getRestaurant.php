<?php
include "connect.php";
class DisplayJsonRestaurant{
    function getAllJsonRestaurant(){
        global $con;
        if (isset($_GET['idRes'])){
        $res_id = $_GET['idRes'];
            $jsonRestaurant = array();
            $success ="success";
            $message = "message";

            try{
                $query = "SELECT * FROM restaurant WHERE id = '$res_id'";
                $result = mysqli_query($con, $query);

                foreach ($result as $row){
                   array_push($jsonRestaurant, new restaurant(
                        $row['id'],
                        $row['res_name'],
                        $row['res_address'],
                        $row['res_describe'],
                        $row['phone']));
                }
            }catch (PDOException $e){
                echo "Error while displaying json : " . $e->getMessage();
            }

            if(!empty($jsonRestaurant)){
                echo json_encode(
                    array("restaurant"=>$jsonRestaurant,$success=>true,$message=>"Success"));
            }else{
                echo json_encode(
                    array("restaurant"=>null,$success=>false, $message=>"Failed while displaying data area"));
            }
        }
    }
}

$json = new DisplayJsonRestaurant();
$json->getAllJsonRestaurant();

mysqli_close($con);

class restaurant{
    function restaurant($id, $res_name, $res_address, $res_describe, $phone){
        $this->id = $id;
        $this->res_name = $res_name;
        $this->res_address = $res_address;
        $this->res_describe = $res_describe;
        $this->phone = $phone;
    }
}
?>