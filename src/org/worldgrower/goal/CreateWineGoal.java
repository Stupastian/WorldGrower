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
import org.worldgrower.generator.BuildingDimensions;
import org.worldgrower.generator.BuildingGenerator;
import org.worldgrower.text.FormattableText;
import org.worldgrower.text.TextId;

public class CreateWineGoal implements Goal {

	public CreateWineGoal(List<Goal> allGoals) {
		allGoals.add(this);
	}

	@Override
	public OperationInfo calculateGoal(WorldObject performer, World world) {
		Integer breweryId = BuildingGenerator.getBreweryId(performer);
		if (performer.getProperty(Constants.INVENTORY).getQuantityFor(Constants.GRAPE) < 5) {
			OperationInfo harvestGrapesOperationInfo = Goals.HARVEST_GRAPES_GOAL.calculateGoal(performer, world);
			if (harvestGrapesOperationInfo != null) {
				return harvestGrapesOperationInfo;
			} else {
				WorldObject target = BuildLocationUtils.findOpenLocationNearExistingProperty(performer, BuildingDimensions.GRAPE_VINE, world);
		
				if (target != null) {
					return new OperationInfo(performer, target, Args.EMPTY, Actions.PLANT_GRAPE_VINE_ACTION);
				} else {
					return null;
				}
			}
		} else if (breweryId == null) {
			return Goals.BREWERY_GOAL.calculateGoal(performer, world);
		} else {
			WorldObject brewery = world.findWorldObjectById(breweryId);
			return new OperationInfo(performer, brewery, Args.EMPTY, Actions.BREW_WINE_ACTION);
		}
	}
	
	@Override
	public void goalMetOrNot(WorldObject performer, World world, boolean goalMet) {
	}

	@Override
	public boolean isGoalMet(WorldObject performer, World world) {
		return performer.getProperty(Constants.INVENTORY).getQuantityFor(Constants.WINE) > 5;
	}
	
	@Override
	public boolean isUrgentGoalMet(WorldObject performer, World world) {
		return isGoalMet(performer, world);
	}

	@Override
	public FormattableText getDescription() {
		return new FormattableText(TextId.GOAL_CREATE_WINE);
	}

	@Override
	public int evaluate(WorldObject performer, World world) {
		return performer.getProperty(Constants.INVENTORY).getQuantityFor(Constants.WINE);
	}
}