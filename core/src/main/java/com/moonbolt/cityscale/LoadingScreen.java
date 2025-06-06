package com.moonbolt.cityscale;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.moonbolt.cityscale.models.Player;
import com.badlogic.gdx.Input.TextInputListener;

public class LoadingScreen implements Screen, ApplicationListener, InputProcessor, TextInputListener {

	//Objects
	private MainGame game;
	private GameControl gameControl;
	private int playernumber = 0;
	private String account = "";
	
	//Loading Variables
	private boolean loading = true;
	private int loadtime = 100;
	private int loadanimationtime = 0;

	//Sprites
	private Sprite spr_loadingBlack;
	private Texture tex_loadingBlack;

	//Primitives
	private boolean changeScreen = false;

	//fonts
	private BitmapFont font_master;

	//Camera
	private OrthographicCamera camera;
    private Viewport viewport;

    //Controller
    private final IntSet downKeys = new IntSet(20);

	public LoadingScreen(MainGame gameAlt,GameControl gameControlAlt, int _playernumber) {
		this.game = gameAlt;
		this.gameControl = gameControlAlt;
		this.playernumber = _playernumber;

		//Camera and Inputs
		camera = new OrthographicCamera();
	    viewport = new StretchViewport(100,100,camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		Gdx.input.setInputProcessor(this);

		//font
		font_master = new BitmapFont(Gdx.files.internal("data/assets/font/impact.fnt"),Gdx.files.internal("data/assets/font/impact.png"), false);
		font_master.setColor(Color.WHITE);
		font_master.getData().setScale(0.11f,0.23f);
		font_master.setUseIntegerPositions(false);

		//Sprites
		tex_loadingBlack = new Texture(Gdx.files.internal("data/assets/etc/blackscreen.png"));
		spr_loadingBlack = new Sprite(tex_loadingBlack);
		spr_loadingBlack.setSize(100, 100);
	}


	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();

		//Change Screen
		if(loading) {
			spr_loadingBlack.setSize(200, 200);
			spr_loadingBlack.setPosition(0, 0);
			spr_loadingBlack.draw(game.batch);

			loadanimationtime++;
			if(loadanimationtime >= 0 && loadanimationtime <= 10) {
				font_master.draw(game.batch, "Car",80,10);
			}
			if(loadanimationtime >= 10 && loadanimationtime <= 20) {
				font_master.draw(game.batch, "Carreg",80,10);
			}
			if(loadanimationtime >= 20 && loadanimationtime <= 30) {
				font_master.draw(game.batch, "Carregando...",80,10);
			}
			if(loadanimationtime > 29) {
				loadanimationtime = 0;
			}

			loadtime--;
			if(loadtime < 0) {
				loading = false;
				changeScreen = true;
			}
		}

		if(changeScreen){
			game.loadingmanager.atualizaComponentes(game,gameControl,playernumber);
			game.Switch("GameMap",account,playernumber); 
		}

		game.batch.end();
	}

	@Override
	public boolean touchDown(int p1, int p2, int pointer, int button) {
		if(loading) { return false; }
		Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));
		return false;
	}
	@Override
	public void resize(int p1, int p2)
	{
		viewport.update(p1,p2);
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {return false;}

	private void onMultipleKeysDown (int mostRecentKeycode){}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {return false;}
	public boolean scrolled(int amount) {return false;}
	@Override
	public void create() {}
	@Override
	public void render() {}
	@Override
	public void show() {}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
	@Override
	public void hide() {}
	@Override
	public void dispose() {

		tex_loadingBlack.dispose();
	}
	@Override
	public void input(String text) {}
	@Override
	public void canceled() {}
	@Override
	public boolean keyDown(int keycode) {return false;}
	@Override
	public boolean keyUp(int keycode) { downKeys.remove(keycode); return false;}
	@Override
	public boolean keyTyped(char character) {return false;}


	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'touchCancelled'");
	}


	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'scrolled'");
	}
}
