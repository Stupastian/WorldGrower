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
import org.worldgrower.MockCommonerGenerator;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.condition.VampireUtils;
import org.worldgrower.condition.WorldStateChangedListeners;
import org.worldgrower.generator.CommonerGenerator;
import org.worldgrower.generator.Item;
import org.worldgrower.goal.GroupPropertyUtils;

public class UTestCreateBloodAction {

	private final CommonerGenerator commonerGenerator = new MockCommonerGenerator();
	
	@Test
	public void testExecute() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject organization = GroupPropertyUtils.create(null, "TestOrg", world);
		WorldObject performer = createPerformer(world, organization);
		WorldObject target = createPerformer(world, organization);
		VampireUtils.vampirizePerson(performer, new WorldStateChangedListeners());
		
		assertEquals(26 * Item.COMBAT_MULTIPLIER, target.getProperty(Constants.HIT_POINTS).intValue());
		
		Actions.CREATE_BLOOD_ACTION.execute(performer, target, Args.EMPTY, world);
		
		assertEquals(25 * Item.COMBAT_MULTIPLIER, target.getProperty(Constants.HIT_POINTS).intValue());
		assertEquals(1, performer.getProperty(Constants.INVENTORY).getQuantityFor(Constants.WATER));
	}
	
	@Test
	public void testIsValidTarget() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject organization = GroupPropertyUtils.create(null, "TestOrg", world);
		WorldObject performer = createPerformer(world, organization);
		WorldObject target = createPerformer(world, organization);
		
		assertEquals(true, Actions.CREATE_BLOOD_ACTION.isValidTarget(performer, target, world));
		
		VampireUtils.vampirizePerson(target, new WorldStateChangedListeners());
		assertEquals(false, Actions.CREATE_BLOOD_ACTION.isValidTarget(performer, target, world));
	}
	
	@Test
	public void testIsActionPossible() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject organization = GroupPropertyUtils.create(null, "TestOrg", world);
		WorldObject performer = createPerformer(world, organization);
		WorldObject target = createPerformer(world, organization);
		VampireUtils.vampirizePerson(performer, new WorldStateChangedListeners());
		
		assertEquals(true, Actions.CREATE_BLOOD_ACTION.isActionPossible(performer, target, Args.EMPTY, world));
	}
	
	@Test
	public void testDistance() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject organization = GroupPropertyUtils.create(null, "TestOrg", world);
		WorldObject performer = createPerformer(world, organization);
		WorldObject target = createPerformer(world, organization);
		VampireUtils.vampirizePerson(performer, new WorldStateChangedListeners());
		
		assertEquals(0, Actions.CREATE_BLOOD_ACTION.distance(performer, target, Args.EMPTY, world));
	}
	
	private WorldObject createPerformer(World world, WorldObject organization) {
		int performerId = commonerGenerator.generateCommoner(0, 0, world, organization, CommonerGenerator.NO_PARENT);
		WorldObject performer = world.findWorldObjectById(performerId);
		return performer;
	}
}