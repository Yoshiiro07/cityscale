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
    
    //Variables usables
    private float floatUseA = 0;
    private float floatUseB = 0;
    private float floatUseC = 0;
    private float floatUseD = 0;
    private int intUseA = 0;
    private int intUseB = 0;
    private int intUseC = 0;
    private int intUseD = 0;
    
    private int regen = 0;
    private int regenMax = 0;
    
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
    private float touchSkillX;
    private float touchSkillY;
    private boolean selectAreaRanged = false;
    private Sprite spr_sit;
    private int regenTime = 0;
    
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
    
    //Sprite NPC
    private Sprite spr_npc;
    
    //UX
    private float padmoveX = -80;
    private float padmoveY = -75;
    private Sprite spr_playerTag;
     
    //Sprites Background
    private Sprite spr_Background;
    private Texture tex_Background;
    private Texture tex_BackgroundOver;
    
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
			if(player.Map.equals("MetroStation")) { tex_Background = new Texture(Gdx.files.internal("data/assets/maps/metrostation.png"));  }
			if(player.Map.equals("StreetsA")) { tex_Background = new Texture(Gdx.files.internal("data/assets/maps/streetsA.png"));  }	
			if(player.Map.equals("Sewers")) { tex_Background = new Texture(Gdx.files.internal("data/assets/maps/sewers.png"));  }
			
			//Mobs
			if(player.Map.equals("Sewers")) { listMonsters = gameControl.LoadMonsters("Sewers"); }
			
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
			
			
			//player.PlayerExpGet = "2024-10-11 10:40:26";
			
			//Save
			LoopTime--;
			if(LoopTime < 0) {
				try {
					gameControl.UpdateControlPlayer(player);
					gameControl.SaveChar("SaveChar",player.AccountNumber,playernumString, new HttpCallback() {
					    @Override
					    public void onSuccess(String response) {
					    	if(response.contains("success")) {
					    		System.out.println("Salvo com sucesso");
					    	}
					    	else {
					    		avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
					    		aviso = true;
					    	}
					    }

					    @Override
					    public void onFailure(Throwable t) {
					       System.out.println("Error: " + t.getMessage());
					       avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
						   aviso = true;
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
				if(player.Map.equals("Sewers")) {
					if(player.SyncPlayerMob.equals("Sewers")) {		
						SendMobData();
					}
					else {
						RecoverOnlineMobs();
					}
				}
				
				player = gameControl.ReturnPlayerUse();
			}
			
			if(LoopTime < 0) { LoopTime = 35; }
			
			//Check Regen
			regen = Integer.parseInt(player.regenTime);
			regenMax = Integer.parseInt(player.regenTimeMax);
			
			regen--;
			
			player.regenTime = String.valueOf(regen);
			if(regen < 0) {
				
				intUseA = Integer.parseInt(player.Hp);
				intUseB = Integer.parseInt(player.HpMax);
				
				intUseC = Integer.parseInt(player.Mp);
				intUseD = Integer.parseInt(player.MpMax);
				
				intUseA = intUseA + 10;
				intUseC = intUseC + 10;
				
				if(intUseA >= intUseB) { player.Hp = player.HpMax; }
				if(intUseC >= intUseD) { player.Mp = player.MpMax; }
				
				player.Hp = player.HpMax;
				player.Mp = player.MpMax; 
				player.regenTime = player.regenTimeMax;
			}
			
			//Map Render
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
			
			
			ShowNPCs();
			ShowOnlinePlayers();
			ShowChats();
			
			//Char
			player = gameControl.SetCharMov(player, player.breakwalk);
			
			spr_playerfooter = gameControl.GetFooterChar(player, "no",0,0);
			spr_playerfooter.draw(game.batch);
			
			spr_playerbottom = gameControl.GetBottomChar(player, "no",0,0);
			spr_playerbottom.draw(game.batch);
							
			spr_playertop = gameControl.GetTopChar(player, "no", 0,0);
			spr_playertop.draw(game.batch);
			
			spr_playerhair = gameControl.GetHairChar(player, "no",0,0);
			spr_playerhair.draw(game.batch);
			
			if(!player.Hat.equals("none")) {
				spr_playerhat = gameControl.GetHatChar(player,"",0,0);
				spr_playerhat.draw(game.batch);
			}
			
			
			if(player.playerInBattle.equals("yes") || player.playerInBattle.equals("yes") || player.playerInBattle.equals("yes")) {
				spr_playerweapon = gameControl.SetWeapon(player);
				spr_playerweapon.draw(game.batch);
			}

			//Show Mobs
			if(player.Map.equals("Sewers")){ ShowMobs(); }
		
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
			
			
			font_master.draw(game.batch, player.Name, cameraCoordsX - 88f, cameraCoordsY + 93.7f);
			font_master.draw(game.batch, String.valueOf(player.Hp + "/" + player.HpMax), cameraCoordsX - 85f, cameraCoordsY + 82f);
			font_master.draw(game.batch, String.valueOf(player.Mp + "/" + player.MpMax), cameraCoordsX - 85f, cameraCoordsY + 73.7f);
			font_master.draw(game.batch, String.valueOf(player.Level), cameraCoordsX - 88f, cameraCoordsY + 64f);
			font_master.draw(game.batch, gameControl.ExpPercent(player) + "%", cameraCoordsX - 72f, cameraCoordsY + 64f);
			
			spr_master = gameControl.GetUX("innerpad", cameraCoordsX, cameraCoordsY);
			spr_master.setPosition(cameraCoordsX + padmoveX,cameraCoordsY + padmoveY);
			spr_master.draw(game.batch);
			
			//Ranged Skill
			if(selectAreaRanged) {
				spr_master = gameControl.GetUX("textbar", 0, 0);
				spr_master.setPosition(cameraCoordsX - 33, cameraCoordsY + 35);
				spr_master.setSize(60, 15);
				spr_master.draw(game.batch);
				font_master.draw(game.batch, "Selecione a Area",cameraCoordsX - 20, cameraCoordsY + 46);
			}
			if(showZone) {
				if(countZoneSkill > 0) {
					spr_master = gameControl.GetUX("target", 0, 0);
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
			
			//Checks e Cards
			ShowCards();
			CheckColision();
			CheckPlayerParty();
			CheckAutoAttack();
			CheckMobAutoAttack();
			CheckMobDeadRespawn();
			ShowDamage();
			ShowSkill();
			
			if(playerDead) { ShowPlayerDead(); }
			
			//Item Drop
			if(showDropMsg > 0) {
				spr_master = gameControl.GetUX("textbar", cameraCoordsX, cameraCoordsY);
				spr_master.setPosition(cameraCoordsX - 40, cameraCoordsY + 29);
				spr_master.setSize(80, 20);
				spr_master.draw(game.batch);
				font_master.draw(game.batch, itemdropname, cameraCoordsX - 38, cameraCoordsY + 42);
				showDropMsg--;
			}

			if(state.equals("Menu")){
				spr_master = gameControl.GetUX("menu", cameraCoordsX, cameraCoordsY);
				spr_master.draw(game.batch);
				//Status
				font_master.draw(game.batch, String.valueOf(player.Str), cameraCoordsX - 76f, cameraCoordsY - 50f);
				font_master.draw(game.batch, String.valueOf(player.Vit), cameraCoordsX - 62f, cameraCoordsY - 50f);
				font_master.draw(game.batch, String.valueOf(player.Agi), cameraCoordsX - 48f, cameraCoordsY - 50f);
				font_master.draw(game.batch, String.valueOf(player.Wis), cameraCoordsX - 34f, cameraCoordsY - 50f);
				font_master.draw(game.batch, String.valueOf(player.Dex), cameraCoordsX - 21f, cameraCoordsY - 50f);
				font_master.draw(game.batch, String.valueOf(player.StatusPoint), cameraCoordsX - 43f, cameraCoordsY - 67f);
				
				font_master.draw(game.batch,"Dinheiro:" + String.valueOf(player.Money),cameraCoordsX - 10f, cameraCoordsY - 67f);		
				font_master.draw(game.batch,"Exp:" + String.valueOf(player.Exp),cameraCoordsX + 20f, cameraCoordsY - 67f);			
				
				//CharacterShow
				spr_playerhair = gameControl.GetHairChar(player, "Show", cameraCoordsX, cameraCoordsY);
				spr_playerhair.draw(game.batch);
				
				spr_playerfooter = gameControl.GetFooterChar(player, "Show", cameraCoordsX, cameraCoordsY);
				spr_playerfooter.draw(game.batch);
				
				spr_playerbottom = gameControl.GetBottomChar(player, "Show", cameraCoordsX, cameraCoordsY);
				spr_playerbottom.draw(game.batch);
				
				spr_playertop = gameControl.GetTopChar(player, "Show", cameraCoordsX, cameraCoordsY);
				spr_playertop.draw(game.batch);
				
				if(!player.Hat.equals("none")) {
					spr_playerhatmenu = gameControl.GetHatChar(player,"",cameraCoordsX, cameraCoordsY);
					spr_playerhatmenu.setScale(0.5f, 0.7f);
					spr_playerhatmenu.setPosition(cameraCoordsX - 84, cameraCoordsY + 42);
					spr_playerhatmenu.draw(game.batch);
				}
				

				//Show Character
				//Itens Equipped
				spr_playerfooter = gameControl.GetItem(player.SetFooter, player);
				spr_playerfooter.setPosition(cameraCoordsX + 13, cameraCoordsY + 41);
				spr_playerfooter.setSize(13, 22);
				spr_playerfooter.draw(game.batch);
				
				spr_playerbottom = gameControl.GetItem(player.SetBottom, player);
				spr_playerbottom.setPosition(cameraCoordsX + 27, cameraCoordsY + 41);
				spr_playerbottom.setSize(13, 22);
				spr_playerbottom.draw(game.batch);
				
				spr_playertop = gameControl.GetItem(player.SetUpper, player);
				spr_playertop.setPosition(cameraCoordsX + 41, cameraCoordsY + 41);
				spr_playertop.setSize(13, 22);
				spr_playertop.draw(game.batch);
				
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
					spr_master.setPosition(cameraCoordsX - 12, cameraCoordsY - 63);
					spr_master.draw(game.batch); 
				}
				
			}
			
			if(state.equals("Shop")) {
				
				if(shopname.equals("refrishop")) {
					spr_shop = gameControl.GetShops("refrishop",cameraCoordsX, cameraCoordsY);
					spr_shop.draw(game.batch);		
					font_master.draw(game.batch, String.valueOf(player.Money), cameraCoordsX - 25, cameraCoordsY - 37);				
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
				spr_master = gameControl.GetUX("battlezoneA", cameraCoordsX, cameraCoordsY);
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
			
			
			spr_testeDot.setPosition(cameraCoordsX - 60, cameraCoordsY + 56);
			spr_testeDot.setSize(1, 1);
			spr_testeDot.draw(game.batch);

			
			spr_testeDot.setPosition(cameraCoordsX - 49, cameraCoordsY + 75);
			spr_testeDot.setSize(1, 1);
			spr_testeDot.draw(game.batch);
			
			
			game.batch.end();
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
				    		avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
				    		aviso = true;
				    	}
				    }

				    @Override
				    public void onFailure(Throwable t) {
				       System.out.println("Error: " + t.getMessage());
				       avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
					   aviso = true;
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
				    		avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
				    		aviso = true;
				    	}
				    }

				    @Override
				    public void onFailure(Throwable t) {
				       System.out.println("Error: " + t.getMessage());
				       avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
					   aviso = true;
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
				    		avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
				    		aviso = true;
				    	}
				    }

				    @Override
				    public void onFailure(Throwable t) {
				       System.out.println("Error: " + t.getMessage());
				       avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
					   aviso = true;
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
				    		avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
				    		aviso = true;
				    	}
				    }

				    @Override
				    public void onFailure(Throwable t) {
				       System.out.println("Error: " + t.getMessage());
				       avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
					   aviso = true;
				    }
				});
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void ShowOnlinePlayers() {
			if(lstOnlinePlayers.size() > 0) {
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
				
				font_master.draw(game.batch, String.valueOf(listDamage.get(i).DamageValue), listDamage.get(i).DamagePosX, listDamage.get(i).DamagePosY);
				
				if(listDamage.get(i).DamageTime < 0) {
					listDamage.remove(listDamage.get(i));
				}

				font_master.setColor(Color.WHITE);
			}
		}
		
		public void CheckMobDeadRespawn() {
			
			if(player.Map.equals("Sewers")) {
				for(int num = 0; num < listMonsters.size(); num++) {
					
					if(listMonsters.get(num).MobHp <= 0) {
						listMonsters.get(num).MobHp = 0; 
						listMonsters.get(num).MobDead = "yes";   
					}
					
					if(listMonsters.get(num).MobDead.equals("yes")) {
						listMonsters.get(num).MobPosX = 200;
						listMonsters.get(num).MobPosY = 200;
						listMonsters.get(num).MobTimeDead--;
						
						if(listMonsters.get(num).MobTimeDead <= 0) {
							listMonsters.get(num).MobTimeDead = 250;
							listMonsters.get(num).MobDead = "no";
							listMonsters.get(num).MobHp = listMonsters.get(num).MobHpMax;
							listMonsters.get(num).MobMp = listMonsters.get(num).MobMpMax;
							listMonsters.get(num).MobTarget = "none";
							listMonsters.get(num).MobPosX = 0;
							listMonsters.get(num).MobPosY = 0;
						}
					}
				}
		    }		
		}
		
		public void ShowNPCs() {
			//NPCs
			if(player.Map.equals("StreetsA")) {
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

				font_master.draw(game.batch, "Arenas", 105, -81);
				font_master.draw(game.batch, "Doadora", -7f, -80f);
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
			
			int Stamina = Integer.parseInt(player.Stamina);
			
			
			if(player.Map.equals("Sewers") && autoattack) {
				for(int i = 0; i < listMonsters.size(); i++) {
					
					if(player.Target.equals(listMonsters.get(i).MobID)) {
						
						if((listMonsters.get(i).MobPosX + 5) > (playerposX - 15) && (listMonsters.get(i).MobPosX + 5) < (playerposX + 15)
						   && (listMonsters.get(i).MobPosY + 7) > (playerposY - 18) && (listMonsters.get(i).MobPosY + 7) < (playerposY + 40)) { 
							player.playerInBattle = "yes";
							AtkTimer--;
							player.AtkTimer = String.valueOf(AtkTimer);
							
							if(AtkTimer <= 0) { 
								player.AtkTimer = player.AtkTimerMax;
								int atkweapon = CheckWeapon();
								int mobhp = listMonsters.get(i).MobHp; //CheckDamageDifer(listMonsters.get(i).MobHpMax, 1);
								int damagehit = Atk + atkweapon + Str;
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
									mobhp = mobhp - 5; 
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
			
			if(player.Map.equals("Sewers")) {
				for(int i = 0; i < listMonsters.size(); i++) {						
					if(listMonsters.get(i).MobTarget.equals(player.Name)) {
						if(playerposX > (listMonsters.get(i).MobPosX - 15) && playerposX < (listMonsters.get(i).MobPosX + 15)
							&& playerposY > (listMonsters.get(i).MobPosY - 18) && playerposY < (listMonsters.get(i).MobPosY + 40)) {
								
								listMonsters.get(i).MobAtkTimer--;
								if(listMonsters.get(i).MobAtkTimer <= 0) {
									int mobluck = randnumber.nextInt(100);
									if(mobluck > 5 && mobluck < 20) {
										Hp = Hp - ((listMonsters.get(i).MobAtk * 2) - Def);
										player.Hp = String.valueOf(Hp);
									}
									if(mobluck >= 0 && mobluck < 5) {
										Hp = Hp - ((listMonsters.get(i).MobAtk * 3) - Def);
										player.Hp = String.valueOf(Hp);
									}
									if(mobluck > 10) {
									{
										Hp = Hp - (listMonsters.get(i).MobAtk - Def);
										player.Hp = String.valueOf(Hp);
									}								 
									listMonsters.get(i).MobAtkTimer = listMonsters.get(i).MobAtkTimerMax;
									Damage damage = new Damage();
									damage.DamagePosX = listMonsters.get(i).MobPosX;
									damage.DamagePosY = listMonsters.get(i).MobPosY;
									damage.DamageTime = 100;
									damage.DamageType = "player";
									damage.DamageValue = listMonsters.get(i).MobAtk;
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
				    		avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
				    		aviso = true;
				    	}
				    }

				    @Override
				    public void onFailure(Throwable t) {
				       System.out.println("Error: " + t.getMessage());
				       avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
					   aviso = true;
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
		
		public void CheckColision() {
			float posY = Float.parseFloat(player.PosY);
			float posX = Float.parseFloat(player.PosX);
			
			if(player.Map.equals("MetroStation")) {
				if(posX > 127 && posX < 148 && posY> -192  && posY < - 140) {
					MapChange("StreetsA");
				}
			}
			
			if(player.Map.equals("StreetsA")) {
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
			if(player.Map.equals("Sewers")) {
				if(posX > 40f && posX < 53 && posY> 5 && posY < 21.5f) {
					MapChange("StreetsAFromSewers");
				}
			}
		}
		
		public void ShowCards() {
			//Basic Cards
			//Hotkey 1 / 2
			if(player.hotkey1.equals("none")) {
				spr_master = gameControl.GetCard("cardempty");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 30);
				spr_master.draw(game.batch);
			}
			
			if(player.hotkey2.equals("none")) {
				spr_master = gameControl.GetCard("cardempty");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY + 0);
				spr_master.draw(game.batch);
			}
			
			if(player.hotkey1.equals("hpcan")) {
				spr_master = gameControl.GetCard("cardhp");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 30);
				spr_master.draw(game.batch);
			}
			
			if(player.hotkey2.equals("hpcan")) {
				spr_master = gameControl.GetCard("cardhp");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY + 0);
				spr_master.draw(game.batch);
			}
			
			
			if(!player.Map.equals("Sewers")){
				spr_master = gameControl.GetCard("cardaction");
				spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 60);
				spr_master.draw(game.batch);
			}

			if(player.Map.equals("Sewers")){ //cardactionON
				if(autoattack){
					spr_master = gameControl.GetCard("cardactionON");
					spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 60);
					spr_master.draw(game.batch);
				}
				else{
					spr_master = gameControl.GetCard("cardaction");
					spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 60);
					spr_master.draw(game.batch);
				}
			}
			
			spr_master = gameControl.GetCard("cardtarget");
			spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 60);
			spr_master.draw(game.batch);
			
			//Novice Cards
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
					font_master.draw(game.batch, ShowQuantityItem(i), spr_item.getX() + 9,spr_item.getY() + 7);
				}
			}
					
			//Crystal Itens    
			//slot 1
			if(!player.Crystal1.equals("none")) {
				spr_item = gameControl.GetItem(player.Crystal1, player);
				spr_item.setPosition(1.5f, 25);
				spr_item.setSize(9, 14);
				//spr_item.draw(game.batch);
			}
			
			if(!player.Crystal2.equals("none")) {
				spr_item = gameControl.GetItem(player.Crystal1, player);
				spr_item.setPosition(10.5f, 25);
				spr_item.setSize(9, 14);
				//spr_item.draw(game.batch); 
			}
			
			//slot 3
			if(!player.Crystal3.equals("none")) {
				spr_item = gameControl.GetItem(player.Crystal1, player);
				spr_item.setPosition(19.5f, 25);
				spr_item.setSize(9, 14);
				//spr_item.draw(game.batch); 
			}
			
			//slot 4
			if(!player.Crystal4.equals("none")) {
				spr_item = gameControl.GetItem(player.Crystal1, player);
				spr_item.setPosition(29f, 25);
				spr_item.setSize(9, 14);
				//spr_item.draw(game.batch); 
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
				if(num == 5){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 6){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 7){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 8){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 9){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 10){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 11){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 12){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 13){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 14){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 15){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}	
				
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
			
			if(player.Map.equals("Sewers") && autoattack) {
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
								int totaldmg = Atk + ((Str * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								//if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }
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
							if(skillname.equals("hammercrash")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((Str * 2) + (Vit * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								//if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "tripleattack";
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
							if(skillname.equals("flysword")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((Str * 3) + (Agi * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								//if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "tripleattack";
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
							if(skillname.equals("poisonhit")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((Luk * 2)+ (Str * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								listMonsters.get(i).MobHp = mobHP;
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "tripleattack";
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
							if(skillname.equals("steal")) {
								
							}
							if(skillname.equals("overpower")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((Vit * 3) + (Str * 5) + (Luk * 2) + atkweapon);	
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								//if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }					
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
							Hp = Hp + (Wis * 3);
							if(Hp > HpMax) { Hp = HpMax; }
							rangedAttack = false; 
							skillEffect = true;
							Skill skillInUse = new Skill();
							Damage damageSkill = new Damage();
							skillInUse.SkillName = "heal";
							skillInUse.SkillPosX = playerPosX;
							skillInUse.SkillPosY = playerPosY;
							damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
							damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							damageSkill.DamageTime = 100;
							damageSkill.DamageType = "heal";
							damageSkill.DamageValue = 0;
							return;
						}
						
						if(skillname.equals("defboost")) { 
							GiveBuff("defboost"); 
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
							return; 	
						}						
						if(skillname.equals("healthboost")) { GiveBuff("healthboost"); rangedAttack = false; return; }			
						if(skillname.equals("regen")) { 
							GiveBuff("regen"); 
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
							return; 	
						}		
						if(skillname.equals("ironshield")) { 
							GiveBuff("ironshield"); 
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
							return; 	
						}				
						if(skillname.equals("invisibility")) { 
							GiveBuff("invisibility");
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
							return; 								
						}
						
						
						if((listMonsters.get(i).MobPosX + 5) > (touchSkillX - 5) && (listMonsters.get(i).MobPosX + 5) < (touchSkillX + 5)
						   && (listMonsters.get(i).MobPosY + 5) > (touchSkillY - 10) && (listMonsters.get(i).MobPosY + 5) < (touchSkillY + 10)) {
							player.playerInBattle = "yes";
							listMonsters.get(i).MobTarget = player.Name;
							
							if(skillname.equals("rockbound")) {
								int atkweapon = CheckWeapon();
								int totaldmg = Atk + ((Wis * 2) + 10);
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
								return;
							}
							
							if(skillname.equals("fireball")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((Wis * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								listMonsters.get(i).MobHp = mobHP;				
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "fireball";
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
								return;
							}
							
							if(skillname.equals("icecrystal")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((Wis * 6) + (Dex * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								//if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "icecrystal";
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
								return;
							}												
							if(skillname.equals("thundercloud")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((Wis * 3) + (Agi * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								//if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }						
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
								return;
							}
							
							if(skillname.equals("bulletrain")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((Dex * 2) + (Agi * 2) + 10);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								//if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }					
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "bulletrain";
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
								return;
							}						
							if(skillname.equals("holyprism")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((Wis) + Luk + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								//if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }					
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "holyprism";
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
								return;
							}
							if(skillname.equals("mine")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((Dex * 2) + 10);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								//if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }					
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
			int Atk = Integer.parseInt(player.Atk);
			int Def = Integer.parseInt(player.Def);
			
			int Str = Integer.parseInt(player.Str);
			int Vit = Integer.parseInt(player.Vit);
			int Agi = Integer.parseInt(player.Agi);
			int Dex = Integer.parseInt(player.Dex);
			int Luk = Integer.parseInt(player.Luk);
			int Wis = Integer.parseInt(player.Wis);
			
			int Hp = Integer.parseInt(player.Hp);
			int HpMax = Integer.parseInt(player.HpMax);
			
			int Mp = Integer.parseInt(player.Mp);
			int MpMax = Integer.parseInt(player.MpMax);
			
			int regenTimeMax = Integer.parseInt(player.regenTimeMax);
			
			if(player.buffA.equals(buffname)) { return; }
			if(player.buffB.equals(buffname)) { return; }
			if(player.buffC.equals(buffname)) { return; }
			
			if(player.buffA.equals("none") && !setBuff) { player.buffA = buffname; setBuff  = true; buff = "A"; }
			if(player.buffB.equals("none") && !setBuff) { player.buffB = buffname; setBuff  = true; buff = "B"; }
			if(player.buffC.equals("none") && !setBuff) { player.buffC = buffname; setBuff  = true; buff = "C"; }
					
			if(buffname.equals("defboost")) {
				
				Atk = Atk + Atk * 2;
				Def = Def + Def * 2;
				HpMax = HpMax + 30;
				MpMax = MpMax + 30;
				
				player.Atk = String.valueOf(Atk);
				player.Def = String.valueOf(Def);
				player.HpMax = String.valueOf(HpMax);
				player.MpMax = String.valueOf(MpMax);
				
				if(buff.equals("A")) { player.BuffTimeA = "5000"; }
				if(buff.equals("B")) { player.BuffTimeB = "5000"; }
				if(buff.equals("C")) { player.BuffTimeC = "5000"; }
			}
			
			if(buffname.equals("ironshield")) {
				
				Def = Def * 4;
				
				player.Def = String.valueOf(Def);
				
				if(buff.equals("A")) { player.BuffTimeA = "2500"; }
				if(buff.equals("B")) { player.BuffTimeB = "2500"; }
				if(buff.equals("C")) { player.BuffTimeC = "2500"; }
			}
			
			if(buffname.equals("healthboost")) {
				HpMax = HpMax * 3;
				
				if(buff.equals("A")) { player.BuffTimeA = "4500"; }
				if(buff.equals("B")) { player.BuffTimeB = "4500"; }
				if(buff.equals("C")) { player.BuffTimeC = "4500"; }
			}
			
			if(buffname.equals("berserk")) {
				Str = Str * 3;
				
				if(buff.equals("A")) { player.BuffTimeA = "2500"; }
				if(buff.equals("B")) { player.BuffTimeB = "2500"; }
				if(buff.equals("C")) { player.BuffTimeC = "2500"; }
			}
			
			if(buffname.equals("regen")) {
				regenTimeMax = regenTimeMax - 3000;
				
				if(buff.equals("A")) { player.BuffTimeA = "2000"; }
				if(buff.equals("B")) { player.BuffTimeB = "2000"; }
				if(buff.equals("C")) { player.BuffTimeC = "2000"; }
			}
			
			if(buffname.equals("invisibility")) {
				if(buff.equals("A")) { player.BuffTimeA = "1000"; }
				if(buff.equals("B")) { player.BuffTimeB = "1000"; }
				if(buff.equals("C")) { player.BuffTimeC = "1000"; }
			}
			
			if(buffname.equals("lockshot")) {
				Dex = Dex * 2;
				Luk = Luk * 2;
				
				if(buff.equals("A")) { player.BuffTimeA = "3000"; }
				if(buff.equals("B")) { player.BuffTimeB = "3000"; }
				if(buff.equals("C")) { player.BuffTimeC = "3000"; }
			}		
		}
		
		public void RemoveBuffs(String buffname) {
			String buff = "";
			
			int Atk = Integer.parseInt(player.Atk);
			int Def = Integer.parseInt(player.Def);
			
			int Str = Integer.parseInt(player.Str);
			int Vit = Integer.parseInt(player.Vit);
			int Agi = Integer.parseInt(player.Agi);
			int Dex = Integer.parseInt(player.Dex);
			int Luk = Integer.parseInt(player.Luk);
			int Wis = Integer.parseInt(player.Wis);
			
			int Hp = Integer.parseInt(player.Hp);
			int HpMax = Integer.parseInt(player.HpMax);
			
			int Mp = Integer.parseInt(player.Mp);
			int MpMax = Integer.parseInt(player.MpMax);
			
			int regenTimeMax = Integer.parseInt(player.regenTimeMax);
			
			if(player.buffA.equals(buffname)) { buff = "A"; }
			if(player.buffB.equals(buffname)) { buff = "B"; }
			if(player.buffC.equals(buffname)) { buff = "C"; }
			
			if(buffname.equals("boost")) {
				Atk = Atk - Atk / 2;
				Def = Def - Def / 2;
				HpMax = HpMax - 30;
				MpMax = MpMax - 30;
				
				player.Atk = String.valueOf(Atk);
				player.Def = String.valueOf(Def);
				player.HpMax = String.valueOf(HpMax);
				player.MpMax = String.valueOf(MpMax);
			}
			
			if(buffname.equals("ironshield")) {
				Def = Def / 4;
				player.Def = String.valueOf(Def);
			}
			
			if(buffname.equals("healthboost")) {
				HpMax = HpMax / 3;
				player.HpMax = String.valueOf(HpMax);
			}
			
			if(buffname.equals("berserk")) {
				Str = Str / 3;
				player.Str = String.valueOf(Str);
			}
			
			if(buffname.equals("regen")) {
				regenTimeMax = regenTimeMax + 3000;			
				player.regenTimeMax = String.valueOf(regenTimeMax);
			}
			
			if(buffname.equals("lockshot")) {
				Dex = Dex / 2;
				Luk = Luk / 2;
				
				player.Dex = String.valueOf(Dex);
				player.Luk = String.valueOf(Luk);
			}
			
			if(buff.equals("A")) { player.buffA = "none"; player.BuffTimeA = "0"; }
			if(buff.equals("B")) { player.buffB = "none"; player.BuffTimeB = "0"; }
			if(buff.equals("C")) { player.buffC = "none"; player.BuffTimeC = "0"; }
			
		}
		
		public void ShowBuffs() {
			int buffTimeA = Integer.parseInt(player.BuffTimeA);
			int buffTimeB = Integer.parseInt(player.BuffTimeB);
			int buffTimeC = Integer.parseInt(player.BuffTimeC);
			
			if(!player.buffA.equals("none")) {
				if(player.buffA.equals("defboost")) { spr_master = gameControl.GetCard("cardboost"); }
				if(player.buffA.equals("ironshield")) { spr_master = gameControl.GetCard("cardironshield"); }
				if(player.buffA.equals("healthboost")) { spr_master = gameControl.GetCard("cardhealthboost"); }
				if(player.buffA.equals("berserk")) {  spr_master = gameControl.GetCard("cardberserk");}
				if(player.buffA.equals("regen")) { spr_master = gameControl.GetCard("cardregen"); }
				if(player.buffA.equals("invisibility")) { spr_master = gameControl.GetCard("cardinvisibility"); }
				if(player.buffA.equals("lockshot")) { spr_master = gameControl.GetCard("cardlockshot"); }
				spr_master.setSize(3, 8);
				spr_master.setPosition(-50, 30);
				spr_master.draw(game.batch);
				
				buffTimeA = buffTimeA - 1;
				if(buffTimeA <= 0) {
					RemoveBuffs(player.buffA);
				}		
			}
			if(!player.buffB.equals("none")) {
				if(player.buffB.equals("defboost")) { spr_master = gameControl.GetCard("cardboost"); }
				if(player.buffB.equals("ironshield")) { spr_master = gameControl.GetCard("cardironshield"); }
				if(player.buffB.equals("healthboost")) { spr_master = gameControl.GetCard("cardhealthboost"); }
				if(player.buffB.equals("berserk")) {  spr_master = gameControl.GetCard("cardberserk");}
				if(player.buffB.equals("regen")) { spr_master = gameControl.GetCard("cardregen"); }
				if(player.buffB.equals("invisibility")) { spr_master = gameControl.GetCard("cardinvisibility"); }
				if(player.buffB.equals("lockshot")) { spr_master = gameControl.GetCard("cardlockshot"); }
				spr_master.setSize(3, 8);
				spr_master.setPosition(-45, 30);
				spr_master.draw(game.batch);
				
				buffTimeB = buffTimeB - 1;
				if(buffTimeB <= 0) {
					RemoveBuffs(player.buffB);
				}
			}
			if(!player.buffC.equals("none")) {
				if(player.buffC.equals("defboost")) { spr_master = gameControl.GetCard("cardboost"); }
				if(player.buffC.equals("ironshield")) { spr_master = gameControl.GetCard("cardironshield"); }
				if(player.buffC.equals("healthboost")) { spr_master = gameControl.GetCard("cardhealthboost"); }
				if(player.buffC.equals("berserk")) {  spr_master = gameControl.GetCard("cardberserk");}
				if(player.buffC.equals("regen")) { spr_master = gameControl.GetCard("cardregen"); }
				if(player.buffC.equals("invisibility")) { spr_master = gameControl.GetCard("cardinvisibility"); }
				if(player.buffC.equals("lockshot")) { spr_master = gameControl.GetCard("cardlockshot"); }
				spr_master.setSize(3, 8);
				spr_master.setPosition(-40, 30);
				spr_master.draw(game.batch);
				
				buffTimeC = buffTimeC - 1;
				if(buffTimeC <= 0) {
					RemoveBuffs(player.buffC);
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
					if(listSkills.get(i).SkillName.equals("tripleattack")) { spr_master = gameControl.GetSpriteSkill("tripleattack",6); }
					if(listSkills.get(i).SkillName.equals("steal")) { spr_master = gameControl.GetSpriteSkill("steal",6); }
					if(listSkills.get(i).SkillName.equals("soulclash")) { spr_master = gameControl.GetSpriteSkill("soulclash",6); }
					if(listSkills.get(i).SkillName.equals("ravenblade")) { spr_master = gameControl.GetSpriteSkill("ravenblade",6); }
					if(listSkills.get(i).SkillName.equals("ragebound")) { spr_master = gameControl.GetSpriteSkill("ragebound",6); }
					if(listSkills.get(i).SkillName.equals("thundercloud")) { spr_master = gameControl.GetSpriteSkill("thundercloud",6); }
					if(listSkills.get(i).SkillName.equals("lockshot")) { spr_master = gameControl.GetSpriteSkill("lockshot",6); }
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
					if(listSkills.get(i).SkillName.equals("rockbound")) { spr_master = gameControl.GetSpriteSkill("rockbound",6); }
					
					spr_master.setPosition(listSkills.get(i).SkillPosX -10, listSkills.get(i).SkillPosY);
					spr_master.setSize(40,40);
					spr_master.draw(game.batch);
				}
				
				if(listSkills.get(i).SkillTime >= 60 && listSkills.get(i).SkillTime <= 80) { 
					if(listSkills.get(i).SkillName.equals("tripleattack")) { spr_master = gameControl.GetSpriteSkill("tripleattack",5); }
					if(listSkills.get(i).SkillName.equals("steal")) { spr_master = gameControl.GetSpriteSkill("steal",5); }
					if(listSkills.get(i).SkillName.equals("soulclash")) { spr_master = gameControl.GetSpriteSkill("soulclash",5); }
					if(listSkills.get(i).SkillName.equals("ravenblade")) { spr_master = gameControl.GetSpriteSkill("ravenblade",5); }
					if(listSkills.get(i).SkillName.equals("ragebound")) { spr_master = gameControl.GetSpriteSkill("ragebound",5); }
					if(listSkills.get(i).SkillName.equals("thundercloud")) { spr_master = gameControl.GetSpriteSkill("thundercloud",5); }
					if(listSkills.get(i).SkillName.equals("lockshot")) { spr_master = gameControl.GetSpriteSkill("lockshot",5); }
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
					spr_master.setSize(40,40);
					spr_master.draw(game.batch);
				}
				
				if(listSkills.get(i).SkillTime >= 40 && listSkills.get(i).SkillTime <= 60) { 
					if(listSkills.get(i).SkillName.equals("tripleattack")) { spr_master = gameControl.GetSpriteSkill("tripleattack",4); }
					if(listSkills.get(i).SkillName.equals("steal")) { spr_master = gameControl.GetSpriteSkill("steal",4); }
					if(listSkills.get(i).SkillName.equals("soulclash")) { spr_master = gameControl.GetSpriteSkill("soulclash",4); }
					if(listSkills.get(i).SkillName.equals("ravenblade")) { spr_master = gameControl.GetSpriteSkill("ravenblade",4); }
					if(listSkills.get(i).SkillName.equals("ragebound")) { spr_master = gameControl.GetSpriteSkill("ragebound",4); }
					if(listSkills.get(i).SkillName.equals("thundercloud")) { spr_master = gameControl.GetSpriteSkill("thundercloud",4); }
					if(listSkills.get(i).SkillName.equals("lockshot")) { spr_master = gameControl.GetSpriteSkill("lockshot",4); }
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
					spr_master.setSize(40,40);
					spr_master.draw(game.batch);
				}
				
				if(listSkills.get(i).SkillTime >= 20 && listSkills.get(i).SkillTime <= 40) { 
					if(listSkills.get(i).SkillName.equals("tripleattack")) { spr_master = gameControl.GetSpriteSkill("tripleattack",3); }
					if(listSkills.get(i).SkillName.equals("steal")) { spr_master = gameControl.GetSpriteSkill("steal",3); }
					if(listSkills.get(i).SkillName.equals("soulclash")) { spr_master = gameControl.GetSpriteSkill("soulclash",3); }
					if(listSkills.get(i).SkillName.equals("ravenblade")) { spr_master = gameControl.GetSpriteSkill("ravenblade",3); }
					if(listSkills.get(i).SkillName.equals("ragebound")) { spr_master = gameControl.GetSpriteSkill("ragebound",3); }
					if(listSkills.get(i).SkillName.equals("thundercloud")) { spr_master = gameControl.GetSpriteSkill("thundercloud",3); }
					if(listSkills.get(i).SkillName.equals("lockshot")) { spr_master = gameControl.GetSpriteSkill("lockshot",3); }
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
					spr_master.setSize(40,40);
					spr_master.draw(game.batch);
				}
				
				if(listSkills.get(i).SkillTime >= 10 && listSkills.get(i).SkillTime <= 20) { 
					if(listSkills.get(i).SkillName.equals("tripleattack")) { spr_master = gameControl.GetSpriteSkill("tripleattack",2); }
					if(listSkills.get(i).SkillName.equals("steal")) { spr_master = gameControl.GetSpriteSkill("steal",2); }
					if(listSkills.get(i).SkillName.equals("soulclash")) { spr_master = gameControl.GetSpriteSkill("soulclash",2); }
					if(listSkills.get(i).SkillName.equals("ravenblade")) { spr_master = gameControl.GetSpriteSkill("ravenblade",2); }
					if(listSkills.get(i).SkillName.equals("ragebound")) { spr_master = gameControl.GetSpriteSkill("ragebound",2); }
					if(listSkills.get(i).SkillName.equals("thundercloud")) { spr_master = gameControl.GetSpriteSkill("thundercloud",2); }
					if(listSkills.get(i).SkillName.equals("lockshot")) { spr_master = gameControl.GetSpriteSkill("lockshot",2); }
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
					spr_master.setSize(40,40);
					spr_master.draw(game.batch);
				}
				
				if(listSkills.get(i).SkillTime >= 0 && listSkills.get(i).SkillTime <= 10) { 
					if(listSkills.get(i).SkillName.equals("tripleattack")) { spr_master = gameControl.GetSpriteSkill("tripleattack",1); }
					if(listSkills.get(i).SkillName.equals("steal")) { spr_master = gameControl.GetSpriteSkill("steal",1); }
					if(listSkills.get(i).SkillName.equals("soulclash")) { spr_master = gameControl.GetSpriteSkill("soulclash",1); }
					if(listSkills.get(i).SkillName.equals("ravenblade")) { spr_master = gameControl.GetSpriteSkill("ravenblade",1); }
					if(listSkills.get(i).SkillName.equals("ragebound")) { spr_master = gameControl.GetSpriteSkill("ragebound",1); }
					if(listSkills.get(i).SkillName.equals("thundercloud")) { spr_master = gameControl.GetSpriteSkill("thundercloud",1); }
					if(listSkills.get(i).SkillName.equals("lockshot")) { spr_master = gameControl.GetSpriteSkill("lockshot",1); }
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
					if(listSkills.get(i).SkillName.equals("rockbound")) { spr_master = gameControl.GetSpriteSkill("rockbound",1); spr_master.setSize(60,60); }
					
					spr_master.setPosition(listSkills.get(i).SkillPosX -10, listSkills.get(i).SkillPosY);
					spr_master.setSize(40,40);
					spr_master.draw(game.batch);
				}
				
				if(listSkills.get(i).SkillTime < 0) { 
					listSkills.remove(listSkills.get(i));
				}
			}
		}
		
		public int CheckWeapon() {  
			
			if(player.Weapon.equals("basic_knife")) { return 1;}			
			if(player.Weapon.equals("doubleedgeknife")) { return 3; }		
			if(player.Weapon.equals("woodsword")) { return 10;}							
			if(player.Weapon.equals("basicpistol")) { return 8;}			
			if(player.Weapon.equals("basicdagger")) { return 8;}		
			if(player.Weapon.equals("stickrod")) { return 6;}	
			if(player.Weapon.equals("basicaxe")) { return 12;}
			
			return 0;
		}
		
		public void MobDead(int mobindex) {
			int playermoney = Integer.parseInt(player.Money);
			
			player.Target = "none";
			player.AtkTimer = player.AtkTimerMax;
			player.playerInBattle = "no";
		    player.playerInAttack = "no";
		    player.playerInCast = "no";	
		    autoattack = false;
		    
		    //here
			String date = dateTimeProvider.getCurrentDateTime();
			
			showDropMsg = 100;
		    itemdropname = gameControl.ItemDrop(listMonsters.get(mobindex).MobName);
		    int expreceived = listMonsters.get(mobindex).MobExp;
		    gameControl.GiveExp(expreceived);

			expreceived = expreceived / 10;
			
			if(expreceived <= 0) { expreceived = 1; }
			
		    try {
				gameControl.SendExpBank("SendExpBank",player.AccountNumber,playernumString,player.Name,String.valueOf(expreceived),date, new HttpCallback() {
				    @Override
				    public void onSuccess(String response) {
				    	if(response.contains("success")) {}
				    	else {
				    		avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
				    		aviso = true;
				    	}
				    }

				    @Override
				    public void onFailure(Throwable t) {
				       System.out.println("Error: " + t.getMessage());
				       avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
					   aviso = true;
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
		                            avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
		                            aviso = true;
		                        }
		                    }

		                    @Override
		                    public void onFailure(Throwable t) {
		                        System.out.println("Error: " + t.getMessage());
		                        avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
		                        aviso = true;
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
						}
					}
				}
			}
		}
		
		public void ChangeTarget() {
					
			if(player.Map.equals("Sewers")) {
			
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
								player.Target = listMonsters.get(countSwitchTarget).MobID;						
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
			if(num == 1 && player.Job.equals("Feiticeiro")) { SetUseSkill("fireball"); }
			if(num == 1 && player.Job.equals("Batedor")) { SetUseSkill("hammercrash"); }
			if(num == 1 && player.Job.equals("Pistoleiro")) { SetUseSkill("bulletrain"); }
			if(num == 1 && player.Job.equals("Curandeiro")) { SetUseSkill("heal"); }
			if(num == 1 && player.Job.equals("Ladrao")) { SetUseSkill("poisonhit"); }
			
			if(num == 2 && player.Job.equals("Aprendiz")) { SetUseSkill("rockbound"); }
			if(num == 2 && player.Job.equals("Espadachim")) { SetUseSkill("ironshield"); }
			if(num == 2 && player.Job.equals("Feiticeiro")) { SetUseSkill("thundercloud"); }
			if(num == 2 && player.Job.equals("Batedor")) { SetUseSkill("overpower"); }
			if(num == 2 && player.Job.equals("Pistoleiro")) { SetUseSkill("mine"); }
			if(num == 2 && player.Job.equals("Curandeiro")) { SetUseSkill("holyprism"); }
			if(num == 2 && player.Job.equals("Ladrao")) { SetUseSkill("invisibility"); }
			
			if(num == 3 && player.Job.equals("Espadachim")) { SetUseSkill("healthboost"); }
			if(num == 3 && player.Job.equals("Feiticeiro")) { SetUseSkill("icecrystal"); }
			if(num == 3 && player.Job.equals("Batedor")) { SetUseSkill("berserk"); }
			if(num == 3 && player.Job.equals("Pistoleiro")) { SetUseSkill("lockshot"); }
			if(num == 3 && player.Job.equals("Curandeiro")) { SetUseSkill("defboost"); }
			if(num == 3 && player.Job.equals("Ladrao")) { SetUseSkill("steal"); }			
		}
		
		public void SetUseSkill(String skill) {
			int Mp = Integer.parseInt(player.Mp);
			
			//Cost
			if(skill.equals("tripleattack") && Mp < 5) { notmp = true; return; }
			if(skill.equals("rockbound") && Mp < 5) { notmp = true; return; }
			if(skill.equals("regen") && Mp < 2) { notmp = true; return; }
			
			if(skill.equals("flysword") && Mp < 45) { notmp = true; return; }
			if(skill.equals("ironshield") && Mp < 30) { notmp = true; return; }
			if(skill.equals("healthboost") && Mp < 40) { notmp = true; return; }
			
			if(skill.equals("fireball") && Mp < 30) { notmp = true; return; }
			if(skill.equals("thundercloud") && Mp < 60) { notmp = true; return; }
			if(skill.equals("icecrystal") && Mp < 100) { notmp = true; return; }
			
			if(skill.equals("heal") && Mp < 20) { notmp = true; return; }
			if(skill.equals("holyprism") && Mp < 5) { notmp = true; return; }
			if(skill.equals("defboost") && Mp < 40) { notmp = true; return; }
			
			if(skill.equals("poisonhit") && Mp < 25) { notmp = true; return; }
			if(skill.equals("steal") && Mp < 10) { notmp = true; return; }
			if(skill.equals("invisibility") && Mp < 15) { notmp = true; return; }
			
			if(skill.equals("berserk") && Mp < 25) { notmp = true; return; }
			if(skill.equals("overpower") && Mp < 50) { notmp = true; return; }
			if(skill.equals("hammercrash") && Mp < 20) { notmp = true; return; }
			
			if(skill.equals("lockshot") && Mp < 15) { notmp = true; return; }
			if(skill.equals("mine") && Mp < 20) { notmp = true; return; }
			if(skill.equals("bulletrain") && Mp < 40) { notmp = true; return; }
			
			if(skill.equals("tripleattack")) { Mp = Mp - 5; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("rockbound")) { Mp = Mp - 5; if(Mp <= 0) { Mp = 0;}}
			if(skill.equals("regen")) { Mp = Mp - 2; if(Mp <= 0) { Mp = 0;} }
			
			if(skill.equals("flysword")) { Mp = Mp - 25; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("ironshield")) { Mp = Mp - 15; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("healthboost")) { Mp = Mp - 40; if(Mp <= 0) { Mp = 0;} }
			
			if(skill.equals("fireball")) { Mp = Mp - 10; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("thundercloud")) { Mp = Mp - 40; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("icecrystal")) { Mp = Mp - 100; if(Mp <= 0) { Mp = 0;} }
			
			if(skill.equals("heal")) { Mp = Mp - 10; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("holyprism")) { Mp = Mp - 5; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("defboost")) { Mp = Mp - 40; if(Mp <= 0) { Mp = 0;} }
			
			if(skill.equals("poisonhit")) { Mp = Mp - 10; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("steal")) { Mp = Mp - 10; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("invisibility")) { Mp = Mp - 15; if(Mp <= 0) { Mp = 0;} }
			
			if(skill.equals("berserk")) { Mp = Mp - 25; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("overpower")) { Mp = Mp - 50; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("hammercrash")) { Mp = Mp - 20; if(Mp <= 0) { Mp = 0;} }
			
			if(skill.equals("lockshot")) { Mp = Mp - 15; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("mine")) { Mp = Mp - 20; if(Mp <= 0) { Mp = 0;} }
			if(skill.equals("bulletrain")) { Mp = Mp - 40; if(Mp <= 0) { Mp = 0;} }
			
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
		
		public void MapChange(String map) {
			if(map.equals("Sewers")) {
				player.Map = "Sewers";
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
				if(item.equals("garrafadrink")) { player.hotkey1 = item; return; }
				if(item.equals("mpcan")) { player.hotkey1 = item; return; }
				if(item.equals("garrafamagica")) { player.hotkey1 = item; return; }
				if(item.equals("stcan")) { player.hotkey1 = item; return; }
				if(item.equals("garrafasuco")) { player.hotkey1 = item; return; }
			}
			if(hotkeynum == 2) {
				if(item.equals("hpcan")) { player.hotkey2 = item; return; }
				if(item.equals("garrafadrink")) { player.hotkey2 = item; return; }
				if(item.equals("mpcan")) { player.hotkey2 = item; return; }
				if(item.equals("garrafamagica")) { player.hotkey2 = item; return; }
				if(item.equals("stcan")) { player.hotkey2 = item; return; }
				if(item.equals("garrafasuco")) { player.hotkey2 = item; return; }
			}
		}
		
		public void CheckStatus(String status) {
			int Str = Integer.parseInt(player.Str);
			int Vit = Integer.parseInt(player.Vit);
			int Agi = Integer.parseInt(player.Agi);
			int Wis = Integer.parseInt(player.Wis);
			int Dex = Integer.parseInt(player.Dex);
			int HPMax = Integer.parseInt(player.Hp);
			int MpMax = Integer.parseInt(player.Mp);
			int StatusPoint = Integer.parseInt(player.StatusPoint);
			
			int Atk = Integer.parseInt(player.Atk);
			int Def = Integer.parseInt(player.Def);
			
			int AtkTimerMax = Integer.parseInt(player.AtkTimerMax);
			
			if (StatusPoint >= 1) {
				if (status.equals("Str")) {
					StatusPoint = StatusPoint - 1;
					Str = Str + 1;
					Atk = Atk + 2;
				} else if (status.equals("Vit")) {
					Vit = Vit + 1;
					StatusPoint = StatusPoint - 1;
					player.Def = player.Def + 2;
					HPMax = HPMax + 5;
				} else if (status.equals("Agi")) {
					Agi = Agi + 1;
					player.Def = player.Def + 1;
					StatusPoint = StatusPoint - 1;
					AtkTimerMax = AtkTimerMax - 1;
				} else if (status.equals("Wis")) {
					Wis = Wis + 1;
					StatusPoint = StatusPoint - 1;
					player.Def = player.Def + 1;
					MpMax = MpMax + 5;
				} else if (status.equals("Dex")) {
					Dex = Dex + 1;
					Atk = Atk + 1;
					StatusPoint = StatusPoint - 1;
				}
				
				// Update player attributes
				player.Str = String.valueOf(Str);
				player.Vit = String.valueOf(Vit);
				player.Agi = String.valueOf(Agi);
				player.Wis = String.valueOf(Wis);
				player.Dex = String.valueOf(Dex);
				player.Hp = String.valueOf(HPMax);
				player.Mp = String.valueOf(MpMax);
				player.StatusPoint = String.valueOf(StatusPoint);
				player.Atk = String.valueOf(Atk);
				player.AtkTimerMax = String.valueOf(AtkTimerMax);
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
							    		avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
							    		aviso = true;
							    	}
							    }
		
							    @Override
							    public void onFailure(Throwable t) {
							       System.out.println("Error: " + t.getMessage());
							       avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
								   aviso = true;
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
				if(player.playerInCast.equals("no")) { 
					movement = true; 
				} 
				else { 
					movement = false; 
				}
				
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
				if(coordsTouch.x > cameraCoordsX - 99 && coordsTouch.x < cameraCoordsX - 61 && coordsTouch.y > cameraCoordsY + 57 && coordsTouch.y < cameraCoordsY + 96) {
					state = "Menu";
					return false;
				}
				
				//Action
				if(coordsTouch.x > cameraCoordsX + 62 && coordsTouch.x < cameraCoordsX + 73 && coordsTouch.y > cameraCoordsY -60 && coordsTouch.y < cameraCoordsY -35)  {
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
				//Block
				//if(coordsTouch.x > cameraCoordsX + 62 && coordsTouch.x < cameraCoordsX + 73 && coordsTouch.y > cameraCoordsY -60 && coordsTouch.y < cameraCoordsY -35) {
				//	return false;
				//}
				//Target
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY -60 && coordsTouch.y < cameraCoordsY -35) {
					ChangeTarget();
					return false;
				}
				//Skill 1
				if(coordsTouch.x > cameraCoordsX + 47 && coordsTouch.x < cameraCoordsX + 56 && coordsTouch.y > cameraCoordsY - 90 && coordsTouch.y < cameraCoordsY - 66) {
					if(skillUsed > 0) { return false; }
					skillUsed = 200;
					CheckSkill(1);
					return false;
				}
				//Skill 2
				if(coordsTouch.x > cameraCoordsX + 63 && coordsTouch.x < cameraCoordsX + 72 && coordsTouch.y > cameraCoordsY - 90 && coordsTouch.y < cameraCoordsY - 66) {
					if(skillUsed > 0) { return false; }
					skillUsed = 200;
					CheckSkill(2);
					return false;
				}
				//Skill 3
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY - 90 && coordsTouch.y < cameraCoordsY - 66) {			
					return false;
				}
				//Sit 
				if(coordsTouch.x > cameraCoordsX - 45 && coordsTouch.x < cameraCoordsX - 25 && coordsTouch.y > cameraCoordsY + 86 && coordsTouch.y < cameraCoordsY + 97) {
					if(!player.playerInBattle.equals("yes")) {
						if(player.playerSit.equals("none")) {
							player.playerSit = "yes";
						}
						else {
							player.playerSit = "none";
						}
					}
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
				
				//Descartar
				if(coordsTouch.x > cameraCoordsX - 12 && coordsTouch.x < cameraCoordsX + 17 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 48) {
					menuoption = "descartar";
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
				//Dungeon 1
				if(coordsTouch.x > cameraCoordsX - 47 && coordsTouch.x < cameraCoordsX + 32 && coordsTouch.y > cameraCoordsY + 17 && coordsTouch.y < cameraCoordsY + 34) {
					MapChange("Sewers");
					return false;
				}
				//Voltar
				if(coordsTouch.x > cameraCoordsX - 21 && coordsTouch.x < cameraCoordsX + 12 && coordsTouch.y > cameraCoordsY - 42 && coordsTouch.y < cameraCoordsY - 24) {
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
				if(coordsTouch.x > cameraCoordsX - 12 && coordsTouch.x < cameraCoordsX + 17 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 48) {
					menuoption = "descartar";
					return false;
				}
				
				
				//Desligar touch
				if(coordsTouch.x > cameraCoordsX + 13 && coordsTouch.x < cameraCoordsX + 25 && coordsTouch.y > cameraCoordsY - 35 && coordsTouch.y < cameraCoordsY - 13) {
					//if(controlstate.equals("PC")) { controlstate = "Mobile"; }
					//else { controlstate = "PC"; }		
					//return false;
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
				if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 32 && coordsTouch.y > cameraCoordsY - 8 && coordsTouch.y < cameraCoordsY - 30) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(12,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(12,2); menuoption = ""; return false;  }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(12); menuoption = ""; return false; }
					gameControl.UseItem(12);
					return false;
				}
				
				//Item 14
				if(coordsTouch.x > cameraCoordsX - 30 && coordsTouch.x < cameraCoordsX - 18 && coordsTouch.y > cameraCoordsY - 8 && coordsTouch.y < cameraCoordsY - 30) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(13,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(13,2); menuoption = ""; return false;  }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(13); menuoption = ""; return false; }
					gameControl.UseItem(13);
					return false;
				}
				
				//Item 15
				if(coordsTouch.x > cameraCoordsX - 16 && coordsTouch.x < cameraCoordsX - 4 && coordsTouch.y > cameraCoordsY - 8 && coordsTouch.y < cameraCoordsY - 30) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(14,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(14,2); menuoption = ""; return false;  }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(14); menuoption = ""; return false; }
					gameControl.UseItem(14);
					return false;
				}
				
				//Item 16
				if(coordsTouch.x > cameraCoordsX - 3 && coordsTouch.x < cameraCoordsX + 10 && coordsTouch.y > cameraCoordsY - 8 && coordsTouch.y < cameraCoordsY - 30) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(15,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(15,2); menuoption = ""; return false;  }
					if(menuoption.equals("descartar")) { gameControl.DiscartItem(15); menuoption = ""; return false; }
					gameControl.UseItem(15);
					return false;
				}
				
				
				//Status point
				//STR
				if(coordsTouch.x > cameraCoordsX - 81 && coordsTouch.x < cameraCoordsX - 69 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					CheckStatus("Str");
					return false;
				}
				//VIT
				if(coordsTouch.x > cameraCoordsX - 67 && coordsTouch.x < cameraCoordsX - 55 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					CheckStatus("Vit");
					return false;
				}
				//AGI
				if(coordsTouch.x > cameraCoordsX - 54 && coordsTouch.x < cameraCoordsX - 41 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					CheckStatus("Agi");
					return false;
				}
				//WIS
				if(coordsTouch.x > cameraCoordsX - 40 && coordsTouch.x < cameraCoordsX - 27 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					CheckStatus("Wis");
					return false;
				}
				//DES
				if(coordsTouch.x > cameraCoordsX - 26 && coordsTouch.x < cameraCoordsX - 14 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					CheckStatus("Dex");
					return false;
				}
				
				//unequip
				if(coordsTouch.x > cameraCoordsX + 68 && coordsTouch.x < cameraCoordsX + 80 && coordsTouch.y > cameraCoordsY + 40 && coordsTouch.y < cameraCoordsY + 62) {
					gameControl.UnequipHat();
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
					if(coordsTouch.x > cameraCoordsX - 61 && coordsTouch.x < cameraCoordsX - 47 && coordsTouch.y > cameraCoordsY + 37 && coordsTouch.y < cameraCoordsY + 59) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("refrishop", 1);
						return false; 
					}
				}
				
				if(coordsTouch.x > cameraCoordsX + 51 && coordsTouch.x < cameraCoordsX + 61 && coordsTouch.y > cameraCoordsY + 60 && coordsTouch.y < cameraCoordsY + 75) {
					state = "Main";
					return false; 
				}
			}
			
			if (state.contains("keyboard")) {
				this.KeyboardKeyPressed(coordsTouch.x, coordsTouch.y);
				return false;
			}
			
			return false;
		}
		
		public void ShowMobs() {			
			
			listMonsters = gameControl.RecoverMonsterList();
			float playerPosX = Float.parseFloat(player.PosX);
			float playerPosY = Float.parseFloat(player.PosY);
			
			if(player.Map.equals("Sewers") || player.Map.equals("Watercave") || player.Map.equals("Mines") || player.Map.equals("Snowpalace") || player.Map.equals("Tower")) {
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
							mobTimerFrame = 40;
							mobFrame = listMonsters.get(i).MobFrame;
							mobFrame++;
							if(mobFrame > 3) { mobFrame = 1;}
							listMonsters.get(i).MobFrame = mobFrame;
							listMonsters.get(i).MobFrameTime = 40;
						}
						
						//Sem Target
						if(!player.SyncPlayerMob.equals("none")) {
							mobTimerMov = listMonsters.get(i).MobTimerMov;
							mobRandomSt = listMonsters.get(i).MobRandomSt;
							if(mobTimerMov >= 0) { 
								mobTimerMov--;
								listMonsters.get(i).MobTimerMov = mobTimerMov;
							}
							if(mobTimerMov < 0) { 
								mobRandomSt = randnumber.nextInt(4); 
								mobTimerMov = 100; 
								listMonsters.get(i).MobTimerMov = mobTimerMov;
								listMonsters.get(i).MobRandomSt = mobRandomSt;
							}
												
							if(mobRandomSt == 0) { 
								mobPositionCoordX = listMonsters.get(i).MobPosX;
								mobPositionCoordX = mobPositionCoordX + 0.07f; 
								listMonsters.get(i).MobPosX = mobPositionCoordX;
							}
							if(mobRandomSt == 1) { 
								mobPositionCoordX = listMonsters.get(i).MobPosX;
								mobPositionCoordX = mobPositionCoordX - 0.07f;
								listMonsters.get(i).MobPosX = mobPositionCoordX;
							}
							if(mobRandomSt == 2) { 
								mobPositionCoordY = listMonsters.get(i).MobPosY;
								mobPositionCoordY = mobPositionCoordY + 0.07f;
								listMonsters.get(i).MobPosY = mobPositionCoordY;
							}
							if(mobRandomSt == 3) { 
								mobPositionCoordY = listMonsters.get(i).MobPosY;
								mobPositionCoordY = mobPositionCoordY - 0.07f;
								listMonsters.get(i).MobPosY = mobPositionCoordY;
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
						
						if(player.Map.equals("Sewers")) { 
							spr_monster = gameControl.GetMonster(listMonsters.get(i).MobName, listMonsters.get(i).MobFrame, "L");
						}
						//if(player.Map_A.equals("Watercave")) { spr_monster = atlas_mobWatercave.createSprite(listMonsters.get(i).MobName + listMonsters.get(i).MobFrame + "L"); }
						//if(player.Map_A.equals("Mines")) { spr_monster = atlas_mobMines.createSprite(listMonsters.get(i).MobName + listMonsters.get(i).MobFrame + "L"); }
						//if(player.Map_A.equals("Snowpalace")) { spr_monster = atlas_mobSnowpalace.createSprite(listMonsters.get(i).MobName + listMonsters.get(i).MobFrame + "L"); }
						//if(player.Map_A.equals("Tower")) { spr_monster = atlas_mobTower.createSprite(listMonsters.get(i).MobName + listMonsters.get(i).MobFrame + "L"); }
						spr_monster.setPosition(listMonsters.get(i).MobPosX, listMonsters.get(i).MobPosY);
						spr_monster.setSize(listMonsters.get(i).MobSizeX, listMonsters.get(i).MobSizeY);
						spr_monster.draw(game.batch);
									
						mobPositionCoordX = listMonsters.get(i).MobPosX;
						mobPositionCoordY = listMonsters.get(i).MobPosY;
						mobPositionCoordY = mobPositionCoordY - 0.2f;
						font_master.getData().setScale(0.10f,0.15f);
						font_master.setUseIntegerPositions(false);
						font_master.draw(game.batch, listMonsters.get(i).MobName,mobPositionCoordX, mobPositionCoordY);
						font_master.draw(game.batch, " HP :" + listMonsters.get(i).MobHp + "/" + listMonsters.get(i).MobHpMax ,mobPositionCoordX - 4, mobPositionCoordY - 8);
						
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
