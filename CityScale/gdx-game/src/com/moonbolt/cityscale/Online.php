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
	
	$lbuffA = $_POST['lbuffA'];
	$lbuffB = $_POST['lbuffB'];
	$lbuffC = $_POST['lbuffC'];
	$lheal = $_POST['lheal'];
	$lexpshared = $_POST['lexpshared'];
	$ldamage = $_POST['ldamage'];
	
	$lsyncPlayerMob = $_POST['lsyncPlayerMob'];
	$lsyncMobInfo = $_POST['lsyncMobInfo'];
		
	#Variaveis de chat
	$lchat = $_POST['lchat'];
	
	#Variaveis de Combate
	$lmobID = $_POST['lmobID'];
	$ldmg = $_POST['ldmg'];
	$lmobAtkTargetSync = $_POST['lmobAtkTargetSync'];
	$ldmgMob = $_POST['ldmgMob'];
	
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
			
		//Verifica Reset Obrigatório
		$lAll = '';
		$sql = "SELECT * FROM Reset where AccountID = '$laccount' ";
		$result = $conn->query($sql);			
		if ($result->num_rows < 1) {
			$lAll = '';
			$sql = "INSERT INTO Reset (AccountID) VALUES ('$laccount')"; 
		
			if ($conn->query($sql) === TRUE) {
				echo "SYSTEMRESET \n";
			} else {
				echo "Error: " . $sql . "<br>" . $conn->error;
			}
		}
		
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
			
		/////Atualiza posicoes caso seja o player lead /////
		if($lsyncPlayerMob == "yes"){
			$sql = "UPDATE Mobs set MobsInfo = '$lsyncMobInfo'
												where MAP = '$lmap' ";
			$result = $conn->query($sql);
			if ($conn->query($sql) === TRUE) { echo nl2br("\n - Atualizado Pos Mobs - \n"); } else { echo nl2br("\n - Falhou Pos Mobs - \n") . $conn->error; }
		}
		
		//Atualização HP/Target dos mobs
		$lAll = '';
		$sql = "SELECT * FROM Mobs where MAP = '$lmap' ";
		$result = $conn->query($sql);			
		if ($result->num_rows > 0) {
			while($row = $result->fetch_assoc()) {								
			$lAll = $lAll . ("SYSTEMMOBS - :MobID=" . $row["MobsID"]. 
							  ":MobHpA=" .  $row["MobHpA"]. 
							  ":MobHpB=" .  $row["MobHpB"].
							  ":MobHpC=" .  $row["MobHpC"].
							  ":MobHpD=" .  $row["MobHpD"].
							  ":MobHpE=" .  $row["MobHpE"].
							  ":MobHpF=" .  $row["MobHpF"].
							  ":MobHpG=" . $row["MobHpG"] . 
							  ":TargetA=" . $row["TargetA"] . 
							  ":TargetB=" . $row["TargetB"] . 
							  ":TargetC=" . $row["TargetC"] . 
							  ":TargetD=" . $row["TargetD"] .
							  ":TargetE=" . $row["TargetE"] . 
							  ":TargetF=" . $row["TargetF"] .
							  ":TargetG=" . $row["TargetG"] .
							  ":MobsInfo=" . $row["MobsInfo"] .
							  ":MAP=" . $row["MAP"] .
							  ": - \n");			
			}		
		}
		echo($lAll);

		//Verifica se já está ativo
		$sql = "SELECT * FROM Processos where Account = '$ldata' and Name = '$lname' ";
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
										 Job = '$ljob',
										 Damage = '$ldamage',
										 BuffA = '$lbuffA',
										 BuffB = '$lbuffB',
										 BuffC = '$lbuffC',
										 Heal = '$lheal',
										 ExpShared = '$lexpshared',
										 SyncPlayerMob = '$lsyncPlayerMob'
										 where Account = '$laccount' ";			
			$result = $conn->query($sql);
			if ($conn->query($sql) === TRUE) { echo nl2br("\n - Atualizado - \n"); } else { echo nl2br("\n - Falhou Update - \n") . $conn->error; }
		}
		//Insere se não existir
		else
		{
			$lAll = '';
			$sql = "INSERT INTO Processos (Name,Sex,Level,Hp,Mp,CoordsX,CoordsY,Map,Hair,Hat,SetEquip,Party,Job,Damage,Walk,Pos,Weapon,Account,Side,Battle,BuffA,BuffB,BuffC,Heal,ExpShared,SyncPlayerMob) VALUES ('$lname','$lsex','$llevel','$lhp','$lmp','$lcoordsX','$lcoordsY','$lmap','$lhair','$lhat','$lsetEquip','$lparty','$ljob','$ldamage','$lwalk','$lpos','$lweapon','$laccount','$lside','$lbattle','$lbuffA','$lbuffB','$lbuffC','$lheal','$lexpshared','$lsyncPlayerMob')"; 
		
			if ($conn->query($sql) === TRUE) {
				echo "SYSTEMINSERT \n";
			} else {
				echo "Error: " . $sql . "<br>" . $conn->error;
			}			
		}
		
		echo "Vai rodar \n";
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
							  ":Damage=" . $row["Damage"] .
							  ":BuffA=" . $row["BuffA"] .
							  ":BuffB=" . $row["BuffB"] .
							  ":BuffC=" . $row["BuffC"] .
							  ":Heal=" . $row["Heal"] .
							  ":ExpShared=" . $row["ExpShared"] .							  
							  ":SyncPlayerMob=" . $row["SyncPlayerMob"] . 
							  ": - \n");
				echo($lAll);
			}
		}
		$conn->close();
		
		echo "Passou aqui \n";		
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
		
		return;
	}

	#Atk 
	if ($lrequest == "Atk"){
		$lAll = '';
		$sql = '';
		if($lmobAtkTargetSync == "MobA"){
			$sql = "UPDATE Mobs set TargetA = '$lname', MobHpA = '$ldmgMob' where MAP = '$lmap' ";  
			$result = $conn->query($sql);
			if ($conn->query($sql) === TRUE) { echo nl2br("\n - Atualizado MobA - \n"); } else { echo nl2br("\n - Falhou MobsA - \n") . $conn->error; }
		}
		if($lmobAtkTargetSync == "MobB"){
			$sql = "UPDATE Mobs set TargetB = '$lname', MobHpB = '$ldmgMob' where MAP = '$lmap' ";  
			$result = $conn->query($sql);
			if ($conn->query($sql) === TRUE) { echo nl2br("\n - Atualizado MobB - \n"); } else { echo nl2br("\n - Falhou MobsB - \n") . $conn->error; }
		}
		if($lmobAtkTargetSync == "MobC"){
			$sql = "UPDATE Mobs set TargetC = '$lname', MobHpC = '$ldmgMob' where MAP = '$lmap' ";  
			$result = $conn->query($sql);
			if ($conn->query($sql) === TRUE) { echo nl2br("\n - Atualizado MobC - \n"); } else { echo nl2br("\n - Falhou MobsC - \n") . $conn->error; }
		}
		if($lmobAtkTargetSync == "MobD"){
			$sql = "UPDATE Mobs set TargetD = '$lname', MobHpD = '$ldmgMob' where MAP = '$lmap' ";  
			$result = $conn->query($sql);
			if ($conn->query($sql) === TRUE) { echo nl2br("\n - Atualizado MobD - \n"); } else { echo nl2br("\n - Falhou MobsD - \n") . $conn->error; }
		}
		if($lmobAtkTargetSync == "MobE"){
			$sql = "UPDATE Mobs set TargetE = '$lname', MobHpE = '$ldmgMob' where MAP = '$lmap' ";  
			$result = $conn->query($sql);
			if ($conn->query($sql) === TRUE) { echo nl2br("\n - Atualizado MobE - \n"); } else { echo nl2br("\n - Falhou MobsE - \n") . $conn->error; }
		}
		if($lmobAtkTargetSync == "MobF"){
			$sql = "UPDATE Mobs set TargetF = '$lname', MobHpF = '$ldmgMob' where MAP = '$lmap' ";  
			$result = $conn->query($sql);
			if ($conn->query($sql) === TRUE) { echo nl2br("\n - Atualizado MobF - \n"); } else { echo nl2br("\n - Falhou MobsF - \n") . $conn->error; }
		}
		if($lmobAtkTargetSync == "MobG"){
			$sql = "UPDATE Mobs set TargetG = '$lname', MobHpG = '$ldmgMob' where MAP = '$lmap' ";  
			$result = $conn->query($sql);
			if ($conn->query($sql) === TRUE) { echo nl2br("\n - Atualizado MobG - \n"); } else { echo nl2br("\n - Falhou MobsG - \n") . $conn->error; }
		}
		
		$conn->close(); 
		
		return;
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
		
		return;
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
