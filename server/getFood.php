<?php
	include "connect.php";
    $id_food;
    if (isset($_GET['id'])){
        $id_food = $_GET['id'];

        $data;

        switch ($id_food) {
            case '-1': {
                $data = getAllFood();
                break;
            }
            
            default: {
                $data = getAllFoodByKindFood($id_food);
                break;
            }
        }
        if($data === FALSE) { 
            yourErrorHandler(mysqli_error($mysqli));
        }else {
            $jsonFood = array();
            $status="status";
            $message = "message";
            while ($row = mysqli_fetch_assoc($data)){
               array_push($jsonFood, new food(
                    $row['id'],
                    $row['food_name'],
                    $row['kind_food'],
                    $row['quantity'],
                    $row['price'],
                    $row['promotions'],
                    $row['kind_food_name']));
            }

            if(!empty($jsonFood)){
                echo json_encode(array("food"=>$jsonFood,$status=>1,$message=>"Success"));
            }else{
                echo json_encode(array("food"=>null,$status=>0, $message=>"Failed while displaying data food"));
            }
        }
    }
    
    mysqli_close($con);

    class food{
        function food($id, $food_name, $kind_food, $quantity, $price, $promotions, $kind_food_name){
            $this->id = $id;
            $this->food_name = $food_name;
            $this->kind_food = $kind_food;
            $this->quantity = $quantity;
            $this->price = $price;
            $this->promotions = $promotions;
            $this->kind_food_name = $kind_food_name;
        }
    }

    function getAllFood(){
        global $con;
        $query = "SELECT f.*, k.kind_food_name 
        FROM food as f LEFT JOIN kind_food as k ON f.kind_food = k.id
        WHERE f.status = 0";
        return mysqli_query($con, $query);
    }

    function getAllFoodByKindFood($idK){
        global $con;
        $query = "SELECT f.*, k.kind_food_name 
        FROM food as f LEFT JOIN kind_food as k ON f.kind_food = k.id 
        WHERE f.kind_food = '$idK' AND f.status = 0";
        return mysqli_query($con, $query);
    }
?>