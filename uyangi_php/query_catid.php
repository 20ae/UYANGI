<?php
error_reporting(E_ALL);
ini_set('display_errors',1);
include('dbcon.php');


$catid= isset($_GET['catid']) ? $_GET['catid'] : '';
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


if ($catid != "" ){

    $sql="select * from socks where catid='$catid'";
    $stmt = $con->prepare($sql);
    $stmt->execute();

    if ($stmt->rowCount() == 0){

        echo "'";
        echo $catid;
        echo "' incorrect category id.";
    }
        else{
        
                $data = array();

        while($row=$stmt->fetch(PDO::FETCH_ASSOC)){

                extract($row);

            array_push($data,
                array(
                'productID'=>$row["productID"],
                'productName'=>$row["productName"],
                'price'=>$row["price"],
                'description'=>$row["description"],
                'catid'=>$row["catid"],
                'img_url'=>$row["img_url"]

            ));
        }


        if (!$android) {
            echo "<pre>";
            print_r($data);
            echo '</pre>';
        }else
        {
            header('Content-Type: application/json; charset=utf8');
            $json = json_encode(array("user"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
            echo $json;
        }
    }
}
else {
    echo "category id";
}

?>