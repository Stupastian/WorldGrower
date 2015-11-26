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
package org.worldgrower.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.attribute.IdList;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.generator.Item;

public class UTestStealAction {

	@Test
	public void testExecuteSuccess() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject performer = createPerformer(2);
		WorldObject target = createPerformer(3);
		target.getProperty(Constants.INVENTORY).addQuantity(Item.WATER.generate(1f));
		
		int index = target.getProperty(Constants.INVENTORY).getIndexFor(Constants.WATER);
		Actions.STEAL_ACTION.execute(performer, target, new int[] { index, 10 }, world);
		
		assertEquals(0, performer.getProperty(Constants.INVENTORY).getIndexFor(Constants.WATER));
		assertEquals(-1, target.getProperty(Constants.INVENTORY).getIndexFor(Constants.WATER));
	}
	
	@Test
	public void testExecuteFailure() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject performer = createPerformer(2);
		WorldObject target = createPerformer(3);
		
		performer.setProperty(Constants.GROUP, new IdList().add(7));
		target.setProperty(Constants.GROUP, new IdList().add(7));
		target.getProperty(Constants.INVENTORY).addQuantity(Item.IRON_CUIRASS.generate(1f));
		
		int index = target.getProperty(Constants.INVENTORY).getIndexFor(Constants.EQUIPMENT_SLOT);
		Actions.STEAL_ACTION.execute(performer, target, new int[] { index, 10 }, world);
		
		assertEquals(-1, performer.getProperty(Constants.INVENTORY).getIndexFor(Constants.EQUIPMENT_SLOT));
		assertEquals(0, target.getProperty(Constants.INVENTORY).getIndexFor(Constants.EQUIPMENT_SLOT));
		assertEquals(0, performer.getProperty(Constants.GROUP).size());

	}
	
	private WorldObject createPerformer(int id) {
		WorldObject performer = TestUtils.createSkilledWorldObject(id, Constants.INVENTORY, new WorldObjectContainer());
		performer.setProperty(Constants.X, 0);
		performer.setProperty(Constants.Y, 0);
		performer.setProperty(Constants.WIDTH, 1);
		performer.setProperty(Constants.HEIGHT, 1);
		return performer;
	}
}