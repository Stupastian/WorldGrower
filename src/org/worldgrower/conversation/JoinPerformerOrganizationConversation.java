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
import org.worldgrower.attribute.IdList;
import org.worldgrower.goal.GroupPropertyUtils;
import org.worldgrower.history.HistoryItem;
import org.worldgrower.text.Text;

public class JoinPerformerOrganizationConversation implements Conversation {

	private static final int YES = 0;
	private static final int NO = 1;
	
	@Override
	public Response getReplyPhrase(ConversationContext conversationContext) {
		WorldObject performer = conversationContext.getPerformer();
		WorldObject target = conversationContext.getTarget();
		
		final int replyId;
		int relationshipValue = target.getProperty(Constants.RELATIONSHIPS).getValue(performer);
		if (relationshipValue >= 0) {
			replyId = YES;
		} else {
			replyId = NO;
		}
		
		return getReply(getReplyPhrases(conversationContext), replyId);
	}

	@Override
	public List<Question> getQuestionPhrases(WorldObject performer, WorldObject target, HistoryItem questionHistoryItem, WorldObject subjectWorldObject, World world) {
		IdList performerOrganizations = performer.getProperty(Constants.GROUP);
		IdList targetOrganizations = target.getProperty(Constants.GROUP);
		List<Integer> organizationsToJoin = performerOrganizations.getIdsNotPresentInOther(targetOrganizations);
		
		List<Question> questions = new ArrayList<>();
		for(int organizationId : organizationsToJoin) {
			WorldObject organization = world.findWorldObjectById(organizationId);
			if (GroupPropertyUtils.hasAuthorityToAddMembers(performer, organization, world)) {
				if (GroupPropertyUtils.canJoinOrganization(target, organization)) {
					questions.add(new Question(organization, Text.QUESTION_JOIN_PERFORMER_ORG.get(organization.getProperty(Constants.NAME))));
				}
			}
		}
		
		return questions;
	}
	
	@Override
	public List<Response> getReplyPhrases(ConversationContext conversationContext) {
		WorldObject organization = conversationContext.getSubject();
		
		return Arrays.asList(
			new Response(YES, Text.ANSWER_JOIN_PERFORMER_ORG_YES.get(organization.getProperty(Constants.NAME))),
			new Response(NO, Text.ANSWER_JOIN_PERFORMER_ORG_NO.get())
			);
	}
	
	@Override
	public boolean isConversationAvailable(WorldObject performer, WorldObject target, WorldObject subject, World world) {
		return true;
	}
	
	@Override
	public void handleResponse(int replyIndex, ConversationContext conversationContext) {
		if (replyIndex == YES) {
			WorldObject target = conversationContext.getTarget();
			WorldObject organization = conversationContext.getSubject();
			World world = conversationContext.getWorld();
			
			WorldObject oldOrganization = GroupPropertyUtils.findProfessionOrganization(target, world);
			if (oldOrganization != null) {
				target.getProperty(Constants.GROUP).remove(oldOrganization);
			}
			
			target.getProperty(Constants.GROUP).add(organization);
		}
	}
	
	@Override
	public String getDescription(WorldObject performer, WorldObject target, World world) {
		return "talking about joining an organization";
	}
}
