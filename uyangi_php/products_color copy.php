<?PHP
    include_once("connection.php");

    $ProductID = $_GET['ProductID'];
    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    $query = "SELECT * FROM products_color where productID='$productID'";
    //$query = "SELECT product_color FROM color where ProductID='$ProductID'"; 
    
    $result = mysqli_query($conn, $query) or die("Selection Failure !");
    
    while($row = mysqli_fetch_assoc($result)){
            $data[] = $row;
    }
    echo json_encode($data);
    //mysql_close($conn);
?>