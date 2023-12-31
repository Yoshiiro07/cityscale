<?php

	#Variaveis Recebidas do Cliente
	$ldata = $_POST['ldata'];
	$lrequest = $_POST['lrequest'];
	$lIDLocal = $_POST['lIDLocal'];
	$lChat = $_POST['lChat'];
	$lplayersync = $_POST['lplayersync'];
	
	#Variaveis de Atualizacao
	$lnome = $_POST['lnome'];
	$lversion = $_POST['lversion'];
	
	$lAccountID = $_POST['lAccountID'];
	$lName = $_POST['lName'];
	$lLevel = $_POST['lLevel'];
	$lMap = $_POST['lMap'];
	$lHp = $_POST['lHp'];
	$lMp = $_POST['lMp'];
	$lPosX = $_POST['lPosX'];
	$lPosY = $_POST['lPosY'];
	$lWalk = $_POST['lWalk'];
	$lWeapon = $_POST['lWeapon'];
	$lFrame = $_POST['lFrame'];
	$lExp = $_POST['lExp'];
	$lParty = $_POST['lParty'];
	$lSyncPlayerMob = $_POST['lSyncPlayerMob'];
	$lPlayerSet = $_POST['lPlayerSet'];
	$lHair = $_POST['lHair'];
	$lSex = $_POST['lSex'];
	$lColor = $_POST['lColor'];
	$lHat = $_POST['lHat'];
	$lSide = $_POST['lSide'];
	$lJob = $_POST['lJob'];
	$lplayerInBattle = $_POST['lplayerInBattle'];
	$lplayerInAttack = $_POST['lplayerInAttack'];
	$lplayerInCast = $_POST['lplayerInCast'];
	
	$lMobA = $_POST['lMobA'];
	$lMobAPosX = $_POST['lMobAPosX'];
	$lMobAPosY = $_POST['lMobAPosY'];
	
	$lMobB = $_POST['lMobB'];
	$lMobBPosX = $_POST['lMobBPosX'];
	$lMobBPosY = $_POST['lMobBPosY'];
	
	$lMobC = $_POST['lMobC'];
	$lMobCPosX = $_POST['lMobCPosX'];
	$lMobCPosY = $_POST['lMobCPosY'];
	
	$lMobD = $_POST['lMobD'];
	$lMobDPosX = $_POST['lMobDPosX'];
	$lMobDPosY = $_POST['lMobDPosY'];

	$lHpMobAtual = $_POST['lHpMobAtual'];
	$lMobHitTarget = $_POST['lMobHitTarget'];
	$lMobLetter = $_POST['lMobLetter'];
		
	$lAll = '';
	
	#Variaveis de Banco
	$servername = $_POST['lservername'];
	$username = $_POST['lusername'];
	$password = $_POST['lpassword'];
	$dbname = $_POST['ldbname'];

	
	#\n  (Quebra Linha)
	
	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);
	
	// Check connection
	if ($conn->connect_error) {
		die("Resultado: - Falhou na Conexão com Banco -" . $conn->connect_error);
		return;
	}
	
	#Efetua Login
	if ($lrequest == "CheckVersion")
	{
		$sql = "SELECT * FROM VersionControl where descricao = '1A';";
		$result = $conn->query($sql);
		
		$lAll = '';
		if ($result->num_rows > 0) {
			// output data of each row
			while($row = $result->fetch_assoc()) {
				if($row["descricao"] == $lversion){
					echo nl2br("Autorizado");
				}
				else{
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
		$sql = "INSERT INTO Chats (AccountID,Name,Msg) VALUES ('$lAccountID','$lName','$lChat')";
		if ($conn->query($sql) === TRUE) { echo nl2br("\n - Adicionado Chat - \n"); } else { echo nl2br($sql); echo nl2br("\n - Falhou Add Chat - \n") . $conn->error;}		
		$result = $conn->query($sql);
		$conn->close();	
		return;
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
	if ($lrequest == "SyncChats")
	{
	$sql = "SELECT * FROM Chats order by ChatID desc limit 3";
		$result = $conn->query($sql);
		if ($conn->query($sql) === TRUE) { echo nl2br("\n - Recuperado Chats - \n"); } else { echo nl2br($sql); echo nl2br("\n - Falhou Chats - \n") . $conn->error;}
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
	
	#Efetua Sync
	if ($lrequest == "SyncPlayer")
	{
		$sql = "REPLACE INTO Sync (AccountID, Name,Level,Map,Hp,Mp,PosX,PosY,Walk,Weapon,Frame,Exp,Party,SyncPlayerMob,PlayerSet,Hair,Sex,Color,Hat,Side,Job,playerInBattle,playerInAttack,playerInCast) VALUES ('$lAccountID','$lName','$lLevel','$lMap','$lHp','$lMp','$lPosX','$lPosY','$lWalk','$lWeapon','$lFrame','$lExp','$lParty','$lSyncPlayerMob','$lPlayerSet','$lHair','$lSex','$lColor','$lHat','$lSide','$lJob','$lplayerInBattle','$lplayerInAttack','$lplayerInCast');";
		$result = $conn->query($sql);
		if ($conn->query($sql) === TRUE) { echo nl2br("\n - Atualizado Player- \n"); } else { echo nl2br($sql); echo nl2br("\n - Falhou Player - \n") . $conn->error;}
		
		$sql = "SELECT * from Sync";
		$result = $conn->query($sql);
		
		$lAll = '';
		if ($result->num_rows > 0) {
			// output data of each row
			while($row = $result->fetch_assoc()) {
				$lAll = $lAll . ("SYSTEMPLAYERS - :AccountID:" . $row["AccountID"]. 
							  ":Name:" .  $row["Name"]. 
							  ":Level:" . $row["Level"] . 
							  ":Map:" . $row["Map"] .
							  ":Hp:" . $row["Hp"] .
							  ":Mp:" . $row["Mp"] .
							  ":PosX:" . $row["PosX"] .
							  ":PosY:" . $row["PosY"] .
							  ":Walk:" . $row["Walk"] .
							  ":Weapon:" . $row["Weapon"] .
							  ":Frame:" . $row["Frame"] .
							  ":Exp:" . $row["Exp"] .
							  ":Party:" . $row["Party"] .		  
							  ":SyncPlayerMob:" . $row["SyncPlayerMob"] . 
							  ":PlayerSet:" . $row["PlayerSet"] . 
							  ":Hair:" . $row["Hair"] . 
							  ":Sex:" . $row["Sex"] . 
							  ":Color:" . $row["Color"] . 
							  ":Hat:" . $row["Hat"] . 
							  ":Side:" . $row["Side"] . 
							  ":Job:" . $row["Job"] .
							  ":playerInBattle:" . $row["playerInBattle"] . 
							  ":playerInAttack:" . $row["playerInAttack"] . 
							  ":playerInCast:" . $row["playerInCast"] .
							  ": - \n");
				echo($lAll);
			}
		}
		$conn->close();	
	}
	
	##Sync Mob
	if ($lrequest == "SyncMob"){	
		
		if($lplayersync == "playerMobSync"){	
			$sql = "UPDATE Mobs SET MobPosXA = '$lMobAPosX', MobPosYA = '$lMobAPosY', MobPosXB = '$lMobBPosX', MobPosYB = '$lMobBPosY', MobPosXC = '$lMobCPosX', MobPosYC = '$lMobCPosY', MobPosXD = '$lMobDPosX', MobPosYD = '$lMobDPosY' where Map = '$lMap'";
			$result = $conn->query($sql);
			
			echo nl2br($sql);
			echo nl2br("\n - Passou - \n");
		
			$lplayersync = "playerMobSyncNot";
		}
		
		
		
		if($lplayersync == "playerMobSyncNot"){	 
		$sql = "SELECT * FROM Mobs where Map = '$lMap';";
		$result = $conn->query($sql);
		if ($conn->query($sql) === TRUE) { echo nl2br("\n - Recuperado Mobs - \n"); } else { echo nl2br($sql); echo nl2br("\n - Falhou Mobs - \n") . $conn->error;}
			if ($result->num_rows > 0) {
				// output data of each row
				while($row = $result->fetch_assoc()) {
					$lAll = $lAll . ("SYSTEMMOB - :MobIDA:" . $row["MobIDA"]. 
								  ":MobHpA :" .  $row["MobHpA"]. 
								  ":MobMpA:" .  $row["MobMpA"]. 
								  ":MobPosXA:" .  $row["MobPosXA"]. 
								  ":MobPosYA:" .  $row["MobPosYA"]. 
								  ":MobTargetA:" .  $row["MobTargetA"]. 
								  ":MobDeadA:" .  $row["MobDeadA"]. 
								  ":MobTimeDeadA:" .  $row["MobTimeDeadA"]. 
								  ":MobIDB:" .  $row["MobIDB"]. 
								  ":MobHpB:" .  $row["MobHpB"]. 
								  ":MobMpB:" .  $row["MobMpB"]. 
								  ":MobPosXB:" .  $row["MobPosXB"]. 
								  ":MobPosYB:" .  $row["MobPosYB"]. 
								  ":MobTargetB:" .  $row["MobTargetB"]. 
								  ":MobDeadB:" .  $row["MobDeadB"]. 
								  ":MobTimeDeadB:" .  $row["MobTimeDeadB"]. 
								  ":MobIDC:" .  $row["MobIDC"]. 
								  ":MobHpC:" .  $row["MobHpC"]. 
								  ":MobMpC:" .  $row["MobMpC"]. 
								  ":MobPosXC:" .  $row["MobPosXC"]. 
								  ":MobPosYC:" .  $row["MobPosYC"]. 
								  ":MobTargetC:" .  $row["MobTargetC"]. 
								  ":MobDeadC:" .  $row["MobDeadC"]. 
								  ":MobTimeDeadC:" .  $row["MobTimeDeadC"]. 
								  ":MobIDD:" .  $row["MobIDD"]. 
								  ":MobHpD:" .  $row["MobHpD"]. 
								  ":MobMpD:" .  $row["MobMpD"]. 
								  ":MobPosXD:" .  $row["MobPosXD"]. 
								  ":MobPosYD:" .  $row["MobPosYD"]. 
								  ":MobTargetD:" .  $row["MobTargetD"]. 
								  ":MobDeadD:" .  $row["MobDeadD"]. 
								  ":MobTimeDeadD:" .  $row["MobTimeDeadD"]. 
								  ":Map:" .  $row["Map"]. 
								  ": - \n");
					echo($lAll);
				}
			}	 
		}	 
	}
	
	##UPLOAD
	if ($lrequest == "Upload"){
		
		$arquivo = $lAccountID + '.txt';
		$file = fopen($arquivo, 'w');	
		if(fwrite($file, $ldata)){
			echo nl2br("Resultado: \n - Upload com Sucesso- \n");
		}
		fclose($file);
	}
	
	##DOWNLOAD
	if ($lrequest == "Download"){
		
		if (file_exists($lAccountID)) {
			$arquivo = fopen ($lAccountID,"r");
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