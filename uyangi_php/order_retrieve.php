<?PHP
    include_once("connection.php");
    $userID= isset($_POST['txtuserID']) ? $_POST['txtuserID'] : '';

    $query = "SELECT * FROM orders WHERE CustomerID = '$userID' ORDER BY OrderNumber DESC"; 
    
    $result = mysqli_query($conn, $query);
    
    while($row = mysqli_fetch_assoc($result)){
            $data[] = $row;
    }
    echo json_encode($data);
?>
<?php
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if (!$android) {
?>

    <html>

    <body>

        <form action="<?php $_PHP_SELF ?>" method="POST">
            CustomerID: <input type="text" name="txtuserID" />
            <input type="submit" />
        </form>

    </body>

    </html>
<?php
}
?>