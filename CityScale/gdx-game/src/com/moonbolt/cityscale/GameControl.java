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
				return "Masculino";
			}
			if(touchX >= 54.3 && touchX <= 66.6 && touchY >= 57.3 && touchY <= 64.1) {
				return "Feminino";
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
	private void SetCharacterActive(int num) {
		dataManager.SetActivePlayerData(num);
		activePlayer = dataManager.GetPlayer();
	}
	
	//[C] Character Code
	public Sprite ShowCharacterMenu(String sex) {
		spr_master = gameLoader.LoadCharactersMenu(sex);
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
