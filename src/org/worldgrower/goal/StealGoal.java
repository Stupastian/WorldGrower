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

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.worldgrower.Constants;
import org.worldgrower.OperationInfo;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.attribute.IdMap;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.generator.Item;
import org.worldgrower.text.FormattableText;
import org.worldgrower.text.TextId;

public class StealGoal implements Goal {

	public StealGoal(List<Goal> allGoals) {
		allGoals.add(this);
	}

	@Override
	public OperationInfo calculateGoal(WorldObject performer, World world) {
		List<WorldObject> targets = GoalUtils.findNearestTargetsByProperty(performer, Actions.STEAL_ACTION, Constants.STRENGTH, w -> isValidThievingTarget(performer, w), world);
		if (targets.size() > 0) {
			sortThievingTargets(performer, targets);
			int targetGold = targets.get(0).getProperty(Constants.GOLD);
			if (targetGold > 500) {
				int amount = targetGold / 20;
				return new OperationInfo(performer, targets.get(0), new int[] { amount }, Actions.STEAL_GOLD_ACTION);
			} else {
				int targetInventoryIndex = getIndexOfWorldObjectToBeStolen(targets.get(0));
				return new OperationInfo(performer, targets.get(0), new int[] { targetInventoryIndex }, Actions.STEAL_ACTION);
			}
		}
		
		return null;
	}

	private void sortThievingTargets(WorldObject performer, List<WorldObject> targets) {
		Collections.sort(targets, new ThievingComparator(performer));
	}
	
	boolean isValidThievingTarget(WorldObject performer, WorldObject w) {
		int wGold = w.getProperty(Constants.GOLD).intValue();
		return !performer.equals(w) 
				&& wGold > 100 
				&& (getIndexOfWorldObjectToBeStolen(w) != -1 || wGold > 500);
	}
	
	private static class ThievingComparator implements Comparator<WorldObject> {

		private final WorldObject performer;
		
		public ThievingComparator(WorldObject performer) {
			super();
			this.performer = performer;
		}

		@Override
		public int compare(WorldObject o1, WorldObject o2) {
			IdMap relationships = performer.getProperty(Constants.RELATIONSHIPS);
			int relationshipValue1 = relationships.getValue(o1);
			int relationshipValue2 = relationships.getValue(o2);
			if (relationshipValue1 == relationshipValue2) {
				int index1 = getIndexOfWorldObjectToBeStolen(o1);
				int index2 = getIndexOfWorldObjectToBeStolen(o2);
				
				if (index1 == index2) {
					return 0;
				} else if (index1 == -1) {
					return -1;
				} else if (index2 == -1) {
					return Integer.MAX_VALUE;
				} else {
					//TODO: look at price and other variables instead of index
					return Integer.compare(index1, index2);
				}
			} else {
				return Integer.compare(relationshipValue1, relationshipValue2);
			}
		}
	}
	
	static int getIndexOfWorldObjectToBeStolen(WorldObject target) {
		Map<Integer, Float> pricePerWeightMap = getPricePerWeightMap(target);
		
		Map.Entry<Integer, Float> maxEntry = null;
		for (Map.Entry<Integer, Float> entry : pricePerWeightMap.entrySet()) {
		    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
		        maxEntry = entry;
		    }
		}
		if (maxEntry != null) {
			return maxEntry.getKey();
		} else {
			return -1;
		}
	}

	private static Map<Integer, Float> getPricePerWeightMap(WorldObject target) {
		Map<Integer, Float> pricePerWeightMap = new HashMap<>();
		WorldObjectContainer inventory = target.getProperty(Constants.INVENTORY);
		inventory.iterate((item, index) ->
		{
			if (item.getProperty(Constants.PRICE) == null) { throw new IllegalStateException("price is null for " + item); }
			int price = item.getProperty(Constants.PRICE);
			int weight = item.hasProperty(Constants.WEIGHT) ? item.getProperty(Constants.WEIGHT) : 0;
			float pricePerWeight = (price) / (weight + 1.0f);
			pricePerWeightMap.put(index, pricePerWeight);
		});
		return pricePerWeightMap;
	}

	@Override
	public void goalMetOrNot(WorldObject performer, World world, boolean goalMet) {
	}

	@Override
	public boolean isGoalMet(WorldObject performer, World world) {
		int gold = performer.getProperty(Constants.GOLD);
		return (gold > 200);
	}
	
	@Override
	public boolean isUrgentGoalMet(WorldObject performer, World world) {
		return isGoalMet(performer, world);
	}

	@Override
	public FormattableText getDescription() {
		return new FormattableText(TextId.GOAL_STEAL, Item.GOLD);
	}

	@Override
	public int evaluate(WorldObject performer, World world) {
		return 0;
	}
}
