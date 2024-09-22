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
import com.moonbolt.cityscale.interfaces.HttpCallback;

public class TitleScreen implements Screen, ApplicationListener, InputProcessor, TextInputListener {

	// Objects
	private MainGame game;
	private ManagerScreen screen;
	private GameControl gameControl;
	private String state = "main";
	private String accountnumber = "";
	private boolean aviso = false;
	private int avisoTimer = 0;
	private String avisoMsg = "";
	
	private Json json;
	private FileHandle file;

	// Manager
	private String systemMsg;
	private String conta = "";
	private String avisoconta = "";
	private int avisotimer = 0;

	// Fonts
	private BitmapFont font_master;

	// Camera
	private OrthographicCamera camera;
	private Viewport viewport;
	private float cameraCoordsX = 0;
	private float cameraCoordsY = 0;

	// Sprites
	private Sprite spr_Background;
	private Texture tex_Background;

	// Teste
	private Texture tex_testeDot;
	private Sprite spr_testeDot;

	// Logo
	private Texture tex_logo;
	private Sprite spr_logo;

	// Sprites
	private Sprite spr_mainmenu;
	private Sprite spr_recovermenu;
	private Sprite spr_loginmenu;
	private Sprite spr_master;

	// keyboard
	private Texture tex_keyboard;
	private Sprite spr_keyboard;
	private String keyboardText = "";

	// Textures
	private TextureAtlas atlas_gameUI;

	// Controller
	private final IntSet downKeys = new IntSet(20);

