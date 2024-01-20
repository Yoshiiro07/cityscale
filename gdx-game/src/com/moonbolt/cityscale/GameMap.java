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
	
		//Objects
	    private MainGame game;
	    private ManagerScreen screen;
	    private GameControl gameControl;
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
	    private Player player;
	    private float playerPosX;
	    private float playerPosY;
	    private float plPosX;
	    private float plPosY;
	    private float touchSkillX;
	    private float touchSkillY;
	    private boolean movement = false;	
	        	    
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
	    private ArrayList<Monster> lstMobs;
	    private int mobFrame = 1;
	    private float mobPositionCoordX = 0;
	    private float mobPositionCoordY = 0;
	    private int mobRandomSt = 0;
	    private int mobTimerMov = 100;
	    private int mobTimerFrame = 40;
	    
	    //Online
	    private ArrayList<Player> lstOnlinePlayers;
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
	    private Player newOnlinePlayer;
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
	    private ArrayList<Damage> lstDamage;
	    
	    //Skills
	    private boolean showZone = false;
	    private int countZoneSkill = 0;
	    private ArrayList<Skill> lstSkills;
	    
	    //NPC
	    private float npcsideleft;
	    private float npcsideright;
	    private String steptalk = "";
	    private int npcframe = 1;
	    private boolean setNPCSdefault = true;
	    
	    //Sprites Background
	    private Sprite spr_Background;
	    private Texture tex_Background;
	    
	    //Teste Dot
	    private Sprite spr_testeDot;
	    private Texture tex_testeDot;
	    private float testX;
	    private float testY;
	    
	    //Controller
	    private final IntSet downKeys = new IntSet(20);	
		
		public GameMap(MainGame _game,ManagerScreen _screen, boolean _network) {
			this.game = _game;	
			this.screen = _screen;
			this.json = new Json();
			this.randnumber = new Random();
			this.network = network;
			this.gameControl = new GameControl();
			
			lstOnlinePlayers = new ArrayList<Player>();
			lstDamage = new ArrayList<Damage>();
			lstSkills = new ArrayList<Skill>();
			lstChats = new ArrayList<String>();
			newOnlinePlayer = new Player();
			
			//Set list of chats
			lstChats.add(""); lstChats.add(""); lstChats.add(""); 

			//test dot
			tex_testeDot = new Texture(Gdx.files.internal("data/assets/misc/selected.png"));
			spr_testeDot = new Sprite(tex_testeDot);
			
			//Load Player Data
			file = Gdx.files.local("SaveData/save.json");		
			player = json.fromJson(Player.class, Base64Coder.decodeString(file.readString()));
			
			//Load Title
			if(player.Map_A.equals("StreetsA")) {
				tex_Background = new Texture(Gdx.files.internal("data/assets/maps/streetsA.png")); 
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
					
			player.party_A = "none";
			player.playerInAttack_A = "no";
			player.playerInBattle_A = "no";
			player.playerInCast_A = "no";
		}
			
		@Override
		public void render(float delta) {
			try {
				savedataTime--;
				if(savedataTime < 0) {
					savedataTime = 700;
			
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
	
		@Override
		public void input(String input) {
			if(input.contains(":")) { return; }
			if(input.contains("none")) { return; }
		}

		@Override
		public void canceled() {}

		@Override
		public boolean keyDown(int keycode) {
			
			//if(playerDead) { return false; }
			
			if(state.equals("main")) {
				movement = true;		
				downKeys.add(keycode);
		        if (downKeys.size >= 2){
		            onMultipleKeysDown(keycode);
		        }
		        if(downKeys.size == 1) {
		        	if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
		        		player.Side_A = "left";
		        		player.Walk_A = "walk"; 
		        		return false;
		            }
		    		
		    		if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
		    			player.Side_A = "back";
		    			player.Walk_A = "walk";
		    			return false;
		            }
		    		
		    		if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
		    			player.Side_A = "front";
		    			player.Walk_A = "walk";	
		    			return false;
		            }
		    		
		    		if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
		    			player.Side_A = "right";
		    			player.Walk_A = "walk";
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
		        	player.Side_A = "left-front";
		        	player.Walk_A = "walk";  	
		        }
		    }
		    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
		        	player.Side_A = "left-back";
		        	player.Walk_A = "walk";
		        }
		    }
		    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
		    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
		    		player.Side_A = "right-back";
		    		player.Walk_A = "walk";	
		    	}
		    }
		    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
		    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
		    		player.Side_A = "right-front";
		    		player.Walk_A = "walk";	
		    	}
		    }
		    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
		        	player.Side_A = "back-right";
		        	player.Walk_A = "walk";
		        }
		    }
		    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
		        	player.Side_A = "back-left";
		        	player.Walk_A = "walk";
		         }
		    }
		    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
		        	player.Side_A = "front-right";
		        	player.Walk_A = "walk";
		       }
		    }
		    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
		        	player.Side_A = "front-left";
		        	player.Walk_A = "walk";	
		         }
		    }
		}	

		@Override
		public boolean keyUp(int keycode) {
			movement = false;
			downKeys.remove(keycode);
			player.Walk_A = "no";
			//breakwalk = "";
			
			if(player.Side_A.equals("left-front")) { player.Side_A = "front"; }
			if(player.Side_A.equals("left-back")) { player.Side_A = "front";}
			if(player.Side_A.equals("right-back")) { player.Side_A = "front";}
			if(player.Side_A.equals("right-front")) { player.Side_A = "front";}
			if(player.Side_A.equals("back-right")) { player.Side_A = "front";}
			if(player.Side_A.equals("back-left")) { player.Side_A = "front";}
			if(player.Side_A.equals("front-right")) { player.Side_A = "front";}
			if(player.Side_A.equals("front-left")) { player.Side_A = "front"; }
			
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int p1, int p2, int pointer, int button) {
			
			//if(playerDead) { return false; }
			
			Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));	
			
			
			return false;
			
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			movement = false;
			player.Walk_A = "no";
			
			if(player.Side_A.equals("left-front")) { player.Side_A = "front"; }
			if(player.Side_A.equals("left-back")) { player.Side_A = "front";}
			if(player.Side_A.equals("right-back")) { player.Side_A = "front";}
			if(player.Side_A.equals("right-front")) { player.Side_A = "front";}
			if(player.Side_A.equals("back-right")) { player.Side_A = "front";}
			if(player.Side_A.equals("back-left")) { player.Side_A = "front";}
			if(player.Side_A.equals("front-right")) { player.Side_A = "front";}
			if(player.Side_A.equals("front-left")) { player.Side_A = "front"; }
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			
			//if(playerDead) { return false; }
			
			Vector3 coordsTouch = camera.unproject(new Vector3(screenX,screenY,0));
			
			if(movement){	
				//Right
     				if(coordsTouch.x >= -53.5f && coordsTouch.x <= -45.5f && coordsTouch.y > -50f && coordsTouch.y < -42f) {
					player.Side_A = "right";
					player.Walk_A = "walk";	
					return false;
				}
				//Left
				if(coordsTouch.x >= -57.5f && coordsTouch.x <= -45.5f && coordsTouch.y > -50f && coordsTouch.y < -42) {
					player.Side_A = "left";
					player.Walk_A = "walk";	
					return false;
				}
				//Front
				if(coordsTouch.x > -59.5f && coordsTouch.x < -51.5f && coordsTouch.y > -65 && coordsTouch.y < -50f) {
					player.Side_A = "front";
					player.Walk_A = "walk";	
					return false;
				}
				//Back
				if(coordsTouch.x > -59.5f && coordsTouch.x < -51.5f && coordsTouch.y > -42f && coordsTouch.y < -27) {
					player.Side_A = "back";
					player.Walk_A = "walk";	
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
