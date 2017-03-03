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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.DefaultConversationFormatter;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.generator.Item;
import org.worldgrower.generator.TerrainGenerator;

public class UTestMineResourceGoal {

	private MineResourcesGoal goal = Goals.MINE_RESOURCES_GOAL;
	
	@Test
	public void testCalculateGoalNull() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createSkilledWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());
		
		assertEquals(null, goal.calculateGoal(performer, world));
	}
	
	@Test
	public void testCalculateGoalStone() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		
		TerrainGenerator.generateStoneResource(5, 5, world);
		
		assertEquals(Actions.MINE_STONE_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testCalculateGoalOre() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		
		TerrainGenerator.generateOreResource(5, 5, world);
		
		assertEquals(Actions.MINE_ORE_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testCalculateGoalGold() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		
		TerrainGenerator.generateGoldResource(5, 5, world);
		
		assertEquals(Actions.MINE_GOLD_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testIsGoalMet() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer();
		
		assertEquals(false, goal.isGoalMet(performer, world));
		
		WorldObjectContainer performerInventory = performer.getProperty(Constants.INVENTORY);
		performerInventory.addQuantity(Item.STONE.generate(1f), 10);
		performerInventory.addQuantity(Item.ORE.generate(1f), 10);
		performerInventory.addQuantity(Item.GOLD.generate(1f), 10);
		assertEquals(true, goal.isGoalMet(performer, world));
	}
	
	@Test
	public void testGetDescription() {
		assertEquals("looking for resources like stone, iron ore and gold", DefaultConversationFormatter.FORMATTER.format(goal.getDescription()));
	}

	private WorldObject createPerformer() {
		WorldObject performer = TestUtils.createSkilledWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());
		performer.setProperty(Constants.X, 0);
		performer.setProperty(Constants.Y, 0);
		performer.setProperty(Constants.WIDTH, 1);
		performer.setProperty(Constants.HEIGHT, 1);
		return performer;
	}
}