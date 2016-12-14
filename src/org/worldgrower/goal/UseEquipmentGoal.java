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
package org.worldgrower.goal;

import java.util.List;

import org.worldgrower.Constants;
import org.worldgrower.OperationInfo;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.attribute.UnCheckedProperty;
import org.worldgrower.attribute.WorldObjectContainer;

public class UseEquipmentGoal implements Goal {

	public UseEquipmentGoal(List<Goal> allGoals) {
		allGoals.add(this);
	}

	@Override
	public OperationInfo calculateGoal(WorldObject performer, World world) {
		if (hasUnusedEquipment(performer, Constants.HEAD_EQUIPMENT)) {
			return equipUnusedEquipment(performer, Constants.HEAD_EQUIPMENT, world);
		} else if (hasUnusedEquipment(performer, Constants.TORSO_EQUIPMENT)) {
			return equipUnusedEquipment(performer, Constants.TORSO_EQUIPMENT, world);
		} else if (hasUnusedEquipment(performer, Constants.ARMS_EQUIPMENT)) {
			return equipUnusedEquipment(performer, Constants.ARMS_EQUIPMENT, world);
		} else if (hasUnusedEquipment(performer, Constants.FEET_EQUIPMENT)) {
			return equipUnusedEquipment(performer, Constants.FEET_EQUIPMENT, world);
		} else if (hasUnusedEquipment(performer, Constants.LEGS_EQUIPMENT)) {
			return equipUnusedEquipment(performer, Constants.LEGS_EQUIPMENT, world);
		} else {
			return null;
		}
	}

	public static OperationInfo equipUnusedEquipment(WorldObject performer, UnCheckedProperty<WorldObject> equipmentSlotProperty, World world) {
		int index = getIndexOfUnusedEquipment(performer, equipmentSlotProperty);
		int[] args = new int[] { index };
		return new OperationInfo(performer, performer, args, Actions.EQUIP_INVENTORY_ITEM_ACTION);
	}
	
	private static int getIndexOfUnusedEquipment(WorldObject performer, UnCheckedProperty<WorldObject> equipmentSlotProperty) {
		WorldObject equipmentSlot = performer.getProperty(equipmentSlotProperty);
		
		if (equipmentSlot == null) {
			WorldObjectContainer inventory = performer.getProperty(Constants.INVENTORY);
			int index = inventory.getIndexFor(Constants.EQUIPMENT_SLOT, equipmentSlotProperty);
			if (index != -1) {
				return index;
			}
		}
		return -1;
	}
	
	public static boolean hasUnusedEquipment(WorldObject performer, UnCheckedProperty<WorldObject> equipmentSlotProperty) {
		return getIndexOfUnusedEquipment(performer, equipmentSlotProperty) != -1;
	}
	
	@Override
	public void goalMetOrNot(WorldObject performer, World world, boolean goalMet) {
	}

	@Override
	public boolean isGoalMet(WorldObject performer, World world) {
		return getNumberOfEquipmentItemsInInventory(performer) >= 5;
	}
	
	private int getNumberOfEquipmentItemsInInventory(WorldObject performer) {
		WorldObjectContainer inventory = performer.getProperty(Constants.INVENTORY);
		return inventory.getWorldObjectsByFunction(Constants.EQUIPMENT_HEALTH, w -> true).size();
	}
	
	@Override
	public boolean isUrgentGoalMet(WorldObject performer, World world) {
		return isGoalMet(performer, world);
	}

	@Override
	public String getDescription() {
		return "looking for equipment";
	}

	@Override
	public int evaluate(WorldObject performer, World world) {
		return getNumberOfEquipmentItemsInInventory(performer);
	}
}