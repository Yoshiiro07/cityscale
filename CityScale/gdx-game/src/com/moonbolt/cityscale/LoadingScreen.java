package com.moonbolt.cityscale;

import java.util.ArrayList;

import com.badlogic.gdx.Screen;

public class LoadingScreen implements Screen{
	ArrayList<Screen> screens;
	private MainGame game;
	private String config[];
	private GameControl control;
	
	public LoadingScreen(MainGame game){
		this.game = game;
		screens = new ArrayList<Screen>();
		control = new GameControl();
	}
	
	public void screenSwitch(String tipo){
		
		if(tipo.equals("SplashScreen")){	
			SplashScreen splashScreen = new SplashScreen(game, this);
			game.setScreen(splashScreen);
		}
		
		if(tipo.equals("TitleScreen")){	
			TitleScreen titleScreen = new TitleScreen(game, config, control);
			game.setScreen(titleScreen);
		}
	}
	
	public void atualizaComponentes(MainGame maingameAlt, String[] configAlt, GameControl controlAlt){
		this.game = maingameAlt;
		this.config = configAlt;
		this.control = controlAlt;
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
