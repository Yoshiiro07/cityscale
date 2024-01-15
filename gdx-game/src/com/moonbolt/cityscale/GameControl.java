package com.moonbolt.cityscale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class GameControl {
	
	//Server Credentials
    public String lservername = "cityserver.mysql.uhserver.com";
    public String lusername = "citymaster";
    public String lpassword = "City@key90";
    public String ldbname = "cityserver";
    
    //Variables
    private String warning = "";

	//[DATA CONTROL]//
	//[ONLINE MANAGER]//
	
	private GameObject player;
	private Json json;
	private FileHandle file;
	private Random randnumber;
	
	public GameControl() {
		json = new Json();
		randnumber = new Random();
		
	}
	
	//[DATA CONTROL]//
	
	private void CreateNewChar(String name, String sex, String hair, String color) {
		player = new GameObject();
		
		FileHandle file = Gdx.files.local("SaveData/svdt.json");		
		player = json.fromJson(GameObject.class, Base64Coder.decodeString(file.readString()));
		
		player.Name = name;
		player.Sex = sex;
		player.Level = 1;
		player.Exp = 0;
		player.Job = "Aprendiz";
		player.Map = "MetroStation";
		player.Hp = 100;
		player.Mp = 100;
		player.HpMax = 100;
		player.MpMax = 100;
		player.regenTime = 6000;
		player.regenTimeMax = 6000;
		player.PosX = -0.5f;
		player.PosY = -4;
		player.Walk = "no";
		player.Frame = 1;
		player.Money = 0;
		player.AtkTimer = 200;
		player.AtkTimerMax = 200;
		player.Casting = "no";
		player.Target = "none";
		player.Atk = 9;
		player.Def = 1;
		player.Evasion = 1;
		player.Side = "front";
		player.Set = "basic";
		player.Hair = hair;
		player.Color = color;
		player.Hat = "none";
		player.Weapon = "basicknife";
		player.Crystal1 = "none";
		player.Crystal2 = "none";
		player.Crystal3 = "none";
		player.Crystal4 = "none";
		player.Crystal5 = "none";
		player.StatusPoint = 0;
		player.Str = 1;
		player.Agi = 1;
		player.Vit = 1;
		player.Dex = 1;
		player.Wis = 1;
		player.Luk = 1;
		player.Res = 1;
		player.Stamina = 100;
		player.StaminaMax = 100;
		player.StaminaTimer = 1200;
		player.buffA = "none";
		player.buffB = "none";
		player.buffC = "none";
		player.BuffTimeA = 0;
		player.BuffTimeB = 0;
		player.BuffTimeC = 0;
		player.party = "none";
		player.playerInBattle = "no";
		player.playerInAttack = "no";
		player.playerInCast = "no";
		player.hotkey1 = "none";
		player.hotkey2 = "none";
		
		String itensList = "";
        for(int i = 0; i < 16; i++) {
            if(i == 0) { itensList = itensList + "[blue_crystal_intextra_3#4]-"; } 
            if(i == 1) {  if(sex.equals("M")) {itensList = itensList + "[basicset_m#1]-"; } else { itensList = itensList + "[basicset_f#1]-"; }}
            if(i == 2) {  itensList = itensList + "[basicknife#1]-"; } 
            if(i > 2) { itensList = itensList + "[NONE]-"; }          
        }
        player.Itens = itensList;
        
        //String itensList = "";
        //for(int i = 0; i < 16; i++) {
        //    if(i == 0) { itensList = itensList + "[hpcan#30]-"; } 
        //    if(i == 1) {  if(sex.equals("M")) {itensList = itensList + "[basicset_m#1]-"; } else { itensList = itensList + "[basicset_f#1]-"; }}
        //    if(i == 2) {  itensList = itensList + "[basicknife#1]-"; } 
        //    if(i > 2) { itensList = itensList + "[NONE]-"; }          
        //}
        //player.Itens = itensList;
        
		SaveData(player);		
	}

	public void SaveData(GameObject acPlayer) {
		file = Gdx.files.local("SaveData/save.json");
		file.writeString(Base64Coder.encodeString(json.prettyPrint(acPlayer)), false);
	}
	
	public void CheckData() {
		file = Gdx.files.local("SaveData/save.json");
		
		//Creating a new one
		if (!file.exists()) {
				try {
					GameObject player = new GameObject();
					int accNumber = randnumber.nextInt(999999);
					player.AccountID = String.valueOf(accNumber);
					player.Name = "none";
					file.writeString(Base64Coder.encodeString(json.prettyPrint(player)), false);
					
				} catch (Exception e) 
				{
					String test = e.getMessage();
				}
		}
		
		else 
		{
			FileHandle file = Gdx.files.local("SaveData/save.json");		
			player = json.fromJson(GameObject.class, Base64Coder.decodeString(file.readString()));
		}
	}
	
	public void LoadDownloadData(String hash) {
		FileHandle file = Gdx.files.local("SaveData/save.json");
		GameObject player = json.fromJson(GameObject.class,Base64Coder.decodeString(hash));			
		file.writeString(Base64Coder.encodeString(json.prettyPrint(player)),false);
	}
	
	public void DeleteData() {
		FileHandle file = Gdx.files.local("SaveData/save.json");
		file.delete();
	}
	
	public String GerenciamentoOnline(String tipoRequisicao, String subData) throws IOException {
		
		String linhaLida = "";
		
		try {
		
			if(tipoRequisicao.equals("Download")) {
				try {			
			        // Construct data
					//Inscricoes para Conexao
			        String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8");
			        data += "&" + URLEncoder.encode("lAccountID", "UTF-8") + "=" + URLEncoder.encode(subData, "UTF-8");
			        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Download", "UTF-8");
			        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
			        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
			        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
			        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
			   	    	        
			        // Send data
			        URL url = new URL("http://moonboltprojects.online/Conector/Online.php");
			        URLConnection conn = url.openConnection();
			        conn.setDoOutput(true);
			        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			        wr.write(data);
			        wr.flush();
			 
			        // Get the response
			        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			        String line;
			        while ((line = rd.readLine()) != null) {
			        	if(!line.contains("Inexistente")) {
			        		LoadDownloadData(line);
			        		warning = "Recuperado com sucesso";
			        	}
			        	else {
			        		warning = "Conta nao encontrada";
			        	}
			        }		        
			        wr.close();
			        rd.close();
			    } 
				
				catch (Exception e) { warning = "Operacao falhou"; return "retry";}
			}
				
			return "";
		}
		
		catch(Exception ex) {
			return "retry";
		}		
	}
	
	
}
