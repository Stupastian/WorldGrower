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
package org.worldgrower.actions.magic;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.attribute.IdList;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.condition.Conditions;
import org.worldgrower.generator.Item;

public class UTestAnimateSuitOfArmorAction {

	@Test
	public void testExecute() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		
		performer.setProperty(Constants.GROUP, new IdList());
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_CUIRASS.generate(1f));
		assertEquals(1, performer.getProperty(Constants.INVENTORY).getQuantityFor(Constants.ARMOR));
		
		Actions.ANIMATE_SUIT_OF_ARMOR_ACTION.execute(performer, performer, new int[] {0}, world);
		
		assertEquals(2, world.getWorldObjects().size());
		assertEquals(0, performer.getProperty(Constants.INVENTORY).getQuantityFor(Constants.ARMOR));
	}
	
	@Test
	public void testIsValidTarget() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		WorldObject target = createPerformer(3);
		
		assertEquals(true, Actions.ANIMATE_SUIT_OF_ARMOR_ACTION.isValidTarget(performer, performer, world));
		assertEquals(false, Actions.ANIMATE_SUIT_OF_ARMOR_ACTION.isValidTarget(performer, target, world));
	}
	
	@Test
	public void testIsActionPossible() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		
		performer.setProperty(Constants.CONDITIONS, new Conditions());
		performer.setProperty(Constants.KNOWN_SPELLS, Arrays.asList(Actions.ANIMATE_SUIT_OF_ARMOR_ACTION));
		performer.setProperty(Constants.GROUP, new IdList());
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_CUIRASS.generate(1f));
		
		WorldObject soulGem = Item.FILLED_SOUL_GEM.generate(1f);
		performer.getProperty(Constants.INVENTORY).addQuantity(soulGem);
		
		assertEquals(true, Actions.ANIMATE_SUIT_OF_ARMOR_ACTION.isActionPossible(performer, performer, new int[] {0}, world));
	}
	
	private WorldObject createPerformer(int id) {
		WorldObject performer = TestUtils.createSkilledWorldObject(id, Constants.INVENTORY, new WorldObjectContainer());
		performer.setProperty(Constants.X, 0);
		performer.setProperty(Constants.Y, 0);
		performer.setProperty(Constants.WIDTH, 1);
		performer.setProperty(Constants.HEIGHT, 1);
		performer.setProperty(Constants.ENERGY, 1000);
		return performer;
	}
}