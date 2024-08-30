package com.moonbolt.cityscale;

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
import com.moonbolt.cityscale.models.Player;


public class CharacterSelect implements Screen, ApplicationListener, InputProcessor, TextInputListener {
		//Objects
	    private MainGame game;
	    private GameControl gameControl;
	    private ManagerScreen screen;
	    private String state = "Account";
	    private boolean network = false;
	    private String systemMsg = "";

		//Fonts
		private BitmapFont font_master;

	    //Camera
	    private OrthographicCamera camera;
	    private Viewport viewport;
	    private float cameraCoordsX = 0;
	    private float cameraCoordsY = 0;

	    //Player
	    private Player player;
	    private String name = "";
	    private String sex = "M";
	    private String hair = "hair1";
	    private String color = "yellow";
	    private String setUpper = "basic";
	    private String setBottom = "basic";
	    private String setFooter = "basic";
	    private int selectedchar = 0;

	    //Sprites
	    private Sprite spr_Background;
	    private Texture tex_Background;
		private float posTrainY = -70;

	    //Teste
	    private Texture tex_testeDot;
	    private Sprite spr_testeDot;

	    //Sprites
	    private Sprite spr_master;
	    private Sprite spr_player;
	    private Sprite spr_hair;
	    private Sprite spr_hat;

	    //Textures
	    private TextureAtlas atlas_gameUI;

	    //Controller
	    private final IntSet downKeys = new IntSet(20);

