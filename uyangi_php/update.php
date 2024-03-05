<?php
include_once("connection.php");

$u = $_POST['txtuserID'];
$newP = $_POST['txtPassword'];

$sql = "UPDATE customer SET Password='$newP' WHERE userID='$u'";
$sql2 = "SELECT * FROM customer WHERE userID='$u'";
$result = mysqli_query($conn, $sql2);

//if the userID exists, then change the password
if (($conn->query($sql) === TRUE) && ($result->num_rows > 0)) {
    echo "Update Successful";
    exit;
} else {
    echo "Update Unsuccessful";
    exit;
}

//Close the connection 
$conn->close();
?>