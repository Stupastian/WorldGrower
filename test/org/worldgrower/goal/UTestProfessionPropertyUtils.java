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
import org.worldgrower.OperationInfo;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.history.Turn;

public class UTestProfessionPropertyUtils {

	@Test
	public void testWasFiredOnce() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject leader = createPerformer(2);
		WorldObject taxCollector = createPerformer(3);
		
		assertEquals(false, ProfessionPropertyUtils.wasFiredOnce(leader, world));
		assertEquals(false, ProfessionPropertyUtils.wasFiredOnce(taxCollector, world));
		
		world.getHistory().actionPerformed(new OperationInfo(leader, taxCollector, new int[0], Actions.FIRE_PUBLIC_EMPLOYEE_ACTION), Turn.valueOf(0));		
		assertEquals(false, ProfessionPropertyUtils.wasFiredOnce(leader, world));
		assertEquals(true, ProfessionPropertyUtils.wasFiredOnce(taxCollector, world));
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