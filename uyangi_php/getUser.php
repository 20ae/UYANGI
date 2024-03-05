<?PHP
include_once("connection.php"); 
if(isset($_POST['txtuserID'])){
    
    $userID = $_POST['txtuserID'];


    $query = "SELECT CustomerID FROM customer WHERE userID = '$userID'";

    $result = mysqli_query($conn, $query);
    if($result->num_rows > 0){ //has record. correct userID and password
        echo "success";
        exit;
    }
    else{
        echo "Wrong userID and password"; 
        exit;
    }
}
?>


<html>
<head><title>Login | Test</title></head>
    <body>
        <h1>Login Example | <a href=â€http://www.kosalgeek.comâ€>Test</a></h1>
        <form action="<?PHP $_PHP_SELF ?>" method="post">
            userID <input type="text" name="txtuserID" value="" placeholder="Enter userID" /><br/>
            <input type="submit" name="btnSubmit" value="Login"/>
        </form>
    </body>