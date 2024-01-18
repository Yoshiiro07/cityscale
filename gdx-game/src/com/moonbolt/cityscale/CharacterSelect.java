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


public class CharacterSelect implements Screen, ApplicationListener, InputProcessor, TextInputListener {
		//Objects
	    private MainGame game;
	    private GameControl gameControl;
	    private ManagerScreen screen;
	    private String state = "Main";
	    private boolean network = false;
	    
	    
	    //Manager
	    private String systemMsg;
		private String conta = "";
		private String avisoconta = "";
	    
		//Fonts
		private BitmapFont font_master;
		
	    //Camera
	    private OrthographicCamera camera;
	    private Viewport viewport;
	    private float cameraCoordsX = 0;
	    private float cameraCoordsY = 0;
	    
	    //Player
	    private GameObject player;
	    private String name = "";
	    private String sex = "M";
	    private String hair = "hair1";
	    private String color = "brown";
	    private String set = "basicset_m";
	    
	    //Sprites
	    private Sprite spr_Background;
	    private Texture tex_Background;
	    
	    //Teste
	    private Texture tex_testeDot;
	    private Sprite spr_testeDot;
	    
	    //Sprites
	    private Sprite spr_master;
	    private Sprite spr_player;
	    private Sprite spr_hair;
	    
	    //Textures
	    private TextureAtlas atlas_gameUI;
	    private TextureAtlas atlas_basicset;	    
	    
	    private TextureAtlas atlas_hairs1;
	    private TextureAtlas atlas_hairs2;
	    private TextureAtlas atlas_hairs3;
	    
	    //Controller
	    private final IntSet downKeys = new IntSet(20);	
		
		public CharacterSelect(MainGame _game, ManagerScreen _screen,boolean _network) {
			this.game = _game;
			this.screen = _screen;
			this.network = _network;
			this.gameControl = new GameControl();
			
			//test dot
			tex_testeDot = new Texture(Gdx.files.internal("data/assets/misc/selected.png"));
			spr_testeDot = new Sprite(tex_testeDot);
			spr_master = new Sprite(tex_testeDot);
			
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
			
			//Atlas
			atlas_gameUI = new TextureAtlas(Gdx.files.internal("data/assets/UI/uirenew.txt"));
			atlas_basicset = new TextureAtlas(Gdx.files.internal("data/assets/chars/player/sets/basic/basic.txt"));
			atlas_hairs1 = new TextureAtlas(Gdx.files.internal("data/assets/chars/player/hairs/hairs1.txt"));
			atlas_hairs2 = new TextureAtlas(Gdx.files.internal("data/assets/chars/player/hairs/hairs2.txt"));		
			atlas_hairs3 = new TextureAtlas(Gdx.files.internal("data/assets/chars/player/hairs/hairs3.txt"));
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
					//selecione
					spr_master = atlas_gameUI.createSprite("selecionepersonagem");
					spr_master.setPosition(-65, 50);
					spr_master.setSize(50,8);
					spr_master.draw(game.batch);
					
					//btn criar
					spr_master = atlas_gameUI.createSprite("btncriar");
					spr_master.setPosition(-60, -60);
					spr_master.setSize(20,10);
					spr_master.draw(game.batch);
					
					//btn criar
					spr_master = atlas_gameUI.createSprite("btnexcluir");
					spr_master.setPosition(40, -60);
					spr_master.setSize(20,10);
					spr_master.draw(game.batch);
				}
				
