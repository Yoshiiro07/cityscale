package com.moonbolt.cityscale;
import java.util.ArrayList;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;

import com.badlogic.gdx.Screen;

public class ManagerScreen implements Screen{
	private MainGame game;
	private GameControl gameControl;
	private String config[];
	private String platform;
	private String network;
	
	public ManagerScreen(MainGame game){
		this.game = game;
		this.gameControl = new GameControl(); 
		platform = "Mobile";
		network = "on";
	}
	
	public void screenSwitch(String tipo){
		
		if(tipo.equals("SplashScreen")){	
			SplashScreen splashScreen = new SplashScreen(game, this);
			game.setScreen(splashScreen);
		}
		
		if(tipo.equals("TitleScreen")){	
			TitleScreen titleScreen = new TitleScreen(game,gameControl, config, platform);
			game.setScreen(titleScreen);
		}
		
		if(tipo.equals("CharacterSelect")) {
			CharacterSelect characterScreen = new CharacterSelect(game,gameControl, config, platform);
			game.setScreen(characterScreen);
		}
		
		if(tipo.equals("MetroStation")) {
			MetroStation metroScreen = new MetroStation(game,gameControl, config, platform,network);
			game.setScreen(metroScreen);
		}
		
		if(tipo.equals("Streets305")) {
			Streets305 streets305Screen = new Streets305(game,gameControl, config, platform,network);
			game.setScreen(streets305Screen);
		}
		
		if(tipo.equals("Sewers")) {
			Sewers sewersScreen = new Sewers(game,gameControl, config, platform,network);
			game.setScreen(sewersScreen);
		}	
		
		if(tipo.equals("SewersBoss")) {
			SewersBoss sewersBossScreen = new SewersBoss(game, gameControl, config, platform, network);
			game.setScreen(sewersBossScreen);
		}
		
		if(tipo.equals("Streets750")) {
			Streets750 streets750Screen = new Streets750(game,gameControl, config, platform,network);
			game.setScreen(streets750Screen);
		}
		
		if(tipo.equals("Watercave")) {
			Watercave watercaveScreen = new Watercave(game,gameControl, config, platform,network);
			game.setScreen(watercaveScreen);
		}
		
		if(tipo.equals("WatercaveBoss")) {
			WatercaveBoss watercaveBossScreen = new WatercaveBoss(game,gameControl, config, platform,network);
			game.setScreen(watercaveBossScreen);
		}
		
		if(tipo.equals("Streets920")) {
			Streets920 streets920Screen = new Streets920(game,gameControl, config, platform,network);
			game.setScreen(streets920Screen);
		}
		
		if(tipo.equals("Mines")) {
			Mines MinesScreen = new Mines(game,gameControl, config, platform,network);
			game.setScreen(MinesScreen);
		}
	}
	
	public void atualizaComponentes(MainGame maingameAlt,GameControl gameControl, String[] configAlt, String platformAlt, String networkAlt){
		this.game = maingameAlt;
		this.gameControl = gameControl;
		this.config = configAlt;
		this.platform = platformAlt;
		this.network = networkAlt;
	}

	@Override
	public void show(){}

	@Override
	public void render(float p1){}

	@Override
	public void resize(int p1, int p2){}

	@Override
	public void pause(){}

	@Override
	public void resume(){}

	@Override
	public void hide(){}

	@Override
	public void dispose(){}
}
