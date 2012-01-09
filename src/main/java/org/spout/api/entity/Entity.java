/*
 * This file is part of SpoutAPI (http://www.spout.org/).
 *
 * SpoutAPI is licensed under the SpoutDev license version 1.
 *
 * SpoutAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * SpoutAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev license version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://getspout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package org.spout.api.entity;

import org.spout.api.collision.model.CollisionModel;
import org.spout.api.datatable.Datatable;
import org.spout.api.geo.World;
import org.spout.api.geo.cuboid.Chunk;
import org.spout.api.geo.cuboid.Region;
import org.spout.api.geo.discrete.Transform;
import org.spout.api.model.Model;
import org.spout.api.util.thread.DelayedWrite;
import org.spout.api.util.thread.LiveRead;
import org.spout.api.util.thread.SnapshotRead;

/**
 * Represents an entity, which may or may not be spawned into the world.
 */
public interface Entity extends Datatable {
	
	public int getId();
	
	// TODO - should these be main thread only ?
	public Controller getController() ;
	public void setController(Controller controller);
	
	/**
	 * Gets the transform for entity
	 * 
	 * @return
	 */
	@SnapshotRead
	public Transform getTransform();
	
	/**
	 * Gets the live/unstable position of the entity.  
	 * 
	 * Use of live reads may have a negative performance impact
	 * 
	 * @return
	 */
	@LiveRead
	public Transform getLiveTransform();
	
	/**
	 * Set transform
	 * 
	 * @param transform new Transform
	 */
	@DelayedWrite
	public void setTransform(Transform transform);
	
	// TODO - add thread timing annotations
	public void setModel(Model model);
	public Model getModel();
	
	public void setCollision(CollisionModel model);
	public CollisionModel getCollision();
	
	
	/**
	 * Returns true if this entity's controller is the provided controller
	 * 
	 * @param clazz 
	 * @return true if this entity's controller is the provided controller
	 */
	public boolean is(Class<? extends Controller> clazz);
	
	/**
	 * Returns true if this entity is spawned and being Simulated in the world
	 * 
	 * @return spawned
	 */
	public boolean isSpawned();

	/**
	 * Gets the chunk the entity resides in, or null if unspawned.
	 * 
	 * @return chunk
	 */
	@SnapshotRead
	public Chunk getChunk();
	
	/**
	 * Gets the region the entity is associated and managed with, or null if unspawned.
	 * 
	 * @return region
	 */
	@SnapshotRead
	public Region getRegion();
	
	/**
	 * Gets the world the entity is associated with, or null if unspawned.
	 * 
	 * @return world
	 */
	@SnapshotRead
	public World getWorld();
	
	/**
	 * Called just before a snapshot update.  
	 * 
	 * This is intended purely as a monitor based step.  
	 * 
	 * No updates should be made to the entity at this stage.
	 * 
	 * It can be used to send packets for network update.
	 */
	public void preSnapshot();
	
	/**
	 * Kills the entity.  This takes effect at the next snapshot.
	 * 
	 * If the entity's position is set before the next snapshot, the entity won't be removed.
	 * 
	 * @return true if the entity was alive
	 */
	@DelayedWrite
	@LiveRead
	public boolean kill();
}
