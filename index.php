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
$lcharnumber = $_POST['lcharnumber'];

#Variaveis de Uso
$lAll = '';
$lchat = $_POST['lchat'];

$llevel = $_POST['llevel'];
$lname = $_POST['lname'];
$lsex = $_POST['lsex'];
$lhair = $_POST['lhair'];
$lcolor = $_POST['lcolor'];
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
$lplayerIDEXP = $_POST['lplayerIDEXP'];
$itensList = $_POST['litensList'];

#\n  (Quebra Linha)

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
	die("Resultado: - Falhou na Conexão com Banco -" . $conn->connect_error);
}


# Check New Account
if ($lrequest == "NewAccount") {
	$sql = "SELECT * FROM Accounts WHERE AccountNumber = '$ldataaccount'";
	$result = $conn->query($sql);
	if ($result === FALSE) {
		echo nl2br($sql);
		echo nl2br("\n - fail - \n") . $conn->error;
	} else {
		echo nl2br("\n - Recuperado - \n");
		if ($result->num_rows == 0) {
			// Insert three new accounts with specific values
			for ($i = 1; $i <= 3; $i++) {
				$insert_sql = "INSERT INTO Accounts (
					AccountNumber, Characternumber, Name
				) VALUES (
					'$ldataaccount', '$i', 'none'
				)";
				if ($conn->query($insert_sql) === TRUE) {
					echo "Account $i created\n";
				} else {
					echo "fail: " . $insert_sql . "<br>" . $conn->error;
				}
			}
		} else {
			echo "fail";
		}
	}
}

if ($lrequest == "LoadData") {
	$sql = "SELECT * FROM Accounts WHERE AccountNumber = '$ldataaccount' AND characternumber = '$lcharnumber'";
	$result = $conn->query($sql);
	if ($result === FALSE) {
		echo nl2br($sql);
		echo nl2br("\n - fail - \n") . $conn->error;
	} else {
		echo nl2br("\n - Recuperado - \n");
		if ($result->num_rows > 0) {
			// output data of each row
			while ($row = $result->fetch_assoc()) {
				$lAll = "SYSTEMACCOUNT - :AccountID:" . $row["AccountID"] .
					":AccountNumber:" . $row["AccountNumber"] .
					":Characternumber:" . $row["Characternumber"] .
					":Name:" . $row["Name"] .
					":Sex:" . $row["Sex"] .
					":Hair:" . $row["Hair"] .
					":Color:" . $row["Color"] .
					":Hat:" . $row["Hat"] .
					":Job:" . $row["Job"] .
					":SetUpper:" . $row["SetUpper"] .
					":SetBottom:" . $row["SetBottom"] .
					":SetFooter:" . $row["SetFooter"] .
					":Level:" . $row["Level"] .
					":Exp:" . $row["Exp"] .
					":Map:" . $row["Map"] .
					":Hp:" . $row["Hp"] .
					":Mp:" . $row["Mp"] .
					":Money:" . $row["Money"] .
					":HpMax:" . $row["HpMax"] .
					":MpMax:" . $row["MpMax"] .
					":regenTime:" . $row["regenTime"] .
					":regenTimeMax:" . $row["regenTimeMax"] .
					":PosX:" . $row["PosX"] .
					":PosY:" . $row["PosY"] .
					":Walk:" . $row["Walk"] .
					":Frame:" . $row["Frame"] .
					":countFrame:" . $row["countFrame"] .
					":breakwalk:" . $row["breakwalk"] .
					":Target:" . $row["Target"] .
					":AtkTimer:" . $row["AtkTimer"] .
					":AtkTimerMax:" . $row["AtkTimerMax"] .
					":Casting:" . $row["Casting"] .
					":Atk:" . $row["Atk"] .
					":Def:" . $row["Def"] .
					":Evasion:" . $row["Evasion"] .
					":Side:" . $row["Side"] .
					":Weapon:" . $row["Weapon"] .
					":Crystal1:" . $row["Crystal1"] .
					":Crystal2:" . $row["Crystal2"] .
					":Crystal3:" . $row["Crystal3"] .
					":Crystal4:" . $row["Crystal4"] .
					":StatusPoint:" . $row["StatusPoint"] .
					":Str:" . $row["Str"] .
					":Agi:" . $row["Agi"] .
					":Vit:" . $row["Vit"] .
					":Dex:" . $row["Dex"] .
					":Wis:" . $row["Wis"] .
					":Luk:" . $row["Luk"] .
					":Res:" . $row["Res"] .
					":Stamina:" . $row["Stamina"] .
					":StaminaMax:" . $row["StaminaMax"] .
					":Itens:" . $row["Itens"] .
					":Quests:" . $row["Quests"] .
					":hotkey1:" . $row["hotkey1"] .
					":hotkey2:" . $row["hotkey2"] .
					":buffA:" . $row["buffA"] .
					":buffB:" . $row["buffB"] .
					":buffC:" . $row["buffC"] .
					":BuffTimeA:" . $row["BuffTimeA"] .
					":BuffTimeB:" . $row["BuffTimeB"] .
					":BuffTimeC:" . $row["BuffTimeC"] .
					":party:" . $row["party"] .
					":playerInBattle:" . $row["playerInBattle"] .
					":playerInAttack:" . $row["playerInAttack"] .
					":playerInCast:" . $row["playerInCast"] .
					":playerSit:" . $row["playerSit"] .
					":SyncPlayerMob:" . $row["SyncPlayerMob"] .
					":PlayerExpGet:" . $row["PlayerExpGet"] .
					":pet:" . $row["pet"] .
					":pethungry:" . $row["pethungry"] .
					":petcare:" . $row["petcare"] .
					":petTraining:" . $row["petTraining"] .
					":petBath:" . $row["petBath"] .
					":petLevel:" . $row["petLevel"] . ": - \n";
				echo nl2br($lAll);
			}
		} else {
			echo "0 results";
		}
	}
}

