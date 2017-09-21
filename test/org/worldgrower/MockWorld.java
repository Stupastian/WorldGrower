/*******************************************************************************
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.worldgrower;

import java.io.File;
import java.util.List;

import org.worldgrower.attribute.IntProperty;
import org.worldgrower.attribute.ManagedProperty;
import org.worldgrower.condition.WorldStateChangedListener;
import org.worldgrower.condition.WorldStateChangedListeners;
import org.worldgrower.goal.Goal;
import org.worldgrower.history.History;
import org.worldgrower.history.Turn;
import org.worldgrower.terrain.Terrain;

public class MockWorld implements World {

	private final Terrain terrain;
	private final World world;
	
	private Integer currentTurn = null;
	
	public MockWorld(Terrain terrain, World world) {
		super();
		this.terrain = terrain;
		this.world = world;
	}

	@Override
	public void addWorldObject(WorldObject worldObject) {
		world.addWorldObject(worldObject);
		
	}

	@Override
	public void removeWorldObject(WorldObject worldObject) {
		
	}

	@Override
	public List<WorldObject> getWorldObjects() {
		return world.getWorldObjects();
	}

	@Override
	public boolean exists(WorldObject worldObject) {
		return false;
	}
	
	@Override
	public boolean exists(int id) {
		return false;
	}

	@Override
	public List<WorldObject> findWorldObjects(WorldObjectCondition worldObjectCondition) {
		return world.findWorldObjects(worldObjectCondition);
	}

	@Override
	public WorldObject findWorldObjectById(int id) {
		return world.findWorldObjectById(id);
	}

	@Override
	public List<WorldObject> findWorldObjectsByProperty(ManagedProperty<?> managedProperty, WorldObjectCondition worldObjectCondition) {
		return world.findWorldObjectsByProperty(managedProperty, worldObjectCondition);
	}

	@Override
	public int generateUniqueId() {
		return 0;
	}

	@Override
	public <T> void logAction(ManagedOperation managedOperation, WorldObject performer, WorldObject target, int[] args, T value) {
		
	}

	@Override
	public void addListener(ManagedOperationListener listener) {
		
	}

	@Override
	public void removeListener(ManagedOperationListener listener) {
		
	}

	@Override
	public <T> T getListenerByClass(Class<T> clazz) {
		return null;
	}

	@Override
	public int getWidth() {
		return terrain.getWidth();
	}

	@Override
	public int getHeight() {
		return terrain.getHeight();
	}

	@Override
	public Terrain getTerrain() {
		return terrain;
	}

	@Override
	public Goal getGoal(WorldObject worldObject) {
		return null;
	}

	@Override
	public OperationInfo getImmediateGoal(WorldObject worldObject, World world) {
		return null;
	}

	@Override
	public Turn getCurrentTurn() {
		if (currentTurn != null) {
			return Turn.valueOf(this.currentTurn);
		} else {
			return world.getCurrentTurn();
		}
	}

	@Override
	public void nextTurn() {
		world.nextTurn();
	}

	@Override
	public History getHistory() {
		return null;
	}

	@Override
	public void save(File fileToSave) {
		
	}

	@Override
	public WorldOnTurn getWorldOnTurn() {
		return null;
	}

	@Override
	public WorldStateChangedListeners getWorldStateChangedListeners() {
		return new WorldStateChangedListeners();
	}

	@Override
	public void addWorldStateChangedListener(WorldStateChangedListener worldStateChangedListener) {
		
	}

	public void setCurrentTurn(Integer currentTurn) {
		this.currentTurn = currentTurn;
	}

	@Override
	public WorldObjectsCache getWorldObjectsCache(IntProperty intProperty1, IntProperty intProperty2) {
		return world.getWorldObjectsCache(intProperty1, intProperty2);
	}

	@Override
	public WorldObjectsCache getWorldObjectsCache() {
		return world.getWorldObjectsCache();
	}

	@Override
	public void removeDeadWorldObjects() {
		
	}

	@Override
	public <T> T getUserData(Class<T> clazz) {
		return null;
	}
}
