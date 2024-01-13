package com.moonbolt.cityscale;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadingScreen implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	
	private MainGame game;
	private ManagerScreen screen;
	private GameControl gameControl;
	
	private OrthographicCamera camera;
    private Viewport viewport;
    
    private Player player;
    private BitmapFont font_master;
    
    private Texture tex_background;
    private Sprite spr_background;
    
    int loadtime = 0;
    
    
	public LoadingScreen(MainGame game, GameControl gameControl, ManagerScreen screen) {
		this.screen = screen;
		this.game = game;
		this.gameControl = gameControl;
		
		camera = new OrthographicCamera();
		viewport = new StretchViewport(100,100,camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		Gdx.input.setInputProcessor(this);
		
		//font
		font_master = new BitmapFont(Gdx.files.internal("data/assets/font/impact.fnt"),Gdx.files.internal("data/assets/font/impact.png"), false);
		font_master.setColor(Color.WHITE);
		font_master.getData().setScale(0.13f,0.08f);
		font_master.setUseIntegerPositions(false);
		
		tex_background = new Texture(Gdx.files.internal("data/assets/misc/etc/blackscreen.png"));
		spr_background = new Sprite(tex_background);
	}
	
	@Override
	public void render(float delta) {


		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
			
		game.batch.begin();
			
		spr_background.draw(game.batch);
		spr_background.setPosition(0, 0);
		spr_background.setSize(200, 200);
		spr_background.draw(game.batch);
		
		font_master.setColor(Color.WHITE);
		font_master.getData().setScale(0.06f,0.08f);
		font_master.setUseIntegerPositions(false);
		
		font_master.draw(game.batch, "Carregando...", 78, 87.5f);
		
		loadtime++;
		if(loadtime >= 400) {
			
			if(player.map_A.equals("MetroStation")) {
				game.Switch("MetroStation");
			}
			else
			{
				game.Switch("GameMap");
			}		
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
