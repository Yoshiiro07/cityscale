package com.moonbolt.cityscale;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameControl {
		
		//Global Variables 
		//private Texture tex_teste;
		
		//Primitives
	
		//Objects
		private SaveFileManager saveFileManager;
		private MovementManager movManager; 
		private InterfaceManager interfaceManager;
		private OnlineManager onlineManager;
		private BattleManager battleManager;
		private QuestManager questManager;
		//Online Variables
		
		//Constructor//
		public GameControl(){
			//tex_teste = new Texture(Gdx.files.internal("data/assets/testdot.png"));
			//spr_master = new Sprite(tex_teste);
			
			saveFileManager = new SaveFileManager();
			movManager = new MovementManager();
			interfaceManager = new InterfaceManager();
			onlineManager = new OnlineManager();
			battleManager = new BattleManager();
			questManager = new QuestManager();
		}
		
		
		//Save Manager Methods
		public void LoadData() {
			saveFileManager.LoadData();
		}
		public String GetAccount() {
			return saveFileManager.GetAccount();
		}
		public String[] LoadShowCharSelectScreen() {
			return saveFileManager.LoadShowCharSelectScreen();
		}
		public boolean CheckExistCharacter(int num) {
			return saveFileManager.CheckExistCharacter(num);
		}
		public void SetNumberChar(int num) {
			saveFileManager.SetActiveCharacter(num);
		}
		public void CreateNewCharacter(String[] data) {
			saveFileManager.CreateNewCharacter(data);
		}
		public void DeleteCharacter(int num) {
			saveFileManager.DeleteCharacter(num);
		}
		public void CreateNewData() {
			saveFileManager.CreateNewData();
		}
		public void WriteDataCharacterActive() {
			saveFileManager.WriteDataCharacterActive();
		}
		public void UpdateMap(String map) {
			saveFileManager.UpdateMap(map);
		}
		
		public int RecoverActiveChar() {
			return saveFileManager.RecoverActiveChar();
		}
		
		public Player SetActiveCharacter(int num) {
			return saveFileManager.SetActiveCharacter(num);
		}
		
		//MovementManager
		public Sprite ReturnHairs(String hairName,String side,String walk,float posX,float posY) {
			return movManager.ReturnHairs(hairName, side, walk, posX, posY);
		}	
		public Sprite MovChar(String set,String side,String walk,String type,float posX,float posY,int posInterject) {
			return movManager.MovChar(set, side, walk, type, posX, posY, posInterject);
		}
		
		//InterfaceManager
		public Sprite InterfaceMain(String item) {
			return interfaceManager.InterfaceMain("IDbox");
		}
		
		public String[] CameraSettings(String location) {
			return interfaceManager.CameraSettings(location);
		}
		
		public void AtualizaCameraX(float cameraposX) {
			interfaceManager.AtualizaCameraX(cameraposX);
		}
		
		public void AtualizaCameraY(float cameraposY) {
			interfaceManager.AtualizaCameraX(cameraposY);
		}
		
		public Sprite InterfaceMetroStation(String type, String complement) {
			return interfaceManager.InterfaceMetroStation(type, complement);
		}
		
		
		//Battle Manager
		public void RegenerateHPTiming() {
			battleManager.RegenerateHPTiming();
		}
		
		//Quest Manager
		public Sprite ShowNPCS(String map) {
			return questManager.ShowNPCS(map);
		}
		
		//OnlineManager
		public void OperacaoOnline(String operacao, String text) {
			onlineManager.OperacaoOnline(operacao, text);
		}
		
		

}

