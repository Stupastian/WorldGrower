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
import org.worldgrower.actions.BuildHouseAction;
import org.worldgrower.attribute.BuildingType;
import org.worldgrower.generator.BuildingDimensions;
import org.worldgrower.text.FormattableText;
import org.worldgrower.text.TextId;

public class CreateHouseGoal implements Goal {

	public CreateHouseGoal(List<Goal> allGoals) {
		allGoals.add(this);
	}

	@Override
	public OperationInfo calculateGoal(WorldObject performer, World world) {
		if (!BuildHouseAction.hasEnoughStone(performer)) {
			return Goals.STONE_GOAL.calculateGoal(performer, world);
		} else {
			WorldObject target = BuildLocationUtils.findOpenLocationNearExistingProperty(performer, BuildingDimensions.HOUSE, world);
			if (target != null) {
				return new OperationInfo(performer, target, Args.EMPTY, Actions.BUILD_HOUSE_ACTION);
			} else {
				return null;
			}
		}
	}

	@Override
	public void goalMetOrNot(WorldObject performer, World world, boolean goalMet) {
	}

	@Override
	public boolean isGoalMet(WorldObject performer, World world) {
		return performer.getProperty(Constants.BUILDINGS).count(BuildingType.HOUSE) > 1;
	}
	
	@Override
	public boolean isUrgentGoalMet(WorldObject performer, World world) {
		return isGoalMet(performer, world);
	}

	@Override
	public FormattableText getDescription() {
		return new FormattableText(TextId.GOAL_CREATE_HOUSE);
	}

	@Override
	public int evaluate(WorldObject performer, World world) {
		return performer.getProperty(Constants.BUILDINGS).count(BuildingType.HOUSE);
	}
}