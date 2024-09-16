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
import com.moonbolt.cityscale.interfaces.HttpCallback;
import com.moonbolt.cityscale.models.Damage;
import com.moonbolt.cityscale.models.Monster;
import com.moonbolt.cityscale.models.Player;
import com.moonbolt.cityscale.models.Skill;


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
    private int playernum = 0;
    private int flipzone = 0;
    
    //Variables usables
    private float floatUseA = 0;
    private float floatUseB = 0;
    private float floatUseC = 0;
    private float floatUseD = 0;
    private int intUseA = 0;
    private int intUseB = 0;
    private int intUseC = 0;
    private int intUseD = 0;
    
    //Aviso
    private boolean aviso = false;
	private int avisoTimer = 0;
	private String avisoMsg = "";
    
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
    private Sprite spr_playerhatmenu;
    private Sprite spr_playerhat;
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
	private int hotketcountitem1;
	private int hotketcountitem2;
	private int skillUsed = 0; 
	private boolean notmp = false;
	private boolean rangedAttack = false;
    private boolean skillEffect = false;
    private String skillname = "";
    private float touchSkillX;
    private float touchSkillY;
    private boolean selectAreaRanged = false;
    private Sprite spr_sit;
    private int regenTime = 0;
    
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
	private ArrayList<String> lstChats;
	private String itemdropname;
	private int SysMsgCount = 0;    
    private int savedataTime = 500;
	private int showDropMsg;
	private Sprite spr_shop;
	private String showbuymsg = "";
	private int showbuymsgtime = 2000;
	private boolean showZone = false;
    private int countZoneSkill = 0;

	//Online
	private ArrayList<Player> lstOnlinePlayers;
	private Sprite spr_playerTopOnline;
    private Sprite spr_playerBottomOnline;
    private Sprite spr_playerFooterOnline;
    private Sprite spr_playerHairOnline;
    private Sprite spr_playerHatOnline;
    private Sprite spr_weaponOnline;
    private boolean onlineAuth = false;
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
    private Texture tex_BackgroundOver;
    
    //Teste Dot
    private Sprite spr_testeDot;
    private Texture tex_testeDot;
    
    //Controller
    private final IntSet downKeys = new IntSet(20);	

		public GameMap(MainGame _game, ManagerScreen _screen, boolean _network, String account, int _playernumber, ArrayList<Player> lstPlayer) {
			
			this.game = _game;	
			this.screen = _screen;
			this.randnumber = new Random();
			this.network = _network;
			this.playernum = _playernumber;
			
			if(_playernumber == 1) { player = lstPlayer.get(0); }
			if(_playernumber == 2) { player = lstPlayer.get(1); }
			if(_playernumber == 3) { player = lstPlayer.get(2); }

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
			
			//Just for coloring
			Gdx.gl.glClearColor(1,1,1,1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			//Camera Ajustments
			cameraCoordsX = Float.parseFloat(player.PosX);
			cameraCoordsY = Float.parseFloat(player.PosY);
			
			//Follow camera
			floatUseA = Float.parseFloat(player.PosX); 
			if(floatUseA <= 18.5f) 	{ cameraCoordsX = 18.5f; }
			if(floatUseA >= 93) 	{ cameraCoordsX = 93; 	 }
			floatUseB = Float.parseFloat(player.PosY);
			if(floatUseB >= -22f) { cameraCoordsY = -22f; }
			if(floatUseB <= -97) 	{ cameraCoordsY = -97;  }
			
			//Update camera and start drawling
			camera.position.set(cameraCoordsX -2,cameraCoordsY+1,0);
			camera.update();
		    game.batch.setProjectionMatrix(camera.combined);	    
			game.batch.begin();
			
			//Save
			savedataTime--;
			if(savedataTime < 0) {
				savedataTime = 500;
				gameControl.SaveData(player);
			}
			
			//Check Regen
			intUseA = Integer.parseInt(player.regenTime);
			intUseA--;
			if(intUseA < 0) {
				intUseA = Integer.parseInt(player.Hp);
				intUseB = Integer.parseInt(player.Mp);
				intUseC = Integer.parseInt(player.HpMax);
				intUseD = Integer.parseInt(player.MpMax);
				
				intUseA = intUseA + 10;
				intUseB = intUseB + 10;
				
				if(intUseA >= intUseC) { player.Hp = player.HpMax; }
				if(intUseB >= intUseB) { player.Mp = player.MpMax; }
				
				player.regenTime = player.regenTimeMax;
			}
			
			//Map Render
			if(player.Map.equals("StreetsA")) {
				spr_Background.setPosition(-81,-194);
				spr_Background.setSize(270, 270);
				spr_Background.draw(game.batch);
			}
			
			//Background	
			if(player.Map.equals("Sewers")) {
				flipzone++;
				if (flipzone >= 0 && flipzone <= 20) {
					spr_Background.setPosition(-81, -194);
					spr_Background.setSize(270, 270);
					spr_Background.draw(game.batch);
				}
				if (flipzone >= 20 && flipzone <= 40) {
					spr_Background.setPosition(-81, -194);
					spr_Background.setSize(270, 270);
					spr_Background.draw(game.batch);
				}
				if (flipzone >= 40) { flipzone = 0; }
			}
			
			//npcs
			//ShowOnlinePlayers();
			//ShowNPCs();
			
			//Char
			player = gameControl.SetCharMov(player, player.breakwalk);
			
			spr_playerfooter = gameControl.GetFooterChar(player, "no",0,0);
			spr_playerfooter.draw(game.batch);
			
			spr_playerbottom = gameControl.GetBottomChar(player, "no",0,0);
			spr_playerbottom.draw(game.batch);
							
			spr_playertop = gameControl.GetTopChar(player, "no", 0,0);
			spr_playertop.draw(game.batch);
			
			spr_playerhair = gameControl.GetHairChar(player, "no",0,0);
			spr_playerhair.draw(game.batch);
			
			if(!player.Hat.equals("none")) {
				spr_playerhat = gameControl.GetHatChar(player,"",0,0);
				spr_playerhat.draw(game.batch);
			}
			
			
			if(player.playerInBattle.equals("yes") || player.playerInBattle.equals("yes") || player.playerInBattle.equals("yes")) {
				spr_playerweapon = gameControl.SetWeapon(player);
				spr_playerweapon.draw(game.batch);
			}

			//Show Mobs
			if(player.Map.equals("Sewers")){ ShowMobs(); }
		
			//UX
			spr_playerTag = gameControl.GetUX("playertag",cameraCoordsX, cameraCoordsY);
			spr_playerTag.draw(game.batch);
			
			spr_playerTagHair = gameControl.GetHairCharTag(player, cameraCoordsX, cameraCoordsY);
			spr_playerTagHair.draw(game.batch);
			
			
			font_master.getData().setScale(0.15f,0.26f);
			font_master.draw(game.batch, "X:" + player.PosX, cameraCoordsX - 98f, cameraCoordsY + 53.7f);
			font_master.draw(game.batch, "Y:" + player.PosY, cameraCoordsX - 78f, cameraCoordsY + 53.7f);
			
			spr_sit = gameControl.GetUX("btnsit", cameraCoordsX, cameraCoordsY);
			spr_sit.draw(game.batch);
			
			
			font_master.draw(game.batch, player.Name, cameraCoordsX - 88f, cameraCoordsY + 93.7f);
			font_master.draw(game.batch, String.valueOf(player.Hp + "/" + player.HpMax), cameraCoordsX - 85f, cameraCoordsY + 82f);
			font_master.draw(game.batch, String.valueOf(player.Mp + "/" + player.MpMax), cameraCoordsX - 85f, cameraCoordsY + 73.7f);
			font_master.draw(game.batch, String.valueOf(player.Level), cameraCoordsX - 88f, cameraCoordsY + 64f);
			font_master.draw(game.batch, gameControl.ExpPercent(player) + "%", cameraCoordsX - 72f, cameraCoordsY + 64f);
			
			spr_master = gameControl.GetUX("innerpad", cameraCoordsX, cameraCoordsY);
			spr_master.setPosition(cameraCoordsX + padmoveX,cameraCoordsY + padmoveY);
			spr_master.draw(game.batch);
			
			//Ranged Skill
			if(selectAreaRanged) {
				spr_master = gameControl.GetUX("textbar", 0, 0);
				spr_master.setPosition(cameraCoordsX - 33, cameraCoordsY + 35);
				spr_master.setSize(60, 15);
				spr_master.draw(game.batch);
				font_master.draw(game.batch, "Selecione a Area",cameraCoordsX - 20, cameraCoordsY + 46);
			}
			if(showZone) {
				if(countZoneSkill > 0) {
					spr_master = gameControl.GetUX("target", 0, 0);
					spr_master.setPosition(touchSkillX -5, touchSkillY - 8);
					spr_master.setSize(10,20);
					spr_master.draw(game.batch);
					countZoneSkill--;
				}
				else {
					showZone = false;
					countZoneSkill = 0;
				}			
			}
			
			//Checks e Cards
			//ShowCards();
			//CheckColision();
			//CheckAutoAttack();
			//CheckMobAutoAttack();
			//CheckMobDeadRespawn();
			//ShowDamage();
			//ShowSkill();
			
			if(playerDead) { } //ShowPlayerDead(); }
			
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
				font_master.draw(game.batch, String.valueOf(player.Str), cameraCoordsX - 76f, cameraCoordsY - 50f);
				font_master.draw(game.batch, String.valueOf(player.Vit), cameraCoordsX - 62f, cameraCoordsY - 50f);
				font_master.draw(game.batch, String.valueOf(player.Agi), cameraCoordsX - 48f, cameraCoordsY - 50f);
				font_master.draw(game.batch, String.valueOf(player.Wis), cameraCoordsX - 34f, cameraCoordsY - 50f);
				font_master.draw(game.batch, String.valueOf(player.Dex), cameraCoordsX - 21f, cameraCoordsY - 50f);
				font_master.draw(game.batch, String.valueOf(player.StatusPoint), cameraCoordsX - 43f, cameraCoordsY - 67f);
				
				font_master.draw(game.batch,"Dinheiro:" + String.valueOf(player.Money),cameraCoordsX - 10f, cameraCoordsY - 67f);		
				font_master.draw(game.batch,"Exp:" + String.valueOf(player.Exp),cameraCoordsX + 20f, cameraCoordsY - 67f);			
				
				//CharacterShow
				spr_playerhair = gameControl.GetHairChar(player, "Show", cameraCoordsX, cameraCoordsY);
				spr_playerhair.draw(game.batch);
				
				spr_playerfooter = gameControl.GetFooterChar(player, "Show", cameraCoordsX, cameraCoordsY);
				spr_playerfooter.draw(game.batch);
				
				spr_playerbottom = gameControl.GetBottomChar(player, "Show", cameraCoordsX, cameraCoordsY);
				spr_playerbottom.draw(game.batch);
				
				spr_playertop = gameControl.GetTopChar(player, "Show", cameraCoordsX, cameraCoordsY);
				spr_playertop.draw(game.batch);
				
				if(!player.Hat.equals("none")) {
					spr_playerhatmenu = gameControl.GetHatChar(player,"",cameraCoordsX, cameraCoordsY);
					spr_playerhatmenu.setScale(0.5f, 0.7f);
					spr_playerhatmenu.setPosition(cameraCoordsX - 84, cameraCoordsY + 42);
					spr_playerhatmenu.draw(game.batch);
				}
				

				//Show Character
				//Itens Equipped
				spr_playerfooter = gameControl.GetItem(player.SetFooter, player);
				spr_playerfooter.setPosition(cameraCoordsX + 13, cameraCoordsY + 41);
				spr_playerfooter.setSize(13, 22);
				spr_playerfooter.draw(game.batch);
				
				spr_playerbottom = gameControl.GetItem(player.SetBottom, player);
				spr_playerbottom.setPosition(cameraCoordsX + 27, cameraCoordsY + 41);
				spr_playerbottom.setSize(13, 22);
				spr_playerbottom.draw(game.batch);
				
				spr_playertop = gameControl.GetItem(player.SetUpper, player);
				spr_playertop.setPosition(cameraCoordsX + 41, cameraCoordsY + 41);
				spr_playertop.setSize(13, 22);
				spr_playertop.draw(game.batch);
				
				spr_playerweapon = gameControl.GetItem(player.Weapon, player);
				spr_playerweapon.setPosition(cameraCoordsX + 54.4f, cameraCoordsY + 41);
				spr_playerweapon.setSize(13, 22);
				spr_playerweapon.draw(game.batch);
				
				if(!player.Hat.equals("none")) { 
					spr_playerhat = gameControl.GetHatItem(player,0,0);
					spr_playerhat.setPosition(cameraCoordsX + 67.8f, cameraCoordsY + 41);
					spr_playerhat.setSize(13, 22);
					spr_playerhat.draw(game.batch);
				}
				
				ShowBag();
				
				//Mensagens Aviso 
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
				
				
				if(menuoption.equals("hotkey1")) { spr_master = gameControl.GetUX("hotkey1",cameraCoordsX + 16, cameraCoordsY - 23); spr_master.draw(game.batch); }
				if(menuoption.equals("hotkey2")) { spr_master = gameControl.GetUX("hotkey1",cameraCoordsX + 36, cameraCoordsY - 23); spr_master.draw(game.batch); }
				if(menuoption.equals("descartar")) { 
					spr_master = gameControl.GetUX("descartar",cameraCoordsX, cameraCoordsY); 
					spr_master.setPosition(cameraCoordsX - 12, cameraCoordsY - 63);
					spr_master.draw(game.batch); 
				}
				
			}
			
			if(state.equals("Shop")) {
				
				if(shopname.equals("refrishop")) {
					spr_shop = gameControl.GetShops("refrishop",cameraCoordsX, cameraCoordsY);
					spr_shop.draw(game.batch);		
					font_master.draw(game.batch, String.valueOf(player.Money), cameraCoordsX - 25, cameraCoordsY - 37);				
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
			
			//spr_testeDot.setPosition(cameraCoordsX + 68,cameraCoordsY + 62);
			//spr_testeDot.setSize(1, 1);
			//spr_testeDot.draw(game.batch);

			//spr_testeDot.setPosition(cameraCoordsX + 80,cameraCoordsY + 40);
			//spr_testeDot.setSize(1, 1);
			//spr_testeDot.draw(game.batch);
			
			game.batch.end();
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
			if(!player.Crystal1.equals("none")) {
				spr_item = gameControl.GetItem(player.Crystal1, player);
				spr_item.setPosition(1.5f, 25);
				spr_item.setSize(9, 14);
				//spr_item.draw(game.batch);
			}
			
			if(!player.Crystal2.equals("none")) {
				spr_item = gameControl.GetItem(player.Crystal1, player);
				spr_item.setPosition(10.5f, 25);
				spr_item.setSize(9, 14);
				//spr_item.draw(game.batch); 
			}
			
			//slot 3
			if(!player.Crystal3.equals("none")) {
				spr_item = gameControl.GetItem(player.Crystal1, player);
				spr_item.setPosition(19.5f, 25);
				spr_item.setSize(9, 14);
				//spr_item.draw(game.batch); 
			}
			
			//slot 4
			if(!player.Crystal4.equals("none")) {
				spr_item = gameControl.GetItem(player.Crystal1, player);
				spr_item.setPosition(29f, 25);
				spr_item.setSize(9, 14);
				//spr_item.draw(game.batch); 
			}
		}
		
		public String ShowQuantityItem(int num) {
			//Structure: [HPCAN#3]
			String qtd = "";
			String item = "";
			String[] lstItem = player.Itens.split("-");
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
		
		public Sprite ShowItem(int num) {
			String[] lstItem = player.Itens.split("-");
			String[] itemSplit;
			String item;
			
			item = lstItem[num];
			if(!item.equals("[NONE]")) {
				itemSplit = item.split("#");
				item = itemSplit[0].replace("[", "");
				spr_item = gameControl.GetItem(item, player);
				
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
		
		public void CheckAreaRangedSkill() {
			if(player.Map_A.equals("Sewers") && autoattack) {
				for(int i = 0; i < listMonsters.size(); i++) {
					
					//Close Ranged
					if(player.Target_A.equals(listMonsters.get(i).MobID) && !rangedAttack) {		 
						if((listMonsters.get(i).MobPosX + 5) > (player.PosX_A - 5) && (listMonsters.get(i).MobPosX + 5) < (player.PosX_A + 15)
						   && (listMonsters.get(i).MobPosY + 5) > (player.PosY_A - 7) && (listMonsters.get(i).MobPosY + 5) < (player.PosY_A + 18)) {
							player.playerInBattle_A = "yes";
							listMonsters.get(i).MobTarget = player.Name_A;				
							//Aprendiz
							if(skillname.equals("tripleattack")) {
								int atkweapon = CheckWeapon();
								int totaldmg = player.Atk_A + ((player.Str_A * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "tripleattack";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX +5;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY + 5;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX + 5;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY + 5;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }							
							}
							if(skillname.equals("hammercrash")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Str_A * 2) + (player.Vit_A * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "tripleattack";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
							}
							if(skillname.equals("flysword")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Str_A * 3) + (player.Agi_A * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "tripleattack";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
							}
							if(skillname.equals("poisonhit")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Luk_A * 2)+ (player.Str_A * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								listMonsters.get(i).MobHp = mobHP;
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "tripleattack";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
							}
							if(skillname.equals("steal")) {
								
							}
							if(skillname.equals("overpower")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Vit_A * 3) + (player.Str_A * 5) + (player.Luk_A * 2) + atkweapon);	
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }					
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "overpower";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
							}
						}
					}
					
					//Long Ranged
					if(rangedAttack) {	
					
						if(skillname.equals("heal")) {
							player.Hp_A = player.Hp_A + (player.Wis_A * 3);
							if(player.Hp_A > player.HpMax_A) {player.Hp_A = player.HpMax_A; }
							rangedAttack = false; 
							skillEffect = true;
							Skill skillInUse = new Skill();
							Damage damageSkill = new Damage();
							skillInUse.SkillName = "heal";
							skillInUse.SkillPosX = player.PosX_A;
							skillInUse.SkillPosY = player.PosY_A;
							damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
							damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							damageSkill.DamageTime = 100;
							damageSkill.DamageType = "heal";
							damageSkill.DamageValue = 0;
							return;
						}
						
						if(skillname.equals("defboost")) { 
							GiveBuff("defboost"); 
							rangedAttack = false; 
							skillEffect = true;
							Skill skillInUse = new Skill();
							Damage damageSkill = new Damage();
							skillInUse.SkillName = "defboost";
							skillInUse.SkillPosX = player.PosX_A;
							skillInUse.SkillPosY = player.PosY_A;
							damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
							damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							damageSkill.DamageTime = 100;
							damageSkill.DamageType = "heal";
							damageSkill.DamageValue = 0;
							return; 	
						}						
						if(skillname.equals("healthboost")) { GiveBuff("healthboost"); rangedAttack = false; return; }			
						if(skillname.equals("regen")) { 
							GiveBuff("regen"); 
							rangedAttack = false; 
							skillEffect = true;
							Skill skillInUse = new Skill();
							Damage damageSkill = new Damage();
							skillInUse.SkillName = "regen";
							skillInUse.SkillPosX = player.PosX_A;
							skillInUse.SkillPosY = player.PosY_A;
							damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
							damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							damageSkill.DamageTime = 100;
							damageSkill.DamageType = "heal";
							damageSkill.DamageValue = 0;
							return; 	
						}		
						if(skillname.equals("ironshield")) { 
							GiveBuff("ironshield"); 
							rangedAttack = false; 
							skillEffect = true;
							Skill skillInUse = new Skill();
							Damage damageSkill = new Damage();
							skillInUse.SkillName = "ironshield";
							skillInUse.SkillPosX = player.PosX_A;
							skillInUse.SkillPosY = player.PosY_A;
							damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
							damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							damageSkill.DamageTime = 100;
							damageSkill.DamageType = "heal";
							damageSkill.DamageValue = 0;
							return; 	
						}				
						if(skillname.equals("invisibility")) { 
							GiveBuff("invisibility");
							rangedAttack = false; 
							skillEffect = true;
							Skill skillInUse = new Skill();
							Damage damageSkill = new Damage();
							skillInUse.SkillName = "invisibility";
							skillInUse.SkillPosX = player.PosX_A;
							skillInUse.SkillPosY = player.PosY_A;
							damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
							damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							damageSkill.DamageTime = 100;
							damageSkill.DamageType = "heal";
							damageSkill.DamageValue = 0;
							return; 								
						}
						
						
						if((listMonsters.get(i).MobPosX + 5) > (touchSkillX - 5) && (listMonsters.get(i).MobPosX + 5) < (touchSkillX + 5)
						   && (listMonsters.get(i).MobPosY + 5) > (touchSkillY - 10) && (listMonsters.get(i).MobPosY + 5) < (touchSkillY + 10)) {
							player.playerInBattle_A = "yes";
							listMonsters.get(i).MobTarget = player.Name_A;
							
							if(skillname.equals("rockbound")) {
								int atkweapon = CheckWeapon();
								int totaldmg = player.Atk_A + ((player.Wis_A * 2) + 10);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }						
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "rockbound";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
								return;
							}
							
							if(skillname.equals("fireball")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Wis_A * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								listMonsters.get(i).MobHp = mobHP;				
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "fireball";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
								return;
							}
							
							if(skillname.equals("icecrystal")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Wis_A * 6) + (player.Dex_A * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "icecrystal";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
								return;
							}												
							if(skillname.equals("thundercloud")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Wis_A * 3) + (player.Agi_A * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }						
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "thundercloud";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
								return;
							}
							
							if(skillname.equals("bulletrain")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Dex_A * 2) + (player.Agi_A * 2) + 10);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }					
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "bulletrain";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
								return;
							}						
							if(skillname.equals("holyprism")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Wis_A) + player.Luk_A + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }					
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "holyprism";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
								return;
							}
							if(skillname.equals("mine")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Dex_A * 2) + 10);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControl.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }					
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "mine";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);	
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
								return;
							}							
						}
					}
				}		
			}
		}
		
		public void MobDead(int mobindex) {
			player.Target = "none";
			player.AtkTimer = player.AtkTimerMax;
			player.playerInBattle = "no";
		    player.playerInAttack = "no";
		    player.playerInCast = "no";	
		    autoattack = false;
		    
		    showDropMsg = 100;
		    itemdropname = gameControl.ItemDrop(listMonsters.get(mobindex).MobName);
		    player = gameControl.GiveExp(listMonsters.get(mobindex).MobExp);
		    if(player.Money > 1500) { return; }
		    player.Money = player.Money_A + 2;
		    
		}
		
		
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			if(playerDead) { return false; }
			
			Vector3 coordsTouch = camera.unproject(new Vector3(screenX,screenY,0));
			
			//Main
			//[Main State]//
			if(state.equals("Main")) {
				if(player.playerInCast.equals("no")) { movement = true; } else { movement = false; }
				
				if(selectAreaRanged) {
					touchSkillX = coordsTouch.x;
					touchSkillY = coordsTouch.y;
					selectAreaRanged = false;
					rangedAttack = true;
					CheckAreaRangedSkill();
					showZone = true;
					countZoneSkill = 40;
				}
				
				//Menu
				if(coordsTouch.x > cameraCoordsX - 99 && coordsTouch.x < cameraCoordsX - 61 && coordsTouch.y > cameraCoordsY + 57 && coordsTouch.y < cameraCoordsY + 96) {
					state = "Menu";
					return false;
				}
				
				//Action
				if(coordsTouch.x > cameraCoordsX + 62 && coordsTouch.x < cameraCoordsX + 73 && coordsTouch.y > cameraCoordsY -60 && coordsTouch.y < cameraCoordsY -35)  {
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
				//if(coordsTouch.x > cameraCoordsX + 62 && coordsTouch.x < cameraCoordsX + 73 && coordsTouch.y > cameraCoordsY -60 && coordsTouch.y < cameraCoordsY -35) {
				//	return false;
				//}
				//Target
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY -60 && coordsTouch.y < cameraCoordsY -35) {
					ChangeTarget();
					return false;
				}
				//Skill 1
				if(coordsTouch.x > cameraCoordsX + 47 && coordsTouch.x < cameraCoordsX + 56 && coordsTouch.y > cameraCoordsY - 90 && coordsTouch.y < cameraCoordsY - 66) {
					if(skillUsed > 0) { return false; }
					skillUsed = 200;
					CheckSkill(1);
					return false;
				}
				//Skill 2
				if(coordsTouch.x > cameraCoordsX + 63 && coordsTouch.x < cameraCoordsX + 72 && coordsTouch.y > cameraCoordsY - 90 && coordsTouch.y < cameraCoordsY - 66) {
					if(skillUsed > 0) { return false; }
					skillUsed = 200;
					CheckSkill(2);
					return false;
				}
				//Skill 3
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY - 90 && coordsTouch.y < cameraCoordsY - 66) {			
					return false;
				}
				//Sit 
				if(coordsTouch.x > cameraCoordsX - 45 && coordsTouch.x < cameraCoordsX - 25 && coordsTouch.y > cameraCoordsY + 86 && coordsTouch.y < cameraCoordsY + 97) {
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
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY - 30 && coordsTouch.y < cameraCoordsY - 6) {
					if(!player.hotkey1_A.equals("none")) {
						gameControl.UseItem(hotketcountitem1);
					}
					return false;
				}
				
				//hotkey2
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY + 1 && coordsTouch.y < cameraCoordsY + 24) {
					if(!player.hotkey2_A.equals("none")) {
						gameControl.UseItem(hotketcountitem2);
					}
					return false;
				}
				
				//Descartar
				if(coordsTouch.x > cameraCoordsX - 12 && coordsTouch.x < cameraCoordsX + 17 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 48) {
					menuoption = "descartar";
					return false;
				}
				
				//Chat
				if(coordsTouch.x > cameraCoordsX - 60 && coordsTouch.x < cameraCoordsX - 49 && coordsTouch.y > cameraCoordsY + 77 && coordsTouch.y < cameraCoordsY + 97) {
					Gdx.input.getTextInput(this,"Digite sua mensagem","","");
					return false;
				}
			}
			
			
			return false;
		}
		
		public void ShowMobs() {			
			
			float playerPosX = Float.parseFloat(player.PosX);
			float playerPosY = Float.parseFloat(player.PosY);
			
			if(player.Map.equals("Sewers") || player.Map.equals("Watercave") || player.Map.equals("Mines") || player.Map.equals("Snowpalace") || player.Map.equals("Tower")) {
				for(int i = 0; i < listMonsters.size(); i++) {
					
						//Target do player
						if(player.Target.equals(listMonsters.get(i).MobID)) {
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
						
						if(listMonsters.get(i).MobTarget.equals(player.Name)) {
							if(listMonsters.get(i).MobPosX < playerPosX + 3) { listMonsters.get(i).MobPosX = listMonsters.get(i).MobPosX + 0.07f; }
							if(listMonsters.get(i).MobPosX > playerPosX + 3) { listMonsters.get(i).MobPosX = listMonsters.get(i).MobPosX - 0.07f; }
							if(listMonsters.get(i).MobPosY < playerPosY - 6 ) { listMonsters.get(i).MobPosY = listMonsters.get(i).MobPosY + 0.07f; }
							if(listMonsters.get(i).MobPosY > playerPosY - 6) { listMonsters.get(i).MobPosY = listMonsters.get(i).MobPosY - 0.07f; }
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
						
						if(player.Map.equals("Sewers")) { spr_monster = gameControl.GetMonster(listMonsters.get(i).MobName, listMonsters.get(i).MobFrame, "L");}
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
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean scrolled(float amountX, float amountY) {
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
