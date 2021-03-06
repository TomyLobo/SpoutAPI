/*
 * This file is part of SpoutAPI.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
 * SpoutAPI is licensed under the SpoutDev License Version 1.
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
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package org.spout.api.geo;

import org.spout.api.Source;
import org.spout.api.entity.component.controller.BlockController;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.material.BlockMaterial;
import org.spout.api.math.Vector3;
import org.spout.api.util.thread.DelayedWrite;
import org.spout.api.util.thread.LiveWrite;
import org.spout.api.util.thread.Threadsafe;

public interface AreaBlockAccess extends AreaBlockSource {

	/**
	 * Sets the data for the block at (x, y, z) to the given data.
	 *
	 * @param x coordinate of the block
	 * @param y coordinate of the block
	 * @param z coordinate of the block
	 * @param data to set to
	 * @param source of the change
	 * @throws NullPointerException
	 */
	@LiveWrite
	public boolean setBlockData(int x, int y, int z, short data, Source source);

	/**
	 * Sets the material and data for the block at (x, y, z) to the given material and data.
	 *
	 * @param x coordinate of the block
	 * @param y coordinate of the block
	 * @param z coordinate of the block
	 * @param data value to set to
	 * @param material to set to
	 * @param source of the change
	 * @throws NullPointerException
	 */
	@LiveWrite
	public boolean setBlockMaterial(int x, int y, int z, BlockMaterial material, short data, Source source);
	
	/**
	 * Sets the block light level for the block at (x, y, z) to the given light level
	 *
	 * @param x coordinate of the block
	 * @param y coordinate of the block
	 * @param z coordinate of the block
	 * @param light level to set to
	 * @param source of the change
	 * @throws NullPointerException
	 */
	@LiveWrite
	public boolean setBlockLight(int x, int y, int z, byte light, Source source);
	
	/**
	 * Sets the block sky light level for the block at (x, y, z) to the given light level
	 *
	 * @param x coordinate of the block
	 * @param y coordinate of the block
	 * @param z coordinate of the block
	 * @param light level to set to
	 * @param source of the change
	 * @throws NullPointerException
	 */
	@LiveWrite
	public boolean setBlockSkyLight(int x, int y, int z, byte light, Source source);

	/**
	 * Sets the block controller for the block at (x, y, z) to the given controller
	 *
	 * @param x,y,z position of the controller
	 * @param controller to set to, null to remove the controller
	 * @throws NullPointerException
	 */
	@LiveWrite
	public void setBlockController(int x, int y, int z, BlockController controller);

	/**
	 * Sets the data of the block at (x, y, z) if the expected state matches
	 *
	 * @param x coordinate of the block
	 * @param y coordinate of the block
	 * @param z coordinate of the block
	 * @param expect is the state of the block it expects
	 * @param data to set to if it matches
	 * @throws NullPointerException
	 */
	@LiveWrite
	public boolean compareAndSetData(int x, int y, int z, int expect, short data);
	
	/**
	 * Sets the given bits in the data for the block at (x, y, z)<br>
	 * <br>
	 * newData = oldData | (bits)
	 * 
	 * @param x coordinate of the block
	 * @param y coordinate of the block
	 * @param z coordinate of the block
	 * @param bits the bits to set
	 * @return the old data for the block
	 */
	@LiveWrite
	public short setBlockDataBits(int x, int y, int z, short bits);
	
	/**
	 * Clears the given bits in the data for the block at (x, y, z)<br>
	 * <br>
	 * newData = oldData & (~bits)
	 * 
	 * @param x coordinate of the block
	 * @param y coordinate of the block
	 * @param z coordinate of the block
	 * @param bits the bits to clear
	 * @return the old data for the block
	 */
	@LiveWrite
	public short clearBlockDataBits(int x, int y, int z, short bits);
	
	/**
	 * Gets the data field from the block at (x, y, z)<br>
	 * <br>
	 * field = (data & bits) >> (shift)<br>
	 * <br>
	 * The shift value used shifts the least significant non-zero bit of bits to the LSB position
	 * 
	 * @param x coordinate of the block
	 * @param y coordinate of the block
	 * @param z coordinate of the block
	 * @param bits the bits of the field
	 * @return the field value
	 */
	@Threadsafe
	public int getBlockDataField(int x, int y, int z, int bits);
	
	/**
	 * Sets the data field from the block at (x, y, z).  This is the reverse operation to the getBlockDataField method.<br>
	 * <br>
	 * newData = ((value << shift) & bits) | (oldData & (~bits))<br>
	 * <br>
	 * The shift value used shifts the least significant non-zero bit of bits to the LSB position
	 * 
	 * @param x coordinate of the block
	 * @param y coordinate of the block
	 * @param z coordinate of the block
	 * @param bits the bits of the field
	 * @param value the new value of the field
	 * @return the old value of the field
	 */
	@LiveWrite
	@Threadsafe
	public int setBlockDataField(int x, int y, int z, int bits, int value);
	
	/**
	 * Forces a physics update for the block at (x, y, z)
	 *
	 * @param x coordinate of the block
	 * @param y coordinate of the block
	 * @param z coordinate of the block
	 * @param source of this physics update
	 */
	public void updateBlockPhysics(int x, int y, int z, Source source);

	/**
	 * Gets if a block is contained in this area
	 * @param x coordinate of the block
	 * @param y coordinate of the block
	 * @param z coordinate of the block
	 * @return True if it is contained, False if not
	 */
	public boolean containsBlock(int x, int y, int z);

	/**
	 * Gets a {@link Block} representing the block at (x, y, z)
	 * @param x coordinate of the block
	 * @param y coordinate of the block
	 * @param z coordinate of the block
	 * 
	 * @return the Block
	 */
	@Threadsafe
	public Block getBlock(int x, int y, int z);
	
	/**
	 * Gets a {@link Block} representing the block at (x, y, z)
	 * @param x coordinate of the block
	 * @param y coordinate of the block
	 * @param z coordinate of the block
	 * @param source the block should represent
	 * 
	 * @return the Block
	 */
	@Threadsafe
	public Block getBlock(int x, int y, int z, Source source);

	/**
	 * Gets a {@link Block} representing the block at (x, y, z)
	 * @param x coordinate of the block
	 * @param y coordinate of the block
	 * @param z coordinate of the block
	 * 
	 * @return the Block
	 */
	@Threadsafe
	public Block getBlock(float x, float y, float z);

	/**
	 * Gets a {@link Block} representing the block at (x, y, z)
	 * @param x coordinate of the block
	 * @param y coordinate of the block
	 * @param z coordinate of the block
	 * @param source the block should represent
	 * 
	 * @return the Block
	 */
	@Threadsafe
	public Block getBlock(float x, float y, float z, Source source);

	/**
	 * Gets a {@link Block} representing the block at the position given
	 * @param position of the block
	 * 
	 * @return the Block
	 */
	@Threadsafe
	public Block getBlock(Vector3 position);

	/**
	 * Gets a {@link Block} representing the block at the position given
	 * @param position of the block
	 * @param source the block should represent
	 * 
	 * @return the Block
	 */
	@Threadsafe
	public Block getBlock(Vector3 position, Source source);

	/**
	 * Resets all dynamic material updates queued for the given location. This list is checked during the finalize part of the tick, and will cause the onPlacement method to be called.<br>
	 *
	 * @param x
	 * @param y
	 * @param z
	 */
	@DelayedWrite
	public abstract void resetDynamicBlock(int x, int y, int z);
	
	/**
	 * Queues a dynamic material updated for the given location. This list is checked during the finalize part of the tick, and will cause the update method to be called.<br>
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	@DelayedWrite
	public abstract void queueDynamicUpdate(int x, int y, int z);
	
	/**
	 * Queues a dynamic material updated for the given location. This list is checked during the finalize part of the tick, and will cause the update method to be called.<br>
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param nextUpdate the update time
	 */
	@DelayedWrite
	public abstract void queueDynamicUpdate(int x, int y, int z, long nextUpdate);
	
	/**
	 * Queues a dynamic material updated for the given location. This list is checked during the finalize part of the tick, and will cause the update method to be called.<br>
	 * If a replacement policy, other than REPLACE_NONE, is specified and there is already an update for that block pending, the update must replace at least one of those updates, or it won't be added
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param nextUpdate the update time
	 * @param policy the insertion policy
	 */
	@DelayedWrite
	public abstract void queueDynamicUpdate(int x, int y, int z, InsertionPolicy policy, long nextUpdate);
	
	/**
	 * Queues a dynamic material updated for the given location. This list is checked during the finalize part of the tick, and will cause the update method to be called.<br>
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param nextUpdate the update time
	 * @param hint a non-persistent hint for the update
	 */
	@DelayedWrite
	public abstract void queueDynamicUpdate(int x, int y, int z, long nextUpdate, Object hint);
	
	/**
	 * Queues a dynamic material updated for the given location. This list is checked during the finalize part of the tick, and will cause the update method to be called.<br>
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param nextUpdate the update time
	 * @param policy the insertion policy
	 * @param hint a non-persistent hint for the update
	 */
	@DelayedWrite
	public abstract void queueDynamicUpdate(int x, int y, int z, InsertionPolicy policy, long nextUpdate, Object hint);
	
	
}
