<?PHP
    include_once("connection.php");

    $thickness= isset($_GET['thickness']) ? $_GET['thickness'] : '';
    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    $query = "select * from products where thickness='$thickness'"; 
    
    $result = mysqli_query($conn, $query);
    
    while($row = mysqli_fetch_assoc($result)){
            $data[] = $row;
    }
    echo json_encode($data);
?>