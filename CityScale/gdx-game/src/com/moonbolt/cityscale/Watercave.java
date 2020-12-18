package com.moonbolt.cityscale;

import java.io.UnsupportedEncodingException;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Watercave implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	//Objects
	private MainGame game;
	private GameControl gameControl;
	private String[] config;
	private String platform;
	private boolean network = false;
	private String networkState = "on";
	private String mapSwitchConfig = "";
	private String mapSwitch = "";

	//Player
	private Player activePlayer;
	private int numPlayerActive;
	private int framePlayer = 1;
	private String state = "front";
	private String walk = "stop";
	private String shop = "";
	private String[] stats;
	private String[] statsPoint;
	private String breakWalk = "";
	private boolean movement;
	private float playerPosX;
	private float playerPosY;
	private int playerPosiX;
	private int playerPosiY;
	private int numSkillCast = 0;
	private int castTime = 0;
	private String autoAtk = "no";
	private boolean isAreaSkill = false;
	private boolean areaSkillSelected = false;
	private float skillTouchX;
	private float skillTouchY;
	private int deathCount = 300;
	private String mapChange = "";

	//Sprites
	private Sprite spr_Background;
	private Texture tex_Background;
	private Sprite spr_BackgroundOver;
	private Texture tex_BackgroundOver;

	private Sprite spr_playerCharacter;
	private Sprite spr_playerHair;
	private Sprite spr_playerHat;
	private Sprite spr_playerTag;
	private Sprite spr_playerHairTag;
	private Sprite spr_Minibar;
	private Sprite spr_Hotbar;
	private Sprite spr_BackController;
	private Sprite spr_Controller;
	private Sprite spr_autoAtk;
	private Sprite spr_switchTarget;
	private Sprite spr_TargetArrow;
	private Sprite spr_Weapon;
	private Sprite spr_Skill;
	private Sprite spr_Shop;
	private Sprite spr_item;
	private Sprite spr_mob;
	private Sprite spr_npc;
	private Sprite spr_lootItem;
	private Sprite spr_lootBar;
	private Sprite spr_skill;
	private Sprite spr_buff;
	private Sprite spr_areaSelect;
	private Sprite spr_areaSkillTarget;
	private Sprite spr_boardJob;
	private Sprite spr_iconSkillMenu;
	private Sprite spr_death;

	private Sprite spr_Menubar;
	private Sprite spr_MenuStatus;
	private Sprite spr_MenuItens;
	private Sprite spr_MenuSkills;
	private Sprite spr_MenuPet;
	private Sprite spr_MenuSocial;
	private Sprite spr_MenuConfig;

	//Primitives
	private float posTouchX = 0;
	private float posTouchY = 0;
	private boolean changeScreen = false;
	private boolean discart = false;
	private boolean hotkey = false;
	private boolean description = false;
	private boolean typeParty = false;
	private String detailItem = "";
	private String gameState = "Main";
	private String text = "";
	private int count = 0;
	private int menuItemTab = 1;
	private int countDisplay = 0;
	private int countParty = 0;
	private int timeBuyCount = 0;
	private String nameBuy = "";
	private float walkNPC = 0;
	private boolean isDisplay = false;
	private String typeDisplay = "";
	private String msgDisplay = "";
	private String[] logItens;
	private String job = "";
	private ArrayList<Monster> lstMobs;
	private ArrayList<Damage> lstDano;
	private ArrayList<Skill> lstSkill;
	private ArrayList<Sprite> lstNPCs;
	private boolean deathCheck = false;

	//fonts
	private BitmapFont font_master;

	//Online
	private ArrayList<String> lstChats;
	private ArrayList<Player> lstPlayerOnline;
	private Sprite spr_playerCharacterOnline;
	private Sprite spr_playerHairOnline;
	private Sprite spr_TagParty;
	private Sprite spr_TagPartyHair;
	private Sprite spr_TagPartyHat;
	private Sprite spr_playerHatOnline;

	//Camera
	private OrthographicCamera camera;
	private Viewport viewport;
	private float cameraCoordsX = 0;
	private float cameraCoordsY = 0;

	//Teste Dot
	private Sprite spr_testeDot;
	private Texture tex_testeDot;

	//Controller
	private final IntSet downKeys = new IntSet(20);

	public Watercave(MainGame gameAlt, GameControl gameControl,String[] configAlt,String platformAlt, String networkState) {
		this.game = gameAlt;
		this.gameControl = gameControl;
		this.config = configAlt;
		this.platform = platformAlt;
		this.networkState = networkState;

		//test dot
		tex_testeDot = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		spr_testeDot = new Sprite(tex_testeDot);

		//Load Player Data
		numPlayerActive = gameControl.GetPlayerActiveNum();
		activePlayer = gameControl.SetActivePlayerData(numPlayerActive);
		playerPosX = Float.parseFloat(activePlayer.coordX_A);
		playerPosY = Float.parseFloat(activePlayer.coordY_A);

		//Camera and Inputs
		camera = new OrthographicCamera();
		viewport = new StretchViewport(135,135,camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		Gdx.input.setInputProcessor(this);

		//font
		font_master = new BitmapFont(Gdx.files.internal("data/font/impact.fnt"),Gdx.files.internal("data/font/impact.png"), false);
		font_master.setColor(Color.RED);
		font_master.getData().setScale(0.11f,0.23f);
		font_master.setUseIntegerPositions(false);	

		//Initializing Chats & Monsters
		lstChats = new ArrayList<String>();
		//lstMobs = new ArrayList<Monster>();
		//lstMobs = gameControl.LoadMonsters("Sewers");

		//Sprites
		tex_Background = new Texture(Gdx.files.internal("data/maps/watercave.png"));
		spr_Background = new Sprite(tex_Background);
		spr_Background.setSize(100, 100);

		tex_BackgroundOver = new Texture(Gdx.files.internal("data/maps/streets750over.png"));
		spr_BackgroundOver = new Sprite(tex_BackgroundOver);
		spr_BackgroundOver.setSize(100, 100);

		spr_Skill = new Sprite(tex_testeDot);
		spr_Shop = new Sprite(tex_testeDot);

		if(networkState.equals("on")) {
			network = true;
			gameControl.OnlineManager("Sync","");
			typeDisplay = "Config";
			msgDisplay = "Online Ligado";
			isDisplay = true;
			countDisplay = 200;
		}		
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Regen Timer
		gameControl.RegenerateHPMP();
		//Save Data
		gameControl.UpdateDataSave(numPlayerActive);

		//Check Stamina
		gameControl.CheckStamina();

		//Coords Player
		activePlayer = gameControl.GetPlayer();
		playerPosX = Float.parseFloat(activePlayer.coordX_A);
		playerPosY = Float.parseFloat(activePlayer.coordY_A);

		//Camera Ajustments
		cameraCoordsX = playerPosX;
		cameraCoordsY = playerPosY;

		//Follow camera
		if(playerPosX <= -25f) { cameraCoordsX = -25; }
		if(playerPosX >= 175) { cameraCoordsX = 175; }
		if(playerPosY >= 91.5f) { cameraCoordsY = 91.5f; }
		if(playerPosY <= -105) { cameraCoordsY = -105; }

		camera.position.set(cameraCoordsX,cameraCoordsY + 30,0);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);		    
		game.batch.begin();

		//Update Camera for UI elements
		gameControl.AtualizaCamera(cameraCoordsX, cameraCoordsY);

		//Background
		spr_Background.setPosition(-100, -150);
		spr_Background.setSize(350, 350);
		spr_Background.draw(game.batch);

		//Show NPC
		ShowNPCs();

		//Player Character
		spr_playerCharacter = gameControl.MovPlayerCharacter(activePlayer.set_A,activePlayer.sex_A,walk,state, false, breakWalk);
		spr_playerCharacter.setSize(22, 34);
		spr_playerCharacter.setPosition(playerPosX, playerPosY);
		spr_playerCharacter.draw(game.batch);

		spr_playerHair = gameControl.MovPlayerHair(activePlayer.hair_A,activePlayer.sex_A,state, "Main", walk);
		spr_playerHair.draw(game.batch);

		if(!activePlayer.hat_A.equals("none")) {
			spr_playerHat = gameControl.MovPlayerHat(activePlayer.hat_A,activePlayer.sex_A,state, "Main", walk);
			spr_playerHat.draw(game.batch);
		}

		//Show Online Players
		ShowOnlinePlayers();

		//Show Monsters
		ShowMonsters();

		//Background Over
		//spr_BackgroundOver.setPosition(-100, -150);
		//spr_BackgroundOver.setSize(350, 350);
		//spr_BackgroundOver.draw(game.batch);

		//UI Elements
		spr_playerTag = gameControl.LoadInterfaceGamePlay("playerTag","","");
		spr_playerTag.draw(game.batch);

		spr_playerHairTag = gameControl.LoadInterfaceGamePlay("hairTag",activePlayer.hair_A,activePlayer.sex_A);
		spr_playerHairTag.draw(game.batch);

		spr_Minibar = gameControl.LoadInterfaceGamePlay("minibar", "","");
		spr_Minibar.draw(game.batch);

		spr_Hotbar = gameControl.LoadInterfaceGamePlay("hotbar", "","");
		spr_Hotbar.draw(game.batch);

		spr_BackController = gameControl.LoadInterfaceGamePlay("outerpad", "","");
		spr_BackController.draw(game.batch);

		spr_Controller = gameControl.LoadInterfaceGamePlay("innerpad", walk,state);
		spr_Controller.draw(game.batch);


		if(autoAtk.equals("yes")) {
			spr_autoAtk = gameControl.LoadInterfaceGamePlay("autoatkOFF", "","");
			spr_autoAtk.draw(game.batch);		
		}

		if(autoAtk.equals("no")) {
			spr_autoAtk = gameControl.LoadInterfaceGamePlay("autoatkON","","");
			spr_autoAtk.draw(game.batch);
			gameControl.ExitBattle();
		}

		spr_switchTarget = gameControl.LoadInterfaceGamePlay("switchTarget","","");
		spr_switchTarget.draw(game.batch);

		font_master.draw(game.batch, "Atk:" + String.valueOf(gameControl.GetCountDown()), cameraCoordsX + 58, cameraCoordsY + 32);
		font_master.draw(game.batch, "Skl:" + String.valueOf(gameControl.GetCountDownSkill()), cameraCoordsX + 58, cameraCoordsY + 37);


		font_master.setColor(Color.WHITE);
		font_master.getData().setScale(0.09f,0.11f);
		font_master.setUseIntegerPositions(false);	
		font_master.draw(game.batch, activePlayer.name_A, cameraCoordsX - 53f,cameraCoordsY + 91.5f);
		font_master.draw(game.batch, activePlayer.hp_A, cameraCoordsX - 55.8f,cameraCoordsY + 87.8f);
		font_master.draw(game.batch, activePlayer.maxhp_A, cameraCoordsX - 49f,cameraCoordsY + 87.8f);
		font_master.draw(game.batch, activePlayer.mp_A, cameraCoordsX - 55.8f,cameraCoordsY + 81.8f);
		font_master.draw(game.batch, activePlayer.maxmp_A, cameraCoordsX - 49f,cameraCoordsY + 81.8f);
		font_master.draw(game.batch, activePlayer.level_A, cameraCoordsX - 39f,cameraCoordsY + 87.8f);
		font_master.draw(game.batch, activePlayer.exp_A, cameraCoordsX - 41f,cameraCoordsY + 81.8f);
		font_master.draw(game.batch, activePlayer.stamina_A, cameraCoordsX - 41f,cameraCoordsY + 73.2f);

		playerPosiX = Math.round(playerPosX);
		playerPosiY = Math.round(playerPosY);
		font_master.draw(game.batch, "X:" + Math.round(playerPosX), cameraCoordsX - 34f, cameraCoordsY + 75f);
		font_master.draw(game.batch, "Y:" + Math.round(playerPosY), cameraCoordsX - 34f, cameraCoordsY + 70f);


		if(!lstChats.isEmpty()) {
			font_master.draw(game.batch, "Chats:", cameraCoordsX - 37f, cameraCoordsY - 12.7f);
			for(count = 0; count < lstChats.size(); count++) {
				if(count == 0) {
					if(lstChats.get(count) == null) {
						return;
					}
					font_master.draw(game.batch, lstChats.get(count), cameraCoordsX - 37f, cameraCoordsY - 17.7f);
				}
				if(count == 1) {
					if(lstChats.get(count) == null) {
						return;
					}
					font_master.draw(game.batch, lstChats.get(count), cameraCoordsX - 37f, cameraCoordsY - 22.7f);
				}
				if(count == 2) {
					if(lstChats.get(count) == null) {
						return;
					}
					font_master.draw(game.batch, lstChats.get(count), cameraCoordsX - 37f, cameraCoordsY - 27.7f);
				}	
			}
		}

		//Hotkey Itens
		spr_item = gameControl.ShowItemBar(1, cameraCoordsX, cameraCoordsY);
		if(spr_item != null) { spr_item.draw(game.batch); }

		spr_item = gameControl.ShowItemBar(2, cameraCoordsX, cameraCoordsY);
		if(spr_item != null) { spr_item.draw(game.batch); }

		//hotbarskills
		spr_Skill = gameControl.SkillHotbar("All","");
		spr_Skill.draw(game.batch);

		job = activePlayer.job_A;

		if(activePlayer.job_A.equals("Novice")) {
			spr_Skill = gameControl.SkillHotbar("Novice","tripleattack"); spr_Skill.draw(game.batch);		
		}
		if(activePlayer.job_A.equals("Swordman")) {
			spr_Skill = gameControl.SkillHotbar("Swordman","flysword"); spr_Skill.draw(game.batch);
			spr_Skill = gameControl.SkillHotbar("Swordman","ravenblade"); spr_Skill.draw(game.batch);			
			spr_Skill = gameControl.SkillHotbar("Swordman","healthboost"); spr_Skill.draw(game.batch);
			spr_Skill = gameControl.SkillHotbar("Swordman","ironshield"); spr_Skill.draw(game.batch);
			spr_Skill = gameControl.SkillHotbar("Swordman","protect"); spr_Skill.draw(game.batch);			
		}
		if(activePlayer.job_A.equals("Mage")) {
			spr_Skill = gameControl.SkillHotbar("Mage","fireball"); spr_Skill.draw(game.batch);
			spr_Skill = gameControl.SkillHotbar("Mage","icecrystal"); spr_Skill.draw(game.batch);			
			spr_Skill = gameControl.SkillHotbar("Mage","thundercloud"); spr_Skill.draw(game.batch);
			spr_Skill = gameControl.SkillHotbar("Mage","rockbound"); spr_Skill.draw(game.batch);
			spr_Skill = gameControl.SkillHotbar("Mage","soulcrash"); spr_Skill.draw(game.batch);			
		}
		if(activePlayer.job_A.equals("Thief")) {
			spr_Skill = gameControl.SkillHotbar("Thief","doublehit"); spr_Skill.draw(game.batch);
			spr_Skill = gameControl.SkillHotbar("Thief","poisonhit"); spr_Skill.draw(game.batch);			
			spr_Skill = gameControl.SkillHotbar("Thief","steal"); spr_Skill.draw(game.batch);
			spr_Skill = gameControl.SkillHotbar("Thief","dashkick"); spr_Skill.draw(game.batch);
			spr_Skill = gameControl.SkillHotbar("Thief","invisibility"); spr_Skill.draw(game.batch);			
		}
		if(activePlayer.job_A.equals("Medic")) {
			spr_Skill = gameControl.SkillHotbar("Medic","heal"); spr_Skill.draw(game.batch);
			spr_Skill = gameControl.SkillHotbar("Medic","atkboost"); spr_Skill.draw(game.batch);			
			spr_Skill = gameControl.SkillHotbar("Medic","defboost"); spr_Skill.draw(game.batch);
			spr_Skill = gameControl.SkillHotbar("Medic","holyprism"); spr_Skill.draw(game.batch);
			spr_Skill = gameControl.SkillHotbar("Medic","regen"); spr_Skill.draw(game.batch);			
		}
		if(activePlayer.job_A.equals("Beater")) {
			spr_Skill = gameControl.SkillHotbar("Beater","hammercrash"); spr_Skill.draw(game.batch);
			spr_Skill = gameControl.SkillHotbar("Beater","berserk"); spr_Skill.draw(game.batch);			
			spr_Skill = gameControl.SkillHotbar("Beater","overpower"); spr_Skill.draw(game.batch);
			spr_Skill = gameControl.SkillHotbar("Beater","ragebound"); spr_Skill.draw(game.batch);
			spr_Skill = gameControl.SkillHotbar("Beater","impound"); spr_Skill.draw(game.batch);			
		}
		if(activePlayer.job_A.equals("Gunner")) {
			spr_Skill = gameControl.SkillHotbar("Gunner","fastshot"); spr_Skill.draw(game.batch);
			spr_Skill = gameControl.SkillHotbar("Gunner","bulletrain"); spr_Skill.draw(game.batch);			
			spr_Skill = gameControl.SkillHotbar("Gunner","precision"); spr_Skill.draw(game.batch);
			spr_Skill = gameControl.SkillHotbar("Gunner","lockshot"); spr_Skill.draw(game.batch);
			spr_Skill = gameControl.SkillHotbar("Gunner","mine"); spr_Skill.draw(game.batch);			
		}

		//Calculate MetaInfo From Online
		gameControl.MetaInfoOnline();

		if(gameState.equals("Menu")) {
			spr_Menubar = gameControl.LoadInterfaceGamePlay("barMenu", "", "");
			spr_Menubar.draw(game.batch);
		}

		if(gameState.equals("Menu-Status")) {
			spr_MenuStatus = gameControl.LoadInterfaceGamePlay("menuStatus", "", "");
			spr_MenuStatus.draw(game.batch);

			font_master.draw(game.batch, activePlayer.job_A, cameraCoordsX - 37,cameraCoordsY + 62);
			font_master.draw(game.batch, activePlayer.level_A, cameraCoordsX - 39,cameraCoordsY + 57);
			font_master.draw(game.batch, gameControl.GetExpNeeded(), cameraCoordsX - 34,cameraCoordsY + 52);

			//Remain points
			font_master.draw(game.batch, activePlayer.statusPoint_A, cameraCoordsX - 18,cameraCoordsY - 5);

			//Str
			stats = activePlayer.stats_A.split("#");
			statsPoint = stats[0].split(":");
			font_master.draw(game.batch, statsPoint[1], cameraCoordsX - 10,cameraCoordsY + 54);

			//Agi
			stats = activePlayer.stats_A.split("#");
			statsPoint = stats[1].split(":");
			font_master.draw(game.batch, statsPoint[1], cameraCoordsX - 10,cameraCoordsY + 45);

			//Wis
			stats = activePlayer.stats_A.split("#");
			statsPoint = stats[2].split(":");
			font_master.draw(game.batch, statsPoint[1], cameraCoordsX - 10,cameraCoordsY + 36);

			//Vit
			stats = activePlayer.stats_A.split("#");
			statsPoint = stats[3].split(":");
			font_master.draw(game.batch, statsPoint[1], cameraCoordsX - 10,cameraCoordsY + 26);

			//Des
			stats = activePlayer.stats_A.split("#");
			statsPoint = stats[4].split(":");
			font_master.draw(game.batch, statsPoint[1], cameraCoordsX - 10,cameraCoordsY + 16);

			//Sor
			stats = activePlayer.stats_A.split("#");
			statsPoint = stats[5].split(":");
			font_master.draw(game.batch, statsPoint[1], cameraCoordsX - 10,cameraCoordsY + 6);

			//Res
			stats = activePlayer.stats_A.split("#");
			statsPoint = stats[6].split(":");
			font_master.draw(game.batch, statsPoint[1], cameraCoordsX - 10,cameraCoordsY - 4);

			spr_playerCharacter = gameControl.MovPlayerCharacter(activePlayer.set_A,activePlayer.sex_A,"stop","front", false, breakWalk);
			spr_playerCharacter.setSize(40, 60);
			spr_playerCharacter.setPosition(cameraCoordsX - 51, cameraCoordsY - 10);
			spr_playerCharacter.draw(game.batch);

			spr_playerHair = gameControl.MovPlayerHair(activePlayer.hair_A,activePlayer.sex_A,"stop", "Menu-Status", "stop");
			spr_playerHair.setSize(12,19);
			spr_playerHair.setPosition(cameraCoordsX - 37.8f, cameraCoordsY + 30.2f);
			spr_playerHair.draw(game.batch);
		}

		if(gameState.equals("Menu-Itens")) {
			spr_MenuItens = gameControl.LoadInterfaceGamePlay("menuItens", "", "");
			spr_MenuItens.draw(game.batch);

			spr_playerCharacter = gameControl.MovPlayerCharacter(activePlayer.set_A,activePlayer.sex_A,"stop","front", false, breakWalk);
			spr_playerCharacter.setSize(40, 60);
			spr_playerCharacter.setPosition(cameraCoordsX + 5, cameraCoordsY);
			spr_playerCharacter.draw(game.batch);

			spr_playerHair = gameControl.MovPlayerHair(activePlayer.hair_A,activePlayer.sex_A,"stop", "Menu-Status","stop");
			spr_playerHair.setSize(12,19);
			spr_playerHair.setPosition(cameraCoordsX + 18.2f, cameraCoordsY + 40.3f);
			spr_playerHair.draw(game.batch);

			font_master.draw(game.batch, activePlayer.money_A, cameraCoordsX - 15,cameraCoordsY + 9);

			ShowItensBag();
		}

		if(gameState.equals("Menu-Skills")) {
			spr_MenuSkills = gameControl.LoadInterfaceGamePlay("menuSkills", "", "");
			spr_MenuSkills.draw(game.batch);

			font_master.draw(game.batch, activePlayer.job_A, cameraCoordsX - 38,cameraCoordsY + 63);

			if(activePlayer.job_A.equals("Novice")) {
				spr_iconSkillMenu = gameControl.SkillHotbar("Novice","tripleattack");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY + 34);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Ataque Triplo -", cameraCoordsX - 38,cameraCoordsY + 48);
				font_master.draw(game.batch, "Desfere um ataque que causa", cameraCoordsX - 38,cameraCoordsY + 42);
				font_master.draw(game.batch, "3 vez o dano normal", cameraCoordsX - 38,cameraCoordsY + 38);
			}
			if(activePlayer.job_A.equals("Swordman")) {
				spr_iconSkillMenu = gameControl.SkillHotbar("Swordman","flysword");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY + 34);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Espadas Voadoras -", cameraCoordsX - 38,cameraCoordsY + 48);
				font_master.draw(game.batch, "Desfere um ataque forte ", cameraCoordsX - 38,cameraCoordsY + 42);
				font_master.draw(game.batch, "baseado em Forca", cameraCoordsX - 38,cameraCoordsY + 38);

				spr_iconSkillMenu = gameControl.SkillHotbar("Swordman","ravenblade");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY + 15);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Laminas Aladas -", cameraCoordsX - 38,cameraCoordsY + 28);
				font_master.draw(game.batch, "Desfere um ataque alado ", cameraCoordsX - 38,cameraCoordsY + 23);
				font_master.draw(game.batch, "que corta profundamente", cameraCoordsX - 38,cameraCoordsY + 19);

				spr_iconSkillMenu = gameControl.SkillHotbar("Swordman","healthboost");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY - 3);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Aumento de Vida -", cameraCoordsX - 38,cameraCoordsY + 9);
				font_master.draw(game.batch, "Aumenta seu HP Total", cameraCoordsX - 38,cameraCoordsY + 4);
				font_master.draw(game.batch, "Temporariamente", cameraCoordsX - 38,cameraCoordsY);

				spr_iconSkillMenu = gameControl.SkillHotbar("Swordman","ironshield");
				spr_iconSkillMenu.setPosition(cameraCoordsX + 2, cameraCoordsY + 34);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Escudo de Ferro -", cameraCoordsX + 11,cameraCoordsY + 48);
				font_master.draw(game.batch, "Diminui o dano dos inimigos", cameraCoordsX + 11,cameraCoordsY + 42);
				font_master.draw(game.batch, "infligido no jogador", cameraCoordsX + 11,cameraCoordsY + 38);

				spr_iconSkillMenu = gameControl.SkillHotbar("Swordman","protect");
				spr_iconSkillMenu.setPosition(cameraCoordsX + 2, cameraCoordsY + 15);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Protecao -", cameraCoordsX + 11,cameraCoordsY + 28);
				font_master.draw(game.batch, "Ignora o dano dos inimigos", cameraCoordsX + 11,cameraCoordsY + 23);
				font_master.draw(game.batch, "completamente", cameraCoordsX + 11,cameraCoordsY + 19);
			}

			if(activePlayer.job_A.equals("Mage")) {
				spr_iconSkillMenu = gameControl.SkillHotbar("Mage","fireball");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY + 34);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Bola de Fogo -", cameraCoordsX - 38,cameraCoordsY + 48);
				font_master.draw(game.batch, "Desfere uma bola de fogo", cameraCoordsX - 38,cameraCoordsY + 42);
				font_master.draw(game.batch, "magica contra o oponente", cameraCoordsX - 38,cameraCoordsY + 38);

				spr_iconSkillMenu = gameControl.SkillHotbar("Mage","icecrystal");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY + 15);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Cristal de Gelo -", cameraCoordsX - 38,cameraCoordsY + 28);
				font_master.draw(game.batch, "Cria um cristal gelado", cameraCoordsX - 38,cameraCoordsY + 23);
				font_master.draw(game.batch, "que impala os inimigos", cameraCoordsX - 38,cameraCoordsY + 19);

				spr_iconSkillMenu = gameControl.SkillHotbar("Mage","thundercloud");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY - 3);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Tempestade -", cameraCoordsX - 38,cameraCoordsY + 9);
				font_master.draw(game.batch, "Conjunto de Raios perfurantes", cameraCoordsX - 38,cameraCoordsY + 4);
				font_master.draw(game.batch, "baseado em Agi e Des", cameraCoordsX - 38,cameraCoordsY);

				spr_iconSkillMenu = gameControl.SkillHotbar("Mage","rockbound");
				spr_iconSkillMenu.setPosition(cameraCoordsX + 2, cameraCoordsY + 34);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Terremoto -", cameraCoordsX + 11,cameraCoordsY + 48);
				font_master.draw(game.batch, "Estremece a terra causando", cameraCoordsX + 11,cameraCoordsY + 42);
				font_master.draw(game.batch, "dano ao inimigo", cameraCoordsX + 11,cameraCoordsY + 38);

				spr_iconSkillMenu = gameControl.SkillHotbar("Mage","soulcrash");
				spr_iconSkillMenu.setPosition(cameraCoordsX + 2, cameraCoordsY + 15);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Impacto da Alma -", cameraCoordsX + 11,cameraCoordsY + 28);
				font_master.draw(game.batch, "Acerta o inimigo com poderes", cameraCoordsX + 11,cameraCoordsY + 23);
				font_master.draw(game.batch, "vindos de outro mundo", cameraCoordsX + 11,cameraCoordsY + 19);
			}

			if(activePlayer.job_A.equals("Thief")) {
				spr_iconSkillMenu = gameControl.SkillHotbar("Thief","doublehit");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY + 34);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Ataque Duplo -", cameraCoordsX - 38,cameraCoordsY + 48);
				font_master.draw(game.batch, "Acerta o Inimigo causando", cameraCoordsX - 38,cameraCoordsY + 42);
				font_master.draw(game.batch, "um dano perfurante", cameraCoordsX - 38,cameraCoordsY + 38);

				spr_iconSkillMenu = gameControl.SkillHotbar("Thief","poisonhit");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY + 15);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Golpe Envenenador -", cameraCoordsX - 38,cameraCoordsY + 28);
				font_master.draw(game.batch, "Atinge o inimigo com uma arma", cameraCoordsX - 38,cameraCoordsY + 23);
				font_master.draw(game.batch, "envenenada, chance de veneno", cameraCoordsX - 38,cameraCoordsY + 19);

				spr_iconSkillMenu = gameControl.SkillHotbar("Thief","steal");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY - 3);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Roubar -", cameraCoordsX - 38,cameraCoordsY + 9);
				font_master.draw(game.batch, "Tenta furtar o adversario", cameraCoordsX - 38,cameraCoordsY + 4);
				font_master.draw(game.batch, "a chance e aleatoria fixa", cameraCoordsX - 38,cameraCoordsY);

				spr_iconSkillMenu = gameControl.SkillHotbar("Thief","dashkick");
				spr_iconSkillMenu.setPosition(cameraCoordsX + 2, cameraCoordsY + 34);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Chute Rapido -", cameraCoordsX + 11,cameraCoordsY + 48);
				font_master.draw(game.batch, "Atinge em tamanha velocidade", cameraCoordsX + 11,cameraCoordsY + 42);
				font_master.draw(game.batch, "baseado em agi e sor", cameraCoordsX + 11,cameraCoordsY + 38);

				spr_iconSkillMenu = gameControl.SkillHotbar("Thief","invisibility");
				spr_iconSkillMenu.setPosition(cameraCoordsX + 2, cameraCoordsY + 15);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Invisibilidade -", cameraCoordsX + 11,cameraCoordsY + 28);
				font_master.draw(game.batch, "Deixa o jogador invisivel sem", cameraCoordsX + 11,cameraCoordsY + 23);
				font_master.draw(game.batch, "tomar danos", cameraCoordsX + 11,cameraCoordsY + 19);
			}
			if(activePlayer.job_A.equals("Medic")) {
				spr_iconSkillMenu = gameControl.SkillHotbar("Medic","heal");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY + 34);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Curar -", cameraCoordsX - 38,cameraCoordsY + 48);
				font_master.draw(game.batch, "Recupera o HP do jogador", cameraCoordsX - 38,cameraCoordsY + 42);
				font_master.draw(game.batch, "e do grupo de aliados", cameraCoordsX - 38,cameraCoordsY + 38);

				spr_iconSkillMenu = gameControl.SkillHotbar("Medic","atkboost");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY + 15);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Aumentar Ataque -", cameraCoordsX - 38,cameraCoordsY + 28);
				font_master.draw(game.batch, "Aumenta o atk do jogador", cameraCoordsX - 38,cameraCoordsY + 23);
				font_master.draw(game.batch, "e do grupo de aliados", cameraCoordsX - 38,cameraCoordsY + 19);

				spr_iconSkillMenu = gameControl.SkillHotbar("Medic","defboost");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY - 3);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Aumentar Defesa -", cameraCoordsX - 38,cameraCoordsY + 9);
				font_master.draw(game.batch, "Aumenta a def do jogador", cameraCoordsX - 38,cameraCoordsY + 4);
				font_master.draw(game.batch, "e do grupo de aliados", cameraCoordsX - 38,cameraCoordsY);

				spr_iconSkillMenu = gameControl.SkillHotbar("Medic","holyprism");
				spr_iconSkillMenu.setPosition(cameraCoordsX + 2, cameraCoordsY + 34);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Prisma Sagrado -", cameraCoordsX + 11,cameraCoordsY + 48);
				font_master.draw(game.batch, "Atinge o inimigo com o poder", cameraCoordsX + 11,cameraCoordsY + 42);
				font_master.draw(game.batch, "da luz, baseado em vit", cameraCoordsX + 11,cameraCoordsY + 38);

				spr_iconSkillMenu = gameControl.SkillHotbar("Medic","regen");
				spr_iconSkillMenu.setPosition(cameraCoordsX + 2, cameraCoordsY + 15);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Regeneracao -", cameraCoordsX + 11,cameraCoordsY + 28);
				font_master.draw(game.batch, "Recupera gradualmente", cameraCoordsX + 11,cameraCoordsY + 23);
				font_master.draw(game.batch, "o HP seu e dos aliados", cameraCoordsX + 11,cameraCoordsY + 19);
			}
			if(activePlayer.job_A.equals("Gunner")) {
				spr_iconSkillMenu = gameControl.SkillHotbar("Gunner","fastshot");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY + 34);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Tiro Rapido -", cameraCoordsX - 38,cameraCoordsY + 48);
				font_master.draw(game.batch, "Acerta o Inimigo de forma veloz", cameraCoordsX - 38,cameraCoordsY + 42);
				font_master.draw(game.batch, "baseado em luk", cameraCoordsX - 38,cameraCoordsY + 38);

				spr_iconSkillMenu = gameControl.SkillHotbar("Gunner","bulletrain");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY + 15);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Chuva de Balas -", cameraCoordsX - 38,cameraCoordsY + 28);
				font_master.draw(game.batch, "Acerta o inimigo com tiros", cameraCoordsX - 38,cameraCoordsY + 23);
				font_master.draw(game.batch, "que caem do ceu", cameraCoordsX - 38,cameraCoordsY + 19);

				spr_iconSkillMenu = gameControl.SkillHotbar("Gunner","precision");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY - 3);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Precisao -", cameraCoordsX - 38,cameraCoordsY + 9);
				font_master.draw(game.batch, "Garante o acerto no alvo", cameraCoordsX - 38,cameraCoordsY + 4);
				font_master.draw(game.batch, "ao jogador e grupo de aliados", cameraCoordsX - 38,cameraCoordsY);

				spr_iconSkillMenu = gameControl.SkillHotbar("Gunner","lockshot");
				spr_iconSkillMenu.setPosition(cameraCoordsX + 2, cameraCoordsY + 34);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Tiro Certeiro -", cameraCoordsX + 11,cameraCoordsY + 48);
				font_master.draw(game.batch, "Atinge pontos vitais do inimigo", cameraCoordsX + 11,cameraCoordsY + 42);
				font_master.draw(game.batch, "baseado em varios atributos", cameraCoordsX + 11,cameraCoordsY + 38);

				spr_iconSkillMenu = gameControl.SkillHotbar("Gunner","mine");
				spr_iconSkillMenu.setPosition(cameraCoordsX + 2, cameraCoordsY + 15);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Atirar Mina -", cameraCoordsX + 11,cameraCoordsY + 28);
				font_master.draw(game.batch, "Joga uma mina no adversario", cameraCoordsX + 11,cameraCoordsY + 23);
				font_master.draw(game.batch, "base em MP,efeito atordoador", cameraCoordsX + 11,cameraCoordsY + 19);
			}
			if(activePlayer.job_A.equals("Beater")) {
				spr_iconSkillMenu = gameControl.SkillHotbar("Beater","hammercrash");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY + 34);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Batida do Machado -", cameraCoordsX - 38,cameraCoordsY + 48);
				font_master.draw(game.batch, "Acerta o Inimigo com forca", cameraCoordsX - 38,cameraCoordsY + 42);
				font_master.draw(game.batch, "baseado em forca", cameraCoordsX - 38,cameraCoordsY + 38);

				spr_iconSkillMenu = gameControl.SkillHotbar("Beater","berserk");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY + 15);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Furia -", cameraCoordsX - 38,cameraCoordsY + 28);
				font_master.draw(game.batch, "Atinge o inimigo com toda ira", cameraCoordsX - 38,cameraCoordsY + 23);
				font_master.draw(game.batch, "do usuario", cameraCoordsX - 38,cameraCoordsY + 19);

				spr_iconSkillMenu = gameControl.SkillHotbar("Beater","overpower");
				spr_iconSkillMenu.setPosition(cameraCoordsX - 47, cameraCoordsY - 3);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Sobrepoder -", cameraCoordsX - 38,cameraCoordsY + 9);
				font_master.draw(game.batch, "Aumenta o Atk / HP do usuario", cameraCoordsX - 38,cameraCoordsY + 4);
				font_master.draw(game.batch, "por tempo determinado", cameraCoordsX - 38,cameraCoordsY);

				spr_iconSkillMenu = gameControl.SkillHotbar("Beater","ragebound");
				spr_iconSkillMenu.setPosition(cameraCoordsX + 2, cameraCoordsY + 34);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Batida da Furia -", cameraCoordsX + 11,cameraCoordsY + 48);
				font_master.draw(game.batch, "Acerta o inimigo canalizando", cameraCoordsX + 11,cameraCoordsY + 42);
				font_master.draw(game.batch, "a forca da mente", cameraCoordsX + 11,cameraCoordsY + 38);

				spr_iconSkillMenu = gameControl.SkillHotbar("Beater","impound");
				spr_iconSkillMenu.setPosition(cameraCoordsX + 2, cameraCoordsY + 15);
				spr_iconSkillMenu.draw(game.batch);
				font_master.draw(game.batch, "Imponderamento -", cameraCoordsX + 11,cameraCoordsY + 28);
				font_master.draw(game.batch, "Uma energia que protege", cameraCoordsX + 11,cameraCoordsY + 23);
				font_master.draw(game.batch, "os danos, jogador e aliados", cameraCoordsX + 11,cameraCoordsY + 19);
			}
		}

		if(gameState.equals("Menu-Social")) {
			spr_MenuSocial = gameControl.LoadInterfaceGamePlay("menuSocial", "", "");
			spr_MenuSocial.draw(game.batch);

			font_master.draw(game.batch, "Em Grupo:" + activePlayer.party_A, cameraCoordsX + 5,cameraCoordsY + 62);
		}

		if(gameState.equals("Menu-Pet")) {
			spr_MenuPet = gameControl.LoadInterfaceGamePlay("menuPet", "", "");
			spr_MenuPet.draw(game.batch);
		}

		if(gameState.equals("Menu-Config")) {
			spr_MenuConfig = gameControl.LoadInterfaceGamePlay("menuConfig", "", "");
			spr_MenuConfig.draw(game.batch);

			font_master.draw(game.batch, "Seu ID:" + activePlayer.accountID, cameraCoordsX + 30, cameraCoordsY - 5);
		}

		if(gameState.equals("Shop")) {
			spr_Shop = gameControl.LoadInterfaceGamePlay(shop, "", "");
			spr_Shop.draw(game.batch);
		}

		//Display Message
		if(isDisplay) {
			countDisplay--;
			if(countDisplay <= 0) {
				countDisplay = 0;
				isDisplay = true;
				typeDisplay = "";
			}

			if(typeDisplay.equals("Config")) {
				font_master.draw(game.batch, msgDisplay, cameraCoordsX - 7,cameraCoordsY);
			}
		}

		//Show Damage
		ShowDamage();
		ShowSkillUsed();
		ShowBuffs();

		//Verify Combate
		gameControl.CheckSkillCooldown();
		if(autoAtk.equals("yes")) {
			gameControl.AutoAttack();
		}

		//Show Weapon
		if(activePlayer.job_A.equals("Novice")) { spr_Weapon = gameControl.ShowWeaponNovice(playerPosX, playerPosY, walk); }
		if(activePlayer.job_A.equals("Swordman")) { spr_Weapon = gameControl.ShowWeaponSwordman(playerPosX, playerPosY, walk); }
		if(activePlayer.job_A.equals("Gunner")) { spr_Weapon = gameControl.ShowWeaponGunner(playerPosX, playerPosY, walk); }
		if(activePlayer.job_A.equals("Thief")) { spr_Weapon = gameControl.ShowWeaponThief(playerPosX, playerPosY, walk); }
		if(activePlayer.job_A.equals("Beater")) { spr_Weapon = gameControl.ShowWeaponBeater(playerPosX, playerPosY, walk); }
		if(activePlayer.job_A.equals("Mage") || activePlayer.job_A.equals("Medic")) { spr_Weapon = gameControl.ShowWeaponCaster(playerPosX, playerPosY, walk); }
		if(spr_Weapon != null) { spr_Weapon.draw(game.batch); }		
		gameControl.CheckMonsterAttack();		
		gameControl.RespawnMob();

		//Show Loot
		spr_lootItem = gameControl.ShowLootItem(cameraCoordsX, cameraCoordsY);
		if(spr_lootItem != null) {	
			spr_lootBar = gameControl.LoadInterfaceGamePlay("lootbar", "", "");			
			spr_lootBar.draw(game.batch);
			font_master.draw(game.batch, "Item Obtido:  " + gameControl.GetLootName(), cameraCoordsX - 23,cameraCoordsY + 68);
			spr_lootItem.draw(game.batch);
		}

		//Ranged Skill
		if(isAreaSkill) {
			spr_areaSelect = gameControl.LoadInterfaceGamePlay("areaSelect", "", "");
			spr_areaSelect.draw(game.batch);
		}

		//CastTime Skill
		if(areaSkillSelected) {
			if(castTime > 0) {
				font_master.draw(game.batch, "Cast:" + castTime, cameraCoordsX + 5, cameraCoordsY + 38.6f);
				castTime--;
				isAreaSkill = false;
				spr_areaSkillTarget = gameControl.LoadInterfaceGamePlay("skillareatarget", String.valueOf(skillTouchX), String.valueOf(skillTouchY));
				spr_areaSkillTarget.draw(game.batch);
			}
			if(castTime <= 0 && numSkillCast > 0) {
				if(activePlayer.job_A.equals("Mage")) { gameControl.SkillAtkMage(numSkillCast, skillTouchX, skillTouchY); }
				if(activePlayer.job_A.equals("Medic")) { gameControl.SkillAtkMedic(numSkillCast, skillTouchX, skillTouchY); }
				numSkillCast = 0;
				areaSkillSelected = false;
				gameState = "Main";
			}
		}

		if(gameState.equals("jobpost")) {
			spr_boardJob = gameControl.LoadInterfaceGamePlay("boardJob", "", "");
			spr_boardJob.draw(game.batch);
		}

		if(gameState.equals("Shop")) {
			font_master.draw(game.batch, activePlayer.money_A, cameraCoordsX + 12,cameraCoordsY + 3);
			font_master.draw(game.batch, nameBuy, cameraCoordsX + 27,cameraCoordsY + 3);
			if(timeBuyCount > 0) {
				timeBuyCount--;

				if(timeBuyCount <= 0) {
					nameBuy = "";
					timeBuyCount = 0;
				}
			}
		}

		CheckColide();

		//Change Screen
		if(changeScreen){	
			gameControl.ScreenChange(mapSwitchConfig);
			gameControl.UpdateDataSave(numPlayerActive);
			game.AtualizaElementos(game,gameControl, config, platform, networkState);
			game.Switch(mapSwitch);			
		}

		deathCheck = gameControl.VerifyDeath();
		if(deathCheck) {
			deathCount--;
			spr_death = gameControl.LoadInterfaceGamePlay("deathbar", "","");
			spr_death.draw(game.batch);

			if(deathCount <= 0) {
				activePlayer.hp_A = "1";
				activePlayer.mp_A = "1";
				activePlayer.inBattle_A = "no";
				mapChange = "MetroStation";
				changeScreen = true;
			}
		}



		game.batch.end();	
	}


	private void CheckColide() {

		if(playerPosX > -80 && playerPosX < -63 && playerPosY > 101 && playerPosY < 120) {
			changeScreen = true;
			mapSwitchConfig = "Streets750CaveOut";
			mapSwitch = "Streets750";
			return;
		}
		
		//Wall up
		if(playerPosY > 159) {
			breakWalk = "back";
			return;
		}
		
		breakWalk = "";
	}

	private void ActionVerify() {
		//Shop 305
		if(playerPosX > 119 && playerPosX < 136 && playerPosY > 51 && playerPosY < 70) {
			gameState = "Shop";
			shop = "305";
		}
		//Shop Classico
		if(playerPosX > -43 && playerPosX < -25 && playerPosY > 55 && playerPosY < 70) {
			gameState = "Shop";
			shop = "Classical";
		}

		//Refri Shop
		if(playerPosX > -9.7f && playerPosX < 5.4f && playerPosY > 54f && playerPosY < 70.2f) {
			gameState = "Shop";
			shop = "RefriShop";
		}

		//JobMaster
		if(playerPosX > 8f && playerPosX < 23 && playerPosY > -69f && playerPosY < -47) {
			int playerlevel = Integer.parseInt(activePlayer.level_A);
			if(!activePlayer.job_A.equals("Novice")) { return; } 
			if(playerlevel < 10) { return; }
			if(playerlevel > 10) { return; }
			gameState = "jobpost";
		}
	}

	private void ShowItensBag() {		
		//Common Itens
		for(count = 0; count <= 47; count++) {
			spr_item = gameControl.ShowItem(count,menuItemTab,cameraCoordsX, cameraCoordsY);
			if(spr_item != null) {
				spr_item.draw(game.batch);
				font_master.draw(game.batch, gameControl.ShowQuantityItem(count), spr_item.getX() + 7,spr_item.getY() + 3);
			}
		}	

		//Equipament Itens
		spr_item = gameControl.ShowEquippedItens(1,cameraCoordsX, cameraCoordsY); // Weapon
		spr_item.draw(game.batch);
		spr_item = gameControl.ShowEquippedItens(2,cameraCoordsX, cameraCoordsY); // Set
		spr_item.draw(game.batch);
		spr_item = gameControl.ShowEquippedItens(3,cameraCoordsX, cameraCoordsY); // Hat
		if(spr_item != null) { spr_item.draw(game.batch); }

		//HotKey Itens
		spr_item = gameControl.ShowItemHotKey(1,cameraCoordsX,cameraCoordsY);
		if(spr_item != null) { spr_item.draw(game.batch); }

		spr_item = gameControl.ShowItemHotKey(2,cameraCoordsX,cameraCoordsY);
		if(spr_item != null) { spr_item.draw(game.batch); }

		//Case Selected
		if(discart) {
			spr_item = gameControl.SelectedSprites("Discart", cameraCoordsX, cameraCoordsY);
			spr_item.draw(game.batch);
		}

		if(description) {
			spr_item = gameControl.SelectedSprites("Description", cameraCoordsX, cameraCoordsY);
			spr_item.draw(game.batch);
		}

		if(hotkey) {
			spr_item = gameControl.SelectedSprites("Hotkey", cameraCoordsX, cameraCoordsY);
			spr_item.draw(game.batch);
		}

		if(menuItemTab == 1) {
			spr_item = gameControl.SelectedSprites("Menu1", cameraCoordsX, cameraCoordsY);
			spr_item.draw(game.batch);
		}

		if(menuItemTab == 2) {
			spr_item = gameControl.SelectedSprites("Menu2", cameraCoordsX, cameraCoordsY);
			spr_item.draw(game.batch);
		}

		if(menuItemTab == 3) {
			spr_item = gameControl.SelectedSprites("Menu3", cameraCoordsX, cameraCoordsY);
			spr_item.draw(game.batch);
		}

		if(menuItemTab == 4) {
			spr_item = gameControl.SelectedSprites("Menu4", cameraCoordsX, cameraCoordsY);
			spr_item.draw(game.batch);
		}

	}

	private void ShowOnlinePlayers() {

		if(network) {			
			lstChats = gameControl.GetOnlineChats();				
			lstPlayerOnline = gameControl.GetOnlinePlayers();					

			for(int i = 0; i < lstPlayerOnline.size(); i++) {	
				if(lstPlayerOnline.get(i).accountID == null) { return; }
				//Exibe jogadores do mesmo mapa
				if(!lstPlayerOnline.get(i).accountID.equals(activePlayer.accountID) && lstPlayerOnline.get(i).map_A.equals(activePlayer.map_A)) {
					spr_playerCharacterOnline = gameControl.MovPlayerOnline(lstPlayerOnline.get(i));
					spr_playerCharacterOnline.setSize(22, 34);
					spr_playerCharacterOnline.draw(game.batch);

					spr_playerHairOnline = gameControl.MovPlayerOnlineHair(lstPlayerOnline.get(i));
					spr_playerHairOnline.draw(game.batch);

					if(!lstPlayerOnline.get(i).hat_A.equals("none")) {
						spr_playerHatOnline = gameControl.MovPlayerOnlineHat(lstPlayerOnline.get(i));
						spr_playerHatOnline.draw(game.batch);
					}

					font_master.draw(game.batch, lstPlayerOnline.get(i).name_A, spr_playerCharacterOnline.getX() + 7.5f,spr_playerCharacterOnline.getY() + 5);				
				}

				//Verifica Party
				if(lstPlayerOnline.get(i).party_A.equals(activePlayer.party_A) && !activePlayer.party_A.equals("None") && !lstPlayerOnline.get(i).name_A.equals(activePlayer.name_A)) {
					countParty++;

					if(countParty == 1) {

						spr_TagParty = gameControl.LoadInterfaceGamePlay("tagParty", "1", "");
						spr_TagParty.draw(game.batch);

						spr_TagPartyHair = gameControl.LoadInterfaceGamePlay("hairTagParty1",lstPlayerOnline.get(i).hair_A,lstPlayerOnline.get(i).sex_A);
						spr_TagPartyHair.draw(game.batch);	

						if(!lstPlayerOnline.get(i).hat_A.equals("none")) {
							spr_TagPartyHat = gameControl.LoadInterfaceGamePlay("hatTagParty1",lstPlayerOnline.get(i).hat_A,lstPlayerOnline.get(i).sex_A);
							spr_TagPartyHat.draw(game.batch);
						}

						font_master.draw(game.batch, lstPlayerOnline.get(i).name_A, cameraCoordsX - 54,cameraCoordsY + 65f);
						font_master.draw(game.batch, lstPlayerOnline.get(i).hp_A, cameraCoordsX - 56.7f,cameraCoordsY + 60.9f);
						font_master.draw(game.batch, lstPlayerOnline.get(i).mp_A, cameraCoordsX - 47.9f,cameraCoordsY + 60.9f);
						font_master.draw(game.batch, lstPlayerOnline.get(i).level_A, cameraCoordsX - 54.5f,cameraCoordsY + 57f);
						font_master.draw(game.batch, lstPlayerOnline.get(i).map_A, cameraCoordsX - 60.3f,cameraCoordsY + 52.7f);										
					}

					if(countParty == 2) {
						spr_TagParty = gameControl.LoadInterfaceGamePlay("tagParty", "2", "");
						spr_TagParty.draw(game.batch);

						spr_TagPartyHair = gameControl.LoadInterfaceGamePlay("hairTagParty2",lstPlayerOnline.get(i).hair_A,lstPlayerOnline.get(i).sex_A);
						spr_TagPartyHair.draw(game.batch);	

						if(!lstPlayerOnline.get(i).hat_A.equals("none")) {
							spr_TagPartyHat = gameControl.LoadInterfaceGamePlay("hatTagParty2",lstPlayerOnline.get(i).hat_A,lstPlayerOnline.get(i).sex_A);
							spr_TagPartyHat.draw(game.batch);
						}

						font_master.draw(game.batch, lstPlayerOnline.get(i).name_A, cameraCoordsX - 54,cameraCoordsY + 45.8f);
						font_master.draw(game.batch, lstPlayerOnline.get(i).hp_A, cameraCoordsX - 56.7f,cameraCoordsY + 42);
						font_master.draw(game.batch, lstPlayerOnline.get(i).mp_A, cameraCoordsX - 47.9f,cameraCoordsY + 42);
						font_master.draw(game.batch, lstPlayerOnline.get(i).level_A, cameraCoordsX - 54.5f,cameraCoordsY + 38);
						font_master.draw(game.batch, lstPlayerOnline.get(i).map_A, cameraCoordsX - 60.3f,cameraCoordsY + 33.5f);
					}

					if(countParty == 3) {
						spr_TagParty = gameControl.LoadInterfaceGamePlay("tagParty", "3", "");
						spr_TagParty.draw(game.batch);

						spr_TagPartyHair = gameControl.LoadInterfaceGamePlay("hairTagParty3",lstPlayerOnline.get(i).hair_A,lstPlayerOnline.get(i).sex_A);
						spr_TagPartyHair.draw(game.batch);	//here

						if(!lstPlayerOnline.get(i).hat_A.equals("none")) {
							spr_TagPartyHat = gameControl.LoadInterfaceGamePlay("hatTagParty3",lstPlayerOnline.get(i).hat_A,lstPlayerOnline.get(i).sex_A);
							spr_TagPartyHat.draw(game.batch);
						}

						font_master.draw(game.batch, lstPlayerOnline.get(i).name_A, cameraCoordsX - 54,cameraCoordsY + 26.8f);
						font_master.draw(game.batch, lstPlayerOnline.get(i).hp_A, cameraCoordsX - 56.7f,cameraCoordsY + 23);
						font_master.draw(game.batch, lstPlayerOnline.get(i).mp_A, cameraCoordsX - 47.9f,cameraCoordsY + 23);
						font_master.draw(game.batch, lstPlayerOnline.get(i).level_A, cameraCoordsX - 54.5f,cameraCoordsY + 19);
						font_master.draw(game.batch, lstPlayerOnline.get(i).map_A, cameraCoordsX - 60.3f,cameraCoordsY + 14.5f);
					}					
				}
			}
			countParty = 0;	
		}
	}

	private void ShowNPCs() {		
		//JOB NPC
		//spr_npc = gameControl.LoadInterfaceGamePlay("btnjobchange","","");
		//spr_npc.draw(game.batch);


		//lstNPCs = gameControl.GetNpcsStreets305();
		//for(int i = 0; i < lstNPCs.size(); i++) {
		//	spr_npc = lstNPCs.get(i);
		//	spr_npc.draw(game.batch);
		//}
	}

	private void ShowMonsters() {
		lstMobs = gameControl.GetMonsters();
		for(int i = 0; i < lstMobs.size(); i++) {
			spr_mob = gameControl.MobAppear(i);
			spr_mob.draw(game.batch);
			font_master.draw(game.batch, lstMobs.get(i).mobID + ":" + lstMobs.get(i).hp + "/" + lstMobs.get(i).maxHP, spr_mob.getX(), spr_mob.getY());
		}	

		spr_TargetArrow = gameControl.TargetMobArrow();
		if(spr_TargetArrow != null) {
			//spr_TargetArrow.draw(game.batch);
		}
	}

	public void ShowDamage() {
		lstDano = gameControl.GetDamageList();
		for(int i = 0; i < lstDano.size(); i++) {			
			lstDano.get(i).frame++;	
			if(lstDano.get(i).frame > 50) { gameControl.RemoveDamage(i); return; }
			if(lstDano.get(i).color.equals("Yellow")) { font_master.setColor(Color.YELLOW); }
			if(lstDano.get(i).color.equals("Red")) { font_master.setColor(Color.RED); }
			if(lstDano.get(i).color.equals("Green")) { font_master.setColor(Color.GREEN); }
			font_master.getData().setScale(0.20f,0.23f);
			font_master.setUseIntegerPositions(false);	
			font_master.draw(game.batch, String.valueOf(lstDano.get(i).dmg), lstDano.get(i).posX, lstDano.get(i).posY);

			font_master.setColor(Color.WHITE);
			font_master.getData().setScale(0.09f,0.11f);
			font_master.setUseIntegerPositions(false);
		}
	}

	public void ShowSkillUsed() {
		lstSkill = gameControl.GetSkillList();

		for(int i = 0; i < lstSkill.size(); i++) {	

			if(lstSkill.get(i).follow == false) {
				spr_skill = gameControl.EffectSkill(lstSkill.get(i));
				if(spr_skill != null) {
					lstSkill.get(i).frame++;
					spr_skill.draw(game.batch);

					if(lstSkill.get(i).frame > 60) { lstSkill.remove(i); }
				}
			}
			else {
				spr_skill = gameControl.EffectSkill(lstSkill.get(i));
				if(spr_skill != null) {
					lstSkill.get(i).frame++;
					spr_skill.draw(game.batch);

					if(lstSkill.get(i).frame > 60) { lstSkill.remove(i); }
				}
			}
		}
	}

	public void ShowBuffs() {
		gameControl.BuffEffectDuration();

		spr_buff = gameControl.ReturnIconBuffs(1);
		if(spr_buff != null) { spr_buff.draw(game.batch); }

		spr_buff = gameControl.ReturnIconBuffs(2);
		if(spr_buff != null) { spr_buff.draw(game.batch); }

		spr_buff = gameControl.ReturnIconBuffs(3);
		if(spr_buff != null) { spr_buff.draw(game.batch); }		
	}

	@Override
	public void input(String input) {
		text = input;

		if(typeParty) {
			if(text.equals("None")) { return; }
			gameControl.SetPartyName(text);
			typeParty = false;
			return;
		}

		if(network) {
			try {
				gameControl.OnlineOperation("Chat", text);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		else {
			lstChats.add(input);
		}		
	}

	@Override
	public void canceled() {
		// TODO Auto-generated method stub			
	}

	@Override
	public boolean keyDown(int keycode) {

		if(deathCheck) { return false; }

		if(gameState.equals("Main")) {		
			movement = true;
			downKeys.add(keycode);
			if (downKeys.size >= 2){
				onMultipleKeysDown(keycode);
			}
			if(downKeys.size == 1) {
				if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
					state = "left";
					walk = "walk";    		
				}

				if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
					state = "back";
					walk = "walk";
				}

				if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
					state = "front";
					walk = "walk";	
				}

				if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
					state = "right";
					walk = "walk";	   			
				} 		
			}      
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		downKeys.remove(keycode);
		movement = false;
		walk = "stop";
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int p1, int p2, int pointer, int button) {
		// TODO Auto-generated method stub

		if(deathCheck) { return false; }

		Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));
		if(gameState.equals("Main")) {

			if(isAreaSkill) {
				gameState = "casting";
				areaSkillSelected = true;
				skillTouchX = coordsTouch.x;
				skillTouchY = coordsTouch.y;
				castTime = gameControl.GetCastTime(numSkillCast);
				return false;
			}
			else {
				movement = true;
			}

			//Action Button
			if(coordsTouch.x >= (cameraCoordsX + 23) && coordsTouch.x <= (cameraCoordsX + 30) && coordsTouch.y >= (cameraCoordsY - 37) && coordsTouch.y <= (cameraCoordsY - 22)) {
				ActionVerify();
				return false;
			}

			//Menu button
			if(coordsTouch.x >= (cameraCoordsX - 66.5f) && coordsTouch.x <= (cameraCoordsX - 57.5f) && coordsTouch.y >= (cameraCoordsY + 68) && coordsTouch.y <= (cameraCoordsY + 75)) {
				gameState = "Menu";
				return false;
			}		

			//Atk Button
			if(coordsTouch.x >= (cameraCoordsX + 58) && coordsTouch.x <= (cameraCoordsX + 67) && coordsTouch.y >= (cameraCoordsY - 7) && coordsTouch.y <= (cameraCoordsY + 7)) {
				if(autoAtk.equals("yes")) {
					autoAtk = "no";
					return false;
				}
				if(autoAtk.equals("no")) {
					autoAtk = "yes";
					return false;
				}
				return false;
			}

			//Switch Target
			if(coordsTouch.x >= (cameraCoordsX + 58) && coordsTouch.x <= (cameraCoordsX + 67) && coordsTouch.y >= (cameraCoordsY + 10) && coordsTouch.y <= (cameraCoordsY + 25)) {
				gameControl.SwitchTarget();
				return false;
			}

			//Chat Button
			if(coordsTouch.x >= (cameraCoordsX - 56.5f) && coordsTouch.x <= (cameraCoordsX - 47.5f) && coordsTouch.y >= (cameraCoordsY + 68) && coordsTouch.y <= (cameraCoordsY + 75)) {
				Gdx.input.getTextInput(this,"Mensagem","","");
				return false;
			}

			//Hotbar 1
			if(coordsTouch.x >= (cameraCoordsX + 51) && coordsTouch.x <= (cameraCoordsX + 59) && coordsTouch.y >= (cameraCoordsY - 22) && coordsTouch.y <= (cameraCoordsY - 9)) {
				gameControl.UseItemHotbar(1);
				return false;
			}

			//Hotbar 2
			if(coordsTouch.x >= (cameraCoordsX + 59) && coordsTouch.x <= (cameraCoordsX + 67) && coordsTouch.y >= (cameraCoordsY - 22) && coordsTouch.y <= (cameraCoordsY - 9)) {
				gameControl.UseItemHotbar(2);
				return false;
			}

			//Skill 1
			if(coordsTouch.x >= (cameraCoordsX + 31) && coordsTouch.x <= (cameraCoordsX + 37) && coordsTouch.y >= (cameraCoordsY - 37) && coordsTouch.y <= (cameraCoordsY - 23)) {
				isAreaSkill = gameControl.CheckSkillType(1);
				numSkillCast = 1;
				if(!isAreaSkill) {
					if(activePlayer.job_A.equals("Novice")) { gameControl.SkillAtkNovice(1); }
					if(activePlayer.job_A.equals("Swordman")) { gameControl.SkillAtkSwordman(1); }	
					if(activePlayer.job_A.equals("Thief")) { gameControl.SkillAtkThief(1); }
					if(activePlayer.job_A.equals("Gunner")) { gameControl.SkillAtkGunner(1); }
					if(activePlayer.job_A.equals("Beater")) { gameControl.SkillAtkBeater(1); }
				}
				return false;
			}
			//Skill 2
			if(coordsTouch.x >= (cameraCoordsX + 38) && coordsTouch.x <= (cameraCoordsX + 45) && coordsTouch.y >= (cameraCoordsY - 37) && coordsTouch.y <= (cameraCoordsY - 23)) {
				isAreaSkill = gameControl.CheckSkillType(2);
				numSkillCast = 2;
				if(!isAreaSkill) {
					if(activePlayer.job_A.equals("Novice")) { gameControl.SkillAtkNovice(2); }
					if(activePlayer.job_A.equals("Swordman")) { gameControl.SkillAtkSwordman(2); }
					if(activePlayer.job_A.equals("Thief")) { gameControl.SkillAtkThief(2); }
					if(activePlayer.job_A.equals("Gunner")) { gameControl.SkillAtkGunner(2); }
					if(activePlayer.job_A.equals("Beater")) { gameControl.SkillAtkBeater(2); }
				}
				return false;
			}
			//Skill 3
			if(coordsTouch.x >= (cameraCoordsX + 45) && coordsTouch.x <= (cameraCoordsX + 52) && coordsTouch.y >= (cameraCoordsY - 37) && coordsTouch.y <= (cameraCoordsY - 23)) {
				isAreaSkill = gameControl.CheckSkillType(3);
				numSkillCast = 3;
				if(!isAreaSkill) {
					if(activePlayer.job_A.equals("Novice")) { gameControl.SkillAtkNovice(3); }
					if(activePlayer.job_A.equals("Swordman")) { gameControl.SkillAtkSwordman(3); }	
					if(activePlayer.job_A.equals("Thief")) { gameControl.SkillAtkThief(3); }
					if(activePlayer.job_A.equals("Gunner")) { gameControl.SkillAtkGunner(3); }
					if(activePlayer.job_A.equals("Beater")) { gameControl.SkillAtkBeater(3); }
				}
				return false;
			}
			//Skill 4
			if(coordsTouch.x >= (cameraCoordsX + 52) && coordsTouch.x <= (cameraCoordsX + 59) && coordsTouch.y >= (cameraCoordsY - 37) && coordsTouch.y <= (cameraCoordsY - 23)) {
				isAreaSkill = gameControl.CheckSkillType(4);
				numSkillCast = 4;
				if(!isAreaSkill) {
					if(activePlayer.job_A.equals("Novice")) { gameControl.SkillAtkNovice(4); }
					if(activePlayer.job_A.equals("Swordman")) { gameControl.SkillAtkSwordman(4); }	
					if(activePlayer.job_A.equals("Thief")) { gameControl.SkillAtkThief(4); }
					if(activePlayer.job_A.equals("Gunner")) { gameControl.SkillAtkGunner(4); }
					if(activePlayer.job_A.equals("Beater")) { gameControl.SkillAtkBeater(4); }
				}
				return false;
			}
			//Skill 5
			if(coordsTouch.x >= (cameraCoordsX + 59) && coordsTouch.x <= (cameraCoordsX + 67) && coordsTouch.y >= (cameraCoordsY - 37) && coordsTouch.y <= (cameraCoordsY - 23)) {
				isAreaSkill = gameControl.CheckSkillType(5);
				numSkillCast = 5;
				if(!isAreaSkill) {
					if(activePlayer.job_A.equals("Novice")) { gameControl.SkillAtkNovice(5); }
					if(activePlayer.job_A.equals("Swordman")) { gameControl.SkillAtkSwordman(5); }
					if(activePlayer.job_A.equals("Thief")) { gameControl.SkillAtkThief(5); }
					if(activePlayer.job_A.equals("Gunner")) { gameControl.SkillAtkGunner(5); }
					if(activePlayer.job_A.equals("Beater")) { gameControl.SkillAtkBeater(5); }
				}
				return false;
			}
		}


		//Menu
		if(gameState.equals("Menu")) {
			movement = false;

			//Status Menu button
			if(coordsTouch.x >= (cameraCoordsX + 57.5f) && coordsTouch.x <= (cameraCoordsX + 67f) && coordsTouch.y >= (cameraCoordsY + 62) && coordsTouch.y <= (cameraCoordsY + 75)) {
				gameState = "Menu-Status";
				return false;
			}

			//Item Menu button
			if(coordsTouch.x >= (cameraCoordsX + 57.5f) && coordsTouch.x <= (cameraCoordsX + 67f) && coordsTouch.y >= (cameraCoordsY + 51) && coordsTouch.y <= (cameraCoordsY + 61)) {
				gameState = "Menu-Itens";
				return false;
			}		

			//Skill Menu button
			if(coordsTouch.x >= (cameraCoordsX + 57.5f) && coordsTouch.x <= (cameraCoordsX + 67f) && coordsTouch.y >= (cameraCoordsY + 39) && coordsTouch.y <= (cameraCoordsY + 50)) {
				gameState = "Menu-Skills";
				return false;
			}

			//Social Menu button
			if(coordsTouch.x >= (cameraCoordsX + 57.5f) && coordsTouch.x <= (cameraCoordsX + 67f) && coordsTouch.y >= (cameraCoordsY + 27) && coordsTouch.y <= (cameraCoordsY + 38)) {
				gameState = "Menu-Social";
				return false;
			}

			//Pet Menu button
			if(coordsTouch.x >= (cameraCoordsX + 57.5f) && coordsTouch.x <= (cameraCoordsX + 67f) && coordsTouch.y >= (cameraCoordsY + 15) && coordsTouch.y <= (cameraCoordsY + 26)) {
				gameState = "Menu-Pet";
				return false;
			}

			//Config Menu button
			if(coordsTouch.x >= (cameraCoordsX + 57.5f) && coordsTouch.x <= (cameraCoordsX + 67f) && coordsTouch.y >= (cameraCoordsY + 4) && coordsTouch.y <= (cameraCoordsY + 14)) {
				gameState = "Menu-Config";
				return false;
			}

			//Sair Menu button
			if(coordsTouch.x >= (cameraCoordsX + 57.5f) && coordsTouch.x <= (cameraCoordsX + 67f) && coordsTouch.y >= (cameraCoordsY - 5) && coordsTouch.y <= (cameraCoordsY + 3)) {
				gameState = "Main";
				return false;
			}
		}

		if(gameState.equals("Menu-Status")) {
			//Voltar
			if(coordsTouch.x >= (cameraCoordsX + 32) && coordsTouch.x <= (cameraCoordsX + 49) && coordsTouch.y >= (cameraCoordsY + 65) && coordsTouch.y <= (cameraCoordsY + 83)) {
				gameState = "Menu";
				return false;
			}
			//Str Point
			if(coordsTouch.x >= (cameraCoordsX - 6.5f) && coordsTouch.x <= (cameraCoordsX + 46.5f) && coordsTouch.y >= (cameraCoordsY + 48.2f) && coordsTouch.y <= (cameraCoordsY + 56.2f)) {
				gameControl.DistributeStatusPoint("Str");
				return false;
			}		
			//Agi Point
			if(coordsTouch.x >= (cameraCoordsX - 6.5f) && coordsTouch.x <= (cameraCoordsX + 46.5f) && coordsTouch.y >= (cameraCoordsY + 38f) && coordsTouch.y <= (cameraCoordsY + 47f)) {
				gameControl.DistributeStatusPoint("Agi");
				return false;
			}
			//Wis Point
			if(coordsTouch.x >= (cameraCoordsX - 6.5f) && coordsTouch.x <= (cameraCoordsX + 46.5f) && coordsTouch.y >= (cameraCoordsY + 29f) && coordsTouch.y <= (cameraCoordsY + 37f)) {
				gameControl.DistributeStatusPoint("Wis");
				return false;
			}
			//Vit Point
			if(coordsTouch.x >= (cameraCoordsX - 6.5f) && coordsTouch.x <= (cameraCoordsX + 46.5f) && coordsTouch.y >= (cameraCoordsY + 19f) && coordsTouch.y <= (cameraCoordsY + 28f)) {
				gameControl.DistributeStatusPoint("Vit");
				return false;
			}
			//Des Point
			if(coordsTouch.x >= (cameraCoordsX - 6.5f) && coordsTouch.x <= (cameraCoordsX + 46.5f) && coordsTouch.y >= (cameraCoordsY + 9f) && coordsTouch.y <= (cameraCoordsY + 18)) {
				gameControl.DistributeStatusPoint("Des");
				return false;
			}
			//Sor Point
			if(coordsTouch.x >= (cameraCoordsX - 6.5f) && coordsTouch.x <= (cameraCoordsX + 46.5f) && coordsTouch.y >= (cameraCoordsY) && coordsTouch.y <= (cameraCoordsY + 8)) {
				gameControl.DistributeStatusPoint("Sor");
				return false;
			}
			//Res Point
			if(coordsTouch.x >= (cameraCoordsX - 6.5f) && coordsTouch.x <= (cameraCoordsX + 46.5f) && coordsTouch.y >= (cameraCoordsY - 11) && coordsTouch.y <= (cameraCoordsY - 2)) {
				gameControl.DistributeStatusPoint("Res");
				return false;
			}		
		}
		if(gameState.equals("Menu-Itens")) {
			//Voltar
			if(coordsTouch.x >= (cameraCoordsX + 32) && coordsTouch.x <= (cameraCoordsX + 49) && coordsTouch.y >= (cameraCoordsY + 65) && coordsTouch.y <= (cameraCoordsY + 83)) {
				gameState = "Menu";
				return false;
			}

			//Tab 1
			if(coordsTouch.x >= (cameraCoordsX - 48) && coordsTouch.x <= (cameraCoordsX - 43) && coordsTouch.y >= (cameraCoordsY + 1) && coordsTouch.y <= (cameraCoordsY + 9)) {
				menuItemTab = 1;
				return false;
			}
			//Tab 2
			if(coordsTouch.x >= (cameraCoordsX - 43) && coordsTouch.x <= (cameraCoordsX - 38) && coordsTouch.y >= (cameraCoordsY + 1) && coordsTouch.y <= (cameraCoordsY + 9)) {
				menuItemTab = 2;
				return false;
			}
			//Tab 3
			if(coordsTouch.x >= (cameraCoordsX - 38) && coordsTouch.x <= (cameraCoordsX - 33) && coordsTouch.y >= (cameraCoordsY + 1) && coordsTouch.y <= (cameraCoordsY + 9)) {
				menuItemTab = 3;
				return false;
			}
			//Tab 4
			if(coordsTouch.x >= (cameraCoordsX - 33) && coordsTouch.x <= (cameraCoordsX - 27) && coordsTouch.y >= (cameraCoordsY + 1) && coordsTouch.y <= (cameraCoordsY + 9)) {
				menuItemTab = 4;
				return false;
			}

			//Item 1
			if(coordsTouch.x >= (cameraCoordsX - 47) && coordsTouch.x <= (cameraCoordsX - 39) && coordsTouch.y >= (cameraCoordsY + 43) && coordsTouch.y <= (cameraCoordsY + 56)) {
				if(menuItemTab == 1 && discart) { gameControl.DiscartItem(0); discart = false; return false; }
				if(menuItemTab == 2 && discart) { gameControl.DiscartItem(12); discart = false; return false; }
				if(menuItemTab == 3 && discart) { gameControl.DiscartItem(24); discart = false; return false; }
				if(menuItemTab == 4 && discart) { gameControl.DiscartItem(36); discart = false; return false; }

				if(menuItemTab == 1 && hotkey) { gameControl.HotKeyItem(0); hotkey = false; return false; }
				if(menuItemTab == 2 && hotkey) { gameControl.HotKeyItem(12); hotkey = false; return false; }
				if(menuItemTab == 3 && hotkey) { gameControl.HotKeyItem(24); hotkey = false; return false; }
				if(menuItemTab == 4 && hotkey) { gameControl.HotKeyItem(36); hotkey = false; return false; }

				if(menuItemTab == 1 && description) { gameControl.Decription(0); description = false; return false; }
				if(menuItemTab == 2 && description) { gameControl.Decription(12); description = false; return false; }
				if(menuItemTab == 3 && description) { gameControl.Decription(24); description = false; return false; }
				if(menuItemTab == 4 && description) { gameControl.Decription(36); description = false; return false; }

				if(menuItemTab == 1) { gameControl.UseItem(0);  }
				if(menuItemTab == 2) { gameControl.UseItem(12); }
				if(menuItemTab == 3) { gameControl.UseItem(24); }
				if(menuItemTab == 4) { gameControl.UseItem(36); }

				return false;
			}

			//Item 2
			if(coordsTouch.x >= (cameraCoordsX - 37.5f) && coordsTouch.x <= (cameraCoordsX - 29.5f) && coordsTouch.y >= (cameraCoordsY + 43) && coordsTouch.y <= (cameraCoordsY + 56)) {
				if(menuItemTab == 1 && discart) { gameControl.DiscartItem(1); discart = false; return false; }
				if(menuItemTab == 2 && discart) { gameControl.DiscartItem(13); discart = false; return false; }
				if(menuItemTab == 3 && discart) { gameControl.DiscartItem(25); discart = false; return false; }
				if(menuItemTab == 4 && discart) { gameControl.DiscartItem(37); discart = false; return false; }

				if(menuItemTab == 1 && hotkey) { gameControl.HotKeyItem(1); hotkey = false; return false; }
				if(menuItemTab == 2 && hotkey) { gameControl.HotKeyItem(13); hotkey = false; return false; }
				if(menuItemTab == 3 && hotkey) { gameControl.HotKeyItem(25); hotkey = false; return false; }
				if(menuItemTab == 4 && hotkey) { gameControl.HotKeyItem(37); hotkey = false; return false; }

				if(menuItemTab == 1 && description) { gameControl.Decription(1); description = false; return false; }
				if(menuItemTab == 2 && description) { gameControl.Decription(13); description = false; return false; }
				if(menuItemTab == 3 && description) { gameControl.Decription(25); description = false; return false; }
				if(menuItemTab == 4 && description) { gameControl.Decription(37); description = false; return false; }

				if(menuItemTab == 1) { gameControl.UseItem(1); }
				if(menuItemTab == 2) { gameControl.UseItem(13); }
				if(menuItemTab == 3) { gameControl.UseItem(25); }
				if(menuItemTab == 4) { gameControl.UseItem(37); }
				return false;
			}

			//Item 3
			if(coordsTouch.x >= (cameraCoordsX - 27.7f) && coordsTouch.x <= (cameraCoordsX - 19.7f) && coordsTouch.y >= (cameraCoordsY + 43) && coordsTouch.y <= (cameraCoordsY + 56)) {
				if(menuItemTab == 1 && discart) { gameControl.DiscartItem(2); discart = false; return false; }
				if(menuItemTab == 2 && discart) { gameControl.DiscartItem(14); discart = false; return false; }
				if(menuItemTab == 3 && discart) { gameControl.DiscartItem(26); discart = false; return false; }
				if(menuItemTab == 4 && discart) { gameControl.DiscartItem(38); discart = false; return false; }

				if(menuItemTab == 1 && hotkey) { gameControl.HotKeyItem(2); hotkey = false; return false; }
				if(menuItemTab == 2 && hotkey) { gameControl.HotKeyItem(14); hotkey = false; return false; }
				if(menuItemTab == 3 && hotkey) { gameControl.HotKeyItem(26); hotkey = false; return false; }
				if(menuItemTab == 4 && hotkey) { gameControl.HotKeyItem(38); hotkey = false; return false; }

				if(menuItemTab == 1 && description) { gameControl.Decription(2); description = false; return false; }
				if(menuItemTab == 2 && description) { gameControl.Decription(14); description = false; return false; }
				if(menuItemTab == 3 && description) { gameControl.Decription(26); description = false; return false; }
				if(menuItemTab == 4 && description) { gameControl.Decription(38); description = false; return false; }

				if(menuItemTab == 1) { gameControl.UseItem(2); }
				if(menuItemTab == 2) { gameControl.UseItem(14); }
				if(menuItemTab == 3) { gameControl.UseItem(26); }
				if(menuItemTab == 4) { gameControl.UseItem(38); }
				return false;
			}

			//Item 4
			if(coordsTouch.x >= (cameraCoordsX - 18f) && coordsTouch.x <= (cameraCoordsX - 10f) && coordsTouch.y >= (cameraCoordsY + 43) && coordsTouch.y <= (cameraCoordsY + 56)) {
				if(menuItemTab == 1 && discart) { gameControl.DiscartItem(3); discart = false; return false; }
				if(menuItemTab == 2 && discart) { gameControl.DiscartItem(15); discart = false; return false; }
				if(menuItemTab == 3 && discart) { gameControl.DiscartItem(27); discart = false; return false; }
				if(menuItemTab == 4 && discart) { gameControl.DiscartItem(39); discart = false; return false; }

				if(menuItemTab == 1 && hotkey) { gameControl.HotKeyItem(3); hotkey = false; return false; }
				if(menuItemTab == 2 && hotkey) { gameControl.HotKeyItem(15); hotkey = false; return false; }
				if(menuItemTab == 3 && hotkey) { gameControl.HotKeyItem(27); hotkey = false; return false; }
				if(menuItemTab == 4 && hotkey) { gameControl.HotKeyItem(39); hotkey = false; return false; }

				if(menuItemTab == 1 && description) { gameControl.Decription(3); description = false; return false; }
				if(menuItemTab == 2 && description) { gameControl.Decription(15); description = false; return false; }
				if(menuItemTab == 3 && description) { gameControl.Decription(27); description = false; return false; }
				if(menuItemTab == 4 && description) { gameControl.Decription(39); description = false; return false; }

				if(menuItemTab == 1) { gameControl.UseItem(3); }
				if(menuItemTab == 2) { gameControl.UseItem(15); }
				if(menuItemTab == 3) { gameControl.UseItem(27); }
				if(menuItemTab == 4) { gameControl.UseItem(39); }
				return false;
			}

			//Item 5
			if(coordsTouch.x >= (cameraCoordsX - 47) && coordsTouch.x <= (cameraCoordsX - 39) && coordsTouch.y >= (cameraCoordsY + 27) && coordsTouch.y <= (cameraCoordsY + 40.5f)) {
				if(menuItemTab == 1 && discart) { gameControl.DiscartItem(4); discart = false; return false; }
				if(menuItemTab == 2 && discart) { gameControl.DiscartItem(16); discart = false; return false; }
				if(menuItemTab == 3 && discart) { gameControl.DiscartItem(28); discart = false; return false; }
				if(menuItemTab == 4 && discart) { gameControl.DiscartItem(40); discart = false; return false; }

				if(menuItemTab == 1 && hotkey) { gameControl.HotKeyItem(4); hotkey = false; return false; }
				if(menuItemTab == 2 && hotkey) { gameControl.HotKeyItem(16); hotkey = false; return false; }
				if(menuItemTab == 3 && hotkey) { gameControl.HotKeyItem(28); hotkey = false; return false; }
				if(menuItemTab == 4 && hotkey) { gameControl.HotKeyItem(40); hotkey = false; return false; }

				if(menuItemTab == 1 && description) { gameControl.Decription(4); description = false; return false; }
				if(menuItemTab == 2 && description) { gameControl.Decription(16); description = false; return false; }
				if(menuItemTab == 3 && description) { gameControl.Decription(28); description = false; return false; }
				if(menuItemTab == 4 && description) { gameControl.Decription(40); description = false; return false; }

				if(menuItemTab == 1) { gameControl.UseItem(4); }
				if(menuItemTab == 2) { gameControl.UseItem(16); }
				if(menuItemTab == 3) { gameControl.UseItem(28); }
				if(menuItemTab == 4) { gameControl.UseItem(40); }

				return false;
			}

			//Item 6
			if(coordsTouch.x >= (cameraCoordsX - 37.5f) && coordsTouch.x <= (cameraCoordsX - 29.5f) && coordsTouch.y >= (cameraCoordsY + 27) && coordsTouch.y <= (cameraCoordsY + 40.5f)) {
				if(menuItemTab == 1 && discart) { gameControl.DiscartItem(5); discart = false; return false; }
				if(menuItemTab == 2 && discart) { gameControl.DiscartItem(17); discart = false; return false; }
				if(menuItemTab == 3 && discart) { gameControl.DiscartItem(29); discart = false; return false; }
				if(menuItemTab == 4 && discart) { gameControl.DiscartItem(41); discart = false; return false; }

				if(menuItemTab == 1 && hotkey) { gameControl.HotKeyItem(5); hotkey = false; return false; }
				if(menuItemTab == 2 && hotkey) { gameControl.HotKeyItem(17); hotkey = false; return false; }
				if(menuItemTab == 3 && hotkey) { gameControl.HotKeyItem(29); hotkey = false; return false; }
				if(menuItemTab == 4 && hotkey) { gameControl.HotKeyItem(41); hotkey = false; return false; }

				if(menuItemTab == 1 && description) { gameControl.Decription(5); description = false; return false; }
				if(menuItemTab == 2 && description) { gameControl.Decription(17); description = false; return false; }
				if(menuItemTab == 3 && description) { gameControl.Decription(29); description = false; return false; }
				if(menuItemTab == 4 && description) { gameControl.Decription(41); description = false; return false; }

				if(menuItemTab == 1) { gameControl.UseItem(5); }
				if(menuItemTab == 2) { gameControl.UseItem(17); }
				if(menuItemTab == 3) { gameControl.UseItem(29); }
				if(menuItemTab == 4) { gameControl.UseItem(41); }
				return false;
			}

			//Item 7
			if(coordsTouch.x >= (cameraCoordsX - 27.7f) && coordsTouch.x <= (cameraCoordsX - 19.7f) && coordsTouch.y >= (cameraCoordsY + 27) && coordsTouch.y <= (cameraCoordsY + 40.5f)) {
				if(menuItemTab == 1 && discart) { gameControl.DiscartItem(6); discart = false; return false; }
				if(menuItemTab == 2 && discart) { gameControl.DiscartItem(18); discart = false; return false; }
				if(menuItemTab == 3 && discart) { gameControl.DiscartItem(30); discart = false; return false; }
				if(menuItemTab == 4 && discart) { gameControl.DiscartItem(42); discart = false; return false; }

				if(menuItemTab == 1 && hotkey) { gameControl.HotKeyItem(6); hotkey = false; return false; }
				if(menuItemTab == 2 && hotkey) { gameControl.HotKeyItem(18); hotkey = false; return false; }
				if(menuItemTab == 3 && hotkey) { gameControl.HotKeyItem(30); hotkey = false; return false; }
				if(menuItemTab == 4 && hotkey) { gameControl.HotKeyItem(42); hotkey = false; return false; }

				if(menuItemTab == 1 && description) { gameControl.Decription(6); description = false; return false; }
				if(menuItemTab == 2 && description) { gameControl.Decription(18); description = false; return false; }
				if(menuItemTab == 3 && description) { gameControl.Decription(30); description = false; return false; }
				if(menuItemTab == 4 && description) { gameControl.Decription(42); description = false; return false; }

				if(menuItemTab == 1) { gameControl.UseItem(6); }
				if(menuItemTab == 2) { gameControl.UseItem(18); }
				if(menuItemTab == 3) { gameControl.UseItem(30); }
				if(menuItemTab == 4) { gameControl.UseItem(42); }
				return false;
			}

			//Item 8
			if(coordsTouch.x >= (cameraCoordsX - 18f) && coordsTouch.x <= (cameraCoordsX - 10f) && coordsTouch.y >= (cameraCoordsY + 27) && coordsTouch.y <= (cameraCoordsY + 40.5f)) {
				if(menuItemTab == 1 && discart) { gameControl.DiscartItem(7); discart = false; return false; }
				if(menuItemTab == 2 && discart) { gameControl.DiscartItem(19); discart = false; return false; }
				if(menuItemTab == 3 && discart) { gameControl.DiscartItem(31); discart = false; return false; }
				if(menuItemTab == 4 && discart) { gameControl.DiscartItem(43); discart = false; return false; }

				if(menuItemTab == 1 && hotkey) { gameControl.HotKeyItem(7); hotkey = false; return false; }
				if(menuItemTab == 2 && hotkey) { gameControl.HotKeyItem(19); hotkey = false; return false; }
				if(menuItemTab == 3 && hotkey) { gameControl.HotKeyItem(31); hotkey = false; return false; }
				if(menuItemTab == 4 && hotkey) { gameControl.HotKeyItem(43); hotkey = false; return false; }

				if(menuItemTab == 1 && description) { gameControl.Decription(7); description = false; return false; }
				if(menuItemTab == 2 && description) { gameControl.Decription(19); description = false; return false; }
				if(menuItemTab == 3 && description) { gameControl.Decription(31); description = false; return false; }
				if(menuItemTab == 4 && description) { gameControl.Decription(43); description = false; return false; }

				if(menuItemTab == 1) { gameControl.UseItem(7); }
				if(menuItemTab == 2) { gameControl.UseItem(19); }
				if(menuItemTab == 3) { gameControl.UseItem(31); }
				if(menuItemTab == 4) { gameControl.UseItem(43); }
				return false;
			}

			//Item 9
			if(coordsTouch.x >= (cameraCoordsX - 47) && coordsTouch.x <= (cameraCoordsX - 39) && coordsTouch.y >= (cameraCoordsY + 11f) && coordsTouch.y <= (cameraCoordsY + 24.7f)) {
				if(menuItemTab == 1 && discart) { gameControl.DiscartItem(8); discart = false; return false; }
				if(menuItemTab == 2 && discart) { gameControl.DiscartItem(20); discart = false; return false; }
				if(menuItemTab == 3 && discart) { gameControl.DiscartItem(32); discart = false; return false; }
				if(menuItemTab == 4 && discart) { gameControl.DiscartItem(44); discart = false; return false; }

				if(menuItemTab == 1 && hotkey) { gameControl.HotKeyItem(8); hotkey = false; return false; }
				if(menuItemTab == 2 && hotkey) { gameControl.HotKeyItem(20); hotkey = false; return false; }
				if(menuItemTab == 3 && hotkey) { gameControl.HotKeyItem(32); hotkey = false; return false; }
				if(menuItemTab == 4 && hotkey) { gameControl.HotKeyItem(44); hotkey = false; return false; }

				if(menuItemTab == 1 && description) { gameControl.Decription(8); description = false; return false; }
				if(menuItemTab == 2 && description) { gameControl.Decription(20); description = false; return false; }
				if(menuItemTab == 3 && description) { gameControl.Decription(32); description = false; return false; }
				if(menuItemTab == 4 && description) { gameControl.Decription(44); description = false; return false; }

				if(menuItemTab == 1) { gameControl.UseItem(8); }
				if(menuItemTab == 2) { gameControl.UseItem(20); }
				if(menuItemTab == 3) { gameControl.UseItem(32); }
				if(menuItemTab == 4) { gameControl.UseItem(44); }

				return false;
			}

			//Item 10
			if(coordsTouch.x >= (cameraCoordsX - 37.5f) && coordsTouch.x <= (cameraCoordsX - 29.5f) && coordsTouch.y >= (cameraCoordsY + 11f) && coordsTouch.y <= (cameraCoordsY + 24.7f)) {
				if(menuItemTab == 1 && discart) { gameControl.DiscartItem(9); discart = false; return false; }
				if(menuItemTab == 2 && discart) { gameControl.DiscartItem(21); discart = false; return false; }
				if(menuItemTab == 3 && discart) { gameControl.DiscartItem(33); discart = false; return false; }
				if(menuItemTab == 4 && discart) { gameControl.DiscartItem(45); discart = false; return false; }

				if(menuItemTab == 1 && hotkey) { gameControl.HotKeyItem(9); hotkey = false; return false; }
				if(menuItemTab == 2 && hotkey) { gameControl.HotKeyItem(21); hotkey = false; return false; }
				if(menuItemTab == 3 && hotkey) { gameControl.HotKeyItem(33); hotkey = false; return false; }
				if(menuItemTab == 4 && hotkey) { gameControl.HotKeyItem(45); hotkey = false; return false; }

				if(menuItemTab == 1 && description) { gameControl.Decription(9); description = false; return false; }
				if(menuItemTab == 2 && description) { gameControl.Decription(21); description = false; return false; }
				if(menuItemTab == 3 && description) { gameControl.Decription(33); description = false; return false; }
				if(menuItemTab == 4 && description) { gameControl.Decription(45); description = false; return false; }

				if(menuItemTab == 1) { gameControl.UseItem(9); }
				if(menuItemTab == 2) { gameControl.UseItem(21); }
				if(menuItemTab == 3) { gameControl.UseItem(33); }
				if(menuItemTab == 4) { gameControl.UseItem(45); }
				return false;
			}

			//Item 11
			if(coordsTouch.x >= (cameraCoordsX - 27.7f) && coordsTouch.x <= (cameraCoordsX - 19.7f) && coordsTouch.y >= (cameraCoordsY + 11f) && coordsTouch.y <= (cameraCoordsY + 24.7f)) {
				if(menuItemTab == 1 && discart) { gameControl.DiscartItem(10); discart = false; return false; }
				if(menuItemTab == 2 && discart) { gameControl.DiscartItem(22); discart = false; return false; }
				if(menuItemTab == 3 && discart) { gameControl.DiscartItem(34); discart = false; return false; }
				if(menuItemTab == 4 && discart) { gameControl.DiscartItem(46); discart = false; return false; }

				if(menuItemTab == 1 && hotkey) { gameControl.HotKeyItem(10); hotkey = false; return false; }
				if(menuItemTab == 2 && hotkey) { gameControl.HotKeyItem(22); hotkey = false; return false; }
				if(menuItemTab == 3 && hotkey) { gameControl.HotKeyItem(34); hotkey = false; return false; }
				if(menuItemTab == 4 && hotkey) { gameControl.HotKeyItem(46); hotkey = false; return false; }

				if(menuItemTab == 1 && description) { gameControl.Decription(10); description = false; return false; }
				if(menuItemTab == 2 && description) { gameControl.Decription(22); description = false; return false; }
				if(menuItemTab == 3 && description) { gameControl.Decription(34); description = false; return false; }
				if(menuItemTab == 4 && description) { gameControl.Decription(46); description = false; return false; }

				if(menuItemTab == 1) { gameControl.UseItem(10); }
				if(menuItemTab == 2) { gameControl.UseItem(22); }
				if(menuItemTab == 3) { gameControl.UseItem(34); }
				if(menuItemTab == 4) { gameControl.UseItem(46); }
				return false;
			}

			//Item 12
			if(coordsTouch.x >= (cameraCoordsX - 18f) && coordsTouch.x <= (cameraCoordsX - 10f) && coordsTouch.y >= (cameraCoordsY + 11f) && coordsTouch.y <= (cameraCoordsY + 24.7f)) {
				if(menuItemTab == 1 && discart) { gameControl.DiscartItem(11); discart = false; return false; }
				if(menuItemTab == 2 && discart) { gameControl.DiscartItem(23); discart = false; return false; }
				if(menuItemTab == 3 && discart) { gameControl.DiscartItem(35); discart = false; return false; }
				if(menuItemTab == 4 && discart) { gameControl.DiscartItem(47); discart = false; return false; }

				if(menuItemTab == 1 && hotkey) { gameControl.HotKeyItem(11); hotkey = false; return false; }
				if(menuItemTab == 2 && hotkey) { gameControl.HotKeyItem(23); hotkey = false; return false; }
				if(menuItemTab == 3 && hotkey) { gameControl.HotKeyItem(35); hotkey = false; return false; }
				if(menuItemTab == 4 && hotkey) { gameControl.HotKeyItem(47); hotkey = false; return false; }

				if(menuItemTab == 1 && description) { gameControl.Decription(11); description = false; return false; }
				if(menuItemTab == 2 && description) { gameControl.Decription(23); description = false; return false; }
				if(menuItemTab == 3 && description) { gameControl.Decription(35); description = false; return false; }
				if(menuItemTab == 4 && description) { gameControl.Decription(47); description = false; return false; }

				if(menuItemTab == 1) { gameControl.UseItem(11); }
				if(menuItemTab == 2) { gameControl.UseItem(23); }
				if(menuItemTab == 3) { gameControl.UseItem(35); }
				if(menuItemTab == 4) { gameControl.UseItem(47); }
				return false;
			}

			//Descartar
			if(coordsTouch.x >= (cameraCoordsX - 48) && coordsTouch.x <= (cameraCoordsX - 31) && coordsTouch.y >= (cameraCoordsY - 11f) && coordsTouch.y <= (cameraCoordsY - 3)) {
				description = false;
				hotkey = false;
				if(discart) { 
					discart = false;
				} 
				else 
				{ 
					discart = true; 
				}
				return false;
			}

			//Description
			if(coordsTouch.x >= (cameraCoordsX - 30) && coordsTouch.x <= (cameraCoordsX - 13) && coordsTouch.y >= (cameraCoordsY - 11f) && coordsTouch.y <= (cameraCoordsY - 3)) {
				discart = false;
				hotkey = false;
				if(description) { 
					description = false;
				} 
				else 
				{ 
					description = true; 
				}
				return false;
			}

			//Unequip Hat
			if(coordsTouch.x >= (cameraCoordsX - 2) && coordsTouch.x <= (cameraCoordsX + 7) && coordsTouch.y >= (cameraCoordsY + 42) && coordsTouch.y <= (cameraCoordsY + 56)) {
				gameControl.UnequipHat();
				return false;
			}


			//Hotkey
			if(coordsTouch.x >= (cameraCoordsX - 12) && coordsTouch.x <= (cameraCoordsX + 5) && coordsTouch.y >= (cameraCoordsY - 11f) && coordsTouch.y <= (cameraCoordsY - 3)) {
				discart = false;
				description = false;
				if(hotkey) { 
					hotkey = false;
				} 
				else 
				{ 
					hotkey = true; 
				}
				return false;
			}

			//Hotkey Quit 1
			if(coordsTouch.x >= (cameraCoordsX + 37) && coordsTouch.x <= (cameraCoordsX + 46) && coordsTouch.y >= (cameraCoordsY + 42) && coordsTouch.y <= (cameraCoordsY + 56)) {
				discart = false;
				hotkey = false;
				description = false;
				gameControl.WipeHotkey(1);
				return false;
			}

			//Hotkey Quit 2
			if(coordsTouch.x >= (cameraCoordsX + 37) && coordsTouch.x <= (cameraCoordsX + 46) && coordsTouch.y >= (cameraCoordsY + 27) && coordsTouch.y <= (cameraCoordsY + 41)) {
				discart = false;
				hotkey = false;
				description = false;
				gameControl.WipeHotkey(2);
				return false;
			}			
		}

		if(gameState.equals("Menu-Skills")) {
			//Voltar
			if(coordsTouch.x >= (cameraCoordsX + 32) && coordsTouch.x <= (cameraCoordsX + 49) && coordsTouch.y >= (cameraCoordsY + 65) && coordsTouch.y <= (cameraCoordsY + 83)) {
				gameState = "Menu";
				return false;
			}
		}
		if(gameState.equals("Menu-Social")) {
			//Voltar
			if(coordsTouch.x >= (cameraCoordsX + 32) && coordsTouch.x <= (cameraCoordsX + 49) && coordsTouch.y >= (cameraCoordsY + 65) && coordsTouch.y <= (cameraCoordsY + 83)) {
				gameState = "Menu";
				return false;
			}
			//Criar Grupo
			if(coordsTouch.x >= (cameraCoordsX - 48) && coordsTouch.x <= (cameraCoordsX - 1) && coordsTouch.y >= (cameraCoordsY + 15) && coordsTouch.y <= (cameraCoordsY + 34)) {
				typeParty = true;
				Gdx.input.getTextInput(this,"Nome do Grupo","","");
				return false;
			}
			//Sair do Grupo
			if(coordsTouch.x >= (cameraCoordsX) && coordsTouch.x <= (cameraCoordsX + 47) && coordsTouch.y >= (cameraCoordsY + 15) && coordsTouch.y <= (cameraCoordsY + 34)) {
				gameControl.LeaveParty();
				activePlayer.party_A = "None";
				gameState = "Main";
				return false;
			}
		}
		if(gameState.equals("Menu-Pet")) {
			//Voltar
			if(coordsTouch.x >= (cameraCoordsX + 32) && coordsTouch.x <= (cameraCoordsX + 49) && coordsTouch.y >= (cameraCoordsY + 65) && coordsTouch.y <= (cameraCoordsY + 83)) {
				gameState = "Menu";
				return false;
			}
		}
		if(gameState.equals("Menu-Config")) {
			//Voltar
			if(coordsTouch.x >= (cameraCoordsX + 32) && coordsTouch.x <= (cameraCoordsX + 49) && coordsTouch.y >= (cameraCoordsY + 65) && coordsTouch.y <= (cameraCoordsY + 83)) {
				gameState = "Menu";
				return false;
			}

			//Online Ligar
			if(coordsTouch.x >= (cameraCoordsX - 36) && coordsTouch.x <= (cameraCoordsX - 23) && coordsTouch.y >= (cameraCoordsY + 35) && coordsTouch.y <= (cameraCoordsY + 42)) {
				//network = true;
				//gameControl.OnlineManager("Sync","");
				//typeDisplay = "Config";
				//msgDisplay = "Online Ligado";
				//isDisplay = true;
				//countDisplay = 200;
				//networkState = "yes";
				return false;
			}

			//Online Desligar
			if(coordsTouch.x >= (cameraCoordsX - 17) && coordsTouch.x <= (cameraCoordsX - 3) && coordsTouch.y >= (cameraCoordsY + 35) && coordsTouch.y <= (cameraCoordsY + 42)) {
				//network = true;
				//gameControl.OnlineManager("Desligar","");
				//typeDisplay = "Config";
				//msgDisplay = "Online Desligado";
				//isDisplay = true;
				//countDisplay = 200;
				return false;
			}

			//Trocar Personagem
			if(coordsTouch.x >= (cameraCoordsX + 11) && coordsTouch.x <= (cameraCoordsX + 45) && coordsTouch.y >= (cameraCoordsY + 47) && coordsTouch.y <= (cameraCoordsY + 56)) {
				gameControl.UpdateDataSave(numPlayerActive);
				if(network) {
					game.AtualizaElementos(game,gameControl, config, platform, "Yes");
				}
				else {
					game.AtualizaElementos(game,gameControl, config, platform, "No");
				}

				game.Switch("CharacterSelect");
				network = false;
				return false;
			}

			//Upload Save
			if(coordsTouch.x >= (cameraCoordsX + 11) && coordsTouch.x <= (cameraCoordsX + 45) && coordsTouch.y >= (cameraCoordsY + 33) && coordsTouch.y <= (cameraCoordsY + 45)) {
				network = true;  
				gameControl.OnlineManager("Sync","");
				typeDisplay = "Config";
				isDisplay = true;
				countDisplay = 200;

				gameControl.OnlineManager("Upload","");
				msgDisplay = "Online Ligado / " + gameControl.ResultOnlineRequest();

				return false;
			}
		}


		//Job Board
		if(gameState.equals("jobpost")) {
			//Voltar
			if(coordsTouch.x >= (cameraCoordsX + 16) && coordsTouch.x <= (cameraCoordsX + 20) && coordsTouch.y >= (cameraCoordsY + 63) && coordsTouch.y <= (cameraCoordsY + 70)) {
				gameState = "Main";
				return false;
			}

			//Swordman
			if(coordsTouch.x >= (cameraCoordsX - 16) && coordsTouch.x <= (cameraCoordsX + 16) && coordsTouch.y >= (cameraCoordsY + 55) && coordsTouch.y <= (cameraCoordsY + 62)) {
				activePlayer.job_A = "Swordman";
				gameControl.UpdateJob("Swordman");
				gameState = "Main";
				return false;
			}
			//Mage
			if(coordsTouch.x >= (cameraCoordsX - 16) && coordsTouch.x <= (cameraCoordsX + 16) && coordsTouch.y >= (cameraCoordsY + 48) && coordsTouch.y <= (cameraCoordsY + 54)) {
				activePlayer.job_A = "Mage";
				gameControl.UpdateJob("Mage");
				gameState = "Main";
				return false;
			}
			//Thief
			if(coordsTouch.x >= (cameraCoordsX - 16) && coordsTouch.x <= (cameraCoordsX + 16) && coordsTouch.y >= (cameraCoordsY + 39) && coordsTouch.y <= (cameraCoordsY + 46)) {
				activePlayer.job_A = "Thief";
				gameControl.UpdateJob("Thief");
				gameState = "Main";
				return false;
			}
			//Medic
			if(coordsTouch.x >= (cameraCoordsX - 16) && coordsTouch.x <= (cameraCoordsX + 16) && coordsTouch.y >= (cameraCoordsY + 31) && coordsTouch.y <= (cameraCoordsY + 38)) {
				activePlayer.job_A = "Medic";
				gameControl.UpdateJob("Medic");
				gameState = "Main";
				return false;
			}
			//Gunner
			if(coordsTouch.x >= (cameraCoordsX - 16) && coordsTouch.x <= (cameraCoordsX + 16) && coordsTouch.y >= (cameraCoordsY + 23) && coordsTouch.y <= (cameraCoordsY + 30)) {
				activePlayer.job_A = "Gunner";
				gameControl.UpdateJob("Gunner");
				gameState = "Main";
				return false;
			}
			//Beater
			if(coordsTouch.x >= (cameraCoordsX - 16) && coordsTouch.x <= (cameraCoordsX + 16) && coordsTouch.y >= (cameraCoordsY + 15) && coordsTouch.y <= (cameraCoordsY + 22)) {
				activePlayer.job_A = "Beater";
				gameControl.UpdateJob("Beater");
				gameState = "Main";
				return false;
			}
		}


		if(gameState.equals("Shop")) {
			//Voltar
			if(coordsTouch.x >= (cameraCoordsX + 40) && coordsTouch.x <= (cameraCoordsX + 57) && coordsTouch.y >= (cameraCoordsY - 1) && coordsTouch.y <= (cameraCoordsY + 8)) {
				gameState = "Main";
				return false;
			}
			//Item 1
			if(coordsTouch.x >= (cameraCoordsX + 11) && coordsTouch.x <= (cameraCoordsX + 22) && coordsTouch.y >= (cameraCoordsY + 44) && coordsTouch.y <= (cameraCoordsY + 59)) {
				if(shop.equals("RefriShop")) { nameBuy = gameControl.ItemBuyStreets305("RefriShop", 1); timeBuyCount = 40; } 
				if(shop.equals("305")) { nameBuy = gameControl.ItemBuyStreets305("305", 1); timeBuyCount = 40; } 
				if(shop.equals("Classical")) { nameBuy = gameControl.ItemBuyStreets305("Classical", 1); timeBuyCount = 40; } 

				return false;
			}
			//Item 2
			if(coordsTouch.x >= (cameraCoordsX + 23) && coordsTouch.x <= (cameraCoordsX + 33) && coordsTouch.y >= (cameraCoordsY + 44) && coordsTouch.y <= (cameraCoordsY + 59)) {
				if(shop.equals("RefriShop")) { nameBuy =  gameControl.ItemBuyStreets305("RefriShop", 2); timeBuyCount = 40; }
				if(shop.equals("305")) { nameBuy = gameControl.ItemBuyStreets305("305", 2); timeBuyCount = 40; } 
				if(shop.equals("Classical")) { nameBuy = gameControl.ItemBuyStreets305("Classical", 2); timeBuyCount = 40; }
				return false;
			}
			//Item 3
			if(coordsTouch.x >= (cameraCoordsX + 35) && coordsTouch.x <= (cameraCoordsX + 45) && coordsTouch.y >= (cameraCoordsY + 44) && coordsTouch.y <= (cameraCoordsY + 59)) {
				if(shop.equals("RefriShop")) { nameBuy =  gameControl.ItemBuyStreets305("RefriShop", 3); timeBuyCount = 40; }
				if(shop.equals("305")) { nameBuy = gameControl.ItemBuyStreets305("305", 3); timeBuyCount = 40; } 
				if(shop.equals("Classical")) { nameBuy = gameControl.ItemBuyStreets305("Classical", 3); timeBuyCount = 40; }
				return false;
			}
			//Item 4
			if(coordsTouch.x >= (cameraCoordsX + 46) && coordsTouch.x <= (cameraCoordsX + 57) && coordsTouch.y >= (cameraCoordsY + 44) && coordsTouch.y <= (cameraCoordsY + 59)) {
				if(shop.equals("RefriShop")) { nameBuy =  gameControl.ItemBuyStreets305("RefriShop", 4); timeBuyCount = 40; }
				if(shop.equals("305")) { nameBuy = gameControl.ItemBuyStreets305("305", 4); timeBuyCount = 40; } 
				if(shop.equals("Classical")) { nameBuy = gameControl.ItemBuyStreets305("Classical", 4); timeBuyCount = 40; }
				return false;
			}

			//Item 5
			if(coordsTouch.x >= (cameraCoordsX + 11) && coordsTouch.x <= (cameraCoordsX + 22) && coordsTouch.y >= (cameraCoordsY + 27) && coordsTouch.y <= (cameraCoordsY + 42)) {
				if(shop.equals("305")) { nameBuy = gameControl.ItemBuyStreets305("305", 5); timeBuyCount = 40; } 
				if(shop.equals("Classical")) { nameBuy = gameControl.ItemBuyStreets305("Classical", 5); timeBuyCount = 40; }
				return false;
			}
			//Item 6
			if(coordsTouch.x >= (cameraCoordsX + 23) && coordsTouch.x <= (cameraCoordsX + 33) && coordsTouch.y >= (cameraCoordsY + 27) && coordsTouch.y <= (cameraCoordsY + 42)) {
				if(shop.equals("305")) { nameBuy = gameControl.ItemBuyStreets305("305", 6); timeBuyCount = 40; } 
				if(shop.equals("Classical")) { nameBuy = gameControl.ItemBuyStreets305("Classical", 6); timeBuyCount = 40; }
				return false;
			}
			//Item 7
			if(coordsTouch.x >= (cameraCoordsX + 35) && coordsTouch.x <= (cameraCoordsX + 45) && coordsTouch.y >= (cameraCoordsY + 27) && coordsTouch.y <= (cameraCoordsY + 42)) {
				if(shop.equals("305")) { nameBuy = gameControl.ItemBuyStreets305("305", 7); timeBuyCount = 40; } 
				if(shop.equals("Classical")) { nameBuy = gameControl.ItemBuyStreets305("Classical", 7); timeBuyCount = 40; }
				return false;
			}
			//Item 8
			if(coordsTouch.x >= (cameraCoordsX + 46) && coordsTouch.x <= (cameraCoordsX + 57) && coordsTouch.y >= (cameraCoordsY + 27) && coordsTouch.y <= (cameraCoordsY + 42)) {
				if(shop.equals("305")) { nameBuy = gameControl.ItemBuyStreets305("305", 8); timeBuyCount = 40; } 
				if(shop.equals("Classical")) { nameBuy = gameControl.ItemBuyStreets305("Classical", 8); timeBuyCount = 40; }
				return false;
			}	

			//Item 9
			if(coordsTouch.x >= (cameraCoordsX + 11) && coordsTouch.x <= (cameraCoordsX + 22) && coordsTouch.y >= (cameraCoordsY + 11) && coordsTouch.y <= (cameraCoordsY + 26)) {
				if(shop.equals("305")) { nameBuy = gameControl.ItemBuyStreets305("305", 9); timeBuyCount = 40; } 
				if(shop.equals("Classical")) { nameBuy = gameControl.ItemBuyStreets305("Classical", 9); timeBuyCount = 40; }
				return false;
			}
			//Item 10
			if(coordsTouch.x >= (cameraCoordsX + 23) && coordsTouch.x <= (cameraCoordsX + 33) && coordsTouch.y >= (cameraCoordsY + 11) && coordsTouch.y <= (cameraCoordsY + 26)) {
				if(shop.equals("305")) { nameBuy = gameControl.ItemBuyStreets305("305", 10); timeBuyCount = 40; } 
				if(shop.equals("Classical")) { nameBuy = gameControl.ItemBuyStreets305("Classical", 10); timeBuyCount = 40; }
				return false;
			}
			//Item 11
			if(coordsTouch.x >= (cameraCoordsX + 35) && coordsTouch.x <= (cameraCoordsX + 45) && coordsTouch.y >= (cameraCoordsY + 11) && coordsTouch.y <= (cameraCoordsY + 26)) {
				if(shop.equals("305")) { nameBuy = gameControl.ItemBuyStreets305("305", 11); timeBuyCount = 40; } 
				if(shop.equals("Classical")) { nameBuy = gameControl.ItemBuyStreets305("Classical", 11); timeBuyCount = 40; }
				return false;
			}
			//Item 12
			if(coordsTouch.x >= (cameraCoordsX + 46) && coordsTouch.x <= (cameraCoordsX + 57) && coordsTouch.y >= (cameraCoordsY + 11) && coordsTouch.y <= (cameraCoordsY + 26)) {
				if(shop.equals("305")) { nameBuy = gameControl.ItemBuyStreets305("305", 12); timeBuyCount = 40; } 
				if(shop.equals("Classical")) { nameBuy = gameControl.ItemBuyStreets305("Classical", 12); timeBuyCount = 40; }
				return false;
			}	
		}

		posTouchX = coordsTouch.x;
		posTouchY = coordsTouch.y;

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		walk = "stop";
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(movement == true){
			Vector3 coordsTouch = camera.unproject(new Vector3(screenX,screenY,0));

			//Right
			if(coordsTouch.x > (cameraCoordsX - 50.5f) && coordsTouch.x <= (cameraCoordsX - 40.5f) && coordsTouch.y > (cameraCoordsY - 24) && coordsTouch.y < (cameraCoordsY - 7)) {
				state = "right";
				walk = "walk";	
				return false;
			}

			//Left
			if(coordsTouch.x >= (cameraCoordsX - 60.5f) && coordsTouch.x < (cameraCoordsX - 50.5f) && coordsTouch.y > (cameraCoordsY - 24) && coordsTouch.y < (cameraCoordsY - 7)) {
				state = "left";
				walk = "walk";
				return false;
			}

			//Front
			if(coordsTouch.x > (cameraCoordsX - 54f) && coordsTouch.x < (cameraCoordsX - 47f) && coordsTouch.y > (cameraCoordsY - 31) && coordsTouch.y < (cameraCoordsY - 16)) {
				state = "front";
				walk = "walk";
				return false;
			}

			//Back
			if(coordsTouch.x > (cameraCoordsX - 54f) && coordsTouch.x < (cameraCoordsX - 47f) && coordsTouch.y > (cameraCoordsY - 16) && coordsTouch.y <= (cameraCoordsY)) {
				state = "back";
				walk = "walk";
				return false;
			}

			//Right-Back
			if(coordsTouch.x > (cameraCoordsX - 47f) && coordsTouch.x < (cameraCoordsX - 40.5f) && coordsTouch.y > (cameraCoordsY - 16) && coordsTouch.y <= (cameraCoordsY)) {
				state = "right-back";
				walk = "walk";
				return false;
			}

			//Left-Back
			if(coordsTouch.x > (cameraCoordsX - 60.5f) && coordsTouch.x < (cameraCoordsX - 54f) && coordsTouch.y > (cameraCoordsY - 16) && coordsTouch.y <= (cameraCoordsY)) {
				state = "left-back";
				walk = "walk";
				return false;
			}

			//Right-Front
			if(coordsTouch.x > (cameraCoordsX - 47f) && coordsTouch.x < (cameraCoordsX - 40.5f) && coordsTouch.y > (cameraCoordsY - 31) && coordsTouch.y <= (cameraCoordsY - 16)) {
				state = "right-front";
				walk = "walk";
				return false;
			}

			//Left-Front
			if(coordsTouch.x > (cameraCoordsX - 60.5f) && coordsTouch.x < (cameraCoordsX - 54f) && coordsTouch.y > (cameraCoordsY - 31) && coordsTouch.y <= (cameraCoordsY - 16)) {
				state = "left-front";
				walk = "walk";
				return false;
			}
		}
		return false;
	}

	private void onMultipleKeysDown (int mostRecentKeycode){
		//For multiple key presses
		if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
			if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
				state = "left-front";
				walk = "walk";  	
			}
		}
		if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
			if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
				state = "left-back";
				walk = "walk";		
			}
		}
		if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
			if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
				state = "right-back";
				walk = "walk";		
			}
		}
		if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
			if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
				state = "right-front";
				walk = "walk";		
			}
		}
		if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
			if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
				state = "back-right";
				walk = "walk";		
			}
		}
		if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
			if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
				state = "back-left";
				walk = "walk";		
			}
		}
		if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
			if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
				state = "front-right";
				walk = "walk";		
			}
		}
		if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
			if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
				state = "front-left";
				walk = "walk";		
			}
		}
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
