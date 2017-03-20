<?php require_once("includes/functions.php"); do_pre();?>
<?php 
$result=get_menu_info();
$data = array();
while($hell=mysql_fetch_assoc($result)){
	$data[]=$hell;
}	
echo json_encode($data);


?>
