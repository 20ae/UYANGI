<?PHP
include_once("connection.php"); 
if(isset($_POST['userID'])){
    
    $userID = $_POST['userID'];


    $query = "SELECT * FROM customer WHERE userID='$userID'";

    $r = mysqli_query($conn, $query) or die("Selection Failure !");
    
    $result = array();

    while($row = mysqli_fetch_array($r)){
        array_push($result,array(
            'Name'=>$row['Name']
        ));
    }

    echo json_encode(array('result'=>$result));
    // $response = array();//배열 선언


    // while($row = mysqli_fetch_array($result)){
    // //response["userID"]=$row[0] ....이런식으로 됨.

    //     array_push($response, array("userID"=>$row[0], "Name"=>$row[1], "PhoneNumber"=>$row[2], "address"=>$row[3], "Email"=>$row[4]));

    // }

    // //response라는 변수명으로 JSON 타입으로 $response 내용을 출력

    // echo json_encode(array("response"=>$response));


}
?>