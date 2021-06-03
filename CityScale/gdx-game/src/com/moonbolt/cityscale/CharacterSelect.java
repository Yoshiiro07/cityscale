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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CharacterSelect implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	////MAINLY///
	private MainGame game;
	private GameControl gameControl;
	private Player activeplayer;
	private String[] config;
	private String platform;
	private boolean network = false;
	private String networkState = "on";
	
	//Primitives
	private String systemMsg = "";
	private String screenState = "ID";
	private String text = "";
	private String sex = "M";
	private String hair = "hair1";
	private String name = "";
	private boolean changeScreen = false;
	private float movBackground = 0;
	private int num = 0;
	private int charnum = 0;
	private String accountid;
	
	//Camera
	private OrthographicCamera camera;
    private Viewport viewport;

	//Sprite 
	private Sprite spr_Background;
	private Sprite spr_titleTop;
	private Sprite spr_btnCreate;
	private Sprite spr_btnDelete;
	private Sprite spr_btnVoltar;
	private Sprite spr_barraCharacter;
	private Sprite spr_boardCreate;
	private Sprite spr_characterSet;
	private Sprite spr_hair;
	private Sprite spr_hat;
	private Sprite spr_hairLoop;
	private Sprite spr_light;
	private Sprite spr_darkLight;
	private Sprite spr_barraID;
	
	//Texture
	private Texture tex_Background;
	
	//fonts
	private BitmapFont font_master;
	
	//Teste Dot
    private Sprite spr_testeDot;
    private Texture tex_testeDot;
	
		
	public CharacterSelect(MainGame gameAlt,GameControl gameControl, String[] configAlt, String platformAlt){
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
		tex_Background = new Texture(Gdx.files.internal("data/maps/characterselect.png"));
		spr_Background = new Sprite(tex_Background);
		
		//test dot
		tex_testeDot = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		spr_testeDot = new Sprite(tex_testeDot);
		
		//Load Data
		gameControl.LoadData();
		
		//Online
		gameControl.OnlineManager("Sync","");
		networkState = "on";
		network = true;
	}

	@Override
	public void render(float p1)
	{	
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		
		//Loader
		gameControl.LoadData();
		activeplayer = gameControl.GetPlayer();
		
		//Sprites
		MovBackground();
		spr_Background.setPosition(0, movBackground);
		spr_Background.setSize(100, 100);
		spr_Background.draw(game.batch);
		
		if(screenState.equals("ID")) {	
			font_master.setColor(Color.YELLOW);
			font_master.getData().setScale(0.10f,0.20f);
			font_master.setUseIntegerPositions(false);	
			
			spr_barraID = gameControl.LoadInterfaceGamePlay("IDbar", "", "");
			spr_barraID.draw(game.batch);		
			font_master.draw(game.batch, "Seu ID de acesso:", 27,70);
			font_master.draw(game.batch, activeplayer.accountID, 60,70);
		}
			
		//Main Screen Character Select
		if(screenState.equals("Main")) {
			
			spr_light = gameControl.LoadElements("light1");
			spr_light.draw(game.batch);
			
			spr_light = gameControl.LoadElements("light2");
			spr_light.draw(game.batch);
			
			spr_light = gameControl.LoadElements("light3");
			spr_light.draw(game.batch);
			
			spr_titleTop = gameControl.LoadInterface("titleTopSelect");
			spr_titleTop.draw(game.batch);
			
			spr_btnCreate = gameControl.LoadInterface("btnCreate");
			spr_btnCreate.draw(game.batch);
			
			spr_btnDelete = gameControl.LoadInterface("btnDelete");
			spr_btnDelete.draw(game.batch);
			
			if(!activeplayer.name_1.equals("none")) {
				if(activeplayer.sex_1.equals("M")) {
					spr_characterSet = gameControl.MovPlayerCharacter(activeplayer.set_1, activeplayer.sex_1, "stop", "front", true, "");
					spr_characterSet.setSize(25, 40);
					spr_characterSet.setPosition(9, 14);
					spr_characterSet.draw(game.batch);
					
					spr_hair = gameControl.PlayerHairCharacterSelect(activeplayer.hair_1,activeplayer.sex_1, "front");
					spr_hair.setSize(8, 11);
					spr_hair.setPosition(17.1f, 41.2f);
					spr_hair.draw(game.batch);
					
					if(!activeplayer.hat_1.equals("none")) {					
						spr_hat = gameControl.MovPlayerHat(activeplayer.hat_1,activeplayer.sex_1,"front", "Menu-Status", "");
						spr_hat.setSize(13, 21);
						spr_hat.setPosition(14.5f, 40.2f);
						spr_hat.draw(game.batch);
					}				
				}
				
				if(activeplayer.sex_1.equals("F")) {
					spr_characterSet = gameControl.MovPlayerCharacter(activeplayer.set_1, activeplayer.sex_1, "stop", "front", true, "");
					spr_characterSet.setSize(25, 40);
					spr_characterSet.setPosition(9, 14);
					spr_characterSet.draw(game.batch);
					
					spr_hair = gameControl.PlayerHairCharacterSelect(activeplayer.hair_1,activeplayer.sex_1, "front");
					spr_hair.setSize(8, 11);
					spr_hair.setPosition(17.1f, 41.4f);
					spr_hair.draw(game.batch);
					
					if(!activeplayer.hat_1.equals("none")) {					
						spr_hat = gameControl.MovPlayerHat(activeplayer.hat_1,activeplayer.sex_1,"front", "Menu-Status", "");
						spr_hat.setSize(13, 21);
						spr_hat.setPosition(14.5f, 40.2f);
						spr_hat.draw(game.batch);
					}
				}
			}
			
			if(!activeplayer.name_2.equals("none")) {
				if(activeplayer.sex_2.equals("M")) {
					spr_characterSet = gameControl.MovPlayerCharacter(activeplayer.set_2, activeplayer.sex_2, "stop", "front", true, "");
					spr_characterSet.setSize(25, 40);
					spr_characterSet.setPosition(37.5f, 14);
					spr_characterSet.draw(game.batch);
					
					spr_hair = gameControl.PlayerHairCharacterSelect(activeplayer.hair_2,activeplayer.sex_2, "front");
					spr_hair.setSize(8, 11);
					spr_hair.setPosition(45.6f, 41.2f);
					spr_hair.draw(game.batch);
					
					if(!activeplayer.hat_2.equals("none")) {					
						spr_hat = gameControl.MovPlayerHat(activeplayer.hat_2,activeplayer.sex_2,"front", "Menu-Status", "");
						spr_hat.setSize(13, 21);
						spr_hat.setPosition(43.6f, 40.2f);
						spr_hat.draw(game.batch);
					}
				}
				if(activeplayer.sex_2.equals("F")) {
					spr_characterSet = gameControl.MovPlayerCharacter(activeplayer.set_2, activeplayer.sex_2, "stop", "front", true, "");
					spr_characterSet.setSize(25, 40);
					spr_characterSet.setPosition(37.5f, 14);
					spr_characterSet.draw(game.batch);
					
					spr_hair = gameControl.PlayerHairCharacterSelect(activeplayer.hair_2,activeplayer.sex_2, "front");
					spr_hair.setSize(8, 11);
					spr_hair.setPosition(45.6f, 41.2f);
					spr_hair.draw(game.batch);
					
					if(!activeplayer.hat_2.equals("none")) {					
						spr_hat = gameControl.MovPlayerHat(activeplayer.hat_2,activeplayer.sex_2,"front", "Menu-Status", "");
						spr_hat.setSize(13, 21);
						spr_hat.setPosition(43.6f, 40.2f);
						spr_hat.draw(game.batch);
					}
				}
			}
			
			if(!activeplayer.name_3.equals("none")) {
				if(activeplayer.sex_3.equals("M")) {
					spr_characterSet = gameControl.MovPlayerCharacter(activeplayer.set_3, activeplayer.sex_3, "stop", "front", true, "");
					spr_characterSet.setSize(25, 40);
					spr_characterSet.setPosition(70f, 14);
					spr_characterSet.draw(game.batch);
					
					spr_hair = gameControl.PlayerHairCharacterSelect(activeplayer.hair_3,activeplayer.sex_3, "front");
					spr_hair.setSize(8, 11);
					spr_hair.setPosition(78, 41.2f);
					spr_hair.draw(game.batch);
					
					if(!activeplayer.hat_3.equals("none")) {					
						spr_hat = gameControl.MovPlayerHat(activeplayer.hat_3,activeplayer.sex_3,"front", "Menu-Status", "");
						spr_hat.setSize(13, 21);
						spr_hat.setPosition(76, 40.2f);
						spr_hat.draw(game.batch);
					}
				}
				
				if(activeplayer.sex_3.equals("F")) {
					spr_characterSet = gameControl.MovPlayerCharacter(activeplayer.set_3, activeplayer.sex_3, "stop", "front", true, "");
					spr_characterSet.setSize(25, 40);
					spr_characterSet.setPosition(70f, 14);
					spr_characterSet.draw(game.batch);
					
					spr_hair = gameControl.PlayerHairCharacterSelect(activeplayer.hair_3,activeplayer.sex_3, "front");
					spr_hair.setSize(8, 11);
					spr_hair.setPosition(78, 41.2f);
					spr_hair.draw(game.batch);	
					
					if(!activeplayer.hat_3.equals("none")) {					
						spr_hat = gameControl.MovPlayerHat(activeplayer.hat_3,activeplayer.sex_3,"front", "Menu-Status", "");
						spr_hat.setSize(13, 21);
						spr_hat.setPosition(76, 40.2f);
						spr_hat.draw(game.batch);
					}
				}
			}			
		}
		
		if(screenState.equals("Select")) {
			
			spr_barraCharacter = gameControl.LoadInterface("tagStart");
			spr_barraCharacter.draw(game.batch);
			
			spr_btnVoltar = gameControl.LoadInterface("btnVoltar");
			spr_btnVoltar.draw(game.batch);
			
			font_master.setColor(Color.WHITE);
			font_master.getData().setScale(0.10f,0.13f);
			font_master.setUseIntegerPositions(false);	
			
			if(charnum == 1) {
				spr_light = gameControl.LoadElements("light1");
				spr_light.draw(game.batch);
											
				font_master.draw(game.batch, activeplayer.name_1,72.5f, 94.8f);
				font_master.draw(game.batch, activeplayer.level_1,72.5f, 90f);
				font_master.draw(game.batch, activeplayer.job_1,73.9f, 85.7f);
				font_master.draw(game.batch, activeplayer.map_1,72.5f, 81f);
				
				if(activeplayer.sex_1.equals("M")) {
					spr_characterSet = gameControl.MovPlayerCharacter(activeplayer.set_1, activeplayer.sex_1, "stop", "front", true, "");
					spr_characterSet.setSize(25, 40);
					spr_characterSet.setPosition(9, 14);
					spr_characterSet.draw(game.batch);
					
					spr_hair = gameControl.PlayerHairCharacterSelect(activeplayer.hair_1,activeplayer.sex_1, "front");
					spr_hair.setSize(8, 11);
					spr_hair.setPosition(17.1f, 41.2f);
					spr_hair.draw(game.batch);
					
					if(!activeplayer.hat_1.equals("none")) {					
						spr_hat = gameControl.MovPlayerHat(activeplayer.hat_1,activeplayer.sex_1,"front", "Menu-Status", "");
						spr_hat.setSize(13, 21);
						spr_hat.setPosition(14.5f, 40.2f);
						spr_hat.draw(game.batch);
					}	
				}
				
				if(activeplayer.sex_1.equals("F")) {
					spr_characterSet = gameControl.MovPlayerCharacter(activeplayer.set_1, activeplayer.sex_1, "stop", "front", true, "");
					spr_characterSet.setSize(25, 40);
					spr_characterSet.setPosition(9, 14);
					spr_characterSet.draw(game.batch);
					
					spr_hair = gameControl.PlayerHairCharacterSelect(activeplayer.hair_1,activeplayer.sex_1, "front");
					spr_hair.setSize(8, 11);
					spr_hair.setPosition(17.1f, 41.4f);
					spr_hair.draw(game.batch);
					
					if(!activeplayer.hat_1.equals("none")) {					
						spr_hat = gameControl.MovPlayerHat(activeplayer.hat_1,activeplayer.sex_1,"front", "Menu-Status", "");
						spr_hat.setSize(13, 21);
						spr_hat.setPosition(14.5f, 40.2f);
						spr_hat.draw(game.batch);
					}
				}			
			}
			
			if(charnum == 2) {
				spr_light = gameControl.LoadElements("light2");
				spr_light.draw(game.batch);
				
				font_master.draw(game.batch, activeplayer.name_2,72.5f, 94.8f);
				font_master.draw(game.batch, activeplayer.level_2,72.5f, 90f);
				font_master.draw(game.batch, activeplayer.job_2,73.9f, 85.7f);
				font_master.draw(game.batch, activeplayer.map_2,72.5f, 81f);
				
				if(activeplayer.sex_2.equals("M")) {
					spr_characterSet = gameControl.MovPlayerCharacter(activeplayer.set_2, activeplayer.sex_2, "stop", "front", true, "");
					spr_characterSet.setSize(25, 40);
					spr_characterSet.setPosition(37.5f, 14);
					spr_characterSet.draw(game.batch);
					
					spr_hair = gameControl.PlayerHairCharacterSelect(activeplayer.hair_2,activeplayer.sex_2, "front");
					spr_hair.setSize(8, 11);
					spr_hair.setPosition(45.6f, 41.2f);
					spr_hair.draw(game.batch);
					
					if(!activeplayer.hat_2.equals("none")) {					
						spr_hat = gameControl.MovPlayerHat(activeplayer.hat_2,activeplayer.sex_2,"front", "Menu-Status", "");
						spr_hat.setSize(13, 21);
						spr_hat.setPosition(43.6f, 40.2f);
						spr_hat.draw(game.batch);
					}
				}
				if(activeplayer.sex_2.equals("F")) {
					spr_characterSet = gameControl.MovPlayerCharacter(activeplayer.set_2, activeplayer.sex_2, "stop", "front", true, "");
					spr_characterSet.setSize(25, 40);
					spr_characterSet.setPosition(37.5f, 14);
					spr_characterSet.draw(game.batch);
					
					spr_hair = gameControl.PlayerHairCharacterSelect(activeplayer.hair_2,activeplayer.sex_2, "front");
					spr_hair.setSize(8, 11);
					spr_hair.setPosition(45.6f, 41.2f);
					spr_hair.draw(game.batch);
					
					if(!activeplayer.hat_2.equals("none")) {					
						spr_hat = gameControl.MovPlayerHat(activeplayer.hat_2,activeplayer.sex_2,"front", "Menu-Status", "");
						spr_hat.setSize(13, 21);
						spr_hat.setPosition(43.6f, 40.2f);
						spr_hat.draw(game.batch);
					}
				}
			}

			if(charnum == 3) {
				spr_light = gameControl.LoadElements("light3");
				spr_light.draw(game.batch);
				
				font_master.draw(game.batch, activeplayer.name_3,72.5f, 94.8f);
				font_master.draw(game.batch, activeplayer.level_3,72.5f, 90f);
				font_master.draw(game.batch, activeplayer.job_3,73.9f, 85.7f);
				font_master.draw(game.batch, activeplayer.map_3,72.5f, 81f);
				
				if(activeplayer.sex_3.equals("M")) {
					spr_characterSet = gameControl.MovPlayerCharacter(activeplayer.set_3, activeplayer.sex_3, "stop", "front", true, "");
					spr_characterSet.setSize(25, 40);
					spr_characterSet.setPosition(70f, 14);
					spr_characterSet.draw(game.batch);
					
					spr_hair = gameControl.PlayerHairCharacterSelect(activeplayer.hair_3,activeplayer.sex_3, "front");
					spr_hair.setSize(8, 11);
					spr_hair.setPosition(78, 41.2f);
					spr_hair.draw(game.batch);
					
					if(!activeplayer.hat_3.equals("none")) {					
						spr_hat = gameControl.MovPlayerHat(activeplayer.hat_3,activeplayer.sex_3,"front", "Menu-Status", "");
						spr_hat.setSize(13, 21);
						spr_hat.setPosition(76, 40.2f);
						spr_hat.draw(game.batch);
					}
				}
				
				if(activeplayer.sex_3.equals("F")) {
					spr_characterSet = gameControl.MovPlayerCharacter(activeplayer.set_3, activeplayer.sex_3, "stop", "front", true, "");
					spr_characterSet.setSize(25, 40);
					spr_characterSet.setPosition(70f, 14);
					spr_characterSet.draw(game.batch);
					
					spr_hair = gameControl.PlayerHairCharacterSelect(activeplayer.hair_3,activeplayer.sex_3, "front");
					spr_hair.setSize(8, 11);
					spr_hair.setPosition(78, 41.2f);
					spr_hair.draw(game.batch);	
					
					if(!activeplayer.hat_3.equals("none")) {					
						spr_hat = gameControl.MovPlayerHat(activeplayer.hat_3,activeplayer.sex_3,"front", "Menu-Status", "");
						spr_hat.setSize(13, 21);
						spr_hat.setPosition(76, 40.2f);
						spr_hat.draw(game.batch);
					}
				}
			}		
		}
		
		
		//Create Character Screen
		if(screenState.equals("Create")) {
			spr_titleTop = gameControl.LoadInterface("titleTopCreate");
			spr_titleTop.draw(game.batch);
			
			spr_boardCreate = gameControl.LoadInterface("boardCreate");
			spr_boardCreate.draw(game.batch);
			
			font_master.setColor(Color.WHITE);
			font_master.getData().setScale(0.11f,0.12f);
			font_master.setUseIntegerPositions(false);	
			font_master.draw(game.batch, text, 37.2f,73);	
						
			spr_characterSet = gameControl.LoadCharacterMenu(sex); 
			spr_characterSet.draw(game.batch);
			
			spr_hair = gameControl.LoadCharacterHairMenu(hair, sex);
			spr_hair.draw(game.batch);
			
			//for show
			for(num = 1; num <= 11; num++) {
			spr_hairLoop = gameControl.LoadAllHairsMenu(num, sex);
			spr_hairLoop.draw(game.batch);
			}
			
			font_master.draw(game.batch, systemMsg,57.5f, 72.3f);		
		}
		
		
		//Delete Character Screen
		if(screenState.equals("Delete")) {
			
			spr_light = gameControl.LoadElements("light1");
			spr_light.draw(game.batch);
			
			spr_light = gameControl.LoadElements("light2");
			spr_light.draw(game.batch);
			
			spr_light = gameControl.LoadElements("light3");
			spr_light.draw(game.batch);
			
			spr_titleTop = gameControl.LoadInterface("titleTopDelete");
			spr_titleTop.draw(game.batch);	
			
			spr_btnVoltar = gameControl.LoadInterface("btnVoltar");
			spr_btnVoltar.draw(game.batch);
			
			if(!activeplayer.name_1.equals("none")) {
				if(activeplayer.sex_1.equals("M")) {
					spr_characterSet = gameControl.MovPlayerCharacter(activeplayer.set_1, activeplayer.sex_1, "stop", "front", true, "");
					spr_characterSet.setSize(25, 40);
					spr_characterSet.setPosition(9, 14);
					spr_characterSet.draw(game.batch);
					
					spr_hair = gameControl.PlayerHairCharacterSelect(activeplayer.hair_1,activeplayer.sex_1, "front");
					spr_hair.setSize(8, 11);
					spr_hair.setPosition(17.1f, 41.2f);
					spr_hair.draw(game.batch);
				}
				
				if(activeplayer.sex_1.equals("F")) {
					spr_characterSet = gameControl.MovPlayerCharacter(activeplayer.set_1, activeplayer.sex_1, "stop", "front", true, "");
					spr_characterSet.setSize(25, 40);
					spr_characterSet.setPosition(9, 14);
					spr_characterSet.draw(game.batch);
					
					spr_hair = gameControl.PlayerHairCharacterSelect(activeplayer.hair_1,activeplayer.sex_1, "front");
					spr_hair.setSize(8, 11);
					spr_hair.setPosition(17.1f, 41.4f);
					spr_hair.draw(game.batch);
				}
			}
			
			if(!activeplayer.name_2.equals("none")) {
				if(activeplayer.sex_2.equals("M")) {
					spr_characterSet = gameControl.MovPlayerCharacter(activeplayer.set_2, activeplayer.sex_2, "stop", "front", true, "");
					spr_characterSet.setSize(25, 40);
					spr_characterSet.setPosition(37.5f, 14);
					spr_characterSet.draw(game.batch);
					
					spr_hair = gameControl.PlayerHairCharacterSelect(activeplayer.hair_2,activeplayer.sex_2, "front");
					spr_hair.setSize(8, 11);
					spr_hair.setPosition(45.6f, 41.2f);
					spr_hair.draw(game.batch);
				}
				if(activeplayer.sex_2.equals("F")) {
					spr_characterSet = gameControl.MovPlayerCharacter(activeplayer.set_2, activeplayer.sex_2, "stop", "front", true, "");
					spr_characterSet.setSize(25, 40);
					spr_characterSet.setPosition(37.5f, 14);
					spr_characterSet.draw(game.batch);
					
					spr_hair = gameControl.PlayerHairCharacterSelect(activeplayer.hair_2,activeplayer.sex_2, "front");
					spr_hair.setSize(8, 11);
					spr_hair.setPosition(45.6f, 41.2f);
					spr_hair.draw(game.batch);
				}
			}
			
			if(!activeplayer.name_3.equals("none")) {
				if(activeplayer.sex_3.equals("M")) {
					spr_characterSet = gameControl.MovPlayerCharacter(activeplayer.set_3, activeplayer.sex_3, "stop", "front", true, "");
					spr_characterSet.setSize(25, 40);
					spr_characterSet.setPosition(70f, 14);
					spr_characterSet.draw(game.batch);
					
					spr_hair = gameControl.PlayerHairCharacterSelect(activeplayer.hair_3,activeplayer.sex_3, "front");
					spr_hair.setSize(8, 11);
					spr_hair.setPosition(78, 41.2f);
					spr_hair.draw(game.batch);
				}
				
				if(activeplayer.sex_3.equals("F")) {
					spr_characterSet = gameControl.MovPlayerCharacter(activeplayer.set_3, activeplayer.sex_3, "stop", "front", true, "");
					spr_characterSet.setSize(25, 40);
					spr_characterSet.setPosition(70f, 14);
					spr_characterSet.draw(game.batch);
					
					spr_hair = gameControl.PlayerHairCharacterSelect(activeplayer.hair_3,activeplayer.sex_3, "front");
					spr_hair.setSize(8, 11);
					spr_hair.setPosition(78, 41.2f);
					spr_hair.draw(game.batch);					
				}
			}
		}
			
		//Change Screen
		if(changeScreen){
			changeScreen = false;
			gameControl.OnlineManager("Desligar", "");
		    game.AtualizaElementos(game,gameControl, config, platform, networkState);
		    if(charnum == 1) { game.Switch(activeplayer.map_1); }
		    if(charnum == 2) { game.Switch(activeplayer.map_2); }
		    if(charnum == 3) { game.Switch(activeplayer.map_3); }			
		}
		
		//spr_testeDot.setPosition(67.2f, 50);
		//spr_testeDot.draw(game.batch);
		
		//spr_testeDot.setPosition(cameraCoordsX, cameraCoordsY);
		//spr_testeDot.draw(game.batch);
		
		game.batch.end();
	}
	
	
	
	private void MovBackground() {
		movBackground = movBackground + 0.01f;
		
		if(movBackground > 0.5f) {
			movBackground = 0;
		}
	}
	
	private boolean ValidaCharacterCreate() {
		if(name.equals("none")){ systemMsg = "Insira um nome"; return false;}
		if(name.equals("")) { systemMsg = "Insira um nome"; return false; }
		if(name.contains(".")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains("-")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains(";")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains("'")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains("~")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains(":")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains("?")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains("!")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains("-")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains("*")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains("=")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains("@")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains("#")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains("$")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains("%")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains("&")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains("(")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains(")")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains("=")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains("/")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains("\\")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains("<")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.contains(">")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(name.length() > 10) { systemMsg = "Ate 10 letras"; return false; }
		if(sex.equals("")) { systemMsg = "Escolha o sexo"; return false; }
		
		return true;
	}

	@Override
	public void resize(int p1, int p2)
	{
		viewport.update(p1,p2);
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
	}	
	
	//TOUCH RESPONSE	
	@Override
	public boolean touchDown(int p1, int p2, int p3, int p4)
	{
		Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));
		
		if(screenState.equals("ID")) {
			if(coordsTouch.x >= 0 && coordsTouch.x <= 100 && coordsTouch.y >= 0 && coordsTouch.y <= 100) {
				screenState = "Main";
				return false;
			}
		}
		
			
		if(screenState.equals("Main")) {
			//Create button
			if(coordsTouch.x >= 84.9 && coordsTouch.x <= 94.2 && coordsTouch.y >= 2.4 && coordsTouch.y <= 15.6) {
				screenState = "Create";
				return false;
			}
			
			//Delete Button
			if(coordsTouch.x >= 5.1 && coordsTouch.x <= 14.7 && coordsTouch.y >= 2.2 && coordsTouch.y <= 15.6) {
				screenState = "Delete";
				return false;
			}
			
			//Select Char 1
			if(coordsTouch.x >= 12.1 && coordsTouch.x <= 31.7 && coordsTouch.y >= 20.1 && coordsTouch.y <= 68.2) {
				if(!activeplayer.name_1.equals("none")) {
				charnum = 1;
				screenState = "Select";
				}
				return false;
			}
			
			//Select Char 2
			if(coordsTouch.x >= 39.9 && coordsTouch.x <= 60.4 && coordsTouch.y >= 20.1 && coordsTouch.y <= 68.2) {
				if(!activeplayer.name_2.equals("none")) {
				charnum = 2;
				screenState = "Select";
				}
				return false;
			}
			
			//Select Char 3
			if(coordsTouch.x >= 71.8 && coordsTouch.x <= 91.8 && coordsTouch.y >= 20.1 && coordsTouch.y <= 68.2) {
				if(!activeplayer.name_3.equals("none")) {
				charnum = 3;
				screenState = "Select";
				}
				return false;
			}
		}
		
		if(screenState.equals("Select")) {
			//Voltar
			if(coordsTouch.x >= 5.12 && coordsTouch.x <= 14.7 && coordsTouch.y >= 3.5 && coordsTouch.y <= 15.7) {
				screenState = "Main"; 
				return false;
			}
			//Iniciar
			if(coordsTouch.x >= 83.1f && coordsTouch.x <= 99 && coordsTouch.y >= 71.3f && coordsTouch.y <= 77.3f) {
				gameControl.SetActivePlayer(charnum);
				changeScreen = true; 
				return false;
			}
		}
		
		if(screenState.equals("Create")) {
			
			//Confirmar
			if(coordsTouch.x >= 68.1 && coordsTouch.x <= 80 && coordsTouch.y >= 9.5 && coordsTouch.y <= 15.8) {
				boolean checkvalida = ValidaCharacterCreate();			
				if(checkvalida) { 
					gameControl.CreateNewCharacter(name, hair, sex);
					gameControl.LoadData();
					screenState = "Main"; 
				}
				return false;
			}
			//Voltar
			if(coordsTouch.x >= 36.8 && coordsTouch.x <= 49.1 && coordsTouch.y >= 9 && coordsTouch.y <= 15.8) {
				screenState = "Main";
				return false;
			}
			
			//Name Button
			if(coordsTouch.x >= 36.8 && coordsTouch.x <= 57.3 && coordsTouch.y >= 64.3 && coordsTouch.y <= 74) {
				Gdx.input.getTextInput(this,"Digite o nome","","");
				return false;
			}
			
			//Sexo
			if(coordsTouch.x >= 41.3 && coordsTouch.x <= 53.6 && coordsTouch.y >= 57.3 && coordsTouch.y <= 64.1) {
				sex = "M"; hair = "hair1";
				return false;
			}
			if(coordsTouch.x >= 54.3 && coordsTouch.x <= 66.6 && coordsTouch.y >= 57.3 && coordsTouch.y <= 64.1) {
				sex = "F"; hair = "hair1";
				return false;
			}
			
			//Hair1 
			if(coordsTouch.x >= 36.8 && coordsTouch.x <= 42.6 && coordsTouch.y >= 36.8 && coordsTouch.y <= 47.6) {
				if(sex.equals("M")) { hair = "hair1"; }
				if(sex.equals("F")) { hair = "hair1";}
				return false;
			}
			//Hair2
			if(coordsTouch.x >= 43.2 && coordsTouch.x <= 48.9 && coordsTouch.y >= 36.8 && coordsTouch.y <= 47.6) {
				if(sex.equals("M")) { hair = "hair2"; }
				if(sex.equals("F")) { hair = "hair2";}
				return false;
			}
			//Hair3
			if(coordsTouch.x >= 49.4 && coordsTouch.x <= 55.1 && coordsTouch.y >= 36.8 && coordsTouch.y <= 47.6) {
				if(sex.equals("M")) { hair = "hair3"; }
				if(sex.equals("F")) { hair = "hair3";}
				return false;
			}
			//Hair4
			if(coordsTouch.x >= 55.7 && coordsTouch.x <= 61.4 && coordsTouch.y >= 36.8 && coordsTouch.y <= 47.6) {
				if(sex.equals("M")) { hair = "hair4"; }
				if(sex.equals("F")) { hair = "hair4";}
				return false;
			}
			//Hair5
			if(coordsTouch.x >= 62 && coordsTouch.x <= 67.2f && coordsTouch.y >= 36.8 && coordsTouch.y <= 47.6) {
				if(sex.equals("M")) { hair = "hair5"; }
				if(sex.equals("F")) { hair = "hair5";}
				return false;
			}
			//Hair6
			if(coordsTouch.x >= 68.3 && coordsTouch.x <= 74.0 && coordsTouch.y >= 36.8 && coordsTouch.y <= 47.6) {
				if(sex.equals("M")) { hair = "hair6"; }
				if(sex.equals("F")) { hair = "hair6";}
				return false;
			}
			
			//Hair7
			if(coordsTouch.x >= 36.8 && coordsTouch.x <= 42.6 && coordsTouch.y >= 25.2 && coordsTouch.y <= 35.7) {
				if(sex.equals("M")) { hair = "hair7"; }
				if(sex.equals("F")) { hair = "hair7";}
				return false;
			}
			//Hair8
			if(coordsTouch.x >= 43.2 && coordsTouch.x <= 48.9 && coordsTouch.y >= 25.2 && coordsTouch.y <= 35.7) {
				if(sex.equals("M")) { hair = "hair8"; }
				if(sex.equals("F")) { hair = "hair8";}
				return false;
			}
			//Hair9
			if(coordsTouch.x >= 49.4 && coordsTouch.x <= 55.1 && coordsTouch.y >= 25.2 && coordsTouch.y <= 35.7) {
				if(sex.equals("M")) { hair = "hair9"; }
				if(sex.equals("F")) { hair = "hair9";}
				return false;
			}
			//Hair10
			if(coordsTouch.x >= 55.7 && coordsTouch.x <= 61.4 && coordsTouch.y >= 25.2 && coordsTouch.y <= 35.7) {
				if(sex.equals("M")) { hair = "hair10"; }
				if(sex.equals("F")) { hair = "hair10"; }
				return false;
			}
			//Hair11
			if(coordsTouch.x >= 62 && coordsTouch.x <= 67.2f && coordsTouch.y >= 25.2 && coordsTouch.y <= 35.7) {
				if(sex.equals("M")) { hair = "hair11"; }
				if(sex.equals("F")) { hair = "hair11";}
				return false;
			}
			//Hair12
			if(coordsTouch.x >= 68.3 && coordsTouch.x <= 74.0 && coordsTouch.y >= 25.2 && coordsTouch.y <= 35.7) {
				//hair = "hair12";
			}
		}
		
		if(screenState.equals("Delete")) {
			//Voltar
			if(coordsTouch.x >= 5.12 && coordsTouch.x <= 14.7 && coordsTouch.y >= 3.5 && coordsTouch.y <= 15.7) {
				screenState = "Main"; 
				return false;
			}
			
			//Delete Char 1
			if(coordsTouch.x >= 12.1 && coordsTouch.x <= 31.7 && coordsTouch.y >= 20.1 && coordsTouch.y <= 68.2) {
				gameControl.DeleteCharacter(1);
				gameControl.LoadData();
				return false;
			}
			
			//Delete Char 2
			if(coordsTouch.x >= 39.9 && coordsTouch.x <= 60.4 && coordsTouch.y >= 20.1 && coordsTouch.y <= 68.2) {
				gameControl.DeleteCharacter(2);
				gameControl.LoadData();
				return false;
			}
			
			//Delete Char 3
			if(coordsTouch.x >= 71.8 && coordsTouch.x <= 91.8 && coordsTouch.y >= 20.1 && coordsTouch.y <= 68.2) {
				gameControl.DeleteCharacter(3);
				gameControl.LoadData();
				return false;
			}			
		}
		
		return false;
	}
	
	@Override
	public void input(String input){
		text = input;	
		name = text;
		//gameControl.OperacaoOnline("Download", text);
	}
	
	@Override
	public void canceled(){
	}
	
	@Override
	public void create()
	{
		// TODO: Implement this method
	}
	
	@Override
	public void render()
	{
		// TODO: Implement this method
	}
	
	@Override
	public void hide()
	{
		// TODO: Implement this method
	}

	@Override
	public void show()
	{
	}

	@Override
	public void resume()
	{
		// TODO: Implement this method
	}

	@Override
	public void pause()
	{
		// TODO: Implement this method
	}

	@Override
	public void dispose()
	{
		//gameControl = null;
		camera = null;
		viewport = null;
		game.dispose();
	}
	
	@Override
	public boolean touchUp(int p1, int p2, int p3, int p4)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean touchDragged(int p1, int p2, int p3)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean keyUp(int p1)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean keyDown(int p1)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean keyTyped(char p1)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean mouseMoved(int p1, int p2)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean scrolled(int p1)
	{
		// TODO: Implement this method
		return false;
	}
}
