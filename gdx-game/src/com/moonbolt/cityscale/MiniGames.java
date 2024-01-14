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


public class MiniGames implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	
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
	    private boolean onlineAuth = false;
	    private boolean versionDif = false; 
	    private boolean uploadDone = false;
	    private boolean notmp = false;
	    private boolean accountnumber = false;
		private float npcWalk1 = 100;
	    private float npcWalk2 = -20;
	    private TextureAtlas atlas_generic;
	    private int savedataTime = 500;
	    
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
	    private float playerSpeed = 0;
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
	    
	    //Online
	    private ArrayList<GameObject> lstOnlinePlayers;
	    private int countCleanOnline = 700;
		private String retornoOnline = "";
		private int threahCountSyncPlayer = 0;
		private int threahCountSyncChat = 0;
		private int threahCountSyncMob = 0;
		private Thread thrOnlineSyncPlayer;
		private Thread thrOnlineSyncChat;
		private Thread thrOnlineSyncMob;
		private boolean network = true;
		private Sprite spr_playerOnline;
	    private Sprite spr_hairOnline;
	    private Sprite spr_hatOnline;	 
	    private Sprite spr_weaponOnline;
	    private GameObject newOnlinePlayer;
	    private boolean keepnetwork = false;
	    private boolean receiveExpOnline = true;
	    private int timerreceiveExpOnline = 0;
	    private int GiveExp = 0;
	    private int timerGiveExp = 100;
	    private int countParty = 0;
	    private boolean playerMobSync = false;
	    private int timecheckonline = 2;
	    		
	    //Chats
	    private ArrayList<String> lstChats;
	    
	    //Sprites Background
	    private Sprite spr_Background;
	    private Texture tex_Background;
	    
	    //Volleyball
	    private TextureAtlas atlas_ball;
	    
	  	//Textures
	    private TextureAtlas atlas_gameUI;
	    private TextureAtlas atlas_basicset;
	    private TextureAtlas atlas_blackset;
	    
	    private TextureAtlas atlas_cards;
	     
	    private TextureAtlas atlas_hairs;
	    private TextureAtlas atlas_hair1;
	    private TextureAtlas atlas_hair2;
	    
	    private TextureAtlas atlas_itensSet;
	    private TextureAtlas atlas_itensDrop;
	    private TextureAtlas atlas_itensHat;
	    private TextureAtlas atlas_itensUtil;
	    private TextureAtlas atlas_itensWeapon;
	    private TextureAtlas atlas_hatFrame;
	    
	    private TextureAtlas atlas_mobSewers;
	    
	    //Teste Dot
	    private Sprite spr_testeDot;
	    private Texture tex_testeDot;
	    private float testX;
	    private float testY;
	    
	    //Controller
	    private final IntSet downKeys = new IntSet(20);	
		
		public MiniGames(MainGame gameAlt,ManagerScreen screenAlt, boolean network) {
			this.game = gameAlt;	
			this.screen = screenAlt;
			this.json = new Json();
			this.randnumber = new Random();
			this.network = network;
			
			lstOnlinePlayers = new ArrayList<GameObject>();
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
			if(player.Map.equals("Volleyball")) {
				tex_Background = new Texture(Gdx.files.internal("data/assets/maps/volleycourt.png")); 
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
			
			atlas_hair1 = new TextureAtlas(Gdx.files.internal("data/assets/chars/hair1.txt"));
			atlas_hair2 = new TextureAtlas(Gdx.files.internal("data/assets/chars/hair2.txt"));
			
			atlas_basicset = new TextureAtlas(Gdx.files.internal("data/assets/chars/basicset.txt"));
			atlas_blackset = new TextureAtlas(Gdx.files.internal("data/assets/chars/blackset.txt"));
			atlas_generic = new TextureAtlas(Gdx.files.internal("data/assets/chars/basicset.txt"));
			
			atlas_cards = new TextureAtlas(Gdx.files.internal("data/assets/cards/cards.txt"));
			
			atlas_itensSet = new TextureAtlas(Gdx.files.internal("data/assets/itens/itensSet/itensSet.txt"));
			atlas_itensHat = new TextureAtlas(Gdx.files.internal("data/assets/itens/itensHat/itensHat.txt"));
			atlas_itensUtil = new TextureAtlas(Gdx.files.internal("data/assets/itens/itensUtil/itensUtil.txt"));
			atlas_itensWeapon = new TextureAtlas(Gdx.files.internal("data/assets/itens/itensWeapon/itensWeapon.txt"));
			atlas_itensDrop = new TextureAtlas(Gdx.files.internal("data/assets/itens/itensDrop/itensDrop.txt"));
			
			atlas_hatFrame = new TextureAtlas(Gdx.files.internal("data/assets/itens/hatframes/hatframes.txt"));
			
			atlas_ball = new TextureAtlas(Gdx.files.internal("data/assets/cards/skilleffect/dashkick.txt"));
			
			player.party = "none";
			player.playerInAttack = "no";
			player.playerInBattle = "no";
			player.playerInCast = "no";
		}
			
		@Override
		public void render(float delta) {
			
				savedataTime--;
				if(savedataTime < 0) {
					savedataTime = 700;
					//SaveData();
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
				
				//float percent = (player.Exp * 100.0f) / CheckLevelExpPercent();
				font_master.draw(game.batch, String.valueOf(player.AtkTimer), cameraCoordsX + 57.5f, cameraCoordsY - 25.7f);
				font_master.draw(game.batch, player.Name, cameraCoordsX - 60.5f, cameraCoordsY + 64.7f);
				font_master.draw(game.batch, String.valueOf(player.Level), cameraCoordsX - 41.5f, cameraCoordsY + 64.7f);
				font_master.draw(game.batch, player.Hp + "/" + player.HpMax , cameraCoordsX - 60.5f, cameraCoordsY + 58.4f);
				font_master.draw(game.batch, player.Mp + "/" + player.MpMax , cameraCoordsX - 60.5f, cameraCoordsY + 51.7f);
				font_master.draw(game.batch, player.Stamina + "/" + player.StaminaMax , cameraCoordsX - 58.5f, cameraCoordsY + 44.7f);
				//font_master.draw(game.batch, String.format("%.02f", percent) + "%", cameraCoordsX - 43.5f, cameraCoordsY + 44.7f);
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
				
				game.batch.end();
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
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}
		
}
