<?PHP
    include_once("connection.php");

    $ProductName= isset($_POST['ProductName']) ? $_POST['ProductName'] : '';
    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    $query = "select * from products WHERE ProductName LIKE '%$ProductName%'"; 
    
    $result = mysqli_query($conn, $query);
    
    while($row = mysqli_fetch_assoc($result)){
            $data[] = $row;
    }

    header('Content-Type: application/json; charset=utf8');
            // $json = json_encode(array("search" => $data), JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE);
    $json = json_encode($data, JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE);
    echo $json;
?>

<?php
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if (!$android) {
?>

    <html>

    <body>

        <form action="<?php $_PHP_SELF ?>" method="POST">
            ProductName: <input type="text" name="ProductName" />
            <input type="submit" />
        </form>

    </body>

    </html>
<?php
}
?>