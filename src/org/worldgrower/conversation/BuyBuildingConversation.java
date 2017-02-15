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

import java.util.Arrays;
import java.util.List;

import org.worldgrower.Constants;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.attribute.BuildingType;
import org.worldgrower.generator.Item;
import org.worldgrower.goal.HousePropertyUtils;
import org.worldgrower.history.HistoryItem;
import org.worldgrower.text.Text;
import org.worldgrower.util.SentenceUtils;

public class BuyBuildingConversation implements Conversation {

	private final BuildingType buildingType;
	
	private static final int YES = 0;
	private static final int NO = 1;

	public BuyBuildingConversation(BuildingType buildingType) {
		this.buildingType = buildingType;
	}

	@Override
	public Response getReplyPhrase(ConversationContext conversationContext) {
		int replyId = YES;
		return getReply(getReplyPhrases(conversationContext), replyId);
	}

	@Override
	public List<Question> getQuestionPhrases(WorldObject performer, WorldObject target, HistoryItem questionHistoryItem, WorldObject subjectWorldObject, World world) {
		String description = buildingType.getDescription();
		return Arrays.asList(new Question(null, Text.QUESTION_BUY_BUILDING.get(SentenceUtils.getArticle(description), description)));
	}
	
	@Override
	public List<Response> getReplyPhrases(ConversationContext conversationContext) {
		return Arrays.asList(
			new Response(YES, Text.ANSWER_BUY_BUILDING_YES),
			new Response(NO, Text.ANSWER_BUY_BUILDING_NO)
			);
	}

	@Override
	public boolean isConversationAvailable(WorldObject performer, WorldObject target, WorldObject subject, World world) {
		return HousePropertyUtils.hasBuildingForSale(target, buildingType, world);
	}

	@Override
	public void handleResponse(int replyIndex, ConversationContext conversationContext) {
		WorldObject performer = conversationContext.getPerformer();
		WorldObject target = conversationContext.getTarget();
		World world = conversationContext.getWorld();
		
		if (replyIndex == YES) {
			WorldObject building = HousePropertyUtils.getBuildingForSale(target, buildingType, world);
			Item buildingTypeItem = Item.mapBuildingTypeToItem(buildingType);
			int price = target.getProperty(Constants.PRICES).getPrice(buildingTypeItem);
			
			performer.getProperty(Constants.BUILDINGS).add(building, buildingType);
			target.getProperty(Constants.BUILDINGS).remove(building);
			building.setProperty(Constants.SELLABLE, Boolean.FALSE);
			
			target.increment(Constants.GOLD, price);
			performer.increment(Constants.GOLD, -price);
		}
	}
	
	@Override
	public String getDescription(WorldObject performer, WorldObject target, World world) {
		return "talking about selling a " + buildingType.getDescription();
	}

	public BuildingType getBuildingType() {
		return buildingType;
	}
}
