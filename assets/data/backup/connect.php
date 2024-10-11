<?php

// Allow from any origin
header("Access-Control-Allow-Origin: *");

// Allow specific methods
header("Access-Control-Allow-Methods: POST, GET, OPTIONS");

// Allow specific headers
header("Access-Control-Allow-Headers: Content-Type, Authorization");

// Handle preflight requests
if ($_SERVER['REQUEST_METHOD'] == 'OPTIONS') {
    exit(0);
}

#Variaveis de Banco
$Servername = $_POST['Servername'];
$Username = $_POST['Username'];
$Password = $_POST['Password'];
$Dbname = $_POST['Dbname'];

#Variaveis Recebidas do Cliente
$Request = $_POST['Request'];
$Dataaccount = $_POST['Dataaccount'];
$Version = $_POST['Version'];
$Data = $_POST['Data'];
$Charnumber = $_POST['Charnumber'];

#Variaveis de Uso
$lAll = '';
$Chat = $_POST['Chat'];

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
$RegenTime = $_POST['RegenTime'];
$RegenTimeMax = $_POST['RegenTimeMax'];
$PosX = $_POST['PosX'];
$PosY = $_POST['PosY'];
$Walk = $_POST['Walk'];
$Frame = $_POST['Frame'];
$CountFrame = $_POST['CountFrame'];
$Breakwalk = $_POST['Breakwalk'];
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
$Hotkey1 = $_POST['Hotkey1'];
$Hotkey2 = $_POST['Hotkey2'];
$BuffA = $_POST['BuffA'];
$BuffB = $_POST['BuffB'];
$BuffC = $_POST['BuffC'];
$BuffTimeA = $_POST['BuffTimeA'];
$BuffTimeB = $_POST['BuffTimeB'];
$BuffTimeC = $_POST['BuffTimeC'];
$Party = $_POST['Party'];
$PlayerInBattle = $_POST['PlayerInBattle'];
$PlayerInAttack = $_POST['PlayerInAttack'];
$PlayerInCast = $_POST['PlayerInCast'];
$PlayerSit = $_POST['playerSit'];
$SyncPlayerMob = $_POST['SyncPlayerMob'];
$PlayerExpGet = $_POST['PlayerExpGet'];
$Pet = $_POST['Pet'];
$Pethungry = $_POST['Pethungry'];
$Petcare = $_POST['Petcare'];
$PetTraining = $_POST['PetTraining'];
$PetBath = $_POST['PetBath'];
$PetLevel = $_POST['PetLevel'];

$isPlayerOnline = $_POST['isPlayerOnline'];


$PlayerMapMobSync = $_POST['PlayerMapMobSync'];


#\n  (Quebra Linha)

// Create connection
$conn = new mysqli($Servername, $Username, $Password, $Dbname);

// Check connection
if ($conn->connect_error) {
	die("Resultado: - Falhou na Conexão com Banco -" . $conn->connect_error);
}


# Check New Account
if ($Request == "NewAccount") {
	$sql = "SELECT * FROM Accounts WHERE AccountNumber = '$Dataaccount'";
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
					'$Dataaccount', '$i', 'none'
				)";
				if ($conn->query($insert_sql) === TRUE) {
					echo "Account $i created\n";
				} else {
					echo "fail: " . $insert_sql . "<br>" . $conn->error;
				}
			}
		} else {
			echo "fail New Account";
		}
	}
}

