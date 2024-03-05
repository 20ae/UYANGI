<?PHP
    include_once("connection.php");

if(isset($_POST['txtType']) && isset($_POST['txtHolder']) && isset($_POST['txtNumber']) && 
   isset($_POST['txtExpMonth']) && isset($_POST['txtExpYear']) && isset($_POST['txtCvc']) ){
	
    $type = $_POST['txtType'];
	$holder = $_POST['txtHolder'];
    $number = $_POST['txtNumber'];
    $expMonth = $_POST['txtExpMonth'];
    $expYear = $_POST['txtExpYear'];
	$cvc = $_POST['txtCvc'];

    $query = "INSERT INTO card(type, cardholder, cardnumber, expMonth, expYear, cvc) 
    VALUES ('$type', '$holder', '$number', '$expMonth', '$expYear', '$cvc')"; 
    
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
<head><title>Insert | Cart</title></head>
    <body>
        <h1>Insert to Cart | <a href=â€http://www.kosalgeek.comâ€>Test</a></h1>
        <form action="<?PHP $_PHP_SELF ?>" method="post">
            Card Type <input type="text" name="txtType" value=""/><br/>
			Card Holder <input type="text" name="txtHolder" value=""/><br/>
            Card Number <input type="text" name="txtNumber" value=""/><br/>
            Expiry Month <input type="text" name="txtExpMonth" value=""/><br/>
            Expiry Year <input type="text" name="txtExpYear" value=""/><br/>
			CVC <input type="text" name="txtCvc" value=""/><br/>
            <input type="submit" name="btnSubmit" value="Insert"/>
        </form>
    </body>
</html>