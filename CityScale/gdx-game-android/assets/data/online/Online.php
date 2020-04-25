<?php

	#Variaveis Recebidas do Cliente
	$ldata = $_POST['ldata'];
	$lrequest = $_POST['lrequest'];
	$lversion = $_POST['lversion'];
	
	#Variaveis de Atualizacao
	$lnome = $_POST['lnome'];
	$lhp = $_POST['lhp'];
	$lmp = $_POST['lmp'];
	$lposX = $_POST['lposX'];
	$lposY = $_POST['lposY'];
	$lwalk = $_POST['lwalk'];
	$lsex = $_POST['lsex'];
	$lmap = $_POST['lmap'];
	$ljob = $_POST['ljob'];
	$llevel = $_POST['llevel'];
	$lsetchar = $_POST['lsetchar'];
	$lhair = $_POST['lhair'];
	$lhat = $_POST['lhat'];
	$lweapon = $_POST['lweapon'];
	$lbattle = $_POST['lbattle'];
	$lside = $_POST['lside'];
	$lpos = $_POST['lpos'];
	$lskillOnline = $_POST['lskillOnline'];
	$laccount = $_POST['laccount'];
	$lparty = $_POST['lparty'];
	$lchat = $_POST['lchat'];
	$lmobID = $_POST['lmobID'];
	$ldmg = $_POST['ldmg'];
	$lexpshared = $_POST['lexpshared'];
	
	#Variaveis de uso Global
	$lAll = '';
	$lQueryEx = '';
	$lnomeplayer = '';
	
	#Variaveis de Banco
	$servername = $_POST['lservername'];
	$username = $_POST['lusername'];
	$password = $_POST['lpassword'];
	$dbname = $_POST['ldbname'];

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
	if ($lrequest == "Sincronizar"){
		
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
		$lAll = '';
		$sql = "SELECT * FROM Mobs where MAP = '$lmap' ";
		$result = $conn->query($sql);			
		if ($result->num_rows > 0) {
			while($row = $result->fetch_assoc()) {
									
				$lAll = $lAll . ("SYSTEMMOBS - :MOBID=" . $row["MobsID"]. 
							  ":GameMobID=" .  $row["GameMobID"]. 
							  ":MOBHP=" . $row["HPMob"] . 
							  ":MAP=" . $row["MAP"] .
							  ": - \n");			
			}		
		}
		echo($lAll);
		
		/////Recupera EXPSHARED /////
		$lAll = '';
		$sql = "SELECT * FROM PartyExp where Party = '$lparty' ORDER BY ExpSharedID DESC LIMIT 1";
		$result = $conn->query($sql);			
		if ($result->num_rows > 0) {
			while($row = $result->fetch_assoc()) {
									
				$lAll = $lAll . ("SYSTEMPARTYEXP - :EXPSHAREDID=" . $row["ExpSharedID"]. 
							  ":EXPSEND=" .  $row["ExpSend"]. 
							  ":PARTY=" . $row["Party"] . 
							  ":ACCOUNT=" . $row["Account"] . 
							  ": - \n");			
			}		
		}
		echo($lAll);
		
		//echo nl2br($lAll);
		
		//Verifica se já está ativo
		$sql = "SELECT * FROM Processos where ACCOUNT = '$ldata' ";
		$result = $conn->query($sql);
		if ($result->num_rows > 0) {			
			$sql = "UPDATE Processos set NOME = '$lnome',
										 HP = '$lhp',
										 MP = '$lmp',
										 POSX = '$lposX',
										 POSY = '$lposY',
										 MAP = '$lmap',
										 LEVEL = '$llevel',
										 SETCHAR = '$lsetchar',
										 HAIR = '$lhair',
										 HAT = '$lhat',
										 WEAPON = '$lweapon',
										 BATTLE = '$lbattle',
										 SIDE = '$lside',
										 POS = '$lpos',
										 WALK = '$lwalk',
										 SKILLONLINE = '$lskillOnline',
										 ACCOUNT = '$ldata',
										 PARTY = '$lparty',
										 SEX = '$lsex',
										 JOB = '$ljob',
										 EXPSHARED = '$lexpshared'
										 where ACCOUNT = '$ldata' ";			
			$result = $conn->query($sql);
			if ($conn->query($sql) === TRUE) { echo nl2br("\n - Atualizado - \n"); } else { echo nl2br("\n - Falhou Update - \n") . $conn->error; }
		}
		//Insere se não existir
		else
		{
			$lAll = '';
			$sql = 
			"INSERT INTO Processos (NOME, HP, MP, POSX, POSY, MAP, LEVEL, SETCHAR, HAIR, HAT, WEAPON, BATTLE, SIDE, POS, WALK, SKILLONLINE, ACCOUNT, PARTY, SEX, JOB, EXPSHARED)  VALUES 
			('$lnome', '$lhp', '$lmp', '$lposX', '$lposY', '$lmap', '$llevel', '$lsetchar','$lhair', '$lhat', '$lweapon', '$lbattle' ,'$lside', '$lpos', '$lwalk', '$lskillOnline','$ldata', '$lparty', '$lsex', '$ljob', '$lexpshared') ";
			
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
									
				$lAll = $lAll . ("SYSTEMPLAYERS - :NOME=" . $row["NOME"]. 
							  ":HP=" .  $row["HP"]. 
							  ":MP=" . $row["MP"] . 
							  ":POSX=" . $row["POSX"] .
							  ":POSY=" . $row["POSY"] .
							  ":MAP=" . $row["MAP"] .
							  ":LEVEL=" . $row["LEVEL"] .
							  ":SETCHAR=" . $row["SETCHAR"] .
							  ":HAIR=" . $row["HAIR"] .
							  ":HAT=" . $row["HAT"] .
							  ":WEAPON=" . $row["WEAPON"] .
							  ":BATTLE=" . $row["BATTLE"] .
							  ":SIDE=" . $row["SIDE"] .
							  ":POS=" . $row["POS"] .
							  ":WALK=" . $row["WALK"] .
							  ":SKILLONLINE=" . $row["SKILLONLINE"] .
							  ":ACCOUNT=" . $row["ACCOUNT"] .
							  ":PARTY=" . $row["PARTY"] .
							  ":SEX=" . $row["SEX"] .
							  ":JOB=" . $row["JOB"] .   
							  ":EXPSHARED=" . $row["EXPSHARED"] .
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
						        VALUES ('$lnome','$lchat') ";
								
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
