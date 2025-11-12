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

	$llevel = $_POST['llevel'];
	$lname = $_POST['lname'];
	$lmap = $_POST['lmap'];
	$lhp = $_POST['lhp'];
	$lmp = $_POST['lmp'];
	$lposX = $_POST['lposX'];
	$lposY = $_POST['lposY'];
	$lwalk = $_POST['lwalk'];
	$lweapon = $_POST['lweapon'];
	$lframe = $_POST['lframe'];
	$lsyncPlayerMob = $_POST['lsyncPlayerMob'];
	$lsetUpper = $_POST['lsetUpper'];
	$lsetBottom = $_POST['lsetBottom'];
	$lsetFooter = $_POST['lsetFooter'];
	$lhair = $_POST['lhair'];
	$lsex = $_POST['lsex'];
	$lcolor = $_POST['lcolor'];
	$lhat = $_POST['lhat'];
	$lside = $_POST['lside'];
	$ljob = $_POST['ljob'];
	$lplayerInBattle = $_POST['lplayerInBattle'];
	$lplayerInAttack = $_POST['lplayerInAttack'];
	$lplayerInCast = $_POST['lplayerInCast'];
	$lplayerSit = $_POST['lplayerSit'];
	$lparty = $_POST['lparty'];
	$lexpshared = $_POST['lexpshared'];
	$lexpsend = $_POST['lexpsend'];

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
		#$result = $conn->query($sql);
		$conn->close();	
		return;
	}

	#Adiciona Exp
	if ($lrequest == "ExpSharedSend")
	{
		$currentDateTime = date('Y-m-d H:i:s');
		$sql = "INSERT INTO ExpBank (AccountID,Name,ExpSended,Date) VALUES ('$ldataaccount','$lname','$lexpsend','$currentDateTime')";
		if ($conn->query($sql) === TRUE) { echo nl2br("\n - Adicionado - \n"); } else { echo nl2br($sql); echo nl2br("\n - Falhou \n") . $conn->error;}		
		#$result = $conn->query($sql);
		$conn->close();	
		return;
	}

	#Recupera Exp
	if ($lrequest == "ExpGiver")
	{
		$currentDateTime = date('Y-m-d H:i:s');
		$currentDate = date('Y-m-d');
		$sql = "SELECT * FROM ExpBank WHERE AccountID <> '$ldataaccount' AND Date > '$currentDateTime' AND Date LIKE '$currentDate%'";
		$result = $conn->query($sql);
		if ($result === FALSE) { 
			echo nl2br($sql); 
			echo nl2br("\n - Falhou - \n") . $conn->error;
		} else {
			echo nl2br("\n - Recuperado - \n"); 
			if ($result->num_rows > 0) {
				// output data of each row
				while($row = $result->fetch_assoc()) {
					$lAll = "SYSTEMEXP - :Name:" . $row["Name"]. 
							":ExpSended:" .  $row["ExpSended"]. 
							": - \n";
					echo nl2br($lAll);
				}
			} else {
				echo "0 results";
			}
		}
	}

	#Adicionar Atk
	if ($lrequest == "Atk")
	{
		$sql = "UPDATE Mobs SET MobHp$lMobLetter = '$lHpMobAtual', MobTarget$lMobLetter = '$lName' where MobID$lMobLetter = '$lMobHitTarget'";
		echo($sql);
		$result = $conn->query($sql);
		$conn->close();	
	}

	#Sync Chats
	if ($lrequest == "SyncChats") {
		$sql = "SELECT * FROM Chats order by ChatID desc limit 5";
		$result = $conn->query($sql);
		if ($result === FALSE) { 
			echo nl2br($sql); 
			echo nl2br("\n - Falhou - \n") . $conn->error;
		} else {
			echo nl2br("\n - Recuperado - \n"); 
			if ($result->num_rows > 0) {
				// output data of each row
				while($row = $result->fetch_assoc()) {
					$lAll = "SYSTEMCHAT - :Name:" . $row["Name"]. 
							":Msg:" .  $row["Msg"]. 
							": - \n";
					echo nl2br($lAll);
				}
			} else {
				echo "0 results";
			}
		}
	}

	#Efetua Sync
	if ($lrequest == "SyncPlayer")
	{
		$sql = "REPLACE INTO Sync (AccountID, name, level, map, hp, mp, posX, posY, walk, weapon, frame, syncPlayerMob, setUpper, setBottom, setFooter, hair, sex, color, hat, side, job, playerInBattle, playerInAttack, playerInCast, playerSit, party, expShared) VALUES ('$ldataaccount','$lname', '$llevel', '$lmap', '$lhp', '$lmp', '$lposX', '$lposY', '$lwalk', '$lweapon', '$lframe', '$lsyncPlayerMob', '$lsetUpper', '$lsetBottom', '$lsetFooter', '$lhair', '$lsex', '$lcolor', '$lhat', '$lside', '$ljob', '$lplayerInBattle', '$lplayerInAttack', '$lplayerInCast', '$lplayerSit', '$lparty', '$lexpshared');";
		if ($conn->query($sql) === TRUE) { 
			echo nl2br("\n - Atualizado Player- \n"); 
		} else { 
			echo nl2br($sql); 
			echo nl2br("\n - Falhou Player - \n") . $conn->error;
		}

		$sql = "SELECT * from Sync";

		$result = $conn->query($sql);
		
		$lAll = '';
		if ($result->num_rows > 0) {
			// output data of each row
			while($row = $result->fetch_assoc()) {
				$lAll = $lAll . ("SYSTEMPLAYERS - :Name:" . $row["name"]. 
							  ":AccountID:" . $row["AccountID"].
                              ":Level:" .  $row["level"]. 
                              ":Map:" . $row["map"] .
                              ":Hp:" . $row["hp"] .
                              ":Mp:" . $row["mp"] .
                              ":PosX:" . $row["posX"] .
                              ":PosY:" . $row["posY"] .
                              ":Walk:" . $row["walk"] .
                              ":Weapon:" . $row["weapon"] .
                              ":Frame:" . $row["frame"] .
                              ":SyncPlayerMob:" . $row["syncPlayerMob"] . 
                              ":SetUpper:" . $row["setUpper"] . 
                              ":SetBottom:" . $row["setBottom"] . 
                              ":SetFooter:" . $row["setFooter"] . 
                              ":Hair:" . $row["hair"] . 
                              ":Sex:" . $row["sex"] . 
                              ":Color:" . $row["color"] . 
                              ":Hat:" . $row["hat"] . 
                              ":Side:" . $row["side"] . 
                              ":Job:" . $row["job"] .
                              ":PlayerInBattle:" . $row["playerInBattle"] . 
                              ":PlayerInAttack:" . $row["playerInAttack"] . 
                              ":PlayerInCast:" . $row["playerInCast"] .
                              ":PlayerSit:" . $row["playerSit"] .
                              ":Party:" . $row["party"] .
                              ":ExpShared:" . $row["expShared"] .
                              ": - \n");
				echo($lAll);
			}
		}
		$conn->close();	
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
			echo nl2br (":Atualizado:");
			
		}
		else
		{
			echo nl2br("Negado");
		}		
	}
?>