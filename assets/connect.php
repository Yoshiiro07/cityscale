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

$AccountID = $_POST['AccountID'];
$AccountNumber = $_POST['AccountNumber'];
$Characternumber = $_POST['Characternumber'];
$Name = $_POST['Name'];
$Sex = $_POST['Sex'];
$Hair = $_POST['Hair'];
$Color = $_POST['Color'];
$Hat = $_POST['Hat'];
$Job = $_POST['Job'];
$SetUpper = $_POST['SetUpper'];
$SetBottom = $_POST['SetBottom'];
$SetFooter = $_POST['SetFooter'];
$Level = $_POST['Level'];
$Exp = $_POST['Exp'];
$Map = $_POST['Map'];
$Hp = $_POST['Hp'];
$Mp = $_POST['Mp'];
$Money = $_POST['Money'];
$HpMax = $_POST['HpMax'];
$MpMax = $_POST['MpMax'];
$regenTime = $_POST['regenTime'];
$regenTimeMax = $_POST['regenTimeMax'];
$PosX = $_POST['PosX'];
$PosY = $_POST['PosY'];
$Walk = $_POST['Walk'];
$Frame = $_POST['Frame'];
$countFrame = $_POST['countFrame'];
$breakwalk = $_POST['breakwalk'];
$Target = $_POST['Target'];
$AtkTimer = $_POST['AtkTimer'];
$AtkTimerMax = $_POST['AtkTimerMax'];
$Casting = $_POST['Casting'];
$Atk = $_POST['Atk'];
$Def = $_POST['Def'];
$Evasion = $_POST['Evasion'];
$Side = $_POST['Side'];
$Weapon = $_POST['Weapon'];
$Crystal1 = $_POST['Crystal1'];
$Crystal2 = $_POST['Crystal2'];
$Crystal3 = $_POST['Crystal3'];
$Crystal4 = $_POST['Crystal4'];
$StatusPoint = $_POST['StatusPoint'];
$Str = $_POST['Str'];
$Agi = $_POST['Agi'];
$Vit = $_POST['Vit'];
$Dex = $_POST['Dex'];
$Wis = $_POST['Wis'];
$Luk = $_POST['Luk'];
$Res = $_POST['Res'];
$Stamina = $_POST['Stamina'];
$StaminaMax = $_POST['StaminaMax'];
$Itens = $_POST['Itens'];
$Quests = $_POST['Quests'];
$hotkey1 = $_POST['hotkey1'];
$hotkey2 = $_POST['hotkey2'];
$buffA = $_POST['buffA'];
$buffB = $_POST['buffB'];
$buffC = $_POST['buffC'];
$BuffTimeA = $_POST['BuffTimeA'];
$BuffTimeB = $_POST['BuffTimeB'];
$BuffTimeC = $_POST['BuffTimeC'];
$party = $_POST['party'];
$playerInBattle = $_POST['playerInBattle'];
$playerInAttack = $_POST['playerInAttack'];
$playerInCast = $_POST['playerInCast'];
$playerSit = $_POST['playerSit'];
$SyncPlayerMob = $_POST['SyncPlayerMob'];
$PlayerExpGet = $_POST['PlayerExpGet'];
$pet = $_POST['pet'];
$pethungry = $_POST['pethungry'];
$petcare = $_POST['petcare'];
$petTraining = $_POST['petTraining'];
$petBath = $_POST['petBath'];
$petLevel = $_POST['petLevel'];

$isPlayerOnline = $_POST['lisPlayerOnline'];

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
    $sql = "SELECT * FROM Accounts WHERE AccountNumber = '$ldataaccount'"; // AND characternumber = '$lcharnumber'";
    $result = $conn->query($sql);
    if ($result === FALSE) {
        echo nl2br($sql);
        echo nl2br("\n - fail - \n") . $conn->error;
    } else {
        if ($result->num_rows > 0) {
            // output data of each row
            while ($row = $result->fetch_assoc()) {
                $lAll = "#Success# AccountID:" . $row["AccountID"] .
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
                    ":petLevel:" . $row["petLevel"] . ":@";
                echo nl2br($lAll);
            }
        } else {
            echo "fail";
        }
    }
}

