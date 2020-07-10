package com.moonbolt.cityscale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameControl {

	//Sumary
	//[A] Interface
	//[B] Account Data
	//[C] Character Code
	
	//Object Variables
	private GameLoad gameLoader;	
	private DataManager dataManager;
	private Player activePlayer;
	
	//Game Variables
	private Sprite spr_master;
	private Texture tex_teste;
	
	//Primitive Variables
	private String charState;
	
	
	public GameControl() {
		this.gameLoader = new GameLoad();
		this.dataManager = new DataManager();
		this.activePlayer = new Player();
		
		this.tex_teste = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		this.spr_master = new Sprite(tex_teste);
	}

	//[A] Interface
	//Load elements for show in UI.
	public Sprite LoadInterfaceCreate(String type) {		
		spr_master = gameLoader.LoadInterface(type);
		return spr_master;
	}
	
	//Load Elements from maps e screens 
	public Sprite LoadObjectElements(String type) {
		spr_master = gameLoader.LoadElements(type);
		return spr_master;
	}
	
	//Process comand select from UI. (Size/Position fixed from element)
	public String TouchVerify(float touchX, float touchY, String ScreenPress) {
		
		
		//Title Screen
		if(ScreenPress.equals("TitleScreen")) {
			//Start Button
			if(touchX >= 75.7 && touchX <= 87.7 && touchY >= 18.8 && touchY <= 24.8) {
				CheckData();
				LoadData();
				return "ChangeScreen";
			}
			//Recovery Button
			if(touchX >= 75.7 && touchX <= 87.7 && touchY >= 12.6 && touchY <= 17.7) {
			
			}
		}
		
		//Character Select Screen
		if(ScreenPress.equals("CharacterScreenMain")) {
			//Create Button
			if(touchX >= 84.9 && touchX <= 94.2 && touchY >= 2.4 && touchY <= 15.6) {
				return "CharacterScreenCreate";
			}
			//Delete Button
			if(touchX >= 5.1 && touchX <= 14.7 && touchY >= 2.2 && touchY <= 15.6) {
				return "CharacterScreenDelete";
			}
		}
		
		if(ScreenPress.equals("CharacterScreenCreate")) {
			//Name Button
			if(touchX >= 36.8 && touchX <= 57.3 && touchY >= 64.3 && touchY <= 74) {
				return "NameSelect";
			}
			//Sexo
			if(touchX >= 41.3 && touchX <= 53.6 && touchY >= 57.3 && touchY <= 64.1) {
				return "M";
			}
			if(touchX >= 54.3 && touchX <= 66.6 && touchY >= 57.3 && touchY <= 64.1) {
				return "F";
			}
			//Confirmar
			if(touchX >= 68.1 && touchX <= 80 && touchY >= 9.5 && touchY <= 15.8) {
				return "Confirmar";
			}
			//Voltar
			if(touchX >= 36.8 && touchX <= 49.1 && touchY >= 9 && touchY <= 15.8) {
				return "Voltar";
			}
			
			//Hair1 
			if(touchX >= 36.8 && touchX <= 42.6 && touchY >= 36.8 && touchY <= 47.6) {
				return "hair1";
			}
			//Hair2
			if(touchX >= 43.2 && touchX <= 48.9 && touchY >= 36.8 && touchY <= 47.6) {
				return "hair2";
			}
			//Hair3
			if(touchX >= 49.4 && touchX <= 55.1 && touchY >= 36.8 && touchY <= 47.6) {
				return "hair3";
			}
			//Hair4
			if(touchX >= 55.7 && touchX <= 61.4 && touchY >= 36.8 && touchY <= 47.6) {
				return "hair4";
			}
			//Hair5
			if(touchX >= 62 && touchX <= 74.0 && touchY >= 67.6 && touchY <= 47.6) {
				return "hair5";
			}
			//Hair6
			if(touchX >= 68.3 && touchX <= 74.0 && touchY >= 36.8 && touchY <= 47.6) {
				return "hair6";
			}
			
			//Hair7
			if(touchX >= 36.8 && touchX <= 42.6 && touchY >= 25.2 && touchY <= 35.7) {
				return "hair7";
			}
			//Hair8
			if(touchX >= 43.2 && touchX <= 48.9 && touchY >= 25.2 && touchY <= 35.7) {
				return "hair8";
			}
			//Hair9
			if(touchX >= 49.4 && touchX <= 55.1 && touchY >= 25.2 && touchY <= 35.7) {
				return "hair9";
			}
			//Hair10
			if(touchX >= 55.7 && touchX <= 61.4 && touchY >= 25.2 && touchY <= 35.7) {
				return "hair10";
			}
			//Hair11
			if(touchX >= 62 && touchX <= 74.0 && touchY >= 25.2 && touchY <= 35.7) {
				return "hair11";
			}
			//Hair12
			if(touchX >= 68.3 && touchX <= 74.0 && touchY >= 25.2 && touchY <= 35.7) {
				return "hair12";
			}
			
			
			
		}
		
		if(ScreenPress.equals("CharacterScreenDelete")) {
			//Name Button
			if(touchX >= 84.9 && touchX <= 94.2 && touchY >= 2.4 && touchY <= 15.6) {
				return "CharacterScreenDelete";
			}
		}
		
		
		return "";
	}
	
	public Sprite LoadAllHairs(int num, String sex) {
		spr_master = gameLoader.LoadAllHairsMenu(num, sex);
		
		return spr_master;
	}
	
	//[B] Account Data
	//Check if the player has already a account, if not creates one
	private void CheckData() {
		dataManager.CheckData();
	}
	
	//Load player data from account
	private void LoadData() {
		dataManager.LoadData();
	}
	
	//Set Active Character selected by player
	public void SetCharacterActive(int num) {
		dataManager.SetActivePlayerData(num);
		activePlayer = dataManager.GetPlayer();
	}
	
	public void GenerateCharacter(String name, String hair, String sex) {
		dataManager.CreateNewCharacter(name, hair, sex);
	}
	
	//[C] Character Code
	public Sprite ShowCharacterMenu(String sex) {
		spr_master = gameLoader.LoadCharactersMenu(sex);
		return spr_master;
	}
	
	public Sprite ShowCharacterHairMenu(String hair) {
		spr_master = gameLoader.LoadCharactersHairMenu(hair);
		return spr_master;
	}
	
	// Check 
	public Sprite ShowCharacterCreate(String set, String state, String sex) {	
		spr_master = gameLoader.LoadCharactersModel(set, state, sex);		
		return spr_master;
	}
	
	//
	public Player LoadPlayer() {
		dataManager.LoadData();
		activePlayer = dataManager.GetPlayer();
		return activePlayer;
	}
	
}
