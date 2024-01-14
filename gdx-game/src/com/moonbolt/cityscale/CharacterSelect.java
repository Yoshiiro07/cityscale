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
	    private GameObject gameObject;
	    private ManagerScreen screen;
	    private String state = "main";
	    private boolean network = true;
	    
	    //Manager
	    private Json json;
		private FileHandle file;
		private Random randnumber;
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
	    private float playerPosX = 0;
	    private float playerPosY = 0;
	    private GameObject player;
	    private String name = "";
	    private String sex = "M";
	    private String hair = "hair1";
	    private String color = "brown";
	    private String set = "basicset_m";
	    private float posListHairX = -29f;
	    private int posListHairAux = 0;
	    
	    //Sprites
	    private Sprite spr_Background;
	    private Texture tex_Background;
	    
	    //Teste
	    private Texture tex_testeDot;
	    private Sprite spr_testeDot;
	    
	    //Sprites
	    private Sprite spr_loginmenu;
	    private Sprite spr_createmenu;
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
		
		public CharacterSelect(MainGame gameAlt, ManagerScreen screen) {
			this.game = gameAlt;
			this.screen = screen;
			
			//Misc
			randnumber = new Random();
			json = new Json();
			
			//test dot
			tex_testeDot = new Texture(Gdx.files.internal("data/assets/misc/selected.png"));
			spr_testeDot = new Sprite(tex_testeDot);
			
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
				
				//Camera Ajustments
				//cameraCoordsX = playerPosX;
				//cameraCoordsY = playerPosY;
				
				//Follow camera
				if(playerPosX <= -25f) { cameraCoordsX = -25; }
				if(playerPosX >= 175) { cameraCoordsX = 175; }
				if(playerPosY >= 91.5f) { cameraCoordsY = 91.5f; }
				if(playerPosY <= -105) { cameraCoordsY = -105; }
				
				//Update camera and start drawling
				camera.position.set(cameraCoordsX,cameraCoordsY,0);
				camera.update();
			    game.batch.setProjectionMatrix(camera.combined);	    
				game.batch.begin();
				
				//Background	
				spr_Background.setPosition(-70,-70);
				spr_Background.setSize(140, 140);
				spr_Background.draw(game.batch);
				
				
				if(state.equals("main")) {
					//Menus
					spr_loginmenu = atlas_gameUI.createSprite("mainmenu");
					spr_loginmenu.setPosition(15, -60);
					spr_loginmenu.setSize(50,50);
					spr_loginmenu.draw(game.batch);
				}
				
				if(state.equals("create")) {
					//Menus
					spr_createmenu = atlas_gameUI.createSprite("createmenu");
					spr_createmenu.setPosition(-59, -59);
					spr_createmenu.setSize(120,120);
					spr_createmenu.draw(game.batch);
					
					if(sex.equals("M")) {
						spr_player = atlas_basicset.createSprite("u_male_front1");
						spr_player.setPosition(-46f, 10);
						spr_player.setSize(9, 15);
						spr_player.draw(game.batch);
						
						spr_player = atlas_basicset.createSprite("b_male_front1");
						spr_player.setPosition(-51.5f, -5);
						spr_player.setSize(9, 15);
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
				
				if(state.equals("Recover")) {
					//Menus
					spr_createmenu = atlas_gameUI.createSprite("recuperar");
					spr_createmenu.setPosition(-59, -59);
					spr_createmenu.setSize(120,120);
					spr_createmenu.draw(game.batch);
					
					font_master.setColor(Color.WHITE);
					font_master.getData().setScale(0.16f,0.22f);
					font_master.setUseIntegerPositions(false);	
					
					font_master.draw(game.batch, conta, -50 , 38);
					font_master.draw(game.batch, avisoconta , -50 , 22);
				}
				font_master.setColor(Color.WHITE);
				font_master.getData().setScale(0.07f,0.12f);
				font_master.setUseIntegerPositions(false);	
						
				font_master.draw(game.batch, "Versao: 1B" , -60 , -58);
						
				//spr_testeDot.setPosition(-57,-36);
				//spr_testeDot.setSize(1, 1);
				//spr_testeDot.draw(game.batch);

				//spr_testeDot.setPosition(-1, -55);
				//spr_testeDot.setSize(1, 1);
				//spr_testeDot.draw(game.batch);
					
				game.batch.end();
			
		}
		
		public void CheckData() {
			file = Gdx.files.local("SaveData/save.json");
			
			//Creating a new one
			if (!file.exists()) {
					try {
						GameObject player = new GameObject();
						int accNumber = randnumber.nextInt(999999);
						player.AccountID = String.valueOf(accNumber);
						player.Name = "none";
						file.writeString(Base64Coder.encodeString(json.prettyPrint(player)), false);
						state = "create";
						
					} catch (Exception e) 
					{
						String test = e.getMessage();
					}
			}
			
			else 
			{
				FileHandle file = Gdx.files.local("SaveData/save.json");		
				player = json.fromJson(GameObject.class, Base64Coder.decodeString(file.readString()));
				if(player.Name.equals("none")) { state = "create"; } else { this.screen.screenSwitch("GameMap",network); }
			}
		}
		
		private void CreateNewChar() {
			player = new GameObject();
			
			FileHandle file = Gdx.files.local("SaveData/save.json");		
			player = json.fromJson(GameObject.class, Base64Coder.decodeString(file.readString()));
			
			player.Name = name;
			player.Sex = sex;
			player.Level = 1;
			player.Exp = 0;
			player.Job = "Aprendiz";
			player.Map = "MetroStation";
			player.Hp = 100;
			player.Mp = 100;
			player.HpMax = 100;
			player.MpMax = 100;
			player.regenTime = 6000;
			player.regenTimeMax = 6000;
			player.PosX = -0.5f;
			player.PosY = -4;
			player.Walk = "no";
			player.Frame = 1;
			player.Money = 0;
			player.AtkTimer = 200;
			player.AtkTimerMax = 200;
			player.Casting = "no";
			player.Target = "none";
			player.Atk = 9;
			player.Def = 1;
			player.Evasion = 1;
			player.Side = "front";
			player.Set = set;
			player.Hair = hair;
			player.Color = color;
			player.Hat = "none";
			player.Weapon = "basicknife";
			player.Crystal1 = "none";
			player.Crystal2 = "none";
			player.Crystal3 = "none";
			player.Crystal4 = "none";
			player.Crystal5 = "none";
			player.StatusPoint = 0;
			player.Str = 1;
			player.Agi = 1;
			player.Vit = 1;
			player.Dex = 1;
			player.Wis = 1;
			player.Luk = 1;
			player.Res = 1;
			player.Stamina = 100;
			player.StaminaMax = 100;
			player.StaminaTimer = 1200;
			player.buffA = "none";
			player.buffB = "none";
			player.buffC = "none";
			player.BuffTimeA = 0;
			player.BuffTimeB = 0;
			player.BuffTimeC = 0;
			player.party = "none";
			player.playerInBattle = "no";
			player.playerInAttack = "no";
			player.playerInCast = "no";
			player.hotkey1 = "none";
			player.hotkey2 = "none";
			
			String itensList = "";
            for(int i = 0; i < 16; i++) {
                if(i == 0) { itensList = itensList + "[blue_crystal_intextra_3#4]-"; } 
                if(i == 1) {  if(sex.equals("M")) {itensList = itensList + "[basicset_m#1]-"; } else { itensList = itensList + "[basicset_f#1]-"; }}
                if(i == 2) {  itensList = itensList + "[basicknife#1]-"; } 
                if(i > 2) { itensList = itensList + "[NONE]-"; }          
            }
            player.Itens = itensList;
            
            //String itensList = "";
            //for(int i = 0; i < 16; i++) {
            //    if(i == 0) { itensList = itensList + "[hpcan#30]-"; } 
            //    if(i == 1) {  if(sex.equals("M")) {itensList = itensList + "[basicset_m#1]-"; } else { itensList = itensList + "[basicset_f#1]-"; }}
            //    if(i == 2) {  itensList = itensList + "[basicknife#1]-"; } 
            //    if(i > 2) { itensList = itensList + "[NONE]-"; }          
            //}
            //player.Itens = itensList;
            
			SaveData(player);		
			state = "change";
		}
		
		//[Save file data]//
		public void SaveData(GameObject acPlayer) {
			file = Gdx.files.local("SaveData/save.json");
			file.writeString(Base64Coder.encodeString(json.prettyPrint(acPlayer)), false);
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
		
		public String GerenciamentoOnline(String tipoRequisicao, String subData) throws IOException {
			
			String linhaLida = "";
			
			try {
			
				if(tipoRequisicao.equals("Download")) {
					try {			
				        // Construct data
						//Inscricoes para Conexao
				        String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8");
				        data += "&" + URLEncoder.encode("lAccountID", "UTF-8") + "=" + URLEncoder.encode(subData, "UTF-8");
				        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Download", "UTF-8");
				        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(screen.lservername, "UTF-8");
				        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(screen.lusername, "UTF-8");
				        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(screen.lpassword, "UTF-8");
				        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(screen.ldbname, "UTF-8");
				   	    	        
				        // Send data
				        URL url = new URL("http://moonboltprojects.online/Conector/Online.php");
				        URLConnection conn = url.openConnection();
				        conn.setDoOutput(true);
				        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				        wr.write(data);
				        wr.flush();
				 
				        // Get the response
				        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				        String line;
				        while ((line = rd.readLine()) != null) {
				        	if(!line.contains("Inexistente")) {
				        		LoadDownloadData(line);
				        		avisoconta = "Recuperado com sucesso";
				        	}
				        	else {
				        		avisoconta = "Conta nao encontrada";
				        	}
				        }		        
				        wr.close();
				        rd.close();
				    } 
					
					catch (Exception e) { avisoconta = "Operacao falhou"; return "retry";}
				}
					
				return "";
			}
			
			catch(Exception ex) {
				return "retry";
			}		
		}
		
		public void LoadDownloadData(String hash) {
			FileHandle file = Gdx.files.local("SaveData/save.json");
			GameObject player = json.fromJson(GameObject.class,Base64Coder.decodeString(hash));			
			file.writeString(Base64Coder.encodeString(json.prettyPrint(player)),false);
		}
		
		public void DeleteData() {
			FileHandle file = Gdx.files.local("SaveData/save.json");
			file.delete();
		}
	
		@Override
		public void input(String input) {	
			if(state.equals("create")) {
				name = input;
			}
			
			if(state.equals("Recover")) {
				conta = input;
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
			if(state.equals("main")) {
				//Jogar Online
				if(coordsTouch.x >=  + 20 && coordsTouch.x <= +59 && coordsTouch.y >= -28 && coordsTouch.y <= -15) {
					network = true;
					CheckData();
					return false;
				}
								
				//Jogar Offline
				if(coordsTouch.x >=  + 20 && coordsTouch.x <= +59 && coordsTouch.y >= -42 && coordsTouch.y <= -28) {
					network = false;
					CheckData();
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
					state = "main";
				}
				//input acc
				if(coordsTouch.x >= -57 && coordsTouch.x <= -2 && coordsTouch.y >= 25 && coordsTouch.y <= 45) {
					Gdx.input.getTextInput(this,"Digite o numero da conta:","","");
					return false;
				}
				//Recuperar botao
				if(coordsTouch.x >= -1 && coordsTouch.x <= 30 && coordsTouch.y >= 25 && coordsTouch.y <= 45) {
					try {
						GerenciamentoOnline("Download",conta);
					} catch (IOException e) {
						avisoconta = "Nao foi possível efetuar operacao";
					}
				}
				//delete acc
				if(coordsTouch.x >= -57 && coordsTouch.x <= -1 && coordsTouch.y >= -55 && coordsTouch.y <= -36) {
					DeleteData();
					return false;
				}
			}
			
			if(state.equals("create")) {
				//Nome
				if(coordsTouch.x >= cameraCoordsX - 17 && coordsTouch.x <= cameraCoordsX + 14 && coordsTouch.y >= cameraCoordsY + 30 && coordsTouch.y <= cameraCoordsY + 39) {
					Gdx.input.getTextInput(this,"Digite o nome","","");
					return false;
				}		
				//Sex Male
				if(coordsTouch.x >= cameraCoordsX - 17 && coordsTouch.x <= cameraCoordsX -4 && coordsTouch.y >= cameraCoordsY + 16 && coordsTouch.y <= cameraCoordsY + 29) {
					sex = "M";
					hair = "hair1";
					set = "basicset_m";
					return false;
				}
				//Sex Female
				if(coordsTouch.x >= cameraCoordsX - 1 && coordsTouch.x <= cameraCoordsX + 12 && coordsTouch.y >= cameraCoordsY + 16 && coordsTouch.y <= cameraCoordsY + 29) {
					sex = "F";
					hair = "hair1";
					set = "basicset_f";
					return false;
				}
				//Hair1 
				if(coordsTouch.x >=  -28 && coordsTouch.x <=  -18.6f && coordsTouch.y >=  - 15 && coordsTouch.y <=  + 1) {
					hair = "hair1";
					return false;
				}
				//Hair2
				if(coordsTouch.x >=  -17.4f && coordsTouch.x <=  -8.6f && coordsTouch.y >=  - 15 && coordsTouch.y <=  + 1) {
					hair = "hair2";
					return false;
				}
				//Hair3
				if(coordsTouch.x >=  -7 && coordsTouch.x <= 2 && coordsTouch.y >=  - 15 && coordsTouch.y <=  + 1) {
					hair = "hair3";
					return false;
				}
				//Hair4
				if(coordsTouch.x >=  3f && coordsTouch.x <= 13f && coordsTouch.y >=  - 15 && coordsTouch.y <=  + 1) {
					hair = "hair4";
					return false;
				}
				//Hair5
				if(coordsTouch.x >=  14f && coordsTouch.x <=  23f && coordsTouch.y >=  - 15 && coordsTouch.y <=  + 1) {
					hair = "hair5";
					return false;
				}
				//Hair6
				if(coordsTouch.x >=  24f && coordsTouch.x <=  33f && coordsTouch.y >=  - 15 && coordsTouch.y <=  + 1) {
					hair = "hair6";
					return false;
				}
				//Hair7
				if(coordsTouch.x >=  34f && coordsTouch.x <=  44f && coordsTouch.y >=  - 15 && coordsTouch.y <=  + 1) {
					hair = "hair7";
					return false;
				}
				//Hair8
				if(coordsTouch.x >=  45f && coordsTouch.x <=  55f && coordsTouch.y >=  - 15 && coordsTouch.y <=  + 1) {
					hair = "hair8";
					return false;
				}
				
				//Color Orange 
				if(coordsTouch.x >= -28 && coordsTouch.x <= -19 && coordsTouch.y >=  - 41 && coordsTouch.y <= - 25) {
					color = "orange";
					return false;
				}
				//Color Yellow 
				if(coordsTouch.x >= -17 && coordsTouch.x <= -8 && coordsTouch.y >=  - 41 && coordsTouch.y <= - 25) {
					color = "yellow";
					return false;
				}
				//Color Green 
				if(coordsTouch.x >= -7 && coordsTouch.x <= 2 && coordsTouch.y >=  - 41 && coordsTouch.y <= - 25) {
					color = "green";
					return false;
				}
				//Color Red 
				if(coordsTouch.x >= 3 && coordsTouch.x <= 12 && coordsTouch.y >=  - 41 && coordsTouch.y <= - 25) {
					color = "red";
					return false;
				}
				//Color Pink 
				if(coordsTouch.x >= 14 && coordsTouch.x <= 23 && coordsTouch.y >=  - 41 && coordsTouch.y <= - 25) {
					color = "pink";
					return false;
				}
				//Color Brown 
				if(coordsTouch.x >= + 24 && coordsTouch.x <= + 33 && coordsTouch.y >= - 41 && coordsTouch.y <= - 25) {
					color = "brown";
					return false;
				}
				//Confirmar 
				if(coordsTouch.x >= cameraCoordsX + 32 && coordsTouch.x <= cameraCoordsX + 57 && coordsTouch.y >= cameraCoordsY - 55 && coordsTouch.y <= cameraCoordsY - 44) {
					if(!CheckName()) { return false; }	
					CreateNewChar();
					return false;
				}
				//Voltar 
				if(coordsTouch.x >= cameraCoordsX - 57 && coordsTouch.x <= cameraCoordsX - 32 && coordsTouch.y >= cameraCoordsY - 55 && coordsTouch.y <= cameraCoordsY - 44) {
					state = "main";
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
