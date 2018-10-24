<?php
$servername = 'localhost';
$database = "manager_restaurant";
$username = 'root';
$password = '';

// Create connection
$conn = new mysqli($servername, $username, $password);
// Check connection
if ($conn->connect_error) {
	echo json_encode(array("success"=>false));
   	die();
}
echo json_encode(array("success"=>true));

?>