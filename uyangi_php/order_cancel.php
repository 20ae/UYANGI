<?PHP
include_once("connection.php");

if(isset($_POST['CustomerID'])){
    
    $CustomerID = $_POST['CustomerID'];
    $OrderNumber= isset($_POST['OrderNumber']) ? $_POST['OrderNumber'] : '';

    $query = "UPDATE orders SET OrderStatus = '취소요청' WHERE OrderNumber ='$OrderNumber' and CustomerID = '$CustomerID'";
    
    $result = mysqli_query($conn, $query);

    if($result > 0){
        echo "success";
        exit;
    }
    else{
        echo "failed";
        exit;
    }
}    
?>
<?php
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if (!$android) {
?>

    <html>

    <body>

        <form action="<?php $_PHP_SELF ?>" method="POST">
            CustomerID: <input type="text" name="CustomerID"/><br/>
            OrderNumber <input type="text" name="OrderNumber" value=""/><br/>
            <input type="submit" />
        </form>

    </body>

    </html>
<?php
}
?>