<?php
if(!empty($_GET))
{
	require_once("includes/functions.php"); do_pre();
	$CusId = $_GET['cus']; 
	$row = get_Customer_info($CusId);
	echo "Customer Name:{$row['CusName']}"."&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"."Table No.:{$row['TableNo']}"."<br />";
	
	$res = get_order_detail($CusId);
	while($data = mysql_fetch_array($res))
	echo "<br />Food Name:".get_food_name($data['FoodId'])."<br />Quantity:".$data['Qty']."<br /><br /><br />";
	}
else echo "lol!! No values has been passed";
?>