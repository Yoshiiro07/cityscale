package com.moonbolt.cityscale;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class InterfaceManager
{
	//Main
	
	//Primitives
	private float fX;
	private float fY;
	private String qtdItem;
	private String text;
	private String[] itemUsage;
	private int countA;
	private int playerHPMAX;
	private int playerMPMAX;
	
	//Objects
	private Sprite spr_master;
	private Player Character_Data;
	
	//Textures
	private TextureAtlas atlas_gameplay_interface;
	private TextureAtlas atlas_objectsMetro;
	private TextureAtlas atlas_shop;
	private TextureAtlas atlas_btnskills;
	private TextureAtlas atlas_Usable;
	private TextureAtlas atlas_Loots;
	private TextureAtlas atlas_sets;
	
	
	public InterfaceManager(){
		atlas_gameplay_interface = new TextureAtlas(Gdx.files.internal("data/interface/gameplay/gameplay.txt"));
		atlas_objectsMetro = new TextureAtlas(Gdx.files.internal("data/assets/objects1.txt"));
		atlas_shop = new TextureAtlas(Gdx.files.internal("data/interface/shops/shops.txt"));
		atlas_btnskills = new TextureAtlas(Gdx.files.internal("data/interface/gameplay/skillicons.txt"));
		atlas_Usable = new TextureAtlas(Gdx.files.internal("data/itens/Usables/Usables.txt"));		
		atlas_Loots = new TextureAtlas(Gdx.files.internal("data/itens/Loots/Loots.txt"));
		atlas_sets = new TextureAtlas(Gdx.files.internal("data/itens/Sets/sets.txt"));
		
	}
	
	public void UpdateCharacterData(Player playerActive){
		this.Character_Data = playerActive;
	}
	
	public String ItemQuantidade() {	
		return qtdItem;
	}
	
	public void UpdateCameraX(float cameraposX) {
		fX = cameraposX;
	}
	public void UpdateCameraY(float cameraposY) {
		fY = cameraposY;
	}
	
	private String VerificaTipo(String item) {
		
		if(item.equals("Refrigerante")){
			return "usable";
		}
		
		return "";
	}
	
	public String ItemDescritivo(String item){
		if(item.equals("Refrigerante")){ return "Restaura 20 de HP";}
		if(item.equals("SucoHP")){ return "Restaura 90 de HP";}
		if(item.equals("IoguteHP")){ return "Restaura 200 de HP";}
		if(item.equals("RefrigeranteMP")){ return "Restaura 20 de MP";}
		if(item.equals("SucoMP")){ return "Restaura 50 de MP";}
		if(item.equals("IoguteMP")){ return "Restaura 100 de MP";}
		return "";
	}
	

	// Scenario Objects //
	public Sprite LoadObject(String name) {
		
		if(name.equals("metrobackword1")) {
			spr_master = atlas_objectsMetro.createSprite("metrobackword1");
		}
		if(name.equals("metrobackword2")) {
			spr_master = atlas_objectsMetro.createSprite("metrobackword2");
		}
		if(name.equals("metrobackword3")) {
			spr_master = atlas_objectsMetro.createSprite("metrobackword3");
		}
		
		if(name.equals("metroTV1")) {
			spr_master = atlas_objectsMetro.createSprite("metroTV1");
		}
		if(name.equals("metroTV2")) {
			spr_master = atlas_objectsMetro.createSprite("metroTV2");
		}
		if(name.equals("metroTV3")) {
			spr_master = atlas_objectsMetro.createSprite("metroTV3");
		}
		
		return spr_master;
	}
	
	public void VerificaItemSelecionado(int menuTab, int numItem) {
		
		//[SucoHP#3]-[SucoHP#3]
		
		String[] lstItem = Character_Data.Itens_A.split("-");
		String[] splitItem;
		String nomeItem = "";
		String qtdString = "";
		String tipoItem = "";
		String backItens = "";
		int itemSelecionado = numItem;
		int qtd;
		
		
		//Consolida o item
		if(lstItem[itemSelecionado].equals("[None]")) { return; }
		splitItem = lstItem[itemSelecionado].split("#");
		nomeItem = splitItem[0].replace("[", "");
		qtdString = splitItem[1].replace("]", "");
		qtd = Integer.parseInt(qtdString);
		tipoItem = VerificaTipo(nomeItem);
		if(tipoItem.equals("usable")) { 
			ItemEfeito(nomeItem);
			qtd = qtd -1;
			if(qtd == 0) { 
				lstItem[itemSelecionado] = "[None]"; 
				backItens = Arrays.toString(lstItem).replace(", ","-");
				backItens = backItens.substring(1, backItens.length() -1);
				Character_Data.Itens_A = backItens; 
				return;
			}
			if(qtd > 0) { 
				lstItem[itemSelecionado] = "[" + nomeItem + "#" + qtd + "]"; 
				backItens = Arrays.toString(lstItem).replace(", ","-");
				backItens = backItens.substring(1, backItens.length() -1);
				Character_Data.Itens_A = backItens; 
				return; 
			}						
		}
	}
	
	public void GiveExp(Monster mob) {
		
		int exp = Integer.parseInt(mob.EXP);
		int expPlayer = Integer.parseInt(Character_Data.Exp_A);
		int levelPlayer = Integer.parseInt(Character_Data.Level_A);
		int pontosStatus = Integer.parseInt(Character_Data.StatusPoint_A);
		expPlayer = expPlayer + exp;
		
		
		if(levelPlayer == 5) { return; }
		
		if(expPlayer >= 100 && levelPlayer == 1) { levelPlayer++; pontosStatus = pontosStatus + 3; expPlayer = 0;}
		if(expPlayer >= 350 && levelPlayer == 2) { levelPlayer++; pontosStatus = pontosStatus + 3; expPlayer = 0;}
		if(expPlayer >= 520 && levelPlayer == 3) { levelPlayer++; pontosStatus = pontosStatus + 3; expPlayer = 0;}
		if(expPlayer >= 780 && levelPlayer == 4) { levelPlayer++; pontosStatus = pontosStatus + 4; expPlayer = 0;}
		if(expPlayer >= 1220 && levelPlayer == 5) { levelPlayer++; pontosStatus = pontosStatus + 4; expPlayer = 0;}
		if(expPlayer >= 2500 && levelPlayer == 6) { levelPlayer++; pontosStatus = pontosStatus + 4; expPlayer = 0;}
		if(expPlayer >= 5600 && levelPlayer == 7) { levelPlayer++; pontosStatus = pontosStatus + 4; expPlayer = 0;}
		if(expPlayer >= 9500 && levelPlayer == 8) { levelPlayer++; pontosStatus = pontosStatus + 4; expPlayer = 0;}
		if(expPlayer >= 15200 && levelPlayer == 9) { levelPlayer++; pontosStatus = pontosStatus + 4; expPlayer = 0;}
		if(expPlayer >= 23400 && levelPlayer == 10) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
		if(expPlayer >= 26200 && levelPlayer == 11) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
		if(expPlayer >= 32000 && levelPlayer == 12) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
		if(expPlayer >= 35000 && levelPlayer == 13) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
		if(expPlayer >= 39000 && levelPlayer == 14) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
		if(expPlayer >= 43000 && levelPlayer == 15) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
		if(expPlayer >= 46400 && levelPlayer == 16) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
		if(expPlayer >= 52000 && levelPlayer == 17) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
		if(expPlayer >= 57000 && levelPlayer == 18) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
		if(expPlayer >= 59000 && levelPlayer == 19) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
		if(expPlayer >= 65000 && levelPlayer == 20) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
		if(expPlayer >= 72000 && levelPlayer == 21) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
		if(expPlayer >= 74000 && levelPlayer == 22) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
		if(expPlayer >= 79000 && levelPlayer == 23) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
		if(expPlayer >= 82000 && levelPlayer == 24) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
		if(expPlayer >= 85000 && levelPlayer == 25) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
		if(expPlayer >= 89000 && levelPlayer == 26) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
		if(expPlayer >= 92340 && levelPlayer == 27) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
		if(expPlayer >= 97420 && levelPlayer == 28) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
		if(expPlayer >= 110342 && levelPlayer == 29) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
		if(expPlayer >= 130020 && levelPlayer == 30) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
		if(expPlayer >= 150000 && levelPlayer == 31) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
		if(expPlayer >= 180900 && levelPlayer == 32) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
		if(expPlayer >= 223100 && levelPlayer == 33) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
		if(expPlayer >= 255221 && levelPlayer == 34) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
		if(expPlayer >= 290111 && levelPlayer == 35) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
		if(expPlayer >= 339999 && levelPlayer == 36) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
		if(expPlayer >= 496554 && levelPlayer == 37) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
		if(expPlayer >= 532312 && levelPlayer == 38) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
		if(expPlayer >= 699992 && levelPlayer == 39) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
		if(expPlayer >= 739231 && levelPlayer == 40) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
		if(expPlayer >= 892312 && levelPlayer == 41) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
		if(expPlayer >= 1324230 && levelPlayer == 42) { levelPlayer++; pontosStatus = pontosStatus + 8; expPlayer = 0;}
		if(expPlayer >= 1923120 && levelPlayer == 43) { levelPlayer++; pontosStatus = pontosStatus + 8; expPlayer = 0;}
		if(expPlayer >= 3245235 && levelPlayer == 44) { levelPlayer++; pontosStatus = pontosStatus + 8; expPlayer = 0;}
		if(expPlayer >= 5522332 && levelPlayer == 45) { levelPlayer++; pontosStatus = pontosStatus + 8; expPlayer = 0;}
		if(expPlayer >= 8023422 && levelPlayer == 46) { levelPlayer++; pontosStatus = pontosStatus + 8; expPlayer = 0;}
		if(expPlayer >= 11203245 && levelPlayer == 47) { levelPlayer++; pontosStatus = pontosStatus + 8; expPlayer = 0;}
		if(expPlayer >= 19064345 && levelPlayer == 48) { levelPlayer++; pontosStatus = pontosStatus + 8; expPlayer = 0;}
		if(expPlayer >= 36543199 && levelPlayer == 49) { levelPlayer++; pontosStatus = pontosStatus + 8; expPlayer = 0;}
		
		Character_Data.Exp_A = String.valueOf(expPlayer);
		Character_Data.Level_A = String.valueOf(levelPlayer);
		Character_Data.StatusPoint_A = String.valueOf(pontosStatus);
	}
	
	private void ResetaPontosStatus(){
		
		Character_Data.Strengh_A = "1";
		Character_Data.Vitality_A = "1";
		Character_Data.Agility_A = "1";
		Character_Data.Dextery_A = "1";
		Character_Data.Lucky_A = "1";
		Character_Data.Mind_A = "1";
		Character_Data.Resistence_A = "1";
		Character_Data.HPMAX_A = "100";
		Character_Data.MPMAX_A = "100";
		Character_Data.Stamina_A = "100";
		Character_Data.StatusPoint_A = "0";
		
		int levelPlayer = Integer.parseInt(Character_Data.Level_A);
		int pontosStatus = 0;
		
		if(levelPlayer >= 2){ pontosStatus = pontosStatus + 3; }
		if(levelPlayer >= 3){ pontosStatus = pontosStatus + 3; }
		if(levelPlayer >= 4){ pontosStatus = pontosStatus + 4; }
		if(levelPlayer >= 5){ pontosStatus = pontosStatus + 4;}
		if(levelPlayer >= 6){ pontosStatus = pontosStatus + 4;}
		if(levelPlayer >= 7){ pontosStatus = pontosStatus + 4;}
		if(levelPlayer >= 8){ pontosStatus = pontosStatus + 4;}
		if(levelPlayer >= 9){ pontosStatus = pontosStatus + 4;}
		if(levelPlayer >= 10){ pontosStatus = pontosStatus + 5;}
		if(levelPlayer >= 11){ pontosStatus = pontosStatus + 5;}
		if(levelPlayer >= 12){ pontosStatus = pontosStatus + 5;}
		if(levelPlayer >= 13){ pontosStatus = pontosStatus + 5;}
		if(levelPlayer >= 14){ pontosStatus = pontosStatus + 5;}
		if(levelPlayer >= 15){ pontosStatus = pontosStatus + 5;}
		if(levelPlayer >= 16){ pontosStatus = pontosStatus + 5;}
		if(levelPlayer >= 17){ pontosStatus = pontosStatus + 5;}
		if(levelPlayer >= 18){ pontosStatus = pontosStatus + 5;}
		if(levelPlayer >= 19){ pontosStatus = pontosStatus + 5;}
		if(levelPlayer >= 20){ pontosStatus = pontosStatus + 5;}
		if(levelPlayer >= 21){ pontosStatus = pontosStatus + 6;}
		if(levelPlayer >= 22){ pontosStatus = pontosStatus + 6;}
		if(levelPlayer >= 23){ pontosStatus = pontosStatus + 6;}
		if(levelPlayer >= 24){ pontosStatus = pontosStatus + 6;}
		if(levelPlayer >= 25){ pontosStatus = pontosStatus + 6;}
		if(levelPlayer >= 26){ pontosStatus = pontosStatus + 6;}
		if(levelPlayer >= 27){ pontosStatus = pontosStatus + 6;}
		if(levelPlayer >= 28){ pontosStatus = pontosStatus + 6;}
		if(levelPlayer >= 29){ pontosStatus = pontosStatus + 6;}
		if(levelPlayer >= 30){ pontosStatus = pontosStatus + 6;}
		if(levelPlayer >= 31){ pontosStatus = pontosStatus + 6;}
		if(levelPlayer >= 32){ pontosStatus = pontosStatus + 6;}
		if(levelPlayer >= 33){ pontosStatus = pontosStatus + 6;}
		if(levelPlayer >= 34){ pontosStatus = pontosStatus + 6;}
		if(levelPlayer >= 35){ pontosStatus = pontosStatus + 6;}
		if(levelPlayer >= 36){ pontosStatus = pontosStatus + 7;}
		if(levelPlayer >= 37){ pontosStatus = pontosStatus + 7;}
		if(levelPlayer >= 38){ pontosStatus = pontosStatus + 7;}
		if(levelPlayer >= 39){ pontosStatus = pontosStatus + 7;}
		if(levelPlayer >= 40){ pontosStatus = pontosStatus + 7;}
		if(levelPlayer >= 41){ pontosStatus = pontosStatus + 7;}
		if(levelPlayer >= 42){ pontosStatus = pontosStatus + 7;}
		if(levelPlayer >= 43){ pontosStatus = pontosStatus + 7;}
		if(levelPlayer >= 44){ pontosStatus = pontosStatus + 7;}
		if(levelPlayer >= 45){ pontosStatus = pontosStatus + 7;}
		if(levelPlayer >= 46){ pontosStatus = pontosStatus + 7;}
		if(levelPlayer >= 47){ pontosStatus = pontosStatus + 7;}
		if(levelPlayer >= 48){ pontosStatus = pontosStatus + 7;}
		if(levelPlayer >= 49){ pontosStatus = pontosStatus + 7;}
		if(levelPlayer >= 50){ pontosStatus = pontosStatus + 7;}			
	}
	
	public Sprite ItemImage(String item, int pos, float ccX, float ccY){
		
		itemUsage = item.split("#");
		item = itemUsage[0];
		text = item.replace("[","");
		
		if(text.equals("None")) { return spr_master; }
		
		if(text.equals("Refrigerante")) {
			spr_master = atlas_Usable.createSprite("Cola");
		}
		
		qtdItem = itemUsage[1].replace("]","");
		
		if(pos == 0 || pos == 12 || pos == 24 || pos == 36 || pos == 48) { spr_master.setPosition(ccX - 59, ccY - 3); }
		if(pos == 1 || pos == 13 || pos == 25 || pos == 37 || pos == 49) { spr_master.setPosition(ccX - 45, ccY - 3); }
		if(pos == 2 || pos == 14 || pos == 26 || pos == 38 || pos == 50) { spr_master.setPosition(ccX - 31, ccY - 3); }
		if(pos == 3 || pos == 15 || pos == 27 || pos == 39 || pos == 51) { spr_master.setPosition(ccX - 17, ccY - 3); }
		if(pos == 4 || pos == 16 || pos == 28 || pos == 40 || pos == 52) { spr_master.setPosition(ccX - 59, ccY - 29); }
		if(pos == 5 || pos == 17 || pos == 29 || pos == 41 || pos == 53) { spr_master.setPosition(ccX - 45, ccY - 29); }
		if(pos == 6 || pos == 18 || pos == 30 || pos == 42 || pos == 54) { spr_master.setPosition(ccX - 31, ccY - 29); }
		if(pos == 7 || pos == 19 || pos == 31 || pos == 43 || pos == 55) { spr_master.setPosition(ccX - 17, ccY - 29); }
		if(pos == 8 || pos == 20 || pos == 32 || pos == 44 || pos == 56) { spr_master.setPosition(ccX - 59, ccY - 55); }
		if(pos == 9 || pos == 21 || pos == 33 || pos == 45 || pos == 57) { spr_master.setPosition(ccX - 45, ccY - 55); }
		if(pos == 10 || pos == 22 || pos == 34 || pos == 46 || pos == 58) { spr_master.setPosition(ccX - 31, ccY - 55); }
		if(pos == 11 || pos == 23 || pos == 35 || pos == 47 || pos == 59) { spr_master.setPosition(ccX - 17, ccY - 55); }
		spr_master.setSize(15, 30);
		return spr_master;
	}
	
	public Sprite ItemEquipped(String item, float ccX, float ccY) {
		
		String set = Character_Data.Set_A;
		String weapon = Character_Data.Weapon_A;
		String hat = Character_Data.Hat_A;
		
		if(item.equals("weapon")) { 
			if(weapon.equals("basic_knife")) { spr_master = atlas_nKnifes.createSprite("basic_knife_right"); }			
			spr_master.setPosition(ccX - 7, ccY + 26);
			spr_master.setSize(25, 40);
		}
		
		if(item.equals("set")) { 
			if(set.equals("basic_set_male")) { spr_master = atlas_sets.createSprite("basicsetmale"); }
			if(set.equals("basic_set_female")) { spr_master = atlas_sets.createSprite("basicsetfemale"); }
			
			spr_master.setPosition(ccX + 9, ccY + 27);
			spr_master.setSize(20, 30);
		}
		
		if(item.equals("hat")) { 
			if(hat.equals("none")) { return spr_master = null; }
			
		}
		
		
		return spr_master;
	}
	
	private void EquipaItem() {
		
	}
	
	private void ItemEfeito(String item){
		//Consumivel
		if(item.equals("Refrigerante")){
			countA = Integer.parseInt(Character_Data.HP_A);
			countA = countA + 20;
			playerHPMAX = Integer.parseInt(Character_Data.HPMAX_A);
			if(countA >= playerHPMAX) { countA = playerHPMAX; } 
			Character_Data.HP_A = String.valueOf(countA);
			WriteDataCharacterActive();
		}
		if(item.equals("Soda")){
			countA = Integer.parseInt(Character_Data.MP_A);
			countA = countA + 50;
			playerMPMAX = Integer.parseInt(Character_Data.MPMAX_A);
			if(countA >= playerMPMAX) { countA = playerMPMAX; } 
			Character_Data.MP_A = String.valueOf(countA);
		}
		if(item.equals("IoguteMP")){
			countA = Integer.parseInt(Character_Data.MP_A);
			countA = countA + 100;
			Character_Data.MP_A = String.valueOf(countA);
		}
	}
	
	
	
	public boolean ExibirMsgItem() {
		
		if(showLootTime > 0) {
			showLootTime--;
			return true;
		}
		else {
			return false;
		}
	}
	
	public String itemDrop() {
		if(showLootTime > 0) {
			showLootTime--;
			return nomeLoot;
		}
		else {
			return nomeLoot;
		}
	}
	
	public Sprite ItemDropImage(String item) {
		
		if(showLootTime > 0) {
			if(item.equals("Refrigerante")) {
				spr_master = atlas_Usable.createSprite("Cola");
				spr_master.setSize(15, 30);
			}
		}			
		return spr_master;
	}
	
	
	public void VerificaItemCompra(String nomeloja, int numeroItem) {
		
		int money = Integer.parseInt(Character_Data.Money_A);		
		if(nomeloja.equals("SodaMachine")) {
			if(numeroItem == 1) {
				if(money >= 10) { 
				AdicionaItemMochila("Refrigerante"); 
				money = money - 10;  
				Character_Data.Money_A = String.valueOf(money); 
				}
			}
		}
	}
	
	public void AddItemMochila(String nomeItem) {
		String[] lstItem = Character_Data.Itens_A.split("-");
		String[] itemSplit;
		boolean exist = false;
		int qtd = 0;
		int posicaoItem = 0;
		String listaItemFinal;
		for(int i = 0; i < lstItem.length; i++) {
			if(lstItem[i].contains(nomeItem) && !exist) {
				posicaoItem = i;
				exist = true;
			}
		}
		
		if(exist) {
		itemSplit = lstItem[posicaoItem].split("#");
		qtd = Integer.parseInt(itemSplit[1].replace("]", ""));
		qtd++;
		if(qtd >= 99) { return;}
		lstItem[posicaoItem] = "[" + itemSplit[0].replace("[", "") + "#" + String.valueOf(qtd) + "]";
		listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
		listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
		Character_Data.Itens_A = listaItemFinal;
		}
		else {
			for(int i = 0; i < lstItem.length; i++) {
				if(lstItem[i].contains("None") && !exist) {
					posicaoItem = i;
					exist = true;
				}
			}
			
			if(exist) {
				lstItem[posicaoItem] = "[" + nomeItem + "#" + "1" + "]";
				listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
				listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
				Character_Data.Itens_A = listaItemFinal;
			}
		}
	}
	
	public String[] CameraSettings(String map) {
		String[] cameraSet = new String[3];
		if(map.equals("MetroStation")) {
			cameraSet[0] = "400";
			cameraSet[1] = "100";
			return cameraSet;
		}

		if(map.equals("Streets305")) {
			cameraSet[0] = "400";
			cameraSet[1] = "1200";
			return cameraSet;
		}

		return cameraSet;
	}
	
	
	public Sprite InterfaceMetroStation(String item, String complement) {

		if(item.equals("PlayerTag")) {
			spr_master = atlas_gameplay_interface.createSprite("tagplayer");
			spr_master.setSize(45, 35);
			spr_master.setPosition(fX - 98, fY + 68);
			return spr_master; 
		}

		if(item.equals("Portrait")) {
			spr_master = atlas_hairs.createSprite(complement);
			spr_master.setSize(14, 19);
			spr_master.setPosition(fX - 99, fY + 78);
			return spr_master; 
		}

		if(item.equals("Hotcrossbar")) {
			spr_master = atlas_gameplay_interface.createSprite("hotcrossbar");
			spr_master.setSize(55, 40);
			spr_master.setPosition(fX + 45, fY - 95);
			return spr_master;
		}

		if(item.equals("Backanalog")) {
			spr_master = atlas_gameplay_interface.createSprite("controlerbackground");
			spr_master.setSize(40, 50);
			spr_master.setPosition(fX - 78, fY - 80);
			return spr_master;
		}

		if(item.equals("Analog") && complement.equals("Stop")) {
			spr_master = atlas_gameplay_interface.createSprite("controllertouch");
			spr_master.setSize(20, 25);
			spr_master.setPosition(fX - 68, fY - 67);
			return spr_master;
		}

		if(item.equals("Analog") && complement.equals("Right")) {
			spr_master = atlas_gameplay_interface.createSprite("controllertouch");
			spr_master.setSize(20, 25);
			spr_master.setPosition(fX - 58, fY - 67);
			return spr_master;
		}

		if(item.equals("Analog") && complement.equals("Left")) {
			spr_master = atlas_gameplay_interface.createSprite("controllertouch");
			spr_master.setSize(20, 25);
			spr_master.setPosition(fX - 78, fY - 67);
			return spr_master;
		}

		if(item.equals("Analog") && complement.equals("Front")) {
			spr_master = atlas_gameplay_interface.createSprite("controllertouch");
			spr_master.setSize(20, 25);
			spr_master.setPosition(fX - 68, fY - 81);
			return spr_master;
		}
		if(item.equals("Analog") && complement.equals("Back")) {
			spr_master = atlas_gameplay_interface.createSprite("controllertouch");
			spr_master.setSize(20, 25);
			spr_master.setPosition(fX - 68, fY - 55);
			return spr_master;
		}

		return spr_master;
	}

	public Sprite InterfaceBase(String item, String complement) {

		if(item.equals("PlayerTag")) {
			spr_master = atlas_gameplay_interface.createSprite("tagplayer");
			spr_master.setSize(45, 35);
			spr_master.setPosition(fX - 100, fY + 95);
			return spr_master; 
		}

		if(item.equals("PartyTag1")) {
			spr_master = atlas_gameplay_interface.createSprite("tagparty");
			spr_master.setSize(38, 28);
			spr_master.setPosition(fX - 100, fY + 59);
			return spr_master; 
		}

		if(item.equals("PartyTag2")) {
			spr_master = atlas_gameplay_interface.createSprite("tagparty");
			spr_master.setSize(38, 28);
			spr_master.setPosition(fX - 100, fY + 31);
			return spr_master; 
		}

		if(item.equals("PartyTag3")) {
			spr_master = atlas_gameplay_interface.createSprite("tagparty");
			spr_master.setSize(38, 28);
			spr_master.setPosition(fX - 100, fY + 3);
			return spr_master; 
		}

		if(item.equals("deathmsg")) {
			spr_master = atlas_gameplay_interface.createSprite("mortemsg");
			spr_master.setSize(45, 35);
			spr_master.setPosition(fX - 15, fY + 8);
			return spr_master; 
		}

		if(item.equals("Portrait")) {
			spr_master = atlas_hairs.createSprite(complement);
			spr_master.setSize(14, 19);
			spr_master.setPosition(fX - 101, fY + 106);
			return spr_master; 
		}

		if(item.equals("Hotcrossbar")) {
			spr_master = atlas_gameplay_interface.createSprite("hotcrossbar");
			spr_master.setSize(55, 38);
			spr_master.setPosition(fX + 45, fY - 70);
			return spr_master;
		}

		if(item.equals("Backanalog")) {
			spr_master = atlas_gameplay_interface.createSprite("controlerbackground");
			spr_master.setSize(40, 50);
			spr_master.setPosition(fX - 78, fY - 50);
			return spr_master;
		}

		if(item.equals("Analog") && complement.equals("Stop")) {
			spr_master = atlas_gameplay_interface.createSprite("controllertouch");
			spr_master.setSize(20, 25);
			spr_master.setPosition(fX - 68, fY - 37);
			return spr_master;
		}

		if(item.equals("flagStreet305")) {
			spr_master = atlas_gameplay_interface.createSprite("flagStreets305");
			spr_master.setSize(30, 15);
			spr_master.setPosition(fX + 70, fY + 115);
			return spr_master;
		}

		if(item.equals("flagFlorestaA")) {
			spr_master = atlas_gameplay_interface.createSprite("flagFlorestaA");
			spr_master.setSize(30, 15);
			spr_master.setPosition(fX + 70, fY + 115);
			return spr_master;
		}

		if(item.equals("Analog") && complement.equals("Right")) {
			spr_master = atlas_gameplay_interface.createSprite("controllertouch");
			spr_master.setSize(20, 25);
			spr_master.setPosition(fX - 58, fY - 37);
			return spr_master;
		}

		if(item.equals("Analog") && complement.equals("Left")) {
			spr_master = atlas_gameplay_interface.createSprite("controllertouch");
			spr_master.setSize(20, 25);
			spr_master.setPosition(fX - 78, fY - 37);
			return spr_master;
		}

		if(item.equals("Analog") && complement.equals("Front")) {
			spr_master = atlas_gameplay_interface.createSprite("controllertouch");
			spr_master.setSize(20, 25);
			spr_master.setPosition(fX - 68, fY - 51);
			return spr_master;
		}
		if(item.equals("Analog") && complement.equals("Back")) {
			spr_master = atlas_gameplay_interface.createSprite("controllertouch");
			spr_master.setSize(20, 25);
			spr_master.setPosition(fX - 68, fY - 25);
			return spr_master;
		}

		if(item.equals("Analog") && complement.equals("Left-Front")) {
			spr_master = atlas_gameplay_interface.createSprite("controllertouch");
			spr_master.setSize(20, 25);
			spr_master.setPosition(fX - 76, fY - 46);
			return spr_master;
		}

		if(item.equals("Analog") && complement.equals("Right-Front")) {
			spr_master = atlas_gameplay_interface.createSprite("controllertouch");
			spr_master.setSize(20, 25);
			spr_master.setPosition(fX - 61, fY - 46);
			return spr_master;
		}

		if(item.equals("Analog") && complement.equals("Left-Back")) {
			spr_master = atlas_gameplay_interface.createSprite("controllertouch");
			spr_master.setSize(20, 25);
			spr_master.setPosition(fX - 76, fY - 30);
			return spr_master;
		}

		if(item.equals("Analog") && complement.equals("Right-Back")) {
			spr_master = atlas_gameplay_interface.createSprite("controllertouch");
			spr_master.setSize(20, 25);
			spr_master.setPosition(fX - 61, fY - 30);
			return spr_master;
		}

		if(item.equals("Analog") && complement.equals("Back-Left")) {
			spr_master = atlas_gameplay_interface.createSprite("controllertouch");
			spr_master.setSize(20, 25);
			spr_master.setPosition(fX - 76, fY - 30);
			return spr_master;
		}

		if(item.equals("Analog") && complement.equals("Back-Right")) {
			spr_master = atlas_gameplay_interface.createSprite("controllertouch");
			spr_master.setSize(20, 25);
			spr_master.setPosition(fX - 61, fY - 30);
			return spr_master;
		}

		if(item.equals("Analog") && complement.equals("Front-Right")) {
			spr_master = atlas_gameplay_interface.createSprite("controllertouch");
			spr_master.setSize(20, 25);
			spr_master.setPosition(fX - 61, fY - 46);			
			return spr_master;
		}

		if(item.equals("Analog") && complement.equals("Front-Left")) {
			spr_master = atlas_gameplay_interface.createSprite("controllertouch");
			spr_master.setSize(20, 25);
			spr_master.setPosition(fX - 76, fY - 46);
			return spr_master;
		}

		//

		if(item.equals("AreaCombate")) {
			spr_master = atlas_gameplay_interface.createSprite("btnAreaBatalha");
			spr_master.setSize(25, 15);
			spr_master.setPosition(fX - 55f, fY + 115);
			return spr_master;
		}
		if(item.equals("tabelaBatalha")) {
			spr_master = atlas_gameplay_interface.createSprite("tabelaBatalha");
			spr_master.setSize(100, 100);
			spr_master.setPosition(fX, fY);
			return spr_master;
		}
		if(item.equals("autoATK") && complement.equals("ON")) {
			spr_master = atlas_gameplay_interface.createSprite("btnAutoAttackOn");
			spr_master.setSize(8, 16);
			spr_master.setPosition(fX + 73, fY - 49);
			return spr_master;
		}
		if(item.equals("autoATK") && complement.equals("OFF")) {
			spr_master = atlas_gameplay_interface.createSprite("btnAutoAttackOff");
			spr_master.setSize(8, 16);
			spr_master.setPosition(fX + 73, fY - 49);
			return spr_master;
		}
		if(item.equals("AtkBtn")) {
			spr_master = atlas_gameplay_interface.createSprite("hotAtk");
			spr_master.setSize(8, 16);
			spr_master.setPosition(fX + 46, fY - 68.5f);
			return spr_master;
		}
		if(item.equals("ActionBtn")) {
			spr_master = atlas_gameplay_interface.createSprite("btnAcao");
			spr_master.setSize(8, 16);
			spr_master.setPosition(fX + 91, fY - 49f);
			return spr_master;
		}
		if(item.equals("textovershop1")) {
			spr_master = atlas_objectsMetro.createSprite("anibarshop1");
			spr_master.setSize(82.5f, 14);
			spr_master.setPosition(184, -8);
			return spr_master;
		}
		if(item.equals("textovershop2")) {
			spr_master = atlas_objectsMetro.createSprite("anibarshop2");
			spr_master.setSize(82.5f, 14);
			spr_master.setPosition(184, -8);
			return spr_master;
		}
		if(item.equals("textovershop3")) {
			spr_master = atlas_objectsMetro.createSprite("anibarshop3");
			spr_master.setSize(82.5f, 14);
			spr_master.setPosition(184, -8);
			return spr_master;
		}
		if(item.equals("tripleAttackbtn")) {
			spr_master = atlas_btnskills.createSprite("btntripleattack");
			spr_master.setSize(8, 16);
			spr_master.setPosition(fX + 55, fY - 68.5f);
			return spr_master;
		}
		if(item.equals("tripleAttackbtnMenu")) {
			spr_master = atlas_btnskills.createSprite("btntripleattack");
			spr_master.setSize(8, 16);
			spr_master.setPosition(fX - 55, fY + 22);
			return spr_master;
		}
		if(item.equals("SodaMachine")) {
			spr_master = atlas_shop.createSprite("refrishop");
			spr_master.setSize(90, 140);
			spr_master.setPosition(fX, fY - 20);
			return spr_master;
		}

		if(item.equals("avisoMissao")) {
			spr_master = atlas_gameplay_interface.createSprite("avisoMissao");
			spr_master.setSize(12, 12);
			spr_master.setPosition(64, -40);
			return spr_master;
		}

		if(item.equals("questBar")) {
			spr_master = atlas_gameplay_interface.createSprite("bar_text");
			spr_master.setSize(205,50);
			spr_master.setPosition(fX - 100, fY - 72);
			return spr_master;
		}

		if(item.equals("lootbox")) {
			spr_master = atlas_gameplay_interface.createSprite("baritem");
			spr_master.setSize(80,20);
			spr_master.setPosition(fX - 40, fY + 80);
			return spr_master;
		}

		if(item.equals("hotkey")) {
			spr_master = atlas_gameplay_interface.createSprite("hotkey");
			spr_master.setSize(10,15);
			spr_master.setPosition(fX + 90, fY - 20);
			return spr_master;
		}

		//Swordmans tabs
		if(item.equals("flyswordbtn")) {
			spr_master = atlas_btnskills.createSprite("btnflysword");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 55, fY - 68);
			return spr_master;
		}

		if(item.equals("ravenbladebtn")) {
			spr_master = atlas_btnskills.createSprite("btnravenblade");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 64, fY - 68);
			return spr_master;
		}

		if(item.equals("healthboostbtn")) {
			spr_master = atlas_btnskills.createSprite("btnhealthboost");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 73, fY - 68);
			return spr_master;
		}

		if(item.equals("protectbtn")) {
			spr_master = atlas_btnskills.createSprite("btnprotect");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 81.5f, fY - 68);
			return spr_master;
		}

		if(item.equals("ironshieldbtn")) {
			spr_master = atlas_btnskills.createSprite("btnironshield");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 90, fY - 68);
			return spr_master;
		}

		//Magician tabs
		if(item.equals("icecrystalbtn")) {
			spr_master = atlas_btnskills.createSprite("btnicecrystal");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 55, fY - 68);
			return spr_master;
		}

		if(item.equals("fireballbtn")) {
			spr_master = atlas_btnskills.createSprite("btnfireball");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 64, fY - 68);
			return spr_master;
		}

		if(item.equals("thundercloudbtn")) {
			spr_master = atlas_btnskills.createSprite("btnthundercloud");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 73, fY - 68);
			return spr_master;
		}

		if(item.equals("rockboundbtn")) {
			spr_master = atlas_btnskills.createSprite("btnrockbound");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 81.5f, fY - 68);
			return spr_master;
		}

		if(item.equals("soulclashbtn")) {
			spr_master = atlas_btnskills.createSprite("btnsoulclash");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 90, fY - 68);
			return spr_master;
		}

		//Thief tabs
		if(item.equals("invisibilitybtn")) {
			spr_master = atlas_btnskills.createSprite("btninvisibility");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 55, fY - 68);
			return spr_master;
		}

		if(item.equals("doublehitbtn")) {
			spr_master = atlas_btnskills.createSprite("btndoublehit");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 64, fY - 68);
			return spr_master;
		}

		if(item.equals("dashkickbtn")) {
			spr_master = atlas_btnskills.createSprite("btndashkick");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 73, fY - 68);
			return spr_master;
		}

		if(item.equals("poisonhitbtn")) {
			spr_master = atlas_btnskills.createSprite("btnpoisonhit");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 81.5f, fY - 68);
			return spr_master;
		}

		if(item.equals("stealbtn")) {
			spr_master = atlas_btnskills.createSprite("btnsteal");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 90, fY - 68);
			return spr_master;
		}

		//Gunner tabs
		if(item.equals("bulletrainbtn")) {
			spr_master = atlas_btnskills.createSprite("btnbulletrain");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 55, fY - 68);
			return spr_master;
		}

		if(item.equals("lockshotbtn")) {
			spr_master = atlas_btnskills.createSprite("btnlockshot");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 64, fY - 68);
			return spr_master;
		}

		if(item.equals("precisionbtn")) {
			spr_master = atlas_btnskills.createSprite("btnprecision");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 73, fY - 68);
			return spr_master;
		}

		if(item.equals("fastshotbtn")) {
			spr_master = atlas_btnskills.createSprite("btnfastshot");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 81.5f, fY - 68);
			return spr_master;
		}

		if(item.equals("minebtn")) {
			spr_master = atlas_btnskills.createSprite("btnmine");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 90, fY - 68);
			return spr_master;
		}

		//Priest tabs
		if(item.equals("healbtn")) {
			spr_master = atlas_btnskills.createSprite("btnheal");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 55, fY - 68);
			return spr_master;
		}

		if(item.equals("atkboostbtn")) {
			spr_master = atlas_btnskills.createSprite("btnatkboost");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 64, fY - 68);
			return spr_master;
		}

		if(item.equals("defboostbtn")) {
			spr_master = atlas_btnskills.createSprite("btndefboost");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 73, fY - 68);
			return spr_master;
		}

		if(item.equals("regenbtn")) {
			spr_master = atlas_btnskills.createSprite("btnregen");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 81.5f, fY - 68);
			return spr_master;
		}

		if(item.equals("holyprismbtn")) {
			spr_master = atlas_btnskills.createSprite("btnholyprism");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 90, fY - 68);
			return spr_master;
		}

		//Beater tabs
		if(item.equals("rageboundbtn")) {
			spr_master = atlas_btnskills.createSprite("btnragebound");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 55, fY - 68);
			return spr_master;
		}

		if(item.equals("hammercrashbtn")) {
			spr_master = atlas_btnskills.createSprite("btnhammercrash");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 64, fY - 68);
			return spr_master;
		}

		if(item.equals("impoundbtn")) {
			spr_master = atlas_btnskills.createSprite("btnimpound");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 73, fY - 68);
			return spr_master;
		}

		if(item.equals("overpowerbtn")) {
			spr_master = atlas_btnskills.createSprite("btnoverpower");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 81.5f, fY - 68);
			return spr_master;
		}

		if(item.equals("berserkbtn")) {
			spr_master = atlas_btnskills.createSprite("btnberserk");
			spr_master.setSize(8,15);
			spr_master.setPosition(fX + 90, fY - 68);
			return spr_master;
		}

		return spr_master;
	}

	public Sprite InterfaceMain(String tipo) {
		if(tipo.equals("IDbox"))
			spr_master = atlas_gameplay_interface.createSprite("bar_text");
		spr_master.setSize(60,30);
		spr_master.setPosition(fX + 20, fY + 35);
		return spr_master;
	}


	public Sprite ItensMenu(String item, String complement) {

		if(item.equals("Menu") && complement.equals("Main")) {
			spr_master = atlas_gameplay_interface.createSprite("menu");
			spr_master.setSize(15,120);
			spr_master.setPosition(fX + 85.5f, fY - 30.5f);
		}
		if(item.equals("Menu") && complement.equals("Status")) {
			spr_master = atlas_gameplay_interface.createSprite("menustatus");
			spr_master.setSize(130,130);
			spr_master.setPosition(fX - 60.5f, fY - 40.5f);
		}
		if(item.equals("Menu") && complement.equals("Itens")) {
			spr_master = atlas_gameplay_interface.createSprite("menuitens");
			spr_master.setSize(130,130);
			spr_master.setPosition(fX - 60.5f, fY - 40.5f);
		}
		if(item.equals("Menu") && complement.equals("Skills")) {
			spr_master = atlas_gameplay_interface.createSprite("menuskills");
			spr_master.setSize(130,130);
			spr_master.setPosition(fX - 60.5f, fY - 40.5f);
		}
		if(item.equals("Menu") && complement.equals("Social")) {
			spr_master = atlas_gameplay_interface.createSprite("menusocial");
			spr_master.setSize(130,130);
			spr_master.setPosition(fX - 60.5f, fY - 40.5f);
		}
		if(item.equals("Menu") && complement.equals("Config")) {
			spr_master = atlas_gameplay_interface.createSprite("menuopcoes");
			spr_master.setSize(130,130);
			spr_master.setPosition(fX - 60.5f, fY - 40.5f);
		}
		if(item.equals("Menu") && complement.equals("Batalha")) {
			spr_master = atlas_gameplay_interface.createSprite("menubatalha");
			spr_master.setSize(130,130);
			spr_master.setPosition(fX - 60.5f, fY - 40.5f);
		}

		return spr_master;
	}
}