				if(state.equals("Create")) {
					//Menus
					spr_master = atlas_gameUI.createSprite("createmenu");
					spr_master.setPosition(-59, -59);
					spr_master.setSize(120,120);
					spr_master.draw(game.batch);
					
					if(sex.equals("M")) {
						spr_player = atlas_basicset.createSprite("u_male_front1");
						spr_player.setPosition(-47.8f, 9);
						spr_player.setSize(12, 19);
						spr_player.draw(game.batch);
						
						spr_player = atlas_basicset.createSprite("b_male_front1");
						spr_player.setPosition(-47f, -1.5f);
						spr_player.setSize(11, 17);
						spr_player.draw(game.batch);
						
						if(hair.equals("hair1")) { spr_hair = atlas_hairs1.createSprite(hair + color + sex + "Front"); }
						if(hair.equals("hair2")) { spr_hair = atlas_hairs2.createSprite(hair + color + sex + "Front"); }
						
						spr_hair.setPosition(-47.6f, 17.9f);
						spr_hair.setSize(12, 19);
						spr_hair.draw(game.batch);
						
					}
					else {
						spr_player = atlas_basicset.createSprite("u_female_front1");
						spr_player.setPosition(-51.5f, -5);
						spr_player.setSize(20, 35);
						spr_player.draw(game.batch);
						
						spr_player = atlas_basicset.createSprite("b_female_front1");
						spr_player.setPosition(-51.5f, -5);
						spr_player.setSize(20, 35);
						spr_player.draw(game.batch);
						
						if(hair.equals("hair1")) { spr_hair = atlas_hairs1.createSprite(hair + color + sex + "Front"); }
						if(hair.equals("hair2")) { spr_hair = atlas_hairs2.createSprite(hair + color + sex + "Front"); }
	
						spr_hair.setPosition(-47.3f, 17.8f);
						spr_hair.setSize(12, 19);
						spr_hair.draw(game.batch);
					}
					
					//List of hairs
					if(sex.equals("M")) {					
						spr_hair = atlas_hairs1.createSprite("hair" + 1 + color + "MFront");
						spr_hair.setSize(12, 19);
						spr_hair.setPosition(-29f, -16); spr_hair.draw(game.batch); 
						
						spr_hair = atlas_hairs2.createSprite("hair" + 2 + color + "MFront");
						spr_hair.setSize(12, 19);
						spr_hair.setPosition(-18.5f, -16); spr_hair.draw(game.batch); 
						
						spr_hair = atlas_hairs3.createSprite("hair" + 3 + color + "MFront");
						spr_hair.setSize(12, 19);
						spr_hair.setPosition(-08f, -16); spr_hair.draw(game.batch); 
						
						//spr_hair.setPosition( 2f, -16); spr_hair.draw(game.batch); 
						//spr_hair.setPosition(12.6f, -16); spr_hair.draw(game.batch); 
						//spr_hair.setPosition(22.9f, -16); spr_hair.draw(game.batch); 
						//spr_hair.setPosition(33.5f, -16); spr_hair.draw(game.batch); 
						//spr_hair.setPosition(44f, -16); spr_hair.draw(game.batch); 	
					}
					if(sex.equals("F")) {
						spr_hair = atlas_hairs1.createSprite("hair" + 1 + color + "FFront");
						spr_hair.setSize(12, 19);
						spr_hair.setPosition(-29f, -16); spr_hair.draw(game.batch); 
						
						spr_hair = atlas_hairs2.createSprite("hair" + 2 + color + "FFront");
						spr_hair.setSize(12, 19);
						spr_hair.setPosition(-18.5f, -16); spr_hair.draw(game.batch); 
						
						spr_hair = atlas_hairs3.createSprite("hair" + 3 + color + "FFront");
						spr_hair.setSize(12, 19);
						spr_hair.setPosition(-08f, -16); spr_hair.draw(game.batch); 
						
						//spr_hair.setPosition( 2f, -16); spr_hair.draw(game.batch); 
						//spr_hair.setPosition(12.6f, -16); spr_hair.draw(game.batch); 
						//spr_hair.setPosition(22.9f, -16); spr_hair.draw(game.batch); 
						//spr_hair.setPosition(33.5f, -16); spr_hair.draw(game.batch); 
						//spr_hair.setPosition(44f, -16); spr_hair.draw(game.batch);					
					}
					
					font_master.draw(game.batch, name , cameraCoordsX - 17 , cameraCoordsY + 39);
				}
				
				if(state.equals("change")) {
					this.screen.screenSwitch("LoadingScreen", network);
				}
				
				if(state.equals("Delete")) {
					
				}
						
				spr_testeDot.setPosition(-60,-50);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);

				spr_testeDot.setPosition(-40, -60);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);
					
				game.batch.end();
			
		}
		
		private boolean CheckName() {
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
			if(name.contains("\\")){ systemMsg = "Nome com caracters especiais"; return false; }
			if(name.contains("<")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(name.contains(">")) { systemMsg = "Nome com caracters especiais"; return false; }
			if(name.length() > 10) { systemMsg = "Ate 10 letras"; return false; }
			
			return true;
		}
	
		@Override
		public void input(String input) {	
			if(state.equals("create")) {
				name = input;
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
				//Create
				if(coordsTouch.x >=  -60 && coordsTouch.x <= -40 && coordsTouch.y >= -60 && coordsTouch.y <= -50) {
					state = "Create";
					return false;
				}
								
				//Delete
				if(coordsTouch.x >=  + 20 && coordsTouch.x <= +59 && coordsTouch.y >= -42 && coordsTouch.y <= -28) {
					state = "Delete";			
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
