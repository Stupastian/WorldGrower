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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;

public class UTestCollectTaxesGoal {

	private CollectTaxesGoal collectTaxesGoal = new CollectTaxesGoal();
	
	@Test
	public void testSortTargets() {
		World world = new WorldImpl(10, 10, null);
		world.addWorldObject(TestUtils.createIntelligentWorldObject(1, Constants.HOUSE_ID, 3));
		world.addWorldObject(TestUtils.createIntelligentWorldObject(2, Constants.HOUSE_ID, 4));
		
		List<WorldObject> targets = new ArrayList<>(world.getWorldObjects());
		
		collectTaxesGoal.sortTargets(targets, world, this::getAmount);
		
		assertEquals(2, targets.get(0).getProperty(Constants.ID).intValue());
		assertEquals(1, targets.get(1).getProperty(Constants.ID).intValue());
	}
	
	private int getAmount(WorldObject w, World world) {
		if (w.getProperty(Constants.ID) == 1) {
			return 100;
		} else if (w.getProperty(Constants.ID) == 2) {
			return 150;
		} else {
			return 0;
		}
	}
}