if ($Request == "LoadData") {
    $sql = "SELECT * FROM Accounts WHERE AccountNumber = '$Dataaccount'"; // AND characternumber = '$lcharnumber'";
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
                    ":regenTime:" . $row["RegenTime"] .
                    ":regenTimeMax:" . $row["RegenTimeMax"] .
                    ":PosX:" . $row["PosX"] .
                    ":PosY:" . $row["PosY"] .
                    ":Walk:" . $row["Walk"] .
                    ":Frame:" . $row["Frame"] .
                    ":countFrame:" . $row["countFrame"] .
                    ":Breakwalk:" . $row["breakwalk"] .
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
                    ":hotkey1:" . $row["Hotkey1"] .
                    ":hotkey2:" . $row["Hotkey2"] .
                    ":buffA:" . $row["BuffA"] .
                    ":buffB:" . $row["BuffB"] .
                    ":buffC:" . $row["BuffC"] .
                    ":BuffTimeA:" . $row["BuffTimeA"] .
                    ":BuffTimeB:" . $row["BuffTimeB"] .
                    ":BuffTimeC:" . $row["BuffTimeC"] .
                    ":Party:" . $row["Party"] .
                    ":PlayerInBattle:" . $row["PlayerInBattle"] .
                    ":PlayerInAttack:" . $row["PlayerInAttack"] .
                    ":PlayerInCast:" . $row["PlayerInCast"] .
                    ":PlayerSit:" . $row["PlayerSit"] .
                    ":SyncPlayerMob:" . $row["SyncPlayerMob"] .
                    ":PlayerExpGet:" . $row["PlayerExpGet"] .
                    ":Pet:" . $row["Pet"] .
                    ":Pethungry:" . $row["Pethungry"] .
                    ":Petcare:" . $row["Petcare"] .
                    ":PetTraining:" . $row["PetTraining"] .
                    ":PetBath:" . $row["PetBath"] .
                    ":PetLevel:" . $row["PetLevel"] . ":@";
                echo nl2br($lAll);
            }
        } else {
            echo "fail Load Data";
        }
    }
}

#Create new Character
if ($Request == "CreateChar") {
	$sql = "SELECT * FROM Accounts WHERE AccountNumber = '$Dataaccount' AND Characternumber = '$Charnumber'";
	$result = $conn->query($sql);
	if ($result === FALSE) {
	} else {
		echo nl2br("\n - Success - \n");
		if ($result->num_rows == 1) {
			$update_sql = "UPDATE Accounts SET
                Name = '$Name',
                Sex = '$Sex',
                Hair = '$Hair',
                Color = '$Color',
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
                CountFrame = 1,
                Breakwalk = 'none',
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
                Hotkey1 = 'none',
                Hotkey2 = 'none',
                BuffA = 'none',
                BuffB = 'none',
                BuffC = 'none',
                BuffTimeA = 0,
                BuffTimeB = 0,
                BuffTimeC = 0,
                Party = 'none',
                PlayerInBattle = 'none',
                PlayerInAttack = 'none',
                PlayerInCast = 'none',
                PlayerSit = 'none',
                SyncPlayerMob = 'none',
                PlayerExpGet = '0',
                Itens = '$Itens',
				Pet = 'none',
				Pethungry = 'none',
				Petcare = 'none',
				PetTraining = 'none',
				PetBath = 'none',
				PetLevel = 'none',
				isPlayerOnline = 'offline'
            WHERE
                AccountNumber = '$Dataaccount' AND
                Characternumber = '$Charnumber'";

			if ($conn->query($update_sql) === TRUE) {
				echo " updated ";
			} else {
				echo "fail Create Char";
			}
		} else {
			echo "fail Create Char";
		}
	}
}


#Check Account 
if ($Request == "CheckAccount") {
	$sql = "SELECT * FROM Accounts where AccountNumber = '$Dataaccount'";
	$result = $conn->query($sql);
	if($result->num_rows > 0) {
		echo nl2br("success");
	} else {
		echo nl2br("fail Check Account");
	}
}

#Delete Char
if ($Request == "DeleteChar") {
    $sql = "SELECT * FROM Accounts WHERE AccountNumber = '$Dataaccount'";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
        $update_sql = "UPDATE Accounts SET Name = 'none' WHERE AccountNumber = '$Dataaccount' AND Characternumber = '$Charnumber'";
        
        // Echo the update SQL query for debugging
        echo nl2br("Update SQL: " . $update_sql . "\n");

        if ($conn->query($update_sql) === TRUE) {
            echo nl2br("Character name updated to 'none'\n");
        } else {
            echo "fail: " . $update_sql . "<br>" . mysqli_error($conn);
        }
    } else {
        echo nl2br("fail Delete Chat");
    }
}

