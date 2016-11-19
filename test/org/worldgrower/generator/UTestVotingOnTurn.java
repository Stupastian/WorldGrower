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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.DoNothingWorldOnTurn;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.condition.WorldStateChangedListeners;
import org.worldgrower.goal.GroupPropertyUtils;
import org.worldgrower.profession.Professions;

public class UTestVotingOnTurn {

	@Test
	public void testOnTurnOfTurnCounter() {
		World world = new WorldImpl(10, 10, null, new DoNothingWorldOnTurn());
		
		int votingBoxId = BuildingGenerator.generateVotingBox(5, 5, world);
		WorldObject votingBox = world.findWorldObjectById(votingBoxId);
		votingBox.setProperty(Constants.ORGANIZATION_ID, 1);
		createVillagersOrganization(world);
		
		assertEquals(0, votingBox.getProperty(Constants.TURN_COUNTER).intValue());
		
		votingBox.onTurn(world, new WorldStateChangedListeners());
		assertEquals(1, votingBox.getProperty(Constants.TURN_COUNTER).intValue());
	}
	
	private WorldObject createVillagersOrganization(World world) {
		world.generateUniqueId(); world.generateUniqueId(); world.generateUniqueId(); 
		WorldObject organization = GroupPropertyUtils.createVillagersOrganization(world);
		organization.setProperty(Constants.ID, 1);
		world.addWorldObject(organization);
		return organization;
	}
	
	@Test
	public void testOnTurnOfTurnCounterElectionDone() {
		World world = new WorldImpl(10, 10, null, new DoNothingWorldOnTurn());
		WorldObject candidate1 = TestUtils.createIntelligentWorldObject(2, "candidate1");
		WorldObject candidate2 = TestUtils.createIntelligentWorldObject(3, "candidate2");
		
		world.addWorldObject(candidate1);
		world.addWorldObject(candidate2);
		
		int votingBoxId = BuildingGenerator.generateVotingBox(5, 5, world);
		WorldObject votingBox = world.findWorldObjectById(votingBoxId);
		WorldObject organization = GroupPropertyUtils.createProfessionOrganization(null, "TestOrg", Professions.FARMER_PROFESSION, world);
		votingBox.setProperty(Constants.ORGANIZATION_ID, organization.getProperty(Constants.ID));
		
		votingBox.setProperty(Constants.TURN_COUNTER, 3000);
		votingBox.getProperty(Constants.VOTES).incrementValue(candidate1, 5);
		votingBox.getProperty(Constants.VOTES).incrementValue(candidate2, 7);
		
		votingBox.onTurn(world, new WorldStateChangedListeners());
		assertEquals(3, organization.getProperty(Constants.ORGANIZATION_LEADER_ID).intValue());
	}
	
	@Test
	public void testCalculatePercentageWon() {
		World world = new WorldImpl(10, 10, null, new DoNothingWorldOnTurn());
		WorldObject candidate1 = TestUtils.createIntelligentWorldObject(2, "candidate1");
		WorldObject candidate2 = TestUtils.createIntelligentWorldObject(3, "candidate2");
		
		int votingBoxId = BuildingGenerator.generateVotingBox(5, 5, world);
		WorldObject votingBox = world.findWorldObjectById(votingBoxId);
		
		votingBox.getProperty(Constants.VOTES).incrementValue(candidate1, 1);
		assertEquals(100, new VotingBoxOnTurn().calculatePercentageWon(votingBox, 2));
		
		votingBox.getProperty(Constants.VOTES).incrementValue(candidate2, 3);
		assertEquals(75, new VotingBoxOnTurn().calculatePercentageWon(votingBox, 3));
	}

}