<?PHP
    include_once("connection.php");

    $style= isset($_GET['style']) ? $_GET['style'] : '';
    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    $query = "select * from products where style='$style'"; 
    
    $result = mysqli_query($conn, $query);
    
    while($row = mysqli_fetch_assoc($result)){
            $data[] = $row;
    }
    echo json_encode($data);
?>