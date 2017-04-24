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
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.attribute.BuildingList;
import org.worldgrower.attribute.BuildingType;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.generator.BuildingGenerator;
import org.worldgrower.generator.Item;
import org.worldgrower.generator.PlantGenerator;
import org.worldgrower.generator.TerrainGenerator;

public class UTestCraftEquipmentGoal {

	private CraftEquipmentGoal goal = Goals.CRAFT_EQUIPMENT_GOAL;
	
	@Test
	public void testCalculateGoalNull() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createSkilledWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());
		performer.setProperty(Constants.BUILDINGS, new BuildingList());
		
		assertEquals(null, goal.calculateGoal(performer, world));
	}
	
	@Test
	public void testCalculateGoalWood() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		performer.setProperty(Constants.BUILDINGS, new BuildingList());
		
		PlantGenerator.generateOldTree(5, 5, world);
		
		assertEquals(Actions.CUT_WOOD_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testCalculateGoalStone() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.WOOD.generate(1f), 20);
		performer.setProperty(Constants.BUILDINGS, new BuildingList());
		
		TerrainGenerator.generateOreResource(5, 5, world);
		
		assertEquals(Actions.MINE_ORE_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testCalculateGoalIronAxe() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.WOOD.generate(1f), 20);
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.ORE.generate(1f), 10);
		
		addSmith(world, performer);
		
		assertEquals(Actions.CRAFT_IRON_AXE_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testCalculateGoalIronCuirass() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.WOOD.generate(1f), 20);
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.ORE.generate(1f), 10);
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_AXE.generate(1f));
		
		addSmith(world, performer);
		
		assertEquals(Actions.CRAFT_IRON_CUIRASS_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testCalculateGoalIronHelmet() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.WOOD.generate(1f), 20);
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.ORE.generate(1f), 10);
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_AXE.generate(1f));
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_CUIRASS.generate(1f));
		
		addSmith(world, performer);
		
		assertEquals(Actions.CRAFT_IRON_HELMET_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testCalculateGoalIronGauntlets() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.WOOD.generate(1f), 20);
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.ORE.generate(1f), 10);
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_AXE.generate(1f));
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_CUIRASS.generate(1f));
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_HELMET.generate(1f));
		
		addSmith(world, performer);
		
		assertEquals(Actions.CRAFT_IRON_GAUNTLETS_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testCalculateGoalIronGreaves() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.WOOD.generate(1f), 20);
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.ORE.generate(1f), 10);
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_AXE.generate(1f));
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_CUIRASS.generate(1f));
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_HELMET.generate(1f));
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_GAUNTLETS.generate(1f));
		
		addSmith(world, performer);
		
		assertEquals(Actions.CRAFT_IRON_GREAVES_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}

	private void addSmith(World world, WorldObject performer) {
		int smithId = BuildingGenerator.generateSmith(0, 0, world, performer);
		performer.setProperty(Constants.BUILDINGS, new BuildingList().add(smithId, BuildingType.SMITH));
	}
	
	@Test
	public void testCalculateGoalIronBoots() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.WOOD.generate(1f), 20);
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.ORE.generate(1f), 10);
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_AXE.generate(1f));
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_CUIRASS.generate(1f));
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_HELMET.generate(1f));
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_GAUNTLETS.generate(1f));
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_GREAVES.generate(1f));
		
		addSmith(world, performer);
		
		assertEquals(Actions.CRAFT_IRON_BOOTS_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testCalculateGoalDone() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.WOOD.generate(1f), 20);
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.ORE.generate(1f), 10);
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_AXE.generate(1f));
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_CUIRASS.generate(1f));
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_HELMET.generate(1f));
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_GAUNTLETS.generate(1f));
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_GREAVES.generate(1f));
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_BOOTS.generate(1f));
		
		addSmith(world, performer);
		
		assertEquals(null, goal.calculateGoal(performer, world));
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