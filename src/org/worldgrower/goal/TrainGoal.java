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
import org.worldgrower.OperationInfo;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.actions.AttackUtils;
import org.worldgrower.actions.ConstructTrainingDummyAction;
import org.worldgrower.attribute.SkillProperty;
import org.worldgrower.generator.BuildingDimensions;
import org.worldgrower.generator.BuildingGenerator;
import org.worldgrower.text.FormattableText;
import org.worldgrower.text.TextId;

public class TrainGoal implements Goal {

	public TrainGoal(List<Goal> allGoals) {
		allGoals.add(this);
	}

	@Override
	public OperationInfo calculateGoal(WorldObject performer, World world) {
		List<WorldObject> targets = GoalUtils.findNearestTargets(performer, Actions.MELEE_ATTACK_ACTION, w -> BuildingGenerator.isTrainingDummy(w), world);
		if (targets.size() > 0) {
			return new OperationInfo(performer, targets.get(0), new int[] { 0 }, Actions.MELEE_ATTACK_ACTION);
		} else {
			if (!ConstructTrainingDummyAction.hasEnoughWood(performer)) {
				return Goals.WOOD_GOAL.calculateGoal(performer, world);
			} else {
				WorldObject target = BuildLocationUtils.findOpenLocationNearExistingProperty(performer, BuildingDimensions.TRAINING_DUMMY, world);
				return new OperationInfo(performer, target, Args.EMPTY, Actions.CONSTRUCT_TRAINING_DUMMY_ACTION);
			}
		}
	}
	
	@Override
	public void goalMetOrNot(WorldObject performer, World world, boolean goalMet) {
	}

	@Override
	public boolean isGoalMet(WorldObject performer, World world) {
		SkillProperty skillProperty = AttackUtils.determineSkill(performer);
		return performer.getProperty(skillProperty).getLevel(performer) > 15;
	}
	
	@Override
	public boolean isUrgentGoalMet(WorldObject performer, World world) {
		return isGoalMet(performer, world);
	}

	@Override
	public FormattableText getDescription() {
		return new FormattableText(TextId.GOAL_TRAIN);
	}

	@Override
	public int evaluate(WorldObject performer, World world) {
		return 0;
	}
}
