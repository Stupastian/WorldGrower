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
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.condition.Condition;
import org.worldgrower.condition.Conditions;

public class UTestFreedomOfMovementAction {

	private final FreedomOfMovementAction action = Actions.FREEDOM_OF_MOVEMENT_ACTION;
	
	@Test
	public void testExecute() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		WorldObject target = createPerformer(3);
		
		Conditions.add(target, Condition.PARALYZED_CONDITION, 8, world);
		Conditions.add(target, Condition.ENTANGLED_CONDITION, 8, world);
		
		action.execute(performer, target, Args.EMPTY, world);
		
		assertEquals(true, target.getProperty(Constants.CONDITIONS).hasCondition(Condition.FREEDOM_OF_MOVEMENT_CONDITION));
		assertEquals(false, target.getProperty(Constants.CONDITIONS).hasCondition(Condition.PARALYZED_CONDITION));
		assertEquals(false, target.getProperty(Constants.CONDITIONS).hasCondition(Condition.ENTANGLED_CONDITION));
	}
	
	@Test
	public void testExecutePreventMovementLimitation() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		WorldObject target = createPerformer(3);
		
		action.execute(performer, target, Args.EMPTY, world);
		
		assertEquals(true, target.getProperty(Constants.CONDITIONS).hasCondition(Condition.FREEDOM_OF_MOVEMENT_CONDITION));
		assertEquals(false, target.getProperty(Constants.CONDITIONS).hasCondition(Condition.PARALYZED_CONDITION));
		assertEquals(false, target.getProperty(Constants.CONDITIONS).hasCondition(Condition.ENTANGLED_CONDITION));
	
		Conditions.add(target, Condition.PARALYZED_CONDITION, 8, world);
		Conditions.add(target, Condition.ENTANGLED_CONDITION, 8, world);
		
		assertEquals(true, target.getProperty(Constants.CONDITIONS).hasCondition(Condition.FREEDOM_OF_MOVEMENT_CONDITION));
		assertEquals(false, target.getProperty(Constants.CONDITIONS).hasCondition(Condition.PARALYZED_CONDITION));
		assertEquals(false, target.getProperty(Constants.CONDITIONS).hasCondition(Condition.ENTANGLED_CONDITION));
	}
	
	@Test
	public void testIsValidTarget() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		
		assertEquals(false, action.isValidTarget(performer, performer, world));
		
		performer.setProperty(Constants.KNOWN_SPELLS, Arrays.asList(action));
		assertEquals(true, action.isValidTarget(performer, performer, world));
	}
	
	@Test
	public void testIsActionPossible() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		
		assertEquals(true, action.isActionPossible(performer, performer, Args.EMPTY, world));
	}
	
	@Test
	public void testDistance() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		WorldObject target = createPerformer(3);
		
		assertEquals(0, action.distance(performer, target, Args.EMPTY, world));
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