package com.moonbolt.cityscale;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TitleScreen implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	private MainGame game;
	private ManagerScreen screen;
	private OrthographicCamera camera;
    private Viewport viewport;
    private GameControl gameControl; 
    private BitmapFont font_master;
    private String state = "Splash";
    
    //Splash Variables
    private boolean interpolation;
	private int countEffect;
	private int fadeInCount;
	private int fadeOutCount;
    
    private Sprite spr_background;
    private Texture tex_background;
    
    private Sprite spr_logoBackground;
    private Texture tex_logoBackground;
    
    private Sprite spr_logo;
    private Texture tex_logo;
	
	public TitleScreen(MainGame game, ManagerScreen screen) {
		this.screen = screen;
		this.game = game;
		
		fadeInCount = 1;
		fadeOutCount = 1;
		interpolation = false;
		countEffect = 0;
		
		camera = new OrthographicCamera();
		viewport = new StretchViewport(100,100,camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		Gdx.input.setInputProcessor(this);
		
		tex_background = new Texture(Gdx.files.internal("data/assets/maps/subway.png"));
		spr_background = new Sprite(tex_background);
		
		tex_logoBackground = new Texture(Gdx.files.internal("data/assets/misc/whitebg.png"));
		spr_logoBackground = new Sprite(tex_logoBackground);
		
		tex_logo = new Texture(Gdx.files.internal("data/assets/misc/logo.png"));
		spr_logo = new Sprite(tex_logo);
		
	}
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
			
		game.batch.begin();
		
		if(state.equals("Splash"))
		{
			spr_logoBackground.setPosition(0,0);
			spr_logoBackground.setSize(100,100);
			spr_logoBackground.draw(game.batch);
			
			spr_logo.setPosition(30,29);
			spr_logo.setSize(40,44);
			spr_logo.draw(game.batch);
			
			if(countEffect < 250 && interpolation == false){
				spr_logo.setAlpha(fadeInCount);
				fadeInCount -= 1.5f;
				countEffect += 1.5f;
			}
			if(countEffect >= 250 && interpolation == false){
				interpolation = true;
				countEffect = 0;
			}
			
			if(countEffect < 250 && interpolation == true){
				spr_logo.setAlpha(fadeOutCount);
				fadeOutCount += 1.5f;
			    countEffect += 1.5f;
			}
			
			if(countEffect >= 250 && interpolation == true){
				state = "Title";
			}
		}
		
		if(state.equals("Title")) 
		{
			spr_background.setPosition(0,0);
			spr_background.setSize(100,100);
			spr_background.draw(game.batch);
		}
		
		//spr_testdot.setPosition(67,44);
		//spr_testdot.setSize(1,1);
		//spr_testdot.draw(game.batch);
			
		game.batch.end();	
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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

}
