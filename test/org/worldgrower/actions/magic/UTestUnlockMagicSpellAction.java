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
import org.worldgrower.Args;
import org.worldgrower.Constants;
import org.worldgrower.ManagedOperation;
import org.worldgrower.ManagedOperationListener;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.attribute.SkillUtils;
import org.worldgrower.condition.Conditions;
import org.worldgrower.condition.WorldStateChangedListeners;
import org.worldgrower.generator.BuildingGenerator;

public class UTestUnlockMagicSpellAction {

	@Test
	public void testExecute() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer(2);
		int id = BuildingGenerator.generateHouse(0, 0, world, performer);
		WorldObject target = world.findWorldObjectById(id);
		
		assertEquals(true, target.getProperty(Constants.LOCKED));
		
		world.addListener(new ManagedOperationListener() {
			
			@Override
			public void actionPerformed(ManagedOperation managedOperation, WorldObject performer, WorldObject target, int[] args, Object value) {
				assertEquals("worldObject unlocks house", value.toString());
			}
		});		
		
		SkillUtils.useSkill(performer, Constants.EVOCATION_SKILL, 100, new WorldStateChangedListeners());
		Actions.UNLOCK_MAGIC_SPELL_ACTION.execute(performer, target, Args.EMPTY, world);
		
		assertEquals(false, target.getProperty(Constants.LOCKED));
	}
	
	@Test
	public void testExecuteUnlockFails() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer(2);
		int id = BuildingGenerator.generateHouse(0, 0, world, performer);
		WorldObject target = world.findWorldObjectById(id);
		
		assertEquals(true, target.getProperty(Constants.LOCKED));
		target.setProperty(Constants.LOCK_STRENGTH, 100);
		
		world.addListener(new ManagedOperationListener() {
	
			@Override
			public void actionPerformed(ManagedOperation managedOperation, WorldObject performer, WorldObject target, int[] args, Object value) {
				assertEquals("worldObject fails to unlock house", value.toString());
			}
		});
		
		Actions.UNLOCK_MAGIC_SPELL_ACTION.execute(performer, target, Args.EMPTY, world);
		
		assertEquals(true, target.getProperty(Constants.LOCKED));
	}
	
	@Test
	public void testIsValidTarget() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer(2);
		int id = BuildingGenerator.generateHouse(0, 0, world, performer);
		WorldObject target = world.findWorldObjectById(id);
		
		assertEquals(false, Actions.UNLOCK_MAGIC_SPELL_ACTION.isValidTarget(performer, target, world));
		
		performer.setProperty(Constants.KNOWN_SPELLS, Arrays.asList(Actions.UNLOCK_MAGIC_SPELL_ACTION));
		target.setProperty(Constants.LOCKED, Boolean.TRUE);
		
		assertEquals(true, Actions.UNLOCK_MAGIC_SPELL_ACTION.isValidTarget(performer, target, world));
	}
	
	@Test
	public void testIsActionPossible() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer(2);
		int id = BuildingGenerator.generateHouse(0, 0, world, performer);
		WorldObject target = world.findWorldObjectById(id);
		
		assertEquals(true, Actions.UNLOCK_MAGIC_SPELL_ACTION.isActionPossible(performer, target, Args.EMPTY, world));
	}
	
	@Test
	public void testDistance() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		WorldObject target = createPerformer(3);
		
		assertEquals(0, Actions.UNLOCK_MAGIC_SPELL_ACTION.distance(performer, target, Args.EMPTY, world));
	}
	
	@Test
	public void testHasRequiredEnergy() {
		WorldObject performer = createPerformer(2);
		
		performer.setProperty(Constants.ENERGY, 1000);
		assertEquals(true, Actions.UNLOCK_MAGIC_SPELL_ACTION.hasRequiredEnergy(performer));
		
		performer.setProperty(Constants.ENERGY, 0);
		assertEquals(false, Actions.UNLOCK_MAGIC_SPELL_ACTION.hasRequiredEnergy(performer));
	}
	
	private WorldObject createPerformer(int id) {
		WorldObject performer = TestUtils.createSkilledWorldObject(id, Constants.CONDITIONS, new Conditions());
		performer.setProperty(Constants.X, 0);
		performer.setProperty(Constants.Y, 0);
		performer.setProperty(Constants.WIDTH, 1);
		performer.setProperty(Constants.HEIGHT, 1);
		performer.setProperty(Constants.ENERGY, 1000);
		return performer;
	}
}