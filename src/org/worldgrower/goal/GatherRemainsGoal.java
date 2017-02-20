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
import org.worldgrower.text.FormattableText;
import org.worldgrower.text.TextId;

public class GatherRemainsGoal implements Goal {

	public GatherRemainsGoal(List<Goal> allGoals) {
		allGoals.add(this);
	}

	@Override
	public OperationInfo calculateGoal(WorldObject performer, World world) {
		
		List<WorldObject> targets = getRemains(performer, world);

		if (targets.size() > 0) {
			return new OperationInfo(performer, targets.get(0), Args.EMPTY, Actions.GATHER_REMAINS_ACTION);
		} else {
			return null;
		}
	}

	private List<WorldObject> getRemains(WorldObject performer, World world) {
		return GoalUtils.findNearestTargets(performer, Actions.GATHER_REMAINS_ACTION, w -> w.hasProperty(Constants.DECEASED_WORLD_OBJECT) && w.getProperty(Constants.DECEASED_WORLD_OBJECT), world);
	}
	
	@Override
	public void goalMetOrNot(WorldObject performer, World world, boolean goalMet) {
	}

	@Override
	public boolean isGoalMet(WorldObject performer, World world) {
		boolean performerHasRemains = performer.getProperty(Constants.INVENTORY).getIndexFor(Constants.DECEASED_WORLD_OBJECT) != -1;
		return ((getRemains(performer, world).size() == 0) || (performerHasRemains));
	}
	
	@Override
	public boolean isUrgentGoalMet(WorldObject performer, World world) {
		return isGoalMet(performer, world);
	}

	@Override
	public FormattableText getDescription() {
		return new FormattableText(TextId.GOAL_GATHER_REMAINS);
	}

	@Override
	public int evaluate(WorldObject performer, World world) {
		return performer.getProperty(Constants.INVENTORY).getIndexFor(Constants.DECEASED_WORLD_OBJECT) != -1 ? 1 : 0;
	}
}