package com.moonbolt.cityscale;

import java.util.ArrayList;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;

import com.badlogic.gdx.Screen;

public class LoadingScreen implements Screen{
	ArrayList<Screen> screens;
	private MainGame game;
	private GameControl gameControl;
	private String config[];
	private String platform;
	private String network;
	
	public LoadingScreen(MainGame game){
		this.game = game;
		this.gameControl = new GameControl();
		screens = new ArrayList<Screen>();
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
			Sewers streets305Screen = new Sewers(game,gameControl, config, platform,network);
			game.setScreen(streets305Screen);
		}	
		
		if(tipo.equals("Streets750")) {
			Streets750 streets750Screen = new Streets750(game,gameControl, config, platform,network);
			game.setScreen(streets750Screen);
		}
		
		if(tipo.equals("Watercave")) {
			Watercave watercaveScreen = new Watercave(game,gameControl, config, platform,network);
			game.setScreen(watercaveScreen);
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
	public void show()
	{
		// TODO: Implement this method
	}

	@Override
	public void render(float p1)
	{
		// TODO: Implement this method
	}

	@Override
	public void resize(int p1, int p2)
	{
		// TODO: Implement this method
	}

	@Override
	public void pause()
	{
		// TODO: Implement this method
	}

	@Override
	public void resume()
	{
		// TODO: Implement this method
	}

	@Override
	public void hide()
	{
	}

	@Override
	public void dispose()
	{
		// TODO: Implement this method
	}
}
