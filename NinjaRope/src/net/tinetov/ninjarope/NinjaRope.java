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
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;


public class NinjaRope implements ApplicationListener {

	private FPSLogger fpsLogger;	
	private OrthographicCamera camera;

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private SpriteBatch batch;
	
	private Entity game;

	@Override
	public void create() {		
        this.fpsLogger = new FPSLogger();
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		this.camera = new OrthographicCamera(w / 50f, h / 50f);
		
		this.world = new World(new Vector2(0, -9.81f), true);
		this.debugRenderer = new Box2DDebugRenderer();
		
		this.batch = new SpriteBatch();
		
		this.game = new NinjaRopeGame(this.world, this.camera);
		
		Gdx.input.setInputProcessor((InputProcessor) this.game);
		
		this.game.init();
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void render() {		
		world.step(1/60f, 3, 8);
		
		this.game.render(this.batch);
		
		this.debugRenderer.render(this.world, this.camera.combined);
		
		fpsLogger.log();
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
