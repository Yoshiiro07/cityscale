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
	    private boolean network = false;
	    private String state = "Main";
	    private Sprite spr_master;
	    private String SysMsg = "";
	    private int SysMsgCount = 0;    
	    private int savedataTime = 500;
	    
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
	    private float touchPosX;
	    private float touchPosY;
	    private int showPosX = 0;
	    private int showPosY = 0;
	    private boolean movement = false;	
	    private Sprite spr_playerUpper;
	    private Sprite spr_playerBottom;
	    private Sprite spr_playerHair;
	     
	    //Mob
	    private ArrayList<Monster> lstMobs;
	    
	    //Online
	    private ArrayList<Player> lstOnlinePlayers;
	    		
	    //Chats
	    private ArrayList<String> lstChats;
	    
	    //Damage
	    private ArrayList<Damage> lstDamage;
	    
	    //Skills
	    private ArrayList<Skill> lstSkills;
	    
	    //Sprites Background
	    private Sprite spr_Background;
	    private Texture tex_Background;
	    
	    //Teste Dot
	    private Sprite spr_testeDot;
	    private Texture tex_testeDot;
	    
	    //Controller
	    private final IntSet downKeys = new IntSet(20);	
		
		public GameMap(MainGame _game,ManagerScreen _screen, boolean _network) {
			this.game = _game;	
			this.screen = _screen;
			this.gameControl = new GameControl();
			this.network = _network;
			
			lstOnlinePlayers = new ArrayList<Player>();
			lstDamage = new ArrayList<Damage>();
			lstSkills = new ArrayList<Skill>();
			lstChats = new ArrayList<String>();
			
			//test dot
			tex_testeDot = new Texture(Gdx.files.internal("data/assets/misc/selected.png"));
			spr_testeDot = new Sprite(tex_testeDot);
			
			//Load Player Data
			player = gameControl.LoadData();
			
			//Load Title
			if(player.Map_A.equals("StreetsA")) { tex_Background = new Texture(Gdx.files.internal("data/assets/maps/streetsA.png")); }
				
			spr_Background = new Sprite(tex_Background);
					
			//Camera and Inputs
			camera = new OrthographicCamera();
		    viewport = new StretchViewport(200,200,camera);
			viewport.apply();
			camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
			Gdx.input.setInputProcessor(this);
	
			//font
			font_master = new BitmapFont(Gdx.files.internal("data/assets/font/impact.fnt"),Gdx.files.internal("data/assets/font/impact.png"), false);
			font_master.setColor(Color.WHITE);
			font_master.getData().setScale(0.07f,0.11f);
			font_master.setUseIntegerPositions(false);	
		}
			
		@Override
		public void render(float delta) {
			try {						
				savedataTime--;
				if(savedataTime < 0) { savedataTime = 700;}
				
				//Just for coloring
				Gdx.gl.glClearColor(1,1,1,1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
				playerPosX = player.PosX_A;
				playerPosY = player.PosY_A;
				
				//Camera Ajustments
				cameraCoordsX = playerPosX;
				cameraCoordsY = playerPosY;
				
				//Follow camera
				if(playerPosX <= 0f) { cameraCoordsX = 0; }
				if(playerPosX >= 175) { cameraCoordsX = 175; }
				if(playerPosY >= 91.5f) { cameraCoordsY = 91.5f; }
				if(playerPosY <= -80) { cameraCoordsY = -80; }
				
				camera.position.set(cameraCoordsX,cameraCoordsY + 30,0);
				camera.update();
			    game.batch.setProjectionMatrix(camera.combined);		    
				game.batch.begin();
				
				//Background	
				spr_Background.setPosition(-100, -150);
				spr_Background.setSize(400, 400);
				spr_Background.draw(game.batch);
				
				//Player
				gameControl.SetCharMovement("",touchPosX -5.5f,touchPosY);		
				spr_playerBottom = gameControl.CharacterMoveBottom("", 0);
				spr_playerBottom.draw(game.batch);
				
				spr_playerUpper = gameControl.CharacterMoveUpper("", 0);
				spr_playerUpper.draw(game.batch);
				
				spr_playerHair = gameControl.CharacterMoveHair();
				spr_playerHair.draw(game.batch);
				
				player = gameControl.GetPlayer();
				
				
				/////[Interface]/////
				
				//arrowmove
				spr_master = gameControl.GetInterface("arrowmove");
				spr_master.setPosition(touchPosX -1.5f, touchPosY);
				spr_master.setSize(4, 10);
				spr_master.draw(game.batch);
				
				//player tag
				spr_master = gameControl.GetInterface("tagplayer");
				spr_master.setPosition(cameraCoordsX - 99, cameraCoordsY + 90);
				spr_master.setSize(60, 40);
				spr_master.draw(game.batch);
				
				
				font_master.getData().setScale(0.13f,0.20f);
				font_master.setUseIntegerPositions(false);	
				
				font_master.draw(game.batch, player.Name_A , cameraCoordsX - 77 , cameraCoordsY + 124);
				font_master.draw(game.batch, String.valueOf(player.Hp_A), cameraCoordsX - 82 , cameraCoordsY + 115);
				font_master.draw(game.batch, String.valueOf(player.Mp_A) , cameraCoordsX - 63 , cameraCoordsY + 115);
				font_master.draw(game.batch, String.valueOf(player.Exp_A) , cameraCoordsX - 60 , cameraCoordsY + 105);
				font_master.draw(game.batch, String.valueOf(player.Level_A) , cameraCoordsX - 77 , cameraCoordsY + 105);
				
				showPosX = Math.round(player.PosX_A);
				showPosY = Math.round(player.PosX_A);
				font_master.draw(game.batch, "X:" + String.valueOf(showPosX), cameraCoordsX - 90 , cameraCoordsY + 90);
				font_master.draw(game.batch, "Y:" + String.valueOf(showPosY) , cameraCoordsX - 75 , cameraCoordsY + 90);
				
				//cards
				spr_master = gameControl.GetInterface("cardaction");
				spr_master.setPosition(cameraCoordsX + 78, cameraCoordsY - 65);
				spr_master.setSize(13, 32);
				spr_master.draw(game.batch);
				
				
				if(state.equals("Menu"))
				{
					spr_master = gameControl.GetInterface("charmenu");
					spr_master.setPosition(cameraCoordsX - 90, cameraCoordsY - 65);
					spr_master.setSize(180, 155);
					spr_master.draw(game.batch);
				}
				
				//ShowNPCS
				ShowNPC();
				
				//Teste				
				spr_testeDot.setPosition(touchPosX, touchPosY);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);
				
				spr_testeDot.setPosition(cameraCoordsX + 79, cameraCoordsY + 85);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);
				
				spr_testeDot.setPosition(cameraCoordsX + 87, cameraCoordsY + 72);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);
				
				if(state.equals("Change")) {
					this.screen.screenSwitch("LoadingScreen", network);
				}
				
				game.batch.end();		
			}
			
			catch(Exception ex) {
				Gdx.app.exit();
			}
		}
		
		
		public void CheckColisionMetroStation() {		
			//Change to CityStreets (RPG side)
			if(player.PosX_A > 30 && player.PosX_A < 45 && player.PosY_A > - 65 && player.PosY_A < - 40) {
				player.Map_A = "StreetsA";
				gameControl.SetPlayer(player);
				gameControl.SaveData(player);
				state = "Change";
			}
		}
	
		@Override
		public void input(String input) {
			if(input.contains(":")) { return; }
			if(input.contains("none")) { return; }
		}

		@Override
		public void canceled() {}
		
		
		public void ShowNPC() {
			spr_master = gameControl.ShowNPC("guard");
			spr_master.setPosition(0, 0);
			spr_master.setSize(0, 0);
			spr_master.draw(game.batch);
		}

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
			
			if(state.equals("Main")) {
				
				if(coordsTouch.x >=  cameraCoordsX - 100 && coordsTouch.x <= cameraCoordsX - 56 && coordsTouch.y >= cameraCoordsY + 93 && coordsTouch.y <= cameraCoordsY + 126) {
					state = "Menu";
					return false;
				}
				
				touchPosX = coordsTouch.x;
				touchPosY = coordsTouch.y;
				gameControl.SetCharSide(touchPosX, touchPosY);
			}
			
			if(state.equals("Menu")) {
				if(coordsTouch.x >=  cameraCoordsX + 79 && coordsTouch.x <= cameraCoordsX + 87 && coordsTouch.y >= cameraCoordsY + 72 && coordsTouch.y <= cameraCoordsY + 85) {
					state = "Main";
					return false;
				}
			}
			
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
