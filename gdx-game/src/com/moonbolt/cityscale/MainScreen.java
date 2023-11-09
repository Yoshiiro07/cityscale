package com.moonbolt.citymanager;

import java.io.UnsupportedEncodingException;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainScreen implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	private MainGame game;
	private ManagerScreen screen;
	private OrthographicCamera camera;
    private Viewport viewport;
    private GameControl gameControl;
    private Player accountPlayer;
    private boolean verificaConta = false;
    private String teste = "";
    
    private String state = "MainMenu";
    private String accountNumber = "";
    
    private Sprite spr_background;
	private Texture tex_background;
	
	private Sprite spr_mainmenu;
	private Texture tex_mainmenu;
	
	private Sprite spr_conta;
	private Texture tex_conta;
	
	private Sprite spr_barraStatus;
	private Texture tex_barraStatus;
	
	private Sprite spr_Personagem1;
	private Texture tex_Personagem1;
	
	private Sprite spr_Personagem2;
	private Texture tex_Personagem2;
	
	private Sprite spr_Personagem3;
	private Texture tex_Personagem3;
	
	private Sprite spr_personagembar;
	private Texture tex_personagembar;
	
	private Sprite spr_Confirmar;
	private Texture tex_Confirmar;
	
	private Sprite spr_Voltar;
	private Texture tex_Voltar;
	
	private Sprite spr_testdot;
	private Texture tex_testdot;
	
	private BitmapFont font_master;
	
	public MainScreen(MainGame game, ManagerScreen screen){
		this.screen = screen;
		this.game = game;
		
		camera = new OrthographicCamera();
		viewport = new StretchViewport(100,100,camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		Gdx.input.setInputProcessor(this);
		
		gameControl = new GameControl();
		accountPlayer = new Player();
		
		tex_background = new Texture(Gdx.files.internal("data/assets/title.png"));
		spr_background = new Sprite(tex_background);

		tex_mainmenu = new Texture(Gdx.files.internal("data/assets/RecuperarConta.png"));
		spr_mainmenu = new Sprite(tex_mainmenu);
		
		tex_conta = new Texture(Gdx.files.internal("data/assets/conta.png"));
		spr_conta = new Sprite(tex_conta);
		
		tex_barraStatus = new Texture(Gdx.files.internal("data/assets/item.png"));
		spr_barraStatus = new Sprite(tex_barraStatus);
			
		tex_personagembar = new Texture(Gdx.files.internal("data/assets/personagembar.png"));
		spr_personagembar = new Sprite(tex_personagembar);
		
		tex_Confirmar = new Texture(Gdx.files.internal("data/assets/confirmar.png"));
		spr_Confirmar = new Sprite(tex_Confirmar);
		
		tex_Voltar = new Texture(Gdx.files.internal("data/assets/voltar.png"));
		spr_Voltar = new Sprite(tex_Voltar);
		
		tex_testdot = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		spr_testdot = new Sprite(tex_testdot);
		
		//font
		font_master = new BitmapFont(Gdx.files.internal("data/font/impact.fnt"),Gdx.files.internal("data/font/impact.png"), false);
		font_master.setColor(Color.RED);
		font_master.getData().setScale(0.13f,0.08f);
		font_master.setUseIntegerPositions(false);	
			
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float p1)
	{
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
			
		game.batch.begin();
		
		spr_background.setPosition(0,0);
		spr_background.setSize(100,100);
		spr_background.draw(game.batch);
		
		
		if(state.equals("MainMenu")) {
			spr_mainmenu.setPosition(70, 13);
			spr_mainmenu.setSize(30,15);		
			spr_mainmenu.draw(game.batch);
		}
			
		if(state.equals("GetAccount")) {
			spr_conta.setPosition(30, 50);
			spr_conta.setSize(40,10);		
			spr_conta.draw(game.batch);
			
			font_master.getData().setScale(0.12f,0.12f);
			font_master.setUseIntegerPositions(false);	
			font_master.draw(game.batch, accountNumber,47, 56);  	
		}
		
		if(state.equals("EditAccount")) {
			spr_personagembar.setPosition(30, 20);
			spr_personagembar.setSize(40,70);		
			spr_personagembar.draw(game.batch);
			
			font_master.getData().setScale(0.12f,0.12f);
			font_master.setUseIntegerPositions(false);	
			font_master.draw(game.batch, accountNumber,45, 81);
			
		}
		
		spr_testdot.setPosition(50,57);
		spr_testdot.setSize(1,1);
		spr_testdot.draw(game.batch);
		
		spr_testdot.setPosition(67,44);
		spr_testdot.setSize(1,1);
		spr_testdot.draw(game.batch);
			
		game.batch.end();
	}
	
	@Override
	public boolean touchDown(int p1, int p2, int p3, int p4)
	{
		// TODO: Implement this method
		Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));

		if(state.equals("MainMenu")) {
			//Recuperar Conta
			if(coordsTouch.x >= 70 && coordsTouch.x <= 99 && coordsTouch.y >= 12 && coordsTouch.y <= 27){
				state = "GetAccount";
			}
		}
		
		if(state.equals("GetAccount")) {
			//Bot�o voltar
			if(coordsTouch.x >= 70 && coordsTouch.x <= 99 && coordsTouch.y >= 12 && coordsTouch.y <= 27){
				state = "MainMenu";
			}
			//�rea de inserir conta
			if(coordsTouch.x >= 46 && coordsTouch.x <= 58 && coordsTouch.y >= 51 && coordsTouch.y <= 58){
				Gdx.input.getTextInput(this,"Digite o ID","","");
			}		
			//Bot�o Buscar
			if(coordsTouch.x >= 60 && coordsTouch.x <= 67 && coordsTouch.y >= 50 && coordsTouch.y <= 58){
				RecuperaDataDownload();
			}
		}
		
		if(state.equals("EditAccount")) {
			//Botao voltar
			if(coordsTouch.x >= 70 && coordsTouch.x <= 99 && coordsTouch.y >= 12 && coordsTouch.y <= 27){
				state = "MainMenu";
			}
			//Extract
			if(coordsTouch.x >= 32 && coordsTouch.x <= 48 && coordsTouch.y >= 44 && coordsTouch.y <= 57){
				
			}		
			//Send
			if(coordsTouch.x >= 50 && coordsTouch.x <= 67 && coordsTouch.y >= 44 && coordsTouch.y <= 57){
				
			}
		}
			
		return false;
	}
	
	private void RecuperaDataDownload() {
		gameControl.Onlineoperation("Download",accountNumber);	
		verificaConta = gameControl.VerificaExisteConta();
		
		if(verificaConta) {
			accountPlayer = gameControl.RetornaPlayer();
			if(accountPlayer != null) {
				state = "EditAccount";
			}
			else {
				state = "GetAccount";
			}
		}
	}
	
	@Override
	public void input(String input) {
		accountNumber = input;			
	}
	
	
	@Override
	public void resize(int p1, int p2)
	{
		viewport.update(p1,p2);
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
	}

	@Override
	public void hide()
	{
	}

	@Override
	public void show()
	{
		
	}

	@Override
	public void resume()
	{
		// TODO: Implement this method
	}

	@Override
	public void pause()
	{
		// TODO: Implement this method
	}

	@Override
	public void dispose()
	{
		game.dispose();
	}
	
	@Override
	public void create()
	{
		// TODO: Implement this method
	}

	@Override
	public void render()
	{
		// TODO: Implement this method
	}
	
	@Override
	public boolean keyDown(int p1)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean keyUp(int p1)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean keyTyped(char p1)
	{
		// TODO: Implement this method
		return false;
	}
	
	@Override
	public boolean touchUp(int p1, int p2, int p3, int p4)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean touchDragged(int p1, int p2, int p3)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean mouseMoved(int p1, int p2)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean scrolled(int p1)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public void canceled() {
		// TODO Auto-generated method stub
		
	}

	
}
