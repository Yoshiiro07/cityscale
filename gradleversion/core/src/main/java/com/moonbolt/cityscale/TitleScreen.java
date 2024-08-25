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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class TitleScreen implements Screen, ApplicationListener, InputProcessor, TextInputListener {

		//Objects
	    private MainGame game;
	    private ManagerScreen screen;
	    private GameControl gameControl;
	    private String state = "main";
	    private boolean network = true;
	    private int playernum;

		//input
		private Vector2 coordsTouch = new Vector2();

	    //Manager
	    private String systemMsg;
		private String conta = "";
		private String avisoconta = "";
		private int avisotimer = 0;

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

	    //Logo
	    private Texture tex_logo;
	    private Sprite spr_logo;

	    //Sprites
	    private Sprite spr_mainmenu;
	    private Sprite spr_recovermenu;
	    private Sprite spr_loginmenu;
	    
	    //keyboard
	    private Texture tex_keyboard;
	    private Sprite spr_keyboard;
	    private String keyboardText = "";


	    //Textures
	    private TextureAtlas atlas_gameUI;

	    //Controller
	    private final IntSet downKeys = new IntSet(20);

		public TitleScreen(MainGame gameAlt, ManagerScreen screen, int playernumAlt) {
			this.game = gameAlt;
			this.screen = screen;
			this.playernum = playernumAlt;
			gameControl = new GameControl();

			//test dot
			tex_testeDot = new Texture(Gdx.files.internal("data/assets/etc/testdot.png"));
			spr_testeDot = new Sprite(tex_testeDot);
			
			//keyboard
			tex_keyboard = new Texture(Gdx.files.internal("data/assets/ux/keyboard.png"));
			spr_keyboard = new Sprite(tex_keyboard);

			//Load Title
			tex_Background = new Texture(Gdx.files.internal("data/assets/maps/titlemap.png"));
			spr_Background = new Sprite(tex_Background);

			//Logo
			tex_logo = new Texture(Gdx.files.internal("data/assets/etc/maintitle.png"));
			spr_logo = new Sprite(tex_logo);

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
			atlas_gameUI = new TextureAtlas(Gdx.files.internal("data/assets/ux/ux.txt"));
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

				spr_logo.setPosition(-65, 15);
				spr_logo.setSize(50,50);
				spr_logo.draw(game.batch);
				
				if(state.equals("charselect")) {
					this.screen.screenSwitch("CharacterSelectScreen", network,playernum);
					dispose();
				}

				if(state.equals("main")) {
					//Menus
					spr_mainmenu = atlas_gameUI.createSprite("main");
					spr_mainmenu.setPosition(15, -60);
					spr_mainmenu.setSize(50,50);
					spr_mainmenu.draw(game.batch);
					
					font_master.setColor(Color.WHITE);
					font_master.getData().setScale(0.07f,0.12f);
					font_master.setUseIntegerPositions(false);
					font_master.draw(game.batch, "Versao: 0.2b" , -60 , -58);
				}

				if(state.equals("Recover")){
					//Menus
					spr_recovermenu = atlas_gameUI.createSprite("recover");
					spr_recovermenu.setPosition(-23, -26);
					spr_recovermenu.setSize(50,50);
					spr_recovermenu.draw(game.batch);

					font_master.getData().setScale(0.15f,0.20f);
					font_master.setUseIntegerPositions(false);
					font_master.draw(game.batch, conta , -17 , 11);

					if(avisotimer > 0) {
						font_master.draw(game.batch, avisoconta , -20 , -16);
						avisotimer--;
						if(avisotimer < 0) { avisotimer = 0; avisoconta = ""; conta = ""; }
					}
				}
				
				if(state.equals("Login")){
					//Menus
					spr_loginmenu = atlas_gameUI.createSprite("login");
					spr_loginmenu.setPosition(-23, -26);
					spr_loginmenu.setSize(50,50);
					spr_loginmenu.draw(game.batch);

					font_master.getData().setScale(0.15f,0.20f);
					font_master.setUseIntegerPositions(false);
					font_master.draw(game.batch, conta , -17 , 11);

					if(avisotimer > 0) {
						font_master.draw(game.batch, avisoconta , -20 , -16);
						avisotimer--;
						if(avisotimer < 0) { avisotimer = 0; avisoconta = ""; conta = ""; }
					}
				}
				
				if(state.equals("keyboard-login")) {
					spr_keyboard.setPosition(-70, -69);
					spr_keyboard.setSize(140,130);
					spr_keyboard.draw(game.batch);
					
					font_master.getData().setScale(0.15f,0.20f);
					font_master.setUseIntegerPositions(false);
					font_master.draw(game.batch, keyboardText , -65 ,55);
				}
				
				
				
				//Teste
				spr_testeDot.setPosition(47,37);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);

				spr_testeDot.setPosition(57, 17);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);

				game.batch.end();

		}
		
		@Override
		public void input(String text) {
			System.out.println("Input: " + text);
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
			if(state.equals("main")) {
				//[OLD OFFLINE] //
				//Jogar Online
				/*if(coordsTouch.x >=  + 19 && coordsTouch.x <= 60 && coordsTouch.y >= -26 && coordsTouch.y <= -13) {
					network = true;
					gameControl.CheckData();
					state = "charselect";
					return false;
				}

				//Jogar Offline
				if(coordsTouch.x >=  + 19 && coordsTouch.x <= 60 && coordsTouch.y >= -42 && coordsTouch.y <= -29) {
					network = false;
					gameControl.CheckData();
					state = "charselect";
					return false;
				}

				//Recuperar Conta
				if(coordsTouch.x >=  + 19 && coordsTouch.x <= 60 && coordsTouch.y >= -56 && coordsTouch.y <= -44) {
					state = "Recover";
					return false;
				}*/
				
				//[New placement] //
				if(coordsTouch.x >=  19 && coordsTouch.x <= 60 && coordsTouch.y >= -31 && coordsTouch.y <= -18) {
					state = "Login";
					return false;
				}
			}
			
			if(state.equals("Login")) {
				//[Input Login] //
				if(coordsTouch.x >= -20 && coordsTouch.x <= 22 && coordsTouch.y >= 0 && coordsTouch.y <= 14) {	
					state = "keyboard-login";
					return false;
				}
			}
			

			if(state.equals("Recover")) {
				//Voltar
				if(coordsTouch.x >= 3 && coordsTouch.x <= 22 && coordsTouch.y >= -15 && coordsTouch.y <= -2) {
					state = "main";
				}
				//input acc
				if(coordsTouch.x >= -20 && coordsTouch.x <= 22 && coordsTouch.y >= 1 && coordsTouch.y <= 14) {
					Gdx.input.getTextInput(this,"Digite o numero da conta:","","");
					return false;
				}
				//Recuperar botao
				if(coordsTouch.x >= -20 && coordsTouch.x <= -2 && coordsTouch.y >= -15 && coordsTouch.y <= -2) {
					try {
						String retorno = gameControl.TipoOperacaoOnline("Download",conta);
						if (retorno.equals("Atualizado")) {
							avisotimer = 500;
							avisoconta = "Conta recuperada";
						} else {
							avisotimer = 0;
							avisoconta = "Sem Registro";
						}
					} catch (Exception e) {
						avisoconta = "Operacao falhou";
					}
				}
			}
			
			if(state.contains("keyboard")) {
				this.KeyboardKeyPressed(coordsTouch.x,coordsTouch.y);
				return false;
			}

			return false;
		}
		
		public void KeyboardKeyPressed(float x, float y) {
			if(x >= -66 && x <= -56 && y >= 17 && y <= 37) { keyboardText = keyboardText + "1";  }
			if(x >= -54 && x <= -44 && y >= 17 && y <= 37) { keyboardText = keyboardText + "2"; }
			if(x >= -41 && x <= -31 && y >= 17 && y <= 37) { keyboardText = keyboardText + "3";  }
			if(x >= -28 && x <= -18 && y >= 17 && y <= 37) { keyboardText = keyboardText + "4"; }
			if(x >= -15 && x <= -6 && y >= 17 && y <= 37) { keyboardText = keyboardText + "5";  }
			if(x >= -3 && x <= 7 && y >= 17 && y <= 37) { keyboardText = keyboardText + "6";  }
			if(x >= 10 && x <= 19 && y >= 17 && y <= 37) { keyboardText = keyboardText + "7";  }
			if(x >= 22 && x <= 32 && y >= 17 && y <= 37) { keyboardText = keyboardText + "8";  }
			if(x >= 35 && x <= 45 && y >= 17 && y <= 37) { keyboardText = keyboardText + "9";  }
			if(x >= 47 && x <= 57 && y >= 17 && y <= 37) { keyboardText = keyboardText + "0"; }
		}
		

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			return false;
		}

		private void onMultipleKeysDown (int mostRecentKeycode){}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

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

		@Override
		public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Unimplemented method 'touchCancelled'");
		}

		@Override
		public boolean scrolled(float amountX, float amountY) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Unimplemented method 'scrolled'");
		}
}
