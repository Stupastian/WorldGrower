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
import org.worldgrower.attribute.BuildingType;
import org.worldgrower.generator.BuildingGenerator;
import org.worldgrower.text.FormattableText;
import org.worldgrower.text.TextId;

public class PaperMillGoal implements Goal {

	public PaperMillGoal(List<Goal> allGoals) {
		allGoals.add(this);
	}

	@Override
	public OperationInfo calculateGoal(WorldObject performer, World world) {
		List<WorldObject> unownedPapermills = BuildingGenerator.findUnownedBuildingsForClaiming(performer, Constants.PAPER_MILL_QUALITY, w -> BuildingGenerator.isPapermill(w), world);
		if (unownedPapermills.size() > 0) {
			return new OperationInfo(performer, unownedPapermills.get(0), Args.EMPTY, Actions.CLAIM_BUILDING_ACTION);
		} else {
			OperationInfo buyBuildingOperationInfo = HousePropertyUtils.createBuyBuildingOperationInfo(performer, BuildingType.PAPERMILL, world);
			if (buyBuildingOperationInfo != null) {
				return buyBuildingOperationInfo;
			} else {
				return Goals.CREATE_PAPER_MILL_GOAL.calculateGoal(performer, world);
			}
		}
	}
	
	@Override
	public void goalMetOrNot(WorldObject performer, World world, boolean goalMet) {
		defaultGoalMetOrNot(performer, world, goalMet, Constants.PAPER_MILL_QUALITY);
	}

	@Override
	public boolean isGoalMet(WorldObject performer, World world) {
		Integer paperMillId = BuildingGenerator.getPapermillId(performer);
		if (paperMillId != null) {
			WorldObject paperMill = world.findWorldObjectById(paperMillId);
			return (paperMill.getProperty(Constants.PAPER_MILL_QUALITY) > 0);
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isUrgentGoalMet(WorldObject performer, World world) {
		return isGoalMet(performer, world);
	}

	@Override
	public FormattableText getDescription() {
		return new FormattableText(TextId.GOAL_PAPER_MILL);
	}

	@Override
	public int evaluate(WorldObject performer, World world) {
		Integer paperMillId = BuildingGenerator.getPapermillId(performer);
		return (paperMillId != null) ? 1 : 0;
	}
}