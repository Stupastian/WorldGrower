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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.worldgrower.Constants;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.generator.Item;
import org.worldgrower.goal.CattlePropertyUtils;
import org.worldgrower.goal.RelationshipPropertyUtils;
import org.worldgrower.history.HistoryItem;
import org.worldgrower.profession.Professions;
import org.worldgrower.text.Text;

public class StopSellingConversation implements Conversation {

	private static final int YES = 0;
	private static final int NO = 1;
	
	@Override
	public Response getReplyPhrase(ConversationContext conversationContext) {
		WorldObject performer = conversationContext.getPerformer();
		WorldObject target = conversationContext.getTarget();
		final int replyId;
		if (target.getProperty(Constants.RELATIONSHIPS).getValue(performer) > 750) {
			replyId = YES;
		} else {
			replyId = NO;
		}
		
		return getReply(getReplyPhrases(conversationContext), replyId);
	}

	@Override
	public List<Question> getQuestionPhrases(WorldObject performer, WorldObject target, HistoryItem questionHistoryItem, WorldObject subjectWorldObject, World world) {
		List<Question> questions = new ArrayList<>();
		List<Item> items = target.getProperty(Constants.ITEMS_SOLD).getItems();
		
		for(Item item : items) {
			String itemDescription = getItemDescription(item);
			questions.add(new Question(null, Text.QUESTION_STOP_SELLING.get(itemDescription), item.ordinal()));
		}
		
		return questions;
	}
	
	@Override
	public List<Response> getReplyPhrases(ConversationContext conversationContext) {
		int itemId = conversationContext.getAdditionalValue();
		Item item = Item.value(itemId);
		
		String itemDescription = getItemDescription(item);
		return Arrays.asList(
			new Response(YES, Text.ANSWER_STOP_SELLING_YES.get(itemDescription)),
			new Response(NO, Text.ANSWER_STOP_SELLING_NO.get())
			);
	}

	private String getItemDescription(Item item) {
		String itemDescription = item.getDescription();
		if (!itemDescription.endsWith("s")) {
			itemDescription = itemDescription + "s";
		}
		return itemDescription;
	}
	
	@Override
	public boolean isConversationAvailable(WorldObject performer, WorldObject target, WorldObject subject, World world) {
		return !target.getProperty(Constants.ITEMS_SOLD).isEmpty();
	}
	
	@Override
	public void handleResponse(int replyIndex, ConversationContext conversationContext) {
		WorldObject performer = conversationContext.getPerformer();
		WorldObject target = conversationContext.getTarget();
		World world = conversationContext.getWorld();
		
		if (replyIndex == YES) {
			RelationshipPropertyUtils.changeRelationshipValue(performer, target, 50, Actions.TALK_ACTION, Conversations.createArgs(this), world);
			//TODO: stop selling should be permanent
			if (target.getProperty(Constants.PROFESSION) == Professions.BUTCHER_PROFESSION) {
				unclaimCattle(target, world);
			}
			target.setProperty(Constants.PROFESSION, null);
			
			
		} else if (replyIndex == NO) {
			RelationshipPropertyUtils.changeRelationshipValue(performer, target, -100, Actions.TALK_ACTION, Conversations.createArgs(this), world);
		}
	}
	
	private void unclaimCattle(WorldObject performer, World world) {
		for(WorldObject ownedCattle : CattlePropertyUtils.getOwnedCattle(performer, world)) {
			ownedCattle.setProperty(Constants.CATTLE_OWNER_ID, null);
		}
		
	}

	@Override
	public String getDescription(WorldObject performer, WorldObject target, World world) {
		return "talking about selling";
	}
}
