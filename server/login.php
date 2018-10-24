<?php
include "connect.php";

if (isset($_GET['email']) && isset($_GET['pass'])) {

    $email = $_GET['email'];
    $pass = $_GET['pass'];
    $jsonStaff = array();
    $isSuccess;

    $result = login($email, $pass);
    $count = mysqli_num_rows($result);
    // nếu tài khoản trùng khớp thì sẽ trả về giá trị 1 cho biến $count
    if($count==1){
        foreach ($result as $row) {
            array_push($jsonStaff, new staff(
                $row['id'],
                $row['staff_name'],
                $row['email'],
                $row['password'],
                $row['address'],
                $row['phone_number'],
                $row['kind_of_staff'],
                $row['res_id'], 
                $row['staff_root'],
                $row['kind_name']));
        }
    }

    result($jsonStaff);
}

mysqli_close($con);

function result($jsonStaff) {
    $success="success";
    $message = "message";

    if(!empty($jsonStaff)){
        echo json_encode(
            array("listStaff"=>$jsonStaff,$success=>true,$message=>"Success"));
    }else{
        echo json_encode(
            array("listStaff"=>null,$success=>false, $message=>"Failed while displaying data staff"));
    }
}

function login($myusername, $mypassword) {
    global $con;

    // Xử lý để tránh MySQL injection
    $myusername = stripslashes($myusername);
    $mypassword = stripslashes($mypassword);
     
    $sql="SELECT s.*, k.kind_name FROM staff as s 
    LEFT JOIN kind_of_staff as k ON s.kind_of_staff = k.id 
    WHERE `email`= '$myusername' && `password` = '$mypassword' && `status` = '0'
    ORDER BY `s`.`id` ASC";
    
    return mysqli_query($con, $sql);
}

class staff{
    function staff($id, $name, $email, $pass, $address, 
        $phone, $kind_of_staff, $res_id, $staff_root, $kind_name){
        $this->id = $id;
        $this->staff_name = $name;
        $this->email = $email;
        $this->password = $pass;
        $this->address = $address;
        $this->phone_number = $phone;
        $this->kind_of_staff = $kind_of_staff;
        $this->res_id = $res_id;
        $this->staff_root = $staff_root;
        $this->kind_name = $kind_name;
    }
}

?>