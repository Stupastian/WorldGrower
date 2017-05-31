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
package org.worldgrower.deity;

import java.io.ObjectStreamException;
import java.util.Arrays;
import java.util.List;

import org.worldgrower.Constants;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.attribute.SkillProperty;
import org.worldgrower.condition.Condition;
import org.worldgrower.condition.WorldStateChangedListeners;
import org.worldgrower.creaturetype.CreatureType;
import org.worldgrower.goal.Goals;
import org.worldgrower.gui.ImageIds;
import org.worldgrower.personality.PersonalityTrait;
import org.worldgrower.profession.Professions;

public class Poseidon implements Deity {

	@Override
	public String getName() {
		return "Poseidon";
	}

	@Override
	public String getExplanation() {
		return getName() + " is the God of the oceans, earthquakes, droughts, floods, water, aquatic creatures, marine weather and horses";
	}

	public Object readResolve() throws ObjectStreamException {
		return readResolveImpl();
	}
	
	@Override
	public List<String> getReasons() {
		return Arrays.asList(
				"As a priest of " + getName() + ", I want to honor the God of the seas so that the seas are safe and full of fish"
		);
	}

	@Override
	public int getReasonIndex(WorldObject performer, World world) {
		if (performer.getProperty(Constants.PROFESSION) == Professions.PRIEST_PROFESSION) {
			return 0;
		}
		
		return -1;
	}
	
	@Override
	public int getOrganizationGoalIndex(WorldObject performer, World world) {
		if (performer.getProperty(Constants.PERSONALITY).getValue(PersonalityTrait.POWER_HUNGRY) > 0) {
			return getOrganizationGoals().indexOf(Goals.SWITCH_DEITY_GOAL);
		}
		return -1;
	}
	
	@Override
	public void onTurn(World world, WorldStateChangedListeners worldStateChangedListeners) {
		if (DeityRetribution.shouldCheckForDeityRetribution(this, world)) { 
			if (DeityPropertyUtils.deityIsUnhappy(this, world)) {
				killFish(world);
			}
		}
	}

	private void killFish(World world) {
		List<WorldObject> fishCreatures = world.findWorldObjectsByProperty(Constants.CREATURE_TYPE, w -> w.getProperty(Constants.CREATURE_TYPE) == CreatureType.FISH_CREATURE_TYPE);
		for(int i=0; i<fishCreatures.size(); i++) {
			WorldObject fish = fishCreatures.get(i);
			if (i % 2 == 0) {
				fish.setProperty(Constants.HIT_POINTS, 0);
			}
		}
		
		world.getWorldStateChangedListeners().deityRetributed(this, getName() + " is displeased due to lack of followers and worship and caused some fish to die");
	}
	
	@Override
	public ImageIds getStatueImageId() {
		return ImageIds.STATUE_OF_POSEIDON;
	}
	
	@Override
	public SkillProperty getSkill() {
		return Constants.FISHING_SKILL;
	}
	
	@Override
	public ImageIds getBoonImageId() {
		return ImageIds.POSEIDON_SYMBOL;
	}
	
	@Override
	public Condition getBoon() {
		return Condition.POSEIDON_BOON_CONDITION;
	}
	
	@Override
	public String getBoonDescription() {
		return getName() + "'s boon: grants a bonus to fishing";
	}
}
