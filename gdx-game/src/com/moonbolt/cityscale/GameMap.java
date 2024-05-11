package com.moonbolt.cityscale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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


public class GameMap implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	
		//Objects
	    private MainGame game;
	    private ManagerScreen screen;
	    private GameControl gameControl;
	    private String state = "Main";
	    private Sprite spr_master;
		private Random randnumber;
	    
		//Fonts
		private BitmapFont font_master;
		
	    //Camera
	    private OrthographicCamera camera;
	    private Viewport viewport;
	    private float cameraCoordsX = 0;
	    private float cameraCoordsY = 0;
	    
	    //Player
	    public Player player;
	    private float playerPosX;
	    private float playerPosY;
	    private Sprite spr_playerTagHair;
	    private Sprite spr_playerhair;
	    private Sprite spr_playertop;
	    private Sprite spr_playerbottom;
	    private Sprite spr_playerfooter;
	    private boolean playerDead = false;
	    private boolean movement = false;
		private Sprite spr_target;

		//Monster
		private ArrayList<Monster> listMonsters;
		private Sprite spr_monster;
		private int mobFrame = 1;
	    private float mobPositionCoordX = 0;
	    private float mobPositionCoordY = 0;
	    private int mobRandomSt = 0;
	    private int mobTimerMov = 100;
	    private int mobTimerFrame = 40;
		private int countSwitchTarget;
	    
	    //Sprite NPC
	    private Sprite spr_npc;
	    
	    //UX
	    private float padmoveX = -56;
	    private float padmoveY =  -50;
	    private Sprite spr_playerTag;
	     
	    //Sprites Background
	    private Sprite spr_Background;
	    private Texture tex_Background;
	    
	    //Teste Dot
	    private Sprite spr_testeDot;
	    private Texture tex_testeDot;
	    private float testX;
	    private float testY;
	    
	    //Controller
	    private final IntSet downKeys = new IntSet(20);	
		
		public GameMap(MainGame gameAlt,ManagerScreen screenAlt, boolean network) {
			this.game = gameAlt;	
			this.screen = screenAlt;
			this.randnumber = new Random();
			
			//Load Player Data
			this.gameControl = new GameControl();
			player = gameControl.LoadData();
			
			if(player.Map_A.equals("StreetsA")) { tex_Background = new Texture(Gdx.files.internal("data/assets/maps/streetsA.png")); }	
			if(player.Map_A.equals("Sewers")) { tex_Background = new Texture(Gdx.files.internal("data/assets/maps/sewers.png")); }	
			spr_Background = new Sprite(tex_Background);
			
			//Mobs
			if(player.Map_A.equals("Sewers")) {
				listMonsters = gameControl.LoadMonsters("Sewers");
			}
					
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
			try {
				//Just for coloring
				Gdx.gl.glClearColor(1,1,1,1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
				//Camera Ajustments
				cameraCoordsX = player.PosX_A;
				cameraCoordsY = player.PosY_A;
				
				//Follow camera
				if(player.PosX_A <= 18.5f) 	{ cameraCoordsX = 18.5f; 	 }
				if(player.PosX_A >= 93) 	{ cameraCoordsX = 93; 	 }
				if(player.PosY_A >= -22f) { cameraCoordsY = -22f; }
				if(player.PosY_A <= -97) 	{ cameraCoordsY = -97;  }
				
				//Update camera and start drawling
				camera.position.set(cameraCoordsX -2,cameraCoordsY+1,0);
				camera.update();
			    game.batch.setProjectionMatrix(camera.combined);	    
				game.batch.begin();
				
				//Background	
				spr_Background.setPosition(-81,-194);
				spr_Background.setSize(270, 270);
				spr_Background.draw(game.batch);
				
				//npcs
				ShowNPCs();
				
				//Char
				player = gameControl.SetCharMov(player, player.breakwalk_A);
				spr_playerhair = gameControl.GetHairChar(player, "no",0,0);
				spr_playerhair.draw(game.batch);
				
				spr_playerfooter = gameControl.GetFooterChar(player, "no",0,0);
				spr_playerfooter.draw(game.batch);
				
				spr_playerbottom = gameControl.GetBottomChar(player, "no",0,0);
				spr_playerbottom.draw(game.batch);
				
				spr_playertop = gameControl.GetTopChar(player, "no", 0,0);
				spr_playertop.draw(game.batch);

				//Show Mobs
				if(player.Map_A.equals("Sewers")){
					ShowMobs();
				}
				
				//UX
				spr_playerTag = gameControl.GetUX("playertag",cameraCoordsX, cameraCoordsY);
				spr_playerTag.draw(game.batch);
				
				spr_playerTagHair = gameControl.GetHairCharTag(player, cameraCoordsX, cameraCoordsY);
				spr_playerTagHair.draw(game.batch);
				
				
				font_master.getData().setScale(0.15f,0.26f);
				font_master.draw(game.batch, "X:" + player.PosX_A, cameraCoordsX - 98f, cameraCoordsY + 53.7f);
				font_master.draw(game.batch, "Y:" + player.PosY_A, cameraCoordsX - 78f, cameraCoordsY + 53.7f);
				
				
				font_master.draw(game.batch, player.Name_A, cameraCoordsX - 88f, cameraCoordsY + 93.7f);
				font_master.draw(game.batch, String.valueOf(player.Hp_A + "/" + player.HpMax_A), cameraCoordsX - 85f, cameraCoordsY + 82f);
				font_master.draw(game.batch, String.valueOf(player.Mp_A + "/" + player.MpMax_A), cameraCoordsX - 85f, cameraCoordsY + 73.7f);
				font_master.draw(game.batch, String.valueOf(player.Level_A), cameraCoordsX - 88f, cameraCoordsY + 65.7f);
				font_master.draw(game.batch, String.valueOf(player.Exp_A) + "%", cameraCoordsX - 69f, cameraCoordsY + 65.7f);
				
				spr_master = gameControl.GetUX("innerpad", cameraCoordsX, cameraCoordsY);
				spr_master.setPosition(cameraCoordsX + padmoveX,cameraCoordsY + padmoveY);
				spr_master.draw(game.batch);
				
				ShowCards();
				
				//Colision
				CheckColision();

				if(state.equals("menu")){
					spr_master = gameControl.GetUX("menu", cameraCoordsX, cameraCoordsY);
					spr_master.draw(game.batch);
					//Status
					font_master.draw(game.batch, String.valueOf(player.Str_A), cameraCoordsX - 76f, cameraCoordsY - 50f);
					font_master.draw(game.batch, String.valueOf(player.Vit_A), cameraCoordsX - 62f, cameraCoordsY - 50f);
					font_master.draw(game.batch, String.valueOf(player.Agi_A), cameraCoordsX - 48f, cameraCoordsY - 50f);
					font_master.draw(game.batch, String.valueOf(player.Wis_A), cameraCoordsX - 34f, cameraCoordsY - 50f);
					font_master.draw(game.batch, String.valueOf(player.Dex_A), cameraCoordsX - 21f, cameraCoordsY - 50f);

					//Show Character
					//Itens Equipped
					spr_playerhair = gameControl.GetHairChar(player, "yes", cameraCoordsX, cameraCoordsY);
					spr_playerhair.draw(game.batch);
					
					spr_playerfooter = gameControl.GetFooterChar(player, "yes", cameraCoordsX, cameraCoordsY);
					spr_playerfooter.draw(game.batch);
					
					spr_playerbottom = gameControl.GetBottomChar(player, "yes", cameraCoordsX, cameraCoordsY);
					spr_playerbottom.draw(game.batch);
					
					spr_playertop = gameControl.GetTopChar(player, "yes", cameraCoordsX, cameraCoordsY);
					spr_playertop.draw(game.batch);

					//CharacterShow
					spr_playerhair = gameControl.GetHairChar(player, "Show", cameraCoordsX, cameraCoordsY);
					spr_playerhair.draw(game.batch);
					
					spr_playerfooter = gameControl.GetFooterChar(player, "Show", cameraCoordsX, cameraCoordsY);
					spr_playerfooter.draw(game.batch);
					
					spr_playerbottom = gameControl.GetBottomChar(player, "Show", cameraCoordsX, cameraCoordsY);
					spr_playerbottom.draw(game.batch);
					
					spr_playertop = gameControl.GetTopChar(player, "Show", cameraCoordsX, cameraCoordsY);
					spr_playertop.draw(game.batch);
					
				}

				
				if(state.equals("DungeonSelect")) {
					spr_master = gameControl.GetUX("battlezoneA", cameraCoordsX, cameraCoordsY);
					spr_master.draw(game.batch);
				}
				
				spr_testeDot.setPosition(cameraCoordsX + 68,cameraCoordsY + 84);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);

				spr_testeDot.setPosition(cameraCoordsX + 82,cameraCoordsY + 69);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);
				
				
				game.batch.end();
			}
			
			catch(Exception ex) {
				Gdx.app.exit();
			}
		}
		
		public void MapChange(String map) {
			if(map.equals("Sewers")) {
				player.Map_A = "Sewers";
				player.PosX_A = 44.5f;
				player.PosY_A = -4.5f;
				gameControl.SaveData(player);
				this.screen.screenSwitch("LoadingScreen",false);
				dispose();	
			}
			if(map.equals("StreetsAFromSewers")) {
				player.Map_A = "StreetsA";
				player.PosX_A = 112.5f;
				player.PosY_A = -142f;
				gameControl.SaveData(player);
				this.screen.screenSwitch("LoadingScreen",false);
				dispose();	
			}
		}
		
		public void ShowNPCs() {
			//NPCs
			if(player.Map_A.equals("StreetsA")) {
				spr_npc = gameControl.GetNPC("DungeonMaster", 0);
				spr_npc.draw(game.batch);

				spr_master = gameControl.GetUX("textbar", 0, 0);
				spr_master.setSize(20,10);
				spr_master.setPosition(102, -90);
				spr_master.draw(game.batch);

				font_master.draw(game.batch, "Arenas", 105, -81);
			}
		}
		
		public void CheckColision() {
			if(player.Map_A.equals("StreetsA")) {
				if(player.PosY_A < -192.5f) {
					player.breakwalk_A = "front";
				}
				if(player.PosX_A > 184.5f) {
					player.breakwalk_A = "right";
				}
				if(player.PosX_A < -77) {
					player.breakwalk_A = "left";
				}
			}
			if(player.Map_A.equals("Sewers")) {
				if(player.PosX_A > 40f && player.PosX_A < 53 && player.PosY_A > 5 && player.PosY_A < 21.5f) {
					MapChange("StreetsAFromSewers");
				}
			}
		}
		
		public void CheckAction() {
			if(player.Map_A.equals("StreetsA")) {
				if(player.PosX_A >= 103.5f && player.PosX_A <= 122 && player.PosY_A >= -142 && player.PosY_A <= -128.5f ) {
					state = "DungeonSelect";
				}
			}
		}
		
		public void ChangeTarget() {
			
			if(player.Map_A.equals("Sewers")) {
			
				String playerTarget = player.Target_A;
				for(int i = 0; i < listMonsters.size(); i++) {
					
					if(countSwitchTarget == 0) {
						countSwitchTarget = i;
					}
					
					if(countSwitchTarget >= 0) {
						if(countSwitchTarget <= listMonsters.size()) {
							countSwitchTarget++;
							if(countSwitchTarget >= listMonsters.size()) { countSwitchTarget = 0; }
							if(!playerTarget.equals(listMonsters.get(countSwitchTarget).MobID)) {
								player.Target_A = listMonsters.get(countSwitchTarget).MobID;						
								return;		
							}
						}
						else {
							countSwitchTarget = 0;
						}
					}	
				}
			}
		}

		public void CheckBlock() {
			
		}

		public void ShowMobs() {			
			if(player.Map_A.equals("Sewers") || player.Map_A.equals("Watercave") || player.Map_A.equals("Mines") || player.Map_A.equals("Snowpalace") || player.Map_A.equals("Tower")) {
				for(int i = 0; i < listMonsters.size(); i++) {
					
						//Target do player
						if(player.Target_A.equals(listMonsters.get(i).MobID)) {
							spr_target = gameControl.GetUX("target", 0, 0);
							spr_target.setPosition(listMonsters.get(i).MobPosX + 2, listMonsters.get(i).MobPosY + 16);
							spr_target.setSize(4,8);
							spr_target.draw(game.batch);
						}
									
						mobTimerFrame = listMonsters.get(i).MobFrameTime;
						if(mobTimerFrame > 0) {
							mobTimerFrame--;
							listMonsters.get(i).MobFrameTime = mobTimerFrame;
						}
						if(mobTimerFrame <= 0) {
							mobTimerFrame = 40;
							mobFrame = listMonsters.get(i).MobFrame;
							mobFrame++;
							if(mobFrame > 3) { mobFrame = 1;}
							listMonsters.get(i).MobFrame = mobFrame;
							listMonsters.get(i).MobFrameTime = 40;
						}
						
						//Sem Target
						if(listMonsters.get(i).MobTarget.equals("none")) {
							mobTimerMov = listMonsters.get(i).MobTimerMov;
							mobRandomSt = listMonsters.get(i).MobRandomSt;
							if(mobTimerMov >= 0) { 
								mobTimerMov--;
								listMonsters.get(i).MobTimerMov = mobTimerMov;
							}
							if(mobTimerMov < 0) { 
								mobRandomSt = randnumber.nextInt(4); 
								mobTimerMov = 100; 
								listMonsters.get(i).MobTimerMov = mobTimerMov;
								listMonsters.get(i).MobRandomSt = mobRandomSt;
							}
												
							if(mobRandomSt == 0) { 
								mobPositionCoordX = listMonsters.get(i).MobPosX;
								mobPositionCoordX = mobPositionCoordX + 0.07f; 
								listMonsters.get(i).MobPosX = mobPositionCoordX;
							}
							if(mobRandomSt == 1) { 
								mobPositionCoordX = listMonsters.get(i).MobPosX;
								mobPositionCoordX = mobPositionCoordX - 0.07f;
								listMonsters.get(i).MobPosX = mobPositionCoordX;
							}
							if(mobRandomSt == 2) { 
								mobPositionCoordY = listMonsters.get(i).MobPosY;
								mobPositionCoordY = mobPositionCoordY + 0.07f;
								listMonsters.get(i).MobPosY = mobPositionCoordY;
							}
							if(mobRandomSt == 3) { 
								mobPositionCoordY = listMonsters.get(i).MobPosY;
								mobPositionCoordY = mobPositionCoordY - 0.07f;
								listMonsters.get(i).MobPosY = mobPositionCoordY;
							}
						}
						
						if(listMonsters.get(i).MobTarget.equals(player.Name_A)) {
							if(listMonsters.get(i).MobPosX < player.PosX_A + 3) { listMonsters.get(i).MobPosX = listMonsters.get(i).MobPosX + 0.07f; }
							if(listMonsters.get(i).MobPosX > player.PosX_A + 3) { listMonsters.get(i).MobPosX = listMonsters.get(i).MobPosX - 0.07f; }
							if(listMonsters.get(i).MobPosY < player.PosY_A - 6 ) { listMonsters.get(i).MobPosY = listMonsters.get(i).MobPosY + 0.07f; }
							if(listMonsters.get(i).MobPosY > player.PosY_A - 6) { listMonsters.get(i).MobPosY = listMonsters.get(i).MobPosY - 0.07f; }
						}
						
						//Limit screen
						if(mobPositionCoordY >= 18f && listMonsters.get(i).MobDead.equals("no")) 
						{						
							listMonsters.get(i).MobPosY = -112f;
							listMonsters.get(i).MobPosX = 96.5f;
						}
						if(mobPositionCoordY <= -190.5f && listMonsters.get(i).MobDead.equals("no")) 
						{
							listMonsters.get(i).MobPosY = -112f;
							listMonsters.get(i).MobPosX = 67;
						}
						if(mobPositionCoordX >= 185 && listMonsters.get(i).MobDead.equals("no")) 
						{
							listMonsters.get(i).MobPosY = -112f;
							listMonsters.get(i).MobPosX = 65;
						}
						if(mobPositionCoordX <= -77f && listMonsters.get(i).MobDead.equals("no")) 
						{
							listMonsters.get(i).MobPosY = -112f;
							listMonsters.get(i).MobPosX = 65;
						}
						
						if(player.Map_A.equals("Sewers")) { spr_monster = gameControl.GetMonster(listMonsters.get(i).MobName, listMonsters.get(i).MobFrame, "L");}
						//if(player.Map_A.equals("Watercave")) { spr_monster = atlas_mobWatercave.createSprite(listMonsters.get(i).MobName + listMonsters.get(i).MobFrame + "L"); }
						//if(player.Map_A.equals("Mines")) { spr_monster = atlas_mobMines.createSprite(listMonsters.get(i).MobName + listMonsters.get(i).MobFrame + "L"); }
						//if(player.Map_A.equals("Snowpalace")) { spr_monster = atlas_mobSnowpalace.createSprite(listMonsters.get(i).MobName + listMonsters.get(i).MobFrame + "L"); }
						//if(player.Map_A.equals("Tower")) { spr_monster = atlas_mobTower.createSprite(listMonsters.get(i).MobName + listMonsters.get(i).MobFrame + "L"); }
						spr_monster.setPosition(listMonsters.get(i).MobPosX, listMonsters.get(i).MobPosY);
						spr_monster.setSize(listMonsters.get(i).MobSizeX, listMonsters.get(i).MobSizeY);
						spr_monster.draw(game.batch);
									
						mobPositionCoordX = listMonsters.get(i).MobPosX;
						mobPositionCoordY = listMonsters.get(i).MobPosY;
						mobPositionCoordY = mobPositionCoordY - 0.2f;
						font_master.draw(game.batch, listMonsters.get(i).MobName + " HP :" + listMonsters.get(i).MobHp + "/" + listMonsters.get(i).MobHpMax ,mobPositionCoordX, mobPositionCoordY);
					}			
			}
		}
		
		
		public void ShowCards() {
			//Basic Cards
			//Hotkey 1 / 2
			spr_master = gameControl.GetCard("cardempty");
			spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 30);
			spr_master.draw(game.batch);
			
			spr_master = gameControl.GetCard("cardempty");
			spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 30);
			spr_master.draw(game.batch);
			
			spr_master = gameControl.GetCard("cardaction");
			spr_master.setPosition(cameraCoordsX + 47, cameraCoordsY - 60);
			spr_master.draw(game.batch);
			
			spr_master = gameControl.GetCard("cardtarget");
			spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 60);
			spr_master.draw(game.batch);
			
			spr_master = gameControl.GetCard("cardblock");
			spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 60);
			spr_master.draw(game.batch);
			
			spr_master = gameControl.GetCard("cardsit");
			spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY + 0);
			spr_master.draw(game.batch);
			
			//Novice Cards
			spr_master = gameControl.GetCard("cardcutbreak");
			spr_master.setPosition(cameraCoordsX + 47, cameraCoordsY - 90);
			spr_master.draw(game.batch);
			
			spr_master = gameControl.GetCard("cardrockbound");
			spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 90);
			spr_master.draw(game.batch);
			
			spr_master = gameControl.GetCard("cardempty");
			spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 90);
			spr_master.draw(game.batch);
		}
		

		@Override
		public void input(String text) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void canceled() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean keyDown(int keycode) {
			if(playerDead) { return false; }
			
			if(state.equals("Main")) {
				movement = true;		
				downKeys.add(keycode);
		        if (downKeys.size >= 2){
		            onMultipleKeysDown(keycode);
		        }
		        if(downKeys.size == 1) {
		        	if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
		        		player.Side_A = "left";
		        		player.Walk_A = "walk"; 
		        		padmoveX = -85;
		        		player.playerInBattle_A = "no";
		        		return false;
		            }
		    		
		    		if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
		    			player.Side_A = "back";
		    			player.Walk_A = "walk";
		    			padmoveY = -65;
		    			player.playerInBattle_A = "no";
		    			return false;
		            }
		    		
		    		if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
		    			player.Side_A = "front";
		    			player.Walk_A = "walk";	
		    			padmoveY = -85;
		    			player.playerInBattle_A = "no";
		    			return false;
		            }
		    		
		    		if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
		    			player.Side_A = "right";
		    			player.Walk_A = "walk";
		    			padmoveX = -75;
		    			player.playerInBattle_A = "no";
		    			return false;
		            } 
		        }
			}
			
			
			
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			movement = false;
			downKeys.remove(keycode);
			player.Walk_A = "no";
			player.Frame_A = 1;
			padmoveX = -80;
			padmoveY = -75;
			player.breakwalk_A = "";
			
			if(player.Side_A.equals("left-front")) { player.Side_A = "front"; }
			if(player.Side_A.equals("left-back")) { player.Side_A = "front";}
			if(player.Side_A.equals("right-back")) { player.Side_A = "front";}
			if(player.Side_A.equals("right-front")) { player.Side_A = "front";}
			if(player.Side_A.equals("back-right")) { player.Side_A = "front";}
			if(player.Side_A.equals("back-left")) { player.Side_A = "front";}
			if(player.Side_A.equals("front-right")) { player.Side_A = "front";}
			if(player.Side_A.equals("front-left")) { player.Side_A = "front"; }
			
			return false;
		}
		
		private void onMultipleKeysDown (int mostRecentKeycode){
			
			if(state.equals("menu")) { return; }
			
			//For multiple key presses
		    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
		        	//player.Side_A = "left-front";
		        	player.Side_A = "left";
		        	player.Walk_A = "walk";  	
		        	padmoveX = -66;
		        	padmoveY = -60;
		        	player.playerInBattle_A = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
		        	//player.Side_A = "left-back";
		        	player.Side_A = "left";
		        	player.Walk_A = "walk";
		        	padmoveX = -66;
		        	padmoveY = -50; 
		        	player.playerInBattle_A = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
		    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
		    		//player.Side_A = "right-back";
		    		player.Side_A = "right";
		    		player.Walk_A = "walk";	
		    		padmoveX = -55;
		    		padmoveY = -50;
		    		player.playerInBattle_A = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
		    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
		    		//player.Side_A = "right-front";
		    		player.Side_A = "right";
		    		player.Walk_A = "walk";	
		    		padmoveX = -55;
		    		padmoveY = -60;
		    		player.playerInBattle_A = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
		        	//player.Side_A = "back-right";
		        	player.Side_A = "back";
		        	player.Walk_A = "walk";
		        	padmoveX = -55;
		        	padmoveY = -50;
		        	player.playerInBattle_A = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
		        	//player.Side_A = "back-left";
		        	player.Side_A = "back";
		        	player.Walk_A = "walk";
		        	padmoveX = -66;
		        	padmoveY = -50;
		        	player.playerInBattle_A = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
		        	//player.Side_A = "front-right";
		        	player.Side_A = "front";
		        	player.Walk_A = "walk";
		        	padmoveX = -55;
		        	padmoveY = -60;
		        	player.playerInBattle_A = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
		        	//player.Side_A = "front-left";
		        	player.Side_A = "front";
		        	player.Walk_A = "walk";	
		        	padmoveX = -66;
		        	padmoveY = -60;
		        	player.playerInBattle_A = "no";
		        }
		    }
		}	

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int p1, int p2, int pointer, int button) {
			
			if(playerDead) { return false; }
			
			Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));
			
			//Main
			//[Main State]//
			if(state.equals("Main")) {
				if(player.playerInCast_A.equals("none")) { movement = true; } else { movement = false; }
				
				//Menu
				if(coordsTouch.x > cameraCoordsX - 99 && coordsTouch.x < cameraCoordsX - 61 && coordsTouch.y > cameraCoordsY + 57 && coordsTouch.y < cameraCoordsY + 96) {
					state = "menu";
					return false;
				}
				
				
				//Action
				if(coordsTouch.x > cameraCoordsX + 46 && coordsTouch.x < cameraCoordsX + 57 && coordsTouch.y > cameraCoordsY -60 && coordsTouch.y < cameraCoordsY -35) {
					CheckAction();
					return false;
				}
				//Block
				if(coordsTouch.x > cameraCoordsX + 62 && coordsTouch.x < cameraCoordsX + 73 && coordsTouch.y > cameraCoordsY -60 && coordsTouch.y < cameraCoordsY -35) {
					CheckBlock();
					return false;
				}
				//Target
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY -60 && coordsTouch.y < cameraCoordsY -35) {
					ChangeTarget();
					return false;
				}
			}
			
			if(state.equals("DungeonSelect")) {
				//Dungeon 1
				if(coordsTouch.x > cameraCoordsX - 47 && coordsTouch.x < cameraCoordsX + 32 && coordsTouch.y > cameraCoordsY + 17 && coordsTouch.y < cameraCoordsY + 34) {
					MapChange("Sewers");
					return false;
				}
				//Voltar
				if(coordsTouch.x > cameraCoordsX - 21 && coordsTouch.x < cameraCoordsX + 12 && coordsTouch.y > cameraCoordsY - 42 && coordsTouch.y < cameraCoordsY - 24) {
					state = "Main";
					return false;
				}
			}
			//[Menu State]//
			//Action
			if(state.equals("menu")) {
				if(coordsTouch.x > cameraCoordsX + 68 && coordsTouch.x < cameraCoordsX + 82 && coordsTouch.y > cameraCoordsY + 69 && coordsTouch.y < cameraCoordsY + 84) {
					state = "Main";
					return false;
				}
			}
			
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			movement = false;
			player.breakwalk_A = "";
			player.Walk_A = "no";
			padmoveX = -80;
			padmoveY = -75;
			
			if(player.Side_A.equals("left-front")) { player.Side_A = "front"; }
			if(player.Side_A.equals("left-back")) { player.Side_A = "front";}
			if(player.Side_A.equals("right-back")) { player.Side_A = "front";}
			if(player.Side_A.equals("right-front")) { player.Side_A = "front";}
			if(player.Side_A.equals("back-right")) { player.Side_A = "front";}
			if(player.Side_A.equals("back-left")) { player.Side_A = "front";}
			if(player.Side_A.equals("front-right")) { player.Side_A = "front";}
			if(player.Side_A.equals("front-left")) { player.Side_A = "front"; }
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			if(playerDead) { return false; }
			
			Vector3 coordsTouch = camera.unproject(new Vector3(screenX,screenY,0));
			
			if(movement){	
				//Right
     				if(coordsTouch.x >= cameraCoordsX -70 && coordsTouch.x <= cameraCoordsX -50 && coordsTouch.y > cameraCoordsY - 70 && coordsTouch.y < cameraCoordsY - 48) {
					player.Side_A = "right";
					player.Walk_A = "walk";	
					padmoveX = -75;
					player.playerInBattle_A = "no";
					return false;
				}
				//Left
     				if(coordsTouch.x >= cameraCoordsX -90 && coordsTouch.x <= cameraCoordsX -50 && coordsTouch.y > cameraCoordsY - 70 && coordsTouch.y < cameraCoordsY - 48) {
					player.Side_A = "left";
					player.Walk_A = "walk";	
					padmoveX = -85;	
					player.playerInBattle_A = "no";
					return false;
				}
				//Front
				if(coordsTouch.x > cameraCoordsX -80 && coordsTouch.x < cameraCoordsX -60 && coordsTouch.y > cameraCoordsY - 94 && coordsTouch.y < cameraCoordsY - 58) {
					player.Side_A = "front";
					player.Walk_A = "walk";	
					padmoveY = -85;			
					player.playerInBattle_A = "no";
					return false;
				}
				//Back
				if(coordsTouch.x > cameraCoordsX -80 && coordsTouch.x < cameraCoordsX -60 && coordsTouch.y > cameraCoordsY - 58 && coordsTouch.y < cameraCoordsY - 24) {
					player.Side_A = "back";
					player.Walk_A = "walk";	
					padmoveY = -65;
					player.playerInBattle_A = "no";
					return false;
				}
			}
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
		public void resize(int p1, int p2) {
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
