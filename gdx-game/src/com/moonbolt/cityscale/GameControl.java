package com.moonbolt.citymanager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class GameControl {

	//Variables
	private Json json;
	private FileHandle file;
	private Player playerInfo;
	private String request;
	private String line;
	private String returnFromServer;
	private String subdata;
	private String OnlineRequest;
	
	public GameControl() {	
		json = new Json();
		playerInfo = new Player();
	}
	
	//[A] DATA MANAGER
	public void CheckData() {
		file = Gdx.files.local("SaveData/save.json");		
		if(!file.exists()) { 
			//CreateNewData(); 
		}
	}
	
	public void LoadData() {
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		playerInfo = json.fromJson(Player.class,Base64Coder.decodeString(file.readString()));	
	}
	
	public void SaveData(Player playerInfo) {		
		file = Gdx.files.local("SaveData/SvDT.json");
		file.writeString(Base64Coder.encodeString(json.prettyPrint(playerInfo)),false);
	}
	
	//file.writeString(Base64Coder.encodeString(json.prettyPrint(player)),false);
	
	public void Onlineoperation(String request, String subData) {
		try {
			if(request.equals("Download")) {
				
				String data = URLEncoder.encode("laccount", "UTF-8") + "=" + URLEncoder.encode(subData, "UTF-8");
		        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Download", "UTF-8");
		        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode("citybase.mysql.uhserver.com", "UTF-8");
		        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode("citymaster", "UTF-8");
		        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode("City@2020", "UTF-8");
		        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode("citybase", "UTF-8");
		        data += "&" + URLEncoder.encode("lversion", "UTF-8") + "=" + URLEncoder.encode("a1", "UTF-8");
		        
		        // Send data
		        URL url = new URL("http://moonbolt.online/Conector/Online.php");
		        URLConnection conn = url.openConnection();
		        conn.setDoOutput(true);
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		        wr.write(data);
		        wr.flush();
		        
		        // Get the response
		        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        line = "";
		        returnFromServer = "";
		        while ((line = rd.readLine()) != null) {
		        	returnFromServer = line;   
		        	//Resultado:
		        	if(!returnFromServer.contains("Inexistente")) {
		        		LoadDownloadData(returnFromServer);
		        		OnlineRequest = "Concluido";
		        	}
				}
		        
		        wr.close();
		        rd.close();
		        return;
			}

			if(request.equals("Upload")) { 
					
				String accountData = LoadDataText();
				
				String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(accountData, "UTF-8");
			    data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Upload", "UTF-8");
			    data += "&" + URLEncoder.encode("laccount", "UTF-8") + "=" + URLEncoder.encode(playerInfo.accountID, "UTF-8");
			    data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode("citybase.mysql.uhserver.com", "UTF-8");
			    data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode("citymaster", "UTF-8");
			    data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode("City@2020", "UTF-8");
			    data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode("citybase", "UTF-8");
			    data += "&" + URLEncoder.encode("lversion", "UTF-8") + "=" + URLEncoder.encode("a1", "UTF-8");
			    
			    // Send data
			    URL url = new URL("http://moonbolt.online/Conector/Online.php");
			    URLConnection conn = url.openConnection();
			    conn.setDoOutput(true);
			    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			    wr.write(data);
			    wr.flush();
			    
			    // Get the response
			    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    line = "";
			    returnFromServer = "";
			    while ((line = rd.readLine()) != null) {
			    	returnFromServer = line;   
			    	//Resultado:
			    	if(returnFromServer.contains("Sucesso")) {
			    		OnlineRequest = "Concluido";
			    	}
				}
			    
			    wr.close();
			    rd.close();
			    return;
			}
		}
	
		catch(Exception ex) {
			OnlineRequest = "Operacao Falhou";
		}
	}
	
	public boolean VerificaExisteConta() {
		if(playerInfo == null) { return false; } else { return true; }
	}
	
	public Player RetornaPlayer() {
		return playerInfo;
	}

	public void LoadDownloadData(String hash) {
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		playerInfo = json.fromJson(Player.class,Base64Coder.decodeString(hash));			
		file.writeString(Base64Coder.encodeString(json.prettyPrint(playerInfo)),false);
	}
	
	public String LoadDataText() {
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		String dataStr = file.readString();
		return dataStr;
	}
}
