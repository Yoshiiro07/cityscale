package com.moonbolt.cityscale;
import java.util.ArrayList;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.moonbolt.cityscale.interfaces.DateTimeProvider;
import com.moonbolt.cityscale.models.Player;
import com.badlogic.gdx.Screen;

public class ManagerScreen implements Screen{
	private MainGame game;
	private GameControl gameControl;
	private int playernum;
	private DateTimeProvider dateTimeProvider;
	
	public ManagerScreen(MainGame game,DateTimeProvider dateTimeProvider){
		this.game = game;
		this.gameControl = new GameControl();
		ArrayList<Player> lstPlayers = new ArrayList<Player>();
		this.dateTimeProvider = dateTimeProvider;
	}
	
	public void screenSwitch(String tipo, String account, int playernum){
		
		if(tipo.equals("SplashScreen")){	
			SplashScreen splashScreen = new SplashScreen(game, this,playernum);
			game.setScreen(splashScreen);
		}
		
		if(tipo.equals("TitleScreen")){	
			TitleScreen titleScreen = new TitleScreen(game,this, this.playernum);
			game.setScreen(titleScreen);
		}
		
		if(tipo.equals("CharacterSelectScreen")){	
			CharacterSelect CharacterSelectScreen = new CharacterSelect(game,this,account, playernum);
			game.setScreen(CharacterSelectScreen);
		}
		
		if(tipo.equals("LoadingScreen")){	
			LoadingScreen loadingScreen = new LoadingScreen(game,gameControl,playernum);
			game.setScreen(loadingScreen);
		}
		
		if(tipo.equals("GameMap")) {
			GameMap gameMapScreen = new GameMap(game,this,gameControl,playernum,dateTimeProvider);
			game.setScreen(gameMapScreen);
		}
	}
	
	public void atualizaComponentes(MainGame maingameAlt,GameControl gameControlAlt, int playernumberAlt){
		this.game = maingameAlt;
		this.gameControl = gameControlAlt;
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
