package com.moonbolt.cityscale;

import java.io.UnsupportedEncodingException;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
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

public class MainScreen implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	private MainGame game;
	private ManagerScreen screen;
	private OrthographicCamera camera;
    private Viewport viewport;
    private GameControl gameControl;
    private Player player;
    
    private String state = "Main";
    
    private Sprite spr_background;
	private Texture tex_background;
	
	private TextureAtlas atlas_ui;
	private Sprite spr_mainmenu;
	
	private Sprite spr_master;
	private Texture tex_master;
	
	private Sprite spr_testdot;
	private Texture tex_testdot;
	
	private BitmapFont font_master;
	
	public MainScreen(MainGame game, ManagerScreen screen){
		this.screen = screen;
		this.game = game;
		
		camera = new OrthographicCamera();
		viewport = new StretchViewport(100,100,camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		Gdx.input.setInputProcessor(this);
		
		gameControl = new GameControl();
		player = new Player();
				
		tex_background = new Texture(Gdx.files.internal("data/assets/maps/titlescreen.png"));
		spr_background = new Sprite(tex_background);

		tex_testdot = new Texture(Gdx.files.internal("data/assets/misc/etc/testdot.png"));
		spr_testdot = new Sprite(tex_testdot);
		
		//font
		font_master = new BitmapFont(Gdx.files.internal("data/assets/font/impact.fnt"),Gdx.files.internal("data/assets/font/impact.png"), false);
		font_master.setColor(Color.RED);
		font_master.getData().setScale(0.13f,0.08f);
		font_master.setUseIntegerPositions(false);
		
		//Atlas
		atlas_ui = new TextureAtlas(Gdx.files.internal("data/assets/ui/ui.txt"));
		spr_mainmenu = new Sprite(tex_testdot);
			
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float p1)
	{
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
			
		game.batch.begin();
		
		spr_background.setPosition(0,0);
		spr_background.setSize(100,100);
		spr_background.draw(game.batch);
		
		
		if(state.equals("Main")) {
			spr_mainmenu = atlas_ui.createSprite("mainmenu");
			spr_mainmenu.setPosition(62, 0);
			spr_mainmenu.setSize(40,42);
			spr_mainmenu.draw(game.batch);
		}
		
			
		spr_testdot.setPosition(93,28);
		spr_testdot.setSize(1,1);
		spr_testdot.draw(game.batch);
		
		spr_testdot.setPosition(69,37);
		spr_testdot.setSize(1,1);
		spr_testdot.draw(game.batch);
			
		game.batch.end();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int p1, int p2, int p3, int p4)
	{
		// TODO: Implement this method
		Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));

		if(state.equals("Main")) {
			if(coordsTouch.x >= 69 && coordsTouch.x <= 93 && coordsTouch.y >= 28 && coordsTouch.y <= 37){
				//Offline Mode
				gameControl.CheckData();
				game.Switch("CharacterSelect");
			}
		}
			
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public void create() {

	}

	@Override
	public void resize(int p1, int p2)
	{
		viewport.update(p1,p2);
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void render() {

	}


	@Override
	public void input(String text) {

	}

	@Override
	public void canceled() {

	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
