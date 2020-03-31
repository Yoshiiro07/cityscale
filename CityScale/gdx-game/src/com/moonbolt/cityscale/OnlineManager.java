package com.moonbolt.cityscale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;


public class OnlineManager
{
	
	private int threahCount = 0;
	private boolean onlineCheck = false;
	private String chat1 = "";
	private String chat2 = "";
	private String chat3 = "";
	private boolean PartyON = false;
	private String[] onlineData;
	private String[] splitonlineData;
	private String auxOnline;
	private String retornoOnline = "";
	private String skillOnline = "";
	private int chatNumber = 0;
	private int cleanPlayersOnline = 0;
	private float posOnlineFX;
	private float posOnlineFY;
	private int posOnlineX;
	private int posOnlineY;
	private ArrayList<Player> lstOnlinePlayers;
	private Player Character_Data;
	private Player plOnline;
	private Monster mobOnline;
	private SaveFileManager saveManager;
	
	//Construtor//
	public OnlineManager(){
	onlineData = new String [255];
	splitonlineData = new String [255];
	saveManager = new SaveFileManager();
	}
	
	// Online Management//		
	public void AtualizaCharacterData(Player playerActive){
		this.Character_Data = playerActive;
	}
	

	public void InsereChat(String chatmsg) {
		lstChats.add(Character_Data.Name_A + ":" + chatmsg);
	}
	
