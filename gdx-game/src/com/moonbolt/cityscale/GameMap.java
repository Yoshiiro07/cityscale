package com.moonbolt.cityscale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class GameMap implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	
		//Server Crendentals
	
		private String lservername = "cityserver.mysql.uhserver.com";
		private String lusername = "citymaster";
		private String lpassword = "City@key90";
		private String ldbname = "cityserver";
		
		//Objects
	    private MainGame game;
	    private ManagerScreen screen;
	    private FileHandle file;
	    private Json json;
	    private Random randnumber;
	    private String state = "main";
	    private String shop = "";
	    private Sprite spr_master;
	    private Sprite spr_shop;
	    private String SysMsg = "";
	    private int countExit = 100;
	    private int SysMsgCount = 0;    
	    private int savedataTime = 500;
	    private boolean onlineAuth = false;
	    private boolean versionDif = false; 
	    private boolean uploadDone = false;
	    private boolean notmp = false;
	    private boolean accountnumber = false;
		private float npcWalk1 = 100;
	    private float npcWalk2 = -20;
	    private TextureAtlas atlas_generic;
	    
		//Fonts
		private BitmapFont font_master;
		
	    //Camera
	    private OrthographicCamera camera;
	    private Viewport viewport;
	    private float cameraCoordsX = 0;
	    private float cameraCoordsY = 0;
	    
	    //Player
	    private Sprite spr_player;
	    private Sprite spr_hair;
	    private Sprite spr_hat;	 
	    private Sprite spr_weapon;
	    private Sprite spr_cardButton;
	    private GameObject player;
	    private float playerPosX;
	    private float playerPosY;
	    private float plPosX;
	    private float plPosY;
	    private float touchSkillX;
	    private float touchSkillY;
	    private int countFrame = 1;
	    private int playerFrame = 1;
	    private int countDead = 100;
	    private int skillUsed = 0; 
	    private int hotketcountitem = 0;
	    private String breakwalk = "";
	    private String skillname = "";
	    private boolean autoattack = false;
	    private boolean playerInAttack = false;
	    private boolean playerInCast = false;
	    private boolean playerDead = false;
	    private boolean rangedAttack = false;
	    private boolean skillEffect = false;
	    private boolean movement = false;	
	    private boolean partyOn = false;
	        	    
	    //Player UI
	    private Sprite spr_playerTag;
	    private Sprite spr_touchpad;
	    private Sprite spr_playerMenu;
	    private Sprite spr_target;
	    private Sprite spr_baritemdesc;
	    private float padX = -60;
	    private float padY = -55;
	    private int countSwitchTarget = 0;
	    private int showDropMsg = 0;
	    private String menuOptions = "";
	    private String itemdropname = "";
	    private String detailItem = "";
	    private boolean showDescription = false;
	    private boolean selectAreaRanged = false;
	     
	    //Mob
	    private Sprite spr_mob;
	    private ArrayList<GameObject> lstMobs;
	    private int mobFrame = 1;
	    private float mobPositionCoordX = 0;
	    private float mobPositionCoordY = 0;
	    private int mobRandomSt = 0;
	    private int mobTimerMov = 100;
	    private int mobTimerFrame = 40;
	    
	    //Online
	    private ArrayList<GameObject> lstOnlinePlayers;
	    private int countCleanOnline = 800;
		private String retornoOnline = "";
		private int threahCountSyncPlayer = 0;
		private int threahCountSyncChat = 0;
		private int threahCountSyncMob = 0;
		private boolean network = true;
		private boolean playerMobSync = false;
		private boolean keepnetwork = false;
	    private boolean receiveExpOnline = true;
		private Sprite spr_playerOnline;
	    private Sprite spr_hairOnline;
	    private Sprite spr_hatOnline;	 
	    private Sprite spr_weaponOnline;
	    private GameObject newOnlinePlayer;
	    private int timerreceiveExpOnline = 0;
	    private int GiveExp = 0;
	    private int timerGiveExp = 100;
	    private int countParty = 0;
	    private Thread thrOnlineSyncPlayer;
		private Thread thrOnlineSyncChat;
		private Thread thrOnlineSyncMob;
				
	    //Chats
	    private ArrayList<String> lstChats;
	    
	    //Damage
	    private ArrayList<GameObject> lstDamage;
	    
	    //Skills
	    private boolean showZone = false;
	    private int countZoneSkill = 0;
	    private ArrayList<GameObject> lstSkills;
	    
	    //NPC
	    private float npcsideleft;
	    private float npcsideright;
	    private String steptalk = "";
	    private int npcframe = 1;
	    private boolean setNPCSdefault = true;
	    
	    //Sprites Background
	    private Sprite spr_Background;
	    private Texture tex_Background;
	    
	  	//Textures
	    private TextureAtlas atlas_gameUI;
	    private TextureAtlas atlas_basicset;
	    private TextureAtlas atlas_blackset;
	    
	    private TextureAtlas atlas_cards;
	     
	    private TextureAtlas atlas_hairs;
	    private TextureAtlas atlas_hair1;
	    private TextureAtlas atlas_hair2;
	    private TextureAtlas atlas_hair3;
	    
	    private TextureAtlas atlas_shop1;
	    
	    private TextureAtlas atlas_npcs1;
	    
	    private TextureAtlas atlas_weapongeneric;
	    private TextureAtlas atlas_axes;
	    private TextureAtlas atlas_daggers;
	    private TextureAtlas atlas_nknifes;
	    private TextureAtlas atlas_pistols;
	    private TextureAtlas atlas_rods;
	    private TextureAtlas atlas_swords;
	    
	    private TextureAtlas atlas_itensSet;
	    private TextureAtlas atlas_itensDrop;
	    private TextureAtlas atlas_itensHat;
	    private TextureAtlas atlas_itensUtil;
	    private TextureAtlas atlas_itensWeapon;
	    private TextureAtlas atlas_hatFrame;
	    
	    private TextureAtlas atlas_mobSewers;
	    private TextureAtlas atlas_mobWatercave;
	    private TextureAtlas atlas_mobMines;
	    private TextureAtlas atlas_mobSnowpalace;
	    private TextureAtlas atlas_mobTower;
	    
	    private TextureAtlas atlas_tripleattack;
	    private TextureAtlas atlas_rockbound;
	    private TextureAtlas atlas_regen;
	    private TextureAtlas atlas_steal;
	    private TextureAtlas atlas_soulclash;
	    private TextureAtlas atlas_ravenblade;
	    private TextureAtlas atlas_ragebound;
	    private TextureAtlas atlas_thundercloud;
	    private TextureAtlas atlas_lockshot;
	    private TextureAtlas atlas_mine;
	    private TextureAtlas atlas_overpower;
	    private TextureAtlas atlas_poisonhit;
	    private TextureAtlas atlas_precision;
	    private TextureAtlas atlas_protect;
	    private TextureAtlas atlas_healthboost;
	    private TextureAtlas atlas_holyprism;
	    private TextureAtlas atlas_icecrystal;
	    private TextureAtlas atlas_impound;
	    private TextureAtlas atlas_invisibility;
	    private TextureAtlas atlas_ironshield;
	    private TextureAtlas atlas_doublehit;
	    private TextureAtlas atlas_fastshot;
	    private TextureAtlas atlas_fireball;
	    private TextureAtlas atlas_flysword;
	    private TextureAtlas atlas_heal;
	    private TextureAtlas atlas_boost;
	    private TextureAtlas atlas_berserk;
	    private TextureAtlas atlas_bulletrain;
	    private TextureAtlas atlas_dashkick;
	    
	    //Teste Dot
	    private Sprite spr_testeDot;
	    private Texture tex_testeDot;
	    private float testX;
	    private float testY;
	    
	    //Controller
	    private final IntSet downKeys = new IntSet(20);	
		
		public GameMap(MainGame gameAlt,ManagerScreen screenAlt, boolean network) {
			this.game = gameAlt;	
			this.screen = screenAlt;
			this.json = new Json();
			this.randnumber = new Random();
			this.network = network;
			
			lstOnlinePlayers = new ArrayList<GameObject>();
			lstDamage = new ArrayList<GameObject>();
			lstSkills = new ArrayList<GameObject>();
			lstChats = new ArrayList<String>();
			newOnlinePlayer = new GameObject();
			
			//Set list of chats
			lstChats.add(""); lstChats.add(""); lstChats.add(""); 

			//test dot
			tex_testeDot = new Texture(Gdx.files.internal("data/assets/selected.png"));
			spr_testeDot = new Sprite(tex_testeDot);
			
			//Load Player Data
			file = Gdx.files.local("SaveData/save.json");		
			player = json.fromJson(GameObject.class, Base64Coder.decodeString(file.readString()));
			
			//Load Title
			if(player.Map.equals("StreetsA")) { 
				tex_Background = new Texture(Gdx.files.internal("data/assets/maps/streetsA.png")); 
			}
			if(player.Map.equals("StreetsB")) { 
				tex_Background = new Texture(Gdx.files.internal("data/assets/maps/streetsB.png")); 
			}
			if(player.Map.equals("StreetsC")) { 
				tex_Background = new Texture(Gdx.files.internal("data/assets/maps/streetsC.png")); 
			}
			
			if(player.Map.equals("Sewers")) { 
				tex_Background = new Texture(Gdx.files.internal("data/assets/maps/sewers.png")); 
				atlas_mobSewers = new TextureAtlas(Gdx.files.internal("data/assets/mobs/mobSewers.txt"));
				LoadMobsSewers();
			}
			if(player.Map.equals("Watercave")) { 
				tex_Background = new Texture(Gdx.files.internal("data/assets/maps/watercave.png")); 
				atlas_mobWatercave = new TextureAtlas(Gdx.files.internal("data/assets/mobs/mobWatercave.txt"));	
				LoadMobsWatercave();
			}
			if(player.Map.equals("Mines")) { 
				tex_Background = new Texture(Gdx.files.internal("data/assets/maps/mines.png")); 
				atlas_mobMines = new TextureAtlas(Gdx.files.internal("data/assets/mobs/mobMines.txt"));		
				LoadMobsMines();
			}
			if(player.Map.equals("Snowpalace")) { 
				tex_Background = new Texture(Gdx.files.internal("data/assets/maps/snowpalace.png")); 
				atlas_mobSnowpalace = new TextureAtlas(Gdx.files.internal("data/assets/mobs/mobSnowpalace.txt"));	
				LoadMobsSnowpalace();
			}
			
			spr_Background = new Sprite(tex_Background);
					
			//Camera and Inputs
			camera = new OrthographicCamera();
		    viewport = new StretchViewport(135,135,camera);
			viewport.apply();
			camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
			Gdx.input.setInputProcessor(this);
	
			//font
			font_master = new BitmapFont(Gdx.files.internal("data/assets/font/impact.fnt"),Gdx.files.internal("data/assets/font/impact.png"), false);
			font_master.setColor(Color.WHITE);
			font_master.getData().setScale(0.07f,0.11f);
			font_master.setUseIntegerPositions(false);	
			
			//Atlas
			atlas_gameUI = new TextureAtlas(Gdx.files.internal("data/assets/UI/UI.txt"));
			
			atlas_npcs1 = new TextureAtlas(Gdx.files.internal("data/assets/chars/npcs1.txt"));
			
			atlas_hair1 = new TextureAtlas(Gdx.files.internal("data/assets/chars/hairs1.txt"));
			atlas_hair2 = new TextureAtlas(Gdx.files.internal("data/assets/chars/hairs2.txt"));
			atlas_hair3 = new TextureAtlas(Gdx.files.internal("data/assets/chars/hairs3.txt"));
			
			atlas_basicset = new TextureAtlas(Gdx.files.internal("data/assets/chars/basicset.txt"));
			atlas_blackset = new TextureAtlas(Gdx.files.internal("data/assets/chars/blackset.txt"));
			atlas_generic = new TextureAtlas(Gdx.files.internal("data/assets/chars/basicset.txt"));
			
			atlas_cards = new TextureAtlas(Gdx.files.internal("data/assets/cards/cards.txt"));
			
			atlas_itensSet = new TextureAtlas(Gdx.files.internal("data/assets/itens/itensSet/itensSet.txt"));
			atlas_itensHat = new TextureAtlas(Gdx.files.internal("data/assets/itens/itensHat/itensHat.txt"));
			atlas_itensUtil = new TextureAtlas(Gdx.files.internal("data/assets/itens/itensUtil/itensUtil.txt"));
			atlas_itensWeapon = new TextureAtlas(Gdx.files.internal("data/assets/itens/itensWeapon/itensWeapon.txt"));
			atlas_itensDrop = new TextureAtlas(Gdx.files.internal("data/assets/itens/itensDrop/itensDrop.txt"));
			
			atlas_swords = new TextureAtlas(Gdx.files.internal("data/assets/itens/weaponframes/swords.txt"));
			atlas_rods = new TextureAtlas(Gdx.files.internal("data/assets/itens/weaponframes/rods.txt"));
			atlas_pistols = new TextureAtlas(Gdx.files.internal("data/assets/itens/weaponframes/pistols.txt"));
			atlas_daggers = new TextureAtlas(Gdx.files.internal("data/assets/itens/weaponframes/daggers.txt"));
			atlas_axes = new TextureAtlas(Gdx.files.internal("data/assets/itens/weaponframes/axes.txt"));
			atlas_nknifes = new TextureAtlas(Gdx.files.internal("data/assets/itens/weaponframes/nknifes.txt"));
			atlas_weapongeneric = new TextureAtlas(Gdx.files.internal("data/assets/itens/weaponframes/nknifes.txt"));
	
			atlas_hatFrame = new TextureAtlas(Gdx.files.internal("data/assets/itens/hatframes/hatframes.txt"));
			
			atlas_tripleattack = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/tripleattack.txt"));
			atlas_rockbound = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/rockbound.txt"));
			atlas_regen = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/regen.txt"));
			atlas_steal = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/steal.txt"));
			atlas_soulclash = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/soulclash.txt"));
			atlas_ravenblade = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/ravenblade.txt"));
			atlas_ragebound = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/ragebound.txt"));
			atlas_thundercloud = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/thundercloud.txt"));
			atlas_lockshot = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/lockshot.txt"));
			atlas_mine = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/mine.txt"));
			atlas_overpower = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/overpower.txt"));
			atlas_poisonhit = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/poisonhit.txt"));
			atlas_precision = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/precision.txt"));
			atlas_protect = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/protect.txt"));
			atlas_healthboost = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/healthboost.txt"));
			atlas_holyprism = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/holyprism.txt"));
			atlas_icecrystal = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/icecrystal.txt"));
			atlas_impound = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/impound.txt"));
			atlas_invisibility = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/invisibility.txt"));
			atlas_ironshield = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/ironshield.txt"));
			atlas_doublehit = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/doublehit.txt"));
			atlas_fastshot = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/fastshot.txt"));
			atlas_fireball = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/fireball.txt"));
			atlas_flysword = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/flysword.txt"));
			atlas_boost = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/boost.txt"));
			atlas_berserk = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/berserk.txt"));
			atlas_bulletrain = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/bulletrain.txt"));
			atlas_dashkick = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/dashkick.txt"));
			
			atlas_shop1 = new TextureAtlas(Gdx.files.internal("data/assets/shops/shops1.txt"));
			
			RemoveBuffs(player.buffA);
			RemoveBuffs(player.buffB);
			RemoveBuffs(player.buffC);
			
			player.party = "none";
			player.playerInAttack = "no";
			player.playerInBattle = "no";
			player.playerInCast = "no";
		}
			
		@Override
		public void render(float delta) {
			try {
				savedataTime--;
				if(savedataTime < 0) {
					savedataTime = 700;
					SaveData();
				}
			
				//Just for coloring
				Gdx.gl.glClearColor(1,1,1,1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
				//Camera Ajustments
				cameraCoordsX = playerPosX;
				cameraCoordsY = playerPosY;
				
				//Follow camera
				if(playerPosX <= -25f) 	{ cameraCoordsX = -25; 	 }
				if(playerPosX >= 175) 	{ cameraCoordsX = 175; 	 }
				if(playerPosY >= 91.5f) { cameraCoordsY = 91.5f; }
				if(playerPosY <= -105) 	{ cameraCoordsY = -105;  }
				
				//Update camera and start drawling
				camera.position.set(cameraCoordsX -2,cameraCoordsY+1,0);
				camera.update();
			    game.batch.setProjectionMatrix(camera.combined);	    
				game.batch.begin();
				
				//Background	
				spr_Background.setPosition(-70,-70);
				spr_Background.setSize(136, 140);
				spr_Background.draw(game.batch);
				
				//UI
				if(!player.Map.equals("Volleyball")) {
				spr_playerTag = atlas_gameUI.createSprite("tagplayer");
				spr_playerTag.setPosition(-69.5f,38.9f);
				spr_playerTag.setSize(40,30);
				spr_playerTag.draw(game.batch);
				
				float percent = (player.Exp * 100.0f) / CheckLevelExpPercent();
				font_master.draw(game.batch, String.valueOf(player.AtkTimer), cameraCoordsX + 57.5f, cameraCoordsY - 25.7f);
				font_master.draw(game.batch, player.Name, cameraCoordsX - 60.5f, cameraCoordsY + 64.7f);
				font_master.draw(game.batch, String.valueOf(player.Level), cameraCoordsX - 41.5f, cameraCoordsY + 64.7f);
				font_master.draw(game.batch, player.Hp + "/" + player.HpMax , cameraCoordsX - 60.5f, cameraCoordsY + 58.4f);
				font_master.draw(game.batch, player.Mp + "/" + player.MpMax , cameraCoordsX - 60.5f, cameraCoordsY + 51.7f);
				font_master.draw(game.batch, player.Stamina + "/" + player.StaminaMax , cameraCoordsX - 58.5f, cameraCoordsY + 44.7f);
				font_master.draw(game.batch, String.format("%.02f", percent) + "%", cameraCoordsX - 43.5f, cameraCoordsY + 44.7f);
				font_master.draw(game.batch, String.valueOf(skillUsed), cameraCoordsX + 40.5f, cameraCoordsY - 45f);
				
				
				
				font_master.draw(game.batch, "Local:" + player.Map , cameraCoordsX - 68f, cameraCoordsY + 38.7f);
				font_master.draw(game.batch, "X:" + player.PosX , cameraCoordsX - 68f, cameraCoordsY + 34.7f);
				font_master.draw(game.batch, "Y:" + player.PosY , cameraCoordsX - 68f, cameraCoordsY + 30.7f);
				
				if(!autoattack) {
					spr_cardButton = atlas_cards.createSprite("cardaction");
					spr_cardButton.setSize(6, 15);
					spr_cardButton.setPosition(56,-45);
					spr_cardButton.draw(game.batch);
				}
				else {
					spr_cardButton = atlas_cards.createSprite("cardactionON");
					spr_cardButton.setSize(6, 15);
					spr_cardButton.setPosition(56,-45);
					spr_cardButton.draw(game.batch);
				}
				
				spr_cardButton = atlas_cards.createSprite("cardtarget");
				spr_cardButton.setSize(6, 15);
				spr_cardButton.setPosition(56,-65);
				spr_cardButton.draw(game.batch);
				
				if(player.Job.equals("Novice") || player.Job.equals("Aprendiz")) { spr_cardButton = atlas_cards.createSprite("cardtripleattack"); }
				if(player.Job.equals("Swordman") || player.Job.equals("Espadachim")) { spr_cardButton = atlas_cards.createSprite("cardflysword"); }
				if(player.Job.equals("Magician") || player.Job.equals("Feiticeiro")) { spr_cardButton = atlas_cards.createSprite("cardfireball"); }
				if(player.Job.equals("Beater") || player.Job.equals("Batedor")) { spr_cardButton = atlas_cards.createSprite("cardhammercrash"); }
				if(player.Job.equals("Gunner") || player.Job.equals("Pistoleiro")) { spr_cardButton = atlas_cards.createSprite("cardbulletrain"); }
				if(player.Job.equals("Medic") || player.Job.equals("Medico")) { spr_cardButton = atlas_cards.createSprite("cardheal"); }
				if(player.Job.equals("Thief") || player.Job.equals("Ladrao")) { spr_cardButton = atlas_cards.createSprite("cardpoison"); }
								
				spr_cardButton.setSize(6, 15);
				spr_cardButton.setPosition(30,-65);
				spr_cardButton.draw(game.batch);
				
				if(player.Job.equals("Novice") || player.Job.equals("Aprendiz")) { spr_cardButton = atlas_cards.createSprite("cardrock"); }
				if(player.Job.equals("Swordman") || player.Job.equals("Espadachim")) { spr_cardButton = atlas_cards.createSprite("cardhealthboost"); }
				if(player.Job.equals("Magician") || player.Job.equals("Feiticeiro")) { spr_cardButton = atlas_cards.createSprite("cardicecrystal"); }
				if(player.Job.equals("Beater") || player.Job.equals("Batedor")) { spr_cardButton = atlas_cards.createSprite("cardberserk"); }
				if(player.Job.equals("Gunner") || player.Job.equals("Pistoleiro")) { spr_cardButton = atlas_cards.createSprite("cardlockshot"); }
				if(player.Job.equals("Medic") || player.Job.equals("Medico")) { spr_cardButton = atlas_cards.createSprite("cardboost"); }
				if(player.Job.equals("Thief") || player.Job.equals("Ladrao")) { spr_cardButton = atlas_cards.createSprite("cardsteal"); }
				spr_cardButton.setSize(6, 15);
				spr_cardButton.setPosition(46,-65);
				spr_cardButton.draw(game.batch);
				
				if(player.Job.equals("Novice") || player.Job.equals("Aprendiz")) { spr_cardButton = atlas_cards.createSprite("cardregen"); }
				if(player.Job.equals("Swordman") || player.Job.equals("Espadachim")) { spr_cardButton = atlas_cards.createSprite("cardironshield"); }
				if(player.Job.equals("Magician") || player.Job.equals("Feiticeiro")) { spr_cardButton = atlas_cards.createSprite("cardthundercloud"); }
				if(player.Job.equals("Beater") || player.Job.equals("Batedor")) { spr_cardButton = atlas_cards.createSprite("cardoverpower"); }
				if(player.Job.equals("Gunner") || player.Job.equals("Pistoleiro")) { spr_cardButton = atlas_cards.createSprite("cardmine"); }
				if(player.Job.equals("Medic") || player.Job.equals("Medico")) { spr_cardButton = atlas_cards.createSprite("cardholyprism"); }
				if(player.Job.equals("Thief") || player.Job.equals("Ladrao")) { spr_cardButton = atlas_cards.createSprite("cardinvisibility"); }
				spr_cardButton.setSize(6, 15);
				spr_cardButton.setPosition(38,-65);
				spr_cardButton.draw(game.batch);
				
				}
				
				spr_touchpad = atlas_gameUI.createSprite("pad");
				spr_touchpad.setPosition(padX, padY);
				spr_touchpad.setSize(10, 20);
				spr_touchpad.draw(game.batch);
				
				font_master.setColor(Color.WHITE);
				font_master.getData().setScale(0.07f,0.11f);
				
				if(network) {
					if(!onlineAuth) {
						OnlineManager("CheckVersion","","");
					}
					else {
						OnlineManager("SyncPlayer","","");
						OnlineManager("SyncChats","","");
						OnlineManager("SyncMob","","");
						network = false;
						keepnetwork = true;
					}			
				}
				
				//NPCS
				ShowNPCS();
				
				//Player
				spr_player = SetCharMov(player,"player");
				spr_player.draw(game.batch);
				
				spr_hair = SetCharHair(player,"player");
				spr_hair.draw(game.batch);
				
				if(!player.Hat.equals("none")) {
					spr_hat = SetCharHat(player,"player");
					spr_hat.draw(game.batch);
				}
				if(player.playerInBattle.equals("yes") || player.playerInAttack.equals("yes") || player.playerInCast.equals("yes")) {
					spr_weapon = SetWeapon(player);
					spr_weapon.draw(game.batch);
				}
				
				
				//Shows
				if(keepnetwork) { ShowMobsOnline(); }
				if(!keepnetwork) { ShowMobs();  } 	
				CheckStamina();
				CheckAutoAttack();
				CheckMobAutoAttack();
				CheckMobDeadRespawn();
				ShowDamage();
				ShowSkill();
				ShowBuffs();
				CheckRegenTime();
				ShowChats();
				ShowHotKey();
				
				font_master.setColor(Color.WHITE);
				font_master.getData().setScale(0.07f,0.11f);
				font_master.setUseIntegerPositions(false);	
				
				//Ranged Skill
				if(selectAreaRanged) {
					spr_master = atlas_gameUI.createSprite("bardescription");
					spr_master.setPosition(-17, 53);
					spr_master.setSize(30, 15);
					spr_master.draw(game.batch);
					font_master.draw(game.batch, "Selecione a Area",-10, 62);
				}
				if(showZone) {
					if(countZoneSkill > 0) {
						spr_master = atlas_gameUI.createSprite("areaskill");
						spr_master.setPosition(touchSkillX -5, touchSkillY - 8);
						spr_master.setSize(10,20);
						spr_master.draw(game.batch);
						countZoneSkill--;
					}
					else {
						showZone = false;
						countZoneSkill = 0;
					}			
				}
				
				//Colisions
				CheckColision();
				
				
				//Bars
				if(player.Map.equals("StreetsA")) {
				//spr_master = atlas_gameUI.createSprite("bardescription");
				//spr_master.setPosition(-17, -7);
				//spr_master.setSize(10, 8);
				//spr_master.draw(game.batch);
				//font_master.draw(game.batch, "Cristais",-16, -2);
				
				
				spr_master = atlas_gameUI.createSprite("bardescription");
				spr_master.setPosition(-36, -7);
				spr_master.setSize(10, 8);
				spr_master.draw(game.batch);
				font_master.draw(game.batch, "Classes",-35, -2);
				
				//spr_master = atlas_gameUI.createSprite("bardescription");
				//spr_master.setPosition(37, 39);
				//spr_master.setSize(10, 8);
				//spr_master.draw(game.batch);
				//font_master.draw(game.batch, "Missao",38, 44);
				
				spr_master = atlas_gameUI.createSprite("bardescription");
				spr_master.setPosition(-31, 39);
				spr_master.setSize(10, 8);
				spr_master.draw(game.batch);
				font_master.draw(game.batch, "MiniGame",-30.5f, 44);
				}
				
				ShowOnlinePlayers();
				
				//Menu
				if(state.equals("menu")) {
					spr_playerMenu = atlas_gameUI.createSprite("playermenu");
					spr_playerMenu.setSize(120, 100);
					spr_playerMenu.setPosition(-63,-50);
					spr_playerMenu.draw(game.batch);
					
					font_master.draw(game.batch, String.valueOf(player.Name),-51.5f, -7.5f);
					font_master.draw(game.batch, String.valueOf(player.Job) ,-50.5f, -12.5f);
					font_master.draw(game.batch, String.valueOf(player.Exp) ,-54.5f, -17.9f);
					font_master.draw(game.batch, String.valueOf(player.StatusPoint) ,-48f, -23.5f);
					font_master.draw(game.batch, String.valueOf(player.Money) ,-48f, -28.5f);
										
					font_master.draw(game.batch, String.valueOf(player.Str) ,5, 10);
					font_master.draw(game.batch, String.valueOf(player.Vit) ,15, 10);
					font_master.draw(game.batch, String.valueOf(player.Agi) ,24, 10);
					font_master.draw(game.batch, String.valueOf(player.Dex) ,33, 10);
					font_master.draw(game.batch, String.valueOf(player.Wis) ,42, 10);
					font_master.draw(game.batch, String.valueOf(player.Luk) ,5, -10);
					font_master.draw(game.batch, String.valueOf(player.Res) ,15, -10);
					
					ShowBag();
					
					
					if(showDescription) {
						spr_baritemdesc = atlas_gameUI.createSprite("bardescription");
						spr_baritemdesc.setPosition(-44, -65);
						spr_baritemdesc.setSize(80, 15);
						spr_baritemdesc.draw(game.batch);
						
						font_master.draw(game.batch,detailItem ,-40, -56);
					}
					
					spr_player = SetCharMov(player,"");
					spr_player.draw(game.batch);
					
					spr_hair = SetCharHair(player,"");
					spr_hair.draw(game.batch);
					
					if(!player.Hat.equals("none")) {
						spr_hat = SetCharHat(player,"player");
						spr_hat.draw(game.batch);
					}
					
					//Menu Options
					if(menuOptions.equals("descricao")){ 
						spr_master = atlas_gameUI.createSprite("selectedop"); 
						spr_master.setPosition(-32,-47); 
						spr_master.setSize(15, 10); 
						spr_master.draw(game.batch); 
					}
					if(menuOptions.equals("descartar")){ 
						spr_master = atlas_gameUI.createSprite("selectedop"); 
						spr_master.setPosition(-1.2f,-47); 
						spr_master.setSize(15, 10); 
						spr_master.draw(game.batch); 
					}
					if(menuOptions.equals("atalho")){ 
						spr_master = atlas_gameUI.createSprite("selectedop"); 
						spr_master.setPosition(-16.5f,-47); 
						spr_master.setSize(15, 10); 
						spr_master.draw(game.batch); 
					}
					
				}
				
				if(state.equals("shop")) {
					if(shop == "shopStreetsA1") {
						spr_shop = atlas_shop1.createSprite("shopStreetsA1");
						spr_shop.setSize(50, 100);
						spr_shop.setPosition(-30, -40);
						spr_shop.draw(game.batch);
						
						font_master.draw(game.batch, String.valueOf(player.Money),-10, -21);
					}
					if(shop == "shopStreetsA2") {
						spr_shop = atlas_shop1.createSprite("shopStreetsA2");
						spr_shop.setSize(50, 100);
						spr_shop.setPosition(-30, -40);
						spr_shop.draw(game.batch);
						
						font_master.draw(game.batch, String.valueOf(player.Money),-10, -21);
					}
					if(shop == "shopCrytal") {
						spr_shop = atlas_gameUI.createSprite("Crystal");
						spr_shop.setSize(50, 100);
						spr_shop.setPosition(-30, -40);
						spr_shop.draw(game.batch);
					}
					if(shop == "shopJob") {
						spr_shop = atlas_gameUI.createSprite("jobmaster");
						spr_shop.setSize(50, 100);
						spr_shop.setPosition(-30, -40);
						spr_shop.draw(game.batch);
					}
					if(shop == "shopStreetsB3") {
						spr_shop = atlas_shop1.createSprite("shopStreetsB3");
						spr_shop.setSize(50, 100);
						spr_shop.setPosition(-30, -40);
						spr_shop.draw(game.batch);
						
						font_master.draw(game.batch, String.valueOf(player.Money),-10, -21);
					}
					if(shop == "shopStreetsB4") {
						spr_shop = atlas_shop1.createSprite("shopStreetsB4");
						spr_shop.setSize(50, 100);
						spr_shop.setPosition(-30, -40);
						spr_shop.draw(game.batch);
						
						font_master.draw(game.batch, String.valueOf(player.Money),-10, -21);
					}
					
					if(SysMsgCount > 0) {
						if(state.equals("shop")) {
							font_master.draw(game.batch,SysMsg,-25, -11);
							SysMsgCount--;
						}
					}
				}
				
				MsgChecks();
				
				if(playerDead) {
					countDead--;
					
					player.Target = "none";
					player.playerInBattle = "no";
					playerInAttack = false;
					playerInCast = false;
					autoattack = false;
					spr_master = atlas_gameUI.createSprite("bardescription");
					spr_master.setPosition(-22.5f, -10);
					spr_master.setSize(40, 30);
					spr_master.draw(game.batch);
					
					font_master.draw(game.batch, "Voce morreu, retornando...",-15, 6);
					
					if(countDead <= 0) {
						player.Hp = 10;
						player.Mp = 10;
						player.Map = "StreetsA";
						player.PosX = 31.5f;
						player.PosY = -40;
						SaveData();
						this.screen.screenSwitch("LoadingScreen",keepnetwork);
					}
				}
				
				
											
				//Teste				
				//spr_testeDot.setPosition(54, -10f);
				//spr_testeDot.setSize(1, 1);
				//spr_testeDot.draw(game.batch);
				
				//spr_testeDot.setPosition(65, -25f);
				//spr_testeDot.setSize(1, 1);
				//spr_testeDot.draw(game.batch);
				
				game.batch.end();
			}
			
			catch(Exception ex) {
				Gdx.app.exit();
			}
		}
		
		public void CheckStamina() {
			int sttimer = player.StaminaTimer;
			sttimer--;
			if(sttimer < 0) { 
				player.Stamina--;
				sttimer = 1200;
			}
			if(player.Stamina <= 0) { player.Stamina = 0; }
		}
		
		public void MsgChecks() {
			if(showDropMsg > 0) {
				spr_master = atlas_gameUI.createSprite("bardescription");
				spr_master.setPosition(-27, 30);
				spr_master.setSize(50, 15);
				spr_master.draw(game.batch);
				
				font_master.draw(game.batch, "Obtido item: " + itemdropname,-24, 39);				
				showDropMsg--;
			}
			
			
			if(versionDif) {
				spr_master = atlas_gameUI.createSprite("bardescription");
				spr_master.setPosition(-27, 30);
				spr_master.setSize(50, 15);
				spr_master.draw(game.batch);
				
				font_master.draw(game.batch, SysMsg,-24, 39);	
				countExit--;
				if(countExit <= 0) {
					countExit = 200;
					versionDif = false;
					Gdx.app.exit();
				}
			}
			
			if(uploadDone) {
				spr_master = atlas_gameUI.createSprite("bardescription");
				spr_master.setPosition(-27, 30);
				spr_master.setSize(50, 15);
				spr_master.draw(game.batch);
				
				font_master.draw(game.batch, SysMsg,-24, 39);	
				countExit--;
				if(countExit <= 0) {
					countExit = 200;
					uploadDone = false;
				}
			}
			
			if(accountnumber) {
				spr_master = atlas_gameUI.createSprite("bardescription");
				spr_master.setPosition(-27, 30);
				spr_master.setSize(50, 15);
				spr_master.draw(game.batch);
				
				font_master.draw(game.batch, SysMsg,-24, 39);	
				countExit--;
				if(countExit <= 0) {
					countExit = 200;
					accountnumber = false;
				}
			}
			
			if(notmp) {
				spr_master = atlas_gameUI.createSprite("bardescription");
				spr_master.setPosition(-27, 30);
				spr_master.setSize(50, 15);
				spr_master.draw(game.batch);
				
				font_master.draw(game.batch, "MP insuficiente",-24, 39);	
				countExit--;
				if(countExit <= 0) {
					countExit = 200;
					notmp = false;
				}
			}
		}
		
		public void ShowHotKey() {  
			if(!player.hotkey1.equals("none")) {
				spr_master = ItemLog(player.hotkey1);
				spr_master.setPosition(55, -20);
				spr_master.setSize(9, 14);
				spr_master.draw(game.batch);
				String teste = ShowQuantityItem(hotketcountitem);
				
				if(teste.equals("")) {
					player.hotkey1 = "none";
				}
				else {
					font_master.draw(game.batch, teste, 60, -15);
					font_master.draw(game.batch, "Atalho",55, -20);
				}		
			}
		}
		
		public void ShowOnlinePlayers() {
			
			if(lstOnlinePlayers == null) { return; }
			
			for(int i = 0; i < lstOnlinePlayers.size(); i++) {
				
				if(lstOnlinePlayers.get(0).AccountID.equals(player.AccountID)) { playerMobSync = true; } else { playerMobSync = false;}
								
				if(!lstOnlinePlayers.get(i).AccountID.equals(player.AccountID)) {
					if(player.Map.equals(lstOnlinePlayers.get(i).Map)) {
						font_master.draw(game.batch, lstOnlinePlayers.get(i).Name, lstOnlinePlayers.get(i).PosX +2.5f, lstOnlinePlayers.get(i).PosY + 22.5f);
						spr_playerOnline = SetCharMov(lstOnlinePlayers.get(i),"online");
						spr_playerOnline.draw(game.batch);
						
						spr_hairOnline = SetCharHair(lstOnlinePlayers.get(i), "online");
						spr_hairOnline.draw(game.batch);
						
						spr_hatOnline = SetCharHat(lstOnlinePlayers.get(i), "online");
						spr_hatOnline.draw(game.batch);
						
						if(player.playerInBattle.equals("yes") || player.playerInAttack.equals("yes") || player.playerInCast.equals("yes")) {
							spr_weaponOnline = SetWeapon(lstOnlinePlayers.get(i));
							spr_weaponOnline.draw(game.batch);
						}
					}
					
					if(!player.party.equals("none")) {
						if(lstOnlinePlayers.get(i).party.equals(player.party)) {
							countParty++;
							if(countParty > 3) {
								countParty = 0;
							}
							ShowParty(i);
						}
					}
				}
			}
			countParty = 0;
		}
		
		public void ShowParty(int num) {  
			if(timerreceiveExpOnline >= 0) {
				timerreceiveExpOnline--;
				if(timerreceiveExpOnline <= 0) {
					timerreceiveExpOnline = 0;
					receiveExpOnline = true;
				}
			}
			
			if(timerGiveExp >= 0) {
				timerGiveExp--;
				if(timerGiveExp <= 0) {
					timerGiveExp = 0;
					GiveExp = 0;
				}
			}
			if(!player.party.equals("none")) {
				font_master.draw(game.batch, player.party,-37, 43);
			}
			
			//Show Tags online party
			if(countParty == 1) {
				spr_master = atlas_gameUI.createSprite("partytag");
				spr_master.setPosition(-70, 10);
				spr_master.setSize(25, 16);
				spr_master.draw(game.batch);
				
				font_master.draw(game.batch, lstOnlinePlayers.get(num).Name,-63,23.5f);
				font_master.draw(game.batch, String.valueOf(lstOnlinePlayers.get(num).Level),-49,23.5f);
				font_master.draw(game.batch, String.valueOf(lstOnlinePlayers.get(num).Hp),-64.6f, 19);
				font_master.draw(game.batch, String.valueOf(lstOnlinePlayers.get(num).Mp),-54f, 19);
				font_master.draw(game.batch, String.valueOf(lstOnlinePlayers.get(num).Job),-65, 14.5f);
			}	
			if(countParty == 2) {
				spr_master = atlas_gameUI.createSprite("partytag");
				spr_master.setPosition(-70, -6);
				spr_master.setSize(25, 16);
				spr_master.draw(game.batch);
				
				font_master.draw(game.batch, lstOnlinePlayers.get(num).Name,-63,7.5f);
				font_master.draw(game.batch, String.valueOf(lstOnlinePlayers.get(num).Level),-49,7.5f);
				font_master.draw(game.batch, String.valueOf(lstOnlinePlayers.get(num).Hp),-64.6f, 3);
				font_master.draw(game.batch, String.valueOf(lstOnlinePlayers.get(num).Mp),-54f, 3);
				font_master.draw(game.batch, String.valueOf(lstOnlinePlayers.get(num).Job),-65, -1.5f);
			}
			if(countParty == 3) {
				spr_master = atlas_gameUI.createSprite("partytag");
				spr_master.setPosition(-70, -22);
				spr_master.setSize(25, 16);
				spr_master.draw(game.batch);
				
				font_master.draw(game.batch, lstOnlinePlayers.get(num).Name,-63,-8.5f);
				font_master.draw(game.batch, String.valueOf(lstOnlinePlayers.get(num).Level),-49,-8.5f);
				font_master.draw(game.batch, String.valueOf(lstOnlinePlayers.get(num).Hp),-64.6f, -13f);
				font_master.draw(game.batch, String.valueOf(lstOnlinePlayers.get(num).Mp),-54f, -13f);
				font_master.draw(game.batch, String.valueOf(lstOnlinePlayers.get(num).Job),-65, -17.5f);
			}
		}
		
		public void ShowChats() {
				font_master.setColor(Color.WHITE);
				font_master.getData().setScale(0.10f,0.16f);
				font_master.setUseIntegerPositions(false);	
				if(lstChats.get(0) != null) { font_master.draw(game.batch, lstChats.get(0),-24, -39);  }
				if(lstChats.get(1) != null) { font_master.draw(game.batch, lstChats.get(1),-24, -49);  }
				if(lstChats.get(2) != null) { font_master.draw(game.batch, lstChats.get(2),-24, -59);  }		
		}
		
		//[SAVE]//
		public void SaveData() {		
			file = Gdx.files.local("SaveData/save.json");
			file.writeString(Base64Coder.encodeString(json.prettyPrint(player)),false);		
		}
		
		//[Char movement]//
		public Sprite SetCharMov(GameObject playerUse, String type) {			
			//Check MovePosition
			if(playerUse.Walk.equals("walk")) {
				if(playerUse.Side.equals("front") && !breakwalk.equals("front")) { playerUse.PosY = playerUse.PosY - 0.5f; }
				if(playerUse.Side.equals("back") && !breakwalk.equals("back")) { playerUse.PosY = playerUse.PosY + 0.5f; }
				if(playerUse.Side.equals("left") && !breakwalk.equals("left")) { playerUse.PosX = playerUse.PosX - 0.5f; }
				if(playerUse.Side.equals("right") && !breakwalk.equals("right")) { playerUse.PosX = playerUse.PosX + 0.5f; }
			}
			
			//Check Frames
			if(type.equals("player")) {
				if(!playerUse.Walk.equals("no")) { countFrame++; }
				if(playerUse.Walk.equals("no")) { playerUse.Frame = 1; }
				if(countFrame > 1 && countFrame <= 15) { playerUse.Frame = 2; }
				if(countFrame >= 15 && countFrame <= 30) { playerUse.Frame = 1; }
				if(countFrame >= 30 && countFrame <= 45) { playerUse.Frame = 3; }
				if(countFrame >= 45 && countFrame <= 60) { playerUse.Frame = 1; }
				if(countFrame >= 60) { countFrame = 1; }
			}
					
			//Check Set
			if(playerUse.Set.equals("basicset_m") || playerUse.Set.equals("basicset_f")) { atlas_generic = atlas_basicset; }
			if(playerUse.Set.equals("blackset_m") || playerUse.Set.equals("blackset_f")) { atlas_generic = atlas_blackset; }
					
		
			//Create Sprite
			if(playerUse.Walk.equals("no")) {
				if(playerUse.Side.equals("front") && playerUse.Frame == 1) { spr_master = atlas_generic.createSprite("front1" + playerUse.Sex);}
				if(playerUse.Side.equals("back") && playerUse.Frame == 1)  { spr_master = atlas_generic.createSprite("back1" + playerUse.Sex); }
				if(playerUse.Side.equals("right") && playerUse.Frame == 1) { spr_master = atlas_generic.createSprite("right1" + playerUse.Sex);}
				if(playerUse.Side.equals("left") && playerUse.Frame == 1)  { spr_master = atlas_generic.createSprite("left1" + playerUse.Sex); }
			}
			if(playerUse.Walk.equals("walk")) {
				if(playerUse.Side.equals("front")) { spr_master = atlas_generic.createSprite("front"+ playerUse.Frame + playerUse.Sex);}
				if(playerUse.Side.equals("back")) { spr_master = atlas_generic.createSprite("back"+ playerUse.Frame + playerUse.Sex);  }
				if(playerUse.Side.equals("left")) { spr_master = atlas_generic.createSprite("left"+ playerUse.Frame + playerUse.Sex);  }
				if(playerUse.Side.equals("right")) { spr_master = atlas_generic.createSprite("right"+ playerUse.Frame + playerUse.Sex);}
				
				if(playerUse.Side.equals("left-front") ) {spr_master = atlas_generic.createSprite("front"+ 1 + playerUse.Sex);  }
				if(playerUse.Side.equals("left-back") ) {spr_master = atlas_generic.createSprite("front"+ 1 + playerUse.Sex);   }
				if(playerUse.Side.equals("right-back") ) {spr_master = atlas_generic.createSprite("front"+ 1 + playerUse.Sex);  }
				if(playerUse.Side.equals("right-front") ) {spr_master = atlas_generic.createSprite("front"+ 1 + playerUse.Sex); }
				if(playerUse.Side.equals("back-right") ) {spr_master = atlas_generic.createSprite("front"+ 1 + playerUse.Sex);  }
				if(playerUse.Side.equals("back-left") ) {spr_master = atlas_generic.createSprite("front"+ 1 + playerUse.Sex);   }
				if(playerUse.Side.equals("front-right") ) {spr_master = atlas_generic.createSprite("front"+ 1 + playerUse.Sex); }
				if(playerUse.Side.equals("front-left") ) {spr_master = atlas_generic.createSprite("front"+ 1 + playerUse.Sex);  }			
			}
			
			if(player.playerInBattle.equals("yes")) {
				spr_master = atlas_generic.createSprite("bright"+ playerUse.Frame + playerUse.Sex);
			}
			
			if(player.playerInAttack.equals("yes")) {
				spr_master = atlas_generic.createSprite("s2bright" + playerUse.Sex);
			}
			
			spr_master.setSize(10, 17);
			spr_master.setPosition(playerUse.PosX,playerUse.PosY);
			
			
			if(state.equals("menu")) {
				spr_master = atlas_generic.createSprite("front1" + playerUse.Sex);
				spr_master.setSize(17, 32);
				spr_master.setPosition(-56,-2);
			}
					
			return spr_master;
		}
		
		public Sprite SetCharHair(GameObject playerUse, String type) {
			
			if(playerUse.Hair.equals("hair1")) { atlas_hairs = atlas_hair1; }
			if(playerUse.Hair.equals("hair2")) { atlas_hairs = atlas_hair2; }
			if(playerUse.Hair.equals("hair3")) { atlas_hairs = atlas_hair3; }
			
			//Male
			if(playerUse.Side.equals("front") && playerUse.Sex.equals("M")) { spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.9f, playerUse.PosY + 10.5f);	 }
			if(playerUse.Side.equals("back")  && playerUse.Sex.equals("M"))  { spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Back"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.9f, playerUse.PosY + 10.5f);	 }
			if(playerUse.Side.equals("right")  && playerUse.Sex.equals("M")) { spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Right"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 2.5f, playerUse.PosY + 10.8f);	 }
			if(playerUse.Side.equals("left")  && playerUse.Sex.equals("M"))  { spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Left"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.5f, playerUse.PosY + 10.8f);	 }
			
			if(playerUse.Side.equals("left-front")  && playerUse.Sex.equals("M") ) {spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.9f, playerUse.PosY + 10.5f); }
			if(playerUse.Side.equals("left-back")  && playerUse.Sex.equals("M") ) {spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.9f, playerUse.PosY + 10.5f);  }
			if(playerUse.Side.equals("right-back")  && playerUse.Sex.equals("M") ) {spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.9f, playerUse.PosY + 10.5f);  }
			if(playerUse.Side.equals("right-front")  && playerUse.Sex.equals("M") ) {spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.9f, playerUse.PosY + 10.5f); }
			if(playerUse.Side.equals("back-right")  && playerUse.Sex.equals("M") ) {spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.9f, playerUse.PosY + 10.5f);  }
			if(playerUse.Side.equals("back-left")  && playerUse.Sex.equals("M") ) {spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.9f, playerUse.PosY + 10.5f);  }
			if(playerUse.Side.equals("front-right")  && playerUse.Sex.equals("M") ) {spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.9f, playerUse.PosY + 10.5f); }
			if(playerUse.Side.equals("front-left")  && playerUse.Sex.equals("M") ) {spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.9f, playerUse.PosY + 10.5f); }	
			
			if(player.playerInBattle.equals("yes") || player.playerInAttack.equals("yes") || player.playerInCast.equals("yes")) {        //hair1brownFBattle2
				if(playerUse.Side.equals("front") && playerUse.Sex.equals("M")) { spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Battle1"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.9f, playerUse.PosY + 10.5f);	 }
				if(playerUse.Side.equals("back")  && playerUse.Sex.equals("M"))  { spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Battle1"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.9f, playerUse.PosY + 10.5f);	 }
				if(playerUse.Side.equals("right")  && playerUse.Sex.equals("M")) { spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Battle1"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 2.5f, playerUse.PosY + 10.8f);	 }
				if(playerUse.Side.equals("left")  && playerUse.Sex.equals("M"))  { spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Battle1"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.5f, playerUse.PosY + 10.8f);	 }
			}
			
			//Female
			if(playerUse.Side.equals("front")  && playerUse.Sex.equals("F")) { spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 2.1f, playerUse.PosY + 10.5f);	 }
			if(playerUse.Side.equals("back")  && playerUse.Sex.equals("F"))  { spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Back"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.9f, playerUse.PosY + 10f);	 }
			if(playerUse.Side.equals("right")  && playerUse.Sex.equals("F")) { spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Right"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 2.2f, playerUse.PosY + 10.2f);	 }
			if(playerUse.Side.equals("left")  && playerUse.Sex.equals("F"))  { spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Left"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.5f, playerUse.PosY + 10.2f);	 }
			
			if(playerUse.Side.equals("left-front") && playerUse.Sex.equals("F") ) {spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 2.1f, playerUse.PosY + 10.5f);	 }
			if(playerUse.Side.equals("left-back") && playerUse.Sex.equals("F") ) {spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 2.1f, playerUse.PosY + 10.5f);   }
			if(playerUse.Side.equals("right-back") && playerUse.Sex.equals("F") ) {spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 2.1f, playerUse.PosY + 10.5f);  }
			if(playerUse.Side.equals("right-front") && playerUse.Sex.equals("F") ) {spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 2.1f, playerUse.PosY + 10.5f); }
			if(playerUse.Side.equals("back-right") && playerUse.Sex.equals("F") ) {spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 2.1f, playerUse.PosY + 10.5f);  }
			if(playerUse.Side.equals("back-left") && playerUse.Sex.equals("F") ) {spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 2.1f, playerUse.PosY + 10.5f);  }
			if(playerUse.Side.equals("front-right") && playerUse.Sex.equals("F") ) {spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 2.1f, playerUse.PosY + 10.5f); }
			if(playerUse.Side.equals("front-left") && playerUse.Sex.equals("F") ) {spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 2.1f, playerUse.PosY + 10.5f); }	
			
			if(player.playerInBattle.equals("yes") || player.playerInAttack.equals("yes") || player.playerInCast.equals("yes")) {        
				if(playerUse.Side.equals("front") && playerUse.Sex.equals("F")) { spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Battle1"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.9f, playerUse.PosY + 10.5f);	 }
				if(playerUse.Side.equals("back")  && playerUse.Sex.equals("F"))  { spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Battle1"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.9f, playerUse.PosY + 10.5f);	 }
				if(playerUse.Side.equals("right")  && playerUse.Sex.equals("F")) { spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Battle1"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 2.5f, playerUse.PosY + 10.8f);	 }
				if(playerUse.Side.equals("left")  && playerUse.Sex.equals("F"))  { spr_master = atlas_hairs.createSprite(playerUse.Hair + playerUse.Color + playerUse.Sex + "Battle1"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.5f, playerUse.PosY + 10.8f);	 }
			}
			
			if(!type.equals("online")) {
				if(state.equals("menu")) {			
					if(playerUse.Sex.equals("M")) {
						spr_master = atlas_hairs.createSprite(player.Hair + player.Color + player.Sex + "Front");
						spr_master.setSize(10, 18);
						spr_master.setPosition(-52.3f,18.5f);
					}
					if(playerUse.Sex.equals("F")) {
						spr_master = atlas_hairs.createSprite(player.Hair + player.Color + player.Sex + "Front");
						spr_master.setSize(10, 18);
						spr_master.setPosition(-52.3f,18.5f);
					}
				}
			}
			
			return spr_master;
		}
		
		public Sprite SetWeapon(GameObject playerUse) {   
			
			//player.playerInBattle = "no";
			//playerInAttack = true;
			
			if(playerUse.Job.equals("Aprendiz")) { atlas_weapongeneric = atlas_nknifes; }
			if(playerUse.Job.equals("Espadachim")) { atlas_weapongeneric = atlas_swords; }
			if(playerUse.Job.equals("Feiticeiro")) { atlas_weapongeneric = atlas_rods; }
			if(playerUse.Job.equals("Batedor")) { atlas_weapongeneric = atlas_axes; }
			if(playerUse.Job.equals("Pistoleiro")) { atlas_weapongeneric = atlas_pistols; }
			if(playerUse.Job.equals("Medico")) { atlas_weapongeneric = atlas_rods; }
			if(playerUse.Job.equals("Ladrao")) { atlas_weapongeneric = atlas_daggers; }
				
			if(playerUse.playerInBattle.equals("yes")) {
				if(playerUse.Job.equals("Aprendiz")) {
					if(playerUse.Weapon.equals("basicknife")) { spr_master = atlas_nknifes.createSprite("basic_knife_right"); spr_master.setSize(12, 18); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
					if(playerUse.Weapon.equals("doubleedgeknife")) { spr_master = atlas_nknifes.createSprite("doubleedge_knife_right"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
				}
			}
			
			if(playerUse.playerInAttack.equals("yes")) {
				if(playerUse.Job.equals("Aprendiz")) {
					if(playerUse.Weapon.equals("basicknife")) { spr_master = atlas_nknifes.createSprite("basic_knife_attack_right"); spr_master.setSize(12, 18); spr_master.setPosition(playerUse.PosX + 2.2f, playerUse.PosY + 2f);  }
					if(playerUse.Weapon.equals("doubleedgeknife")) { spr_master = atlas_nknifes.createSprite("doubleedge_knife_right"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 2.2f, playerUse.PosY + 2f);  }
				}
			}
			
			return spr_master;
			
		}
		
		
		public Sprite SetCharHat(GameObject playerUse, String type) { 
			
			if(playerUse.Side.equals("front")) { spr_master = atlas_hatFrame.createSprite(playerUse.Hat + "_front"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.9f, playerUse.PosY + 11.5f); }
			if(playerUse.Side.equals("back"))  { spr_master = atlas_hatFrame.createSprite(playerUse.Hat + "_back"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.9f, playerUse.PosY + 9f); }
			if(playerUse.Side.equals("right")) { spr_master = atlas_hatFrame.createSprite(playerUse.Hat + "_right"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 2.5f, playerUse.PosY + 10.8f); }
			if(playerUse.Side.equals("left"))  { spr_master = atlas_hatFrame.createSprite(playerUse.Hat + "_left"); spr_master.setSize(6, 11); spr_master.setPosition(playerUse.PosX + 1.5f, playerUse.PosY + 10.8f);	}
			
			if(state.equals("menu")) {		
				spr_master = atlas_hatFrame.createSprite(playerUse.Hat + "_front");
				spr_master.setSize(10, 18);
				spr_master.setPosition(-52.3f,20.5f);
			}
			
			return spr_master;
		}
		
		//[Mobs]//
		//Mostra mobs
		public void ShowMobs() {			
			if(player.Map.equals("Sewers") || player.Map.equals("Watercave") || player.Map.equals("Mines") || player.Map.equals("Snowpalace") || player.Map.equals("Tower")) {
				for(int i = 0; i < lstMobs.size(); i++) {
					
						//Target do player
						if(player.Target.equals(lstMobs.get(i).MobID)) {
							spr_target = atlas_gameUI.createSprite("target");
							spr_target.setPosition(lstMobs.get(i).MobPosX + 2, lstMobs.get(i).MobPosY + 16);
							spr_target.setSize(4,8);
							spr_target.draw(game.batch);
						}
									
						mobTimerFrame = lstMobs.get(i).MobFrameTime;
						if(mobTimerFrame > 0) {
							mobTimerFrame--;
							lstMobs.get(i).MobFrameTime = mobTimerFrame;
						}
						if(mobTimerFrame <= 0) {
							mobTimerFrame = 40;
							mobFrame = lstMobs.get(i).MobFrame;
							mobFrame++;
							if(mobFrame > 3) { mobFrame = 1;}
							lstMobs.get(i).MobFrame = mobFrame;
							lstMobs.get(i).MobFrameTime = 40;
						}
						
						//Sem Target
						if(lstMobs.get(i).MobTarget.equals("none")) {
							mobTimerMov = lstMobs.get(i).MobTimerMov;
							mobRandomSt = lstMobs.get(i).MobRandomSt;
							if(mobTimerMov >= 0) { 
								mobTimerMov--;
								lstMobs.get(i).MobTimerMov = mobTimerMov;
							}
							if(mobTimerMov < 0) { 
								mobRandomSt = randnumber.nextInt(4); 
								mobTimerMov = 100; 
								lstMobs.get(i).MobTimerMov = mobTimerMov;
								lstMobs.get(i).MobRandomSt = mobRandomSt;
							}
												
							if(mobRandomSt == 0) { 
								mobPositionCoordX = lstMobs.get(i).MobPosX;
								mobPositionCoordX = mobPositionCoordX + 0.07f; 
								lstMobs.get(i).MobPosX = mobPositionCoordX;
							}
							if(mobRandomSt == 1) { 
								mobPositionCoordX = lstMobs.get(i).MobPosX;
								mobPositionCoordX = mobPositionCoordX - 0.07f;
								lstMobs.get(i).MobPosX = mobPositionCoordX;
							}
							if(mobRandomSt == 2) { 
								mobPositionCoordY = lstMobs.get(i).MobPosY;
								mobPositionCoordY = mobPositionCoordY + 0.07f;
								lstMobs.get(i).MobPosY = mobPositionCoordY;
							}
							if(mobRandomSt == 3) { 
								mobPositionCoordY = lstMobs.get(i).MobPosY;
								mobPositionCoordY = mobPositionCoordY - 0.07f;
								lstMobs.get(i).MobPosY = mobPositionCoordY;
							}
						}
						
						if(lstMobs.get(i).MobTarget.equals(player.Name)) {
							if(lstMobs.get(i).MobPosX < player.PosX + 3) { lstMobs.get(i).MobPosX = lstMobs.get(i).MobPosX + 0.07f; }
							if(lstMobs.get(i).MobPosX > player.PosX + 3) { lstMobs.get(i).MobPosX = lstMobs.get(i).MobPosX - 0.07f; }
							if(lstMobs.get(i).MobPosY < player.PosY - 6 ) { lstMobs.get(i).MobPosY = lstMobs.get(i).MobPosY + 0.07f; }
							if(lstMobs.get(i).MobPosY > player.PosY - 6) { lstMobs.get(i).MobPosY = lstMobs.get(i).MobPosY - 0.07f; }
						}
						
						//Limit screen
						if(mobPositionCoordY >= 57 && lstMobs.get(i).MobDead.equals("no")) 
						{						
							lstMobs.get(i).MobPosY = 0;
							lstMobs.get(i).MobPosX = 0;
						}
						if(mobPositionCoordY <= -73.5f && lstMobs.get(i).MobDead.equals("no")) 
						{
							lstMobs.get(i).MobPosY = 0;
							lstMobs.get(i).MobPosX = 0;
						}
						if(mobPositionCoordX >= 45f && lstMobs.get(i).MobDead.equals("no")) 
						{
							lstMobs.get(i).MobPosY = 0;
							lstMobs.get(i).MobPosX = 0;
						}
						if(mobPositionCoordX <= -66.5f && lstMobs.get(i).MobDead.equals("no")) 
						{
							lstMobs.get(i).MobPosY = 0;
							lstMobs.get(i).MobPosX = 0;
						}
						
						if(player.Map.equals("Sewers")) { spr_mob = atlas_mobSewers.createSprite(lstMobs.get(i).MobName + lstMobs.get(i).MobFrame + "L"); }
						if(player.Map.equals("Watercave")) { spr_mob = atlas_mobWatercave.createSprite(lstMobs.get(i).MobName + lstMobs.get(i).MobFrame + "L"); }
						if(player.Map.equals("Mines")) { spr_mob = atlas_mobMines.createSprite(lstMobs.get(i).MobName + lstMobs.get(i).MobFrame + "L"); }
						if(player.Map.equals("Snowpalace")) { spr_mob = atlas_mobSnowpalace.createSprite(lstMobs.get(i).MobName + lstMobs.get(i).MobFrame + "L"); }
						if(player.Map.equals("Tower")) { spr_mob = atlas_mobTower.createSprite(lstMobs.get(i).MobName + lstMobs.get(i).MobFrame + "L"); }
						spr_mob.setPosition(lstMobs.get(i).MobPosX, lstMobs.get(i).MobPosY);
						spr_mob.setSize(lstMobs.get(i).MobSizeX, lstMobs.get(i).MobSizeY);
						spr_mob.draw(game.batch);
						
						
						mobPositionCoordX = lstMobs.get(i).MobPosX;
						mobPositionCoordY = lstMobs.get(i).MobPosY;
						mobPositionCoordY = mobPositionCoordY - 0.2f;
						font_master.draw(game.batch, lstMobs.get(i).MobName + " HP :" + lstMobs.get(i).MobHp + "/" + lstMobs.get(i).MobHpMax ,mobPositionCoordX, mobPositionCoordY);
					}			
			}
		}
		
		public void ShowMobsOnline() {			
			if(player.Map.equals("Sewers") || player.Map.equals("Watercave") || player.Map.equals("Mines") || player.Map.equals("Snowpalace") || player.Map.equals("Tower")) {
				for(int i = 0; i < lstMobs.size(); i++) {
					
						//if(!lstMobs.get(i).MobID.equals("SlimeA") && lstMobs.get(i).MobID.equals("SlimeB")) { return; }
					
						//Target do player
						if(player.Target.equals(lstMobs.get(i).MobID)) {
							spr_target = atlas_gameUI.createSprite("target");
							spr_target.setPosition(lstMobs.get(i).MobPosX + 2, lstMobs.get(i).MobPosY + 16);
							spr_target.setSize(4,8);
							spr_target.draw(game.batch);
						}
											
						//Frame do Mob
						mobTimerFrame = lstMobs.get(i).MobFrameTime;
						if(mobTimerFrame > 0) {
							mobTimerFrame--;
							lstMobs.get(i).MobFrameTime = mobTimerFrame;
						}
						if(mobTimerFrame <= 0) {
							mobTimerFrame = 40;
							mobFrame = lstMobs.get(i).MobFrame;
							mobFrame++;
							if(mobFrame > 3) { mobFrame = 1;}
							lstMobs.get(i).MobFrame = mobFrame;
							lstMobs.get(i).MobFrameTime = 40;
						}
						
						//Sem Target
						if(lstMobs.get(i).MobTarget.equals("none")) {
							mobTimerMov = lstMobs.get(i).MobTimerMov;
							mobRandomSt = lstMobs.get(i).MobRandomSt;
							if(mobTimerMov >= 0) { 
								mobTimerMov--;
								lstMobs.get(i).MobTimerMov = mobTimerMov;
							}
							if(mobTimerMov < 0) { 
								mobRandomSt = randnumber.nextInt(4); 
								mobTimerMov = 100; 
								lstMobs.get(i).MobTimerMov = mobTimerMov;
								lstMobs.get(i).MobRandomSt = mobRandomSt;
							}
							
							if(playerMobSync) {
								if(mobRandomSt == 0) { 
									mobPositionCoordX = lstMobs.get(i).MobPosX;
									mobPositionCoordX = mobPositionCoordX + 0.07f; 
									lstMobs.get(i).MobPosX = mobPositionCoordX;
								}
								if(mobRandomSt == 1) { 
									mobPositionCoordX = lstMobs.get(i).MobPosX;
									mobPositionCoordX = mobPositionCoordX - 0.07f;
									lstMobs.get(i).MobPosX = mobPositionCoordX;
								}
								if(mobRandomSt == 2) { 
									mobPositionCoordY = lstMobs.get(i).MobPosY;
									mobPositionCoordY = mobPositionCoordY + 0.07f;
									lstMobs.get(i).MobPosY = mobPositionCoordY;
								}
								if(mobRandomSt == 3) { 
									mobPositionCoordY = lstMobs.get(i).MobPosY;
									mobPositionCoordY = mobPositionCoordY - 0.07f;
									lstMobs.get(i).MobPosY = mobPositionCoordY;
								}
							}
							else {
								mobPositionCoordX = lstMobs.get(i).MobPosX;
								lstMobs.get(i).MobPosX = mobPositionCoordX;
								
								mobPositionCoordY = lstMobs.get(i).MobPosY;
								lstMobs.get(i).MobPosY = mobPositionCoordY;
							}
						}
						else {
							//Segue o jogador Online
							for(int x = 0; x < lstOnlinePlayers.size(); x++) {
								if(lstMobs.get(i).MobTarget.equals(lstOnlinePlayers.get(x).Name)) {
									if(lstMobs.get(i).MobPosX < lstOnlinePlayers.get(x).PosX + 3) { lstMobs.get(i).MobPosX = lstMobs.get(i).MobPosX + 0.07f; }
									if(lstMobs.get(i).MobPosX > lstOnlinePlayers.get(x).PosX + 3) { lstMobs.get(i).MobPosX = lstMobs.get(i).MobPosX - 0.07f; }
									if(lstMobs.get(i).MobPosY < lstOnlinePlayers.get(x).PosY - 6) { lstMobs.get(i).MobPosY = lstMobs.get(i).MobPosY + 0.07f; }
									if(lstMobs.get(i).MobPosY > lstOnlinePlayers.get(x).PosY - 6) { lstMobs.get(i).MobPosY = lstMobs.get(i).MobPosY - 0.07f; }
								}
							}
						}
						
						//Limit screen
						if(mobPositionCoordY >= 57 && lstMobs.get(i).MobDead.equals("no")) 
						{						
							lstMobs.get(i).MobPosY = 0;
							lstMobs.get(i).MobPosX = 0;
						}
						if(mobPositionCoordY <= -73.5f && lstMobs.get(i).MobDead.equals("no")) 
						{
							lstMobs.get(i).MobPosY = 0;
							lstMobs.get(i).MobPosX = 0;
						}
						if(mobPositionCoordX >= 45f && lstMobs.get(i).MobDead.equals("no")) 
						{
							lstMobs.get(i).MobPosY = 0;
							lstMobs.get(i).MobPosX = 0;
						}
						if(mobPositionCoordX <= -66.5f && lstMobs.get(i).MobDead.equals("no")) 
						{
							lstMobs.get(i).MobPosY = 0;
							lstMobs.get(i).MobPosX = 0;
						}
						
						if(player.Map.equals("Sewers")) { spr_mob = atlas_mobSewers.createSprite(lstMobs.get(i).MobName + lstMobs.get(i).MobFrame + "L"); }
						if(player.Map.equals("Watercave")) { spr_mob = atlas_mobWatercave.createSprite(lstMobs.get(i).MobName + lstMobs.get(i).MobFrame + "L"); }
						if(player.Map.equals("Mines")) { spr_mob = atlas_mobMines.createSprite(lstMobs.get(i).MobName + lstMobs.get(i).MobFrame + "L"); }
						if(player.Map.equals("Snowpalace")) { spr_mob = atlas_mobSnowpalace.createSprite(lstMobs.get(i).MobName + lstMobs.get(i).MobFrame + "L"); }
						if(player.Map.equals("Tower")) { spr_mob = atlas_mobTower.createSprite(lstMobs.get(i).MobName + lstMobs.get(i).MobFrame + "L"); }
						spr_mob.setPosition(lstMobs.get(i).MobPosX, lstMobs.get(i).MobPosY);
						spr_mob.setSize(lstMobs.get(i).MobSizeX, lstMobs.get(i).MobSizeY);
						spr_mob.draw(game.batch);
											
						mobPositionCoordX = lstMobs.get(i).MobPosX;
						mobPositionCoordY = lstMobs.get(i).MobPosY;
						mobPositionCoordY = mobPositionCoordY - 0.2f;
						font_master.draw(game.batch, lstMobs.get(i).MobName + " HP :" + lstMobs.get(i).MobHp + "/" + lstMobs.get(i).MobHpMax ,mobPositionCoordX, mobPositionCoordY);
					}			
			}
		}
		
		public void CheckRegenTime() {		
			player.regenTime--;		
			if(player.regenTime <= 0) {
				player.Hp = player.Hp + 15;
				if(player.Hp > player.HpMax) { player.Hp = player.HpMax; }
				
				player.Mp = player.Mp + 10;
				if(player.Mp > player.MpMax) { player.Mp = player.MpMax; }
				player.regenTime = player.regenTimeMax;
			}		
		}
			
		//CarregaMobs
		public void LoadMobsSewers() {
			
			lstMobs = new ArrayList<GameObject>();
			lstMobs.clear();
			
			if(player.Map.equals("Sewers")) {
				GameObject newMob1 = new GameObject();
				newMob1.MobName = "slime"; 
				newMob1.MobHp = 100;
				newMob1.MobMp = 30;
				newMob1.MobHpMax = 100; 
				newMob1.MobMpMax = 30;
				newMob1.MobFrame = 1;
				newMob1.MobPosX = 0;
				newMob1.MobPosY = 0;
				newMob1.MobExp = 2;
				newMob1.MobID = "SlimeA";
				newMob1.MobMoney = 1;
				newMob1.MobSizeY = 17;
				newMob1.MobSizeX = 9;
				newMob1.MobTarget = "none";
				newMob1.MobDead = "no";
				newMob1.MobTimeDead = 0;
				newMob1.MobSkillChance = "5";
				newMob1.MobAtk = 5;
				newMob1.MobEvade = 10;
				newMob1.MobPosition = "L";
				newMob1.MobRandomSt = 1;
				newMob1.MobTimerMov = 50;
				newMob1.MobFrameTime = 40;
				newMob1.MobTimeDead = 250;
				newMob1.MobDead = "no";
				newMob1.MobAtkTimer = 250;
				newMob1.MobAtkTimerMax = 250;
				newMob1.MobLevel = 1;
				newMob1.Map = "Sewers";
				lstMobs.add(newMob1);
				
				GameObject newMob2 = new GameObject();
				newMob2.MobName = "slime"; 
				newMob2.MobHp = 100;
				newMob2.MobMp = 30;
				newMob2.MobHpMax = 100; 
				newMob2.MobMpMax = 30;
				newMob2.MobFrame = 1;
				newMob2.MobPosX = 15;
				newMob2.MobPosY = 10;
				newMob2.MobExp = 2;
				newMob2.MobID = "SlimeB";
				newMob2.MobMoney = 1;
				newMob2.MobSizeY = 17;
				newMob2.MobSizeX = 9;
				newMob2.MobTarget = "none";
				newMob2.MobDead = "no";
				newMob2.MobTimeDead = 0;
				newMob2.MobSkillChance = "5";
				newMob2.MobAtk = 5;
				newMob2.MobEvade = 2;
				newMob2.MobPosition = "L";
				newMob2.MobRandomSt = 2;
				newMob2.MobTimerMov = 60;
				newMob2.MobFrameTime = 40;
				newMob2.MobTimeDead = 250;
				newMob2.MobDead = "no";
				newMob2.MobAtkTimer = 250;
				newMob2.MobAtkTimerMax = 250;
				newMob2.MobLevel = 1;
				newMob2.Map = "Sewers";
				lstMobs.add(newMob2);
				
				GameObject newMob3 = new GameObject();
				newMob3.MobName = "oikplant"; 
				newMob3.MobHp = 150;
				newMob3.MobMp = 30;
				newMob3.MobHpMax = 150; 
				newMob3.MobMpMax = 30;
				newMob3.MobFrame = 1;
				newMob3.MobPosX = -35;
				newMob3.MobPosY = -45;
				newMob3.MobExp = 10;
				newMob3.MobID = "OikPlantA";
				newMob3.MobMoney = 2;
				newMob3.MobSizeY = 22;
				newMob3.MobSizeX = 11;
				newMob3.MobTarget = "none";
				newMob3.MobDead = "no";
				newMob3.MobTimeDead = 0;
				newMob3.MobSkillChance = "5";
				newMob3.MobAtk = 15;
				newMob3.MobEvade = 10;
				newMob3.MobPosition = "L";
				newMob3.MobRandomSt = 0;
				newMob3.MobTimerMov = 80;
				newMob3.MobFrameTime = 40;
				newMob3.MobTimeDead = 250;
				newMob3.MobDead = "no";
				newMob3.MobAtkTimer = 250;
				newMob3.MobAtkTimerMax = 250;
				newMob3.MobLevel = 1;
				newMob3.Map = "Sewers";
				lstMobs.add(newMob3);
				
				GameObject newMob4 = new GameObject();
				newMob4.MobName = "poro"; 
				newMob4.MobHp = 200;
				newMob4.MobMp = 30;
				newMob4.MobHpMax = 200; 
				newMob4.MobMpMax = 30;
				newMob4.MobFrame = 1;
				newMob4.MobPosX = -35;
				newMob4.MobPosY = -45;
				newMob4.MobExp = 25;
				newMob4.MobID = "PoroA";
				newMob4.MobMoney = 2;
				newMob4.MobSizeY = 17;
				newMob4.MobSizeX = 9;
				newMob4.MobTarget = "none";
				newMob4.MobDead = "no";
				newMob4.MobTimeDead = 0;
				newMob4.MobSkillChance = "5";
				newMob4.MobAtk = 35;
				newMob4.MobEvade = 15;
				newMob4.MobPosition = "L";
				newMob4.MobRandomSt = 3;
				newMob4.MobTimerMov = 100;
				newMob4.MobFrameTime = 40;
				newMob4.MobTimeDead = 250;
				newMob4.MobDead = "no";
				newMob4.MobAtkTimer = 320;
				newMob4.MobAtkTimerMax = 320;
				newMob4.MobLevel = 1;
				newMob4.Map = "Sewers";
				lstMobs.add(newMob4);
			}
		}
		
		public void LoadMobsWatercave() {
			
			lstMobs = new ArrayList<GameObject>();
			lstMobs.clear();
			
			if(player.Map.equals("Watercave")) {
				GameObject newMob1 = new GameObject();
				newMob1.MobName = "poyo"; 
				newMob1.MobHp = 450;
				newMob1.MobMp = 50;
				newMob1.MobHpMax = 450;
				newMob1.MobMpMax = 50;
				newMob1.MobFrame = 1;
				newMob1.MobPosX = 0;
				newMob1.MobPosY = 0;
				newMob1.MobExp = 80;
				newMob1.MobID = "PoyoA";
				newMob1.MobMoney = 1;
				newMob1.MobSizeY = 13;
				newMob1.MobSizeX = 15;
				newMob1.MobTarget = "none";
				newMob1.MobDead = "no";
				newMob1.MobTimeDead = 0;
				newMob1.MobSkillChance = "5";
				newMob1.MobAtk = 45;
				newMob1.MobEvade = 10;
				newMob1.MobPosition = "L";
				newMob1.MobRandomSt = 1;
				newMob1.MobTimerMov = 50;
				newMob1.MobFrameTime = 40;
				newMob1.MobTimeDead = 250;
				newMob1.MobDead = "no";
				newMob1.MobAtkTimer = 350;
				newMob1.MobAtkTimerMax = 350;
				newMob1.MobLevel = 2;
				newMob1.Map = "Watercave";
				lstMobs.add(newMob1);
				
				GameObject newMob2 = new GameObject();
				newMob2.MobName = "poyo"; 
				newMob2.MobHp = 450;
				newMob2.MobMp = 50;
				newMob2.MobHpMax = 450; 
				newMob2.MobMpMax = 50;
				newMob2.MobFrame = 1;
				newMob2.MobPosX = 13;
				newMob2.MobPosY = 15;
				newMob2.MobExp = 80;
				newMob2.MobID = "PoyoB";
				newMob2.MobMoney = 1;
				newMob2.MobSizeY = 13;
				newMob2.MobSizeX = 15;
				newMob2.MobTarget = "none";
				newMob2.MobDead = "no";
				newMob2.MobTimeDead = 0;
				newMob2.MobSkillChance = "5";
				newMob2.MobAtk = 45;
				newMob2.MobEvade = 10;
				newMob2.MobPosition = "L";
				newMob2.MobRandomSt = 2;
				newMob2.MobTimerMov = 60;
				newMob2.MobFrameTime = 40;
				newMob2.MobTimeDead = 250;
				newMob2.MobDead = "no";
				newMob2.MobAtkTimer = 350;
				newMob2.MobAtkTimerMax = 350;
				newMob2.MobLevel = 2;
				newMob2.Map = "Watercave";
				lstMobs.add(newMob2);
				
				GameObject newMob3 = new GameObject();
				newMob3.MobName = "aranoid"; 
				newMob3.MobHp = 620;
				newMob3.MobMp = 50;
				newMob3.MobHpMax = 620; 
				newMob3.MobMpMax = 50;
				newMob3.MobFrame = 1;
				newMob3.MobPosX = -35;
				newMob3.MobPosY = -45;
				newMob3.MobExp = 150;
				newMob3.MobID = "AranoidA";
				newMob3.MobMoney = 1;
				newMob3.MobSizeY = 22;
				newMob3.MobSizeX = 11;
				newMob3.MobTarget = "none";
				newMob3.MobDead = "no";
				newMob3.MobTimeDead = 0;
				newMob3.MobSkillChance = "5";
				newMob3.MobAtk = 55;
				newMob3.MobEvade = 10;
				newMob3.MobPosition = "L";
				newMob3.MobRandomSt = 0;
				newMob3.MobTimerMov = 80;
				newMob3.MobFrameTime = 40;
				newMob3.MobTimeDead = 250;
				newMob3.MobDead = "no";
				newMob3.MobAtkTimer = 200;
				newMob3.MobAtkTimerMax = 200;
				newMob3.MobLevel = 2;
				newMob3.Map = "Watercave";
				lstMobs.add(newMob3);
				
				GameObject newMob4 = new GameObject();
				newMob4.MobName = "shark"; 
				newMob4.MobHp = 890;
				newMob4.MobMp = 50;
				newMob4.MobHpMax = 890; 
				newMob4.MobMpMax = 50;
				newMob4.MobFrame = 1;
				newMob4.MobPosX = -35;
				newMob4.MobPosY = -45;
				newMob4.MobExp = 320;
				newMob4.MobID = "SharkA";
				newMob4.MobMoney = 3;
				newMob4.MobSizeY = 20;
				newMob4.MobSizeX = 24;
				newMob4.MobTarget = "none";
				newMob4.MobDead = "no";
				newMob4.MobTimeDead = 0;
				newMob4.MobSkillChance = "5";
				newMob4.MobAtk = 70;
				newMob4.MobEvade = 10;
				newMob4.MobPosition = "L";
				newMob4.MobRandomSt = 3;
				newMob4.MobTimerMov = 100;
				newMob4.MobFrameTime = 40;
				newMob4.MobTimeDead = 250;
				newMob4.MobDead = "no";
				newMob4.MobAtkTimer = 400;
				newMob4.MobAtkTimerMax = 400;
				newMob4.MobLevel = 2;
				newMob4.Map = "Watercave";
				lstMobs.add(newMob4);
			}
		}
		
		public void LoadMobsMines() {
			
			lstMobs = new ArrayList<GameObject>();
			lstMobs.clear();
			
			if(player.Map.equals("Mines")) {
				GameObject newMob1 = new GameObject();
				newMob1.MobName = "fire"; 
				newMob1.MobHp = 710;
				newMob1.MobMp = 50;
				newMob1.MobHpMax = 710;
				newMob1.MobMpMax = 50;
				newMob1.MobFrame = 1;
				newMob1.MobPosX = 0;
				newMob1.MobPosY = 0;
				newMob1.MobExp = 650;
				newMob1.MobID = "FireA";
				newMob1.MobMoney = 2;
				newMob1.MobSizeY = 17;
				newMob1.MobSizeX = 9;
				newMob1.MobTarget = "none";
				newMob1.MobDead = "no";
				newMob1.MobTimeDead = 0;
				newMob1.MobSkillChance = "5";
				newMob1.MobAtk = 75;
				newMob1.MobEvade = 10;
				newMob1.MobPosition = "L";
				newMob1.MobRandomSt = 1;
				newMob1.MobTimerMov = 50;
				newMob1.MobFrameTime = 40;
				newMob1.MobTimeDead = 250;
				newMob1.MobDead = "no";
				newMob1.MobAtkTimer = 350;
				newMob1.MobAtkTimerMax = 350;
				newMob1.MobLevel = 3;
				newMob1.Map = "Mines";
				lstMobs.add(newMob1);
				
				GameObject newMob2 = new GameObject();
				newMob2.MobName = "worm"; 
				newMob2.MobHp = 1230;
				newMob2.MobMp = 50;
				newMob2.MobHpMax = 1230; 
				newMob2.MobMpMax = 50;
				newMob2.MobFrame = 1;
				newMob2.MobPosX = 15;
				newMob2.MobPosY = 10;
				newMob2.MobExp = 1200;
				newMob2.MobID = "WormB";
				newMob2.MobMoney = 2;
				newMob2.MobSizeY = 17;
				newMob2.MobSizeX = 9;
				newMob2.MobTarget = "none";
				newMob2.MobDead = "no";
				newMob2.MobTimeDead = 0;
				newMob2.MobSkillChance = "5";
				newMob2.MobAtk = 90;
				newMob2.MobEvade = 10;
				newMob2.MobPosition = "L";
				newMob2.MobRandomSt = 2;
				newMob2.MobTimerMov = 60;
				newMob2.MobFrameTime = 40;
				newMob2.MobTimeDead = 250;
				newMob2.MobDead = "no";
				newMob2.MobAtkTimer = 350;
				newMob2.MobAtkTimerMax = 350;
				newMob2.MobLevel = 3;
				newMob2.Map = "Mines";
				lstMobs.add(newMob2);
				
				GameObject newMob3 = new GameObject();
				newMob3.MobName = "golem"; 
				newMob3.MobHp = 9600;
				newMob3.MobMp = 50;
				newMob3.MobHpMax = 4204; 
				newMob3.MobMpMax = 50;
				newMob3.MobFrame = 1;
				newMob3.MobPosX = -35;
				newMob3.MobPosY = -45;
				newMob3.MobExp = 5800;
				newMob3.MobID = "GolemA";
				newMob3.MobMoney = 3;
				newMob3.MobSizeY = 28;
				newMob3.MobSizeX = 16;
				newMob3.MobTarget = "none";
				newMob3.MobDead = "no";
				newMob3.MobTimeDead = 0;
				newMob3.MobSkillChance = "5";
				newMob3.MobAtk = 150;
				newMob3.MobEvade = 10;
				newMob3.MobPosition = "L";
				newMob3.MobRandomSt = 0;
				newMob3.MobTimerMov = 80;
				newMob3.MobFrameTime = 40;
				newMob3.MobTimeDead = 250;
				newMob3.MobDead = "no";
				newMob3.MobAtkTimer = 550;
				newMob3.MobAtkTimerMax = 550;
				newMob3.MobLevel = 3;
				newMob3.Map = "Mines";
				lstMobs.add(newMob3);
				
				GameObject newMob4 = new GameObject();
				newMob4.MobName = "death"; 
				newMob4.MobHp = 1700;
				newMob4.MobMp = 50;
				newMob4.MobHpMax = 1700; 
				newMob4.MobMpMax = 50;
				newMob4.MobFrame = 1;
				newMob4.MobPosX = -35;
				newMob4.MobPosY = -45;
				newMob4.MobExp = 2220;
				newMob4.MobID = "DeathA";
				newMob4.MobMoney = 3;
				newMob4.MobSizeY = 18;
				newMob4.MobSizeX = 14;
				newMob4.MobTarget = "none";
				newMob4.MobDead = "no";
				newMob4.MobTimeDead = 0;
				newMob4.MobSkillChance = "5";
				newMob4.MobAtk = 120;
				newMob4.MobEvade = 10;
				newMob4.MobPosition = "L";
				newMob4.MobRandomSt = 3;
				newMob4.MobTimerMov = 100;
				newMob4.MobFrameTime = 40;
				newMob4.MobTimeDead = 250;
				newMob4.MobDead = "no";
				newMob4.MobAtkTimer = 400;
				newMob4.MobAtkTimerMax = 400;
				newMob4.MobLevel = 3;
				newMob4.Map = "Mines";
				lstMobs.add(newMob4);
			}
		}
		
		public void LoadMobsSnowpalace() {
			
			lstMobs = new ArrayList<GameObject>();
			lstMobs.clear();
			
			if(player.Map.equals("Snowpalace")) {
				GameObject newMob1 = new GameObject();
				newMob1.MobName = "goblin"; 
				newMob1.MobHp = 3500;
				newMob1.MobMp = 50;
				newMob1.MobHpMax = 3500;
				newMob1.MobMpMax = 50;
				newMob1.MobFrame = 1;
				newMob1.MobPosX = 0;
				newMob1.MobPosY = 0;
				newMob1.MobExp = 3520;
				newMob1.MobID = "GoblinA";
				newMob1.MobMoney = 2;
				newMob1.MobSizeY = 17;
				newMob1.MobSizeX = 9;
				newMob1.MobTarget = "none";
				newMob1.MobDead = "no";
				newMob1.MobTimeDead = 0;
				newMob1.MobSkillChance = "5";
				newMob1.MobAtk = 220;
				newMob1.MobEvade = 10;
				newMob1.MobPosition = "L";
				newMob1.MobRandomSt = 1;
				newMob1.MobTimerMov = 50;
				newMob1.MobFrameTime = 40;
				newMob1.MobTimeDead = 250;
				newMob1.MobDead = "no";
				newMob1.MobAtkTimer = 400;
				newMob1.MobAtkTimerMax = 400;
				newMob1.MobLevel = 4;
				newMob1.Map = "Snowpalace";
				lstMobs.add(newMob1);
				
				GameObject newMob2 = new GameObject();
				newMob2.MobName = "pinguim"; 
				newMob2.MobHp = 4200;
				newMob2.MobMp = 50;
				newMob2.MobHpMax = 4200; 
				newMob2.MobMpMax = 50;
				newMob2.MobFrame = 1;
				newMob2.MobPosX = 15;
				newMob2.MobPosY = 10;
				newMob2.MobExp = 5320;
				newMob2.MobID = "PinguimA";
				newMob2.MobMoney = 2;
				newMob2.MobSizeY = 17;
				newMob2.MobSizeX = 9;
				newMob2.MobTarget = "none";
				newMob2.MobDead = "no";
				newMob2.MobTimeDead = 0;
				newMob2.MobSkillChance = "5";
				newMob2.MobAtk = 270;
				newMob2.MobEvade = 10;
				newMob2.MobPosition = "L";
				newMob2.MobRandomSt = 2;
				newMob2.MobTimerMov = 60;
				newMob2.MobFrameTime = 40;
				newMob2.MobTimeDead = 250;
				newMob2.MobDead = "no";
				newMob2.MobAtkTimer = 400;
				newMob2.MobAtkTimerMax = 400;
				newMob2.MobLevel = 4;
				newMob2.Map = "Snowpalace";
				lstMobs.add(newMob2);
				
				GameObject newMob3 = new GameObject();
				newMob3.MobName = "snowman"; 
				newMob3.MobHp = 5400;
				newMob3.MobMp = 50;
				newMob3.MobHpMax = 5400; 
				newMob3.MobMpMax = 50;
				newMob3.MobFrame = 1;
				newMob3.MobPosX = -35;
				newMob3.MobPosY = -45;
				newMob3.MobExp = 7420;
				newMob3.MobID = "SnowmanA";
				newMob3.MobMoney = 2;
				newMob3.MobSizeY = 22;
				newMob3.MobSizeX = 11;
				newMob3.MobTarget = "none";
				newMob3.MobDead = "no";
				newMob3.MobTimeDead = 0;
				newMob3.MobSkillChance = "5";
				newMob3.MobAtk = 290;
				newMob3.MobEvade = 10;
				newMob3.MobPosition = "L";
				newMob3.MobRandomSt = 0;
				newMob3.MobTimerMov = 80;
				newMob3.MobFrameTime = 40;
				newMob3.MobTimeDead = 250;
				newMob3.MobDead = "no";
				newMob3.MobAtkTimer = 400;
				newMob3.MobAtkTimerMax = 400;
				newMob3.MobLevel = 4;
				newMob3.Map = "Snowpalace";
				lstMobs.add(newMob3);
				
				GameObject newMob4 = new GameObject();
				newMob4.MobName = "icewolf"; 
				newMob4.MobHp = 7800;
				newMob4.MobMp = 50;
				newMob4.MobHpMax = 7800; 
				newMob4.MobMpMax = 50;
				newMob4.MobFrame = 1;
				newMob4.MobPosX = -35;
				newMob4.MobPosY = -45;
				newMob4.MobExp = 9620;
				newMob4.MobID = "IcewolfA";
				newMob4.MobMoney = 5;
				newMob4.MobSizeY = 17;
				newMob4.MobSizeX = 9;
				newMob4.MobTarget = "none";
				newMob4.MobDead = "no";
				newMob4.MobTimeDead = 0;
				newMob4.MobSkillChance = "5";
				newMob4.MobAtk = 340;
				newMob4.MobEvade = 10;
				newMob4.MobPosition = "L";
				newMob4.MobRandomSt = 3;
				newMob4.MobTimerMov = 100;
				newMob4.MobFrameTime = 40;
				newMob4.MobTimeDead = 250;
				newMob4.MobDead = "no";
				newMob4.MobAtkTimer = 400;
				newMob4.MobAtkTimerMax = 400;
				newMob4.MobLevel = 4;
				newMob4.Map = "Snowpalace";
				lstMobs.add(newMob4);
			}
		}
		
		public void LoadMobsTower() {
			
			lstMobs = new ArrayList<GameObject>();
			lstMobs.clear();
			
			if(player.Map.equals("Tower")) {
				GameObject newMob1 = new GameObject();
				newMob1.MobName = "succubus"; 
				newMob1.MobHp = 9700;
				newMob1.MobMp = 50;
				newMob1.MobHpMax = 9700;
				newMob1.MobMpMax = 50;
				newMob1.MobFrame = 1;
				newMob1.MobPosX = 0;
				newMob1.MobPosY = 0;
				newMob1.MobExp = 15200;
				newMob1.MobID = "SuccubusA";
				newMob1.MobMoney = 10;
				newMob1.MobSizeY = 17;
				newMob1.MobSizeX = 9;
				newMob1.MobTarget = "none";
				newMob1.MobDead = "no";
				newMob1.MobTimeDead = 0;
				newMob1.MobSkillChance = "5";
				newMob1.MobAtk = 480;
				newMob1.MobEvade = 10;
				newMob1.MobPosition = "L";
				newMob1.MobRandomSt = 1;
				newMob1.MobTimerMov = 50;
				newMob1.MobFrameTime = 40;
				newMob1.MobTimeDead = 250;
				newMob1.MobDead = "no";
				newMob1.MobAtkTimer = 600;
				newMob1.MobAtkTimerMax = 600;
				newMob1.MobLevel = 5;
				newMob1.Map = "Tower";
				lstMobs.add(newMob1);
				
				GameObject newMob2 = new GameObject();
				newMob2.MobName = "Diabolic"; 
				newMob2.MobHp = 11200;
				newMob2.MobMp = 50;
				newMob2.MobHpMax = 11200; 
				newMob2.MobMpMax = 50;
				newMob2.MobFrame = 1;
				newMob2.MobPosX = 15;
				newMob2.MobPosY = 10;
				newMob2.MobExp = 24200;
				newMob2.MobID = "DiabolicA";
				newMob2.MobMoney = 10;
				newMob2.MobSizeY = 17;
				newMob2.MobSizeX = 9;
				newMob2.MobTarget = "none";
				newMob2.MobDead = "no";
				newMob2.MobTimeDead = 0;
				newMob2.MobSkillChance = "5";
				newMob2.MobAtk = 560;
				newMob2.MobEvade = 10;
				newMob2.MobPosition = "L";
				newMob2.MobRandomSt = 2;
				newMob2.MobTimerMov = 60;
				newMob2.MobFrameTime = 40;
				newMob2.MobTimeDead = 250;
				newMob2.MobDead = "no";
				newMob2.MobAtkTimer = 800;
				newMob2.MobAtkTimerMax = 800;
				newMob2.MobLevel = 5;
				newMob2.Map = "Tower";
				lstMobs.add(newMob2);
				
				GameObject newMob3 = new GameObject();
				newMob3.MobName = "Extratos"; 
				newMob3.MobHp = 15000;
				newMob3.MobMp = 50;
				newMob3.MobHpMax = 15000; 
				newMob3.MobMpMax = 50;
				newMob3.MobFrame = 1;
				newMob3.MobPosX = -35;
				newMob3.MobPosY = -45;
				newMob3.MobExp = 35200;
				newMob3.MobID = "ExtratosA";
				newMob3.MobMoney = 10;
				newMob3.MobSizeY = 22;
				newMob3.MobSizeX = 11;
				newMob3.MobTarget = "none";
				newMob3.MobDead = "no";
				newMob3.MobTimeDead = 0;
				newMob3.MobSkillChance = "5";
				newMob3.MobAtk = 720;
				newMob3.MobEvade = 10;
				newMob3.MobPosition = "L";
				newMob3.MobRandomSt = 0;
				newMob3.MobTimerMov = 80;
				newMob3.MobFrameTime = 40;
				newMob3.MobTimeDead = 250;
				newMob3.MobDead = "no";
				newMob3.MobAtkTimer = 700;
				newMob3.MobAtkTimerMax = 700;
				newMob3.MobLevel = 5;
				newMob3.Map = "Tower";
				lstMobs.add(newMob3);
				
				GameObject newMob4 = new GameObject();
				newMob4.MobName = "Demonlord"; 
				newMob4.MobHp = 73000;
				newMob4.MobMp = 50;
				newMob4.MobHpMax = 73000; 
				newMob4.MobMpMax = 50;
				newMob4.MobFrame = 1;
				newMob4.MobPosX = -35;
				newMob4.MobPosY = -45;
				newMob4.MobExp = 755200;
				newMob4.MobID = "DemonLordA";
				newMob4.MobMoney = 10;
				newMob4.MobSizeY = 17;
				newMob4.MobSizeX = 9;
				newMob4.MobTarget = "none";
				newMob4.MobDead = "no";
				newMob4.MobTimeDead = 0;
				newMob4.MobSkillChance = "5";
				newMob4.MobAtk = 1200;
				newMob4.MobEvade = 10;
				newMob4.MobPosition = "L";
				newMob4.MobRandomSt = 3;
				newMob4.MobTimerMov = 100;
				newMob4.MobFrameTime = 40;
				newMob4.MobTimeDead = 250;
				newMob4.MobDead = "no";
				newMob4.MobAtkTimer = 850;
				newMob4.MobAtkTimerMax = 850;
				newMob4.MobLevel = 5;
				newMob4.Map = "Tower";
				lstMobs.add(newMob4);
			}
		}
		
		//Give EXP
		public void GiveExp(int exp) {
			boolean levelup = false;
			if(player.Level == 10) {
				GiveExp = exp;
				timerGiveExp = 100;
				return;
			}
			
			if(player.Level == 50) {
				GiveExp = exp;
				timerGiveExp = 100;
				return;
			}
			
			player.Exp = player.Exp + exp;
			GiveExp = exp;
			timerGiveExp = 100;
			
			
			//Sewers   
			if(player.Level == 1 && player.Exp >= 100) {  player.Level = 2; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true; }
			if(player.Level == 2 && player.Exp >= 150) {  player.Level = 3; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 3 && player.Exp >= 250) {  player.Level = 4; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 4 && player.Exp >= 360) {  player.Level = 5; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 5 && player.Exp >= 430) {  player.Level = 6; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 6 && player.Exp >= 500) {  player.Level = 7; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 7 && player.Exp >= 730) {  player.Level = 8; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 8 && player.Exp >= 1000) {  player.Level = 9; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 9 && player.Exp >= 1450) {  player.Level = 10; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			//Watercave
			if(player.Level == 10 && player.Exp >= 1840) {  player.Level = 11; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 11 && player.Exp >= 3330) {  player.Level = 12; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 12 && player.Exp >= 5500) {  player.Level = 13; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 13 && player.Exp >= 7600) {  player.Level = 14; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 14 && player.Exp >= 9929) {  player.Level = 15; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 15 && player.Exp >= 12820) {  player.Level = 16; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 16 && player.Exp >= 15293) {  player.Level = 17; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 17 && player.Exp >= 17300) {  player.Level = 18; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 18 && player.Exp >= 22402) {  player.Level = 19; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 19 && player.Exp >= 26902) {  player.Level = 20; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			//Mines
			if(player.Level == 20 && player.Exp >= 34592) {  player.Level = 21; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 21 && player.Exp >= 46923) {  player.Level = 22; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 22 && player.Exp >= 75829) {  player.Level = 23; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 23 && player.Exp >= 90234) {  player.Level = 24; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 24 && player.Exp >= 153042) {  player.Level = 25; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 25 && player.Exp >= 179232) {  player.Level = 26; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 26 && player.Exp >= 221011) {  player.Level = 27; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 27 && player.Exp >= 259323) {  player.Level = 28; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 28 && player.Exp >= 279293) {  player.Level = 29; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 29 && player.Exp >= 383421) {  player.Level = 30; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			//Snowpalace
			if(player.Level == 30 && player.Exp >= 593421)  {  player.Level = 31; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 31 && player.Exp >= 814402)  {  player.Level = 32; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 32 && player.Exp >= 1534611) {  player.Level = 33; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 33 && player.Exp >= 1839770) {  player.Level = 34; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 34 && player.Exp >= 2433026) {  player.Level = 35; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 35 && player.Exp >= 2792074) {  player.Level = 36; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 36 && player.Exp >= 2931441) {  player.Level = 37; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 37 && player.Exp >= 3304900) {  player.Level = 38; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 38 && player.Exp >= 3588905) {  player.Level = 39; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 39 && player.Exp >= 4987320) {  player.Level = 40; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			//Tower			
												   
			if(player.Level == 40 && player.Exp >= 159432300) {  player.Level = 41; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 41 && player.Exp >= 318864600) {  player.Level = 42; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 42 && player.Exp >= 418864600) {  player.Level = 43; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 43 && player.Exp >= 518864600) {  player.Level = 44; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 44 && player.Exp >= 618864600) {  player.Level = 45; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 45 && player.Exp >= 718864600) {  player.Level = 46; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 46 && player.Exp >= 818864600) {  player.Level = 47; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 47 && player.Exp >= 918864600) {  player.Level = 48; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 48 && player.Exp >= 958864600) {  player.Level = 49; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			if(player.Level == 49 && player.Exp >= 999999999) {  player.Level = 50; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
			
			if(levelup) {
				if(player.Job.equals("Espadachim")) { player.HpMax = player.HpMax + 20; player.Atk = player.Atk + 5;}
				
				if(player.Job.equals("Feiticeiro")) { player.MpMax = player.MpMax + 10; player.Atk = player.Atk + 3;}
				
				if(player.Job.equals("Batedor")) { player.HpMax = player.HpMax + 12; player.Atk = player.Atk + 8;}
				
				if(player.Job.equals("Medico")) { player.MpMax = player.MpMax + 10; player.Atk = player.Atk + 3;}
				
				if(player.Job.equals("Pistoleiro")) { player.HpMax = player.HpMax + 10; player.Atk = player.Atk + 5; player.AtkTimerMax = player.AtkTimerMax -2;}
				
				if(player.Job.equals("Ladrao")) { player.HpMax = player.HpMax + 10; player.Atk = player.Atk + 5; player.AtkTimerMax = player.AtkTimerMax -5;}		
			}	
			
			levelup = false;
		}
		
		public int CheckLevelExpPercent() {
			//Sewers
			if(player.Level == 1) {  return 100;  }
			if(player.Level == 2) {  return 150;  }
			if(player.Level == 3) {  return 250; }
			if(player.Level == 4) {  return 360; }
			if(player.Level == 5) {  return 430;  }
			if(player.Level == 6) {  return 500;  }
			if(player.Level == 7) {  return 730; }
			if(player.Level == 8) {  return 1000;  }
			if(player.Level == 9) {  return 1450; }
			//Watercave
			if(player.Level == 10) {  return 1840;}
			if(player.Level == 11) {  return 3330;}
			if(player.Level == 12) {  return 5500;}
			if(player.Level == 13) {  return 7600;}
			if(player.Level == 14) {  return 9929;}
			if(player.Level == 15) {  return 12820;}
			if(player.Level == 16) {  return 15293;}
			if(player.Level == 17) {  return 17300;}
			if(player.Level == 18) {  return 22402;}
			if(player.Level == 19) {  return 26902;}
			//Mines
			if(player.Level == 20) {  return 34592; }
			if(player.Level == 21) {  return 46923;}
			if(player.Level == 22) {  return 75829;}
			if(player.Level == 23) {  return 90234;}
			if(player.Level == 24) {  return 153042;}
			if(player.Level == 25) {  return 179232;}
			if(player.Level == 26) {  return 221011;}
			if(player.Level == 27) {  return 259323;}
			if(player.Level == 28) {  return 279293;}
			if(player.Level == 29) {  return 383421;}
			//Snowpalace
			if(player.Level == 30)  {  return 593421;}
			if(player.Level == 31)  {  return 814402;}
			if(player.Level == 32) {  return 1534611;}
			if(player.Level == 33) {  return 1839770;}
			if(player.Level == 34) {  return 2433026;}
			if(player.Level == 35) {  return 2792074;}
			if(player.Level == 36) {  return 2931441;}
			if(player.Level == 37) {  return 3304900;}
			if(player.Level == 38) {  return 3588905;}
			if(player.Level == 39) {  return 4987320;}
			//Tower															   
			if(player.Level == 40) {  return 159432300;}
			if(player.Level == 41) {  return 318864600;}
			if(player.Level == 42) {  return 418864600;}
			if(player.Level == 43) {  return 518864600;}
			if(player.Level == 44) {  return 618864600;}
			if(player.Level == 45) {  return 718864600;}
			if(player.Level == 46) {  return 818864600;}
			if(player.Level == 47) {  return 918864600;}
			if(player.Level == 48) {  return 958864600;}
			if(player.Level == 49) {  return 999999999;}
			if(player.Level == 50) {  return 999999999;}
			
			return 1000;
		}
		
		//Colision
		public void CheckColision() {
			//Screen Limits
			if(player.PosX >= 57) {
				breakwalk = "right";
				return;
			}
			if(player.PosX <= -73.5f) {
				breakwalk = "left";
				return;
			}
			if(player.PosY >= 45f) {
				breakwalk = "back";
				return;
			}
			if(player.PosY <= -66.5f) {
				breakwalk = "front";
				return;
			}
			
			if(player.Map.equals("StreetsA")) {
				
				//Change StreetsB
				//if(plPosX >= -70 && plPosX <= -60 && plPosY > -15 && plPosY < 25) {
				//	player.PosX = 43.5f;
				//	player.PosY = 24.5f;
				//	player.Map = "StreetsB";
				//	SaveData();
				//	this.screen.screenSwitch("LoadingScreen");
				//	return;
				//}
				//Change StreetsC
				//if(plPosX >= 49.5f && plPosX <= 59.5f && plPosY > -15 && plPosY < 25) {
				//	player.PosX = 54;
				//	player.PosY = 27;
				//	player.Map = "StreetsC";
				//	SaveData();
				//	this.screen.screenSwitch("LoadingScreen");
				//	return;
				//}
			}
			if(player.Map.equals("StreetsB")) {
				//Change StreetsA
				if(player.PosX >= 55f && player.PosX <= 63f && player.PosY > -30.5f && player.PosY < -0.5f) {
					player.PosX = -57;
					player.PosY = 0;
					player.Map = "StreetsA";
					SaveData();
					this.screen.screenSwitch("LoadingScreen",keepnetwork);
					return;
				}
				//Change Watercave
				if(player.PosX >= -74.5f && player.PosX <= -54f && player.PosY > -4f && player.PosY < 8) {
					player.PosX = -57;
					player.PosY = 0;
					player.Map = "Watercave";
					SaveData();
					this.screen.screenSwitch("LoadingScreen",keepnetwork);
					return;
				}
			}
			if(player.Map.equals("StreetsC")) {
				//Change StreetsA
				if(player.PosX >= -74 && player.PosX <= -67 && player.PosY > 3 && player.PosY < 23) {
					player.PosX = -57;
					player.PosY = 0;
					player.Map = "StreetsA";
					SaveData();
					this.screen.screenSwitch("LoadingScreen",keepnetwork);
					return;
				}	
				//Change Mines
				if(player.PosX >= 46.5f && player.PosX <= 57.5f && player.PosY > 25.5f && player.PosY < 39.0f) {  
					player.PosX = -57;
					player.PosY = 0;
					player.Map = "Mines";
					SaveData();
					this.screen.screenSwitch("LoadingScreen",keepnetwork);
					return;
				}
			}
			
			if(player.Map.equals("Watercave")) {		
				//Change StreetsB
				if(player.PosX >= -15.5f && player.PosX <= 4f && player.PosY > 23.5f && player.PosY < 40) {
					player.Map = "StreetsB";
					player.PosX = -65.5f;
					player.PosY = 11;
					SaveData();
					this.screen.screenSwitch("LoadingScreen",keepnetwork);
					return;
				}
			}
			
			if(player.Map.equals("Mines")) {		
				//Change StreetsB
				if(player.PosX >= 48 && player.PosX <= 58 && player.PosY > 19.5f && player.PosY < 45) {
					player.Map = "StreetsC";
					player.PosX = 0;
					player.PosY = 0;
					SaveData();
					this.screen.screenSwitch("LoadingScreen",keepnetwork);
					return;
				}
			}
			
			if(player.Map.equals("Volleyball")) { 
				//Change streetsA
				if(player.PosX >= -13.5f && player.PosX <= -3 && player.PosY > -66.5f && player.PosY < -53f) {
					player.PosX = -31;
					player.PosY = 11.5f;
					player.Map = "StreetsA";
					SaveData();
					this.screen.screenSwitch("LoadingScreen",keepnetwork);
					return;
				}
				//Change StreetsB
				//if(plPosX >= -70 && plPosX <= -60 && plPosY > -15 && plPosY < 25) {
				//	player.PosX = 43.5f;
				//	player.PosY = 24.5f;
				//	player.Map = "StreetsB";
				//	SaveData();
				//	this.screen.screenSwitch("LoadingScreen");
				//	return;
				//}
				//Change StreetsC
				//if(plPosX >= 49.5f && plPosX <= 59.5f && plPosY > -15 && plPosY < 25) {
				//	player.PosX = 54;
				//	player.PosY = 27;
				//	player.Map = "StreetsC";
				//	SaveData();
				//	this.screen.screenSwitch("LoadingScreen");
				//	return;
				//}
			}
		}
		//Action
		public void CheckAction() {
			
			if(player.Map.equals("StreetsA")) {
				if(player.PosX > 20 && player.PosX < 37 && player.PosY > 13 && player.PosY < 23) {
					state = "shop";
					shop = "shopStreetsA1";
				}
				
				if(player.PosX > -24 && player.PosX < -11.5f && player.PosY > 11.5f && player.PosY < 23.5f) {
					state = "shop";
					shop = "shopStreetsA2";
				}
				
				if(player.PosX > -43.5f && player.PosX < -30.5f && player.PosY > -43 && player.PosY < -24) {
					//state = "shop";
					//shop = "shopJob";
				}
				
				if(player.PosX > -24 && player.PosX < -12 && player.PosY > -43 && player.PosY < -24) {
					//state = "shop";
					//shop = "shopCrytal";
				}
				
				if(player.PosX > -35.5f && player.PosX < -26.5f && player.PosY > 11.5 && player.PosY < 19) {
					player.Map = "Volleyball";
					player.PosX = 0;
					player.PosY = 0;
					SaveData();
					this.screen.screenSwitch("LoadingScreen",network);
				}
				//Change Sewers
				if(player.PosX >= 41f && player.PosX <= 54f && player.PosY > -37f && player.PosY < -25) {
					player.PosX = 32;
					player.PosY = 40;
					player.Map = "Sewers";
					threahCountSyncPlayer = 0;
					threahCountSyncChat = 0;
					threahCountSyncMob = 0;
					SaveData();
					this.screen.screenSwitch("LoadingScreen",keepnetwork);
					return;
				}
			}
			
			if(player.Map.equals("StreetsB")) {
				if(player.PosX > 23.5f && player.PosX < 48 && player.PosY > -9 && player.PosY < 4.5f) {
					state = "shop";
					shop = "shopStreetsB3";
				}
				if(player.PosX > -10.5f && player.PosX < 7.5 && player.PosY > -8 && player.PosY < 4) {
					state = "shop";
					shop = "shopStreetsB4";
				}
			}
			
			if(player.Map.equals("Sewers")) {
				if(autoattack) { autoattack = false; player.Target = "none"; } else { autoattack = true;}
				
				//Change StreetsA
				if(player.PosX >= 52f && player.PosX <= 65f && player.PosY > 35f && player.PosY < 60) {
					player.Map = "StreetsA";
					player.PosX = 31.5f;
					player.PosY = -40;
					threahCountSyncPlayer = 0;
					threahCountSyncChat = 0;
					threahCountSyncMob = 0;
					SaveData();
					this.screen.screenSwitch("LoadingScreen",keepnetwork);
					return;
				}
			}
 		}
		
		public int CheckDamageDifer(int mobHPMax, int moblevel) {
			
			if(moblevel > 0 && moblevel < 10 && player.Level > 10)  { return mobHPMax / 3; }
			if(moblevel > 10 && moblevel < 20 && player.Level > 20) { return mobHPMax / 5; }
			if(moblevel > 20 && moblevel < 30 && player.Level > 30) { return mobHPMax / 7; }
			if(moblevel > 30 && moblevel < 40 && player.Level > 40) { return mobHPMax / 10; }
			
			return 0;
		}
		
		public void CheckAutoAttack() {
			if(player.Map.equals("Sewers") && autoattack) {
				for(int i = 0; i < lstMobs.size(); i++) {
					
					if(player.Target.equals(lstMobs.get(i).MobID)) {
						 
						if((lstMobs.get(i).MobPosX + 5) > (player.PosX - 5) && (lstMobs.get(i).MobPosX + 5) < (player.PosX + 15)
						   && (lstMobs.get(i).MobPosY + 7) > (player.PosY - 7) && (lstMobs.get(i).MobPosY + 5) < (player.PosY + 18)) {
							player.playerInBattle = "yes";
							player.AtkTimer--;
							
							if(player.AtkTimer < (player.AtkTimerMax - 10) && player.playerInAttack.equals("yes")) {
								player.playerInAttack = "no";
							}
							
							if(player.AtkTimer <= 0) { 	
								int atkweapon = CheckWeapon();
								int mobhp = lstMobs.get(i).MobHp; //CheckDamageDifer(lstMobs.get(i).MobHpMax, 1);
								int damagehit = player.Atk + atkweapon + CheckCritical() + player.Str;
								
								if(CheckMobEvade()) { 
									GameObject damage = new GameObject();
									damage.DamagePosX = lstMobs.get(i).MobPosX;
									damage.DamagePosY = lstMobs.get(i).MobPosY;
									damage.DamageTime = 100;
									damage.DamageType = "mob";
									damage.DamageValue = 0;
									lstDamage.add(damage);
									return; 
								}
								
								if(keepnetwork) {
									int mobHpGet = lstMobs.get(i).MobHp;
									int st = player.Stamina;
									if(st > 0) { mobHpGet =  mobHpGet - damagehit;  } else {  mobHpGet =  mobHpGet - 5; }								
									OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHpGet));
									if(mobHpGet < 0) { mobHpGet = 0; }
									if(mobHpGet <= 0) { 					
										player.Target = "none";
										player.AtkTimer = player.AtkTimerMax;
										player.playerInBattle = "no";
									    player.playerInAttack = "no";
									    player.playerInCast = "no";	
									    autoattack = false;
									    
									    ItemDrop(lstMobs.get(i).MobName);
									    player.Money = player.Money + 2;
									    GiveExp(lstMobs.get(i).MobExp);
									    return;
									}									
									GameObject damage = new GameObject();
									damage.DamagePosX = lstMobs.get(i).MobPosX;
									damage.DamagePosY = lstMobs.get(i).MobPosY;
									damage.DamageTime = 100;
									damage.DamageType = "mob";
									damage.DamageValue = damagehit;
									lstDamage.add(damage);
									
									player.AtkTimer = player.AtkTimerMax;
									player.playerInAttack = "yes";
									lstMobs.get(i).MobTarget = player.Name;	
								}
								else {
									int st = player.Stamina;
									if(st > 0) { mobhp = mobhp - damagehit;  } else {  mobhp = mobhp - 5; }								
									if(mobhp < 0) { mobhp = 0; }
									lstMobs.get(i).MobHp = mobhp;
									
									if(lstMobs.get(i).MobHp <= 0) { 
										
										player.Target = "none";
										player.AtkTimer = player.AtkTimerMax;
										player.playerInBattle = "no";
									    player.playerInAttack = "no";
									    player.playerInCast = "no";	
									    autoattack = false;
									    
									    ItemDrop(lstMobs.get(i).MobName);
									    player.Money = player.Money + 2;
									    GiveExp(lstMobs.get(i).MobExp);
									    return;
									}
									
									GameObject damage = new GameObject();
									damage.DamagePosX = lstMobs.get(i).MobPosX;
									damage.DamagePosY = lstMobs.get(i).MobPosY;
									damage.DamageTime = 100;
									damage.DamageType = "mob";
									damage.DamageValue = damagehit;
									lstDamage.add(damage);
									
									player.AtkTimer = player.AtkTimerMax;
									player.playerInAttack = "yes";
									lstMobs.get(i).MobTarget = player.Name;	
								}
									
								
								
							}					
						}
						else {
							player.playerInBattle = "no";
						}
					}
				}
			}
		}
		
		public void CheckMobAutoAttack() {
			if(player.Map.equals("Sewers")) {
				for(int i = 0; i < lstMobs.size(); i++) {						
					if(lstMobs.get(i).MobTarget.equals(player.Name)) {
						if(player.PosX > (lstMobs.get(i).MobPosX - 5) && player.PosX < (lstMobs.get(i).MobPosX + 15)
							   && player.PosY > (lstMobs.get(i).MobPosY - 7) && player.PosY < (lstMobs.get(i).MobPosY + 18)) {
								
								lstMobs.get(i).MobAtkTimer--;
								if(lstMobs.get(i).MobAtkTimer <= 0) {
									 int mobluck = randnumber.nextInt(100);
									 if(mobluck > 5 && mobluck < 20) {
										 player.Hp = player.Hp - ((lstMobs.get(i).MobAtk * 2) - player.Def);
									 }
									 if(mobluck >= 0 && mobluck < 5) {
										 player.Hp = player.Hp - ((lstMobs.get(i).MobAtk * 3) - player.Def);
									 }
									 if(mobluck > 10) {
									 {
										 player.Hp = player.Hp - (lstMobs.get(i).MobAtk - player.Def);
									 }								 
									 lstMobs.get(i).MobAtkTimer = lstMobs.get(i).MobAtkTimerMax;
									 GameObject damage = new GameObject();
									 damage.DamagePosX = lstMobs.get(i).MobPosX;
									 damage.DamagePosY = lstMobs.get(i).MobPosY;
									 damage.DamageTime = 100;
									 damage.DamageType = "player";
									 damage.DamageValue = lstMobs.get(i).MobAtk;
									 lstDamage.add(damage);
								}	
								if(player.Hp <= 0) {
									playerDead = true;
								}
						}
					}				
				}
			}
		}
		
		public boolean CheckMobEvade() {
			int nextint = randnumber.nextInt(100);
			
			if(nextint < 10) {
				return true;
			}
			else {
				return false;
			}		
		}
		
		public void CheckMobDeadRespawn() {
			if(!keepnetwork) {
				if(player.Map.equals("Sewers")) {
					for(int num = 0; num < lstMobs.size(); num++) {
						
						if(lstMobs.get(num).MobHp <= 0) {
							lstMobs.get(num).MobHp = 0; 
							lstMobs.get(num).MobDead = "yes";   
						}
						
						if(lstMobs.get(num).MobDead.equals("yes")) {
							lstMobs.get(num).MobPosX = 200;
							lstMobs.get(num).MobPosY = 200;
							lstMobs.get(num).MobTimeDead--;
							
							if(lstMobs.get(num).MobTimeDead <= 0) {
								lstMobs.get(num).MobTimeDead = 250;
								lstMobs.get(num).MobDead = "no";
								lstMobs.get(num).MobHp = lstMobs.get(num).MobHpMax;
								lstMobs.get(num).MobMp = lstMobs.get(num).MobMpMax;
								lstMobs.get(num).MobTarget = "none";
								lstMobs.get(num).MobPosX = 0;
								lstMobs.get(num).MobPosY = 0;
							}
						}
					}
			    }
			}
			
			if(keepnetwork) {
				if(player.Map.equals("Sewers")) {
					for(int num = 0; num < lstMobs.size(); num++) {
						
						if(lstMobs.get(num).MobDead.equals("yes")) { 
							if(lstMobs.get(num).MobHp <= 0) {
								lstMobs.get(num).MobHp = 0; 
								lstMobs.get(num).MobPosX = 200;
								lstMobs.get(num).MobPosY = 200;
							}
						}
					}
			    }
			}
		}
		
		public int CheckWeapon() {  //here
		
			if(player.Weapon.equals("basicknife")) { return 0;}			
			if(player.Weapon.equals("doubleedgeknife")) { return 3; }
			
			if(player.Weapon.equals("woodsword")) { return 10;}			
						
			if(player.Weapon.equals("basicpistol")) { return 8;}	
			
			if(player.Weapon.equals("basicdagger")) { return 7;}
			
			if(player.Weapon.equals("stickrod")) { return 6;}
			
			if(player.Weapon.equals("basicaxe")) { return 12;}
			
			
			return 0;
		}
		
		public int CheckCritical() {
			int chance = randnumber.nextInt(100);
			if(player.Luk > 90) {
				if(chance <= (player.Luk - 5)) {
					return 30;
				}
			}
			else {
				if(chance <= player.Luk) {
					return 30;
				}
			}		
			return 0;
		}
		
		
		public void ChangeTarget() {
			
			if(player.Map.equals("Sewers")) {
			
				String playerTarget = player.Target;
				for(int i = 0; i < lstMobs.size(); i++) {
					
					if(countSwitchTarget == 0) {
						countSwitchTarget = i;
					}
					
					if(countSwitchTarget >= 0) {
						if(countSwitchTarget <= lstMobs.size()) {
							countSwitchTarget++;
							if(countSwitchTarget >= lstMobs.size()) { countSwitchTarget = 0; }
							if(!playerTarget.equals(lstMobs.get(countSwitchTarget).MobID)) {
								player.Target = lstMobs.get(countSwitchTarget).MobID;						
								return;		
							}
						}
						else {
							countSwitchTarget = 0;
						}
					}	
				}
			}
		}
		
		
		//[BAG]
		public void UseItem(int numItem) {
			String[] lstItem = player.Itens.split("-");
			String[] itemSplit;
			String item = "";
			String itemName = "";
			String lstitensFinal;
			int qtd;
			boolean equipable = false;  
			String crystalUse = "no";
			
			item = lstItem[numItem];
			if(!item.equals("[NONE]")) {
				itemSplit = item.split("#");
				itemName = itemSplit[0].replace("[", "");
				qtd = Integer.parseInt(itemSplit[1].replace("]", ""));
				
				if(itemName.equals("batfang_loot")) { return; }
				if(itemName.equals("bearfoot_loot")) { return; }
				if(itemName.equals("bigmoney_loot")) { return; }
				if(itemName.equals("blop_loot")) { return; }
				if(itemName.equals("crown_loot")) { return; }
				if(itemName.equals("fang_loot")) { return; }
				if(itemName.equals("freezertail_loot")) { return; }
				if(itemName.equals("galhos_loot")) { return; }
				if(itemName.equals("goblinhat_loot")) { return; }
				if(itemName.equals("lowmoney_loot")) { return; }
				if(itemName.equals("mushroom_loot")) { return; }
				if(itemName.equals("poisonleaf_loot")) { return; }
				if(itemName.equals("snowballs_loot")) { return; }
				
				//Consumable
				if(itemName.equals("hpcan")) { player.Hp = player.Hp + 10; if(player.Hp > player.HpMax) { player.Hp = player.HpMax; } equipable = false;}	
				if(itemName.equals("garrafadrink")) { player.Hp = player.Hp + 100; if(player.Hp > player.HpMax) { player.Hp = player.HpMax; }equipable = false;}			
				if(itemName.equals("mpcan")) { player.Mp = player.Mp + 25; if(player.Mp > player.MpMax) { player.Mp = player.MpMax; } equipable = false;}	
				if(itemName.equals("garrafamagica")) { player.Mp = player.Mp + 50; if(player.Mp > player.MpMax) { player.Mp = player.MpMax; } equipable = false;}
				if(itemName.equals("stcan")) { player.Stamina = player.Stamina + 5; if(player.Stamina > player.StaminaMax) { player.Stamina = player.StaminaMax; } equipable = false;}	
				if(itemName.equals("garrafasuco")) { player.Stamina = player.Stamina + 30; if(player.Stamina > player.StaminaMax) { player.Stamina = player.StaminaMax; } equipable = false;}
				if(itemName.equals("fries")) { player.Stamina = player.Stamina + 15; if(player.Stamina > player.StaminaMax) { player.Stamina = player.StaminaMax; } equipable = false;}	
				if(itemName.equals("pizza")) { player.Stamina = player.Stamina + 5; if(player.Stamina > player.StaminaMax) { player.Stamina = player.StaminaMax; } } player.Hp = player.Hp + 30; if(player.Hp > player.HpMax) { player.Hp = player.HpMax; equipable = false;}	
				
				if(player.Sex.equals("M")) {
					if(itemName.equals("basicset_m")) {  if(player.Set.equals("basicset_m")){ return; } else { AddItemBag(player.Set); player.Set = "basicset_m"; lstItem = player.Itens.split("-"); }}
					if(itemName.equals("blackset_m")) {  if(player.Set.equals("blackset_m")){ return; } else { AddItemBag(player.Set); player.Set = "blackset_m"; lstItem = player.Itens.split("-");  }}
					if(itemName.equals("catset_m")) {  if(player.Set.equals("catset_m")){ return; } else { AddItemBag(player.Set); player.Set = "catset_m"; lstItem = player.Itens.split("-");  }}
					if(itemName.equals("flamingoset_m")) {  if(player.Set.equals("flamingoset_m")){ return; } else { AddItemBag(player.Set); player.Set = "flamingoset_m"; lstItem = player.Itens.split("-");  }}
					if(itemName.equals("rougeset_m")) {  if(player.Set.equals("rougeset_m")){ return; } else { AddItemBag(player.Set); player.Set = "rougeset_m"; lstItem = player.Itens.split("-");  }}
					if(itemName.equals("schoolset_m")) {  if(player.Set.equals("schoolset_m")){ return; } else { AddItemBag(player.Set); player.Set = "schoolset_m"; lstItem = player.Itens.split("-");  }}
					if(itemName.equals("sportset_m")) {  if(player.Set.equals("sportset_m")){ return; } else { AddItemBag(player.Set); player.Set = "sportset_m"; lstItem = player.Itens.split("-");  }}
				}
				if(player.Sex.equals("F")) {
					if(itemName.equals("basicset_f")) {  if(player.Set.equals("basicset_f")){ return; } else { AddItemBag(player.Set); player.Set = "basicset_f"; lstItem = player.Itens.split("-");  }}
					if(itemName.equals("blackset_f")) {  if(player.Set.equals("blackset_f")){ return; } else { AddItemBag(player.Set); player.Set = "blackset_f"; lstItem = player.Itens.split("-");  }}
					if(itemName.equals("catset_f")) {  if(player.Set.equals("catset_f")){ return; } else { AddItemBag(player.Set); player.Set = "catset_f"; lstItem = player.Itens.split("-");  }}
					if(itemName.equals("flamingoset_f")) {  if(player.Set.equals("flamingoset_f")){ return; } else { AddItemBag(player.Set); player.Set = "flamingoset_f"; lstItem = player.Itens.split("-");  }}
					if(itemName.equals("rougeset_f")) {  if(player.Set.equals("rougeset_f")){ return; } else { AddItemBag(player.Set); player.Set = "rougeset_f"; lstItem = player.Itens.split("-");  }}
					if(itemName.equals("schoolset_f")) {  if(player.Set.equals("schoolset_f")){ return; } else { AddItemBag(player.Set); player.Set = "schoolset_f"; lstItem = player.Itens.split("-");  }}
				}
				
				//aprendiz
				if(itemName.equals("basicknife")) {  
					if(player.Weapon.equals("basicknife")){ return; } 
					if(!player.Weapon.equals("basicknife")) { AddItemBag(player.Weapon); player.Weapon = "basicknife"; lstItem = player.Itens.split("-"); }
				}
				if(itemName.equals("doubleedgeknife")) {  
					if(player.Weapon.equals("doubleedgeknife")){ return; } 
					if(!player.Weapon.equals("doubleedgeknife")) { AddItemBag(player.Weapon); player.Weapon = "doubleedgeknife"; lstItem = player.Itens.split("-"); }
				}
				//espadachim
				if(itemName.equals("woodsword")) {  
					if(!player.Job.equals("Espadachim")){ return; } 
					if(player.Weapon.equals("woodsword")){ return; } 
					if(!player.Weapon.equals("woodsword")) { AddItemBag(player.Weapon); player.Weapon = "woodsword"; lstItem = player.Itens.split("-"); }
				}
				//Pistoleiro
				if(itemName.equals("basicpistol")) { 
					if(!player.Job.equals("Pistoleiro")){ return; } 
					if(player.Weapon.equals("basicpistol")){ return; } 
					if(!player.Weapon.equals("basicpistol")) {  AddItemBag(player.Weapon); player.Weapon = "basicpistol"; lstItem = player.Itens.split("-"); }
				}
				//Ladrao
				if(itemName.equals("basicdagger")) { 
					if(!player.Job.equals("Ladrao")){ return; } 
					if(player.Weapon.equals("basicdagger")){ return; } 
					if(!player.Weapon.equals("basicdagger")) { AddItemBag(player.Weapon); player.Weapon = "basicdagger"; lstItem = player.Itens.split("-"); }
				}
				//Feiticeiro
				if(itemName.equals("stickrod")) { 
					if(!player.Job.equals("Feiticeiro") || !player.Job.equals("Medico")){ return; } 
					if(player.Weapon.equals("stickrod")){ return; } 
					if(!player.Weapon.equals("stickrod")) { AddItemBag(player.Weapon); player.Weapon = "stickrod"; lstItem = player.Itens.split("-"); }
				}
				//Batedor
				if(itemName.equals("basicaxe")) { 
					if(!player.Job.equals("Batedor")){ return; } 
					if(player.Weapon.equals("basicaxe")){ return; } 
					if(!player.Weapon.equals("basicaxe")) { AddItemBag(player.Weapon); player.Weapon = "basicaxe"; lstItem = player.Itens.split("-"); }
				}
							
				//Hats
				if(itemName.equals("pirate_hat")) {  
					if(player.Hat.equals("pirate_hat")){ return; } 
					if(!player.Hat.equals("none")) { AddItemBag(player.Hat); player.Hat = "pirate_hat"; lstItem = player.Itens.split("-");  }
					if(player.Hat.equals("none")) { player.Hat = "pirate_hat";}
				}
						
				if(itemName.equals("magician_hat")) {  
					if(player.Hat.equals("magician_hat")){ return; } 
					if(!player.Hat.equals("none")) { AddItemBag(player.Hat); player.Hat = "magician_hat";  lstItem = player.Itens.split("-"); }
					if(player.Hat.equals("none")) { player.Hat = "magician_hat";}
				}
				
				if(itemName.equals("bunny_hat")) {  
					if(player.Hat.equals("bunny_hat")){ return; } 
					if(!player.Hat.equals("none")) { AddItemBag(player.Hat); player.Hat = "bunny_hat";  lstItem = player.Itens.split("-"); }
					if(player.Hat.equals("none")) { player.Hat = "bunny_hat";}
				}
				
				if(itemName.equals("slime_hat")) {  
					if(player.Hat.equals("slime_hat")){ return; } 
					if(!player.Hat.equals("none")) { AddItemBag(player.Hat); player.Hat = "slime_hat"; lstItem = player.Itens.split("-"); }
					if(player.Hat.equals("none")) { player.Hat = "slime_hat";}
				}
				if(itemName.equals("bear_hat")) {  
					if(player.Hat.equals("bear_hat")){ return; } 
					if(!player.Hat.equals("none")) { AddItemBag(player.Hat); player.Hat = "bear_hat"; lstItem = player.Itens.split("-"); }
					if(player.Hat.equals("none")) { player.Hat = "bear_hat";}
				}
				if(itemName.equals("santa_hat")) {  
					if(player.Hat.equals("santa_hat")){ return; } 
					if(!player.Hat.equals("none")) { AddItemBag(player.Hat); player.Hat = "santa_hat"; lstItem = player.Itens.split("-"); }
					if(player.Hat.equals("none")) { player.Hat = "santa_hat";}
				}
				if(itemName.equals("beachglass_hat")) {  
					if(player.Hat.equals("beachglass_hat")){ return; } 
					if(!player.Hat.equals("none")) { AddItemBag(player.Hat); player.Hat = "beachglass_hat"; lstItem = player.Itens.split("-"); }
					if(player.Hat.equals("none")) { player.Hat = "beachglass_hat";}
				}
				if(itemName.equals("capoult_hat")) {  
					if(player.Hat.equals("capoult_hat")){ return; } 
					if(!player.Hat.equals("none")) { AddItemBag(player.Hat); player.Hat = "capoult_hat"; lstItem = player.Itens.split("-"); }
					if(player.Hat.equals("none")) { player.Hat = "capoult_hat";}
				}
				if(itemName.equals("clock_hat")) {  
					if(player.Hat.equals("clock_hat")){ return; } 
					if(!player.Hat.equals("none")) { AddItemBag(player.Hat); player.Hat = "clock_hat"; lstItem = player.Itens.split("-"); }
					if(player.Hat.equals("none")) { player.Hat = "clock_hat";}
				}
				if(itemName.equals("brazilflag_hat")) {  
					if(player.Hat.equals("brazilflag_hat")){ return; } 
					if(!player.Hat.equals("none")) { AddItemBag(player.Hat); player.Hat = "brazilflag_hat"; lstItem = player.Itens.split("-"); }
					if(player.Hat.equals("none")) { player.Hat = "brazilflag_hat";}
				}
				
				if(itemName.equals("headphone_hat")) {  
					if(player.Hat.equals("headphone_hat")){ return; } 
					if(!player.Hat.equals("none")) { AddItemBag(player.Hat); player.Hat = "headphone_hat"; lstItem = player.Itens.split("-"); }
					if(player.Hat.equals("none")) { player.Hat = "headphone_hat";}
				}
				if(itemName.equals("sunglass_hat")) {  
					if(player.Hat.equals("sunglass_hat")){ return; } 
					if(!player.Hat.equals("none")) { AddItemBag(player.Hat); player.Hat = "sunglass_hat"; lstItem = player.Itens.split("-"); }
					if(player.Hat.equals("none")) { player.Hat = "sunglass_hat";}
				}
				
				//orbs
				if(itemName.equals("blue_orb")) { return; }
				if(itemName.equals("orange_orb")) { return; }
				if(itemName.equals("pink_orb")) { return; }
				if(itemName.equals("purple_orb")) { return; }
				if(itemName.equals("red_orb")) { return; }
				if(itemName.equals("green_orb")) { return; }
				if(itemName.equals("yellow_orb")) { return; }
				if(itemName.equals("gray_orb")) { return; }
				if(itemName.equals("green_orb")) { return; }
				
				//Crystals
				if(itemName.equals("blue_crystal_intextra_1") && !equipable) { UseCrystal(itemName); equipable = true;  }
				if(itemName.equals("blue_crystal_intextra_2") && !equipable) { UseCrystal(itemName); equipable = true; }
				if(itemName.equals("blue_crystal_intextra_3") && !equipable) { UseCrystal(itemName); equipable = true; }
				
				if(itemName.equals("green_crystal_lukextra_1") && !equipable) { UseCrystal(itemName); equipable = true; }
				if(itemName.equals("green_crystal_lukextra_2") && !equipable) { UseCrystal(itemName); equipable = true; }
				if(itemName.equals("green_crystal_lukextra_3") && !equipable) { UseCrystal(itemName); equipable = true; }
							
				if(itemName.equals("purple_crystal_vitextra_1") && !equipable) { UseCrystal(itemName); equipable = true; }
				if(itemName.equals("purple_crystal_vitextra_2") && !equipable) { UseCrystal(itemName); equipable = true; }
				if(itemName.equals("purple_crystal_vitextra_3") && !equipable) { UseCrystal(itemName); equipable = true; }
				
				if(itemName.equals("yellow_crystal_agiextra_1") && !equipable) { UseCrystal(itemName); equipable = true; }
				if(itemName.equals("yellow_crystal_agiextra_2") && !equipable) { UseCrystal(itemName); equipable = true; }
				if(itemName.equals("yellow_crystal_agiextra_3") && !equipable) { UseCrystal(itemName); equipable = true; }
				
				if(itemName.equals("red_crystal_strextra_1") && !equipable) { UseCrystal(itemName); equipable = true; }
				if(itemName.equals("red_crystal_strextra_2") && !equipable) { UseCrystal(itemName); equipable = true; }
				if(itemName.equals("red_crystal_strextra_3") && !equipable) { UseCrystal(itemName); equipable = true; }
				
				if(itemName.equals("grey_crystal_dexextra_1") && !equipable) { UseCrystal(itemName); equipable = true; }
				if(itemName.equals("grey_crystal_dexextra_2") && !equipable) { UseCrystal(itemName); equipable = true; }
				if(itemName.equals("grey_crystal_dexextra_3") && !equipable) { UseCrystal(itemName); equipable = true; }
				
				if(itemName.equals("orange_crystal_resextra_1") && !equipable) { UseCrystal(itemName); equipable = true; }
				if(itemName.equals("orange_crystal_resextra_2") && !equipable) { UseCrystal(itemName); equipable = true; }
				if(itemName.equals("orange_crystal_resextra_3") && !equipable) { UseCrystal(itemName); equipable = true; }
				
				if(itemName.equals("green_orb")) { return; }
				if(itemName.equals("yellow_orb")) { return; }
				if(itemName.equals("gray_orb")) { return; }
				if(itemName.equals("green_orb")) { return; }
				if(itemName.equals("red_orb")) { return; }
				if(itemName.equals("blue_orb")) { return; }
							
				qtd = qtd - 1;
				
				if(qtd == 0) {
					itemName = "[NONE]";
					lstItem[numItem] = itemName;
					lstitensFinal = Arrays.toString(lstItem).replace(", ","-");
					lstitensFinal = lstitensFinal.substring(1, lstitensFinal.length() -1);
					player.Itens = lstitensFinal;
				}
				else {
					itemName = "[" + itemName + "#" + qtd + "]"; 
					lstItem[numItem] = itemName;
					lstitensFinal = Arrays.toString(lstItem).replace(", ","-");
					lstitensFinal = lstitensFinal.substring(1, lstitensFinal.length() -1);
					player.Itens = lstitensFinal;
				}			
			}
		}
		
		public void UnequipHat() {
			String nameHat = player.Hat;
			if(nameHat.equals("none")) { return; }
			player.Hat = "none";
			AddItemBag(nameHat);
		}
		
		
		public void UseCrystal(String item) {
			if(player.Crystal1.equals("none")) { player.Crystal1 = item; ApplyCrystals(item); return; }
			if(player.Crystal2.equals("none")) { player.Crystal2 = item; ApplyCrystals(item); return; }
			if(player.Crystal3.equals("none")) { player.Crystal3 = item; ApplyCrystals(item); return; }
			if(player.Crystal4.equals("none")) { player.Crystal4 = item; ApplyCrystals(item); return; }
		}
		
		public void ApplyCrystals(String item) {
			
			if(item.equals("blue_crystal_intextra_1")) { player.Wis = player.Wis + 2; player.MpMax = player.MpMax + 20; }	
			if(item.equals("blue_crystal_intextra_2")) { player.Wis = player.Wis + 5; player.MpMax = player.MpMax + 50; }
			if(item.equals("blue_crystal_intextra_3")) { player.Wis = player.Wis + 10; player.MpMax = player.MpMax + 100; }
			
			if(item.equals("green_crystal_lukextra_1")) { player.Luk = player.Luk + 2;  }	
			if(item.equals("green_crystal_lukextra_2")) { player.Luk = player.Luk + 5;  }
			if(item.equals("green_crystal_lukextra_3")) { player.Luk = player.Luk + 10; }
			
			if(item.equals("purple_crystal_vitextra_1")) { player.Vit = player.Vit + 2; player.MpMax = player.HpMax + 20; }	
			if(item.equals("purple_crystal_vitextra_2")) { player.Vit = player.Vit + 5; player.MpMax = player.HpMax + 50; }
			if(item.equals("purple_crystal_vitextra_3")) { player.Vit = player.Vit + 10; player.MpMax = player.HpMax + 100; }
			
			if(item.equals("yellow_crystal_agiextra_1")) { player.Agi = player.Agi + 2; player.AtkTimerMax = player.AtkTimerMax - 2; }	
			if(item.equals("yellow_crystal_agiextra_2")) { player.Agi = player.Agi + 5; player.AtkTimerMax = player.AtkTimerMax - 4; }
			if(item.equals("yellow_crystal_agiextra_3")) { player.Agi = player.Agi + 10; player.AtkTimerMax = player.AtkTimerMax - 6; }
			
			if(item.equals("red_crystal_strextra_1")) { player.Str = player.Str + 2; }	
			if(item.equals("red_crystal_strextra_2")) { player.Str = player.Str + 5; }
			if(item.equals("red_crystal_strextra_3")) { player.Str = player.Str + 10; }
			
			if(item.equals("grey_crystal_dexextra_1")) { player.Dex = player.Dex + 2; }	
			if(item.equals("grey_crystal_dexextra_2")) { player.Dex = player.Dex + 5; }
			if(item.equals("grey_crystal_dexextra_3")) { player.Dex = player.Dex + 10; }
			
			if(item.equals("orange_crystal_resextra_1")) { player.Res = player.Res + 2; player.StaminaMax = player.StaminaMax + 20; player.regenTimeMax = player.regenTimeMax - 300; }	
			if(item.equals("orange_crystal_resextra_2")) { player.Res = player.Res + 5; player.StaminaMax = player.StaminaMax + 50; player.regenTimeMax = player.regenTimeMax - 500; }
			if(item.equals("orange_crystal_resextra_3")) { player.Res = player.Res + 10; player.StaminaMax = player.StaminaMax + 100; player.regenTimeMax = player.regenTimeMax - 700; }
			
		}
		
		public void RemoveCrystals(int num) {
			
			if(num == 1 && player.Crystal1.equals("blue_crystal_intextra_1")) { AddItemBag("blue_crystal_intextra_1"); player.Wis = player.Wis - 2; player.MpMax = player.MpMax - 20; player.Crystal1 = "none"; return; }	
			if(num == 2 && player.Crystal2.equals("blue_crystal_intextra_1")) { AddItemBag("blue_crystal_intextra_1"); player.Wis = player.Wis - 2; player.MpMax = player.MpMax - 20; player.Crystal2 = "none";return; }	
			if(num == 3 && player.Crystal3.equals("blue_crystal_intextra_1")) { AddItemBag("blue_crystal_intextra_1"); player.Wis = player.Wis - 2; player.MpMax = player.MpMax - 20; player.Crystal3 = "none"; return; }	
			if(num == 4 && player.Crystal4.equals("blue_crystal_intextra_1")) { AddItemBag("blue_crystal_intextra_1"); player.Wis = player.Wis - 2; player.MpMax = player.MpMax - 20; player.Crystal4 = "none"; return; }	
			
			if(num == 1 && player.Crystal1.equals("blue_crystal_intextra_2")) { AddItemBag("blue_crystal_intextra_2"); player.Wis = player.Wis - 5; player.MpMax = player.MpMax - 50; player.Crystal1 = "none"; return; }	
			if(num == 2 && player.Crystal2.equals("blue_crystal_intextra_2")) { AddItemBag("blue_crystal_intextra_2"); player.Wis = player.Wis - 5; player.MpMax = player.MpMax - 50; player.Crystal2 = "none"; return; }	
			if(num == 3 && player.Crystal3.equals("blue_crystal_intextra_2")) { AddItemBag("blue_crystal_intextra_2"); player.Wis = player.Wis - 5; player.MpMax = player.MpMax - 50; player.Crystal3 = "none"; return; }	
			if(num == 4 && player.Crystal4.equals("blue_crystal_intextra_2")) { AddItemBag("blue_crystal_intextra_2"); player.Wis = player.Wis - 5; player.MpMax = player.MpMax - 50; player.Crystal4 = "none"; return; }
			
			if(num == 1 && player.Crystal1.equals("blue_crystal_intextra_3")) { AddItemBag("blue_crystal_intextra_3"); player.Wis = player.Wis - 10; player.MpMax = player.MpMax - 100; player.Crystal1 = "none"; return; }	
			if(num == 2 && player.Crystal2.equals("blue_crystal_intextra_3")) { AddItemBag("blue_crystal_intextra_3"); player.Wis = player.Wis - 10; player.MpMax = player.MpMax - 100; player.Crystal2 = "none"; return; }	
			if(num == 3 && player.Crystal3.equals("blue_crystal_intextra_3")) { AddItemBag("blue_crystal_intextra_3"); player.Wis = player.Wis - 10; player.MpMax = player.MpMax - 100; player.Crystal3 = "none"; return; }	
			if(num == 4 && player.Crystal4.equals("blue_crystal_intextra_3")) { AddItemBag("blue_crystal_intextra_3"); player.Wis = player.Wis - 10; player.MpMax = player.MpMax - 100; player.Crystal4 = "none"; return; }
			
			if(num == 1 && player.Crystal1.equals("green_crystal_lukextra_1")) { AddItemBag("green_crystal_lukextra_1"); player.Luk = player.Luk - 2; player.Crystal1 = "none"; return; }	
			if(num == 2 && player.Crystal2.equals("green_crystal_lukextra_1")) { AddItemBag("green_crystal_lukextra_1"); player.Luk = player.Luk - 2; player.Crystal2 = "none"; return; }	
			if(num == 3 && player.Crystal3.equals("green_crystal_lukextra_1")) { AddItemBag("green_crystal_lukextra_1"); player.Luk = player.Luk - 2; player.Crystal3 = "none"; return; }	
			if(num == 4 && player.Crystal4.equals("green_crystal_lukextra_1")) { AddItemBag("green_crystal_lukextra_1"); player.Luk = player.Luk - 2; player.Crystal4 = "none"; return; }	
			
			if(num == 1 && player.Crystal1.equals("green_crystal_lukextra_2")) { AddItemBag("green_crystal_lukextra_2"); player.Luk = player.Luk - 5; player.Crystal1 = "none"; return; }	
			if(num == 2 && player.Crystal2.equals("green_crystal_lukextra_2")) { AddItemBag("green_crystal_lukextra_2"); player.Luk = player.Luk - 5; player.Crystal2 = "none"; return; }	
			if(num == 3 && player.Crystal3.equals("green_crystal_lukextra_2")) { AddItemBag("green_crystal_lukextra_2"); player.Luk = player.Luk - 5; player.Crystal3 = "none"; return; }	
			if(num == 4 && player.Crystal4.equals("green_crystal_lukextra_2")) { AddItemBag("green_crystal_lukextra_2"); player.Luk = player.Luk - 5; player.Crystal4 = "none"; return; }
			
			if(num == 1 && player.Crystal1.equals("green_crystal_lukextra_3")) { AddItemBag("green_crystal_lukextra_3"); player.Luk = player.Luk - 10; player.Crystal1 = "none"; return; }	
			if(num == 2 && player.Crystal2.equals("green_crystal_lukextra_3")) { AddItemBag("green_crystal_lukextra_3"); player.Luk = player.Luk - 10; player.Crystal2 = "none"; return; }	
			if(num == 3 && player.Crystal3.equals("green_crystal_lukextra_3")) { AddItemBag("green_crystal_lukextra_3"); player.Luk = player.Luk - 10; player.Crystal3 = "none"; return; }	
			if(num == 4 && player.Crystal4.equals("green_crystal_lukextra_3")) { AddItemBag("green_crystal_lukextra_3"); player.Luk = player.Luk - 10; player.Crystal4 = "none"; return; }
			
			if(num == 1 && player.Crystal1.equals("purple_crystal_vitextra_1")) { AddItemBag("purple_crystal_vitextra_1"); player.Vit = player.Vit - 2; player.Crystal1 = "none"; player.MpMax = player.HpMax - 20; return; }	
			if(num == 2 && player.Crystal2.equals("purple_crystal_vitextra_1")) { AddItemBag("purple_crystal_vitextra_1"); player.Vit = player.Vit - 2; player.Crystal2 = "none";  player.MpMax = player.HpMax - 20; return; }	
			if(num == 3 && player.Crystal3.equals("purple_crystal_vitextra_1")) { AddItemBag("purple_crystal_vitextra_1"); player.Vit = player.Vit - 2; player.Crystal3 = "none";  player.MpMax = player.HpMax - 20; return; }	
			if(num == 4 && player.Crystal4.equals("purple_crystal_vitextra_1")) { AddItemBag("purple_crystal_vitextra_1"); player.Vit = player.Vit - 2; player.Crystal4 = "none";  player.MpMax = player.HpMax - 20; return; }	
			
			if(num == 1 && player.Crystal1.equals("purple_crystal_vitextra_2")) { AddItemBag("purple_crystal_vitextra_2"); player.Vit = player.Vit - 5; player.Crystal1 = "none";  player.MpMax = player.HpMax - 50; return; }	
			if(num == 2 && player.Crystal2.equals("purple_crystal_vitextra_2")) { AddItemBag("purple_crystal_vitextra_2"); player.Vit = player.Vit - 5; player.Crystal2 = "none";  player.MpMax = player.HpMax - 50; return; }	
			if(num == 3 && player.Crystal3.equals("purple_crystal_vitextra_2")) { AddItemBag("purple_crystal_vitextra_2"); player.Vit = player.Vit - 5; player.Crystal3 = "none";  player.MpMax = player.HpMax - 50; return; }	
			if(num == 4 && player.Crystal4.equals("purple_crystal_vitextra_2")) { AddItemBag("purple_crystal_vitextra_2"); player.Vit = player.Vit - 5; player.Crystal4 = "none";  player.MpMax = player.HpMax - 50; return; }
			
			if(num == 1 && player.Crystal1.equals("purple_crystal_vitextra_3")) { AddItemBag("purple_crystal_vitextra_3"); player.Vit = player.Vit - 10; player.Crystal1 = "none";  player.MpMax = player.HpMax - 100; return; }	
			if(num == 2 && player.Crystal2.equals("purple_crystal_vitextra_3")) { AddItemBag("purple_crystal_vitextra_3"); player.Vit = player.Vit - 10; player.Crystal2 = "none";  player.MpMax = player.HpMax - 100; return; }	
			if(num == 3 && player.Crystal3.equals("purple_crystal_vitextra_3")) { AddItemBag("purple_crystal_vitextra_3"); player.Vit = player.Vit - 10; player.Crystal3 = "none";  player.MpMax = player.HpMax - 100; return; }	
			if(num == 4 && player.Crystal4.equals("purple_crystal_vitextra_3")) { AddItemBag("purple_crystal_vitextra_3"); player.Vit = player.Vit - 10; player.Crystal4 = "none";  player.MpMax = player.HpMax - 100; return; }
			
			if(num == 1 && player.Crystal1.equals("yellow_crystal_agiextra_1")) { AddItemBag("yellow_crystal_agiextra_1"); player.Agi = player.Agi - 2; player.Crystal1 = "none";  player.AtkTimerMax = player.AtkTimerMax + 2; return; }	
			if(num == 2 && player.Crystal2.equals("yellow_crystal_agiextra_1")) { AddItemBag("yellow_crystal_agiextra_1"); player.Agi = player.Agi - 2; player.Crystal2 = "none";  player.AtkTimerMax = player.AtkTimerMax + 2; return; }	
			if(num == 3 && player.Crystal3.equals("yellow_crystal_agiextra_1")) { AddItemBag("yellow_crystal_agiextra_1"); player.Agi = player.Agi - 2; player.Crystal3 = "none";  player.AtkTimerMax = player.AtkTimerMax + 2; return; }	
			if(num == 4 && player.Crystal4.equals("yellow_crystal_agiextra_1")) { AddItemBag("yellow_crystal_agiextra_1"); player.Agi = player.Agi - 2; player.Crystal4 = "none";  player.AtkTimerMax = player.AtkTimerMax + 2; return; }	
			
			if(num == 1 && player.Crystal1.equals("yellow_crystal_agiextra_2")) { AddItemBag("yellow_crystal_agiextra_2"); player.Agi = player.Agi - 5; player.AtkTimerMax = player.AtkTimerMax + 4;  player.Crystal1 = "none";  return; }	
			if(num == 2 && player.Crystal2.equals("yellow_crystal_agiextra_2")) { AddItemBag("yellow_crystal_agiextra_2"); player.Agi = player.Agi - 5; player.AtkTimerMax = player.AtkTimerMax + 4;  player.Crystal2 = "none";  return; }	
			if(num == 3 && player.Crystal3.equals("yellow_crystal_agiextra_2")) { AddItemBag("yellow_crystal_agiextra_2"); player.Agi = player.Agi - 5; player.AtkTimerMax = player.AtkTimerMax + 4;  player.Crystal3 = "none";  return; }	
			if(num == 4 && player.Crystal4.equals("yellow_crystal_agiextra_2")) { AddItemBag("yellow_crystal_agiextra_2"); player.Agi = player.Agi - 5; player.AtkTimerMax = player.AtkTimerMax + 4;  player.Crystal4 = "none";  return; }
			
			if(num == 1 && player.Crystal1.equals("yellow_crystal_agiextra_3")) { AddItemBag("yellow_crystal_agiextra_3"); player.Agi = player.Agi - 10; player.AtkTimerMax = player.AtkTimerMax + 6; player.Crystal1 = "none"; return; }	
			if(num == 2 && player.Crystal2.equals("yellow_crystal_agiextra_3")) { AddItemBag("yellow_crystal_agiextra_3"); player.Agi = player.Agi - 10; player.AtkTimerMax = player.AtkTimerMax + 6; player.Crystal2 = "none";  return; }	
			if(num == 3 && player.Crystal3.equals("yellow_crystal_agiextra_3")) { AddItemBag("yellow_crystal_agiextra_3"); player.Agi = player.Agi - 10; player.AtkTimerMax = player.AtkTimerMax + 6; player.Crystal3 = "none";  return; }	
			if(num == 4 && player.Crystal4.equals("yellow_crystal_agiextra_3")) { AddItemBag("yellow_crystal_agiextra_3"); player.Agi = player.Agi - 10; player.AtkTimerMax = player.AtkTimerMax + 6; player.Crystal4 = "none";  return; }
			
			if(num == 1 && player.Crystal1.equals("red_crystal_strextra_1")) { AddItemBag("red_crystal_strextra_1"); player.Str = player.Str - 2; player.Crystal1 = "none";  return; }	
			if(num == 2 && player.Crystal2.equals("red_crystal_strextra_1")) { AddItemBag("red_crystal_strextra_1"); player.Str = player.Str - 2; player.Crystal2 = "none";  return; }	
			if(num == 3 && player.Crystal3.equals("red_crystal_strextra_1")) { AddItemBag("red_crystal_strextra_1"); player.Str = player.Str - 2; player.Crystal3 = "none";  return; }	
			if(num == 4 && player.Crystal4.equals("red_crystal_strextra_1")) { AddItemBag("red_crystal_strextra_1"); player.Str = player.Str - 2; player.Crystal4 = "none";  return; }	
			
			if(num == 1 && player.Crystal1.equals("red_crystal_strextra_2")) { AddItemBag("red_crystal_strextra_2"); player.Str = player.Str - 5; player.Crystal1 = "none";  return; }	
			if(num == 2 && player.Crystal2.equals("red_crystal_strextra_2")) { AddItemBag("red_crystal_strextra_2"); player.Str = player.Str - 5; player.Crystal2 = "none";  return; }	
			if(num == 3 && player.Crystal3.equals("red_crystal_strextra_2")) { AddItemBag("red_crystal_strextra_2"); player.Str = player.Str - 5; player.Crystal3 = "none";  return; }	
			if(num == 4 && player.Crystal4.equals("red_crystal_strextra_2")) { AddItemBag("red_crystal_strextra_2"); player.Str = player.Str - 5; player.Crystal4 = "none";  return; }
			
			if(num == 1 && player.Crystal1.equals("red_crystal_strextra_3")) { AddItemBag("red_crystal_strextra_3"); player.Str = player.Str - 10; player.Crystal1 = "none";  return; }	
			if(num == 2 && player.Crystal2.equals("red_crystal_strextra_3")) { AddItemBag("red_crystal_strextra_3"); player.Str = player.Str - 10; player.Crystal2 = "none";  return; }	
			if(num == 3 && player.Crystal3.equals("red_crystal_strextra_3")) { AddItemBag("red_crystal_strextra_3"); player.Str = player.Str - 10; player.Crystal3 = "none";  return; }	
			if(num == 4 && player.Crystal4.equals("red_crystal_strextra_3")) { AddItemBag("red_crystal_strextra_3"); player.Str = player.Str - 10; player.Crystal4 = "none";  return; }
			
			if(num == 1 && player.Crystal1.equals("grey_crystal_dexextra_1")) { AddItemBag("grey_crystal_dexextra_1"); player.Dex = player.Dex - 2; player.Crystal1 = "none";  return; }	
			if(num == 2 && player.Crystal2.equals("grey_crystal_dexextra_1")) { AddItemBag("grey_crystal_dexextra_1"); player.Dex = player.Dex - 2; player.Crystal2 = "none";  return; }	
			if(num == 3 && player.Crystal3.equals("grey_crystal_dexextra_1")) { AddItemBag("grey_crystal_dexextra_1"); player.Dex = player.Dex - 2; player.Crystal3 = "none";  return; }	
			if(num == 4 && player.Crystal4.equals("grey_crystal_dexextra_1")) { AddItemBag("grey_crystal_dexextra_1"); player.Dex = player.Dex - 2; player.Crystal4 = "none";  return; }	
			
			if(num == 1 && player.Crystal1.equals("grey_crystal_dexextra_2")) { AddItemBag("grey_crystal_dexextra_2"); player.Dex = player.Dex - 5; player.Crystal1 = "none";  return; }	
			if(num == 2 && player.Crystal2.equals("grey_crystal_dexextra_2")) { AddItemBag("grey_crystal_dexextra_2"); player.Dex = player.Dex - 5; player.Crystal2 = "none";  return; }	
			if(num == 3 && player.Crystal3.equals("grey_crystal_dexextra_2")) { AddItemBag("grey_crystal_dexextra_2"); player.Dex = player.Dex - 5; player.Crystal3 = "none";  return; }	
			if(num == 4 && player.Crystal4.equals("grey_crystal_dexextra_2")) { AddItemBag("grey_crystal_dexextra_2"); player.Dex = player.Dex - 5; player.Crystal4 = "none";  return; }
			
			if(num == 1 && player.Crystal1.equals("grey_crystal_dexextra_3")) { AddItemBag("grey_crystal_dexextra_3"); player.Dex = player.Dex - 10; player.Crystal1 = "none";  return; }	
			if(num == 2 && player.Crystal2.equals("grey_crystal_dexextra_3")) { AddItemBag("grey_crystal_dexextra_3"); player.Dex = player.Dex - 10; player.Crystal2 = "none";  return; }	
			if(num == 3 && player.Crystal3.equals("grey_crystal_dexextra_3")) { AddItemBag("grey_crystal_dexextra_3"); player.Dex = player.Dex - 10; player.Crystal3 = "none";  return; }	
			if(num == 4 && player.Crystal4.equals("grey_crystal_dexextra_3")) { AddItemBag("grey_crystal_dexextra_3"); player.Dex = player.Dex - 10; player.Crystal4 = "none";  return; }
			
			if(num == 1 && player.Crystal1.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_1"); player.Res = player.Res - 2; player.StaminaMax = player.StaminaMax - 10; player.regenTimeMax = player.regenTimeMax + 300; player.Crystal1 = "none"; return; }	
			if(num == 2 && player.Crystal2.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_1"); player.Res = player.Res - 2; player.StaminaMax = player.StaminaMax - 10; player.regenTimeMax = player.regenTimeMax + 300; player.Crystal2 = "none";  return; }	
			if(num == 3 && player.Crystal3.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_1"); player.Res = player.Res - 2; player.StaminaMax = player.StaminaMax - 10; player.regenTimeMax = player.regenTimeMax + 300; player.Crystal3 = "none";  return; }	
			if(num == 4 && player.Crystal4.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_1"); player.Res = player.Res - 2; player.StaminaMax = player.StaminaMax - 10; player.regenTimeMax = player.regenTimeMax + 300; player.Crystal4 = "none";  return; }	
			
			if(num == 1 && player.Crystal1.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_2"); player.Res = player.Res - 5; player.StaminaMax = player.StaminaMax - 50; player.regenTimeMax = player.regenTimeMax + 500; player.Crystal1 = "none";  return; }	
			if(num == 2 && player.Crystal2.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_2"); player.Res = player.Res - 5; player.StaminaMax = player.StaminaMax - 50; player.regenTimeMax = player.regenTimeMax + 500; player.Crystal2 = "none";  return; }	
			if(num == 3 && player.Crystal3.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_2"); player.Res = player.Res - 5; player.StaminaMax = player.StaminaMax - 50; player.regenTimeMax = player.regenTimeMax + 500; player.Crystal3 = "none";  return; }	
			if(num == 4 && player.Crystal4.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_2"); player.Res = player.Res - 5; player.StaminaMax = player.StaminaMax - 50; player.regenTimeMax = player.regenTimeMax + 500; player.Crystal4 = "none";  return; }
			
			if(num == 1 && player.Crystal1.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_3"); player.Res = player.Res - 10; player.StaminaMax = player.StaminaMax - 100; player.regenTimeMax = player.regenTimeMax + 700; player.Crystal1 = "none";  return; }	
			if(num == 2 && player.Crystal2.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_3"); player.Res = player.Res - 10; player.StaminaMax = player.StaminaMax - 100; player.regenTimeMax = player.regenTimeMax + 700; player.Crystal2 = "none";  return; }	
			if(num == 3 && player.Crystal3.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_3"); player.Res = player.Res - 10; player.StaminaMax = player.StaminaMax - 100; player.regenTimeMax = player.regenTimeMax + 700; player.Crystal3 = "none";  return; }	
			if(num == 4 && player.Crystal4.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_3"); player.Res = player.Res - 10; player.StaminaMax = player.StaminaMax - 100; player.regenTimeMax = player.regenTimeMax + 700; player.Crystal4 = "none";  return; }
		}
		
		
		public void ShowBag() {
			try {
			//Common Itens
			for(int count = 0; count < 16; count++) {  
				spr_master = ShowItem(count);
				if(spr_master != null) {
					spr_master.draw(game.batch);
					font_master.draw(game.batch, ShowQuantityItem(count), spr_master.getX() + 7,spr_master.getY() + 3);
				}
			}	
					
			//Crystal Itens    
			//slot 1
			if(!player.Crystal1.equals("none")) {
				spr_master = ItemLog(player.Crystal1);
				spr_master.setPosition(1.5f, 25);
				spr_master.setSize(9, 14);
				spr_master.draw(game.batch); 
			}
			
			if(!player.Crystal2.equals("none")) {
				spr_master = ItemLog(player.Crystal2);
				spr_master.setPosition(10.5f, 25);
				spr_master.setSize(9, 14);
				spr_master.draw(game.batch); 
			}
			
			//slot 3
			if(!player.Crystal3.equals("none")) {
				spr_master = ItemLog(player.Crystal3);
				spr_master.setPosition(19.5f, 25);
				spr_master.setSize(9, 14);
				spr_master.draw(game.batch); 
			}
			
			//slot 4
			if(!player.Crystal4.equals("none")) {
				spr_master = ItemLog(player.Crystal4);
				spr_master.setPosition(29f, 25);
				spr_master.setSize(9, 14);
				spr_master.draw(game.batch); 
			}
			
			}
			
			catch(Exception ex) {
				
			}
		}
		
		public Sprite ShowItem(int num) {
			String[] lstItem = player.Itens.split("-");
			String[] itemSplit;
			String item;
			
			item = lstItem[num];
			if(!item.equals("[NONE]")) {
				itemSplit = item.split("#");
				item = itemSplit[0].replace("[", "");
				if(player.Set.equals(item)) { font_master.draw(game.batch, "Em uso", spr_master.getX() + 10,spr_master.getY() + 15); }
				if(player.Weapon.equals(item)) { font_master.draw(game.batch, "Em uso", spr_master.getX() + 10,spr_master.getY() + 15); }
				spr_master = ItemLog(item);
				
				//look
				if(spr_master == null) { 
					spr_master = atlas_itensUtil.createSprite("cake"); 
					font_master.draw(game.batch, String.valueOf(num), 20, 15);
					return spr_master; 
				}
				if(num == 0){ spr_master.setPosition(-35f, 24); spr_master.setSize(9, 14); return spr_master; }
				if(num == 1){ spr_master.setPosition(-26f, 24); spr_master.setSize(9, 14); return spr_master; }
				if(num == 2){ spr_master.setPosition(-17f, 24); spr_master.setSize(9, 14); return spr_master; }
				if(num == 3){ spr_master.setPosition(-7.5f, 24); spr_master.setSize(9, 14); return spr_master; }
				if(num == 4){ spr_master.setPosition(-35f, 11); spr_master.setSize(9, 14); return spr_master; }
				if(num == 5){ spr_master.setPosition(-26f, 11); spr_master.setSize(9, 14); return spr_master; }
				if(num == 6){ spr_master.setPosition(-17f, 11); spr_master.setSize(9, 14); return spr_master; }
				if(num == 7){ spr_master.setPosition(-7.5f, 11); spr_master.setSize(9, 14); return spr_master; }
				if(num == 8){ spr_master.setPosition(-35f, -3); spr_master.setSize(9, 14); return spr_master; }
				if(num == 9){ spr_master.setPosition(-26f, -3); spr_master.setSize(9, 14); return spr_master; }
				if(num == 10){ spr_master.setPosition(-17f, -3); spr_master.setSize(9, 14); return spr_master; }
				if(num == 11){ spr_master.setPosition(-7.5f, -3); spr_master.setSize(9, 14); return spr_master; }
				if(num == 12){ spr_master.setPosition(-35f, -15.5f); spr_master.setSize(9, 14); return spr_master; }
				if(num == 13){ spr_master.setPosition(-26f, -15.5f); spr_master.setSize(9, 14); return spr_master; }
				if(num == 14){ spr_master.setPosition(-17f, -15.5f); spr_master.setSize(9, 14); return spr_master; }
				if(num == 15){ spr_master.setPosition(-7.5f, -15.5f); spr_master.setSize(9, 14); return spr_master; }	
			}
				
			return spr_master;
		}
		
		public Sprite ItemLog(String item) {
			//Util
			if(item.equals("cake")) { spr_master = atlas_itensUtil.createSprite("cake"); return spr_master;  }
			if(item.equals("cheesebread")) { spr_master = atlas_itensUtil.createSprite("cheesebread");  return spr_master;  }
			if(item.equals("chipz")) { spr_master = atlas_itensUtil.createSprite("chipz");  return spr_master;  }
			if(item.equals("egg")) { spr_master = atlas_itensUtil.createSprite("egg");  return spr_master;  }
			if(item.equals("fries")) { spr_master = atlas_itensUtil.createSprite("fries");  return spr_master;  }
			if(item.equals("garrafadrink")) { spr_master = atlas_itensUtil.createSprite("garrafadrink");  return spr_master;  }
			if(item.equals("garrafamagica")) { spr_master = atlas_itensUtil.createSprite("garrafamagica");  return spr_master;  }
			if(item.equals("garrafasuco")) { spr_master = atlas_itensUtil.createSprite("garrafasuco");  return spr_master;  }
			if(item.equals("hamburguer")) { spr_master = atlas_itensUtil.createSprite("hamburguer");  return spr_master;  }
			if(item.equals("hotdog")) { spr_master = atlas_itensUtil.createSprite("hotdog");  return spr_master;  }
			if(item.equals("hpcan")) { spr_master = atlas_itensUtil.createSprite("hpcan");  return spr_master;  }
			if(item.equals("icecreamorange")) { spr_master = atlas_itensUtil.createSprite("icecreamorange");  return spr_master;  }
			if(item.equals("icecreampurple")) { spr_master = atlas_itensUtil.createSprite("icecreampurple");  return spr_master;  }
			if(item.equals("meatball")) { spr_master = atlas_itensUtil.createSprite("meatball");  return spr_master;  }
			if(item.equals("mpcan")) { spr_master = atlas_itensUtil.createSprite("mpcan");  return spr_master;  }
			if(item.equals("pizza")) { spr_master = atlas_itensUtil.createSprite("pizza");  return spr_master;  }
			if(item.equals("spaghetti")) { spr_master = atlas_itensUtil.createSprite("spaghetti");  return spr_master;  }
			if(item.equals("stcan")) { spr_master = atlas_itensUtil.createSprite("stcan");  return spr_master;  }
			if(item.equals("steak")) { spr_master = atlas_itensUtil.createSprite("steak");  return spr_master;  }
			if(item.equals("stjar")) { spr_master = atlas_itensUtil.createSprite("stjar");  return spr_master;  }
			if(item.equals("sushi")) { spr_master = atlas_itensUtil.createSprite("sushi");  return spr_master;  }
			if(item.equals("blue_orb")) { spr_master = atlas_itensUtil.createSprite("blue_orb");  return spr_master;  }
			if(item.equals("gray_orb")) { spr_master = atlas_itensUtil.createSprite("gray_orb");  return spr_master;  }
			if(item.equals("green_orb")) { spr_master = atlas_itensUtil.createSprite("green_orb");  return spr_master;  }
			if(item.equals("orange_orb")) { spr_master = atlas_itensUtil.createSprite("orange_orb");  return spr_master;  }
			if(item.equals("pink_orb")) { spr_master = atlas_itensUtil.createSprite("pink_orb");  return spr_master;  }
			if(item.equals("purple_orb")) { spr_master = atlas_itensUtil.createSprite("purple_orb");  return spr_master;  }
			if(item.equals("red_orb")) { spr_master = atlas_itensUtil.createSprite("red_orb");  return spr_master;  }
			if(item.equals("yellow_orb")) { spr_master = atlas_itensUtil.createSprite("yellow_orb");  return spr_master;  }
			
			//Hats
			if(item.equals("banana_hat")) { spr_master = atlas_itensHat.createSprite("banana_hat");  return spr_master;  }
			if(item.equals("bat_hat")) { spr_master = atlas_itensHat.createSprite("bat_hat");  return spr_master;  }
			if(item.equals("beachglass_hat")) { spr_master = atlas_itensHat.createSprite("beachglass_hat");  return spr_master;  }
			if(item.equals("bear_hat")) { spr_master = atlas_itensHat.createSprite("bear_hat");  return spr_master;  }
			if(item.equals("blizzardcap_hat")) { spr_master = atlas_itensHat.createSprite("blizzardcap_hat");  return spr_master;  }
			if(item.equals("brazilflag_hat")) { spr_master = atlas_itensHat.createSprite("brazilflag_hat");  return spr_master;  }
			if(item.equals("bunny_hat")) { spr_master = atlas_itensHat.createSprite("bunny_hat");  return spr_master;  }
			if(item.equals("butterfly_hat")) { spr_master = atlas_itensHat.createSprite("butterfly_hat");  return spr_master;  }
			if(item.equals("capoult_hat")) { spr_master = atlas_itensHat.createSprite("capoult_hat");  return spr_master;  }
			if(item.equals("capred_hat")) { spr_master = atlas_itensHat.createSprite("capred_hat");  return spr_master;  }
			if(item.equals("clay_hat")) { spr_master = atlas_itensHat.createSprite("clay_hat");  return spr_master;  }
			if(item.equals("clock_hat")) { spr_master = atlas_itensHat.createSprite("clock_hat");  return spr_master;  }
			if(item.equals("cooking_hat")) { spr_master = atlas_itensHat.createSprite("cooking_hat");  return spr_master;  }
			if(item.equals("eyepatch_hat")) { spr_master = atlas_itensHat.createSprite("eyepatch_hat");  return spr_master;  }
			if(item.equals("headphone_hat")) { spr_master = atlas_itensHat.createSprite("headphone_hat");  return spr_master;  }
			if(item.equals("magician_hat")) { spr_master = atlas_itensHat.createSprite("magician_hat");  return spr_master;  }
			if(item.equals("miner_hat")) { spr_master = atlas_itensHat.createSprite("miner_hat");  return spr_master;  }
			if(item.equals("pirate_hat")) { spr_master = atlas_itensHat.createSprite("pirate_hat");  return spr_master;  }
			if(item.equals("poyobat_hat")) { spr_master = atlas_itensHat.createSprite("poyobat_hat");  return spr_master;  }
			if(item.equals("santa_hat")) { spr_master = atlas_itensHat.createSprite("santa_hat");  return spr_master;  }
			if(item.equals("slime_hat")) { spr_master = atlas_itensHat.createSprite("slime_hat");  return spr_master;  }
			if(item.equals("sunglass_hat")) { spr_master = atlas_itensHat.createSprite("sunglass_hat");  return spr_master;  }
			if(item.equals("timer_hat")) { spr_master = atlas_itensHat.createSprite("timer_hat");  return spr_master;  }
			
			
			//Set
			if(item.equals("basicset_m")) { spr_master = atlas_itensSet.createSprite("basicset_m");  return spr_master;  }
			if(item.equals("basicset_f")) { spr_master = atlas_itensSet.createSprite("basicset_f");  return spr_master;  }
			if(item.equals("blackset_f")) { spr_master = atlas_itensSet.createSprite("blackset_f");  return spr_master;  }
			if(item.equals("blackset_m")) { spr_master = atlas_itensSet.createSprite("blackset_m");  return spr_master;  }
			if(item.equals("catset_f")) { spr_master = atlas_itensSet.createSprite("catset_f");  return spr_master;  }
			if(item.equals("catset_m")) { spr_master = atlas_itensSet.createSprite("catset_m");  return spr_master;  }
			if(item.equals("flamingoset_f")) { spr_master = atlas_itensSet.createSprite("flamingoset_f");  return spr_master;  }
			if(item.equals("flamingoset_m")) { spr_master = atlas_itensSet.createSprite("flamingoset_m");  return spr_master;  }
			if(item.equals("rougeset_f")) { spr_master = atlas_itensSet.createSprite("rougeset_f");  return spr_master;  }
			if(item.equals("rougeset_m")) { spr_master = atlas_itensSet.createSprite("rougeset_m");  return spr_master;  }
			if(item.equals("schoolset_f")) { spr_master = atlas_itensSet.createSprite("schoolset_f");  return spr_master;  }
			if(item.equals("schoolset_m")) { spr_master = atlas_itensSet.createSprite("schoolset_m");  return spr_master;  }
			
			//Drop
			if(item.equals("batfang_loot")) { spr_master = atlas_itensDrop.createSprite("batfang_loot");  return spr_master;  }
			if(item.equals("bearfoot_loot")) { spr_master = atlas_itensDrop.createSprite("bearfoot_loot");  return spr_master;  }
			if(item.equals("bigmoney_loot")) { spr_master = atlas_itensDrop.createSprite("bigmoney_loot");  return spr_master;  }
			if(item.equals("blop_loot")) { spr_master = atlas_itensDrop.createSprite("blop_loot");  return spr_master;  }
			if(item.equals("crown_loot")) { spr_master = atlas_itensDrop.createSprite("crown_loot");  return spr_master;  }
			if(item.equals("fang_loot")) { spr_master = atlas_itensDrop.createSprite("fang_loot");  return spr_master;  }
			if(item.equals("freezertail_loot")) { spr_master = atlas_itensDrop.createSprite("freezertail_loot");  return spr_master;  }
			if(item.equals("galhos_loot")) { spr_master = atlas_itensDrop.createSprite("galhos_loot");  return spr_master;  }
			if(item.equals("goblinhat_loot")) { spr_master = atlas_itensDrop.createSprite("goblinhat_loot");  return spr_master;  }
			if(item.equals("lowmoney_loot")) { spr_master = atlas_itensDrop.createSprite("lowmoney_loot");  return spr_master;  }
			if(item.equals("mushroom_loot")) { spr_master = atlas_itensDrop.createSprite("mushroom_loot");  return spr_master;  }
			if(item.equals("poisonleaf_loot")) { spr_master = atlas_itensDrop.createSprite("poisonleaf_loot");  return spr_master;  }
			if(item.equals("snowballs_loot")) { spr_master = atlas_itensDrop.createSprite("snowballs_loot");  return spr_master;  }
			
			//Weapon
			if(item.equals("basicaxe")) { spr_master = atlas_itensWeapon.createSprite("basicaxe");  return spr_master;  }
			if(item.equals("anchoraxe")) { spr_master = atlas_itensWeapon.createSprite("anchoraxe");  return spr_master;  }
			if(item.equals("basicdagger")) { spr_master = atlas_itensWeapon.createSprite("basicdagger");  return spr_master;  }
			if(item.equals("basicknife")) { spr_master = atlas_itensWeapon.createSprite("basicknife");  return spr_master;  }
			if(item.equals("basicpistol")) { spr_master = atlas_itensWeapon.createSprite("basicpistol");  return spr_master;  }
			if(item.equals("blackdagger")) { spr_master = atlas_itensWeapon.createSprite("blackdagger");  return spr_master;  }
			if(item.equals("bloodteethaxe")) { spr_master = atlas_itensWeapon.createSprite("bloodteethaxe");  return spr_master;  }
			if(item.equals("butterflyrod")) { spr_master = atlas_itensWeapon.createSprite("butterflyrod");  return spr_master;  }
			if(item.equals("cannonpistol")) { spr_master = atlas_itensWeapon.createSprite("cannonpistol");  return spr_master;  }
			if(item.equals("clerigrod")) { spr_master = atlas_itensWeapon.createSprite("clerigrod");  return spr_master;  }
			if(item.equals("colisseumdagger")) { spr_master = atlas_itensWeapon.createSprite("colisseumdagger");  return spr_master;  }
			if(item.equals("cristalsword")) { spr_master = atlas_itensWeapon.createSprite("cristalsword");  return spr_master;  }
			if(item.equals("curvedsword")) { spr_master = atlas_itensWeapon.createSprite("curvedsword");  return spr_master;  }
			if(item.equals("deathrod")) { spr_master = atlas_itensWeapon.createSprite("deathrod");  return spr_master;  }
			if(item.equals("doubleedgeknife")) { spr_master = atlas_itensWeapon.createSprite("doubleedgeknife");  return spr_master;  }
			if(item.equals("edgedagger")) { spr_master = atlas_itensWeapon.createSprite("edgedagger");  return spr_master;  }
			if(item.equals("edgesword")) { spr_master = atlas_itensWeapon.createSprite("edgesword");  return spr_master;  }
			if(item.equals("flamebergdagger")) { spr_master = atlas_itensWeapon.createSprite("flamebergdagger");  return spr_master;  }
			if(item.equals("flamesword")) { spr_master = atlas_itensWeapon.createSprite("flamesword");  return spr_master;  }
			if(item.equals("gemrod")) { spr_master = atlas_itensWeapon.createSprite("gemrod");  return spr_master;  }
			if(item.equals("gloomrod")) { spr_master = atlas_itensWeapon.createSprite("gloomrod");  return spr_master;  }
			if(item.equals("guitaraxe")) { spr_master = atlas_itensWeapon.createSprite("guitaraxe");  return spr_master;  }
			if(item.equals("hammeraxe")) { spr_master = atlas_itensWeapon.createSprite("hammeraxe");  return spr_master;  }
			if(item.equals("heavymachinepistol")) { spr_master = atlas_itensWeapon.createSprite("heavymachinepistol");  return spr_master;  }
			if(item.equals("killeraxe")) { spr_master = atlas_itensWeapon.createSprite("killeraxe");  return spr_master;  }
			if(item.equals("knightsword")) { spr_master = atlas_itensWeapon.createSprite("knightsword");  return spr_master;  }
			if(item.equals("lightpistol")) { spr_master = atlas_itensWeapon.createSprite("lightpistol");  return spr_master;  }
			if(item.equals("lightwieldrod")) { spr_master = atlas_itensWeapon.createSprite("lightwieldrod");  return spr_master;  }
			if(item.equals("machinepistol")) { spr_master = atlas_itensWeapon.createSprite("machinepistol");  return spr_master;  }
			if(item.equals("marinedagger")) { spr_master = atlas_itensWeapon.createSprite("marinedagger");  return spr_master;  }
			if(item.equals("pickaxe")) { spr_master = atlas_itensWeapon.createSprite("pickaxe");  return spr_master;  }
			if(item.equals("poisondagger")) { spr_master = atlas_itensWeapon.createSprite("poisondagger");  return spr_master;  }
			if(item.equals("ragesword")) { spr_master = atlas_itensWeapon.createSprite("ragesword");  return spr_master;  }
			if(item.equals("revolverpistol")) { spr_master = atlas_itensWeapon.createSprite("revolverpistol");  return spr_master;  }
			if(item.equals("riflepistol")) { spr_master = atlas_itensWeapon.createSprite("riflepistol");  return spr_master;  }
			if(item.equals("sabersword")) { spr_master = atlas_itensWeapon.createSprite("sabersword");  return spr_master;  }
			if(item.equals("sacredrod")) { spr_master = atlas_itensWeapon.createSprite("sacredrod");  return spr_master;  }
			if(item.equals("scytheaxe")) { spr_master = atlas_itensWeapon.createSprite("scytheaxe");  return spr_master;  }
			if(item.equals("serpentrod")) { spr_master = atlas_itensWeapon.createSprite("serpentrod");  return spr_master;  }
			if(item.equals("serpentsword")) { spr_master = atlas_itensWeapon.createSprite("serpentsword");  return spr_master;  }
			if(item.equals("sharkpistol")) { spr_master = atlas_itensWeapon.createSprite("sharkpistol");  return spr_master;  }
			if(item.equals("sharpaxe")) { spr_master = atlas_itensWeapon.createSprite("sharpaxe");  return spr_master;  }
			if(item.equals("shooterpistol")) { spr_master = atlas_itensWeapon.createSprite("shooterpistol");  return spr_master;  }
			if(item.equals("starrod")) { spr_master = atlas_itensWeapon.createSprite("starrod");  return spr_master;  }
			if(item.equals("stickrod")) { spr_master = atlas_itensWeapon.createSprite("stickrod");  return spr_master;  }
			if(item.equals("thunderdagger")) { spr_master = atlas_itensWeapon.createSprite("thunderdagger");  return spr_master;  }
			if(item.equals("triplodagger")) { spr_master = atlas_itensWeapon.createSprite("triplodagger");  return spr_master;  }
			if(item.equals("turretpistol")) { spr_master = atlas_itensWeapon.createSprite("turretpistol");  return spr_master;  }
			if(item.equals("venomsword")) { spr_master = atlas_itensWeapon.createSprite("venomsword");  return spr_master;  }
			if(item.equals("winddagger")) { spr_master = atlas_itensWeapon.createSprite("winddagger");  return spr_master;  }
			if(item.equals("woodsword")) { spr_master = atlas_itensWeapon.createSprite("woodsword");  return spr_master;  }
			if(item.equals("wrenckaxe")) { spr_master = atlas_itensWeapon.createSprite("wrenckaxe");  return spr_master;  }
			
			//Cristals
			if(item.equals("blue_crystal_intextra_1") || item.equals("blue_crystal_intextra_2") || item.equals("blue_crystal_intextra_3")) { spr_master = atlas_itensUtil.createSprite("blue_crystal");  return spr_master;    }
			if(item.equals("green_crystal_lukextra_1") || item.equals("green_crystal_lukextra_2") || item.equals("green_crystal_lukextra_3")) { spr_master = atlas_itensUtil.createSprite("green_crystal");  return spr_master;  }
			if(item.equals("purple_crystal_vitextra_1") || item.equals("purple_crystal_vitextra_2") || item.equals("purple_crystal_vitextra_3")) { spr_master = atlas_itensUtil.createSprite("purple_crystal");  return spr_master;  }
			if(item.equals("yellow_crystal_agiextra_1") || item.equals("yellow_crystal_agiextra_2") || item.equals("yellow_crystal_agiextra_3")) { spr_master = atlas_itensUtil.createSprite("yellow_crystal");  return spr_master;  }
			if(item.equals("red_crystal_strextra_1") || item.equals("red_crystal_strextra_2") || item.equals("red_crystal_strextra_3")) { spr_master = atlas_itensUtil.createSprite("red_crystal");  return spr_master;  }
			if(item.equals("grey_crystal_dexextra_1") || item.equals("grey_crystal_dexextra_2") || item.equals("grey_crystal_dexextra_3")) { spr_master = atlas_itensUtil.createSprite("gray_crystal");  return spr_master;  }
			if(item.equals("orange_crystal_resextra_1") || item.equals("orange_crystal_resextra_2") || item.equals("orange_crystal_resextra_3")) { spr_master = atlas_itensUtil.createSprite("orange_crystal");  return spr_master;  }
					
			return spr_master;
		}
	
		public void CrystalGive() {		
			//AddItemBag 
			String item = "";
			String itemName = "";
			String lstitensFinal = "";
			String crystalType = "";
			String[] lstItem = player.Itens.split("-");
			String[] itemSplit;
			String listaItemFinal;
			int qtd = 0;
			boolean itemHasAdded = false;
			boolean crystalexist = false;
		
			for(int i = 0; i < 48; i++) {
				if(!lstItem[i].equals("[NONE]")) {
				
				item = lstItem[i];		
				itemSplit = item.split("#");
				itemName = itemSplit[0].replace("[", "");
				qtd = Integer.parseInt(itemSplit[1].replace("]", ""));
				
				if(itemName.equals("blue_orb") && qtd >= 50) {
					crystalType = CrystalRoullete("blue_orb");
					AddItemBag(crystalType); 
					itemHasAdded = true;
					
					qtd = qtd - 50;
					if(qtd > 0) { lstItem[i] = "[blue_orb#" + qtd + "]"; }
					if(qtd <= 0) { lstItem[i] = "[NONE]"; }
					
					listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
					listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
					player.Itens = listaItemFinal;				
				}
				
				if(itemName.equals("green_orb") && qtd >= 20) {
					crystalType = CrystalRoullete("green_orb");
					AddItemBag(crystalType); 
					itemHasAdded = true;
					
					qtd = qtd - 50;
					if(qtd > 0) { lstItem[i] = "[green_orb#" + qtd + "]"; }
					if(qtd <= 0) { lstItem[i] = "[NONE]"; }
					
					listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
					listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
					player.Itens = listaItemFinal;			
				}
				
				if(itemName.equals("purple_orb") && qtd >= 20) {
					crystalType = CrystalRoullete("purple_orb");
					AddItemBag(crystalType); 
					itemHasAdded = true;
					
					qtd = qtd - 50;
					if(qtd > 0) { lstItem[i] = "[purple_orb#" + qtd + "]"; }
					if(qtd <= 0) { lstItem[i] = "[NONE]"; }
					
					listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
					listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
					player.Itens = listaItemFinal;			
				}
				
				if(itemName.equals("yellow_orb") && qtd >= 20) {
					crystalType = CrystalRoullete("yellow_orb");
					AddItemBag(crystalType); 
					itemHasAdded = true;
					
					qtd = qtd - 50;
					if(qtd > 0) { lstItem[i] = "[yellow_orb#" + qtd + "]"; }
					if(qtd <= 0) { lstItem[i] = "[NONE]"; }
					
					listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
					listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
					player.Itens = listaItemFinal;				
				}
				
				if(itemName.equals("red_orb") && qtd >= 20) {
					crystalType = CrystalRoullete("red_orb");
					AddItemBag(crystalType); 
					itemHasAdded = true;
					
					qtd = qtd - 50;
					if(qtd > 0) { lstItem[i] = "[red_orb#" + qtd + "]"; }
					if(qtd <= 0) { lstItem[i] = "[NONE]"; }
					
					listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
					listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
					player.Itens = listaItemFinal;		
				}	
				
				if(itemName.equals("gray_orb") && qtd >= 20) {
					crystalType = CrystalRoullete("gray_orb");
					AddItemBag(crystalType); 
					itemHasAdded = true;
					
					qtd = qtd - 50;
					if(qtd > 0) { lstItem[i] = "[gray_orb#" + qtd + "]"; }
					if(qtd <= 0) { lstItem[i] = "[NONE]"; }
					
					listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
					listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
					player.Itens = listaItemFinal;				
				}
				
				if(itemName.equals("orange_orb") && qtd >= 20) {
					crystalType = CrystalRoullete("orange_orb");
					AddItemBag(crystalType); 
					itemHasAdded = true;
					
					qtd = qtd - 50;
					if(qtd > 0) { lstItem[i] = "[orange_orb#" + qtd + "]"; }
					if(qtd <= 0) { lstItem[i] = "[NONE]"; }
					
					listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
					listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
					player.Itens = listaItemFinal;				
				}
				
				}			
			}
			
			if(itemHasAdded) {		
				for(int i = 0; i < 48; i++) {
					if(!lstItem[i].equals("[NONE]")) {
						item = lstItem[i];		
						itemSplit = item.split("#");
						itemName = itemSplit[0].replace("[", "");
						qtd = Integer.parseInt(itemSplit[1].replace("]", ""));
						
						if(itemName.equals(crystalType)) {
							item = "[" + crystalType + "#" + qtd + "]"; 
							lstItem[i] = item;
							lstitensFinal = Arrays.toString(lstItem).replace(", ","-");
							lstitensFinal = lstitensFinal.substring(1, lstitensFinal.length() -1);
							player.Itens = lstitensFinal;	
							crystalexist = true;
						}		
					}
				}
				
				if(!crystalexist) {
					for(int i = 0; i < 48; i++) {			
						if(lstItem[i].equals("[NONE]")) {
						item = "[" + crystalType + "#" + 1 + "]"; 
						lstItem[i] = item;
						lstitensFinal = Arrays.toString(lstItem).replace(", ","-");
						lstitensFinal = lstitensFinal.substring(1, lstitensFinal.length() -1);
						player.Itens = lstitensFinal;	
						crystalexist = false;
						SysMsg = "Trocado com Sucesso!";
						SysMsgCount = 100;
						}
					}
				}
				
				SysMsg = "Trocado com Sucesso!";
				SysMsgCount = 100;
			}
			else {
				SysMsg = "Quantidade insuficiente!";
				SysMsgCount = 100;
			}
		}
		
		public String CrystalRoullete(String typeCrystal) {
			
			int selected = randnumber.nextInt(1000);
			
			if(typeCrystal.equals("blue_orb")) {
				if(selected > 0 && selected <= 600) { return "blue_crystal_intextra_1"; }
				if(selected > 600 && selected <= 950) { return "blue_crystal_intextra_2"; }
				if(selected > 995 && selected <= 1000) { return "blue_crystal_intextra_3"; }
			}
			
			if(typeCrystal.equals("green_orb")) {
				if(selected > 0 && selected <= 600) { return "green_crystal_lukextra_1"; }
				if(selected > 600 && selected <= 950) { return "green_crystal_lukextra_2"; }
				if(selected > 995 && selected <= 1000) { return "green_crystal_lukextra_3"; }
			}
			
			if(typeCrystal.equals("purple_orb")) {
				if(selected > 0 && selected <= 600) { return "purple_crystal_vitextra_1"; }
				if(selected > 600 && selected <= 950) { return "purple_crystal_vitextra_2"; }
				if(selected > 995 && selected <= 1000) { return "purple_crystal_vitextra_3"; }
			}
			
			if(typeCrystal.equals("yellow_orb")) {
				if(selected > 0 && selected <= 600) { return "yellow_crystal_agiextra_1"; }
				if(selected > 600 && selected <= 950) { return "yellow_crystal_agiextra_2"; }
				if(selected > 995 && selected <= 1000) { return "yellow_crystal_agiextra_3"; }
			}
			
			if(typeCrystal.equals("red_orb")) {
				if(selected > 0 && selected <= 600) { return "red_crystal_strextra_1"; }
				if(selected > 600 && selected <= 950) { return "red_crystal_strextra_2"; }
				if(selected > 995 && selected <= 1000) { return "red_crystal_strextra_3"; }
			}
			
			if(typeCrystal.equals("gray_orb")) {
				if(selected > 0 && selected <= 600) { return "grey_crystal_dexextra_1"; }
				if(selected > 600 && selected <= 950) { return "grey_crystal_dexextra_2"; }
				if(selected > 995 && selected <= 1000) { return "grey_crystal_dexextra_3"; }
			}
			
			if(typeCrystal.equals("orange_orb")) {
				if(selected > 0 && selected <= 600) { return "orange_crystal_resextra_1"; }
				if(selected > 600 && selected <= 950) { return "orange_crystal_resextra_2"; }
				if(selected > 995 && selected <= 1000) { return "orange_crystal_resextra_3"; }
			}
					
			return "none";
		}
		
		public void ItemDrop(String mob) {
			int chance = randnumber.nextInt(100);
			
			if(mob.equals("slime"))
			if(chance <= 40) { AddItemBag("blop_loot"); itemdropname = "Gosma"; showDropMsg = 100; return; }
			if(chance >= 40 && chance <= 95) { AddItemBag("blue_orb"); itemdropname = "Orb Azul"; showDropMsg = 100; return; }
			if(chance >= 95 && chance <= 98) { AddItemBag("hpcan"); itemdropname = "Lata de HP"; showDropMsg = 100; return; }
			if(chance >= 98) { AddItemBag("slime_hat"); itemdropname = "Chapeu de Slime"; showDropMsg = 100; return; }
			
			if(mob.equals("oikplant"))
			if(chance <= 40) { AddItemBag("poisonleaf_loot"); return; }
			if(chance >= 40 && chance <= 95) { AddItemBag("green_orb"); return; }
			if(chance >= 95 && chance <= 98) { AddItemBag("hpcan"); return;  }
			if(chance >= 98) { AddItemBag("lowmoney_loot"); return; }
			
			if(mob.equals("poro"))
			if(chance <= 40) { AddItemBag("mushroom_loot"); return; }
			if(chance >= 40 && chance <= 95) { AddItemBag("yellow_orb"); return; }
			if(chance >= 95 && chance <= 98) { AddItemBag("hpcan"); return;  }
			if(chance >= 98) { AddItemBag("mpcan"); return; }
		}
		
		public String ShowQuantityItem(int num) {
			//Structure: [HPCAN#3]
			String qtd = "";
			String item = "";
			String[] lstItem = player.Itens.split("-");
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
		
		public void CheckStatus(String sts) {
			if(sts.equals("str")) {
				if(player.StatusPoint >= 1) {
					player.Atk = player.Atk + 3;
					player.Str = player.Str + 1;
					player.StatusPoint = player.StatusPoint - 1;
					if(player.StatusPoint <= 0) { player.StatusPoint = 0; }
				}
			}
			if(sts.equals("vit")) {
				if(player.StatusPoint >= 1) {
					player.HpMax = player.HpMax + 20;
					player.Vit = player.Vit + 1;
					player.StatusPoint = player.StatusPoint - 1;
					if(player.StatusPoint <= 0) { player.StatusPoint = 0; }
				}
			}
			if(sts.equals("agi")) {
				if(player.StatusPoint >= 1) {
					player.AtkTimerMax = player.AtkTimerMax - 1;
					player.Agi = player.Agi + 1;
					player.StatusPoint = player.StatusPoint - 1;
					if(player.StatusPoint <= 0) { player.StatusPoint = 0; }
				}
			}
			if(sts.equals("dex")) {
				if(player.StatusPoint >= 1) {
					player.Evasion = player.Evasion + 1;
					player.Atk = player.Atk + 1;
					player.Dex = player.Dex + 1;
					player.StatusPoint = player.StatusPoint - 1;
					if(player.StatusPoint <= 0) { player.StatusPoint = 0; }
				}
			}
			if(sts.equals("wis")) {
				if(player.StatusPoint >= 1) {
					player.MpMax = player.MpMax + 15;
					player.Wis = player.Wis + 1;
					player.StatusPoint = player.StatusPoint - 1;
					if(player.StatusPoint <= 0) { player.StatusPoint = 0; }
				}
			}
			if(sts.equals("luk")) {
				if(player.StatusPoint >= 1) {
					player.Atk = player.Atk + 1;
					player.Luk = player.Luk + 1;
					player.StatusPoint = player.StatusPoint - 1;
					if(player.StatusPoint <= 0) { player.StatusPoint = 0; }
				}
			}
			if(sts.equals("res")) {
				if(player.StatusPoint >= 1) {
					player.regenTimeMax = player.regenTimeMax - 1;
					player.Res = player.Res + 1;
					player.StaminaMax = player.StaminaMax + 5;
					player.StatusPoint = player.StatusPoint - 1;
					if(player.StatusPoint <= 0) { player.StatusPoint = 0; }
				}
			}
		}
		
		//SKILL
		public void CheckSkill(int num) {
			
			if(num == 1 && player.Job.equals("Aprendiz")) { SetUseSkill("tripleattack"); }
			if(num == 1 && player.Job.equals("Espadachim")) { SetUseSkill("flysword"); }
			if(num == 1 && player.Job.equals("Feiticeiro")) { SetUseSkill("fireball"); }
			if(num == 1 && player.Job.equals("Batedor")) { SetUseSkill("hammercrash"); }
			if(num == 1 && player.Job.equals("Pistoleiro")) { SetUseSkill("bulletrain"); }
			if(num == 1 && player.Job.equals("Medico")) { SetUseSkill("heal"); }
			if(num == 1 && player.Job.equals("Ladrao")) { SetUseSkill("poisonhit"); }
			
			if(num == 2 && player.Job.equals("Aprendiz")) { SetUseSkill("regen"); }
			if(num == 2 && player.Job.equals("Espadachim")) { SetUseSkill("ironshield"); }
			if(num == 2 && player.Job.equals("Feiticeiro")) { SetUseSkill("thundercloud"); }
			if(num == 2 && player.Job.equals("Batedor")) { SetUseSkill("overpower"); }
			if(num == 2 && player.Job.equals("Pistoleiro")) { SetUseSkill("mine"); }
			if(num == 2 && player.Job.equals("Medico")) { SetUseSkill("holyprism"); }
			if(num == 2 && player.Job.equals("Ladrao")) { SetUseSkill("invisibility"); }
			
			if(num == 3 && player.Job.equals("Aprendiz")) { SetUseSkill("rockbound"); }
			if(num == 3 && player.Job.equals("Espadachim")) { SetUseSkill("healthboost"); }
			if(num == 3 && player.Job.equals("Feiticeiro")) { SetUseSkill("icecrystal"); }
			if(num == 3 && player.Job.equals("Batedor")) { SetUseSkill("berserk"); }
			if(num == 3 && player.Job.equals("Pistoleiro")) { SetUseSkill("lockshot"); }
			if(num == 3 && player.Job.equals("Medico")) { SetUseSkill("boost"); }
			if(num == 3 && player.Job.equals("Ladrao")) { SetUseSkill("steal"); }			
		}
		
		
		public void SetUseSkill(String skill) {
			//Cost
			if(skill.equals("tripleattack") && player.Mp < 5) { notmp = true; return; }
			if(skill.equals("rockbound") && player.Mp < 5) { notmp = true; return; }
			if(skill.equals("regen") && player.Mp < 2) { notmp = true; return; }
			
			if(skill.equals("flysword") && player.Mp < 45) { notmp = true; return; }
			if(skill.equals("ironshield") && player.Mp < 30) { notmp = true; return; }
			if(skill.equals("healthboost") && player.Mp < 40) { notmp = true; return; }
			
			if(skill.equals("fireball") && player.Mp < 30) { notmp = true; return; }
			if(skill.equals("thundercloud") && player.Mp < 60) { notmp = true; return; }
			if(skill.equals("icecrystal") && player.Mp < 100) { notmp = true; return; }
			
			if(skill.equals("heal") && player.Mp < 20) { notmp = true; return; }
			if(skill.equals("holyprism") && player.Mp < 5) { notmp = true; return; }
			if(skill.equals("boost") && player.Mp < 40) { notmp = true; return; }
			
			if(skill.equals("poisonhit") && player.Mp < 25) { notmp = true; return; }
			if(skill.equals("steal") && player.Mp < 10) { notmp = true; return; }
			if(skill.equals("invisibility") && player.Mp < 15) { notmp = true; return; }
			
			if(skill.equals("berserk") && player.Mp < 25) { notmp = true; return; }
			if(skill.equals("overpower") && player.Mp < 50) { notmp = true; return; }
			if(skill.equals("hammercrash") && player.Mp < 20) { notmp = true; return; }
			
			if(skill.equals("lockshot") && player.Mp < 15) { notmp = true; return; }
			if(skill.equals("mine") && player.Mp < 20) { notmp = true; return; }
			if(skill.equals("bulletrain") && player.Mp < 40) { notmp = true; return; }
			
			if(skill.equals("tripleattack")) { player.Mp = player.Mp - 5; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("rockbound")) { player.Mp = player.Mp - 5; if(player.Mp <= 0) { player.Mp = player.Mp = 0;}}
			if(skill.equals("regen")) { player.Mp = player.Mp - 2; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			
			if(skill.equals("flysword")) { player.Mp = player.Mp - 25; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("ironshield")) { player.Mp = player.Mp - 15; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("healthboost")) { player.Mp = player.Mp - 40; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			
			if(skill.equals("fireball")) { player.Mp = player.Mp - 10; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("thundercloud")) { player.Mp = player.Mp - 40; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("icecrystal")) { player.Mp = player.Mp - 100; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			
			if(skill.equals("heal")) { player.Mp = player.Mp - 10; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("holyprism")) { player.Mp = player.Mp - 5; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("boost")) { player.Mp = player.Mp - 40; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			
			if(skill.equals("poisonhit")) { player.Mp = player.Mp - 10; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("steal")) { player.Mp = player.Mp - 10; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("invisibility")) { player.Mp = player.Mp - 15; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			
			if(skill.equals("berserk")) { player.Mp = player.Mp - 25; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("overpower")) { player.Mp = player.Mp - 50; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("hammercrash")) { player.Mp = player.Mp - 20; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			
			if(skill.equals("lockshot")) { player.Mp = player.Mp - 15; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("mine")) { player.Mp = player.Mp - 20; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("bulletrain")) { player.Mp = player.Mp - 40; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			
			
			//Ranged?
			if(skill.equals("tripleattack")) { rangedAttack = false; }
			if(skill.equals("regen")) { rangedAttack = true; }
			if(skill.equals("rockbound")) { rangedAttack = true; }
			
			if(skill.equals("flysword")) { rangedAttack = false; }
			if(skill.equals("healthboost")) { rangedAttack = true; }
			if(skill.equals("ironshield")) { rangedAttack = false; }
			
			if(skill.equals("fireball")) { rangedAttack = true; }
			if(skill.equals("icecrystal")) { rangedAttack = true; }
			if(skill.equals("thundercloud")) { rangedAttack = true; }
			
			if(skill.equals("heal")) { rangedAttack = true; }
			if(skill.equals("boost")) { rangedAttack = true; }
			if(skill.equals("holyprism")) { rangedAttack = true; }
			
			if(skill.equals("hammercrash")) { rangedAttack = false; }
			if(skill.equals("overpower")) { rangedAttack = false; }		
			if(skill.equals("berserk")) { rangedAttack = false; }
			
			
			if(skill.equals("lockshot")) { rangedAttack = true; }
			if(skill.equals("mine")) { rangedAttack = true; }
			if(skill.equals("bulletrain")) { rangedAttack = true; }
			
			if(skill.equals("steal")) { rangedAttack = false; }
			if(skill.equals("poisonhit")) { rangedAttack = false; }
			if(skill.equals("invisibility")) { rangedAttack = true; }
					
			
			skillname = skill;
			if(!rangedAttack) { 
				CheckAreaRangedSkill();
			}
			else { 
				selectAreaRanged = true; 
			}
		  
		}
		
		public void MobDead(GameObject mob) {
			if(mob.MobHp <= 0) { 
				mob.MobHp = 0; 
				mob.MobDead = "yes";   
				
				player.Target = "none";
				player.AtkTimer = player.AtkTimerMax;
				player.playerInBattle = "no";
			    playerInAttack = false;
			    playerInCast = false;	
			    autoattack = false;
			    
			    ItemDrop(mob.MobName);
			    GiveExp(mob.MobExp);
			    player.Money = 1;
			    return;
			}
		}
		
		public void MobDeadOnline(int hp, GameObject mob) {
			if(hp <= 0) {
				player.Target = "none";
				player.AtkTimer = player.AtkTimerMax;
				player.playerInBattle = "no";
			    playerInAttack = false;
			    playerInCast = false;	
			    autoattack = false;
			    
			    ItemDrop(mob.MobName);
			    GiveExp(mob.MobExp);
			    player.Money = 1;
			    return;
			}
		}
		
		public void CheckAreaRangedSkill() {
			if(player.Map.equals("Sewers") && autoattack) {
				for(int i = 0; i < lstMobs.size(); i++) {
					
					//Close Ranged
					if(player.Target.equals(lstMobs.get(i).MobID) && !rangedAttack) {		 
						if((lstMobs.get(i).MobPosX + 5) > (player.PosX - 5) && (lstMobs.get(i).MobPosX + 5) < (player.PosX + 15)
						   && (lstMobs.get(i).MobPosY + 5) > (player.PosY - 7) && (lstMobs.get(i).MobPosY + 5) < (player.PosY + 18)) {
							player.playerInBattle = "yes";
							lstMobs.get(i).MobTarget = player.Name;				
							//Aprendiz
							if(skillname.equals("tripleattack")) {
								int atkweapon = CheckWeapon();
								int totaldmg = player.Atk + ((player.Str * 2) + atkweapon);
								int mobHP = lstMobs.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(keepnetwork) { OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { lstMobs.get(i).MobHp = mobHP; }
								skillEffect = true;
								GameObject skillInUse = new GameObject();
								skillInUse.SkillName = "tripleattack";
								skillInUse.SkillPosX = lstMobs.get(i).MobPosX +5;
								skillInUse.SkillPosY = lstMobs.get(i).MobPosY + 5;
								skillInUse.DamagePosX = lstMobs.get(i).MobPosX + 5;
								skillInUse.DamagePosY = lstMobs.get(i).MobPosY + 5;
								skillInUse.SkillTime = 100;
								skillInUse.DamageTime = 100;
								skillInUse.DamageType = "mob";
								skillInUse.DamageValue = totaldmg;
								lstSkills.add(skillInUse);	
								lstDamage.add(skillInUse);
								rangedAttack = false;
								if(keepnetwork) { MobDeadOnline(mobHP,lstMobs.get(i)); } else { MobDead(lstMobs.get(i)); }
								
							}
							if(skillname.equals("hammercrash")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Str * 2) + (player.Vit * 2) + atkweapon);
								int mobHP = lstMobs.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(keepnetwork) { OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { lstMobs.get(i).MobHp = mobHP; }
								skillEffect = true;
								GameObject skillInUse = new GameObject();
								skillInUse.SkillName = "tripleattack";
								skillInUse.SkillPosX = lstMobs.get(i).MobPosX;
								skillInUse.SkillPosY = lstMobs.get(i).MobPosY;
								skillInUse.DamagePosX = lstMobs.get(i).MobPosX;
								skillInUse.DamagePosY = lstMobs.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								skillInUse.DamageTime = 100;
								skillInUse.DamageType = "mob";
								skillInUse.DamageValue = totaldmg;
								lstSkills.add(skillInUse);	
								lstDamage.add(skillInUse);
								rangedAttack = false;
								if(keepnetwork) { MobDeadOnline(mobHP,lstMobs.get(i)); } else { MobDead(lstMobs.get(i)); }
							}
							if(skillname.equals("flysword")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Str * 3) + (player.Agi * 2) + atkweapon);
								int mobHP = lstMobs.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(keepnetwork) { OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { lstMobs.get(i).MobHp = mobHP; }
								skillEffect = true;
								GameObject skillInUse = new GameObject();
								skillInUse.SkillName = "tripleattack";
								skillInUse.SkillPosX = lstMobs.get(i).MobPosX;
								skillInUse.SkillPosY = lstMobs.get(i).MobPosY;
								skillInUse.DamagePosX = lstMobs.get(i).MobPosX;
								skillInUse.DamagePosY = lstMobs.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								skillInUse.DamageTime = 100;
								skillInUse.DamageType = "mob";
								skillInUse.DamageValue = totaldmg;
								lstSkills.add(skillInUse);	
								lstDamage.add(skillInUse);
								rangedAttack = false;
								if(keepnetwork) { MobDeadOnline(mobHP,lstMobs.get(i)); } else { MobDead(lstMobs.get(i)); }
							}
							if(skillname.equals("poisonhit")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Luk * 2)+ (player.Str * 2) + atkweapon);
								int mobHP = lstMobs.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(keepnetwork) { OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { lstMobs.get(i).MobHp = mobHP; }
								skillEffect = true;
								GameObject skillInUse = new GameObject();
								skillInUse.SkillName = "tripleattack";
								skillInUse.SkillPosX = lstMobs.get(i).MobPosX;
								skillInUse.SkillPosY = lstMobs.get(i).MobPosY;
								skillInUse.DamagePosX = lstMobs.get(i).MobPosX;
								skillInUse.DamagePosY = lstMobs.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								skillInUse.DamageTime = 100;
								skillInUse.DamageType = "mob";
								skillInUse.DamageValue = totaldmg;
								lstSkills.add(skillInUse);	
								lstDamage.add(skillInUse);
								rangedAttack = false;
								if(keepnetwork) { MobDeadOnline(mobHP,lstMobs.get(i)); } else { MobDead(lstMobs.get(i)); }
							}
							if(skillname.equals("steal")) {
								
							}
							if(skillname.equals("overpower")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Vit * 3) + (player.Str * 5) + (player.Luk * 2) + atkweapon);	
								int mobHP = lstMobs.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(keepnetwork) { OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { lstMobs.get(i).MobHp = mobHP; }					
								skillEffect = true;
								GameObject skillInUse = new GameObject();
								skillInUse.SkillName = "overpower";
								skillInUse.SkillPosX = lstMobs.get(i).MobPosX;
								skillInUse.SkillPosY = lstMobs.get(i).MobPosY;
								skillInUse.DamagePosX = lstMobs.get(i).MobPosX;
								skillInUse.DamagePosY = lstMobs.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								skillInUse.DamageTime = 100;
								skillInUse.DamageType = "mob";
								skillInUse.DamageValue = totaldmg;
								lstSkills.add(skillInUse);
								lstDamage.add(skillInUse);
								rangedAttack = false;
								if(keepnetwork) { MobDeadOnline(mobHP,lstMobs.get(i)); } else { MobDead(lstMobs.get(i)); }
							}
						}
					}
					
					//Long Ranged
					if(rangedAttack) {	
					
						if(skillname.equals("heal")) {
							player.Hp = player.Hp + (player.Wis * 10);
							if(player.Hp > player.HpMax) {player.Hp = player.HpMax; }
							rangedAttack = false; 
							skillEffect = true;
							GameObject skillInUse = new GameObject();
							skillInUse.SkillName = "heal";
							skillInUse.SkillPosX = player.PosX;
							skillInUse.SkillPosY = player.PosY;
							skillInUse.DamagePosX = lstMobs.get(i).MobPosX;
							skillInUse.DamagePosY = lstMobs.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							skillInUse.DamageTime = 100;
							skillInUse.DamageType = "heal";
							skillInUse.DamageValue = 0;
							return;
						}
						
						if(skillname.equals("boost")) { 
							GiveBuff("boost"); 
							rangedAttack = false; 
							skillEffect = true;
							GameObject skillInUse = new GameObject();
							skillInUse.SkillName = "boost";
							skillInUse.SkillPosX = player.PosX;
							skillInUse.SkillPosY = player.PosY;
							skillInUse.DamagePosX = lstMobs.get(i).MobPosX;
							skillInUse.DamagePosY = lstMobs.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							skillInUse.DamageTime = 100;
							skillInUse.DamageType = "heal";
							skillInUse.DamageValue = 0;
							return; 	
						}						
						if(skillname.equals("healthboost")) { GiveBuff("healthboost"); rangedAttack = false; return; }			
						if(skillname.equals("regen")) { 
							GiveBuff("regen"); 
							rangedAttack = false; 
							skillEffect = true;
							GameObject skillInUse = new GameObject();
							skillInUse.SkillName = "regen";
							skillInUse.SkillPosX = player.PosX;
							skillInUse.SkillPosY = player.PosY;
							skillInUse.DamagePosX = lstMobs.get(i).MobPosX;
							skillInUse.DamagePosY = lstMobs.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							skillInUse.DamageTime = 100;
							skillInUse.DamageType = "heal";
							skillInUse.DamageValue = 0;
							return; 	
						}		
						if(skillname.equals("ironshield")) { 
							GiveBuff("ironshield"); 
							rangedAttack = false; 
							skillEffect = true;
							GameObject skillInUse = new GameObject();
							skillInUse.SkillName = "ironshield";
							skillInUse.SkillPosX = player.PosX;
							skillInUse.SkillPosY = player.PosY;
							skillInUse.DamagePosX = lstMobs.get(i).MobPosX;
							skillInUse.DamagePosY = lstMobs.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							skillInUse.DamageTime = 100;
							skillInUse.DamageType = "heal";
							skillInUse.DamageValue = 0;
							return; 	
						}				
						if(skillname.equals("invisibility")) { 
							GiveBuff("invisibility");
							rangedAttack = false; 
							skillEffect = true;
							GameObject skillInUse = new GameObject();
							skillInUse.SkillName = "invisibility";
							skillInUse.SkillPosX = player.PosX;
							skillInUse.SkillPosY = player.PosY;
							skillInUse.DamagePosX = lstMobs.get(i).MobPosX;
							skillInUse.DamagePosY = lstMobs.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							skillInUse.DamageTime = 100;
							skillInUse.DamageType = "heal";
							skillInUse.DamageValue = 0;
							return; 								
						}
						
						
						if((lstMobs.get(i).MobPosX + 5) > (touchSkillX - 5) && (lstMobs.get(i).MobPosX + 5) < (touchSkillX + 5)
						   && (lstMobs.get(i).MobPosY + 5) > (touchSkillY - 10) && (lstMobs.get(i).MobPosY + 5) < (touchSkillY + 10)) {
							player.playerInBattle = "yes";
							lstMobs.get(i).MobTarget = player.Name;
							
							if(skillname.equals("rockbound")) {
								int atkweapon = CheckWeapon();
								int totaldmg = player.Atk + ((player.Wis * 2) + 10);
								int mobHP = lstMobs.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(keepnetwork) { OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { lstMobs.get(i).MobHp = mobHP; }						
								skillEffect = true;
								GameObject skillInUse = new GameObject();
								skillInUse.SkillName = "rockbound";
								skillInUse.SkillPosX = lstMobs.get(i).MobPosX;
								skillInUse.SkillPosY = lstMobs.get(i).MobPosY;
								skillInUse.DamagePosX = lstMobs.get(i).MobPosX;
								skillInUse.DamagePosY = lstMobs.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								skillInUse.DamageTime = 100;
								skillInUse.DamageType = "mob";
								skillInUse.DamageValue = totaldmg;
								lstSkills.add(skillInUse);
								lstDamage.add(skillInUse);
								rangedAttack = false;
								if(keepnetwork) { MobDeadOnline(mobHP,lstMobs.get(i)); } else { MobDead(lstMobs.get(i)); }
								return;
							}
							
							if(skillname.equals("fireball")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Wis * 2) + atkweapon);
								int mobHP = lstMobs.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(keepnetwork) { OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { lstMobs.get(i).MobHp = mobHP; }						
								skillEffect = true;
								GameObject skillInUse = new GameObject();
								skillInUse.SkillName = "fireball";
								skillInUse.SkillPosX = lstMobs.get(i).MobPosX;
								skillInUse.SkillPosY = lstMobs.get(i).MobPosY;
								skillInUse.DamagePosX = lstMobs.get(i).MobPosX;
								skillInUse.DamagePosY = lstMobs.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								skillInUse.DamageTime = 100;
								skillInUse.DamageType = "mob";
								skillInUse.DamageValue = totaldmg;
								lstSkills.add(skillInUse);	
								lstDamage.add(skillInUse);
								rangedAttack = false;
								if(keepnetwork) { MobDeadOnline(mobHP,lstMobs.get(i)); } else { MobDead(lstMobs.get(i)); }
								return;
							}
							
							if(skillname.equals("icecrystal")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Wis * 6) + (player.Dex * 2) + atkweapon);
								int mobHP = lstMobs.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(keepnetwork) { OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { lstMobs.get(i).MobHp = mobHP; }
								skillEffect = true;
								GameObject skillInUse = new GameObject();
								skillInUse.SkillName = "icecrystal";
								skillInUse.SkillPosX = lstMobs.get(i).MobPosX;
								skillInUse.SkillPosY = lstMobs.get(i).MobPosY;
								skillInUse.DamagePosX = lstMobs.get(i).MobPosX;
								skillInUse.DamagePosY = lstMobs.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								skillInUse.DamageTime = 100;
								skillInUse.DamageType = "mob";
								skillInUse.DamageValue = totaldmg;
								lstSkills.add(skillInUse);	
								lstDamage.add(skillInUse);
								rangedAttack = false;
								if(keepnetwork) { MobDeadOnline(mobHP,lstMobs.get(i)); } else { MobDead(lstMobs.get(i)); }
								return;
							}												
							if(skillname.equals("thundercloud")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Wis * 3) + (player.Agi * 2) + atkweapon);
								int mobHP = lstMobs.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(keepnetwork) { OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { lstMobs.get(i).MobHp = mobHP; }						
								skillEffect = true;
								GameObject skillInUse = new GameObject();
								skillInUse.SkillName = "thundercloud";
								skillInUse.SkillPosX = lstMobs.get(i).MobPosX;
								skillInUse.SkillPosY = lstMobs.get(i).MobPosY;
								skillInUse.DamagePosX = lstMobs.get(i).MobPosX;
								skillInUse.DamagePosY = lstMobs.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								skillInUse.DamageTime = 100;
								skillInUse.DamageType = "mob";
								skillInUse.DamageValue = totaldmg;
								lstSkills.add(skillInUse);	
								lstDamage.add(skillInUse);
								rangedAttack = false;
								if(keepnetwork) { MobDeadOnline(mobHP,lstMobs.get(i)); } else { MobDead(lstMobs.get(i)); }
								return;
							}
							
							if(skillname.equals("bulletrain")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Dex * 2) + (player.Agi * 2) + 10);
								int mobHP = lstMobs.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(keepnetwork) { OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { lstMobs.get(i).MobHp = mobHP; }					
								skillEffect = true;
								GameObject skillInUse = new GameObject();
								skillInUse.SkillName = "bulletrain";
								skillInUse.SkillPosX = lstMobs.get(i).MobPosX;
								skillInUse.SkillPosY = lstMobs.get(i).MobPosY;
								skillInUse.DamagePosX = lstMobs.get(i).MobPosX;
								skillInUse.DamagePosY = lstMobs.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								skillInUse.DamageTime = 100;
								skillInUse.DamageType = "mob";
								skillInUse.DamageValue = totaldmg;
								lstSkills.add(skillInUse);	
								lstDamage.add(skillInUse);
								rangedAttack = false;
								if(keepnetwork) { MobDeadOnline(mobHP,lstMobs.get(i)); } else { MobDead(lstMobs.get(i)); }
								return;
							}						
							if(skillname.equals("holyprism")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Wis) + player.Luk + atkweapon);
								int mobHP = lstMobs.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(keepnetwork) { OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { lstMobs.get(i).MobHp = mobHP; }					
								skillEffect = true;
								GameObject skillInUse = new GameObject();
								skillInUse.SkillName = "holyprism";
								skillInUse.SkillPosX = lstMobs.get(i).MobPosX;
								skillInUse.SkillPosY = lstMobs.get(i).MobPosY;
								skillInUse.DamagePosX = lstMobs.get(i).MobPosX;
								skillInUse.DamagePosY = lstMobs.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								skillInUse.DamageTime = 100;
								skillInUse.DamageType = "mob";
								skillInUse.DamageValue = totaldmg;
								lstSkills.add(skillInUse);	
								lstDamage.add(skillInUse);
								rangedAttack = false;
								if(keepnetwork) { MobDeadOnline(mobHP,lstMobs.get(i)); } else { MobDead(lstMobs.get(i)); }
								return;
							}
							if(skillname.equals("mine")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Dex * 2) + 10);
								int mobHP = lstMobs.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(keepnetwork) { OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { lstMobs.get(i).MobHp = mobHP; }					
								skillEffect = true;
								GameObject skillInUse = new GameObject();
								skillInUse.SkillName = "mine";
								skillInUse.SkillPosX = lstMobs.get(i).MobPosX;
								skillInUse.SkillPosY = lstMobs.get(i).MobPosY;
								skillInUse.DamagePosX = lstMobs.get(i).MobPosX;
								skillInUse.DamagePosY = lstMobs.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								skillInUse.DamageTime = 100;
								skillInUse.DamageType = "mob";
								skillInUse.DamageValue = totaldmg;
								lstSkills.add(skillInUse);	
								lstDamage.add(skillInUse);
								rangedAttack = false;
								if(keepnetwork) { MobDeadOnline(mobHP,lstMobs.get(i)); } else { MobDead(lstMobs.get(i)); }
								return;
							}							
						}
					}
				}		
			}
		}
			
		public void GiveBuff(String buffname) {
			boolean setBuff = false;
			String buff = "";
			
			if(player.buffA.equals(buffname)) { return; }
			if(player.buffB.equals(buffname)) { return; }
			if(player.buffC.equals(buffname)) { return; }
			
			if(player.buffA.equals("none") && !setBuff) { player.buffA = buffname; setBuff  = true; buff = "A"; }
			if(player.buffB.equals("none") && !setBuff) { player.buffB = buffname; setBuff  = true; buff = "B"; }
			if(player.buffC.equals("none") && !setBuff) { player.buffC = buffname; setBuff  = true; buff = "C"; }
					
			if(buffname.equals("boost")) {
				player.Str = player.Str + player.Str * 2;
				player.Wis = player.Wis + player.Wis * 2;
				player.Agi = player.Agi + player.Agi * 2;
				player.Luk = player.Luk + player.Luk * 2;
				player.Dex = player.Dex + player.Dex * 2;
				player.HpMax = player.HpMax + 50;
				player.MpMax = player.MpMax + 30;
				
				if(buff.equals("A")) { player.BuffTimeA = 5000; }
				if(buff.equals("B")) { player.BuffTimeB = 5000; }
				if(buff.equals("C")) { player.BuffTimeC = 5000; }
			}
			
			if(buffname.equals("ironshield")) {
				player.Def = player.Def * 2;
				
				if(buff.equals("A")) { player.BuffTimeA = 2500; }
				if(buff.equals("B")) { player.BuffTimeB = 2500; }
				if(buff.equals("C")) { player.BuffTimeC = 2500; }
			}
			
			if(buffname.equals("healthboost")) {
				player.HpMax = player.HpMax * 3;
				
				if(buff.equals("A")) { player.BuffTimeA = 4500; }
				if(buff.equals("B")) { player.BuffTimeB = 4500; }
				if(buff.equals("C")) { player.BuffTimeC = 4500; }
			}
			
			if(buffname.equals("berserk")) {
				player.Str = player.Str * 3;
				
				if(buff.equals("A")) { player.BuffTimeA = 2500; }
				if(buff.equals("B")) { player.BuffTimeB = 2500; }
				if(buff.equals("C")) { player.BuffTimeC = 2500; }
			}
			
			if(buffname.equals("regen")) {
				player.regenTimeMax = player.regenTimeMax - 3000;
				
				if(buff.equals("A")) { player.BuffTimeA = 2000; }
				if(buff.equals("B")) { player.BuffTimeB = 2000; }
				if(buff.equals("C")) { player.BuffTimeC = 2000; }
			}
			
			if(buffname.equals("invisibility")) {
				if(buff.equals("A")) { player.BuffTimeA = 1000; }
				if(buff.equals("B")) { player.BuffTimeB = 1000; }
				if(buff.equals("C")) { player.BuffTimeC = 1000; }
			}
			
			if(buffname.equals("lockshot")) {
				player.Dex = player.Dex * 2;
				player.Luk = player.Luk * 2;
				
				if(buff.equals("A")) { player.BuffTimeA = 3000; }
				if(buff.equals("B")) { player.BuffTimeB = 3000; }
				if(buff.equals("C")) { player.BuffTimeC = 3000; }
			}		
		}
		
		public void RemoveBuffs(String buffname) {
			String buff = "";
			
			if(player.buffA.equals(buffname)) { buff = "A"; }
			if(player.buffB.equals(buffname)) { buff = "B"; }
			if(player.buffC.equals(buffname)) { buff = "C"; }
			
			if(buffname.equals("boost")) {
				player.Str = player.Str + player.Str / 2;
				player.Wis = player.Wis + player.Wis / 2;
				player.Agi = player.Agi + player.Agi / 2;
				player.Luk = player.Luk + player.Luk / 2;
				player.Dex = player.Dex + player.Dex / 2;
				player.HpMax = player.HpMax - 50;
				player.MpMax = player.MpMax - 30;
			}
			
			if(buffname.equals("ironshield")) {
				player.Def = player.Def / 2;
			}
			
			if(buffname.equals("healthboost")) {
				player.HpMax = player.HpMax / 3;
			}
			
			if(buffname.equals("berserk")) {
				player.Str = player.Str / 3;
			}
			
			if(buffname.equals("regen")) {
				player.regenTimeMax = player.regenTimeMax + 3000;
			}
			
			if(buffname.equals("lockshot")) {
				player.Dex = player.Dex / 2;
				player.Luk = player.Luk / 2;
			}
			
			if(buff.equals("A")) { player.buffA = "none"; player.BuffTimeA = 0; }
			if(buff.equals("B")) { player.buffB = "none"; player.BuffTimeB = 0; }
			if(buff.equals("C")) { player.buffC = "none"; player.BuffTimeC = 0; }
			
		}
		
		public void ShowBuffs() {
			
			if(!player.buffA.equals("none")) {
				if(player.buffA.equals("boost")) { spr_master = atlas_cards.createSprite("cardboost"); }
				if(player.buffA.equals("ironshield")) { spr_master = atlas_cards.createSprite("cardironshield"); }
				if(player.buffA.equals("healthboost")) { spr_master = atlas_cards.createSprite("cardhealthboost"); }
				if(player.buffA.equals("berserk")) {  spr_master = atlas_cards.createSprite("cardberserk");}
				if(player.buffA.equals("regen")) { spr_master = atlas_cards.createSprite("cardregen"); }
				if(player.buffA.equals("invisibility")) { spr_master = atlas_cards.createSprite("cardinvisibility"); }
				if(player.buffA.equals("lockshot")) { spr_master = atlas_cards.createSprite("cardlockshot"); }
				spr_master.setSize(3, 8);
				spr_master.setPosition(-50, 30);
				spr_master.draw(game.batch);
				
				player.BuffTimeA = player.BuffTimeA - 1;
				if(player.BuffTimeA <= 0) {
					RemoveBuffs(player.buffA);
				}		
			}
			if(!player.buffB.equals("none")) {
				if(player.buffB.equals("boost")) { spr_master = atlas_cards.createSprite("cardboost"); }
				if(player.buffB.equals("ironshield")) { spr_master = atlas_cards.createSprite("cardironshield"); }
				if(player.buffB.equals("healthboost")) { spr_master = atlas_cards.createSprite("cardhealthboost"); }
				if(player.buffB.equals("berserk")) {  spr_master = atlas_cards.createSprite("cardberserk");}
				if(player.buffB.equals("regen")) { spr_master = atlas_cards.createSprite("cardregen"); }
				if(player.buffB.equals("invisibility")) { spr_master = atlas_cards.createSprite("cardinvisibility"); }
				if(player.buffB.equals("lockshot")) { spr_master = atlas_cards.createSprite("cardlockshot"); }
				spr_master.setSize(3, 8);
				spr_master.setPosition(-45, 30);
				spr_master.draw(game.batch);
				
				player.BuffTimeB = player.BuffTimeB - 1;
				if(player.BuffTimeB <= 0) {
					RemoveBuffs(player.buffB);
				}
			}
			if(!player.buffC.equals("none")) {
				if(player.buffC.equals("boost")) { spr_master = atlas_cards.createSprite("cardboost"); }
				if(player.buffC.equals("ironshield")) { spr_master = atlas_cards.createSprite("cardironshield"); }
				if(player.buffC.equals("healthboost")) { spr_master = atlas_cards.createSprite("cardhealthboost"); }
				if(player.buffC.equals("berserk")) {  spr_master = atlas_cards.createSprite("cardberserk");}
				if(player.buffC.equals("regen")) { spr_master = atlas_cards.createSprite("cardregen"); }
				if(player.buffC.equals("invisibility")) { spr_master = atlas_cards.createSprite("cardinvisibility"); }
				if(player.buffC.equals("lockshot")) { spr_master = atlas_cards.createSprite("cardlockshot"); }
				spr_master.setSize(3, 8);
				spr_master.setPosition(-40, 30);
				spr_master.draw(game.batch);
				
				player.BuffTimeC = player.BuffTimeC - 1;
				if(player.BuffTimeC <= 0) {
					RemoveBuffs(player.buffC);	
				}
			}		
		}
		
		public void ShowSkill() {
			
			if(skillUsed > 0) {
				skillUsed--;
				if(skillUsed < 0) { skillUsed = 0; }
			}
			
			if(lstSkills.size() == 0) {
				return;
			}
			
			for(int i = 0; i < lstSkills.size(); i++) {
				
				int time = lstSkills.get(i).SkillTime;
				lstSkills.get(i).SkillTime = time - 1;
				
				if(lstSkills.get(i).SkillTime >= 80 && lstSkills.get(i).SkillTime <= 100) { 
					if(lstSkills.get(i).SkillName.equals("tripleattack")) { spr_master = atlas_tripleattack.createSprite("tripleattack6"); }
					if(lstSkills.get(i).SkillName.equals("steal")) { spr_master = atlas_steal.createSprite("steal6"); }
					if(lstSkills.get(i).SkillName.equals("soulclash")) { spr_master = atlas_soulclash.createSprite("soulclash6"); }
					if(lstSkills.get(i).SkillName.equals("ravenblade")) { spr_master = atlas_ravenblade.createSprite("ravenblade6"); }
					if(lstSkills.get(i).SkillName.equals("ragebound")) { spr_master = atlas_ragebound.createSprite("ragebound6"); }
					if(lstSkills.get(i).SkillName.equals("thundercloud")) { spr_master = atlas_thundercloud.createSprite("thundercloud6"); }
					if(lstSkills.get(i).SkillName.equals("lockshot")) { spr_master = atlas_lockshot.createSprite("lockshot6"); }
					if(lstSkills.get(i).SkillName.equals("mine")) { spr_master = atlas_mine.createSprite("mine6"); }
					if(lstSkills.get(i).SkillName.equals("overpower")) { spr_master = atlas_overpower.createSprite("overpower6"); }
					if(lstSkills.get(i).SkillName.equals("poisonhit")) { spr_master = atlas_poisonhit.createSprite("poisonhit6"); }
					if(lstSkills.get(i).SkillName.equals("precision")) { spr_master = atlas_precision.createSprite("precision6"); }
					if(lstSkills.get(i).SkillName.equals("protect")) { spr_master = atlas_protect.createSprite("protect6"); }
					if(lstSkills.get(i).SkillName.equals("healthboost")) { spr_master = atlas_healthboost.createSprite("healthboost6"); }
					if(lstSkills.get(i).SkillName.equals("holyprism")) { spr_master = atlas_holyprism.createSprite("holyprism6"); }
					if(lstSkills.get(i).SkillName.equals("icecrystal")) { spr_master = atlas_icecrystal.createSprite("icecrystal6"); }
					if(lstSkills.get(i).SkillName.equals("impound")) { spr_master = atlas_impound.createSprite("impound6"); }
					if(lstSkills.get(i).SkillName.equals("invisibility")) { spr_master = atlas_invisibility.createSprite("invisibility6"); }
					if(lstSkills.get(i).SkillName.equals("ironshield")) { spr_master = atlas_ironshield.createSprite("ironshield6"); }
					if(lstSkills.get(i).SkillName.equals("doublehit")) { spr_master = atlas_doublehit.createSprite("doublehit6"); }
					if(lstSkills.get(i).SkillName.equals("fastshot")) { spr_master = atlas_fastshot.createSprite("fastshot6"); }
					if(lstSkills.get(i).SkillName.equals("fireball")) { spr_master = atlas_fireball.createSprite("fireball6"); }
					if(lstSkills.get(i).SkillName.equals("flysword")) { spr_master = atlas_flysword.createSprite("flysword6"); }
					if(lstSkills.get(i).SkillName.equals("heal")) { spr_master = atlas_heal.createSprite("heal6"); }
					if(lstSkills.get(i).SkillName.equals("boost")) { spr_master = atlas_boost.createSprite("boost6"); }
					if(lstSkills.get(i).SkillName.equals("berserk")) { spr_master = atlas_berserk.createSprite("berserk6"); }
					if(lstSkills.get(i).SkillName.equals("bulletrain")) { spr_master = atlas_bulletrain.createSprite("bulletrain6"); }
					if(lstSkills.get(i).SkillName.equals("dashkick")) { spr_master = atlas_dashkick.createSprite("dashkick6"); }
					if(lstSkills.get(i).SkillName.equals("regen")) { spr_master = atlas_regen.createSprite("regen6"); }
					if(lstSkills.get(i).SkillName.equals("rockbound")) { spr_master = atlas_rockbound.createSprite("rockbound6"); }
					
					spr_master.setPosition(lstSkills.get(i).SkillPosX, lstSkills.get(i).SkillPosY);
					spr_master.setSize(20,20);
					spr_master.draw(game.batch);
				}
				
				if(lstSkills.get(i).SkillTime >= 60 && lstSkills.get(i).SkillTime <= 80) { 
					if(lstSkills.get(i).SkillName.equals("tripleattack")) { spr_master = atlas_tripleattack.createSprite("tripleattack5"); }
					if(lstSkills.get(i).SkillName.equals("steal")) { spr_master = atlas_steal.createSprite("steal5"); }
					if(lstSkills.get(i).SkillName.equals("soulclash")) { spr_master = atlas_soulclash.createSprite("soulclash5"); }
					if(lstSkills.get(i).SkillName.equals("ravenblade")) { spr_master = atlas_ravenblade.createSprite("ravenblade5"); }
					if(lstSkills.get(i).SkillName.equals("ragebound")) { spr_master = atlas_ragebound.createSprite("ragebound5"); }
					if(lstSkills.get(i).SkillName.equals("thundercloud")) { spr_master = atlas_thundercloud.createSprite("thundercloud5"); }
					if(lstSkills.get(i).SkillName.equals("lockshot")) { spr_master = atlas_lockshot.createSprite("lockshot5"); }
					if(lstSkills.get(i).SkillName.equals("mine")) { spr_master = atlas_mine.createSprite("mine5"); }
					if(lstSkills.get(i).SkillName.equals("overpower")) { spr_master = atlas_overpower.createSprite("overpower5"); }
					if(lstSkills.get(i).SkillName.equals("poisonhit")) { spr_master = atlas_poisonhit.createSprite("poisonhit5"); }
					if(lstSkills.get(i).SkillName.equals("precision")) { spr_master = atlas_precision.createSprite("precision5"); }
					if(lstSkills.get(i).SkillName.equals("protect")) { spr_master = atlas_protect.createSprite("protect5"); }
					if(lstSkills.get(i).SkillName.equals("healthboost")) { spr_master = atlas_healthboost.createSprite("healthboost5"); }
					if(lstSkills.get(i).SkillName.equals("holyprism")) { spr_master = atlas_holyprism.createSprite("holyprism5"); }
					if(lstSkills.get(i).SkillName.equals("icecrystal")) { spr_master = atlas_icecrystal.createSprite("icecrystal5"); }
					if(lstSkills.get(i).SkillName.equals("impound")) { spr_master = atlas_impound.createSprite("impound5"); }
					if(lstSkills.get(i).SkillName.equals("invisibility")) { spr_master = atlas_invisibility.createSprite("invisibility5"); }
					if(lstSkills.get(i).SkillName.equals("ironshield")) { spr_master = atlas_ironshield.createSprite("ironshield5"); }
					if(lstSkills.get(i).SkillName.equals("doublehit")) { spr_master = atlas_doublehit.createSprite("doublehit5"); }
					if(lstSkills.get(i).SkillName.equals("fastshot")) { spr_master = atlas_fastshot.createSprite("fastshot5"); }
					if(lstSkills.get(i).SkillName.equals("fireball")) { spr_master = atlas_fireball.createSprite("fireball5"); }
					if(lstSkills.get(i).SkillName.equals("flysword")) { spr_master = atlas_flysword.createSprite("flysword5"); }
					if(lstSkills.get(i).SkillName.equals("heal")) { spr_master = atlas_heal.createSprite("heal5"); }
					if(lstSkills.get(i).SkillName.equals("boost")) { spr_master = atlas_boost.createSprite("boost5"); }
					if(lstSkills.get(i).SkillName.equals("berserk")) { spr_master = atlas_berserk.createSprite("berserk5"); }
					if(lstSkills.get(i).SkillName.equals("bulletrain")) { spr_master = atlas_bulletrain.createSprite("bulletrain5"); }
					if(lstSkills.get(i).SkillName.equals("dashkick")) { spr_master = atlas_dashkick.createSprite("dashkick5"); }
					if(lstSkills.get(i).SkillName.equals("regen")) { spr_master = atlas_regen.createSprite("regen5"); }
					if(lstSkills.get(i).SkillName.equals("rockbound")) { spr_master = atlas_rockbound.createSprite("rockbound5"); }
					
					spr_master.setPosition(lstSkills.get(i).SkillPosX, lstSkills.get(i).SkillPosY);
					spr_master.setSize(20,20);
					spr_master.draw(game.batch);
				}
				
				if(lstSkills.get(i).SkillTime >= 40 && lstSkills.get(i).SkillTime <= 60) { 
					if(lstSkills.get(i).SkillName.equals("tripleattack")) { spr_master = atlas_tripleattack.createSprite("tripleattack4"); }
					if(lstSkills.get(i).SkillName.equals("steal")) { spr_master = atlas_steal.createSprite("steal4"); }
					if(lstSkills.get(i).SkillName.equals("soulclash")) { spr_master = atlas_soulclash.createSprite("soulclash4"); }
					if(lstSkills.get(i).SkillName.equals("ravenblade")) { spr_master = atlas_ravenblade.createSprite("ravenblade4"); }
					if(lstSkills.get(i).SkillName.equals("ragebound")) { spr_master = atlas_ragebound.createSprite("ragebound4"); }
					if(lstSkills.get(i).SkillName.equals("thundercloud")) { spr_master = atlas_thundercloud.createSprite("thundercloud4"); }
					if(lstSkills.get(i).SkillName.equals("lockshot")) { spr_master = atlas_lockshot.createSprite("lockshot4"); }
					if(lstSkills.get(i).SkillName.equals("mine")) { spr_master = atlas_mine.createSprite("mine4"); }
					if(lstSkills.get(i).SkillName.equals("overpower")) { spr_master = atlas_overpower.createSprite("overpower4"); }
					if(lstSkills.get(i).SkillName.equals("poisonhit")) { spr_master = atlas_poisonhit.createSprite("poisonhit4"); }
					if(lstSkills.get(i).SkillName.equals("precision")) { spr_master = atlas_precision.createSprite("precision4"); }
					if(lstSkills.get(i).SkillName.equals("protect")) { spr_master = atlas_protect.createSprite("protect4"); }
					if(lstSkills.get(i).SkillName.equals("healthboost")) { spr_master = atlas_healthboost.createSprite("healthboost4"); }
					if(lstSkills.get(i).SkillName.equals("holyprism")) { spr_master = atlas_holyprism.createSprite("holyprism4"); }
					if(lstSkills.get(i).SkillName.equals("icecrystal")) { spr_master = atlas_icecrystal.createSprite("icecrystal4"); }
					if(lstSkills.get(i).SkillName.equals("impound")) { spr_master = atlas_impound.createSprite("impound4"); }
					if(lstSkills.get(i).SkillName.equals("invisibility")) { spr_master = atlas_invisibility.createSprite("invisibility4"); }
					if(lstSkills.get(i).SkillName.equals("ironshield")) { spr_master = atlas_ironshield.createSprite("ironshield4"); }
					if(lstSkills.get(i).SkillName.equals("doublehit")) { spr_master = atlas_doublehit.createSprite("doublehit4"); }
					if(lstSkills.get(i).SkillName.equals("fastshot")) { spr_master = atlas_fastshot.createSprite("fastshot4"); }
					if(lstSkills.get(i).SkillName.equals("fireball")) { spr_master = atlas_fireball.createSprite("fireball4"); }
					if(lstSkills.get(i).SkillName.equals("flysword")) { spr_master = atlas_flysword.createSprite("flysword4"); }
					if(lstSkills.get(i).SkillName.equals("heal")) { spr_master = atlas_heal.createSprite("heal4"); }
					if(lstSkills.get(i).SkillName.equals("boost")) { spr_master = atlas_boost.createSprite("boost4"); }
					if(lstSkills.get(i).SkillName.equals("berserk")) { spr_master = atlas_berserk.createSprite("berserk4"); }
					if(lstSkills.get(i).SkillName.equals("bulletrain")) { spr_master = atlas_bulletrain.createSprite("bulletrain4"); }
					if(lstSkills.get(i).SkillName.equals("dashkick")) { spr_master = atlas_dashkick.createSprite("dashkick4"); }
					if(lstSkills.get(i).SkillName.equals("regen")) { spr_master = atlas_regen.createSprite("regen4"); }
					if(lstSkills.get(i).SkillName.equals("rockbound")) { spr_master = atlas_rockbound.createSprite("rockbound4"); }
					
					spr_master.setPosition(lstSkills.get(i).SkillPosX, lstSkills.get(i).SkillPosY);
					spr_master.setSize(20,20);
					spr_master.draw(game.batch);
				}
				
				if(lstSkills.get(i).SkillTime >= 20 && lstSkills.get(i).SkillTime <= 40) { 
					if(lstSkills.get(i).SkillName.equals("tripleattack")) { spr_master = atlas_tripleattack.createSprite("tripleattack3"); }
					if(lstSkills.get(i).SkillName.equals("steal")) { spr_master = atlas_steal.createSprite("steal3"); }
					if(lstSkills.get(i).SkillName.equals("soulclash")) { spr_master = atlas_soulclash.createSprite("soulclash3"); }
					if(lstSkills.get(i).SkillName.equals("ravenblade")) { spr_master = atlas_ravenblade.createSprite("ravenblade3"); }
					if(lstSkills.get(i).SkillName.equals("ragebound")) { spr_master = atlas_ragebound.createSprite("ragebound3"); }
					if(lstSkills.get(i).SkillName.equals("thundercloud")) { spr_master = atlas_thundercloud.createSprite("thundercloud3"); }
					if(lstSkills.get(i).SkillName.equals("lockshot")) { spr_master = atlas_lockshot.createSprite("lockshot3"); }
					if(lstSkills.get(i).SkillName.equals("mine")) { spr_master = atlas_mine.createSprite("mine3"); }
					if(lstSkills.get(i).SkillName.equals("overpower")) { spr_master = atlas_overpower.createSprite("overpower3"); }
					if(lstSkills.get(i).SkillName.equals("poisonhit")) { spr_master = atlas_poisonhit.createSprite("poisonhit3"); }
					if(lstSkills.get(i).SkillName.equals("precision")) { spr_master = atlas_precision.createSprite("precision3"); }
					if(lstSkills.get(i).SkillName.equals("protect")) { spr_master = atlas_protect.createSprite("protect3"); }
					if(lstSkills.get(i).SkillName.equals("healthboost")) { spr_master = atlas_healthboost.createSprite("healthboost3"); }
					if(lstSkills.get(i).SkillName.equals("holyprism")) { spr_master = atlas_holyprism.createSprite("holyprism3"); }
					if(lstSkills.get(i).SkillName.equals("icecrystal")) { spr_master = atlas_icecrystal.createSprite("icecrystal3"); }
					if(lstSkills.get(i).SkillName.equals("impound")) { spr_master = atlas_impound.createSprite("impound3"); }
					if(lstSkills.get(i).SkillName.equals("invisibility")) { spr_master = atlas_invisibility.createSprite("invisibility3"); }
					if(lstSkills.get(i).SkillName.equals("ironshield")) { spr_master = atlas_ironshield.createSprite("ironshield3"); }
					if(lstSkills.get(i).SkillName.equals("doublehit")) { spr_master = atlas_doublehit.createSprite("doublehit3"); }
					if(lstSkills.get(i).SkillName.equals("fastshot")) { spr_master = atlas_fastshot.createSprite("fastshot3"); }
					if(lstSkills.get(i).SkillName.equals("fireball")) { spr_master = atlas_fireball.createSprite("fireball3"); }
					if(lstSkills.get(i).SkillName.equals("flysword")) { spr_master = atlas_flysword.createSprite("flysword3"); }
					if(lstSkills.get(i).SkillName.equals("heal")) { spr_master = atlas_heal.createSprite("heal3"); }
					if(lstSkills.get(i).SkillName.equals("boost")) { spr_master = atlas_boost.createSprite("boost3"); }
					if(lstSkills.get(i).SkillName.equals("berserk")) { spr_master = atlas_berserk.createSprite("berserk3"); }
					if(lstSkills.get(i).SkillName.equals("bulletrain")) { spr_master = atlas_bulletrain.createSprite("bulletrain3"); }
					if(lstSkills.get(i).SkillName.equals("dashkick")) { spr_master = atlas_dashkick.createSprite("dashkick3"); }
					if(lstSkills.get(i).SkillName.equals("regen")) { spr_master = atlas_regen.createSprite("regen3"); }
					if(lstSkills.get(i).SkillName.equals("rockbound")) { spr_master = atlas_rockbound.createSprite("rockbound3"); }
					
					spr_master.setPosition(lstSkills.get(i).SkillPosX, lstSkills.get(i).SkillPosY);
					spr_master.setSize(20,20);
					spr_master.draw(game.batch);
				}
				
				if(lstSkills.get(i).SkillTime >= 10 && lstSkills.get(i).SkillTime <= 20) { 
					if(lstSkills.get(i).SkillName.equals("tripleattack")) { spr_master = atlas_tripleattack.createSprite("tripleattack2"); }
					if(lstSkills.get(i).SkillName.equals("steal")) { spr_master = atlas_steal.createSprite("steal2"); }
					if(lstSkills.get(i).SkillName.equals("soulclash")) { spr_master = atlas_soulclash.createSprite("soulclash2"); }
					if(lstSkills.get(i).SkillName.equals("ravenblade")) { spr_master = atlas_ravenblade.createSprite("ravenblade2"); }
					if(lstSkills.get(i).SkillName.equals("ragebound")) { spr_master = atlas_ragebound.createSprite("ragebound2"); }
					if(lstSkills.get(i).SkillName.equals("thundercloud")) { spr_master = atlas_thundercloud.createSprite("thundercloud2"); }
					if(lstSkills.get(i).SkillName.equals("lockshot")) { spr_master = atlas_lockshot.createSprite("lockshot2"); }
					if(lstSkills.get(i).SkillName.equals("mine")) { spr_master = atlas_mine.createSprite("mine2"); }
					if(lstSkills.get(i).SkillName.equals("overpower")) { spr_master = atlas_overpower.createSprite("overpower2"); }
					if(lstSkills.get(i).SkillName.equals("poisonhit")) { spr_master = atlas_poisonhit.createSprite("poisonhit2"); }
					if(lstSkills.get(i).SkillName.equals("precision")) { spr_master = atlas_precision.createSprite("precision2"); }
					if(lstSkills.get(i).SkillName.equals("protect")) { spr_master = atlas_protect.createSprite("protect2"); }
					if(lstSkills.get(i).SkillName.equals("healthboost")) { spr_master = atlas_healthboost.createSprite("healthboost2"); }
					if(lstSkills.get(i).SkillName.equals("holyprism")) { spr_master = atlas_holyprism.createSprite("holyprism2"); }
					if(lstSkills.get(i).SkillName.equals("icecrystal")) { spr_master = atlas_icecrystal.createSprite("icecrystal2"); }
					if(lstSkills.get(i).SkillName.equals("impound")) { spr_master = atlas_impound.createSprite("impound2"); }
					if(lstSkills.get(i).SkillName.equals("invisibility")) { spr_master = atlas_invisibility.createSprite("invisibility2"); }
					if(lstSkills.get(i).SkillName.equals("ironshield")) { spr_master = atlas_ironshield.createSprite("ironshield2"); }
					if(lstSkills.get(i).SkillName.equals("doublehit")) { spr_master = atlas_doublehit.createSprite("doublehit2"); }
					if(lstSkills.get(i).SkillName.equals("fastshot")) { spr_master = atlas_fastshot.createSprite("fastshot2"); }
					if(lstSkills.get(i).SkillName.equals("fireball")) { spr_master = atlas_fireball.createSprite("fireball2"); }
					if(lstSkills.get(i).SkillName.equals("flysword")) { spr_master = atlas_flysword.createSprite("flysword2"); }
					if(lstSkills.get(i).SkillName.equals("heal")) { spr_master = atlas_heal.createSprite("heal2"); }
					if(lstSkills.get(i).SkillName.equals("boost")) { spr_master = atlas_boost.createSprite("boost2"); }
					if(lstSkills.get(i).SkillName.equals("berserk")) { spr_master = atlas_berserk.createSprite("berserk2"); }
					if(lstSkills.get(i).SkillName.equals("bulletrain")) { spr_master = atlas_bulletrain.createSprite("bulletrain2"); }
					if(lstSkills.get(i).SkillName.equals("dashkick")) { spr_master = atlas_dashkick.createSprite("dashkick2"); }
					if(lstSkills.get(i).SkillName.equals("regen")) { spr_master = atlas_regen.createSprite("regen2"); }
					if(lstSkills.get(i).SkillName.equals("rockbound")) { spr_master = atlas_rockbound.createSprite("rockbound2"); }
					
					spr_master.setPosition(lstSkills.get(i).SkillPosX, lstSkills.get(i).SkillPosY);
					spr_master.setSize(20,20);
					spr_master.draw(game.batch);
				}
				
				if(lstSkills.get(i).SkillTime >= 0 && lstSkills.get(i).SkillTime <= 10) { 
					if(lstSkills.get(i).SkillName.equals("tripleattack")) { spr_master = atlas_tripleattack.createSprite("tripleattack1"); }
					if(lstSkills.get(i).SkillName.equals("steal")) { spr_master = atlas_steal.createSprite("steal1"); }
					if(lstSkills.get(i).SkillName.equals("soulclash")) { spr_master = atlas_soulclash.createSprite("soulclash1"); }
					if(lstSkills.get(i).SkillName.equals("ravenblade")) { spr_master = atlas_ravenblade.createSprite("ravenblade1"); }
					if(lstSkills.get(i).SkillName.equals("ragebound")) { spr_master = atlas_ragebound.createSprite("ragebound1"); }
					if(lstSkills.get(i).SkillName.equals("thundercloud")) { spr_master = atlas_thundercloud.createSprite("thundercloud1"); }
					if(lstSkills.get(i).SkillName.equals("lockshot")) { spr_master = atlas_lockshot.createSprite("lockshot1"); }
					if(lstSkills.get(i).SkillName.equals("mine")) { spr_master = atlas_mine.createSprite("mine1"); }
					if(lstSkills.get(i).SkillName.equals("overpower")) { spr_master = atlas_overpower.createSprite("overpower1"); }
					if(lstSkills.get(i).SkillName.equals("poisonhit")) { spr_master = atlas_poisonhit.createSprite("poisonhit1"); }
					if(lstSkills.get(i).SkillName.equals("precision")) { spr_master = atlas_precision.createSprite("precision1"); }
					if(lstSkills.get(i).SkillName.equals("protect")) { spr_master = atlas_protect.createSprite("protect1"); }
					if(lstSkills.get(i).SkillName.equals("healthboost")) { spr_master = atlas_healthboost.createSprite("healthboost1"); }
					if(lstSkills.get(i).SkillName.equals("holyprism")) { spr_master = atlas_holyprism.createSprite("holyprism1"); }
					if(lstSkills.get(i).SkillName.equals("icecrystal")) { spr_master = atlas_icecrystal.createSprite("icecrystal1"); }
					if(lstSkills.get(i).SkillName.equals("impound")) { spr_master = atlas_impound.createSprite("impound1"); }
					if(lstSkills.get(i).SkillName.equals("invisibility")) { spr_master = atlas_invisibility.createSprite("invisibility1"); }
					if(lstSkills.get(i).SkillName.equals("ironshield")) { spr_master = atlas_ironshield.createSprite("ironshield1"); }
					if(lstSkills.get(i).SkillName.equals("doublehit")) { spr_master = atlas_doublehit.createSprite("doublehit1"); }
					if(lstSkills.get(i).SkillName.equals("fastshot")) { spr_master = atlas_fastshot.createSprite("fastshot1"); }
					if(lstSkills.get(i).SkillName.equals("fireball")) { spr_master = atlas_fireball.createSprite("fireball1"); }
					if(lstSkills.get(i).SkillName.equals("flysword")) { spr_master = atlas_flysword.createSprite("flysword1"); }
					if(lstSkills.get(i).SkillName.equals("heal")) { spr_master = atlas_heal.createSprite("heal1"); }
					if(lstSkills.get(i).SkillName.equals("boost")) { spr_master = atlas_boost.createSprite("boost1"); }
					if(lstSkills.get(i).SkillName.equals("berserk")) { spr_master = atlas_berserk.createSprite("berserk1"); }
					if(lstSkills.get(i).SkillName.equals("bulletrain")) { spr_master = atlas_bulletrain.createSprite("bulletrain1"); }
					if(lstSkills.get(i).SkillName.equals("dashkick")) { spr_master = atlas_dashkick.createSprite("dashkick1"); }
					if(lstSkills.get(i).SkillName.equals("regen")) { spr_master = atlas_regen.createSprite("regen1"); }
					if(lstSkills.get(i).SkillName.equals("rockbound")) { spr_master = atlas_rockbound.createSprite("rockbound1"); }
					
					spr_master.setPosition(lstSkills.get(i).SkillPosX, lstSkills.get(i).SkillPosY);
					spr_master.setSize(20,20);
					spr_master.draw(game.batch);
				}
				
				if(lstSkills.get(i).SkillTime < 0) { 
					lstSkills.remove(lstSkills.get(i));
				}
			}
		}
		
		
		public void ShowDamage() {
			
			if(lstDamage.size() == 0) {
				return;
			}
			
			for(int i = 0; i < lstDamage.size(); i++) {
				lstDamage.get(i).DamagePosY = lstDamage.get(i).DamagePosY + 0.4f;
				lstDamage.get(i).DamageTime = lstDamage.get(i).DamageTime - 1;
								
				font_master.getData().setScale(0.10f,0.15f);
				font_master.setUseIntegerPositions(false);
				if(lstDamage.get(i).DamageType.equals("mob")) { font_master.setColor(Color.YELLOW); }
				if(lstDamage.get(i).DamageType.equals("player")) { font_master.setColor(Color.RED); }
				if(lstDamage.get(i).DamageType.equals("heal")) { font_master.setColor(Color.GREEN); }
				
				font_master.draw(game.batch, String.valueOf(lstDamage.get(i).DamageValue), lstDamage.get(i).DamagePosX, lstDamage.get(i).DamagePosY);
				
				if(lstDamage.get(i).DamageTime < 0) {
					lstDamage.remove(lstDamage.get(i));
				}
			}
		}
		
			
		//SHOP
		public void CheckBuyItemStreetsA(int num) {
			if(shop.equals("shopStreetsA1")) {
				if(num == 1) {  
					if(player.Money >= 2) {  AddItemBag("basicknife"); player.Money = player.Money - 2; if(player.Money < 0) { player.Money = 0; }					
					}else {SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 2) {  
					if(player.Money >= 10) { AddItemBag("doubleedgeknife"); player.Money = player.Money - 10;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 3) {  
					if(player.Money >= 25) { AddItemBag("slime_hat"); player.Money = player.Money - 25;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 4) {  
					if(player.Money >= 25) { AddItemBag("magician_hat"); player.Money = player.Money - 25;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 5) {  
					if(player.Money >= 25) { AddItemBag("butterfly_hat"); player.Money = player.Money - 25;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 6) {  
					if(player.Money >= 20) { AddItemBag("mpcan"); player.Money = player.Money - 20;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 7) {  
					if(player.Money >= 2) { AddItemBag("hpcan"); player.Money = player.Money - 2;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 8) {  
					if(player.Money >= 5) { AddItemBag("stcan"); player.Money = player.Money - 5; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }}
				
				if(num == 9) {  
					if(player.Money >= 25) { AddItemBag("bear_hat"); player.Money = player.Money - 25; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }				
				}
				if(num == 10) {  	
					if(player.Money >= 25) { AddItemBag("capoult_hat"); player.Money = player.Money - 25; if(player.Money < 0) { player.Money = 0; }}
				    else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }			
				}
				if(num == 11) {  
					if(player.Money >= 100) { AddItemBag("garrafamagica"); player.Money = player.Money - 100; if(player.Money < 0) { player.Money = 0; }}
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}					
				}
				if(num == 12) {  
					if(player.Money >= 50) { AddItemBag("garrafadrink"); player.Money = player.Money - 50; if(player.Money < 0) { player.Money = 0; }}
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}					
				}
				if(num == 13) {  
					if(player.Money >= 25) { AddItemBag("garrafasuco"); player.Money = player.Money - 25; if(player.Money < 0) { player.Money = 0; }}
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }					
				}
				if(num == 14) {  
					if(player.Money >= 25) { AddItemBag("pirate_hat"); player.Money = player.Money - 25; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}			
				}
				if(num == 15) {  
					if(player.Money >= 25) { AddItemBag("santa_hat"); player.Money = player.Money - 25; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }					
				}
				if(num == 16) {  
					if(player.Money >= 25) { AddItemBag("basicset_m"); player.Money = player.Money - 25; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }		
				}
				if(num == 17) {  
					if(player.Money >= 25) { AddItemBag("basicset_f"); player.Money = player.Money - 25; if(player.Money < 0) { player.Money = 0; }}
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}			
				}		
			}
			
			if(shop.equals("shopStreetsA2")) {
				if(num == 1) {  
					if(player.Money >= 100) {  AddItemBag("woodsword"); player.Money = player.Money - 100; if(player.Money < 0) { player.Money = 0; }					
					}else {SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 2) {  
					if(player.Money >= 100) { AddItemBag("basicpistol"); player.Money = player.Money - 100;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 3) {  
					if(player.Money >= 100) { AddItemBag("basicdagger"); player.Money = player.Money - 100;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 4) {  
					if(player.Money >= 100) { AddItemBag("stickrod"); player.Money = player.Money - 100;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 5) {  
					if(player.Money >= 100) { AddItemBag("basicaxe"); player.Money = player.Money - 100;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 6) {  
					if(player.Money >= 20) { AddItemBag("mpcan"); player.Money = player.Money - 20;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 7) {  
					if(player.Money >= 2) { AddItemBag("hpcan"); player.Money = player.Money - 2;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 8) {  
					if(player.Money >= 5) { AddItemBag("stcan"); player.Money = player.Money - 5; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }}
				
				if(num == 9) {  
					if(player.Money >= 15) { AddItemBag("fries"); player.Money = player.Money - 15; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }				
				}
				if(num == 10) {  	
					if(player.Money >= 25) { AddItemBag("bunny_hat"); player.Money = player.Money - 25; if(player.Money < 0) { player.Money = 0; }}
				    else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }			
				}
				if(num == 11) {  
					if(player.Money >= 100) { AddItemBag("garrafamagica"); player.Money = player.Money - 100; if(player.Money < 0) { player.Money = 0; }}
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}					
				}
				if(num == 12) {  
					if(player.Money >= 50) { AddItemBag("garrafadrink"); player.Money = player.Money - 50; if(player.Money < 0) { player.Money = 0; }}
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}					
				}
				if(num == 13) {  
					if(player.Money >= 25) { AddItemBag("garrafasuco"); player.Money = player.Money - 25; if(player.Money < 0) { player.Money = 0; }}
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }					
				}
				if(num == 14) {  
					if(player.Money >= 40) { AddItemBag("pizza"); player.Money = player.Money - 40; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}			
				}
				if(num == 15) {  
					if(player.Money >= 25) { AddItemBag("clock_hat"); player.Money = player.Money - 25; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }					
				}
				if(num == 16) {  
					if(player.Money >= 30) { AddItemBag("blackset_m"); player.Money = player.Money - 30; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }		
				}
				if(num == 17) {  
					if(player.Money >= 30) { AddItemBag("blackset_f"); player.Money = player.Money - 30; if(player.Money < 0) { player.Money = 0; }}
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}			
				}		
			}
		}
		
		public void CheckBuyItemStreetsB(int num) {
			if(shop.equals("shopStreetsB3")) {
				if(num == 1) {  
					if(player.Money >= 300) {  AddItemBag("edgesword"); player.Money = player.Money - 300; if(player.Money < 0) { player.Money = 0; }					
					}else {SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 2) {  
					if(player.Money >= 300) { AddItemBag("machinepistol"); player.Money = player.Money - 300;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 3) {  
					if(player.Money >= 300) { AddItemBag("edgedagger"); player.Money = player.Money - 300;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 4) {  
					if(player.Money >= 300) { AddItemBag("lightwieldrod"); player.Money = player.Money - 300;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 5) {  
					if(player.Money >= 300) { AddItemBag("pickaxe"); player.Money = player.Money - 300;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 6) {  
					if(player.Money >= 20) { AddItemBag("mpcan"); player.Money = player.Money - 20;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 7) {  
					if(player.Money >= 2) { AddItemBag("hpcan"); player.Money = player.Money - 2;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 8) {  
					if(player.Money >= 5) { AddItemBag("stcan"); player.Money = player.Money - 5; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }}
				
				if(num == 9) {  
					if(player.Money >= 95) { AddItemBag("hamburger"); player.Money = player.Money - 95; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }				
				}
				if(num == 10) {  	
					if(player.Money >= 130) { AddItemBag("cake"); player.Money = player.Money - 130; if(player.Money < 0) { player.Money = 0; }}
				    else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }			
				}
				if(num == 11) {  
					if(player.Money >= 100) { AddItemBag("garrafamagica"); player.Money = player.Money - 100; if(player.Money < 0) { player.Money = 0; }}
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}					
				}
				if(num == 12) {  
					if(player.Money >= 50) { AddItemBag("garrafadrink"); player.Money = player.Money - 50; if(player.Money < 0) { player.Money = 0; }}
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}					
				}
				if(num == 13) {  
					if(player.Money >= 25) { AddItemBag("garrafasuco"); player.Money = player.Money - 25; if(player.Money < 0) { player.Money = 0; }}
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }					
				}
				if(num == 14) {  
					if(player.Money >= 25) { AddItemBag("headphone_hat"); player.Money = player.Money - 25; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}			
				}
				if(num == 15) {  
					if(player.Money >= 25) { AddItemBag("sunglass_hat"); player.Money = player.Money - 25; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }					
				}
				if(num == 16) {  
					if(player.Money >= 25) { AddItemBag("rougeset_m"); player.Money = player.Money - 25; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }		
				}
				if(num == 17) {  
					if(player.Money >= 25) { AddItemBag("rougeset_f"); player.Money = player.Money - 25; if(player.Money < 0) { player.Money = 0; }}
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}			
				}		
			}
			
			if(shop.equals("shopStreetsB4")) {
				if(num == 1) {  
					if(player.Money >= 100) {  AddItemBag("woodsword"); player.Money = player.Money - 100; if(player.Money < 0) { player.Money = 0; }					
					}else {SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 2) {  
					if(player.Money >= 100) { AddItemBag("basicpistol"); player.Money = player.Money - 100;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 3) {  
					if(player.Money >= 100) { AddItemBag("basicdagger"); player.Money = player.Money - 100;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 4) {  
					if(player.Money >= 100) { AddItemBag("stickrod"); player.Money = player.Money - 100;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 5) {  
					if(player.Money >= 100) { AddItemBag("basicaxe"); player.Money = player.Money - 100;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 6) {  
					if(player.Money >= 20) { AddItemBag("mpcan"); player.Money = player.Money - 20;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 7) {  
					if(player.Money >= 2) { AddItemBag("hpcan"); player.Money = player.Money - 2;if(player.Money < 0) { player.Money = 0; }
					}else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}}
				
				if(num == 8) {  
					if(player.Money >= 5) { AddItemBag("stcan"); player.Money = player.Money - 5; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }}
				
				if(num == 9) {  
					if(player.Money >= 15) { AddItemBag("fries"); player.Money = player.Money - 15; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }				
				}
				if(num == 10) {  	
					if(player.Money >= 25) { AddItemBag("bunny_hat"); player.Money = player.Money - 25; if(player.Money < 0) { player.Money = 0; }}
				    else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }			
				}
				if(num == 11) {  
					if(player.Money >= 100) { AddItemBag("garrafamagica"); player.Money = player.Money - 100; if(player.Money < 0) { player.Money = 0; }}
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}					
				}
				if(num == 12) {  
					if(player.Money >= 50) { AddItemBag("garrafadrink"); player.Money = player.Money - 50; if(player.Money < 0) { player.Money = 0; }}
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}					
				}
				if(num == 13) {  
					if(player.Money >= 25) { AddItemBag("garrafasuco"); player.Money = player.Money - 25; if(player.Money < 0) { player.Money = 0; }}
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }					
				}
				if(num == 14) {  
					if(player.Money >= 40) { AddItemBag("pizza"); player.Money = player.Money - 40; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}			
				}
				if(num == 15) {  
					if(player.Money >= 25) { AddItemBag("clock_hat"); player.Money = player.Money - 25; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }					
				}
				if(num == 16) {  
					if(player.Money >= 30) { AddItemBag("blackset_m"); player.Money = player.Money - 30; if(player.Money < 0) { player.Money = 0; } }
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100; }		
				}
				if(num == 17) {  
					if(player.Money >= 30) { AddItemBag("blackset_f"); player.Money = player.Money - 30; if(player.Money < 0) { player.Money = 0; }}
					else { SysMsg = "Gold insuficiente"; SysMsgCount = 100;}			
				}		
			}
		}
		
		public String GetDescription(int itemNum) {
			String[] lstItem = player.Itens.split("-");
			String[] itemSplit = lstItem[itemNum].split("#");
			String item = "";
						
			item = itemSplit[0].replace("[", "");
			
			//Sets
			if(item.equals("basicset_M")) { return "Roupa padrao escolar"; }
			if(item.equals("basicset_F")) { return "Roupa padrao escolar"; }
			
			//Armas
			if(item.equals("basicknife")) { return "Faca basica"; }
			
			//Food Stats
			if(item.equals("hpcan")) { return "Restaura 5 de HP"; }
			if(item.equals("garrafadrink")) { return "Restaura 35 de HP"; }
			if(item.equals("mpcan")) { return "Restaura 25 de MP"; }
			if(item.equals("garrafamagica")) { return "Restaura 50 de MP"; }
			if(item.equals("stcan")) { return "Restaura 5 de Energia"; }
			if(item.equals("stjar")) { return "Restaura 10 de Energia"; }
			if(item.equals("sushi")) { return "Restaura 8 de Energia e concede 1 de for temporario"; }
			if(item.equals("chipz")) { return "Restaura 4 de Energia e concede 1 de res temporario"; }
			if(item.equals("cheesebread")) { return "Restaura 35 de Energia, 65 de HP e 25 de MP"; }
			if(item.equals("cake")) { return "Restaura 8 de Energia, 4 de HPe 2 de MP"; }
			if(item.equals("egg")) { return "Restaura 80 de HP"; }
			if(item.equals("fries")) { return "Restaura 90 de MP"; }
			if(item.equals("hamburguer")) { return "Restaura 20 de Energia"; }
			if(item.equals("hotdog")) { return "Restaura 12 de Energia e 25 de HP"; }
			if(item.equals("icecreamorange")) { return "Restaura 2 de Energia, 2 de HP e 2 de MP"; }
			if(item.equals("icecreampurple")) { return "Restaura 8 de Energia, 4 de HPe 2 de MP"; }
			if(item.equals("meatball")) { return "Restaura 50 de Energia, 35 de HP e 90 de MP"; }
			if(item.equals("pizza")) { return "Restaura 10 de Energia, 7 de HP e 4 de MP"; }
			if(item.equals("spaghetti")) { return "Restaura 80 de Energia, 150 de HP e 100 de MP"; }
			if(item.equals("steak")) { return "Restaura 100 de Energia, 300 de HP e 140 de MP"; }
			
			//Crystals
			if(item.equals("blue_crystal_intextra_1")) { return "Cristal Azul 1, Aumenta 2 em Inteligencia e 20 de MP Max"; }
			if(item.equals("blue_crystal_intextra_2")) { return "Cristal Azul 2, Aumenta 5 em Inteligencia e 50 de MP Max"; }
			if(item.equals("blue_crystal_intextra_3")) { return "Cristal Azul 3, Aumenta 10 em Inteligencia e 100 de MP Max"; }
			
			if(item.equals("green_crystal_lukextra_1")) { return "Cristal Verde 1, Aumenta 2 em Sorte"; }
			if(item.equals("green_crystal_lukextra_2")) { return "Cristal Verde 2, Aumenta 5 em Sorte"; }
			if(item.equals("green_crystal_lukextra_3")) { return "Cristal Verde 3, Aumenta 10 em Sorte"; }
			
			if(item.equals("purple_crystal_vitextra_1")) { return "Cristal Roxo 1, Aumenta 2 em Vitalidade e 20 de HP Max"; }
			if(item.equals("purple_crystal_vitextra_2")) { return "Cristal Roxo 2, Aumenta 5 em Vitalidade e 50 de HP Max"; }
			if(item.equals("purple_crystal_vitextra_3")) { return "Cristal Roxo 3, Aumenta 10 em Vitalidade e 100 de HP Max"; }
			
			if(item.equals("yellow_crystal_agiextra_1")) { return "Cristal Amarelo 1, Aumenta 2 em Agilidade, diminui tempo de ataque"; }
			if(item.equals("yellow_crystal_agiextra_2")) { return "Cristal Amarelo 2, Aumenta 5 em Agilidade, diminui tempo de ataque"; }
			if(item.equals("yellow_crystal_agiextra_3")) { return "Cristal Amarelo 3, Aumenta 10 em Agilidade, diminui tempo de ataque"; }
			
			if(item.equals("red_crystal_strextra_1")) { return "Cristal Vermelho 1, Aumenta 2 de Forca"; }
			if(item.equals("red_crystal_strextra_2")) { return "Cristal Vermelho 2, Aumenta 5 de Forca"; }
			if(item.equals("red_crystal_strextra_3")) { return "Cristal Vermelho 3, Aumenta 10 de Forca"; }
			
			if(item.equals("grey_crystal_dexextra_1")) { return "Cristal Cinza 1, Aumenta 2 de Destreza"; }
			if(item.equals("grey_crystal_dexextra_2")) { return "Cristal Cinza 2, Aumenta 5 de Destreza"; }
			if(item.equals("grey_crystal_dexextra_3")) { return "Cristal Cinza 3, Aumenta 10 de Destreza"; }
			
			if(item.equals("orange_crystal_resextra_1")) { return "Cristal Laranja 1, Tempo Regen reduzido em 300, aumentado def, aumentado energia"; }
			if(item.equals("orange_crystal_resextra_2")) { return "Cristal Laranja 2, Tempo Regen reduzido em 50, aumentado def+, aumentado energia"; }
			if(item.equals("orange_crystal_resextra_3")) { return "Cristal Laranja 3, Tempo Regen reduzido em 700, aumentado def++, aumentado energia"; }
					
			//Crystals
			if(item.equals("blue_orb")) { return "Trocavel por cristal azul com 50 unidades"; }
			if(item.equals("gray_orb")) { return "Trocavel por cristal cinza com 50 unidades"; }
			if(item.equals("green_orb")) { return "Trocavel por cristal verde com 50 unidades"; }
			if(item.equals("orange_orb")) { return "Trocavel por cristal laranja com 50 unidades"; }
			if(item.equals("pink_orb")) { return "Trocavel por cristal rosa com 50 unidades"; }
			if(item.equals("purple_orb")) { return "Trocavel por cristal roxo com 50 unidades"; }
			if(item.equals("red_orb")) { return "Trocavel por cristal vermelho com 50 unidades"; }
			if(item.equals("yellow_orb")) { return "Trocavel por cristal amarelo com 50 unidades"; }
			
			return "";
		}
		
		public void DiscartItem(int itemNum) {
			String[] lstItem = player.Itens.split("-");
			String listaItemFinal = "";
			String[] itemSplit;
			String item;
			String itemName;
			int qtd;
			int money = 0;
			
			//Get Item
			item = lstItem[itemNum];
			if(item.equals("[NONE]")) { return; }
				
			//Check quantity
			itemSplit = item.split("#");
			itemName = itemSplit[0].replace("[", "");
			qtd = Integer.parseInt(itemSplit[1].replace("]", ""));
			
			//Give Money
			money = player.Money;
			if(money > 5000) { return; }
			int moneygave = randnumber.nextInt(5);
			while(moneygave < 2) { moneygave = randnumber.nextInt(5); }
			money = money + (moneygave * 2);
			player.Money = money;
			
			//Clean Item placebox
			lstItem[itemNum] = "[NONE]";
			listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
			listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
			player.Itens = listaItemFinal;	
		}
		
		public void AddItemBag(String itemName) {
			String[] lstItem = player.Itens.split("-");
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
					player.Itens = listaItemFinal;
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
					player.Itens = listaItemFinal;
				}
			}
		}
		
		
		
		
		//Hotkey
		public void HotKeyItem(int itemNum) {
			String[] lstItem = player.Itens.split("-");
			String[] itemSplit = lstItem[itemNum].split("#");
			String item = "";
			hotketcountitem = itemNum;
						
			item = itemSplit[0].replace("[", "");
			
			if(item.equals("hpcan")) { player.hotkey1 = item; return; }
			if(item.equals("garrafadrink")) { player.hotkey1 = item; return; }
			if(item.equals("mpcan")) { player.hotkey1 = item; return; }
			if(item.equals("garrafamagica")) { player.hotkey1 = item; return; }
			if(item.equals("stcan")) { player.hotkey1 = item; return; }
			if(item.equals("garrafasuco")) { player.hotkey1 = item; return; }
		}
		
		
		//[PET]
		public void AddRemovePet() {}
		public void PetFood() {}
		public void PetTraining() {}
		public void PetClean() {}
		
		
		//[NPCS / Quests]
		public void ShowNPCS() {
			
			if(player.Map.equals("StreetsA")) {
				if(npcframe >= 1  && npcframe <= 15) { spr_master = atlas_npcs1.createSprite("crystalguy1");  }
				if(npcframe >= 15 && npcframe <= 30) { spr_master = atlas_npcs1.createSprite("crystalguy2");  }
				if(npcframe >= 30 && npcframe <= 45) { spr_master = atlas_npcs1.createSprite("crystalguy3");  }
				
				spr_master.setPosition(-16, -28);
				spr_master.setSize(8, 20);
				spr_master.draw(game.batch);
				
				
				if(npcframe >= 1   && npcframe <= 15) { spr_master = atlas_npcs1.createSprite("guard1");  }
				if(npcframe >= 15 && npcframe <= 30) { spr_master = atlas_npcs1.createSprite("guard2");  }
				if(npcframe >= 30 && npcframe <= 45) { spr_master = atlas_npcs1.createSprite("guard1");  }
				
				spr_master.setPosition(-35, -28);
				spr_master.setSize(8, 20);
				spr_master.draw(game.batch);
				
				
				if(npcframe >= 1   && npcframe <= 15) { spr_master = atlas_npcs1.createSprite("flowergirl1");  }
				if(npcframe >= 15 && npcframe <= 30) { spr_master = atlas_npcs1.createSprite("flowergirl2");  }
				if(npcframe >= 30 && npcframe <= 45) { spr_master = atlas_npcs1.createSprite("flowergirl3");  }
				
				spr_master.setPosition(38, 19);
				spr_master.setSize(8, 20);
				spr_master.draw(game.batch);
				
				
				if(npcframe >= 1   && npcframe <= 15) { spr_master = atlas_npcs1.createSprite("schoolerC2");  }
				if(npcframe >= 15 && npcframe <= 30) { spr_master = atlas_npcs1.createSprite("schoolerC2");  }
				if(npcframe >= 30 && npcframe <= 45) { spr_master = atlas_npcs1.createSprite("schoolerC2");  }
				
				spr_master.setPosition(-30, 19);
				spr_master.setSize(8, 20);
				spr_master.draw(game.batch);
						
				npcframe++;
				if(npcframe > 45) { npcframe = 1; }
			}
			
			if(player.Map.equals("StreetsB")) {
				npcWalk1 = npcWalk1 - 0.2f;
				npcWalk2 = npcWalk2 + 0.2f;
				npcframe++;
				
				if(npcframe >= 1  && npcframe <= 15) { spr_master = atlas_npcs1.createSprite("schoolerD1");  }
				if(npcframe >= 15 && npcframe <= 30) { spr_master = atlas_npcs1.createSprite("schoolerD2");  }
				if(npcframe >= 30 && npcframe <= 45) { spr_master = atlas_npcs1.createSprite("schoolerD3");  }
				if(npcframe >= 45) { npcframe = 1; }
				
				spr_master.setPosition(npcWalk1, -28);
				spr_master.setSize(8, 20);
				spr_master.draw(game.batch);
				
				if(npcframe >= 1  && npcframe <= 15) { spr_master = atlas_npcs1.createSprite("worker1"); }
				if(npcframe >= 15 && npcframe <= 30) { spr_master = atlas_npcs1.createSprite("worker2"); }
				if(npcframe >= 30 && npcframe <= 45) { spr_master = atlas_npcs1.createSprite("worker3"); }
				
				spr_master.setPosition(npcWalk2, -35);
				spr_master.setSize(8, 20);
				spr_master.draw(game.batch);
				
				if(npcframe >= 1  && npcframe <= 15) { spr_master = atlas_npcs1.createSprite("lady1"); }
				if(npcframe >= 15 && npcframe <= 30) { spr_master = atlas_npcs1.createSprite("lady2"); }
				if(npcframe >= 30 && npcframe <= 45) { spr_master = atlas_npcs1.createSprite("lady3"); }
				
				spr_master.setPosition(14.5f, -10f);
				spr_master.setSize(8, 20);
				spr_master.draw(game.batch);
				
				if(npcframe >= 1  && npcframe <= 15) { spr_master = atlas_npcs1.createSprite("cooker1"); }
				if(npcframe >= 15 && npcframe <= 30) { spr_master = atlas_npcs1.createSprite("cooker2"); }
				if(npcframe >= 30 && npcframe <= 45) { spr_master = atlas_npcs1.createSprite("cooker3"); }
				
				spr_master.setPosition(-37.5f, -4f);
				spr_master.setSize(8, 20);
				spr_master.draw(game.batch);
				
				if(npcWalk1 < -80) { npcWalk1 = 100; }
				if(npcWalk2 > 100) { npcWalk2 = -80; }
			}
		}
		
		@Override
		public void input(String input) {
			if(input.contains(":")) { return; }
			if(input.contains("none")) { return; }
			String text = player.Name + ":" + input;
			
			if(partyOn) {
				player.party = input;
				partyOn = false;
				return;
			}
			
			if(keepnetwork) {
				OnlineManager("Chat",input,"");
			}
			else {
				lstChats.add(text);
			}		
		}

		@Override
		public void canceled() {}

		@Override
		public boolean keyDown(int keycode) {
			
			if(playerDead) { return false; }
			
			if(state.equals("main")) {
				movement = true;		
				downKeys.add(keycode);
		        if (downKeys.size >= 2){
		            onMultipleKeysDown(keycode);
		        }
		        if(downKeys.size == 1) {
		        	if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
		        		player.Side = "left";
		        		player.Walk = "walk"; 
		        		padX = -66;
		        		player.playerInBattle = "no";
		        		return false;
		            }
		    		
		    		if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
		    			player.Side = "back";
		    			player.Walk = "walk";
		    			padY = -50;
		    			player.playerInBattle = "no";
		    			return false;
		            }
		    		
		    		if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
		    			player.Side = "front";
		    			player.Walk = "walk";	
		    			padY = -60;
		    			player.playerInBattle = "no";
		    			return false;
		            }
		    		
		    		if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
		    			player.Side = "right";
		    			player.Walk = "walk";
		    			padX = -55;
		    			player.playerInBattle = "no";
		    			return false;
		            } 
		    		if (keycode == Input.Keys.NUM_1) {
		    			if(skillUsed > 0) { return false; }
						skillUsed = 200;
						CheckSkill(1);
						return false;
		            }
		    		if (keycode == Input.Keys.NUM_2) {
		    			if(skillUsed > 0) { return false; }
						skillUsed = 200;
						CheckSkill(2);
						return false;
		            }
		    		if (keycode == Input.Keys.NUM_3) {
		    			if(skillUsed > 0) { return false; }
						skillUsed = 200;
						CheckSkill(3);
						return false;
		            }
		    		if (keycode == Input.Keys.TAB) {
		    			ChangeTarget();
						return false;
		            }
		    		if (keycode == Input.Keys.NUM_4) {
		    			CheckAction();
						return false;
		            }
		    		if (keycode == Input.Keys.H) {
		    			if(!player.hotkey1.equals("none")) {
							UseItem(hotketcountitem);
						}
		            }
		    		if (keycode == Input.Keys.C) {
		    			Gdx.input.getTextInput(this,"Mensagem:","","");
						return false;
		            }
		    		if (keycode == Input.Keys.M) {
		    			if(state.equals("main")) { state = "menu"; }
						return false;
		            }
		        }
			}
			
			
			
			return false;
		}
		
		private void onMultipleKeysDown (int mostRecentKeycode){
			
			if(state.equals("menu")) { return; }
			
			//For multiple key presses
		    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
		        	player.Side = "left-front";
		        	player.Walk = "walk";  	
		        	padX = -66;
		        	padY = -60;
		        	player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
		        	player.Side = "left-back";
		        	player.Walk = "walk";
		        	padX = -66;
		        	padY = -50; 
		        	player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
		    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
		    		player.Side = "right-back";
		    		player.Walk = "walk";	
		    		padX = -55;
		    		padY = -50;
		    		player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
		    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
		    		player.Side = "right-front";
		    		player.Walk = "walk";	
		    		padX = -55;
		    		padY = -60;
		    		player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
		        	player.Side = "back-right";
		        	player.Walk = "walk";
		        	padX = -55;
		        	padY = -50;
		        	player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
		        	player.Side = "back-left";
		        	player.Walk = "walk";
		        	padX = -66;
		        	padY = -50;
		        	player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
		        	player.Side = "front-right";
		        	player.Walk = "walk";
		        	padX = -55;
		        	padY = -60;
		        	player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
		        	player.Side = "front-left";
		        	player.Walk = "walk";	
		        	padX = -66;
		        	padY = -60;
		        	player.playerInBattle = "no";
		        }
		    }
		}	

		@Override
		public boolean keyUp(int keycode) {
			movement = false;
			downKeys.remove(keycode);
			player.Walk = "no";
			playerFrame = 1;
			countFrame = 1;
			padX = -60;
			padY = -55;
			breakwalk = "";
			
			if(player.Side.equals("left-front")) { player.Side = "front"; }
			if(player.Side.equals("left-back")) { player.Side = "front";}
			if(player.Side.equals("right-back")) { player.Side = "front";}
			if(player.Side.equals("right-front")) { player.Side = "front";}
			if(player.Side.equals("back-right")) { player.Side = "front";}
			if(player.Side.equals("back-left")) { player.Side = "front";}
			if(player.Side.equals("front-right")) { player.Side = "front";}
			if(player.Side.equals("front-left")) { player.Side = "front"; }
			
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int p1, int p2, int pointer, int button) {
			
			if(playerDead) { return false; }
			
			Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));	
			
			if(state.equals("main")) {
				if(player.Casting.equals("no")) { movement = true; } else { movement = false; }
				
				if(selectAreaRanged) {
					touchSkillX = coordsTouch.x;
					touchSkillY = coordsTouch.y;
					selectAreaRanged = false;
					rangedAttack = true;
					CheckAreaRangedSkill();
					showZone = true;
					countZoneSkill = 40;
				}
						
				//Menu
				if(coordsTouch.x > -70f && coordsTouch.x < -39f && coordsTouch.y > 39f && coordsTouch.y < 67f) {
					state = "menu";
					return false;
				}
				
				//Party
				if(coordsTouch.x > -38 && coordsTouch.x < -30f && coordsTouch.y > 44.5f && coordsTouch.y < 55.5f) {
					partyOn = true;
					Gdx.input.getTextInput(this,"Nome do grupo:","","");
					return false;
				}
				
				//Chat
				if(coordsTouch.x > -38 && coordsTouch.x < -30f && coordsTouch.y > 56 && coordsTouch.y < 67) {
					Gdx.input.getTextInput(this,"Mensagem:","","");
					return false;
				}
				
				//Action
				if(coordsTouch.x > 56 && coordsTouch.x < 61 && coordsTouch.y > -46 && coordsTouch.y < -30) {
					CheckAction();
					return false;
				}
				
				//hotkey
				if(coordsTouch.x > 54 && coordsTouch.x < 65 && coordsTouch.y > -25f && coordsTouch.y < -10f) {
					if(!player.hotkey1.equals("none")) {
						UseItem(hotketcountitem);
					}
					return false;
				}
				
				//ChangeTarget
				if(coordsTouch.x > 55 && coordsTouch.x < 62 && coordsTouch.y > -65f && coordsTouch.y < -51) {
					ChangeTarget();
					return false;
				}
				
				//Skill 1
				if(coordsTouch.x > 30 && coordsTouch.x < 35 && coordsTouch.y > -65f && coordsTouch.y < -51) {
					if(skillUsed > 0) { return false; }
					skillUsed = 200;
					CheckSkill(1);
					return false;
				}
				
				//Skill 2
				if(coordsTouch.x > 38 && coordsTouch.x < 43 && coordsTouch.y > -65f && coordsTouch.y < -51) {
					if(skillUsed > 0) { return false; }
					skillUsed = 200;
					CheckSkill(2);
					return false;
				}
				
				//Skill 3
				if(coordsTouch.x > 46 && coordsTouch.x < 51 && coordsTouch.y > -65f && coordsTouch.y < -51) {
					if(skillUsed > 0) { return false; }
					skillUsed = 200;
					CheckSkill(3);
					return false;
				}
				
			}
			
			if(state.equals("menu")) {
				//Voltar
				if(coordsTouch.x > -61 && coordsTouch.x < -36f && coordsTouch.y > -47f && coordsTouch.y < -38f) {
					state = "main";
					menuOptions = "";
					showDescription = false;
					return false;
				}
				
				//show acc
				if(coordsTouch.x > 17 && coordsTouch.x < 26 && coordsTouch.y > -45.5f && coordsTouch.y < -33.5f) {
					accountnumber = true;
					SysMsg = "Numero da conta: " + player.AccountID;
				}
				
				//Upload
				if(coordsTouch.x > 27 && coordsTouch.x < 35 && coordsTouch.y > -45.5f && coordsTouch.y < -33.5f) {
					OnlineManager("Upload","","");
					return false;
				}
						
				//Sound
				if(coordsTouch.x > 36 && coordsTouch.x < 44 && coordsTouch.y > -45.5f && coordsTouch.y < -33.5f) {
					//todo
				}
				
				//Exit
				if(coordsTouch.x > 45 && coordsTouch.x < 54 && coordsTouch.y > -45.5f && coordsTouch.y < -33.5f) {
					Gdx.app.exit();
				}
				
				//Item 1
				if(coordsTouch.x > -35.5f && coordsTouch.x < -27 && coordsTouch.y > 24 && coordsTouch.y < 36.5f) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(0); showDescription = true; menuOptions = ""; return false; }
					if(menuOptions.equals("descartar")) { DiscartItem(0);  menuOptions = ""; return false; }
					if(menuOptions.equals("atalho"))    { HotKeyItem(0);   menuOptions = ""; return false; }
					UseItem(0);
					return false;
				}
				
				//Item 2
				if(coordsTouch.x > -26f && coordsTouch.x < -18 && coordsTouch.y > 24 && coordsTouch.y < 36.5f) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(1); showDescription = true; menuOptions = ""; return false; }
					if(menuOptions.equals("descartar")) { DiscartItem(1);  menuOptions = ""; return false; }
					if(menuOptions.equals("atalho"))    { HotKeyItem(1);   menuOptions = ""; return false; }
					UseItem(1);
					return false;
				}
				
				//Item 3
				if(coordsTouch.x > -17f && coordsTouch.x < -8.5f && coordsTouch.y > 24 && coordsTouch.y < 36.5f) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(2); showDescription = true; menuOptions = ""; return false; }
					if(menuOptions.equals("descartar")) { DiscartItem(2);  menuOptions = ""; return false; }
					if(menuOptions.equals("atalho"))    { HotKeyItem(2);   menuOptions = ""; return false; }
					UseItem(2);
					return false;
				}

				//Item 4
				if(coordsTouch.x > -7.5f && coordsTouch.x < 0.5f && coordsTouch.y > 24 && coordsTouch.y < 36.5f) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(3); showDescription = true; menuOptions = ""; return false; }
					if(menuOptions.equals("descartar")) { DiscartItem(3);  menuOptions = ""; return false; }
					if(menuOptions.equals("atalho"))    { HotKeyItem(3);   menuOptions = ""; return false; }
					UseItem(3);
					return false;
				}
				
				//Item 5
				if(coordsTouch.x > -35.5f && coordsTouch.x < -27 && coordsTouch.y > 11 && coordsTouch.y < 23) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(4); showDescription = true; menuOptions = ""; return false; }
					if(menuOptions.equals("descartar")) { DiscartItem(4);  menuOptions = ""; return false; }
					if(menuOptions.equals("atalho"))    { HotKeyItem(4);   menuOptions = ""; return false; }
					UseItem(4);
					return false;
				}

				//Item 6
				if(coordsTouch.x > -26f && coordsTouch.x < -18 && coordsTouch.y > 11 && coordsTouch.y < 23) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(5); showDescription = true; menuOptions = ""; return false; }
					if(menuOptions.equals("descartar")) { DiscartItem(5);  menuOptions = ""; return false; }
					if(menuOptions.equals("atalho"))    { HotKeyItem(5);   menuOptions = ""; return false; }
					UseItem(5);
					return false;
				}
				
				//Item 7
				if(coordsTouch.x > -17f && coordsTouch.x < -8.5f && coordsTouch.y > 11 && coordsTouch.y < 23) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(6); showDescription = true; menuOptions = ""; return false; }
					if(menuOptions.equals("descartar")) { DiscartItem(6);  menuOptions = ""; return false; }
					if(menuOptions.equals("atalho"))    { HotKeyItem(6);   menuOptions = ""; return false; }
					UseItem(6);
					return false;
				}
				
				//Item 8
				if(coordsTouch.x > -7.5f && coordsTouch.x < 0.5f && coordsTouch.y > 11 && coordsTouch.y < 23) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(7); showDescription = true; menuOptions = ""; return false; }
					if(menuOptions.equals("descartar")) { DiscartItem(7);  menuOptions = ""; return false; }
					if(menuOptions.equals("atalho"))    { HotKeyItem(7);   menuOptions = ""; return false; }
					UseItem(7);
					return false;
				}
				
				//Item 9
				if(coordsTouch.x > -35.5f && coordsTouch.x < -27 && coordsTouch.y > -2 && coordsTouch.y < 10) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(8); showDescription = true; menuOptions = ""; return false; }
					if(menuOptions.equals("descartar")) { DiscartItem(8);  menuOptions = ""; return false; }
					if(menuOptions.equals("atalho"))    { HotKeyItem(8);   menuOptions = ""; return false; }
					UseItem(8);
					return false;
				}
				
				//Item 10
				if(coordsTouch.x > -26f && coordsTouch.x < -18 && coordsTouch.y > -2 && coordsTouch.y < 10) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(9); showDescription = true; menuOptions = ""; return false; }
					if(menuOptions.equals("descartar")) { DiscartItem(9);  menuOptions = ""; return false; }
					if(menuOptions.equals("atalho"))    { HotKeyItem(9);   menuOptions = ""; return false; }
					UseItem(9);
					return false;
				}
				
				//Item 11
				if(coordsTouch.x > -17f && coordsTouch.x < -8.5f && coordsTouch.y > -2 && coordsTouch.y < 10) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(10); showDescription = true; menuOptions = ""; return false; }
					if(menuOptions.equals("descartar")) { DiscartItem(10);  menuOptions = ""; return false; }
					if(menuOptions.equals("atalho"))    { HotKeyItem(10);   menuOptions = ""; return false; }
					UseItem(10);
					return false;
				}
				
				//Item 12
				if(coordsTouch.x > -7.5f && coordsTouch.x < 0.5f && coordsTouch.y > -2 && coordsTouch.y < 10) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(11); showDescription = true; menuOptions = ""; return false; }
					if(menuOptions.equals("descartar")) { DiscartItem(11);  menuOptions = ""; return false; }
					if(menuOptions.equals("atalho"))    { HotKeyItem(11);   menuOptions = ""; return false; }
					UseItem(11);
					return false;
				}
				
				//Item 13
				if(coordsTouch.x > -35.5f && coordsTouch.x < -27 && coordsTouch.y > -15 && coordsTouch.y < -3) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(12); showDescription = true; menuOptions = ""; return false; }
					if(menuOptions.equals("descartar")) { DiscartItem(12);  menuOptions = ""; return false; }
					if(menuOptions.equals("atalho"))    { HotKeyItem(12);   menuOptions = ""; return false; }
					UseItem(12);
					return false;
				}
				
				//Item 14
				if(coordsTouch.x > -26f && coordsTouch.x < -18 && coordsTouch.y > -15 && coordsTouch.y < -3) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(13); showDescription = true; menuOptions = ""; return false; }
					if(menuOptions.equals("descartar")) { DiscartItem(13);  menuOptions = ""; return false; }
					if(menuOptions.equals("atalho"))    { HotKeyItem(13);   menuOptions = ""; return false; }
					UseItem(13);
					return false;
				}
				
				//Item 15
				if(coordsTouch.x > -17f && coordsTouch.x < -8.5f && coordsTouch.y > -15 && coordsTouch.y < -3) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(14); showDescription = true; menuOptions = ""; return false; }
					if(menuOptions.equals("descartar")) { DiscartItem(14);  menuOptions = ""; return false; }
					if(menuOptions.equals("atalho"))    { HotKeyItem(14);   menuOptions = ""; return false; }
					UseItem(14);
					return false;
				}
				
				//Item 16
				if(coordsTouch.x > -7.5f && coordsTouch.x < 0.5f && coordsTouch.y > -15 && coordsTouch.y < -3) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(15); showDescription = true; menuOptions = ""; return false; }
					if(menuOptions.equals("descartar")) { DiscartItem(15);  menuOptions = ""; return false; }
					if(menuOptions.equals("atalho"))    { HotKeyItem(15);   menuOptions = ""; return false; }
					UseItem(15);
					return false;
				}
				
				//Descrição
				if(coordsTouch.x > -32 && coordsTouch.x < -18 && coordsTouch.y > -47 && coordsTouch.y < -38) {
					menuOptions = "descricao";
					return false;
				}
				
				//Atalho
				if(coordsTouch.x > -17 && coordsTouch.x < -3 && coordsTouch.y > -47 && coordsTouch.y < -38) {
					menuOptions = "atalho";
					return false;
				}
				
				//Descartar
				if(coordsTouch.x > -1 && coordsTouch.x < 13 && coordsTouch.y > -47 && coordsTouch.y < -38) {
					menuOptions = "descartar";
					return false;
				}
				
				//Unequip hat
				if(coordsTouch.x > -60 && coordsTouch.x < -36.5f && coordsTouch.y > -6 && coordsTouch.y < 36) {
					UnequipHat();
					return false;
				}
					
				//PET 1
				if(coordsTouch.x > -35.5f && coordsTouch.x < -27 && coordsTouch.y > -36 && coordsTouch.y < -23) {
					AddRemovePet();
					return false;
				}
				
				//Pet food
				if(coordsTouch.x > -26f && coordsTouch.x < -18 && coordsTouch.y > -36 && coordsTouch.y < -23) {
					PetFood();
					return false;
				}
				
				//Pet Training
				if(coordsTouch.x > -17f && coordsTouch.x < -8.5f && coordsTouch.y > -36 && coordsTouch.y < -23) {
					PetTraining();
					return false;
				}
				
				//Pet Clean
				if(coordsTouch.x > -7.5f && coordsTouch.x < 0.5f && coordsTouch.y > -36 && coordsTouch.y < -23) {
					PetClean();
					return false;
				}
				
					
				//Cristal 1
				if(coordsTouch.x > 1.5f && coordsTouch.x < 9.5f && coordsTouch.y > 25f && coordsTouch.y < 36.2f) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(15); showDescription = true; menuOptions = ""; return false; }
					RemoveCrystals(1);
				}
				
				//Cristal 2
				if(coordsTouch.x > 10.5f && coordsTouch.x < 19 && coordsTouch.y > 25f && coordsTouch.y < 36.2f) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(15); showDescription = true; menuOptions = ""; return false; }
					RemoveCrystals(2);
				}
				
				//Cristal 3
				if(coordsTouch.x > 20f && coordsTouch.x < 28 && coordsTouch.y > 25f && coordsTouch.y < 36.2f) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(15); showDescription = true; menuOptions = ""; return false; }
					RemoveCrystals(3);
				}
				
				//Cristal 4
				if(coordsTouch.x > 29f && coordsTouch.x < 37 && coordsTouch.y > 25f && coordsTouch.y < 36.2f) {
					if(menuOptions.equals("descricao")) { detailItem = GetDescription(15); showDescription = true; menuOptions = ""; return false; }
					RemoveCrystals(4);
				}
				
				
				//str
				if(coordsTouch.x > 1.5f && coordsTouch.x < 10 && coordsTouch.y > 2 && coordsTouch.y < 15) {
					if(!player.buffA.equals("none")) { return false; }
					if(!player.buffB.equals("none")) { return false; }
					if(!player.buffC.equals("none")) { return false; }
					CheckStatus("str");
				}
				//vit
				if(coordsTouch.x > 11f && coordsTouch.x < 19 && coordsTouch.y > 2 && coordsTouch.y < 15) {
					if(!player.buffA.equals("none")) { return false; }
					if(!player.buffB.equals("none")) { return false; }
					if(!player.buffC.equals("none")) { return false; }
					CheckStatus("vit");
				}
				//agi
				if(coordsTouch.x > 20f && coordsTouch.x < 28 && coordsTouch.y > 2 && coordsTouch.y < 15) {
					if(!player.buffA.equals("none")) { return false; }
					if(!player.buffB.equals("none")) { return false; }
					if(!player.buffC.equals("none")) { return false; }
					CheckStatus("agi");
				}
				//dex
				if(coordsTouch.x > 29 && coordsTouch.x < 37 && coordsTouch.y > 2 && coordsTouch.y < 15) {
					if(!player.buffA.equals("none")) { return false; }
					if(!player.buffB.equals("none")) { return false; }
					if(!player.buffC.equals("none")) { return false; }
					CheckStatus("dex");
				}
				//int
				if(coordsTouch.x > 38 && coordsTouch.x < 46 && coordsTouch.y > 2 && coordsTouch.y < 15) {
					if(!player.buffA.equals("none")) { return false; }
					if(!player.buffB.equals("none")) { return false; }
					if(!player.buffC.equals("none")) { return false; }
					CheckStatus("wis");
				}
				//luk
				if(coordsTouch.x > 1.5f && coordsTouch.x < 10 && coordsTouch.y > -17 && coordsTouch.y < -5) {
					if(!player.buffA.equals("none")) { return false; }
					if(!player.buffB.equals("none")) { return false; }
					if(!player.buffC.equals("none")) { return false; }
					CheckStatus("luk");
				}
				//res
				if(coordsTouch.x > 11f && coordsTouch.x < 19 && coordsTouch.y > -17 && coordsTouch.y < -5) {
					if(!player.buffA.equals("none")) { return false; }
					if(!player.buffB.equals("none")) { return false; }
					if(!player.buffC.equals("none")) { return false; }
					CheckStatus("res");
				}
				
			}
			
			if(state.equals("shop")) {
				//Voltar
				if(coordsTouch.x > -28 && coordsTouch.x < -6 && coordsTouch.y > -37f && coordsTouch.y < -28f) {
					state = "main";
				}
				
				if(shop.equals("shopCrytal")) {
					if(coordsTouch.x > -28 && coordsTouch.x < 16 && coordsTouch.y > 38 && coordsTouch.y < 46) {
						CrystalGive();
					}
				}
				
				if(shop.equals("shopJob")) {
					if(coordsTouch.x > -28 && coordsTouch.x < 16 && coordsTouch.y > 38 && coordsTouch.y < 46) {
						if(player.Job.equals("Aprendiz") && player.Level > 10) {
							player.Job = "Espadachim";
							return false;
						}
						if(player.Level < 10) { SysMsg = "Apenas level 10 ou maior"; SysMsgCount = 200;  return false;}
						if(!player.Job.equals("Aprendiz")) { SysMsg = "Voce ja evoluiu de classe"; SysMsgCount = 200; return false;}
					}
					if(coordsTouch.x > -28 && coordsTouch.x < 16 && coordsTouch.y > 29 && coordsTouch.y < 37) {
						if(player.Job.equals("Aprendiz") && player.Level > 10) {
							player.Job = "Feiticeiro";
							return false;
						}
						if(player.Level < 10) { SysMsg = "Apenas level 10 ou maior"; SysMsgCount = 200;  return false;}
						if(!player.Job.equals("Aprendiz")) { SysMsg = "Voce ja evoluiu de classe"; SysMsgCount = 200; return false;}
					}
					if(coordsTouch.x > -28 && coordsTouch.x < 16 && coordsTouch.y > 20 && coordsTouch.y < 28) {
						if(player.Job.equals("Aprendiz") && player.Level > 10) {
							player.Job = "Medico";
							return false;
						}
						if(player.Level < 10) { SysMsg = "Apenas level 10 ou maior"; SysMsgCount = 200;  return false;}
						if(!player.Job.equals("Aprendiz")) { SysMsg = "Voce ja evoluiu de classe"; SysMsgCount = 200; return false;}
					}
					if(coordsTouch.x > -28 && coordsTouch.x < 16 && coordsTouch.y > 11 && coordsTouch.y < 18) {
						if(player.Job.equals("Novice") && player.Level > 10) {
							player.Job = "Batedor";
							return false;
						}
						if(player.Level < 10) { SysMsg = "Apenas level 10 ou maior"; SysMsgCount = 200;  return false;}
						if(!player.Job.equals("Aprendiz")) { SysMsg = "Voce ja evoluiu de classe"; SysMsgCount = 200; return false;}
					}
					if(coordsTouch.x > -28 && coordsTouch.x < 16 && coordsTouch.y > 1 && coordsTouch.y < 9) {
						if(player.Job.equals("Novice") && player.Level > 10) {
							player.Job = "Pistoleiro";
							return false;
						}
						if(player.Level < 10) { SysMsg = "Apenas level 10 ou maior"; SysMsgCount = 200;  return false;}
						if(!player.Job.equals("Aprendiz")) { SysMsg = "Voce ja evoluiu de classe"; SysMsgCount = 200; return false;}
					}
					if(coordsTouch.x > -28 && coordsTouch.x < 16 && coordsTouch.y > -8 && coordsTouch.y < -1) {
						if(player.Job.equals("Novice") && player.Level > 10) {
							player.Job = "Ladrao";
							return false;
						}
						if(player.Level < 10) { SysMsg = "Apenas level 10 ou maior"; SysMsgCount = 200;  return false;}
						if(!player.Job.equals("Aprendiz")) { SysMsg = "Voce ja evoluiu de classe"; SysMsgCount = 200; return false;}
					}			
				}
				
				
				//shopitem1
				if(coordsTouch.x > -29 && coordsTouch.x < -21 && coordsTouch.y > 32f && coordsTouch.y < 45f) {
					if(player.Map.equals("StreetsA")) { CheckBuyItemStreetsA(1); }
				}
				//shopitem2
				if(coordsTouch.x > -19 && coordsTouch.x < -11 && coordsTouch.y > 32f && coordsTouch.y < 45f) {
					if(player.Map.equals("StreetsA")) { CheckBuyItemStreetsA(2); }
				}
				//shopitem3
				if(coordsTouch.x > -9 && coordsTouch.x < -1 && coordsTouch.y > 32f && coordsTouch.y < 45f) {
					if(player.Map.equals("StreetsA")) { CheckBuyItemStreetsA(3); }
				}
				//shopitem4
				if(coordsTouch.x > 1 && coordsTouch.x < 9 && coordsTouch.y > 32f && coordsTouch.y < 45f) {
					if(player.Map.equals("StreetsA")) { CheckBuyItemStreetsA(4); }
				}
				//shopitem5
				if(coordsTouch.x > 10 && coordsTouch.x < 18 && coordsTouch.y > 32f && coordsTouch.y < 45f) {
					if(player.Map.equals("StreetsA")) { CheckBuyItemStreetsA(5); }
				}
				//shopitem6
				if(coordsTouch.x > -29 && coordsTouch.x < -21 && coordsTouch.y > 15f && coordsTouch.y < 28f) {
					if(player.Map.equals("StreetsA")) { CheckBuyItemStreetsA(6); }
				}
				//shopitem7
				if(coordsTouch.x > -19 && coordsTouch.x < -11 && coordsTouch.y > 15f && coordsTouch.y < 28f) {
					if(player.Map.equals("StreetsA")) { CheckBuyItemStreetsA(7); }
				}
				//shopitem8
				if(coordsTouch.x > -9 && coordsTouch.x < -1 && coordsTouch.y > 15f && coordsTouch.y < 28f) {
					if(player.Map.equals("StreetsA")) { CheckBuyItemStreetsA(8); }
				}
				//shopitem9
				if(coordsTouch.x > 1 && coordsTouch.x < 9 && coordsTouch.y > 15f && coordsTouch.y < 28f) {
					if(player.Map.equals("StreetsA")) { CheckBuyItemStreetsA(9); }
				}
				//shopitem10
				if(coordsTouch.x > 10 && coordsTouch.x < 18 && coordsTouch.y > 15f && coordsTouch.y < 28f) {
					if(player.Map.equals("StreetsA")) { CheckBuyItemStreetsA(10); }
				}
				//shopitem11
				if(coordsTouch.x > -29 && coordsTouch.x < -21 && coordsTouch.y > -1f && coordsTouch.y < 12f) {
					if(player.Map.equals("StreetsA")) { CheckBuyItemStreetsA(11); }
				}
				//shopitem12
				if(coordsTouch.x > -19 && coordsTouch.x < -11 && coordsTouch.y > -1f && coordsTouch.y < 12f) {
					if(player.Map.equals("StreetsA")) { CheckBuyItemStreetsA(12); }
				}
				//shopitem13
				if(coordsTouch.x > -9 && coordsTouch.x < -1 && coordsTouch.y > -1f && coordsTouch.y < 12f) {
					if(player.Map.equals("StreetsA")) { CheckBuyItemStreetsA(13); }
				}
				//shopitem14
				if(coordsTouch.x > 1 && coordsTouch.x < 9 && coordsTouch.y > -1f && coordsTouch.y < 12f) {
					if(player.Map.equals("StreetsA")) { CheckBuyItemStreetsA(14); }
				}
				//shopitem15
				if(coordsTouch.x > 10 && coordsTouch.x < 18 && coordsTouch.y > -1f && coordsTouch.y < 12f) {
					if(player.Map.equals("StreetsA")) { CheckBuyItemStreetsA(15); }
				}
				//shopitem16
				if(coordsTouch.x > 1 && coordsTouch.x < 9 && coordsTouch.y > -16f && coordsTouch.y < -4f) {
					if(player.Map.equals("StreetsA")) { CheckBuyItemStreetsA(16); }
				}
				//shopitem17
				if(coordsTouch.x > 10 && coordsTouch.x < 18 && coordsTouch.y > -16f && coordsTouch.y < -4f) {
					if(player.Map.equals("StreetsA")) { CheckBuyItemStreetsA(17); }
				}
			}
			
			return false;
			
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			movement = false;
			player.Walk = "no";
			playerFrame = 1;
			countFrame = 1;
			padX = -60;
			padY = -55;
			breakwalk = "";
			
			if(player.Side.equals("left-front")) { player.Side = "front"; }
			if(player.Side.equals("left-back")) { player.Side = "front";}
			if(player.Side.equals("right-back")) { player.Side = "front";}
			if(player.Side.equals("right-front")) { player.Side = "front";}
			if(player.Side.equals("back-right")) { player.Side = "front";}
			if(player.Side.equals("back-left")) { player.Side = "front";}
			if(player.Side.equals("front-right")) { player.Side = "front";}
			if(player.Side.equals("front-left")) { player.Side = "front"; }
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			
			if(playerDead) { return false; }
			
			Vector3 coordsTouch = camera.unproject(new Vector3(screenX,screenY,0));
			
			if(movement){	
				//Right
     				if(coordsTouch.x >= -53.5f && coordsTouch.x <= -45.5f && coordsTouch.y > -50f && coordsTouch.y < -42f) {
					player.Side = "right";
					player.Walk = "walk";	
					padX = -55;		
					player.playerInBattle = "no";
					return false;
				}
				//Left
				if(coordsTouch.x >= -57.5f && coordsTouch.x <= -45.5f && coordsTouch.y > -50f && coordsTouch.y < -42) {
					player.Side = "left";
					player.Walk = "walk";	
					padX = -66;		
					player.playerInBattle = "no";
					return false;
				}
				//Front
				if(coordsTouch.x > -59.5f && coordsTouch.x < -51.5f && coordsTouch.y > -65 && coordsTouch.y < -50f) {
					player.Side = "front";
					player.Walk = "walk";	
					padY = -60;
					player.playerInBattle = "no";
					return false;
				}
				//Back
				if(coordsTouch.x > -59.5f && coordsTouch.x < -51.5f && coordsTouch.y > -42f && coordsTouch.y < -27) {
					player.Side = "back";
					player.Walk = "walk";	
					padY = -50;
					player.playerInBattle = "no";
					return false;
				}
			}
			return false;
		}
		
		public void OnlineManager(String operation, String subData, String extraData) {
			try {
				if(operation.equals("CheckVersion")) {  
					TipoOperacaoOnline("CheckVersion", subData);
				}
				if(operation.equals("Upload")) {  
					GerenciamentoOnline("Upload","",extraData);
				}
				if(operation.equals("Download")) {
					GerenciamentoOnline("Download","",extraData);    
				}
				if(operation.equals("Chat")) {
					GerenciamentoOnline("Chat",subData,extraData);
				}
				if(operation.equals("Atk")) {
					GerenciamentoOnline("Atk",subData,extraData);
				}
				
				if(operation.equals("SyncPlayer")) {
					threahCountSyncPlayer = 1;
					ThreadsSyncStartPlayer();				
				}
				if(operation.equals("SyncChats")) {
					threahCountSyncChat = 1;
					ThreadsSyncStartChat();				
				}
				if(operation.equals("SyncMob")) {
					threahCountSyncMob = 1;
					ThreadsSyncStartMobs();				
				}
			}
			
			catch(Exception ex) {}
		}
	
		public void TipoOperacaoOnline(String nomeOperacao, String subData) {
			
			try {
				String retorno = "retry";
				
				if(nomeOperacao.equals("CheckVersion")) {
					retorno = GerenciamentoOnline("CheckVersion","","");
					
					if(retorno.equals("Autorizado")) { onlineAuth = true; }
					
					if(!versionDif) {
						if(retorno.equals("Probido")){
							versionDif = true;
							SysMsg = "Versao incompativel, fechando jogo";
							SysMsgCount = 200;
						}
					}
				}
			}
			
			catch(Exception ex) {
				
			}	
		}
		
		private void ThreadsSyncStartPlayer() {
			thrOnlineSyncPlayer = new Thread(t1);
			thrOnlineSyncPlayer.start();
		}
		
		private Runnable t1 = new Runnable() {
			public void run() {
				try{    
					while(threahCountSyncPlayer == 1) {
						GerenciamentoOnline("SyncPlayer","","");            	
					}
				}
				catch(Exception ex) {
					Thread.currentThread().interrupt();	
				}	
			}
		};
		
		private void ThreadsSyncStartChat() {
			thrOnlineSyncChat = new Thread(t2);
			thrOnlineSyncChat.start();
		}
		
		private Runnable t2 = new Runnable() {
			public void run() {
				try{    
					while(threahCountSyncChat == 1) {
						GerenciamentoOnline("SyncChats","","");            	
					}
				}
				catch(Exception ex) {
					Thread.currentThread().interrupt();	
				}	
			}
		};
		
		private void ThreadsSyncStartMobs() {
			thrOnlineSyncMob = new Thread(t3);
			thrOnlineSyncMob.start();
		}
		
		private Runnable t3 = new Runnable() {
			public void run() {
				try{    
					while(threahCountSyncMob == 1) {
						GerenciamentoOnline("SyncMob","","");            	
					}
				}
				catch(Exception ex) {
					Thread.currentThread().interrupt();	
				}	
			}
		};
		
		public String GerenciamentoOnline(String tipoRequisicao, String subData, String extraData) throws IOException {
	    	
			String linhaLida = "";
			
			countCleanOnline--;
			if(countCleanOnline < 0) {
				countCleanOnline = 500;
				lstOnlinePlayers.clear();
				lstOnlinePlayers.add(player);
				countCleanOnline = 800;
			}
			
			
			try {
			
			if(tipoRequisicao.equals("CheckVersion")){
				// Construct data
				
				String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
				data += "&" + URLEncoder.encode("lIDLocal", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
		        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("CheckVersion", "UTF-8");
		        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
		        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
		        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
		        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
		        data += "&" + URLEncoder.encode("lversion", "UTF-8") + "=" + URLEncoder.encode("1A", "UTF-8");
		        
		        // Send data
		        URL url = new URL("http://moonboltprojects.online/Conector/Online.php");
		        //URL url = new URL("http://localhost/default.php");
		        URLConnection conn = url.openConnection();
		        conn.setDoOutput(true);
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		        wr.write(data);
		        wr.flush();
		        
		        // Get the response
		        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        String line;
		        line = "";
		        retornoOnline = "retry";
		        while ((line = rd.readLine()) != null) {
		        	linhaLida = line;   
		        	//Resultado: - Logado -. <br>done
			        if (linhaLida.contains("Autorizado")) {            	
		        		retornoOnline = "Autorizado";       		
		            }	
			        else {
			        	retornoOnline = "Probido"; 
			        }
	    		}	        
		        wr.close();
		        rd.close();
	    
		        return retornoOnline;		        
			}
			
			if(tipoRequisicao.equals("Chat")){
				
				// Construct data	
				String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
				data += "&" + URLEncoder.encode("lIDLocal", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
				data += "&" + URLEncoder.encode("lAccountID", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
		        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Chat", "UTF-8");
		        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
		        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
		        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
		        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
		        data += "&" + URLEncoder.encode("lversion", "UTF-8") + "=" + URLEncoder.encode("1A", "UTF-8");
		        data += "&" + URLEncoder.encode("lName", "UTF-8") + "=" + URLEncoder.encode(player.Name, "UTF-8");
		        data += "&" + URLEncoder.encode("lChat", "UTF-8") + "=" + URLEncoder.encode(subData, "UTF-8");
		        
		        // Send data
		        URL url = new URL("http://moonboltprojects.online/Conector/Online.php");
		        URLConnection conn = url.openConnection();
		        conn.setDoOutput(true);
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		        wr.write(data);
		        wr.flush();
		        
		        // Get the response
		        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        String line;
		        line = "";
		        retornoOnline = "retry";
		        while ((line = rd.readLine()) != null) {
		        	linhaLida = line;   
	    		}	        
		        wr.close();
		        rd.close();
	    
		        return retornoOnline;		        
			}
			
			if(tipoRequisicao.equals("Atk")){
				int numMob = Integer.parseInt(subData);
				
 				String mobLetter = "A";
				if(numMob == 0) { mobLetter = "A"; }
				if(numMob == 1) { mobLetter = "B"; }
				if(numMob == 2) { mobLetter = "C"; }
				if(numMob == 3) { mobLetter = "D"; }
				
				// Construct data	
				String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
				data += "&" + URLEncoder.encode("lIDLocal", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
				data += "&" + URLEncoder.encode("lAccountID", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
		        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Atk", "UTF-8");
		        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
		        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
		        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
		        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
		        data += "&" + URLEncoder.encode("lMobHitTarget", "UTF-8") + "=" + URLEncoder.encode(lstMobs.get(numMob).MobID, "UTF-8");
		        data += "&" + URLEncoder.encode("lName", "UTF-8") + "=" + URLEncoder.encode(player.Name, "UTF-8");
		        data += "&" + URLEncoder.encode("lHpMobAtual", "UTF-8") + "=" + URLEncoder.encode(extraData, "UTF-8"); 
		        data += "&" + URLEncoder.encode("lMobLetter", "UTF-8") + "=" + URLEncoder.encode(mobLetter, "UTF-8");
		        
		        // Send data
		        URL url = new URL("http://moonboltprojects.online/Conector/Online.php");
		        URLConnection conn = url.openConnection();
		        conn.setDoOutput(true);
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		        wr.write(data);
		        wr.flush();
		        
		        // Get the response
		        BufferedReader rdd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        String line;
		        line = "";
		        retornoOnline = "retry";
		        while ((line = rdd.readLine()) != null) {
		        	linhaLida = line;   
	    		}	        
		        wr.close();
		        rdd.close();
		        
		        return retornoOnline;		        
			}
			
			
			if(tipoRequisicao.equals("SyncChats")){
				
				// Construct data				
				String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
				data += "&" + URLEncoder.encode("lIDLocal", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
		        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("SyncChats", "UTF-8");
		        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
		        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
		        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
		        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
		        data += "&" + URLEncoder.encode("lName", "UTF-8") + "=" + URLEncoder.encode(player.Name, "UTF-8");
		        data += "&" + URLEncoder.encode("lChat", "UTF-8") + "=" + URLEncoder.encode(subData, "UTF-8");
		        
		        // Send data
		        URL url = new URL("http://moonboltprojects.online/Conector/Online.php");
		        URLConnection conn = url.openConnection();
		        conn.setDoOutput(true);
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		        wr.write(data);
		        wr.flush();
		        
		        // Get the response
		        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        String line;
		        String linechat = "";
		        line = "";
		        
		        retornoOnline = "retry";
		        while ((line = rd.readLine()) != null) {
		        	linhaLida = line;  
		        	//Resultado: - Logado -. <br>done
		        	if (linhaLida.contains("SYSTEMCHAT")) { 
		        		String[] lineSplit = line.split(":");
		        		linechat = lineSplit[2] + "=" + lineSplit[4];
		    			UpdateListOnlineChats(linechat);
		            }
	    		}	        
		        wr.close();
		        rd.close();
	    
		        return retornoOnline;		        
			}
			
			if(tipoRequisicao.equals("SyncPlayer")){
				// Construct data
				String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
				data += "&" + URLEncoder.encode("lIDLocal", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
		        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("SyncPlayer", "UTF-8");
		        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
		        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
		        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
		        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
		        //Sync Data
		        data += "&" + URLEncoder.encode("lAccountID", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
		        data += "&" + URLEncoder.encode("lName", "UTF-8") + "=" + URLEncoder.encode(player.Name, "UTF-8");
		        data += "&" + URLEncoder.encode("lLevel", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.Level), "UTF-8");
		        data += "&" + URLEncoder.encode("lMap", "UTF-8") + "=" + URLEncoder.encode(player.Map, "UTF-8");
		        data += "&" + URLEncoder.encode("lHp", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.Hp), "UTF-8");
		        data += "&" + URLEncoder.encode("lMp", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.Mp), "UTF-8");
		        data += "&" + URLEncoder.encode("lPosX", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.PosX), "UTF-8");
		        data += "&" + URLEncoder.encode("lPosY", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.PosY), "UTF-8");
		        data += "&" + URLEncoder.encode("lWalk", "UTF-8") + "=" + URLEncoder.encode(player.Walk, "UTF-8");
		        data += "&" + URLEncoder.encode("lWeapon", "UTF-8") + "=" + URLEncoder.encode(player.Weapon, "UTF-8");
		        data += "&" + URLEncoder.encode("lFrame", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.Frame), "UTF-8");
		        data += "&" + URLEncoder.encode("lExp", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(GiveExp), "UTF-8");
		        data += "&" + URLEncoder.encode("lParty", "UTF-8") + "=" + URLEncoder.encode(player.party, "UTF-8");
		        data += "&" + URLEncoder.encode("lSyncPlayerMob", "UTF-8") + "=" + URLEncoder.encode("none", "UTF-8");
		        data += "&" + URLEncoder.encode("lPlayerSet", "UTF-8") + "=" + URLEncoder.encode(player.Set, "UTF-8");       
		        data += "&" + URLEncoder.encode("lHair", "UTF-8") + "=" + URLEncoder.encode(player.Hair, "UTF-8");    
		        data += "&" + URLEncoder.encode("lSex", "UTF-8") + "=" + URLEncoder.encode(player.Sex, "UTF-8");  
		        data += "&" + URLEncoder.encode("lColor", "UTF-8") + "=" + URLEncoder.encode(player.Color, "UTF-8");  
		        data += "&" + URLEncoder.encode("lHat", "UTF-8") + "=" + URLEncoder.encode(player.Hat, "UTF-8"); 
		        data += "&" + URLEncoder.encode("lSide", "UTF-8") + "=" + URLEncoder.encode(player.Side, "UTF-8"); 
		        data += "&" + URLEncoder.encode("lJob", "UTF-8") + "=" + URLEncoder.encode(player.Job, "UTF-8"); 
		        data += "&" + URLEncoder.encode("lplayerInBattle", "UTF-8") + "=" + URLEncoder.encode(player.playerInBattle, "UTF-8"); 
		        data += "&" + URLEncoder.encode("lplayerInAttack", "UTF-8") + "=" + URLEncoder.encode(player.playerInAttack, "UTF-8"); 
		        data += "&" + URLEncoder.encode("lplayerInCast", "UTF-8") + "=" + URLEncoder.encode(player.playerInCast, "UTF-8"); 
		        
		        // Send data
		        URL url = new URL("http://moonboltprojects.online/Conector/Online.php");
		        URLConnection conn = url.openConnection();
		        conn.setDoOutput(true);
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		        wr.write(data);
		        wr.flush();
		        
		        // Get the response
		        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        String line = "";
		        retornoOnline = "retry";
		        while ((line = rd.readLine()) != null) {
		        	linhaLida = line;   
		        	if (linhaLida.contains("SYSTEMPLAYERS")) {            	
			        	UpdateListOnlinePlayers(line);     		
		            }	
	    		}	        
		        wr.close();
		        rd.close();
		
		        return retornoOnline;		        
			}
			
			if(tipoRequisicao.equals("SyncMob")){ 
				
				// Construct data				
				String data = URLEncoder.encode("lIDLocal", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8");
				data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("SyncMob", "UTF-8");
		        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
		        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
		        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
		        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
		        data += "&" + URLEncoder.encode("lName", "UTF-8") + "=" + URLEncoder.encode(player.Name, "UTF-8");
		        data += "&" + URLEncoder.encode("lMap", "UTF-8") + "=" + URLEncoder.encode(player.Map, "UTF-8");
		        
		        if(playerMobSync) { 
		        	
			        data += "&" + URLEncoder.encode("lplayersync", "UTF-8") + "=" + URLEncoder.encode("playerMobSync", "UTF-8");  //here
			        
			        data += "&" + URLEncoder.encode("lMobA", "UTF-8") + "=" + URLEncoder.encode(lstMobs.get(0).MobID, "UTF-8");  
			        data += "&" + URLEncoder.encode("lMobAPosX", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lstMobs.get(0).MobPosX), "UTF-8");  
			        data += "&" + URLEncoder.encode("lMobAPosY", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lstMobs.get(0).MobPosY), "UTF-8"); 
			        
			        data += "&" + URLEncoder.encode("lMobB", "UTF-8") + "=" + URLEncoder.encode(lstMobs.get(1).MobID, "UTF-8");  
			        data += "&" + URLEncoder.encode("lMobBPosX", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lstMobs.get(1).MobPosX), "UTF-8");  
			        data += "&" + URLEncoder.encode("lMobBPosY", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lstMobs.get(1).MobPosY), "UTF-8");  
			        
			        data += "&" + URLEncoder.encode("lMobC", "UTF-8") + "=" + URLEncoder.encode(lstMobs.get(2).MobID, "UTF-8");  
			        data += "&" + URLEncoder.encode("lMobCPosX", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lstMobs.get(2).MobPosX), "UTF-8");  
			        data += "&" + URLEncoder.encode("lMobCPosY", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lstMobs.get(2).MobPosY), "UTF-8");   
			        
			        data += "&" + URLEncoder.encode("lMobD", "UTF-8") + "=" + URLEncoder.encode(lstMobs.get(3).MobID, "UTF-8");  
			        data += "&" + URLEncoder.encode("lMobDPosX", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lstMobs.get(3).MobPosX), "UTF-8");  
			        data += "&" + URLEncoder.encode("lMobDPosY", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lstMobs.get(3).MobPosY), "UTF-8"); 
			        
		        }
		        
		        if(!playerMobSync) { data += "&" + URLEncoder.encode("lplayersync", "UTF-8") + "=" + URLEncoder.encode("playerMobSyncNot", "UTF-8");  }
		        		        
		        // Send data
		        URL url = new URL("http://moonboltprojects.online/Conector/Online.php");
		        URLConnection conn = url.openConnection();
		        conn.setDoOutput(true);
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		        wr.write(data);
		        wr.flush();
		        
		        // Get the response
		        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        String line;
		        line = "";
		        
		        retornoOnline = "retry";
		        if(playerMobSync) {  
		        	retornoOnline = "retry";
		        }
		        while ((line = rd.readLine()) != null) {  //heremob
		        	linhaLida = line;  
		        	//Resultado: - Logado -. <br>done
		        	if (linhaLida.contains("SYSTEMMOB")) {
		        		UpdateListOnlineMobs(linhaLida);
		            }
	    		}	        
		        wr.close();
		        rd.close();
	    
		        return retornoOnline;		        
			}
			
			if(tipoRequisicao.equals("Upload")) {
					try {
					
					//Edite dada
					FileHandle file = Gdx.files.local("SaveData/save.json");	
					String arq = file.readString();
					
			        // Construct data
					//Instrucoes para Conexao
			        String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(arq, "UTF-8");
			        data += "&" + URLEncoder.encode("lAccountID", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
			        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Upload", "UTF-8");
			        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
			        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
			        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
			        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
			   	        
			        // Send data
			        URL url = new URL("http://moonboltprojects.online/Conector/Online.php");
			        URLConnection conn = url.openConnection();
			        conn.setDoOutput(true);
			        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			        wr.write(data);
			        wr.flush();
			 
			        // Get the response
			        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			        String line;
			        while ((line = rd.readLine()) != null) {
			            if(line.contains("Sucesso")){
			            	uploadDone = true;
			            	SysMsg = "Upload feito com sucesso";
			            	SysMsgCount = 200;
			            }
			        	//System.out.println(line);
			        }		        
			        wr.close();
			        rd.close();
			        return retornoOnline;
			    } 
				
				catch (Exception e) { return "retry";}
			}
			
			return "";
			}
			
			catch(Exception ex) {
				return "retry";
			}
		}
		
		public void UpdateListOnlineMobs(String line) {
			String[] lineSplit = line.split(":");
			
			for(int i = 0; i < lstMobs.size(); i++) {
				if(i == 0) {
					if(lstMobs.get(i).MobID.equals(lineSplit[2])) {
						lstMobs.get(i).MobHp = Integer.parseInt(lineSplit[4]);
						lstMobs.get(i).MobMp = Integer.parseInt(lineSplit[6]);
						if(!playerMobSync) {  lstMobs.get(i).MobPosX = Float.parseFloat(lineSplit[8]); }
						if(!playerMobSync) {  lstMobs.get(i).MobPosY = Float.parseFloat(lineSplit[10]); }
						lstMobs.get(i).MobTarget = lineSplit[12];
						lstMobs.get(i).MobDead = lineSplit[14];		
						lstMobs.get(i).MobTimeDead = Integer.parseInt(lineSplit[16]);
					}
				}	
				if(i == 1) {
					if(lstMobs.get(i).MobID.equals(lineSplit[18])) {
						lstMobs.get(i).MobHp = Integer.parseInt(lineSplit[20]);
						lstMobs.get(i).MobMp = Integer.parseInt(lineSplit[22]);
						if(!playerMobSync) {  lstMobs.get(i).MobPosX = Float.parseFloat(lineSplit[24]); }
						if(!playerMobSync) {  lstMobs.get(i).MobPosY = Float.parseFloat(lineSplit[26]); }
						lstMobs.get(i).MobTarget = lineSplit[28];
						lstMobs.get(i).MobDead = lineSplit[30];	
						lstMobs.get(i).MobTimeDead = Integer.parseInt(lineSplit[32]);
					}
				}
				if(i == 2) {
					if(lstMobs.get(i).MobID.equals(lineSplit[34])) {
						lstMobs.get(i).MobHp = Integer.parseInt(lineSplit[36]);
						lstMobs.get(i).MobMp = Integer.parseInt(lineSplit[38]);
						if(!playerMobSync) {  lstMobs.get(i).MobPosX = Float.parseFloat(lineSplit[40]); }
						if(!playerMobSync) {  lstMobs.get(i).MobPosY = Float.parseFloat(lineSplit[42]); }
						lstMobs.get(i).MobTarget = lineSplit[44];
						lstMobs.get(i).MobDead = lineSplit[46];	
						lstMobs.get(i).MobTimeDead = Integer.parseInt(lineSplit[48]);
					}
				}
				if(i == 3) {
					if(lstMobs.get(i).MobID.equals(lineSplit[50])) {
						lstMobs.get(i).MobHp = Integer.parseInt(lineSplit[52]);
						lstMobs.get(i).MobMp = Integer.parseInt(lineSplit[54]);
						if(!playerMobSync) {  lstMobs.get(i).MobPosX = Float.parseFloat(lineSplit[56]); }
						if(!playerMobSync) {  lstMobs.get(i).MobPosY = Float.parseFloat(lineSplit[58]); }
						lstMobs.get(i).MobTarget = lineSplit[60];
						lstMobs.get(i).MobDead = lineSplit[62];		
						lstMobs.get(i).MobTimeDead = Integer.parseInt(lineSplit[64]);
					}
				}
			}
		}
		
		public void UpdateListOnlineChats(String line) {
			
			if(lstChats.get(0).equals(line) || lstChats.get(1).equals(line) || lstChats.get(2).equals(line)) { return; }
			
			if(!lstChats.get(0).equals(line) && !lstChats.get(1).equals(line) && !lstChats.get(2).equals(line)) { 
				
				String chat0 = lstChats.get(0);
				String chat1 = lstChats.get(1);
				
				lstChats.set(0, line);
				lstChats.set(1, chat0);
				lstChats.set(2, chat1);
			}
		}
	   
		public void UpdateListOnlinePlayers(String line) {   //here
			String[] lineSplit = line.split(":");
			newOnlinePlayer = new GameObject();
			newOnlinePlayer.AccountID = lineSplit[2];
			newOnlinePlayer.Name = lineSplit[4];
			newOnlinePlayer.Level = Integer.parseInt(lineSplit[6]);
			newOnlinePlayer.Map = lineSplit[8];
			newOnlinePlayer.Hp = Integer.parseInt(lineSplit[10]);
			newOnlinePlayer.Mp = Integer.parseInt(lineSplit[12]);
			newOnlinePlayer.PosX = Float.parseFloat(lineSplit[14]);
			newOnlinePlayer.PosY = Float.parseFloat(lineSplit[16]);
			newOnlinePlayer.Walk = lineSplit[18];
			newOnlinePlayer.Weapon = lineSplit[20];
			newOnlinePlayer.Frame = Integer.parseInt(lineSplit[22]);
			newOnlinePlayer.Exp = Integer.parseInt(lineSplit[24]);
			newOnlinePlayer.party = lineSplit[26];
			newOnlinePlayer.Set = lineSplit[30];
			newOnlinePlayer.Hair = lineSplit[32];
			newOnlinePlayer.Sex = lineSplit[34];
			newOnlinePlayer.Color = lineSplit[36];
			newOnlinePlayer.Hat = lineSplit[38];
			newOnlinePlayer.Side = lineSplit[40];
			newOnlinePlayer.Job = lineSplit[42];
			newOnlinePlayer.playerInBattle = lineSplit[44];
			newOnlinePlayer.playerInAttack = lineSplit[46];
			newOnlinePlayer.playerInCast = lineSplit[48];
			
			if(player.Level < 10) {
			ReceiveExpFromOnlinePlayers(newOnlinePlayer.Exp,newOnlinePlayer.party);
			}
				
			boolean check = false;
			for(int i = 0; i < lstOnlinePlayers.size(); i++) {
				if(newOnlinePlayer.AccountID.equals(lstOnlinePlayers.get(i).AccountID)) {				
					lstOnlinePlayers.get(i).Name = newOnlinePlayer.Name; 
					lstOnlinePlayers.get(i).Level = newOnlinePlayer.Level; 
					lstOnlinePlayers.get(i).Map = newOnlinePlayer.Map; 
					lstOnlinePlayers.get(i).Hp = newOnlinePlayer.Hp; 
					lstOnlinePlayers.get(i).Mp = newOnlinePlayer.Mp; 
					lstOnlinePlayers.get(i).PosX = newOnlinePlayer.PosX; 
					lstOnlinePlayers.get(i).PosY = newOnlinePlayer.PosY;
					lstOnlinePlayers.get(i).Walk = newOnlinePlayer.Walk; 
					lstOnlinePlayers.get(i).Weapon = newOnlinePlayer.Weapon; 
					lstOnlinePlayers.get(i).Frame = newOnlinePlayer.Frame;
					lstOnlinePlayers.get(i).Exp = newOnlinePlayer.Exp;
					lstOnlinePlayers.get(i).party = newOnlinePlayer.party;
					lstOnlinePlayers.get(i).Set = newOnlinePlayer.Set;
					lstOnlinePlayers.get(i).Hair = newOnlinePlayer.Hair;
					lstOnlinePlayers.get(i).Sex = newOnlinePlayer.Sex;
					lstOnlinePlayers.get(i).Color = newOnlinePlayer.Color;
					lstOnlinePlayers.get(i).Hat = newOnlinePlayer.Hat;
					lstOnlinePlayers.get(i).Side = newOnlinePlayer.Side;
					lstOnlinePlayers.get(i).Job = newOnlinePlayer.Job;
					lstOnlinePlayers.get(i).playerInBattle = newOnlinePlayer.playerInBattle;
					lstOnlinePlayers.get(i).playerInAttack = newOnlinePlayer.playerInAttack;
					lstOnlinePlayers.get(i).playerInCast = newOnlinePlayer.playerInCast;
					check = true;
				}
			}		
			if(!check) {
				lstOnlinePlayers.add(newOnlinePlayer);
			}	
		}
		
		public void ReceiveExpFromOnlinePlayers(int exp, String party) {
			
			if(receiveExpOnline && party.equals(player.party)) {
				timerreceiveExpOnline = 200; 
				receiveExpOnline = false;
				player.Exp = player.Exp + exp;
			}		
		}
			
		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void create() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void render() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void show() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void resize(int p1, int p2)
		{
			viewport.update(p1,p2);
			camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		}	

		@Override
		public void pause() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void resume() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void hide() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}
		
}
