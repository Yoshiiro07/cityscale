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

public class Streets305 implements Screen, ApplicationListener, InputProcessor, TextInputListener {
		//Objects
		private MainGame game;
		private GameControl gameControl;
		private String[] config;
		private String platform;
		private boolean network = false;
		
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
		
		//Sprites
		private Sprite spr_Background;
		private Texture tex_Background;
		
		private Sprite spr_playerCharacter;
		private Sprite spr_playerHair;
		private Sprite spr_playerTag;
		private Sprite spr_playerHairTag;
		private Sprite spr_Minibar;
		private Sprite spr_Hotbar;
		private Sprite spr_BackController;
		private Sprite spr_Controller;
		private Sprite spr_Skill;
		private Sprite spr_Shop;
		private Sprite spr_item;
		private Sprite spr_mob;
		private Sprite spr_npc;
		
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
		private String detailItem = "";
		private String gameState = "Main";
		private String text = "";
		private int count = 0;
		private int menuItemTab = 1;
		private int countDisplay = 0;
		private boolean isDisplay = false;
		private String typeDisplay = "";
		private String msgDisplay = "";
		private String[] logItens;
		private ArrayList<Monster> lstMobs;
		
			
		//fonts
		private BitmapFont font_master;
		
		//Online
		private ArrayList<String> lstChats;
		private ArrayList<Player> lstPlayerOnline;
		private Sprite spr_playerCharacterOnline;
		private Sprite spr_playerHairOnline;
		
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
		
