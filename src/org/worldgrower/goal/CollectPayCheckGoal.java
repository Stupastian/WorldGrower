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

import org.worldgrower.OperationInfo;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.conversation.Conversations;
import org.worldgrower.text.FormattableText;
import org.worldgrower.text.TextId;

public class CollectPayCheckGoal implements Goal {

	public CollectPayCheckGoal(List<Goal> allGoals) {
		allGoals.add(this);
	}

	@Override
	public OperationInfo calculateGoal(WorldObject performer, World world) {
		WorldObject target = GroupPropertyUtils.getLeaderOfVillagers(world);
		if (target != null && !target.equals(performer)) {
			if (isTargetForConversation(performer, target, world)) {
				return new OperationInfo(performer, target, Conversations.createArgs(Conversations.COLLECT_PAY_CHECK_CONVERSATION), Actions.TALK_ACTION);
			}
		}
		return null;
	}
	
	private boolean isTargetForConversation(WorldObject performer, WorldObject target, World world) {
		return !Conversations.COLLECT_PAY_CHECK_CONVERSATION.previousAnswerWasNegative(performer, target, world);
	}
	
	@Override
	public void goalMetOrNot(WorldObject performer, World world, boolean goalMet) {
	}

	@Override
	public boolean isGoalMet(WorldObject performer, World world) {
		int payCheckAmount = GroupPropertyUtils.getPayCheckAmount(performer, world);
		WorldObject leader = GroupPropertyUtils.getLeaderOfVillagers(world);
		if ((payCheckAmount > 0) && (leader != null)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isUrgentGoalMet(WorldObject performer, World world) {
		return isGoalMet(performer, world);
	}

	@Override
	public FormattableText getDescription() {
		return new FormattableText(TextId.GOAL_COLLECT_PAYCHECK);
	}

	@Override
	public int evaluate(WorldObject performer, World world) {
		return 0;
	}
}
