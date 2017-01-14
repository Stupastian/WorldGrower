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
package org.worldgrower.goal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.worldgrower.AssertUtils;
import org.worldgrower.Constants;
import org.worldgrower.OperationInfo;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.attribute.IdList;
import org.worldgrower.attribute.IdRelationshipMap;
import org.worldgrower.attribute.KnowledgeMap;
import org.worldgrower.conversation.Conversations;
import org.worldgrower.creaturetype.CreatureType;
import org.worldgrower.history.Turn;

public class UTestSocializeGoal {

	private final SocializeGoal goal = Goals.SOCIALIZE_GOAL;
	
	@Test
	public void testIsSocializeTargetForPerformer() {
		WorldObject performer = TestUtils.createIntelligentWorldObject(0, Constants.GROUP, new IdList());
		WorldObject target = TestUtils.createIntelligentWorldObject(1, Constants.GROUP, new IdList());
		
		assertEquals(false, SocializeGoal.isSocializeTargetForPerformer(performer, target));
		
		performer.getProperty(Constants.GROUP).add(2);
		target.getProperty(Constants.GROUP).add(2);
		assertEquals(true, SocializeGoal.isSocializeTargetForPerformer(performer, target));
	}
	
	@Test
	public void testIsFirstTimeSocializeTargetForPerformer() {
		WorldObject performer = TestUtils.createIntelligentWorldObject(0, Constants.RELATIONSHIPS, new IdRelationshipMap());
		WorldObject target = TestUtils.createIntelligentWorldObject(1, Constants.RELATIONSHIPS, new IdRelationshipMap());
		
		performer.getProperty(Constants.GROUP).add(6);
		target.getProperty(Constants.GROUP).add(6);
		
		assertEquals(true, goal.isFirstTimeSocializeTargetForPerformer(performer, target));
		
		performer.getProperty(Constants.RELATIONSHIPS).incrementValue(target, 5);
		assertEquals(false, goal.isFirstTimeSocializeTargetForPerformer(performer, target));
	}
	
	@Test
	public void testIsTargetForShareKnowledgeConversation() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(0, Constants.KNOWLEDGE_MAP, new KnowledgeMap());
		WorldObject target = TestUtils.createIntelligentWorldObject(1, Constants.KNOWLEDGE_MAP, new KnowledgeMap());
		
		assertEquals(false, goal.isTargetForShareKnowledgeConversation(performer, target, world));
		
		performer.getProperty(Constants.KNOWLEDGE_MAP).addKnowledge(2, Constants.FOOD, 500);
		assertEquals(true, goal.isTargetForShareKnowledgeConversation(performer, target, world));
	}
	
	@Test
	public void testGetPreviousResponseIds() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(0, Constants.KNOWLEDGE_MAP, new KnowledgeMap());
		WorldObject target = TestUtils.createIntelligentWorldObject(1, Constants.KNOWLEDGE_MAP, new KnowledgeMap());
		
		assertEquals(false, Conversations.SHARE_KNOWLEDGE_CONVERSATION.previousAnswerWasGetLost(performer, target, world));
		
		world.getHistory().setNextAdditionalValue(1);
		world.getHistory().actionPerformed(new OperationInfo(performer, target, Conversations.createArgs(Conversations.SHARE_KNOWLEDGE_CONVERSATION), Actions.TALK_ACTION), new Turn());
		
		assertEquals(true, Conversations.SHARE_KNOWLEDGE_CONVERSATION.previousAnswerWasGetLost(performer, target, world));
	}
	
	@Test
	public void testGetShareKnowledgeOperationInfoNoTarget() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(0, Constants.KNOWLEDGE_MAP, new KnowledgeMap());
		
		assertEquals(null, goal.getShareKnowledgeOperationInfo(performer, world));
	}
	
	@Test
	public void testGetShareKnowledgeOperationInfoShareToTarget() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(0, Constants.KNOWLEDGE_MAP, new KnowledgeMap());
		WorldObject target = TestUtils.createIntelligentWorldObject(1, Constants.KNOWLEDGE_MAP, new KnowledgeMap());

		performer.getProperty(Constants.GROUP).add(6);
		target.getProperty(Constants.GROUP).add(6);
		
		world.addWorldObject(performer);
		world.addWorldObject(target);
		
		performer.getProperty(Constants.RELATIONSHIPS).incrementValue(target, 5);
		performer.getProperty(Constants.KNOWLEDGE_MAP).addKnowledge(0, Constants.FOOD, 500);
		
		assertEquals(Actions.TALK_ACTION, goal.getShareKnowledgeOperationInfo(performer, world).getManagedOperation());
		AssertUtils.assertConversation(goal.getShareKnowledgeOperationInfo(performer, world), Conversations.SHARE_KNOWLEDGE_CONVERSATION);
	}
	
	@Test
	public void testIsGoalMet() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, "performer");
		
		assertEquals(false, goal.isGoalMet(performer, world));
		
		performer.increment(Constants.SOCIAL, 1000);
		assertEquals(true, goal.isGoalMet(performer, world));
	}
	
	@Test
	public void testCalculateGoalNull() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, "performer");
		
		assertEquals(null, goal.calculateGoal(performer, world));
	}
	
	@Test
	public void testCalculateGoalFirstConversation() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, "performer");
		WorldObject target = TestUtils.createIntelligentWorldObject(2, "target");
		
		performer.setProperty(Constants.GROUP, new IdList().add(7));
		target.setProperty(Constants.GROUP, new IdList().add(7));
		
		performer.setProperty(Constants.CREATURE_TYPE, CreatureType.HUMAN_CREATURE_TYPE);
		target.setProperty(Constants.CREATURE_TYPE, CreatureType.HUMAN_CREATURE_TYPE);
		
		world.addWorldObject(performer);
		world.addWorldObject(target);
		
		assertEquals(Actions.TALK_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
		AssertUtils.assertConversation(goal.calculateGoal(performer, world), Conversations.NAME_CONVERSATION);
	}
}
