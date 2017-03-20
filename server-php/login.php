<?php require_once("includes/functions.php"); do_pre();?>
<?php 
$response = array();
$CusName = $_POST['CusName'];
$PhNo = $_POST['PhNo'];
$TableNo = $_POST['TableNo'];
$result=insert_to_Customers($CusName,$PhNo,$TableNo);
 if ($result) {
        $response["success"] = 1;
        $response["message"] = "Welcome!! {$CusName}";
		$response["CusId"] = "{$result}";
        echo json_encode($response);
    } else {
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred!";
        echo json_encode($response);
        }
?>







