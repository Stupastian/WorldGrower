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
package org.worldgrower.condition;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;

public class UTestConditions {

	@Test
	public void testaddCondition() {
		Conditions conditions = new Conditions();
		World world = new WorldImpl(1, 1, null, null);
		conditions.addCondition(null, Condition.COCOONED_CONDITION, 8, world);
		
		assertEquals(true, conditions.hasCondition(Condition.COCOONED_CONDITION));
	}
	
	@Test
	public void testOnTurn() {
		Conditions conditions = new Conditions();
		World world = new WorldImpl(1, 1, null, null);
		conditions.addCondition(null, Condition.COCOONED_CONDITION, 2, world);
		
		conditions.onTurn(null, world, null);
		assertEquals(true, conditions.hasCondition(Condition.COCOONED_CONDITION));
		
		conditions.onTurn(null, world, null);
		assertEquals(false, conditions.hasCondition(Condition.COCOONED_CONDITION));
	}
	
	@Test
	public void testOnTurnIntoxicated() {
		WorldObject worldObject = TestUtils.createIntelligentWorldObject(3, "worldObject");
		Conditions conditions = worldObject.getProperty(Constants.CONDITIONS);
		World world = new WorldImpl(1, 1, null, null);
		conditions.addCondition(worldObject, Condition.INTOXICATED_CONDITION, 2, world);
		conditions.addCondition(worldObject, Condition.COCOONED_CONDITION, 2, world);
		worldObject.setProperty(Constants.ALCOHOL_LEVEL, 12);
		
		conditions.onTurn(worldObject, world, null);
		assertEquals(true, conditions.hasCondition(Condition.INTOXICATED_CONDITION));
		
		worldObject.setProperty(Constants.ALCOHOL_LEVEL, 0);
		conditions.onTurn(worldObject, world, null);
		assertEquals(false, conditions.hasCondition(Condition.INTOXICATED_CONDITION));
	}
	
	@Test
	public void testCanTakeAction() {
		Conditions conditions = new Conditions();
		World world = new WorldImpl(1, 1, null, null);
		conditions.addCondition(null, Condition.COCOONED_CONDITION, 2, world);
		
		assertEquals(false, conditions.canTakeAction());
	}
	
	@Test
	public void testCanMove() {
		Conditions conditions = new Conditions();
		World world = new WorldImpl(1, 1, null, null);
		conditions.addCondition(null, Condition.COCOONED_CONDITION, 2, world);
		
		assertEquals(false, conditions.canMove());
	}
	
	@Test
	public void testHasDiseaseCondition() {
		Conditions conditions = new Conditions();
		World world = new WorldImpl(1, 1, null, null);
		conditions.addCondition(null, Condition.VAMPIRE_BITE_CONDITION, 2, world);
		
		assertEquals(true, conditions.hasDiseaseCondition());
	}
	
	@Test
	public void testGetDiseaseConditions() {
		Conditions conditions = new Conditions();
		World world = new WorldImpl(1, 1, null, null);
		conditions.addCondition(null, Condition.COCOONED_CONDITION, 2, world);
		conditions.addCondition(null, Condition.VAMPIRE_BITE_CONDITION, 2, world);
		
		assertEquals(Arrays.asList(Condition.VAMPIRE_BITE_CONDITION), conditions.getDiseaseConditions());
	}
	
	@Test
	public void testRemoveAllDiseases() {
		Conditions conditions = new Conditions();
		World world = new WorldImpl(1, 1, null, null);
		conditions.addCondition(null, Condition.VAMPIRE_BITE_CONDITION, 2, world);
	
		assertEquals(true, conditions.hasDiseaseCondition());
		
		conditions.removeAllDiseases(null, new WorldStateChangedListeners());
		assertEquals(false, conditions.hasDiseaseCondition());
	}
	
