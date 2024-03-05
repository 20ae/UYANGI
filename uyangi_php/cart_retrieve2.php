<?PHP
    include_once("connection.php");
    $userID= isset($_GET['customer']) ? $_GET['customer'] : '';

    $query = "SELECT * FROM cart WHERE customer = '$userID' ORDER BY cart_id DESC"; 
    
    $r = mysqli_query($conn, $query) or die("Selection Failure !");
    
    $result = array();

    while($row = mysqli_fetch_array($r)){
        array_push($result,array(
            'ProductID'=>$row['ProductID'],
            'ProductName'=>$row['ProductName'],
            'option1'=>$row['option1'],
            'quantity'=>$row['quantity'],
            'price'=>$row['price'],
            'img_url'=>$row['img_url'],
            'customer'=>$row['customer'],
            'reg_time'=>$row['reg_time']
        ));
    }
    echo json_encode(array('result'=>$result));
?>
<?php
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if (!$android) {
?>

    <html>

    <body>

        <form action="<?php $_PHP_SELF ?>" method="GET">
            UserID: <input type="text" name="txtuserID" />
            <input type="submit" />
        </form>

    </body>

    </html>
<?php
}
?>