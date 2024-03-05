<?php
include_once("connection.php");

if(isset($_POST['userID']) && isset($_POST['password']) && isset($_POST['Name']) && isset($_POST['pNumber']) && isset($_POST['address']) && isset($_POST['email']))
{ 
	$userID = $_POST['userID'];
	$password = $_POST['password'];
	$Name = $_POST['Name'];
	$pNumber = $_POST['pNumber'];
	$address = $_POST['address'];
	$email = $_POST['email'];
	
	
	$query = "INSERT INTO customer(userID, Password, Name, PhoneNumber, address, Email) VALUES ('$userID', '$password', '$Name', '$pNumber', '$address', '$email')";
	
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
<head><title>Register | Test</title></head>
    <body>
        <h1>Register Example | <a href="http://group8.hol.es/register.php"�>Test</a></h1>
        <form action="<?PHP $_PHP_SELF ?>" method="post">
						userID <input type="text" name="userID" value="" placeholder="Enter userID" /><br/>
						Password <input type="password" name="password" value="" placeholder="Enter Password" /><br/>
            Name <input type="text" name="Name" value="" placeholder="Enter Your name" /><br/>
						Phone Number <input type="text" name="pNumber" value="" placeholder="Enter phone number" /><br/>
						address <input type="text" name="address" value="" placeholder="Enter Your address" /><br/>
						Email <input type="text" name="email" value="" placeholder="Enter Email" /><br/>
            <input type="submit" name="btnSubmit" value="Register"/>
        </form>
    </body>
</html>			