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
import com.moonbolt.cityscale.models.Player;


public class MetroStation implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	
		//Objects
	    private MainGame game;
	    private ManagerScreen screen;
	    private GameControl gameControl;
	    private String state = "Main";
	    private Sprite spr_master;
	    private boolean network = false;
	    
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
	    private Sprite spr_playerhair;
	    private Sprite spr_playertop;
	    private Sprite spr_playerbottom;
	    private Sprite spr_playerfooter;
	    private boolean playerDead = false;
	    private boolean movement = false;
	    private String breakwalk = "";
	    private int countFrame = 1;
	    
	    //UX
	    private float padmoveX = -56;
	    private float padmoveY =  -50;
	    private String padmove = "center";
	    private Sprite spr_playerTag;
	     
	    //Sprites Background
	    private Sprite spr_Background;
	    private Texture tex_Background;
	    
	    private Sprite spr_metro;
	    private Texture tex_metro;
	    private float metroX = -189;
	    
	    //Teste Dot
	    private Sprite spr_testeDot;
	    private Texture tex_testeDot;
	    private float testX;
	    private float testY;
	    
	    //Controller
	    private final IntSet downKeys = new IntSet(20);	
		
		public MetroStation(MainGame gameAlt,ManagerScreen screenAlt, boolean networkAlt) {
			this.game = gameAlt;	
			this.screen = screenAlt;
			this.network = networkAlt;
			
			//Load Player Data
			this.gameControl = new GameControl();
			player = gameControl.LoadData();
			
			//Load Title
			tex_Background = new Texture(Gdx.files.internal("data/assets/maps/metrostation.png")); 
			tex_metro = new Texture(Gdx.files.internal("data/assets/etc/metro.png")); 
			spr_metro = new Sprite(tex_metro);
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
			
			//test dot
			tex_testeDot = new Texture(Gdx.files.internal("data/assets/etc/testdot.png"));
			spr_testeDot = new Sprite(tex_testeDot);
		}
			
		@Override
		public void render(float delta) {
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
				
				metroX++;
				spr_metro.setSize(100, 40);
				spr_metro.setPosition(metroX, 36);
				spr_metro.draw(game.batch);
				if(metroX > 100) { metroX = -189; }
				
				//UX						
				spr_master = gameControl.GetUXSmall("innerpad");
				spr_master.setPosition(padmoveX, padmoveY);
				spr_master.draw(game.batch);
				
				
				//Char
				player = gameControl.SetCharMov(player, player.breakwalk_A);
				spr_playerhair = gameControl.GetHairChar(player, "no", 0, 0);
				spr_playerhair.draw(game.batch);
				
				spr_playerfooter = gameControl.GetFooterChar(player,"no", 0, 0);
				spr_playerfooter.draw(game.batch);
				
				spr_playerbottom = gameControl.GetBottomChar(player,"no", 0, 0);
				spr_playerbottom.draw(game.batch);
				
				spr_playertop = gameControl.GetTopChar(player,"no", 0, 0);
				spr_playertop.draw(game.batch);
				
				
				//Colision
				CheckColision();
											
				//Teste				
				//spr_testeDot.setPosition(59.5f, -65);
				//spr_testeDot.setSize(1, 1);
				//spr_testeDot.draw(game.batch);
				
				//spr_testeDot.setPosition(-51.5f, -50f);
				//spr_testeDot.setSize(1, 1);
				//spr_testeDot.draw(game.batch);
				
				game.batch.end();
			}
			
			
		
		
		public void CheckColision() {
			if(player.Map_A.equals("MetroStation")) {
				if(player.PosX_A > 32 && player.PosX_A < 36 && player.PosY_A > - 68 && player.PosY_A < -47) {
					player.Map_A = "StreetsA";
					player.PosX_A = 134;
					player.PosY_A = -186;
					gameControl.SaveData(player);
					this.screen.screenSwitch("LoadingScreen", network);
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
			
			if(state.equals("Main")) {
				movement = true;		
				downKeys.add(keycode);
		        if (downKeys.size >= 2){
		            onMultipleKeysDown(keycode);
		        }
		        if(downKeys.size == 1) {
		        	if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
		        		player.Side_A = "left";
		        		player.Walk_A = "walk"; 
		        		padmoveX = -66;
		        		player.playerInBattle_A = "no";
		        		return false;
		            }
		    		
		    		if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
		    			player.Side_A = "back";
		    			player.Walk_A = "walk";
		    			padmoveY = -50;
		    			player.playerInBattle_A = "no";
		    			return false;
		            }
		    		
		    		if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
		    			player.Side_A = "front";
		    			player.Walk_A = "walk";	
		    			padmoveY = -60;
		    			player.playerInBattle_A = "no";
		    			return false;
		            }
		    		
		    		if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
		    			player.Side_A = "right";
		    			player.Walk_A = "walk";
		    			padmoveX = -55;
		    			player.playerInBattle_A = "no";
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
			player.Walk_A = "no";
			player.Frame_A = 1;
			countFrame = 1;
			padmoveX = -60;
			padmoveY = -55;
			player.breakwalk_A = "";
			
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
		
		private void onMultipleKeysDown (int mostRecentKeycode){
			
			if(state.equals("menu")) { return; }
			
			//For multiple key presses
		    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
		        	//player.Side_A = "left-front";
		        	player.Side_A = "left";
		        	player.Walk_A = "walk";  	
		        	padmoveX = -66;
		        	padmoveY = -60;
		        	player.playerInBattle_A = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
		        	//player.Side_A = "left-back";
		        	player.Side_A = "left";
		        	player.Walk_A = "walk";
		        	padmoveX = -66;
		        	padmoveY = -50; 
		        	player.playerInBattle_A = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
		    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
		    		//player.Side_A = "right-back";
		    		player.Side_A = "right";
		    		player.Walk_A = "walk";	
		    		padmoveX = -55;
		    		padmoveY = -50;
		    		player.playerInBattle_A = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
		    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
		    		//player.Side_A = "right-front";
		    		player.Side_A = "right";
		    		player.Walk_A = "walk";	
		    		padmoveX = -55;
		    		padmoveY = -60;
		    		player.playerInBattle_A = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
		        	//player.Side_A = "back-right";
		        	player.Side_A = "back";
		        	player.Walk_A = "walk";
		        	padmoveX = -55;
		        	padmoveY = -50;
		        	player.playerInBattle_A = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
		        	//player.Side_A = "back-left";
		        	player.Side_A = "back";
		        	player.Walk_A = "walk";
		        	padmoveX = -66;
		        	padmoveY = -50;
		        	player.playerInBattle_A = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
		        	//player.Side_A = "front-right";
		        	player.Side_A = "front";
		        	player.Walk_A = "walk";
		        	padmoveX = -55;
		        	padmoveY = -60;
		        	player.playerInBattle_A = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
		        	//player.Side_A = "front-left";
		        	player.Side_A = "front";
		        	player.Walk_A = "walk";	
		        	padmoveX = -66;
		        	padmoveY = -60;
		        	player.playerInBattle_A = "no";
		        }
		    }
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
			
			//Main
			//[Main State]//
			if(state.equals("Main")) {
				if(player.playerInCast_A.equals("none")) { movement = true; } else { movement = false; }
				
				//Menu
				if(coordsTouch.x > -70f && coordsTouch.x < -39f && coordsTouch.y > 39f && coordsTouch.y < 67f) {
					state = "menu";
					return false;
				}	
			}
			
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			movement = false;
			player.breakwalk_A = "";
			player.Walk_A = "no";
			padmoveX = -60;
			padmoveY = -55;
			
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
			if(playerDead) { return false; }
			
			Vector3 coordsTouch = camera.unproject(new Vector3(screenX,screenY,0));
			
			if(movement){	
				//Right
     				if(coordsTouch.x >= -53.5f && coordsTouch.x <= -45.5f && coordsTouch.y > -50f && coordsTouch.y < -42f) {
					player.Side_A = "right";
					player.Walk_A = "walk";	
					padmoveX = -55;
					player.playerInBattle_A = "no";
					return false;
				}
				//Left
				if(coordsTouch.x >= -57.5f && coordsTouch.x <= -45.5f && coordsTouch.y > -50f && coordsTouch.y < -42) {
					player.Side_A = "left";
					player.Walk_A = "walk";	
					padmoveX = -66;		
					player.playerInBattle_A = "no";
					return false;
				}
				//Front
				if(coordsTouch.x > -59.5f && coordsTouch.x < -51.5f && coordsTouch.y > -65 && coordsTouch.y < -50f) {
					player.Side_A = "front";
					player.Walk_A = "walk";	
					padmoveY = -60;
					player.playerInBattle_A = "no";
					return false;
				}
				//Back
				if(coordsTouch.x > -59.5f && coordsTouch.x < -51.5f && coordsTouch.y > -42f && coordsTouch.y < -27) {
					player.Side_A = "back";
					player.Walk_A = "walk";	
					padmoveY = -50;
					player.playerInBattle_A = "no";
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
