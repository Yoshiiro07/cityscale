package com.moonbolt.cityscale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	//[E] Itens and Inventory
	//[F] Online
	//[G] Battle
	
	//Data Manager Variables
	private Json json;
	private FileHandle file;
	private Random randnumber;
	private Player playerInfo;
	private Player playerInfoOnline;
	private ArrayList<Monster> lstMobs;
	
	//Primitive Variable
	private int activeCharNumber;
	private int countFrameMov = 1;
	private int frame = 1;
	private float playerPosX;
	private float playerPosY;
	private float playerSpeed = 0.4f;
	private int recoverytimer = 700;
	private int savetimer = 800;
	private int frameMob = 0;
	private int skillMob = 0;
	
	
	private int playerHP;
	private int playerMP;
	private int playerHPMax;
	private int playerMPMax;
	private int coordsXint = 0;
	private int coordsYint = 0;
	private float coordsXfloat = 0;
	private float coordsYfloat = 0;
	private int setIndexPlayerOnline = 0;
	
	//Online
	private int threahCount = 0;
	private boolean onlineCheck = false; 
	private ArrayList<String> lstChats;
	private ArrayList<Player> lstPlayersOnline;
	private String onlineDataReceive = "";
	private String[] dataSplit;
	private String[] dataInfoSplit;
	

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
	private TextureAtlas atlas_shops;
	private TextureAtlas atlas_itens;
	
	private TextureAtlas atlas_skills;
	private TextureAtlas atlas_iconSkills;
	private TextureAtlas atlas_tripleattack;
	
	private TextureAtlas atlas_basicknife;
	private TextureAtlas atlas_weapon;
	
	private TextureAtlas atlas_MonstersSewer;
	
	
	// CONSTRUCTOR
	public GameControl() {
		this.playerInfo = new Player();
		
		this.tex_teste = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		this.spr_master = new Sprite(tex_teste);
				
		//Data Manager
		json = new Json();
		playerInfo = new Player();
		playerInfoOnline = new Player();
		lstPlayersOnline = new ArrayList<Player>();
		lstChats = new ArrayList<String>();
		lstMobs = new ArrayList<Monster>();
		CheckData();
		
		//Atlas Section
		//InterfaceCreate
		atlas_InterfaceCreate = new TextureAtlas(Gdx.files.internal("data/assets/interfaceCreate.txt"));	
		atlas_shops = new TextureAtlas(Gdx.files.internal("data/assets/shops.txt"));	
		
		//Character Hairs
		atlas_hairs = new TextureAtlas(Gdx.files.internal("data/characters/players/hair/hairs.txt"));
		
		//Character Sets
		atlas_basicset_m = new TextureAtlas(Gdx.files.internal("data/characters/players/basicset_m/basicset_m.txt"));
		atlas_basicset_f = new TextureAtlas(Gdx.files.internal("data/characters/players/basicset_f/basicset_f.txt"));
		
		//Itens 
		atlas_itens = new TextureAtlas(Gdx.files.internal("data/itens/itens.txt"));
		
		//Weapons Sets
		atlas_weapon = new TextureAtlas(Gdx.files.internal("data/weapons/nknifes.txt"));
		
		//Skill Icons
		atlas_iconSkills = new TextureAtlas(Gdx.files.internal("data/skills/skillicons.txt"));  //tripleattack
		
		//Skill Effects
		atlas_tripleattack = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/tripleattack.txt"));
		
		//Monsters
		atlas_MonstersSewer = new TextureAtlas(Gdx.files.internal("data/monsters/mobsForest.txt"));
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
		String itensList = "";
		
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
			playerInfo.pos_1 = "none";
			playerInfo.walk_1 = "stop";
			playerInfo.side_1 = "front";
			playerInfo.maxhp_1 = "100";
			playerInfo.maxmp_1 = "100";
			playerInfo.money_1 = "50";
			playerInfo.cooldown_1 = "";
			playerInfo.statusPoint_1 = "0";
			playerInfo.skillPoint_1 = "0";
			playerInfo.skills_1 = "none";
			playerInfo.inBattle_1 = "no";
			playerInfo.target_1 = "none";
			playerInfo.map_1 = "MetroStation";
			playerInfo.party_1 = "none";
			playerInfo.inCasting_1 = "no";
			playerInfo.quest_1 = "Starting";
			playerInfo.state_1 = "stop";
			playerInfo.hotkey1_1 = "none";
			playerInfo.hotkey2_1 = "none";
					
			for(int i = 0; i < 48; i++) {
				itensList = itensList + "[HPCAN#1]-";
			}			
			playerInfo.itens_1 = itensList;
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
			playerInfo.pos_2 = "none";
			playerInfo.walk_2 = "stop";
			playerInfo.side_2 = "front";
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
			playerInfo.hotkey1_2 = "none";
			playerInfo.hotkey2_2 = "none";
			
			for(int i = 0; i < 48; i++) {
				itensList = itensList + "[NONE]-";
			}			
			playerInfo.itens_2 = itensList;
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
			playerInfo.pos_3 = "none";
			playerInfo.walk_3 = "stop";
			playerInfo.side_3 = "front";
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
			playerInfo.hotkey1_3 = "none";
			playerInfo.hotkey2_3 = "none";
			
			for(int i = 0; i < 48; i++) {
				itensList = itensList + "[NONE]-";
			}			
			playerInfo.itens_3 = itensList;
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
				playerInfo.pos_1 = playerInfo.pos_A;
				playerInfo.walk_1 = playerInfo.walk_A;
				playerInfo.side_1 = playerInfo.side_A;
				playerInfo.inBattle_1 = playerInfo.inBattle_A;
				playerInfo.target_1 = playerInfo.target_A;
				playerInfo.itens_1 = playerInfo.itens_A;
				playerInfo.map_1 = playerInfo.map_A;
				playerInfo.party_1 = playerInfo.party_A;
				playerInfo.inCasting_1 = playerInfo.party_A;
				playerInfo.quest_1 = playerInfo.quest_A;
				playerInfo.state_1 = playerInfo.state_A;
				playerInfo.hotkey1_1 = playerInfo.hotkey1_A;
				playerInfo.hotkey2_1 = playerInfo.hotkey2_A;
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
				playerInfo.pos_2 = playerInfo.pos_A;
				playerInfo.walk_2 = playerInfo.walk_A;
				playerInfo.side_2 = playerInfo.side_A;
				playerInfo.inBattle_2 = playerInfo.inBattle_A;
				playerInfo.target_2 = playerInfo.target_A;
				playerInfo.itens_2 = playerInfo.itens_A;
				playerInfo.map_2 = playerInfo.map_A;
				playerInfo.party_2 = playerInfo.party_A;
				playerInfo.inCasting_2 = playerInfo.party_A;
				playerInfo.quest_2 = playerInfo.quest_A;
				playerInfo.state_2 = playerInfo.state_A;
				playerInfo.hotkey1_2 = playerInfo.hotkey1_A;
				playerInfo.hotkey2_2 = playerInfo.hotkey2_A;
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
				playerInfo.pos_3 = playerInfo.pos_A;
				playerInfo.walk_3 = playerInfo.walk_A;
				playerInfo.side_3 = playerInfo.side_A;
				playerInfo.inBattle_3 = playerInfo.inBattle_A;
				playerInfo.target_3 = playerInfo.target_A;
				playerInfo.itens_3 = playerInfo.itens_A;
				playerInfo.map_3 = playerInfo.map_A;
				playerInfo.party_3 = playerInfo.party_A;
				playerInfo.inCasting_3 = playerInfo.party_A;
				playerInfo.quest_3 = playerInfo.quest_A;
				playerInfo.state_3 = playerInfo.state_A;
				playerInfo.hotkey1_3 = playerInfo.hotkey1_A;
				playerInfo.hotkey2_3 = playerInfo.hotkey2_A;
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
			playerInfo.pos_A = playerInfo.pos_1;
			playerInfo.walk_A = playerInfo.walk_1;
			playerInfo.side_A = playerInfo.side_1;
			playerInfo.inBattle_A = playerInfo.inBattle_1;
			playerInfo.target_A = playerInfo.target_1;
			playerInfo.itens_A = playerInfo.itens_1;
			playerInfo.map_A = playerInfo.map_1;
			playerInfo.party_A = playerInfo.party_1;
			playerInfo.inCasting_A = playerInfo.party_1;
			playerInfo.quest_A = playerInfo.quest_1;
			playerInfo.state_A = playerInfo.state_1;
			playerInfo.hotkey1_A = playerInfo.hotkey1_1;
			playerInfo.hotkey2_A = playerInfo.hotkey2_1;
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
			playerInfo.pos_A = playerInfo.pos_2;
			playerInfo.walk_A = playerInfo.walk_2;
			playerInfo.side_A = playerInfo.side_2;
			playerInfo.inBattle_A = playerInfo.inBattle_2;
			playerInfo.target_A = playerInfo.target_2;
			playerInfo.itens_A = playerInfo.itens_2;
			playerInfo.map_A = playerInfo.map_2;
			playerInfo.party_A = playerInfo.party_2;
			playerInfo.inCasting_A = playerInfo.party_2;
			playerInfo.quest_A = playerInfo.quest_2;
			playerInfo.state_A = playerInfo.state_2;
			playerInfo.hotkey1_A = playerInfo.hotkey1_2;
			playerInfo.hotkey2_A = playerInfo.hotkey2_2;
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
			playerInfo.pos_A = playerInfo.pos_3;
			playerInfo.walk_A = playerInfo.walk_3;
			playerInfo.side_A = playerInfo.side_3;
			playerInfo.inBattle_A = playerInfo.inBattle_3;
			playerInfo.target_A = playerInfo.target_3;
			playerInfo.itens_A = playerInfo.itens_3;
			playerInfo.map_A = playerInfo.map_3;
			playerInfo.party_A = playerInfo.party_3;
			playerInfo.inCasting_A = playerInfo.party_3;
			playerInfo.quest_A = playerInfo.quest_3;
			playerInfo.state_A = playerInfo.state_3;
			playerInfo.hotkey1_A = playerInfo.hotkey1_3;
			playerInfo.hotkey2_A = playerInfo.hotkey2_3;
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
		
		//Shops 
		if(type.equals("RefriShop")) {
			spr_master = atlas_shops.createSprite("shopRefri");
			spr_master.setSize(50,80);
			spr_master.setPosition(cameraCoordsX + 10,cameraCoordsY - 5);
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
				playerInfo.side_A = "right";
			}
			if(state.equals("left") && walk.equals("walk")) {
				countFrameMov++;
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX - playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);
				playerInfo.side_A = "left";
			}
			if(state.equals("front") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY - playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
				playerInfo.side_A = "front";
			}
			if(state.equals("back") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY + playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
				playerInfo.side_A = "back";
			}
			
			if(state.equals("front-left") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY - playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
				
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX - playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);	
				playerInfo.side_A = "left";
			}
			
			if(state.equals("front-right") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY - playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
				
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX + playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);	
				playerInfo.side_A = "right";
			}
			
			if(state.equals("back-left") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY + playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
				
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX - playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);	
				playerInfo.side_A = "left";
			}
			
			if(state.equals("back-right") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY + playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
				
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX + playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);	
				playerInfo.side_A = "right";
			}
			
			if(state.equals("right-front") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY - playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
				
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX + playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);
				playerInfo.side_A = "right";
			}
			
			if(state.equals("right-back") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY + playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
				
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX + playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);
				playerInfo.side_A = "right";
			}
			
			if(state.equals("left-front") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY - playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
				
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX - playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);
				playerInfo.side_A = "left";
			}
			
			if(state.equals("left-back") && walk.equals("walk")) {
				countFrameMov++;
				playerPosY = Float.parseFloat(playerInfo.coordY_A);
				playerPosY = playerPosY + playerSpeed;
				playerInfo.coordY_A = String.valueOf(playerPosY);
				
				playerPosX = Float.parseFloat(playerInfo.coordX_A);
				playerPosX = playerPosX - playerSpeed;
				playerInfo.coordX_A = String.valueOf(playerPosX);
				playerInfo.side_A = "left";
			}
		
			playerInfo.pos_A = String.valueOf(frame);
			playerInfo.walk_A = walk;
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
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 2) { spr_master = atlas_basicset_m.createSprite("back2"); return spr_master; }
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 3) { spr_master = atlas_basicset_m.createSprite("back3"); return spr_master; }
					
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
	
	public Sprite SkillHotbar(String job, String skill) {
		
		if(job.equals("All")) {
			spr_master = atlas_iconSkills.createSprite("btnacao");
			spr_master.setSize(6.8f,13);
			spr_master.setPosition(cameraCoordsX + 24,cameraCoordsY - 36);
			return spr_master;
		}
				
		if(job.equals("Novice")) {
			if(skill.equals("tripleattack")) {
				spr_master = atlas_iconSkills.createSprite("btntripleattack");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 31,cameraCoordsY - 36);
				return spr_master;
			}	
			
			if(skill.equals("icecrystal")) {
				spr_master = atlas_iconSkills.createSprite("btnthundercloud");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 38.2f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("berserk")) {
				spr_master = atlas_iconSkills.createSprite("btnrockbound");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 45.4f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("precision")) {
				spr_master = atlas_iconSkills.createSprite("btnregen");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 52.8f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("fireball")) {
				spr_master = atlas_iconSkills.createSprite("btnsteal");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 60,cameraCoordsY - 36);
				return spr_master;
			}
		}
		
		return spr_master;
	}
	
	//[E] Itens and Inventory
	public String ItemBuy(String shopName, int numItem) {
		
		int playerMoney = Integer.parseInt(playerInfo.money_A);
		
		if(shopName.equals("RefriShop")) {
			//HPCAN
			if(numItem == 1) {
				if(playerMoney >= 0) {
					AddItemBag("HPCAN");
					return "Comprado";
				} 
				else {
					return "Dinheiro Insuficiente";
				}
			}
		}
		
		
		return "";
	}
	
	public String ShowQuantityItem(int num) {
		//Structure: [HPCAN#3]
		String qtd = "";
		String item = "";
		String[] lstItem = playerInfo.itens_A.split("-");
		String[] itemSplit;
			
		item = lstItem[num];
		if(!item.equals("[NONE]")) {
			itemSplit = item.split("#");
			item = itemSplit[1].replace("]", "");		
			qtd = item;
		}
		else {
			qtd = "";
		}		
		return qtd;
	}
	
	public Sprite ShowItem(int num, int tab, float coordsX, float coordsY) {
		//Structure: [HPCAN#3]
		String[] lstItem = playerInfo.itens_A.split("-");
		String[] itemSplit;
		String item;
		
		item = lstItem[num];
		if(!item.equals("[NONE]")) {
			itemSplit = item.split("#");
			item = itemSplit[0].replace("[", "");		
			spr_master = ItemLog(item);
			
			if((num == 0 && tab ==  1)  || 
		       (num == 12 && tab == 2)  || 
		       (num == 24 && tab == 3)  || 
		       (num == 36 && tab == 4)) { 
				spr_master.setPosition(coordsX - 47, coordsY + 43); spr_master.setSize(9, 14); return spr_master; 
				}
			
			if((num == 1 && tab ==  1)  || 
		       (num == 13 && tab == 2)  || 
		       (num == 25 && tab == 3)  || 
		       (num == 37 && tab == 4)) { 
				spr_master.setPosition(coordsX - 37.5f, coordsY + 43); spr_master.setSize(9, 14); return spr_master; 
				}
			
			if((num == 2 && tab ==  1)  || 
		       (num == 14 && tab == 2)  || 
		       (num == 26 && tab == 3)  || 
		       (num == 38 && tab == 4)) { 
				spr_master.setPosition(coordsX - 27.8f, coordsY + 43); spr_master.setSize(9, 14); return spr_master; 
				}
			
			if((num == 3 && tab ==  1)  || 
		       (num == 15 && tab == 2)  || 
		       (num == 27 && tab == 3)  || 
		       (num == 39 && tab == 4)) { 
				spr_master.setPosition(coordsX - 18f, coordsY + 43); spr_master.setSize(9, 14); return spr_master;
				}
			
			if((num == 4 && tab ==  1)  || 
		       (num == 16 && tab == 2)  || 
		       (num == 28 && tab == 3)  || 
		       (num == 40 && tab == 4)) { 
				spr_master.setPosition(coordsX - 47, coordsY + 27); spr_master.setSize(9, 14); return spr_master;
				}
			
			if((num == 5 && tab ==  1)  || 
		       (num == 17 && tab == 2)  || 
		       (num == 29 && tab == 3)  || 
		       (num == 41 && tab == 4)) { 
				spr_master.setPosition(coordsX - 37.5f, coordsY + 27); spr_master.setSize(9, 14); return spr_master;
				}
			
			if((num == 5 && tab ==  1)  || 
		       (num == 17 && tab == 2)  || 
		       (num == 29 && tab == 3)  || 
		       (num == 41 && tab == 4)) { 
				spr_master.setPosition(coordsX - 37.5f, coordsY + 27); spr_master.setSize(9, 14); return spr_master;
				}
			
			if((num == 6 && tab ==  1)  || 
		       (num == 18 && tab == 2)  || 
		       (num == 30 && tab == 3)  || 
		       (num == 42 && tab == 4)) { 
				spr_master.setPosition(coordsX - 27.8f, coordsY + 27); spr_master.setSize(9, 14); return spr_master;
				}
			
			if((num == 7 && tab ==  1)  || 
		       (num == 19 && tab == 2)  || 
		       (num == 31 && tab == 3)  || 
		       (num == 43 && tab == 4)) { 
				spr_master.setPosition(coordsX - 18f, coordsY + 27); spr_master.setSize(9, 14); return spr_master;
				}
			
			if((num == 8 && tab ==  1)  || 
		       (num == 20 && tab == 2)  || 
		       (num == 32 && tab == 3)  || 
		       (num == 44 && tab == 4)) { 
				spr_master.setPosition(coordsX - 47, coordsY + 11.5f); spr_master.setSize(9, 14); return spr_master;
				}
			
			if((num == 9 && tab ==  1)  || 
		       (num == 21 && tab == 2)  || 
		       (num == 33 && tab == 3)  || 
		       (num == 45 && tab == 4)) { 
				spr_master.setPosition(coordsX - 37.5f, coordsY + 11.5f); spr_master.setSize(9, 14); return spr_master;
				}
			
			if((num == 10 && tab ==  1)  || 
		       (num == 22 && tab == 2)  || 
		       (num == 34 && tab == 3)  || 
		       (num == 46 && tab == 4)) { 
				spr_master.setPosition(coordsX - 27.8f, coordsY + 11.5f); spr_master.setSize(9, 14); return spr_master;
				}
			
			if((num == 11 && tab ==  1)  || 
		       (num == 23 && tab == 2)  || 
		       (num == 35 && tab == 3)  || 
		       (num == 47 && tab == 4)) {
				spr_master.setPosition(coordsX - 18f, coordsY + 11.5f); spr_master.setSize(9, 14); return spr_master;
				}
		}
		
		spr_master = null;
		return spr_master;
	}
	
	public Sprite ShowEquippedItens(int num, float coordsX, float coordsY) {
		
		//Weapon
		if(num == 1) {
			if(playerInfo.weapon_A.equals("basic_knife")) { 
				spr_master = atlas_itens.createSprite("basicknife"); 
				spr_master.setPosition(coordsX -1.5f, coordsY + 11f); 
				spr_master.setSize(9, 14); 
				return spr_master; 
			}
		}
		
		//Set
		if(num == 2) {
			//Male
			if(playerInfo.set_A.equals("basicset_m")) {  
				spr_master = atlas_itens.createSprite("basicset"); 
				spr_master.setPosition(coordsX -1.5f, coordsY + 26.8f); 
				spr_master.setSize(9, 14); 
				return spr_master; 
			}
			
			//Female
			if(playerInfo.set_A.equals("basicset_f")) {  
				spr_master = atlas_itens.createSprite("basicsetF"); 
				spr_master.setPosition(coordsX -1.5f, coordsY + 26.8f); 
				spr_master.setSize(9, 14); 
				return spr_master; 
			}
		}
		
		//Hat
		if(num == 3) {
			//coordsX -1.5f, coordsY + 42.8f
		}
		
		return spr_master;
		
	}
	
	public Sprite ShowItemHotKey(int numItem,float coordsX, float coordsY) {
		String itemHotKey1 = playerInfo.hotkey1_A;
		String itemHotKey2 = playerInfo.hotkey2_A;
				
		if(numItem == 1) {
			if(itemHotKey1.equals("none")) { return null; }
			if(itemHotKey1.equals("HPCAN")) { spr_master = atlas_itens.createSprite("hpcan"); }
			if(itemHotKey1.equals("MPCAN")) { spr_master = atlas_itens.createSprite("mpcan"); }
			if(itemHotKey1.equals("STCAN")) { spr_master = atlas_itens.createSprite("stcan"); }
			if(itemHotKey1.equals("CHIPZ")) { spr_master = atlas_itens.createSprite("chipz"); }
					
			spr_master.setPosition(coordsX + 37.8f, coordsY + 42.5f);
			spr_master.setSize(9, 14);
		}
		
		if(numItem == 2) {
			if(itemHotKey2.equals("none")) { return null; }
			if(itemHotKey2.equals("HPCAN")) { spr_master = atlas_itens.createSprite("hpcan"); }
			if(itemHotKey2.equals("MPCAN")) { spr_master = atlas_itens.createSprite("mpcan"); }
			if(itemHotKey2.equals("STCAN")) { spr_master = atlas_itens.createSprite("stcan"); }
			if(itemHotKey2.equals("CHIPZ")) { spr_master = atlas_itens.createSprite("chipz"); }
						
			spr_master.setPosition(coordsX + 37.8f, coordsY + 27.3f);
			spr_master.setSize(9, 14);
		}	
		return spr_master;
	}
	
	public Sprite ShowItemBar(int numItem, float coordsX, float coordsY) {
		String item1 = playerInfo.hotkey1_A;
		String item2 = playerInfo.hotkey2_A;
		
		if(numItem == 1) {
			if(item1.equals("none")) { return null; }
			if(item1.equals("HPCAN")) { spr_master = atlas_itens.createSprite("hpcan"); }
			if(item1.equals("MPCAN")) { spr_master = atlas_itens.createSprite("mpcan"); }
			if(item1.equals("STCAN")) { spr_master = atlas_itens.createSprite("stcan"); }
			if(item1.equals("CHIPZ")) { spr_master = atlas_itens.createSprite("chipz"); }
						
			spr_master.setPosition(coordsX + 52.1f, coordsY - 21.4f);
			spr_master.setSize(7, 13);
		}
		
		if(numItem == 2) {
			if(item2.equals("none")) { return null; }
			if(item2.equals("HPCAN")) { spr_master = atlas_itens.createSprite("hpcan"); }
			if(item2.equals("MPCAN")) { spr_master = atlas_itens.createSprite("mpcan"); }
			if(item2.equals("STCAN")) { spr_master = atlas_itens.createSprite("stcan"); }
			if(item2.equals("CHIPZ")) { spr_master = atlas_itens.createSprite("chipz"); }
						
			spr_master.setPosition(coordsX + 60.1f, coordsY - 21.4f);
			spr_master.setSize(7, 13);
		}
		
		return spr_master;
	}
	
	
	public void WipeHotkey(int numItem) {
		if(numItem == 1) {
			playerInfo.hotkey1_A = "none";
		}
		if(numItem == 2) {
			playerInfo.hotkey2_A = "none";
		}
	}
	
	
	public Sprite ItemLog(String item) {
		if(item.equals("HPCAN")) { spr_master = atlas_itens.createSprite("hpcan"); return spr_master; }
		if(item.equals("MPCAN")) { spr_master = atlas_itens.createSprite("mpcan"); return spr_master; }
		if(item.equals("STCAN")) { spr_master = atlas_itens.createSprite("stcan"); return spr_master; }
		if(item.equals("CHIPZ")) { spr_master = atlas_itens.createSprite("chipz"); return spr_master; }
		
		return spr_master;
	}
	
	public Sprite SelectedSprites(String name, float coordsX, float coordsY) {
		
		if(name.equals("Discart")) {
			spr_master = atlas_InterfaceCreate.createSprite("selected");
			spr_master.setPosition(coordsX - 46.8f, coordsY - 10);
			spr_master.setSize(4, 7);
			return spr_master;
		}
		
		if(name.equals("Description")) {
			spr_master = atlas_InterfaceCreate.createSprite("selected");
			spr_master.setPosition(coordsX - 28.8f, coordsY - 10);
			spr_master.setSize(4, 7);
			return spr_master;
		}
		
		if(name.equals("Hotkey")) {
			spr_master = atlas_InterfaceCreate.createSprite("selected");
			spr_master.setPosition(coordsX - 10.8f, coordsY - 10);
			spr_master.setSize(4, 7);
			return spr_master;
		}
		
		if(name.equals("Menu1")) {
			spr_master = atlas_InterfaceCreate.createSprite("selected");
			spr_master.setPosition(coordsX - 44.5f, coordsY + 2.3f);
			spr_master.setSize(1, 1);
			return spr_master;
		}
		
		if(name.equals("Menu2")) {
			spr_master = atlas_InterfaceCreate.createSprite("selected");
			spr_master.setPosition(coordsX - 39.2f, coordsY + 2.3f);
			spr_master.setSize(1, 1);
			return spr_master;
		}
		
		if(name.equals("Menu3")) {
			spr_master = atlas_InterfaceCreate.createSprite("selected");
			spr_master.setPosition(coordsX - 34f, coordsY + 2.3f);
			spr_master.setSize(1, 1);
			return spr_master;
		}
		
		if(name.equals("Menu4")) {
			spr_master = atlas_InterfaceCreate.createSprite("selected");
			spr_master.setPosition(coordsX - 29f, coordsY + 2.3f);
			spr_master.setSize(1, 1);
			return spr_master;
		}
		
		return spr_master;
	}
	
	public void UseItem(int numItem) {
		//Structure: [HPCAN#3]-[HPCAN#3]
		String[] lstItem = playerInfo.itens_A.split("-");
		String[] itemSplit;
		String item = "";
		String itemName = "";
		String lstitensFinal;
		int playerHP = 0;
		int playerHPMax = 0;
		int playerMP = 0;
		int playerMPMax = 0;
		int playerStrenght = 0;
		int qtd;
		
		item = lstItem[numItem];
		if(!item.equals("[NONE]")) {
			itemSplit = item.split("#");
			itemName = itemSplit[0].replace("[", "");
			qtd = Integer.parseInt(itemSplit[1].replace("]", ""));
			
			
			if(itemName.equals("HPCAN")) {
				playerHP = Integer.parseInt(playerInfo.hp_A);
				playerHPMax = Integer.parseInt(playerInfo.maxhp_A);
				
				playerHP = playerHP + 30;
				
				if(playerHP > playerHPMax) { playerHP = playerHPMax; }
				playerInfo.hp_A = String.valueOf(playerHP);
			}
			
			if(itemName.equals("MPCAM")) {
				playerMP = Integer.parseInt(playerInfo.mp_A);
				playerMPMax = Integer.parseInt(playerInfo.maxmp_A);
				
				playerMP = playerMP + 25;
				
				if(playerMP > playerMPMax) { playerMP = playerMPMax; }
				playerInfo.mp_A = String.valueOf(playerMP);
			}
			
			qtd = qtd - 1;
			
			if(qtd == 0) {
				itemName = "[NONE]";
				lstItem[numItem] = itemName;
				lstitensFinal = Arrays.toString(lstItem).replace(", ","-");
				lstitensFinal = lstitensFinal.substring(1, lstitensFinal.length() -1);
				playerInfo.itens_A = lstitensFinal;
			}
			else {
				itemName = "[" + itemName + "#" + qtd + "]"; 
				lstItem[numItem] = itemName;
				lstitensFinal = Arrays.toString(lstItem).replace(", ","-");
				lstitensFinal = lstitensFinal.substring(1, lstitensFinal.length() -1);
				playerInfo.itens_A = lstitensFinal;
			}	
		}
	}
	
	public void DiscartItem(int itemNum) {
		String[] lstItem = playerInfo.itens_A.split("-");
		String listaItemFinal = "";
		int money = 0;
		
		lstItem[itemNum] = "[NONE]";
		listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
		listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
		playerInfo.itens_A = listaItemFinal;
		
		money = Integer.parseInt(playerInfo.money_A);
		money = money + 2;
		playerInfo.money_A = String.valueOf(money);
	}
	
	public String Decription(int numItem) {
		
		
		return "";
	}
	
	public void HotKeyItem(int itemNum) {
		String[] lstItem = playerInfo.itens_A.split("-");
		String[] itemSplit = lstItem[itemNum].split("#");
		String item = "";
					
		item = itemSplit[0].replace("[", "");
		
		if(item.equals("HPCAN")) { 
			if(playerInfo.hotkey1_A.equals("none")) { playerInfo.hotkey1_A = "HPCAN"; return; }
			if(playerInfo.hotkey2_A.equals("none")) { playerInfo.hotkey2_A = "HPCAN"; return; }
		}
		
		if(item.equals("MPCAN")) { 
			if(playerInfo.hotkey1_A.equals("none")) { playerInfo.hotkey1_A = "MPCAN"; return; }
			if(playerInfo.hotkey2_A.equals("none")) { playerInfo.hotkey2_A = "MPCAN"; return; }
		}
		
		if(item.equals("STCAN")) { 
			if(playerInfo.hotkey1_A.equals("none")) { playerInfo.hotkey1_A = "STCAN"; return; }
			if(playerInfo.hotkey2_A.equals("none")) { playerInfo.hotkey2_A = "STCAN"; return; }
		}
		
		if(item.equals("CHIPZ")) { 
			if(playerInfo.hotkey1_A.equals("none")) { playerInfo.hotkey1_A = "CHIPZ"; return; }
			if(playerInfo.hotkey2_A.equals("none")) { playerInfo.hotkey2_A = "CHIPZ"; return; }
		}
	}
	
	public void AddItemBag(String itemName) {
		
		//Structure: [HPCAN#3]-[HPCAN#3]
		String[] lstItem = playerInfo.itens_A.split("-");
		String[] itemSplit;
		boolean exist = false;
		int qtd = 0;
		int posicaoItem = 0;
		String listaItemFinal;
		
		for(int i = 0; i < lstItem.length; i++) {
			if(lstItem[i].contains(itemName) && !exist) {
				posicaoItem = i;
				exist = true;
			}
		}
		
		if(exist) {
			itemSplit = lstItem[posicaoItem].split("#");
			qtd = Integer.parseInt(itemSplit[1].replace("]", ""));
			qtd++;
			if(qtd >= 99) { return;}
				lstItem[posicaoItem] = "[" + itemSplit[0].replace("[", "") + "#" + String.valueOf(qtd) + "]";
				listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
				listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
				playerInfo.itens_A = listaItemFinal;
			}
			else {
				for(int i = 0; i < lstItem.length; i++) {
					if(lstItem[i].contains("None") && !exist) {
						posicaoItem = i;
						exist = true;
					}
				}
			
			if(exist) {
				lstItem[posicaoItem] = "[" + itemName + "#" + "1" + "]";
				listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
				listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
				playerInfo.itens_A = listaItemFinal;
			}
		}
	}
	
	//[F] Online
	public void OnlineManager(String operation, String subData) {
		try {
			if(operation.equals("Upload")) {
				OnlineOperation("Upload", subData);
			}
			if(operation.equals("Download")) {
				OnlineOperation("Download", subData);
			}
			if(operation.equals("Chat")) {
				OnlineOperation("Chat", subData);
			}
			if(operation.equals("Sync")) {
				onlineCheck = true;
				threahCount = 1;
				ThreadsSync();				
			}
			if(operation.equals("Desligar")) {
				onlineCheck = false;
				threahCount = 0;
			}
			if(operation.equals("Atk")) {
				OnlineOperation("Atk", subData);
			}		
			if(operation.equals("Party")) {
				OnlineOperation("Party", subData);
			}
			if(operation.equals("ExpSharedUpdate")) {
				OnlineOperation("ExpSharedUpdate",subData);
			}
			
		}
		
		catch(Exception ex) {
			
		}
	}
	
	private void ThreadsSync() {
		new Thread(t1).start();			
	}
	
	private Runnable t1 = new Runnable() {
		public void run() {
			try{    
				while(threahCount == 1) {
					OnlineOperation("Sync", "");            	
				}
	}
	catch(Exception ex) {}
			//} catch (InterruptedException e){}	
		}
	};	
	
	public ArrayList<Player> GetOnlinePlayers() {
		return lstPlayersOnline;
	}
	
	public ArrayList<String> GetOnlineChats(){
		return lstChats;
	}
	
	public Sprite MovPlayerOnline(Player playerOnline) {
	
		String state = playerOnline.side_A;
		String set = playerOnline.set_A;
		String walk = playerOnline.walk_A;
		int frame = Integer.parseInt(playerOnline.pos_A);
		String sex = playerOnline.sex_A;
		float coordsX = Float.parseFloat(playerOnline.coordX_A);
		float coordsY = Float.parseFloat(playerOnline.coordY_A);
		
		//Male
		if(sex.equals("M")) {
			if(set.equals("basicset_m")) {
				
				//Stop
				if(walk.equals("stop")) {
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 1) { spr_master = atlas_basicset_m.createSprite("front1"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 1) { spr_master = atlas_basicset_m.createSprite("back1"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 1) { spr_master = atlas_basicset_m.createSprite("left1"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 1) { spr_master = atlas_basicset_m.createSprite("right1"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
				}
				
				//Walk Front
				if(walk.equals("walk")) {
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 1) { spr_master = atlas_basicset_m.createSprite("front1"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 2) { spr_master = atlas_basicset_m.createSprite("front2"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 3) { spr_master = atlas_basicset_m.createSprite("front3"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 1) { spr_master = atlas_basicset_m.createSprite("back1"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 2) { spr_master = atlas_basicset_m.createSprite("back2"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 3) { spr_master = atlas_basicset_m.createSprite("back3"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 1) { spr_master = atlas_basicset_m.createSprite("left1"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 2) { spr_master = atlas_basicset_m.createSprite("left2"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 3) { spr_master = atlas_basicset_m.createSprite("left3"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 1) { spr_master = atlas_basicset_m.createSprite("right1"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 2) { spr_master = atlas_basicset_m.createSprite("right2"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 3) { spr_master = atlas_basicset_m.createSprite("right3"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
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
	
	public Sprite MovPlayerOnlineHair(Player playerOnline) {
		
		String sex = playerOnline.sex_A;
		String state = playerOnline.side_A;
		int frame = Integer.parseInt(playerOnline.pos_A);
		String hair = playerOnline.hair_A;
		float coordsX = Float.parseFloat(playerOnline.coordX_A);
		float coordsY = Float.parseFloat(playerOnline.coordY_A);
		
		if(sex.equals("M")) {		
			if((state.equals("front") || state.equals("front-left") || state.equals("front-right"))  && frame == 1) { spr_master = atlas_hairs.createSprite(hair); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7, coordsY + 23f); return spr_master; }
			if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 1) { spr_master = atlas_hairs.createSprite(hair + "up"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7.3f, coordsY + 22.7f); return spr_master; }
			if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 1) { spr_master = atlas_hairs.createSprite(hair + "left"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7.5f, coordsY + 23); return spr_master; }
			if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 1) { spr_master = atlas_hairs.createSprite(hair + "right"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7.8f, coordsY + 23); return spr_master; }
			
			if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7.2f, coordsY + 22.7f); return spr_master; }
			if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair + "up"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7.3f, coordsY + 22.7f); return spr_master; }
			if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair + "left"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 6.8f, coordsY + 22.3f); return spr_master; }
			if((state.equals("right")  || state.equals("right-front") || state.equals("right-back")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair + "right"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 6.5f, coordsY + 22.3f); return spr_master; }
			
			if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7.2f, coordsY + 22.4f); return spr_master; }
			if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair + "up"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7.3f, coordsY + 22.7f); return spr_master; }
			if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair + "left"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7.2f, coordsY + 22.8f); return spr_master; }
			if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair + "right"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7.4f, coordsY + 22.8f); return spr_master; }
		}
		
		if(sex.equals("F")) {			
			if(state.equals("front")) { spr_master = atlas_hairs.createSprite(hair); }
			if(state.equals("back")) { spr_master = atlas_hairs.createSprite(hair + "up"); }
			if(state.equals("left")) { spr_master = atlas_hairs.createSprite(hair + "left"); }
			if(state.equals("right")) { spr_master = atlas_hairs.createSprite(hair + "right"); }		
		}
		
		return spr_master;
	}
	
	public void OnlineOperation(String request, String subdata) throws UnsupportedEncodingException {
		
		try {
			if(request.equals("Sync")) {
			String retornoOnline = "retry";
			String returnFromServer = "";
			
			coordsXint = 0;
			coordsYint = 0;
			coordsXfloat = 0;
			coordsYfloat = 0;
			
			coordsXfloat = Float.parseFloat(playerInfo.coordX_A);
			coordsYfloat = Float.parseFloat(playerInfo.coordY_A);
			
			coordsXint = Math.round(coordsXfloat);
			coordsYint = Math.round(coordsYfloat);
	        
			String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(playerInfo.accountID, "UTF-8");
	        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Sync", "UTF-8");
	        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode("citybase.mysql.uhserver.com", "UTF-8");
	        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode("citymaster", "UTF-8");
	        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode("City@2020", "UTF-8");
	        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode("citybase", "UTF-8");
	        data += "&" + URLEncoder.encode("lversion", "UTF-8") + "=" + URLEncoder.encode("a1", "UTF-8");
	        data += "&" + URLEncoder.encode("lname", "UTF-8") + "=" + URLEncoder.encode(playerInfo.name_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lsex", "UTF-8") + "=" + URLEncoder.encode(playerInfo.sex_A, "UTF-8");
	        data += "&" + URLEncoder.encode("llevel", "UTF-8") + "=" + URLEncoder.encode(playerInfo.level_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lhp", "UTF-8") + "=" + URLEncoder.encode(playerInfo.hp_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lmp", "UTF-8") + "=" + URLEncoder.encode(playerInfo.mp_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lcoordsX", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(coordsXint), "UTF-8");
	        data += "&" + URLEncoder.encode("lcoordsY", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(coordsYint), "UTF-8");
	        data += "&" + URLEncoder.encode("lmap", "UTF-8") + "=" + URLEncoder.encode(playerInfo.map_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lhair", "UTF-8") + "=" + URLEncoder.encode(playerInfo.hair_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lhat", "UTF-8") + "=" + URLEncoder.encode(playerInfo.hat_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lsetEquip", "UTF-8") + "=" + URLEncoder.encode(playerInfo.set_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lparty", "UTF-8") + "=" + URLEncoder.encode(playerInfo.party_A, "UTF-8");
	        data += "&" + URLEncoder.encode("ljob", "UTF-8") + "=" + URLEncoder.encode(playerInfo.job_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lwalk", "UTF-8") + "=" + URLEncoder.encode(playerInfo.walk_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lpos", "UTF-8") + "=" + URLEncoder.encode(playerInfo.pos_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lweapon", "UTF-8") + "=" + URLEncoder.encode(playerInfo.weapon_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lside", "UTF-8") + "=" + URLEncoder.encode(playerInfo.side_A, "UTF-8");
	        data += "&" + URLEncoder.encode("laccount", "UTF-8") + "=" + URLEncoder.encode(playerInfo.accountID, "UTF-8");
	        data += "&" + URLEncoder.encode("lbattle", "UTF-8") + "=" + URLEncoder.encode(playerInfo.inBattle_A, "UTF-8");
	        
	        // Send data
	        URL url = new URL("http://moonbolt.online/Conector/Online.php");
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data);
	        wr.flush();
	        
	        // Get the response
	        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line;
	        line = "";
	        returnFromServer = "";
	        while ((line = rd.readLine()) != null) {
	        	returnFromServer = line;   
	        	//Resultado:
	        	if(returnFromServer.contains("SYSTEMCHAT")) {
	        		ChatManagerOnline(returnFromServer); 
	        	}
	        	
		        if (returnFromServer.contains("SYSTEMPLAYERS")) {            	
		        	PlayersManagerOnline(returnFromServer);     		
	            }	
		        
		        if(returnFromServer.contains("SYSTEMMOBS")) {
		        	MobsManagerOnline(returnFromServer);
		        }
		        
		        if(returnFromServer.contains("SYSTEMPARTYEXP")) {
		        	ExpSharedManagerOnline(returnFromServer);
		        }
			}
	        
	        wr.close();
	        rd.close();
	        
			}
			
			
			if(request.equals("Chat")) {
				
				String retornoOnline = "retry";
				String returnFromServer = "";
				
				String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(playerInfo.accountID, "UTF-8");
		        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Chat", "UTF-8");
		        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode("citybase.mysql.uhserver.com", "UTF-8");
		        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode("citymaster", "UTF-8");
		        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode("City@2020", "UTF-8");
		        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode("citybase", "UTF-8");
		        data += "&" + URLEncoder.encode("lversion", "UTF-8") + "=" + URLEncoder.encode("a1", "UTF-8");
		        data += "&" + URLEncoder.encode("lname", "UTF-8") + "=" + URLEncoder.encode(playerInfo.name_A, "UTF-8");
		        data += "&" + URLEncoder.encode("lchat", "UTF-8") + "=" + URLEncoder.encode(subdata, "UTF-8");
		        
		        // Send data
		        URL url = new URL("http://moonbolt.online/Conector/Online.php");
		        URLConnection conn = url.openConnection();
		        conn.setDoOutput(true);
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		        wr.write(data);
		        wr.flush();
		        
		        // Get the response
		        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        String line;
		        line = "";
		        returnFromServer = "";
		        while ((line = rd.readLine()) != null) {
		        	returnFromServer = line;   
		        	//Resultado:
		        	if(returnFromServer.contains("SYSTEMCHAT")) {
		        		ChatManagerOnline(returnFromServer); 
		        	}
		        	
			        if (returnFromServer.contains("SYSTEMPLAYERS")) {            	
			        	PlayersManagerOnline(returnFromServer);     		
		            }	
			        
			        if(returnFromServer.contains("SYSTEMMOBS")) {
			        	MobsManagerOnline(returnFromServer);
			        }
			        
			        if(returnFromServer.contains("SYSTEMPARTYEXP")) {
			        	ExpSharedManagerOnline(returnFromServer);
			        }
				}
		        
		        wr.close();
		        rd.close();
			}
			
		}
		catch(Exception ex) {
			
		}
	}
	
	private void PlayersManagerOnline(String data) {
		//lstPlayersOnline
		
		dataSplit = data.split(":");
		
		dataInfoSplit = dataSplit[1].split("=");
		playerInfoOnline.name_A = dataInfoSplit[1];
		
		if(playerInfo.name_A.equals(playerInfoOnline.name_A)) {
			return;
		}
		
		dataInfoSplit = dataSplit[2].split("=");
		playerInfoOnline.hp_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[3].split("=");
		playerInfoOnline.mp_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[4].split("=");
		playerInfoOnline.coordX_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[5].split("=");
		playerInfoOnline.coordY_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[6].split("=");
		playerInfoOnline.map_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[7].split("=");
		playerInfoOnline.level_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[8].split("=");
		playerInfoOnline.set_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[9].split("=");
		playerInfoOnline.hair_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[10].split("=");
		playerInfoOnline.hat_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[11].split("=");
		playerInfoOnline.weapon_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[12].split("=");
		playerInfoOnline.inBattle_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[13].split("=");
		playerInfoOnline.side_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[14].split("=");
		playerInfoOnline.pos_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[15].split("=");
		playerInfoOnline.walk_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[16].split("=");
		playerInfoOnline.accountID = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[17].split("=");
		playerInfoOnline.party_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[18].split("=");
		playerInfoOnline.sex_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[19].split("=");
		playerInfoOnline.job_A = dataInfoSplit[1];
		
		if(lstPlayersOnline.contains(playerInfoOnline)){
			setIndexPlayerOnline = lstPlayersOnline.indexOf(playerInfoOnline);
			lstPlayersOnline.set(setIndexPlayerOnline, playerInfoOnline);
		}
		else {
			lstPlayersOnline.add(playerInfoOnline);
		}	
	}
	
	private void MobsManagerOnline(String data) {
		
	}
	
	private void ChatManagerOnline(String data) {
		dataSplit = data.split(":");	
		dataInfoSplit = dataSplit[1].split("=");
		onlineDataReceive = dataInfoSplit[1];
		dataInfoSplit = dataSplit[2].split("=");
		onlineDataReceive = onlineDataReceive + ":" + dataInfoSplit[1];		
		lstChats.add(onlineDataReceive);		
		
		if(lstChats.size() > 3) { lstChats.remove(0); }
		
	}
	
	private void ExpSharedManagerOnline(String data) {
		
	}
	
	
	//[G] Battle
	public ArrayList<Monster> LoadMonsters(String map) {
		
		//Sewers
		if(map.equals("Sewers")) {
			Monster mobA = new Monster();
			mobA.name = "Slime";
			mobA.mobPosX = 59;
			mobA.mobPosY = -26;
			mobA.mobID = "SlimeA";
			mobA.hp = 10;
			mobA.mp = 10;
			mobA.maxHP = 10;
			mobA.maxMP = 10;
			mobA.exp = 2;
			mobA.inCasting = false;
			mobA.dead = false;
			mobA.target = "None";
			mobA.frame = 1;
			mobA.atkCount = 0;
			mobA.atkHit = 100;
			mobA.getHit = false;
			mobA.side = "right";
			lstMobs.add(mobA);
		}
		
		return lstMobs;
	}
	
	public ArrayList<Monster> GetMonsters() {
		return lstMobs;
	}
	
	public Sprite MobAppear(int num) {
		
		Monster mob = lstMobs.get(num);
		
		//Slime
		if(mob.name.equals("Slime")) {
			if(mob.name.equals("Slime") && mob.side.equals("right")) {
				if(mob.frame >= 1 && mob.frame <= 10) { spr_master = atlas_MonstersSewer.createSprite("slime1_right"); }
				if(mob.frame >= 11 && mob.frame <= 21) { spr_master = atlas_MonstersSewer.createSprite("slime2_right"); }
				if(mob.frame >= 22 && mob.frame <= 33) { spr_master = atlas_MonstersSewer.createSprite("slime3_right"); }
				//if(mob.frame >= 15  && mob.frame <= 20) { spr_master = atlas_MonstersSewer.createSprite("slime4_right"); }
				if(mob.getHit) { spr_master = atlas_MonstersSewer.createSprite("slime1_damage_right"); mob.getHit = false; }
			}
			if(mob.name.equals("Slime") && mob.side.equals("left")) {
				if(mob.frame >= 1 && mob.frame <= 10) { spr_master = atlas_MonstersSewer.createSprite("slime1_left"); }
				if(mob.frame >= 11 && mob.frame <= 21) { spr_master = atlas_MonstersSewer.createSprite("slime2_left"); }
				if(mob.frame >= 22 && mob.frame <= 33) { spr_master = atlas_MonstersSewer.createSprite("slime3_left"); }
				//if(mob.frame >= 15 && mob.frame <= 20) { spr_master = atlas_MonstersSewer.createSprite("slime4_left"); }
				if(mob.getHit) { spr_master = atlas_MonstersSewer.createSprite("slime1_damage_left"); mob.getHit = false; }
			}
		
			//Mov X
			frameMob = randnumber.nextInt(10);
			if(frameMob >= 0 && frameMob <= 4) {
				mob.mobPosX = mob.mobPosX + 0.10f;
			}
			if(frameMob >= 5 && frameMob <= 10) {
				mob.mobPosX = mob.mobPosX - 0.10f;
			}
		
			//Mov Y
			frameMob = randnumber.nextInt(10);		
			if(frameMob >= 0 && frameMob <= 4) {
				mob.mobPosY = mob.mobPosY + 0.10f;
			}
			if(frameMob >= 5 && frameMob <= 10) {
				mob.mobPosY = mob.mobPosY - 0.10f;
			}
		
			spr_master.setPosition(mob.mobPosX, mob.mobPosY);
			spr_master.setSize(10, 14);
			
			mob.frame++;
			if(mob.frame >= 33) { mob.frame = 1; }		
			lstMobs.set(num, mob);
		}
		
		return spr_master;
	}
	
	
}