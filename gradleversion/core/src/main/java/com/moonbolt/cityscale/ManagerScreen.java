package com.moonbolt.cityscale;
import java.util.ArrayList;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;

import com.badlogic.gdx.Screen;

public class ManagerScreen implements Screen{
	private MainGame game;
	private GameControl gameControl;
	private boolean network;
	private int playernum;
	
	public ManagerScreen(MainGame game){
		this.game = game;
		this.gameControl = new GameControl(); 
	}
	
	public void screenSwitch(String tipo, boolean network, String account, int playernum){
		
		if(tipo.equals("SplashScreen")){	
			SplashScreen splashScreen = new SplashScreen(game, this,playernum);
			game.setScreen(splashScreen);
		}
		
		if(tipo.equals("TitleScreen")){	
			TitleScreenHTML titleScreen = new TitleScreenHTML(game,this, this.playernum);
			game.setScreen(titleScreen);
		}
		
		if(tipo.equals("CharacterSelectScreen")){	
			CharacterSelectHTML CharacterSelectScreen = new CharacterSelectHTML(game,this, network,account, playernum);
			game.setScreen(CharacterSelectScreen);
		}
		
		if(tipo.equals("LoadingScreen")){	
			LoadingScreenHTML loadingScreen = new LoadingScreenHTML(game,network,account,playernum);
			game.setScreen(loadingScreen);
		}
		
		if(tipo.equals("MetroStation")){
			MetroStationHTML MetroScreen = new MetroStationHTML(game,this, network,account, playernum);
			game.setScreen(MetroScreen);
		}
		
		if(tipo.equals("GameMap")) {
			GameMapHTML gameMapScreen = new GameMapHTML(game,this, network,account, playernum);
			game.setScreen(gameMapScreen);
		}
	}
	
	public void atualizaComponentes(MainGame maingameAlt,GameControl gameControlAlt, boolean network, int playernumberAlt){
		this.game = maingameAlt;
		this.gameControl = gameControlAlt;
		this.network = network;
		this.playernum = playernumberAlt;
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
