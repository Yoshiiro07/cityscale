package com.moonbolt.cityscale;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TitleScreen implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	////MAINLY///
	private MainGame game;
	private GameControl gameControl;
	private String[] config;
	private String platform;
	private String networkState; 
	
	//Primitives
	private boolean changeScreen = false;
	private Sprite spr_titleBackground;
	private Texture tex_titleBackground;
	private Sprite spr_logo;
	private Texture tex_logo;
	private String state = "Main";
	private String ResultOnlineRequest = "";
	private String version = "V 0.1";
	
	private float posTouchX = 0;
	private float posTouchY = 0;
	
	//Camera
	private OrthographicCamera camera;
    private Viewport viewport;

	//Sprite 
	private Sprite spr_barAccess;
	private Sprite spr_barRecover;
	
	private Sprite spr_testeDot;
    private Texture tex_testeDot;
    
	//fonts
	private BitmapFont font_master;
	
	public TitleScreen(MainGame gameAlt,GameControl gameControl, String[] configAlt, String platformAlt){
		this.game = gameAlt;
		this.gameControl = gameControl;
		this.config = configAlt;
		this.platform = platformAlt;
		this.networkState = "no";
		
		//Camera and Inputs
		camera = new OrthographicCamera();
	    viewport = new StretchViewport(100,100,camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		Gdx.input.setInputProcessor(this);
		
		//Title
		tex_titleBackground = new Texture(Gdx.files.internal("data/assets/titlescreen.jpg"));
		spr_titleBackground = new Sprite(tex_titleBackground);
		spr_titleBackground.setSize(100, 100);
		
		//Logo
		tex_logo = new Texture(Gdx.files.internal("data/assets/maintitle.png"));
		spr_logo = new Sprite(tex_logo);
		spr_logo.setSize(100, 100);
		
		//font
		font_master = new BitmapFont(Gdx.files.internal("data/font/impact.fnt"),Gdx.files.internal("data/font/impact.png"), false);
		font_master.setColor(Color.RED);
		font_master.getData().setScale(0.13f,0.08f);
		font_master.setUseIntegerPositions(false);	
		
		//Sprites
		spr_barAccess = gameControl.LoadInterface("barAccess");
		
	    //test dot
	    tex_testeDot = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		spr_testeDot = new Sprite(tex_testeDot);
		
	}

	@Override
	public void render(float p1)
	{	
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		
		//Background
		spr_titleBackground.setPosition(0, -10);
		spr_titleBackground.setSize(100, 120);
		spr_titleBackground.draw(game.batch);
		
		spr_logo.setPosition(5, 55);
		spr_logo.setSize(30, 30);
		spr_logo.draw(game.batch);
		
		if(state.equals("Main")) {
			//Sprites
			spr_barAccess.draw(game.batch);
			
			font_master.setColor(Color.WHITE);
			font_master.getData().setScale(0.10f,0.15f);
			font_master.setUseIntegerPositions(false);	
			font_master.draw(game.batch, ResultOnlineRequest,10, 10);  //version
			
			font_master.draw(game.batch, version,10, 5);
			//font_master.draw(game.batch, "X:" + posTouchX,posTouchX, posTouchY);
			//font_master.draw(game.batch, "Y:" + posTouchY,posTouchX, posTouchY);
			
			//Change Screen
			if(changeScreen){		
			    game.AtualizaElementos(game,gameControl, config, platform, networkState);
			    game.Switch("CharacterSelect");			
			}
		}
		
		// Test Dot
		//spr_testeDot.setPosition(75.7f,17f);
		//spr_testeDot.draw(game.batch);

		//spr_testeDot.setPosition(87.7f,12);
		//spr_testeDot.draw(game.batch);

		
		game.batch.end();
	}

	@Override
	public void resize(int p1, int p2)
	{
		viewport.update(p1,p2);
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
	}	
	
	//TOUCH RESPONSE	
	@Override
	public boolean touchDown(int p1, int p2, int p3, int p4)
	{
		Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));
		
		//Start Button
		if(coordsTouch.x >= 75.7 && coordsTouch.x <= 87.7 && coordsTouch.y >= 18.8 && coordsTouch.y <= 24.8) {
			gameControl.CheckData();
			gameControl.LoadData();
			changeScreen = true; 			
		}
		
		//Recovery Button
		if(coordsTouch.x >= 75.7 && coordsTouch.x <= 87.7 && coordsTouch.y >= 12 && coordsTouch.y <= 17) {
			Gdx.input.getTextInput(this,"Digite seu ID","","");
		}
		
		posTouchX = coordsTouch.x;
		posTouchY = coordsTouch.y;
		
		return false;
	}
	
	@Override
	public void input(String input){
		gameControl.OnlineManager("Download", input);
		ResultOnlineRequest = gameControl.ResultOnlineRequest();
		if(ResultOnlineRequest.equals("Concluido")) {
			gameControl.LoadData();
			changeScreen = true; 	
		}
	}
	
	@Override
	public void canceled(){
	}
	
	@Override
	public void create()
	{
		// TODO: Implement this method
	}
	
	@Override
	public void render()
	{
		// TODO: Implement this method
	}
	
	@Override
	public void hide()
	{
		// TODO: Implement this method
	}

	@Override
	public void show()
	{
	}

	@Override
	public void resume()
	{
		// TODO: Implement this method
	}

	@Override
	public void pause()
	{
		// TODO: Implement this method
	}

	@Override
	public void dispose()
	{
		//gameControl = null;
		camera = null;
		viewport = null;
		game.dispose();
	}
	
	@Override
	public boolean touchUp(int p1, int p2, int p3, int p4)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean touchDragged(int p1, int p2, int p3)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean keyUp(int p1)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean keyDown(int p1)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean keyTyped(char p1)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean mouseMoved(int p1, int p2)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean scrolled(int p1)
	{
		// TODO: Implement this method
		return false;
	}
}
