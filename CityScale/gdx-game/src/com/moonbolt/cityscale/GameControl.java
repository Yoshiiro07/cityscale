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
	float plusX;
	float minusX;
	float plusY;
	float minusY;
	float area;
	
	
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
			
			plusX = CheckSizePosition(70,24, "plus");
			minusX = CheckSizePosition(70,24, "minus");
			plusY = CheckSizePosition(10,20, "plus");
			minusY = CheckSizePosition(10,20, "minus");
			
			if(touchX >= minusX && touchX <= plusX && touchY >= minusY && touchY <= plusY) {
				CheckData();
				LoadData();
				
				return "ChangeScreen";
			}
		}
		
		return "";
	}
	
	//Check if it is touch in the correct area for buttons
	private float CheckSizePosition(float position, float size, String indicator) {
		area = 0;
		
		if(indicator.equals("plus")) {
			area = position + (size/2);		
		}
		if(indicator.equals("minus")){
			area = position - (size/2);					
		}
		
		return area;
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
