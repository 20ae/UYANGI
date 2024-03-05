<?PHP

    include_once("connection.php");

    $ProductID= isset($_GET['ProductID']) ? $_GET['ProductID'] : '';
    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    $query = "SELECT * FROM products_image where ProductID='$ProductID'"; 
    
    $r = mysqli_query($conn, $query) or die("Selection Failure !");
    
    $result = array();

    while($row = mysqli_fetch_array($r)){
        array_push($result,array(
            'ProductID'=>$row['ProductID'],
            'detailImg1'=>$row['detailImg1']
        ));
    }

    echo json_encode(array('result'=>$result));
    //mysql_close($conn);
?>