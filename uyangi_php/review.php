<?PHP
    error_reporting(E_ALL);
    ini_set('display_errors', 1);
    include_once("connection.php");

    $productID = isset($_POST['productID']) ? $_POST['productID'] : '';
    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if($productID != null){

        $data = array();

        // $query = "SELECT count(*) FROM review WHERE product_id='$productID';";
        // $query .= "SELECT count(*) FROM review WHERE product_id='$productID' AND pos=1;";
        // $query .= "SELECT count(*) FROM review WHERE product_id='$productID' AND pos=0;"; 
        // $query .= "SELECT ProductName FROM products where ProductID='$productID';";

        $query = "
        SELECT count(*) FROM review WHERE product_id='$productID';
        SELECT count(*) FROM review WHERE product_id='$productID' AND pos=1;
        SELECT count(*) FROM review WHERE product_id='$productID' AND pos=0;
        SELECT ProductName FROM products where ProductId='$productID';
        ";
        

        /* execute multi query */
        mysqli_multi_query($conn, $query);
        do {
            /* store the result set in PHP */
            if ($result = mysqli_store_result($conn)) {
                while ($row = mysqli_fetch_array($result)) {
                    //array_push($data, $row);
                    array_push($data, 
                        array('count'=>$row[0],
                    ));
                }
            }
            /* print divider */
            if (mysqli_more_results($conn)) {
            }
        } while (mysqli_next_result($conn));

        #$query = "SELECT count(*) FROM d_quality WHERE pd_Id='$productID' AND pos=1"; 
        #$query .= "SELECT count(*) FROM d_color WHERE pd_Id='$productID' AND pos=1;";
        #$query .= "SELECT count(*) FROM d_price WHERE pd_Id='$productID' AND pos=1;";
        #$query .= "SELECT count(*) FROM d_delivery WHERE pd_Id='$productID' AND pos=1;"; 
        
        $query = "
        SELECT count(*) FROM d_quality WHERE pd_Id='$productID' AND pos=1;
        SELECT count(*) FROM d_quality WHERE pd_Id='$productID' AND pos=0;
        SELECT count(*) FROM d_color WHERE pd_Id='$productID' AND pos=1;
        SELECT count(*) FROM d_color WHERE pd_Id='$productID' AND pos=0;
        SELECT count(*) FROM d_price WHERE pd_Id='$productID' AND pos=1;
        SELECT count(*) FROM d_price WHERE pd_Id='$productID' AND pos=0;
        SELECT count(*) FROM d_delivery WHERE pd_Id='$productID' AND pos=1;
        SELECT count(*) FROM d_delivery WHERE pd_Id='$productID' AND pos=0;
        ";

        /* execute multi query */
        mysqli_multi_query($conn, $query);
        do {
            /* store the result set in PHP */
            if ($result = mysqli_store_result($conn)) {
                while ($row = mysqli_fetch_array($result)) {
                    //array_push($data, $row);
                    array_push($data, 
                        array('d_count'=>$row[0],
                    ));
                }
            }
            /* print divider */
            if (mysqli_more_results($conn)) {
            }
        } while (mysqli_next_result($conn));


        $query = "SELECT user_id, content, percent FROM review WHERE product_id='$productID' AND pos=1 
        ORDER BY percent DESC LIMIT 10;" ;
        $query .= "SELECT user_id, content, percent FROM review WHERE product_id='$productID' AND pos=0
        ORDER BY percent DESC LIMIT 10" ;

        /* execute multi query */
        mysqli_multi_query($conn, $query);
        do {
            /* store the result set in PHP */
            if ($result = mysqli_store_result($conn)) {
                while ($row = mysqli_fetch_array($result)) {
                    //array_push($data, $row);
                    array_push($data, 
                        array('user_id'=>$row[0],
                        'content'=>$row[1],
                        'percent'=>$row[2],
                    ));
                }
            }
            /* print divider */
            if (mysqli_more_results($conn)) {
            }
        } while (mysqli_next_result($conn));

       
        $query = "SELECT summary1, summary2, summary3 FROM keyword WHERE pd_Id='$productID' " ;

        /* execute multi query */
        mysqli_multi_query($conn, $query);
        do {
            /* store the result set in PHP */
            if ($result = mysqli_store_result($conn)) {
                while ($row = mysqli_fetch_array($result)) {
                    //array_push($data, $row);
                    array_push($data, 
                        array('summary1'=>$row[0],
                        'summary2'=>$row[1],
                        'summary3'=>$row[2],
                    ));
                }
            }
            /* print divider */
            if (mysqli_more_results($conn)) {
            }
        } while (mysqli_next_result($conn));


        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("user" => $data), JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE);
        echo $json;
        

    } 
    else {
        echo " not correct ID ";
    }    
    
?>

<?php
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if (!$android) {
?>

    <html>

    <body>

        <form action="<?php $_PHP_SELF ?>" method="POST">
            product_id: <input type="text" name="productID" />
            <input type="submit" />
        </form>

    </body>

    </html>
<?php
}
?>