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
import org.worldgrower.attribute.BuildingType;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.generator.BuildingGenerator;
import org.worldgrower.generator.Item;

public class UTestCreateSleepingPotionGoal {

	private CreateSleepingPotionGoal goal = Goals.CREATE_SLEEPING_POTION_GOAL;
	
	@Test
	public void testCalculateGoalNull() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject performer = createPerformer();
		
		assertEquals(null, goal.calculateGoal(performer, world));
	}
	
	@Test
	public void testCalculateGoalPlantNightShade() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		
		assertEquals(Actions.PLANT_NIGHT_SHADE_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testCalculateGoalBrewSleepingPotion() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		
		addApothecary(world, performer);
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.NIGHT_SHADE.generate(1f), 20);
		
		assertEquals(Actions.BREW_SLEEPING_POTION_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testCalculateGoalClaimApothecary() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		
		BuildingGenerator.generateApothecary(0, 0, world, performer);
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.NIGHT_SHADE.generate(1f), 20);
		
		assertEquals(Actions.CLAIM_BUILDING_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	private void addApothecary(World world, WorldObject performer) {
		int apothecaryId = BuildingGenerator.generateApothecary(0, 0, world, performer);
		performer.getProperty(Constants.BUILDINGS).add(apothecaryId, BuildingType.APOTHECARY);
	}
	
	@Test
	public void testIsGoalMet() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		
		assertEquals(false, goal.isGoalMet(performer, world));
		
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.SLEEPING_POTION.generate(1f), 20);
		assertEquals(true, goal.isGoalMet(performer, world));
	}
	
	@Test
	public void testGetDescription() {
		assertEquals("creating a sleeping potion", DefaultConversationFormatter.FORMATTER.format(goal.getDescription()));
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