package com.moonbolt.cityscale;

import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

public class MetroStation implements Screen, ApplicationListener, InputProcessor, TextInputListener  {

	private MainGame game;
	private ManagerScreen screen;
	private GameControl gameControl;
	private OrthographicCamera camera;
    private Viewport viewport;
    private Player player;
    private String systemMsg = "";
    private BitmapFont font_master;
	
	private Sprite spr_master;
	
	private Sprite spr_background;
	private Texture tex_background;
	
	private Texture tex_testdot;
	private Sprite spr_testdot;
	
	public MetroStation(MainGame gameAlt,ManagerScreen screenAlt,GameControl gameControl, boolean network) {
		this.game = gameAlt;	
		this.screen = screenAlt;
		this.gameControl = gameControl;
		
		//test dot
		tex_testdot = new Texture(Gdx.files.internal("data/assets/misc/etc/testdot.png"));
		spr_testdot = new Sprite(tex_testdot);
		
		//background
		tex_background = new Texture(Gdx.files.internal("data/assets/maps/metrostation.png"));
		spr_background = new Sprite(tex_background);
		
		//Load Player Data
		player = gameControl.GetPlayer();
		
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
		
	}
	
	
	@Override
	public void render(float delta) {
		try 
		{
			Gdx.gl.glClearColor(1,1,1,1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			camera.update();
		    game.batch.setProjectionMatrix(camera.combined);
				
			game.batch.begin(); 
			
			spr_background.setPosition(0, 0);
			spr_background.setSize(140,140);
			spr_background.draw(game.batch);
			
			
			
			//Show Char
			spr_master = gameControl.LoadCharUp(player,)
			
			
			game.batch.end();
			
		}
		
		catch(Exception ex) {
			Gdx.app.exit();
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
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
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
	public boolean scrolled(float amountX, float amountY) {
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
	
}
