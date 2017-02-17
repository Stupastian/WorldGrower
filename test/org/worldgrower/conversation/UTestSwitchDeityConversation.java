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
package org.worldgrower.conversation;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.DefaultConversationFormatter;
import org.worldgrower.OperationInfo;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.deity.Deity;
import org.worldgrower.goal.Goals;
import org.worldgrower.history.Turn;

public class UTestSwitchDeityConversation {

	private final SwitchDeityConversation conversation = Conversations.SWITCH_DEITY_CONVERSATION;
	
	@Test
	public void testGetReplyPhrases() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.DEITY, Deity.HADES);
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.DEITY, Deity.DEMETER);
		
		ConversationContext context = new ConversationContext(performer, target, null, null, world, 0);
		List<Response> replyPhrases = conversation.getReplyPhrases(context);
		assertEquals(3, replyPhrases.size());
		assertEquals("Yes, I'll worship Hades instead of Demeter.", replyPhrases.get(0).getResponsePhrase(DefaultConversationFormatter.FORMATTER));
		assertEquals("No", replyPhrases.get(1).getResponsePhrase(DefaultConversationFormatter.FORMATTER));
		assertEquals("Get lost", replyPhrases.get(2).getResponsePhrase(DefaultConversationFormatter.FORMATTER));
	}

	@Test
	public void testGetReplyPhrase() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.DEITY, Deity.HADES);
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.DEITY, Deity.DEMETER);
		
		ConversationContext context = new ConversationContext(performer, target, null, null, world, 0);
		assertEquals(1, conversation.getReplyPhrase(context).getId());
		
		target.getProperty(Constants.RELATIONSHIPS).incrementValue(performer, 1000);
		assertEquals(0, conversation.getReplyPhrase(context).getId());
	}
	
	@Test
	public void testGetQuestionPhrases() {
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.DEITY, Deity.HADES);
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.DEITY, Deity.DEMETER);
		
		List<Question> questions = conversation.getQuestionPhrases(performer, target, null, null, null);
		assertEquals(true, questions.size() > 0);
		assertEquals("Would you like to worship Hades instead of your current deity?", questions.get(0).getQuestionPhrase(DefaultConversationFormatter.FORMATTER));
	}
	
	@Test
	public void testHandleResponse0() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.DEITY, Deity.HADES);
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.DEITY, Deity.DEMETER);
		
		ConversationContext context = new ConversationContext(performer, target, null, null, world, 0);
		
		conversation.handleResponse(0, context);
		assertEquals(50, performer.getProperty(Constants.RELATIONSHIPS).getValue(target));
		assertEquals(50, target.getProperty(Constants.RELATIONSHIPS).getValue(performer));
	}
	
	@Test
	public void testHandleResponse1() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.DEITY, Deity.HADES);
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.DEITY, Deity.DEMETER);
		
		ConversationContext context = new ConversationContext(performer, target, null, null, world, 0);
		
		conversation.handleResponse(1, context);
		assertEquals(-50, performer.getProperty(Constants.RELATIONSHIPS).getValue(target));
		assertEquals(-50, target.getProperty(Constants.RELATIONSHIPS).getValue(performer));
	}
	
	@Test
	public void testHandleResponse2() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.DEITY, Deity.HADES);
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.DEITY, Deity.DEMETER);
		
		ConversationContext context = new ConversationContext(performer, target, null, null, world, 0);
		
		conversation.handleResponse(2, context);
		assertEquals(-100, performer.getProperty(Constants.RELATIONSHIPS).getValue(target));
		assertEquals(-100, target.getProperty(Constants.RELATIONSHIPS).getValue(performer));
	}
	
	@Test
	public void testIsConversationAvailable() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.DEITY, Deity.HADES);
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.DEITY, Deity.DEMETER);
		
		assertEquals(true, conversation.isConversationAvailable(performer, target, null, world));
		
		target.setProperty(Constants.DEITY, Deity.HADES);
		assertEquals(false, conversation.isConversationAvailable(performer, target, null, world));
	}
	
	@Test
	public void testGetPreviousResponseIds() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.DEITY, Deity.HADES);
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Goals.CHILDREN_GOAL);
		target.setProperty(Constants.DEITY, Deity.DEMETER);
		
		assertEquals(false, conversation.previousAnswerWasGetLost(performer, target, world));
	
		int[] args = Conversations.createArgs(conversation);
		target.getProperty(Constants.RELATIONSHIPS).incrementValue(performer, -1000);
		Actions.TALK_ACTION.execute(performer, target, args, world);
		world.getHistory().actionPerformed(new OperationInfo(performer, target, args, Actions.TALK_ACTION), new Turn());
		
		assertEquals(true, conversation.previousAnswerWasGetLost(performer, target, world));
	}
}
