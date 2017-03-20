<?php require_once("includes/functions.php"); do_pre();?>
<head>
<meta http-equiv="refresh" content="05" />
</head>
<?php
$result = get_Orders();
while ($row = mysql_fetch_array($result)) {
	$CusId=$row["CusId"];
echo "<button>"."<a href=\"showOrderDetails.php?cus=".$CusId."\">".$CusId."</a>"."</button>"."<br />"."<br />";
   }

?>
