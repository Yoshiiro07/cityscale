<?php

	#Variaveis de Banco
	$servername = $_POST['lservername'];
	$username = $_POST['lusername'];
	$password = $_POST['lpassword'];
	$dbname = $_POST['ldbname'];

	#Variaveis Recebidas do Cliente
	$lrequest = $_POST['lrequest'];
	$ldataaccount = $_POST['ldataaccount'];
	$lversion = $_POST['lversion'];
	
	#Variaveis de Uso
	$lAll = '';

	#\n  (Quebra Linha)
	
	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);
	
	// Check connection
	if ($conn->connect_error) {
		die("Resultado: - Falhou na Conexão com Banco -" . $conn->connect_error);
	}
	
	#Efetua Login
	if ($lrequest == "CheckVersion")
	{
		$sql = "SELECT * FROM VersionControl";
		$result = $conn->query($sql);
		
		$lAll = '';
		if ($result->num_rows > 0) {
			// output data of each row
			while($row = $result->fetch_assoc()) {
				if($row["descricao"] == $lversion){
					echo nl2br("Autorizado");
				}
				if($row["descricao"] != $lversion){
					echo nl2br("Probido");
				}
			}
		}
		
		$conn->close();	
		return;
	}
?>