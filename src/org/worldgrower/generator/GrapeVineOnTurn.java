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
package org.worldgrower.generator;

import org.worldgrower.Constants;
import org.worldgrower.OnTurn;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.condition.WorldStateChangedListeners;
import org.worldgrower.goal.DrownUtils;

public class GrapeVineOnTurn implements OnTurn {

	@Override
	public void onTurn(WorldObject worldObject, World world, WorldStateChangedListeners creatureTypeChangedListeners) {
		increaseGrapeAmount(worldObject, world);
		
		DrownUtils.checkForDrowning(worldObject, world);
	}

	private static void increaseGrapeAmount(WorldObject worldObject, World world) {
		worldObject.increment(Constants.GRAPE_SOURCE, 5);
		
		worldObject.setProperty(Constants.IMAGE_ID, VineImageCalculator.getImageId(worldObject, world));
	}
	
	public static void increaseGrapeAmountToMax(WorldObject worldObject, World world) {
		while (!Constants.GRAPE_SOURCE.isAtMax(worldObject)) {
			increaseGrapeAmount(worldObject, world);
		}
	}
}
