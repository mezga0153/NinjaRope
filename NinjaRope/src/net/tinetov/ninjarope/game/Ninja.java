package net.tinetov.ninjarope.game;

import net.tinetov.ninjarope.util.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;

public class Ninja extends Entity {

	private Body ninja;
	
	private Vector2 movement = new Vector2();

	private Texture texture;
	private Sprite sprite;
		
	private Body pulley_weight;
	private PulleyJoint pulley;
	
	private Vector2 attach_point;
	
	public Ninja(World world) {
		super(world);
	}

	@Override
	public void init() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(0, 5f);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.5f, 1f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.1f;
		fixtureDef.density = 0.5f;
		fixtureDef.shape = shape;

		this.ninja = this.world.createBody(bodyDef);
		this.ninja.createFixture(fixtureDef);
		
		texture = new Texture(Gdx.files.internal("ninja/ninja.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		TextureRegion region = new TextureRegion(texture, 7, 11, 17, 41);
		
		this.sprite = new Sprite(region);
		this.sprite.setSize(1f, 2f);
		this.sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		
		shape.dispose();
	}
	
	public void move(Vector2 force) {
		this.movement = force;
	}

	public Vector2 position() {
		return this.ninja.getPosition();
	}
	
	public void attachTo(Vector3 attach_point) {
		
		this.attach_point = new Vector2(attach_point.x, attach_point.y);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(attach_point.x + 0.5f, attach_point.y);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.1f, 0.1f);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 2f;
		
		
		//fixtureDef.filter.groupIndex = -1;
		fixtureDef.isSensor = true;
		
		this.pulley_weight = this.world.createBody(bodyDef);
		this.pulley_weight.createFixture(fixtureDef);
				
		//this.pulley_weight //setLinearVelocity(0, -5f);
		
		PulleyJointDef pulleyDef;

		pulleyDef = new PulleyJointDef();
		pulleyDef.collideConnected = false;
		
		pulleyDef.initialize(
				this.ninja,
				this.pulley_weight,
				new Vector2(attach_point.x, attach_point.y),
				new Vector2(attach_point.x + 0.5f, attach_point.y),
				new Vector2(ninja.getPosition().x + 0.5f, ninja.getPosition().y + 0.2f),
				pulley_weight.getPosition(),
				0.9f
		);
		
		this.pulley = (PulleyJoint) world.createJoint(pulleyDef);
		
		//this.pulley.applyLinearImpulse(new Vector2(0, 1f), new Vector2(0, 0), true);
	}
	
	public void detach() {
		if (this.pulley != null) {
			this.world.destroyJoint(this.pulley);
			this.pulley = null;
			this.world.destroyBody(this.pulley_weight);
			this.pulley_weight = null;
		}
	}
	
	@Override
	public void render(SpriteBatch batch) {
	
		this.sprite.setPosition(this.ninja.getPosition().x - this.sprite.getWidth() / 2, this.ninja.getPosition().y - this.sprite.getHeight() / 2);
		this.sprite.setRotation(this.ninja.getAngle() * MathUtils.radiansToDegrees);
		
		this.sprite.draw(batch);
		
		if (this.pulley != null) {
			this.pulley_weight.applyForceToCenter(new Vector2(0, -25f), true);
			
			if (this.pulley_weight.getLinearVelocity().y > 0) {
				this.detach();
			}
			
			//System.out.println(this.ninja.getPosition().dst(this.attach_point));
			System.out.println(this.ninja.getAngularVelocity());
			if (this.ninja.getPosition().dst(this.attach_point) < 1f) {
				this.detach();
			}
		}

		this.ninja.applyForceToCenter(movement, true);
	}

	@Override
	public void dispose() {
		
	}
	
}
