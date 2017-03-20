<?php
include("includes/constants.php");

function do_pre()
{
$my_db=connect_to_db();
query_success($my_db);
query_success(select_db($my_db));
}

function connect_to_db()
{
	 $db = mysql_connect(HOST,USER);	
	 return $db;
}

function query_success($query)
{
	if(!$query)
 {
	die("Something went wrong".mysql_error());
 }
}

function select_db($data_base)
{
	$result=mysql_select_db(DB_NAME,$data_base);
	return $result;
}

function insert_to_Customers($CusName,$PhNo,$TableNo)
{
	 $query = "INSERT INTO customers (CusId, CusName, PhNo, TableNo) 
	 VALUES 
	 (NULL, 
	 '{$CusName}', 
	 '{$PhNo}',	
	 '{$TableNo}')";	 
	 $res=mysql_query($query);
	 query_success($res);
	 return get_CustomerId($PhNo,$CusName);	
}

function get_Customer_info($CusId)
{ 
$query = "SELECT CusName, PhNo, TableNo FROM customers WHERE CusId = {$CusId}"; 
$res = mysql_query($query);
query_success($res);
$data = mysql_fetch_array($res);
return $data;
}

function get_menu_info()
{ 
$query = "SELECT FoodId,FoodName,Cost,Descr,ImgUrl FROM menu ORDER BY `FoodName` ASC"; 
$res = mysql_query($query);
query_success($res);
//$data = mysql_fetch_array($res);
return $res;
}

function insert_to_Oders($CusId,$FoodId,$Qty)
{
	 $query = "INSERT INTO orders (CusId, FoodId, Qty) 
	 VALUES 
	 ( 
	 '{$CusId}', 
	 '{$FoodId}',	
	 '{$Qty}')";	 
	 $res=mysql_query($query);
	 query_success($res);
	 return $res;	
}

function get_CustomerId($PhNo,$CusName)
{
	$query = "SELECT `CusId` FROM `customers` WHERE `CusName`='{$CusName}' and `PhNo`={$PhNo}";
	 $res = mysql_query($query);
	 query_success($res);
	 $data = mysql_fetch_array($res);
	 return $data['CusId'];
}

function get_Orders()
{
	$query = "SELECT `CusId` FROM orders group by CusId desc";
	 $res = mysql_query($query);
	 query_success($res);	 
	 return $res;
}

function get_order_detail($CusId)
{
$query = "SELECT FoodId, Qty FROM orders WHERE CusId = {$CusId}"; 
$res = mysql_query($query);
query_success($res);
return $res;
}

function get_food_name($FoodId)
{
$query = "SELECT FoodName FROM menu WHERE FoodId = {$FoodId}"; 
$res = mysql_query($query);
query_success($res);
$data = mysql_fetch_array($res);
return $data['FoodName'];
}

?>