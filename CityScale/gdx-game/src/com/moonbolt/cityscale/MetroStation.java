package com.moonbolt.cityscale;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.Input.TextInputListener;

public class MetroStation implements Screen, ApplicationListener, InputProcessor, TextInputListener {

	//Objects
	private MainGame game;
	private GameControl gameControl;
	private String[] config;
	private String platform;
	private String networkState;
	
	//Player
	private Player activePlayer;
	private int numPlayerActive;
	private int framePlayer = 1;
	private String state = "front";
	private String walk = "stop";
	private boolean movement;
	private float playerPosX;
	private float playerPosY;
	
	//Sprites
	private Sprite spr_Background;
	private Texture tex_Background;
	
	private Sprite spr_metro;
	private Texture tex_metro;
	
	private Sprite spr_playerCharacter;
	private Sprite spr_playerHair;
	private Sprite spr_playerTag;
	private Sprite spr_playerHairTag;
	private Sprite spr_BackController;
	private Sprite spr_Controller;
	
	private float posTrainX = -60;
	private float posTrainY = 76;
	
	//Primitives
	private float posTouchX = 0;
	private float posTouchY = 0;
	private boolean changeScreen = false;
	
	//fonts
	private BitmapFont font_master;
	
	//Camera
	private OrthographicCamera camera;
    private Viewport viewport;
    private float cameraCoordsX = 0;
    private float cameraCoordsY = 0;
    
    //Controller
    private final IntSet downKeys = new IntSet(20);
    
  //Teste Dot
    private Sprite spr_testeDot;
    private Texture tex_testeDot;
	
	public MetroStation(MainGame gameAlt, GameControl gameControl,String[] configAlt,String platformAlt) {
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
	    viewport = new StretchViewport(100,100,camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		Gdx.input.setInputProcessor(this);
		
		//font
		font_master = new BitmapFont(Gdx.files.internal("data/font/impact.fnt"),Gdx.files.internal("data/font/impact.png"), false);
		font_master.setColor(Color.RED);
		font_master.getData().setScale(0.11f,0.23f);
		font_master.setUseIntegerPositions(false);	
		
		//Sprites
		tex_Background = new Texture(Gdx.files.internal("data/maps/metrostation.png"));
		spr_Background = new Sprite(tex_Background);
		spr_Background.setSize(100, 100);
		
		tex_metro = new Texture(Gdx.files.internal("data/assets/metro.png"));
		spr_metro = new Sprite(tex_metro);
		spr_metro.setPosition(posTrainX, posTrainY);
		spr_metro.setSize(70, 30);
		
		tex_testeDot = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		spr_testeDot = new Sprite(tex_testeDot);
		
	}
	
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);	
	    
		game.batch.begin();
		
		//Coords Player
		activePlayer = gameControl.GetPlayer();
		playerPosX = Float.parseFloat(activePlayer.coordX_A);
		playerPosY = Float.parseFloat(activePlayer.coordY_A);
		
		//Regen Timer
		gameControl.RegenerateHPMP();
		//Save Data
		gameControl.UpdateDataSave(numPlayerActive);
		
		
		//Background
		spr_Background.draw(game.batch);
		
		//Elements
		MoveTrain();
		spr_metro.setPosition(posTrainX, posTrainY);
		spr_metro.draw(game.batch);
				
		//Player Character
		spr_playerCharacter = gameControl.MovPlayerCharacter(activePlayer.set_A,activePlayer.sex_A,walk,state, false);
		spr_playerCharacter.setSize(22, 34);
		spr_playerCharacter.setPosition(playerPosX,playerPosY);
		spr_playerCharacter.draw(game.batch);
		
		spr_playerHair = gameControl.MovPlayerHair(activePlayer.hair_A,activePlayer.sex_A,state, "Main");
		spr_playerHair.draw(game.batch);
		
		//UI Elements
		spr_BackController = gameControl.LoadInterfaceGamePlay("outerpad", "", "");
		spr_BackController.setSize(12,20);
		spr_BackController.setPosition(10, 10);
		spr_BackController.draw(game.batch);
		
		spr_Controller = gameControl.LoadInterfaceGamePlay("innerpad", walk, state);
		spr_Controller.setSize(6,10);
		
		if(walk.equals("stop")) {
			spr_Controller.setPosition(13, 15);
		}
		if(walk.equals("walk") && state.equals("right")) {
			spr_Controller.setPosition(16, 15);
		}
		if(walk.equals("walk") && state.equals("left")) {
			spr_Controller.setPosition(10, 15);
		}
		if(walk.equals("walk") && state.equals("front")) {
			spr_Controller.setPosition(13, 10);
		}
		if(walk.equals("walk") && state.equals("back")) {
			spr_Controller.setPosition(13, 20);
		}
		if(walk.equals("walk") && state.equals("right-back")) {
			spr_Controller.setPosition(16, 20);
		}
		if(walk.equals("walk") && state.equals("left-back")) {
			spr_Controller.setPosition(10, 20);
		}
		if(walk.equals("walk") && state.equals("right-front")) {
			spr_Controller.setPosition(16, 10);
		}
		if(walk.equals("walk") && state.equals("left-front")) {
			spr_Controller.setPosition(10, 10);
		}		
		if(walk.equals("walk") && state.equals("front-right")) {
			spr_Controller.setPosition(16, 10);
		}
		if(walk.equals("walk") && state.equals("front-left")) {
			spr_Controller.setPosition(10, 10);
		}
		if(walk.equals("walk") && state.equals("back-right")) {
			spr_Controller.setPosition(16, 20);
		}
		if(walk.equals("walk") && state.equals("back-left")) {
			spr_Controller.setPosition(10, 20);
		}
		
