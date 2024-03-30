package com.moonbolt.cityscale;
import java.util.ArrayList;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;

import com.badlogic.gdx.Screen;

public class ManagerScreen implements Screen{
	private MainGame game;
	private GameControl gameControl;
	private boolean network;
	public String lservername = "cityserver.mysql.uhserver.com";
	public String lusername = "citymaster";
	public String lpassword = "City@key90";
	public String ldbname = "cityserver";
	
	public ManagerScreen(MainGame game){
		this.game = game;
		this.gameControl = new GameControl(); 
	}
	
	public void screenSwitch(String tipo, boolean network){
		
		if(tipo.equals("SplashScreen")){	
			SplashScreen splashScreen = new SplashScreen(game, this);
			game.setScreen(splashScreen);
		}
		
		if(tipo.equals("TitleScreen")){	
			TitleScreen titleScreen = new TitleScreen(game,this);
			game.setScreen(titleScreen);
		}
		
		if(tipo.equals("LoadingScreen")){	
			LoadingScreen loadingScreen = new LoadingScreen(game,network);
			game.setScreen(loadingScreen);
		}
		
		if(tipo.equals("GameMap")) {
			GameMap gameMapScreen = new GameMap(game,this, network);
			game.setScreen(gameMapScreen);
		}
	}
	
	public void atualizaComponentes(MainGame maingameAlt,GameControl gameControlAlt, boolean network){
		this.game = maingameAlt;
		this.gameControl = gameControlAlt;
		this.network = network;
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
