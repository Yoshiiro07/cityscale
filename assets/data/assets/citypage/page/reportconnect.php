<?php

$servername = "cityserver.mysql.uhserver.com";
$username = "citymaster";
$password = "City@key90";
$dbname = "cityserver";

// Create connection
$conn = mysqli_connect($servername, $username, $password, $dbname);
// Check connection
if (!$conn) {
  die("Connection failed: " . mysqli_connect_error());
}

$sql = "SELECT * FROM Reports";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
  // output data of each row
  while($row = mysqli_fetch_assoc($result)) {
  	
	echo "<div class=\"row m-4\">";
	
	echo "<div class=\"col-lg-8\">" . $row["Descricao"]. "";
	echo "</div>";
	
	echo "<div class=\"col-lg-2 text-center\">"  . $row["Tipo"]. "";
	echo "</div>";
	
	echo "<div class=\"col-lg-2 text-center\">" . $row["Situacao"]. "";
	echo "</div>";
	
	echo "</div>";
	
    //echo "id: " . $row["id"]. " - Name: " . $row["firstname"]. " " . $row["lastname"]. "<br>";
  }
} else {
  echo "Sem Reports";
}

mysqli_close($conn);







?>