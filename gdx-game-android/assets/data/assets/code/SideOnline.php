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
	$lSyncPlayerMob = $_POST['lSyncPlayerMob'];
	
	
	#Variaveis de player
	$AccountID	= $_POST['lAccountID'];
	$Name = $_POST['lName'];
	$Sex = $_POST['lSex'];
	$Level = $_POST['lLevel'];
	$Map = $_POST['lMap'];
	$Exp = $_POST['lExp'];
	$Job = $_POST['lJob'];
	$Hp	= $_POST['lHp'];
	$Mp	= $_POST['lMp'];
	$Money = $_POST['lMoney'];
	$HpMax = $_POST['lHpMax'];
	$MpMax = $_POST['lMpMax'];
	$regenTime = $_POST['lregenTime'];
	$regenTimeMax = $_POST['lregenTimeMax'];
	$PosX = $_POST['lPosX'];
	$PosY = $_POST['lPosY'];
	$Walk = $_POST['lWalk'];
	$Frame = $_POST['lFrame'];
	$Target	= $_POST['lTarget'];
	$AtkTime = $_POST['lAtkTimer'];
	$AtkTimerMax = $_POST['lAtkTimerMax'];
	$Casting = $_POST['lCasting'];
	$Atk = $_POST['lAtk'];
	$Def = $_POST['lDef'];
	$Evasion = $_POST['lEvasion'];
	$Side = $_POST['lSide'];
	$Set = $_POST['lSet'];
	$Hair = $_POST['lHair'];
	$Color = $_POST['lColor'];
	$Hat = $_POST['lHat'];
	$Weapon	= $_POST['lWeapon'];
	$Crystal1 = $_POST['lCrystal1'];
	$Crystal2 = $_POST['lCrystal2'];
	$Crystal3 = $_POST['lCrystal3'];
	$Crystal4 = $_POST['lCrystal4'];
	$Crystal5 = $_POST['lCrystal5'];
	$StatusPoint = $_POST['lStatusPoint'];
	$Str = $_POST['lStr'];
	$Agi = $_POST['lAgi'];
	$Vit = $_POST['lVit'];
	$Dex = $_POST['lDex'];
	$Wis = $_POST['lWis'];
	$Luk = $_POST['lLuk'];
	$Res = $_POST['lRes'];
	$Stamina = $_POST['lStamina'];
	$StaminaMax	= $_POST['lStaminaMax'];
	$StaminaTimer = $_POST['lStaminaTimer'];
	$Itens = $_POST['lItens'];
	$Quests	= $_POST['lQuests'];
	$hotkey1 = $_POST['lhotkey1'];
	$hotkey2 = $_POST['lhotkey2'];
	$buffA = $_POST['lbuffA'];
	$BuffTimeA = $_POST['lBuffTimeA'];
	$buffB = $_POST['lbuffB'];
	$BuffTimeB = $_POST['lBuffTimeB'];
	$buffC = $_POST['lbuffC'];
	$BuffTimeC = $_POST['lBuffTimeC'];
	$party = $_POST['lparty'];
	$playerInBattle	= $_POST['lplayerInBattle'];
	$playerInAttack	= $_POST['lplayerInAttack'];
	$playerInCast = $_POST['lplayerInCast'];
	
	$lpet = $_POST['lpet'];
	$lpethungry = $_POST['lpethungry'];
	$lpetcare = $_POST['lpetcare'];
	$lpetTraining = $_POST['lpetTraining'];
	$lpetBath = $_POST['lpetBath'];
	$lpetLevel = $_POST['lpetLevel'];
	
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
	
	##UPLOAD-DB
	if ($lrequest == "Upload"){
		$sql = "REPLACE INTO SaveData (AccountID,Name,Sex,Level,Map,Exp,Job,Hp,Mp,Money,HpMax,MpMax,regenTime,regenTimeMax,PosX,PosY,Walk,Frame,Target,AtkTimer,Casting,Atk,Def,Evasion,Side,Set,Hair,Color,Hat,Weapon,Crystal1,Crystal2,Crystal3,Crystal4,Crystal5,StatusPoint,Str,Agi,Vit,Dex,Wis,Luk,Res,Stamina,StaminaMax,StaminaTimer,Itens,Quests,hotkey1,hotkey2,buffA,BuffTimeA,buffB,BuffTimeB,buffC,BuffTimeC,party,playerInBattle,playerInAttack,playerInCast) VALUES ('$lAccountID','$lName','$lSex','$l'$lMap','$lExp','$lJob','$lHp','$lMp','$lMoney','$lHpMax','$lMpMax','$lregenTime','$lregenTimeMax','$lPosX','$lPosY','$lWalk','$lFrame','$lTarget','$lAtkTimer','$lAtkTimerMax','$lCasting','$lAtk','$lDef','$lEvasion','$lSide','$lSet','$lHair','$lColor','$lHat','$lWeapon','$lCrystal1','$lCrystal2','$lCrystal3','$lCrystal4','$lCrystal5','$lStatusPoint','$lStr','$lAgi','$lVit','$lDex','$lWis','$lLuk','$lRes','$lStamina','$lStaminaMax','$lStaminaTimer','$lItens','$lQuests','$lhotkey1','$lhotkey2','$lbuffA','$lBuffTimeA','$lbuffB','$lBuffTimeB','$lbuffC','$lBuffTimeC','$lparty','$lplayerInBattle','$lplayerInAttack','$lplayerInCast');";
		$result = $conn->query($sql);
		if ($conn->query($sql) === TRUE) { echo nl2br("\n - Salvo - \n"); } else { echo nl2br($sql); echo nl2br("\n - Falhou - \n") . $conn->error;}
		
	}
	
	

	##UPLOAD
	if ($lrequest == "UploadFile"){
		
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