#SaveChar
if ($Request == "SaveChar") {
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
	regenTime = '$RegenTime',
	regenTimeMax = '$RegenTimeMax',
	PosX = '$PosX',
	PosY = '$PosY',
	Walk = '$Walk',
	Frame = '$Frame',
	CountFrame = '$CountFrame',
	Breakwalk = '$Breakwalk',
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
	Hotkey1 = '$Hotkey1',
	Hotkey2 = '$Hotkey2',
	BuffA = '$BuffA',
	BuffB = '$BuffB',
	BuffC = '$BuffC',
	BuffTimeA = '$BuffTimeA',
	BuffTimeB = '$BuffTimeB',
	BuffTimeC = '$BuffTimeC',
	Party = '$Party',
	PlayerInBattle = '$PlayerInBattle',
	PlayerInAttack = '$PlayerInAttack',
	PlayerInCast = '$PlayerInCast',
	PlayerSit = '$PlayerSit',
	SyncPlayerMob = '$SyncPlayerMob',
	PlayerExpGet = '$PlayerExpGet',
	Pet = '$Pet',
	Pethungry = '$Pethungry',
	Petcare = '$Petcare',
	PetTraining = '$PetTraining',
	PetBath = '$PetBath',
	PetLevel = '$PetLevel',
	isPlayerOnline = 'online'
	WHERE AccountNumber = '$Dataaccount' AND Characternumber = '$Charnumber'";

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
        echo nl2br("fail Save Char");
    }
}

#SendAtk
if ($Request == "SendAtk") {
	$sql = "UPDATE INTO Chats (AccountID,Name,Msg) VALUES ('$Dataaccount','$Name','$Chat')";
	if ($conn->query($sql) === TRUE) {
		echo nl2br("\n - AdicionadoAtk - \n");
	} else {
		echo nl2br($sql);
		echo nl2br("\n - Fail Insert Atk \n") . $conn->error;
	}
	#$result = $conn->query($sql);
	$conn->close();
	return;
}

#Adicionar Chat
if ($Request == "Chat") {
	$sql = "INSERT INTO Chats (AccountID,Name,Msg) VALUES ('$Dataaccount','$Name','$Chat')";
	if ($conn->query($sql) === TRUE) {
		echo nl2br("\n - AdicionadoChat - \n");
	} else {
		echo nl2br($sql);
		echo nl2br("\n - Fail Insert Chat \n") . $conn->error;
	}
	#$result = $conn->query($sql);
	$conn->close();
	return;
}

#Update Mob Data
if ($Request == "UpdateMobData") {
    $account = $_POST['Dataaccount'];
    $charnumber = $_POST['Charnumber'];
    $name = $_POST['Name'];

    // Loop through the mob data in the POST request
    for ($i = 0; isset($_POST['MobID' . $i]); $i++) {
        $mobID = $_POST['MobID' . $i];
        $mobHp = $_POST['MobHp' . $i];
        $mobMp = $_POST['MobMp' . $i];
        $mobPosX = $_POST['MobPosX' . $i];
        $mobPosY = $_POST['MobPosY' . $i];
        $mobTarget = $_POST['MobTarget' . $i];
        $mobDead = $_POST['MobDead' . $i];
        $mobMap = $_POST['MobMap' . $i];

        // Update the mob data in the database
        $sql = "UPDATE Mobs SET 
                MobHp = '$mobHp',
                MobMp = '$mobMp',
                MobPosX = '$mobPosX',
                MobPosY = '$mobPosY',
                MobTarget = '$mobTarget',
                MobDead = '$mobDead',
                MobMap = '$mobMap'
                WHERE MobID = '$mobID'";

        if (!$conn->query($sql)) {
            echo "Error updating mob $mobID: " . $conn->error;
        }
    }

    echo "Mob data updated successfully.";
}

#Efetua Sync

