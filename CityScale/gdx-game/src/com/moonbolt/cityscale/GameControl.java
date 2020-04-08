package com.moonbolt.cityscale;
import com.badlogic.gdx.graphics.g2d.*;

public class GameControl {
	
	//Primitives
	private int pos;
	private String sidePlayer;
	
	//Objects
	private Sprite spr_master;
	private Player Character_Data;
	private SaveManager saveManager;
	private OnlineManager onlineManager;
	private MoveManager moveManager;
	

	public GameControl() {
		saveManager = new SaveManager();
		moveManager = new MoveManager();
		onlineManager = new OnlineManager();
	}
	
	//[SAVE METHODS]//
	public void CreateNewData() { saveManager.CreateNewData(); }
	
	//[MOVIMENT METHODS]//
	public Sprite MoveCharacter(){
		spr_master = moveManager.MovChar();
		return spr_master;
	}
	
	//[ONLINE METHODS]//
	public void OnlineOperations(String operation, String complement) { onlineManager.OnlineOperations(operation, complement); }

    public void UpdateOnlineSave(){
		onlineManager.UpdateOnlineSave(saveManager);
	}
}
