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
package org.worldgrower.text;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.worldgrower.conversation.Conversation;
import org.worldgrower.conversation.Conversations;
import org.worldgrower.conversation.InterceptedConversation;

public enum TextId {
	QUESTION_ARENA_PAY_CHECK(Conversations.ARENA_FIGHTER_PAY_CHECK_CONVERSATION),
	ANSWER_ARENA_PAY_CHECK_YES(Conversations.ARENA_FIGHTER_PAY_CHECK_CONVERSATION),
	ANSWER_ARENA_PAY_CHECK_NO(Conversations.ARENA_FIGHTER_PAY_CHECK_CONVERSATION),
	QUESTION_ASK_GOAL(Conversations.ASK_GOAL_CONVERSATION), 
	ANSWER_ASK_GOAL_YES(Conversations.ASK_GOAL_CONVERSATION), 
	ANSWER_ASK_GOAL_EXPLAIN(Conversations.ASK_GOAL_CONVERSATION),
	ANSWER_ASK_GOAL_NO(Conversations.ASK_GOAL_CONVERSATION),
	QUESTION_ASSASSINATE_TARGET(Conversations.ASSASSINATE_TARGET_CONVERSATION),
	ANSWER_ASSASSINATE_TARGET_YES(Conversations.ASSASSINATE_TARGET_CONVERSATION),
	ANSWER_ASSASSINATE_TARGET_NO(Conversations.ASSASSINATE_TARGET_CONVERSATION),
	QUESTION_ARENA_FIGHTER(Conversations.BECOME_ARENA_FIGHTER_CONVERSATION),
	ANSWER_ARENA_FIGHTER_YES(Conversations.BECOME_ARENA_FIGHTER_CONVERSATION),
	ANSWER_ARENA_FIGHTER_NO(Conversations.BECOME_ARENA_FIGHTER_CONVERSATION),
	QUESTION_BRAWL_GOLD(Conversations.BRAWL_CONVERSATION),
	QUESTION_BRAWL(Conversations.BRAWL_CONVERSATION),
	ANSWER_BRAWL_YES(Conversations.BRAWL_CONVERSATION),
	ANSWER_BRAWL_NO(Conversations.BRAWL_CONVERSATION),
	ANSWER_BRAWL_LATER(Conversations.BRAWL_CONVERSATION),
	ANSWER_BRAWL_NOT_ENOUGH_GOLD(Conversations.BRAWL_CONVERSATION),
	ANSWER_BRAWL_GET_LOST(Conversations.BRAWL_CONVERSATION),
	QUESTION_BREAKUP(Conversations.BREAKUP_WITH_MATE_CONVERSATION),
	ANSWER_BREAKUP_YES(Conversations.BREAKUP_WITH_MATE_CONVERSATION),
	QUESTION_BUY_BUILDING(Conversations.BUY_HOUSE_CONVERSATION),
	ANSWER_BUY_BUILDING_YES(Conversations.BUY_HOUSE_CONVERSATION),
	ANSWER_BUY_BUILDING_NO(Conversations.BUY_HOUSE_CONVERSATION),
	QUESTION_COLLECT_BOUNTY(Conversations.COLLECT_BOUNTY_FROM_THIEVES_CONVERSATION),
	ANSWER_COLLECT_BOUNTY_PAY(Conversations.COLLECT_BOUNTY_FROM_THIEVES_CONVERSATION),
	ANSWER_COLLECT_BOUNTY_JAIL(Conversations.COLLECT_BOUNTY_FROM_THIEVES_CONVERSATION),
	ANSWER_COLLECT_BOUNTY_RESIST(Conversations.COLLECT_BOUNTY_FROM_THIEVES_CONVERSATION),
	QUESTION_COLLECT_PAYCHECK(Conversations.COLLECT_PAY_CHECK_CONVERSATION),
	ANSWER_COLLECT_PAYCHECK_YES(Conversations.COLLECT_PAY_CHECK_CONVERSATION),
	ANSWER_COLLECT_PAYCHECK_NO(Conversations.COLLECT_PAY_CHECK_CONVERSATION),
	QUESTION_COLLECT_TAXES(Conversations.COLLECT_TAXES_CONVERSATION),
	ANSWER_COLLECT_TAXES_YES(Conversations.COLLECT_TAXES_CONVERSATION),
	ANSWER_COLLECT_TAXES_NO(Conversations.COLLECT_TAXES_CONVERSATION),
	QUESTION_COMPLIMENT_STRONG(Conversations.COMPLIMENT_CONVERSATION),
	QUESTION_COMPLIMENT_HANDSOME(Conversations.COMPLIMENT_CONVERSATION),
	ANSWER_COMPLIMENT_THANKS(Conversations.COMPLIMENT_CONVERSATION),
	ANSWER_COMPLIMENT_STOP(Conversations.COMPLIMENT_CONVERSATION),
	ANSWER_COMPLIMENT_GETLOST(Conversations.COMPLIMENT_CONVERSATION),
	QUESTION_CURE_DISEASE(Conversations.CURE_DISEASE_CONVERSATION),
	ANSWER_CURE_DISEASE_YES(Conversations.CURE_DISEASE_CONVERSATION),
	ANSWER_CURE_DISEASE_NO(Conversations.CURE_DISEASE_CONVERSATION),
	ANSWER_CURE_DISEASE_GETLOST(Conversations.CURE_DISEASE_CONVERSATION),
	QUESTION_CURE_POISON(Conversations.CURE_POISON_CONVERSATION),
	ANSWER_CURE_POISON_YES(Conversations.CURE_POISON_CONVERSATION),
	ANSWER_CURE_POISON_NO(Conversations.CURE_POISON_CONVERSATION),
	ANSWER_CURE_POISON_GETLOST(Conversations.CURE_POISON_CONVERSATION),
	QUESTION_DEITY(Conversations.DEITY_CONVERSATION),
	ANSWER_DEITY_WORSHIP(Conversations.DEITY_CONVERSATION),
	ANSWER_DEITY_DONT_WORSHIP(Conversations.DEITY_CONVERSATION),
	ANSWER_DEITY_ALREADY(Conversations.DEITY_CONVERSATION),
	ANSWER_DEITY_CHANGED(Conversations.DEITY_CONVERSATION),
	QUESTION_DEITY_EXPLANATION(Conversations.DEITY_EXPLANATION_CONVERSATION),
	ANSWER_DEITY_EXPLANATION_YES(Conversations.DEITY_EXPLANATION_CONVERSATION),
	ANSWER_DEITY_EXPLANATION_NO(Conversations.DEITY_EXPLANATION_CONVERSATION),
	ANSWER_DEITY_EXPLANATION_GETLOST(Conversations.DEITY_EXPLANATION_CONVERSATION),
	QUESTION_DEITY_FOLLOWERS(Conversations.DEITY_FOLLOWERS_CONVERSATION),
	ANSWER_DEITY_FOLLOWERS_YES(Conversations.DEITY_FOLLOWERS_CONVERSATION),
	ANSWER_DEITY_FOLLOWERS_NO(Conversations.DEITY_FOLLOWERS_CONVERSATION),
	QUESTION_DEITY_REASON(Conversations.DEITY_REASON_CONVERSATION),
	ANSWER_DEITY_REASON(Conversations.DEITY_REASON_CONVERSATION),
	ANSWER_DEITY_REASON_DONT_CARE(Conversations.DEITY_REASON_CONVERSATION),
	QUESTION_DEMAND_MONEY(Conversations.DEMAND_MONEY_CONVERSATION),
	ANSWER_DEMAND_MONEY_GETLOST(Conversations.DEMAND_MONEY_CONVERSATION),
	ANSWER_DEMAND_MONEY_SURE(Conversations.DEMAND_MONEY_CONVERSATION),
	ANSWER_DEMAND_MONEY_NO(Conversations.DEMAND_MONEY_CONVERSATION),
	ANSWER_DEMAND_MONEY_CAN_ONLY(Conversations.DEMAND_MONEY_CONVERSATION),
	QUESTION_DRINKING_CONTEST_GOLD(Conversations.DRINKING_CONTEST_CONVERSATION),
	QUESTION_DRINKING_CONTEST(Conversations.DRINKING_CONTEST_CONVERSATION),
	ANSWER_DRINKING_CONTEST_YES(Conversations.DRINKING_CONTEST_CONVERSATION),
	ANSWER_DRINKING_CONTEST_NO(Conversations.DRINKING_CONTEST_CONVERSATION),
	ANSWER_DRINKING_CONTEST_LATER(Conversations.DRINKING_CONTEST_CONVERSATION),
	ANSWER_DRINKING_CONTEST_NOGOLD(Conversations.DRINKING_CONTEST_CONVERSATION),
	QUESTION_CURSE(Conversations.EXPLAIN_CURSE_CONVERSATION),
	ANSWER_CURSE_YES(Conversations.EXPLAIN_CURSE_CONVERSATION),
	ANSWER_CURSE_NO(Conversations.EXPLAIN_CURSE_CONVERSATION),
	ANSWER_CURSE_GETLOST(Conversations.EXPLAIN_CURSE_CONVERSATION),
	QUESTION_FAMILY(Conversations.FAMILY_CONVERSATION),
	ANSWER_FAMILY_YES(Conversations.FAMILY_CONVERSATION),
	ANSWER_FAMILY_NO(Conversations.FAMILY_CONVERSATION),
	QUESTION_GIVE_ITEM(Conversations.GIVE_COTTON_CONVERSATION),
	ANSWER_GIVE_ITEM_THANKS(Conversations.GIVE_COTTON_CONVERSATION),
	ANSWER_GIVE_ITEM_GETLOST(Conversations.GIVE_COTTON_CONVERSATION),
	ANSWER_GIVE_ITEM_AGAIN(Conversations.GIVE_COTTON_CONVERSATION),
	QUESTION_GIVE_MONEY(Conversations.GIVE_MONEY_CONVERSATION),
	ANSWER_GIVE_MONEY_THANKS(Conversations.GIVE_MONEY_CONVERSATION),
	ANSWER_GIVE_MONEY_GETLOST(Conversations.GIVE_MONEY_CONVERSATION),
	ANSWER_GIVE_MONEY_AGAIN(Conversations.GIVE_MONEY_CONVERSATION),
	ANSWER_GIVE_MONEY_BRIBE(Conversations.GIVE_MONEY_CONVERSATION),
	QUESTION_GOAL(Conversations.GOAL_CONVERSATION),
	ANSWER_GOAL_YES(Conversations.GOAL_CONVERSATION),
	ANSWER_GOAL_NO(Conversations.GOAL_CONVERSATION),
	QUESTION_IMMEDIATE_GOAL(Conversations.IMMEDIATE_GOAL_CONVERSATION),
	ANSWER_IMMEDIATE_GOAL_YES(Conversations.IMMEDIATE_GOAL_CONVERSATION),
	ANSWER_IMMEDIATE_GOAL_NO(Conversations.IMMEDIATE_GOAL_CONVERSATION),
	QUESTION_INTIMIDATE(Conversations.getIntimidateConversation()),
	ANSWER_INTIMIDATE_GETLOST(Conversations.getIntimidateConversation()),
	ANSWER_INTIMIDATE_COMPLY(Conversations.getIntimidateConversation()),
	QUESTION_JOIN_PERFORMER_ORG(Conversations.JOIN_PERFORMER_ORGANIZATION_CONVERSATION),
	ANSWER_JOIN_PERFORMER_ORG_YES(Conversations.JOIN_PERFORMER_ORGANIZATION_CONVERSATION),
	ANSWER_JOIN_PERFORMER_ORG_NO(Conversations.JOIN_PERFORMER_ORGANIZATION_CONVERSATION),
	QUESTION_JOIN_TARGET_ORG(Conversations.JOIN_TARGET_ORGANIZATION_CONVERSATION),
	ANSWER_JOIN_TARGET_ORG_YES(Conversations.JOIN_TARGET_ORGANIZATION_CONVERSATION),
	ANSWER_JOIN_TARGET_ORG_NO(Conversations.JOIN_TARGET_ORGANIZATION_CONVERSATION),
	QUESTION_KISS(Conversations.KISS_CONVERSATION),
	ANSWER_KISS_YES(Conversations.KISS_CONVERSATION),
	ANSWER_KISS_NO(Conversations.KISS_CONVERSATION),
	ANSWER_KISS_SAME(Conversations.KISS_CONVERSATION),
	ANSWER_KISS_DIFFERENT(Conversations.KISS_CONVERSATION),
	QUESTION_LEARN_SKILL(Conversations.LEARN_SKILLS_USING_ORGANIZATION),
	ANSWER_LEARN_SKILL_YES(Conversations.LEARN_SKILLS_USING_ORGANIZATION),
	ANSWER_LEARN_SKILL_NO(Conversations.LEARN_SKILLS_USING_ORGANIZATION),
	QUESTION_LOCATION(Conversations.LOCATION_CONVERSATION),
	ANSWER_LOCATION(Conversations.LOCATION_CONVERSATION),
	QUESTION_MERGE_ORG(Conversations.MERGE_ORGANIZATIONS_CONVERSATION),
	ANSWER_MERGE_ORG_YES(Conversations.MERGE_ORGANIZATIONS_CONVERSATION),
	ANSWER_MERGE_ORG_NO(Conversations.MERGE_ORGANIZATIONS_CONVERSATION),
	QUESTION_MINOR_HEAL(Conversations.MINOR_HEAL_CONVERSATION),
	ANSWER_MINOR_HEAL_YES(Conversations.MINOR_HEAL_CONVERSATION),
	ANSWER_MINOR_HEAL_NO(Conversations.MINOR_HEAL_CONVERSATION),
	ANSWER_MINOR_HEAL_GETLOST(Conversations.MINOR_HEAL_CONVERSATION),
	QUESTION_NAME(Conversations.NAME_CONVERSATION),
	ANSWER_NAME(Conversations.NAME_CONVERSATION),
	ANSWER_NAME_GETLOST(Conversations.NAME_CONVERSATION),
	ANSWER_NAME_TOLD_WHILE(Conversations.NAME_CONVERSATION),
	ANSWER_NAME_LIKE(Conversations.NAME_CONVERSATION),
	ANSWER_NAME_DIDNT(Conversations.NAME_CONVERSATION),
	QUESTION_NICE(Conversations.NICER_CONVERSATION),
	QUESTION_NOT_NICE(Conversations.NICER_CONVERSATION),
	ANSWER_NICE_YES(Conversations.NICER_CONVERSATION),
	ANSWER_NICE_NO(Conversations.NICER_CONVERSATION),
	ANSWER_NICE_SAME(Conversations.NICER_CONVERSATION),
	ANSWER_NICE_DIFFERENT(Conversations.NICER_CONVERSATION),
	QUESTION_ORG(Conversations.ORGANIZATION_CONVERSATION),
	ANSWER_ORG_GROUP(Conversations.ORGANIZATION_CONVERSATION),
	ANSWER_ORG_NO_GROUP(Conversations.ORGANIZATION_CONVERSATION),
	ANSWER_ORG_SAME(Conversations.ORGANIZATION_CONVERSATION),
	ANSWER_ORG_DIFFERENT(Conversations.ORGANIZATION_CONVERSATION),
	QUESTION_PAY_BOUNTY(Conversations.PAY_BOUNTY_CONVERSATION),
	ANSWER_PAY_BOUNTY_YES(Conversations.PAY_BOUNTY_CONVERSATION),
	ANSWER_PAY_BOUNTY_NO(Conversations.PAY_BOUNTY_CONVERSATION),
	QUESTION_PROFESSION(Conversations.PROFESSION_CONVERSATION),
	ANSWER_PROFESSION_MY(Conversations.PROFESSION_CONVERSATION),
	ANSWER_PROFESSION_NO(Conversations.PROFESSION_CONVERSATION),
	ANSWER_PROFESSION_SAME(Conversations.PROFESSION_CONVERSATION),
	ANSWER_PROFESSION_NEW(Conversations.PROFESSION_CONVERSATION),
	QUESTION_PROFESSION_USERS(Conversations.PROFESSION_PRACTITIONERS_CONVERSATION),
	ANSWER_PROFESSION_USERS_YES(Conversations.PROFESSION_PRACTITIONERS_CONVERSATION),
	ANSWER_PROFESSION_USERS_NO(Conversations.PROFESSION_PRACTITIONERS_CONVERSATION),
	QUESTION_PROFESSION_REASON(Conversations.PROFESSION_REASON_CONVERSATION),
	ANSWER_PROFESSION_REASON_YES(Conversations.PROFESSION_REASON_CONVERSATION),
	ANSWER_PROFESSION_REASON_NO(Conversations.PROFESSION_REASON_CONVERSATION),
	ANSWER_PROFESSION_REASON_SAME(Conversations.PROFESSION_REASON_CONVERSATION),
	ANSWER_PROFESSION_REASON_DIFFERENT(Conversations.PROFESSION_REASON_CONVERSATION),
	QUESTION_PROPOSE_MATE(Conversations.PROPOSE_MATE_CONVERSATION),
	ANSWER_PROPOSE_MATE_YES(Conversations.PROPOSE_MATE_CONVERSATION),
	ANSWER_PROPOSE_MATE_NO(Conversations.PROPOSE_MATE_CONVERSATION),
	ANSWER_PROPOSE_MATE_SAME(Conversations.PROPOSE_MATE_CONVERSATION),
	ANSWER_PROPOSE_MATE_DIFFERENT(Conversations.PROPOSE_MATE_CONVERSATION),
	QUESTION_RELATIONSHIP(Conversations.RELATIONSHIP_CONVERSATION),
	ANSWER_RELATIONSHIP_DONT(Conversations.RELATIONSHIP_CONVERSATION),
	ANSWER_RELATIONSHIP_LIKE(Conversations.RELATIONSHIP_CONVERSATION),
	ANSWER_RELATIONSHIP_REALLY_LIKE(Conversations.RELATIONSHIP_CONVERSATION),
	ANSWER_RELATIONSHIP_DISLIKE(Conversations.RELATIONSHIP_CONVERSATION),
	QUESTION_SELL_BUILDING(Conversations.SELL_APOTHECARY_CONVERSATION),
	ANSWER_SELL_BUILDING_YES(Conversations.SELL_APOTHECARY_CONVERSATION),
	ANSWER_SELL_BUILDING_NO(Conversations.SELL_APOTHECARY_CONVERSATION),
	QUESTION_SET_PRICE(Conversations.SET_ORGANIZATION_PROFIT_PERCENTAGE),
	ANSWER_SET_PRICE_YES(Conversations.SET_ORGANIZATION_PROFIT_PERCENTAGE),
	ANSWER_SET_PRICE_NO(Conversations.SET_ORGANIZATION_PROFIT_PERCENTAGE),
	QUESTION_SHARE_KNOWLEDGE(Conversations.SHARE_KNOWLEDGE_CONVERSATION),
	ANSWER_SHARE_KNOWLEDGE_THANKS(Conversations.SHARE_KNOWLEDGE_CONVERSATION),
	ANSWER_SHARE_KNOWLEDGE_GETLOST(Conversations.SHARE_KNOWLEDGE_CONVERSATION),
	QUESTION_START_ARENA_FIGHT(Conversations.START_ARENA_FIGHT_CONVERSATION),
	ANSWER_START_ARENA_FIGHT_YES(Conversations.START_ARENA_FIGHT_CONVERSATION),
	ANSWER_START_ARENA_FIGHT_NO(Conversations.START_ARENA_FIGHT_CONVERSATION),
	ANSWER_START_ARENA_FIGHT_WAIT(Conversations.START_ARENA_FIGHT_CONVERSATION),
	QUESTION_STOP_SELLING(Conversations.STOP_SELLING_CONVERSATION),
	ANSWER_STOP_SELLING_YES(Conversations.STOP_SELLING_CONVERSATION),
	ANSWER_STOP_SELLING_NO(Conversations.STOP_SELLING_CONVERSATION),
	QUESTION_SWITCH_DEITY(Conversations.STOP_SELLING_CONVERSATION),
	ANSWER_SWITCH_DEITY_YES(Conversations.STOP_SELLING_CONVERSATION),
	ANSWER_SWITCH_DEITY_NO(Conversations.STOP_SELLING_CONVERSATION),
	ANSWER_SWITCH_DEITY_GETLOST(Conversations.STOP_SELLING_CONVERSATION),
	QUESTION_VOTE_LEADER(Conversations.VOTE_LEADER_ORGANIZATION_CONVERSATION),
	ANSWER_VOTE_LEADER(Conversations.VOTE_LEADER_ORGANIZATION_CONVERSATION),
	QUESTION_LEADER(Conversations.WHO_IS_LEADER_ORGANIZATION_CONVERSATION),
	ANSWER_LEADER_YES(Conversations.WHO_IS_LEADER_ORGANIZATION_CONVERSATION),
	ANSWER_LEADER_NONE(Conversations.WHO_IS_LEADER_ORGANIZATION_CONVERSATION),
	ANSWER_LEADER_ALREADY(Conversations.WHO_IS_LEADER_ORGANIZATION_CONVERSATION),
	QUESTION_ANGRY(Conversations.WHY_ANGRY_CONVERSATION),
	ANSWER_ANGRY_REASON(Conversations.WHY_ANGRY_CONVERSATION),
	ANSWER_ANGRY_GETLOST(Conversations.WHY_ANGRY_CONVERSATION),
	QUESTION_ANGRY_OTHER(Conversations.WHY_ANGRY_OTHER_CONVERSATION),
	ANSWER_ANGRY_OTHER_REASON(Conversations.WHY_ANGRY_OTHER_CONVERSATION),
	ANSWER_ANGRY_OTHER_GETLOST(Conversations.WHY_ANGRY_OTHER_CONVERSATION),
	QUESTION_REMOVE_CURSE(Conversations.REMOVE_CURSE_CONVERSATION),
	ANSWER_REMOVE_CURSE_YES(Conversations.REMOVE_CURSE_CONVERSATION),
	ANSWER_REMOVE_CURSE_NO(Conversations.REMOVE_CURSE_CONVERSATION),
	ANSWER_REMOVE_CURSE_GETLOST(Conversations.REMOVE_CURSE_CONVERSATION),
	QUESTION_DEMANDS(Conversations.DEMANDS_CONVERSATION),
	ANSWER_DEMANDS_YES(Conversations.DEMANDS_CONVERSATION),
	ANSWER_DEMANDS_NO(Conversations.DEMANDS_CONVERSATION),
	SAW_THROUGH_DISGUISE(Conversations.SAW_DISGUISING_CONVERSATION),
	WHY_NOT_INTELLIGENT(Conversations.WHY_NOT_INTELLIGENT_CONVERSATION),
	SEE_THROUGH(Conversations.WHY_NOT_INTELLIGENT_CONVERSATION),
	LOOK_SAME(Conversations.LOOK_THE_SAME_CONVERSATION),
	QUESTION_CAN_ATTACK_CRIMINALS(Conversations.CAN_ATTACK_CRIMINALS_CONVERSATION),
	ANSWER_CAN_ATTACK_CRIMINALS_YES(Conversations.CAN_ATTACK_CRIMINALS_CONVERSATION),
	ANSWER_CAN_ATTACK_CRIMINALS_NO(Conversations.CAN_ATTACK_CRIMINALS_CONVERSATION),
	QUESTION_CAN_COLLECT_TAXES(Conversations.CAN_COLLECT_TAXES_CONVERSATION),
	ANSWER_CAN_COLLECT_TAXES_YES(Conversations.CAN_COLLECT_TAXES_CONVERSATION),
	ANSWER_CAN_COLLECT_TAXES_NO(Conversations.CAN_COLLECT_TAXES_CONVERSATION),
	GOAL_MARK_AS_SELLABLE,
	GOAL_SCRIBE_SPELLS,
	GOAL_ADJUST_PRICES,
	GOAL_ANIMAL_FOOD,
	GOAL_APOTHECARY,
	GOAL_ARENA_FIGHT,
	GOAL_ARENA,
	GOAL_ASSASSINATE_TARGET,
	GOAL_ATTACK_TARGET,
	GOAL_BECOME_LICH,
	GOAL_BECOME_PROFESSION_ORGANIZATION_MEMBER,
	GOAL_BECOME_RELIGION_ORGANIZATION_MEMBER, 
	GOAL_BRAWL, 
	GOAL_BREAKUP_WITH_MATE, 
	GOAL_BREWERY, 
	GOAL_BUTCHER_KNIFE, 
	GOAL_BUTCHER_OWNED_CATTLE, 
	GOAL_BUY_CLOTHES, 
	GOAL_CAPTURE_CRIMINALS, 
	GOAL_CATCH_FISH, 
	GOAL_CATCH_THIES, 
	GOAL_CHILDREN,
	GOAL_CHOOSE_DEITY, 
	GOAL_CHOOSE_PROFESSION, 
	GOAL_CLAIM_CATTLE, 
	GOAL_COCOON_OUTSIDER, 
	GOAL_COLLECT_ARENA_REWARD, 
	GOAL_COLLECT_PAYCHECK, 
	GOAL_COLLECT_TAXES, 
	GOAL_COLLECT_WATER, 
	GOAL_COTTON, 
	GOAL_CRAFT_EQUIPMENT, 
	GOAL_CREATE_APOTHECARY, 
	GOAL_CREATE_BREWERY, GOAL_CREATE_BUTCHER_KNIFE, GOAL_CREATE_FISHING_POLE, GOAL_CREATE_FOOD_SOURCE, GOAL_CREATE_FURNITURE, GOAL_CREATE_GRAVE, GOAL_CREATE_HEALING_POTION, GOAL_CREATE_HOUSE, GOAL_CREATE_IRON_AXE, GOAL_CREATE_NEWS_PAPER, GOAL_CREATE_OR_PLANT_WOOD, GOAL_CREATE_PAPER, GOAL_CREATE_PAPERMILL, GOAL_CREATE_PICKAXE, GOAL_CREATE_POISON, GOAL_CREATE_REPAIR_HAMMER, GOAL_CREATE_SCYTHE, GOAL_CREATE_SLEEPING_POTION, GOAL_CREATE_SMITH, GOAL_CREATE_WEAVERY, GOAL_CREATE_WINE, GOAL_CREATE_WOOD, GOAL_CREATE_WORKBENCH, GOAL_CURSE_KISS, GOAL_DEITY_BOON, GOAL_DESTROY_SHRINES_TO_OTHER_DEITIES, GOAL_DONATE_MONEY_TO_ARENA, GOAL_DRINK_WATER, GOAL_EQUIP_BUTCHER_KNIFE, GOAL_EQUIPMENT, GOAL_EQUIP_PICKAXE, GOAL_EQUIP_REPAIR_HAMMER, GOAL_EQUIP_SCYTHE, GOAL_EQUIP_WOOD_CUTTING_TOOL, GOAL_FEED_OTHERS, GOAL_FIGHT_IN_ARENA, GOAL_FILL_SOUL_GEM, GOAL_FIND_ASSASSINATION_CLIENT, GOAL_FIND_SECLUDED_LOCATION, GOAL_FISHING_POLE, GOAL_FOOD, GOAL_FURNITURE, GOAL_GATHER_FOOD, GOAL_GATHER_REMAINS, GOAL_GET_DISEASE_CURED, GOAL_GET_HEALED, GOAL_GET_POISON_CURED, GOAL_GHOUL, GOAL_GHOUL_MEAT_LEVEL, GOAL_GOLD, GOAL_HANDOVER_TAXES, GOAL_HARVEST_COTTON, GOAL_HARVESTING_GRAPES, GOAL_HARVEST_NIGHTSHADE, GOAL_HEAL_OTHERS, GOAL_HOUSE, GOAL_HUNT_UNDEAD, GOAL_IDLE, GOAL_IMPROVE_RELATIONSHIP, GOAL_JAIL, GOAL_KILL_OUTSIDER, GOAL_KILL_VILLAGERS, GOAL_LEARN_SKILL, GOAL_LEATHER, GOAL_LEGALIZE_VAMPIRISM, GOAL_LIBRARY, GOAL_MARK_AS_SELLABLE_OR_DROP, GOAL_MARK_BUILDING_AS_SELLABLE, GOAL_MARK_NON_EQUIPED_ITEMS_AS_SELLABLE, GOAL_MARK_UNUSED_BUILDING_AS_SELLABLE, GOAL_MATE, GOAL_MINE_GOLD, GOAL_MINE_ORE, GOAL_MINE_RESOURCES, GOAL_MINE_SOULGEMS, GOAL_MINE_STONE, GOAL_MINT_GOLD, GOAL_OFFSPRING, GOAL_ORE, GOAL_ORGANIZATION_CANDIDATE, GOAL_ORGANIZATION_VOTE, GOAL_PAPER, GOAL_PAPER_MILL, GOAL_PAY_BOUNTY, GOAL_PICKAXE, GOAL_POISON_WEAPON, GOAL_PROTECT_ONE_SELF, GOAL_READ_NEWS_PAPER, GOAL_RECRUIT_PROFESSION_ORGANIZATION_MEMBER, GOAL_REDISTRIBUTE_GOLD_AMONG_FAMILY, GOAL_RELEASE_CAPTURED_CRIMINALS, GOAL_REMOVE_CURSE, GOAL_REPAIR_EQUIPMENT, GOAL_REPAIR_HAMMER, GOAL_RESEARCH_MAGIC_SKILLS_KNOWLEDGE, GOAL_REST, GOAL_REVENGE, GOAL_SACRIFICE_PEOPLE_TO_DEITY, GOAL_SCYTHE, GOAL_SELL_FOOD, GOAL_SELL_FURNITURE, GOAL_SELL_GOLD, GOAL_SELL_HEALING_POTION, GOAL_SELL_HOUSE, GOAL_SELL_NEWSPAPER, GOAL_SELL_ORE, GOAL_SELL_POISON, GOAL_SELL_SLEEPING_POTION, GOAL_SELL_STOLEN_GOODS, GOAL_SELL_STONE, GOAL_SELL_UNUSED_ITEMS, GOAL_SELL_WINE, GOAL_SELL_WOOD, GOAL_SET_TAXES, GOAL_SEX, GOAL_SHACK, GOAL_SHRINE_TO_DEITY, GOAL_SMITH, GOAL_SOCIALIZE, GOAL_SOULGEM, GOAL_STAND_STILL_TO_TALK, GOAL_START_BRAWL, GOAL_START_DRINKING_CONTEST, GOAL_START_ORGANIZATION_VOTE, GOAL_STEAL, GOAL_STONE, GOAL_STOP_SELLING, GOAL_SUBDUE_OUTSIDERS, GOAL_SWINDLE_MONEY, GOAL_SWITCH_DEITY, GOAL_TRADE, GOAL_TRAIN, GOAL_USE_EQUIPMENT, GOAL_VAMPIRE_BLOOD, GOAL_WEAVE_CLOTHES, GOAL_WEAVE_LEATHER_ARMOR, GOAL_WEAVERY, GOAL_WINE, GOAL_WOOD_CUTTING_TOOL, GOAL_WOOD, GOAL_WORKBENCH, GOAL_DOING_NOTHING, CRAFT_ITEM, BREW_ITEM, MINT_GOLD, CREATE_STEEL, CREATE_PAPER, PLANT_TREE, PLANT_COTTON_PLANT, PLANT_NIGHTSHADE_PLANT, PLANT_GRAPEVINE_PLANT, PLANT_BERRYBUSH, BUILD_WELL, QUESTION_START_REBELLION, ANSWER_START_REBELLION_YES, ANSWER_START_REBELLION_NO, ANSWER_START_REBELLION_GET_LOST, GOAL_REBELLION, GOAL_CREATE_SAW, GOAL_SAW 
	;
	