#Sync Mobs
if ($Request == "SyncMobs") {
    $sql = "SELECT * FROM Mobs WHERE MobMap = '$PlayerMapMobSync'";
    $result = $conn->query($sql);
    if ($result === FALSE) {
        echo nl2br($sql);
        echo nl2br("\n - Fail Sync Mob - \n") . $conn->error;
    } else {
        if ($result->num_rows > 0) {
            // output data of each row
            while ($row = $result->fetch_assoc()) {
                $lAll = ":MobID:" . $row["MobID"] .
                    ":MobHp:" . $row["MobHp"] .
                    ":MobMp:" . $row["MobMp"] .
                    ":MobPosX:" . $row["MobPosX"] .
                    ":MobPosY:" . $row["MobPosY"] .
                    ":MobTarget:" . $row["MobTarget"] .
                    ":MobDead:" . $row["MobDead"] .
                    ":MobMap:" . $row["MobMap"] . ":@";
                echo nl2br($lAll);
            }
        }
    }
}

#Sync Chats
if ($Request == "SyncChats") {
	$sql = "SELECT * FROM Chats order by ChatID desc limit 5";
	$result = $conn->query($sql);
	if ($result === FALSE) {
		echo nl2br($sql);
		echo nl2br("\n - Fail Sync Chat - \n") . $conn->error;
	} else {
		echo nl2br("\n - RecuperadoChat - \n");
		if ($result->num_rows > 0) {
			// output data of each row
			while ($row = $result->fetch_assoc()) {
				$lAll = "@ - :Name:" . $row["Name"] .
					":Msg:" . $row["Msg"] .
					": - \n";
				echo nl2br($lAll);
			}
		} else {
			echo "0 results";
		}
	}
}

#Sync Mobs
if ($Request == "SyncPlayers") {
	$sql = "SELECT * FROM Accounts WHERE isPlayerOnline = 'online';";
	$result = $conn->query($sql);
	$lAll = '';
	if ($result->num_rows > 0) {
		// output data of each row
		while ($row = $result->fetch_assoc()) {
			$lAll = $lAll . ":Name:" . $row["Name"] .
				":AccountID:" . $row["AccountID"] .
				":Level:" . $row["Level"] .
				":Map:" . $row["Map"] .
				":Hp:" . $row["Hp"] .
				":Mp:" . $row["Mp"] .
				":PosX:" . $row["PosX"] .
				":PosY:" . $row["PosY"] .
				":Walk:" . $row["Walk"] .
				":Weapon:" . $row["Weapon"] .
				":Frame:" . $row["Frame"] .
				":SyncPlayerMob:" . $row["SyncPlayerMob"] .
				":SetUpper:" . $row["SetUpper"] .
				":SetBottom:" . $row["SetBottom"] .
				":SetFooter:" . $row["SetFooter"] .
				":Hair:" . $row["Hair"] .
				":Sex:" . $row["Sex"] .
				":Color:" . $row["Color"] .
				":Hat:" . $row["Hat"] .
				":Side:" . $row["Side"] .
				":Job:" . $row["Job"] .
				":PlayerInBattle:" . $row["PlayerInBattle"] .
				":PlayerInAttack:" . $row["PlayerInAttack"] .
				":PlayerInCast:" . $row["PlayerInCast"] .
				":PlayerSit:" . $row["PlayerSit"] .
				":Party:" . $row["Party"] .
				":isPlayerOnline:" . $row["isPlayerOnline"] . ":@";
                echo nl2br($lAll);
		}
	}
	else{
		echo nl2br("fail Sync Players");
	}
	$conn->close();
}

##UPLOAD
if ($Request == "Upload") {

	$arquivo = $Dataaccount;
	$file = fopen($arquivo, 'w');
	if (fwrite($file, $ldata)) {
		echo nl2br("Atualizado");
		fclose($file);
	} else {
		echo nl2br("Negado");
	}

}

##DOWNLOAD
if ($Request == "Download") {

	if (file_exists($Dataaccount)) {
		$arquivo = fopen($Dataaccount, "r");
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