	public TitleScreen(MainGame gameAlt, ManagerScreen screen, int playernumAlt) {
		
		json = new Json();
		
		
		this.game = gameAlt;
		this.screen = screen;
		gameControl = new GameControl();

		// test dot
		tex_testeDot = new Texture(Gdx.files.internal("data/assets/etc/testdot.png"));
		spr_testeDot = new Sprite(tex_testeDot);
		spr_master = new Sprite(tex_testeDot);

		// keyboard
		tex_keyboard = new Texture(Gdx.files.internal("data/assets/ux/keyboard.png"));
		spr_keyboard = new Sprite(tex_keyboard);

		// Load Title
		tex_Background = new Texture(Gdx.files.internal("data/assets/maps/titlemap.png"));
		spr_Background = new Sprite(tex_Background);

		// Logo
		tex_logo = new Texture(Gdx.files.internal("data/assets/etc/maintitle.png"));
		spr_logo = new Sprite(tex_logo);

		// Camera and Inputs
		camera = new OrthographicCamera();
		viewport = new StretchViewport(135, 135, camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		Gdx.input.setInputProcessor(this);

		// font
		font_master = new BitmapFont(Gdx.files.internal("data/assets/font/impact.fnt"),
				Gdx.files.internal("data/assets/font/impact.png"), false);
		font_master.setColor(Color.WHITE);
		font_master.getData().setScale(0.07f, 0.12f);
		font_master.setUseIntegerPositions(false);

		// Atlas
		atlas_gameUI = new TextureAtlas(Gdx.files.internal("data/assets/ux/ux.txt"));
	}

	@Override
	public void render(float delta) {

		// Just for coloring
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Update camera and start drawling
		camera.position.set(cameraCoordsX, cameraCoordsY, 0);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();

		// Background
		spr_Background.setPosition(-70, -70);
		spr_Background.setSize(140, 140);
		spr_Background.draw(game.batch);

		spr_logo.setPosition(-65, 15);
		spr_logo.setSize(50, 50);
		spr_logo.draw(game.batch);

		if (state.equals("main")) {
			// Menus
			spr_mainmenu = atlas_gameUI.createSprite("main");
			spr_mainmenu.setPosition(15, -60);
			spr_mainmenu.setSize(50, 50);
			spr_mainmenu.draw(game.batch);

			font_master.setColor(Color.WHITE);
			font_master.getData().setScale(0.07f, 0.12f);
			font_master.setUseIntegerPositions(false);
			font_master.draw(game.batch, "Versao: 0.3", -60, -58);
			
			if (avisotimer > 0) {
				font_master.draw(game.batch, avisoconta, -20, -16);
				avisotimer--;
				if (avisotimer < 0) {
					avisotimer = 0;
					avisoconta = "";
					conta = "";
				}
			}
		}

		if (state.equals("Recover")) {
			// Menus
			spr_recovermenu = atlas_gameUI.createSprite("recover");
			spr_recovermenu.setPosition(-23, -26);
			spr_recovermenu.setSize(50, 50);
			spr_recovermenu.draw(game.batch);

			font_master.getData().setScale(0.15f, 0.20f);
			font_master.setUseIntegerPositions(false);
			font_master.draw(game.batch, conta, -17, 11);

			if (avisotimer > 0) {
				font_master.draw(game.batch, avisoconta, -20, -16);
				avisotimer--;
				if (avisotimer < 0) {
					avisotimer = 0;
					avisoconta = "";
					conta = "";
				}
			}
		}

		if (state.equals("Login")) {
			// Menus
			spr_loginmenu = atlas_gameUI.createSprite("login");
			spr_loginmenu.setPosition(-23, -26);
			spr_loginmenu.setSize(50, 50);
			spr_loginmenu.draw(game.batch);

			font_master.getData().setScale(0.15f, 0.20f);
			font_master.setUseIntegerPositions(false);
			font_master.draw(game.batch, keyboardText, -17, 11);

			if (avisotimer > 0) {
				font_master.draw(game.batch, avisoconta, -20, -16);
				avisotimer--;
				if (avisotimer < 0) {
					avisotimer = 0;
					avisoconta = "";
					conta = "";
				}
			}
		}

		if (state.equals("keyboard-login")) {
			spr_keyboard.setPosition(-70, -69);
			spr_keyboard.setSize(140, 130);
			spr_keyboard.draw(game.batch);

			font_master.getData().setScale(0.15f, 0.20f);
			font_master.setUseIntegerPositions(false);
			font_master.draw(game.batch, keyboardText, -65, 55);
		}

		if (state.equals("charselect")) {
			this.screen.screenSwitch("CharacterSelectScreen", accountnumber, 0);
			dispose();
		}
		
		if (aviso) {
			spr_master = gameControl.GetUX("textbar", 0, 0);
			spr_master.setSize(90,20);
			spr_master.setPosition(-45, 5);
			spr_master.draw(game.batch);
			font_master.getData().setScale(0.12f, 0.19f);
			font_master.draw(game.batch, avisoMsg, -40, 18);
			avisoTimer++;
			if (avisoTimer > 100) {
				aviso = false;
				avisoTimer = 0;
			}
		}

		// Teste
		// spr_testeDot.setPosition(19,-57);
		// spr_testeDot.setSize(1, 1);
		// spr_testeDot.draw(game.batch);

		// spr_testeDot.setPosition(60, -43);
		// spr_testeDot.setSize(1, 1);
		// spr_testeDot.draw(game.batch);

		game.batch.end();

	}

	@Override
	public void input(String text) {
		System.out.println("Input: " + text);
	}

	@Override
	public void canceled() {
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int p1, int p2, int pointer, int button) {

		Vector3 coordsTouch = camera.unproject(new Vector3(p1, p2, 0));

		// [MainState]//
		if (state.equals("main")) {

			// [New placement] //
			if (coordsTouch.x >= 19 && coordsTouch.x <= 60 && coordsTouch.y >= -31 && coordsTouch.y <= -18) {
				state = "Login";
				return false;
			}

			// [CreateNewAccount] //
			if (coordsTouch.x >= 19 && coordsTouch.x <= 60 && coordsTouch.y >= -57 && coordsTouch.y <= -43) {
				try {
					gameControl.CreateAccountOnline("NewAccount", new HttpCallback() {
                        @Override
                        public void onSuccess(String response) {
                        	if(response.contains("Recuperado")) {
                        		accountnumber = gameControl.GetAccount();
	                            state = "charselect";
	                            System.out.println("Passou");
                        	}
                        	else {
                        		avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
                        		aviso = true;
                        	}
                        }

                        @Override
                        public void onFailure(Throwable t) {
                           System.out.println("Error: " + t.getMessage());
                        }
                    });

                    return false;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		if (state.equals("Login")) {
			// [Input Login] //
			if (coordsTouch.x >= -20 && coordsTouch.x <= 22 && coordsTouch.y >= 0 && coordsTouch.y <= 14) {
				state = "keyboard-login";
				return false;
			}

			// [Acessar] //
			if (coordsTouch.x >= -20 && coordsTouch.x <= -1 && coordsTouch.y >= -14 && coordsTouch.y <= -2) {
				try {
				gameControl.CheckAccount("CheckAccount",keyboardText, new HttpCallback() {
					@Override
                    public void onSuccess(String response) {
						String result = gameControl.GetResult();
						if(result.equals("success")) {
							accountnumber = keyboardText;
	    					state = "charselect";
						}
						else 
						{
							avisoMsg = "Verifique a conta e tente novamente";
                    		aviso = true;
						}		        
                    }

                    @Override
                    public void onFailure(Throwable t) {
                       avisoconta = "Conta nao encontrada!";
   					   avisotimer = 200;
                       System.out.println("Error: " + t.getMessage());
                    }
				});
				
				return false;
			} catch (Exception e) {
				e.printStackTrace();
			}
			}

			// [Voltar] //
			if (coordsTouch.x >= 4 && coordsTouch.x <= 23 && coordsTouch.y >= -14 && coordsTouch.y <= -2) {
				state = "main";
				return false;
			}
		}

		if (state.contains("keyboard")) {
			this.KeyboardKeyPressed(coordsTouch.x, coordsTouch.y);
			return false;
		}

		return false;
	}

	public void KeyboardKeyPressed(float x, float y) {
		if (x >= -66 && x <= -56 && y >= 17 && y <= 37) {
			keyboardText = keyboardText + "1";
		}
		if (x >= -54 && x <= -44 && y >= 17 && y <= 37) {
			keyboardText = keyboardText + "2";
		}
		if (x >= -41 && x <= -31 && y >= 17 && y <= 37) {
			keyboardText = keyboardText + "3";
		}
		if (x >= -28 && x <= -18 && y >= 17 && y <= 37) {
			keyboardText = keyboardText + "4";
		}
		if (x >= -15 && x <= -6 && y >= 17 && y <= 37) {
			keyboardText = keyboardText + "5";
		}
		if (x >= -3 && x <= 7 && y >= 17 && y <= 37) {
			keyboardText = keyboardText + "6";
		}
		if (x >= 10 && x <= 19 && y >= 17 && y <= 37) {
			keyboardText = keyboardText + "7";
		}
		if (x >= 22 && x <= 32 && y >= 17 && y <= 37) {
			keyboardText = keyboardText + "8";
		}
		if (x >= 35 && x <= 45 && y >= 17 && y <= 37) {
			keyboardText = keyboardText + "9";
		}
		if (x >= 47 && x <= 57 && y >= 17 && y <= 37) {
			keyboardText = keyboardText + "0";
		}

		if (x >= -59 && x <= -48 && y >= -6 && y <= 12) {
			keyboardText = keyboardText + "Q";
		}
		if (x >= -46 && x <= -36 && y >= -6 && y <= 12) {
			keyboardText = keyboardText + "W";
		}
		if (x >= -34 && x <= -24 && y >= -6 && y <= 12) {
			keyboardText = keyboardText + "E";
		}
		if (x >= -21 && x <= -12 && y >= -6 && y <= 12) {
			keyboardText = keyboardText + "R";
		}
		if (x >= -9 && x <= 1 && y >= -6 && y <= 12) {
			keyboardText = keyboardText + "T";
		}
		if (x >= 3 && x <= 13 && y >= -6 && y <= 12) {
			keyboardText = keyboardText + "Y";
		}
		if (x >= 15 && x <= 25 && y >= -6 && y <= 12) {
			keyboardText = keyboardText + "U";
		}
		if (x >= 28 && x <= 37 && y >= -6 && y <= 12) {
			keyboardText = keyboardText + "I";
		}
		if (x >= 40 && x <= 50 && y >= -6 && y <= 12) {
			keyboardText = keyboardText + "O";
		}
		if (x >= 52 && x <= 62 && y >= -6 && y <= 12) {
			keyboardText = keyboardText + "P";
		}

		if (x >= -52 && x <= -42 && y >= -26 && y <= -9) {
			keyboardText = keyboardText + "A";
		}
		if (x >= -40 && x <= -30 && y >= -26 && y <= -9) {
			keyboardText = keyboardText + "S";
		}
		if (x >= -27 && x <= -18 && y >= -26 && y <= -9) {
			keyboardText = keyboardText + "D";
		}
		if (x >= -15 && x <= -6 && y >= -26 && y <= -9) {
			keyboardText = keyboardText + "F";
		}
		if (x >= -3 && x <= 7 && y >= -26 && y <= -9) {
			keyboardText = keyboardText + "G";
		}
		if (x >= 9 && x <= 19 && y >= -26 && y <= -9) {
			keyboardText = keyboardText + "H";
		}
		if (x >= 21 && x <= 31 && y >= -26 && y <= -9) {
			keyboardText = keyboardText + "J";
		}
		if (x >= 34 && x <= 43 && y >= -26 && y <= -9) {
			keyboardText = keyboardText + "K";
		}
		if (x >= 46 && x <= 56 && y >= -26 && y <= -9) {
			keyboardText = keyboardText + "L";
		}

		if (x >= -40 && x <= -30 && y >= -46 && y <= -29) {
			keyboardText = keyboardText + "Z";
		}
		if (x >= -27 && x <= -18 && y >= -46 && y <= -29) {
			keyboardText = keyboardText + "X";
		}
		if (x >= -15 && x <= -6 && y >= -46 && y <= -29) {
			keyboardText = keyboardText + "C";
		}
		if (x >= -3 && x <= 7 && y >= -46 && y <= -29) {
			keyboardText = keyboardText + "V";
		}
		if (x >= 9 && x <= 19 && y >= -46 && y <= -29) {
			keyboardText = keyboardText + "B";
		}
		if (x >= 21 && x <= 31 && y >= -46 && y <= -29) {
			keyboardText = keyboardText + "N";
		}
		if (x >= 34 && x <= 43 && y >= -46 && y <= -29) {
			keyboardText = keyboardText + "M";
		}

		if (x >= -40 && x <= 20 && y >= -66 && y <= -50) {
			keyboardText = keyboardText + " ";
		}
		if (x >= 23 && x <= 43 && y >= -66 && y <= -50) {
			if (!keyboardText.isEmpty()) {
				keyboardText = keyboardText.substring(0, keyboardText.length() - 1);
			}
		}

		if (x >= 50 && x <= 67 && y >= -66 && y <= -50) {
			state = "Login";
		}

	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	private void onMultipleKeysDown(int mostRecentKeycode) {
	}

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
	public void resize(int p1, int p2) {
		viewport.update(p1, p2);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
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
		// throw new UnsupportedOperationException("Unimplemented method
		// 'touchCancelled'");
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		//// TODO Auto-generated method stub
		return false;
		// throw new UnsupportedOperationException("Unimplemented method 'scrolled'");
	}
}
