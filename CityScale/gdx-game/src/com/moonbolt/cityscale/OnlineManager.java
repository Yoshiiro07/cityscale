package com.moonbolt.cityscale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class OnlineManager {
	
	//Primitives
	private int threadCount = 0;
	private int cleanPlayersOnline = 0;
	private int chatNumber = 0;
	private String textOnline;
	private String line;
	private String data;
	private String text;
	private String chat1;
	private String chat2;
	private String chat3;
	private String[] atkData = new String [3];
	private String[] onlineData = new String[50];
	private String[] splitonlineData = new String[3];
	private boolean findplayersList = false;
	private boolean partyON = false;
	private float posOnlineFX;
	private float posOnlineFY;
	private float posOnlineX;
	private float posOnlineY;
	
	
	//Objects
	private Player plOnline;
	private Monster mobOnline;
	private ArrayList<String> lstChats; 
	private ArrayList<Player> lstOnlinePlayers;
	private ArrayList<Monster> lstMonsters;
	private Player Character_Data;
	

	public OnlineManager() {
		lstOnlinePlayers = new ArrayList<Player>();
		plOnline = new Player();
	}
	
	public void UpdateCharacterData(Player chardata) {
		this.Character_Data = chardata;
	}
	
	public void OnlineOperations(String nomeOperacao, String subdado) {
		
		try {
			
			if(nomeOperacao.equals("Upload")) {
				OnlineControl("Upload", subdado);
			}
			
			if(nomeOperacao.equals("Download")) {
				OnlineControl("Download", subdado);
			}
			
			if(nomeOperacao.equals("Sincronizar")) {
				threadCount = 1;
				ThreadsSincronia();				
			}
			
			if(nomeOperacao.equals("Chat")) {
				OnlineControl("Chat", subdado);
			}
			
			if(nomeOperacao.equals("Atk")) {
				OnlineControl("Atk", subdado);
			}
			
			if(nomeOperacao.equals("Party")) {
				OnlineControl("Party", subdado);
			}
			
			if(nomeOperacao.equals("Desligar")) {
				threadCount = 0;
			}
		}
		
		catch(Exception ex) {
			
		}
	}
	
	public void OnlineControl(String tipoRequisicao, String subdado) throws IOException {
				
		try {
		
		if(tipoRequisicao.equals("Sincronizar")){
			
			cleanPlayersOnline++;
			
			if(cleanPlayersOnline > 100) {
				lstOnlinePlayers.clear();
				cleanPlayersOnline = 0;
			}
			
			posOnlineFX = Float.parseFloat(Character_Data.PX_A);
			posOnlineFY = Float.parseFloat(Character_Data.PY_A);
			
			posOnlineX = Math.round(posOnlineFX);
			posOnlineY = Math.round(posOnlineFY);
			
			String account = Character_Data.Account;
			
			data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(account, "UTF-8");
	        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Sincronizar", "UTF-8");
	        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode("citybase.mysql.uhserver.com", "UTF-8");
	        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode("citymaster", "UTF-8");
	        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode("City@2020", "UTF-8");
	        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode("citybase", "UTF-8");
	        data += "&" + URLEncoder.encode("lversion", "UTF-8") + "=" + URLEncoder.encode("a1", "UTF-8");
	        //UserData
	        data += "&" + URLEncoder.encode("lnome", "UTF-8") + "=" + URLEncoder.encode(Character_Data.Name_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lhp", "UTF-8") + "=" + URLEncoder.encode(Character_Data.HP_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lmp", "UTF-8") + "=" + URLEncoder.encode(Character_Data.MP_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lposX", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(posOnlineX), "UTF-8");
	        data += "&" + URLEncoder.encode("lposY", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(posOnlineY), "UTF-8");
	        data += "&" + URLEncoder.encode("lsex", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(Character_Data.Sex_A), "UTF-8");
	        data += "&" + URLEncoder.encode("lmap", "UTF-8") + "=" + URLEncoder.encode(Character_Data.Map_A, "UTF-8");
	        data += "&" + URLEncoder.encode("llevel", "UTF-8") + "=" + URLEncoder.encode(Character_Data.Level_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lsetchar", "UTF-8") + "=" + URLEncoder.encode(Character_Data.Set_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lhair", "UTF-8") + "=" + URLEncoder.encode(Character_Data.Hair_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lhat", "UTF-8") + "=" + URLEncoder.encode(Character_Data.Hat_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lweapon", "UTF-8") + "=" + URLEncoder.encode(Character_Data.Weapon_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lbattle", "UTF-8") + "=" + URLEncoder.encode(Character_Data.Battle_A, "UTF-8");
			data += "&" + URLEncoder.encode("lside", "UTF-8") + "=" + URLEncoder.encode(sidePlayer, "UTF-8");
			data += "&" + URLEncoder.encode("lpos", "UTF-8") + "=" + URLEncoder.encode(pos, "UTF-8");
			data += "&" + URLEncoder.encode("lskillOnline", "UTF-8") + "=" + URLEncoder.encode("none", "UTF-8");
			data += "&" + URLEncoder.encode("lparty", "UTF-8") + "=" + URLEncoder.encode(Character_Data.Party_A, "UTF-8");
			data += "&" + URLEncoder.encode("lchat", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8");
			
				
	        // Send data
	        //URL url = new URL("http://moonbolt.online/Conector/Online.php");
	        URL url = new URL("http://moonbolt.online/Conector/Online.php");
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data);
	        wr.flush();
	        
	        // Get the response
	        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line;
	        line = "";
	        chatNumber = 0;
	        textOnline = "retry";
	        while ((line = rd.readLine()) != null) {
	        	textOnline = line;   
	        	//Resultado: - Logado -. <br>done
	        	
	        	if(textOnline.contains("SYSTEMCHAT")) {
	        		chatNumber++;
	        		TrataChatOnline(textOnline , chatNumber); 
	        	}
	        	
		        if (textOnline.contains("SYSTEMPLAYERS")) {            	
	        		TrataPlayersOnline(textOnline);     		
	            }	
		        
		        if(textOnline.contains("SYSTEMMOBS")) {
		        	TrataMobs(textOnline);
		        }
    		}	
	        
	        wr.close();
	        rd.close();
    
	        TrataChatOnline("Sync", 0);
	        
	        return;
			}
		
		
		if(tipoRequisicao.equals("Chat")){
			data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(Character_Data.Account, "UTF-8");
	        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Chat", "UTF-8");
	        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode("citybase.mysql.uhserver.com", "UTF-8");
	        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode("citymaster", "UTF-8");
	        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode("City@2020", "UTF-8");
	        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode("citybase", "UTF-8");		 
	        data += "&" + URLEncoder.encode("lchat", "UTF-8") + "=" + URLEncoder.encode(subdado, "UTF-8");
	        data += "&" + URLEncoder.encode("lnome", "UTF-8") + "=" + URLEncoder.encode(Character_Data.Name_A, "UTF-8");
	        
	        // Send data
	        URL url = new URL("http://moonbolt.online/Conector/Online.php");
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data);
	        wr.flush();
	        wr.close();
	        
		}
		
		if(tipoRequisicao.equals("Party")){
			data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(Character_Data.Account, "UTF-8");
	        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Party", "UTF-8");
	        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode("citybase.mysql.uhserver.com", "UTF-8");
	        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode("citymaster", "UTF-8");
	        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode("City@2020", "UTF-8");
	        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode("citybase", "UTF-8");		 
	        data += "&" + URLEncoder.encode("lparty", "UTF-8") + "=" + URLEncoder.encode(subdado, "UTF-8");
	        data += "&" + URLEncoder.encode("lnome", "UTF-8") + "=" + URLEncoder.encode(Character_Data.Name_A, "UTF-8");
	        
	        // Send data
	        URL url = new URL("http://moonbolt.online/Conector/Online.php");
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data);
	        wr.flush();
	        
	        // Get the response
	        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        line = subdado;
	        textOnline = "retry";
	        while ((line = rd.readLine()) != null) {
	        	textOnline = line;   
	        	//Resultado: - Logado -. <br>done
		        if (textOnline.contains("AtualizadoParty")) {            	
		        	partyON = true;  
		        	Character_Data.Party_A = subdado;
	            }		            
    		}	        
	        wr.close();
	        rd.close();
		}
		
		if(tipoRequisicao.equals("Atk")){
			
			atkData = subdado.split(":");
			
			data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(Character_Data.Account, "UTF-8");
	        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Atk", "UTF-8");
	        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode("citybase.mysql.uhserver.com", "UTF-8");
	        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode("citymaster", "UTF-8");
	        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode("City@2020", "UTF-8");
	        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode("citybase", "UTF-8");		 
	        data += "&" + URLEncoder.encode("lmobID", "UTF-8") + "=" + URLEncoder.encode(atkData[0], "UTF-8");
	        data += "&" + URLEncoder.encode("ldmg", "UTF-8") + "=" + URLEncoder.encode(atkData[1], "UTF-8");
	        
	        // Send data
	        URL url = new URL("http://moonbolt.online/Conector/Online.php");
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data);
	        wr.flush();
	        wr.close();
	        
		}
		
		if(tipoRequisicao.equals("Download")){
			String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(subdado, "UTF-8");
	        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Download", "UTF-8");
	        data += "&" + URLEncoder.encode("laccount", "UTF-8") + "=" + URLEncoder.encode(subdado, "UTF-8");
	        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode("citybase.mysql.uhserver.com", "UTF-8");
	        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode("citymaster", "UTF-8");
	        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode("City@2020", "UTF-8");
	        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode("citybase", "UTF-8");		 
	        
	        // Send data
	        URL url = new URL("http://moonbolt.online/Conector/Online.php");
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data);
	        wr.flush();
	        
	        // Get the response
	        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line;
	        line = subdado;
	        textOnline = "retry";
	        while ((line = rd.readLine()) != null) {
	        	textOnline = line;   
	        	//Resultado: - Logado -. <br>done
		        if (!textOnline.contains("Inexistente")) {            	
		        	LoadDownloadData(textOnline);       		
	            }		            
    		}	        
	        wr.close();
	        rd.close();
		}
		
		if(tipoRequisicao.equals("Upload")){
			
			WriteDataCharacterActive();
			SaveData();
			String accountData = LoadDataText();
						
			String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(accountData, "UTF-8");
			data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Upload", "UTF-8");
	        data += "&" + URLEncoder.encode("laccount", "UTF-8") + "=" + URLEncoder.encode(subdado, "UTF-8");
	        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode("citybase.mysql.uhserver.com", "UTF-8");
	        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode("citymaster", "UTF-8");
	        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode("City@2020", "UTF-8");
	        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode("citybase", "UTF-8");		 
	        
	        // Send data
	        URL url = new URL("http://moonbolt.online/Conector/Online.php");
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data);
	        wr.flush();
	        wr.close();
		}
				
		return ;
		}
		
		catch(Exception ex) {
			return ;
		}
	}
	
	public ArrayList<Player> InfoPlayerOnline() {
		return lstOnlinePlayers;
	}
	
	
	
	public void TrataMobs(String dadosMobs) {
		onlineData = dadosMobs.split(":");
		
		mobOnline = new Monster();
		
		//Mob ID
		textOnline = onlineData[2];
		splitonlineData = textOnline.split("=");	
		mobOnline.ID = splitonlineData[1];
		
		//Mob HP
		textOnline = onlineData[3];
		splitonlineData = textOnline.split("=");	
		mobOnline.HP = splitonlineData[1];
		
		//Mob MAP
		textOnline = onlineData[4];
		splitonlineData = textOnline.split("=");	
		mobOnline.MAP = splitonlineData[1];
		
			
		for(int i = 0; i < lstMonsters.size(); i++) {				
			if(lstMonsters.get(i).ID.equals(mobOnline.ID) && lstMonsters.get(i).MAP.equals(mobOnline.MAP)) {
				lstMonsters.get(i).HP = mobOnline.HP; 
			}
		}
	}
	
	public void TrataPlayersOnline(String dadosPlayer) {
		onlineData = dadosPlayer.split(":");			
		
		plOnline = new Player();
		
		textOnline = onlineData[16];	
		splitonlineData = textOnline.split("=");	
		plOnline.Account = splitonlineData[1];
		
		textOnline = onlineData[1];	
		splitonlineData = textOnline.split("=");	
		plOnline.Name_A = splitonlineData[1];
		
		textOnline = onlineData[2];	
		splitonlineData = textOnline.split("=");	
		plOnline.HP_A = splitonlineData[1];
		
		textOnline = onlineData[3];	
		splitonlineData = textOnline.split("=");	
		plOnline.MP_A = splitonlineData[1];
		
		textOnline = onlineData[4];	
		splitonlineData = textOnline.split("=");	
		plOnline.PX_A = splitonlineData[1];
		
		textOnline = onlineData[5];	
		splitonlineData = textOnline.split("=");	
		plOnline.PY_A = splitonlineData[1];
		
		textOnline = onlineData[6];	
		splitonlineData = textOnline.split("=");	
		plOnline.Map_A = splitonlineData[1];
		
		textOnline = onlineData[7];	
		splitonlineData = textOnline.split("=");	
		plOnline.Level_A = splitonlineData[1];
		
		textOnline = onlineData[8];	
		splitonlineData = textOnline.split("=");	
		plOnline.Set_A = splitonlineData[1];
		
		textOnline = onlineData[9];	
		splitonlineData = textOnline.split("=");	
		plOnline.Hair_A = splitonlineData[1];
		
		textOnline = onlineData[10];	
		splitonlineData = textOnline.split("=");	
		plOnline.Hat_A = splitonlineData[1];
	
		textOnline = onlineData[11];	
		splitonlineData = textOnline.split("=");	
		plOnline.Weapon_A = splitonlineData[1];
		
		textOnline = onlineData[12];	
		splitonlineData = textOnline.split("=");	
		plOnline.Battle_A = splitonlineData[1];
		
		textOnline = onlineData[13];	
		splitonlineData = textOnline.split("=");	
		plOnline.Side_A = splitonlineData[1];
		
		textOnline = onlineData[14];	
		splitonlineData = textOnline.split("=");	
		plOnline.Position_A = splitonlineData[1];	
		
		textOnline = onlineData[17];	
		splitonlineData = textOnline.split("=");	
		plOnline.Party_A = splitonlineData[1];	
		
		textOnline = onlineData[18];
		splitonlineData = textOnline.split("=");
		plOnline.Sex_A = splitonlineData[1];
		
		if(!plOnline.Name_A.equals(Character_Data.Name_A)) {
			
			findplayersList = false;
			for(int i = 0; i < lstOnlinePlayers.size(); i++) {				
				if(lstOnlinePlayers.get(i).Account.equals(plOnline.Account)) {
					findplayersList = true;						
					lstOnlinePlayers.get(i).HP_A = plOnline.HP_A; 
					lstOnlinePlayers.get(i).MP_A = plOnline.MP_A;
					lstOnlinePlayers.get(i).PX_A = plOnline.PX_A;
					lstOnlinePlayers.get(i).PY_A = plOnline.PY_A;
					lstOnlinePlayers.get(i).Map_A = plOnline.Map_A;
					lstOnlinePlayers.get(i).Level_A = plOnline.Level_A;
					lstOnlinePlayers.get(i).Set_A = plOnline.Set_A;
					lstOnlinePlayers.get(i).Hair_A = plOnline.Hair_A;
					lstOnlinePlayers.get(i).Weapon_A = plOnline.Weapon_A;
					lstOnlinePlayers.get(i).Battle_A = plOnline.Battle_A;
					lstOnlinePlayers.get(i).Side_A = plOnline.Side_A;
					lstOnlinePlayers.get(i).Position_A = plOnline.Position_A;	
					lstOnlinePlayers.get(i).Sex_A = plOnline.Sex_A;
				}
			}
			
			if(!findplayersList) {
				lstOnlinePlayers.add(plOnline);
			}			
		}
	}
	
	public void TrataChatOnline(String dadosChat, int number) {
		if(dadosChat != "Sync") {
		onlineData = dadosChat.split(":");
		textOnline = onlineData[1];
		//Nome do personagem
		splitonlineData = textOnline.split("=");		
		text = splitonlineData[1];	
		//Mensagem 
		onlineData = dadosChat.split(":");
		textOnline = onlineData[2];
		splitonlineData = textOnline.split("=");		
		text = text + ": " + splitonlineData[1];	
		//Conclus�o
		textOnline = text;
		
		if(number == 1) { chat1 = textOnline; }
		if(number == 2) { chat2 = textOnline; }
		if(number == 3) { chat3 = textOnline; }	
		}
		else {
				lstChats.clear();
				lstChats.add(chat1);
				lstChats.add(chat2);
				lstChats.add(chat3);
		}			
	}
	
	///////////////////////////// Threads Sincronia //////////////////////////////
	private void ThreadsSincronia() {
		new Thread(t1).start();			
	}
	
	//////////////////////////////////////////INICIO DAS THREADS ////////////////////////////////////////////////
	
	//Threads para Sincronia
	private Runnable t1 = new Runnable() {
		public void run() {
			try{    
				while(threadCount == 1) {
					OnlineControl("Sincronizar", "");            	
			}
		}
		catch(Exception ex) {}
			//} catch (InterruptedException e){}	
			}
		};	
	////////////////////////////////////////// FIM DAS THREADS ////////////////////////////////////////////////
}
