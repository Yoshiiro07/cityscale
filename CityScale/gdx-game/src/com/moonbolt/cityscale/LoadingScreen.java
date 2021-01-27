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

public class LoadingScreen implements Screen, ApplicationListener, InputProcessor, TextInputListener {

	//Objects
	private MainGame game;
	private GameControl gameControl;
	private String[] config;
	private String platform;
	private String networkState;
	
	//Loading Variables
	private boolean loading = true;
	private int loadingTime = 0;
	private int loadingDownCurtain = 0;
	
	//Player
	private Player activePlayer;
	private int numPlayerActive;
	private int framePlayer = 1;
	private String state = "front";
	private String walk = "stop";
	private String breakWalk = "";
	private boolean movement;
	private float playerPosX;
	private float playerPosY;
	
	//Sprites
	private Sprite spr_Background;
	private Texture tex_Background;
	
	private Sprite spr_loadingText;
	private Sprite spr_loadingBlack;
	private Texture tex_loadingText;
	private Texture tex_loadingBlack;
	
	private Sprite spr_metro;
	private Texture tex_metro;
	
	private Sprite spr_playerCharacter;
	private Sprite spr_playerHair;
	private Sprite spr_playerTag;
	private Sprite spr_playerHairTag;
	private Sprite spr_BackController;
	private Sprite spr_Controller;
	private Sprite spr_skill;
	
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
	
	public LoadingScreen(MainGame gameAlt, GameControl gameControl,String[] configAlt,String platformAlt, String networkState) {
		this.game = gameAlt;
		this.gameControl = gameControl;
		this.config = configAlt;
		this.platform = platformAlt;
		this.networkState = networkState;
			
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
		tex_loadingText = new Texture(Gdx.files.internal("data/assets/carregando.png"));
		tex_loadingBlack = new Texture(Gdx.files.internal("data/assets/blackscreen.png"));
		
		spr_loadingText = new Sprite(tex_loadingText);
		spr_loadingText.setSize(100, 100);
		
		spr_loadingBlack = new Sprite(tex_loadingBlack);
		spr_loadingBlack.setSize(100, 100);
				
		tex_testeDot = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		spr_testeDot = new Sprite(tex_testeDot);
		
		spr_skill = new Sprite(tex_testeDot);
		
	}
	
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);	
	    
		game.batch.begin();
			
		//Change Screen
		if(loading) {
			loadingDownCurtain--;
			spr_loadingBlack.setSize(200, 200);
			spr_loadingBlack.setPosition(0, 0);
			spr_loadingBlack.draw(game.batch);
			spr_loadingText.setSize(25, 15);
			spr_loadingText.setPosition(75, 2);
			spr_loadingText.draw(game.batch);
			gameControl.OnlineManager("Desligar", "");
			
			if(loadingDownCurtain < 0) {
				loading = false;
				loadingDownCurtain = 0;
				changeScreen = true;
			}
		}
		
		if(changeScreen){	
		    game.Switch("Streets305");			
		}
		
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
		
		if(loading) { return false; }
		
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
		
		return false;
	}
	
	private void onMultipleKeysDown (int mostRecentKeycode){
	    
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
