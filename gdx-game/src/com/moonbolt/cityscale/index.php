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
	$ldata = $_POST['ldata'];
	
	#Variaveis de Uso
	$lAll = '';
	$lchat = $_POST['lchat'];
	$lname = $_POST['lname'];

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

	#Adicionar Chat
	if ($lrequest == "Chat")
	{
		$sql = "INSERT INTO Chats (AccountID,Name,Msg) VALUES ('$ldataaccount','$lname','$lchat')";
		if ($conn->query($sql) === TRUE) { echo nl2br("\n - Adicionado - \n"); } else { echo nl2br($sql); echo nl2br("\n - Falhou \n") . $conn->error;}		
		$result = $conn->query($sql);
		$conn->close();	
		return;
	}

	#Sync Chats
	if ($lrequest == "SyncChats")
	{
	$sql = "SELECT * FROM Chats order by ChatID desc limit 3";
		$result = $conn->query($sql);
		if ($conn->query($sql) === TRUE) { echo nl2br("\n - Recuperado - \n"); } else { echo nl2br($sql); echo nl2br("\n - Falhou - \n") . $conn->error;}
		if ($result->num_rows > 0) {
			// output data of each row
			while($row = $result->fetch_assoc()) {
				$lAll = $lAll . ("SYSTEMCHAT - :Name:" . $row["Name"]. 
							  ":Msg:" .  $row["Msg"]. 
							  ": - \n");
				echo($lAll);
			}
		}
	}

	##UPLOAD
	if ($lrequest == "Upload"){
		
		$arquivo = $ldataaccount;
		$file = fopen($arquivo, 'w');	
		if(fwrite($file, $ldata)){
			echo nl2br("Atualizado");
			fclose($file);
		}	
		else
		{
			echo nl2br("Negado");
		}
		
	}
	
	##DOWNLOAD
	if ($lrequest == "Download"){
		
		if (file_exists($ldataaccount)) {
			$arquivo = fopen ($ldataaccount,"r");
			while (!feof ($arquivo)) {
				$linha = fgets($arquivo,4096);
				 echo nl2br ($linha);
			}
			fclose ($arquivo);
			echo nl2br("Atualizado");
		}
		else
		{
			echo nl2br("Negado");
		}		
	}
?>