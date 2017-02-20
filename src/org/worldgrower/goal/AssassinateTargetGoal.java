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
import org.worldgrower.text.FormattableText;
import org.worldgrower.text.TextId;

public class AssassinateTargetGoal implements Goal {

	public AssassinateTargetGoal(List<Goal> allGoals) {
		allGoals.add(this);
	}

	@Override
	public OperationInfo calculateGoal(WorldObject performer, World world) {
		Integer assassinateTargetId = performer.getProperty(Constants.ASSASSINATE_TARGET_ID);
		if (assassinateTargetId != null) {
			WorldObject assassinateTarget = GoalUtils.findNearestPersonLookingLike(performer, assassinateTargetId, world);
			if (LocationUtils.isPersonIsolated(performer, assassinateTarget, world)) {
				return new AttackTargetGoal(assassinateTarget).calculateGoal(performer, world);
			}
		}
		return null;
	}

	@Override
	public void goalMetOrNot(WorldObject performer, World world, boolean goalMet) {
	}

	@Override
	public boolean isGoalMet(WorldObject performer, World world) {
		return performer.getProperty(Constants.ASSASSINATE_TARGET_ID) == null;
	}
	
	@Override
	public boolean isUrgentGoalMet(WorldObject performer, World world) {
		return isGoalMet(performer, world);
	}

	@Override
	public FormattableText getDescription() {
		return new FormattableText(TextId.GOAL_ASSASSINATE_TARGET);
	}

	@Override
	public int evaluate(WorldObject performer, World world) {
		return performer.getProperty(Constants.ASSASSINATE_TARGET_ID) == null ? 1 : 0;
	}
}
