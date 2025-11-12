package com.moonbolt.cityscale;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.moonbolt.cityscale.interfaces.DateTimeProvider;
import com.moonbolt.cityscale.interfaces.HttpCallback;
import com.moonbolt.cityscale.models.Damage;
import com.moonbolt.cityscale.models.Monster;
import com.moonbolt.cityscale.models.Player;
import com.moonbolt.cityscale.models.Skill;


public class GameMap implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	
	//Objects
    private MainGame game;
    private ManagerScreen screen;
    private GameControl gameControl;
    private String state = "Main";
    private Sprite spr_master;
	private Random randnumber;
	private String shopname = "";
    private String menuoption = "";
    private int playernum = 0;
    private int flipzone = 0;
    private String playernumString = "";
    private String keyboardType = "none";
	private DateTimeProvider dateTimeProvider;
	private int changeBackground = 300;
	private String controlstate = "Mobile";
    
    //Variables usables
    private float floatUseA = 0;
    private float floatUseB = 0;
    private int playerHP = 0;
    private int playerHPMax = 0;
    private int playerMP = 0;
    private int playerMPMax = 0;
    private int climatic = 0;
    private int climaticTimer = 6000;
    private int climaticEffectTimer = 0;
    private int daytime = 0;
    private int regen = 0;
    
    //Aviso
    private boolean aviso = false;
	private int avisoTimer = 0;
	private String avisoMsg = "";
    
	//Fonts
	private BitmapFont font_master;
	
    //Camera
    private OrthographicCamera camera;
    private Viewport viewport;
    private float cameraCoordsX = 0;
    private float cameraCoordsY = 0;
    
    //Player
    public Player player;
    private float playerPosX;
    private float playerPosY;
    private Sprite spr_playerTagHair;
    private Sprite spr_playerhatmenu;
    private Sprite spr_playerhat;
    private Sprite spr_playerhair;
    private Sprite spr_playertop;
    private Sprite spr_playerbottom;
    private Sprite spr_playerfooter;
    private Sprite spr_playerweapon;
    private boolean playerDead = false;
    private boolean movement = false;
	private boolean autoattack = false;
	private int atkManual = 10;
	private int defManual = 0;
	private boolean defTrigged = false;
	private Sprite spr_target;
	private int countDead = 100;
	private String itemEquipped = "";
	private Sprite spr_item;
	private int hotketcountitem1;
	private int hotketcountitem2;
	private int skillUsed = 0; 
	private boolean notmp = false;
	private boolean rangedAttack = false;
    private boolean skillEffect = false;
    private String skillname = "";
    private boolean selectAreaRanged = false;
    private Sprite spr_sit;
    private int regenTime = 0;
    private float playerExp = 0;
    private int playerCastTime = 0;
    private Sprite spr_castEffect;
    private int castFrame = 30;
    private int grabTime = 30;
    private int grabStop = 0;
    private int backEnergy = 2;
    private Sprite spr_buffA;
    private Sprite spr_buffB;
    private Sprite spr_buffC;
    
	//Monster
	private ArrayList<Monster> listMonsters;
	private Sprite spr_monster;
	private int mobFrame = 1;
    private float mobPositionCoordX = 0;
    private float mobPositionCoordY = 0;
    private int mobRandomSt = 0;
    private int mobTimerMov = 100;
    private int mobTimerFrame = 40;
	private int countSwitchTarget;
	private int mobIndexAtk = 0;

	//Misc
	private ArrayList<Damage> listDamage;
	private ArrayList<Skill> listSkills;
	private ArrayList<String> lstChats;
	private String itemdropname;
	private int SysMsgCount = 0;    
    private int savedataTime = 500;
	private int showDropMsg;
	private Sprite spr_shop;
	private String showbuymsg = "";
	private int showbuymsgtime = 2000;
	private boolean showZone = false;
    private int countZoneSkill = 0;

	//Online
	private ArrayList<Player> lstOnlinePlayers;
	private Sprite spr_playerTopOnline;
    private Sprite spr_playerBottomOnline;
    private Sprite spr_playerFooterOnline;
    private Sprite spr_playerHairOnline;
    private Sprite spr_playerHatOnline;
    private Sprite spr_weaponOnline;
    private int timersync = 100;
    private float playerOnlineX;
    private float playerOnlineY;
    private Sprite spr_PartyTag;
    private Sprite spr_PartyHair;
    
    //NPC
    private Sprite spr_npc;  
    private float npcMobY = 60;
    private float npcMobX = 190;
    private float npcMobXReverse = -90;
    private int framenpc = 1;
    private int framecountnpc = 10;
    
    //UX
    private float padmoveX = -80;
    private float padmoveY = -75;
    private Sprite spr_playerTag;
     
    //Sprites Background
    private Sprite spr_Background;
    private Sprite spr_BackgroundOver;
    private Texture tex_Background;
    private Texture tex_Background2;
    private Texture tex_Background3;
    private Texture tex_BackgroundOver;
    private Texture tex_rain1;
    private Texture tex_rain2;
    private Texture tex_rain3;
    private Texture tex_snow1;
    private Texture tex_snow2;
    private Texture tex_snow3;
    private Texture tex_taxileft;
    private Texture tex_taxiright;
    private Texture tex_afternoon;
    private Texture tex_night;
    private Texture tex_blackout;
    private Sprite spr_rain;
    private Sprite spr_taxi1;
    private Sprite spr_taxi2;
    private float taxpathleft = 185;
    private float taxpathright = 0;
    
    //Metro
    private Sprite spr_metro;
    private Texture tex_metro;
    float metroPosX = -200;
    
    //Teste Dot
    private Sprite spr_testeDot;
    private Texture tex_testeDot;
    private int LoopTime = 35;
    
    // keyboard
 	private Texture tex_keyboard;
 	private Sprite spr_keyboard;
 	private String keyboardText = "";
 	
    
    //Controller
    private final IntSet downKeys = new IntSet(20);	

		public GameMap(MainGame _game, ManagerScreen _screen, GameControl _gameControl,int _playernumber,DateTimeProvider dateTimeProvider) {
			
			this.game = _game;	
			this.screen = _screen;
			this.randnumber = new Random();
			this.playernum = _playernumber;
			this.gameControl = _gameControl;
			this.playernumString = String.valueOf(playernum);
			this.dateTimeProvider = dateTimeProvider;
			
			ArrayList<Player> lstPlayer = new ArrayList<Player>();
			lstPlayer = gameControl.GetPlayers();
			
			if(_playernumber == 1) { player = lstPlayer.get(0); }
			if(_playernumber == 2) { player = lstPlayer.get(1); }
			if(_playernumber == 3) { player = lstPlayer.get(2); }

			//Damage/skill Stance
			listDamage = new ArrayList<Damage>();
			listSkills = new ArrayList<Skill>();
					
			//Camera and Inputs
			camera = new OrthographicCamera();
		    viewport = new StretchViewport(195,195,camera);
			viewport.apply();
			camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);			
			Gdx.input.setInputProcessor(this);
			
			// keyboard
			tex_keyboard = new Texture(Gdx.files.internal("data/assets/ux/keyboard.png"));
			spr_keyboard = new Sprite(tex_keyboard);
			
			//Etc
			lstChats = new ArrayList<String>();
			lstChats.add(""); lstChats.add(""); lstChats.add(""); lstChats.add(""); lstChats.add("");
			
			//Map
			if(player.Map.equals("MetroStation")) { 
				tex_Background = new Texture(Gdx.files.internal("data/assets/maps/metrostation.png"));  
			}
			
			tex_rain1 = new Texture(Gdx.files.internal("data/assets/maps/rain1.png")); 
			tex_rain2 = new Texture(Gdx.files.internal("data/assets/maps/rain2.png")); 
			tex_rain3 = new Texture(Gdx.files.internal("data/assets/maps/rain3.png")); 
			tex_snow1 = new Texture(Gdx.files.internal("data/assets/maps/snow1.png")); 
		    tex_snow2 = new Texture(Gdx.files.internal("data/assets/maps/snow2.png")); 
		    tex_snow3 = new Texture(Gdx.files.internal("data/assets/maps/snow3.png")); 
		    tex_afternoon = new Texture(Gdx.files.internal("data/assets/maps/afternoon.png")); 
		    tex_night = new Texture(Gdx.files.internal("data/assets/maps/night.png")); 
		    tex_blackout = new Texture(Gdx.files.internal("data/assets/maps/blackout.png"));
		    tex_taxileft = new Texture(Gdx.files.internal("data/assets/etc/taxileft.png"));
		    tex_taxiright = new Texture(Gdx.files.internal("data/assets/etc/taxiright.png"));
		    spr_rain = new Sprite(tex_rain1);
		    spr_taxi1 = new Sprite(tex_taxileft);
		    spr_taxi2 = new Sprite(tex_taxiright);
			
			if(player.Map.equals("StreetsA")) { 
				tex_Background = new Texture(Gdx.files.internal("data/assets/maps/streetsA.png"));  
				tex_Background2 = new Texture(Gdx.files.internal("data/assets/maps/streetsB.png")); 
				tex_Background3 = new Texture(Gdx.files.internal("data/assets/maps/streetsC.png")); 
				tex_BackgroundOver = new Texture(Gdx.files.internal("data/assets/maps/streetsAOver.png"));
				spr_BackgroundOver = new Sprite(tex_BackgroundOver);
			}
			
			if(player.Map.equals("Sewers")) { tex_Background = new Texture(Gdx.files.internal("data/assets/maps/sewers.png"));  }
			if(player.Map.equals("Forest")) { 
				tex_Background = new Texture(Gdx.files.internal("data/assets/maps/forest.png"));
				tex_BackgroundOver = new Texture(Gdx.files.internal("data/assets/maps/forestOver.png"));
				spr_BackgroundOver = new Sprite(tex_BackgroundOver);
			}
			if(player.Map.equals("Watercave")) { tex_Background = new Texture(Gdx.files.internal("data/assets/maps/watercave.png"));  }
			if(player.Map.equals("Desert")) { tex_Background = new Texture(Gdx.files.internal("data/assets/maps/desert.png"));  }
			if(player.Map.equals("Vulcano")) { tex_Background = new Texture(Gdx.files.internal("data/assets/maps/vulcano.png"));  }
			if(player.Map.equals("Mines")) { tex_Background = new Texture(Gdx.files.internal("data/assets/maps/mines.png"));  }
			if(player.Map.equals("Snowpalace")) { tex_Background = new Texture(Gdx.files.internal("data/assets/maps/snowpalace.png"));  }
			if(player.Map.equals("Swamp")) { tex_Background = new Texture(Gdx.files.internal("data/assets/maps/swamp.png"));  }
			
			//Mobs
			listMonsters = new ArrayList<Monster>();
			if(player.Map.equals("Sewers")) {  
				listMonsters = gameControl.LoadMonsters("Sewers");
			}
			if(player.Map.equals("Forest")) { 			
				listMonsters = gameControl.LoadMonsters("Forest"); 
			}
			if(player.Map.equals("Watercave")) { 			
				listMonsters = gameControl.LoadMonsters("Watercave"); 
			}
			if(player.Map.equals("Desert")) { 			
				listMonsters = gameControl.LoadMonsters("Desert"); 
			}
			if(player.Map.equals("Mines")) { 			
				listMonsters = gameControl.LoadMonsters("Mines"); 
			}
			if(player.Map.equals("Vulcano")) { 			
				listMonsters = gameControl.LoadMonsters("Vulcano"); 
			}
			if(player.Map.equals("Snowpalace")) { 			
				listMonsters = gameControl.LoadMonsters("Snowpalace"); 
			}
			if(player.Map.equals("Swamp")) { 			
				listMonsters = gameControl.LoadMonsters("Swamp"); 
			}
			
			spr_Background = new Sprite(tex_Background);
			
			//Network
			lstOnlinePlayers = new ArrayList<Player>();
	
			//font
			font_master = new BitmapFont(Gdx.files.internal("data/assets/font/impact.fnt"),Gdx.files.internal("data/assets/font/impact.png"), false);
			font_master.setColor(Color.WHITE);
			font_master.getData().setScale(0.07f,0.11f);
			font_master.setUseIntegerPositions(false);
			
			//Metro
			tex_metro = new Texture(Gdx.files.internal("data/assets/etc/metro.png"));
			spr_metro = new Sprite(tex_metro);
		    
			//test dot
			tex_testeDot = new Texture(Gdx.files.internal("data/assets/etc/testdot.png"));
			spr_testeDot = new Sprite(tex_testeDot);
			
			player.party = "none";
		}
		
		@Override
		public void render(float delta) {
			
			/* player.HpMax = "60";
			player.MpMax = "50";
			player.Vit = "1";
			player.Wis = "1";
			player.StatusPoint = "6"; */
			player.Money = "2000";
			player.Atk = "0";
			player.Def = "0";
			
			//Just for coloring
			Gdx.gl.glClearColor(1,1,1,1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			//Camera Ajustments
			cameraCoordsX = Float.parseFloat(player.PosX);
			cameraCoordsY = Float.parseFloat(player.PosY);
			
			//Follow camera
			floatUseA = Float.parseFloat(player.PosX); 
			if(floatUseA <= 18.5f) 	{ cameraCoordsX = 18.5f; }
			if(floatUseA >= 93) 	{ cameraCoordsX = 93; 	 }
			floatUseB = Float.parseFloat(player.PosY);
			if(floatUseB >= -22f) { cameraCoordsY = -22f; }
			if(floatUseB <= -97) 	{ cameraCoordsY = -97;  }
			
			//Update camera and start drawling
			camera.position.set(cameraCoordsX -2,cameraCoordsY+1,0);
			camera.update();
		    game.batch.setProjectionMatrix(camera.combined);	    
			game.batch.begin();
			
			
			//Save
			LoopTime--;
			if(LoopTime < 0) {
				try {
					gameControl.UpdateControlPlayer(player);
					gameControl.SaveChar("SaveChar",player.AccountNumber,playernumString, new HttpCallback() {
					    @Override
					    public void onSuccess(String response) {
					    	if(response.contains("success")) {
					    		//System.out.println("Salvo com sucesso");
					    	}
					    	else {
					    		//avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
					    		//aviso = true;
					    	}
					    }

					    @Override
					    public void onFailure(Throwable t) {
					       //System.out.println("Error: " + t.getMessage());
					       //avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
						   //aviso = true;
					    }
					});
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Online Sync
			if(LoopTime < 0) {
				gameControl.UpdatePlayer(player);
				
				RecoverOnlinePlayers();
				RecoverOnlineChats();
				RecoverOnlineMobs();
				player = gameControl.ReturnPlayerUse();
			}
			
			if(LoopTime < 0) { LoopTime = 35; }
			
			//Check Regen
			regen = Integer.parseInt(player.regenTime);
			regen = regen - 1;
			player.regenTime = String.valueOf(regen);
			gameControl.UpdateControlPlayer(player);
			
			if(regen <= 0) {
						
				playerHP = Integer.parseInt(player.Hp);
				playerHPMax = Integer.parseInt(player.HpMax);
				
				playerMP = Integer.parseInt(player.Mp);
				playerMPMax = Integer.parseInt(player.MpMax);
				
				playerHP = playerHP + 25;
				playerMP = playerMP + 25;
				
				if(playerHP >= playerHPMax) { playerHP = playerHPMax; }
				if(playerMP >= playerMPMax) { playerMP = playerMPMax; }
				
				player.Hp = String.valueOf(playerHP);
				player.Mp = String.valueOf(playerMP);
					
				player.regenTime = player.regenTimeMax;
			} 
			
			//Map Render
			if(player.Map.equals("StreetsA")) { 
				changeBackground -= 2;
				if(changeBackground >= 200 && changeBackground <= 300) {
					spr_Background = new Sprite(tex_Background);
				}
				if(changeBackground >= 100 && changeBackground <= 200) {
					spr_Background = new Sprite(tex_Background2);
				}
				if(changeBackground >= 0 && changeBackground <= 100) {
					spr_Background = new Sprite(tex_Background3);
				}
				if(changeBackground <= 0) { changeBackground = 300; }
			}
			
			spr_Background.setPosition(-81, -194);
			spr_Background.setSize(270, 270);
			spr_Background.draw(game.batch);
			
			if(player.Map.equals("MetroStation")){
				if(metroPosX < 150) { metroPosX++; }
				if(metroPosX >= 150) { metroPosX = -200; }
				spr_metro.setPosition(metroPosX, 16);
				spr_metro.setSize(140, 55);
				spr_metro.draw(game.batch);
			}
					
			
			//Background	
			if(player.Map.equals("Sewers")) {
				flipzone++;
				if (flipzone >= 0 && flipzone <= 20) {
					spr_Background.setPosition(-81, -194);
					spr_Background.setSize(270, 270);
					spr_Background.draw(game.batch);
				}
				if (flipzone >= 20 && flipzone <= 40) {
					spr_Background.setPosition(-81, -194);
					spr_Background.setSize(270, 270);
					spr_Background.draw(game.batch);
				}
				if (flipzone >= 40) { flipzone = 0; }
			}
			
			//Enviroment
			//OverBackground
			if(player.Map.equals("StreetsA")) {
				
				ShowOnlinePlayers();
				
				playerPosX = Float.parseFloat(player.PosX);
				playerPosY = Float.parseFloat(player.PosY);
				
				if(playerPosY >= -75) {
					ShowPlayer();
				}
				
				ShowCabs();
				spr_BackgroundOver.setPosition(-81, -194);
				spr_BackgroundOver.setSize(270, 270);
				spr_BackgroundOver.draw(game.batch);
				
				ShowNPCs();
				
				
				if(playerPosY < -75) {
					ShowPlayer();
				}		
				CheckMapEffect();
			}
			
			if(player.Map.equals("Forest")) {
				ShowOnlinePlayers();
				ShowPlayer();
				spr_BackgroundOver.setPosition(-81, -194);
				spr_BackgroundOver.setSize(270, 270);
				spr_BackgroundOver.draw(game.batch);
				
				CheckMapEffect();
			}
			
			if(!player.Map.equals("StreetsA") && !player.Map.equals("Forest"))
			{
				ShowOnlinePlayers();
				ShowPlayer();
			}
			
			ShowChats();

			//Show Mobs
			if(player.Map.equals("Sewers")){ ShowMobs(); }
			if(player.Map.equals("Forest")){ ShowMobs(); }
			if(player.Map.equals("Watercave")){ ShowMobs(); }
			if(player.Map.equals("Desert")){ ShowMobs(); }
			if(player.Map.equals("Vulcano")){ ShowMobs(); }
			if(player.Map.equals("Mines")){ ShowMobs(); }
			if(player.Map.equals("Snowpalace")){ ShowMobs(); }
			if(player.Map.equals("Swamp")){ ShowMobs(); }
		
			//UX
			spr_playerTag = gameControl.GetUX("playertag",cameraCoordsX, cameraCoordsY);
			spr_playerTag.draw(game.batch);
			
			spr_playerTagHair = gameControl.GetHairCharTag(player, cameraCoordsX, cameraCoordsY);
			spr_playerTagHair.draw(game.batch);
			
			
			font_master.getData().setScale(0.15f,0.26f);
			font_master.draw(game.batch, "X:" + player.PosX, cameraCoordsX - 98f, cameraCoordsY + 53.7f);
			font_master.draw(game.batch, "Y:" + player.PosY, cameraCoordsX - 78f, cameraCoordsY + 53.7f);
			font_master.draw(game.batch, player.party, cameraCoordsX - 58f, cameraCoordsY + 53.7f);
			
			spr_sit = gameControl.GetUX("btnsit", cameraCoordsX, cameraCoordsY);
			spr_sit.draw(game.batch);
			
			
			font_master.draw(game.batch, player.Name, cameraCoordsX - 89f, cameraCoordsY + 95f);
			font_master.draw(game.batch, String.valueOf(player.Hp + "/" + player.HpMax), cameraCoordsX - 85f, cameraCoordsY + 82f);
			font_master.draw(game.batch, String.valueOf(player.Mp + "/" + player.MpMax), cameraCoordsX - 85f, cameraCoordsY + 73.7f);
			font_master.draw(game.batch, String.valueOf(player.Level), cameraCoordsX - 88f, cameraCoordsY + 64f);
			font_master.draw(game.batch, gameControl.ExpPercent(player) + "%", cameraCoordsX - 72f, cameraCoordsY + 64f);
			
			if(controlstate.equals("Mobile")){
				spr_master = gameControl.GetUX("innerpad", cameraCoordsX, cameraCoordsY);
				spr_master.setPosition(cameraCoordsX + padmoveX,cameraCoordsY + padmoveY);
				spr_master.draw(game.batch);
			}
			
			//Checks
			if(player.Map.contains("MetroStation")) { CheckColisionMetroStation(); }
			if(player.Map.contains("StreetsA")) { CheckColisionStreetsA(); }
			if(player.Map.contains("Sewers")) { CheckColisionSewers(); }
			if(player.Map.contains("Forest")) { CheckColisionForest(); }
			if(player.Map.contains("Watercave")) { CheckColisionWatercave(); }
			if(player.Map.contains("Desert")) { CheckColisionDesert(); }
			if(player.Map.contains("Vulcano")) { CheckColisionVulcano(); }
			if(player.Map.contains("Mines")) { CheckColisionMines(); }
			if(player.Map.contains("Snowpalace")) { CheckColisionSnowpalace(); }
			if(player.Map.contains("Swamp")) { CheckColisionSwamp(); }
			
			ShowCards();
			ShowCardHotkeyA();
			ShowCardHotkeyB();
			CheckPlayerParty();
			CheckAutoAttack();
			CheckMobAutoAttack();
			ShowDamage();
			ShowSkill();
			CheckOnlineGrab();
			CheckBuffsToRemove();
			ShowBuffs(cameraCoordsX, cameraCoordsY);
			CheckEnergy();
			
			font_master.draw(game.batch, String.valueOf(defManual), cameraCoordsX + 67, cameraCoordsY - 30);
			//check def
			if(defManual > 0) {
				
				defManual--;
				if(defManual <= 0) { 
					defManual = 0; 
					defTrigged = false;
					gameControl.SetCharacterDefense(defTrigged);
				}
			}
			
			
			if(playerDead) { ShowPlayerDead(); }
			
			//Item Drop
			if(showDropMsg > 0) {
				spr_master = gameControl.GetUX("textbar", cameraCoordsX, cameraCoordsY);
				spr_master.setPosition(cameraCoordsX - 40, cameraCoordsY + 29);
				spr_master.setSize(80, 20);
				spr_master.draw(game.batch);
				font_master.setColor(Color.WHITE);
				font_master.getData().setScale(0.14f,0.23f);
				font_master.setUseIntegerPositions(false);
				font_master.draw(game.batch, itemdropname, cameraCoordsX - 38, cameraCoordsY + 42);
				showDropMsg--;
			}

			if(state.equals("Menu")){
				spr_master = gameControl.GetUX("menu", cameraCoordsX, cameraCoordsY);
				spr_master.draw(game.batch);
				
				//job
				font_master.draw(game.batch, String.valueOf(player.Job), cameraCoordsX - 80f, cameraCoordsY - 10f);
				//Status
				font_master.draw(game.batch, String.valueOf(player.Str), cameraCoordsX - 76f, cameraCoordsY - 50f);
				font_master.draw(game.batch, String.valueOf(player.Vit), cameraCoordsX - 62f, cameraCoordsY - 50f);
				font_master.draw(game.batch, String.valueOf(player.Agi), cameraCoordsX - 48f, cameraCoordsY - 50f);
				font_master.draw(game.batch, String.valueOf(player.Wis), cameraCoordsX - 34f, cameraCoordsY - 50f);
				font_master.draw(game.batch, String.valueOf(player.Dex), cameraCoordsX - 21f, cameraCoordsY - 50f);
				font_master.draw(game.batch, String.valueOf(player.Luk), cameraCoordsX - 7f, cameraCoordsY - 50f);
				font_master.draw(game.batch, String.valueOf(player.Res), cameraCoordsX - -7f, cameraCoordsY - 50f);
				
				font_master.draw(game.batch, String.valueOf(player.StrExtra), cameraCoordsX - 76f, cameraCoordsY - 64f);
				font_master.draw(game.batch, String.valueOf(player.VitExtra), cameraCoordsX - 62f, cameraCoordsY - 64f);
				font_master.draw(game.batch, String.valueOf(player.AgiExtra), cameraCoordsX - 48f, cameraCoordsY - 64f);
				font_master.draw(game.batch, String.valueOf(player.WisExtra), cameraCoordsX - 34f, cameraCoordsY - 64f);
				font_master.draw(game.batch, String.valueOf(player.DexExtra), cameraCoordsX - 21f, cameraCoordsY - 64f);
				font_master.draw(game.batch, String.valueOf(player.LukExtra), cameraCoordsX - 7f, cameraCoordsY - 64f);
				font_master.draw(game.batch, String.valueOf(player.ResExtra), cameraCoordsX - -7f, cameraCoordsY - 64f);
				
				font_master.draw(game.batch, String.valueOf(player.StatusPoint), cameraCoordsX - 43f, cameraCoordsY - 72f);
				
				playerExp = Float.parseFloat(player.Exp);
				font_master.draw(game.batch,"Dinheiro:" + String.valueOf(player.Money),cameraCoordsX - 10f, cameraCoordsY - 72f);		
				font_master.draw(game.batch,"Exp:" + Math.round(playerExp),cameraCoordsX + 20f, cameraCoordsY - 72f);			
				
				//CharacterShow
				spr_playerhair = gameControl.GetHairChar(player, "Show", cameraCoordsX, cameraCoordsY);
				spr_playerhair.draw(game.batch);
				
				spr_playertop = gameControl.GetTopChar(player, "Show", cameraCoordsX, cameraCoordsY);
				spr_playertop.draw(game.batch);
				
				spr_playerbottom = gameControl.GetBottomChar(player, "Show", cameraCoordsX, cameraCoordsY);
				spr_playerbottom.draw(game.batch);
				
				spr_playerfooter = gameControl.GetFooterChar(player, "Show", cameraCoordsX, cameraCoordsY);
				spr_playerfooter.draw(game.batch);
				
				if(!player.Hat.equals("none")) {
					spr_playerhatmenu = gameControl.GetHatChar(player,"",cameraCoordsX, cameraCoordsY);
					spr_playerhatmenu.setScale(0.5f, 0.7f);
					spr_playerhatmenu.setPosition(cameraCoordsX - 84, cameraCoordsY + 42);
					spr_playerhatmenu.draw(game.batch);
				}
				
				//Show Character
				//Itens Equipped
						
				spr_playerbottom = gameControl.GetItem(player.SetBottom, player);
				spr_playerbottom.setPosition(cameraCoordsX + 27, cameraCoordsY + 41);
				spr_playerbottom.setSize(13, 22);
				spr_playerbottom.draw(game.batch);
				
				spr_playertop = gameControl.GetItem(player.SetUpper, player);
				spr_playertop.setPosition(cameraCoordsX + 41, cameraCoordsY + 41);
				spr_playertop.setSize(13, 22);
				spr_playertop.draw(game.batch);
				
				spr_playerfooter = gameControl.GetItem(player.SetFooter, player);
				spr_playerfooter.setPosition(cameraCoordsX + 13, cameraCoordsY + 41);
				spr_playerfooter.setSize(13, 22);
				spr_playerfooter.draw(game.batch);
				
				spr_playerweapon = gameControl.GetItem(player.Weapon, player);
				spr_playerweapon.setPosition(cameraCoordsX + 54.4f, cameraCoordsY + 41);
				spr_playerweapon.setSize(13, 22);
				spr_playerweapon.draw(game.batch);
				
				if(!player.Hat.equals("none")) { 
					spr_playerhat = gameControl.GetHatItem(player,0,0);
					spr_playerhat.setPosition(cameraCoordsX + 67.8f, cameraCoordsY + 41);
					spr_playerhat.setSize(13, 22);
					spr_playerhat.draw(game.batch);
				}
				
				ShowBag();
				
				if(menuoption.equals("hotkey1")) { spr_master = gameControl.GetUX("hotkey1",cameraCoordsX + 16, cameraCoordsY - 23); spr_master.draw(game.batch); }
				if(menuoption.equals("hotkey2")) { spr_master = gameControl.GetUX("hotkey1",cameraCoordsX + 36, cameraCoordsY - 23); spr_master.draw(game.batch); }
				if(menuoption.equals("descartar")) { 
					spr_master = gameControl.GetUX("descartar",cameraCoordsX, cameraCoordsY); 
					spr_master.setPosition(cameraCoordsX + 50, cameraCoordsY - 56);
					spr_master.draw(game.batch); 
				}			
			}
			
			if(state.equals("Shop")) {
				
				if(shopname.equals("refrishop")) {
					spr_shop = gameControl.GetShops("refrishop",cameraCoordsX, cameraCoordsY);
					spr_shop.draw(game.batch);		
					font_master.draw(game.batch, String.valueOf(player.Money), cameraCoordsX - 25, cameraCoordsY - 37);				
				}
				
				if(shopname.equals("weaponshop")) {
					spr_shop = gameControl.GetShops("weaponshop",cameraCoordsX, cameraCoordsY);
					spr_shop.draw(game.batch);		
					font_master.draw(game.batch, String.valueOf(player.Money), cameraCoordsX - 25, cameraCoordsY - 37);				
				}
				
				if(shopname.equals("jobmaster")) {
					spr_shop = gameControl.GetShops("jobmaster",cameraCoordsX, cameraCoordsY);
					spr_shop.draw(game.batch);									
				}
				
				if(shopname.equals("cristalized")) {
					spr_shop = gameControl.GetShops("cristalized",cameraCoordsX, cameraCoordsY);
					spr_shop.draw(game.batch);								
				}
				
				if(shopname.equals("lojaroupas1")) {
					spr_shop = gameControl.GetShops("lojaroupas1",cameraCoordsX, cameraCoordsY);
					spr_shop.draw(game.batch);								
				}
					
				if(!showbuymsg.equals("")) {
					showbuymsgtime--;
					font_master.draw(game.batch, showbuymsg, cameraCoordsX + 15, cameraCoordsY - 37);
					if(showbuymsgtime <= 0) {
						showbuymsg = "";
						showbuymsgtime = 2000;						
					}
				}
			}

			if(state.equals("DungeonSelect")) {
				spr_master = gameControl.GetUX("battlezoneC", cameraCoordsX, cameraCoordsY);
				spr_master.draw(game.batch);
			}
			
			
			if (state.equals("keyboard")) {
				spr_keyboard.setPosition(cameraCoordsX -100,cameraCoordsY -100);
				spr_keyboard.setSize(196, 198);
				spr_keyboard.draw(game.batch);

				font_master.getData().setScale(0.23f, 0.35f);
				font_master.setUseIntegerPositions(false);
				font_master.draw(game.batch, keyboardText, cameraCoordsX - 95, cameraCoordsY + 90);
			}
			
			//Mensagens Aviso 
			if (aviso) {
				spr_master = gameControl.GetUX("textbar", 0, 0);
				spr_master.setSize(90,20);
				spr_master.setPosition(-45, 5);
				spr_master.draw(game.batch);
				font_master.getData().setScale(0.12f, 0.19f);
				font_master.draw(game.batch, avisoMsg, -40, 18);
				avisoTimer++;
				if (avisoTimer > 100) {
					aviso = false;
					avisoTimer = 0;
				}
			}
			
			gameControl.UpdateControlPlayer(player);
				
			/*spr_testeDot.setPosition(cameraCoordsX - 61, cameraCoordsY - 12);
			spr_testeDot.setSize(1, 1);
			spr_testeDot.draw(game.batch);
		
			spr_testeDot.setPosition(cameraCoordsX - 47, cameraCoordsY - 32);  
			spr_testeDot.setSize(1, 1);
			spr_testeDot.draw(game.batch);*/
			
			int money = Integer.parseInt(player.Money);
			if(money > 3500) {
				player.Money = "3500";
			}
			
			game.batch.end();
		}
		
		
		public void ShowPlayer() {
			//Char
			player = gameControl.SetCharMov(player, player.breakwalk);
			
			CheckCasting();
			
			spr_playerbottom = gameControl.GetBottomChar(player, "no",0,0);
			spr_playerbottom.draw(game.batch);
							
			spr_playertop = gameControl.GetTopChar(player, "no", 0,0);
			spr_playertop.draw(game.batch);
			
			spr_playerhair = gameControl.GetHairChar(player, "no",0,0);
			spr_playerhair.draw(game.batch);
			
			spr_playerfooter = gameControl.GetFooterChar(player, "no",0,0);
			spr_playerfooter.draw(game.batch);
			
			if(!player.Hat.equals("none")) {
				spr_playerhat = gameControl.GetHatChar(player,"",0,0);
				spr_playerhat.draw(game.batch);
			}
			
			if(player.playerInBattle.equals("yes") || player.playerInCast.equals("yes") || player.playerInAttack.equals("yes")) {
				if(!defTrigged) {
					spr_playerweapon = gameControl.SetWeapon(player);
					spr_playerweapon.draw(game.batch);
				}
			}
		}
		
		public void CheckEnergy() {
			int stamina = Integer.parseInt(player.Stamina);
			int staminaMax = Integer.parseInt(player.StaminaMax);
			
			backEnergy--;
			if(backEnergy < 0) { backEnergy = 3;  }
			
			if(backEnergy > 1) {		
				stamina = stamina - 1;
				if(stamina < 0) { stamina = 0; }
				player.Stamina = String.valueOf(stamina);
			}
			
			if (stamina > 0.8 * staminaMax) {
				spr_master = gameControl.GetUX("energia3", cameraCoordsX, cameraCoordsY);
				spr_master.setSize(10, 22);
				spr_master.setPosition(cameraCoordsX + 82, cameraCoordsY + 30);
				spr_master.draw(game.batch);
			}

			if ((stamina > 0.3 * staminaMax) && (stamina < 0.8 * staminaMax)) {
				spr_master = gameControl.GetUX("energia2", cameraCoordsX, cameraCoordsY);
				spr_master.setSize(10, 22);
				spr_master.setPosition(cameraCoordsX + 82, cameraCoordsY + 30);
				spr_master.draw(game.batch);
			}

			if ((stamina > 0.0 * staminaMax) && (stamina < 0.3 * staminaMax)) {
				spr_master = gameControl.GetUX("energia1", cameraCoordsX, cameraCoordsY);
				spr_master.setSize(10, 22);
				spr_master.setPosition(cameraCoordsX + 82, cameraCoordsY + 30);
				spr_master.draw(game.batch);
			}

			if ((stamina <= 0.0)) {
				spr_master = gameControl.GetUX("energia4", cameraCoordsX, cameraCoordsY);
				spr_master.setSize(10, 22);
				spr_master.setPosition(cameraCoordsX + 82, cameraCoordsY + 30);
				spr_master.draw(game.batch);
			}
		}
		
		public void CheckMapEffect() {
			daytime++;
			
			if(player.Map.equals("Forest") || player.Map.equals("Swamp") || player.Map.equals("StreetsA")) {		
				//Tarde
					if(daytime > 10000 && daytime < 20000) {
						spr_master.setTexture(tex_afternoon);
						spr_master.setPosition(cameraCoordsX- 100, cameraCoordsY - 100);
						spr_master.setSize(200, 200);
						spr_master.setColor(1, 1, 1, 0.2f); // 0.5f is 50% transparency
						spr_master.draw(game.batch);
					}
					//Noite
					if(daytime > 20000 && daytime < 30000) {
						spr_master.setTexture(tex_night);
						spr_master.setPosition(cameraCoordsX- 100, cameraCoordsY - 100);
						spr_master.setSize(200, 200);
						spr_master.setColor(1, 1, 1, 0.3f); // 0.5f is 50% transparency
						spr_master.draw(game.batch);
					}
					
					if(daytime > 30000) {
						daytime = 0;
					}
			}
			
			if(player.Map.equals("Forest") || player.Map.equals("Swamp") || player.Map.equals("StreetsA")) { 
			//Climatic
			climaticTimer--;
			if(climaticTimer <= 0) {
				climatic = randnumber.nextInt(1000);
				climaticTimer = 10000;
			}
			
			//Chuva
			if(climatic >= 0 && climatic <= 100) {
				climaticEffectTimer++;
				if(climaticEffectTimer >= 0 && climaticEffectTimer <= 10) {
					spr_rain.setTexture(tex_rain1);
					spr_rain.setPosition(cameraCoordsX - 100, cameraCoordsY - 100);
					spr_rain.setSize(200, 200);
					spr_rain.draw(game.batch);
				}
				if(climaticEffectTimer >= 10 && climaticEffectTimer <= 20) {
					spr_rain.setTexture(tex_rain2);
					spr_rain.setPosition(cameraCoordsX- 100, cameraCoordsY - 100);
					spr_rain.setSize(200, 200);
					spr_rain.draw(game.batch);				
				}
				if(climaticEffectTimer >= 20 && climaticEffectTimer <= 30) {
					spr_rain.setTexture(tex_rain3);
					spr_rain.setPosition(cameraCoordsX- 100, cameraCoordsY - 100);
					spr_rain.setSize(200, 200);
					spr_rain.draw(game.batch);
				}
				if(climaticEffectTimer > 30) {
					climaticEffectTimer = 1;
				}			
			}
			
			//Neve
			if(climatic >= 100 && climatic <= 200) {
				climaticEffectTimer++;
				if(climaticEffectTimer >= 0 && climaticEffectTimer <= 25) {
					spr_rain.setTexture(tex_snow1);
					spr_rain.setPosition(cameraCoordsX - 100, cameraCoordsY - 100);
					spr_rain.setSize(200, 200);
					spr_rain.draw(game.batch);
				}
				if(climaticEffectTimer >= 25 && climaticEffectTimer <= 50) {
					spr_rain.setTexture(tex_snow2);
					spr_rain.setPosition(cameraCoordsX- 100, cameraCoordsY - 100);
					spr_rain.setSize(200, 200);
					spr_rain.draw(game.batch);				
				}
				if(climaticEffectTimer >= 50 && climaticEffectTimer <= 75) {
					spr_rain.setTexture(tex_snow3);
					spr_rain.setPosition(cameraCoordsX- 100, cameraCoordsY - 100);
					spr_rain.setSize(200, 200);
					spr_rain.draw(game.batch);
				}
				if(climaticEffectTimer > 75) {
					climaticEffectTimer = 1;
				}			
			}
			
			}	
		}
		
		public void ShowCabs() {
			//Taxi
			taxpathleft--;
			taxpathright++;
			if(taxpathleft < -200) { taxpathleft = 185; }
			if(taxpathright > 200) { taxpathright = -130; }
			spr_taxi1.setPosition(taxpathleft, -70); 
			spr_taxi1.setSize(50, 40);
			spr_taxi1.draw(game.batch);
			
			spr_taxi2.setPosition(taxpathright, -102);
			spr_taxi2.setSize(50, 40);
			spr_taxi2.draw(game.batch);
		}
		
		public void CheckCasting() {
			if(player.playerInCast.equals("yes")) {			
				
				castFrame--; 
				float posX = Float.parseFloat(player.PosX);
				float posY = Float.parseFloat(player.PosY);
				
				if(castFrame >= 0 && castFrame <= 10) { 
					spr_castEffect = gameControl.GetEffectCasting("casteffect1",15,15);
					spr_castEffect.setPosition(posX - 8, posY - 8);
					spr_castEffect.draw(game.batch);
				}
				if(castFrame >= 10 && castFrame <= 20) { 
					spr_castEffect = gameControl.GetEffectCasting("casteffect2",15,15); 
					spr_castEffect.setPosition(posX - 8, posY - 8);
					spr_castEffect.draw(game.batch);
				}
				if(castFrame >= 20 && castFrame <= 30) { 
					spr_castEffect = gameControl.GetEffectCasting("casteffect3",15,15);  
					spr_castEffect.setPosition(posX - 8, posY - 8);
					spr_castEffect.draw(game.batch);
				}
				if(castFrame <= 0) {
					castFrame = 30;
				}
				
				if(playerCastTime >= 0) {
					playerCastTime--;
				}
				else {
					CheckAreaRangedSkill();
				}
			}
		}
		
		public void ShowChats() {
			if (lstChats == null || lstChats.isEmpty()) {
				return;
			}
		
			font_master.setColor(Color.WHITE);
			font_master.getData().setScale(0.13f, 0.19f);
			font_master.setUseIntegerPositions(false);
			font_master.draw(game.batch, "Chat:", cameraCoordsX - 98, cameraCoordsY +44);
		
			if (lstChats.size() > 0 && lstChats.get(0) != null) {
				font_master.draw(game.batch, lstChats.get(0),cameraCoordsX - 98,cameraCoordsY +37);
			}
			if (lstChats.size() > 1 && lstChats.get(1) != null) {
				font_master.draw(game.batch, lstChats.get(1), cameraCoordsX - 98, cameraCoordsY +30);
			}
			if (lstChats.size() > 2 && lstChats.get(2) != null) {
				font_master.draw(game.batch, lstChats.get(2), cameraCoordsX - 98, cameraCoordsY +23);
			}
		}
		
		public void RecoverOnlinePlayers() {
			
			try {
				gameControl.SyncPlayers("SyncPlayers",player.AccountNumber,playernumString, new HttpCallback() {
				    @Override
				    public void onSuccess(String response) {
				    	if(response.contains("success")) {
				    		lstOnlinePlayers = gameControl.RecoverOnlineList();
				    	}
				    	else {
				    		//avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
				    		//aviso = true;
				    	}
				    }

				    @Override
				    public void onFailure(Throwable t) {
				       //System.out.println("Error: " + t.getMessage());
				       //avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
					   //aviso = true;
				    }
				});
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void RecoverOnlineChats() {
			try {
				gameControl.SyncChats("SyncChats",player.AccountNumber,playernumString, new HttpCallback() {
				    @Override
				    public void onSuccess(String response) {
				    	if(response.contains("success")) {
				    		lstChats = gameControl.RecoverOnlineChat();
				    	}
				    	else {
				    		//avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
				    		//aviso = true;
				    	}
				    }

				    @Override
				    public void onFailure(Throwable t) {
				       //System.out.println("Error: " + t.getMessage());
				       //avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
					   //aviso = true;
				    }
				});
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void SendMobData() {
			try {
				gameControl.SendMobData("UpdateMobData",player.AccountNumber,playernumString, new HttpCallback() {
				    @Override
				    public void onSuccess(String response) {
				    	if(response.contains("success")) {
				    		
				    	}
				    	else {
				    		//avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
				    		//aviso = true;
				    	}
				    }

				    @Override
				    public void onFailure(Throwable t) {
				       //System.out.println("Error: " + t.getMessage());
				       //avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
					   //aviso = true;
				    }
				});
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void RecoverOnlineMobs() {
			try {
				gameControl.SyncMobs("SyncMobs",player.AccountNumber,playernumString, new HttpCallback() {
				    @Override
				    public void onSuccess(String response) {
				    	if(response.contains("success")) {
				    		listMonsters = gameControl.RecoverMonsterList();
				    	}
				    	else {
				    		//avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
				    		//aviso = true;
				    	}
				    }

				    @Override
				    public void onFailure(Throwable t) {
				       //System.out.println("Error: " + t.getMessage());
				       //avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
					   //aviso = true;
				    }
				});
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void ShowOnlinePlayers() {
			if(lstOnlinePlayers.size() > 0) {
				font_master.draw(game.batch, "Online:" + lstOnlinePlayers.size(), cameraCoordsX + 75, cameraCoordsY + 82);
				for(int i = 0; i < lstOnlinePlayers.size(); i++) {
					if(player.Map.equals(lstOnlinePlayers.get(i).Map)) {
						spr_playerHairOnline = gameControl.GetHairChar(lstOnlinePlayers.get(i), "no",0,0);
						spr_playerHairOnline.draw(game.batch);
						
						spr_playerFooterOnline = gameControl.GetFooterChar(lstOnlinePlayers.get(i), "no",0,0);
						spr_playerFooterOnline.draw(game.batch);
						
						spr_playerBottomOnline = gameControl.GetBottomChar(lstOnlinePlayers.get(i), "no",0,0);
						spr_playerBottomOnline.draw(game.batch);
						
						spr_playerTopOnline = gameControl.GetTopChar(lstOnlinePlayers.get(i), "no", 0,0);
						spr_playerTopOnline.draw(game.batch);
						
						spr_playerHatOnline = gameControl.GetHatChar(lstOnlinePlayers.get(i), "no", 0,0);
						spr_playerHatOnline.draw(game.batch);
						
						font_master.draw(game.batch, lstOnlinePlayers.get(i).Name, Float.parseFloat(lstOnlinePlayers.get(i).PosX) - 4, Float.parseFloat(lstOnlinePlayers.get(i).PosY) - 2);
					}
				}
			}
		}
		
		public void ShowDamage() {
			
			if(listDamage.size() == 0) {
				return;
			}
			
			for(int i = 0; i < listDamage.size(); i++) {
				listDamage.get(i).DamagePosY = listDamage.get(i).DamagePosY + 0.4f;
				listDamage.get(i).DamageTime = listDamage.get(i).DamageTime - 1;
								
				font_master.getData().setScale(0.30f,0.35f);
				font_master.setUseIntegerPositions(false);
				if(listDamage.get(i).DamageType.equals("mob")) { font_master.setColor(Color.YELLOW); }
				if(listDamage.get(i).DamageType.equals("player")) { font_master.setColor(Color.RED); }
				if(listDamage.get(i).DamageType.equals("heal")) { font_master.setColor(Color.GREEN); }
				
				int stamina = Integer.parseInt(player.Stamina);
				if(stamina <= 0) { font_master.draw(game.batch, "10", listDamage.get(i).DamagePosX, listDamage.get(i).DamagePosY); }
				if(stamina > 0) { font_master.draw(game.batch, String.valueOf(listDamage.get(i).DamageValue), listDamage.get(i).DamagePosX, listDamage.get(i).DamagePosY); }
				
				
				if(listDamage.get(i).DamageTime < 0) {
					listDamage.remove(listDamage.get(i));
				}

				font_master.setColor(Color.WHITE);
			}
		}
		
		public void ShowNPCs() {
			//NPCs
			if(player.Map.equals("StreetsA")) {
				spr_npc = gameControl.GetNPC("JobMaster", 0);
				spr_npc.draw(game.batch);
				
				spr_npc = gameControl.GetNPC("CrystalTrader", 0);
				spr_npc.draw(game.batch);
				
				spr_npc = gameControl.GetNPC("DungeonMaster", 0);
				spr_npc.draw(game.batch);
				
				spr_npc = gameControl.GetNPC("ExpGiver", 0);
				spr_npc.draw(game.batch);

				spr_master = gameControl.GetUX("textbar", 0, 0);
				spr_master.setSize(20,10);
				spr_master.setPosition(102, -90);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.GetUX("textbar", 0, 0);
				spr_master.setSize(20,10);
				spr_master.setPosition(-8, -88.5f);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.GetUX("textbar", 0, 0);
				spr_master.setSize(26,10);
				spr_master.setPosition(2, 4);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.GetUX("textbar", 0, 0);
				spr_master.setSize(37,10);
				spr_master.setPosition(85.5f, 4);
				spr_master.draw(game.batch);

				font_master.draw(game.batch, "Arenas", 105, -81);
				font_master.draw(game.batch, "Doadora", -7f, -80f);
				font_master.draw(game.batch, "Cristalizador", 2, 12);
				font_master.draw(game.batch, "Mestre de Classes", 85.5f, 12);
				
				npcMobY = npcMobY - 0.4f;
				npcMobX = npcMobX - 0.4f;
				npcMobXReverse = npcMobXReverse + 0.4f;
				framecountnpc--;
				
				if(framenpc == 1 && framecountnpc <= 0) { framenpc = 2; framecountnpc = 15; }
				if(framenpc == 2 && framecountnpc <= 0) { framenpc = 3; framecountnpc = 15; }
				if(framenpc == 3 && framecountnpc <= 0) { framenpc = 1; framecountnpc = 15; }
				
				spr_npc = gameControl.GetNPC("NPCW", framenpc);
				spr_npc.setPosition(91, npcMobY);
				spr_npc.draw(game.batch);
				if(npcMobY < -240) { npcMobY = 80; }
				
				spr_npc = gameControl.GetNPC("NPCO", framenpc);
				spr_npc.setPosition(15, npcMobY - 40);
				spr_npc.draw(game.batch);
				if(npcMobY < -240) { npcMobY = 80; }
				
				spr_npc = gameControl.GetNPC("NPCC", framenpc);
				spr_npc.setPosition(npcMobX, -132.f);
				spr_npc.draw(game.batch);
				if(npcMobX < -90) { npcMobX = 190; }
				
				spr_npc = gameControl.GetNPC("NPCE", framenpc);
				spr_npc.setPosition(npcMobXReverse, -134.f);
				spr_npc.draw(game.batch);
				if(npcMobXReverse > 190) { npcMobXReverse = -90; }
				
			}
		}
		
		public void ShowPlayerDead() {
			countDead--;
			
			player.Target = "none";
			player.playerInBattle = "no";
			player.playerInAttack = "no";
			player.playerInCast = "no";
			autoattack = false;
			spr_master = gameControl.GetUX("textbar", 0, 0);
			spr_master.setPosition(cameraCoordsX -32f,cameraCoordsY -10);
			spr_master.setSize(60, 30);
			spr_master.draw(game.batch);
			font_master.getData().setScale(0.10f,0.15f);
			font_master.setUseIntegerPositions(false);
			font_master.draw(game.batch, "Voce morreu, retornando...",cameraCoordsX - 28,cameraCoordsY + 8);
			
			if(countDead <= 0) {
				player.Hp = String.valueOf("10");
				player.Mp = String.valueOf("10");
				player.Map = "MetroStation";
				player.PosX = String.valueOf(53);
				player.PosY = String.valueOf(-1);
				//gameControl.SaveData(player);
				this.screen.screenSwitch("LoadingScreen","",playernum);
			}
		}
		
		public void CheckAutoAttack() {
			float playerposX = Float.parseFloat(player.PosX);
			float playerposY = Float.parseFloat(player.PosY);
			
			int AtkTimer = Integer.parseInt(player.AtkTimer);
			int AtkTimerMax = Integer.parseInt(player.AtkTimerMax);
			int Atk = Integer.parseInt(player.Atk);
			
			int Dex = Integer.parseInt(player.Dex);
			int Str = Integer.parseInt(player.Str);
			int StrExtra = Integer.parseInt(player.StrExtra);
			
			int Stamina = Integer.parseInt(player.Stamina);
			boolean checkRange = false;
			
			
			if(player.Map.equals("Sewers") || player.Map.equals("Forest") 
					|| player.Map.equals("Watercave") || player.Map.equals("Desert")
					|| player.Map.equals("Vulcano") || player.Map.equals("Mines")
					|| player.Map.equals("Snowpalace") || player.Map.equals("Swamp")
					|| player.Map.equals("Tower")
					
					&& autoattack) {
				for(int i = 0; i < listMonsters.size(); i++) {
					
					if(player.Target.equals(listMonsters.get(i).MobID)) {
						
						if((listMonsters.get(i).MobPosX + 5) > (playerposX - 80) && (listMonsters.get(i).MobPosX + 5) < (playerposX + 80)
								   && (listMonsters.get(i).MobPosY + 7) > (playerposY - 80) && (listMonsters.get(i).MobPosY + 7) < (playerposY + 80) && player.Job.equals("Atirador")) {
							checkRange = true;
						}
						
						
						if((listMonsters.get(i).MobPosX + 5) > (playerposX - 15) && (listMonsters.get(i).MobPosX + 5) < (playerposX + 15)
						   && (listMonsters.get(i).MobPosY + 7) > (playerposY - 18) && (listMonsters.get(i).MobPosY + 7) < (playerposY + 40) && !player.Job.equals("Atirador"))	
							{ 
							 checkRange = true;
							}
					
							
							if(checkRange) {		
							player.playerInBattle = "yes";
							if(!defTrigged) {
							AtkTimer--;
							player.AtkTimer = String.valueOf(AtkTimer);
							}
							
							if(AtkTimer <= 0) { 
								player.AtkTimer = player.AtkTimerMax;
								int atkweapon = CheckWeapon();
								int mobhp = listMonsters.get(i).MobHp; //CheckDamageDifer(listMonsters.get(i).MobHpMax, 1);
								int damagehit = Atk + atkweapon + Str + StrExtra;
								player.playerInAttack = "yes";
								player.AtkTimer = String.valueOf(AtkTimerMax);
								gameControl.SetAttackFrame();
								
								if(CheckMobEvade()) { 
									Damage damage = new Damage();
									damage.DamagePosX = listMonsters.get(i).MobPosX;
									damage.DamagePosY = listMonsters.get(i).MobPosY;
									damage.DamageTime = 100;
									damage.DamageType = "mob";
									damage.DamageValue = 0;
									listDamage.add(damage);
									return; 
								}
								
								String mobDeadStatus = "no";
								int st = Stamina;
								if(st > 0) { 
									mobhp = mobhp - damagehit;  
								} 
								else 
								{  
									mobhp = mobhp - 10; 
								}								
								
								
								if(mobhp <= 0) { 
									mobhp = 0; 
									mobDeadStatus = "yes";
									MobDead(i);
								}
								
								mobIndexAtk = i;
								
								//public void PushAttack(int mobhp, int mobIndex, String mobDeadStatus) {
								PushAttack(mobhp,i,mobDeadStatus);
								
								
								Damage damage = new Damage();
								damage.DamagePosX = listMonsters.get(i).MobPosX;
								damage.DamagePosY = listMonsters.get(i).MobPosY;
								damage.DamageTime = 100;
								damage.DamageType = "mob";
								damage.DamageValue = damagehit;
								listDamage.add(damage);											
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
			float playerposX = Float.parseFloat(player.PosX);
			float playerposY = Float.parseFloat(player.PosY);
			
			int Def = Integer.parseInt(player.Def);
			int Hp = Integer.parseInt(player.Hp);
			int mobAtk = 0;
				
			if(player.Map.equals("Sewers") || player.Map.equals("Forest") 
					|| player.Map.equals("Watercave") || player.Map.equals("Desert")
					|| player.Map.equals("Vulcano") || player.Map.equals("Mines")
					|| player.Map.equals("Snowpalace") || player.Map.equals("Swamp")
					|| player.Map.equals("Tower")) {
				
				for(int i = 0; i < listMonsters.size(); i++) {						
					if(listMonsters.get(i).MobTarget.equals(player.Name)) {
						if(playerposX > (listMonsters.get(i).MobPosX - 50) && playerposX < (listMonsters.get(i).MobPosX + 50)
							&& playerposY > (listMonsters.get(i).MobPosY - 50) && playerposY < (listMonsters.get(i).MobPosY + 50) && listMonsters.get(i).MobDead.equals("no")) {
							
								listMonsters.get(i).MobAtkTimer--;
								if(listMonsters.get(i).MobAtkTimer <= 0) {
									int mobluck = randnumber.nextInt(100);
									int checkDef = randnumber.nextInt(100);
									if(mobluck >= 0 && mobluck < 5) {
										mobAtk = listMonsters.get(i).MobAtk * 3;
										if(checkDef > 50) { mobAtk = mobAtk - Def; }									
										if(defTrigged) { mobAtk = mobAtk / 2; }
										if(mobAtk <= 0) { mobAtk = 1; }
										Hp = Hp - mobAtk;
										player.Hp = String.valueOf(Hp);
									}
									if(mobluck > 5 && mobluck < 10) {
										mobAtk = listMonsters.get(i).MobAtk * 2;
										if(checkDef > 50) { mobAtk = mobAtk - Def; }
										if(defTrigged) { mobAtk = mobAtk / 2; }
										if(mobAtk <= 0) { mobAtk = 1; }
										Hp = Hp - mobAtk;
										player.Hp = String.valueOf(Hp);
									}						
									if(mobluck > 20) {
									{
										mobAtk = listMonsters.get(i).MobAtk;
										if(checkDef > 50) { mobAtk = mobAtk - Def; }
										if(defTrigged) { mobAtk = mobAtk / 2; }
										if(mobAtk <= 0) { mobAtk = 1; }
										Hp = Hp - mobAtk;
										player.Hp = String.valueOf(Hp);
									}	
									
									listMonsters.get(i).MobAtkTimer = listMonsters.get(i).MobAtkTimerMax;
									Damage damage = new Damage();
									damage.DamagePosX = listMonsters.get(i).MobPosX;
									damage.DamagePosY = listMonsters.get(i).MobPosY;
									damage.DamageTime = 100;
									damage.DamageType = "player";
									damage.DamageValue = mobAtk;
									listDamage.add(damage);
								}	
								if(Hp <= 0) {
									playerDead = true;
								}
							}
						}				
					}
				}
			}
		}
		
		
		
		public void PushAttack(int mobhp, int mobIndex, String mobDeadStatus) {
			try {
				String mobhpsend = String.valueOf(mobhp);
				listMonsters.get(mobIndex).MobTarget = player.Name;
				gameControl.SendAtk("SendAtk",player.AccountNumber,playernumString,mobhpsend,player.Name,mobDeadStatus,listMonsters.get(mobIndex).MobID, new HttpCallback() {
				    @Override
				    public void onSuccess(String response) {
				    	if(response.contains("success")) {
				    		listMonsters.get(mobIndexAtk).MobHp = Integer.parseInt(mobhpsend);
				    	}
				    	else {
				    		//avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
				    		//aviso = true;
				    	}
				    }

				    @Override
				    public void onFailure(Throwable t) {
				       //System.out.println("Error: " + t.getMessage());
				       //avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
					   //aviso = true;
				    }
				});
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		
		public void CheckColisionMetroStation() {
			float posY = Float.parseFloat(player.PosY);
			float posX = Float.parseFloat(player.PosX);
			
			if(posX < -77) {
				player.breakwalk = "left";
			}				
			if(posX > 184.5f) {
				player.breakwalk = "right";
			}
			
			if(posY > 41) {
				player.breakwalk = "back";
			}
			if(posY < -192.5f) {
				player.breakwalk = "front";
			}
				
			
			if(player.Map.equals("MetroStation")) {
				if(posX > 127 && posX < 148 && posY> -192  && posY < - 140) {
					MapChange("StreetsA");
				}
			}
		}
		
		public void CheckColisionStreetsA() {
			float posY = Float.parseFloat(player.PosY);
			float posX = Float.parseFloat(player.PosX);
			
			if(posX < -77) {
				player.breakwalk = "left";
			}				
			if(posX > 184.5f) {
				player.breakwalk = "right";
			}
			
			if(posY > 41) {
				player.breakwalk = "back";
			}
			if(posY < -192.5f) {
				player.breakwalk = "front";
			}
		}
		
		public void CheckColisionSewers() {
			float posY = Float.parseFloat(player.PosY);
			float posX = Float.parseFloat(player.PosX);
			
			if(posX < -77) {
				player.breakwalk = "left";
			}				
			if(posX > 184.5f) {
				player.breakwalk = "right";
			}
			
			if(posY > 41) {
				player.breakwalk = "back";
			}
			if(posY < -192.5f) {
				player.breakwalk = "front";
			}
			
			
			if(player.Map.equals("Sewers")) {
				if(posX > 40f && posX < 53 && posY> 5 && posY < 21.5f) {
					MapChange("StreetsAFromSewers");
				}
			}
		}
		
		public void CheckColisionForest() {
			float posY = Float.parseFloat(player.PosY);
			float posX = Float.parseFloat(player.PosX);
			
			if(posX < -77) {
				player.breakwalk = "left";
			}				
			if(posX > 184.5f) {
				player.breakwalk = "right";
			}
			
			if(posY > 41) {
				player.breakwalk = "back";
			}
			if(posY < -192.5f) {
				player.breakwalk = "front";
			}
			
			
			if(player.Map.equals("Forest")) {
				if(posX > -7 && posX < 22.5f && posY> 31.5f && posY < 41.5f) {
					MapChange("StreetsAFromSewers");
				}
			}
		}
		
		public void CheckColisionWatercave() {
			float posY = Float.parseFloat(player.PosY);
			float posX = Float.parseFloat(player.PosX);
			
			if(posX < -77) {
				player.breakwalk = "left";
			}				
			if(posX > 184.5f) {
				player.breakwalk = "right";
			}
			
			if(posY > 41) {
				player.breakwalk = "back";
			}
			if(posY < -192.5f) {
				player.breakwalk = "front";
			}
			
			
			if(player.Map.equals("Watercave")) {
				if(posX > 31 && posX < 56 && posY> -0.5 && posY < 16) {
					MapChange("StreetsAFromSewers");
				}
			}
		}
		
		public void CheckColisionDesert() {
			float posY = Float.parseFloat(player.PosY);
			float posX = Float.parseFloat(player.PosX);
			
			if(posX < -77) {
				player.breakwalk = "left";
			}				
			if(posX > 184.5f) {
				player.breakwalk = "right";
			}
			
			if(posY > 41) {
				player.breakwalk = "back";
			}
			if(posY < -192.5f) {
				player.breakwalk = "front";
			}
			
			
			if(player.Map.equals("Desert")) {
				if(posX > 34 && posX < 63 && posY> 22.5f && posY < 41.5f) {
					MapChange("StreetsAFromSewers");
				}
			}
		}
		
		public void CheckColisionVulcano() {
			float posY = Float.parseFloat(player.PosY);
			float posX = Float.parseFloat(player.PosX);
			
			if(posX < -77) {
				player.breakwalk = "left";
			}				
			if(posX > 184.5f) {
				player.breakwalk = "right";
			}
			
			if(posY > 41) {
				player.breakwalk = "back";
			}
			if(posY < -192.5f) {
				player.breakwalk = "front";
			}
			
			
			if(player.Map.equals("Vulcano")) {
				if(posX > 10 && posX < 34.5f && posY> -5f && posY < 12) {
					MapChange("StreetsAFromSewers");
				}
			}
		}
		
		public void CheckColisionMines() {
			float posY = Float.parseFloat(player.PosY);
			float posX = Float.parseFloat(player.PosX);
			
			if(posX < -77) {
				player.breakwalk = "left";
			}				
			if(posX > 184.5f) {
				player.breakwalk = "right";
			}
			
			if(posY > 41) {
				player.breakwalk = "back";
			}
			if(posY < -192.5f) {
				player.breakwalk = "front";
			}
			
			
			if(player.Map.equals("Mines")) {
				if(posX > 165 && posX < 185 && posY> -12.5f && posY < 19.5f) {
					MapChange("StreetsAFromSewers");
				}
			}
		}
		
		public void CheckColisionSnowpalace() {
			float posY = Float.parseFloat(player.PosY);
			float posX = Float.parseFloat(player.PosX);
			
			if(posX < -77) {
				player.breakwalk = "left";
			}				
			if(posX > 184.5f) {
				player.breakwalk = "right";
			}
			
			if(posY > 41) {
				player.breakwalk = "back";
			}
			if(posY < -192.5f) {
				player.breakwalk = "front";
			}
			
			
			if(player.Map.equals("Snowpalace")) {
				if(posX > -6 && posX < 16.5f && posY> -193f && posY < -168f) {
					MapChange("StreetsAFromSewers");
				}
			}
		}
		
		public void CheckColisionSwamp() {
			float posY = Float.parseFloat(player.PosY);
			float posX = Float.parseFloat(player.PosX);
			
			if(posX < -77) {
				player.breakwalk = "left";
			}				
			if(posX > 184.5f) {
				player.breakwalk = "right";
			}
			
			if(posY > 41) {
				player.breakwalk = "back";
			}
			if(posY < -192.5f) {
				player.breakwalk = "front";
			}
			
			
			if(player.Map.equals("Swamp")) {
				if(posX > 14 && posX < 39 && posY> 36.5f && posY < 41.5f) {
					MapChange("StreetsAFromSewers");
				}
			}
		}
		
		
		public void ShowCards() {
			//Basic Cards
			if(!player.Map.equals("Sewers") && !player.Map.equals("Forest")){
				spr_master = gameControl.GetCard("cardaction");
				spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY  - 30);
				spr_master.draw(game.batch);
			}

			if(player.Map.equals("Sewers") || player.Map.equals("Forest") 
					|| player.Map.equals("Watercave") || player.Map.equals("Desert")
					|| player.Map.equals("Vulcano") || player.Map.equals("Mines")
					|| player.Map.equals("Snowpalace") || player.Map.equals("Swamp")
					|| player.Map.equals("Tower")) {
				if(autoattack){
					spr_master = gameControl.GetCard("cardactionON");
					spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY  - 30);
					spr_master.draw(game.batch);
					font_master.getData().setScale(0.15f,0.26f);
					font_master.draw(game.batch, player.AtkTimer, cameraCoordsX + 64, cameraCoordsY + 2);
				}
				else{
					spr_master = gameControl.GetCard("cardaction");
					spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY  - 30);
					spr_master.draw(game.batch);
				}
				
				spr_master = gameControl.GetCard("cardatk");
				spr_master.setPosition(cameraCoordsX + 47, cameraCoordsY - 60);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.GetCard("carddef");
				spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 60);
				spr_master.draw(game.batch);
			}
				
			spr_master = gameControl.GetCard("cardtarget");
			spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 60);
			spr_master.draw(game.batch);
			
			//Aprendiz Cards
			if(player.Job.equals("Aprendiz")) {
				spr_master = gameControl.GetCard("cardcutbreak");
				spr_master.setPosition(cameraCoordsX + 47, cameraCoordsY - 90);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.GetCard("cardrockbound");
				spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 90);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.GetCard("cardempty");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 90);
				spr_master.draw(game.batch);
			}
			
			//Espadachim Cards
			if(player.Job.equals("Espadachim")) {
				spr_master = gameControl.GetCard("cardslash");
				spr_master.setPosition(cameraCoordsX + 47, cameraCoordsY - 90);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.GetCard("cardhealthboost");
				spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 90);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.GetCard("cardempty");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 90);
				spr_master.draw(game.batch);
			}
			
			//Mago Cards
			if(player.Job.equals("Mago")) {
				spr_master = gameControl.GetCard("cardfireball");
				spr_master.setPosition(cameraCoordsX + 47, cameraCoordsY - 90);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.GetCard("cardicespike");
				spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 90);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.GetCard("cardempty");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 90);
				spr_master.draw(game.batch);
			}
			
			//Atirador Cards
			if(player.Job.equals("Atirador")) {
				spr_master = gameControl.GetCard("cardarrowrain");
				spr_master.setPosition(cameraCoordsX + 47, cameraCoordsY - 90);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.GetCard("cardperfectshot");
				spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 90);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.GetCard("cardempty");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 90);
				spr_master.draw(game.batch);
			}
			
			//Curandeiro Cards
			if(player.Job.equals("Curandeiro")) {
				spr_master = gameControl.GetCard("cardheal");
				spr_master.setPosition(cameraCoordsX + 47, cameraCoordsY - 90);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.GetCard("cardholy");
				spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 90);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.GetCard("cardempty");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 90);
				spr_master.draw(game.batch);
			}
			
			//Ladrao Cards
			if(player.Job.equals("Ladrao")) {
				spr_master = gameControl.GetCard("cardpoison");
				spr_master.setPosition(cameraCoordsX + 47, cameraCoordsY - 90);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.GetCard("cardsteal");
				spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 90);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.GetCard("cardempty");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 90);
				spr_master.draw(game.batch);
			}
		}
		
		public void ShowCardHotkeyA() {
			//Hotkey 1 / 2
			if(player.hotkey1.equals("none")) {
				spr_master = gameControl.GetCard("cardempty");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 30);
				spr_master.draw(game.batch);
			}
			
			// Hotkey 1
			if(player.hotkey1.equals("hpcan")) {
				spr_master = gameControl.GetCard("cardhp");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 30);
				spr_master.draw(game.batch);
			}

			if(player.hotkey1.equals("hpbigcan")) {
				spr_master = gameControl.GetCard("cardhpplus");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 30);
				spr_master.draw(game.batch);
			}

			if(player.hotkey1.equals("hpultracan")) {
				spr_master = gameControl.GetCard("cardhpultra");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 30);
				spr_master.draw(game.batch);
			}

			if(player.hotkey1.equals("mpcan")) {
				spr_master = gameControl.GetCard("cardmp");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 30);
				spr_master.draw(game.batch);
			}

			if(player.hotkey1.equals("mpbigcan")) {
				spr_master = gameControl.GetCard("cardmpplus");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 30);
				spr_master.draw(game.batch);
			}

			if(player.hotkey1.equals("mpultracan")) {
				spr_master = gameControl.GetCard("cardmpultra");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 30);
				spr_master.draw(game.batch);
			}
		}
		
		public void ShowCardHotkeyB() {
			// Hotkey 2
			if(player.hotkey2.equals("none")) {
				spr_master = gameControl.GetCard("cardempty");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY + 0);
				spr_master.draw(game.batch);
			}
			
			if(player.hotkey2.equals("hpcan")) {
				spr_master = gameControl.GetCard("cardhp");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY + 0);
				spr_master.draw(game.batch);
			}

			if(player.hotkey2.equals("hpbigcan")) {
				spr_master = gameControl.GetCard("cardhpplus");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY + 0);
				spr_master.draw(game.batch);
			}

			if(player.hotkey2.equals("hpultracan")) {
				spr_master = gameControl.GetCard("cardhpultra");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY + 0);
				spr_master.draw(game.batch);
			}

			if(player.hotkey2.equals("mpcan")) {
				spr_master = gameControl.GetCard("cardmp");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY + 0);
				spr_master.draw(game.batch);
			}

			if(player.hotkey2.equals("mpbigcan")) {
				spr_master = gameControl.GetCard("cardmpplus");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY + 0);
				spr_master.draw(game.batch);
			}

			if(player.hotkey2.equals("mpultracan")) {
				spr_master = gameControl.GetCard("cardmpultra");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY + 0);
				spr_master.draw(game.batch);
			}
				
		}
		
		public void ShowBag() {
			//Common Itens
			for (int i = 0; i < 16; i++) {
				spr_item = ShowItem(i);
				if(spr_item != null) {				
					spr_item.draw(game.batch);
				}
			}	
			for (int i = 0; i < 16; i++) {
				spr_item = ShowItem(i);
				if(spr_item != null) {				
					font_master.draw(game.batch, ShowQuantityItem(i), spr_item.getX() + 1,spr_item.getY() + 7);
				}
			}
					
			//Crystal Itens    
			//slot 1
			if(!player.Crystal1.equals("none")) {
				spr_item = gameControl.GetItem(player.Crystal1, player);
				spr_item.setPosition(cameraCoordsX + 13.3f,cameraCoordsY + 2);
				spr_item.setSize(13, 23);
				spr_item.draw(game.batch);
			}
			
			if(!player.Crystal2.equals("none")) {
				spr_item = gameControl.GetItem(player.Crystal2, player);
				spr_item.setPosition(cameraCoordsX + 27.2f,cameraCoordsY + 2);
				spr_item.setSize(13, 23);
				spr_item.draw(game.batch); 
			}
			
			//slot 3
			if(!player.Crystal3.equals("none")) {
				spr_item = gameControl.GetItem(player.Crystal3, player);
				spr_item.setPosition(cameraCoordsX + 41.1f,cameraCoordsY + 2);
				spr_item.setSize(13, 23);
				spr_item.draw(game.batch); 
			}
			
			//slot 4
			if(!player.Crystal4.equals("none")) {
				spr_item = gameControl.GetItem(player.Crystal4, player);
				spr_item.setPosition(cameraCoordsX + 54.7f,cameraCoordsY + 2);
				spr_item.setSize(13, 23);
				spr_item.draw(game.batch); 
			}
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
		
		public Sprite ShowItem(int num) {
			if(player.Itens.equals("")) { player.Itens = "[NONE]"; return spr_master; } 
			String[] lstItem = player.Itens.split("-");
			String[] itemSplit;
			String item;
			
			item = lstItem[num];
			if(!item.equals("[NONE]")) {
				itemSplit = item.split("#");
				item = itemSplit[0].replace("[", "");
				spr_item = gameControl.GetItem(item, player);
				
				
				if(num == 0){ spr_item.setPosition(cameraCoordsX - 44.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 1){ spr_item.setPosition(cameraCoordsX - 30.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 2){ spr_item.setPosition(cameraCoordsX - 16.5f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 3){ spr_item.setPosition(cameraCoordsX - 2.6f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 4){ spr_item.setPosition(cameraCoordsX - 44.3f,cameraCoordsY + 17.7f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 5){ spr_item.setPosition(cameraCoordsX - 30.3f,cameraCoordsY + 17.7f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 6){ spr_item.setPosition(cameraCoordsX - 16.5f,cameraCoordsY + 17.7f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 7){ spr_item.setPosition(cameraCoordsX - 2.6f,cameraCoordsY + 17.7f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 8){ spr_item.setPosition(cameraCoordsX - 44.3f,cameraCoordsY -5.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 9){ spr_item.setPosition(cameraCoordsX - 30.3f,cameraCoordsY -5.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 10){ spr_item.setPosition(cameraCoordsX - 16.5f,cameraCoordsY -5.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 11){ spr_item.setPosition(cameraCoordsX - 2.6f,cameraCoordsY -5.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 12){ spr_item.setPosition(cameraCoordsX - 44.3f,cameraCoordsY - 29.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 13){ spr_item.setPosition(cameraCoordsX - 30.3f,cameraCoordsY - 29.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 14){ spr_item.setPosition(cameraCoordsX - 16.5f,cameraCoordsY - 29.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 15){ spr_item.setPosition(cameraCoordsX - 2.6f,cameraCoordsY - 29.6f); spr_item.setSize(13, 23); return spr_item;}			
			}
				
			return spr_item;
		}
		
		public void CheckAreaRangedSkill() {
			
			String mobDeadStatus = "";
			
			float playerPosX = Float.parseFloat(player.PosX);
			float playerPosY = Float.parseFloat(player.PosY);
			
			float mobPosX = 0;
			float mobPosY = 0;
			
			int Hp = Integer.parseInt(player.Hp);
			int HpMax = Integer.parseInt(player.HpMax);
			
			int Mp = Integer.parseInt(player.Mp);
			int MpMax = Integer.parseInt(player.MpMax);
			
			int Atk = Integer.parseInt(player.Atk);		
			int Str = Integer.parseInt(player.Str);
			int Vit = Integer.parseInt(player.Vit);
			int Agi = Integer.parseInt(player.Agi);
			int Dex = Integer.parseInt(player.Dex);
			int Luk = Integer.parseInt(player.Luk);
			int Wis = Integer.parseInt(player.Wis);
			
			int StrExtra = Integer.parseInt(player.StrExtra);
			int VitExtra = Integer.parseInt(player.VitExtra);
			int AgiExtra = Integer.parseInt(player.AgiExtra);
			int DexExtra = Integer.parseInt(player.DexExtra);
			int LukExtra = Integer.parseInt(player.LukExtra);
			int WisExtra = Integer.parseInt(player.WisExtra);
			
			int stamina = Integer.parseInt(player.Stamina);
			
			int playerlevel = Integer.parseInt(player.Level);
			
			if(player.Map.equals("Sewers") || player.Map.equals("Forest") || player.Map.equals("Watercave")  
					|| player.Map.equals("Desert") || player.Map.equals("Vulcano") 
					|| player.Map.equals("Mines") || player.Map.equals("Snowpalace")
					|| player.Map.equals("Swamp") || player.Map.equals("Tower")
					&& autoattack) {
				for(int i = 0; i < listMonsters.size(); i++) {
					
					mobPosX = listMonsters.get(i).MobPosX;
					mobPosY = listMonsters.get(i).MobPosY;			
					mobDeadStatus = listMonsters.get(i).MobDead;
					
					//Close Ranged
					if(player.Target.equals(listMonsters.get(i).MobID) && !rangedAttack) {		 
						if((mobPosX + 5) > (playerPosX - 5) && (mobPosX + 5) < (playerPosX + 15)
						   && (mobPosY + 5) > (playerPosY - 7) && (mobPosY + 5) < (playerPosY + 18)) {
							player.playerInBattle = "yes";
							listMonsters.get(i).MobTarget = player.Name;				
							//Aprendiz
							if(skillname.equals("tripleattack")) {
								int atkweapon = CheckWeapon();
								int totaldmg = (Atk * 3) + ((Str * 2) + atkweapon) + (playerlevel * 10) + (StrExtra * 2);
								if(stamina <= 0) { totaldmg = 10; }
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "tripleattack";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX +5;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY + 5;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX + 5;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY + 5;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { mobHP = 0; mobDeadStatus = "yes"; MobDead(i); }	
								PushAttack(mobHP,i,mobDeadStatus);
							}
							if(skillname.equals("flysword")) {
								int atkweapon = CheckWeapon();
								int totaldmg = Atk + ((Str * 3) + (Agi * 2) + atkweapon) + 50 + (playerlevel * 10) + (StrExtra * 2) + (AgiExtra * 2);
								if(stamina <= 0) { totaldmg = 10; }
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "flysword";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX - 20;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY - 20;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { mobHP = 0; mobDeadStatus = "yes"; MobDead(i); }	
								PushAttack(mobHP,i,mobDeadStatus);
							}
							if(skillname.equals("poisonhit")) {
								int atkweapon = CheckWeapon();
								int totaldmg = Atk + ((Luk * 2)+ (Str * 2) + atkweapon) + 30 + (playerlevel * 10) + (LukExtra * 2) + (StrExtra * 2);
								if(stamina <= 0) { totaldmg = 10; }
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								listMonsters.get(i).MobHp = mobHP;
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "poisonhit";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX - 30;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY - 20;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { mobHP = 0; mobDeadStatus = "yes"; MobDead(i); }	
								PushAttack(mobHP,i,mobDeadStatus);
							}
							if(skillname.equals("steal")) {
								showDropMsg = 100;
							    itemdropname = gameControl.ItemDrop(listMonsters.get(i).MobName);
								Skill skillInUse = new Skill();
								skillInUse.SkillName = "steal";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX- 20;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY- 20;
								skillInUse.SkillTime = 100;
								listSkills.add(skillInUse);	
							}
							
							if(skillname.equals("overpower")) {
								int atkweapon = CheckWeapon();
								int totaldmg = Atk + ((Vit * 3) + (Str * 5) + (Luk * 2) + atkweapon) + (VitExtra * 3) + (StrExtra * 2) + (LukExtra * 3);
								if(stamina <= 0) { totaldmg = 10; }
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "overpower";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { mobHP = 0; mobDeadStatus = "yes"; MobDead(i); }	
								PushAttack(mobHP,i,mobDeadStatus);
							}
						}
					}
					
					//Long Ranged
					if(rangedAttack) {	
					
						if(skillname.equals("heal")) {
							int healpoint = (Wis * 5) + (WisExtra * 3);
							if(stamina <= 0) { healpoint = 10; }
							Hp = Hp + healpoint;
							if(Hp > HpMax) { Hp = HpMax; }
							player.Hp = String.valueOf(Hp);
							rangedAttack = false; 
							skillEffect = true;
							Skill skillInUse = new Skill();
							Damage damageSkill = new Damage();
							skillInUse.SkillName = "heal";
							skillInUse.SkillPosX = playerPosX - 25;
							skillInUse.SkillPosY = playerPosY - 20;
							damageSkill.DamagePosX = playerPosX;
							damageSkill.DamagePosY = playerPosY;
							skillInUse.SkillTime = 100;
							damageSkill.DamageTime = 100;
							damageSkill.DamageType = "heal";
							damageSkill.DamageValue = healpoint;
							listSkills.add(skillInUse);	
							listDamage.add(damageSkill);
							player.playerInCast = "no";
							player.MagicSync = "heal%"+ healpoint;
							return;
						}
						
						if(skillname.equals("defboost")) { 
							gameControl.GiveBuff("defboost");
							player.MagicSync = "defboost %"+ 0;
							rangedAttack = false; 
							skillEffect = true;
							Skill skillInUse = new Skill();
							Damage damageSkill = new Damage();
							skillInUse.SkillName = "defboost";
							skillInUse.SkillPosX = Float.parseFloat(player.PosX);
							skillInUse.SkillPosY = Float.parseFloat(player.PosY);
							damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
							damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							damageSkill.DamageTime = 100;
							damageSkill.DamageType = "heal";
							damageSkill.DamageValue = 0;
							player.playerInCast = "no";
							return; 	
						}						
						if(skillname.equals("healthboost")) {
							gameControl.GiveBuff("healthboost");
							Skill skillInUse = new Skill();
							skillInUse.SkillName = "healthboost";
							skillInUse.SkillPosX = Float.parseFloat(player.PosX) - 18;
							skillInUse.SkillPosY = Float.parseFloat(player.PosY) - 3;
							skillInUse.SkillTime = 100;
							listSkills.add(skillInUse);					
							rangedAttack = false; 
							player.playerInCast = "no"; 
							return; 
						}	
						if(skillname.equals("perfectshot")) { 
							gameControl.GiveBuff("perfectshot");
							Skill skillInUse = new Skill();
							skillInUse.SkillName = "perfectshot";
							skillInUse.SkillPosX = Float.parseFloat(player.PosX) - 45;
							skillInUse.SkillPosY = Float.parseFloat(player.PosY) - 25;
							skillInUse.SkillTime = 100;
							listSkills.add(skillInUse);	
							player.MagicSync = "perfectshot %"+ 0;
							rangedAttack = false; 
							player.playerInCast = "no"; 
							return; 
						}
						if(skillname.equals("regen")) { 
							gameControl.GiveBuff("regen"); 
							rangedAttack = false; 
							skillEffect = true;
							Skill skillInUse = new Skill();
							Damage damageSkill = new Damage();
							skillInUse.SkillName = "regen";
							skillInUse.SkillPosX = Float.parseFloat(player.PosX);
							skillInUse.SkillPosY = Float.parseFloat(player.PosY);
							damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
							damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							damageSkill.DamageTime = 100;
							damageSkill.DamageType = "heal";
							damageSkill.DamageValue = 0;
							player.playerInCast = "no";
							return; 	
						}		
						if(skillname.equals("ironshield")) { 
							gameControl.GiveBuff("ironshield"); 
							rangedAttack = false; 
							skillEffect = true;
							Skill skillInUse = new Skill();
							Damage damageSkill = new Damage();
							skillInUse.SkillName = "ironshield";
							skillInUse.SkillPosX = Float.parseFloat(player.PosX);
							skillInUse.SkillPosY = Float.parseFloat(player.PosY);
							damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
							damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							damageSkill.DamageTime = 100;
							damageSkill.DamageType = "heal";
							damageSkill.DamageValue = 0;
							player.playerInCast = "no";
							return; 	
						}				
						if(skillname.equals("invisibility")) { 
							gameControl.GiveBuff("invisibility");
							rangedAttack = false; 
							skillEffect = true;
							Skill skillInUse = new Skill();
							Damage damageSkill = new Damage();
							skillInUse.SkillName = "invisibility";
							skillInUse.SkillPosX = Float.parseFloat(player.PosX);
							skillInUse.SkillPosY = Float.parseFloat(player.PosY);
							damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
							damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							damageSkill.DamageTime = 100;
							damageSkill.DamageType = "heal";
							damageSkill.DamageValue = 0;
							player.playerInCast = "no";
							return; 								
						}
						
						
						if(player.Target.equals(listMonsters.get(i).MobID) && listMonsters.get(i).MobDead.equals("no")) {
							player.playerInBattle = "yes";
							listMonsters.get(i).MobTarget = player.Name;
							
							if(skillname.equals("rockbound")) {
								int atkweapon = CheckWeapon();
								int totaldmg = Atk + ((Wis * 2) + (Str * 2) + 25) + (WisExtra * 2);
								if(stamina <= 0) { totaldmg = 10; }
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "rockbound";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { mobHP = 0; mobDeadStatus = "yes"; MobDead(i); }	
								PushAttack(mobHP,i,mobDeadStatus);
								player.playerInCast = "no";
								return;
							}
							
							if(skillname.equals("fireball")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((Wis * 2) + atkweapon) + 50 + (playerlevel * 10) + (WisExtra * 2);
								if(stamina <= 0) { totaldmg = 10; }
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								listMonsters.get(i).MobHp = mobHP;				
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "fireball";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX - 20;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY - 20;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { mobHP = 0; mobDeadStatus = "yes"; MobDead(i); }	
								PushAttack(mobHP,i,mobDeadStatus);
								player.playerInCast = "no";
								return;
							}
							
							if(skillname.equals("icecrystal")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((Wis * 6) + (Dex * 2) + atkweapon) + 100 + (playerlevel * 14) + (WisExtra * 3) + (DexExtra * 2);
								if(stamina <= 0) { totaldmg = 10; }
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "icecrystal";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX - 30;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY - 20;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 200;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { mobHP = 0; mobDeadStatus = "yes"; MobDead(i); }	
								PushAttack(mobHP,i,mobDeadStatus);
								player.playerInCast = "no";
								return;
							}												
							if(skillname.equals("thundercloud")) {
								int atkweapon = CheckWeapon();
								int totaldmg = Atk + ((Wis * 10) + (Agi * 2) + atkweapon) + (playerlevel * 10) + (WisExtra * 10);
								if(stamina <= 0) { totaldmg = 10; }
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "thundercloud";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { mobHP = 0; mobDeadStatus = "yes"; MobDead(i); }	
								PushAttack(mobHP,i,mobDeadStatus);
								player.playerInCast = "no";
								return;
							}
							
							if(skillname.equals("bulletrain")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((Dex * 2) + (Agi * 2) + 10) + 40 + (playerlevel * 10) + (DexExtra * 2) + (AgiExtra * 2);
								if(stamina <= 0) { totaldmg = 10; }
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "bulletrain";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX - 30;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY - 20;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { mobHP = 0; mobDeadStatus = "yes"; MobDead(i); }	
								PushAttack(mobHP,i,mobDeadStatus);
								player.playerInCast = "no";
								return;
							}						
							if(skillname.equals("holyprism")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((Luk * 2) + atkweapon) + 20 + (playerlevel * 10) + (AgiExtra * 2) + (StrExtra * 2);
								if(stamina <= 0) { totaldmg = 10; }
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "holyprism";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX - 30;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY - 20;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { mobHP = 0; mobDeadStatus = "yes"; MobDead(i); }	
								PushAttack(mobHP,i,mobDeadStatus);
								player.playerInCast = "no";
								return;
							}
							if(skillname.equals("mine")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((Dex * 2) + 10);
								if(stamina <= 0) { totaldmg = 10; }
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "mine";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { mobHP = 0; mobDeadStatus = "yes"; MobDead(i); }	
								PushAttack(mobHP,i,mobDeadStatus);
								player.playerInCast = "no";
								return;
							}							
						}
					}
				}		
			}
		}
		
		public void CheckOnlineGrab() {
			String onlineGrab = gameControl.GetOnlineGrab();
			if(!onlineGrab.equals("none") && grabStop == 0) {
				String[] lstGrab = onlineGrab.split("%");
				//Case heal
				if(lstGrab[0].equals("heal")) {
					int playerHP = Integer.parseInt(player.Hp);
					int playerHPMax = Integer.parseInt(player.HpMax);
					int healGrab = Integer.parseInt(lstGrab[1]);
					playerHP = playerHP + healGrab;
					if(playerHP > playerHPMax) { playerHP = playerHPMax; }
					player.Hp = String.valueOf(playerHP);
					grabStop = 50;
				}
				else {
					//Case Buffs
					gameControl.GiveBuff(lstGrab[0]);
				}	
			}
			
			if(grabStop > 0) {
				grabStop--;
				if(grabStop <= 0) {
					grabStop = 0; onlineGrab = "none"; gameControl.SetOnlineGrab();					
				}
			}
			
			if(!player.MagicSync.equals("none")) {
				grabTime--;
				if(grabTime <= 0) { grabTime = 40; player.MagicSync = "none"; }
			}
			
		}
		
		public void ShowBuffs(float cameraCoordsX, float cameraCoordsY) {
			int buffTimeA = Integer.parseInt(player.BuffTimeA);
			int buffTimeB = Integer.parseInt(player.BuffTimeB);
			int buffTimeC = Integer.parseInt(player.BuffTimeC);
			
			if(!player.buffA.equals("none")) {
				if(player.buffA.equals("defboost")) { spr_buffA = gameControl.GetCard("cardboost"); }
				if(player.buffA.equals("ironshield")) { spr_buffA = gameControl.GetCard("cardironshield"); }
				if(player.buffA.equals("healthboost")) { spr_buffA = gameControl.GetCard("cardhealthboost"); }
				if(player.buffA.equals("berserk")) {  spr_buffA = gameControl.GetCard("cardberserk");}
				if(player.buffA.equals("regen")) { spr_buffA = gameControl.GetCard("cardregen"); }
				if(player.buffA.equals("invisibility")) { spr_buffA = gameControl.GetCard("cardinvisibility"); }
				if(player.buffA.equals("perfectshot")) { spr_buffA = gameControl.GetCard("cardperfectshot"); }
				if(player.buffA.equals("chipz")) { spr_buffA = gameControl.GetCard("cardchipz"); }
				if(player.buffA.equals("turkey")) { spr_buffA = gameControl.GetCard("cardturkey"); }
				if(player.buffA.equals("egg")) { spr_buffA = gameControl.GetCard("cardegg"); }
				spr_buffA.setSize(10, 20);
				spr_buffA.setPosition(cameraCoordsX - 45, cameraCoordsY + 60);
				spr_buffA.draw(game.batch);
				
				buffTimeA = buffTimeA - 1;
				if(buffTimeA <= 0) {
					gameControl.RemoveBuffs(player.buffA);
				}		
			}
			if(!player.buffB.equals("none")) {
				if(player.buffB.equals("defboost")) { spr_buffB = gameControl.GetCard("cardboost"); }
				if(player.buffB.equals("ironshield")) { spr_buffB = gameControl.GetCard("cardironshield"); }
				if(player.buffB.equals("healthboost")) { spr_buffB = gameControl.GetCard("cardhealthboost"); }
				if(player.buffB.equals("berserk")) {  spr_buffB = gameControl.GetCard("cardberserk");}
				if(player.buffB.equals("regen")) { spr_buffB = gameControl.GetCard("cardregen"); }
				if(player.buffB.equals("invisibility")) { spr_buffB = gameControl.GetCard("cardinvisibility"); }
				if(player.buffB.equals("perfectshot")) { spr_buffB = gameControl.GetCard("cardperfectshot"); }
				if(player.buffB.equals("chipz")) { spr_buffB = gameControl.GetCard("cardchipz"); }
				if(player.buffB.equals("turkey")) { spr_buffB = gameControl.GetCard("cardturkey"); }
				if(player.buffB.equals("egg")) { spr_buffB = gameControl.GetCard("cardegg"); }
				spr_buffB.setSize(10, 20);
				spr_buffB.setPosition(cameraCoordsX - 34, cameraCoordsY + 60);
				spr_buffB.draw(game.batch);
				
				buffTimeB = buffTimeB - 1;
				if(buffTimeB <= 0) {
					gameControl.RemoveBuffs(player.buffB);
				}
			}
			if(!player.buffC.equals("none")) {
				if(player.buffC.equals("defboost")) { spr_buffC = gameControl.GetCard("cardboost"); }
				if(player.buffC.equals("ironshield")) { spr_buffC = gameControl.GetCard("cardironshield"); }
				if(player.buffC.equals("healthboost")) { spr_buffC = gameControl.GetCard("cardhealthboost"); }
				if(player.buffC.equals("berserk")) {  spr_buffC = gameControl.GetCard("cardberserk");}
				if(player.buffC.equals("regen")) { spr_buffC = gameControl.GetCard("cardregen"); }
				if(player.buffC.equals("invisibility")) { spr_buffC = gameControl.GetCard("cardinvisibility"); }
				if(player.buffC.equals("lockshot")) { spr_buffC = gameControl.GetCard("cardlockshot"); }
				if(player.buffC.equals("perfectshot")) { spr_buffC = gameControl.GetCard("cardperfectshot"); }
				if(player.buffC.equals("chipz")) { spr_buffC = gameControl.GetCard("cardchipz"); }
				if(player.buffC.equals("turkey")) { spr_buffC = gameControl.GetCard("cardturkey"); }
				if(player.buffC.equals("egg")) { spr_buffC = gameControl.GetCard("cardegg"); }
				spr_buffC.setSize(10, 20);
				spr_buffC.setPosition(cameraCoordsX - 23, cameraCoordsY + 60);
				spr_buffC.draw(game.batch);
				
				buffTimeC = buffTimeC - 1;
				if(buffTimeC <= 0) {
					gameControl.RemoveBuffs(player.buffC);
				}
			}		
		}
		
		public void ShowSkill() {
			
			if(skillUsed > 0) {
				skillUsed--;
				if(skillUsed < 0) { skillUsed = 0; }
			}
			
			if(listSkills.size() == 0) {
				return;
			}
			
			for(int i = 0; i < listSkills.size(); i++) {
				
				int time = listSkills.get(i).SkillTime;
				listSkills.get(i).SkillTime = time - 1;
				
				if(listSkills.get(i).SkillTime >= 80 && listSkills.get(i).SkillTime <= 100) { 
					if(listSkills.get(i).SkillName.equals("tripleattack")) { spr_master = gameControl.GetSpriteSkill("tripleattack",1); }
					if(listSkills.get(i).SkillName.equals("steal")) { spr_master = gameControl.GetSpriteSkill("steal",1); }
					if(listSkills.get(i).SkillName.equals("soulclash")) { spr_master = gameControl.GetSpriteSkill("soulclash",1); }
					if(listSkills.get(i).SkillName.equals("ravenblade")) { spr_master = gameControl.GetSpriteSkill("ravenblade",1); }
					if(listSkills.get(i).SkillName.equals("ragebound")) { spr_master = gameControl.GetSpriteSkill("ragebound",1); }
					if(listSkills.get(i).SkillName.equals("thundercloud")) { spr_master = gameControl.GetSpriteSkill("thundercloud",1); }
					if(listSkills.get(i).SkillName.equals("perfectshot")) { spr_master = gameControl.GetSpriteSkill("perfectshot",1); }
					if(listSkills.get(i).SkillName.equals("mine")) { spr_master = gameControl.GetSpriteSkill("mine",1); }
					if(listSkills.get(i).SkillName.equals("overpower")) { spr_master = gameControl.GetSpriteSkill("overpower",1); }
					if(listSkills.get(i).SkillName.equals("poisonhit")) { spr_master = gameControl.GetSpriteSkill("poisonhit",1); }
					if(listSkills.get(i).SkillName.equals("precision")) { spr_master = gameControl.GetSpriteSkill("precision",1); }
					if(listSkills.get(i).SkillName.equals("protect")) { spr_master = gameControl.GetSpriteSkill("protect",1); }
					if(listSkills.get(i).SkillName.equals("healthboost")) { spr_master = gameControl.GetSpriteSkill("healthboost",1); }
					if(listSkills.get(i).SkillName.equals("holyprism")) { spr_master = gameControl.GetSpriteSkill("holyprism",1); }
					if(listSkills.get(i).SkillName.equals("icecrystal")) { spr_master = gameControl.GetSpriteSkill("icecrystal",1); }
					if(listSkills.get(i).SkillName.equals("impound")) { spr_master = gameControl.GetSpriteSkill("impound",1); }
					if(listSkills.get(i).SkillName.equals("invisibility")) { spr_master = gameControl.GetSpriteSkill("invisibility",1); }
					if(listSkills.get(i).SkillName.equals("ironshield")) { spr_master = gameControl.GetSpriteSkill("ironshield",1); }
					if(listSkills.get(i).SkillName.equals("doublehit")) { spr_master = gameControl.GetSpriteSkill("doublehit",1); }
					if(listSkills.get(i).SkillName.equals("fastshot")) { spr_master = gameControl.GetSpriteSkill("fastshot",1); }
					if(listSkills.get(i).SkillName.equals("fireball")) { spr_master = gameControl.GetSpriteSkill("fireball",1); }
					if(listSkills.get(i).SkillName.equals("flysword")) { spr_master = gameControl.GetSpriteSkill("flysword",1); }
					if(listSkills.get(i).SkillName.equals("heal")) { spr_master = gameControl.GetSpriteSkill("heal",1); }
					if(listSkills.get(i).SkillName.equals("defboost")) { spr_master = gameControl.GetSpriteSkill("defboost",1); }
					if(listSkills.get(i).SkillName.equals("berserk")) { spr_master = gameControl.GetSpriteSkill("berserk",1); }
					if(listSkills.get(i).SkillName.equals("bulletrain")) { spr_master = gameControl.GetSpriteSkill("bulletrain",1); }
					if(listSkills.get(i).SkillName.equals("dashkick")) { spr_master = gameControl.GetSpriteSkill("dashkick",1); }
					if(listSkills.get(i).SkillName.equals("regen")) { spr_master = gameControl.GetSpriteSkill("regen",1); }
					if(listSkills.get(i).SkillName.equals("rockbound")) { spr_master = gameControl.GetSpriteSkill("rockbound",1); }
					
					spr_master.setPosition(listSkills.get(i).SkillPosX -10, listSkills.get(i).SkillPosY);
					spr_master.setSize(100,100);
					
					if(listSkills.get(i).SkillName.equals("healthboost")) { spr_master.setSize(60,60); }
					if(listSkills.get(i).SkillName.equals("fireball")) { 
						spr_master.setSize(70,70); 
						float playerposx = Float.parseFloat(player.PosX);
						float playerposy = Float.parseFloat(player.PosY);
						spr_master.setPosition(playerposx - 20, playerposy - 10);
					}
					
					spr_master.draw(game.batch);
				}
				
				if(listSkills.get(i).SkillTime >= 60 && listSkills.get(i).SkillTime <= 80) { 
					if(listSkills.get(i).SkillName.equals("tripleattack")) { spr_master = gameControl.GetSpriteSkill("tripleattack",2); }
					if(listSkills.get(i).SkillName.equals("steal")) { spr_master = gameControl.GetSpriteSkill("steal",2); }
					if(listSkills.get(i).SkillName.equals("soulclash")) { spr_master = gameControl.GetSpriteSkill("soulclash",2); }
					if(listSkills.get(i).SkillName.equals("ravenblade")) { spr_master = gameControl.GetSpriteSkill("ravenblade",2); }
					if(listSkills.get(i).SkillName.equals("ragebound")) { spr_master = gameControl.GetSpriteSkill("ragebound",2); }
					if(listSkills.get(i).SkillName.equals("thundercloud")) { spr_master = gameControl.GetSpriteSkill("thundercloud",2); }
					if(listSkills.get(i).SkillName.equals("perfectshot")) { spr_master = gameControl.GetSpriteSkill("perfectshot",2); }
					if(listSkills.get(i).SkillName.equals("mine")) { spr_master = gameControl.GetSpriteSkill("mine",2); }
					if(listSkills.get(i).SkillName.equals("overpower")) { spr_master = gameControl.GetSpriteSkill("overpower",2); }
					if(listSkills.get(i).SkillName.equals("poisonhit")) { spr_master = gameControl.GetSpriteSkill("poisonhit",2); }
					if(listSkills.get(i).SkillName.equals("precision")) { spr_master = gameControl.GetSpriteSkill("precision",2); }
					if(listSkills.get(i).SkillName.equals("protect")) { spr_master = gameControl.GetSpriteSkill("protect",2); }
					if(listSkills.get(i).SkillName.equals("healthboost")) { spr_master = gameControl.GetSpriteSkill("healthboost",2); }
					if(listSkills.get(i).SkillName.equals("holyprism")) { spr_master = gameControl.GetSpriteSkill("holyprism",2); }
					if(listSkills.get(i).SkillName.equals("icecrystal")) { spr_master = gameControl.GetSpriteSkill("icecrystal",2); }
					if(listSkills.get(i).SkillName.equals("impound")) { spr_master = gameControl.GetSpriteSkill("impound",2); }
					if(listSkills.get(i).SkillName.equals("invisibility")) { spr_master = gameControl.GetSpriteSkill("invisibility",2); }
					if(listSkills.get(i).SkillName.equals("ironshield")) { spr_master = gameControl.GetSpriteSkill("ironshield",2); }
					if(listSkills.get(i).SkillName.equals("doublehit")) { spr_master = gameControl.GetSpriteSkill("doublehit",2); }
					if(listSkills.get(i).SkillName.equals("fastshot")) { spr_master = gameControl.GetSpriteSkill("fastshot",2); }
					if(listSkills.get(i).SkillName.equals("fireball")) { spr_master = gameControl.GetSpriteSkill("fireball",2); }
					if(listSkills.get(i).SkillName.equals("flysword")) { spr_master = gameControl.GetSpriteSkill("flysword",2); }
					if(listSkills.get(i).SkillName.equals("heal")) { spr_master = gameControl.GetSpriteSkill("heal",2); }
					if(listSkills.get(i).SkillName.equals("defboost")) { spr_master = gameControl.GetSpriteSkill("defboost",2); }
					if(listSkills.get(i).SkillName.equals("berserk")) { spr_master = gameControl.GetSpriteSkill("berserk",2); }
					if(listSkills.get(i).SkillName.equals("bulletrain")) { spr_master = gameControl.GetSpriteSkill("bulletrain",2); }
					if(listSkills.get(i).SkillName.equals("dashkick")) { spr_master = gameControl.GetSpriteSkill("dashkick",2); }
					if(listSkills.get(i).SkillName.equals("regen")) { spr_master = gameControl.GetSpriteSkill("regen",2); }
					if(listSkills.get(i).SkillName.equals("rockbound")) { spr_master = gameControl.GetSpriteSkill("rockbound",2); }
					
					spr_master.setPosition(listSkills.get(i).SkillPosX -10, listSkills.get(i).SkillPosY);
					spr_master.setSize(100,100);
					
					if(listSkills.get(i).SkillName.equals("healthboost")) { spr_master.setSize(60,60); }
					if(listSkills.get(i).SkillName.equals("fireball")) { 
						spr_master.setSize(70,70); 
						float playerposx = Float.parseFloat(player.PosX);
						float playerposy = Float.parseFloat(player.PosY);
						spr_master.setPosition(playerposx - 20, playerposy - 10);
					}
					spr_master.draw(game.batch);
				}
				
				if(listSkills.get(i).SkillTime >= 40 && listSkills.get(i).SkillTime <= 60) { 
					if(listSkills.get(i).SkillName.equals("tripleattack")) { spr_master = gameControl.GetSpriteSkill("tripleattack",3); }
					if(listSkills.get(i).SkillName.equals("steal")) { spr_master = gameControl.GetSpriteSkill("steal",3); }
					if(listSkills.get(i).SkillName.equals("soulclash")) { spr_master = gameControl.GetSpriteSkill("soulclash",3); }
					if(listSkills.get(i).SkillName.equals("ravenblade")) { spr_master = gameControl.GetSpriteSkill("ravenblade",3); }
					if(listSkills.get(i).SkillName.equals("ragebound")) { spr_master = gameControl.GetSpriteSkill("ragebound",3); }
					if(listSkills.get(i).SkillName.equals("thundercloud")) { spr_master = gameControl.GetSpriteSkill("thundercloud",3); }
					if(listSkills.get(i).SkillName.equals("perfectshot")) { spr_master = gameControl.GetSpriteSkill("perfectshot",3); }
					if(listSkills.get(i).SkillName.equals("mine")) { spr_master = gameControl.GetSpriteSkill("mine",3); }
					if(listSkills.get(i).SkillName.equals("overpower")) { spr_master = gameControl.GetSpriteSkill("overpower",3); }
					if(listSkills.get(i).SkillName.equals("poisonhit")) { spr_master = gameControl.GetSpriteSkill("poisonhit",3); }
					if(listSkills.get(i).SkillName.equals("precision")) { spr_master = gameControl.GetSpriteSkill("precision",3); }
					if(listSkills.get(i).SkillName.equals("protect")) { spr_master = gameControl.GetSpriteSkill("protect",3); }
					if(listSkills.get(i).SkillName.equals("healthboost")) { spr_master = gameControl.GetSpriteSkill("healthboost",3); }
					if(listSkills.get(i).SkillName.equals("holyprism")) { spr_master = gameControl.GetSpriteSkill("holyprism",3); }
					if(listSkills.get(i).SkillName.equals("icecrystal")) { spr_master = gameControl.GetSpriteSkill("icecrystal",3); }
					if(listSkills.get(i).SkillName.equals("impound")) { spr_master = gameControl.GetSpriteSkill("impound",3); }
					if(listSkills.get(i).SkillName.equals("invisibility")) { spr_master = gameControl.GetSpriteSkill("invisibility",3); }
					if(listSkills.get(i).SkillName.equals("ironshield")) { spr_master = gameControl.GetSpriteSkill("ironshield",3); }
					if(listSkills.get(i).SkillName.equals("doublehit")) { spr_master = gameControl.GetSpriteSkill("doublehit",3); }
					if(listSkills.get(i).SkillName.equals("fastshot")) { spr_master = gameControl.GetSpriteSkill("fastshot",3); }
					if(listSkills.get(i).SkillName.equals("fireball")) { spr_master = gameControl.GetSpriteSkill("fireball",3); }
					if(listSkills.get(i).SkillName.equals("flysword")) { spr_master = gameControl.GetSpriteSkill("flysword",3); }
					if(listSkills.get(i).SkillName.equals("heal")) { spr_master = gameControl.GetSpriteSkill("heal",3); }
					if(listSkills.get(i).SkillName.equals("defboost")) { spr_master = gameControl.GetSpriteSkill("defboost",3); }
					if(listSkills.get(i).SkillName.equals("berserk")) { spr_master = gameControl.GetSpriteSkill("berserk",3); }
					if(listSkills.get(i).SkillName.equals("bulletrain")) { spr_master = gameControl.GetSpriteSkill("bulletrain",3); }
					if(listSkills.get(i).SkillName.equals("dashkick")) { spr_master = gameControl.GetSpriteSkill("dashkick",3); }
					if(listSkills.get(i).SkillName.equals("regen")) { spr_master = gameControl.GetSpriteSkill("regen",3); }
					if(listSkills.get(i).SkillName.equals("rockbound")) { spr_master = gameControl.GetSpriteSkill("rockbound",3); }
					
					spr_master.setPosition(listSkills.get(i).SkillPosX -10, listSkills.get(i).SkillPosY);
					spr_master.setSize(100,100);
					
					if(listSkills.get(i).SkillName.equals("healthboost")) { spr_master.setSize(60,60); }
					if(listSkills.get(i).SkillName.equals("fireball")) { 
						spr_master.setSize(70,70); 
						spr_master.setPosition(listSkills.get(i).SkillPosX -10, listSkills.get(i).SkillPosX -10);
					}
					spr_master.draw(game.batch);
				}
				
				if(listSkills.get(i).SkillTime >= 20 && listSkills.get(i).SkillTime <= 40) { 
					if(listSkills.get(i).SkillName.equals("tripleattack")) { spr_master = gameControl.GetSpriteSkill("tripleattack",4); }
					if(listSkills.get(i).SkillName.equals("steal")) { spr_master = gameControl.GetSpriteSkill("steal",4); }
					if(listSkills.get(i).SkillName.equals("soulclash")) { spr_master = gameControl.GetSpriteSkill("soulclash",4); }
					if(listSkills.get(i).SkillName.equals("ravenblade")) { spr_master = gameControl.GetSpriteSkill("ravenblade",4); }
					if(listSkills.get(i).SkillName.equals("ragebound")) { spr_master = gameControl.GetSpriteSkill("ragebound",4); }
					if(listSkills.get(i).SkillName.equals("thundercloud")) { spr_master = gameControl.GetSpriteSkill("thundercloud",4); }
					if(listSkills.get(i).SkillName.equals("perfectshot")) { spr_master = gameControl.GetSpriteSkill("perfectshot",4); }
					if(listSkills.get(i).SkillName.equals("mine")) { spr_master = gameControl.GetSpriteSkill("mine",4); }
					if(listSkills.get(i).SkillName.equals("overpower")) { spr_master = gameControl.GetSpriteSkill("overpower",4); }
					if(listSkills.get(i).SkillName.equals("poisonhit")) { spr_master = gameControl.GetSpriteSkill("poisonhit",4); }
					if(listSkills.get(i).SkillName.equals("precision")) { spr_master = gameControl.GetSpriteSkill("precision",4); }
					if(listSkills.get(i).SkillName.equals("protect")) { spr_master = gameControl.GetSpriteSkill("protect",4); }
					if(listSkills.get(i).SkillName.equals("healthboost")) { spr_master = gameControl.GetSpriteSkill("healthboost",4); }
					if(listSkills.get(i).SkillName.equals("holyprism")) { spr_master = gameControl.GetSpriteSkill("holyprism",4); }
					if(listSkills.get(i).SkillName.equals("icecrystal")) { spr_master = gameControl.GetSpriteSkill("icecrystal",4); }
					if(listSkills.get(i).SkillName.equals("impound")) { spr_master = gameControl.GetSpriteSkill("impound",4); }
					if(listSkills.get(i).SkillName.equals("invisibility")) { spr_master = gameControl.GetSpriteSkill("invisibility",4); }
					if(listSkills.get(i).SkillName.equals("ironshield")) { spr_master = gameControl.GetSpriteSkill("ironshield",4); }
					if(listSkills.get(i).SkillName.equals("doublehit")) { spr_master = gameControl.GetSpriteSkill("doublehit",4); }
					if(listSkills.get(i).SkillName.equals("fastshot")) { spr_master = gameControl.GetSpriteSkill("fastshot",4); }
					if(listSkills.get(i).SkillName.equals("fireball")) { spr_master = gameControl.GetSpriteSkill("fireball",4); }
					if(listSkills.get(i).SkillName.equals("flysword")) { spr_master = gameControl.GetSpriteSkill("flysword",4); }
					if(listSkills.get(i).SkillName.equals("heal")) { spr_master = gameControl.GetSpriteSkill("heal",4); }
					if(listSkills.get(i).SkillName.equals("defboost")) { spr_master = gameControl.GetSpriteSkill("defboost",4); }
					if(listSkills.get(i).SkillName.equals("berserk")) { spr_master = gameControl.GetSpriteSkill("berserk",4); }
					if(listSkills.get(i).SkillName.equals("bulletrain")) { spr_master = gameControl.GetSpriteSkill("bulletrain",4); }
					if(listSkills.get(i).SkillName.equals("dashkick")) { spr_master = gameControl.GetSpriteSkill("dashkick",4); }
					if(listSkills.get(i).SkillName.equals("regen")) { spr_master = gameControl.GetSpriteSkill("regen",4); }
					if(listSkills.get(i).SkillName.equals("rockbound")) { spr_master = gameControl.GetSpriteSkill("rockbound",4); }
					
					spr_master.setPosition(listSkills.get(i).SkillPosX -10, listSkills.get(i).SkillPosY);
					spr_master.setSize(100,100);
					
					if(listSkills.get(i).SkillName.equals("healthboost")) { spr_master.setSize(60,60); }
					if(listSkills.get(i).SkillName.equals("fireball")) { 
						spr_master.setSize(70,70); 
						spr_master.setPosition(listSkills.get(i).SkillPosX -10, listSkills.get(i).SkillPosX -10);
					}
					spr_master.draw(game.batch);
				}
				
				if(listSkills.get(i).SkillTime >= 10 && listSkills.get(i).SkillTime <= 20) { 
					if(listSkills.get(i).SkillName.equals("tripleattack")) { spr_master = gameControl.GetSpriteSkill("tripleattack",5); }
					if(listSkills.get(i).SkillName.equals("steal")) { spr_master = gameControl.GetSpriteSkill("steal",5); }
					if(listSkills.get(i).SkillName.equals("soulclash")) { spr_master = gameControl.GetSpriteSkill("soulclash",5); }
					if(listSkills.get(i).SkillName.equals("ravenblade")) { spr_master = gameControl.GetSpriteSkill("ravenblade",5); }
					if(listSkills.get(i).SkillName.equals("ragebound")) { spr_master = gameControl.GetSpriteSkill("ragebound",5); }
					if(listSkills.get(i).SkillName.equals("thundercloud")) { spr_master = gameControl.GetSpriteSkill("thundercloud",5); }
					if(listSkills.get(i).SkillName.equals("perfectshot")) { spr_master = gameControl.GetSpriteSkill("perfectshot",5); }
					if(listSkills.get(i).SkillName.equals("mine")) { spr_master = gameControl.GetSpriteSkill("mine",5); }
					if(listSkills.get(i).SkillName.equals("overpower")) { spr_master = gameControl.GetSpriteSkill("overpower",5); }
					if(listSkills.get(i).SkillName.equals("poisonhit")) { spr_master = gameControl.GetSpriteSkill("poisonhit",5); }
					if(listSkills.get(i).SkillName.equals("precision")) { spr_master = gameControl.GetSpriteSkill("precision",5); }
					if(listSkills.get(i).SkillName.equals("protect")) { spr_master = gameControl.GetSpriteSkill("protect",5); }
					if(listSkills.get(i).SkillName.equals("healthboost")) { spr_master = gameControl.GetSpriteSkill("healthboost",5); }
					if(listSkills.get(i).SkillName.equals("holyprism")) { spr_master = gameControl.GetSpriteSkill("holyprism",5); }
					if(listSkills.get(i).SkillName.equals("icecrystal")) { spr_master = gameControl.GetSpriteSkill("icecrystal",5); }
					if(listSkills.get(i).SkillName.equals("impound")) { spr_master = gameControl.GetSpriteSkill("impound",5); }
					if(listSkills.get(i).SkillName.equals("invisibility")) { spr_master = gameControl.GetSpriteSkill("invisibility",5); }
					if(listSkills.get(i).SkillName.equals("ironshield")) { spr_master = gameControl.GetSpriteSkill("ironshield",5); }
					if(listSkills.get(i).SkillName.equals("doublehit")) { spr_master = gameControl.GetSpriteSkill("doublehit",5); }
					if(listSkills.get(i).SkillName.equals("fastshot")) { spr_master = gameControl.GetSpriteSkill("fastshot",5); }
					if(listSkills.get(i).SkillName.equals("fireball")) { spr_master = gameControl.GetSpriteSkill("fireball",5); }
					if(listSkills.get(i).SkillName.equals("flysword")) { spr_master = gameControl.GetSpriteSkill("flysword",5); }
					if(listSkills.get(i).SkillName.equals("heal")) { spr_master = gameControl.GetSpriteSkill("heal",5); }
					if(listSkills.get(i).SkillName.equals("defboost")) { spr_master = gameControl.GetSpriteSkill("defboost",5); }
					if(listSkills.get(i).SkillName.equals("berserk")) { spr_master = gameControl.GetSpriteSkill("berserk",5); }
					if(listSkills.get(i).SkillName.equals("bulletrain")) { spr_master = gameControl.GetSpriteSkill("bulletrain",5); }
					if(listSkills.get(i).SkillName.equals("dashkick")) { spr_master = gameControl.GetSpriteSkill("dashkick",5); }
					if(listSkills.get(i).SkillName.equals("regen")) { spr_master = gameControl.GetSpriteSkill("regen",5); }
					if(listSkills.get(i).SkillName.equals("rockbound")) { spr_master = gameControl.GetSpriteSkill("rockbound",5); }
					
					spr_master.setPosition(listSkills.get(i).SkillPosX -10, listSkills.get(i).SkillPosY);
					spr_master.setSize(100,100);
					
					if(listSkills.get(i).SkillName.equals("healthboost")) { spr_master.setSize(60,60); }
					if(listSkills.get(i).SkillName.equals("fireball")) { 
						spr_master.setSize(70,70); 
						spr_master.setPosition(listSkills.get(i).SkillPosX -10, listSkills.get(i).SkillPosX -10);
					}
					spr_master.draw(game.batch);
				}
				
				if(listSkills.get(i).SkillTime >= 0 && listSkills.get(i).SkillTime <= 10) { 
					if(listSkills.get(i).SkillName.equals("tripleattack")) { spr_master = gameControl.GetSpriteSkill("tripleattack",6); }
					if(listSkills.get(i).SkillName.equals("steal")) { spr_master = gameControl.GetSpriteSkill("steal",6); }
					if(listSkills.get(i).SkillName.equals("soulclash")) { spr_master = gameControl.GetSpriteSkill("soulclash",6); }
					if(listSkills.get(i).SkillName.equals("ravenblade")) { spr_master = gameControl.GetSpriteSkill("ravenblade",6); }
					if(listSkills.get(i).SkillName.equals("ragebound")) { spr_master = gameControl.GetSpriteSkill("ragebound",6); }
					if(listSkills.get(i).SkillName.equals("thundercloud")) { spr_master = gameControl.GetSpriteSkill("thundercloud",6); }
					if(listSkills.get(i).SkillName.equals("perfectshot")) { spr_master = gameControl.GetSpriteSkill("perfectshot",6); }
					if(listSkills.get(i).SkillName.equals("mine")) { spr_master = gameControl.GetSpriteSkill("mine",6); }
					if(listSkills.get(i).SkillName.equals("overpower")) { spr_master = gameControl.GetSpriteSkill("overpower",6); }
					if(listSkills.get(i).SkillName.equals("poisonhit")) { spr_master = gameControl.GetSpriteSkill("poisonhit",6); }
					if(listSkills.get(i).SkillName.equals("precision")) { spr_master = gameControl.GetSpriteSkill("precision",6); }
					if(listSkills.get(i).SkillName.equals("protect")) { spr_master = gameControl.GetSpriteSkill("protect",6); }
					if(listSkills.get(i).SkillName.equals("healthboost")) { spr_master = gameControl.GetSpriteSkill("healthboost",6); }
					if(listSkills.get(i).SkillName.equals("holyprism")) { spr_master = gameControl.GetSpriteSkill("holyprism",6); }
					if(listSkills.get(i).SkillName.equals("icecrystal")) { spr_master = gameControl.GetSpriteSkill("icecrystal",6); }
					if(listSkills.get(i).SkillName.equals("impound")) { spr_master = gameControl.GetSpriteSkill("impound",6); }
					if(listSkills.get(i).SkillName.equals("invisibility")) { spr_master = gameControl.GetSpriteSkill("invisibility",6); }
					if(listSkills.get(i).SkillName.equals("ironshield")) { spr_master = gameControl.GetSpriteSkill("ironshield",6); }
					if(listSkills.get(i).SkillName.equals("doublehit")) { spr_master = gameControl.GetSpriteSkill("doublehit",6); }
					if(listSkills.get(i).SkillName.equals("fastshot")) { spr_master = gameControl.GetSpriteSkill("fastshot",6); }
					if(listSkills.get(i).SkillName.equals("fireball")) { spr_master = gameControl.GetSpriteSkill("fireball",6); }
					if(listSkills.get(i).SkillName.equals("flysword")) { spr_master = gameControl.GetSpriteSkill("flysword",6); }
					if(listSkills.get(i).SkillName.equals("heal")) { spr_master = gameControl.GetSpriteSkill("heal",6); }
					if(listSkills.get(i).SkillName.equals("defboost")) { spr_master = gameControl.GetSpriteSkill("defboost",6); }
					if(listSkills.get(i).SkillName.equals("berserk")) { spr_master = gameControl.GetSpriteSkill("berserk",6); }
					if(listSkills.get(i).SkillName.equals("bulletrain")) { spr_master = gameControl.GetSpriteSkill("bulletrain",6); }
					if(listSkills.get(i).SkillName.equals("dashkick")) { spr_master = gameControl.GetSpriteSkill("dashkick",6); }
					if(listSkills.get(i).SkillName.equals("regen")) { spr_master = gameControl.GetSpriteSkill("regen",6); }
					if(listSkills.get(i).SkillName.equals("rockbound")) { spr_master = gameControl.GetSpriteSkill("rockbound",6); spr_master.setSize(60,60); }
					
					spr_master.setPosition(listSkills.get(i).SkillPosX -10, listSkills.get(i).SkillPosY);
					spr_master.setSize(100,100);
					
					if(listSkills.get(i).SkillName.equals("healthboost")) { spr_master.setSize(60,60); }
					if(listSkills.get(i).SkillName.equals("fireball")) { 
						spr_master.setSize(70,70); 
						spr_master.setPosition(listSkills.get(i).SkillPosX -10, listSkills.get(i).SkillPosX -10);
					}
					spr_master.draw(game.batch);
				}
				
				if(listSkills.get(i).SkillTime < 0) { 
					listSkills.remove(listSkills.get(i));
				}
			}
		}
		
		public int CheckWeapon() {  
			
			//knifes
			if(player.Weapon.equals("basic_knife_c")) { return 1;}	
			if(player.Weapon.equals("basic_knife_b")) { return 8;}	
			if(player.Weapon.equals("basic_knife_a")) { return 12;}	
			if(player.Weapon.equals("basic_knife_s")) { return 100;}	
						
			if(player.Weapon.equals("doubleedgeknife_c")) { return 3; }	
			if(player.Weapon.equals("doubleedgeknife_b")) { return 10; }
			if(player.Weapon.equals("doubleedgeknife_a")) { return 15; }
			if(player.Weapon.equals("doubleedgeknife_s")) { return 102; }
					
			//swords
			if(player.Weapon.equals("woodsword_c")) { return 10;}	
			if(player.Weapon.equals("woodsword_b")) { return 35;}	
			if(player.Weapon.equals("woodsword_a")) { return 50;}	
			if(player.Weapon.equals("woodsword_s")) { return 400;}	
			
			if(player.Weapon.equals("ragesword_c")) { return 15;}	
			if(player.Weapon.equals("ragesword_b")) { return 52;}	
			if(player.Weapon.equals("ragesword_a")) { return 79;}	
			if(player.Weapon.equals("ragesword_s")) { return 410;}	
					
			if(player.Weapon.equals("serpentsword_c")) { return 32;}	
			if(player.Weapon.equals("serpentsword_b")) { return 70;}
			if(player.Weapon.equals("serpentsword_a")) { return 110;}
			if(player.Weapon.equals("serpentsword_s")) { return 420;}
						
			if(player.Weapon.equals("flamesword_c")) { return 40;}	
			if(player.Weapon.equals("flamesword_b")) { return 89;}
			if(player.Weapon.equals("flamesword_a")) { return 140;}
			if(player.Weapon.equals("flamesword_s")) { return 430;}
					
			if(player.Weapon.equals("cristalsword_c")) { return 48;}	
			if(player.Weapon.equals("cristalsword_b")) { return 120;}	
			if(player.Weapon.equals("cristalsword_a")) { return 170;}	
			if(player.Weapon.equals("cristalsword_s")) { return 540;}	
			
			
			//Rods
			if(player.Weapon.equals("stickrod_c")) { return 5;}
			if(player.Weapon.equals("stickrod_b")) { return 12;}
			if(player.Weapon.equals("stickrod_a")) { return 20;}
			if(player.Weapon.equals("stickrod_s")) { return 190;}
				
			if(player.Weapon.equals("gloomrod_c")) { return 10;}	
			if(player.Weapon.equals("gloomrod_b")) { return 20;}	
			if(player.Weapon.equals("gloomrod_a")) { return 45;}	
			if(player.Weapon.equals("gloomrod_s")) { return 200;}	
				
			if(player.Weapon.equals("starrod_c")) { return 14;}	
			if(player.Weapon.equals("starrod_b")) { return 40;}	
			if(player.Weapon.equals("starrod_a")) { return 60;}	
			if(player.Weapon.equals("starrod_s")) { return 220;}	
				
			if(player.Weapon.equals("deathrod_c")) { return 25;}	
			if(player.Weapon.equals("deathrod_b")) { return 50;}	
			if(player.Weapon.equals("deathrod_a")) { return 70;}	
			if(player.Weapon.equals("deathrod_s")) { return 240;}	
				
			if(player.Weapon.equals("butterflyrod_c")) { return 35;}	
			if(player.Weapon.equals("butterflyrod_b")) { return 72;}	
			if(player.Weapon.equals("butterflyrod_a")) { return 100;}	
			if(player.Weapon.equals("butterflyrod_s")) { return 350;}	
			
			//Dagger
			if(player.Weapon.equals("basicdagger_c")) { return 8;}
			if(player.Weapon.equals("basicdagger_b")) { return 15;}		
			if(player.Weapon.equals("basicdagger_a")) { return 23;}		
			if(player.Weapon.equals("basicdagger_s")) { return 300;}	
			
			if(player.Weapon.equals("flamebergdagger_c")) { return 12;}
			if(player.Weapon.equals("flamebergdagger_b")) { return 24;}
			if(player.Weapon.equals("flamebergdagger_a")) { return 48;}
			if(player.Weapon.equals("flamebergdagger_s")) { return 330;}
			
			if(player.Weapon.equals("marinedagger_c")) { return 18;}
			if(player.Weapon.equals("marinedagger_b")) { return 45;}
			if(player.Weapon.equals("marinedagger_a")) { return 70;}
			if(player.Weapon.equals("marinedagger_s")) { return 340;}
			
			if(player.Weapon.equals("winddagger_c")) { return 29;}
			if(player.Weapon.equals("winddagger_b")) { return 56;}
			if(player.Weapon.equals("winddagger_a")) { return 90;}
			if(player.Weapon.equals("winddagger_s")) { return 360;}
			
			if(player.Weapon.equals("thunderdagger_c")) { return 40;}
			if(player.Weapon.equals("thunderdagger_b")) { return 78;}
			if(player.Weapon.equals("thunderdagger_a")) { return 120;}
			if(player.Weapon.equals("thunderdagger_s")) { return 370;}
			
			//Pistol
			if(player.Weapon.equals("basicpistol_c")) { return 8;}
			if(player.Weapon.equals("basicpistol_b")) { return 15;}
			if(player.Weapon.equals("basicpistol_a")) { return 23;}
			if(player.Weapon.equals("basicpistol_s")) { return 300;}
					
			if(player.Weapon.equals("lightpistol_c")) { return 12;}
			if(player.Weapon.equals("lightpistol_b")) { return 24;}
			if(player.Weapon.equals("lightpistol_a")) { return 48;}
			if(player.Weapon.equals("lightpistol_s")) { return 330;}
			
			if(player.Weapon.equals("shooterpistol_c")) { return 18;}
			if(player.Weapon.equals("shooterpistol_b")) { return 45;}
			if(player.Weapon.equals("shooterpistol_a")) { return 70;}
			if(player.Weapon.equals("shooterpistol_s")) { return 340;}
					
			if(player.Weapon.equals("sharkpistol_c")) { return 29;}
			if(player.Weapon.equals("sharkpistol_b")) { return 56;}
			if(player.Weapon.equals("sharkpistol_a")) { return 90;}
			if(player.Weapon.equals("sharkpistol_s")) { return 360;}
			
			if(player.Weapon.equals("cannonpistol_c")) { return 40;}
			if(player.Weapon.equals("cannonpistol_b")) { return 78;}
			if(player.Weapon.equals("cannonpistol_a")) { return 120;}
			if(player.Weapon.equals("cannonpistol_s")) { return 370;}
			
			//Axe		
			if(player.Weapon.equals("basicaxe_c")) { return 12;}
			if(player.Weapon.equals("basicaxe_b")) { return 37;}
			if(player.Weapon.equals("basicaxe_a")) { return 52;}
			if(player.Weapon.equals("basicaxe_s")) { return 405;}
			
			if(player.Weapon.equals("hammeraxe_c")) { return 17;}
			if(player.Weapon.equals("hammeraxe_b")) { return 55;}
			if(player.Weapon.equals("hammeraxe_a")) { return 83;}
			if(player.Weapon.equals("hammeraxe_s")) { return 415;}
					
			if(player.Weapon.equals("scytheaxe_c")) { return 35;}
			if(player.Weapon.equals("scytheaxe_b")) { return 74;}
			if(player.Weapon.equals("scytheaxe_a")) { return 123;}
			if(player.Weapon.equals("scytheaxe_s")) { return 12;}
			
			if(player.Weapon.equals("anchoraxe_c")) { return 45;}
			if(player.Weapon.equals("anchoraxe_b")) { return 95;}
			if(player.Weapon.equals("anchoraxe_a")) { return 150;}
			if(player.Weapon.equals("anchoraxe_s")) { return 435;}
					
			if(player.Weapon.equals("guitaraxe_c")) { return 58;}
			if(player.Weapon.equals("guitaraxe_b")) { return 130;}
			if(player.Weapon.equals("guitaraxe_a")) { return 180;}
			if(player.Weapon.equals("guitaraxe_s")) { return 550;}
			
			return 0;
		}
		
		public void MobDead(int mobindex) {
			int playermoney = Integer.parseInt(player.Money);
			int expsended = 0;
			
			player.Target = "none";
			player.AtkTimer = player.AtkTimerMax;
			player.playerInBattle = "no";
		    player.playerInAttack = "no";
		    player.playerInCast = "no";	
		    autoattack = false;
		    
			String date = dateTimeProvider.getCurrentDateTime();
			
			showDropMsg = 100;
		    itemdropname = gameControl.ItemDrop(listMonsters.get(mobindex).MobName);
		    int expreceived = listMonsters.get(mobindex).MobExp;
		    gameControl.GiveExp(expreceived, listMonsters.get(mobindex).MobLevel);

		    expsended = 1;
			
		    try {
				gameControl.SendExpBank("SendExpBank",player.AccountNumber,playernumString,player.Name,String.valueOf(expsended),date, new HttpCallback() {
				    @Override
				    public void onSuccess(String response) {
				    	if(response.contains("success")) {}
				    	else {
				    		//avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
				    		//aviso = true;
				    	}
				    }

				    @Override
				    public void onFailure(Throwable t) {
				       //System.out.println("Error: " + t.getMessage());
				       //avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
					   //aviso = true;
				    }
				});
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    if(playermoney > 2000) { return; }
		    playermoney = playermoney + 2;
		    player.Money = String.valueOf(playermoney);
		    
		}
		
		public void CheckAction() {
		    float posX = Float.parseFloat(player.PosX);
		    float posY = Float.parseFloat(player.PosY);

		    if (player.Map.equals("StreetsA")) {
		        if (posX >= 103.5f && posX <= 122 && posY >= -142 && posY <= -128.5f) {
		            state = "DungeonSelect";
		        }

		        // Exp Giver
		        if (posX >= -8f && posX <= 10.5f && posY >= -145 && posY <= -112.5f) {
		            try {
		                gameControl.GetExpBank("GetExpBank", player.AccountNumber, playernumString, player.Name, player.PlayerExpGet, new HttpCallback() {
		                    @Override
		                    public void onSuccess(String response) {
		                        if (response.contains("success")) {
		                        	String date = dateTimeProvider.getCurrentDateTime();

		                            // Format the current date and time as a string
		                            player.PlayerExpGet = date;
		                            
		                            LoopTime = 10;
		                        } else {
		                            //avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
		                            //aviso = true;
		                        }
		                    }

		                    @Override
		                    public void onFailure(Throwable t) {
		                        //System.out.println("Error: " + t.getMessage());
		                        //avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
		                        //aviso = true;
		                    }
		                });
		            } catch (UnsupportedEncodingException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }
		        }

		        if (posX >= 127 && posX <= 143 && posY >= -140 && posY <= -124) {
		            state = "Shop";
		            shopname = "refrishop";
		        }
		        
		        if (posX >= 143.5f && posX <= 163 && posY >= -136.5f && posY <= -109) {
		            state = "Shop";
		            shopname = "weaponshop";
		        }
		        
		        //mestre das classes
		        if (posX >= 83 && posX <= 102 && posY >= -52 && posY <= -14.5f) {
		            state = "Shop";
		            shopname = "jobmaster";
		        }
		        
		        //Cristalized
		        if (posX >= 6.5f && posX <= 24 && posY >= -52 && posY <= -14.5f) {
		            state = "Shop";
		            shopname = "cristalized";
		        }
		        
		        //Loja Roupas 1
		        if (posX >= 116 && posX <= 137.5 && posY >= -19.5f && posY <= 4.5f) {
		            state = "Shop";
		            shopname = "lojaroupas1";
		        }
		        
		    }
		}
		
		public void CheckPlayerParty() {
			if(!player.party.equals("none")) {
				if(lstOnlinePlayers.size() > 0) {
					int partynum = 0;
					for(int i = 0; i < lstOnlinePlayers.size(); i++) {
						if(player.party.equals(lstOnlinePlayers.get(i).party)) {
							partynum++;
							
							if(partynum == 1) {
								spr_PartyTag = gameControl.GetUX("partytag", 0, 0);
								spr_PartyTag.setPosition(cameraCoordsX - 15, cameraCoordsY + 73);
								spr_PartyTag.setSize(35,25);
								spr_PartyTag.draw(game.batch);
								
								spr_PartyHair = gameControl.GetHairChar(lstOnlinePlayers.get(i), "no",0,0);
								spr_PartyHair.setPosition(cameraCoordsX - 30, cameraCoordsY + 72);
								spr_PartyHair.draw(game.batch);
								
								font_master.draw(game.batch, lstOnlinePlayers.get(i).Name, cameraCoordsX - 14, cameraCoordsY + 82);
								font_master.draw(game.batch, lstOnlinePlayers.get(i).Level, cameraCoordsX + 13, cameraCoordsY + 81);
								
								font_master.draw(game.batch, lstOnlinePlayers.get(i).Hp, cameraCoordsX + 7, cameraCoordsY + 96);
								font_master.draw(game.batch, lstOnlinePlayers.get(i).Mp, cameraCoordsX + 7, cameraCoordsY + 88);
							}
							if(partynum == 2) {
								spr_PartyTag = gameControl.GetUX("partytag", 0, 0);
								spr_PartyTag.setPosition(cameraCoordsX + 22, cameraCoordsY + 73);
								spr_PartyTag.setSize(35,25);
								spr_PartyTag.draw(game.batch);
								
								spr_PartyHair = gameControl.GetHairChar(lstOnlinePlayers.get(i), "no",0,0);
								spr_PartyHair.setPosition(cameraCoordsX + 8, cameraCoordsY + 72);
								spr_PartyHair.draw(game.batch);
								
								font_master.draw(game.batch, lstOnlinePlayers.get(i).Name, cameraCoordsX + 23, cameraCoordsY + 82);
								font_master.draw(game.batch, lstOnlinePlayers.get(i).Level, cameraCoordsX + 50, cameraCoordsY + 81);
								
								font_master.draw(game.batch, lstOnlinePlayers.get(i).Hp, cameraCoordsX + 43, cameraCoordsY + 96);
								font_master.draw(game.batch, lstOnlinePlayers.get(i).Mp, cameraCoordsX + 43, cameraCoordsY + 88);				
							}
							if(partynum == 3) {
								//3
								spr_PartyTag = gameControl.GetUX("partytag", 0, 0);
								spr_PartyTag.setPosition(cameraCoordsX + 60, cameraCoordsY + 73);
								spr_PartyTag.setSize(35,25);
								spr_PartyTag.draw(game.batch);
								
								spr_PartyHair = gameControl.GetHairChar(lstOnlinePlayers.get(i), "no",0,0);
								spr_PartyHair.setPosition(cameraCoordsX + 45, cameraCoordsY + 72);
								spr_PartyHair.draw(game.batch);
								
								font_master.draw(game.batch, lstOnlinePlayers.get(i).Name, cameraCoordsX + 61, cameraCoordsY + 82);
								font_master.draw(game.batch, lstOnlinePlayers.get(i).Level, cameraCoordsX + 88, cameraCoordsY + 81);
								
								font_master.draw(game.batch, lstOnlinePlayers.get(i).Hp, cameraCoordsX + 81, cameraCoordsY + 96);
								font_master.draw(game.batch, lstOnlinePlayers.get(i).Mp, cameraCoordsX + 81, cameraCoordsY + 88);
							}
							if(partynum == 4) {
								player.party = "none";
							}
						}
					}
				}
			}
		}
		
		public void ChangeTarget() {
					
			if(player.Map.equals("Sewers") || player.Map.equals("Forest") ||
			   player.Map.equals("Watercave") || player.Map.equals("Desert") ||	
			   player.Map.equals("Vulcano") || player.Map.equals("Mines") ||
			   player.Map.equals("Snowpalace") || player.Map.equals("Swamp")
				) {
			
				String playerTarget = player.Target;
				for(int i = 0; i < listMonsters.size(); i++) {
					
					if(countSwitchTarget == 0) {
						countSwitchTarget = i;
					}
					
					if(countSwitchTarget >= 0) {
						if(countSwitchTarget <= listMonsters.size()) {
							countSwitchTarget++;
							if(countSwitchTarget >= listMonsters.size()) { countSwitchTarget = 0; }
							if(!playerTarget.equals(listMonsters.get(countSwitchTarget).MobID)) {
								if(listMonsters.get(countSwitchTarget).MobDead.equals("no")) {
									player.Target = listMonsters.get(countSwitchTarget).MobID;		
								}
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
		
		//SKILL
		public void CheckSkill(int num) {
			
			if(num == 1 && player.Job.equals("Aprendiz")) { SetUseSkill("tripleattack"); }
			if(num == 1 && player.Job.equals("Espadachim")) { SetUseSkill("flysword"); }
			if(num == 1 && player.Job.equals("Mago")) { SetUseSkill("fireball"); }
			if(num == 1 && player.Job.equals("Atirador")) { SetUseSkill("bulletrain"); }
			if(num == 1 && player.Job.equals("Curandeiro")) { SetUseSkill("heal"); }
			if(num == 1 && player.Job.equals("Ladrao")) { SetUseSkill("poisonhit"); }
			
			if(num == 2 && player.Job.equals("Aprendiz")) { SetUseSkill("rockbound"); }
			if(num == 2 && player.Job.equals("Espadachim")) { SetUseSkill("healthboost"); }
			if(num == 2 && player.Job.equals("Mago")) { SetUseSkill("icecrystal"); }
			if(num == 2 && player.Job.equals("Atirador")) { SetUseSkill("perfectshot"); } 
			if(num == 2 && player.Job.equals("Curandeiro")) { SetUseSkill("holyprism"); }
			if(num == 2 && player.Job.equals("Ladrao")) { SetUseSkill("steal"); }
			
			//if(num == 3 && player.Job.equals("Espadachim")) { SetUseSkill("healthboost"); }
			//if(num == 3 && player.Job.equals("Mago")) { SetUseSkill("icecrystal"); }
			//if(num == 3 && player.Job.equals("Atirador")) { SetUseSkill("lockshot"); }
			//if(num == 3 && player.Job.equals("Curandeiro")) { SetUseSkill("defboost"); }
			//if(num == 3 && player.Job.equals("Ladrao")) { SetUseSkill("steal"); }			
		}
		
		public void CheckBuffsToRemove() {
			if(!player.buffA.equals("none")) {
				int buffATime = Integer.parseInt(player.BuffTimeA);
				if(buffATime > 0) { 
					buffATime = buffATime - 1; 
					player.BuffTimeA = String.valueOf(buffATime); 
				}
				if(buffATime <= 0) { player.buffA = "none";}
			}
			if(!player.buffB.equals("none")) {
				int buffBTime = Integer.parseInt(player.BuffTimeB);
				if(buffBTime > 0) { 
					buffBTime = buffBTime - 1; 
					player.BuffTimeB = String.valueOf(buffBTime); 
				}
				if(buffBTime <= 0) { player.buffB = "none";}
			}
			if(!player.buffC.equals("none")) {
				int buffCTime = Integer.parseInt(player.BuffTimeC);
				if(buffCTime > 0) { 
					buffCTime = buffCTime - 1; 
					player.BuffTimeC = String.valueOf(buffCTime); 
				}
				if(buffCTime <= 0) { player.buffC = "none";}
			}
		}
		
		public void SetUseSkill(String skill) {
			int Mp = Integer.parseInt(player.Mp);
			
			//Cost
			if(skill.equals("tripleattack") && Mp < 5) { notmp = true; return; }
			if(skill.equals("rockbound") && Mp < 5) { notmp = true; return; }
			
			if(skill.equals("flysword") && Mp < 25) { notmp = true; return; }
			if(skill.equals("healthboost") && Mp < 20) { notmp = true; return; }
			
			if(skill.equals("fireball") && Mp < 10) { notmp = true; return; }
			if(skill.equals("icecrystal") && Mp < 50) { notmp = true; return; }
			
			if(skill.equals("heal") && Mp < 15) { notmp = true; return; }
			if(skill.equals("holyprism") && Mp < 2) { notmp = true; return; }
			
			if(skill.equals("poisonhit") && Mp < 20) { notmp = true; return; }
			if(skill.equals("steal") && Mp < 10) { notmp = true; return; }
			
			if(skill.equals("bulletrain") && Mp < 15) { notmp = true; return; }
			if(skill.equals("perfectshot") && Mp < 20) { notmp = true; return; }
			
			if(skill.equals("tripleattack")) { Mp = Mp - 5; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("rockbound")) { Mp = Mp - 5; if(Mp <= 0) { Mp = 0;}}
			
			if(skill.equals("flysword")) { Mp = Mp - 25; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("healthboost")) { Mp = Mp - 20; if(Mp <= 0) { Mp = 0;} }
			
			if(skill.equals("fireball")) { Mp = Mp - 10; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("icecrystal")) { Mp = Mp - 50; if(Mp <= 0) { Mp = 0;} }
			
			if(skill.equals("heal")) { Mp = Mp - 15; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("holyprism")) { Mp = Mp - 2; if(Mp <= 0) { Mp = 0;} }
			
			if(skill.equals("poisonhit")) { Mp = Mp - 20; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("steal")) { Mp = Mp - 10; if(Mp <= 0) { Mp = 0;} }
			
			if(skill.equals("bulletrain")) { Mp = Mp - 15; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("perfectshot")) { Mp = Mp - 20; if(Mp <= 0) { Mp = 0;} }
			
			player.Mp = String.valueOf(Mp);
			
			
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
			if(skill.equals("defboost")) { rangedAttack = true; }
			if(skill.equals("holyprism")) { rangedAttack = true; }
			
			if(skill.equals("hammercrash")) { rangedAttack = false; }
			if(skill.equals("overpower")) { rangedAttack = false; }		
			if(skill.equals("berserk")) { rangedAttack = false; }
			
			
			if(skill.equals("bulletrain")) { rangedAttack = true; }
			if(skill.equals("perfectshot")) { rangedAttack = true; }
			
			if(skill.equals("steal")) { rangedAttack = false; }
			if(skill.equals("poisonhit")) { rangedAttack = false; }
			if(skill.equals("invisibility")) { rangedAttack = true; }
					
			
			skillname = skill;
			if(!rangedAttack) { 
				CheckAreaRangedSkill();
			}
			else { 
				player.playerInCast = "yes";
				playerCastTime = 100;
			}	  
		}
		
		public void MapChange(String map) {
			if(map.equals("Sewers")) {
				player.Map = "Sewers";
				player.PosX = String.valueOf("44.5f");
				player.PosY = String.valueOf("-4.5f");
				this.screen.screenSwitch("LoadingScreen","",playernum);
				dispose();	
			}
			if(map.equals("Forest")) {
				player.Map = "Forest";
				player.PosX = String.valueOf("44.5f");
				player.PosY = String.valueOf("-4.5f");
				this.screen.screenSwitch("LoadingScreen","",playernum);
				dispose();	
			}
			if(map.equals("StreetsAFromSewers")) {
				player.Map = "StreetsA";
				player.PosX = String.valueOf("112.5f");
				player.PosY = String.valueOf("-142f");
				this.screen.screenSwitch("LoadingScreen","",playernum);
				dispose();
			}
			
			if(map.equals("StreetsA")) {
				player.Map = "StreetsA";
				player.PosX = String.valueOf("112.5f");
				player.PosY = String.valueOf("-142f");
				this.screen.screenSwitch("LoadingScreen","",playernum);
				dispose();
			}
			
			if(map.equals("Watercave")) { 
				player.Map = "Watercave";
				player.PosX = String.valueOf("46");
				player.PosY = String.valueOf("-15.5f");
				this.screen.screenSwitch("LoadingScreen","",playernum);
				dispose();
			}
			
			if(map.equals("Desert")) {  
				player.Map = "Desert";
				player.PosX = String.valueOf("46");
				player.PosY = String.valueOf("-15.5f");
				this.screen.screenSwitch("LoadingScreen","",playernum);
				dispose();
			}
			
			if(map.equals("Vulcano")) { 
				player.Map = "Vulcano";
				player.PosX = String.valueOf("23");
				player.PosY = String.valueOf("-36.5f");
				this.screen.screenSwitch("LoadingScreen","",playernum);
				dispose();
			}
			
			if(map.equals("Mines")) {  
				player.Map = "Mines";
				player.PosX = String.valueOf("145");
				player.PosY = String.valueOf("2");
				this.screen.screenSwitch("LoadingScreen","",playernum);
				dispose();
			}
			
			if(map.equals("Snowpalace")) {  
				player.Map = "Snowpalace";
				player.PosX = String.valueOf("6");
				player.PosY = String.valueOf("-154f");
				this.screen.screenSwitch("LoadingScreen","",playernum);
				dispose();
			}
			
			if(map.equals("Swamp")) {  
				player.Map = "Swamp";
				player.PosX = String.valueOf("24.5f");
				player.PosY = String.valueOf("11");
				this.screen.screenSwitch("LoadingScreen","",playernum);
				dispose();
			}
		}
		
		//Hotkey
		public void HotKeyItem(int itemNum, int hotkeynum) {
			String[] lstItem = player.Itens.split("-");
			String[] itemSplit = lstItem[itemNum].split("#");
			String item = "";
			//hotketcountitem = itemNum;
			
			
			if(hotkeynum == 1) {
				hotketcountitem1 = itemNum;
			}
			
			if(hotkeynum == 2) {
				hotketcountitem2 = itemNum;
			}
						
			item = itemSplit[0].replace("[", "");
			
			if(hotkeynum == 1) {
				if(item.equals("hpcan")) { player.hotkey1 = item; return; }
				if(item.equals("hpbigcan")) { player.hotkey1 = item; return; }
				if(item.equals("hpultracan")) { player.hotkey1 = item; return; }
				if(item.equals("mpcan")) { player.hotkey1 = item; return; }
				if(item.equals("mpbigcan")) { player.hotkey1 = item; return; }
				if(item.equals("mpultracan")) { player.hotkey1 = item; return; }
			}
			if(hotkeynum == 2) {
				if(item.equals("hpcan")) { player.hotkey2 = item; return; }
				if(item.equals("hpbigcan")) { player.hotkey2 = item; return; }
				if(item.equals("hpultracan")) { player.hotkey2 = item; return; }
				if(item.equals("mpcan")) { player.hotkey2 = item; return; }
				if(item.equals("mpbigcan")) { player.hotkey2 = item; return; }
				if(item.equals("mpultracan")) { player.hotkey2 = item; return; }
			}
		}
		
		public void CheckStatus(String status) {
			int Str = Integer.parseInt(player.Str);
			int Vit = Integer.parseInt(player.Vit);
			int Agi = Integer.parseInt(player.Agi);
			int Wis = Integer.parseInt(player.Wis);
			int Dex = Integer.parseInt(player.Dex);
			int Luk = Integer.parseInt(player.Luk);
			int Res = Integer.parseInt(player.Res);
			int HPMax = Integer.parseInt(player.HpMax);
			int MpMax = Integer.parseInt(player.MpMax);
			int StatusPoint = Integer.parseInt(player.StatusPoint);
			
			int Atk = Integer.parseInt(player.Atk);
			int Def = Integer.parseInt(player.Def);
			
			int AtkTimerMax = Integer.parseInt(player.AtkTimerMax);
			
			int stamina = Integer.parseInt(player.Stamina);
			int staminaMax = Integer.parseInt(player.StaminaMax);
			
			if (StatusPoint >= 1) {
				if (status.equals("Str") && Str <= 99) {
					StatusPoint = StatusPoint - 1;
					Str = Str + 1;
					Atk = Atk + 2;
				} else if (status.equals("Vit") && Vit <= 99) {
					Vit = Vit + 1;
					StatusPoint = StatusPoint - 1;
					HPMax = HPMax + 5;
				} else if (status.equals("Agi") && Agi <= 99) {
					Agi = Agi + 1;
					StatusPoint = StatusPoint - 1;
					AtkTimerMax = AtkTimerMax - 1;
				} else if (status.equals("Wis") && Wis <= 99) {
					Wis = Wis + 1;
					StatusPoint = StatusPoint - 1;
					MpMax = MpMax + 5;
				} else if (status.equals("Dex") && Dex <= 99) {
					Dex = Dex + 1;
					Atk = Atk + 1;
					StatusPoint = StatusPoint - 1;
				} else if (status.equals("Luk") && Luk <= 99) {
					Luk = Luk + 1;
					Atk = Atk + 1;
					StatusPoint = StatusPoint - 1;
				} else if (status.equals("Res") && Res <= 99) {
					Res = Res + 1;
					staminaMax = staminaMax + 100;
					Def = Def + 1;
					StatusPoint = StatusPoint - 1;
				}
				
				// Update player attributes
				player.Str = String.valueOf(Str);
				player.Vit = String.valueOf(Vit);
				player.Agi = String.valueOf(Agi);
				player.Wis = String.valueOf(Wis);
				player.Dex = String.valueOf(Dex);
				player.Luk = String.valueOf(Luk);
				player.Res = String.valueOf(Res);
				player.HpMax = String.valueOf(HPMax);
				player.MpMax = String.valueOf(MpMax);
				player.StatusPoint = String.valueOf(StatusPoint);
				player.Atk = String.valueOf(Atk);
				player.AtkTimerMax = String.valueOf(AtkTimerMax);
				player.Def = String.valueOf(Def);
				player.Stamina = String.valueOf(stamina);
				player.StaminaMax = String.valueOf(staminaMax);
			}
		}
		
		public void KeyboardKeyPressed(float x, float y) {
			if (x >= cameraCoordsX - 95 && x <= cameraCoordsX - 80 && y >= cameraCoordsY + 31 && y <= cameraCoordsY + 62) {
				keyboardText = keyboardText + "1";
			}
			if (x >= cameraCoordsX - 77 && x <= cameraCoordsX - 63 && y >= cameraCoordsY + 31 && y <= cameraCoordsY + 62) {
				keyboardText = keyboardText + "2";
			}
			if (x >= cameraCoordsX - 59 && x <= cameraCoordsX - 45 && y >= cameraCoordsY + 31 && y <= cameraCoordsY + 62) {
					keyboardText = keyboardText + "3";
			}
			if (x >= cameraCoordsX - 42 && x <= cameraCoordsX - 27 && y >= cameraCoordsY + 31 && y <= cameraCoordsY + 62) {
					keyboardText = keyboardText + "4";
			}
			if (x >= cameraCoordsX - 23 && x <= cameraCoordsX - 10 && y >= cameraCoordsY + 31 && y <= cameraCoordsY + 62) {
					keyboardText = keyboardText + "5";
			}
			if (x >= cameraCoordsX - 6 && x <= cameraCoordsX + 8 && y >= cameraCoordsY + 31 && y <= cameraCoordsY + 62) {
					keyboardText = keyboardText + "6";
			}
			if (x >= cameraCoordsX + 12 && x <= cameraCoordsX + 26 && y >= cameraCoordsY + 31 && y <= cameraCoordsY + 62) {
					keyboardText = keyboardText + "7";
			}
			
			if (x >= cameraCoordsX + 29 && x <= cameraCoordsX + 43 && y >= cameraCoordsY + 31 && y <= cameraCoordsY + 62) {
				keyboardText = keyboardText + "8";
			}
			if (x >= cameraCoordsX + 47 && x <= cameraCoordsX + 61 && y >= cameraCoordsY + 31 && y <= cameraCoordsY + 62) {
				keyboardText = keyboardText + "9";
			}
			if (x >= cameraCoordsX + 64 && x <= cameraCoordsX + 79 && y >= cameraCoordsY + 31 && y <= cameraCoordsY + 62) {
				keyboardText = keyboardText + "0";
			}

			if (x >= cameraCoordsX - 84 && x <= cameraCoordsX - 69 && y >= cameraCoordsY - 3 && y <= cameraCoordsY + 24) {
				keyboardText = keyboardText + "Q";
			}
			if (x >= cameraCoordsX - 66 && x <= cameraCoordsX - 52 && y >= cameraCoordsY - 3 && y <= cameraCoordsY + 24) {
				keyboardText = keyboardText + "W";
			}
			if (x >= cameraCoordsX - 49 && x <= cameraCoordsX - 35 && y >= cameraCoordsY - 3 && y <= cameraCoordsY + 24) {
				keyboardText = keyboardText + "E";
			}
			if (x >= cameraCoordsX - 32 && x <= cameraCoordsX - 18 && y >= cameraCoordsY - 3 && y <= cameraCoordsY + 24) {
				keyboardText = keyboardText + "R";
			}
			if (x >= cameraCoordsX - 15 && x <= cameraCoordsX - 1 && y >= cameraCoordsY - 3 && y <= cameraCoordsY + 24) {
				keyboardText = keyboardText + "T";
			}
			if (x >= cameraCoordsX + 2 && x <= cameraCoordsX + 16 && y >= cameraCoordsY - 3 && y <= cameraCoordsY + 24) {
				keyboardText = keyboardText + "Y";
			}
			if (x >= cameraCoordsX + 19 && x <= cameraCoordsX + 34 && y >= cameraCoordsY - 3 && y <= cameraCoordsY + 24) {
				keyboardText = keyboardText + "U";
			}
			if (x >= cameraCoordsX + 37 && x <= cameraCoordsX + 51 && y >= cameraCoordsY - 3 && y <= cameraCoordsY + 24) {
				keyboardText = keyboardText + "I";
			}
			if (x >= cameraCoordsX + 53 && x <= cameraCoordsX + 68 && y >= cameraCoordsY - 3 && y <= cameraCoordsY + 24) {
				keyboardText = keyboardText + "O";
			}
			if (x >= cameraCoordsX + 70 && x <= cameraCoordsX + 85 && y >= cameraCoordsY - 3 && y <= cameraCoordsY + 24) {
				keyboardText = keyboardText + "P";
			}

			if (x >= cameraCoordsX - 75 && x <= cameraCoordsX - 60 && y >= cameraCoordsY - 35 && y <= cameraCoordsY - 8) {
				keyboardText = keyboardText + "A";
			}
			if (x >= cameraCoordsX - 58 && x <= cameraCoordsX - 43 && y >= cameraCoordsY - 35 && y <= cameraCoordsY - 8) {
				keyboardText = keyboardText + "S";
			}
			if (x >= cameraCoordsX - 40 && x <= cameraCoordsX - 27 && y >= cameraCoordsY - 35 && y <= cameraCoordsY - 8) {
				keyboardText = keyboardText + "D";
			}
			if (x >= cameraCoordsX - 24 && x <= cameraCoordsX - 10 && y >= cameraCoordsY - 35 && y <= cameraCoordsY - 8) {
				keyboardText = keyboardText + "F";
			}
			if (x >= cameraCoordsX - 6 && x <= cameraCoordsX + 8 && y >= cameraCoordsY - 35 && y <= cameraCoordsY - 8) {
				keyboardText = keyboardText + "G";
			}
			if (x >= cameraCoordsX + 11 && x <= cameraCoordsX + 25 && y >= cameraCoordsY - 35 && y <= cameraCoordsY - 8) {
				keyboardText = keyboardText + "H";
			}
			if (x >= cameraCoordsX + 28 && x <= cameraCoordsX + 42 && y >= cameraCoordsY - 35 && y <= cameraCoordsY - 8) {
				keyboardText = keyboardText + "J";
			}
			if (x >= cameraCoordsX + 45 && x <= cameraCoordsX + 59 && y >= cameraCoordsY - 35 && y <= cameraCoordsY - 8) {
				keyboardText = keyboardText + "K";
			}
			if (x >= cameraCoordsX + 62 && x <= cameraCoordsX + 76 && y >= cameraCoordsY - 35 && y <= cameraCoordsY - 8) {
				keyboardText = keyboardText + "L";
			}

			if (x >= cameraCoordsX - 58 && x <= cameraCoordsX - 43 && y >= cameraCoordsY - 65 && y <= cameraCoordsY - 39) {
				keyboardText = keyboardText + "Z";
			}
			if (x >= cameraCoordsX - 40 && x <= cameraCoordsX - 27 && y >= cameraCoordsY - 65 && y <= cameraCoordsY - 39) {
				keyboardText = keyboardText + "X";
			}
			if (x >= cameraCoordsX - 24 && x <= cameraCoordsX - 10 && y >= cameraCoordsY - 65 && y <= cameraCoordsY - 39) {
				keyboardText = keyboardText + "C";
			}
			if (x >= cameraCoordsX - 6 && x <= cameraCoordsX + 8 && y >= cameraCoordsY - 65 && y <= cameraCoordsY - 39) {
				keyboardText = keyboardText + "V";
			}
			if (x >= cameraCoordsX + 11 && x <= cameraCoordsX + 25 && y >= cameraCoordsY - 65 && y <= cameraCoordsY - 39) {
				keyboardText = keyboardText + "B";
			}
			if (x >= cameraCoordsX + 28 && x <= cameraCoordsX + 42 && y >= cameraCoordsY - 65 && y <= cameraCoordsY - 39) {
				keyboardText = keyboardText + "N";
			}
			if (x >= cameraCoordsX + 45 && x <= cameraCoordsX + 59 && y >= cameraCoordsY - 65 && y <= cameraCoordsY - 39) {
				keyboardText = keyboardText + "M";
			}
			
			//Sair
			if (x >= cameraCoordsX - 99 && x <= cameraCoordsX - 72 && y >= cameraCoordsY - 95 && y <= cameraCoordsY - 70) {
				keyboardText = "";
				state = "Main";
			}

			//Space
			if (x >= cameraCoordsX - 57 && x <= cameraCoordsX + 25 && y >= cameraCoordsY - 95 && y <= cameraCoordsY - 70) {
				keyboardText = keyboardText + " ";
			}
			
			//BackSpace
			if (x >= cameraCoordsX + 30 && x <= cameraCoordsX + 58 && y >= cameraCoordsY - 95 && y <= cameraCoordsY - 70) {
				if (!keyboardText.isEmpty()) {
					keyboardText = keyboardText.substring(0, keyboardText.length() - 1);
				}
			}

			//Enter
				if (x >= cameraCoordsX + 67 && x <= cameraCoordsX + 95 && y >= cameraCoordsY - 95 && y <= cameraCoordsY - 70) {
					if(keyboardType.equals("Chat")) {
							
						try {
							gameControl.SendChat("Chat",player.AccountNumber,playernumString,keyboardText, new HttpCallback() {
							    @Override
							    public void onSuccess(String response) {
							    	if(response.contains("success")) {
							    		keyboardText = "";
										state = "Main";
							    	}
							    	else {
							    		//avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
							    		//aviso = true;
							    	}
							    }
		
							    @Override
							    public void onFailure(Throwable t) {
							       //System.out.println("Error: " + t.getMessage());
							       //avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
								   //aviso = true;
							    }
							});
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}
					
					if(keyboardType.equals("Party")) {
						player.party = keyboardText;
						state = "Main";			
					}
				}	
		}
		
		@Override
		public boolean touchDown(int p1, int p2, int pointer, int button) {
			if(playerDead) { return false; }
			
			Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));
			
			//Main
			//[Main State]//
			if(state.equals("Main")) {
				//Sit 
				if(coordsTouch.x > cameraCoordsX - 45 && coordsTouch.x < cameraCoordsX - 25 && coordsTouch.y > cameraCoordsY + 86 && coordsTouch.y < cameraCoordsY + 97) {
					if(!player.playerInCast.equals("none")) {
						if(player.playerSit.equals("none")) {
							player.playerSit = "yes";
						}
						else {
							player.playerSit = "none";
						}
					}
					return false;
				}
				
				if(playerDead) { return false; }
				if(player.playerSit.equals("yes")){ return false; }
	            if(defTrigged) { movement = false; return false; }
				if(player.playerInCast.equals("yes")) { movement = false; return false; }
				
				movement = true; 
				
				//Menu
				if(coordsTouch.x > cameraCoordsX - 99 && coordsTouch.x < cameraCoordsX - 61 && coordsTouch.y > cameraCoordsY + 57 && coordsTouch.y < cameraCoordsY + 96) {
					state = "Menu";
					return false;
				}
				
				//Action
				if(coordsTouch.x > cameraCoordsX + 62 && coordsTouch.x < cameraCoordsX + 73 && coordsTouch.y > cameraCoordsY -30 && coordsTouch.y < cameraCoordsY -6)  {
					CheckAction();
					if(autoattack){
						autoattack = false;
					}
					else
					{
						autoattack = true;
					}
					return false;
				}
				
				//Atk
				if(coordsTouch.x > cameraCoordsX + 47 && coordsTouch.x < cameraCoordsX + 56 && coordsTouch.y > cameraCoordsY -60 && coordsTouch.y < cameraCoordsY -35) {
					player.AtkTimer = "30";
					return false;
				}
				
				//Def
				if(coordsTouch.x > cameraCoordsX + 62 && coordsTouch.x < cameraCoordsX + 73 && coordsTouch.y > cameraCoordsY -60 && coordsTouch.y < cameraCoordsY -35)  {
					defTrigged = true;
					gameControl.SetCharacterDefense(defTrigged);
					defManual = 50;
					return false;
				}
				
				//Target
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY -60 && coordsTouch.y < cameraCoordsY -35) {
					ChangeTarget();
					return false;
				}
				//Skill 1
				if(coordsTouch.x > cameraCoordsX + 47 && coordsTouch.x < cameraCoordsX + 56 && coordsTouch.y > cameraCoordsY - 90 && coordsTouch.y < cameraCoordsY - 66) {
					if(skillUsed > 0) { return false; }
					if(!autoattack) { return false; }
					skillUsed = 200;
					CheckSkill(1);
					return false;
				}
				//Skill 2
				if(coordsTouch.x > cameraCoordsX + 63 && coordsTouch.x < cameraCoordsX + 72 && coordsTouch.y > cameraCoordsY - 90 && coordsTouch.y < cameraCoordsY - 66) {
					if(skillUsed > 0) { return false; }
					if(!autoattack) { return false; }
					skillUsed = 200;
					CheckSkill(2);
					return false;
				}
				//Skill 3
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY - 90 && coordsTouch.y < cameraCoordsY - 66) {			
					return false;
				}
				
				
				//hotkey1
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY - 30 && coordsTouch.y < cameraCoordsY - 6) {
					if(!player.hotkey1.equals("none")) {
						gameControl.UseItem(hotketcountitem1);
					}
					return false;
				}
				
				//hotkey2
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY + 1 && coordsTouch.y < cameraCoordsY + 24) {
					if(!player.hotkey2.equals("none")) {
						gameControl.UseItem(hotketcountitem2);
					}
					return false;
				}
				
				//Chat
				if(coordsTouch.x > cameraCoordsX - 60 && coordsTouch.x < cameraCoordsX - 49 && coordsTouch.y > cameraCoordsY + 77 && coordsTouch.y < cameraCoordsY + 97) {
					state = "keyboard";
					keyboardType = "Chat";
					return false;
				}
				
				//Party
				if(coordsTouch.x > cameraCoordsX - 60 && coordsTouch.x < cameraCoordsX - 49 && coordsTouch.y > cameraCoordsY + 56 && coordsTouch.y < cameraCoordsY + 75) {
					state = "keyboard";
					keyboardType = "Party";
					return false;
				}
			}
			
			if(state.equals("DungeonSelect")) {
				//Dungeon 1 - Sewers
				if(coordsTouch.x > cameraCoordsX - 46 && coordsTouch.x < cameraCoordsX + 32 && coordsTouch.y > cameraCoordsY + 70 && coordsTouch.y < cameraCoordsY + 83) {
					MapChange("Sewers");
					return false;
				}
				//Dungeon 1 - Forest
				if(coordsTouch.x > cameraCoordsX - 46 && coordsTouch.x < cameraCoordsX + 32 && coordsTouch.y > cameraCoordsY + 55 && coordsTouch.y < cameraCoordsY + 68) {
					MapChange("Forest");
					return false;
				}
				//Dungeon 2 - Watercave
				if(coordsTouch.x > cameraCoordsX - 46 && coordsTouch.x < cameraCoordsX + 32 && coordsTouch.y > cameraCoordsY + 37 && coordsTouch.y < cameraCoordsY + 49) {
					MapChange("Watercave");
					return false;
				}
				//Dungeon 2 - Desert
				if(coordsTouch.x > cameraCoordsX - 46 && coordsTouch.x < cameraCoordsX + 32 && coordsTouch.y > cameraCoordsY + 22 && coordsTouch.y < cameraCoordsY + 34) {
					MapChange("Desert");
					return false;
				}
				
				//Dungeon 3 - Vulcano
				if(coordsTouch.x > cameraCoordsX - 46 && coordsTouch.x < cameraCoordsX + 32 && coordsTouch.y > cameraCoordsY + 3 && coordsTouch.y < cameraCoordsY + 16) {
					MapChange("Vulcano");
					return false;
				}
				//Dungeon 3 - Mines
				if(coordsTouch.x > cameraCoordsX - 46 && coordsTouch.x < cameraCoordsX + 32 && coordsTouch.y > cameraCoordsY - 12 && coordsTouch.y < cameraCoordsY + 1) {
					MapChange("Mines");
					return false;
				}
				
				//Dungeon 4 - Snowpalace
				if(coordsTouch.x > cameraCoordsX - 46 && coordsTouch.x < cameraCoordsX + 32 && coordsTouch.y > cameraCoordsY - 29 && coordsTouch.y < cameraCoordsY - 17) {
					MapChange("Snowpalace");
					return false;
				}
				//Dungeon 4 - Swap
				if(coordsTouch.x > cameraCoordsX - 46 && coordsTouch.x < cameraCoordsX + 32 && coordsTouch.y > cameraCoordsY - 43 && coordsTouch.y < cameraCoordsY - 31) {
					MapChange("Swamp");
					return false;
				}
				
				//Dungeon 5 - Tower
				if(coordsTouch.x > cameraCoordsX - 46 && coordsTouch.x < cameraCoordsX + 32 && coordsTouch.y > cameraCoordsY - 43 && coordsTouch.y < cameraCoordsY - 31) {
					//MapChange("Tower");
					return false;
				}
				
				//Voltar
				if(coordsTouch.x > cameraCoordsX - 20 && coordsTouch.x < cameraCoordsX + 14 && coordsTouch.y > cameraCoordsY - 89 && coordsTouch.y < cameraCoordsY - 77) {
					state = "Main";
					return false;
				}
			}
			//[Menu State]//
			if(state.equals("Menu")) {
				
				//Voltar
				if(coordsTouch.x > cameraCoordsX + 68 && coordsTouch.x < cameraCoordsX + 82 && coordsTouch.y > cameraCoordsY + 69 && coordsTouch.y < cameraCoordsY + 84) {
					state = "Main";
					menuoption = "";
					return false;
				}
				
				//Hotkey 1
				if(coordsTouch.x > cameraCoordsX - 30 && coordsTouch.x < cameraCoordsX - 10 && coordsTouch.y > cameraCoordsY + 65 && coordsTouch.y < cameraCoordsY + 78) {
					menuoption = "hotkey1";
					return false;
				}
				
				//Hotkey 2
				if(coordsTouch.x > cameraCoordsX - 8 && coordsTouch.x < cameraCoordsX + 10 && coordsTouch.y > cameraCoordsY + 65 && coordsTouch.y < cameraCoordsY + 78) {
					menuoption = "hotkey2";
					return false;
				}
				
				//Descartar
				if(coordsTouch.x > cameraCoordsX + 50 && coordsTouch.x < cameraCoordsX + 80 && coordsTouch.y > cameraCoordsY - 56 && coordsTouch.y < cameraCoordsY - 41) {
					if(menuoption.equals("descartar")) { menuoption = ""; return false; }
					menuoption = "descartar";
					return false;
				}
				
				
				//Desligar touch
				if(coordsTouch.x > cameraCoordsX + 19 && coordsTouch.x < cameraCoordsX + 31 && coordsTouch.y > cameraCoordsY - 42 && coordsTouch.y < cameraCoordsY - 20) {
					if(controlstate.equals("PC")) { controlstate = "Mobile"; return false;}
					if(controlstate.equals("Mobile")) { controlstate = "PC"; return false;}
					return false;
				}
				
				//Sair do Jogo
				if(coordsTouch.x > cameraCoordsX + 32 && coordsTouch.x < cameraCoordsX + 45 && coordsTouch.y > cameraCoordsY - 42 && coordsTouch.y < cameraCoordsY - 20) {
					this.screen.screenSwitch("SplashScreen","",playernum);
					return false;
				}
				
				
				//Item 1
				if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 32 && coordsTouch.y > cameraCoordsY + 42 && coordsTouch.y < cameraCoordsY + 64) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(0,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(0,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(0); menuoption = ""; return false; }
					gameControl.UseItem(0);
					return false;
				}
				
				//Item 2
				if(coordsTouch.x > cameraCoordsX - 30 && coordsTouch.x < cameraCoordsX - 18 && coordsTouch.y > cameraCoordsY + 42 && coordsTouch.y < cameraCoordsY + 64) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(1,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(1,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(1); menuoption = ""; return false; }
					gameControl.UseItem(1);
					return false;
				}
				
				//Item 3
				if(coordsTouch.x > cameraCoordsX - 16 && coordsTouch.x < cameraCoordsX - 4 && coordsTouch.y > cameraCoordsY + 42 && coordsTouch.y < cameraCoordsY + 64) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(2,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(2,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(2); menuoption = ""; return false; }
					gameControl.UseItem(2);
					return false;
				}
				
				//Item 4
				if(coordsTouch.x > cameraCoordsX - 3 && coordsTouch.x < cameraCoordsX + 10 && coordsTouch.y > cameraCoordsY + 42 && coordsTouch.y < cameraCoordsY + 64) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(3,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(3,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(3); menuoption = ""; return false; }
					gameControl.UseItem(3);
					return false;
				}
				
				//Item 5
				if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 32 && coordsTouch.y > cameraCoordsY + 19 && coordsTouch.y < cameraCoordsY + 39) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(4,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(4,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(4); menuoption = ""; return false; }
					gameControl.UseItem(4);
					return false;
				}
				
				//Item 6
				if(coordsTouch.x > cameraCoordsX - 30 && coordsTouch.x < cameraCoordsX - 18 && coordsTouch.y > cameraCoordsY + 19 && coordsTouch.y < cameraCoordsY + 39) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(5,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(5,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(5); menuoption = ""; return false; }
					gameControl.UseItem(5);
					return false;
				}
				
				//Item 7
				if(coordsTouch.x > cameraCoordsX - 16 && coordsTouch.x < cameraCoordsX - 4 && coordsTouch.y > cameraCoordsY + 19 && coordsTouch.y < cameraCoordsY + 39) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(6,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(6,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(6); menuoption = ""; return false; }
					gameControl.UseItem(6);
					return false;
				}
				
				//Item 8
				if(coordsTouch.x > cameraCoordsX - 3 && coordsTouch.x < cameraCoordsX + 10 && coordsTouch.y > cameraCoordsY + 19 && coordsTouch.y < cameraCoordsY + 39) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(7,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(7,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(7); menuoption = ""; return false; }
					gameControl.UseItem(7);
					return false;
				}
				
				//Item 9
				if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 32 && coordsTouch.y > cameraCoordsY - 5 && coordsTouch.y < cameraCoordsY + 15) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(8,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(8,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(8); menuoption = ""; return false; }
					gameControl.UseItem(8);
					return false;
				}
				
				//Item 10
				if(coordsTouch.x > cameraCoordsX - 30 && coordsTouch.x < cameraCoordsX - 18 && coordsTouch.y > cameraCoordsY - 5 && coordsTouch.y < cameraCoordsY + 15) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(9,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(9,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(9); menuoption = ""; return false; }
					gameControl.UseItem(9);
					return false;
				}
				
				//Item 11
				if(coordsTouch.x > cameraCoordsX - 16 && coordsTouch.x < cameraCoordsX - 4 && coordsTouch.y > cameraCoordsY - 5 && coordsTouch.y < cameraCoordsY + 15) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(10,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(10,2); menuoption = ""; return false;  }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(10); menuoption = ""; return false; }
					gameControl.UseItem(10);
					return false;
				}
				
				//Item 12
				if(coordsTouch.x > cameraCoordsX - 3 && coordsTouch.x < cameraCoordsX + 10 && coordsTouch.y > cameraCoordsY - 5 && coordsTouch.y < cameraCoordsY + 15) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(11,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(11,2); menuoption = ""; return false;  }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(11); menuoption = ""; return false; }
					gameControl.UseItem(11);
					return false;
				}
				
				//Item 13
				if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 32 && coordsTouch.y > cameraCoordsY - 30 && coordsTouch.y < cameraCoordsY - 8) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(12,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(12,2); menuoption = ""; return false;  }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(12); menuoption = ""; return false; }
					gameControl.UseItem(12);
					return false;
				}
				
				//Item 14
				if(coordsTouch.x > cameraCoordsX - 30 && coordsTouch.x < cameraCoordsX - 18 && coordsTouch.y > cameraCoordsY - 30 && coordsTouch.y < cameraCoordsY - 8) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(13,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(13,2); menuoption = ""; return false;  }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(13); menuoption = ""; return false; }
					gameControl.UseItem(13);
					return false;
				}
				
				//Item 15
				if(coordsTouch.x > cameraCoordsX - 16 && coordsTouch.x < cameraCoordsX - 4 && coordsTouch.y > cameraCoordsY - 30 && coordsTouch.y < cameraCoordsY - 8) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(14,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(14,2); menuoption = ""; return false;  }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(14); menuoption = ""; return false; }
					gameControl.UseItem(14);
					return false;
				}
				
				//Item 16
				if(coordsTouch.x > cameraCoordsX - 3 && coordsTouch.x < cameraCoordsX + 10 && coordsTouch.y > cameraCoordsY - 30 && coordsTouch.y < cameraCoordsY - 8) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(15,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(15,2); menuoption = ""; return false;  }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(15); menuoption = ""; return false; }
					gameControl.UseItem(15);
					return false;
				}
				
				
				//Status point
				//STR
				if(coordsTouch.x > cameraCoordsX - 81 && coordsTouch.x < cameraCoordsX - 69 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					if(!menuoption.equals("")) { return false; }
					CheckStatus("Str");
					return false;
				}
				//VIT
				if(coordsTouch.x > cameraCoordsX - 67 && coordsTouch.x < cameraCoordsX - 55 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					if(!menuoption.equals("")) { return false; }
					if(!player.buffA.equals("none")) { return false; }
					if(!player.buffB.equals("none")) { return false; }
					if(!player.buffC.equals("none")) { return false; }
					CheckStatus("Vit");
					return false;
				}
				//AGI
				if(coordsTouch.x > cameraCoordsX - 54 && coordsTouch.x < cameraCoordsX - 41 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					if(!menuoption.equals("")) { return false; }
					if(!player.buffA.equals("none")) { return false; }
					if(!player.buffB.equals("none")) { return false; }
					if(!player.buffC.equals("none")) { return false; }
					CheckStatus("Agi");
					return false;
				}
				//WIS
				if(coordsTouch.x > cameraCoordsX - 40 && coordsTouch.x < cameraCoordsX - 27 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					if(!menuoption.equals("")) { return false; }
					if(!player.buffA.equals("none")) { return false; }
					if(!player.buffB.equals("none")) { return false; }
					if(!player.buffC.equals("none")) { return false; }
					CheckStatus("Wis");
					return false;
				}
				//DES
				if(coordsTouch.x > cameraCoordsX - 26 && coordsTouch.x < cameraCoordsX - 14 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					if(!menuoption.equals("")) { return false; }
					if(!player.buffA.equals("none")) { return false; }
					if(!player.buffB.equals("none")) { return false; }
					if(!player.buffC.equals("none")) { return false; }
					CheckStatus("Dex");
					return false;
				}
				//LUK
				if(coordsTouch.x > cameraCoordsX - 12 && coordsTouch.x < cameraCoordsX + 0 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					if(!menuoption.equals("")) { return false; }
					if(!player.buffA.equals("none")) { return false; }
					if(!player.buffB.equals("none")) { return false; }
					if(!player.buffC.equals("none")) { return false; }
					CheckStatus("Luk");
					return false;
				}
				//RES
				if(coordsTouch.x > cameraCoordsX + 1 && coordsTouch.x < cameraCoordsX + 14 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					if(!menuoption.equals("")) { return false; } 
					if(!player.buffA.equals("none")) { return false; }
					if(!player.buffB.equals("none")) { return false; }
					if(!player.buffC.equals("none")) { return false; }
					CheckStatus("Res");
					return false;
				}
				
				//unequip
				if(coordsTouch.x > cameraCoordsX + 68 && coordsTouch.x < cameraCoordsX + 80 && coordsTouch.y > cameraCoordsY + 40 && coordsTouch.y < cameraCoordsY + 62) {
					gameControl.UnequipHat();
					return false;
				}
				
				////// Crystals
				//Crystal 1
				if(coordsTouch.x > cameraCoordsX + 13 && coordsTouch.x < cameraCoordsX + 26 && coordsTouch.y > cameraCoordsY + 1 && coordsTouch.y < cameraCoordsY + 25) {
					if(!menuoption.equals("")) { return false; }
					if(!player.buffA.equals("none")) { return false; }
					if(!player.buffB.equals("none")) { return false; }
					if(!player.buffC.equals("none")) { return false; }
					gameControl.RemoveCrystals(1);
					return false;
				}
				//Crystal 2
				if(coordsTouch.x > cameraCoordsX + 27 && coordsTouch.x < cameraCoordsX + 39 && coordsTouch.y > cameraCoordsY + 1 && coordsTouch.y < cameraCoordsY + 25) {
					if(!menuoption.equals("")) { return false; }
					if(!player.buffA.equals("none")) { return false; }
					if(!player.buffB.equals("none")) { return false; }
					if(!player.buffC.equals("none")) { return false; }
					gameControl.RemoveCrystals(2);
					return false;
				}
				//Crystal 3
				if(coordsTouch.x > cameraCoordsX + 41 && coordsTouch.x < cameraCoordsX + 53 && coordsTouch.y > cameraCoordsY + 1 && coordsTouch.y < cameraCoordsY + 25) {
					if(!menuoption.equals("")) { return false; }
					if(!player.buffA.equals("none")) { return false; }
					if(!player.buffB.equals("none")) { return false; }
					if(!player.buffC.equals("none")) { return false; }
					gameControl.RemoveCrystals(3);
					return false;
				}
				//Crystal 4
				if(coordsTouch.x > cameraCoordsX + 55 && coordsTouch.x < cameraCoordsX + 67 && coordsTouch.y > cameraCoordsY + 1 && coordsTouch.y < cameraCoordsY + 25) {
					if(!menuoption.equals("")) { return false; }
					if(!player.buffA.equals("none")) { return false; }
					if(!player.buffB.equals("none")) { return false; }
					if(!player.buffC.equals("none")) { return false; }
					gameControl.RemoveCrystals(4);
					return false;
				}
				
				//config
				if(coordsTouch.x > cameraCoordsX + 28 && coordsTouch.x < cameraCoordsX + 40 && coordsTouch.y > cameraCoordsY - 35 && coordsTouch.y < cameraCoordsY - 13) {
					//onlineresponse = gameControl.OnlineManager("Upload","","");
					//if(onlineresponse.equals("Atualizado")) {
					//	msgShowTime = 100;
					//}
					return false;
				}
			}
			
			//Shops
			if(state.equals("Shop")) {
				if(shopname.equals("refrishop")) { 
					if(coordsTouch.x > cameraCoordsX + 51 && coordsTouch.x < cameraCoordsX + 61 && coordsTouch.y > cameraCoordsY + 60 && coordsTouch.y < cameraCoordsY + 75) {
						state = "Main";
						return false; 
					}
					//pote pequena HP
					if(coordsTouch.x > cameraCoordsX - 61 && coordsTouch.x < cameraCoordsX - 47 && coordsTouch.y > cameraCoordsY + 37 && coordsTouch.y < cameraCoordsY + 59) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("refrishop", 1);
						return false; 
					}
					//pote grande HP
					if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 30 && coordsTouch.y > cameraCoordsY + 37 && coordsTouch.y < cameraCoordsY + 59) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("refrishop", 2);
						return false; 
					}
					//pote ultra HP
					if(coordsTouch.x > cameraCoordsX - 26 && coordsTouch.x < cameraCoordsX - 13 && coordsTouch.y > cameraCoordsY + 37 && coordsTouch.y < cameraCoordsY + 59) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("refrishop", 3);
						return false; 
					}
					//pote pequena MP
					if(coordsTouch.x > cameraCoordsX - 61 && coordsTouch.x < cameraCoordsX - 47 && coordsTouch.y > cameraCoordsY + 15 && coordsTouch.y < cameraCoordsY + 33) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("refrishop", 4);
						return false; 
					}
					//pote grande MP
					if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 30 && coordsTouch.y > cameraCoordsY + 15 && coordsTouch.y < cameraCoordsY + 33) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("refrishop", 5);
						return false; 
					}
					//pote ultra MP
					if(coordsTouch.x > cameraCoordsX - 26 && coordsTouch.x < cameraCoordsX - 13 && coordsTouch.y > cameraCoordsY + 15 && coordsTouch.y < cameraCoordsY + 33) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("refrishop", 6);
						return false; 
					}
					//pote pequena st
					if(coordsTouch.x > cameraCoordsX - 61 && coordsTouch.x < cameraCoordsX - 47 && coordsTouch.y > cameraCoordsY - 9 && coordsTouch.y < cameraCoordsY + 10) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("refrishop", 7);
						return false; 
					}
					//pote grande st
					if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 30 && coordsTouch.y > cameraCoordsY - 9 && coordsTouch.y < cameraCoordsY + 10) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("refrishop", 8);
						return false; 
					}
					//Chipz
					if(coordsTouch.x > cameraCoordsX - 61 && coordsTouch.x < cameraCoordsX - 47 && coordsTouch.y > cameraCoordsY - 32 && coordsTouch.y < cameraCoordsY - 12) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("refrishop", 9);
						return false; 
					}
					//Turkey
					if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 30 && coordsTouch.y > cameraCoordsY - 32 && coordsTouch.y < cameraCoordsY - 12) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("refrishop", 10);
						return false; 
					}
					//Egg
					if(coordsTouch.x > cameraCoordsX - 26 && coordsTouch.x < cameraCoordsX - 13 && coordsTouch.y > cameraCoordsY - 32 && coordsTouch.y < cameraCoordsY - 12) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("refrishop", 11);
						return false; 
					}
				}
				if(shopname.equals("weaponshop")) { 
					if(coordsTouch.x > cameraCoordsX + 51 && coordsTouch.x < cameraCoordsX + 61 && coordsTouch.y > cameraCoordsY + 60 && coordsTouch.y < cameraCoordsY + 75) {
						state = "Main";
						return false; 
					}
					if(coordsTouch.x > cameraCoordsX - 20 && coordsTouch.x < cameraCoordsX + 14 && coordsTouch.y > cameraCoordsY - 12 && coordsTouch.y < cameraCoordsY + 2) {
						showbuymsg = gameControl.CheckBuyWeapon();
						return false; 
					}
				}
				if(shopname.equals("jobmaster")) {  
					if(coordsTouch.x > cameraCoordsX - 20 && coordsTouch.x < cameraCoordsX + 12 && coordsTouch.y > cameraCoordsY - 70 && coordsTouch.y < cameraCoordsY - 52) {
						state = "Main";
						return false; 
					}
					if(coordsTouch.x > cameraCoordsX - 63 && coordsTouch.x < cameraCoordsX - 40 && coordsTouch.y > cameraCoordsY + 41 && coordsTouch.y < cameraCoordsY + 52) {
						ChangeJob("Espadachim");
						return false; 
					}
					if(coordsTouch.x > cameraCoordsX - 38.4f && coordsTouch.x < cameraCoordsX - 16 && coordsTouch.y > cameraCoordsY + 41 && coordsTouch.y < cameraCoordsY + 52) {
						ChangeJob("Mago");
						return false; 
					}
					if(coordsTouch.x > cameraCoordsX - 14 && coordsTouch.x < cameraCoordsX + 8 && coordsTouch.y > cameraCoordsY + 41 && coordsTouch.y < cameraCoordsY + 52) {
						ChangeJob("Atirador");
						return false; 
					}
					if(coordsTouch.x > cameraCoordsX + 10 && coordsTouch.x < cameraCoordsX + 32 && coordsTouch.y > cameraCoordsY + 41 && coordsTouch.y < cameraCoordsY + 52) {
						ChangeJob("Curandeiro");
						return false; 
					}
					if(coordsTouch.x > cameraCoordsX + 34 && coordsTouch.x < cameraCoordsX + 57 && coordsTouch.y > cameraCoordsY + 41 && coordsTouch.y < cameraCoordsY + 52) {
						ChangeJob("Ladrao");
						return false; 
					}
				}
				if(shopname.equals("cristalized")) { 
					//Sair
					if(coordsTouch.x > cameraCoordsX + 41 && coordsTouch.x < cameraCoordsX + 60 && coordsTouch.y > cameraCoordsY - 76 && coordsTouch.y <  - 60) {
						state = "Main";
						return false; 
					}
					
					//Cristal Amarelo
					if(coordsTouch.x > cameraCoordsX + 1 && coordsTouch.x < cameraCoordsX + 60 && coordsTouch.y > cameraCoordsY + 42 && coordsTouch.y < cameraCoordsY + 58) {
						gameControl.CrystalExchanged("yellow");
						return false; 
					}
					//Cristal Azul
					if(coordsTouch.x > cameraCoordsX + 1 && coordsTouch.x < cameraCoordsX + 60 && coordsTouch.y > cameraCoordsY + 18 && coordsTouch.y < cameraCoordsY + 32) {
						gameControl.CrystalExchanged("blue");
						return false; 
					}
					//Cristal Roxo
					if(coordsTouch.x > cameraCoordsX + 1 && coordsTouch.x < cameraCoordsX + 60 && coordsTouch.y > cameraCoordsY - 30 && coordsTouch.y < cameraCoordsY - 14) {
						gameControl.CrystalExchanged("purple");
						return false; 
					}
					//Cristal Verde
					if(coordsTouch.x > cameraCoordsX + 1 && coordsTouch.x < cameraCoordsX + 60 && coordsTouch.y > cameraCoordsY - 53 && coordsTouch.y < cameraCoordsY - 36) {
						gameControl.CrystalExchanged("green");
						return false; 
					}
					//Cristal Vermelho
					if(coordsTouch.x > cameraCoordsX + 1 && coordsTouch.x < cameraCoordsX + 60 && coordsTouch.y > cameraCoordsY - 76 && coordsTouch.y < cameraCoordsY - 60) {
						gameControl.CrystalExchanged("red");
						return false; 
					}
				}
				if(shopname.equals("lojaroupas1")) { 
					if(coordsTouch.x > cameraCoordsX + 51 && coordsTouch.x < cameraCoordsX + 61 && coordsTouch.y > cameraCoordsY + 60 && coordsTouch.y < cameraCoordsY + 75) {
						state = "Main";
						return false;    
					}
					///////fila 1
					if(coordsTouch.x > cameraCoordsX - 61 && coordsTouch.x < cameraCoordsX - 47 && coordsTouch.y > cameraCoordsY + 38 && coordsTouch.y < cameraCoordsY + 57) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 1); //hatbear
						return false;
					}
					if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 30 && coordsTouch.y > cameraCoordsY + 38 && coordsTouch.y < cameraCoordsY + 57) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 2); //bunnyhat
						return false;
					}
					if(coordsTouch.x > cameraCoordsX - 27 && coordsTouch.x < cameraCoordsX - 12 && coordsTouch.y > cameraCoordsY + 38 && coordsTouch.y < cameraCoordsY + 57) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 3); //headset
						return false;
					}
					if(coordsTouch.x > cameraCoordsX - 10 && coordsTouch.x < cameraCoordsX + 5 && coordsTouch.y > cameraCoordsY + 38 && coordsTouch.y < cameraCoordsY + 57) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 4); //basictopM
						return false;
					}
					if(coordsTouch.x > cameraCoordsX + 7 && coordsTouch.x < cameraCoordsX + 20 && coordsTouch.y > cameraCoordsY + 38 && coordsTouch.y < cameraCoordsY + 57) {
						//showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 5); //vazio
						return false; 
					}
					if(coordsTouch.x > cameraCoordsX + 24 && coordsTouch.x < cameraCoordsX + 38 && coordsTouch.y > cameraCoordsY + 38 && coordsTouch.y < cameraCoordsY + 57) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 6); //basictopF
						return false;
					}
					if(coordsTouch.x > cameraCoordsX + 41 && coordsTouch.x < cameraCoordsX + 55 && coordsTouch.y > cameraCoordsY + 38 && coordsTouch.y < cameraCoordsY + 57) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 7); //vazio
						return false;
					}
					
					
					/////////fila 2
					if(coordsTouch.x > cameraCoordsX - 61 && coordsTouch.x < cameraCoordsX - 47 && coordsTouch.y > cameraCoordsY + 15 && coordsTouch.y < cameraCoordsY + 35) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 8); //hatmagician
						return false;
					}
					if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 30 && coordsTouch.y > cameraCoordsY + 15 && coordsTouch.y < cameraCoordsY + 35) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 9); //hatpirate
						return false;
					}
					if(coordsTouch.x > cameraCoordsX - 27 && coordsTouch.x < cameraCoordsX - 12 && coordsTouch.y > cameraCoordsY + 15 && coordsTouch.y < cameraCoordsY + 35) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 10); //hatsanta
						return false;
					}
					if(coordsTouch.x > cameraCoordsX - 10 && coordsTouch.x < cameraCoordsX + 5 && coordsTouch.y > cameraCoordsY + 15 && coordsTouch.y < cameraCoordsY + 35) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 11); //basicbottomM
						return false;
					}
					if(coordsTouch.x > cameraCoordsX + 7 && coordsTouch.x < cameraCoordsX + 20 && coordsTouch.y > cameraCoordsY + 15 && coordsTouch.y < cameraCoordsY + 35) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 12); //basicfooterM
						return false; 
					}
					if(coordsTouch.x > cameraCoordsX + 24 && coordsTouch.x < cameraCoordsX + 38 && coordsTouch.y > cameraCoordsY + 15 && coordsTouch.y < cameraCoordsY + 35) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 13); //basicbottomF
						return false;
					}
					if(coordsTouch.x > cameraCoordsX + 41 && coordsTouch.x < cameraCoordsX + 55 && coordsTouch.y > cameraCoordsY + 15 && coordsTouch.y < cameraCoordsY + 35) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 14); //basicfooterF
						return false;
					}
					
					
					
					///////fila 3
					if(coordsTouch.x > cameraCoordsX - 61 && coordsTouch.x < cameraCoordsX - 47 && coordsTouch.y > cameraCoordsY - 9 && coordsTouch.y < cameraCoordsY + 11) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 15); //hatslime
						return false;
					}
					if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 30 && coordsTouch.y > cameraCoordsY - 9 && coordsTouch.y < cameraCoordsY + 11) {
						//showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 16); //vazio
						return false;
					}
					if(coordsTouch.x > cameraCoordsX - 27 && coordsTouch.x < cameraCoordsX - 12 && coordsTouch.y > cameraCoordsY - 9 && coordsTouch.y < cameraCoordsY + 11) {
						//showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 17); //vazio
						return false;
					}
					if(coordsTouch.x > cameraCoordsX - 10 && coordsTouch.x < cameraCoordsX + 5 && coordsTouch.y > cameraCoordsY - 9 && coordsTouch.y < cameraCoordsY + 11) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 18); //sporttopM
						return false;
					}
					if(coordsTouch.x > cameraCoordsX + 7 && coordsTouch.x < cameraCoordsX + 20 && coordsTouch.y > cameraCoordsY - 9 && coordsTouch.y < cameraCoordsY + 11) {
						//showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 19); //vazio
						return false; 
					}
					if(coordsTouch.x > cameraCoordsX + 24 && coordsTouch.x < cameraCoordsX + 38 && coordsTouch.y > cameraCoordsY - 9 && coordsTouch.y < cameraCoordsY + 11) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 20); //sporttopF
						return false;
					}
					if(coordsTouch.x > cameraCoordsX + 41 && coordsTouch.x < cameraCoordsX + 55 && coordsTouch.y > cameraCoordsY - 9 && coordsTouch.y < cameraCoordsY + 11) {
						//showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 21); //vazio
						return false;
					}
					
					
					
					
				///////fila 4
				if(coordsTouch.x > cameraCoordsX - 61 && coordsTouch.x < cameraCoordsX - 47 && coordsTouch.y > cameraCoordsY - 33 && coordsTouch.y < cameraCoordsY - 13) {
					//showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 22); //vazio
					return false;
				}
				if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 30 && coordsTouch.y > cameraCoordsY - 33 && coordsTouch.y < cameraCoordsY - 13) {
					//showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 23); //vazio
					return false;
				}
				if(coordsTouch.x > cameraCoordsX - 27 && coordsTouch.x < cameraCoordsX - 12 && coordsTouch.y > cameraCoordsY - 33 && coordsTouch.y < cameraCoordsY - 13) {
					//showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 24); //vazio
					return false;
				}
				if(coordsTouch.x > cameraCoordsX - 10 && coordsTouch.x < cameraCoordsX + 5 && coordsTouch.y > cameraCoordsY - 33 && coordsTouch.y < cameraCoordsY - 13) {
					showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 25); //sportbottomM
					return false;
				}
				if(coordsTouch.x > cameraCoordsX + 7 && coordsTouch.x < cameraCoordsX + 20 && coordsTouch.y > cameraCoordsY - 33 && coordsTouch.y < cameraCoordsY - 13) {
					showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 26); //sportfooterM
					return false; 
				}
				if(coordsTouch.x > cameraCoordsX + 24 && coordsTouch.x < cameraCoordsX + 38 && coordsTouch.y > cameraCoordsY - 33 && coordsTouch.y < cameraCoordsY - 13) {
					showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 27); //sportbottomF
					return false;
				}
				if(coordsTouch.x > cameraCoordsX + 41 && coordsTouch.x < cameraCoordsX + 55 && coordsTouch.y > cameraCoordsY - 33 && coordsTouch.y < cameraCoordsY - 13) {
					showbuymsg = gameControl.CheckBuyItemStreetsA("lojaroupas1", 28); //sportfooterF
					return false;
				}
					
					
				}
			}
			
			if (state.contains("keyboard")) {
				this.KeyboardKeyPressed(coordsTouch.x, coordsTouch.y);
				return false;
			}
			
			return false;
		}
		
		public void ChangeJob(String job) {
			int playerlevel = Integer.parseInt(player.Level);
			if(player.Job.equals("Aprendiz") && playerlevel >= 10) {
				player.Job = job;
				
				if(player.Job.equals("Espadachim")) {
					player.Weapon = "woodsword_c";
				}
				if(player.Job.equals("Mago")) {
					player.Weapon = "stickrod_c";
				}
				if(player.Job.equals("Atirador")) {
					player.Weapon = "basicpistol_c";
				}
				if(player.Job.equals("Curandeiro")) {
					player.Weapon = "stickrod_c";
				}
				if(player.Job.equals("Ladrao")) {
					player.Weapon = "basicdagger_c";
				}	
			}
		}
		
		public void ShowMobs() {			
			
			listMonsters = gameControl.RecoverMonsterList();
			float playerPosX = Float.parseFloat(player.PosX);
			float playerPosY = Float.parseFloat(player.PosY);
			
			if(player.Map.equals("Sewers") || player.Map.equals("Forest") || 
			   player.Map.equals("Watercave") || player.Map.equals("Desert") ||
			   player.Map.equals("Vulcano") || player.Map.equals("Mines") || 
			   player.Map.equals("Snowpalace") || player.Map.equals("Swamp")){
				for(int i = 0; i < listMonsters.size(); i++) {
					
						//Target do player
						if(player.Target.equals(listMonsters.get(i).MobID)) {
							spr_target = gameControl.GetUX("target", 0, 0);
							spr_target.setPosition(listMonsters.get(i).MobPosX + 2, listMonsters.get(i).MobPosY + 16);
							spr_target.setSize(4,8);
							spr_target.draw(game.batch);
						}
									
						mobTimerFrame = listMonsters.get(i).MobFrameTime;
						if(mobTimerFrame > 0) {
							mobTimerFrame--;
							listMonsters.get(i).MobFrameTime = mobTimerFrame;
						}
						if(mobTimerFrame <= 0) {
							mobTimerFrame = 30;
							mobFrame = listMonsters.get(i).MobFrame;
							mobFrame++;
							if(mobFrame > 3) { mobFrame = 1;}
							listMonsters.get(i).MobFrame = mobFrame;
							listMonsters.get(i).MobFrameTime = 18;
						}
						
						//Sem Target
						if(listMonsters.get(i).MobTarget.equals("none")) {
							if (listMonsters.get(i).MobPosX < listMonsters.get(i).MobPosXFinal) {
								listMonsters.get(i).MobPosX += 0.07f;
							}
							if (listMonsters.get(i).MobPosX > listMonsters.get(i).MobPosXFinal) {
								listMonsters.get(i).MobPosX -= 0.07f;
							}
							if (listMonsters.get(i).MobPosY > listMonsters.get(i).MobPosYFinal) {
								listMonsters.get(i).MobPosY -= 0.07f;
							}
							if (listMonsters.get(i).MobPosY < listMonsters.get(i).MobPosYFinal) {
								listMonsters.get(i).MobPosY += 0.07f;
							}
						}
						
						if(listMonsters.get(i).MobTarget.equals(player.Name)) {
							if(listMonsters.get(i).MobPosX < playerPosX + 3) { listMonsters.get(i).MobPosX = listMonsters.get(i).MobPosX + 0.07f; }
							if(listMonsters.get(i).MobPosX > playerPosX + 3) { listMonsters.get(i).MobPosX = listMonsters.get(i).MobPosX - 0.07f; }
							if(listMonsters.get(i).MobPosY < playerPosY - 6 ) { listMonsters.get(i).MobPosY = listMonsters.get(i).MobPosY + 0.07f; }
							if(listMonsters.get(i).MobPosY > playerPosY - 6) { listMonsters.get(i).MobPosY = listMonsters.get(i).MobPosY - 0.07f; }
						}
						if(!listMonsters.get(i).MobTarget.equals("none")) {
							if (lstOnlinePlayers == null || lstOnlinePlayers.isEmpty() || lstOnlinePlayers.size() == 0) {
							} else {
								for (int p = 0; p < lstOnlinePlayers.size(); p++) {
									for (int j = 0; j < listMonsters.size(); j++) {
										if (lstOnlinePlayers.get(p).Name.equals(listMonsters.get(j).MobTarget)) {
											float playerOnlineX = Float.parseFloat(lstOnlinePlayers.get(p).PosX);
											float playerOnlineY = Float.parseFloat(lstOnlinePlayers.get(p).PosY);
							
											if (listMonsters.get(j).MobPosX < playerOnlineX + 3) {
												listMonsters.get(j).MobPosX += 0.07f;
											}
											if (listMonsters.get(j).MobPosX > playerOnlineX + 3) {
												listMonsters.get(j).MobPosX -= 0.07f;
											}
											if (listMonsters.get(j).MobPosY < playerOnlineY - 6) {
												listMonsters.get(j).MobPosY += 0.07f;
											}
											if (listMonsters.get(j).MobPosY > playerOnlineY - 6) {
												listMonsters.get(j).MobPosY -= 0.07f;
											}
										}
									}
								}
							}		
						}
						
						//Limit screen
						if(mobPositionCoordY >= 18f && listMonsters.get(i).MobDead.equals("no")) 
						{						
							listMonsters.get(i).MobPosY = -112f;
							listMonsters.get(i).MobPosX = 96.5f;
						}
						if(mobPositionCoordY <= -190.5f && listMonsters.get(i).MobDead.equals("no")) 
						{
							listMonsters.get(i).MobPosY = -112f;
							listMonsters.get(i).MobPosX = 67;
						}
						if(mobPositionCoordX >= 185 && listMonsters.get(i).MobDead.equals("no")) 
						{
							listMonsters.get(i).MobPosY = -112f;
							listMonsters.get(i).MobPosX = 65;
						}
						if(mobPositionCoordX <= -77f && listMonsters.get(i).MobDead.equals("no")) 
						{
							listMonsters.get(i).MobPosY = -112f;
							listMonsters.get(i).MobPosX = 65;
						}
						
						if(player.Map.equals("Sewers")) { spr_monster = gameControl.GetMonsterSewers(listMonsters.get(i).MobName, listMonsters.get(i).MobFrame, ""); }
						if(player.Map.equals("Forest")) { spr_monster = gameControl.GetMonsterForest(listMonsters.get(i).MobName, listMonsters.get(i).MobFrame, ""); }
						if(player.Map.equals("Watercave")) { spr_monster = gameControl.GetMonsterWatercave(listMonsters.get(i).MobName, listMonsters.get(i).MobFrame, ""); }
						if(player.Map.equals("Desert")) { spr_monster = gameControl.GetMonsterDesert(listMonsters.get(i).MobName, listMonsters.get(i).MobFrame, ""); }
						if(player.Map.equals("Mines")) { spr_monster = gameControl.GetMonsterMines(listMonsters.get(i).MobName, listMonsters.get(i).MobFrame, ""); }
						if(player.Map.equals("Vulcano")) { spr_monster = gameControl.GetMonsterVulcano(listMonsters.get(i).MobName, listMonsters.get(i).MobFrame, ""); }
						if(player.Map.equals("Snowpalace")) { spr_monster = gameControl.GetMonsterSnowpalace(listMonsters.get(i).MobName, listMonsters.get(i).MobFrame, ""); }
						if(player.Map.equals("Swamp")) { spr_monster = gameControl.GetMonsterSwamp(listMonsters.get(i).MobName, listMonsters.get(i).MobFrame, ""); }
						
						spr_monster.setPosition(listMonsters.get(i).MobPosX, listMonsters.get(i).MobPosY);
						spr_monster.setSize(listMonsters.get(i).MobSizeX, listMonsters.get(i).MobSizeY);
						if(listMonsters.get(i).MobDead.equals("no")){ spr_monster.draw(game.batch); }	
						
						mobPositionCoordX = listMonsters.get(i).MobPosX;
						mobPositionCoordY = listMonsters.get(i).MobPosY;
						mobPositionCoordY = mobPositionCoordY - 0.2f;
						font_master.getData().setScale(0.10f,0.15f);
						font_master.setUseIntegerPositions(false);
						if(listMonsters.get(i).MobDead.equals("no")){ 
						font_master.draw(game.batch, listMonsters.get(i).MobName,mobPositionCoordX, mobPositionCoordY);
						font_master.draw(game.batch, " HP :" + listMonsters.get(i).MobHp + "/" + listMonsters.get(i).MobHpMax ,mobPositionCoordX - 4, mobPositionCoordY - 8);
						}
						
					}			
			}
		}
		
		@Override
		public void input(String text) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void canceled() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean keyDown(int keycode) {
			
			if(playerDead) { return false; }
			if(player.playerSit.equals("yes")){ return false; }
            if(defTrigged) { movement = false; return false; }
			if(player.playerInCast.equals("yes")) { movement = false; return false; }
			
			if(state.equals("Main")) {
				movement = true;		
				downKeys.add(keycode);
		        if (downKeys.size >= 2){
		            onMultipleKeysDown(keycode);
		        }
		        if(downKeys.size == 1) {
		        	if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
		        		player.Side = "left";
		        		player.Walk = "walk"; 
		        		padmoveX = -85;
		        		player.playerInBattle = "no";
		        		return false;
		            }
		    		
		    		if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
		    			player.Side = "back";
		    			player.Walk = "walk";
		    			padmoveY = -65;
		    			player.playerInBattle = "no";
		    			return false;
		            }
		    		
		    		if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
		    			player.Side = "front";
		    			player.Walk = "walk";	
		    			padmoveY = -85;
		    			player.playerInBattle = "no";
		    			return false;
		            }
		    		
		    		if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
		    			player.Side = "right";
		    			player.Walk = "walk";
		    			padmoveX = -75;
		    			player.playerInBattle = "no";
		    			return false;
		            } 
		    		if (keycode == Input.Keys.SPACE) {
		    			CheckAction();
						if(autoattack){
							autoattack = false;
						}
						else
						{
							autoattack = true;
						}
						return false;
		            } 
		    		if (keycode == Input.Keys.NUM_1) {
		    			if(!player.hotkey1.equals("none")) {
		    				gameControl.UseItem(hotketcountitem1);
		    			}
		    			return false;
		            } 
		    		if (keycode == Input.Keys.NUM_2) {
		    			if(!player.hotkey2.equals("none")) {
		    				gameControl.UseItem(hotketcountitem2);
		    			}
		    			return false;
		            } 
		    		if (keycode == Input.Keys.Q) {
		    			ChangeTarget();
						return false;
		            } 
		    		if (keycode == Input.Keys.J) {
		    			player.AtkTimer = "30";
		    			return false;
		            } 
		    		if (keycode == Input.Keys.K) {
		    			defTrigged = true;
		    			gameControl.SetCharacterDefense(defTrigged);
		    			defManual = 50;
		    			return false;
		            } 
					if (keycode == Input.Keys.NUM_8) {
							    			
						if(skillUsed > 0) { return false; }
						if(!autoattack) { return false; }
						skillUsed = 200;
						CheckSkill(1);
						return false;
					} 
					if (keycode == Input.Keys.NUM_9) {
						if(skillUsed > 0) { return false; }
						if(!autoattack) { return false; }
						skillUsed = 200;
						CheckSkill(2);
						return false;
					} 
		        }
			}
			
			
			
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			movement = false;
			downKeys.remove(keycode);
			player.Walk = "no";
			player.Frame = "1";
			padmoveX = -80;
			padmoveY = -75;
			player.breakwalk = "none";

			gameControl.UpdatePlayer(player);
			
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
		
		private void onMultipleKeysDown (int mostRecentKeycode){
			
			if(state.equals("Menu")) { return; }
			
			//For multiple key presses
		    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
		        	//player.Side_A = "left-front";
		        	player.Side = "left";
		        	player.Walk = "walk";  	
		        	padmoveX = -66;
		        	padmoveY = -60;
		        	player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
		        	//player.Side_A = "left-back";
		        	player.Side = "left";
		        	player.Walk = "walk";
		        	padmoveX = -66;
		        	padmoveY = -50; 
		        	player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
		    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
		    		//player.Side_A = "right-back";
		    		player.Side = "right";
		    		player.Walk = "walk";	
		    		padmoveX = -55;
		    		padmoveY = -50;
		    		player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
		    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
		    		//player.Side_A = "right-front";
		    		player.Side = "right";
		    		player.Walk = "walk";	
		    		padmoveX = -55;
		    		padmoveY = -60;
		    		player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
		        	//player.Side_A = "back-right";
		        	player.Side = "back";
		        	player.Walk = "walk";
		        	padmoveX = -55;
		        	padmoveY = -50;
		        	player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
		        	//player.Side_A = "back-left";
		        	player.Side = "back";
		        	player.Walk = "walk";
		        	padmoveX = -66;
		        	padmoveY = -50;
		        	player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
		        	//player.Side_A = "front-right";
		        	player.Side = "front";
		        	player.Walk = "walk";
		        	padmoveX = -55;
		        	padmoveY = -60;
		        	player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
		        	//player.Side_A = "front-left";
		        	player.Side = "front";
		        	player.Walk = "walk";	
		        	padmoveX = -66;
		        	padmoveY = -60;
		        	player.playerInBattle = "no";
		        }
		    }
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			movement = false;
			player.breakwalk = "none";
			player.Walk = "no";
			padmoveX = -80;
			padmoveY = -75;
			
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
		public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			if(playerDead) { return false; }
			if(player.playerSit.equals("yes")){ return false; }
            if(defTrigged) { movement = false; return false; }
			if(player.playerInCast.equals("yes")) { movement = false; return false; }
					
			Vector3 coordsTouch = camera.unproject(new Vector3(screenX,screenY,0));
			
			if(movement){	
				//Right
     				if(coordsTouch.x >= cameraCoordsX -70 && coordsTouch.x <= cameraCoordsX -50 && coordsTouch.y > cameraCoordsY - 70 && coordsTouch.y < cameraCoordsY - 48) {
					player.Side = "right";
					player.Walk = "walk";	
					padmoveX = -75;
					player.playerInBattle = "none";
					return false;
				}
				//Left
     				if(coordsTouch.x >= cameraCoordsX -90 && coordsTouch.x <= cameraCoordsX -50 && coordsTouch.y > cameraCoordsY - 70 && coordsTouch.y < cameraCoordsY - 48) {
					player.Side = "left";
					player.Walk = "walk";	
					padmoveX = -85;	
					player.playerInBattle = "none";
					return false;
				}
				//Front
				if(coordsTouch.x > cameraCoordsX -80 && coordsTouch.x < cameraCoordsX -60 && coordsTouch.y > cameraCoordsY - 94 && coordsTouch.y < cameraCoordsY - 58) {
					player.Side = "front";
					player.Walk = "walk";	
					padmoveY = -85;			
					player.playerInBattle = "none";
					return false;
				}
				//Back
				if(coordsTouch.x > cameraCoordsX -80 && coordsTouch.x < cameraCoordsX -60 && coordsTouch.y > cameraCoordsY - 58 && coordsTouch.y < cameraCoordsY - 24) {
					player.Side = "back";
					player.Walk = "walk";	
					padmoveY = -65;
					player.playerInBattle = "none";
					return false;
				}
			}
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean scrolled(float amountX, float amountY) {
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
		public void resize(int p1, int p2) {
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
