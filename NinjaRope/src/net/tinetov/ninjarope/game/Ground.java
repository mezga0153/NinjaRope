package net.tinetov.ninjarope.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import net.tinetov.ninjarope.util.Entity;

public class Ground extends Entity {

	private Sprite background;
	
	public Ground(World world) {
		super(world);
	}

	@Override
	public void init() {
		BodyDef groundDef = new BodyDef();
		groundDef.type = BodyType.StaticBody;
		groundDef.position.set(0, 0);
		
		ChainShape shape = new ChainShape();
		shape.createChain(new Vector2[] {
				new Vector2(-1000f, 0),
				new Vector2(1000f, 0),
				new Vector2(1000f, 50f),
				new Vector2(-1000f, 50f),
				new Vector2(-1000f, 0)
		});

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.friction = 0.5f;
		fixtureDef.shape = shape;

		Body ground = this.world.createBody(groundDef);
		ground.createFixture(fixtureDef);
		
		ground.setUserData(this);

		fixtureDef.isSensor = true;
		ground.createFixture(fixtureDef);
	
		this.background = new Sprite(new Texture("ninja/background.png"));
		this.background.setSize(3000f, 48f);
		this.background.setOrigin(background.getWidth() / 2, background.getHeight() / 2);
		this.background.setPosition(-1500f, -2.8f);
		
		shape.dispose();
	}

	@Override
	public void render(SpriteBatch batch) {
		this.background.draw(batch);
	}

	@Override
	public void dispose() {
		
	}

}
