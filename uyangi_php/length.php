<?PHP
    include_once("connection.php");

    $length= isset($_GET['length']) ? $_GET['length'] : '';
    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    $query = "select * from products where length='$length'"; 
    
    $result = mysqli_query($conn, $query);
    
    while($row = mysqli_fetch_assoc($result)){
            $data[] = $row;
    }
    echo json_encode($data);
?>