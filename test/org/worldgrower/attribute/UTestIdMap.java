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
package org.worldgrower.attribute;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;

public class UTestIdMap {
	
	@Test
	public void testIncrement() {
		IdMap idMap = new IdToIntegerMap();
		assertEquals(0, idMap.getValue(6));
		
		idMap.incrementValue(6, 2);
		assertEquals(2, idMap.getValue(6));
	}
	
	@Test
	public void testFindBestId() {
		IdMap idMap = new IdToIntegerMap();
		World world = new WorldImpl(1, 1, null, null);
		
		WorldObject person1 = TestUtils.createIntelligentWorldObject(1, Constants.GOLD, 10);
		WorldObject person2 = TestUtils.createIntelligentWorldObject(2, Constants.GOLD, 0);
		
		world.addWorldObject(person1);
		world.addWorldObject(person2);
		
		idMap.incrementValue(person1, 60);
		idMap.incrementValue(person2, 80);
		
		assertEquals(2, idMap.findBestId(w -> true, world));
		assertEquals(1, idMap.findBestId(w -> w.getProperty(Constants.GOLD) > 0, world));
	}
	
	@Test
	public void testFindWorstId() {
		IdMap idMap = new IdToIntegerMap();
		World world = new WorldImpl(1, 1, null, null);
		
		WorldObject person1 = TestUtils.createIntelligentWorldObject(1, Constants.GOLD, 10);
		WorldObject person2 = TestUtils.createIntelligentWorldObject(2, Constants.GOLD, 0);
		
		world.addWorldObject(person1);
		world.addWorldObject(person2);
		
		idMap.incrementValue(person1, 60);
		idMap.incrementValue(person2, 80);
		
		assertEquals(1, idMap.findWorstId(world));
	}
	
	@Test
	public void testFindBestIdWithComparator() {
		IdMap idMap = new IdToIntegerMap();
		World world = new WorldImpl(1, 1, null, null);
		
		WorldObject person1 = TestUtils.createIntelligentWorldObject(1, Constants.GOLD, 10);
		WorldObject person2 = TestUtils.createIntelligentWorldObject(2, Constants.GOLD, 0);
		
		world.addWorldObject(person1);
		world.addWorldObject(person2);
		
		idMap.incrementValue(person1, 60);
		idMap.incrementValue(person2, 80);
		
		assertEquals(1, idMap.findBestId(w -> true,  new WorldObjectComparator(), world));
		assertEquals(1, idMap.findBestId(w -> w.getProperty(Constants.GOLD) > 0,  new WorldObjectComparator(), world));
		
		person2.setProperty(Constants.GOLD, 100);
		assertEquals(2, idMap.findBestId(w -> true,  new WorldObjectComparator(), world));
	}
	
	private static class WorldObjectComparator implements Comparator<WorldObject> {

		@Override
		public int compare(WorldObject o1, WorldObject o2) {
			return Integer.compare(o1.getProperty(Constants.GOLD), o2.getProperty(Constants.GOLD));
		}
		
	}
	
	@Test
	public void testGetIds() {
		IdMap idMap = new IdToIntegerMap();
		WorldObject person1 = TestUtils.createWorldObject(1, "Test1");
		WorldObject person2 = TestUtils.createWorldObject(2, "Test2");
		
		idMap.incrementValue(person1, 60);
		idMap.incrementValue(person2, 80);
		
		assertEquals(2, idMap.getIds().size());
		assertEquals(true, idMap.getIds().contains(1));
		assertEquals(true, idMap.getIds().contains(2));
	}
	
	@Test
	public void testGetIdsWithoutTarget() {
		IdMap idMap = new IdToIntegerMap();
		WorldObject person1 = TestUtils.createWorldObject(1, "Test1");
		WorldObject person2 = TestUtils.createWorldObject(2, "Test2");
		
		idMap.incrementValue(person1, 60);
		idMap.incrementValue(person2, 80);
		
		assertEquals(Arrays.asList(2), idMap.getIdsWithoutTarget(person1));
		assertEquals(Arrays.asList(1), idMap.getIdsWithoutTarget(person2));
	}
	
	@Test
	public void testIdRelationshipMap() {
		IdMap idMap = new IdRelationshipMap();
		idMap.incrementValue(1, 2000);
		
		assertEquals(1000, idMap.getValue(1));
		assertEquals(0, idMap.getValue(90));
	}
	
	@Test
	public void testRemoveId() {
		IdMap idMap = new IdRelationshipMap();
		WorldObject person = TestUtils.createIntelligentWorldObject(1, Constants.RELATIONSHIPS, idMap);
		idMap.incrementValue(6, 2);
		WorldObject person2 = TestUtils.createWorldObject(1, "Test2");
		
		assertEquals(2, idMap.getValue(6));
		idMap.remove(person, Constants.RELATIONSHIPS, 6);
		assertEquals(0, idMap.getValue(6));
		assertEquals(false, idMap.contains(person2));
	}
	
	@Test
	public void testRemoveWorldObject() {
		IdMap idMap = new IdRelationshipMap();
		WorldObject person = TestUtils.createWorldObject(6, "Test");
		idMap.incrementValue(person, 10);
		assertEquals(10, idMap.getValue(person));
		
		idMap.remove(person);
		assertEquals(0, idMap.getValue(person));
		assertEquals(false, idMap.contains(person));
	}
}
