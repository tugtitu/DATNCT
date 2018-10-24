<?php

$host='localhost';
$username='root';
$pwd='';
$db="manager_restaurant";

$con = mysqli_connect($host,$username,$pwd, $db);
mysqli_query($con,"SET character_set_results = 'utf8', character_set_client = 'utf8', character_set_connection = 'utf8', character_set_database = 'utf8', character_set_server = 'utf8'");

?>