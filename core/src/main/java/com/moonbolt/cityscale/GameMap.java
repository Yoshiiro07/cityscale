package com.moonbolt.cityscale;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class GameMap implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	
	//Objects
    private MainGame game;
    private ManagerScreen screen;
    private GameControl gameControl;
    private String state = "Main";
    private Sprite spr_master;
	private Random randnumber;
    
    //Player
    private float playerCoordsX = 0;
    private float playerCoordsY = 0;

	//Fonts
	private BitmapFont font_master;
	
    //Camera
    private OrthographicCamera camera;
    private Viewport viewport;
    private float cameraCoordsX = 0;
    private float cameraCoordsY = 0;
    
    //Sprites Background
    private Sprite spr_Background;
    private Texture tex_Background;
    
    //Teste Dot
    private Sprite spr_testeDot;
    private Texture tex_testeDot;
    
    //Controller
    private final IntSet downKeys = new IntSet(20);	

		public GameMap(MainGame _game, ManagerScreen _screen, GameControl _gameControl) {
			
			this.game = _game;	
			this.screen = _screen;
			this.randnumber = new Random();
			this.gameControl = _gameControl;
					
			//Camera and Inputs
			camera = new OrthographicCamera();
		    viewport = new StretchViewport(195,195,camera);
			viewport.apply();
			camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);			
			Gdx.input.setInputProcessor(this);
			
			//font
			font_master = new BitmapFont(Gdx.files.internal("data/assets/font/impact.fnt"),Gdx.files.internal("data/assets/font/impact.png"), false);
			font_master.setColor(Color.WHITE);
			font_master.getData().setScale(0.07f,0.11f);
			font_master.setUseIntegerPositions(false);
			
			//test dot
			tex_testeDot = new Texture(Gdx.files.internal("data/assets/etc/testdot.png"));
			spr_testeDot = new Sprite(tex_testeDot);			
		}
		
		@Override
		public void render(float delta) {
			
			Gdx.gl.glClearColor(1,1,1,1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			//cameraCoordsX = Float.parseFloat(player.PosX);
			//cameraCoordsY = Float.parseFloat(player.PosY);
			
            /*
			playerCoordsX = 0; //Float.parseFloat(player.PosX); 
			if(playerCoordsX <= 18.5f) 	{ cameraCoordsX = 18.5f; }
			if(playerCoordsX >= 93) 	{ cameraCoordsX = 93; 	 }

			playerCoordsY = 0; //Float.parseFloat(player.PosY);
			if(playerCoordsY >= -22f) { cameraCoordsY = -22f; }
			if(playerCoordsY <= -97) 	{ cameraCoordsY = -97;  }
			 */

			camera.position.set(cameraCoordsX -2,cameraCoordsY+1,0);
			camera.update();
		    game.batch.setProjectionMatrix(camera.combined);	    
			game.batch.begin();
			
			/*spr_testeDot.setPosition(cameraCoordsX - 61, cameraCoordsY - 12);
			spr_testeDot.setSize(1, 1);
			spr_testeDot.draw(game.batch);
		
			spr_testeDot.setPosition(cameraCoordsX - 47, cameraCoordsY - 32);  
			spr_testeDot.setSize(1, 1);
			spr_testeDot.draw(game.batch);*/
			
			game.batch.end();
		}

		@Override
		public void create() {
			// Initialization currently happens in constructor.
		}

		@Override
		public void render() {
			render(Gdx.graphics.getDeltaTime());
		}

		@Override
		public void resize(int width, int height) {
			viewport.update(width, height, true);
		}

		@Override
		public void pause() {
		}

		@Override
		public void resume() {
		}

		@Override
		public void show() {
		}

		@Override
		public void hide() {
		}

		@Override
		public void dispose() {
			if (tex_testeDot != null) {
				tex_testeDot.dispose();
			}
			if (font_master != null) {
				font_master.dispose();
			}
		}

		@Override
		public boolean keyDown(int keycode) {
			downKeys.add(keycode);
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			downKeys.remove(keycode);
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
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
		public boolean scrolled(float amountX, float amountY) {
			return false;
		}

		@Override
		public void input(String text) {
		}

		@Override
		public void canceled() {
		}		
}