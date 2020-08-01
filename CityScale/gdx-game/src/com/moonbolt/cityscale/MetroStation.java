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
    
    //teste
  	private Sprite spr_teste;
  	private Texture tex_teste;
	
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
		spr_playerCharacter.setPosition(playerPosX, playerPosY);
		spr_playerCharacter.draw(game.batch);
		
		spr_playerHair = gameControl.MovPlayerHair(activePlayer.hair_A,activePlayer.sex_A,state);
		spr_playerHair.draw(game.batch);
			
		//UI Elements
		spr_playerTag = gameControl.LoadInterface("playerTag");
		spr_playerTag.draw(game.batch);
		
		spr_playerHairTag = gameControl.LoadPlayerTagHair(activePlayer.hair_A);
		spr_playerHairTag.draw(game.batch);
		
		font_master.setColor(Color.WHITE);
		font_master.getData().setScale(0.07f,0.08f);
		font_master.setUseIntegerPositions(false);	
		font_master.draw(game.batch, activePlayer.name_A, 10.3f,97.5f);
		font_master.draw(game.batch, activePlayer.hp_A, 9f,94.5f);
		font_master.draw(game.batch, activePlayer.mp_A, 9f,90.5f);
		font_master.draw(game.batch, activePlayer.level_A, 9f,90.5f);
		font_master.draw(game.batch, activePlayer.exp_A, 18.8f,94.7f);
		font_master.draw(game.batch, "X:" + playerPosX, 1.5f,78.7f);
		font_master.draw(game.batch, "Y:" + playerPosY, 10.5f,78.7f);
		
		//Test
		font_master.draw(game.batch, "X:" + posTouchX,posTouchX, posTouchY);
		//font_master.draw(game.batch, "Y:" + posTouchY,posTouchX, posTouchY);
		//font_master.draw(game.batch, "Aqui",25.3f, 28.9f);
		
		
		CheckColide();
		
		//Change Screen
		if(changeScreen){		
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
