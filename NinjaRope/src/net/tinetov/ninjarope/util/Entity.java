package net.tinetov.ninjarope.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity {

	protected World world;
	
	public Entity(World world) {
		this.world = world;
	}
	
	public abstract void init();
	
	public abstract void render(SpriteBatch batch);
	
	public abstract void dispose();
}
