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
import org.worldgrower.generator.CreatureGenerator;
import org.worldgrower.generator.Item;
import org.worldgrower.goal.GroupPropertyUtils;

public class UTestButcherAction {

	@Test
	public void testExecute() {
		World world = new WorldImpl(1, 1, null, null);
		createVillagersOrganization(world);
		WorldObject performer = createPerformer(2);
		WorldObject target = createCow(world);
		
		Actions.BUTCHER_ACTION.execute(performer, target, Args.EMPTY, world);
		
		assertEquals(false, world.exists(target));
		assertEquals(1, performer.getProperty(Constants.INVENTORY).getQuantityFor(Constants.FOOD));
		assertEquals(5, performer.getProperty(Constants.INVENTORY).getQuantityFor(Constants.LEATHER));
	}
	
	@Test
	public void testExecuteWithButcherKnife() {
		World world = new WorldImpl(1, 1, null, null);
		createVillagersOrganization(world);
		WorldObject performer = createPerformer(2);
		performer.setProperty(Constants.LEFT_HAND_EQUIPMENT, Item.BUTCHER_KNIFE.generate(1f));
		WorldObject target = createCow(world);
		
		Actions.BUTCHER_ACTION.execute(performer, target, Args.EMPTY, world);
		
		assertEquals(false, world.exists(target));
		assertEquals(2, performer.getProperty(Constants.INVENTORY).getQuantityFor(Constants.FOOD));
		assertEquals(10, performer.getProperty(Constants.INVENTORY).getQuantityFor(Constants.LEATHER));
	}
	
	@Test
	public void testIsValidTarget() {
		World world = new WorldImpl(1, 1, null, null);
		createVillagersOrganization(world);
		WorldObject performer = createPerformer(2);
		WorldObject target = createCow(world);
		
		assertEquals(true, Actions.BUTCHER_ACTION.isValidTarget(performer, target, world));
		
		target.setProperty(Constants.MEAT_SOURCE, 0);
		assertEquals(false, Actions.BUTCHER_ACTION.isValidTarget(performer, target, world));
	}
	
	@Test
	public void testIsActionPossible() {
		World world = new WorldImpl(1, 1, null, null);
		createVillagersOrganization(world);
		WorldObject performer = createPerformer(2);
		WorldObject target = createCow(world);
		
		assertEquals(true, Actions.BUTCHER_ACTION.isActionPossible(performer, target, Args.EMPTY, world));
	}
	
	@Test
	public void testDistance() {
		World world = new WorldImpl(1, 1, null, null);
		createVillagersOrganization(world);
		WorldObject performer = createPerformer(2);
		WorldObject target = createCow(world);
		
		assertEquals(0, Actions.BUTCHER_ACTION.distance(performer, target, Args.EMPTY, world));
	}
	
	@Test
	public void testGetDescription() {
		World world = new WorldImpl(1, 1, null, null);
		createVillagersOrganization(world);
		WorldObject performer = createPerformer(2);
		WorldObject target = createCow(world);
		
		assertEquals("butchering a Cow", Actions.BUTCHER_ACTION.getDescription(performer, target, Args.EMPTY, world));
	}

	private WorldObject createCow(World world) {
		int id = new CreatureGenerator(GroupPropertyUtils.create(null, "CowOrg", world)).generateCow(0, 0, world);
		WorldObject target = world.findWorldObjectById(id);
		return target;
	}
	
	private WorldObject createPerformer(int id) {
		WorldObject performer = TestUtils.createSkilledWorldObject(id, Constants.INVENTORY, new WorldObjectContainer());
		performer.setProperty(Constants.X, 0);
		performer.setProperty(Constants.Y, 0);
		performer.setProperty(Constants.WIDTH, 1);
		performer.setProperty(Constants.HEIGHT, 1);
		return performer;
	}
	
	private WorldObject createVillagersOrganization(World world) {
		WorldObject villagersOrganization = GroupPropertyUtils.createVillagersOrganization(world);
		villagersOrganization.setProperty(Constants.ID, 1);
		world.addWorldObject(villagersOrganization);
		return villagersOrganization;
	}
}