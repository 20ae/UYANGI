<?PHP
    include_once("connection.php");

if(isset($_POST['ProductID'])){
    
    $ProductID = $_POST['ProductID'];
    $userID= isset($_POST['userID']) ? $_POST['userID'] : '';
    $option1= isset($_POST['option1']) ? $_POST['option1'] : '';

    $query = "DELETE FROM favorite WHERE ProductID='$ProductID' and customer = '$userID' and option1='$option1'";
    
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