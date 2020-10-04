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
	private ArrayList<Damage> lstDanos;
	private ArrayList<Skill> lstSkill;
	
	//Primitive Variable
	private int activeCharNumber;
	private int countFrameMov = 1;
	private int frame = 1;
	private int atkHitRandom = 0;
	private float playerPosX;
	private float playerPosY;
	private float playerSpeed = 0.4f;
	private int recoverytimer = 700;
	private int savetimer = 800;
	private int frameMob = 0;
	private int randomCount;
	private int countLootShowing = 0;
	private String lootItemName = "none";
	
	//Player Variables
	private int playerHP;
	private int playerMP;
	private int playerHPMax;
	private int playerMPMax;
	private int playerCountDown = 200;
	private int coordsXint = 0;
	private int coordsYint = 0;
	private int playerAtk = 3;
	private int exitAnimationFrame = 0;
	private int skillCoolDown = 0;
	private String playerSide = "";
	private String playerWeapon = "";
	private String[] playerStatus;
	private String[] playerStatusNumber;
	private float playerCoordsX;
	private float playerCoordsY;
	private float coordsXfloat = 0;
	private float coordsYfloat = 0;
	private float playerRangeXMinus;
	private float playerRangeXPlus;
	private float playerRangeYMinus;
	private float playerRangeYPlus;
	private int playerStr;
	private int playerAgi;
	private int playerDex;
	private int playerWis;
	private int playerLuk;
	private int playerRes;
	private int playerVit;
	
	
	//Mob Variables
	private int mobCountDown;
	private int mobHP;
	private int mobMP;
	private int mobAtk;
	private float mobX;
	private float mobY;
	private float mobRangeXMinus;
	private float mobRangeXPlus;
	private float mobRangeYMinus;
	private float mobRangeYPlus;
	
	
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
	private Sprite spr_targetArrow;
	private Texture tex_teste;
	
	
	//Atlas Section
	private TextureAtlas atlas_hairs;
	private TextureAtlas atlas_basicset_m;
	private TextureAtlas atlas_basicset_f;
	private TextureAtlas atlas_InterfaceCreate;
	private TextureAtlas atlas_shops;
	private TextureAtlas atlas_itens;
	private TextureAtlas atlas_loots;
	
	private TextureAtlas atlas_iconSkills;
	private TextureAtlas atlas_tripleattack;
	
	private TextureAtlas atlas_flysword;
	private TextureAtlas atlas_ravenblade;
	private TextureAtlas atlas_healthboost;
	private TextureAtlas atlas_ironshield;
	private TextureAtlas atlas_protect;
	
	private TextureAtlas atlas_fireball;
	private TextureAtlas atlas_icecrystal;
	private TextureAtlas atlas_thundercloud;
	private TextureAtlas atlas_rockbound;
	private TextureAtlas atlas_soulcrash;
	
	private TextureAtlas atlas_doublehit;
	private TextureAtlas atlas_poisonhit;
	private TextureAtlas atlas_steal;
	private TextureAtlas atlas_dashkick;
	private TextureAtlas atlas_invisibility;
	
	private TextureAtlas atlas_heal;
	private TextureAtlas atlas_atkboost;
	private TextureAtlas atlas_defboost;
	private TextureAtlas atlas_holyprism;
	private TextureAtlas atlas_regen;
	
	private TextureAtlas atlas_hammercrash;
	private TextureAtlas atlas_berserk;
	private TextureAtlas atlas_overpower;
	private TextureAtlas atlas_ragebound;
	private TextureAtlas atlas_impound;
	
	private TextureAtlas atlas_fastshot;
	private TextureAtlas atlas_bulletrain;
	private TextureAtlas atlas_precision;
	private TextureAtlas atlas_lockshot;
	private TextureAtlas atlas_mine;
	
	private TextureAtlas atlas_basicknife;
	
	private TextureAtlas atlas_MonstersSewer;
	
	
	// CONSTRUCTOR
	public GameControl() {
		this.playerInfo = new Player();
		
		this.tex_teste = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		this.spr_master = new Sprite(tex_teste);
		
		randnumber = new Random();
				
		//Data Manager
		json = new Json();
		playerInfo = new Player();
		playerInfoOnline = new Player();
		lstPlayersOnline = new ArrayList<Player>();
		lstChats = new ArrayList<String>();
		lstMobs = new ArrayList<Monster>();
		lstDanos = new ArrayList<Damage>();
		lstSkill = new ArrayList<Skill>();
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
		
		//Loots
		atlas_loots = new TextureAtlas(Gdx.files.internal("data/itens/loots.txt"));
		
		//Weapons Sets
		atlas_basicknife = new TextureAtlas(Gdx.files.internal("data/weapons/nknifes.txt"));
		
		//Skill Icons
		atlas_iconSkills = new TextureAtlas(Gdx.files.internal("data/skills/skillicons.txt"));  
		
		//Skill Effects
		atlas_tripleattack = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/tripleattack.txt")); //tripleattack
		
		atlas_flysword = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/flysword.txt"));
		atlas_ravenblade = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/ravenblade.txt"));
		atlas_healthboost = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/healthboost.txt"));
		atlas_ironshield = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/ironshield.txt"));
		atlas_protect = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/protect.txt"));
		
		atlas_fireball = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/fireball.txt"));
		atlas_icecrystal = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/icecrystal.txt"));
		atlas_thundercloud = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/thundercloud.txt"));
		atlas_rockbound = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/rockbound.txt"));
		atlas_soulcrash = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/soulclash.txt"));
		
		atlas_doublehit = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/doublehit.txt"));
		atlas_poisonhit = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/poisonhit.txt"));
		atlas_steal = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/steal.txt"));
		atlas_dashkick = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/dashkick.txt"));
		atlas_invisibility = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/invisibility.txt"));
		
		atlas_heal = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/heal.txt"));
		atlas_atkboost = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/atkboost.txt"));
		atlas_defboost = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/defboost.txt"));
		atlas_holyprism = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/holyprism.txt"));
		atlas_regen = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/regen.txt"));
		
		atlas_hammercrash = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/hammercrash.txt"));
		atlas_berserk = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/berserk.txt"));
		atlas_overpower = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/overpower.txt"));
		atlas_ragebound = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/ragebound.txt"));
		atlas_impound = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/impound.txt"));
			
		atlas_fastshot = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/fastshot.txt"));
		atlas_bulletrain = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/bulletrain.txt"));
		atlas_precision = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/precision.txt"));
		atlas_lockshot = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/lockshot.txt"));
		atlas_mine = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/mine.txt"));
		
		
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
			playerInfo.stamina_1 = "100";
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
			playerInfo.buffs_1 = "none";
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
			playerInfo.stamina_2 = "100";
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
			playerInfo.buffs_2 = "none";
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
			playerInfo.stamina_3 = "100";
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
			playerInfo.buffs_3 = "none";
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
				playerInfo.stamina_1 = playerInfo.stamina_A;
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
				playerInfo.buffs_1 = playerInfo.buffs_A;
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
				playerInfo.stamina_2 = playerInfo.stamina_A;
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
				playerInfo.buffs_2 = playerInfo.buffs_A;
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
				playerInfo.stamina_3 = playerInfo.stamina_A;
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
				playerInfo.buffs_3 = playerInfo.buffs_A;
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
			playerInfo.stamina_A = playerInfo.stamina_1;
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
			playerInfo.buffs_A = playerInfo.buffs_1;
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
			playerInfo.stamina_A = playerInfo.stamina_2;
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
			playerInfo.buffs_A = playerInfo.buffs_2;
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
			playerInfo.stamina_A = playerInfo.stamina_3;
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
			playerInfo.buffs_A = playerInfo.buffs_3;
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
	
	public Sprite LoadCharacterHairMenu(String hair, String sex) {
		if(sex.equals("M")) {
			spr_master = atlas_hairs.createSprite(hair);
			spr_master.setSize(8, 12);
			spr_master.setPosition(19.5f, 47f);			
			return spr_master;
		}
		if(sex.equals("F")) {
			spr_master = atlas_hairs.createSprite(hair + "_f");
			spr_master.setSize(8, 12);
			spr_master.setPosition(19.5f, 47f);			
			return spr_master;
		}	
		
		return spr_master;
	}
	
	public Sprite LoadPlayerTagHair(String hair, String sex) {
		if(sex.equals("M")) {
			spr_master = atlas_hairs.createSprite(hair);
			spr_master.setSize(7, 11);
			spr_master.setPosition(-0.1f, 87);
			return spr_master;
		}
		
		if(sex.equals("F")) {
			spr_master = atlas_hairs.createSprite(hair + "_f");
			spr_master.setSize(7, 11);
			spr_master.setPosition(-0.1f, 87);
			return spr_master;
		}
		
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
			if(extra.equals("M")) {
				spr_master = atlas_hairs.createSprite(value);
				spr_master.setSize(9, 13);
				spr_master.setPosition(cameraCoordsX - 66,cameraCoordsY + 78);
				return spr_master;
			}
			if(extra.equals("F")) {
				spr_master = atlas_hairs.createSprite(value + "_f");
				spr_master.setSize(9, 13);
				spr_master.setPosition(cameraCoordsX - 66,cameraCoordsY + 78);
				return spr_master;
			}
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
		
		//Auto Atk 
		if(type.equals("autoatkON")) {
			spr_master = atlas_InterfaceCreate.createSprite("btnAutoAttackON");
			spr_master.setSize(10,15);
			spr_master.setPosition(cameraCoordsX + 58,cameraCoordsY - 7);
			return spr_master;
		}
		if(type.equals("autoatkOFF")) {
			spr_master = atlas_InterfaceCreate.createSprite("btnAutoAttackOFF");
			spr_master.setSize(10,15);
			spr_master.setPosition(cameraCoordsX + 58,cameraCoordsY - 7);
			return spr_master;
		}
		
		//Switch Target 
		if(type.equals("switchTarget")) {
			spr_master = atlas_InterfaceCreate.createSprite("btnTrocarAlvo");
			spr_master.setSize(10,15);
			spr_master.setPosition(cameraCoordsX + 58,cameraCoordsY + 10);
			return spr_master;
		}
		
		//Loot Bar
		if(type.equals("lootbar")) {
			spr_master = atlas_InterfaceCreate.createSprite("barloot");
			spr_master.setSize(50,15);
			spr_master.setPosition(cameraCoordsX - 25,cameraCoordsY + 60);
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
		
		playerInfo.job_A = "Swordman";
		
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
			
			if(playerCountDown <= 0) {
				exitAnimationFrame++;
			}
			
			if(exitAnimationFrame >= 1 && exitAnimationFrame < 20) {
				exitAnimationFrame++;
			}
			
			if(exitAnimationFrame >= 20) {
				frame = 1;
				exitAnimationFrame = 0;
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
				
				//Menu
				if(menu) {
					if(state.equals("front")) { spr_master = atlas_basicset_m.createSprite("front1"); return spr_master; }
				}
				
				//in Battle
				if(walk.equals("stop") && playerInfo.inBattle_A.equals("yes")) {
					countFrameMov++;
					if(playerSide.equals("right") && frame == 1 && exitAnimationFrame == 0) { spr_master = atlas_basicset_m.createSprite("battle1right"); return spr_master; }
					if(playerSide.equals("left") && frame == 1 && exitAnimationFrame == 0) { spr_master = atlas_basicset_m.createSprite("battle1left"); return spr_master; }
					
					if(playerSide.equals("right") && frame == 2 && exitAnimationFrame == 0) { spr_master = atlas_basicset_m.createSprite("battle2right"); return spr_master; }
					if(playerSide.equals("left") && frame == 2 && exitAnimationFrame == 0) { spr_master = atlas_basicset_m.createSprite("battle2left"); return spr_master; }
					
					if(playerSide.equals("right") && frame == 3 && exitAnimationFrame == 0) { spr_master = atlas_basicset_m.createSprite("battle3right"); return spr_master; }
					if(playerSide.equals("left") && frame == 3 && exitAnimationFrame == 0) { spr_master = atlas_basicset_m.createSprite("battle3left"); return spr_master; }
					
					if(playerInfo.job_A.equals("Mage") || playerInfo.job_A.equals("Medic")) {
						if(playerSide.equals("right") && frame >= 50) { spr_master = atlas_basicset_m.createSprite("attackmagicright"); return spr_master; }
						if(playerSide.equals("left") && frame >= 50) { spr_master = atlas_basicset_m.createSprite("attackmagicleft"); return spr_master; }
					}
					else {
						if(playerSide.equals("right") && exitAnimationFrame > 0) { spr_master = atlas_basicset_m.createSprite("attackhitright"); return spr_master; }
						if(playerSide.equals("left") && exitAnimationFrame > 0) { spr_master = atlas_basicset_m.createSprite("attackhitleft"); return spr_master; }
					}				
				}
				
				
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
					
					//Menu
					if(menu) {
						if(state.equals("front")) { spr_master = atlas_basicset_f.createSprite("front1"); return spr_master; }
					}
					
					//in Battle
					if(walk.equals("stop") && playerInfo.inBattle_A.equals("yes")) {
						countFrameMov++;
						if(playerSide.equals("right") && frame == 1 && exitAnimationFrame == 0) { spr_master = atlas_basicset_f.createSprite("battle1right"); return spr_master; }
						if(playerSide.equals("left") && frame == 1 && exitAnimationFrame == 0) { spr_master = atlas_basicset_f.createSprite("battle1left"); return spr_master; }
						
						if(playerSide.equals("right") && frame == 2 && exitAnimationFrame == 0) { spr_master = atlas_basicset_f.createSprite("battle2right"); return spr_master; }
						if(playerSide.equals("left") && frame == 2 && exitAnimationFrame == 0) { spr_master = atlas_basicset_f.createSprite("battle2left"); return spr_master; }
						
						if(playerSide.equals("right") && frame == 3 && exitAnimationFrame == 0) { spr_master = atlas_basicset_f.createSprite("battle3right"); return spr_master; }
						if(playerSide.equals("left") && frame == 3 && exitAnimationFrame == 0) { spr_master = atlas_basicset_f.createSprite("battle3left"); return spr_master; }
						
						if(playerInfo.job_A.equals("Mage") || playerInfo.job_A.equals("Medic")) {
							if(playerSide.equals("right") && frame >= 50) { spr_master = atlas_basicset_f.createSprite("attackmagicright"); return spr_master; }
							if(playerSide.equals("left") && frame >= 50) { spr_master = atlas_basicset_f.createSprite("attackmagicleft"); return spr_master; }
						}
						else {
							if(playerSide.equals("right") && exitAnimationFrame > 0) { spr_master = atlas_basicset_f.createSprite("attackhitright"); return spr_master; }
							if(playerSide.equals("left") && exitAnimationFrame > 0) { spr_master = atlas_basicset_f.createSprite("attackhitleft"); return spr_master; }
						}				
					}
					
					//Stop
					if(walk.equals("stop")) {
						frame = 1;
						if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 1) { spr_master = atlas_basicset_f.createSprite("front1"); return spr_master; }
						if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 1) { spr_master = atlas_basicset_f.createSprite("back1"); return spr_master; }
						if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 1) { spr_master = atlas_basicset_f.createSprite("left1"); return spr_master; }
						if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 1) { spr_master = atlas_basicset_f.createSprite("right1"); return spr_master; }
					}
					
					//Walk Front
					if(walk.equals("walk")) {
						if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 1) { spr_master = atlas_basicset_f.createSprite("front1"); return spr_master; }
						if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 2) { spr_master = atlas_basicset_f.createSprite("front2"); return spr_master; }
						if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 3) { spr_master = atlas_basicset_f.createSprite("front3"); return spr_master; }
						
						if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 1) { spr_master = atlas_basicset_f.createSprite("back1"); return spr_master; }
						if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 2) { spr_master = atlas_basicset_f.createSprite("back2"); return spr_master; }
						if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 3) { spr_master = atlas_basicset_f.createSprite("back3"); return spr_master; }
						
						if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 1) { spr_master = atlas_basicset_f.createSprite("left1"); return spr_master; }
						if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 2) { spr_master = atlas_basicset_f.createSprite("left2"); return spr_master; }
						if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 3) { spr_master = atlas_basicset_f.createSprite("left3"); return spr_master; }
						
						if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 1) { spr_master = atlas_basicset_f.createSprite("right1"); return spr_master; }
						if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 2) { spr_master = atlas_basicset_f.createSprite("right2"); return spr_master; }
						if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 3) { spr_master = atlas_basicset_f.createSprite("right3"); return spr_master; }
					}				
			}
		}
		
		
		return spr_master;	
		
	}
	
	public Sprite MovPlayerHair(String hair,String sex, String state, String gameState, String walk) {
		
		if(gameState.equals("Menu-Status")) {
			if(sex.equals("M")) { spr_master = atlas_hairs.createSprite(hair); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7, playerPosY + 23f); return spr_master; }		
			if(sex.equals("F")) { spr_master = atlas_hairs.createSprite(hair + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7, playerPosY + 23f); return spr_master; }
		}
			
		if(sex.equals("M")) {	
			
			if(exitAnimationFrame > 0 ) {
				if(playerSide.equals("right") && playerInfo.inBattle_A.equals("yes") && exitAnimationFrame > 0) {
					spr_master = atlas_hairs.createSprite(hair + "attack_right"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7, playerPosY + 23f); return spr_master;		
				}
				
				if(playerSide.equals("left") && playerInfo.inBattle_A.equals("yes") && exitAnimationFrame > 0) {			
					spr_master = atlas_hairs.createSprite(hair + "attack_left"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7, playerPosY + 23f); return spr_master;
				}
			}
			
			if(playerSide.equals("right") && playerInfo.inBattle_A.equals("yes") && walk.equals("stop")) {
				spr_master = atlas_hairs.createSprite(hair + "battle"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7, playerPosY + 23.5f); return spr_master;		
			}
			
			if(playerSide.equals("left") && playerInfo.inBattle_A.equals("yes") && walk.equals("stop")) {			
				spr_master = atlas_hairs.createSprite(hair + "battle_left"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.5f, playerPosY + 23.5f); return spr_master;
			}
			
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
			if((state.equals("front") || state.equals("front-left") || state.equals("front-right"))  && frame == 1) { spr_master = atlas_hairs.createSprite(hair + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7, playerPosY + 23f); return spr_master; }
			if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 1) { spr_master = atlas_hairs.createSprite(hair + "up" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.3f, playerPosY + 22.7f); return spr_master; }
			if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 1) { spr_master = atlas_hairs.createSprite(hair + "left" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.5f, playerPosY + 23); return spr_master; }
			if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 1) { spr_master = atlas_hairs.createSprite(hair + "right" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.8f, playerPosY + 23); return spr_master; }
			
			if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.2f, playerPosY + 22.7f); return spr_master; }
			if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair + "up" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.3f, playerPosY + 22.7f); return spr_master; }
			if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair + "left" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 6.8f, playerPosY + 22.3f); return spr_master; }
			if((state.equals("right")  || state.equals("right-front") || state.equals("right-back")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair + "right" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 6.5f, playerPosY + 22.3f); return spr_master; }
			
			if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.2f, playerPosY + 22.4f); return spr_master; }
			if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair + "up" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.3f, playerPosY + 22.7f); return spr_master; }
			if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair + "left" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.2f, playerPosY + 22.8f); return spr_master; }
			if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair + "right" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.4f, playerPosY + 22.8f); return spr_master; }		
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
				spr_master = atlas_hairs.createSprite(hair + "_f"); 
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
		}
		
		if(job.equals("Swordman")) {
			
			if(skill.equals("flysword")) {
				spr_master = atlas_iconSkills.createSprite("btnflysword");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 31,cameraCoordsY - 36);
				return spr_master;
			}	
			
			if(skill.equals("ravenblade")) {
				spr_master = atlas_iconSkills.createSprite("btnravenblade");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 38.2f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("healthboost")) {
				spr_master = atlas_iconSkills.createSprite("btnhealthboost");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 45.4f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("ironshield")) {
				spr_master = atlas_iconSkills.createSprite("btnironshield");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 52.8f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("protect")) {
				spr_master = atlas_iconSkills.createSprite("btnprotect");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 60,cameraCoordsY - 36);
				return spr_master;
			}
		}
		
		if(job.equals("Mage")) {
			
			if(skill.equals("fireball")) {
				spr_master = atlas_iconSkills.createSprite("btnfireball");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 31,cameraCoordsY - 36);
				return spr_master;
			}	
			
			if(skill.equals("icecrystal")) {
				spr_master = atlas_iconSkills.createSprite("btnicecrystal");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 38.2f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("thundercloud")) {
				spr_master = atlas_iconSkills.createSprite("btnthundercloud");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 45.4f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("rockbound")) {
				spr_master = atlas_iconSkills.createSprite("btnrockbound");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 52.8f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("soulcrash")) {
				spr_master = atlas_iconSkills.createSprite("btnsoulclash");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 60,cameraCoordsY - 36);
				return spr_master;
			}
		}
		
		if(job.equals("Thief")) {
			
			if(skill.equals("doublehit")) {
				spr_master = atlas_iconSkills.createSprite("btndoublehit");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 31,cameraCoordsY - 36);
				return spr_master;
			}	
			
			if(skill.equals("dashkick")) {
				spr_master = atlas_iconSkills.createSprite("btndashkick");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 38.2f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("steal")) {
				spr_master = atlas_iconSkills.createSprite("btnsteal");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 45.4f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("poisonhit")) {
				spr_master = atlas_iconSkills.createSprite("btnpoisonhit");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 52.8f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("invisibility")) {
				spr_master = atlas_iconSkills.createSprite("btninvisibility");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 60,cameraCoordsY - 36);
				return spr_master;
			}
		}
		
		if(job.equals("Medic")) {
			
			if(skill.equals("heal")) {
				spr_master = atlas_iconSkills.createSprite("btnheal");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 31,cameraCoordsY - 36);
				return spr_master;
			}	
			
			if(skill.equals("atkboost")) {
				spr_master = atlas_iconSkills.createSprite("btnatkboost");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 38.2f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("defboost")) {
				spr_master = atlas_iconSkills.createSprite("btndefboost");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 45.4f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("holyprism")) {
				spr_master = atlas_iconSkills.createSprite("btnholyprism");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 52.8f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("regen")) {
				spr_master = atlas_iconSkills.createSprite("btnregen");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 60,cameraCoordsY - 36);
				return spr_master;
			}
		}
		
		if(job.equals("Beater")) {
			
			if(skill.equals("hammercrash")) {
				spr_master = atlas_iconSkills.createSprite("btnhammercrash");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 31,cameraCoordsY - 36);
				return spr_master;
			}	
			
			if(skill.equals("berserk")) {
				spr_master = atlas_iconSkills.createSprite("btnberserk");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 38.2f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("overpower")) {
				spr_master = atlas_iconSkills.createSprite("btnoverpower");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 45.4f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("ragebound")) {
				spr_master = atlas_iconSkills.createSprite("btnragebound");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 52.8f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("impound")) {
				spr_master = atlas_iconSkills.createSprite("btnimpound");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 60,cameraCoordsY - 36);
				return spr_master;
			}
		}
		
		if(job.equals("Gunner")) {
			
			if(skill.equals("fastshot")) {
				spr_master = atlas_iconSkills.createSprite("btnfastshot");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 31,cameraCoordsY - 36);
				return spr_master;
			}	
			
			if(skill.equals("bulletrain")) {
				spr_master = atlas_iconSkills.createSprite("btnbulletrain");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 38.2f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("precision")) {
				spr_master = atlas_iconSkills.createSprite("btnprecision");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 45.4f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("lockshot")) {
				spr_master = atlas_iconSkills.createSprite("btnlockshot");
				spr_master.setSize(7,13);
				spr_master.setPosition(cameraCoordsX + 52.8f,cameraCoordsY - 36);
				return spr_master;
			}
			
			if(skill.equals("mine")) {
				spr_master = atlas_iconSkills.createSprite("btnmine");
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
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 1) { spr_master = atlas_basicset_f.createSprite("front1"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 2) { spr_master = atlas_basicset_f.createSprite("front2"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 3) { spr_master = atlas_basicset_f.createSprite("front3"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 1) { spr_master = atlas_basicset_f.createSprite("back1"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 2) { spr_master = atlas_basicset_f.createSprite("back2"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 3) { spr_master = atlas_basicset_f.createSprite("back3"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 1) { spr_master = atlas_basicset_f.createSprite("left1"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 2) { spr_master = atlas_basicset_f.createSprite("left2"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 3) { spr_master = atlas_basicset_f.createSprite("left3"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 1) { spr_master = atlas_basicset_f.createSprite("right1"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 2) { spr_master = atlas_basicset_f.createSprite("right2"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 3) { spr_master = atlas_basicset_f.createSprite("right3"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
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
				}
		        
		        wr.close();
		        rd.close();
			}
			
			if(request.equals("Atk")) {
				String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(playerInfo.accountID, "UTF-8");
		        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Atk", "UTF-8");
		        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode("citybase.mysql.uhserver.com", "UTF-8");
		        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode("citymaster", "UTF-8");
		        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode("City@2020", "UTF-8");
		        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode("citybase", "UTF-8");
		        data += "&" + URLEncoder.encode("lversion", "UTF-8") + "=" + URLEncoder.encode("a1", "UTF-8");
		        data += "&" + URLEncoder.encode("lmobID", "UTF-8") + "=" + URLEncoder.encode(playerInfo.target_A, "UTF-8");
		        data += "&" + URLEncoder.encode("lmap", "UTF-8") + "=" + URLEncoder.encode(playerInfo.map_A, "UTF-8");
		        data += "&" + URLEncoder.encode("ldmg", "UTF-8") + "=" + URLEncoder.encode(subdata, "UTF-8");
		        	        
		        // Send data
		        URL url = new URL("http://moonbolt.online/Conector/Online.php");
		        URLConnection conn = url.openConnection();
		        conn.setDoOutput(true);
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		        wr.write(data);
		        wr.flush();
		        
		        // Get the response
		        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        
		        rd.close();
		        wr.close();	        
			}
			
			if(request.equals("Download")) {
				
				String returnFromServer = "";
				
				String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(playerInfo.accountID, "UTF-8");
		        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Download", "UTF-8");
		        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode("citybase.mysql.uhserver.com", "UTF-8");
		        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode("citymaster", "UTF-8");
		        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode("City@2020", "UTF-8");
		        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode("citybase", "UTF-8");
		        data += "&" + URLEncoder.encode("lversion", "UTF-8") + "=" + URLEncoder.encode("a1", "UTF-8");
		        
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
		        	if(!returnFromServer.contains("Inexistente")) {
		        		LoadDownloadData(returnFromServer);       
		        	}
				}
		        
		        wr.close();
		        rd.close();
			}

			if(request.equals("Upload")) {
					
				String returnFromServer = "";
				String accountData = LoadDataText();
				
				String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(accountData, "UTF-8");
			    data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Upload", "UTF-8");
			    data += "&" + URLEncoder.encode("laccount", "UTF-8") + "=" + URLEncoder.encode(playerInfo.accountID, "UTF-8");
			    data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode("citybase.mysql.uhserver.com", "UTF-8");
			    data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode("citymaster", "UTF-8");
			    data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode("City@2020", "UTF-8");
			    data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode("citybase", "UTF-8");
			    data += "&" + URLEncoder.encode("lversion", "UTF-8") + "=" + URLEncoder.encode("a1", "UTF-8");
			    
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
			    	if(returnFromServer.contains("Works")) {
			    		//Retorna que funcionou
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
		
		playerInfoOnline = new Player();
		
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
		
		boolean check = false;
		for(int i = 0; i < lstPlayersOnline.size(); i++) {
			if(playerInfoOnline.accountID.equals(lstPlayersOnline.get(i).accountID)) {
				
				lstPlayersOnline.get(i).name_A = playerInfoOnline.name_A; 
				lstPlayersOnline.get(i).hp_A = playerInfoOnline.hp_A; 
				lstPlayersOnline.get(i).mp_A = playerInfoOnline.mp_A; 
				lstPlayersOnline.get(i).coordX_A = playerInfoOnline.coordX_A; 
				lstPlayersOnline.get(i).coordY_A = playerInfoOnline.coordY_A; 
				lstPlayersOnline.get(i).level_A = playerInfoOnline.level_A; 
				lstPlayersOnline.get(i).set_A = playerInfoOnline.set_A; 
				lstPlayersOnline.get(i).hair_A = playerInfoOnline.hair_A; 
				lstPlayersOnline.get(i).hat_A = playerInfoOnline.hat_A;
				lstPlayersOnline.get(i).weapon_A = playerInfoOnline.weapon_A;
				lstPlayersOnline.get(i).inBattle_A = playerInfoOnline.inBattle_A;
				lstPlayersOnline.get(i).side_A = playerInfoOnline.side_A;
				lstPlayersOnline.get(i).pos_A = playerInfoOnline.pos_A;
				lstPlayersOnline.get(i).walk_A = playerInfoOnline.walk_A;
				lstPlayersOnline.get(i).accountID = playerInfoOnline.accountID;
				lstPlayersOnline.get(i).party_A = playerInfoOnline.party_A;
				lstPlayersOnline.get(i).sex_A = playerInfoOnline.sex_A;
				lstPlayersOnline.get(i).job_A = playerInfoOnline.job_A;
				check = true;
			}
		}		
		if(!check) {
			lstPlayersOnline.add(playerInfoOnline);
		}	
	}
	
	private void MobsManagerOnline(String data) {
			
		for(int i = 0; i < lstMobs.size(); i++) {
			
			dataSplit = data.split(":");
			dataInfoSplit = dataSplit[4].split("=");
			
			if(lstMobs.get(i).map.equals(dataInfoSplit[1])) {
				
				dataSplit = data.split(":");
				dataInfoSplit = dataSplit[2].split("=");
					
				if(lstMobs.get(i).mobID.equals(dataInfoSplit[1])) {
					dataSplit = data.split(":");
					dataInfoSplit = dataSplit[3].split("=");
					lstMobs.get(i).hp = Integer.parseInt(dataInfoSplit[1]);
				}				
			}		
		}	
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
	
	public void LoadDownloadData(String hash) {
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		playerInfo = json.fromJson(Player.class,Base64Coder.decodeString(hash));			
		file.writeString(Base64Coder.encodeString(json.prettyPrint(playerInfo)),false);
	}
	
	public String LoadDataText() {
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		String dataStr = file.readString();
		return dataStr;
	}
	
	
	
	/////////////////////////////////////////////////////////ONLINE  ENDS//////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//[G] Battle
	public ArrayList<Monster> LoadMonsters(String map) {
		
		lstMobs.clear();
		
		//Sewers
		if(map.equals("Sewers")) {
			Monster mobA = new Monster();
			mobA.name = "Slime";
			mobA.mobPosX = 59;
			mobA.mobPosY = -26;
			mobA.mobID = "SlimeA";
			mobA.hp = 30;
			mobA.mp = 10;
			mobA.maxHP = 30;
			mobA.maxMP = 10;
			mobA.exp = 2;
			mobA.inCasting = false;
			mobA.dead = false;
			mobA.target = "None";
			mobA.frame = 1;
			mobA.atkCount = 0;
			mobA.atkHit = 5;
			mobA.getHit = false;
			mobA.mobCountDown = 300;
			mobA.mobCountDownMax = 300;
			mobA.side = "right";
			mobA.MobSelected = "no";
			mobA.maxRanged = 20;
			mobA.minRanged = 40;
			mobA.loot1 = "GOSMA";
			mobA.loot2 = "HPCAN";
			mobA.loot3 = "GOSMA";
			mobA.respawnTime = 300;
			mobA.respawnTimeMax = 300;
			mobA.map = "Streets305";
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
				//for atk
				if(mob.frame >= 50 && mob.frame < 70) { 
					spr_master = atlas_MonstersSewer.createSprite("slime4_right"); 
					spr_master.setPosition(mob.mobPosX, mob.mobPosY); 
					spr_master.setSize(10, 14); 
					mob.frame++; 
					return spr_master; 
				}
				if(mob.frame >= 70) { 
					mob.frame = 2; 
				}
				
				//For move
				if(mob.frame >= 1 && mob.frame <= 10) { spr_master = atlas_MonstersSewer.createSprite("slime1_right"); }
				if(mob.frame >= 11 && mob.frame <= 21) { spr_master = atlas_MonstersSewer.createSprite("slime2_right"); }
				if(mob.frame >= 22 && mob.frame <= 33) { spr_master = atlas_MonstersSewer.createSprite("slime3_right"); }
				
				//Getting hit
				if(mob.getHit) { spr_master = atlas_MonstersSewer.createSprite("slime1_damage_right"); mob.getHit = false; }
			}
			if(mob.name.equals("Slime") && mob.side.equals("left")) {
				//for atk
				if(mob.frame >= 50 && mob.frame < 70) { 
					spr_master = atlas_MonstersSewer.createSprite("slime4_left"); 
					spr_master.setPosition(mob.mobPosX, mob.mobPosY); 
					spr_master.setSize(10, 14); 
					mob.frame++; 
					return spr_master; 
				}
				if(mob.frame >= 70) { 
					mob.frame = 2; 
				}
				
				//for move
				if(mob.frame >= 1 && mob.frame <= 10) { spr_master = atlas_MonstersSewer.createSprite("slime1_left"); }
				if(mob.frame >= 11 && mob.frame <= 21) { spr_master = atlas_MonstersSewer.createSprite("slime2_left"); }
				if(mob.frame >= 22 && mob.frame <= 33) { spr_master = atlas_MonstersSewer.createSprite("slime3_left"); }
				
				//Getting hit
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
			
			if(playerInfo.target_A.equals(mob.mobID)){
				spr_targetArrow = atlas_InterfaceCreate.createSprite("target");
				spr_targetArrow.setSize(7,11);
				spr_targetArrow.setPosition(mob.mobPosX + 1.5f, mob.mobPosY + 15);
			}
			else {
				spr_targetArrow = null;
			}
		}
		
		if(mob.target.equals(playerInfo.name_A)) {
			playerCoordsX = Float.parseFloat(playerInfo.coordX_A);
			playerCoordsY = Float.parseFloat(playerInfo.coordY_A);
			
			if(mob.mobPosX > playerCoordsX) { mob.mobPosX -= 0.08f; }
			if(mob.mobPosX < playerCoordsX + 9) { mob.mobPosX += 0.08f; }
			if(mob.mobPosY > playerCoordsY) { mob.mobPosY -= 0.08f; }
			if(mob.mobPosY < playerCoordsY) { mob.mobPosY += 0.08f; }
		}
		
		return spr_master;
	}
	
	public void AutoAttack() {
		
		if(playerInfo.target_A.equals("none")) { return; }
		
		playerCoordsX = Float.parseFloat(playerInfo.coordX_A);
		playerCoordsY = Float.parseFloat(playerInfo.coordY_A);
				
		if (playerInfo.job_A.equals("Gunner")) {
			playerRangeXMinus = playerCoordsX - 70;
			playerRangeXPlus = playerCoordsX + 70;
			playerRangeYMinus = playerCoordsY - 70;
			playerRangeYPlus = playerCoordsY + 70;
		}
		else {
			playerRangeXMinus = playerCoordsX - 8;
			playerRangeXPlus = playerCoordsX + 30;
			playerRangeYMinus = playerCoordsY - 10;
			playerRangeYPlus = playerCoordsY + 40;
		}
		
		if(playerInfo.inBattle_A.equals("yes")) {
			playerCountDown--;
			skillCoolDown--;
			
			if(playerCountDown < 0) { 
				playerCountDown = 200; 
			}
			
			if(skillCoolDown <= 0) {
				skillCoolDown = 0;
			}
		}
		
		for(int i = 0; i < lstMobs.size(); i++) {
			
			mobX = lstMobs.get(i).mobPosX;
			mobY = lstMobs.get(i).mobPosY;	
			
			if(playerRangeXPlus > mobX && playerRangeXMinus < mobX && playerRangeYPlus > mobY && playerRangeYMinus < mobY) {
				
				if(playerCoordsX < mobX) { playerSide = "right"; }
				if(playerCoordsX > mobX) { playerSide = "left"; }
				
				if(playerInfo.target_A.equals(lstMobs.get(i).mobID)) {
					
					playerInfo.inBattle_A = "yes";			
					if(playerInfo.job_1.equals("Novice") && playerCountDown <= 0) {
						
						playerStatus = playerInfo.stats_A.split("#");
						playerStatusNumber = playerStatus[0].split(":");
						playerStr = Integer.parseInt(playerStatusNumber[1]);
						playerAtk = 3;
						playerAtk = playerAtk + playerStr;
						if(playerInfo.stamina_A.equals("0")) { playerAtk = 1; }
						mobHP = lstMobs.get(i).hp;
						mobHP = mobHP - playerAtk;
						lstMobs.get(i).target = playerInfo.name_A;
						lstMobs.get(i).getHit = true;
						lstMobs.get(i).hp = mobHP;
						
						if(lstMobs.get(i).hp <= 0) {  
							GiveLoot(lstMobs.get(i));							
							lstMobs.get(i).dead = true;
							playerInfo.inBattle_A = "no";
						}
						
						Damage dmg = new Damage();
						dmg.color = "Yellow";
						dmg.posX = mobX;
						dmg.posY = mobY + 10;
						dmg.user = playerInfo.name_A;
						dmg.dmg = playerAtk;
						dmg.frame = 0;					
						lstDanos.add(dmg);
						
						OnlineManager("Atk",String.valueOf(mobHP));
					}			
				}			
			}
		}	
	}
	
	public void CheckMonsterAttack() {
		
		playerCoordsX = Float.parseFloat(playerInfo.coordX_A);
		playerCoordsY = Float.parseFloat(playerInfo.coordY_A);
		playerHP = Integer.parseInt(playerInfo.hp_A);
		
		for(int i = 0; i < lstMobs.size(); i++) {
			
			mobX = lstMobs.get(i).mobPosX;
			mobY = lstMobs.get(i).mobPosY;
			
			mobRangeXMinus = mobX - 20;
			mobRangeXPlus = mobX + 20; 
			
			mobRangeYMinus = mobY - 30;
			mobRangeYPlus = mobY + 30;
			
			mobCountDown = lstMobs.get(i).mobCountDown;
			
			if(lstMobs.get(i).target.equals(playerInfo.name_A)) {
				mobCountDown--;
				lstMobs.get(i).mobCountDown = mobCountDown;
				if(mobCountDown <= 0) {
					if(mobRangeXPlus > playerCoordsX && mobRangeXMinus < playerCoordsX && mobRangeYPlus > playerCoordsY && mobRangeYMinus < playerCoordsY) {
						
						atkHitRandom = randnumber.nextInt(10);
						
						if(atkHitRandom <= 2) {
							mobAtk = lstMobs.get(i).atkHit + 10;
							playerHP = playerHP - mobAtk;
							playerInfo.hp_A = String.valueOf(playerHP);
							lstMobs.get(i).frame = 50;
							
							Damage dmg = new Damage();
							dmg.color = "Red";
							dmg.posX = playerCoordsX + 5;
							dmg.posY = playerCoordsY + 10;
							dmg.user = lstMobs.get(i).name;
							dmg.dmg = mobAtk;
							dmg.frame = 0;					
							lstDanos.add(dmg);
						}
						else 
						{
							mobAtk = lstMobs.get(i).atkHit;
							playerHP = playerHP - mobAtk;
							playerInfo.hp_A = String.valueOf(playerHP);
							lstMobs.get(i).frame = 50;
							
							Damage dmg = new Damage();
							dmg.color = "Red";
							dmg.posX = playerCoordsX + 5;
							dmg.posY = playerCoordsY + 10;
							dmg.user = lstMobs.get(i).name;
							dmg.dmg = mobAtk;
							dmg.frame = 0;					
							lstDanos.add(dmg);
						}	
						
						lstMobs.get(i).mobCountDown = lstMobs.get(i).mobCountDownMax;
					}	
				}
			}
		}		
	}
	
	public boolean CheckSkillType(int numSkill) {
		
		if(playerInfo.job_A.equals("Novice")) {
			//Triple Attack
			if(numSkill == 1) {
				return false;
			}
		}
		
		if(playerInfo.job_A.equals("Swordman")) {
			//flysword
			if(numSkill == 1) {
				return false;
			}
			//Raven blade
			if(numSkill == 2) {
				return false;
			}
			//Heath Boost
			if(numSkill == 3) {
				return false;
			}
		}
		
		
		
		
		return false;
	}
	
	public void SkillAtk(int numSkill) {
		
		if(skillCoolDown > 0) { return; }
		playerCountDown = 200;
		
		playerCoordsX = Float.parseFloat(playerInfo.coordX_A);
		playerCoordsY = Float.parseFloat(playerInfo.coordY_A);
		
		if (playerInfo.job_A.equals("Gunner")) {
			playerRangeXMinus = playerCoordsX - 70;
			playerRangeXPlus = playerCoordsX + 70;
			playerRangeYMinus = playerCoordsY - 70;
			playerRangeYPlus = playerCoordsY + 70;
		}
		else {
			playerRangeXMinus = playerCoordsX - 8;
			playerRangeXPlus = playerCoordsX + 30;
			playerRangeYMinus = playerCoordsY - 10;
			playerRangeYPlus = playerCoordsY + 40;
		}
		
		
		//Novice
		if(playerInfo.job_1.equals("Novice")) {
			//Triple Attack
			if(numSkill == 1) {
				
				for(int i = 0; i < lstMobs.size(); i++) {
					
					mobX = lstMobs.get(i).mobPosX;
					mobY = lstMobs.get(i).mobPosY;
					
					if(playerRangeXPlus > mobX && playerRangeXMinus < mobX && playerRangeYPlus > mobY && playerRangeYMinus < mobY) {
						
						if(playerCoordsX < mobX) { playerSide = "right"; }
						if(playerCoordsX > mobX) { playerSide = "left"; }
					
						if(playerInfo.target_A.equals(lstMobs.get(i).mobID)) {
							
							playerInfo.inBattle_A = "yes";
							playerStatus = playerInfo.stats_A.split("#");
							playerStatusNumber = playerStatus[0].split(":");
							playerStr = Integer.parseInt(playerStatusNumber[1]);
							playerAtk = 3;
							playerAtk = (playerAtk + playerStr) * 3;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 
							
							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));							
								lstMobs.get(i).dead = true;
								playerInfo.inBattle_A = "no";
							}
							
							Damage dmg = new Damage();
							dmg.color = "Yellow";
							dmg.posX = mobX;
							dmg.posY = mobY + 10;
							dmg.user = playerInfo.name_A;
							dmg.dmg = playerAtk;
							dmg.frame = 0;					
							lstDanos.add(dmg);
							
							Skill skl = new Skill();
							skl.caster = playerInfo.name_A;
							skl.name = "Ataque Triplo";
							skl.posX = mobX;
							skl.posY = mobY;
							skl.height = 30;
							skl.width = 30;
							skl.type = "dmg";
							skl.dmg = playerAtk;
							skl.follow = false;
							skl.frame = 1;
							skl.cd = 300;
							lstSkill.add(skl);
							
							skillCoolDown = 300;
							
							OnlineManager("Atk",String.valueOf(mobHP));
							
						}					
					}
				}			
			}
		}
		
		
		//Swordman
		if(playerInfo.job_1.equals("Swordman")) {
			
			//Flysword
			if(numSkill == 1) {
				
				for(int i = 0; i < lstMobs.size(); i++) {
					
					mobX = lstMobs.get(i).mobPosX;
					mobY = lstMobs.get(i).mobPosY;
					
					if(playerRangeXPlus > mobX && playerRangeXMinus < mobX && playerRangeYPlus > mobY && playerRangeYMinus < mobY) {
						
						if(playerCoordsX < mobX) { playerSide = "right"; }
						if(playerCoordsX > mobX) { playerSide = "left"; }
					
						if(playerInfo.target_A.equals(lstMobs.get(i).mobID)) {
							
							playerInfo.inBattle_A = "yes";
							playerStatus = playerInfo.stats_A.split("#");
							playerStatusNumber = playerStatus[0].split(":");
							playerStr = Integer.parseInt(playerStatusNumber[1]);
							playerAtk = 3;
							playerAtk = (playerAtk + playerStr) * 10;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 
							
							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));							
								lstMobs.get(i).dead = true;
								playerInfo.inBattle_A = "no";
							}
							
							Damage dmg = new Damage();
							dmg.color = "Yellow";
							dmg.posX = mobX;
							dmg.posY = mobY + 10;
							dmg.user = playerInfo.name_A;
							dmg.dmg = playerAtk;
							dmg.frame = 0;					
							lstDanos.add(dmg);
							
							Skill skl = new Skill();
							skl.caster = playerInfo.name_A;
							skl.name = "Espadas Voadoras";
							skl.posX = mobX - 15;
							skl.posY = mobY - 5;
							skl.height = 50;
							skl.width = 50;
							skl.type = "dmg";
							skl.dmg = playerAtk;
							skl.follow = false;
							skl.frame = 1;
							skl.cd = 300;
							lstSkill.add(skl);
							
							skillCoolDown = 300;
							
							OnlineManager("Atk",String.valueOf(mobHP));
							
						}					
					}
				}			
			}
			
			//RavenBlade
			if(numSkill == 2) {
					for(int i = 0; i < lstMobs.size(); i++) {
					
					mobX = lstMobs.get(i).mobPosX;
					mobY = lstMobs.get(i).mobPosY;
					
					if(playerRangeXPlus > mobX && playerRangeXMinus < mobX && playerRangeYPlus > mobY && playerRangeYMinus < mobY) {
						
						if(playerCoordsX < mobX) { playerSide = "right"; }
						if(playerCoordsX > mobX) { playerSide = "left"; }
					
						if(playerInfo.target_A.equals(lstMobs.get(i).mobID)) {
							
							playerInfo.inBattle_A = "yes";
							playerStatus = playerInfo.stats_A.split("#");
							playerStatusNumber = playerStatus[4].split(":");
							playerDex = Integer.parseInt(playerStatusNumber[1]);
							playerAtk = 3;
							playerAtk = (playerAtk + (playerDex * 2)) * 5;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 
							
							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));							
								lstMobs.get(i).dead = true;
								playerInfo.inBattle_A = "no";
							}
							
							Damage dmg = new Damage();
							dmg.color = "Yellow";
							dmg.posX = mobX;
							dmg.posY = mobY + 10;
							dmg.user = playerInfo.name_A;
							dmg.dmg = playerAtk;
							dmg.frame = 0;					
							lstDanos.add(dmg);
							
							Skill skl = new Skill();
							skl.caster = playerInfo.name_A;
							skl.name = "Corte da Rapina";
							skl.posX = mobX - 15;
							skl.posY = mobY - 15;
							skl.height = 70;
							skl.width = 50;
							skl.type = "dmg";
							skl.dmg = playerAtk;
							skl.follow = false;
							skl.frame = 1;
							skl.cd = 200;
							lstSkill.add(skl);
							
							skillCoolDown = 300;
							
							OnlineManager("Atk",String.valueOf(mobHP));
							
						}					
					}
				}
			}
			
			//Health Boost
			if(numSkill == 3) {
									
					
				
			}
		}		
	}
	
	public Sprite EffectSkill(Skill skillUsed) {
		
		spr_master = null;
		int frame = skillUsed.frame;
		if(skillUsed.name.equals("Ataque Triplo")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_tripleattack.createSprite("tripleattack1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_tripleattack.createSprite("tripleattack2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_tripleattack.createSprite("tripleattack3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_tripleattack.createSprite("tripleattack4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_tripleattack.createSprite("tripleattack5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_tripleattack.createSprite("tripleattack6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		if(skillUsed.name.equals("Espadas Voadoras")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_flysword.createSprite("flysword1");	} 
			if(frame >= 10 && frame <= 20) { spr_master = atlas_flysword.createSprite("flysword2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_flysword.createSprite("flysword3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_flysword.createSprite("flysword4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_flysword.createSprite("flysword5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_flysword.createSprite("flysword6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		if(skillUsed.name.equals("Corte da Rapina")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_ravenblade.createSprite("ravenblade1");	} 
			if(frame >= 10 && frame <= 20) { spr_master = atlas_ravenblade.createSprite("ravenblade2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_ravenblade.createSprite("ravenblade3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_ravenblade.createSprite("ravenblade4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_ravenblade.createSprite("ravenblade5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_ravenblade.createSprite("ravenblade6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		
		
		
		
		return spr_master;
	}
	
	public void RespawnMob() {
		
		for(int i = 0; i < lstMobs.size(); i++) {
		
			if(lstMobs.get(i).hp <= 0 && lstMobs.get(i).dead == true) {
				
				lstMobs.get(i).mobPosX = 409;
				lstMobs.get(i).mobPosY = 400;
				lstMobs.get(i).target = "none";
				lstMobs.get(i).dead = true;
				lstMobs.get(i).respawnTime--;
			}
			
			if(lstMobs.get(i).respawnTime <= 0 && lstMobs.get(i).dead == true) {
				randomCount = randnumber.nextInt(100);			
				lstMobs.get(i).mobPosX = randomCount;
				
				randomCount = randnumber.nextInt(100);
				lstMobs.get(i).mobPosY = randomCount;
				
				lstMobs.get(i).hp = lstMobs.get(i).maxHP;
				lstMobs.get(i).mp = lstMobs.get(i).maxMP;
				
				lstMobs.get(i).respawnTime = lstMobs.get(i).respawnTimeMax;
				lstMobs.get(i).dead = false;
				
			}
		}
	}
	
	public void GiveLoot(Monster mob) {
		
		randomCount = randnumber.nextInt(100);
		
		if(randomCount > 50) {			
			if(randomCount > 50 && randomCount < 70) {
				lootItemName = mob.loot1;
				countLootShowing = 200;
			}
			if(randomCount > 70 && randomCount < 99) {
				lootItemName = mob.loot2;
				countLootShowing = 200;
			}
			if(randomCount >= 99 && randomCount <= 100) {
				lootItemName = mob.loot3;
				countLootShowing = 200;
			}
		}
		else {
			lootItemName = "none";
			countLootShowing = 0;
		}
	}
	
	public void GiveExp(Monster mob) {
		
		int playerExp = Integer.parseInt(playerInfo.exp_A);
		
		playerExp = playerExp + mob.exp;
		
		
		if(playerInfo.level_A.equals("1") && playerExp >= 100) { playerInfo.level_A = "2"; playerInfo.exp_A = "0"; }
		
		
	}
	
	public Sprite ShowLootItem(float ccX, float ccY) {
		
		if(countLootShowing > 0) {
			if(lootItemName.equals("HPCAN")) { spr_master = atlas_itens.createSprite("hpcan"); }
			if(lootItemName.equals("MPCAN")) { spr_master = atlas_itens.createSprite("mpcan"); }
			if(lootItemName.equals("STCAN")) { spr_master = atlas_itens.createSprite("stcan"); }
			if(lootItemName.equals("CHIPZ")) { spr_master = atlas_itens.createSprite("chipz"); }
			if(lootItemName.equals("GOSMA")) { spr_master = atlas_loots.createSprite("gosma"); }
			if(lootItemName.equals("FOLHAVENENOSA")) { spr_master = atlas_loots.createSprite("folhavenenosa"); }
			if(lootItemName.equals("COGUMELO")) { spr_master = atlas_loots.createSprite("cogumelo"); }
			if(lootItemName.equals("FERRAO")) { spr_master = atlas_loots.createSprite("ferrao"); }
			if(lootItemName.equals("GALHOS")) { spr_master = atlas_loots.createSprite("galhos"); }
			
			spr_master.setSize(7, 12);
			spr_master.setPosition(ccX + 15, ccY + 61.5f);
			
			countLootShowing--;
			return spr_master;
		}
		
		else {
			countLootShowing = 0;
			lootItemName = "none";
			return null;
		}
	
	}
	
	public String GetLootName() {
		return lootItemName;
	}
	
	public void SwitchTarget() {
		String playerTarget = playerInfo.target_A;
		playerCoordsX = Float.parseFloat(playerInfo.coordX_A);
		playerCoordsY = Float.parseFloat(playerInfo.coordY_A);
				
		playerRangeXMinus = playerCoordsX - 65;
		playerRangeXPlus = playerCoordsX + 66;
		
		playerRangeYMinus = playerCoordsY - 34;
		playerRangeYPlus = playerCoordsY + 90;
		
		for(int i = 0; i < lstMobs.size(); i++) {
			
			mobX = lstMobs.get(i).mobPosX;
			mobY = lstMobs.get(i).mobPosY;	
		
			if(playerRangeXPlus > mobX && playerRangeXMinus < mobX && playerRangeYPlus > mobY && playerRangeYMinus < mobY) {
				if(!playerTarget.equals(lstMobs.get(i).mobID)) {
					playerInfo.target_A = lstMobs.get(i).mobID;
					return;
				}
			}	
		}
	}
	
	public Sprite ShowWeapon(float pX, float pY, String walk) {
		if(playerInfo.inBattle_A.equals("yes")) {
			
			if(walk.equals("walk")) {
				return null;
			}
			
			playerInfo.hp_A = "10";
			
			playerWeapon = playerInfo.weapon_A;
			if(playerWeapon.equals("basic_knife")) {
				
				if(playerSide.equals("right")) {
					if(exitAnimationFrame == 0) { 
						spr_master = atlas_basicknife.createSprite("basic_knife_right"); 
						spr_master.setSize(20, 25);
						spr_master.setPosition(pX - 2.5f, pY + 13);
						return spr_master;
					}
					if(exitAnimationFrame > 0) { 
						spr_master = atlas_basicknife.createSprite("basic_knife_attack_right"); 
						spr_master.setSize(20, 25);
						spr_master.setPosition(pX + 7, pY + 4);
						return spr_master;
					}
				}
				
				if(playerSide.equals("left")) {
					if(exitAnimationFrame == 0) { 
						spr_master = atlas_basicknife.createSprite("basic_knife_left"); 
						spr_master.setSize(20, 25);
						spr_master.setPosition(pX + 4.4f, pY + 13);
						return spr_master;
					}
					if(exitAnimationFrame > 0) { 
						spr_master = atlas_basicknife.createSprite("basic_knife_attack_left");  
						spr_master.setSize(20, 25);
						spr_master.setPosition(pX - 5, pY + 4);
						return spr_master;
					}
				}			
			}		
		}
		
		return null;
	}
	
	public void RemoveDamage(int count) {
		lstDanos.remove(count);
	}
	
	public int GetCountDown() {
		return playerCountDown;
	}
	
	public int GetCountDownSkill() {
		return skillCoolDown;
	}
	
	public void ExitBattle() {
		playerCountDown = 200;
		playerInfo.inBattle_A = "no";
	}
	
	public Sprite TargetMobArrow() {	
		return spr_targetArrow;
	}
	
	public ArrayList<Damage> GetDamageList(){
		return lstDanos;
	}
	
	public ArrayList<Skill> GetSkillList(){
		return lstSkill;
	}
}