package com.moonbolt.cityscale;

import java.io.UnsupportedEncodingException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class TitleScreen implements Screen, ApplicationListener, InputProcessor, TextInputListener {
		//Objects
	    private MainGame game;
	    private GameControl gameControl;
	    private ManagerScreen screen;
	    private String state = "Main";
	    private boolean network = false;
	    
	    //Manager
	    private boolean checkData = false;
	    private String systemMsg;
		private String account = "";
		private String accountwarning = "";
	    
		//Fonts
		private BitmapFont font_master;
		
	    //Camera
	    private OrthographicCamera camera;
	    private Viewport viewport;
	    private float cameraCoordsX = 0;
	    private float cameraCoordsY = 0;
	    
	    //Sprites
	    private Sprite spr_Background;
	    private Texture tex_Background;
	    
	    //Teste
	    private Texture tex_testeDot;
	    private Sprite spr_testeDot;
	    
	    //Sprites
	    private Sprite spr_master;
	    
	    //Textures
	    private TextureAtlas atlas_gameUI;
	    
	    //Controller
	    private final IntSet downKeys = new IntSet(20);	
		
		public TitleScreen(MainGame _gameAlt, ManagerScreen _screen, boolean _network) {
			this.game = _gameAlt;
			this.screen = _screen;
			this.network = _network;
			
			gameControl = new GameControl();
					
			//test dot
			tex_testeDot = new Texture(Gdx.files.internal("data/assets/misc/selected.png"));
			spr_testeDot = new Sprite(tex_testeDot);
			spr_master = new Sprite(tex_testeDot);
			
			//test dot
			tex_testeDot = new Texture(Gdx.files.internal("data/assets/misc/selected.png"));
			spr_testeDot = new Sprite(tex_testeDot);
			
			//Load Title
			tex_Background = new Texture(Gdx.files.internal("data/assets/maps/titlemap.png"));
			spr_Background = new Sprite(tex_Background);
					
			//Camera and Inputs
			camera = new OrthographicCamera();
		    viewport = new StretchViewport(135,135,camera);
			viewport.apply();
			camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
			Gdx.input.setInputProcessor(this);
	
			//font
			font_master = new BitmapFont(Gdx.files.internal("data/assets/font/impact.fnt"),Gdx.files.internal("data/assets/font/impact.png"), false);
			font_master.setColor(Color.WHITE);
			font_master.getData().setScale(0.07f,0.12f);
			font_master.setUseIntegerPositions(false);	
			
			//Atlas
			atlas_gameUI = new TextureAtlas(Gdx.files.internal("data/assets/UI/uirenew.txt"));
		}
			
		@Override
		public void render(float delta) {
			
				//Just for coloring
				Gdx.gl.glClearColor(1,1,1,1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
				//Update camera and start drawling
				camera.position.set(cameraCoordsX,cameraCoordsY,0);
				camera.update();
			    game.batch.setProjectionMatrix(camera.combined);	    
				game.batch.begin();
				
				//Background	
				spr_Background.setPosition(-70,-70);
				spr_Background.setSize(140, 140);
				spr_Background.draw(game.batch);
				
				
				if(state.equals("Main")) {
					//Menus
					spr_master = gameControl.GetInterface("mainmenu");
					spr_master.draw(game.batch);
				}
				
				if(state.equals("Change")) {
					this.screen.screenSwitch("CharacterSelect", network);
				}
				
				if(state.equals("Recover")) {
					//Menus
					spr_master = atlas_gameUI.createSprite("recuperar");
					spr_master.setPosition(-59, -59);
					spr_master.setSize(120,120);
					spr_master.draw(game.batch);
					
					font_master.setColor(Color.WHITE);
					font_master.getData().setScale(0.16f,0.22f);
					font_master.setUseIntegerPositions(false);	
					
					font_master.draw(game.batch, account, -50 , 38);
					font_master.draw(game.batch, accountwarning , -50 , 22);
				}
				font_master.setColor(Color.WHITE);
				font_master.getData().setScale(0.07f,0.12f);
				font_master.setUseIntegerPositions(false);	
						
				font_master.draw(game.batch, "Versao: 1-A" , -60 , -58);
						
				spr_testeDot.setPosition(26,-22);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);

				spr_testeDot.setPosition(57, -32);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);
					
				game.batch.end();		
		}
	
		@Override
		public void input(String input) {	
			if(state.equals("Recover")) {
				account = input;
			}		
		}

		@Override
		public void canceled() {}

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
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int p1, int p2, int pointer, int button) {
			
			Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));
			
			//[MainState]// 
			if(state.equals("Main")) {
				//Jogar Offline
				if(coordsTouch.x >= 26 && coordsTouch.x <= 57 && coordsTouch.y >= -32 && coordsTouch.y <= -22) {
					network = false;
					gameControl.CheckData();
					state = "Change";
					return false;
				}
								
				//Jogar Online
				if(coordsTouch.x >=  + 20 && coordsTouch.x <= +59 && coordsTouch.y >= -42 && coordsTouch.y <= -28) {
					network = true;
					gameControl.CheckData();
					state = "Change";
					return false;
				}
						
				//Recuperar Conta
				if(coordsTouch.x >= + 20 && coordsTouch.x <= 59 && coordsTouch.y >= -56 && coordsTouch.y <= -44) {
					state = "Recover";
					return false;
				}
			}
			
			if(state.equals("Recover")) {
				//Voltar
				if(coordsTouch.x >= 28 && coordsTouch.x <= 58 && coordsTouch.y >= -57 && coordsTouch.y <= -44) {
					state = "Main";
				}
				//input acc
				if(coordsTouch.x >= -57 && coordsTouch.x <= -2 && coordsTouch.y >= 25 && coordsTouch.y <= 45) {
					Gdx.input.getTextInput(this,"Digite o numero da conta:","","");
					return false;
				}
				//Recuperar botao
				if(coordsTouch.x >= -1 && coordsTouch.x <= 30 && coordsTouch.y >= 25 && coordsTouch.y <= 45) {
					/*try {
						GerenciamentoOnline("Download",conta);
					} catch (IOException e) {
						accountwarning = "Nao foi possível efetuar operacao";
					}*/
				}
				//delete acc
				if(coordsTouch.x >= -57 && coordsTouch.x <= -1 && coordsTouch.y >= -55 && coordsTouch.y <= -36) {
					gameControl.DeleteData();
					return false;
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