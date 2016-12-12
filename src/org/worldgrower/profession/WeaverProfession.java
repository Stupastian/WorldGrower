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
package org.worldgrower.profession;

import java.io.ObjectStreamException;
import java.util.Arrays;
import java.util.List;

import org.worldgrower.Constants;
import org.worldgrower.attribute.SkillProperty;
import org.worldgrower.generator.Item;
import org.worldgrower.goal.Goal;
import org.worldgrower.goal.Goals;
import org.worldgrower.gui.ImageIds;

public class WeaverProfession implements Profession {

	public WeaverProfession(List<Profession> allProfessions) {
		allProfessions.add(this);
	}

	@Override
	public String getDescription() {
		return "weaver";
	}

	@Override
	public List<Goal> getProfessionGoals() {
		return Arrays.asList(
				Goals.WEAVERY_GOAL,
				Goals.COTTON_GOAL,
				Goals.WEAVE_CLOTHES_GOAL,
				Goals.MARK_CLOTHES_AS_SELLABLE_GOAL,
				Goals.LEATHER_GOAL);
	}

	@Override
	public SkillProperty getSkillProperty() {
		return Constants.WEAVING_SKILL;
	}
	
	public Object readResolve() throws ObjectStreamException {
		return readResolveImpl();
	}

	@Override
	public boolean isPaidByVillagerLeader() {
		return false;
	}
	
	@Override
	public boolean avoidEnemies() {
		return true;
	}

	@Override
	public List<Item> getSellItems() {
		return Arrays.asList(Item.COTTON_BOOTS, Item.COTTON_GLOVES, Item.COTTON_HAT, Item.COTTON_PANTS, Item.COTTON_SHIRT);
	}
	
	@Override
	public List<Item> getBuyItems() {
		return Arrays.asList(Item.COTTON, Item.LEATHER);
	}

	@Override
	public ImageIds getImageId() {
		return ImageIds.COTTON_SHIRT;
	}
}
