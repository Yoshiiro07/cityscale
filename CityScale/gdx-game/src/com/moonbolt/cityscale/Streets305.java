package com.moonbolt.cityscale;

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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Streets305 implements Screen, ApplicationListener, InputProcessor, TextInputListener {
		//Objects
		private MainGame game;
		private GameControl gameControl;
		private String[] config;
		private String platform;
		
		//Player
		private Player activePlayer;
		private int numPlayerActive;
		private int framePlayer = 1;
		private String state = "front";
		private String walk = "stop";
		private boolean movement;
		private float playerPosX;
		private float playerPosY;
		
		private float testeX;
		private float testeY;
		
		//Sprites
		private Sprite spr_Background;
		private Texture tex_Background;
		
		private Sprite spr_playerCharacter;
		private Sprite spr_playerHair;
		private Sprite spr_playerTag;
		private Sprite spr_playerHairTag;
		private Sprite spr_Minibar;
		private Sprite spr_Hotbar;
		private Sprite spr_BackController;
		private Sprite spr_Controller;
		
		private Sprite spr_Menubar;
		private Sprite spr_Status;
		private Sprite spr_Itens;
		private Sprite spr_Skills;
		private Sprite spr_Pet;
		private Sprite spr_Social;
		private Sprite spr_Config;
		
		
		//Primitives
		private float posTouchX = 0;
		private float posTouchY = 0;
		private boolean changeScreen = false;
		private String gameState = "Main";
		
		//fonts
		private BitmapFont font_master;
		
		//Camera
		private OrthographicCamera camera;
	    private Viewport viewport;
	    private float cameraCoordsX = 0;
	    private float cameraCoordsY = 0;
	    
	    //Teste Dot
	    private Sprite spr_testeDot;
	    private Texture tex_testeDot;
	    
	    //Controller
	    private final IntSet downKeys = new IntSet(20);
		
		public Streets305(MainGame gameAlt, GameControl gameControl,String[] configAlt,String platformAlt) {
			this.game = gameAlt;
			this.gameControl = gameControl;
			this.config = configAlt;
			this.platform = platformAlt;
			
			//Load Player Data
			numPlayerActive = gameControl.GetPlayerActiveNum();
			activePlayer = gameControl.SetActivePlayerData(numPlayerActive);
			playerPosX = Float.parseFloat(activePlayer.coordX_A);
			playerPosY = Float.parseFloat(activePlayer.coordY_A);
			
			//Camera and Inputs
			camera = new OrthographicCamera();
		    viewport = new StretchViewport(150,150,camera);
			viewport.apply();
			camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
			Gdx.input.setInputProcessor(this);
			
			//font
			font_master = new BitmapFont(Gdx.files.internal("data/font/impact.fnt"),Gdx.files.internal("data/font/impact.png"), false);
			font_master.setColor(Color.RED);
			font_master.getData().setScale(0.11f,0.23f);
			font_master.setUseIntegerPositions(false);	
			
			//Sprites
			tex_Background = new Texture(Gdx.files.internal("data/maps/streets305.png"));
			spr_Background = new Sprite(tex_Background);
			spr_Background.setSize(100, 100);
			
			
			tex_testeDot = new Texture(Gdx.files.internal("data/assets/testdot.png"));
			spr_testeDot = new Sprite(tex_testeDot);
			spr_Background.setSize(1, 1);
		}
		
		
		@Override
		public void render(float delta) {
			
			Gdx.gl.glClearColor(1,1,1,1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			//Regen Timer
			gameControl.RegenerateHPMP();
			//Save Data
			gameControl.UpdateDataSave(numPlayerActive);
			
			//Coords Player
			activePlayer = gameControl.GetPlayer();
			playerPosX = Float.parseFloat(activePlayer.coordX_A);
			playerPosY = Float.parseFloat(activePlayer.coordY_A);
		    
			//Camera Ajustments
			cameraCoordsX = playerPosX;
			cameraCoordsY = playerPosY;
			
			//Follow camera
			if(playerPosX <= -25f) { cameraCoordsX = -25; }
			if(playerPosX >= 175) { cameraCoordsX = 175; }
			if(playerPosY >= 91.5f) { cameraCoordsY = 91.5f; }
			if(playerPosY <= -105) { cameraCoordsY = -105; }
			
			camera.position.set(cameraCoordsX,cameraCoordsY + 30,0);
			camera.update();
		    game.batch.setProjectionMatrix(camera.combined);		    
			game.batch.begin();
					
			//Update Camera for UI elements
			gameControl.AtualizaCamera(cameraCoordsX, cameraCoordsY);
			
			//Background
			spr_Background.setPosition(-100, -150);
			spr_Background.setSize(350, 350);
			spr_Background.draw(game.batch);
			
			//Elements
			
			
			//Player Character
			spr_playerCharacter = gameControl.MovPlayerCharacter(activePlayer.set_A,activePlayer.sex_A,walk,state, false);
			spr_playerCharacter.setSize(22, 34);
			spr_playerCharacter.setPosition(playerPosX, playerPosY);
			spr_playerCharacter.draw(game.batch);
			
			spr_playerHair = gameControl.MovPlayerHair(activePlayer.hair_A,activePlayer.sex_A,state, "Main");
			spr_playerHair.draw(game.batch);
				
			//UI Elements
			spr_playerTag = gameControl.LoadInterfaceGamePlay("playerTag","","");
			spr_playerTag.draw(game.batch);
			
			spr_playerHairTag = gameControl.LoadInterfaceGamePlay("hairTag",activePlayer.hair_A,"");
			spr_playerHairTag.draw(game.batch);
			
			spr_Minibar = gameControl.LoadInterfaceGamePlay("minibar", "","");
			spr_Minibar.draw(game.batch);
			
			spr_Hotbar = gameControl.LoadInterfaceGamePlay("hotbar", "","");
			spr_Hotbar.draw(game.batch);
			
			spr_BackController = gameControl.LoadInterfaceGamePlay("outerpad", "","");
			spr_BackController.draw(game.batch);
			
			spr_Controller = gameControl.LoadInterfaceGamePlay("innerpad", walk,state);
			spr_Controller.draw(game.batch);
			
			font_master.setColor(Color.WHITE);
			font_master.getData().setScale(0.10f,0.12f);
			font_master.setUseIntegerPositions(false);	
			font_master.draw(game.batch, activePlayer.name_A, cameraCoordsX - 60.5f,cameraCoordsY + 101f);
			font_master.draw(game.batch, activePlayer.hp_A, cameraCoordsX - 63f,cameraCoordsY + 97f);
			font_master.draw(game.batch, activePlayer.mp_A, cameraCoordsX - 63f,cameraCoordsY + 91f);
			font_master.draw(game.batch, activePlayer.level_A, cameraCoordsX - 49f,cameraCoordsY + 97f);
			font_master.draw(game.batch, activePlayer.exp_A, cameraCoordsX - 50.5f,cameraCoordsY + 90f);
			font_master.draw(game.batch, "X:" + playerPosX, cameraCoordsX - 73f, cameraCoordsY + 72.7f);
			font_master.draw(game.batch, "Y:" + playerPosY, cameraCoordsX - 60f, cameraCoordsY + 72.7f);
			
			
			if(gameState.equals("Menu")) {
				spr_Menubar = gameControl.LoadInterfaceGamePlay("barMenu", "", "");
				spr_Menubar.draw(game.batch);
			}
			
			if(gameState.equals("Menu-Status")) {
				spr_Status = gameControl.LoadInterfaceGamePlay("menuStatus", "", "");
				spr_Status.draw(game.batch);
				
				spr_playerCharacter = gameControl.MovPlayerCharacter(activePlayer.set_A,activePlayer.sex_A,"stop","front", false);
				spr_playerCharacter.setSize(40, 60);
				spr_playerCharacter.setPosition(cameraCoordsX - 51, cameraCoordsY - 10);
				spr_playerCharacter.draw(game.batch);
				
				spr_playerHair = gameControl.MovPlayerHair(activePlayer.hair_A,activePlayer.sex_A,"stop", "Menu-Status");
				spr_playerHair.setSize(12,19);
				spr_playerHair.setPosition(cameraCoordsX - 37.8f, cameraCoordsY + 30.2f);
				spr_playerHair.draw(game.batch);
			}
			
			if(gameState.equals("Menu-Itens")) {
				spr_Status = gameControl.LoadInterfaceGamePlay("menuItens", "", "");
				spr_Status.draw(game.batch);
			}
			
			
			spr_testeDot.setPosition(cameraCoordsX + 65, cameraCoordsY + 61);
			spr_testeDot.draw(game.batch);
			
			spr_testeDot.setPosition(cameraCoordsX + 74, cameraCoordsY + 51);
			spr_testeDot.draw(game.batch);
			//font_master.draw(game.batch, "A",testeX, testeY);
			//font_master.draw(game.batch, "Y:" + posTouchY,posTouchX, posTouchY);
			//font_master.draw(game.batch, "Aqui",25.3f, 28.9f);
			
			
			game.batch.end();	
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
			
			if(gameState.equals("Main")) {		
				movement = true;
				downKeys.add(keycode);
		        if (downKeys.size >= 2){
		            onMultipleKeysDown(keycode);
		        }
		        if(downKeys.size == 1) {
		        	if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
		        		state = "left";
		        		walk = "walk";    		
		            }
		    		
		    		if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
		    			state = "back";
		    			walk = "walk";
		            }
		    		
		    		if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
		    			state = "front";
		    			walk = "walk";	
		            }
		    		
		    		if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
		    			state = "right";
		    			walk = "walk";	   			
		            } 		
		        }      
			}
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			downKeys.remove(keycode);
			movement = false;
			walk = "stop";
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int p1, int p2, int pointer, int button) {
			// TODO Auto-generated method stub
			
			Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));
			if(gameState.equals("Main")) {
				movement = true;
				
				//Menu button
				if(coordsTouch.x >= (cameraCoordsX - 75) && coordsTouch.x <= (cameraCoordsX - 66) && coordsTouch.y >= (cameraCoordsY + 75) && coordsTouch.y <= (cameraCoordsY + 83)) {
					gameState = "Menu";
					return false;
				}				
			}
			
			if(gameState.equals("Menu")) {
				movement = false;
				
				//Status Menu button
				if(coordsTouch.x >= (cameraCoordsX + 65) && coordsTouch.x <= (cameraCoordsX + 74) && coordsTouch.y >= (cameraCoordsY + 62) && coordsTouch.y <= (cameraCoordsY + 74)) {
					gameState = "Menu-Status";
					return false;
				}
				
				//Item Menu button
				if(coordsTouch.x >= (cameraCoordsX + 65) && coordsTouch.x <= (cameraCoordsX + 74) && coordsTouch.y >= (cameraCoordsY + 51) && coordsTouch.y <= (cameraCoordsY + 61)) {
					gameState = "Menu-Itens";
					return false;
				}
			}
			
			if(gameState.equals("Menu-Status")) {
				
			}
					
			posTouchX = coordsTouch.x;
			posTouchY = coordsTouch.y;
				
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			walk = "stop";
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			if(movement == true){
				Vector3 coordsTouch = camera.unproject(new Vector3(screenX,screenY,0));
					
				if(coordsTouch.x > (cameraCoordsX - 58) && coordsTouch.x < (cameraCoordsX - 39) && coordsTouch.y > (cameraCoordsY -37) && coordsTouch.y < (cameraCoordsY -13)) {
					state = "right";
					walk = "walk";		
				}
				
				if(coordsTouch.x > (cameraCoordsX - 77) && coordsTouch.x < (cameraCoordsX - 58) && coordsTouch.y > (cameraCoordsY -37) && coordsTouch.y < (cameraCoordsY -13)) {
					state = "left";
					walk = "walk";		
				}
				
				if(coordsTouch.x > (cameraCoordsX - 68) && coordsTouch.x < (cameraCoordsX - 49) && coordsTouch.y > (cameraCoordsY -45) && coordsTouch.y < (cameraCoordsY -26)) {
					state = "front";
					walk = "walk";
				}
				
				if(coordsTouch.x > (cameraCoordsX - 68) && coordsTouch.x < (cameraCoordsX - 49) && coordsTouch.y > (cameraCoordsY -26) && coordsTouch.y < (cameraCoordsY -7)) {
					state = "back";
					walk = "walk";
				}
			}
			return false;
		}
		
		private void onMultipleKeysDown (int mostRecentKeycode){
		    //For multiple key presses
		    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
		        	state = "left-front";
	        		walk = "walk";  	
		        }
		    }
		    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
		        	state = "left-back";
		        	walk = "walk";		
		        }
		    }
		    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
		    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
		    		state = "right-back";
		        	walk = "walk";		
		        }
		    }
		    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
		    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
		    		state = "right-front";
		        	walk = "walk";		
		        }
		    }
		    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
		        	state = "back-right";
		        	walk = "walk";		
		        }
		    }
		    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
		        	state = "back-left";
		        	walk = "walk";		
		        }
		    }
		    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
		        	state = "front-right";
		        	walk = "walk";		
		        }
		    }
		    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
		        	state = "front-left";
		        	walk = "walk";		
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
}
