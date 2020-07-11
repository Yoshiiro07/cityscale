package com.moonbolt.cityscale;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class GameControl {

	//Sumary
	//[A] Data Manager
	//[A] Interface
	//[B] Account Data
	//[C] Character Code
	
	//Data Manager Variables
	private Json json;
	private FileHandle file;
	private Random randnumber;
	private Player playerInfo;
	private int activeCharNumber;
	
	
	//Object Variables
	private DataManager dataManager;
	
	//Game Variables
	private Sprite spr_master;
	private Texture tex_teste;
	
	
	//Atlas Section
	private TextureAtlas atlas_hairs;
	private TextureAtlas atlas_basicset_m;
	private TextureAtlas atlas_basicset_f;
	private TextureAtlas atlas_InterfaceCreate;
	
	
	// CONSTRUCTOR
	public GameControl() {
		this.dataManager = new DataManager();
		this.playerInfo = new Player();
		
		this.tex_teste = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		this.spr_master = new Sprite(tex_teste);
		
		
		//Data Manager
		json = new Json();
		playerInfo = new Player();
		activeCharNumber = 0;
		CheckData();
		
		//Atlas Section
		//InterfaceCreate
		atlas_InterfaceCreate = new TextureAtlas(Gdx.files.internal("data/assets/interfaceCreate.txt"));	
		
		//Character Assets
		atlas_hairs = new TextureAtlas(Gdx.files.internal("data/characters/players/hair/hairs.txt"));
				
		atlas_basicset_m = new TextureAtlas(Gdx.files.internal("data/characters/players/basicset_m/basicset_m.txt"));
		atlas_basicset_f = new TextureAtlas(Gdx.files.internal("data/characters/players/basicset_f/basicset_f.txt"));
	}
	
	//[A] DATA MANAGER
	public void CheckData() {
		file = Gdx.files.local("SaveData/save.json");		
		if(!file.exists()) { 
			CreateNewData(); 
		}
	}
	
	public void CreateNewData(){
		try {
			//Variaveis
			int count;
			Player player = new Player();
			
			//método
			count = 0;	   	    
			randnumber = new Random();
			FileHandle file = Gdx.files.local("SaveData/SvDT.json");
			while(count <= 100000){
				count = randnumber.nextInt(999999);
			}
					
				if (!file.exists()) {
					player = new Player();
					player.accountID = String.valueOf(count);
					
					player.name_1 = "none";
					player.name_2 = "none";
					player.name_3 = "none";
					
					file.writeString(Base64Coder.encodeString(json.prettyPrint(player)),false);
				}
		}
		
		catch(Exception e) {}		
	}
	
	public void LoadData() {
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		playerInfo = json.fromJson(Player.class,Base64Coder.decodeString(file.readString()));	
	}
	
	public void CreateNewCharacter(String name, String 	hair, String sex) {
		//Carrega Save
		LoadData();
		
		//Variaveis
		int charNumber = 0;
		boolean created = false;
		
		//Processamento
		if(playerInfo.name_1.equals("none") && !created) { charNumber = 1; created = true;}
		if(playerInfo.name_2.equals("none") && !created) { charNumber = 2; created = true;}
		if(playerInfo.name_3.equals("none") && !created) { charNumber = 3; created = true;}
		
		if(charNumber == 1) {
			playerInfo.name_1 = name;
			playerInfo.sex_1 = sex;
			playerInfo.job_1 = "Novice";
			playerInfo.weapon_1 = "basic_knife";
			playerInfo.level_1 = "1";
			playerInfo.stats_1 = "|str:1|vit:1|agi:1|dex:1|luk:1|wis:1|res:1|";
			if(sex.equals("M")) { playerInfo.set_1 = "basicset_m"; } else { playerInfo.set_1 = "basicset_f"; }
			playerInfo.hat_1 = "none";
			playerInfo.exp_1 = "0";
			playerInfo.hp_1 = "100";
			playerInfo.mp_1 = "100";
			playerInfo.maxhp_1 = "100";
			playerInfo.maxmp_1 = "100";
			playerInfo.money_1 = "50";
			playerInfo.cooldown_1 = "";
			playerInfo.statusPoint_1 = "0";
			playerInfo.skillPoint_1 = "0";
			playerInfo.skills_1 = "none";
			playerInfo.coordX_1 = "";
			playerInfo.coordY_1 = "";
			playerInfo.inBattle_1 = "no";
			playerInfo.target_1 = "none";
			playerInfo.itens_1 = "none";
			playerInfo.map_1 = "MetroStation";
			playerInfo.party_1 = "none";
			playerInfo.inCasting_1 = "no";
			playerInfo.quest_1 = "Starting";
			playerInfo.state_1 = "stop";
		}
		
		if(charNumber == 2) {
			playerInfo.name_2 = name;
			playerInfo.sex_2 = sex;
			playerInfo.job_2 = "Novice";
			playerInfo.weapon_2 = "basic_knife";
			playerInfo.level_2 = "1";
			playerInfo.stats_2 = "|str:1|vit:1|agi:1|dex:1|luk:1|wis:1|res:1|";
			if(sex.equals("M")) { playerInfo.set_2 = "basicset_m"; } else { playerInfo.set_2 = "basicset_f"; }
			playerInfo.hat_2 = "none";
			playerInfo.exp_2 = "0";
			playerInfo.hp_2 = "100";
			playerInfo.mp_2 = "100";
			playerInfo.maxhp_2 = "100";
			playerInfo.maxmp_2 = "100";
			playerInfo.money_2 = "50";
			playerInfo.cooldown_2 = "";
			playerInfo.statusPoint_2 = "0";
			playerInfo.skillPoint_2 = "0";
			playerInfo.skills_2 = "none";
			playerInfo.coordX_2 = "";
			playerInfo.coordY_2 = "";
			playerInfo.inBattle_2 = "no";
			playerInfo.target_2 = "none";
			playerInfo.itens_2 = "none";
			playerInfo.map_2 = "MetroStation";
			playerInfo.party_2 = "none";
			playerInfo.inCasting_2 = "no";
			playerInfo.quest_2 = "Starting";
			playerInfo.state_2 = "stop";
		}
		
		if(charNumber == 3) {
			playerInfo.name_3 = name;
			playerInfo.sex_3 = sex;
			playerInfo.job_3 = "Novice";
			playerInfo.weapon_3 = "basic_knife";
			playerInfo.level_3 = "1";
			playerInfo.stats_3 = "|str:1|vit:1|agi:1|dex:1|luk:1|wis:1|res:1|";
			if(sex.equals("M")) { playerInfo.set_3 = "basicset_m"; } else { playerInfo.set_3 = "basicset_f"; }
			playerInfo.hat_3 = "none";
			playerInfo.exp_3 = "0";
			playerInfo.hp_3 = "100";
			playerInfo.mp_3 = "100";
			playerInfo.maxhp_3 = "100";
			playerInfo.maxmp_3 = "100";
			playerInfo.money_3 = "50";
			playerInfo.cooldown_3 = "";
			playerInfo.statusPoint_3 = "0";
			playerInfo.skillPoint_3 = "0";
			playerInfo.skills_3 = "none";
			playerInfo.coordX_3 = "";
			playerInfo.coordY_3 = "";
			playerInfo.inBattle_3 = "no";
			playerInfo.target_3 = "none";
			playerInfo.itens_3 = "none";
			playerInfo.map_3 = "MetroStation";
			playerInfo.party_3 = "none";
			playerInfo.inCasting_3 = "no";
			playerInfo.quest_3 = "Starting";
			playerInfo.state_3 = "stop";
		}
		
		file.writeString(Base64Coder.encodeString(json.prettyPrint(playerInfo)),false);		
	}
	
	public void SaveData() {		
		file = Gdx.files.local("SaveData/SvDT.json");
		file.writeString(Base64Coder.encodeString(json.prettyPrint(playerInfo)),false);
	}
	
	public void SetActivePlayerData(int num) {
		
		if(num == 1) {
			playerInfo.name_A = playerInfo.name_1;
			playerInfo.sex_A = playerInfo.sex_1;
			playerInfo.job_A = playerInfo.job_1;
			playerInfo.weapon_A = playerInfo.weapon_1;
			playerInfo.level_A = playerInfo.level_1;
			playerInfo.stats_A = playerInfo.stats_1;
			playerInfo.sex_A = playerInfo.sex_1; 
			playerInfo.hat_A = playerInfo.hat_1;
			playerInfo.exp_A = playerInfo.exp_1;
			playerInfo.hp_A = playerInfo.hp_1;
			playerInfo.mp_A = playerInfo.mp_1;
			playerInfo.maxhp_A = playerInfo.maxhp_1;
			playerInfo.maxmp_A = playerInfo.maxmp_1;
			playerInfo.money_A = playerInfo.money_1;
			playerInfo.cooldown_A = playerInfo.cooldown_1;
			playerInfo.statusPoint_A = playerInfo.statusPoint_1;
			playerInfo.skillPoint_A = playerInfo.skillPoint_1;
			playerInfo.skills_A = playerInfo.skills_1;
			playerInfo.coordX_A = playerInfo.coordX_1;
			playerInfo.coordY_A = playerInfo.coordY_1;
			playerInfo.inBattle_A = playerInfo.inBattle_1;
			playerInfo.target_A = playerInfo.target_1;
			playerInfo.itens_A = playerInfo.itens_1;
			playerInfo.map_A = playerInfo.map_1;
			playerInfo.party_A = playerInfo.party_1;
			playerInfo.inCasting_A = playerInfo.party_1;
			playerInfo.quest_A = playerInfo.quest_1;
			playerInfo.state_A = playerInfo.state_1;
		}
		if(num == 2) {
			playerInfo.name_A = playerInfo.name_2;
			playerInfo.sex_A = playerInfo.sex_2;
			playerInfo.job_A = playerInfo.job_2;
			playerInfo.weapon_A = playerInfo.weapon_2;
			playerInfo.level_A = playerInfo.level_2;
			playerInfo.stats_A = playerInfo.stats_2;
			playerInfo.sex_A = playerInfo.sex_2; 
			playerInfo.hat_A = playerInfo.hat_2;
			playerInfo.exp_A = playerInfo.exp_2;
			playerInfo.hp_A = playerInfo.hp_2;
			playerInfo.mp_A = playerInfo.mp_2;
			playerInfo.maxhp_A = playerInfo.maxhp_2;
			playerInfo.maxmp_A = playerInfo.maxmp_2;
			playerInfo.money_A = playerInfo.money_2;
			playerInfo.cooldown_A = playerInfo.cooldown_2;
			playerInfo.statusPoint_A = playerInfo.statusPoint_2;
			playerInfo.skillPoint_A = playerInfo.skillPoint_2;
			playerInfo.skills_A = playerInfo.skills_2;
			playerInfo.coordX_A = playerInfo.coordX_2;
			playerInfo.coordY_A = playerInfo.coordY_2;
			playerInfo.inBattle_A = playerInfo.inBattle_2;
			playerInfo.target_A = playerInfo.target_2;
			playerInfo.itens_A = playerInfo.itens_2;
			playerInfo.map_A = playerInfo.map_2;
			playerInfo.party_A = playerInfo.party_2;
			playerInfo.inCasting_A = playerInfo.party_2;
			playerInfo.quest_A = playerInfo.quest_2;
			playerInfo.state_A = playerInfo.state_2;
		}
		if(num == 3) {
			playerInfo.name_A = playerInfo.name_3;
			playerInfo.sex_A = playerInfo.sex_3;
			playerInfo.job_A = playerInfo.job_3;
			playerInfo.weapon_A = playerInfo.weapon_3;
			playerInfo.level_A = playerInfo.level_3;
			playerInfo.stats_A = playerInfo.stats_3;
			playerInfo.sex_A = playerInfo.sex_3; 
			playerInfo.hat_A = playerInfo.hat_3;
			playerInfo.exp_A = playerInfo.exp_3;
			playerInfo.hp_A = playerInfo.hp_3;
			playerInfo.mp_A = playerInfo.mp_3;
			playerInfo.maxhp_A = playerInfo.maxhp_3;
			playerInfo.maxmp_A = playerInfo.maxmp_3;
			playerInfo.money_A = playerInfo.money_3;
			playerInfo.cooldown_A = playerInfo.cooldown_3;
			playerInfo.statusPoint_A = playerInfo.statusPoint_3;
			playerInfo.skillPoint_A = playerInfo.skillPoint_3;
			playerInfo.skills_A = playerInfo.skills_3;
			playerInfo.coordX_A = playerInfo.coordX_3;
			playerInfo.coordY_A = playerInfo.coordY_3;
			playerInfo.inBattle_A = playerInfo.inBattle_3;
			playerInfo.target_A = playerInfo.target_3;
			playerInfo.itens_A = playerInfo.itens_3;
			playerInfo.map_A = playerInfo.map_3;
			playerInfo.party_A = playerInfo.party_3;
			playerInfo.inCasting_A = playerInfo.party_3;
			playerInfo.quest_A = playerInfo.quest_3;
			playerInfo.state_A = playerInfo.state_3;
		}
	}
	
	

	//[B] INTERFACE
	//Load elements for show in UI.
	public Sprite LoadInterfaceCreate(String type) {		
		spr_master = LoadInterface(type);
		return spr_master;
	}
	
	//Load Elements from maps e screens 
	public Sprite LoadObjectElements(String type) {
		spr_master = LoadElements(type);
		return spr_master;
	}
	

	public Sprite LoadAllHairsMenu(int num, String sex) {
		if(sex.equals("M")) {
			spr_master = atlas_hairs.createSprite("hair" + num);
			spr_master.setSize(8, 12);
			
			if(num == 1) { spr_master.setPosition(35.8f,38); }
			if(num == 2) { spr_master.setPosition(42f,38); }
			if(num == 3) { spr_master.setPosition(48.5f,38); }
			if(num == 4) { spr_master.setPosition(54.7f,38); }
			if(num == 5) { spr_master.setPosition(60.9f,38); }
			if(num == 6) { spr_master.setPosition(67f,38); }
			if(num == 7) { spr_master.setPosition(35.8f,25); }
			if(num == 8) { spr_master.setPosition(42f,25); }
			if(num == 9) { spr_master.setPosition(48.5f,25); }
			if(num == 10) { spr_master.setPosition(54.7f,25); }
			if(num == 11) { spr_master.setPosition(60.9f,25); }		
		}
		
		if(sex.equals("F")) {
			spr_master = atlas_hairs.createSprite("hair" + num + "_f");
			spr_master.setSize(8, 11);

			if(num == 1) { spr_master.setPosition(35.8f,38); }
			if(num == 2) { spr_master.setPosition(42f,38); }
			if(num == 3) { spr_master.setPosition(48.5f,38); }
			if(num == 4) { spr_master.setPosition(54.7f,38); }
			if(num == 5) { spr_master.setPosition(60.9f,38); }
			if(num == 6) { spr_master.setPosition(67f,38); }
			if(num == 7) { spr_master.setPosition(35.8f,25); }
			if(num == 8) { spr_master.setPosition(42f,25); }
			if(num == 9) { spr_master.setPosition(48.5f,25); }
			if(num == 10) { spr_master.setPosition(54.7f,25); }
			if(num == 11) { spr_master.setPosition(60.9f,25); }	
			
		}
		
		
		return spr_master;
	}
	
	public Sprite LoadInterface(String type) {
		
		if(type.equals("barAccess")) {
			spr_master = atlas_InterfaceCreate.createSprite("barAccess");
			spr_master.setSize(24, 20);
			spr_master.setPosition(70, 10);
		}
		
		if(type.equals("titleTopSelect")) {
			spr_master = atlas_InterfaceCreate.createSprite("selecionepersonagem");
			spr_master.setSize(45, 7);
			spr_master.setPosition(2, 87); 
		}
		
		if(type.equals("titleTopCreate")) {
			spr_master = atlas_InterfaceCreate.createSprite("criandopersonagem");
			spr_master.setSize(45, 7);
			spr_master.setPosition(2, 87); 
		}
		
		if(type.equals("titleTopDelete")) {
			spr_master = atlas_InterfaceCreate.createSprite("deletandopersonagem");
			spr_master.setSize(45, 7);
			spr_master.setPosition(2, 87); 
		}
		
		if(type.equals("boardCreate")) {
			spr_master = atlas_InterfaceCreate.createSprite("barcreatechar");
			spr_master.setSize(80, 80);
			spr_master.setPosition(9.8f, 5); 
		}
		
		
		if(type.equals("btnCreate")) {
			spr_master = atlas_InterfaceCreate.createSprite("btnCriarNovo");
			spr_master.setSize(10,14);
			spr_master.setPosition(85,2);
		}
		
		if(type.equals("btnDelete")) {
			spr_master = atlas_InterfaceCreate.createSprite("btnDeletar");
			spr_master.setSize(10,14);
			spr_master.setPosition(5,2);
		}
		
		
		
		return spr_master;
	}
	
	public Sprite LoadElements(String type) {
		
		if(type.equals("light1")) {
			spr_master = atlas_InterfaceCreate.createSprite("luzclara");
			spr_master.setSize(20,50);
			spr_master.setPosition(12,20);
		}
		
		if(type.equals("light2")) {
			spr_master = atlas_InterfaceCreate.createSprite("luzclara");
			spr_master.setSize(20,50);
			spr_master.setPosition(40,20);
		}
		
		if(type.equals("light3")) {
			spr_master = atlas_InterfaceCreate.createSprite("luzclara");
			spr_master.setSize(20,50);
			spr_master.setPosition(72,20);
		}
		
		return spr_master;
	}
	
	//Process comand select from UI. (Size/Position fixed from element)
	public String TouchVerify(float touchX, float touchY, String ScreenPress) {
		
		
		//Title Screen
		if(ScreenPress.equals("TitleScreen")) {
			//Start Button
			if(touchX >= 75.7 && touchX <= 87.7 && touchY >= 18.8 && touchY <= 24.8) {
				CheckData();
				LoadData();
				return "ChangeScreen";
			}
			//Recovery Button
			if(touchX >= 75.7 && touchX <= 87.7 && touchY >= 12.6 && touchY <= 17.7) {
			
			}
		}
		
		//Character Select Screen
		if(ScreenPress.equals("CharacterScreenMain")) {
			//Create Button
			if(touchX >= 84.9 && touchX <= 94.2 && touchY >= 2.4 && touchY <= 15.6) {
				return "CharacterScreenCreate";
			}
			//Delete Button
			if(touchX >= 5.1 && touchX <= 14.7 && touchY >= 2.2 && touchY <= 15.6) {
				return "CharacterScreenDelete";
			}
		}
		
		if(ScreenPress.equals("CharacterScreenCreate")) {
			//Name Button
			if(touchX >= 36.8 && touchX <= 57.3 && touchY >= 64.3 && touchY <= 74) {
				return "NameSelect";
			}
			//Sexo
			if(touchX >= 41.3 && touchX <= 53.6 && touchY >= 57.3 && touchY <= 64.1) {
				return "M";
			}
			if(touchX >= 54.3 && touchX <= 66.6 && touchY >= 57.3 && touchY <= 64.1) {
				return "F";
			}
			//Confirmar
			if(touchX >= 68.1 && touchX <= 80 && touchY >= 9.5 && touchY <= 15.8) {
				return "Confirmar";
			}
			//Voltar
			if(touchX >= 36.8 && touchX <= 49.1 && touchY >= 9 && touchY <= 15.8) {
				return "Voltar";
			}
			
			//Hair1 
			if(touchX >= 36.8 && touchX <= 42.6 && touchY >= 36.8 && touchY <= 47.6) {
				return "hair1";
			}
			//Hair2
			if(touchX >= 43.2 && touchX <= 48.9 && touchY >= 36.8 && touchY <= 47.6) {
				return "hair2";
			}
			//Hair3
			if(touchX >= 49.4 && touchX <= 55.1 && touchY >= 36.8 && touchY <= 47.6) {
				return "hair3";
			}
			//Hair4
			if(touchX >= 55.7 && touchX <= 61.4 && touchY >= 36.8 && touchY <= 47.6) {
				return "hair4";
			}
			//Hair5
			if(touchX >= 62 && touchX <= 74.0 && touchY >= 67.6 && touchY <= 47.6) {
				return "hair5";
			}
			//Hair6
			if(touchX >= 68.3 && touchX <= 74.0 && touchY >= 36.8 && touchY <= 47.6) {
				return "hair6";
			}
			
			//Hair7
			if(touchX >= 36.8 && touchX <= 42.6 && touchY >= 25.2 && touchY <= 35.7) {
				return "hair7";
			}
			//Hair8
			if(touchX >= 43.2 && touchX <= 48.9 && touchY >= 25.2 && touchY <= 35.7) {
				return "hair8";
			}
			//Hair9
			if(touchX >= 49.4 && touchX <= 55.1 && touchY >= 25.2 && touchY <= 35.7) {
				return "hair9";
			}
			//Hair10
			if(touchX >= 55.7 && touchX <= 61.4 && touchY >= 25.2 && touchY <= 35.7) {
				return "hair10";
			}
			//Hair11
			if(touchX >= 62 && touchX <= 74.0 && touchY >= 25.2 && touchY <= 35.7) {
				return "hair11";
			}
			//Hair12
			if(touchX >= 68.3 && touchX <= 74.0 && touchY >= 25.2 && touchY <= 35.7) {
				return "hair12";
			}
			
			
			
		}
		
		if(ScreenPress.equals("CharacterScreenDelete")) {
			//Name Button
			if(touchX >= 84.9 && touchX <= 94.2 && touchY >= 2.4 && touchY <= 15.6) {
				return "CharacterScreenDelete";
			}
		}
		
		
		return "";
	}	
}
