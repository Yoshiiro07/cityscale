package com.moonbolt.cityscale;

public class InterfaceManager
{
	
	
	public InterfaceManager(){
		
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

	public Sprite InterfaceStreets305(String item, String complement) {

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
