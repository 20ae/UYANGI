<?PHP
    include_once("connection.php");

    $query = "SELECT * FROM products WHERE catid = 1 or catid = 4 ORDER BY ProductID ASC"; 
    
    $result = mysqli_query($conn, $query);
    
    while($row = mysqli_fetch_assoc($result)){
            $data[] = $row;
    }
    echo json_encode($data);
?>