#Create new Character
if ($lrequest == "CreateChar") {
	$sql = "SELECT * FROM Accounts WHERE AccountNumber = '$ldataaccount' AND Characternumber = '$lcharnumber'";
	$result = $conn->query($sql);
	if ($result === FALSE) {
	} else {
		echo nl2br("\n - Success - \n");
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
                PosX = 53,
                PosY = -1,
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
				Luk = 1,
				Res = 1,
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

			if ($conn->query($update_sql) === TRUE) {
				echo " updated ";
			} else {
				echo "fail";
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

#SaveChar
if ($lrequest == "SaveChar") {
	$sql = "UPDATE Accounts SET 
	AccountID = '$AccountID',
	AccountNumber = '$AccountNumber',
	Characternumber = '$Characternumber',
	Name = '$Name',
	Sex = '$Sex',
	Hair = '$Hair',
	Color = '$Color',
	Hat = '$Hat',
	Job = '$Job',
	SetUpper = '$SetUpper',
	SetBottom = '$SetBottom',
	SetFooter = '$SetFooter',
	Level = '$Level',
	Exp = '$Exp',
	Map = '$Map',
	Hp = '$Hp',
	Mp = '$Mp',
	Money = '$Money',
	HpMax = '$HpMax',
	MpMax = '$MpMax',
	regenTime = '$regenTime',
	regenTimeMax = '$regenTimeMax',
	PosX = '$PosX',
	PosY = '$PosY',
	Walk = '$Walk',
	Frame = '$Frame',
	countFrame = '$countFrame',
	breakwalk = '$breakwalk',
	Target = '$Target',
	AtkTimer = '$AtkTimer',
	AtkTimerMax = '$AtkTimerMax',
	Casting = '$Casting',
	Atk = '$Atk',
	Def = '$Def',
	Evasion = '$Evasion',
	Side = '$Side',
	Weapon = '$Weapon',
	Crystal1 = '$Crystal1',
	Crystal2 = '$Crystal2',
	Crystal3 = '$Crystal3',
	Crystal4 = '$Crystal4',
	StatusPoint = '$StatusPoint',
	Str = '$Str',
	Agi = '$Agi',
	Vit = '$Vit',
	Dex = '$Dex',
	Wis = '$Wis',
	Luk = '$Luk',
	Res = '$Res',
	Stamina = '$Stamina',
	StaminaMax = '$StaminaMax',
	Itens = '$Itens',
	Quests = '$Quests',
	hotkey1 = '$hotkey1',
	hotkey2 = '$hotkey2',
	buffA = '$buffA',
	buffB = '$buffB',
	buffC = '$buffC',
	BuffTimeA = '$BuffTimeA',
	BuffTimeB = '$BuffTimeB',
	BuffTimeC = '$BuffTimeC',
	party = '$party',
	playerInBattle = '$playerInBattle',
	playerInAttack = '$playerInAttack',
	playerInCast = '$playerInCast',
	playerSit = '$playerSit',
	SyncPlayerMob = '$SyncPlayerMob',
	PlayerExpGet = '$PlayerExpGet',
	pet = '$pet',
	pethungry = '$pethungry',
	petcare = '$petcare',
	petTraining = '$petTraining',
	petBath = '$petBath',
	petLevel = '$petLevel'
WHERE AccountNumber = '$ldataaccount' AND Characternumber = '$lcharnumber'";

	echo nl2br("query: " . $sql);

    $result = $conn->query($sql);
    if ($result->num_rows > 0) {   
        // Echo the update SQL query for debugging
        echo nl2br("Update SQL: " . $update_sql . "\n");

        if ($conn->query($update_sql) === TRUE) {
            echo nl2br("Character UPDATE\n");
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
if ($lrequest == "SyncPlayers") {
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