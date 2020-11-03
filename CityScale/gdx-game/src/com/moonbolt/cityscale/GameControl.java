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
	//[H] Npcs
	//[I] Auxiliary
	
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
	private int statusPointRemain = 0;
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
	private int playerDef = 1;
	private int exitAnimationFrame = 0;
	private int skillCoolDown = 0;
	private String heal = "0";
	private String expShared = "0";
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
	private float touchPosX;
	private float touchPosY;
	private boolean isCasting;
	private boolean playerRangeHit;
	private boolean playerAtkCooldownHit;
	private int playerStr;
	private int playerAgi;
	private int playerDex;
	private int playerWis;
	private int playerLuk;
	private int playerRes;
	private int playerVit;
	private int playerStamina;
	private int playerStaminaMax;
	private int loopStamina = 2000;
	
	//Mob Variables
	private int mobCountDown;  
	private int mobHP;
	private int mobMP;
	private int mobAtk;
	private int walkInDirection = 0;
	private float mobX;
	private float mobY;
	private float mobHeight;
	private float mobWidth;
	private float mobRangeXMinus;
	private float mobRangeXPlus;
	private float mobRangeYMinus;
	private float mobRangeYPlus;
	private String mobDirectionOnWalk = "wait";
	private String damageOnline = "0";
	private String mobStatus = "";
	private String MobsHPSync = "";
	private String syncPlayerMob = "yes";
	private String mobCondition = "";
	
	//NPCs
	private int npcFrame = 1;
	private float npcFromRight = 249;
	private float npcFromLeft = -110;
	private float npcFromLeft2 = -110;
	private float npcFromLeft3 = -110;
	private float npcFromTop = -110;
	private float npcFromBottom = 249;
	private ArrayList<Sprite> lstNpcs;
	
	
	//Online
	private int threahCount = 0;
	private boolean onlineCheck = false; 
	private ArrayList<String> lstChats;
	private ArrayList<Player> lstPlayersOnline;
	private String onlineDataReceive = "";
	private String[] dataSplit;
	private String[] dataSplitExtra;
	private String[] dataInfoSplit;
	private int countMobSync = 0;
	private int countMobLoop = 0;
	private int countplayerOnline = 0;
	private int countPartyPlayers = 0;
	private String expsharedOnline = "0";
	private int expsharedReceive = 0;
	private String IDSharedOnline = "0";
	private int cdExpSharedSend = 0;
	private int cdExpShared = 0;
	private int cdHealShared = 0;
	private int countMembersParty = 0;
	private int auxShared = 0;
	private int healStop = 0;
	private String healOnline = "";
	private String OnlineRequest = "Aguardando..";
	
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
	private TextureAtlas atlas_sport_m;
	private TextureAtlas atlas_schoolpride_f;
	private TextureAtlas atlas_hat;
	private TextureAtlas atlas_InterfaceCreate;
	private TextureAtlas atlas_shops;
	private TextureAtlas atlas_itens;
	private TextureAtlas atlas_npc;
	
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
	private TextureAtlas atlas_soulclash;
	
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
	private TextureAtlas atlas_doubleedgeknife;
	private TextureAtlas atlas_swords;
	private TextureAtlas atlas_guns;
	private TextureAtlas atlas_rods;
	private TextureAtlas atlas_daggers;
	private TextureAtlas atlas_axes;
	
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
		lstNpcs = new ArrayList<Sprite>();
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
		
		atlas_sport_m = new TextureAtlas(Gdx.files.internal("data/characters/players/sportset/sportset.txt"));
		atlas_schoolpride_f = new TextureAtlas(Gdx.files.internal("data/characters/players/schoolpridesetF/schoolpridesetF.txt"));
		
		//Hats
		atlas_hat = new TextureAtlas(Gdx.files.internal("data/itens/hats.txt"));
		
		//NPCs
		atlas_npc = new TextureAtlas(Gdx.files.internal("data/characters/npcs/npcs.txt"));
		
		//Itens 
		atlas_itens = new TextureAtlas(Gdx.files.internal("data/itens/itens.txt"));
		
		//Weapons Sets
		atlas_basicknife = new TextureAtlas(Gdx.files.internal("data/weapons/nknifes.txt"));
		atlas_doubleedgeknife = new TextureAtlas(Gdx.files.internal("data/weapons/nknifes.txt"));
		
		atlas_swords = new TextureAtlas(Gdx.files.internal("data/weapons/swords.txt"));
		atlas_guns = new TextureAtlas(Gdx.files.internal("data/weapons/pistols.txt"));
		atlas_rods = new TextureAtlas(Gdx.files.internal("data/weapons/rods.txt"));
		atlas_daggers = new TextureAtlas(Gdx.files.internal("data/weapons/daggers.txt"));
		atlas_axes = new TextureAtlas(Gdx.files.internal("data/weapons/axes.txt"));
		
		
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
		atlas_soulclash = new TextureAtlas(Gdx.files.internal("data/skills/base/skilleffect/soulclash.txt"));
		
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
		atlas_MonstersSewer = new TextureAtlas(Gdx.files.internal("data/monsters/mobsSewers.txt"));
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
			playerInfo.weapon_1 = "BASICKNIFE";
			playerInfo.level_1 = "1";
			playerInfo.stats_1 = "str:1#agi:1#wis:1#vit:1#des:1#sor:1#res:1";
			playerInfo.stamina_1 = "100";
			playerInfo.staminamax_1 = "100";
			if(sex.equals("M")) { playerInfo.set_1 = "BASICSET"; } else { playerInfo.set_1 = "BASICSETF"; }
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
			playerInfo.atk_1 = "3";
			playerInfo.def_1 = "0";
			playerInfo.cooldown_1 = "";
			playerInfo.statusPoint_1 = "0";
			playerInfo.skillPoint_1 = "0";
			playerInfo.skills_1 = "none";
			playerInfo.buffsA_1 = "none-0";
			playerInfo.buffsB_1 = "none-0";
			playerInfo.buffsC_1 = "none-0";
			playerInfo.inBattle_1 = "no";
			playerInfo.target_1 = "none";
			playerInfo.map_1 = "MetroStation";
			playerInfo.party_1 = "none";
			playerInfo.inCasting_1 = "no";
			playerInfo.quest_1 = "Starting";
			playerInfo.state_1 = "stop";
			playerInfo.hotkey1_1 = "none";
			playerInfo.hotkey2_1 = "none";
			playerInfo.heal_1 = "0";
			playerInfo.expshared_1 = "0";
					
			for(int i = 0; i < 48; i++) {
				itensList = itensList + "[HPCAN#1]-";
			}			
			playerInfo.itens_1 = itensList;
		}
		
		if(charNumber == 2) {
			playerInfo.name_2 = name;
			playerInfo.sex_2 = sex;
			playerInfo.job_2 = "Novice";
			playerInfo.weapon_2 = "BASICKNIFE";
			playerInfo.level_2 = "1";
			playerInfo.stats_2 = "str:1#agi:1#wis:1#vit:1#des:1#sor:1#res:1";
			playerInfo.stamina_2 = "100";
			playerInfo.staminamax_2 = "100";
			if(sex.equals("M")) { playerInfo.set_2 = "BASICSET"; } else { playerInfo.set_2 = "BASICSETF"; }
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
			playerInfo.atk_2 = "3";
			playerInfo.def_2 = "0";
			playerInfo.money_2 = "50";
			playerInfo.cooldown_2 = "";
			playerInfo.statusPoint_2 = "0";
			playerInfo.skillPoint_2 = "0";
			playerInfo.skills_2 = "none";
			playerInfo.buffsA_2 = "none-0";
			playerInfo.buffsB_2 = "none-0";
			playerInfo.buffsC_2 = "none-0";
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
			playerInfo.heal_2 = "0";
			playerInfo.expshared_2 = "0";
			
			for(int i = 0; i < 48; i++) {
				itensList = itensList + "[NONE]-";
			}			
			playerInfo.itens_2 = itensList;
		}
		
		if(charNumber == 3) {
			playerInfo.name_3 = name;
			playerInfo.sex_3 = sex;
			playerInfo.job_3 = "Novice";
			playerInfo.weapon_3 = "BASICKNIFE";
			playerInfo.level_3 = "1";
			playerInfo.stats_3 = "str:1#agi:1#wis:1#vit:1#des:1#sor:1#res:1";
			playerInfo.stamina_3 = "100";
			playerInfo.staminamax_3 = "100";
			if(sex.equals("M")) { playerInfo.set_3 = "BASICSET"; } else { playerInfo.set_3 = "BASICSETF"; }
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
			playerInfo.atk_3 = "3";
			playerInfo.def_3 = "0";
			playerInfo.money_3 = "50";
			playerInfo.cooldown_3 = "";
			playerInfo.statusPoint_3 = "0";
			playerInfo.skillPoint_3 = "0";
			playerInfo.skills_3 = "none";
			playerInfo.buffsA_3 = "none-0";
			playerInfo.buffsB_3 = "none-0";
			playerInfo.buffsC_3 = "none-0";
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
			playerInfo.heal_3 = "0";
			playerInfo.expshared_3 = "0";
			
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
				playerInfo.staminamax_1 = playerInfo.staminamax_A;
				playerInfo.sex_1 = playerInfo.sex_A; 
				playerInfo.hat_1 = playerInfo.hat_A;
				playerInfo.exp_1 = playerInfo.exp_A;
				playerInfo.hp_1 = playerInfo.hp_A;
				playerInfo.mp_1 = playerInfo.mp_A;
				playerInfo.maxhp_1 = playerInfo.maxhp_A;
				playerInfo.maxmp_1 = playerInfo.maxmp_A;
				playerInfo.atk_1 = playerInfo.atk_A;
				playerInfo.def_1 = playerInfo.def_A;
				playerInfo.money_1 = playerInfo.money_A;
				playerInfo.cooldown_1 = playerInfo.cooldown_A;
				playerInfo.statusPoint_1 = playerInfo.statusPoint_A;
				playerInfo.skillPoint_1 = playerInfo.skillPoint_A;
				playerInfo.skills_1 = playerInfo.skills_A;
				playerInfo.buffsA_1 = playerInfo.buffsA_A;
				playerInfo.buffsB_1 = playerInfo.buffsB_A;
				playerInfo.buffsC_1 = playerInfo.buffsC_A;
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
				playerInfo.heal_1 = playerInfo.heal_A;
				playerInfo.expshared_1 = playerInfo.expshared_A;
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
				playerInfo.staminamax_2 = playerInfo.staminamax_A;
				playerInfo.sex_2 = playerInfo.sex_A; 
				playerInfo.hat_2 = playerInfo.hat_A;
				playerInfo.exp_2 = playerInfo.exp_A;
				playerInfo.hp_2 = playerInfo.hp_A;
				playerInfo.mp_2 = playerInfo.mp_A;
				playerInfo.maxhp_2 = playerInfo.maxhp_A;
				playerInfo.maxmp_2 = playerInfo.maxmp_A;
				playerInfo.atk_2 = playerInfo.atk_A;
				playerInfo.def_2 = playerInfo.def_A;
				playerInfo.money_2 = playerInfo.money_A;
				playerInfo.cooldown_2 = playerInfo.cooldown_A;
				playerInfo.statusPoint_2 = playerInfo.statusPoint_A;
				playerInfo.skillPoint_2 = playerInfo.skillPoint_A;
				playerInfo.skills_2 = playerInfo.skills_A;
				playerInfo.buffsA_2 = playerInfo.buffsA_A;
				playerInfo.buffsB_2 = playerInfo.buffsB_A;
				playerInfo.buffsC_2 = playerInfo.buffsC_A;
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
				playerInfo.heal_2 = playerInfo.heal_A;
				playerInfo.expshared_2 = playerInfo.expshared_A;
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
				playerInfo.staminamax_3 = playerInfo.staminamax_A;
				playerInfo.sex_3 = playerInfo.sex_A; 
				playerInfo.hat_3 = playerInfo.hat_A;
				playerInfo.exp_3 = playerInfo.exp_A;
				playerInfo.hp_3 = playerInfo.hp_A;
				playerInfo.mp_3 = playerInfo.mp_A;
				playerInfo.maxhp_3 = playerInfo.maxhp_A;
				playerInfo.maxmp_3 = playerInfo.maxmp_A;
				playerInfo.atk_3 = playerInfo.atk_A;
				playerInfo.def_3 = playerInfo.def_A;
				playerInfo.money_3 = playerInfo.money_A;
				playerInfo.cooldown_3 = playerInfo.cooldown_A;
				playerInfo.statusPoint_3 = playerInfo.statusPoint_A;
				playerInfo.skillPoint_3 = playerInfo.skillPoint_A;
				playerInfo.skills_3 = playerInfo.skills_A;
				playerInfo.buffsA_3 = playerInfo.buffsA_A;
				playerInfo.buffsB_3 = playerInfo.buffsB_A;
				playerInfo.buffsC_3 = playerInfo.buffsC_A;
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
				playerInfo.heal_3 = playerInfo.heal_A;
				playerInfo.expshared_3 = playerInfo.expshared_A;
			}
			SaveData(playerInfo);
			savetimer = 800;
		}
	}
	
	public Player SetActivePlayerData(int num) {
		
		playerInfo.party_1 = "None";
		playerInfo.party_2 = "None";
		playerInfo.party_3 = "None";
		
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
			playerInfo.staminamax_A = playerInfo.staminamax_1;
			playerInfo.sex_A = playerInfo.sex_1; 
			playerInfo.hat_A = playerInfo.hat_1;
			playerInfo.exp_A = playerInfo.exp_1;
			playerInfo.hp_A = playerInfo.hp_1;
			playerInfo.mp_A = playerInfo.mp_1;
			playerInfo.maxhp_A = playerInfo.maxhp_1;
			playerInfo.maxmp_A = playerInfo.maxmp_1;
			playerInfo.atk_A = playerInfo.atk_1;
			playerInfo.def_A = playerInfo.def_1;
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
			playerInfo.buffsA_A = playerInfo.buffsA_1;
			playerInfo.buffsB_A = playerInfo.buffsB_1;
			playerInfo.buffsC_A = playerInfo.buffsC_1;
			playerInfo.heal_A = playerInfo.heal_1;
			playerInfo.expshared_A = playerInfo.expshared_1;
			
			CleanBuffEffectsLog();
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
			playerInfo.staminamax_A = playerInfo.staminamax_2;
			playerInfo.sex_A = playerInfo.sex_2; 
			playerInfo.hat_A = playerInfo.hat_2;
			playerInfo.exp_A = playerInfo.exp_2;
			playerInfo.hp_A = playerInfo.hp_2;
			playerInfo.mp_A = playerInfo.mp_2;
			playerInfo.maxhp_A = playerInfo.maxhp_2;
			playerInfo.maxmp_A = playerInfo.maxmp_2;
			playerInfo.atk_A = playerInfo.atk_2;
			playerInfo.def_A = playerInfo.def_2;
			playerInfo.money_A = playerInfo.money_2;
			playerInfo.cooldown_A = playerInfo.cooldown_2;
			playerInfo.statusPoint_A = playerInfo.statusPoint_2;
			playerInfo.skillPoint_A = playerInfo.skillPoint_2;
			playerInfo.skills_A = playerInfo.skills_2;
			playerInfo.buffsA_A = playerInfo.buffsA_2;
			playerInfo.buffsB_A = playerInfo.buffsB_2;
			playerInfo.buffsC_A = playerInfo.buffsC_2;
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
			playerInfo.heal_2 = playerInfo.heal_A;
			playerInfo.expshared_2 = playerInfo.expshared_A;
			
			CleanBuffEffectsLog();
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
			playerInfo.staminamax_A = playerInfo.staminamax_3;
			playerInfo.sex_A = playerInfo.sex_3; 
			playerInfo.hat_A = playerInfo.hat_3;
			playerInfo.exp_A = playerInfo.exp_3;
			playerInfo.hp_A = playerInfo.hp_3;
			playerInfo.mp_A = playerInfo.mp_3;
			playerInfo.maxhp_A = playerInfo.maxhp_3;
			playerInfo.maxmp_A = playerInfo.maxmp_3;
			playerInfo.atk_A = playerInfo.atk_3;
			playerInfo.def_A = playerInfo.def_3;
			playerInfo.money_A = playerInfo.money_3;
			playerInfo.cooldown_A = playerInfo.cooldown_3;
			playerInfo.statusPoint_A = playerInfo.statusPoint_3;
			playerInfo.skillPoint_A = playerInfo.skillPoint_3;
			playerInfo.skills_A = playerInfo.skills_3;
			playerInfo.buffsA_A = playerInfo.buffsA_3;
			playerInfo.buffsB_A = playerInfo.buffsB_3;
			playerInfo.buffsC_A = playerInfo.buffsC_3;
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
			playerInfo.heal_3 = playerInfo.heal_A;
			playerInfo.expshared_3 = playerInfo.expshared_A;
			
			CleanBuffEffectsLog();
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
	
	
	public void SetPartyName(String partyName) {
		
		countMembersParty = 0;
		if(onlineCheck) {
		for(int i = 0; i < lstPlayersOnline.size(); i++) {
			if(lstPlayersOnline.get(i).party_A.equals(partyName) && !lstPlayersOnline.get(i).accountID.equals(playerInfo.accountID)) {
				countMembersParty++;
			}
		}
		
		if(countMembersParty <= 2) {
			playerInfo.party_A = partyName;
		}
		
		}
	}
	
	public void LeaveParty() {
		playerInfo.party_A = "None";
	}
	
	
	public void CheckStamina() {
		loopStamina--;
		
		if(loopStamina < 0) {
			loopStamina = 10000;
			playerStamina = Integer.parseInt(playerInfo.stamina_A);
			playerStamina--;		
			playerInfo.stamina_A = String.valueOf(playerStamina);
			if(playerStamina <= 0) { playerInfo.stamina_A = "0"; }			
		}
	}
	
	
	public void DistributeStatusPoint(String statusSelect) {
		
		statusPointRemain = Integer.parseInt(playerInfo.statusPoint_A);
		
		statusPointRemain = statusPointRemain + 10;
		
		
		if(statusPointRemain > 0) {
			if(statusSelect.equals("Str")) {
				playerStatus = playerInfo.stats_A.split("#");
				playerStatusNumber = playerStatus[0].split(":");
				playerStr = Integer.parseInt(playerStatusNumber[1]);
				if(playerStr >= 99) { return; }
				playerStr++;			
				playerStatus[0] = "str:" + String.valueOf(playerStr);
				
				playerInfo.stats_A = playerStatus[0] + "#" + playerStatus[1] + "#" + playerStatus[2] + "#" + playerStatus[3] + "#" +
									 playerStatus[4] + "#" + playerStatus[5] + "#" + playerStatus[6];
				
				playerAtk = Integer.parseInt(playerInfo.atk_A);
				playerAtk++;
				playerInfo.atk_A = String.valueOf(playerAtk);
				
				statusPointRemain--;
				playerInfo.statusPoint_A = String.valueOf(statusPointRemain);
			}
			
			if(statusSelect.equals("Agi")) {
				playerStatus = playerInfo.stats_A.split("#");
				playerStatusNumber = playerStatus[1].split(":");
				playerAgi = Integer.parseInt(playerStatusNumber[1]);
				if(playerAgi >= 99) { return; }
				playerAgi++;			
				playerStatus[1] = "agi:" + String.valueOf(playerAgi);
				
				playerInfo.stats_A = playerStatus[0] + "#" + playerStatus[1] + "#" + playerStatus[2] + "#" + playerStatus[3] + "#" +
									 playerStatus[4] + "#" + playerStatus[5] + "#" + playerStatus[6];
				
				statusPointRemain--;
				playerInfo.statusPoint_A = String.valueOf(statusPointRemain);
			}
			
			if(statusSelect.equals("Wis")) {
				playerStatus = playerInfo.stats_A.split("#");
				playerStatusNumber = playerStatus[2].split(":");
				playerWis = Integer.parseInt(playerStatusNumber[1]);
				if(playerWis >= 99) { return; }
				playerWis++;			
				playerStatus[2] = "wis:" + String.valueOf(playerWis);
				
				playerInfo.stats_A = playerStatus[0] + "#" + playerStatus[1] + "#" + playerStatus[2] + "#" + playerStatus[3] + "#" +
									 playerStatus[4] + "#" + playerStatus[5] + "#" + playerStatus[6];
				
				playerMPMax = Integer.parseInt(playerInfo.maxmp_A);
				playerMPMax = playerMPMax + 20;
				playerInfo.maxmp_A = String.valueOf(playerMPMax);
				
				statusPointRemain--;
				playerInfo.statusPoint_A = String.valueOf(statusPointRemain);
			}
			
			if(statusSelect.equals("Vit")) {
				playerStatus = playerInfo.stats_A.split("#");
				playerStatusNumber = playerStatus[3].split(":");
				playerVit = Integer.parseInt(playerStatusNumber[1]);
				if(playerVit >= 99) { return; }
				playerVit++;			
				playerStatus[3] = "vit:" + String.valueOf(playerVit);
				
				playerInfo.stats_A = playerStatus[0] + "#" + playerStatus[1] + "#" + playerStatus[2] + "#" + playerStatus[3] + "#" +
									 playerStatus[4] + "#" + playerStatus[5] + "#" + playerStatus[6];
				
				playerHPMax = Integer.parseInt(playerInfo.maxhp_A);
				playerHPMax = playerHPMax + 15;
				playerInfo.maxhp_A = String.valueOf(playerHPMax);
				
				statusPointRemain--;
				playerInfo.statusPoint_A = String.valueOf(statusPointRemain);
			}
			
			if(statusSelect.equals("Des")) {
				playerStatus = playerInfo.stats_A.split("#");
				playerStatusNumber = playerStatus[4].split(":");
				playerDex = Integer.parseInt(playerStatusNumber[1]);
				if(playerDex >= 99) { return; }
				playerDex++;			
				playerStatus[4] = "des:" + String.valueOf(playerDex);
				
				playerInfo.stats_A = playerStatus[0] + "#" + playerStatus[1] + "#" + playerStatus[2] + "#" + playerStatus[3] + "#" +
									 playerStatus[4] + "#" + playerStatus[5] + "#" + playerStatus[6];
				
				statusPointRemain--;
				playerInfo.statusPoint_A = String.valueOf(statusPointRemain);
			}
			
			if(statusSelect.equals("Sor")) {
				playerStatus = playerInfo.stats_A.split("#");
				playerStatusNumber = playerStatus[5].split(":");
				playerLuk = Integer.parseInt(playerStatusNumber[1]);
				if(playerLuk >= 99) { return; }
				playerLuk++;			
				playerStatus[5] = "sor:" + String.valueOf(playerLuk);
				
				playerInfo.stats_A = playerStatus[0] + "#" + playerStatus[1] + "#" + playerStatus[2] + "#" + playerStatus[3] + "#" +
									 playerStatus[4] + "#" + playerStatus[5] + "#" + playerStatus[6];
				
				statusPointRemain--;
				playerInfo.statusPoint_A = String.valueOf(statusPointRemain);
			}
			
			if(statusSelect.equals("Res")) {
				playerStatus = playerInfo.stats_A.split("#");
				playerStatusNumber = playerStatus[6].split(":");
				playerRes = Integer.parseInt(playerStatusNumber[1]);
				if(playerRes >= 99) { return; }
				playerRes++;			
				playerStatus[6] = "res:" + String.valueOf(playerRes);
				
				playerInfo.stats_A = playerStatus[0] + "#" + playerStatus[1] + "#" + playerStatus[2] + "#" + playerStatus[3] + "#" +
									 playerStatus[4] + "#" + playerStatus[5] + "#" + playerStatus[6];
				
				playerHPMax = Integer.parseInt(playerInfo.maxhp_A);
				playerHPMax = playerHPMax + 3;
				playerInfo.maxhp_A = String.valueOf(playerHPMax);
				
				playerDef = Integer.parseInt(playerInfo.def_A);
				playerDef = playerDef++;
				playerInfo.def_A = String.valueOf(playerDef);
				
				playerStamina = Integer.parseInt(playerInfo.staminamax_A);
				playerStamina = playerStamina + 3;
				playerInfo.staminamax_A = String.valueOf(playerStamina);
				
				statusPointRemain--;
				playerInfo.statusPoint_A = String.valueOf(statusPointRemain);
			}
		
		}
		
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
		
		//Account ID bar
		if(type.equals("IDbar")) {
			spr_master = atlas_InterfaceCreate.createSprite("barloot");
			spr_master.setSize(50,15);
			spr_master.setPosition(25,60);
			return spr_master;
			
		}
		
		//Player Tag
		if(type.equals("playerTag")) {
			spr_master = atlas_InterfaceCreate.createSprite("tagPlayer");
			spr_master.setSize(33,27);
			spr_master.setPosition(cameraCoordsX - 66,cameraCoordsY + 68);
			return spr_master;
		}
		
		//Party Tag
		if(type.equals("tagParty") && value.equals("1")) {
			spr_master = atlas_InterfaceCreate.createSprite("tagParty");
			spr_master.setSize(25,19);
			spr_master.setPosition(cameraCoordsX - 66,cameraCoordsY + 48);
			return spr_master;
		}
		if(type.equals("tagParty") && value.equals("2")) {
			spr_master = atlas_InterfaceCreate.createSprite("tagParty");
			spr_master.setSize(25,19);
			spr_master.setPosition(cameraCoordsX - 66,cameraCoordsY + 29);
			return spr_master;
		}
		if(type.equals("tagParty") && value.equals("3")) {
			spr_master = atlas_InterfaceCreate.createSprite("tagParty");
			spr_master.setSize(25,19);
			spr_master.setPosition(cameraCoordsX - 66,cameraCoordsY + 10);
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
		//Hair Tag Party
		if(type.equals("hairTagParty1")) {
			if(extra.equals("M")) {
				spr_master = atlas_hairs.createSprite(value);
				spr_master.setSize(8, 12);
				spr_master.setPosition(cameraCoordsX  - 66.5f,cameraCoordsY + 54);
				return spr_master;
			}
			if(extra.equals("F")) {
				spr_master = atlas_hairs.createSprite(value + "_f");
				spr_master.setSize(8, 12);
				spr_master.setPosition(cameraCoordsX - 66.5f,cameraCoordsY + 54);
				return spr_master;
			}
		}
		if(type.equals("hairTagParty2")) {
			if(extra.equals("M")) {
				spr_master = atlas_hairs.createSprite(value);
				spr_master.setSize(8, 12);
				spr_master.setPosition(cameraCoordsX - 66.5f,cameraCoordsY + 35);
				return spr_master;
			}
			if(extra.equals("F")) {
				spr_master = atlas_hairs.createSprite(value + "_f");
				spr_master.setSize(8, 12);
				spr_master.setPosition(cameraCoordsX - 66.5f,cameraCoordsY + 35);
				return spr_master;
			}
		}
		
		if(type.equals("hairTagParty3")) {
			if(extra.equals("M")) {
				spr_master = atlas_hairs.createSprite(value);
				spr_master.setSize(8, 12);
				spr_master.setPosition(cameraCoordsX - 66.5f,cameraCoordsY + 16);
				return spr_master;
			}
			if(extra.equals("F")) {
				spr_master = atlas_hairs.createSprite(value + "_f");
				spr_master.setSize(8, 12);
				spr_master.setPosition(cameraCoordsX - 66.5f,cameraCoordsY + 16);
				return spr_master;
			}
		}
		
		
		
		//Area Select Skill
		if(type.equals("areaSelect")) {
			spr_master = atlas_InterfaceCreate.createSprite("areaSelect");
			spr_master.setSize(30,16);
			spr_master.setPosition(cameraCoordsX - 10,cameraCoordsY + 48);
			return spr_master;
		}
		
		//Area Target 
		if(type.equals("skillareatarget")) {
			spr_master = atlas_InterfaceCreate.createSprite("skillareatarget");
			touchPosX = Float.parseFloat(value);
			touchPosY = Float.parseFloat(extra);
			spr_master.setPosition(touchPosX - 10,touchPosY - 10);
			spr_master.setSize(40,40);
			
			return spr_master;
		}
		
		//Bar job change
		if(type.equals("btnjobchange")) {
			spr_master = atlas_InterfaceCreate.createSprite("btnMestreClasses");
			spr_master.setSize(8,12);
			spr_master.setPosition(22, -28);
			return spr_master;
		}
		
		//Board Job
		if(type.equals("boardJob")) {
			spr_master = atlas_InterfaceCreate.createSprite("JobBoard");
			spr_master.setSize(42,80);
			spr_master.setPosition(cameraCoordsX - 20, cameraCoordsY - 5);
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
			spr_master = atlas_shops.createSprite("shopsRefri");
			spr_master.setSize(50,80);
			spr_master.setPosition(cameraCoordsX + 10,cameraCoordsY - 5);
			return spr_master;		
		}
		if(type.equals("Classical")) {
			spr_master = atlas_shops.createSprite("shopClassico");
			spr_master.setSize(50,80);
			spr_master.setPosition(cameraCoordsX + 10,cameraCoordsY - 5);
			return spr_master;		
		}
		if(type.equals("305")) {
			spr_master = atlas_shops.createSprite("shop305");
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
				
			if(playerInfo.buffsA_A.contains("healthboost")) { return; }
			if(playerInfo.buffsB_A.contains("healthboost")) { return; }
			if(playerInfo.buffsC_A.contains("healthboost")) { return; }
			
			//regen	
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
			
			if(playerInfo.buffsA_A.contains("regen")) { recoverytimer = 500; return; }
			if(playerInfo.buffsB_A.contains("regen")) { recoverytimer = 500; return; }
			if(playerInfo.buffsC_A.contains("regen")) { recoverytimer = 500; return; }
			
			recoverytimer = 2500;
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
		//exitAnimationFrame = 3;
		
		//Male
		if(sex.equals("M")) {
			if(set.equals("BASICSET")) {
				
				//Casting
				if(isCasting && playerSide.equals("right")) { spr_master = atlas_basicset_m.createSprite("attackmagicright"); return spr_master; }
				if(isCasting && playerSide.equals("left")) { spr_master = atlas_basicset_m.createSprite("attackmagicleft"); return spr_master; }
				
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
						if(playerSide.equals("right") && exitAnimationFrame > 0) { spr_master = atlas_basicset_m.createSprite("attackmagicright"); return spr_master; }
						if(playerSide.equals("left") && exitAnimationFrame > 0) { spr_master = atlas_basicset_m.createSprite("attackmagicleft"); return spr_master; }
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
			
			if(set.equals("SPORTSET")) { 
				
				//Casting
				if(isCasting && playerSide.equals("right")) { spr_master = atlas_sport_m.createSprite("attackmagicright"); return spr_master; }
				if(isCasting && playerSide.equals("left")) { spr_master = atlas_sport_m.createSprite("attackmagicleft"); return spr_master; }
				
				//Menu
				if(menu) {
					if(state.equals("front")) { spr_master = atlas_sport_m.createSprite("front1"); return spr_master; }
				}
				
				//in Battle
				if(walk.equals("stop") && playerInfo.inBattle_A.equals("yes")) {
					countFrameMov++;
					if(playerSide.equals("right") && frame == 1 && exitAnimationFrame == 0) { spr_master = atlas_sport_m.createSprite("battle1right"); return spr_master; }
					if(playerSide.equals("left") && frame == 1 && exitAnimationFrame == 0) { spr_master = atlas_sport_m.createSprite("battle1left"); return spr_master; }
					
					if(playerSide.equals("right") && frame == 2 && exitAnimationFrame == 0) { spr_master = atlas_sport_m.createSprite("battle2right"); return spr_master; }
					if(playerSide.equals("left") && frame == 2 && exitAnimationFrame == 0) { spr_master = atlas_sport_m.createSprite("battle2left"); return spr_master; }
					
					if(playerSide.equals("right") && frame == 3 && exitAnimationFrame == 0) { spr_master = atlas_sport_m.createSprite("battle3right"); return spr_master; }
					if(playerSide.equals("left") && frame == 3 && exitAnimationFrame == 0) { spr_master = atlas_sport_m.createSprite("battle3left"); return spr_master; }
					
					if(playerInfo.job_A.equals("Mage") || playerInfo.job_A.equals("Medic")) {
						if(playerSide.equals("right") && exitAnimationFrame > 0) { spr_master = atlas_sport_m.createSprite("attackmagicright"); return spr_master; }
						if(playerSide.equals("left") && exitAnimationFrame > 0) { spr_master = atlas_sport_m.createSprite("attackmagicleft"); return spr_master; }
					}
					else {
						if(playerSide.equals("right") && exitAnimationFrame > 0) { spr_master = atlas_sport_m.createSprite("attackhitright"); return spr_master; }
						if(playerSide.equals("left") && exitAnimationFrame > 0) { spr_master = atlas_sport_m.createSprite("attackhitleft"); return spr_master; }
					}				
				}
				
				
				//Stop
				if(walk.equals("stop")) {
					frame = 1;
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 1) { spr_master = atlas_sport_m.createSprite("front1"); return spr_master; }
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 1) { spr_master = atlas_sport_m.createSprite("back1"); return spr_master; }
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 1) { spr_master = atlas_sport_m.createSprite("left1"); return spr_master; }
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 1) { spr_master = atlas_sport_m.createSprite("right1"); return spr_master; }
				}
				
				//Walk Front
				if(walk.equals("walk")) {
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 1) { spr_master = atlas_sport_m.createSprite("front1"); return spr_master; }
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 2) { spr_master = atlas_sport_m.createSprite("front2"); return spr_master; }
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 3) { spr_master = atlas_sport_m.createSprite("front3"); return spr_master; }
					
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 1) { spr_master = atlas_sport_m.createSprite("back1"); return spr_master; }
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 2) { spr_master = atlas_sport_m.createSprite("back2"); return spr_master; }
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 3) { spr_master = atlas_sport_m.createSprite("back3"); return spr_master; }
					
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 1) { spr_master = atlas_sport_m.createSprite("left1"); return spr_master; }
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 2) { spr_master = atlas_sport_m.createSprite("left2"); return spr_master; }
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 3) { spr_master = atlas_sport_m.createSprite("left3"); return spr_master; }
					
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 1) { spr_master = atlas_sport_m.createSprite("right1"); return spr_master; }
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 2) { spr_master = atlas_sport_m.createSprite("right2"); return spr_master; }
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 3) { spr_master = atlas_sport_m.createSprite("right3"); return spr_master; }
				}				
			}
		}
		
			//Female
			if(sex.equals("F")) {
				if(set.equals("BASICSETF")) {
					
					//Casting
					if(isCasting && playerSide.equals("right")) { spr_master = atlas_basicset_f.createSprite("attackmagicright"); return spr_master; }
					if(isCasting && playerSide.equals("left")) { spr_master = atlas_basicset_f.createSprite("attackmagicleft"); return spr_master; }
					
					//Menu
					if(menu) {
						if(state.equals("front")) { spr_master = atlas_basicset_f.createSprite("front1"); return spr_master; }
					}
					
					//in Battle
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
							if(playerSide.equals("right") && exitAnimationFrame > 0) { spr_master = atlas_basicset_f.createSprite("attackmagicright"); return spr_master; }
							if(playerSide.equals("left") && exitAnimationFrame > 0) { spr_master = atlas_basicset_f.createSprite("attackmagicleft"); return spr_master; }
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
				
			if(set.equals("SCHOOLPRIDESETF")) {
				
				//Casting
				if(isCasting && playerSide.equals("right")) { spr_master = atlas_schoolpride_f.createSprite("attackmagicright"); return spr_master; }
				if(isCasting && playerSide.equals("left")) { spr_master = atlas_schoolpride_f.createSprite("attackmagicleft"); return spr_master; }
				
				//Menu
				if(menu) {
					if(state.equals("front")) { spr_master = atlas_schoolpride_f.createSprite("front1"); return spr_master; }
				}
				
				//in Battle
				if(walk.equals("stop") && playerInfo.inBattle_A.equals("yes")) {
					countFrameMov++;
					if(playerSide.equals("right") && frame == 1 && exitAnimationFrame == 0) { spr_master = atlas_schoolpride_f.createSprite("battle1right"); return spr_master; }
					if(playerSide.equals("left") && frame == 1 && exitAnimationFrame == 0) { spr_master = atlas_schoolpride_f.createSprite("battle1left"); return spr_master; }
					
					if(playerSide.equals("right") && frame == 2 && exitAnimationFrame == 0) { spr_master = atlas_schoolpride_f.createSprite("battle2right"); return spr_master; }
					if(playerSide.equals("left") && frame == 2 && exitAnimationFrame == 0) { spr_master = atlas_schoolpride_f.createSprite("battle2left"); return spr_master; }
					
					if(playerSide.equals("right") && frame == 3 && exitAnimationFrame == 0) { spr_master = atlas_schoolpride_f.createSprite("battle3right"); return spr_master; }
					if(playerSide.equals("left") && frame == 3 && exitAnimationFrame == 0) { spr_master = atlas_schoolpride_f.createSprite("battle3left"); return spr_master; }
					
					if(playerInfo.job_A.equals("Mage") || playerInfo.job_A.equals("Medic")) {
						if(playerSide.equals("right") && exitAnimationFrame > 0) { spr_master = atlas_schoolpride_f.createSprite("attackmagicright"); return spr_master; }
						if(playerSide.equals("left") && exitAnimationFrame > 0) { spr_master = atlas_schoolpride_f.createSprite("attackmagicleft"); return spr_master; }
					}
					else {
						if(playerSide.equals("right") && exitAnimationFrame > 0) { spr_master = atlas_schoolpride_f.createSprite("attackhitright"); return spr_master; }
						if(playerSide.equals("left") && exitAnimationFrame > 0) { spr_master = atlas_schoolpride_f.createSprite("attackhitleft"); return spr_master; }
					}				
				}
				
				//Stop
				if(walk.equals("stop")) {
					frame = 1;
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 1) { spr_master = atlas_schoolpride_f.createSprite("front1"); return spr_master; }
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 1) { spr_master = atlas_schoolpride_f.createSprite("back1"); return spr_master; }
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 1) { spr_master = atlas_schoolpride_f.createSprite("left1"); return spr_master; }
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 1) { spr_master = atlas_schoolpride_f.createSprite("right1"); return spr_master; }
				}
				
				//Walk Front
				if(walk.equals("walk")) {
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 1) { spr_master = atlas_schoolpride_f.createSprite("front1"); return spr_master; }
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 2) { spr_master = atlas_schoolpride_f.createSprite("front2"); return spr_master; }
					if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 3) { spr_master = atlas_schoolpride_f.createSprite("front3"); return spr_master; }
					
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 1) { spr_master = atlas_schoolpride_f.createSprite("back1"); return spr_master; }
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 2) { spr_master = atlas_schoolpride_f.createSprite("back2"); return spr_master; }
					if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 3) { spr_master = atlas_schoolpride_f.createSprite("back3"); return spr_master; }
					
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 1) { spr_master = atlas_schoolpride_f.createSprite("left1"); return spr_master; }
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 2) { spr_master = atlas_schoolpride_f.createSprite("left2"); return spr_master; }
					if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 3) { spr_master = atlas_schoolpride_f.createSprite("left3"); return spr_master; }
					
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 1) { spr_master = atlas_schoolpride_f.createSprite("right1"); return spr_master; }
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 2) { spr_master = atlas_schoolpride_f.createSprite("right2"); return spr_master; }
					if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 3) { spr_master = atlas_schoolpride_f.createSprite("right3"); return spr_master; }
				}				
			}
		}
		
		return spr_master;			
	}
	
	public Sprite MovPlayerHat(String hat,String sex, String state, String gameState, String walk) {
			
			if(gameState.equals("Menu-Status")) {
				if(sex.equals("M")) { spr_master = atlas_hat.createSprite(hat + "_front"); spr_master.setSize(13, 21); spr_master.setPosition(playerPosX + 4, playerPosY + 21f); return spr_master; }		
				if(sex.equals("F")) { spr_master = atlas_hat.createSprite(hat + "_front"); spr_master.setSize(13, 21); spr_master.setPosition(playerPosX + 4, playerPosY + 21f); return spr_master; }
			}
		
			
			if(isCasting && playerSide.equals("right")) {
				spr_master = atlas_hat.createSprite(hat + "_front"); spr_master.setSize(13, 21); spr_master.setPosition(playerPosX + 4, playerPosY + 21f); return spr_master;
			}
			
			if(isCasting && playerSide.equals("right")) {
				spr_master = atlas_hat.createSprite(hat + "_front"); spr_master.setSize(13, 21); spr_master.setPosition(playerPosX + 4, playerPosY + 21f); return spr_master;
			}
			
			if(exitAnimationFrame > 0 ) {
				if(playerSide.equals("right") && playerInfo.inBattle_A.equals("yes") && exitAnimationFrame > 0) {
					spr_master = atlas_hat.createSprite(hat + "_front"); spr_master.setSize(13, 21); spr_master.setPosition(playerPosX + 4, playerPosY + 21f); return spr_master;		
				}
				
				if(playerSide.equals("left") && playerInfo.inBattle_A.equals("yes") && exitAnimationFrame > 0) {			
					spr_master = atlas_hat.createSprite(hat + "_front"); spr_master.setSize(13, 21); spr_master.setPosition(playerPosX + 4, playerPosY + 21f); return spr_master;
				}
			}
			
			if(playerSide.equals("right") && playerInfo.inBattle_A.equals("yes") && walk.equals("stop")) {
				spr_master = atlas_hat.createSprite(hat + "_front"); spr_master.setSize(13, 21); spr_master.setPosition(playerPosX + 4, playerPosY + 21f); return spr_master;	
			}
			
			if(playerSide.equals("left") && playerInfo.inBattle_A.equals("yes") && walk.equals("stop")) {			
				spr_master = atlas_hat.createSprite(hat + "_front"); spr_master.setSize(13, 21); spr_master.setPosition(playerPosX + 4, playerPosY + 21f); return spr_master;
			}
			
			if((state.equals("front") || state.equals("front-left") || state.equals("front-right"))  && frame == 1) { spr_master = atlas_hat.createSprite(hat + "_front"); spr_master.setSize(13, 21); spr_master.setPosition(playerPosX + 4, playerPosY + 21f); return spr_master; }
			if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 1) { spr_master = atlas_hat.createSprite(hat + "_back"); spr_master.setSize(11, 21); spr_master.setPosition(playerPosX + 5.4f, playerPosY + 21); return spr_master; }
			if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 1) { spr_master = atlas_hat.createSprite(hat + "_left"); spr_master.setSize(11, 21); spr_master.setPosition(playerPosX + 5f, playerPosY + 21); return spr_master; }
			if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 1) { spr_master = atlas_hat.createSprite(hat + "_right"); spr_master.setSize(11, 21); spr_master.setPosition(playerPosX + 6f, playerPosY + 21); return spr_master; }
			
			if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 2) { spr_master = atlas_hat.createSprite(hat + "_front"); spr_master.setSize(13, 21); spr_master.setPosition(playerPosX + 3.8f, playerPosY + 21f); return spr_master; }
			if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 2) { spr_master = atlas_hat.createSprite(hat + "_back"); spr_master.setSize(11, 21); spr_master.setPosition(playerPosX + 5.6f, playerPosY + 21); return spr_master; }
			if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 2) { spr_master = atlas_hat.createSprite(hat + "_left"); spr_master.setSize(11, 21); spr_master.setPosition(playerPosX + 4.5f, playerPosY + 21); return spr_master; }
			if((state.equals("right")  || state.equals("right-front") || state.equals("right-back")) && frame == 2) { spr_master = atlas_hat.createSprite(hat + "_right"); spr_master.setSize(11, 21); spr_master.setPosition(playerPosX + 5.5f, playerPosY + 21); return spr_master; }
			
			if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 3) { spr_master = atlas_hat.createSprite(hat + "_front"); spr_master.setSize(13, 21); spr_master.setPosition(playerPosX + 4, playerPosY + 21f); return spr_master; }
			if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 3) { spr_master = atlas_hat.createSprite(hat + "_back"); spr_master.setSize(11, 21); spr_master.setPosition(playerPosX + 5.4f, playerPosY + 21); return spr_master; }
			if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 3) { spr_master = atlas_hat.createSprite(hat + "_left"); spr_master.setSize(11, 21); spr_master.setPosition(playerPosX + 5f, playerPosY + 21); return spr_master; }
			if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 3) { spr_master = atlas_hat.createSprite(hat + "_right"); spr_master.setSize(11, 21); spr_master.setPosition(playerPosX + 6f, playerPosY + 21); return spr_master; }
	
		return spr_master;
	}
	
	public Sprite MovPlayerHair(String hair,String sex, String state, String gameState, String walk) {
		
		if(gameState.equals("Menu-Status")) {
			if(sex.equals("M")) { spr_master = atlas_hairs.createSprite(hair); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7, playerPosY + 23f); return spr_master; }		
			if(sex.equals("F")) { spr_master = atlas_hairs.createSprite(hair + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7, playerPosY + 23f); return spr_master; }
		}
			
		if(sex.equals("M")) {	
			
			if(isCasting && playerSide.equals("right")) {
				spr_master = atlas_hairs.createSprite(hair + "attack_right"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7, playerPosY + 23f); return spr_master;
			}
			
			if(isCasting && playerSide.equals("right")) {
				spr_master = atlas_hairs.createSprite(hair + "attack_left"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7, playerPosY + 23f); return spr_master;
			}
			
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
			
			if(isCasting && playerSide.equals("right")) {
				spr_master = atlas_hairs.createSprite(hair + "attack_f_right"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7, playerPosY + 23f); return spr_master;
			}
			
			if(isCasting && playerSide.equals("right")) {
				spr_master = atlas_hairs.createSprite(hair + "attack_f_left"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7, playerPosY + 23f); return spr_master;
			}
			
			if(exitAnimationFrame > 0 ) {
				if(playerSide.equals("right") && playerInfo.inBattle_A.equals("yes") && exitAnimationFrame > 0) {
					spr_master = atlas_hairs.createSprite(hair + "attack_f_right"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7, playerPosY + 23f); return spr_master;		
				}
				
				if(playerSide.equals("left") && playerInfo.inBattle_A.equals("yes") && exitAnimationFrame > 0) {			
					spr_master = atlas_hairs.createSprite(hair + "attack_f_left"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7, playerPosY + 23f); return spr_master;
				}
			}
			
			if(playerSide.equals("right") && playerInfo.inBattle_A.equals("yes") && walk.equals("stop")) {
				spr_master = atlas_hairs.createSprite(hair + "battle_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7, playerPosY + 23.5f); return spr_master;		
			}
			
			if(playerSide.equals("left") && playerInfo.inBattle_A.equals("yes") && walk.equals("stop")) {			
				spr_master = atlas_hairs.createSprite(hair + "battle_f_left"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.5f, playerPosY + 23.5f); return spr_master;
			}
			
			if((state.equals("front") || state.equals("front-left") || state.equals("front-right"))  && frame == 1) { spr_master = atlas_hairs.createSprite(hair + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7, playerPosY + 23f); return spr_master; }
			if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 1) { spr_master = atlas_hairs.createSprite(hair + "up" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.3f, playerPosY + 22.7f); return spr_master; }
			if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 1) { spr_master = atlas_hairs.createSprite(hair + "left" + "_f"); spr_master.setSize(8, 10); spr_master.setPosition(playerPosX + 6.5f, playerPosY + 23); return spr_master; }
			if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 1) { spr_master = atlas_hairs.createSprite(hair + "right" + "_f"); spr_master.setSize(8, 10); spr_master.setPosition(playerPosX + 7.1f, playerPosY + 23); return spr_master; }
			
			if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.2f, playerPosY + 22.7f); return spr_master; }
			if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair + "up" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.3f, playerPosY + 22.7f); return spr_master; }
			if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair + "left" + "_f"); spr_master.setSize(8, 10); spr_master.setPosition(playerPosX + 6.8f, playerPosY + 22.3f); return spr_master; }
			if((state.equals("right")  || state.equals("right-front") || state.equals("right-back")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair + "right" + "_f"); spr_master.setSize(8, 10); spr_master.setPosition(playerPosX + 7.1f, playerPosY + 22.3f); return spr_master; }
			
			if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.2f, playerPosY + 22.4f); return spr_master; }
			if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair + "up" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(playerPosX + 7.3f, playerPosY + 22.7f); return spr_master; }
			if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair + "left" + "_f"); spr_master.setSize(8, 10); spr_master.setPosition(playerPosX + 7.2f, playerPosY + 22.8f); return spr_master; }
			if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair + "right" + "_f"); spr_master.setSize(8, 10); spr_master.setPosition(playerPosX + 7.1f, playerPosY + 22.8f); return spr_master; }		
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
		
		//Teste
		int playerMoney = Integer.parseInt(playerInfo.money_A);
		playerMoney = playerMoney + 100;
		playerInfo.money_A = String.valueOf(playerMoney);
		
		if(shopName.equals("RefriShop")) {
			//HPCAN
			if(numItem == 1) {
				if(playerMoney >= 15) {
					playerMoney = playerMoney - 15;
					playerInfo.money_A = String.valueOf(playerMoney);
					AddItemBag("HPCAN");
					return "Comprado";
				} 
				else {
					return "Dinheiro Insuficiente";
				}
			}
			//MPCAN
			if(numItem == 2) {
				if(playerMoney >= 35) {
					playerMoney = playerMoney - 35;
					playerInfo.money_A = String.valueOf(playerMoney);
					AddItemBag("MPCAN");
					return "Comprado";
				} 
				else {
					return "Dinheiro Insuficiente";
				}
			}
			//STCAN
			if(numItem == 3) {
				if(playerMoney >= 20) {
					playerMoney = playerMoney - 20;
					playerInfo.money_A = String.valueOf(playerMoney);
					AddItemBag("STCAN");
					return "Comprado";
				} 
				else {
					return "Dinheiro Insuficiente";
				}
			}
			//CHIPZ
			if(numItem == 4) {
				if(playerMoney >= 50) {
					playerMoney = playerMoney - 50;
					playerInfo.money_A = String.valueOf(playerMoney);
					AddItemBag("CHIPZ");
					return "Comprado";
				} 
				else {
					return "Dinheiro Insuficiente";
				}
			}
		}
		
		if(shopName.equals("305")) {
			//DOUBLEEDGEKNIFE
			if(numItem == 1) {
				if(playerMoney >= 30) {
					playerMoney = playerMoney - 30;
					playerInfo.money_A = String.valueOf(playerMoney);
					AddItemBag("DOUBLEEDGEKNIFE"); //doubleedgeknife
					return "Comprado";
				} 
				else {
					return "Dinheiro Insuficiente";
				}
			}
			//SABERSWORD
			if(numItem == 2) {
				if(playerMoney >= 50) {
					playerMoney = playerMoney - 50;
					playerInfo.money_A = String.valueOf(playerMoney);
					AddItemBag("SABERSWORD"); //sabersword
					return "Comprado";
				} 
				else {
					return "Dinheiro Insuficiente";
				}
			}
			//REVOLVERPISTOL
			if(numItem == 3) {
				if(playerMoney >= 50) {
					playerMoney = playerMoney - 50;
					playerInfo.money_A = String.valueOf(playerMoney);
					AddItemBag("REVOLVERPISTOL"); //revolverpistol
					return "Comprado";
				} 
				else {
					return "Dinheiro Insuficiente";
				}
			}
			//GLOOMROD
			if(numItem == 4) {
				if(playerMoney >= 50) {
					playerMoney = playerMoney - 50;
					playerInfo.money_A = String.valueOf(playerMoney);
					AddItemBag("GLOOMROD"); //gloomrod
					return "Comprado";
				} 
				else {
					return "Dinheiro Insuficiente";
				}
			}
			
			//HAMMERAXE
			if(numItem == 5) {
				if(playerMoney >= 30) {
					playerMoney = playerMoney - 30;
					playerInfo.money_A = String.valueOf(playerMoney);
					AddItemBag("HAMMERAXE"); //hammeraxe
					return "Comprado";
				} 
				else {
					return "Dinheiro Insuficiente";
				}
			}
			
			//EDGEDAGGER
			if(numItem == 6) {
				if(playerMoney >= 30) {
					playerMoney = playerMoney - 30;
					playerInfo.money_A = String.valueOf(playerMoney);
					AddItemBag("EDGEDAGGER"); //edgedagger
					return "Comprado";
				} 
				else {
					return "Dinheiro Insuficiente";
				}
			}
			
			//HATMAGICIAN
			if(numItem == 7) {
				if(playerMoney >= 80) {
					playerMoney = playerMoney - 80;
					playerInfo.money_A = String.valueOf(playerMoney);
					AddItemBag("HATMAGICIAN"); //hatmagician
					return "Comprado";
				} 
				else {
					return "Dinheiro Insuficiente";
				}
			}
			
			//HATSLIME
			if(numItem == 8) {
				if(playerMoney >= 2) {
					playerMoney = playerMoney - 2;
					playerInfo.money_A = String.valueOf(playerMoney);
					AddItemBag("HATSLIME"); //slimehat
					return "Comprado";
				} 
				else {
					return "Dinheiro Insuficiente";
				}
			}
			
			//SPORTSET
			if(numItem == 9) {
				if(playerMoney >= 60) {
					playerMoney = playerMoney - 60;
					playerInfo.money_A = String.valueOf(playerMoney);
					AddItemBag("SPORTSET"); //sportset
					return "Comprado";
				} 
				else {
					return "Dinheiro Insuficiente";
				}
			}
			
			//SCHOOLPRIDESETF
			if(numItem == 10) {
				if(playerMoney >= 60) {
					playerMoney = playerMoney - 60;
					playerInfo.money_A = String.valueOf(playerMoney);
					AddItemBag("SCHOOLPRIDESETF"); //schoolpridesetF
					return "Comprado";
				} 
				else {
					return "Dinheiro Insuficiente";
				}
			}
			
			//HATBUNNY
			if(numItem == 11) {
				if(playerMoney >= 50) {
					playerMoney = playerMoney - 50;
					playerInfo.money_A = String.valueOf(playerMoney);
					AddItemBag("HATBUNNY"); //bunnyhat
					return "Comprado";
				} 
				else {
					return "Dinheiro Insuficiente";
				}
			}
			
			//HATSANTA
			if(numItem == 12) {
				if(playerMoney >= 50) {
					playerMoney = playerMoney - 50;
					playerInfo.money_A = String.valueOf(playerMoney);
					AddItemBag("HATSANTA"); //santahat
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
	
	public void UnequipHat() {
		String nameHat = playerInfo.hat_A;
		if(nameHat.equals("none")) { return; }
		playerInfo.hat_A = "none";
		AddItemBag(nameHat);
	}
	
	public Sprite ShowEquippedItens(int num, float coordsX, float coordsY) {
		
		//Weapon
		if(num == 1) {
			if(playerInfo.weapon_A.equals("BASICKNIFE")) { 
				spr_master = atlas_itens.createSprite("basicknife"); 
				spr_master.setPosition(coordsX -1.5f, coordsY + 11f); 
				spr_master.setSize(9, 14); 
				return spr_master; 
			}
			if(playerInfo.weapon_A.equals("DOUBLEEDGEKNIFE")) { 
				spr_master = atlas_itens.createSprite("doubleedgeknife"); 
				spr_master.setPosition(coordsX -1.5f, coordsY + 11f); 
				spr_master.setSize(9, 14); 
				return spr_master; 
			}
			if(playerInfo.weapon_A.equals("SABERSWORD")) { 
				spr_master = atlas_itens.createSprite("sabersword"); 
				spr_master.setPosition(coordsX -1.5f, coordsY + 11f); 
				spr_master.setSize(9, 14); 
				return spr_master; 
			}
			if(playerInfo.weapon_A.equals("REVOLVERPISTOL")) { 
				spr_master = atlas_itens.createSprite("revolverpistol"); 
				spr_master.setPosition(coordsX -1.5f, coordsY + 11f); 
				spr_master.setSize(9, 14); 
				return spr_master; 
			}
			if(playerInfo.weapon_A.equals("GLOOMROD")) { 
				spr_master = atlas_itens.createSprite("gloomrod"); 
				spr_master.setPosition(coordsX -1.5f, coordsY + 11f); 
				spr_master.setSize(9, 14); 
				return spr_master; 
			}
			
			if(playerInfo.weapon_A.equals("HAMMERAXE")) { 
				spr_master = atlas_itens.createSprite("hammeraxe"); 
				spr_master.setPosition(coordsX -1.5f, coordsY + 11f); 
				spr_master.setSize(9, 14); 
				return spr_master; 
			}		
			if(playerInfo.weapon_A.equals("EDGEDAGGER")) { 
				spr_master = atlas_itens.createSprite("edgedagger"); 
				spr_master.setPosition(coordsX -1.5f, coordsY + 11f); 
				spr_master.setSize(9, 14); 
				return spr_master; 
			}
		}
		
		//Set
		if(num == 2) {
			//Male
			if(playerInfo.set_A.equals("BASICSET")) {  
				spr_master = atlas_itens.createSprite("basicset"); 
				spr_master.setPosition(coordsX -1.5f, coordsY + 26.8f); 
				spr_master.setSize(9, 14); 
				return spr_master; 
			}
			if(playerInfo.set_A.equals("SPORTSET")) {  
				spr_master = atlas_itens.createSprite("sportset"); 
				spr_master.setPosition(coordsX -1.5f, coordsY + 26.8f); 
				spr_master.setSize(9, 14); 
				return spr_master; 
			}
			
			//Female
			if(playerInfo.set_A.equals("BASICSETF")) {  
				spr_master = atlas_itens.createSprite("basicsetF"); 
				spr_master.setPosition(coordsX -1.5f, coordsY + 26.8f); 
				spr_master.setSize(9, 14); 
				return spr_master; 
			}
			
			if(playerInfo.set_A.equals("SCHOOLPRIDESETF")) {  
				spr_master = atlas_itens.createSprite("schoolpridesetF"); 
				spr_master.setPosition(coordsX -1.5f, coordsY + 26.8f); 
				spr_master.setSize(9, 14); 
				return spr_master; 
			}
		}
		
		//Hat
		if(num == 3) {
			if(playerInfo.hat_A.equals("none")) {  
				return null; 
			}
			
			if(playerInfo.hat_A.equals("HATMAGICIAN")) { 
				spr_master = atlas_itens.createSprite("hatmagician");
				spr_master.setPosition(coordsX -1.5f, coordsY + 42.7f); 
				spr_master.setSize(9, 14); 
				return spr_master;
			}
			if(playerInfo.hat_A.equals("HATSLIME")) { 
				spr_master = atlas_itens.createSprite("hatslime");
				spr_master.setPosition(coordsX -1.5f, coordsY + 42.7f); 
				spr_master.setSize(9, 14); 
				return spr_master;
			}
			
			if(playerInfo.hat_A.equals("HATBUNNY")) { 
				spr_master = atlas_itens.createSprite("hatbunny");
				spr_master.setPosition(coordsX -1.5f, coordsY + 42.7f); 
				spr_master.setSize(9, 14); 
				return spr_master;
			}
			if(playerInfo.hat_A.equals("HATSANTA")) { 
				spr_master = atlas_itens.createSprite("hatsanta");
				spr_master.setPosition(coordsX -1.5f, coordsY + 42.7f); 
				spr_master.setSize(9, 14); 
				return spr_master;
			}
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
		
		if(item.equals("BASICSET")) { spr_master = atlas_itens.createSprite("basicset"); return spr_master; } 
		if(item.equals("BASICSETF")) { spr_master = atlas_itens.createSprite("basicsetF"); return spr_master; }
		if(item.equals("BASICKNIFE")) { spr_master = atlas_itens.createSprite("basicknife"); return spr_master; }
		if(item.equals("DOUBLEEDGEKNIFE")) { spr_master = atlas_itens.createSprite("doubleedgeknife"); return spr_master; }
		if(item.equals("SABERSWORD")) { spr_master = atlas_itens.createSprite("sabersword"); return spr_master; }
		if(item.equals("REVOLVERPISTOL")) { spr_master = atlas_itens.createSprite("revolverpistol"); return spr_master; }
		if(item.equals("GLOOMROD")) { spr_master = atlas_itens.createSprite("gloomrod"); return spr_master; }
		if(item.equals("HAMMERAXE")) { spr_master = atlas_itens.createSprite("hammeraxe"); return spr_master; }
		if(item.equals("EDGEDAGGER")) { spr_master = atlas_itens.createSprite("edgedagger"); return spr_master; }
		if(item.equals("HATMAGICIAN")) { spr_master = atlas_itens.createSprite("hatmagician"); return spr_master; }
		if(item.equals("HATSLIME")) { spr_master = atlas_itens.createSprite("hatslime"); return spr_master; }
		if(item.equals("SPORTSET")) { spr_master = atlas_itens.createSprite("sportset"); return spr_master; }
		if(item.equals("SCHOOLPRIDESETF")) { spr_master = atlas_itens.createSprite("schoolpridesetF"); return spr_master; }
		if(item.equals("HATBUNNY")) { spr_master = atlas_itens.createSprite("hatbunny"); return spr_master; }
		if(item.equals("HATSANTA")) { spr_master = atlas_itens.createSprite("hatsanta"); return spr_master; }
		
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
		boolean equipable = false;
		
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
				equipable = false;
			}
			
			if(itemName.equals("MPCAN")) {
				playerMP = Integer.parseInt(playerInfo.mp_A);
				playerMPMax = Integer.parseInt(playerInfo.maxmp_A);
				
				playerMP = playerMP + 25;
				
				if(playerMP > playerMPMax) { playerMP = playerMPMax; }
				playerInfo.mp_A = String.valueOf(playerMP);
				equipable = false;
			}
			
			if(itemName.equals("STCAN")) {
				playerStamina = Integer.parseInt(playerInfo.stamina_A); 
				playerStaminaMax = Integer.parseInt(playerInfo.staminamax_A);
				
				playerStamina = playerStamina + 5;
				
				if(playerStamina > playerStaminaMax) { playerStamina = playerStaminaMax; }
				playerInfo.stamina_A = String.valueOf(playerStamina);
				equipable = false;
			}
			
			if(itemName.equals("CHIPZ")) {
				playerStamina = Integer.parseInt(playerInfo.stamina_A); 
				playerStaminaMax = Integer.parseInt(playerInfo.staminamax_A);
				
				playerStamina = playerStamina + 23;
				
				if(playerStamina > playerStaminaMax) { playerStamina = playerStaminaMax; }
				playerInfo.stamina_A = String.valueOf(playerStamina);
				equipable = false;
			}
			
			if(itemName.equals("BASICKNIFE")) {	
				if(itemName.equals(playerInfo.weapon_A)) { return; }
				if(!playerInfo.job_A.equals("Novice")) { return; }
					AddItemBag(playerInfo.weapon_A);
					playerInfo.weapon_A = itemName;
					lstItem = playerInfo.itens_A.split("-");			
				equipable = true;
			}
			
			if(itemName.equals("DOUBLEEDGEKNIFE")) {
				if(itemName.equals(playerInfo.weapon_A)) { return; }
				if(!playerInfo.job_A.equals("Novice")) { return; }
					AddItemBag(playerInfo.weapon_A);
					playerInfo.weapon_A = itemName;
					lstItem = playerInfo.itens_A.split("-");		
				equipable = true;
			}
			
			if(itemName.equals("SABERSWORD")) {	
				if(itemName.equals(playerInfo.weapon_A)) { return; }
				if(!playerInfo.job_A.equals("Swordman")) { return; }
					AddItemBag(playerInfo.weapon_A);
					playerInfo.weapon_A = itemName;
					lstItem = playerInfo.itens_A.split("-");			
				equipable = true;
			}
			
			if(itemName.equals("REVOLVERPISTOL")) {	
				if(itemName.equals(playerInfo.weapon_A)) { return; }
				if(!playerInfo.job_A.equals("Gunner")) { return; }
					AddItemBag(playerInfo.weapon_A);
					playerInfo.weapon_A = itemName;
					lstItem = playerInfo.itens_A.split("-");			
				equipable = true;
			}
			
			if(itemName.equals("GLOOMROD")) {	
				if(itemName.equals(playerInfo.weapon_A)) { return; }
				if(!(playerInfo.job_A.equals("Medic") || playerInfo.job_A.equals("Mage") )) { return; }
					AddItemBag(playerInfo.weapon_A);
					playerInfo.weapon_A = itemName;
					lstItem = playerInfo.itens_A.split("-");			
				equipable = true;
			}
			
			if(itemName.equals("HAMMERAXE")) {	
				if(itemName.equals(playerInfo.weapon_A)) { return; }
				if(!playerInfo.job_A.equals("Beater")) { return; }
					AddItemBag(playerInfo.weapon_A);
					playerInfo.weapon_A = itemName;
					lstItem = playerInfo.itens_A.split("-");			
				equipable = true;
			}
			
			if(itemName.equals("EDGEDAGGER")) {	
				if(itemName.equals(playerInfo.weapon_A)) { return; }
				if(!playerInfo.job_A.equals("Thief")) { return; }
					AddItemBag(playerInfo.weapon_A);
					playerInfo.weapon_A = itemName;
					lstItem = playerInfo.itens_A.split("-");			
				equipable = true;
			}
			
			if(itemName.equals("HATMAGICIAN")) {
				if(itemName.equals(playerInfo.hat_A)) { return; }
				if(!playerInfo.hat_A.equals("none")) { AddItemBag(playerInfo.hat_A); }
				playerInfo.hat_A = itemName;
				lstItem = playerInfo.itens_A.split("-");
				equipable = true;
			}
			if(itemName.equals("HATSLIME")) {	
				if(itemName.equals(playerInfo.hat_A)) { return; }
				if(!playerInfo.hat_A.equals("none")) { AddItemBag(playerInfo.hat_A); }
				playerInfo.hat_A = itemName;
				lstItem = playerInfo.itens_A.split("-");
				equipable = true;
			}
			if(itemName.equals("HATBUNNY")) {	
				if(itemName.equals(playerInfo.hat_A)) { return; }
				if(!playerInfo.hat_A.equals("none")) { AddItemBag(playerInfo.hat_A); }
				playerInfo.hat_A = itemName;
				lstItem = playerInfo.itens_A.split("-");
				equipable = true;
			}
			if(itemName.equals("HATSANTA")) {
				if(itemName.equals(playerInfo.hat_A)) { return; }
				if(!playerInfo.hat_A.equals("none")) { AddItemBag(playerInfo.hat_A); }
				playerInfo.hat_A = itemName;
				lstItem = playerInfo.itens_A.split("-");
				equipable = true;
			}
			
			if(itemName.equals("BASICSET")) {
				if(playerInfo.sex_A.equals("F")) { return; }
				if(itemName.equals(playerInfo.set_A)) { return; }
				if(!playerInfo.set_A.equals("none")) { AddItemBag(playerInfo.set_A); }
				playerInfo.set_A = itemName;
				lstItem = playerInfo.itens_A.split("-");
				equipable = true;  
			}
			
			if(itemName.equals("BASICSETF")) {
				if(playerInfo.sex_A.equals("M")) { return; }
				if(itemName.equals(playerInfo.set_A)) { return; }
				if(!playerInfo.set_A.equals("none")) { AddItemBag(playerInfo.set_A); }
				playerInfo.set_A = itemName;
				lstItem = playerInfo.itens_A.split("-");
				equipable = true;  
			}
			
			if(itemName.equals("SPORTSET")) {
				if(playerInfo.sex_A.equals("F")) { return; }
				if(itemName.equals(playerInfo.set_A)) { return; }
				if(!playerInfo.set_A.equals("none")) { AddItemBag(playerInfo.set_A); }
				playerInfo.set_A = itemName;
				lstItem = playerInfo.itens_A.split("-");
				equipable = true;  
			}
			
			if(itemName.equals("SCHOOLPRIDESETF")) {
				if(playerInfo.sex_A.equals("M")) { return; }
				if(itemName.equals(playerInfo.set_A)) { return; }
				if(!playerInfo.set_A.equals("none")) { AddItemBag(playerInfo.set_A); }
				playerInfo.set_A = itemName;
				lstItem = playerInfo.itens_A.split("-");
				equipable = true;  
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
				if(lstItem[i].contains("[NONE]") && !exist) {
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
		String inBattle = playerOnline.inBattle_A;
		
		//Male
		if(sex.equals("M")) {
			if(set.equals("BASICSET")) {
				
				//Battle
				if(inBattle.equals("yes") && walk.equals("stop")) {
					if(state.equals("front") && frame == 1) { spr_master = atlas_basicset_m.createSprite("battle1right"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if(state.equals("right") && frame == 1) { spr_master = atlas_basicset_m.createSprite("battle1right"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if(state.equals("left") && frame == 1) { spr_master = atlas_basicset_m.createSprite("battle1left"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if(state.equals("back") && frame == 1) { spr_master = atlas_basicset_m.createSprite("battle1left"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					
					if(state.equals("front") && frame == 2) { spr_master = atlas_basicset_m.createSprite("battle2right"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if(state.equals("right") && frame == 2) { spr_master = atlas_basicset_m.createSprite("battle2right"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if(state.equals("left") && frame == 2) { spr_master = atlas_basicset_m.createSprite("battle2left"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if(state.equals("back") && frame == 2) { spr_master = atlas_basicset_m.createSprite("battle2left"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					
					if(state.equals("front") && frame == 3) { spr_master = atlas_basicset_m.createSprite("battle3right"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if(state.equals("right") && frame == 3) { spr_master = atlas_basicset_m.createSprite("battle3right"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if(state.equals("left") && frame == 3) { spr_master = atlas_basicset_m.createSprite("battle3left"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if(state.equals("back") && frame == 3) { spr_master = atlas_basicset_m.createSprite("battle3left"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					
				}
				
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
				if(set.equals("BASICSETF")) {
					
				//Battle
				if(inBattle.equals("yes") && walk.equals("stop")) {
					if(playerSide.equals("right") && frame == 1) { spr_master = atlas_basicset_f.createSprite("battle1right"); return spr_master; }
					if(playerSide.equals("left") && frame == 1) { spr_master = atlas_basicset_f.createSprite("battle1left"); return spr_master; }
					
					if(playerSide.equals("right") && frame == 2) { spr_master = atlas_basicset_f.createSprite("battle2right"); return spr_master; }
					if(playerSide.equals("left") && frame == 2) { spr_master = atlas_basicset_f.createSprite("battle2left"); return spr_master; }
					
					if(playerSide.equals("right") && frame == 3) { spr_master = atlas_basicset_f.createSprite("battle3right"); return spr_master; }
					if(playerSide.equals("left") && frame == 3) { spr_master = atlas_basicset_f.createSprite("battle3left"); return spr_master; }
					
				}
					
				//Stop
				if(walk.equals("stop")) {
					if(state.equals("front") || state.equals("front-left") || state.equals("front-right")) { spr_master = atlas_basicset_f.createSprite("front1"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if(state.equals("back") || state.equals("back-left") || state.equals("back-right")) { spr_master = atlas_basicset_f.createSprite("back1"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if(state.equals("left") || state.equals("left-front") || state.equals("left-back")) { spr_master = atlas_basicset_f.createSprite("left1"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
					if(state.equals("right") || state.equals("right-front") || state.equals("right-back")) { spr_master = atlas_basicset_f.createSprite("right1"); spr_master.setPosition(coordsX, coordsY); return spr_master; }
				}
				
				//Walk Front
				if(walk.equals("walk")) {
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
		String walk = playerOnline.walk_A;
		float coordsX = Float.parseFloat(playerOnline.coordX_A);
		float coordsY = Float.parseFloat(playerOnline.coordY_A);
		String inBattle = playerOnline.inBattle_A;
		
		if(sex.equals("M")) {	
			
			if(state.equals("right") && inBattle.equals("yes") && walk.equals("stop")) {
				spr_master = atlas_hairs.createSprite(hair + "battle"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7, coordsY + 23.5f); return spr_master;		
			}
			
			if(state.equals("left") && inBattle.equals("yes") && walk.equals("stop")) {			
				spr_master = atlas_hairs.createSprite(hair + "battle_left"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7, coordsY + 23.5f); return spr_master;
			}
			
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
			
			if((state.equals("front") || state.equals("front-left") || state.equals("front-right"))  && frame == 1) { spr_master = atlas_hairs.createSprite(hair + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7, coordsY + 23f); return spr_master; }
			if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 1) { spr_master = atlas_hairs.createSprite(hair + "up" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7.3f, coordsY + 22.7f); return spr_master; }
			if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 1) { spr_master = atlas_hairs.createSprite(hair + "left" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7.5f, coordsY + 23); return spr_master; }
			if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 1) { spr_master = atlas_hairs.createSprite(hair + "right" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7.8f, coordsY + 23); return spr_master; }
			
			if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7.2f, coordsY + 22.7f); return spr_master; }
			if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair + "up" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7.3f, coordsY + 22.7f); return spr_master; }
			if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair + "left" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 6.8f, coordsY + 22.3f); return spr_master; }
			if((state.equals("right")  || state.equals("right-front") || state.equals("right-back")) && frame == 2) { spr_master = atlas_hairs.createSprite(hair + "right" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 6.5f, coordsY + 22.3f); return spr_master; }
			
			if((state.equals("front") || state.equals("front-left") || state.equals("front-right")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7.2f, coordsY + 22.4f); return spr_master; }
			if((state.equals("back") || state.equals("back-left") || state.equals("back-right")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair + "up" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7.3f, coordsY + 22.7f); return spr_master; }
			if((state.equals("left") || state.equals("left-front") || state.equals("left-back")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair + "left" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7.2f, coordsY + 22.8f); return spr_master; }
			if((state.equals("right") || state.equals("right-front") || state.equals("right-back")) && frame == 3) { spr_master = atlas_hairs.createSprite(hair + "right" + "_f"); spr_master.setSize(7, 10); spr_master.setPosition(coordsX + 7.4f, coordsY + 22.8f); return spr_master; }
		}
		
		return spr_master;
	}
	
	public void MetaInfoOnline() {
		countPartyPlayers = 0;
		cdExpShared--;
		cdHealShared--;
		if(cdExpShared <= 0) { cdExpShared = 0; }
		if(cdHealShared <= 0) { cdHealShared = 0; }
		
		CleanBuffEffects();
		
		//Check All Shared Meta Infos
		for(int i = 0; i < lstPlayersOnline.size(); i++) {
			
			if(!playerInfo.party_A.equals("None")) {
				
				if(lstPlayersOnline.get(i).party_A.equals(playerInfo.party_A) && !lstPlayersOnline.get(i).accountID.equals(playerInfo.accountID)) {
					countPartyPlayers++;
					
					//Check ExpShared
					if(cdExpShared == 0 && expsharedReceive != 0) {
						GiveExp(null,"partyshared",expsharedReceive);
						expsharedReceive = 0;
					}
					
					if(!lstPlayersOnline.get(i).expshared_A.equals("0") && cdExpShared == 0) {
						expsharedReceive = Integer.parseInt(lstPlayersOnline.get(i).expshared_A);
						cdExpShared = 100;
					}
					
					//Check Heal
					healOnline = lstPlayersOnline.get(i).heal_A;
					if(!healOnline.equals("0") && cdHealShared == 0) {     
						auxShared = Integer.parseInt(lstPlayersOnline.get(i).heal_A);
						playerHP = Integer.parseInt(playerInfo.hp_A);
						playerHPMax = Integer.parseInt(playerInfo.maxhp_A);
						playerHP = playerHP + auxShared;
						if(playerHP > playerHPMax) { playerHP = playerHPMax; }
						playerInfo.hp_A = String.valueOf(playerHP);
						cdHealShared = 100;
					}	
							
					//Check Buffs
					//AtkBoost
					if(lstPlayersOnline.get(i).buffsA_A.contains("atkboost") && playerInfo.buffsA_A.contains("none")) {
						if(!(playerInfo.buffsA_A.contains("atkboost") || playerInfo.buffsA_A.contains("atkboost") || playerInfo.buffsA_A.contains("atkboost"))) {
							playerInfo.buffsA_A = lstPlayersOnline.get(i).buffsA_A;
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = playerAtk + 30;
							playerInfo.atk_A = String.valueOf(playerAtk);
						}
					}
					if(lstPlayersOnline.get(i).buffsB_A.contains("atkboost") && playerInfo.buffsB_A.contains("none")) {
						if(!(playerInfo.buffsA_A.contains("atkboost") || playerInfo.buffsA_A.contains("atkboost") || playerInfo.buffsA_A.contains("atkboost"))) {
							playerInfo.buffsB_A = lstPlayersOnline.get(i).buffsB_A;
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = playerAtk + 30;
							playerInfo.atk_A = String.valueOf(playerAtk);
						}
					}
					if(lstPlayersOnline.get(i).buffsC_A.contains("atkboost") && playerInfo.buffsC_A.contains("none")) {
						if(!(playerInfo.buffsA_A.contains("atkboost") || playerInfo.buffsA_A.contains("atkboost") || playerInfo.buffsA_A.contains("atkboost"))) {
							playerInfo.buffsC_A = lstPlayersOnline.get(i).buffsC_A;
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = playerAtk + 30;
							playerInfo.atk_A = String.valueOf(playerAtk);
						}
					}
					//DefBoost
					if(lstPlayersOnline.get(i).buffsA_A.contains("defboost") && playerInfo.buffsA_A.contains("none")) {
						if(!(playerInfo.buffsA_A.contains("defboost") || playerInfo.buffsA_A.contains("defboost") || playerInfo.buffsA_A.contains("defboost"))) {
							playerInfo.buffsA_A = lstPlayersOnline.get(i).buffsA_A;
							playerDef = Integer.parseInt(playerInfo.atk_A);
							playerDef = playerDef + 30;
							playerInfo.def_A = String.valueOf(playerDef);
						}
					}
					if(lstPlayersOnline.get(i).buffsB_A.contains("defboost") && playerInfo.buffsB_A.contains("none")) {
						if(!(playerInfo.buffsA_A.contains("defboost") || playerInfo.buffsA_A.contains("defboost") || playerInfo.buffsA_A.contains("defboost"))) {
							playerInfo.buffsB_A = lstPlayersOnline.get(i).buffsB_A;
							playerDef = Integer.parseInt(playerInfo.atk_A);
							playerDef = playerDef + 30;
							playerInfo.def_A = String.valueOf(playerDef);
						}
					}
					if(lstPlayersOnline.get(i).buffsC_A.contains("defboost") && playerInfo.buffsC_A.contains("none")) {
						if(!(playerInfo.buffsA_A.contains("defboost") || playerInfo.buffsA_A.contains("defboost") || playerInfo.buffsA_A.contains("defboost"))) {
							playerInfo.buffsC_A = lstPlayersOnline.get(i).buffsC_A;
							playerDef = Integer.parseInt(playerInfo.atk_A);
							playerDef = playerDef + 30;
							playerInfo.def_A = String.valueOf(playerDef);
						}
					}
					//Precision
					if(lstPlayersOnline.get(i).buffsA_A.contains("precision") && playerInfo.buffsA_A.contains("none")) {
						if(!(playerInfo.buffsA_A.contains("precision") || playerInfo.buffsA_A.contains("precision") || playerInfo.buffsA_A.contains("precision"))) {
							playerInfo.buffsA_A = lstPlayersOnline.get(i).buffsA_A;
						}
					}
					if(lstPlayersOnline.get(i).buffsB_A.contains("precision") && playerInfo.buffsB_A.contains("none")) {
						if(!(playerInfo.buffsA_A.contains("precision") || playerInfo.buffsA_A.contains("precision") || playerInfo.buffsA_A.contains("precision"))) {
						playerInfo.buffsB_A = lstPlayersOnline.get(i).buffsB_A;
						}
					}
					if(lstPlayersOnline.get(i).buffsC_A.contains("precision") && playerInfo.buffsC_A.contains("none")) {
						if(!(playerInfo.buffsA_A.contains("precision") || playerInfo.buffsA_A.contains("precision") || playerInfo.buffsA_A.contains("precision"))) {
						playerInfo.buffsC_A = lstPlayersOnline.get(i).buffsC_A;
						}
					}
					
					//Impound
					if(lstPlayersOnline.get(i).buffsA_A.contains("impound") && playerInfo.buffsA_A.contains("none")) {
						if(!(playerInfo.buffsA_A.contains("impound") || playerInfo.buffsA_A.contains("impound") || playerInfo.buffsA_A.contains("impound"))) {
							playerInfo.buffsA_A = lstPlayersOnline.get(i).buffsA_A;
							playerDef = Integer.parseInt(playerInfo.def_A);
							playerDef = playerDef * 2;
							playerInfo.def_A = String.valueOf(playerDef);
						}
					}
					if(lstPlayersOnline.get(i).buffsB_A.contains("impound") && playerInfo.buffsB_A.contains("none")) {
						if(!(playerInfo.buffsA_A.contains("impound") || playerInfo.buffsA_A.contains("impound") || playerInfo.buffsA_A.contains("impound"))) {
							playerInfo.buffsB_A = lstPlayersOnline.get(i).buffsB_A;
							playerDef = Integer.parseInt(playerInfo.def_A);
							playerDef = playerDef * 2;
							playerInfo.def_A = String.valueOf(playerDef);
						}
					}
					if(lstPlayersOnline.get(i).buffsC_A.contains("impound") && playerInfo.buffsC_A.contains("none")) {
						if(!(playerInfo.buffsA_A.contains("impound") || playerInfo.buffsA_A.contains("impound") || playerInfo.buffsA_A.contains("impound"))) {
							playerInfo.buffsC_A = lstPlayersOnline.get(i).buffsC_A;
							playerDef = Integer.parseInt(playerInfo.def_A);
							playerDef = playerDef * 2;
							playerInfo.def_A = String.valueOf(playerDef);
						}
					}
					
					//Regen
					if(lstPlayersOnline.get(i).buffsA_A.contains("regen") && playerInfo.buffsA_A.contains("none")) {
						if(!(playerInfo.buffsA_A.contains("regen") || playerInfo.buffsA_A.contains("regen") || playerInfo.buffsA_A.contains("regen"))) {
							playerInfo.buffsA_A = lstPlayersOnline.get(i).buffsA_A;
							recoverytimer = 500;
						}
					}
					if(lstPlayersOnline.get(i).buffsB_A.contains("regen") && playerInfo.buffsB_A.contains("none")) {
						if(!(playerInfo.buffsA_A.contains("regen") || playerInfo.buffsA_A.contains("regen") || playerInfo.buffsA_A.contains("regen"))) {
							playerInfo.buffsB_A = lstPlayersOnline.get(i).buffsB_A;
							recoverytimer = 500;
						}
					}
					if(lstPlayersOnline.get(i).buffsC_A.contains("regen") && playerInfo.buffsC_A.contains("none")) {
						if(!(playerInfo.buffsA_A.contains("regen") || playerInfo.buffsA_A.contains("regen") || playerInfo.buffsA_A.contains("regen"))) {
							playerInfo.buffsC_A = lstPlayersOnline.get(i).buffsC_A;
							recoverytimer = 500;
						}
					}				
				}
			}
		}	
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
			
			if(cdExpSharedSend > 0) {
				cdExpSharedSend--;
			}
			if(cdExpSharedSend <= 0) {
				cdExpSharedSend = 0;
				expsharedOnline = "0";
			}
			
			if(healStop > 0) {
				healStop--;
			}
			
			if(healStop <= 0) {
				healStop = 0;
				heal = "0";
			}
	        
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
	        data += "&" + URLEncoder.encode("lbuffA", "UTF-8") + "=" + URLEncoder.encode(playerInfo.buffsA_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lbuffB", "UTF-8") + "=" + URLEncoder.encode(playerInfo.buffsB_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lbuffC", "UTF-8") + "=" + URLEncoder.encode(playerInfo.buffsC_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lheal", "UTF-8") + "=" + URLEncoder.encode(heal, "UTF-8");
	        data += "&" + URLEncoder.encode("lexpshared", "UTF-8") + "=" + URLEncoder.encode(expsharedOnline, "UTF-8"); 
	        data += "&" + URLEncoder.encode("ldamage", "UTF-8") + "=" + URLEncoder.encode(damageOnline, "UTF-8");
	        data += "&" + URLEncoder.encode("lsyncPlayerMob", "UTF-8") + "=" + URLEncoder.encode(syncPlayerMob, "UTF-8");
	        data += "&" + URLEncoder.encode("lMobsHPSync", "UTF-8") + "=" + URLEncoder.encode(MobsHPSync, "UTF-8");
	              
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
		        
		        if(returnFromServer.contains("SYSTEMINSERT")) {
		        	line = "";
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
				
				String data = URLEncoder.encode("laccount", "UTF-8") + "=" + URLEncoder.encode(subdata, "UTF-8");
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
		        		OnlineRequest = "Concluido";
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
			    		OnlineRequest = "Concluido";
			    	}
				}
			    
			    wr.close();
			    rd.close();
			}
		}
		catch(Exception ex) {
			OnlineRequest = "Operacao Falhou";
		}
	}
	
	public String ResultOnlineRequest() {
		return OnlineRequest;
	}
	
	private void PlayersManagerOnline(String data) {
		//lstPlayersOnline	
		dataSplit = data.split(":");
		
		playerInfoOnline = new Player();
		
		dataInfoSplit = dataSplit[1].split("=");
		playerInfoOnline.name_A = dataInfoSplit[1];
		
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
		
		dataInfoSplit = dataSplit[21].split("=");
		playerInfoOnline.buffsA_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[22].split("=");
		playerInfoOnline.buffsB_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[23].split("=");
		playerInfoOnline.buffsC_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[24].split("=");
		playerInfoOnline.heal_A = dataInfoSplit[1];
		
		dataInfoSplit = dataSplit[25].split("=");
		playerInfoOnline.expshared_A = dataInfoSplit[1]; 
		
		if(lstPlayersOnline.size() > 0) {
			if(lstPlayersOnline.get(0).accountID.equals(playerInfo.accountID) && lstPlayersOnline.get(0).map_A.equals(playerInfo.map_A)) {
				syncPlayerMob = "yes";
			}
			else {
				syncPlayerMob = "no";
				dataInfoSplit = dataSplit[27].split("=");
				MobsHPSync = dataInfoSplit[1];  
			}	
		}
		
		//if(playerInfo.name_A.equals(playerInfoOnline.name_A)) {
		//	return;
		//}
		
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
				lstPlayersOnline.get(i).buffsA_A = playerInfoOnline.buffsA_A;
				lstPlayersOnline.get(i).buffsB_A = playerInfoOnline.buffsB_A;
				lstPlayersOnline.get(i).buffsC_A = playerInfoOnline.buffsC_A;
				lstPlayersOnline.get(i).heal_A = playerInfoOnline.heal_A;
				lstPlayersOnline.get(i).expshared_A = playerInfoOnline.expshared_A;
				check = true;
			}
		}		
		if(!check) {
			lstPlayersOnline.add(playerInfoOnline);
		}	
	}
	
	private void MobsManagerOnline(String data) {
		
		if(syncPlayerMob.equals("no")) {
			dataSplit = MobsHPSync.split("#");
		}
		else {
			MobsHPSync = "";
		}
		
		for(int i = 0; i < lstMobs.size(); i++) {	
			countMobLoop = i + 1;
			if(syncPlayerMob.equals("no")) {
				dataSplitExtra = dataSplit[countMobLoop].split("@");
				if(lstMobs.get(i).mobID.equals(dataSplitExtra[0])){
					lstMobs.get(i).mobPosX = Float.parseFloat(dataSplitExtra[1]);
					lstMobs.get(i).mobPosY = Float.parseFloat(dataSplitExtra[2]);
					if(dataSplitExtra[3].equals("dead")) { lstMobs.get(i).dead = true; } else { lstMobs.get(i).dead = false; }
				}
			}
			
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
			
			if(syncPlayerMob.equals("yes") && lstMobs.get(i).map.equals(playerInfo.map_A)) {
				if(lstMobs.get(i).dead) { mobCondition = "dead"; } else { mobCondition = "alive"; }
				MobsHPSync = MobsHPSync + "#" + lstMobs.get(i).mobID + "@" + lstMobs.get(i).mobPosX + "@" + lstMobs.get(i).mobPosY + "@" + mobCondition; 
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
			mobA.mobStartPosX = 59;
			mobA.mobStartPosY = -26;
			mobA.mobHeight = 14;
			mobA.mobWidth = 10;
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
			mobA.atkHit = 1;
			mobA.getHit = false;
			mobA.mobCountDown = 300;
			mobA.mobCountDownMax = 300;
			mobA.side = "right";
			mobA.MobSelected = "no";
			mobA.maxRanged = 20;
			mobA.minRanged = 40;
			mobA.loot1 = "BLOP";
			mobA.loot2 = "HPCAN";
			mobA.loot3 = "HATSLIME";
			mobA.respawnTime = 300;
			mobA.respawnTimeMax = 300;
			mobA.map = "Streets305";
			mobA.statusTime = 300;
			mobA.speed = 0.12f;
			mobA.status = "none";
			mobA.OnlineID = "MobA";
			lstMobs.add(mobA);
		}
		
		return lstMobs;
	}
	
	public ArrayList<Monster> GetMonsters() {
		return lstMobs;
	}
	
	public Sprite MobAppear(int num) {
		
		Monster mob = lstMobs.get(num);
		
		if(mob.hp <= 0) { mob.dead = true; }
		
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
		
			
			//See status stun
			if(mob.status.equals("stun")) {
				
				if(syncPlayerMob.equals("yes")) {
					//Mov X
					frameMob = randnumber.nextInt(80);
					if(frameMob >= 0 && frameMob <= 20 && mobDirectionOnWalk.equals("wait")) {
						walkInDirection = 20;
						mobDirectionOnWalk = "right";					
					}
					if(frameMob >= 20 && frameMob <= 40 && mobDirectionOnWalk.equals("wait")) {
						walkInDirection = 20;
						mobDirectionOnWalk = "left";						
					}			
					//Mov Y
					if(frameMob >= 40 && frameMob <= 60 && mobDirectionOnWalk.equals("wait")) {
						walkInDirection = 20;
						mobDirectionOnWalk = "back";					
					}
					if(frameMob >= 60 && frameMob <= 80 && mobDirectionOnWalk.equals("wait")) {
						walkInDirection = 20;
						mobDirectionOnWalk = "front";
					}
					
					if(walkInDirection > 0) {
						walkInDirection--;
						if(mobDirectionOnWalk.equals("right")) { mob.mobPosX = mob.mobPosX; } //+ mob.speed; }
						if(mobDirectionOnWalk.equals("left")) { mob.mobPosX = mob.mobPosX; } // - mob.speed;  }
						if(mobDirectionOnWalk.equals("back")) { mob.mobPosY = mob.mobPosY; } // + mob.speed; }
						if(mobDirectionOnWalk.equals("front")) { mob.mobPosY = mob.mobPosY; } // - mob.speed;  }
					}
					if(walkInDirection <= 0) {
						walkInDirection = 0;
						mobDirectionOnWalk = "wait";
					}
					
					if(mob.mobPosX > 237 && !mob.dead) { mob.mobPosX = mob.mobStartPosX; mob.mobPosY = mob.mobStartPosY; }
					if(mob.mobPosX < -105 && !mob.dead) { mob.mobPosX = mob.mobStartPosX; mob.mobPosY = mob.mobStartPosY; }
					if(mob.mobPosY > 237 && !mob.dead) { mob.mobPosX = mob.mobStartPosX; mob.mobPosY = mob.mobStartPosY; }
					if(mob.mobPosY < -161 && !mob.dead) { mob.mobPosX = mob.mobStartPosX; mob.mobPosY = mob.mobStartPosY; }
				}
			}
			
			//See status poison
			if(mob.status.equals("poison") && mob.frame >= 10 && mob.frame <= 12) {
				mob.hp = mob.hp--;
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
		
		//if(mob.target.equals(playerInfo.name_A)) {
		//	playerCoordsX = Float.parseFloat(playerInfo.coordX_A);
		//	playerCoordsY = Float.parseFloat(playerInfo.coordY_A);
			
		//	if(mob.mobPosX > playerCoordsX) { mob.mobPosX -= 0.15f; }
		//	if(mob.mobPosX < playerCoordsX + 9) { mob.mobPosX += 0.15f; }
		//	if(mob.mobPosY > playerCoordsY) { mob.mobPosY -= 0.15f; }
		//	if(mob.mobPosY < playerCoordsY) { mob.mobPosY += 0.15f; }
		//}
		
		return spr_master;
	}
	
	public void CheckSkillCooldown() {
		if(skillCoolDown > 0) {
			skillCoolDown--;
		}
	}
	
	public void AutoAttack() {
		
		if(playerInfo.target_A.equals("none")) { return; }
		
		playerCoordsX = Float.parseFloat(playerInfo.coordX_A);
		playerCoordsY = Float.parseFloat(playerInfo.coordY_A);
		playerRangeHit = false;
		playerAtkCooldownHit = false;
		
		if (playerInfo.job_A.equals("Gunner")) {
			playerRangeXPlus = playerCoordsX + 55;
			playerRangeYPlus = playerCoordsY + 70;
			playerRangeXMinus = playerCoordsX - 45;
			playerRangeYMinus = playerCoordsY - 30;
			
		}
		else {
			playerRangeXPlus = playerCoordsX + 25;
			playerRangeYPlus = playerCoordsY + 45;
			playerRangeXMinus = playerCoordsX - 5;		 
			playerRangeYMinus = playerCoordsY - 5;
			
		}
		
		if(playerInfo.inBattle_A.equals("yes")) {
			playerCountDown--;
			
			if(playerCountDown < 0) { 
				playerStatus = playerInfo.stats_A.split("#");
				playerStatusNumber = playerStatus[1].split(":");
				playerAgi = Integer.parseInt(playerStatusNumber[1]);
				
				playerCountDown = 300; 
				
				if(playerAgi > 1) {
					playerCountDown = playerCountDown - playerAgi;
				}
				
				if(playerInfo.job_1.equals("Thief")) {
					playerCountDown = playerCountDown - 20;
				}
				
				playerAtkCooldownHit = true;
			}
			
			if(skillCoolDown <= 0) {
				skillCoolDown = 0;
			}
		}
		
		for(int i = 0; i < lstMobs.size(); i++) {
			
			mobX = lstMobs.get(i).mobPosX;
			mobY = lstMobs.get(i).mobPosY;	
			
			mobHeight = lstMobs.get(i).mobHeight;
			mobWidth = lstMobs.get(i).mobWidth;
			
			mobRangeXMinus = mobX - (mobWidth * 2);
			mobRangeXPlus = mobX + (mobWidth * 2);
			
			mobRangeYMinus = mobY - (mobHeight * 3);
			mobRangeYPlus = mobY + (mobHeight * 3);
			
			if(playerRangeXPlus > mobRangeXMinus && playerRangeXPlus < mobRangeXPlus && playerRangeYPlus > mobRangeYMinus && playerRangeYPlus < mobRangeYPlus) { playerRangeHit = true; }
			if(playerRangeXPlus > mobRangeXMinus && playerRangeXPlus < mobRangeXPlus && playerRangeYMinus < mobRangeYPlus && playerRangeYMinus > mobRangeYMinus) { playerRangeHit = true; }
			if(playerRangeXMinus < mobRangeXPlus && playerRangeXMinus > mobRangeXMinus && playerRangeYPlus > mobRangeYMinus && playerRangeYPlus < mobRangeYPlus) { playerRangeHit = true; }
			if(playerRangeXMinus < mobRangeXPlus && playerRangeXMinus > mobRangeXMinus && playerRangeYMinus < mobRangeYPlus && playerRangeYMinus > mobRangeYMinus) { playerRangeHit = true; }
			
			playerRangeHit = false; 
			if(playerRangeHit) {
				
				if(playerCoordsX < mobX) { playerSide = "right"; }
				if(playerCoordsX > mobX) { playerSide = "left"; }
				
				if(playerInfo.target_A.equals(lstMobs.get(i).mobID)) {
					
					playerInfo.inBattle_A = "yes";			
					if(playerAtkCooldownHit) {
						
						playerAtkCooldownHit = false;
						playerRangeHit = false;
						playerStatus = playerInfo.stats_A.split("#");
						playerStatusNumber = playerStatus[0].split(":");
						playerStr = Integer.parseInt(playerStatusNumber[1]);
						playerAtk = Integer.parseInt(playerInfo.atk_A);
						playerAtk = playerAtk + playerStr;
						playerStatusNumber = playerStatus[4].split(":");
						playerDex = Integer.parseInt(playerStatusNumber[1]);						
						atkHitRandom = randnumber.nextInt(150);
						if(playerInfo.buffsA_A.contains("precision")) { playerDex = playerDex + 50; }
						if(playerInfo.buffsB_A.contains("precision")) { playerDex = playerDex + 50; }
						if(playerInfo.buffsC_A.contains("precision")) { playerDex = playerDex + 50; }
						if(atkHitRandom <= (50 + playerDex)) {
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 2; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP;
							
							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));	
								GiveExp(lstMobs.get(i),"normal",0);
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
							
							if(onlineCheck) { 
								OnlineManager("Atk",String.valueOf(mobHP)); 
							}
						}
						else
						{
							Damage dmg = new Damage();
							dmg.color = "Yellow";
							dmg.posX = mobX;
							dmg.posY = mobY + 10;
							dmg.user = playerInfo.name_A;
							dmg.dmg = 0;
							dmg.frame = 0;					
							lstDanos.add(dmg);
							
							if(onlineCheck) { 
								OnlineManager("Atk",String.valueOf(mobHP)); 
							}
						}
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
			
			if(!lstMobs.get(i).status.equals("none")){
				lstMobs.get(i).statusTime = lstMobs.get(i).statusTime--;
				if(lstMobs.get(i).statusTime <= 0) {
					lstMobs.get(i).status = "none";
					lstMobs.get(i).statusTime = 300;
				}
			}
			
			mobX = lstMobs.get(i).mobPosX;
			mobY = lstMobs.get(i).mobPosY;
			
			mobRangeXMinus = mobX - 10;
			mobRangeXPlus = mobX + 25; 
			
			mobRangeYMinus = mobY - 30;
			mobRangeYPlus = mobY + 35;
			
			mobCountDown = lstMobs.get(i).mobCountDown;
			
			if(lstMobs.get(i).target.equals(playerInfo.name_A)) {
				mobCountDown--;
				lstMobs.get(i).mobCountDown = mobCountDown;
				if(mobCountDown <= 0) {
					if(mobRangeXPlus > (playerCoordsX + 10) && mobRangeXMinus < (playerCoordsX + 10) && mobRangeYPlus > (playerCoordsY + 18) && mobRangeYMinus < (playerCoordsY + 18)) {
						
						atkHitRandom = randnumber.nextInt(10);						
						if(!lstMobs.get(i).status.equals("stun")) {
						
							if(atkHitRandom <= 2) {
								mobAtk = lstMobs.get(i).atkHit * 2;
								if(playerInfo.buffsA_A.contains("ironshield")) { mobAtk = mobAtk/2 ; }
								if(playerInfo.buffsB_A.contains("ironshield")) { mobAtk = mobAtk/2 ; }
								if(playerInfo.buffsC_A.contains("ironshield")) { mobAtk = mobAtk/2 ; }
								if(playerInfo.buffsA_A.contains("protect")) { mobAtk = 0 ; }
								if(playerInfo.buffsB_A.contains("protect")) { mobAtk = 0 ; }
								if(playerInfo.buffsC_A.contains("protect")) { mobAtk = 0 ; }
								if(playerInfo.buffsA_A.contains("invisibility")) { mobAtk = 0 ; }
								if(playerInfo.buffsB_A.contains("invisibility")) { mobAtk = 0 ; }
								if(playerInfo.buffsC_A.contains("invisibility")) { mobAtk = 0 ; }
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
								if(playerInfo.buffsA_A.contains("ironshield")) { mobAtk = mobAtk/2 ; }
								if(playerInfo.buffsB_A.contains("ironshield")) { mobAtk = mobAtk/2 ; }
								if(playerInfo.buffsC_A.contains("ironshield")) { mobAtk = mobAtk/2 ; }
								if(playerInfo.buffsA_A.contains("protect")) { mobAtk = 0 ; }
								if(playerInfo.buffsB_A.contains("protect")) { mobAtk = 0 ; }
								if(playerInfo.buffsC_A.contains("protect")) { mobAtk = 0 ; }
								if(playerInfo.buffsA_A.contains("invisibility")) { mobAtk = 0 ; }
								if(playerInfo.buffsB_A.contains("invisibility")) { mobAtk = 0 ; }
								if(playerInfo.buffsC_A.contains("invisibility")) { mobAtk = 0 ; }
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
	}
	
	public boolean CheckSkillType(int numSkill) {
		if(skillCoolDown > 0) { return false; }
		
		//Novice
		if(playerInfo.job_A.equals("Novice")) {
			//Triple Attack
			if(numSkill == 1) {
				return false;
			}
		}
		
		//Swordman
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
			//Ironshield
			if(numSkill == 4) {
				return false;
			}
			//Protect
			if(numSkill == 5) {
				return false;
			}
		}
		
		//Mage
		if(playerInfo.job_A.equals("Mage")) {
			//Fireball
			if(numSkill == 1) {
				return true;
			}
			//icecrystal
			if(numSkill == 2) {
				return true;
			}
			//Thundercloud
			if(numSkill == 3) {
				return true;
			}
			//Rockbound
			if(numSkill == 4) {
				return true;
			}
			//soulcrash
			if(numSkill == 5) {
				return true;
			}
		}
		
		//Thief
		if(playerInfo.job_A.equals("Thief")) {
			//Double hit
			if(numSkill == 1) {
				return false;
			}
			//Dash Kick
			if(numSkill == 2) {
				return false;
			}
			//Steal
			if(numSkill == 3) {
				return false;
			}
			//Poison Hit
			if(numSkill == 4) {
				return false;
			}
			//Invisibility
			if(numSkill == 5) {
				return false;
			}
		}
		
		//Medic
		if(playerInfo.job_A.equals("Medic")) {
			//Heal
			if(numSkill == 1) {
				return true;
			}
			//atkboost
			if(numSkill == 2) {
				return true;
			}
			//defboost
			if(numSkill == 3) {
				return true;
			}
			//holyprism
			if(numSkill == 4) {
				return true;
			}
			//regen
			if(numSkill == 5) {
				return true;
			}
		}
			
		return false;
	}
	
	public void SkillAtkNovice(int numSkill){
		if(skillCoolDown > 0) { return; }
		playerCountDown = 200;

		playerCoordsX = Float.parseFloat(playerInfo.coordX_A);
		playerCoordsY = Float.parseFloat(playerInfo.coordY_A);

		playerRangeXMinus = playerCoordsX - 8;
		playerRangeXPlus = playerCoordsX + 30;
		playerRangeYMinus = playerCoordsY - 10;
		playerRangeYPlus = playerCoordsY + 40;
		
		//Novice
		if(playerInfo.job_A.equals("Novice")) {
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
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = (playerAtk + playerStr) * 3;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 

							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));	
								GiveExp(lstMobs.get(i),"normal",0);
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

							if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }
							damageOnline = "0";
						}					
					}
				}			
			}
		}
	}
	
	public void SkillAtkSwordman(int numSkill) {
		
		if(skillCoolDown > 0) { return; }
		playerCountDown = 200;
		
		playerCoordsX = Float.parseFloat(playerInfo.coordX_A);
		playerCoordsY = Float.parseFloat(playerInfo.coordY_A);
	
		playerRangeXMinus = playerCoordsX - 8;
		playerRangeXPlus = playerCoordsX + 30;
		playerRangeYMinus = playerCoordsY - 10;
		playerRangeYPlus = playerCoordsY + 40;
		
		//Swordman
		if(playerInfo.job_A.equals("Swordman")) {
			
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
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = (playerAtk + playerStr) * 10;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 
							
							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));	
								GiveExp(lstMobs.get(i),"normal",0);
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
							
							if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }	
							damageOnline = "0";
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
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = (playerAtk + (playerDex * 2)) * 5;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 
							
							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));	
									GiveExp(lstMobs.get(i),"normal",0);
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
							
							if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }	
							damageOnline = "0";
						}					
					}
				}
			}
			
			//Health Boost
			if(numSkill == 3) {
				
				if(playerInfo.buffsA_A.contains("healthboost") || playerInfo.buffsB_A.contains("healthboost") || playerInfo.buffsC_A.contains("healthboost")) { return; }
				
				playerHP = Integer.parseInt(playerInfo.hp_A);
				playerHP = playerHP * 5;
				playerInfo.hp_A = String.valueOf(playerHP);
				
				Skill skl = new Skill();
				skl.caster = playerInfo.name_A;
				skl.name = "Aumento de Vida";
				skl.posX = Float.parseFloat(playerInfo.coordX_A);
				skl.posY = Float.parseFloat(playerInfo.coordY_A);
				skl.height = 70;
				skl.width = 50;
				skl.type = "heal";
				skl.dmg = 0;
				skl.follow = false;
				skl.frame = 1;
				skl.cd = 2000;
				lstSkill.add(skl);
				
				skillCoolDown = 2000;
				
				if(playerInfo.buffsA_A.equals("none-0")) { playerInfo.buffsA_A = "healthboost-2000"; return; }
				if(playerInfo.buffsB_A.equals("none-0")) { playerInfo.buffsB_A = "healthboost-2000"; return; }
				if(playerInfo.buffsC_A.equals("none-0")) { playerInfo.buffsC_A = "healthboost-2000"; return; }				
			}
			
			// Iron Shield
			if(numSkill == 4){
				
				if(playerInfo.buffsA_A.contains("ironshield") || playerInfo.buffsB_A.contains("ironshield") || playerInfo.buffsC_A.contains("ironshield")) { return; }
				
				Skill skl = new Skill();
				skl.caster = playerInfo.name_A;
				skl.name = "Escudo de Ferro";
				skl.posX = Float.parseFloat(playerInfo.coordX_A);
				skl.posY = Float.parseFloat(playerInfo.coordY_A);
				skl.height = 70;
				skl.width = 50;
				skl.type = "heal";
				skl.dmg = 0;
				skl.follow = false;
				skl.frame = 1;
				skl.cd = 500;
				lstSkill.add(skl);

				skillCoolDown = 500;

				if(playerInfo.buffsA_A.equals("none-0")) { playerInfo.buffsA_A = "ironshield-1500"; return; }
				if(playerInfo.buffsB_A.equals("none-0")) { playerInfo.buffsB_A = "ironshield-1500"; return; }
				if(playerInfo.buffsC_A.equals("none-0")) { playerInfo.buffsC_A = "ironshield-1500"; return; }		
			}
			
			//Protect
			if(numSkill == 5){
				
				if(playerInfo.buffsA_A.contains("protect") || playerInfo.buffsB_A.contains("protect") || playerInfo.buffsC_A.contains("protect")) { return; }
				
				Skill skl = new Skill();
				skl.caster = playerInfo.name_A;
				skl.name = "Protecao";
				skl.posX = Float.parseFloat(playerInfo.coordX_A);
				skl.posY = Float.parseFloat(playerInfo.coordY_A);
				skl.height = 70;
				skl.width = 50;
				skl.type = "heal";
				skl.dmg = 0;
				skl.follow = false;
				skl.frame = 1;
				skl.cd = 2000;
				lstSkill.add(skl);

				skillCoolDown = 2000;
				
				if(playerInfo.buffsA_A.equals("none-0")) { playerInfo.buffsA_A = "protect-500"; return; }
				if(playerInfo.buffsB_A.equals("none-0")) { playerInfo.buffsB_A = "protect-500"; return; }
				if(playerInfo.buffsC_A.equals("none-0")) { playerInfo.buffsC_A = "protect-500"; return; }
			}
		}		
	}
	
	
	
	public void SkillAtkThief(int numSkill){
		if(skillCoolDown > 0) { return; }
		playerCountDown = 200;

		playerCoordsX = Float.parseFloat(playerInfo.coordX_A);
		playerCoordsY = Float.parseFloat(playerInfo.coordY_A);

		playerRangeXMinus = playerCoordsX - 8;
		playerRangeXPlus = playerCoordsX + 30;
		playerRangeYMinus = playerCoordsY - 10;
		playerRangeYPlus = playerCoordsY + 40;
		
		//Thief
		if(playerInfo.job_A.equals("Thief")) {
			//Double hit
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
							playerStatusNumber = playerStatus[5].split(":");
							playerLuk = Integer.parseInt(playerStatusNumber[1]);
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = (playerAtk + (playerLuk * 2)) * 4;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 

							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));
								GiveExp(lstMobs.get(i),"normal",0);
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
							skl.name = "Golpe Duplo";
							skl.posX = mobX - 30;
							skl.posY = mobY - 20;
							skl.height = 70;
							skl.width = 70;
							skl.type = "dmg";
							skl.dmg = playerAtk;
							skl.follow = false;
							skl.frame = 1;
							skl.cd = 100;
							lstSkill.add(skl);

							skillCoolDown = 100;

							if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }
							damageOnline = "0";
						}					
					}
				}			
			}
			//Low kick
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
							playerStatusNumber = playerStatus[1].split(":");
							playerAgi = Integer.parseInt(playerStatusNumber[1]);
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = (playerAtk + 50 + (playerLuk) + (playerAgi * 3)) * 2;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 
							lstMobs.get(i).status = "Stun";

							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));
								GiveExp(lstMobs.get(i),"normal",0);
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
							skl.name = "Chute Rapido";
							skl.posX = mobX - 30;
							skl.posY = mobY - 20;
							skl.height = 70;
							skl.width = 70;
							skl.type = "dmg";
							skl.dmg = playerAtk;
							skl.follow = false;
							skl.frame = 1;
							skl.cd = 1000;
							lstSkill.add(skl);

							skillCoolDown = 1000;

							if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }
							damageOnline = "0";
						}					
					}
				}			
			}
			//Steal
			if(numSkill == 3) {

				for(int i = 0; i < lstMobs.size(); i++) {

					mobX = lstMobs.get(i).mobPosX;
					mobY = lstMobs.get(i).mobPosY;

					if(playerRangeXPlus > mobX && playerRangeXMinus < mobX && playerRangeYPlus > mobY && playerRangeYMinus < mobY) {

						if(playerCoordsX < mobX) { playerSide = "right"; }
						if(playerCoordsX > mobX) { playerSide = "left"; }

						if(playerInfo.target_A.equals(lstMobs.get(i).mobID)) {

							playerInfo.inBattle_A = "yes";
							playerStatus = playerInfo.stats_A.split("#"); 
							playerStatusNumber = playerStatus[5].split(":");
							playerLuk = Integer.parseInt(playerStatusNumber[1]);
							randomCount = randnumber.nextInt(100);
							
							if(randomCount <= playerLuk) {
								GiveLoot(lstMobs.get(i));
							}
							
							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));							
								lstMobs.get(i).dead = true;
								playerInfo.inBattle_A = "no";
							}

							Skill skl = new Skill();
							skl.caster = playerInfo.name_A;
							skl.name = "Roubar";
							skl.posX = mobX - 30;
							skl.posY = mobY - 20;
							skl.height = 70;
							skl.width = 70;
							skl.type = "dmg";
							skl.dmg = 0;
							skl.follow = false;
							skl.frame = 1;
							skl.cd = 1000;
							lstSkill.add(skl);

							skillCoolDown = 1000;	
						}					
					}
				}			
			}
			
			//Poison Hit
			if(numSkill == 4) {

				for(int i = 0; i < lstMobs.size(); i++) {

					mobX = lstMobs.get(i).mobPosX;
					mobY = lstMobs.get(i).mobPosY;

					if(playerRangeXPlus > mobX && playerRangeXMinus < mobX && playerRangeYPlus > mobY && playerRangeYMinus < mobY) {

						if(playerCoordsX < mobX) { playerSide = "right"; }
						if(playerCoordsX > mobX) { playerSide = "left"; }

						if(playerInfo.target_A.equals(lstMobs.get(i).mobID)) {

							playerInfo.inBattle_A = "yes";
							playerStatus = playerInfo.stats_A.split("#"); 
							playerStatusNumber = playerStatus[5].split(":");
							playerLuk = Integer.parseInt(playerStatusNumber[1]);
							playerStatusNumber = playerStatus[1].split(":");
							playerAgi = Integer.parseInt(playerStatusNumber[1]);
							playerStatusNumber = playerStatus[0].split(":");
							playerStr = Integer.parseInt(playerStatusNumber[1]);				
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = (playerAtk + (playerLuk * 2) + (playerAgi * 2) + (playerStr * 2)) * 2;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 
							lstMobs.get(i).status = "Poison";
							
							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));	
								GiveExp(lstMobs.get(i),"normal",0);
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
							skl.name = "Envenenar";
							skl.posX = mobX - 30;
							skl.posY = mobY - 20;
							skl.height = 70;
							skl.width = 70;
							skl.type = "dmg";
							skl.dmg = 0;
							skl.follow = false;
							skl.frame = 1;
							skl.cd = 250;
							lstSkill.add(skl);

							skillCoolDown = 250;

							if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }	
							damageOnline = "0";
						}					
					}
				}			
			}
			
			//Invisibility
			if(numSkill == 5) {
				
				if(playerInfo.buffsA_A.contains("invisibility") || playerInfo.buffsB_A.contains("invisibility") || playerInfo.buffsC_A.contains("invisibility")) { return; }
				
				Skill skl = new Skill();
				skl.caster = playerInfo.name_A;
				skl.name = "Invisibilidade";
				skl.posX = Float.parseFloat(playerInfo.coordX_A);
				skl.posY = Float.parseFloat(playerInfo.coordY_A);
				skl.height = 70;
				skl.width = 50;
				skl.type = "heal";
				skl.dmg = 0;
				skl.follow = false;
				skl.frame = 1;
				skl.cd = 1000;
				lstSkill.add(skl);
				
				skillCoolDown = 1000;
				
				if(playerInfo.buffsA_A.equals("none-0")) { playerInfo.buffsA_A = "invisibility-2000"; return; }
				if(playerInfo.buffsB_A.equals("none-0")) { playerInfo.buffsB_A = "invisibility-2000"; return; }
				if(playerInfo.buffsC_A.equals("none-0")) { playerInfo.buffsC_A = "invisibility-2000"; return; }				
			}	
		}
	}
	
	public void SkillAtkMedic(int numSkill, float posX, float posY){
		if(skillCoolDown > 0) { return; }
		playerCountDown = 200;
		
		isCasting = false;

		playerCoordsX = Float.parseFloat(playerInfo.coordX_A);
		playerCoordsY = Float.parseFloat(playerInfo.coordY_A);
		
		playerRangeXMinus = playerCoordsX - 10;
		playerRangeXPlus = playerCoordsX + 40;
		playerRangeYMinus = playerCoordsY - 10;
		playerRangeYPlus = playerCoordsY + 40;
		
		//Medic
		if(playerInfo.job_A.equals("Medic")) {
			//Heal
			if(numSkill == 1) {					
				playerStatus = playerInfo.stats_A.split("#");
				playerStatusNumber = playerStatus[2].split(":");
				playerWis = Integer.parseInt(playerStatusNumber[1]);
				playerAtk = Integer.parseInt(playerInfo.atk_A);
				playerAtk = (playerAtk + playerWis * 2) * 5;
				if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
				playerHP = Integer.parseInt(playerInfo.hp_A);
				playerHPMax = Integer.parseInt(playerInfo.maxhp_A);
				playerHP = playerHP + playerAtk;
				if(playerHP >= playerHPMax) { playerHP = playerHPMax; }
				playerInfo.hp_A = String.valueOf(playerHP);
				heal = String.valueOf(playerAtk);
				
				Damage dmg = new Damage();
				dmg.color = "Green";
				dmg.posX = Float.parseFloat(playerInfo.coordX_A);
				dmg.posY = Float.parseFloat(playerInfo.coordY_A);
				dmg.user = playerInfo.name_A;
				dmg.dmg = playerAtk;
				dmg.frame = 0;					
				lstDanos.add(dmg);

				Skill skl = new Skill();
				skl.caster = playerInfo.name_A;
				skl.name = "Curar";
				skl.posX = Float.parseFloat(playerInfo.coordX_A);
				skl.posY = Float.parseFloat(playerInfo.coordY_A);
				skl.height = 50;
				skl.width = 50;
				skl.type = "dmg";
				skl.dmg = playerAtk;
				skl.follow = false;
				skl.frame = 1;
				skl.cd = 300;
				lstSkill.add(skl);

				skillCoolDown = 300;

				if(onlineCheck) { 
					heal = String.valueOf(playerAtk);
					healStop = 50;
				}	
				damageOnline = "0";
										
			}
			
			//Atk Boost
			if(numSkill == 2) {		
				
				if((playerInfo.buffsA_A.contains("atkboost") || playerInfo.buffsA_A.contains("atkboost") || playerInfo.buffsA_A.contains("atkboost"))) {
					return; 
				}
				
				playerAtk = Integer.parseInt(playerInfo.atk_A);
				playerAtk = playerAtk + 30;
				playerInfo.atk_A = String.valueOf(playerAtk);

				Skill skl = new Skill();
				skl.caster = playerInfo.name_A;
				skl.name = "Aumentar Ataque";
				skl.posX = playerCoordsX - 30;
				skl.posY = playerCoordsY - 15;
				skl.height = 100;
				skl.width = 100;
				skl.type = "dmg";
				skl.dmg = 0;
				skl.follow = false;
				skl.frame = 1;
				skl.cd = 20;
				lstSkill.add(skl);

				skillCoolDown = 20;
							
				if(playerInfo.buffsA_A.equals("none-0")) { playerInfo.buffsA_A = "atkboost-800"; return; }
				if(playerInfo.buffsB_A.equals("none-0")) { playerInfo.buffsB_A = "atkboost-800"; return; }
				if(playerInfo.buffsC_A.equals("none-0")) { playerInfo.buffsC_A = "atkboost-800"; return; }	
	
			}
			
			//Def Boost
			if(numSkill == 3) {	
				
				if((playerInfo.buffsA_A.contains("defboost") || playerInfo.buffsA_A.contains("defboost") || playerInfo.buffsA_A.contains("defboost"))) {
					return;  
				}
				
				playerDef = Integer.parseInt(playerInfo.atk_A);
				playerDef = playerDef + 30;
				playerInfo.def_A = String.valueOf(playerDef);

				Skill skl = new Skill();
				skl.caster = playerInfo.name_A;
				skl.name = "Aumentar Defesa";
				skl.posX = playerCoordsX - 30;
				skl.posY = playerCoordsY - 15;
				skl.height = 100;
				skl.width = 100;
				skl.type = "dmg";
				skl.dmg = 0;
				skl.follow = false;
				skl.frame = 1;
				skl.cd = 30;
				lstSkill.add(skl);

				skillCoolDown = 30;
				
				if(playerInfo.buffsA_A.equals("none-0")) { playerInfo.buffsA_A = "defboost-700"; return; }
				if(playerInfo.buffsB_A.equals("none-0")) { playerInfo.buffsB_A = "defboost-700"; return; }
				if(playerInfo.buffsC_A.equals("none-0")) { playerInfo.buffsC_A = "defboost-700"; return; }					
			}
		
		
			//Holyprism
			if(numSkill == 4) {
				for(int i = 0; i < lstMobs.size(); i++) {
	
					mobX = lstMobs.get(i).mobPosX;
					mobY = lstMobs.get(i).mobPosY;		

					if((posX + 20) > mobX && (posX - 20) < mobX && (posY + 20) > mobY && (posY - 20) < mobY) {
	
						if(playerCoordsX < mobX) { playerSide = "right"; }
						if(playerCoordsX > mobX) { playerSide = "left"; }
	
						if(playerInfo.target_A.equals(lstMobs.get(i).mobID)) {
							playerInfo.inBattle_A = "yes";
							playerStatus = playerInfo.stats_A.split("#"); 
							playerStatusNumber = playerStatus[2].split(":");
							playerWis = Integer.parseInt(playerStatusNumber[1]);
							playerStatusNumber = playerStatus[3].split(":");
							playerVit = Integer.parseInt(playerStatusNumber[1]);
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = (playerAtk + (playerWis * 3) + (playerVit * 3)) * 3;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 
	
							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));	
								GiveExp(lstMobs.get(i),"normal",0);
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
							skl.name = "Prisma Sagrado";
							skl.posX = mobX - 20;
							skl.posY = mobY - 10;
							skl.posMobX = mobX - 10;
							skl.posMobY = mobY;
							skl.height = 70;
							skl.width = 70;
							skl.type = "dmg";
							skl.dmg = playerAtk;
							skl.follow = true;
							skl.frame = 1;
							skl.cd = 300;
							lstSkill.add(skl);
	
							skillCoolDown = 300;
	
							if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }		
						}					
					}
				}
			}
			
			//Regen
			if(numSkill == 5) {
				
				if((playerInfo.buffsA_A.contains("regen") || playerInfo.buffsA_A.contains("regen") || playerInfo.buffsA_A.contains("regen"))) {
					return;  
				}
				
				recoverytimer = 500;

				Skill skl = new Skill();
				skl.caster = playerInfo.name_A;
				skl.name = "Regenerar";
				skl.posX = playerCoordsX - 30;
				skl.posY = playerCoordsY - 15;
				skl.height = 100;
				skl.width = 100;
				skl.type = "dmg";
				skl.dmg = 0;
				skl.follow = false;
				skl.frame = 1;
				skl.cd = 100;
				lstSkill.add(skl);

				skillCoolDown = 100;
				
				if(playerInfo.buffsA_A.equals("none-0")) { playerInfo.buffsA_A = "regen-2500"; return; }
				if(playerInfo.buffsB_A.equals("none-0")) { playerInfo.buffsB_A = "regen-2500"; return; }
				if(playerInfo.buffsC_A.equals("none-0")) { playerInfo.buffsC_A = "regen-2500"; return; }			
			}
		}
	}
	
	public void SkillAtkMage(int numSkill, float posX, float posY){
		if(skillCoolDown > 0) { return; }
		playerCountDown = 200;
		isCasting = false;
		
		//Mage
		if(playerInfo.job_A.equals("Mage")) {
			//Fireball
			if(numSkill == 1) {
				for(int i = 0; i < lstMobs.size(); i++) {

					mobX = lstMobs.get(i).mobPosX;
					mobY = lstMobs.get(i).mobPosY;		

					if((posX + 20) > mobX && (posX - 20) < mobX && (posY + 20) > mobY && (posY - 20) < mobY) {

						if(playerCoordsX < mobX) { playerSide = "right"; }
						if(playerCoordsX > mobX) { playerSide = "left"; }

						if(playerInfo.target_A.equals(lstMobs.get(i).mobID)) {
							playerInfo.inBattle_A = "yes";
							playerStatus = playerInfo.stats_A.split("#"); 
							playerStatusNumber = playerStatus[2].split(":");
							playerWis = Integer.parseInt(playerStatusNumber[1]);
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = (playerAtk + playerWis) * 5;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 

							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));
								GiveExp(lstMobs.get(i),"normal",0);
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
							skl.name = "Bola de Fogo";
							skl.posX = Float.parseFloat(playerInfo.coordX_A);
							skl.posY = Float.parseFloat(playerInfo.coordY_A);
							skl.posMobX = mobX;
							skl.posMobY = mobY;
							skl.height = 40;
							skl.width = 40;
							skl.type = "dmg";
							skl.dmg = playerAtk;
							skl.follow = true;
							skl.frame = 1;
							skl.cd = 150;
							lstSkill.add(skl);

							skillCoolDown = 150;

							if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }		
						}					
					}
				}			
			}
			//Icecrystal
			if(numSkill == 2) {
				for(int i = 0; i < lstMobs.size(); i++) {

					mobX = lstMobs.get(i).mobPosX;
					mobY = lstMobs.get(i).mobPosY;		

					if((posX + 20) > mobX && (posX - 20) < mobX && (posY + 20) > mobY && (posY - 20) < mobY) {

						if(playerCoordsX < mobX) { playerSide = "right"; }
						if(playerCoordsX > mobX) { playerSide = "left"; }

						if(playerInfo.target_A.equals(lstMobs.get(i).mobID)) {
							playerInfo.inBattle_A = "yes";
							playerStatus = playerInfo.stats_A.split("#"); 
							playerStatusNumber = playerStatus[2].split(":");
							playerWis = Integer.parseInt(playerStatusNumber[1]);
							playerStatusNumber = playerStatus[5].split(":");
							playerDex = Integer.parseInt(playerStatusNumber[1]);
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = (playerAtk + playerWis + (playerDex * 2)) * 3;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 

							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));	
								GiveExp(lstMobs.get(i),"normal",0);
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
							skl.name = "Cristal de Gelo";
							skl.posX = mobX;
							skl.posY = mobY;
							skl.posMobX = mobX - 10;
							skl.posMobY = mobY;
							skl.height = 70;
							skl.width = 70;
							skl.type = "dmg";
							skl.dmg = playerAtk;
							skl.follow = false;
							skl.frame = 1;
							skl.cd = 350;
							lstSkill.add(skl);

							skillCoolDown = 350;

							if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }		
						}					
					}
				}			
			}
			
			//thundercloud
			if(numSkill == 3) {
				for(int i = 0; i < lstMobs.size(); i++) {

					mobX = lstMobs.get(i).mobPosX;
					mobY = lstMobs.get(i).mobPosY;		

					if((posX + 20) > mobX && (posX - 20) < mobX && (posY + 20) > mobY && (posY - 20) < mobY) {

						if(playerCoordsX < mobX) { playerSide = "right"; }
						if(playerCoordsX > mobX) { playerSide = "left"; }

						if(playerInfo.target_A.equals(lstMobs.get(i).mobID)) {
							playerInfo.inBattle_A = "yes";
							playerStatus = playerInfo.stats_A.split("#"); 
							playerStatusNumber = playerStatus[2].split(":");
							playerWis = Integer.parseInt(playerStatusNumber[1]);
							playerStatusNumber = playerStatus[5].split(":");
							playerDex = Integer.parseInt(playerStatusNumber[1]);
							playerStatusNumber = playerStatus[2].split(":");
							playerAgi = Integer.parseInt(playerStatusNumber[1]);
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = (playerAtk + (playerWis * 3) + playerDex + (playerAgi *2)) * 5;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 

							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));
								GiveExp(lstMobs.get(i),"normal",0);
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
							skl.name = "Tempestade";
							skl.posX = mobX;
							skl.posY = mobY;
							skl.posMobX = mobX - 10;
							skl.posMobY = mobY;
							skl.height = 70;
							skl.width = 70;
							skl.type = "dmg";
							skl.dmg = playerAtk;
							skl.follow = false;
							skl.frame = 1;
							skl.cd = 750;
							lstSkill.add(skl);

							skillCoolDown = 750;

							if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }		
						}					
					}
				}			
			}
			
			//Rockbound
			if(numSkill == 4) {
				for(int i = 0; i < lstMobs.size(); i++) {

					mobX = lstMobs.get(i).mobPosX;
					mobY = lstMobs.get(i).mobPosY;		

					if((posX + 20) > mobX && (posX - 20) < mobX && (posY + 20) > mobY && (posY - 20) < mobY) {

						if(playerCoordsX < mobX) { playerSide = "right"; }
						if(playerCoordsX > mobX) { playerSide = "left"; }

						if(playerInfo.target_A.equals(lstMobs.get(i).mobID)) {
							playerInfo.inBattle_A = "yes";
							playerStatus = playerInfo.stats_A.split("#"); 
							playerStatusNumber = playerStatus[2].split(":");
							playerWis = Integer.parseInt(playerStatusNumber[1]);
							playerStatusNumber = playerStatus[4].split(":");
							playerDex = Integer.parseInt(playerStatusNumber[1]);
							playerHP = Integer.parseInt(playerInfo.hp_A);
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = (playerAtk + (playerWis * 2) + playerDex + (playerHP)) * 2;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 

							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));	
								GiveExp(lstMobs.get(i),"normal",0);
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
							skl.name = "Terremoto";
							skl.posX = mobX;
							skl.posY = mobY;
							skl.posMobX = mobX - 10;
							skl.posMobY = mobY;
							skl.height = 70;
							skl.width = 70;
							skl.type = "dmg";
							skl.dmg = playerAtk;
							skl.follow = false;
							skl.frame = 1;
							skl.cd = 300;
							lstSkill.add(skl);

							skillCoolDown = 300;

							if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }	
						}					
					}
				}			
			}
			
			//Soul Clash
			if(numSkill == 5) {
				for(int i = 0; i < lstMobs.size(); i++) {

					mobX = lstMobs.get(i).mobPosX;
					mobY = lstMobs.get(i).mobPosY;		

					if((posX + 20) > mobX && (posX - 20) < mobX && (posY + 20) > mobY && (posY - 20) < mobY) {

						if(playerCoordsX < mobX) { playerSide = "right"; }
						if(playerCoordsX > mobX) { playerSide = "left"; }

						if(playerInfo.target_A.equals(lstMobs.get(i).mobID)) {
							playerInfo.inBattle_A = "yes";
							playerStatus = playerInfo.stats_A.split("#"); 
							playerStatusNumber = playerStatus[2].split(":");
							playerWis = Integer.parseInt(playerStatusNumber[1]);
							playerStatusNumber = playerStatus[5].split(":");
							playerLuk = Integer.parseInt(playerStatusNumber[1]);
							playerMP = Integer.parseInt(playerInfo.mp_A);
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = (playerAtk + (playerWis * 2) + (playerLuk * 2) + (playerMP)) * 2;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 

							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));	
								GiveExp(lstMobs.get(i),"normal",0);
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
							skl.name = "Fantasmagorica";
							skl.posX = mobX;
							skl.posY = mobY;
							skl.posMobX = mobX - 10;
							skl.posMobY = mobY;
							skl.height = 40;
							skl.width = 40;
							skl.type = "dmg";
							skl.dmg = playerAtk;
							skl.follow = true;
							skl.frame = 1;
							skl.cd = 50;
							lstSkill.add(skl);

							skillCoolDown = 50;

							if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }		
						}					
					}
				}			
			}
		}
	}
	
	
	public void SkillAtkGunner(int numSkill){
		if(skillCoolDown > 0) { return; }
		playerCountDown = 200;

		playerCoordsX = Float.parseFloat(playerInfo.coordX_A);
		playerCoordsY = Float.parseFloat(playerInfo.coordY_A);
		
		playerRangeXMinus = playerCoordsX - 24;
		playerRangeXPlus = playerCoordsX + 75;
		playerRangeYMinus = playerCoordsY - 24;
		playerRangeYPlus = playerCoordsY + 75;
		
		isCasting = false;
		
		//Gunner
		if(playerInfo.job_A.equals("Gunner")) {
			//Fastshot
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
							playerStatusNumber = playerStatus[5].split(":");
							playerLuk = Integer.parseInt(playerStatusNumber[1]);
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = (playerAtk + playerLuk) * 2;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 

							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));
								GiveExp(lstMobs.get(i),"normal",0);
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
							skl.name = "Tiro Rapido";
							skl.posX = playerCoordsX;
							skl.posY = playerCoordsY;
							skl.posMobX = mobX;
							skl.posMobY = mobY;
							skl.height = 40;
							skl.width = 40;
							skl.type = "dmg";
							skl.dmg = playerAtk;
							skl.follow = true;
							skl.frame = 1;
							skl.cd = 50;
							lstSkill.add(skl);

							skillCoolDown = 50;

							if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }		
						}	
					}
				}			
			}
			//Bullet Rain
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
						playerStatusNumber = playerStatus[1].split(":");
						playerAgi = Integer.parseInt(playerStatusNumber[1]);
						playerStatusNumber = playerStatus[4].split(":");
						playerDex = Integer.parseInt(playerStatusNumber[1]);
						playerAtk = Integer.parseInt(playerInfo.atk_A);
						playerAtk = (playerAtk + playerAgi) + (playerDex * 3) * 4;
						if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
						mobHP = lstMobs.get(i).hp;
						mobHP = mobHP - playerAtk;
						lstMobs.get(i).target = playerInfo.name_A;
						lstMobs.get(i).getHit = true;
						lstMobs.get(i).hp = mobHP; 

						if(lstMobs.get(i).hp <= 0) {  
							GiveLoot(lstMobs.get(i));	
							GiveExp(lstMobs.get(i),"normal",0);
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
						skl.name = "Chuva de balas";
						skl.posX = mobX;
						skl.posY = mobY;
						skl.posMobX = mobX - 10;
						skl.posMobY = mobY;
						skl.height = 70;
						skl.width = 70;
						skl.type = "dmg";
						skl.dmg = playerAtk;
						skl.follow = false;
						skl.frame = 1;
						skl.cd = 300;
						lstSkill.add(skl);

						skillCoolDown = 300;

						if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }		
					}
				}	
				}
			}
			
			//Precision
			if(numSkill == 3) {		
		
				if(playerInfo.buffsA_A.contains("precision") || playerInfo.buffsB_A.contains("precision") || playerInfo.buffsC_A.contains("precision")) { return; }
				
				Skill skl = new Skill();
				skl.caster = playerInfo.name_A;
				skl.name = "Precisao";
				skl.posX = playerCoordsX - 30;
				skl.posY = playerCoordsY - 15;
				skl.height = 100;
				skl.width = 100;
				skl.type = "dmg";
				skl.dmg = 0;
				skl.follow = false;
				skl.frame = 1;
				skl.cd = 200;
				lstSkill.add(skl);

				skillCoolDown = 200;
				
				if(playerInfo.buffsA_A.equals("none-0")) { playerInfo.buffsA_A = "precision-1500"; return; }
				if(playerInfo.buffsB_A.equals("none-0")) { playerInfo.buffsB_A = "precision-1500"; return; }
				if(playerInfo.buffsC_A.equals("none-0")) { playerInfo.buffsC_A = "precision-1500"; return; }			
			}
			
			//LockShot
			if(numSkill == 4) {
				for(int i = 0; i < lstMobs.size(); i++) {

					mobX = lstMobs.get(i).mobPosX;
					mobY = lstMobs.get(i).mobPosY;		
					
					if(playerRangeXPlus > mobX && playerRangeXMinus < mobX && playerRangeYPlus > mobY && playerRangeYMinus < mobY) {

					if(playerCoordsX < mobX) { playerSide = "right"; }
					if(playerCoordsX > mobX) { playerSide = "left"; }

					if(playerInfo.target_A.equals(lstMobs.get(i).mobID)) {
						playerInfo.inBattle_A = "yes";
						playerStatus = playerInfo.stats_A.split("#"); 
						playerStatusNumber = playerStatus[2].split(":");
						playerWis = Integer.parseInt(playerStatusNumber[1]);
						playerStatusNumber = playerStatus[4].split(":");
						playerDex = Integer.parseInt(playerStatusNumber[1]);
						playerStatusNumber = playerStatus[2].split(":");
						playerAgi = Integer.parseInt(playerStatusNumber[1]);
						playerStatusNumber = playerStatus[5].split(":");
						playerLuk = Integer.parseInt(playerStatusNumber[1]);
						playerAtk = Integer.parseInt(playerInfo.atk_A);
						playerAtk = ((playerAtk * 3) + (playerWis * 2) + (playerDex * 2) + (playerLuk * 3)) * 2;
						if(playerInfo.stamina_A.equals("0")) { playerAtk = 30; }
						mobHP = lstMobs.get(i).hp;
						mobHP = mobHP - playerAtk;
						lstMobs.get(i).target = playerInfo.name_A;
						lstMobs.get(i).getHit = true;
						lstMobs.get(i).hp = mobHP; 

						if(lstMobs.get(i).hp <= 0) {  
							GiveLoot(lstMobs.get(i));	
							GiveExp(lstMobs.get(i),"normal",0);
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
						skl.name = "Tiro Mirado";
						skl.posX = mobX - 20;
						skl.posY = mobY - 10;
						skl.posMobX = mobX - 20;
						skl.posMobY = mobY - 10;
						skl.height = 60;
						skl.width = 60;
						skl.type = "dmg";
						skl.dmg = playerAtk;
						skl.follow = false;
						skl.frame = 1;
						skl.cd = 2500;
						lstSkill.add(skl);

						skillCoolDown = 2500;

						if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }	
					}
					}
				}			
			}
			
			//Mine
			if(numSkill == 5) {
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
						playerMP = Integer.parseInt(playerInfo.mp_A);
						playerAtk = Integer.parseInt(playerInfo.atk_A);
						playerAtk = (playerAtk + playerStr) * 2;
						if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
						mobHP = lstMobs.get(i).hp;
						mobHP = mobHP - playerAtk;
						lstMobs.get(i).target = playerInfo.name_A;
						lstMobs.get(i).getHit = true;
						lstMobs.get(i).hp = mobHP; 
						lstMobs.get(i).status = "stun";

						if(lstMobs.get(i).hp <= 0) {  
							GiveLoot(lstMobs.get(i));	
							GiveExp(lstMobs.get(i),"normal",0);
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
						skl.name = "Mina";
						skl.posX = playerCoordsX - 10;
						skl.posY = playerCoordsY;
						skl.posMobX = mobX - 10;
						skl.posMobY = mobY;
						skl.height = 50;
						skl.width = 50;
						skl.type = "dmg";
						skl.dmg = playerAtk;
						skl.follow = true;
						skl.frame = 1;
						skl.cd = 400;
						lstSkill.add(skl);

						skillCoolDown = 400;

						if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }		
					}								
				}
				}
			}
		}
	}
	
	public void SkillAtkBeater(int numSkill){
		if(skillCoolDown > 0) { return; }
		playerCountDown = 200;
		
		playerCoordsX = Float.parseFloat(playerInfo.coordX_A);
		playerCoordsY = Float.parseFloat(playerInfo.coordY_A);
	
		playerRangeXMinus = playerCoordsX - 8;
		playerRangeXPlus = playerCoordsX + 30;
		playerRangeYMinus = playerCoordsY - 10;
		playerRangeYPlus = playerCoordsY + 40;
		
		//Beater
		if(playerInfo.job_A.equals("Beater")) {
			
			//HammerCrash
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
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = (playerAtk + (playerStr * 2)) * 3;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 
							
							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));	
								GiveExp(lstMobs.get(i),"normal",0);
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
							skl.name = "Batida do Martelo";
							skl.posX = mobX - 15;
							skl.posY = mobY - 5;
							skl.height = 50;
							skl.width = 50;
							skl.type = "dmg";
							skl.dmg = playerAtk;
							skl.follow = false;
							skl.frame = 1;
							skl.cd = 350;
							lstSkill.add(skl);
							
							skillCoolDown = 350;
							
							if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }	
							
						}					
					}
				}			
			}
			
			//Berserk
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
							playerStatusNumber = playerStatus[0].split(":");
							playerStr = Integer.parseInt(playerStatusNumber[1]);
							playerStatusNumber = playerStatus[4].split(":");
							playerDex = Integer.parseInt(playerStatusNumber[1]);
							playerStatusNumber = playerStatus[5].split(":");
							playerLuk = Integer.parseInt(playerStatusNumber[1]);
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = (playerAtk + (playerStr * 5) + (playerLuk * 2) + (playerDex)) * 6;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 
							
							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));	
								GiveExp(lstMobs.get(i),"normal",0);
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
							skl.name = "Furia";
							skl.posX = mobX - 15;
							skl.posY = mobY - 5;
							skl.height = 50;
							skl.width = 50;
							skl.type = "dmg";
							skl.dmg = playerAtk;
							skl.follow = false;
							skl.frame = 1;
							skl.cd = 2500;
							lstSkill.add(skl);
							
							skillCoolDown = 2500;
							
							if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }	
							
						}					
					}
				}	
			}
			
			//Overpower
			if(numSkill == 3) {	
				if(playerInfo.buffsA_A.contains("overpower") || playerInfo.buffsB_A.contains("overpower") || playerInfo.buffsC_A.contains("overpower")) { return; }
				
				playerHP = Integer.parseInt(playerInfo.hp_A);
				playerHP = playerHP * 2;
				playerInfo.hp_A = String.valueOf(playerHP);
				playerAtk = Integer.parseInt(playerInfo.atk_A);
				playerAtk = playerAtk * 2;
				playerInfo.atk_A = String.valueOf(playerAtk);
				
				Skill skl = new Skill();
				skl.caster = playerInfo.name_A;
				skl.name = "Super Poder";
				skl.posX = playerCoordsX - 20;
				skl.posY = playerCoordsY - 10;
				skl.height = 70;
				skl.width = 50;
				skl.type = "heal";
				skl.dmg = 0;
				skl.follow = false;
				skl.frame = 1;
				skl.cd = 2000;
				lstSkill.add(skl);
				
				skillCoolDown = 2000;
							
				if(playerInfo.buffsA_A.equals("none-0")) { playerInfo.buffsA_A = "overpower-2000"; return; }
				if(playerInfo.buffsB_A.equals("none-0")) { playerInfo.buffsB_A = "overpower-2000"; return; }
				if(playerInfo.buffsC_A.equals("none-0")) { playerInfo.buffsC_A = "overpower-2000"; return; }	
			
			}
			
			// Rage Bound
			if(numSkill == 4){
				
				for(int i = 0; i < lstMobs.size(); i++) {
					
					mobX = lstMobs.get(i).mobPosX;
					mobY = lstMobs.get(i).mobPosY;
					
					if(playerRangeXPlus > mobX && playerRangeXMinus < mobX && playerRangeYPlus > mobY && playerRangeYMinus < mobY) {
						
						if(playerCoordsX < mobX) { playerSide = "right"; }
						if(playerCoordsX > mobX) { playerSide = "left"; }
					
						if(playerInfo.target_A.equals(lstMobs.get(i).mobID)) {
							
							playerInfo.inBattle_A = "yes";
							playerStatus = playerInfo.stats_A.split("#");
							playerStatusNumber = playerStatus[2].split(":");
							playerWis = Integer.parseInt(playerStatusNumber[1]);
							playerAtk = Integer.parseInt(playerInfo.atk_A);
							playerAtk = (playerAtk + (playerWis * 4)) * 2;
							if(playerInfo.stamina_A.equals("0")) { playerAtk = 10; }
							mobHP = lstMobs.get(i).hp;
							mobHP = mobHP - playerAtk;
							lstMobs.get(i).target = playerInfo.name_A;
							lstMobs.get(i).getHit = true;
							lstMobs.get(i).hp = mobHP; 
							
							if(lstMobs.get(i).hp <= 0) {  
								GiveLoot(lstMobs.get(i));	
								GiveExp(lstMobs.get(i),"normal",0);
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
							skl.name = "Batida da Furia";
							skl.posX = mobX - 15;
							skl.posY = mobY - 5;
							skl.height = 50;
							skl.width = 50;
							skl.type = "dmg";
							skl.dmg = playerAtk;
							skl.follow = false;
							skl.frame = 1;
							skl.cd = 550;
							lstSkill.add(skl);
							
							skillCoolDown = 500;
							
							if(onlineCheck) { damageOnline = String.valueOf(playerAtk); OnlineManager("Atk",String.valueOf(mobHP)); }	
							
						}					
					}
				}		
			}
			
			//Imponderamento
			if(numSkill == 5){
				playerDef = Integer.parseInt(playerInfo.def_A);
				playerDef = playerDef * 2;
				playerInfo.def_A = String.valueOf(playerDef);
				
				Skill skl = new Skill();
				skl.caster = playerInfo.name_A;
				skl.name = "Imponderamento";
				skl.posX = playerCoordsX - 20;
				skl.posY = playerCoordsY - 10;
				skl.height = 70;
				skl.width = 50;
				skl.type = "heal";
				skl.dmg = 0;
				skl.follow = false;
				skl.frame = 1;
				skl.cd = 50;
				lstSkill.add(skl);

				skillCoolDown = 50;
				
				if(playerInfo.buffsA_A.contains("impound") || playerInfo.buffsB_A.contains("impound") || playerInfo.buffsC_A.contains("impound")) { return; }

				if(playerInfo.buffsA_A.equals("none-0")) { playerInfo.buffsA_A = "impound-800"; return; }
				if(playerInfo.buffsB_A.equals("none-0")) { playerInfo.buffsB_A = "impound-800"; return; }
				if(playerInfo.buffsC_A.equals("none-0")) { playerInfo.buffsC_A = "impound-800"; return; }
			}
		}
	}
	
	public int GetCastTime(int skillNum) {
		if(playerInfo.job_A.equals("Mage")) {
			
			isCasting = true;
			
			//Fireball
			if(skillNum == 1) {
				return 100;
			}
			//icecrystal
			if(skillNum == 2) {
				return 350;
			}
			//thundercloud
			if(skillNum == 3) {
				return 400;
			}
			//rockbound
			if(skillNum == 4) {
				return 200;
			}
			//soulcrash 
			if(skillNum == 5) {
				return 50;
			}	
		}
		
		if(playerInfo.job_A.equals("Medic")) {
			isCasting = true;
			
			//Heal
			if(skillNum == 1) {
				return 100;
			}
			//AtkBoost
			if(skillNum == 2) {
				return 50;
			}
			//DefBoost
			if(skillNum == 3) {
				return 50;
			}
			//Holyprism
			if(skillNum == 4) {
				return 200;
			}
			//Regen 
			if(skillNum == 5) {
				return 50;
			}
		}
		
		return 0;
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
		
		if(skillUsed.name.equals("Aumento de Vida")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_healthboost.createSprite("healthboost1");  } 
			if(frame >= 10 && frame <= 20) { spr_master = atlas_healthboost.createSprite("healthboost2"); }
			if(frame >= 20 && frame <= 30) { spr_master = atlas_healthboost.createSprite("healthboost3"); }
			if(frame >= 30 && frame <= 40) { spr_master = atlas_healthboost.createSprite("healthboost4"); }
			if(frame >= 40 && frame <= 50) { spr_master = atlas_healthboost.createSprite("healthboost5"); }
			if(frame >= 50 && frame <= 60) { spr_master = atlas_healthboost.createSprite("healthboost6"); }
			
			spr_master.setPosition(skillUsed.posX - 12, skillUsed.posY - 7);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		if(skillUsed.name.equals("Escudo de Ferro")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_ironshield.createSprite("ironshield1");  } 
			if(frame >= 10 && frame <= 20) { spr_master = atlas_ironshield.createSprite("ironshield2"); }
			if(frame >= 20 && frame <= 30) { spr_master = atlas_ironshield.createSprite("ironshield3"); }
			if(frame >= 30 && frame <= 40) { spr_master = atlas_ironshield.createSprite("ironshield4"); }
			if(frame >= 40 && frame <= 50) { spr_master = atlas_ironshield.createSprite("ironshield5"); }
			if(frame >= 50 && frame <= 60) { spr_master = atlas_ironshield.createSprite("ironshield6"); }
			
			spr_master.setPosition(skillUsed.posX - 12, skillUsed.posY - 7);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		if(skillUsed.name.equals("Protecao")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_protect.createSprite("protect1");  } 
			if(frame >= 10 && frame <= 20) { spr_master = atlas_protect.createSprite("protect2"); }
			if(frame >= 20 && frame <= 30) { spr_master = atlas_protect.createSprite("protect3"); }
			if(frame >= 30 && frame <= 40) { spr_master = atlas_protect.createSprite("protect4"); }
			if(frame >= 40 && frame <= 50) { spr_master = atlas_protect.createSprite("protect5"); }
			if(frame >= 50 && frame <= 60) { spr_master = atlas_protect.createSprite("protect6"); }
			
			spr_master.setPosition(skillUsed.posX - 12, skillUsed.posY - 7);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		if(skillUsed.name.equals("Bola de Fogo")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_fireball.createSprite("fireball1");  } 
			if(frame >= 10 && frame <= 20) { spr_master = atlas_fireball.createSprite("fireball2"); }
			if(frame >= 20 && frame <= 30) { spr_master = atlas_fireball.createSprite("fireball3"); }
			if(frame >= 30 && frame <= 40) { spr_master = atlas_fireball.createSprite("fireball4"); }
			if(frame >= 40 && frame <= 50) { spr_master = atlas_fireball.createSprite("fireball5"); }
			if(frame >= 50 && frame <= 60) { spr_master = atlas_fireball.createSprite("fireball6"); }
			
			if(skillUsed.posX < skillUsed.posMobX) { skillUsed.posX = skillUsed.posX + 0.9f; }
			if(skillUsed.posX > skillUsed.posMobX) { skillUsed.posX = skillUsed.posX - 0.9f; }
			if(skillUsed.posY < skillUsed.posMobY) { skillUsed.posY = skillUsed.posY + 0.9f; }
			if(skillUsed.posY > skillUsed.posMobY) { skillUsed.posY = skillUsed.posY - 0.9f; }
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		if(skillUsed.name.equals("Cristal de Gelo")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_icecrystal.createSprite("icecrystal1");  } 
			if(frame >= 10 && frame <= 20) { spr_master = atlas_icecrystal.createSprite("icecrystal2"); }
			if(frame >= 20 && frame <= 30) { spr_master = atlas_icecrystal.createSprite("icecrystal3"); }
			if(frame >= 30 && frame <= 40) { spr_master = atlas_icecrystal.createSprite("icecrystal4"); }
			if(frame >= 40 && frame <= 50) { spr_master = atlas_icecrystal.createSprite("icecrystal5"); }
			if(frame >= 50 && frame <= 60) { spr_master = atlas_icecrystal.createSprite("icecrystal6"); }
			
			spr_master.setPosition(skillUsed.posX - 12, skillUsed.posY - 7);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		if(skillUsed.name.equals("Tempestade")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_thundercloud.createSprite("thundercloud1");  } 
			if(frame >= 10 && frame <= 20) { spr_master = atlas_thundercloud.createSprite("thundercloud2"); }
			if(frame >= 20 && frame <= 30) { spr_master = atlas_thundercloud.createSprite("thundercloud3"); }
			if(frame >= 30 && frame <= 40) { spr_master = atlas_thundercloud.createSprite("thundercloud4"); }
			if(frame >= 40 && frame <= 50) { spr_master = atlas_thundercloud.createSprite("thundercloud5"); }
			if(frame >= 50 && frame <= 60) { spr_master = atlas_thundercloud.createSprite("thundercloud6"); }
			
			spr_master.setPosition(skillUsed.posX - 12, skillUsed.posY - 7);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		if(skillUsed.name.equals("Terremoto")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_rockbound.createSprite("rockbound1");  } 
			if(frame >= 10 && frame <= 20) { spr_master = atlas_rockbound.createSprite("rockbound2"); }
			if(frame >= 20 && frame <= 30) { spr_master = atlas_rockbound.createSprite("rockbound3"); }
			if(frame >= 30 && frame <= 40) { spr_master = atlas_rockbound.createSprite("rockbound4"); }
			if(frame >= 40 && frame <= 50) { spr_master = atlas_rockbound.createSprite("rockbound5"); }
			if(frame >= 50 && frame <= 60) { spr_master = atlas_rockbound.createSprite("rockbound6"); }
			
			spr_master.setPosition(skillUsed.posX - 12, skillUsed.posY - 7);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		if(skillUsed.name.equals("Fantasmagorica")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_soulclash.createSprite("soulclash1");  } 
			if(frame >= 10 && frame <= 20) { spr_master = atlas_soulclash.createSprite("soulclash2"); }
			if(frame >= 20 && frame <= 30) { spr_master = atlas_soulclash.createSprite("soulclash3"); }
			if(frame >= 30 && frame <= 40) { spr_master = atlas_soulclash.createSprite("soulclash4"); }
			if(frame >= 40 && frame <= 50) { spr_master = atlas_soulclash.createSprite("soulclash5"); }
			if(frame >= 50 && frame <= 60) { spr_master = atlas_soulclash.createSprite("soulclash6"); }
			
			if(skillUsed.posX < skillUsed.posMobX) { skillUsed.posX = skillUsed.posX + 0.9f; }
			if(skillUsed.posX > skillUsed.posMobX) { skillUsed.posX = skillUsed.posX - 0.9f; }
			if(skillUsed.posY < skillUsed.posMobY) { skillUsed.posY = skillUsed.posY + 0.9f; }
			if(skillUsed.posY > skillUsed.posMobY) { skillUsed.posY = skillUsed.posY - 0.9f; }
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		if(skillUsed.name.equals("Golpe Duplo")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_doublehit.createSprite("doublehit1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_doublehit.createSprite("doublehit2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_doublehit.createSprite("doublehit3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_doublehit.createSprite("doublehit4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_doublehit.createSprite("doublehit5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_doublehit.createSprite("doublehit6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		if(skillUsed.name.equals("Chute Rapido")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_dashkick.createSprite("dashkick1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_dashkick.createSprite("dashkick2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_dashkick.createSprite("dashkick3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_dashkick.createSprite("dashkick4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_dashkick.createSprite("dashkick5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_dashkick.createSprite("dashkick6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}  
		
		if(skillUsed.name.equals("Roubar")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_steal.createSprite("steal1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_steal.createSprite("steal2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_steal.createSprite("steal3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_steal.createSprite("steal4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_steal.createSprite("steal5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_steal.createSprite("steal6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		if(skillUsed.name.equals("Envenenar")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_poisonhit.createSprite("poisonhit1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_poisonhit.createSprite("poisonhit2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_poisonhit.createSprite("poisonhit3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_poisonhit.createSprite("poisonhit4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_poisonhit.createSprite("poisonhit5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_poisonhit.createSprite("poisonhit6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		if(skillUsed.name.equals("Invisibilidade")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_invisibility.createSprite("invisibility1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_invisibility.createSprite("invisibility2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_invisibility.createSprite("invisibility3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_invisibility.createSprite("invisibility4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_invisibility.createSprite("invisibility5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_invisibility.createSprite("invisibility6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		if(skillUsed.name.equals("Curar")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_heal.createSprite("heal1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_heal.createSprite("heal2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_heal.createSprite("heal3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_heal.createSprite("heal4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_heal.createSprite("heal5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_heal.createSprite("heal6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		//Aumentar Ataque
		if(skillUsed.name.equals("Aumentar Ataque")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_atkboost.createSprite("atkboost1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_atkboost.createSprite("atkboost2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_atkboost.createSprite("atkboost3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_atkboost.createSprite("atkboost4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_atkboost.createSprite("atkboost5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_atkboost.createSprite("atkboost6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		//Aumentar Defesa
		if(skillUsed.name.equals("Aumentar Defesa")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_defboost.createSprite("defboost1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_defboost.createSprite("defboost2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_defboost.createSprite("defboost3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_defboost.createSprite("defboost4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_defboost.createSprite("defboost5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_defboost.createSprite("defboost6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		//Prisma Sagrado
		if(skillUsed.name.equals("Prisma Sagrado")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_holyprism.createSprite("holyprism1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_holyprism.createSprite("holyprism2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_holyprism.createSprite("holyprism3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_holyprism.createSprite("holyprism4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_holyprism.createSprite("holyprism5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_holyprism.createSprite("holyprism6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		//Regenerar
		if(skillUsed.name.equals("Regenerar")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_regen.createSprite("regen1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_regen.createSprite("regen2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_regen.createSprite("regen3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_regen.createSprite("regen4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_regen.createSprite("regen5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_regen.createSprite("regen6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		//Tiro Rapido
		if(skillUsed.name.equals("Tiro Rapido")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_fastshot.createSprite("fastshot1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_fastshot.createSprite("fastshot2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_fastshot.createSprite("fastshot3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_fastshot.createSprite("fastshot4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_fastshot.createSprite("fastshot5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_fastshot.createSprite("fastshot6");	}
			
			if(skillUsed.posX < skillUsed.posMobX) { skillUsed.posX = skillUsed.posX + 0.9f; }
			if(skillUsed.posX > skillUsed.posMobX) { skillUsed.posX = skillUsed.posX - 0.9f; }
			if(skillUsed.posY < skillUsed.posMobY) { skillUsed.posY = skillUsed.posY + 0.9f; }
			if(skillUsed.posY > skillUsed.posMobY) { skillUsed.posY = skillUsed.posY - 0.9f; }
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		//Chuva de Balas
		if(skillUsed.name.equals("Chuva de balas")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_bulletrain.createSprite("bulletrain1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_bulletrain.createSprite("bulletrain2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_bulletrain.createSprite("bulletrain3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_bulletrain.createSprite("bulletrain4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_bulletrain.createSprite("bulletrain5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_bulletrain.createSprite("bulletrain6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		//Precisao
		if(skillUsed.name.equals("Precisao")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_precision.createSprite("precision1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_precision.createSprite("precision2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_precision.createSprite("precision3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_precision.createSprite("precision4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_precision.createSprite("precision5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_precision.createSprite("precision6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		//Tiro mirado
		if(skillUsed.name.equals("Tiro Mirado")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_lockshot.createSprite("lockshot1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_lockshot.createSprite("lockshot2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_lockshot.createSprite("lockshot3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_lockshot.createSprite("lockshot4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_lockshot.createSprite("lockshot5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_lockshot.createSprite("lockshot6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		//Mina
		if(skillUsed.name.equals("Mina")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_mine.createSprite("mine1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_mine.createSprite("mine2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_mine.createSprite("mine3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_mine.createSprite("mine4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_mine.createSprite("mine5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_mine.createSprite("mine6");	}
			
			if(skillUsed.posX < skillUsed.posMobX) { skillUsed.posX = skillUsed.posX + 0.9f; }
			if(skillUsed.posX > skillUsed.posMobX) { skillUsed.posX = skillUsed.posX - 0.9f; }
			if(skillUsed.posY < skillUsed.posMobY) { skillUsed.posY = skillUsed.posY + 0.9f; }
			if(skillUsed.posY > skillUsed.posMobY) { skillUsed.posY = skillUsed.posY - 0.9f; }
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		
		if(skillUsed.name.equals("Batida do Martelo")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_hammercrash.createSprite("hammercrash1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_hammercrash.createSprite("hammercrash2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_hammercrash.createSprite("hammercrash3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_hammercrash.createSprite("hammercrash4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_hammercrash.createSprite("hammercrash5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_hammercrash.createSprite("hammercrash6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		if(skillUsed.name.equals("Furia")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_berserk.createSprite("berserk1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_berserk.createSprite("berserk2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_berserk.createSprite("berserk3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_berserk.createSprite("berserk4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_berserk.createSprite("berserk5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_berserk.createSprite("berserk6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		if(skillUsed.name.equals("Super Poder")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_overpower.createSprite("overpower1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_overpower.createSprite("overpower2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_overpower.createSprite("overpower3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_overpower.createSprite("overpower4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_overpower.createSprite("overpower5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_overpower.createSprite("overpower6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
		
		if(skillUsed.name.equals("Batida da Furia")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_ragebound.createSprite("ragebound1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_ragebound.createSprite("ragebound2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_ragebound.createSprite("ragebound3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_ragebound.createSprite("ragebound4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_ragebound.createSprite("ragebound5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_ragebound.createSprite("ragebound6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
			
		if(skillUsed.name.equals("Imponderamento")) {
			if(frame >= 1 && frame <= 10) { spr_master = atlas_impound.createSprite("impound1");	}
			if(frame >= 10 && frame <= 20) { spr_master = atlas_impound.createSprite("impound2");	}
			if(frame >= 20 && frame <= 30) { spr_master = atlas_impound.createSprite("impound3");	}
			if(frame >= 30 && frame <= 40) { spr_master = atlas_impound.createSprite("impound4");	}
			if(frame >= 40 && frame <= 50) { spr_master = atlas_impound.createSprite("impound5");	}
			if(frame >= 50 && frame <= 60) { spr_master = atlas_impound.createSprite("impound6");	}
			
			spr_master.setPosition(skillUsed.posX, skillUsed.posY);
			spr_master.setSize(skillUsed.width, skillUsed.height);
		}
				
		return spr_master;
	}
	
	public void CleanBuffEffects() {
		String[] buff;
		String buffname;
		int duration;
		int stats; 
		
		//Buff A
		buff = playerInfo.buffsA_A.split("-");
		buffname = buff[0];
		duration = Integer.parseInt(buff[1]);
		if(buffname.equals("healthboost") && (duration <= 0)) { playerInfo.hp_A = playerInfo.maxhp_A; }
		if(buffname.equals("invisibility") && (duration <= 0)) {  }
		if(buffname.equals("atkboost") && (duration <= 0)) { playerAtk = Integer.parseInt(playerInfo.atk_A); playerAtk = playerAtk - 30; playerInfo.atk_A = String.valueOf(playerAtk);  }
		if(buffname.equals("defboost") && (duration <= 0)) { playerDef = Integer.parseInt(playerInfo.def_A); playerDef = playerDef - 30; playerInfo.def_A = String.valueOf(playerDef);  }
		if(buffname.equals("regen") && (duration <= 0)) {  }
		if(buffname.equals("impound") && (duration <= 0)) { playerDef = Integer.parseInt(playerInfo.def_A); playerAtk = playerDef / 2; playerInfo.def_A = String.valueOf(playerDef);  }
		if(buffname.equals("overpower") && (duration <= 0)) { playerInfo.hp_A = playerInfo.maxhp_A; playerAtk = Integer.parseInt(playerInfo.atk_A); playerAtk = playerAtk / 2; playerInfo.atk_A = String.valueOf(playerAtk); }
		if(buffname.equals("protect") && (duration <= 0)) {  }
		if(buffname.equals("ironshield") && (duration <= 0)) {  }
		if(buffname.equals("precision") && (duration <= 0)) {  }
		
		//Buff B
		buff = playerInfo.buffsB_A.split("-");
		buffname = buff[0];
		duration = Integer.parseInt(buff[1]);
		if(buffname.equals("healthboost") && (duration <= 0)) { playerInfo.hp_A = playerInfo.maxhp_A; }
		if(buffname.equals("invisibility") && (duration <= 0)) {  }
		if(buffname.equals("atkboost") && (duration <= 0)) { playerAtk = Integer.parseInt(playerInfo.atk_A); playerAtk = playerAtk - 30; playerInfo.atk_A = String.valueOf(playerAtk);  }
		if(buffname.equals("defboost") && (duration <= 0)) { playerDef = Integer.parseInt(playerInfo.def_A); playerDef = playerDef - 30; playerInfo.def_A = String.valueOf(playerDef);  }
		if(buffname.equals("regen") && (duration <= 0)) {  }
		if(buffname.equals("impound") && (duration <= 0)) { playerDef = Integer.parseInt(playerInfo.def_A); playerAtk = playerDef / 2; playerInfo.def_A = String.valueOf(playerDef);  }
		if(buffname.equals("overpower") && (duration <= 0)) { playerInfo.hp_A = playerInfo.maxhp_A; playerAtk = Integer.parseInt(playerInfo.atk_A); playerAtk = playerAtk / 2; playerInfo.atk_A = String.valueOf(playerAtk); }
		if(buffname.equals("protect") && (duration <= 0)) {  }
		if(buffname.equals("ironshield") && (duration <= 0)) {  }
		if(buffname.equals("precision") && (duration <= 0)) {  }
		
		//Buff C
		buff = playerInfo.buffsC_A.split("-");
		buffname = buff[0];
		duration = Integer.parseInt(buff[1]);
		if(buffname.equals("healthboost") && (duration <= 0)) { playerInfo.hp_A = playerInfo.maxhp_A; }
		if(buffname.equals("invisibility") && (duration <= 0)) {  }
		if(buffname.equals("atkboost") && (duration <= 0)) { playerAtk = Integer.parseInt(playerInfo.atk_A); playerAtk = playerAtk - 30; playerInfo.atk_A = String.valueOf(playerAtk);  }
		if(buffname.equals("defboost") && (duration <= 0)) { playerDef = Integer.parseInt(playerInfo.def_A); playerDef = playerDef - 30; playerInfo.def_A = String.valueOf(playerDef);  }
		if(buffname.equals("regen") && (duration <= 0)) {  }
		if(buffname.equals("impound") && (duration <= 0)) { playerDef = Integer.parseInt(playerInfo.def_A); playerAtk = playerDef / 2; playerInfo.def_A = String.valueOf(playerDef);  }
		if(buffname.equals("overpower") && (duration <= 0)) { playerInfo.hp_A = playerInfo.maxhp_A; playerAtk = Integer.parseInt(playerInfo.atk_A); playerAtk = playerAtk / 2; playerInfo.atk_A = String.valueOf(playerAtk); }
		if(buffname.equals("protect") && (duration <= 0)) {  }
		if(buffname.equals("ironshield") && (duration <= 0)) {  }
		if(buffname.equals("precision") && (duration <= 0)) {  }
	}
	
	public void CleanBuffEffectsLog() {
		String[] buff;
		String buffname;
		
		buff = playerInfo.buffsA_A.split("-");
		buffname = buff[0];
		playerInfo.buffsA_A = buffname + "-" + "0";
		
		buff = playerInfo.buffsA_A.split("-");
		buffname = buff[0];
		playerInfo.buffsB_A = buffname + "-" + "0";
		
		buff = playerInfo.buffsA_A.split("-");
		buffname = buff[0];
		playerInfo.buffsC_A = buffname + "-" + "0";
		
		BuffEffectDuration();
		
	}
	
	public void BuffEffectDuration() {
		
		if(playerInfo.buffsA_A.equals("none-0") && playerInfo.buffsB_A.equals("none-0") && playerInfo.buffsC_A.equals("none-0")) {
			return;
		}
			
		String[] buff;
		String buffname;
		int duration;
		
		//Buff A
		buff = playerInfo.buffsA_A.split("-");
		buffname = buff[0];
		duration = Integer.parseInt(buff[1]);
		if(duration > 0) { duration--; playerInfo.buffsA_A = buffname + "-" + duration; }
		if(duration <= 0) { CleanBuffEffects(); duration = 0; buffname = "none"; playerInfo.buffsA_A = buffname + "-" + duration; }
		
		//Buff B
		buff = playerInfo.buffsB_A.split("-");
		buffname = buff[0];
		duration = Integer.parseInt(buff[1]);
		if(duration > 0) { duration--; playerInfo.buffsB_A = buffname + "-" + duration; }
		if(duration <= 0) { CleanBuffEffects(); duration = 0; buffname = "none"; playerInfo.buffsB_A = buffname + "-" + duration; }
		
		//Buff C
		buff = playerInfo.buffsC_A.split("-");
		buffname = buff[0];
		duration = Integer.parseInt(buff[1]);
		if(duration > 0) { duration--; playerInfo.buffsC_A = buffname + "-" + duration; }
		if(duration <= 0) { CleanBuffEffects(); duration = 0; buffname = "none"; playerInfo.buffsC_A = buffname + "-" + duration; }
		
	}
	
	public Sprite ReturnIconBuffs(int numBuff) {
		
		String[] buff;
		String buffname;
		
		if(numBuff == 1) {
			buff = playerInfo.buffsA_A.split("-");
			buffname = buff[0];
			if(buffname.equals("none")) { return null; }
			spr_master = atlas_iconSkills.createSprite("btn" + buffname);
			spr_master.setPosition(cameraCoordsX - 33, cameraCoordsY + 88);
			spr_master.setSize(5, 7);
		}
		
		if(numBuff == 2) {
			buff = playerInfo.buffsB_A.split("-");
			buffname = buff[0];
			if(buffname.equals("none")) { return null; }
			spr_master = atlas_iconSkills.createSprite("btn" + buffname);
			spr_master.setPosition(cameraCoordsX - 33, cameraCoordsY + 80);
			spr_master.setSize(5, 7);
		}
		
		if(numBuff == 3) {
			buff = playerInfo.buffsC_A.split("-");
			buffname = buff[0];
			if(buffname.equals("none")) { return null; }
			spr_master = atlas_iconSkills.createSprite("btn" + buffname);
			spr_master.setPosition(cameraCoordsX - 33, cameraCoordsY + 72);
			spr_master.setSize(5, 7);
		}
		
		
		return spr_master;
	}
	
	public void RespawnMob() {
		
		for(int i = 0; i < lstMobs.size(); i++) {
		
			//lstMobs.get(i).mobPosY = -26;
			//lstMobs.get(i).mobPosX = 59;
			
			if(lstMobs.get(i).dead == true) {
				
				lstMobs.get(i).mobPosX = 409;
				lstMobs.get(i).mobPosY = 400;
				lstMobs.get(i).target = "none";
				lstMobs.get(i).dead = true;
				lstMobs.get(i).respawnTime = lstMobs.get(i).respawnTime - 1;
			}
			
			if(lstMobs.get(i).respawnTime <= 0 && lstMobs.get(i).dead == true) {
				randomCount = randnumber.nextInt(30);			
				lstMobs.get(i).mobPosX = randomCount;
				
				randomCount = randnumber.nextInt(30);
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
			if(randomCount >= 50 && randomCount <= 70) {
				lootItemName = mob.loot1;
				countLootShowing = 200;
				AddItemBag(lootItemName);
			}
			if(randomCount >= 70 && randomCount <= 99) {
				lootItemName = mob.loot2;
				countLootShowing = 200;
				AddItemBag(lootItemName);
			}
			if(randomCount >= 99 && randomCount <= 100) {
				lootItemName = mob.loot3;
				countLootShowing = 200;
				AddItemBag(lootItemName);
			}
		}
		else {
			lootItemName = "none";
			countLootShowing = 0;
		}
	}
	
	public void GiveExp(Monster mob, String typeExp, int expShared) {
		
		boolean levelup = false;
		int playerExp = Integer.parseInt(playerInfo.exp_A);
		
		if(typeExp.equals("normal")) { playerExp = playerExp + mob.exp; } 
		if(typeExp.equals("partyshared")) { playerExp = playerExp + expShared; }
		int point = Integer.parseInt(playerInfo.statusPoint_A);
		
		playerInfo.exp_A = String.valueOf(playerExp);
		
		if(playerInfo.level_A.equals("1") && playerExp >= 100) { playerInfo.level_A = "2"; playerInfo.exp_A = "0"; levelup = true; point = point + 2; }
		if(playerInfo.level_A.equals("2") && playerExp >= 230) { playerInfo.level_A = "3"; playerInfo.exp_A = "0"; levelup = true; point = point + 2; }
		if(playerInfo.level_A.equals("3") && playerExp >= 410) { playerInfo.level_A = "4"; playerInfo.exp_A = "0"; levelup = true; point = point + 2; }
		if(playerInfo.level_A.equals("4") && playerExp >= 650) { playerInfo.level_A = "5"; playerInfo.exp_A = "0"; levelup = true; point = point + 2; }
		if(playerInfo.level_A.equals("5") && playerExp >= 810) { playerInfo.level_A = "6"; playerInfo.exp_A = "0"; levelup = true; point = point + 2; }
		if(playerInfo.level_A.equals("6") && playerExp >= 1340) { playerInfo.level_A = "7"; playerInfo.exp_A = "0"; levelup = true; point = point + 2; }
		if(playerInfo.level_A.equals("7") && playerExp >= 1780) { playerInfo.level_A = "8"; playerInfo.exp_A = "0"; levelup = true; point = point + 2; }
		if(playerInfo.level_A.equals("8") && playerExp >= 2420) { playerInfo.level_A = "9"; playerInfo.exp_A = "0"; levelup = true; point = point + 2; }
		if(playerInfo.level_A.equals("9") && playerExp >= 3700) { playerInfo.level_A = "10"; playerInfo.exp_A = "0"; levelup = true; point = point + 2; }
		if(playerInfo.level_A.equals("10") && playerExp >= 10200) { playerInfo.level_A = "11"; playerInfo.exp_A = "0"; levelup = true; point = point + 3; }
		if(playerInfo.level_A.equals("11") && playerExp >= 12500) { playerInfo.level_A = "12"; playerInfo.exp_A = "0"; levelup = true; point = point + 3; }
		if(playerInfo.level_A.equals("12") && playerExp >= 15700) { playerInfo.level_A = "13"; playerInfo.exp_A = "0"; levelup = true; point = point + 3; }
		if(playerInfo.level_A.equals("13") && playerExp >= 19800) { playerInfo.level_A = "14"; playerInfo.exp_A = "0"; levelup = true; point = point + 3; }
		if(playerInfo.level_A.equals("14") && playerExp >= 22500) { playerInfo.level_A = "15"; playerInfo.exp_A = "0"; levelup = true; point = point + 3; }
		if(playerInfo.level_A.equals("15") && playerExp >= 27800) { playerInfo.level_A = "16"; playerInfo.exp_A = "0"; levelup = true; point = point + 3; }
		if(playerInfo.level_A.equals("16") && playerExp >= 32500) { playerInfo.level_A = "17"; playerInfo.exp_A = "0"; levelup = true; point = point + 3; }
		if(playerInfo.level_A.equals("17") && playerExp >= 35900) { playerInfo.level_A = "18"; playerInfo.exp_A = "0"; levelup = true; point = point + 3; }
		if(playerInfo.level_A.equals("18") && playerExp >= 39010) { playerInfo.level_A = "19"; playerInfo.exp_A = "0"; levelup = true; point = point + 3; }
		if(playerInfo.level_A.equals("19") && playerExp >= 45020) { playerInfo.level_A = "20"; playerInfo.exp_A = "0"; levelup = true; point = point + 3; }
		if(playerInfo.level_A.equals("20") && playerExp >= 78000) { playerInfo.level_A = "21"; playerInfo.exp_A = "0"; levelup = true; point = point + 3; }
		if(playerInfo.level_A.equals("21") && playerExp >= 79500) { playerInfo.level_A = "22"; playerInfo.exp_A = "0"; levelup = true; point = point + 3; }
		if(playerInfo.level_A.equals("22") && playerExp >= 83222) { playerInfo.level_A = "23"; playerInfo.exp_A = "0"; levelup = true; point = point + 3; }
		if(playerInfo.level_A.equals("23") && playerExp >= 100) { playerInfo.level_A = "24"; playerInfo.exp_A = "0"; levelup = true; point = point + 4; }
		if(playerInfo.level_A.equals("24") && playerExp >= 100) { playerInfo.level_A = "25"; playerInfo.exp_A = "0"; levelup = true; point = point + 4; }
		if(playerInfo.level_A.equals("25") && playerExp >= 100) { playerInfo.level_A = "26"; playerInfo.exp_A = "0"; levelup = true; point = point + 4; }
		if(playerInfo.level_A.equals("26") && playerExp >= 100) { playerInfo.level_A = "27"; playerInfo.exp_A = "0"; levelup = true; point = point + 4; }
		if(playerInfo.level_A.equals("27") && playerExp >= 100) { playerInfo.level_A = "28"; playerInfo.exp_A = "0"; levelup = true; point = point + 4; }
		if(playerInfo.level_A.equals("28") && playerExp >= 100) { playerInfo.level_A = "29"; playerInfo.exp_A = "0"; levelup = true; point = point + 4; }
		if(playerInfo.level_A.equals("29") && playerExp >= 100) { playerInfo.level_A = "30"; playerInfo.exp_A = "0"; levelup = true; point = point + 4; }
		if(playerInfo.level_A.equals("30") && playerExp >= 100) { playerInfo.level_A = "31"; playerInfo.exp_A = "0"; levelup = true; point = point + 4; }
		if(playerInfo.level_A.equals("31") && playerExp >= 100) { playerInfo.level_A = "32"; playerInfo.exp_A = "0"; levelup = true; point = point + 4; }
		if(playerInfo.level_A.equals("32") && playerExp >= 100) { playerInfo.level_A = "33"; playerInfo.exp_A = "0"; levelup = true; point = point + 4; }
		if(playerInfo.level_A.equals("33") && playerExp >= 100) { playerInfo.level_A = "34"; playerInfo.exp_A = "0"; levelup = true; point = point + 4; }
		if(playerInfo.level_A.equals("34") && playerExp >= 100) { playerInfo.level_A = "35"; playerInfo.exp_A = "0"; levelup = true; point = point + 4; }
		if(playerInfo.level_A.equals("35") && playerExp >= 100) { playerInfo.level_A = "36"; playerInfo.exp_A = "0"; levelup = true; point = point + 4; }
		if(playerInfo.level_A.equals("36") && playerExp >= 100) { playerInfo.level_A = "37"; playerInfo.exp_A = "0"; levelup = true; point = point + 4; }
		if(playerInfo.level_A.equals("37") && playerExp >= 100) { playerInfo.level_A = "38"; playerInfo.exp_A = "0"; levelup = true; point = point + 4; }
		if(playerInfo.level_A.equals("38") && playerExp >= 100) { playerInfo.level_A = "39"; playerInfo.exp_A = "0"; levelup = true; point = point + 4; }
		if(playerInfo.level_A.equals("39") && playerExp >= 100) { playerInfo.level_A = "40"; playerInfo.exp_A = "0"; levelup = true; point = point + 4; }
		if(playerInfo.level_A.equals("40") && playerExp >= 100) { playerInfo.level_A = "41"; playerInfo.exp_A = "0"; levelup = true; point = point + 5; }
		if(playerInfo.level_A.equals("41") && playerExp >= 100) { playerInfo.level_A = "42"; playerInfo.exp_A = "0"; levelup = true; point = point + 5; }
		if(playerInfo.level_A.equals("42") && playerExp >= 100) { playerInfo.level_A = "43"; playerInfo.exp_A = "0"; levelup = true; point = point + 5; }
		if(playerInfo.level_A.equals("43") && playerExp >= 100) { playerInfo.level_A = "44"; playerInfo.exp_A = "0"; levelup = true; point = point + 5; }
		if(playerInfo.level_A.equals("44") && playerExp >= 100) { playerInfo.level_A = "45"; playerInfo.exp_A = "0"; levelup = true; point = point + 5; }
		if(playerInfo.level_A.equals("45") && playerExp >= 100) { playerInfo.level_A = "46"; playerInfo.exp_A = "0"; levelup = true; point = point + 5; }
		if(playerInfo.level_A.equals("46") && playerExp >= 100) { playerInfo.level_A = "47"; playerInfo.exp_A = "0"; levelup = true; point = point + 5; }
		if(playerInfo.level_A.equals("47") && playerExp >= 100) { playerInfo.level_A = "48"; playerInfo.exp_A = "0"; levelup = true; point = point + 5; }
		if(playerInfo.level_A.equals("48") && playerExp >= 100) { playerInfo.level_A = "49"; playerInfo.exp_A = "0"; levelup = true; point = point + 5; }
		if(playerInfo.level_A.equals("49") && playerExp >= 100) { playerInfo.level_A = "50"; playerInfo.exp_A = "0"; levelup = true; point = point + 5; }
		
		if(levelup) {
			
			CleanBuffEffects();	
			playerInfo.statusPoint_A = String.valueOf(point);
			if(playerInfo.job_1.equals("Novice")) {
				playerAtk = Integer.parseInt(playerInfo.atk_A);  
				playerAtk = playerAtk + 2;
				playerInfo.atk_A = String.valueOf(playerAtk);
				playerHPMax = Integer.parseInt(playerInfo.maxhp_A);
				playerHPMax = playerHPMax + 10;
				playerInfo.maxhp_A = String.valueOf(playerHPMax);
				levelup = false;
			}
			
			if(playerInfo.job_1.equals("Swordman")) {
				playerAtk = Integer.parseInt(playerInfo.atk_A);
				playerAtk = playerAtk + 2;
				playerInfo.atk_A = String.valueOf(playerAtk);
				playerDef = Integer.parseInt(playerInfo.def_A);
				playerDef = playerDef + 2;
				playerInfo.def_A = String.valueOf(playerDef);
				playerHPMax = Integer.parseInt(playerInfo.maxhp_A);
				playerHPMax = playerHPMax + 30;
				playerInfo.maxhp_A = String.valueOf(playerHPMax);
				levelup = false;
			}
			
			if(playerInfo.job_1.equals("Mage")) {
				playerDef = Integer.parseInt(playerInfo.def_A);
				playerDef = playerDef + 1;
				playerInfo.atk_A = String.valueOf(playerDef);
				playerMPMax = Integer.parseInt(playerInfo.maxmp_A);
				playerMPMax = playerMPMax + 40;
				playerInfo.maxhp_A = String.valueOf(playerHPMax);
				levelup = false;
			}
			
			if(playerInfo.job_1.equals("Thief")) {
				playerAtk = Integer.parseInt(playerInfo.atk_A);
				playerAtk = playerAtk + 1;
				playerInfo.atk_A = String.valueOf(playerAtk);
				playerDef = Integer.parseInt(playerInfo.def_A);
				playerDef = playerDef + 1;
				playerInfo.def_A = String.valueOf(playerDef);
				playerHPMax = Integer.parseInt(playerInfo.maxhp_A);
				playerHPMax = playerHPMax + 10;
				playerInfo.maxhp_A = String.valueOf(playerHPMax);
				levelup = false;
			}
			
			if(playerInfo.job_1.equals("Medic")) {
				playerDef = Integer.parseInt(playerInfo.def_A);
				playerDef = playerDef + 2;
				playerInfo.def_A = String.valueOf(playerDef);
				playerHPMax = Integer.parseInt(playerInfo.maxhp_A);
				playerHPMax = playerHPMax + 10;
				playerInfo.maxhp_A = String.valueOf(playerHPMax);
				levelup = false;
			}
			if(playerInfo.job_1.equals("Gunner")) {
				playerAtk = Integer.parseInt(playerInfo.atk_A);
				playerAtk = playerAtk + 2;
				playerInfo.atk_A = String.valueOf(playerAtk);
				playerHPMax = Integer.parseInt(playerInfo.maxhp_A);
				playerHPMax = playerHPMax + 20;
				playerInfo.maxhp_A = String.valueOf(playerHPMax);
				levelup = false;
			}
			if(playerInfo.job_1.equals("Beater")) {
				playerAtk = Integer.parseInt(playerInfo.atk_A);
				playerAtk = playerAtk + 5;
				playerInfo.atk_A = String.valueOf(playerAtk);
				playerHPMax = Integer.parseInt(playerInfo.maxhp_A);
				playerHPMax = playerHPMax + 5;
				playerInfo.maxhp_A = String.valueOf(playerHPMax);
				levelup = false;
			}
		}
		
		if(onlineCheck) { 
			if(!playerInfo.party_A.equals("None") && typeExp.equals("normal")) {
				int sharedpart = mob.exp / 2;
				expsharedOnline = String.valueOf(sharedpart);
				cdExpSharedSend = 15;
			}
		}
	}
	
	public Sprite ShowLootItem(float ccX, float ccY) {
		
		if(countLootShowing > 0) {
			if(lootItemName.equals("HPCAN")) { spr_master = atlas_itens.createSprite("hpcan"); }
			if(lootItemName.equals("MPCAN")) { spr_master = atlas_itens.createSprite("mpcan"); }
			if(lootItemName.equals("STCAN")) { spr_master = atlas_itens.createSprite("stcan"); }
			if(lootItemName.equals("CHIPZ")) { spr_master = atlas_itens.createSprite("chipz"); }
			if(lootItemName.equals("BLOP")) { spr_master = atlas_itens.createSprite("lootblop"); }
			if(lootItemName.equals("POISONLEAf")) { spr_master = atlas_itens.createSprite("lootpoisonleaf"); }
			if(lootItemName.equals("MUSHROOM")) { spr_master = atlas_itens.createSprite("lootmushroom"); }
			if(lootItemName.equals("STRING")) { spr_master = atlas_itens.createSprite("lootfang"); }
			if(lootItemName.equals("STICK")) { spr_master = atlas_itens.createSprite("lootfang"); }
			
			if(lootItemName.equals("HATSLIME")) { spr_master = atlas_itens.createSprite("lootfang"); }
			
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
	
	public Sprite ShowWeaponNovice(float pX, float pY, String walk) {
		if(playerInfo.inBattle_A.equals("yes")) {
			
			if(walk.equals("walk")) {
				return null;
			}
			
			playerWeapon = playerInfo.weapon_A;
			if(playerWeapon.equals("BASICKNIFE")) {
				
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
			
			if(playerWeapon.equals("DOUBLEEDGEKNIFE")) {
				
				if(playerSide.equals("right")) {
					if(exitAnimationFrame == 0) { 
						spr_master = atlas_doubleedgeknife.createSprite("doubleedge_knife_right"); 
						spr_master.setSize(20, 25);
						spr_master.setPosition(pX - 2.5f, pY + 13);
						return spr_master;
					}
					if(exitAnimationFrame > 0) { 
						spr_master = atlas_doubleedgeknife.createSprite("doubleedge_knife_right_attack"); 
						spr_master.setSize(20, 25);
						spr_master.setPosition(pX + 7, pY + 4);
						return spr_master;
					}
				}
				
				if(playerSide.equals("left")) {
					if(exitAnimationFrame == 0) { 
						spr_master = atlas_doubleedgeknife.createSprite("doubleedge_knife_left"); 
						spr_master.setSize(20, 25);
						spr_master.setPosition(pX + 4.4f, pY + 13);
						return spr_master;
					}
					if(exitAnimationFrame > 0) { 
						spr_master = atlas_doubleedgeknife.createSprite("doubleedge_knife_left_attack");  
						spr_master.setSize(20, 25);
						spr_master.setPosition(pX - 5, pY + 4);
						return spr_master;
					}
				}			
			}
		}
		
		return null;
	}
	
	public Sprite ShowWeaponSwordman(float pX, float pY, String walk) {
		if(playerInfo.inBattle_A.equals("yes")) {
			
			if(walk.equals("walk")) {
				return null;
			}
			
			playerWeapon = playerInfo.weapon_A;
			if(playerWeapon.equals("SABERSWORD")) {
				
				if(playerSide.equals("right")) {
					if(exitAnimationFrame == 0) { 
						spr_master = atlas_swords.createSprite("saber_sword_left"); 
						spr_master.setSize(20, 25);
						spr_master.setPosition(pX - 2.5f, pY + 13);
						return spr_master;
					}
					if(exitAnimationFrame > 0) { 
						spr_master = atlas_swords.createSprite("saber_sword_attack_right"); 
						spr_master.setSize(20, 25);
						spr_master.setPosition(pX + 7, pY + 4);
						return spr_master;
					}
				}
				
				if(playerSide.equals("left")) {
					if(exitAnimationFrame == 0) { 
						spr_master = atlas_swords.createSprite("saber_sword_right"); 
						spr_master.setSize(20, 25);
						spr_master.setPosition(pX + 4.4f, pY + 13);
						return spr_master;
					}
					if(exitAnimationFrame > 0) { 
						spr_master = atlas_swords.createSprite("saber_sword_attack_left");  
						spr_master.setSize(20, 25);
						spr_master.setPosition(pX - 5, pY + 4);
						return spr_master;
					}
				}			
			}
		}
		
		return null;
	}
	
	public Sprite ShowWeaponGunner(float pX, float pY, String walk) {
		if(playerInfo.inBattle_A.equals("yes")) {
			
			if(walk.equals("walk")) {
				return null;
			}
			
			playerWeapon = playerInfo.weapon_A;
			if(playerWeapon.equals("REVOLVERPISTOL")) {
				
				if(playerSide.equals("right")) {
					if(exitAnimationFrame == 0) { 
						spr_master = atlas_guns.createSprite("revolver_pistol_attack_right"); 
						spr_master.setSize(15, 20);
						spr_master.setPosition(pX - 2, pY + 9);
						return spr_master;
					}
					if(exitAnimationFrame > 0) { 
						spr_master = atlas_guns.createSprite("revolver_pistol_attack_left"); 
						spr_master.setSize(15, 20);
						spr_master.setPosition(pX + 6, pY + 8);
						return spr_master;
					}
				}
				
				if(playerSide.equals("left")) {
					if(exitAnimationFrame == 0) { 
						spr_master = atlas_guns.createSprite("revolver_pistol_attack_left"); 
						spr_master.setSize(15, 20);
						spr_master.setPosition(pX + 8.4f, pY + 9);
						return spr_master;
					}
					if(exitAnimationFrame > 0) { 
						spr_master = atlas_guns.createSprite("revolver_pistol_attack_right");  
						spr_master.setSize(13, 17);
						spr_master.setPosition(pX + 2, pY + 9);
						return spr_master;
					}
				}			
			}
		}
		
		return null;
	}
	
	public Sprite ShowWeaponThief(float pX, float pY, String walk) {
		if(playerInfo.inBattle_A.equals("yes")) {
			
			if(walk.equals("walk")) {
				return null;
			}
			
			playerWeapon = playerInfo.weapon_A;
			if(playerWeapon.equals("EDGEDAGGER")) {
				
				if(playerSide.equals("right")) {
					if(exitAnimationFrame == 0) { 
						spr_master = atlas_daggers.createSprite("edgedagger_left"); 
						spr_master.setSize(10, 16);
						spr_master.setPosition(pX + 2, pY + 15);
						return spr_master;
					}
					if(exitAnimationFrame > 0) { 
						spr_master = atlas_daggers.createSprite("edgedagger_attack_right"); 
						spr_master.setSize(10, 16);
						spr_master.setPosition(pX + 8, pY + 8);
						return spr_master;
					}
				}
				
				if(playerSide.equals("left")) {
					if(exitAnimationFrame == 0) { 
						spr_master = atlas_daggers.createSprite("edgedagger_right"); 
						spr_master.setSize(10, 16);
						spr_master.setPosition(pX + 10f, pY + 15);
						return spr_master;
					}
					if(exitAnimationFrame > 0) { 
						spr_master = atlas_daggers.createSprite("edgedagger_side_right");  
						spr_master.setSize(10, 16);
						spr_master.setPosition(pX + 2, pY + 9);
						return spr_master;
					}
				}			
			}
		}
		
		return null;
	}
	
	public Sprite ShowWeaponBeater(float pX, float pY, String walk) {
		if(playerInfo.inBattle_A.equals("yes")) {
			
			if(walk.equals("walk")) {
				return null;
			}
			
			playerWeapon = playerInfo.weapon_A;
			if(playerWeapon.equals("HAMMERAXE")) {
				
				if(playerSide.equals("right")) {
					if(exitAnimationFrame == 0) { 
						spr_master = atlas_axes.createSprite("hammer_axe_left"); 
						spr_master.setSize(15, 22);
						spr_master.setPosition(pX - 0.5f, pY + 15);
						return spr_master;
					}
					if(exitAnimationFrame > 0) { 
						spr_master = atlas_axes.createSprite("hammer_axe_right_attack"); 
						spr_master.setSize(15, 22);
						spr_master.setPosition(pX + 7, pY + 4);
						return spr_master;
					}
				}
				
				if(playerSide.equals("left")) {
					if(exitAnimationFrame == 0) { 
						spr_master = atlas_axes.createSprite("hammer_axe_right"); 
						spr_master.setSize(15, 22);
						spr_master.setPosition(pX + 6f, pY + 15);
						return spr_master;
					}
					if(exitAnimationFrame > 0) { 
						spr_master = atlas_axes.createSprite("hammer_axe_attack_left");  
						spr_master.setSize(15, 22);
						spr_master.setPosition(pX - 1, pY + 4);
						return spr_master;
					}
				}				
			}
		}
		
		return null;
	}
	
	public Sprite ShowWeaponCaster(float pX, float pY, String walk) {
		if(playerInfo.inBattle_A.equals("yes")) {
			
			if(walk.equals("walk")) {
				return null;
			}
			
			playerWeapon = playerInfo.weapon_A;
			if(playerWeapon.equals("GLOOMROD")) {
				if(playerSide.equals("right")) {
					if(exitAnimationFrame == 0) { 
						spr_master = atlas_rods.createSprite("gloom_rod_left"); 
						spr_master.setSize(15, 20);
						spr_master.setPosition(pX - 2.5f, pY + 13);
						return spr_master;
					}
					if(exitAnimationFrame > 0) { 
						spr_master = atlas_rods.createSprite("gloom_rod_right"); 
						spr_master.setSize(15, 20);
						spr_master.setPosition(pX + 9, pY + 20);
						return spr_master;
					}
				}
				
				if(playerSide.equals("left")) {
					if(exitAnimationFrame == 0) { 
						spr_master = atlas_rods.createSprite("gloom_rod_right"); 
						spr_master.setSize(15, 20);
						spr_master.setPosition(pX + 8.4f, pY + 13);
						return spr_master;
					}
					if(exitAnimationFrame > 0) { 
						spr_master = atlas_rods.createSprite("gloom_rod_left");  
						spr_master.setSize(15, 20);
						spr_master.setPosition(pX - 3, pY + 20);
						return spr_master;
					}
				}			
			}
		}
		
		return null;
	}
	
	
	//[H] Npcs
	public ArrayList<Sprite> GetNpcsStreets305() {
		
		lstNpcs.clear();
		
		npcFrame++;
		if(npcFrame > 100) { npcFrame = 1; }
		
		//Job Master
		spr_master = atlas_npc.createSprite("NPCS");
		spr_master.setSize(8, 26);
		spr_master.setPosition(22, -53);	
		lstNpcs.add(spr_master);
		
		//StudentBrown			
		if(npcFrame >= 0 && npcFrame <= 25) { spr_master = atlas_npc.createSprite("NPCE"); }
		if(npcFrame >= 25 && npcFrame <= 50) { spr_master = atlas_npc.createSprite("NPCE2"); }
		if(npcFrame >= 50 && npcFrame <= 75) { spr_master = atlas_npc.createSprite("NPCE"); }
		if(npcFrame >= 75 && npcFrame <= 100) { spr_master = atlas_npc.createSprite("NPCE3"); }
		npcFromLeft = npcFromLeft + 0.3f;
		if(npcFromLeft > 249) { npcFromLeft = -110; }
		spr_master.setSize(7, 24);
		spr_master.setPosition(npcFromLeft, -65);	
		lstNpcs.add(spr_master);
		
		//Blond NPC
		if(npcFrame >= 0 && npcFrame <= 25) { spr_master = atlas_npc.createSprite("NPCC"); }
		if(npcFrame >= 25 && npcFrame <= 50) { spr_master = atlas_npc.createSprite("NPCC2"); }
		if(npcFrame >= 50 && npcFrame <= 75) { spr_master = atlas_npc.createSprite("NPCC"); }
		if(npcFrame >= 75 && npcFrame <= 100) { spr_master = atlas_npc.createSprite("NPCC3"); }
		npcFromRight = npcFromRight - 0.3f;
		if(npcFromRight < - 110) { npcFromRight = 249; }
		spr_master.setSize(7, 24);
		spr_master.setPosition(npcFromRight, -70);
		lstNpcs.add(spr_master);
		
		//BusinessMan			
		spr_master = atlas_npc.createSprite("NPCA");
		spr_master.setSize(8, 26);
		spr_master.setPosition(125, -113);	
		lstNpcs.add(spr_master);
		
		//GeekGuy			
		spr_master = atlas_npc.createSprite("NPCU");
		spr_master.setSize(10, 26);
		spr_master.setPosition(169, 67);	
		lstNpcs.add(spr_master);
		
		//CellWoman			
		if(npcFrame >= 0 && npcFrame <= 25) { spr_master = atlas_npc.createSprite("NPCD"); }
		if(npcFrame >= 25 && npcFrame <= 50) { spr_master = atlas_npc.createSprite("NPCD2"); }
		if(npcFrame >= 50 && npcFrame <= 75) { spr_master = atlas_npc.createSprite("NPCD3"); }
		if(npcFrame >= 75 && npcFrame <= 100) { spr_master = atlas_npc.createSprite("NPCD3"); }
		spr_master.setSize(7, 24);
		spr_master.setPosition(156, -98);	
		lstNpcs.add(spr_master);
		
		//FlowerGirl	
		if(npcFrame >= 0 && npcFrame <= 25) { spr_master = atlas_npc.createSprite("NPCB2"); }
		if(npcFrame >= 25 && npcFrame <= 50) { spr_master = atlas_npc.createSprite("NPCB2"); }
		if(npcFrame >= 50 && npcFrame <= 75) { spr_master = atlas_npc.createSprite("NPCB"); }
		if(npcFrame >= 75 && npcFrame <= 100) { spr_master = atlas_npc.createSprite("NPCB"); }
		spr_master.setSize(8, 26);
		spr_master.setPosition(185, -60);	
		lstNpcs.add(spr_master);
		
		//StudantWomanBrown
		if(npcFrame >= 0 && npcFrame <= 25) { spr_master = atlas_npc.createSprite("NPCG"); }
		if(npcFrame >= 25 && npcFrame <= 50) { spr_master = atlas_npc.createSprite("NPCG2"); }
		if(npcFrame >= 50 && npcFrame <= 75) { spr_master = atlas_npc.createSprite("NPCG"); }
		if(npcFrame >= 75 && npcFrame <= 100) { spr_master = atlas_npc.createSprite("NPCG3"); }
		npcFromTop = npcFromTop - 0.3f;
		if(npcFromTop < - 170) { npcFromTop = 190; }
		spr_master.setSize(7, 24);
		spr_master.setPosition(108, npcFromTop);
		lstNpcs.add(spr_master);
		
		//Wallpaperman	
		if(npcFrame >= 0 && npcFrame <= 25) { spr_master = atlas_npc.createSprite("NPCM"); }
		if(npcFrame >= 25 && npcFrame <= 50) { spr_master = atlas_npc.createSprite("NPCM"); }
		if(npcFrame >= 50 && npcFrame <= 75) { spr_master = atlas_npc.createSprite("NPCM2"); }
		if(npcFrame >= 75 && npcFrame <= 100) { spr_master = atlas_npc.createSprite("NPCM2"); }
		spr_master.setSize(8, 26);
		spr_master.setPosition(-7, 69);		
		lstNpcs.add(spr_master);
		
		//BlueShirtGirl			
		if(npcFrame >= 0 && npcFrame <= 25) { spr_master = atlas_npc.createSprite("NPCN"); }
		if(npcFrame >= 25 && npcFrame <= 50) { spr_master = atlas_npc.createSprite("NPCN2"); }
		if(npcFrame >= 50 && npcFrame <= 75) { spr_master = atlas_npc.createSprite("NPCN"); }
		if(npcFrame >= 75 && npcFrame <= 100) { spr_master = atlas_npc.createSprite("NPCN3"); }
		npcFromLeft2 = npcFromLeft2 + 0.3f;
		if(npcFromLeft2 > 249) { npcFromLeft2 = -110; }
		spr_master.setSize(7, 24);
		spr_master.setPosition(npcFromLeft2, 24);	
		lstNpcs.add(spr_master);
		
		//Operator			
		if(npcFrame >= 0 && npcFrame <= 25) { spr_master = atlas_npc.createSprite("NPCL"); }
		if(npcFrame >= 25 && npcFrame <= 50) { spr_master = atlas_npc.createSprite("NPCL2"); }
		if(npcFrame >= 50 && npcFrame <= 75) { spr_master = atlas_npc.createSprite("NPCL"); }
		if(npcFrame >= 75 && npcFrame <= 100) { spr_master = atlas_npc.createSprite("NPCL3"); }
		npcFromLeft3 = npcFromLeft3 + 0.3f;
		if(npcFromLeft3 > 249) { npcFromLeft3 = -110; }
		spr_master.setSize(7, 24);
		spr_master.setPosition(npcFromLeft3 - 45, 32);	
		lstNpcs.add(spr_master);
		
		//GreenGirl
		if(npcFrame >= 0 && npcFrame <= 25) { spr_master = atlas_npc.createSprite("NPCO"); }
		if(npcFrame >= 25 && npcFrame <= 50) { spr_master = atlas_npc.createSprite("NPCO2"); }
		if(npcFrame >= 50 && npcFrame <= 75) { spr_master = atlas_npc.createSprite("NPCO"); }
		if(npcFrame >= 75 && npcFrame <= 100) { spr_master = atlas_npc.createSprite("NPCO3"); }
		npcFromBottom = npcFromBottom + 0.3f;
		if(npcFromBottom > 190) { npcFromBottom = - 170; }
		spr_master.setSize(7, 24);
		spr_master.setPosition(23, npcFromBottom);
		lstNpcs.add(spr_master);
				
		
		
		return lstNpcs;
	}
	
	//[I] Auxiliary
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
	
	public void UpdateJob(String job) {
		playerInfo.job_A = job;
	}
}
