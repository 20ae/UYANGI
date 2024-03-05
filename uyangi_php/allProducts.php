<?PHP
    include_once("connection.php");

    $catid= isset($_GET['catid']) ? $_GET['catid'] : '';
    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    $query = "select * from products"; 
    
    $result = mysqli_query($conn, $query);
    
    while($row = mysqli_fetch_assoc($result)){
            $data[] = $row;
    }
    echo json_encode($data);
?>