<?PHP
    include_once("connection.php");

    $query = "SELECT * FROM products where catid = 2 and length = '덧신' ORDER BY ProductID ASC;"; 
    
    $result = mysqli_query($conn, $query);
    
    while($row = mysqli_fetch_assoc($result)){
            $data[] = $row;
    }
    echo json_encode($data);
?>