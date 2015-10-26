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

import java.util.List;

import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.attribute.ManagedProperty;
import org.worldgrower.attribute.PropertyCountMap;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.generator.ItemGenerator;

public class UTestBuySellUtils {

	@Test
	public void testGetPrice() {
		WorldObject performer = TestUtils.createIntelligentWorldObject(0, Constants.INVENTORY, new WorldObjectContainer());
		performer.getProperty(Constants.INVENTORY).add(ItemGenerator.getIronCuirass(0f));
		performer.setProperty(Constants.PROFIT_PERCENTAGE, 100);
		
		assertEquals(600, BuySellUtils.getPrice(performer, 0));
	}
	
	@Test
	public void testGetDemandGoods() {
		WorldObject performer = TestUtils.createIntelligentWorldObject(0, Constants.DEMANDS, new PropertyCountMap<ManagedProperty<?>>());
		WorldObject inventoryItem = ItemGenerator.generateBerries();
		
		assertEquals(false, BuySellUtils.hasDemandForInventoryItemGoods(performer, inventoryItem));
		
		performer.getProperty(Constants.DEMANDS).add(Constants.FOOD, 1);
		assertEquals(true, BuySellUtils.hasDemandForInventoryItemGoods(performer, inventoryItem));
	}
	
	@Test
	public void testPerformerCanBuyGoods() {
		WorldObject performer = TestUtils.createIntelligentWorldObject(0, Constants.INVENTORY, new WorldObjectContainer());
		performer.setProperty(Constants.GOLD, 0);
		
		WorldObject target = TestUtils.createIntelligentWorldObject(0, Constants.INVENTORY, new WorldObjectContainer());
		WorldObject inventoryItem = ItemGenerator.generateBerries();
		target.getProperty(Constants.INVENTORY).add(inventoryItem);
		target.setProperty(Constants.PROFIT_PERCENTAGE, 100);
		
		assertEquals(false, BuySellUtils.performerCanBuyGoods(performer, target, 0, 1));
		
		performer.setProperty(Constants.GOLD, 100);
		assertEquals(true, BuySellUtils.performerCanBuyGoods(performer, target, 0, 1));
	}
	
	@Test
	public void testGetBuyOperationInfo() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(0, Constants.INVENTORY, new WorldObjectContainer());
		performer.setProperty(Constants.GOLD, 100);
		
		assertEquals(null, BuySellUtils.getBuyOperationInfo(performer, Constants.FOOD, world));
		
		WorldObject target = TestUtils.createIntelligentWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());
		WorldObject inventoryItem = ItemGenerator.generateBerries();
		inventoryItem.setProperty(Constants.SELLABLE, Boolean.TRUE);
		target.getProperty(Constants.INVENTORY).addQuantity(inventoryItem);
		target.setProperty(Constants.PROFIT_PERCENTAGE, 100);
		world.addWorldObject(target);
		
		assertEquals(Actions.BUY_ACTION, BuySellUtils.getBuyOperationInfo(performer, Constants.FOOD, world).getManagedOperation());
	}
	
	@Test
	public void testGetBuyOperationInfoForEquipment() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(0, Constants.INVENTORY, new WorldObjectContainer());
		performer.setProperty(Constants.GOLD, 100);
		
		assertEquals(null, BuySellUtils.getBuyOperationInfo(performer, Constants.FOOD, world));
		
		WorldObject target = TestUtils.createIntelligentWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());
		WorldObject inventoryItem = ItemGenerator.getIronCuirass(1f);
		inventoryItem.setProperty(Constants.SELLABLE, Boolean.TRUE);
		target.getProperty(Constants.INVENTORY).addQuantity(inventoryItem);
		target.setProperty(Constants.PROFIT_PERCENTAGE, 100);
		world.addWorldObject(target);
		
		assertEquals(true, BuySellUtils.hasSellableEquipment(Constants.EQUIPMENT_SLOT, Constants.TORSO_EQUIPMENT, target));
	}
	
	@Test
	public void testTargetWillBuyGoods() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(0, Constants.INVENTORY, new WorldObjectContainer());
		
		WorldObject target = TestUtils.createIntelligentWorldObject(0, Constants.INVENTORY, new WorldObjectContainer());
		target.setProperty(Constants.GOLD, 100);
		
		WorldObject inventoryItem = ItemGenerator.generateBerries();
		performer.getProperty(Constants.INVENTORY).add(inventoryItem);
		performer.setProperty(Constants.PROFIT_PERCENTAGE, 100);
		
		assertEquals(false, BuySellUtils.targetWillBuyGoods(performer, target, 0, world));
		
		target.setProperty(Constants.DEMANDS, new PropertyCountMap<>());
		target.getProperty(Constants.DEMANDS).add(Constants.FOOD, 1);
		assertEquals(true, BuySellUtils.targetWillBuyGoods(performer, target, 0, world));
	}
	
	//TODO: worldObject cannot buy from itself
	
	
	@Test
	public void testFindBuyTargets() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.INVENTORY, new WorldObjectContainer());
		world.addWorldObject(target);
		WorldObject cottonShirt = ItemGenerator.getCottonShirt(1f);
		cottonShirt.setProperty(Constants.SELLABLE, Boolean.TRUE);
		target.getProperty(Constants.INVENTORY).add(cottonShirt);
		
		List<WorldObject> targets = BuySellUtils.findBuyTargets(performer, Constants.NAME, ItemGenerator.COTTON_SHIRT_NAME, world);
		assertEquals(1, targets.size());
		assertEquals(target, targets.get(0));
	}
}