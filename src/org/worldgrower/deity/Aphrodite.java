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
import org.worldgrower.gui.ImageIds;
import org.worldgrower.profession.Professions;

public class Aphrodite implements Deity {

	@Override
	public String getName() {
		return "Aphrodite";
	}

	@Override
	public String getExplanation() {
		return getName() + " is the Goddess of love, beauty, desire, sex and pleasure.";
	}

	public Object readResolve() throws ObjectStreamException {
		return readResolveImpl();
	}
	
	@Override
	public List<String> getReasons() {
		return Arrays.asList(
				"I worship " + getName() + " to have a good love life.",
				"As a priest of " + getName() + ", I strive to spread love and beauty in our community"
		);
	}

	@Override
	public int getReasonIndex(WorldObject performer, World world) {
		if (performer.getProperty(Constants.CHARISMA) >= 12) {
			return 0;
		} else if (performer.getProperty(Constants.PROFESSION) == Professions.PRIEST_PROFESSION) {
			return 1;
		}
		
		return -1;
	}

	@Override
	public void onTurn(World world, WorldStateChangedListeners worldStateChangedListeners) {
	}

	@Override
	public ImageIds getStatueImageId() {
		return ImageIds.STATUE_OF_APHRODITE;
	}

	@Override
	public SkillProperty getSkill() {
		return Constants.DIPLOMACY_SKILL;
	}

	@Override
	public ImageIds getBoonImageId() {
		return ImageIds.APHRODITE_SYMBOL;
	}

	@Override
	public Condition getBoon() {
		return Condition.APHRODITE_BOON_CONDITION;
	}

	@Override
	public String getBoonDescription() {
		return getName() + "'s boon: grants a bonus to enchantment magic spells";
	}
}
