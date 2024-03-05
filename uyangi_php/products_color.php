<?PHP

    include_once("connection.php");

    $ProductID= isset($_GET['ProductID']) ? $_GET['ProductID'] : '';
    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    $query = "SELECT * FROM color where ProductID='$ProductID'"; 
    
    $r = mysqli_query($conn, $query) or die("Selection Failure !");
    
    $result = array();

    while($row = mysqli_fetch_array($r)){
        array_push($result,array(
            'ProductID'=>$row['ProductID'],
            'product_color'=>$row['product_color'],

        ));
    }

    echo json_encode(array('result'=>$result));
    //mysql_close($conn);
?>