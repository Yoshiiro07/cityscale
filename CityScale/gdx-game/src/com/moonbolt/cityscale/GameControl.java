package com.moonbolt.cityscale;

public class GameControl {
	
	//Primitives
	private int pos;
	private String sidePlayer;
	
	//Objects
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
	public void 
	
	//[ONLINE METHODS]//
	public void OnlineOperations(String operation, String complement) { onlineManager.OnlineOperations(operation, complement); }
}
