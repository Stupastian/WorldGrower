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
import org.worldgrower.attribute.Prices;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.generator.Item;

public class UTestBuyClothesGoal {

	private BuyClothesGoal goal = Goals.BUY_CLOTHES_GOAL;
	
	@Test
	public void testCalculateGoalNoSellers() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(0, Constants.INVENTORY, new WorldObjectContainer());
		
		assertEquals(null, goal.calculateGoal(performer, world));
	}
	
	@Test
	public void testCalculateGoalOneSeller() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.INVENTORY, new WorldObjectContainer());
		world.addWorldObject(target);
		addPrices(target);
		
		performer.setProperty(Constants.GOLD, 2000);
		WorldObject cottonShirt = Item.COTTON_SHIRT.generate(1f);
		cottonShirt.setProperty(Constants.SELLABLE, Boolean.TRUE);
		target.getProperty(Constants.INVENTORY).addQuantity(cottonShirt);
		
		assertEquals(Actions.BUY_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testCalculateGoalOneSellerLeather() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.INVENTORY, new WorldObjectContainer());
		world.addWorldObject(target);
		addPrices(target);
		
		performer.setProperty(Constants.GOLD, 2000);
		WorldObject leatherShirt = Item.LEATHER_SHIRT.generate(1f);
		leatherShirt.setProperty(Constants.SELLABLE, Boolean.TRUE);
		target.getProperty(Constants.INVENTORY).addQuantity(leatherShirt);		
		WorldObject cottonShirt = Item.COTTON_SHIRT.generate(1f);
		cottonShirt.setProperty(Constants.SELLABLE, Boolean.TRUE);
		
		assertEquals(Actions.BUY_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testCalculateGoalOnePantsSeller() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.INVENTORY, new WorldObjectContainer());
		world.addWorldObject(target);
		performer.setProperty(Constants.GOLD, 2000);
		
		performer.getProperty(Constants.INVENTORY).add(Item.COTTON_SHIRT.generate(1f));
		addCottonPants(target);
		addPrices(target);
		
		assertEquals(Actions.BUY_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testCalculateGoalOneBootsSeller() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.INVENTORY, new WorldObjectContainer());
		world.addWorldObject(target);
		addPrices(target);
		performer.setProperty(Constants.GOLD, 2000);
		
		performer.getProperty(Constants.INVENTORY).add(Item.COTTON_SHIRT.generate(1f));
		performer.getProperty(Constants.INVENTORY).add(Item.COTTON_PANTS.generate(1f));
		addCottonBoots(target);
		
		assertEquals(Actions.BUY_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}

	private void addPrices(WorldObject target) {
		target.setProperty(Constants.PRICES, new Prices());
	}

	private void addCottonShirt(WorldObject target) {
		WorldObject cottonPants = Item.COTTON_SHIRT.generate(1f);
		cottonPants.setProperty(Constants.SELLABLE, Boolean.TRUE);
		target.getProperty(Constants.INVENTORY).addQuantity(cottonPants);
	}
	
	private void addCottonPants(WorldObject target) {
		WorldObject cottonPants = Item.COTTON_PANTS.generate(1f);
		cottonPants.setProperty(Constants.SELLABLE, Boolean.TRUE);
		target.getProperty(Constants.INVENTORY).addQuantity(cottonPants);
	}
	
	private void addCottonBoots(WorldObject target) {
		WorldObject cottonPants = Item.COTTON_BOOTS.generate(1f);
		cottonPants.setProperty(Constants.SELLABLE, Boolean.TRUE);
		target.getProperty(Constants.INVENTORY).addQuantity(cottonPants);
	}
	
	@Test
	public void testIsGoalMetNoTargets() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());

		assertEquals(true, goal.isGoalMet(performer, world));
	}

	@Test
	public void testIsGoalMetTargetsWithoutClothesToSell() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());

		WorldObject target = TestUtils.createIntelligentWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());
		world.addWorldObject(target);
		
		assertEquals(true, goal.isGoalMet(performer, world));
	}
	
	@Test
	public void testIsGoalMetTargetsWithClothesToSell() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());
		performer.setProperty(Constants.GOLD, 1000);
		
		WorldObject target = createTargetWithSellableCottonShirt(2);
		world.addWorldObject(target);
		
		assertEquals(false, goal.isGoalMet(performer, world));
	}
	
	@Test
	public void testIsGoalMetTargetsWithClothesToSellAndAlreadyClothed() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());
		performer.setProperty(Constants.GOLD, 1000);
		addCottonShirt(performer);
		addCottonPants(performer);
		addCottonBoots(performer);
		
		WorldObject target = createTargetWithSellableCottonShirt(2);
		world.addWorldObject(target);
		
		assertEquals(true, goal.isGoalMet(performer, world));
	}

	private WorldObject createTargetWithSellableCottonShirt(int id) {
		WorldObject target = TestUtils.createIntelligentWorldObject(id, Constants.INVENTORY, new WorldObjectContainer());
		target.getProperty(Constants.INVENTORY).addQuantity(Item.COTTON_SHIRT.generate(1f), 10);
		target.getProperty(Constants.INVENTORY).get(0).setProperty(Constants.SELLABLE, Boolean.TRUE);
		return target;
	}
}
