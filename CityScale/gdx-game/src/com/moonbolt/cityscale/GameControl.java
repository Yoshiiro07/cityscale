package com.moonbolt.cityscale;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.*;

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
		private MonstersManager monstersManager;
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
		
		public int GetPosition() {
			return movManager.GetPosition();
		}
		
		public Sprite ShowWeapon(String side,String walk, String type, float posX, float posY) {
			return movManager.ShowWeapon(side, walk, type, posX, posY);
		}
		
		//InterfaceManager
		public Sprite InterfaceMain(String item) {
			return interfaceManager.InterfaceMain("IDbox");
		}
		
		public Sprite InterfaceBase(String item, String complement) {
			return interfaceManager.InterfaceBase(item, complement);
		}
		
		public String[] CameraSettings(String location) {
			return interfaceManager.CameraSettings(location);
		}
		
		public void UpdateCameraX(float cameraposX) {
			interfaceManager.UpdateCameraX(cameraposX);
		}
		
		public void UpdateCameraY(float cameraposY) {
			interfaceManager.UpdateCameraY(cameraposY);
		}
		
		public Sprite InterfaceMetroStation(String type, String complement) {
			return interfaceManager.InterfaceMetroStation(type, complement);
		}
		
		public Sprite ItensMenu(String item, String complement){
			return interfaceManager.ItensMenu(item,complement);
		}
		
	    public Sprite ItemDropImage(String item){
			return interfaceManager.ItemDropImage(item);
		}
		
		//Monsters Manager
		public void LoadMonstersMap(String map) {
			monstersManager.LoadMonstersMap(map);
		}
		
		public ArrayList<Sprite> ShowMonsters(float playerX, float playerY) {
			return ShowMonsters(playerX, playerY);
		}
		
		public ArrayList<String> ShowMonstersName(){
			return monstersManager.ShowMonstersName();
		}
		
		public void RespawnMonsters(){
			monstersManager.RespawnMonsters();
		}
		
		
		//Battle Manager
		public void RegenerateHPTiming() {
			battleManager.RegenerateHPTiming();
		}	
		public int GetCooldown() {
			return battleManager.GetCooldown();
		}
		public void VerifyAttack(String AutoAttack, String ManualAttack) {
			battleManager.VerifyAttack(AutoAttack, ManualAttack,interfaceManager, onlineManager);
		}
		
	    public ArrayList<Damage> ShowDamage(){
		    return battleManager.ShowDamage();
		}
		
		//Quest Manager
		public ArrayList<Sprite> ShowNPCS(String map) {
			return questManager.ShowNPCS(map);
		}
		
		//OnlineManager
		public void OperacaoOnline(String operacao, String text) {
			onlineManager.OperacaoOnline(operacao, text);
		}
		
		

}