		public CharacterSelect(MainGame _game, ManagerScreen _screen,boolean _network) {
			this.game = _game;
			this.screen = _screen;
			this.network = _network;
			this.gameControl = new GameControl();

			//Load Account
			player = gameControl.LoadData();

			//test dot
			tex_testeDot = new Texture(Gdx.files.internal("data/assets/etc/testdot.png"));
			spr_testeDot = new Sprite(tex_testeDot);
			spr_master = new Sprite(tex_testeDot);

			spr_hair = new Sprite(tex_testeDot);
			spr_hat = new Sprite(tex_testeDot);

			//Load Title
			tex_Background = new Texture(Gdx.files.internal("data/assets/maps/characterselect.png"));
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
				spr_Background.setPosition(-70,posTrainY);
				spr_Background.setSize(140, 140);
				spr_Background.draw(game.batch);

				posTrainY = posTrainY + 0.02f;
				if(posTrainY > -68f) { posTrainY = -70; }


				if(state.equals("Account")) {
					spr_master = gameControl.GetUXSmall("textbar");
					spr_master.setPosition(-42, 0);
					spr_master.setSize(90, 30);
					spr_master.draw(game.batch);

					font_master.getData().setScale(0.10f,0.16f);
					font_master.setUseIntegerPositions(false);
					font_master.draw(game.batch, "ID da conta, guarde para recupera-la posteriormente", -40 , 26);

					font_master.getData().setScale(0.25f,0.35f);
					font_master.draw(game.batch, player.AccountID, -12 , 18);

					font_master.getData().setScale(0.10f,0.16f);
					font_master.draw(game.batch, "Toque/Click para continuar", 10, 7);
				}


				if(state.equals("Main")) {
					spr_master = gameControl.GetUXSmall("bannerselect");
					spr_master.draw(game.batch);

					spr_master = gameControl.GetUXSmall("btncreatenew");
					spr_master.draw(game.batch);

					spr_master = gameControl.GetUXSmall("btnexclude");
					spr_master.draw(game.batch);

					if(!player.Name_1.equals("none")) {
						spr_master = gameControl.SelectShowCharacterSprite(player, "footer",1);
						spr_master.draw(game.batch);

						spr_master = gameControl.SelectShowCharacterSprite(player, "bottom",1);
						spr_master.draw(game.batch);

						spr_master = gameControl.SelectShowCharacterSprite(player, "upper",1);
						spr_master.draw(game.batch);

						spr_master = gameControl.MenuHairCreateSprite(player.Sex_1, player.Hair_1,player.Color_1,1);
						spr_master.draw(game.batch);
					}
					if(!player.Name_2.equals("none")) {
						spr_master = gameControl.SelectShowCharacterSprite(player, "footer",2);
						spr_master.draw(game.batch);

						spr_master = gameControl.SelectShowCharacterSprite(player, "bottom",2);
						spr_master.draw(game.batch);

						spr_master = gameControl.SelectShowCharacterSprite(player, "upper",2);
						spr_master.draw(game.batch);

						spr_master = gameControl.MenuHairCreateSprite(player.Sex_2, player.Hair_2,player.Color_2,2);
						spr_master.draw(game.batch);
					}
					if(!player.Name_3.equals("none")) {
						spr_master = gameControl.SelectShowCharacterSprite(player, "footer",3);
						spr_master.draw(game.batch);

						spr_master = gameControl.SelectShowCharacterSprite(player, "bottom",3);
						spr_master.draw(game.batch);

						spr_master = gameControl.SelectShowCharacterSprite(player, "upper",3);
						spr_master.draw(game.batch);

						spr_master = gameControl.MenuHairCreateSprite(player.Sex_3, player.Hair_3,player.Color_3,3);
						spr_master.draw(game.batch);
					}
				}

				if(state.equals("Create")) {
					spr_master = gameControl.GetUXSmall("create");
					spr_master.draw(game.batch);

					spr_master = gameControl.MenuCreateSprite(sex, "footer");
					spr_master.draw(game.batch);

					spr_master = gameControl.MenuCreateSprite(sex, "bottom");
					spr_master.draw(game.batch);

					spr_master = gameControl.MenuCreateSprite(sex, "upper");
					spr_master.draw(game.batch);

					spr_master = gameControl.MenuHairCreateSprite(sex, hair,color, 99);
					spr_master.draw(game.batch);

					spr_master = gameControl.MenuHairsSelect(1, sex, color);
					spr_master.draw(game.batch);
					spr_master = gameControl.MenuHairsSelect(2, sex, color);
					spr_master.draw(game.batch);
					spr_master = gameControl.MenuHairsSelect(3, sex, color);
					spr_master.draw(game.batch);
					spr_master = gameControl.MenuHairsSelect(4, sex, color);
					spr_master.draw(game.batch);

					font_master.setColor(Color.WHITE);
					font_master.getData().setScale(0.12f,0.19f);
					font_master.setUseIntegerPositions(false);
					font_master.draw(game.batch, name , -13 , 37);
				}

				if(state.equals("Delete")) {
					spr_master = gameControl.GetUXSmall("bannerdelete");
					spr_master.draw(game.batch);

					spr_master = gameControl.GetUXSmall("btnvoltar");
					spr_master.draw(game.batch);



					if(!player.Name_1.equals("none")) {
						spr_master = gameControl.SelectShowCharacterSprite(player, "footer",1);
						spr_master.draw(game.batch);

						spr_master = gameControl.SelectShowCharacterSprite(player, "bottom",1);
						spr_master.draw(game.batch);

						spr_master = gameControl.SelectShowCharacterSprite(player, "upper",1);
						spr_master.draw(game.batch);

						spr_master = gameControl.MenuHairCreateSprite(player.Sex_1, player.Hair_1,player.Color_1,1);
						spr_master.draw(game.batch);
					}
					if(!player.Name_2.equals("none")) {
						spr_master = gameControl.SelectShowCharacterSprite(player, "footer",2);
						spr_master.draw(game.batch);

						spr_master = gameControl.SelectShowCharacterSprite(player, "bottom",2);
						spr_master.draw(game.batch);

						spr_master = gameControl.SelectShowCharacterSprite(player, "upper",2);
						spr_master.draw(game.batch);

						spr_master = gameControl.MenuHairCreateSprite(player.Sex_2, player.Hair_2,player.Color_2,2);
						spr_master.draw(game.batch);
					}
					if(!player.Name_3.equals("none")) {
						spr_master = gameControl.SelectShowCharacterSprite(player, "footer",3);
						spr_master.draw(game.batch);

						spr_master = gameControl.SelectShowCharacterSprite(player, "bottom",3);
						spr_master.draw(game.batch);

						spr_master = gameControl.SelectShowCharacterSprite(player, "upper",3);
						spr_master.draw(game.batch);

						spr_master = gameControl.MenuHairCreateSprite(player.Sex_3, player.Hair_3,player.Color_3,3);
						spr_master.draw(game.batch);
					}
				}

				if(state.equals("Selected")) {

					spr_master = gameControl.GetUXSmall("confirmtab");
					spr_master.draw(game.batch);

					spr_master = gameControl.GetUXSmall("btnvoltar");
					spr_master.draw(game.batch);

					font_master.setColor(Color.WHITE);
					font_master.getData().setScale(0.12f,0.19f);
					font_master.setUseIntegerPositions(false);


					if(!player.Name_1.equals("none") && selectedchar == 1) {
						spr_master = gameControl.SelectShowCharacterSprite(player, "footer",1);
						spr_master.draw(game.batch);

						spr_master = gameControl.SelectShowCharacterSprite(player, "bottom",1);
						spr_master.draw(game.batch);

						spr_master = gameControl.SelectShowCharacterSprite(player, "upper",1);
						spr_master.draw(game.batch);

						spr_master = gameControl.MenuHairCreateSprite(player.Sex_1, player.Hair_1,player.Color_1,1);
						spr_master.draw(game.batch);

						font_master.draw(game.batch, player.Name_1 , 25 , 48);
						font_master.draw(game.batch, String.valueOf(player.Level_1), 25 , 42);
						font_master.draw(game.batch, player.Map_1 , 25 , 35);
					}
					if(!player.Name_2.equals("none")  && selectedchar == 2) {

						spr_master = gameControl.SelectShowCharacterSprite(player, "footer",2);
						spr_master.draw(game.batch);

						spr_master = gameControl.SelectShowCharacterSprite(player, "bottom",2);
						spr_master.draw(game.batch);

						spr_master = gameControl.SelectShowCharacterSprite(player, "upper",2);
						spr_master.draw(game.batch);

						spr_master = gameControl.MenuHairCreateSprite(player.Sex_2, player.Hair_2,player.Color_2,2);
						spr_master.draw(game.batch);

						font_master.draw(game.batch, player.Name_2 , 25 , 48);
						font_master.draw(game.batch, String.valueOf(player.Level_2), 25 , 42);
						font_master.draw(game.batch, player.Map_2 , 25 , 35);
					}
					if(!player.Name_3.equals("none")  && selectedchar == 3) {
						spr_master = gameControl.SelectShowCharacterSprite(player, "footer",3);
						spr_master.draw(game.batch);

						spr_master = gameControl.SelectShowCharacterSprite(player, "bottom",3);
						spr_master.draw(game.batch);

						spr_master = gameControl.SelectShowCharacterSprite(player, "upper",3);
						spr_master.draw(game.batch);

						spr_master = gameControl.MenuHairCreateSprite(player.Sex_3, player.Hair_3,player.Color_3,3);
						spr_master.draw(game.batch);

						font_master.draw(game.batch, player.Name_3 , 25 , 48);
						font_master.draw(game.batch, String.valueOf(player.Level_3), 25 , 42);
						font_master.draw(game.batch, player.Map_3 , 25 , 35);
					}
				}

				if(state.equals("Change")) {
					player.Map_A.equals("MetroStation");
					gameControl.SaveData(player);
					//this.screen.screenSwitch("LoadingScreen", network, selectedchar);
					dispose();
				}


				//spr_testeDot.setPosition(9,-10);
				//spr_testeDot.setSize(1, 1);
				//spr_testeDot.draw(game.batch);

				//spr_testeDot.setPosition(1,5);
				//spr_testeDot.setSize(1, 1);
				//spr_testeDot.draw(game.batch);

				game.batch.end();

		}

