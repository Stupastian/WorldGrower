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

public class ClaimCattleGoal implements Goal {

	public ClaimCattleGoal(List<Goal> allGoals) {
		allGoals.add(this);
	}

	@Override
	public OperationInfo calculateGoal(WorldObject performer, World world) {
		WorldObject target = getTarget(performer, world);
		if (target != null) {
			return new OperationInfo(performer, target, Args.EMPTY, Actions.CLAIM_CATTLE_ACTION);
		} else {
			return null;
		}
	}
	
	private WorldObject getTarget(WorldObject performer, World world) {
		List<WorldObject> targets = GoalUtils.findNearestTargetsByProperty(performer, Actions.CLAIM_CATTLE_ACTION, Constants.MEAT_SOURCE, w -> isValidClaimCattleTarget(performer, w), world);
		if (targets.size() > 0) {
			return targets.get(0);
		} else {
			return null;
		}
	}

	private boolean isValidClaimCattleTarget(WorldObject performer, WorldObject w) {
		return w.getProperty(Constants.CATTLE_OWNER_ID) == null;
	}
	
	@Override
	public void goalMetOrNot(WorldObject performer, World world, boolean goalMet) {
	}
	
	@Override
	public boolean isGoalMet(WorldObject performer, World world) {
		WorldObject target = getTarget(performer, world);
		return target == null;
	}
	
	@Override
	public boolean isUrgentGoalMet(WorldObject performer, World world) {
		return isGoalMet(performer, world);
	}

	@Override
	public FormattableText getDescription() {
		return new FormattableText(TextId.GOAL_CLAIM_CATTLE);
	}

	@Override
	public int evaluate(WorldObject performer, World world) {
		return CattlePropertyUtils.getOwnedCattle(performer, world).size();
	}
}
