package com.moonbolt.cityscale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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


public class GameMap implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	
		//Objects
	    private MainGame game;
	    private ManagerScreen screen;
	    private GameControl gameControl;
	    private String state = "Main";
	    private Sprite spr_master;
		private Random randnumber;
		private boolean network = false;
		private String shopname = "";
	    private String menuoption = "";
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
	    private Sprite spr_playerweapon;
	    private boolean playerDead = false;
	    private boolean movement = false;
		private boolean autoattack = false;
		private Sprite spr_target;
		private int countDead = 100;
		private String itemEquipped = "";
		private Sprite spr_item;
		private String msgShowMenu = "Atualizado com sucesso";
		private int msgShowTime = 0;
		private int hotketcountitem1;
		private int hotketcountitem2;
		
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

		//Misc
		private ArrayList<Damage> listDamage;
		private ArrayList<Skill> listSkills;
		private String itemdropname;
		private int SysMsgCount = 0;    
	    private int savedataTime = 500;
		private int showDropMsg;
		private Sprite spr_shop;
		private String showbuymsg = "";
		private int showbuymsgtime = 2000;
		private ArrayList<String> lstChats;

		//Online
		private ArrayList<Player> lstOnlinePlayers;
		private Sprite spr_playerTopOnline;
	    private Sprite spr_playerBottomOnline;
	    private Sprite spr_playerFooterOnline;
	    private Sprite spr_playerHairOnline;
	    private Sprite spr_playerHatOnline;
	    private Sprite spr_weaponOnline;
	    private boolean onlineAuth = false;
	    private String onlineresponse = "";
	    private int ExpSharedOnline = 0;
	    private boolean SyncStart = false;
		
		
	    //Sprite NPC
	    private Sprite spr_npc;
	    
	    //UX
	    private float padmoveX = -56;
	    private float padmoveY = -50;
	    private Sprite spr_playerTag;
	     
	    //Sprites Background
	    private Sprite spr_Background;
	    private Texture tex_Background;
	    
	    //Teste Dot
	    private Sprite spr_testeDot;
	    private Texture tex_testeDot;
	    
	    //Controller
	    private final IntSet downKeys = new IntSet(20);	
		
		public GameMap(MainGame gameAlt,ManagerScreen screenAlt, boolean networkAlt) {
			this.game = gameAlt;	
			this.screen = screenAlt;
			this.randnumber = new Random();
			this.network = networkAlt;

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

			//Damage/skill Stance
			listDamage = new ArrayList<Damage>();
			listSkills = new ArrayList<Skill>();
					
			//Camera and Inputs
			camera = new OrthographicCamera();
		    viewport = new StretchViewport(195,195,camera);
			viewport.apply();
			camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);			
			Gdx.input.setInputProcessor(this);
			
			//Etc
			lstChats = new ArrayList<String>();
			lstChats.add(""); lstChats.add(""); lstChats.add(""); lstChats.add(""); lstChats.add("");
			
			//Network
			lstOnlinePlayers = new ArrayList<Player>();
	
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
			//try {
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
				
				//Save
				savedataTime--;
				if(savedataTime < 0) {
					savedataTime = 700;
					gameControl.SaveData(player);
				}
				
				//Background	
				spr_Background.setPosition(-81,-194);
				spr_Background.setSize(270, 270);
				spr_Background.draw(game.batch);
				
				//npcs
				ShowOnlinePlayers();
				ShowNPCs();
				
				if(network) {
					if(!onlineAuth) {
						onlineresponse = gameControl.OnlineManager("CheckVersion","","");		
						if(onlineresponse.equals("Autorizado")) {
							onlineAuth = true;
						}		
					}
					else {
						if(!SyncStart) {
							gameControl.OnlineManager("SyncChats","","");
							gameControl.OnlineManager("SyncPlayer","","");
							SyncStart = true;
						}
					}			
				}
				
				//Char
				player = gameControl.SetCharMov(player, player.breakwalk_A);
				
				spr_playerfooter = gameControl.GetFooterChar(player, "no",0,0);
				spr_playerfooter.draw(game.batch);
				
				spr_playerbottom = gameControl.GetBottomChar(player, "no",0,0);
				spr_playerbottom.draw(game.batch);
								
				spr_playertop = gameControl.GetTopChar(player, "no", 0,0);
				spr_playertop.draw(game.batch);
				
				spr_playerhair = gameControl.GetHairChar(player, "no",0,0);
				spr_playerhair.draw(game.batch);
				
				if(player.playerInBattle_A.equals("yes") || player.playerInBattle_A.equals("yes") || player.playerInBattle_A.equals("yes")) {
					spr_playerweapon = gameControl.SetWeapon(player);
					spr_playerweapon.draw(game.batch);
				}

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
				font_master.draw(game.batch, String.valueOf(player.Level_A), cameraCoordsX - 88f, cameraCoordsY + 64f);
				font_master.draw(game.batch, String.valueOf(player.Exp_A) + "%", cameraCoordsX - 72f, cameraCoordsY + 64f);
				
				spr_master = gameControl.GetUX("innerpad", cameraCoordsX, cameraCoordsY);
				spr_master.setPosition(cameraCoordsX + padmoveX,cameraCoordsY + padmoveY);
				spr_master.draw(game.batch);
				
				
				try {
				if(network) { lstChats = gameControl.GetChatList(); }
				font_master.draw(game.batch, "Chats:", cameraCoordsX - 40f, cameraCoordsY - 40f);
				if(lstChats.size() > 4) {
					for(int i = 0; i < lstChats.size(); i++) {
						if(i == 0) { font_master.draw(game.batch, lstChats.get(i), cameraCoordsX - 36f, cameraCoordsY - 50f); }
						if(i == 1) { font_master.draw(game.batch, lstChats.get(i), cameraCoordsX - 36f, cameraCoordsY - 60f); }
						if(i == 2) { font_master.draw(game.batch, lstChats.get(i), cameraCoordsX - 36f, cameraCoordsY - 70f); }
						if(i == 3) { font_master.draw(game.batch, lstChats.get(i), cameraCoordsX - 36f, cameraCoordsY - 80f); }
						if(i == 4) { font_master.draw(game.batch, lstChats.get(i), cameraCoordsX - 36f, cameraCoordsY - 90f); }
					}
				}
				}
				
				catch(Exception ex) {}
				
				if(network) {
					font_master.draw(game.batch, "Online Ativo", cameraCoordsX - 48, cameraCoordsY + 96);
				}
				
				//Checks e Cards
				ShowCards();
				CheckColision();
				CheckAutoAttack();
				CheckMobAutoAttack();
				CheckMobDeadRespawn();
				ShowDamage();
				
				if(playerDead) { ShowPlayerDead(); }
				
				
				//Item Drop
				if(showDropMsg > 0) {
					spr_master = gameControl.GetUX("textbar", cameraCoordsX, cameraCoordsY);
					spr_master.setPosition(cameraCoordsX - 40, cameraCoordsY + 29);
					spr_master.setSize(80, 20);
					spr_master.draw(game.batch);
					font_master.draw(game.batch, itemdropname, cameraCoordsX - 38, cameraCoordsY + 42);
					showDropMsg--;
				}

				if(state.equals("Menu")){
					spr_master = gameControl.GetUX("menu", cameraCoordsX, cameraCoordsY);
					spr_master.draw(game.batch);
					//Status
					font_master.draw(game.batch, String.valueOf(player.Str_A), cameraCoordsX - 76f, cameraCoordsY - 50f);
					font_master.draw(game.batch, String.valueOf(player.Vit_A), cameraCoordsX - 62f, cameraCoordsY - 50f);
					font_master.draw(game.batch, String.valueOf(player.Agi_A), cameraCoordsX - 48f, cameraCoordsY - 50f);
					font_master.draw(game.batch, String.valueOf(player.Wis_A), cameraCoordsX - 34f, cameraCoordsY - 50f);
					font_master.draw(game.batch, String.valueOf(player.Dex_A), cameraCoordsX - 21f, cameraCoordsY - 50f);
					font_master.draw(game.batch, String.valueOf(player.StatusPoint_A), cameraCoordsX - 43f, cameraCoordsY - 67f);
					
					
					//CharacterShow
					spr_playerhair = gameControl.GetHairChar(player, "Show", cameraCoordsX, cameraCoordsY);
					spr_playerhair.draw(game.batch);
					
					spr_playerfooter = gameControl.GetFooterChar(player, "Show", cameraCoordsX, cameraCoordsY);
					spr_playerfooter.draw(game.batch);
					
					spr_playerbottom = gameControl.GetBottomChar(player, "Show", cameraCoordsX, cameraCoordsY);
					spr_playerbottom.draw(game.batch);
					
					spr_playertop = gameControl.GetTopChar(player, "Show", cameraCoordsX, cameraCoordsY);
					spr_playertop.draw(game.batch);

					//Show Character
					//Itens Equipped
					spr_playerfooter = gameControl.GetItem(player.SetFooter_A);
					spr_playerfooter.setPosition(cameraCoordsX + 13, cameraCoordsY + 41);
					spr_playerfooter.setSize(13, 22);
					spr_playerfooter.draw(game.batch);
					
					spr_playerbottom = gameControl.GetItem(player.SetBottom_A);
					spr_playerbottom.setPosition(cameraCoordsX + 27, cameraCoordsY + 41);
					spr_playerbottom.setSize(13, 22);
					spr_playerbottom.draw(game.batch);
					
					spr_playertop = gameControl.GetItem(player.SetUpper_A);
					spr_playertop.setPosition(cameraCoordsX + 41, cameraCoordsY + 41);
					spr_playertop.setSize(13, 22);
					spr_playertop.draw(game.batch);
					
					spr_playerweapon = gameControl.GetItem(player.Weapon_A);
					spr_playerweapon.setPosition(cameraCoordsX + 54.4f, cameraCoordsY + 41);
					spr_playerweapon.setSize(13, 22);
					spr_playerweapon.draw(game.batch);
					
					ShowBag();
					if(msgShowTime > 0) {
						msgShowTime--;
						font_master.draw(game.batch, msgShowMenu, cameraCoordsX + 14, cameraCoordsY - 38f);
						if(msgShowTime < 0) {
							msgShowTime = 0;
						}
					}	
				}
				
				if(state.equals("Shop")) {
					
					if(shopname.equals("refrishop")) {
						spr_shop = gameControl.GetShops("refrishop",cameraCoordsX, cameraCoordsY);
						spr_shop.draw(game.batch);		
						font_master.draw(game.batch, String.valueOf(player.Money_A), cameraCoordsX - 25, cameraCoordsY - 37);				
					}
					
					if(!showbuymsg.equals("")) {
						showbuymsgtime--;
						font_master.draw(game.batch, showbuymsg, cameraCoordsX + 15, cameraCoordsY - 37);
						if(showbuymsgtime <= 0) {
							showbuymsg = "";
							showbuymsgtime = 2000;						
						}
					}
				}

				if(state.equals("DungeonSelect")) {
					spr_master = gameControl.GetUX("battlezoneA", cameraCoordsX, cameraCoordsY);
					spr_master.draw(game.batch);
				}
				
				spr_testeDot.setPosition(cameraCoordsX + 79,cameraCoordsY - 66);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);

				spr_testeDot.setPosition(cameraCoordsX + 88,cameraCoordsY - 90);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);
				
				game.batch.end();
			}
			
			//catch(Exception ex) {
			//	Gdx.app.exit();
			//}
		
		public void ShowOnlinePlayers() {
			try {
			lstOnlinePlayers = gameControl.GetListOnlinePlayers();
			
				if(lstOnlinePlayers.size() > 0) {
					for(int i = 0; i < lstOnlinePlayers.size(); i++) {
						spr_playerhair = gameControl.GetHairChar(lstOnlinePlayers.get(i), "no",0,0);
						spr_playerhair.draw(game.batch);
						
						spr_playerfooter = gameControl.GetFooterChar(lstOnlinePlayers.get(i), "no",0,0);
						spr_playerfooter.draw(game.batch);
						
						spr_playerbottom = gameControl.GetBottomChar(lstOnlinePlayers.get(i), "no",0,0);
						spr_playerbottom.draw(game.batch);
						
						spr_playertop = gameControl.GetTopChar(lstOnlinePlayers.get(i), "no", 0,0);
						spr_playertop.draw(game.batch);
					}
				}
			}
			
			catch(Exception ex) {}
		}
		
		public void CheckAutoAttack() {
			
			if(player.Map_A.equals("Sewers") && autoattack) {
				for(int i = 0; i < listMonsters.size(); i++) {
					
					if(player.Target_A.equals(listMonsters.get(i).MobID)) {
						 
						if((listMonsters.get(i).MobPosX + 5) > (player.PosX_A - 5) && (listMonsters.get(i).MobPosX + 5) < (player.PosX_A + 15)
						   && (listMonsters.get(i).MobPosY + 7) > (player.PosY_A - 7) && (listMonsters.get(i).MobPosY + 5) < (player.PosY_A + 18)) {
							player.playerInBattle_A = "yes";
							player.AtkTimer_A--;
							
							if(player.AtkTimer_A <= 0) { 	
								int atkweapon = CheckWeapon();
								int mobhp = listMonsters.get(i).MobHp; //CheckDamageDifer(lstMobs.get(i).MobHpMax, 1);
								int damagehit = player.Atk_A + atkweapon + player.Str_A;
								player.playerInAttack_A = "yes";
								player.AtkTimer_A = player.AtkTimerMax_A;
								listMonsters.get(i).MobTarget = player.Name_A;
								gameControl.SetAttackFrame();
								
								if(CheckMobEvade()) { 
									Damage damage = new Damage();
									damage.DamagePosX = listMonsters.get(i).MobPosX;
									damage.DamagePosY = listMonsters.get(i).MobPosY;
									damage.DamageTime = 100;
									damage.DamageType = "mob";
									damage.DamageValue = 0;
									listDamage.add(damage);
									return; 
								}
								
								int st = player.Stamina_A;
								if(st > 0) { mobhp = mobhp - damagehit;  } else {  mobhp = mobhp - 5; }								
								if(mobhp < 0) { mobhp = 0; }
								listMonsters.get(i).MobHp = mobhp;
								
								if(listMonsters.get(i).MobHp <= 0) { 
									
									player.Target_A = "none";
									player.AtkTimer_A = player.AtkTimerMax_A;
									player.playerInBattle_A = "no";
								    player.playerInAttack_A = "no";
								    player.playerInCast_A = "no";	
								    autoattack = false;
								    
								    ItemDrop(listMonsters.get(i).MobName);
								    player.Money_A = player.Money_A + 2;
								    GiveExp(listMonsters.get(i).MobExp);
								    return;
								}
								
								Damage damage = new Damage();
								damage.DamagePosX = listMonsters.get(i).MobPosX;
								damage.DamagePosY = listMonsters.get(i).MobPosY;
								damage.DamageTime = 100;
								damage.DamageType = "mob";
								damage.DamageValue = damagehit;
								listDamage.add(damage);											
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
					for(int i = 0; i < listMonsters.size(); i++) {						
						if(listMonsters.get(i).MobTarget.equals(player.Name_A)) {
							if(player.PosX_A > (listMonsters.get(i).MobPosX - 5) && player.PosX_A < (listMonsters.get(i).MobPosX + 15)
								&& player.PosY_A > (listMonsters.get(i).MobPosY - 7) && player.PosY_A < (listMonsters.get(i).MobPosY + 18)) {
									
									listMonsters.get(i).MobAtkTimer--;
									if(listMonsters.get(i).MobAtkTimer <= 0) {
										int mobluck = randnumber.nextInt(100);
										if(mobluck > 5 && mobluck < 20) {
											player.Hp_A = player.Hp_A - ((listMonsters.get(i).MobAtk * 2) - player.Def_A);
										}
										if(mobluck >= 0 && mobluck < 5) {
											player.Hp_A = player.Hp_A - ((listMonsters.get(i).MobAtk * 3) - player.Def_A);
										}
										if(mobluck > 10) {
										{
											player.Hp_A = player.Hp_A - (listMonsters.get(i).MobAtk - player.Def_A);
										}								 
										listMonsters.get(i).MobAtkTimer = listMonsters.get(i).MobAtkTimerMax;
										Damage damage = new Damage();
										damage.DamagePosX = listMonsters.get(i).MobPosX;
										damage.DamagePosY = listMonsters.get(i).MobPosY;
										damage.DamageTime = 100;
										damage.DamageType = "player";
										damage.DamageValue = listMonsters.get(i).MobAtk;
										listDamage.add(damage);
									}	
									if(player.Hp_A <= 0) {
										playerDead = true;
									}
							}
						}				
					}
				}
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
		
				public void ShowBag() {
				//Common Itens
				for (int i = 0; i < 16; i++) {
					spr_item = ShowItem(i);
					if(spr_item != null) {				
						spr_item.draw(game.batch);
					}
				}	
				for (int i = 0; i < 16; i++) {
					spr_item = ShowItem(i);
					if(spr_item != null) {				
						font_master.draw(game.batch, ShowQuantityItem(i), spr_item.getX() + 9,spr_item.getY() + 7);
					}
				}
						
				//Crystal Itens    
				//slot 1
				if(!player.Crystal1_A.equals("none")) {
					spr_item = gameControl.GetItem(player.Crystal1_A);
					spr_item.setPosition(1.5f, 25);
					spr_item.setSize(9, 14);
					//spr_item.draw(game.batch);
				}
				
				if(!player.Crystal2_A.equals("none")) {
					spr_item = gameControl.GetItem(player.Crystal1_A);
					spr_item.setPosition(10.5f, 25);
					spr_item.setSize(9, 14);
					//spr_item.draw(game.batch); 
				}
				
				//slot 3
				if(!player.Crystal3_A.equals("none")) {
					spr_item = gameControl.GetItem(player.Crystal1_A);
					spr_item.setPosition(19.5f, 25);
					spr_item.setSize(9, 14);
					//spr_item.draw(game.batch); 
				}
				
				//slot 4
				if(!player.Crystal4_A.equals("none")) {
					spr_item = gameControl.GetItem(player.Crystal1_A);
					spr_item.setPosition(29f, 25);
					spr_item.setSize(9, 14);
					//spr_item.draw(game.batch); 
				}
		}
		
		public void CheckStatus(String status) {
			if(player.StatusPoint_A > 1) {
				if(status.equals("Str")) {
					player.Str_A = player.Str_A + 1;
					player.StatusPoint_A = player.StatusPoint_A - 1;
					player.Atk_A = player.Atk_A + 2;
				}
				if(status.equals("Vit")) {
					player.Vit_A = player.Vit_A + 1;
					player.StatusPoint_A = player.StatusPoint_A - 1;
					player.Def_A = player.Def_A + 2;
					player.HpMax_A = player.HpMax_A + 5;
				}
				if(status.equals("Agi")) {
					player.Agi_A = player.Agi_A + 1;
					player.StatusPoint_A = player.StatusPoint_A - 1;
					player.Def_A = player.Def_A + 1;
					player.AtkTimerMax_A = player.AtkTimerMax_A - 1;
				}
				if(status.equals("Wis")) {
					player.Wis_A = player.Wis_A + 1;
					player.StatusPoint_A = player.StatusPoint_A - 1;
					player.Def_A = player.Def_A + 1;
					player.MpMax_A = player.MpMax_A + 5;
				}
				if(status.equals("Dex")) {
					player.Dex_A = player.Dex_A + 1;
					player.StatusPoint_A = player.StatusPoint_A - 1;
					player.Atk_A = player.Atk_A + 1;
				}
				
			}
		}
		
		public Sprite ShowItem(int num) {
			String[] lstItem = player.Itens_A.split("-");
			String[] itemSplit;
			String item;
			
			item = lstItem[num];
			if(!item.equals("[NONE]")) {
				itemSplit = item.split("#");
				item = itemSplit[0].replace("[", "");
				spr_item = gameControl.GetItem(item);
				
				if(num == 0){ spr_item.setPosition(cameraCoordsX - 44.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 1){ spr_item.setPosition(cameraCoordsX - 30.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 2){ spr_item.setPosition(cameraCoordsX - 16.5f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 3){ spr_item.setPosition(cameraCoordsX - 2.6f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 4){ spr_item.setPosition(cameraCoordsX - 44.3f,cameraCoordsY + 17.7f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 5){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 6){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 7){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 8){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 9){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 10){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 11){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 12){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 13){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 14){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 15){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}	
				
			}
				
			return spr_item;
		}
		
		public String ShowQuantityItem(int num) {
			//Structure: [HPCAN#3]
			String qtd = "";
			String item = "";
			String[] lstItem = player.Itens_A.split("-");
			String[] itemSplit;
				
			item = lstItem[num];
			if(!item.equals("[NONE]")) {
				itemSplit = item.split("#");
				item = itemSplit[1].replace("]", "");		
				qtd = item;
			}
			else {
				qtd = "";
			}		
			return qtd;
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
		
		public void ShowPlayerDead() {
			countDead--;
			
			player.Target_A = "none";
			player.playerInBattle_A = "no";
			player.playerInAttack_A = "no";
			player.playerInCast_A = "no";
			autoattack = false;
			spr_master = gameControl.GetUX("textbar", 0, 0);
			spr_master.setPosition(cameraCoordsX -32f,cameraCoordsY -10);
			spr_master.setSize(60, 30);
			spr_master.draw(game.batch);
			font_master.getData().setScale(0.10f,0.15f);
			font_master.setUseIntegerPositions(false);
			font_master.draw(game.batch, "Voce morreu, retornando...",cameraCoordsX - 28,cameraCoordsY + 8);
			
			if(countDead <= 0) {
				player.Hp_A = 10;
				player.Mp_A = 10;
				player.Map_A = "MetroStation";
				player.PosX_A = 0;
				player.PosY_A = 0;
				gameControl.SaveData(player);
				this.screen.screenSwitch("LoadingScreen",network);
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
				
				if(player.PosX_A >= 127 && player.PosX_A <= 143 && player.PosY_A >= -140 && player.PosY_A <= -124 ) {
					state = "Shop";
					shopname = "refrishop";
				}
			}
		}

		
		
		
		
		public boolean CheckMobEvade() {
			int nextint = randnumber.nextInt(100);
			
			if(nextint < 10) {
				return true;
			}
			else {
				return false;
			}		
		}
		
		public void CheckMobDeadRespawn() {
			if(!network) {
				if(player.Map_A.equals("Sewers")) {
					for(int num = 0; num < listMonsters.size(); num++) {
						
						if(listMonsters.get(num).MobHp <= 0) {
							listMonsters.get(num).MobHp = 0; 
							listMonsters.get(num).MobDead = "yes";   
						}
						
						if(listMonsters.get(num).MobDead.equals("yes")) {
							listMonsters.get(num).MobPosX = 200;
							listMonsters.get(num).MobPosY = 200;
							listMonsters.get(num).MobTimeDead--;
							
							if(listMonsters.get(num).MobTimeDead <= 0) {
								listMonsters.get(num).MobTimeDead = 250;
								listMonsters.get(num).MobDead = "no";
								listMonsters.get(num).MobHp = listMonsters.get(num).MobHpMax;
								listMonsters.get(num).MobMp = listMonsters.get(num).MobMpMax;
								listMonsters.get(num).MobTarget = "none";
								listMonsters.get(num).MobPosX = 0;
								listMonsters.get(num).MobPosY = 0;
							}
						}
					}
			    }
			}
			
			if(network) {
				if(player.Map_A.equals("Sewers")) {
					for(int num = 0; num < listMonsters.size(); num++) {
						
						if(listMonsters.get(num).MobDead.equals("yes")) { 
							if(listMonsters.get(num).MobHp <= 0) {
								listMonsters.get(num).MobHp = 0; 
								listMonsters.get(num).MobPosX = 200;
								listMonsters.get(num).MobPosY = 200;
							}
						}
					}
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

		public int CheckWeapon() {  
		
			if(player.Weapon_A.equals("basic_knife")) { return 6;}			
			if(player.Weapon_A.equals("doubleedgeknife")) { return 3; }
			
			if(player.Weapon_A.equals("woodsword")) { return 10;}			
						
			if(player.Weapon_A.equals("basicpistol")) { return 8;}	
			
			if(player.Weapon_A.equals("basicdagger")) { return 7;}
			
			if(player.Weapon_A.equals("stickrod")) { return 6;}
			
			if(player.Weapon_A.equals("basicaxe")) { return 12;}
			
			
			return 0;
		}

		public int CheckCritical() {
			int chance = randnumber.nextInt(100);
			if(player.Luk_A > 90) {
				if(chance <= (player.Luk_A - 5)) {
					return 30;
				}
			}
			else {
				if(chance <= player.Luk_A) {
					return 30;
				}
			}		
			return 0;
		}

		public void ShowDamage() {
			
			if(listDamage.size() == 0) {
				return;
			}
			
			for(int i = 0; i < listDamage.size(); i++) {
				listDamage.get(i).DamagePosY = listDamage.get(i).DamagePosY + 0.4f;
				listDamage.get(i).DamageTime = listDamage.get(i).DamageTime - 1;
								
				font_master.getData().setScale(0.30f,0.35f);
				font_master.setUseIntegerPositions(false);
				if(listDamage.get(i).DamageType.equals("mob")) { font_master.setColor(Color.YELLOW); }
				if(listDamage.get(i).DamageType.equals("player")) { font_master.setColor(Color.RED); }
				if(listDamage.get(i).DamageType.equals("heal")) { font_master.setColor(Color.GREEN); }
				
				font_master.draw(game.batch, String.valueOf(listDamage.get(i).DamageValue), listDamage.get(i).DamagePosX, listDamage.get(i).DamagePosY);
				
				if(listDamage.get(i).DamageTime < 0) {
					listDamage.remove(listDamage.get(i));
				}

				font_master.setColor(Color.WHITE);
			}
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
						font_master.getData().setScale(0.10f,0.15f);
						font_master.setUseIntegerPositions(false);
						font_master.draw(game.batch, listMonsters.get(i).MobName,mobPositionCoordX, mobPositionCoordY);
						font_master.draw(game.batch, " HP :" + listMonsters.get(i).MobHp + "/" + listMonsters.get(i).MobHpMax ,mobPositionCoordX - 4, mobPositionCoordY - 8);
						
					}			
			}
		}

		public void ItemDrop(String mob) {
			int chance = randnumber.nextInt(100);
			
			if(mob.equals("slime")) {
				if(chance <= 40) { AddItemBag("hpcan"); itemdropname = "Adicionado Refrigerante de HP (P)"; showDropMsg = 100; return; }
				if(chance >= 40 && chance <= 95) { AddItemBag("hpcan"); itemdropname = "Adicionado Refrigerante de HP (P)"; showDropMsg = 100; return; }
				if(chance >= 95 && chance <= 98) { AddItemBag("hpcan"); itemdropname = "Adicionado Refrigerante de HP (P)"; showDropMsg = 100; return; }
				if(chance >= 98) { AddItemBag("hpcan"); itemdropname = "Adicionado Refrigerante de HP pequeno (P)"; showDropMsg = 100; return; }
			}
		
		}

		public void AddItemBag(String itemName) {
			String[] lstItem = player.Itens_A.split("-");
			String[] itemSplit;
			boolean exist = false;
			int qtd = 0;
			int posicaoItem = 0;
			String listaItemFinal;
			
			for(int i = 0; i < lstItem.length; i++) {
				if(lstItem[i].contains(itemName) && !exist) {
					posicaoItem = i;
					exist = true;
				}
			}
			
			if(exist) {
				itemSplit = lstItem[posicaoItem].split("#");
				qtd = Integer.parseInt(itemSplit[1].replace("]", ""));
				qtd++;
				if(qtd >= 99) { return;}
					lstItem[posicaoItem] = "[" + itemSplit[0].replace("[", "") + "#" + String.valueOf(qtd) + "]";
					listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
					listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
					player.Itens_A = listaItemFinal;
			}
			else {
				for(int i = 0; i < lstItem.length; i++) {
					if(lstItem[i].contains("[NONE]") && !exist) {
						posicaoItem = i;
						exist = true;
					}
				}
				
				if(exist) {
					lstItem[posicaoItem] = "[" + itemName + "#" + "1" + "]";
					listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
					listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
					player.Itens_A = listaItemFinal;
				}
			}
		}
		
		
		public void ShowCards() {
			//Basic Cards
			//Hotkey 1 / 2
			if(player.hotkey1_A.equals("none")) {
				spr_master = gameControl.GetCard("cardempty");
				spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 30);
				spr_master.draw(game.batch);
			}
			
			if(player.hotkey2_A.equals("none")) {
				spr_master = gameControl.GetCard("cardempty");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 30);
				spr_master.draw(game.batch);
			}
			
			if(player.hotkey1_A.equals("hpcan")) {
				spr_master = gameControl.GetCard("cardhp");
				spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 30);
				spr_master.draw(game.batch);
			}
			
			if(player.hotkey2_A.equals("hpcan")) {
				spr_master = gameControl.GetCard("cardhp");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 30);
				spr_master.draw(game.batch);
			}
			
			
			if(!player.Map_A.equals("Sewers")){
				spr_master = gameControl.GetCard("cardaction");
				spr_master.setPosition(cameraCoordsX + 47, cameraCoordsY - 60);
				spr_master.draw(game.batch);
			}

			if(player.Map_A.equals("Sewers")){ //cardactionON
				if(autoattack){
					spr_master = gameControl.GetCard("cardactionON");
					spr_master.setPosition(cameraCoordsX + 47, cameraCoordsY - 60);
					spr_master.draw(game.batch);
				}
				else{
					spr_master = gameControl.GetCard("cardaction");
					spr_master.setPosition(cameraCoordsX + 47, cameraCoordsY - 60);
					spr_master.draw(game.batch);
				}
			}
			
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
		
		//Give EXP
		public void GiveExp(int exp) {
			boolean levelup = false;
			if(player.Level_A == 10) {
				ExpSharedOnline = exp;
				return;
			}
			
			if(player.Level_A == 50) {
				ExpSharedOnline = exp;
				return;
			}
			
			player.Exp_A = player.Exp_A + exp;
			ExpSharedOnline = exp;
			
			//Sewers   
			if(player.Level_A == 1 && player.Exp_A >= 100) {  player.Level_A = 2; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true; }
			if(player.Level_A == 2 && player.Exp_A >= 150) {  player.Level_A = 3; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 3 && player.Exp_A >= 250) {  player.Level_A = 4; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 4 && player.Exp_A >= 360) {  player.Level_A = 5; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 5 && player.Exp_A >= 430) {  player.Level_A = 6; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 6 && player.Exp_A >= 500) {  player.Level_A = 7; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 7 && player.Exp_A >= 730) {  player.Level_A = 8; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 8 && player.Exp_A >= 1000) {  player.Level_A = 9; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 9 && player.Exp_A >= 1450) {  player.Level_A = 10; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			
			//Watercave
			if(player.Level_A == 10 && player.Exp_A >= 1840) {  player.Level_A = 11; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 11 && player.Exp_A >= 3330) {  player.Level_A = 12; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 12 && player.Exp_A >= 5500) {  player.Level_A = 13; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 13 && player.Exp_A >= 7600) {  player.Level_A = 14; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 14 && player.Exp_A >= 9929) {  player.Level_A = 15; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 15 && player.Exp_A >= 12820) {  player.Level_A = 16; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 16 && player.Exp_A >= 15293) {  player.Level_A = 17; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 17 && player.Exp_A >= 17300) {  player.Level_A = 18; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 18 && player.Exp_A >= 22402) {  player.Level_A = 19; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 19 && player.Exp_A >= 26902) {  player.Level_A = 20; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			
			//Mines
			if(player.Level_A == 20 && player.Exp_A >= 34592) {  player.Level_A = 21; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 21 && player.Exp_A >= 46923) {  player.Level_A = 22; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 22 && player.Exp_A >= 75829) {  player.Level_A = 23; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 23 && player.Exp_A >= 90234) {  player.Level_A = 24; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 24 && player.Exp_A >= 153042) {  player.Level_A = 25; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 25 && player.Exp_A >= 179232) {  player.Level_A = 26; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 26 && player.Exp_A >= 221011) {  player.Level_A = 27; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 27 && player.Exp_A >= 259323) {  player.Level_A = 28; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 28 && player.Exp_A >= 279293) {  player.Level_A = 29; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 29 && player.Exp_A >= 383421) {  player.Level_A = 30; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			
			//Snowpalace
			if(player.Level_A == 30 && player.Exp_A >= 593421)  {  player.Level_A = 31; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 31 && player.Exp_A >= 814402)  {  player.Level_A = 32; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 32 && player.Exp_A >= 1534611) {  player.Level_A = 33; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 33 && player.Exp_A >= 1839770) {  player.Level_A = 34; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 34 && player.Exp_A >= 2433026) {  player.Level_A = 35; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 35 && player.Exp_A >= 2792074) {  player.Level_A = 36; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 36 && player.Exp_A >= 2931441) {  player.Level_A = 37; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 37 && player.Exp_A >= 3304900) {  player.Level_A = 38; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 38 && player.Exp_A >= 3588905) {  player.Level_A = 39; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 39 && player.Exp_A >= 4987320) {  player.Level_A = 40; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			
			//Tower												   
			if(player.Level_A == 40 && player.Exp_A >= 9188696000f) {  player.Level_A = 41; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 41 && player.Exp_A >= 9288526000f) {  player.Level_A = 42; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 42 && player.Exp_A >= 9488446000f) {  player.Level_A = 43; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 43 && player.Exp_A >= 9588316000f) {  player.Level_A = 44; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 44 && player.Exp_A >= 9688236000f) {  player.Level_A = 45; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 45 && player.Exp_A >= 9798126000f) {  player.Level_A = 46; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 46 && player.Exp_A >= 9828646000f) {  player.Level_A = 47; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 47 && player.Exp_A >= 9878756009f) {  player.Level_A = 48; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 48 && player.Exp_A >= 9888866009f) {  player.Level_A = 49; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			if(player.Level_A == 49 && player.Exp_A >= 9999999999f) {  player.Level_A = 50; player.Exp_A = 0; player.HpMax_A = player.HpMax_A + 10; player.StatusPoint_A = player.StatusPoint_A + 6; levelup = true;}
			
			if(levelup) {
				if(player.Job_A.equals("Espadachim")) { player.HpMax_A = player.HpMax_A + 20; player.Atk_A = player.Atk_A + 5;}
				
				if(player.Job_A.equals("Feiticeiro")) { player.MpMax_A = player.MpMax_A + 10; player.Atk_A = player.Atk_A + 3;}
				
				if(player.Job_A.equals("Medico")) { player.MpMax_A = player.MpMax_A + 10; player.Atk_A = player.Atk_A + 3;}
				
				if(player.Job_A.equals("Pistoleiro")) { player.HpMax_A = player.HpMax_A + 10; player.Atk_A = player.Atk_A + 5; player.AtkTimerMax_A = player.AtkTimerMax_A -2;}
				
				if(player.Job_A.equals("Ladrao")) { player.HpMax_A = player.HpMax_A + 10; player.Atk_A = player.Atk_A + 5; player.AtkTimerMax_A = player.AtkTimerMax_A -5;}		
			}	
			
			levelup = false;
		}
		
		public int CheckLevelExpPercent() {
			//Sewers
			if(player.Level_A == 1) {  return 100;  }
			if(player.Level_A == 2) {  return 150;  }
			if(player.Level_A == 3) {  return 250; }
			if(player.Level_A == 4) {  return 360; }
			if(player.Level_A == 5) {  return 430;  }
			if(player.Level_A == 6) {  return 500;  }
			if(player.Level_A == 7) {  return 730; }
			if(player.Level_A == 8) {  return 1000;  }
			if(player.Level_A == 9) {  return 1450; }
			//Watercave
			if(player.Level_A == 10) {  return 1840;}
			if(player.Level_A == 11) {  return 3330;}
			if(player.Level_A == 12) {  return 5500;}
			if(player.Level_A == 13) {  return 7600;}
			if(player.Level_A == 14) {  return 9929;}
			if(player.Level_A == 15) {  return 12820;}
			if(player.Level_A == 16) {  return 15293;}
			if(player.Level_A == 17) {  return 17300;}
			if(player.Level_A == 18) {  return 22402;}
			if(player.Level_A == 19) {  return 26902;}
			//Mines
			if(player.Level_A == 20) {  return 34592; }
			if(player.Level_A == 21) {  return 46923;}
			if(player.Level_A == 22) {  return 75829;}
			if(player.Level_A == 23) {  return 90234;}
			if(player.Level_A == 24) {  return 153042;}
			if(player.Level_A == 25) {  return 179232;}
			if(player.Level_A == 26) {  return 221011;}
			if(player.Level_A == 27) {  return 259323;}
			if(player.Level_A == 28) {  return 279293;}
			if(player.Level_A == 29) {  return 383421;}
			//Snowpalace
			if(player.Level_A == 30)  {  return 593421;}
			if(player.Level_A == 31)  {  return 814402;}
			if(player.Level_A == 32) {  return 1534611;}
			if(player.Level_A == 33) {  return 1839770;}
			if(player.Level_A == 34) {  return 2433026;}
			if(player.Level_A == 35) {  return 2792074;}
			if(player.Level_A == 36) {  return 2931441;}
			if(player.Level_A == 37) {  return 3304900;}
			if(player.Level_A == 38) {  return 3588905;}
			if(player.Level_A == 39) {  return 4987320;}
			//Tower															   
			if(player.Level_A == 40) {  return 159432300;}
			if(player.Level_A == 41) {  return 318864600;}
			if(player.Level_A == 42) {  return 418864600;}
			if(player.Level_A == 43) {  return 518864600;}
			if(player.Level_A == 44) {  return 618864600;}
			if(player.Level_A == 45) {  return 718864600;}
			if(player.Level_A == 46) {  return 818864600;}
			if(player.Level_A == 47) {  return 918864600;}
			if(player.Level_A == 48) {  return 958864600;}
			if(player.Level_A == 49) {  return 999999999;}
			if(player.Level_A == 50) {  return 999999999;}
			
			return 1000;
		}
		
		//Hotkey
		public void HotKeyItem(int itemNum, int hotkeynum) {
			String[] lstItem = player.Itens_A.split("-");
			String[] itemSplit = lstItem[itemNum].split("#");
			String item = "";
			//hotketcountitem = itemNum;
			
			
			if(hotkeynum == 1) {
				hotketcountitem1 = itemNum;
			}
			
			if(hotkeynum == 2) {
				hotketcountitem2 = itemNum;
			}
						
			item = itemSplit[0].replace("[", "");
			
			if(hotkeynum == 1) {
				if(item.equals("hpcan")) { player.hotkey1_A = item; return; }
				if(item.equals("garrafadrink")) { player.hotkey1_A = item; return; }
				if(item.equals("mpcan")) { player.hotkey1_A = item; return; }
				if(item.equals("garrafamagica")) { player.hotkey1_A = item; return; }
				if(item.equals("stcan")) { player.hotkey1_A = item; return; }
				if(item.equals("garrafasuco")) { player.hotkey1_A = item; return; }
			}
			if(hotkeynum == 2) {
				if(item.equals("hpcan")) { player.hotkey2_A = item; return; }
				if(item.equals("garrafadrink")) { player.hotkey2_A = item; return; }
				if(item.equals("mpcan")) { player.hotkey2_A = item; return; }
				if(item.equals("garrafamagica")) { player.hotkey2_A = item; return; }
				if(item.equals("stcan")) { player.hotkey2_A = item; return; }
				if(item.equals("garrafasuco")) { player.hotkey2_A = item; return; }
			}
		}

		@Override
		public void input(String input) {
			
			if(!network) {
				lstChats.add(0, input);
				if(lstChats.size() > 10)
					lstChats.remove(lstChats.size() - 1);
			}
			else 
			{
				if(input.contains(":")) { return; }
				if(input.contains("none")) { return; }
				String text = input;
				onlineresponse = gameControl.OnlineManager("Chat",text,"");
			}
		}

		@Override
		public void canceled() {
			
		}

		@Override
		public boolean keyDown(int keycode) {
			if(playerDead) { return false; }
			if(player.playerSit_A.equals("yes")){ return false; }
			
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

			gameControl.UpdatePlayer(player);
			
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
			
			if(state.equals("Menu")) { return; }
			
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
					state = "Menu";
					return false;
				}
				
				//Action
				if(coordsTouch.x > cameraCoordsX + 46 && coordsTouch.x < cameraCoordsX + 57 && coordsTouch.y > cameraCoordsY -60 && coordsTouch.y < cameraCoordsY -35) {
					CheckAction();
					if(autoattack){
						autoattack = false;
					}
					else
					{
						autoattack = true;
					}
					return false;
				}
				//Block
				if(coordsTouch.x > cameraCoordsX + 62 && coordsTouch.x < cameraCoordsX + 73 && coordsTouch.y > cameraCoordsY -60 && coordsTouch.y < cameraCoordsY -35) {
					return false;
				}
				//Target
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY -60 && coordsTouch.y < cameraCoordsY -35) {
					ChangeTarget();
					return false;
				}
				//Skill 1
				if(coordsTouch.x > cameraCoordsX + 47 && coordsTouch.x < cameraCoordsX + 56 && coordsTouch.y > cameraCoordsY - 90 && coordsTouch.y < cameraCoordsY - 66) {
					
					return false;
				}
				//Skill 2
				if(coordsTouch.x > cameraCoordsX + 63 && coordsTouch.x < cameraCoordsX + 72 && coordsTouch.y > cameraCoordsY - 90 && coordsTouch.y < cameraCoordsY - 66) {
					
					return false;
				}
				//Skill 3
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY - 90 && coordsTouch.y < cameraCoordsY - 66) {
					
					return false;
				}
				//Sit 
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY + 1 && coordsTouch.y < cameraCoordsY + 24) {
					if(!player.playerInBattle_A.equals("yes")) {
						if(player.playerSit_A.equals("none")) {
							player.playerSit_A = "yes";
						}
						else {
							player.playerSit_A = "none";
						}
					}
					return false;
				}
				
				//hotkey1
				if(coordsTouch.x > cameraCoordsX + 62 && coordsTouch.x < cameraCoordsX + 72 && coordsTouch.y > cameraCoordsY - 30 && coordsTouch.y < cameraCoordsY - 6) {
					if(!player.hotkey1_A.equals("none")) {
						gameControl.UseItem(hotketcountitem1);
					}
					return false;
				}
				
				//hotkey2
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY - 30 && coordsTouch.y < cameraCoordsY - 6) {
					if(!player.hotkey2_A.equals("none")) {
						gameControl.UseItem(hotketcountitem2);
					}
					return false;
				}
				
				//Chat
				if(coordsTouch.x > cameraCoordsX - 60 && coordsTouch.x < cameraCoordsX - 49 && coordsTouch.y > cameraCoordsY + 77 && coordsTouch.y < cameraCoordsY + 97) {
					Gdx.input.getTextInput(this,"Digite sua mensagem","","");
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
			if(state.equals("Menu")) {
				
				//Voltar
				if(coordsTouch.x > cameraCoordsX + 68 && coordsTouch.x < cameraCoordsX + 82 && coordsTouch.y > cameraCoordsY + 69 && coordsTouch.y < cameraCoordsY + 84) {
					state = "Main";
					return false;
				}
				
				//Hotkey 1
				if(coordsTouch.x > cameraCoordsX - 30 && coordsTouch.x < cameraCoordsX - 10 && coordsTouch.y > cameraCoordsY + 65 && coordsTouch.y < cameraCoordsY + 78) {
					menuoption = "hotkey1";
					return false;
				}
				
				//Hotkey 2
				if(coordsTouch.x > cameraCoordsX - 8 && coordsTouch.x < cameraCoordsX + 10 && coordsTouch.y > cameraCoordsY + 65 && coordsTouch.y < cameraCoordsY + 78) {
					menuoption = "hotkey2";
					return false;
				}
				
				
				//Item 1
				if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 32 && coordsTouch.y > cameraCoordsY + 42 && coordsTouch.y < cameraCoordsY + 64) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(0,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(0,2); menuoption = ""; return false; }
					gameControl.UseItem(0);
					return false;
				}
				
				//Item 2
				if(coordsTouch.x > cameraCoordsX - 30 && coordsTouch.x < cameraCoordsX - 18 && coordsTouch.y > cameraCoordsY + 42 && coordsTouch.y < cameraCoordsY + 64) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(1,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(1,2); menuoption = ""; return false; }
					gameControl.UseItem(1);
					return false;
				}
				
				//Item 3
				if(coordsTouch.x > cameraCoordsX - 16 && coordsTouch.x < cameraCoordsX - 4 && coordsTouch.y > cameraCoordsY + 42 && coordsTouch.y < cameraCoordsY + 64) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(2,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(2,2); menuoption = ""; return false; }
					gameControl.UseItem(2);
					return false;
				}
				
				//Item 4
				if(coordsTouch.x > cameraCoordsX - 3 && coordsTouch.x < cameraCoordsX + 10 && coordsTouch.y > cameraCoordsY + 42 && coordsTouch.y < cameraCoordsY + 64) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(3,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(3,2); menuoption = ""; return false; }
					gameControl.UseItem(3);
					return false;
				}
				
				
				
				//Item 5
				if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 32 && coordsTouch.y > cameraCoordsY + 19 && coordsTouch.y < cameraCoordsY + 39) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(4,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(4,2); menuoption = ""; return false; }
					gameControl.UseItem(4);
					return false;
				}
				
				//Item 6
				if(coordsTouch.x > cameraCoordsX - 30 && coordsTouch.x < cameraCoordsX - 18 && coordsTouch.y > cameraCoordsY + 19 && coordsTouch.y < cameraCoordsY + 39) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(5,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(5,2); menuoption = ""; return false; }
					gameControl.UseItem(5);
					return false;
				}
				
				//Item 7
				if(coordsTouch.x > cameraCoordsX - 16 && coordsTouch.x < cameraCoordsX - 4 && coordsTouch.y > cameraCoordsY + 19 && coordsTouch.y < cameraCoordsY + 39) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(6,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(6,2); menuoption = ""; return false; }
					gameControl.UseItem(6);
					return false;
				}
				
				//Item 8
				if(coordsTouch.x > cameraCoordsX - 3 && coordsTouch.x < cameraCoordsX + 10 && coordsTouch.y > cameraCoordsY + 19 && coordsTouch.y < cameraCoordsY + 39) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(7,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(7,2); menuoption = ""; return false; }
					gameControl.UseItem(7);
					return false;
				}
				
				//Item 9
				if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 32 && coordsTouch.y > cameraCoordsY - 5 && coordsTouch.y < cameraCoordsY + 15) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(8,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(8,2); menuoption = ""; return false; }
					gameControl.UseItem(8);
					return false;
				}
				
				//Item 10
				if(coordsTouch.x > cameraCoordsX - 30 && coordsTouch.x < cameraCoordsX - 18 && coordsTouch.y > cameraCoordsY - 5 && coordsTouch.y < cameraCoordsY + 15) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(9,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(9,2); menuoption = ""; return false; }
					gameControl.UseItem(9);
					return false;
				}
				
				//Item 11
				if(coordsTouch.x > cameraCoordsX - 16 && coordsTouch.x < cameraCoordsX - 4 && coordsTouch.y > cameraCoordsY - 5 && coordsTouch.y < cameraCoordsY + 15) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(10,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(10,2); menuoption = ""; return false;  }
					gameControl.UseItem(10);
					return false;
				}
				
				//Item 12
				if(coordsTouch.x > cameraCoordsX - 3 && coordsTouch.x < cameraCoordsX + 10 && coordsTouch.y > cameraCoordsY - 5 && coordsTouch.y < cameraCoordsY + 15) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(11,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(11,2); menuoption = ""; return false;  }
					gameControl.UseItem(11);
					return false;
				}
				
				//Item 13
				if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 32 && coordsTouch.y > cameraCoordsY - 8 && coordsTouch.y < cameraCoordsY - 30) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(12,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(12,2); menuoption = ""; return false;  }
					gameControl.UseItem(12);
					return false;
				}
				
				//Item 14
				if(coordsTouch.x > cameraCoordsX - 30 && coordsTouch.x < cameraCoordsX - 18 && coordsTouch.y > cameraCoordsY - 8 && coordsTouch.y < cameraCoordsY - 30) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(13,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(13,2); menuoption = ""; return false;  }
					gameControl.UseItem(13);
					return false;
				}
				
				//Item 15
				if(coordsTouch.x > cameraCoordsX - 16 && coordsTouch.x < cameraCoordsX - 4 && coordsTouch.y > cameraCoordsY - 8 && coordsTouch.y < cameraCoordsY - 30) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(14,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(14,2); menuoption = ""; return false;  }
					gameControl.UseItem(14);
					return false;
				}
				
				//Item 16
				if(coordsTouch.x > cameraCoordsX - 3 && coordsTouch.x < cameraCoordsX + 10 && coordsTouch.y > cameraCoordsY - 8 && coordsTouch.y < cameraCoordsY - 30) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(15,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(15,2); menuoption = ""; return false;  }
					gameControl.UseItem(15);
					return false;
				}
				
				
				//Status point
				//STR
				if(coordsTouch.x > cameraCoordsX - 81 && coordsTouch.x < cameraCoordsX - 69 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					CheckStatus("Str");
					return false;
				}
				//VIT
				if(coordsTouch.x > cameraCoordsX - 67 && coordsTouch.x < cameraCoordsX - 55 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					CheckStatus("Vit");
					return false;
				}
				//AGI
				if(coordsTouch.x > cameraCoordsX - 54 && coordsTouch.x < cameraCoordsX - 41 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					CheckStatus("Agi");
					return false;
				}
				//WIS
				if(coordsTouch.x > cameraCoordsX - 40 && coordsTouch.x < cameraCoordsX - 27 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					CheckStatus("Wis");
					return false;
				}
				//DES
				if(coordsTouch.x > cameraCoordsX - 26 && coordsTouch.x < cameraCoordsX - 14 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					CheckStatus("Des");
					return false;
				}
				
				//config
				if(coordsTouch.x > cameraCoordsX + 28 && coordsTouch.x < cameraCoordsX + 40 && coordsTouch.y > cameraCoordsY - 35 && coordsTouch.y < cameraCoordsY - 13) {
					onlineresponse = gameControl.OnlineManager("Upload","","");
					if(onlineresponse.equals("Atualizado")) {
						msgShowTime = 100;
					}
					return false;
				}
			}
			
			//Shops
			if(state.equals("Shop")) {
				if(shopname.equals("refrishop")) { 
					if(coordsTouch.x > cameraCoordsX - 61 && coordsTouch.x < cameraCoordsX - 47 && coordsTouch.y > cameraCoordsY + 37 && coordsTouch.y < cameraCoordsY + 59) {
						showbuymsg = gameControl.CheckBuyItemStreetsA("refrishop", 1);
						return false; 
					}
				}
				
				if(coordsTouch.x > cameraCoordsX + 51 && coordsTouch.x < cameraCoordsX + 61 && coordsTouch.y > cameraCoordsY + 60 && coordsTouch.y < cameraCoordsY + 75) {
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
			if(player.playerSit_A.equals("yes")){ return false; }
			
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