	private final Conversation conversationKey;
	
	private TextId(Conversation conversationKey) {
		this.conversationKey = conversationKey;
	}
	
	private TextId(InterceptedConversation conversationKey) {
		this.conversationKey = null;
	}
	
	private TextId() {
		this.conversationKey = null;
	}

	private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("MessagesBundle");
	
	public String get() {
        return MESSAGES.getString(this.toString());
	}
	
	public String get(String parm) {
        MessageFormat formatter = createFormatter();
        return formatter.format(new Object[]{ parm });
	}
	
	public String get(String parm1, String parm2) {
        MessageFormat formatter = createFormatter();
        return formatter.format(new Object[]{ parm1, parm2 });
	}
	
	public String get(String parm1, String parm2, String parm3) {
		 MessageFormat formatter = createFormatter();
	        return formatter.format(new Object[]{ parm1, parm2, parm3 });
	}

	public String get(List<String> args) {
		MessageFormat formatter = createFormatter();
        return formatter.format(args.toArray());
	}

	private MessageFormat createFormatter() {
		MessageFormat formatter = new MessageFormat("");
        formatter.applyPattern(get().replace("'", "''"));
		return formatter;
	}
	
	public String get(int parm) {
		return get(Integer.toString(parm));
	}
	
	private Conversation getConversationKey() {
		return conversationKey;
	}

