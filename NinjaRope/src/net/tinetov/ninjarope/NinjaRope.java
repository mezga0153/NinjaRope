package net.tinetov.ninjarope;

import net.tinetov.ninjarope.game.NinjaRopeGame;
import net.tinetov.ninjarope.util.Entity;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;


public class NinjaRope implements ApplicationListener {

	private FPSLogger fpsLogger;	
	private OrthographicCamera camera;

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private SpriteBatch batch;
	
	private Entity game;
	
	private int state = 0;
	
	private final int STATE_NOT_READY = 0;
	private final int STATE_READY = 1;

	@Override
	public void create() {		
        this.fpsLogger = new FPSLogger();
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		this.camera = new OrthographicCamera(w / 50f, h / 50f);
		
		this.debugRenderer = new Box2DDebugRenderer();
		
		this.start();
	}
	
	public void start() {
		
		this.world = new World(new Vector2(0, -9.81f), true);

		this.batch = new SpriteBatch();

		this.game = new NinjaRopeGame(this.world, this.camera, this);
		
		Gdx.input.setInputProcessor((InputProcessor) this.game);

		this.game.init();
		
		this.state = this.STATE_READY;
	}

	@Override
	public void dispose() {
		this.state = this.STATE_NOT_READY;
		this.game.dispose();
		this.batch.dispose();
		this.world.dispose();
	}
	
	public void restart() {
		this.dispose();
		this.start();
	}

	@Override
	public void render() {
		if (this.state == this.STATE_READY) {
			this.game.render(this.batch);
			
			this.debugRenderer.render(this.world, this.camera.combined);			
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
