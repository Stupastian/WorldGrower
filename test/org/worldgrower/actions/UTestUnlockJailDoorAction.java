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
import org.worldgrower.Args;
import org.worldgrower.Constants;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.generator.BuildingGenerator;

public class UTestUnlockJailDoorAction {

	@Test
	public void testExecute() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		WorldObject target = createPerformer(3);
		world.addWorldObject(performer);
		world.addWorldObject(target);
		Actions.UNLOCK_JAIL_DOOR_ACTION.execute(performer, target, Args.EMPTY, world);
		
		assertEquals(2, world.getWorldObjects().size());
		assertEquals(2, world.getWorldObjects().get(0).getProperty(Constants.ID).intValue());
		assertEquals(0, target.getProperty(Constants.HIT_POINTS).intValue());
	}
	
	@Test
	public void testIsValidTarget() {
		World world = new WorldImpl(15, 15, null, null);
		WorldObject performer = createPerformer(2);
		
		BuildingGenerator.generateJail(0, 0, world, 1f);
		WorldObject target = world.findWorldObjects(w -> BuildingGenerator.isJailDoor(w)).get(0);
		
		assertEquals(false, Actions.UNLOCK_JAIL_DOOR_ACTION.isValidTarget(performer, target, world));
		
		performer.setProperty(Constants.CAN_ATTACK_CRIMINALS, Boolean.TRUE);
		assertEquals(true, Actions.UNLOCK_JAIL_DOOR_ACTION.isValidTarget(performer, target, world));
	}
	
	@Test
	public void testDistance() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		WorldObject target = createPerformer(3);
		
		assertEquals(0, Actions.UNLOCK_JAIL_DOOR_ACTION.distance(performer, target, Args.EMPTY, world));
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