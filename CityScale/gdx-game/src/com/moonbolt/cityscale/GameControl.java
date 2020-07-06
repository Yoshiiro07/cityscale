package com.moonbolt.cityscale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameControl {

	//Sumary
	//[A] Interface
	//[B] Account Data
	
	//Object Variables
	private GameLoad gameLoader;	
	private DataManager dataManager;
	private Player activePlayer;
	
	//Game Variables
	private Sprite spr_master;
	private Texture tex_teste;
	
	//Primitive Variables
	
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
}
