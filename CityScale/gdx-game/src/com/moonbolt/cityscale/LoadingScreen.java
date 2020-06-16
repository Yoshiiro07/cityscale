package com.moonbolt.cityscale;

import java.util.ArrayList;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;

import com.badlogic.gdx.Screen;

public class LoadingScreen implements Screen{
	ArrayList<Screen> screens;
	private MainGame game;
	private String config[];
	private String platform;
	private String network;
	
	public LoadingScreen(MainGame game){
		this.game = game;
		screens = new ArrayList<Screen>();
		platform = "Mobile";
		network = "off";
	}
	
	public void screenSwitch(String tipo){
		
		if(tipo.equals("SplashScreen")){	
			SplashScreen splashScreen = new SplashScreen(game, this);
			game.setScreen(splashScreen);
		}
		
		if(tipo.equals("TitleScreen")){	
			TitleScreen titleScreen = new TitleScreen(game, config, platform);
			game.setScreen(titleScreen);
		}
		
		//if(tipo.equals("CharacterSelect")) {
		//	CharacterSelect characterScreen = new CharacterSelect(game, config, control,platform);
		//	game.setScreen(characterScreen);
		//}
		
	}
	
	public void atualizaComponentes(MainGame maingameAlt, String[] configAlt, String platformAlt, String networkAlt){
		this.game = maingameAlt;
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
