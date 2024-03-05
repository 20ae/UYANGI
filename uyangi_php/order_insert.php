<?PHP
 include_once("connection.php");

if(isset($_POST['CustomerID']) && isset($_POST['OrderName']) && isset($_POST['TotalPrice'])){
	  $CustomerID = $_POST['CustomerID'];
    $OrderName = $_POST['OrderName'];
    $TotalPrice = $_POST['TotalPrice'];
    

    $query = "INSERT INTO orders(CustomerID, OrderName, TotalPrice) VALUES ('$CustomerID', '$OrderName', '$TotalPrice')";

    $result = mysqli_query($conn, $query);

    if($result > 0){
            echo "success";
            exit;

    }
    else{
         echo "failed";
         exit;

    }
}

?>
<html>
<head><title>Insert | Order</title></head>
    <body>
        <h1>Insert to Order | <a href=â€http://www.kosalgeek.comâ€>Test</a></h1>
        <form action="<?PHP $_PHP_SELF ?>" method="post">
            CustomerID <input type="text" name="CustomerID" value=""/><br/>
            OrderName <input type="text" name="OrderName" value=""/><br/>
            TotalPrice <input type="text" name="TotalPrice" value=""/><br/>
            
            <input type="submit" name="btnSubmit" value="Insert"/>
        </form>
    </body>
</html>