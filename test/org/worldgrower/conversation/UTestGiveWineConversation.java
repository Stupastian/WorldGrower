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
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.attribute.IdList;
import org.worldgrower.attribute.IdRelationshipMap;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.generator.Item;
import org.worldgrower.goal.GroupPropertyUtils;

public class UTestGiveWineConversation {

	private final GiveItemConversation conversation = Conversations.GIVE_WINE_CONVERSATION;
	
	@Test
	public void testGetReplyPhrases() {
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.RELATIONSHIPS, new IdRelationshipMap());
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.RELATIONSHIPS, new IdRelationshipMap());
		
		ConversationContext context = new ConversationContext(performer, target, null, null, null, 0);
		List<Response> replyPhrases = conversation.getReplyPhrases(context);
		assertEquals(3, replyPhrases.size());
		assertEquals("Thanks", replyPhrases.get(0).getResponsePhrase(DefaultConversationFormatter.FORMATTER));
		assertEquals("Get lost", replyPhrases.get(1).getResponsePhrase(DefaultConversationFormatter.FORMATTER));
	}
	
	@Test
	public void testGetReplyPhrase() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.RELATIONSHIPS, new IdRelationshipMap());
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.RELATIONSHIPS, new IdRelationshipMap());
		WorldObject organization = GroupPropertyUtils.create(null, "OrgName", world);
		
		ConversationContext context = new ConversationContext(performer, target, organization, null, world, 0);
		assertEquals(0, conversation.getReplyPhrase(context).getId());
	}
	
	@Test
	public void testGetQuestionPhrases() {
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.GROUP, new IdList());
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.GROUP, new IdList());
		
		List<Question> questions = conversation.getQuestionPhrases(performer, target, null, null, null);
		assertEquals(1, questions.size());
		assertEquals("Would you like to have some wine?", questions.get(0).getQuestionPhrase());
	}
	
	@Test
	public void testHandleResponse0Quantities() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createSkilledWorldObject(1, Constants.RELATIONSHIPS, new IdRelationshipMap());
		WorldObject target = TestUtils.createSkilledWorldObject(2, Constants.RELATIONSHIPS, new IdRelationshipMap());
		
		performer.setProperty(Constants.INVENTORY, new WorldObjectContainer());
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.WINE.generate(1f), 20);
		target.setProperty(Constants.INVENTORY, new WorldObjectContainer());
		
		ConversationContext context = new ConversationContext(performer, target, null, null, world, 0);
		
		conversation.handleResponse(0, context);
		assertEquals(15, performer.getProperty(Constants.INVENTORY).getQuantityFor(Constants.WINE));
		assertEquals(5, target.getProperty(Constants.INVENTORY).getQuantityFor(Constants.WINE));
	}
	
	@Test
	public void testHandleResponse0() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createSkilledWorldObject(1, Constants.RELATIONSHIPS, new IdRelationshipMap());
		WorldObject target = TestUtils.createSkilledWorldObject(2, Constants.RELATIONSHIPS, new IdRelationshipMap());
		
		performer.setProperty(Constants.INVENTORY, new WorldObjectContainer());
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.WINE.generate(1f), 5);
		target.setProperty(Constants.INVENTORY, new WorldObjectContainer());
		
		ConversationContext context = new ConversationContext(performer, target, null, null, world, 0);
		
		conversation.handleResponse(0, context);
		assertEquals(10, performer.getProperty(Constants.RELATIONSHIPS).getValue(target));
		assertEquals(10, target.getProperty(Constants.RELATIONSHIPS).getValue(performer));
		assertEquals(0, performer.getProperty(Constants.INVENTORY).getQuantityFor(Constants.WINE));
		assertEquals(5, target.getProperty(Constants.INVENTORY).getQuantityFor(Constants.WINE));
	}

	@Test
	public void testHandleResponse1() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createSkilledWorldObject(1, Constants.RELATIONSHIPS, new IdRelationshipMap());
		WorldObject target = TestUtils.createSkilledWorldObject(2, Constants.RELATIONSHIPS, new IdRelationshipMap());
		
		ConversationContext context = new ConversationContext(performer, target, null, null, world, 0);
		
		conversation.handleResponse(1, context);
		assertEquals(-20, performer.getProperty(Constants.RELATIONSHIPS).getValue(target));
		assertEquals(-20, target.getProperty(Constants.RELATIONSHIPS).getValue(performer));
	}
	
	@Test
	public void testHandleResponse2() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createSkilledWorldObject(1, Constants.RELATIONSHIPS, new IdRelationshipMap());
		WorldObject target = TestUtils.createSkilledWorldObject(2, Constants.RELATIONSHIPS, new IdRelationshipMap());
		
		performer.setProperty(Constants.INVENTORY, new WorldObjectContainer());
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.WINE.generate(1f), 5);
		target.setProperty(Constants.INVENTORY, new WorldObjectContainer());
		
		ConversationContext context = new ConversationContext(performer, target, null, null, world, 0);
		
		conversation.handleResponse(2, context);
		assertEquals(10, performer.getProperty(Constants.RELATIONSHIPS).getValue(target));
		assertEquals(10, target.getProperty(Constants.RELATIONSHIPS).getValue(performer));
		assertEquals(0, performer.getProperty(Constants.INVENTORY).getQuantityFor(Constants.WINE));
		assertEquals(5, target.getProperty(Constants.INVENTORY).getQuantityFor(Constants.WINE));
	}
	
	@Test
	public void testIsConversationAvailable() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createSkilledWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());
		WorldObject target = TestUtils.createSkilledWorldObject(2, Constants.INVENTORY, new WorldObjectContainer());
		
		assertEquals(false, conversation.isConversationAvailable(performer, target, null, world));
		
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.WINE.generate(1f), 5);
		assertEquals(true, conversation.isConversationAvailable(performer, target, null, world));
	}
}
