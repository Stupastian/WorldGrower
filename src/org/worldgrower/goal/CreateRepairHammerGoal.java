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

public class CreateRepairHammerGoal implements Goal {

	private static final int QUANTITY = 2;
	
	public CreateRepairHammerGoal(List<Goal> allGoals) {
		allGoals.add(this);
	}

	@Override
	public OperationInfo calculateGoal(WorldObject performer, World world) {
		WorldObjectContainer inventory = performer.getProperty(Constants.INVENTORY);
		Integer smithId = BuildingGenerator.getSmithId(performer);
		int wood = inventory.getQuantityFor(Constants.WOOD);
		int ore = inventory.getQuantityFor(Constants.ORE);
		if (wood < Actions.CRAFT_REPAIR_HAMMER_ACTION.getWoodRequired()) {
			return Goals.WOOD_GOAL.calculateGoal(performer, world);
		} else if (ore < Actions.CRAFT_REPAIR_HAMMER_ACTION.getOreRequired()) {
			return Goals.ORE_GOAL.calculateGoal(performer, world);
		} else if (smithId == null) {
			return Goals.SMITH_GOAL.calculateGoal(performer, world);
		} else {
			WorldObject smith = world.findWorldObjectById(smithId);
			return new OperationInfo(performer, smith, Args.EMPTY, Actions.CRAFT_REPAIR_HAMMER_ACTION);
		}
	}

	@Override
	public void goalMetOrNot(WorldObject performer, World world, boolean goalMet) {
	}
	
	@Override
	public boolean isGoalMet(WorldObject performer, World world) {
		return getNumberOfRepairHammers(performer) > QUANTITY;
	}
	
	@Override
	public boolean isUrgentGoalMet(WorldObject performer, World world) {
		return isGoalMet(performer, world);
	}

	@Override
	public FormattableText getDescription() {
		return new FormattableText(TextId.GOAL_CREATE_REPAIR_HAMMER);
	}
	
	private int getNumberOfRepairHammers(WorldObject performer) {
		WorldObjectContainer inventory = performer.getProperty(Constants.INVENTORY);
		return inventory.getQuantityFor(Constants.REPAIR_QUALITY);
	}
	
	@Override
	public int evaluate(WorldObject performer, World world) {
		return getNumberOfRepairHammers(performer);
	}
}