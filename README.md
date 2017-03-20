# FoodMe

An Android application to place orders when you sit inside Restaurants or Hotels. This reduces the work of Waiters/Servers and most importantly you no need to wait until waiter addresses you.

<h3>Steps</h3>
1.Simply connect to their Wi-Fi network.<br>
2.Login with your Name, Contact Number, Table Number. <br>
3.You will be presented with Menus available.<br>
4.Select food items and quantity and palce the order. <br>
5.Bill will be generated and presented to you. <br>
6.Orders will be sent to the Chef/Cook. So that they can prepare you a delicious food.<br>

<h3>How application works</h3>
1.Server side code is written in php and the server will be running on standard ports which is known to the android application.<br>
2.App sends GET request and reads JSON file from the server.<br>
3.While placing the orders. The POST request is sent to the server and the server responds with generated Bill.<br>