#Create new Character
if ($lrequest == "CreateChar") {
	$sql = "SELECT * FROM Accounts WHERE AccountNumber = '$ldataaccount' AND Characternumber = '$lcharnumber'";
	$result = $conn->query($sql);
	if ($result === FALSE) {
		echo nl2br($sql);
		echo nl2br("\n - fail Select - \n") . $conn->error;
	} else {
		echo nl2br("\n - Conta Recuperada - \n");
		if ($result->num_rows == 1) {
			$update_sql = "UPDATE Accounts SET
                Name = '$lname',
                Sex = '$lsex',
                Hair = '$lhair',
                Color = '$lcolor',
                Hat = 'none',
                Job = 'Aprendiz',
                SetUpper = 'basictop',
                SetBottom = 'basicbottom',
                SetFooter = 'basicfooter',
                Level = 1,
                Exp = 0,
                Map = 'MetroStation',
                Hp = 30,
                Mp = 20,
                Money = 50,
                HpMax = 30,
                MpMax = 20,
                regenTime = 6000,
                regenTimeMax = 6000,
                PosX = 0,
                PosY = 0,
                Walk = 'no',
                Frame = 1,
                countFrame = 1,
                breakwalk = 'none',
                Target = 'none',
                AtkTimer = 300,
                AtkTimerMax = 300,
                Casting = 'no',
                Atk = 5,
                Def = 1,
                Evasion = 0,
                Side = 'front',
                Weapon = 'basicknife',
                Crystal1 = 'none',
                Crystal2 = 'none',
                Crystal3 = 'none',
                Crystal4 = 'none',
                StatusPoint = 0,
                Str = 1,
                Agi = 1,
                Vit = 1,
                Dex = 1,
                Wis = 1,
                Stamina = 100,
                StaminaMax = 100,
                Quests = 'none',
                hotkey1 = 'none',
                hotkey2 = 'none',
                buffA = 'none',
                buffB = 'none',
                buffC = 'none',
                BuffTimeA = 0,
                BuffTimeB = 0,
                BuffTimeC = 0,
                party = 'none',
                playerInBattle = 'none',
                playerInAttack = 'none',
                playerInCast = 'none',
                playerSit = 'none',
                SyncPlayerMob = 'none',
                PlayerExpGet = '0',
                Itens = '$itensList',
				pet = 'none',
				pethungry = 'none',
				petcare = 'none',
				petTraining = 'none',
				petBath = 'none',
				petLevel = 'none'
            WHERE
                AccountNumber = '$ldataaccount' AND
                Characternumber = '$lcharnumber'";

			// Echo the update SQL query
			echo nl2br("Update SQL: " . $update_sql . "\n");

			if ($conn->query($update_sql) === TRUE) {
				echo "Character updated\n";
			} else {
				echo "fail Update: " . $update_sql . "<br>" . mysqli_error($conn);
			}
		} else {
			echo "fail";
		}
	}
}


#Check Account 
if ($lrequest == "CheckAccount") {
	$sql = "SELECT * FROM Accounts where AccountNumber = '$ldataaccount'";
	$result = $conn->query($sql);
	if($result->num_rows > 0) {
		echo nl2br("success");
	} else {
		echo nl2br("fail");
	}
}