		private boolean CheckName(String nameinput) {
			if(nameinput.equals("none")){ systemMsg = "Insira um nome"; return false;}
			if(nameinput.equals("")) { systemMsg = "Insira um nome"; return false; }
			if(nameinput.contains(".")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains("-")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains(";")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains("'")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains("~")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains(":")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains("?")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains("!")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains("-")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains("*")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains("=")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains("@")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains("#")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains("$")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains("%")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains("&")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains("(")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains(")")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains("=")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains("/")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains("\\")){ systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains("<")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.contains(">")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(nameinput.length() > 10) { systemMsg = "Ate 10 letras"; return false; }
			if(nameinput.length() < 3) { systemMsg = "Menos de 3 letras"; return false; }

			return true;
		}

		@Override
		public void input(String input) {
			if(state.equals("Create")) {
				if(CheckName(input)) {
					name = input;
				}
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
			return false;
		}

		@Override
		public boolean touchDown(int p1, int p2, int pointer, int button) {

			Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));

			//[Account]//
			if(state.equals("Account")) {
				if(coordsTouch.x >=  -200 && coordsTouch.x <= 200 && coordsTouch.y >= -200 && coordsTouch.y <= 200) {
					state = "Main";
					return false;
				}
			}

			//[MainState]//
			if(state.equals("Main")) {
				//Create
				if(coordsTouch.x >=  -60 && coordsTouch.x <= -40 && coordsTouch.y >= -61 && coordsTouch.y <= -50) {
					state = "Create";
					return false;
				}

				//Delete
				if(coordsTouch.x >=  40 && coordsTouch.x <= 60 && coordsTouch.y >= -61 && coordsTouch.y <= -50) {
					state = "Delete";
					return false;
				}
				//select char 1
				if(coordsTouch.x >=  -52 && coordsTouch.x <= -28 && coordsTouch.y >= -42 && coordsTouch.y <= 20) {
					if(!player.Name_1.equals("none")) {
						selectedchar = 1;
						state = "Selected";
					}
					return false;
				}
				//select char 2
				if(coordsTouch.x >=  -15 && coordsTouch.x <= 15 && coordsTouch.y >= -42 && coordsTouch.y <= 20) {
					if(!player.Name_2.equals("none")) {
						selectedchar = 2;
						state = "Selected";
					}
					return false;
				}
				//select char 3
				if(coordsTouch.x >=  22 && coordsTouch.x <= 44 && coordsTouch.y >= -42 && coordsTouch.y <= 20) {
					if(!player.Name_3.equals("none")) {
						selectedchar = 3;
						state = "Selected";
					}
					return false;
				}
			}


			//[SelectState]//
			if(state.equals("Selected")) {
				//Voltar
				if(coordsTouch.x >=  + 39 && coordsTouch.x <= 60 && coordsTouch.y >= -62 && coordsTouch.y <= -50) {
					state = "Main";
					return false;
				}
			}

			//[CreateState]//
			if(state.equals("Create")) {
				//Back
				if(coordsTouch.x >= -55 && coordsTouch.x <= -30f && coordsTouch.y >= -53 && coordsTouch.y <= -42) {
					state = "Main";
					return false;
				}

				//Nome
				if(coordsTouch.x >= -16 && coordsTouch.x <= 17 && coordsTouch.y >= 29 && coordsTouch.y <= 39) {
					Gdx.input.getTextInput(this,"Digite o nome","","");
					return false;
				}

				//Masc
				if(coordsTouch.x >= -16 && coordsTouch.x <= -10 && coordsTouch.y >= 16 && coordsTouch.y <= 27) {
					sex = "M";
					return false;
				}

				//Fem
				if(coordsTouch.x >= -8 && coordsTouch.x <= -1 && coordsTouch.y >= 16 && coordsTouch.y <= 27) {
					sex = "F";
					return false;
				}

				//Hair 1
				if(coordsTouch.x >= -28 && coordsTouch.x <= -19.5f && coordsTouch.y >= -10 && coordsTouch.y <= 5) {
					hair = "hair1";
					return false;
				}
				//Hair 2
				if(coordsTouch.x >= -18.5f && coordsTouch.x <= -10 && coordsTouch.y >= -10 && coordsTouch.y <= 5) {
					hair = "hair2";
					return false;
				}
				//Hair 3
				if(coordsTouch.x >= -9 && coordsTouch.x <= 0 && coordsTouch.y >= -10 && coordsTouch.y <= 5) {
					hair = "hair3";
					return false;
				}
				//Hair 4
				if(coordsTouch.x >= 1 && coordsTouch.x <= 9 && coordsTouch.y >= -10 && coordsTouch.y <= 5) {
					hair = "hair4";
					return false;
				}

				//Color
				//Yellow
				if(coordsTouch.x >= -28 && coordsTouch.x <= -19 && coordsTouch.y >= -38 && coordsTouch.y <= -22) {
					color = "yellow";
					return false;
				}
				//green
				if(coordsTouch.x >= -18 && coordsTouch.x <= -9 && coordsTouch.y >= -38 && coordsTouch.y <= -22) {
					color = "green";
					return false;
				}
				//red
				if(coordsTouch.x >= -7 && coordsTouch.x <= 2 && coordsTouch.y >= -38 && coordsTouch.y <= -22) {
					color = "red";
					return false;
				}
				//pink
				if(coordsTouch.x >= 3 && coordsTouch.x <= 12 && coordsTouch.y >= -38 && coordsTouch.y <= -22) {
					color = "pink";
					return false;
				}
				//blue
				if(coordsTouch.x >= 13 && coordsTouch.x <= 22 && coordsTouch.y >= -38 && coordsTouch.y <= -22) {
					color = "blue";
					return false;
				}
				//grey
				if(coordsTouch.x >= 24 && coordsTouch.x <= 33 && coordsTouch.y >= -38 && coordsTouch.y <= -22) {
					color = "grey";
					return false;
				}

				//Confirmar
				if(coordsTouch.x >= 28 && coordsTouch.x <= 54 && coordsTouch.y >= -53 && coordsTouch.y <= -42) {
					gameControl.CreateNewChar(name, sex, hair, color);
					player = gameControl.LoadData();
					state = "Main";
					return false;
				}



			}

			if(state.equals("Delete")) {
				//Voltar
				if(coordsTouch.x >=  + 39 && coordsTouch.x <= 60 && coordsTouch.y >= -62 && coordsTouch.y <= -50) {
					state = "Main";
					return false;
				}
				//delete char 1
				if(coordsTouch.x >=  -52 && coordsTouch.x <= -28 && coordsTouch.y >= -42 && coordsTouch.y <= 20) {
					gameControl.DeleteChar(1);
					player = gameControl.LoadData();
					return false;
				}
				//delete char 2
				if(coordsTouch.x >=  -15 && coordsTouch.x <= 15 && coordsTouch.y >= -42 && coordsTouch.y <= 20) {
					gameControl.DeleteChar(2);
					player = gameControl.LoadData();
					return false;
				}
				//delete char 3
				if(coordsTouch.x >=  22 && coordsTouch.x <= 44 && coordsTouch.y >= -42 && coordsTouch.y <= 20) {
					gameControl.DeleteChar(3);
					player = gameControl.LoadData();
					return false;
				}
			}

			if(state.equals("Selected")) {
				//Voltar
				if(coordsTouch.x >=  + 39 && coordsTouch.x <= 60 && coordsTouch.y >= -62 && coordsTouch.y <= -50) {
					state = "Main";
					return false;
				}
				//Selected
				if(coordsTouch.x >=  + 40 && coordsTouch.x <= 63 && coordsTouch.y >= 11 && coordsTouch.y <= 24) {
					gameControl.SetCharacter(selectedchar);
					state = "Change";
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
