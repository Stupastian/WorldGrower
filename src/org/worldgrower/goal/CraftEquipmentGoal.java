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

import org.worldgrower.Args;
import org.worldgrower.Constants;
import org.worldgrower.OperationInfo;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.generator.BuildingGenerator;
import org.worldgrower.text.FormattableText;
import org.worldgrower.text.TextId;

public class CraftEquipmentGoal implements Goal {

	public CraftEquipmentGoal(List<Goal> allGoals) {
		allGoals.add(this);
	}

	@Override
	public OperationInfo calculateGoal(WorldObject performer, World world) {
		Integer smithId = BuildingGenerator.getSmithId(performer);
		WorldObjectContainer inventory = performer.getProperty(Constants.INVENTORY);
		if (inventory.getQuantityFor(Constants.WOOD) < 10) {
			return Goals.WOOD_GOAL.calculateGoal(performer, world);
		} else if (inventory.getQuantityFor(Constants.ORE) < 7) {
			return Goals.ORE_GOAL.calculateGoal(performer, world);
		} else if (smithId != null) {
			int ironAxeCount = inventory.getWorldObjects(Constants.EQUIPMENT_SLOT, Constants.LEFT_HAND_EQUIPMENT).size();
			int ironCuirassCount = inventory.getWorldObjects(Constants.EQUIPMENT_SLOT, Constants.TORSO_EQUIPMENT).size();
			int ironHelmetCount = inventory.getWorldObjects(Constants.EQUIPMENT_SLOT, Constants.HEAD_EQUIPMENT).size();
			int ironGauntletsCount = inventory.getWorldObjects(Constants.EQUIPMENT_SLOT, Constants.ARMS_EQUIPMENT).size();
			int ironGreavesCount = inventory.getWorldObjects(Constants.EQUIPMENT_SLOT, Constants.LEGS_EQUIPMENT).size();
			int ironBootsCount = inventory.getWorldObjects(Constants.EQUIPMENT_SLOT, Constants.FEET_EQUIPMENT).size();
			WorldObject smith = world.findWorldObjectById(smithId);
			
			if (ironAxeCount == 0){
				return new OperationInfo(performer, smith, Args.EMPTY, Actions.CRAFT_IRON_AXE_ACTION);
			} else if (ironCuirassCount < ironAxeCount) {
				return new OperationInfo(performer, smith, Args.EMPTY, Actions.CRAFT_IRON_CUIRASS_ACTION);
			} else if (ironHelmetCount < ironCuirassCount) {
				return new OperationInfo(performer, smith, Args.EMPTY, Actions.CRAFT_IRON_HELMET_ACTION);
			} else if (ironGauntletsCount < ironHelmetCount) {
				return new OperationInfo(performer, smith, Args.EMPTY, Actions.CRAFT_IRON_GAUNTLETS_ACTION);
			} else if (ironGreavesCount < ironGauntletsCount) {
				return new OperationInfo(performer, smith, Args.EMPTY, Actions.CRAFT_IRON_GREAVES_ACTION);
			} else if (ironBootsCount < ironGauntletsCount) {
				return new OperationInfo(performer, smith, Args.EMPTY, Actions.CRAFT_IRON_BOOTS_ACTION);
			}
		}
		return null;
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
		int numberOfDamageItems = inventory.getQuantityFor(Constants.DAMAGE);
		int numberOfArmorItems = inventory.getQuantityFor(Constants.ARMOR);
		return (numberOfDamageItems + numberOfArmorItems);
	}
	
	@Override
	public boolean isUrgentGoalMet(WorldObject performer, World world) {
		return isGoalMet(performer, world);
	}

	@Override
	public FormattableText getDescription() {
		return new FormattableText(TextId.GOAL_CRAFT_EQUIPMENT);
	}

	@Override
	public int evaluate(WorldObject performer, World world) {
		return getNumberOfEquipmentItemsInInventory(performer);
	}
}