	@Test
	public void testPerform() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, "Test");
		WorldObject target = TestUtils.createIntelligentWorldObject(2, "Test");
		
		Conditions targetConditions = target.getProperty(Constants.CONDITIONS);
		targetConditions.addCondition(null, Condition.SLEEP_CONDITION, 2, world);
		
		targetConditions.perform(performer, target, null, Actions.MELEE_ATTACK_ACTION, world);
		assertEquals(false, targetConditions.hasCondition(Condition.SLEEP_CONDITION));
	}
	
	@Test
	public void testRemoveAllMagicEffects() {
		Conditions conditions = new Conditions();
		World world = new WorldImpl(1, 1, null, null);
		conditions.addCondition(null, Condition.ENLARGED_CONDITION, 2, world);
	
		assertEquals(true, conditions.hasCondition(Condition.ENLARGED_CONDITION));
		
		conditions.removeAllMagicEffects(null, new WorldStateChangedListeners());
		assertEquals(false, conditions.hasCondition(Condition.ENLARGED_CONDITION));
	}
	
	@Test
	public void testGetMagicConditions() {
		Conditions conditions = new Conditions();
		World world = new WorldImpl(1, 1, null, null);
		conditions.addCondition(null, Condition.COCOONED_CONDITION, 2, world);
		conditions.addCondition(null, Condition.ENLARGED_CONDITION, 2, world);
		
		assertEquals(Arrays.asList(Condition.ENLARGED_CONDITION), conditions.getMagicConditions());
	}
	
	@Test
	public void testGetDescriptions() {
		Conditions conditions = new Conditions();
		World world = new WorldImpl(1, 1, null, null);
		conditions.addCondition(null, Condition.ENLARGED_CONDITION, 2, world);
		
		assertEquals(Arrays.asList("enlarged(2)"), conditions.getDescriptions());
	}
	
	@Test
	public void testGetDescriptionsPermanent() {
		Conditions conditions = new Conditions();
		World world = new WorldImpl(1, 1, null, null);
		WorldObject worldObject = TestUtils.createIntelligentWorldObject(2, Constants.CONDITIONS, conditions);
		Conditions.addPermanent(worldObject, Condition.ENLARGED_CONDITION, world);
		
		assertEquals(Arrays.asList("enlarged"), conditions.getDescriptions());
	}
	
	@Test
	public void testGetLongerDescriptions() {
		Conditions conditions = new Conditions();
		World world = new WorldImpl(1, 1, null, null);
		conditions.addCondition(null, Condition.ENLARGED_CONDITION, 2, world);
		
		assertEquals(Arrays.asList("an enlarged creature deals more damage with physical attacks (2 turns remaining)"), conditions.getLongerDescriptions());
	}
	
	@Test
	public void testGetLongerDescriptionsPermanent() {
		Conditions conditions = new Conditions();
		World world = new WorldImpl(1, 1, null, null);
		WorldObject worldObject = TestUtils.createIntelligentWorldObject(2, Constants.CONDITIONS, conditions);
		Conditions.addPermanent(worldObject, Condition.ENLARGED_CONDITION, world);
		
		assertEquals(Arrays.asList("an enlarged creature deals more damage with physical attacks"), conditions.getLongerDescriptions());
	}
	
	@Test
	public void testShouldAddConditionDiseaseImmunity() {
		Conditions conditions = new Conditions();
		World world = new WorldImpl(1, 1, null, null);
		assertEquals(true, conditions.shouldAddCondition(Condition.VAMPIRE_BITE_CONDITION));
		
		conditions.addCondition(null, Condition.DISEASE_IMMUNITY_CONDITION, 8, world);
		assertEquals(false, conditions.shouldAddCondition(Condition.VAMPIRE_BITE_CONDITION));
	}
	
	@Test
	public void testShouldAddConditionFreedomOfMovement() {
		Conditions conditions = new Conditions();
		World world = new WorldImpl(1, 1, null, null);
		assertEquals(true, conditions.shouldAddCondition(Condition.PARALYZED_CONDITION));
		assertEquals(true, conditions.shouldAddCondition(Condition.ENTANGLED_CONDITION));
		
		conditions.addCondition(null, Condition.FREEDOM_OF_MOVEMENT_CONDITION, 8, world);
		assertEquals(false, conditions.shouldAddCondition(Condition.PARALYZED_CONDITION));
		assertEquals(false, conditions.shouldAddCondition(Condition.ENTANGLED_CONDITION));
	}
	
	@Test
	public void testIsPermanent() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject worldObject = TestUtils.createIntelligentWorldObject(3, "worldObject");
		Conditions.addPermanent(worldObject, Condition.PARALYZED_CONDITION, world);
		Conditions.add(worldObject, Condition.ENTANGLED_CONDITION, 8, world);
		
		assertEquals(true, Conditions.isConditionPermanent(worldObject, Condition.PARALYZED_CONDITION));
		assertEquals(false, Conditions.isConditionPermanent(worldObject, Condition.ENTANGLED_CONDITION));
		
		worldObject.getProperty(Constants.CONDITIONS).onTurn(worldObject, world, new WorldStateChangedListeners());
		assertEquals(true, Conditions.isConditionPermanent(worldObject, Condition.PARALYZED_CONDITION));
		assertEquals(false, Conditions.isConditionPermanent(worldObject, Condition.ENTANGLED_CONDITION));
	}
}
