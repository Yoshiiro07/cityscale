package com.moonbolt.cityscale;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Input.TextInputListener;

public class MetroStation implements Screen, ApplicationListener, InputProcessor, TextInputListener {

	private MainGame game;
	private GameControl gameControl;
	private String[] config;
	private String platform;
	private Sprite spr_master;   
	private Sprite spr_Background;
	private Texture tex_Background;
	
	private Sprite spr_metro;
	private Texture tex_metro;
	
	//fonts
	private BitmapFont font_master;
	
	//Camera
	private OrthographicCamera camera;
    private Viewport viewport;
	
	public MetroStation(MainGame gameAlt, GameControl gameControl,String[] configAlt,String platformAlt) {
		this.game = gameAlt;
		this.gameControl = gameControl;
		this.config = configAlt;
		this.platform = platformAlt;
		
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
	}
	
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);	
	    
		game.batch.begin();
			
		
		
		spr_Background.draw(game.batch);
				
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
	public boolean touchDown(int p1, int p2, int pointer, int button) {
		// TODO Auto-generated method stub
		
		Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));
		
		if(coordsTouch.x >= 50) {
			return false;
		}
		
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
