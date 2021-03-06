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
import org.worldgrower.attribute.KnowledgeMap;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.condition.Condition;
import org.worldgrower.condition.Conditions;
import org.worldgrower.generator.BuildingGenerator;

public class UTestDrinkAction {

	@Test
	public void testExecuteDrinkWater() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer(2);
		
		int wellId = BuildingGenerator.buildWell(0, 0, world, 1f);
		WorldObject well = world.findWorldObjectById(wellId);
		
		assertEquals(800, performer.getProperty(Constants.WATER).intValue());
		Actions.DRINK_ACTION.execute(performer, well, Args.EMPTY, world);
		
		assertEquals(950, performer.getProperty(Constants.WATER).intValue());
	}
	
	@Test
	public void testExecuteDrinkAlcohol() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer(2);
		
		int wellId = BuildingGenerator.buildWell(0, 0, world, 1f);
		WorldObject well = world.findWorldObjectById(wellId);
		well.setProperty(Constants.ALCOHOL_LEVEL, 9000);
		
		assertEquals(800, performer.getProperty(Constants.WATER).intValue());
		Actions.DRINK_ACTION.execute(performer, well, Args.EMPTY, world);
		
		assertEquals(true, performer.getProperty(Constants.CONDITIONS).hasCondition(Condition.INTOXICATED_CONDITION));
	}
	
	@Test
	public void testExecuteDrinkPoison() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer(2);
		
		int wellId = BuildingGenerator.buildWell(0, 0, world, 1f);
		WorldObject well = world.findWorldObjectById(wellId);
		well.setProperty(Constants.POISON_DAMAGE, 10);
		
		assertEquals(800, performer.getProperty(Constants.WATER).intValue());
		Actions.DRINK_ACTION.execute(performer, well, Args.EMPTY, world);
		
		assertEquals(true, performer.getProperty(Constants.CONDITIONS).hasCondition(Condition.POISONED_CONDITION));
	}
	
	@Test
	public void testIsValidTarget() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer(2);
		performer.setProperty(Constants.KNOWLEDGE_MAP, new KnowledgeMap());
		
		int wellId = BuildingGenerator.buildWell(0, 0, world, 1f);
		WorldObject well = world.findWorldObjectById(wellId);
		
		assertEquals(true, Actions.DRINK_ACTION.isValidTarget(performer, well, world));
		assertEquals(false, Actions.DRINK_ACTION.isValidTarget(performer, performer, world));
	}
	
	@Test
	public void testIsActionPossible() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer(2);
		
		int wellId = BuildingGenerator.buildWell(0, 0, world, 1f);
		WorldObject well = world.findWorldObjectById(wellId);
		
		assertEquals(true, Actions.DRINK_ACTION.isActionPossible(performer, well, Args.EMPTY, world));
	}
	
	private WorldObject createPerformer(int id) {
		WorldObject performer = TestUtils.createSkilledWorldObject(id, Constants.INVENTORY, new WorldObjectContainer());
		performer.setProperty(Constants.X, 0);
		performer.setProperty(Constants.Y, 0);
		performer.setProperty(Constants.WIDTH, 1);
		performer.setProperty(Constants.HEIGHT, 1);
		performer.setProperty(Constants.WATER, 800);
		performer.setProperty(Constants.ALCOHOL_LEVEL, 0);
		performer.setProperty(Constants.CONDITIONS, new Conditions());
		return performer;
	}
}