package com.moonbolt.cityscale;
import com.badlogic.gdx.Screen;

public class ManagerScreen implements Screen{
	private MainGame game;
	private GameControl gameControl;
	
	public ManagerScreen(MainGame game){
		this.game = game;
		this.gameControl = new GameControl();
	}
	
	public void screenSwitch(String tipo, String account){
		
		if(tipo.equals("SplashScreen")){	
			SplashScreen splashScreen = new SplashScreen(game,this);
			game.setScreen(splashScreen);
		}
	}
	
	public void atualizaComponentes(MainGame maingameAlt,GameControl gameControlAlt){
		this.game = maingameAlt;
		this.gameControl = gameControlAlt;
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