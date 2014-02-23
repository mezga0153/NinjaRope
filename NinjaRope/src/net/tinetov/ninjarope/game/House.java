package net.tinetov.ninjarope.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import net.tinetov.ninjarope.util.Attachable;
import net.tinetov.ninjarope.util.Entity;

public class House extends Entity implements Attachable {

	private float width, height, x;
	private Body house;
	
	public House(World world, float width, float height, float x) {
		super(world);
		
		this.width = width;
		this.height = height;
		this.x = x;
		
	}

	@Override
	public void init() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(this.x, 0);
		
		PolygonShape shape = new PolygonShape();
		shape.set(new Vector2[] {
				new Vector2(0, 0),
				new Vector2(0, this.height),
				new Vector2(this.width/2, this.height + 1.5f),
				new Vector2(this.width, this.height),
				new Vector2(this.width, 0)
		});
		//shape.setAsBox(this.width / 2, this.height / 2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.isSensor = true;

		this.house = this.world.createBody(bodyDef);
		this.house.createFixture(fixtureDef);
		
		this.house.setUserData(this);
		
		shape.dispose();
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Body getBody() {
		return house;
	}

}
