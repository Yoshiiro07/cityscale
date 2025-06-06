package com.moonbolt.cityscale;

import java.util.ArrayList;

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
import com.moonbolt.cityscale.interfaces.HttpCallback;
import com.moonbolt.cityscale.models.Player;

public class CharacterSelect implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	// Objects
	private MainGame game;
	private GameControl gameControl;
	private ManagerScreen screen;
	private String state = "Account";
	private boolean aviso = false;
	private int avisoTimer = 0;
	private String avisoMsg = "";
	private boolean network = false;
	private String systemMsg = "";
	private String showMsg = "";
	private String account = "";
	private int playernumber = 0;
	private boolean loadAccountState = false;
	private boolean accountInProcess = false;

	// Fonts
	private BitmapFont font_master;

	// Camera
	private OrthographicCamera camera;
	private Viewport viewport;
	private float cameraCoordsX = 0;
	private float cameraCoordsY = 0;

	// Player
	private Player player;
	private ArrayList<Player> lstPlayer;
	private String name = "";
	private String sex = "M";
	private String hair = "hair1";
	private String color = "yellow";
	private String setUpper = "basic";
	private String setBottom = "basic";
	private String setFooter = "basic";
	private int selectedchar = 0;

	// Sprites
	private Sprite spr_Background;
	private Texture tex_Background;
	private float posTrainY = -70;

	// Teste
	private Texture tex_testeDot;
	private Sprite spr_testeDot;

	// Sprites
	private Sprite spr_master;
	private Sprite spr_player;
	private Sprite spr_hair;
	private Sprite spr_hat;

	// keyboard
	private Texture tex_keyboard;
	private Sprite spr_keyboard;
	private String keyboardText = "";

	// Textures
	private TextureAtlas atlas_gameUI;

	// Controller
	private final IntSet downKeys = new IntSet(20);

	public CharacterSelect(MainGame _game, ManagerScreen _screen, String account, int _playernumber) {
		this.game = _game;
		this.screen = _screen;
		this.account = account;
		this.playernumber = _playernumber;
		this.gameControl = new GameControl();

		// Load Player
		lstPlayer = new ArrayList<Player>();

		// test dot
		tex_testeDot = new Texture(Gdx.files.internal("data/assets/etc/testdot.png"));
		spr_testeDot = new Sprite(tex_testeDot);
		spr_master = new Sprite(tex_testeDot);

		spr_hair = new Sprite(tex_testeDot);
		spr_hat = new Sprite(tex_testeDot);

		// Load Title
		tex_Background = new Texture(Gdx.files.internal("data/assets/maps/characterselect.png"));
		spr_Background = new Sprite(tex_Background);

		// keyboard
		tex_keyboard = new Texture(Gdx.files.internal("data/assets/ux/keyboard.png"));
		spr_keyboard = new Sprite(tex_keyboard);

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
	}

	@Override
	public void render(float delta) {

		// Load Account////////////////////////////////////////////
		if (!loadAccountState && !accountInProcess) {
			try {
				accountInProcess = true;
				gameControl.GetAccountOnline("LoadData", this.account,"", new HttpCallback() {
					@Override
					public void onSuccess(String response) {
						if (response.equals("success")) {
							//Recupera lista da conta 
							lstPlayer = gameControl.GetPlayers();
							//Reseta processamento de conta
							loadAccountState = true;
							accountInProcess = false;
						}
						if (response.equals("fail")) {
							screen.screenSwitch("TitleScreen", account, playernumber);
						}
					}

					@Override
					public void onFailure(Throwable t) 
					{screen.screenSwitch("TitleScreen", "", playernumber);}
				});
			} catch (Exception ex) {
				this.screen.screenSwitch("TitleScreen", "", 0);
			}
		}
		// Load Account////////////////////////////////////////////

		if (loadAccountState) {
			// Just for coloring
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			// Update camera and start drawling
			camera.position.set(cameraCoordsX, cameraCoordsY, 0);
			camera.update();
			game.batch.setProjectionMatrix(camera.combined);
			game.batch.begin();

			// Background
			spr_Background.setPosition(-70, posTrainY);
			spr_Background.setSize(140, 140);
			spr_Background.draw(game.batch);

			posTrainY = posTrainY + 0.02f;
			if (posTrainY > -68f) {
				posTrainY = -70;
			}

			if (state.equals("Account")) {
				spr_master = gameControl.GetUX("textbar", 0, 0);
				spr_master.setPosition(-42, 0);
				spr_master.setSize(90, 30);
				spr_master.draw(game.batch);

				font_master.getData().setScale(0.10f, 0.16f);
				font_master.setUseIntegerPositions(false);
				font_master.draw(game.batch, "ID da conta, guarde para recupera-la posteriormente", -40, 26);

				font_master.getData().setScale(0.25f, 0.35f);
				font_master.draw(game.batch, lstPlayer.get(0).AccountNumber, -12, 18);

				font_master.getData().setScale(0.10f, 0.16f);
				font_master.draw(game.batch, "Toque/Click para continuar", 10, 7);
			}

			if (state.equals("Main")) {

				spr_master = gameControl.GetUX("bannerselect", 0, 0);
				spr_master.draw(game.batch);

				spr_master = gameControl.GetUX("btncreatenew", 0, 0);
				spr_master.draw(game.batch);

				spr_master = gameControl.GetUX("btnexclude", 0, 0);
				spr_master.draw(game.batch);

				if (!lstPlayer.get(0).Name.equals("none")) {
					spr_master = gameControl.CharSelect(lstPlayer.get(0), "bottom", 1);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharSelect(lstPlayer.get(0), "upper", 1);
					spr_master.draw(game.batch);
					
					spr_master = gameControl.CharSelect(lstPlayer.get(0), "footer", 1);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharHairSelect(lstPlayer.get(0).Sex, lstPlayer.get(0).Hair,
							lstPlayer.get(0).Color, 1);
					spr_master.draw(game.batch);
				}
				
				if (!lstPlayer.get(1).Name.equals("none")) {
					spr_master = gameControl.CharSelect(lstPlayer.get(1), "footer", 2);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharSelect(lstPlayer.get(1), "bottom", 2);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharSelect(lstPlayer.get(1), "upper", 2);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharHairSelect(lstPlayer.get(1).Sex, lstPlayer.get(1).Hair,
							lstPlayer.get(1).Color, 2);
					spr_master.draw(game.batch);
				}
				
				if (!lstPlayer.get(2).Name.equals("none")) {
					spr_master = gameControl.CharSelect(lstPlayer.get(2), "footer", 3);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharSelect(lstPlayer.get(2), "bottom", 3);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharSelect(lstPlayer.get(2), "upper", 3);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharHairSelect(lstPlayer.get(2).Sex, lstPlayer.get(1).Hair,
							lstPlayer.get(2).Color, 3);
					spr_master.draw(game.batch);
				}
			}

			if (state.equals("Create")) {
				spr_master = gameControl.GetUX("create", 0, 0);
				spr_master.draw(game.batch);
		
				spr_master = gameControl.CharacterCreate(sex, "bottom");
				spr_master.draw(game.batch);

				spr_master = gameControl.CharacterCreate(sex, "upper");
				spr_master.draw(game.batch);
				
				spr_master = gameControl.CharacterCreate(sex, "footer");
				spr_master.draw(game.batch);


				spr_master = gameControl.HairCharacterCreate(sex, hair, color, 99);
				spr_master.draw(game.batch);

				spr_master = gameControl.AllHairs(1, sex, color);
				spr_master.draw(game.batch);
				spr_master = gameControl.AllHairs(2, sex, color);
				spr_master.draw(game.batch);
				spr_master = gameControl.AllHairs(3, sex, color);
				spr_master.draw(game.batch);
				spr_master = gameControl.AllHairs(4, sex, color);
				spr_master.draw(game.batch);

				font_master.setColor(Color.WHITE);
				font_master.getData().setScale(0.12f, 0.19f);
				font_master.setUseIntegerPositions(false);
				font_master.draw(game.batch, name, -13, 37);
			}

			if (state.equals("Delete")) {
				spr_master = gameControl.GetUX("bannerdelete", 0, 0);
				spr_master.draw(game.batch);

				spr_master = gameControl.GetUX("btnvoltar", 0, 0);
				spr_master.draw(game.batch);

				if (!lstPlayer.get(0).Name.equals("none")) {
					spr_master = gameControl.CharSelect(lstPlayer.get(0), "bottom", 1);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharSelect(lstPlayer.get(0), "upper", 1);
					spr_master.draw(game.batch);
					
					spr_master = gameControl.CharSelect(lstPlayer.get(0), "footer", 1);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharHairSelect(lstPlayer.get(0).Sex, lstPlayer.get(0).Hair,
							lstPlayer.get(0).Color, 1);
					spr_master.draw(game.batch);
				}
				if (!lstPlayer.get(1).Name.equals("none")) {
					spr_master = gameControl.CharSelect(lstPlayer.get(1), "footer", 2);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharSelect(lstPlayer.get(1), "bottom", 2);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharSelect(lstPlayer.get(1), "upper", 2);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharHairSelect(lstPlayer.get(1).Sex, lstPlayer.get(1).Hair,
							lstPlayer.get(1).Color, 2);
					spr_master.draw(game.batch);
				}
				if (!lstPlayer.get(2).Name.equals("none")) {
					spr_master = gameControl.CharSelect(lstPlayer.get(2), "footer", 3);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharSelect(lstPlayer.get(2), "bottom", 3);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharSelect(lstPlayer.get(2), "upper", 3);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharHairSelect(lstPlayer.get(2).Sex, lstPlayer.get(1).Hair,
							lstPlayer.get(2).Color, 3);
					spr_master.draw(game.batch);
				}
			}

			if (state.equals("Selected")) {

				spr_master = gameControl.GetUX("confirmtab", 0, 0);
				spr_master.draw(game.batch);

				spr_master = gameControl.GetUX("btnvoltar", 0, 0);
				spr_master.draw(game.batch);

				font_master.setColor(Color.WHITE);
				font_master.getData().setScale(0.12f, 0.19f);
				font_master.setUseIntegerPositions(false);

				if (!lstPlayer.get(0).Name.equals("none") && selectedchar == 1) {
					
					spr_master = gameControl.CharSelect(lstPlayer.get(0), "bottom", 1);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharSelect(lstPlayer.get(0), "upper", 1);
					spr_master.draw(game.batch);
					
					spr_master = gameControl.CharSelect(lstPlayer.get(0), "footer", 1);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharHairSelect(lstPlayer.get(0).Sex, lstPlayer.get(0).Hair,
							lstPlayer.get(0).Color, 1);
					spr_master.draw(game.batch);

					font_master.draw(game.batch, lstPlayer.get(0).Name, 25, 48);
					font_master.draw(game.batch, lstPlayer.get(0).Level, 25, 42);
					font_master.draw(game.batch, lstPlayer.get(0).Map, 25, 35);
				}
				if (!lstPlayer.get(1).Name.equals("none")  && selectedchar == 2) {
					spr_master = gameControl.CharSelect(lstPlayer.get(1), "footer", 2);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharSelect(lstPlayer.get(1), "bottom", 2);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharSelect(lstPlayer.get(1), "upper", 2);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharHairSelect(lstPlayer.get(1).Sex, lstPlayer.get(1).Hair,
							lstPlayer.get(1).Color, 2);
					spr_master.draw(game.batch);

					font_master.draw(game.batch, lstPlayer.get(1).Name, 25, 48);
					font_master.draw(game.batch, lstPlayer.get(1).Level, 25, 42);
					font_master.draw(game.batch, lstPlayer.get(1).Map, 25, 35);
				}
				if (!lstPlayer.get(2).Name.equals("none")  && selectedchar == 3) {
					spr_master = gameControl.CharSelect(lstPlayer.get(2), "footer", 3);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharSelect(lstPlayer.get(2), "bottom", 3);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharSelect(lstPlayer.get(2), "upper", 3);
					spr_master.draw(game.batch);

					spr_master = gameControl.CharHairSelect(lstPlayer.get(2).Sex, lstPlayer.get(1).Hair,
							lstPlayer.get(2).Color, 3);
					spr_master.draw(game.batch);

					font_master.draw(game.batch, lstPlayer.get(2).Name, 25, 48);
					font_master.draw(game.batch, lstPlayer.get(2).Level, 25, 42);
					font_master.draw(game.batch, lstPlayer.get(2).Map, 25, 35);
				}
			}

			if (state.equals("Change")) {
				gameControl.SetSelectedChar(selectedchar);
				gameControl.SetPlayers(lstPlayer);
				this.screen.atualizaComponentes(game, gameControl, selectedchar);
				this.screen.screenSwitch("LoadingScreen", account, selectedchar);
				dispose();
			}

			if (state.equals("keyboard")) {
				spr_keyboard.setPosition(-70, -69);
				spr_keyboard.setSize(140, 130);
				spr_keyboard.draw(game.batch);

				font_master.getData().setScale(0.15f, 0.20f);
				font_master.setUseIntegerPositions(false);
				font_master.draw(game.batch, keyboardText, -65, 55);
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
			

			// spr_testeDot.setPosition(9,-10);
			// spr_testeDot.setSize(1, 1);
			// spr_testeDot.draw(game.batch);

			// spr_testeDot.setPosition(1,5);
			// spr_testeDot.setSize(1, 1);
			// spr_testeDot.draw(game.batch);

			game.batch.end();

		}

	}

	private boolean CheckName(String nameinput) {
		if (nameinput.equals("none")) {
			showMsg = "Insira um nome";
			return false;
		}
		if (nameinput.equals("")) {
			showMsg = "Insira um nome";
			return false;
		}
		if (nameinput.contains(".")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains("-")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains(";")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains("'")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains("~")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains(":")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains("?")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains("!")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains("-")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains("*")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains("=")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains("@")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains("#")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains("$")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains("%")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains("&")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains("(")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains(")")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains("=")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains("/")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains("\\")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains("<")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.contains(">")) {
			showMsg = "Nome com caracters especiais";
			return false;
		}
		if (nameinput.length() > 10) {
			showMsg = "Ate 10 letras";
			return false;
		}
		if (nameinput.length() < 3) {
			showMsg = "Menos de 3 letras";
			return false;
		}

		return true;
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
			name = keyboardText;
			state = "Create";
		}

	}

	@Override
	public void input(String input) {
		if (state.equals("Create")) {
			if (CheckName(input)) {
				name = input;
			}
		}
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
		return false;
	}

	@Override
	public boolean touchDown(int p1, int p2, int pointer, int button) {

		Vector3 coordsTouch = camera.unproject(new Vector3(p1, p2, 0));

		// [Account]//
		if (state.equals("Account")) {
			if (coordsTouch.x >= -200 && coordsTouch.x <= 200 && coordsTouch.y >= -200 && coordsTouch.y <= 200) {
				state = "Main";
				return false;
			}
		}

		// [MainState]//
		if (state.equals("Main")) {
			// Create
			if (coordsTouch.x >= -60 && coordsTouch.x <= -40 && coordsTouch.y >= -61 && coordsTouch.y <= -50) {

				if (lstPlayer.get(0).Name.equals("none")) {
					selectedchar = 1;
					state = "Create";
					return false;
				}

				if (lstPlayer.get(1).Name.equals("none")) {
					selectedchar = 2;
					state = "Create";
					return false;
				}

				if (lstPlayer.get(2).Name.equals("none")) {
					selectedchar = 3;
					state = "Create";
					return false;
				}

				return false;
			}

			// Delete
			if (coordsTouch.x >= 40 && coordsTouch.x <= 60 && coordsTouch.y >= -61 && coordsTouch.y <= -50) {
				state = "Delete";
				return false;
			}
			// select char 1
			if (coordsTouch.x >= -52 && coordsTouch.x <= -28 && coordsTouch.y >= -42 && coordsTouch.y <= 20) {
				if (!lstPlayer.get(0).Name.equals("none")) {
					selectedchar = 1;
					state = "Selected";
				}
				return false;
			}
			// select char 2
			if (coordsTouch.x >= -15 && coordsTouch.x <= 15 && coordsTouch.y >= -42 && coordsTouch.y <= 20) {
				if (!lstPlayer.get(1).Name.equals("none")) {
					selectedchar = 2;
					state = "Selected";
				}
				return false;
			}
			// select char 3
			if (coordsTouch.x >= 22 && coordsTouch.x <= 44 && coordsTouch.y >= -42 && coordsTouch.y <= 20) {
				if (!lstPlayer.get(2).Name.equals("none")) {
					selectedchar = 3;
					state = "Selected";
				}
				return false;
			}
		}

		// [SelectState]//
		if (state.equals("Selected")) {
			// Voltar
			if (coordsTouch.x >= +39 && coordsTouch.x <= 60 && coordsTouch.y >= -62 && coordsTouch.y <= -50) {
				state = "Main";
				return false;
			}
		}

		// [CreateState]//
		if (state.equals("Create")) {
			// Back
			if (coordsTouch.x >= -55 && coordsTouch.x <= -30f && coordsTouch.y >= -53 && coordsTouch.y <= -42) {
				state = "Main";
				return false;
			}

			// Nome
			if (coordsTouch.x >= -16 && coordsTouch.x <= 17 && coordsTouch.y >= 29 && coordsTouch.y <= 39) {
				state = "keyboard";
				return false;
			}

			// Masc
			if (coordsTouch.x >= -16 && coordsTouch.x <= -10 && coordsTouch.y >= 16 && coordsTouch.y <= 27) {
				sex = "M";
				return false;
			}

			// Fem
			if (coordsTouch.x >= -8 && coordsTouch.x <= -1 && coordsTouch.y >= 16 && coordsTouch.y <= 27) {
				sex = "F";
				return false;
			}

			// Hair 1
			if (coordsTouch.x >= -28 && coordsTouch.x <= -19.5f && coordsTouch.y >= -10 && coordsTouch.y <= 5) {
				hair = "hair1";
				return false;
			}
			// Hair 2
			if (coordsTouch.x >= -18.5f && coordsTouch.x <= -10 && coordsTouch.y >= -10 && coordsTouch.y <= 5) {
				hair = "hair2";
				return false;
			}
			// Hair 3
			if (coordsTouch.x >= -9 && coordsTouch.x <= 0 && coordsTouch.y >= -10 && coordsTouch.y <= 5) {
				hair = "hair3";
				return false;
			}
			// Hair 4
			if (coordsTouch.x >= 1 && coordsTouch.x <= 9 && coordsTouch.y >= -10 && coordsTouch.y <= 5) {
				hair = "hair4";
				return false;
			}

			// Color
			// Yellow
			if (coordsTouch.x >= -28 && coordsTouch.x <= -19 && coordsTouch.y >= -38 && coordsTouch.y <= -22) {
				color = "yellow";
				return false;
			}
			// green
			if (coordsTouch.x >= -18 && coordsTouch.x <= -9 && coordsTouch.y >= -38 && coordsTouch.y <= -22) {
				color = "green";
				return false;
			}
			// red
			if (coordsTouch.x >= -7 && coordsTouch.x <= 2 && coordsTouch.y >= -38 && coordsTouch.y <= -22) {
				color = "red";
				return false;
			}
			// pink
			if (coordsTouch.x >= 3 && coordsTouch.x <= 12 && coordsTouch.y >= -38 && coordsTouch.y <= -22) {
				color = "pink";
				return false;
			}
			// blue
			if (coordsTouch.x >= 13 && coordsTouch.x <= 22 && coordsTouch.y >= -38 && coordsTouch.y <= -22) {
				color = "blue";
				return false;
			}
			// grey
			if (coordsTouch.x >= 24 && coordsTouch.x <= 33 && coordsTouch.y >= -38 && coordsTouch.y <= -22) {
				color = "grey";
				return false;
			}

			// Confirmar
			if (coordsTouch.x >= 28 && coordsTouch.x <= 54 && coordsTouch.y >= -53 && coordsTouch.y <= -42) {
				try {
					gameControl.CreateCharOnline("CreateChar",account,name,sex,hair,color, new HttpCallback() {
                        @Override
                        public void onSuccess(String response) {
                        	if(response.contains("success")) {
                        		System.out.println("Atualizado");
                        		loadAccountState = false;
                        		accountInProcess = false;
                        		state = "Main";
                        	}
                        	else {
                        		avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
                        		aviso = true;
                        	}
                        }

                        @Override
                        public void onFailure(Throwable t) {
                           System.out.println("Error: " + t.getMessage());
                           avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
                   		   aviso = true;
                        }
                    });
					return false;	
				} catch (Exception ex) {
					avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
            		aviso = true;
				}
			}
		}

		if (state.equals("Delete")) {
			// Voltar
			if (coordsTouch.x >= +39 && coordsTouch.x <= 60 && coordsTouch.y >= -62 && coordsTouch.y <= -50) {
				state = "Main";
				return false;
			}
			// delete char 1
			if (coordsTouch.x >= -52 && coordsTouch.x <= -28 && coordsTouch.y >= -42 && coordsTouch.y <= 20) {
				try {
					gameControl.DeleteChar("DeleteChar",account,"1", new HttpCallback() {
                        @Override
                        public void onSuccess(String response) {
                        	if(response.contains("success")) {
                        		System.out.println("Deletado");
                        		loadAccountState = false;
                        		accountInProcess = false;
                        		state = "Main";
                        	}
                        	else {
                        		avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
                        		aviso = true;
                        	}
                        }

                        @Override
                        public void onFailure(Throwable t) {
                           System.out.println("Error: " + t.getMessage());
                           avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
                   		   aviso = true;
                        }
                    });
					return false;	
				} catch (Exception ex) {
					avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
            		aviso = true;
				}
			}
			// delete char 2
			if (coordsTouch.x >= -15 && coordsTouch.x <= 15 && coordsTouch.y >= -42 && coordsTouch.y <= 20) {
				try {
					gameControl.DeleteChar("DeleteChar",account,"2", new HttpCallback() {
                        @Override
                        public void onSuccess(String response) {
                        	if(response.contains("success")) {
                        		System.out.println("Deletado");
                        		loadAccountState = false;
                        		accountInProcess = false;
                        		state = "Main";
                        	}
                        	else {
                        		avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
                        		aviso = true;
                        	}
                        }

                        @Override
                        public void onFailure(Throwable t) {
                           System.out.println("Error: " + t.getMessage());
                           avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
                   		   aviso = true;
                        }
                    });
					return false;	
				} catch (Exception ex) {
					avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
            		aviso = true;
				}
			}
			// delete char 3
			if (coordsTouch.x >= 22 && coordsTouch.x <= 44 && coordsTouch.y >= -42 && coordsTouch.y <= 20) {
				try {
					gameControl.DeleteChar("DeleteChar",account,"3", new HttpCallback() {
                        @Override
                        public void onSuccess(String response) {
                        	if(response.contains("success")) {
                        		System.out.println("Deletado");
                        		loadAccountState = false;
                        		accountInProcess = false;
                        		state = "Main";
                        	}
                        	else {
                        		avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
                        		aviso = true;
                        	}
                        }

                        @Override
                        public void onFailure(Throwable t) {
                           System.out.println("Error: " + t.getMessage());
                           avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
                   		   aviso = true;
                        }
                    });
					return false;	
				} catch (Exception ex) {
					avisoMsg = "Nao foi possivel efetuar operacao, tente novamente";
            		aviso = true;
				}
			}
		}

		if (state.equals("Selected")) {
			// Voltar
			if (coordsTouch.x >= +39 && coordsTouch.x <= 60 && coordsTouch.y >= -62 && coordsTouch.y <= -50) {
				state = "Main";
				return false;
			}
			// Selected
			if (coordsTouch.x >= +40 && coordsTouch.x <= 63 && coordsTouch.y >= 11 && coordsTouch.y <= 24) {
				state = "Change";
				return false;
			}
		}

		if (state.contains("keyboard")) {
			this.KeyboardKeyPressed(coordsTouch.x, coordsTouch.y);
			return false;
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
		throw new UnsupportedOperationException("Unimplemented method 'touchCancelled'");
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'scrolled'");
	}
}
