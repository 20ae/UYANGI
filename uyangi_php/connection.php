<?php
$servername = "localhost"; //replace it with your database server name
$userID = "root";  //replace it with your database userID
$password = "990218";  //replace it with your database password
$dbname = "uyangidb";
// Create connection
$conn = mysqli_connect($servername, $userID, $password, $dbname);
// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
?>