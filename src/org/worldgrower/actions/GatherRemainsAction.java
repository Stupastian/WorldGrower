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
package org.worldgrower.actions;

import java.io.ObjectStreamException;

import org.worldgrower.Constants;
import org.worldgrower.ManagedOperation;
import org.worldgrower.Reach;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.generator.Item;
import org.worldgrower.gui.ImageIds;

public class GatherRemainsAction implements ManagedOperation {

	@Override
	public void execute(WorldObject performer, WorldObject target, int[] args, World world) {
		WorldObjectContainer performerInventory = performer.getProperty(Constants.INVENTORY);
		
		if (target.getProperty(Constants.GOLD) == null) {
			throw new IllegalStateException("target has no gold: " + target);
		}
		
		int targetGold = target.getProperty(Constants.GOLD);
		performer.increment(Constants.GOLD, targetGold);
		target.removeProperty(Constants.GOLD);
		
		target.setProperty(Constants.ITEM_ID, Item.REMAINS);
		target.setProperty(Constants.PRICE, 0);
		
		WorldObjectContainer targetInventory = target.getProperty(Constants.INVENTORY);
		performerInventory.moveItemsFrom(targetInventory);
		
		performerInventory.addQuantity(target);
		target.setProperty(Constants.HIT_POINTS, 0);
	}

	@Override
	public int distance(WorldObject performer, WorldObject target, int[] args, World world) {
		return Reach.evaluateTarget(performer, target, 1);
	}
	
	@Override
	public boolean isActionPossible(WorldObject performer, WorldObject target, int[] args, World world) {
		return true;
	}
	
	@Override
	public String getRequirementsDescription() {
		return CraftUtils.getRequirementsDescription(Constants.DISTANCE, 1);
	}
	
	@Override
	public String getDescription() {
		return "gather remains and store it in the inventory";
	}

	@Override
	public boolean requiresArguments() {
		return false;
	}

	@Override
	public boolean isValidTarget(WorldObject performer, WorldObject target, World world) {
		return target.hasProperty(Constants.DECEASED_WORLD_OBJECT) && target.getProperty(Constants.DECEASED_WORLD_OBJECT);
	}
	
	@Override
	public String getDescription(WorldObject performer, WorldObject target, int[] args, World world) {
		return "gathering remains";
	}
	
	public Object readResolve() throws ObjectStreamException {
		return readResolveImpl();
	}

	@Override
	public String getSimpleDescription() {
		return "gather remains";
	}
	
	@Override
	public ImageIds getImageIds(WorldObject performer) {
		return ImageIds.SKELETAL_REMAINS;
	}
}