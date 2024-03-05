<?PHP
    include_once("connection.php");

    $catid= isset($_POST['catid']) ? $_POST['catid'] : '';
    $length= isset($_POST['length']) ? $_POST['length'] : '';
    $style= isset($_POST['style']) ? $_POST['style'] : '';
    //$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    $query = "select * from products where catid='$catid' and length='$length' and style='$style'"; 
    
    $result = mysqli_query($conn, $query);
    
    while($row = mysqli_fetch_assoc($result)){
            $data[] = $row;
    }
    //echo json_encode($data)
    header('Content-Type: application/json; charset=utf8');
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
            Category ID: <input type="text" name="catid" />
            Length: <input type="text" name="length" />
            Style: <input type="text" name="style" />
            <input type="submit" />
        </form>

    </body>

    </html>
<?php
}
?>