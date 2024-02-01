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
	    private boolean network = false;
	    private String state = "Main";
	    private Sprite spr_master;
	    private String SysMsg = "";
	    private int SysMsgCount = 0;    
	    private int savedataTime = 500;
	    private Random randnumber;
	    
		//Fonts
		private BitmapFont font_master;
		
	    //Camera
	    private OrthographicCamera camera;
	    private Viewport viewport;
	    private float cameraCoordsX = 0;
	    private float cameraCoordsY = 0;
	    
	    //Player	   
	    private Player player;
	    private float playerPosX;
	    private float playerPosY;
	    private float touchPosX;
	    private float touchPosY;
	    private int showPosX = 0;
	    private int showPosY = 0;
	    private boolean movement = false;	
	    private Sprite spr_playerUpper;
	    private Sprite spr_playerBottom;
	    private Sprite spr_playerHair;
	    private boolean AutoAttack = false;
	    private int countSwitchTarget = 0;
	    private float playerPosXSelective = 0;
	    private float playerPosYSelective = 0;
	     
	    //Mob
	    private Monster mob;
	    private ArrayList<Monster> lstMobs;
	    private int mobFrame = 1;
	    private float mobPositionCoordX = 0;
	    private float mobPositionCoordY = 0;
	    private int mobRandomSt = 0;
	    private int mobTimerMov = 100;
	    private int mobTimerFrame = 40;
	    
	    float mobposXmax = 0;
		float mobposXmin = 0;
		float mobposYmax = 0;
		float mobposYmin = 0;
	    
	    //Online
	    private ArrayList<Player> lstOnlinePlayers;
	    		
	    //Chats
	    private ArrayList<String> lstChats;
	    
	    //Damage
	    private ArrayList<Damage> lstDamage;
	    
	    //Skills
	    private ArrayList<Skill> lstSkills;
	    
	    //Sprites Background
	    private Sprite spr_Background;
	    private Texture tex_Background;
	    
	    //Teste Dot
	    private Sprite spr_testeDot;
	    private Texture tex_testeDot;
	    
	    //Controller
	    private final IntSet downKeys = new IntSet(20);	
		
		public GameMap(MainGame _game,ManagerScreen _screen, boolean _network) {
			this.game = _game;	
			this.screen = _screen;
			this.gameControl = new GameControl();
			this.network = _network;
			this.mob = new Monster();
			this.randnumber = new Random();
			
			lstMobs = new ArrayList<Monster>();
			lstOnlinePlayers = new ArrayList<Player>();
			lstDamage = new ArrayList<Damage>();
			lstSkills = new ArrayList<Skill>();
			lstChats = new ArrayList<String>();
			
			//test dot
			tex_testeDot = new Texture(Gdx.files.internal("data/assets/misc/selected.png"));
			spr_testeDot = new Sprite(tex_testeDot);
			
			//Load Player Data
			player = gameControl.LoadData();
			
			//Load Title
			if(player.Map_A.equals("StreetsA")) { tex_Background = new Texture(Gdx.files.internal("data/assets/maps/streetsA.png")); }
			if(player.Map_A.equals("Sewers")) { tex_Background = new Texture(Gdx.files.internal("data/assets/maps/sewers.png")); }
			
			
			if(player.Map_A.equals("Sewers")) { lstMobs = gameControl.LoadMonsters("Sewers"); }
				
			spr_Background = new Sprite(tex_Background);
					
			//Camera and Inputs
			camera = new OrthographicCamera();
		    viewport = new StretchViewport(200,200,camera);
			viewport.apply();
			camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
			Gdx.input.setInputProcessor(this);
	
			//font
			font_master = new BitmapFont(Gdx.files.internal("data/assets/font/impact.fnt"),Gdx.files.internal("data/assets/font/impact.png"), false);
			font_master.setColor(Color.WHITE);
			font_master.getData().setScale(0.07f,0.11f);
			font_master.setUseIntegerPositions(false);	
		}
			
		@Override
		public void render(float delta) {
			try {						
				savedataTime--;
				if(savedataTime < 0) { savedataTime = 700;}
				
				//Just for coloring
				Gdx.gl.glClearColor(1,1,1,1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
				playerPosX = player.PosX_A;
				playerPosY = player.PosY_A;
				
				//Camera Ajustments
				cameraCoordsX = playerPosX;
				cameraCoordsY = playerPosY;
				
				//Follow camera
				if(playerPosX <= 0f) { cameraCoordsX = 0; }
				if(playerPosX >= 175) { cameraCoordsX = 175; }
				if(playerPosY >= 91.5f) { cameraCoordsY = 91.5f; }
				if(playerPosY <= -80) { cameraCoordsY = -80; }
				
				camera.position.set(cameraCoordsX,cameraCoordsY + 30,0);
				camera.update();
			    game.batch.setProjectionMatrix(camera.combined);		    
				game.batch.begin();
				
				//Background	
				spr_Background.setPosition(-100, -150);
				spr_Background.setSize(400, 400);
				spr_Background.draw(game.batch);
				
				//ShowNPCS
				ShowNPC();
				
				//Player
				gameControl.SetCharMovement("",touchPosX -5.5f,touchPosY);		
				spr_playerBottom = gameControl.CharacterMoveBottom("", 0);
				spr_playerBottom.draw(game.batch);
				
				spr_playerUpper = gameControl.CharacterMoveUpper("", 0);
				spr_playerUpper.draw(game.batch);
				
				spr_playerHair = gameControl.CharacterMoveHair();
				spr_playerHair.draw(game.batch);
				
				player = gameControl.GetPlayer();
				
				
				/////[Interface]/////
				
				//arrowmove
				spr_master = gameControl.GetInterface("arrowmove");
				spr_master.setPosition(touchPosX -1.5f, touchPosY);
				spr_master.setSize(4, 10);
				spr_master.draw(game.batch);
				
				//player tag
				spr_master = gameControl.GetInterface("tagplayer");
				spr_master.setPosition(cameraCoordsX - 99, cameraCoordsY + 90);
				spr_master.setSize(60, 40);
				spr_master.draw(game.batch);
				
				spr_playerHair = gameControl.MenuHair(cameraCoordsX,cameraCoordsY);
				spr_playerHair.draw(game.batch);
				
				
				font_master.getData().setScale(0.13f,0.20f);
				font_master.setUseIntegerPositions(false);	
				
				font_master.draw(game.batch, player.Name_A , cameraCoordsX - 77 , cameraCoordsY + 124);
				font_master.draw(game.batch, String.valueOf(player.Hp_A), cameraCoordsX - 82 , cameraCoordsY + 115);
				font_master.draw(game.batch, String.valueOf(player.Mp_A) , cameraCoordsX - 63 , cameraCoordsY + 115);
				font_master.draw(game.batch, String.valueOf(player.Exp_A) , cameraCoordsX - 60 , cameraCoordsY + 105);
				font_master.draw(game.batch, String.valueOf(player.Level_A) , cameraCoordsX - 77 , cameraCoordsY + 105);
				
				showPosX = Math.round(player.PosX_A);
				showPosY = Math.round(player.PosY_A);
				font_master.draw(game.batch, "X:" + String.valueOf(showPosX), cameraCoordsX - 90 , cameraCoordsY + 90);
				font_master.draw(game.batch, "Y:" + String.valueOf(showPosY) , cameraCoordsX - 75 , cameraCoordsY + 90);
				
				//cards action
				if(AutoAttack) {
					spr_master = gameControl.GetInterface("cardactionON");
					spr_master.setPosition(cameraCoordsX + 78, cameraCoordsY - 65);
					spr_master.setSize(13, 32);
					spr_master.draw(game.batch);
				}
				else {
					spr_master = gameControl.GetInterface("cardaction");
					spr_master.setPosition(cameraCoordsX + 78, cameraCoordsY - 65);
					spr_master.setSize(13, 32);
					spr_master.draw(game.batch);
				}
				
				if(player.Map_A.equals("Sewers")) {
					spr_master = gameControl.GetInterface("cardtarget");
					spr_master.setPosition(cameraCoordsX + 78, cameraCoordsY - 30);
					spr_master.setSize(13, 32);
					spr_master.draw(game.batch);
				}
				
				//cards job
				
				
				
				//Show Monsters
				ShowMobs();
				
				
				if(state.equals("Menu"))
				{
					spr_master = gameControl.GetInterface("charmenu");
					spr_master.setPosition(cameraCoordsX - 90, cameraCoordsY - 65);
					spr_master.setSize(180, 155);
					spr_master.draw(game.batch);
					
					font_master.draw(game.batch, "For", cameraCoordsX + 4 , cameraCoordsY + 32);
					font_master.draw(game.batch, String.valueOf(player.Str_A), cameraCoordsX + 8 , cameraCoordsY + 22);
					
					font_master.draw(game.batch, "Vit", cameraCoordsX + 18 , cameraCoordsY + 32);
					font_master.draw(game.batch, String.valueOf(player.Vit_A), cameraCoordsX + 22 , cameraCoordsY + 22);
					
					font_master.draw(game.batch, "Agi", cameraCoordsX + 32 , cameraCoordsY + 32);
					font_master.draw(game.batch, String.valueOf(player.Agi_A), cameraCoordsX + 36 , cameraCoordsY + 22);
									
					font_master.draw(game.batch, "Des", cameraCoordsX + 46 , cameraCoordsY + 32);
					font_master.draw(game.batch, String.valueOf(player.Dex_A), cameraCoordsX + 50 , cameraCoordsY + 22);

					font_master.draw(game.batch, "Int", cameraCoordsX + 60 , cameraCoordsY + 32);
					font_master.draw(game.batch, String.valueOf(player.Wis_A), cameraCoordsX + 64 , cameraCoordsY + 22);
				}
				
				if(state.equals("DungeonSelect")) {
					spr_master = gameControl.GetInterface("dungeonselector");
					spr_master.setPosition(cameraCoordsX - 90, cameraCoordsY - 65);
					spr_master.setSize(180, 155);
					spr_master.draw(game.batch);
				}
				
				if(state.equals("Shop")) {
					
				}
						
				CheckColisionSewers();
				CheckAutoAttack();
				CheckMobAutoAttack();
				ShowDamage();
							
				//Teste		
				
				//teste X
				spr_testeDot.setPosition(mobposXmax + 20, mobposYmax);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);
				
				spr_testeDot.setPosition(mobposXmax, mobposYmax);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);
				
				//teste Y
				spr_testeDot.setPosition(mobposYmax + 20, mobposYmax);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);
				
				spr_testeDot.setPosition(mobposYmax, mobposYmax);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);
				
				//teste player
				spr_testeDot.setPosition( player.PosX_A + 5, player.PosY_A);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);
				
				//spr_testeDot.setPosition(touchPosX, touchPosY);
				//spr_testeDot.setSize(1, 1);
				//spr_testeDot.draw(game.batch);
				
				//spr_testeDot.setPosition(cameraCoordsX + 78, cameraCoordsY + 2);
				//spr_testeDot.setSize(1, 1);
				//spr_testeDot.draw(game.batch);
				
				if(state.equals("Change")) {
					this.screen.screenSwitch("LoadingScreen", network);
				}
				
				game.batch.end();		
			}
			
			catch(Exception ex) {
				Gdx.app.exit();
			}
		}
		
		
		public void CheckAutoAttack() {
			if(player.Map_A.equals("Sewers") && AutoAttack) {
				
				playerPosXSelective = player.PosX_A + 5;
				playerPosYSelective = player.PosY_A;
				
				for(int i = 0; i < lstMobs.size(); i++) {
					
					if(player.Target_A.equals(lstMobs.get(i).MobID)) {
						
						float mobposXmax = lstMobs.get(i).MobPosX + 20;
						float mobposXmin = lstMobs.get(i).MobPosX;
						float mobposYmax = lstMobs.get(i).MobPosY + 30;
						float mobposYmin = lstMobs.get(i).MobPosY - 10;
						 
						if((playerPosXSelective) > (mobposXmin) && (mobposXmax) < (playerPosXSelective)
						   && (mobposYmin) > (playerPosYSelective) && (playerPosYSelective) < (mobposYmax)) {
							player.playerInBattle_A = "yes";
							player.AtkTimer_A--;
							
							if(player.AtkTimer_A < (player.AtkTimerMax_A - 10) && player.playerInAttack_A.equals("yes")) {
								player.playerInAttack_A = "no";
							}
							
							if(player.AtkTimer_A <= 0) { 	
								int atkweapon = 5;
								int mobhp = lstMobs.get(i).MobHp; //CheckDamageDifer(lstMobs.get(i).MobHpMax, 1);
								int damagehit = player.Atk_A + atkweapon + player.Str_A;
								
								/*if(CheckMobEvade()) { 
									GameObject damage = new GameObject();
									damage.DamagePosX = lstMobs.get(i).MobPosX;
									damage.DamagePosY = lstMobs.get(i).MobPosY;
									damage.DamageTime = 100;
									damage.DamageType = "mob";
									damage.DamageValue = 0;
									lstDamage.add(damage);
									return; 
								}*/
								
								if(network) {
									/*int mobHpGet = lstMobs.get(i).MobHp;
									int st = player.Stamina_A;
									if(st > 0) { mobHpGet =  mobHpGet - damagehit;  } else {  mobHpGet =  mobHpGet - 5; }								
									OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHpGet));
									if(mobHpGet < 0) { mobHpGet = 0; }
									if(mobHpGet <= 0) { 					
										player.Target = "none";
										player.AtkTimer = player.AtkTimerMax;
										player.playerInBattle = "no";
									    player.playerInAttack = "no";
									    player.playerInCast = "no";	
									    autoattack = false;
									    
									    ItemDrop(lstMobs.get(i).MobName);
									    player.Money = player.Money + 2;
									    GiveExp(lstMobs.get(i).MobExp);
									    return;
									}									
									GameObject damage = new GameObject();
									damage.DamagePosX = lstMobs.get(i).MobPosX;
									damage.DamagePosY = lstMobs.get(i).MobPosY;
									damage.DamageTime = 100;
									damage.DamageType = "mob";
									damage.DamageValue = damagehit;
									lstDamage.add(damage);
									
									player.AtkTimer = player.AtkTimerMax;
									player.playerInAttack = "yes";
									lstMobs.get(i).MobTarget = player.Name;	*/
								}
								else {
									int st = player.Stamina_A;
									if(st > 0) { mobhp = mobhp - damagehit;  } else {  mobhp = mobhp - 5; }								
									if(mobhp < 0) { mobhp = 0; }
									lstMobs.get(i).MobHp = mobhp;
									
									if(lstMobs.get(i).MobHp <= 0) { 
										
										player.Target_A = "none";
										player.AtkTimer_A = player.AtkTimerMax_A;
										player.playerInBattle_A = "no";
									    player.playerInAttack_A = "no";
									    player.playerInCast_A = "no";	
									    AutoAttack = false;
									    
									    //ItemDrop(lstMobs.get(i).MobName);
									    player.Money_A = player.Money_A + 2;
									    //GiveExp(lstMobs.get(i).MobExp);
									    return;
									}
									
									Damage damage = new Damage();
									damage.DamagePosX = lstMobs.get(i).MobPosX;
									damage.DamagePosY = lstMobs.get(i).MobPosY;
									damage.DamageTime = 100;
									damage.DamageType = "mob";
									damage.DamageValue = damagehit;
									lstDamage.add(damage);
									
									player.AtkTimer_A = player.AtkTimerMax_A;
									player.playerInAttack_A = "yes";
									lstMobs.get(i).MobTarget = player.Name_A;	
								}
									
								
								
							}					
						}
						else {
							player.playerInBattle_A = "no";
						}
					}
				}
			}
		}
		
		public void CheckMobAutoAttack() {
			if(player.Map_A.equals("Sewers")) {
				for(int i = 0; i < lstMobs.size(); i++) {						
					if(lstMobs.get(i).MobTarget.equals(player.Name_A)) {
						if(player.PosX_A > (lstMobs.get(i).MobPosX - 5) && player.PosX_A < (lstMobs.get(i).MobPosX + 15)
							   && player.PosY_A > (lstMobs.get(i).MobPosY - 7) && player.PosY_A < (lstMobs.get(i).MobPosY + 18)) {
								
								lstMobs.get(i).MobAtkTimer--;
								if(lstMobs.get(i).MobAtkTimer <= 0) {
									 int mobluck = randnumber.nextInt(100);
									 if(mobluck > 5 && mobluck < 20) {
										 player.Hp_A = player.Hp_A - ((lstMobs.get(i).MobAtk * 2) - player.Def_A);
									 }
									 if(mobluck >= 0 && mobluck < 5) {
										 player.Hp_A = player.Hp_A - ((lstMobs.get(i).MobAtk * 3) - player.Def_A);
									 }
									 if(mobluck > 10)
									 {
										 player.Hp_A = player.Hp_A - (lstMobs.get(i).MobAtk - player.Def_A);
									 }								 
									 lstMobs.get(i).MobAtkTimer = lstMobs.get(i).MobAtkTimerMax;
									 Damage damage = new Damage();
									 damage.DamagePosX = lstMobs.get(i).MobPosX;
									 damage.DamagePosY = lstMobs.get(i).MobPosY;
									 damage.DamageTime = 100;
									 damage.DamageType = "player";
									 damage.DamageValue = lstMobs.get(i).MobAtk;
									 lstDamage.add(damage);
									 
									if(player.Hp_A <= 0) {
										//playerDead = true;
									}
								}
							}
						}
					}
				}
			}
		
		public void ShowDamage() {
			
			if(lstDamage.size() == 0) {
				return;
			}
			
			for(int i = 0; i < lstDamage.size(); i++) {
				lstDamage.get(i).DamagePosY = lstDamage.get(i).DamagePosY + 0.4f;
				lstDamage.get(i).DamageTime = lstDamage.get(i).DamageTime - 1;
								
				font_master.getData().setScale(0.10f,0.15f);
				font_master.setUseIntegerPositions(false);
				if(lstDamage.get(i).DamageType.equals("mob")) { font_master.setColor(Color.YELLOW); }
				if(lstDamage.get(i).DamageType.equals("player")) { font_master.setColor(Color.RED); }
				if(lstDamage.get(i).DamageType.equals("heal")) { font_master.setColor(Color.GREEN); }
				
				font_master.draw(game.batch, String.valueOf(lstDamage.get(i).DamageValue), lstDamage.get(i).DamagePosX, lstDamage.get(i).DamagePosY);
				
				if(lstDamage.get(i).DamageTime < 0) {
					lstDamage.remove(lstDamage.get(i));
				}
			}
		}
		
		
		public void ShowMobs() {			
			if(player.Map_A.equals("Sewers") || player.Map_A.equals("Watercave") || player.Map_A.equals("Mines") || player.Map_A.equals("Snowpalace") || player.Map_A.equals("Tower")) {
				for(int i = 0; i < lstMobs.size(); i++) {

					//Target do player
					if(player.Target_A.equals(lstMobs.get(i).MobID)) {
						spr_master = gameControl.GetInterface("arrowmove");
						spr_master.setPosition(lstMobs.get(i).MobPosX + 7, lstMobs.get(i).MobPosY + 30);
						spr_master.setSize(4,8);
						spr_master.draw(game.batch);
					}
					
					spr_testeDot.setPosition(lstMobs.get(i).MobPosX + 20, lstMobs.get(i).MobPosY);
					spr_testeDot.setSize(1, 1);
					spr_testeDot.draw(game.batch);
					
					spr_testeDot.setPosition(lstMobs.get(i).MobPosX, lstMobs.get(i).MobPosY);
					spr_testeDot.setSize(1, 1);
					spr_testeDot.draw(game.batch);
					
					spr_testeDot.setPosition(lstMobs.get(i).MobPosX, lstMobs.get(i).MobPosY + 30);
					spr_testeDot.setSize(1, 1);
					spr_testeDot.draw(game.batch);
					
					spr_testeDot.setPosition(lstMobs.get(i).MobPosX, lstMobs.get(i).MobPosY);
					spr_testeDot.setSize(1, 1);
					spr_testeDot.draw(game.batch);
					
					
					mobTimerFrame = lstMobs.get(i).MobFrameTime;
					if(mobTimerFrame > 0) {
						mobTimerFrame--;
						lstMobs.get(i).MobFrameTime = mobTimerFrame;
					}
					if(mobTimerFrame <= 0) {
						mobTimerFrame = 40;
						mobFrame = lstMobs.get(i).MobFrame;
						mobFrame++;
						if(mobFrame > 3) { mobFrame = 1;}
						lstMobs.get(i).MobFrame = mobFrame;
						lstMobs.get(i).MobFrameTime = 40;
					}

					//Sem Target
					if(lstMobs.get(i).MobTarget.equals("none")) {
						mobTimerMov = lstMobs.get(i).MobTimerMov;
						mobRandomSt = lstMobs.get(i).MobRandomSt;
						if(mobTimerMov >= 0) { 
							mobTimerMov--;
							lstMobs.get(i).MobTimerMov = mobTimerMov;
						}
						if(mobTimerMov < 0) { 
							mobRandomSt = randnumber.nextInt(4); 
							mobTimerMov = 100; 
							lstMobs.get(i).MobTimerMov = mobTimerMov;
							lstMobs.get(i).MobRandomSt = mobRandomSt;
						}

						if(mobRandomSt == 0) { 
							mobPositionCoordX = lstMobs.get(i).MobPosX;
							mobPositionCoordX = mobPositionCoordX + 0.07f; 
							lstMobs.get(i).MobPosX = mobPositionCoordX;
						}
						if(mobRandomSt == 1) { 
							mobPositionCoordX = lstMobs.get(i).MobPosX;
							mobPositionCoordX = mobPositionCoordX - 0.07f;
							lstMobs.get(i).MobPosX = mobPositionCoordX;
						}
						if(mobRandomSt == 2) { 
							mobPositionCoordY = lstMobs.get(i).MobPosY;
							mobPositionCoordY = mobPositionCoordY + 0.07f;
							lstMobs.get(i).MobPosY = mobPositionCoordY;
						}
						if(mobRandomSt == 3) { 
							mobPositionCoordY = lstMobs.get(i).MobPosY;
							mobPositionCoordY = mobPositionCoordY - 0.07f;
							lstMobs.get(i).MobPosY = mobPositionCoordY;
						}
					}

					if(lstMobs.get(i).MobTarget.equals(player.Name_A)) {
						if(lstMobs.get(i).MobPosX < player.PosX_A + 3) { lstMobs.get(i).MobPosX = lstMobs.get(i).MobPosX + 0.07f; }
						if(lstMobs.get(i).MobPosX > player.PosX_A + 3) { lstMobs.get(i).MobPosX = lstMobs.get(i).MobPosX - 0.07f; }
						if(lstMobs.get(i).MobPosY < player.PosY_A - 6 ) { lstMobs.get(i).MobPosY = lstMobs.get(i).MobPosY + 0.07f; }
						if(lstMobs.get(i).MobPosY > player.PosY_A - 6) { lstMobs.get(i).MobPosY = lstMobs.get(i).MobPosY - 0.07f; }
					}

					//Limit screen
					if(mobPositionCoordY >= 57 && lstMobs.get(i).MobDead.equals("no")) 
					{						
						lstMobs.get(i).MobPosY = 0;
						lstMobs.get(i).MobPosX = 0;
					}
					if(mobPositionCoordY <= -73.5f && lstMobs.get(i).MobDead.equals("no")) 
					{
						lstMobs.get(i).MobPosY = 0;
						lstMobs.get(i).MobPosX = 0;
					}
					if(mobPositionCoordX >= 45f && lstMobs.get(i).MobDead.equals("no")) 
					{
						lstMobs.get(i).MobPosY = 0;
						lstMobs.get(i).MobPosX = 0;
					}
					if(mobPositionCoordX <= -66.5f && lstMobs.get(i).MobDead.equals("no")) 
					{
						lstMobs.get(i).MobPosY = 0;
						lstMobs.get(i).MobPosX = 0;
					}

					if(player.Map_A.equals("Sewers")) { spr_master = gameControl.GetSpriteMonster("Sewers",lstMobs.get(i).MobName + lstMobs.get(i).MobFrame + "L"); }
					//if(player.Map_A.equals("Watercave")) { spr_mob = atlas_mobWatercave.createSprite(lstMobs.get(i).MobName + lstMobs.get(i).MobFrame + "L"); }
					//if(player.Map_A.equals("Mines")) { spr_mob = atlas_mobMines.createSprite(lstMobs.get(i).MobName + lstMobs.get(i).MobFrame + "L"); }
					//if(player.Map_A.equals("Snowpalace")) { spr_mob = atlas_mobSnowpalace.createSprite(lstMobs.get(i).MobName + lstMobs.get(i).MobFrame + "L"); }
					//if(player.Map_A.equals("Tower")) { spr_mob = atlas_mobTower.createSprite(lstMobs.get(i).MobName + lstMobs.get(i).MobFrame + "L"); }
					spr_master.setPosition(lstMobs.get(i).MobPosX, lstMobs.get(i).MobPosY);
					spr_master.setSize(20,35);
					//spr_master.setSize(lstMobs.get(i).MobSizeX, lstMobs.get(i).MobSizeY);
					spr_master.draw(game.batch);


					mobPositionCoordX = lstMobs.get(i).MobPosX;
					mobPositionCoordY = lstMobs.get(i).MobPosY;
					mobPositionCoordY = mobPositionCoordY - 0.2f;
					font_master.draw(game.batch, lstMobs.get(i).MobName + " HP :" + lstMobs.get(i).MobHp + "/" + lstMobs.get(i).MobHpMax ,mobPositionCoordX, mobPositionCoordY);
				}			
			}
		}
		
		
		public void CheckColisionMetroStation() {		
			//Change to CityStreets (RPG side)
			if(player.PosX_A > 30 && player.PosX_A < 45 && player.PosY_A > - 65 && player.PosY_A < - 40) {
				player.Map_A = "StreetsA";
				gameControl.SetPlayer(player);
				gameControl.SaveData(player);
				state = "Change";
			}
		}
	
		@Override
		public void input(String input) {
			if(input.contains(":")) { return; }
			if(input.contains("none")) { return; }
		}

		@Override
		public void canceled() {}
		
		
		public void ShowNPC() {
			if(player.Map_A.equals("StreetsA")) {
				spr_master = gameControl.ShowNPC("guard");
				spr_master.setPosition(185, -47);
				spr_master.setSize(13, 31);
				spr_master.draw(game.batch);
				
				spr_master = gameControl.GetInterface("ballonguard");
				spr_master.setPosition(182, -15);
				spr_master.setSize(19, 10);
				spr_master.draw(game.batch);
				
				font_master.draw(game.batch, "Cavernas", 183 , -7);
			}
		}
		
		public void CheckColisionSewers() {	
			
			if(player.Map_A.equals("Sewers")) {
				//Change to CityStreets (RPG side)
				if(player.PosX_A > 67 && player.PosX_A < 90 && player.PosY_A > 181 && player.PosY_A < 218) {
					player.Map_A = "StreetsA";
					player.PosX_A = 186;
					player.PosY_A = -68;
					gameControl.SetPlayer(player);
					gameControl.SaveData(player);
					state = "Change";
				}
			}
		}
		
		public void CheckAction() {
			
			if(player.Map_A.equals("StreetsA")) {		
				if(player.PosX_A > 176 && player.PosY_A < 198 && player.PosY_A > -67 && player.PosY_A < -34 ) {
					state = "DungeonSelect";
				}		
			}
			
			if(player.Map_A.equals("Sewers")) {
				if(AutoAttack) { AutoAttack = false; } else { AutoAttack = true; }
			}
		}
		
		public void ChangeTarget() {
			
			if(player.Map_A.equals("Sewers")) {
			
				String playerTarget = player.Target_A;
				for(int i = 0; i < lstMobs.size(); i++) {
					
					if(countSwitchTarget == 0) {
						countSwitchTarget = i;
					}
					
					if(countSwitchTarget >= 0) {
						if(countSwitchTarget <= lstMobs.size()) {
							countSwitchTarget++;
							if(countSwitchTarget >= lstMobs.size()) { countSwitchTarget = 0; }
							if(!playerTarget.equals(lstMobs.get(countSwitchTarget).MobID)) {
								player.Target_A = lstMobs.get(countSwitchTarget).MobID;						
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

		@Override
		public boolean keyDown(int keycode) {
			
			//if(playerDead) { return false; }
			
			if(state.equals("main")) {
				movement = true;		
				downKeys.add(keycode);
		        if (downKeys.size >= 2){
		            onMultipleKeysDown(keycode);
		        }
		        if(downKeys.size == 1) {
		        	if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
		        		player.Side_A = "left";
		        		player.Walk_A = "walk"; 
		        		return false;
		            }
		    		
		    		if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
		    			player.Side_A = "back";
		    			player.Walk_A = "walk";
		    			return false;
		            }
		    		
		    		if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
		    			player.Side_A = "front";
		    			player.Walk_A = "walk";	
		    			return false;
		            }
		    		
		    		if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
		    			player.Side_A = "right";
		    			player.Walk_A = "walk";
		    			return false;
		            }
		        }
			}
			
			return false;
		}
		
		private void onMultipleKeysDown (int mostRecentKeycode){
			
			if(state.equals("menu")) { return; }
			
			//For multiple key presses
		    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
		        	player.Side_A = "left-front";
		        	player.Walk_A = "walk";  	
		        }
		    }
		    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
		        	player.Side_A = "left-back";
		        	player.Walk_A = "walk";
		        }
		    }
		    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
		    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
		    		player.Side_A = "right-back";
		    		player.Walk_A = "walk";	
		    	}
		    }
		    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
		    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
		    		player.Side_A = "right-front";
		    		player.Walk_A = "walk";	
		    	}
		    }
		    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
		        	player.Side_A = "back-right";
		        	player.Walk_A = "walk";
		        }
		    }
		    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
		        	player.Side_A = "back-left";
		        	player.Walk_A = "walk";
		         }
		    }
		    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
		        	player.Side_A = "front-right";
		        	player.Walk_A = "walk";
		       }
		    }
		    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
		        	player.Side_A = "front-left";
		        	player.Walk_A = "walk";	
		         }
		    }
		}	

		@Override
		public boolean keyUp(int keycode) {
			movement = false;
			downKeys.remove(keycode);
			player.Walk_A = "no";
			//breakwalk = "";
			
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
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int p1, int p2, int pointer, int button) {
			
			//if(playerDead) { return false; }
			
			Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));
			
			if(state.equals("Main")) {
				
				if(coordsTouch.x >=  cameraCoordsX - 100 && coordsTouch.x <= cameraCoordsX - 56 && coordsTouch.y >= cameraCoordsY + 93 && coordsTouch.y <= cameraCoordsY + 126) {
					state = "Menu";
					return false;
				}
				
				//CheckAction
				if(coordsTouch.x >=  cameraCoordsX + 78 && coordsTouch.x <= cameraCoordsX + 90 && coordsTouch.y >= cameraCoordsY - 66 && coordsTouch.y <= cameraCoordsY - 35) {
					CheckAction();
					return false;
				}
				
				if(player.Map_A.equals("Sewers")) {
					if(coordsTouch.x >=  cameraCoordsX + 78 && coordsTouch.x <= cameraCoordsX + 90 && coordsTouch.y >= cameraCoordsY - 30 && coordsTouch.y <= cameraCoordsY + 2) {
						ChangeTarget();
						return false;
					}
				}
				
				touchPosX = coordsTouch.x;
				touchPosY = coordsTouch.y;
				gameControl.SetCharSide(touchPosX, touchPosY);
			}
			
			if(state.equals("Menu")) {
				//btn back
				if(coordsTouch.x >=  cameraCoordsX + 79 && coordsTouch.x <= cameraCoordsX + 87 && coordsTouch.y >= cameraCoordsY + 72 && coordsTouch.y <= cameraCoordsY + 85) {
					state = "Main";
					return false;
				}
				//Item 1
				if(coordsTouch.x >=  cameraCoordsX - 86 && coordsTouch.x <= cameraCoordsX - 73 && coordsTouch.y >= cameraCoordsY + 46 && coordsTouch.y <= cameraCoordsY + 65) {
					gameControl.UseItem(1);
					return false;
				}
				//Item 2
				if(coordsTouch.x >=  cameraCoordsX - 72 && coordsTouch.x <= cameraCoordsX - 59 && coordsTouch.y >= cameraCoordsY + 46 && coordsTouch.y <= cameraCoordsY + 65) {
					gameControl.UseItem(2);
					return false;
				}
				//Item 3
				if(coordsTouch.x >=  cameraCoordsX - 58 && coordsTouch.x <= cameraCoordsX - 44 && coordsTouch.y >= cameraCoordsY + 46 && coordsTouch.y <= cameraCoordsY + 65) {
					gameControl.UseItem(3);
					return false;
				}
				//Item 4
				if(coordsTouch.x >=  cameraCoordsX - 43 && coordsTouch.x <= cameraCoordsX - 30 && coordsTouch.y >= cameraCoordsY + 46 && coordsTouch.y <= cameraCoordsY + 65) {
					gameControl.UseItem(4);
					return false;
				}
				//Item 5
				if(coordsTouch.x >=  cameraCoordsX - 29 && coordsTouch.x <= cameraCoordsX - 16 && coordsTouch.y >= cameraCoordsY + 46 && coordsTouch.y <= cameraCoordsY + 65) {
					gameControl.UseItem(5);
					return false;
				}
				//Item 6
				if(coordsTouch.x >=  cameraCoordsX - 15 && coordsTouch.x <= cameraCoordsX - 2 && coordsTouch.y >= cameraCoordsY + 46 && coordsTouch.y <= cameraCoordsY + 65) {
					gameControl.UseItem(6);
					return false;
				}
			}
			
			if(state.equals("DungeonSelect")) {
				//btn back
				if(coordsTouch.x >=  cameraCoordsX + 79 && coordsTouch.x <= cameraCoordsX + 87 && coordsTouch.y >= cameraCoordsY + 72 && coordsTouch.y <= cameraCoordsY + 85) {
					state = "Main";
					return false;
				}
				//Dungeon 1
				if(coordsTouch.x >=  cameraCoordsX - 87 && coordsTouch.x <= cameraCoordsX - 8 && coordsTouch.y >= cameraCoordsY + 45 && coordsTouch.y <= cameraCoordsY + 64) {
					player.Map_A = "Sewers";
					player.PosX_A = 78;
					player.PosY_A = 150;
					gameControl.SetPlayer(player);
					gameControl.SaveData(player);
					state = "Change";
					return false;
				}
			}
			
			
			
			
			return false;		
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			movement = false;
			player.Walk_A = "no";
			
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
			
			//if(playerDead) { return false; }
			
			Vector3 coordsTouch = camera.unproject(new Vector3(screenX,screenY,0));
			
			if(movement){	
				//Right
     				if(coordsTouch.x >= -53.5f && coordsTouch.x <= -45.5f && coordsTouch.y > -50f && coordsTouch.y < -42f) {
					player.Side_A = "right";
					player.Walk_A = "walk";	
					return false;
				}
				//Left
				if(coordsTouch.x >= -57.5f && coordsTouch.x <= -45.5f && coordsTouch.y > -50f && coordsTouch.y < -42) {
					player.Side_A = "left";
					player.Walk_A = "walk";	
					return false;
				}
				//Front
				if(coordsTouch.x > -59.5f && coordsTouch.x < -51.5f && coordsTouch.y > -65 && coordsTouch.y < -50f) {
					player.Side_A = "front";
					player.Walk_A = "walk";	
					return false;
				}
				//Back
				if(coordsTouch.x > -59.5f && coordsTouch.x < -51.5f && coordsTouch.y > -42f && coordsTouch.y < -27) {
					player.Side_A = "back";
					player.Walk_A = "walk";	
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