#Delete Char
if ($lrequest == "DeleteChar") {
    $sql = "SELECT * FROM Accounts WHERE AccountNumber = '$ldataaccount'";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
        $update_sql = "UPDATE Accounts SET Name = 'none' WHERE AccountNumber = '$ldataaccount' AND Characternumber = '$lcharnumber'";
        
        // Echo the update SQL query for debugging
        echo nl2br("Update SQL: " . $update_sql . "\n");

        if ($conn->query($update_sql) === TRUE) {
            echo nl2br("Character name updated to 'none'\n");
        } else {
            echo "fail: " . $update_sql . "<br>" . mysqli_error($conn);
        }
    } else {
        echo nl2br("fail");
    }
}

#Efetua Login
if ($lrequest == "CheckVersion") {
	$sql = "SELECT * FROM VersionControl";
	$result = $conn->query($sql);

	$lAll = '';
	if ($result->num_rows > 0) {
		// output data of each row
		while ($row = $result->fetch_assoc()) {
			if ($row["descricao"] == $lversion) {
				echo nl2br("Autorizado");
			}
			if ($row["descricao"] != $lversion) {
				echo nl2br("Probido");
			}
		}
	}

	$conn->close();
	return;
}

#Adicionar Chat
if ($lrequest == "Chat") {
	$sql = "INSERT INTO Chats (AccountID,Name,Msg) VALUES ('$ldataaccount','$lname','$lchat')";
	if ($conn->query($sql) === TRUE) {
		echo nl2br("\n - Adicionado - \n");
	} else {
		echo nl2br($sql);
		echo nl2br("\n - Falhou \n") . $conn->error;
	}
	#$result = $conn->query($sql);
	$conn->close();
	return;
}

#Adiciona Exp
if ($lrequest == "ExpSharedSend") {
	$currentDateTime = date('Y-m-d H:i:s');
	$sql = "INSERT INTO ExpBank (AccountID,Name,ExpSended,Date) VALUES ('$ldataaccount','$lname','$lexpsend','$currentDateTime')";
	if ($conn->query($sql) === TRUE) {
		echo nl2br("\n - Adicionado - \n");
	} else {
		echo nl2br($sql);
		echo nl2br("\n - Falhou \n") . $conn->error;
	}
	#$result = $conn->query($sql);
	$conn->close();
	return;
}

#Recupera Exp
if ($lrequest == "ExpGiver") {
	$datenow = date('Y-m-d');
	$startOfDay = $datenow . ' 00:00:00';
	$endOfDay = $datenow . ' 23:59:59';

	$sql = "SELECT * FROM ExpBank WHERE AccountID <> '$ldataaccount' AND ExpBankID > '$lplayerIDEXP'";
	$result = $conn->query($sql);
	if ($result === FALSE) {
		echo nl2br($sql);
		echo nl2br("\n - Falhou - \n") . $conn->error;
	} else {
		echo nl2br("\n - Recuperado - \n");
		if ($result->num_rows > 0) {
			// output data of each row
			while ($row = $result->fetch_assoc()) {
				$lAll = "SYSTEMEXP - :Name:" . $row["Name"] .
					":ExpSended:" . $row["ExpSended"] .
					":ExpBankID:" . $row["ExpBankID"] .
					": - \n";
				echo nl2br($lAll);
			}
		} else {
			echo "0 results";
		}
	}
}

#Adicionar Atk
if ($lrequest == "Atk") {
	$sql = "UPDATE Mobs SET MobHp$lMobLetter = '$lHpMobAtual', MobTarget$lMobLetter = '$lName' where MobID$lMobLetter = '$lMobHitTarget'";
	echo ($sql);
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
			while ($row = $result->fetch_assoc()) {
				$lAll = "SYSTEMCHAT - :Name:" . $row["Name"] .
					":Msg:" . $row["Msg"] .
					": - \n";
				echo nl2br($lAll);
			}
		} else {
			echo "0 results";
		}
	}
}

#Efetua Sync
if ($lrequest == "SyncPlayer") {
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
		while ($row = $result->fetch_assoc()) {
			$lAll = $lAll . ("SYSTEMPLAYERS - :Name:" . $row["name"] .
				":AccountID:" . $row["AccountID"] .
				":Level:" . $row["level"] .
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
			echo ($lAll);
		}
	}
	$conn->close();
}

##UPLOAD
if ($lrequest == "Upload") {

	$arquivo = $ldataaccount;
	$file = fopen($arquivo, 'w');
	if (fwrite($file, $ldata)) {
		echo nl2br("Atualizado");
		fclose($file);
	} else {
		echo nl2br("Negado");
	}

}

##DOWNLOAD
if ($lrequest == "Download") {

	if (file_exists($ldataaccount)) {
		$arquivo = fopen($ldataaccount, "r");
		while (!feof($arquivo)) {
			$linha = fgets($arquivo, 4096);
			echo nl2br($linha);
		}
		fclose($arquivo);
		echo nl2br(":Atualizado:");

	} else {
		echo nl2br("Negado");
	}
}
?>