		spr_Controller.draw(game.batch);
		
		
		
		//Test
		font_master.setColor(Color.WHITE);
		font_master.getData().setScale(0.10f,0.12f);
		font_master.setUseIntegerPositions(false);	
		font_master.draw(game.batch, "X:" + posTouchX,posTouchX, posTouchY);
		//font_master.draw(game.batch, "Y:" + posTouchY,posTouchX, posTouchY);
		//font_master.draw(game.batch, "Aqui",25.3f, 28.9f);
		
		spr_testeDot.setSize(1, 1);
		
		//Back
		spr_testeDot.setPosition(15.6f, 19);
		spr_testeDot.draw(game.batch);
		
		spr_testeDot.setPosition(18f, 29);
		spr_testeDot.draw(game.batch);
		
		spr_testeDot.setPosition(13f, 29);
		spr_testeDot.draw(game.batch);
		
		//Right
		spr_testeDot.setPosition(21, 23);
		spr_testeDot.draw(game.batch);
		
		spr_testeDot.setPosition(21, 15);
		spr_testeDot.draw(game.batch);
		
		spr_testeDot.setPosition(15.6f, 19);
		spr_testeDot.draw(game.batch);
		
		//Left
		spr_testeDot.setPosition(10, 23);
		spr_testeDot.draw(game.batch);
		
		spr_testeDot.setPosition(10, 15);
		spr_testeDot.draw(game.batch);
		
		spr_testeDot.setPosition(15.6f, 19);
		spr_testeDot.draw(game.batch);
		
		//Front
		spr_testeDot.setPosition(15.6f, 19);
		spr_testeDot.draw(game.batch);
		
		spr_testeDot.setPosition(18f, 9);
		spr_testeDot.draw(game.batch);
		
		spr_testeDot.setPosition(13f, 9);
		spr_testeDot.draw(game.batch);
		
		CheckColide();
		
		//Change Screen
		if(changeScreen){	
			
			gameControl.UpdateDataSave(numPlayerActive);
		    game.AtualizaElementos(game,gameControl, config, platform, networkState);
		    game.Switch("Streets305");			
		}
		
		game.batch.end();	
	}
	
	private void CheckColide() {
		if(playerPosX > 70 && playerPosY > -12.5f && playerPosY < 14) {
			changeScreen = true;
		}
	}
	
	private void MoveTrain() {
		if(posTrainX < 200) { posTrainX++; }
		if(posTrainX >= 200) { posTrainX = -70; }
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
		movement = true;	
		
		
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
				
			//Right
			if(coordsTouch.x > 15.6f && coordsTouch.x < 21 && coordsTouch.y > 15 && coordsTouch.y < 23) {
				state = "right";
				walk = "walk";	
				return false;
			}
			
			//Left
			if(coordsTouch.x > 10 && coordsTouch.x < 15.6f && coordsTouch.y > 15 && coordsTouch.y < 23) {
				state = "left";
				walk = "walk";	
				return false;
			}
			
			//Front
			if(coordsTouch.x > 13f && coordsTouch.x < 18f && coordsTouch.y > 9 && coordsTouch.y < 19) {
				state = "front";
				walk = "walk";
				return false;
			}
			
			//Back
			if(coordsTouch.x > 13f && coordsTouch.x < 18f && coordsTouch.y > 19 && coordsTouch.y < 29) {
				state = "back";
				walk = "walk";
				return false;
			}
			
			//right-back
			if(coordsTouch.x > 15.6f && coordsTouch.x < 21 && coordsTouch.y > 23 && coordsTouch.y < 29.6f) {
				state = "right-back";
				walk = "walk";		
				return false;
			}
			
			//left-back
			if(coordsTouch.x > 10 && coordsTouch.x < 15.6f && coordsTouch.y > 23 && coordsTouch.y < 29.6f) {
				state = "left-back";
				walk = "walk";	
				return false;
			}
			
			//left-front
			if(coordsTouch.x > 10.1f && coordsTouch.x < 13.5f && coordsTouch.y > 9.6f && coordsTouch.y < 15.4f) {
				state = "left-front";
				walk = "walk";		
				return false;
			}
			
			//right-front
			if(coordsTouch.x > 18.2f && coordsTouch.x < 21.3f && coordsTouch.y > 9.6f && coordsTouch.y < 15.4f) {
				state = "right-front";
				walk = "walk";		
				return false;
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
