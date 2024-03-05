<?PHP

    include_once("connection.php");

    $product_color= isset($_GET['product_color']) ? $_GET['product_color'] : '';
    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    $query = "SELECT * FROM combination where product_color='$product_color'"; 
    
    $r = mysqli_query($conn, $query) or die("Selection Failure !");
    
    $result = array();

    while($row = mysqli_fetch_array($r)){
        array_push($result,array(
            'product_color'=>$row['product_color'],
            'HEX'=>$row['HEX'],
            'c_pair_1'=>$row['c_pair_1'],
            'c_pair_2'=>$row['c_pair_2'],
            'c_pair_3'=>$row['c_pair_3'],
            't_pair_1'=>$row['t_pair_1'],
            't_pair_2'=>$row['t_pair_2'],
            't_pair_3'=>$row['t_pair_3']
        ));
    }

    echo json_encode(array('result'=>$result));
    //mysql_close($conn);
?>