		public Streets305(MainGame gameAlt, GameControl gameControl,String[] configAlt,String platformAlt) {
			this.game = gameAlt;
			this.gameControl = gameControl;
			this.config = configAlt;
			this.platform = platformAlt;
			
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
			lstMobs = new ArrayList<Monster>();
			lstMobs = gameControl.LoadMonsters("Sewers");
			
			//Sprites
			tex_Background = new Texture(Gdx.files.internal("data/maps/streets305.png"));
			spr_Background = new Sprite(tex_Background);
			spr_Background.setSize(100, 100);
			
			spr_Skill = new Sprite(tex_testeDot);
			spr_Shop = new Sprite(tex_testeDot);
		}
		
		
		@Override
		public void render(float delta) {
			
			Gdx.gl.glClearColor(1,1,1,1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			//Regen Timer
			gameControl.RegenerateHPMP();
			//Save Data
			gameControl.UpdateDataSave(numPlayerActive);
			
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
			
			//Player Character
			spr_playerCharacter = gameControl.MovPlayerCharacter(activePlayer.set_A,activePlayer.sex_A,walk,state, false);
			spr_playerCharacter.setSize(22, 34);
			spr_playerCharacter.setPosition(playerPosX, playerPosY);
			spr_playerCharacter.draw(game.batch);
			
			spr_playerHair = gameControl.MovPlayerHair(activePlayer.hair_A,activePlayer.sex_A,state, "Main");
			spr_playerHair.draw(game.batch);
				
			//UI Elements
			spr_playerTag = gameControl.LoadInterfaceGamePlay("playerTag","","");
			spr_playerTag.draw(game.batch);
			
			spr_playerHairTag = gameControl.LoadInterfaceGamePlay("hairTag",activePlayer.hair_A,"");
			spr_playerHairTag.draw(game.batch);
			
			spr_Minibar = gameControl.LoadInterfaceGamePlay("minibar", "","");
			spr_Minibar.draw(game.batch);
			
			spr_Hotbar = gameControl.LoadInterfaceGamePlay("hotbar", "","");
			spr_Hotbar.draw(game.batch);
			
			spr_BackController = gameControl.LoadInterfaceGamePlay("outerpad", "","");
			spr_BackController.draw(game.batch);
			
			spr_Controller = gameControl.LoadInterfaceGamePlay("innerpad", walk,state);
			spr_Controller.draw(game.batch);
			
			font_master.setColor(Color.WHITE);
			font_master.getData().setScale(0.09f,0.11f);
			font_master.setUseIntegerPositions(false);	
			font_master.draw(game.batch, activePlayer.name_A, cameraCoordsX - 52f,cameraCoordsY + 91.5f);
			font_master.draw(game.batch, activePlayer.hp_A, cameraCoordsX - 54f,cameraCoordsY + 87.8f);
			font_master.draw(game.batch, activePlayer.mp_A, cameraCoordsX - 54f,cameraCoordsY + 81.8f);
			font_master.draw(game.batch, activePlayer.level_A, cameraCoordsX - 41f,cameraCoordsY + 87.8f);
			font_master.draw(game.batch, activePlayer.exp_A, cameraCoordsX - 43f,cameraCoordsY + 81.8f);
			
			playerPosiX = Math.round(playerPosX);
			playerPosiY = Math.round(playerPosY);
			font_master.draw(game.batch, "X:" + Math.round(playerPosX), cameraCoordsX - 65f, cameraCoordsY + 66.7f);
			font_master.draw(game.batch, "Y:" + Math.round(playerPosY), cameraCoordsX - 48f, cameraCoordsY + 66.7f);
			
			
			font_master.draw(game.batch, "Chats:", cameraCoordsX - 37f, cameraCoordsY - 12.7f);
			for(count = 0; count < lstChats.size(); count++) {
				if(count == 0) {
					font_master.draw(game.batch, lstChats.get(count), cameraCoordsX - 37f, cameraCoordsY - 17.7f);
				}
				if(count == 1) {
					font_master.draw(game.batch, lstChats.get(count), cameraCoordsX - 37f, cameraCoordsY - 22.7f);
				}
				if(count == 2) {
					font_master.draw(game.batch, lstChats.get(count), cameraCoordsX - 37f, cameraCoordsY - 27.7f);
				}
				if(count == 3) {
					font_master.draw(game.batch, lstChats.get(count), cameraCoordsX - 37f, cameraCoordsY - 32.7f);
				}
				if(count == 4) {
					font_master.draw(game.batch, lstChats.get(count), cameraCoordsX - 37f, cameraCoordsY - 37.7f);
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
			
			String jo = activePlayer.job_A;
			if(activePlayer.job_A.equals("Novice")) {
				spr_Skill = gameControl.SkillHotbar("Novice","tripleattack"); spr_Skill.draw(game.batch);		
			}
			if(activePlayer.job_A.equals("Espadachim")) {
				spr_Skill = gameControl.SkillHotbar("Espadachim","tripleattack"); spr_Skill.draw(game.batch);
				spr_Skill = gameControl.SkillHotbar("Espadachim","icecrystal"); spr_Skill.draw(game.batch);			
				spr_Skill = gameControl.SkillHotbar("Espadachim","berserk"); spr_Skill.draw(game.batch);
				spr_Skill = gameControl.SkillHotbar("Espadachim","precision"); spr_Skill.draw(game.batch);
				spr_Skill = gameControl.SkillHotbar("Espadachim","fireball"); spr_Skill.draw(game.batch);			
			}
			if(activePlayer.job_A.equals("Mago")) {
				spr_Skill = gameControl.SkillHotbar("Mago","fireball"); spr_Skill.draw(game.batch);
				spr_Skill = gameControl.SkillHotbar("Mago","icecrystal"); spr_Skill.draw(game.batch);			
				spr_Skill = gameControl.SkillHotbar("Mago","thundercloud"); spr_Skill.draw(game.batch);
				spr_Skill = gameControl.SkillHotbar("Mago","rockbound"); spr_Skill.draw(game.batch);
				spr_Skill = gameControl.SkillHotbar("Mago","soulcrash"); spr_Skill.draw(game.batch);			
			}
			if(activePlayer.job_A.equals("Ladrao")) {
				spr_Skill = gameControl.SkillHotbar("Ladrao","tripleattack"); spr_Skill.draw(game.batch);
				spr_Skill = gameControl.SkillHotbar("Ladrao","icecrystal"); spr_Skill.draw(game.batch);			
				spr_Skill = gameControl.SkillHotbar("Ladrao","berserk"); spr_Skill.draw(game.batch);
				spr_Skill = gameControl.SkillHotbar("Ladrao","precision"); spr_Skill.draw(game.batch);
				spr_Skill = gameControl.SkillHotbar("Ladrao","fireball"); spr_Skill.draw(game.batch);			
			}
			if(activePlayer.job_A.equals("Medico")) {
				spr_Skill = gameControl.SkillHotbar("Medico","tripleattack"); spr_Skill.draw(game.batch);
				spr_Skill = gameControl.SkillHotbar("Medico","icecrystal"); spr_Skill.draw(game.batch);			
				spr_Skill = gameControl.SkillHotbar("Medico","berserk"); spr_Skill.draw(game.batch);
				spr_Skill = gameControl.SkillHotbar("Medico","precision"); spr_Skill.draw(game.batch);
				spr_Skill = gameControl.SkillHotbar("Medico","fireball"); spr_Skill.draw(game.batch);			
			}
			if(activePlayer.job_A.equals("Batedor")) {
				spr_Skill = gameControl.SkillHotbar("Batedor","tripleattack"); spr_Skill.draw(game.batch);
				spr_Skill = gameControl.SkillHotbar("Batedor","icecrystal"); spr_Skill.draw(game.batch);			
				spr_Skill = gameControl.SkillHotbar("Batedor","berserk"); spr_Skill.draw(game.batch);
				spr_Skill = gameControl.SkillHotbar("Batedor","precision"); spr_Skill.draw(game.batch);
				spr_Skill = gameControl.SkillHotbar("Batedor","fireball"); spr_Skill.draw(game.batch);			
			}
			if(activePlayer.job_A.equals("Pistoleiro")) {
				spr_Skill = gameControl.SkillHotbar("Pistoleiro","tripleattack"); spr_Skill.draw(game.batch);
				spr_Skill = gameControl.SkillHotbar("Pistoleiro","icecrystal"); spr_Skill.draw(game.batch);			
				spr_Skill = gameControl.SkillHotbar("Pistoleiro","berserk"); spr_Skill.draw(game.batch);
				spr_Skill = gameControl.SkillHotbar("Pistoleiro","precision"); spr_Skill.draw(game.batch);
				spr_Skill = gameControl.SkillHotbar("Pistoleiro","fireball"); spr_Skill.draw(game.batch);			
			}
			
			
			//Show Online Players
			ShowOnlinePlayers();
			
			
			//Show Monsters
			ShowMonsters();
				
			if(gameState.equals("Menu")) {
				spr_Menubar = gameControl.LoadInterfaceGamePlay("barMenu", "", "");
				spr_Menubar.draw(game.batch);
			}
			
			if(gameState.equals("Menu-Status")) {
				spr_MenuStatus = gameControl.LoadInterfaceGamePlay("menuStatus", "", "");
				spr_MenuStatus.draw(game.batch);
				
				font_master.draw(game.batch, activePlayer.job_A, cameraCoordsX - 37,cameraCoordsY + 62);
				font_master.draw(game.batch, activePlayer.level_A, cameraCoordsX - 39,cameraCoordsY + 57);

				
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
				
				spr_playerCharacter = gameControl.MovPlayerCharacter(activePlayer.set_A,activePlayer.sex_A,"stop","front", false);
				spr_playerCharacter.setSize(40, 60);
				spr_playerCharacter.setPosition(cameraCoordsX - 51, cameraCoordsY - 10);
				spr_playerCharacter.draw(game.batch);
				
				spr_playerHair = gameControl.MovPlayerHair(activePlayer.hair_A,activePlayer.sex_A,"stop", "Menu-Status");
				spr_playerHair.setSize(12,19);
				spr_playerHair.setPosition(cameraCoordsX - 37.8f, cameraCoordsY + 30.2f);
				spr_playerHair.draw(game.batch);
			}
			
			if(gameState.equals("Menu-Itens")) {
				spr_MenuItens = gameControl.LoadInterfaceGamePlay("menuItens", "", "");
				spr_MenuItens.draw(game.batch);
				
				spr_playerCharacter = gameControl.MovPlayerCharacter(activePlayer.set_A,activePlayer.sex_A,"stop","front", false);
				spr_playerCharacter.setSize(40, 60);
				spr_playerCharacter.setPosition(cameraCoordsX + 5, cameraCoordsY);
				spr_playerCharacter.draw(game.batch);
				
				spr_playerHair = gameControl.MovPlayerHair(activePlayer.hair_A,activePlayer.sex_A,"stop", "Menu-Status");
				spr_playerHair.setSize(12,19);
				spr_playerHair.setPosition(cameraCoordsX + 18.2f, cameraCoordsY + 40.3f);
				spr_playerHair.draw(game.batch);
				
				ShowItensBag();
			}
			
			if(gameState.equals("Menu-Skills")) {
				spr_MenuSkills = gameControl.LoadInterfaceGamePlay("menuSkills", "", "");
				spr_MenuSkills.draw(game.batch);
			}
			
			if(gameState.equals("Menu-Social")) {
				spr_MenuSocial = gameControl.LoadInterfaceGamePlay("menuSocial", "", "");
				spr_MenuSocial.draw(game.batch);
			}
			
			if(gameState.equals("Menu-Pet")) {
				spr_MenuPet = gameControl.LoadInterfaceGamePlay("menuPet", "", "");
				spr_MenuPet.draw(game.batch);
			}
			
			if(gameState.equals("Menu-Config")) {
				spr_MenuConfig = gameControl.LoadInterfaceGamePlay("menuConfig", "", "");
				spr_MenuConfig.draw(game.batch);
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
						
			// Test Dot
			spr_testeDot.setPosition(cameraCoordsX + 11, cameraCoordsY + 56);
			spr_testeDot.draw(game.batch);

			spr_testeDot.setPosition(cameraCoordsX + 45, cameraCoordsY + 47);
			spr_testeDot.draw(game.batch);
			
			game.batch.end();	
		}
		
		
		private void CheckColide() {
			if(playerPosX > 70 && playerPosY > -12.5f && playerPosY < 14) {
				changeScreen = true;
			}
			
			if(playerPosX < -8) {
				state = "right";
				breakWalk = "left";
			}
			
			if(playerPosY > 58) {
				state = "front";
				breakWalk = "back";
			}
			
			if(playerPosX > 7 && playerPosY > 15.5f) {
				state = "left";
				breakWalk = "right";
			}
			
			if(playerPosY < -7) {
				state = "back";
				breakWalk = "front";
			}
			
			breakWalk = "";
		}
		
		private void ActionVerify() {
			//Shop 1
			if(playerPosX > 118.2f && playerPosY < 134.2f && playerPosY < 68.6f) {
				
			}
			
			//Refri Shop
			if(playerPosX > -9.7f && playerPosX < 5.4f && playerPosY > 54f && playerPosY < 70.2f) {
				gameState = "Shop";
				shop = "RefriShop";
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
			//spr_item = gameControl.ShowEquippedItens(3); // Hat
			
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
					spr_playerCharacterOnline = gameControl.MovPlayerOnline(lstPlayerOnline.get(i));
					spr_playerCharacterOnline.setSize(22, 34);
					spr_playerCharacterOnline.draw(game.batch);
					
					spr_playerHairOnline = gameControl.MovPlayerOnlineHair(lstPlayerOnline.get(i));
					spr_playerHairOnline.draw(game.batch);
				}
			}
		}
		
		private void ShowMonsters() {
			
			lstMobs = gameControl.GetMonsters();
			
			for(int i = 0; i < lstMobs.size(); i++) {
				spr_mob = gameControl.MobAppear(i);
				spr_mob.draw(game.batch);
			}
			
		}
		
		@Override
		public void input(String input) {
			text = input;						
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
			
			Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));
			if(gameState.equals("Main")) {
				movement = true;
				
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
				
				//Chat Button
				if(coordsTouch.x >= (cameraCoordsX - 56.5f) && coordsTouch.x <= (cameraCoordsX - 47.5f) && coordsTouch.y >= (cameraCoordsY + 68) && coordsTouch.y <= (cameraCoordsY + 75)) {
					Gdx.input.getTextInput(this,"Mensagem","","");
					return false;
				}
				
				//Hotbar 1
				if(coordsTouch.x >= (cameraCoordsX + 51) && coordsTouch.x <= (cameraCoordsX + 59) && coordsTouch.y >= (cameraCoordsY - 22) && coordsTouch.y <= (cameraCoordsY - 9)) {
					return false;
				}
				
				//Hotbar 2
				if(coordsTouch.x >= (cameraCoordsX + 59) && coordsTouch.x <= (cameraCoordsX + 67) && coordsTouch.y >= (cameraCoordsY - 22) && coordsTouch.y <= (cameraCoordsY - 9)) {
					//HotBar 1
					return false;
				}
				
				//Skill 1
				if(coordsTouch.x >= (cameraCoordsX + 23) && coordsTouch.x <= (cameraCoordsX + 30) && coordsTouch.y >= (cameraCoordsY - 37) && coordsTouch.y <= (cameraCoordsY - 23)) {
					//Skill 1
					return false;
				}
				//Skill 2
				if(coordsTouch.x >= (cameraCoordsX + 31) && coordsTouch.x <= (cameraCoordsX + 37) && coordsTouch.y >= (cameraCoordsY - 37) && coordsTouch.y <= (cameraCoordsY - 23)) {
					//Skill 1
					return false;
				}
				//Skill 3
				if(coordsTouch.x >= (cameraCoordsX + 38) && coordsTouch.x <= (cameraCoordsX + 45) && coordsTouch.y >= (cameraCoordsY - 37) && coordsTouch.y <= (cameraCoordsY - 23)) {
					//Skill 1
					return false;
				}
				//Skill 4
				if(coordsTouch.x >= (cameraCoordsX + 45) && coordsTouch.x <= (cameraCoordsX + 52) && coordsTouch.y >= (cameraCoordsY - 37) && coordsTouch.y <= (cameraCoordsY - 23)) {
					//Skill 1
					return false;
				}
				//Skill 5
				if(coordsTouch.x >= (cameraCoordsX + 52) && coordsTouch.x <= (cameraCoordsX + 59) && coordsTouch.y >= (cameraCoordsY - 37) && coordsTouch.y <= (cameraCoordsY - 23)) {
					//Skill 1
					return false;
				}
				//Skill 6
				if(coordsTouch.x >= (cameraCoordsX + 59) && coordsTouch.x <= (cameraCoordsX + 67) && coordsTouch.y >= (cameraCoordsY - 37) && coordsTouch.y <= (cameraCoordsY - 23)) {
					//Skill 1
					return false;
				}
			}
			
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
					gameState = "Menu";
					return false;
				}		
				//Agi Point
				if(coordsTouch.x >= (cameraCoordsX - 6.5f) && coordsTouch.x <= (cameraCoordsX + 46.5f) && coordsTouch.y >= (cameraCoordsY + 38f) && coordsTouch.y <= (cameraCoordsY + 47f)) {
					gameState = "Menu";
					return false;
				}
				//Wis Point
				if(coordsTouch.x >= (cameraCoordsX - 6.5f) && coordsTouch.x <= (cameraCoordsX + 46.5f) && coordsTouch.y >= (cameraCoordsY + 29f) && coordsTouch.y <= (cameraCoordsY + 37f)) {
					gameState = "Menu";
					return false;
				}
				//Vit Point
				if(coordsTouch.x >= (cameraCoordsX - 6.5f) && coordsTouch.x <= (cameraCoordsX + 46.5f) && coordsTouch.y >= (cameraCoordsY + 19f) && coordsTouch.y <= (cameraCoordsY + 28f)) {
					gameState = "Menu";
					return false;
				}
				//Des Point
				if(coordsTouch.x >= (cameraCoordsX - 6.5f) && coordsTouch.x <= (cameraCoordsX + 46.5f) && coordsTouch.y >= (cameraCoordsY + 9f) && coordsTouch.y <= (cameraCoordsY + 18)) {
					gameState = "Menu";
					return false;
				}
				//Sor Point
				if(coordsTouch.x >= (cameraCoordsX - 6.5f) && coordsTouch.x <= (cameraCoordsX + 46.5f) && coordsTouch.y >= (cameraCoordsY + 8) && coordsTouch.y <= (cameraCoordsY)) {
					gameState = "Menu";
					return false;
				}
				//Res Point
				if(coordsTouch.x >= (cameraCoordsX - 6.5f) && coordsTouch.x <= (cameraCoordsX + 46.5f) && coordsTouch.y >= (cameraCoordsY - 11) && coordsTouch.y <= (cameraCoordsY - 2)) {
					gameState = "Menu";
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
					network = true;
					gameControl.OnlineManager("Sync","");
					typeDisplay = "Config";
					msgDisplay = "Online Ligado";
					isDisplay = true;
					countDisplay = 200;
					return false;
				}
				
				//Online Ligar
				if(coordsTouch.x >= (cameraCoordsX - 17) && coordsTouch.x <= (cameraCoordsX - 3) && coordsTouch.y >= (cameraCoordsY + 35) && coordsTouch.y <= (cameraCoordsY + 42)) {
					network = true;
					gameControl.OnlineManager("Desligar","");
					typeDisplay = "Config";
					msgDisplay = "Online Desligado";
					isDisplay = true;
					countDisplay = 200;
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
			}
			
			
			
			if(gameState.equals("Shop")) {
				//Voltar
				if(coordsTouch.x >= (cameraCoordsX + 32) && coordsTouch.x <= (cameraCoordsX + 49) && coordsTouch.y >= (cameraCoordsY + 65) && coordsTouch.y <= (cameraCoordsY + 83)) {
					gameState = "Main";
					return false;
				}
				//Item 1
				if(coordsTouch.x >= (cameraCoordsX + 11) && coordsTouch.x <= (cameraCoordsX + 22) && coordsTouch.y >= (cameraCoordsY + 44) && coordsTouch.y <= (cameraCoordsY + 59)) {
					if(shop.equals("RefriShop")) { gameControl.ItemBuy("RefriShop", 1); }
					return false;
				}
				//Item 2
				if(coordsTouch.x >= (cameraCoordsX + 23) && coordsTouch.x <= (cameraCoordsX + 33) && coordsTouch.y >= (cameraCoordsY + 44) && coordsTouch.y <= (cameraCoordsY + 59)) {
					if(shop.equals("RefriShop")) { gameControl.ItemBuy("RefriShop", 2); }
					return false;
				}
				//Item 3
				if(coordsTouch.x >= (cameraCoordsX + 35) && coordsTouch.x <= (cameraCoordsX + 45) && coordsTouch.y >= (cameraCoordsY + 44) && coordsTouch.y <= (cameraCoordsY + 59)) {
					if(shop.equals("RefriShop")) { gameControl.ItemBuy("RefriShop", 3);}
					return false;
				}
				//Item 4
				if(coordsTouch.x >= (cameraCoordsX + 46) && coordsTouch.x <= (cameraCoordsX + 57) && coordsTouch.y >= (cameraCoordsY + 44) && coordsTouch.y <= (cameraCoordsY + 59)) {
					if(shop.equals("RefriShop")) { gameControl.ItemBuy("RefriShop", 4);}
					return false;
				}
				
				//Item 5
				if(coordsTouch.x >= (cameraCoordsX + 11) && coordsTouch.x <= (cameraCoordsX + 22) && coordsTouch.y >= (cameraCoordsY + 27) && coordsTouch.y <= (cameraCoordsY + 42)) {
					if(shop.equals("RefriShop")) {}
					return false;
				}
				//Item 6
				if(coordsTouch.x >= (cameraCoordsX + 23) && coordsTouch.x <= (cameraCoordsX + 33) && coordsTouch.y >= (cameraCoordsY + 27) && coordsTouch.y <= (cameraCoordsY + 42)) {
					if(shop.equals("RefriShop")) {}
					return false;
				}
				//Item 7
				if(coordsTouch.x >= (cameraCoordsX + 35) && coordsTouch.x <= (cameraCoordsX + 45) && coordsTouch.y >= (cameraCoordsY + 27) && coordsTouch.y <= (cameraCoordsY + 42)) {
					if(shop.equals("RefriShop")) {}
					return false;
				}
				//Item 8
				if(coordsTouch.x >= (cameraCoordsX + 46) && coordsTouch.x <= (cameraCoordsX + 57) && coordsTouch.y >= (cameraCoordsY + 27) && coordsTouch.y <= (cameraCoordsY + 42)) {
					if(shop.equals("RefriShop")) {}
					return false;
				}	
				
				//Item 9
				if(coordsTouch.x >= (cameraCoordsX + 11) && coordsTouch.x <= (cameraCoordsX + 22) && coordsTouch.y >= (cameraCoordsY + 11) && coordsTouch.y <= (cameraCoordsY + 26)) {
					if(shop.equals("RefriShop")) {}
					return false;
				}
				//Item 10
				if(coordsTouch.x >= (cameraCoordsX + 23) && coordsTouch.x <= (cameraCoordsX + 33) && coordsTouch.y >= (cameraCoordsY + 11) && coordsTouch.y <= (cameraCoordsY + 26)) {
					if(shop.equals("RefriShop")) {}
					return false;
				}
				//Item 11
				if(coordsTouch.x >= (cameraCoordsX + 35) && coordsTouch.x <= (cameraCoordsX + 45) && coordsTouch.y >= (cameraCoordsY + 11) && coordsTouch.y <= (cameraCoordsY + 26)) {
					if(shop.equals("RefriShop")) {}
					return false;
				}
				//Item 12
				if(coordsTouch.x >= (cameraCoordsX + 46) && coordsTouch.x <= (cameraCoordsX + 57) && coordsTouch.y >= (cameraCoordsY + 11) && coordsTouch.y <= (cameraCoordsY + 26)) {
					if(shop.equals("RefriShop")) {}
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
