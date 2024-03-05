<?PHP
 include_once("connection.php");

if(isset($_POST['txtName']) && isset($_POST['txtPID']) && isset($_POST['txtColor']) &&
   isset($_POST['txtPrice']) && isset($_POST['txtImageUrl']) && isset($_POST['txtCustomer'])){
	$pid = $_POST['txtPID'];
    $name = $_POST['txtName'];
    $option1 = $_POST['txtColor'];
    $price = $_POST['txtPrice'];
    $image_url = $_POST['txtImageUrl'];
	$customer = $_POST['txtCustomer'];

    $query = "INSERT INTO favorite(ProductID, ProductName, option1, price, img_url, customer) VALUES ('$pid', '$name', '$option1','$price', '$image_url', '$customer')";

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
<head><title>Insert | Favorite</title></head>
    <body>
        <h1>Insert to Favorite | <a href=â€http://www.kosalgeek.comâ€>Test</a></h1>
        <form action="<?PHP $_PHP_SELF ?>" method="post">
            PID <input type="text" name="txtPID" value=""/><br/>
            Name <input type="text" name="txtName" value=""/><br/>
            ColorOption <input type="text" name="txtColor" value=""/><br/>
            Price <input type="text" name="txtPrice" value=""/><br/>
            Image URL <input type="text" name="txtImageUrl" value=""/><br/>
			Customer <input type="text" name="txtCustomer" value=""/><br/>
            <input type="submit" name="btnSubmit" value="Insert"/>
        </form>
    </body>
</html>