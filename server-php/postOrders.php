<?php require_once("includes/functions.php"); do_pre();?>
<?php 
$CusId = $_POST['CusId'];
$FoodId = $_POST['FoodId'];
$Qty = $_POST['Qty'];
$result = insert_to_Oders($CusId,$FoodId,$Qty);
if($result)
{
	echo $result;
}
?>