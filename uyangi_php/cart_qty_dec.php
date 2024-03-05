<?PHP
include_once("connection.php");

echo "this is update.php";

if(isset($_POST['ProductID'])){
    
    $ProductID = $_POST['ProductID'];
    $userID= isset($_POST['userID']) ? $_POST['userID'] : '';
    $option1= isset($_POST['option1']) ? $_POST['option1'] : '';

    $query = "UPDATE cart SET quantity=quantity-1 WHERE ProductID='$ProductID' and customer = '$userID' and option1='$option1'";
    
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
            ProductID: <input type="text" name="ProductID"/><br/>
            Customer <input type="text" name="userID" value=""/><br/>
            ColorOption <input type="text" name="option1" value=""/><br/>
            <input type="submit" />
        </form>

    </body>

    </html>
<?php
}
?>