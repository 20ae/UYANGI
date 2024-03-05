<?PHP
include_once("connection.php"); 
if(isset($_POST['txtuserID']) && isset($_POST['txtPassword'])){
    
    $userID = $_POST['txtuserID'];
    $password = $_POST['txtPassword'];


    $query = "SELECT * FROM customer WHERE userID = '$userID' AND Password = '$password'";

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
        <h1>Login Example | <a href=”http://www.kosalgeek.com”>Test</a></h1>
        <form action="<?PHP $_PHP_SELF ?>" method="post">
            userID <input type="text" name="txtuserID" value="" placeholder="Enter userID" /><br/>
            Password <input type="password" name="txtPassword" value="" placeholder="Enter Password" /><br/>
            <input type="submit" name="btnSubmit" value="Login"/>
        </form>
    </body>
</html>	