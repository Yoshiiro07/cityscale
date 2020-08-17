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
	//[B] Interface
	//[C] Account Data
	//[D] Character Code
	
	//Data Manager Variables
	private Json json;
	private FileHandle file;
	private Random randnumber;
	private Player playerInfo;
	
	//Primitive Variable
	private int activeCharNumber;
	private int countFrameMov = 1;
	private int frame = 1;
	private float playerPosX;
	private float playerPosY;
	private float playerSpeed = 0.4f;
	private int recoverytimer = 700;
	private int savetimer = 800;
	
	private int playerHP;
	private int playerMP;
	private int playerHPMax;
	private int playerMPMax;
	

	//Camera Variables
	private float cameraCoordsX;
	private float cameraCoordsY;	
	
	//Object Variables
	
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
		this.playerInfo = new Player();
		
		this.tex_teste = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		this.spr_master = new Sprite(tex_teste);
				
		//Data Manager
		json = new Json();
		playerInfo = new Player();
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
	
	public void SetActivePlayer(int num) {
		activeCharNumber = num; 
	}
	
	public Player GetPlayer() {
		return playerInfo;
	}
	
	public int GetPlayerActiveNum() {
		return activeCharNumber;
	}
	
	public void ScreenChange(String map) {
		if(map.equals("MetroStation")) {
			
		}
		
		if(map.equals("Streets305")) {
			playerInfo.map_A = "Streets305";
			playerInfo.coordX_A = "181";
			playerInfo.coordY_A = "-130";
			savetimer = 0;
		}
	}
	
	public void CreateNewData(){
		try {
			//Variaveis
			int count;
			Player player = new Player();
			
			//m�todo
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
			playerInfo.stats_1 = "str:1#agi:1#wis:1#vit:1#des:1#sor:1#res:1";
			if(sex.equals("M")) { playerInfo.set_1 = "basicset_m"; } else { playerInfo.set_1 = "basicset_f"; }
			playerInfo.hair_1 = hair;
			playerInfo.hat_1 = "none";
			playerInfo.exp_1 = "0";
			playerInfo.hp_1 = "100";
			playerInfo.mp_1 = "100";
			playerInfo.coordX_1 = "1";
			playerInfo.coordY_1 = "38";
			playerInfo.maxhp_1 = "100";
			playerInfo.maxmp_1 = "100";
			playerInfo.money_1 = "50";
			playerInfo.cooldown_1 = "";
			playerInfo.statusPoint_1 = "0";
			playerInfo.skillPoint_1 = "0";
			playerInfo.skills_1 = "none";
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
			playerInfo.stats_2 = "str:1#agi:1#wis:1#vit:1#des:1#sor:1#res:1";
			if(sex.equals("M")) { playerInfo.set_2 = "basicset_m"; } else { playerInfo.set_2 = "basicset_f"; }
			playerInfo.hair_2 = hair;
			playerInfo.hat_2 = "none";
			playerInfo.exp_2 = "0";
			playerInfo.hp_2 = "100";
			playerInfo.mp_2 = "100";
			playerInfo.coordX_2 = "1";
			playerInfo.coordY_2 = "38";
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
			playerInfo.stats_3 = "str:1#agi:1#wis:1#vit:1#des:1#sor:1#res:1";
			if(sex.equals("M")) { playerInfo.set_3 = "basicset_m"; } else { playerInfo.set_3 = "basicset_f"; }
			playerInfo.hair_3 = hair;
			playerInfo.hat_3 = "none";
			playerInfo.exp_3 = "0";
			playerInfo.hp_3 = "100";
			playerInfo.mp_3 = "100";
			playerInfo.coordX_3 = "1";
			playerInfo.coordY_3 = "38";
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
		
		SaveData(playerInfo);	
	}
	
	public void DeleteCharacter(int num) {
		if(num == 1) { playerInfo.name_1 = "none"; }
		if(num == 2) { playerInfo.name_2 = "none"; }
		if(num == 3) { playerInfo.name_3 = "none"; }
		
		SaveData(playerInfo);
	}
	
	public void SaveData(Player playerInfo) {		
		file = Gdx.files.local("SaveData/SvDT.json");
		file.writeString(Base64Coder.encodeString(json.prettyPrint(playerInfo)),false);
	}
	
	public void UpdateDataSave(int num) {
		savetimer--;
		if(savetimer <= 0) {
		
			if(num == 1) {
				playerInfo.name_1 = playerInfo.name_A;
				playerInfo.hair_1 = playerInfo.hair_A;
				playerInfo.set_1 = playerInfo.set_A;
				playerInfo.sex_1 = playerInfo.sex_A;
				playerInfo.job_1 = playerInfo.job_A;
				playerInfo.weapon_1 = playerInfo.weapon_A;
				playerInfo.level_1 = playerInfo.level_A;
				playerInfo.stats_1 = playerInfo.stats_A;
				playerInfo.sex_1 = playerInfo.sex_A; 
				playerInfo.hat_1 = playerInfo.hat_A;
				playerInfo.exp_1 = playerInfo.exp_A;
				playerInfo.hp_1 = playerInfo.hp_A;
				playerInfo.mp_1 = playerInfo.mp_A;
				playerInfo.maxhp_1 = playerInfo.maxhp_A;
				playerInfo.maxmp_1 = playerInfo.maxmp_A;
				playerInfo.money_1 = playerInfo.money_A;
				playerInfo.cooldown_1 = playerInfo.cooldown_A;
				playerInfo.statusPoint_1 = playerInfo.statusPoint_A;
				playerInfo.skillPoint_1 = playerInfo.skillPoint_A;
				playerInfo.skills_1 = playerInfo.skills_A;
				playerInfo.coordX_1 = playerInfo.coordX_A;
				playerInfo.coordY_1 = playerInfo.coordY_A;
				playerInfo.inBattle_1 = playerInfo.inBattle_A;
				playerInfo.target_1 = playerInfo.target_A;
				playerInfo.itens_1 = playerInfo.itens_A;
				playerInfo.map_1 = playerInfo.map_A;
				playerInfo.party_1 = playerInfo.party_A;
				playerInfo.inCasting_1 = playerInfo.party_A;
				playerInfo.quest_1 = playerInfo.quest_A;
				playerInfo.state_1 = playerInfo.state_A;
			}
			if(num == 2) {
				playerInfo.name_2 = playerInfo.name_A;
				playerInfo.hair_2 = playerInfo.hair_A;
				playerInfo.set_2 = playerInfo.set_A;
				playerInfo.sex_2 = playerInfo.sex_A;
				playerInfo.job_2 = playerInfo.job_A;
				playerInfo.weapon_2 = playerInfo.weapon_A;
				playerInfo.level_2 = playerInfo.level_A;
				playerInfo.stats_2 = playerInfo.stats_A;
				playerInfo.sex_2 = playerInfo.sex_A; 
				playerInfo.hat_2 = playerInfo.hat_A;
				playerInfo.exp_2 = playerInfo.exp_A;
				playerInfo.hp_2 = playerInfo.hp_A;
				playerInfo.mp_2 = playerInfo.mp_A;
				playerInfo.maxhp_2 = playerInfo.maxhp_A;
				playerInfo.maxmp_2 = playerInfo.maxmp_A;
				playerInfo.money_2 = playerInfo.money_A;
				playerInfo.cooldown_2 = playerInfo.cooldown_A;
				playerInfo.statusPoint_2 = playerInfo.statusPoint_A;
				playerInfo.skillPoint_2 = playerInfo.skillPoint_A;
				playerInfo.skills_2 = playerInfo.skills_A;
				playerInfo.coordX_2 = playerInfo.coordX_A;
				playerInfo.coordY_2 = playerInfo.coordY_A;
				playerInfo.inBattle_2 = playerInfo.inBattle_A;
				playerInfo.target_2 = playerInfo.target_A;
				playerInfo.itens_2 = playerInfo.itens_A;
				playerInfo.map_2 = playerInfo.map_A;
				playerInfo.party_2 = playerInfo.party_A;
				playerInfo.inCasting_2 = playerInfo.party_A;
				playerInfo.quest_2 = playerInfo.quest_A;
				playerInfo.state_2 = playerInfo.state_A;
			}
			if(num == 3) {
				playerInfo.name_3 = playerInfo.name_A;
				playerInfo.hair_3 = playerInfo.hair_A;
				playerInfo.set_3 = playerInfo.set_A;
				playerInfo.sex_3 = playerInfo.sex_A;
				playerInfo.job_3 = playerInfo.job_A;
				playerInfo.weapon_3 = playerInfo.weapon_A;
				playerInfo.level_3 = playerInfo.level_A;
				playerInfo.stats_3 = playerInfo.stats_A;
				playerInfo.sex_3 = playerInfo.sex_A; 
				playerInfo.hat_3 = playerInfo.hat_A;
				playerInfo.exp_3 = playerInfo.exp_A;
				playerInfo.hp_3 = playerInfo.hp_A;
				playerInfo.mp_3 = playerInfo.mp_A;
				playerInfo.maxhp_3 = playerInfo.maxhp_A;
				playerInfo.maxmp_3 = playerInfo.maxmp_A;
				playerInfo.money_3 = playerInfo.money_A;
				playerInfo.cooldown_3 = playerInfo.cooldown_A;
				playerInfo.statusPoint_3 = playerInfo.statusPoint_A;
				playerInfo.skillPoint_3 = playerInfo.skillPoint_A;
				playerInfo.skills_3 = playerInfo.skills_A;
				playerInfo.coordX_3 = playerInfo.coordX_A;
				playerInfo.coordY_3 = playerInfo.coordY_A;
				playerInfo.inBattle_3 = playerInfo.inBattle_A;
				playerInfo.target_3 = playerInfo.target_A;
				playerInfo.itens_3 = playerInfo.itens_A;
				playerInfo.map_3 = playerInfo.map_A;
				playerInfo.party_3 = playerInfo.party_A;
				playerInfo.inCasting_3 = playerInfo.party_A;
				playerInfo.quest_3 = playerInfo.quest_A;
				playerInfo.state_3 = playerInfo.state_A;
			}
			SaveData(playerInfo);
			savetimer = 800;
		}
	}
	
	public Player SetActivePlayerData(int num) {
		
		if(num == 1) {
			playerInfo.name_A = playerInfo.name_1;
			playerInfo.hair_A = playerInfo.hair_1;
			playerInfo.set_A = playerInfo.set_1;
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
			playerInfo.hair_A = playerInfo.hair_2;
			playerInfo.set_A = playerInfo.set_2;
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
			playerInfo.hair_A = playerInfo.hair_3;
			playerInfo.set_A = playerInfo.set_3;
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
		
		return playerInfo;
	}
	
	

	//[B] INTERFACE
	//Load elements for show in UI.
	
	public Sprite LoadCharacterMenu(String sex) {
		
		if(sex.equals("M")) {
			spr_master = atlas_basicset_m.createSprite("front1");
			spr_master.setSize(25, 40);
			spr_master.setPosition(11.5f, 20);
		}
		
		if(sex.equals("F")) {
			spr_master = atlas_basicset_f.createSprite("front1");
			spr_master.setSize(25, 40);
			spr_master.setPosition(11.4f, 19.8f);	
		}
		
		return spr_master;
	}
	
	public Sprite LoadCharacterHairMenu(String hair) {
		spr_master = atlas_hairs.createSprite(hair);
		spr_master.setSize(8, 12);
		spr_master.setPosition(19.5f, 47f);			
		return spr_master;
	}
	
	public Sprite LoadPlayerTagHair(String hair) {
		spr_master = atlas_hairs.createSprite(hair);
		spr_master.setSize(7, 11);
		spr_master.setPosition(-0.1f, 87);
		return spr_master;
	}
	
	
	public void AtualizaCamera(float cameraX, float cameraY) {
		cameraCoordsX = cameraX;
		cameraCoordsY = cameraY;
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
		
		if(type.equals("btnVoltar")) {
			spr_master = atlas_InterfaceCreate.createSprite("btnVoltar");
			spr_master.setSize(10,14);
			spr_master.setPosition(5,2);
		}
		
		if(type.equals("tagStart")) {
			spr_master = atlas_InterfaceCreate.createSprite("tagStart");
			spr_master.setSize(35,27);
			spr_master.setPosition(64,70);
		}
		
		
		//Player UI
		if(type.equals("playerTag")) {
			spr_master = atlas_InterfaceCreate.createSprite("tagPlayer");
			spr_master.setSize(25,20);
			spr_master.setPosition(0,80);
		}
					
		return spr_master;
	}
	
	public Sprite LoadInterfaceGamePlay(String type, String value, String extra) {
		//Player Tag
		if(type.equals("playerTag")) {
			spr_master = atlas_InterfaceCreate.createSprite("tagPlayer");
			spr_master.setSize(33,27);
			spr_master.setPosition(cameraCoordsX - 66,cameraCoordsY + 68);
			return spr_master;
		}
		
		//Hair Tag
		if(type.equals("hairTag")) {
			spr_master = atlas_hairs.createSprite(value);
			spr_master.setSize(9, 13);
			spr_master.setPosition(cameraCoordsX - 66,cameraCoordsY + 78);
			return spr_master;
		}
		
		//Mini Bar
		if(type.equals("minibar")) {
			spr_master = atlas_InterfaceCreate.createSprite("minibar");
			spr_master.setSize(16,14);
			spr_master.setPosition(cameraCoordsX + 51.5f,cameraCoordsY - 21.9f);
			return spr_master;
		}
		
		//Hot Bar
		if(type.equals("hotbar")) {
			spr_master = atlas_InterfaceCreate.createSprite("hotbar");
			spr_master.setSize(45,15);
			spr_master.setPosition(cameraCoordsX + 23,cameraCoordsY - 37);
			return spr_master;
		}
		
		//Outer Pad
		if(type.equals("outerpad")) {
			spr_master = atlas_InterfaceCreate.createSprite("outerpad");
			spr_master.setSize(20,30);
			spr_master.setPosition(cameraCoordsX - 60,cameraCoordsY - 30);
			return spr_master;
		}
		
		//Menu Bar
		if(type.equals("barMenu")) {
			spr_master = atlas_InterfaceCreate.createSprite("barMenu");
			spr_master.setSize(10,80);
			spr_master.setPosition(cameraCoordsX + 58,cameraCoordsY - 5);
			return spr_master;
		}
		
		//Menu Status
		if(type.equals("menuStatus")) {
			spr_master = atlas_InterfaceCreate.createSprite("menuStatus");
			spr_master.setSize(100,100);
			spr_master.setPosition(cameraCoordsX - 50,cameraCoordsY - 15);
			return spr_master;
		}
		
		//Menu Itens
		if(type.equals("menuItens")) {
			spr_master = atlas_InterfaceCreate.createSprite("menuItens");
			spr_master.setSize(100,100);
			spr_master.setPosition(cameraCoordsX - 50,cameraCoordsY - 15);
			return spr_master;
		}
		
		//Menu Skills
		if(type.equals("menuSkills")) {
			spr_master = atlas_InterfaceCreate.createSprite("menuSkills");
			spr_master.setSize(100,100);
			spr_master.setPosition(cameraCoordsX - 50,cameraCoordsY - 15);
			return spr_master;
		}
		
		//Menu Social
		if(type.equals("menuSocial")) {
			spr_master = atlas_InterfaceCreate.createSprite("menuSocial");
			spr_master.setSize(100,100);
			spr_master.setPosition(cameraCoordsX - 50,cameraCoordsY - 15);
			return spr_master;
		}
		
		//Menu Pet
		if(type.equals("menuPet")) {
			spr_master = atlas_InterfaceCreate.createSprite("menuPet");
			spr_master.setSize(100,100);
			spr_master.setPosition(cameraCoordsX - 50,cameraCoordsY - 15);
			return spr_master;
		}
		
		//Menu Pet
		if(type.equals("menuConfig")) {
			spr_master = atlas_InterfaceCreate.createSprite("menuConfig");
			spr_master.setSize(100,100);
			spr_master.setPosition(cameraCoordsX - 50,cameraCoordsY - 15);
			return spr_master;
		}
		
		//innerpad
		if(type.equals("innerpad") && value.equals("stop")) {
			spr_master = atlas_InterfaceCreate.createSprite("innerpad");
			spr_master.setSize(12,22);
			spr_master.setPosition(cameraCoordsX - 56,cameraCoordsY - 26);
			return spr_master;
		}
		if(type.equals("innerpad") && value.equals("walk") && extra.equals("right")) {
			spr_master = atlas_InterfaceCreate.createSprite("innerpad");
			spr_master.setSize(12,22);
			spr_master.setPosition(cameraCoordsX - 51,cameraCoordsY - 26);
			return spr_master;
		}		
		if(type.equals("innerpad") && value.equals("walk") && extra.equals("left")) {
			spr_master = atlas_InterfaceCreate.createSprite("innerpad");
			spr_master.setSize(12,22);
			spr_master.setPosition(cameraCoordsX - 61,cameraCoordsY - 26);
			return spr_master;
		}
		if(type.equals("innerpad") && value.equals("walk") && extra.equals("front")) {
			spr_master = atlas_InterfaceCreate.createSprite("innerpad");
			spr_master.setSize(12,22);
			spr_master.setPosition(cameraCoordsX - 56,cameraCoordsY - 31);
			return spr_master;
		}
		if(type.equals("innerpad") && value.equals("walk") && extra.equals("back")) {
			spr_master = atlas_InterfaceCreate.createSprite("innerpad");
			spr_master.setSize(12,22);
			spr_master.setPosition(cameraCoordsX - 56,cameraCoordsY - 19);
			return spr_master;
		}
		
		if(type.equals("innerpad") && value.equals("walk") && extra.equals("right-front")) {
			spr_master = atlas_InterfaceCreate.createSprite("innerpad");
			spr_master.setSize(12,22);
			spr_master.setPosition(cameraCoordsX - 51,cameraCoordsY - 31);
			return spr_master;
		}
		
		if(type.equals("innerpad") && value.equals("walk") && extra.equals("left-front")) {
			spr_master = atlas_InterfaceCreate.createSprite("innerpad");
			spr_master.setSize(12,22);
			spr_master.setPosition(cameraCoordsX - 61,cameraCoordsY - 31);
			return spr_master;
		}
		
		if(type.equals("innerpad") && value.equals("walk") && extra.equals("front-right")) {
			spr_master = atlas_InterfaceCreate.createSprite("innerpad");
			spr_master.setSize(12,22);
			spr_master.setPosition(cameraCoordsX - 51,cameraCoordsY - 31);
			return spr_master;
		}
		
		if(type.equals("innerpad") && value.equals("walk") && extra.equals("front-left")) {
			spr_master = atlas_InterfaceCreate.createSprite("innerpad");
			spr_master.setSize(12,22);
			spr_master.setPosition(cameraCoordsX - 61,cameraCoordsY - 31);
			return spr_master;
		}
		
		if(type.equals("innerpad") && value.equals("walk") && extra.equals("right-back")) {
			spr_master = atlas_InterfaceCreate.createSprite("innerpad");
			spr_master.setSize(12,22);
			spr_master.setPosition(cameraCoordsX - 51,cameraCoordsY - 19);
			return spr_master;
		}
		
		if(type.equals("innerpad") && value.equals("walk") && extra.equals("left-back")) {
			spr_master = atlas_InterfaceCreate.createSprite("innerpad");
			spr_master.setSize(12,22);
			spr_master.setPosition(cameraCoordsX - 61,cameraCoordsY - 19);
			return spr_master;
		}
		
		if(type.equals("innerpad") && value.equals("walk") && extra.equals("back-right")) {
			spr_master = atlas_InterfaceCreate.createSprite("innerpad");
			spr_master.setSize(12,22);
			spr_master.setPosition(cameraCoordsX - 51,cameraCoordsY - 19);
			return spr_master;
		}
		
		if(type.equals("innerpad") && value.equals("walk") && extra.equals("back-left")) {
			spr_master = atlas_InterfaceCreate.createSprite("innerpad");
			spr_master.setSize(12,22);
			spr_master.setPosition(cameraCoordsX - 61,cameraCoordsY - 19);
			return spr_master;
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
	
	
	
	
	
	// [D] Character Code
	public void RegenerateHPMP() {
		recoverytimer--;
		if(recoverytimer <= 0) {
			playerHP = Integer.parseInt(playerInfo.hp_A);
			playerMP = Integer.parseInt(playerInfo.mp_A);
			
			playerHPMax = Integer.parseInt(playerInfo.maxhp_A);
			playerMPMax = Integer.parseInt(playerInfo.maxmp_A);
			
			playerHP = playerHP + 20;
			playerMP = playerMP + 20;
			
			if(playerHP >= playerHPMax) { playerHP = playerHPMax; }
			if(playerMP >= playerMPMax) { playerMP = playerMPMax; }
			
			playerInfo.hp_A = String.valueOf(playerHP);
			playerInfo.mp_A = String.valueOf(playerMP);
			
			recoverytimer = 700;
		}
	}
	
	public Sprite MovPlayerCharacter(String set,String sex, String walk, String state, boolean menu) {
		
		if(!menu) {
			if(state.equals("right") && walk.equals("walk")) {
				countFrameMov++;
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX + playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);
			}
			if(state.equals("left") && walk.equals("walk")) {
				countFrameMov++;
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX - playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);
			}
			if(state.equals("front") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY - playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
			}
			if(state.equals("back") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY + playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
			}
			
			if(state.equals("front-left") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY - playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
				
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX - playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);				
			}
			
			if(state.equals("front-right") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY - playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
				
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX + playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);				
			}
			
			if(state.equals("back-left") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY + playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
				
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX - playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);				
			}
			
			if(state.equals("back-right") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY + playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
				
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX + playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);				
			}
			
			if(state.equals("right-front") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY - playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
				
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX + playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);				
			}
			
			if(state.equals("right-back") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY + playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
				
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX + playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);				
			}
			
			if(state.equals("left-front") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY - playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
				
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX - playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);				
			}
			
			if(state.equals("left-back") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY + playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
				
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX - playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);				
			}
		
			if(countFrameMov >= 0 && countFrameMov <= 10) { frame = 1; }
			if(countFrameMov >= 10 && countFrameMov <= 20) { frame = 2; }
			if(countFrameMov >= 20 && countFrameMov <= 30) { frame = 1; }
			if(countFrameMov >= 30 && countFrameMov <= 40) { frame = 3;}
			if(countFrameMov >= 40) { countFrameMov = 1; frame = 1; }
			
		}
		
		//walk = "walk";
		//frame = 3;
		
		//Male
		if(sex.equals("M")) {
			if(set.equals("basicset_m")) {
				
				//Stop
				if(walk.equals("stop")) {
					frame = 1;
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 1) { spr_master = atlas_basicset_m.createSprite("front1"); return spr_master; }
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 1) { spr_master = atlas_basicset_m.createSprite("back1"); return spr_master; }
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 1) { spr_master = atlas_basicset_m.createSprite("left1"); return spr_master; }
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 1) { spr_master = atlas_basicset_m.createSprite("right1"); return spr_master; }
				}
				
				//Walk Front
				if(walk.equals("walk")) {
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 1) { spr_master = atlas_basicset_m.createSprite("front1"); return spr_master; }
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 2) { spr_master = atlas_basicset_m.createSprite("front2"); return spr_master; }
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 3) { spr_master = atlas_basicset_m.createSprite("front3"); return spr_master; }
					
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 1) { spr_master = atlas_basicset_m.createSprite("back1"); return spr_master; }
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 2) { spr_master = atlas_basicset_m.createSprite("back1"); return spr_master; }
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 3) { spr_master = atlas_basicset_m.createSprite("back1"); return spr_master; }
					
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 1) { spr_master = atlas_basicset_m.createSprite("left1"); return spr_master; }
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 2) { spr_master = atlas_basicset_m.createSprite("left2"); return spr_master; }
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 3) { spr_master = atlas_basicset_m.createSprite("left3"); return spr_master; }
					
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 1) { spr_master = atlas_basicset_m.createSprite("right1"); return spr_master; }
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 2) { spr_master = atlas_basicset_m.createSprite("right2"); return spr_master; }
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 3) { spr_master = atlas_basicset_m.createSprite("right3"); return spr_master; }
				}				
			}
		}
		
		//Female
		if(sex.equals("F")) {
				if(set.equals("basicset_f")) {
					
				//Stop
				if(walk.equals("stop")) {
					if(state.equals("front") || state.equals("front-left") || state.equals("front-right")) { spr_master = atlas_basicset_f.createSprite("front1"); return spr_master; }
					if(state.equals("back") || state.equals("back-left") || state.equals("back-right")) { spr_master = atlas_basicset_f.createSprite("back1"); return spr_master; }
					if(state.equals("left") || state.equals("left-front") || state.equals("left-back")) { spr_master = atlas_basicset_f.createSprite("left1"); return spr_master; }
					if(state.equals("right") || state.equals("right-front") || state.equals("right-back")) { spr_master = atlas_basicset_f.createSprite("right1"); return spr_master; }
				}
				
				//Walk Front
				if(walk.equals("Walk")) {
					
				}				
			}
		}
		
		
		return spr_master;	
		
	}
	
	public Sprite MovPlayerHair(String hair,String sex, String state, String gameState) {
		
		if(gameState.equals("Menu-Status")) {
			if(sex.equals("M")) { spr_master = atlas_hairs.createSprite(hair); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7, playerPosY + 23f); return spr_master; }
			
			if(sex.equals("F")) {}
		}
		
		
		if(sex.equals("M")) {		
			if((state.equals("front") || state.equals("front-left") || state.equals("front-right"))  && frame == 1) { spr_master = atlas_hairs.createSprite(hair); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7, playerPosY + 23f); return spr_master; }
			if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 1) { spr_master = atlas_hairs.createSprite(hair + "up"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.3f, playerPosY + 22.7f); return spr_master; }
			if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 1) { spr_master = atlas_hairs.createSprite(hair + "left"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.5f, playerPosY + 23); return spr_master; }
			if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 1) { spr_master = atlas_hairs.createSprite(hair + "right"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.8f, playerPosY + 23); return spr_master; }
			
			if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.2f, playerPosY + 22.7f); return spr_master; }
			if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair + "up"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.3f, playerPosY + 22.7f); return spr_master; }
			if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair + "left"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 6.8f, playerPosY + 22.3f); return spr_master; }
			if((state.equals("right")  || state.equals("right-front") || state.equals("right-back")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair + "right"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 6.5f, playerPosY + 22.3f); return spr_master; }
			
			if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.2f, playerPosY + 22.4f); return spr_master; }
			if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair + "up"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.3f, playerPosY + 22.7f); return spr_master; }
			if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair + "left"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.2f, playerPosY + 22.8f); return spr_master; }
			if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair + "right"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.4f, playerPosY + 22.8f); return spr_master; }
		}
		
		if(sex.equals("F")) {			
			if(state.equals("front")) { spr_master = atlas_hairs.createSprite(hair); }
			if(state.equals("back")) { spr_master = atlas_hairs.createSprite(hair + "up"); }
			if(state.equals("left")) { spr_master = atlas_hairs.createSprite(hair + "left"); }
			if(state.equals("right")) { spr_master = atlas_hairs.createSprite(hair + "right"); }		
		}
		
		return spr_master;
	}
	
	public Sprite PlayerHairCharacterSelect(String hair,String sex, String state) {
		if(sex.equals("M")) {		
			if(state.equals("front")) { 
				spr_master = atlas_hairs.createSprite(hair); 
				spr_master.setSize(7, 10); 
				spr_master.setPosition(playerPosX + 7, playerPosY + 23f); 
				return spr_master; 
			}
		}
		
		if(sex.equals("F")) {			
			if(state.equals("front")) { 
				spr_master = atlas_hairs.createSprite(hair); 
				return spr_master; 
				}	
		}
		
		return spr_master;
	}
	
	
	
	
}
