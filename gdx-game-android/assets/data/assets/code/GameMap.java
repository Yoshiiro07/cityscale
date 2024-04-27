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
	    private String state = "Main";
	    private Sprite spr_master;
	    
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
	    
	    //Sprite NPC
	    private Sprite spr_npc;
	    
	    //UX
	    private float padmoveX = -56;
	    private float padmoveY =  -50;
	    private Sprite spr_playerTag;
	     
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
		
		public GameMap(MainGame gameAlt,ManagerScreen screenAlt, boolean network) {
			this.game = gameAlt;	
			this.screen = screenAlt;
			
			//Load Player Data
			this.gameControl = new GameControl();
			player = gameControl.LoadData();
			
			if(player.Map_A.equals("StreetsA")) { tex_Background = new Texture(Gdx.files.internal("data/assets/maps/streetsA.png")); }	
			spr_Background = new Sprite(tex_Background);
					
			//Camera and Inputs
			camera = new OrthographicCamera();
		    viewport = new StretchViewport(195,195,camera);
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
			try {
				//Just for coloring
				Gdx.gl.glClearColor(1,1,1,1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
				//Camera Ajustments
				cameraCoordsX = player.PosX_A;
				cameraCoordsY = player.PosY_A;
				
				//Follow camera
				if(player.PosX_A <= 18.5f) 	{ cameraCoordsX = 18.5f; 	 }
				if(player.PosX_A >= 93) 	{ cameraCoordsX = 93; 	 }
				if(player.PosY_A >= -22f) { cameraCoordsY = -22f; }
				if(player.PosY_A <= -97) 	{ cameraCoordsY = -97;  }
				
				//Update camera and start drawling
				camera.position.set(cameraCoordsX -2,cameraCoordsY+1,0);
				camera.update();
			    game.batch.setProjectionMatrix(camera.combined);	    
				game.batch.begin();
				
				//Background	
				spr_Background.setPosition(-81,-194);
				spr_Background.setSize(270, 270);
				spr_Background.draw(game.batch);
				
				//npcs
				ShowNPCs();
				
				//UX
				spr_playerTag = gameControl.GetUX("playertag",cameraCoordsX, cameraCoordsY);
				spr_playerTag.draw(game.batch);
				
				spr_playerTagHair = gameControl.GetHairCharTag(player);
				spr_playerTagHair.draw(game.batch);
				
				
				font_master.getData().setScale(0.17f,0.28f);
				font_master.draw(game.batch, "Local:" + player.Map_A, cameraCoordsX - 98f, cameraCoordsY + 44.7f);
				font_master.draw(game.batch, "X:" + player.PosX_A, cameraCoordsX - 98f, cameraCoordsY + 53.7f);
				font_master.draw(game.batch, "Y:" + player.PosY_A, cameraCoordsX - 78f, cameraCoordsY + 53.7f);
				
				
				font_master.draw(game.batch, player.Name_A, cameraCoordsX - 88f, cameraCoordsY + 93.7f);
				font_master.draw(game.batch, String.valueOf(player.Hp_A + "/" + player.HpMax_A), cameraCoordsX - 85f, cameraCoordsY + 82f);
				font_master.draw(game.batch, String.valueOf(player.Mp_A + "/" + player.MpMax_A), cameraCoordsX - 85f, cameraCoordsY + 73.7f);
				font_master.draw(game.batch, String.valueOf(player.Level_A), cameraCoordsX - 88f, cameraCoordsY + 65.7f);
				font_master.draw(game.batch, String.valueOf(player.Exp_A) + "%", cameraCoordsX - 69f, cameraCoordsY + 65.7f);
				
				spr_master = gameControl.GetUX("innerpad", cameraCoordsX, cameraCoordsY);
				spr_master.setPosition(cameraCoordsX + padmoveX,cameraCoordsY + padmoveY);
				spr_master.draw(game.batch);
				
				
				//Char
				player = gameControl.SetCharMov(player, player.breakwalk_A);
				spr_playerhair = gameControl.GetHairChar(player);
				spr_playerhair.draw(game.batch);
				
				spr_playerfooter = gameControl.GetFooterChar(player);
				spr_playerfooter.draw(game.batch);
				
				spr_playerbottom = gameControl.GetBottomChar(player);
				spr_playerbottom.draw(game.batch);
				
				spr_playertop = gameControl.GetTopChar(player);
				spr_playertop.draw(game.batch);
				
				
				//Colision
				CheckColision();
				
				
				spr_testeDot.setPosition(cameraCoordsX + 19,cameraCoordsY -44);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);

				spr_testeDot.setPosition(cameraCoordsX + 19,cameraCoordsY -50);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);
				
				
				//if(coordsTouch.x > -59.5f && coordsTouch.x < -51.5f && coordsTouch.y > -65 && coordsTouch.y < -50f) {
				
				game.batch.end();
			}
			
			catch(Exception ex) {
				Gdx.app.exit();
			}
		}
		
		public void ShowNPCs() {
			//NPCs
			if(player.Map_A.equals("StreetsA")) {
				spr_npc = gameControl.GetNPC("DungeonMaster", 0);
				spr_npc.draw(game.batch);
			}
		}
		
		public void CheckColision() {
			if(player.Map_A.equals("StreetsA")) {
				if(player.PosY_A < -192.5f) {
					player.breakwalk_A = "front";
				}
				if(player.PosX_A > 184.5f) {
					player.breakwalk_A = "right";
				}
				if(player.PosX_A < -77) {
					player.breakwalk_A = "left";
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
		        		padmoveX = -85;
		        		player.playerInBattle_A = "no";
		        		return false;
		            }
		    		
		    		if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
		    			player.Side_A = "back";
		    			player.Walk_A = "walk";
		    			padmoveY = -65;
		    			player.playerInBattle_A = "no";
		    			return false;
		            }
		    		
		    		if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
		    			player.Side_A = "front";
		    			player.Walk_A = "walk";	
		    			padmoveY = -85;
		    			player.playerInBattle_A = "no";
		    			return false;
		            }
		    		
		    		if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
		    			player.Side_A = "right";
		    			player.Walk_A = "walk";
		    			padmoveX = -75;
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
			padmoveX = -80;
			padmoveY = -75;
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
			padmoveX = -80;
			padmoveY = -75;
			
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
     				if(coordsTouch.x >= cameraCoordsX -70 && coordsTouch.x <= cameraCoordsX -50 && coordsTouch.y > cameraCoordsY - 70 && coordsTouch.y < cameraCoordsY - 48) {
					player.Side_A = "right";
					player.Walk_A = "walk";	
					padmoveX = -75;
					player.playerInBattle_A = "no";
					return false;
				}
				//Left
     				if(coordsTouch.x >= cameraCoordsX -90 && coordsTouch.x <= cameraCoordsX -50 && coordsTouch.y > cameraCoordsY - 70 && coordsTouch.y < cameraCoordsY - 48) {
					player.Side_A = "left";
					player.Walk_A = "walk";	
					padmoveX = -85;	
					player.playerInBattle_A = "no";
					return false;
				}
				//Front
				if(coordsTouch.x > cameraCoordsX -80 && coordsTouch.x < cameraCoordsX -60 && coordsTouch.y > cameraCoordsY - 94 && coordsTouch.y < cameraCoordsY - 58) {
					player.Side_A = "front";
					player.Walk_A = "walk";	
					padmoveY = -85;			
					player.playerInBattle_A = "no";
					return false;
				}
				//Back
				if(coordsTouch.x > cameraCoordsX -80 && coordsTouch.x < cameraCoordsX -60 && coordsTouch.y > cameraCoordsY - 58 && coordsTouch.y < cameraCoordsY - 24) {
					player.Side_A = "back";
					player.Walk_A = "walk";	
					padmoveY = -65;
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
