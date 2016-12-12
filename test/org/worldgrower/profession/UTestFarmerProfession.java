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
package org.worldgrower.profession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.generator.Item;
import org.worldgrower.goal.Goal;
import org.worldgrower.goal.Goals;

public class UTestFarmerProfession {

	FarmerProfession profession;
	List<Profession> professions;
	
	@Before
	public void setUp() {
		professions = new ArrayList<Profession>();
		profession = new FarmerProfession(professions);
	}
	
	@Test
	public void testGetDescription() {
		assertEquals(profession.getDescription(), "farmer");
	}

	@Test
	public void testGetProfessionGoals() {
		List<Goal> goals = profession.getProfessionGoals();
		assertEquals(goals.get(0), Goals.GATHER_FOOD_GOAL);
		assertEquals(goals.get(1), Goals.MARK_FOOD_AS_SELLABLE_GOAL);
		assertEquals(goals.get(2), Goals.CREATE_FOOD_SOURCES_GOAL);
		assertEquals(goals.get(3), Goals.SELL_FOOD_GOAL);
	}

	@Test
	public void testGetSkillProperty() {
		assertEquals(profession.getSkillProperty(), Constants.FARMING_SKILL);
	}

	//@Test
	//public void testReadResolve() throws ObjectStreamException {
	//	FarmerProfession profession2 = new FarmerProfession(professions);
	//	assertEquals(profession.readResolveImpl(), profession2);
	//}

	@Test
	public void testIsPaidByVillagerLeader() {
		assertFalse(profession.isPaidByVillagerLeader());
	}

	@Test
	public void testAvoidEnemies() {
		assertTrue(profession.avoidEnemies());
	}
	
	@Test
	public void testGetSellItems() {
		List<Item> item = new ArrayList<Item>();
		item.add(Item.BERRIES);
		item.add(Item.MEAT);
		item.add(Item.WINE);
		item.add(Item.LEATHER);
		assertEquals(profession.getSellItems(), item);
	}
}