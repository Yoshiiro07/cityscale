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
import com.badlogic.gdx.graphics.g3d.particles.renderers.ParticleControllerControllerRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CharacterSelect implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	private MainGame game;
	private ManagerScreen screen;
	private GameControl gameControl;
	private OrthographicCamera camera;
    private Viewport viewport;
    private Player player;
    private String charname = "Gui";
    private String systemMsg = "";
    private String sex = "M";
    private String hair = "hair1";
    
    
    private String state = "Main";
    private int charselect = 0;
    
    private Sprite spr_background;
	private Texture tex_background;
	private float posbackground = 0;
	
	private TextureAtlas atlas_ui;
	private Sprite spr_mainmenu;
	
	private Sprite spr_master;
	private Texture tex_master;
	
	private Sprite spr_testdot;
	private Texture tex_testdot;
	
	private BitmapFont font_master;
	
	public CharacterSelect(MainGame game, ManagerScreen screen){
		this.screen = screen;
		this.game = game;
		this.gameControl = gameControl;
		
		camera = new OrthographicCamera();
		viewport = new StretchViewport(100,100,camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		Gdx.input.setInputProcessor(this);
		
		
		gameControl = new GameControl();
		gameControl.LoadData();
		
		player = new Player();
		player = gameControl.GetPlayer();
				
		tex_background = new Texture(Gdx.files.internal("data/assets/maps/characterselect.png"));
		spr_background = new Sprite(tex_background);

		tex_testdot = new Texture(Gdx.files.internal("data/assets/misc/etc/testdot.png"));
		spr_testdot = new Sprite(tex_testdot);
		
		//font
		font_master = new BitmapFont(Gdx.files.internal("data/assets/font/impact.fnt"),Gdx.files.internal("data/assets/font/impact.png"), false);
		font_master.setColor(Color.WHITE);
		font_master.getData().setScale(0.13f,0.08f);
		font_master.setUseIntegerPositions(false);
		
		//Atlas
		atlas_ui = new TextureAtlas(Gdx.files.internal("data/assets/ui/ui.txt"));
		spr_mainmenu = new Sprite(tex_testdot);
	}

	@Override
	public void render(float p1)
	{
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
			
		game.batch.begin();
		
		TrainMove();
		
		if(state.equals("Main")) {
			
			spr_master = atlas_ui.createSprite("btncriar");
			spr_master.setPosition(5, 4);
			spr_master.setSize(17,10);
			spr_master.draw(game.batch);
			
			spr_master = atlas_ui.createSprite("btnexcluir");
			spr_master.setPosition(80, 4);
			spr_master.setSize(17,10);
			spr_master.draw(game.batch);
			
			spr_master = atlas_ui.createSprite("selecionepersonagem");
			spr_master.setPosition(5, 85);
			spr_master.setSize(45,8);
			spr_master.draw(game.batch);
			
			if(!player.name_1.equals("none")) {
				spr_master = gameControl.LoadCharUp(player, "yes", 1);
				spr_master.setPosition(15, 34);
				spr_master.setSize(8, 15);
				spr_master.draw(game.batch);	
				
				spr_master = gameControl.LoadCharBottom(player, "yes", 1);
				spr_master.setPosition(15.2f, 24.4f);
				spr_master.setSize(8, 15);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.LoadCharHair(player, "yes", 1);
				spr_master.setPosition(16f, 43.9f);
				spr_master.setSize(6, 11);
				spr_master.draw(game.batch);
			}	
			
			if(!player.name_2.equals("none")) {
				spr_master = gameControl.LoadCharUp(player, "yes", 2);
				spr_master.setPosition(45, 34);
				spr_master.setSize(8, 15);
				spr_master.draw(game.batch);	
				
				spr_master = gameControl.LoadCharBottom(player, "yes", 2);
				spr_master.setPosition(45.2f, 24.4f);
				spr_master.setSize(8, 15);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.LoadCharHair(player, "yes", 2);
				spr_master.setPosition(46, 43.9f);
				spr_master.setSize(6, 11);
				spr_master.draw(game.batch);
			}
			
			if(!player.name_3.equals("none")) {
				spr_master = gameControl.LoadCharUp(player, "yes", 3);
				spr_master.setPosition(74, 34);
				spr_master.setSize(8, 15);
				spr_master.draw(game.batch);	
				
				spr_master = gameControl.LoadCharBottom(player, "yes", 3);
				spr_master.setPosition(74.2f, 24.4f);
				spr_master.setSize(8, 15);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.LoadCharHair(player, "yes", 3);
				spr_master.setPosition(75f, 43.9f);
				spr_master.setSize(6, 11);
				spr_master.draw(game.batch);
			}
		}
		
		
		if(state.equals("Delete")) {
			
			spr_master = atlas_ui.createSprite("btnvoltar");
			spr_master.setPosition(41, 5);
			spr_master.setSize(14,8);
			spr_master.draw(game.batch);
			
			spr_master = atlas_ui.createSprite("excluindopersonagem");
			spr_master.setPosition(5, 85);
			spr_master.setSize(45,8);
			spr_master.draw(game.batch);
			
			if(!player.name_1.equals("none")) {
				spr_master = gameControl.LoadCharUp(player, "yes", 1);
				spr_master.setPosition(15, 34);
				spr_master.setSize(8, 15);
				spr_master.draw(game.batch);	
				
				spr_master = gameControl.LoadCharBottom(player, "yes", 1);
				spr_master.setPosition(15.2f, 24.4f);
				spr_master.setSize(8, 15);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.LoadCharHair(player, "yes", 1);
				spr_master.setPosition(16f, 43.9f);
				spr_master.setSize(6, 11);
				spr_master.draw(game.batch);
			}	
			
			if(!player.name_2.equals("none")) {
				spr_master = gameControl.LoadCharUp(player, "yes", 2);
				spr_master.setPosition(45, 34);
				spr_master.setSize(8, 15);
				spr_master.draw(game.batch);	
				
				spr_master = gameControl.LoadCharBottom(player, "yes", 2);
				spr_master.setPosition(45.2f, 24.4f);
				spr_master.setSize(8, 15);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.LoadCharHair(player, "yes", 2);
				spr_master.setPosition(46, 43.9f);
				spr_master.setSize(6, 11);
				spr_master.draw(game.batch);
			}
			
			if(!player.name_3.equals("none")) {
				spr_master = gameControl.LoadCharUp(player, "yes", 3);
				spr_master.setPosition(74, 34);
				spr_master.setSize(8, 15);
				spr_master.draw(game.batch);	
				
				spr_master = gameControl.LoadCharBottom(player, "yes", 3);
				spr_master.setPosition(74.2f, 24.4f);
				spr_master.setSize(8, 15);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.LoadCharHair(player, "yes", 3);
				spr_master.setPosition(75f, 43.9f);
				spr_master.setSize(6, 11);
				spr_master.draw(game.batch);
			}
		}
		
		if(state.equals("Selected")) {
			
			font_master.setColor(Color.WHITE);
			font_master.getData().setScale(0.06f,0.08f);
			font_master.setUseIntegerPositions(false);
			
			spr_master = atlas_ui.createSprite("btnvoltar");
			spr_master.setPosition(41, 5);
			spr_master.setSize(14,8);
			spr_master.draw(game.batch);
			
			spr_master = atlas_ui.createSprite("selecionepersonagem");
			spr_master.setPosition(5, 85);
			spr_master.setSize(45,8);
			spr_master.draw(game.batch);
			
			if(!player.name_1.equals("none") && charselect == 1) {
				spr_master = gameControl.LoadCharUp(player, "yes", 1);
				spr_master.setPosition(15, 34);
				spr_master.setSize(8, 15);
				spr_master.draw(game.batch);	
				
				spr_master = gameControl.LoadCharBottom(player, "yes", 1);
				spr_master.setPosition(15.2f, 24.4f);
				spr_master.setSize(8, 15);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.LoadCharHair(player, "yes", 1);
				spr_master.setPosition(16f, 43.9f);
				spr_master.setSize(6, 11);
				spr_master.draw(game.batch);
				
				spr_master = atlas_ui.createSprite("charmenuselect");
				spr_master.setPosition(70, 65);
				spr_master.setSize(30,30);
				spr_master.draw(game.batch);
				
				font_master.draw(game.batch, player.name_1 , 78, 87.5f);
				font_master.draw(game.batch, player.level_1 , 78, 84);
				font_master.draw(game.batch, player.job_1 , 78, 77);
				font_master.draw(game.batch, player.map_1 , 78, 80.5f);
			}	
			
			if(!player.name_2.equals("none") && charselect == 2) {
				spr_master = gameControl.LoadCharUp(player, "yes", 2);
				spr_master.setPosition(45, 34);
				spr_master.setSize(8, 15);
				spr_master.draw(game.batch);	
				
				spr_master = gameControl.LoadCharBottom(player, "yes", 2);
				spr_master.setPosition(45.2f, 24.4f);
				spr_master.setSize(8, 15);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.LoadCharHair(player, "yes", 2);
				spr_master.setPosition(46, 43.9f);
				spr_master.setSize(6, 11);
				spr_master.draw(game.batch);
				
				font_master.draw(game.batch, player.name_2 , 78, 87.5f);
				font_master.draw(game.batch, player.level_2 , 78, 84);
				font_master.draw(game.batch, player.job_2 , 78, 77);
				font_master.draw(game.batch, player.map_2 , 78, 80.5f);
			}
			
			if(!player.name_3.equals("none") && charselect == 3) {
				spr_master = gameControl.LoadCharUp(player, "yes", 3);
				spr_master.setPosition(74, 34);
				spr_master.setSize(8, 15);
				spr_master.draw(game.batch);	
				
				spr_master = gameControl.LoadCharBottom(player, "yes", 3);
				spr_master.setPosition(74.2f, 24.4f);
				spr_master.setSize(8, 15);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.LoadCharHair(player, "yes", 3);
				spr_master.setPosition(75f, 43.9f);
				spr_master.setSize(6, 11);
				spr_master.draw(game.batch);
				
				font_master.draw(game.batch, player.name_3 , 78, 87.5f);
				font_master.draw(game.batch, player.level_3 , 78, 84);
				font_master.draw(game.batch, player.job_3 , 78, 77);
				font_master.draw(game.batch, player.map_3 , 78, 80.5f);
			}
		}
		
		
		if(state.equals("Create")) {
			spr_master = atlas_ui.createSprite("createmenu");
			spr_master.setPosition(11, 10);
			spr_master.setSize(80,80);
			spr_master.draw(game.batch);
			
			if(!charname.equals("")){
				font_master.setColor(Color.WHITE);
				font_master.getData().setScale(0.09f,0.16f);
				font_master.setUseIntegerPositions(false);
				font_master.draw(game.batch, charname , 39, 70);
			}
			
			if(sex.equals("M")) {	
				spr_master = gameControl.LoadCharBottomMenu(player, "malecreate");
				spr_master.draw(game.batch);
				spr_master = gameControl.LoadCharUpMenu(player, "malecreate");
				spr_master.draw(game.batch);
				spr_master = gameControl.LoadCharHairsMenu(sex, hair);
				spr_master.draw(game.batch);
			}
			if(sex.equals("F")) {			
				spr_master = gameControl.LoadCharBottomMenu(player, "femalecreate");
				spr_master.draw(game.batch);
				spr_master = gameControl.LoadCharUpMenu(player, "femalecreate");
				spr_master.draw(game.batch);
				spr_master = gameControl.LoadCharHairsMenu(sex, hair);
				spr_master.draw(game.batch);
			}
			
			font_master.draw(game.batch, charname , 39, 70);
			
			//Hair Pallete
			if(sex.equals("M")) {
				spr_master = gameControl.HairPallete("hair1", "M"); spr_master.draw(game.batch);
				spr_master = gameControl.HairPallete("hair2", "M"); spr_master.draw(game.batch);
				spr_master = gameControl.HairPallete("hair3", "M"); spr_master.draw(game.batch);
				spr_master = gameControl.HairPallete("hair4", "M"); spr_master.draw(game.batch);
				spr_master = gameControl.HairPallete("hair5", "M"); spr_master.draw(game.batch);
				spr_master = gameControl.HairPallete("hair6", "M"); spr_master.draw(game.batch);
				spr_master = gameControl.HairPallete("hair7", "M"); spr_master.draw(game.batch);
				spr_master = gameControl.HairPallete("hair8", "M"); spr_master.draw(game.batch);
			}
			if(sex.equals("F")) {
				spr_master = gameControl.HairPallete("hair1", "F"); spr_master.draw(game.batch);
				spr_master = gameControl.HairPallete("hair2", "F"); spr_master.draw(game.batch);
				spr_master = gameControl.HairPallete("hair3", "F"); spr_master.draw(game.batch);
				spr_master = gameControl.HairPallete("hair4", "F"); spr_master.draw(game.batch);
				spr_master = gameControl.HairPallete("hair5", "F"); spr_master.draw(game.batch);
				spr_master = gameControl.HairPallete("hair6", "F"); spr_master.draw(game.batch);
				spr_master = gameControl.HairPallete("hair7", "F"); spr_master.draw(game.batch);
				spr_master = gameControl.HairPallete("hair8", "F"); spr_master.draw(game.batch);
			}
				
		}
		
		
		spr_testdot.setPosition(83,73f);
		spr_testdot.setSize(1,1);
		spr_testdot.draw(game.batch);
		
		spr_testdot.setPosition(97,67);
		spr_testdot.setSize(1,1);
		spr_testdot.draw(game.batch);
			
		game.batch.end();
	}
	
	public void TrainMove() {
		posbackground += 0.02f;
		
		if(posbackground >= 1) {
			posbackground = -1f;
		}
		
		spr_background.setPosition(0,posbackground);
		spr_background.setSize(100,100);
		spr_background.draw(game.batch);
		
	}
	
	@Override
	public boolean touchDown(int p1, int p2, int p3, int p4)
	{
		// TODO: Implement this method
		Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));

		if(state.equals("Main")) {
			if(coordsTouch.x >= 5 && coordsTouch.x <= 21 && coordsTouch.y >= 4 && coordsTouch.y <= 13){
				//Criar
				state = "Create";
				return false;
			}
			if(coordsTouch.x >= 80f && coordsTouch.x <= 97f && coordsTouch.y >= 3 && coordsTouch.y <= 13f){
				//Delete
				state = "Delete";
				return false;
			}
			// char 1
			if(coordsTouch.x >= 10f && coordsTouch.x <= 27 && coordsTouch.y >= 20 && coordsTouch.y <= 59f){
				if(!player.name_1.equals("none")) {
					charselect = 1;
					state = "Selected";
				}
				return false;
			}
			// char 2
			if(coordsTouch.x >= 38 && coordsTouch.x <= 60 && coordsTouch.y >= 20 && coordsTouch.y <= 59f){
				if(!player.name_2.equals("none")) {
					charselect = 2;
					state = "Selected";
				}
				return false;
			}
			// char 3
			if(coordsTouch.x >= 67 && coordsTouch.x <= 90 && coordsTouch.y >= 20 && coordsTouch.y <= 59f){
				if(!player.name_3.equals("none")) {
					charselect = 3;
					state = "Selected";
				}
				return false;
			}
		}
		
		if(state.equals("Delete")) {
			//Voltar
			if(coordsTouch.x >= 40 && coordsTouch.x <= 55 && coordsTouch.y >= 5 && coordsTouch.y <= 12f){
				state = "Main";
				return false;
			}
			
			//delete char 1
			if(coordsTouch.x >= 10f && coordsTouch.x <= 27 && coordsTouch.y >= 20 && coordsTouch.y <= 59f){
				//Delete
				gameControl.DeleteChar(1);
				player = gameControl.GetPlayer();
				state = "Main";
				return false;
			}
			//delete char 2
			if(coordsTouch.x >= 38 && coordsTouch.x <= 60 && coordsTouch.y >= 20 && coordsTouch.y <= 59f){
				//Delete
				gameControl.DeleteChar(2);
				player = gameControl.GetPlayer();
				state = "Main";
				return false;
			}
			//delete char 3
			if(coordsTouch.x >= 67 && coordsTouch.x <= 90 && coordsTouch.y >= 20 && coordsTouch.y <= 59f){
				//Delete
				gameControl.DeleteChar(3);
				player = gameControl.GetPlayer();
				state = "Main";
				return false;
			}
		}
		
		if(state.equals("Selected")) {
			//Voltar
			if(coordsTouch.x >= 40 && coordsTouch.x <= 55 && coordsTouch.y >= 5 && coordsTouch.y <= 12f){
				state = "Main";
				return false;
			}
			//iniciar
			if(coordsTouch.x >= 83 && coordsTouch.x <= 97 && coordsTouch.y >= 67 && coordsTouch.y <= 73f){
				gameControl.SetCharSelected(charselect);
				game.UpdateComponents(gameControl, false);
				game.Switch("MetroStation");
				return false;
			}
		}
		
		if(state.equals("Create")) {
			if(coordsTouch.x >= 37 && coordsTouch.x <= 63 && coordsTouch.y >= 63 && coordsTouch.y <= 72){
				//Nome
				Gdx.input.getTextInput(this, "", "", "", null);
				return false;
			}
			if(coordsTouch.x >= 37 && coordsTouch.x <= 46 && coordsTouch.y >= 49  && coordsTouch.y <= 58.6f){
				//Sex M
				sex = "M";
				return false;
			}
			if(coordsTouch.x >= 47 && coordsTouch.x <= 56 && coordsTouch.y >= 49  && coordsTouch.y <= 58.6f){
				//Sex F
				sex = "F";
				return false;
			}
			if(coordsTouch.x >= 37 && coordsTouch.x <= 43 && coordsTouch.y >= 35  && coordsTouch.y <= 44){
				//Hair 1
				hair = "hair1";
				return false;
			}
			if(coordsTouch.x >= 43.5f && coordsTouch.x <= 49 && coordsTouch.y >= 35  && coordsTouch.y <= 44){
				//Hair 2
				hair = "hair2";
				return false;
			}
			if(coordsTouch.x >= 49.5f && coordsTouch.x <= 55.5f && coordsTouch.y >= 35  && coordsTouch.y <= 44){
				//Hair 3
				hair = "hair3";
				return false;
			}
			if(coordsTouch.x >= 56f && coordsTouch.x <= 61.5f && coordsTouch.y >= 35  && coordsTouch.y <= 44){
				//Hair 4
				hair = "hair4";
				return false;
			}
			if(coordsTouch.x >= 62f && coordsTouch.x <= 67.5f && coordsTouch.y >= 35  && coordsTouch.y <= 44){
				//Hair 5
				hair = "hair5";
				return false;
			}
			if(coordsTouch.x >= 68f && coordsTouch.x <= 74f && coordsTouch.y >= 35  && coordsTouch.y <= 44f){
				//Hair 6
				hair = "hair6";
				return false;
			}
			if(coordsTouch.x >= 74.5f && coordsTouch.x <= 80f && coordsTouch.y >= 35  && coordsTouch.y <= 44f){
				//Hair 7
				hair = "hair7";
				return false;
			}
			if(coordsTouch.x >= 81f && coordsTouch.x <= 86f && coordsTouch.y >= 35  && coordsTouch.y <= 44f){
				//Hair 8
				hair = "hair8";
				return false;
			}
			if(coordsTouch.x >= 13 && coordsTouch.x <= 27 && coordsTouch.y >= 13  && coordsTouch.y <= 21f){
				//btn back
				state = "Main";
				return false;
			}
			if(coordsTouch.x >= 73f && coordsTouch.x <= 87.5f && coordsTouch.y >= 13  && coordsTouch.y <= 21f){
				//btn Create
				CreateNewChar();
				return false;
			}
		}
			
		return false;
	}
	
	public void CreateNewChar() {
		gameControl.CreateNewChar(charname, sex, hair);
		state = "Main";
		player = gameControl.GetPlayer();
	}
	
	public boolean CheckName() {
		
		if(charname.equals("none")){ systemMsg = "Insira um nome"; return false;}
		if(charname.equals("")) { systemMsg = "Insira um nome"; return false; }
		if(charname.contains(".")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("-")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains(";")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("'")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("~")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains(":")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("?")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("!")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("-")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("*")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("=")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("@")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("#")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("$")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("%")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("&")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("(")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains(")")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("=")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("/")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("\\")){ systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("<")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains(">")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.length() > 10) { systemMsg = "Ate 10 letras"; return false; }
		if(charname.length() < 2) { systemMsg = "Menos de 2 letras"; return false; }
		
		return false;
	}
	
	@Override
	public void input(String input) {
		if(state.equals("Create")) {
			if(CheckName()) {
				charname = input;
			}	
		}	
	}
	
	
	@Override
	public void resize(int p1, int p2)
	{
		viewport.update(p1,p2);
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
	}

	@Override
	public void hide()
	{
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
		game.dispose();
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
	public boolean keyDown(int p1)
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
	public boolean keyTyped(char p1)
	{
		// TODO: Implement this method
		return false;
	}
	
	@Override
	public boolean touchUp(int p1, int p2, int p3, int p4)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int p1, int p2, int p3)
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
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

	@Override
	public void canceled() {
		// TODO Auto-generated method stub
	}
}
