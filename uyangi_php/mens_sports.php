<?PHP
    include_once("connection.php");

    $query = "SELECT * FROM products where catid = 1 and length = '스포츠' ORDER BY ProductID ASC;"; 
    
    $result = mysqli_query($conn, $query);
    
    while($row = mysqli_fetch_assoc($result)){
            $data[] = $row;
    }
    echo json_encode($data);
?>