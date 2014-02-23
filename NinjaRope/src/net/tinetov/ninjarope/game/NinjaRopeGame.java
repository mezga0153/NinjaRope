package net.tinetov.ninjarope.game;

import net.tinetov.ninjarope.util.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;

public class NinjaRopeGame extends Entity implements InputProcessor {
	
	private Ninja ninja;
	private Entity ground;
	private House[] houses;
	
	private OrthographicCamera camera;

	public NinjaRopeGame(World world, OrthographicCamera camera) {
		super(world);

		this.camera = camera;
		
		this.ninja = new Ninja(this.world);
		this.ground = new Ground(this.world);
		
		int x = 5;
		int num_houses = 10;
		this.houses = new House[num_houses];
		for (int i=0; i < num_houses; i++) {
			float width = 6f + (float)Math.random() * 8f;
			float height = 5f + (float)Math.random() * 20f;
			
			this.houses[i] = new House(this.world, width, height, x);

			System.out.println(width + " " + height + " " + x);
			
			x += width;
			x += Math.random() * 20;
		}
		
	}

	@Override
	public void init() {
		this.ninja.init();
		this.ground.init();
		
		for(House house : this.houses) {
			house.init();
		}
		
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("ninja/Record_0003.wav"));
		sound.play(0.05f);
	}

	@Override
	public void render(SpriteBatch batch) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		this.camera.position.set(this.ninja.position().x, Math.max(this.ninja.position().y, 3f), 0);
		
		/*
		if (this.ninja.position().y > 5f) {
			this.camera.zoom = this.ninja.position().y / 5f;			
		} else {
			this.camera.zoom = 1;
		}
		*/
		this.camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		this.ninja.render(batch);
		this.ground.render(batch);
		for(House house : this.houses) {
			house.render(batch);
		}
		batch.end();
		
	}

	@Override
	public void dispose() {
		this.ninja.dispose();
		this.ground.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		Vector2 movement = new Vector2();
		float speed = 50f;
		switch(keycode) {
			case Keys.W :
				movement.y = speed;
			break;
			case Keys.S :
				movement.y = -speed;
			break;
			case Keys.A :
				movement.x = -speed;
			break;
			case Keys.D :
				movement.x = speed;
			break;
		}
		
		this.ninja.move(movement);
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		Vector2 movement = new Vector2();
		switch(keycode) {
			case Keys.W :
			case Keys.S :
				movement.y = 0;
			break;
			case Keys.A :
			case Keys.D :
				movement.x = 0;
			break;
		}
		
		this.ninja.move(movement);
		
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	
	Vector3 touch_location = new Vector3();
	
	private QueryCallback queryCallback = new QueryCallback() {

		@Override
		public boolean reportFixture(Fixture fixture) {
			if (!fixture.testPoint(touch_location.x, touch_location.y)) {
				return true;
			}
			
			Body clicked = fixture.getBody();
			
			if (clicked.getUserData() != null && clicked.getUserData() instanceof net.tinetov.ninjarope.util.Attachable) {
				ninja.attachTo(touch_location);
				
				return false;
			}
			
			return true;
		}
		
	};

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (screenX < 500) {
			//this.ninja.detach();
		}
		this.ninja.detach();
		
		this.camera.unproject(touch_location.set(screenX, screenY, 0));
		
		this.world.QueryAABB(queryCallback, touch_location.x, touch_location.y, touch_location.x, touch_location.y);
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		
		return false;
	}
	
}