	public void OperacaoOnline(String nomeOperacao, String subdado) {

		try {

			String retorno = "";

			if(nomeOperacao.equals("Upload")) {
				GerenciamentoOnline("Upload", subdado);
			}

			if(nomeOperacao.equals("Download")) {
				GerenciamentoOnline("Download", subdado);
			}

			if(nomeOperacao.equals("Sincronizar")) {
				onlineCheck = true;
				threahCount = 1;
				ThreadsSincronia();				
			}

			if(nomeOperacao.equals("Chat")) {
				GerenciamentoOnline("Chat", subdado);
			}

			if(nomeOperacao.equals("Atk")) {
				GerenciamentoOnline("Atk", subdado);
			}

			if(nomeOperacao.equals("Party")) {
				GerenciamentoOnline("Party", subdado);
			}

			if(nomeOperacao.equals("Desligar")) {
				onlineCheck = false;
				threahCount = 0;
			}
		}

		catch(Exception ex) {

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
				while(threahCount == 1) {
					GerenciamentoOnline("Sincronizar", "");            	
				}
			}
			catch(Exception ex) {}
			//} catch (InterruptedException e){}	
		}
	};	
	////////////////////////////////////////// FIM DAS THREADS ////////////////////////////////////////////////



	public void GerenciamentoOnline(String tipoRequisicao, String subdado) throws IOException {

		String linhaLida;
		String resposta;
		String skill;
		String[] atkData = new String [3];


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

				String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(account, "UTF-8");
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
				data += "&" + URLEncoder.encode("lpos", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(pos), "UTF-8");
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
		        retornoOnline = "retry";
		        while ((line = rd.readLine()) != null) {
		        	linhaLida = line;   
		        	//Resultado: - Logado -. <br>done

		        	if(linhaLida.contains("SYSTEMCHAT")) {
		        		chatNumber++;
		        		TrataChatOnline(linhaLida , chatNumber); 
		        	}

			        if (linhaLida.contains("SYSTEMPLAYERS")) {            	
		        		TrataPlayersOnline(linhaLida);     		
		            }	

			        if(linhaLida.contains("SYSTEMMOBS")) {
			        	TrataMobs(linhaLida);
			        }
	    		}	

		        wr.close();
		        rd.close();

		        TrataChatOnline("Sync", 0);

		        return;
			}


			if(tipoRequisicao.equals("Chat")){
				String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(Character_Data.Account, "UTF-8");
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

		        // Get the response
		        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        String line;
		        line = subdado;
		        retornoOnline = "retry";
		        while ((line = rd.readLine()) != null) {
		        	linhaLida = line;   
		        	//Resultado: - Logado -. <br>done
			        if (linhaLida.contains("Works")) {            	
		        		retornoOnline = "Works";       		
		            }		            
	    		}	        
		        wr.close();
		        rd.close();
			}

			if(tipoRequisicao.equals("Party")){
				String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(Character_Data.Account, "UTF-8");
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
		        String line;
		        line = subdado;
		        retornoOnline = "retry";
		        while ((line = rd.readLine()) != null) {
		        	linhaLida = line;   
		        	//Resultado: - Logado -. <br>done
			        if (linhaLida.contains("AtualizadoParty")) {            	
			        	PartyON = true;  
			        	Character_Data.Party_A = subdado;
		            }		            
	    		}	        
		        wr.close();
		        rd.close();
			}

			if(tipoRequisicao.equals("Atk")){

				atkData = subdado.split(":");

				String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(Character_Data.Account, "UTF-8");
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

		        // Get the response
		        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        String line;
		        line = subdado;
		        retornoOnline = "retry";
		        while ((line = rd.readLine()) != null) {
		        	linhaLida = line;   
		        	//Resultado: - Logado -. <br>done
			        if (linhaLida.contains("AtualizadoMOB")) {            	
		        		retornoOnline = "Works";       		
		            }		            
	    		}	        
		        wr.close();
		        rd.close();
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
		        retornoOnline = "retry";
		        while ((line = rd.readLine()) != null) {
		        	linhaLida = line;   
		        	//Resultado: - Logado -. <br>done
			        if (!linhaLida.contains("Inexistente")) {            	
			        	SaveManager.LoadDownloadData(linhaLida);       		
		            }		            
	    		}	        
		        wr.close();
		        rd.close();
			}

			if(tipoRequisicao.equals("Upload")){

				SaveManager.WriteDataCharacterActive();
				SaveData();
				String accountData = SaveManager.LoadDataText();

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

		        // Get the response
		        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        String line;
		        line = subdado;
		        retornoOnline = "retry";
		        while ((line = rd.readLine()) != null) {
		        	linhaLida = line;   
		        	//Resultado: - Logado -. <br>done
			        if (linhaLida.contains("Works")) {            	
		        		retornoOnline = "Works";       		
		            }		            
	    		}	        
		        wr.close();
		        rd.close();
			}

			return ;
		}

		catch(Exception ex) {
			return ;
		}
	}
	
	public void TrataMobs(String dadosMobs) {
		onlineData = dadosMobs.split(":");

		mobOnline = new Monster();

		//Mob ID
		auxOnline = onlineData[2];
		splitonlineData = auxOnline.split("=");	
		mobOnline.ID = splitonlineData[1];

		//Mob HP
		auxOnline = onlineData[3];
		splitonlineData = auxOnline.split("=");	
		mobOnline.HP = splitonlineData[1];

		//Mob MAP
		auxOnline = onlineData[4];
		splitonlineData = auxOnline.split("=");	
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

		auxOnline = onlineData[16];	
		splitonlineData = auxOnline.split("=");	
		plOnline.Account = splitonlineData[1];

		auxOnline = onlineData[1];	
		splitonlineData = auxOnline.split("=");	
		plOnline.Name_A = splitonlineData[1];

		auxOnline = onlineData[2];	
		splitonlineData = auxOnline.split("=");	
		plOnline.HP_A = splitonlineData[1];

		auxOnline = onlineData[3];	
		splitonlineData = auxOnline.split("=");	
		plOnline.MP_A = splitonlineData[1];

		auxOnline = onlineData[4];	
		splitonlineData = auxOnline.split("=");	
		plOnline.PX_A = splitonlineData[1];

		auxOnline = onlineData[5];	
		splitonlineData = auxOnline.split("=");	
		plOnline.PY_A = splitonlineData[1];

		auxOnline = onlineData[6];	
		splitonlineData = auxOnline.split("=");	
		plOnline.Map_A = splitonlineData[1];

		auxOnline = onlineData[7];	
		splitonlineData = auxOnline.split("=");	
		plOnline.Level_A = splitonlineData[1];

		auxOnline = onlineData[8];	
		splitonlineData = auxOnline.split("=");	
		plOnline.Set_A = splitonlineData[1];

		auxOnline = onlineData[9];	
		splitonlineData = auxOnline.split("=");	
		plOnline.Hair_A = splitonlineData[1];

		auxOnline = onlineData[10];	
		splitonlineData = auxOnline.split("=");	
		plOnline.Hat_A = splitonlineData[1];

		auxOnline = onlineData[11];	
		splitonlineData = auxOnline.split("=");	
		plOnline.Weapon_A = splitonlineData[1];

		auxOnline = onlineData[12];	
		splitonlineData = auxOnline.split("=");	
		plOnline.Battle_A = splitonlineData[1];

		auxOnline = onlineData[13];	
		splitonlineData = auxOnline.split("=");	
		plOnline.Side_A = splitonlineData[1];

		auxOnline = onlineData[14];	
		splitonlineData = auxOnline.split("=");	
		plOnline.Position_A = splitonlineData[1];	

		auxOnline = onlineData[17];	
		splitonlineData = auxOnline.split("=");	
		plOnline.Party_A = splitonlineData[1];	

		auxOnline = onlineData[18];
		splitonlineData = auxOnline.split("=");
		plOnline.Sex_A = splitonlineData[1];

		if(!plOnline.Name_A.equals(Character_Data.Name_A)) {

			findplayerlist = false;
			for(int i = 0; i < lstOnlinePlayers.size(); i++) {				
				if(lstOnlinePlayers.get(i).Account.equals(plOnline.Account)) {
					findplayerlist = true;						
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

			if(!findplayerlist) {
				lstOnlinePlayers.add(plOnline);
			}			
		}
	}

	public void TrataChatOnline(String dadosChat, int number) {
		if(dadosChat != "Sync") {
			onlineData = dadosChat.split(":");
			auxOnline = onlineData[1];
			//Nome do personagem
			splitonlineData = auxOnline.split("=");		
			text = splitonlineData[1];	
			//Mensagem 
			onlineData = dadosChat.split(":");
			auxOnline = onlineData[2];
			splitonlineData = auxOnline.split("=");		
			text = text + ": " + splitonlineData[1];	
			//Conclus�o
			auxOnline = text;

			if(number == 1) { chat1 = auxOnline; }
			if(number == 2) { chat2 = auxOnline; }
			if(number == 3) { chat3 = auxOnline; }

		}
		else {
			lstChats.clear();
			lstChats.add(chat1);
			lstChats.add(chat2);
			lstChats.add(chat3);
		}			
	
}
