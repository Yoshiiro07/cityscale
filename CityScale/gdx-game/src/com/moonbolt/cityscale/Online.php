<?php

	#Variaveis Recebidas do Cliente
	$ldata = $_POST['ldata'];
	$lrequest = $_POST['lrequest'];
	$lversion = $_POST['lversion'];
	
	#Variaveis de Banco
	$servername = $_POST['lservername'];
	$username = $_POST['lusername'];
	$password = $_POST['lpassword'];
	$dbname = $_POST['ldbname'];
	
	#Variaveis de Atualizacao
	$lname = $_POST['lname'];
	$lsex = $_POST['lsex'];
	$llevel = $_POST['llevel'];
	$lhp = $_POST['lhp'];
	$lmp = $_POST['lmp'];
	$lcoordsX = $_POST['lcoordsX'];
	$lcoordsY = $_POST['lcoordsY'];
	$lmap = $_POST['lmap'];
	$lhair = $_POST['lhair'];
	$lhat = $_POST['lhat'];
	$lsetEquip = $_POST['lsetEquip'];
	$lparty = $_POST['lparty'];
	$ljob = $_POST['ljob'];
	$lwalk = $_POST['lwalk'];
	$lpos = $_POST['lpos'];
	$lweapon = $_POST['lweapon'];
	$laccount = $_POST['laccount'];
	$lside = $_POST['lside'];
	$lbattle = $_POST['lbattle'];
	
	$lchat = $_POST['lchat'];
	
	#Variaveis de uso Global
	$lAll = '';
	$lQueryEx = '';
	$lnomeplayer = '';
	
	
	#Comandos Uteis #
	#\n  (Quebra Linha)
	
	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
    die("Resultado: - Falhou na Conexão com Banco -" . $conn->connect_error);
	return;
	}
	
	#Sincronizar
	if ($lrequest == "Sync"){		
		///// Recupera Chat /////
		$lAll = '';
		$sql = "SELECT * FROM Chats ORDER BY ChatID DESC LIMIT 3";
		$result = $conn->query($sql);
		
		if ($result->num_rows > 0) {
			// output data of each row
			while($row = $result->fetch_assoc()) {
				$lAll = $lAll . ("SYSTEMCHAT - :Nome=" . $row["Nome"] . 
								":Mensagem=" .  $row["Mensagem"]. ":\n");			
			}
			echo($lAll);			
		}
		
		/////Recupera Mobs /////
		//$lAll = '';
		//$sql = "SELECT * FROM Mobs where MAP = '$lmap' ";
		//$result = $conn->query($sql);			
		//if ($result->num_rows > 0) {
		//	while($row = $result->fetch_assoc()) {
									
		//	$lAll = $lAll . ("SYSTEMMOBS - :MOBID=" . $row["MobsID"]. 
		//					  ":GameMobID=" .  $row["GameMobID"]. 
		//					  ":MOBHP=" . $row["HPMob"] . 
		//					  ":MAP=" . $row["MAP"] .
		//					  ": - \n");			
		//	}		
		//}
		//echo($lAll);
		
		/////Recupera EXPSHARED /////
		//$lAll = '';
		//$sql = "SELECT * FROM PartyExp where Party = '$lparty' ORDER BY ExpSharedID DESC LIMIT 1";
		//$result = $conn->query($sql);			
		//if ($result->num_rows > 0) {
		//	while($row = $result->fetch_assoc()) {
									
		//		$lAll = $lAll . ("SYSTEMPARTYEXP - :EXPSHAREDID=" . $row["ExpSharedID"]. 
		//					  ":EXPSEND=" .  $row["ExpSend"]. 
		//					  ":PARTY=" . $row["Party"] . 
		//					  ":ACCOUNT=" . $row["Account"] . 
		//					  ": - \n");			
		//	}		
		//}
		//echo($lAll);
		
		//echo nl2br($lAll);
		
		//Verifica se já está ativo
		$sql = "SELECT * FROM Processos where ACCOUNT = '$ldata' ";
		$result = $conn->query($sql);
		if ($result->num_rows > 0) {			
			$sql = "UPDATE Processos set Name = '$lname',
										 Hp = '$lhp',
										 Mp = '$lmp',
										 CoordsX = '$lcoordsX',
										 CoordsY = '$lcoordsY',
										 Map = '$lmap',
										 Level = '$llevel',
										 SetEquip = '$lsetEquip',
										 Hair = '$lhair',
										 Hat = '$lhat',
										 Weapon = '$lweapon',
										 Battle = '$lbattle',
										 Side = '$lside',
										 Pos = '$lpos',
										 Walk = '$lwalk',
										 Account = '$laccount',
										 Party = '$lparty',
										 Sex = '$lsex',
										 Job = '$ljob'
										 where Account = '$laccount' ";			
			$result = $conn->query($sql);
			if ($conn->query($sql) === TRUE) { echo nl2br("\n - Atualizado - \n"); } else { echo nl2br("\n - Falhou Update - \n") . $conn->error; }
		}
		//Insere se não existir
		else
		{
			$lAll = '';
			$sql = "INSERT INTO Processos (Name,Sex,Level,Hp,Mp,CoordsX,CoordsY,Map,Hair,Hat,SetEquip,Party,Job,Walk,Pos,Weapon,Account,Side,Battle) VALUES ('$lname','$lsex','$llevel','$lhp','$lmp','$lcoordsX','$lcoordsY','$lmap','$lhair','$lhat','$lsetEquip','$lparty','$ljob','$lwalk','$lpos','$lweapon','$laccount','$lside','$lbattle')"; 
		
			if ($conn->query($sql) === TRUE) {
				echo "SYSTEMINSERT \n";
			} else {
				echo "Error: " . $sql . "<br>" . $conn->error;
			}			
		}
		
		/////Retorna Players Online /////
		$lAll = '';
		$sql = "SELECT * FROM Processos";
		$result = $conn->query($sql);			
		if ($result->num_rows > 0) {
			while($row = $result->fetch_assoc()) {
									
				$lAll = $lAll . ("SYSTEMPLAYERS - :Name=" . $row["Name"]. 
							  ":Hp=" .  $row["Hp"]. 
							  ":Mp=" . $row["Mp"] . 
							  ":CoordsX=" . $row["CoordsX"] .
							  ":CoordsY=" . $row["CoordsY"] .
							  ":Map=" . $row["Map"] .
							  ":Level=" . $row["Level"] .
							  ":SetEquip=" . $row["SetEquip"] .
							  ":Hair=" . $row["Hair"] .
							  ":Hat=" . $row["Hat"] .
							  ":Weapon=" . $row["Weapon"] .
							  ":Battle=" . $row["Battle"] .
							  ":Side=" . $row["Side"] .
							  ":Pos=" . $row["Pos"] .
							  ":Walk=" . $row["Walk"] .
							  ":Account=" . $row["Account"] .
							  ":Party=" . $row["Party"] .
							  ":Sex=" . $row["Sex"] .
							  ":Job=" . $row["Job"] .   
							  ": - \n");
				echo($lAll);
			}
		}
		
		
		$conn->close();
	}
	
	#CREATE PARTY 
	if ($lrequest == "Party"){
		
		$lAll = '';
		$sql = "SELECT * FROM Processos where Party = '$lparty' ";	
		$result = $conn->query($sql);
		if ($result->num_rows < 4) {			
			$sql = "UPDATE Processos set PARTY = '$lparty' where ACCOUNT = '$ldata' ";			
			$result = $conn->query($sql);
			if ($conn->query($sql) === TRUE) { echo nl2br("\n - AtualizadoParty - \n"); } else { echo nl2br("\n - Falhou Update - \n") . $conn->error; }
		}
		
		$conn->close();
	}
	
	#SEND EXP PARTY
	if ($lrequest == "ExpSharedUpdate"){
		
		$lAll = '';
		$sql = 
		"INSERT INTO PartyExp (ExpSend,Party,Account)  VALUES 
		('$lexpshared','$lparty','$ldata') ";

		if ($conn->query($sql) === TRUE) {
			echo "SYSTEMEXPUPDATE \n";
		} else {
			echo "Error: " . $sql . "<br>" . $conn->error;
		}
		
		$conn->close();
	}
	
	#Atk 
	if ($lrequest == "Atk"){
		
		$lAll = '';
		$sql = "SELECT * FROM Mobs where GameMobID = '$lmobID' ";	
		$result = $conn->query($sql);
		if ($result->num_rows > 0) {			
			$sql = "UPDATE Mobs set HPMob = '$ldmg' where GameMobID = '$lmobID' ";			
			$result = $conn->query($sql);
			if ($conn->query($sql) === TRUE) { echo nl2br("\n - AtualizadoMOB - \n"); } else { echo nl2br("\n - Falhou Update - \n") . $conn->error; }
		}
		
		$conn->close();
	}
	
	#Chat
	if ($lrequest == "Chat"){
		
		$lAll = '';
			$sql = "INSERT INTO Chats (Nome,Mensagem) 
						        VALUES ('$lname','$lchat') ";
								
		if ($conn->query($sql) === TRUE) {	
		}
		else{
			echo nl2br ("Mensagem Invalida \n");
		}
		
		$conn->close();
	}
	
	##UPLOAD
	if ($lrequest == "Upload"){
		
		$arquivo = $laccount + '.txt';
		$file = fopen($arquivo, 'w');	
		if(fwrite($file, $ldata)){
			echo nl2br("Resultado: \n - Upload com Sucesso- \n");
		}
		fclose($file);
	}
	
	##DOWNLOAD
	if ($lrequest == "Download"){
		
		if (file_exists($laccount)) {
			$arquivo = fopen ($laccount,"r");
			while (!feof ($arquivo)) {
				$linha = fgets($arquivo,4096);
				 echo nl2br ($linha);
			}
			fclose ($arquivo);
		}
		else
		{
			echo nl2br("Arquivo Inexistente");
		}		
	}
?>