	private boolean isConversation() {
		return getConversationKey() != null;
	}
	
	private boolean isQuestion() {
		return name().startsWith("QUESTION_");
	}
	
	private boolean isAnswer() {
		return name().startsWith("ANSWER_");
	}
	
	public static List<ConversationDescription> getConversationDescriptions() {
		Map<Conversation, ConversationDescription> conversationDescriptions = new HashMap<>();
		for(TextId text : TextId.values()) {
			if (text.isConversation()) {
				Conversation conversationKey = text.getConversationKey();
				ConversationDescription conversationDescription = getConversationDescription(conversationDescriptions, conversationKey);
				if (text.isQuestion()) {
					conversationDescription.addQuestion(text.get());
				}
				if (text.isAnswer()) {
					conversationDescription.addAnswer(text.get());
				}
			}
		}
		
		
		List<ConversationDescription> conversationDescriptionList = new ArrayList<>(conversationDescriptions.values());
		validate(conversationDescriptionList);
		return conversationDescriptionList;
	}

	private static void validate(List<ConversationDescription> conversationDescriptionList) {
		for(ConversationDescription conversationDescription : conversationDescriptionList) {
			if (conversationDescription.getQuestions().size() == 0) {
				throw new IllegalStateException("conversationDescription " + conversationDescription.getConversationKey() + " doens't contain any questions");
			}
			if (conversationDescription.getAnswers().size() == 0) {
				throw new IllegalStateException("conversationDescription " + conversationDescription.getConversationKey() + " doens't contain any answers");
			}
		}
	}

	private static ConversationDescription getConversationDescription(Map<Conversation, ConversationDescription> conversationDescriptions, Conversation conversationKey) {
		final ConversationDescription conversationDescription;
		if (conversationDescriptions.containsKey(conversationKey)) {
			conversationDescription = conversationDescriptions.get(conversationKey);
		} else {
			conversationDescription = new ConversationDescription(conversationKey);
			conversationDescriptions.put(conversationKey, conversationDescription);
		}
		return conversationDescription;
	}
	
	public void parse(TextParser textParser) {
		String patternString = get();
		Pattern pattern = Pattern.compile("\\{\\d*\\}");
		Matcher matcher = pattern.matcher(patternString);
		int lastMatchedEnd = 0;
		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			textParser.constantStringFound(patternString.substring(lastMatchedEnd, start));
			String group = matcher.group();
			textParser.variableFound(Integer.parseInt(group.substring(1, group.length() - 1)));
			lastMatchedEnd = end;
		}
		if (lastMatchedEnd < patternString.length()) {
			textParser.constantStringFound(patternString.substring(lastMatchedEnd, patternString.length()));
		}
	}
}
